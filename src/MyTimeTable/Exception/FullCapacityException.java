package MyTimeTable.Exception;

//	Custom exception that is thrown when user tries to enrol in a course with face-to-face delivery and has a capacity of 0. 

public class FullCapacityException extends Exception {

	public FullCapacityException() {
		super();
	}

	public FullCapacityException(String errorMessage) {
		super(errorMessage);
	}
}
