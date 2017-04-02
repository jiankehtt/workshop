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

getServerStatus(){
    getTradeProtalPID
    echo "================================================================================================================"
    if [ $tradePortalPID -ne 0 ]; then
        echo "$APP_MAIN is running(PID=$tradePortalPID)"
        echo "================================================================================================================"
    else
        echo "$APP_MAIN is not running"
        echo "================================================================================================================"
    fi
}

getServerStatus