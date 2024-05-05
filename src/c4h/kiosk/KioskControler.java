package c4h.kiosk;

import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

public class KioskControler implements Initializable{

    @FXML
    private Button StartViewbutton;
    @FXML
    private Parent kioskContainer;
    @FXML
    private StackPane stackPane;
	
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
	
	/*
	 * public void loadBrowser() {
	 * 
	 * 
	 * // Konfiguriere die CEF-Einstellungen CefSettings settings = new
	 * CefSettings(); CefApp cefApp = CefApp.getInstance(settings);
	 * 
	 * // Initialisiere das CEF-Framework CefApp.addAppHandler(new
	 * org.cef.handler.CefAppHandlerAdapter(null) {});
	 * 
	 * // Erstelle den CEF-Browser CefClient client = cefApp.createClient();
	 * CefBrowser browser = client.createBrowser("https://www.example.com", false,
	 * false);
	 * 
	 * // Erstelle einen SwingNode und füge den Browser hinzu GLJPanel browserUi =
	 * (GLJPanel) browser.getUIComponent(); SwingUtilities.invokeLater(() ->
	 * swingNode.setContent(browserUi)); swingNode.setContent(browserUi );
	 * 
	 * // Erstelle ein JavaFX-Layout stackPane = new StackPane();
	 * stackPane.getChildren().add(swingNode);
	 * 
	 * }
	 */
	@FXML
	private void loadBrowser() {
		try {
	            TrustManager[] trustAllCerts = new TrustManager[]{
	                    new X509TrustManager() {
	                        public X509Certificate[] getAcceptedIssuers() {
	                            return null;
	                        }

	                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
	                        }

	                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
	                        }
	                    }
	            };

	            // SSL-Kontext initialisieren
	            SSLContext sslContext = SSLContext.getInstance("TLS");
	            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

	            // SSL-Socket-Fabrik initialisieren
	            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
	        } catch (NoSuchAlgorithmException | KeyManagementException e) {
	            e.printStackTrace();
	        }
		System.out.println("KioskSeite");
		
         webkit = Kioskbrowser.getEngine();
         Kioskbrowser.setPageFill(javafx.scene.paint.Color.TRANSPARENT);
         
         Kioskbrowser.setFontScale(1);
         webkit.load("http://141.91.183.36/bwebserver/kiosk/login");
         }
	
    

    }
/*
 * 
    }*/
