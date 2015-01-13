package io;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import data.PERSTDatabase;

public class PERST_MNIST_Converter {
	private PERSTDatabase db_;
	private String readLabelPath_ = "ImageData/train-labels.idx1-ubyte";
	private String readImagePath_ = "ImageData/train-images.idx3-ubyte";
	private String writeLabelPath_;
	private String writeImagePath_;

	// read from standard path
	public PERST_MNIST_Converter(String writeLabelPath, String writeImagePath) {
		this.db_ = PERSTDatabase.getInstance();
		this.writeLabelPath_ = writeLabelPath;
		this.writeImagePath_ = writeImagePath;
	}

	public PERST_MNIST_Converter(String writeLabelPath, String writeImagePath,
			String readLabelPath, String readImagePath) {
		this.db_ = PERSTDatabase.getInstance();
		this.readLabelPath_ = readLabelPath;
		this.readImagePath_ = readImagePath;
		this.writeLabelPath_ = writeLabelPath;
		this.writeImagePath_ = writeImagePath;
	}

	public int read() throws IOException {
		DataInputStream labels = new DataInputStream(new FileInputStream(
				readLabelPath_));
		DataInputStream images = new DataInputStream(new FileInputStream(
				readImagePath_));
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
			db_.createDatabaseElement(classification, pixels);
		}
		return numberOfImages;
	}

	public void write() {

	}
}
