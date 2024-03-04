package MyTimeTable.Controller;

import java.io.IOException;

import MyTimeTable.Course;
import MyTimeTable.CourseManager;
import MyTimeTable.Model;
import MyTimeTable.Exception.CourseClashException;
import MyTimeTable.Exception.DuplicateCourseException;
import MyTimeTable.Exception.FullCapacityException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage; 

//	Allows users to search for courses to enrol in by entering a keyword in the textfield. 

public class SearchEnrolController {
	
	@FXML
	private GridPane root;
	@FXML
	private Label topFont;
	@FXML
	private Label otherFont;
	@FXML
	private TextField searchField;
	@FXML
	private TableView<Course> resultsTable;
	@FXML
	private TableColumn<Course, String> courseNameColumn;
	@FXML
	private TableColumn<Course, String> capacityColumn;
	@FXML
	private TableColumn<Course, String> yearColumn;
	@FXML
	private TableColumn<Course, String> deliveryModeColumn;
	@FXML
	private TableColumn<Course, String> dayColumn;
	@FXML
	private TableColumn<Course, String> timeColumn;
	@FXML
	private TableColumn<Course, Double> durationColumn;
	@FXML
	private TableColumn<Course, Course> enrolColumn;
	@FXML
	private Label courseName;
	@FXML
	private Label capacity;
	@FXML
	private Label year;
	@FXML
	private Label deliveryMode;
	@FXML
	private Label dayOfLecture;
	@FXML
	private Label timeOfLecture;
	@FXML
	private Label duration;
	@FXML
	private Label enrol;
	@FXML
	private Label messagePrinter;
	@FXML
	private Button goBackButton;
	private Scene scene;
	private Model model;

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public SearchEnrolController(Model model) {
		this.model = model;
	}
	
//	The method below is called when courses matching the search keyword are found.
//	These matching courses are printed to the results table in the scene.
//	An 'enrol' button is set up on the final column cell of each result row, which allows the user to enrol in that course.
//	Custom exceptions are defined in try-catch blocks to prevent users from enrolling in full courses, duplicate courses or clashing courses.

	
	
	public void setUpResultsTable(Stage primaryStage) {
		
		String currentUser = this.model.getCurrentUser();
		
		courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
		deliveryModeColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryMode"));
		dayColumn.setCellValueFactory(new PropertyValueFactory<>("dayOfLecture"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeOfLecture"));
		durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
		String buttonColor = this.model.getFontPreference().getColor();
		enrolColumn.setCellFactory(column -> new TableCell<Course, Course>() {
			private Button enrolButton = new Button("Enrol");
			{
				enrolButton.setOnAction(event -> {
					Course course = getTableRow().getItem();
					CourseManager cm = new CourseManager();
					try {
						cm.addCourse(currentUser, course);
						printConfirmation("You have sucessfully enrolled in '" + course.getName() + "'!");
						resultsTable.getItems().clear();
						for (Course c : cm.getCoursesByKeyword(searchField.getText())) {
							resultsTable.getItems().add(c);
						}
					} 
					catch (FullCapacityException e) {
						printError("The capacity of '" + course.getName() + "' is full! Please select another course.");
					}
					catch (DuplicateCourseException e) {
						printError("You are already enrolled in '" + course.getName() + "'! Please select another course.");

					} catch (CourseClashException e) {
						printError("'" + course.getName() + "' clashes with an existing enrolled course! Please select another course.");
					}
				});
			}	
			@Override
	        public void updateItem(Course value, boolean empty) {
				
	            super.updateItem(value, empty);
	            if (empty) {
	                setGraphic(null);
	            } 
	            else {
	                setGraphic(enrolButton);
	                enrolButton.setPrefWidth(84);
	                enrolButton.setStyle("-fx-text-fill:" + buttonColor);
	            }
	        }
		});
	}
	
//	This method below prints out all courses matching the user's input keyword to the table.
	
	public void searchCourse(Stage primaryStage) {
		
		searchField.setOnAction(event -> {
			
			resultsTable.getItems().clear();
			CourseManager cm = new CourseManager();
			for (Course course : cm.getCoursesByKeyword(searchField.getText())) {
				resultsTable.getItems().add(course);
			}
		});
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
		resultsTable.setStyle("-fx-font-family:" + this.model.getFontPreference().getFamily() + ";" 
				+ "-fx-font-weight:" + this.model.getFontPreference().getWeight() + ";"
				+ "-fx-font-style:" + fontStyle + ";"
				+ "-fx-font-size:" + this.model.getFontPreference().getSize()[2]);
		topFont.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[0]));
	}
	
//	This method sets the text colour for this scene by applying the colour preferences from the
//	user's font preferences in the Model class.
	
	public void setColor() {
		
		topFont.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		otherFont.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		searchField.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		courseName.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		capacity.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		year.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		deliveryMode.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		dayOfLecture.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		timeOfLecture.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		duration.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		enrol.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		courseNameColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		capacityColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		yearColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		deliveryModeColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		dayColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		timeColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		durationColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		enrolColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		goBackButton.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
	}
	
//	These two methods print out confirmation messages (in green) or error messages (in red).
	
	public void printConfirmation(String message) {
		
		messagePrinter.setText(message);
		messagePrinter.setTextFill(Color.GREEN);
	}
	
	public void printError(String message) {
		
		messagePrinter.setText(message);
		messagePrinter.setTextFill(Color.RED);
	}
}
