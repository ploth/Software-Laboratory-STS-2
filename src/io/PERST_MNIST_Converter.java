package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import data.PERSTDatabase;

public class PERST_MNIST_Converter {
	private PERSTDatabase db_;
	private String readLabelPath_ = "ImageData/train-labels.idx1-ubyte";
	private String readImagePath_ = "ImageData/train-images.idx3-ubyte";
	private String writeLabelPath_;
	private String writeImagePath_;

	// read from standart path
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

	public void readMNIST() throws IOException {
		// Charset encoding = Charset.defaultCharset();
		// // Charset encoding = Charset.forName("UTF-8");
		// File file = new File(readLabelPath_);
		// InputStream in = new FileInputStream(file);
		// Reader reader = new InputStreamReader(in, encoding);
		// Reader buffer = new BufferedReader(reader);
		// char bla = (char) reader.read();
		// System.out.println(bla);
		//
		//
		// FileReader frLabels = new FileReader(readLabelPath_);
		// FileReader frImages = new FileReader(readImagePath_);
		// BufferedReader brLabels = new BufferedReader(frLabels);
		// BufferedReader brImages = new BufferedReader(frImages);
		// read in first line
		// System.out.println((char) brLabels.read());
		// String firstLine = brLabels.readLine();
		// if (firstLine == null) {
		// System.out.println("troll");
		// }
		// firstLine = brLabels.readLine();
		// System.out.println("firstLine: " + firstLine);
		// StringTokenizer labelTokens = new StringTokenizer(firstLine);
		// // check magic number
		// String test = labelTokens.nextToken();
		// System.out.println(test);
		// StringTokenizer imageTokens = new
		// StringTokenizer(brImages.readLine());
		// File f = new File(readLabelPath_);
		// f.renameTo(new File("dick"));
		//
		//
		// Path path = FileSystems.getDefault().getPath("ImageData", "dick"); //
		// train-images.idx3-ubyte
		// BufferedReader reader = Files.newBufferedReader(path,
		// StandardCharsets.UTF_8);
		// System.out.println(reader.read());
		//
		//
		// Path path = FileSystems.getDefault().getPath("ImageData", "dick"); //
		// train-images.idx3-ubyte
		// Stream<String> stream = Files.lines(path);
		// System.out.println(stream.count());
		//
		//
		Path path = Paths.get(readLabelPath_);
		byte[] data = Files.readAllBytes(path);
		System.out.println(data[0]);
		// System.out.println(stream.toString());
		// Stream<String> string = lines(readLabelPath_);
	}

	public void writeMNIST() {

	}
}
