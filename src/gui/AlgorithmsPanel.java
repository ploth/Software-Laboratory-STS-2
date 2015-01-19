package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import org.garret.perst.IterableIterator;

import algorithm.KNN;
import data.PERSTDatabase;
import data.PERSTDatabase.DatabaseElement;

public class AlgorithmsPanel extends JPanel implements ActionListener {

	private String chosenDistaneMeasurementMethod;
	private int chosenParameterK;
	private static final long serialVersionUID = 1L;
	private final PERSTDatabase db = PERSTDatabase.getInstance();

	public AlgorithmsPanel() {
		setLayout(new MigLayout("", "[grow]", "[][]"));

		JPanel pnlKNN = new JPanel();
		pnlKNN.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "k-nearest neighbors",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		add(pnlKNN, "cell 0 0,grow");
		pnlKNN.setLayout(new MigLayout("", "[grow]", "[][]"));

		JButton btnStartTestRunKNN = new JButton(
				"Start test run & Display statistics");
		btnStartTestRunKNN.addActionListener(this);
		btnStartTestRunKNN.setActionCommand("startKNNTest");
		pnlKNN.add(btnStartTestRunKNN, "cell 0 0,growx");

		JButton btnClassifyByKNN = new JButton("Classify data");
		btnClassifyByKNN.addActionListener(this);
		btnClassifyByKNN.setActionCommand("classifyByKNN");
		pnlKNN.add(btnClassifyByKNN, "cell 0 1,growx");

		JPanel pnlKMeans = new JPanel();
		pnlKMeans.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "k-means clustering",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		add(pnlKMeans, "cell 0 1,grow");
		pnlKMeans.setLayout(new MigLayout("", "[grow]", "[][]"));

		JButton btnStartTestRunKMeans = new JButton(
				"Start test run & Display statistics");
		btnStartTestRunKMeans.addActionListener(this);
		btnStartTestRunKMeans.setActionCommand("startKMeansTest");
		pnlKMeans.add(btnStartTestRunKMeans, "cell 0 0,growx");

		JButton btnClassifyByKMeans = new JButton("Classify data");
		btnClassifyByKMeans.addActionListener(this);
		btnClassifyByKMeans.setActionCommand("classifyByKMeans");
		pnlKMeans.add(btnClassifyByKMeans, "cell 0 1,growx");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "startKNNTest":
			testKNN();
			break;
		case "classifyByKNN":
			classifyByKNN();
			break;
		case "startKMeansTest":
			// TODO Write code for kMeans test run
			break;
		case "classifyByKMeans":
			// TODO Write code for kMeans classifying
			break;
		}
	}

	private boolean isDatabaseEmpty() {

		if (db.getNumberOfCorrectDatabaseElements() == 0) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Please load some training data first!");
			return true;
		}
		if (db.getNumberOfNonTrainingdataDatabaseElements() == 0) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Please load some data to classify first!");
			return true;
		}
		return false;
	}

	private int inputK() {
		int k = 0;
		String k_str = JOptionPane.showInputDialog(new JFrame(),
				"Enter a value for k.");
		if (k_str == null) {
			return 0;
		}
		k = Integer.valueOf(k_str);
		if (k < 0) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Please choose a value above 0.");
			return 0;
		}
		return k;
	}

	private String inputDistanceMeasurement() {
		String[] distanceCalculationMethods = { "Euclid", "Manhattan" };
		String distanceMeasurement = (String) JOptionPane.showInputDialog(
				new JFrame(), "Choose a distance measurement method:",
				"Distance Measurement", JOptionPane.QUESTION_MESSAGE, null,
				distanceCalculationMethods, distanceCalculationMethods[0]);
		return distanceMeasurement;
	}

	private void testKNN() {
		if(!launchKNN())
			return;
		int numTotalTestObjects = db
				.getNumberOfNonTrainingdataDatabaseElements();
		IterableIterator<DatabaseElement> iter_test = db
				.getNonTrainingdataDatabaseIterator();
		int[] testObjectsPerClass = new int[10];
		Arrays.fill(testObjectsPerClass, 0);
		int meanSquaredError = 0;
		ArrayList<DatabaseElement> falseClassifiedObjects = new ArrayList<DatabaseElement>();
		while (iter_test.hasNext()) {
			DatabaseElement e = iter_test.next();
			int classValue = e.getCorrectClassification();
			int algoValue = e.getAlgoClassification();
			if (classValue != algoValue) {
				falseClassifiedObjects.add(e);
				int meanSquaredError_temp = classValue - algoValue;
				meanSquaredError_temp *= meanSquaredError_temp;
				meanSquaredError += meanSquaredError_temp;
			}
			testObjectsPerClass[classValue]++;
		}
		meanSquaredError = meanSquaredError / numTotalTestObjects;
		int numTotalTrainingObjects = db.getNumberOfCorrectDatabaseElements();
		int[] trainingObjectsPerClass = new int[10];
		Arrays.fill(trainingObjectsPerClass, 0);
		IterableIterator<DatabaseElement> iter_training = db
				.getCorrectDatabaseIterator();
		while (iter_training.hasNext()) {
			DatabaseElement e = iter_training.next();
			int classValue = e.getCorrectClassification();
			trainingObjectsPerClass[classValue]++;
		}

		new StatisticsDialog("k-Nearest-Neighbor",
				chosenDistaneMeasurementMethod, chosenParameterK,
				numTotalTestObjects, testObjectsPerClass,
				numTotalTrainingObjects, trainingObjectsPerClass,
				falseClassifiedObjects, meanSquaredError);
	}

	private void classifyByKNN() {
		if (launchKNN()) {
			IterableIterator<DatabaseElement> iter = db.getNonTrainingdataDatabaseIterator();
			ArrayList<DatabaseElement> classifiedElements = iter.toList();
			new ResultDisplayDialog(classifiedElements);
		}
	}

	private boolean launchKNN() {
		if (isDatabaseEmpty())
			return false;
		chosenParameterK = inputK();
		chosenDistaneMeasurementMethod = inputDistanceMeasurement();
		if (chosenParameterK == 0 || chosenDistaneMeasurementMethod == null) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Please set the algorithm parameters correctly");
			return false;
		}
		KNN kNearestNeighborAlgorithm = new KNN();
		if (chosenDistaneMeasurementMethod == "Euclid") {
			kNearestNeighborAlgorithm.doAlgorithm(KNN.SQR_EUCLID,
					chosenParameterK);
			return true;
		} else if (chosenDistaneMeasurementMethod == "Manhattan") {
			kNearestNeighborAlgorithm.doAlgorithm(KNN.MANHATTAN,
					chosenParameterK);
			return true;
		} else {
			JOptionPane.showMessageDialog(new JFrame(),
					"Invalid distance measurement method chosen");
			return false;
		}
	}
}
