## 4.2.3 MVCC(Multi Version Concurrency Control

### 쓰는 이유
- 락 기반의 방식은 데이터를 읽던 쓰던간에 Read하기가 어렵지만 MVCC는 그걸 기다리지말고, undo 로그를 이용해 논리적으로 복사본을 보는 것

### Undo Log
- 변경(UPDATE / INSERT / DELETE)이 일어나면 변경 전의 내용을 Undo 로그, 변경 이후에 내용을 Redo 로그에 기록한다.
  - 장애 발생시 커밋 된 내용은 Redo 다시 쓰고, 미커밋된 내용을 Undo를 보고 다시 쓴다.

### 잠금 없는 일관된 읽기(Non-Locking Consistent Read)
- InnoDB 스토리지 엔진은 MVCC 기술을 이용해 잠금을 걸지 않고 읽기 작업을 수행한다.
- 특정 사용자가 레코드를 변경하고 아직 커밋을 수행하지 않았다고 하더라도, 이 변경 트랜잭션이 다른 사용자의 SELECT 작업을 방해하지 않는 것을 잠금 없는 일관된 읽기라고 표현한다.
  - 격리 수준에 따라서 다른데 `READ_UNCOMMITTED`인 경우 변경된 값을 바로 보고, `READ_COMMITTED`, `REPEATABLE_READ`인 경우에는 UNDO로그를 통해 본다.
- 빨리 트랜잭션을 종료해야 Undo로그가 쌓인다는 단점을 줄일 수 있다.
