package io;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.garret.perst.IterableIterator;

import data.PERSTDatabase.DatabaseElement;

// This is a converter to convert from our PERST Database to png files and from PNG files to our database.
public class PERST_PNG_Converter extends AbstractConverter {

	private static final int BAND = 0;
	private static final int MAX_CHAR = 255;

	public static void read(String path) throws IOException, ConverterException {
		if (path.endsWith("png")) {
			File file = new File(path);
			BufferedImage image = ImageIO.read(file);
			WritableRaster wr = image.getRaster();
			int hmw = image.getHeight() * image.getWidth();
			char pixels[] = new char[hmw];
			for (int i = 0; i < hmw; i++) {
				int x = i % (int) Math.sqrt(hmw);
				int y = i / (int) Math.sqrt(hmw);
				pixels[i] = (char) (MAX_CHAR - wr.getSample(x, y, BAND));
			}
			getDb_().createUnclassifiedDatabaseElement(pixels);
		} else
			throw new ConverterException("Please select a .png file.");
	}

	public static void write(String folderPath) throws IOException,
			ConverterException {
		IterableIterator<DatabaseElement> iter = getDb_()
				.getCorrectDatabaseIterator();
		while (iter.hasNext()) {
			DatabaseElement e = iter.next();
			writeOneImage(e.getPixels(), folderPath + "element" + e.getIndex()
					+ "-class" + (int) e.getCorrectClassification() + ".png");
		}
	}

	// Method to generate a single png file from pixels
	public static void writeOneImage(char[] pixels, String path)
			throws IOException, ConverterException {
		if (path.endsWith("png")) {
			File file = new File(path);
			int i = 0;
			int dim = (int) Math.sqrt(pixels.length);
			BufferedImage image = new BufferedImage(dim, dim,
					BufferedImage.TYPE_BYTE_GRAY); // TYPE_BYTE_GRAY
			for (int x = 0; x < dim; x++) {
				for (int y = 0; y < dim; y++) {
					WritableRaster raster = image.getRaster();
					raster.setSample(y, x, BAND, MAX_CHAR - pixels[i]); // mnist
																		// uses
					// inverted standard
					i++;
				}
			}
			ImageIO.write(image, "png", file);
		} else
			throw new ConverterException("Please save as .png file.");
	}
}
