#!/bin/bash
source ../jar.properties

SERVER_NAME={{SERVER_NAME}}

TARGET_NAME=$SERVER_NAME-server
SOURCE_PATH='/server/'$SERVER_NAME-server
if [[ $SERVER_NAME == "gateway" ]]; then
    SOURCE_PATH='/gateway'
    TARGET_NAME='gateway'
fi

POM_VERSION=$(eval echo '$'$(echo "$SERVER_NAME" | tr '[:lower:]' '[:upper:]')_POM_VERSION)

JAR_PATH=$DEPLOY_DIR/$SERVER_NAME/$TARGET_NAME/$TARGET_NAME-$POM_VERSION.jar

PID=`ps -ef | grep $JAR_PATH | grep -v grep`
if [ -z "$PID" ]; then
  echo "$SERVER_NAME"未运行
else
  ps aux | grep $JAR_PATH | grep -v grep | awk '{print $2}' | xargs kill -9
fi