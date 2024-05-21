package c4h.PcInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Erstelle eine MAP mit den PC Modell Inhalte 
 * Key[0]= (Name) PC Modell
 * Value[0]= Modell Name und Bennenung
 * Value[1]= SystemModell aus Windows
 * Value[2]= Optische Merkmale / Sicherheitsupdates
 * Value[3]= Hardware
 * Value[4]= Windows 10 64Bit Treiber
 * Value[5]= Herstellungsjahr/Ausschreibung
 * Value[6]=  Dauer 3S Support
 * Value[7]=  Win11 Kompatibilität 
 * 
 *  * @author Helmi Bani
 * @version 1.0
 * 
 */

public class ParsePcModelInMap {
    
	
	private static Map<String, ArrayList<String>> mapList = new HashMap<>();
	public String filePathAuschreiubungPCPath = "/ausschreibungsPC.txt";
	private String keyPcModell;
	
	

	public Map<String, ArrayList<String>> getMapList() {
		return mapList;
	}



	public static void setMapList(Map<String, ArrayList<String>> mapList) {
		ParsePcModelInMap.mapList = mapList;
	}

	
	
	
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
	            //return KEY
	            setKeyPcModell(keyPcModell);
	            return keyPCModell;
	        } else {
	            //nicht in der ausschreibung return null
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
	 public  String findKaufDatum(String searchKey) {
	        // Suche den Wert für den gegebenen Schlüssel in der Map
	        ArrayList<String> values = mapList.get(searchKey);
	        if (values != null && values.size() >= 8) {
	            // Wenn der Wert gefunden wird und die ArrayList mindestens 4 Elemente hat, gib den vierten Wert zurück
	            return values.get(5); // Index 3 entspricht dem vierten Eintrag in der ArrayList (0-basiert)
	        } else {
	            // Wenn der Schlüssel nicht gefunden wird oder die ArrayList weniger als 4 Elemente hat, gib null zurück
	        	System.out.println("Kaufdatum supportEnde keine Information ");
	            return null;
	        }
	 }
	
	 public String findSupportEndethValue(String searchKey) {
	        // Suche den Wert für den gegebenen Schlüssel in der Map
	        ArrayList<String> values = mapList.get(searchKey);
	        if (values != null && values.size() >= 8) {
	            // Wenn der Wert gefunden wird und die ArrayList mindestens 4 Elemente hat, gib den vierten Wert zurück
	            return values.get(6); // Index 4 entspricht dem 5 Eintrag in der ArrayList (0-basiert)
	        } else {
	            // Wenn der Schlüssel nicht gefunden wird oder die ArrayList weniger als 4 Elemente hat, gib null zurück
	        	System.out.println("supportEnde keine Information ");
	            return null;
	        }
	 }
	 public String findWindows11Support(String searchKey) {
	        // Suche den Wert für den gegebenen Schlüssel in der Map
	        ArrayList<String> values = mapList.get(searchKey);
	        if (values != null && values.size() >= 8) {
	            // Wenn der Wert gefunden wird und die ArrayList mindestens 4 Elemente hat, gib den vierten Wert zurück
	            return values.get(7); // Index 5 entspricht dem 6 Eintrag in der ArrayList (0-basiert)
	        } else {
	            // Wenn der Schlüssel nicht gefunden wird oder die ArrayList weniger als 4 Elemente hat, gib null zurück
	        	System.out.println("Windows 11 Support  keine Information ");
	            return null;
	        }
	 }


}
