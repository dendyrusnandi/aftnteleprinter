package ti_iais;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import displays.AmscSplashScreen2;

import actions.cProgressBar;



public class TableRejectedMETEO {
//	private java.util.List playersRejMETEO;
//	private PlayerComparatorRejMETEO comparatorRejMETEO;
//	
////	ButtonsSetting bs = new ButtonsSetting();
////	Criteria crit = new Criteria();
////	HeaderComposite HeaderComposite;
//	
//	//untuk refresh table
//	Shell shell;
//	static String pDate="",pDateTo="",pt7a="",pt13a="",pt13b="",pt15b="",pt15bFr="",pt15bTo="",pt15c="",pt16a="";
//	//untuk table
//	Connection conn;
//	Table table;
//	TableItem[] selection;
//	Button next, prev, first, last, go;
//	Text tPage, tTotPage, tGoToHal;
//	static String id="",P1="",P2="",P3="",P4="",P5="",P6="",P7="",P8="",sqlSelect="",string="",string2="",isiHal="",jml_item="";
//	static String fta="",ftb="",ftc="",ftd="",fte="",ftf="";
//	static int baris=20,qq=0,jumlah=0,rowNo=0,qu=0;
//	int dir=0,p=0,yu=0;
//	
//	//period -update 20101109
//	int iCnt=0; //counter ResultSet
//	String tblName="",id_rjt_msg="",yearMonth="",yearFr="",monthFr="",yearTo="",monthTo="",tglReject="";
//	String lihatsql="",select="",where="",order="",tgl="",tglTo="",tipe7a="",tipe13a="",tipe13b="",tipe15b="",tipe15bFr="",tipe15bTo="",tipe15c="",tipe16a="";
//	Connection conn1[];
//	ResultSet rs[];
//	
//	List cPriority;
//	Text tFiling,tOriginator,tOri_Ref,text,tError,tType;
//	Text tDest1,tDest2,tDest3,tDest4,tDest5,tDest6,tDest7;
//	Text tDest8,tDest9,tDest10,tDest11,tDest12,tDest13,tDest14;
//	Text tDest15,tDest16,tDest17,tDest18,tDest19,tDest20,tDest21;
//	
//	static ScrollBar sbPrio;
//	static int sbiPrio,lsbiPrio,initPrio;
//	
//	//Priority
//	final String FF = "FF";
//	final String DD = "DD";
//	final String GG = "GG";
//	final String KK = "KK";
//	final String SS = "SS";
//	String[] priority = new String[]{FF,DD,GG,KK,SS};
//	
//  	public TableRejectedMETEO() { }
//
//  	void koneksiDB(Shell pShell, String pFrom, String pTo) {
//  		shell=pShell;
//  		pDate=pFrom;
//  		pDateTo=pTo;
//  		//------------------------------------ KONEKSI DB ------------------------------------
//		try {
//			String driver = "com.mysql.jdbc.Driver";
//			Class.forName(driver).newInstance();
//		} catch(ClassNotFoundException c) {
//			c.printStackTrace();
//		} catch(Exception ex){
//			ex.printStackTrace();
//		}
//		
//		final cProgressBar progbr = new cProgressBar("Selecting data ...");
//		new Thread() {
//	        public void run() {
//	        	if (AmscSplashScreen2.display.isDisposed()) return;
//	        	AmscSplashScreen2.display.asyncExec(new Runnable() {
//	        		public void run() {
//	        			try {
//	        				progbr.create(baris);
//							// Create the comparatorRejMETEO used for sorting
//						    comparatorRejMETEO = new PlayerComparatorRejMETEO();
//						    comparatorRejMETEO.setColumn(PlayerComparatorRejMETEO.TGL);
//						    comparatorRejMETEO.setDirection(PlayerComparatorRejMETEO.ASCENDING);
//						    
//						    // Create the playersRejMETEO
//						    playersRejMETEO = new ArrayList();
//						    
//							select = "SELECT * FROM ";
//							where = " WHERE tgl!='0000-00-00 00:00:00'";
//							order = " ORDER BY tgl DESC";
//
//							crit.tgl();
//							System.out.println("\nDate Now::" + crit.tg);
//							yearMonth = crit.tg.substring(0, 7);
//							if (yearMonth.contains("-")) yearMonth = yearMonth.replace("-", "_");
//							tblName="reject_message"+yearMonth;
//							
//							//tgl update20101111 [pencarian bisa sampai tahun, bulan, tanggal, menit, detik]
//							if (pDate.compareTo("0000-00-00 00:00") != 0) { //tampilkan data dari Date/Time ini
//								crit.Date(pDate);
//								tgl = " AND tgl >= '"+crit.Date+"'"; 
//							
//								if (crit.Date.length()>=4) yearFr = crit.Date.substring(0, 4); else yearFr=yearMonth.substring(0, 4);
//								if (crit.Date.length()>=7) monthFr = crit.Date.substring(5, 7); else monthFr="01";
//								//Field Date/Time To tidak diisi. -update 20101206
//								if (pDateTo.compareTo("0000-00-00 00:00") == 0) {
//									yearTo=yearMonth.substring(0, 4);
//									monthTo=yearMonth.substring(5, 7);
//
//									String ddhhmm = crit.tanggal.substring(7, crit.tanggal.length());
//									crit.DateTo(yearTo+"-"+monthTo+ddhhmm);
//									tglTo = " AND tgl <= '"+crit.Date+"'";
//								}
//							} 
//							if (pDateTo.compareTo("0000-00-00 00:00") != 0) { //tampilkan data sampai Date/Time ini
//								crit.DateTo(pDateTo);
//								tglTo = " AND tgl <= '"+crit.Date+"'";
//								
//								if (crit.Date.length()>=4) yearTo = crit.Date.substring(0, 4); else yearTo=yearMonth.substring(0, 4);
//								if (crit.Date.length()>=7) monthTo = crit.Date.substring(5, 7); else monthTo="12"; 
//								String yeartoo=yearTo;
//								//Field Date/Time From tidak diisi. 
//								if (pDate.compareTo("0000-00-00 00:00") == 0) {
//									yearFr=yeartoo;//yearMonth.substring(0, 4);
//									monthFr="01";//yearMonth.substring(5, 7);
//									tgl = " AND tgl >= '"+yeartoo+"-01-01 00:00:00"/*crit.Date*/+"'";
//								}
//							}
//							if (pDate.compareTo("0000-00-00 00:00") == 0 && pDateTo.compareTo("0000-00-00 00:00") == 0) { //tampilkan data hari ini
//								tgl = " AND tgl >= '"+crit.tg+" 00:00:00'"; 
//								tglTo = " AND tgl <= '"+crit.tg+" 23:59:59'";
//								tblName="reject_message"+yearMonth;
//								yearFr=yearTo=yearMonth.substring(0, 4);
//								monthFr=monthTo=yearMonth.substring(5, 7);
//							}
//							
//							jumlah=0; //inisialisasi
//							iCnt=0;
//							rowNo = 0;
//							//menentukan tblName berdasarkan Date/Time
//							System.out.println("Date From::" + yearFr + "-" + monthFr + "*");
//							System.out.println("Date To::" + yearTo + "-" + monthTo + "*");
//							
//							Connection conn1[] = new Connection[Integer.parseInt(yearTo)*Integer.parseInt(monthTo)];
//							rs = new ResultSet[Integer.parseInt(yearTo)*Integer.parseInt(monthTo)];
//							
//							for (int iYear=Integer.parseInt(yearFr); iYear<=Integer.parseInt(yearTo); iYear++) {
//								tblName = "reject_message"+iYear;
//								
//								int iMtemp=0;
//								int n=0;
//								boolean flg=false;
//								if (iYear==Integer.parseInt(yearFr)) {
//									iMtemp=Integer.parseInt(monthFr);
//									n=12-iMtemp;
//								}
//								else if ((iYear>Integer.parseInt(yearFr)) && (iYear < Integer.parseInt(yearTo))) {
//									iMtemp=1;
//									n=12-iMtemp;
//								}
//								else if (iYear == Integer.parseInt(yearTo)) {
//									iMtemp= Integer.parseInt(monthTo);
//									n=iMtemp;
//									iMtemp=0;
//									flg=true;
//								}
//								
//								int nn=0;
//								for (int iMonth=iMtemp; iMonth<=n+iMtemp; iMonth++) {
//									if ((flg) && (iMonth>=12)) { 
//										nn++; 
//										if ((n==iMtemp) && (nn>12)) break; 
//									} else nn=iMonth;
//									
//									String sMonth = Integer.toString(nn);//iMonth);
//									if (sMonth.length()==1) sMonth = sMonth.replace(sMonth, "0"+sMonth);
//									tblName = tblName.replace(tblName, "reject_message"+iYear+"_"+sMonth);
//									
//									conn1[iCnt] = conn;
//									conn1[iCnt] = MenuItemSearchReject.gConn;
//									Statement stmt = conn1[iCnt].createStatement();
//									
//									try { //biar kalo ada table yang doesn't exist ga mati.
//										lihatsql = select+tblName+where+tgl+tglTo+order;
//										System.out.println("\nQuery: " + lihatsql);
//										rs[iCnt] = stmt.executeQuery(lihatsql);
//					
//										rs[iCnt].last();
//										jumlah += rs[iCnt].getRow();
//										rs[iCnt].beforeFirst();
//							
//										final String datatabel[][] = new String[baris][8];
//										p = 0;
//								
//										while (rs[iCnt].next() && (rowNo < baris)) {
//											rowNo++;
//											
//											datatabel[p][0] = rs[iCnt].getString("id_rjt_msg"); id=datatabel[p][0]; if (id==null){ id=""; }
//											datatabel[p][1] = rs[iCnt].getString("seqid"); P1=datatabel[p][1]; if (P1==null){ P1=""; }
//											datatabel[p][2] = rs[iCnt].getString("seqnr"); P2=datatabel[p][2]; if (P2==null){ P2=""; }
//											datatabel[p][3] = rs[iCnt].getString("filing_time"); P3=datatabel[p][3]; if (P3==null){ P3=""; }
//											datatabel[p][4] = rs[iCnt].getString("originator"); P4=datatabel[p][4]; if (P4==null){ P4=""; }
//											datatabel[p][5] = rs[iCnt].getString("origin_message"); P5=datatabel[p][5]; if (P5==null){ P5=""; }
//											datatabel[p][6] = rs[iCnt].getString("tgl"); P6 = datatabel[p][6].substring(0, 19); if (P6==null){ P6=""; }
//											datatabel[p][7] = rs[iCnt].getString("tbl_name"); P7=datatabel[p][7]; if (P7==null){ P7=""; }
//											if (P5.contains("`")) P5 = P5.replace("`", "'");
//											if (P5.contains("\n")) {
//												P5 = P5.substring(0, P5.indexOf("\n"));
//												if (P5.length() >= 45) { P5 = P5.substring(0,45).concat(" ..."); }
//											}
//											
//											p++;
//											playersRejMETEO.add(new PlayerRejMETEO(id,P1,P2,P3,P4,P5,P6,P7));
//											progbr.bar.setSelection(rowNo);
//										} //end of while
//									} catch (SQLException s) { // biar kalo ada table yang doesn't exist ga mati.
//										System.out.println("Error: " + s.getMessage());
//									}
//									iCnt++; //counter rs
//								} //end of for2
//							} //end of for1
//							progbr.finish();
//				
//							if (getJumlah() != 0) {
//								shell.setText("List of Rejected Meteo Messages");
//								createContents(shell);
//								shell.setLocation(0,200);
//								shell.setSize(500, 700);
//								shell.setLayout(new GridLayout());
//								shell.pack();
//								shell.open();
//					  		} else {
//					  			DialogFactory.openInfoDialog("Rejected Meteo Messages","There's no data !!");
//					  		}
//						} catch (SQLException s) {
//							DialogFactory.openInfoDialog("Search Message", s.toString());
//							s.printStackTrace();
//							progbr.finish();
//							try {
//								if (MenuItemSearchReject.gConn!=null) MenuItemSearchReject.gConn.close();
//								MenuItemSearchReject.gConn = DriverManager.getConnection(jdbc.getUrl(),jdbc.getUser(),jdbc.getPass());
//							} catch(SQLException e){
//								System.out.println("Fatal Error: "+e);
//							}
//						} catch (java.lang.OutOfMemoryError hs) {
//		        			hs.printStackTrace();
//							DialogFactory.openInfoDialog("Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
//							progbr.finish();
//							try {
//								if (MenuItemSearchReject.gConn!=null) MenuItemSearchReject.gConn.close();
//								MenuItemSearchReject.gConn = DriverManager.getConnection(jdbc.getUrl(),jdbc.getUser(),jdbc.getPass());
//							} catch(SQLException e){
//								System.out.println("Fatal Error: "+e);
//							}
//						}
//	        		}
//	        	});
//	        }
//		}.start();
//		progbr.begin();
//	}
//							
//  	void Next() {
//  		final cProgressBar progbr = new cProgressBar("Selecting data ...");
//		new Thread() {
//			public void run() {
//				if (AmscSplashScreen2.display.isDisposed()) return;
//				AmscSplashScreen2.display.asyncExec(new Runnable() {
//					public void run() {
//						try {
//							progbr.create(baris);
//						    playersRejMETEO.clear();
//							rowNo = 0;
//							
//							for (int i=0; i<iCnt; i++) {
//								try {
//									if (rs[i]!=null) {
//										final String datatabel[][] = new String[baris][8];
//										p = 0;
//										rs[i].beforeFirst();
//
//										while (rs[i].next()) {
//											rowNo++;
//											if ((rowNo>(qq*baris)) && (rowNo<=((qq*baris)+baris))) {
//												datatabel[p][0] = rs[i].getString("id_rjt_msg"); id=datatabel[p][0]; if (id==null){ id=""; }
//												datatabel[p][1] = rs[i].getString("seqid"); P1=datatabel[p][1]; if (P1==null){ P1=""; }
//												datatabel[p][2] = rs[i].getString("seqnr"); P2=datatabel[p][2]; if (P2==null){ P2=""; }
//												datatabel[p][3] = rs[i].getString("filing_time"); P3=datatabel[p][3]; if (P3==null){ P3=""; }
//												datatabel[p][4] = rs[i].getString("originator"); P4=datatabel[p][4]; if (P4==null){ P4=""; }
//												datatabel[p][5] = rs[i].getString("origin_message"); P5=datatabel[p][5]; if (P5==null){ P5=""; }
//												datatabel[p][6] = rs[i].getString("tgl"); P6 = datatabel[p][6].substring(0, 19); if (P6==null){ P6=""; }
//												datatabel[p][7] = rs[i].getString("tbl_name"); P7=datatabel[p][7]; if (P7==null){ P7=""; }
//												if (P5.contains("`")) P5 = P5.replace("`", "'");
//												if (P5.contains("\n")) {
//													P5 = P5.substring(0, P5.indexOf("\n"));
//													if (P5.length() >= 45) { P5 = P5.substring(0,45).concat(" ..."); }
//												}
//												
//												TableItem item = new TableItem (table, SWT.NONE);
//												item.setText (0,id);
//												item.setText (1,P1);
//												item.setText (2,P2); 
//												item.setText (3,P3);
//												item.setText (4,P4); 
//												item.setText (5,P5); 
//												item.setText (6,P6); 
//												item.setText (7,P7);
//												
//												p++;
//												playersRejMETEO.add(new PlayerRejMETEO(id,P1,P2,P3,P4,P5,P6,P7));
//												progbr.bar.setSelection(rowNo);
//											} //end of if
//											if (rowNo >= ((qq*baris)+baris)) break;
//										} //end of while
//										if (rowNo >= ((qq*baris)+baris)) break;
//									} //end of if
//								} catch (SQLException s) {
//									DialogFactory.openInfoDialog("Search Message", s.getMessage());
//									s.printStackTrace();
//								} //enf of try
//							} //end of for
//							progbr.finish();
//						} catch (java.lang.OutOfMemoryError hs) {
//		        			hs.printStackTrace();
//							DialogFactory.openInfoDialog("Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
//							progbr.finish();
//							try {
//								if (MenuItemSearchReject.gConn!=null) MenuItemSearchReject.gConn.close();
//								MenuItemSearchReject.gConn = DriverManager.getConnection(jdbc.getUrl(),jdbc.getUser(),jdbc.getPass());
//							} catch(SQLException e){
//								System.out.println("Fatal Error : "+e);
//							}
//						} //end of try
//					} //end of run
//				}); //end of runnable
//			}
//		}.start();
//		progbr.begin();
//	}
//
//  	void createContents(Composite composite) {
//  		final Group Group = new Group(composite,SWT.NONE);
//		Group.setText("List of Rejected Meteo Messages");
//		Group.setLayout(new GridLayout(1,false));
//		
//	    // Create the table
//	    table = new Table (Group, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
//		table.setLinesVisible (true);
//		table.setHeaderVisible (true);
//		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
//		data.heightHint = 100;
//		data.widthHint = 900;
//		table.setLayoutData(data);
//		
//	    // Create each of the columns, adding an event listener 
//	    // that will set the appropriate fields into the comparatorRejMETEO 
//	    // and then call the fillTable helper method
//		final TableColumn ID = new TableColumn(table,SWT.LEFT);
//		ID.setText("id");
//		ID.setWidth(0); ID.setResizable(false);
//		ID.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent event) {
//				comparatorRejMETEO.setColumn(PlayerComparatorRejMETEO.ID);
//				comparatorRejMETEO.reverseDirection();
//				fillTable(table);
//			}
//		});
//
//		final TableColumn SEQ_ID = new TableColumn(table,SWT.LEFT);
//		SEQ_ID.setText("CID"); SEQ_ID.setToolTipText("Sequence Id");
//		SEQ_ID.setWidth(55);
//		SEQ_ID.setResizable(false);
//		SEQ_ID.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent event) {
//		    	comparatorRejMETEO.setColumn(PlayerComparatorRejMETEO.SEQ_ID);
//		        comparatorRejMETEO.reverseDirection();
//		        fillTable(table);
//			}
//		});
//
//		final TableColumn SEQ_NUM = new TableColumn(table,SWT.LEFT);
//		SEQ_NUM.setText("Seq"); SEQ_NUM.setToolTipText("Sequence Number");
//		SEQ_NUM.setWidth(55);
//		SEQ_NUM.setResizable(false);
//		SEQ_NUM.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent event) {
//		    	comparatorRejMETEO.setColumn(PlayerComparatorRejMETEO.SEQ_NR);
//		        comparatorRejMETEO.reverseDirection();
//		        fillTable(table);
//			}
//		});
//		
//		final TableColumn FILING = new TableColumn(table,SWT.LEFT);
//		FILING.setText("Filing"); FILING.setToolTipText("Filing Time");
//		FILING.setWidth(80);
//		FILING.setResizable(false);
//		FILING.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent event) {
//		    	comparatorRejMETEO.setColumn(PlayerComparatorRejMETEO.SEND_AT);
//		        comparatorRejMETEO.reverseDirection();
//		        fillTable(table);
//			}
//		});
//		
//		final TableColumn ORIG = new TableColumn(table,SWT.LEFT);
//		ORIG.setText("Originator"); ORIG.setToolTipText("Originator");
//		ORIG.setWidth(100);
//		ORIG.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent event) {
//		    	comparatorRejMETEO.setColumn(PlayerComparatorRejMETEO.ORIGINATOR);
//		        comparatorRejMETEO.reverseDirection();
//		        fillTable(table);
//			}
//		});
//
//		final TableColumn MSG = new TableColumn(table,SWT.LEFT);
//		MSG.setText("Message Text"); MSG.setToolTipText("Origin Message");
//		MSG.setWidth(300);
//		MSG.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent event) {
//		    	comparatorRejMETEO.setColumn(PlayerComparatorRejMETEO.MESSAGE);
//		        comparatorRejMETEO.reverseDirection();
//		        fillTable(table);
//			}
//		});
//
//		final TableColumn DATETIME = new TableColumn(table,SWT.LEFT);
//		DATETIME.setText("Date/Time"); DATETIME.setToolTipText("Date/Time");
//		DATETIME.setWidth(110);
//		DATETIME.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent event) {
//		    	comparatorRejMETEO.setColumn(PlayerComparatorRejMETEO.TGL);
//		        comparatorRejMETEO.reverseDirection();
//		        fillTable(table);
//			}
//		});
//
//		final TableColumn TBL_NAME = new TableColumn(table,SWT.LEFT);
//		TBL_NAME.setText("TBL NAME"); TBL_NAME.setToolTipText("TBL NAME");
//		TBL_NAME.setWidth(0);
//		TBL_NAME.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent event) {
//		    	comparatorRejMETEO.setColumn(PlayerComparatorRejMETEO.TBL_NAME);
//		        comparatorRejMETEO.reverseDirection();
//		        fillTable(table);
//			}
//		});
//	    
//		// Do the initial fill of the table
//	    fillTable(table);
//	    
//	    Listener sortListener = new Listener() {
//	    	public void handleEvent(Event e) {
//	    		// determine new sort column and direction
//	        	TableColumn sortColumn = table.getSortColumn();
//	        	TableColumn currentColumn = (TableColumn) e.widget;
//	        	dir = table.getSortDirection();
//	        	
//	        	if (sortColumn == currentColumn) {
//	        		dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
//	        	} else {
//	        		table.setSortColumn(currentColumn);
//		            dir = SWT.UP;
//	        	}
//	    	}
//	    };
//	      
//	    ID.addListener(SWT.Selection, sortListener);
//	    SEQ_ID.addListener(SWT.Selection, sortListener);
//	    SEQ_NUM.addListener(SWT.Selection, sortListener);
//	    FILING.addListener(SWT.Selection, sortListener);
//	    ORIG.addListener(SWT.Selection, sortListener);
//	    MSG.addListener(SWT.Selection, sortListener);
//	    DATETIME.addListener(SWT.Selection, sortListener);
//	    TBL_NAME.addListener(SWT.Selection, sortListener);
//	    
//	    table.setSortColumn(ID);
//	    table.setSortDirection(SWT.UP);
//	      
//	    // To get ID of the row
//	    table.addListener(SWT.Selection, new Listener() {
//			public void handleEvent(Event e) {
//				string = "";
//				selection = table.getSelection();
//	          	//20101110
//				id_rjt_msg="";
//				tblName="";
//				tglReject="";
//				for (int i=0; i<selection.length; i++) {
//					tblName = selection[i].getText(table.getColumnCount()-1);
//					id_rjt_msg = selection[i].getText(0);
//					tglReject = selection[i].getText(6);
//					string += selection [i] + " ";
//				}
//
//				if (string.contains("TableItem {")) {
//					string = string.replace("TableItem {", "");
//					if (string.contains("}")) string = string.replaceAll("} ", "");
//					sqlSelect="SELECT * FROM "+tblName+" WHERE id_rjt_msg='"+id_rjt_msg+"'"; //untuk fungsi View,Edit
//				}
//			}
//		});
//	    
//	    final Group group = new Group(Group, SWT.NONE);
//		group.setLayout(new GridLayout(2, false));
//		
//		Composite type = new Composite(group, SWT.NONE);
//		Button Edit = new Button(type,SWT.PUSH );
//		if (XuxaActionBarAdvisor.getStorage().equals("backup")) { Edit.setEnabled(false); }
//		else {
//			if ((Login.getLevel().equals("ADMIN")) || (Login.getLevel().equals("L1 BO")) || (Login.getLevel().equals("L1 NOTAM"))) { Edit.setEnabled(true); }
//			if ((Login.getLevel().equals("L1 METEO")) || (Login.getLevel().equals("L2 BO")) || (Login.getLevel().equals("L2 NOTAM")) || (Login.getLevel().equals("L2 METEO"))) { Edit.setEnabled(false); }			
//		}
//		Edit.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				if (sqlSelect == null) { sqlSelect = ""; }
//				if (sqlSelect != "") {
//					jdbc.select2(GetSqlSelect(),tblName);
//					String id = string;
//					set(id);
//					
////					cPriority.setText(jdbc.getPriority());
//					String pridb=jdbc.getPriority();
//					int i=0;
//					for (i=0;i<priority.length;i++) {
//						if (pridb.compareTo(priority[i])==0) {break;}
//					}
//					initPrio=0;
//					cPriority.setSelection(i);
//					tDest1.setText(jdbc.getDes1());
//					tDest2.setText(jdbc.getDes2());
//					tDest3.setText(jdbc.getDes3());
//					tDest4.setText(jdbc.getDes4());
//					tDest5.setText(jdbc.getDes5());
//					tDest6.setText(jdbc.getDes6());
//					tDest7.setText(jdbc.getDes7());
//					
//					tDest8.setText(jdbc.getDes8());
//					tDest9.setText(jdbc.getDes9());
//					tDest10.setText(jdbc.getDes10());
//					tDest11.setText(jdbc.getDes11());
//					tDest12.setText(jdbc.getDes12());
//					tDest13.setText(jdbc.getDes13());
//					tDest14.setText(jdbc.getDes14());
//					
//					tDest15.setText(jdbc.getDes15());
//					tDest16.setText(jdbc.getDes16());
//					tDest17.setText(jdbc.getDes17());
//					tDest18.setText(jdbc.getDes18());
//					tDest19.setText(jdbc.getDes19());
//					tDest20.setText(jdbc.getDes20());
//					tDest21.setText(jdbc.getDes21());
//					
//					tFiling.setText(jdbc.getSendAt());
//					tOriginator.setText(jdbc.getOriginator());
//					tOri_Ref.setText(jdbc.getOriRef());
//					
//					String msg = jdbc.getTextMsg();
//					if (msg.contains("`")) msg = msg.replace("`", "'");
//					text.setText(msg);
//					tError.setText(jdbc.getErrorMsg());
//					tType.setText(jdbc.getTypeMsg());
//					
//					//pengosongan ID
//					sqlSelect=""; string="";
//				} else {
//					DialogFactory.openInfoDialog("Edit Message","At least one row must be selected !!");					
//				} 
//			}
//		});
//		
//		Button Delete = new Button(type,SWT.PUSH);
//		if (XuxaActionBarAdvisor.getStorage().equals("backup")) { Delete.setEnabled(false); }
//		else {
//			if ((Login.getLevel().equals("ADMIN")) || (Login.getLevel().equals("L1 BO")) || (Login.getLevel().equals("L1 NOTAM"))) { Delete.setEnabled(true); }
//			if ((Login.getLevel().equals("L1 METEO")) || (Login.getLevel().equals("L2 BO")) || (Login.getLevel().equals("L2 NOTAM")) || (Login.getLevel().equals("L2 METEO"))) { Delete.setEnabled(false); }			
//		}
//		Delete.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				//memilih baris yang akan di hapus -update 20101109
//				selection = table.getSelection(); 
//				if ((selection.length == 0) || (sqlSelect.equals(""))) {
//					DialogFactory.openInfoDialog("Delete Message","At least one row must be selected !!");
//				} else {
//					id_rjt_msg="";
//					tblName="";
//					
//					DialogFactory.openYesNoDialog("Delete Message", "Are you sure ?");
//					boolean tentu = DialogFactory.getPenentuan(); 
//					if (tentu == true) {
//						for (int i=0; i<selection.length; i++) { //banyak baris yang akan dihapus
//							tblName = selection[i].getText(table.getColumnCount()-1);
//							id_rjt_msg = selection[i].getText(0);
//							
//							String dele = "DELETE FROM "+tblName+" WHERE id_rjt_msg="+id_rjt_msg;
//							jdbc.delete(dele);
//						}
//						
//						if (getJumlah() > 1) {
//							string=""; sqlSelect="";
//							table.removeAll(); playersRejMETEO.clear();
//							FormSearchReject.refreshMETEO(pDate, pDateTo);
//						} else if (getJumlah() == 1) {
//							string=""; sqlSelect=""; shell.close();
//						}
//					} //end of if
//				}
//			}
//		});
//
//		Button DeleteAll = new Button(type,SWT.PUSH );
//		if (XuxaActionBarAdvisor.getStorage().equals("backup")) { DeleteAll.setEnabled(false); }
//		else {
//			if ((Login.getLevel().equals("ADMIN")) || (Login.getLevel().equals("L1 BO")) || (Login.getLevel().equals("L1 NOTAM"))) { DeleteAll.setEnabled(true); }
//			if ((Login.getLevel().equals("L1 METEO")) || (Login.getLevel().equals("L2 BO")) || (Login.getLevel().equals("L2 NOTAM")) || (Login.getLevel().equals("L2 METEO"))) { DeleteAll.setEnabled(false); }			
//		}
//		DeleteAll.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				try {
//					tblName="";
//				
//					DialogFactory.openYesNoDialog("Delete Message", "Are you sure ?");
//					boolean tentu = DialogFactory.getPenentuan(); 
//					if (tentu == true) {
//						jumlah=0; //inisialisasi
//						iCnt=0;
//						rowNo = 0;
//						
//						//menentukan tblName berdasarkan Date/Time
//						System.out.println("Date From::" + yearFr + "-" + monthFr + "*");
//						System.out.println("Date To::" + yearTo + "-" + monthTo + "*");
//						
//						conn1 = new Connection[Integer.parseInt(yearTo)*Integer.parseInt(monthTo)];
//						rs = new ResultSet[Integer.parseInt(yearTo)*Integer.parseInt(monthTo)];
//						
//						for (int iYear=Integer.parseInt(yearFr); iYear<=Integer.parseInt(yearTo); iYear++) {
//							tblName = "reject_message"+iYear;
//							
//							int iMtemp=0;
//							int n=0;
//							boolean flg=false;
//							if (iYear==Integer.parseInt(yearFr)) {
//								iMtemp=Integer.parseInt(monthFr);
//								n=12-iMtemp;
//							}
//							else if ((iYear>Integer.parseInt(yearFr)) && (iYear < Integer.parseInt(yearTo))) {
//								iMtemp=1;
//								n=12-iMtemp;
//							}
//							else if (iYear == Integer.parseInt(yearTo)) {
//								iMtemp= Integer.parseInt(monthTo);
//								n=iMtemp;
//								iMtemp=0;
//								flg=true;
//							}
//							
//							int nn=0;
//							for (int iMonth=iMtemp; iMonth<=n+iMtemp; iMonth++) {
//								
//								if ((flg) && (iMonth>=12)) { 
//									nn++; 
//									if ((n==iMtemp) && (nn>12)) break; 
//								} else nn=iMonth;
//								
//								String sMonth = Integer.toString(nn);//iMonth);
//								if (sMonth.length()==1) sMonth = sMonth.replace(sMonth, "0"+sMonth);
//								tblName = tblName.replace(tblName, "reject_message"+iYear+"_"+sMonth);
//
//								conn1[iCnt] = conn;
//								conn1[iCnt] = MenuItemSearchReject.gConn;
//								Statement stmt = conn1[iCnt].createStatement();
//								try { //biar kalo ada table yang doesn't exist ga mati.
//									lihatsql = select+tblName+where+tgl+tglTo+order;
//									rs[iCnt] = stmt.executeQuery(lihatsql);
//									if (lihatsql.contains("SELECT * FROM")) lihatsql = lihatsql.replace("SELECT * FROM", "DELETE FROM");
//									jdbc.delete(lihatsql);
//								} catch (SQLException s) { // biar kalo ada table yang doesn't exist ga mati.
//									System.out.println("Error: " + s.getMessage());
//								}
//								iCnt++; //counter rs
//							} //end of for2
//						} //end of for1
//						shell.close();
//					} //end of if
//				} catch (SQLException s) {
//					s.printStackTrace();
//        		} catch (java.lang.OutOfMemoryError hs) {
//        			hs.printStackTrace();
//        		}
//			}
//		});
//		bs.EDD(type, Edit, Delete, DeleteAll);
//		
//		Composite type2 = new Composite(group, SWT.NONE);
//		
//		Label hal = new Label(type2,SWT.LEFT);
//		hal.setText("Page:");
//		
//		yu = 0; qq = 0;
//		yu = qq+1;
//		isiHal = "";
//		isiHal = Integer.toString(yu);
//		
//		tPage = new Text(type2, SWT.BORDER);
//		tPage.setText(isiHal);
//
//		hal = new Label(type2, SWT.LEFT);
//		hal.setText(" of ");
//
//		int jml;
//		if (jumlah % baris == 0) {
//			jml = jumlah/baris;
//		} else {
//			jml = jumlah/baris + 1;
//		}
//
//		String jum = Integer.toString(jml);
//		tTotPage = new Text(type2, SWT.BORDER);
//		tTotPage.setText(jum);
//
//		first = new Button(type2, SWT.PUSH);
//		first.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				if (first.getEnabled() == true) {
//					first.setEnabled(false);
//					prev.setEnabled(false);
//					next.setEnabled(true);
//
//					if (last.getEnabled() == false) {
//						last.setEnabled(true);
//					}
//				}
//
//				table.removeAll();
//				qq = 0;
//				isiHal = Integer.toString(qq);
//				tPage.setText("1");
//				tGoToHal.setText("1");
//				Next(); discard();
//			}
//		});
//
//		prev = new Button(type2, SWT.PUSH);
//		prev.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				if (prev.getEnabled() == true) {
//					next.setEnabled(true);
//					first.setEnabled(true);
//					last.setEnabled(true);
//				}
//
//				qq--;
//				if (qq == 0) {
//					prev.setEnabled(false);
//					first.setEnabled(false);
//				}
//
//				if (qq < 0)
//					qq = 0;
//				else {
//					table.removeAll();
//					int as = Integer.parseInt(akhir().trim()) - 1;
//					isiHal = Integer.toString(as);
//					tPage.setText(isiHal);
//					Next(); discard();
//				}
//				tGoToHal.setText(isiHal);
//			}
//		});
//
//		next = new Button(type2, SWT.PUSH);
//		next.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				prev.setEnabled(true);
//				first.setEnabled(true);
//
//				qq++;
//
//				// ----
//				int jml;
//				if (jumlah % baris == 0) {
//					jml = jumlah/baris;
//				} else {
//					jml = jumlah/baris + 1;
//				}
//				// -----
//
//				int all = jml;
//
//				if ((all - 1) == qq) {
//					next.setEnabled(false);
//					last.setEnabled(false);
//				}
//
//				if (qq < jml) {
//					table.removeAll();
//					int as = qq + 1;
//					isiHal = Integer.toString(as);
//					tPage.setText(isiHal);
//					Next(); discard();
//				}
//				tGoToHal.setText(isiHal);
//			}
//		});
//
//		last = new Button(type2, SWT.PUSH);
//		last.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				next.setEnabled(false);
//				prev.setEnabled(true);
//				first.setEnabled(true);
//
//				table.removeAll();
//				if (jumlah % baris == 0)
//					qq = jumlah/baris - 1;
//				else
//					qq = jumlah/baris;
//
//				isiHal = Integer.toString(qq + 1);
//				tPage.setText(isiHal);
//				Next(); discard();
//				last.setEnabled(false);
//
//				tGoToHal.setText(isiHal);
//			}
//		});
//
//		hal = new Label(type2,SWT.LEFT);
//		hal.setText("  Go to: ");
//		
//		tGoToHal = new Text(type2, SWT.BORDER);
//		
//		final int hasil;
//        if (jumlah % baris == 0) { hasil = jumlah/baris; }
//        else { hasil = jumlah/baris+1; }
//        
//		go = new Button(type2,SWT.PUSH);
//		go.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				String sGoToHal = tGoToHal.getText();
//                if ((tGoToHal.getText().equals("")) || (tGoToHal.getText().equals("0"))) {
//                    sGoToHal = "1";
//                    tGoToHal.setText("1");
//                }
//                
//                int m = Integer.parseInt(sGoToHal);
//                int nHasil = hasil-1;
//                m-=1;
//
//                if (m == 0) {
//                	first.setEnabled(false);
//                	prev.setEnabled(false);
//                    next.setEnabled(true);
//                    last.setEnabled(true);
//                }
//                if (m == nHasil) {
//                	first.setEnabled(true);
//                	prev.setEnabled(true);
//                	next.setEnabled(false);
//                    last.setEnabled(false);
//                }
//                if ((m != 0) && (m != nHasil)) {
//                    first.setEnabled(true);
//                    prev.setEnabled(true);
//                    next.setEnabled(true);                        
//                    last.setEnabled(true);
//                }
//                if (m > nHasil) {
//                    m = nHasil;
//                    first.setEnabled(true);
//                	prev.setEnabled(true);
//                	next.setEnabled(false);
//                    last.setEnabled(false);
//                    tGoToHal.setText(Integer.toString(hasil));
//                }
//
//                m = m-1;
//                qq = m;
//                qq++;
//
//                table.removeAll();
//                int as=qq+1;
//                isiHal=Integer.toString(as);
//                tPage.setText(isiHal);
//                Next(); discard();
//			}
//		});
//
//		if (hasil == 1) {
//        	first.setEnabled(false);
//        	prev.setEnabled(false);
//            next.setEnabled(false);
//            last.setEnabled(false);
//            
//            tGoToHal.setEnabled(false);
//            go.setEnabled(false);
//        } 
//		
//		if (jumlah <= baris) {
//			first.setEnabled(false);
//			prev.setEnabled(false);
//			next.setEnabled(false);
//			last.setEnabled(false);
//			
//			tGoToHal.setEnabled(false);
//            go.setEnabled(false);
//		}
//		bs.Navigation(type2, tPage, tTotPage, first, prev, next, last, tGoToHal, go);
//		createForm(composite);
//  	}
//  	
//  	void locText(Text text) {
//  		GridData gd = new GridData();
//		gd.widthHint = 75;
//		text.setTextLimit(8);
//		text.setLayoutData(gd);
//		TextSetting.letter(text);
//  	}
//  	
//  	void createForm(Composite composite) {
//		crit.tgl();
//		
//		final Group Group = new Group(composite,SWT.NONE);
//		Group.setText("Edit Rejected Meteo Message");
//		Group.setLayout(new GridLayout(1,false));
//
//		final Group GroupHeader = new Group(Group,SWT.NONE);
//		GroupHeader.setLayout(new GridLayout(1,false));
//
//		Composite comp = new Composite(GroupHeader, SWT.NONE);
//		GridLayout layout = new GridLayout();
//		layout.numColumns = 7;
//		comp.setLayout(layout);
//		GridData gd = new GridData();
//		comp.setLayoutData(gd);
//		
//		Label label = new Label(comp,SWT.NONE);
//		label.setText("Priority");
//		label.setFont(FontAction.getBiggerBoldFont(shell));
//		label.setForeground(new Color (label.getShell().getDisplay(),0,64,255));
//		GridData gdP = new GridData();
//		gdP.verticalAlignment = SWT.TOP;
//		gdP.horizontalAlignment = SWT.LEFT;
//		label.setLayoutData(gdP);
//		label = new Label(comp,SWT.NONE);
//		label.setText(":");
//		label.setFont(FontAction.getBiggerBoldFont(shell));
//		label.setForeground(new Color (label.getShell().getDisplay(),0,64,255));
//		GridData gdP1 = new GridData();
//		gdP1.verticalAlignment = SWT.TOP;
//		gdP1.horizontalAlignment = SWT.LEFT;
//		label.setLayoutData(gdP1);
////		cPriority = new Combo(comp,SWT.READ_ONLY);
////		cPriority.setItems(HeaderComposite.PriorityMETEO);
////		GridData gpc = new GridData();
////		gpc.verticalAlignment = SWT.TOP;
////		gpc.horizontalAlignment = SWT.LEFT;
////		cPriority.setLayoutData(gpc);
////		cPriority.select(1);	// Equal to FF in AFTN, default-nya
//		cPriority = new List(comp, SWT.BORDER|SWT.V_SCROLL);
//		GridData gdcPriority = new GridData();
//		gdcPriority.horizontalSpan = 1;
//		gdcPriority.widthHint = 60;
//		gdcPriority.heightHint=20;
//		gdcPriority.verticalAlignment = SWT.TOP;
//		cPriority.setFont(FontAction.getSmallerFont(shell));
//		cPriority.setLayoutData(gdcPriority);
//		cPriority.setItems(this.priority);
//		//cPriority.setVisibleItemCount(6);
//		cPriority.setToolTipText("Message priority.");
//		sbPrio = cPriority.getVerticalBar();
//		lsbiPrio=0;sbiPrio=0;initPrio=1;
//		sbPrio.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				if (initPrio==1) {
//					if (lsbiPrio>sbPrio.getSelection()) {if (sbiPrio>0) sbiPrio--;cPriority.select(sbiPrio);/*System.out.println("up");*/}
//					else if (lsbiPrio<sbPrio.getSelection()) {if (sbiPrio<cPriority.getItemCount()-1) sbiPrio++;cPriority.select(sbiPrio);/*System.out.println("down");*/}
//					cPriority.showSelection();
//				}
//				else {lsbiPrio=1+(cPriority.getSelectionIndex()*26);sbiPrio=cPriority.getSelectionIndex();}
//				lsbiPrio=sbPrio.getSelection();
//				initPrio=1;
//			}
//		});
//		label = new Label(comp,SWT.NONE);
//		
//		label = new Label(comp,SWT.NONE);
//		label.setText("\tAddress");
//		label.setFont(FontAction.getBiggerBoldFont(shell));
//		label.setForeground(new Color (label.getShell().getDisplay(),0,64,255));
//		GridData gadd = new GridData();
//		gadd.verticalAlignment = SWT.TOP;
//		gadd.horizontalAlignment = SWT.LEFT;
//		label.setLayoutData(gadd);
//		label = new Label(comp,SWT.NONE);
//		label.setText(":");
//		label.setFont(FontAction.getBiggerBoldFont(shell));
//		label.setForeground(new Color (label.getShell().getDisplay(),0,64,255));
//		GridData gadd1 = new GridData();
//		gadd1.verticalAlignment = SWT.TOP;
//		gadd1.horizontalAlignment = SWT.LEFT;
//		label.setLayoutData(gadd1);
//		Composite compAdd = new Composite(comp, SWT.NONE);
//		GridLayout LcompAdd = new GridLayout();
//		LcompAdd.numColumns = 7;
//		compAdd.setLayout(LcompAdd);
//		GridData gdcompAdd = new GridData();
//		compAdd.setLayoutData(gdcompAdd);
//		//percobaan
//		tDest1 = new Text(compAdd,SWT.BORDER); tDest1.setFocus(); locText(tDest1);
//		tDest2 = new Text(compAdd,SWT.BORDER); locText(tDest2);
//		tDest3 = new Text(compAdd,SWT.BORDER); locText(tDest3);
//		tDest4 = new Text(compAdd,SWT.BORDER); locText(tDest4);
//		tDest5 = new Text(compAdd,SWT.BORDER); locText(tDest5);
//		tDest6 = new Text(compAdd,SWT.BORDER); locText(tDest6);
//		tDest7 = new Text(compAdd,SWT.BORDER); locText(tDest7);
//		//--------------------------------------
//		tDest8 = new Text(compAdd,SWT.BORDER); locText(tDest8);
//		tDest9 = new Text(compAdd,SWT.BORDER); locText(tDest9);
//		tDest10 = new Text(compAdd,SWT.BORDER); locText(tDest10);
//		tDest11 = new Text(compAdd,SWT.BORDER); locText(tDest11);
//		tDest12 = new Text(compAdd,SWT.BORDER); locText(tDest12);
//		tDest13 = new Text(compAdd,SWT.BORDER); locText(tDest13);
//		tDest14 = new Text(compAdd,SWT.BORDER); locText(tDest14);
//		//--------------------------------------
//		tDest15 = new Text(compAdd,SWT.BORDER); locText(tDest15);
//		tDest16 = new Text(compAdd,SWT.BORDER); locText(tDest16);
//		tDest17 = new Text(compAdd,SWT.BORDER); locText(tDest17);
//		tDest18 = new Text(compAdd,SWT.BORDER); locText(tDest18);
//		tDest19 = new Text(compAdd,SWT.BORDER); locText(tDest19);
//		tDest20 = new Text(compAdd,SWT.BORDER); locText(tDest20);
//		tDest21 = new Text(compAdd,SWT.BORDER); locText(tDest21);
//		//--
//		label = new Label(comp,SWT.NONE);
//		label.setText("Filing Time");
//		label.setFont(FontAction.getBiggerBoldFont(shell));
//		label = new Label(comp,SWT.NONE);
//		label.setText(":");
//		label.setFont(FontAction.getBiggerBoldFont(shell));
//		tFiling = new Text(comp,SWT.BORDER);
//		GridData gd1 = new GridData();
//		gd1.widthHint = 50;
//		tFiling.setTextLimit(6);
//		tFiling.setLayoutData(gd1);
//		tFiling.setText(crit.filing_time);
//		final Calendar caltFiling = Calendar.getInstance();
//		tFiling.addListener(SWT.Verify, new Listener() {
//			boolean ignore;
//			public void handleEvent(Event e) {
//				if (ignore) return;
//				e.doit = false;
//				StringBuffer buffer = new StringBuffer(e.text);
//				char[] chars = new char[buffer.length()];
//				buffer.getChars(0, chars.length, chars, 0);
//				if (e.character == '\b') {
//					for (int i = e.start; i < e.end; i++) {
//						switch (i) {
//							case 0: { buffer.append(ftA()); break; } //* [D]D *
//							case 1: { buffer.append(ftB()); break; } //* D[D] *
//							
//							case 2: { buffer.append(ftC()); break; } //* [H]H *
//							case 3: { buffer.append(ftD()); break; } //* H[H] *
//							
//							case 4: { buffer.append(ftE()); break; } //* [M]M * 
//							case 5: { buffer.append(ftF()); break; } //* M[M] *
//							
//							default:
//								return;
//						}
//					}
//					tFiling.setSelection(e.start, e.start + buffer.length());
//					ignore = true;
//					tFiling.insert(buffer.toString());
//					ignore = false;
//					tFiling.setSelection(e.start, e.start);
//					return;
//				}
//			
//				int start = e.start;
//				if (start > 10) return;
//				int index = 0;
//				for (int i = 0; i < chars.length; i++) {
//					if (chars[i] < '0' || '9' < chars[i]) return;
//					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
//					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
//					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
//					index++;
//				}
//				
//				String newText = buffer.toString();
//				int length = newText.length();
//				StringBuffer date = new StringBuffer(tFiling.getText());
//				date.replace(e.start, e.start + length, newText);
//				
//				caltFiling.set(Calendar.YEAR, 1901);
//				caltFiling.set(Calendar.MONTH, Calendar.JANUARY);
//				caltFiling.set(Calendar.DATE, 1);
//				caltFiling.set(Calendar.HOUR_OF_DAY, 00);
//				caltFiling.set(Calendar.MINUTE, 00);
//				
//				String dd = date.substring(0,2); 
//				if (dd.indexOf('0') == -1) {
//					int day = Integer.parseInt(dd);
//					int maxDay = caltFiling.getActualMaximum(Calendar.DATE);
//					if (1 > day || day > maxDay) return;
//					caltFiling.set(Calendar.DATE, day);
//				} 
//				String hh = date.substring(2,4); 
//				if (hh.indexOf('0') == -1) {
//					int hour = Integer.parseInt(hh);
//					int maxHour = caltFiling.getActualMaximum(Calendar.HOUR_OF_DAY);
//					if (0 > hour || hour > maxHour) return;
//					caltFiling.set(Calendar.HOUR_OF_DAY, hour);
//				}
//				String mm = date.substring(4,6); 
//				if (mm.indexOf('0') == -1) {
//					int minute = Integer.parseInt(mm);
//					int maxMinute = caltFiling.getActualMaximum(Calendar.MINUTE);
//					if (0 > minute || minute > maxMinute) return;
//					caltFiling.set(Calendar.MINUTE, minute);
//				}
//				tFiling.setSelection(e.start, e.start + length);
//				ignore = true;
//				tFiling.insert(newText);
//				ignore = false;
//			}
//		});
//		label = new Label(comp,SWT.NONE);
//		label.setText(" (DDhhmm)");
//		label.setFont(FontAction.getSmallerItalicFont(shell));
//		
//		label = new Label(comp,SWT.NONE);
//		label.setText("\tOriginator");
//		label.setFont(FontAction.getBiggerBoldFont(shell));
//		label.setForeground(new Color (label.getShell().getDisplay(),0,64,255));
//		label = new Label(comp,SWT.NONE);
//		label.setText(":");
//		label.setFont(FontAction.getBiggerBoldFont(shell));
//		label.setForeground(new Color (label.getShell().getDisplay(),0,64,255));
//		Composite compOri = new Composite(comp, SWT.NONE);
//		GridLayout LcompOri = new GridLayout();
//		LcompOri.numColumns = 3;
//		compOri.setLayout(LcompOri);
//		GridData gdcompOri = new GridData();
//		compOri.setLayoutData(gdcompOri);
//		tOriginator = new Text(compOri,SWT.BORDER);
//		GridData gd2 = new GridData();
//		gd2.widthHint = 75;
//		tOriginator.setTextLimit(8);
//		tOriginator.setLayoutData(gd2);
//		TextSetting.letter(tOriginator);
//		
//		label = new Label(compOri,SWT.NONE);
//		label.setText("Originator's Reference : ");
//		label.setFont(FontAction.getBiggerBoldFont(shell));
//		tOri_Ref = new Text(compOri,SWT.BORDER);
//		GridData gd3 = new GridData();
//		gd3.widthHint = 350;
//		tOri_Ref.setTextLimit(61);
//		tOri_Ref.setLayoutData(gd3);
//		TextSetting.upper(tOri_Ref);
//		//Form Edit
//		Composite compEdit = new Composite(Group, SWT.NONE);
//		GridLayout LcompEdit = new GridLayout();
//		LcompEdit.numColumns = 3;
//		compEdit.setLayout(LcompEdit);
//		GridData gdcompEdit = new GridData();
//		compEdit.setLayoutData(gdcompEdit);
//		
//		label = new Label(compEdit,SWT.NONE);
//		label.setText("TEXT");
//		label.setFont(FontAction.getSmallerBoldFont(shell));
//		GridData gdT = new GridData();
//		gdT.verticalAlignment = SWT.TOP;
//		label.setLayoutData(gdT);
//		label.setForeground(new Color (label.getShell().getDisplay(),0,64,255));
//		label = new Label(compEdit,SWT.NONE);
//		label.setText(":");
//		label.setFont(FontAction.getSmallerBoldFont(shell));
//		GridData gdT1 = new GridData();
//		gdT1.verticalAlignment = SWT.TOP;
//		label.setLayoutData(gdT1);
//		label.setForeground(new Color (label.getShell().getDisplay(),0,64,255));
//		text = new Text(compEdit, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
//		GridData gdText = new GridData();
//		gdText.widthHint = 500;
//		gdText.heightHint = 100;
//		text.setFont(FontAction.getSmallerFont(shell));
//		text.setLayoutData(gdText);
//		TextSetting.upper(text);
//		
//		label = new Label(compEdit,SWT.NONE);
//		label.setText("ERROR MESSAGE");
//		label.setFont(FontAction.getSmallerFont(shell));
//		GridData gdE = new GridData();
//		gdE.verticalAlignment = SWT.TOP;
//		label.setLayoutData(gdE);
//		label = new Label(compEdit,SWT.NONE);
//		label.setText(":");
//		label.setFont(FontAction.getSmallerBoldFont(shell));
//		GridData gdE1 = new GridData();
//		gdE1.verticalAlignment = SWT.TOP;
//		label.setLayoutData(gdE1);
//		tError = new Text(compEdit, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
//		GridData gdError = new GridData();
//		gdError.widthHint = 500;
//		gdError.heightHint = 40;
//		tError.setEditable(false);
//		tError.setFont(FontAction.getSmallerFont(shell));
//		tError.setLayoutData(gdError);
//
//		label = new Label(compEdit,SWT.NONE);
//		label.setText("TYPE OF MESSAGE");
//		label.setFont(FontAction.getSmallerFont(shell));
//		label = new Label(compEdit,SWT.NONE);
//		label.setText(":");
//		label.setFont(FontAction.getSmallerBoldFont(shell));
//		tType = new Text(compEdit, SWT.BORDER);
//		GridData gdType = new GridData();
//		gdType.widthHint = 200;
//		tType.setEditable(false);
//		tType.setFont(FontAction.getSmallerFont(shell));
//		tType.setLayoutData(gdType);
//		
//		// Button
//	    Composite type = new Composite(Group, SWT.NONE);
//		Button Send = new Button(type,SWT.PUSH);
//		if (XuxaActionBarAdvisor.getStorage().equals("backup")) { Send.setEnabled(false); }
//		else {
//			if ((Login.getLevel().equals("ADMIN")) || (Login.getLevel().equals("L1 BO")) || (Login.getLevel().equals("L1 NOTAM"))) { Send.setEnabled(true); }
//			if ((Login.getLevel().equals("L1 METEO")) || (Login.getLevel().equals("L2 BO")) || (Login.getLevel().equals("L2 NOTAM")) || (Login.getLevel().equals("L2 METEO"))) { Send.setEnabled(false); }			
//		}
//		Send.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				String spriorityCombo="",shCombo="",sm2Combo="";
//				if (cPriority.getSelectionCount()>0) spriorityCombo = cPriority.getSelection()[0];
//				
//				if (sett().equals("")) {
//					DialogFactory.openInfoDialog("Send Message","At least one row must be selected if you want to send your message !!");
//				} else {
//					if (text.getText().equals("")) {
//						DialogFactory.openInfoDialog("Send Message","The value in field TEXT is required."); text.setFocus();
//					} else {
//						crit.tgl();
//						//UPDATE KE reject_message
//						String msg = text.getText();
//						if (msg.contains("'")) msg = msg.replace("'", "`");
//						
//						String priority = "";
//						if (cPriority.getSelectionCount()>0) priority=cPriority.getSelection()[0];
//						if (priority.equals("")) { priority = "FF"; } 
//						
//						String update = "UPDATE "+tblName+" SET priority='"+priority///cPriority.getText()
//						+"',destination1='"+tDest1.getText()+"',destination2='"+tDest2.getText()+"',destination3='"+tDest3.getText()+"',destination4='"+tDest4.getText()+"',destination5='"+tDest5.getText()+"',destination6='"+tDest6.getText()+"',destination7='"+tDest7.getText()
//						+"',destination8='"+tDest8.getText()+"',destination9='"+tDest9.getText()+"',destination10='"+tDest10.getText()+"',destination11='"+tDest11.getText()+"',destination12='"+tDest12.getText()+"',destination13='"+tDest13.getText()+"',destination14='"+tDest14.getText()
//						+"',destination15='"+tDest15.getText()+"',destination16='"+tDest16.getText()+"',destination17='"+tDest17.getText()+"',destination18='"+tDest18.getText()+"',destination19='"+tDest19.getText()+"',destination20='"+tDest20.getText()+"',destination21='"+tDest21.getText()
//						+"',filing_time='"+tFiling.getText()+"',originator='"+tOriginator.getText()+"',ori_ref='"+tOri_Ref.getText()
//						+"',origin_message='"+msg+"',tgl='"+crit.tanggal+"' WHERE id_rjt_msg='"+sett()+"'";
//						jdbc.update(update);
//						
//						//MASUK KE check_status
//						String message = text.getText().toString();
//						if (message.length() >= 69) { message = message.substring(0,69); }
//						
//						String insert = "INSERT INTO check_status (id,tbl_name,filing_time,status,jam,flag,tgl,aircraft_id,type,series,originator,message) VALUES ('"+sett()+"','"+tblName/*"reject_message"*/+"','"+crit.filing_time+"','"+"waiting"+"','"+crit.filing_time+"','"+"0"+"','"+/*tglReject/**/crit.tanggal/**/+"','','REJECT METEO','','"+tOriginator.getText()+"','"+message+"')";
//						jdbc.connect(insert);
//						TriggerAction.setUpNetworking();
//						discard(); string2=""; string=""; sqlSelect="";
//
//						//buat ng'refreshnya
//						table.removeAll(); playersRejMETEO.clear(); shell.close();
//						FormSearchReject.refreshMETEO(pDate, pDateTo);
//					}
//				}
//			}
//		});
//
//		Button Clear = new Button(type,SWT.PUSH );
//		Clear.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				discard();
//			}
//		});
//		bs.SendClear(type, Send, Clear);
//	}
//  	
//  	void discard() {
//		if (text.getText() != "") text.setText("");
//		if (tError.getText() != "") tError.setText("");
//		if (tType.getText() != "") tType.setText("");
//	}
//  	
//  	void fillTable(Table table) {
//	    // Turn off drawing to avoid flicker
//	    table.setRedraw(false);
//        
//	    // We remove all the table entries, sort our rows, then add the entries
//	    table.removeAll();
//	    
//	    Collections.sort(playersRejMETEO, comparatorRejMETEO);
//	    for (Iterator itr = playersRejMETEO.iterator(); itr.hasNext();) {
//	    	PlayerRejMETEO player = (PlayerRejMETEO) itr.next();
//	    	TableItem item = new TableItem(table, SWT.NONE);
//	    	int c = 0;
//	    	item.setText(c++, player.getID());
//	    	item.setText(c++, player.getSEQ_ID());
//	    	item.setText(c++, player.getSEQ_NR());
//	    	item.setText(c++, player.getSEND_AT());
//	    	item.setText(c++, player.getORIGINATOR());
//	    	item.setText(c++, player.getMESSAGE());
//	    	item.setText(c++, player.getTGL());
//	    	item.setText(c++, player.getTBL_NAME());
//	    }
//	    
//	    // sort indicator
//	    table.setSortDirection(dir);
//        //table.clearAll();
//        
//	    // Turn drawing back on
//	    table.setRedraw(true);
//  	}
//
//  	private static void set(String string) { string2 = string; }
//  	public static String sett() { return string2; }
//  	public static String akhir() { return isiHal; }
//	public static String GetSqlSelect() { return sqlSelect; }
//	public static String GetString() { return string; }
//	public static int getJumlah() { return jumlah; }
//	
//	// FILING TIME
//	private static String ftA() { if (fta == null) { fta = "0"; } return fta; }
//	private static String ftB() { if (ftb == null) { ftb = "0"; } return ftb; }
//	private static String ftC() { if (ftc == null) { ftc = "0"; } return ftc; }
//	private static String ftD() { if (ftd == null) { ftd = "0"; } return ftd; }
//	private static String ftE() { if (fte == null) { fte = "0"; } return fte; }
//	private static String ftF() { if (ftf == null) { ftf = "0"; } return ftf; }
}

/**
 * This class represents a player.
 */
class PlayerRejMETEO {
	private String ID;
	private String SEQ_ID;
	private String SEQ_NR;
	private String SEND_AT;
	private String ORIGINATOR;
	private String MESSAGE;
	private String TGL;
	private String TBL_NAME;
	
	public PlayerRejMETEO(String ID, String SEQ_ID, String SEQ_NR, String SEND_AT, String ORIGINATOR, String MESSAGE, String TGL, String TBL_NAME) {
		this.ID = ID;
		this.SEQ_ID = SEQ_ID;
		this.SEQ_NR = SEQ_NR;
		this.SEND_AT = SEND_AT;
		this.ORIGINATOR = ORIGINATOR;
		this.MESSAGE = MESSAGE;
		this.TGL = TGL;
		this.TBL_NAME = TBL_NAME;
	}

	public String getID() { return ID; }
	public String getSEQ_ID() { return SEQ_ID; }
	public String getSEQ_NR() { return SEQ_NR; }
	public String getSEND_AT() { return SEND_AT; }
	public String getORIGINATOR() { return ORIGINATOR; }
	public String getMESSAGE() { return MESSAGE; }
	public String getTGL() { return TGL; }
	public String getTBL_NAME() { return TBL_NAME; }  
}

class PlayerComparatorRejMETEO implements Comparator {
	public static final int ID = 0;
	public static final int SEQ_ID = 1;
	public static final int SEQ_NR = 2;
	public static final int SEND_AT = 3;
	public static final int ORIGINATOR = 4;
	public static final int MESSAGE = 5;
	public static final int TGL = 6;
	public static final int TBL_NAME = 7;
	
	public static final int ASCENDING = 0;
	public static final int DESCENDING = 1;
	
	private int column;
	private int direction;
	
	/**
	 * Compares two Player objects
	 * 
	 * @param obj1 the first Player
	 * @param obj2 the second Player
	 * @return int
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	
	public int compare(Object obj1, Object obj2) {
		int rc = 0;
	    PlayerRejMETEO p1 = (PlayerRejMETEO) obj1;
	    PlayerRejMETEO p2 = (PlayerRejMETEO) obj2;
	
	    // Determine which field to sort on, then sort on that field
	    switch (column) {
		    case ID:
		    	rc = p1.getID().compareTo(p2.getID());
		    	break;
		    case SEQ_ID:
		    	rc = p1.getSEQ_ID().compareTo(p2.getSEQ_ID());
		    	break;
		    case SEQ_NR:
		    	rc = p1.getSEQ_NR().compareTo(p2.getSEQ_NR());
		    	break;
		    case SEND_AT:
		    	rc = p1.getSEND_AT().compareTo(p2.getSEND_AT());
		    	break;
		    case ORIGINATOR:
		    	rc = p1.getORIGINATOR().compareTo(p2.getORIGINATOR());
		    	break;
		    case MESSAGE:
		    	rc = p1.getMESSAGE().compareTo(p2.getMESSAGE());
		    	break;
		    case TGL:
		    	rc = p1.getTGL().compareTo(p2.getTGL());
		    	break;
		    case TBL_NAME:
		    	rc = p1.getTBL_NAME().compareTo(p2.getTBL_NAME());
		    	break;
	    }
	
	    // Check the direction for sort and flip the sign if appropriate
	    if (direction == ASCENDING) 
	    	rc = -rc;
	    return rc;
	}
	
	// Sets the column for sorting
	public void setColumn(int column) {
		this.column = column;
	}
	
	// Sets the direction for sorting
	public void setDirection(int direction) {
		this.direction = direction;
	}	
	
	// Reverses the direction
	public void reverseDirection() {
		direction = 1 - direction;
	}
}