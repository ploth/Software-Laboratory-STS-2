package test;

import java.io.IOException;

import algorithm.KMean;
import data.PERSTDatabase;

public class KMeanTest {

	public static void main(String[] args) throws IOException {
		PERSTDatabase db = PERSTDatabase.getInstance();
		// System.out.println("numberOfDatabaseElements: "
		// + db.getNumberOfDatabaseElements());
		// init a database
		// PERST_MNIST_Converter.read("ImageData/train-labels.idx1-ubyte",
		// "ImageData/train-images.idx3-ubyte", 1, 60000, true);
		System.out.println("numberOfDatabaseElements: "
				+ db.getNumberOfDatabaseElements());
		KMean kmean = new KMean();
		kmean.doAlgorithm(KMean.SQR_EUCLID, 20);
		db.closeDB();
	}
}
