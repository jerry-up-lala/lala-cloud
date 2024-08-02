#!/bin/bash
WORK_DIR=$(pwd)

cd $WORK_DIR/../cloud/api
mvn clean deploy -Dmaven.test.skip=true