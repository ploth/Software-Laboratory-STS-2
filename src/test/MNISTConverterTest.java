package test;

import java.io.IOException;

import data.PERSTDatabase;

public class MNISTConverterTest {

	public static void main(String[] args) throws IOException {
		// get all classifications from database
		// PERSTDatabase db = PERSTDatabase.getInstance();
		// PERST_MNIST_Converter mnistconv = new PERST_MNIST_Converter(
		// "writeLabelPath", "writeImagePath");
		// mnistconv.readMNIST();
		//
		// IterableIterator<DatabaseElement> iterator =
		// db.getDatabaseIterator();
		// // int entries = 0;
		// while (iterator.hasNext()) {
		// System.out.println((int) iterator.next().getClassification());
		// // entries++;
		// }
		// // System.out.println(entries);
		// db.closeDB();
		//
		//
		// get number of database entries
		PERSTDatabase db = PERSTDatabase.getInstance();
		// PERST_MNIST_Converter mnistconv = new
		// PERST_MNIST_Converter("writeLabelPath", "writeImagePath");
		// mnistconv.readMNIST();
		System.out.println(db.getDatabaseInfos().getNumberOfDatabaseElements());
		db.closeDB();
	}
}
