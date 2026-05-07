package threads;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.eclipse.swt.SWT;

import readwrite.ReadFromFile;
import setting.Colors;
import setting.Shorten;
import displays.MainForm;
import displays.TeleSplashScreen2016IP;

public class TimerIPLocal {

	static Shorten sh = new Shorten();
	static ReadFromFile rff = new ReadFromFile();
	
	static String str="";
	public static Thread threadtime;
	static String sdt="";
	
	public TimerIPLocal() {
		// TODO Auto-generated constructor stub
	}
	
	public static void time() {
		threadtime = new Thread() {
            public void run() {
                while (true) {
                    TeleSplashScreen2016IP.display.syncExec(new Runnable() {
                        @Override
                        public void run() {
//                        	System.err.println(MainForm.strLocalIP());
                        	sh.clabelStyle(MainForm.clLocalIP, MainForm.strLocalIP(), MainForm.ixIP, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
            				sh.clabelStyle(MainForm.clDestIP, MainForm.strDestIP(), MainForm.ixIP, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
                        	if (!sdt.isEmpty()) {
                        		if (sdt.compareTo(MainForm.clLocalIP.getText())!=0) {
                        			System.out.println("down");
                        			sndudp("down");
                    				sdt=MainForm.clLocalIP.getText();
                        		}
                        	}
                        	else sdt=MainForm.clLocalIP.getText();
                        }
                    });

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        threadtime.setDaemon(true);
        threadtime.start();
        
	}
	
    static void sndudp(String sData) {
  	  try {
		  DatagramSocket s = new DatagramSocket();
          byte[] line = new byte[1000];
          InetAddress dest = InetAddress.getByName("127.0.0.1");
          //System.out.println("getbytes");
          line = sData.getBytes();
          int len = line.length;
          DatagramPacket pkt = new DatagramPacket(line, len, dest, Integer.parseInt("100"));
		  System.out.println("Send : "+len+" bytes "+"Data:"+sData);
		  System.out.println("To   : "+"local"+" Port:"+100);
          s.send(pkt);
          s.close();
      } catch (Exception err) {
          System.out.println("err dgram" + err);
      }
    }

}
