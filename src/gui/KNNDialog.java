package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import org.garret.perst.IterableIterator;

import data.PERSTDatabase;
import data.PERSTDatabase.DatabaseElement;

public class KNNDialog extends JDialog implements ActionListener {
	private final JPanel contentPanel = new JPanel();
	private final GraphicsPanel graphicsPanel;
	private final JButton btnConfirmNext;
	private final JLabel lblclassifiedValue;
	private final JLabel lblcorrectValue;
	private final JLabel lblIndexNumber;
	private final PERSTDatabase db_ = PERSTDatabase.getInstance();
	private IterableIterator<DatabaseElement> iter_;
	private DatabaseElement currentElement_;
	private int confirmedCounter_ = 0;
	private final int numOfIncorrectElements_;

	public KNNDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 450, 300);

		iter_ = db_.getNonTrainingdataDatabaseIterator();
		currentElement_ = iter_.next();
		numOfIncorrectElements_ = db_.getNumberOfUncorrectDatabaseElements();

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[164.00][grow]"));

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
		contentPanel.add(panel, "cell 0 1,growx,aligny bottom");
		panel.setLayout(new MigLayout("", "[][][135.00][grow]", "[]"));

		btnConfirmNext = new JButton("Confirm & Next");
		btnConfirmNext.addActionListener(this);
		btnConfirmNext.setActionCommand("Confirm");
		panel.add(btnConfirmNext, "cell 0 0");

		JButton btnEnterCorrectValue = new JButton("Enter correct value");
		btnEnterCorrectValue.addActionListener(this);
		btnEnterCorrectValue.setActionCommand("Edit");
		panel.add(btnEnterCorrectValue, "cell 1 0");

		JLabel lblIndex = new JLabel("Index:");
		panel.add(lblIndex, "cell 2 0,alignx right");

		lblIndexNumber = new JLabel("-/-");
		panel.add(lblIndexNumber, "cell 3 0");

		updateGUIState();
	}

	private void updateGUIState() {
		graphicsPanel.update();
		lblclassifiedValue.setText(String.valueOf((int) currentElement_
				.getAlgoClassification()));
		lblcorrectValue.setText(String.valueOf((int) currentElement_
				.getCorrectClassification()));
		lblIndexNumber.setText((confirmedCounter_ + 1) + "/"
				+ numOfIncorrectElements_);
		if (!iter_.hasNext()) {
			btnConfirmNext.setEnabled(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Confirm":
			currentElement_ = iter_.next();
			confirmedCounter_++;
			updateGUIState();
			break;
		case "Edit":
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