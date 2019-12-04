#!/usr/bin/env bash
KILL_WAIT_COUNT=120
cat pid | while read PID
do
    echo "kill: $PID"
    kill  ${PID}
    COUNT=0
    while [ ${COUNT} -lt ${KILL_WAIT_COUNT} ]; do
        echo -e ".\c"
        sleep 1
        let COUNT=$COUNT+1
        PID_EXIST=`ps -f -p ${PID} | grep java`
        #echo ${PID_EXIST}
        if [ -z "$PID_EXIST" ]; then
            echo -e "\n"
            echo "stop $PID success."
            exit 0;
        fi
    done
    echo "stop $PID failure,dump and kill -9 it."
    kill -9 ${PID}
done
rm pid