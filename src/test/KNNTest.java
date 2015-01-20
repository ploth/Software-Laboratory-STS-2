package test;

import io.ConverterException;
import io.PERST_MNIST_Converter;

import java.io.IOException;

import algorithm.AbstractAlgorithm;
import algorithm.KNN;
import data.PERSTDatabase;

public class KNNTest {

	public static void main(String[] args) throws IOException,
			ConverterException {
		PERSTDatabase db = PERSTDatabase.getInstance();
		// init a database
		PERST_MNIST_Converter.read("ImageData/train-labels.idx1-ubyte",
				"ImageData/train-images.idx3-ubyte", 10000, 30000, true);
		System.out.println(db.getNumberOfDatabaseElements());
		// System.out.println();
		// System.out.println(db.getNumberOfDatabaseElements());
		// System.out.println(db.getNumberOfCorrectDatabaseElements());
		// create unclassified object
		db.createUnclassifiedDatabaseElement(db.getDatabaseElement(1)
				.getPixels());
		// System.out.println();
		// System.out.println(db.getNumberOfDatabaseElements());
		// System.out.println(db.getNumberOfCorrectDatabaseElements());
		// Following has the KNN Button to do:
		KNN knn = new KNN();
		knn.doAlgorithm(AbstractAlgorithm.SQR_EUCLID, 20);
		// System.out.println();
		// System.out.println(db.getNumberOfDatabaseElements());
		// System.out.println(db.getNumberOfCorrectDatabaseElements()); //
		// hasn't
		// changed
		// hopefully
		// at this point the gui has to display all uncorrect database elements
		// he will convert it to a correct element and enters a 5
		int index = db.getNonTrainingdataDatabaseIterator().first().getIndex();
		// System.out.println("algo: "
		// + (int) db.getDatabaseElement(index).getAlgoClassification());
		// System.out
		// .println("correct: "
		// + (int) db.getDatabaseElement(index)
		// .getCorrectClassification());
		// System.out.println();
		// System.out.println("user input .... ");
		db.convertToCorrect(index, (char) 5);
		// System.out.println();
		// System.out.println(db.getNumberOfDatabaseElements());
		// System.out.println(db.getNumberOfCorrectDatabaseElements());
		// System.out.println("algo: "
		// + (int) db.getDatabaseElement(index).getAlgoClassification());
		// System.out
		// .println("correct: "
		// + (int) db.getDatabaseElement(index)
		// .getCorrectClassification());
		db.closeDB();
	}
}
