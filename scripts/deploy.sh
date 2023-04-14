ROOT_PATH=/home/ubuntu
BUILD_PATH=$ROOT_PATH/wal-server/build/libs/*.jar
JAR_NAME=$(basename $BUILD_PATH)
echo "> build 파일명: $JAR_NAME"

echo "> build 된 jar 파일 복사"
DEPLOY_PATH=$ROOT_PATH/deploy-jar
cp $BUILD_PATH $DEPLOY_PATH

cd $ROOT_PATH
nohup java -jar -Dspring.profiles.active=prod1 /home/ubuntu/wal-server/build/libs/*.jar > /home/ubuntu/nohup.out 2>&1 &
echo "> jar 파일 실행"

echo "> 현재 구동중인 Profile 확인"
CURRENT_PROFILE=$(curl -s http://localhost/api/profile)
echo "> 현재 구동중인 Profile: $CURRENT_PROFILE"