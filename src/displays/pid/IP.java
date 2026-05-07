package displays.pid;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import setting.ButtonsSetting;
import setting.Colors;
import setting.Shorten;


import displays.MainForm;


public class IP {
	Shorten sh = new Shorten();
	ButtonsSetting bs = new ButtonsSetting();
	
	Shell shell;
	int typ;
	String s1="",s2="",s3="";
	Text t_1,t_2;//,t_3;
	String string="";

	public IP(Shell shell,int typ) {
		this.shell=shell;
		this.typ=typ;
		contents();
	}
	
	public void open() {
		String title="";
		if (typ==1) { title= "TCP Setup"; }
		if (typ==2) { title= "UDP Setup"; }
		if (typ==3) { title= "RAW Setup"; }
		
		sh.shellStyle(shell, title);
		shell.setLocation(MainForm.setLocX(shell), MainForm.setLocY(shell));
	}
	
	public void contents() {
		Group group = new Group(shell, SWT.NONE); sh.groupStyle(group, 1, "");
		Composite comp = new Composite(group, SWT.NONE); sh.compositeStyle(comp, 2, SWT.LEFT, SWT.CENTER);
		MainForm.separator(group);
		Composite compBtn = new Composite(group, SWT.NONE); sh.compositeStyle(compBtn, 2, SWT.CENTER, SWT.CENTER);
		
		Label l_1 = new Label(comp, SWT.NONE); sh.labelStyle1(l_1, "Destination IP Address", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		
		File file=new File("/tp/adrg.txt");
		File file1=new File("/tp/portg.txt");
		File file2=new File("/tp/portg1.txt");
		try {
			s1 = readFileAsAString(file);
			s2 = readFileAsAString(file1);
			s3 = readFileAsAString(file2);
		} catch(IOException e){System.err.println("nis.txt");};		
		
		t_1 = new Text(comp, SWT.BORDER); sh.textStyle(t_1, 180, 23, SWT.LEFT, SWT.CENTER, sh.numericdot, "", true);
		t_1.setText(s1);
		
		if (typ!=3) {
			Label l_2 = new Label(comp, SWT.NONE); sh.labelStyle1(l_2, "Port", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t_2 = new Text(comp, SWT.BORDER); sh.textStyle(t_2, 100, 5, SWT.LEFT, SWT.CENTER, sh.numeric, "", true);
			t_2.setText(s2);
			/*string=s2;
			t_2.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
				}
			
				public void keyReleased(KeyEvent e) {
					switch (e.character) {
						case 0:
						break;
						case SWT.BS: 
							t_3.setText(t_2.getText());
						break;
						default: 
							t_3.setText(t_2.getText());
						break;
					}
				}
			});
			
			Label l_3 = new Label(comp, SWT.NONE); sh.labelStyle1(l_3, "Destination port", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t_3 = new Text(comp, SWT.BORDER); sh.textStyle(t_3, 100, 5, SWT.LEFT, SWT.CENTER, sh.numeric, "", true);
			t_3.setText(s3);*/

		}
		
		s1="";
		file=new File("/tp/setupipport.txt");
		try {
			s1 = readFileAsAString(file);
		} catch(IOException e){System.err.println("setupipport.txt");};
		if (s1.startsWith("0")) t_1.setEnabled(false);
		if (typ!=3) {
			if (s1.startsWith("0")) t_2.setEnabled(false);
		}
		Button b_1 = new Button(compBtn,SWT.PUSH); sh.buttonStyle(b_1, "OK", "", bs.widthBtn, bs.colorBtn, SWT.LEFT, SWT.CENTER, null);
		Button b_2 = new Button(compBtn,SWT.PUSH); sh.buttonStyle(b_2, "Close", "", bs.widthBtn, bs.colorBtn, SWT.LEFT, SWT.CENTER, null);
		
		b_1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent ed) {
				if (s1.compareTo(t_1.getText())!=0) {
					WriteToAFile wr = new WriteToAFile();
					wr.open("/tp/adrg.txt");
					wr.write(t_1.getText());
					wr.close();
					s1=t_1.getText();
				}
				if (typ!=3){
					boolean flg=false;
					if (s2.compareTo(t_2.getText())!=0) {
						WriteToAFile wr = new WriteToAFile();
						wr.open("/tp/portg.txt");
						wr.write(t_2.getText());
						wr.close();
						flg=true;
						s2=t_2.getText();
						wr = new WriteToAFile();
						wr.open("/tp/portg1.txt");
						wr.write(t_2.getText());
						wr.close();
						flg=true;
					}
					/*if (s2.compareTo(t_2.getText())!=0) {
						WriteToAFile wr = new WriteToAFile();
						wr.open("/tp/portg1.txt");
						wr.write(t_3.getText());
						wr.close();
						flg=true;
						s3=t_3.getText();
					}*/
					if (flg){
					    sndudp("down");
					}
				}
				
				sh.clabelStyle(MainForm.clLocalIP, MainForm.strLocalIP(), MainForm.ixIP, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
				sh.clabelStyle(MainForm.clDestIP, MainForm.strDestIP(), MainForm.ixIP, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
			}
		});
		
		b_2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent ed) {
				shell.close();
			}
		});
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
	
    void sndudp(String sData) {
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

