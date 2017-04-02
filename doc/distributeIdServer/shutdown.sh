#!/bin/sh
JAVA_HOME="/usr/java/jdk1.6.0_32"
JAVA_OPTS="-server -Xms128m -Xmx512m -XX:MaxPermSize=128m"
APP_LOG=/webapp/idserver/logs
APP_HOME=/webapp/idserver
APP_JAR=distributeIdServer-0.0.1-SNAPSHOT-jar-with-dependencies.jar
APP_MAIN=com.up366.distributeid.server.ServerMain


tradePortalPID=0

getTradeProtalPID(){
    javaps=`$JAVA_HOME/bin/jps -l | grep $APP_MAIN`
    if [ -n "$javaps" ]; then
        tradePortalPID=`echo $javaps | awk '{print $1}'`
    else
        tradePortalPID=0
    fi
}

shutdown(){
    getTradeProtalPID
    echo "================================================================================================================"
    if [ $tradePortalPID -ne 0 ]; then
        echo -n "Stopping $APP_MAIN(PID=$tradePortalPID)..."
        kill -9 $tradePortalPID
        if [ $? -eq 0 ]; then
            echo "[Success]"
            echo "================================================================================================================"
        else
            echo "[Failed]"
            echo "================================================================================================================"
        fi
        getTradeProtalPID
        if [ $tradePortalPID -ne 0 ]; then
            shutdown
        fi
    else
        echo "$APP_MAIN is not running"
        echo "================================================================================================================"
    fi
}

shutdown
