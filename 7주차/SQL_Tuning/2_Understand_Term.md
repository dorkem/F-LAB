## 데이터를 가져오는 순서
- MySQL엔진으로부터 시작
### 1. 파서
- 사용자가 요청한 SQL문을 쪼개 트리로 만든다.
- 문법검사를 수행한다.

```sql
SELECT name, price FROM menu WHERE price > 10000;
```
- Root: `SELECT`
  - Child1: `name`, `price`
  - Child2: `menu`
  - Child3: `>`
    - Left: `price`
    - Right: `10000`

### 2. 전처리기
- 파서에서 만든 트리를 바탕으로 SQL 문장에 `오타와 구조적인 문제`를 찾음
- `DB 접근권한` 확인
- from 절에 테이블 및 뷰가 실제로 있는 오브젝트인지 확인

### 3. 옵티마이저
- 파서에서 검사가 끝난 쿼리를 가지고 사용자가 요청한 데이터를 빠르고 효율적으로 찾아갈 수 있도록 `실행 계획`을 세운다.

### 4. 익스큐터
- MySQL엔진과 스토리지 엔진에 걸터있는 오브젝트
- 옵티마이저에서 만든 실행계획을 바탕으로 스토리지 엔진에서 `데이터를 가져온다.`

### 5. 스토리지 엔진
- 실행계획을 바탕으로 실제로 데이터를 가져오는 물리적인 작업을 수행한다.
- OTLP 환경으로 `ACID`, `Row Lock`, `MVCC`(읽기는 쓰기를 기다리지 않음)등을 지원하는 `InnoDB`엔진을 보통 이용한다.
- ```sql
  select engine, transactions, comment from information_Schema.engines;

### 6. 결과전달
- 최종적으로 MySQL엔진에서 데이터를 조인하거나 필터링해서 데이터를 사용자에게 전달한다.

