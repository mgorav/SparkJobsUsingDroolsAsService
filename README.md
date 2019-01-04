**Spark Job with Drools As A Service**

A micro service which trigger a Spark Job which utilizes Drools rules
engine. The rules are distributed to all the worker nodes of Spark using
Broadcast variables.

**_Compile & Test:_**

mvn clean install

**_Run_**

java -jar target/spark-drools.jar

_**Swagger API**_

http://localhost:8080/swagger-ui.html

_**Deep Learning Data Engineering Architecture**_

Deep Learning Data Engineering Architecture - The idea behind this architecture is to create data engineering platform, which has a capabilities to learn day-by-day all data quality rules. The genesis of such architecture starts by adding data quality rules in the Rule Engine (like Drools) and stream the data being rejected into data rejection topic (Kafka/Kinesis etc). This data rejection topic act as input layer to CNN which learns data quality rules being applied and this learning is feed back to the Rules Engine. So in this style of architecture, the learning model act like a "Data Quality. Engineer", which is learning about the data quality rules and providing the feedback. So the moral such architecture  - "treat CNN/Deeplearning model as your employee and feed them with correct information the same as we provide to human engineer" This is shown pictorially below

![alt text](./SelfDeepLearning.png)