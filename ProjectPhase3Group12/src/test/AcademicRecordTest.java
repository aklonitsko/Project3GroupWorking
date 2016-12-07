/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import academic.AcademicRecord;
import academic.TransferSchool;

/**
 * @author Andrew,Brandon,Brian
 *
 */
public class AcademicRecordTest {

	private AcademicRecord testRecord;
	private ArrayList<TransferSchool> testTransferSchools = new ArrayList<TransferSchool>();
	private String academicIDTest = "01234";
	private String studentIDTest = "56789";
	private String programTest = "CSS";
	private String degreeLevelTest = "B.S.";
	private String graduationTermTest = "Winter";
	private String graduationYearTest = "2018";
	private String uwEmailTest = "gibbbran@uw.edu";
	private String externalEmailTest = "somebodyIDontKnow@gmail.com";
	private double GPATest = 3.5;
	private String transferSchoolNameTest = "Idaho State University";
	private String degreeEarnedTest = "B.S. in CS";
	
	@Before
	public void setUp() {
		
		testTransferSchools.add(new TransferSchool(academicIDTest, studentIDTest, transferSchoolNameTest, GPATest, degreeEarnedTest));
		testRecord = new AcademicRecord(academicIDTest, studentIDTest, programTest, degreeLevelTest, graduationTermTest,
				graduationYearTest, uwEmailTest, externalEmailTest, GPATest, null);
	}
	
	@Test
	public void testAcademicConstructorWithID() {
		testRecord = new AcademicRecord(academicIDTest, studentIDTest, programTest, degreeLevelTest, graduationTermTest,
				graduationYearTest, uwEmailTest, externalEmailTest, GPATest, null);
		assertNotNull("Object Failed to Instanciate", testRecord);
	}
	
	@Test
	public void testAcademicConstructorWithIDTruth() {
		AcademicRecord testRecordTwo = new AcademicRecord(academicIDTest, studentIDTest, programTest, degreeLevelTest, graduationTermTest,
				graduationYearTest, uwEmailTest, externalEmailTest, GPATest, null);
		assertEquals(testRecordTwo, testRecord);
	}
	
	@Test
	public void testAcademicConstructorWithoutID() {
		testRecord = new AcademicRecord(studentIDTest, programTest, degreeLevelTest, graduationTermTest,
				graduationYearTest, uwEmailTest, externalEmailTest, GPATest, null);
		assertNotNull("Object Failed to Instanciate", testRecord);
	}
	
	@Test
	public void testAcademicConstructorWithoutIDTruth() {
		AcademicRecord testRecordTwo = new AcademicRecord(academicIDTest, studentIDTest, programTest, degreeLevelTest, graduationTermTest,
				graduationYearTest, uwEmailTest, externalEmailTest, GPATest, null);
		assertEquals(testRecordTwo, testRecord);
	}
	
	@Test
	public void testGetID() {
		assertTrue(testRecord.getID().equals(academicIDTest));
	}
	
	@Test
	public void testSetID() {
		testRecord.setID("99999");
		assertEquals(testRecord.getID(), "99999");
		
	}
	
	@Test
	public void testGetStudentID() {
		assertTrue(testRecord.getStudentID().equals(studentIDTest));
	}
	
	@Test
	public void testSetStudentID() {
		testRecord.setStudentID("99999");
		assertEquals(testRecord.getStudentID(), "99999");
	}
	
	@Test
	public void testGetProgram() {
		assertTrue(testRecord.getStudentID().equals(programTest));
	}
	
	@Test
	public void testSetProgram() {
		testRecord.setProgram("99999");
		assertEquals(testRecord.getProgram(), "99999");
	}
	
	@Test
	public void testGetDegreeLevel() {
		assertTrue(testRecord.getDegreeLevel().equals(degreeLevelTest));

	}
	
	@Test
	public void testSetDegreeLevel() {
		testRecord.setDegreeLevel("99999");
		assertEquals(testRecord.getDegreeLevel(), "99999");
	}
	
	@Test
	public void testGetGraduationTerm() {
		assertTrue(testRecord.getGraduationTerm().equals(graduationTermTest));
	}
	
	@Test
	public void testSetGraduationTerm() {
		testRecord.setGraduationTerm("99999");
		assertEquals(testRecord.getGraduationTerm(), "99999");
	}
	
	@Test
	public void testGetGraduationYear() {
		assertTrue(testRecord.getGraduationYear().equals(graduationYearTest));
	}
	
	@Test
	public void testSetGraduationYear() {
		testRecord.setGraduationYear("99999");
		assertEquals(testRecord.getGraduationYear(), "99999");
	}
	
	@Test
	public void testGetUWEmail() {
		assertTrue(testRecord.getUWEmail().equals(uwEmailTest));
	}
	
	@Test
	public void testSetUWEmail() {
		testRecord.setUWEmail("notAnEmail@Nothing.net");
		assertEquals(testRecord.getUWEmail(), "notAnEmail@Nothing.net");
	}
	
	@Test
	public void testGetExternalEmail() {
		assertTrue(testRecord.getExternalEmail().equals(externalEmailTest));
	}
	
	@Test
	public void testSetExternalEmail() {
		testRecord.setExternalEmail("notAnEmail@Nothing.net");
		assertEquals(testRecord.getExternalEmail(), "notAnEmail@Nothing.net");
	}
	
	@Test
	public void testGetGPA() {
		assertTrue(testRecord.getGPA() == GPATest);
	}
	
	@Test
	public void testSetGPA() {
		testRecord.setGPA(4.0);
		assertTrue(testRecord.getGPA() == 4.0);
	}
	
	@Test
	public void testSetGPAFail() {
		testRecord.setGPA(-1);
		assertFalse(testRecord.getGPA() == -1);
	}
	
	@Test
	public void testGetTransferSchools() {
		ArrayList<TransferSchool> tempSchools = new ArrayList<TransferSchool>();
		tempSchools = testRecord.getTransferSchools();
		assertEquals(tempSchools, testRecord.getTransferSchools());
	}
	
	@Test
	public void testSetTransferSchools() {
		testRecord.setTransferSchools(new ArrayList<TransferSchool>());
		assertEquals(testRecord.getTransferSchools(), null);
	}
	
	@Test
	public void testAddTransferSchool() {
		testRecord.addTransferSchool(new TransferSchool(academicIDTest, academicIDTest, academicIDTest, GPATest, academicIDTest));
		//testTransferSchools.addTransferSchool(new TransferSchool(academicIDTest, academicIDTest, academicIDTest, GPATest, academicIDTest));
		assertEquals(testRecord.getTransferSchools().get(testRecord.getTransferSchools().size()), testTransferSchools.get(testTransferSchools.size()));
	}
}
