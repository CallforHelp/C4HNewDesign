package c4h.browser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.net.ssl.*;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
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

        // Erstellen eines WebView-Objekts
        WebView webView = new WebView();

        // JavaScript aktivieren
        WebEngine webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);

        // User-Agent setzen
        webEngine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.71 Safari/537.36");

        // Fehlerbehandlung für WebView hinzufügen
        webEngine.setOnError(event -> {
            System.out.println("Fehler beim Laden der Seite: " + event.getMessage());
        });

        // Laden der URL in den WebView
        webEngine.load("https://141.91.183.36/bwebserver/kiosk/login");

        // Erstellen der Szene und Setzen des WebView
        Scene scene = new Scene(webView, 800, 600);

        // Setzen der Szene in das Hauptfenster
        primaryStage.setScene(scene);
        primaryStage.setTitle("Kiosk Seite");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
