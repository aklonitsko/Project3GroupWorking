/**
 * 
 */
package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import employment.Employer;
import employment.EmployerCollection;
import student.Student;
import student.StudentCollection;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.UIManager;
import javax.swing.JTextArea;

/**
 * @author Brandon
 *
 */
public class ReportsGUI extends JPanel implements ActionListener, PropertyChangeListener {
	
	private static final long serialVersionUID = 2174454681815171064L;
	private ArrayList<Student> myStudentList = new ArrayList<Student>();
	ReportFilterOptionsGUI filterOptionGUI;
	private static final String newLine = "\r\n";
	private static final String spaceChar = " ";
	
	JPanel reportButtonPanel = new JPanel();
	JTextArea reportDataWindow = new JTextArea(3,5);
	JScrollPane scrollPane = new JScrollPane();
	JButton reportOneButton = new JButton("Report #1");
	JButton reportTwoButton = new JButton("Report #2");
	JButton reportThreeButton = new JButton("Report #3");
	private final JButton saveReportButton = new JButton("Save Report");
	
	public ReportsGUI() {
		setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 255), new Color(192, 192, 192), new Color(0, 0, 255), new Color(192, 192, 192)));

		
		setLayout(new BorderLayout(0, 0));
		
		setUpComponents();
		getReportData();
	}

	private void setUpComponents() {
		
		reportButtonPanel.setBackground(new Color(245, 245, 220));
		reportButtonPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		add(reportButtonPanel, BorderLayout.NORTH);
		reportButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		reportOneButton.addActionListener(this);
		reportButtonPanel.add(reportOneButton);
		
		reportTwoButton.addActionListener(this);
		reportButtonPanel.add(reportTwoButton);
		
		reportThreeButton.addActionListener(this);
		reportButtonPanel.add(reportThreeButton);
		
		add(scrollPane, BorderLayout.CENTER);
		reportDataWindow.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		
		reportDataWindow.setFont(new Font("Times New Roman", Font.BOLD, 14));
		reportDataWindow.setEditable(false);
		reportDataWindow.setText("Report data will appear here");
		scrollPane.setViewportView(reportDataWindow);
		
		saveReportButton.addActionListener(this);
		add(saveReportButton, BorderLayout.SOUTH);
	}
	
	
	private void getReportData() {
		
		myStudentList = StudentCollection.getStudents();
	}
	
	/**
	 * Runs a report that looks at all students and their GPA. Splits them into 5 categories,
	 * 4.0, 3.0-3.9, 2.0-2.9, 1.0-1.9, and <1.0 and gives a percentage of students in those brackets. Additionally,
	 * a transfer student breakdown is available for this report, which displays only transfer students.
	 * This allows the university to evaluate how students are doing. These can be filtered by Program and degree level.
	 */
	private void runAcademicReport() {
		double academicDataArray[] = {};
		String[] tierNames = {"Tier 1 - 4.0 ", "Tier 2 - 3.0 to 3.99 ", "Tier 3 - 2.0 to 2.99 ", "Tier 4 - 1.0 to 1.99 ", "Tier 5 less than 1.0 "};
		double totalStudents = 0;
		double tier1 = 0;
		double tier2 = 0;
		double tier3 = 0;
		double tier4 = 0; 
		double tier5 = 0;
		
		if(ReportFilterOptionsGUI.transferFilter == true) {
			if(ReportFilterOptionsGUI.programFilter == true) {
				
				ArrayList<Student> tempList = myStudentList;
				
				for(Student s : tempList) {
					if(s.getAcademicRecord().isTransfer() == true) {
						tempList.add(s);
					}
				}
				
				for(Student s : tempList) {
					if(s.getAcademicRecord().getGPA() == 4.0) {
						tier1++;
					}
					if(s.getAcademicRecord().getGPA() < 4.0 && s.getAcademicRecord().getGPA() >= 3.0) {
						tier2++;
					}
					if(s.getAcademicRecord().getGPA() < 3.0 && s.getAcademicRecord().getGPA() >= 2.0) {
						tier3++;
					}
					if(s.getAcademicRecord().getGPA() < 2.0 && s.getAcademicRecord().getGPA() >= 1.0) {
						tier4++;
					}
					if(s.getAcademicRecord().getGPA() < 1.0 && s.getAcademicRecord().getGPA() >= 0.0) {
						tier5++;
					}
					
					totalStudents++;
				}
				
				academicDataArray[0] = (tier1 / totalStudents);
				academicDataArray[1] = (tier2 / totalStudents);
				academicDataArray[2] = (tier3 / totalStudents);
				academicDataArray[3] = (tier4 / totalStudents);
				academicDataArray[4] = (tier5 / totalStudents);
				
				reportDataWindow.setText(Double.toString(tier1) + spaceChar);
				reportDataWindow.append(Double.toString(tier2) + spaceChar);
				reportDataWindow.append(Double.toString(tier3) + spaceChar);
				reportDataWindow.append(Double.toString(tier4) + spaceChar);
				reportDataWindow.append(Double.toString(tier5) + spaceChar);
				reportDataWindow.append(newLine + Double.toString(academicDataArray[0]));
				reportDataWindow.append(newLine + Double.toString(academicDataArray[1]));
				reportDataWindow.append(newLine + Double.toString(academicDataArray[2]));
				reportDataWindow.append(newLine + Double.toString(academicDataArray[3]));
				reportDataWindow.append(newLine + Double.toString(academicDataArray[4]));
				
				}
			} else {
				for(Student s : myStudentList) {
					
				}
			} 
		
			if(ReportFilterOptionsGUI.programFilter == true) {
				for(Student s : myStudentList) {
					
				}
			} else {
				for(Student s : myStudentList) {
					
				}
			}
		}
	
	/**
	 * Runs a report on how many GRADUATES currently have jobs versus those who do not and places a percentage of 
	 * students to represent that, as well as a percentage breakdown of the companies students are working for
	 * This can be filtered by program, and degree level.
	 */
	private void runEmploymentReport() {
		if(ReportFilterOptionsGUI.transferFilter == true) {
			if(ReportFilterOptionsGUI.programFilter == true) {
				for(Student s : myStudentList) {
					
				}
			} else {
				for(Student s : myStudentList) {
					
				}
			}
		} else {
			if(ReportFilterOptionsGUI.programFilter == true) {
				for(Student s : myStudentList) {
					
				}
			} else {
				for(Student s : myStudentList) {
					
				}
			}
		}
	}
	
	/**
	 * Runs a report on how many students who have/or had internships currently have jobs
	 * and a percentage breakdown of companies students are working for. This can be filterd by program and degree level.
	 */
	private void runInternReport() {
		if(ReportFilterOptionsGUI.transferFilter == true) {
			if(ReportFilterOptionsGUI.programFilter == true) {
				for(Student s : myStudentList) {
					
				}
			} else {
				for(Student s : myStudentList) {
					
				}
			}
		} else {
			if(ReportFilterOptionsGUI.programFilter == true) {
				for(Student s : myStudentList) {
					
				}
			} else {
				for(Student s : myStudentList) {
					
				}
			}
		}
	}
	
	/**
	 * Saves the report to a file on the local system.
	 */
	private void saveReport() {
		
	}
	
	private void runFilterGUI(String reportType) {
		filterOptionGUI = new ReportFilterOptionsGUI(reportType);
		filterOptionGUI.addPropertyChangeListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent theEvent) {
		
		if(theEvent.getSource() == reportOneButton) {
			
			runFilterGUI("Academic");
			
		} else if(theEvent.getSource() == reportTwoButton) {
			runFilterGUI("Employment");
			
		} else if(theEvent.getSource() == reportThreeButton) { 
			runFilterGUI("Intern");
			
		} else if(theEvent.getSource() == saveReportButton) {
			saveReport();
			
		}	else { //If for some reason the event is not one of my buttons, an error appears.
			JOptionPane.showMessageDialog(null, "Report Failed");
			
		}	
	}

	@Override
	public void propertyChange(PropertyChangeEvent propertyEvent) {
		if (ReportFilterOptionsGUI.reportType == "Academic") {
			runAcademicReport();
		} else if(ReportFilterOptionsGUI.reportType == "Employment") {
			
		} else if(ReportFilterOptionsGUI.reportType == "Intern") {
			
		}
		
	}
}
