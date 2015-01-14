package algorithm;

import org.garret.perst.IterableIterator;

import data.KdTree.SqrEuclid;
import data.KdTreeHelper;
import data.PERSTDatabase.DatabaseElement;

public class KMean extends AbstractAlgorithm {

	private static final int FIRST_LIST_ELEMENT = 0;
	private static final int LIST_SIZE = 1;
	private static final int MAX_ITERATIONS = 100000;
	private static final int MINIMUM_ADJUSTMENT = 0;

	public KMean() {

	}

	@Override
	public void doSqrEuclid(int k) {
		// select k random prototypes and add them to kdtree
		KdTreeHelper treeHelper = KdTreeHelper.getInstance();
		int dim = getDb_().getDim();
		int adjustment = 0;
		int[] indexes = new int[k];
		char[][] pixels = new char[k][dim * dim];
		SqrEuclid<Character> SETreeKE = treeHelper
				.createSqrEuclidKdTreeWithRandomElementsFromDatabase(k, indexes);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = getDb_().getDatabaseElement(indexes[i]).getPixels();
		}
		// debug
		// for (int i = 0; i < indexes.length; i++) {
		// System.out.println((int) getDb_().getDatabaseElement(indexes[i])
		// .getCorrectClassification());
		// }
		int iterations = 0;
		while (adjustment > MINIMUM_ADJUSTMENT || iterations <= MAX_ITERATIONS) {
			iterations++;
			// iterate through every point and check distance to k prototypes in
			// kdtree and set the cluster value
			// the cluster value is the value of the nearest prototype
			IterableIterator<DatabaseElement> iter = getDb_()
					.getDatabaseIterator();
			while (iter.hasNext()) {
				DatabaseElement e = iter.next();
				e.setClusterValue(SETreeKE.nearestNeighbor(
						e.getPixelsAsDouble(), LIST_SIZE, true).get(
						FIRST_LIST_ELEMENT).value);
			}
			// backup the position of all k (vector)
			// safe them in 2 dimensional array and the index is the cluster
			// value

			// delete kdtree

			// iterate through all points with cluster value 0 for example and
			// calculate arithmetic mean. the arithmetic mean is to add to a new
			// kdtree
			for (int i = 0; i < k; i++) {
				getDb_().getClusteredDatabaseIterator((char) i);
			}
			// compare arithmetic means to old positions
			// -> sum up all distances and divide by k (arithmetic mean)
			// safe to adjustment value

			// start over at "iteration through every point..." if adjustment is
			// small enough
		}
	}

	@Override
	public void doManhattan(int k) {
		// TODO Auto-generated method stub

	}

}
