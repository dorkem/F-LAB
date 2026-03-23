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
1. Trigger: 내 PC에서 feature/step2 브랜치로 코드를 Push 한다.
2. Build (GitHub 서버): GitHub이 무료로 빌려주는 임시 컴퓨터가 켜져서, 코드를 다운받고, 프론트엔드와 백엔드를 각각 빌드(압축)한다.
3. Transfer (네트워크): 완성된 결과물 파일만 내 EC2 서버로 전송한다.
4. Deploy (EC2 서버): EC2 서버에서 기존에 돌고 있던 앱을 끄고, 새로 받은 파일로 앱을 다시 켠다.

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


