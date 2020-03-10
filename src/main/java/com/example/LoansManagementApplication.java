package com.example;


import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
public class LoansManagementApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LoansManagementApplication.class, args);

	}
	

	@Override

	public void run(String... args) throws Exception {

		Scanner newScan = new Scanner(System.in);





		// كيفية ادخال تاريخ
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
		String dateInString = "31-08-1982";
		Date date = sdf.parse(dateInString);
		System.out.println(date);

//		Calendar calendar = Calendar.getInstance();
//		calendar.set(2018, 12, 1);
//		s=calendar.getTime();
//		//d.SetDate(s);
//		System.out.println(s); ||Bank.BankCode.contains("-")
		String s = "-122#";


		if(StringUtils.isNumeric(s)==false ||s.contains("-") )
			System.out.println("o");
		if(s.length()>15 || !s.matches("[@_!#$%^&*()<>?/\\|}{~:]" ))
		{
			System.out.println("s");
		}

		if (StringUtils.isNumeric(s)==false||Float.valueOf(s)<0)
			System.out.println("k");





		}
	}
