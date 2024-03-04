package MyTimeTable;

import javafx.fxml.FXMLLoader;
import java.sql.SQLException;

import MyTimeTable.Controller.PreLoginViewController;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

//	This main class first initialises the model instance for the MVC pattern.
//	When the application starts, the program will first set up tables in the SQLite database (if they do not exist). 
//	Then, it will read and process the courses from the course.csv file. 
//	Finally it will set the pre-login scene and load the GUI for the user to interact with.

public class Main extends Application {
	
	private Model model;

	public void init() {
		this.model = Model.getInstance();
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException, SQLException {
		 
		this.model.getUserDao().setUp();
		this.model.getUserFontDao().setUp();
		this.model.getCourseCapacityDao().setUp();
		ReadFile.readData("course.csv");
		this.model.getCourseCapacityDao().addCourseCapacities();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("View/PreLoginView.fxml"));
		PreLoginViewController plvc = new PreLoginViewController(this.model);
		loader.setController(plvc);
		GridPane gp = loader.load();
		Scene scene = new Scene(gp);
		primaryStage.setTitle("MyTimeTable - Start");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		plvc.setScene(scene);
		plvc.login(primaryStage);
		plvc.signup(primaryStage);
	} 
	
	public static void main(String[] args) {
		
		Application.launch(args);
	}
}
