package c4h;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class C4hController {
	@FXML
	private Button myExitButton;
	String imageDecline = "/C4HNewDesign/src/images/decline-button.png";
	
	
	//Constructor
	
	
	@FXML
	private void initialize() {
	//	myExitButton.setGraphic(new ImageView(imageDecline));
		RotateTransition rotation = new RotateTransition(Duration.seconds(0.5), myExitButton);
		rotation.setCycleCount(Animation.INDEFINITE);
		rotation.setByAngle(360);

		myExitButton.setOnMouseEntered(e -> rotation.play());
		myExitButton.setOnMouseExited(e -> rotation.pause());
		myExitButton.setOnAction((event) -> {
			System.out.println("Button Action\n");
			System.exit(0);
		});
	}
}
