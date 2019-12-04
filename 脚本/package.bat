@echo off

rem 测试环境使用 application-test.yml profiles=test
SET CONFIG=application-test.yml
rem 正式环境使用application.yml profiles=online
rem SET CONFIG=application.yml

SET THIS_PATH=%cd%

cd %~dp0

SET PA=C:\Users\xuliduo\Desktop\runtime
SET FILE1=ows-activity-center-0.0.1-SNAPSHOT.jar
SET FILE2=ows-activity-center-1.0.jar
SET VER=v1.0.0-test
SET P=ows-activity-center-%VER%
SET LOG=log4j2-for-test.xml

IF EXIST "%PA%\%P%" rd /s/q "%PA%\%P%"
IF EXIST ..\target rd /s/q ..\target
IF EXIST "%PA%\%P%\temp" rd /s/q  "%PA%\%P%\temp"

md "%PA%\%P%\temp"

move  ..\src\main\resources\*.yml "%PA%\%P%\temp\"
move ..\src\main\resources\*.xml "%PA%\%P%\temp\"

cd ..
call mvn -DskipTests=true clean  package

IF EXIST "target\%FILE1%" (
    echo "package done...copping..."
    copy "target\%FILE1%" "%PA%\%P%\%FILE2%"
    copy "%PA%\%P%\temp\%LOG%" "%PA%\%P%"
    copy "%PA%\%P%\temp\application.yml" "%PA%\%P%\%CONFIG%"
    IF EXIST "sql" xcopy sql "%PA%\%P%\sql\"
) ELSE (
     echo "error..."
)

cd bin

move "%PA%\%P%\temp\*" "..\src\main\resources"
rd /s/q "%PA%\%P%\temp"

start "%PA%\%P%"
cd %THIS_PATH%