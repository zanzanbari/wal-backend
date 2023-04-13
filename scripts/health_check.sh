CURRENT_PORT=$(cat /etc/nginx/conf.d/service-url.inc | grep -Po '[0-9]+' | tail -1)

if [ $CURRENT_PORT == 8081 ]; then
    TARGET_PORT=8082
elif [ $CURRENT_PORT == 8082 ]; then
    TARGET_PORT=8081
else
  echo "> 일치하는 Port 가 없습니다. 8081 Port 를 할당합니다"
  echo "> Port: $CURRENT_PORT"
  TARGET_PORT=8081
fi

echo "> $TARGET_PORT 5초 후 Health check 시작"
echo "> curl -s http://localhost:$TARGET_PORT/api/health"
sleep 5

for RETRY_COUNT in {1..10}
do
  echo "> #${RETRY_COUNT} trying..."
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://127.0.0.1:$TARGET_PORT/api/health)

  if [ $RESPONSE_CODE -eq 200 ]; then
      echo "> $TARGET_PORT WAS Health check 성공"
      break
  else
      echo "> Health check 의 응답 status 가 $RESPONSE_CODE 입니다"
  fi

  if [ $RETRY_COUNT -eq 10 ]; then
    echo "> Health check 실패 "
    echo "> Nginx 에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..."
  sleep 10
done