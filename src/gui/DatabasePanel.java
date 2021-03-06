package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;
import data.PERSTDatabase;
import data.PERSTDatabase.DatabaseElement;

/*
 * This panel acts as a data viewer for the database.
 * Database elements without a known classification can be classified by the user using this panel.
 */
public class DatabasePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private GraphicsPanel graphicsPanel;
	private final JButton btnEnterClassification;
	// Some GUI elements have to be members because they are being referenced by
	// some actions
	private JLabel lblClassification;
	private JLabel lblDatacounter;
	private JSpinner spnIndex;
	private PERSTDatabase db_;
	// Stores the current total number of database elements
	private int numOfDatabaseElements_;
	// Stores the database index of the current element to be displayed
	private int currentIndex;

	public DatabasePanel() {
		db_ = PERSTDatabase.getInstance();
		setLayout(new MigLayout("", "[332.00,grow]", "[335.00,grow]"));

		// Update the total number of database-elements and always show the
		// first element after switching to this panel
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				if (db_.getNumberOfDatabaseElements() > 0) {
					updateDatabaseState();
					graphicsPanel.update(1); // Reset displayed number to first
												// index
					if (db_.getDatabaseElement(1).getCorrectClassification() != PERSTDatabase.NO_CORRECT_CLASSIFICATION) {
						btnEnterClassification.setEnabled(false);
					} else {
						btnEnterClassification.setEnabled(true);
					}
				}
			}
		});

		// /////////////////////////////////////////////
		// Database element counter
		// /////////////////////////////////////////////

		JPanel panelTop = new JPanel();
		panelTop.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"Controls"));
		add(panelTop, "cell 0 0,grow");
		panelTop.setLayout(new MigLayout("", "[184.00,grow][grow]",
				"[][][][grow]"));

		JLabel lblNumberOfDataelements = new JLabel(
				"Number of data elements in database:");
		panelTop.add(lblNumberOfDataelements, "cell 0 0");

		lblDatacounter = new JLabel("-");
		panelTop.add(lblDatacounter, "cell 1 0,alignx center");

		JSeparator separator = new JSeparator();
		panelTop.add(separator, "cell 0 1 2 1,growx");

		// /////////////////////////////////////////////
		// Index number spinner
		// /////////////////////////////////////////////

		JPanel indexChooserPanel = new JPanel();
		indexChooserPanel.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "Display data",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTop.add(indexChooserPanel, "cell 0 2 2 1,grow");
		indexChooserPanel.setLayout(new MigLayout("", "[][grow]", "[]"));

		JLabel lblIndex = new JLabel("Index:");
		indexChooserPanel.add(lblIndex, "cell 0 0");

		spnIndex = new JSpinner();
		if (numOfDatabaseElements_ <= 0) {
			spnIndex.setModel(new SpinnerNumberModel(0, 0, 0, 0));
			spnIndex.setEnabled(false);
		} else {
			spnIndex.setModel(new SpinnerNumberModel(1, 1,
					numOfDatabaseElements_, 1));
		}
		spnIndex.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				currentIndex = (int) spnIndex.getValue();
				graphicsPanel.update(currentIndex);
				updateClassificationLabel(currentIndex);
			}
		});
		indexChooserPanel.add(spnIndex, "cell 1 0,growx");

		// /////////////////////////////////////////////
		// Element display panel
		// /////////////////////////////////////////////

		JPanel panelBottom = new JPanel();
		panelTop.add(panelBottom, "cell 0 3 2 1,grow");
		panelBottom
				.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelBottom.setLayout(new MigLayout("", "[grow][152.00][grow]",
				"[167.00][][]"));

		JPanel panelImageOuter = new JPanel();
		panelBottom.add(panelImageOuter, "cell 1 0,growx,aligny top");
		panelImageOuter.setLayout(new MigLayout("", "[140px,grow]", "[150px]"));

		JPanel panelImageInner = new JPanel();
		panelImageInner.setBorder(new TitledBorder(new BevelBorder(
				BevelBorder.LOWERED, null, null, null, null), "Image",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panelImageOuter.add(panelImageInner, "cell 0 0,grow");
		panelImageInner.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JPanel panelClassificationOuter = new JPanel();
		panelBottom.add(panelClassificationOuter, "cell 1 1,growx,aligny top");
		panelClassificationOuter.setLayout(new MigLayout("", "[150px,grow]",
				"[150px]"));

		JPanel panelClassificationInner = new JPanel();
		panelClassificationInner.setForeground(Color.BLACK);
		panelClassificationInner.setBorder(new TitledBorder(new BevelBorder(
				BevelBorder.LOWERED, null, null, null, null), "Classification",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panelClassificationOuter.add(panelClassificationInner, "cell 0 0,grow");
		panelClassificationInner
				.setLayout(new MigLayout("", "[grow]", "[grow]"));

		lblClassification = new JLabel("-");
		lblClassification.setForeground(Color.BLACK);
		lblClassification.setFont(new Font("Comic Sans MS", Font.BOLD, 72));
		panelClassificationInner.add(lblClassification,
				"cell 0 0,alignx center,aligny center");

		graphicsPanel = new GraphicsPanel();
		graphicsPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelImageInner.add(graphicsPanel, "cell 0 0,grow");

		btnEnterClassification = new JButton("Enter classification");
		btnEnterClassification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enterCorrectClassification();
			}
		});
		btnEnterClassification.setActionCommand("");
		btnEnterClassification.setEnabled(false);
		panelBottom.add(btnEnterClassification, "cell 1 2,growx");
	}

	// Display the classification corresponding to the currently chosen database
	// element
	private void updateClassificationLabel(int index) {
		int classification = db_.getDatabaseElement(index)
				.getCorrectClassification();
		if (classification == PERSTDatabase.NO_CORRECT_CLASSIFICATION) {
			lblClassification.setText("-");
			btnEnterClassification.setEnabled(true);
		} else {
			lblClassification.setText(String.valueOf(classification));
			btnEnterClassification.setEnabled(false);
		}
	}

	// Update the GUI based on the number of objects in the database
	private void updateDatabaseState() {
		numOfDatabaseElements_ = db_.getNumberOfDatabaseElements();
		lblDatacounter.setText(String.valueOf(numOfDatabaseElements_));
		if (numOfDatabaseElements_ > 0) {
			spnIndex.setModel(new SpinnerNumberModel(1, 1,
					numOfDatabaseElements_, 1));
			spnIndex.setEnabled(true);
			updateClassificationLabel(1);
		}
	}

	// Manually enter the classification for a database element
	private void enterCorrectClassification() {
		String enteredClassification_str = JOptionPane
				.showInputDialog("Enter correct classification");
		if (enteredClassification_str == null
				|| enteredClassification_str.isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Please enter a number between 0 and 9.");
			return;
		}
		try {
			int enteredClassfication = Integer
					.valueOf(enteredClassification_str);
			if (enteredClassfication < 0 || enteredClassfication > 9) {
				JOptionPane.showMessageDialog(new JFrame(),
						"Please enter a number between 0 and 9!");
				return;
			}
			db_.convertToCorrect(currentIndex, (char) enteredClassfication);
		} catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Please enter a valid number!", "Wrong number format",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		lblClassification.setText(enteredClassification_str);
	}

	// Panel for drawing the current element
	class GraphicsPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private int index_;

		public GraphicsPanel() {
			setBackground(Color.WHITE);
			index_ = 1;
		}

		public void update(int index) {
			index_ = index;
			repaint();
		}

		private void paintNumber(Graphics g) {
			DatabaseElement e = db_.getDatabaseElement(index_);
			if (e == null) {
				return;
			}
			char[] pixels = e.getPixels();

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

			// Scale the current element to fit in the panel properly
			BufferedImage scaledImage = new BufferedImage(145, 145,
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
