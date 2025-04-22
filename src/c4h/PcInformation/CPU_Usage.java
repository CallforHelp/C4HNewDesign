package c4h.PcInformation;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ProgressIndicator;
import javafx.util.Duration;

/**
 * Die Klasse CPU_Usage überwacht die CPU-Auslastung des aktuellen Prozesses
 * und aktualisiert einen ProgressIndicator in regelmäßigen Abständen.
 */
public class CPU_Usage {

	/**
	 * Überwacht die CPU-Auslastung und aktualisiert den ProgressIndicator.
	 *
	 * @param indicator der ProgressIndicator, der die CPU-Auslastung anzeigt
	 */
	public static void monitorCPUUsage(ProgressIndicator indicator) {
		// Setzt die Mindestgröße für den Indicator
		indicator.setMinSize(100, 100);

		// Erhalte das erweiterte OperatingSystemMXBean
		OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

		// Erstelle eine Timeline, um die CPU-Auslastung periodisch zu aktualisieren
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.ZERO, e -> {
					// Hole die CPU-Auslastung des Prozesses (Wert zwischen 0.0 und 1.0)
					double processCpuLoad = osBean.getProcessCpuLoad();
					// Aktualisiere den ProgressIndicator, falls ein gültiger Wert zurückkommt
					if (processCpuLoad >= 0) {
						indicator.setProgress(processCpuLoad);
					}
				}),
				new KeyFrame(Duration.seconds(2))  // Aktualisiere alle 2 Sekunden
				);

		// Setzt die Timeline auf unendliche Wiederholung und startet sie
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	public static void close() {
		// TODO Auto-generated method stub
		
	}
}
