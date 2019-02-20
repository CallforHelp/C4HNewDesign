package c4h;

import java.io.IOException;


import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class C4hController {
	@FXML
	private Button myExitButton;
	String imageDecline = "/C4HNewDesign/src/images/decline-button.png";
	@FXML
	private Stage stage;
    @FXML
    private Button openScene1;
    @FXML
    private Button openLogin;

	
	
	//Constructor
	
    public void initialize() {
		//	myExitButton.setGraphic(new ImageView(imageDecline));
		RotateTransition rotation = new RotateTransition(Duration.seconds(0.5), myExitButton);
		rotation.setCycleCount(Animation.INDEFINITE);
		rotation.setByAngle(360);
		myExitButton.setOnMouseEntered(e -> rotation.play());
		myExitButton.setOnMouseExited(e -> rotation.pause());
		
		myExitButton.setOnAction((event) -> {
			System.out.println("Button Action\n");
			System.exit(0);
		});

    }
    
	@FXML
	private void openStage2() throws IOException {
		stage = (Stage) openScene1.getScene().getWindow();
	        AnchorPane root;
	        root = (AnchorPane) FXMLLoader.load(getClass().getResource("Scene1.fxml"));
	        Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        stage.setScene(scene);
	        System.out.println("Scene.fxml opened");
	}
	
	 @FXML
	    private void openLogin() throws IOException{
	        stage = (Stage) openLogin.getScene().getWindow();
	        AnchorPane root;
	        root = (AnchorPane) FXMLLoader.load(getClass().getResource("initC4HRootLayout.fxml"));
	        Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        stage.setScene(scene);

	        System.out.println("Login.fxml opened");
	    }
}
