package test;

import io.PERST_PNG_Converter;

import java.io.IOException;

import org.garret.perst.IterableIterator;

import data.PERSTDatabase;
import data.PERSTDatabase.DatabaseElement;

public class PNGConverterTest {

	public static void main(String[] args) throws IOException {
		PERSTDatabase db = PERSTDatabase.getInstance();
		System.out.println("numberOfDatabaseElements: "
				+ db.getNumberOfDatabaseElements());
		PERST_PNG_Converter png = new PERST_PNG_Converter();
		IterableIterator<DatabaseElement> iter = db.getDatabaseIterator();
		DatabaseElement e = iter.next();
		// which database entry?
		int number = 2;
		for (int i = 0; i < number; i++) {
			e = iter.next();
		}
		System.out.println("classification: "
				+ (int) e.getCorrectClassification());
		// print array
		for (int r = 0; r < Math.sqrt(e.getPixels().length); r++) {
			System.out.println();
			for (int t = 0; t < Math.sqrt(e.getPixels().length); t++) {
				System.out.print((int) e.getPixels()[r
						* (int) Math.sqrt(e.getPixels().length) + t]);
			}
		}
		png.write(e.getCorrectClassification(), e.getPixels(),
				"src/test/test.png");
	}
}
