package algorithm;

import data.KdTreeHelper;

public class KMean extends AbstractAlgorithm {

	public KMean() {

	}

	@Override
	public void doSqrEuclid(int k) {
		KdTreeHelper treeHelper = KdTreeHelper.getInstance();
		// select k random prototypes and add to kdtree

		// iterate through every point and check distance to k prototypes in
		// kdtree and set the cluster value

		// backup the position of all k (vector)
		// safe them in 2 dimensional array and the index is the cluster value

		// delete kdtree

		// iterate through all points with cluster value 0 for example and
		// calculate arithmetic mean. the arithmetic mean is to add to a new
		// kdtree

		// compare arithmetic means to old positions
		// -> sum up all distances and divide by k (arithmetic mean)
		// safe to adjustment value

		// start over at "iteration through every point..." if adjustment is
		// small enough
	}

	@Override
	public void doManhattan(int k) {
		// TODO Auto-generated method stub

	}

}
