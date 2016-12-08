/**
()to do for this gui
1) add academic info to the same panel as academic
2) add transfer table to the same as adding buttons
3) make sure every updates when add and changed
 * 
 */
package student;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import academic.AcademicCollection;
import academic.AcademicRecord;
import employment.Employer;
import employment.EmployerCollection;

/**
 * @author Andrew,Brandon,Brian
 *
 */
public class Student extends Observable {


	public static final String FIRST_NAME = "FIRST_NAME";
	public static final String LAST_NAME = "LAST_NAME";
	public static final String ACADEMIC_RECORD = "ACADEMIC_RECORD";
	public static final String EMPLOYERS = "EMPLOYERS";
	
	private String myID;
	private String myStudentID;
	private String myFirstName;
	private String myLastName;
	private AcademicRecord myAcademicRecord;
	private ArrayList<Employer> myEmployers;
	
	/**
	 * This Constructor should be used in most cases.
	 * @param theStudentID Unique ID of the student
	 * @param theFirstName first name of the student
	 * @param theLastName last name of the student;
	 * @param theRecord for any existing academic record.
	 * @param theEmployers for any employers the student already has.
	 */
	public Student(String theStudentID, String theFirstName, String theLastName, AcademicRecord theRecord, ArrayList<Employer> theEmployers) {
		myID = theStudentID;
		//setStudentID(theStudentID);
		myFirstName = theFirstName;
		myLastName = theLastName;
		myAcademicRecord = theRecord;
		myEmployers = theEmployers;
	}
	
	/**
	 * WARNING- This constructor is ONLY for use when a student is to be added, but no information is available.
	 * @param theName is the student's name.
	 */
	public Student(String theFirstName, String theLastName) {
		myFirstName = theFirstName;
		myLastName = theLastName;
		//this.setFirstName(theFirstName);
		//this.setLastName(theLastName);
		
		/**
		 * This allows a record to be created, and editable for future use.
		 */
		myEmployers = new ArrayList<Employer>();
		myAcademicRecord = null;
	}
	
	public boolean addAcademicRecord(AcademicRecord theRecord) {
		
		boolean flag = false;
		
		try {
			myAcademicRecord = new AcademicRecord(theRecord.getID(),theRecord.getStudentID(), theRecord.getProgram(), theRecord.getDegreeLevel(),
					theRecord.getGraduationTerm(), theRecord.getGraduationYear(), theRecord.getUWEmail(), 
						theRecord.getExternalEmail(), theRecord.getGPA(), theRecord.getTransferSchools());
			AcademicCollection.add(myAcademicRecord);
			hasChanged();
			notifyObservers(ACADEMIC_RECORD);
				
				flag = true;
			} catch(Exception e) {
				
				flag = false;
			}
			
			return flag;
	}
	
	public boolean addEmployer(Employer theEmployer) {
		
		boolean flag = false;
		
		try {
			myEmployers.add(theEmployer);
			EmployerCollection.add(theEmployer, this.myAcademicRecord.getStudentID());
			hasChanged();
			notifyObservers(EMPLOYERS);
			flag = true;
			
		} catch(Exception e) {
				
				flag = false;
		}
			
			return flag;
	}
	
	public String getStudentID() {
		return myStudentID;
	}

	public void setStudentID(String theStudentID) {
		this.myStudentID = theStudentID;
	}

	public String getFirstName() {
		return myFirstName;
	}
	
	public void setFirstName(String theFirstName) {
		this.myFirstName = theFirstName;
		StudentCollection.update(this, "firstName", myFirstName);
		hasChanged();
		notifyObservers(FIRST_NAME);
	}
	
	public String getLastName() {
		return myLastName;
	}
	
	public void setLastName(String theLastName) {
		this.myLastName = theLastName;
		StudentCollection.update(this, "lastName", myLastName);
		hasChanged();
		notifyObservers(LAST_NAME);
	}
	
	public AcademicRecord getAcademicRecord() {
		return myAcademicRecord;
	}
	
	public void setAcademicRecord(AcademicRecord theAcademicRecord) {
		this.myAcademicRecord = theAcademicRecord;
		AcademicCollection.add(myAcademicRecord);
		hasChanged();
		notifyObservers(ACADEMIC_RECORD);
	}
	
	public ArrayList<Employer> getEmployers() {
		return myEmployers;
	}
	
	public Employer getEmployer(String theEmployerID) {

		int ndx = -1;
		for(Employer e : myEmployers) {
			if(e.getID() == theEmployerID) {
				ndx = myEmployers.indexOf(e);
			}
		}
		
		if(ndx == -1) {
			return null;
		} else {
			
			return myEmployers.get(ndx);
		}
	}
	
	public void setEmployers(ArrayList<Employer> theEmployers) {
		myEmployers = theEmployers;
		
		for(Employer e : myEmployers) {
			EmployerCollection.add(e, this.myAcademicRecord.getStudentID());
			hasChanged();
			notifyObservers(EMPLOYERS);
		}
	}
	
	public void setEmployers(Employer theEmployer) {
		myEmployers = new ArrayList<Employer>();
		myEmployers.add(theEmployer);
		EmployerCollection.add(theEmployer, this.myAcademicRecord.getStudentID());
		hasChanged();
		notifyObservers(EMPLOYERS);
	}

	public String getID() {
		return myID;
	}

	public void setID(String theID) {
		this.myID = theID;
	}

	public void addToObservers(Observer theObserver) {
		addObserver(theObserver);
	}
	
}
