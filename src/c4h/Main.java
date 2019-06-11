package c4h;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;




public class Main extends Application {
	private double xOffset = 0;
	private double yOffset = 0;
		@Override
	public void start(Stage primaryStage){
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.getIcons().add(new Image("/images/decline-button.png"));
			try {
				Parent page = FXMLLoader.load(getClass().getResource("/startView/StartView.fxml"));
				
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
		}
		
		public static void main(String[] args) {
			launch(args);
		}
}
