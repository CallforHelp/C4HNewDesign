package c4h.PcInformation;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GPU_Usage {

	private static final int REFRESH_INTERVAL_SECONDS = 1;
	private static final int UPDATE_INTERVAL_MILLISECONDS = REFRESH_INTERVAL_SECONDS * 1000;

	private static ScheduledExecutorService executorService;
	private static Task<Void> task;

	/**
	 * Überwacht die GPU-Auslastung und aktualisiert den ProgressIndicator.
	 *
	 * @param indicator der ProgressIndicator, der die GPU-Auslastung anzeigt
	 */
	public static void monitorGPUUsage(ProgressIndicator indicator) {
		task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				while (!isCancelled()) {
					double gpuUsage = getGPUUsage();
					// Aktualisiert den ProgressIndicator auf dem JavaFX-Anwendungsthread
					Platform.runLater(() -> indicator.setProgress(gpuUsage));
					Thread.sleep(UPDATE_INTERVAL_MILLISECONDS);
				}
				return null;
			}
		};

		executorService = Executors.newSingleThreadScheduledExecutor();
		// Startet die Überwachung sofort und wiederholt sie alle REFRESH_INTERVAL_SECONDS Sekunden
		executorService.scheduleWithFixedDelay(task, 0, REFRESH_INTERVAL_SECONDS, TimeUnit.SECONDS);
	}

	/**
	 * Ruft die aktuelle GPU-Auslastung ab.
	 * Hier wird eine simulierte GPU-Auslastung zurückgegeben.
	 *
	 * @return die GPU-Auslastung als Wert zwischen 0.0 und 1.0
	 */
	private static double getGPUUsage() {
		// Simulierte GPU-Auslastung für Demonstrationszwecke
		return Math.random();
	}

	/**
	 * Stoppt die Überwachung der GPU-Auslastung.
	 */
	public static void stopMonitoring() {
		if (task != null) {
			task.cancel();
		}
		if (executorService != null) {
			executorService.shutdown();
		}
	}

	public static void close() {
		stopMonitoring();
	}
}
