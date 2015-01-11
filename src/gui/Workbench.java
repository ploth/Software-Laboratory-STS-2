package gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;

public class Workbench extends JFrame implements ActionListener {
	
	private static final int WIDTH = 1152;
	private static final int HEIGHT = 864;
	
	private DataloadingPanel dataLoadingPanel_;
	private DatabasePanel databasePanel_;
	private AlgorithmsPanel algorithmsPanel_;
	
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
		
		JLabel label = new JLabel("");
		
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
		GroupLayout gl_dataLoadingPanel_ = new GroupLayout(dataLoadingPanel_);
		gl_dataLoadingPanel_.setHorizontalGroup(
			gl_dataLoadingPanel_.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_dataLoadingPanel_.createSequentialGroup()
					.addGroup(gl_dataLoadingPanel_.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_dataLoadingPanel_.createSequentialGroup()
							.addGap(570)
							.addComponent(label))
						.addGroup(gl_dataLoadingPanel_.createSequentialGroup()
							.addGap(274)
							.addComponent(lblAddTrainingData))
						.addGroup(gl_dataLoadingPanel_.createSequentialGroup()
							.addGap(332)
							.addGroup(gl_dataLoadingPanel_.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnAddCsvFile, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnAddIdxubyteFile, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnAddIdxubyte, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnAddPngImage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblAddTrainingData_1, Alignment.LEADING))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_dataLoadingPanel_.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSaveDatabaseTo)
								.addComponent(btnSaveDatabaseTo)
								.addComponent(btnSaveDatabaseTo_2)
								.addComponent(btnSaveDatabaseTo_1))))
					.addContainerGap(274, Short.MAX_VALUE))
		);
		gl_dataLoadingPanel_.setVerticalGroup(
			gl_dataLoadingPanel_.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_dataLoadingPanel_.createSequentialGroup()
					.addGap(5)
					.addComponent(label)
					.addGap(149)
					.addComponent(lblAddTrainingData)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_dataLoadingPanel_.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_dataLoadingPanel_.createSequentialGroup()
							.addGroup(gl_dataLoadingPanel_.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAddTrainingData_1)
								.addComponent(lblSaveDatabaseTo))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAddCsvFile)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAddIdxubyteFile)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAddPngImage))
						.addGroup(gl_dataLoadingPanel_.createSequentialGroup()
							.addComponent(btnSaveDatabaseTo)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSaveDatabaseTo_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSaveDatabaseTo_2)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAddIdxubyte)
					.addGap(468))
		);
		dataLoadingPanel_.setLayout(gl_dataLoadingPanel_);
		tabbedPane.addTab("Database", databasePanel_);
		tabbedPane.addTab("Algorithms", algorithmsPanel_);
		
		this.getContentPane().add(tabbedPane); //Add tabs to workbench
		
		setVisible(true);
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
