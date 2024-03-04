package MyTimeTable.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import MyTimeTable.Course;
import MyTimeTable.CourseManager;

//	This class updates the external SQLite database table 'coursecapacity' that keeps track of the capacity of courses.

public class CourseCapacityDao {
	
//	This method below creates the 'coursecapacity' table if it does not currently exist.
	
	public void setUp() {
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			Statement stmt = conn.createStatement();
			stmt.execute("create table if not exists coursecapacity ("
					+ "course varchar(30) not null, "
					+ "capacity int, "
					+ "primary key (course));");
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
//	This method below checks that the 'coursecapacity' table is already populated with course data and prevents courses to be inserted twice.
	
	public boolean checkForData() {
		
		boolean dataExists = false;
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			String sql = "select * from coursecapacity;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				dataExists = true;
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} 
		return dataExists;
	}
	
//	This method below populates the 'coursecapacity' table with courses (and their capacities) from the myCourses ArrayList 
//	after being read from the 'course.csv' file.
	
	public void addCourseCapacities() {
		
		if (checkForData());
		else {
			try {
				Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
				CourseManager cm = new CourseManager();
				for (Course course : cm.getCourseList()) {
					String sql = "insert into coursecapacity (course, capacity)"
							+ "values (?, ?);";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, course.getName());
					stmt.setString(2, null);
					stmt.executeUpdate();
					stmt.close();
				}
				conn.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
//	This method below is called after a course is enrolled or withdrawn and it updates the capacity of that course. 
	
	public void updateCourseCapacity() {
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			CourseManager cm = new CourseManager();
			for (Course course : cm.getCourseList()) {
				String sql = "update coursecapacity "
						+ "set capacity = ? "
						+ "where course = ?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, course.getCapacity());
				stmt.setString(2, course.getName());
				stmt.executeUpdate();
				stmt.close();
			}
			conn.close();
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
	}

}
