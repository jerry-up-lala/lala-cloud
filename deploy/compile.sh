#!/bin/bash
COMPILE_DIR=$1
SERVER_NAME=$2

if [ $# -eq 0 ]; then
    echo "请输入编译输出目录"
    exit 1
fi

if [ $# -eq 1 ]; then
    echo "请输入服务名称"
    exit 1
fi

if [ ! -d $COMPILE_DIR ]; then
    mkdir -p $COMPILE_DIR
fi

WORK_DIR=$(pwd)

TARGET_NAME=$SERVER_NAME-server
SOURCE_PATH='/server/'$SERVER_NAME-server
if [[ $SERVER_NAME == "gateway" ]]; then
    SOURCE_PATH='/gateway'
    TARGET_NAME='gateway'
fi

cd $WORK_DIR/../cloud/$SOURCE_PATH
mvn clean install -Dmaven.test.skip=true
rm -rf $COMPILE_DIR/$TARGET_NAME
tar -xf ./target/zip/$TARGET_NAME.tar.gz -C $COMPILE_DIR

cd $WORK_DIR