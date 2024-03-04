package MyTimeTable.Controller;

import java.io.IOException;

import MyTimeTable.Course;
import MyTimeTable.CourseManager;
import MyTimeTable.Model;
import MyTimeTable.Dao.UserDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage; 

//	Allows users to withdraw from an enrolled course.

public class WithdrawController {
	
	@FXML
	private GridPane root;
	@FXML
	private Label topFont;
	@FXML
	private Label otherFont;
	@FXML
	private TableView<Course> enrolmentsTable;
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
	private TableColumn<Course, Course> withdrawColumn;
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
	private Label withdraw;
	@FXML
	private Label messagePrinter;
	@FXML
	private Button goBackButton;
	private Scene scene;
	private Model model;

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public WithdrawController(Model model) {
		this.model = model;
	}
	
//	This method below first prints out all the user's currently-enrolled courses to a table. 
//	It then sets up a 'Withdraw' button on the final column cell of each row, which allows the user to withdraw from that course.
	
	public void setUpResultsTable(Stage primaryStage) {
		
		String currentUser = this.model.getCurrentUser();
		
		courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
		deliveryModeColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryMode"));
		dayColumn.setCellValueFactory(new PropertyValueFactory<>("dayOfLecture"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeOfLecture"));
		durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
		enrolmentsTable.getItems().clear();
		CourseManager cm = new CourseManager();
		for (Course course : cm.getMyCourses()) {
			enrolmentsTable.getItems().add(course);
		}
		String buttonColor = this.model.getFontPreference().getColor();
		withdrawColumn.setCellFactory(column -> new TableCell<Course, Course>() {
			private Button withdrawButton = new Button("Withdraw");
			{
				withdrawButton.setOnAction(event -> {
					Course course = getTableRow().getItem();
					cm.removeEnrolledCourse(course.getName());
					UserDao userDao = new UserDao();
					userDao.updateEnrolments(currentUser, cm.getMyCoursesNames());
					printConfirmation("You have successfully withdrawn from '" + course.getName() + "'!");
					setUpResultsTable(primaryStage);
				});
			}	
			@Override
	        public void updateItem(Course value, boolean empty) {
	            super.updateItem(value, empty);
	            if (empty) {
	                setGraphic(null);
	            } 
	            else {
	                setGraphic(withdrawButton);
	                withdrawButton.setPrefWidth(82.5);
	                withdrawButton.setStyle("-fx-text-fill:" + buttonColor);
	            }
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
		enrolmentsTable.setStyle("-fx-font-family:" + this.model.getFontPreference().getFamily() + ";" 
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
		courseNameColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		capacityColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		yearColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		deliveryModeColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		dayColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		timeColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		durationColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		withdrawColumn.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		courseName.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		capacity.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		year.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		deliveryMode.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		dayOfLecture.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		timeOfLecture.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		duration.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		withdraw.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		goBackButton.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
	}

	
//	This method below prints out a confirmation message to the scene.
	
	public void printConfirmation(String message) {
		messagePrinter.setText(message);
		messagePrinter.setTextFill(Color.GREEN);
	}
}
