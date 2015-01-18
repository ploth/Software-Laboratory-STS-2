package data;

import java.util.Random;

import org.garret.perst.IterableIterator;

import data.KdTree.Manhattan;
import data.KdTree.SqrEuclid;
import data.PERSTDatabase.DatabaseElement;

public class KdTreeHelper {

	private Integer sizeLimit_ = 1000000;
	private static KdTreeHelper instance_;
	private PERSTDatabase db_;
	private final static int FIRST_DATABASE_INDEX = 1;

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
		SqrEuclid<Character> seTree = new SqrEuclid<Character>(db_.getDim()
				* db_.getDim(), sizeLimit_);
		while (iter.hasNext()) {
			DatabaseElement e = iter.next();
			seTree.addPoint(e.getPixelsAsDouble(), e.getCorrectClassification());
		}
		return seTree;
	}

	public SqrEuclid<Character> createSqrEuclidKdTreeWithRandomElementsFromDatabase(
			int k, int[] indexes) {
		int numberOfDatabaseElements = db_.getNumberOfDatabaseElements();
		if (k > numberOfDatabaseElements) {
			// TODO throw exception
			// the k is greater than the number of database elements
			System.err
					.println("HELLO I AM THE CREATESQREUCLIDKDTREEWITHRANDOMELEMENTSFROMDATABASE FUNCTION IN KDTREEHELPER. WE NEED A EXCEPTION HERE");
		}
		SqrEuclid<Character> seTree = new SqrEuclid<Character>(db_.getDim()
				* db_.getDim(), sizeLimit_);
		for (int i = 0; i < k; i++) {
			DatabaseElement e = db_.getDatabaseElement(randomIntInRange(
					FIRST_DATABASE_INDEX, numberOfDatabaseElements));
			indexes[i] = e.getIndex();
			seTree.addPoint(e.getPixelsAsDouble(), (char) i);
		}
		return seTree;
	}

	public SqrEuclid<Character> createSqrEuclidKdTreeFromArray(
			double[][] databaseElements) {
		SqrEuclid<Character> seTree = new SqrEuclid<Character>(db_.getDim()
				* db_.getDim(), databaseElements.length);
		for (int clusterID = 0; clusterID < databaseElements.length; clusterID++) {
			seTree.addPoint(databaseElements[clusterID], (char) clusterID);
		}
		return seTree;
	}

	public Manhattan<Character> createManhattenKdTreeFromDatabase() {
		IterableIterator<DatabaseElement> iter = db_
				.getCorrectDatabaseIterator();
		Manhattan<Character> mTree = new Manhattan<Character>(db_.getDim()
				* db_.getDim(), sizeLimit_);
		while (iter.hasNext()) {
			DatabaseElement e = iter.next();
			mTree.addPoint(e.getPixelsAsDouble(), e.getCorrectClassification());
		}
		return mTree;
	}

	public Manhattan<Character> createManhattanKdTreeWithRandomElementsFromDatabase(
			int k, int[] indexes) {
		int numberOfDatabaseElements = db_.getNumberOfDatabaseElements();
		if (k > numberOfDatabaseElements) {
			// TODO throw exception
			// the k is greater than the number of database elements
			System.err
					.println("HELLO I AM THE CREATESQREUCLIDKDTREEWITHRANDOMELEMENTSFROMDATABASE FUNCTION IN KDTREEHELPER. WE NEED A EXCEPTION HERE");
		}
		Manhattan<Character> mTree = new Manhattan<Character>(db_.getDim()
				* db_.getDim(), sizeLimit_);
		for (int i = 0; i < k; i++) {
			DatabaseElement e = db_.getDatabaseElement(randomIntInRange(
					FIRST_DATABASE_INDEX, numberOfDatabaseElements));
			indexes[i] = e.getIndex();
			mTree.addPoint(e.getPixelsAsDouble(), (char) i);
		}
		return mTree;
	}

	public Manhattan<Character> createManhattanKdTreeFromArray(
			double[][] databaseElements) {
		Manhattan<Character> mTree = new Manhattan<Character>(db_.getDim()
				* db_.getDim(), databaseElements.length);
		for (int clusterID = 0; clusterID < databaseElements.length; clusterID++) {
			mTree.addPoint(databaseElements[clusterID], (char) clusterID);
		}
		return mTree;
	}

	public int randomIntInRange(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}
