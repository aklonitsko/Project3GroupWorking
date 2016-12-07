/**
 * 
 * 
 * 
 * andrew Klonitsko
 * Brian Lloyd
 */
package ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import academic.AcademicRecord;
import academic.TransferSchool;
import employment.Employer;
import student.Student;

/**
 * @author Andrew,Brandon,Brian
 *
 */
public class MainGUI extends JFrame implements PropertyChangeListener {


	private static final long serialVersionUID = 1L;
	private JTabbedPane myTabbedPane;
	private static Student mStudent;

	public static void main(String[] args) {
		MainGUI frame = new MainGUI();
		UserSelectorGUI userSelector = new UserSelectorGUI();

		mStudent = new Student("andrew", "klonitsko");
		mStudent.addEmployer(new Employer("THIS", "HERE"));
		mStudent.addEmployer(new Employer("Now", "HERE"));
		mStudent.addAcademicRecord(new AcademicRecord("Hello1", "Hello2", "Hello3", "Hello4", "Hello5", "Hello6", "Hello7", "hello1", 3.2, new ArrayList<TransferSchool>()));
		
		// Display User selector GUI and listen to user selection
		userSelector.addPropertyChangeListener(frame);
		userSelector.setVisible(true);

	}

	/**
	 * Launches the GUI, starting point of application
	 */

	public MainGUI() {
		super("Student Database System");
		createComponents();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 700);
		setLocationRelativeTo(null);
	}

	/**
	 * Create GUI components. This includes the UserSelectorGUI
	 */
	private void createComponents() {
		// Create the main tabbed pane
		myTabbedPane = new JTabbedPane();

		// Create the user selector GUI

	}

	private void createStaffComponents() {
		createTabbedGUI(new String[] { "Student", "Academic", "Employment", "Reports" });
	}

	private void createFacultyComponents() {
		createTabbedGUI(new String[] { "Reports" });
	}

	private void createStudentComponents() {
		createTabbedGUI(new String[] { "Employment" });
	}

	private void createTabbedGUI(String[] tabs) {
		for(String tabName : tabs) {
			JComponent newPanel = makeTextPanel(tabName);
			myTabbedPane.addTab(tabName, newPanel);
		}

		this.add(myTabbedPane);
		this.setVisible(true);
	}
	
	//Something

	private JComponent makeTextPanel(String type) {

		JPanel panel = new JPanel();
		if(type.equalsIgnoreCase("Employment")) {
			panel.add(new EmploymentGUI(mStudent));
			System.out.println("after panel add");
		} 
		else if(type.equalsIgnoreCase("Student")) {
			panel.add(new StudentGUI());
		} 
		else if(type.equalsIgnoreCase("Academic")) {
			panel.add(new AcademicGUI(mStudent));
		}
		else {
			panel.add(new JLabel("Needs to be implemented!"));
		}
		
		
		return panel;
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		// TODO Auto-generated method stub

		// Debug only
		System.out.println(e.getPropertyName() + " Old Value: " + e.getOldValue() + " New Value: " + e.getNewValue());

		if (e.getPropertyName().equals("user")) {
			String user = (String) e.getNewValue();
			if (user.equals("Staff")) {
				createStaffComponents();
			} else if (user.equals("Faculty")) {
				createFacultyComponents();
			} else {
				// Must be student if not Staff or Faculty
				createStudentComponents();
			}
		}

	}
}