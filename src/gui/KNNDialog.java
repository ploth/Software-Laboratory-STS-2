package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import data.PERSTDatabase;
import data.PERSTDatabase.DatabaseElement;
import net.miginfocom.swing.MigLayout;

public class KNNDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final PERSTDatabase db_ = PERSTDatabase.getInstance();

	public KNNDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[]", "[]"));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
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
			if(e == null) {
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
			BufferedImage scaledImage = new BufferedImage(145, 145, BufferedImage.TYPE_BYTE_GRAY);
			AffineTransform transform = new AffineTransform();
			transform.scale(4.1, 4.1);
			AffineTransformOp scaleOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
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
