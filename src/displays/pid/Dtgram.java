package displays.pid;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Shell;

import readwrite.ReadFromFile;
import setting.Colors;
import setting.Images;
import setting.Shorten;
import databases.jdbc;
import displays.MainForm;
import displays.TeleSplashScreen2016IP;

public class Dtgram implements Runnable{
	
	Shorten sh = new Shorten();
	ReadFromFile rff = new ReadFromFile();
	
	private static boolean g_stclose=false;
	DatagramSocket socket;
	DatagramPacket packet;
	Shell shell;
	Shell shellFPL;
	Shell shellDEP;
	Shell shellStatusConn;
	static String sTable="",sTable1="",sTable2;
	static String sMetTable[] = new String[18];
	static String sNtmTable[] = new String[9];
	String sResrcv="";
	String sResrcv1="";
	String sdt="";
	int jumlah=0;
	
	
	public void run() {
		try{
			System.out.println("openbef "+socket+" "+packet);
		    byte message[] = new byte[256];
			socket = new DatagramSocket(101);
    		packet = new DatagramPacket(message, message.length);
    		MainForm.socket=socket;
    		
    		if (!shdisp.getDisplay().isDisposed()) {
    			shdisp.getDisplay().syncExec(new Runnable() {
    				public void run() {
    					shell = new Shell(SWT.Close);
    					shellFPL = new Shell(SWT.MIN);
    					shellDEP = new Shell(SWT.MIN);
    					shellStatusConn = new Shell(SWT.MAX);
    				}
    			});
    		}
    		
    		while (true) {	
				if (g_stclose) break;
				else {
					System.out.println("waitc");
					try{
					    System.out.println("waitcc");
					    socket.receive(packet);
					    sResrcv = new String(packet.getData());
					    int ln=packet.getLength();
					    sResrcv.substring(0, ln);
					    sResrcv1=sResrcv.substring(0, ln);
					    System.out.println("length"+packet.getLength()+" "+sResrcv1);
	
					    if (sResrcv1.startsWith("dead")) {System.exit(0);}
					    System.out.println("ln:"+ln+" from:"+packet.getAddress());
					 
					    if ((sResrcv1.startsWith("Tseq")) || 
					    		(sResrcv1.startsWith("Rseq")) || 
					    		(sResrcv1.startsWith("Queu")) || 
					    		(sResrcv1.startsWith("Line")) || 
					    		(sResrcv1.startsWith("Link")) || 
					    		(sResrcv1.startsWith("Conn")) || 
					    		(sResrcv1.startsWith("warning")) || 
					    		(sResrcv1.startsWith("QPrint")) ||
					    		(sResrcv1.startsWith("Free"))) {
					    	
						    System.out.println("****sdtA:"+sdt);
					    	sdt = sResrcv1.substring(5, sResrcv1.length());
						    System.out.println("****sdtB:"+sdt);

				    		if (!shdisp.getDisplay().isDisposed()) {
				    			shdisp.getDisplay().syncExec(new Runnable() {
				    				public void run() {
				    					if (sResrcv1.startsWith("Tseq")) {
				    						if (TeleSplashScreen2016IP.telestat.compareTo("1")==0) {//if (ReadFromFile.ReadInputFile1("/tp/template/telestat.txt").compareTo("1")==0) {
				    							if (MainForm.clTseq!=null) {
					                        		if (!MainForm.clTseq.isDisposed()) {
					                        			sh.clabelStyle(MainForm.clTseq, "Tseq : "+sdt, MainForm.ixTseq, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
					                        		}
				    							}
				    						}
				    					}
				    					
				    					/*else if (sResrcv1.startsWith("Rseq")) {
				    						if (TeleSplashScreen2016IP.telestat.compareTo("1")==0) {//if (ReadFromFile.ReadInputFile1("/tp/template/telestat.txt").compareTo("1")==0) {
				    							if (MainForm.clRseq!=null) {
					                        		if (!MainForm.clRseq.isDisposed()) {
					                        			sh.clabelStyle(MainForm.clRseq, "Rseq : "+sdt, MainForm.ixRseq, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
					                        		}
				    							}
				    						}
				    						
				    					}*/
				    					
				    					else if (sResrcv1.startsWith("Queu")) {
				    						if (TeleSplashScreen2016IP.telestat.compareTo("1")==0) {//if (ReadFromFile.ReadInputFile1("/tp/template/telestat.txt").compareTo("1")==0) {
				    							if (MainForm.clQueue!=null) {
					                        		if (!MainForm.clQueue.isDisposed()) {
					                        			sh.clabelStyle(MainForm.clQueue, "Queue : "+sdt, MainForm.ixQueue, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.BLUE, null);
					                        			if (!sdt.startsWith("0")) MainForm.clQueue.setForeground(Colors.red);
					                        			else MainForm.clQueue.setForeground(Colors.BLUE);
					                        		}
				    							}
				    						}
				    					}

				    					else if (sResrcv1.startsWith("Conn")) {
				    						String snis = DisplayMain.readInputFile("/tp/nis.txt");
				    						if (!snis.isEmpty()) {
				    							String sarr[] = snis.split("\n");
				    							snis="";
				    							for (int i=0;i<1;i++) {
				    								snis=sarr[i];
				    							}
				    						}
				    						/*if (snis.compareTo("1")==0) {
				    							if (MainForm.clRefuse!=null) {
					                        		if (!MainForm.clRefuse.isDisposed()) {
					                        			if (sResrcv1.compareToIgnoreCase("Conn,Conn : Success")==0 || 
					                        					sResrcv1.compareToIgnoreCase("Conn,Conn : OK")==0 || 
					                        					sResrcv1.compareToIgnoreCase("Conn,Conn :OK")==0 || 
					                        					sResrcv1.compareToIgnoreCase("Conn,Conn: OK")==0 || 
					                        					sResrcv1.compareToIgnoreCase("Conn,Conn:OK")==0) {
					                        				int indColon = sResrcv1.indexOf(":");
					                        				String str = sResrcv1.substring(indColon+1, sResrcv1.length());
					                        				sh.clabelStyle(MainForm.clRefuse, "Connection : "+str, MainForm.ixRefuse, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
					                        			}
					                        				
					                        			else {//if (sResrcv1.compareToIgnoreCase("Conn,Conn : Connection refused")==0)
					                        				int indColon = sResrcv1.indexOf(":");
					                        				String str = sResrcv1.substring(indColon+1, sResrcv1.length());
					                        				sh.clabelStyle(MainForm.clRefuse, "Connection : "+str, MainForm.ixRefuse, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.red, null);
					                        			}
					                        			
//					                        			if (sResrcv1.compareToIgnoreCase("Conn,Conn : Connection refused")==0)
//					                        				sh.clabelStyle(MainForm.clRefuse, "Connection : refused", MainForm.ixRefuse, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.red, null);
//					                        			else if (sResrcv1.compareToIgnoreCase("Conn,Conn : Success")==0) 
//					                        				sh.clabelStyle(MainForm.clRefuse, "Connection : success", MainForm.ixRefuse, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
//					                        			
//					                        			String strRefuse=sResrcv1.replaceFirst("Conn,", "");
//					                        			if (strRefuse.contains("Refuse"))
//					                        				sh.clabelStyle(MainForm.clRefuse, strRefuse, MainForm.ixRefuse, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.red, null);
//					                        			else if (strRefuse.contains("Success")) 
//					                        				sh.clabelStyle(MainForm.clRefuse, strRefuse, MainForm.ixRefuse, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
					                        		}
				    							}
				    						} else {
				    							if (MainForm.clRefuse!=null) {
					                        		if (!MainForm.clRefuse.isDisposed()) {
					                        			sh.clabelStyle(MainForm.clRefuse, "", MainForm.ixRefuse, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.red, null);
					                        		}
				    							}
				    						}*/
				    					}
				    					else if ((sResrcv1.startsWith("Line"))||(sResrcv1.startsWith("Link"))) {
				    						System.out.println("***sdt"+sdt);
				    						if (TeleSplashScreen2016IP.telestat.compareTo("1")==0) {//if (ReadFromFile.ReadInputFile1("/tp/template/telestat.txt").compareTo("1")==0) {
				    							if (MainForm.clLine!=null) {
					                        		if (!MainForm.clLine.isDisposed()) {
					                        			sh.clabelStyle(MainForm.clLine, sdt, MainForm.ixLine, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
					                        		}
				    							}
				    						}
				    						
				    						if ((sdt.contains("Open")) || (sdt.contains("Down")) )
				    							if (TeleSplashScreen2016IP.telestat.compareTo("1")==0) {//if (ReadFromFile.ReadInputFile1("/tp/template/telestat.txt").compareTo("1")==0) {
				    								if (MainForm.clLine!=null) {
				    	                        		if (!MainForm.clLine.isDisposed()) {
				    	                        			MainForm.clLine.setForeground(Colors.red);
				    	                        		}
				    								}
				    							}
				    						else 
				    							if (TeleSplashScreen2016IP.telestat.compareTo("1")==0) {//if (ReadFromFile.ReadInputFile1("/tp/template/telestat.txt").compareTo("1")==0) {
				    								if (MainForm.clLine!=null) {
				    	                        		if (!MainForm.clLine.isDisposed()) {
				    	                        			MainForm.clLine.setForeground(Colors.BLUE);
				    	                        		}
				    								}
				    							}
				    					}
				    					
				    					else if (sResrcv1.startsWith("Free")) {
				    						if (TeleSplashScreen2016IP.telestat.compareTo("1")==0) {//if (ReadFromFile.ReadInputFile1("/tp/template/telestat.txt").compareTo("1")==0) {
				    							if (MainForm.clFree!=null) {
					                        		if (!MainForm.clFree.isDisposed()) {
					                        			sh.clabelStyle(MainForm.clFree, sdt, MainForm.ixfree, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
					                        		}
				    							}
				    						}
				    					}

				    					else if (sResrcv1.startsWith("warning")) {
											
				    						Connection conn = null; // connection object
				    					    Statement stmt = null; // statement object
				    					    ResultSet rs = null; // result set object
				    					    
									    	try { conn = jdbc.getDBConnection(); } 
											catch (Exception e) { e.printStackTrace(); }
											
											try {
										    	stmt = conn.createStatement();
												String lihatsql = "SELECT * FROM warning";				
												System.out.println("\nQuery: " + lihatsql);
												rs = stmt.executeQuery(lihatsql);
												rs.last();
												jumlah = rs.getRow();
												System.out.println("Jumlah warning=" + jumlah);
												if (!TeleSplashScreen2016IP.display.isDisposed()) {
									    			TeleSplashScreen2016IP.display.syncExec(new Runnable() {
									    				public void run() {
									    					if (TeleSplashScreen2016IP.telestat.compareTo("1")==0) {//if (ReadFromFile.ReadInputFile1("/tp/template/telestat.txt").compareTo("1")==0) {
									    						if (MainForm.clWarning!=null) {
									                        		if (!MainForm.clWarning.isDisposed()) {
									                        			sh.clabelStyle(MainForm.clWarning, "Incm wrng msg : "+jumlah, MainForm.ixWarning, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
									                        		}
								                        		}
									    					}
									    				}
									    			});
									    		}
												
												rs.close();
												stmt.close();
												conn.close();
												
											} catch(SQLException se) {
												se.printStackTrace();
											}
									    }
				    					
				    					else if (sResrcv1.startsWith("QPrint")) {

				    						if (!TeleSplashScreen2016IP.display.isDisposed()) {
								    			TeleSplashScreen2016IP.display.syncExec(new Runnable() {
								    				public void run() {
								    					if (TeleSplashScreen2016IP.telestat.compareTo("1")==0) {//if (ReadFromFile.ReadInputFile1("/tp/template/telestat.txt").compareTo("1")==0) {
								    						if (MainForm.clPQueue!=null) {
								                        		if (!MainForm.clPQueue.isDisposed()) {
											    					ReadFromFile.readConfiguration();
											    					if (ReadFromFile.getPrint().compareToIgnoreCase("yes")==0 || ReadFromFile.getPrint().compareToIgnoreCase("y")==0) {
											    						if (!MainForm.clPQueue.isDisposed()) {
											    							MainForm.clPQueue.setText("Print Queue \n"+MainForm.query(MainForm.queryQueuelp));
											    							MainForm.clPQueue.setImage(Images.img_printer32);
											    							MainForm.clPQueue.setFont(Shorten.getFont());
											    							MainForm.clPQueue.setForeground(Colors.NORMAL);
												    					}
											    					}
										    					}
										    				}
								    					}
								    				}
								    			});
								    		}
				    						
//				    						Connection conn = null; // connection object
//				    					    Statement stmt = null; // statement object
//				    					    ResultSet rs = null; // result set object
//											
//									    	try { conn = jdbc.getDBConnection(); } 
//											catch (Exception e) { e.printStackTrace(); }
//											
//											try {
//										    	stmt = conn.createStatement();
//												String lihatsql = "SELECT * FROM queuelp";				
//												System.out.println("\nQuery: " + lihatsql);
//												rs = stmt.executeQuery(lihatsql);
//												rs.last();
//												jumlah = rs.getRow();
//												System.out.println("Jumlah queue=" + jumlah);
//												if (!TeleSplashScreen.display.isDisposed()) {
//									    			TeleSplashScreen.display.syncExec(new Runnable() {
//									    				public void run() {
//									    					ReadFromFile.readConfiguration();
//									    					if (ReadFromFile.getPrint().compareToIgnoreCase("yes")==0 || ReadFromFile.getPrint().compareToIgnoreCase("y")==0) {
//										    					if (!MainForm.itemQueue.isDisposed()) {
//										    						sh.toolitemStyle(MainForm.itemQueue, "Print &Queue:"+jumlah+"  ", TableQueueLP.getText(), Images.img_printer32);
//										    					}
//									    					}
//									    				}
//									    			});
//									    		}
//												
//												rs.close();
//												stmt.close();
//												conn.close();
//												
//											} catch(SQLException se) {
//												se.printStackTrace();
//											}
									    } 
				    				}
				    			});
				    		}
					    }

					    else if (sResrcv1.startsWith("id")) {
						    String sQuery1="";
							int typ1=-1;
							getYearMonth();
							
							sQuery1="SELECT * FROM incoming_msg";
							String s=sResrcv1.substring(2, sResrcv1.length());
							if (!s.isEmpty()) sQuery1+=" where id='"+s+"' or id="+s;
							sQuery1+=" ORDER BY tgl desc,id DESC";
							typ1=0;
							shdisp.OneByOne(1,sQuery1,typ1);
					    }
					    
					} catch(IOException e){
						e.printStackTrace();
					}
				}
			}//end while
		} catch(IOException e){
			e.printStackTrace();
			System.out.println("error");
		}
	}
	
	public static void setstclose(boolean stclose1){
		g_stclose=stclose1;
	}

	public static void getYearMonth() {
        java.util.Date tgl = new java.util.Date();
        long waktujava = tgl.getTime();
        java.sql.Timestamp stemp = new java.sql.Timestamp(waktujava);
        System.out.println("stemp:" + stemp);
        String yearMonth = stemp.toString().substring(0, 7);
        yearMonth = yearMonth.replace("-", "_");
        System.out.println("yearMonth:" + yearMonth);
        sTable = "air_message" + yearMonth;
        sTable1 = "air_message_free_text" + yearMonth;
        sTable2 = "reject_message_ats"+yearMonth;
        System.out.println("sTable:"+sTable);
        System.out.println("sTable1:"+sTable1);
        System.out.println("sTable2:"+sTable2);
	}
	
	static String gtMonth(){
        sMetTable[0]="meteo_metar";
        sMetTable[1]="meteo_speci";
        sMetTable[2]="meteo_sigmet";
        sMetTable[3]="meteo_airmet";
        sMetTable[4]="meteo_tafor";
        sMetTable[5]="meteo_tafor";
        sMetTable[6]="meteo_airep";
        sMetTable[7]="meteo_arfor";
        sMetTable[8]="meteo_rofor";
        sMetTable[9]="meteo_synop";
        sMetTable[10]="meteo_wins_war";
        sMetTable[11]="meteo_wintem";
        sMetTable[12]="meteo_wsynop";
        sMetTable[13]="vulcano_adv";
        sMetTable[14]="rqm";
        sMetTable[15]="volcanic_act";
        sMetTable[16]="air_message_free_text_meteo";
        sMetTable[17]="reject_message";

        sNtmTable[0]="ashtam";
        sNtmTable[1]="birdtam";
        sNtmTable[2]="notam_multi";
        sNtmTable[3]="snowtam";
        sNtmTable[4]="rqn";
        sNtmTable[5]="rql";
        sNtmTable[6]="reject_message_notam";
        sNtmTable[7]="notam_criteria";
        sNtmTable[8]="notam_cl";
        java.util.Date tgl = new java.util.Date();
        long waktujava = tgl.getTime();
        java.sql.Timestamp stemp = new java.sql.Timestamp(waktujava);
        System.out.println("stemp:" + stemp);
        String yearMonth = stemp.toString().substring(0, 7);
        yearMonth = yearMonth.replace("-", "_");
        System.out.println("yearMonth:" + yearMonth);
		return yearMonth;
	}
	
	String getstamp() {
        java.util.Date tgl = new java.util.Date();
        long waktujava = tgl.getTime();
        java.sql.Timestamp stamp = new java.sql.Timestamp(waktujava);

        String tmp3 = stamp.toString();
        int q;
        String tm1;
        tm1 = tmp3;
        q = tm1.length();
        if (q > 4)
            q = 4;
        String subs2 = tm1.substring(0, 19);
        return subs2;
	}

}
