package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import data.PERSTDatabase;
import data.PERSTDatabase.DatabaseElement;

public class ResultDisplayDialog extends JDialog implements ActionListener {

	// TODO Refactor member variables with underscore
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private final GraphicsPanel graphicsPanel;
	private final JButton btnNext;
	private final JButton btnEnterCorrectValue;
	private final JButton btnDone;
	private final JLabel lblclassifiedValue;
	private final JLabel lblcorrectValue;
	private final JLabel lblIndexNumber;
	private final PERSTDatabase db_ = PERSTDatabase.getInstance();
	private Iterator<DatabaseElement> iter_;
	private DatabaseElement currentElement_;
	private int currentlyEnteredClassification = PERSTDatabase.NO_CORRECT_CLASSIFICATION;
	private int confirmedCounter_ = 0;
	private final int numOElements_;
	private static final int MIN_LABEL = 0;
	private static final int MAX_LABEL = 9;

	public ResultDisplayDialog(ArrayList<DatabaseElement> elements) {
		setModal(true);
		setSize(new Dimension(450, 250));
		setLocationRelativeTo(null);
		setTitle("Results");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		iter_ = elements.iterator();
		currentElement_ = iter_.next();
		numOElements_ = elements.size();

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[154.00][grow]"));

		JPanel panel_1 = new JPanel();
		contentPanel.add(panel_1, "cell 0 0,grow");
		panel_1.setLayout(new MigLayout("", "[grow][grow][grow]", "[grow]"));

		JPanel imageBorderPanel = new JPanel();
		imageBorderPanel.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "Image",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(imageBorderPanel, "cell 0 0,grow");
		imageBorderPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));

		graphicsPanel = new GraphicsPanel();
		imageBorderPanel.add(graphicsPanel, "cell 0 0,grow");

		JPanel classifiedValuePanel = new JPanel();
		classifiedValuePanel.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "Classified by k-NN",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panel_1.add(classifiedValuePanel, "cell 1 0,grow");
		classifiedValuePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));

		lblclassifiedValue = new JLabel("-");
		lblclassifiedValue.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblclassifiedValue.setHorizontalAlignment(SwingConstants.CENTER);
		classifiedValuePanel.add(lblclassifiedValue, "cell 0 0,grow");

		JPanel correctValueBorderPanel = new JPanel();
		correctValueBorderPanel.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "Correct value",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(correctValueBorderPanel, "cell 2 0,grow");
		correctValueBorderPanel
				.setLayout(new MigLayout("", "[grow]", "[grow]"));

		lblcorrectValue = new JLabel("-");
		lblcorrectValue.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblcorrectValue.setHorizontalAlignment(SwingConstants.CENTER);
		correctValueBorderPanel.add(lblcorrectValue, "cell 0 0,grow");

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPanel.add(panel, "cell 0 1,growx,aligny bottom");
		panel.setLayout(new MigLayout("", "[][][135.00][116.00][50.00,grow]",
				"[]"));

		btnNext = new JButton("Next");
		btnNext.addActionListener(this);
		btnNext.setActionCommand("next");
		panel.add(btnNext, "cell 0 0");

		JLabel lblIndex = new JLabel("Index:");
		panel.add(lblIndex, "cell 1 0,alignx right");

		lblIndexNumber = new JLabel("-/-");
		panel.add(lblIndexNumber, "cell 2 0");

		btnEnterCorrectValue = new JButton("Enter correct classification");
		btnEnterCorrectValue.addActionListener(this);
		btnEnterCorrectValue.setActionCommand("enterCorrectValue");
		panel.add(btnEnterCorrectValue, "cell 3 0");

		btnDone = new JButton("Done");
		btnDone.addActionListener(this);
		btnDone.setActionCommand("done");
		btnDone.setEnabled(false);
		panel.add(btnDone, "cell 4 0");

		updateGUIState();

		setVisible(true);
	}

	private void updateGUIState() {
		graphicsPanel.update();
		int algoClassification = (int) currentElement_.getAlgoClassification();
		if (algoClassification == PERSTDatabase.NO_ALGORITHM_CLASSIFICATION) {
			lblclassifiedValue.setText("-");
		} else {
			lblclassifiedValue.setText(String.valueOf(algoClassification));
		}
		int correctClassfication = (int) currentElement_
				.getCorrectClassification();
		if (correctClassfication == PERSTDatabase.NO_CORRECT_CLASSIFICATION) {
			lblcorrectValue.setText("-");
			btnEnterCorrectValue.setEnabled(true);

		} else {
			lblcorrectValue.setText(String.valueOf(correctClassfication));
			btnEnterCorrectValue.setEnabled(false);
			currentlyEnteredClassification = correctClassfication;
		}
		lblIndexNumber.setText((confirmedCounter_ + 1) + "/" + numOElements_);
		if (!iter_.hasNext()) {
			btnNext.setEnabled(false);
			btnDone.setEnabled(true);
		}
	}

	private void enterCorrectClassification() {
		String enteredClassification_str = JOptionPane
				.showInputDialog("Enter correct classification");
		if (enteredClassification_str == null) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Please enter a number between 0 and 9.");
			currentlyEnteredClassification = PERSTDatabase.NO_CORRECT_CLASSIFICATION;
			return;
		}
		currentlyEnteredClassification = Integer
				.valueOf(enteredClassification_str);
		if (currentlyEnteredClassification < MIN_LABEL
				|| currentlyEnteredClassification > MAX_LABEL) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Please enter a number between 0 and 9!");
			return;
		}
		lblcorrectValue.setText(enteredClassification_str);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "done":
			if (currentlyEnteredClassification != PERSTDatabase.NO_CORRECT_CLASSIFICATION) {
				db_.convertToCorrect(currentElement_.getIndex(),
						(char) currentlyEnteredClassification);
				System.out.println("convertedToCorrect");
			}
			dispose();
			break;
		case "next":
			if (currentlyEnteredClassification != PERSTDatabase.NO_CORRECT_CLASSIFICATION) {
				db_.convertToCorrect(currentElement_.getIndex(),
						(char) currentlyEnteredClassification);
				System.out.println("convertedToCorrect");
			}
			currentElement_ = iter_.next();
			confirmedCounter_++;
			currentlyEnteredClassification = PERSTDatabase.NO_CORRECT_CLASSIFICATION;
			updateGUIState();
			break;
		case "enterCorrectValue":
			enterCorrectClassification();
			break;
		}
	}

	class GraphicsPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		public GraphicsPanel() {
			setBackground(Color.WHITE);
		}

		public void update() {
			repaint();
		}

		private void paintNumber(Graphics g) {
			char[] pixels = currentElement_.getPixels();

			BufferedImage image = new BufferedImage(28, 28,
					BufferedImage.TYPE_BYTE_GRAY);
			int i = 0;
			for (int x = 0; x < 28; x++) {
				for (int y = 0; y < 28; y++) {
					WritableRaster raster = image.getRaster();
					raster.setSample(y, x, 0, 255 - pixels[i]);
					i++;
				}
			}
			BufferedImage scaledImage = new BufferedImage(145, 200,
					BufferedImage.TYPE_BYTE_GRAY);
			AffineTransform transform = new AffineTransform();
			transform.scale(4.1, 4.1);
			AffineTransformOp scaleOp = new AffineTransformOp(transform,
					AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			scaledImage = scaleOp.filter(image, scaledImage);

			g.drawImage(scaledImage, 0, 0, null);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			paintNumber(g);
		}
	}
}
