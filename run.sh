#!/bin/sh
cd $(dirname $0)

mvn exec:java -Dexec.mainClass=com.java.momentum.dailypoints.Application

exit
