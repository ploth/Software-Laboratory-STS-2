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
		// PERST_MNIST_Converter.read("ImageData/train-labels.idx1-ubyte",
		// "ImageData/train-images.idx3-ubyte");
		// System.out.println(db.getDatabaseInfos().getNumberOfDatabaseElements());
		System.out.println(db.getNumberOfDatabaseElements_());
		System.out.println((int) db.getDatabaseElement(1)
				.getCorrectClassification());
		System.out.println((int) db.getDatabaseElement(1)
				.getAlgoClassification());
		System.out.println(db.getDatabaseElement(1).isTrainingdata());
		db.closeDB();
	}
}
