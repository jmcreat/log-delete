#!/bin/bash
#nohup /usr/share/tomcat7/log-delete/src/logDelete.sh &
nohup java -cp .:/usr/share/tomcat7/log-delete/log4j-1.2.17.jar LogDel access_log 60 &

