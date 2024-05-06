package c4h;

	
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


	public class TrayIconKlasse {
		
		private SystemTray         tray        = SystemTray.getSystemTray();
		public TrayIcon trayIcon    = new TrayIcon(createImage("/C4HNewDesign/ressource/images/bulb.png", "trayIcon"));
		 // Füge ein Popup-Menü zum Tray-Icon hinzu
        private PopupMenu popupMenu = new PopupMenu();
        private MenuItem exitItem = new MenuItem("Exit");

	    public TrayIconKlasse(Parent parent, Stage stage) {
	        
	    	// Überprüfe, ob das System-Tray unterstützt wird
	        if (!SystemTray.isSupported()) {
	            System.out.println("System tray is not supported.");
	            return;
	        }
	        
	        // Füge das Tray-Icon zum System-Tray hinzu
	        try {
     			tray.add(trayIcon);
     		}catch(Throwable e2) {
         		System.out.println("TrayIcon could not be added."+e2.getMessage());
         		return;
         	}
	        // Füge Tooltip hinzu
			trayIcon.setToolTip("Schul-Support-Service - Call for Help");


	       
	      
	        
	        exitItem.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Schließe die Anwendung beim Klicken auf "Exit"
	                Platform.exit();
	                tray.remove(trayIcon);
	            }
	        });
	        popupMenu.add(exitItem);
	        trayIcon.setPopupMenu(popupMenu);

	    	
        	


	        // Füge Listener für Doppelklick auf Tray-Icon hinzu
	        trayIcon.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Hier kannst du die Aktion ausführen, wenn das Tray-Icon angeklickt wird
	                Platform.runLater(() -> {
	                    // Hier kannst du mit dem Parent-Element interagieren, z.B. die Szene wechseln
	                	stage.show();
	                	//	stage =   scene.getWindow();
	                	if (!stage.isShowing()) {
	                		System.out.println("stage is Hide");
	                		Stage newStage= new Stage();
	                		Scene newScene= new Scene(parent);
	                		newStage.setScene(newScene);
	                		newStage.show();
	                	}
	                		
	                });
	            }
	        });
	    
	    }
		protected java.awt.Image createImage(String path, String description) {
			URL imageURL = C4H.class.getResource(path);
			if (imageURL == null) {
				System.err.println("Resource not found: " + path);
				return null;
			} else {
				return (new ImageIcon(imageURL, description)).getImage();
			}
		}

		
	    

}



