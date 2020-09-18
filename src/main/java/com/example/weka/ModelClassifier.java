package com.example.weka;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.rules.OneR;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.SerializationHelper;


public class ModelClassifier {

    private Attribute age;
    private Attribute finance_type;
    private Attribute loan_type;
    private Attribute married;
    private Attribute net_ammount;
    private Attribute numberofchilderen;
    private Attribute total_ammount;
    private Attribute address;
    private Attribute gender;
    private Attribute income;
    private Attribute status;
    
    

    private ArrayList<Attribute> attributes;
    private ArrayList<String> classVal;
    private Instances dataRaw;
	
	
	/*
    private Attribute petallength;
    private Attribute petalwidth;

    private ArrayList<Attribute> attributes;
    private ArrayList<String> classVal;
    private Instances dataRaw;

*/
    public ModelClassifier() {
    	age = new Attribute("age",(ArrayList<String>)null);
    	finance_type = new Attribute("finance_type",(ArrayList<String>)null);
    	loan_type = new Attribute("loan_type",(ArrayList<String>)null);
    	married = new Attribute("married",(ArrayList<String>)null);
    	net_ammount = new Attribute("net_ammount",(ArrayList<String>)null);
    	numberofchilderen = new Attribute("numberofchilderen",(ArrayList<String>)null);
    	total_ammount = new Attribute("total_ammount",(ArrayList<String>)null);
    	address = new Attribute("address",(ArrayList<String>)null);
    	gender = new Attribute("gender",(ArrayList<String>)null);
    	income = new Attribute("income",(ArrayList<String>)null);
    	status = new Attribute("status",(ArrayList<String>)null);
    	
    	
        attributes = new ArrayList<Attribute>();
        classVal = new ArrayList<String>();
        classVal.add("Yes");
        classVal.add("No");

        attributes.add(age);
        attributes.add(finance_type);
        attributes.add(loan_type);
        attributes.add(married);
        attributes.add(net_ammount);
        attributes.add(numberofchilderen);
        attributes.add(total_ammount);
        attributes.add(address);
        attributes.add(gender);
        attributes.add(income);
        attributes.add(status);

        attributes.add(new Attribute("class", classVal));
        dataRaw = new Instances("TestInstances", attributes, 0);
        dataRaw.setClassIndex(dataRaw.numAttributes() - 1);
    }

    

    public Instances createInstance(String  age , String  finance_type,String  loan_type,String  married,String  net_ammount,String  numberofchilderen,String  total_ammount,String  address ,String  gender ,String  income ,String  status,  double result) {
        dataRaw.clear();
      //  double[] instanceValue1 = new double[]{dataRaw.attribute(0).addStringValue(age), dataRaw.attribute(1).addStringValue(finance_type), dataRaw.attribute(2).addStringValue(loan_type),dataRaw.attribute(3).addStringValue(married),dataRaw.attribute(4).addStringValue(net_ammount),dataRaw.attribute(5).addStringValue(numberofchilderen),dataRaw.attribute(6).addStringValue(total_ammount),dataRaw.attribute(7).addStringValue(address),dataRaw.attribute(8).addStringValue(gender),dataRaw.attribute(9).addStringValue(income),dataRaw.attribute(10).addStringValue(status), 1};
        double[] instanceValue1 = new double[dataRaw.numAttributes()];
      // System.out.println(instanceValue1.length + " ------------" +attributes.size());
        instanceValue1[0] =  dataRaw.attribute(0).addStringValue(age);
        instanceValue1[1] =  dataRaw.attribute(1).addStringValue(finance_type);
        instanceValue1[2] =  dataRaw.attribute(2).addStringValue(loan_type);
        instanceValue1[3] =  dataRaw.attribute(3).addStringValue(married);
        instanceValue1[4] =  dataRaw.attribute(4).addStringValue(net_ammount);
        instanceValue1[5] =  dataRaw.attribute(5).addStringValue(numberofchilderen);
        instanceValue1[6] =  dataRaw.attribute(6).addStringValue(total_ammount);
        instanceValue1[7] =  dataRaw.attribute(7).addStringValue(address);
        instanceValue1[8] =  dataRaw.attribute(8).addStringValue(gender);
        instanceValue1[9] =  dataRaw.attribute(9).addStringValue(income);
        instanceValue1[10] =  dataRaw.attribute(10).addStringValue(status);
        instanceValue1[11] =  0;
        
        dataRaw.add(new DenseInstance(1.0, instanceValue1));
        
        System.out.println(dataRaw);
       
       
        /*double[] instanceValue1 = new double[]{petallength, petalwidth, 0};
        dataRaw.add(new DenseInstance(1.0, instanceValue1));*/
        
        
        
        return dataRaw;
    }


    public String classifiy(Instances insts, String path) {
        String result = "Not classified!!";
        Classifier cls = null;
        try {
            cls = (OneR) SerializationHelper.read(path);
            result = classVal.get((int) cls.classifyInstance(insts.firstInstance()));
        } catch (Exception ex) {
            Logger.getLogger(ModelClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }


    public Instances getInstance() {
        return dataRaw;
    }
    

}
