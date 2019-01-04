package com.gonnect.spark.drools.apis;

import com.gonnect.spark.drools.worker.SparkWorker;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
@Api("APIs to demonstrate Drools for creating data cleansing rules")
public class PersonApprovalApis {


    @PostMapping(value = "/spark")
    public String approval() {

        SparkWorker.doExecuteSparkWithDrools();


        return "Person Approval Spark Job Triggered";
    }

    @GetMapping("/version")
    public String version() {

        return "0.0.1";
    }

}
