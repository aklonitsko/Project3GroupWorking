/**
Brandon Gibbons
 * 
 */
package academic;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Andrew,Brandon,Brian
 *
 */
public class AcademicRecord extends Observable {
	
	public static final String STUDENT_ID = "STUDENT_ID";
	public static final String PROGRAM = "PROGRAM";
	public static final String DEGREE_LEVEL = "DEGREE_LEVEL";
	public static final String GRADUATION_TERM = "GRADUATION_TERM";
	public static final String GRADUATION_YEAR = "GRADUATION_YEAR";
	public static final String UW_EMAIL = "UW_EMAIL";
	public static final String EXTERNAL_EMAIL = "EXTERNAL_EMAIL";
	public static final String GPA = "GPA";
	public static final String TRANSFER_SCHOOLS = "TRANSFER_SCHOOLS";
	
	private String myID;
	private String myStudentID;
	private String myProgram;
	private String myDegreeLevel;
	private String myGraduationTerm;
	private String myGraduationYear;
	private String myUWEmail;
	private String myExternalEmail;
	private double myGPA;
	private ArrayList<TransferSchool> myTransferSchools;
	private boolean isTransfer = false;
	
	public AcademicRecord(String theAcademicID, String theStudentID, String theProgram, String theDegreeLevel, String theGraduationTerm, 
			String theGraduationYear, String theUWEmail, String theExternalEmail, double theGPA, ArrayList<TransferSchool> theTransferSchools) {
		
		myID = theAcademicID;
		myStudentID = theStudentID;
		myProgram = theProgram;
		myDegreeLevel = theDegreeLevel;
		myGraduationTerm = theGraduationTerm;
		myGraduationYear = theGraduationYear;
		myUWEmail = theUWEmail;
		myExternalEmail = theExternalEmail;
		myGPA = theGPA;
		myTransferSchools = theTransferSchools;
	}
	
	public AcademicRecord(String theStudentID, String theProgram, String theDegreeLevel, String theGraduationTerm, String theGraduationYear,
			String theUWEmail, String theExternalEmail, double theGPA, ArrayList<TransferSchool> thePreviousSchools){
		this.setStudentID(theStudentID);
		this.setProgram(theProgram);
		this.setDegreeLevel(theDegreeLevel);
		this.setGraduationTerm(theGraduationTerm);
		this.setGraduationYear(theGraduationYear);
		this.setUWEmail(theUWEmail);
		this.setExternalEmail(theExternalEmail);
		this.setGPA(theGPA);
		
		myTransferSchools = thePreviousSchools;
	}
	
	public String getID() {
		return myID;
	}

	public void setID(String theID) {
		this.myID = theID;
	}
	
	public String getStudentID() {
		return myStudentID;
	}

	public void setStudentID(String theStudentID) {
		this.myStudentID = theStudentID;
		hasChanged();
		AcademicCollection.update(this, "studentID", myStudentID);
		notifyObservers(STUDENT_ID);
	}

	public String getProgram() {
		return myProgram;
	}

	public void setProgram(String theProgram) {
		this.myProgram = theProgram;
		hasChanged();
		AcademicCollection.update(this, "program", theProgram);
		notifyObservers(PROGRAM);
	}
	
	public String getDegreeLevel() {
		return myDegreeLevel;
	}

	public void setDegreeLevel(String theDegreeLevel) {
		this.myDegreeLevel = theDegreeLevel;
		hasChanged();
		AcademicCollection.update(this, "degreeLevel", myDegreeLevel);
		notifyObservers(DEGREE_LEVEL);
	}

	public String getGraduationTerm() {
		return myGraduationTerm;
	}

	public void setGraduationTerm(String theGraduationTerm) {
		this.myGraduationTerm = theGraduationTerm;
		hasChanged();
		AcademicCollection.update(this, "graduationTerm", myGraduationTerm);
		notifyObservers(GRADUATION_TERM);
	}

	public String getGraduationYear() {
		return myGraduationYear;
	}

	public void setGraduationYear(String theGraduationYear) {
		this.myGraduationYear = theGraduationYear;
		hasChanged();
		AcademicCollection.update(this, "graduationYear", myGraduationYear);
		notifyObservers(GRADUATION_YEAR);
	}

	public String getUWEmail() {
		return myUWEmail;
	}

	public void setUWEmail(String theUWEmail) {
		this.myUWEmail = theUWEmail;
		hasChanged();
		AcademicCollection.update(this, "uwEmail", myUWEmail);
		notifyObservers(UW_EMAIL);
	}

	public String getExternalEmail() {
		return myExternalEmail;
	}

	public void setExternalEmail(String theExternalEmail) {
		this.myExternalEmail = theExternalEmail;
		hasChanged();
		AcademicCollection.update(this, "externalEmail", myExternalEmail);
		notifyObservers(EXTERNAL_EMAIL);
	}

	public double getGPA() {
		return myGPA;
	}

	public void setGPA(double theGPA) {
		this.myGPA = theGPA;
		hasChanged();
		AcademicCollection.update(this, "GPA", myGPA);
		notifyObservers(GPA);
	}

	public ArrayList<TransferSchool> getTransferSchools() {
		return myTransferSchools;
	}
	
	public void setTransferSchools(ArrayList<TransferSchool> thePreviousSchools) {
		
		myTransferSchools = new ArrayList<TransferSchool>();
		
		for( TransferSchool t : thePreviousSchools) {
			myTransferSchools.add(t);
			AcademicCollection.add(t);
			hasChanged();
			notifyObservers(TRANSFER_SCHOOLS);
		}
	}
	
	public boolean isTransfer() {
		if(myTransferSchools != null) {
			isTransfer = true;
		}
		
		return isTransfer;
	}

	public boolean addTransferSchool(TransferSchool theTransferSchool){
		boolean flag = false;
		
		try {
			
			myTransferSchools.add(theTransferSchool);
			flag = true;
			hasChanged();
			AcademicCollection.add(theTransferSchool);
			notifyObservers(TRANSFER_SCHOOLS);
			
		} catch(Exception e) {
			
			flag = false;
		}
		
		return flag;
	}
	
	public void addToObservers(Observer theObserver) {
		this.addObserver(theObserver);
	}

}
