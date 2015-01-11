package test;

import io.PERST_MNIST_Converter;

import java.io.IOException;

public class MNISTConverterTest {

	public static void main(String[] args) throws IOException {
		PERST_MNIST_Converter mnistconv = new PERST_MNIST_Converter(
				"writeLabelPath", "writeImagePath");
		mnistconv.readMNIST();

		// PERSTDatabase db = PERSTDatabase.getInstance();
		// IterableIterator<DatabaseElement> iterator =
		// db.getDatabaseIterator();
		// while (iterator.hasNext()) {
		// System.out.println((int) iterator.next().getClassification());
		// }
		// db.closeDB();
	}

}
