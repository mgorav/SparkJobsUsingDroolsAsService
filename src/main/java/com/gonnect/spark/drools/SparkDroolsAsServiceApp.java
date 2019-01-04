/**
 *
 */
package com.gonnect.spark.drools;

import java.util.Arrays;

/**
 * @author prakrish
 *
 */

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SparkDroolsAsServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(SparkDroolsAsServiceApp.class, args);
    }


    public static void doExecuteSparkWithDrools() {

        //Setup data
        List<Person> inputData = Arrays.asList(
                new Person(1, "firstNam21", "lastName1", 10000, 568),
                new Person(2, "firstName2", "lastName2", 12000, 654),
                new Person(3, "firstName3", "lastName3", 100, 568),
                new Person(4, "firstName4", "lastName4", 1000000, 788),
                new Person(5, "firstName5", "lastName5", 10, 788),
                new Person(6, "firstName6", "lastName6", 34000, 900),
                new Person(7, "firstName7", "lastName7", 12000, 457),
                new Person(8, "firstName8", "lastName8", 10000, 300),
                new Person(9, "firstName9", "lastName9", 20000, 721),
                new Person(10, "firstName10", "lastName10", 25000, 590)
        );
        //Use Max cores
        SparkConf conf = new SparkConf().setAppName("Simple Application").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);


        KieBase rules = loadRules();
        Broadcast<KieBase> broadcastRules = sc.broadcast(rules);

        //Spark work starts here
        JavaRDD<Person> applicants = sc.parallelize(inputData);

        long numApproved = applicants.map(a -> applyRules(broadcastRules.value(), a))
                .filter(a -> a.isApproved()) // apply drools rules
                .count();  //First Action Performed on Spark - lazy loading

        System.out.println("Number of persons approved: " + numApproved);
        applicants.saveAsTextFile("com.gonnect.spark.drools.output"); // 2nd Action is Performed on this Job to Save the File.
        sc.close();
    }

    /**
     * Load the Rules (PersonApprovalRule.drl) in the container
     */
    public static KieBase loadRules() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        return kieContainer.getKieBase();
    }


    /**
     * Fire/apply the rules if the credit score > 600
     */
    public static Person applyRules(KieBase base, Person a) {
        StatelessKieSession session = base.newStatelessKieSession();
        session.execute(a);
        return a;
    }
}
