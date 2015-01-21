package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import algorithm.AlgorithmException;
import algorithm.KMean;
import algorithm.KNN;
import data.PERSTDatabase;
import data.PERSTDatabase.DatabaseElement;

/*
 * This class implements a panel which allows choosing of an algorithm to process data.
 * The algorithm test runs and the data classifying can be started from here.
 */
public class AlgorithmsPanel extends JPanel implements ActionListener {

	// Stores the user-chosen distance measurement method for the next algorithm
	private String chosenDistaneMeasurementMethod;
	// Stores the user-chosen parameter k for the next algorithm.
	private int chosenParameterK;
	private static final long serialVersionUID = 1L;
	private final PERSTDatabase db = PERSTDatabase.getInstance();
	// These buttons have to be members of the class because they get disabled
	// and enabled by actions.
	private final JButton btnStartTestRunKMean;
	private final JButton btnClassifyByKMean;
	// It is necessary to keep a reference to the k-Mean-algorithm,
	// because it works in two steps. Create the class map first,
	// then use it to classify new elements.
	private KMean kMeanAlgorithm;

	private enum Algorithm {
		KMEAN, KNN
	}

	public AlgorithmsPanel() {
		setLayout(new MigLayout("", "[grow]", "[][]"));

		// /////////////////////////////////////////////
		// KNN GUI elements
		// /////////////////////////////////////////////

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

		// /////////////////////////////////////////////
		// KMean GUI elements
		// /////////////////////////////////////////////

		JPanel pnlKMeans = new JPanel();
		pnlKMeans.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "k-means clustering",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		add(pnlKMeans, "cell 0 1,grow");
		pnlKMeans.setLayout(new MigLayout("", "[grow]", "[][][]"));

		JButton btnPerformKmeansclustering = new JButton(
				"Perform k-Means clustering");
		btnPerformKmeansclustering.addActionListener(this);
		btnPerformKmeansclustering.setActionCommand("performClustering");
		pnlKMeans.add(btnPerformKmeansclustering, "cell 0 0,growx");

		btnStartTestRunKMean = new JButton(
				"Start test run & Display statistics");
		btnStartTestRunKMean.addActionListener(this);
		btnStartTestRunKMean.setActionCommand("startKMeanTest");
		btnStartTestRunKMean.setEnabled(false);
		pnlKMeans.add(btnStartTestRunKMean, "cell 0 1,growx");

		btnClassifyByKMean = new JButton("Classify data");
		btnClassifyByKMean.addActionListener(this);
		btnClassifyByKMean.setActionCommand("classifyByKMean");
		btnClassifyByKMean.setEnabled(false);
		pnlKMeans.add(btnClassifyByKMean, "cell 0 2,growx");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "startKNNTest":
			if (!launchKNN())
				return;
			testAlgorithm(Algorithm.KNN);
			break;
		case "classifyByKNN":
			classifyByKNN();
			break;
		case "performClustering":
			// Create a new class map by clustering the data
			if (!launchKMean())
				return;
			// Get the cluster means calculated by the algorithm and display
			// them in the classification dialog
			double[][] clusterMeans = kMeanAlgorithm.getClusterMeans();
			// Store the user-classifications
			char[] clusterClassifications = new char[chosenParameterK];
			new ClusterClassificationDialog(clusterMeans,
					clusterClassifications);
			for (int i = 0; i < clusterClassifications.length; i++) {
				// Pass user-classifications to algorithm
				kMeanAlgorithm.classifyCluster(i, clusterClassifications[i]);
			}
			btnStartTestRunKMean.setEnabled(true);
			btnClassifyByKMean.setEnabled(true);
			JOptionPane
					.showMessageDialog(new JFrame(),
							"The k-Mean cluster map was created, you can now classify new objects quickly.");
			break;
		case "startKMeanTest":
			// Check if there is data to be classified
			if (db.getNumberOfNonTrainingdataDatabaseElements() > 0) {
				testAlgorithm(Algorithm.KMEAN);
			} else {
				JOptionPane.showMessageDialog(new JFrame(),
						"Please load some test data to classify first!");
			}
			break;
		case "classifyByKMean":
			classifyByKMean();
			break;
		}
	}

	private boolean isDatabaseEmpty() {
		if (db.getNumberOfTrainingdataDatabaseElements() == 0) {
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

	// Method for choosing parameter k by GUI
	private int inputK() {
		int k = 0;
		String k_str = JOptionPane.showInputDialog(new JFrame(),
				"Enter a value for k.");
		if (k_str == null || k_str.isEmpty()) {
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

	// Method for choosing distance measurement by GUI
	private String inputDistanceMeasurement() {
		String[] distanceCalculationMethods = { "Euclid", "Manhattan" };
		String distanceMeasurement = (String) JOptionPane.showInputDialog(
				new JFrame(), "Choose a distance measurement method:",
				"Distance Measurement", JOptionPane.QUESTION_MESSAGE, null,
				distanceCalculationMethods, distanceCalculationMethods[0]);
		return distanceMeasurement;
	}

	private boolean launchKMean() {
		// Only perform KMean if database is not empty
		if (db.getNumberOfTrainingdataDatabaseElements() == 0
				&& db.getNumberOfNonTrainingdataDatabaseElements() == 0) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Please load some data first!");
			return false;
		}

		// Choose parameters
		chosenParameterK = inputK();
		chosenDistaneMeasurementMethod = inputDistanceMeasurement();
		if (chosenParameterK == 0 || chosenDistaneMeasurementMethod.isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Please set the algorithm parameters correctly");
			return false;
		}
		int maxIterations = 0;
		String maxIterations_str = JOptionPane.showInputDialog(new JFrame(),
				"Enter the maximum iteration limit:");
		if (maxIterations_str.isEmpty()) {
			return false;
		}
		maxIterations = Integer.valueOf(maxIterations_str);
		if (maxIterations <= 0) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Please choose a value above 0.");
			return false;
		}
		double deviation = 0;
		String deviation_str = JOptionPane.showInputDialog(new JFrame(),
				"Enter the threshold for the cluster mean deviation.\n"
						+ "The algorithm will stop when the cluster means\n"
						+ "move less than this deviation threshold.\n"
						+ "Values from 0.1 to 10 make sense,\n"
						+ "but you can input any positive number");
		if (deviation_str.isEmpty()) {
			return false;
		}
		deviation = Double.valueOf(deviation_str);
		if (deviation <= 0) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Please choose a value above 0.");
			return false;
		}

		kMeanAlgorithm = new KMean();
		// Set parameters
		kMeanAlgorithm.setMaxIterations(maxIterations);
		kMeanAlgorithm.setDeviation(deviation);
		// Start the k-Means-clustering algorithm with the appropriate distance
		// measurement method. This first step only classifies the cluster means
		// and creates a class map for classifying new data.
		try {
			if (chosenDistaneMeasurementMethod == "Euclid") {
				kMeanAlgorithm.doAlgorithm(KNN.SQR_EUCLID, chosenParameterK);
				return true;
			} else if (chosenDistaneMeasurementMethod == "Manhattan") {
				kMeanAlgorithm.doAlgorithm(KNN.MANHATTAN, chosenParameterK);
				return true;
			} else {
				JOptionPane.showMessageDialog(new JFrame(),
						"Invalid distance measurement method chosen");
				return false;
			}
		} catch (AlgorithmException e) {
			JOptionPane.showMessageDialog(new JFrame(),
					"An error ocurred while performing the k-Means algorithm. Message:\n"
							+ e.getMessage(), "Algorithm error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	private void classifyByKMean() {
		String distanceMeasurement = inputDistanceMeasurement();
		int type;
		if (distanceMeasurement.equals("Euclid")) {
			type = KMean.SQR_EUCLID;
		} else if (distanceMeasurement.equals("Manhattan")) {
			type = KMean.MANHATTAN;
		} else {
			return;
		}
		// Classify all data, which is stil to be classified
		boolean success = kMeanAlgorithm.classifyNewElements(type);
		if (success) {
			// Collect all the new classified elements and display them in the
			// results panel
			IterableIterator<DatabaseElement> iter = db
					.getNonTrainingdataDatabaseIterator();
			ArrayList<DatabaseElement> classifiedElements = iter.toList();
			if (!classifiedElements.isEmpty()) {
				new ResultDisplayDialog(classifiedElements);
			} else {
				JOptionPane.showMessageDialog(new JFrame(),
						"Please load some data to classify first!");
			}
		}
	}

	// Runs a test of an algorithm and displays statistics about the run
	// afterwards
	private void testAlgorithm(Algorithm algorithm) {
		int numTotalTestObjects = db
				.getNumberOfNonTrainingdataDatabaseElements();
		IterableIterator<DatabaseElement> iter_test = db
				.getNonTrainingdataDatabaseIterator();
		int[] testObjectsPerClass = new int[10];
		Arrays.fill(testObjectsPerClass, 0);
		int numTotalTrainingObjects = db
				.getNumberOfTrainingdataDatabaseElements();
		int[] trainingObjectsPerClass = new int[10];
		Arrays.fill(trainingObjectsPerClass, 0);
		IterableIterator<DatabaseElement> iter_training = db
				.getCorrectDatabaseIterator();
		// Count the number of training objects per class
		while (iter_training.hasNext()) {
			DatabaseElement e = iter_training.next();
			int classValue = e.getCorrectClassification();
			trainingObjectsPerClass[classValue]++;
		}

		int meanSquaredError = 0;
		ArrayList<DatabaseElement> falseClassifiedObjects = new ArrayList<DatabaseElement>();
		// Count the number of false classified objects
		while (iter_test.hasNext()) {
			DatabaseElement e = iter_test.next();
			int classValue = e.getCorrectClassification();
			int algoValue = e.getAlgoClassification();
			// Continue processing of current element only if it has a known
			// classification
			if (classValue == PERSTDatabase.NO_CORRECT_CLASSIFICATION) {
				continue;
			}
			// Check if current element has false classification and calculate
			// error
			if (classValue != algoValue) {
				falseClassifiedObjects.add(e);
				int meanSquaredError_temp = classValue - algoValue;
				meanSquaredError_temp *= meanSquaredError_temp;
				meanSquaredError += meanSquaredError_temp;
			}
			testObjectsPerClass[classValue]++;
		}
		// Calculate the final mean error using previously calculated values
		meanSquaredError = meanSquaredError / numTotalTestObjects;

		// Display the statistics window for the chosen algorithm
		if (algorithm == Algorithm.KNN) {
			new StatisticsDialog("k-Nearest-Neighbor",
					chosenDistaneMeasurementMethod, chosenParameterK,
					numTotalTestObjects, testObjectsPerClass,
					numTotalTrainingObjects, trainingObjectsPerClass,
					falseClassifiedObjects, meanSquaredError);
		} else if (algorithm == Algorithm.KMEAN) {
			new StatisticsDialog("k-Nearest-Neighbor",
					chosenDistaneMeasurementMethod, chosenParameterK,
					numTotalTestObjects, testObjectsPerClass,
					numTotalTrainingObjects, trainingObjectsPerClass,
					falseClassifiedObjects, meanSquaredError);
		}
	}

	// Launches the KNN algorithm and displays it's results in a new window
	private void classifyByKNN() {
		if (launchKNN()) {
			IterableIterator<DatabaseElement> iter = db
					.getNonTrainingdataDatabaseIterator();
			ArrayList<DatabaseElement> classifiedElements = iter.toList();
			new ResultDisplayDialog(classifiedElements);
		}
	}

	// Launches the KNN algorithm
	private boolean launchKNN() {
		if (isDatabaseEmpty())
			return false;

		// Choose parameters
		chosenParameterK = inputK();
		chosenDistaneMeasurementMethod = inputDistanceMeasurement();
		if (chosenParameterK == 0 || chosenDistaneMeasurementMethod.isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Please set the algorithm parameters correctly");
			return false;
		}

		KNN kNearestNeighborAlgorithm = new KNN();

		// Start algorithm with chosen distance measurement method
		try {
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
		} catch (AlgorithmException e) {
			JOptionPane.showMessageDialog(new JFrame(),
					"An error ocurred while performing the k-NN algorithm. Message:\n"
							+ e.getMessage(), "Algorithm error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
}
