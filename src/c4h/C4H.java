package c4h;
	
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class C4H extends Application {
	private double xOffset = 0;
	private double yOffset = 0;
	
	
	@FXML
	private Button ExitButton;
	public TrayIcon trayIcon    = new TrayIcon(createImage("images/bulb.png", "trayIcon"));
	SystemTray         tray        = SystemTray.getSystemTray();

	
	@Override
	public void start(Stage primaryStage) throws AWTException{
		
		primaryStage.initStyle(StageStyle.UNDECORATED);
		try {
			Parent page = FXMLLoader.load(getClass().getResource("/c4h/startView/StartView.fxml"));	
			 
			//Move Stage 
	        page.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
	        	public void handle(MouseEvent event) {
					xOffset = event.getSceneX();
					yOffset = event.getSceneY();
	            }
	        });
	        page.setOnMouseDragged(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                primaryStage.setX(event.getScreenX() - xOffset);
	                primaryStage.setY(event.getScreenY() - yOffset);
	            }
	         });
	      
	        
	        Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			
		} catch (IOException e) {
				e.printStackTrace();
			}
		
		
		 // Set up TrayIcon
			if (!SystemTray.isSupported()) {
				System.out.println("SystemTray is not supported");
				return;
			}
			trayIcon.setToolTip("Schul-Support-Service - Call for Help");
		    
			try {
     			tray.add(trayIcon);
     		}catch(Throwable e2) {
         		System.out.println("TrayIcon could not be added."+e2.getMessage());
         		return;
         	}
			// Add a TrayIcon that opens/closes theimaryStage when clicked
			trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (e.getButton() == java.awt.event.MouseEvent.BUTTON1) { // Left mouse button
                        Platform.runLater(() -> {
                            try {
                                primaryStage.setResizable(true);
                                primaryStage.show();
                                primaryStage.toFront();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });
                    }
                }
            });
			 primaryStage.show();
			
    }

		
		
		
		public static void main(String[] args) {
			launch(args);
		}
		
		
		
		protected static Image createImage(String path, String description) {
			URL imageURL = C4H.class.getResource(path);
			if (imageURL == null) {
				System.err.println("Resource not found: " + path);
				return null;
			} else {
				return (new ImageIcon(imageURL, description)).getImage();
			}
		}
	   
}

