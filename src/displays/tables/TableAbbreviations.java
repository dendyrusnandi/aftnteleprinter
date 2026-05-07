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

import setting.ButtonsSetting;
import setting.Colors;
import setting.Shells;
import setting.Shorten;
import setting.TextSetting;
import actions.RefreshTable;
import actions.cProgressBar;
import actions.check69;
import databases.jdbc;
import displays.DialogFactory;
import displays.MainForm;
import displays.TeleSplashScreen2016IP;


public class TableAbbreviations {
	@SuppressWarnings("unchecked")
	private java.util.List playersAbbr;
	private PlayerComparatorAbbr comparatorAbbr;
	
	Shorten sh = new Shorten();
	ButtonsSetting bs = new ButtonsSetting();
	TextSetting ts = new TextSetting();
	check69 cekmn = new check69();
	
	Shell shell;
	Table table;
	TableItem [] selection;
	Button next,prev,first,last;//,go;
	Text tPage, tTotPage, tGoToHal;
	
	String ch69="";
	String select="", sele="",id="",P1="",P2="",string="",string2="",sqlSelect="",jml_item="",isiHal="",pAbbr="",pMean="";
	
	int dir=0,p=0,yu=0,hasil=0;
	static int baris=15,qq=0,jumlah=0,rowNo=0,qu=0;
	
	Connection conn = null; // connection object
    Statement stmt = null; // statement object
    ResultSet rs = null; // result set object
	
	void char69(String text) {
		 cekmn.check("", text, "",100); //PROSES 100 karakter
		 int nItemE = cekmn.i_totNotam;
		 String itemE[] = cekmn.sEres;
		 String coba="";
		 for (int i=0; i<nItemE; i++) {
			 coba = itemE[i];
			 ch69+="\n"+coba;
		 }
		 if (ch69.startsWith("\n")) ch69 = ch69.replaceFirst("\n", "");
	}
	
	public void koneksiDB(Shell pshell, String abbr, String mean) {
		shell = pshell;
		pAbbr = abbr;
		pMean = mean;
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
						    // Create the comparatorAbbr used for sorting
						    comparatorAbbr = new PlayerComparatorAbbr();
						    comparatorAbbr.setColumn(PlayerComparatorAbbr.ABBR);
						    comparatorAbbr.setDirection(PlayerComparatorAbbr.DESCENDING);
						    
						    // Create the playersAbbr
						    playersAbbr = new ArrayList();
						   
							String sInd="",sLoc="";
							if (pAbbr.compareTo("")!=0) sInd=" AND abbr LIKE '"+pAbbr+"%'"; else sInd="";
							if (pMean.compareTo("")!=0) sLoc=" AND mean LIKE '%"+pMean+"%'"; else sLoc="";
							String kriteria = sInd+sLoc;
							if (kriteria.startsWith(" AND")) kriteria = kriteria.replaceFirst(" AND", "");
							if (!kriteria.isEmpty()) kriteria = kriteria.replaceFirst(kriteria, " WHERE"+kriteria);
							
							stmt = conn.createStatement();
							String lihatsql = sele = "SELECT * FROM abbreviations"+kriteria+" ORDER BY abbr ASC";				
							System.out.println("\nQuery: " + lihatsql);
							rs = stmt.executeQuery(lihatsql);
							rs.last();
							jumlah = rs.getRow();
							System.out.println("Jumlah data=" + jumlah);
							rs.beforeFirst();
							
							final String datatabel[][] = new String[baris][3];
							p = 0;
							rowNo = 0;
							while (rs.next() && (rowNo < baris)) {
								rowNo++;
								
								datatabel[p][0] = rs.getString("id"); id=datatabel[p][0]; if (id==null){ id=""; }
								datatabel[p][1] = rs.getString("abbr"); P1=datatabel[p][1]; if (P1==null){ P1=""; }
								datatabel[p][2] = rs.getString("mean"); P2=datatabel[p][2]; if (P2==null){ P2=""; }
								ch69=""; char69(P2); P2=ch69;
								
								p++;
								playersAbbr.add(new PlayerAbbr(id,P1,P2));
								progbr.bar.setSelection(rowNo);
							} //end of while
							progbr.finish();
							
//							if (getJumlah() != 0) {
								if (getJumlah()==0) DialogFactory.openInfoDialog(Shells.sh_tAbbr, "Search Message","Data not found, your search has no result !!");
							    formSearch();
								formList();
							    sh.shellStyle(shell, "ICAO Abbreviations and Codes (PANS-ABC)");
							    shell.addListener(SWT.Close, new Listener() {
									public void handleEvent(Event event) {
										connClose();
									}
								});
//					  		} else {
//					  			DialogFactory.openInfoDialog(Shells.sh_tAbbr, "Search Message","Data not found, your search has no result !!");
//					  		}
	        			} catch (SQLException s) {
							DialogFactory.openInfoDialog(Shells.sh_tAbbr, "Search Message", s.toString());
							s.printStackTrace();
							progbr.finish();
		        		} catch (java.lang.OutOfMemoryError hs) {
		        			hs.printStackTrace();
							DialogFactory.openInfoDialog(Shells.sh_tAbbr, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
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
							
							final String datatabel[][] = new String[baris][3];
							for (rowNo=0; rowNo<(qq*baris); rowNo++) rs.next();
							
							p = 0;
							rowNo = 0;
							playersAbbr.clear();
							while (rs.next() && (rowNo < baris)) {
								rowNo++;
								
								datatabel[p][0] = rs.getString("id"); id=datatabel[p][0]; if (id==null){ id=""; }
								datatabel[p][1] = rs.getString("abbr"); P1=datatabel[p][1]; if (P1==null){ P1=""; }
								datatabel[p][2] = rs.getString("mean"); P2=datatabel[p][2]; if (P2==null){ P2=""; }
								ch69=""; char69(P2); P2=ch69;
								
								TableItem item = new TableItem (table, SWT.NONE);
								item.setText (0,id); 
								item.setText (1,P1);
								item.setText (2,P2);

								p++;
								playersAbbr.add(new PlayerAbbr(id,P1,P2));
								progbr.bar.setSelection(rowNo);
							} //end of while
							progbr.finish();
	        			} catch (SQLException s) {
							DialogFactory.openInfoDialog(Shells.sh_tAbbr, "Search Message", s.toString());
							s.printStackTrace();
							progbr.finish();
		        		} catch (java.lang.OutOfMemoryError hs) {
		        			hs.printStackTrace();
							DialogFactory.openInfoDialog(Shells.sh_tAbbr, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
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
  		final Group grpSearch = new Group(shell, SWT.NONE); sh.groupStyle(grpSearch, 8, "Search Abbreviations and Codes");
  		Label label = new Label(grpSearch,SWT.NONE); sh.labelStyle1(label, "Abbreviations", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		final Text tAbbr_Search = new Text(grpSearch,SWT.BORDER); sh.textStyle(tAbbr_Search, 250, 220, SWT.LEFT, SWT.LEFT, sh.alphaNumSpace, "Abbreviations", true); 
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle1(label, "Meaning", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		final Text tMean_Search = new Text(grpSearch,SWT.BORDER); sh.textStyle(tMean_Search, 250, 100, SWT.LEFT, SWT.LEFT, sh.alphaNumSpace, "Abbreviation's meaning", true);
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Button Search = new Button(grpSearch, SWT.PUSH);
		Button Clear = new Button(grpSearch, SWT.PUSH);
		bs.SC(Search, Clear);

		if (!pAbbr.isEmpty()) tAbbr_Search.setText(pAbbr);
		if (!pMean.isEmpty()) tMean_Search.setText(pMean);
		
		tMean_Search.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					RefreshTable.refreshTableAbbr(tAbbr_Search.getText(), tMean_Search.getText());
				}
			}
	    });
		
		tAbbr_Search.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					RefreshTable.refreshTableAbbr(tAbbr_Search.getText(), tMean_Search.getText());
				}
			}
	    });
		
		Search.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RefreshTable.refreshTableAbbr(tAbbr_Search.getText(), tMean_Search.getText());
			}
		});
		
		Clear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				tAbbr_Search.setText(""); tMean_Search.setText("");
				RefreshTable.refreshTableAbbr(tAbbr_Search.getText(), tMean_Search.getText());
			}
		});	
	}
 	
 	void formList() {
  		final Group Group = new Group(shell, SWT.NONE); sh.groupStyle(Group, 1, "List of Abbreviation(s)");
	    // Create the table
	    table = new Table(Group, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION); sh.tableStyle(table, true, true, 300, MainForm.getWidthRightTop());//1230);
	    // Create each of the columns, adding an event listener 
	    // that will set the appropriate fields into the comparatorAbbr 
	    // and then call the fillTable helper method
		final TableColumn ID = new TableColumn(table,SWT.LEFT); sh.tablecol(ID, "id", "", 0, false);
		ID.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorAbbr.setColumn(PlayerComparatorAbbr.ID);
		        comparatorAbbr.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn ABBR = new TableColumn(table,SWT.LEFT); sh.tablecol(ABBR, "Abbreviations", "Abbreviations", 200, false);
		ABBR.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorAbbr.setColumn(PlayerComparatorAbbr.ABBR);
		        comparatorAbbr.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn MEAN = new TableColumn(table,SWT.LEFT); sh.tablecol(MEAN, "Meaning", "Meaning's of the abbreviations", 500, false);
		MEAN.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorAbbr.setColumn(PlayerComparatorAbbr.MEAN);
		        comparatorAbbr.reverseDirection();
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
	    ABBR.addListener(SWT.Selection, sortListener);
	    MEAN.addListener(SWT.Selection, sortListener);
	    
	    table.setSortColumn(ABBR);
	    table.setSortDirection(SWT.UP);
	      
	    Composite crow = new Composite(Group, SWT.NONE); sh.compositeStyle(crow, 1, SWT.RIGHT, SWT.CENTER);
	    Label lrow = new Label(crow, SWT.NONE); 
		sh.labelStyle1(lrow, getJumlah() + " Element(s) in this table ", SWT.LEFT, SWT.CENTER, SWT.ITALIC | SWT.BOLD, Colors.MANDATORY);
		jdbc.setJmlData(getJumlah());

		Composite typeB = new Composite(Group, SWT.NONE); sh.compositeStyle(typeB, 1, SWT.CENTER, SWT.CENTER);
		Label label = new Label(typeB, SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelStyle(label, "", MainForm.getWidthRightTop(), SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
		
		Composite type1b = new Composite(Group, SWT.NONE); sh.compositeStyle(type1b, 10, SWT.CENTER, SWT.CENTER);
		Label hal = new Label(type1b,SWT.LEFT); sh.labelStyle1(hal, "Page", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		yu = 0; qq = 0;
		yu = qq+1;
		isiHal = "";
		isiHal = Integer.toString(yu);
		tPage = new Text(type1b, SWT.BORDER);
		tPage.setText(isiHal);
		hal = new Label(type1b,SWT.LEFT); sh.labelStyle1(hal, "of", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		int jml;
		if (jumlah % baris == 0) {
			jml = jumlah / baris;
		} else {
			jml = jumlah / baris + 1;
		}
		String jum = Integer.toString(jml);
		tTotPage = new Text(type1b, SWT.BORDER);
		tTotPage.setText(jum);

		first = new Button(type1b,SWT.PUSH);
		prev = new Button(type1b,SWT.PUSH);
		next = new Button(type1b,SWT.PUSH);
		last = new Button(type1b,SWT.PUSH);	
		hal = new Label(type1b,SWT.LEFT); sh.labelStyle1(hal, "Go to", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		
		tGoToHal = new Text(type1b, SWT.BORDER);
		
//		final int hasil;
        if (jumlah % baris == 0) { hasil = jumlah/baris; }
        else { hasil = jumlah/baris+1; }
        
//		go = new Button(type1b,SWT.PUSH); go.setVisible(false);
		if (hasil == 1) {
        	first.setEnabled(false);
        	prev.setEnabled(false);
            next.setEnabled(false);
            last.setEnabled(false);
            
            tGoToHal.setEnabled(false);
//            go.setEnabled(false);
        } 
		
		if (jumlah <= baris) {
			first.setEnabled(false);
			prev.setEnabled(false);
			next.setEnabled(false);
			last.setEnabled(false);
			
			tGoToHal.setEnabled(false);
//            go.setEnabled(false);
		}
		bs.Navigation(tPage, tTotPage, first, prev, next, last, tGoToHal);//, go);
		addListener();
 	}
 	
 	void addListener() {
		first.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (first.getEnabled() == true) {
					first.setEnabled(false); prev.setEnabled(false); next.setEnabled(true);

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
					next.setEnabled(true); first.setEnabled(true); last.setEnabled(true);
				}
				
				qq--;
				if (qq == 0) {
					prev.setEnabled(false); first.setEnabled(false);
				}
				
				if (qq < 0) {
					qq = 0;
				} else {
					table.removeAll();
					int as = Integer.parseInt(akhir().trim())-1;
					isiHal=Integer.toString(as);
					tPage.setText(isiHal);
					Next();	
				}
				tGoToHal.setText(isiHal);
			}
		});
		
		next.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				prev.setEnabled(true); first.setEnabled(true);

				qq++;
				int jml;
				if (jumlah % baris == 0) { 
					jml = jumlah/baris; 
				} else { 
					jml = jumlah/baris+1;
				}
				
				int all = jml;
				if (all-1 == qq) {
					next.setEnabled(false); last.setEnabled(false);
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
				next.setEnabled(false); prev.setEnabled(true); first.setEnabled(true);
				
				table.removeAll();
				if (jumlah % baris == 0) qq = jumlah/baris - 1;
				else qq = jumlah/baris;
				
				isiHal = Integer.toString(qq+1);
				tPage.setText(isiHal);
				Next();
				last.setEnabled(false);
				tGoToHal.setText(isiHal);
			}
		});		
		
//		go.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				setgo();
//			}
//		});
		
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
 	
 	@SuppressWarnings("unchecked")
	void fillTable(Table table) {
	    // Turn off drawing to avoid flicker
	    table.setRedraw(false);
       
	    // We remove all the table entries, sort our rows, then add the entries
	    table.removeAll();
	    
	    Collections.sort(playersAbbr, comparatorAbbr);
	    for (Iterator itr = playersAbbr.iterator(); itr.hasNext();) {
	    	PlayerAbbr player = (PlayerAbbr) itr.next();
	    	TableItem item = new TableItem(table, SWT.NONE);
	    	int c = 0;
	    	item.setText(c++, player.getID());
	    	item.setText(c++, player.getABBR());
	    	item.setText(c++, player.getMEAN());
	    }
	    
	    // sort indicator
	    table.setSortDirection(dir);
       //table.clearAll();
       
	    // Turn drawing back on
	    table.setRedraw(true);
 	}
 	
 	public String akhir() { return isiHal; }
	public String GetSqlSelect() { return sqlSelect; }
	public String GetString() { return string; }
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
class PlayerAbbr {
	private String ID;
	private String ABBR;
	private String MEAN;
	
	public PlayerAbbr(String ID, String ABBR, String MEAN) {
		this.ID = ID;
		this.ABBR = ABBR;
		this.MEAN = MEAN;
	}

	public String getID() { return ID; }
	public String getABBR() { return ABBR; }
	public String getMEAN() { return MEAN; }
}

@SuppressWarnings("unchecked")
class PlayerComparatorAbbr implements Comparator {
	public static final int ID = 0;
	public static final int ABBR = 1;
	public static final int MEAN = 2;
	
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
	    PlayerAbbr p1 = (PlayerAbbr) obj1;
	    PlayerAbbr p2 = (PlayerAbbr) obj2;
	
	    // Determine which field to sort on, then sort on that field
	    switch (column) {
		    case ID:
		    	rc = p1.getID().compareTo(p2.getID());
		    	break;
		    case ABBR:
		    	rc = p1.getABBR().compareTo(p2.getABBR());
		    	break;
		    case MEAN:
		    	rc = p1.getMEAN().compareTo(p2.getMEAN());
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
