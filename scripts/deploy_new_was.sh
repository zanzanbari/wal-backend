ROOT_PATH=/home/ubuntu
BUILD_PATH=$ROOT_PATH/wal-server/build/libs/*.jar
JAR_NAME=$(basename $BUILD_PATH)
echo "> build 파일명: $JAR_NAME"

echo "> build 파일 복사"
DEPLOY_PATH=$ROOT_PATH/jar
cp $BUILD_PATH $DEPLOY_PATH

echo "> 현재 구동중인 Port 확인"
CURRENT_PORT=$(cat /etc/nginx/conf.d/service-url.inc | grep -Po '[0-9]+' | tail -1)

if [ $CURRENT_PORT == 8081 ]; then
    TARGET_PROFILES=prod2
    TARGET_PORT=8082
elif [ $CURRENT_PORT == 8082 ]; then
    TARGET_PROFILES=prod1
    TARGET_PORT=8081
else
  echo "> 일치하는 Profile 이 없습니다. Profile: $CURRENT_PORT"
  echo "> prod1 을 할당합니다. TARGET_PROFILES: prod1"
  TARGET_PROFILES=prod1
  TARGET_PORT=8081
fi

echo "> application.jar 교체"
TARGET_APPLICATION=$TARGET_PROFILES-wal-server.jar
TARGET_APPLICATION_PATH=$DEPLOY_PATH$TARGET_APPLICATION
ln -Tfs $DEPLOY_PATH$JAR_NAME $TARGET_APPLICATION_PATH

echo "> $TARGET_PROFILE 에서 구동중인 애플리케이션 pid 확인"
TARGET_PID=$(pgrep -f $TARGET_APPLICATION)

if [ -z $TARGET_PID ]; then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $TARGET_PID"
  kill -15 $TARGET_PID
  sleep 5
fi

echo "> $TARGET_PROFILES 배포 및 jar 실행"
nohup java -jar -Dspring.profiles.active=$TARGET_PROFILES $TARGET_APPLICATION_PATH  > /home/ubuntu/nohup.out 2>&1 &
echo "> Now new WAS runs at $TARGET_PORT"