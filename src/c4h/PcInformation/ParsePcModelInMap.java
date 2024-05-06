package c4h.PcInformation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
 * Value[0]= Optische Merkmale / Sicherheitsupdates
 * Value[1]= Hardware
 * Value[2]= Windows 10 64Bit Treiber
 * Value[3]= Herstellungsjahr/Ausschreibung
 * Value[4]=  Dauer 3S Support
 * Value[5]=  Win11 Kompatibilität 
 * 
 */

public class ParsePcModelInMap {
    
	
	private Map<String, ArrayList<String>> mapList = new HashMap<>();

	final static  String filePathDesktops = "C:\\Users\\PC-SWV\\eclipse-workspace\\TestProjekt\\src\\design\\Desktops.txt";
	final static String filePathminiDesktops = "C:\\Users\\PC-SWV\\eclipse-workspace\\TestProjekt\\src\\design\\miniDesktops.txt";
	final static String filePathLaptops = "C:\\Users\\PC-SWV\\eclipse-workspace\\TestProjekt\\src\\design\\LapTops.txt";
	
	final static String filePathAuschreiubungPC = "C:\\Users\\PC-SWV\\Documents\\GitHub\\C4HNewDesign\\src\\c4h\\PcInformation\\ausschreibungsPC.txt";
	
	final String filePathAuschreiubungPCPath = "C:\\Users\\PC-SWV\\Documents\\GitHub\\C4HNewDesign\\ressource\\ausschreibungsPC.txt";
	
	public ParsePcModelInMap() {
		// TODO Auto-generated constructor stub
		parseText(filePathAuschreiubungPCPath);
	}

	public Map<String, ArrayList<String>> parseText(String filePath) {
		try {
            FileReader fileReader = new FileReader(filePath);
            // Proceed with reading the file
        } catch (FileNotFoundException e) {
            System.out.println("The specified file does not exist or cannot be found.");
            e.printStackTrace();
        }
		
        String currentKey = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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
        }
       
		return mapList;


		
	}
	
	public String findePcModell(String PCModell) {
		String valueToFind = PCModell;
		 String keyPCModell = getKeyByExactValueInList(mapList, valueToFind);
		 if (keyPCModell != null) {
	            System.out.println("Der Schlüssel für den Wert '" + valueToFind + "' ist: " + keyPCModell);
	            
	            return keyPCModell;
	        } else {
	            System.out.println("Der Wert '" + valueToFind + "' wurde nicht in der Map gefunden.");
	            return "";
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
		parse.printMAP();
		


        
 
    }
}
