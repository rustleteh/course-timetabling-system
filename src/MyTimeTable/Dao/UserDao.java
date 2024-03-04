package MyTimeTable.Dao;

import java.sql.Statement;
import java.util.ArrayList;

import MyTimeTable.CourseManager;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

//	This class updates the external SQLite database table 'users' that keeps track of user information.

public class UserDao {
	
//	This method below creates the 'users' table (if it does not currently exists) when the program starts. 

	public void setUp() {
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			Statement stmt = conn.createStatement();
			stmt.execute("create table if not exists users ("
					+ "username varchar(20) not null, "
					+ "password varchar(10) not null, "
					+ "studentno varchar(10) not null, "
					+ "firstname varchar(20) not null, "
					+ "lastname varchar(20) not null, "
					+ "courselist varchar(300), "
					+ "primary key (username));");
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
//	This method below takes in a String username parameter and checks the 'users' table if a similar username already exists in the database.

	public boolean checkDuplicateUsername(String username) {
		
		boolean duplicateFlag = false;
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			String sql = "select * from users where username = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				duplicateFlag = true;
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		return duplicateFlag;
	}
	
//	This method below takes in a String student number parameter and checks the 'users' table if a similar student number already exists in the database.

	public boolean checkDuplicateStudentNo(String studentNo) {
		
		boolean duplicateFlag = false;
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			String sql = "select * from users where studentno = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, studentNo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				duplicateFlag = true;
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		return duplicateFlag;
	}
	
//	This method below takes in String username and String password parameters and checks if these credentials already exist in the database.
	
	public boolean findUser(String username, String password) {
		
		boolean validUser = false;
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			String sql = "select * from users where username = ? and password = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				validUser = true;
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		return validUser;
	}

//	This method below takes in several String parameters containing user information from when a new user account is created 
//	and inserts a new entry into the 'users' table.
	
	public void addUser(String username, String password, String studentNo, String firstName, String lastName) {
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			String sql = "insert into users (username, password, studentno, firstname, lastname, courselist)"
					+ "values (?, ?, ?, ?, ?, ?);";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, studentNo);
			stmt.setString(4, firstName);
			stmt.setString(5, lastName);
			stmt.setString(6, null);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
//	This method below takes in a String username parameter and retrieves the corresponding student number from the 'users' table. 
	
	public String getStudentNo(String username) {
		
		String studentNo = "";
				
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			String sql = "select studentno from users where username = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			studentNo = rs.getString("studentno");
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return studentNo;
	}
	
//	This method below takes in a username parameter and retrieves the corresponding first name from the 'users' table. 

	public String getFirstName(String username) {
		
		String firstName = "";
				
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			String sql = "select firstname from users where username = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			firstName = rs.getString("firstname");
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return firstName;
	}
	
//	This method below takes in a String username parameter and retrieves the corresponding last name from the 'users' table. 

	public String getLastName(String username) {
		
		String lastName = "";
				
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			String sql = "select lastname from users where username = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			lastName = rs.getString("lastname");
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return lastName;
	}
	
//	This method below takes in a String username parameter and String firstName parameter and 
//	updates the user's existing first name with the new first name that was passed in. 
	
	public void updateFirstName(String firstName, String username) {
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			String sql = "update users "
					+ "set firstname = ? "
					+ "where username = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, firstName);
			stmt.setString(2, username);
			stmt.execute();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
//	This method below takes in a String username parameter and String lastName parameter and 
//	updates the user's existing last name with the new last name that was passed in. 
	
	public void updateLastName(String lastName, String username) {
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			String sql = "update users "
					+ "set lastname = ? "
					+ "where username = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, lastName);
			stmt.setString(2, username);
			stmt.execute();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
//	This method below takes in a String username parameter and String password parameter and 
//	updates the user's existing password with the new password that was passed in. 
	
	public void updatePassword(String password, String username) {
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			String sql = "update users "
					+ "set password = ? "
					+ "where username = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, password);
			stmt.setString(2, username);
			stmt.execute();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
//	This method below takes in a String username parameter and an ArrayList parameter that stores only the NAMES of the user's
//	enrolled courses. It will update the courselist tuple in the database with this array of course names.
	
	public void updateEnrolments (String username, ArrayList<String> myCoursesNames) {
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			String sql = "update users "
					+ "set courselist = ? "
					+ "where username = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			if (myCoursesNames.isEmpty()) {
				stmt.setString(1, null);
			}
			else {stmt.setString(1, myCoursesNames.toString());
			}
			stmt.setString(2, username);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		}
			catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
//	This method below is called when a user successfully logs in. It retrieves the user's list of enrolled course names from the SQLite 
//	database, convert these names to actual course objects and stores them in the myCourses ArrayList in the program for manipulation and display.
	
	public boolean restoreEnrolments (String username) {
		
		boolean hasNoCourses = false;
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			String sql = "select courselist from users where username = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			String myCoursesNamesString = rs.getString("courselist");
			if (myCoursesNamesString==null) {
				hasNoCourses = true;
				stmt.close();
				conn.close();
			}
			else {		
				String myCoursesNamesStripped = myCoursesNamesString.replace("[", "").replace("]", "");
				String[] courseNamesArray = myCoursesNamesStripped.split(",");
				CourseManager cm = new CourseManager();
				for (String course : courseNamesArray) {
					cm.getMyCoursesNames().add(course.strip());
				}
				for (String courseName : cm.getMyCoursesNames()) {
					cm.getMyCourses().add(cm.getCourseByName(courseName));
				}
				stmt.close();
				conn.close();
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return hasNoCourses;
	}
}
