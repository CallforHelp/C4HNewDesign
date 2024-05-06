package c4h.PcInformation;




import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.management.OperatingSystemMXBean;

import javafx.animation.Animation;
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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


public class PcInformationControler implements Initializable {

	private pcInformation pcIno = new pcInformation();
	
	private ParsePcModelInMap parsePcModell= new ParsePcModelInMap();
	private double xOffset = 0;
	private double yOffset = 0;
	
	
	// SystemInfoLabel Labels
    
	@FXML  
	GridPane gridSystemInformation ;
	@FXML  
	GridPane gridNetzwerkInformation ;
	@FXML  
	GridPane gridSupportkInformation ;
	@FXML  
	Pane pane ;
	@FXML
	private Parent PcInfoContainer;
	
	
    @FXML
    private Button StartViewbutton;

    
    
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
	private Label Seriennummer;
	@FXML
	private Label kaufdatum;
	@FXML
	private Label win11komp;
	
	//IMGInfo
	@FXML
	private ImageView image;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
		try {
			System.out.println("PCInformation");
			loadRamUsage();
			loadCpuUsage();
			loadGPUUsage();
			LoadPCTyp();
			systemInformation();
			netzwerkInformation();
			supportkInformation();
			modellImage();
			
		} catch (Throwable e) {
			e.printStackTrace();
		}		
    }
    
    @FXML
    private void modellImage() throws Throwable {
    	
    	String pcModell= parsePcModell.findePcModell(pcIno.getPcModell());
    	
    	String srcPath = "/c4h/images/PcModell/"+pcModell;
    	System.out.println(srcPath);
        // Bild aus dem Pfad laden
        Image modellFoto = new Image(srcPath);
        image.setImage(modellFoto);
    }
    

    @SuppressWarnings("unused")
	private void mobileParent() {
    	
    	PcInfoContainer.setOnMousePressed(event -> {
            xOffset = event.getSceneX() - PcInfoContainer.getTranslateX();
            yOffset = event.getSceneY() - PcInfoContainer.getTranslateY();
        });

    	PcInfoContainer.setOnMouseDragged(event -> {
    		PcInfoContainer.setTranslateX(event.getSceneX() - xOffset);
    		PcInfoContainer.setTranslateY(event.getSceneY() - yOffset);
        });
    }
    
    @FXML
	public void systemInformation() throws Throwable {
		
		// SystemInfoLabel Labels
         HostNameLabel.setText(pcIno.getLocalHost());
         SchulNummer.setText(pcIno.getSchulNummer());
         MusterImage.setText(pcIno.getMusterImages());
         Schuldomain.setText(pcIno.getMachindomain());
    }
    
    @FXML
	public void netzwerkInformation() throws Throwable {
		
		// SystemInfoLabel Labels
         IP.setText(pcIno.getLocalAdresse());
         LanMAC.setText(pcIno.getMacAddress());
         wlanSsID.setText(pcIno.getConnectedWifiInfo());
         wlanMAC.setText(pcIno.getWifiMacAdresse());
         Gateway.setText(pcIno.getDefaultgateway());
         TFKIP.setText(pcIno.getDHCPServer());
        
	}
    
    @FXML
	public void supportkInformation() throws Throwable {
		
		// SystemInfoLabel Labels
         rechnerTyp.setText(pcIno.getPcModell());
         Seriennummer.setText(pcIno.getSerienNummer());
        
	}

	@FXML
    private void loadRoot(ActionEvent event) throws IOException {
    	
    	//PcInfoContainer = FXMLLoader.load(getClass().getResource("PcInformation.fxml"));
        
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
    private void pcInfo() {
    	
    	try {
			pcIno.printBGinfo();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	@FXML
	private void loadCpuUsage() throws IOException{
		indictor2.setMinSize(100, 100);
		OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		Timeline timeline = new Timeline(new KeyFrame( Duration.millis(2000),
			     ae ->  indictor2.setProgress(bean.getCpuLoad())));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
		 
	@FXML
	private void loadRamUsage() throws IOException{
		indictor.setMinSize(100, 100);
		Thread rammonitor = new Thread() { 
		int RAM =100000	;
		@Override 
				
		public void run() { 
			Runtime rt = Runtime.getRuntime(); 
			
			Double usedKB = (double) ((rt.totalMemory() - rt.freeMemory()) / 1024);
			if(usedKB==1024)
				usedKB=0.0;
				
		   	indictor.setProgress(usedKB/RAM);
		    try {
		    	Thread.sleep(500); 
		    }catch (InterruptedException e) { 
		    	e.printStackTrace(); 
		    } 
		    run(); 
		    } 
		}; 
			rammonitor.start(); 
		}
	@FXML
	private void loadGPUUsage() throws IOException{
		indictor.setMinSize(100, 100);		
		}
	
	@FXML
	private void LoadPCTyp() {
		
	}

}
