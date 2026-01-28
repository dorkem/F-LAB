## TCP의 3-way 핸드셰이킹 과정이 왜 필요한지 설명해보세요.
- TCP의 `3-way Handshaking`은 우선 양방향 통신이 가능한지 여부를 확인해준다.
- 또한 순서보장을 하지 않는 UDP와 다르게, TCP는 데이터를 IP 패킷에 순서 번호를 붙여 스트림으로 관리하기 때문에 1, 2, 3번이 도착한 다음에 5번이 도착하면 4번이 유실된 것이므로 4번을 요청하게 된다. 이 때 누락된 4번을 재요청하고 순서가 맞을 때 까지 뒤 패킷을 대기시킨다(HOL)

<br><br>

## 슬로우 스타트(Slow Start)와 네이글 알고리즘(Nagle Algorithm)의 차이와 각각의 장단점을 설명해보세요.
- `Slow Start`는 TCP 혼잡 제어의 한 방식으로, 네트워크 상태를 알 수 없는 연결 초기에 대량의 패킷을 한꺼번에 투입해 병목이 발생하는 것을 막기 위해 전송량을 점진적으로 증가시키는 알고리즘이다.
  - ex) 처음 수도관을 연결할 때 바로 쎄게 틀지말고 점점 세게 트는 것
- `Nagle 알고리즘`은 애플리케이션 계층의 비효율적인 송신 패턴을 보완하기 위한 기법으로, 네트워크 혼잡 여부와는 무관하게 불필요하게 작은 패킷을 쪼개 전송함으로써 발생하는 트래픽 오버헤드를 줄이는 데 목적이 있다.
  - ex) 편지 하나 보내는데 택배 트럭을 매번 부르지 말자

<br><br>

## 자바 스트림(Stream)의 특징과 주의해야 할 점을 설명해보세요.

## 트랜잭션 스크립트 패턴이 무엇인지, 그리고 언제 사용하는 것이 적합한지 설명해보세요.

## JPA에서 OneToMany 관계를 사용하지 않고 ID 기반으로 설계할 때의 차이점과 그 이유를 설명해보세요.

## ORM이 해결하고자 하는 패러다임 불일치란 무엇인지 설명해보세요.

## 클러스터드 인덱스(Clustered Index)의 구조와 장단점, 그리고 삭제/삽입 시 성능에 미치는 영향에 대해 설명해보세요.

## 트랜잭션의 ACID 네 가지 속성을 실제 예시와 함께 설명해보세요.

## 엔티티 설계 시 조회용(Read)과 명령용(Command) 엔티티를 분리하는 이유와 그 설계상의 고려사항을 설명해보세요.



TCP의 3-way 핸드셰이킹 과정이 신뢰성 보장에 어떻게 기여하는지, 그리고 이 과정이 생략될 경우 발생할 수 있는 네트워크 레벨의 문제를 설명하세요.    •  TCP와 UDP의 연결 성립 방식 차이와 각각의 장단점은 무엇인가요?    •  SYN Flooding 공격이 3-way 핸드셰이킹에 미치는 영향과 방어 기법은 무엇인가요?    •  TCP 연결의 상태 전이(State Transition)와 TIME_WAIT 상태의 의미를 설명하세요.    •  TCP의 시퀀스 넘버와 ACK 넘버가 재전송 및 패킷 손실 처리에 어떻게 활용되는지 설명하세요.    •  3-way 핸드셰이킹 과정에서 발생할 수 있는 Half-Open Connection 문제와 그 처리 방법을 설명하세요. Head-of-Line Blocking이 TCP와 HTTP/1.1, HTTP/2에서 각각 어떻게 발생하며, 이를 해결하기 위한 프로토콜 수준의 접근법은 무엇인가요?    •  HTTP/2의 멀티플렉싱이 Head-of-Line Blocking을 어떻게 완화하는지 설명하세요.    •  QUIC 프로토콜이 Head-of-Line Blocking 문제를 어떻게 해결하는지 기술하세요.    •  TCP 레벨에서 HOL Blocking이 발생하는 내부 메커니즘을 상세히 설명하세요.    •  HTTP/3에서 HOL Blocking 문제를 해결하는 구조적 차별점은 무엇인가요?    •  HTTP/2의 스트림 우선순위와 흐름 제어가 HOL Blocking에 미치는 영향은 무엇인가요? 자바 스트림(Stream) API의 레이지 평가와 재사용 불가 특성이 실제 대용량 데이터 처리에서 어떤 장단점을 갖는지 설명하세요.    •  스트림 파이프라인에서 중간 연산과 최종 연산의 차이와 실행 시점은 어떻게 결정되나요?    •  병렬 스트림(parallelStream()) 사용 시 발생할 수 있는 동시성 이슈와 성능 저하 원인은 무엇인가요?    •  스트림 사용 시 OutOfMemoryError가 발생할 수 있는 케이스와 그 원인은 무엇인가요?    •  스트림의 short-circuiting 연산이 전체 파이프라인에 미치는 영향은 무엇인가요?    •  스트림 API의 내부 반복자(Internal Iterator)와 외부 반복자(External Iterator)의 차이점은 무엇인가요? JPA에서 OneToMany 연관관계를 사용할 때 발생하는 N+1 문제의 원인과 이를 해결하기 위한 전략을 설명하세요.    •  FetchType.LAZY와 FetchType.EAGER의 차이와 각각의 장단점은 무엇인가요?    •  EntityGraph와 JPQL fetch join의 차이와 사용 시 주의점은 무엇인가요?    •  BatchSize 옵션이 N+1 문제 해결에 미치는 영향과 한계는 무엇인가요?    •  컬렉션을 Value Object로 분리할 때 N+1 문제에 어떤 영향을 주나요?    •  Hibernate의 2차 캐시가 N+1 문제에 미치는 영향은 무엇인가요? 트랜잭션의 ACID 속성 중 일관성(Consistency)과 격리성(Isolation)의 차이와, 각각이 실제 데이터베이스 동작에서 어떻게 보장되는지 설명하세요.    •  Serializable, Repeatable Read, Read Committed, Read Uncommitted 각 격리 수준의 동작 원리와 발생 가능한 현상은 무엇인가요?    •  Foreign Key, Unique, Check 제약조건이 일관성 보장에 어떻게 기여하는지 설명하세요.    •  Phantom Read, Non-repeatable Read, Dirty Read의 차이와 방지 방법은 무엇인가요?    •  MVCC(Multi-Version Concurrency Control)가 격리성과 일관성에 미치는 영향은 무엇인가요?    •  트랜잭션 로그와 Write-Ahead Logging이 ACID 보장에 어떻게 기여하는지 설명하세요.
