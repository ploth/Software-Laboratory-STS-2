package io;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PERST_PNG_Converter extends AbstractConverter {

	private static final int BAND = 0;

	public static void read(String path) throws IOException {
		// TODO magic number party
		File file = new File(path);
		BufferedImage image = ImageIO.read(file);
		WritableRaster wr = image.getRaster();
		char pixels[] = new char[784];
		for (int i = 0; i < 784; i++) {
			int x = i % 28;
			int y = i / 28;
			pixels[i] = (char) wr.getSample(x, y, BAND);
		}
		getDb_().createUnclassifiedDatabaseElement(pixels);
	}

	public static void write(char classification, char[] pixels, String path) {
		File file = new File(path);
		int i = 0;
		int dim = (int) Math.sqrt(pixels.length);
		BufferedImage image = new BufferedImage(dim, dim,
				BufferedImage.TYPE_BYTE_GRAY); // TYPE_BYTE_GRAY
		for (int x = 0; x < dim; x++) {
			for (int y = 0; y < dim; y++) {
				WritableRaster raster = image.getRaster();
				raster.setSample(y, x, 0, 255 - pixels[i]); // mnist uses
															// inverted standard
				i++;
			}
		}
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void write(int classification, int[] pixels, String path) {
		File file = new File(path);
		int i = 0;
		int dim = (int) Math.sqrt(pixels.length);
		BufferedImage image = new BufferedImage(dim, dim,
				BufferedImage.TYPE_BYTE_GRAY); // TYPE_BYTE_GRAY
		for (int x = 0; x < dim; x++) {
			for (int y = 0; y < dim; y++) {
				WritableRaster raster = image.getRaster();
				raster.setSample(y, x, 0, 255 - pixels[i]); // mnist uses
															// inverted standard
				i++;
			}
		}
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void write(int classification, double[] pixels, String path) {
		File file = new File(path);
		int i = 0;
		int dim = (int) Math.sqrt(pixels.length);
		BufferedImage image = new BufferedImage(dim, dim,
				BufferedImage.TYPE_BYTE_GRAY); // TYPE_BYTE_GRAY
		for (int x = 0; x < dim; x++) {
			for (int y = 0; y < dim; y++) {
				WritableRaster raster = image.getRaster();
				raster.setSample(y, x, 0, 255 - pixels[i]); // mnist uses
															// inverted standard
				i++;
			}
		}
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
