package c4h;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.sound.sampled.LineListener;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.sun.management.OperatingSystemMXBean;

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
	@FXML
	ListView<String> list = new ListView<String>();
	@FXML
	ObservableList<String> items =FXCollections.observableArrayList ("Single", "Double", "Suite", "Family App");
	@FXML
	private Label label;
	@FXML
	private ProgressIndicator indictor = new ProgressIndicator(0);
	@FXML
	private ProgressIndicator indictor2 = new ProgressIndicator(0);
	@FXML
	private WebView browser = new WebView();
	@FXML
	private WebEngine webkit  = browser.getEngine();


	//Constructor
	
    public void initialize() throws IOException {

    	RotateTransition rotation = new RotateTransition(Duration.seconds(0.5), myExitButton);
		rotation.setCycleCount(Animation.INDEFINITE);
		rotation.setByAngle(360);
		
		myExitButton.setOnMouseEntered(e -> rotation.play());
		myExitButton.setOnMouseExited(e -> rotation.pause());
		
		myExitButton.setOnAction((event) -> {
			System.out.println("Button Action\n");
			System.exit(0);
		});

		list.setItems(items);
		setIndikator();
		setIndikator2();

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
	@FXML
	private void openScene1() throws IOException{
	    
	    AnchorPane root;
	    stage = (Stage) openLogin.getScene().getWindow();
	    root = (AnchorPane) FXMLLoader.load(getClass().getResource("Scene1.fxml"));
	 
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("ButtonRounded.css").toExternalForm());
            stage.setScene(scene);  
             
            // Set up the embedded browser:
            browser = new WebView();
            webkit = browser.getEngine();
            webkit.load("http://www.google.de");
            browser.setFontScale(1);

            ObservableList<Node> children = root.getChildren();
            children.add(browser);             
            
	   /* stage = (Stage) openLogin.getScene().getWindow();
	    AnchorPane root;
	
	        root = (AnchorPane) FXMLLoader.load(getClass().getResource("Scene1.fxml"));
	        Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("ButtonRounded.css").toExternalForm());
	        stage.setScene(scene);
/*	        webkit.load("http://www.google.de");
	        root.getChildren().add(browser);*/
	        stage.show();
	        System.out.println("scene1 opened");

	    }

	 @SuppressWarnings("restriction")
	@FXML
	private void setIndikator() throws IOException{
	     
	     OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	     Timeline timeline = new Timeline(new KeyFrame( Duration.millis(2500),
		     ae ->  indictor.setProgress(bean.getSystemCpuLoad())));
	timeline.setCycleCount(Animation.INDEFINITE);
	timeline.play();
	}
	 
	 @SuppressWarnings("restriction")
	private void setIndikator2() throws IOException{
	    OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		     Timeline timeline = new Timeline(new KeyFrame( Duration.millis(2500),
			     ae ->  indictor2.setProgress(bean.getProcessCpuTime())));
		     System.out.println(bean.getVersion());
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

}
