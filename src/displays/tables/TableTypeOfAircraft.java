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
import setting.Images;
import setting.Shells;
import setting.Shorten;
import setting.TextSetting;
import actions.RefreshTable;
import actions.cProgressBar;
import databases.jdbc;
import displays.DialogFactory;
import displays.MainForm;
import displays.TeleSplashScreen2016IP;


public class TableTypeOfAircraft {
	@SuppressWarnings("unchecked")
	private java.util.List playersTYPE9;
	private PlayerComparatorTYPE9 comparatorTYPE9;
	
	Shorten sh = new Shorten();
	ButtonsSetting bs = new ButtonsSetting();
	TextSetting ts = new TextSetting();

	static String P0,P1,P2,P3,P4,id,sqlSelect,string,select,sele,isiHal,jml_item="",string2="";
	static String des1,des2,des3,des4,des5,des6,des7,des8,des9,des10,des11,des12,des13,des14,des15,des16,des17,des18,des19,des20,des21;
	String tTOA,tWTC;
	
	static int baris=20,qq=0,jumlah=0,rowNo=0,qu=0;
	int dir=0,p=0,yu=0,hasil=0;
	
	Shell shell;
	Table table;
	TableItem[] selection;
	Button next,prev,first,last,Edit,Delete,DeleteAll;
	Text tPage,tTotPage,tGoToHal;
	
	private Text tTOA_Type9b,tWTC_Type9b;
//	private Combo lWTC_Type9b;
	Button bWTC_Type9b;
	
	Connection conn = null; // connection object
    Statement stmt = null; // statement object
    ResultSet rs = null; // result set object

    
	public TableTypeOfAircraft() { }

	public void koneksiDB(Shell pShell, String ptTOA, String ptWTC) {
		shell=pShell;
		tTOA=ptTOA;
		tWTC=ptWTC;
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
						    // Create the comparatorTYPE9 used for sorting
						    comparatorTYPE9 = new PlayerComparatorTYPE9();
						    comparatorTYPE9.setColumn(PlayerComparatorTYPE9.ID);
						    comparatorTYPE9.setDirection(PlayerComparatorTYPE9.ASCENDING);
						    
						    // Create the playersTYPE9
						    playersTYPE9 = new ArrayList();
						    
							String sTOA="",sWTC="",and="",where="";
							if (tTOA.compareTo("")!=0) sTOA="type9b LIKE '"+tTOA+"%'"; else sTOA="";
							if (tWTC.compareTo("")!=0) sWTC="type9c='"+tWTC+"'"; else sWTC="";
							if (!sTOA.isEmpty() && !sWTC.isEmpty()) { and=" AND "; } else { and=""; }
							if (!sTOA.isEmpty() || !sWTC.isEmpty()) { where=" WHERE "; } else { where=""; }
							
							stmt = conn.createStatement();
							String lihatsql = sele = "SELECT * FROM aircraft_wtc"+where+sTOA+and+sWTC+" ORDER BY id DESC";
							System.out.println("\nQuery: " + lihatsql);
							rs = stmt.executeQuery(lihatsql);
							rs.last();
							jumlah = rs.getRow();
							rs.beforeFirst();
							
							final String datatabel[][] = new String[baris][4];
							p = 0;
							rowNo = 0;
							while (rs.next() && (rowNo < baris)) {
								rowNo++;
								
								datatabel[p][0] = rs.getString("id"); id=datatabel[p][0]; if (id==null){ id=""; }
								datatabel[p][1] = rs.getString("type9b"); P1=datatabel[p][1]; if (P1==null){ P1=""; }
								datatabel[p][2] = rs.getString("type9c"); P2=datatabel[p][2]; if (P2==null){ P2=""; }
								
								p++;
								playersTYPE9.add(new PlayerTYPE9(id,P1,P2));
								progbr.bar.setSelection(rowNo);
							}
							progbr.finish();
							
//							if (getJumlah() != 0) {
								if (getJumlah()==0) DialogFactory.openInfoDialog(Shells.sh_tType9b, "Search Message","Data not found, your search has no result !!");
								formSearch();
						  		formList();
						  		formAddEdit();
							    sh.shellStyle(shell, "Type of Aircraft");
							    shell.addListener(SWT.Close, new Listener() {
									public void handleEvent(Event event) {
										connClose();
									}
								});
//					  		} else {
//					  			DialogFactory.openInfoDialog(Shells.sh_tType9b, "Search Message","Data not found, your search has no result !!");
//					  		}
	        			} catch (SQLException s) {
							DialogFactory.openInfoDialog(Shells.sh_tType9b, "Search Message", s.toString());
							s.printStackTrace();
							progbr.finish();
						} catch (java.lang.OutOfMemoryError hs) {
							DialogFactory.openInfoDialog(Shells.sh_tType9b, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
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
							
							final String datatabel[][] = new String[baris][4];
							for (rowNo=0; rowNo<(qq*baris); rowNo++) rs.next();
							
							p = 0;
							rowNo = 0;
							playersTYPE9.clear();
							while (rs.next() && (rowNo < baris)) {
								rowNo++;
								
								datatabel[p][0] = rs.getString("id"); id=datatabel[p][0]; if (id==null){ id=""; }
								datatabel[p][1] = rs.getString("type9b"); P1=datatabel[p][1]; if (P1==null){ P1=""; }
								datatabel[p][2] = rs.getString("type9c"); P2=datatabel[p][2]; if (P2==null){ P2=""; }
								
								TableItem item = new TableItem (table, SWT.NONE);
								item.setText (0,id); 
								item.setText (1,P1);
								item.setText (2,P2); 
								
								p++;
								playersTYPE9.add(new PlayerTYPE9(id,P1,P2));
								progbr.bar.setSelection(rowNo);
							}
							progbr.finish();
	        			} catch (SQLException s) {
							DialogFactory.openInfoDialog(Shells.sh_tType9b, "Search Message", s.toString());
							s.printStackTrace();
							progbr.finish();
						} catch (java.lang.OutOfMemoryError hs) {
							DialogFactory.openInfoDialog(Shells.sh_tType9b, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
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
  		final Group grpSearch = new Group(shell, SWT.NONE); sh.groupStyle(grpSearch, 9, "Search Type of Aircraft");
		Label label = new Label(grpSearch,SWT.NONE); sh.labelStyle1(label, "TYPE OF AIRCRAFT", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		final Text t9b_Search = new Text(grpSearch,SWT.BORDER); sh.textStyle(t9b_Search, 50, 4, SWT.LEFT, SWT.LEFT, sh.alphanum, ATSComponentSetting.b9b, true);
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle1(label, "WAKE TURB. CATEGORY", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		final Text t9c_Search = new Text(grpSearch,SWT.BORDER); sh.textStyle(t9c_Search, 20, 1, SWT.LEFT, SWT.LEFT, sh.letter, ATSComponentSetting.b9c, false);
	  	Button b9c_Search = new Button(grpSearch, SWT.PUSH); sh.buttonStyle(b9c_Search, "", "Choose Wake Turb. Cat. Value", bs.widthNavBtn, bs.colorBtn, SWT.CENTER, SWT.CENTER, Images.img_LIST);
	  	label = new Label(grpSearch,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Button Search = new Button(grpSearch, SWT.PUSH);
		Button Clear = new Button(grpSearch, SWT.PUSH);
		bs.SC(Search, Clear);
		
		if (!tTOA.isEmpty()) t9b_Search.setText(tTOA);
		if (!tWTC.isEmpty()) t9c_Search.setText(tWTC);

		b9c_Search.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RefreshTable.refresh9c(t9c_Search);
			}
		});
		
		t9b_Search.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					RefreshTable.refreshTableType9b(t9b_Search.getText(), t9c_Search.getText());
				}
			}
	    });
		
//		c9c_Search.addKeyListener(new KeyListener() {
//			public void keyPressed(KeyEvent e) {
//			}
//		
//			public void keyReleased(KeyEvent e) {
//				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
//					RefreshTable.refreshTableType9b(t9b_Search.getText(), c9c_Search.getText());
//				}
//			}
//	    });
		
		Search.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RefreshTable.refreshTableType9b(t9b_Search.getText(), t9c_Search.getText());
			}
		});
		
		Clear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				t9b_Search.setText(""); t9c_Search.setText("");
				RefreshTable.refreshTableType9b(t9b_Search.getText(), t9c_Search.getText());
			}
		});	
	}
	
  	private void formList() {
  		final Group Group = new Group(shell, SWT.NONE); sh.groupStyle(Group, 1, "List of Type of Aircraft(s)");
	    // Create the table
	    table = new Table(Group, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION); sh.tableStyle(table, true, true, 200, MainForm.getWidthRightTop());//1230);
	    // Create each of the columns, adding an event listener 
	    // that will set the appropriate fields into the comparatorTYPE9 
	    // and then call the fillTable helper method
	    final TableColumn ID = new TableColumn(table,SWT.LEFT); sh.tablecol(ID, "id", "", 0, false);
		ID.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorTYPE9.setColumn(PlayerComparatorTYPE9.ID);
				comparatorTYPE9.reverseDirection();
				fillTable(table);
			}
		});
		
		final TableColumn TOA = new TableColumn(table,SWT.LEFT); sh.tablecol(TOA, "TYPE OF AIRCRAFT", "Type of Aircraft", 200, false);
		TOA.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorTYPE9.setColumn(PlayerComparatorTYPE9.TOA);
		    	comparatorTYPE9.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn WTC = new TableColumn(table,SWT.LEFT); sh.tablecol(WTC, "WAKE TURB. CATEGORY", "Wake Turbulence Category", 200, false);
		WTC.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorTYPE9.setColumn(PlayerComparatorTYPE9.WTC);
		        comparatorTYPE9.reverseDirection();
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
	    TOA.addListener(SWT.Selection, sortListener);
	    WTC.addListener(SWT.Selection, sortListener);
	    
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
					sqlSelect="SELECT * FROM aircraft_wtc WHERE id='"+string+"'";
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
				if (selection.length==0 || sqlSelect.equals("")) { DialogFactory.openInfoAtLeast(Shells.sh_tType9b,DialogFactory.delete); } 
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
					if (string.equals("")) { DialogFactory.openInfoAtLeast(Shells.sh_tType9b,DialogFactory.delete); } 
					else {
						DialogFactory.openYesNoDelete(Shells.sh_tType9b);
						boolean tentu = DialogFactory.getPenentuan(); 
						if (tentu == true) {
							String del = "DELETE FROM aircraft_wtc WHERE id='" + string +"'";
							jdbc.delete(del);
//							string=""; sqlSelect="";
//							table.removeAll(); playersTYPE9.clear(); RefreshTable.refreshTableType9b(tTOA, tWTC);
							if (getJumlah() > 1) {
								string=""; sqlSelect="";
								table.removeAll(); playersTYPE9.clear(); RefreshTable.refreshTableType9b(tTOA, tWTC);
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
				if (getJumlah() == 0) { DialogFactory.openInfoDialog(Shells.sh_tType9b, "Delete Message", "There's no data !!"); }
				else {
					DialogFactory.openYesNoDelete(Shells.sh_tType9b);
					boolean tentu = DialogFactory.getPenentuan(); 
					if (tentu == true) {
						String dele = getSele().toString(); 
						dele = dele.replace("SELECT * FROM", "DELETE FROM");
						dele = dele.replace(" ORDER BY tgl DESC", "");
						jdbc.delete(dele);
						table.removeAll(); playersTYPE9.clear(); shell.close();
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
			
			tTOA_Type9b.setText(jdbc.get9b());
			tWTC_Type9b.setText(jdbc.get9c());

			//pengosongan ID
			sqlSelect=""; string="";
		} else { DialogFactory.openInfoAtLeast(Shells.sh_tType9b,DialogFactory.edit); } 
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
	    
	    Collections.sort(playersTYPE9, comparatorTYPE9);
	    for (Iterator itr = playersTYPE9.iterator(); itr.hasNext();) {
	    	PlayerTYPE9 player = (PlayerTYPE9) itr.next();
	    	TableItem item = new TableItem(table, SWT.NONE);
	    	int c = 0;
	    	item.setText(c++, player.getID());
	    	item.setText(c++, player.getTOA());
	    	item.setText(c++, player.getWTC());
	    }
	    
	    // sort indicator
	    table.setSortDirection(dir);
        //table.clearAll();
        
	    // Turn drawing back on
	    table.setRedraw(true);
  	}

  	void formAddEdit() {
  		final Group Group = new Group(shell, SWT.NONE); sh.groupStyle(Group, 1, "Add/Edit Type of Aircraft");
		Composite comp = new Composite(Group, SWT.NONE); sh.compositeStyle(comp, 2, SWT.LEFT, SWT.CENTER);
		Label label = new Label(comp,SWT.NONE); sh.labelStyle1(label, "TYPE OF AIRCRAFT", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		tTOA_Type9b = new Text(comp,SWT.BORDER); sh.textStyle(tTOA_Type9b, 50, 4, SWT.LEFT, SWT.LEFT, sh.alphanum, ATSComponentSetting.b9b, true);
	  	label = new Label(comp,SWT.NONE); sh.labelStyle1(label, "WAKE TURB. CATEGORY", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY); 
	  	Composite cWTC = new Composite(comp, SWT.NONE); sh.compositeStyle(cWTC, 2, SWT.LEFT, SWT.CENTER);
	  	tWTC_Type9b = new Text(cWTC,SWT.BORDER); sh.textStyle(tWTC_Type9b, 20, 1, SWT.LEFT, SWT.LEFT, sh.letter, ATSComponentSetting.b9c, false);
	  	bWTC_Type9b = new Button(cWTC, SWT.PUSH); sh.buttonStyle(bWTC_Type9b, "", "Choose Wake Turb. Cat. Value", bs.widthNavBtn, bs.colorBtn, SWT.CENTER, SWT.CENTER, Images.img_LIST);
//	  	lWTC_Type9b = new Combo(comp, SWT.READ_ONLY); sh.comboStyle(lWTC_Type9b, ATSComponentSetting.b9c, 50, SWT.CENTER);
//	  	lWTC_Type9b.setItems(Combos.cType9c);
	  	
	  	Composite typeA = new Composite(Group, SWT.NONE); sh.compositeStyle(typeA, 1, SWT.LEFT, SWT.CENTER);
		label = new Label(typeA, SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelStyle(label, "", MainForm.getWidthRightTop(), SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
		
		Composite Btn = new Composite(Group, SWT.NONE); sh.compositeStyle(Btn, 3, SWT.CENTER, SWT.CENTER);
		Button Save = new Button(Btn, SWT.PUSH);
		Button Update = new Button(Btn, SWT.PUSH);
		Button Clear = new Button(Btn, SWT.PUSH);
		bs.SUC(Save, Update, Clear);
		
		bWTC_Type9b.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RefreshTable.refresh9c(tWTC_Type9b);
			}
		});
		
		Save.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {	
				if (tTOA_Type9b.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_tType9b, "Save Data", "The value in field Type of Aircraft is required."); tTOA_Type9b.setFocus(); }
				else if (!tTOA_Type9b.getText().isEmpty() && tTOA_Type9b.getText().length()<2) { DialogFactory.openInfoDialog(Shells.sh_tType9b, "Save Data", "Please insert 2 - 4 CHARACTERS for field Type of Aircraft !!"); tTOA_Type9b.setFocus(); }
				else if (tWTC_Type9b.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_tType9b, "Save Data", "The value in field Wake Turbulence Category is required."); tWTC_Type9b.setFocus(); }
				else {
					String insert = "INSERT INTO aircraft_wtc (type9b,type9c) VALUES ('"+tTOA_Type9b.getText()+"','"+tWTC_Type9b.getText()+"')";
					jdbc.connect(insert);
					discard(); string2=""; string=""; sqlSelect="";
					//buat ng'refreshnya
					table.removeAll(); playersTYPE9.clear(); RefreshTable.refreshTableType9b(tTOA, tWTC);
				}
			}
		});
		
		Update.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {	
				if (sett().equals("")) { DialogFactory.openInfoDialog(Shells.sh_tType9b, "Update Data","At least one row must be selected if you want to update your message !!"); } 
				else if (tTOA_Type9b.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_tType9b, "Update Data", "The value in field Type of Aircraft is required."); tTOA_Type9b.setFocus(); }
				else if (!tTOA_Type9b.getText().isEmpty() && tTOA_Type9b.getText().length()<2) { DialogFactory.openInfoDialog(Shells.sh_tType9b, "Update Data", "Please insert 2 - 4 CHARACTERS for field Type of Aircraft !!"); tTOA_Type9b.setFocus(); }
				else if (tWTC_Type9b.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_tType9b, "Update Data", "The value in field Wake Turbulence Category is required."); tWTC_Type9b.setFocus(); }
				else {
					String update = "UPDATE aircraft_wtc SET type9b='"+tTOA_Type9b.getText()+"',type9c='"+tWTC_Type9b.getText()+"' WHERE id='"+sett()+"'";
					jdbc.update(update);
					discard(); string2=""; sqlSelect="";
					//buat ng'refreshnya
					table.removeAll(); playersTYPE9.clear(); RefreshTable.refreshTableType9b(tTOA, tWTC);
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
  		tTOA_Type9b.setText("");
  		tWTC_Type9b.setText(""); 
  	}
  	
  	private static void set(String string) { TableTypeOfAircraft.string2 = string; }
  	public static String sett() { return TableTypeOfAircraft.string2; }
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
class PlayerTYPE9 {
	private String ID;
	private String TOA;
	private String WTC;
	
	public PlayerTYPE9(String ID, String TOA, String WTC) {
		this.ID = ID;
		this.TOA = TOA;
		this.WTC = WTC;
	}

	public String getID() { return ID; }
	public String getTOA() { return TOA; }
	public String getWTC() { return WTC; }
}

@SuppressWarnings("unchecked")
class PlayerComparatorTYPE9 implements Comparator {
	public static final int ID = 0;
	public static final int TOA = 1;
	public static final int WTC = 2;
	
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
	    PlayerTYPE9 p1 = (PlayerTYPE9) obj1;
	    PlayerTYPE9 p2 = (PlayerTYPE9) obj2;
	
	    // Determine which field to sort on, then sort on that field
	    switch (column) {
		    case ID:
		    	rc = p1.getID().compareTo(p2.getID());
		    	break;
		    case TOA:
		    	rc = p1.getTOA().compareTo(p2.getTOA());
		    	break;
		    case WTC:
		    	rc = p1.getWTC().compareTo(p2.getWTC());
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