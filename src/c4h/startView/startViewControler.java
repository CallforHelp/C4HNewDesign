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

@SuppressWarnings("restriction")

public class startViewControler implements Initializable {
	
	@FXML
	private ProgressIndicator indictor = new ProgressIndicator(0);
	@FXML
	private ProgressIndicator indictor2 = new ProgressIndicator(0);
	@FXML
	private ProgressIndicator indictor3 = new ProgressIndicator(0);
	@FXML
	private Button buttonBrowser;
	@FXML
	private Button buttonPcInfo;
	@FXML
	private Button buttonChat;
	@FXML
	private Button buttonWeb;
	@FXML
	private Button buttonExit;
	@FXML
	private Button buttonHilfe;
	@FXML
	private Button buttonWebSeite;
	@FXML
	private Button faq;
	@FXML
	private AnchorPane anchorRoot;
	@FXML
	private AnchorPane parentContainer;
	@Override
	
	
	
	public void initialize(URL url, ResourceBundle rb) {
		try {
			loadRamUsage();
			loadCpuUsage();
			loadNetzwekUsage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	@FXML
	private void Loadfaq(ActionEvent event) throws IOException{
		
		Parent root = FXMLLoader.load(getClass().getResource("/c4h/faq/faqBrowser.fxml"));
	    Scene scene = faq.getScene();
	    
	    root.translateYProperty().set(scene.getWidth());

	    parentContainer.getChildren().add(root);

	    Timeline timeline = new Timeline();
	    KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
	    KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
	    timeline.getKeyFrames().add(kf);
	    timeline.setOnFinished(t -> {
	    	parentContainer.getChildren().remove(anchorRoot);
	    });
	    timeline.play();
	}
	@FXML
	private void LoadWebSeite(ActionEvent event) throws IOException{
		System.out.println("Webseite von 3s : https://www.3s-hamburg.de");
		
		Parent root = FXMLLoader.load(getClass().getResource("/c4h/webPageVon3S/WebPagePanelDesign.fxml"));
	    Scene scene = buttonWebSeite.getScene();
	    
	    root.translateYProperty().set(scene.getWidth());

	    parentContainer.getChildren().add(root);

	    Timeline timeline = new Timeline();
	    KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
	    KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
	    timeline.getKeyFrames().add(kf);
	    timeline.setOnFinished(t -> {
	    	parentContainer.getChildren().remove(anchorRoot);
	    });
	    timeline.play();
	}
	@FXML
	private void pcInformation(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/c4h/PcInformation/PcInformation.fxml"));
	    Scene scene = buttonPcInfo.getScene();
	    
	    root.translateYProperty().set(scene.getWidth());

	    parentContainer.getChildren().add(root);

	    Timeline timeline = new Timeline();
	    KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
	    KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
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
	    KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
	    timeline.getKeyFrames().add(kf);
	    timeline.setOnFinished(t -> {
	    	parentContainer.getChildren().remove(anchorRoot);
	    });
	    timeline.play();
	    }
	    
	@FXML
	private void loadChat(ActionEvent event)throws IOException{

		Parent root = FXMLLoader.load(getClass().getResource("/c4h/chat/ChatDesign.fxml"));
	    Scene scene = buttonChat.getScene();
	    
	    root.translateYProperty().set(scene.getWidth());

	    parentContainer.getChildren().add(root);

	    Timeline timeline = new Timeline();
	    KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
	    KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
	    timeline.getKeyFrames().add(kf);
	    timeline.setOnFinished(t -> {
	    	parentContainer.getChildren().remove(anchorRoot);
	    });
	    timeline.play();		
	}
	@FXML
	private void loadhilfe(ActionEvent event)throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("/c4h/hilfe/WebDesign.fxml"));
	    Scene scene = buttonWeb.getScene();
	    
	    root.translateYProperty().set(scene.getWidth());

	    parentContainer.getChildren().add(root);

	    Timeline timeline = new Timeline();
	    KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
	    KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
	    timeline.getKeyFrames().add(kf);
	    timeline.setOnFinished(t -> {
	    	parentContainer.getChildren().remove(anchorRoot);
	    });
	    timeline.play();	
		
	}
	@FXML
	private void loadDownloads(ActionEvent event)throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("/c4h/downloads/WebDesign.fxml"));
	    Scene scene = buttonWeb.getScene();
	    
	    root.translateYProperty().set(scene.getWidth());

	    parentContainer.getChildren().add(root);

	    Timeline timeline = new Timeline();
	    KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
	    KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
	    timeline.getKeyFrames().add(kf);
	    timeline.setOnFinished(t -> {
	    	parentContainer.getChildren().remove(anchorRoot);
	    });
	    timeline.play();	
		
	}
	@FXML
	private void exit(ActionEvent event)throws IOException{
		System.exit(0);
	}
	
	@FXML
	private void loadCpuUsage() throws IOException{
		
		OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		Timeline timeline = new Timeline(new KeyFrame( Duration.millis(2000),
			     ae ->  indictor.setProgress(bean.getSystemCpuLoad())));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		System.out.println(bean.getSystemCpuLoad());
	}

	@FXML
	private void loadRamUsage() throws IOException{
		
		OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		double usedKB= ((bean.getFreePhysicalMemorySize())/1e+10);
		Timeline timeline = new Timeline(new KeyFrame( Duration.millis(2000),
			     ae ->  indictor2.setProgress(bean.getFreePhysicalMemorySize()/1e+10)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		System.out.println(usedKB);
		
		
		/*
		Thread rammonitor = new Thread() { 
		@Override 
				
		public void run() { 
			
			Runtime rt = Runtime.getRuntime(); 
			Double usedKB = (double) ((rt.totalMemory() - rt.freeMemory()) / 1024);
			Double progress = (usedKB/100000);
			System.out.println(progress);
			indictor2.setProgress(progress);
			
			OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
			
			double usedKB= ((bean.getFreePhysicalMemorySize())/1e+10);
			System.out.println(usedKB);
			
			Timeline timeline = new Timeline(new KeyFrame( Duration.millis(2000),
				     ae ->  indictor2.setProgress(usedKB)));
			timeline.setCycleCount(Animation.INDEFINITE);
			timeline.play();
		   	
		    try {
		    	Thread.sleep(500); 
		    }catch (InterruptedException e) { 
		    	e.printStackTrace(); 
		    } 
		    run(); 
		    } 
		}; 
			rammonitor.start();*/ 
		}
	private void loadNetzwekUsage() throws IOException{
		
		OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		double NetweorkUsed= bean.getProcessCpuLoad();
		
		Timeline timeline = new Timeline(new KeyFrame( Duration.millis(2000),
			     ae ->  indictor3.setProgress(NetweorkUsed)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		System.out.println(NetweorkUsed);
		
	}

}
