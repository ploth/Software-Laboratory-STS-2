package data;

import org.garret.perst.Database;
import org.garret.perst.IterableIterator;
import org.garret.perst.Persistent;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;

public class PERSTDatabase {

	private Storage storage;
	private Database db;
	private Integer numberOfDatabaseElements = 0;
	private Integer numberOfCorrectDatabaseElements = 0; // Trainingdata
	private int dim = 0;
	private static PERSTDatabase instance;
	private static final String defaultDatabaseName = "perstdatabase.dbs";
	public static final char NO_CORRECT_CLASSIFICATION = 43;
	public static final char NO_ALGORITHM_CLASSIFICATION = 42;
	private static final int DEBUG_9000 = 9000;
	private static final char MAX_CHAR = 255;

	private PERSTDatabase(String databaseName) {
		storage = StorageFactory.getInstance().createStorage();
		// 1GB
		storage.open(databaseName, 1024000000); // minimum 40000 bytes
													// (40kbytes)
		db = new Database(storage, false);
		instance = this;
		IterableIterator<DatabaseElement> iter = getDatabaseIterator();
		while (iter.hasNext()) {
			DatabaseElement e = iter.next();
			if (e.isTrainingdata()) {
				numberOfCorrectDatabaseElements++;
			}
			numberOfDatabaseElements++;
		}
	}

	private Database getDB() {
		return db;
	}

	public static String getDBName() {
		return defaultDatabaseName;
	}

	public Integer getNumberOfDatabaseElements() {
		return numberOfDatabaseElements;
	}

	public Integer getNumberOfTrainingdataDatabaseElements() {
		return numberOfCorrectDatabaseElements;
	}

	public Integer getNumberOfNonTrainingdataDatabaseElements() {
		return (getNumberOfDatabaseElements() - getNumberOfTrainingdataDatabaseElements());
	}

	public int getDim() {
		// 28! not 28*28!
		if (dim == 0) {
			dim = (int) Math.sqrt(this.getDatabaseIterator().first()
					.getPixels().length);
			return dim;
		} else
			return dim;
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
	}

	public void updateTrainingdata(DatabaseElement e, boolean trainingdata) {
		db.updateKey(e, "trainingdata", trainingdata);
	}

	public void updateCorrectClassification(DatabaseElement e,
			char correctClassification) {
		db.updateKey(e, "correctClassification", correctClassification);
	}

	public void updateAlgoClassification(DatabaseElement e,
			char algoClassification) {
		db.updateKey(e, "algoClassification", algoClassification);
	}

	public void updateClusterID(DatabaseElement e, int clusterID) {
		db.updateKey(e, "clusterID", clusterID);
	}

	public void convertToCorrect(int index, char correctClassification) {
		DatabaseElement e = getDatabaseElement(index);
		if (e.isTrainingdata() == false) {
			updateTrainingdata(e, true);
			updateCorrectClassification(e, correctClassification);
			numberOfCorrectDatabaseElements++;
		}
	}

	public static PERSTDatabase getInstance() {
		if (instance == null) {
			instance = new PERSTDatabase(defaultDatabaseName);
		}
		return instance;
	}

	// returns index
	public int createCorrectDatabaseElement(char correctClassification,
			char[] pixels, boolean trainingdata) {
		numberOfDatabaseElements++;
		if (trainingdata) {
			numberOfCorrectDatabaseElements++;
		}
		DatabaseElement DatabaseElement = new DatabaseElement(pixels,
				numberOfDatabaseElements, trainingdata);
		updateCorrectClassification(DatabaseElement, correctClassification);
		return numberOfDatabaseElements;
	}

	// return index
	public int createUnclassifiedDatabaseElement(char[] pixels) {
		numberOfDatabaseElements++;
		DatabaseElement DatabaseElement = new DatabaseElement(pixels,
				numberOfDatabaseElements, false);
		return numberOfDatabaseElements;
	}

	public IterableIterator<DatabaseElement> getDatabaseIterator() {
		return db.<DatabaseElement> getRecords(DatabaseElement.class);
	}

	public IterableIterator<DatabaseElement> getCorrectDatabaseIterator() {
		return db.<DatabaseElement> select(DatabaseElement.class,
				"trainingdata = true");
	}

	public IterableIterator<DatabaseElement> getNonTrainingdataDatabaseIterator() {
		return db.<DatabaseElement> select(DatabaseElement.class,
				"trainingdata = false");
	}

	public IterableIterator<DatabaseElement> getClusteredDatabaseIterator(
			int clusterID) {
		String query = "clusterID = " + String.valueOf(clusterID);
		IterableIterator<DatabaseElement> iterator = db
				.<DatabaseElement> select(DatabaseElement.class, query);
		return iterator;
	}

	public DatabaseElement getDatabaseElement(int index) {
		String indexString = "index = " + String.valueOf(index);
		return db.<DatabaseElement> select(DatabaseElement.class, indexString)
				.first();
	}

	public void closeDB() {
		db.commitTransaction();
		storage.close();
	}

}
