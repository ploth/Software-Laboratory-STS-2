package test;

import java.util.List;

import data.KdTree.Entry;
import data.KdTree.Manhattan;
import data.KdTree.SqrEuclid;
import data.KdTreeHelper;
import data.PERSTDatabase;
import data.PERSTDatabase.DatabaseElement;

public class KdTreeTest {

	public static void main(String[] args) {
		KdTreeHelper treeHelper = KdTreeHelper.getInstance();
		SqrEuclid<Character> set = treeHelper
				.createSqrEuclidKdTreeFromDatabase();
		Manhattan<Character> mt = treeHelper
				.createManhattenKdTreeFromDatabase();
		PERSTDatabase db = PERSTDatabase.getInstance();
		DatabaseElement e = db.getDatabaseElement(1);
		// System.out.println((int) e.getPixels()[350]);
		// System.out.println(e.getPixelsAsDouble()[350]);
		List<Entry<Character>> nearest = set.nearestNeighbor(
				e.getPixelsAsDouble(), 20, true);
		System.out.println(nearest.get(0).distance);
		System.out.println((int) nearest.get(0).value);
	}
}
