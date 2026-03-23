# 📋 목차

### 자바
- [🍵자바](https://github.com/dorkem/F-LAB/blob/main/JAVA/java.md)
- [🧊OOP](https://github.com/dorkem/F-LAB/blob/main/JAVA/OOP.md)

### 스프링
- [🍃디스패처 서블릿&리졸버](https://github.com/dorkem/F-LAB/blob/main/SPRING/DispatcherServlet.md)
- [🌿스프링부트](https://github.com/dorkem/F-LAB/blob/main/SPRING/SpringBoot.md)

### DB
- [🦅RealMySQL](https://github.com/dorkem/F-LAB/blob/main/DB/RealMySQL.md)
- [🔒Lock](https://github.com/dorkem/F-LAB/blob/main/DB/lock.md)
- [🐬MySql](https://github.com/dorkem/F-LAB/blob/main/DB/MySQL.md)
- [💰Cache](https://github.com/dorkem/F-LAB/blob/main/DB/Cache.md)

### Web & 네트워크
- [🕸️HTTP The Definitive Guide](https://github.com/dorkem/F-LAB/blob/main/NETWORK/HTTP-The-Definitive-Guide.md)
- [🤝🏻TCP](https://github.com/dorkem/F-LAB/blob/main/NETWORK/TCP.md)
- [🗝️AUTH](https://github.com/dorkem/F-LAB/blob/main/NETWORK/Login.md)

### DevOps
- [🐈‍⬛DevOps](https://github.com/dorkem/F-LAB/blob/main/CICD/DevOps.md)

<br><br><br>

# 🤝🏻TCP
## 📍 TCP/IP (전송 제어 프로토콜)
- TCP/IP는 인터넷에서 데이터를 안정적으로 보내기 위한 전체적인 규칙의 모음입니다. 단순히 연결뿐만 아니라 데이터를 쪼개고, 보내고, 확인하는 전 과정을 포괄합니다.
- 커넥션 식별 (5-Tuple): 연결을 고유하게 식별하기 위해 다음 5가지 정보가 일치해야 합니다.
    - 출발지 IP / 출발지 포트 번호 / 목적지 IP / 목적지 포트 번호 /프로토콜 (TCP)
- 주요 장점:
    - 신뢰성(Reliable): 데이터 유실 시 재전송을 요청합니다.
    - 순서 보장: 데이터가 뒤섞여 도착해도 원래 순서대로 정렬합니다.
    - 제어 기능: 흐름 제어(받는 쪽 속도에 맞춤), 혼잡 제어(네트워크 상태에 맞춤)를 수행합니다.
    - 양방향 통신: 클라이언트와 서버가 동시에 데이터를 주고받을 수 있습니다.

<br><br>

## 📍 3-Way Handshake (연결 수립 과정)
- TCP의 특징인 '신뢰성'을 확보하기 위해, 실제 데이터를 보내기 전 상대방과 통신 준비가 되었는지 세 번 주고받으며 확인하는 구체적인 단계입니다.

### 진행 절차
1. [SYN] 단계: 클라이언트가 서버에게 "통신 시작할까? 내 번호는 이거야(Sequence Number)"라고 요청을 보냅니다.
2. [SYN + ACK] 단계: 서버가 요청을 확인하고 "응, 나도 준비됐어! 내 번호는 이거고, 네 번호 잘 받았어(Acknowledgment Number)"라고 답합니다.
3. [ACK] 단계: 클라이언트가 마지막으로 "확인 완료! 이제 진짜 데이터 보낼게"라고 답하며 연결이 최종 성립됩니다.

### 특징 및 단점
- 지연 시간(Latency): 데이터를 보내기도 전에 최소 3번의 왕복이 필요하여 물리적인 시간이 소요됩니다.
- 보안 취약점 (SYN Flooding): 공격자가 SYN만 계속 보내고 마지막 ACK를 보내지 않아 서버의 연결 리소스를 꽉 채워버리는 공격에 노출될 수 있습니다.

<br><br>

## 📍 포웨이 핸드셰이킹은 언제 사용되며, 그 과정에서 발생할 수 있는 문제점은 무엇인가요?
- 4-way-handshake은 연결종료시에 사용된다.
1. `FIN`: 클라이언트가 이제 보낼거 다 보냄
2. `ACK`: 서버가 일단 확인했고, 이제 보낼 게 남았으니 조금 기다리라는 Half-Close상태가 됨
3. `FIN`: 서버가 남은 데이터를 다 보내고 끊자는 의미로 보냄
4. `ACK`: 클라이언트도 확인함
- `TIME_WAIT`이라고 클라이언트가 마지막 ACK을 보내고 지연패킷이 올 수 있으므로 기다리게 되면서 잠깐 점유하게 된다. (자원낭비)
- 보통 2분 정도 기다림

<br>

### `TIME_WAIT` 상태가 필요한 이유
- 클라이언트가 서버에게 `ACK`을 보냈지만 중간에 유실되면 서버는 계속 `FIN`을 보내게 된다. `TIME_WAIT`상태 덕분에 `ACK`이 안갔다는 사실을 알 수 있게된다.
- `ACK`을 보내고 닫았다가 나중에 새 연결을 할 때 떠돌던 패킷이 갑자기 도착해버려서 데이터가 오염됨
  
<br><br>

## 📍 TCP와 UDP의 차이점은 무엇인가요?
- `UDP`는 데이터를 그냥 던지고, 확인하지 않아 빠르다.(게임, 스트리밍등)
- `TCP`는 3way-handshake연결을 통해 안전한 연결을 하고, 순서가 바뀌거나 누락되면 다시 보내게된다. (이메일, 파일전송 등)

<br><br>

## 📍 HTTP 1.0의 Keep-Alive와 HTTP 1.1의 지속 커넥션의 차이점은 무엇인가요?
- `HTTP 1.0`의 `Keep-Alive`는 비표준 기능이라서 `멍청한 프록시`가 헤더를 서버에 전달만 하고 자신은 연결을 끊어서 통신이 잘 되지 않았다.
- `HTTP 1.1`에서는 지속 커넥션을 규약으로 표준화해서 전체 구간에서 지속적으로 연결을 유지하게 만들었다.

<br><br>

## 📍 HOL(Head-of-Line Blocking)
- 앞에있는 네트워크 패킷이 전송되지 못하고 있어서 뒤에 있는 패킷들 전체의 전송이 지연되는 것

### HTTP/1.1
- HTTP/1.1은 기본적으로 요청 하나를 보내면 응답이 올 때까지 기다려야 했습니다. 이를 극복하려고 `파이프라이닝(Pipelining)`을 도입했죠.
    - 파이프라이닝: 응답을 기다리지 않고, 요청을 여러개 연속으로 보내는 것
    - 요청은 한번에 여러개 보내도, 서버는 받은 순서대로 응답해야되니까, 첫 번째 데이터가 너무 크면 뒤에 가벼운 데이터들까지 느려지는 `HTTP`레벨의 HOL 발생

### HTTP/2
- HTTP레벨의 HOL 문제를 해결하기 위해 데이터를 작은 프레임 단위로 쪼개서 무작위로 보냄(멀티플렉싱 `Multiplexing`)
- 하지만 TCP 프로토콜로 전송하다보니, 1번의 조각이 모두 다 올때까지 2번이후로부터 다 기다리는 `TCP 레벨의 HOL`에 걸림

### HTTP/3
- 위의`TCP 레벨의 HOL`을 해결하기 위해 구글에서 QUIC이라는 UDP기반으로 여러개의 데이터를 보낼 때 독립적인 통로로 보내서 통과하면 브라우저(애플리케이션)으로 올리고, 빠진 데이터는 다시 요청하게 된다.

<br><br>

## 📍 TCP의 성능과 관련된 주요 이슈와 이를 해결하기 위한 알고리즘에는 어떤 것들이 있나요?
### 성능이슈
1. 지연 시간: 데이터를 보내기 전에 3-way-handshake를 해야하므로 연결하는데 오래걸린다.
2. 혼잡: 네트워크 통로가 좁아지면 패킷을 잃어버리고, 재전송하면서 속도가 느려진다.
3. HOL Blocking: 패킷 하나가 유실되면 그 다음 패킷들이 잘 도착해도 애플리케이션에 전달되지 못하고 기다려야한다.

<br>


### 혼잡제어
- 네트워크 내에 전송하는 패킷의 수가 너무 많아져서 전송속도가 늦어지는걸 막음
- Slow Start: 처음에는 조금씩 보내면서 성공의 의미로 ACK을 받으면 2배씩 늘리면서 보냄

### 흐름제어 
- 송신자와 수신자의 메모리나 버퍼의 사이즈를 보고 여유가 있는지 없는지를 보고 윈도우 사이즈를 조절하거나, ack이 올때까지 무조건 멈춰서 기다림

<br><br>

## 📍 슬로우 스타트와 네이글 알고리즘의 차이점은 무엇인가요?
### 슬로우 스타트
- 한 번에 많이 보내서 네트워크가 터지는, 네트워크 혼잡 방지를 목적으로 한다.
- 초기 전송속도가 느리다.

### 네이글 알고리즘
- 작은 패킷을 너무 많이 보내서 대역폭이 낭비되는, 네트워크 대역폭 낭비를 방지하는 목적으로 한다.
- ACK을 기다리는데 지연시간이 발생한다.
