package data;

import java.io.IOException;

import org.garret.perst.Database;
import org.garret.perst.IterableIterator;
import org.garret.perst.Persistent;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;

public class PERSTDatabase {

	private Storage storage_;
	private Database db_;
	private Integer numberOfDatabaseElements_ = 0;
	private Integer numberOfCorrectDatabaseElements_ = 0; // Trainingdata
	private int dim_ = 0;
	private static PERSTDatabase instance_;
	private static String defaultDatabaseName_ = "perstdatabase.dbs";
	public static final char NO_CORRECT_CLASSIFICATION = 43;
	public static final char NO_ALGORITHM_CLASSIFICATION = 42;
	private static final int DEBUG_9000 = 9000;
	private static final char MAX_CHAR = 255;

	private PERSTDatabase(String databaseName) {
		storage_ = StorageFactory.getInstance().createStorage();
		// 1GB
		storage_.open(databaseName, 1024000000); // minimum 40000 bytes
													// (40kbytes)
		db_ = new Database(storage_, false);
		instance_ = this;

		// IterableIterator<DatabaseElement> iter = getDatabaseIterator();
		// while (iter.hasNext()) {
		// DatabaseElement e = iter.next();
		// if (e.isTrainingdata()) {
		// System.out.println("numberOfCorrectDatabaseElements_++");
		// numberOfCorrectDatabaseElements_++;
		// }
		// System.out.println("numberOfDatabaseElements_++");
		// numberOfDatabaseElements_++;
		// }
	}

	private Database getDB() {
		return db_;
	}

	public Integer getNumberOfDatabaseElements() {
		numberOfDatabaseElements_ = 0;
		IterableIterator<DatabaseElement> iter = getDatabaseIterator();
		while (iter.hasNext()) {
			DatabaseElement e = iter.next();
			System.out.println("numberOfDatabaseElements_++");
			numberOfDatabaseElements_++;
		}
		return numberOfDatabaseElements_;
	}

	public Integer getNumberOfCorrectDatabaseElements() {
		numberOfCorrectDatabaseElements_ = 0;
		IterableIterator<DatabaseElement> iter = getDatabaseIterator();
		while (iter.hasNext()) {
			DatabaseElement e = iter.next();
			if (e.isTrainingdata()) {
				numberOfCorrectDatabaseElements_++;
				System.out.println("numberOfCorrectDatabaseElements_++");
			}
		}
		return numberOfCorrectDatabaseElements_;
	}

	public Integer getNumberOfNonTrainingdataDatabaseElements() {
		return (getNumberOfDatabaseElements() - getNumberOfCorrectDatabaseElements());
	}

	public int getDim() {
		// 28! not 28*28!
		if (dim_ == 0) {
			dim_ = (int) Math.sqrt(this.getDatabaseIterator().first()
					.getPixels().length);
			return dim_;
		} else
			return dim_;
	}

	public class DatabaseElement extends Persistent {
		// values have to be int to make a query seach possible
		private int correctClassification = NO_CORRECT_CLASSIFICATION;
		private int algoClassification = NO_ALGORITHM_CLASSIFICATION;
		// 9000 for debugging
		private int clusterID = DEBUG_9000;
		private char[] pixels;
		private int index;
		private boolean trainingdata;

		public DatabaseElement(char[] pixels, int index, boolean trainingdata) {
			this.pixels = pixels;
			this.index = index;
			this.trainingdata = trainingdata;
			PERSTDatabase.getInstance().getDB().addRecord(this);
		}

		public int getClusterID() {
			return clusterID;
		}

		public void setClusterID(int clusterID) {
			this.clusterID = clusterID;
		}

		public int getIndex() {
			return index;
		}

		public char getCorrectClassification() {
			return (char) correctClassification;
		}

		public char getAlgoClassification() {
			return (char) algoClassification;
		}

		public char[] getPixels() {
			return pixels;
		}

		public char[] getPixelsInverted() {
			char[] pixelsInverted = new char[pixels.length];
			for (int i = 0; i < pixels.length; i++) {
				pixelsInverted[i] = (char) (MAX_CHAR - pixels[i]);
			}
			return pixelsInverted;
		}

		public double[] getPixelsAsDouble() {
			double[] pixelsAsDouble = new double[pixels.length];
			for (int i = 0; i < pixelsAsDouble.length; i++) {
				pixelsAsDouble[i] = pixels[i];
			}
			return pixelsAsDouble;
		}

		public int[] getPixelsAsInteger() {
			int[] pixelsAsInteger = new int[pixels.length];
			for (int i = 0; i < pixelsAsInteger.length; i++) {
				pixelsAsInteger[i] = pixels[i];
			}
			return pixelsAsInteger;
		}

		public boolean isTrainingdata() {
			return trainingdata;
		}

		public void setTrainingdata(boolean trainingdata) {
			// this.trainingdata = trainingdata;
			db_.updateKey(this, "trainingdata", trainingdata);
		}

		public void setCorrectClassification(char correctClassification) {
			this.correctClassification = (int) correctClassification;
		}

		public void setAlgoClassification(char algoClassification) {
			this.algoClassification = (int) algoClassification;
		}
	}

	public void updateTrainingdata(DatabaseElement e, boolean trainingdata) {
		db_.updateKey(e, "trainingdata", trainingdata);
	}

	public void updateCorrectClassification(DatabaseElement e,
			char correctClassification) {
		db_.updateKey(e, "correctClassification", correctClassification);
	}

	public void updateAlgoClassification(DatabaseElement e,
			char algoClassification) {
		db_.updateKey(e, "algoClassification", algoClassification);
	}

	public void updateClusterID(DatabaseElement e, int clusterID) {
		db_.updateKey(e, "clusterID", clusterID);
	}

	public void convertToCorrect(int index, char correctClassification) {
		DatabaseElement e = getDatabaseElement(index);
		if (e.isTrainingdata() == false) {
			updateTrainingdata(e, true);
			e.setCorrectClassification(correctClassification);
			numberOfCorrectDatabaseElements_++;
		}
	}

	public static PERSTDatabase getInstance() {
		if (instance_ == null) {
			instance_ = new PERSTDatabase(defaultDatabaseName_);
		}
		return instance_;
	}

	// returns index
	public int createCorrectDatabaseElement(char correctClassification,
			char[] pixels, boolean trainingdata) {
		numberOfDatabaseElements_++;
		if (trainingdata) {
			numberOfCorrectDatabaseElements_++;
		}
		DatabaseElement DatabaseElement = new DatabaseElement(pixels,
				numberOfDatabaseElements_, trainingdata);
		DatabaseElement.setCorrectClassification(correctClassification);
		return numberOfDatabaseElements_;
	}

	// return index
	public int createUnclassifiedDatabaseElement(char[] pixels) {
		numberOfDatabaseElements_++;
		DatabaseElement DatabaseElement = new DatabaseElement(pixels,
				numberOfDatabaseElements_, false);
		return numberOfDatabaseElements_;
	}

	public IterableIterator<DatabaseElement> getDatabaseIterator() {
		return db_.<DatabaseElement> getRecords(DatabaseElement.class);
	}

	public IterableIterator<DatabaseElement> getCorrectDatabaseIterator() {
		return db_.<DatabaseElement> select(DatabaseElement.class,
				"trainingdata = true");
	}

	public IterableIterator<DatabaseElement> getNonTrainingdataDatabaseIterator() {
		return db_.<DatabaseElement> select(DatabaseElement.class,
				"trainingdata = false");
	}

	public IterableIterator<DatabaseElement> getClusteredDatabaseIterator(
			int clusterID) {
		String query = "clusterID = " + String.valueOf(clusterID);
		IterableIterator<DatabaseElement> iterator = db_
				.<DatabaseElement> select(DatabaseElement.class, query);
		return iterator;
	}

	public DatabaseElement getDatabaseElement(int index) {
		String indexString = "index = " + String.valueOf(index);
		return db_.<DatabaseElement> select(DatabaseElement.class, indexString)
				.first();
	}

	public void closeDB() {
		int td = 0;
		int dbe = 0;
		IterableIterator<DatabaseElement> iter = getDatabaseIterator();
		while (iter.hasNext()) {
			DatabaseElement e = iter.next();
			if (e.isTrainingdata()) {
				td++;
			}
			dbe++;
		}
		System.out.println("database elements: " + dbe);
		System.out.println("training data: " + td);
		db_.commitTransaction();
		storage_.close();
	}

	public void deleteDatabase() throws IOException {
		db_.dropTable(DatabaseElement.class);
		numberOfDatabaseElements_ = 0;
	}
}
