# 목차

### 자바
- [🍵자바](https://github.com/dorkem/F-LAB/blob/main/JAVA/java.md)
- [🧊OOP](https://github.com/dorkem/F-LAB/blob/main/JAVA/OOP.md)

### 스프링
- [🍃디스패처 서블릿&리졸버](https://github.com/dorkem/F-LAB/blob/main/SPRING/DispatcherServlet.md)

### DB
- [🦅RealMySQL](https://github.com/dorkem/F-LAB/blob/main/DB/RealMySQL.md)
- [🔒Lock](https://github.com/dorkem/F-LAB/blob/main/DB/lock.md)
- [🐬MySql](https://github.com/dorkem/F-LAB/blob/main/DB/MySQL.md)
- [💰Cache](https://github.com/dorkem/F-LAB/blob/main/DB/Cache.md)

### Web & 네트워크
- [🕸️HTTP The Definitive Guide](https://github.com/dorkem/F-LAB/blob/main/NETWORK/HTTP-The-Definitive-Guide.md)
- [🤝🏻TCP](https://github.com/dorkem/F-LAB/blob/main/NETWORK/TCP.md)
- [🗝️AUTH](https://github.com/dorkem/F-LAB/blob/main/NETWORK/Login.md)

<br><br><br>

# 🗝️AUTH
## 📍 JWT와 세션 로그인 방식 비교
### 상태성(Stateful) vs 무상태성(Stateless) 인증 아키텍처 비교
- 서버 아키텍처 설계 시 가장 중요한 기준은 '서버가 클라이언트의 이전 상태(Context)를 메모리에 저장하고 의존하는가?' 이다.
- `Stateful` (상태 유지): 서버가 클라이언트의 상태 정보를 메모리나 DB에 직접 보관하고, 매 요청마다 이를 조회하여 처리하는 방식.
- `Stateless` (무상태): 서버가 클라이언트의 상태를 전혀 저장하지 않으며, 클라이언트가 매 요청마다 인증에 필요한 모든 완전한 정보(토큰 등)를 담아 서버에 전달하는 방식.

### 분산 환경(Scale-out)에서의 Stateful 아키텍처 한계
- `단일 서버`에서는 `Stateful` 방식이 효율적이나, 트래픽 증가로 서버를 여러 대로 늘리는 `스케일 아웃(Scale-out)` 환경에서는 구조적인 `문제`가 발생합니다.
    - 세션 불일치 현상: 사용자가 서버 A에서 로그인하여 세션이 생성되었을 때, 로드밸런서가 다음 요청을 서버 B로 라우팅하면 서버 B에는 해당 세션 정보가 없어 인증에 실패(로그아웃)하게 됩니다.
- 이를 해결하기 위한 아키텍처의 한계
    - `Sticky Session`: 로드밸런서가 특정 사용자의 요청을 무조건 특정 서버로만 보내도록 강제하는 방식. 트래픽 분산(Load Balancing)의 본래 목적이 훼손되며, 해당 서버 다운 시 세션이 모두 유실됩니다.
    - `Session Clustering`: 모든 서버가 세션 정보를 동일하게 복제하여 공유하는 방식. 서버 메모리 낭비가 심하고, 복제를 위한 네트워크 오버헤드가 발생합니다.

<br><br>

## 📍 상태성 (Stateful) 인증 방식: 세션 (Session)
- 핵심 원리: 서버가 클라이언트의 상태 정보를 메모리나 DB에 직접 보관한다.

### 동작 순서

1. 인증 요청: 사용자가 ID/PW를 서버로 전송하여 `로그인을 요청`한다.
2. 상태 저장: 서버는 계정 정보를 대조하고 검증에 성공하면, 해당 사용자의 상태 정보(Session)를 서버 측 저장소(메모리 또는 세션 DB)에 `저장`한다.
3. Session ID 발급: 서버는 이 세션 정보를 식별할 수 있는 고유한 Session ID를 생성하여 HTTP 응답의 `Set-Cookie 헤더`를 통해 클라이언트에게 전달한다.
4. API 요청: 클라이언트(브라우저)는 이후 모든 API 요청 시 발급받은 Session Cookie를 `HTTP 헤더`에 담아 보낸다.
5. 상태 대조 및 인가: 서버는 매 요청마다 전달받은 `Session ID`를 자신이 보관하고 있는 `세션 저장소`와 `대조`하여 유효한 사용자인지 확인한 후 로직을 처리한다.

### 한계 및 특징

- 외부 저장소 병목
    - 앞서 언급한 분산 환경의 한계를 극복하기 위해 Redis 같은 외부 인메모리 저장소를 두어 세션을 중앙에서 관리하는 방식을 주로 사용합니다. 
하지만 대규모 트래픽 환경에서는 매 API 요청마다 애플리케이션 서버가 Redis 서버를 조회해야 하므로, 이 과정 자체가 새로운 `네트워크 I/O 병목(Bottleneck)`이 될 수 있습니다.
- 브라우저 정책 제약
    - 서버가 발급한 쿠키는 `SOP`(Same-Origin Policy) 및 최신 브라우저의 `서드파티 쿠키 차단 정책`으로 인해 다른 도메인(Cross-Domain)으로 전송하기 어려워 시스템 간 `연동에 제약`이 큽니다.

<br><br>

## 📍 무상태성 (Stateless) 인증 방식: JWT (JSON Web Token)
- 핵심 원리: 서버는 상태 정보를 저장하지 않고, 자체적으로 검증 가능한 정보와 서명이 담긴 '토큰'을 클라이언트에게 발급하여 클라이언트가 직접 관리하게 한다.

### JWT의 구조

- `Header`: 토큰의 타입(JWT)과 서명에 사용된 `해시 알고리즘`(ex: HMAC SHA256) 정보.
- `Payload`: `유저 ID`, `권한`, `만료 시간` 등 비즈니스 로직에 필요한 실제 정보가 Base64로 인코딩되어 담긴다.
- `Signature`: 인코딩된 Header와 Payload 데이터에, 서버만 알고 있는 `비밀키(Secret Key)`를 조합하여 해시 알고리즘으로 생성한 암호화된 `서명`. (데이터 위변조 방지 역할)

### 디테일한 동작 순서 (인증 및 검증)
1. 토큰 발급: 사용자가 로그인하면 서버는 `DB를 대조`하고, 유효할 경우 서버의 비밀키로 서명된 `Access Token`과 `Refresh Token`을 `생성`하여 반환한다.
2. API 요청: 클라이언트는 API 요청 시 HTTP 헤더에 `Authorization: Bearer <엑세스토큰>` 형태로 토큰을 담아 보낸다.
3. 토큰 분해 (DB 조회 없음): 서버의 보안 필터는 DB나 세션 저장소를 조회하지 않고, 전달받은 토큰을 Header, Payload, Signature 세 조각으로 `분해`한다.
4. 서명 재계산 및 비교: 서버는 전달받은 Header와 Payload 값을 자신이 가진 `비밀키(Secret Key)`로 다시 `해싱`(재계산)해 본다.
5. 유효성 검증: 방금 서버가 재계산하여 나온 해시값과, 클라이언트가 보내온 Signature 값이 정확히 일치하는지 `비교`한다. 일치하면 위조되지 않은 정상 토큰으로 간주한다.
6. 로직 실행: 검증에 통과하면 Payload를 디코딩하여 유저 ID 등 필요한 정보를 확인하고 비즈니스 로직을 실행한다.

### 토큰 갱신 흐름 (Refresh Flow)
- `서버`는 매 요청마다 Payload의 만료 시간(exp)을 확인하며, 기한이 지났다면 401 Unauthorized (인증 만료) 응답을 반환한다.
- `클라이언트`는 미리 보관해 둔 Refresh Token(유저 ID를 Key로 하여 서버 DB/Redis에 저장된 긴 수명의 토큰)을 담아 `/refresh` 엔드포인트로 갱신을 요청한다.
- 서버는 전달받은 Refresh Token을 DB의 값과 대조하고 유효성을 검증한 뒤, 새로운 Access Token을 `발급`하여 내려준다.

### 장점 및 특징 (왜 분산 환경에 유리한가)
- 확장성 극대화: 토큰 자체가 유저 정보와 위조 검증을 위한 서명을 모두 포함하고 있습니다. 서버는 외부 저장소를 조회할 필요 없이, 자신이 가진 비밀키로 서명만 검증하면 되므로 서버를 수백 대로 스케일 아웃해도 완벽하게 동작합니다.
- 플랫폼 간 연동성: 쿠키와 달리 HTTP 헤더에 명시적으로 담아 보내므로 CORS(교차 출처) 환경이나 모바일 앱, 외부 파트너사와의 소셜 로그인(OAuth) 연동 시 보안 통제와 구현이 매우 용이합니다.


## OAuth

<img width="100%" alt="Image" src="https://github.com/user-attachments/assets/33cf7d9c-4c5b-4e6a-8c9c-de313cab815a" />
