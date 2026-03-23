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
- [💰Cache](https://github.com/dorkem/F-LAB/blob/main/DB/Cache.md)

### Web & 네트워크
- [🕸️HTTP The Definitive Guide](https://github.com/dorkem/F-LAB/blob/main/NETWORK/HTTP-The-Definitive-Guide.md)
- [🤝🏻TCP](https://github.com/dorkem/F-LAB/blob/main/NETWORK/TCP.md)


<br><br><br>


# 💰Cache
## 📍Redis (Remote Dictionary Server)
### 개념
- Key-Value 형태로 저장하고 관리하는 인메모리(RAM) 기반 전용 서버. (해시테이블 자료구조 사용)
### 자료구조
  - Strings(키-값)
  - Lists(Queue용)
  - Sets(중복 방지)
  - Sorted Sets(실시간 랭킹)
  - Hashes(필드-값)

### 특징
  1. 메모리 스냅샷 및 파일 추가(AOF) 방식으로 로컬 디스크에 백업.
  2. 싱글 스레드와 I/O 멀티플렉싱(이벤트 루프) 방식으로 동작하여 램 접근 결과를 매우 빠르게 처리.
  3. 일반 DB로 쓰기에는 관계 설정이 어렵고 비용이 비싸다는 단점이 있음.

### 메모리 캐시 vs 분산 캐시
- 메모리 캐시 (Local Cache): 주소 목록 등 잘 변하지 않는 정보 저장. 인프라 비용이 없고 네트워크를 타지 않아 매우 빠름(10~100 나노초). 단, Heap 영역 비대화로 인한 GC(Stop-the-world) 이슈 발생 가능.
- 분산 캐시 (Distributed Cache): 로그인 세션, 실시간 재고/랭킹 등 여러 서버가 동일한 데이터를 봐야 할 때 사용(데이터 불일치 해결).
- 확장성과 성능: Redis는 C언어로 개발되어 GC가 없고 확장이 용이함(0.1~2 밀리초). 다중 서버 환경에서 네트워크를 통한 로그 동기화 방식의 부하와 불일치 문제를 해결할 수 있음.
- 도입 기준: 매 요청마다 외부 저장소 조회가 병목이 될지 부하 테스트(CPU, 네트워크 대역폭 확인)를 거쳐 정합성과 처리 속도 간의 트레이드오프를 판단해야 함.
