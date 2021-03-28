#!/bin/sh
PRG="$0"
while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done
PRGDIR=`dirname "$PRG"`
java -Xms@gui.vm.initialHeapSize@m -Xmx@gui.vm.maxHeapSize@m -D@mainClass@.main.mode=gui -DSWT_GTK3=0 -jar "$PRGDIR/@linux.jarFileName"
