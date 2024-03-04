package MyTimeTable;

import java.util.ArrayList;

import MyTimeTable.Dao.UserDao;
import MyTimeTable.Exception.CourseClashException;
import MyTimeTable.Exception.DuplicateCourseException;
import MyTimeTable.Exception.FullCapacityException;

import java.time.LocalTime;

public class CourseManager {

//	Stores users' enrolled course.
	
	private static ArrayList<Course> myCourses = new ArrayList<>();
	
//	Stores the NAMES of user's enrolled courses.
	
	private static ArrayList<String> myCoursesNames = new ArrayList<>();
	
//	Stores all courses imported from the 'course.csv' file.
	
	private static ArrayList<Course> courseList = new ArrayList<>();
	
//	Adds a course to the user's list of enrolled courses and also updates the capacity of the course (if delivery = face-to-face).
	
	public void addCourse(String username, Course courseObject) throws DuplicateCourseException, FullCapacityException, CourseClashException {

		if (courseObject.getCapacity().equals("0")) {
			throw new FullCapacityException();
		}
		else if (myCourses.contains(courseObject)) {
			throw new DuplicateCourseException();
		}
		else if(courseClashChecker(courseObject) == true) {
			throw new CourseClashException();
		}
		else {
			getMyCourses().add(courseObject);
			getMyCoursesNames().add(courseObject.getName());
			if (courseObject.getCapacity().equals("N/A"));
			else {
				int newCapacity = Integer.parseInt(courseObject.getCapacity()) - 1;
				courseObject.setCapacity(Integer.toString(newCapacity));
			}
			UserDao userDao = new UserDao();
			userDao.updateEnrolments(username, getMyCoursesNames());
		}
	}

//	Takes in a course name String argument and return the associated course object from the list of available courses.
	
	public Course getCourseByName(String courseName) { 

		Course locatedCourse = null;

		for (Course course : courseList) {
			if (courseName.equals(course.getName())) {
				locatedCourse = course;
			}
		}
		return locatedCourse;
	}

//	Takes in a course Object as a parameter and returns true if the course overlaps with one of the user's existing enrolled courses.
	
	public boolean courseClashChecker(Course courseObject) {

		boolean overlaps = false;

		for (Course c : myCourses) {
			if (c == null) {
				overlaps = false;
			}
			else if (c.getDayOfLecture().equals(courseObject.getDayOfLecture())) {
				String c1Start = c.getTimeOfLecture().split("-")[0];
				String c1End = c.getTimeOfLecture().split("-")[1];
				String c2Start = courseObject.getTimeOfLecture().split("-")[0];
				String c2End = courseObject.getTimeOfLecture().split("-")[1];
				LocalTime c1StartTime = LocalTime.of(Integer.parseInt((c1Start).split(":")[0]), Integer.parseInt((c1Start).split(":")[1]));
				LocalTime c1EndTime = LocalTime.of(Integer.parseInt((c1End).split(":")[0]), Integer.parseInt((c1End).split(":")[1]));
				LocalTime c2StartTime = LocalTime.of(Integer.parseInt((c2Start).split(":")[0]), Integer.parseInt((c2Start).split(":")[1]));
				LocalTime c2EndTime = LocalTime.of(Integer.parseInt((c2End).split(":")[0]), Integer.parseInt((c2End).split(":")[1]));
				overlaps = ((c1StartTime.isBefore(c2EndTime)) && (c1EndTime.isAfter(c2StartTime)));
			}
		}
		return overlaps;
	}

//	Takes in a keyword String argument entered by the user and return an ArrayList storing any matching courses with course names containing that keyword.

	public ArrayList<Course> getCoursesByKeyword(String keyword) {

		ArrayList<Course> foundCourses = new ArrayList<>();

		for (Course course : getCourseList()) { 
			String courseName = course.getName().toUpperCase();
			if (courseName.contains(keyword.toUpperCase())) {
				foundCourses.add(course);	
			}
		}
		return foundCourses;
	}
	
//	Takes in a course name String argument and removes the associated course object from the user's list enrolled courses.
	

	public void removeEnrolledCourse(String courseName) {
		
		getMyCourses().remove(getCourseByName(courseName));
		getMyCoursesNames().remove(courseName);
		if (getCourseByName(courseName).getCapacity().equals("N/A"));
		else {
			int newCapacity = Integer.parseInt(getCourseByName(courseName).getCapacity()) + 1;
			getCourseByName(courseName).setCapacity(Integer.toString(newCapacity));
		}
	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	public ArrayList<Course> getMyCourses() {
		return myCourses;
	}

	public void setMyCourses(ArrayList<Course> myCourses) {
		CourseManager.myCourses = myCourses;
	}

	public void setCourseList(ArrayList<Course> courseList) {
		CourseManager.courseList = courseList;
	}

	public ArrayList<String> getMyCoursesNames() {
		return myCoursesNames;
	}

	public void setMyCoursesNames(ArrayList<String> myCoursesNames) {
		CourseManager.myCoursesNames = myCoursesNames;
	}	



}
