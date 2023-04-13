ROOT_PATH="/home/ubuntu"
ORIGINAL_JAR_FILE="$ROOT_PATH/wal-server/build/libs/*.jar"

CURRENT_TIME=$(date +%c)

cp $ORIGINAL_JAR_FILE $ROOT_PATH

cd $ROOT_PATH
nohup java -jar -Dspring.profiles.active=prod /home/ubuntu/wal-server/build/libs/*.jar > /home/ubuntu/nohup.out 2>&1 &
echo "$CURRENT_TIME > jar 파일 실행"

EXECUTED_PROCESS_PID=$(lsof -t -i tcp:8080)
echo "$CURRENT_TIME > executed process pid = $EXECUTED_PROCESS_PID"
curl -s -d "payload={\"text\":\"Application Execution : $EXECUTED_PROCESS_PID\"}" "${SLACK_WEBHOOK_URI}"