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

	private PERSTDatabase(String databaseName) {
		storage_ = StorageFactory.getInstance().createStorage();
		// 1GB
		storage_.open(databaseName, 1024000000); // minimum 40000 bytes
													// (40kbytes)
		db_ = new Database(storage_, false);
		instance_ = this;
	}

	private Database getDB() {
		return db_;
	}

	public Integer getNumberOfDatabaseElements() {
		if (numberOfDatabaseElements_ == 0) {
			IterableIterator<DatabaseElement> iter = this.getDatabaseIterator();
			while (iter.hasNext()) {
				iter.next();
				numberOfDatabaseElements_++;
			}
		}
		return numberOfDatabaseElements_;
	}

	public Integer getNumberOfCorrectDatabaseElements() {
		if (numberOfCorrectDatabaseElements_ == 0) {
			IterableIterator<DatabaseElement> iter = this
					.getCorrectDatabaseIterator();
			while (iter.hasNext()) {
				iter.next();
				numberOfCorrectDatabaseElements_++;
			}
		}
		return numberOfCorrectDatabaseElements_;
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

		// 1337 for debugging
		private int correctClassification = 1337;// int to make query
													// search possible
		// 42 for debugging
		private int algoClassification = 42; // int to make query seach
												// possible
		private char[] pixels;
		private int index;
		private boolean trainingdata;

		public DatabaseElement(char[] pixels, int index, boolean trainingdata) {
			this.pixels = pixels;
			this.index = index;
			this.trainingdata = trainingdata;
			PERSTDatabase.getInstance().getDB().addRecord(this);
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

		public double[] getPixelsAsDouble() {
			// long time1 = 0, time2 = 0;
			// time1 = System.currentTimeMillis();
			double[] pixelsAsDouble = new double[pixels.length];
			for (int i = 0; i < pixelsAsDouble.length; i++) {
				pixelsAsDouble[i] = pixels[i];
			}
			// time2 = System.currentTimeMillis();
			// System.out.println((time2 - time1));
			return pixelsAsDouble;
		}

		public boolean isTrainingdata() {
			return trainingdata;
		}

		public void setTrainingdata(boolean trainingdata) {
			this.trainingdata = trainingdata;
		}

		public void setCorrectClassification(char correctClassification) {
			this.correctClassification = (int) correctClassification;
		}

		public void setAlgoClassification(char algoClassification) {
			this.algoClassification = (int) algoClassification;
		}
	}

	public void convertToCorrect(int index, char correctClassification) {
		DatabaseElement e = getDatabaseElement(index);
		if (e.isTrainingdata() == false) {
			e.setTrainingdata(true);
			e.setCorrectClassification(correctClassification);
			// System.out.println("debug: " + numberOfCorrectDatabaseElements_);
			numberOfCorrectDatabaseElements_++;
			// System.out.println("debug: " + numberOfCorrectDatabaseElements_);
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

	// // returns index
	// public int createCorrectDatabaseElement(char correctClassification,
	// char[] pixels) {
	// numberOfDatabaseElements_++;
	// DatabaseElement DatabaseElement = new DatabaseElement(pixels,
	// numberOfDatabaseElements_, true);
	// DatabaseElement.setCorrectClassification(correctClassification);
	// return numberOfDatabaseElements_;
	// }
	//
	// // returns index
	// public int createAlgoDatabaseElement(char algoClassification, char[]
	// pixels) {
	// numberOfDatabaseElements_++;
	// DatabaseElement DatabaseElement = new DatabaseElement(pixels,
	// numberOfDatabaseElements_, false);
	// DatabaseElement.setAlgoClassification(algoClassification);
	// return numberOfDatabaseElements_;
	// }

	public IterableIterator<DatabaseElement> getDatabaseIterator() {
		return db_.<DatabaseElement> getRecords(DatabaseElement.class);
	}

	public IterableIterator<DatabaseElement> getCorrectDatabaseIterator() {
		return db_.<DatabaseElement> select(DatabaseElement.class,
				"trainingdata = true");
	}

	public IterableIterator<DatabaseElement> getUnCorrectDatabaseIterator() {
		return db_.<DatabaseElement> select(DatabaseElement.class,
				"trainingdata = false");
	}

	public IterableIterator<DatabaseElement> queryDatabaseElements(
			char classification) {
		String query = "classification = "
				+ String.valueOf((int) classification);
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
		db_.commitTransaction();
		storage_.close();
	}

	public void deleteDatabase() throws IOException {
		db_.dropTable(DatabaseElement.class);
	}
}
