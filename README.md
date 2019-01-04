Spark Job with Drools As Service

A micro service which trigger a Spark Job which utilizes Drools rules
engine. The rules are distributed to all the worker nodes of Spark using
Broadcast variables.

Compile & Test:
mvn clean install

Run
java -jar target/spark-drools.jar