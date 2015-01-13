package test;

import io.PERST_MNIST_Converter;

import java.io.IOException;

import data.PERSTDatabase;
import data.PERSTDatabase.DatabaseElement;

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
		PERST_MNIST_Converter.read("ImageData/train-labels.idx1-ubyte",
				"ImageData/train-images.idx3-ubyte", 0, 60000, true);
		// System.out.println(db.getDatabaseInfos().getNumberOfDatabaseElements());
		//
		//
		// System.out.println(db.getNumberOfDatabaseElements_());
		// System.out.println((int) db.getDatabaseElement(1)
		// .getCorrectClassification());
		// System.out.println((int) db.getDatabaseElement(1)
		// .getAlgoClassification());
		// System.out.println(db.getDatabaseElement(1).isTrainingdata());
		// System.out.println();
		// System.out.println();

		int index = db.createUnclassifiedDatabaseElement(db.getDatabaseElement(
				20000).getPixels());
		System.out.println("unclassified element created");
		System.out.println("index of unclassified element: " + index);
		DatabaseElement e = db.getUnCorrectDatabaseIterator().first();
		e.setAlgoClassification((char) 2);
		System.out
				.println("algo recognized a 2 and set it to the algo classification value");
		System.out.println("number of database elements: "
				+ db.getNumberOfDatabaseElements());
		System.out.println("number of correct database elements: "
				+ db.getNumberOfCorrectDatabaseElements());
		System.out.println("number of unclassified database elements: "
				+ (db.getNumberOfDatabaseElements() - db
						.getNumberOfCorrectDatabaseElements()));
		db.convertToCorrect(index, (char) 1);
		System.out
				.println("got uncorrect database element and we set the correct value to 1 by hand and converted it to a correct database element (trainingdata)");
		System.out.println("number of database elements: "
				+ db.getNumberOfDatabaseElements());
		System.out.println("number of correct database elements: "
				+ db.getNumberOfCorrectDatabaseElements());
		System.out.println("number of uncorrect database elements: "
				+ (db.getNumberOfDatabaseElements() - db
						.getNumberOfCorrectDatabaseElements()));
		System.out
				.println("correct classification: "
						+ (int) db.getDatabaseElement(index)
								.getCorrectClassification());
		System.out.println("algo classification: "
				+ (int) db.getDatabaseElement(index).getAlgoClassification());

		db.closeDB();
	}
}
