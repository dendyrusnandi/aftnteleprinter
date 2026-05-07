package displays.pid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import readwrite.ReadFromFile;

import actions.TriggerAction;

public class timeoutext implements Runnable{
	static boolean g_stclose=false;
	static boolean g_start=false;
	static int iCnt;
	int iMax=0;
	String max;
	String sPlus="";
	public void run(){
		g_stclose=false;
		iCnt=0;
		while (true) {
			iMax=testmsg.count;
			if (g_stclose) break;
			
			//System.out.println("timeout ext:"+iCnt+" iMax:"+iMax);
			if (((iMax>iCnt) || (iMax==0)) && (g_start)){
				//if (testmsg.lcounter.getShell().isDisposed()) break;
				//System.out.println("send thread:"+iCnt+" msg:"+testmsg.smsg);
				System.out.println("send thread:"+iCnt);
				sPlus=testmsg.smsg;
	    		if (!shdisp.getDisplay().isDisposed()) {
	    			shdisp.getDisplay().syncExec(new Runnable() {
	    				public void run() {
	    					if (!testmsg.tout.isDisposed()) { 
	    					testmsg.tout.setText(sPlus);
	    					//System.out.println("send thread:"+iCnt+" msg:"+testmsg.smsg);

	    					testmsg.lcounter.setText(Integer.toString(iCnt+1));
//	    					senddata("100",testmsg.smsg,testmsg.smsg.length());
	    					TriggerAction.trigger(testmsg.smsg, 100);
	    					}
	    					else g_start=false;
//	    					try {
//	    						  Thread.sleep(200);
//	    					} catch (InterruptedException e) {
//	    					  e.printStackTrace();
//	    					}
	    				}
	    			});
	    		}
			}
			else {g_start=false;iCnt=0;sPlus="";}
	       	if (g_start) iCnt++;
			try {
				/*
				//rumus= 1.0:baudrate*(10*nano): diasumsikan baudrate 19200 untuk isian di bawah
				int iBaudrate = Integer.parseInt(ReadFromFile.ReadInputFile1("/tp/baudrate.txt"));
				long nanos = 1000000000;
				float fSleep=(1.0f/iBaudrate*(10*nanos));
//				System.out.println("sleep=" + fSleep + "#"); //isinya :: sleep=520833.34#
				// returns the float value represented by this object converted to type long 
				Float obj = new Float(fSleep);//"9873267.90");
				long lSleep = obj.longValue();
//				System.out.println("longsleep=" + lSleep + "#"); //isinya :: longsleep=520833#
				int iSleep = (int) lSleep;
//				System.out.println("isleep=" + lSleep + "#"); //isinya :: isleep=520833#
				Thread.sleep(0, iSleep);*/
				Thread.sleep(200); //used to use this
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