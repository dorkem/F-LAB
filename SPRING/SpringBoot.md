# 📋 목차

### 자바
- [🍵자바](https://github.com/dorkem/F-LAB/blob/main/JAVA/java.md)
- [🧊OOP](https://github.com/dorkem/F-LAB/blob/main/JAVA/OOP.md)

### 스프링
- [🍃디스패처 서블릿&리졸버](https://github.com/dorkem/F-LAB/blob/main/SPRING/DispatcherServlet.md)
- [🌿스프링부트](https://github.com/dorkem/F-LAB/blob/main/SPRING/SpringBoot.md)

### DB
- [🦅RealMySQL](https://github.com/dorkem/F-LAB/blob/main/DB/RealMySQL.md)
- [🔒Lock](https://github.com/dorkem/F-LAB/blob/main/DB/Lock.md)
- [🐬MySql](https://github.com/dorkem/F-LAB/blob/main/DB/MySQL.md)
- [💰Cache](https://github.com/dorkem/F-LAB/blob/main/DB/Cache.md)
- [🍂SpringJPA](https://github.com/dorkem/F-LAB/blob/main/DB/SpringJPA.md)

### Web & 네트워크
- [🕸️HTTP The Definitive Guide](https://github.com/dorkem/F-LAB/blob/main/NETWORK/HTTP-The-Definitive-Guide.md)
- [🤝🏻TCP](https://github.com/dorkem/F-LAB/blob/main/NETWORK/TCP.md)
- [🗝️AUTH](https://github.com/dorkem/F-LAB/blob/main/NETWORK/Login.md)

### DevOps
- [🐈‍⬛DevOps](https://github.com/dorkem/F-LAB/blob/main/CICD/DevOps.md)

<br><br><br>

# 🌿스프링부트
## 📍 Spring Bean Scope (빈 생명주기)와 상태 데이터 관리
- 스프링 컨테이너는 `빈(Bean)`이 생성되고, 존재하고, 소멸하는 주기인 `스코프(Scope)`를 관리한다.
- `@Controller`, `@Service`, `@Repository` 어노테이션은 내부에 `@Component`를 포함하고 있어 자동으로 스프링 빈으로 등록되며, `@Scope` 어노테이션을 통해 주기를 설정할 수 있다.

### 싱글톤(Singleton-기본값)
- 스프링 컨테이너 내에 단 하나의 객체만 생성되서 공유되는 가장 기본적인 스코프.
- 특징: 스프링 서버가 켜질 때 생성되고, 서버가 종료될 때 제거된다.
- 작동 방식: `@Autowired`를 통한 의존성 주입이 애플리케이션 구동 시 단 한 번만 일어난다. 이후 들어오는 모든 클라이언트의 요청은 이 동일한 객체를 재사용한다.
    - 지금은 `RequiredArgsConstructor`를 사용함
- 용도: 상태를 가지지 않는(Stateless) 공유 로직을 처리하는 Service, Repository 등에 적합하다.

### 프로토타입 (Prototype)
- 컨테이너에 요청할 때마다 항상 새로운 객체를 생성해서 반환하는 스코프.
- 특징: 스프링 컨테이너는 프로토타입 빈의 생성과 의존성 주입까지만 관여하고, 그 이후에는 관리하지 않는다.
- 동작 방식: 의존성 주입을 받거나 getBean()으로 조회할 때마다 메모리(Heap)에 완전히 새로운 인스턴스가 할당된다.
- 용도: 요청마다 독립적인 상태(데이터)를 유지해야 하는 복잡한 연산 객체 등에 사용된다.


### 3. 웹 관련 스코프 (Web Scopes)
스프링 웹(Spring Web) 환경에서만 동작하는 특수한 스코프들이다.
- `request 스코프`
  - 하나의 HTTP 요청(Request)이 들어오고 나갈 때까지 유지되는 스코프.
  - 동작 방식: A 사용자가 API를 요청하면 A 전용 객체가 생성되고, 응답이 완료되면 즉시 소멸한다. 동시에 B 사용자가 요청하면 B 전용 객체가 따로 생성된다.
  - 용도: 각 HTTP 요청마다 남겨야 하는 개별 로그(UUID 등) 처리나, 단일 요청 내에서 사용자 정보를 유지할 때 사용한다.

- `session 스코프`
  - HTTP 세션(Session)과 동일한 생명주기를 가지는 스코프.
  - 동작 방식: 사용자가 브라우저를 통해 접속하여 세션이 생성될 때 객체가 만들어지고, 로그아웃하거나 세션이 만료될 때 소멸한다.
  - 용도: 사용자별 장바구니, 로그인한 유저의 고유 설정 정보 등을 서버 메모리에 유지할 때 사용한다.

### 스코프 혼용 시 발생하는 치명적 문제와 해결책
- 스프링 개발 시, 생명주기가 긴 빈(싱글톤) 안에 생명주기가 짧은 빈(프로토타입, 웹 스코프)을 주입하려 할 때 구조적인 문제가 발생합니다.

1. **Request 스코프 주입시 서버 구동 실패**
      -  싱글톤 객체는 스프링 서버가 구동되는 시점에 생성되며, 이때 내부에 필요한 의존성(UserContext)도 함께 주입받으려 시도합니다.
      -  하지만 request 스코프 객체는 실제 클라이언트의 HTTP 요청이 들어와야만 생성됩니다.
      -  서버가 켜지는 시점에는 아직 아무런 HTTP 요청이 없으므로 주입할 빈을 찾지 못해 애플리케이션 구동이 실패합니다.

2. **단일 객체로 고정되어 버리는 문제 (Prototype 스코프 주입 시)**
      - 프로토타입 빈을 싱글톤에 주입할 경우 서버 구동은 성공합니다.
      - 스프링이 구동 시점에 프로토타입 빈을 하나 새로 만들어서 싱글톤에 주입해주기 때문
      - 하지만 싱글톤은 구동 시점에 딱 한 번만 주입을 받기 때문에, 이후 사용자들이 아무리 서비스를 호출해도 내부에 주입된 프로토타입 객체는 새로 생성되지 않는다.
      - 결국 매번 새로운 객체를 쓰려는 본래 의도와 다르게, 모든 사용자가 하나의 객체(최초 주입된 객체)를 공유하게 된다.

<br><br>

## 📍 Request 스코프와 ThreadLocal

---

### 1. 왜 필요한가

로그인 구현 후 모든 비즈니스 로직에 userId가 필요해졌다고 가정하자.

```
Controller → Service → Repository
  userId      userId     userId
```

모든 메서드에 userId를 파라미터로 계속 넘겨야 한다. 이걸 **파라미터 릴레이**라고 한다. 코드가 지저분해지고 메서드 추가할 때마다 파라미터도 계속 추가해야 한다.

이걸 끊어내기 위해 **UserContext** 라는 클래스를 만들고 거기에 userId를 저장해둔다.

```java
@Component
@Scope("request")   // ← 핵심, 요청 1개당 객체 1개 생성
public class UserContext {
    private Long userId;
}
```

이러면 파라미터 없이 어디서든 꺼내 쓸 수 있다.

```java
// 파라미터 릴레이 방식 (나쁜 것)
public Order createOrder(Long userId, ...) { ... }

// UserContext 방식 (좋은 것)
public Order createOrder(...) {
    Long userId = userContext.getUserId();   // 그냥 꺼내씀
}
```

---

### 2. ThreadLocal — 어떻게 사용자별로 안 섞이나

유저 1000명이 동시에 요청을 보내면 UserContext도 1000개가 생긴다.

그럼 유저A의 UserContext랑 유저B의 UserContext가 섞이지 않을까?

**안 섞이는 이유가 ThreadLocal이다.**

톰캣은 요청이 들어올 때마다 **스레드를 1개씩 배정**한다.

```
유저A 요청 → 스레드1 배정
유저B 요청 → 스레드2 배정
유저C 요청 → 스레드3 배정
```

ThreadLocal은 **스레드마다 따로 존재하는 개인 금고**다.

```
스레드1의 금고: { userId: 1 }   ← 유저A 정보
스레드2의 금고: { userId: 2 }   ← 유저B 정보
스레드3의 금고: { userId: 3 }   ← 유저C 정보
```

스레드1은 자기 금고만 열 수 있으니 절대 섞이지 않는다.

---

### 3. 요청 하나의 생명주기

```
클라이언트 요청 들어옴
    ↓
톰캣이 스레드 배정
    ↓
DispatcherServlet이 요청을 ThreadLocal에 묶음 (바인딩)
    ↓
Interceptor - preHandle 실행
JWT 토큰 검증 후 UserContext에 userId 저장
    ↓
Controller → Service → Repository 실행
userContext.getUserId() 로 꺼내씀
    ↓
응답 완료
    ↓
DispatcherServlet이 ThreadLocal 완전히 비움
스레드를 다시 스레드 풀에 반환
```

UserContext는 **요청이 시작될 때 생기고, 응답이 끝나면 바로 사라진다.**

---

### 4. 스레드가 분리되면 터진다

**REQUIRES_NEW (트랜잭션 분리)**

트랜잭션이 실행 중일 때 또 다른 트랜잭션을 호출하면 어떻게 할지 정하는 옵션이다.

```
트랜잭션A 실행 중
    ↓
REQUIRES_NEW로 트랜잭션B 호출
    ↓
트랜잭션A 잠시 멈춤
트랜잭션B 독립적으로 실행
    ↓
B가 실패해서 롤백돼도 A는 멀쩡히 계속 진행
```

주로 에러 로그 기록처럼 **메인 로직 실패와 상관없이 무조건 저장해야 할 때** 사용한다.

REQUIRES_NEW는 스레드를 새로 만들지 않는다. **같은 스레드에서 실행**되므로 ThreadLocal 금고를 그대로 쓸 수 있다. 문제없다.

---

**진짜 문제는 @Async (비동기)**

```
스레드1 (유저A 요청 처리 중)
    ↓
@Async 메서드 호출
    ↓
스레드2 (새로 생성됨)
    ↓
스레드2가 userContext.getUserId() 호출
    ↓
💥 ScopeNotActiveException 에러
```

왜 터지냐면:

```
스레드1의 금고: { userId: 1 }   ← 유저A 정보 있음
스레드2의 금고: {  }            ← 텅 비어있음
```

스레드2는 HTTP 요청을 받은 스레드가 아니라 새로 생긴 스레드다. 금고가 비어있으니 꺼낼 게 없어서 즉시 에러가 난다.

---

### 한눈에 정리

```
UserContext     = userId를 저장해두는 바구니
@Scope("request") = 요청 1개당 바구니 1개 생성
ThreadLocal     = 스레드마다 따로 있는 개인 금고
                  유저별로 데이터가 절대 안 섞이는 이유

REQUIRES_NEW    = 같은 스레드, 금고 공유 → 문제없음
@Async          = 새 스레드 생성, 금고 비어있음 → 즉시 에러
```

**Request 스코프는 철저하게 하나의 스레드에 의존한다. 새 스레드가 생기는 순간 유저 정보가 없어서 터진다.**

