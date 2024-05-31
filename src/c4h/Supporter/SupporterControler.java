package c4h.Supporter;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

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
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class SupporterControler implements Initializable {

	@FXML
	private AnchorPane supporterContainer;
	@FXML
	private Button StartViewbutton;
	@FXML
	private PasswordField password;
	@FXML
	private String path ="/config";
	@FXML
	private ImageView imageLogo;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		System.out.println("SupportControler");
		LogoImage();

	}
	/**
	 * Setzt das Bild für das ImageView.
	 */
	@FXML
	private void LogoImage() {
		Image modellFoto = new Image("/image/3s_logo_tex2t.png");
		imageLogo.setImage(modellFoto);
	}

	@FXML 
	private void supporter(ActionEvent event) throws IOException { 	
		// Passwort aus der Konfigurationsdatei laden
		System.out.println(getClass().getResource(path).getFile());
		Properties properties = new Properties();
		try (InputStream input = (getClass().getResourceAsStream(path)) ){

			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
			// Handle the exception (e.g., show an alert to the user)
			return;
		}

		String encryptedPassword = properties.getProperty("password");
		String ID = "schulsupportserv"; 

		String correctPassword = null;
		try {
			correctPassword = decrypt(encryptedPassword, ID);
		} catch (Exception e) {
			e.printStackTrace();
			// Handle the exception
			return;
		}

		// Hier das Password überprüfen
		String enteredPassword = password.getText();

		if (enteredPassword.equals(correctPassword)) {
			// Passwort ist korrekt
			Parent root = FXMLLoader.load(getClass().getResource("/c4h/Supporter/SupporterDesign2.fxml"));

			Scene scene = password.getScene();
			root.translateYProperty().set(scene.getHeight());

			AnchorPane parentContainer = (AnchorPane) password.getScene().getRoot();
			parentContainer.getChildren().add(root);

			Timeline timeline = new Timeline();
			KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
			KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
			timeline.getKeyFrames().add(kf);
			timeline.setOnFinished(t -> {
				parentContainer.getChildren().remove(supporterContainer);
			});
			timeline.play();
		} else {
			// Passwort ist falsch
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Falsches Passwort");
			alert.setHeaderText(null);
			alert.setContentText("Das eingegebene Passwort ist falsch. Bitte versuchen Sie es erneut.");
			alert.showAndWait();

			// Hier kannst du weitere Aktionen ausführen, z.B. das Passwortfeld leeren oder den Benutzer erneut auffordern, das Passwort einzugeben
			password.clear(); // Passwortfeld leeren
		}
	}

	public static String decrypt(String strToDecrypt, String secret) throws Exception {
		SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	}


	@FXML
	private void loadRoot(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/c4h/startView/StartView.fxml"));
		Scene currentScene = StartViewbutton.getScene();
		//browserContainer = (AnchorPane) currentScene.getRoot();

		// Initial position of the new root (below the current scene)
		root.translateYProperty().set(-currentScene.getHeight());
		supporterContainer.getChildren().add(root);

		// Animation to move current scene up
		Timeline currentSceneTimeline = new Timeline();
		KeyValue currentSceneKv = new KeyValue(supporterContainer.translateYProperty(), currentScene.getHeight(), Interpolator.EASE_IN);
		KeyFrame currentSceneKf = new KeyFrame(Duration.seconds(1), currentSceneKv);
		currentSceneTimeline.getKeyFrames().add(currentSceneKf);

		// Animation to move new root up
		Timeline newRootTimeline = new Timeline();
		KeyValue newRootKv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
		KeyFrame newRootKf = new KeyFrame(Duration.seconds(1), newRootKv);
		newRootTimeline.getKeyFrames().add(newRootKf);

		// Start the current scene animation and set up a listener to start the new root animation
		currentSceneTimeline.setOnFinished(t -> {
			//newRootTimeline.play();
		});

		// Remove the old scene after the new scene animation is finished
		newRootTimeline.setOnFinished(t -> {
			//browserContainer.getChildren().remove(StartViewbutton); // Remove button if necessary
			supporterContainer.getChildren().remove(supporterContainer.lookup("#oldSceneRoot")); // Assume old scene root has this ID
			supporterContainer.setTranslateY(0); // Reset translateY of the parent container
		});

		currentSceneTimeline.play();
	}

}
