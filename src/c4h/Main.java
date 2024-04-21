package c4h;
	
import java.awt.*;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
	private double xOffset = 0;
	private double yOffset = 0;
	@FXML
	private Button ExitButton;
	static TrayIcon trayIcon    = new TrayIcon(createImage("images/bulb.png", "trayIcon"));
	final SystemTray         tray        = SystemTray.getSystemTray();
	
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
			
			primaryStage.show();
			
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

        
			/*
			 * if (SystemTray.isSupported()) { // Add a TrayIcon that opens/closes the
			 * primaryStage when clicked trayIcon.addActionListener(e -> {
			 * Platform.runLater(() -> { primaryStage.show();
			 * 
			 * }); }); } else { System.out.println("SystemTray is not supported."); }
			 */
       
			trayIcon.addMouseListener(null);

        //primaryStage.show();
    }

		
		
		
		public static void main(String[] args) {
			launch(args);
		}
		
		
		
		protected static Image createImage(String path, String description) {
			URL imageURL = Main.class.getResource(path);
			if (imageURL == null) {
				System.err.println("Resource not found: " + path);
				return null;
			} else {
				return (new ImageIcon(imageURL, description)).getImage();
			}
		}
}
