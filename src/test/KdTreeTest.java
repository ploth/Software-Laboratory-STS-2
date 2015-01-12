package test;

import data.KdTree.Manhattan;
import data.KdTree.SqrEuclid;
import data.KdTreeHelper;

public class KdTreeTest {

	public static void main(String[] args) {
		KdTreeHelper treeHelper = KdTreeHelper.getInstance();
		SqrEuclid<Character> set = treeHelper
				.createSqrEuclidKdTreeFromDatabase();
		Manhattan<Character> mt = treeHelper
				.createManhattenKdTreeFromDatabase();
		set.nearestNeighbor(location, count, sequentialSorting)
	}
}
