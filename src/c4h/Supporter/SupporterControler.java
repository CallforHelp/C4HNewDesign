package c4h.Supporter;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Worker;
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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

public class SupporterControler implements Initializable {

	//public String URL = "https://fehlermeldung.3s-hamburg.de/mitarbeiter/";
	public String URL = "https://www.google.de";
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
	@FXML
	private AnchorPane browserContainer;
	@FXML
	private WebView  browser = new WebView();
	@FXML
	private WebEngine webkit;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		System.out.println("SupportControler");
		//LogoImage();
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
			loadBrowser();
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
	@FXML
	private void loadBrowser() {
		System.out.println("browserstart");

		try {
			// TrustManager-Array initialisieren, um das Serverzertifikat zu überprüfen
			TrustManager[] trustAllCerts = new TrustManager[]{
					new X509TrustManager() {
						public X509Certificate[] getAcceptedIssuers() {
							return null;
						}

						public void checkClientTrusted(X509Certificate[] certs, String authType) {}

						public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
							for (@SuppressWarnings("unused") X509Certificate cert : certs) {
								// Hier können Sie die Zertifikatsprüfung anpassen, z. B. Überprüfung des Ausstellers, Gültigkeitszeitraums usw.
								// Beispiel: cert.getIssuerDN().getName() für den Aussteller
								// Beispiel: cert.getNotAfter() für das Ablaufdatum
							}
						}
					}
			};

			// SSL-Kontext initialisieren
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

			// SSL-Socket-Fabrik initialisieren
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

			// Verbindung zur URL herstellen
			URL url = new URL(URL);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			// Antwortcode abrufen
			int responseCode = connection.getResponseCode();
			System.out.println("Response Code: " + responseCode);
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// JavaScript aktivieren
		webkit = browser.getEngine();
		webkit.setJavaScriptEnabled(true);

		// User-Agent setzen
		webkit.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.71 Safari/537.36");

		// Fehlerbehandlung für WebView hinzufügen
		webkit.setOnError(event -> {
			System.out.println("Fehler beim Laden der Seite: " + event.getMessage());
		});

		String userDataDirectory=System.getProperty("user.home") + File.separator + "AppData" + File.separator + "Roaming"+"\\c4h.MainC4H\\webview";
		System.out.println(userDataDirectory);
		System.setProperty("user.home", userDataDirectory);

		// Ereignis zum Überwachen des Ladezustands der WebEngine
		webkit.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == Worker.State.SUCCEEDED) {
				// Wenn die Seite geladen ist, Lazy-Loading von Bildern implementieren
				webkit.executeScript(
						"var lazyImages = document.querySelectorAll('img[data-src]');" +
								"lazyImages.forEach(function(img) {" +
								"  img.setAttribute('src', img.getAttribute('data-src'));" +
								"  img.onload = function() {" +
								"    img.removeAttribute('data-src');" +
								"  };" +
								"});"
						);

				System.out.println("Webseite erfolgreich geladen.");
			} else if (newValue == Worker.State.FAILED) {
				System.out.println("Fehler beim Laden der Webseite.");
			}
		});

		// Laden der URL in den WebView
		try {
			webkit.load(URL);
			System.out.println(URL);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
