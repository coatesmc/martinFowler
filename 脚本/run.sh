#!/usr/bin/env bash
JAR_FILE=ows-activity-center-0.0.1-SNAPSHOT.jar
nohup java -jar -server -Xdebug -Xrunjdwp:transport=dt_socket,suspend=n,server=y,address=20003 -Dspring.profiles.active=dev $JAR_FILE -Dspring.config.location=./application.yml &

PID=$(ps -ef | grep $JAR_FILE | grep 'java' | grep -v grep | awk '{print $2}')
if [ $? -eq 0 ]; then
    echo "[$JAR_FILE]启动成功,process id:$PID"
    echo $PID>pid
else
    echo "process $JAR_FILE not exit"
    exit
fi