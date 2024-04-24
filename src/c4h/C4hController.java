package c4h;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
	@FXML
	private Button openStartScene;
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
	private VBox vBox = new VBox();
	@FXML
	private Pane pane = new Pane();
	@FXML
	private WebEngine webEngine;
	@FXML
	WebView browser = new WebView(); 
	@FXML
	

	//Constructor
	
    public void initialize() throws IOException {

		list.setItems(items);
		setIndikator();
		setIndikator2();
    }
    @FXML
	private void openSartView() throws IOException {
		
		stage = (Stage) openStartScene.getScene().getWindow();
		AnchorPane root;
	    root = (AnchorPane) FXMLLoader.load(getClass().getResource("StartView.fxml"));
	    
	    Scene scene = new Scene(root);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);	    
	    System.out.println("Scene.fxml opened");
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
	    
        stage = (Stage) openLogin.getScene().getWindow();
        
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Scene1.fxml"));

     //   root.getChildren().add(browser);
	    Scene scene = new Scene(new Group());
	    
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    scene.setRoot(root);
	    stage.setScene(scene);	        
	    stage.show();
	    
	    System.out.println("scene1 opened");

	    }
	@FXML
	private void webview()throws IOException {
		  webEngine = browser.getEngine();
		  webEngine.load("https://fehlermeldung.3s-hamburg.de/index-login.php"); 
		  webEngine.load("www.141.91.183.36/bwebserver/kiosk/login"); 
	}

	@FXML
	private void setIndikator() throws IOException{
	     
	     OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	     Timeline timeline = new Timeline(new KeyFrame( Duration.millis(2500),
		     ae ->  indictor.setProgress(bean.getSystemCpuLoad())));
	timeline.setCycleCount(Animation.INDEFINITE);
	timeline.play();
	}
	 
	 private void setIndikator2() throws IOException{
		Thread rammonitor = new Thread() { 
			int RAM =100000	;
			@Override 
			
			public void run() { 
				Runtime rt = Runtime.getRuntime(); 
				Double usedKB = (double) ((rt.totalMemory() - rt.freeMemory()) / 1024); 
	            	System.out.println("Ram usage: " + usedKB/RAM); 
	            	indictor2.setProgress(usedKB/RAM);
	            	try { 
	            		Thread.sleep(500); 
	            	} catch (InterruptedException e) { 
	            		e.printStackTrace(); 
	            	} 

	           run(); 
	        } 
		}; 
		rammonitor.start(); 
	}

}
