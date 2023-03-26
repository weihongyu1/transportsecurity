#!/bin/bash

LOCAL_TIME=$(date+"%Y%m%d %H:%M:%S")
HEALTH='健康检查'
echo ${HEALTH}
pIDa=`/usr/sbin/lsof -i :80 | grep -v "PID" | awk '{print $2}'`
echo $pIDa
if [ "$pIDa" != "" ];
then
   echo '健康检查通过'
else
   echo '健康检查不通过'
   nohup java -jar /home/java/transport/transportsecurity/target/transportsecurity_finally-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod &
   echo '程序启动完成'
fi
