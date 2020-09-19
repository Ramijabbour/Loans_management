package com.example.weka;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.rules.OneR;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;


public class ModelClassifier {

    private Attribute age;
   // private Attribute finance_type;
    //private Attribute loan_type;
    //private Attribute married;
    private Attribute net_ammount;
    private Attribute numberofchilderen;
    private Attribute total_ammount;
    //private Attribute address;
    //private Attribute gender;
    private Attribute income;
   // private Attribute status;
    
    

    private ArrayList<Attribute> attributes;
    private ArrayList<String> classVal;
    private Instances dataRaw;
	
	/*
	 	private Attribute outlook;
	    private Attribute temperature;
	    private Attribute humidity;
	 	private Attribute windy;
	    
	    

	    private ArrayList<Attribute> attributes;
	    private ArrayList<String> classVal;
	    private Instances dataRaw;
	*/
	
	
	/*
    private Attribute petallength;
    private Attribute petalwidth;

    private ArrayList<Attribute> attributes;
    private ArrayList<String> classVal;
    private Instances dataRaw;

*/
    public ModelClassifier() {
    	age = new Attribute("age");
    //	finance_type = new Attribute("finance_type",(ArrayList<String>)null);
    //	loan_type = new Attribute("loan_type",(ArrayList<String>)null);
    //	married = new Attribute("married",(ArrayList<String>)null);
    	net_ammount = new Attribute("net_ammount");
    	numberofchilderen = new Attribute("numberofchilderen");
    	total_ammount = new Attribute("total_ammount");
    //	address = new Attribute("address",(ArrayList<String>)null);
    //	gender = new Attribute("gender",(ArrayList<String>)null);
    	income = new Attribute("income");
    	//status = new Attribute("status",(ArrayList<String>)null);
    	
    	
        attributes = new ArrayList<Attribute>();
        classVal = new ArrayList<String>();
        classVal.add("Yes");
        classVal.add("No");

        attributes.add(age);
      //  attributes.add(finance_type);
       // attributes.add(loan_type);
        //attributes.add(married);
        attributes.add(net_ammount);
        attributes.add(numberofchilderen);
        attributes.add(total_ammount);
        //attributes.add(address);
        //attributes.add(gender);
        attributes.add(income);
        //attributes.add(status);

        attributes.add(new Attribute("class", classVal));
        dataRaw = new Instances("TestInstances", attributes, 0);
        dataRaw.setClassIndex(dataRaw.numAttributes() - 1);
    	
    	
     /*
    	outlook = new Attribute("outlook",(ArrayList<String>)null);
    	temperature = new Attribute("temperature");
    	humidity = new Attribute("humidity");
    	windy = new Attribute("windy",(ArrayList<String>)null);
    
    	
        attributes = new ArrayList<Attribute>();
        classVal = new ArrayList<String>();
        classVal.add("Yes");
        classVal.add("No");

        attributes.add(outlook);
        attributes.add(temperature);
        attributes.add(humidity);
        attributes.add(windy);

        attributes.add(new Attribute("class", classVal));
        dataRaw = new Instances("TestInstances", attributes, 0);
        dataRaw.setClassIndex(dataRaw.numAttributes() - 1);
        
*/
    	
    }

    
/*
    public Instances createInstance(int  age , String  finance_type,String  loan_type,String  married,double net_ammount,int  numberofchilderen,int  total_ammount,String  address ,String  gender ,int  income ,String  status,  double result) {
        dataRaw.clear();
      //  double[] instanceValue1 = new double[]{dataRaw.attribute(0).addStringValue(age), dataRaw.attribute(1).addStringValue(finance_type), dataRaw.attribute(2).addStringValue(loan_type),dataRaw.attribute(3).addStringValue(married),dataRaw.attribute(4).addStringValue(net_ammount),dataRaw.attribute(5).addStringValue(numberofchilderen),dataRaw.attribute(6).addStringValue(total_ammount),dataRaw.attribute(7).addStringValue(address),dataRaw.attribute(8).addStringValue(gender),dataRaw.attribute(9).addStringValue(income),dataRaw.attribute(10).addStringValue(status), 1};
        double[] instanceValue1 = new double[dataRaw.numAttributes()];
      // System.out.println(instanceValue1.length + " ------------" +attributes.size());
        instanceValue1[0] =  age;
        instanceValue1[1] =  dataRaw.attribute(1).addStringValue(finance_type);
        instanceValue1[2] =  dataRaw.attribute(2).addStringValue(loan_type);
        instanceValue1[3] =  dataRaw.attribute(3).addStringValue(married);
        instanceValue1[4] =  net_ammount;
        instanceValue1[5] =  numberofchilderen;
        instanceValue1[6] =  total_ammount;
        instanceValue1[7] =  dataRaw.attribute(7).addStringValue(address);
        instanceValue1[8] =  dataRaw.attribute(8).addStringValue(gender);
        instanceValue1[9] =  income;
        instanceValue1[10] =  dataRaw.attribute(10).addStringValue(status);
        instanceValue1[11] =  0;
        
        dataRaw.add(new DenseInstance(1.0, instanceValue1));
        
        System.out.println(dataRaw);
       
       
        /*double[] instanceValue1 = new double[]{petallength, petalwidth, 0};
        dataRaw.add(new DenseInstance(1.0, instanceValue1));*/
     /*           
        return dataRaw;
    }
    */
    

    public Instances createInstance(double  age,double net_ammount,double  numberofchilderen,double  total_ammount ,double  income ,  double result) {
     
    	   dataRaw.clear();
           double[] instanceValue1 = new double[]{age, net_ammount,numberofchilderen,total_ammount,income, 0};
           dataRaw.add(new DenseInstance(1.0, instanceValue1));
           System.out.println(dataRaw);
           return dataRaw;
    }


    /*
    public Instances createInstance(String  outlook , double temp  ,double   hum,String  windy,double reuslt) {
    dataRaw.clear();
    
    double[] instanceValue1 = new double[dataRaw.numAttributes()];
    
    
    instanceValue1[0] = dataRaw.attribute(0).addStringValue(outlook);    
    instanceValue1[1] =  temp;
    instanceValue1[2] =  hum;
    instanceValue1[3] =  dataRaw.attribute(3).addStringValue(windy);
    
    instanceValue1[4] =  reuslt;
    
    dataRaw.add(new DenseInstance(1.0, instanceValue1));
    
    System.out.println(instanceValue1[0]+" --------------------++++++++++++++++++");
    System.out.println();
    System.out.println();
    System.out.println(dataRaw);
   
    
    
    return dataRaw;
}
    */
    

    public String classifiy(Instances insts, String path) throws Exception {
        String result = "Not classified!!";
        Classifier cls = null;        
        try {
            cls = (J48) SerializationHelper.read(path);
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
