package c4h.PcInformation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * Erstelle eine MAP mit den PC Modell Inhalte 
 * Key[0]= (Name) PC Modell
 * Value[0]= Modell Name und Bennenung
 * Value[1]= Optische Merkmale / Sicherheitsupdates
 * Value[2]= Hardware
 * Value[3]= Windows 10 64Bit Treiber
 * Value[4]= Herstellungsjahr/Ausschreibung
 * Value[5]=  Dauer 3S Support
 * Value[6]=  Win11 Kompatibilität 
 * 
 *  * @author Helmi Bani
 * @version 1.0
 * 
 */

public class ParsePcModelInMap {
    
	
	private static Map<String, ArrayList<String>> mapList = new HashMap<>();

	public String filePathAuschreiubungPCPath = "/ausschreibungsPC.txt";

	
	private String keyPcModell;
	
	
	
	public String getKeyPcModell() {
		return keyPcModell;
	}



	public void setKeyPcModell(String keyPcModell) {
		this.keyPcModell = keyPcModell;
	}



	public ParsePcModelInMap() {
		// TODO Auto-generated constructor stub
		parseText(filePathAuschreiubungPCPath);
	} 
	
	

	public Map<String, ArrayList<String>> parseText(String filePath) {
		
        String currentKey = null;
        InputStream inputStream = getClass().getResourceAsStream(filePath);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
            	line = line.replaceAll("\\|.*?\\|", "");
                // Wenn die Zeile "datei" enthält, setze foundDatei auf true
                if (line.toLowerCase().contains("datei")) {
                	   
                    // Setze den aktuellen Schlüssel zurück, wenn eine neue Sektion beginnt
                    currentKey = null;
                   // continue; // Springe zur nächsten Iteration
                }

                // Wenn "datei" gefunden wurde, bearbeite die Zeile und füge sie zur relevanten Liste hinzu
                if (currentKey != null) {
                    // Wenn die Zeile leer ist oder das Ende der Sektion erreicht ist, setze den aktuellen Schlüssel zurück
                    if (line.contains("|-")) {
                        currentKey = null;
                        continue;
                    }
                    mapList.get(currentKey).add(line);
                }

                // Überprüfe, ob die Zeile eine Klammer enthält
                if (line.contains("(") && line.contains(")")) {
                    // Überprüfe, ob die Klammer von einem Zollzeichen begrenzt ist
                    //Pattern pattern = Pattern.compile("\\(.*?\\)");
                  //  Pattern pattern = Pattern.compile("\\((.{4})\\)");
                	 Pattern pattern = Pattern.compile("\\((?=.*\\d)(?=.*[a-zA-Z]).{1,4}\\)");
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        String match = matcher.group();
                        if (!match.contains("\"")) { // Überprüfe, ob die Klammer kein Zollzeichen enthält
                            
                        	// Extrahiere den Text innerhalb der Klammern
                            
                        	String key = match.replace("(", "");
                            key = key.replace(")", "");


                            // Wenn eine neue Klammer gefunden wird, erstelle eine neue Map
                            mapList.put(key, new ArrayList<>());
                            currentKey = key;
                            mapList.get(currentKey).add(line);
                            
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File not Found");
        }
       
		return mapList;


		
	}
	
	public String findePcModell(String PCModell) {
		String valueToFind = PCModell;
		 String keyPCModell = getKeyByExactValueInList(mapList, valueToFind);
		 if (keyPCModell != null) {
	            System.out.println("Der Schlüssel für den Wert '" + valueToFind + "' ist: " + keyPCModell);
	            setKeyPcModell(keyPcModell);
	            return keyPCModell;
	        } else {
	            System.out.println("Der PC-Modell '" + valueToFind + "' ist nich in der Ausschreibung.");
	            return null;
	        }
	}
	
	public static <K> K getKeyByExactValueInList(Map<K, ArrayList<String>> map, String value) {
        for (Map.Entry<K, ArrayList<String>> entry : map.entrySet()) {
            ArrayList<String> list = entry.getValue();
            for (String listItem : list) {
                if (listItem.contains(value)) {
                    return entry.getKey();
                }
            }
        }
        return null; // Wenn der Wert nicht gefunden wird
    }
	
	 public static String findSupportEndethValue(String searchKey) {
	        // Suche den Wert für den gegebenen Schlüssel in der Map
	        ArrayList<String> values = mapList.get(searchKey);
	        if (values != null && values.size() >= 6) {
	            // Wenn der Wert gefunden wird und die ArrayList mindestens 4 Elemente hat, gib den vierten Wert zurück
	            return values.get(5); // Index 4 entspricht dem 5 Eintrag in der ArrayList (0-basiert)
	        } else {
	            // Wenn der Schlüssel nicht gefunden wird oder die ArrayList weniger als 4 Elemente hat, gib null zurück
	        	System.out.println("supportEnde keine Information ");
	            return null;
	        }
	 }
	 public static String findWindows11Support(String searchKey) {
	        // Suche den Wert für den gegebenen Schlüssel in der Map
	        ArrayList<String> values = mapList.get(searchKey);
	        if (values != null && values.size() >= 6) {
	            // Wenn der Wert gefunden wird und die ArrayList mindestens 4 Elemente hat, gib den vierten Wert zurück
	            return values.get(6); // Index 5 entspricht dem 6 Eintrag in der ArrayList (0-basiert)
	        } else {
	            // Wenn der Schlüssel nicht gefunden wird oder die ArrayList weniger als 4 Elemente hat, gib null zurück
	        	System.out.println("Windows 11 Support  keine Information ");
	            return null;
	        }
	 }
	 public static String findKaufDatum(String searchKey) {
	        // Suche den Wert für den gegebenen Schlüssel in der Map
	        ArrayList<String> values = mapList.get(searchKey);
	        if (values != null && values.size() >= 6) {
	            // Wenn der Wert gefunden wird und die ArrayList mindestens 4 Elemente hat, gib den vierten Wert zurück
	            return values.get(4); // Index 3 entspricht dem vierten Eintrag in der ArrayList (0-basiert)
	        } else {
	            // Wenn der Schlüssel nicht gefunden wird oder die ArrayList weniger als 4 Elemente hat, gib null zurück
	        	System.out.println("Kaufdatum supportEnde keine Information ");
	            return null;
	        }
	 }
	 
	 
	 public void printMAP() {
	        
			//Print Maps
	        // Ausgabe der Maps
	        for (Map.Entry<String, ArrayList<String>> entry : mapList.entrySet()) {
	            String key = entry.getKey();
	            ArrayList<String> value = entry.getValue();
	            System.out.println("Pc Modell: '" + key + "':");
	            for (String line : value) {
	                System.out.println(line);
	            }
	            System.out.println(); // Leere Zeile zur Trennung
	        }
		}
		
	
	
    
	public static void main(String[] args) {
		
		
		
		
		ParsePcModelInMap parse = new ParsePcModelInMap();
		
		String Key= "HP09";
		
		//parse.printMAP();
		try {
			System.out.println(parse.findSupportEndethValue(Key));
			System.out.println(parse.findKaufDatum(Key));
			System.out.println(parse.findWindows11Support(Key));
			System.out.println(parse.findePcModell(Key));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        
 
    }
}
