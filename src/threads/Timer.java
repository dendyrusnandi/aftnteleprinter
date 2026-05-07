/**
 * create a date/time at bottom panel 
 */
package threads;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;

import readwrite.ReadFromFile;
import setting.Colors;
import setting.Images;
import setting.Shorten;
import actions.Calendar;
import displays.MainForm;
import displays.TeleSplashScreen2016IP;

public class Timer {

	static Shorten sh = new Shorten();
	static ReadFromFile rff = new ReadFromFile();
	
	static String str="";
	public static Thread threadtime;
	
	public Timer() {
	
	}
	
//	private static void getConf() {
//		ReadFromFile.readConfiguration();
//		
//		String sbaud = DisplayMain.readInputFile("/tp/baudrate.txt");
//		if (!sbaud.isEmpty()) {
//			String sarr[] = sbaud.split("\n");
//			sbaud="";
//			for (int i=0;i<1;i++) {
//				sbaud=sarr[i];
//			}
//		}
//		
//		String sdigit = DisplayMain.readInputFile("/tp/digit.txt");
//		if (!sdigit.isEmpty()) {
//			String sarr[] = sdigit.split("\n");
//			sdigit="";
//			for (int i=0;i<1;i++) {
//				sdigit=sarr[i];
//			}
//		}
//		
//		String stransid = DisplayMain.readInputFile("/tp/transid.txt");
//		if (!stransid.isEmpty()) {
//			String sarr[] = stransid.split("\n");
//			stransid="";
//			for (int i=0;i<1;i++) {
//				stransid=sarr[i];
//			}
//		}
//
//		String stsec = DisplayMain.readInputFile("/tp/tseq.txt");
//		if (!stsec.isEmpty()) {
//			String sarr[] = stsec.split("\n");
//			stsec="";
//			for (int i=0;i<1;i++) {
//				stsec=sarr[i];
//			}
//		}
//
//		String srseq = DisplayMain.readInputFile("/tp/rseq.txt");
//		if (!srseq.isEmpty()) {
//			String sarr[] = srseq.split("\n");
//			srseq="";
//			for (int i=0;i<1;i++) {
//				srseq=sarr[i];
//			}
//		}
//		
//		String ssvcmsg = DisplayMain.readInputFile("/tp/svcmsg.txt");
//		if (!ssvcmsg.isEmpty()) {
//			String sarr[] = ssvcmsg.split("\n");
//			ssvcmsg="";
//			for (int i=0;i<1;i++) {
//				ssvcmsg=sarr[i];
//			}
//		}
//		if (ssvcmsg.compareTo("1")==0) ssvcmsg="ON";
//		else if (ssvcmsg.compareTo("0")==0) ssvcmsg="OFF";
//		
//		String scode1="";
//		String scode = DisplayMain.readInputFile("/tp/databits.txt");
//		if (!scode.isEmpty()) {
//			String sarr[] = scode.split("\n");
//			scode="";
//			for (int i=0;i<1;i++) {
//				scode=sarr[i];
//			}
//		}
//		if (scode.compareTo("5")==0) scode1="ITA 2";
//		else if (scode.compareTo("8")==0) scode1="IA 5";
//		
//		String sfree = DisplayMain.readInputFile("/tp/freespace.txt");
//		if (!sfree.isEmpty()) {
//			String sarr[] = sfree.split("\n");
//			sfree="";
//			for (int i=0;i<1;i++) {
//				sfree=sarr[i];
//			}
//		}
//		
//		sh.clabelStyle(MainForm.clLine, "Line : ", MainForm.ixLine, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL, null);
//		sh.clabelStyle(MainForm.clBaudrate, "Baudrate : "+sbaud, MainForm.ixBaudrate, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL, null);
//		sh.clabelStyle(MainForm.clCode, "Code : "+scode1, MainForm.ixCode, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL, null);
//		sh.clabelStyle(MainForm.clSvcMsgGen, "Svc msg gen : "+ssvcmsg, MainForm.ixSvcMsgGen, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL, null);
//		sh.clabelStyle(MainForm.clCID, "CID : "+stransid, MainForm.ixtransid, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL, null);
//		sh.clabelStyle(MainForm.clDigit, "Digit seq : "+sdigit, MainForm.ixDigit, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL, null);
//		sh.clabelStyle(MainForm.clTseq, "Tseq : "+stsec, MainForm.ixTseq, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL, null);
//		sh.clabelStyle(MainForm.clRseq, "Rseq : "+srseq, MainForm.ixRseq, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL, null);
//		sh.clabelStyle(MainForm.clQueue, "Queue : "/*+MainForm.query(TableOutgoing.queryQueue)*/, MainForm.ixQueue, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL, null);
//		sh.clabelStyle(MainForm.clWarning, "Incm wrng msg : "/*+MainForm.query(MainForm.queryWarning)*/, MainForm.ixWarning, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL, null);
//		sh.clabelStyle(MainForm.clFree, "Free : "+sfree, MainForm.ixfree, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL, null);
//		
//		
//		// ========== set DATABASE
////		public String getDBUrl() { return arrConf[59]; }
////		public String getDBUser() { return arrConf[60]; }
////		public String getDBPass() { return arrConf[61]; }
//
//		// ========== set JUDUL di FORM UTAMA (tanpa continuous printer: atas)
//		MainForm.shell.setText(ReadFromFile.getTitle());
//
//		// ========== set TINGGI di FORM UTAMA (semakin besar, semakin tinggi)
////		sh.compositeStyle(MainForm.Height_MainForm, 1, ReadFromFile.getHeight_MainForm());//unused
//
//		// ========== set LEBAR label di FORM UTAMA (semakin besar, semakin lebar)
////		public int getXline() { if (arrConf[75]==null) arrConf[75]="0"; return Integer.parseInt(arrConf[75]); }
////		public int getXbaud() { if (arrConf[69]==null) arrConf[69]="0"; return Integer.parseInt(arrConf[69]); }
////		public int getXcode() { if (arrConf[76]==null) arrConf[76]="0"; return Integer.parseInt(arrConf[76]); }
////		public int getWidthSrvMsgGen() { if (arrConf[79]==null) arrConf[79]="0"; return Integer.parseInt(arrConf[79]); }
////		public int getXcid() { if (arrConf[71]==null) arrConf[71]="0"; return Integer.parseInt(arrConf[71]); }
////		public int getXdigit() { if (arrConf[70]==null) arrConf[70]="0"; return Integer.parseInt(arrConf[70]); }
////		public int getXtseq() { if (arrConf[72]==null) arrConf[72]="0"; return Integer.parseInt(arrConf[72]); }
////		public int getXrseq() { if (arrConf[73]==null) arrConf[73]="0"; return Integer.parseInt(arrConf[73]); }
////		public int getXqueue() { if (arrConf[74]==null) arrConf[74]="0"; return Integer.parseInt(arrConf[74]); }
////		public int getXwarning() { if (arrConf[78]==null) arrConf[78]="0"; return Integer.parseInt(arrConf[78]); }
////		public int getXfree() { if (arrConf[80]==null) arrConf[80]="0"; return Integer.parseInt(arrConf[80]); }
////		public int getXprintertype() { if (arrConf[77]==null) arrConf[77]="0"; return Integer.parseInt(arrConf[77]); }
////		public int getXtime() { if (arrConf[68]==null) arrConf[68]="0"; return Integer.parseInt(arrConf[68]); }
//		
//		// ========== set FONT label di FORM UTAMA
////		public int getFontSizeStatusBar() { if (arrConf[24]==null) arrConf[24]="0"; return Integer.parseInt(arrConf[24]); }
//
//		// ========== set PDF -BISA OTOMATIS
//		// ========== set DOC -BISA OTOMATIS
//		// ========== set FUNGSI discard -BISA OTOMATIS
//		// ========== set KONFIGURASI printer -BISA OTOMATIS
//		// ========== set OTOMATIS checklist pada Send at -BISA OTOMATIS
//		// ========== set FONT untuk textbox Freetext -BISA OTOMATIS
//		// ========== set JUMLAH karakter berita yang akan dikirim -BISA OTOMATIS
//		// ========== set TEMPLATE -BISA OTOMATIS
//		// ========== set TINGGI scroll BODY TEMPLATE -BISA OTOMATIS
//		// ========== set LEBAR textbox OriRef -BISA OTOMATIS
//		// ========== set TABLE ATS: tinggi,nama kolom dan panjang kolom -BISA OTOMATIS
//	}
	
	public static void time() {
		threadtime = new Thread() {
            public void run() {
                while (true) {
                    TeleSplashScreen2016IP.display.syncExec(new Runnable() {
                        @Override
                        public void run() {
//                        	getConf();
                        	
                        	final DateFormat datetimeFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");// aaa");
                        	if (TeleSplashScreen2016IP.telestat.compareTo("1")==0) {//if (ReadFromFile.ReadInputFile1("/tp/template/telestat.txt").compareTo("1")==0) {
	                        	if (MainForm.clTimer!=null) {
	                        		if (!MainForm.clTimer.isDisposed()) {
	                        			Date date = new Date();
	                                    str = datetimeFormat.format(date);
	                                    sh.clabelStyle(MainForm.clTimer, str, MainForm.ixTime, SWT.CENTER, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
	                        		}
	                        	}
                        	} else if (ReadFromFile.ReadInputFile1("/tp/template/telestat.txt").compareTo("0")==0) {}
                        	
                        	final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");// aaa");                        	
                        	if (TeleSplashScreen2016IP.telestat.compareTo("1")==0) {//if (ReadFromFile.ReadInputFile1("/tp/template/telestat.txt").compareTo("1")==0) {
	                        	if (Calendar.clTimer!=null) {
	                        		if (!Calendar.clTimer.isDisposed()) {
	                        			Date date = new Date();
	                                    String str = timeFormat.format(date);
	                                    sh.clabelStyle(Calendar.clTimer, str, 200, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL, null);
	                        		}
	                        		
	                        	}
                        	} else if (ReadFromFile.ReadInputFile1("/tp/template/telestat.txt").compareTo("0")==0) {}
                        	
                        	//1: Continuous printer
	    					//0: Standard printer
                        	if (TeleSplashScreen2016IP.telestat.compareTo("1")==0) {//if (ReadFromFile.ReadInputFile1("/tp/template/telestat.txt").compareTo("1")==0) {
		    					String printerType = ReadFromFile.ReadInputFile1("/tp/stlp.txt");
		    					if (!printerType.isEmpty() && printerType.compareTo("1")==0) {
		    						printerType="Cont.";
		    						ReadFromFile.readConfiguration();
		    						if (ReadFromFile.getPrint().compareToIgnoreCase("yes")==0 || ReadFromFile.getPrint().compareToIgnoreCase("y")==0) { 
		    							if (!MainForm.clPQueue.isDisposed()) MainForm.clPQueue.setEnabled(true); 
		    							if (!MainForm.clPQueue.isDisposed()) MainForm.clPQueue.setForeground(Colors.NORMAL);
		    							if (!MainForm.miQueue.isDisposed()) MainForm.miQueue.setEnabled(true);
		    						} 
		    					} else {
		    						printerType="Std.";
		    						ReadFromFile.readConfiguration();
		    						if (ReadFromFile.getPrint().compareToIgnoreCase("yes")==0 || ReadFromFile.getPrint().compareToIgnoreCase("y")==0) {
		    							if (!MainForm.clPQueue.isDisposed()) MainForm.clPQueue.setEnabled(false);
		    							if (!MainForm.clPQueue.isDisposed()) MainForm.clPQueue.setForeground(Colors.DarkGrey);
		    							if (!MainForm.miQueue.isDisposed()) MainForm.miQueue.setEnabled(false);
		    						} 
		    					}
		    					
	                        	if (MainForm.clPrinterType!=null) {
	                        		if (!MainForm.clPrinterType.isDisposed()) {
	                        			ReadFromFile.readConfiguration();
	                        			if (ReadFromFile.getPrint().compareToIgnoreCase("yes")==0 || ReadFromFile.getPrint().compareToIgnoreCase("y")==0) {
	                        				sh.clabelStyle(MainForm.clPrinterType, printerType, MainForm.ixPrinterType, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, Images.img_printer16);	
	                        			} else {
	                        				sh.clabelStyle(MainForm.clPrinterType, "", MainForm.ixPrinterType, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
	                        			}
	                        		}
	                        	}
                        	} else if (ReadFromFile.ReadInputFile1("/tp/template/telestat.txt").compareTo("0")==0) {}
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        threadtime.setDaemon(true);
        threadtime.start();
        
	}
}
