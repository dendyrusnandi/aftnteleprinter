package actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import readwrite.ReadFromFile;


public class TriggerAction {
	static ReadFromFile rff = new ReadFromFile();
	
	private static String[] tmp3a = new String[1000];
    public static int port, ip;
    
    static String contentStr="",ipDisp="",bawah="";
	static String lineStr = null;
	
	private static void readFile() {
    	try {
		      File myFile = new File("/tp/configjv.txt");
		      FileReader fileReader = new FileReader(myFile);

		      BufferedReader buffReader = new BufferedReader(fileReader);

		      String line = null;

		      while ((line = buffReader.readLine()) != null) {
		        parsing(line);
		      }
		      buffReader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    static void parsing(String line) {
		 String tmp;
		 String[] tmp1 = new String[100];
		 String[] tmp2 = new String[100];
		 String[] data = new String[100];
		 String[] sub = new String[100];
		 
		 //sub = contentStr.split("\n"); //pake ReadInputFile
		 sub = line.split("\n");
		 for (int x = 0; x < sub.length; x++) {
			 data = sub[x].split("=");
	     }

		 for (int x = 0; x < sub.length; x++) {
			 tmp = sub[x];
			 if (tmp.indexOf("=") != -1) {
				 data = tmp.split("=");
				 tmp1[x] = data[0];
				 tmp2[x] = data[1];
				 
				 if (tmp1[x].compareTo("portpidcreate") == 0) {
					 tmp3a[0] = tmp2[x]; 
				 } else if (tmp1[x].compareTo("portpiddisplay") == 0) {
					 tmp3a[1] = tmp2[x]; 
				 } else if (tmp1[x].compareTo("portinboxats") == 0) {
					 tmp3a[2] = tmp2[x]; 
				 } else if (tmp1[x].compareTo("portinboxnotam") == 0) {
					 tmp3a[3] = tmp2[x]; 
				 } else if (tmp1[x].compareTo("portinboxmeteo") == 0) {
					 tmp3a[4] = tmp2[x]; 
				 } else if (tmp1[x].compareTo("portrejectats") == 0) {
					 tmp3a[5] = tmp2[x]; 
				 } else if (tmp1[x].compareTo("portrejectnotam") == 0) {
					 tmp3a[6] = tmp2[x]; 
				 } else if (tmp1[x].compareTo("portrejectmeteo") == 0) {
					 tmp3a[7] = tmp2[x]; 
				 } else if (tmp1[x].compareTo("portchkstatus") == 0) {
					 tmp3a[8] = tmp2[x]; 
				 } else if (tmp1[x].compareTo("ip0") == 0) {
					 tmp3a[9] = tmp2[x]; 
				 }
			 }
		 }
	 }
	 
	public static int getPort() { 
		port = Integer.parseInt(String.valueOf(tmp3a[8]));
		return port;
	}
	
	public static String getIp() { 
		return tmp3a[9];
	}
    
    public static void trigger(String sData, int port) {
    	readFile();
		System.out.println("**trigger**");
		try {
			DatagramSocket s = new DatagramSocket();
			byte[] line = new byte[10000];
			System.out.println("To   : "+getIp()+" Data:"+sData);
			InetAddress dest = InetAddress.getByName(getIp());//getIp():127.0.0.1
			String data=sData;
			line = data.getBytes();
			int len = line.length;
			DatagramPacket pkt = new DatagramPacket(line, len, dest, port);
			System.out.println("Send : "+len+" bytes ");
			s.send(pkt);
			s.close();
		} catch (Exception err) {
			System.out.println("err" + err);
		}
    }
    
}
