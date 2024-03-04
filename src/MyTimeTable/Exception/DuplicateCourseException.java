package MyTimeTable.Exception;

//	Custom exception that is thrown when user tries to enrol in a course that they are already enrolled in. 

public class DuplicateCourseException extends Exception {

	public DuplicateCourseException() {
		super();
	}

	public DuplicateCourseException(String errorMessage) {
		super(errorMessage);
	}
}

