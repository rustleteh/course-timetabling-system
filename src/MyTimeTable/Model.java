package MyTimeTable;

import java.sql.SQLException;

import MyTimeTable.Dao.CourseCapacityDao;
import MyTimeTable.Dao.UserDao;
import MyTimeTable.Dao.UserFontDao;

//	This model class manages the data and logic and rules of the program. 
//	This class also defines instances of both the User and CourseCapacity data access objects.
//	used the Singleton pattern to ensure that only one unique instance of the model is defined throughout the program. 

public class Model {
	
	private UserDao userDao = new UserDao();
	private UserFontDao userFontDao = new UserFontDao();
	private CourseCapacityDao courseCapacityDao = new CourseCapacityDao();
	private double[] defaultFontSize = {24.0, 18.0, 13.0};
	private UserFont fontPreference = new UserFont("System", "NORMAL", "REGULAR", defaultFontSize, "BLACK");
	private String currentUser;
	private static Model uniqueInstance;
	
	private Model() {
		
	}
	public static Model getInstance() {
		
		if(uniqueInstance == null) {
			uniqueInstance = new Model();
		}
		return uniqueInstance;
	}
	
	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	public void setUp() throws SQLException {
		this.userDao.setUp();
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public CourseCapacityDao getCourseCapacityDao() {
		return courseCapacityDao;
	}
	public UserFont getFontPreference() {
		return fontPreference;
	}
	public void setFontPreference(UserFont fontPreference) {
		this.fontPreference = fontPreference;
	}
	public UserFontDao getUserFontDao() {
		return userFontDao;
	}
	public void setUserFontDao(UserFontDao userFontDao) {
		this.userFontDao = userFontDao;
	}

}
