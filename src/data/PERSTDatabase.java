package data;

import org.garret.perst.Database;
import org.garret.perst.IterableIterator;
import org.garret.perst.Persistent;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;

public class PERSTDatabase {

	private Storage storage_;
	private Database db_;
	private long numberOfDatabaseElements_ = 0;
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

	public long getNumberOfDatabaseElements_() {
		return numberOfDatabaseElements_;
	}

	public class DatabaseElement extends Persistent {

		private int classification; // int to make query search possible
		private char[] pixels;

		public DatabaseElement(char classification, char[] pixels) {
			this.classification = (int) classification;
			this.pixels = pixels;
			PERSTDatabase.getInstance().getDB().addRecord(this);
		}

		public char getClassification() {
			return (char) classification;
		}

		public char[] getPixels() {
			return pixels;
		}
	}

	public static PERSTDatabase getInstance() {
		if (instance_ == null) {
			instance_ = new PERSTDatabase(defaultDatabaseName_);
		}
		return instance_;
	}

	public void createDatabaseElement(char classification, char[] pixels) {
		DatabaseElement DatabaseElement = new DatabaseElement(classification,
				pixels);
		numberOfDatabaseElements_++;
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

	public void closeDB() {
		db_.commitTransaction();
		storage_.close();
	}
}
