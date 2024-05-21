package c4h;

import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Die MainC4H-Klasse ist die Hauptklasse der C4H-Anwendung. Sie erbt von Application und implementiert die
 * start()-Methode, um die JavaFX-Anwendung zu initialisieren.
 */
public class MainC4H extends Application {
	
    private String cssPath="/application.css";

    // Die Pfadangabe für das Anwendungssymbol wird sowohl für das Anwendungstray-Symbol als auch für das Taskleistensymbol gemeinsam genutzt.
    // Es können auch mehrere Symbole verwendet werden, um eine saubere Anzeige von Symbolen im Tray auf Hi-DPI-Geräten zu ermöglichen.
    private static final String iconImageLoc = "/image/bulb.png";

    // Die Anwendungsstufe wird gespeichert, damit sie basierend auf den Operationen des Systemtray-Symbols angezeigt und ausgeblendet werden kann.
    private Stage primaryStage;

    // Initialisiert die JavaFX-Anwendung.
    // Ein Systemtray-Symbol wird für das Symbol eingerichtet, aber die Hauptbühne bleibt unsichtbar, bis der Benutzer
    // mit dem Systemtray-Symbol interagiert.
    @Override
    public void start(final Stage stage) {
        // Speichert eine Referenz auf die Bühne.
        this.primaryStage = stage;

        // Instruiert das JavaFX-System, nicht implizit zu beenden, wenn das letzte Anwendungsfenster geschlossen wird.
        Platform.setImplicitExit(false);

        // Richtet das Tray-Symbol ein (unter Verwendung von awt-Code, der auf dem Swing-Thread ausgeführt wird).
        javax.swing.SwingUtilities.invokeLater(this::addAppToTray);

        // Unsere Bühne wird transparent sein, daher geben Sie ihr einen transparenten Stil.
       // stage.initStyle(StageStyle.TRANSPARENT);

        // Erstellt das Layout für die JavaFX-Bühne.
        @SuppressWarnings("unused")
		Pane layout = null;
        try {
            layout = (Pane) createContent();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Für diese Dummy-Anwendung sagt der (JavaFX-Szenengraph-) Inhalt der C4H.
     * Eine echte Anwendung könnte z. B. ein FXML laden oder ähnliches.
     *
     * @return Der Inhalt der Hauptfensteranwendung.
     * @throws Throwable
     */
    private Node createContent() throws Throwable {
        Pane page = null;

        try {
            page = FXMLLoader.load(getClass().getResource("/c4h/startView/StartView.fxml"));

            // Bühne verschieben
            double[] dragDelta = {0.0, 0.0};
            page.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    dragDelta[0] = primaryStage.getX() - event.getScreenX();
                    dragDelta[1] = primaryStage.getY() - event.getScreenY();
                }
            });

            page.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    primaryStage.setX(event.getScreenX() + dragDelta[0]);
                    primaryStage.setY(event.getScreenY() + dragDelta[1]);
                }
            });
            
  

            Scene scene = new Scene(page);
            scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setResizable(true);
            primaryStage.getIcons().add(new Image("/image/3sTask.png"));
            
        } catch (IOException e) {
            e.getCause().printStackTrace();
        }

        return page;
    }

    /**
     * Richtet ein Systemtray-Symbol für die Anwendung ein.
     */
    private void addAppToTray() {
        try {
            // Stellen Sie sicher, dass das AWT-Toolkit initialisiert ist.
            java.awt.Toolkit.getDefaultToolkit();

            // Die Anwendung benötigt Unterstützung für das Systemtray, beenden Sie einfach, wenn keine Unterstützung vorhanden ist.
            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("Keine Unterstützung für Systemtray, Anwendung wird beendet.");
                Platform.exit();
            }

            // Ein Systemtray-Symbol wird eingerichtet.
            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            URL imageLoc = getClass().getResource(iconImageLoc);
            java.awt.Image image = ImageIO.read(imageLoc);
            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);

            // Wenn der Benutzer auf das Tray-Symbol doppelklickt, wird das Hauptfenster der Anwendung angezeigt.
            trayIcon.addActionListener(event -> Platform.runLater(this::showStage));

            // Wenn der Benutzer das Standardmenüelement auswählt (das den Anwendungsnamen enthält),
            // wird das Hauptfenster der Anwendung angezeigt.
            java.awt.MenuItem openItem = new java.awt.MenuItem("C4H");
            openItem.addActionListener(event -> Platform.runLater(this::showStage));

            // Die Konvention für Tray-Symbole scheint zu sein, das Standard-Symbol für das Öffnen
            // des Anwendungsfensters in fett gedruckter Schrift festzulegen.
            java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
            openItem.setFont(boldFont);

            // Um die Anwendung wirklich zu beenden, muss der Benutzer zum Systemtray-Symbol gehen
            // und die Option "Beenden" auswählen. Dadurch wird JavaFX heruntergefahren und das
            // Tray-Symbol entfernt (das Entfernen des Tray-Symbols führt auch zum Herunterfahren von AWT).
            java.awt.MenuItem exitItem = new java.awt.MenuItem("Beenden");
            exitItem.addActionListener(event -> {
                Platform.exit();
               
                tray.remove(trayIcon);
                System.exit(0);
                
            });

            // Einrichten des Popup-Menüs für die Anwendung.
            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);
            // Fügen Sie das Anwendungstray-Symbol zum Systemtray hinzu.
            tray.add(trayIcon);
        } catch (java.awt.AWTException | IOException e) {
            System.out.println("Systemtray kann nicht initialisiert werden");
            e.printStackTrace();
        }
    }

    /**
     * Zeigt die Anwendungsstufe an und stellt sicher, dass sie im Vordergrund aller Stufen liegt.
     */
    private void showStage() {
        if (primaryStage != null) {
            primaryStage.show();
            primaryStage.toFront();
        }
    }

    /**
     * Die main()-Methode dient zum Starten der JavaFX-Anwendung.
     * Aufgrund der Codierung der Anwendung bleibt die Anwendung aktiv,
     * bis der Benutzer die Menüoption "Beenden" des Tray-Symbols auswählt.
     */
    public static void main(String[] args) throws IOException, java.awt.AWTException {
        // Startet einfach die JavaFX-Anwendung.
        // Aufgrund der Codierung der Anwendung bleibt die Anwendung aktiv,
        // bis der Benutzer die Menüoption "Beenden" des Tray-Symbols auswählt.
        launch(args);
    }
    
    /**
     * Schließt das angegebene Fenster.
     * 
     * @param scene Die Szene, zu der das Fenster gehört.
     */
    public static void closeStage(Scene scene) {
    	System.out.println("ExitButton");
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }
}
