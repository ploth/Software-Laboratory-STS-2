package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import data.PERSTDatabase;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Workbench extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 340;
	private static final int HEIGHT = 620;

	private JPanel dataLoadingPanel_;
	private JPanel databasePanel_;
	private JPanel algorithmsPanel_;

	public Workbench(String title) {
		setResizable(false);
		setTitle(title);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null); // Set initial window position to center of
									 // screen
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// -----------------------
		// TABS
		// -----------------------

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
		
		// -----------------------
		// TABS
		// -----------------------

		dataLoadingPanel_ = new InputOutputPanel();
		tabbedPane.addTab("Input/Output Data", dataLoadingPanel_);
		tabbedPane.addTab("Database", databasePanel_);
		tabbedPane.addTab("Algorithms", algorithmsPanel_);
		this.getContentPane().add(tabbedPane); // Add tabs to workbench

		addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                PERSTDatabase.getInstance().closeDB();
                e.getWindow().dispose();
            }
        });
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "about":
			JOptionPane.showMessageDialog(this, "Programmed: by\nPascal Loth\nThomas Schattschneider\n\n2015");
			break;
		default:
			System.err.println("Unknown action: " + e.getActionCommand());
		}
	}
}
