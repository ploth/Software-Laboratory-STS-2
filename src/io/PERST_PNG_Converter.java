package io;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PERST_PNG_Converter {

	public static void read() {
		// TODO
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
