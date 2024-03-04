package MyTimeTable.Controller;

import java.io.IOException;
import java.util.HashMap;

import MyTimeTable.Course;
import MyTimeTable.CourseManager;
import MyTimeTable.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

//	Allows the user to see all their enrolled courses organised and color-coded in WEEKLY timetable format.
//	Note that this timetable view only shows a generic week and does not correspond to an actual calendar.
//	The semester period (Start of March to end of May) is displayed above the timetable view. 

public class EnrolmentTimetableController {
	
	private HashMap<String, Integer> timeGridMap = new HashMap<String, Integer>();
	private HashMap<String, Integer> dayGridMap = new HashMap<String, Integer>();
	
	@FXML
	private GridPane root;
	@FXML
	private Label topFont;
	@FXML
	private Label otherFont;
	@FXML
	private Label otherFont2;
	@FXML
	private GridPane timetable;
	@FXML
	private Label timeDay;
	@FXML
	private Label monday;
	@FXML
	private Label tuesday;
	@FXML
	private Label wednesday;
	@FXML
	private Label thursday;
	@FXML
	private Label friday;
	@FXML
	private Label saturday;
	@FXML
	private Label sunday;
	@FXML
	private Label t1;
	@FXML
	private Label t2;
	@FXML
	private Label t3;
	@FXML
	private Label t4;
	@FXML
	private Label t5;
	@FXML
	private Label t6;
	@FXML
	private Label t7;
	@FXML
	private Label t8;
	@FXML
	private Label t9;
	@FXML
	private Label t10;
	@FXML
	private Label t11;
	@FXML
	private Label t12;
	@FXML
	private Label t13;
	@FXML
	private Label t14;
	@FXML
	private Label t15;
	@FXML
	private Label t16;
	@FXML
	private Label t17;
	@FXML
	private Label t18;
	@FXML
	private Label t19;
	@FXML
	private Label t20;
	@FXML
	private Label t21;
	@FXML
	private Label t22;
	@FXML
	private Button goBackButton;
	private Scene scene;
	private Model model;

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public EnrolmentTimetableController(Model model) {
		this.model = model;
	}
	
//	This method below sets up the grid pane in the scene and labels the columns and rows with days and times respectively.
	
	public void setUp() {
		
		dayGridMap.put("Monday", 1);
		dayGridMap.put("Tuesday", 2);
		dayGridMap.put("Wednesday", 3);
		dayGridMap.put("Thursday", 4);
		dayGridMap.put("Friday", 5);
		dayGridMap.put("Saturday", 6);
		dayGridMap.put("Sunday", 7);
		timeGridMap.put("08:00", 1);
		timeGridMap.put("08:30", 2);
		timeGridMap.put("09:00", 3);
		timeGridMap.put("09:30", 4);
		timeGridMap.put("10:00", 5);
		timeGridMap.put("10:30", 6);
		timeGridMap.put("11:00", 7);
		timeGridMap.put("11:30", 8);
		timeGridMap.put("12:00", 9);
		timeGridMap.put("12:30", 10);
		timeGridMap.put("13:00", 11);
		timeGridMap.put("13:30", 12);
		timeGridMap.put("14:00", 13);
		timeGridMap.put("14:30", 14);
		timeGridMap.put("15:00", 15);
		timeGridMap.put("15:30", 16);
		timeGridMap.put("16:00", 17);
		timeGridMap.put("16:30", 18);
		timeGridMap.put("17:00", 19);
		timeGridMap.put("17:30", 20);
		timeGridMap.put("18:00", 21);
		timeGridMap.put("18:30", 22);
	}
	
//	This method below generates the timetable based on the user's enrolled courses.
//	It stores an array of colours that are systematically assigned to each subject.
//	Each course pane on the timetable is determined based on the course's start and end time and is 
//	also labelled with the course name.
	
	public void generateTimetable() {
		
		String[] colorArray = {"CHARTREUSE", "AQUA", "GOLD", "LIGHTPINK", "DARKGREY", "DARKTURQUOISE", "DARKSEAGREEN", "MINTCREAM"};
		int i = 0;
		CourseManager cm = new CourseManager();
		for (Course course : cm.getMyCourses()) {
			String day = course.getDayOfLecture();
			String[] timeValues = course.getTimeOfLecture().split("-");
			String startTime = timeValues[0];
			String endTime = timeValues[1];
			for (int j = timeGridMap.get(startTime); j <= timeGridMap.get(endTime) - 1; j ++) {
				Pane pane = new Pane();
				timetable.add(pane, dayGridMap.get(day), j);
				pane.setStyle("-fx-background-color:" + colorArray[i]);
				Label courseLabel = new Label(course.getName());
				courseLabel.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
				timetable.add(courseLabel, dayGridMap.get(day), timeGridMap.get(startTime));
			}
		i++;
		}
	}		
		
//	This method below returns the user to the User Dashboard scene. 

	public void goBack(Stage primaryStage) {
		
		goBackButton.setOnAction(event -> {

			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/DashboardView.fxml"));
				GridPane gp;
				String studentNo = this.model.getUserDao().getStudentNo(this.model.getCurrentUser());
				String firstName = this.model.getUserDao().getFirstName(this.model.getCurrentUser());
				String lastName = this.model.getUserDao().getLastName(this.model.getCurrentUser());
				DashboardController dbc = new DashboardController(this.model);
				loader.setController(dbc);
				gp = loader.load();
				Scene scene = new Scene(gp);
				primaryStage.setTitle("MyTimeTable - User Dashboard");
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.show();
				dbc.printUserDetails(studentNo, firstName, lastName);
				dbc.setScene(scene);
				dbc.setFont();
				dbc.setColor();
				dbc.editProfile(primaryStage);
				dbc.viewAllCourses(primaryStage);
				dbc.searchEnrol(primaryStage);
				dbc.viewEnrolmentList(primaryStage);
				dbc.viewEnrolmentTimetable(primaryStage);
				dbc.withdrawCourse(primaryStage);
				dbc.changeFont(primaryStage);
				dbc.export();
				dbc.logout(primaryStage);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		});
	}
	
//	This method sets the font for this scene by applying the type, style and size preferences from the
//	user's font preferences in the Model class.
	
	public void setFont() {
		String fontStyle = this.model.getFontPreference().getPosture();

		if (this.model.getFontPreference().getPosture().equals("REGULAR")) {
			fontStyle = "NORMAL";
		}
		root.setStyle("-fx-font-family:" + this.model.getFontPreference().getFamily() + ";" 
				+ "-fx-font-weight:" + this.model.getFontPreference().getWeight() + ";"
				+ "-fx-font-style:" + fontStyle + ";"
				+ "-fx-font-size:" + this.model.getFontPreference().getSize()[2]);
		timetable.setStyle("-fx-font-family:" + this.model.getFontPreference().getFamily() + ";" 
				+ "-fx-font-weight:" + this.model.getFontPreference().getWeight() + ";"
				+ "-fx-font-style:" + fontStyle + ";"
				+ "-fx-font-size:" + this.model.getFontPreference().getSize()[2]);
		topFont.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[0]));
		otherFont.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[2]));
		otherFont2.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[2]));
	}
	
//	This method sets the text colour for this scene by applying the colour preferences from the
//	user's font preferences in the Model class.
	
	public void setColor() {
		topFont.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		otherFont.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		otherFont2.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		timeDay.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		monday.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		tuesday.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		wednesday.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		thursday.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		friday.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		saturday.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		sunday.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t1.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t2.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t3.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t4.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t5.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t6.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t7.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t8.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t9.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t10.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t11.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t12.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t13.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t14.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t15.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t16.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t17.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t18.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t19.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t20.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t21.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		t22.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		goBackButton.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
	}

}