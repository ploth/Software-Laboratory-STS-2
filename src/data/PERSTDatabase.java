package data;

import org.garret.perst.Database;
import org.garret.perst.IterableIterator;
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
	public class DatabaseElement extends Persistent {
		private char classification;
		private char[][] pixels_;
		private String name = "dick"; //TODO
		public DatabaseElement(char classification, char[][] pixels){
			this.classification = classification;
			this.pixels_ = pixels;
			PERSTDatabase.getInstance().getDB().addRecord(this);
		}
		public char getClassification_() {
			return classification;
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
	public void getDatabaseElement(char classification){
		String string = String.valueOf(classification);
		for (DatabaseElement e : db_.<DatabaseElement> select(DatabaseElement.class, "name = 'dick'")) {
			int out = (int)e.getPixels_()[2][2];
			System.out.println(out);
		}
		//IterableIterator<DatabaseElement> iterator = db_.<DatabaseElement> select(DatabaseElement.class, "classification="+classification);
	}
	public void closeDB() {
		//db_.commitTransaction();
		//storage_.close();
	}
}
