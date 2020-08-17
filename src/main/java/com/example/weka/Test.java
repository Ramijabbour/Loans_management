package com.example.weka;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import weka.classifiers.rules.OneR;
import weka.core.Debug;
import weka.core.Instances;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

@RestController
public class Test {

    public static final String DATASETPATH = "C:\\Users\\ramij\\Downloads\\Compressed\\weka-example-master\\data\\iris.2D.arff";
    public static final String MODElPATH = "C:\\Users\\ramij\\Downloads\\Compressed\\weka-example-master\\data\\model.bin";

	@RequestMapping(method = RequestMethod.GET , value = "/test1")
    public static void test() throws Exception {    
        ModelGenerator mg = new ModelGenerator();
      /*  try {
        	InstanceQuery query = new InstanceQuery();
        	 query.setUsername("root");
        	 query.setPassword("admin");
        	 query.setQuery("select * from client_loan");
        	 // query.setSparseData(true);
        	 Instances dataset = query.retrieveInstances();         
        	 dataset.setClassIndex(dataset.numAttributes() - 2);

            System.out.println(dataset);*/
            
        Instances dataset = mg.loadDataset(DATASETPATH);
        Filter filter = new Normalize();

        // divide dataset to train dataset 80% and test dataset 20%
        int trainSize = (int) Math.round(dataset.numInstances() * 0.8);
        int testSize = dataset.numInstances() - trainSize;

        dataset.randomize(new Debug.Random(1));// if you comment this line the accuracy of the model will be droped from 96.6% to 80%
        
        //Normalize dataset
        filter.setInputFormat(dataset);
        Instances datasetnor = Filter.useFilter(dataset, filter);

        Instances traindataset = new Instances(datasetnor, 0, trainSize);
        Instances testdataset = new Instances(datasetnor, trainSize, testSize);

        // build classifier with train dataset             
        OneR ann = (OneR) mg.buildClassifier(traindataset);

        // Evaluate classifier with test dataset
        String evalsummary = mg.evaluateModel(ann, traindataset, testdataset);
        System.out.println("Evaluation: " + evalsummary);
        System.out.println();
        

        //Save model 
        mg.saveModel(ann, MODElPATH);
       //classifiy a single instance 
        ModelClassifier cls = new ModelClassifier();
        String classname =cls.classifiy(Filter.useFilter(cls.createInstance(1.6, 0.2, 0), filter), MODElPATH);
        System.out.println("\n The class name for the instance with petallength = 1.6 and petalwidth =0.2 is  " +classname);
        /*}
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
