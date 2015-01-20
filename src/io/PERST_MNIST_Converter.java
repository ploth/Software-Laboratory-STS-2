package io;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.garret.perst.IterableIterator;

import data.PERSTDatabase.DatabaseElement;

public class PERST_MNIST_Converter extends AbstractConverter {

	// TODO: Exceptions
	public static int read(String readLabelPath, String readImagePath,
			int rangeStart, int rangeEnd, boolean trainingdata)
			throws IOException {
		if (rangeStart <= 0) {
			rangeStart = 1;
			System.err.println("Hey, what are you doing? (lower limit set to  "
					+ rangeStart + ".");
		}
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
		int numberOfImages = images.readInt();
		int numberOfRows = images.readInt();
		int numberOfColumns = images.readInt();
		if (numberOfLabels < rangeEnd) {
			rangeEnd = numberOfLabels;
			System.err
					.println("Range is greater than MNIST database (upper limit set to "
							+ rangeEnd + ".");
		}
		if (numberOfLabels != numberOfImages) {
			System.err
					.println("Image file and label file do not contain the same number of entries.");
			System.err.println("Label file contains: " + numberOfLabels);
			System.err.println("Image file contains: " + numberOfImages);
			System.exit(0);
		}
		int numPixels = numberOfRows * numberOfColumns;
		images.skipBytes(28 * 28 * (rangeStart - 1));
		labels.skipBytes((rangeStart - 1));
		int dataCount = 0;
		while ((labels.available() > 0) && dataCount <= (rangeEnd - rangeStart)) {
			char classification = (char) labels.readByte();
			char[] pixels = new char[numPixels];
			for (int i = 0; i < numPixels; i++) {
				pixels[i] = (char) images.readUnsignedByte();
			}
			getDb_().createCorrectDatabaseElement(classification, pixels,
					trainingdata);
			dataCount++;
		}
		labels.close();
		images.close();
		return numberOfImages;
	}

	public static void write(String writeLabelPath, String writeImagePath)
			throws IOException {
		IterableIterator<DatabaseElement> iter = getDb_()
				.getCorrectDatabaseIterator();
		int dim = getDb_().getDim();
		FileWriter fwLabel = new FileWriter(writeLabelPath);
		FileWriter fwImage = new FileWriter(writeImagePath);
		BufferedWriter bwLabel = new BufferedWriter(fwLabel);
		BufferedWriter bwImage = new BufferedWriter(fwImage);
		while (iter.hasNext()) {
			DatabaseElement e = iter.next();
			bwImage.write(e.getPixels());
		}
		bwLabel.close();
		bwImage.close();
		fwLabel.close();
		fwImage.close();
	}
}
