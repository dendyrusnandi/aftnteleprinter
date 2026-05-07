package displays.pid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.SelectionAdapter;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.graphics.Color;
//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.widgets.*;

public class DisplayMain {
	static String g_sURL="";
	static int g_maxrowinfo=0;
//	static String contentStr = "";
//	static int text_w=0,text_h=0;
//	static int pid20_y=0;
//	static int pid20_space_row=0;
//	static int pid20_w=0;
//	static int pid20_space_column=0;
//	static int pid20_group_w=0;
//	static int inbox_h=0;
//	static int shell_w=0,shell_h=0;
	
	
	public DisplayMain(String[] args) {

//	public static void main(String[] args) {//add mega
//		String contentStr = readInputFile("/aftn/sizepid.txt");
//		String sFile[] = contentStr.split("\n");
//		for (int i=0;i<sFile.length;i++) {
//			System.out.println(sFile[i]);
//			if (sFile[i].startsWith("text_w")) {
//				String vFile[] = sFile[i].split("=");
//				if (vFile.length>1) text_w=Integer.parseInt(vFile[1]);
//			}
//			else if (sFile[i].startsWith("text_h")) {
//				String vFile[] = sFile[i].split("=");
//				if (vFile.length>1)	text_h=Integer.parseInt(vFile[1]);
//			}
//			else if (sFile[i].startsWith("pid20_y")) {
//				String vFile[] = sFile[i].split("=");
//				if (vFile.length>1) pid20_y=Integer.parseInt(vFile[1]);
//			}
//			else if (sFile[i].startsWith("pid20_space_row")) {
//				String vFile[] = sFile[i].split("=");
//				if (vFile.length>1) pid20_space_row=Integer.parseInt(vFile[1]);
//			}
//			else if (sFile[i].startsWith("pid20_w")) {
//				String vFile[] = sFile[i].split("=");
//				if (vFile.length>1) pid20_w=Integer.parseInt(vFile[1]);
//			}
//			else if (sFile[i].startsWith("pid20_space_column")) {
//				String vFile[] = sFile[i].split("=");
//				if (vFile.length>1) pid20_space_column=Integer.parseInt(vFile[1]);
//			}
//			else if (sFile[i].startsWith("pid20_group_w")) {
//				String vFile[] = sFile[i].split("=");
//				if (vFile.length>1) pid20_group_w=Integer.parseInt(vFile[1]);
//			}
//			else if (sFile[i].startsWith("inbox_h")) {
//				String vFile[] = sFile[i].split("=");
//				if (vFile.length>1) inbox_h=Integer.parseInt(vFile[1]);
//			}
//			else if (sFile[i].startsWith("shell_h")) {
//				String vFile[] = sFile[i].split("=");
//				if (vFile.length>1) shell_h=Integer.parseInt(vFile[1]);
//			}
//			else if (sFile[i].startsWith("shell_w")) {
//				String vFile[] = sFile[i].split("=");
//				if (vFile.length>1)	shell_w=Integer.parseInt(vFile[1]);
//			}
//		}
		System.out.println("2 argument:URL/url.txt and uconfig.txt");
		System.out.println("args[0]"+args[0]);
		System.out.println("args[1]"+args[1]);
		
		System.out.println("date:"+new DateUtils().now());
//		Shell shpid=shdisp.getShell(); //add mega
		g_sURL=args[0];
		getdt(args[1]);
		jdbc.getfl();
		
		/*Runnable theJob=new Dtgram();//PERCOBAAN 21/08/2015
		Thread t=new Thread(theJob)	;
		t.setName("Datagram Thread");
		t.start();
		
		Runnable rmsg=new timeoutext();
		Thread tmsg=new Thread(rmsg);
		tmsg.setName("Test message Thread");
		tmsg.start();*/

//		Runnable rline=new timeoutline();//acep
//		Thread tline=new Thread(rline);
//		tline.setName("Line serial check Thread");
//		tline.start();
		
//	    Menu menuBar = new Menu(shpid, SWT.BAR);//add mega
//	    MenuItem mi_main=new MenuItem(menuBar, SWT.CASCADE);
//	    mi_main.setText("Page Information Display - ELSA");
//	    shpid.setMenuBar(menuBar);
//		pid pidExe = new pid();
//		pidExe.crtIOSR(shpid);
//		pidExe.crtgrp(shpid);
//		pidExe.crtPID(shpid);
	}

	//---updated file feb 2015
//	public static void senddata(String sPort,String sData,int length) {//megol
//		try {
//			String sIPadrp="127.0.0.1";
//			DatagramSocket s = new DatagramSocket();
//			byte[] line = new byte[10000];
//			System.out.println("To   : "+sIPadrp+" Data:"+sData);
//			InetAddress dest = InetAddress.getByName(sIPadrp);
//			line = sData.getBytes();
//			int len = line.length;
//			DatagramPacket pkt = new DatagramPacket(line, len, dest, Integer.parseInt(sPort));
//			System.out.println("Send : "+len+" bytes ");
//			s.send(pkt);
//			s.close();
//		} catch (Exception err) {
//			System.out.println("err:" + err);
//		}
//	}

	private static void getdt(String fname) {
		try {
			File myFile = new File(fname);
            FileReader fileReader = new FileReader(myFile);
            BufferedReader buffReader = new BufferedReader(fileReader);
            String buf = null;
            String[] subbuf1 = new String[100];

            while ((buf = buffReader.readLine()) != null) {
                if ((buf.startsWith("maxrowinfo")) && (!buf.startsWith("#"))) {
                    subbuf1 = buf.split("=");
                    System.out.println("maxrowinfo:" + subbuf1[1]);
                    g_maxrowinfo = Integer.parseInt(subbuf1[1]);
                }            }
            buffReader.close();
		} catch (Exception err) {
            System.out.println("err" + err);
        }
    }

	public static String readInputFile(String path) {
    	String contentStr="";
    	String lineStr = "";
    	try {
        	BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        	lineStr = bf.readLine();
            while (lineStr != null) {
                contentStr += lineStr + "\n";
                lineStr = bf.readLine();
            }
            bf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentStr;
	}
	//---updated file 
}


