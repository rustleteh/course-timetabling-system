package MyTimeTable.Controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

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

//	Controls the scene in which the user can sign up for a new account. 

public class SignUpViewController {
	
	@FXML
	private TextField newUsername;
	@FXML
	private PasswordField newPassword;
	@FXML
	private TextField studentNo;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private Label messagePrinter;
	@FXML
	private Button submitButton;
	@FXML
	private Button goBackButton;
	private Scene scene;
	private Model model;

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public SignUpViewController(Model model) {
		this.model = model;
	}
	
//	This method first runs some other methods from the UserDao class to check for duplicate usernames and student numbers. 
//	If there are no duplicates, it stores the new user details into the database before bringing the user back to the pre-login scene.

	public void submit(Stage primaryStage) {
		
		submitButton.setOnAction(event -> {
			
			try {
				if (this.model.getUserDao().checkDuplicateUsername(newUsername.getText()) == true) {
					printError("Username already taken! Please try again.");
				}
				else if (this.model.getUserDao().checkDuplicateStudentNo(studentNo.getText()) == true) {
					printError("An account with this student number already exists! Please try again.");
				}
				else {
					PasswordHasher ph = new PasswordHasher();
					String hashedPassword = ph.hashPassword(newPassword.getText());
					this.model.getUserDao().addUser(newUsername.getText(), hashedPassword, studentNo.getText(), firstName.getText(), lastName.getText());
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
					plvc.printConfirmation("New user account successfully created!");
					plvc.setScene(scene);
					plvc.login(primaryStage);
					plvc.signup(primaryStage);
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
			} catch (NoSuchAlgorithmException e) {
				System.err.println(e.getMessage());
			}
			
		});
	}
	
//	Brings the user back to the pre-login scene.
	
	public void goBack(Stage primaryStage) {
		
		goBackButton.setOnAction(event -> {
			
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
				plvc.setScene(scene);
				plvc.login(primaryStage);
				plvc.signup(primaryStage);
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
