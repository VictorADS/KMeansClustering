package algorithms;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
public class KMeans {
 
    private int NUM_CLUSTERS = 5;    
    //Min and Max X and Y
    private ArrayList<Point> points;
    private ArrayList<Cluster> clusters;
    
    public KMeans(ArrayList<Point> list) {
    	this.points = list;
    	this.clusters = new ArrayList<>();    	
    }
    
    //Initializes the process
    public void init() {
    	Random r = new Random();
    	for (int i = 0; i < NUM_CLUSTERS; i++) {
    		Cluster cluster = new Cluster(i);
    		Point randPoint = points.get(r.nextInt(points.size()));
    		PointDouble centroid = new PointDouble(randPoint);
    		cluster.setCentroid(centroid);
    		clusters.add(cluster);
    	}
    	
    }
 
    public ArrayList<Cluster> calculate() {
        boolean finish = false;
        int iteration = 0;
        
        while(!finish) {
        	clearClusters();
        	
        	ArrayList<PointDouble> lastCentroids = getCentroids();
        	
        	assignCluster();
            
        	calculateCentroids();
        	
        	iteration++;
        	
        	ArrayList<PointDouble> currentCentroids = getCentroids();
        	
        	double distance = 0;
        	for(int i = 0; i < lastCentroids.size(); i++) {
        		distance += PointDouble.distance(lastCentroids.get(i),currentCentroids.get(i));
        	}
        	System.out.println("#################");
        	System.out.println("Iteration: " + iteration);
        	System.out.println("Centroid distances: " + distance);
        	        	
        	if(distance == 0) {
        		finish = true;
        	}
        }
        return clusters;
    }
    
    private void clearClusters() {
    	for(Cluster cluster : clusters) {
    		cluster.clear();
    	}
    }
    
    private ArrayList<PointDouble> getCentroids() {
    	ArrayList<PointDouble> centroids = new ArrayList<>(NUM_CLUSTERS);
    	for(Cluster cluster : clusters) {
    		PointDouble aux = cluster.getCentroid().clone();
    		centroids.add(aux);
    	}
    	return centroids;
    }
    
    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min = max; 
        int cluster = 0;                 
        double distance = 0.0; 
        
        for(Point point : points) {
        	min = max;
            for(int i = 0; i < NUM_CLUSTERS; i++) {
            	Cluster c = clusters.get(i);
                distance = PointDouble.distance(point, c.getCentroid());
                if(distance < min){
                    min = distance;
                    cluster = i;
                }
            }
            clusters.get(cluster).addPoint(point);
        }
    }
    
    private void calculateCentroids() {
        for(Cluster cluster : clusters) {
            double sumX = 0;
            double sumY = 0;
            ArrayList<Point> list = cluster.getPoints();
            int n_points = list.size();
            
            for(Point point : list) {
            	sumX += point.getX();
                sumY += point.getY();
            }
            
            PointDouble centroid = cluster.getCentroid();
            if(n_points > 0) {
            	double newX = sumX / n_points;
            	double newY = sumY / n_points;
                centroid.setX(newX);
                centroid.setY(newY);
            }
        }
    }
}