package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Map.Entry;

public class KMeans {

	private int NUM_CLUSTERS = 5;
	// Min and Max X and Y
	private ArrayList<Point> points;
	private ArrayList<Cluster> clusters;

	public KMeans(ArrayList<Point> list) {
		this.points = list;
		this.clusters = new ArrayList<>();
	}

	// Initializes the process
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

	public ArrayList<Cluster> naifBudgetClustering(int budget) {
		ArrayList<Cluster> maxClusters = new ArrayList<>();
		// ForEach Cluster
		for (Cluster c : clusters) {
			int maximumSize = Integer.MIN_VALUE;
			Cluster maxCluster = null;
			// For each point of the cluster find the point that maximize the
			// number of points
			for (Point p : c.getPoints()) {
				// Copy the cluster so that we get a clean list
				Cluster copy = new Cluster(c);
				Cluster tmp = new Cluster(0);
				tmp.setCentroid(p);
				// Calculate the clsoest point of the current centroid
				while (tmp.calculateScore() <= budget) {
					Point closestPoint = copy.getClosestPoint(tmp.getCentroid());
					tmp.addPoint(closestPoint);
					calculateCentroids(tmp);
					double tmpScore = tmp.calculateScore();
					// If we filled the cluster remove the point and recalculte
					// the centroid then see if its better
					if (tmpScore > budget) {
						tmp.getPoints().remove(closestPoint);
						calculateCentroids(tmp);
						if (tmp.getPoints().size() > maximumSize) {
							maximumSize = tmp.getPoints().size();
							maxCluster = tmp;
						}
						break;
					}
				}
			}
			maxClusters.add(maxCluster);
		}
		clusters = maxClusters;
		return maxClusters;
	}

	public ArrayList<Cluster> cleanClusters(int budget) {
		ArrayList<Point> notAssignedPoints = points;
		for (Cluster c : clusters) {
			while (c.calculateScore() > budget) {
				System.out.println("On passe la ?");
				// Trouver le point le plus loin
				Point p = c.farthestPointOfCluster();
				// Recalculer le centre
				calculateCentroids(c);
			}
			notAssignedPoints.removeAll(c.getPoints());
		}

		for (Point p : notAssignedPoints) {
			int index = indexOfClosestCluster(p);
			System.out.println("Avant " +clusters.get(index).calculateScore());
			clusters.get(index).addPoint(p);
			calculateCentroids(clusters.get(index));
			System.out.println("Apres " +clusters.get(index).calculateScore());
			if (clusters.get(index).calculateScore() > budget) {
				clusters.get(index).getPoints().remove(p);
				calculateCentroids(clusters.get(index));
			}
		}
		return clusters;
	}

	public ArrayList<Cluster> calculate() {
		boolean finish = false;
		while (!finish) {
			clearClusters();

			ArrayList<PointDouble> lastCentroids = getCentroids();

			assignCluster();

			calculateCentroids();

			ArrayList<PointDouble> currentCentroids = getCentroids();

			double distance = 0;
			for (int i = 0; i < lastCentroids.size(); i++) {
				distance += PointDouble.distance(lastCentroids.get(i), currentCentroids.get(i));
			}

			if (distance == 0) {
				finish = true;
			}
		}
		return clusters;
	}

	private void clearClusters() {
		for (Cluster cluster : clusters) {
			cluster.clear();
		}
	}

	private ArrayList<PointDouble> getCentroids() {
		ArrayList<PointDouble> centroids = new ArrayList<>(NUM_CLUSTERS);
		for (Cluster cluster : clusters) {
			PointDouble aux = cluster.getCentroid().clone();
			centroids.add(aux);
		}
		return centroids;
	}

	private void assignCluster() {
		for (Point point : points) {
			int index = indexOfClosestCluster(point);
			clusters.get(index).addPoint(point);
		}
	}

	private int indexOfClosestCluster(Point p) {
		double distance = 0.0;
		double minDist = Double.MAX_VALUE;
		int indexOfCluster = 0;
		for (int i = 0; i < NUM_CLUSTERS; i++) {
			Cluster c = clusters.get(i);
			distance = PointDouble.distance(p, c.getCentroid());
			if (distance < minDist) {
				minDist = distance;
				indexOfCluster = i;
			}
		}
		return indexOfCluster;
	}

	private void calculateCentroids(Cluster cluster) {
		double sumX = 0;
		double sumY = 0;
		ArrayList<Point> list = cluster.getPoints();
		int n_points = list.size();

		for (Point point : list) {
			sumX += point.getX();
			sumY += point.getY();
		}

		PointDouble centroid = cluster.getCentroid();
		if (n_points > 0) {
			double newX = sumX / n_points;
			double newY = sumY / n_points;
			centroid.setX(newX);
			centroid.setY(newY);
		}
	}

	private void calculateCentroids() {
		for (Cluster cluster : clusters) {
			double sumX = 0;
			double sumY = 0;
			ArrayList<Point> list = cluster.getPoints();
			int n_points = list.size();

			for (Point point : list) {
				sumX += point.getX();
				sumY += point.getY();
			}

			PointDouble centroid = cluster.getCentroid();
			if (n_points > 0) {
				double newX = sumX / n_points;
				double newY = sumY / n_points;
				centroid.setX(newX);
				centroid.setY(newY);
			}
		}
	}
}