package algorithm;

import java.util.ArrayList;
import java.util.Arrays;

import org.garret.perst.IterableIterator;

import data.KdTree.SqrEuclid;
import data.KdTreeHelper;
import data.PERSTDatabase.DatabaseElement;

public class KMean extends AbstractAlgorithm {

	private static final int FIRST_LIST_ELEMENT = 0;
	private static final int LIST_SIZE = 1;
	private static final int MAX_ITERATIONS = 100;
	private static final double MINIMUM_ADJUSTMENT = 0.1;

	public KMean() {

	}

	@Override
	public void doSqrEuclid(int k) {
		KdTreeHelper treeHelper = KdTreeHelper.getInstance();
		char clusterValue = 0;
		int dim = getDb_().getDim();
		int iterations = 1;
		int quantityOfThisCluster = 0;
		double adjustment = MINIMUM_ADJUSTMENT; // looks wrong, but needed to
												// enter while
		double sum = 0;
		double subtraction = 0;
		int[] indexes = new int[k];
		double[][] prototypes = new double[k][dim * dim];

		// adding k random prototypes from database to the kdtree
		SqrEuclid<Character> SETree = treeHelper
				.createSqrEuclidKdTreeWithRandomElementsFromDatabase(k, indexes);

		// save the prototype positions
		for (int i = 0; i < prototypes.length; i++) {
			prototypes[i] = getDb_().getDatabaseElement(indexes[i])
					.getPixelsAsDouble();
		}

		while (adjustment >= MINIMUM_ADJUSTMENT && iterations <= MAX_ITERATIONS) {
			double[][] prototypesNew = new double[k][dim * dim];
			for (int i = 0; i < prototypesNew.length; i++) {
				Arrays.fill(prototypesNew[i], 0);
			}
			// iterate through every point
			IterableIterator<DatabaseElement> iter = getDb_()
					.getDatabaseIterator();
			while (iter.hasNext()) {
				DatabaseElement e = iter.next();
				// check distance to each prototype
				clusterValue = SETree.nearestNeighbor(e.getPixelsAsDouble(),
						LIST_SIZE, true).get(FIRST_LIST_ELEMENT).value;
				// TODO remove syso
				// System.out.println("assigned cluster value: "
				// + (int) clusterValue);
				// and set the cluster value.
				// the cluster value is the value of the nearest prototype
				e.setClusterValue(clusterValue);
			}

			// iterate through cluster value 0, 1, 2, ...
			for (int i = 0; i < k; i++) {
				IterableIterator<DatabaseElement> specificCluster = getDb_()
						.getClusteredDatabaseIterator((char) i);
				ArrayList<DatabaseElement> specificClusterAsList = specificCluster
						.toList();
				quantityOfThisCluster = specificClusterAsList.size();

				// TODO: if quantityOfThisCluster == 0 break this iteration
				// else division by zero
				// System.out.println(quantityOfThisCluster);

				// and calculate the arithmetic mean of each cluster
				// iterate through each db element of current cluster
				for (int m = 0; m < quantityOfThisCluster; m++) {
					DatabaseElement e = specificClusterAsList.get(m);
					// printCreepyArray(e.getPixelsAsDouble());
					// sum up the vectors of a specific cluster
					for (int j = 0; j < prototypesNew[i].length; j++) {
						prototypesNew[i][j] = (prototypesNew[i][j] + e
								.getPixelsAsDouble()[j]);
						// System.out.println(prototypesNew[i][j]);
					}
				}

				// TODO moveable debug blog
				// for (int p = 0; p < prototypesNew.length; p++) {
				// for (int o = 0; o < prototypesNew[p].length; o++) {
				// if (Double.isNaN(prototypesNew[p][o])) {
				// System.out.println("dick");
				// }
				// }
				// }

				// divide by the number of current cluster elements
				for (int j = 0; j < prototypesNew[i].length; j++) {
					prototypesNew[i][j] = prototypesNew[i][j]
							/ quantityOfThisCluster;
					// System.out.println(prototypesNew[i][j]);
				}

				// debug
				// PERST_PNG_Converter.write((char) 1, prototypesNew[i],
				// "Images/"
				// + i + "/ArithmeticMean_Iteration_" + iterations
				// + ".png");
			}

			// overwrite the old kdtree with the new prototypes
			SETree = treeHelper.createSqrEuclidKdTreeFromArray(prototypesNew);
			// compare old and new prototypes
			adjustment = 0;
			sum = 0;
			for (int i = 0; i < prototypesNew.length; i++) {
				for (int j = 0; j < prototypesNew[i].length; j++) {
					// subtract the new prototypes from the old
					subtraction = prototypes[i][j] - prototypesNew[i][j];
					// (1) calculate the norm of each distance
					sum = sum + subtraction * subtraction;
				}
				// (2) calculate the norm of each distance
				// (1) calculate the arithmetic mean of the norms
				adjustment = adjustment + Math.sqrt(sum);
				sum = 0;
			}
			// (2) calculate the arithmetic mean of the norms
			adjustment = adjustment / k;

			// prototypes=prototypesNew.clone();
			for (int i = 0; i < prototypes.length; i++) {
				for (int j = 0; j < prototypes[i].length; j++) {
					prototypes[i][j] = prototypesNew[i][j];
				}
			}

			iterations++;
			// TODO debug
			System.out.println("adjustment: " + adjustment);
		}
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

	// TODO: Remove
	public void printCreepyArray(double[] array) {
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

	@Override
	public void doManhattan(int k) {
		// TODO Auto-generated method stub

	}
}