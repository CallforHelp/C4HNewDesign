package c4h.kiosk;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ResourceBundle;
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
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

public class KioskControler implements Initializable{

	@FXML
	private Button StartViewbutton;
	@FXML
	private AnchorPane kioskContainer;	
	@FXML
	private WebView  Kioskbrowser = new WebView();;
	@FXML
	public WebEngine webkit;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("KioskControler");
		//Kiosk Laden
		loadBrowser();
	}
	@FXML
	public void loadBrowser() { 
		
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
			URL url = new URL("https://141.91.183.36/bwebserver/kiosk/login");
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
		webkit = Kioskbrowser.getEngine();
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
		webkit.load("https://141.91.183.36/bwebserver/kiosk/login");
	}

	@FXML
	private void loadRoot(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/c4h/startView/StartView.fxml"));
		Scene currentScene = StartViewbutton.getScene();
		//browserContainer = (AnchorPane) currentScene.getRoot();

		// Initial position of the new root (below the current scene)
		root.translateYProperty().set(-currentScene.getHeight());
		kioskContainer.getChildren().add(root);

		// Animation to move current scene up
		Timeline currentSceneTimeline = new Timeline();
		KeyValue currentSceneKv = new KeyValue(kioskContainer.translateYProperty(), currentScene.getHeight(), Interpolator.EASE_IN);
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
			kioskContainer.getChildren().remove(kioskContainer.lookup("#oldSceneRoot")); // Assume old scene root has this ID
			kioskContainer.setTranslateY(0); // Reset translateY of the parent container
		});

		currentSceneTimeline.play();
	}
}
