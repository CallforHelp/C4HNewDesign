package c4h.PcInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Eine Klasse zum Zeigen der wichtigsten informationen &uuml;ber den Client.
 * Netwerk und PC Information
 * 
 * @author Helmi Bani
 * @version 2.0
 * 
 */
public class pcInformation {

	public String toolTipFehlerHinweisText;
	
	ArrayList<String> list = new ArrayList<>();
	
	ArrayList<String> PcInfolist = new ArrayList<>();
	ArrayList<String> PcNetzwerklist = new ArrayList<>();
	
	String schulNummer = "";

	public String pcModell ="";

	/**
	 * Die Schulnummer muss erstellt werden damit f&uuml;r die Fehleranmeldung
	 * sofort mitgenommen wird. SchulNummer erstellen
	 * 
	 * @throws Throwable Hostname
	 */
	public pcInformation() {
		try {
//			setPcInfoNetzwerk();
//			setPcInfoSystem();
			getPcModell();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public pcInformation(String s) {

		try {
			getPcModell();
			settSchulNummer();
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Ueberschrift Name Des Tool.
	 * 
	 * @return uebrschrift
	 */
	public String uberSchrift() {

		String ueberschrift = "C4H";

		return ueberschrift;
	}

	/**
	 * Die Zeit wird vom Systemausgelesen.
	 * 
	 * @return Zeit(time)
	 */
	public String timetoBuild() {

		String time = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
		return time;
	}

	/*****************************************************************************************/
	/******************************************* PC_INFO *************************************/

	/**
	 * System Properties Auslesen von OSName
	 * 
	 * @return OsVersion
	 */
	public String getOSversion() {
		
		String OsVersion = new Properties(System.getProperties()).getProperty("os.name");
		
		if (OsVersion == "" || OsVersion == null)
			OsVersion= "Fehler-OSVersion";
		
		PcInfolist.add(OsVersion);
		return OsVersion;
	}
	
    /**
     * Methode zur Überprüfung, ob das Betriebssystem Windows ist.
     * @return true, wenn das Betriebssystem Windows ist, andernfalls false.
     */
    private boolean isWindows() {
        return !getOSversion().contains("w");
    }
	
	/**
	 * System Property ist die Klasse zum Auslesen. von eingenschaften
	 * 
	 * @return userName
	 */
	public String getUserName() {
		
		  if (isWindows()) {
	            return "Mac-Rechner";
	        }
		
		String userName = new Properties(System.getProperties()).getProperty("user.name");
		if (userName == "" || userName == null) {
			userName="Fehler-UserName";
		}
		
		
		return userName;
	}
		
	

	/**
	 * Hier wird anhand der HostName die SchuleNummer ausgelesen.
	 * 
	 * @return Schulnummer
	 * @throws Throwable Hostname
	 */
	public String settSchulNummer() throws Throwable {
		
		if (isWindows()) {
			return "Mac-Rechner";
	    }
		
		String s = System.getenv("SNR");
		
		if (s == null)
			return "Bitte Variable eintagen";
		else
			return s;
	}

	/**
	 * Gibt Einfach die Schulnummer zurueck.
	 * 
	 * @return schulNummer
	 * @throws Throwable Hostname
	 */
	public String getSchulNummer() throws Throwable {
		
		if (isWindows()) {
	            return "Mac-Rechner";
	    }
		
		String schulNummer = System.getenv("SNR");

		if (schulNummer == null) {
			schulNummer="Fehler-SNR Eintag";
		}
		
		return schulNummer;
	}

	/**
	 * System Properties Auslesen von Hersteller
	 * 
	 * @return Hersteller
	 * @throws IOException
	 */
	public String getHersteller() throws IOException {
		
		if (isWindows()) {
            return "Mac-Rechner";
		}
        String line;
        StringBuilder manufacturer = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec("wmic csproduct get vendor");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.contains("Vendor")) {
                    manufacturer.append(line.trim());
                }
            }
            reader.close();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return manufacturer.toString().trim();
	} 
	
	/**
	 * PC-Modell
	 * 
	 * @return PC-Modell
	 * @throws IOException
	 */
	public String getPcModell() throws IOException {
		
		if (isWindows()) {
            return "Mac-Rechner";
		}
		
		try {
			// Befehl ausführen und Ausgabe abrufen
			Process process = Runtime.getRuntime().exec("wmic computersystem get model");
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String Modell;
			int lineCount = 0;
			while ((Modell = reader.readLine()) != null) {
				// Erhöhe den Zähler für jede Zeile
				lineCount++;
				// Überprüfe, ob die dritte Zeile erreicht ist
				if (lineCount == 3) {
					// Gib die dritte Zeile aus
					setPcModell(Modell);
					return Modell;
				}
			}
			// Prozess schließen
			process.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "Fehler-Pc modell";
	}


	private String setPcModell(String modell) {
		// TODO Auto-generated method stub
		return this.pcModell =modell;
	}

	/**
	 * Betriebsystemarchitektur
	 * 
	 * @return OSArchitektur
	 */
	public String getOSArchitecture() {
		
		if (isWindows()) {
            return "Mac-Rechner";
		
		}
		String OsArch = new Properties(System.getProperties()).getProperty("os.arch");
		
	
		return OsArch;
		
	}

	/**
	 * Muster Image von 3s die info bekommen wir aus dem Infobereich des
	 * Betriebsystems
	 * 
	 * @return musterImages
	 * @throws IOException RegEintrag
	 */
	public String getMusterImages() throws Throwable {
	
		if (isWindows()) {
            return "Mac-Rechner";
		}
		
		String musterImages = "";
		String line;
		String location = "HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\OEMInformation";
		String key = "Model";
		Process process = null;
		
		
		 // Run reg query, then read output with
		// StreamReader (internal class)
		process = Runtime.getRuntime().exec("reg query " + location + " /v " + key);
		Reader input = new InputStreamReader(process.getInputStream());
		BufferedReader resultOutput = new BufferedReader(input);
		while ((line = resultOutput.readLine()) != null) {
			if (line.contains("REG")) {
				musterImages = line.split("REG_SZ")[1].trim();
			}
		}		
		if (musterImages == "" || musterImages == null)
			musterImages="Fehler-MusterImage";
		
		
		return musterImages;
	}

	/**
	 * Seriennummer aus dem Rechner auslesen.
	 * 
	 * @return Seriennummer
	 * @throws Exception Seriennummer
	 */
	public String getSerienNummer() throws Exception {
		
		if (isWindows()) {
            return "Mac-Rechner";
		}
		
		try {
            Process process = Runtime.getRuntime().exec("wmic bios get serialnumber");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder serialNumber = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                serialNumber.append(line.trim());
            }
            reader.close();
            process.waitFor();
            return serialNumber.toString().replace("SerialNumber", "").trim();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "keine Seirennummer";
    } 
	
	/**
	 * RechnerTyp aus dem Hostname auslesen.
	 * 
	 * @return RechnerTyp
	 * @throws Exception Hostname

	/*****************************************************************************************/
	/******************************************* NETZWERK ****************************************/

	/**
	 * IpAdresse Auslesen.
	 * 
	 * @return result(IPAdress)
	 * @throws UnknownHostException Hostadresse
	 */
	public String getLocalAdresse() throws UnknownHostException {
		
		if (isWindows()) {
            return "Mac-Rechner";
		}
		
		String IPAdresse = "";
		IPAdresse = InetAddress.getLocalHost().getHostAddress();
	
		if (IPAdresse == "")
			IPAdresse= "Fehler-Netzwerk";
		
		
		return IPAdresse;
		
	}

	/**
	 * MacAdresse auslesen.
	 * 
	 * @return MacAdresse
	 * @throws IOException Commandbefehl
	 */
	public String getMacAddress() throws IOException {
		
		if (isWindows()) {
            return "Mac-Rechner";
		}
		String line;
		String macAdresse = "";
		Process inputStream = Runtime.getRuntime().exec("ipconfig /all");
		InputStreamReader input = new InputStreamReader(inputStream.getInputStream());
		BufferedReader resultOutput = new BufferedReader(input);

		while ((line = resultOutput.readLine()) != null) {
			if (line.contains("Physische Adresse"))
				return macAdresse = line.split(":")[1].trim();
		}
		
		
		return macAdresse;
    
	}

	/**
	 * Localhost auslesen.
	 * 
	 * @return Localhost
	 * @throws UnknownHostException Hostname
	 */
	public String getLocalHost() throws UnknownHostException {
		
		if (isWindows()) {
            return "Mac-Rechner";
		}
		
		String localHost = "";
		localHost = InetAddress.getLocalHost().getHostName();
		
		if (localHost == "")
			localHost= "Fehler-HostName";
		
	
		return localHost;
		
	}

	/**
	 * angemeldete Domaine auslesen.
	 * 
	 * @return domain
	 * @throws IOException commandbefehl
	 */
	public String getMachindomain() throws IOException {
		
		if (isWindows()) {
            return "Mac-Rechner";
		}
		
		String domain="";
		String line;
		Process ipfconfig = null;
		Reader input = null;
		ipfconfig= Runtime.getRuntime().exec("ipconfig /all");
		input = new InputStreamReader(ipfconfig.getInputStream());
		BufferedReader resultOutput = new BufferedReader(input);
		while((line=resultOutput.readLine()) != null){
			if(line.contains("DNS-Suffixsuchliste")){
				domain=line.split(":\\s")[1];
			}
		}
		if(domain=="")
			return "keine-Domain";
		
		return domain;
	}

	/**
	 * Subnetzmask berechnnen und darstellen.
	 * 
	 * @return submasl
	 * @throws SocketException IpAdress
	 * @throws IOException     Localhost
	 */
	public String getSubnetMask() throws SocketException, IOException {
		
		if (isWindows()) {
            return "Mac-Rechner";
		}
		 String line;
	        String subnetMask = "";
	        try {
	            Process process = Runtime.getRuntime().exec("wmic nicconfig get IPSubnet");
	            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	            Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+)");
	            while ((line = reader.readLine()) != null) {
	                Matcher matcher = pattern.matcher(line);
	                if (matcher.find()) {
	                    subnetMask = matcher.group(1);
	                    break;
	                }
	            }
	            reader.close();
	            process.waitFor();
	        } catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	        }
	        
        return subnetMask;
	}
	
	/**
     * Methode zur Konvertierung eines Präfixlängenwerts in eine Subnetzmaske.
     * @param prefixLength Die Länge des Präfixes.
     * @return Die Subnetzmaske entsprechend der gegebenen Präfixlänge.
     */
    private String convertPrefixLengthToMask(int prefixLength) {
        int[] mask = new int[4];
        for (int i = 0; i < 4; i++) {
            if (prefixLength >= 8) {
                mask[i] = 255;
                prefixLength -= 8;
            } else {
                mask[i] = (0xFF << (8 - prefixLength)) & 0xFF;
                prefixLength = 0;
            }
        }
        return mask[0] + "." + mask[1] + "." + mask[2] + "." + mask[3];
    }

	/**
	 * Gateway auslesen.
	 * 
	 * @return defaultgateway
	 * @throws IOException commandbefehl
	 */
	public String getDefaultgateway() throws IOException {
		
		if (isWindows()) {
            return "Mac-Rechner";
		}
		
		String defaultgateway="";
		String line;
		Process ipfconfig=null;
		Reader input = null;
		
		ipfconfig= Runtime.getRuntime().exec("netsh interface ip show config");
		input = new InputStreamReader(ipfconfig.getInputStream());
		BufferedReader resultOutput = new BufferedReader(input);
		while( (line=resultOutput.readLine()) != null ) {
			if(line.contains("Standardgateway")) {
				defaultgateway=line.split(":\\s")[1].trim();
			}
		}
		if(defaultgateway=="")
			return "Fehler-Netzwerk";
		return defaultgateway;
	}

	/**
	 * DHCP serveradresse auslesen.
	 * 
	 * @return dhcpserver
	 * @throws IOException commandbefehl
	 */
	public String getDHCPServer() throws IOException {
		
		if (isWindows()) {
            return "Mac-Rechner";
		}
		String dhcpServer= null;
        try {
            Process process = Runtime.getRuntime().exec("ipconfig /all");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
        	while ((line = reader.readLine()) != null) {
				if (line.contains("DHCP-Server")) {
					dhcpServer = line.split(":\\s")[1];
					break;
				}
        	}
        	reader.close();
            process.destroy();
        
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        
        
		return  dhcpServer;
	}

	/**
	 * DNS serveradresse auslesen.
	 * 
	 * @return dnsserver
	 * @throws IOException commandbefehl
	 */
	public String getDNSServer() throws IOException {
		
		if (isWindows()) {
            return "Mac-Rechner";
		}
		String dnsserver= null;
        try {
            Process process = Runtime.getRuntime().exec("ipconfig /all");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
        	while ((line = reader.readLine()) != null) {
				if (line.contains("DNS-Server")) {
					dnsserver = line.split(":\\s")[1];
					break;
				}
        	}
        	reader.close();
            process.destroy();
        
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        if (dnsserver==null)
        	dnsserver="Fehler-DNS";
      
		return dnsserver;
	}

	/**
	 * Schulnummer Pr&uuml;fen ob es korrekt eingetragen ist. Ein Hinweis als info
	 * wird dargestellt
	 * 
	 * @return true/false
	 * @throws Throwable HostName
	 */
	public boolean pruefeSchulnr() throws Throwable {

		if (getSchulNummer().length() == 4) {
			if ((getSchulNummer().equals("")) || (getSchulNummer().contains("0000"))) {
				return false;
			} else
				return true;
		}
		return false;
	}
	
	/**
	 * Wlan Verbindung aus dem Konsole auslese
	 * 
	 * @return SSID Name
	 */
	public String getConnectedWifiInfo() {
		String SSID = "";
		
		if (!hasWifi())
			return "Kein Wlan Vorhanden";
		
		
		try {
			Process process = Runtime.getRuntime().exec("netsh wlan show interfaces | findstr \"SSID\"");
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("SSID")) {
					return SSID = line.split(":")[1].trim();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SSID;
	}

	/**
	 * MAcAdresse der Aktive Wlan Verbindun
	 * 
	 * @return MACAdresse
	 */
	public String getWifiMacAdresse() {
		if (!hasWifi())
			return "Kein Wlan Vorhanden";
		
		StringBuilder wlanMAC = new StringBuilder();
		try {
			Process process = Runtime.getRuntime().exec("cmd.exe /c netsh wlan show interfaces");
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			String BSSID[] = null;
			while ((line = reader.readLine()) != null) {
				if (line.contains("Physische")) {
					BSSID = line.split(":");
					break;
				}
			}

			int startPos = 1;
			if (startPos < 0 || startPos >= BSSID.length) {
				return ""; // Rückgabe eines leeren Strings, wenn die Startposition ungültig ist
			}

			for (int i = startPos; i < BSSID.length; i++) {
				wlanMAC.append(BSSID[i]);
				if (i < BSSID.length - 1) {
					wlanMAC.append(":"); // Fügen Sie ein Leerzeichen hinzu, außer für das letzte Element
				}
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wlanMAC.toString().trim();
	}

	/**
	 * wlan vorhanden
	 * 
	 * @return
	 */
	public static boolean hasWifi() {
	    try {
            Process process = Runtime.getRuntime().exec("netsh wlan show interfaces");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // Überprüfen, ob die Zeile die Information über eine WLAN-Schnittstelle enthält
                if (line.contains("Name") && line.toLowerCase().contains("wlan")) {
                    return true; // WLAN-Schnittstelle gefunden
                }
            }
            reader.close();
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace(); // Fehlerbehandlung
        }
        return false; // Keine WLAN-Schnittstelle gefunden
    }
	
	/**
	 * 
	 * @return
	 * @throws Throwable 
	 */
    public ArrayList<String> setPcInfoSystem() throws Throwable {
    	PcInfolist.add(getOSversion());
    	PcInfolist.add(timetoBuild());
    	PcInfolist.add(getUserName());
		PcInfolist.add(getSchulNummer());
		PcInfolist.add(getHersteller());
		PcInfolist.add(getPcModell());	
		PcInfolist.add(getOSArchitecture());
		PcInfolist.add(getMusterImages());
		PcInfolist.add(getSerienNummer());
		return PcInfolist;
	}
    
    /**
     * 
     * @return
     * @throws Throwable
     */
    public ArrayList<String> setPcInfoNetzwerk() throws Throwable{
		// Netzwerk
		PcNetzwerklist.add(getLocalAdresse());
		PcNetzwerklist.add(getSubnetMask());
		PcNetzwerklist.add(getMacAddress());
		PcNetzwerklist.add(getMachindomain());
		PcNetzwerklist.add(getDefaultgateway());
		PcNetzwerklist.add(getDHCPServer());
		PcNetzwerklist.add(getDNSServer());
		PcNetzwerklist.add(getConnectedWifiInfo());
		PcNetzwerklist.add(getWifiMacAdresse());
    	return PcNetzwerklist;
    }
    

	public boolean getState() throws Throwable {

		// PC
		if (!list.isEmpty())
			list.removeAll(list);

		list.add(getLocalHost());
		list.add(getUserName());
		list.add(getSchulNummer());
		list.add(getOSversion());
		list.add(getOSArchitecture());
		//list.add(getRechnertypen());
		// Netzwerk
		list.add(getLocalAdresse());
		list.add(getSubnetMask());
		list.add(getMacAddress());
		list.add(getMachindomain());
		list.add(getDefaultgateway());
		list.add(getDHCPServer());
		list.add(getDNSServer());

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equalsIgnoreCase("Fehler-Netzwerk") || (list.get(i).equalsIgnoreCase("Fehler-MusterImage")
					|| (list.get(i).equalsIgnoreCase("Fehler-Schulnummer")))) {
				toolTipFehlerHinweisText = list.get(i).replaceAll("Fehler-", "");
				System.out.println(toolTipFehlerHinweisText);
				return false;
			}
		}
		return true;
	}
	
	public  ArrayList<String> getPCinfoList(){
		return this.PcInfolist;
	}
	public  ArrayList<String> getPCinfoNetzwerkList(){
		return this.PcNetzwerklist;
	}
}