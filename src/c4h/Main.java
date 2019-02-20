package c4h;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;




public class Main extends Application {
	private double xOffset = 0;
	private double yOffset = 0;
		@Override
	public void start(Stage primaryStage){
		primaryStage.initStyle(StageStyle.UNDECORATED);
		try {
			AnchorPane page = (AnchorPane)FXMLLoader.load(getClass().getResource("initC4HRootLayout.fxml"));
				
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
	

/*	
	private Stage primaryStage;
	private AnchorPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		 initC4HRootLayout();
		 createMenuLaout();
	}
	

	public void initC4HRootLayout() {
		
	    try {
	    	primaryStage.initStyle(StageStyle.UNDECORATED);
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(Main.class.getResource("initC4HRootLayout.fxml"));
            rootLayout = (AnchorPane) loader.load();
            
            


            // Set the application icon.
          //  this.primaryStage.getIcons().add(new Image("images/ico.jpg"));
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	@FXML
	private void handleButtonAction(ActionEvent event) {
	    // Button was clicked, do something…
	  System.out.println("Button Action\n");
	}

	public static void main(String[] args) {
		launch(args);
	}*/
}
