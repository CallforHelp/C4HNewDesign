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
 * @version 1.0
 * 
 */
public class pcInformation {

	public String toolTipFehlerHinweisText;
	
	ArrayList<String> list = new ArrayList<>();
	
	ArrayList<String> PcInfolist = new ArrayList<>();
	ArrayList<String> PcNetzwerklist = new ArrayList<>();
	
	String schulNummer = "";

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
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public pcInformation(String s) {

		try {
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
		String systemHersteller = "";
		
		Process Herstteller = Runtime.getRuntime().exec("powershell.exe Get-WmiObject -Class Win32_BIOS");
		InputStreamReader input = new InputStreamReader(Herstteller.getInputStream());
		BufferedReader resultOutput = new BufferedReader(input);
		while ((line = resultOutput.readLine()) != null) {
			if (line.contains("Manufacturer"))
				systemHersteller = line.split(":")[1].trim();
		}
		Herstteller.destroy();	
		
		return systemHersteller;
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
					//return "HP ProDesk 400 G5 Desktop Mini";
					
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
		
		String line;
		String serienNummer = "";
		Process SerienNummer = Runtime.getRuntime().exec("powershell.exe Get-WmiObject -Class Win32_BIOS");
		InputStreamReader input = new InputStreamReader(SerienNummer.getInputStream());
		BufferedReader resultOutput = new BufferedReader(input);

		while ((line = resultOutput.readLine()) != null) {
			if (line.contains("SerialNumber"))
				serienNummer = line.split(":")[1].trim();
		}
		
		
		return serienNummer;
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
		
		String LanMacAdresse = null;
		try {
			Process process = Runtime.getRuntime().exec("powershell.exe Get-NetAdapter | Where-Object { $_.Name -like 'Ethernet*' } | Select-Object -ExpandProperty MacAddress");
	        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        LanMacAdresse = reader.readLine();
	        if (LanMacAdresse == null && LanMacAdresse.isEmpty()) {
	        	LanMacAdresse= "Fehler-MacAdresse ";
	        }
	        
	    }catch (IOException e) {
	    	e.printStackTrace(); // Behandlung der Ausnahme entsprechend
	    }
		
	    
	    return LanMacAdresse;
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
		String domain=null;
		try {
			Process process = Runtime.getRuntime().exec("powershell.exe Get-WmiObject Win32_ComputerSystem | Select-Object -ExpandProperty Domain");
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        domain = reader.readLine();
	        reader.close();
	        process.destroy();
	        if (domain == null && domain.isEmpty()) {
	        	domain= "Fehler-Domain";
	        }
	        
		}catch (IOException e) {
			e.printStackTrace(); // Behandlung der Ausnahme entsprechend
	    }
		
	return domain.trim();
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
		String subnetMask=null;
		try {
            Process process = Runtime.getRuntime().exec("powershell.exe Get-NetIPAddress | Where-Object { $_.AddressFamily -eq 'IPv4' } | Select-Object -ExpandProperty PrefixLength");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            subnetMask = reader.readLine();
            reader.close();
            process.destroy();
            if (subnetMask == null && subnetMask.isEmpty()) {
                subnetMask="Fehler- Netzwerk";
            }
            else 
            	subnetMask=convertPrefixLengthToMask(Integer.parseInt(subnetMask.trim()));
        } catch (IOException e) {
            e.printStackTrace(); // Behandlung der Ausnahme entsprechend
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

		String gateway = "";
		
        try {
            Process process = Runtime.getRuntime().exec("powershell.exe Get-NetRoute -DestinationPrefix 0.0.0.0/0 | Select-Object -ExpandProperty NextHop");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            gateway = reader.readLine();
            reader.close();
            process.destroy();
            if (gateway == null && gateway.isEmpty()) {
                gateway="Fehler-Gateway" ;
            }
        } catch (IOException e) {
            e.printStackTrace(); // Behandlung der Ausnahme entsprechend
        }
        
       
		return gateway.trim();
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
		return wlanMAC.toString();
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

	/************************************************************************************************************/
	/**************************************
	 * PRINTING * @throws Throwable
	 ****************************************/
	/**
	 * Print Information in der Console
	 * 
	 * @throws Throwable Hostname Localhost
	 */
	public void printBGinfo() throws Throwable {

		System.out.println("3S");
		System.out.println("BG_Info");
		System.out.println("time to build :" + timetoBuild());
		System.out.println("*********************************");
		System.out.println("Schul-Support-Services HiTEC e.V.");

		System.out.println("*********************************");
		System.out.println("            PC Info              ");
		System.out.println("*********************************");
		System.out.println("Host Name     :" + getLocalHost());
		System.out.println("User Name     :" + getUserName());
		System.out.println("SchulNummer   :" + getSchulNummer());
		System.out.println("OS Version    :" + getOSversion());
		System.out.println("OS Architektur:" + getOSArchitecture());
		System.out.println("Muster Images :" + getMusterImages());
		System.out.println("Pc Modell     :" + getPcModell());

		System.out.println("*********************************");
		System.out.println("            NETZWERK             ");
		System.out.println("*********************************");

		System.out.println("Adresse local  :" + getLocalAdresse());
		System.out.println("Subnet Mask    :" + getSubnetMask());
		System.out.println("MAC Adresse    :" + getMacAddress());
		System.out.println("Machine Domain :" + getMachindomain());
		System.out.println("Default Gateway:" + getDefaultgateway());
		System.out.println("DHCP Server    :" + getDHCPServer());
		System.out.println("DNS Server     :" + getDNSServer());
		System.out.println("Wlan SSID      :" + getConnectedWifiInfo());
		System.out.println("Wlan MAC      :" + getWifiMacAdresse());
		
		

		try {
			System.out.println("Pruenfung der Schulnummer: " + getSchulNummer() + " is " + pruefeSchulnr());
		} catch (Throwable e) {
			e.printStackTrace();
		}

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

	public static void main(String[] args) throws Throwable {

		pcInformation test = new pcInformation();
//		System.out.println(test.getWifiMacAdresse());
//		//test.printBGinfo();
//		test.setPcInfoSystem();
//		test.setPcInfoNetzwerk();
//		
		System.out.println("PCINFOSYSTEM Element 8; "+ test.PcInfolist.get(8));
		System.out.println("PCINFOSYSTEM Element 0; "+ test.PcNetzwerklist.get(0));
	}
}