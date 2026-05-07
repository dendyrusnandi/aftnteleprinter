package displays.pid;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import setting.ButtonsSetting;
import setting.Colors;
import setting.Images;
import setting.Shells;
import setting.Shorten;
import displays.MainForm;


public class rs232ipsetup {
	Shorten sh = new Shorten();
	ButtonsSetting bs = new ButtonsSetting();
	
	Shell shell;
	Combo comb_1;
	String sResrcv="";
	//String scomm[]={"Serial RS232","IP/TCP","IP/UDP","IP/RAW"};
	String scomm[]={"RS232","UDP"};
	String s1="",s2="",s3="",s4="";


	public rs232ipsetup(Shell shell) {
		this.shell=shell;
		contents();
	}
	
	public void open() {
		sh.shellStyle(shell, "Setup");
		shell.setLocation(MainForm.setLocX(shell), MainForm.setLocY(shell));
	}
	
	public void contents() {
		Group g_1 = new Group(shell, SWT.BORDER); sh.groupStyle(g_1, 1, "Comm. Setup"); g_1.setToolTipText("Communication setup");
		comb_1 = new Combo(g_1, SWT.PUSH); 
		Button b_1 = new Button(g_1,SWT.PUSH); sh.buttonStyle(b_1, "Setup", "Setup", bs.widthBtn, Colors.DarkGrey, SWT.CENTER, SWT.CENTER, null);
		Button b_2 = new Button(g_1,SWT.PUSH); sh.buttonStyle(b_2, "Activate", "Activate", bs.widthBtn, Colors.DarkGrey, SWT.CENTER, SWT.CENTER, null);
		
		Group g_2 = new Group(shell, SWT.BORDER); sh.groupStyle(g_2, 1, "AFTN Configuration");
		Button b_3 = new Button(g_2,SWT.PUSH); sh.buttonStyle(b_3, "Setup", "Setup", bs.widthBtn, Colors.DarkGrey, SWT.CENTER, SWT.CENTER, null);
		Button b_4 = new Button(shell,SWT.PUSH); sh.buttonStyle(b_4, "Close", "Close", bs.widthBtn, Colors.DarkGrey, SWT.CENTER, SWT.CENTER, null);
		
		
		for (int i=0;i<scomm.length;i++) { comb_1.add(scomm[i]); }
		File file=new File("/tp/nis.txt");
		try{
			String s2 = readFileAsAString(file);
			if (s2.startsWith("0")) comb_1.select(0);
			else if (s2.startsWith("2")) comb_1.select(1);
			//else if (s2.startsWith("1")) comb_1.select(1);
			//else if (s2.startsWith("3")) comb_1.select(3);
			s1=comb_1.getText();
		} catch (IOException e) { System.err.println("nis.txt"); };
		
		b_1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent ed) {
				if (!comb_1.getText().isEmpty()) {
					for (int i=0;i<scomm.length;i++) {
						if (comb_1.getText().compareTo(scomm[0])==0) {
							System.out.println("Serial Setup");
							if (!Shells.sh_setupIP.isDisposed()) Shells.sh_setupIP.close();
							if (Shells.sh_setupIP.isDisposed()) Shells.sh_setupIP = new Shell(SWT.MIN);
							Shells.sh_setupIP.setText("Serial setup");
							new RS232(Shells.sh_setupIP).open();
							break;
						}
						else if (comb_1.getText().compareTo(scomm[1])==0) {
							System.out.println("IP/UDP Setup");
							if (!Shells.sh_setupIP.isDisposed()) Shells.sh_setupIP.close();
							if (Shells.sh_setupIP.isDisposed()) Shells.sh_setupIP = new Shell(SWT.MIN);
							Shells.sh_setupIP.setText("UDP setup");
							new IP(Shells.sh_setupIP,2).open();
							break;
						}
						else if (comb_1.getText().compareTo(scomm[2])==0) {
							System.out.println("IP/TCP Setup");
							if (!Shells.sh_setupIP.isDisposed()) Shells.sh_setupIP.close();
							if (Shells.sh_setupIP.isDisposed()) Shells.sh_setupIP = new Shell(SWT.MIN);
							Shells.sh_setupIP.setText("TCP setup");
							new IP(Shells.sh_setupIP,1).open();
							break;
						}
						else if (comb_1.getText().compareTo(scomm[3])==0) {
							System.out.println("IP/RAW Setup");
							if (!Shells.sh_setupIP.isDisposed()) Shells.sh_setupIP.close();
							if (Shells.sh_setupIP.isDisposed()) Shells.sh_setupIP = new Shell(SWT.MIN);
							Shells.sh_setupIP.setText("RAW setup");
							new IP(Shells.sh_setupIP,3).open();
							break;
						}
					}
				}
			}
		});
		
		b_2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent ed) {
				if (!comb_1.getText().isEmpty()) {
					for (int i=0;i<scomm.length;i++) {
						boolean flg=false;
						if (comb_1.getText().compareTo(scomm[0])==0) {
							System.out.println("Serial Activate");
							WriteToAFile wr = new WriteToAFile();
							wr.open("/tp/nis.txt");
							wr.write("0");
							wr.close();
							flg=true;
						}
						else if (comb_1.getText().compareTo(scomm[1])==0) {
							System.out.println("IP/UDP Activate");
							WriteToAFile wr = new WriteToAFile();
							wr.open("/tp/nis.txt");
							wr.write("2");
							wr.close();
							flg=true;
						}
						else if (comb_1.getText().compareTo(scomm[2])==0) {
							System.out.println("IP/TCP Activate");
							WriteToAFile wr = new WriteToAFile();
							wr.open("/tp/nis.txt");
							wr.write("1");
							wr.close();
							flg=true;
						}
						else if (comb_1.getText().compareTo(scomm[3])==0) {
							System.out.println("IP/RAW Activate");
							WriteToAFile wr = new WriteToAFile();
							wr.open("/tp/nis.txt");
							wr.write("3");
							wr.close();
							flg=true;
						}
						System.out.println("s1 "+s1+":"+comb_1.getText());
					    if (s1.compareTo(comb_1.getText())!=0) {
					    	sndudp("down");
					    	s1=comb_1.getText();
					    }
					    if (flg) break;
					}
				}
				
				String snis = DisplayMain.readInputFile("/tp/nis.txt");
				if (!snis.isEmpty()) {
					String sarr[] = snis.split("\n");
					snis="";
					for (int i=0;i<1;i++) {
						snis=sarr[i];
					}
				}
				String sbaud = DisplayMain.readInputFile("/tp/baudrate.txt");
				if (!sbaud.isEmpty()) {
					String sarr[] = sbaud.split("\n");
					sbaud="";
					for (int i=0;i<1;i++) {
						sbaud=sarr[i];
					}
				}
				String strnis="";
				if (snis.compareTo("0")==0) { strnis="RS232"; MainForm.clBaudrate.setText("Baudrate : "+sbaud);MainForm.bRefresh.setEnabled(false);}
				if (snis.compareTo("2")==0) { strnis="UDP"; MainForm.clBaudrate.setText("Baudrate : -");MainForm.bRefresh.setEnabled(true);}
				if (snis.compareTo("1")==0) { strnis="TCP";MainForm.clBaudrate.setText("Baudrate : -"); }
				if (snis.compareTo("3")==0) { strnis="RAW";MainForm.clBaudrate.setText("Baudrate : -"); }
				sh.clabelStyle(MainForm.clNis, strnis, MainForm.ixNis, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
				
				sh.clabelStyle(MainForm.clLocalIP, MainForm.strLocalIP(), MainForm.ixIP, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
				sh.clabelStyle(MainForm.clDestIP, MainForm.strDestIP(), MainForm.ixIP, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
				
				/*if (snis.compareTo("1")!=0) { 
					if (MainForm.clRefuse!=null) {
	            		if (!MainForm.clRefuse.isDisposed()) {
	            			sh.clabelStyle(MainForm.clRefuse, "", MainForm.ixRefuse, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.red, null);
	            		}
					}
				}*/
			}
		});

		b_3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent ed) {
				if (!Shells.sh_setupAFTN.isDisposed()) Shells.sh_setupAFTN.close();
				if (Shells.sh_setupAFTN.isDisposed()) Shells.sh_setupAFTN = new Shell(SWT.MIN);
				new setting(Shells.sh_setupAFTN).open();
			}
		});
		
		b_4.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent ed) {
				shell.close();
			}
		});
	}
	
    void sndudp(String sData)
    {
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
    
	public String readFileAsAString(File file) throws IOException {
		return new String(getBytesFromFile(file));
	}
	
	public byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		long length=file.length();
		if (length>Integer.MAX_VALUE)
			throw new IllegalArgumentException("File is too large");
		byte[] bytes = new byte[(int)length];
		int offset=0;
		int numread=0;
		while(offset<bytes.length && (numread=is.read(bytes,offset,bytes.length-offset))>=0){
			offset+=numread;
		}
		if (offset<bytes.length) {
			throw new IOException("Could not completely read file"+file.getName());
		}
		is.close();
		return bytes;
		
	}
}
