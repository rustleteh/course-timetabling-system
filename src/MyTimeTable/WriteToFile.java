package MyTimeTable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//	This class is responsible for exporting the currently logged-in user's enrolled courses to a .csv file that
//	will be stored in the src folder of the java project.

public class WriteToFile {
	
	public static void exportEnrolments(String filePath) {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
			String header = "Course name,Capacity,Year,Delivery mode,Day of lecture,Time of lecture,Duration of lecture(hour)";
			bw.write(header);
			bw.newLine();
			CourseManager cm = new CourseManager();
			for (Course course : cm.getMyCourses()) {
				String[] timeValues = course.getTimeOfLecture().split("-");
				String startTime = timeValues[0];
				String line = course.getName() + "," + course.getCapacity() + "," + course.getYear() + "," 
				+ course.getDeliveryMode() + "," +  course.getDayOfLecture() + "," + startTime + "," + course.getDuration();
				bw.write(line);
				bw.newLine();
			}
			bw.close();
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
