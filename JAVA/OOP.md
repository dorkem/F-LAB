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

# 📜 객체 지향
## 📍 객체와 객체지향
### `객체`
- 클래스를 바탕으로 실제 메모리에 할당되어 자신만의 고유한 데이터를 갖고 동작하는 데이터 + 함수의 묶음
- `상태`: 이름, 나이, 키, 몸무게
- `행위`: 걷기, 뛰기, 말하기

<br>

### `객체지향`
- 역할, 책임, 협력
- [🔗 객체지향의 생활 체조 원칙](https://datatracker.ietf.org/doc/html/rfc6749)
- 역할(인터페이스)
    - 배달원이라는 추상적인 개념으로, 남자든 여자든 서울 출신이든 아니든, 배달만 잘 해주면 된다는 틀(행동 규약)만 정의되어 있으면 됨
- 책임(구현)
    - 오토바이를 타는 배달원인지, 자전거를 타는 배달원인지 각자가 맡은 `배달`이라는 역할을 자신만의 구체적인 방식으로 완수하는 것
- 협력(클래스간의 메서드 호출)
    - 손님은 주문을 하고, 식당은 요리하고 배달원을 호출하고, 배달원은 음식을 손님에게 배달을 잘 해주도록 서로의 메서드를 호출하면서 비즈니스를 완성해가는 과정
 
<br><br>
 
## 📍 SOLID
###  🎯 SOLID의 목표
- 응집도를 높이고(SRP), 불필요한 의존을 제거해 결합도를 낮추어(ISP, DIP), 다형성 위에서 안전하게(LSP) 코드를 유연하게 확장(OCP)하는 것이다.
- 객체는 고정된 데이터 덩어리가 아니라, 메시지(요청)를 주고받으며 시간에 따라 상태가 변하고 행동하는 능동적인 단위다.


### 응집도, 결합도, IoC, DI
- 응집도(Cohesion)와 결합도(Coupling)
    - `응집도` (높을수록 좋음): 클래스 내부의 필드와 메서드들이 얼마나 밀접하게 연관되어 하나의 목적을 이루는가의 척도.
    - `결합도` (낮을수록 좋음): 한쪽 코드가 변경될 때, 다른 쪽 코드가 얼마나 강하게 영향을 받아 뜯어고쳐야 하는가의 척도.
- IoC와 DI (스프링 프레임워크의 핵심)
    - `IoC` (제어의 역전): 개발자가 아니라 프레임워크가 객체의 생성부터 소멸까지의 흐름을 주도한다는 '설계 원칙 및 패러다임'.
    - `DI `(의존성 주입): IoC 원칙과 DIP(의존성 역전)를 달성하기 위한 구체적인 '구현 기술'. 클래스 내부에서 new로 직접 객체를 낳지 않고, 외부(스프링 컨테이너)에서 필요한 객체(인터페이스의 구현체)를 생성자 등을 통해 주입받아 결합도를 대폭 낮춘다.

### 1. SRP (단일 책임 원칙 - Single Responsibility Principle)
- **"클래스는 변경되어야 할 이유가 단 `하나`뿐이어야 한다."**
- 하나의 클래스가 '동일한 비즈니스 목적'을 가진 기능(책임)만 수행하도록 제한하여 `응집도를 극대화`한다.
- 기능 수정 시 다른 무관한 기능에 영향을 주는 `사이드 이펙트를 차단`한다. (예: 계산기 클래스에는 사칙연산 기능만 둔다.)

<br>

### 2. OCP (개방-폐쇄 원칙 - Open-Closed Principle)
- **"확장에는 열려 있고(Open), 기존 코드 수정에는 닫혀 있어야(Closed) 한다."**
- 구체적인 클래스가 아닌 `추상화`(인터페이스)에 `의존`해야 달성 가능하다.
- 운전자(클라이언트)가 '자동차' 인터페이스만 의존하면, 구체적인 차가 소나타에서 포르쉐로 바뀌어도 운전하는 기존 코드는 전혀 수정할 필요가 없다.

<br>

### 3. LSP (리스코프 치환 원칙 - Liskov Substitution Principle)
- **"자식 클래스는 언제나 부모 클래스를 온전히 대체할 수 있어야 한다."**
- 다형성이 안전하게 동작하기 위한 필수 전제 조건이다. 부모의 의도나 규약을 자식이 깨뜨리면 안 된다.
- List 타입으로 작성된 로직이 있을 때, 실제 구현체를 ArrayList에서 LinkedList로 갈아 끼워도 시스템은 에러 없이 정상 작동해야 한다.

<br>

### 4. ISP (인터페이스 분리 원칙 - Interface Segregation Principle)
- **"하나의 비대한 범용 인터페이스보다, 역할에 맞게 잘게 쪼갠 여러 개의 특화된 인터페이스가 낫다."**
- 클라이언트가 자신이 사용하지 않는 메서드에 강제로 의존하는 것을 막아 `결합도를 낮춘다.`
- '복합기' 인터페이스 하나를 두는 것보다 '프린터', '팩스', '스캐너' 인터페이스로 쪼개야, 구형 프린터 기계가 팩스 기능을 억지로 구현(비워두거나 예외 처리)하는 상황을 막을 수 있다.

<br>

### 5. DIP (의존성 역전 원칙 - Dependency Inversion Principle)
- **"구체적인 구현 클래스(세부 구현)가 아닌, 인터페이스(추상화)를 바라보고 의존해야 한다."**
- 변하기 쉬운 구체적인 것에 의존하지 않게 하여 시스템의 유연성을 높인다.

<br><br>

## 📍일급 컬렉션이란? 
