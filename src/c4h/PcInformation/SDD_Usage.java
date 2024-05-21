package c4h.PcInformation;

import java.io.File;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;

/**
 * Diese Klasse überwacht die RAM-Nutzung und aktualisiert einen Fortschrittsindikator basierend auf der genutzten RAM-Menge.
 * 
 * @version 1.0
 * @author Helmi
 */
public class SDD_Usage {

	/**
	 * Lädt die Festplatten-Datenrate und aktualisiert den Fortschrittsindikator.
	 */

	public void monitorSDDUsage(ProgressIndicator indicator) {
		indicator.setMinSize(100, 100);

		Task<Void> driveTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				while (true) {
					double driveActivity = getHardDriveDataRate();

					Platform.runLater(() -> indicator.setProgress(driveActivity / 100.0));

					Thread.sleep(100);
				}
			}
		};

		Thread driveThread = new Thread(driveTask);
		driveThread.setDaemon(true);
		driveThread.start();
	}
	/**
	 * Überwacht die Festplattenaktivität und gibt die Auslastung in Prozent zurück.
	 * 
	 * @return Die Festplattenaktivität in Prozent
	 */
	private double getHardDriveDataRate() {
		String userHome = System.getProperty("user.home");
		String drivePath = userHome;

		try {
			File drive = new File(drivePath);

			if (drive.exists() && drive.canRead()) {
				long totalSpace = drive.getTotalSpace();
				long freeSpace = drive.getFreeSpace();

				double usagePercent = 1.0 - ((double) freeSpace / totalSpace);

				return usagePercent * 100.0;
			} else {
				return -1;
			}
		} catch (Exception e) {
			return -1;
		}
	}
}
