# Project3GroupWorking
##ReadMe##

Choose your role in the University. Students may only add, edit, or view their own employment information. Faculty may only view the list of
students or, search the database for a student. Staff may view, edit, or add employment or academic information including transfer 
schools, and run reports.

Use case 2.1 adding student academic record.
  This was implemented by the team as a whole. Andrew created the GUI, Brandon created the core classes, and Brian created the collction and
  database connections. There could be a bug that adds a Transfer school twice to the database, which then is displayed directly in 
  order unerneath each other.
  
Use case 2.2 editing student academic record.
  This was implemented by the team as a whole. Andrew created the GUI, Brandon created the core classes, and Brian created the collction and
  database connections. Editing could present a bug that prevents a piece of the record from being edited, and throwing an exception 
  from the database;
  
Use case 2.3 adding Employer Information.
  This use case was written by Brandon, who created the GUI, and the employer class. Andrew helped debug the GUI, and Brian
  created the collection.
  
Use case 2.4 editing Employer Information.
  This use case was written by Brandon, who created the GUI, and the employer class. Andrew helped debug the GUI, and Brian
  created the collection.
  
Additional implementation:
  Employers can take transfer schools, and multiple transfer schools.
  Employers have their own skill list that can host skills of the student.
  
Unimplemented features:
  Faculty and staff cannot run reports as intended.
  Testing was not fully covered for all classes and methods used.
  
Implmentation Specific: None. Simply run the program and the rest is taken care of by the project.

Class Specifics:
Brian -
  AcademicCollection
  AcademicDB
  StudentCollection
  StudentDB
  EmployerConnection
  EmployerDB
  DataConnection
  
Brandon -
  Employer
  TransferSchool
  AcademicRecord
  Student
  StudentGUI
  ReadMe
  Tests implemented
  
Andrew - 
  AcademicGUI
  EmployementGUI
  FacultyGUI
  MainGUI
  ReportsGUI
  StaffGUI
  StudentSearchGUI
  UserSelectorGUI
  
