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
	private JLabel titleLabel;
	private JTextField studentNameTextField;
	private JButton employerSearchButton;

	private JPanel addStudentPanel;
	private JLabel[] studentTextLabels = new JLabel[3];
	private JTextField[] studentTextFields = new JTextField[3];
	private JButton addStudentsButton;

	/**
	 * Use this for Item administration. Add components that contain the list,
	 * search and add to this.
	 */
	public StudentSearchGUI() {
		//myStudent = theStudent;
		//myEmployers = EmployerCollection.getEmployers(myStudent.getStudentID());
		
		setLayout(new BorderLayout());
		myStudentList = getStudentData(null,null);
		setUpComponents();
		setVisible(true);
		setSize(500, 500);
	}

	private ArrayList<Student> getStudentData(String searchKeyF,String searchKeyL) {
		if(searchKeyF != null && searchKeyL != null)
		{
			myStudentList = StudentCollection.searchByName(searchKeyF, searchKeyL);
		}else{
			myStudentList = StudentCollection.getStudents();
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

		buttonPanel.add(myListButton);
		buttonPanel.add(mySearchButton);

		//buttonPanel.add(myAddButton);
		add(buttonPanel, BorderLayout.NORTH);

		// List Panel
		dataPanel = new JPanel();
		myTable = new JTable(myData, studentColumnNames);
		myScrollPane = new JScrollPane(myTable);
		dataPanel.add(myScrollPane);
		myTable.getModel().addTableModelListener(this);

		// Search Panel
		searchPanel = new JPanel();
		titleLabel = new JLabel("Enter Name: ");
		studentNameTextField = new JTextField(25);
		employerSearchButton = new JButton("Search");
		employerSearchButton.addActionListener(this);
		searchPanel.add(titleLabel);
		searchPanel.add(studentNameTextField);
		searchPanel.add(employerSearchButton);

		// Add Panel
		addStudentPanel = new JPanel();
		addStudentPanel.setLayout(new GridLayout(8, 0));
		
		String labelNames[] = {"StudentId", "First Name", "Last Name"};
		
		for (int i = 0; i < labelNames.length; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 0));
			studentTextLabels[i] = new JLabel(labelNames[i]);
			studentTextFields[i] = new JTextField(25);
			panel.add(studentTextLabels[i]);
			panel.add(studentTextFields[i]);
			addStudentPanel.add(panel);
		}

		JPanel panel = new JPanel();
		
		addStudentsButton = new JButton("Add");
		addStudentsButton.addActionListener(this);
		
		panel.add(addStudentsButton);
		
		addStudentPanel.add(panel);
		add(dataPanel, BorderLayout.CENTER);

	}

	/**
	 * Make the buttons work!
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == myListButton) {
			
			dataPanel.removeAll();
			myTable = new JTable(myData, studentColumnNames);
			myTable.getModel().addTableModelListener(this);
			myScrollPane = new JScrollPane(myTable);
			dataPanel.add(myScrollPane);
			dataPanel.revalidate();
			this.repaint();

		} else if (e.getSource() == mySearchButton) {
			dataPanel.removeAll();
			dataPanel.add(searchPanel);
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
			String FirstName = st[0];
			String LastName = st[1];
			System.out.println();
			if (title.length() > 0 && st.length==2) {
				st = title.split(title);
				myStudentList = getStudentData("andrew","klonitsko");
				
				dataPanel.removeAll();
				myTable = new JTable(myData, studentColumnNames);
				myTable.getModel().addTableModelListener(this);
				myScrollPane = new JScrollPane(myTable);
				dataPanel.add(myScrollPane);
				dataPanel.revalidate();
				this.repaint();
				studentNameTextField.setText("");
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
			studentTextFields[0].setFocusable(true);
			return;
		}
		
		String lstNameTemp = studentTextFields[2].getText();
		if (lstNameTemp.length() == 0) {
			JOptionPane.showMessageDialog(null, "Enter students last name");
			studentTextFields[0].setFocusable(true);
			return;
		}
		Student temmpStudent;
		temmpStudent = new Student(firstNameTemp, lstNameTemp);
		String message = "Student add failed";
		if (StudentCollection.add(temmpStudent)) {
			message = "Student added";
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
