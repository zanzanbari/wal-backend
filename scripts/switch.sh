echo "> 현재 구동중인 Port 확인"
CURRENT_PORT=$(cat /etc/nginx/conf.d/service-url.inc | grep -Po '[0-9]+' | tail -1)

if [ $CURRENT_PORT == 8081 ]; then
  TARGET_PORT=8082
elif [ $CURRENT_PORT == 8082 ]; then
  TARGET_PORT=8081
else
  echo "> 일치하는 Profile 이 없습니다. Profile: $CURRENT_PORT"
  echo "> 8081을 할당합니다."
  TARGET_PORT=8081
fi

echo "> 전환할 Port: $TARGET_PORT"
echo "> Port 전환"
echo "set \$service_url http://127.0.0.1:${TARGET_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

PROXY_PORT=$(cat /etc/nginx/conf.d/service-url.inc | grep -Po '[0-9]+' | tail -1)
echo "> Nginx Current Proxy Port: $PROXY_PORT"

echo "> Nginx Reload"
sudo service nginx reload