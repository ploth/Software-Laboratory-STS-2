package io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.garret.perst.IterableIterator;

import data.PERSTDatabase.DatabaseElement;

public class PERST_MNIST_Converter extends AbstractConverter {

	private static final int IMAGE_DIM = 28;
	private static final int MAGIC_NUMBER_LABELS = 2049;
	private static final int MAGIC_NUMBER_IMAGES = 2051;

	public static int read(String readLabelPath, String readImagePath,
			int rangeStart, int rangeEnd, boolean trainingdata)
			throws IOException, ConverterException {
		if (rangeStart <= 0) {
			throw new ConverterException("Please enter a minimum value of 1");
		}
		DataInputStream labels = new DataInputStream(new FileInputStream(
				readLabelPath));
		DataInputStream images = new DataInputStream(new FileInputStream(
				readImagePath));
		int magicNumber = labels.readInt();
		if (magicNumber != 2049) {
			labels.close();
			images.close();
			throw new ConverterException("Label file has wrong magic number: "
					+ magicNumber + " (should be 2049)");
		}
		magicNumber = images.readInt();
		if (magicNumber != 2051) {
			labels.close();
			images.close();
			throw new ConverterException("Image file has wrong magic number: "
					+ magicNumber + " (should be 2051)");
		}
		int numberOfLabels = labels.readInt();
		int numberOfImages = images.readInt();
		int numberOfRows = images.readInt();
		int numberOfColumns = images.readInt();
		if (numberOfLabels < rangeEnd) {
			labels.close();
			images.close();
			throw new ConverterException(
					"Please enter a maximum value of 60000");
		}
		if (numberOfLabels != numberOfImages) {
			labels.close();
			images.close();
			throw new ConverterException(
					"Image file and label file do not contain the same number of entries.");
		}
		int numPixels = numberOfRows * numberOfColumns;
		images.skipBytes(IMAGE_DIM * IMAGE_DIM * (rangeStart - 1));
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
		DataOutputStream labels = new DataOutputStream(new FileOutputStream(
				writeLabelPath));
		DataOutputStream images = new DataOutputStream(new FileOutputStream(
				writeImagePath));
		int dim = getDb_().getDim();
		// magic numbers
		images.writeInt(MAGIC_NUMBER_IMAGES);
		labels.writeInt(MAGIC_NUMBER_LABELS);
		// number of items
		images.writeInt(getDb_().getNumberOfTrainingdataDatabaseElements());
		labels.writeInt(getDb_().getNumberOfTrainingdataDatabaseElements());
		// image dimensions (image file)
		images.writeInt(dim);
		images.writeInt(dim);
		while (iter.hasNext()) {
			// labels
			DatabaseElement e = iter.next();
			labels.writeByte((int) e.getCorrectClassification());
			// pixels
			for (int i = 0; i < (dim * dim); i++) {
				images.writeByte((int) e.getPixels()[i]);
			}
		}
		labels.close();
		images.close();
	}
}
