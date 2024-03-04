package MyTimeTable.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import MyTimeTable.Model;
import MyTimeTable.PasswordHasher;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

//	Controls the edit profile scene where the user can choose to update their first name, last name and password.

public class EditProfileController {

	@FXML
	private GridPane root;
	@FXML
	private Label topFont;
	@FXML
	private Label sceneFont;
	@FXML
	private Label newFirstNameFont;
	@FXML
	private Label newLastNameFont;
	@FXML
	private Label newPasswordFont;
	@FXML
	private TextField newFirstName;
	@FXML 
	private TextField newLastName;
	@FXML
	private PasswordField newPassword;
	@FXML 
	private Button updateFirstNameButton;
	@FXML
	private Button updateLastNameButton;
	@FXML
	private Button updatePasswordButton;
	@FXML
	private Button goBackButton;
	@FXML
	private Label messagePrinter;
	private Scene scene;
	private Model model;

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public EditProfileController(Model model) {
		this.model = model;
	}
	
//	This method below updates the user's first name with what they have entered in the password field and stores it in the database.

	
	public void updateFirstName() {
		
		updateFirstNameButton.setOnAction(event -> {
			
			if (newFirstName.getText().equals("")) {
				printError("First name cannot be blank! Try again.");
			}
			else {
				this.model.getUserDao().updateFirstName(newFirstName.getText(), this.model.getCurrentUser());
				printConfirmation("Your first name has been successfully updated to '" + newFirstName.getText() + "'!");
			}
		});

	}
	
//	This method below updates the user's last name with what they have entered in the password field and stores it in the database.
	
	public void updateLastName() {
		
		updateLastNameButton.setOnAction(event -> {
			
			if (newLastName.getText().equals("")) {
				printError("Last name cannot be blank! Try again.");
			}
			else {
				this.model.getUserDao().updateLastName(newLastName.getText(), this.model.getCurrentUser());
				printConfirmation("Your last name has been successfully updated to '" + newLastName.getText() + "'!");
			}
		});

	}
	
//	This method below updates the user's password with what they have entered in the password field. It calls the password-hashing
//	method before storing the new password in the database.

	public void updatePassword() {
		
		updatePasswordButton.setOnAction(event -> {
			
			if (newPassword.getText().equals("")) {
				printError("Password cannot be blank! Try again.");
			}
			else {
				try {
					PasswordHasher ph = new PasswordHasher();
					String hashedPassword = ph.hashPassword(newPassword.getText());
					this.model.getUserDao().updatePassword(hashedPassword, this.model.getCurrentUser());
					printConfirmation("Your password has been successfully updated!");
				} catch (NoSuchAlgorithmException e) {
					System.err.println(e.getMessage());
				}
			}
		});
	}
	
//	Brings the user back to the pre-login scene.
	
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
		topFont.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[0]));
		sceneFont.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[1]));
		newFirstNameFont.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[2]));
		newLastNameFont.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[2]));
		newPasswordFont.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[2]));
		messagePrinter.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[2]));
	}
	
//	This method sets the text colour for this scene by applying the colour preferences from the
//	user's font preferences in the Model class.
	
	public void setColor() {
		
		topFont.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		sceneFont.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		newFirstNameFont.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		newLastNameFont.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		newPasswordFont.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		newFirstName.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		newLastName.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		newPassword.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		updateFirstNameButton.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		updateLastNameButton.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		updatePasswordButton.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
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
