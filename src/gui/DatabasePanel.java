package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DatabasePanel extends JPanel {
	public DatabasePanel() {
		GridBagLayout gbl_databasePanel_ = new GridBagLayout();
		gbl_databasePanel_.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_databasePanel_.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_databasePanel_.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_databasePanel_.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_databasePanel_);
		
		JLabel lblNumberOfLabels_1 = new JLabel("Number of labels in database: ");
		GridBagConstraints gbc_lblNumberOfLabels_1 = new GridBagConstraints();
		gbc_lblNumberOfLabels_1.gridwidth = 2;
		gbc_lblNumberOfLabels_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfLabels_1.gridx = 1;
		gbc_lblNumberOfLabels_1.gridy = 1;
		add(lblNumberOfLabels_1, gbc_lblNumberOfLabels_1);
		
		JButton btnDeleteLabels = new JButton("Delete labels");
		GridBagConstraints gbc_btnDeleteLabels = new GridBagConstraints();
		gbc_btnDeleteLabels.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDeleteLabels.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeleteLabels.gridx = 3;
		gbc_btnDeleteLabels.gridy = 1;
		add(btnDeleteLabels, gbc_btnDeleteLabels);
		
		JLabel lblNumberOfImages_1 = new JLabel("Number of images in database:");
		GridBagConstraints gbc_lblNumberOfImages_1 = new GridBagConstraints();
		gbc_lblNumberOfImages_1.gridwidth = 2;
		gbc_lblNumberOfImages_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfImages_1.gridx = 1;
		gbc_lblNumberOfImages_1.gridy = 2;
		add(lblNumberOfImages_1, gbc_lblNumberOfImages_1);
		
		JButton btnDeleteImages = new JButton("Delete images");
		GridBagConstraints gbc_btnDeleteImages = new GridBagConstraints();
		gbc_btnDeleteImages.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeleteImages.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDeleteImages.gridx = 3;
		gbc_btnDeleteImages.gridy = 2;
		add(btnDeleteImages, gbc_btnDeleteImages);
		
		JButton btnShowData = new JButton("Show data");
		GridBagConstraints gbc_btnShowData = new GridBagConstraints();
		gbc_btnShowData.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnShowData.gridwidth = 3;
		gbc_btnShowData.insets = new Insets(0, 0, 0, 5);
		gbc_btnShowData.gridx = 1;
		gbc_btnShowData.gridy = 4;
		add(btnShowData, gbc_btnShowData);
	}
}
