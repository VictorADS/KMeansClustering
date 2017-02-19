package algorithms;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.omg.CORBA.MARSHAL;
 
public class Cluster {
	
	public ArrayList<Point> points;
	public PointDouble centroid;
	public int id;
	
	//Creates a new Cluster
	public Cluster(int id) {
		this.id = id;
		this.points = new ArrayList<Point>();
		this.centroid = null;
	}
	public Cluster (Cluster c){
		this.id = c.id;
		this.points = (ArrayList<Point>) c.getPoints().clone();
		this.centroid = null;
	}
	public ArrayList<Point> getPoints() {
		return points;
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}
 
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
 
	public PointDouble getCentroid() {
		return centroid;
	}
 
	public void setCentroid(PointDouble centroid) {
		this.centroid = centroid;
	}
	public void setCentroid(Point centroid) {
		this.centroid = new PointDouble(centroid);
	}
	public int getId() {
		return id;
	}
	public Point farthestPointOfCluster(){
		Point maxPoint = null;
		double dist = Double.MIN_VALUE;
		for(Point p : points){
			double tmpDist = PointDouble.distance(p, centroid);
			if(tmpDist > dist){
				dist = tmpDist;
				maxPoint = p;
			}
		}
		points.remove(maxPoint);
		return maxPoint;
	}
	
	public double calculateScore(){
		double dist = 0;
		for(Point p : points){
			dist += PointDouble.distance(p, centroid);
		}
		return dist;
	}
	public void clear() {
		points.clear();
	}

	public Point getClosestPoint(PointDouble centroid2) {
		Point maxPoint = null;
		double dist = Double.MAX_VALUE;
		for(Point p : points){
			if(p.getX() == centroid2.getX() && p.getY() == centroid2.getY())
				continue;
			double tmpDist = PointDouble.distance(p, centroid2);
			if(tmpDist < dist){
				dist = tmpDist;
				maxPoint = p;
			}
		}
		points.remove(maxPoint);
		return maxPoint;
		}
}