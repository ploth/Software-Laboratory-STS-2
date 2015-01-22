package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import org.garret.perst.IterableIterator;

import data.PERSTDatabase.DatabaseElement;

// This is the converter to convert from our PERST database to csv files and from csv files to our PERST database.
// First value is the classification and the following are the pixels from left to right and from top to bottom.
public class PERST_CSV_Converter extends AbstractConverter {

	public static void read(String path, int rangeStart, int rangeEnd,
			boolean trainingdata) throws IOException, ConverterException {
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String line;
		if (rangeStart <= 0) {
			br.close();
			fr.close();
			throw new ConverterException("Please enter a minimum value of 1");
		}
		int lineCounter = 1;
		while ((line = br.readLine()) != null) {
			if (rangeStart <= lineCounter && lineCounter <= rangeEnd) {
				StringTokenizer lineTokens = new StringTokenizer(line, ",");
				char classification = (char) Integer.parseInt(lineTokens
						.nextToken());
				int numberOfPixels = lineTokens.countTokens();
				char pixels[] = new char[numberOfPixels];
				for (int i = 0; i < numberOfPixels; i++) {
					pixels[i] = (char) Integer.parseInt(lineTokens.nextToken());
				}
				getDb_().createCorrectDatabaseElement(classification, pixels,
						trainingdata);
			}
		}
		br.close();
		fr.close();
	}

	public static void write(String path) throws IOException {
		IterableIterator<DatabaseElement> iter = getDb_()
				.getCorrectDatabaseIterator();
		int dim = getDb_().getDim();
		FileWriter fw = new FileWriter(path);
		BufferedWriter bw = new BufferedWriter(fw);
		while (iter.hasNext()) {
			DatabaseElement e = iter.next();
			// classification
			bw.write(String.valueOf((int) e.getCorrectClassification()));
			for (int i = 0; i < (dim * dim); i++) {
				// pixels
				bw.write(",");
				bw.write(String.valueOf((int) e.getPixels()[i]));
			}
			bw.newLine();
		}
		bw.close();
		fw.close();
	}
}
