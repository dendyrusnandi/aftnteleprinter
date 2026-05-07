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

import setting.ATSComponentSetting;
import setting.ButtonsSetting;
import setting.Colors;
import setting.Shells;
import setting.Shorten;
import setting.TextSetting;
import actions.RefreshTable;
import actions.cProgressBar;
import databases.jdbc;
import displays.DialogFactory;
import displays.MainForm;
import displays.TeleSplashScreen2016IP;


public class TableRoute { 
	@SuppressWarnings("unchecked")
	private java.util.List playersRoute;
	private PlayerComparatorRoute comparatorRoute;
	
	Shorten sh = new Shorten();
	ButtonsSetting bs = new ButtonsSetting();
	TextSetting ts = new TextSetting();

	static String P0,P1,P2,P3,P4,id,sqlSelect,string,select,sele,isiHal,jml_item="",string2="";
	static String des1,des2,des3,des4,des5,des6,des7,des8,des9,des10,des11,des12,des13,des14,des15,des16,des17,des18,des19,des20,des21;
	String t13a,t16a;
	
	static int baris=20,qq=0,jumlah=0,rowNo=0,qu=0;
	int dir=0,p=0,yu=0,hasil=0;
	
	Shell shell;
	Table table;
	TableItem[] selection;
	Button next,prev,first,last,Edit,Delete,DeleteAll;
	Text tPage,tTotPage,tGoToHal,tDepAd,tDestAd,tRoute,tDest1,tDest2,tDest3,tDest4,tDest5,tDest6,tDest7,tDest8,tDest9,tDest10,tDest11,
	tDest12,tDest13,tDest14,tDest15,tDest16,tDest17,tDest18,tDest19,tDest20,tDest21;
	
	Connection conn = null; // connection object
    Statement stmt = null; // statement object
    ResultSet rs = null; // result set object
	
    
  	public TableRoute() { }

	public void koneksiDB(Shell pShell, String pt13a, String pt16a) {
		shell=pShell;
		t13a=pt13a;
		t16a=pt16a;
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
						    // Create the comparatorRoute used for sorting
						    comparatorRoute = new PlayerComparatorRoute();
						    comparatorRoute.setColumn(PlayerComparatorRoute.ID);
						    comparatorRoute.setDirection(PlayerComparatorRoute.ASCENDING);
						    
						    // Create the playersRoute
						    playersRoute = new ArrayList();
							
							String s13a="",s16a="",and="",where="";
							if (t13a.compareTo("")!=0) s13a="type13a LIKE '"+t13a+"%'"; else s13a="";
							if (t16a.compareTo("")!=0) s16a="type16a LIKE '"+t16a+"%'"; else s16a="";
							if (!s13a.isEmpty() && !s16a.isEmpty()) { and=" AND "; } else { and=""; }
							if (!s13a.isEmpty() || !s16a.isEmpty()) { where=" WHERE "; } else { where=""; }
							
							stmt = conn.createStatement();
							String lihatsql = sele = "SELECT * FROM route"+where+s13a+and+s16a+" ORDER BY id_number DESC";
							System.out.println("\nQuery: " + lihatsql);
							rs = stmt.executeQuery(lihatsql);
							rs.last();
							jumlah = rs.getRow();
							rs.beforeFirst();
							
							final String datatabel[][] = new String[baris][26];
							p = 0;
							rowNo = 0;
							while (rs.next() && (rowNo < baris)) {
								rowNo++;
								
								datatabel[p][0] = rs.getString("id_number"); id=datatabel[p][0]; if (id==null){ id=""; }
								datatabel[p][1] = rs.getString("type13a"); P1=datatabel[p][1]; if (P1==null){ P1=""; }
								datatabel[p][2] = rs.getString("type16a"); P2=datatabel[p][2]; if (P2==null){ P2=""; }
								datatabel[p][3] = rs.getString("type15c"); P3=datatabel[p][3]; if (P3==null){ P3=""; }
								
								des1=rs.getString("destination1"); if(des1==null) des1="";
								des2=rs.getString("destination2"); if(des2==null) des2="";
								des3=rs.getString("destination3"); if(des3==null) des3="";
								des4=rs.getString("destination4"); if(des4==null) des4="";
								des5=rs.getString("destination5"); if(des5==null) des5="";
								des6=rs.getString("destination6"); if(des6==null) des6="";
								des7=rs.getString("destination7"); if(des7==null) des7="";
								des8=rs.getString("destination8"); if(des8==null) des8="";
								des9=rs.getString("destination9"); if(des9==null) des9="";
								des10=rs.getString("destination10"); if(des10==null) des10="";
								des11=rs.getString("destination11"); if(des11==null) des11="";
								des12=rs.getString("destination12"); if(des12==null) des12="";
								des13=rs.getString("destination13"); if(des13==null) des13="";
								des14=rs.getString("destination14"); if(des14==null) des14="";
								des15=rs.getString("destination15"); if(des15==null) des15="";
								des16=rs.getString("destination16"); if(des16==null) des16="";
								des17=rs.getString("destination17"); if(des17==null) des17="";
								des18=rs.getString("destination18"); if(des18==null) des18="";
								des19=rs.getString("destination19"); if(des19==null) des19="";
								des20=rs.getString("destination20"); if(des20==null) des20="";
								des21=rs.getString("destination21"); if(des21==null) des21="";
								
								p++;
								playersRoute.add(new PlayerRoute(id,P1,P2,P3,des1,des2,des3,des4,des5,des6,des7,des8,des9,des10,des11,des12,des13,des14,des15,des16,des17,des18,des19,des20,des21));
								progbr.bar.setSelection(rowNo);
							}
							progbr.finish();
							
//							if (getJumlah() != 0) {
								if (getJumlah()==0) DialogFactory.openInfoDialog(Shells.sh_tRoute, "Search Message","Data not found, your search has no result !!");
								formSearch();
						  		formList();
						  		formAddEdit();
							    sh.shellStyle(shell, "Route");
							    shell.addListener(SWT.Close, new Listener() {
									public void handleEvent(Event event) {
										connClose();
									}
								});
//					  		} else {
//					  			DialogFactory.openInfoDialog(Shells.sh_tRoute, "Search Message","Data not found, your search has no result !!");
//					  		}
	        			} catch (SQLException s) {
							DialogFactory.openInfoDialog(Shells.sh_tRoute, "Search Message", s.toString());
							s.printStackTrace();
							progbr.finish();
						} catch (java.lang.OutOfMemoryError hs) {
							DialogFactory.openInfoDialog(Shells.sh_tRoute, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
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
							rs.last();
							jumlah = rs.getRow();
							rs.beforeFirst();
							
							final String datatabel[][] = new String[baris][26];
							for (rowNo=0; rowNo<(qq*baris); rowNo++) rs.next();
							
							p = 0;
							rowNo = 0;
							playersRoute.clear();
							while (rs.next() && (rowNo < baris)) {
								rowNo++;
								
								datatabel[p][0] = rs.getString("id_number"); id=datatabel[p][0]; if (id==null){ id=""; }
								datatabel[p][1] = rs.getString("type13a"); P1=datatabel[p][1]; if (P1==null){ P1=""; }
								datatabel[p][2] = rs.getString("type16a"); P2=datatabel[p][2]; if (P2==null){ P2=""; }
								datatabel[p][3] = rs.getString("type15c"); P3=datatabel[p][3]; if (P3==null){ P3=""; }
								
								des1=rs.getString("destination1"); if(des1==null) des1="";
								des2=rs.getString("destination2"); if(des2==null) des2="";
								des3=rs.getString("destination3"); if(des3==null) des3="";
								des4=rs.getString("destination4"); if(des4==null) des4="";
								des5=rs.getString("destination5"); if(des5==null) des5="";
								des6=rs.getString("destination6"); if(des6==null) des6="";
								des7=rs.getString("destination7"); if(des7==null) des7="";
								des8=rs.getString("destination8"); if(des8==null) des8="";
								des9=rs.getString("destination9"); if(des9==null) des9="";
								des10=rs.getString("destination10"); if(des10==null) des10="";
								des11=rs.getString("destination11"); if(des11==null) des11="";
								des12=rs.getString("destination12"); if(des12==null) des12="";
								des13=rs.getString("destination13"); if(des13==null) des13="";
								des14=rs.getString("destination14"); if(des14==null) des14="";
								des15=rs.getString("destination15"); if(des15==null) des15="";
								des16=rs.getString("destination16"); if(des16==null) des16="";
								des17=rs.getString("destination17"); if(des17==null) des17="";
								des18=rs.getString("destination18"); if(des18==null) des18="";
								des19=rs.getString("destination19"); if(des19==null) des19="";
								des20=rs.getString("destination20"); if(des20==null) des20="";
								des21=rs.getString("destination21"); if(des21==null) des21="";
								
								TableItem item = new TableItem (table, SWT.NONE);
								item.setText (0,id); 
								item.setText (1,P1);
								item.setText (2,P2); 
								item.setText (3,P3);

								item.setText (4,des1); 
								item.setText (5,des2);
								item.setText (6,des3);
								item.setText (7,des4);
								item.setText (8,des5);
								item.setText (9,des6);
								item.setText (10,des7);
								
								item.setText (11,des8);
								item.setText (12,des9);
								item.setText (13,des10);
								item.setText (14,des11); 
								item.setText (15,des12);
								item.setText (16,des13);
								item.setText (17,des14);
								
								item.setText (18,des15); 
								item.setText (19,des16);
								item.setText (20,des17); 
								item.setText (21,des18);
								item.setText (22,des19); 
								item.setText (23,des20);
								item.setText (24,des21); 
								
								p++;
								playersRoute.add(new PlayerRoute(id,P1,P2,P3,des1,des2,des3,des4,des5,des6,des7,des8,des9,des10,des11,des12,des13,des14,des15,des16,des17,des18,des19,des20,des21));
								progbr.bar.setSelection(rowNo);
							}
							progbr.finish();
	        			} catch (SQLException s) {
							DialogFactory.openInfoDialog(Shells.sh_tRoute, "Search Message", s.toString());
							s.printStackTrace();
							progbr.finish();
						} catch (java.lang.OutOfMemoryError hs) {
							DialogFactory.openInfoDialog(Shells.sh_tRoute, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
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
  		final Group grpSearch = new Group(shell, SWT.NONE); sh.groupStyle(grpSearch, 8, "Search Route");
		Label label = new Label(grpSearch,SWT.NONE); sh.labelStyle1(label, "DEP AD", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		final Text tDepAd_Search = new Text(grpSearch,SWT.BORDER); sh.textStyle(tDepAd_Search, 50, 4, SWT.LEFT, SWT.LEFT, sh.letter, ATSComponentSetting.b13a, true);
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle1(label, "DEST AD", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		final Text tDestAd_Search = new Text(grpSearch,SWT.BORDER); sh.textStyle(tDestAd_Search, 50, 4, SWT.LEFT, SWT.LEFT, sh.letter, ATSComponentSetting.b16a, true); 
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Button Search = new Button(grpSearch, SWT.PUSH);
		Button Clear = new Button(grpSearch, SWT.PUSH);
		bs.SC(Search, Clear);

		if (!t13a.isEmpty()) tDepAd_Search.setText(t13a);
		if (!t16a.isEmpty()) tDestAd_Search.setText(t16a);
		
		tDepAd_Search.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					RefreshTable.refreshTableRoute(tDepAd_Search.getText(), tDestAd_Search.getText());
				}
			}
	    });
		
		tDestAd_Search.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					RefreshTable.refreshTableRoute(tDepAd_Search.getText(), tDestAd_Search.getText());
				}
			}
	    });
		
		Search.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RefreshTable.refreshTableRoute(tDepAd_Search.getText(), tDestAd_Search.getText());
			}
		});
		
		Clear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				tDepAd_Search.setText(""); tDestAd_Search.setText("");
				RefreshTable.refreshTableRoute(tDepAd_Search.getText(), tDestAd_Search.getText());
			}
		});	
	}
	
  	private void formList() {
  		final Group Group = new Group(shell, SWT.NONE); sh.groupStyle(Group, 1, "List of Route(s)");
	    // Create the table
	    table = new Table(Group, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION); sh.tableStyle(table, true, true, 200, MainForm.getWidthRightTop());//1230);
	    // Create each of the columns, adding an event listener 
	    // that will set the appropriate fields into the comparatorRoute 
	    // and then call the fillTable helper method
	    final TableColumn ID = new TableColumn(table,SWT.LEFT); sh.tablecol(ID, "id", "", 0, false);
		ID.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorRoute.setColumn(PlayerComparatorRoute.ID);
				comparatorRoute.reverseDirection();
				fillTable(table);
			}
		});
		
		final TableColumn DEP_AD = new TableColumn(table,SWT.LEFT); sh.tablecol(DEP_AD, "DEP AD", "Departure Aerodrome", 90, false);
		DEP_AD.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEP_AD);
		    	comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn DEST_AD = new TableColumn(table,SWT.LEFT); sh.tablecol(DEST_AD, "DEST AD", "Destination Aerodrome", 90, false);
		DEST_AD.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST_AD);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn ROUTE = new TableColumn(table,SWT.LEFT); sh.tablecol(ROUTE, "ROUTE", "Route", 235, false);
		ROUTE.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.ROUTE);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn ADDRESS1 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS1, "Address 1", "Address 1", 120, false);
		ADDRESS1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST1);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn ADDRESS2 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS2, "Address 2", "Address 2", 120, false);
		ADDRESS2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST2);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn ADDRESS3 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS3, "Address 3", "Address 3", 120, false);
		ADDRESS3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST3);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn ADDRESS4 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS4, "Address 4", "Address 4", 120, false);
		ADDRESS4.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST4);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn ADDRESS5 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS5, "Address 5", "Address 5", 120, false);
		ADDRESS5.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST5);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn ADDRESS6 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS6, "Address 6", "Address 6", 120, false);
		ADDRESS6.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST6);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn ADDRESS7 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS7, "Address 7", "Address 7", 120, false);
		ADDRESS7.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST7);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn ADDRESS8 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS8, "Address 8", "Address 8", 120, false);
		ADDRESS8.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST8);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn ADDRESS9 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS9, "Address 9", "Address 9", 120, false);
		ADDRESS9.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST9);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn ADDRESS10 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS10, "Address 10", "Address 10", 120, false);
		ADDRESS10.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST10);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn ADDRESS11 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS11, "Address 11", "Address 11", 120, false);
		ADDRESS11.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST11);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn ADDRESS12 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS12, "Address 12", "Address 12", 120, false);
		ADDRESS12.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST12);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn ADDRESS13 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS13, "Address 13", "Address 13", 120, false);
		ADDRESS13.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST13);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn ADDRESS14 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS14, "Address 14", "Address 14", 120, false);
		ADDRESS14.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST14);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn ADDRESS15 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS15, "Address 15", "Address 15", 120, false);
		ADDRESS15.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST15);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn ADDRESS16 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS16, "Address 16", "Address 16", 120, false);
		ADDRESS16.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST16);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn ADDRESS17 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS17, "Address 17", "Address 17", 120, false);
		ADDRESS17.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST17);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});
		

		final TableColumn ADDRESS18 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS18, "Address 18", "Address 18", 120, false);
		ADDRESS18.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST18);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn ADDRESS19 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS19, "Address 19", "Address 19", 120, false);
		ADDRESS19.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST19);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn ADDRESS20 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS20, "Address 20", "Address 20", 120, false);
		ADDRESS20.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST20);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn ADDRESS21 = new TableColumn(table,SWT.LEFT); sh.tablecol(ADDRESS21, "Address 21", "Address 21", 120, false);
		ADDRESS21.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorRoute.setColumn(PlayerComparatorRoute.DEST21);
		        comparatorRoute.reverseDirection();
		        fillTable(table);
			}
		});
		
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
	    DEP_AD.addListener(SWT.Selection, sortListener);
	    DEST_AD.addListener(SWT.Selection, sortListener);
	    ROUTE.addListener(SWT.Selection, sortListener);

	    ADDRESS1.addListener(SWT.Selection, sortListener);
	    ADDRESS2.addListener(SWT.Selection, sortListener);
	    ADDRESS3.addListener(SWT.Selection, sortListener);
	    ADDRESS4.addListener(SWT.Selection, sortListener);
	    ADDRESS5.addListener(SWT.Selection, sortListener);
	    ADDRESS6.addListener(SWT.Selection, sortListener);
	    ADDRESS7.addListener(SWT.Selection, sortListener);
	    
	    ADDRESS8.addListener(SWT.Selection, sortListener);
	    ADDRESS9.addListener(SWT.Selection, sortListener);
	    ADDRESS10.addListener(SWT.Selection, sortListener);
	    ADDRESS11.addListener(SWT.Selection, sortListener);
	    ADDRESS12.addListener(SWT.Selection, sortListener);
	    ADDRESS13.addListener(SWT.Selection, sortListener);
	    ADDRESS14.addListener(SWT.Selection, sortListener);
	    
	    ADDRESS15.addListener(SWT.Selection, sortListener);
	    ADDRESS16.addListener(SWT.Selection, sortListener);
	    ADDRESS17.addListener(SWT.Selection, sortListener);
	    ADDRESS18.addListener(SWT.Selection, sortListener);
	    ADDRESS19.addListener(SWT.Selection, sortListener);
	    ADDRESS20.addListener(SWT.Selection, sortListener);
	    ADDRESS21.addListener(SWT.Selection, sortListener);
	    
	    table.setSortColumn(ID);
	    table.setSortDirection(SWT.UP);
	      
	    Composite crow = new Composite(Group, SWT.NONE); sh.compositeStyle(crow, 1, SWT.RIGHT, SWT.CENTER);
	    Label lrow = new Label(crow, SWT.NONE); 
		sh.labelStyle1(lrow, getJumlah() + " Element(s) in this table ", SWT.LEFT, SWT.CENTER, SWT.ITALIC | SWT.BOLD, Colors.MANDATORY);
		jdbc.setJmlData(getJumlah());

		Composite group = new Composite(Group, SWT.NONE); sh.compositeStyle(group, 13, SWT.CENTER, SWT.CENTER);
		
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
		
		bs.EDD(Edit, Delete, DeleteAll);
		bs.Navigation(tPage, tTotPage, first, prev, next, last, tGoToHal);
		
		// To get ID of the row
	    table.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				string = "";
				TableItem [] selection = table.getSelection();
				for (int i=0; i<selection.length; i++) 
					string += selection [i] + " ";
	
				if (string.contains("TableItem {")){
					string=string.replace("TableItem {","");
					if (string.contains("}")){
						string=string.replaceAll("} ", "");
					}
					sqlSelect="SELECT * FROM route WHERE id_number='"+string+"'";
				} 
			}
		});
	    
		table.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				openEdit();
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
				if (selection.length==0 || sqlSelect.equals("")) { DialogFactory.openInfoAtLeast(Shells.sh_tRoute,DialogFactory.delete); } 
				else {
					for (int i=0; i<selection.length; i++) { string += ","+selection [i]; }
		
					if (string.contains("}")) { string = string.replace("}", ""); }
					if (string.contains("TableItem {")) { string = string.replace("TableItem {", ""); }
					if (string.contains(",")) {
						String x = string;
						int len = x.length(); 
						int index = x.indexOf(","); 
						String id = string.substring(index+1, len);
						string = id; 
						
						if (string.contains(",")) {
							jml_item="banyak";
							string = string.replace(",", "' OR id_number='");
						} 
					} 
	
					if (string == null) string = "";
					if (string.equals("")) { DialogFactory.openInfoAtLeast(Shells.sh_tRoute,DialogFactory.delete); } 
					else {
						DialogFactory.openYesNoDelete(Shells.sh_tRoute);
						boolean tentu = DialogFactory.getPenentuan(); 
						if (tentu == true) {
							String del = "DELETE FROM route WHERE id_number='" + string +"'";
							jdbc.delete(del);
//							string=""; sqlSelect="";
//							table.removeAll(); playersRoute.clear(); RefreshTable.refreshTableRoute(t13a, t16a);
							if (getJumlah() > 1) {
								string=""; sqlSelect="";
								table.removeAll(); playersRoute.clear(); RefreshTable.refreshTableRoute(t13a, t16a);
							} else if (getJumlah() == 1) {
								string=""; sqlSelect=""; shell.close();
							}
						} else if (tentu == false) {
							string=""; sqlSelect=""; 
						}
					}
				}
			}
		});
		
		DeleteAll.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (getJumlah() == 0) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Delete Message", "There's no data !!"); }
				else {
					DialogFactory.openYesNoDelete(Shells.sh_tRoute);
					boolean tentu = DialogFactory.getPenentuan(); 
					if (tentu == true) {
						String dele = getSele().toString(); 
						dele = dele.replace("SELECT * FROM", "DELETE FROM");
						dele = dele.replace(" ORDER BY tgl DESC", "");
						jdbc.delete(dele);
						table.removeAll(); playersRoute.clear(); shell.close();
					} else if (tentu == false) {
						string=""; sqlSelect=""; 
					}
				}
			}
		});
		
		first.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (first.getEnabled() == true) {
					first.setEnabled(false); prev.setEnabled(false); next.setEnabled(true);
					if (last.getEnabled() == false) { last.setEnabled(true); }
				}
				table.removeAll();
				qq=0;
				isiHal = Integer.toString(qq);
				tPage.setText("1");
				tGoToHal.setText("1");
				Next();
				//pengosongan ID
				string=""; string2=""; sqlSelect=""; discard();
			}
		});
		
		prev.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (prev.getEnabled() == true) {
					next.setEnabled(true); first.setEnabled(true); last.setEnabled(true);
				}
				
				qq--;
				if (qq == 0) { prev.setEnabled(false); first.setEnabled(false); }
				
				if (qq < 0) { qq=0; } 
				else {
					table.removeAll();
					int as = Integer.parseInt(akhir().trim())-1;
					isiHal=Integer.toString(as);
					tPage.setText(isiHal);
					Next();	
				}
				tGoToHal.setText(isiHal);
				//pengosongan ID
				string=""; string2=""; sqlSelect=""; discard();
			}
		});
		
		next.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				prev.setEnabled(true); first.setEnabled(true);

				qq++;
				int jml;
				if (jumlah % baris == 0) { jml = jumlah/baris; } 
				else { jml = jumlah/baris+1; }
				
				int all = jml;
				if (all-1 == qq) { next.setEnabled(false); last.setEnabled(false); }
				
				if (qq < jml) {
					table.removeAll();
					int as = qq + 1;
					isiHal = Integer.toString(as);
					tPage.setText(isiHal);
					Next();
				}
				tGoToHal.setText(isiHal);
				//pengosongan ID
				string=""; string2=""; sqlSelect=""; discard();
			}
		});
						
		last.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				next.setEnabled(false); prev.setEnabled(true); first.setEnabled(true);
				
				table.removeAll();
				if (jumlah % baris == 0) qq = jumlah/baris - 1;
				else qq = jumlah/baris;
				
				isiHal = Integer.toString(qq+1);
				tPage.setText(isiHal);
				Next();
				last.setEnabled(false);
				tGoToHal.setText(isiHal);
				//pengosongan ID
				string=""; string2=""; sqlSelect=""; discard();
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
  		if (sqlSelect == null) { sqlSelect = ""; }
		if (sqlSelect != "") {
			jdbc.select2(GetSqlSelect(),"");
			String id = string;
			set(id);
			
			tDepAd.setText(jdbc.getDepER());
			tDestAd.setText(jdbc.getDestER());
			tRoute.setText(jdbc.getRouteER());
			
			tDest1.setText(jdbc.getDes1());
			tDest2.setText(jdbc.getDes2());
			tDest3.setText(jdbc.getDes3());
			tDest4.setText(jdbc.getDes4());
			tDest5.setText(jdbc.getDes5());
			tDest6.setText(jdbc.getDes6());
			tDest7.setText(jdbc.getDes7());
			
			tDest8.setText(jdbc.getDes8());
			tDest9.setText(jdbc.getDes9());
			tDest10.setText(jdbc.getDes10());
			tDest11.setText(jdbc.getDes11());
			tDest12.setText(jdbc.getDes12());
			tDest13.setText(jdbc.getDes13());
			tDest14.setText(jdbc.getDes14());
			
			tDest15.setText(jdbc.getDes15());
			tDest16.setText(jdbc.getDes16());
			tDest17.setText(jdbc.getDes17());
			tDest18.setText(jdbc.getDes18());
			tDest19.setText(jdbc.getDes19());
			tDest20.setText(jdbc.getDes20());
			tDest21.setText(jdbc.getDes21());
			
			//pengosongan ID
			sqlSelect=""; string="";
		} else { DialogFactory.openInfoAtLeast(Shells.sh_tRoute,DialogFactory.edit); } 
  	}
	
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
		//pengosongan ID
		string=""; string2=""; sqlSelect=""; discard();
	}
  	
  	@SuppressWarnings("unchecked")
	private void fillTable(Table table) {
	    // Turn off drawing to avoid flicker
	    table.setRedraw(false);
        
	    // We remove all the table entries, sort our rows, then add the entries
	    table.removeAll();
	    
	    Collections.sort(playersRoute, comparatorRoute);
	    for (Iterator itr = playersRoute.iterator(); itr.hasNext();) {
	    	PlayerRoute player = (PlayerRoute) itr.next();
	    	TableItem item = new TableItem(table, SWT.NONE);
	    	int c = 0;
	    	item.setText(c++, player.getID());
	    	item.setText(c++, player.getDEP_AD());
	    	item.setText(c++, player.getDEST_AD());
	    	item.setText(c++, player.getROUTE());

	    	item.setText(c++, player.getDEST1());
	    	item.setText(c++, player.getDEST2());
	    	item.setText(c++, player.getDEST3());
	    	item.setText(c++, player.getDEST4());
	    	item.setText(c++, player.getDEST5());
	    	item.setText(c++, player.getDEST6());
	    	item.setText(c++, player.getDEST7());
	    	
	    	item.setText(c++, player.getDEST8());
	    	item.setText(c++, player.getDEST9());
	    	item.setText(c++, player.getDEST10());
	    	item.setText(c++, player.getDEST11());
	    	item.setText(c++, player.getDEST12());
	    	item.setText(c++, player.getDEST13());
	    	item.setText(c++, player.getDEST14());
	    	
	    	item.setText(c++, player.getDEST15());
	    	item.setText(c++, player.getDEST16());
	    	item.setText(c++, player.getDEST17());
	    	item.setText(c++, player.getDEST18());
	    	item.setText(c++, player.getDEST19());
	    	item.setText(c++, player.getDEST20());
	    	item.setText(c++, player.getDEST21());
	    }
	    
	    // sort indicator
	    table.setSortDirection(dir);
        //table.clearAll();
        
	    // Turn drawing back on
	    table.setRedraw(true);
  	}
	
	void loc(Composite comp, Text text) {
		sh.textStyle(text, 80, 8, SWT.LEFT, SWT.LEFT, sh.letter, "Destination", true);
	}

  	void formAddEdit() {
  		final Group Group = new Group(shell, SWT.NONE); sh.groupStyle(Group, 1, "Add/Edit Route");
  		Composite comp = new Composite(Group, SWT.NONE); sh.compositeStyle(comp, 2, SWT.LEFT, SWT.CENTER);
  		Label label = new Label(comp,SWT.NONE); sh.labelStyle1(label, "DEP AD", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		tDepAd= new Text(comp,SWT.BORDER); sh.textStyle(tDepAd, 45, 4, SWT.LEFT, SWT.LEFT, sh.letter, ATSComponentSetting.b13a, true);
		label = new Label(comp,SWT.NONE); sh.labelStyle1(label, "DEST AD", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY); 
		tDestAd= new Text(comp,SWT.BORDER); sh.textStyle(tDestAd, 45, 4, SWT.LEFT, SWT.LEFT, sh.letter, ATSComponentSetting.b16a, true);
		label = new Label(comp,SWT.NONE); sh.labelStyle1(label, "ROUTE", SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.MANDATORY); 
		tRoute= new Text(comp, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER); sh.textAreaStyle(tRoute, 640, 50, SWT.LEFT, SWT.TOP, sh.upper, ATSComponentSetting.b15c);
		label = new Label(comp,SWT.NONE); sh.labelStyle1(label, "ADDRESS", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		Composite compb = new Composite(comp, SWT.NONE); sh.compositeStyle(compb, 7, SWT.LEFT, SWT.CENTER);
		tDest1 = new Text(compb, SWT.BORDER); loc(compb, tDest1);
		tDest2 = new Text(compb, SWT.BORDER); loc(compb, tDest2);
		tDest3 = new Text(compb, SWT.BORDER); loc(compb, tDest3);
		tDest4 = new Text(compb, SWT.BORDER); loc(compb, tDest4);
		tDest5 = new Text(compb, SWT.BORDER); loc(compb, tDest5);
		tDest6 = new Text(compb, SWT.BORDER); loc(compb, tDest6);
		tDest7 = new Text(compb, SWT.BORDER); loc(compb, tDest7);
		label = new Label(comp,SWT.NONE); sh.labelStyle1(label, "", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		Composite compc = new Composite(comp, SWT.NONE); sh.compositeStyle(compc, 7, SWT.LEFT, SWT.CENTER);
		tDest8 = new Text(compc, SWT.BORDER); loc(compc, tDest8);
		tDest9 = new Text(compc, SWT.BORDER); loc(compc, tDest9);
		tDest10 = new Text(compc, SWT.BORDER); loc(compc, tDest10);
		tDest11 = new Text(compc, SWT.BORDER); loc(compc, tDest11);
		tDest12 = new Text(compc, SWT.BORDER); loc(compc, tDest12);
		tDest13 = new Text(compc, SWT.BORDER); loc(compc, tDest13);
		tDest14 = new Text(compc, SWT.BORDER); loc(compc, tDest14);
		label = new Label(comp,SWT.NONE); sh.labelStyle1(label, "", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		Composite compd = new Composite(comp, SWT.NONE); sh.compositeStyle(compd, 7, SWT.LEFT, SWT.CENTER);
		tDest15 = new Text(compd, SWT.BORDER); loc(compd, tDest15);
		tDest16 = new Text(compd, SWT.BORDER); loc(compd, tDest16);
		tDest17 = new Text(compd, SWT.BORDER); loc(compd, tDest17);
		tDest18 = new Text(compd, SWT.BORDER); loc(compd, tDest18);
		tDest19 = new Text(compd, SWT.BORDER); loc(compd, tDest19);
		tDest20 = new Text(compd, SWT.BORDER); loc(compd, tDest20);
		tDest21 = new Text(compd, SWT.BORDER); loc(compd, tDest21);
		
		Composite typeA = new Composite(Group, SWT.NONE); sh.compositeStyle(typeA, 1, SWT.LEFT, SWT.CENTER);
		label = new Label(typeA, SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelStyle(label, "", MainForm.getWidthRightTop(), SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
		
		Composite Btn = new Composite(Group, SWT.NONE); sh.compositeStyle(Btn, 3, SWT.CENTER, SWT.CENTER);
		Button Save = new Button(Btn, SWT.PUSH);
		Button Update = new Button(Btn, SWT.PUSH);
		Button Clear = new Button(Btn, SWT.PUSH);
		bs.SUC(Save, Update, Clear);
		
		Save.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {	
				if (tDepAd.getText().isEmpty()) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "The value in field DEP AD is required."); tDepAd.setFocus(); }
				else if (!tDepAd.getText().isEmpty() && tDepAd.getText().length()<4) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 4 LETTERS for field DEP AD !!"); tDepAd.setFocus(); }
				else if (tDestAd.getText().isEmpty()) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "The value in field DEST AD is required."); tDestAd.setFocus(); }
				else if (!tDestAd.getText().isEmpty() && tDestAd.getText().length()<4) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 4 LETTERS for field DEST AD !!"); tDestAd.setFocus(); }
				else if (tRoute.getText().isEmpty()) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "The value in field ROUTE is required."); tRoute.setFocus(); }
				else if ((!tDest1.getText().equals("")) && (tDest1.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest1.setFocus(); }
				else if ((!tDest2.getText().equals("")) && (tDest2.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest2.setFocus(); }
				else if ((!tDest3.getText().equals("")) && (tDest3.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest3.setFocus(); }
				else if ((!tDest4.getText().equals("")) && (tDest4.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest4.setFocus(); }
				else if ((!tDest5.getText().equals("")) && (tDest5.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest5.setFocus(); }
				else if ((!tDest6.getText().equals("")) && (tDest6.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest6.setFocus(); }
				else if ((!tDest7.getText().equals("")) && (tDest7.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest7.setFocus(); }
				
				else if ((!tDest8.getText().equals("")) && (tDest8.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest8.setFocus(); }
				else if ((!tDest9.getText().equals("")) && (tDest9.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest9.setFocus(); }
				else if ((!tDest10.getText().equals("")) && (tDest10.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest10.setFocus(); }
				else if ((!tDest11.getText().equals("")) && (tDest11.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest11.setFocus(); }
				else if ((!tDest12.getText().equals("")) && (tDest12.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest12.setFocus(); }
				else if ((!tDest13.getText().equals("")) && (tDest13.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest13.setFocus(); }
				else if ((!tDest14.getText().equals("")) && (tDest14.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest14.setFocus(); }
				
				else if ((!tDest15.getText().equals("")) && (tDest15.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest15.setFocus(); }
				else if ((!tDest16.getText().equals("")) && (tDest16.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest16.setFocus(); }
				else if ((!tDest17.getText().equals("")) && (tDest17.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest17.setFocus(); }
				else if ((!tDest18.getText().equals("")) && (tDest18.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest18.setFocus(); }
				else if ((!tDest19.getText().equals("")) && (tDest19.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest19.setFocus(); }
				else if ((!tDest20.getText().equals("")) && (tDest20.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest20.setFocus(); }
				else if ((!tDest21.getText().equals("")) && (tDest21.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest21.setFocus(); }
				else {
					String insert = "INSERT INTO route (type13a,type16a,type15c,destination1,destination2,destination3,destination4,destination5,destination6,destination7,destination8,destination9,destination10,destination11,destination12,destination13,destination14,destination15,destination16,destination17,destination18,destination19,destination20,destination21) VALUES ('"+tDepAd.getText()+"','"+tDestAd.getText()+"','"+tRoute.getText()+"','"+tDest1.getText()+"','"+tDest2.getText()+"','"+tDest3.getText()+"','"+tDest4.getText()+"','"+tDest5.getText()+"','"+tDest6.getText()+"','"+tDest7.getText()+"','"+tDest8.getText()+"','"+tDest9.getText()+"','"+tDest10.getText()+"','"+tDest11.getText()+"','"+tDest12.getText()+"','"+tDest13.getText()+"','"+tDest14.getText()+"','"+tDest15.getText()+"','"+tDest16.getText()+"','"+tDest17.getText()+"','"+tDest18.getText()+"','"+tDest19.getText()+"','"+tDest20.getText()+"','"+tDest21.getText()+"')";
					jdbc.connect(insert);
					discard(); string2=""; string=""; sqlSelect="";
					//buat ng'refreshnya
					table.removeAll(); playersRoute.clear(); RefreshTable.refreshTableRoute(t13a, t16a);
				}
			}
		});
		
		Update.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {	
				if (sett().equals("")) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data","At least one row must be selected if you want to update your message !!"); } 
				else if (tDepAd.getText().isEmpty()) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "The value in field DEP AD is required."); tDepAd.setFocus(); }
				else if (!tDepAd.getText().isEmpty() && tDepAd.getText().length()<4) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 4 LETTERS for field DEP AD !!"); tDepAd.setFocus(); }
				else if (tDestAd.getText().isEmpty()) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "The value in field DEST AD is required."); tDestAd.setFocus(); }
				else if (!tDestAd.getText().isEmpty() && tDestAd.getText().length()<4) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 4 LETTERS for field DEST AD !!"); tDestAd.setFocus(); }
				else if (tRoute.getText().isEmpty()) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "The value in field ROUTE is required."); tRoute.setFocus(); }
				else if ((!tDest1.getText().equals("")) && (tDest1.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest1.setFocus(); }
				else if ((!tDest2.getText().equals("")) && (tDest2.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest2.setFocus(); }
				else if ((!tDest3.getText().equals("")) && (tDest3.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest3.setFocus(); }
				else if ((!tDest4.getText().equals("")) && (tDest4.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest4.setFocus(); }
				else if ((!tDest5.getText().equals("")) && (tDest5.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest5.setFocus(); }
				else if ((!tDest6.getText().equals("")) && (tDest6.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest6.setFocus(); }
				else if ((!tDest7.getText().equals("")) && (tDest7.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest7.setFocus(); }
				
				else if ((!tDest8.getText().equals("")) && (tDest8.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest8.setFocus(); }
				else if ((!tDest9.getText().equals("")) && (tDest9.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest9.setFocus(); }
				else if ((!tDest10.getText().equals("")) && (tDest10.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest10.setFocus(); }
				else if ((!tDest11.getText().equals("")) && (tDest11.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest11.setFocus(); }
				else if ((!tDest12.getText().equals("")) && (tDest12.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest12.setFocus(); }
				else if ((!tDest13.getText().equals("")) && (tDest13.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest13.setFocus(); }
				else if ((!tDest14.getText().equals("")) && (tDest14.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest14.setFocus(); }
				
				else if ((!tDest15.getText().equals("")) && (tDest15.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest15.setFocus(); }
				else if ((!tDest16.getText().equals("")) && (tDest16.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest16.setFocus(); }
				else if ((!tDest17.getText().equals("")) && (tDest17.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest17.setFocus(); }
				else if ((!tDest18.getText().equals("")) && (tDest18.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest18.setFocus(); }
				else if ((!tDest19.getText().equals("")) && (tDest19.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest19.setFocus(); }
				else if ((!tDest20.getText().equals("")) && (tDest20.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest20.setFocus(); }
				else if ((!tDest21.getText().equals("")) && (tDest21.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_tRoute, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest21.setFocus(); }
				else {
					String update = "UPDATE route SET type13a='"+tDepAd.getText()+"',type16a='"+tDestAd.getText()+"',type15c='"+tRoute.getText()+
					"',destination1='"+tDest1.getText()+"',destination2='"+tDest2.getText()+"',destination3='"+tDest3.getText()+"',destination4='"+tDest4.getText()+"',destination5='"+tDest5.getText()+"',destination6='"+tDest6.getText()+"',destination7='"+tDest7.getText()
					+"',destination8='"+tDest8.getText()+"',destination9='"+tDest9.getText()+"',destination10='"+tDest10.getText()+"',destination11='"+tDest11.getText()+"',destination12='"+tDest12.getText()+"',destination13='"+tDest13.getText()+"',destination14='"+tDest14.getText()
					+"',destination15='"+tDest15.getText()+"',destination16='"+tDest16.getText()+"',destination17='"+tDest17.getText()+"',destination18='"+tDest18.getText()+"',destination19='"+tDest19.getText()+"',destination20='"+tDest20.getText()+"',destination21='"+tDest21.getText()
					+"' WHERE id_number='"+sett()+"'";
					
						jdbc.update(update);
						discard(); string2=""; sqlSelect="";
						//buat ng'refreshnya
						table.removeAll(); playersRoute.clear(); RefreshTable.refreshTableRoute(t13a, t16a);
				}
			}
		});
		
		Clear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {	
				discard();
				string=""; string2=""; sqlSelect="";
			}
		});
	}
	
  	public void discard() {
  		if (tDepAd.getText() != null) tDepAd.setText("");
		if (tDestAd.getText() != null) tDestAd.setText("");
		if (tRoute.getText() != null) tRoute.setText("");

		if (tDest1.getText() != "") tDest1.setText("");
		if (tDest2.getText() != "") tDest2.setText("");
		if (tDest3.getText() != "") tDest3.setText("");
		if (tDest4.getText() != "") tDest4.setText("");
		if (tDest5.getText() != "") tDest5.setText("");
		if (tDest6.getText() != "") tDest6.setText("");
		if (tDest7.getText() != "") tDest7.setText("");
		
		if (tDest8.getText() != "") tDest8.setText("");
		if (tDest9.getText() != "") tDest9.setText("");
		if (tDest10.getText() != "") tDest10.setText("");
		if (tDest11.getText() != "") tDest11.setText("");
		if (tDest12.getText() != "") tDest12.setText("");
		if (tDest13.getText() != "") tDest13.setText("");
		if (tDest14.getText() != "") tDest14.setText("");
		
		if (tDest15.getText() != "") tDest15.setText("");
		if (tDest16.getText() != "") tDest16.setText("");
		if (tDest17.getText() != "") tDest17.setText("");
		if (tDest18.getText() != "") tDest18.setText("");
		if (tDest19.getText() != "") tDest19.setText("");
		if (tDest20.getText() != "") tDest20.setText("");
		if (tDest21.getText() != "") tDest21.setText("");
  	}
  	
  	private static void set(String string) { TableRoute.string2 = string; }
  	public static String sett() { return TableRoute.string2; }
  	public static String akhir() { return isiHal; }
  	private static String getSele() { return sele; }
	public static String GetSqlSelect() { return sqlSelect; }
	public static String GetString() { return string; }
	public static int getJumlah() { return jumlah; }
	
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
class PlayerRoute {
	private String ID;
	private String DEP_AD;
	private String DEST_AD;
	private String ROUTE;

	private String DEST1;
	private String DEST2;
	private String DEST3;
	private String DEST4;
	private String DEST5;
	private String DEST6;
	private String DEST7;
	
	private String DEST8;
	private String DEST9;
	private String DEST10;
	private String DEST11;
	private String DEST12;
	private String DEST13;
	private String DEST14;
	
	private String DEST15;
	private String DEST16;
	private String DEST17;
	private String DEST18;
	private String DEST19;
	private String DEST20;
	private String DEST21;
	
	public PlayerRoute(String ID, String DEP_AD, String DEST_AD, String ROUTE,
			String DEST1 ,String DEST2 ,String DEST3 ,String DEST4 ,String DEST5 ,String DEST6 ,String DEST7, 
			String DEST8 ,String DEST9 ,String DEST10 ,String DEST11 ,String DEST12 ,String DEST13 ,String DEST14, 
			String DEST15 ,String DEST16 ,String DEST17 ,String DEST18 ,String DEST19 ,String DEST20 ,String DEST21) {
		this.ID = ID;
		this.DEP_AD = DEP_AD;
		this.DEST_AD = DEST_AD;
		this.ROUTE = ROUTE;

		this.DEST1 = DEST1;
		this.DEST2 = DEST2;
		this.DEST3 = DEST3;
		this.DEST4 = DEST4;
		this.DEST5 = DEST5;
		this.DEST6 = DEST6;
		this.DEST7 = DEST7;
		
		this.DEST8 = DEST8;
		this.DEST9 = DEST9;
		this.DEST10 = DEST10;
		this.DEST11 = DEST11;
		this.DEST12 = DEST12;
		this.DEST13 = DEST13;
		this.DEST14 = DEST14;
		
		this.DEST15 = DEST15;
		this.DEST16 = DEST16;
		this.DEST17 = DEST17;
		this.DEST18 = DEST18;
		this.DEST19 = DEST19;
		this.DEST20 = DEST20;
		this.DEST21 = DEST21;
	}

	public String getID() { return ID; }
	public String getDEP_AD() { return DEP_AD; }
	public String getDEST_AD() { return DEST_AD; }
	public String getROUTE() { return ROUTE; }

	public String getDEST1() { return DEST1; }
	public String getDEST2() { return DEST2; }
	public String getDEST3() { return DEST3; }
	public String getDEST4() { return DEST4; }
	public String getDEST5() { return DEST5; }
	public String getDEST6() { return DEST6; }
	public String getDEST7() { return DEST7; }
	
	public String getDEST8() { return DEST8; }
	public String getDEST9() { return DEST9; }
	public String getDEST10() { return DEST10; }
	public String getDEST11() { return DEST11; }
	public String getDEST12() { return DEST12; }
	public String getDEST13() { return DEST13; }
	public String getDEST14() { return DEST14; }
	
	public String getDEST15() { return DEST15; }
	public String getDEST16() { return DEST16; }
	public String getDEST17() { return DEST17; }
	public String getDEST18() { return DEST18; }
	public String getDEST19() { return DEST19; }
	public String getDEST20() { return DEST20; }
	public String getDEST21() { return DEST21; }
}

@SuppressWarnings("unchecked")
class PlayerComparatorRoute implements Comparator {
	public static final int ID = 0;
	public static final int DEP_AD = 1;
	public static final int DEST_AD = 2;
	public static final int ROUTE = 3;

	public static final int DEST1 = 4;
	public static final int DEST2 = 5;
	public static final int DEST3 = 6;
	public static final int DEST4 = 7;
	public static final int DEST5 = 8;
	public static final int DEST6 = 9;
	public static final int DEST7 = 10;
	
	public static final int DEST8 = 11;
	public static final int DEST9 = 12;
	public static final int DEST10 = 13;
	public static final int DEST11 = 14;
	public static final int DEST12 = 15;
	public static final int DEST13 = 16;
	public static final int DEST14 = 17;
	
	public static final int DEST15 = 18;
	public static final int DEST16 = 19;
	public static final int DEST17 = 20;
	public static final int DEST18 = 21;
	public static final int DEST19 = 22;
	public static final int DEST20 = 23;
	public static final int DEST21 = 24;
	
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
	    PlayerRoute p1 = (PlayerRoute) obj1;
	    PlayerRoute p2 = (PlayerRoute) obj2;
	
	    // Determine which field to sort on, then sort on that field
	    switch (column) {
		    case ID:
		    	rc = p1.getID().compareTo(p2.getID());
		    	break;
		    case DEP_AD:
		    	rc = p1.getDEP_AD().compareTo(p2.getDEP_AD());
		    	break;
		    case DEST_AD:
		    	rc = p1.getDEST_AD().compareTo(p2.getDEST_AD());
		    	break;
		    case ROUTE:
		    	rc = p1.getROUTE().compareTo(p2.getROUTE());
		    	break;

		    case DEST1:
		    	rc = p1.getDEST1().compareTo(p2.getDEST1());
		    	break;
		    case DEST2:
		    	rc = p1.getDEST2().compareTo(p2.getDEST2());
		    	break;
		    case DEST3:
		    	rc = p1.getDEST3().compareTo(p2.getDEST3());
		    	break;
		    case DEST4:
		    	rc = p1.getDEST4().compareTo(p2.getDEST4());
		    	break;
		    case DEST5:
		    	rc = p1.getDEST5().compareTo(p2.getDEST5());
		    	break;
		    case DEST6:
		    	rc = p1.getDEST6().compareTo(p2.getDEST6());
		    	break;
		    case DEST7:
		    	rc = p1.getDEST7().compareTo(p2.getDEST7());
		    	break;
		    	
		    case DEST8:
		    	rc = p1.getDEST8().compareTo(p2.getDEST8());
		    	break;
		    case DEST9:
		    	rc = p1.getDEST9().compareTo(p2.getDEST9());
		    	break;
		    case DEST10:
		    	rc = p1.getDEST10().compareTo(p2.getDEST10());
		    	break;
		    case DEST11:
		    	rc = p1.getDEST11().compareTo(p2.getDEST11());
		    	break;
		    case DEST12:
		    	rc = p1.getDEST12().compareTo(p2.getDEST12());
		    	break;
		    case DEST13:
		    	rc = p1.getDEST13().compareTo(p2.getDEST13());
		    	break;
		    case DEST14:
		    	rc = p1.getDEST14().compareTo(p2.getDEST14());
		    	break;
		    	
		    case DEST15:
		    	rc = p1.getDEST15().compareTo(p2.getDEST15());
		    	break;
		    case DEST16:
		    	rc = p1.getDEST16().compareTo(p2.getDEST16());
		    	break;
		    case DEST17:
		    	rc = p1.getDEST17().compareTo(p2.getDEST17());
		    	break;
		    case DEST18:
		    	rc = p1.getDEST18().compareTo(p2.getDEST18());
		    	break;
		    case DEST19:
		    	rc = p1.getDEST19().compareTo(p2.getDEST19());
		    	break;
		    case DEST20:
		    	rc = p1.getDEST20().compareTo(p2.getDEST20());
		    	break;
		    case DEST21:
		    	rc = p1.getDEST21().compareTo(p2.getDEST21());
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