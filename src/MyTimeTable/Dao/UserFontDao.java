package MyTimeTable.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import MyTimeTable.Model;

//	This class updates the external SQLite database table 'userfonts' that keeps track of user font preferences.

public class UserFontDao {

	public void setUp() {

		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			Statement stmt = conn.createStatement();
			stmt.execute("create table if not exists userfonts ("
					+ "username varchar(30) not null, "
					+ "type varchar(30), "
					+ "weight varchar(20), "
					+ "posture varchar(20), "
					+ "size varchar(30), "
					+ "color varchar(30), "
					+ "primary key (username));");
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
//	This method below checks that the 'userfonts' table is already populated with the font preferences of the user who has just 
//	logged in and prevents data to be inserted twice.
	
	public boolean checkForData() {
		
		boolean dataExists = false;
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			Model model = Model.getInstance();
			String sql = "select * from userfonts where username = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, model.getCurrentUser());
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
	
//	This method below initialises a new row in the external SQLite database with the default font preferences. 
//	This allow for the row to be updated later on if the user changes the font to their liking. 

	public void initialiseNewUserFont() {

		if (checkForData());
		else {
			try {
				Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
				Model model = Model.getInstance();
				String sql = "insert into userfonts (username, type, weight, posture, size, color)"
						+ "values (?, ?, ?, ?, ?, ?);";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, model.getCurrentUser());
				stmt.setString(2, "System");
				stmt.setString(3, "NORMAL");
				stmt.setString(4, "REGULAR");
				stmt.setString(5, "24.0,18.0,13.0");
				stmt.setString(6, "BLACK");
				stmt.executeUpdate();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
//	This method below updates the user's row in the SQLite database according to their chosen font preferences. 
	
	public void saveUserFont() {

		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			Model model = Model.getInstance();
			String sql = "update userfonts "
					+ "set type = ?, "
					+ "weight = ?, "
					+ "posture = ?, "
					+ "size = ?, "
					+ "color = ? "
					+ "where username = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, model.getFontPreference().getFamily());
			stmt.setString(2, model.getFontPreference().getWeight());
			stmt.setString(3, model.getFontPreference().getPosture());
			String size = model.getFontPreference().getSize()[0] + "," + model.getFontPreference().getSize()[1] + "," + model.getFontPreference().getSize()[2];
			stmt.setString(4, size);
			stmt.setString(5, model.getFontPreference().getColor());
			stmt.setString(6, model.getCurrentUser());
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}
	
//	This method below accesses the SQLite database and retrieves the row of data corresponding to the user 
//	who just logged in. The method extracts the data and saves it to the current user's font preferences in the 
//	Model class. 
	
	public void restoreUserFont() {

		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:timetableUsers.db");
			Model model = Model.getInstance();
			String sql = "select type, weight, posture, size, color from userfonts where username = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, model.getCurrentUser());
			ResultSet rs = stmt.executeQuery();
			model.getFontPreference().setFamily(rs.getString("type"));
			model.getFontPreference().setWeight(rs.getString("weight"));
			model.getFontPreference().setPosture(rs.getString("posture"));
			model.getFontPreference().setColor(rs.getString("color"));
			String sizeValues[] = rs.getString("size").split(",");
			double[] size = {Double.parseDouble(sizeValues[0]), Double.parseDouble(sizeValues[1]), Double.parseDouble(sizeValues[2])};
			model.getFontPreference().setSize(size);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
