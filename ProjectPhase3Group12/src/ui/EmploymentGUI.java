/**put skills in the add skills panel
 * fix update issue with employment
 * don't have student select panel open for staff
 * grab 
 */
package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


import javax.swing.JButton;
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
import student.Student;

/**
 * @author Andrew,Brandon,Brian
 *
 */
public class EmploymentGUI extends JPanel 
implements ActionListener,TableModelListener {

	//Variables that are used in every Panel
	private Employer emptoaddSkills;
	private static final long serialVersionUID = 644843L;
	private JButton mBtnEmploymentList,mBtnAdd, mBtnAddSkills;
	private ArrayList<Employer> mEmployerList;
	private Student mStudent;

	//variables for employer table
	private String[] mEmploymentColumnNames = {"name","salary","startDate","position"};
	private JPanel mPnlList;
	private JPanel mPnlBothList;
	private JPanel mPnlButtons;
	private Object[][] mData;
	private JTable mTable;
	private JScrollPane mScrollPane;

	//variables for skill table
	private String[] mSkillColumnNames  = {"employerName","skills"};
	private Object[][] mSkillData;
	private JTable mSkillTable;
	private JScrollPane mSkillScrollPane;

	//variables to add new Skill
	private JPanel mPnlAddSkill;
	private JLabel mTxfLabelEmployerText;
	private JLabel mTxfLabelEmployerID;
	private JLabel mTxfSkillLabel;
	private JTextField mTxfSkill;
	private JButton mBtnAddSkillToEmp;

	//variables to add new employment
	private JPanel mPnlAdd;
	private JLabel[] txfLabel = new JLabel[10];
	private JTextField[] txfField = new JTextField[10];
	private JButton mBtnAddEmployment;


	/**
	 * 
	 * Constructor this creates a JPanel that can be used for employment
	 * 
	 * @param Student object
	 */
	public EmploymentGUI(Student theStudent){
		mStudent = theStudent;
	



		setLayout(new BorderLayout());
		mEmployerList = getData(mStudent); 

		getSkillData();

		createComponents();

		setVisible(true);
		setSize(500, 500);
	}

	/**
	 * 
	 * Sets up the skill data matrix to be use for the Table later
	 * 
	 */
	private void getSkillData() {
		int i = 0;
		mSkillData= new Object[25][2];
		for(Employer emp: mEmployerList ){

			mSkillData[i][0] = emp.getCompanyName();
			for(String skill:emp.getSkills() ){

				mSkillData[i][1]= skill;
				i++;
			}
			i++;
		}

	}

	/**
	 * 
	 * Sets up the employer list to be use for table later
	 * 
	 * @param Student object
	 * @return ArrayList<Employer> 
	 */
	private ArrayList<Employer> getData(Student theStudent) {

		if(theStudent.getEmployers().size() == 0){
			System.out.println("employers null");
			theStudent.addEmployer(new Employer("New Employer", "New Employer"));
		}
		try{
			mEmployerList = theStudent.getEmployers();

		}catch(Exception e){}
		//This populates the employer matrices all well as sets the employer to add skills to as the first one in the list
		if(mEmployerList != null){

			mData = new Object[mEmployerList.size()][mEmploymentColumnNames.length];
			for (int i = 0; i < mEmployerList.size(); i++) {
				mData[i][0] = mEmployerList.get(i).getCompanyName();
				//TODO maybe need to make parse sal as string
				mData[i][1] = mEmployerList.get(i).getSalary();
				mData[i][2] = mEmployerList.get(i).getStartDate();
				mData[i][3] = mEmployerList.get(i).getPosition();
			}
		}


		return mEmployerList;
	}

	/* 
	 * Sets up all the components for all of the panels to start off with some panels are reused later
	 * 
	 * 
	 */
	private void createComponents() {

		// The Top Most Panel That Allows Adding Employer and Listing Employer
		mPnlButtons = new JPanel();

		mBtnEmploymentList = new JButton("Employment List");
		mBtnEmploymentList.addActionListener(this);

		mBtnAdd = new JButton("Add Employer");
		mBtnAdd.addActionListener(this);

		mBtnAddSkills = new JButton("Add Skills");
		mBtnAddSkills.addActionListener(this);

		mPnlButtons.add(mBtnEmploymentList);
		mPnlButtons.add(mBtnAdd);
		mPnlButtons.add(mBtnAddSkills);

		add(mPnlButtons, BorderLayout.NORTH);

		//main panel that list employers for a student
		mPnlList = new JPanel();

		//create a panel for center of main panel
		mPnlBothList = new JPanel(new BorderLayout());
		mTable = new JTable(mData,mEmploymentColumnNames);
		mScrollPane = new JScrollPane(mTable);
		mPnlBothList.add(new JLabel("*To Add Skills To Different Employers: Double Click A Cell Then Press Enter,Then Select Add Skills"),BorderLayout.NORTH);
		mPnlBothList.add(mScrollPane,BorderLayout.CENTER);
		mTable.getModel().addTableModelListener(this);

		//mSkillTable = new JTable(mSkillData,mSkillColumnNames);
		//mSkillScrollPane = new JScrollPane(mSkillTable);
		//mPnlBothList.add(mSkillScrollPane);

		mPnlList.add(mPnlBothList);

		//Add Panel- allows input to add a new employer
		mPnlAdd = new JPanel();
		mPnlAdd.setLayout(new GridLayout(0,1));
		String mLabelName[] = {"Enter Employer Name:","Enter Salary:","Enter Start Date:","Enter Position:"};

		//creates text labels and fields for adding employer
		for (int i = 0; i < mLabelName.length; i++) {
			JPanel panel = new JPanel();
			;
			if(mLabelName[i] == "Enter Start Date:"){
				JPanel datePanel = new JPanel();
				datePanel.setLayout(new GridLayout(1, 7));	

				txfLabel[i] = new JLabel(mLabelName[i]);
				txfLabel[6] = new JLabel("   Month(mm):");
				txfField[6] = new JTextField(2);
				txfLabel[7] = new JLabel("   Day(dd):");
				txfField[7] = new JTextField(2);
				txfLabel[8] = new JLabel("   Year(yyyy):");
				txfField[8] = new JTextField(4);

				datePanel.add(txfLabel[i]);
				datePanel.add(txfLabel[6]);
				datePanel.add(txfField[6]);
				datePanel.add(txfLabel[7]);
				datePanel.add(txfField[7]);
				datePanel.add(txfLabel[8]);
				datePanel.add(txfField[8]);

				mPnlAdd.add(datePanel);
			}else{
				panel.setLayout(new GridLayout(1, 1));
				txfLabel[i] = new JLabel(mLabelName[i]);
				txfField[i] = new JTextField(25);
				panel.add(txfLabel[i]);
				panel.add(txfField[i]);
				mPnlAdd.add(panel);
			}


		}
		//Add new Skill

		mPnlAddSkill = new JPanel();
		if(mEmployerList != null){
			emptoaddSkills = mEmployerList.get(0);
			mTxfLabelEmployerText = new JLabel("You are adding skills to:    ");
			mTxfLabelEmployerID= new JLabel(emptoaddSkills.getCompanyName());

			mTxfSkillLabel= new JLabel("Add A Skill: ");
			mTxfSkill = new JTextField(8);
			mBtnAddSkillToEmp = new JButton("Add Skill");
			mBtnAddSkillToEmp.addActionListener(this);
			mPnlAddSkill.setLayout(new GridLayout(3,2));

			mPnlAddSkill.add(mTxfLabelEmployerText);
			mPnlAddSkill.add(mTxfLabelEmployerID);

			mPnlAddSkill.add(mTxfSkillLabel);
			mPnlAddSkill.add(mTxfSkill);
			mPnlAddSkill.add(mBtnAddSkillToEmp);
		}else{
			mPnlAddSkill.add(new JLabel("ADD An Employer First"));
		}

		JPanel mPanel = new JPanel();
		mBtnAddEmployment = new JButton("Add");
		mBtnAddEmployment.addActionListener(this);
		mPanel.add(mBtnAddEmployment);
		mPnlAdd.add(mPanel);
		add(mPnlList,BorderLayout.CENTER);
	}

	//Checks to determine what object was clicked on the panels
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == mBtnEmploymentList){
			mEmployerList = getData(mStudent);

			mPnlList.removeAll();

			mPnlBothList = new JPanel(new BorderLayout());
			mTable = new JTable(mData,mEmploymentColumnNames);
			mScrollPane = new JScrollPane(mTable);

			mPnlBothList.add(new JLabel("*To Add Skills To Different Employers: Double Click A Cell Then Press Enter,Then Select Add Skills"),BorderLayout.NORTH);
			mPnlBothList.add(mScrollPane,BorderLayout.CENTER);

			mTable.getModel().addTableModelListener(this);
			mPnlList.add(mPnlBothList);

			mPnlList.revalidate();
			this.repaint();

		} else if(e.getSource() == mBtnAdd){
			mPnlList.removeAll();
			mPnlList.add(mPnlAdd);
			mPnlList.revalidate();
			this.repaint();

		}  else if(e.getSource() == mBtnAddSkills){
			System.out.println(emptoaddSkills.getCompanyName());
			if(emptoaddSkills.getCompanyName() != null){
				mEmployerList = getData(mStudent);	
				mPnlList.removeAll();

				getSkillData();
				mPnlAddSkill = new JPanel();
				mTxfLabelEmployerText = new JLabel("You are adding skills to:    ");
				JPanel borderla = new JPanel(new BorderLayout());

				mSkillTable = new JTable(mSkillData,mSkillColumnNames);
				mSkillScrollPane = new JScrollPane(mSkillTable);

				mTxfLabelEmployerID= new JLabel(emptoaddSkills.getCompanyName());
				mTxfSkillLabel= new JLabel("Add A Skill: ");
				mTxfSkill = new JTextField(8);

				mBtnAddSkillToEmp = new JButton("Add Skill");
				mBtnAddSkillToEmp.addActionListener(this);

				mPnlAddSkill.setLayout(new GridLayout(3,2));
				mPnlAddSkill.add(mTxfLabelEmployerText);
				mPnlAddSkill.add(mTxfLabelEmployerID);
				mPnlAddSkill.add(mTxfSkillLabel);
				mPnlAddSkill.add(mTxfSkill);
				mPnlAddSkill.add(mBtnAddSkillToEmp);

				borderla.add(mSkillScrollPane, BorderLayout.NORTH);
				borderla.add(mPnlAddSkill,BorderLayout.CENTER);

				mPnlList.add(borderla);
				mPnlList.revalidate();
				this.repaint();
			}
			else{
				JOptionPane.showMessageDialog(null, "Select an employer from the list");
			}


		} else if(e.getSource() == mBtnAddEmployment){
			performAddEmployment();

		}  else if(e.getSource() == mBtnAddSkillToEmp){
			performAddSkills();
			if(emptoaddSkills.getCompanyName() != null){
				mEmployerList = getData(mStudent);	
				mPnlList.removeAll();

				getSkillData();
				mPnlAddSkill = new JPanel();
				mTxfLabelEmployerText = new JLabel("You are adding skills to:    ");
				JPanel borderla = new JPanel(new BorderLayout());

				mSkillTable = new JTable(mSkillData,mSkillColumnNames);
				mSkillScrollPane = new JScrollPane(mSkillTable);

				mTxfLabelEmployerID= new JLabel(emptoaddSkills.getCompanyName());
				mTxfSkillLabel= new JLabel("Add A Skill: ");;
				mTxfSkill = new JTextField(8);

				mBtnAddSkillToEmp = new JButton("Add Skill");
				mBtnAddSkillToEmp.addActionListener(this);

				mPnlAddSkill.setLayout(new GridLayout(3,2));
				mPnlAddSkill.add(mTxfLabelEmployerText);
				mPnlAddSkill.add(mTxfLabelEmployerID);
				mPnlAddSkill.add(mTxfSkillLabel);
				mPnlAddSkill.add(mTxfSkill);
				mPnlAddSkill.add(mBtnAddSkillToEmp);

				borderla.add(mSkillScrollPane, BorderLayout.NORTH);
				borderla.add(mPnlAddSkill,BorderLayout.CENTER);

				mPnlList.add(borderla);
				mPnlList.revalidate();
				this.repaint();
			}
			else{
				JOptionPane.showMessageDialog(null, "Select an employer from the list");
			}

		}

	}
	/* 
	 * When the add skills button is clicked, this adds a skill depending on what employer was selected
	 * 
	 *
	 */
	private void performAddSkills() {
		String mEmpName = mTxfSkill.getText();
		if (mEmpName.length() == 0) {
			JOptionPane.showMessageDialog(null, "Enter a valid skill");
			mTxfSkill.setFocusable(true);
			return;
		}
		String message = "Skill add failed";
		try{
			emptoaddSkills.addSkill(mEmpName);
			message = "Skill Added";
		}catch(Exception a){

		}

		JOptionPane.showMessageDialog(null, message);


	}

	/* 
	 * When the add Employment button is clicked, this adds a Employer using the textfields
	 * 
	 *
	 */
	private void performAddEmployment() {
		String mEmpName = txfField[0].getText();
		if (mEmpName.length() == 0) {
			JOptionPane.showMessageDialog(null, "Enter a valid Employer name");
			txfField[0].setFocusable(true);
			return;
		}
		String salaryStr = txfField[1].getText();
		double salary = 0.0;
		if (salaryStr.length() != 0) {
			try {
				salary = Double.parseDouble(salaryStr);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Enter Salary as decimal");
				txfField[1].setText("");
				txfField[1].setFocusable(true);
				return;
			}
		}
		String mMonth = txfField[6].getText();
		if (mMonth.length() != 2) {
			JOptionPane.showMessageDialog(null, "Enter Month As mm");
			txfField[6].setFocusable(true);
			return;
		}
		String mDay = txfField[7].getText();
		if (mDay.length() != 2) {
			JOptionPane.showMessageDialog(null, "Enter Day As dd");
			txfField[7].setFocusable(true);
			return;
		}
		String mYear =txfField[8].getText();
		if (mYear.length() != 4) {
			JOptionPane.showMessageDialog(null, "Enter Month As yyyy");
			txfField[8].setFocusable(true);
			return;
		}

		String mDate = mYear + "-" + mMonth + "-" + mDay; 



		String mPosition = txfField[3].getText();
		if (mPosition.length() == 0) {
			JOptionPane.showMessageDialog(null, "Enter A Position:");
			txfField[3].setFocusable(true);
			return;
		}

		//Adding To the collection
		Employer mEmp;
		mEmp = new Employer(mEmpName, mDate, salary,mPosition);


		String message = "Employer add failed";
		if (mStudent.addEmployer(mEmp)) {
			message = "Employer added";
		}
		JOptionPane.showMessageDialog(null, message);

		// Clear all text fields.

		txfField[0].setText("");
		txfField[1].setText("");
		txfField[3].setText("");
		txfField[6].setText("");
		txfField[7].setText("");
		txfField[8].setText("");





	}

	//This is called when the a cell is doubled clicked and enter is pressed. 
	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		int row = e.getFirstRow();
		int column = e.getColumn();
		TableModel model = (TableModel) e.getSource();
		String columnName = model.getColumnName(column);
		Object data = model.getValueAt(row, column);
		if (data != null && ((String) data).length() != 0) {

			try{
				Employer item = mEmployerList.get(row);
				emptoaddSkills = item;

				if (columnName == "name") {
					mStudent.getEmployer(item.getID()).setCompanyName((String)data);
				}
				if (columnName == "salary") {
					mStudent.getEmployer(item.getID()).setSalary((Double.parseDouble((String)data)));
				}
				if (columnName == "startDate") {
					mStudent.getEmployer(item.getID()).setStartDate((String)data);
				}
				if (columnName == "position") {
					mStudent.getEmployer(item.getID()).setPosition((String)data);
				}
			}catch(Exception e4){
				JOptionPane.showMessageDialog(null, "Update failed");
			}

		}

	}



}
