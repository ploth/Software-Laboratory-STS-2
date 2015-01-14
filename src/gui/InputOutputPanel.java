package gui;

import io.ConverterException;
import io.PERST_MNIST_Converter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import data.PERSTDatabase;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class InputOutputPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private PERSTDatabase db_ = PERSTDatabase.getInstance();
	private JLabel lblNumOfTrainingDataElements;
	private JLabel lblNumOfDataToClassify;
	
	public InputOutputPanel() {
		setLayout(new MigLayout("", "[grow]", "[][][]"));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Training data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[230.00px][grow]", "[19.00,grow][][]"));
		
		final JLabel lblNumberOfTrainingData = new JLabel("Number of data training data elements:");
		panel.add(lblNumberOfTrainingData, "cell 0 0,alignx center");
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_5, "cell 1 0,grow");
		
		lblNumOfTrainingDataElements = new JLabel("-");
		lblNumOfTrainingDataElements.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_5.add(lblNumOfTrainingDataElements);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Import data", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.add(panel_3, "cell 0 1 2 1,grow");
		panel_3.setLayout(new MigLayout("", "[grow]", "[][]"));
		
		final JButton btnImportMnistData = new JButton("Import MNIST data");
		btnImportMnistData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadMNISTfiles(lblNumOfTrainingDataElements, true);
			}
		});
		panel_3.add(btnImportMnistData, "flowy,cell 0 0,growx");
		
		JButton btnImportCSV = new JButton("Import from CSV");
		panel_3.add(btnImportCSV, "cell 0 0,growx");
		
		JButton btnLearnFromPng = new JButton("Learn from PNG");
		panel_3.add(btnLearnFromPng, "cell 0 1,growx");
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Modify data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_4, "cell 0 2 2 1,grow");
		panel_4.setLayout(new MigLayout("", "[grow]", "[][]"));
		
		JButton btnViewEditData = new JButton("View/Edit data");
		panel_4.add(btnViewEditData, "cell 0 0,growx");
		
		JButton btnDeleteData = new JButton("Delete data");
		panel_4.add(btnDeleteData, "cell 0 1,growx");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Classify data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new MigLayout("", "[230.00][grow]", "[grow][]"));
		
		JLabel lblNumberOfDataToClassify = new JLabel("Number of data to be classified:");
		panel_1.add(lblNumberOfDataToClassify, "cell 0 0,alignx center");
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.add(panel_6, "cell 1 0,grow");
		
		lblNumOfDataToClassify = new JLabel("-");
		panel_6.add(lblNumOfDataToClassify);
		
		JButton btnClassifyDataFrom = new JButton("Add new data from MNIST files");
		btnClassifyDataFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadMNISTfiles(lblNumOfDataToClassify, false);
			}
		});
		panel_1.add(btnClassifyDataFrom, "flowy,cell 0 1 2 1,growx");
		
		JButton btnAddNewData = new JButton("Add new data from CSV");
		panel_1.add(btnAddNewData, "cell 0 1 2 1,growx");
		
		JButton btnAddDataFromPNG = new JButton("Add new data from png");
		panel_1.add(btnAddDataFromPNG, "cell 0 1 2 1,growx");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Export database", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel_2, "cell 0 2,grow");
		panel_2.setLayout(new MigLayout("", "[grow]", "[][][]"));
		
		JButton btnExportDatabaseMNIST = new JButton("Export database to MNIST files");
		panel_2.add(btnExportDatabaseMNIST, "cell 0 0,growx");
		
		JButton btnExportDatabaseCSV = new JButton("Eport database to CSV file");
		panel_2.add(btnExportDatabaseCSV, "cell 0 1,growx");
		
		JButton btnExportDatabasePNG = new JButton("Export database to PNG files");
		panel_2.add(btnExportDatabasePNG, "cell 0 2,growx");
		
		updateDataCounters();
	}
	
	private void loadMNISTfiles(JLabel dataLabel, boolean isTrainingData) {
		String imagesPath = "";
		String labelsPath = "";
		dataLabel.setText("Loading...");
		
		JFileChooser fc = new JFileChooser("ImageData/");
		FileNameExtensionFilter filterIDX3 = new FileNameExtensionFilter(
		        "MNIST images", "idx3-ubyte");
		FileNameExtensionFilter filterIDX1 = new FileNameExtensionFilter(
		        "MNIST labels", "idx1-ubyte");
		fc.setFileFilter(filterIDX3);
		
		int returnVal = fc.showDialog(new JFrame(), "Load MNIST images");
		
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			imagesPath = fc.getSelectedFile().getPath();
			
			fc.setFileFilter(filterIDX1);
			returnVal = fc.showDialog(new JFrame(), "Load MNIST labels");
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				labelsPath = fc.getSelectedFile().getPath();
				
				int startIndex = Integer.parseInt(JOptionPane.showInputDialog(new JFrame(), "Enter start index"));
				int endIndex = Integer.parseInt(JOptionPane.showInputDialog(new JFrame(), "Enter end index"));
				
				try {
					PERST_MNIST_Converter.read(labelsPath, imagesPath, startIndex, endIndex, isTrainingData);
					updateDataCounters();
				} catch (IOException e) {
					dataLabel.setText("Error");
					JOptionPane.showMessageDialog(new JFrame(),
						    e.getMessage(),
						    "IOException",
						    JOptionPane.ERROR_MESSAGE);
				}
			} else {
				dataLabel.setText("-");
			}
		} else {
			dataLabel.setText("-");
		}
	}
	
	private void updateDataCounters() {
		int numOfTrainingDataElements = db_.getNumberOfCorrectDatabaseElements();
		int numOfDataToClassify = db_.getNumberOfDatabaseElements() - numOfTrainingDataElements;
		if(numOfTrainingDataElements > 0) {
			lblNumOfTrainingDataElements.setText(String.valueOf(numOfTrainingDataElements));
		}
		if(numOfDataToClassify > 0) {
			lblNumOfDataToClassify.setText(String.valueOf(numOfDataToClassify));
		}
	}
}