package gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;

public class InputOutputPanel extends JPanel {
	
	public InputOutputPanel() {
		setLayout(new MigLayout("", "[grow]", "[][][]"));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Training data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[][]"));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Import data", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.add(panel_3, "cell 0 0,grow");
		panel_3.setLayout(new MigLayout("", "[grow]", "[][]"));
		
		JButton btnImportMnistData = new JButton("Import MNIST data");
		panel_3.add(btnImportMnistData, "flowy,cell 0 0,growx");
		
		JButton btnImportCSV = new JButton("Import from CSV");
		panel_3.add(btnImportCSV, "cell 0 0,growx");
		
		JButton btnLearnFromPng = new JButton("Learn from PNG");
		panel_3.add(btnLearnFromPng, "cell 0 1,growx");
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Modify data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_4, "cell 0 1,grow");
		panel_4.setLayout(new MigLayout("", "[grow]", "[][]"));
		
		JButton btnViewEditData = new JButton("View/Edit data");
		panel_4.add(btnViewEditData, "cell 0 0,growx");
		
		JButton btnDeleteData = new JButton("Delete data");
		panel_4.add(btnDeleteData, "cell 0 1,growx");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Classify data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new MigLayout("", "[grow]", "[][][]"));
		
		JButton btnAddDataFromPNG = new JButton("Add new data from png");
		panel_1.add(btnAddDataFromPNG, "cell 0 0,growx");
		
		JButton btnAddNewData = new JButton("Add new data from CSV");
		panel_1.add(btnAddNewData, "cell 0 1,growx");
		
		JButton btnClassifyDataFrom = new JButton("Classify data from database");
		panel_1.add(btnClassifyDataFrom, "cell 0 2,growx");
		
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
		updateDatabaseCounters();
	}
	
	private void updateDatabaseCounters() {
	}
}