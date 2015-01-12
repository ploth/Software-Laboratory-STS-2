package data;

import org.garret.perst.IterableIterator;

import data.KdTree.SqrEuclid;
import data.PERSTDatabase.DatabaseElement;

public class KdTreeHelper {
	public static void createKdTreeFromDatabase() {
		PERSTDatabase db = PERSTDatabase.getInstance();
		IterableIterator<DatabaseElement> iter = db.getDatabaseIterator();
		Integer sizeLimit = 100000;
		SqrEuclid eTree = new SqrEuclid(db.getDim_(), sizeLimit);
		while (iter.hasNext()) {
			DatabaseElement e = iter.next();
			double[] pixels = new double[e.getPixels().length];
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = e.getPixels()[i];
			}
			eTree.addPoint(pixels, e.getClassification());
		}
	}
}
