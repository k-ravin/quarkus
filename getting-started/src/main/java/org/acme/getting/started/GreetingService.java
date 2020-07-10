package org.acme.getting.started;

import javax.enterprise.context.ApplicationScoped;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.*;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.*;
import java.io.*;
//import java.io.*;
@ApplicationScoped
public class GreetingService {
 DoccatModel model;
    static int positive = 0;
    static int negative = 0;
    
    public String greeting(String name) {
        GreetingService g=new GreetingService();
        
        File directory = new File("./");
        g.test();
   System.out.println(directory.getAbsolutePath());
        return "hello " + name;

        
        
    }
public void test(){
String line = "";
        GreetingService twitterCategorizer = new GreetingService();
        twitterCategorizer.trainModel();

        String pqrs="Q: Who do you think should be the next president of the United States? <br> A: Umm... Barack Obama 4 another 4 years! iLovehim! <br> Hey im ashlee im tired of everyone commenting on my damn formspring talking shit";
        String[] qss=pqrs.split("<br>");
        for(int i=0;i<qss.length;i++)
        {
        int q=twitterCategorizer.classifyNewTweet(qss[i]);
        System.out.println(qss[i]+":\t"+q);
        }
    }
        public void trainModel() {
      InputStreamFactory dataIn;
        try {            
        dataIn = new InputStreamFactory() {
            public InputStream createInputStream() throws IOException {
                return new FileInputStream("./projects/quarkus/getting-started/target/input.txt");
            }
        };
            ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            ObjectStream sampleStream = new  DocumentSampleStream(lineStream);
            // Specifies the minimum number of times a feature must be seen
            
             TrainingParameters params = new TrainingParameters();
 params.put(TrainingParameters.ITERATIONS_PARAM, 30);
 params.put(TrainingParameters.CUTOFF_PARAM, 2);

            model = DocumentCategorizerME.train("en",sampleStream,
                                params,                            
                    new DoccatFactory());
                   
                                
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            /*if (dataIn != null) {
                try {
                    dataIn.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }*/
            //}
        }
    }

    public int classifyNewTweet(String tweet)  {
        String[] tokens={tweet};
        
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
        double[] outcomes = myCategorizer.categorize(tokens);
        String category = myCategorizer.getBestCategory(outcomes);

//DocumentCategorizerME myCategorizer = new DocumentCategorizerME(doccatModel);
		//double[] outcomes = myCategorizer.categorize(summary);
		//String category = myCategorizer.getBestCategory(outcomes);


       /* public double[] classifyMessageOpenNLP(myCategorizer, tweet) {
	double[] outcomes = classifier.categorize(tweet);
	String category = classifier.getBestCategory(outcomes);

	System.out.println("Open nlp classified as " + category + " Threshold: bad=" + outcomes[1] + ", good=" + outcomes[0]);
	return outcomes;
}
 */
//File f=new File("C:\\Users\\Administrator\\Desktop\\results2.csv");

//if(!f.exists()) f.createNewFile();

        //BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        //bw.newLine();
        //bw.write("Sentence,Outcomes,category");
       System.out.println(tweet+","+outcomes+","+category);
       //bw.newLine();
       //bw.write(tweet+","+outcomes+","+category);
        //bw.newLine();
        //bw.write("Negative Tweets," + negative);
        //bw.close();


        System.out.print("-----------------------------------------------------\nTWEET :" + tweet + " ===> ");
        if (category.equalsIgnoreCase("1")) {
            System.out.println("POSITIVE");
            return 1;
        } else {
            System.out.println(" NEGATIVE ");
            return 0;
        }
    }


}