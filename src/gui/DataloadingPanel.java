package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DataloadingPanel extends JPanel {
	
	private JLabel lblNumberOfLabels;
	private JLabel lblNumberOfImages;
	
	public DataloadingPanel() {
		
		JLabel lblAddTrainingData = new JLabel("Add training data to the database using the buttons below");
		lblAddTrainingData.setFont(new Font("Tahoma", Font.PLAIN, 23));
		
		JButton btnAddCsvFile = new JButton("Add CSV File to database");
		
		JButton btnAddIdxubyteFile = new JButton("Add idx1.ubyte file to labels database");
		
		JButton btnAddPngImage = new JButton("Add PNG file to image database");
		
		JButton btnAddIdxubyte = new JButton("Add idx3-ubyte file to image database");
		btnAddIdxubyte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JLabel lblAddTrainingData_1 = new JLabel("Add training data");
		
		JButton btnSaveDatabaseTo = new JButton("Save database to MNIST idx-ubytefiles");
		
		JButton btnSaveDatabaseTo_1 = new JButton("Save database to CSV file");
		
		JButton btnSaveDatabaseTo_2 = new JButton("Save database to PNG files");
		
		JLabel lblSaveDatabaseTo = new JLabel("Save database to file");
		
		lblNumberOfLabels = new JLabel("Number of labels in database:");
		
		lblNumberOfImages = new JLabel("Number of images in database:");
		
		JSeparator separator = new JSeparator();
		
		GroupLayout gl_dataLoadingPanel_ = new GroupLayout(this);
		gl_dataLoadingPanel_.setHorizontalGroup(
			gl_dataLoadingPanel_.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_dataLoadingPanel_.createSequentialGroup()
					.addGap(298)
					.addGroup(gl_dataLoadingPanel_.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblAddTrainingData_1)
						.addComponent(btnAddPngImage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnAddIdxubyteFile, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnAddCsvFile, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
						.addComponent(btnAddIdxubyte, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(46)
					.addGroup(gl_dataLoadingPanel_.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSaveDatabaseTo, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
						.addComponent(lblSaveDatabaseTo)
						.addComponent(btnSaveDatabaseTo_1, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
						.addComponent(btnSaveDatabaseTo_2, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
					.addGap(298))
				.addGroup(gl_dataLoadingPanel_.createSequentialGroup()
					.addGap(491)
					.addGroup(gl_dataLoadingPanel_.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNumberOfLabels)
						.addComponent(lblNumberOfImages))
					.addContainerGap(492, Short.MAX_VALUE))
				.addGroup(gl_dataLoadingPanel_.createSequentialGroup()
					.addGap(274)
					.addGroup(gl_dataLoadingPanel_.createParallelGroup(Alignment.LEADING)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 601, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAddTrainingData))
					.addContainerGap(266, Short.MAX_VALUE))
		);
		gl_dataLoadingPanel_.setVerticalGroup(
			gl_dataLoadingPanel_.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_dataLoadingPanel_.createSequentialGroup()
					.addGap(52)
					.addComponent(lblAddTrainingData)
					.addGap(14)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 4, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNumberOfLabels)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNumberOfImages)
					.addGap(40)
					.addGroup(gl_dataLoadingPanel_.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSaveDatabaseTo)
						.addComponent(lblAddTrainingData_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_dataLoadingPanel_.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAddCsvFile)
						.addComponent(btnSaveDatabaseTo))
					.addGroup(gl_dataLoadingPanel_.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_dataLoadingPanel_.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAddIdxubyteFile)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAddPngImage))
						.addGroup(gl_dataLoadingPanel_.createSequentialGroup()
							.addGap(6)
							.addComponent(btnSaveDatabaseTo_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSaveDatabaseTo_2)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAddIdxubyte)
					.addGap(468))
		);
		setLayout(gl_dataLoadingPanel_);
		updateDatabaseCounters();
	}
	
	private void updateDatabaseCounters() {
		lblNumberOfLabels.setText("Number of labels in database: " + "	0");
		lblNumberOfImages.setText("Number of images in database: " + "	0");
	}
}
