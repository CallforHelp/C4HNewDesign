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
	private TrayIconKlasse TrayIconC4H;
	private String cssPath="/application.css";
	
	
	@Override
	public void start(Stage primaryStage) throws AWTException{
		
		
		try {
			
			
			//System.out.println("classpath=" + System.getProperty("java.class.path"));
			
			
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
			scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
	
			TrayIconC4H= new TrayIconKlasse(page, primaryStage);
			
			primaryStage.show();
			
		} catch (IOException e) {
			e.getCause().printStackTrace();
			}
		
		
	}
		public static void main(String[] args) {
			launch(args);
		}
		
		

}

