#!/bin/bash
source ../jar.properties

DATA_PATH=$DEPLOY_DIR/data

if [ ! -d $DATA_PATH ]; then
    mkdir -p $DATA_PATH
fi

SERVER_NAME={{SERVER_NAME}}

TARGET_NAME=$SERVER_NAME-server
SOURCE_PATH='/server/'$SERVER_NAME-server
if [[ $SERVER_NAME == "gateway" ]]; then
    SOURCE_PATH='/gateway'
    TARGET_NAME='gateway'
fi

LOGS_DIR=$DATA_PATH/logs/$TARGET_NAME

if [ ! -d $LOGS_DIR ]; then
    mkdir -p $LOGS_DIR
fi

POM_VERSION=$(eval echo '$'$(echo "$SERVER_NAME" | tr '[:lower:]' '[:upper:]')_POM_VERSION)

JAR_PATH=$DEPLOY_DIR/$SERVER_NAME/$TARGET_NAME/$TARGET_NAME-$POM_VERSION.jar

nohup java -jar \
    -Xmx1024m \
    -Xms1024m \
    -Xmn512m \
    -Xss256k \
    -XX:SurvivorRatio=8 \
    -XX:MetaspaceSize=128m \
    -XX:MaxMetaspaceSize=384m \
    -XX:-OmitStackTraceInFastThrow \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=$LOGS_DIR/heapdump.hprof \
    -XX:+UseCMSInitiatingOccupancyOnly \
    -XX:CMSInitiatingOccupancyFraction=75 \
    -Xloggc:$LOGS_DIR/gc.log \
    -XX:+PrintGCDetails \
    -XX:+PrintGCDateStamps \
    -XX:+PrintGCApplicationConcurrentTime \
    -XX:+PrintHeapAtGC \
    -XX:+UseGCLogFileRotation \
    -XX:NumberOfGCLogFiles=5 \
    -XX:GCLogFileSize=5M \
    $JAR_PATH \
    --DATA_PATH=$DATA_PATH \
    --NACOS_CONFIG_HOST=$NACOS_CONFIG_HOST \
    --NACOS_CONFIG_GROUP=$NACOS_CONFIG_GROUP \
    --NACOS_CONFIG_ENV=$NACOS_CONFIG_ENV \
    > $LOGS_DIR/$TARGET_NAME.log 2>&1 &

echo "部署目录: $DEPLOY_DIR 日志文件目录：$LOGS_DIR "