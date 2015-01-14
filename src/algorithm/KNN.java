package algorithm;

import java.util.Arrays;
import java.util.List;

import org.garret.perst.IterableIterator;

import data.KdTree.Entry;
import data.KdTree.Manhattan;
import data.KdTree.SqrEuclid;
import data.KdTreeHelper;
import data.PERSTDatabase.DatabaseElement;

public class KNN extends AbstractAlgorithm {

	private static final int NUMBER_OF_POSSIBLE_CLASSIFICATIONS = 10; // 0-9

	public KNN() {

	}

	public void doSqrEuclid(int k) {
		KdTreeHelper treeHelper = KdTreeHelper.getInstance();
		SqrEuclid<Character> SETree = treeHelper
				.createSqrEuclidKdTreeFromDatabase();
		IterableIterator<DatabaseElement> uOI = getDb_()
				.getNonTrainingdataDatabaseIterator();
		DatabaseElement e;
		List<Entry<Character>> list;
		int[] predictArray = new int[NUMBER_OF_POSSIBLE_CLASSIFICATIONS];
		int greatestValue = 0;
		int prediction = 0; // array position
		double[] pixels;
		long time1 = 0, time2 = 0;
		while (uOI.hasNext()) {
			e = uOI.next();
			pixels = e.getPixelsAsDouble();

			// time1 = System.currentTimeMillis();
			list = SETree.nearestNeighbor(pixels, k, false);
			// time2 = System.currentTimeMillis();

			Arrays.fill(predictArray, 0);
			for (int i = list.size() - 1; i >= 0; i--) {
				predictArray[(int) list.get(i).value]++;
			}
			for (int i = 0; i < predictArray.length; i++) {
				if (predictArray[i] > greatestValue) {
					greatestValue = predictArray[i];
					prediction = i;
				}
			}
			e.setAlgoClassification((char) prediction);
			greatestValue = 0;
			prediction = 0;
			// System.out.println((time2 - time1));
		}
	}

	public void doManhattan(int k) {
		KdTreeHelper treeHelper = KdTreeHelper.getInstance();
		Manhattan<Character> MTree = treeHelper
				.createManhattenKdTreeFromDatabase();
		IterableIterator<DatabaseElement> uOI = getDb_()
				.getNonTrainingdataDatabaseIterator();
		DatabaseElement e;
		List<Entry<Character>> list;
		int[] predictArray = new int[NUMBER_OF_POSSIBLE_CLASSIFICATIONS];
		int greatestValue = 0;
		int prediction = 0; // array position
		double[] pixels;
		long time1 = 0, time2 = 0;
		while (uOI.hasNext()) {
			e = uOI.next();
			pixels = e.getPixelsAsDouble();

			time1 = System.currentTimeMillis();
			list = MTree.nearestNeighbor(pixels, k, false);
			time2 = System.currentTimeMillis();

			Arrays.fill(predictArray, 0);
			for (int i = list.size() - 1; i >= 0; i--) {
				predictArray[(int) list.get(i).value]++;
			}
			for (int i = 0; i < predictArray.length; i++) {
				if (predictArray[i] > greatestValue) {
					greatestValue = predictArray[i];
					prediction = i;
				}
			}
			e.setAlgoClassification((char) prediction);
			greatestValue = 0;
			prediction = 0;
			System.out.println((time2 - time1));
		}
	}
}
