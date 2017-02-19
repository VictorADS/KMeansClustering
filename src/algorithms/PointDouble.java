package algorithms;
import java.awt.Point;
 
public class PointDouble {
 
    private double x = 0;
    private double y = 0;
 
    public PointDouble(double x, double y)
    {
        this.setX(x);
        this.setY(y);
    }
    public PointDouble(Point p){
    	this.x = p.getX();
    	this.y = p.getY();
    }
    public void setX(double x) {
        this.x = x;
    }
    
    public double getX()  {
        return this.x;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public double getY() {
        return this.y;
    }
    
    public String toString(){
    	return x+" , "+y;
    }
    
    public PointDouble clone(){
    	return new PointDouble(x,y);
    }
    protected static double distance(PointDouble p, PointDouble centroid) {
        return Math.sqrt(Math.pow((centroid.getY() - p.getY()), 2) + Math.pow((centroid.getX() - p.getX()), 2));
    }
    
    protected static double distance(Point p, PointDouble centroid) {
        return Math.sqrt(Math.pow((centroid.getY() - p.getY()), 2) + Math.pow((centroid.getX() - p.getX()), 2));
    }
}