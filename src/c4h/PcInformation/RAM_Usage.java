package c4h.PcInformation;

import javafx.scene.control.ProgressIndicator;

public class RAM_Usage {
	
	
	
	public void monitorRAMUsage(ProgressIndicator indicator) {
		// Setze die Mindestgröße des Indikators
		indicator.setMinSize(100, 100);


        // Erstelle einen Thread für das RAM-Monitoring
        Thread rammonitor = new Thread(() -> {
            int RAM = 100000;

            while (!Thread.currentThread().isInterrupted()) {
                // Hier wird der RAM-Verbrauch gemessen
                Runtime rt = Runtime.getRuntime();

                // Berechne den genutzten Speicher in Kilobyte
                double usedKB = (double) ((rt.totalMemory() - rt.freeMemory()) / 8192);
                if (usedKB == 8192)
                    usedKB = 0.0;

                // Setze den Fortschritt des Indikators basierend auf dem genutzten RAM
                double progress = usedKB / RAM;
                if (progress > 1.0) // Begrenze den Fortschritt auf 1.0
                    progress = 1.0;
                indicator.setProgress(progress);

                try {
                    // Warte für eine kurze Zeit (500 Millisekunden)
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt(); // Setze den Interrupt-Status erneut
                }
            }
        });

        // Starte den RAM-Monitor-Thread
        rammonitor.start();
	}

}
