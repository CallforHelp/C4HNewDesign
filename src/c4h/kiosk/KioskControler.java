package c4h.kiosk;

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
import javafx.concurrent.Worker;

public class KioskControler implements Initializable{

    @FXML
    private Button startSeiteButton;
    @FXML
    private AnchorPane Container;
	@FXML
    private WebView  Kioskbrowser = new WebView();
	@FXML
	private WebEngine webkit;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadBrowser();
	}
	@FXML
    private void loadRoot(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/c4h/startView/startView.fxml"));
        Scene scene = startSeiteButton.getScene();
        root.translateYProperty().set(scene.getWidth());

        AnchorPane parentContainer = (AnchorPane) startSeiteButton.getScene().getRoot();

        parentContainer.getChildren().add(root);


        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(Container);
        });
        timeline.play();
    }
	@FXML
	private void loadBrowser() {
		System.out.println("KioskSeite");
		
         webkit = Kioskbrowser.getEngine();
         webkit.load("https://www.141.91.183.36/bwebserver/kiosk/login");
        // webkit.load("https://www.google.de");
        // webkit.loadContent("https://141.91.183.36/bwebserver/kiosk/login");
         webkit.reload();
       //  Kioskbrowser.setFontScale(1);
         webkit.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
             if (newValue == Worker.State.SUCCEEDED) {
                 // Hier können Sie auf den geladenen Inhalt zugreifen
                 System.out.println("Seite geladen: " + webkit.getLocation());
             }
         });
    }
     
	

}
