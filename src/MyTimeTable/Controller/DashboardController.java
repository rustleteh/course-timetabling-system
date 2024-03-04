package MyTimeTable.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import MyTimeTable.CourseManager;
import MyTimeTable.Model;
import MyTimeTable.WriteToFile;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

//	Controls all the functions on the main User Dashboard once the user has logged in.

public class DashboardController {
	
	@FXML
	private GridPane root;
	@FXML
	private Label topFont;
	@FXML
	private Label studentNoFont;
	@FXML
	private Label firstNameFont;
	@FXML
	private Label lastNameFont;
	@FXML
	private Label profile;
	@FXML
	private Label courses;
	@FXML
	private Label enrolments;
	@FXML
	private Label settings;
	@FXML
	private MenuItem editProfile;
	@FXML 
	private MenuItem viewAllCourses;
	@FXML
	private MenuItem searchEnrol;
	@FXML 
	private MenuItem viewEnrolmentList;
	@FXML 
	private MenuItem viewEnrolmentTimetable;
	@FXML
	private MenuItem withdraw; 
	@FXML
	private MenuItem export;
	@FXML 
	private MenuItem logout;
	@FXML 
	private MenuItem changeFont;
	@FXML 
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML 
	private Label studentNoLabel;
	@FXML 
	private Label messagePrinter;
	private Scene scene;
	private Model model;

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public DashboardController(Model model) {
		this.model = model;
	}
	
//	Prints the user's student number, first name and last name to the top of the dashboard. 
	
	public void printUserDetails(String studentNo, String firstName, String lastName) {
		studentNoLabel.setText(studentNo);
		firstNameLabel.setText(firstName);
		lastNameLabel.setText(lastName);
	}
	
	public void editProfile(Stage primaryStage) {
		
		editProfile.setOnAction(event -> {
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/EditProfileView.fxml"));
				GridPane gp;
				EditProfileController epc = new EditProfileController(this.model);
				loader.setController(epc);
				gp = loader.load();
				Scene scene = new Scene(gp);
				primaryStage.setTitle("MyTimeTable - Dashboard - Edit Profile");
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.show();
				epc.setFont();
				epc.setColor();
				epc.setScene(scene);
				epc.updateFirstName();
				epc.updateLastName();
				epc.updatePassword();
				epc.goBack(primaryStage);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		});
	}
	
	public void viewAllCourses(Stage primaryStage) {
		
		viewAllCourses.setOnAction(event -> {
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ViewAllCoursesView.fxml"));
				GridPane gp;
				ViewAllCoursesController vacc = new ViewAllCoursesController(this.model);
				loader.setController(vacc);
				gp = loader.load();
				Scene scene = new Scene(gp);
				primaryStage.setTitle("MyTimeTable - Dashboard - View All Courses");
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.show();
				vacc.setScene(scene);
				vacc.setFont();
				vacc.setColor();
				vacc.setUpTables(primaryStage);
				vacc.printCourses(primaryStage);
				vacc.goBack(primaryStage);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		});
	}
	
	public void searchEnrol(Stage primaryStage) {
		
		searchEnrol.setOnAction(event -> {
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/SearchEnrolView.fxml"));
				GridPane gp;
				SearchEnrolController sec = new SearchEnrolController(this.model);
				loader.setController(sec);
				gp = loader.load();
				Scene scene = new Scene(gp);
				primaryStage.setTitle("MyTimeTable - Dashboard - Search Courses");
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.show();
				sec.setScene(scene);
				sec.setFont();
				sec.setColor();
				sec.setUpResultsTable(primaryStage);
				sec.searchCourse(primaryStage);
				sec.goBack(primaryStage);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		});
	}
	
	public void withdrawCourse(Stage primaryStage) {
		
		withdraw.setOnAction(event -> {
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/WithdrawView.fxml"));
				GridPane gp;
				WithdrawController wc = new WithdrawController(this.model);
				loader.setController(wc);
				gp = loader.load();
				Scene scene = new Scene(gp);
				primaryStage.setTitle("MyTimeTable - Dashboard - Search Courses");
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.show();
				wc.setScene(scene);
				wc.setFont();
				wc.setColor();
				wc.setUpResultsTable(primaryStage);
				wc.goBack(primaryStage);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		});
	}
	
	public void viewEnrolmentList(Stage primaryStage) {
		
		viewEnrolmentList.setOnAction(event -> {
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/EnrolmentListView.fxml"));
				GridPane gp;
				EnrolmentListController elc = new EnrolmentListController(this.model);
				loader.setController(elc);
				gp = loader.load();
				Scene scene = new Scene(gp);
				primaryStage.setTitle("MyTimeTable - Dashboard - Enrolment List");
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.show();
				elc.setFont();
				elc.setColor();
				elc.setScene(scene);
				elc.printCourseName(primaryStage);
				elc.printCourseYear(primaryStage);
				elc.printCourseDeliveryMode(primaryStage);
				elc.printCourseDay(primaryStage);
				elc.printCourseTime(primaryStage);
				elc.goBack(primaryStage);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		});
	}
	
	public void viewEnrolmentTimetable(Stage primaryStage) {
		
		viewEnrolmentTimetable.setOnAction(event -> {
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/EnrolmentTimetableView.fxml"));
				GridPane gp;
				EnrolmentTimetableController etc = new EnrolmentTimetableController(this.model);
				loader.setController(etc);
				gp = loader.load();
				Scene scene = new Scene(gp);
				primaryStage.setTitle("MyTimeTable - Dashboard - Enrolment Timetable");
				primaryStage.setResizable(false);
				primaryStage.setScene(scene);
				primaryStage.show();
				etc.setScene(scene);
				etc.setFont();
				etc.setColor();
				etc.setUp();
				etc.generateTimetable();
				etc.goBack(primaryStage);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		});
	}
	
//	Exports the user's currently enrolled courses to a .csv file.
	
	public void export() {
		
		export.setOnAction(event -> {
			
			WriteToFile.exportEnrolments(this.model.getUserDao().getStudentNo(this.model.getCurrentUser())+" - Enrolments.csv");
			printConfirmation("Your enrolled courses have been exported to a file named \"[Your Student Number] - Enrolments.csv\"!");
		});
	}
	
	public void changeFont(Stage primaryStage) {
		
		changeFont.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ChangeFontView.fxml"));
				GridPane gp;
				FontSettingsController fsc = new FontSettingsController(this.model);
				loader.setController(fsc);
				gp = loader.load();
				Scene scene = new Scene(gp);
				primaryStage.setTitle("MyTimeTable - Dashboard - Font and Text Settings");
				primaryStage.setResizable(false);
				primaryStage.setScene(scene);
				primaryStage.show();
				fsc.setFont();
				fsc.setColor();
				fsc.setScene(scene);
				fsc.setVerdana();
				fsc.setGeorgia();
				fsc.setDefault();
				fsc.setSmaller();
				fsc.setRegular();
				fsc.setLarger();
				fsc.toggleBold();
				fsc.toggleItalic();
				fsc.setBlue();
				fsc.setBlack();
				fsc.setRed();
				fsc.goBack(primaryStage);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		});
	}
	
	
	
//	Logs the user out of the program and returns them back to the pre-login scene.
	
	public void logout(Stage primaryStage) {
		
		logout.setOnAction(event -> {
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/PreLoginView.fxml"));
				GridPane gp;
				PreLoginViewController plvc = new PreLoginViewController(this.model);
				loader.setController(plvc);
				gp = loader.load();
				Scene scene = new Scene(gp);
				primaryStage.setTitle("MyTimeTable - Start");
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.show();
				plvc.printConfirmation("You have successfully logged out!");
				this.model.getCourseCapacityDao().updateCourseCapacity();
				this.model.getUserFontDao().saveUserFont();
				this.model.setCurrentUser(null);
				CourseManager cm = new CourseManager();
				cm.getMyCourses().clear();
				cm.getMyCoursesNames().clear();
				plvc.setScene(scene);
				plvc.login(primaryStage);
				plvc.signup(primaryStage);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		});
	}
	
	public void setFont() {
		String fontStyle = this.model.getFontPreference().getPosture();
		if (this.model.getFontPreference().getPosture().equals("REGULAR")) {
			fontStyle = "NORMAL";
		}
		root.setStyle("-fx-font-family:" + this.model.getFontPreference().getFamily() + ";" 
					+ "-fx-font-weight:" + this.model.getFontPreference().getWeight() + ";"
					+ "-fx-font-style:" + fontStyle + ";"
					+ "-fx-font-size:" + this.model.getFontPreference().getSize()[2]);
		topFont.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[0]));
		studentNoFont.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[2]));
		firstNameFont.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[2]));
		lastNameFont.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[2]));
		firstNameLabel.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[2]));
		lastNameLabel.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[2]));
		studentNoLabel.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[2]));
		messagePrinter.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[2]));
	}
	
	public void setColor() {
		topFont.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		profile.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		courses.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		enrolments.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		settings.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		studentNoFont.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		firstNameFont.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		lastNameFont.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		firstNameLabel.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		lastNameLabel.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		studentNoLabel.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		editProfile.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		viewAllCourses.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		searchEnrol.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		viewEnrolmentList.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		viewEnrolmentTimetable.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		withdraw.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		export.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		logout.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		changeFont.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
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
