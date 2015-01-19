package gui;

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
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;
import data.PERSTDatabase;

public class InputOutputPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
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
				"[19.00,grow][][]"));

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
		btnImportDataFromMNIST.setActionCommand("importTrainingDataMNIST");
		pnlTrainingData.add(btnImportDataFromMNIST, "cell 0 1 2 1,growx");

		JButton btnImportDataFromCSV = new JButton("Import data from CSV");
		btnImportDataFromCSV.addActionListener(this);
		btnImportDataFromCSV.setActionCommand("importTrainingDataCSV");
		pnlTrainingData.add(btnImportDataFromCSV, "flowy,cell 0 2 2 1,growx");

		JPanel pnlClassifyData = new JPanel();
		pnlClassifyData.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "Classify data",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(pnlClassifyData, "cell 0 1,grow");
		pnlClassifyData.setLayout(new MigLayout("", "[230.00][grow]",
				"[grow][]"));

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

		JButton btnClassifyDataFrom = new JButton(
				"Add new data from MNIST files");
		btnClassifyDataFrom.addActionListener(this);
		btnClassifyDataFrom.setActionCommand("addDataToClassifyFromMNIST");
		pnlClassifyData.add(btnClassifyDataFrom, "flowy,cell 0 1 2 1,growx");

		JButton btnAddNewData = new JButton("Add new data from CSV");
		btnAddNewData.setActionCommand("addDataToClassifyFromCSV");
		pnlClassifyData.add(btnAddNewData, "cell 0 1 2 1,growx");

		JButton btnAddDataFromPNG = new JButton("Add new data from png");
		btnAddDataFromPNG.setActionCommand("addDataToClassifyFromPNG");
		pnlClassifyData.add(btnAddDataFromPNG, "cell 0 1 2 1,growx");

		JPanel pnlExport = new JPanel();
		pnlExport.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "Export database",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		add(pnlExport, "cell 0 2,grow");
		pnlExport.setLayout(new MigLayout("", "[grow]", "[][][]"));

		JButton btnExportDataMNIST = new JButton(
				"Export training data to MNIST files");
		btnExportDataMNIST.setActionCommand("exportToMNIST");
		pnlExport.add(btnExportDataMNIST, "cell 0 0,growx");

		JButton btnExportDataCSV = new JButton("Export traing data to CSV file");
		btnExportDataCSV.setActionCommand("exportToCSV");
		pnlExport.add(btnExportDataCSV, "cell 0 1,growx");

		JButton btnExportDataPNG = new JButton(
				"Export training data to PNG files");
		btnExportDataPNG.setActionCommand("exportToPNG");
		pnlExport.add(btnExportDataPNG, "cell 0 2,growx");

		updateDataCounters();
	}

	private void loadMNISTfiles(JLabel dataLabel, boolean isTrainingData) {
		String imagesPath = "";
		String labelsPath = "";

		JFileChooser fc = new JFileChooser("ImageData/");
		FileNameExtensionFilter filterIDX3 = new FileNameExtensionFilter(
				"MNIST images", "idx3-ubyte");
		FileNameExtensionFilter filterIDX1 = new FileNameExtensionFilter(
				"MNIST labels", "idx1-ubyte");
		fc.setFileFilter(filterIDX3);

		int returnVal = fc.showDialog(new JFrame(), "Load MNIST images");

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			imagesPath = fc.getSelectedFile().getPath();

			fc.setFileFilter(filterIDX1);
			returnVal = fc.showDialog(new JFrame(), "Load MNIST labels");
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				labelsPath = fc.getSelectedFile().getPath();

				int startIndex = 0;
				int endIndex = 0;
				String startIndex_str = JOptionPane.showInputDialog(
						new JFrame(), "Enter start index");
				String endIndex_str = JOptionPane.showInputDialog(new JFrame(),
						"Enter end index");
				if (startIndex_str == null || endIndex_str == null) {
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
					JOptionPane.showMessageDialog(new JFrame(), e.getMessage(),
							"IOException", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
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
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "importTrainingDataMNIST":
			loadMNISTfiles(lblNumOfTrainingDataElements, true);
			updateDataCounters();
			break;
		case "importTrainingDataCSV":
			// TODO add CSV code
			JOptionPane.showMessageDialog(new JFrame(), "Import CSV");
			updateDataCounters();
			break;
		case "addDataToClassifyFromMNIST":
			loadMNISTfiles(lblNumOfDataToClassify, false);
			updateDataCounters();
			break;
		case "addDataToClassifyFromCSV":
			// TODO add CSV code
			updateDataCounters();
			break;
		case "addDataToClassifyFromPNG":
			// TODO add PNG code
			updateDataCounters();
			break;
		case "exportToMNIST":
			// TODO add MNIST export code
			break;
		case "exportToCSV":
			// TODO add CSV export code
			break;
		case "exportToPNG":
			// TODO add PNG export code
			break;
		default:
			// TODO Throw exception?
			System.err.println("Unknown action: " + e.getActionCommand());
		}
	}
}