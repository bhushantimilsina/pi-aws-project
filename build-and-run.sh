#!/bin/sh
# Build and Run the project in local instance
# EXPEPCTED M2_HOME and CATALINA_HOME in SYSTEM PATH
#
echo
echo -e "\e[97mcd /cygdrive/g/git/pi-aws-project\e[0m"
cd /cygdrive/g/git/pi-aws-project
echo -e "\e[90mRunning Maven: mvn clean package install ...\e[0m"

echo
mvn clean package -Dmaven.test.skip=true
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
echo -e "\e[91m*** Removing WAR /cygdrive/g/apache-tomcat-9.0.10/webapps/*.war"
echo
rm /cygdrive/g/apache-tomcat-9.0.10/webapps/*.war
echo -e "\e[91m*** Removing DIRECTORY /cygdrive/g/apache-tomcat-9.0.10/webapps/pi-aws-project"
rm -fr /cygdrive/g/apache-tomcat-9.0.10/webapps/pi-aws-project
echo
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
echo
echo -e "\e[93m*** Copying /cygdrive/g/git/pi-aws-project/target/*.war /cygdrive/g/apache-tomcat-9.0.10/webapps ...\e[0m"
cp /cygdrive/g/git/pi-aws-project/target/*.war /cygdrive/g/apache-tomcat-9.0.10/webapps

read -p "Starting Tomcat in five sec ...." -t 3
catalina.sh run
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
