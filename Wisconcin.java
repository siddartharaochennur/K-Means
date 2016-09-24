import java.math.*;
import java.util.*;
import java.util.*;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


public class Wisconcin {
	double a,b,c,d,e,f,g,h,i,j,k;

	ArrayList<Wisconcin> restdata ;
	ArrayList<ArrayList<Wisconcin>> withCentroid;
	ArrayList<Wisconcin> centroids;
	ArrayList<Wisconcin> newcentroids;
	HashMap<Wisconcin,List<Wisconcin>> hm=new HashMap<Wisconcin,List<Wisconcin>>();
	
	Wisconcin D ;
	int num_cluster;
	
	
	public void cluster(){
		 withCentroid = new ArrayList<ArrayList<Wisconcin>>(this.num_cluster);
		 for(int i = 0;i<this.num_cluster;i++){
		 withCentroid.add(new ArrayList<Wisconcin>());
		 }
		 //System.out.println(withCentroid.size());
	}
	
	public void centroids(){
		
		newcentroids = new ArrayList<Wisconcin>(this.num_cluster);
		centroids = new ArrayList<Wisconcin>(this.num_cluster);
		
		for(int i=0;i<this.num_cluster;i++){
			centroids.add(restdata.get(randInt(0,restdata.size())));
		}
	}
	
	
	public void rest() throws Exception{
		try{
			restdata = new ArrayList<Wisconcin>();
			
		MongoClient mongoclient = new MongoClient("localhost",27017);
		DB db = mongoclient.getDB("Homework2");
		DBCollection coll = db.getCollection("zerodata");
		System.out.println(coll);
		BasicDBObject query1 =  new BasicDBObject();
		query1.put("g", new BasicDBObject("$gt", 0).append("$lt", 11));
		DBCursor cursor2 = coll.find(); 
		
		while (cursor2.hasNext()) {
			
		    DBObject obj = cursor2.next();
		    restdata.add(new Wisconcin((Integer) obj.get("a"),(Integer) obj.get("b"),(Integer) obj.get("c"),(Integer) obj.get("d"),(Integer) obj.get("e"),(Integer) obj.get("f"),(Integer) obj.get("g"),(Integer) obj.get("h"),(Integer) obj.get("i"),(Integer) obj.get("j"),(Integer) obj.get("k")));
		    //System.out.println(restdata.size());
		}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	
	public Wisconcin(double a,double b,double c,double d,double e,double f,double g,double h,double i,double j,double k){
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
		this.g = g;
		this.h = h;
		this.i = i;
		this.j = j;
		this.k = k;
		
	}
	
	public double distance1(Wisconcin centroid,Wisconcin data){
		double temp =  Math.pow(centroid.b - data.b ,2) + Math.pow(centroid.c - data.c ,2) + Math.pow(centroid.d - data.d ,2) + Math.pow(centroid.e - data.e ,2) + Math.pow(centroid.f - data.f ,2) + Math.pow(centroid.g - data.g ,2) + Math.pow(centroid.h - data.h ,2) + Math.pow(centroid.i - data.i ,2) + Math.pow(centroid.j - data.j ,2);
		double dist = Math.sqrt(temp);

		return dist;
	}
	
	public static int randInt(int min, int max) {


	    Random rand = new Random();

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	


	//input two centroids each time recursion happens
	public HashMap<Wisconcin,List<Wisconcin>> compare(){
		//double dd3 = distance1(cc1,cc2);
		//System.out.println("distance between centroids in this cluster : " + dd3);
		
		//public ArrayList<Wisconcin> compare(Wisconcin cc1, Wisconcin cc2){

			cluster();
		assignCentroid();
		// adding points formed and their corresponding centroids to hashmap
		for(int i=0;i<this.num_cluster;i++)
		hm.put(centroids.get(i), withCentroid.get(i));
		
		//forming new centroids
		newCentroids();
		
		
		// distance between new and old centroids
		ArrayList<Double> centroidDistance = new ArrayList<Double> (this.num_cluster);
		for(int i=0;i<this.num_cluster;i++)
		centroidDistance.add(distance1(centroids.get(i), newcentroids.get(i)));
		
		
		
		//convergence check
		//if(dd1+dd2>0.01){
		//	return compare(newCentroid1,newCentroid2);
			//}
		//or
		double sumDistance = 0;
		for(double d : centroidDistance){
			sumDistance = d + sumDistance;	
		}

		if(sumDistance > 0.01){	
			withCentroid.clear();
			centroids.clear();
			for(int i = 0; i<this.num_cluster;i++){
				centroids.add(newcentroids.get(i));
			}
			newcentroids.clear();
			hm.clear();
			return compare();
			}
		
		else{
			return hm;
		}	
	}
	
	public void assignCentroid(){
		double max = 1000000000;
		double min = max;

		for(Wisconcin i : restdata){
			min = max;
			int which = 0;
			for(int j=0;j<this.num_cluster;j++){
				Wisconcin cluster = centroids.get(j);
				double dist = distance1(cluster,i);
		// comparing distance from each centroids and handling ties
			if(dist<min){
				min = dist;
				which = j;
			}
			}
			withCentroid.get(which).add(i);
		}		
	}
	public void newCentroids(){
		
		for(ArrayList<Wisconcin> oneEach : withCentroid){
			int size = oneEach.size();
			double b1=0,c1=0,d1=0,e1=0,f1=0,h1=0,i1=0,j1=0,g1=0 ;
		for(Wisconcin i : oneEach){
			b1 = b1 + i.b;
			c1 = c1 + i.c;
			d1 = d1 + i.d;
			e1 = e1 + i.e;
			f1 = f1 + i.f;
			h1 = h1 + i.h;
			i1 = i1 + i.i;
			j1 = j1 + i.j;
			g1 = g1 + i.g;
		}
		b1=b1/size;
		c1=c1/size;
		d1=d1/size;
		e1=e1/size;
		f1=f1/size;
		g1=g1/size;
		h1=h1/size;
		i1=i1/size;
		j1=j1/size;
		
		newcentroids.add(new Wisconcin(0,b1,c1,d1,e1,f1,g1,h1,i1,j1,0));
		}

	}
	public Wisconcin(int num){
		this.num_cluster = num;

	}
}
