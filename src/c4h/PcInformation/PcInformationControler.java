package c4h.PcInformation;


import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import javax.imageio.ImageIO;

import com.sun.management.OperatingSystemMXBean;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;


public class PcInformationControler implements Initializable {

	private pcInformation pcIno = new pcInformation();
	
	private ParsePcModelInMap parsePcModell= new ParsePcModelInMap();

	private double xOffset = 0;
	private double yOffset = 0;
	
	
	// SystemInfoLabel Labels
    
	@FXML  
	private GridPane gridSystemInformation ;
	@FXML  
	private GridPane gridNetzwerkInformation ;
	@FXML  
	private GridPane gridSupportkInformation ;
	
	//Container
	@FXML
	private Parent PcInfoContainer;	
    @FXML
    private Button StartViewbutton;
    @FXML
    private Button screenShot;
    @FXML
    private Button support;
    
    @FXML
	private ProgressIndicator indictor = new ProgressIndicator(0);
	@FXML
	private ProgressIndicator indictor2 = new ProgressIndicator(0);
	@FXML
	private ProgressIndicator indictor3 = new ProgressIndicator(0);
	
	//Lable SystemInformation
	@FXML
	private Label HostNameLabel;
	@FXML
	private Label SchulNummer;
	@FXML
	private Label MusterImage;
	@FXML
	private Label Schuldomain;
	@FXML
	private Label Seriennummer;
	
	
	//Label NetzwerkInformation
	@FXML
	private Label IP;
	@FXML
	private Label LanMAC;
	@FXML
	private Label wlanSsID;
	@FXML
	private Label wlanMAC;
	@FXML
	private Label Gateway;
	@FXML
	private Label TFKIP;
	
	//Label Supportinformation
	@FXML
	private Label rechnerTyp;
	@FXML
	private Label supportEnde;
	@FXML
	private Label hersteller;
	@FXML
	private Label kaufdatum;
	@FXML
	private Label win11komp;
	
	//IMGInfo
	@FXML
	private ImageView image;
	@FXML
	private ImageView imageLogo;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
		try {
		System.out.println("PCInformation");
			loadRamUsage();
			loadCpuUsage();
			loaddDriveDataRate();
			systemInformation();
			netzwerkInformation();
			supportInformation();
			LogoImage();
			
		} catch (Throwable e) {
			e.printStackTrace();
		}		
    }
    
    @FXML
    private void screenShot(ActionEvent event) throws IOException {
    	System.out.println("ScreenShot");	
    	try {
            // Erfasse den Bereich des Java-Fensters
    		Parent root = FXMLLoader.load(getClass().getResource("/c4h/PcInformation/PcInformation.fxml"));
            
        	Scene scene = screenShot.getScene();
            root.translateYProperty().set(scene.getHeight());
            
            WritableImage writableImage = new WritableImage((int) scene.getWidth(), (int) scene.getHeight());
            scene.snapshot(writableImage);

            // Konvertiere das JavaFX-Image in ein BufferedImage
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);

            // Speichere das Bild auf dem Desktop
            File desktopDir = new File("c:\\Users\\"+pcIno.getUserName()+"\\Desktop");
            System.out.println(desktopDir);
            File file = new File(desktopDir, "screenshot.png");
            ImageIO.write(bufferedImage, "png", file);

            System.out.println("Screenshot wurde unter " + file.getAbsolutePath() + " gespeichert.");
        } catch (IOException ex) {
            System.err.println("Fehler beim Erstellen des Screenshots: " + ex.getMessage());
        }
    }
    
    @FXML
    private void LogoImage() {
		// TODO Auto-generated method stub
        Image modellFoto = new Image("/image/3s_logo_tex2t.png");
        imageLogo.setImage(modellFoto);
		
	}

	@FXML
    private void supportInformation() throws Throwable {
		
		  String pcModell= parsePcModell.findePcModell(pcIno.getPcModell().trim());
		  
		  System.out.println("Pc Model: "+pcModell);
		  
		  String srcPath = "/image/PcModell/"+pcModell+".png";
		  
		  //Path Prüfen ob es vorhanden ist 
		  boolean exists = checkFilePath(srcPath); 
		  if(exists) srcPath ="/image/PcModell/"+pcModell+".png";
		  
		  if (pcModell==""|| pcModell==null) { 
			  srcPath = "/image/PcModell/noPic.png";
			  pcModell="Kein-Ausschreibungsgerät"; 
		  }
		  
		  System.out.println("der Path ist für den RechnerModell: " +srcPath);
		  
		  // Bild aus dem Pfad laden 
		  Image modellFoto = new Image(srcPath.trim());
		  image.setImage(modellFoto); image.setFitHeight(250); image.setFitWidth(250);
		  
		  
		  // Support Labels
		  
		  rechnerTyp.setText(pcModell);
		  supportEnde.setText(parsePcModell.findSupportEndethValue(pcModell));
		  win11komp.setText(parsePcModell.findWindows11Support(pcModell));
		  kaufdatum.setText(parsePcModell.findKaufDatum(pcModell));
		  hersteller.setText(pcIno.getHersteller());
    
    }
    
    private boolean checkFilePath(String filePath) {
            File file = new File(filePath.trim());
            return file.exists();
	}

	
	  @FXML 
	  public void systemInformation() throws Throwable {
	  
		  HostNameLabel.setText(pcIno.getLocalHost());  
		  SchulNummer.setText(pcIno.getSchulNummer());
		  MusterImage.setText(pcIno.getMusterImages());
		  Schuldomain.setText(pcIno.getMachindomain());
		  Seriennummer.setText(pcIno.getSerienNummer()); 
	  }
	  
	  @FXML 
	  public void netzwerkInformation() throws Throwable {
	  
	  IP.setText(pcIno.getLocalAdresse());
	  LanMAC.setText(pcIno.getMacAddress());
	  wlanSsID.setText(pcIno.getConnectedWifiInfo());
	  wlanMAC.setText(pcIno.getWifiMacAdresse());
	  Gateway.setText(pcIno.getDefaultgateway());
	  TFKIP.setText(pcIno.getDHCPServer());
	  
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
            parentContainer.getChildren().remove(PcInfoContainer);
        });
        timeline.play();
    }
	
	@FXML
    private void loadBrowser(ActionEvent event) throws IOException {
    	        
    	Parent root = FXMLLoader.load(getClass().getResource("/c4h/browser/Browser.fxml"));
        
    	Scene scene = StartViewbutton.getScene();
        root.translateYProperty().set(scene.getHeight());

        AnchorPane parentContainer = (AnchorPane) StartViewbutton.getScene().getRoot();

        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(PcInfoContainer);
        });
        timeline.play();
    }
	@FXML
	private void loadCpuUsage() throws IOException{
		indictor2.setMinSize(100, 100);
	    indictor2.setProgress(0); // Setze den Anfangsfortschritt auf 0

	    Timeline timeline = new Timeline(
	        new KeyFrame(Duration.ZERO, e -> {
	            OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	            double processCpuLoad = bean.getProcessCpuLoad();
	            indictor2.setProgress(processCpuLoad);
	        }),
	        new KeyFrame(Duration.seconds(2))
	    );
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
	}
		 
	@FXML
	private void loadRamUsage() throws IOException{
		// Setze die Mindestgröße des Indikators
        indictor.setMinSize(100, 100);


        // Erstelle einen Thread für das RAM-Monitoring
        Thread rammonitor = new Thread(() -> {
            int RAM = 100000;

            while (!Thread.currentThread().isInterrupted()) {
                // Hier wird der RAM-Verbrauch gemessen
                Runtime rt = Runtime.getRuntime();

                // Berechne den genutzten Speicher in Kilobyte
                double usedKB = (double) ((rt.totalMemory() - rt.freeMemory()) / 1024);
                if (usedKB == 1024)
                    usedKB = 0.0;

                // Setze den Fortschritt des Indikators basierend auf dem genutzten RAM
                double progress = usedKB / RAM;
                if (progress > 1.0) // Begrenze den Fortschritt auf 1.0
                    progress = 1.0;
                indictor.setProgress(progress);

                try {
                    // Warte für eine kurze Zeit (500 Millisekunden)
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt(); // Setze den Interrupt-Status erneut
                }
            }
        });

        // Starte den RAM-Monitor-Thread
        rammonitor.start();
	}
	
	@FXML
	private void loaddDriveDataRate(){
		// Setze die Mindestgröße des Indikators
	    indictor3.setMinSize(100, 100);

	    // Erstelle einen Task, um die Festplattenaktivität zu überwachen
	    Task<Void> driveTask = new Task<Void>() {
	        @Override
	        protected Void call() throws Exception {
	            while (true) {
	                // Rufe die Festplattenaktivität ab
	                double driveActivity = getHardDriveDataRate();

	                // Aktualisiere den Fortschrittsindikator basierend auf der Festplattenaktivität
	                Platform.runLater(() -> indictor3.setProgress(driveActivity / 100.0));

	                // Warte für einen bestimmten Zeitraum, bevor du die Festplattenaktivität erneut abrufst
	                Thread.sleep(100); // Überprüfe alle 1 Sekunde
	            }
	        }
	    };

	    // Führe den Task in einem separaten Thread aus
	    Thread driveThread = new Thread(driveTask);
	    driveThread.setDaemon(true); // Setze den Thread als Daemon, damit er beendet wird, wenn die Anwendung geschlossen wird
	    driveThread.start();
	}

	// Methode zur Überwachung der Festplattenaktivität
	private double getHardDriveDataRate() {
	    // Pfad zur Festplatte, die Sie überwachen möchten
		 String userHome = System.getProperty("user.home");
	    String drivePath = userHome; // Überwacht den HomeUserActivity

	    try {
	        // Erstellen Sie ein Objekt für die Festplatte
	        File drive = new File(drivePath);

	        // Überprüfen Sie, ob die Festplatte existiert und lesbar ist
	        if (drive.exists() && drive.canRead()) {
	            // Erhalten Sie die Gesamtkapazität der Festplatte (in Bytes)
	            long totalSpace = drive.getTotalSpace();
	            // Erhalten Sie die freie Kapazität der Festplatte (in Bytes)
	            long freeSpace = drive.getFreeSpace();

	            // Berechnen Sie die Auslastung der Festplatte
	            double usagePercent = 1.0 - ((double) freeSpace / totalSpace);

	            // Rückgabe der Festplattenaktivität (in Prozent)
	            return usagePercent * 100.0;
	        } else {
	            // Wenn die Festplatte nicht gefunden oder nicht lesbar ist, geben Sie -1 zurück, um einen Fehler anzuzeigen
	            return -1;
	        }
	    } catch (Exception e) {
	        // Bei einer Ausnahme (z. B. wenn auf die Festplatte nicht zugegriffen werden kann) geben Sie ebenfalls -1 zurück
	        return -1;
	    }
	}
}
