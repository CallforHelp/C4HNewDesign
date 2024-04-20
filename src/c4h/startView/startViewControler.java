package c4h.startView;

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

public class startViewControler implements Initializable {
	
	@FXML
	private ProgressIndicator indictor = new ProgressIndicator(0);
	@FXML
	private ProgressIndicator indictor2 = new ProgressIndicator(0);

	@FXML
	private Button buttonBrowser;
	@FXML
	private Button buttonPcInfo;
	@FXML
	private AnchorPane anchorRoot;
	@FXML
	private AnchorPane parentContainer;
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			loadRamUsage();
			loadCpuUsage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void pcInformation(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/c4h/PcInformation/PcInformation.fxml"));
	    Scene scene = buttonPcInfo.getScene();
	    
	    root.translateYProperty().set(scene.getWidth());

	    parentContainer.getChildren().add(root);

	    Timeline timeline = new Timeline();
	    KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
	    KeyFrame kf = new KeyFrame(Duration.seconds(2), kv);
	    timeline.getKeyFrames().add(kf);
	    timeline.setOnFinished(t -> {
	    	parentContainer.getChildren().remove(anchorRoot);
	    });
	    timeline.play();
	    }
	
	@FXML
	private void loadBrowser(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/c4h/browser/Browser.fxml"));
	    Scene scene = buttonBrowser.getScene();
	    
	    root.translateYProperty().set(scene.getWidth());

	    parentContainer.getChildren().add(root);

	    Timeline timeline = new Timeline();
	    KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
	    KeyFrame kf = new KeyFrame(Duration.seconds(2), kv);
	    timeline.getKeyFrames().add(kf);
	    timeline.setOnFinished(t -> {
	    	parentContainer.getChildren().remove(anchorRoot);
	    });
	    timeline.play();
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
