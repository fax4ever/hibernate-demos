# Compile && Test
mvn -f ../message-service/pom.xml clean install

cp -f ../message-service/target/message-service.war ./message-service

oc start-build message-service --from-dir=./message-service --follow