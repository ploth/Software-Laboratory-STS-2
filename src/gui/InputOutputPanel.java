package gui;

import io.ConverterException;
import io.PERST_CSV_Converter;
import io.PERST_MNIST_Converter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;
import data.PERSTDatabase;

public class InputOutputPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final int LOAD_FILE = 0;
	private static final int EXPORT_FILE = 1;
	private PERSTDatabase db_ = PERSTDatabase.getInstance();
	private JLabel lblNumOfTrainingDataElements;
	private JLabel lblNumOfDataToClassify;

	public InputOutputPanel() {
		setLayout(new MigLayout("", "[grow]", "[][][]"));

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				updateDataCounters();
			}
		});

		JPanel pnlTrainingData = new JPanel();
		pnlTrainingData.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "Training data",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(pnlTrainingData, "cell 0 0,grow");
		pnlTrainingData.setLayout(new MigLayout("", "[230.00px][grow]",
				"[19.00,grow][][][]"));

		final JLabel lblNumberOfTrainingData = new JLabel(
				"Number of data training data elements:");
		pnlTrainingData.add(lblNumberOfTrainingData, "cell 0 0,alignx center");

		JPanel pnlNumOfTrainingData = new JPanel();
		pnlNumOfTrainingData.setBorder(new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null));
		pnlTrainingData.add(pnlNumOfTrainingData, "cell 1 0,grow");

		lblNumOfTrainingDataElements = new JLabel("0");
		lblNumOfTrainingDataElements
				.setFont(new Font("Tahoma", Font.PLAIN, 11));
		pnlNumOfTrainingData.add(lblNumOfTrainingDataElements);

		JButton btnImportDataFromMNIST = new JButton(
				"Import data from MNIST files");
		btnImportDataFromMNIST.addActionListener(this);

		JSeparator separator = new JSeparator();
		pnlTrainingData.add(separator, "cell 0 1 2 1,growx");
		btnImportDataFromMNIST.setActionCommand("importTrainingDataMNIST");
		pnlTrainingData.add(btnImportDataFromMNIST, "cell 0 2 2 1,growx");

		JButton btnImportDataFromCSV = new JButton("Import data from CSV");
		btnImportDataFromCSV.addActionListener(this);
		btnImportDataFromCSV.setActionCommand("importTrainingDataCSV");
		pnlTrainingData.add(btnImportDataFromCSV, "flowy,cell 0 3 2 1,growx");

		JPanel pnlClassifyData = new JPanel();
		pnlClassifyData.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "Classify data",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(pnlClassifyData, "cell 0 1,grow");
		pnlClassifyData.setLayout(new MigLayout("", "[230.00][grow]",
				"[grow][][]"));

		JLabel lblNumberOfDataToClassify = new JLabel(
				"Number of data to be classified:");
		pnlClassifyData
				.add(lblNumberOfDataToClassify, "cell 0 0,alignx center");

		JPanel pnlNumOfDataToClassify = new JPanel();
		pnlNumOfDataToClassify.setBorder(new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null));
		pnlClassifyData.add(pnlNumOfDataToClassify, "cell 1 0,grow");

		lblNumOfDataToClassify = new JLabel("0");
		pnlNumOfDataToClassify.add(lblNumOfDataToClassify);

		JButton btnClassifyDataFromMNIST = new JButton(
				"Add new data from MNIST files");
		btnClassifyDataFromMNIST.addActionListener(this);
		btnClassifyDataFromMNIST.setActionCommand("addDataToClassifyFromMNIST");
		pnlClassifyData.add(btnClassifyDataFromMNIST,
				"flowy,cell 0 2 2 1,growx");

		JSeparator separator_1 = new JSeparator();
		pnlClassifyData.add(separator_1, "cell 0 1 2 1,growx");

		JButton btnAddDataFromPNG = new JButton("Add new data from png");
		btnAddDataFromPNG.addActionListener(this);
		btnAddDataFromPNG.setActionCommand("addDataToClassifyFromPNG");
		pnlClassifyData.add(btnAddDataFromPNG, "cell 0 2 2 1,growx");

		JPanel pnlExport = new JPanel();
		pnlExport.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "Export database",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		add(pnlExport, "cell 0 2,grow");
		pnlExport.setLayout(new MigLayout("", "[grow]", "[][][]"));

		JButton btnExportDataMNIST = new JButton(
				"Export training data to MNIST files");
		btnExportDataMNIST.addActionListener(this);
		btnExportDataMNIST.setActionCommand("exportToMNIST");
		pnlExport.add(btnExportDataMNIST, "cell 0 0,growx");

		JButton btnExportDataCSV = new JButton("Export traing data to CSV file");
		btnExportDataCSV.addActionListener(this);
		btnExportDataCSV.setActionCommand("exportToCSV");
		pnlExport.add(btnExportDataCSV, "cell 0 1,growx");

		JButton btnExportDataPNG = new JButton(
				"Export training data to PNG files");
		btnExportDataPNG.addActionListener(this);
		btnExportDataPNG.setActionCommand("exportToPNG");
		pnlExport.add(btnExportDataPNG, "cell 0 2,growx");

		updateDataCounters();
	}

	private void updateDataCounters() {
		int numOfTrainingDataElements = db_
				.getNumberOfCorrectDatabaseElements();
		int numOfDataToClassify = db_
				.getNumberOfNonTrainingdataDatabaseElements();
		lblNumOfTrainingDataElements.setText(String
				.valueOf(numOfTrainingDataElements));
		lblNumOfDataToClassify.setText(String.valueOf(numOfDataToClassify));
	}

	@Override
	// TODO Catch exceptions
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "importTrainingDataMNIST":
			loadMNISTfiles(true);
			updateDataCounters();
			break;
		case "importTrainingDataCSV":
			loadCSVfile();
			updateDataCounters();
			break;
		case "addDataToClassifyFromMNIST":
			loadMNISTfiles(false);
			updateDataCounters();
			break;
		case "addDataToClassifyFromPNG":
			// TODO add PNG code
			updateDataCounters();
			break;
		case "exportToMNIST":
			exportToMNIST();
			break;
		case "exportToCSV":
			exportToCSV();
			break;
		case "exportToPNG":
			exportToPNG();
			break;
		default:
			// TODO Throw exception?
			System.err.println("Unknown action: " + e.getActionCommand());
		}
	}

	private void loadCSVfile() {
		String filePath = chooseFile("CSV file", "csv", LOAD_FILE);
		if (filePath.isEmpty())
			return;
		int startIndex = 0;
		int endIndex = 0;
		String startIndex_str = JOptionPane.showInputDialog(new JFrame(),
				"Enter start index");
		String endIndex_str = JOptionPane.showInputDialog(new JFrame(),
				"Enter end index");
		if (startIndex_str.isEmpty() || endIndex_str.isEmpty()) {
			return;
		} else {
			startIndex = Integer.parseInt(startIndex_str);
			endIndex = Integer.parseInt(endIndex_str);
		}
		try {
			PERST_CSV_Converter.read(filePath, startIndex, endIndex);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					new JFrame(),
					"An error ocurred while reading the files. Message:\n"
							+ e.getMessage(), "Converter error",
					JOptionPane.ERROR_MESSAGE);
		} catch (ConverterException e) {
			JOptionPane.showMessageDialog(new JFrame(),
					"An error ocurred while converting the files. Message:\n"
							+ e.getMessage(), "Converter error",
					JOptionPane.ERROR_MESSAGE);
		}
		updateDataCounters();
	}

	private void loadMNISTfiles(boolean isTrainingData) {
		String imagesPath = chooseFile("MNIST images", "idx3-ubyte", LOAD_FILE);
		if (!imagesPath.isEmpty()) {
			String labelsPath = chooseFile("MNIST labels", "idx1-ubyte",
					LOAD_FILE);
			if (!labelsPath.isEmpty()) {
				int startIndex = 0;
				int endIndex = 0;
				String startIndex_str = JOptionPane.showInputDialog(
						new JFrame(), "Enter start index");
				String endIndex_str = JOptionPane.showInputDialog(new JFrame(),
						"Enter end index");
				if (startIndex_str.isEmpty() || endIndex_str.isEmpty()) {
					return;
				} else {
					startIndex = Integer.parseInt(startIndex_str);
					endIndex = Integer.parseInt(endIndex_str);
				}

				try {
					PERST_MNIST_Converter.read(labelsPath, imagesPath,
							startIndex, endIndex, isTrainingData);
					updateDataCounters();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(new JFrame(),
							"An error ocurred while reading the files. Message:\n"
									+ e.getMessage(), "Read error",
							JOptionPane.ERROR_MESSAGE);
				} catch (ConverterException e) {
					JOptionPane.showMessageDialog(new JFrame(),
							"An error ocurred while converting the files. Message:\n"
									+ e.getMessage(), "Converter error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	private void exportToCSV() {
		String filePath = chooseFile("CSV file", "csv", EXPORT_FILE);
		if (filePath.isEmpty())
			return;
		try {
			PERST_CSV_Converter.write(filePath);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					new JFrame(),
					"An error ocurred while exporting the data. Message:\n"
							+ e.getMessage(), "Export error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void exportToPNG() {
		String filePath = chooseFile("PNG file", "png", EXPORT_FILE);
		if (filePath.isEmpty())
			return;
		// try {
		// PERST_PNG_Converter.write(filePath);
		// } catch (IOException e) {
		// JOptionPane.showMessageDialog(
		// new JFrame(),
		// "An error ocurred while exporting the data. Message:\n"
		// + e.getMessage(), "Export error",
		// JOptionPane.ERROR_MESSAGE);
		// }
	}

	private void exportToMNIST() {
		String labelsFilePath = chooseFile("MNIST labels", "idx1-ubyte",
				EXPORT_FILE);
		if (labelsFilePath.isEmpty())
			return;
		String imagesFilePath = chooseFile("MNIST images", "idx3-ubyte",
				EXPORT_FILE);
		if (imagesFilePath.isEmpty())
			return;
		try {
			PERST_MNIST_Converter.write(labelsFilePath, imagesFilePath);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					new JFrame(),
					"An error ocurred while exporting the data. Message:\n"
							+ e.getMessage(), "Export error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private String chooseFile(String fileDescription, String fileSuffix,
			int loadOrExportMode) {
		String filePath = null;
		JFileChooser fc = new JFileChooser("./");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				fileDescription, fileSuffix);
		fc.setFileFilter(filter);
		String mode;
		if (loadOrExportMode == LOAD_FILE) {
			mode = "Load";
		} else {
			mode = "Export to ";
		}
		int returnVal = fc.showDialog(new JFrame(), mode + " "
				+ fileDescription);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			filePath = fc.getSelectedFile().getPath();
			if (loadOrExportMode == EXPORT_FILE
					&& !filePath.endsWith(fileSuffix)) {
				filePath += "." + fileSuffix;
			}
		}
		return filePath;
	}
}