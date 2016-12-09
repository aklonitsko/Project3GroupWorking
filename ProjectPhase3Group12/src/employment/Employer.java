/**
 * 
 */
package employment;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Andrew,Brandon,Brian
 *
 */
public class Employer extends Observable {
	
	public static final String COMPANY_NAME = "COMPANY_NAME";
	public static final String START_DATE = "START_DATE";
	public static final String SALARY = "SALARY";
	public static final String POSITION = "POSITION";
	public static final String SKILLS_LIST = "SKILLS_LIST";
	
	private String myID;
	private String myCompanyName;
	private String myStartDate;
	private double mySalary;
	private String myPosition;
	private ArrayList<String> mySkillsList;
	
	/**
	 * This is the constructor used by the database to return objects of this type.
	 * 
	 * @param id Unique id of the employer created by the database
	 * @param theCompanyName for the name of the company.
	 * @param theStartDate to see if this was an internship (i.e. startDate < graduationDate).
	 * @param theSalary for the yearly pay.
	 * @param thePosition for the title of their position.
	 * @param theSkills list of skills that the employer already has.
	 */
	public Employer(String employerID, String theCompanyName, String theStartDate, double theSalary,
			String thePosition, ArrayList<String> theSkills) {
		myID = employerID;
		myCompanyName = theCompanyName;
		myStartDate = theStartDate;
		mySalary = theSalary;
		myPosition = thePosition;
		mySkillsList = theSkills;
	}
	
	/**
	 * This is the first constructor that takes a single skill to add to the list.
	 * 
	 * @param theCompanyName for the name of the company.
	 * @param theStartDate to see if this was an internship (i.e. startDate < graduationDate).
	 * @param theSalary for the yearly pay.
	 * @param thePosition for the title of their position.
	 * @param theSkill for a skill the student has.
	 */
	public Employer(String theCompanyName, String theStartDate, double theSalary, String thePosition, String theSkill) {
		this.setCompanyName(theCompanyName);
		this.setStartDate(theStartDate);
		this.setSalary(theSalary);
		this.setPosition(thePosition);
		
		mySkillsList = new ArrayList<String>();
		
		this.mySkillsList.add(theSkill);
		EmployerCollection.add(this.myID, theSkill);
		hasChanged();
		notifyObservers(SKILLS_LIST);
	}
	
	/**
	 * This overloaded constructor takes no skills, but everything else.
	 * 
	 * @param theCompanyName
	 * @param theStartDate
	 * @param theSalary
	 * @param thePosition
	 */
	public Employer(String theCompanyName, String theStartDate, double theSalary, String thePosition) {
		
		this.setCompanyName(theCompanyName);
		this.setStartDate(theStartDate);
		this.setSalary(theSalary);
		this.setPosition(thePosition);
		
		mySkillsList = new ArrayList<String>();
		
	}
	
	public Employer(String theCompanyName, String theStartDate) {
		this.myCompanyName = theCompanyName;
		this.myStartDate = theStartDate;
		this.mySalary = 0;
		this.myPosition = null;
		
		mySkillsList = new ArrayList<String>();
	}

	public void setPosition(String thePosition) {
		
		this.myPosition = thePosition;
		EmployerCollection.update(this, "position", myPosition);
		hasChanged();
		notifyObservers(POSITION);
	}

	public void setSalary(double theSalary) {
		
		this.mySalary = theSalary;
		EmployerCollection.update(this, "salary", mySalary);
		hasChanged();
		notifyObservers(SALARY);
	}

	public void setStartDate(String theStartDate) {
		
		this.myStartDate = theStartDate;
		EmployerCollection.update(this, "startDate", myStartDate);
		hasChanged();
		notifyObservers(START_DATE);
	}

	public void setCompanyName(String theCompanyName) {
		
		this.myCompanyName = theCompanyName;
		EmployerCollection.update(this, "name", myCompanyName);
		hasChanged();
		notifyObservers(COMPANY_NAME);
	}
	
	public String getPosition() {
		
		return myPosition;
	}
	
	public double getSalary() {
		
		return mySalary;
	}
	
	public String getStartDate() {
		
		return myStartDate;
	}
	
	public String getCompanyName() {
		
		return myCompanyName;
	}

	public void addSkill(String theSkill) {
		
		mySkillsList.add(theSkill);
		EmployerCollection.add(myID, theSkill);
		hasChanged();
		notifyObservers(SKILLS_LIST);
	}
	
	public void addSkill(ArrayList<String> theSkills) {
		
		for(String s : theSkills) {
			mySkillsList.add(s);
			EmployerCollection.add(myID, s);
			hasChanged();
			notifyObservers(SKILLS_LIST);
		}
	}
	
	public ArrayList<String> getSkills() {
		
		ArrayList<String> tempList = new ArrayList<String>();
		
		for(String s : mySkillsList) {
			tempList.add(s);
		}
		
		return tempList;
	}

	public String getID() {
		return myID;
	}

	public void setID(String myID) {
		this.myID = myID;
	}

	public void addToObservers(Observer theObserver) {
		addObserver(theObserver);
	}
}
