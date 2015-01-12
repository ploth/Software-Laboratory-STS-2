package data;

import org.garret.perst.Database;
import org.garret.perst.IterableIterator;
import org.garret.perst.Persistent;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;

public class PERSTDatabase {

	private Storage storage_;
	private Database db_;
	private Integer numberOfDatabaseElements_ = 0;
	private int dim_ = 0;
	private static PERSTDatabase instance_;
	private static String defaultDatabaseName_ = "perstdatabase.dbs";

	private PERSTDatabase(String databaseName) {
		storage_ = StorageFactory.getInstance().createStorage();
		// TODO: too big?
		// 1GB
		storage_.open(databaseName, 1024000000); // minimum 40000 bytes
													// (40kbytes)
		db_ = new Database(storage_, false);
		instance_ = this;
		// count database object
		IterableIterator<DatabaseElement> iter = this.getDatabaseIterator();
		while (iter.hasNext()) {
			iter.next();
			numberOfDatabaseElements_++;
		}
	}

	private Database getDB() {
		return db_;
	}

	public Integer getNumberOfDatabaseElements_() {
		return numberOfDatabaseElements_;
	}

	public int getDim_() {
		// 28! not 28*28!
		if (dim_ == 0) {
			dim_ = (int) Math.sqrt(this.getDatabaseIterator().first()
					.getPixels().length);
			return dim_;
		} else
			return dim_;
	}

	public class DatabaseElement extends Persistent {

		private int classification; // int to make query search possible
		private char[] pixels;
		private int index;

		public DatabaseElement(char classification, char[] pixels, int index) {
			this.classification = (int) classification;
			this.pixels = pixels;
			this.index = index;
			PERSTDatabase.getInstance().getDB().addRecord(this);
		}

		public char getClassification() {
			return (char) classification;
		}

		public char[] getPixels() {
			return pixels;
		}

		public double[] getPixelsAsDouble() {
			double[] pixelsAsDouble = new double[pixels.length];
			for (int i = 0; i < pixelsAsDouble.length; i++) {
				pixelsAsDouble[i] = pixels[i];
			}
			return pixelsAsDouble;
		}
	}

	public static PERSTDatabase getInstance() {
		if (instance_ == null) {
			instance_ = new PERSTDatabase(defaultDatabaseName_);
		}
		return instance_;
	}

	public void createDatabaseElement(char classification, char[] pixels) {
		numberOfDatabaseElements_++;
		DatabaseElement DatabaseElement = new DatabaseElement(classification,
				pixels, numberOfDatabaseElements_);
	}

	public IterableIterator<DatabaseElement> getDatabaseIterator() {
		return db_.<DatabaseElement> getRecords(DatabaseElement.class);
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
}
