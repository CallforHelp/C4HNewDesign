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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class PcInformationControler implements Initializable {

    @FXML
    private Button StartViewbutton;
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private AnchorPane Container;
	@FXML
	private ProgressIndicator indictor = new ProgressIndicator(0);
	@FXML
	private Parent oldParentContainer;
	@FXML
	private ProgressIndicator indictor2 = new ProgressIndicator(0);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	pcInfo();
		try {
			loadRamUsage();
			loadCpuUsage();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    


    @FXML
    private void loadRoot(ActionEvent event) throws IOException {
    	
    	oldParentContainer = FXMLLoader.load(getClass().getResource("PcInformation.fxml"));
        
    	Parent root = FXMLLoader.load(getClass().getResource("/c4h/startView/startView.fxml"));
        Scene scene = StartViewbutton.getScene();
        root.translateYProperty().set(scene.getHeight());

        AnchorPane parentContainer = (AnchorPane) StartViewbutton.getScene().getRoot();

        parentContainer.getChildren().add(root);


        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(oldParentContainer);
        });
        timeline.play();
    }
    @FXML
    private void pcInfo() {
    	pcInformation test = new pcInformation();
    	try {
			test.printBGinfo();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	@FXML
	private void loadCpuUsage() throws IOException{
		
		OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		Timeline timeline = new Timeline(new KeyFrame( Duration.millis(2000),
			     ae ->  indictor.setProgress(bean.getSystemCpuLoad())));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
		 
	@FXML
	private void loadRamUsage() throws IOException{
		
		Thread rammonitor = new Thread() { 
		int RAM =100000	;
		@Override 
				
		public void run() { 
			Runtime rt = Runtime.getRuntime(); 
			
			Double usedKB = (double) ((rt.totalMemory() - rt.freeMemory()) / 1024);
			if(usedKB==1024)
				usedKB=0.0;
				
		   	indictor2.setProgress(usedKB/RAM);
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

}
