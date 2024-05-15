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

public class GPU_Usage {

    private static int REFRESH_INTERVAL_SECONDS = 1;
    private static int UPDATE_INTERVAL_MILLISECONDS = REFRESH_INTERVAL_SECONDS * 1000;

    private Psapi.PerformanceInformation performanceInformation = new Psapi.PerformanceInformation();
    private ScheduledExecutorService executorService;

    // Diese Methode überwacht die GPU-Auslastung und aktualisiert den ProgressIndicator
    public void monitorGPUUsage(ProgressIndicator indicator) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (!isCancelled()) {
                    double gpuUsage = getGPUUsage();
                    Platform.runLater(() -> indicator.setProgress(gpuUsage));
                    Thread.sleep(UPDATE_INTERVAL_MILLISECONDS);
                }
                return null;
            }
        };

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(task, 0, REFRESH_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    // Diese Methode ruft die GPU-Auslastung ab
    private double getGPUUsage() {
        if (Psapi.INSTANCE.GetPerformanceInfo(performanceInformation, performanceInformation.size())) {
            int physicalTotal = performanceInformation.PhysicalTotal;
            int physicalAvailable = performanceInformation.PhysicalAvailable;
            return (double) (physicalTotal - physicalAvailable) / physicalTotal;
        } else {
            System.err.println("Fehler beim Abrufen der Leistungsdaten.");
            return 0.0;
        }
    }

    // Definition der Psapi-Bibliothek und der zugehörigen Struktur
    public interface Psapi extends StdCallLibrary {
        Psapi INSTANCE = Native.load("Psapi", Psapi.class);

        boolean GetPerformanceInfo(PerformanceInformation pPerformanceInformation, int cb);

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
