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

public class TableLocInd {
	@SuppressWarnings("unchecked")
	private java.util.List playersLocInd;
	private PlayerComparatorLocInd comparatorLocInd;
	
	Shorten sh = new Shorten();
	ButtonsSetting bs = new ButtonsSetting();
	TextSetting ts = new TextSetting();
	
	static String P0,P1,P2,P3,P4,id,sqlSelect,string,select,sele,isiHal,jml_item="",string2="";
	String pIndicator="",pLocation="";
	
	Shell shell;
	Table table;
	TableItem [] selection;
	Button next,prev,first,last,Edit,Delete,DeleteAll;//,go;
	Text tPage, tTotPage, tGoToHal;
	Text tIndicator_Locind,tLocation_Locind;
	
	int dir=0,p=0,yu=0,hasil;
	static int baris=15,qq=0,jumlah=0,rowNo=0,qu=0;
	
	Connection conn = null; // connection object
    Statement stmt = null; // statement object
    ResultSet rs = null; // result set object
	
	public void koneksiDB(Shell pshell, String indicator, String location) {
		shell = pshell;
		pIndicator = indicator;
		pLocation = location;
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
						    // Create the comparatorLocInd used for sorting
						    comparatorLocInd = new PlayerComparatorLocInd();
						    comparatorLocInd.setColumn(PlayerComparatorLocInd.ID);
						    comparatorLocInd.setDirection(PlayerComparatorLocInd.ASCENDING);
						    
						    // Create the playersLocInd
						    playersLocInd = new ArrayList();
							
							String sInd="",sLoc="";
							if (pIndicator.compareTo("")!=0) sInd=" AND indicator LIKE '"+pIndicator+"%'"; else sInd="";
							if (pLocation.compareTo("")!=0) sLoc=" AND location LIKE '%"+pLocation+"%'"; else sLoc="";
							String kriteria = sInd+sLoc;
							if (kriteria.startsWith(" AND")) kriteria = kriteria.replaceFirst(" AND", "");
							if (!kriteria.isEmpty()) kriteria = kriteria.replaceFirst(kriteria, " WHERE"+kriteria);
							
							stmt = conn.createStatement();
							String lihatsql = sele = "SELECT * FROM location_indicator"+kriteria+" ORDER BY id DESC";				
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
								datatabel[p][1] = rs.getString("indicator"); P1=datatabel[p][1]; if (P1==null){ P1=""; }
								datatabel[p][2] = rs.getString("location"); P2=datatabel[p][2]; if (P2==null){ P2=""; }

								p++;
								playersLocInd.add(new PlayerLocInd(id,P1,P2));
								progbr.bar.setSelection(rowNo);
							} //end of while
							progbr.finish();
							
//							if (getJumlah() != 0) {
								if (getJumlah()==0) DialogFactory.openInfoDialog(Shells.sh_tLocind, "Search Message","Data not found, your search has no result !!");
							    formSearch();
								formList();
								formAddEdit();
							    sh.shellStyle(shell, "ICAO Location Indicator");
							    shell.addListener(SWT.Close, new Listener() {
									public void handleEvent(Event event) {
										connClose();
									}
								});
//					  		} else {
//					  			DialogFactory.openInfoDialog(Shells.sh_tLocind, "Search Message","Data not found, your search has no result !!");
//					  		}
	        			} catch (SQLException s) {
							DialogFactory.openInfoDialog(Shells.sh_tLocind, "Search Message", s.toString());
							s.printStackTrace();
							progbr.finish();
		        		} catch (java.lang.OutOfMemoryError hs) {
		        			hs.printStackTrace();
							DialogFactory.openInfoDialog(Shells.sh_tLocind, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
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
							playersLocInd.clear();
							while (rs.next() && (rowNo < baris)) {
								rowNo++;
								
								datatabel[p][0] = rs.getString("id"); id=datatabel[p][0]; if (id==null){ id=""; }
								datatabel[p][1] = rs.getString("indicator"); P1=datatabel[p][1]; if (P1==null){ P1=""; }
								datatabel[p][2] = rs.getString("location"); P2=datatabel[p][2]; if (P2==null){ P2=""; }
								
								TableItem item = new TableItem (table, SWT.NONE);
								item.setText (0,id); 
								item.setText (1,P1);
								item.setText (2,P2);

								p++;
								playersLocInd.add(new PlayerLocInd(id,P1,P2));
								progbr.bar.setSelection(rowNo);
							} //end of while
							progbr.finish();
	        			} catch (SQLException s) {
							DialogFactory.openInfoDialog(Shells.sh_tLocind, "Search Message", s.toString());
							s.printStackTrace();
							progbr.finish();
		        		} catch (java.lang.OutOfMemoryError hs) {
		        			hs.printStackTrace();
							DialogFactory.openInfoDialog(Shells.sh_tLocind, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
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
  		final Group grpSearch = new Group(shell, SWT.NONE); sh.groupStyle(grpSearch, 8, "Search ICAO Location Indicator");
  		Label label = new Label(grpSearch,SWT.NONE); sh.labelStyle1(label, "INDICATOR", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		final Text tIndicator_Search = new Text(grpSearch,SWT.BORDER); sh.textStyle(tIndicator_Search, 50, 4, SWT.LEFT, SWT.LEFT, sh.letter, "ICAO Location Indicator", true); 
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle1(label, "LOCATION", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		final Text tLocation_Search = new Text(grpSearch,SWT.BORDER); sh.textStyle(tLocation_Search, 250, 1000, SWT.LEFT, SWT.LEFT, sh.upper, "Location", true);
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Button Search = new Button(grpSearch, SWT.PUSH);
		Button Clear = new Button(grpSearch, SWT.PUSH);
		bs.SC(Search, Clear);

		if (!pIndicator.isEmpty()) tIndicator_Search.setText(pIndicator);
		if (!pLocation.isEmpty()) tLocation_Search.setText(pLocation);
		
		tLocation_Search.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					RefreshTable.refreshTableLocInd(tIndicator_Search.getText(), tLocation_Search.getText());
				}
			}
	    });
		
		tIndicator_Search.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					RefreshTable.refreshTableLocInd(tIndicator_Search.getText(), tLocation_Search.getText());
				}
			}
	    });
		
		Search.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RefreshTable.refreshTableLocInd(tIndicator_Search.getText(), tLocation_Search.getText());
			}
		});
		
		Clear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				tIndicator_Search.setText(""); tLocation_Search.setText("");
				RefreshTable.refreshTableLocInd(tIndicator_Search.getText(), tLocation_Search.getText());
			}
		});	
	}
 	
 	void formList() {
 		final Group Group = new Group(shell, SWT.NONE); sh.groupStyle(Group, 1, "List of ICAO Location Indicator(s)");
	    // Create the table
	    table = new Table(Group, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION); sh.tableStyle(table, true, true, 200, MainForm.getWidthRightTop());//1230);
	    // Create each of the columns, adding an event listener 
	    // that will set the appropriate fields into the comparatorLocInd 
	    // and then call the fillTable helper method
		final TableColumn ID = new TableColumn(table,SWT.LEFT); sh.tablecol(ID, "id", "", 0, false);
		ID.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorLocInd.setColumn(PlayerComparatorLocInd.ID);
		        comparatorLocInd.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn INDICATOR = new TableColumn(table,SWT.LEFT); sh.tablecol(INDICATOR, "INDICATOR", "ICAO Location Indicator", 120, false);
		INDICATOR.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorLocInd.setColumn(PlayerComparatorLocInd.INDICATOR);
		        comparatorLocInd.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn LOCATION = new TableColumn(table,SWT.LEFT); sh.tablecol(LOCATION, "LOCATION", "ICAO Location Name", 500, false);
		LOCATION.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorLocInd.setColumn(PlayerComparatorLocInd.LOCATION);
		        comparatorLocInd.reverseDirection();
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
	    INDICATOR.addListener(SWT.Selection, sortListener);
	    LOCATION.addListener(SWT.Selection, sortListener);
	    
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
				selection = table.getSelection();
				for (int i=0; i<selection.length; i++) 
					string += selection [i] + " ";
	
				if (string.contains("TableItem {")){
					string=string.replace("TableItem {","");
					if (string.contains("}")){
						string=string.replaceAll("} ", "");
					}
					sqlSelect="SELECT * FROM location_indicator WHERE id='"+string+"'";
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
				if (selection.length==0 || sqlSelect.equals("")) { DialogFactory.openInfoAtLeast(Shells.sh_tLocind,DialogFactory.delete); } 
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
							string = string.replace(",", "' OR id='");
						} 
					} 
	
					if (string == null) string = "";
					if (string.equals("")) {
						DialogFactory.openInfoAtLeast(Shells.sh_tLocind,DialogFactory.delete);
					} else {
						DialogFactory.openYesNoDelete(Shells.sh_tLocind);
						boolean tentu = DialogFactory.getPenentuan(); 
						if (tentu == true) {
							String del = "DELETE FROM location_indicator WHERE id='" + string +"'";
							jdbc.delete(del);
//							string=""; sqlSelect="";
//							table.removeAll(); playersLocInd.clear(); RefreshTable.refreshTableLocInd(pIndicator, pLocation);
							if (getJumlah() > 1) {
								string=""; sqlSelect="";
								table.removeAll(); playersLocInd.clear(); RefreshTable.refreshTableLocInd(pIndicator, pLocation);
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
				if (getJumlah() == 0) { DialogFactory.openInfoDialog(Shells.sh_tLocind, "Delete Message", "There's no data !!"); }
				else {
					DialogFactory.openYesNoDelete(Shells.sh_tLocind);
					boolean tentu = DialogFactory.getPenentuan(); 
					if (tentu == true) {
						String dele = getSele().toString(); 
						dele = dele.replace("SELECT * FROM", "DELETE FROM");
						dele = dele.replace(" ORDER BY tgl DESC", "");
						jdbc.delete(dele);
						table.removeAll(); playersLocInd.clear(); shell.close();
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
			
			tIndicator_Locind.setText(jdbc.getIndicator());
			tLocation_Locind.setText(jdbc.getLocationInd());
			
			//pengosongan ID
			sqlSelect=""; string="";
			jdbc.connClose();
		} else { DialogFactory.openInfoAtLeast(Shells.sh_tLocind,DialogFactory.edit); } 
 	}
 	
 	void formAddEdit() {
  		final Group Group = new Group(shell, SWT.NONE); sh.groupStyle(Group, 1, "Add/Edit ICAO Location Indicator");
  		Composite type = new Composite(Group, SWT.NONE); sh.compositeStyle(type, 2, SWT.LEFT, SWT.CENTER);
		Label label = new Label(type, SWT.NONE); sh.labelStyle1(label, "INDICATOR", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		tIndicator_Locind = new Text(type, SWT.BORDER); sh.textStyle(tIndicator_Locind, 40, 4, SWT.LEFT, SWT.LEFT, sh.letter, "ICAO Location Indicator", true); 
		label = new Label(type, SWT.NONE); sh.labelStyle1(label, "LOCATION", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		tLocation_Locind = new Text(type, SWT.BORDER); sh.textStyle(tLocation_Locind, 500, 500, SWT.LEFT, SWT.LEFT, sh.upper, "Location", true); 

		Composite typeA = new Composite(Group, SWT.NONE); sh.compositeStyle(typeA, 1, SWT.CENTER, SWT.CENTER);
		label = new Label(typeA, SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelStyle(label, "", MainForm.getWidthRightTop(), SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
		
		Composite Btn = new Composite(Group, SWT.NONE); sh.compositeStyle(Btn, 3, SWT.CENTER, SWT.CENTER); 
		Button Save = new Button(Btn, SWT.PUSH);
		Save.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {	
				if (tIndicator_Locind.getText().equals("")) { DialogFactory.openWarningDialog(Shells.sh_tLocind, "Save Data","The value in field INDICATOR is required."); tIndicator_Locind.setFocus(); }
				else if (!tIndicator_Locind.getText().equals("") && tIndicator_Locind.getText().length()<4) { DialogFactory.openWarningDialog(Shells.sh_tLocind, "Save Data","Please insert 4 LETTERS for field INDICATOR !!"); tIndicator_Locind.setFocus(); }
	    		else if (tLocation_Locind.getText().equals("")) { DialogFactory.openWarningDialog(Shells.sh_tLocind, "Save Data","The value in field LOCATION is required."); tLocation_Locind.setFocus(); }
	    		else {
	    			String insert = "INSERT INTO location_indicator (indicator,location) VALUES ('"+tIndicator_Locind.getText()+"','"+tLocation_Locind.getText()+"')";
	    			jdbc.connect(insert);
	    			table.removeAll(); playersLocInd.clear(); RefreshTable.refreshTableLocInd(pIndicator, pLocation); 
	    		}
			}
		});
		
		Button Update = new Button(Btn, SWT.PUSH);
		Update.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {	
				if (sett().equals("")) { DialogFactory.openInfoDialog(Shells.sh_tLocind, "Update Data","At least one row must be selected if you want to update your message !!"); } 
				else if (tIndicator_Locind.getText().equals("")) { DialogFactory.openWarningDialog(Shells.sh_tLocind, "Update Data","The value in field INDICATOR is required."); tIndicator_Locind.setFocus(); }
				else if (!tIndicator_Locind.getText().equals("") && tIndicator_Locind.getText().length()<4) { DialogFactory.openWarningDialog(Shells.sh_tLocind, "Update Data","Please insert 4 LETTERS for field INDICATOR !!"); tIndicator_Locind.setFocus(); }
	    		else if (tLocation_Locind.getText().equals("")) { DialogFactory.openWarningDialog(Shells.sh_tLocind, "Update Data","The value in field LOCATION is required."); tLocation_Locind.setFocus(); }
	    		else {
					String update = "UPDATE location_indicator SET indicator='"+tIndicator_Locind.getText()+"',location='"+tLocation_Locind.getText()+"' WHERE id='"+sett()+"'";
					jdbc.update(update);
					discard(); string2=""; sqlSelect="";
					//buat ng'refreshnya
					table.removeAll(); playersLocInd.clear(); RefreshTable.refreshTableLocInd(pIndicator, pLocation);
				}
			}
		});
		
		Button Clear = new Button(Btn, SWT.PUSH);
		Clear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {	
				discard();
				string=""; string2=""; sqlSelect="";
			}
		});
		bs.SUC(Save, Update, Clear);
	}
		
	void discard() {
		if (tIndicator_Locind.getText() != null) tIndicator_Locind.setText("");
		if (tLocation_Locind.getText() != null) tLocation_Locind.setText("");
	}
 	
 	@SuppressWarnings("unchecked")
	void fillTable(Table table) {
	    // Turn off drawing to avoid flicker
	    table.setRedraw(false);
       
	    // We remove all the table entries, sort our rows, then add the entries
	    table.removeAll();
	    
	    Collections.sort(playersLocInd, comparatorLocInd);
	    for (Iterator itr = playersLocInd.iterator(); itr.hasNext();) {
	    	PlayerLocInd player = (PlayerLocInd) itr.next();
	    	TableItem item = new TableItem(table, SWT.NONE);
	    	int c = 0;
	    	item.setText(c++, player.getID());
	    	item.setText(c++, player.getINDICATOR());
	    	item.setText(c++, player.getLOCATION());
	    }
	    
	    // sort indicator
	    table.setSortDirection(dir);
       //table.clearAll();
       
	    // Turn drawing back on
	    table.setRedraw(true);
 	}
	
  	private static void set(String string) { TableLocInd.string2 = string; }
  	public static String sett() { return TableLocInd.string2; }
  	public static String akhir() { return isiHal; }
  	private static String getSele() { return sele; }
	public static String GetSqlSelect() { return sqlSelect; }
	public static String GetString() { return string; }
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
		//pengosongan ID
		string=""; string2=""; sqlSelect=""; discard();
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
class PlayerLocInd {
	private String ID;
	private String INDICATOR;
	private String LOCATION;
	
	public PlayerLocInd(String ID, String INDICATOR, String LOCATION) {
		this.ID = ID;
		this.INDICATOR = INDICATOR;
		this.LOCATION = LOCATION;
	}

	public String getID() { return ID; }
	public String getINDICATOR() { return INDICATOR; }
	public String getLOCATION() { return LOCATION; }
}

@SuppressWarnings("unchecked")
class PlayerComparatorLocInd implements Comparator {
	public static final int ID = 0;
	public static final int INDICATOR = 1;
	public static final int LOCATION = 2;
	
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
	    PlayerLocInd p1 = (PlayerLocInd) obj1;
	    PlayerLocInd p2 = (PlayerLocInd) obj2;
	
	    // Determine which field to sort on, then sort on that field
	    switch (column) {
		    case ID:
		    	rc = p1.getID().compareTo(p2.getID());
		    	break;
		    case INDICATOR:
		    	rc = p1.getINDICATOR().compareTo(p2.getINDICATOR());
		    	break;
		    case LOCATION:
		    	rc = p1.getLOCATION().compareTo(p2.getLOCATION());
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
