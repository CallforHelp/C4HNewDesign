package c4h.PcInformation;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
	
	@FXML
	private RAM_Usage RAMcontroller = new RAM_Usage();
	@FXML
	private CPU_Usage CPUController = new CPU_Usage();
	@FXML
	private GPU_Usage GPUcontroller = new GPU_Usage();



    @Override
    public void initialize(URL url, ResourceBundle rb) {
		try {
		System.out.println("PCInformationControler");
			
			//PC_INFORMATION
			systemInformation();
			netzwerkInformation();
			supportInformation();
			LogoImage();
			
			
			//CPU_USAGE
			RAMcontroller.monitorRAMUsage(indictor);
			CPUController.monitorCPUUsage(indictor2);
	        GPUcontroller.monitorGPUUsage(indictor3);
	        
	        
			
		} catch (Throwable e) {
			e.printStackTrace();
		}		
    }
    
    @FXML
    private void screenShot(ActionEvent event) throws IOException {
    	System.out.println("ScreenShot");	
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");
		Calendar now = Calendar.getInstance();
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
            File file = new File(desktopDir, "screenshot"+formatter.format(now.getTime())+".png");
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
			  pcModell="Keine-Ausschreibung"; 
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
	
}
