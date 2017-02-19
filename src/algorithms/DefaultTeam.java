package algorithms;

import java.awt.Point;
import java.util.ArrayList;

public class DefaultTeam {

	public ArrayList<ArrayList<Point>> calculKMeans(ArrayList<Point> points) {
		KMeans kmean = new KMeans(points);
		kmean.init();
		ArrayList<Cluster> clusters = kmean.calculate();
		ArrayList<ArrayList<Point>> kmeans = new ArrayList<>();

		for (Cluster c : clusters) {
			kmeans.add(c.getPoints());
		}

		return kmeans;
	}

	public ArrayList<ArrayList<Point>> calculKMeansBudget(ArrayList<Point> points) {
		int BUDGET = 10101;
		KMeans kmean = new KMeans(points);
		kmean.init();
		ArrayList<Cluster> clusters = kmean.calculate();
		clusters = kmean.naifBudgetClustering(BUDGET);
		clusters = kmean.cleanClusters(BUDGET);
		ArrayList<ArrayList<Point>> kmeans = new ArrayList<>();
		
		int nbPoints = 0;
		for (Cluster c : clusters) {
			kmeans.add(c.getPoints());
			nbPoints += c.getPoints().size();
		}
		System.out.println("LE SCORE EST DE "+nbPoints);
		return kmeans;
	}
}
