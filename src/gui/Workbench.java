package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.garret.perst.StorageError;

import data.PERSTDatabase;

/*
 * This workbench window provides the different data handling panels for loading/exporting, viewing, 
 * and recognizing data within the different tabs of the tabbed pane.
 */
public class Workbench extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 340;
	private static final int HEIGHT = 600;

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

		// /////////////////////////////////////////////
		// Menu bar
		// /////////////////////////////////////////////

		JMenuBar menuBar = new JMenuBar();
		JMenu menu_help = new JMenu("Help");
		JMenuItem item_about = new JMenuItem("About...");
		item_about.addActionListener(this);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmDeleteDatabase = new JMenuItem("Delete database & Exit");
		mntmDeleteDatabase.addActionListener(this);
		mntmDeleteDatabase.setActionCommand("deleteAndExit");
		mnFile.add(mntmDeleteDatabase);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(this);
		mntmExit.setActionCommand("exit");
		mnFile.add(mntmExit);
		item_about.setActionCommand("about");
		menu_help.add(item_about);
		menuBar.add(menu_help);

		setJMenuBar(menuBar);

		// /////////////////////////////////////////////
		// Tabs
		// /////////////////////////////////////////////

		databasePanel_ = new DatabasePanel();
		algorithmsPanel_ = new AlgorithmsPanel();

		JTabbedPane tabbedPane = new JTabbedPane();

		dataLoadingPanel_ = new InputOutputPanel();
		tabbedPane.addTab("Input/Output Data", dataLoadingPanel_);
		tabbedPane.addTab("Database", databasePanel_);
		tabbedPane.addTab("Algorithms", algorithmsPanel_);
		this.getContentPane().add(tabbedPane); // Add tabs to workbench

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
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
			JOptionPane
					.showMessageDialog(this,
							"Programmed: by\nPascal Loth\nThomas Schattschneider\n\n2015");
			break;
		case "deleteAndExit":
			Path dbPath = FileSystems.getDefault().getPath(
					PERSTDatabase.getDBName());
			try {
				PERSTDatabase.getInstance().closeDB();
				Files.deleteIfExists(dbPath);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(new JFrame(),
						"An error ocurred while trying to delete the database file:\n"
								+ e1.getMessage(), "IOException",
						JOptionPane.ERROR_MESSAGE);
			} catch (StorageError e2) {
				JOptionPane.showMessageDialog(new JFrame(),
						"An error ocurred while trying to delete the database file:\n"
								+ e2.getMessage(), "Storage error",
						JOptionPane.ERROR_MESSAGE);
			}
			dispose();
			break;
		case "exit":
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			break;
		default:
			JOptionPane.showMessageDialog(new JFrame(),
					"Error: Unknown GUI command", "GUI error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
