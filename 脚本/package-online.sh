#!/bin/sh
#测试环境使用 application-test.yml profiles=test
#CONFIG=application-test.yml
#正式环境使用application.yml profiles=online
CONFIG=application.yml

THIS_PATH=$(pwd)

cd $(cd `dirname $0`; pwd)

PA=/Users/xuliduo/runtime/ows
FILE1=ows-activity-center-0.0.1-SNAPSHOT.jar
#FILE2=ows-activity-center-0.0.1-SNAPSHOT.jar
FILE2=ows-activity-center-1.0.jar
VER=v1.0.0
P=ows-activity-center-${VER}
#LOG=log4j2-for-test.xml
LOG=log4j2-online.xml

rm -rf ${PA}/${P}
rm -rf ../target
rm -rf ${PA}/${P}.zip

mkdir -p  ${PA}/${P}/temp
mv  ../src/main/resources/*.yml ${PA}/${P}/temp
mv ../src/main/resources/*.xml ${PA}/${P}/temp

cd ..
 mvn -DskipTests=true clean  package

if [ -e target/${FILE1} ]; then
    echo "编译完成...开始拷贝..."
    cp target/${FILE1} ${PA}/${P}/${FILE2}
    cp ${PA}/${P}/temp/${LOG} ${PA}/${P}
    cp ${PA}/${P}/temp/application.yml ${PA}/${P}/${CONFIG}
    if [ -d "sql" ]; then
        cp -R sql ${PA}/${P}
    fi
else
    echo "编译失败..."
fi
cd bin

mv ${PA}/${P}/temp/* ../src/main/resources
rm -rf ${PA}/${P}/temp

if [ "$1" = "true" ]
then
    echo "zip..."
    cd ${PA}
    zip -r ${P}.zip ${P}
fi

cd ${THIS_PATH}

open ${PA}/${P}