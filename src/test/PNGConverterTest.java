package test;

import io.PERST_PNG_Converter;

import java.io.IOException;

import data.PERSTDatabase;
import data.PERSTDatabase.DatabaseElement;

public class PNGConverterTest {

	public static void main(String[] args) throws IOException {
		// PERSTDatabase db = PERSTDatabase.getInstance();
		// System.out.println("numberOfDatabaseElements: "
		// + db.getNumberOfDatabaseElements());
		// IterableIterator<DatabaseElement> iter = db.getDatabaseIterator();
		// DatabaseElement e = iter.next();
		// // which database entry?
		// int number = 2;
		// for (int i = 0; i < number; i++) {
		// e = iter.next();
		// }
		// System.out.println("classification: "
		// + (int) e.getCorrectClassification());
		// // print array
		// for (int r = 0; r < Math.sqrt(e.getPixels().length); r++) {
		// System.out.println();
		// for (int t = 0; t < Math.sqrt(e.getPixels().length); t++) {
		// System.out.print((int) e.getPixels()[r
		// * (int) Math.sqrt(e.getPixels().length) + t]);
		// }
		// }
		// PERST_PNG_Converter.write(e.getCorrectClassification(),
		// e.getPixels(),
		// "src/test/test.png");
		//
		//
		// PERST_MNIST_Converter.read("ImageData/train-labels.idx1-ubyte",
		// "ImageData/train-images.idx3-ubyte", 1, 1, true);
		PERSTDatabase db = PERSTDatabase.getInstance();
		// System.out.println("numberOfDatabaseElements: "
		// + db.getNumberOfDatabaseElements());
		// DatabaseElement e = db.getDatabaseIterator().first();
		// PERST_PNG_Converter.write(e.getCorrectClassification(),
		// e.getPixels(),
		// "bitch.png");
		PERST_PNG_Converter.read("bitch.png");
		System.out.println(db.getNumberOfNoTrainingdataDatabaseElements());
		DatabaseElement e = db.getNonTrainingdataDatabaseIterator().first();
		PERST_PNG_Converter.write(e.getCorrectClassification(), e.getPixels(),
				"snitch.png");
		db.closeDB();
	}
}
