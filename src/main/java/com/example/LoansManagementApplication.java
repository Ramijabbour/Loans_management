package com.example;


import ValidContent_Visitor.Valid_Visitable;
import com.BackUpDatabase.DBAutoBackupController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LoansManagementApplication implements CommandLineRunner {


    final static String queueName = "message_queue";

    public static void main(String[] args) throws InterruptedException {

        SpringApplication.run(LoansManagementApplication.class, args);

        
        
    }


    @Override

    public void run(String... args) throws Exception {

    	//
        //اختبار ال visitor
    	//Valid_Visitable valid_visitable = new Valid_Visitable();
    	//tt t = new tt();
    	//t.setTotal("1ahmad  attaR");
    	//t.accept(valid_visitable);


        // كيفية ادخال تاريخ
       /* SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        String dateInString = "31-08-1982";
        Date date = sdf.parse(dateInString);
        System.out.println(date);
        */
    	System.out.println("Loans Management is online continue to essential data injection");
//        DBAutoBackupController dbAutoBackupController = new DBAutoBackupController();
//        dbAutoBackupController.run();
    }
}

