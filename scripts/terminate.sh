PROJECT_ROOT="/home/ubuntu"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"
TIME_NOW=$(date +%c)

CURRENT_PID=$(lsof -t -i tcp:8080)

if [ -z $CURRENT_PID ]; then
  echo "$TIME_NOW > no process"
else
  kill -15 $CURRENT_PID
  echo "$TIME_NOW > process $CURRENT_PID terminated"
fi