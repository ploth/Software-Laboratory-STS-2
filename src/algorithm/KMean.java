package algorithm;

import io.PERST_PNG_Converter;

import java.util.ArrayList;
import java.util.Arrays;

import org.garret.perst.IterableIterator;

import data.KdTree.SqrEuclid;
import data.KdTreeHelper;
import data.PERSTDatabase.DatabaseElement;

public class KMean extends AbstractAlgorithm {

	private static final int FIRST_LIST_ELEMENT = 0;
	private static final int LIST_SIZE = 1;
	private static final int MAX_ITERATIONS = 5;
	private static final double MINIMUM_ADJUSTMENT = 0.1;

	public KMean() {

	}

	@Override
	public void doSqrEuclid(int k) {
		KdTreeHelper treeHelper = KdTreeHelper.getInstance();
		int dim = getDb_().getDim();
		int iterations = 1;
		double adjustment = 0;
		double sum = 0;
		double subtraction = 0;
		int[] indexes = new int[k];
		int[] numbersOfEachCluster = new int[k];
		double[][] prototypes = new double[k][dim * dim];
		double[][] prototypesNew = new double[k][dim * dim];

		for (int i = 0; i < prototypesNew.length; i++) {
			Arrays.fill(prototypesNew[i], 0);
		}

		// adding k random prototypes from database to the kdtree
		SqrEuclid<Character> SETree = treeHelper
				.createSqrEuclidKdTreeWithRandomElementsFromDatabase(k, indexes);

		// save the prototype positions
		for (int i = 0; i < prototypes.length; i++) {
			prototypes[i] = getDb_().getDatabaseElement(indexes[i])
					.getPixelsAsDouble();
		}

		while (adjustment >= MINIMUM_ADJUSTMENT && iterations <= MAX_ITERATIONS) {

			// iterate through every point and check distance to each prototype
			// and set the cluster value
			// the cluster value is the value of the nearest prototype
			IterableIterator<DatabaseElement> iter = getDb_()
					.getDatabaseIterator();
			while (iter.hasNext()) {
				DatabaseElement e = iter.next();
				char clusterValue = SETree.nearestNeighbor(
						e.getPixelsAsDouble(), LIST_SIZE, true).get(
						FIRST_LIST_ELEMENT).value;
				numbersOfEachCluster[clusterValue]++;
				e.setClusterValue(clusterValue);
			}

			// backup the position of all k (vector)
			// safe them in 2 dimensional array and the index is the cluster
			// value

			// iterate through all points with cluster value 0, 1, 2, ...
			for (int i = 0; i < k; i++) {
				// and calculate arithmetic mean.
				IterableIterator<DatabaseElement> specificCluster = getDb_()
						.getClusteredDatabaseIterator((char) i);
				ArrayList<DatabaseElement> specificClusterAsList = specificCluster
						.toList(); // after this command the iterator is at the
									// end
				int quantityOfThisCluster = specificClusterAsList.size();
				for (int m = 0; m < quantityOfThisCluster; m++) {
					// System.out.println("entered");
					// each point of one specific cluster
					DatabaseElement e = specificClusterAsList.get(m);
					// DatabaseElement e = specificCluster.next();
					// sum up all vectors of a specific cluster
					// pixelsNew[i] = pixelsNew[i] + e.getPixels();
					// vector addition not defined
					// System.out.println(pixels[i].length);
					for (int j = 0; j < prototypes[i].length; j++) {
						prototypesNew[i][j] = (prototypesNew[i][j] + e
								.getPixels()[j]);
					}

					// // TODO remove
					// System.out.println("after summation");
					// printCreepyArray(pixelsNew[i]);
					//
					// // TODO: remove
					// System.out.println("after divide by "
					// + quantityOfThisCluster);
					// printCreepyArray(pixelsNew[i]);

				}

				// divide by the number of vectors of a specific cluster

				for (int j = 0; j < prototypes[i].length; j++) {

					double value = prototypesNew[i][j] / quantityOfThisCluster; // cut
					// off
					// after
					// point
					// System.out.println(value);
					prototypesNew[i][j] = value;
				}

				// debug
				PERST_PNG_Converter.write((char) 1, prototypesNew[i],
						"Images/ArithmeticMean_Iteration_" + iterations
								+ "_Cluster_" + i + ".png");
				// i = k; // TODO REMOVE THIS!

			}
			// the arithmetic mean is to add to a new kdtree (overwrite the old
			// one)
			SETree = treeHelper.createSqrEuclidKdTreeFromArray(prototypesNew);

			// compare arithmetic means to old positions
			// -> sum up all distances and divide by k (arithmetic mean)
			// safe to adjustment value

			adjustment = 0;
			sum = 0;
			subtraction = 0;
			for (int i = 0; i < prototypesNew.length; i++) {
				for (int j = 0; j < prototypesNew[i].length; j++) {
					subtraction = prototypes[i][j] - prototypesNew[i][j];
					sum = sum + subtraction * subtraction;
				}
				adjustment = adjustment + Math.sqrt(sum);
				sum = 0;
			}
			adjustment = adjustment / k;
			// TODO debug
			System.out.println("adjustment: " + adjustment);
			iterations++;
		}
	}

	@Override
	public void doManhattan(int k) {
		// TODO Auto-generated method stub

	}

	// TODO: Remove
	public void printCreepyArray(int[] array) {
		for (int r = 0; r < (int) (Math.sqrt(array.length)); r++) {
			System.out.println();
			for (int t = 0; t < 28; t++) {
				System.out
						.print(array[r * (int) (Math.sqrt(array.length)) + t]);
			}
		}
		System.out.println();
		System.out.println();
	}
}
