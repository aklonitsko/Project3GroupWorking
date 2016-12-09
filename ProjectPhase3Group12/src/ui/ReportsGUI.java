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
	
	private static final String newLine = "\r\n";
	private static final String spaceChar = " ";
	
	private ArrayList<Student> myStudentList = new ArrayList<Student>();
	private String[] degreePrograms = {"CSS", "CES", "IT"};
	private String[] degreeLevels = {"BA", "BS", "MS"};
	private boolean transferSelected = false;
	private boolean degreeLevelSelected = false;
	private boolean programSelected = false;
	
	ReportFilterOptionsGUI filterOptionGUI;
	JPanel reportButtonPanel = new JPanel();
	JTextArea reportDataWindow = new JTextArea();
	//JScrollPane scrollPane = new JScrollPane();
	JButton academicReportButton = new JButton("Academic Report");
	JButton employmentReportButton = new JButton("Employment Report");
	JButton saveButton = new JButton("Save Report");
	
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
		
		academicReportButton.addActionListener(this);
		reportButtonPanel.add(academicReportButton);
		
		employmentReportButton.addActionListener(this);
		reportButtonPanel.add(employmentReportButton);
		
		saveButton.addActionListener(this);
		reportButtonPanel.add(saveButton);
		
		//add(scrollPane, BorderLayout.CENTER);
		reportDataWindow.setEditable(false);
		reportDataWindow.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		
		reportDataWindow.setFont(new Font("Times New Roman", Font.BOLD, 14));
		reportDataWindow.setText("Report data will appear here");
		reportDataWindow.setSize(1000, 1000);
		add(reportDataWindow, BorderLayout.CENTER);
		//scrollPane.setViewportView(reportDataWindow);
		//scrollPane.setSize(300, 300);
	}
	
	
	private void getReportData() {
		
		myStudentList = StudentCollection.getStudents();
	}
	
	/**
	 * Runs a report that looks at all students and their GPA. Splits them into 5 categories,
	 * 4.0, 3.0-3.9, 2.0-2.9, 1.0-1.9, and <1.0 and gives a percentage of students in those brackets. Additionally,
	 * a transfer student breakdown is available for this report, which displays only transfer students.
	 * This allows the university to evaluate how students are doing.
	 */
	private void runAcademicReport() {
		reportDataWindow.setText("");
		
		double academicDataArray[] = {};
		String[] tierNames = {"Tier 1 - 4.0 ", "Tier 2 - 3.0 to 3.99 ", "Tier 3 - 2.0 to 2.99 ", "Tier 4 - 1.0 to 1.99 ", "Tier 5 less than 1.0 "};
		double totalStudents = 0;
		double[] tierCount = {0, 0, 0, 0, 0};
		
		if(transferSelected == true) {
				
			ArrayList<Student> tempList = myStudentList;
			
			for(Student s : tempList) {
				if(s.getAcademicRecord().isTransfer() == true) {
						tempList.add(s);
				}
			}
			
			for(Student s : tempList) {
				if(s.getAcademicRecord().getGPA() == 4.0) {
					tierCount[0]++;
				}
				if(s.getAcademicRecord().getGPA() < 4.0 && s.getAcademicRecord().getGPA() >= 3.0) {
					tierCount[1]++;
				}
				if(s.getAcademicRecord().getGPA() < 3.0 && s.getAcademicRecord().getGPA() >= 2.0) {
					tierCount[2]++;
				}
				if(s.getAcademicRecord().getGPA() < 2.0 && s.getAcademicRecord().getGPA() >= 1.0) {
					tierCount[3]++;
				}
				if(s.getAcademicRecord().getGPA() < 1.0 && s.getAcademicRecord().getGPA() >= 0.0) {
					tierCount[4]++;
				}
				
				totalStudents++;
			}
				
			academicDataArray[0] = (tierCount[0] / totalStudents);
			academicDataArray[1] = (tierCount[1] / totalStudents);
			academicDataArray[2] = (tierCount[2] / totalStudents);
			academicDataArray[3] = (tierCount[3] / totalStudents);
			academicDataArray[4] = (tierCount[4] / totalStudents);
			
			for(String s : tierNames) {
				reportDataWindow.append(s);
				reportDataWindow.append(spaceChar);
			}
			
			reportDataWindow.append(newLine);
			for (int ndx2 = 0; ndx2 < tierCount.length; ndx2++) {
				reportDataWindow.append(Double.toString(tierCount[ndx2]));
				reportDataWindow.append(spaceChar);
			}
			
			reportDataWindow.append(newLine);
			for(int ndx = 0; ndx < academicDataArray.length; ndx++) {
				reportDataWindow.append(Double.toString(academicDataArray[ndx]));
				reportDataWindow.append(spaceChar);
			}	
			
			reportDataWindow.append(newLine);
			reportDataWindow.append(Double.toString(totalStudents));
		
		} else {
			
			for(Student s : myStudentList) {
					
				if(s.getAcademicRecord().getGPA() == 4.0) {
					tierCount[0]++;
				}
				if(s.getAcademicRecord().getGPA() < 4.0 && s.getAcademicRecord().getGPA() >= 3.0) {
					tierCount[1]++;
				}
				if(s.getAcademicRecord().getGPA() < 3.0 && s.getAcademicRecord().getGPA() >= 2.0) {
					tierCount[2]++;
				}
				if(s.getAcademicRecord().getGPA() < 2.0 && s.getAcademicRecord().getGPA() >= 1.0) {
					tierCount[3]++;
				}
				if(s.getAcademicRecord().getGPA() < 1.0 && s.getAcademicRecord().getGPA() >= 0.0) {
					tierCount[4]++;
				}
				
				totalStudents++;
			}
				
			academicDataArray[0] = (tierCount[0] / totalStudents);
			academicDataArray[1] = (tierCount[1] / totalStudents);
			academicDataArray[2] = (tierCount[2] / totalStudents);
			academicDataArray[3] = (tierCount[3] / totalStudents);
			academicDataArray[4] = (tierCount[4] / totalStudents);
			
			for(String s : tierNames) {
				reportDataWindow.append(s);
				reportDataWindow.append(spaceChar);
			}
				
			reportDataWindow.append(newLine);
			for (int ndx2 = 0; ndx2 < tierCount.length; ndx2++) {
				reportDataWindow.append(Double.toString(tierCount[ndx2]));
				reportDataWindow.append(spaceChar);
			}
				
			reportDataWindow.append(newLine);
			for(int ndx = 0; ndx < academicDataArray.length; ndx++) {
				reportDataWindow.append(Double.toString(academicDataArray[ndx]));
				reportDataWindow.append(spaceChar);
			}	
				
			reportDataWindow.append(newLine);
			reportDataWindow.append(Double.toString(totalStudents));
		} 
	}
	
	/**
	 * Runs a report on how many GRADUATES currently have jobs versus those who do not and places a percentage of 
	 * students to represent that, as well as a percentage breakdown of the companies students are working for
	 * This can be filtered by program, and degree level.
	 */
	private void runEmploymentReport() {
		
		reportDataWindow.setText("");
		ArrayList<Student> employedStudents = new ArrayList<Student>();
		ArrayList<Student> unemployedStudents = new ArrayList<Student>();
		ArrayList<Student> BAStudents = new ArrayList<Student>();
		ArrayList<Student> BSStudents = new ArrayList<Student>();
		ArrayList<Student> MSStudents = new ArrayList<Student>();
		ArrayList<Student> BAUnEmpStudents = new ArrayList<Student>();
		ArrayList<Student> BSUnEmpStudents = new ArrayList<Student>();
		ArrayList<Student> MSUnEmpStudents = new ArrayList<Student>();
		ArrayList<Student> CSSStudents = new ArrayList<Student>();
		ArrayList<Student> CESStudents = new ArrayList<Student>();
		ArrayList<Student> ITStudents = new ArrayList<Student>();
		ArrayList<Student> CSSUnEmpStudents = new ArrayList<Student>();
		ArrayList<Student> CESUnEmpStudents = new ArrayList<Student>();
		ArrayList<Student> ITUnEmpStudents = new ArrayList<Student>();
		
		for(Student s : myStudentList) {
			if(s.getEmployers() == null) {
				unemployedStudents.add(s);
				
			} else {
				employedStudents.add(s);
			}
		}
		
		if(transferSelected == true) {
			if(degreeLevelSelected == true) {
				for(Student s : employedStudents) {
					if(s.getAcademicRecord().isTransfer() == false) {
						employedStudents.remove(s);
						continue;
					}
					
					if(s.getAcademicRecord().getProgram() == "BS") {
						BSStudents.add(s);
					}
					if(s.getAcademicRecord().getProgram() == "BA") {
						BAStudents.add(s);
					}
					if(s.getAcademicRecord().getProgram() == "MS") {
						MSStudents.add(s);
					}
				}
				
				for(Student s : unemployedStudents) {
					if(s.getAcademicRecord().isTransfer() == false) {
						unemployedStudents.remove(s);
						continue;
					}
					
					if(s.getAcademicRecord().getProgram() == "BS") {
						BSUnEmpStudents.add(s);
					}
					if(s.getAcademicRecord().getProgram() == "BA") {
						BAUnEmpStudents.add(s);
					}
					if(s.getAcademicRecord().getProgram() == "MS") {
						MSUnEmpStudents.add(s);
					}
				}
				
				reportDataWindow.append("Employed Students Total: " + Integer.toString(employedStudents.size()));
				reportDataWindow.append("Unemployed Students Total: " + Integer.toString(unemployedStudents.size()));
				
				for (String s : degreeLevels) {
					reportDataWindow.append(s);
					reportDataWindow.append(spaceChar);
				}
				
				reportDataWindow.append(newLine + "Employed students by degree level: \r\n");
				reportDataWindow.append("CSS: ");
				reportDataWindow.append(Integer.toString(BSStudents.size()) + newLine);
				reportDataWindow.append("CES: ");
				reportDataWindow.append(Integer.toString(BAStudents.size()) + newLine);
				reportDataWindow.append("IT: ");
				reportDataWindow.append(Integer.toString(MSStudents.size()) + newLine);
				
				reportDataWindow.append(newLine + "Unemployed students by degree level: \r\n");
				reportDataWindow.append("CSS: ");
				reportDataWindow.append(Integer.toString(CSSUnEmpStudents.size()) + newLine);
				reportDataWindow.append("CES: ");
				reportDataWindow.append(Integer.toString(CESUnEmpStudents.size()) + newLine);
				reportDataWindow.append("IT: ");
				reportDataWindow.append(Integer.toString(ITUnEmpStudents.size()) + newLine);
				
			} else if (programSelected == true) {
				
				for(Student s : employedStudents) {
					
					if(s.getAcademicRecord().getProgram() == "CSS") {
						CSSStudents.add(s);
					}
					if(s.getAcademicRecord().getProgram() == "CES") {
						CESStudents.add(s);
					}
					if(s.getAcademicRecord().getProgram() == "IT") {
						ITStudents.add(s);
					}
				}
				
				for(Student s : unemployedStudents) {
					
					if(s.getAcademicRecord().getProgram() == "CSS") {
						CSSUnEmpStudents.add(s);
					}
					if(s.getAcademicRecord().getProgram() == "CES") {
						CESUnEmpStudents.add(s);
					}
					if(s.getAcademicRecord().getProgram() == "IT") {
						ITUnEmpStudents.add(s);
					}
				}
				
				reportDataWindow.setText("Employed Students Total: " + Integer.toString(employedStudents.size()));
				reportDataWindow.append("Unemployed Students Total: " + Integer.toString(unemployedStudents.size()));
				
				for (String s : degreePrograms) {
					reportDataWindow.append(s);
					reportDataWindow.append(spaceChar);
				}
				
				reportDataWindow.append(newLine + "Employed students by program: \r\n");
				reportDataWindow.append("CSS: ");
				reportDataWindow.append(Integer.toString(CSSStudents.size()) + newLine);
				reportDataWindow.append("CES: ");
				reportDataWindow.append(Integer.toString(CESStudents.size()) + newLine);
				reportDataWindow.append("IT: ");
				reportDataWindow.append(Integer.toString(ITStudents.size()) + newLine);
				
				reportDataWindow.append(newLine + "Unemployed students by program: \r\n");
				reportDataWindow.append("CSS: ");
				reportDataWindow.append(Integer.toString(CSSUnEmpStudents.size()) + newLine);
				reportDataWindow.append("CES: ");
				reportDataWindow.append(Integer.toString(CESUnEmpStudents.size()) + newLine);
				reportDataWindow.append("IT: ");
				reportDataWindow.append(Integer.toString(ITUnEmpStudents.size()) + newLine);
				
			}
		} else {
			
			if(degreeLevelSelected == true) {
				for(Student s : employedStudents) {
					if(s.getAcademicRecord().getProgram() == "BS") {
						BSStudents.add(s);
					}
					if(s.getAcademicRecord().getProgram() == "BA") {
						BAStudents.add(s);
					}
					if(s.getAcademicRecord().getProgram() == "MS") {
						MSStudents.add(s);
					}
				}
				
				for(Student s : unemployedStudents) {
					if(s.getAcademicRecord().getProgram() == "BS") {
						BSUnEmpStudents.add(s);
					}
					if(s.getAcademicRecord().getProgram() == "BA") {
						BAUnEmpStudents.add(s);
					}
					if(s.getAcademicRecord().getProgram() == "MS") {
						MSUnEmpStudents.add(s);
					}
				}
				
				reportDataWindow.append("Employed Students Total: " + Integer.toString(employedStudents.size()));
				reportDataWindow.append("Unemployed Students Total: " + Integer.toString(unemployedStudents.size()));
				
				for (String s : degreeLevels) {
					reportDataWindow.append(s);
					reportDataWindow.append(spaceChar);
				}
				
				reportDataWindow.append(newLine + "Employed students by degree level: \r\n");
				reportDataWindow.append("CSS: ");
				reportDataWindow.append(Integer.toString(BSStudents.size()) + newLine);
				reportDataWindow.append("CES: ");
				reportDataWindow.append(Integer.toString(BAStudents.size()) + newLine);
				reportDataWindow.append("IT: ");
				reportDataWindow.append(Integer.toString(MSStudents.size()) + newLine);
				
				reportDataWindow.append(newLine + "Unemployed students by degree level: \r\n");
				reportDataWindow.append("CSS: ");
				reportDataWindow.append(Integer.toString(CSSUnEmpStudents.size()) + newLine);
				reportDataWindow.append("CES: ");
				reportDataWindow.append(Integer.toString(CESUnEmpStudents.size()) + newLine);
				reportDataWindow.append("IT: ");
				reportDataWindow.append(Integer.toString(ITUnEmpStudents.size()) + newLine);
				
			} else if(programSelected == true) {
				
				for(Student s : employedStudents) {
					
					if(s.getAcademicRecord().getProgram() == "CSS") {
						CSSStudents.add(s);
					}
					if(s.getAcademicRecord().getProgram() == "CES") {
						CESStudents.add(s);
					}
					if(s.getAcademicRecord().getProgram() == "IT") {
						ITStudents.add(s);
					}
				}
				
				for(Student s : unemployedStudents) {
					
					if(s.getAcademicRecord().getProgram() == "CSS") {
						CSSUnEmpStudents.add(s);
					}
					if(s.getAcademicRecord().getProgram() == "CES") {
						CESUnEmpStudents.add(s);
					}
					if(s.getAcademicRecord().getProgram() == "IT") {
						ITUnEmpStudents.add(s);
					}
				}
				
				reportDataWindow.append("Employed Students Total: " + Integer.toString(employedStudents.size()));
				reportDataWindow.append("Unemployed Students Total: " + Integer.toString(unemployedStudents.size()));
				
				for (String s : degreePrograms) {
					reportDataWindow.append(s);
					reportDataWindow.append(spaceChar);
				}
				
				reportDataWindow.append(newLine + "Employed students by program: \r\n");
				reportDataWindow.append("CSS: ");
				reportDataWindow.append(Integer.toString(CSSStudents.size()) + newLine);
				reportDataWindow.append("CES: ");
				reportDataWindow.append(Integer.toString(CESStudents.size()) + newLine);
				reportDataWindow.append("IT: ");
				reportDataWindow.append(Integer.toString(ITStudents.size()) + newLine);
				
				reportDataWindow.append(newLine + "Unemployed students by program: \r\n");
				reportDataWindow.append("CSS: ");
				reportDataWindow.append(Integer.toString(CSSUnEmpStudents.size()) + newLine);
				reportDataWindow.append("CES: ");
				reportDataWindow.append(Integer.toString(CESUnEmpStudents.size()) + newLine);
				reportDataWindow.append("IT: ");
				reportDataWindow.append(Integer.toString(ITUnEmpStudents.size()) + newLine);
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
		
		if(theEvent.getSource() == academicReportButton) {
			
			runFilterGUI("Academic");
			
		} else if(theEvent.getSource() == employmentReportButton) {
			runFilterGUI("Employment");
			
		}  else if(theEvent.getSource() == saveButton) {
			saveReport();
			
		}	else { //If for some reason the event is not one of my buttons, an error appears.
			JOptionPane.showMessageDialog(null, "Report Failed");
			
		}	
	}

	@Override
	public void propertyChange(PropertyChangeEvent propertyEvent) {
		if (propertyEvent.getPropertyName() == "TRANSFERSELECTED") {
			transferSelected = (boolean) propertyEvent.getNewValue();
		}
		if(propertyEvent.getPropertyName() == "DEGREELEVELSELECTED") {
			degreeLevelSelected = (boolean) propertyEvent.getNewValue();
			
		}
		if(propertyEvent.getPropertyName() == "PROGRAMSELECTED") {
			programSelected = (boolean) propertyEvent.getNewValue();
		}
		if(propertyEvent.getOldValue() == "Academic") {
			runAcademicReport();
		}
		if(propertyEvent.getOldValue() == "Employment") {
			runEmploymentReport();
		}		
	}
}
