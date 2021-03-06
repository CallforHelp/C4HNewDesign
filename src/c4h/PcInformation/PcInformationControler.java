package c4h.PcInformation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class PcInformationControler implements Initializable {

    @FXML
    private Button button;
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private AnchorPane Container;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void loadRoot(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/c4h/startView/startView.fxml"));
        Scene scene = button.getScene();
        root.translateYProperty().set(scene.getWidth());

        AnchorPane parentContainer = (AnchorPane) button.getScene().getRoot();

        parentContainer.getChildren().add(root);


        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(Container);
        });
        timeline.play();
    }

}
