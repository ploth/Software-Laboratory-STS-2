package test;

import data.PERSTDatabase;

public class DatabaseTest {
	
	static public void main(String[] args){
		PERSTDatabase pdb = PERSTDatabase.getInstance();
		char classification = 1;
		char[][] pixels = {{1,2,3},{4,5,6},{7,8,9}};
		pdb.createDatabaseElement(classification, pixels);
		//retrieve
		pdb.closeDB();
	}

}
