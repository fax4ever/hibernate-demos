# Compile & Build
mvn -f ../message-service/pom.xml exec:exec -pl .

oc start-build message-board-web --from-dir=./message-board-web --follow