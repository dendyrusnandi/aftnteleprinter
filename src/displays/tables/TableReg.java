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
import setting.ErrMsg;
import setting.Shells;
import setting.Shorten;
import setting.TextSetting;
import actions.RefreshTable;
import actions.SendSave;
import actions.cProgressBar;
import databases.jdbc;
import displays.DialogFactory;
import displays.MainForm;
import displays.TeleSplashScreen2016IP;
import displays.forms.ATSForms;


public class TableReg {
	@SuppressWarnings("unchecked")
	private java.util.List playersReg;
	private PlayerComparatorReg comparatorReg;
	
	Shorten sh = new Shorten();
	ButtonsSetting bs = new ButtonsSetting();
	TextSetting ts = new TextSetting();
	
	SendSave ss = new SendSave();
	ErrMsg em = new ErrMsg();
	ATSComponentSetting acs = new ATSComponentSetting();

	static String P0="",P1="",P2="",P3="",P4="",P5="",P6="",P7="",id="",sqlSelect="",string="",select="",sele="",isiHal,jml_item="",string2="";
	String pType9b="",pType18="",tes="";
	
	Shell shell;
	Table table;
	TableItem [] selection;
	Button next,prev,first,last,Edit,Delete,DeleteAll;//,go;
	Text tPage, tTotPage, tGoToHal;
	
	private Text t9b_REG,tReg_REG,tSel_REG,tOpr_REG;
	public static Text t10a_REG,t10b_REG,tPbn_REG;
	private Button b10a_REG,b10b_REG,bPBN_REG;
	
	int dir=0,p=0,yu=0,hasil=0;
	static int baris=15,qq=0,jumlah=0,rowNo=0,qu=0;
	
	Connection conn = null; // connection object
    Statement stmt = null; // statement object
    ResultSet rs = null; // result set object
	
	public TableReg() {
		tes = "new";//jdbc.getVersion().toString();
	}
	
	public void koneksiDB(Shell pShell, String type9b, String location) {
		shell = pShell;
		pType9b = type9b;
		pType18 = location;
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
						    // Create the comparatorReg used for sorting
						    comparatorReg = new PlayerComparatorReg();
						    comparatorReg.setColumn(PlayerComparatorReg.ID);
						    comparatorReg.setDirection(PlayerComparatorReg.ASCENDING);
						    
						    // Create the playersReg
						    playersReg = new ArrayList();
				
							String sToA="",sReg="";
							if (pType9b.compareTo("")!=0) sToA=" AND type9b LIKE '"+pType9b+"%'"; else sToA="";
							if (pType18.compareTo("")!=0) sReg=" AND type18 LIKE '"+pType18+"%'"; else sReg="";
							String kriteria = sToA+sReg;
							if (kriteria.startsWith(" AND")) kriteria = kriteria.replaceFirst(" AND", "");
							if (!kriteria.isEmpty()) kriteria = kriteria.replaceFirst(kriteria, " WHERE"+kriteria);
							
							stmt = conn.createStatement();
							String lihatsql = sele = "SELECT * FROM aircraft_reg"+kriteria+" ORDER BY id DESC";				
							System.out.println("\nQuery: " + lihatsql);
							rs = stmt.executeQuery(lihatsql);
							rs.last();
							jumlah = rs.getRow();
							System.out.println("Jumlah data=" + jumlah);
							rs.beforeFirst();
							
							final String datatabel[][] = new String[baris][8];
							p = 0;
							rowNo = 0;
							while (rs.next() && (rowNo < baris)) {
								rowNo++;
								
								datatabel[p][0] = rs.getString("id"); id=datatabel[p][0]; if (id==null){ id=""; }
								datatabel[p][1] = rs.getString("type18"); P1=datatabel[p][1]; if (P1==null){ P1=""; }
								datatabel[p][2] = rs.getString("type9b"); P2=datatabel[p][2]; if (P2==null){ P2=""; }
								datatabel[p][3] = rs.getString("type10a"); P3=datatabel[p][3]; if (P3==null){ P3=""; }
								datatabel[p][4] = rs.getString("type10b"); P4=datatabel[p][4]; if (P4==null){ P4=""; }
								datatabel[p][5] = rs.getString("type18Opr"); P5=datatabel[p][5]; if (P5==null){ P5=""; }
								datatabel[p][6] = rs.getString("type18Pbn"); P6=datatabel[p][6]; if (P6==null){ P6=""; }
								datatabel[p][7] = rs.getString("type18Sel"); P7=datatabel[p][7]; if (P7==null){ P7=""; }

								p++;
								playersReg.add(new PlayerReg(id,P1,P2,P3,P4,P5,P6,P7));
								progbr.bar.setSelection(rowNo);
							} //end of while
							progbr.finish();
							
//							if (getJumlah() != 0) {
								if (getJumlah()==0) DialogFactory.openInfoDialog(Shells.sh_tReg, "Search Message","Data not found, your search has no result !!");
								formSearch();
						  		formList();
						  		formAddEdit();
							    sh.shellStyle(shell, "REG/");
							    shell.addListener(SWT.Close, new Listener() {
									public void handleEvent(Event event) {
										connClose();
									}
								});
//					  		} else {
//					  			DialogFactory.openInfoDialog(Shells.sh_tReg, "Search Message","Data not found, your search has no result !!");
//					  		}
	        			} catch (SQLException s) {
							DialogFactory.openInfoDialog(Shells.sh_tReg, "Search Message", s.toString());
							s.printStackTrace();
							progbr.finish();
		        		} catch (java.lang.OutOfMemoryError hs) {
		        			hs.printStackTrace();
							DialogFactory.openInfoDialog(Shells.sh_tReg, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
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
							
							final String datatabel[][] = new String[baris][8];
							for (rowNo=0; rowNo<(qq*baris); rowNo++) rs.next();
							
							p = 0;
							rowNo = 0;
							playersReg.clear();
							while (rs.next() && (rowNo < baris)) {
								rowNo++;
								
								datatabel[p][0] = rs.getString("id"); id=datatabel[p][0]; if (id==null){ id=""; }
								datatabel[p][1] = rs.getString("type18"); P1=datatabel[p][1]; if (P1==null){ P1=""; }
								datatabel[p][2] = rs.getString("type9b"); P2=datatabel[p][2]; if (P2==null){ P2=""; }
								datatabel[p][3] = rs.getString("type10a"); P3=datatabel[p][3]; if (P3==null){ P3=""; }
								datatabel[p][4] = rs.getString("type10b"); P4=datatabel[p][4]; if (P4==null){ P4=""; }
								datatabel[p][5] = rs.getString("type18Opr"); P5=datatabel[p][5]; if (P5==null){ P5=""; }
								datatabel[p][6] = rs.getString("type18Pbn"); P6=datatabel[p][6]; if (P6==null){ P6=""; }
								datatabel[p][7] = rs.getString("type18Sel"); P7=datatabel[p][7]; if (P7==null){ P7=""; }

								TableItem item = new TableItem (table, SWT.NONE);
								item.setText (0,id); 
								item.setText (1,P1);
								item.setText (2,P2);
								item.setText (3,P3);
								item.setText (4,P4);
								item.setText (5,P5);
								item.setText (6,P6);
								item.setText (7,P7);

								p++;
								playersReg.add(new PlayerReg(id,P1,P2,P3,P4,P5,P6,P7));
								progbr.bar.setSelection(rowNo);
							} //end of while
							progbr.finish();
	        			} catch (SQLException s) {
							DialogFactory.openInfoDialog(Shells.sh_tReg, "Search Message", s.toString());
							s.printStackTrace();
							progbr.finish();
		        		} catch (java.lang.OutOfMemoryError hs) {
		        			hs.printStackTrace();
							DialogFactory.openInfoDialog(Shells.sh_tReg, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
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
  		final Group grpSearch = new Group(shell, SWT.NONE); sh.groupStyle(grpSearch, 8, "Search REG/");
  		Label label = new Label(grpSearch,SWT.NONE); sh.labelStyle1(label, "TYPE", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		final Text tType9b_Search = new Text(grpSearch,SWT.BORDER); sh.textStyle(tType9b_Search, 50, 4, SWT.LEFT, SWT.LEFT, sh.alphanum, ATSComponentSetting.b9b, true); 
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle1(label, "REG/", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		final Text tReg_Search = new Text(grpSearch,SWT.BORDER); sh.textStyle(tReg_Search, 80, 7, SWT.LEFT, SWT.LEFT, sh.alphanum, ATSComponentSetting.b18reg, true);
		label = new Label(grpSearch,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Button Search = new Button(grpSearch, SWT.PUSH);
		Button Clear = new Button(grpSearch, SWT.PUSH);
		bs.SC(Search, Clear);

		if (!pType9b.isEmpty()) tType9b_Search.setText(pType9b);
		if (!pType18.isEmpty()) tReg_Search.setText(pType18);
		
		tReg_Search.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					RefreshTable.refreshTableReg(tType9b_Search.getText(), tReg_Search.getText());
				}
			}
	    });
		
		tType9b_Search.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					RefreshTable.refreshTableReg(tType9b_Search.getText(), tReg_Search.getText());
				}
			}
	    });
		
		Search.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RefreshTable.refreshTableReg(tType9b_Search.getText(), tReg_Search.getText());
			}
		});
		
		Clear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				tReg_Search.setText(""); tType9b_Search.setText("");
				RefreshTable.refreshTableReg(tType9b_Search.getText(), tReg_Search.getText());
			}
		});	
	}
	
  	void formList() {
 		final Group Group = new Group(shell, SWT.NONE); sh.groupStyle(Group, 1, "List of REG/(s)");
	    // Create the table
	    table = new Table(Group, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION); sh.tableStyle(table, true, true, 200, MainForm.getWidthRightTop());//1230);
	    // Create each of the columns, adding an event listener 
	    // that will set the appropriate fields into the comparatorReg 
	    // and then call the fillTable helper method
		final TableColumn ID = new TableColumn(table,SWT.LEFT); sh.tablecol(ID, "id", "", 0, false);
		ID.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorReg.setColumn(PlayerComparatorReg.ID);
		        comparatorReg.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn TYPE18 = new TableColumn(table,SWT.LEFT); sh.tablecol(TYPE18, "REG/", ATSComponentSetting.b18reg, 80, false);
		TYPE18.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorReg.setColumn(PlayerComparatorReg.TYPE18);
		        comparatorReg.reverseDirection();
		        fillTable(table);
			}
		});
		
		final TableColumn TYPE9B = new TableColumn(table,SWT.LEFT); sh.tablecol(TYPE9B, "TYPE", ATSComponentSetting.b9b, 60, false);
		TYPE9B.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorReg.setColumn(PlayerComparatorReg.TYPE9B);
		        comparatorReg.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn TYPE10A = new TableColumn(table,SWT.LEFT); sh.tablecol(TYPE10A, "EQUIPMENT AND CAPABILITIES", ATSComponentSetting.b10a, 240, false);
		TYPE10A.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorReg.setColumn(PlayerComparatorReg.TYPE10A);
		        comparatorReg.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn TYPE10B = new TableColumn(table,SWT.LEFT); sh.tablecol(TYPE10B, "EQUIPMENT AND CAPABILITIES", ATSComponentSetting.b10b, 240, false);
		TYPE10B.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorReg.setColumn(PlayerComparatorReg.TYPE10B);
		        comparatorReg.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn OPERATOR = new TableColumn(table,SWT.LEFT); sh.tablecol(OPERATOR, "OPR/", ATSComponentSetting.b18opr, 300, false);
		OPERATOR.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorReg.setColumn(PlayerComparatorReg.OPERATOR);
		        comparatorReg.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn PBN = new TableColumn(table,SWT.LEFT); sh.tablecol(PBN, "PBN/", ATSComponentSetting.b18pbn, 150, false);
		PBN.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorReg.setColumn(PlayerComparatorReg.PBN);
		        comparatorReg.reverseDirection();
		        fillTable(table);
			}
		});

		final TableColumn tcSEL = new TableColumn(table,SWT.LEFT); sh.tablecol(tcSEL, "SEL/", ATSComponentSetting.b18sel, 60, false);
		tcSEL.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
		    	comparatorReg.setColumn(PlayerComparatorReg.SEL);
		        comparatorReg.reverseDirection();
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
	    TYPE18.addListener(SWT.Selection, sortListener);
	    TYPE9B.addListener(SWT.Selection, sortListener);
	    TYPE10A.addListener(SWT.Selection, sortListener);
	    TYPE10B.addListener(SWT.Selection, sortListener);
	    OPERATOR.addListener(SWT.Selection, sortListener);
	    PBN.addListener(SWT.Selection, sortListener);
	    tcSEL.addListener(SWT.Selection, sortListener);
	    
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
					sqlSelect="SELECT * FROM aircraft_reg WHERE id='"+string+"'";
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
				if (selection.length==0 || sqlSelect.equals("")) { DialogFactory.openInfoAtLeast(Shells.sh_tReg,DialogFactory.delete); } 
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
					if (string.equals("")) { DialogFactory.openInfoAtLeast(Shells.sh_tReg,DialogFactory.delete); } 
					else {
						DialogFactory.openYesNoDelete(Shells.sh_tReg);
						boolean tentu = DialogFactory.getPenentuan(); 
						if (tentu == true) {
							String del = "DELETE FROM aircraft_reg WHERE id='" + string +"'";
							jdbc.delete(del);
//							string=""; sqlSelect="";
//							table.removeAll(); playersReg.clear(); RefreshTable.refreshTableReg(pType9b, pType18);
							if (getJumlah() > 1) {
								string=""; sqlSelect="";
								table.removeAll(); playersReg.clear(); RefreshTable.refreshTableReg(pType9b, pType18);
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
				if (getJumlah() == 0) { DialogFactory.openInfoDialog(Shells.sh_tReg, "Delete Message", "There's no data !!"); }
				else {
					DialogFactory.openYesNoDelete(Shells.sh_tReg);
					boolean tentu = DialogFactory.getPenentuan(); 
					if (tentu == true) {
						String dele = getSele().toString(); 
						dele = dele.replace("SELECT * FROM", "DELETE FROM");
						dele = dele.replace(" ORDER BY type9b ASC", "");
						jdbc.delete(dele);
						table.removeAll(); playersReg.clear(); shell.close();
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
			
			t9b_REG.setText(jdbc.get9b());
			tReg_REG.setText(jdbc.get18());
			t10a_REG.setText(jdbc.get10a());
			t10b_REG.setText(jdbc.get10b());
			tOpr_REG.setText(jdbc.get18Opr());
			tPbn_REG.setText(jdbc.get18Pbn());
			tSel_REG.setText(jdbc.get18Sel());
			
			//pengosongan ID
			sqlSelect=""; string="";
		} else { DialogFactory.openInfoAtLeast(Shells.sh_tReg,DialogFactory.edit); } 
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
	
	void formAddEdit() {
		int iWidth=10;
		final Group Group = new Group(shell, SWT.CENTER); sh.groupStyle(Group, 1, "Add/Edit REG/");
		Composite comp1 = new Composite(Group, SWT.NONE); sh.compositeStyle(comp1, 4, SWT.LEFT, SWT.CENTER);
		Label label = new Label(comp1,SWT.NONE); sh.labelStyle1(label, " 9.TYPE", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		t9b_REG = new Text(comp1, SWT.BORDER);
		label = new Label(comp1,SWT.NONE); sh.labelStyle(label, "", iWidth, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		Composite comp3 = new Composite(comp1, SWT.NONE); sh.compositeStyle(comp3, 7, SWT.LEFT, SWT.CENTER);		
		label = new Label(comp3,SWT.NONE); sh.labelStyle1(label, "10.EQUIPMENT AND CAPABILITIES", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		t10a_REG = new Text(comp3,SWT.BORDER); 
		Button btnChk = new Button(comp3, SWT.PUSH);
		b10a_REG = new Button(comp3, SWT.PUSH);
		label = new Label(comp3,SWT.CENTER); sh.labelStyle1(label, "/", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		t10b_REG = new Text(comp3,SWT.BORDER);
		b10b_REG = new Button(comp3, SWT.PUSH);
		
		label = new Label(comp1,SWT.NONE); sh.labelStyle1(label, "18.REG/", SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.MANDATORY);
		tReg_REG = new Text(comp1,SWT.BORDER);
		label = new Label(comp1,SWT.NONE); sh.labelStyle(label, "", iWidth, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		Composite comp2 = new Composite(comp1, SWT.NONE); sh.compositeStyle(comp2, 9, SWT.LEFT, SWT.CENTER);
		label = new Label(comp2,SWT.TOP); sh.labelStyle1(label, "18.SEL/", SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tSel_REG = new Text(comp2,SWT.BORDER);
		label = new Label(comp2,SWT.NONE); sh.labelStyle(label, "", iWidth, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(comp2,SWT.NONE); sh.labelStyle1(label, "18.PBN/", SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tPbn_REG = new Text(comp2, SWT.BORDER); 
		bPBN_REG = new Button(comp2, SWT.PUSH);
		label = new Label(comp2,SWT.NONE); sh.labelStyle(label, "", iWidth, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(comp2,SWT.TOP); sh.labelStyle1(label, "18.OPR/", SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.MANDATORY);
		tOpr_REG = new Text(comp2, SWT.BORDER | SWT.MULTI | SWT.WRAP);
		
		
		
		
		
//		final Group Group = new Group(shell, SWT.CENTER); sh.groupStyle(Group, 1, "Add/Edit REG/");
//		Composite comp1 = new Composite(Group, SWT.NONE); sh.compositeStyle(comp1, 2, SWT.LEFT, SWT.CENTER);
//		Label label = new Label(comp1,SWT.NONE); sh.labelStyle1(label, " 9.TYPE OF AIRCRAFT", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY); 
//		t9b_REG = new Text(comp1, SWT.BORDER);
//		label = new Label(comp1,SWT.NONE); sh.labelStyle1(label, "18.REG/", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
//		tReg_REG = new Text(comp1,SWT.BORDER);
//		
//		label = new Label(comp1,SWT.NONE); sh.labelStyle1(label, "10.EQUIPMENT AND CAPABILITIES", SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.MANDATORY);
//		Composite comp3 = new Composite(comp1, SWT.NONE); sh.compositeStyle(comp3, 7, SWT.LEFT, SWT.CENTER);
//		t10a_REG = new Text(comp3,SWT.BORDER); 
//		Button btnChk = new Button(comp3, SWT.PUSH);
//		b10a_REG = new Button(comp3, SWT.PUSH);
//		label = new Label(comp3,SWT.CENTER); sh.labelStyle1(label, "/", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
//		t10b_REG = new Text(comp3,SWT.BORDER);
//		b10b_REG = new Button(comp3, SWT.PUSH);
//		
//		label = new Label(comp1,SWT.TOP); sh.labelStyle1(label, "18.OPR/", SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.MANDATORY);
//		tOpr_REG = new Text(comp1, SWT.BORDER | SWT.MULTI | SWT.WRAP);
//		label = new Label(comp1,SWT.NONE); sh.labelStyle1(label, "18.PBN/", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
//		Composite comp5 = new Composite(comp1, SWT.NONE); sh.compositeStyle(comp5, 2, SWT.LEFT, SWT.CENTER);
//		tPbn_REG = new Text(comp5, SWT.BORDER); 
//		bPBN_REG = new Button(comp5, SWT.PUSH);
		
//		ATSForms.b10(b10a_REG, b10b_REG, btnChk, t10a_REG, "sreg");
//		ATSForms.bPBN(bPBN_REG);
		ATSForms.listener10("REG", b10a_REG, b10b_REG, btnChk);
		ATSForms.listenerPBN("REG", bPBN_REG);
		acs.setCompReg(tReg_REG, t9b_REG, t10a_REG, t10b_REG, tOpr_REG, tPbn_REG, tSel_REG);
		
		b10a_REG.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RefreshTable.refresh10a(t10a_REG);
			}
		});		
		b10b_REG.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RefreshTable.refresh10b(t10b_REG);
			}
		});
		bPBN_REG.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RefreshTable.refreshPBN(tPbn_REG);
			}
		});
		
		Composite typeA = new Composite(Group, SWT.NONE); sh.compositeStyle(typeA, 1, SWT.LEFT, SWT.CENTER);
		label = new Label(typeA, SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelStyle(label, "", MainForm.getWidthRightTop(), SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
		
		Composite Btn = new Composite(Group, SWT.NONE); sh.compositeStyle(Btn, 3, SWT.CENTER, SWT.CENTER);
		Button Save = new Button(Btn, SWT.PUSH);
		Save.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {	
				ss.krit1="";ss.krit2="";ss.krit3="";ss.krit4="";ss.krit5="";
				ss.t10b=""; ss.sdbguv=""; ss.sprotek10a=""; ss.sprotekpbn="";
				ss.t10b = t10b_REG.getText(); 
				ss.t10a = t10a_REG.getText();
				ss.tpbn = tPbn_REG.getText();;

				ss.validasi10a(ss.t10a);
				ss.validasi18_10(ss.t10b, ss.t10a);
				ss.proteksi18();
				ss.validasipbn(ss.tpbn);
		    	ss.proteksi18REG(ss.tpbn);
		    	
				//9
				if (tReg_REG.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data", "The value in field REG/ is required."); tReg_REG.setFocus(); }
				else if (t9b_REG.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data", "The value in field TYPE OF AIRCRAFT is required."); t9b_REG.setFocus(); }
				else if (!t9b_REG.getText().isEmpty() && t9b_REG.getText().length()<2) { DialogFactory.openInfoDialog(Shells.sh_tReg, "Save Data", "Please insert 2 - 4 CHARACTERS for field TYPE OF AIRCRAFT !!"); t9b_REG.setFocus(); }
				//OPR
				else if (tOpr_REG.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data", "The value in field OPR/ is required."); tOpr_REG.setFocus(); }
//				else if (ss.tpbn.isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data", "The value in field PBN/ is required."); tPbn_REG.setFocus(); }
				//10
		        else if (ss.t10a.equals("")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is required."); t10a_REG.setFocus(); }
				else if (ss.t10b.equals("")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is required."); t10b_REG.setFocus(); }
				else if ( (ss.t10b.contains("B1") || ss.t10b.contains("U1") || ss.t10b.contains("V1")) && (ss.t10b.contains("B2") || ss.t10b.contains("U2") || ss.t10b.contains("V2"))) { DialogFactory.openInfoDialog(Shells.sh_tReg, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10a.contains("N") && ss.t10a.contains("S")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","Invalid EQUIPMENT AND CAPABILITIES value.\n\n'N' is not allowed, if 'S' has inserted into field EQUIPMENT AND CAPABILITIES and vice versa.\n\nSee the instruction below the EQUIPMENT AND CAPABILITIES text !"); t10a_REG.setFocus(); }
				else if (ss.t10b.contains("N") && (ss.t10b.contains("A") || ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X") || ss.t10b.contains("B1") || ss.t10b.contains("B2") || ss.t10b.contains("U1") || ss.t10b.contains("U2") || ss.t10b.contains("V1") || ss.t10b.contains("V2") || ss.t10b.contains("D1") || ss.t10b.contains("G1"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","Invalid EQUIPMENT AND CAPABILITIES value.\n\nSSR Modes A and C and SSR Mode S are not allowed,\nif 'N' has inserted into field EQUIPMENT AND CAPABILITIES and vice versa.\n\nSee the instruction below the EQUIPMENT AND CAPABILITIES text !"); t10b_REG.setFocus(); }
				else if (!ss.t10b.equals("") && (ss.sdbguv.contains("tdksesuai"))) { DialogFactory.openInfoDialog(Shells.sh_tReg, "Save Data", em.infoInvalid10b+"\nInsert B1 or B2 or U1 or U2 or V1 or V2 or D1 or G1"); t10b_REG.setFocus();; }		
				else if (!ss.t10a.equals("") && (ss.sprotek10a.contains("ngaco"))) { DialogFactory.openInfoDialog(Shells.sh_tReg, "Save Data", em.infoInvalid10aFix); t10a_REG.setFocus(); }		
				//N, harus sendiri
				else if (ss.t10b.contains("N") && ss.t10b.length()>1) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("A") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("C") && (ss.t10b.contains("A") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("E") && (ss.t10b.contains("C") || ss.t10b.contains("A") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("H") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("A") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("I") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("A") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("L") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("A") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("P") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("A") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("S") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("A") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("X") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("A"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
		    	//PBN
		        else if (!ss.tpbn.equals("") && (ss.tpbn.length() > 16 || ss.tpbn.contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","Invalid PBN/ indicator value.\nSee the instruction below the PBN/ indicator text !"); tPbn_REG.setFocus(); }
		        else if (!ss.tpbn.equals("") && ss.sprotekpbn.contains("ngaco")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data", em.infoInvalidPbnFix); tPbn_REG.setFocus(); }
				else if (ss.t10a.contains("R") && ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","Field OTHER INFORMATION with indicator PBN/ should be filled, if the letter 'R' is used in field EQUIPMENT AND CAPABILITIES."); tPbn_REG.setFocus(); }
				else if (!ss.t10a.contains("R") && !ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","Field EQUIPMENT AND CAPABILITIES should be contained the letter 'R', if field OTHER INFORMATION is filled."); t10a_REG.setFocus(); }
		    	//G
				else if (!ss.t10a.contains("G") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","If B1, B2, C1, C2, D1, D2, O1 or O2 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'G' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("G") && !ss.krit2.equals("") && (ss.krit2.contains("B2") || ss.krit2.contains("C2") || ss.krit2.contains("D2") || ss.krit2.contains("O2"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","If B1, B2, C1, C2, D1, D2, O1 or O2 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'G' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
				//D
				else if (!ss.t10a.contains("D") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","If B1, B3, C1, C3, D1, D3, O1 or O3 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("D") && !ss.krit3.equals("") && (ss.krit3.contains("B3") || ss.krit3.contains("C3") || ss.krit3.contains("D3") || ss.krit3.contains("O3"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","If B1, B3, C1, C3, D1, D3, O1 or O3 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
		    	//I
				else if (!ss.t10a.contains("I") && !ss.krit5.equals("")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","If B1, B5 or C1 are filled in field OTHER INFORMATION with PBN/ indicator, then an 'I' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","If B1, B5 or C1 are filled in field OTHER INFORMATION with PBN/ indicator, then an 'I' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
				//DI
				else if (!ss.t10a.contains("D") && !ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("D") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("I") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("I") && (ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10a_REG.setFocus(); }
		    	//OD / SD
				else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit1.contains("B1")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("D") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("D") && ss.krit1.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Save Data", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10a_REG.setFocus(); }
		    	
				else {
					String insert = "INSERT INTO aircraft_reg (type9b,type18,type10a,type10b,type18Opr,type18Pbn,type18Sel) " +
							"VALUES ('"+t9b_REG.getText()+"','"+tReg_REG.getText()+"','"+t10a_REG.getText()+"','"+ss.t10b+"','"+
							tOpr_REG.getText()+"','"+ss.tpbn+"','"+tSel_REG.getText()+"')";
					jdbc.connect(insert);
					discard(); string2=""; sqlSelect="";
					//buat ng'refreshnya
					table.removeAll(); playersReg.clear(); RefreshTable.refreshTableReg(pType9b, pType18);
				}
			}
		});
		
		Button Update = new Button(Btn, SWT.PUSH);
		Update.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {	
				ss.krit1="";ss.krit2="";ss.krit3="";ss.krit4="";ss.krit5="";
				ss.t10b=""; ss.sdbguv=""; ss.sprotek10a=""; ss.sprotekpbn="";
				ss.t10b = t10b_REG.getText(); 
				ss.t10a = t10a_REG.getText();
				ss.tpbn = tPbn_REG.getText();

				ss.validasi10a(ss.t10a);
				ss.validasi18_10(ss.t10b, ss.t10a);
				ss.proteksi18();
				ss.validasipbn(ss.tpbn);
		    	ss.proteksi18REG(ss.tpbn);
		    			    	
		    	if (sett().equals("")) { DialogFactory.openInfoDialog(Shells.sh_tReg, "Update Data","At least one row must be selected if you want to update your message !!"); }
				//9
		    	else if (tReg_REG.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data", "The value in field REG/ is required."); tReg_REG.setFocus(); }
				else if (t9b_REG.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data", "The value in field TYPE OF AIRCRAFT is required."); t9b_REG.setFocus(); }
				else if (!t9b_REG.getText().isEmpty() && t9b_REG.getText().length()<2) { DialogFactory.openInfoDialog(Shells.sh_tReg, "Update Data", "Please insert 2 - 4 CHARACTERS for field TYPE OF AIRCRAFT !!"); t9b_REG.setFocus(); }
		    	//OPR
				else if (tOpr_REG.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data", "The value in field OPR/ is required."); tOpr_REG.setFocus(); }
//				else if (ss.tpbn.isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data", "The value in field PBN/ is required."); tPbn_REG.setFocus(); }
				//10
		        else if (ss.t10a.equals("")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is required."); t10a_REG.setFocus(); }
				else if (ss.t10b.equals("")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is required."); t10b_REG.setFocus(); }
				else if ( (ss.t10b.contains("B1") || ss.t10b.contains("U1") || ss.t10b.contains("V1")) && (ss.t10b.contains("B2") || ss.t10b.contains("U2") || ss.t10b.contains("V2"))) { DialogFactory.openInfoDialog(Shells.sh_tReg, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10a.contains("N") && ss.t10a.contains("S")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","Invalid EQUIPMENT AND CAPABILITIES value.\n\n'N' is not allowed, if 'S' has inserted into field EQUIPMENT AND CAPABILITIES and vice versa.\n\nSee the instruction below the EQUIPMENT AND CAPABILITIES text !"); t10a_REG.setFocus(); }
				else if (ss.t10b.contains("N") && (ss.t10b.contains("A") || ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X") || ss.t10b.contains("B1") || ss.t10b.contains("B2") || ss.t10b.contains("U1") || ss.t10b.contains("U2") || ss.t10b.contains("V1") || ss.t10b.contains("V2") || ss.t10b.contains("D1") || ss.t10b.contains("G1"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","Invalid EQUIPMENT AND CAPABILITIES value.\n\nSSR Modes A and C and SSR Mode S are not allowed,\nif 'N' has inserted into field EQUIPMENT AND CAPABILITIES and vice versa.\n\nSee the instruction below the EQUIPMENT AND CAPABILITIES text !"); t10b_REG.setFocus(); }
				else if (!ss.t10b.equals("") && (ss.sdbguv.contains("tdksesuai"))) { DialogFactory.openInfoDialog(Shells.sh_tReg, "Update Data", em.infoInvalid10b+"\nInsert B1 or B2 or U1 or U2 or V1 or V2 or D1 or G1"); t10b_REG.setFocus();; }		
				else if (!ss.t10a.equals("") && (ss.sprotek10a.contains("ngaco"))) { DialogFactory.openInfoDialog(Shells.sh_tReg, "Update Data", em.infoInvalid10aFix); t10a_REG.setFocus(); }		
				//NEW
		    	//N, harus sendiri
				else if (ss.t10b.contains("N") && ss.t10b.length()>1) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("A") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("C") && (ss.t10b.contains("A") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("E") && (ss.t10b.contains("C") || ss.t10b.contains("A") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("H") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("A") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("I") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("A") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("L") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("A") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("P") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("A") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("S") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("A") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
				else if (ss.t10b.contains("X") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("A"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
		    	//PBN
		        else if (!ss.tpbn.equals("") && (ss.tpbn.length() > 16 || ss.tpbn.contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","Invalid PBN/ indicator value.\nSee the instruction below the PBN/ indicator text !"); tPbn_REG.setFocus(); }
		        else if (!ss.tpbn.equals("") && ss.sprotekpbn.contains("ngaco")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data", em.infoInvalidPbnFix); tPbn_REG.setFocus(); }
				else if (ss.t10a.contains("R") && ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","Field OTHER INFORMATION with indicator PBN/ should be filled, if the letter 'R' is used in field EQUIPMENT AND CAPABILITIES."); tPbn_REG.setFocus(); }
				else if (!ss.t10a.contains("R") && !ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","Field EQUIPMENT AND CAPABILITIES should be contained the letter 'R', if field OTHER INFORMATION is filled."); t10a_REG.setFocus(); }
		    	//G
				else if (!ss.t10a.contains("G") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","If B1, B2, C1, C2, D1, D2, O1 or O2 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'G' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("G") && !ss.krit2.equals("") && (ss.krit2.contains("B2") || ss.krit2.contains("C2") || ss.krit2.contains("D2") || ss.krit2.contains("O2"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","If B1, B2, C1, C2, D1, D2, O1 or O2 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'G' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
				//D
				else if (!ss.t10a.contains("D") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","If B1, B3, C1, C3, D1, D3, O1 or O3 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("D") && !ss.krit3.equals("") && (ss.krit3.contains("B3") || ss.krit3.contains("C3") || ss.krit3.contains("D3") || ss.krit3.contains("O3"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","If B1, B3, C1, C3, D1, D3, O1 or O3 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
		    	//I
				else if (!ss.t10a.contains("I") && !ss.krit5.equals("")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","If B1, B5 or C1 are filled in field OTHER INFORMATION with PBN/ indicator, then an 'I' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1"))) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","If B1, B5 or C1 are filled in field OTHER INFORMATION with PBN/ indicator, then an 'I' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
				//DI
				else if (!ss.t10a.contains("D") && !ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("D") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("I") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("I") && (ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10a_REG.setFocus(); }
		    	//OD / SD
				else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit1.contains("B1")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("D") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10a_REG.setFocus(); }
				else if (!ss.t10a.contains("D") && ss.krit1.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_tReg, "Update Data", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10a_REG.setFocus(); }
		    	
				else {
					String update = "UPDATE aircraft_reg SET type9b='"+t9b_REG.getText()+"',type10a='"+t10a_REG.getText()+"',type10b='"+
						ss.t10b+"',type18='"+tReg_REG.getText()+"',type18Opr='"+tOpr_REG.getText()+"',type18Pbn='"+ss.tpbn+"', type18Sel='" +
						tSel_REG.getText()+"' WHERE id='"+sett()+"'";
					jdbc.update(update);
					discard(); string2=""; sqlSelect="";
					//buat ng'refreshnya
					table.removeAll(); playersReg.clear(); RefreshTable.refreshTableReg(pType9b, pType18);
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
		
//		if ((TeleSplashScreen.sLevel.compareTo("ADMIN")!=0) && (TeleSplashScreen.sLevel.compareTo("L1 NOTAM")!=0) && (TeleSplashScreen.sLevel.compareTo("L1 BO")!=0)) Update.setEnabled(false);		
		bs.SUC(Save, Update, Clear);
	}
	
	void discard() {
		tReg_REG.setText("");
  		t9b_REG.setText("");
		t10a_REG.setText("");
		t10b_REG.setText("");
		tOpr_REG.setText("");
		tPbn_REG.setText("");
		tSel_REG.setText("");
	}
	
 	@SuppressWarnings("unchecked")
	void fillTable(Table table) {
	    // Turn off drawing to avoid flicker
	    table.setRedraw(false);
       
	    // We remove all the table entries, sort our rows, then add the entries
	    table.removeAll();
	    
	    Collections.sort(playersReg, comparatorReg);
	    for (Iterator itr = playersReg.iterator(); itr.hasNext();) {
	    	PlayerReg player = (PlayerReg) itr.next();
	    	TableItem item = new TableItem(table, SWT.NONE);
	    	int c = 0;
	    	item.setText(c++, player.getID());
	    	item.setText(c++, player.getTYPE18());
	    	item.setText(c++, player.getTYPE9B());
	    	item.setText(c++, player.getTYPE10A());
	    	item.setText(c++, player.getTYPE10B());
	    	item.setText(c++, player.getOPERATOR());
	    	item.setText(c++, player.getPBN());
	    	item.setText(c++, player.getSEL());
	    }
	    
	    // sort type9b
	    table.setSortDirection(dir);
       //table.clearAll();
       
	    // Turn drawing back on
	    table.setRedraw(true);
 	}
	
  	private static void set(String string) { TableReg.string2 = string; }
  	public static String sett() { return TableReg.string2; }
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
class PlayerReg {
	private String ID;
	private String TYPE18;
	private String TYPE9B;
	private String TYPE10A;
	private String TYPE10B;
	private String OPERATOR;
	private String PBN;
	private String SEL;
	
	public PlayerReg(String ID, String TYPE18, String TYPE9B, String TYPE10A, String TYPE10B, String OPERATOR, String PBN, String SEL) {
		this.ID = ID;
		this.TYPE18 = TYPE18;
		this.TYPE9B = TYPE9B;
		this.TYPE10A = TYPE10A;
		this.TYPE10B = TYPE10B;
		this.OPERATOR = OPERATOR;
		this.PBN = PBN;
		this.SEL = SEL;
	}

	public String getID() { return ID; }
	public String getTYPE18() { return TYPE18; }
	public String getTYPE9B() { return TYPE9B; }
	public String getTYPE10A() { return TYPE10A; }
	public String getTYPE10B() { return TYPE10B; }
	public String getOPERATOR() { return OPERATOR; }
	public String getPBN() { return PBN; }
	public String getSEL() { return SEL; }
}

@SuppressWarnings("unchecked")
class PlayerComparatorReg implements Comparator {
	public static final int ID = 0;
	public static final int TYPE18 = 1;
	public static final int TYPE9B = 2;
	public static final int TYPE10A = 3;
	public static final int TYPE10B = 4;
	public static final int OPERATOR = 5;
	public static final int PBN = 6;
	public static final int SEL = 7;
	
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
	    PlayerReg p1 = (PlayerReg) obj1;
	    PlayerReg p2 = (PlayerReg) obj2;
	
	    // Determine which field to sort on, then sort on that field
	    switch (column) {
		    case ID: rc = p1.getID().compareTo(p2.getID()); break;
		    case TYPE18: rc = p1.getTYPE18().compareTo(p2.getTYPE18()); break;
		    case TYPE9B: rc = p1.getTYPE9B().compareTo(p2.getTYPE9B()); break;
		    case TYPE10A: rc = p1.getTYPE10A().compareTo(p2.getTYPE10A()); break;
		    case TYPE10B: rc = p1.getTYPE10B().compareTo(p2.getTYPE10B()); break;
		    case OPERATOR: rc = p1.getOPERATOR().compareTo(p2.getOPERATOR()); break;
		    case PBN: rc = p1.getPBN().compareTo(p2.getPBN()); break;
		    case SEL: rc = p1.getSEL().compareTo(p2.getSEL()); break;
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
