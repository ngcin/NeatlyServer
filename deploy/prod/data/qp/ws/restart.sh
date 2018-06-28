#!/usr/bin/env bash
#cd /jmdata/gamecode/jsapp
#git pull origin master
#npm run build
#cd /jmdata/gamecode/ws
#git pull origin development
#chmod 777 ./gradlew
#./gradlew task appjars
##cp -n deploy/qa/nginx.conf /usr/local/nginx/conf/
#rm -rdf /etc/init.d/wsapp
#cp -n deploy/qa/wsapp /etc/init.d/
chmod 777 /etc/init.d/wsapp
#mkdir /jmdata/gamecode/ws/out/config/
#cp -nr deploy/qa/application.properties /jmdata/gamecode/ws/out/config/
##service nginx restart
service wsapp restart
##java -cp "./*" com.geeyao.CreateTestingUserData
