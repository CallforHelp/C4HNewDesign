package c4h.PcInformation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
	


	public Map<String, ArrayList<String>> parseText(String filePath) {
		
    
        String currentKey = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
            	line = line.replaceAll("\\|.*?\\|", "");
                // Wenn die Zeile "datei" enthält, setze foundDatei auf true
                if (line.toLowerCase().contains("datei")) {
                	   
                    // Setze den aktuellen Schlüssel zurück, wenn eine neue Sektion beginnt
                    currentKey = null;
                    continue; // Springe zur nächsten Iteration
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
                    Pattern pattern = Pattern.compile("\\((.{4})\\)");
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
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        

		return mapList;


		
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
		parse.parseText(filePathminiDesktops);


        
 
    }
}
