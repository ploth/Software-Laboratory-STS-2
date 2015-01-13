package test;

import io.ConverterException;
import io.PERST_MNIST_Converter;

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
		try {
			PERST_MNIST_Converter.read("ImageData/train-labels.idx1-ubyte",
					"ImageData/train-images.idx3-ubyte");
		} catch (ConverterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(db.getDatabaseInfos().getNumberOfDatabaseElements());
		System.out.println(db.getNumberOfDatabaseElements_());
		db.closeDB();
	}
}
