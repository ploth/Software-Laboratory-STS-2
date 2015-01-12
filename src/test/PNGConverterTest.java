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
				+ db.getNumberOfDatabaseElements_());
		PERST_PNG_Converter png = new PERST_PNG_Converter();
		IterableIterator<DatabaseElement> iter = db.getDatabaseIterator();
		DatabaseElement e = iter.next();
		for (int i = 0; i < 80; i++) {
			e = iter.next();
		}
		System.out.println("classification: " + (int) e.getClassification());
		// print array
		for (int k = 0; k < e.getPixels().length; k++) {
			System.out.print(e.getPixels()[k]);
		}
		png.write(e.getClassification(), e.getPixels(), "src/test/test.png");
	}
}
