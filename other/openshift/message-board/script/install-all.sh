# Compile && Test
mvn -f ../pom.xml clean install

# Create binary build distribution
sh ./create-binary-build-directory.sh

# Create project
oc new-project message-board

# Install Infinispan Server
# Infinispan template taken from here: https://github.com/infinispan/infinispan-openshift-templates
oc create -f ../template/infinispan-persistent.json
oc new-app --template=infinispan-persistent

# Install MySql Server
oc new-app --template=mysql-persistent -e MYSQL_USER=messageboard MYSQL_PASSWORD=redhat MYSQL_ROOT_PASSWORD=hibernate MYSQL_DATABASE=account

# Install Message MicroService
oc import-image my-jboss-eap-7/eap71-openshift --from=registry.access.redhat.com/jboss-eap-7/eap71-openshift --confirm
oc new-app --image-stream=eap71-openshift:latest~./nocontent --name=message-service
oc start-build message-service --from-dir=./message-service --follow
oc expose svc/message-service

# Install Account MicroService
oc new-app --image-stream=eap71-openshift:latest~./nocontent --name=account-service
oc start-build account-service --from-dir=./account-service --follow
oc expose svc/account-service

oc get routes