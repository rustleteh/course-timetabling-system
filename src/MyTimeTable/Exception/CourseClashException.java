package MyTimeTable.Exception;

//	Custom exception that is thrown when user tries to enrol in a course that clashes with one of their existing enrolled courses. 

public class CourseClashException extends Exception {

	public CourseClashException() {
		super();
	}
	
	public CourseClashException(String errorMessage) {
		super(errorMessage);
	}
}
