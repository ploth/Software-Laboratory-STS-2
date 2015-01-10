package test;

import data.PERSTDatabase;

public class DatabaseTest {
	
	static public void main(String[] args){
		PERSTDatabase pdb = PERSTDatabase.getInstance();
		pdb.createDatabaseElement((char)1);
		
		pdb.closeDB();
	}

}
