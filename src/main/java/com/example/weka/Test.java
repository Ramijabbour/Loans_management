package com.example.weka;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.Loans.LoanService;
import com.example.Loans.Loans;
import com.example.Loans.LoansRepository;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.rules.OneR;
import weka.classifiers.trees.J48;
import weka.core.Debug;
import weka.core.Instance;
import weka.core.Instances;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

@RestController
public class Test {

	@Autowired
	LoanService loanService ;
	
	
    public static final String DATASETPATH = "D:\\okkk\\new3.arff";
    public static final String MODElPATH = "D:\\okkk\\model.bin";

    
	@RequestMapping(method = RequestMethod.GET , value = "/Loans/datamining/{id}")
    public ModelAndView test(@PathVariable int id ) throws Exception {    
		ModelAndView mav = new ModelAndView("Loans/DecisionSupportSystem");
		
		Loans loan  = loanService.getOneByID(id);
		
        ModelGenerator mg = new ModelGenerator();
        try {
        	InstanceQuery query = new InstanceQuery();
        	 query.setUsername("root");
        	 query.setPassword("jad@1234");
        	 query.setQuery("select age,net_ammount,numberofchilderen,total_ammount,income,result from client_loan");
        	 // query.setSparseData(true);
        	 Instances dataset = query.retrieveInstances();         
        	 dataset.setClassIndex(dataset.numAttributes() - 1);

            System.out.println(dataset);
            
       //  Instances dataset = mg.loadDataset(DATASETPATH);
       // System.out.println(dataset);
      //  Filter filter = new Normalize();

        // divide dataset to train dataset 80% and test dataset 20%
       // int trainSize = (int) Math.round(dataset.numInstances() * 0.8);
        //int testSize = dataset.numInstances() - trainSize;

        dataset.randomize(new Debug.Random(1));// if you comment this line the accuracy of the model will be droped from 96.6% to 80%
        
        //Normalize dataset
      //  filter.setInputFormat(dataset);
       // Instances datasetnor = Filter.useFilter(dataset, filter);

      //  Instances traindataset = new Instances(datasetnor, 0, trainSize);
       // Instances testdataset = new Instances(datasetnor, trainSize, testSize);

        // build classifier with train dataset             
       // OneR ann = (OneR) mg.buildClassifier(traindataset);
    	J48 tree = new J48();
		tree.buildClassifier(dataset);
        
        // Evaluate classifier with test dataset
        String evalsummary = mg.evaluateModel(tree, dataset, dataset);
		//mav.addObject("evalsummary",evalsummary);

        System.out.println("Evaluation: " + evalsummary);
        System.out.println();
        System.out.println(tree.toString());
        mav.addObject("evalsummary",tree.toString());

        //Save model 
        mg.saveModel(tree, MODElPATH);
       //classifiy a single instance 
       boolean give =false ,dontGive =true ;
        
        ModelClassifier cls = new ModelClassifier();
        double age =Double.parseDouble(loan.getClient().getAge());
        double netAmmount = Double.parseDouble(loan.getNetAmmount());
        double totalammount = Double.parseDouble(loan.TotalAmmount);
        double income = Double.parseDouble(loan.getClient().getIncome());
        double numberofchildren = Double.parseDouble(loan.getClient().getNumberOFChilderen());
        

        String classname =cls.classifiy(cls.createInstance(age, netAmmount,numberofchildren,totalammount,income, 0), MODElPATH);
       // String classname =cls.classifiy(cls.createInstance("overcast",83,86,"false", 1), MODElPATH);
        System.out.println("\n The class name for the instance is  " +classname); 

        mav.addObject("loan", loan);
        mav.addObject("classname", classname);

        if(classname.equalsIgnoreCase("yes"))
        	{
        		give = true ;
        		dontGive=false ; 
        	}
      
       
        mav.addObject("dontGive", dontGive);
        mav.addObject("give", give);
        
      }
        catch (Exception e) {
            e.printStackTrace();
        }
		return mav;
    }


	@Autowired 
	LoansRepository loanrepo ; 
	@Autowired
	LoanService service ; 
	@RequestMapping(method = RequestMethod.GET, value="/update11")
	public void UpdateLoan() throws IOException {
		List<Loans> allloan = loanrepo.findAll();
		for(Loans l : allloan)
		{
			if(l.getStatus().equalsIgnoreCase("مؤكدة"))
			{
				l.setConfirmed(true);
				service.updateLoan(l);
			}
		}
	}
}
