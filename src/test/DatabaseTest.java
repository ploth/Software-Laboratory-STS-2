package test;

import org.garret.perst.IterableIterator;

import data.PERSTDatabase;
import data.PERSTDatabase.DatabaseElement;

public class DatabaseTest {

	static public void main(String[] args) {
		PERSTDatabase pdb = PERSTDatabase.getInstance();
		char classification1 = 1;
		char[] pixels1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		char classification2 = 2;
		char[] pixels2 = { 1, 2, 3, 4, 5, 6, 7, 8, 255 };
		// pdb.createDatabaseElement(classification1, pixels1);
		// pdb.createDatabaseElement(classification2, pixels2);
		// pdb.queryDatabaseElements((char) 2);
		IterableIterator<DatabaseElement> i = pdb.getDatabaseIterator();
		while (i.hasNext()) {
			System.out.println((int) i.next().getClassification());
		}
		pdb.closeDB();
	}
}
