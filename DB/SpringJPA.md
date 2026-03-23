# 목차

### 자바
- [🍵자바](https://github.com/dorkem/F-LAB/blob/main/JAVA/java.md)
- [🧊OOP](https://github.com/dorkem/F-LAB/blob/main/JAVA/OOP.md)

### 스프링
- [🍃디스패처 서블릿](https://github.com/dorkem/F-LAB/blob/main/SPRING/DispatcherServlet.md)

### DB
- [🦅RealMySQL](https://github.com/dorkem/F-LAB/blob/main/DB/RealMySQL.md)
- [🔒Lock](https://github.com/dorkem/F-LAB/blob/main/DB/lock.md)
- [🐬MySql](https://github.com/dorkem/F-LAB/blob/main/DB/MySQL.md)
- [🌱SpringJPA](https://github.com/dorkem/F-LAB/blob/main/DB/SpringJPA.md)

### 네트워크
- [🕸️HTTP The Definitive Guide](https://github.com/dorkem/F-LAB/blob/main/NETWORK/HTTP-The-Definitive-Guide.md)

<br><br><br>

# 🌱SpringJPA
## 📍 JPA에서 Enum 타입을 사용할 때 주의해야 할 점
- `EnumType.ORDINAL`을 쓰지 말것: 기본값으로 `Enum`의 순서(0, 1, 2...)를 DB에 저장합니다. 나중에 Enum 사이에 새로운 값이 추가되면 기존 데이터의 의미가 완전히 뒤섞여버리는 대참사가 일어납니다.
- `EnumType.STRING`을 써야함: `Enum`의 이름을 문자열 그대로 저장합니다. 데이터 크기는 조금 더 커지지만, 변경에 안전하고 가독성이 훨씬 좋습니다.

<br><br>

## JPA에서 연관관계의 주인이란?

## JPA에서 OneToMany 관계를 사용하지 않고 ID 기반으로 설계할 때의 차이점과 그 이유를 설명해보세요.

## ORM이 해결하고자 하는 패러다임 불일치란?
