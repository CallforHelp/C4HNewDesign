package c4h.PcInformation;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ProgressIndicator;
import javafx.util.Duration;


public class CPU_Usage {
		
	public void monitorCPUUsage(ProgressIndicator indicator) {
		// TODO Auto-generated method stub

		indicator.setMinSize(100, 100);
	    indicator.setProgress(0); // Setze den Anfangsfortschritt auf 0

	    Timeline timeline = new Timeline(
	        new KeyFrame(Duration.ZERO, e -> {
	            OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	            double processCpuLoad = bean.getSystemLoadAverage();
	            indicator.setProgress(processCpuLoad);
	        }),
	        new KeyFrame(Duration.seconds(2))
	    );
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
		
	}

}
