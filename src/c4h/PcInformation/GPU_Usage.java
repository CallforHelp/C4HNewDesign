package c4h.PcInformation;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Die Klasse GPU_Usage überwacht die GPU-Auslastung und aktualisiert
 * einen ProgressIndicator in regelmäßigen Abständen.
 */
public class GPU_Usage {

	private static final int REFRESH_INTERVAL_SECONDS = 1;
	private static final int UPDATE_INTERVAL_MILLISECONDS = REFRESH_INTERVAL_SECONDS * 500;

	private Psapi.PerformanceInformation performanceInformation = new Psapi.PerformanceInformation();
	private ScheduledExecutorService executorService;

	/**
	 * Überwacht die GPU-Auslastung und aktualisiert den ProgressIndicator.
	 *
	 * @param indicator der ProgressIndicator, der die GPU-Auslastung anzeigt
	 */
	public void monitorGPUUsage(ProgressIndicator indicator) {
		Task<Void> task = new Task<Void>() {
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
	 *
	 * @return die GPU-Auslastung als Wert zwischen 0.0 und 1.0
	 */
	private double getGPUUsage() {
		if (Psapi.INSTANCE.GetPerformanceInfo(performanceInformation, performanceInformation.size())) {
			int physicalTotal = performanceInformation.PhysicalTotal;
			int physicalAvailable = performanceInformation.PhysicalAvailable;
			// Berechnet die GPU-Auslastung als Verhältnis von genutztem zu insgesamt verfügbarem Speicher
			return (double) (physicalTotal - physicalAvailable) / physicalTotal;
		} else {
			System.err.println("Fehler beim Abrufen der Leistungsdaten.");
			return 0.0;
		}
	}

	/**
	 * Die Psapi-Schnittstelle zur Interaktion mit der Psapi-Bibliothek.
	 */
	public interface Psapi extends StdCallLibrary {
		Psapi INSTANCE = Native.load("Psapi", Psapi.class);

		/**
		 * Ruft Leistungsinformationen vom System ab.
		 *
		 * @param pPerformanceInformation Struktur, die die Leistungsinformationen enthält
		 * @param cb die Größe der Struktur in Bytes
		 * @return true, wenn die Informationen erfolgreich abgerufen wurden, andernfalls false
		 */
		boolean GetPerformanceInfo(PerformanceInformation pPerformanceInformation, int cb);

		/**
		 * Die PerformanceInformation-Struktur enthält Systemleistungsdaten.
		 */
		class PerformanceInformation extends Structure {
			public int cb;
			public int CommitTotal;
			public int CommitLimit;
			public int CommitPeak;
			public int PhysicalTotal;
			public int PhysicalAvailable;
			public int SystemCache;
			public int KernelTotal;
			public int KernelPaged;
			public int KernelNonpaged;
			public int PageSize;
			public int HandleCount;
			public int ProcessCount;
			public int ThreadCount;

			@Override
			protected List<String> getFieldOrder() {
				return Arrays.asList("cb", "CommitTotal", "CommitLimit", "CommitPeak", "PhysicalTotal",
						"PhysicalAvailable", "SystemCache", "KernelTotal", "KernelPaged", "KernelNonpaged",
						"PageSize", "HandleCount", "ProcessCount", "ThreadCount");
			}
		}
	}
}
