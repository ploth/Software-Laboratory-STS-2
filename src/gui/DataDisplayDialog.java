package gui;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import org.garret.perst.IterableIterator;

import data.PERSTDatabase;
import data.PERSTDatabase.DatabaseElement;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class DataDisplayDialog extends JDialog {
	
	private static final String TITLE = "Display training data";
	private static final int HEIGHT = 400;
	private static final int WIDTH = 600;
	
	private GraphicsPanel graphicsPanel;
	
	public DataDisplayDialog() {
		setResizable(false);
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null); // Set initial window position to center of
												// screen
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "[107.00,fill][grow]", "[][grow][]"));
		
		JLabel lblIndex = new JLabel("Index");
		getContentPane().add(lblIndex, "cell 0 0");
		
		final JSpinner spinner = new JSpinner();
		spinner.setValue(1);
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				graphicsPanel.update( (int) spinner.getValue());
			}
		});
		getContentPane().add(spinner, "flowx,cell 0 1,aligny top");
		
		graphicsPanel = new GraphicsPanel();
		getContentPane().add(graphicsPanel, "cell 1 1,grow");
		
		setVisible(true);
	}
	
	class GraphicsPanel extends JPanel {
		private int index_;
		
		public GraphicsPanel() {
			setSize(new Dimension(120, 120));
			setBackground(Color.WHITE);
			setBorder(BorderFactory.createLineBorder(Color.black));
			index_ = 1;
		}
		
		public void update(int index) {
			index_ = index;
			repaint();
		}
		
		private void paintNumber(Graphics g) {
			PERSTDatabase db = PERSTDatabase.getInstance();
			DatabaseElement e = db.getDatabaseElement(index_);
			//int label = e.getClassification();
			char[] pixels = e.getPixels();
			
			BufferedImage image = new BufferedImage(28, 28,
					BufferedImage.TYPE_BYTE_GRAY); // TYPE_BYTE_GRAY
			int i = 0;
			for (int x = 0; x < 28; x++) {
				for (int y = 0; y < 28; y++) {
					WritableRaster raster = image.getRaster();
					raster.setSample(y, x, 0, 255 - pixels[i]);
					i++;
				}
			}
			
			g.drawImage(image, 0, 0, null);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			paintNumber(g);
		}
	}
}
