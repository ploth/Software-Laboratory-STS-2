package gui;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import data.PERSTDatabase;
import algorithm.KNN;

public class AlgorithmsPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private final PERSTDatabase db = PERSTDatabase.getInstance();

	public AlgorithmsPanel() {
		setLayout(new MigLayout("", "[grow]", "[][]"));
		
		JPanel pnlKNN = new JPanel();
		pnlKNN.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "k-nearest neighbors", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(pnlKNN, "cell 0 0,grow");
		pnlKNN.setLayout(new MigLayout("", "[grow]", "[][]"));
		
		JButton btnStartTestRunKNN = new JButton("Start test run & Display statistics");
		btnStartTestRunKNN.addActionListener(this);
		btnStartTestRunKNN.setActionCommand("startKNNTest");
		pnlKNN.add(btnStartTestRunKNN, "cell 0 0,growx");
		
		JButton btnClassifyByKNN = new JButton("Classify data");
		btnClassifyByKNN.addActionListener(this);
		btnClassifyByKNN.setActionCommand("classifyByKNN");
		pnlKNN.add(btnClassifyByKNN, "cell 0 1,growx");
		
		JPanel pnlKMeans = new JPanel();
		pnlKMeans.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "k-means clustering", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(pnlKMeans, "cell 0 1,grow");
		pnlKMeans.setLayout(new MigLayout("", "[grow]", "[][]"));
		
		JButton btnStartTestRunKMeans = new JButton("Start test run & Display statistics");
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
			//TODO Write code for kNN test run
			break;
		case "classifyByKNN":
			classifyByKNN();
			break;
		case "startKMeansTest":
			//TODO Write code for kMeans test run
			break;
		case "classifyByKMeans":
			//TODO Write code for kMeans classifying
			break;
		}
	}
	
	private boolean isDatabaseEmpty() {
		
		if(db.getNumberOfCorrectDatabaseElements() == 0) {
			JOptionPane.showMessageDialog(new JFrame(), "Please load some training data first!");
			return true;
		} 
		if(db.getNumberOfUncorrectDatabaseElements() == 0) {
			JOptionPane.showMessageDialog(new JFrame(), "Please load some data to classify first!");
			return true;
		}
		return false;
	}
	
	private boolean inputAlgorithmParameters(int k, String distanceMeasurement) {
		String k_str = JOptionPane.showInputDialog(new JFrame(), "Enter a value for k.");
		if(k_str==null) {
			return false;
		}
		k = Integer.valueOf(k_str);
		if(k<0) {
			JOptionPane.showMessageDialog(new JFrame(), "Please choose a value above 0.");
			return false;
		}
		String[] distanceCalculationMethods = {"Square-euclid","Manhattan"};
		distanceMeasurement = (String) JOptionPane.showInputDialog(
				new JFrame(),
				"Choose a distance measurement method:",
				"Distance Measurement",
				JOptionPane.QUESTION_MESSAGE,
				null,
				distanceCalculationMethods,
				distanceCalculationMethods[0]);
		if(distanceMeasurement==null) {
			return false;
		}
		return true;
	}
	
	private void classifyByKNN() {
		if(isDatabaseEmpty()) 
			return;
		int k = 0;
		String distanceMeasurement = "";
		boolean paramsSet = inputAlgorithmParameters(k, distanceMeasurement);
		if(!paramsSet)
			return;
		KNN kNearestNeighborAlgorithm = new KNN();
		if(distanceMeasurement=="Square-euclid") {
			kNearestNeighborAlgorithm.doAlgorithm(KNN.SQR_EUCLID, k);
		} else if (distanceMeasurement=="Manhattan") {
			kNearestNeighborAlgorithm.doAlgorithm(KNN.MANHATTAN, k);
		}
		
		new ResultDisplayDialog();
	}
}
