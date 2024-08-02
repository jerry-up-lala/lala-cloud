#!/bin/bash
source ./jar.properties

WORK_DIR=$(pwd)

SERVER_NAME=$1

if [ $# -eq 0 ]; then
    echo "请输入服务名称"
    exit 1
fi

SERVER_DEPLOY_DIR=$DEPLOY_DIR/$SERVER_NAME

if [ ! -d $SERVER_DEPLOY_DIR ]; then
    mkdir -p $SERVER_DEPLOY_DIR
fi

cd ../
sh ./compile.sh $SERVER_DEPLOY_DIR $SERVER_NAME

cd $WORK_DIR

rm -rf $SERVER_DEPLOY_DIR/start.sh $SERVER_DEPLOY_DIR/stop.sh 

rm -rf $DEPLOY_DIR/jar.properties

cp ./start.sh ./stop.sh $SERVER_DEPLOY_DIR/

cp ./jar.properties $DEPLOY_DIR/

sed -i "s/{{SERVER_NAME}}/$SERVER_NAME/g" $SERVER_DEPLOY_DIR/start.sh
sed -i "s/{{SERVER_NAME}}/$SERVER_NAME/g" $SERVER_DEPLOY_DIR/stop.sh