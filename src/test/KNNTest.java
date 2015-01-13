package test;

import algorithm.AbstractAlgorithm;
import algorithm.KNN;

public class KNNTest {

	public static void main(String[] args) {
		KNN knn = new KNN();
		knn.doAlgorithm(AbstractAlgorithm.SQR_EUCLID, 20);
	}
}
