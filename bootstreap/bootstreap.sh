#!/bin/bash

LOCAL_TIME=$(data+"%Y%m%d %H:%M:%S")
HEALTH='健康检查'
echo "${HEALTH} ${LOCAL_TIME}" >> health.log
RESULT=$(curl -s http://localhost/ts/health)
if [ ${RESULT} -eq 200 ];
then
  echo '健康检查通过' >> health.log
else
  echo '健康检查不通过' >> health.log
  nohup java -jar /home/java/transport/transportsecurity/target/transportsecurity_finally-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod &
fi
