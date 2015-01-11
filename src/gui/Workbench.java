package gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.*;

public class Workbench extends JFrame implements ActionListener {
	
	private DataloadingPanel dataLoadingPanel_;
	private DatabasePanel databasePanel_;
	private AlgorithmsPanel algorithmsPanel_;
	
	public Workbench(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
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
		
		//-----------------------
		// 		TABS
		//-----------------------
		
		dataLoadingPanel_ = new DataloadingPanel();
		databasePanel_ = new DatabasePanel();
		algorithmsPanel_ = new AlgorithmsPanel();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Load Data", dataLoadingPanel_);
		tabbedPane.addTab("Database", databasePanel_);
		tabbedPane.addTab("Algorithms", algorithmsPanel_);
		
		this.getContentPane().add(tabbedPane); //Add tabs to workbench
		
		setVisible(true);
	}
	
	private void displayAboutInfo() {
		JDialog aboutDialog = new JDialog();
		aboutDialog.setTitle("About this program");
		aboutDialog.setLayout(new GridBagLayout());
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
