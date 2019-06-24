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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class PcInformationControler implements Initializable {

    @FXML
    private Button button;
    @FXML
    private Button buttonPcInfo;
    @FXML
    private Button buttonNetzwerkInfo;
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private AnchorPane Container;
    @FXML
    private Pane PaneNetzwerk = new Pane();
    @FXML
    private Pane PanePcInformation = new Pane();
    
    //PC Info
    @FXML
    private Text hostName = new Text();
    @FXML
    private Text UserName = new Text();
    @FXML
    private Text SchulNummer = new Text();
    @FXML
    private Text OSVersion = new Text();
    @FXML
    private Text OsArchitekture = new Text();
    @FXML
    private Text MusterImage = new Text();
    
    //NetzwerkInfo
    @FXML
    private Text iPAdresse = new Text();
    @FXML
    private Text subnetzmaske = new Text();
    @FXML
    private Text macAdresse = new Text();
    @FXML
    private Text domain = new Text();
    @FXML
    private Text gateway= new Text();
    @FXML
    private Text dhcp = new Text();
    @FXML
    private Text dns = new Text();
    
    
    PcInformation info = new PcInformation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
		System.out.println("PcInformation");
		try {
			loadPcInformation() ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
    @FXML
    private void loadPcInformation() throws IOException {
    	System.out.println("Load Pc-Information");
    	
    	PaneNetzwerk.setVisible(true);
    	hostName.setText(info.getLocalHost());
    	UserName.setText(info.getUserName());
    	try {
			SchulNummer.setText(info.getSchulNummer());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	OsArchitekture.setText(info.getOSArchitecture());
    	OSVersion.setText(info.getOSversion());
    	try {
			MusterImage.setText(info.getMusterImages());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    @FXML
    private void loadNetzwerkInformation(ActionEvent event) throws IOException {
    	
    	System.out.println("Load NetzwerkInformation");
 
    	PaneNetzwerk.setVisible(false);
    	iPAdresse.setText(info.getLocalAdresse());
    	subnetzmaske.setText(info.getSubnetMask());
    	macAdresse.setText(info.getMacAddress());
    	domain.setText(info.getMachindomain());
    	gateway.setText(info.getDefaultgateway());
    	dhcp.setText(info.getDHCPServer());
    	dns.setText(info.getDNSServer());
    	
    	
    }

}
