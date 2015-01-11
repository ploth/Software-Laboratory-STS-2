package test;

import io.PERST_MNIST_Converter;

import java.io.IOException;

import org.garret.perst.IterableIterator;

import data.PERSTDatabase;
import data.PERSTDatabase.DatabaseElement;

public class MNISTConverterTest {

	public static void main(String[] args) throws IOException {
		PERST_MNIST_Converter mnistconv = new PERST_MNIST_Converter(
				"writeLabelPath", "writeImagePath");
		mnistconv.readMNIST();

		PERSTDatabase db = PERSTDatabase.getInstance();
		IterableIterator<DatabaseElement> iterator = db.getDatabaseIterator();
		int entries = 0;
		while (iterator.hasNext()) {
			System.out.println((int) iterator.next().getClassification());
			entries++;
		}
		System.out.println(entries);
		db.closeDB();
	}

}
