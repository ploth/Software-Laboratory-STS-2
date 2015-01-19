package io;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PERST_PNG_Converter extends AbstractConverter {

	private static final int BAND = 0;
	private static final int MAX_CHAR = 255;

	public static void read(String path) throws IOException {
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
		}
	}

	public static void write(char classification, char[] pixels, String path) {
		if (path.endsWith("png")) {
			File file = new File(path);
			int i = 0;
			int dim = (int) Math.sqrt(pixels.length);
			BufferedImage image = new BufferedImage(dim, dim,
					BufferedImage.TYPE_BYTE_GRAY); // TYPE_BYTE_GRAY
			for (int x = 0; x < dim; x++) {
				for (int y = 0; y < dim; y++) {
					WritableRaster raster = image.getRaster();
					raster.setSample(y, x, 0, MAX_CHAR - pixels[i]); // mnist
																		// uses
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

	public static void write(int classification, int[] pixels, String path) {
		if (path.endsWith("png")) {
			File file = new File(path);
			int i = 0;
			int dim = (int) Math.sqrt(pixels.length);
			BufferedImage image = new BufferedImage(dim, dim,
					BufferedImage.TYPE_BYTE_GRAY); // TYPE_BYTE_GRAY
			for (int x = 0; x < dim; x++) {
				for (int y = 0; y < dim; y++) {
					WritableRaster raster = image.getRaster();
					raster.setSample(y, x, 0, 255 - pixels[i]); // mnist uses
																// inverted
																// standard
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

	public static void write(int classification, double[] pixels, String path) {
		if (path.endsWith("png")) {
			File file = new File(path);
			int i = 0;
			int dim = (int) Math.sqrt(pixels.length);
			BufferedImage image = new BufferedImage(dim, dim,
					BufferedImage.TYPE_BYTE_GRAY); // TYPE_BYTE_GRAY
			for (int x = 0; x < dim; x++) {
				for (int y = 0; y < dim; y++) {
					WritableRaster raster = image.getRaster();
					raster.setSample(y, x, 0, 255 - pixels[i]); // mnist uses
																// inverted
																// standard
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
}
