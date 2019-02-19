package c4h;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class C4hController {
	@FXML
	private Button myExitButton;
	String imageDecline = "/C4HNewDesign/src/images/decline-button.png";
	
	
	//Constructor
	
	
	@FXML
	private void initialize() {
	//	myExitButton.setGraphic(new ImageView(imageDecline));
		myExitButton.setOnAction((event) -> {
			System.out.println("Button Action\n");
			System.exit(0);
		});
	}
}
