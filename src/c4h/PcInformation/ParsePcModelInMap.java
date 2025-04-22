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
 * Diese Klasse analysiert eine Textdatei, um eine Map mit PC-Modellinformationen zu erstellen.
 * <p>
 * Key[0] = (Name) PC Modell
 * Value[0] = Modell Name und Benennung
 * Value[1] = Systemmodell aus Windows
 * Value[2] = Optische Merkmale / Sicherheitsupdates
 * Value[3] = Hardware
 * Value[4] = Windows 10 64Bit Treiber
 * Value[5] = Herstellungsjahr/Ausschreibung
 * Value[6] = Dauer 3S Support
 * Value[7] = Win11 Kompatibilität
 * </p>
 * 
 * @version 1.0
 */
public class ParsePcModelInMap {

	private static Map<String, ArrayList<String>> mapList = new HashMap<>();
	public String filePathAuschreiubungPCPath = "/ausschreibungsPC.txt";
	private String keyPcModell;

	/**
	 * Gibt die Map mit den PC-Modellinformationen zurück.
	 *
	 * @return die Map mit den PC-Modellinformationen
	 */
	public Map<String, ArrayList<String>> getMapList() {
		return mapList;
	}

	/**
	 * Setzt die Map mit den PC-Modellinformationen.
	 *
	 * @param mapList die zu setzende Map
	 */
	public static void setMapList(Map<String, ArrayList<String>> mapList) {
		ParsePcModelInMap.mapList = mapList;
	}

	/**
	 * Gibt den aktuellen Schlüssel des PC-Modells zurück.
	 *
	 * @return der aktuelle Schlüssel des PC-Modells
	 */
	public String getKeyPcModell() {
		return keyPcModell;
	}

	/**
	 * Setzt den aktuellen Schlüssel des PC-Modells.
	 *
	 * @param keyPcModell der zu setzende Schlüssel des PC-Modells
	 */
	public void setKeyPcModell(String keyPcModell) {
		this.keyPcModell = keyPcModell;
	}

	/**
	 * Konstruktor, der die Textdatei parst und die Map initialisiert.
	 */
	public ParsePcModelInMap() {
		parseText(filePathAuschreiubungPCPath);
	}

	/**
	 * Parst die gegebene Textdatei und erstellt eine Map mit den PC-Modellinformationen.
	 *
	 * @param filePath der Pfad zur Textdatei
	 * @return die Map mit den PC-Modellinformationen
	 */
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
							String key = match.replace("(", "").replace(")", "");

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

	/**
	 * Findet das PC-Modell in der Map.
	 *
	 * @param PCModell das zu suchende PC-Modell
	 * @return der Schlüssel des PC-Modells, wenn gefunden, sonst null
	 */
	public String findePcModell(String PCModell) {
		String valueToFind = PCModell;
		String keyPCModell = getKeyByExactValueInList(mapList, valueToFind);
		if (keyPCModell != null) {
			setKeyPcModell(keyPcModell);
			return keyPCModell;
		} else {
			return null;
		}
	}

	/**
	 * Findet den Schlüssel in der Map, der eine genaue Übereinstimmung mit dem Wert in der Liste enthält.
	 *
	 * @param map die Map mit den PC-Modellinformationen
	 * @param value der zu suchende Wert
	 * @param <K> der Typ des Schlüssels
	 * @return der Schlüssel, wenn gefunden, sonst null
	 */
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

	/**
	 * Findet und extrahiert spezifische Informationen über das PC-Modell.
	 *
	 * @param searchKey der Schlüssel des PC-Modells
	 * @return der extrahierte Wert oder eine Fehlermeldung, wenn die Informationen fehlen
	 */
	public String findPcModellExtrahiert(String searchKey) {
		ArrayList<String> values = mapList.get(searchKey);
		if (values != null && values.size() >= 6) {
			return values.get(6); // Index 6 entspricht dem siebten Eintrag in der ArrayList (0-basiert)
		} else {
			System.out.println("PC-Modell nicht im Wiki eingetragen");
			return "Info fehlt im Wiki";
		}
	}

	/**
	 * Findet das Kaufdatum für das PC-Modell.
	 *
	 * @param searchKey der Schlüssel des PC-Modells
	 * @return das Kaufdatum oder eine Fehlermeldung, wenn die Informationen fehlen
	 */
	public String findKaufDatum(String searchKey) {
		ArrayList<String> values = mapList.get(searchKey);
		if (values != null && values.size() >= 8) {
			return values.get(5); // Index 5 entspricht dem sechsten Eintrag in der ArrayList (0-basiert)
		} else {
			System.out.println("Support-Ende keine Information");
			return "Info fehlt im Wiki";
		}
	}

	/**
	 * Findet das Support-Enddatum für das PC-Modell.
	 *
	 * @param searchKey der Schlüssel des PC-Modells
	 * @return das Support-Enddatum oder eine Fehlermeldung, wenn die Informationen fehlen
	 */
	public String findSupportEndethValue(String searchKey) {
		ArrayList<String> values = mapList.get(searchKey);
		if (values != null && values.size() >= 8) {
			return values.get(6); // Index 6 entspricht dem siebten Eintrag in der ArrayList (0-basiert)
		} else {
			System.out.println("Windows 11 Support keine Information");
			return "Info fehlt im Wiki";
		}
	}

	/**
	 * Findet die Windows 11 Unterstützung für das PC-Modell.
	 *
	 * @param searchKey der Schlüssel des PC-Modells
	 * @return die Windows 11 Unterstützung oder eine Fehlermeldung, wenn die Informationen fehlen
	 */
	public String findWindows11Support(String searchKey) {
		ArrayList<String> values = mapList.get(searchKey);
		if (values != null && values.size() >= 8) {
			return values.get(7); // Index 7 entspricht dem achten Eintrag in der ArrayList (0-basiert)
		} else {
			System.out.println("Windows 11 Support keine Information");
			return "Info fehlt im Wiki";
		}
	}
}
