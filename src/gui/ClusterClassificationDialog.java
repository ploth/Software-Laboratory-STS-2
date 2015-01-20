package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import data.PERSTDatabase;

public class ClusterClassificationDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 250;
	private static final int HEIGHT = 250;
	private final int dim = PERSTDatabase.getInstance().getDim();
	private final GraphicsPanel graphicsPanel;
	private double[] currentCluster = new double[dim * dim];
	private int currentClassification = PERSTDatabase.NO_CORRECT_CLASSIFICATION;
	private int index = 0;

	public ClusterClassificationDialog(final double[][] clusterMeans,
			final char[] clusterClassifications) {
		currentCluster = clusterMeans[index];
		setModal(true);
		setSize(new Dimension(WIDTH, HEIGHT));
		setLocationRelativeTo(null);
		setTitle("Classify Clusters");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(
				new MigLayout("", "[grow][150][grow]", "[150][grow]"));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED,
				null, null), "Cluster mean", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		getContentPane().add(panel, "cell 1 0,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[grow]"));

		graphicsPanel = new GraphicsPanel();
		panel.add(graphicsPanel, "cell 0 0,grow");

		final JButton btnEnterValue = new JButton("Enter value & display next");
		btnEnterValue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String class_str = null;
				while (class_str.isEmpty()) {
					class_str = JOptionPane.showInputDialog(new JFrame(),
							"Enter the cluster's class:");
					if (class_str.isEmpty()) {
						JOptionPane.showMessageDialog(new JFrame(),
								"You have to enter a class!");
					}
				}
				currentClassification = (int) Integer.valueOf(class_str);
				if (currentClassification < 0 || currentClassification > 9) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Please enter a valid value!");
					return;
				}
				clusterClassifications[index] = (char) currentClassification;
				index++;
				if (index >= clusterMeans.length) {
					dispose();
					return;
				}
				currentCluster = clusterMeans[index];
				graphicsPanel.update();
			}
		});
		getContentPane().add(btnEnterValue, "cell 1 1,growx");

		setVisible(true);
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
			double[] pixels = currentCluster;

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
			// TODO Scale numbers properly for this window
			BufferedImage scaledImage = new BufferedImage(145, 145,
					BufferedImage.TYPE_BYTE_GRAY);
			AffineTransform transform = new AffineTransform();
			transform.scale(4.1, 3.9);
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
