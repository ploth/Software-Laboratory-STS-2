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

public class AlgorithmsPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	public AlgorithmsPanel() {
		setLayout(new MigLayout("", "[grow]", "[][]"));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "k-nearest neighbors", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[]"));
		
		JButton btnStartKnearestneighborAlgorithm = new JButton("Start k-NN algorithm");
		btnStartKnearestneighborAlgorithm.addActionListener(this);
		btnStartKnearestneighborAlgorithm.setActionCommand("startKNN");
		panel.add(btnStartKnearestneighborAlgorithm, "cell 0 0,growx");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "k-means clustering", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new MigLayout("", "[grow]", "[]"));
		
		JButton btnStartKmeansclusteringAlgorithm = new JButton("Start k-means clustering algorithm");
		panel_1.add(btnStartKmeansclusteringAlgorithm, "cell 0 0,growx");
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
