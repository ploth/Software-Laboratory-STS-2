package gui;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class AlgorithmsPanel extends JPanel {
	public AlgorithmsPanel() {
		setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnKmeans = new JButton("KMeans");
		btnKmeans.setFont(new Font("Tahoma", Font.PLAIN, 45));
		add(btnKmeans);
		
		JButton btnKnn = new JButton("KNN");
		btnKnn.setFont(new Font("Tahoma", Font.PLAIN, 45));
		add(btnKnn);
	}
}
