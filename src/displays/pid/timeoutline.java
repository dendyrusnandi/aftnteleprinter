package displays.pid;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import actions.TriggerAction;

public class timeoutline implements Runnable {
	static boolean g_stclose=false;
	String sPlus="";
	public void run(){
		g_stclose=false;
		while (true) {
			if (g_stclose) break;
			try {
//				senddata("101","Line",4);
				TriggerAction.trigger("Line", 101);
				System.out.println("checkline");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			  e.printStackTrace();
			}
		}//end while
	}
	
	public static void setstclose(boolean stclose1){
		g_stclose=stclose1;
	}
	
//    public void senddata(String sPort,String sData,int length) {
//    	  try {
//    		  String sIPadrp="127.0.0.1";
//    		  DatagramSocket s = new DatagramSocket();
//              byte[] line = new byte[10000];
//			  System.out.println("To   : "+sIPadrp+" Data:"+sData);
//              InetAddress dest = InetAddress.getByName(sIPadrp);
//              line = sData.getBytes();
//              int len = line.length;
//              DatagramPacket pkt = new DatagramPacket(line, len, dest, Integer.parseInt(sPort));
//			  System.out.println("Send : "+len+" bytes ");
//              s.send(pkt);
//              s.close();
//          } catch (Exception err) {
//              System.out.println("err:" + err);
//          }
//	}

}