package main;

import gui.Workbench;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class OCRApp {
	public static void main(String[] args) {

		// /////////////////////////////////////////////
		// Set GUI design
		// /////////////////////////////////////////////

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			JOptionPane
					.showMessageDialog(
							new JFrame(),
							"System look and feel is not supported!\n"
									+ e.getMessage(),
							"Unsupported look and feel",
							JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(
					new JFrame(),
					"The LookAndFeel class could not be found.\n"
							+ e.getMessage(), "LookAndFeel class not found",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (InstantiationException e) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Could not create a new instance of the LookAndFeel class.\n"
							+ e.getMessage(), "InstantiationException",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (IllegalAccessException e) {
			JOptionPane
					.showMessageDialog(
							new JFrame(),
							"An illegal access exception ocurred.\n"
									+ "This probably means, that access to some libraries is restricted.\n"
									+ e.getMessage(), "Illegal access",
							JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		// /////////////////////////////////////////////
		// Start workbench
		// /////////////////////////////////////////////

		JOptionPane.showMessageDialog(new JFrame(),
				"Please read the README-File before using the program.",
				"Info", JOptionPane.INFORMATION_MESSAGE);
		new Workbench("OCR Workbench");
	}
}
