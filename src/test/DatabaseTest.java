package test;

import data.PERSTDatabase;
import data.PERSTDatabase.DatabaseElement;

public class DatabaseTest {
	
	static public void main(String[] args){
		PERSTDatabase pdb = PERSTDatabase.getInstance();
		char classification = 1;
		char[][] pixels = {{1,2,3},{4,5,6},{7,8,9}};
		pdb.createDatabaseElement(classification, pixels);
		pdb.getDatabaseElement(classification);
		pdb.closeDB();
	}
}
