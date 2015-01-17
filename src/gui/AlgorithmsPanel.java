package gui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import algorithm.KNN;
import javax.swing.JLabel;

public class AlgorithmsPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

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
	
	private void classifyByKNN() {
		KNN kNearestNeighborAlgorithm = new KNN();
		kNearestNeighborAlgorithm.doAlgorithm(KNN.SQR_EUCLID, 20);
		new KNNDialog();
	}
}
