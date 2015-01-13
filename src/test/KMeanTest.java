package test;

import io.PERST_MNIST_Converter;

import java.io.IOException;

import data.PERSTDatabase;

public class KMeanTest {

	public static void main(String[] args) throws IOException {
		PERSTDatabase db = PERSTDatabase.getInstance();
		// init a database
		PERST_MNIST_Converter.read("ImageData/train-labels.idx1-ubyte",
				"ImageData/train-images.idx3-ubyte");
		// create unclassified object
		db.createUnclassifiedDatabaseElement(db.getDatabaseElement(1)
				.getPixels());
	}

}
