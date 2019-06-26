package c4h.downloads;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.Duration;



import javax.swing.JOptionPane;

public class WebControler  implements Initializable{

    @FXML
    private Button button;
    @FXML
    private AnchorPane Container;
	@FXML
    private WebView  browser = new WebView();
	@FXML
	private WebEngine webkit;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Hilfe");
		loadBrowser();
	}
	@FXML
    private void loadRoot(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/c4h/startView/startView.fxml"));
        Scene scene = button.getScene();
        root.translateYProperty().set(scene.getWidth());

        AnchorPane parentContainer = (AnchorPane) button.getScene().getRoot();

        parentContainer.getChildren().add(root);


        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(Container);
        });
        timeline.play();
    }
	@FXML
	private void loadBrowser() {
	
         webkit = browser.getEngine();
         webkit.load("https://fehlermeldung.3s-hamburg.de/downloads");
         download();
       

	}
	@SuppressWarnings("unused")
	@FXML
	private void download() {
		  browser.getEngine().locationProperty().addListener(new ChangeListener<String>() {

			
				int readBytes;
		
				@Override
				
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newLoc) {
					if (newLoc.contains("upload")) {
			            
						FileChooser chooser = new FileChooser();
			            chooser.setTitle("Save " + newLoc);
			    	
					
						String fileName;
			 
						File saveFile = chooser.showSaveDialog(button.getScene().getWindow());
						  int b = 0;
			            if (saveFile != null) {
			            	 BufferedInputStream  is = null;
			                 BufferedOutputStream os = null;
			                 try {
			                     try {
									is = new BufferedInputStream(new URL(newLoc).openStream());
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			                     try {
									os = new BufferedOutputStream(new FileOutputStream(saveFile));
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

			                     try {
									while ((readBytes = is.read()) != -1) {
									 os.write(b);
									 }
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			                 } finally {
			                     try { if (is != null) is.close(); } catch (IOException e) {}
			                     try { if (os != null) os.close(); } catch (IOException e) {}
			                 }
			            }	
			        
			            
			        }
					
					
					
				}
			});
		/*
		 * BufferedInputStream  is = null;
			                BufferedOutputStream os = null;
			                try {
			                    is = new BufferedInputStream(new URL(newLoc).openStream());
			                    os = new BufferedOutputStream(new FileOutputStream(saveFile));

			                    
								while ((readBytes = is.read()) != -1) {
								byte[] b = null;
								os.write(b);
			                    }
			                } catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally {
			                    try { if (is != null) is.close(); } catch (IOException e) {}
			                    try { if (os != null) os.close(); } catch (IOException e) {}
			                }
*/
		
		
	}
}
