#!/bin/sh
# Build and Run the project in local instance
# EXPEPCTED M2_HOME and CATALINA_HOME in SYSTEM PATH
#
echo
echo -e "\e[97mcd /home/pi/git/pi-aws-project\e[0m"
cd /home/pi/git/pi-aws-project
echo -e "\e[90mRunning Maven: mvn clean package install ...\e[0m"

echo
mvn clean package -Dmaven.test.skip=true
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
echo -e "\e[91m*** Removing WAR /opt/apache-tomcat-9.0.11/webapps/*.war"
echo
sudo rm /opt/apache-tomcat-9.0.11/webapps/*.war
echo -e "\e[91m*** Removing DIRECTORY /opt/apache-tomcat-9.0.11/webapps/pi-aws-project"
rm -fr /opt/apache-tomcat-9.0.11/webapps/pi-aws-project
echo
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
echo
echo -e "\e[93m*** Copying /home/pi/git/pi-aws-project/target/*.war /opt/apache-tomcat-9.0.11/webapps ...\e[0m"
cp /home/pi/git/pi-aws-project/target/*.war /opt/apache-tomcat-9.0.11/webapps

read -p "Starting Tomcat in five sec ...." -t 3
catalina.sh run
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
