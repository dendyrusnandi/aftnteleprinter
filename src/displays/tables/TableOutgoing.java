package displays.tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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
import setting.ATSComponentSetting;
import setting.ButtonsSetting;
import setting.Colors;
import setting.ErrMsg;
import setting.Shells;
import setting.Shorten;
import setting.TextSetting;
import actions.RefreshTable;
import actions.TriggerAction;
import actions.ViewATSFunction;
import actions.cProgressBar;
import databases.jdbc;
import displays.DialogFactory;
import displays.MainForm;
import displays.TeleSplashScreen2016IP;
import displays.forms.ATSForms;
import displays.forms.HeaderComposite;


public class TableOutgoing {
	@SuppressWarnings("unchecked")
	private java.util.List playersOUTBOX_ATS;
	private PlayerComparatorOUTBOX_ATS comparatorOUTBOX_ATS;
	
	Shorten sh = new Shorten();
	ButtonsSetting bs = new ButtonsSetting();
	TextSetting ts = new TextSetting();
	ErrMsg em = new ErrMsg();
	ViewATSFunction vATS = new ViewATSFunction();
	
//	public static ComposeWindow window = new ComposeWindow(true);
//	private static MenuItemNewATS newATS;
	
	public static String queryQueue = "SELECT * FROM check_status WHERE tgl != '0000-00-00 00:00:00' AND " +
			"(tbl_name LIKE 'air_message%' OR tbl_name LIKE 'air_message_free_text%') " +
			"AND tbl_name NOT LIKE 'air_message_free_text_meteo%' " +
			"AND (status='waiting' OR status='not sent') " +
			" ORDER BY id DESC, tgl DESC";
	
	public static String queryQueueAcft = "SELECT * FROM check_status WHERE tgl != '0000-00-00 00:00:00' AND " +
	"(tbl_name LIKE 'air_message%' OR tbl_name LIKE 'air_message_free_text%') " +
	"AND tbl_name NOT LIKE 'air_message_free_text_meteo%' " +
	"AND (status='waiting' OR status='not sent') ";
	
	static String id="",P1="",P2="",P3="",P4="",P5="",P6="",P7="",P8="",P9="",P10="",P11="";
	static String sqlSelect="",sqlSelect2="",string="",string2="",tanggal="",lihatsql="",sele="",isiHal="",jml_item="",outbox="",
	tblName="",dateTime="",sType="",sNotes="";
	String id_tbl="",pAircraftId="",strAcftId="";
	//static 
	String id_ats="",msgType="";
	String filing="";//,msgs="";
	static String line="";
	
	int dir=0,p=0,yu=0,hasil=0;
	static int baris=20,qq=0,jumlah=0,rowNo=0,qu=0;

	Shell shell;
	static Table table;
	TableItem[] selection;
	Button next,prev,first,last,View,Edit,Delete,DeleteAll;//,go;
	Text tPage, tTotPage, tGoToHal;
	
	Connection conn = null; // connection object
    Statement stmt = null; // statement object
    ResultSet rs = null; // result set object
	
    String strr="OUT";
    
    
  	public TableOutgoing() { }

  	public static String getText() {
  		return "Queue/Not Delivered Messages";
  	}
  	
  	public void koneksiDB(Shell pshell, String sAircraftId) {
  		shell=pshell;
  		pAircraftId = sAircraftId;
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
						    // Create the comparatorOUTBOX_ATS used for sorting
						    comparatorOUTBOX_ATS = new PlayerComparatorOUTBOX_ATS();
						    comparatorOUTBOX_ATS.setColumn(PlayerComparatorOUTBOX_ATS.TANGGAL);
						    comparatorOUTBOX_ATS.setDirection(PlayerComparatorOUTBOX_ATS.ASCENDING);
						    
						    // Create the playersOUTBOX_ATS
						    playersOUTBOX_ATS = new ArrayList();
						    
						    if (!pAircraftId.isEmpty()) strAcftId = " AND aircraft_id LIKE '"+pAircraftId+"%'"; else strAcftId=""; 
							lihatsql = queryQueueAcft+strAcftId+" ORDER BY id DESC, tgl DESC";//"SELECT * FROM check_status WHERE tgl != '0000-00-00 00:00:00' AND (tbl_name LIKE 'air_message%' OR tbl_name LIKE 'air_message_free_text%') AND tbl_name NOT LIKE 'air_message_free_text_meteo%' AND (status='waiting' OR status='not sent')"+strAcftId+" ORDER BY tgl DESC";
							System.out.println("\nQuery: " + lihatsql);
							stmt = conn.createStatement();
							rs = stmt.executeQuery(lihatsql);
							rs.last();
							jumlah = rs.getRow();
							rs.beforeFirst();
							
							final String datatabel[][] = new String[baris][12];
							p = 0;
							rowNo = 0;
							while (rs.next() && (rowNo < baris)) {
								rowNo++;
								
								datatabel[p][0] = rs.getString("id"); id=datatabel[p][0]; if (id==null){ id=""; }
								datatabel[p][1] = rs.getString("type"); P1=datatabel[p][1]; if (P1==null){ P1=""; }
								datatabel[p][2] = rs.getString("filing_time"); P2=datatabel[p][2]; if (P2==null){ P2=""; }
								datatabel[p][3] = rs.getString("jam"); P3=datatabel[p][3]; if (P3==null){ P3=""; }								
								datatabel[p][4] = rs.getString("originator"); P4=datatabel[p][4]; if (P4==null){ P4=""; }
								datatabel[p][5] = rs.getString("aircraft_id"); P5=datatabel[p][5]; if (P5==null){ P5=""; }
								datatabel[p][6] = rs.getString("message"); P6=datatabel[p][6]; if (P6==null){ P6=""; }
								datatabel[p][7] = rs.getString("status"); P7=datatabel[p][7]; if (P7==null){ P7=""; }
								datatabel[p][8] = rs.getString("tgl"); P8=datatabel[p][8].substring(0,19); if (P8==null){ P8=""; }
								datatabel[p][9] = rs.getString("filed_by"); P9=datatabel[p][9]; if (P9==null){ P9=""; }
								datatabel[p][10] = rs.getString("tbl_name"); P10=datatabel[p][10]; if (P10==null){ P10=""; }
								datatabel[p][11] = rs.getString("note"); P11=datatabel[p][11]; if (P11==null){ P11=""; }
								
								if (P6.length() > 35) P6 = P6.substring(0, 35).concat(" ...");
								
								p++;
								playersOUTBOX_ATS.add(new PlayerOUTBOX_ATS(id,P1,P2,P3,P4,P5,P6,P7,P8,P9,P10,P11));
								progbr.bar.setSelection(rowNo);
							}
							progbr.finish();
							
//							if (getJumlah() != 0) {
								if (getJumlah()==0) DialogFactory.openInfoDialog(Shells.sh_tOutbox, "Search Message","Data not found, your search has no result !!");
								formSearch();
							    formList();
							    sh.shellStyle(shell, getText());
							    shell.addListener(SWT.Close, new Listener() {
									public void handleEvent(Event event) {
										connClose();
									}
								});
//					  		} else {
//								DialogFactory.openInfoDialog(Shells.sh_tOutbox, "Outbox Messages","There's no data !!");
//							}
	        			} catch (SQLException s) {
	    					DialogFactory.openInfoDialog(Shells.sh_tOutbox, getText(), s.toString());
	    					s.printStackTrace();
	    					progbr.finish();
	    				} catch (java.lang.OutOfMemoryError hs) {
	    					hs.printStackTrace();
	    					DialogFactory.openInfoDialog(Shells.sh_tOutbox, getText(), "Out of memory !!");
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
								
								final String datatabel[][] = new String[baris][12];
								
								for (rowNo=0; rowNo<(qq*baris); rowNo++) rs.next();
								p = 0;
								rowNo = 0;
								playersOUTBOX_ATS.clear();
								while (rs.next() && (rowNo < baris)) {
									rowNo++;
									
									datatabel[p][0] = rs.getString("id"); id=datatabel[p][0]; if (id==null){ id=""; }
									datatabel[p][1] = rs.getString("type"); P1=datatabel[p][1]; if (P1==null){ P1=""; }
									datatabel[p][2] = rs.getString("filing_time"); P2=datatabel[p][2]; if (P2==null){ P2=""; }
									datatabel[p][3] = rs.getString("jam"); P3=datatabel[p][3]; if (P3==null){ P3=""; }								
									datatabel[p][4] = rs.getString("originator"); P4=datatabel[p][4]; if (P4==null){ P4=""; }
									datatabel[p][5] = rs.getString("aircraft_id"); P5=datatabel[p][5]; if (P5==null){ P5=""; }
									datatabel[p][6] = rs.getString("message"); P6=datatabel[p][6]; if (P6==null){ P6=""; }
									datatabel[p][7] = rs.getString("status"); P7=datatabel[p][7]; if (P7==null){ P7=""; }
									datatabel[p][8] = rs.getString("tgl"); P8=datatabel[p][8].substring(0,19); if (P8==null){ P8=""; }
									datatabel[p][9] = rs.getString("filed_by"); P9=datatabel[p][9]; if (P9==null){ P9=""; }
									datatabel[p][10] = rs.getString("tbl_name"); P10=datatabel[p][10]; if (P10==null){ P10=""; }
									datatabel[p][11] = rs.getString("note"); P11=datatabel[p][11]; if (P11==null){ P11=""; }
									
									if (P6.length() > 35) P6 = P6.substring(0, 35).concat(" ...");
									
									TableItem item = new TableItem (table, SWT.NONE);
									item.setText (0,id); 
									item.setText (1,P1);
									item.setText (2,P2); 
									item.setText (3,P3);
									item.setText (4,P4); 
									item.setText (5,P5);
									item.setText (6,P6); 
									item.setText (7,P7);
									item.setText (8,P8);
									item.setText (9,P9);
									item.setText (10,P10);
									item.setText (11,P11);
									
									p++;
									playersOUTBOX_ATS.add(new PlayerOUTBOX_ATS(id,P1,P2,P3,P4,P5,P6,P7,P8,P9,P10,P11));
									progbr.bar.setSelection(rowNo);
								}
								progbr.finish();
							} catch (SQLException s) {
								DialogFactory.openInfoDialog(Shells.sh_tOutbox, getText(), s.toString());
								s.printStackTrace();
							}
	    				} catch (java.lang.OutOfMemoryError hs) {
	    					hs.printStackTrace();
	    					DialogFactory.openInfoDialog(Shells.sh_tOutbox, getText(), "Out of memory !!");
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
  		final Group grpSearch = new Group(shell, SWT.NONE); sh.groupStyle(grpSearch, 5, "Search "+getText());
  		Label label = new Label(grpSearch,SWT.NONE); sh.labelStyle1(label, "AIRCRAFT ID", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		final Text tType7a_Search = new Text(grpSearch,SWT.BORDER); sh.textStyle(tType7a_Search, 80, 7, SWT.LEFT, SWT.LEFT, sh.alphanum, ATSComponentSetting.b7a, true); 
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Button Search = new Button(grpSearch, SWT.PUSH);
		Button Clear = new Button(grpSearch, SWT.PUSH);
		bs.SC(Search, Clear);

//		if (!pAircraftId.isEmpty()) tType7a_Search.setText(pAircraftId);
		
		tType7a_Search.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					RefreshTable.refreshTableOutbox(tType7a_Search.getText());
				}
			}
	    });
		
		Search.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RefreshTable.refreshTableOutbox(tType7a_Search.getText());
			}
		});
		
		Clear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				tType7a_Search.setText("");
				RefreshTable.refreshTableOutbox(tType7a_Search.getText());
			}
		});	
		
		if (!pAircraftId.isEmpty()) tType7a_Search.setText(pAircraftId);
	}
  	
  	public static void setTableFocus() {
		// set focus for table
		if (!table.isDisposed()) { table.setFocus(); }
	}
  	
  	void formList() {
  		ReadFromFile.readConfiguration(); 
  		final Group Group = new Group(shell, SWT.NONE); sh.groupStyle(Group, 1, "List of "+getText());
	    // Create the table
	    table = new Table(Group, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION); 
	    sh.tableStyle(table, true, true, ReadFromFile.getHeightTblATS(), MainForm.getWidthRightTop());
	    // Create each of the columns, adding an event listener 
	    // that will set the appropriate fields into the comparatorOUTBOX_ATS 
	    // and then call the fillTable helper method
	    final TableColumn ID = new TableColumn(table,SWT.LEFT); sh.tablecol(ID, "id", "", 0, false);
		ID.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorOUTBOX_ATS.setColumn(PlayerComparatorOUTBOX_ATS.ID);
				comparatorOUTBOX_ATS.reverseDirection();
				fillTable(table);
			}
		});
		
		final TableColumn MsgType = new TableColumn(table,SWT.LEFT); sh.tablecol(MsgType, "Message Type", "Message Type", 150, false);
		MsgType.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorOUTBOX_ATS.setColumn(PlayerComparatorOUTBOX_ATS.TYPE);
		    	comparatorOUTBOX_ATS.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn FILING = new TableColumn(table,SWT.LEFT); sh.tablecol(FILING, "Filing", "Filing Time", 80, false);
		FILING.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorOUTBOX_ATS.setColumn(PlayerComparatorOUTBOX_ATS.FILING_TIME);
		        comparatorOUTBOX_ATS.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn SENDAT = new TableColumn(table,SWT.LEFT); sh.tablecol(SENDAT, "Send At", "Send At", 80, false);
		SENDAT.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorOUTBOX_ATS.setColumn(PlayerComparatorOUTBOX_ATS.SEND_AT);
		        comparatorOUTBOX_ATS.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn ORIGINATOR = new TableColumn(table,SWT.LEFT); sh.tablecol(ORIGINATOR, "Originator", "Originator", 120, false);
		ORIGINATOR.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorOUTBOX_ATS.setColumn(PlayerComparatorOUTBOX_ATS.ORIGINATOR);
		        comparatorOUTBOX_ATS.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn AIRCRAFT = new TableColumn(table,SWT.LEFT); sh.tablecol(AIRCRAFT, "Aircraft", "Aircraft Identification", 100, false);
		AIRCRAFT.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorOUTBOX_ATS.setColumn(PlayerComparatorOUTBOX_ATS.AIRCRAFT);
		        comparatorOUTBOX_ATS.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn MESSAGE = new TableColumn(table,SWT.LEFT); sh.tablecol(MESSAGE, "Message", "Message", 370, false);
		MESSAGE.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorOUTBOX_ATS.setColumn(PlayerComparatorOUTBOX_ATS.MSG);
		        comparatorOUTBOX_ATS.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn STATUS = new TableColumn(table,SWT.LEFT); sh.tablecol(STATUS, "Status", "Status", 130, false);
		STATUS.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorOUTBOX_ATS.setColumn(PlayerComparatorOUTBOX_ATS.STATUS);
		        comparatorOUTBOX_ATS.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn DATETIME = new TableColumn(table,SWT.LEFT); sh.tablecol(DATETIME, "Date/Time", "Date/Time", 120, false);
		DATETIME.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorOUTBOX_ATS.setColumn(PlayerComparatorOUTBOX_ATS.TANGGAL);
		        comparatorOUTBOX_ATS.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn FILEDBY = new TableColumn(table,SWT.LEFT); sh.tablecol(FILEDBY, "Filled by", "Filed by", 0, false);
		FILEDBY.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorOUTBOX_ATS.setColumn(PlayerComparatorOUTBOX_ATS.FILEDBY);
		        comparatorOUTBOX_ATS.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn TBL_NAME = new TableColumn(table,SWT.LEFT); sh.tablecol(TBL_NAME, "tblnm", "tblnm", 0, false);
		TBL_NAME.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorOUTBOX_ATS.setColumn(PlayerComparatorOUTBOX_ATS.TBL_NAME);
		        comparatorOUTBOX_ATS.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn NOTES = new TableColumn(table,SWT.LEFT); sh.tablecol(NOTES, "tblnm", "tblnm", 0, false);
		NOTES.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorOUTBOX_ATS.setColumn(PlayerComparatorOUTBOX_ATS.NOTES);
		        comparatorOUTBOX_ATS.reverseDirection();
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
	    MsgType.addListener(SWT.Selection, sortListener);
	    FILING.addListener(SWT.Selection, sortListener);
	    SENDAT.addListener(SWT.Selection, sortListener);
	    AIRCRAFT.addListener(SWT.Selection, sortListener);
	    ORIGINATOR.addListener(SWT.Selection, sortListener);
	    MESSAGE.addListener(SWT.Selection, sortListener);
	    STATUS.addListener(SWT.Selection, sortListener);
	    DATETIME.addListener(SWT.Selection, sortListener);
	    FILEDBY.addListener(SWT.Selection, sortListener);
	    TBL_NAME.addListener(SWT.Selection, sortListener);
	    NOTES.addListener(SWT.Selection, sortListener);
	    
	    table.setSortColumn(ID);
	    table.setSortDirection(SWT.UP);
	      
	    Composite crow = new Composite(Group, SWT.NONE); sh.compositeStyle(crow, 1, SWT.RIGHT, SWT.CENTER);
	    Label lrow = new Label(crow, SWT.NONE); 
		sh.labelStyle1(lrow, getJumlah() + " Element(s) in this table ", SWT.LEFT, SWT.CENTER, SWT.ITALIC | SWT.BOLD, Colors.MANDATORY);
		jdbc.setJmlData(getJumlah());

		Composite group = new Composite(Group, SWT.NONE); sh.compositeStyle(group, 14, SWT.CENTER, SWT.CENTER);
		
		View = new Button(group,SWT.PUSH);
	    Edit = new Button(group,SWT.PUSH);
		Delete = new Button(group,SWT.PUSH);
		DeleteAll = new Button(group,SWT.PUSH);
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
		
		bs.VEDD(View, Edit, Delete, DeleteAll);
		bs.Navigation(tPage, tTotPage, first, prev, next, last, tGoToHal);
	    
	    // To get ID of the row
	    table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				string = "";
				selection = table.getSelection();
	          	//20101109
				id_ats="";
				tblName="";
				msgType="";
				dateTime="";
				String tipe=""; 
				for (int i=0; i<selection.length; i++) {
					tblName = selection[i].getText(10);
					id_ats = selection[i].getText(0);
					msgType = selection[i].getText(1);
					filing = selection[i].getText(2);
					dateTime = selection[i].getText(8);
					sNotes = selection[i].getText(11);
					string += selection [i] + " ";
				}

				if (string.contains("TableItem {")) {
					string = string.replace("TableItem {", "");
					if (string.contains("}")) string = string.replaceAll("} ", "");
					
					//set msgType disimpan di variabel lain, untuk di get di StoreAction
					setMsgType(msgType);
					//Semua berita RPL masuk ke tabel: air_messageYYYY_MM setelah jam 00:00 (program acep)
					//type3a di tabel air_message : FPL
					//type di check_status : RPL
					if (msgType.compareTo("RPL")==0) msgType="FPL";
					
					//table di buat per bulan
					if (tblName.startsWith("air_message_free_text")) { id_tbl = "id_free_text='"; outbox=msgType; } 
					else if (tblName.startsWith("air_message")) { id_tbl = "id_ats='"; outbox=msgType; tipe="type3a='"+msgType+"' AND ";}
					else if (tblName.startsWith("rpl_content")) { id_tbl = "id_rpl_cont='"; }

					if (tipe.compareTo("type3a='FREETEXT' AND ")==0 || tipe.compareTo("type3a='FREE TEXT' AND ")==0) tipe="";
					if (tipe.compareTo("type3a='' AND ")==0) tipe="";
					sqlSelect="SELECT * FROM "+tblName+" WHERE "+tipe+id_tbl+id_ats+"'"; //untuk fungsi View,Edit
				}
			}
		});
	    
		table.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				openEdit();
			}
		});
	      
		View.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String allMsgType="";
				selection = table.getSelection(); 
				if (selection.length==0 || sqlSelect.equals("")) { DialogFactory.openInfoAtLeast(Shells.sh_tOutbox,DialogFactory.view); } 
				else {
					id_ats="";
					tblName="";
					String msg="",msgall="",printable="";
					for (int i=0; i<selection.length; i++) { //banyak baris yang akan di print
						tblName = selection[i].getText(10);
						id_ats = selection[i].getText(0);
						msgType = selection[i].getText(1);
						
						String template="";
						if (msgType.compareTo("FREETEXT")==0 || msgType.compareTo("FREE TEXT")==0) template = " WHERE";
						else if (msgType.compareTo("ALR")==0) template = " WHERE type3a='ALR' AND"; 
						else if (msgType.compareTo("RCF")==0) template = " WHERE type3a='RCF' AND";
						else if (msgType.compareTo("FPL")==0) template = " WHERE type3a='FPL' AND";
						else if (msgType.compareTo("DLA")==0) template = " WHERE type3a='DLA' AND";
						else if (msgType.compareTo("CHG")==0) template = " WHERE type3a='CHG' AND";
						else if (msgType.compareTo("CNL")==0) template = " WHERE type3a='CNL' AND";
						else if (msgType.compareTo("DEP")==0) template = " WHERE type3a='DEP' AND";
						else if (msgType.compareTo("ARR")==0) template = " WHERE type3a='ARR' AND";
						else if (msgType.compareTo("CDN")==0) template = " WHERE type3a='CDN' AND";
						else if (msgType.compareTo("CPL")==0) template = " WHERE type3a='CPL' AND";
						else if (msgType.compareTo("EST")==0) template = " WHERE type3a='EST' AND";
						else if (msgType.compareTo("ACP")==0) template = " WHERE type3a='ACP' AND";
						else if (msgType.compareTo("LAM")==0) template = " WHERE type3a='LAM' AND";
						else if (msgType.compareTo("RQP")==0) template = " WHERE type3a='RQP' AND";
						else if (msgType.compareTo("RQS")==0) template = " WHERE type3a='RQS' AND";
						else if (msgType.compareTo("SPL")==0) template = " WHERE type3a='SPL' AND";
						String sel = "SELECT * FROM "+tblName+template+" id_ats="+id_ats;
						jdbc.select(sel, tblName, msgType, "");
						
						if (msgType.compareTo("FREETEXT")==0 || msgType.compareTo("FREE TEXT")==0) msg = jdbc.getaftn();
						else if (msgType.compareTo("ALR")==0) msg = jdbc.getalr(); 
						else if (msgType.compareTo("RCF")==0) msg = jdbc.getrcf();
						else if (msgType.compareTo("FPL")==0) msg = jdbc.getfpl();
						else if (msgType.compareTo("DLA")==0) msg = jdbc.getdla();
						else if (msgType.compareTo("CHG")==0) msg = jdbc.getchg();
						else if (msgType.compareTo("CNL")==0) msg = jdbc.getcnl();
						else if (msgType.compareTo("DEP")==0) msg = jdbc.getdep();
						else if (msgType.compareTo("ARR")==0) msg = jdbc.getarr();
						else if (msgType.compareTo("CDN")==0) msg = jdbc.getcdn();
						else if (msgType.compareTo("CPL")==0) msg = jdbc.getcpl();
						else if (msgType.compareTo("EST")==0) msg = jdbc.getest();
						else if (msgType.compareTo("ACP")==0) msg = jdbc.getacp();
						else if (msgType.compareTo("LAM")==0) msg = jdbc.getlam();
						else if (msgType.compareTo("RQP")==0) msg = jdbc.getrqp();
						else if (msgType.compareTo("RQS")==0) msg = jdbc.getrqs();
						else if (msgType.compareTo("SPL")==0) msg = jdbc.getspl();
						
						if (msgall.compareTo(msg)!=0) {
							printable+=msg+line+"\n";
						} else {
							printable+=msg+line+"\n";
						}
						
						allMsgType+=","+msgType;
					}
					
					if (allMsgType.contains(",")) {
						int index = allMsgType.lastIndexOf(",");
						allMsgType = allMsgType.substring(0, index);
					}

					String titleview="";
					if (allMsgType.contains(",")) titleview = "View Selected Message(s)"; else titleview="";
					
					if (msgType.compareTo("FREETEXT")==0 || msgType.compareTo("FREE TEXT")==0) vATS.vfree(printable,titleview,strr);
					else if (msgType.compareTo("ALR")==0) vATS.valr(printable,titleview,strr);
					else if (msgType.compareTo("RCF")==0) vATS.vrcf(printable,titleview,strr);
					else if (msgType.compareTo("FPL")==0) vATS.vfpl(printable,titleview,strr);
					else if (msgType.compareTo("DLA")==0) vATS.vdla(printable,titleview,strr);
					else if (msgType.compareTo("CHG")==0) vATS.vchg(printable,titleview,strr);
					else if (msgType.compareTo("CNL")==0) vATS.vcnl(printable,titleview,strr);
					else if (msgType.compareTo("DEP")==0) vATS.vdep(printable,titleview,strr);
					else if (msgType.compareTo("ARR")==0) vATS.varr(printable,titleview,strr);
					else if (msgType.compareTo("CDN")==0) vATS.vcdn(printable,titleview,strr);
					else if (msgType.compareTo("CPL")==0) vATS.vcpl(printable,titleview,strr);
					else if (msgType.compareTo("EST")==0) vATS.vest(printable,titleview,strr);
					else if (msgType.compareTo("ACP")==0) vATS.vacp(printable,titleview,strr);
					else if (msgType.compareTo("LAM")==0) vATS.vlam(printable,titleview,strr);
					else if (msgType.compareTo("RQP")==0) vATS.vrqp(printable,titleview,strr);
					else if (msgType.compareTo("RQS")==0) vATS.vrqs(printable,titleview,strr);
					else if (msgType.compareTo("SPL")==0) vATS.vspl(printable,titleview,strr);
				} //end of else
			}
		});
		
				
		Edit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				openEdit();
			}
		});

		Delete.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selection = table.getSelection(); 
				if (selection.length==0 || sqlSelect.equals("")) { DialogFactory.openInfoAtLeast(Shells.sh_tOutbox,DialogFactory.delete); } 
				else {
					string="";
					tblName="";
					
					DialogFactory.openYesNoDelete(Shells.sh_tOutbox);
					boolean tentu = DialogFactory.getPenentuan(); 
					if (tentu == true) {
						for (int i=0; i<selection.length; i++) { //banyak baris yang akan dihapus
							tblName = selection[i].getText(10);
							string = selection[i].getText(0);
							dateTime = selection[i].getText(8);
							
							//table di buat per bulan
							if (tblName.startsWith("air_message_free_text")) { id_tbl = "id_free_text='"; } 
							else if (tblName.startsWith("air_message")) { id_tbl = "id_ats='"; }
							else if (tblName.startsWith("rpl_content")) { id_tbl = "id_rpl_cont='"; }
							
							String update = "UPDATE "+tblName+" SET status='deleted by user' WHERE "+id_tbl+string+"' AND tbl_name='"+tblName+"' AND tgl LIKE '"+dateTime+"'";
							jdbc.update(update);
							String del = "DELETE FROM check_status WHERE id='"+string+"' AND tbl_name='"+tblName+"' AND tgl LIKE '"+dateTime+"'";
							jdbc.delete(del);
						}
							
						if (getJumlah() > 1) {
							string=""; sqlSelect="";
							table.removeAll(); playersOUTBOX_ATS.clear();
							RefreshTable.refreshTableOutbox(pAircraftId);
						} else if (getJumlah() == 1) {
							string=""; sqlSelect=""; shell.close();
						}
						TriggerAction.trigger("Queu,"+Integer.toString(MainForm.query(queryQueue)), 101); //TriggerAction.trigger("elsa", 105);
					} //end of if
					
					setTableFocus();
				}
			}
		});
		
		DeleteAll.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					DialogFactory.openYesNoDelete(Shells.sh_tOutbox);
					boolean tentu = DialogFactory.getPenentuan(); 
					if (tentu == true) {
						if (!pAircraftId.isEmpty()) strAcftId = " AND aircraft_id LIKE '"+pAircraftId+"%'"; else strAcftId=""; 
						lihatsql = queryQueueAcft+strAcftId+" ORDER BY id DESC, tgl DESC";//"SELECT * FROM check_status WHERE tgl != '0000-00-00 00:00:00' AND (tbl_name LIKE 'air_message%' OR tbl_name LIKE 'air_message_free_text%') AND tbl_name NOT LIKE 'air_message_free_text_meteo%' AND (status='waiting' OR status='not sent')"+strAcftId+" ORDER BY tgl DESC";
						System.out.println("\nQuery: " + lihatsql);
						stmt = conn.createStatement();
						rs = stmt.executeQuery(lihatsql);
						rs.last();
						jumlah = rs.getRow();
						rs.beforeFirst();
						
						rowNo = 0;
						while (rs.next()) {
							rowNo++;
							String string = rs.getString("id"); if (string==null) { string=""; }
							String dateTime = rs.getString("tgl"); if (dateTime==null){ dateTime=""; } else dateTime = dateTime.substring(0, 19);
							String tblName = rs.getString("tbl_name"); if (tblName==null) { tblName=""; }
	
							//table di buat per bulan
							if (tblName.startsWith("air_message_free_text")) { id_tbl = "id_free_text='"; } 
							else if (tblName.startsWith("air_message")) { id_tbl = "id_ats='"; }
							else if (tblName.startsWith("rpl_content")) { id_tbl = "id_rpl_cont='"; }
							
							String update = "UPDATE "+tblName+" SET status='deleted by user' " +
									"WHERE "+id_tbl+string+"' AND tbl_name='"+tblName+"' AND tgl LIKE '"+dateTime+"'";
							jdbc.update(update);
							String del = "DELETE FROM check_status WHERE id='"+string+"' AND tbl_name='"+tblName+"' " +
									"AND tgl LIKE '"+dateTime+"'";
							jdbc.delete(del);
							TriggerAction.trigger("Queu,"+Integer.toString(MainForm.query(queryQueue)), 101);  //TriggerAction.trigger("elsa", 105);
							
						}
						if (getJumlah() > 1) {
							string=""; sqlSelect="";
							table.removeAll(); playersOUTBOX_ATS.clear();
							RefreshTable.refreshTableOutbox(pAircraftId);
						} else if (getJumlah() == 1) {
							string=""; sqlSelect=""; shell.close();
						}
					} //end of if
					setTableFocus();
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
  		View.setEnabled(b);
  		Edit.setEnabled(b);
  		Delete.setEnabled(b);
  		DeleteAll.setEnabled(b);
  		
  		first.setEnabled(b);
    	prev.setEnabled(b);
        next.setEnabled(b);
        last.setEnabled(b);
        tGoToHal.setEnabled(b);
  	}
  	
  	void openEdit() {
  		string2="";
		if ((selection.length == 0) || (sqlSelect.equals(""))) {
			DialogFactory.openInfoAtLeast(Shells.sh_tOutbox,DialogFactory.edit);
		} else if (selection.length > 1) {
			DialogFactory.openInfoEditMode(Shells.sh_tOutbox,DialogFactory.edit);
		} else if (selection.length == 1) {
//			System.out.println("\ntblName=" + tblName + "*\tType:"+msgType+"*\tId:"+id_ats);
			jdbc.select2(GetSqlSelect(), tblName);

			if (msgType.compareToIgnoreCase("FREE TEXT")==0 && sNotes.compareToIgnoreCase("amo")==0) { 
				RefreshTable.refreshNewAMO("OutAMO",strr); ATSForms.setCurrentAMO(); }
			else if (msgType.compareToIgnoreCase("FREE TEXT")==0 && sNotes.compareToIgnoreCase("freetext")==0) { 
				RefreshTable.refreshNewFREE("OutFREE",strr); ATSForms.setCurrentFREE(); }
			else if (msgType.compareToIgnoreCase("FREETEXT")==0 || msgType.compareToIgnoreCase("FREE TEXT")==0 || 
					msgType.compareToIgnoreCase("")==0) { 
				RefreshTable.refreshNewAFTN("OutAFTN",strr); ATSForms.setCurrentAFTN(); HeaderComposite.tSendAtAFTN.setText(jdbc.getSendAt()); }
			else if (msgType.compareToIgnoreCase("ALR")==0) { 
				RefreshTable.refreshNewALR("OutALR",strr); ATSForms.setCurrentALR(); HeaderComposite.tSendAtALR.setText(jdbc.getSendAt()); }
			else if (msgType.compareToIgnoreCase("RCF")==0) { 
				RefreshTable.refreshNewRCF("OutRCF",strr); ATSForms.setCurrentRCF(); HeaderComposite.tSendAtRCF.setText(jdbc.getSendAt()); }
			else if (msgType.compareToIgnoreCase("FPL")==0) { 
				RefreshTable.refreshNewFPL("OutFPL",strr); ATSForms.setCurrentFPL(); HeaderComposite.tSendAtFPL.setText(jdbc.getSendAt()); }
			else if (msgType.compareToIgnoreCase("DLA")==0) { 
				RefreshTable.refreshNewDLA("OutDLA",strr); ATSForms.setCurrentDLA(); HeaderComposite.tSendAtDLA.setText(jdbc.getSendAt()); }
			else if (msgType.compareToIgnoreCase("CHG")==0) {
				RefreshTable.refreshNewCHG("OutCHG",strr); ATSForms.setCurrentCHG(); HeaderComposite.tSendAtCHG.setText(jdbc.getSendAt()); }
			else if (msgType.compareToIgnoreCase("CNL")==0) { 
				RefreshTable.refreshNewCNL("OutCNL",strr); ATSForms.setCurrentCNL(); HeaderComposite.tSendAtCNL.setText(jdbc.getSendAt()); }
			else if (msgType.compareToIgnoreCase("DEP")==0) {
				RefreshTable.refreshNewDEP("OutDEP",strr); ATSForms.setCurrentDEP(); HeaderComposite.tSendAtDEP.setText(jdbc.getSendAt()); }
			else if (msgType.compareToIgnoreCase("ARR")==0) { 
				RefreshTable.refreshNewARR("OutARR",strr); ATSForms.setCurrentARR(); HeaderComposite.tSendAtARR.setText(jdbc.getSendAt()); }
			else if (msgType.compareToIgnoreCase("CDN")==0) { 
				RefreshTable.refreshNewCDN("OutCDN",strr); ATSForms.setCurrentCDN(); HeaderComposite.tSendAtCDN.setText(jdbc.getSendAt()); }
			else if (msgType.compareToIgnoreCase("CPL")==0) { 
				RefreshTable.refreshNewCPL("OutCPL",strr); ATSForms.setCurrentCPL(); HeaderComposite.tSendAtCPL.setText(jdbc.getSendAt()); }
			else if (msgType.compareToIgnoreCase("EST")==0) {
				RefreshTable.refreshNewEST("OutEST",strr); ATSForms.setCurrentEST(); HeaderComposite.tSendAtEST.setText(jdbc.getSendAt()); }
			else if (msgType.compareToIgnoreCase("ACP")==0) { 
				RefreshTable.refreshNewACP("OutACP",strr); ATSForms.setCurrentACP(); HeaderComposite.tSendAtACP.setText(jdbc.getSendAt()); }
			else if (msgType.compareToIgnoreCase("LAM")==0) { 
				RefreshTable.refreshNewLAM("OutLAM",strr); ATSForms.setCurrentLAM(); HeaderComposite.tSendAtLAM.setText(jdbc.getSendAt()); }
			else if (msgType.compareToIgnoreCase("RQP")==0) { 
				RefreshTable.refreshNewRQP("OutRQP",strr); ATSForms.setCurrentRQP(); HeaderComposite.tSendAtRQP.setText(jdbc.getSendAt()); }
			else if (msgType.compareToIgnoreCase("RQS")==0) { 
				RefreshTable.refreshNewRQS("OutRQS",strr); ATSForms.setCurrentRQS(); HeaderComposite.tSendAtRQS.setText(jdbc.getSendAt()); }
			else if (msgType.compareToIgnoreCase("SPL")==0) { 
				RefreshTable.refreshNewSPL("OutSPL",strr); ATSForms.setCurrentSPL(); HeaderComposite.tSendAtSPL.setText(jdbc.getSendAt()); }
			
			sqlSelect = "";
		} else { DialogFactory.openInfoAtLeast(Shells.sh_tOutbox,DialogFactory.edit); } 
		
		setString2(string);
		string="";
  	}
  	
  	@SuppressWarnings("unchecked")
	private void fillTable(Table table) {
	    // Turn off drawing to avoid flicker
	    table.setRedraw(false);
        
	    // We remove all the table entries, sort our rows, then add the entries
	    table.removeAll();
	    
	    Collections.sort(playersOUTBOX_ATS, comparatorOUTBOX_ATS);
	    for (Iterator itr = playersOUTBOX_ATS.iterator(); itr.hasNext();) {
	    	PlayerOUTBOX_ATS player = (PlayerOUTBOX_ATS) itr.next();
	    	TableItem item = new TableItem(table, SWT.NONE);
	    	int c = 0;
	    	item.setText(c++, player.getID());
	    	item.setText(c++, player.getTYPE());
	    	item.setText(c++, player.getFILING_TIME());
	    	item.setText(c++, player.getSEND_AT());
	    	item.setText(c++, player.getORIGINATOR());
	    	item.setText(c++, player.getAIRCRAFT());
	    	item.setText(c++, player.getMSG());
	    	item.setText(c++, player.getSTATUS());
	    	item.setText(c++, player.getTANGGAL());
	    	item.setText(c++, player.getFILEDBY());
	    	item.setText(c++, player.getTBL_NAME());
	    	item.setText(c++, player.getNOTES());
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
	public static String GetTblName() { return tblName; }
	public static String GetDateTime() { return dateTime; }

	void setMsgType(String str) { sType= str; }
	public static String GetMsgType() { return sType; }
	
//	private static String getSele() { return lihatsql; }
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
}

/**
 * This class represents a player.
 */
class PlayerOUTBOX_ATS {
	private String ID;
	private String TYPE;
	private String FILING_TIME; 
	private String SEND_AT; 
	private String ORIGINATOR; 
	private String AIRCRAFT;
	private String MSG;
	private String STATUS; 
	private String TANGGAL;
	private String FILEDBY;
	private String TBL_NAME;
	private String NOTES;
	 
	public PlayerOUTBOX_ATS(String ID,String TYPE,String FILING_TIME,String SEND_AT,String ORIGINATOR,String AIRCRAFT,String MSG,
			String STATUS,String TANGGAL,String FILEDBY,String TBL_NAME,String NOTES) {
		this.ID = ID;
		this.TYPE = TYPE;
		this.FILING_TIME = FILING_TIME;
		this.SEND_AT = SEND_AT;
		this.ORIGINATOR = ORIGINATOR;
		this.AIRCRAFT = AIRCRAFT;
		this.MSG = MSG;
		this.STATUS = STATUS;
		this.TANGGAL = TANGGAL;
		this.FILEDBY = FILEDBY;
		this.TBL_NAME = TBL_NAME;
		this.NOTES = NOTES;
	}

	public String getID() { return ID; }
	public String getTYPE() { return TYPE; }
	public String getFILING_TIME() { return FILING_TIME; }
	public String getSEND_AT() { return SEND_AT; }
	public String getORIGINATOR() { return ORIGINATOR; }
	public String getAIRCRAFT() { return AIRCRAFT; }
	public String getMSG() { return MSG; }
	public String getSTATUS() { return STATUS; }
	public String getTANGGAL() { return TANGGAL; }
	public String getFILEDBY() { return FILEDBY; }
	public String getTBL_NAME() { return TBL_NAME; }
	public String getNOTES() { return NOTES; }
}

@SuppressWarnings("unchecked")
class PlayerComparatorOUTBOX_ATS implements Comparator {
	public static final int ID = 0;
	public static final int TYPE = 1;
	public static final int FILING_TIME = 2;
	public static final int SEND_AT = 3;
	public static final int ORIGINATOR = 4;
	public static final int AIRCRAFT = 5;
	public static final int MSG = 6;
	public static final int STATUS = 7;
	public static final int TANGGAL = 8;
	public static final int FILEDBY = 9;
	public static final int TBL_NAME = 10;
	public static final int NOTES = 11;
	
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
	    PlayerOUTBOX_ATS p1 = (PlayerOUTBOX_ATS) obj1;
	    PlayerOUTBOX_ATS p2 = (PlayerOUTBOX_ATS) obj2;
	
	    // Determine which field to sort on, then sort on that field
	    switch (column) {
		    case ID: rc = p1.getID().compareTo(p2.getID()); break;
		    case TYPE: rc = p1.getTYPE().compareTo(p2.getTYPE()); break;
		    case FILING_TIME: rc = p1.getFILING_TIME().compareTo(p2.getFILING_TIME()); break;
		    case SEND_AT: rc = p1.getSEND_AT().compareTo(p2.getSEND_AT()); break;
		    case ORIGINATOR: rc = p1.getORIGINATOR().compareTo(p2.getORIGINATOR()); break;
		    case AIRCRAFT: rc = p1.getAIRCRAFT().compareTo(p2.getAIRCRAFT()); break;
		    case MSG: rc = p1.getMSG().compareTo(p2.getMSG()); break;	
		    case STATUS: rc = p1.getSTATUS().compareTo(p2.getSTATUS()); break;
		    case TANGGAL: rc = p1.getTANGGAL().compareTo(p2.getTANGGAL()); break;
		    case FILEDBY: rc = p1.getFILEDBY().compareTo(p2.getFILEDBY()); break;
		    case TBL_NAME: rc = p1.getTBL_NAME().compareTo(p2.getTBL_NAME()); break;
		    case NOTES: rc = p1.getNOTES().compareTo(p2.getNOTES()); break;
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


//DeleteAll.addSelectionListener(new SelectionAdapter() {
//	public void widgetSelected(SelectionEvent e) {
//		//memilih baris yang akan di hapus -update 20101109
//		table.selectAll();
//		selection = table.getSelection(); 
//		
//		if (getJumlah() == 0) {
//			DialogFactory.openInfoDialog(Shells.sh_tOutbox, "Delete Message", "There's no data !!");
//		} else {
//			DialogFactory.openYesNoDialog(Shells.sh_tOutbox, "Delete Message", "Are you sure ?");
//			boolean tentu = DialogFactory.getPenentuan();
//			if (tentu == true) {
//				try { //biar kalo ada table yang doesn't exist ga mati.
//					rs = stmt.executeQuery(lihatsql);
//
//					while (rs.next()) {
//						rowNo++;
//						
//						tblName = rs.getString("tbl_name");
//						string = rs.getString("id");
//						dateTime = rs.getString("tgl").substring(0, 16);
//						
//						//table di buat per bulan
//						if (tblName.startsWith("air_message_free_text")) { id_tbl = "id_free_text='"; } 
//						else if (tblName.startsWith("air_message")) { id_tbl = "id_ats='"; }
//						else if (tblName.startsWith("rpl_content")) { id_tbl = "id_rpl_cont='"; }
//
//						String update = "UPDATE "+tblName+" SET status='deleted by user' WHERE "+id_tbl+string+"' AND tbl_name='"+tblName+"' AND tgl LIKE '"+dateTime+"%'";
//						jdbc.update(update);
//						
//					} //end of while
//					
//					if (lihatsql.contains("SELECT * FROM")) lihatsql = lihatsql.replace("SELECT * FROM", "DELETE FROM");
//					jdbc.delete(lihatsql);
//					//buat ng'refreshnya
//					table.removeAll(); playersOUTBOX_ATS.clear(); shell.close();
//					TriggerAction.trigger("Queu", 101); //TriggerAction.trigger("elsa", 105);
////					TriggerAction.trigger("Queu", 101);
//				} catch (SQLException s) { // biar kalo ada table yang doesn't exist ga mati.
//					System.out.println("Error: " + s.getMessage());
//				}
//			} //end of if
//			else if (tentu == false) { string=""; sqlSelect=""; }
//		}
//	}
//});