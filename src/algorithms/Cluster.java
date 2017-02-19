package algorithms;
import java.awt.Point;
import java.util.ArrayList;
 
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
 
	public int getId() {
		return id;
	}
	
	public void clear() {
		points.clear();
	}
}