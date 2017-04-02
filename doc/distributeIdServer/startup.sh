#!/bin/sh
#-------------------------------------------------------------------------------------------------------------
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

startup(){
    getTradeProtalPID
    echo "================================================================================================================"
    if [ $tradePortalPID -ne 0 ]; then
        echo "$APP_MAIN already started(PID=$tradePortalPID)"
        echo "================================================================================================================"
    else
        echo -n "Starting $APP_MAIN"
        # nohup $JAVA_HOME/bin/java $JAVA_OPTS -classpath $CLASSPATH $APP_MAIN > $APP_LOG/nohup.log &
       	nohup java -cp $APP_JAR:lib/* $APP_MAIN > ./logs/nohup.log &
        getTradeProtalPID
        if [ $tradePortalPID -ne 0 ]; then
            echo "(PID=$tradePortalPID)...[Success]"
            echo "================================================================================================================"
        else
            echo "[Failed]"
            echo "================================================================================================================"
        fi
    fi
}

startup