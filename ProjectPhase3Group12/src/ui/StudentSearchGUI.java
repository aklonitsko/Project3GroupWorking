/**
 * change on gui
 * fix glitch
 * open to search first
 * have a add student
 * go to table to allow user to select student
 */
package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import academic.AcademicRecord;
import academic.TransferSchool;
import employment.Employer;
import employment.EmployerCollection;
import student.Student;
import student.StudentCollection;

/**
 * @author Andrew,Brandon,Brian
 *
 */
public class StudentSearchGUI extends JFrame implements Observer, ActionListener, TableModelListener {

	
	private static final long serialVersionUID = -8675309L;
	
	private ArrayList<Student> myStudentList;
	private Student studentToReturn;
	//private Student myStudent;
	
	private JButton myListButton, mySearchButton, myAddButton;
	private JPanel buttonPanel, dataPanel;
	private String[] studentColumnNames = { "studentID", "firstName", "lastName"};

	private Object[][] myData;
	private JTable myTable;
	private JScrollPane myScrollPane;
	private JPanel searchPanel;
	private JPanel howtoPanel;
	private JLabel titleLabel;
	private JTextField studentNameTextField;
	private JButton employerSearchButton;

	private JPanel addStudentPanel;
	private JLabel[] studentTextLabels = new JLabel[3];
	private JTextField[] studentTextFields = new JTextField[3];
	private JButton addStudentsButton;
	JPanel addNewStudentpanel;

	/**
	 * Use this for Item administration. Add components that contain the list,
	 * search and add to this.
	 */
	public StudentSearchGUI() {
		super("Find Student To Edit");
		
		setLayout(new BorderLayout());
		myStudentList = getStudentData(null,null);
		setUpComponents();
		setVisible(true);
		setSize(500, 500);
		setLocationRelativeTo(null);
	}

	private ArrayList<Student> getStudentData(String searchKeyF,String searchKeyL) {
		if(searchKeyF != null && searchKeyL != null)
		{
			myStudentList = StudentCollection.searchByName(searchKeyF, searchKeyL);
		}else{
			
		}
		if(myStudentList != null){
			myData = new Object[myStudentList.size()][studentColumnNames.length];
			for(int i = 0; i< myStudentList.size(); i++) {
				Student tempStudent = myStudentList.get(i);
				myData[i][0] = tempStudent.getID();
				//TODO Maybe pars gpa as string
				myData[i][1] = tempStudent.getFirstName();
				myData[i][2] = tempStudent.getLastName();
			}
			
			
		}
			
		
		return myStudentList;
	}
	/**
	 * Create the three panels to add to this GUI. One for list, one for search,
	 * one for add.
	 */
	private void setUpComponents() {
		
		// A button panel at the top for list, search, add
		buttonPanel = new JPanel();
		myListButton = new JButton("Student List");
		myListButton.addActionListener(this);

		mySearchButton = new JButton("Student Search");
		mySearchButton.addActionListener(this);

		//myAddButton = new JButton("Add Student");
		//myAddButton.addActionListener(this);
		
		buttonPanel.add(mySearchButton);
		buttonPanel.add(myListButton);
		

		//buttonPanel.add(myAddButton);
		add(buttonPanel, BorderLayout.NORTH);

		// List Panel
		dataPanel = new JPanel();
		myTable = new JTable(myData, studentColumnNames);
		myScrollPane = new JScrollPane(myTable);
		//dataPanel.add(myScrollPane);
		myTable.getModel().addTableModelListener(this);

		// Search Panel
		searchPanel = new JPanel();
		howtoPanel = new JPanel(new BorderLayout());
		
		howtoPanel.add(new JLabel("Enter First and Last Name, ex: John Doe"),BorderLayout.NORTH);
		titleLabel = new JLabel("Enter Name: ");
		studentNameTextField = new JTextField(20);
		employerSearchButton = new JButton("Search");
		employerSearchButton.addActionListener(this);
		searchPanel.add(titleLabel);
		searchPanel.add(studentNameTextField);
		searchPanel.add(employerSearchButton);
		howtoPanel.add(searchPanel,BorderLayout.CENTER);
		
		dataPanel.add(howtoPanel);
		// Add Panel
		addStudentPanel = new JPanel();
		addStudentPanel.setLayout(new GridLayout(8, 0));
		
		String labelNames[] = {"Enter Student Id:", "Enter First Name:", "Enter Last Name:"};
		
		for (int i = 0; i < labelNames.length; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 0));
			studentTextLabels[i] = new JLabel(labelNames[i]);
			studentTextFields[i] = new JTextField(10);
			panel.add(studentTextLabels[i]);
			panel.add(studentTextFields[i]);
			addStudentPanel.add(panel);
		}

		addNewStudentpanel = new JPanel(new BorderLayout());
		
		addStudentsButton = new JButton("Add New Student");
		addStudentsButton.addActionListener(this);
		addNewStudentpanel.add(addStudentPanel, BorderLayout.NORTH);
		addNewStudentpanel.add(addStudentsButton,BorderLayout.CENTER);
		
		add(dataPanel);

	}

	/**
	 * Make the buttons work!
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String FirstName;
		String LastName;
		
		if (e.getSource() == myListButton) {
			if(myData != null){
			dataPanel.removeAll();
			myTable = new JTable(myData, studentColumnNames);
			myTable.getModel().addTableModelListener(this);
			myScrollPane = new JScrollPane(myTable);
			dataPanel.add(myScrollPane);
			dataPanel.revalidate();
			this.repaint();
			}
		} else if (e.getSource() == mySearchButton) {
			dataPanel.removeAll();
			dataPanel.add(howtoPanel);
			dataPanel.revalidate();
			this.repaint();
		} else if (e.getSource() == myAddButton) {
			dataPanel.removeAll();
			dataPanel.add(addStudentPanel);
			dataPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == employerSearchButton) {
			String title = studentNameTextField.getText();
			String[] st = title.split(" ");
			FirstName = st[0];
			LastName = st[1];
			if (title.length() > 0 && st.length==2) {
				
				myStudentList = getStudentData(FirstName,LastName);
				studentNameTextField.setText("");
				dataPanel.removeAll();
				myTable = new JTable(myData, studentColumnNames);
				myTable.getModel().addTableModelListener(this);
				myScrollPane = new JScrollPane(myTable);
				if(myData.length == 0){
				JPanel textAdd = new JPanel(new BorderLayout());
				textAdd.add(new JLabel("Enter A New Student, If Not New Search Again"),BorderLayout.NORTH);
				textAdd.add(addNewStudentpanel,BorderLayout.CENTER);
				studentTextFields[1].setText(FirstName);
				studentTextFields[2].setText(LastName);
				dataPanel.add(addNewStudentpanel);
				}else{
				JPanel nePan = new JPanel(new BorderLayout());
				nePan.add(new JLabel("To Select A Student: Double Click On Student, Then Press Enter"), BorderLayout.NORTH);
				nePan.add(myScrollPane,BorderLayout.CENTER);
				dataPanel.add(nePan);
				}
				add(dataPanel);
				dataPanel.revalidate();
				this.repaint();
				
			}
		} else if (e.getSource() == addStudentsButton) {
			performAddItem();
			
		}

	}

	/**
	 * Allows to add an Item. Only name and address is required.
	 */
	private void performAddItem() {

		String studentIDTemp = studentTextFields[0].getText();
		
		if (studentIDTemp.length() == 0) {
			JOptionPane.showMessageDialog(null, "Enter students ID");
			studentTextFields[0].setFocusable(true);
			return;
		}
		
		String firstNameTemp = studentTextFields[1].getText();
		
		if (firstNameTemp.length() == 0) {
			JOptionPane.showMessageDialog(null, "Enter students first name");
			studentTextFields[1].setFocusable(true);
			return;
		}
		
		String lstNameTemp = studentTextFields[2].getText();
		if (lstNameTemp.length() == 0) {
			JOptionPane.showMessageDialog(null, "Enter students last name");
			studentTextFields[2].setFocusable(true);
			return;
		}
		Student temmpStudent;
		temmpStudent = new Student(firstNameTemp, lstNameTemp);
		
		String message = "Student add failed";
		if (StudentCollection.add(temmpStudent)) {
			message = "Student added";
			myStudentList = getStudentData(firstNameTemp,lstNameTemp);
			dataPanel.removeAll();
			myTable = new JTable(myData, studentColumnNames);
			myTable.getModel().addTableModelListener(this);
			myScrollPane = new JScrollPane(myTable);
			dataPanel.add(myScrollPane);
			add(dataPanel);
			dataPanel.revalidate();
			this.repaint();
			
		}
		JOptionPane.showMessageDialog(null, message);
		
	}
	
	/**
	 * Listen to the cell changes on the table. 
	 */
	@Override
	public void tableChanged(TableModelEvent theEvent) {
		// add something here that can get the student box clicked and pass it to the main GUI for the other gui
		//classes to use
		
		int row = theEvent.getFirstRow();
		int column = theEvent.getColumn();
		
		TableModel tempModel = (TableModel) theEvent.getSource();
		String columnName = tempModel.getColumnName(column);
		Object data = tempModel.getValueAt(row, column);
		
		
		
		if (data != null && ((String) data).length() != 0) {
			Student tempStudent = myStudentList.get(row);
			studentToReturn = myStudentList.get(row);
			firePropertyChange("studentToReturn", null, studentToReturn);
			
			setVisible(false);
			System.out.println(tempStudent.getID());
			if (!StudentCollection.update(tempStudent, columnName, (String)data)) {
				JOptionPane.showMessageDialog(null, "Update failed");
			}
		}

	}
	
	public Student getCurrentStudent(){
		
		return studentToReturn;
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
