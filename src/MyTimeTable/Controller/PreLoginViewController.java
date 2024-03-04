package MyTimeTable.Controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import MyTimeTable.CourseManager;
import MyTimeTable.Model;
import MyTimeTable.PasswordHasher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//	Controls the pre-login scene where the user can choose to log in with an existing account or be directed to the sign-up scene to 
//	create a new user account.

public class PreLoginViewController {
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button loginButton;
	@FXML 
	private Button signupButton;
	@FXML
	private Label messagePrinter;
	private Scene scene;
	private Model model;
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	

	public PreLoginViewController(Model model) {
		this.model = model;
	}
	
//	This method below allows the user to log in to the program. It firsts check that the entered username and password matches
//	what is already stored in the database before validating the login.
	
	public void login(Stage primaryStage) {
		
		loginButton.setOnAction(event -> {
			
			try {
				PasswordHasher ph = new PasswordHasher();
				if (!this.model.getUserDao().findUser(username.getText(), ph.hashPassword(password.getText()))) {
					printError("Incorrect login credentials! Please try again.");
				}
				else {
					try {
						this.model.setCurrentUser(username.getText());
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/DashboardView.fxml"));
						GridPane gp;
						String studentNo = this.model.getUserDao().getStudentNo(this.model.getCurrentUser());
						String firstName = this.model.getUserDao().getFirstName(this.model.getCurrentUser());
						String lastName = this.model.getUserDao().getLastName(this.model.getCurrentUser());
						CourseManager cm = new CourseManager();
						cm.getMyCourses().clear();
						cm.getMyCoursesNames().clear();
						this.model.getUserDao().restoreEnrolments(username.getText());
						this.model.getUserFontDao().initialiseNewUserFont();
						this.model.getUserFontDao().restoreUserFont();
						DashboardController dbc = new DashboardController(this.model);
						loader.setController(dbc);
						gp = loader.load();
						Scene scene = new Scene(gp);
						primaryStage.setTitle("MyTimeTable - User Dashboard");
						primaryStage.setScene(scene);
						primaryStage.setResizable(false);
						primaryStage.show();
						dbc.printUserDetails(studentNo, firstName, lastName);
						dbc.printConfirmation("Login successful - welcome!");
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
					}
					catch (IOException e) {
						System.err.println(e.getMessage());
					}
				}
			} catch (NoSuchAlgorithmException e) {
				System.err.println(e.getMessage());
			}
		});
	}
	
//	This method below brings the user to the sign-up scene where they can create a new user account.
	
	public void signup(Stage primaryStage) {
		
		signupButton.setOnAction(event -> {
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/SignUpView.fxml"));
				GridPane gp;
				SignUpViewController suvc = new SignUpViewController(this.model);
				loader.setController(suvc);
				gp = loader.load();
				Scene scene = new Scene(gp);
				primaryStage.setTitle("MyTimeTable - New User Sign Up");
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.show();
				suvc.submit(primaryStage);
				suvc.goBack(primaryStage);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		});
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
