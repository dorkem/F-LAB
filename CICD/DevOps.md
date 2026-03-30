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

# 🐈‍⬛ DevOps

- [🔗 깃허브 브랜치 전략](https://inpa.tistory.com/entry/GIT-%E2%9A%A1%EF%B8%8F-github-flow-git-flow-%F0%9F%93%88-%EB%B8%8C%EB%9E%9C%EC%B9%98-%EC%A0%84%EB%9E%B5)
- [🔗 작은 PR](https://blog.banksalad.com/tech/banksalad-code-review-culture/)


## 📍 CI/CD란?

### 1. CI (Continuous Integration, 지속적 통합)
- 개발자들이 작성한 코드가 지속적으로 하나의 중심 브랜치에`통합(Merge)`되어야 한다는 의미이다.
- 절대 원칙: 지속적 통합에서 가장 중요한 규칙은 "`머지(Merge)`된 브랜치는 언제나 100% 당장 배포 가능한 상태여야 한다"는 것이다.
- 실무적 동작: 이 '배포 가능한 상태'를 보장하기 위해, 코드가 브랜치에 합쳐지기 직전에 자동으로 `빌드(Build)`해 보고 `테스트(Test)`를 한다.
- 아래의 GitHub Actions 스크립트에서 우분투 서버를 띄워 `npm run build`를 하고 `./gradlew build`를 수행했던 과정이 CI의 과정이다.

### 2. CD (Continuous Deployment / Delivery, 지속적 배포)
- 핵심 개념: CI를 통해 성공적으로 통합되고 안전함이 검증된 코드가 지속적으로 실제 서버(운영 환경)에 배포되어야 한다는 의미이다.
- 실무적 동작: 개발자가 직접 파일 질라(FileZilla)나 푸티(PuTTY)로 서버에 접속해서 파일을 옮기고 껐다 켜는 수동 작업을 없애는 것이다.
- 코드가 통합되는 즉시 자동으로 서버에 전송되고 실행되도록 파이프라인을 구축한다.
- 아래에 작성한 스크립트의 `Transfer JAR`와 `Execute Deployment Script`(`deploy.sh`) 단계가 바로 `CD`를 구현한 것

<br><br>

## 1. 📍 Git Flow 전략
- Git Flow는 체계적이고 큰 규모의 프로젝트에 적합한 전략입니다.

### 5가지 핵심 브랜치
- `master`: 라이브 서버에 배포되는 완성본 브랜치입니다.
- `develop`: 다음 배포를 위해 기능들을 모아두는 중심 브랜치입니다. (여기서 주로 작업이 시작되고 모입니다.)
- `feature`: 새로운 기능을 개발할 때 develop에서 파생되어 사용하는 브랜치입니다. 완료되면 다시 develop으로 합칩니다.
- `release`: 배포 직전 최종 테스트(QA) 및 버그 수정을 위한 브랜치입니다. develop에서 파생되며, 완료되면 master와 develop 모두에 합칩니다.
- `hotfix`: 이미 배포된 서버(master)에서 긴급한 버그가 발생했을 때 수정하는 브랜치입니다. 완료되면 master와 develop 모두에 합칩니다.

### 전체적인 흐름
- `develop`을 중심으로 기능 개발(feature)을 진행하고, 배포 준비가 되면 `release`에서 테스트 후 `master`로 배포합니다. 긴급 수정은 hotfix로 처리합니다.

<br><br>

## 2. 📍 GitHub Flow 전략
- GitHub Flow는 Git Flow보다 훨씬 단순하고, 빠르고 잦은 배포에 적합한 전략입니다. 자동화(CI/CD)를 전제로 합니다.

### 단일 핵심 브랜치
- master: 이 브랜치는 항상 배포 가능한 최신 상태여야 합니다. 이 브랜치 하나만 특별하게 관리됩니다.

### 전체적인 흐름
1. 브랜치 생성: 어떤 작업(기능 추가, 버그 수정 등)을 하든 무조건 master 브랜치에서 새로운 브랜치를 생성합니다. 이때 브랜치 이름은 작업 내용을 명확히 알 수 있게 지어야 합니다.
2. 개발 & 커밋 & 푸시: 개발을 진행하며 수시로 원격 저장소에 푸시합니다.
3. PR (Pull Request) 생성: 작업이 완료되거나 리뷰가 필요할 때 PR을 생성하여 팀원들에게 코드 병합을 요청합니다.
4. 리뷰 & 토의: 팀원들과 코드를 리뷰하고 피드백을 주고받습니다.
5. 테스트 & 최종 Merge: 테스트 서버에서 문제가 없는지 확인 후, master 브랜치로 병합(Merge)합니다.
6. 자동 배포: master 브랜치에 코드가 병합되면 CI/CD 도구에 의해 자동으로 실제 서버에 배포됩니다.

<br><br>

## 📍 배포 스크립트 학습내용 정리

### 1) 전반적인 배포 흐름
1. 내 PC에서 feature/step2 브랜치로 코드를 Push 하며 `훅`이 발생한다.
2. GitHub의 `러너`에서 코드를 `클론`받고, 프론트엔드와 백엔드 코드를 각각 `빌드`하면 `jar`(아티팩트)가 나온다.
3. `jar`를 내 EC2 서버로 `SCP`(Secure Copy Protocol)방식으로 전송한다.
  - `SSH`(Secure Shell) 통신망을 이용해 파일을 복사하는 방식
  - `host`, `username`, `key`(pem 키)를 이용해 `EC2`에 `SSH`로 접속한 뒤, 명시된 `source` 파일들을 target 폴더로 전송
4. Deploy (EC2 서버): EC2 서버에서 기존에 돌고 있던 앱을 끄고, 새로 받은 파일로 앱을 다시 켠다.
    1. 파이프(|)와 최신 JAR 파일 추출
       ```bash
       JAR_NAME=$(ls -tr $JAR_PATH | grep 'jar' | grep -v 'plain' | tail -n 1)
       
       1. ls -tr $JAR_PATH
         해당 폴더의 파일 목록을 출력하되, 시간 역순(-tr, 오래된 것부터 최신순)으로 정렬한다. (제일 아래에 최신 파일이 위치하게 됨)
       2. | grep 'jar'
         위 목록을 넘겨받아, 이름에 'jar'라는 단어가 포함된 파일만 걸러낸다.
       3. | grep -v 'plain'
         걸러진 목록을 다시 넘겨받아, 이름에 'plain'이 포함된 파일은 제외(-v)시킨다. (실행 불가능한 소스코드 껍데기인 xxx-plain.jar가 함께 생성되기 때문에 이를 빼주는 필수 작업)
       4. | tail -n 1
         최종적으로 남은 목록 중 맨 밑에 있는 1줄(-n 1)만 가져온다.
         1번에서 최신순으로 정렬했으므로,이 1줄이 바로 방금 빌드해서 전송한 가장 따끈따끈한 실행용 JAR 파일이 된다.
       ```
    2. PID (Process ID)
       ```bash
       CURRENT_PID=$(pgrep -f "food-0.0.1-SNAPSHOT.jar")
       kill -15 $CURRENT_PID

       - PID란: 리눅스 운영체제에서 현재 실행 중인 프로그램(프로세스)들에게 부여하는 고유한 주민등록번호입니다.
       - 컴퓨터를 껐다 켜거나 앱을 재실행할 때마다 이 번호는 무작위로 바뀝니다. 따라서 기존에 돌고 있던 구버전 앱을 끄려면,
       - 먼저 pgrep 명령어로 "이름에 food 어쩌고가 들어간 녀석의 주민번호를 찾아와!"라고 명령해야 합니다.
       - 찾아낸 주민번호(CURRENT_PID)를 향해 kill -15 (작업하던 것만 마저 끝내고 정상 종료해라) 신호를 보내 안전하게 기존 앱을 내립니다.
       ```
    3. 파일 디스크립터 (File Descriptor)와 백그라운드 실행
       ```bash
       nohup java -jar $JAR_FILE > $DEPLOY_PATH/app.log 2>&1 &
        
       리눅스에서 앱을 실행할 때 발생하는 '입출력'을 어디로 보낼지 결정하는 부분입니다. 리눅스는 모든 입출력을 파일 디스크립터라는 번호표로 관리합니다.
       - 기본 번호표
         - 0: 표준 입력(키보드 입력)
         - 1: 표준 출력(정상적인 로그나 화면 출력)
         - 2: 표준 에러(에러나 경고 메시지)
       - 명령어 해석
         - nohup ... &: 터미널 창을 닫아도 프로그램이 죽지 않고(nohup), 백그라운드에서 계속 돌게(&) 만듭니다.
         - > app.log: 1번(표준 출력) 나오는 정상 로그들을 화면에 보여주지 말고 app.log 파일에 덮어써서 저장하라는 뜻입니다.
         - 2>&1: 여기가 핵심입니다. "2번(표준 에러) 출력도 1번(표준 출력)이 가는 곳으로 똑같이 보내라"는 뜻입니다.
           즉, 정상 로그와 에러 로그를 구분하지 않고 모두 app.log 파일 하나에 몰아서 저장하겠다는 의미입니다.
       ``` 

### 2) GitHub Actions
```yml
name: Deploy to Review Branch

on:
  push:
    branches: [ "feature/step2" ] # feature/step2 브랜치에 코드가 push될 때만 이 스크립트가 실행됨

jobs:
  build-and-deploy:
    # 💡 [질문 1] ubuntu-latest와 Node 24의 의미
    # 내 EC2 서버와는 전혀 상관없습니다! 코드를 빌드하기 위해 GitHub이 잠깐 빌려주는 
    # '임시 컴퓨터(Runner)'의 운영체제를 최신 우분투로 쓰겠다는 뜻입니다.
    # Node 24 설정은 내 프론트엔드 코드 버전이 아니라, GitHub Actions 내부 시스템이 
    # 플러그인들을 실행할 때 구버전 Node를 쓰면 경고가 뜨기 때문에 강제로 최신 버전을 쓰게 만든 억제 옵션입니다.
    runs-on: ubuntu-latest
    env:
      FORCE_JAVASCRIPT_ACTIONS_TO_NODE24: true

    steps:
      # 💡 [질문 2] checkout
      # 네, 맞습니다! 빈 임시 컴퓨터에 내 GitHub 저장소의 최신 코드를 
      # 그대로 `git pull/clone` 해서 가져오는(체크아웃) 작업입니다.
      - name: checkout
        uses: actions/checkout@v4

      # 💡 [질문 3] Set Up JDK 17 (Corretto)
      # 내 로컬이 Oracle Java 17이더라도, Java 17이라는 표준 규격은 동일하므로 
      # 빌드하거나 실행하는 데 전혀 문제가 없습니다. Amazon Corretto는 아마존이 만든 
      # 무료/실무용 표준 Java 배포판일 뿐입니다.
      - name: Set Up JDK17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'corretto'

      - name: Build Frontend
        run: |
          cd frontend
          npm install
          npm run build # 리액트/뷰 등의 프론트엔드 코드를 압축해서 HTML/JS/CSS 정적 파일로 만듦

      # 💡 [질문 4] chmod와 gradlew
      # chmod +x ./gradlew : gradlew(그래들 빌드 도구) 파일에 '실행(x) 권한'을 부여합니다.
      # ./gradlew clean build -x test : 기존에 빌드된 찌꺼기 파일들을 지우고(clean), 
      # 새로 백엔드 코드를 빌드하여 JAR 파일로 압축합니다. (시간 단축을 위해 test는 건너뜀)
      - name: Build with Gradle
        run: |
          chmod +x ./gradlew
          ./gradlew clean build -x test

      # 💡 [질문 5] 왜 또 EC2 폴더로 가져오는가? (Transfer)
      # 지금까지의 과정은 내 EC2가 아니라 'GitHub의 임시 컴퓨터' 안에서 일어난 일입니다!
      # 방금 임시 컴퓨터에서 뚝딱뚝딱 만들어낸 최종 결과물(JAR 파일)을 
      # 이제 진짜로 서비스가 구동될 내 EC2 서버로 보내주는 과정(scp)입니다.
      - name: Transfer JAR
        uses: appleboy/scp-action@v1
        with:
          host: ${{ secrets.SERVER_HOST }}
          username: ${{ secrets.SERVER_USER }}
          key: ${{ secrets.SERVER_KEY }}
          source: "build/libs/*.jar"
          target: "/home/ec2-user/woowahan-delivery"
          strip_components: 2 # build/libs/ 폴더 구조는 떼어버리고 내용물만 타겟 폴더에 넣음

      - name: Transfer Frontend
        uses: appleboy/scp-action@v1
        with:
          host: ${{ secrets.SERVER_HOST }} # 이하 동일
          # ... (생략) ...
          source: "frontend/dist/**"

      # 전달이 다 끝났으면, EC2 서버에 원격 접속(ssh)해서 배포 실행 스크립트(deploy.sh)를 작동시킵니다.
      - name: Execute Deployment Script
        uses: appleboy/ssh-action@v1
        with:
          script: |
            cd /home/ec2-user/woowahan-delivery
            chmod +x deploy.sh
            ./deploy.sh ${{ secrets.SERVER_HOST }}
```

### 3) deploy.sh
```Bash
#!/bin/bash

# 1. 경로 및 변수 설정
APP_NAME="woowahan-delivery"
DEPLOY_PATH="/home/ec2-user/woowahan-delivery"
JAR_PATH="$DEPLOY_PATH"
CONFIG_PATH="$DEPLOY_PATH/config/application-dev.yml"

SERVER_IP=$1 # GitHub Actions에서 넘겨준 파라미터(SERVER_HOST)를 받음

# 2. 방금 전송받은 따끈따끈한 새 JAR 파일 찾기
# (plain이 들어간 파일은 용량이 작은 껍데기 파일이라 제외하고, 시간순 정렬해서 제일 최신 파일 1개만 고름)
JAR_NAME=$(ls -tr $JAR_PATH | grep 'jar' | grep -v 'plain' | tail -n 1)
JAR_FILE="$JAR_PATH/$JAR_NAME"

echo "========== 배포 시작==========="
echo "> 배포 파일: $JAR_NAME"

# 3. 현재 EC2에서 돌아가고 있는 배달 앱의 프로세스 ID(PID) 찾기
CURRENT_PID=$(pgrep -f "food-0.0.1-SNAPSHOT.jar")

# 4. 앱 종료 로직 (Graceful Shutdown)
if [ -z "$CURRENT_PID" ]; then
        # 켜져 있는 앱이 없으면 패스
        echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
        # 켜져 있으면 kill -15를 통해 하던 작업을 마저 끝내고 안전하게 종료하도록 신호를 보냄
        echo "> 실행 중인 애플리케이션 종료 (PID: $CURRENT_PID)"
        kill -15 $CURRENT_PID
        sleep 5 # 완전히 꺼질 때까지 5초 대기
fi

echo "> 새 애플리케이션 배포 및 실행"

# 5. 새 앱 무중단(백그라운드) 실행
# nohup ~ & : 터미널 접속을 끊어도 서버가 죽지 않고 계속 백그라운드에서 돌게 만듦
# -D 옵션들 : 앱을 실행할 때 주입하는 외부 환경변수들 (설정 파일 위치, dev 프로필 사용, 카카오 소셜 로그인 리다이렉트 URI 등)
# > app.log 2>&1 : 실행하면서 나오는 로그(에러 포함)를 app.log 파일에 저장함
nohup java -jar \
-Dspring.config.location=file:$CONFIG_PATH \
-Dspring.profiles.active=dev \
-Dkakao.redirect-uri=http://52.79.117.157/oauth/kakao/callback \
$JAR_FILE > $DEPLOY_PATH/app.log 2>&1 &

echo "========== 배포 완료 =========="
```

### `Kill` 4대장
- `-15` (SIGTERM)
  - 아무 숫자 없이 그냥 kill 1234라고 치면 리눅스는 자동으로 이 `-15` 신호를 보낸다.
  - 자바 앱이 `DB` 연결을 끊고, 쓰던 파일을 닫을 수 있도록 마무리할 시간(Graceful Shutdown)을 주는 종료방식이다.
- `-9` (SIGKILL)
  - `-15`를 보냈는데도 프로그램이 버그에 걸려서 뻗었거나 말을 안 들을 때 쓰는 강제 종료 신호이다.
  - OS 커널이 자비 없이 즉시 날려버리므로 메모리 정리를 못 하고 죽는다.
- `-2` (SIGINT) (Ctrl + C)
  - 우리가 터미널에서 로그를 보거나 앱을 돌리다가 `Ctrl + C`를 눌러서 강제로 빠져나올 때, 컴퓨터 내부적으로 해당 프로세스에 날아가는 신호가 바로 이 `-2`이다.
- `-1` (SIGHUP): "설정 파일만 새로고침(Reload)"
  - 아까 nohup(Hang Up 무시)을 배울 때 나왔던 그 HUP(Hang Up) 신호이다.
  - 원래는 '터미널 접속 끊김'을 알리는 신호이지만, Nginx나 웹 서버 데몬들에게 이 신호를 보내면 아주 특별하게 동작한다.
  - 서버를 껐다 켜면 접속한 유저들이 튕기니까, 서버는 켜둔 상태로 설정 파일만 새로고침하는 "무중단 리로드"라는 뜻으로 자주쓰인다. (예: kill -1 Nginx_PID)
 
<br><br>

## 📍 Nginx, 로드벨런서
### 웹 요청 동작 원리
- 브라우저에 `http://google.com`를 입력하면 브라우저가 서버에게 `index.html`달라고 요청하면 구글 서버가 `HTML` 파일을 응답으로 돌려주는 것
    - 서버는 이게 끝임

### 서버가 1대라면?
- 구글 하루에 접속자가 수천만이 넘어갈텐데, 서버 한 대로 요청을 처리하기에 어렵기 때문에 서버를 여러대 운영한다.
- 서버도 컴퓨터라서 `IP`가 각각 있는데, 클라이언트가 여기에 접속하기 위해 각각의 IP를 알 수도 없고, 알 필요도 없다. 이 문제를 해결하는게 `로드벨런서`이다.

### 로드벨런서
- 클라이언트 요청을 받아서 여러 서버 중 하나로 전달해주는 서버이다.
- 클라이언트는 다른 서버의 갯수나 각 IP는 뭔지 몰라도 되고, 로드벨런서 IP 하나만 알면 된다.
- 로드 밸런서가 요청을 받을 때 A서버가 바쁘면 B서버로 보내는 역할을 함

<br><br>

## 📍 프록시
- 중간에서 요청/응답을 대신 전달해주는 서버

### 포워드 프록시
- 클라이언트 앞에 붙어서 서버는 클라이언트가 누군지 모르고, 프록시에서 요청이 오는 것 처럼 보인다.
- 회사 내부 네트워크에서 나가는 모든 요청을 포워드 프록시를 통해서만 나가게 해서 특정 사이트를 차단함

### 리버스 프록시 
- 서버 앞에 붙어서 클라이언트는 프록시 뒷단의 서버 정보를 모름
- 그래서 여기에 로드벨런서 IP를 등록하면, 리버스프록시가 요청을 받아서 내부 서버로 전달하고, 로드벨런서가 요청을 분배한다.

### Nginx
- 리버스 프록시 기능을 구현한 소프트웨어
- EC2에 설치하면 그 EC2가 HTTP 요청을 받아서 처리할 수 있게 해줌

<br><br>

## 📍 암호화/복호화, SSL

---

### 암호화/복호화가 뭐야

편지를 보낸다고 생각해보자.

```
일반 편지 (HTTP)
"나 비밀번호는 1234야"
→ 중간에 누가 가로채면 그냥 다 읽힘
```

그래서 암호화가 필요하다.

```
암호화 = 내용을 알아볼 수 없게 바꾸는 것
복호화 = 암호화된 내용을 다시 원래대로 푸는 것
```

예시로 보면:

```
원문:    "비밀번호는 1234"
암호화:  "Xk92!@#$Lm34Qp"   ← 중간에 가로채도 못 읽음
복호화:  "비밀번호는 1234"   ← 받는 사람만 다시 풀 수 있음
```

---

### 그럼 어떻게 암호화/복호화를 해?

**열쇠(Key)** 가 필요하다.

```
암호화할 때 열쇠로 잠금
복호화할 때 열쇠로 풀음
```

근데 문제가 있다.

```
서버가 열쇠를 클라이언트한테 보내야하는데
그 열쇠를 보내는 중간에 누가 가로채면?
열쇠를 알았으니 암호화된 내용도 다 풀 수 있음
```

그래서 나온 게 **공개키/개인키** 방식이다.

```
공개키 = 자물쇠 (누구나 가질 수 있음, 잠그기만 가능)
개인키 = 열쇠  (서버만 가짐, 풀기만 가능)
```

흐름을 보면:

```
서버: 공개키(자물쇠)를 클라이언트한테 나눠줌
클라이언트: 공개키로 내용 잠가서 서버한테 보냄
서버: 개인키(열쇠)로 풀어서 읽음

중간에 누가 가로채도 개인키가 없으니 못 풀음
```

---

### SSL이 뭐야

근데 여기서 또 문제가 생긴다.

```
클라이언트: "woowahan-delivery.site 서버야, 공개키 줘"
???:        "나 woowahan-delivery.site인데, 여기 공개키"  ← 가짜일 수도 있음
```

공개키를 받았는데 그게 **진짜 서버 것인지 확인할 방법이 없다.**

그래서 **공인된 기관(CA)** 이 등장한다.

```
CA = 인증 기관 (정부가 인정한 공증 기관 같은 것)
   = DigiCert, Let's Encrypt 같은 곳
```

흐름을 보면:

```
서버: CA한테 "나 woowahan-delivery.site 주인 맞아, 인증해줘"
CA:  확인 후 "맞네, 도장 찍어줄게" → 인증서 발급
서버: 클라이언트한테 인증서 보여줌
클라이언트: "CA 도장 있네, 진짜 서버 맞구나" → 연결 신뢰
```

**이 인증서가 SSL 인증서고, 이 과정 전체가 SSL이다.**

```
SSL = 서버가 진짜인지 확인 + 암호화 통신을 가능하게 해주는 것
```

브라우저 자물쇠 아이콘이 바로 이거다.

```
🔒 자물쇠 있음 = "CA가 인증한 서버 맞고, 암호화 통신 중"
🔓 자물쇠 없음 = "암호화 안됨, 내용 다 노출될 수 있음"
```

---

### 한눈에 정리

```
암호화  = 내용을 알아볼 수 없게 바꾸는 것
복호화  = 암호화된 내용을 다시 원래대로 푸는 것

공개키  = 자물쇠, 누구나 가질 수 있음
개인키  = 열쇠, 서버만 가짐

SSL     = 서버가 진짜인지 CA가 인증 + 공개키/개인키로 암호화 통신
HTTPS   = HTTP + SSL
```

<br><br>

## 📍 작은 PR

### 1. 거대한 PR로 인한 코드 리뷰 병목 현상
- 문제 상황
  - 조직이 커지고 서비스가 복잡해지면서, 한 번에 올라오는 PR(Pull Request)의 코드량이 수천 줄에서 만 줄 이상까지 길어지는 문제가 발생했습니다. 코드가 길어지니 리뷰어의 집중력이 떨어지고, 코드를 이해하는 데 시간이 너무 오래 걸려 리뷰 자체가 일정에 병목(Blocker)이 되었습니다.

- 도입한 문화
  - 작은 PR (Small PR) 규칙: 리뷰가 고객 임팩트를 내는 데 방해가 되지 않도록, 기능 단위를 최대한 잘게 쪼개는 규칙을 만들었습니다.

- 상세 룰 및 플로우
  - 1,000줄 제한: 1개의 PR은 1,000줄을 넘을 수 없습니다. (단, 라인 수를 많이 차지하는 테스트용 Mock json 데이터는 예외로 둡니다.)
  - 최소 기능 단위 분할: 아무리 복잡한 기능이라도 PR과 Commit의 단위를 나눌 수 있는 최소의 기능 단위로 세분화하여 올립니다.

- 결론 및 효과
  - PR 규모가 200-300줄 내외로 줄어들면서, 리뷰어가 코드를 파악하기 쉬워졌습니다. 그 결과 1-2일 이내에 빠른 리뷰가 가능해졌고, 팀원이 늘어나고 코드량이 증가해도 병목 없는 개발 프로세스를 유지할 수 있게 되었습니다.

<br>

### 2. 작은 PR들이 유발하는 Git 브랜치 충돌과 배포 복잡도
- 문제 상황
  - '작은 PR 규칙'을 적용해 큰 프로젝트(예: 뱅크샐러드 2.0 UX 개편)를 진행하려다 보니 브랜치 전략에 문제가 생겼습니다. 개발용 브랜치를 따로 파서 수개월간 작은 PR들을 모은 뒤 나중에 메인 브랜치와 한 번에 합치려고 하면 엄청난 Conflict(충돌)가 예상되었습니다. 그렇다고 미완성된 기능의 작은 PR들을 메인 브랜치에 바로 합치자니, 고객들에게 미완성 화면이 노출될 위험이 있었습니다.
- 도입한 문화: '작은 PR'과 '실험 플랫폼(Feature Flag)'의 시너지
  - 코드 충돌을 막기 위해 코드는 바로바로 합치되, 고객에게는 보이지 않도록 '실험 플랫폼'이라는 칸막이를 활용했습니다.
- 상세 동작 순서
  - 작업자는 전체 기능이 완성되지 않았더라도, 최소 기능 단위로 작은 PR을 올려 리뷰를 받습니다.
  - 리뷰가 완료되면 해당 코드를 지체 없이 메인 브랜치(실제 서비스되는 브랜치)에 병합(Merge)합니다.
  - 이때 코드 내부에 실험 플랫폼을 연동하여 if 유저가 실험군이면 { 신규 UI } else { 기존 UI } 형태로 분기 처리를 해둡니다.
  - 기능이 메인에 합쳐져도, 실험 플랫폼에서 해당 기능을 활성화하기 전까지 일반 고객(대조군)에게는 기존 화면만 보입니다.
- 결론 및 효과
  - 수개월이 걸리는 대규모 프로젝트도 Conflict나 리뷰 병목 없이 800개의 작은 Commit 단위로 안전하게 메인 브랜치에 개발할 수 있었고, 성공적으로 서비스를 오픈할 수 있었습니다.

<br>

### 3. 배경지식 부족으로 인한 커뮤니케이션 비용 증가
- 문제 상황
  - 리뷰어가 해당 PR의 기획 의도나 배경을 모르는 상태에서 코드를 보면, "이거 왜 이렇게 짠 거지?" 하고 의도를 파악하기 위한 추가 질문이 오가게 됩니다. 뱅크샐러드는 이러한 미스 커뮤니케이션이나 추가 질문에 들어가는 시간을 매우 비싼 비용으로 간주했습니다.
- 도입한 문화: 저 문맥(Low Context) 커뮤니케이션과 GitHub 템플릿
  - "내가 아는 것을 상대방은 모른다"는 전제하에, 리뷰 요청자가 PR에 모든 문맥을 밥상 차려주듯 제공하는 문화를 정착시켰습니다.
- 상세 플로우
  - PR을 올릴 때 사전에 정의된 GitHub 템플릿을 엄격하게 채웁니다.
  - 제품 기획서, 기술 스펙, Figma(디자인) 링크를 필수로 첨부합니다.
  - 현재 PR에서 무엇이 변경되었는지, 어떤 테스트를 했는지 명시합니다.
  - 복잡한 UI/UX 변경이 있다면 실행 화면을 GIF로 녹화하여 첨부해 리뷰어의 시각적 이해를 돕습니다.
- 결론 및 효과
  - 리뷰어는 사전 지식이 전혀 없어도 PR 본문만 읽으면 바로 문맥을 파악하고 핵심적인 코드 리뷰에 집중할 수 있게 되었습니다.

<br>

### 4. 텍스트 리뷰의 오해와 우선순위 혼란
- 문제 상황
   - 온라인으로 텍스트만 주고받다 보니, 가벼운 제안이 심각한 지적으로 오해받아 감정이 상하거나 업무에 블로커가 되는 일이 생겼습니다. 또한, 하루에 PR이 여러 개 쏟아질 때 리뷰어가 어떤 것부터 봐야 할지 몰라 본인의 개발 흐름이 끊기는 문제가 있었습니다.

- 도입한 문화: Pn 룰(피드백 강도)과 D-n 룰(리뷰 기한)
   - 텍스트 커뮤니케이션의 한계를 보완하기 위해 코멘트 앞에 명시적인 태그를 달도록 약속했습니다.

- 상세 룰
   - `Pn` 룰 (강조 정도 표현)
     - `P1`: 심각한 버그 등 꼭 수정해야 함 (반영 필수)
     - `P2`: 적극적으로 수정을 고려해 주거나, 토론 요망 (요청)
     - `P3`: 웬만하면 반영해 주고, 안 되면 이유를 남겨줄 것 (코멘트)
     - `P4`: 반영해도 좋고 무시해도 좋음 (승인)
     - `P5`: 아주 사소한 의견 (승인)
   - `D-n` 룰 (일정 공유)
     - `D-0`: 장애 발생 등 아주 긴급한 이슈 (즉시 리뷰 요망)
     - `D-N`: Working Day 기준 N일 이내에 리뷰 요망 (예: 여유로우면 D-3, 보통은 D-2)

- 결론 및 효과
   - 리뷰어의 의도가 명확해져 텍스트로 인한 감정 소모나 오해가 사라졌습니다. 또한 리뷰어가 D-n 태그를 보고 자신의 개발 스케줄 안에서 리뷰 우선순위를 스스로 조율할 수 있게 되었습니다.

<br>

### 5. 사람의 리소스를 낭비하는 스타일 점검과 테스트 대기 시간
- 문제 상황
   - 코드의 들여쓰기(Indent)나 네이밍 컨벤션 같은 단순한 스타일을 리뷰하는 데 아까운 시간을 쏟고 있었습니다. 또한 안정성을 위해 작성한 유닛 테스트 코드를 로컬 PC에서 돌리려면 10~15분이 걸렸고, 하루에 4번만 돌려도 1시간 동안 개발자가 손을 놓고 기다려야 했습니다.
도입한 문화: 자동화 (Automation)
   - "자동화할 수 있는 부분은 기계에 맡기고, 사람의 리소스(인건비)를 아끼자"는 철학을 적용했습니다.
- 상세 동작 순서
   - 코딩 스타일 자동화: SwiftFormat이라는 툴을 도입했습니다. 개발자가 코드를 짜고 빌드만 누르면, 사전에 합의된 룰에 따라 코드가 자동으로 예쁘게 정렬됩니다. 리뷰어는 포맷팅 지적을 할 필요가 없습니다.
   - 테스트 자동화 (CI 구축): 유닛 테스트 전용 고사양 Mac Pro를 구매하고 Jenkins를 연동했습니다. 개발자가 GitHub에 PR을 올리거나 Commit을 하면, CI 서버가 알아서 10~15분짜리 테스트를 대신 돌립니다.
   - 테스트 통과 여부는 PR 상태 창에 초록색/빨간색으로 표시되며, 실패할 경우 물리적으로 Merge 버튼이 비활성화(Block)되어 불량 코드가 메인에 합쳐지는 것을 원천 차단합니다.
- 결론 및 효과
   - 단순 반복 작업에서 해방되어 개발과 핵심 비즈니스 로직에만 집중할 수 있게 되었습니다. 테스트 자동화 시스템이 월 약 133시간(개발자 1명의 한 달 업무 시간)의 대기 시간을 대신 처리해 주어, 사실상 개발자 1명을 추가 채용한 것과 같은 막대한 리소스 절약 효과를 얻었습니다.
