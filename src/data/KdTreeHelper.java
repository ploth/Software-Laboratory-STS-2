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

	public SqrEuclid<Character> createSqrEuclidKdTreeFromDatabase() {
		IterableIterator<DatabaseElement> iter = db_
				.getCorrectDatabaseIterator();
		seTree_ = new SqrEuclid<Character>(db_.getDim() * db_.getDim(),
				sizeLimit_);
		while (iter.hasNext()) {
			DatabaseElement e = iter.next();
			seTree_.addPoint(e.getPixelsAsDouble(),
					e.getCorrectClassification());
		}
		return seTree_;
	}

	public Manhattan<Character> createManhattenKdTreeFromDatabase() {
		IterableIterator<DatabaseElement> iter = db_
				.getCorrectDatabaseIterator();
		mTree_ = new Manhattan<Character>(db_.getDim() * db_.getDim(),
				sizeLimit_);
		while (iter.hasNext()) {
			DatabaseElement e = iter.next();
			mTree_.addPoint(e.getPixelsAsDouble(), e.getCorrectClassification());
		}
		return mTree_;
	}
}
