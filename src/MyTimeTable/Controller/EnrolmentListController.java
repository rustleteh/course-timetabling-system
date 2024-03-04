package MyTimeTable.Controller;

import java.io.IOException;

import MyTimeTable.Course;
import MyTimeTable.CourseManager;
import MyTimeTable.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

//	Controls the scene where the user can see their enrolled courses printed out in a list format. 
//	The list will contain details such as course name, year, delivery mode, day and time. 

public class EnrolmentListController {
	
	@FXML
	private GridPane root;
	@FXML
	private Label topFont;
	@FXML
	private Label otherFont;
	@FXML
	private Label courseName;
	@FXML
	private Label year;
	@FXML
	private Label deliveryMode;
	@FXML
	private Label dayOfLecture;
	@FXML
	private Label timeOfLecture;
	@FXML
	private ListView<String> nameList;
	@FXML
	private ListView<String> yearList;
	@FXML
	private ListView<String> deliveryModeList;
	@FXML
	private ListView<String> dayList;
	@FXML
	private ListView<String> timeList;
	@FXML
	private Button goBackButton;
	private Scene scene;
	private Model model;

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public EnrolmentListController(Model model) {
		this.model = model;
	}
	
//	The method below prints out each of the user's enrolled courses into the list view in the scene. 
	
	public void printCourseName(Stage primaryStage) {
		
		String buttonColor = this.model.getFontPreference().getColor();
		CourseManager cm = new CourseManager();
		for (Course course : cm.getMyCourses()) {
			nameList.getItems().add(course.getName());
		}	
		nameList.setCellFactory(param -> new ListCell<String>() {
	        @Override
	        protected void updateItem(String item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty) {
	                setText(null);
	            } 
	            else {
	                setText(item);
	            	setStyle("-fx-text-fill:" + buttonColor);
	            }
	        }
	    });
	}
	
	public void printCourseYear(Stage primaryStage) {
		
		String buttonColor = this.model.getFontPreference().getColor();
		CourseManager cm = new CourseManager();
		for (Course course : cm.getMyCourses()) {
			yearList.getItems().add(course.getYear());
		}	
		yearList.setCellFactory(param -> new ListCell<String>() {
	        @Override
	        protected void updateItem(String item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty) {
	                setText(null);
	            } 
	            else {
	                setText(item);
	            	setStyle("-fx-text-fill:" + buttonColor);
	            }
	        }
	    });
	}
	
	public void printCourseDeliveryMode(Stage primaryStage) {
		
		String buttonColor = this.model.getFontPreference().getColor();
		CourseManager cm = new CourseManager();
		for (Course course : cm.getMyCourses()) {
			deliveryModeList.getItems().add(course.getDeliveryMode());
		}	
		deliveryModeList.setCellFactory(param -> new ListCell<String>() {
	        @Override
	        protected void updateItem(String item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty) {
	                setText(null);
	            } 
	            else {
	                setText(item);
	            	setStyle("-fx-text-fill:" + buttonColor);
	            }
	        }
	    });
	}
	
	public void printCourseDay(Stage primaryStage) {
		
		String buttonColor = this.model.getFontPreference().getColor();
		CourseManager cm = new CourseManager();
		for (Course course : cm.getMyCourses()) {
			dayList.getItems().add(course.getDayOfLecture());
		}	
		dayList.setCellFactory(param -> new ListCell<String>() {
	        @Override
	        protected void updateItem(String item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty) {
	                setText(null);
	            } 
	            else {
	                setText(item);
	            	setStyle("-fx-text-fill:" + buttonColor);
	            }
	        }
	    });
	}
	
	public void printCourseTime(Stage primaryStage) {
		
		String buttonColor = this.model.getFontPreference().getColor();
		CourseManager cm = new CourseManager();
		for (Course course : cm.getMyCourses()) {
			timeList.getItems().add(course.getTimeOfLecture());
		}	
		timeList.setCellFactory(param -> new ListCell<String>() {
	        @Override
	        protected void updateItem(String item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty) {
	                setText(null);
	            } 
	            else {
	                setText(item);
	            	setStyle("-fx-text-fill:" + buttonColor);
	            }
	        }
	    });
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
				dbc.withdrawCourse(primaryStage);
				dbc.viewEnrolmentList(primaryStage);
				dbc.viewEnrolmentTimetable(primaryStage);
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
		nameList.setStyle("-fx-font-family:" + this.model.getFontPreference().getFamily() + ";" 
				+ "-fx-font-weight:" + this.model.getFontPreference().getWeight() + ";"
				+ "-fx-font-style:" + fontStyle + ";"
				+ "-fx-font-size:" + this.model.getFontPreference().getSize()[2]);
		yearList.setStyle("-fx-font-family:" + this.model.getFontPreference().getFamily() + ";" 
				+ "-fx-font-weight:" + this.model.getFontPreference().getWeight() + ";"
				+ "-fx-font-style:" + fontStyle + ";"
				+ "-fx-font-size:" + this.model.getFontPreference().getSize()[2]);
		deliveryModeList.setStyle("-fx-font-family:" + this.model.getFontPreference().getFamily() + ";" 
				+ "-fx-font-weight:" + this.model.getFontPreference().getWeight() + ";"
				+ "-fx-font-style:" + fontStyle + ";"
				+ "-fx-font-size:" + this.model.getFontPreference().getSize()[2]);
		dayList.setStyle("-fx-font-family:" + this.model.getFontPreference().getFamily() + ";" 
				+ "-fx-font-weight:" + this.model.getFontPreference().getWeight() + ";"
				+ "-fx-font-style:" + fontStyle + ";"
				+ "-fx-font-size:" + this.model.getFontPreference().getSize()[2]);
		timeList.setStyle("-fx-font-family:" + this.model.getFontPreference().getFamily() + ";" 
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

	}
	
//	This method sets the text colour for this scene by applying the colour preferences from the
//	user's font preferences in the Model class.
	
	public void setColor() {
		
		topFont.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		otherFont.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		courseName.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		year.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		deliveryMode.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		dayOfLecture.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		timeOfLecture.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		goBackButton.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
	}

}
