#!/bin/sh
TPID=`ps -ef|grep heart-api|grep -v grep|awk '{print $2}'`
if [ '$TPID' == "" ];then
   echo "服务没有启动"
else
   kill -9 $TPID
fi
cd /home/appadmin/test/heart
sh start.sh start