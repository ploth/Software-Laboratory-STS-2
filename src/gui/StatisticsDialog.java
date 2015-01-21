package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import data.PERSTDatabase.DatabaseElement;

/*
 * This class implements a dialog for showing statistics about a previously performed algorithm test run.
 * Additionally, it provides a button for displaying the wrong classified objects.
 * The statistics values get calculated outside of this class. It takes the information as constructor parameters.
 */
public class StatisticsDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 480;
	private static final int HEIGHT = 400;
	private final JButton btnDisplayFalseClassified;
	private final ArrayList<DatabaseElement> falseClassifiedObjects;

	public StatisticsDialog(String classificatorName,
			String distanceMethodName, int k, int numTotalTestObjects,
			int[] numTestObjectsPerClass, int numTrainingElements,
			int[] numTrainingElementsPerClass,
			ArrayList<DatabaseElement> falseClassifiedObjects,
			int meanSquaredError) {
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setModal(true);
		setTitle("Algorithm test run statistics");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "[grow][]", "[grow][]"));

		this.falseClassifiedObjects = falseClassifiedObjects;

		// /////////////////////////////////////////////
		// General information panel
		// /////////////////////////////////////////////

		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "Results",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, "cell 0 0 2 1,grow");
		contentPanel.setLayout(new MigLayout("", "[156.00,grow][]",
				"[][][][][][][][][][][][]"));

		JLabel lblClassificatorAlgorithm = new JLabel(
				"Classificator algorithm:");
		contentPanel.add(lblClassificatorAlgorithm, "cell 0 0");

		JLabel lblClassificatorname = new JLabel(classificatorName);
		contentPanel.add(lblClassificatorname, "cell 1 0");

		JLabel lblDistanceMeasurementMethod = new JLabel(
				"Distance measurement method:");
		contentPanel.add(lblDistanceMeasurementMethod, "cell 0 1");

		JLabel lblDistanceMethodName = new JLabel(distanceMethodName);
		contentPanel.add(lblDistanceMethodName, "cell 1 1");

		JLabel lblParameterK = new JLabel("Parameter k:");
		contentPanel.add(lblParameterK, "cell 0 2");

		JLabel lblParameterK_number = new JLabel(String.valueOf(k));
		contentPanel.add(lblParameterK_number, "cell 1 2");

		JSeparator seperatorTop = new JSeparator();
		contentPanel.add(seperatorTop, "cell 0 3 2 1,growx");

		// /////////////////////////////////////////////
		// Test object information panel
		// /////////////////////////////////////////////

		JLabel lblNumberOfTotal = new JLabel("Number of total test objects:");
		contentPanel.add(lblNumberOfTotal, "flowx,cell 0 4");

		JLabel lblNumTotalTestObjects = new JLabel(
				String.valueOf(numTotalTestObjects));
		contentPanel.add(lblNumTotalTestObjects, "cell 1 4");

		// /////////////////////////////////////////////
		// Test object per class table
		// /////////////////////////////////////////////

		JPanel testObjectsPerClassTable = new JPanel();
		testObjectsPerClassTable.setBorder(new TitledBorder(new LineBorder(
				new Color(0, 0, 0)), "Test objects per class:",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		contentPanel.add(testObjectsPerClassTable, "cell 0 5 2 1,grow");
		testObjectsPerClassTable
				.setLayout(new MigLayout(
						"",
						"[][grow][][grow][][grow][][grow][][grow][][grow][-36.00][grow][2.00][grow][-11.00][grow][-21.00][1.00][grow]",
						"[][][]"));

		JLabel lblClass = new JLabel("Class:");
		testObjectsPerClassTable.add(lblClass, "cell 0 0,alignx right");

		JLabel label = new JLabel("0");
		testObjectsPerClassTable.add(label, "cell 1 0,alignx center");

		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		testObjectsPerClassTable.add(separator_3, "cell 2 0 1 3,growy");

		JLabel label_1 = new JLabel("1");
		testObjectsPerClassTable.add(label_1, "cell 3 0,alignx center");

		JSeparator separator_4 = new JSeparator();
		separator_4.setOrientation(SwingConstants.VERTICAL);
		testObjectsPerClassTable.add(separator_4, "cell 4 0 1 3,growy");

		JLabel label_2 = new JLabel("2");
		testObjectsPerClassTable.add(label_2, "cell 5 0,alignx center");

		JSeparator separator_5 = new JSeparator();
		separator_5.setOrientation(SwingConstants.VERTICAL);
		testObjectsPerClassTable.add(separator_5, "cell 6 0 1 3,growy");

		JLabel label_3 = new JLabel("3");
		testObjectsPerClassTable.add(label_3, "cell 7 0,alignx center");

		JSeparator separator_6 = new JSeparator();
		separator_6.setOrientation(SwingConstants.VERTICAL);
		testObjectsPerClassTable.add(separator_6, "cell 8 0 1 3,growy");

		JLabel label_4 = new JLabel("4");
		testObjectsPerClassTable.add(label_4, "cell 9 0,alignx center");

		JSeparator separator_7 = new JSeparator();
		separator_7.setOrientation(SwingConstants.VERTICAL);
		testObjectsPerClassTable.add(separator_7, "cell 10 0 1 3,growy");

		JLabel label_5 = new JLabel("5");
		testObjectsPerClassTable.add(label_5, "cell 11 0,alignx center");

		JSeparator separator_8 = new JSeparator();
		separator_8.setOrientation(SwingConstants.VERTICAL);
		testObjectsPerClassTable.add(separator_8, "cell 12 0 1 3,growy");

		JLabel label_6 = new JLabel("6");
		testObjectsPerClassTable.add(label_6, "cell 13 0,alignx center");

		JSeparator separator_9 = new JSeparator();
		separator_9.setOrientation(SwingConstants.VERTICAL);
		testObjectsPerClassTable.add(separator_9, "cell 14 0 1 3,growy");

		JLabel label_7 = new JLabel("7");
		testObjectsPerClassTable.add(label_7, "cell 15 0,alignx center");

		JSeparator separator_10 = new JSeparator();
		separator_10.setOrientation(SwingConstants.VERTICAL);
		testObjectsPerClassTable.add(separator_10, "cell 16 0 1 3,growy");

		JLabel label_8 = new JLabel("8");
		testObjectsPerClassTable.add(label_8, "cell 17 0,alignx center");

		JSeparator separator_11 = new JSeparator();
		separator_11.setOrientation(SwingConstants.VERTICAL);
		testObjectsPerClassTable.add(separator_11, "cell 18 0 1 3,growy");

		JLabel label_9 = new JLabel("9");
		testObjectsPerClassTable.add(label_9, "cell 20 0,alignx center");

		JSeparator separator_12 = new JSeparator();
		separator_12.setOrientation(SwingConstants.VERTICAL);
		testObjectsPerClassTable.add(separator_12, "cell 0 1");

		JLabel lblelements = new JLabel("#elements:");
		testObjectsPerClassTable.add(lblelements, "cell 0 2");

		JLabel lblTest = new JLabel(String.valueOf(numTestObjectsPerClass[0]));
		testObjectsPerClassTable.add(lblTest, "cell 1 2,alignx center");

		JLabel lblTest_1 = new JLabel(String.valueOf(numTestObjectsPerClass[1]));
		testObjectsPerClassTable.add(lblTest_1, "cell 3 2,alignx center");

		JLabel lblTest_2 = new JLabel(String.valueOf(numTestObjectsPerClass[2]));
		testObjectsPerClassTable.add(lblTest_2, "cell 5 2,alignx center");

		JLabel lblTest_3 = new JLabel(String.valueOf(numTestObjectsPerClass[3]));
		testObjectsPerClassTable.add(lblTest_3, "cell 7 2,alignx center");

		JLabel lblTest_4 = new JLabel(String.valueOf(numTestObjectsPerClass[4]));
		testObjectsPerClassTable.add(lblTest_4, "cell 9 2,alignx center");

		JLabel lblTest_5 = new JLabel(String.valueOf(numTestObjectsPerClass[5]));
		testObjectsPerClassTable.add(lblTest_5, "cell 11 2,alignx center");

		JLabel lblTest_6 = new JLabel(String.valueOf(numTestObjectsPerClass[6]));
		testObjectsPerClassTable.add(lblTest_6, "cell 13 2,alignx center");

		JLabel lblTest_7 = new JLabel(String.valueOf(numTestObjectsPerClass[7]));
		testObjectsPerClassTable.add(lblTest_7, "cell 15 2,alignx center");

		JLabel lblTest_8 = new JLabel(String.valueOf(numTestObjectsPerClass[8]));
		testObjectsPerClassTable.add(lblTest_8, "cell 17 2,alignx center");

		JLabel lblTest_9 = new JLabel(String.valueOf(numTestObjectsPerClass[9]));
		testObjectsPerClassTable.add(lblTest_9, "cell 20 2,alignx center");

		JSeparator seperatorMiddle = new JSeparator();
		contentPanel.add(seperatorMiddle, "cell 0 6 2 1,growx");

		// /////////////////////////////////////////////
		// Training data information panel
		// /////////////////////////////////////////////

		JLabel lblTotalNumberOf = new JLabel(
				"Total number of training data elements:");
		contentPanel.add(lblTotalNumberOf, "cell 0 7");

		JLabel lblNumtrainingelements = new JLabel(
				String.valueOf(numTrainingElements));
		contentPanel.add(lblNumtrainingelements, "cell 1 7");

		// /////////////////////////////////////////////
		// Training data per class table
		// /////////////////////////////////////////////

		JPanel trainingObjectsPerClassTable = new JPanel();
		trainingObjectsPerClassTable.setBorder(new TitledBorder(new LineBorder(
				new Color(0, 0, 0)), "Training data per class:",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		contentPanel.add(trainingObjectsPerClassTable, "cell 0 8 2 1,grow");
		trainingObjectsPerClassTable
				.setLayout(new MigLayout(
						"",
						"[][grow][][grow][][grow][][grow][][grow][][grow][][grow][][grow][][grow][][grow]",
						"[][]"));

		JLabel lblClass_1 = new JLabel("Class:");
		trainingObjectsPerClassTable.add(lblClass_1, "cell 0 0,alignx right");

		JLabel label_10 = new JLabel("0");
		trainingObjectsPerClassTable.add(label_10, "cell 1 0,alignx center");

		JSeparator separator_13 = new JSeparator();
		separator_13.setOrientation(SwingConstants.VERTICAL);
		trainingObjectsPerClassTable.add(separator_13, "cell 2 0 1 2,growy");

		JLabel label_11 = new JLabel("1");
		trainingObjectsPerClassTable.add(label_11, "cell 3 0,alignx center");

		JSeparator separator_14 = new JSeparator();
		separator_14.setOrientation(SwingConstants.VERTICAL);
		trainingObjectsPerClassTable.add(separator_14, "cell 4 0 1 2,growy");

		JLabel label_12 = new JLabel("2");
		trainingObjectsPerClassTable.add(label_12, "cell 5 0,alignx center");

		JSeparator separator_15 = new JSeparator();
		separator_15.setOrientation(SwingConstants.VERTICAL);
		trainingObjectsPerClassTable.add(separator_15, "cell 6 0 1 2,growy");

		JLabel label_13 = new JLabel("3");
		trainingObjectsPerClassTable.add(label_13, "cell 7 0,alignx center");

		JSeparator separator_16 = new JSeparator();
		separator_16.setOrientation(SwingConstants.VERTICAL);
		trainingObjectsPerClassTable.add(separator_16, "cell 8 0 1 2,growy");

		JLabel label_14 = new JLabel("4");
		trainingObjectsPerClassTable.add(label_14, "cell 9 0,alignx center");

		JSeparator separator_17 = new JSeparator();
		separator_17.setOrientation(SwingConstants.VERTICAL);
		trainingObjectsPerClassTable.add(separator_17, "cell 10 0 1 2,growy");

		JLabel label_15 = new JLabel("5");
		trainingObjectsPerClassTable.add(label_15, "cell 11 0,alignx center");

		JSeparator separator_18 = new JSeparator();
		separator_18.setOrientation(SwingConstants.VERTICAL);
		trainingObjectsPerClassTable.add(separator_18, "cell 12 0 1 2,growy");

		JLabel label_16 = new JLabel("6");
		trainingObjectsPerClassTable.add(label_16, "cell 13 0,alignx center");

		JSeparator separator_19 = new JSeparator();
		separator_19.setOrientation(SwingConstants.VERTICAL);
		trainingObjectsPerClassTable.add(separator_19, "cell 14 0 1 2,growy");

		JLabel label_17 = new JLabel("7");
		trainingObjectsPerClassTable.add(label_17, "cell 15 0,alignx center");

		JSeparator separator_20 = new JSeparator();
		separator_20.setOrientation(SwingConstants.VERTICAL);
		trainingObjectsPerClassTable.add(separator_20, "cell 16 0 1 2,growy");

		JLabel label_18 = new JLabel("8");
		trainingObjectsPerClassTable.add(label_18, "cell 17 0,alignx center");

		JSeparator separator_21 = new JSeparator();
		separator_21.setOrientation(SwingConstants.VERTICAL);
		trainingObjectsPerClassTable.add(separator_21, "cell 18 0 1 2,growy");

		JLabel label_19 = new JLabel("9");
		trainingObjectsPerClassTable.add(label_19, "cell 19 0,alignx center");

		JLabel lblelements_1 = new JLabel("#elements:");
		trainingObjectsPerClassTable.add(lblelements_1, "cell 0 1");

		JLabel lblTr = new JLabel(
				String.valueOf(numTrainingElementsPerClass[0]));
		trainingObjectsPerClassTable.add(lblTr, "cell 1 1,alignx center");

		JLabel lblTr_1 = new JLabel(
				String.valueOf(numTrainingElementsPerClass[1]));
		trainingObjectsPerClassTable.add(lblTr_1, "cell 3 1,alignx center");

		JLabel lblTr_2 = new JLabel(
				String.valueOf(numTrainingElementsPerClass[2]));
		trainingObjectsPerClassTable.add(lblTr_2, "cell 5 1,alignx center");

		JLabel lblTr_3 = new JLabel(
				String.valueOf(numTrainingElementsPerClass[3]));
		trainingObjectsPerClassTable.add(lblTr_3, "cell 7 1,alignx center");

		JLabel lblTr_4 = new JLabel(
				String.valueOf(numTrainingElementsPerClass[4]));
		trainingObjectsPerClassTable.add(lblTr_4, "cell 9 1,alignx center");

		JLabel lblTr_5 = new JLabel(
				String.valueOf(numTrainingElementsPerClass[5]));
		trainingObjectsPerClassTable.add(lblTr_5, "cell 11 1,alignx center");

		JLabel lblTr_6 = new JLabel(
				String.valueOf(numTrainingElementsPerClass[6]));
		trainingObjectsPerClassTable.add(lblTr_6, "cell 13 1,alignx center");

		JLabel lblTr_7 = new JLabel(
				String.valueOf(numTrainingElementsPerClass[7]));
		trainingObjectsPerClassTable.add(lblTr_7, "cell 15 1,alignx center");

		JLabel lblTr_8 = new JLabel(
				String.valueOf(numTrainingElementsPerClass[8]));
		trainingObjectsPerClassTable.add(lblTr_8, "cell 17 1,alignx center");

		JLabel lblTr_9 = new JLabel(
				String.valueOf(numTrainingElementsPerClass[9]));
		trainingObjectsPerClassTable.add(lblTr_9, "cell 19 1,alignx center");

		JSeparator seperatorBottom = new JSeparator();
		contentPanel.add(seperatorBottom, "cell 0 9 2 1,growx");

		// /////////////////////////////////////////////
		// Error information panel
		// /////////////////////////////////////////////

		JLabel lblNumberOfFalse = new JLabel("Number of false classifications:");
		contentPanel.add(lblNumberOfFalse, "cell 0 10");

		JLabel lblNumwrongclassifications = new JLabel(
				String.valueOf(falseClassifiedObjects.size()));
		contentPanel.add(lblNumwrongclassifications, "cell 1 10");

		JLabel lblMeanSquaredError = new JLabel("Mean squared error:");
		contentPanel.add(lblMeanSquaredError, "cell 0 11");

		JLabel lblMeansquarederrorvalue = new JLabel(
				String.valueOf(meanSquaredError));
		contentPanel.add(lblMeansquarederrorvalue, "cell 1 11");

		btnDisplayFalseClassified = new JButton("Display false classifications");
		btnDisplayFalseClassified.addActionListener(this);
		btnDisplayFalseClassified.setActionCommand("displayWrongElements");
		if (falseClassifiedObjects.size() == 0) {
			btnDisplayFalseClassified.setEnabled(false);
		}
		getContentPane()
				.add(btnDisplayFalseClassified, "cell 0 1,alignx right");

		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(this);
		btnDone.setActionCommand("done");
		getContentPane().add(btnDone, "cell 1 1,alignx right");

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "displayWrongElements":
			// Display the false classified elements in a result dialog
			btnDisplayFalseClassified.setEnabled(false);
			new ResultDisplayDialog(falseClassifiedObjects);
			break;
		case "done":
			dispose();
			break;
		}

	}

}
