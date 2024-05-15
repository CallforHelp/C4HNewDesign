package c4h.PcInformation;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import java.lang.management.*;
import javax.imageio.ImageIO;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;


import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.Psapi;
import com.sun.jna.win32.StdCallLibrary;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class CPU_Usage {
	
	private ProgressIndicator indictor2 = new ProgressIndicator(0);

	
	public void monitorCPUUsage(ProgressIndicator indicato) {
		// TODO Auto-generated method stub

		indictor2.setMinSize(100, 100);
	    indictor2.setProgress(0); // Setze den Anfangsfortschritt auf 0

	    Timeline timeline = new Timeline(
	        new KeyFrame(Duration.ZERO, e -> {
	            OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	            double processCpuLoad = bean.getSystemLoadAverage();
	            indictor2.setProgress(processCpuLoad);
	        }),
	        new KeyFrame(Duration.seconds(2))
	    );
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
		
	}

}
