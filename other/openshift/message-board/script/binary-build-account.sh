# Compile && Test
mvn -f ../account-service/pom.xml clean install

cp -f ../account-service/target/account-service.war ./account-service

oc start-build account-service --from-dir=./account-service --follow