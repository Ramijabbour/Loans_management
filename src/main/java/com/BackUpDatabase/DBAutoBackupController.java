	package com.BackUpDatabase;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableScheduling
public class DBAutoBackupController implements CommandLineRunner {


    @Override
    @Scheduled(cron = "0 30 1 * * ?")
    public void run(String... args) throws Exception {

        System.out.println("Backup Started at " + new Date());

        Date backupDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String backupDateStr = format.format(backupDate);
        String dbNameList = "client_1 client_2";

        String fileName = "Daily_DB_Backup";
        String folderPath = "C:\\home";
        File f1 = new File(folderPath);
        f1.mkdir();

        String saveFileName = fileName + "_" + backupDateStr + ".sql";
        String savePath = filePath + File.separator + saveFileName;

        String executeCmd = "mysqldump -u " + dbUserName + " -p" + dbUserPassword + "  --databases " + dbNameList
                + " -r " + savePath;

        Process runtimeProcess = null;
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int processComplete = 0;
        try {
            processComplete = runtimeProcess.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (processComplete == 0) {
            System.out.println("Backup Complete at " + new Date());
        } else {
            System.out.println("Backup Failure");
        }
    }
}
