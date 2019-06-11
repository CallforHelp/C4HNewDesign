package startView;

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

@SuppressWarnings("restriction")

public class startViewControler implements Initializable {
	
	@FXML
	private ProgressIndicator indictor = new ProgressIndicator(0);
	@FXML
	private ProgressIndicator indictor2 = new ProgressIndicator(0);

	@FXML
	private Button button;
	@FXML
	private AnchorPane anchorRoot;
	@FXML
	private AnchorPane parentContainer;
	    @Override
	    public void initialize(URL url, ResourceBundle rb) {
	    }

	    @FXML
	    private void loadSecond(ActionEvent event) throws IOException {
	        Parent root = FXMLLoader.load(getClass().getResource("initC4HRootLayout.fxml"));
	        Scene scene = button.getScene();
	        root.translateYProperty().set(scene.getHeight());

	        parentContainer.getChildren().add(root);

	        Timeline timeline = new Timeline();
	        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
	        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
	        timeline.getKeyFrames().add(kf);
	        timeline.setOnFinished(t -> {
	            parentContainer.getChildren().remove(anchorRoot);
	            parentContainer.getChildren().remove(parentContainer);
	        });
	        timeline.play();
	    }
	    @FXML
		private void setIndikator() throws IOException{
		     
		     OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		     Timeline timeline = new Timeline(new KeyFrame( Duration.millis(2500),
			     ae ->  indictor.setProgress(bean.getSystemCpuLoad())));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		}
		 
		 @SuppressWarnings("unused")
		private void setIndikator2() throws IOException{
			Thread rammonitor = new Thread() { 
				int RAM =100000	;
				@Override 
				
				public void run() { 
					Runtime rt = Runtime.getRuntime(); 
					Double usedKB = (double) ((rt.totalMemory() - rt.freeMemory()) / 1024); 
		            	System.out.println("Ram usage: " + usedKB/RAM); 
		            	indictor2.setProgress(usedKB/RAM);
		            	try { 
		            		Thread.sleep(500); 
		            	} catch (InterruptedException e) { 
		            		e.printStackTrace(); 
		            	} 

		           run(); 
		        } 
			}; 
			rammonitor.start(); 
		}

	}
