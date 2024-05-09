package c4h.startView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import c4h.MainC4H;
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
import javafx.scene.control.Button;
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
	private AnchorPane parentContainer;
	@FXML
	private String cssPath="/application.css";
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
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
	private void pcInformation(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/c4h/PcInformation/PcInformation.fxml"));
		Scene scene = buttonPcInfo.getScene();
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
