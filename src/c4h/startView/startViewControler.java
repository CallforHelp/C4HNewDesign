package c4h.startView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import c4h.MainC4H;
import c4h.PcInformation.PcInformationControler;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Der Controller für die Startansicht.
 * Er verwaltet die Navigation und die Anzeige von Bildern.
 * 
 * @version 1.0
 */
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
	private AnchorPane startviewContainer;
	@FXML
	private String cssPath = "/application.css";

	// Instanzvariable zur Speicherung der geladenen Ansicht
	private Parent pcInformationView;

	/**
	 * Initialisiert den Controller.
	 * 
	 * @param url  die URL zur Initialisierung
	 * @param rb   das ResourceBundle zur Initialisierung
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		System.out.println("StartviewControler");
		setImage();
		closeUSage();
		exitButton.setOnAction(e -> MainC4H.closeStage(exitButton.getScene()));
	}

	private void closeUSage() {
		// TODO Auto-generated method stub
	PcInformationControler.closeUsage();
	
	}

	/**
	 * Setzt das Bild für das ImageView.
	 */
	private void setImage() {
		Image logo = new Image("/image/3s_logo_tex2t.png");
		image.setImage(logo);
	}

	/**
	 * Behandelt die Aktion des Exit-Buttons.
	 * 
	 * @param event  das ActionEvent
	 * @throws IOException  wenn ein Fehler beim Laden der FXML-Datei auftritt
	 */
	@FXML
	private void exitButton(ActionEvent event) throws IOException {
		System.out.println("Du drückst Exit-Button");

		Parent root = FXMLLoader.load(getClass().getResource("/c4h/startView/startView.fxml"));
		Scene scene = exitButton.getScene();
		root.translateYProperty().set(scene.getHeight());

		if (scene != null) {
			Stage stage = (Stage) scene.getWindow();
			stage.hide();
		}
	}

	/**
	 * Lädt die Supporter-Passwort-Ansicht.
	 * 
	 * @param event  das ActionEvent
	 * @throws IOException  wenn ein Fehler beim Laden der FXML-Datei auftritt
	 */
	@FXML
	private void supporterPassword(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/c4h/Supporter/SupporterDesign.fxml"));
		Scene scene = buttonSupporter.getScene();
		scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

		root.translateYProperty().set(scene.getHeight());
		startviewContainer.getChildren().add(root);

		Timeline timeline = new Timeline();
		KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
		KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
		timeline.getKeyFrames().add(kf);

		timeline.setOnFinished(t -> startviewContainer.getChildren());
		timeline.play();
	}

	/**
	 * Lädt die PC-Informationsansicht.
	 * 
	 * @param event  das ActionEvent
	 * @throws IOException  wenn ein Fehler beim Laden der FXML-Datei auftritt
	 */
	@FXML
	private void pcInformation(ActionEvent event) throws IOException {
		//if (pcInformationView == null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/c4h/PcInformation/PcInformation.fxml"));
			pcInformationView = loader.load();

			Scene scene = buttonPcInfo.getScene();
			scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

			pcInformationView.translateYProperty().set(scene.getHeight());
			startviewContainer.getChildren().add(pcInformationView);

			Stage stage = (Stage) scene.getWindow();
			stage.sceneProperty().addListener((observable, oldScene, newScene) -> {
				if (oldScene == null && newScene != null) {
					// Ausführen der Controllerfunktionen nach vollständigem Laden der Szene
				}
			});

			Timeline timeline = new Timeline();
			KeyValue kv = new KeyValue(pcInformationView.translateYProperty(), 0, Interpolator.EASE_IN);
			KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
			timeline.getKeyFrames().add(kf);

			timeline.setOnFinished(t -> startviewContainer.getChildren());
			timeline.play();
		//} else {
			//startviewContainer.getChildren().add(pcInformationView);
		//}
	}

	/**
	 * Lädt die Browseransicht.
	 * 
	 * @param event  das ActionEvent
	 * @throws IOException  wenn ein Fehler beim Laden der FXML-Datei auftritt
	 */
	@FXML
	private void loadBrowser(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/c4h/browser/Browser.fxml"));
		Scene scene = buttonSupport.getScene();
		scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

		root.translateYProperty().set(scene.getHeight());
		startviewContainer.getChildren().add(root);

		Timeline timeline = new Timeline();
		KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
		KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
		timeline.getKeyFrames().add(kf);

		timeline.setOnFinished(t -> startviewContainer.getChildren());
		timeline.play();
	}

	/**
	 * Lädt die Kiosk-Browseransicht.
	 * 
	 * @param event  das ActionEvent
	 * @throws IOException  wenn ein Fehler beim Laden der FXML-Datei auftritt
	 */
	@FXML
	private void loadKioskBrowser(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/c4h/kiosk/KioskBrowser.fxml"));
		Scene scene = buttonKiosk.getScene();
		scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
		root.translateYProperty().set(scene.getHeight());

		startviewContainer.getChildren().add(root);

		Timeline timeline = new Timeline();
		KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
		KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
		timeline.getKeyFrames().add(kf);
		timeline.setOnFinished(t -> startviewContainer.getChildren());
		timeline.play();
	}
}
