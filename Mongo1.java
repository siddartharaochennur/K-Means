import java.util.*;





public class Mongo1 {
	
	
	
	public static void main(String[] args) throws Exception {
		int iteration = 1;
		double TotErrRate = 0;
		while(iteration<21){
			//give cluster value here
		Wisconcin H = new Wisconcin(2);
		H.rest();
		//H.cluster();
		H.centroids();
		HashMap A = H.compare();
		
		// Get a set of the entries
	      Set set = A.entrySet();
	      
	      // Get an iterator
	      Iterator i = set.iterator();
	      // Display elements

	      ArrayList<ArrayList<Wisconcin>> ab = new ArrayList<ArrayList<Wisconcin>>();
	      while(i.hasNext()) {
	         Map.Entry me = (Map.Entry)i.next();
	         ab.add((ArrayList<Wisconcin>) me.getValue());
	      }
	      int v = 0;
	      int [] benign = new int[H.num_cluster];
	      int [] malignant = new int[H.num_cluster];
	      for (ArrayList<Wisconcin> list : ab)  
	      { 
	    	  for(Wisconcin each : list){
	    		  if(each.k == 2){
	    			  benign[v] = benign[v] + 1;
	    		  }else if(each.k == 4){
	    			  malignant[v] = malignant[v] + 1;
	    		  }
	    	  }
	    	  v = v+1;  
	      }
	      for(int clust=0;clust<H.num_cluster;clust++){
	    	  System.out.println("This is Cluster " + (clust + 1));
	    	  System.out.println("number of benign points are " + benign[clust]);
	    	  System.out.println("number of malignant points are " + malignant[clust]);
	      }
	      
	    //Error rates
	      double [] errrate = new double[H.num_cluster];
	      for(int err =0;err<H.num_cluster;err++){
	    	  if(benign[err] > malignant[err]){
	    	  //System.out.println("cluster " + (err+1));
	    	  errrate[err]=(double) malignant[err]/(double)(benign[err] + malignant[err]);
	    	  System.out.println("error rate " + errrate[err]);
	    	  }
	    	  else if(benign[err] == 0 && malignant[err] == 0){
	    		  System.out.println("SAY HELLOOOOOOOOOOOOOOOOOOOOOOOOOO");
	    	  }
	    	  else{
	    		  //System.out.println("cluster " + (err+1));
	    		  errrate[err]=(double) benign[err]/(double)(benign[err] + malignant[err]);
		    	  System.out.println("error rate " + errrate[err]);
	    	  }
	    	  
	      }
	      for(double o : errrate){
	    	  TotErrRate = TotErrRate + o;
	      }
	      System.out.println(TotErrRate);
	      iteration =iteration +1;
		}
		System.out.println( "Total Err Rate = " + TotErrRate + " ; for iterations = " + (iteration-1));
		System.out.println( " Avg Error Rate " +  (TotErrRate)/(iteration-1));
	      
	      
		
		
	      

		
		
		
		
		
		
		
		
		}
		

}

