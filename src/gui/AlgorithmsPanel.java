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
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "k-nearest neighbors", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[][]"));
		
		JButton btnStartKNN = new JButton("Classify new data");
		btnStartKNN.addActionListener(this);
		
		JButton btnStartTestRunKNN = new JButton("Start test run & Display statistics");
		panel.add(btnStartTestRunKNN, "cell 0 0,growx");
		btnStartKNN.setActionCommand("startKNN");
		panel.add(btnStartKNN, "cell 0 1,growx");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "k-means clustering", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new MigLayout("", "[grow]", "[][]"));
		
		JButton btnStartTestRunKMeans = new JButton("Start test run & Display statistics");
		panel_1.add(btnStartTestRunKMeans, "cell 0 0,growx");
		
		JButton btnStartKMeans = new JButton("Classify new data");
		panel_1.add(btnStartKMeans, "cell 0 1,growx");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "startKNN":
			classifyByKNN();
			break;
		case "startKMEANS":
			break;
		}
	}
	
	private void classifyByKNN() {
		KNN kNearestNeighborAlgorithm = new KNN();
		kNearestNeighborAlgorithm.doAlgorithm(KNN.SQR_EUCLID, 20);
		new KNNDialog();
	}
}
