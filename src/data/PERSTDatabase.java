package data;

import org.garret.perst.Database;
import org.garret.perst.Persistent;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;

public class PERSTDatabase{

	private Storage storage_;
	private Database db_;
	private static PERSTDatabase instance_;
	private static String defaultDatabaseName_ = "perstdatabase.dbs";
	private PERSTDatabase(String databaseName){
		storage_ = StorageFactory.getInstance().createStorage();
		//TODO: too big?
		//1GB
		storage_.open(databaseName, 1024000000); //minimum 40000 bytes (40kbytes)
		db_ = new Database(storage_, false); 
		instance_ = this;
	}
	private Database getDB() {
		return db_;
	}
	private Storage getStorage() {
		return storage_;
	}
	private class DatabaseElement extends Persistent {
		private char classification_;
		private char[][] pixels_;
		public DatabaseElement(char classification, char[][] pixels){
			this.classification_ = classification;
			this.pixels_ = pixels;
			PERSTDatabase.getInstance().getDB().addRecord(this);
		}
		public char getClassification_() {
			return classification_;
		}
		public char[][] getPixels_() {
			return pixels_;
		}
	}
	public static PERSTDatabase getInstance(){
		if(instance_ == null){
			instance_ = new PERSTDatabase(defaultDatabaseName_);
		}
		return instance_;
	}
	public void createDatabaseElement(char classification, char[][] pixels){
		DatabaseElement DatabaseElement = new DatabaseElement(classification, pixels);
	}
	public void closeDB() {
		//TODO
	}
}
