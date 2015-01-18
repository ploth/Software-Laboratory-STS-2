package test;

import io.PERST_CSV_Converter;

import java.io.IOException;

import data.PERSTDatabase;

public class CSVConverterTest {

	public static void main(String[] args) throws IOException {
		PERSTDatabase db = PERSTDatabase.getInstance();
		// write
		// PERST_MNIST_Converter.read("ImageData/train-labels.idx1-ubyte",
		// "ImageData/train-images.idx3-ubyte", 1, 60000, true);
		// long time1 = System.currentTimeMillis();
		// PERST_CSV_Converter.write("dick");
		// long time2 = System.currentTimeMillis();
		// System.out.println((time2 - time1));
		// read
		long time1 = System.currentTimeMillis();
		PERST_CSV_Converter.read("dick");
		long time2 = System.currentTimeMillis();
		System.out.println((time2 - time1));
		System.out.println(db.getNumberOfCorrectDatabaseElements());
		System.out.println((int) db.getCorrectDatabaseIterator().first()
				.getCorrectClassification());
		db.closeDB();
	}
}
