package MyTimeTable.Controller;

import java.io.IOException;

import MyTimeTable.Model;

//	Allows the user to customise their font and text settings - including changing the font to two types: 
//	'Verdana' and 'Georgia', change the font style to Bold or Italic, increase or decrease the font size 
//	and change the colour of the text.

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class FontSettingsController {
	
	@FXML
	private GridPane root;
	@FXML
	private Button verdana;
	@FXML
	private Button georgia;
	@FXML
	private Button setDefault;
	@FXML
	private Button smaller;
	@FXML
	private Button regular;
	@FXML
	private Button larger;
	@FXML
	private Button bold;
	@FXML
	private Button italic;
	@FXML
	private Button black;
	@FXML
	private Button blue;
	@FXML
	private Button red;
	@FXML
	private Label topFont;
	@FXML
	private Label sceneFont;
	@FXML
	private Label otherFont;
	@FXML
	private Label otherFont2;
	@FXML
	private Label otherFont3;
	@FXML
	private Label otherFont4;
	@FXML
	private Button backButton;
	@FXML
	private Label messagePrinter;
	private Scene scene;
	private Model model;

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public FontSettingsController(Model model) {
		this.model = model;
	}
	
//	This method below sets the font type to 'Verdana'.
	
	public void setVerdana() {

		verdana.setOnAction(event -> {

			this.model.getFontPreference().setFamily("Verdana");
			setFont();
			printConfirmation("Font type changed to Verdana!");

		});
	}
	
//	This method below sets the font type to 'Georgia'.

	public void setGeorgia() {

		georgia.setOnAction(event -> {

			this.model.getFontPreference().setFamily("Georgia");
			setFont();
			printConfirmation("Font type changed to Georgia!");

		});
	}
	
//	This method below resets the font type to the default 'System' font.
	
	public void setDefault() {

		setDefault.setOnAction(event -> {

			this.model.getFontPreference().setFamily("System");
			this.model.getFontPreference().setWeight("NORMAL");
			this.model.getFontPreference().setPosture("REGULAR");
			setFont();
			printConfirmation("Font type has been reset to default!");

		});
	}
	
//	This method below slightly decreases the font size. 

	public void setSmaller() {

		smaller.setOnAction(event -> {
			double[] smallerFont = {22.0, 17.0, 11.0};
			this.model.getFontPreference().setSize(smallerFont);
			setFont();
			printConfirmation("Font size has been decreased!");
		});
	}
	
//	This method below resets the font size to the default size. 
	
	public void setRegular() {

		regular.setOnAction(event -> {
			double[] regularFont = {24.0, 18.0, 13.0};
			this.model.getFontPreference().setSize(regularFont);
			setFont();
			printConfirmation("Font size has been reset to default!");
		});
	}
	
//	This method below slightly increases the font size. 

	public void setLarger() {

		larger.setOnAction(event -> {
			double[] largerFont = {26.0, 20.0, 15.0};
			this.model.getFontPreference().setSize(largerFont);
			setFont();
			printConfirmation("Font color has been increased!");
		});
	}

//	This method below sets the font style to Bold when the user clicks the button and back to Regular 
//	when the button is clicked again.

	
	public void toggleBold() {

		bold.setOnAction(event -> {
			if (this.model.getFontPreference().getFamily().equals("System")) {
				printError("Bold style is not available for the default font!");
			}
			else if (this.model.getFontPreference().getWeight().equals("NORMAL")) {
				this.model.getFontPreference().setWeight("BOLD");
				setFont();
				printConfirmation("Bold toggled ON!");
			}
			else {
				this.model.getFontPreference().setWeight("NORMAL");
				setFont();
				printConfirmation("Bold toggled OFF!");
			}
		});
	}
	
//	This method below sets the font style to Italic when the user clicks the button and back to Regular 
//	when the button is clicked again.

	public void toggleItalic() {

		italic.setOnAction(event -> {
			if (this.model.getFontPreference().getFamily().equals("System")) {
				printError("Italic style is not available for the default font!");
			}
			else if (this.model.getFontPreference().getPosture().equals("REGULAR")) {
				this.model.getFontPreference().setPosture("Italic");
				setFont();
				printConfirmation("Italic toggled ON!");
			}
			else {
				this.model.getFontPreference().setPosture("REGULAR");
				setFont();
				printConfirmation("Italic toggled OFF!");
			}
		});
	}
	
//	This method below sets the colour of all text (except for confirmation and error messages) to blue.
	
	public void setBlue() {

		blue.setOnAction(event -> {
			this.model.getFontPreference().setColor("BLUE");
			setColor();
			printConfirmation("Font color has been set to blue!");
		});
	}
	
//	This method below sets the colour of all text (except for confirmation and error messages) to red.
	
	public void setRed() {

		red.setOnAction(event -> {
			this.model.getFontPreference().setColor("RED");
			setColor();
			printConfirmation("Font color has been set to red!");
		});
	}
	
//	This method below sets the colour of all text (except for confirmation and error messages) to black.

	public void setBlack() {

		black.setOnAction(event -> {
			this.model.getFontPreference().setColor("BLACK");
			setColor();
			printConfirmation("Font color has been set to black!");
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
		otherFont.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[2]));
		otherFont2.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[2]));
		otherFont3.setFont(Font.font(this.model.getFontPreference().getFamily(), 
				FontWeight.findByName(this.model.getFontPreference().getWeight()), 
				FontPosture.findByName(this.model.getFontPreference().getPosture()), 
				this.model.getFontPreference().getSize()[2]));
		otherFont4.setFont(Font.font(this.model.getFontPreference().getFamily(), 
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
		otherFont.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		otherFont2.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		otherFont3.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		otherFont4.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		verdana.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		georgia.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		setDefault.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		smaller.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		regular.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		larger.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		bold.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		italic.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		black.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		blue.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		red.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
		backButton.setStyle("-fx-text-fill:" + this.model.getFontPreference().getColor());
	}
	
//	Brings the user back to the pre-login scene.
	
	public void goBack(Stage primaryStage) {
		
		backButton.setOnAction(event -> {

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
