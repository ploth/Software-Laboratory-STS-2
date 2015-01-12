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
		long time01 = System.currentTimeMillis();
		KdTreeHelper treeHelper = KdTreeHelper.getInstance();
		long time02 = System.currentTimeMillis();
		SqrEuclid<Character> set = treeHelper
				.createSqrEuclidKdTreeFromDatabase();
		long time03 = System.currentTimeMillis();
		Manhattan<Character> mt = treeHelper
				.createManhattenKdTreeFromDatabase();
		long time04 = System.currentTimeMillis();
		PERSTDatabase db = PERSTDatabase.getInstance();
		long time05 = System.currentTimeMillis();
		DatabaseElement e = db.getDatabaseElement(60000);
		long time06 = System.currentTimeMillis();
		// System.out.println((int) e.getPixels()[350]);
		// System.out.println(e.getPixelsAsDouble()[350]);
		// get distances SqrEuclid
		long time1 = System.currentTimeMillis();
		List<Entry<Character>> nearest = set.nearestNeighbor(
				e.getPixelsAsDouble(), 20, true);
		for (int i = nearest.size() - 1; i >= 0; i--) {
			System.out.println("distance: " + nearest.get(i).distance);
			System.out.println("value " + (int) nearest.get(i).value);
		}
		long time2 = System.currentTimeMillis();

		System.out.println();
		System.out.println();
		// get distances Manhatten
		long time3 = System.currentTimeMillis();
		List<Entry<Character>> nearest2 = mt.nearestNeighbor(
				e.getPixelsAsDouble(), 20, true);
		for (int i = nearest2.size() - 1; i >= 0; i--) {
			System.out.println("distance: " + nearest2.get(i).distance);
			System.out.println("value " + (int) nearest2.get(i).value);
		}
		long time4 = System.currentTimeMillis();
		System.out.println();
		System.out.println();
		System.out.println("Time getInstance KdTreeHelper: "
				+ (time02 - time01));
		System.out.println("Time createSqrEuclidKdTree: " + (time03 - time02));
		System.out.println("Time createManhattanKdTree: " + (time04 - time03));
		System.out.println("Time getInstance Database: " + (time05 - time04));
		System.out.println("Time getDatabaseElement(60000): "
				+ (time06 - time05));
		System.out.println("Time nearest SqrEuclid: " + (time2 - time1));
		System.out.println("Time nearest Manhattan: " + (time4 - time3));
	}
}
