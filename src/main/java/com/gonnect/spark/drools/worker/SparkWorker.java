package com.gonnect.spark.drools.worker;

import com.gonnect.spark.drools.model.Person;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * A Spark worker class. This class creates Spark job and also Drools artifacts
 */
public class SparkWorker {

    public static void doExecuteSparkWithDrools() {

        //Setup data
        List<Person> anInputData = getInputData();
        //Use all cores
        SparkConf sparkConf = new SparkConf()
                                .setAppName("Spring Boot + Spark Job + Drools Application")
                                .setMaster("local[*]");
        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);


        Broadcast<KieBase> broadcastedRules = sparkContext.broadcast(loadRules());

        //Spark work starts here
        JavaRDD<Person> persons = sparkContext.parallelize(anInputData);

        long numberOfPersonsApproved = persons.map(aPerson -> doExecuteRules(broadcastedRules.value(), aPerson))
                .filter(aPerson -> aPerson.isApproved()) // apply drools rules
                .count();  //First Action Performed on Spark - lazy loading

        System.out.println("Number of persons approved: " + numberOfPersonsApproved);
        persons.saveAsTextFile("com.gonnect.spark.drools.output"); // 2nd Action is Performed on this Job to Save the File.
        sparkContext.close();
    }

    //~~ Helper functions

    /**
     * Load the Rules (PersonApprovalRule.drl) in the container
     */
    private  static KieBase loadRules() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        return kieContainer.getKieBase();
    }

    /**
     * Fire/apply the rules if the credit score > 600
     */
    private static Person doExecuteRules(KieBase kieBase, Person aPerson) {
        StatelessKieSession session = kieBase.newStatelessKieSession();
        session.execute(aPerson);
        return aPerson;
    }

    private static List<Person> getInputData() {
        return asList(
                new Person(1, "firstName1", "lastName1", 10000, 568),
                new Person(2, "firstName2", "lastName2", 12000, 654),
                new Person(3, "firstName3", "lastName3", 100, 568),
                new Person(4, "firstName4", "lastName4", 1000000, 788),
                new Person(5, "firstName5", "lastName5", 10, 788),
                new Person(6, "firstName6", "lastName6", 34000, 900),
                new Person(7, "firstName7", "lastName7", 12000, 457),
                new Person(8, "firstName8", "lastName8", 10000, 300),
                new Person(9, "firstName9", "lastName9", 20000, 721),
                new Person(10, "firstName10", "lastName10", 30000, 890),
                new Person(11, "firstName11", "lastName11", 50000, 490),
                new Person(12, "firstName12", "lastName12", 60000, 690),
                new Person(13, "firstName13", "lastName13", 25000, 390),
                new Person(14, "firstName14", "lastName14", 80000, 290)

        );
    }
}