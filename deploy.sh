#!/bin/bash

echo "Creating AAR"
ant buildAAR
sudo cp -r ./build/musicVisualizationService.aar /var/lib/tomcat7/webapps/axis2/WEB-INF/services
if [ $? -ne 0 ]; then
	echo "FAILED TO COPY AAR TO /VAR/LIB/TOMCAT7/WEBAPPS/AXIS2/WEB-INF/SERVICES"
else
	echo "COPIED AAR TO /VAR/LIB/TOMCAT7/WEBAPPS/AXIS2/WEB-INF/SERVICES"
fi

ant build

# clean
sudo rm -r /var/lib/tomcat7/webapps/Quest.war
sudo rm -r /var/lib/tomcat7/webapps/Quest

sudo cp build/Quest.war /var/lib/tomcat7/webapps/Quest.war
if [ $? -ne 0 ]; then
	echo "FAILED TO COPY Quest.war COPIED TO /VAR/LIB/TOMCAT7/WEBAPPS/"
else
	echo "COPIED Quest.war TO /VAR/LIB/TOMCAT7/WEBAPPS/"
fi
