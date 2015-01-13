package algorithm;

import java.util.Arrays;
import java.util.List;

import org.garret.perst.IterableIterator;

import data.KdTree.Entry;
import data.KdTree.SqrEuclid;
import data.KdTreeHelper;
import data.PERSTDatabase.DatabaseElement;

public class KNN extends AbstractAlgorithm {

	private static final int numberOfPossibleClassifications = 10; // 0-9

	public KNN() {

	}

	public void doSqrEuclid(int k) {
		KdTreeHelper treeHelper = KdTreeHelper.getInstance();
		SqrEuclid<Character> SETree = treeHelper
				.createSqrEuclidKdTreeFromDatabase();
		IterableIterator<DatabaseElement> uOI = getDb_()
				.getUnCorrectDatabaseIterator();
		while (uOI.hasNext()) {
			DatabaseElement e = uOI.next();
			List<Entry<Character>> list = SETree.nearestNeighbor(
					e.getPixelsAsDouble(), k, true);
			int[] predictArray = new int[numberOfPossibleClassifications];
			Arrays.fill(predictArray, 0);
			for (int i = list.size() - 1; i >= 0; i--) {
				predictArray[(int) list.get(i).value]++;
			}
			int greatestValue = 0;
			int prediction = 0; // array position
			for (int i = 0; i < predictArray.length; i++) {
				if (predictArray[i] > greatestValue) {
					greatestValue = predictArray[i];
					prediction = i;
				}
			}
			e.setAlgoClassification((char) prediction);
		}
	}

	public void doManhattan(int k) {
	}
}
