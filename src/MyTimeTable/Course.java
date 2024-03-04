package MyTimeTable;

//	This Course class has the main responsibility of storing information about each course. 

public class Course {
	
	private String name;
	private String capacity;
	private String year;
	private String deliveryMode; 
	private String dayOfLecture;
	private String timeOfLecture;
	private double duration;
	
//	This constructor below initialises each course when it is read from the course.csv file. 
	
	public Course(String name, String capacity, String year, String deliveryMode, String dayOfLecture, String timeOfLecture, double duration) {
		this.name = name;
		this.capacity = capacity;
		this.year = year;
		this.deliveryMode = deliveryMode;
		this.dayOfLecture = dayOfLecture;
		this.timeOfLecture = timeOfLecture;
		this.duration = duration;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getDayOfLecture() {
		return dayOfLecture;
	}

	public void setDayOfLecture(String dayOfLecture) {
		this.dayOfLecture = dayOfLecture;
	}

	public String getTimeOfLecture() {
		return timeOfLecture;
	}

	public void setTimeOfLecture(String timeOfLecture) {
		this.timeOfLecture = timeOfLecture;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

}