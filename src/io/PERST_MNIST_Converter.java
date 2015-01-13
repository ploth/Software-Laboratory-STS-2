package io;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class PERST_MNIST_Converter extends AbstractConverter {

	public static int read(String readLabelPath, String readImagePath)
			throws IOException {
		DataInputStream labels = new DataInputStream(new FileInputStream(
				readLabelPath));
		DataInputStream images = new DataInputStream(new FileInputStream(
				readImagePath));
		int magicNumber = labels.readInt();
		if (magicNumber != 2049) {
			System.err.println("Label file has wrong magic number: "
					+ magicNumber + " (should be 2049)");
			System.exit(0);
		}
		magicNumber = images.readInt();
		if (magicNumber != 2051) {
			System.err.println("Image file has wrong magic number: "
					+ magicNumber + " (should be 2051)");
			System.exit(0);
		}
		int numberOfLabels = labels.readInt();
		// debug
		// System.out.println("numLabels: " + numLabels);
		int numberOfImages = images.readInt();
		int numberOfRows = images.readInt();
		int numberOfColumns = images.readInt();
		if (numberOfLabels != numberOfImages) {
			System.err
					.println("Image file and label file do not contain the same number of entries.");
			System.err.println("Label file contains: " + numberOfLabels);
			System.err.println("Image file contains: " + numberOfImages);
			System.exit(0);
		}
		int numPixels = numberOfRows * numberOfColumns;
		while (labels.available() > 0) {
			char classification = (char) labels.readByte();
			char[] pixels = new char[numPixels];
			for (int i = 0; i < numPixels; i++) {
				pixels[i] = (char) images.readUnsignedByte();
			}
			getDb_().createCorrectDatabaseElement(classification, pixels, true);
		}
		return numberOfImages;
	}

	public void write(String writeLabelPath, String writeImagePath) {

	}
}
