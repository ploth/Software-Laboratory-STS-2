package gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;

public class Workbench extends JFrame implements ActionListener {
	
	private static final int WIDTH = 1152;
	private static final int HEIGHT = 864;
	
	private DataloadingPanel dataLoadingPanel_;
	private DatabasePanel databasePanel_;
	private AlgorithmsPanel algorithmsPanel_;
	private JLabel lblNumberOfLabels;
	private JLabel lblNumberOfImages;
	
	
	public Workbench(String title) {
		setResizable(false);
		setTitle(title);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null); //Set initial window position to center of screen
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//-----------------------
		// 		TABS
		//-----------------------
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu_help = new JMenu("Help");
		JMenuItem item_about = new JMenuItem("About...");
		item_about.addActionListener(this);
		item_about.setActionCommand("about");
		menu_help.add(item_about);
		menuBar.add(menu_help);
		
		setJMenuBar(menuBar);
		databasePanel_ = new DatabasePanel();
		algorithmsPanel_ = new AlgorithmsPanel();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		//-----------------------
		// 		TABS
		//-----------------------
		
		dataLoadingPanel_ = new DataloadingPanel();
		tabbedPane.addTab("Input/Output Data", dataLoadingPanel_);
		
		//-----------------------
		//		GUI BUILDER
		//-----------------------
		
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
		
		GroupLayout gl_dataLoadingPanel_ = new GroupLayout(dataLoadingPanel_);
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
		dataLoadingPanel_.setLayout(gl_dataLoadingPanel_);
		tabbedPane.addTab("Database", databasePanel_);
		GridBagLayout gbl_databasePanel_ = new GridBagLayout();
		gbl_databasePanel_.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_databasePanel_.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_databasePanel_.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_databasePanel_.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		databasePanel_.setLayout(gbl_databasePanel_);
		
		JLabel lblNumberOfLabels_1 = new JLabel("Number of labels in database: ");
		GridBagConstraints gbc_lblNumberOfLabels_1 = new GridBagConstraints();
		gbc_lblNumberOfLabels_1.gridwidth = 2;
		gbc_lblNumberOfLabels_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfLabels_1.gridx = 1;
		gbc_lblNumberOfLabels_1.gridy = 1;
		databasePanel_.add(lblNumberOfLabels_1, gbc_lblNumberOfLabels_1);
		
		JButton btnDeleteLabels = new JButton("Delete labels");
		GridBagConstraints gbc_btnDeleteLabels = new GridBagConstraints();
		gbc_btnDeleteLabels.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDeleteLabels.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeleteLabels.gridx = 3;
		gbc_btnDeleteLabels.gridy = 1;
		databasePanel_.add(btnDeleteLabels, gbc_btnDeleteLabels);
		
		JLabel lblNumberOfImages_1 = new JLabel("Number of images in database:");
		GridBagConstraints gbc_lblNumberOfImages_1 = new GridBagConstraints();
		gbc_lblNumberOfImages_1.gridwidth = 2;
		gbc_lblNumberOfImages_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfImages_1.gridx = 1;
		gbc_lblNumberOfImages_1.gridy = 2;
		databasePanel_.add(lblNumberOfImages_1, gbc_lblNumberOfImages_1);
		
		JButton btnDeleteImages = new JButton("Delete images");
		GridBagConstraints gbc_btnDeleteImages = new GridBagConstraints();
		gbc_btnDeleteImages.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeleteImages.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDeleteImages.gridx = 3;
		gbc_btnDeleteImages.gridy = 2;
		databasePanel_.add(btnDeleteImages, gbc_btnDeleteImages);
		
		JButton btnShowData = new JButton("Show data");
		GridBagConstraints gbc_btnShowData = new GridBagConstraints();
		gbc_btnShowData.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnShowData.gridwidth = 3;
		gbc_btnShowData.insets = new Insets(0, 0, 0, 5);
		gbc_btnShowData.gridx = 1;
		gbc_btnShowData.gridy = 4;
		databasePanel_.add(btnShowData, gbc_btnShowData);
		tabbedPane.addTab("Algorithms", algorithmsPanel_);
		algorithmsPanel_.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnKmeans = new JButton("KMeans");
		btnKmeans.setFont(new Font("Tahoma", Font.PLAIN, 45));
		algorithmsPanel_.add(btnKmeans);
		
		JButton btnKnn = new JButton("KNN");
		btnKnn.setFont(new Font("Tahoma", Font.PLAIN, 45));
		algorithmsPanel_.add(btnKnn);
		
		this.getContentPane().add(tabbedPane); //Add tabs to workbench
		
		updateDatabaseCounters();
		
		setVisible(true);
	}
	
	//TODO Get numbers from databae
	private void updateDatabaseCounters() {
		lblNumberOfLabels.setText("Number of labels in database: " + "	0");
		lblNumberOfImages.setText("Number of images in database: " + "	0");
	}
	
	private void displayAboutInfo() {
		JDialog aboutDialog = new JDialog();
		aboutDialog.setTitle("About this program");
		aboutDialog.getContentPane().setLayout(new GridBagLayout());
		aboutDialog.setSize(300, 200);
		aboutDialog.setLocationRelativeTo(this);
		aboutDialog.setResizable(false);
		aboutDialog.setModal(true);
		aboutDialog.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "about":	displayAboutInfo();
						break;
		default:		System.err.println("Unknown action: " + e.getActionCommand());
		}
	}
}
