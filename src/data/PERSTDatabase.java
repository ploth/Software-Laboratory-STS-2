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
		//private char pixels_ = new
		public DatabaseElement(char classification){
			this.classification_ = classification;
		}
	}
	public static PERSTDatabase getInstance(){
		if(instance_ == null){
			instance_ = new PERSTDatabase(defaultDatabaseName_);
		}
		return instance_;
	}
	public void createDatabaseElement(char classification){
		DatabaseElement DatabaseElement = new DatabaseElement(classification/*TODO*/);
	}
	public void closeDB() {
		//TODO
	}
}
