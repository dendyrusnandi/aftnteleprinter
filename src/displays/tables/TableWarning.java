package displays.tables;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import readwrite.ReadFromFile;
import readwrite.WriteToPDF;
import setting.ButtonsSetting;
import setting.Colors;
import setting.ErrMsg;
import setting.Shells;
import setting.Shorten;
import setting.TextSetting;
import setting.Times;
import actions.RefreshTable;
import actions.TriggerAction;
import actions.ViewATSFunction;
import actions.cProgressBar;
import databases.jdbc;
import displays.DialogFactory;
import displays.MainForm;
import displays.TeleSplashScreen2016IP;


public class TableWarning {
	@SuppressWarnings("unchecked")
	private java.util.List playersWarning;
	private PlayerComparatorWarning comparatorWarning;
	
	Shorten sh = new Shorten();
	ButtonsSetting bs = new ButtonsSetting();
	TextSetting ts = new TextSetting();
	ErrMsg em = new ErrMsg();
	ViewATSFunction vATS = new ViewATSFunction();
	WriteToPDF writetopdf = new WriteToPDF();
	ReadFromFile rff = new ReadFromFile();
	Times tm = new Times();
	
//	public static ComposeWindow window = new ComposeWindow(true);
//	private static MenuItemNewATS newATS;
	
	static String id="",P1="",P2="",P3="";
	static String sqlSelect="",sqlSelect2="",string="",string2="",tanggal="",lihatsql="",sele="",isiHal="",jml_item="";
	String id_tbl="",pMsg="",pReason="",pDate="",pDateTo="";
	String yearMonth="",yearFr="",monthFr="",yearTo="",monthTo="",tgl="",tglTo="",dt="";
	//static 
	String filing="";;
	static String line="";
	
	int dir=0,p=0,yu=0,hasil=0;
	static int baris=20,qq=0,jumlah=0,rowNo=0,qu=0;

	Shell shell;
	Table table;
	TableItem[] selection;
	Button next,prev,first,last,Delete,DeleteAll,Pdf,Pdfs;
	Text tPage, tTotPage, tGoToHal;
	
	Connection conn = null; // connection object
    Statement stmt = null; // statement object
    ResultSet rs = null; // result set object
	
  	public TableWarning() {
  	}
  	
  	public static String getText() {
  		return "Incoming Warning Messages";
  	}

  	public void koneksiDB(Shell pshell, String sMsg, String sReason, String stgl, String stglto) {
  		shell=pshell;
  		pMsg = sMsg;
  		pReason = sReason;
  		pDate=stgl;
  		pDateTo=stglto;
  		
  		//------------------------------------ KONEKSI DB ------------------------------------
  		try { conn = jdbc.getDBConnection(); } 
		catch (Exception e) { e.printStackTrace(); }
		
		final cProgressBar progbr = new cProgressBar("Selecting data ...");
		new Thread() {
	        public void run() {
	        	if (TeleSplashScreen2016IP.display.isDisposed()) return;
	        	TeleSplashScreen2016IP.display.asyncExec(new Runnable() {
	        		@SuppressWarnings("unchecked")
					public void run() {
	        			try {
	        				progbr.create(baris);
						    // Create the comparatorWarning used for sorting
						    comparatorWarning = new PlayerComparatorWarning();
						    comparatorWarning.setColumn(PlayerComparatorWarning.TGL);
						    comparatorWarning.setDirection(PlayerComparatorWarning.ASCENDING);
						    
						    // Create the playersWarning
						    playersWarning = new ArrayList();
						    
						    String strMsg="",strReason="";
						    tm.tgl();
							System.out.println("\nDate Now::" + tm.tg);
							yearMonth = tm.tg.substring(0, 7);
							if (yearMonth.contains("-")) yearMonth = yearMonth.replace("-", "_");
						
							//tgl update20101119 [pencarian bisa sampai tahun, bulan, tanggal, menit, detik]
							if (pDate.compareTo("0000-00-00 00:00")!=0) { //tampilkan data dari Date/Time ini
								tm.Date(pDate);
								tgl = " AND tgl >= '"+tm.Date+"'"; 
							
								if (tm.Date.length()>=4) yearFr = tm.Date.substring(0, 4); else yearFr=yearMonth.substring(0, 4);
								if (tm.Date.length()>=7) monthFr = tm.Date.substring(5, 7); else monthFr="01";
								//Field Date/Time To tidak diisi. -update 20101206
								if (pDateTo.compareTo("0000-00-00 00:00") == 0) {
									yearTo=yearMonth.substring(0, 4);
									monthTo=yearMonth.substring(5, 7);
		
									String ddhhmm = tm.tanggal.substring(7, tm.tanggal.length());
									tm.DateTo(yearTo+"-"+monthTo+ddhhmm);
									tglTo = " AND tgl <= '"+tm.Date+"'";
								}
							} 
							if (pDateTo.compareTo("0000-00-00 00:00")!=0) { //tampilkan data sampai Date/Time ini
								tm.DateTo(pDateTo);
								tglTo = " AND tgl <= '"+tm.Date+"'";
								
								if (tm.Date.length()>=4) yearTo = tm.Date.substring(0, 4); else yearTo=yearMonth.substring(0, 4);
								if (tm.Date.length()>=7) monthTo = tm.Date.substring(5, 7); else monthTo="12"; 
								String yeartoo=yearTo;
								//Field Date/Time From tidak diisi. 
								if (pDate.compareTo("0000-00-00 00:00") == 0) {
									yearFr=yeartoo;//yearMonth.substring(0, 4);
									monthFr="01";//yearMonth.substring(5, 7);
									tgl = " AND tgl >= '"+yeartoo+"-01-01 00:00:00"/*tm.Date*/+"'";
								}
							}
							if (pDate.compareTo("0000-00-00 00:00") == 0 && pDateTo.compareTo("0000-00-00 00:00") == 0) { //tampilkan data hari ini
								tgl = " AND tgl >= '"+tm.tg+" 00:00:00'"; 
								tglTo = " AND tgl <= '"+tm.tg+" 23:59:59'";
								yearFr=yearTo=yearMonth.substring(0, 4);
								monthFr=monthTo=yearMonth.substring(5, 7);
							}
							if (!pMsg.isEmpty()) strMsg = " AND message LIKE '%"+pMsg+"%'"; else strMsg=""; 
						    if (!pReason.isEmpty()) strReason = " AND reason LIKE '%"+pReason+"%'"; else strReason="";
						    String cari = strMsg+strReason;
						    
							lihatsql = "SELECT * FROM warning WHERE tgl!='0000-00-00 00:00:00' "+cari;
							
							String tanggal=tgl+tglTo;
							if (tanggal.contains("1111-11-11 11:11")) {
								lihatsql+=" ORDER BY idcnt DESC,tgl DESC";
								tm.tgl();
								pDate=tm.YYYY+"-"+tm.MM+"-00 00:00";
								pDateTo="0000-00-00 00:00";
							} else {
								lihatsql+=tgl+tglTo+dt+" ORDER BY idcnt DESC,tgl DESC";
							}
							
//						    if (!pMsg.isEmpty()) strMsg = " AND message LIKE '%"+pMsg+"%'"; else strMsg=""; 
//						    if (!pReason.isEmpty()) strReason = " AND reason LIKE '%"+pReason+"%'"; else strReason="";
//						    String cari = strMsg+strReason;
//							lihatsql = "SELECT * FROM warning WHERE tgl!='0000-00-00 00:00:00' "+cari+" ORDER BY idcnt DESC,tgl DESC";
							System.out.println("\nQuery: " + lihatsql);
							stmt = conn.createStatement();
							rs = stmt.executeQuery(lihatsql);
							rs.last();
							jumlah = rs.getRow();
							rs.beforeFirst();
							
							final String datatabel[][] = new String[baris][4];
							p = 0;
							rowNo = 0;
							while (rs.next() && (rowNo < baris)) {
								rowNo++;
								datatabel[p][0] = rs.getString("idcnt"); id=datatabel[p][0]; if (id==null){ id=""; }
								datatabel[p][1] = rs.getString("message"); P1=datatabel[p][1]; if (P1==null){ P1=""; }
								datatabel[p][2] = rs.getString("reason"); P2=datatabel[p][2]; if (P2==null){ P2=""; }
								datatabel[p][3] = rs.getString("tgl"); P3=datatabel[p][3].substring(0,19); if (P3==null){ P3=""; }
								
								p++;
//								System.out.println(">>data::\n" + id + "\t" + P3 + "\t" + P1 + "#");
								playersWarning.add(new PlayerWarning(id,P1,P2,P3));
								progbr.bar.setSelection(rowNo);
							}
							progbr.finish();
							
//							if (getJumlah() != 0) {
								if (getJumlah()==0) DialogFactory.openInfoDialog(Shells.sh_tWarning, "Search Message","Data not found, your search has no result !!");
								formSearch();
							    formList();
							    sh.shellStyle(shell, getText());
							    shell.addListener(SWT.Close, new Listener() {
									public void handleEvent(Event event) {
										connClose();
									}
								});
//					  		} else {
//								DialogFactory.openInfoDialog(Shells.sh_tWarning, "Warning Messages","There's no data !!");
//							}
	        			} catch (SQLException s) {
	    					DialogFactory.openInfoDialog(Shells.sh_tWarning, "Warning Message", s.toString());
	    					s.printStackTrace();
	    					progbr.finish();
	    				} catch (java.lang.OutOfMemoryError hs) {
	    					hs.printStackTrace();
	    					DialogFactory.openInfoDialog(Shells.sh_tWarning, "Warning Message", "Out of memory !!");
	    					progbr.finish();
	    				}
	        		}
	        	});
	        }
		}.start();
		progbr.begin();
  	}
  	
  	void Next() {
  		final cProgressBar progbr = new cProgressBar("Selecting data ...");
		new Thread() {
	        public void run() {
	        	if (TeleSplashScreen2016IP.display.isDisposed()) return;
	        	TeleSplashScreen2016IP.display.asyncExec(new Runnable() {
	        		@SuppressWarnings("unchecked")
					public void run() {
	        			try {
	        				progbr.create(baris);
	        				try {
								rs.last();
								jumlah = rs.getRow();
								rs.beforeFirst();
								
								final String datatabel[][] = new String[baris][4];
								
								for (rowNo=0; rowNo<(qq*baris); rowNo++) rs.next();
								p = 0;
								rowNo = 0;
								playersWarning.clear();
								while (rs.next() && (rowNo < baris)) {
									rowNo++;
									
									rowNo++;
									datatabel[p][0] = rs.getString("idcnt"); id=datatabel[p][0]; if (id==null){ id=""; }
									datatabel[p][1] = rs.getString("message"); P1=datatabel[p][1]; if (P1==null){ P1=""; }
									datatabel[p][2] = rs.getString("reason"); P2=datatabel[p][2]; if (P2==null){ P2=""; }
									datatabel[p][3] = rs.getString("tgl"); P3=datatabel[p][3].substring(0,19); if (P3==null){ P3=""; }
									
									TableItem item = new TableItem (table, SWT.NONE);
									item.setText(0,id); 
									item.setText(1,P1);
									item.setText(2,P2); 
									item.setText(3,P3); 
									
									p++;
									playersWarning.add(new PlayerWarning(id,P1,P2,P3));
									progbr.bar.setSelection(rowNo);
								}
								progbr.finish();
							} catch (SQLException s) {
								DialogFactory.openInfoDialog(Shells.sh_tWarning, "Warning Message", s.toString());
								s.printStackTrace();
							}
	    				} catch (java.lang.OutOfMemoryError hs) {
	    					hs.printStackTrace();
	    					DialogFactory.openInfoDialog(Shells.sh_tWarning, "Warning Message", "Out of memory !!");
	    					progbr.finish();
	    				}
	        		}
	        	});
	        }
		}.start();
		progbr.begin();
	}

  	void formSearch() {
		int iSpace=10;
  		final Group grpSearch = new Group(shell, SWT.NONE); sh.groupStyle(grpSearch, 14, "Search "+getText());
  		Label label = new Label(grpSearch,SWT.NONE); sh.labelStyle1(label, "Message", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		final Text tSearchMsg = new Text(grpSearch,SWT.BORDER); sh.textStyle(tSearchMsg, 200, 20, SWT.LEFT, SWT.LEFT, sh.upper, "Search by Message", true); 
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle1(label, "Reason", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		final Text tReasonMsg = new Text(grpSearch,SWT.BORDER); sh.textStyle(tReasonMsg, 200, 20, SWT.LEFT, SWT.LEFT, sh.upper, "Search by Reason", true); 

		label = new Label(grpSearch,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle1(label, "From", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		final Text tFrom = new Text(grpSearch,SWT.BORDER); 
		
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle1(label, "To", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		final Text tTo = new Text(grpSearch,SWT.BORDER); 
		
//		sh.textStyle(tFrom, 120, 16, SWT.LEFT, SWT.LEFT, "", "Date/Time From (YYYY-MM-DD hh:mm)", true);
//		sh.textStyle(tTo, 120	, 16, SWT.LEFT, SWT.LEFT, "", "Date/Time To (YYYY-MM-DD hh:mm)", true);

		sh.text(tFrom, 16, SWT.CENTER, SWT.CENTER, "", "Date/Time From (YYYY-MM-DD hh:mm)", true);
		sh.text(tTo, 16, SWT.CENTER, SWT.CENTER, "", "Date/Time To (YYYY-MM-DD hh:mm)", true);
		
		
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Button Search = new Button(grpSearch, SWT.PUSH);
		Button Clear = new Button(grpSearch, SWT.PUSH);
		bs.SC(Search, Clear);

		tFrom.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					RefreshTable.refreshTableWarning(tSearchMsg.getText(), tReasonMsg.getText(), tFrom.getText(), tTo.getText());
				}
			}
	    });

		tTo.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					RefreshTable.refreshTableWarning(tSearchMsg.getText(), tReasonMsg.getText(), tFrom.getText(), tTo.getText());
				}
			}
	    });

		tSearchMsg.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					RefreshTable.refreshTableWarning(tSearchMsg.getText(), tReasonMsg.getText(), tFrom.getText(), tTo.getText());
				}
			}
	    });
		
		tReasonMsg.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					RefreshTable.refreshTableWarning(tSearchMsg.getText(), tReasonMsg.getText(), tFrom.getText(), tTo.getText());
				}
			}
	    });
		
		Search.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RefreshTable.refreshTableWarning(tSearchMsg.getText(), tReasonMsg.getText(), tFrom.getText(), tTo.getText());
			}
		});
		
		Clear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				tSearchMsg.setText(""); 
				tReasonMsg.setText("");
				tm.tgl();
				tFrom.setText(tm.YYYY+"-"+tm.MM+"-00 00:00"); tTo.setText("0000-00-00 00:00");
				RefreshTable.refreshTableWarning(tSearchMsg.getText(), tReasonMsg.getText(), tFrom.getText(), tTo.getText());
			}
		});	

		final Calendar calFrom = Calendar.getInstance();
		tm.tgl();
		tFrom.setText(tm.YYYY+"-"+tm.MM+"-00 00:00");
		tFrom.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; }//* [Y]YYY *
							case 1: { buffer.append('0'); break; }//* Y[Y]YY *
							case 2: { buffer.append('0'); break; }//* YY[Y]Y *
							case 3: { buffer.append('0'); break; }//* YYY[Y] *  
							case 4: { buffer.append('-'); break; }//* YYYY[-]MM * 
							case 5: { buffer.append('0'); break; }//* [M]M *
							case 6: { buffer.append('0'); break; }//* M[M] * 
							case 7: { buffer.append('-'); break; }//* MM[-]DD * 
							case 8: { buffer.append('0'); break; }//* [D]D *
							case 9: { buffer.append('0'); break; }//* D[D] * 
							case 10: { buffer.append(' '); break; }//*DD[ ]hh* 
							case 11: { buffer.append('0'); break; }//* [h]h *
							case 12: { buffer.append('0'); break; }//* h[h] *
							case 13: { buffer.append(':'); break; }//*hh[:]mm* 
							case 14: { buffer.append('0'); break; }//* [m]m *
							case 15: { buffer.append('0'); break; }//* m[m] *
							default: return;
						}
					}
					tFrom.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tFrom.insert(buffer.toString());
					ignore = false;
					tFrom.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 15) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (start + index == 4 || start + index == 7) {
						if (chars[i] == '-') { index++; continue; }
						buffer.insert(index++, '-');
					}
					if (start + index == 10) {
						if (chars[i] == ' ') { index++; continue; }
						buffer.insert(index++, ' ');
					}
					if (start + index == 13) {
						if (chars[i] == ':') { index++; continue; }
						buffer.insert(index++, ':');
					}
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 5 &&  '1' < chars[i]) return; //* [M]M *
					if (start + index == 8 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 11 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 14 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tFrom.getText());
				date.replace(e.start, e.start + length, newText);

				calFrom.set(Calendar.YEAR, 1901);
				calFrom.set(Calendar.MONTH, Calendar.JANUARY);
				calFrom.set(Calendar.DATE, 1);
				calFrom.set(Calendar.HOUR_OF_DAY, 00);
				calFrom.set(Calendar.MINUTE, 00);
				
				String yyyy = date.substring(0, 4);
				if (yyyy.indexOf('0') == -1) {
					int year = Integer.parseInt(yyyy);
					calFrom.set(Calendar.YEAR, year);
				}
				String MM = date.substring(5, 7);
				if (MM.indexOf('0') == -1) {
					int month =  Integer.parseInt(MM) - 1;
					int maxMonth = calFrom.getActualMaximum(Calendar.MONTH);
					if (0 > month || month > maxMonth) return;
					calFrom.set(Calendar.MONTH, month);
				}
				String dd = date.substring(8,10);
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = calFrom.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					calFrom.set(Calendar.DATE, day);
				} 
				String hh = date.substring(11,13);
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = calFrom.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					calFrom.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(14,16);
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = calFrom.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					calFrom.set(Calendar.MINUTE, minute);
				}
				else {
					if (calFrom.get(Calendar.MONTH)  == Calendar.FEBRUARY) {
						char firstChar = date.charAt(8);
						if (firstChar!='0' && '2' < firstChar) return;
					}
				}
				tFrom.setSelection(e.start, e.start + length);
				ignore = true;
				tFrom.insert(newText);
				ignore = false;
			}
		});
		
		final Calendar calTo = Calendar.getInstance();
		tTo.setText("0000-00-00 00:00");//YYYY-MM-DD hh:mm
		tTo.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; }//* [Y]YYY *
							case 1: { buffer.append('0'); break; }//* Y[Y]YY *
							case 2: { buffer.append('0'); break; }//* YY[Y]Y *
							case 3: { buffer.append('0'); break; }//* YYY[Y] *  
							case 4: { buffer.append('-'); break; }//* YYYY[-]MM * 
							case 5: { buffer.append('0'); break; }//* [M]M *
							case 6: { buffer.append('0'); break; }//* M[M] * 
							case 7: { buffer.append('-'); break; }//* MM[-]DD * 
							case 8: { buffer.append('0'); break; }//* [D]D *
							case 9: { buffer.append('0'); break; }//* D[D] * 
							case 10: { buffer.append(' '); break; }//*DD[ ]hh* 
							case 11: { buffer.append('0'); break; }//* [h]h *
							case 12: { buffer.append('0'); break; }//* h[h] *
							case 13: { buffer.append(':'); break; }//*hh[:]mm* 
							case 14: { buffer.append('0'); break; }//* [m]m *
							case 15: { buffer.append('0'); break; }//* m[m] *
							default: return;
						}
					}
					tTo.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tTo.insert(buffer.toString());
					ignore = false;
					tTo.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 15) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (start + index == 4 || start + index == 7) {
						if (chars[i] == '-') { index++; continue; }
						buffer.insert(index++, '-');
					}
					if (start + index == 10) {
						if (chars[i] == ' ') { index++; continue; }
						buffer.insert(index++, ' ');
					}
					if (start + index == 13) {
						if (chars[i] == ':') { index++; continue; }
						buffer.insert(index++, ':');
					}
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 5 &&  '1' < chars[i]) return; //* [M]M *
					if (start + index == 8 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 11 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 14 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tTo.getText());
				date.replace(e.start, e.start + length, newText);

				calTo.set(Calendar.YEAR, 1901);
				calTo.set(Calendar.MONTH, Calendar.JANUARY);
				calTo.set(Calendar.DATE, 1);
				calTo.set(Calendar.HOUR_OF_DAY, 00);
				calTo.set(Calendar.MINUTE, 00);
				
				String yyyy = date.substring(0, 4);
				if (yyyy.indexOf('0') == -1) {
					int year = Integer.parseInt(yyyy);
					calTo.set(Calendar.YEAR, year);
				}
				String MM = date.substring(5, 7);
				if (MM.indexOf('0') == -1) {
					int month =  Integer.parseInt(MM) - 1;
					int maxMonth = calTo.getActualMaximum(Calendar.MONTH);
					if (0 > month || month > maxMonth) return;
					calTo.set(Calendar.MONTH, month);
				}
				String dd = date.substring(8,10);
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = calTo.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					calTo.set(Calendar.DATE, day);
				} 
				String hh = date.substring(11,13);
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = calTo.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					calTo.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(14,16);
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = calTo.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					calTo.set(Calendar.MINUTE, minute);
				}
				else {
					if (calTo.get(Calendar.MONTH)  == Calendar.FEBRUARY) {
						char firstChar = date.charAt(8);
						if (firstChar!='0' && '2' < firstChar) return;
					}
				}
				tTo.setSelection(e.start, e.start + length);
				ignore = true;
				tTo.insert(newText);
				ignore = false;
			}
		});
		
		if (!pMsg.isEmpty()) tSearchMsg.setText(pMsg);
		if (!pReason.isEmpty()) tReasonMsg.setText(pReason);
		if (!pDate.isEmpty()) tFrom.setText(pDate);
		if (!pDateTo.isEmpty()) tTo.setText(pDateTo);
	}
  	
  	void formList() {
  		ReadFromFile.readConfiguration(); 
  		final Group Group = new Group(shell, SWT.NONE); sh.groupStyle(Group, 1, "List of "+getText());
	    // Create the table
	    table = new Table(Group, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION); 
	    sh.tableStyle(table, true, true, ReadFromFile.getHeightTblATS(), MainForm.getWidthRightTop());
	    // Create each of the columns, adding an event listener 
	    // that will set the appropriate fields into the comparatorWarning 
	    // and then call the fillTable helper method
	    final TableColumn ID = new TableColumn(table,SWT.LEFT); sh.tablecol(ID, "id", "", 0, false);
		ID.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorWarning.setColumn(PlayerComparatorWarning.ID);
				comparatorWarning.reverseDirection();
				fillTable(table);
			}
		});
		
		final TableColumn tc_MESSAGE = new TableColumn(table,SWT.LEFT); sh.tablecol(tc_MESSAGE, "Message", "Warning Message", 550, true);
		tc_MESSAGE.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorWarning.setColumn(PlayerComparatorWarning.MESSAGE);
		        comparatorWarning.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn tc_REASON = new TableColumn(table,SWT.LEFT); sh.tablecol(tc_REASON, "Reason", "Warning Reason", 400, true);
		tc_REASON.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorWarning.setColumn(PlayerComparatorWarning.REASON);
		        comparatorWarning.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn tc_TGL = new TableColumn(table,SWT.LEFT); sh.tablecol(tc_TGL, "Date/Time", "Date/Time", 120, true);
		tc_TGL.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorWarning.setColumn(PlayerComparatorWarning.TGL);
		        comparatorWarning.reverseDirection();
		        fillTable(table);
			}
		});

		// Do the initial fill of the table
	    fillTable(table);
	    
	    Listener sortListener = new Listener() {
	    	public void handleEvent(Event e) {
	    		// determine new sort column and direction
	        	TableColumn sortColumn = table.getSortColumn();
	        	TableColumn currentColumn = (TableColumn) e.widget;
	        	dir = table.getSortDirection();
	        	
	        	if (sortColumn == currentColumn) {
	        		dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
	        	} else {
	        		table.setSortColumn(currentColumn);
		            dir = SWT.UP;
	        	}
	    	}
	    };
	      
	    ID.addListener(SWT.Selection, sortListener);
	    tc_MESSAGE.addListener(SWT.Selection, sortListener);
	    tc_REASON.addListener(SWT.Selection, sortListener);
	    tc_TGL.addListener(SWT.Selection, sortListener);

	    table.setSortColumn(ID);
	    table.setSortDirection(SWT.UP);
	      
	    Composite crow = new Composite(Group, SWT.NONE); sh.compositeStyle(crow, 1, SWT.RIGHT, SWT.CENTER);
	    Label lrow = new Label(crow, SWT.NONE); 
		sh.labelStyle1(lrow, getJumlah() + " Element(s) in this table ", SWT.LEFT, SWT.CENTER, SWT.ITALIC | SWT.BOLD, Colors.MANDATORY);
		jdbc.setJmlData(getJumlah());

		Composite group = new Composite(Group, SWT.NONE); sh.compositeStyle(group, 14, SWT.CENTER, SWT.CENTER);
		
		Delete = new Button(group,SWT.PUSH);
		DeleteAll = new Button(group,SWT.PUSH);
		Pdf = new Button(group,SWT.PUSH);
		Pdfs = new Button(group,SWT.PUSH);
		Label hal = new Label(group,SWT.LEFT); sh.labelStyle1(hal, " Page", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);

		yu = 0; qq = 0;
		yu = qq+1;
		isiHal = "";
		isiHal = Integer.toString(yu);
		
		tPage = new Text(group, SWT.BORDER); tPage.setText(isiHal);
		hal = new Label(group,SWT.LEFT); sh.labelStyle1(hal, " of ", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		
		int jml;
		if (jumlah % baris == 0) { jml = jumlah/baris; }
		else {  jml = jumlah/baris+1; }
		String jum = Integer.toString(jml);
	
		tTotPage = new Text(group, SWT.BORDER); tTotPage.setText(jum); 
		first = new Button(group,SWT.PUSH);
		prev = new Button(group,SWT.PUSH);
		next = new Button(group,SWT.PUSH);
		last = new Button(group,SWT.PUSH);
		hal = new Label(group,SWT.LEFT); sh.labelStyle1(hal, "  Go to", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tGoToHal = new Text(group, SWT.BORDER);
		
        if (jumlah % baris == 0) { hasil = jumlah/baris; }
        else { hasil = jumlah/baris+1; }
        
        if (hasil == 0) { disableListButton(false); }

        if (hasil == 1) {
        	first.setEnabled(false);
        	prev.setEnabled(false);
            next.setEnabled(false);
            last.setEnabled(false);
            tGoToHal.setEnabled(false);
        } 
		
		if (jumlah <= baris) {
			first.setEnabled(false);
			prev.setEnabled(false);
			next.setEnabled(false);
			last.setEnabled(false);
			tGoToHal.setEnabled(false);
		}
		
		bs.DDPP(Delete, DeleteAll, Pdf, Pdfs);
		bs.Navigation(tPage, tTotPage, first, prev, next, last, tGoToHal);
	    
	    // To get ID of the row
	    table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				string = "";
				selection = table.getSelection();
				String idcntval="";
				for (int i=0; i<selection.length; i++) {
					idcntval = selection[i].getText(0);
					string += selection [i] + " ";
				}

				if (string.contains("TableItem {")) {
					string = string.replace("TableItem {", "");
					if (string.contains("}")) string = string.replaceAll("} ", "");
					sqlSelect="SELECT * FROM warning WHERE idcnt='"+idcntval+"'";
				}
			}
		});

		Delete.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selection = table.getSelection(); 
				if (selection.length==0 || sqlSelect.equals("")) { DialogFactory.openInfoAtLeast(Shells.sh_tWarning,DialogFactory.delete); } 
				else {
					string="";
					
					DialogFactory.openYesNoDelete(Shells.sh_tWarning);
					boolean tentu = DialogFactory.getPenentuan(); 
					if (tentu == true) {
						for (int i=0; i<selection.length; i++) { //banyak baris yang akan dihapus
							string = selection[i].getText(0);
							jdbc.delete("DELETE FROM warning WHERE idcnt="+string);
						}
						
						TriggerAction.trigger("warning", 101);
//						senddata("101","warning",7);
						
						if (getJumlah() > 1) {
							string=""; sqlSelect="";
							table.removeAll(); playersWarning.clear();
							RefreshTable.refreshTableWarning(pMsg,pReason,pDate,pDateTo);
						} else if (getJumlah() == 1) {
							string=""; sqlSelect=""; shell.close();
						}
					} //end of if
				}
			}
		});
		
		DeleteAll.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//memilih baris yang akan di hapus -update 20101109
				table.selectAll();
				selection = table.getSelection(); 
				
				if (getJumlah() == 0) {
					DialogFactory.openInfoDialog(Shells.sh_tWarning, "Delete Message", "There's no data !!");
				} else {
					DialogFactory.openYesNoDelete(Shells.sh_tWarning);
					boolean tentu = DialogFactory.getPenentuan();
					if (tentu == true) {
						try { //biar kalo ada table yang doesn't exist ga mati.
							rs = stmt.executeQuery(lihatsql);

							while (rs.next()) {
								rowNo++;
								string = rs.getString("idcnt");
							} //end of while
							
							if (lihatsql.contains("SELECT * FROM")) lihatsql = lihatsql.replace("SELECT * FROM", "DELETE FROM");
							jdbc.delete(lihatsql);
							//buat ng'refreshnya
							table.removeAll(); playersWarning.clear(); shell.close();
						} catch (SQLException s) { // biar kalo ada table yang doesn't exist ga mati.
							System.out.println("Error: " + s.getMessage());
						}
						
						TriggerAction.trigger("warning", 101);
//						senddata("101","warning",7);
					} //end of if
					else if (tentu == false) { string=""; sqlSelect=""; }
				}
			}
		});
		
		Pdf.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//memilih baris yang akan di print
				selection = table.getSelection(); 
				if ((selection.length == 0) || (sqlSelect.equals(""))) {
					DialogFactory.openInfoAtLeast(Shells.sh_tWarning,DialogFactory.export);
				} else {
					String id_ats="";
					String msg="",msgall="",printable="";
					for (int i=0; i<selection.length; i++) { //banyak baris yang akan di print
						id_ats = selection[i].getText(0);
						
						String sel = "SELECT * FROM warning WHERE idcnt="+id_ats;
						jdbc.select(sel, "warning", "warning", "");
						msg = jdbc.getaftn();

						if (msgall.compareTo(msg)!=0) {
							printable+=msg+"\n";
						} else {
							printable+=msg+"\n";
						}
						
//						if (msgall.compareTo(msg)!=0) {
//							printable+=msg+line+"\n";
//						} else {
//							printable+=msg+line+"\n";
//						}
					}
					String lines="-";
					ReadFromFile.readConfiguration();
					for (int i=0; i<ReadFromFile.getPDFLine(); i++) { lines+="-"; }
					writetopdf.setPrint(printable, lines+"\n"+Integer.toString(selection.length), MainForm.sPath + "Warning.pdf");
					msg="";
					try {
						ReadFromFile.readConfiguration();
						String cmd = ReadFromFile.getPDFCmd() + " /tmp/ramdisk0/Warning.pdf";
						System.out.println(cmd);
						Runtime.getRuntime().exec(cmd);
					} catch (IOException ioe) {
						System.err.println("open pdf err: " + ioe.getMessage());
					}
				} //end of else
			}
		});
		
		Pdfs.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String msg="",printable="";
				try {
					int rowNo = 0;

					Connection conn = null; // connection object
			  	    Statement stmt = null; // statement object
			  	    ResultSet rs = null; // result set object
			  	    
			  	    try { conn = jdbc.getDBConnection(); } 
					catch (Exception eX) { eX.printStackTrace(); }
					
					stmt = conn.createStatement();
					try { //biar kalo ada table yang doesn't exist ga mati.
						lihatsql = "SELECT * FROM warning ORDER BY idcnt DESC,tgl DESC";
						rs = stmt.executeQuery(lihatsql);

						while (rs.next()) {
							rowNo++;
		
							String id = rs.getString("idcnt"); if (id==null) { id=""; }
							String sel = "SELECT * FROM warning WHERE idcnt="+id;
							jdbc.select(sel, "warning", "warning", "");
							
							msg = jdbc.getaftn()+"\n";
							printable+=msg+line+"\n";

						} //end of while
						
					} catch (SQLException s) { // biar kalo ada table yang doesn't exist ga mati.
						System.out.println("info: " + s.getMessage());
					}
					String lines="-";
					ReadFromFile.readConfiguration();
					for (int i=0; i<ReadFromFile.getPDFLine(); i++) { lines+="-"; }
					writetopdf.setPrint(printable, lines+"\n"+Integer.toString(jdbc.getJmlData()), MainForm.sPath + "WarningAll.pdf");
					msg="";
					try {
						ReadFromFile.readConfiguration();
						String cmd = ReadFromFile.getPDFCmd() + " "+MainForm.sPath+"WarningAll.pdf";
						System.out.println(cmd);
						Runtime.getRuntime().exec(cmd);
					} catch (IOException ioe) {
						System.err.println("open pdf err: " + ioe.getMessage());
					}
				} catch (SQLException s) {
					s.printStackTrace();
        		} catch (java.lang.OutOfMemoryError hs) {
        			hs.printStackTrace();
        		}
			}
		});
		
		first.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (first.getEnabled() == true) {
					first.setEnabled(false);
					prev.setEnabled(false);
					next.setEnabled(true);

					if (last.getEnabled() == false) {
						last.setEnabled(true);
					}
				}

				table.removeAll();
				qq = 0;
				isiHal = Integer.toString(qq);
				tPage.setText("1");
				tGoToHal.setText("1");
				Next();
			}
		});

		prev.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (prev.getEnabled() == true) {
					next.setEnabled(true);
					first.setEnabled(true);
					last.setEnabled(true);
				}

				qq--;
				if (qq == 0) {
					prev.setEnabled(false);
					first.setEnabled(false);
				}

				if (qq < 0)
					qq = 0;
				else {
					table.removeAll();
					int as = Integer.parseInt(akhir().trim()) - 1;
					isiHal = Integer.toString(as);
					tPage.setText(isiHal);
					Next();
				}
				tGoToHal.setText(isiHal);
			}
		});

		next.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				prev.setEnabled(true);
				first.setEnabled(true);

				qq++;

				// ----
				int jml;
				if (jumlah % baris == 0) {
					jml = jumlah/baris;
				} else {
					jml = jumlah/baris + 1;
				}
				// -----

				int all = jml;

				if ((all - 1) == qq) {
					next.setEnabled(false);
					last.setEnabled(false);
				}

				if (qq < jml) {
					table.removeAll();
					int as = qq + 1;
					isiHal = Integer.toString(as);
					tPage.setText(isiHal);
					Next();
				}
				tGoToHal.setText(isiHal);
			}
		});

		last.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				next.setEnabled(false);
				prev.setEnabled(true);
				first.setEnabled(true);

				table.removeAll();
				if (jumlah % baris == 0)
					qq = jumlah/baris - 1;
				else
					qq = jumlah/baris;

				isiHal = Integer.toString(qq + 1);
				tPage.setText(isiHal);
				Next();
				last.setEnabled(false);

				tGoToHal.setText(isiHal);
			}
		});

		tGoToHal.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					setgo();
				}
			}
	    });
  	}
  	
  	void disableListButton(boolean b) {
  		Pdf.setEnabled(b);
  		Pdfs.setEnabled(b);
  		Delete.setEnabled(b);
  		DeleteAll.setEnabled(b);
  		
  		first.setEnabled(b);
    	prev.setEnabled(b);
        next.setEnabled(b);
        last.setEnabled(b);
        tGoToHal.setEnabled(b);
  	}
  	
  	@SuppressWarnings("unchecked")
	private void fillTable(Table table) {
	    // Turn off drawing to avoid flicker
	    table.setRedraw(false);
        
	    // We remove all the table entries, sort our rows, then add the entries
	    table.removeAll();
	    
	    Collections.sort(playersWarning, comparatorWarning);
	    for (Iterator itr = playersWarning.iterator(); itr.hasNext();) {
	    	PlayerWarning player = (PlayerWarning) itr.next();
	    	TableItem item = new TableItem(table, SWT.NONE);
	    	int c = 0;
	    	item.setText(c++, player.getID());
	    	item.setText(c++, player.getMESSAGE());
	    	item.setText(c++, player.getREASON());
	    	item.setText(c++, player.getTGL());
	    }
	    
	    // sort indicator
	    table.setSortDirection(dir);
        //table.clearAll();
        
	    // Turn drawing back on
	    table.setRedraw(true);
  	}
  	
  	void setString2(String str) { string2 = str; }
  	public static String akhir() { return isiHal; }
	public static String GetSqlSelect() { return sqlSelect; }
	public static String GetString() { return string; }
	public static String GetString2() { return string2; }
	public static int getJumlah() { return jumlah; }

	void setgo() {
		String sGoToHal = tGoToHal.getText();
        if ((tGoToHal.getText().equals("")) || (tGoToHal.getText().equals("0"))) {
            sGoToHal = "1";
            tGoToHal.setText("1");
        }
        
        int m = Integer.parseInt(sGoToHal);
        int nHasil = hasil-1;
        m-=1;

        if (m == 0) {
        	first.setEnabled(false);
        	prev.setEnabled(false);
            next.setEnabled(true);
            last.setEnabled(true);
        }
        if (m == nHasil) {
        	first.setEnabled(true);
        	prev.setEnabled(true);
        	next.setEnabled(false);
            last.setEnabled(false);
        }
        if ((m != 0) && (m != nHasil)) {
            first.setEnabled(true);
            prev.setEnabled(true);
            next.setEnabled(true);                        
            last.setEnabled(true);
        }
        if (m > nHasil) {
            m = nHasil;
            first.setEnabled(true);
        	prev.setEnabled(true);
        	next.setEnabled(false);
            last.setEnabled(false);
            tGoToHal.setText(Integer.toString(hasil));
        }

        m = m-1;
        qq = m;
        qq++;

        table.removeAll();
        int as=qq+1;
        isiHal=Integer.toString(as);
        tPage.setText(isiHal);
        Next();
	}
	
	void connClose() {
		try {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		} catch(SQLException se) {
			se.printStackTrace();
		}
	}
	
//	 public void senddata(String sPort,String sData,int length) {
//	  	  try {
//	  		  String sIPadrp="127.0.0.1";
//	  		  DatagramSocket s = new DatagramSocket();
//	            byte[] line = new byte[10000];
//				  System.out.println("To   : "+sIPadrp+" Data:"+sData);
//	            InetAddress dest = InetAddress.getByName(sIPadrp);
//	            line = sData.getBytes();
//	            int len = line.length;
//	            DatagramPacket pkt = new DatagramPacket(line, len, dest, Integer.parseInt(sPort));
//				  System.out.println("Send : "+len+" bytes ");
//	            s.send(pkt);
//	            s.close();
//	        } catch (Exception err) {
//	            System.out.println("err:" + err);
//	        }
//		}
}

/**
 * This class represents a player.
 */
class PlayerWarning {
	private String ID;
	private String MESSAGE; 
	private String REASON; 
	private String TGL; 
	 
	public PlayerWarning(String ID,String MESSAGE,String REASON,String TGL) {
		this.ID = ID;
		this.MESSAGE = MESSAGE;
		this.REASON = REASON;
		this.TGL = TGL;
	}

	public String getID() { return ID; }
	public String getMESSAGE() { return MESSAGE; }
	public String getREASON() { return REASON; }
	public String getTGL() { return TGL; }
}

@SuppressWarnings("unchecked")
class PlayerComparatorWarning implements Comparator {
	public static final int ID = 0;
	public static final int MESSAGE = 1;
	public static final int REASON = 2;
	public static final int TGL = 3;
	
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
	    PlayerWarning p1 = (PlayerWarning) obj1;
	    PlayerWarning p2 = (PlayerWarning) obj2;
	
	    // Determine which field to sort on, then sort on that field
	    switch (column) {
		    case ID:
		    	rc = p1.getID().compareTo(p2.getID());
		    	break;
		    case MESSAGE:
		    	rc = p1.getMESSAGE().compareTo(p2.getMESSAGE());
		    	break;
		    case REASON:
		    	rc = p1.getREASON().compareTo(p2.getREASON());
		    	break;
		    case TGL:
		    	rc = p1.getTGL().compareTo(p2.getTGL());
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