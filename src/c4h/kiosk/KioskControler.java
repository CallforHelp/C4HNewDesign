package c4h.kiosk;

import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
import javafx.stage.Stage;
import javafx.util.Duration;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.net.ssl.*;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class KioskControler implements Initializable{

    @FXML
    private Button StartViewbutton;
    @FXML
    private AnchorPane kioskContainer;	
	@FXML
    private WebView  Kioskbrowser = new WebView();;
	
	@FXML
	private WebEngine webkit;

	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Kiosk Laden
	        loadBrowser();
	}
	@FXML
	private void loadBrowser() { try {
        // TrustManager-Array initialisieren, um das Serverzertifikat zu überprüfen
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                        for (X509Certificate cert : certs) {
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

        // InputStream der Antwort lesen, falls gewünscht
        // InputStream inputStream = connection.getInputStream();

        connection.disconnect();
    } catch (Exception e) {
        e.printStackTrace();
    }
		

	        System.out.println("KioskSeite");
	   
	        // JavaScript aktivieren
	        webkit = Kioskbrowser.getEngine();
	        webkit.setJavaScriptEnabled(true);

	        // User-Agent setzen
	        webkit.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.71 Safari/537.36");

	        // Fehlerbehandlung für WebView hinzufügen
	        webkit.setOnError(event -> {
	            System.out.println("Fehler beim Laden der Seite: " + event.getMessage());
	        });
	        
	        // Cache aktivieren
	       // webkit.setCache(true);
	        
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
	     //   Kioskbrowser.dispose();
	        System.out.println(Thread.currentThread().getName());
	    

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
            parentContainer.getChildren().remove(kioskContainer);
        });
        timeline.play();
    }
	
	}

