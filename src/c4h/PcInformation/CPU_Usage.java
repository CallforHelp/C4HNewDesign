package c4h.PcInformation;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ProgressIndicator;
import javafx.util.Duration;


public class CPU_Usage {
		
	public void monitorCPUUsage(ProgressIndicator indicator) {
	    // Setze die Mindestgröße für den Indicator
	    indicator.setMinSize(100, 100);
	    
	    // Erhalte das erweiterte OperatingSystemMXBean
	    OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

	    Timeline timeline = new Timeline(
	        new KeyFrame(Duration.ZERO, e -> {
	            // Hole die CPU-Auslastung (0.0 bis 1.0)
	            double processCpuLoad = osBean.getProcessCpuLoad();
	            
	            // Update den Progress Indicator, falls ein gültiger Wert zurückkommt
	            if (processCpuLoad >= 0) {
	                indicator.setProgress(processCpuLoad);
	            }
	        }),
	        new KeyFrame(Duration.seconds(2))
	    );
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
	}

}
