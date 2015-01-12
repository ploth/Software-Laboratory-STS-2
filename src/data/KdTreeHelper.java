package data;

import org.garret.perst.IterableIterator;

import data.KdTree.Manhattan;
import data.KdTree.SqrEuclid;
import data.PERSTDatabase.DatabaseElement;

public class KdTreeHelper {

	private SqrEuclid<Character> seTree_;
	private Manhattan<Character> mTree_;
	private Integer sizeLimit_ = 100000;
	private static KdTreeHelper instance_;
	private PERSTDatabase db_;

	private KdTreeHelper() {
		db_ = PERSTDatabase.getInstance();
	}

	public static KdTreeHelper getInstance() {
		if (instance_ == null) {
			instance_ = new KdTreeHelper();
		}
		return instance_;
	}

	public void createSqrEuclidKdTreeFromDatabase() {
		IterableIterator<DatabaseElement> iter = db_.getDatabaseIterator();
		seTree_ = new SqrEuclid<Character>(db_.getDim_(), sizeLimit_);
		while (iter.hasNext()) {
			DatabaseElement e = iter.next();
			double[] pixels = new double[e.getPixels().length];
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = e.getPixels()[i];
			}
			seTree_.addPoint(pixels, e.getClassification());
		}
	}

	public void createManhattenKdTreeFromDatabase() {
		IterableIterator<DatabaseElement> iter = db_.getDatabaseIterator();
		mTree_ = new Manhattan<Character>(db_.getDim_(), sizeLimit_);
		while (iter.hasNext()) {
			DatabaseElement e = iter.next();
			double[] pixels = new double[e.getPixels().length];
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = e.getPixels()[i];
			}
			mTree_.addPoint(pixels, e.getClassification());
		}
	}
}
