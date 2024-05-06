package c4h;
	
import java.awt.AWTException;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class C4H extends Application {
	private double xOffset = 0;
	private double yOffset = 0;
	TrayIconKlasse TrayIconC4H;
	
	@Override
	public void start(Stage primaryStage) throws AWTException{
		
		
		try {
			Parent page = FXMLLoader.load(getClass().getResource("/c4h/startView/StartView.fxml"));	
			 
			//Move Stage 
			
			  page.setOnMousePressed(new EventHandler<MouseEvent>() {
			  
			  @Override public void handle(MouseEvent event) {
				  xOffset = event.getSceneX();
				  yOffset = event.getSceneY(); } }); 
			  

			  
			  page.setOnMouseDragged(new EventHandler<MouseEvent>() {
			  
			  @Override public void handle(MouseEvent event) {
			  primaryStage.setX(event.getScreenX() - xOffset);
			  primaryStage.setY(event.getScreenY() - yOffset); } });
			 
	        
	        Scene scene = new Scene(page);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
	
			TrayIconC4H= new TrayIconKlasse(page, primaryStage);
			
			primaryStage.show();
			
		} catch (IOException e) {
				e.printStackTrace();
			}
		
	
	
		    
			
			// Add a TrayIcon that opens/closes theimaryStage when clicked
			/*
			 * trayIcon.addActionListener(new ActionListener() {
			 * 
			 * @Override public void actionPerformed(ActionEvent e) { // TODO Auto-generated
			 * method stub Platform.runLater(() -> {
			 * 
			 * try { Parent page =
			 * FXMLLoader.load(getClass().getResource("/c4h/startView/StartView.fxml"));
			 * 
			 * //Move Stage
			 * 
			 * page.setOnMousePressed(new EventHandler<MouseEvent>() {
			 * 
			 * @Override public void handle(MouseEvent event) { xOffset = event.getSceneX();
			 * yOffset = event.getSceneY(); } }); page.setOnMouseDragged(new
			 * EventHandler<MouseEvent>() {
			 * 
			 * @Override public void handle(MouseEvent event) {
			 * primaryStage.setX(event.getScreenX() - xOffset);
			 * primaryStage.setY(event.getScreenY() - yOffset); } });
			 * 
			 * 
			 * Scene scene = new Scene(page); primaryStage.setScene(scene);
			 * primaryStage.show();
			 * 
			 * } catch (IOException excep) { excep.printStackTrace(); }
			 * 
			 * 
			 * 
			 * // Perform actions when the tray icon is clicked
			 * primaryStage.setIconified(false); // Restore the main window if minimized
			 * primaryStage.toFront(); // Bring the main window to front });
			 * 
			 * } });
			 */
			
    }
	
	

   

		
		
		
		public static void main(String[] args) {
			launch(args);
		}
		
		

}

