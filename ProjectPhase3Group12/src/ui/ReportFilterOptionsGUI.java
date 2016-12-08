package ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

import javax.swing.JRadioButton;

public class ReportFilterOptionsGUI extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 740505097732800940L;
	PropertyChangeSupport propertyChangeObj = new PropertyChangeSupport(this);
	public static String reportType;
	
	JFrame outerFrame = new JFrame();
	JPanel selectPanel = new JPanel();
	JPanel TransferPanel = new JPanel();
	JPanel radioButtonPanel = new JPanel();
	
	ButtonGroup filterGroup = new ButtonGroup();
	JRadioButton programRadioButton = new JRadioButton("Program");
	JRadioButton degreeLevelRadioButton = new JRadioButton("Degree Level");
	JLabel lblPleaseSelectFrom = new JLabel("    Please select from the following filters: ");
	JCheckBox transferCheckBox = new JCheckBox("Transfer Report");
	JButton btnOk = new JButton("OK");
	JButton btnCancel = new JButton("Cancel");
	
	public static boolean transferFilter = false;
	public static boolean degreeLevelFilter = false;
	public static boolean programFilter = false;
	
	public ReportFilterOptionsGUI(String theReportType) {
		reportType = theReportType;
		setLayout(new BorderLayout(0, 0));
		
		lblPleaseSelectFrom.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lblPleaseSelectFrom, BorderLayout.NORTH);
		
		add(selectPanel, BorderLayout.SOUTH);
		selectPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		selectPanel.add(btnOk);
		selectPanel.add(btnCancel);
		
		add(TransferPanel, BorderLayout.WEST);
		TransferPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		TransferPanel.add(transferCheckBox);
		add(radioButtonPanel, BorderLayout.EAST);
		
		filterGroup.add(programRadioButton);
		filterGroup.add(degreeLevelRadioButton);
		radioButtonPanel.setLayout(new GridLayout(0, 2, 0, 0));
		radioButtonPanel.add(programRadioButton);
		radioButtonPanel.add(degreeLevelRadioButton);
		
		outerFrame.add(this);
		outerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		outerFrame.pack();
		outerFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent theEvent) {
		if(theEvent.getSource() == btnOk) {
			transferFilter = transferCheckBox.isSelected();
			degreeLevelFilter = degreeLevelRadioButton.isSelected();
			programFilter = programRadioButton.isSelected();
			
			propertyChangeObj.firePropertyChange("selected", "Null", this);
			
		} else if(theEvent.getSource() == btnCancel) {
			outerFrame.dispose();
		}
		
	}

}
