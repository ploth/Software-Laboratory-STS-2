package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;

public class StatisticsDialog extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 450;
	private static final int HEIGHT = 300;
	public StatisticsDialog(String classificatorName,
			String distanceMethodName, int numTotalTestObjects,
			int numTestObjectsPerClass, int numTrainingElements,
			int numTrainingElements_class, int numWrongClassifications) {
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setModal(true);
		setTitle("Algorithm test run statistics");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "[grow][]", "[grow][]"));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Results", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panel, "cell 0 0 2 1,grow");
		panel.setLayout(new MigLayout("", "[grow][]", "[][][][][][][][][][]"));
		
		JLabel lblClassificatorAlgorithm = new JLabel("Classificator algorithm:");
		panel.add(lblClassificatorAlgorithm, "cell 0 0");
		
		JLabel lblClassificatorname = new JLabel(classificatorName);
		panel.add(lblClassificatorname, "cell 1 0");
		
		JLabel lblDistanceMeasurementMethod = new JLabel("Distance measurement method:");
		panel.add(lblDistanceMeasurementMethod, "cell 0 1");
		
		JLabel lblDistanceMethodName = new JLabel(distanceMethodName);
		panel.add(lblDistanceMethodName, "cell 1 1");
		
		JLabel lblNumberOfTotal = new JLabel("Number of total test objects:");
		panel.add(lblNumberOfTotal, "cell 0 3");
		
		JLabel lblNumTotalTestObjects = new JLabel(String.valueOf(numTotalTestObjects));
		panel.add(lblNumTotalTestObjects, "cell 1 3");
		
		JLabel lblNumberOfTest = new JLabel("Number of test objects per class:");
		panel.add(lblNumberOfTest, "cell 0 4");
		
		JLabel lblNumTotalTestObjects_class = new JLabel(String.valueOf(numTestObjectsPerClass));
		panel.add(lblNumTotalTestObjects_class, "cell 1 4");
		
		JLabel lblTotalNumberOf = new JLabel("Total number of training data elements:");
		panel.add(lblTotalNumberOf, "cell 0 6");
		
		JLabel lblNumtrainingelements = new JLabel(String.valueOf(numTrainingElements));
		panel.add(lblNumtrainingelements, "cell 1 6");
		
		JLabel lblTrainingsDataPer = new JLabel("Number of training data elements per class:");
		panel.add(lblTrainingsDataPer, "cell 0 7");
		
		JLabel lblNumtrainingelementsclass = new JLabel(String.valueOf(numTrainingElements_class));
		panel.add(lblNumtrainingelementsclass, "cell 1 7");
		
		JLabel lblNumberOfWrong = new JLabel("Number of wrong classifications:");
		panel.add(lblNumberOfWrong, "cell 0 9");
		
		JLabel lblNumwrongclassifications = new JLabel(String.valueOf(numWrongClassifications));
		panel.add(lblNumwrongclassifications, "cell 1 9");
		
		JButton btnDisplayWrongClassified = new JButton("Display wrong classified elements");
		btnDisplayWrongClassified.addActionListener(this);
		btnDisplayWrongClassified.setActionCommand("displayWrongElements");
		getContentPane().add(btnDisplayWrongClassified, "cell 0 1,alignx right");
		
		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(this);
		btnDone.setActionCommand("done");
		getContentPane().add(btnDone, "cell 1 1,alignx right");
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "displayWrongElements":
			//TODO Write code for displaying the wrong elements
			break;
		case "done":
			dispose();
			break;
		}
		
	}

}
