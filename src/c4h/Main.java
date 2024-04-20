package c4h;
	
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	private double xOffset = 0;
	private double yOffset = 0;
		@Override
	public void start(Stage primaryStage){
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
			
			// Set up TrayIcon
	        if (SystemTray.isSupported()) {
	            SystemTray tray = SystemTray.getSystemTray();
	            try {
	                // Add a TrayIcon that opens/closes the primaryStage when clicked
	                TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().createImage("images/bulb.png"));
	                trayIcon.addActionListener(e -> {
	                    Platform.runLater(() -> {
	                        if (primaryStage.isShowing()) {
	                            primaryStage.hide();
	                        } else {
	                            primaryStage.show();
	                            primaryStage.toFront();
	                        }
	                    });
	                });
	                tray.add(trayIcon);

	                // Minimize primaryStage to TrayIcon when the application is minimized
	                primaryStage.setOnCloseRequest(event -> {
	                    event.consume();
	                    primaryStage.hide();
	                });

	            } catch (AWTException e) {
	                System.out.println("TrayIcon could not be added.");
	            }
	        } else {
	            System.out.println("SystemTray is not supported.");
	        }
			
			//primaryStage.show();
			
		} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		public static void main(String[] args) {
			launch(args);
		}
}
