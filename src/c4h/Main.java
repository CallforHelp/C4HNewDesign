package c4h;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;



public class Main extends Application {
	
	private Stage primaryStage;
	private AnchorPane rootLayout;
    private double xOffset = 0;
    private double yOffset = 0;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		 initC4HRootLayout();
	}
	
	public void initC4HRootLayout() {
		
	    try {
	    	primaryStage.initStyle(StageStyle.UNDECORATED);
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(Main.class.getResource("initC4HRootLayout.fxml"));
            rootLayout = (AnchorPane) loader.load();
            
            //Move Stage 
            rootLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            rootLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    primaryStage.setX(event.getScreenX() - xOffset);
                    primaryStage.setY(event.getScreenY() - yOffset);
                }
            });


            // Set the application icon.
            this.primaryStage.getIcons().add(new Image("images/ico.jpg"));
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
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
