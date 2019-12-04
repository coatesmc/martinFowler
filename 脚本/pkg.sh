#!/usr/bin/env bash
THIS_PATH=$(pwd)
cd $(cd `dirname $0`; pwd)

PROJECT_NAME=ows-activity-center
JAR_FILE=ows-activity-center-0.0.1-SNAPSHOT.jar
BRANCH=""

if [[ -z $1 && -z $2 ]]; then
    echo "参数错误，请带上参数："
    echo "参数1: git 地址"
    echo "参数2: 打包的jar包名称"
    echo "参数3(可选): 分支"
    exit 0
fi

if [ -z $3 ]; then
    echo "使用分支 master"
else
    BRANCH=$3
    echo "使用分支"${BRANCH}
    BRANCH="-b "${BRANCH}" --single-branch"
fi

rm -rf ${PROJECT_NAME}
#开始克隆项目
echo "开始克隆:git clone $1 ${PROJECT_NAME} ${BRANCH}"
git clone $1 ${PROJECT_NAME} ${BRANCH}

#检查打包脚本
if [ ! -f "${PROJECT_NAME}/bin/package.sh" ]; then
    echo "项目打包脚本不存在";
    cd ${THIS_PATH}
    exit 1
else
    chmod +x ${PROJECT_NAME}/bin/package.sh
fi

#切换分支
#if [ ! -z  ${BRANCH} ];then
#    cd ${PROJECT_NAME}
#    git checkout -b ${BRANCH} origin/${BRANCH}
#    git pull --force
#    cd ..
#fi

#停止服务（如果有 stop.sh）
if [ -f "${PROJECT_NAME}/bin/stop.sh" ];then
    cp ${THIS_PATH}/${PROJECT_NAME}/bin/stop.sh ${THIS_PATH}
    chmod +x ${THIS_PATH}/stop.sh
    ${THIS_PATH}/stop.sh
fi

#开始打包
${THIS_PATH}/${PROJECT_NAME}/bin/package.sh ${THIS_PATH} ${JAR_FILE} v1.0.0-dev

if [ ! -f "${PROJECT_NAME}-v1.0.0-dev/${JAR_FILE}" ]; then
   echo "${JAR_FILE}不存在"
   cd ${THIS_PATH}
   exit 1
fi

#开始拷贝
if [  -f "${JAR_FILE}" ];then
    echo "移除原有文件"
    rm -rf ${JAR_FILE}
fi
echo "开始拷贝..."
mv ${PROJECT_NAME}-v1.0.0-dev/${JAR_FILE} .

if [ ! -f "${JAR_FILE}" ];then
    echo "拷贝失败"
    cd ${THIS_PATH}
    exit 1
fi

#清除目录
rm -rf ${PROJECT_NAME}
rm -rf ${PROJECT_NAME}-v1.0.0-dev

echo "搞定..."

if [ -x "run.sh" ]; then
    ./run.sh
    tail -f nohup.out
fi

cd ${THIS_PATH}