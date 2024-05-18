package c4h.startView;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import c4h.MainC4H;
import c4h.PcInformation.PcInformationControler;
import c4h.Supporter.SupporterControler;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;

public class startViewControler implements Initializable {
		
	
	@FXML
	private ImageView image;
	@FXML
	private Button buttonSupport;
	@FXML
	private Button buttonKiosk;
	@FXML
	private Button exitButton;
	@FXML
	private Button buttonPcInfo;
	@FXML
	private Button buttonSupporter;
	@FXML
	private AnchorPane parentContainer;
	@FXML
	private String cssPath="/application.css";
	
	// Instanzvariable zur Speicherung der geladenen Ansicht
	private Parent pcInformationView;
	private Parent supporterView;
	private Parent supporterViewPass;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		System.out.println("Startview");
		setImage();
		exitButton.setOnAction(e -> {
            MainC4H.closeStage(exitButton.getScene());
        });
	}
	
	private void setImage() {
		// TODO Auto-generated method stub
		
		Image logo = new Image("/image/3s_logo_tex2t.png");
		image.setImage(logo);
	}

	@FXML
	private void exitButton(ActionEvent event) throws IOException {
		System.out.println("du druckst Exit button");
		
		Parent root = FXMLLoader.load(getClass().getResource("/c4h/startView/startView.fxml"));
        Scene scene = exitButton.getScene();
        root.translateYProperty().set(scene.getHeight());
        
        if (scene != null) {
            // Get the stage from the scene
            Stage stage = (Stage) scene.getWindow();
            // Hide the stage
            stage.hide();;        
            }
		
	}
	@FXML
	private void supporterPassword(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/c4h/Supporter/SupporterDesign.fxml"));
		Scene scene = buttonSupporter.getScene();
		scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
	    
	    root.translateYProperty().set(scene.getHeight());

	    parentContainer.getChildren().add(root);

	    Timeline timeline = new Timeline();
	    KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
	    KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
	    timeline.getKeyFrames().add(kf);
	    
	    timeline.setOnFinished(t -> {
	    	parentContainer.getChildren().remove(parentContainer);
	    });
	    timeline.play();
		
	}

	
	
	@FXML
	private void pcInformation(ActionEvent event) throws IOException {
	    // Überprüfen, ob die Ansicht bereits geladen wurde
	    if (pcInformationView == null) {
	        // Laden der FXML-Ansicht
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/c4h/PcInformation/PcInformation.fxml"));
	        pcInformationView = loader.load();
	        
	        // Zugriff auf den geladenen Controller
	        PcInformationControler controller = loader.getController();
	        
	        // Zugriff auf die Szene des Buttons
	        Scene scene = buttonPcInfo.getScene();
	        scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
	        
	        pcInformationView.translateYProperty().set(scene.getHeight());
	        parentContainer.getChildren().add(pcInformationView);
	        
	        // Hinzufügen eines ChangeListeners zur sceneProperty der Stage
	        Stage stage = (Stage) scene.getWindow();
	        stage.sceneProperty().addListener((observable, oldScene, newScene) -> {
	            if (oldScene == null && newScene != null) {
	                // Szene wurde vollständig geladen
	           //     controller.processData(); // Ausführen der Controllerfunktionen
	            }
	        });
	        
	        Timeline timeline = new Timeline();
	        KeyValue kv = new KeyValue(pcInformationView.translateYProperty(), 0, Interpolator.EASE_IN);
	        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
	        timeline.getKeyFrames().add(kf);
	        
	        timeline.setOnFinished(t -> {
	            parentContainer.getChildren().remove(parentContainer);
	        });
	        timeline.play();
	    } else {
	        // Die Ansicht wurde bereits geladen, also füge sie einfach zum Container hinzu
	        parentContainer.getChildren().add(pcInformationView);
	    }
	}
	
	@FXML
	private void loadBrowser(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/c4h/browser/Browser.fxml"));
		Scene scene = buttonSupport.getScene();
		scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
	    
	    root.translateYProperty().set(scene.getHeight());

	    parentContainer.getChildren().add(root);

	    Timeline timeline = new Timeline();
	    KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
	    KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
	    timeline.getKeyFrames().add(kf);
	    
	    timeline.setOnFinished(t -> {
	    	parentContainer.getChildren().remove(parentContainer);
	    });
	    timeline.play();
	    }
	
	
	@FXML
	private void loadKioskBrowser(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/c4h/kiosk/KioskBrowser.fxml"));
	    Scene scene = buttonKiosk.getScene();
	    scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
	    root.translateYProperty().set(scene.getHeight());

	    parentContainer.getChildren().add(root);

	    Timeline timeline = new Timeline();
	    KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
	    KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
	    timeline.getKeyFrames().add(kf);
	    timeline.setOnFinished(t -> {
	    	parentContainer.getChildren().remove(parentContainer);
	    });
	    timeline.play();
	    }   

	}
