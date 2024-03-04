package MyTimeTable.Controller;

import java.io.IOException;

import MyTimeTable.Course;
import MyTimeTable.CourseManager;
import MyTimeTable.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage; 

//	This class is responsible for allowing the user to view all courses that are stored in the program.

public class ViewAllCoursesController {
	
	@FXML
	private GridPane root;
	@FXML
	private Label topFont;
	@FXML
	private Label otherFont;
	@FXML
	private Label otherFont2;
	@FXML
	private Label courseName1;
	@FXML
	private Label courseName2;
	@FXML
	private Label capacity1;
	@FXML
	private Label capacity2;
	@FXML
	private Label year1;
	@FXML
	private Label year2;
	@FXML
	private Label deliveryMode1;
	@FXML
	private Label deliveryMode2;
	@FXML
	private Label dayOfLecture1;
	@FXML
	private Label dayOfLecture2;
	@FXML
	private Label timeOfLecture1;
	@FXML
	private Label timeOfLecture2;
	@FXML
	private Label duration1;
	@FXML
	private Label duration2;
	@FXML
	private TableView<Course> availableCoursesTable;
	@FXML
	private TableView<Course> unavailableCoursesTable;
	@FXML
	private TableColumn<Course, String> courseNameColumn;
	@FXML
	private TableColumn<Course, String> courseNameColumn2;
	@FXML
	private TableColumn<Course, String> capacityColumn;
	@FXML
	private TableColumn<Course, String> capacityColumn2;
	@FXML
	private TableColumn<Course, String> yearColumn;
	@FXML
	private TableColumn<Course, String> yearColumn2;
	@FXML
	private TableColumn<Course, String> deliveryModeColumn;
	@FXML
	private TableColumn<Course, String> deliveryModeColumn2;
	@FXML
	private TableColumn<Course, String> dayColumn;
	@FXML
	private TableColumn<Course, String> dayColumn2;
	@FXML
	private TableColumn<Course, String> timeColumn;
	@FXML
	private TableColumn<Course, String> timeColumn2;
	@FXML
	private TableColumn<Course, Double> durationColumn;
	@FXML
	private TableColumn<Course, Double> durationColumn2;
	@FXML
	private Button goBackButton;
	private Scene scene;
	private Model model;

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public ViewAllCoursesController(Model model) {
		this.model = model;
	}
	
//	This method below functions to setup the two tables in the scene. The top table displays courses available 
//	for enrolment while the bottom table displays courses unavailable for enrolment. 
	
	public void setUpTables(Stage primaryStage) {
		
		courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		courseNameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
		capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
		capacityColumn2.setCellValueFactory(new PropertyValueFactory<>("capacity"));
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
		yearColumn2.setCellValueFactory(new PropertyValueFactory<>("year"));
		deliveryModeColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryMode"));
		deliveryModeColumn2.setCellValueFactory(new PropertyValueFactory<>("deliveryMode"));
		dayColumn.setCellValueFactory(new PropertyValueFactory<>("dayOfLecture"));
		dayColumn2.setCellValueFactory(new PropertyValueFactory<>("dayOfLecture"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeOfLecture"));
		timeColumn2.setCellValueFactory(new PropertyValueFactory<>("timeOfLecture"));
		durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
		durationColumn2.setCellValueFactory(new PropertyValueFactory<>("duration"));
	}
	
//	This method prints all the courses in the program to either of the 'available' or 'unavailable' tables based on its capacity value.
	
	public void printCourses(Stage primaryStage) {
		
		CourseManager cm = new CourseManager();
		
		for (Course course : cm.getCourseList()) {
			if (course.getDeliveryMode().equals("Online") || (course.getDeliveryMode().equals("Face-to-face") && Integer.parseInt(course.getCapacity()) > 0)) {
				availableCoursesTable.getItems().add(course);
			}
			else {
				unavailableCoursesTable.getItems().add(course);
			}
		}
	}
	
//	This method below returns the user back to the User Dashboard scene. 
	
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
		availableCoursesTable.setStyle("-fx-font-family:" + this.model.getFontPreference().getFamily() + ";" 
				+ "-fx-font-weight:" + this.model.getFontPreference().getWeight() + ";"
				+ "-fx-font-style:" + fontStyle + ";"
				+ "-fx-font-size:" + this.model.getFontPreference().getSize()[2]);
		unavailableCoursesTable.setStyle("-fx-font-family:" + this.model.getFontPreference().getFamily() + ";" 
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
		courseNameColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		courseNameColumn2.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		capacityColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		capacityColumn2.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		yearColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		yearColumn2.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		deliveryModeColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		deliveryModeColumn2.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		dayColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		dayColumn2.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		timeColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		timeColumn2.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		durationColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		durationColumn2.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		courseName1.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		courseName2.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		capacity1.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		capacity2.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		year1.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		year2.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		deliveryMode1.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		deliveryMode2.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		dayOfLecture1.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		dayOfLecture2.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		timeOfLecture1.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		timeOfLecture2.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		duration1.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		duration2.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		goBackButton.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
	}
	

}