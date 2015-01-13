package gui;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;

public class AlgorithmsPanel extends JPanel {
	public AlgorithmsPanel() {
		setLayout(new MigLayout("", "[grow]", "[grow][grow]"));
		
		JButton btnKnn = new JButton("KNN");
		btnKnn.setFont(new Font("Tahoma", Font.PLAIN, 36));
		btnKnn.setBackground(Color.GRAY);
		add(btnKnn, "cell 0 0,grow");
		
		JButton btnKmeans = new JButton("KMeans");
		btnKmeans.setFont(new Font("Tahoma", Font.PLAIN, 36));
		btnKmeans.setBackground(Color.GRAY);
		add(btnKmeans, "cell 0 1,grow");
	}
}
