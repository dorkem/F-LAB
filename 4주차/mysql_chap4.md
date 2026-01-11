## 4.2.3 MVCC(Multi Version Concurrency Control

### 쓰는 이유
- 락 기반의 방식은 데이터를 읽던 쓰던간에 Read하기가 어렵지만 MVCC는 그걸 기다리지말고, undo 로그를 이용해 논리적으로 복사본을 보는 것

