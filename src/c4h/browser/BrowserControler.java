package c4h.browser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

public class BrowserControler implements Initializable{

    @FXML
    private Button StartViewbutton;
    @FXML
    private Parent browserContainer;
	@FXML
    private WebView  browser = new WebView();
	@FXML
	private WebEngine webkit;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadBrowser();
	}
	@FXML
    private void loadRoot(ActionEvent event) throws IOException {
        
 	Parent root = FXMLLoader.load(getClass().getResource("/c4h/startView/StartView.fxml"));
        
    	Scene scene = StartViewbutton.getScene();
        root.translateYProperty().set(scene.getHeight());

        AnchorPane parentContainer = (AnchorPane) StartViewbutton.getScene().getRoot();

        parentContainer.getChildren().add(root);


        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(browserContainer);
        });
        timeline.play();
    }
	@FXML
	private void loadBrowser() {
		System.out.println("browserstart");
		
         webkit = browser.getEngine();
         webkit.load("https://fehlermeldung.3s-hamburg.de");
         browser.setFontScale(1);
         browser.setPageFill(javafx.scene.paint.Color.TRANSPARENT);
         
    }
     
	

}
