package c4h;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import c4h.PcInformation.ParsePcModelInMap;
import c4h.PcInformation.pcInformation;

public class TestC4H {
	
	final String test_PD06= "HP ProDesk 400 G5 Desktop Mini";
	final String test_LE04= "Lenovo Thinkbook 4 ABA (15\") (LE04)";
	final String testKeyHP09= "HP09";
	
	public static pcInformation pcinfo= new pcInformation();
	public static ParsePcModelInMap parse = new ParsePcModelInMap();
	
	
	

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		
		
			TestC4H testParse= new TestC4H();
			testParse.TestParsePcModell();
			System.out.println("");
			testParse.printMAP();
			
			
			
			
			
			TestC4H testPcInfo= new TestC4H();
			testPcInfo.printBGinfo();
//			testPcInfo.setPcInfoSystem();
//			testPcInfo.setPcInfoNetzwerk();
//			
			//System.out.println("PCINFOSYSTEM Element 8; "+ test.PcInfolist.get(8));
		//	System.out.println("PCINFOSYSTEM Element 0; "+ test.PcNetzwerklist.get(0));
			
			


	        
	 
	    }
	public void TestParsePcModell() throws Throwable{
		
		//Teste mit einen PC info 
		//String pcModell=pcinfo.getPcModell();
		String pcModell= test_LE04;
		//String pcModell= test_PD06;
		//String pcModell= testKeyHP09;
		
	try {
			System.out.println("PC Modell: " +parse.findePcModell(pcModell));
			System.out.println("Support Ende: "+parse.findSupportEndethValue(parse.findePcModell(pcModell)));
			System.out.println("Kaufdatum: "+parse.findKaufDatum(parse.findePcModell(pcModell)));
			System.out.println("windows 11: "+parse.findWindows11Support(parse.findePcModell(pcModell)));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 public void printMAP() {
		 ParsePcModelInMap TestParse = new ParsePcModelInMap();
		 
		 Map<String, ArrayList<String>> mapList;
			
			mapList= TestParse.getMapList();
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
	 
		/************************************************************************************************************/
		/**************************************
		 * PRINTING * @throws Throwable
		 ****************************************/
		/**
		 * Print Information in der Console
		 * 
		 * @throws Throwable Hostname Localhost
		 */
		public static void printBGinfo() throws Throwable {
			
			

			System.out.println("3S");
			System.out.println("BG_Info");
			System.out.println("time to build :" + pcinfo.timetoBuild());
			System.out.println("*********************************");
			System.out.println("Schul-Support-Services HiTEC e.V.");

			System.out.println("*********************************");
			System.out.println("            PC Info              ");
			System.out.println("*********************************");
			System.out.println("Host Name     :" + pcinfo.getLocalHost());
			System.out.println("User Name     :" + pcinfo.getUserName());
			System.out.println("SchulNummer   :" + pcinfo.getSchulNummer());
			System.out.println("OS Version    :" + pcinfo.getOSversion());
			System.out.println("OS Architektur:" + pcinfo.getOSArchitecture());
			System.out.println("Muster Images :" + pcinfo.getMusterImages());
			System.out.println("Pc Modell     :" + pcinfo.getPcModell());
			System.out.println("Hersteller    :" + pcinfo.getHersteller());
			System.out.println("Seriennummer  :" + pcinfo.getSerienNummer());

			System.out.println("*********************************");
			System.out.println("            NETZWERK             ");
			System.out.println("*********************************");

			System.out.println("Adresse local  :" + pcinfo.getLocalAdresse());
			System.out.println("Subnet Mask    :" + pcinfo.getSubnetMask());
			System.out.println("MAC Adresse    :" + pcinfo.getMacAddress());
			System.out.println("Machine Domain :" + pcinfo.getMachindomain());
			System.out.println("Default Gateway:" + pcinfo.getDefaultgateway());
			System.out.println("DHCP Server    :" + pcinfo.getDHCPServer());
			System.out.println("DNS Server     :" + pcinfo.getDNSServer());
			System.out.println("Wlan SSID      :" + pcinfo.getConnectedWifiInfo());
			System.out.println("Wlan MAC       :" + pcinfo.getWifiMacAdresse());
			
			

			try {
				System.out.println("Pruenfung der Schulnummer: " + pcinfo.getSchulNummer() + " is " + pcinfo.pruefeSchulnr());
			} catch (Throwable e) {
				e.printStackTrace();
			}

		}
	 

	

}
