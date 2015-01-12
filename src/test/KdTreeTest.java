package test;

import data.KdTreeHelper;

public class KdTreeTest {

	public static void main(String[] args) {
		KdTreeHelper treeHelper = KdTreeHelper.getInstance();
		treeHelper.createSqrEuclidKdTreeFromDatabase();
	}
}
