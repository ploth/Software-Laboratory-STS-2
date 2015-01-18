package test;

import io.PERST_MNIST_Converter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import algorithm.KMean;
import data.PERSTDatabase;

public class KMeanTest {

	public static void main(String[] args) throws IOException {
		PERSTDatabase db = PERSTDatabase.getInstance();
		// System.out.println("numberOfDatabaseElements: "
		// + db.getNumberOfDatabaseElements());
		// init a database
		PERST_MNIST_Converter.read("ImageData/train-labels.idx1-ubyte",
				"ImageData/train-images.idx3-ubyte", 1, 1000, true);
		System.out.println("numberOfDatabaseElements: "
				+ db.getNumberOfDatabaseElements());
		KMean kmean = new KMean();
		long time1 = System.currentTimeMillis();
		kmean.doAlgorithm(KMean.SQR_EUCLID, 20);
		long time2 = System.currentTimeMillis();
		System.out.println((time2 - time1));
		db.closeDB();
		String defaultDatabaseName_ = "perstdatabase.dbs";
		Path path = FileSystems.getDefault().getPath(defaultDatabaseName_);
		Files.delete(path);
	}
}
