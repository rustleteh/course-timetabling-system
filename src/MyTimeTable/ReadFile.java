package MyTimeTable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;

//	This ReadFile() class contains the readData method() which extracts course information from the course.csv file and 
//	initialises new course objects to be stored in the 'courselist' ArrayList in the CourseManager class. I utilise the java.time API 
//	in order to print out the course duration in the required time format. 

public class ReadFile {
	
	public static boolean readData(String filepath) {

		String line = "";
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				String name = values[0];
				String capacity = values[1];
				String year = values[2];
				String deliveryMode = values[3];
				String dayOfLecture = values[4];
				String[] timeValues = values[5].split(":");
				String startHour = timeValues[0];
				String startMinute = timeValues[1];
				LocalTime startTime = LocalTime.of(Integer.parseInt(startHour), Integer.parseInt(startMinute));
				Double duration = Double.parseDouble(values[6]);
				String durationString = Double.toString(duration);
				long durationHours = duration.longValue();
				String decimal = durationString.substring(durationString.indexOf(".")).substring(1);
				int durationMinutes = 0;
				if (decimal.equals(Integer.toString(5))) {
					durationMinutes = 30;
				}
				LocalTime endTime = startTime.plusHours(durationHours).plusMinutes(durationMinutes);
				String timeOfLecture = startTime.toString() + "-" + endTime.toString(); 	
				CourseManager cm = new CourseManager();
				cm.getCourseList().add(new Course(name, capacity, year, deliveryMode, dayOfLecture, timeOfLecture, duration));
			}
			br.close();
			return true;
		} catch (FileNotFoundException fnfe) {
			System.err.println("Error! The course.csv file was not found.");
			return false;
		} catch (NullPointerException npe) {
			System.err.println("Error! File is null.");
			return false;
		} catch (IOException ioe) {
			System.err.println("Error! An error occured when reading the course.csv file.");
			return false;
		}
	}
}