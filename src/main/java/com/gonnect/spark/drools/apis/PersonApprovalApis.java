package com.gonnect.spark.drools.apis;

import com.gonnect.spark.drools.SparkDroolsAsServiceApp;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequestMapping("/persons")
@Api("APIs to demonstrate Drools for creating data cleansing rules")
public class PersonApprovalApis {


    @PostMapping(value = "/spark")
    public String approval() {

        SparkDroolsAsServiceApp.doExecuteSparkWithDrools();


        return "Person Approval Spark Job Triggered";
    }

    @GetMapping("/version")
    public String version() {

        return "0.0.1";
    }

}
