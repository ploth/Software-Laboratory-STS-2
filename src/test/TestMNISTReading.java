package test;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class TestMNISTReading {
	public static void main(String[] args) throws IOException {
		DataInputStream labels = new DataInputStream(new FileInputStream(
				"ImageData/train-labels.idx1-ubyte"));
		int magic = labels.readInt();
		System.out.println("magic: " + magic);
		labels.close();
	}
}
