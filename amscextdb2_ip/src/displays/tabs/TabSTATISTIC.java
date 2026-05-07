 package displays.tabs;

import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.jfree.chart.demo.chartMain;


import setting.Balloon;
import setting.Criteria;
import setting.Shorten;
import setting.Titles;
import ti_iais.TableStatisticATS;
import ti_iais.TableStatisticMETEO;
import ti_iais.TableStatisticNOTAM;
import displays.AmscSplashScreen2;
import displays.DialogFactory;
import displays.Lists;
import displays.MainForm;

public class TabSTATISTIC {
	
	static Shell shell = new Shell(AmscSplashScreen2.display,SWT.DIALOG_TRIM);
	
	Shorten sh = new Shorten();
	Criteria cAts = new Criteria();
	
	String[] sMsgTypeATS = new String[] {"","ALR","RCF","FPL","DLA","CHG","CNL","DEP","ARR","CDN","CPL","EST","ACP","LAM","RQP","RQS","SPL"};
	String[] sMsgTypeNTM = new String[] {"","NOTAMN","NOTAMR","NOTAMC"};
	String[] sLevel = new String[] {"","F","S","A","M","VFR"};
	
	public static String sTblNm="",sIdName="",sID="",sCriteria="",msgType="",yyear="",mmonth="";

	int x=0,y=0;
	Shell shelllst,shelllst1,shelllst2,shelllst3;
	boolean flgsh,flgsh1,flgsh2,flgsh3=false;
	
	public static Button /*sent,recv,*/bClear,bFind,bChart,bATS,bNTM,bMET,notdep,notdest;
	public static Combo cbLevel,cbATS,cbNTM;
	public static Text Originator,Location,Serie,tDate,tMonth,tAts,tLev,tNtm,Year,tLevel,tLevel2,tRoute,tAircraft,tDepAd,tDestAd,tTypeAircraft,
	tOrg,tLoc,tSerie;
	
	String select;
	Table table;
	
	Shell shellATS = new Shell(AmscSplashScreen2.display, SWT.NONE);
	Shell shellNOTAM = new Shell(AmscSplashScreen2.display, SWT.NONE);
	Shell shellMETEO = new Shell(AmscSplashScreen2.display, SWT.NONE);
	

	public TabSTATISTIC() { }
	
	public void comp(Composite shell) {
		final Composite Group = new Composite(shell, SWT.NONE); sh.composeStyle(Group, 3, SWT.CENTER, SWT.LEFT);
		Group grp1 = new Group(Group, SWT.NONE); sh.groupStyle(grp1, 1, "");
		final Composite grp2 = new Composite(Group, SWT.NONE); sh.composeStyle(grp2, 1, SWT.CENTER, SWT.CENTER);
		
		final Composite tardelete = new Composite(Group, SWT.NONE); sh.composeStyle(tardelete, 10, SWT.LEFT, SWT.TOP);
		tAts = new Text(tardelete, SWT.BORDER);
		tLev = new Text(tardelete, SWT.BORDER);
		tNtm = new Text(tardelete, SWT.BORDER);
		Year = new Text(tardelete, SWT.BORDER);
		tMonth = new Text(tardelete, SWT.BORDER);
		
		sh.textStyle(tAts, 1, 3, SWT.LEFT, SWT.CENTER, "", "", false);
		sh.textStyle(tLev, 1, 3, SWT.LEFT, SWT.CENTER, "", "", false);
		sh.textStyle(tNtm, 1, 6, SWT.LEFT, SWT.CENTER, "", "", false);
		sh.textStyle(Year, 1, 4, SWT.LEFT, SWT.CENTER, "", "", false);
		sh.textStyle(tMonth, 1, 2, SWT.LEFT, SWT.CENTER, "", "", false);
  		
		tAts.setVisible(false);
		tLev.setVisible(false);
		tNtm.setVisible(false);
		Year.setVisible(false);
		tMonth.setVisible(false);
		
		bFind = new Button(grp2, SWT.PUSH); 
		bClear = new Button(grp2, SWT.PUSH); sh.setCF(bClear, bFind);
		bChart = new Button(grp2, SWT.PUSH); 
		sh.buttonStyle(bChart, "C&hart", "Open chart properties", sh.widthBtn, sh.DarkGrey, SWT.CENTER, SWT.CENTER, null);
		
		Composite comp1 = new Composite(grp1, SWT.NONE); sh.composeStyle(comp1, 12, SWT.LEFT, SWT.LEFT);
		Label 
//		label = new Label(comp1, SWT.NONE); sh.labelStyle1(label, "Msg type", SWT.LEFT, SWT.CENTER, SWT.BOLD, sh.BLACK);
//		Composite comp1a = new Composite(comp1, SWT.NONE); sh.composeStyle(comp1a, 2, SWT.LEFT, SWT.CENTER);
//		sent = new Button(comp1a, SWT.RADIO); 
//  		recv = new Button(comp1a, SWT.RADIO); 
//  		label = new Label(comp1, SWT.NONE);
  		label = new Label(comp1, SWT.NONE); sh.labelStyle1(label, "Stat type", SWT.LEFT, SWT.CENTER, SWT.BOLD, sh.BLUE);
  		bATS = new Button(comp1, SWT.RADIO); 
  		cbATS = new Combo(comp1, SWT.READ_ONLY); 
  		bNTM = new Button(comp1, SWT.RADIO);
  		cbNTM = new Combo(comp1, SWT.READ_ONLY); 
  		bMET = new Button(comp1, SWT.RADIO); 
  		label = new Label(comp1, SWT.NONE);
  		label = new Label(comp1, SWT.NONE); sh.labelStyle1(label, "Date", SWT.LEFT, SWT.CENTER, SWT.BOLD, sh.BLUE);
  		tDate = new Text(comp1, SWT.BORDER); 
  		label = new Label(comp1, SWT.NONE);
  		label = new Label(comp1, SWT.NONE); sh.labelStyle1(label, "Orig.", SWT.LEFT, SWT.CENTER, SWT.BOLD, sh.BLACK);
  		tOrg = new Text(comp1, SWT.BORDER); 
  		
  		Composite comp2 = new Composite(grp1, SWT.NONE); sh.composeStyle(comp2, 11, SWT.TOP, SWT.LEFT);
  		label = new Label(comp2, SWT.NONE); sh.labelStyle1(label, "Acft Id", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
  		tAircraft = new Text(comp2, SWT.BORDER);
  		label = new Label(comp2, SWT.NONE);
  		label = new Label(comp2, SWT.NONE); sh.labelStyle1(label, "Type", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
  		tTypeAircraft = new Text(comp2, SWT.BORDER);
  		label = new Label(comp2, SWT.NONE);
  		label = new Label(comp2, SWT.NONE); sh.labelStyle1(label, "DEP", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
  		Composite comp2a = new Composite(comp2, SWT.NONE); sh.composeStyle(comp2a, 2, SWT.TOP, SWT.LEFT);
  		tDepAd = new Text(comp2a, SWT.BORDER);
  		notdep = new Button(comp2a, SWT.CHECK);
  		label = new Label(comp2, SWT.NONE);
  		label = new Label(comp2, SWT.NONE); sh.labelStyle1(label, "DEST", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
  		Composite comp2b = new Composite(comp2, SWT.NONE); sh.composeStyle(comp2b, 2, SWT.TOP, SWT.LEFT);
  		tDestAd = new Text(comp2b, SWT.BORDER);
  		notdest = new Button(comp2b, SWT.CHECK);
  		
  		label = new Label(comp2, SWT.RIGHT); sh.labelStyle1(label, "Serie", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
  		tSerie = new Text(comp2, SWT.BORDER);
  		label = new Label(comp2, SWT.NONE);
  		label = new Label(comp2, SWT.RIGHT); sh.labelStyle1(label, "Loca.", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
  		tLoc = new Text(comp2, SWT.BORDER);
  		label = new Label(comp2, SWT.NONE);
  		label = new Label(comp2, SWT.RIGHT); sh.labelStyle1(label, "Level Fr", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
  		Composite comp2c = new Composite(comp2, SWT.NONE); sh.composeStyle(comp2c, 7, SWT.TOP, SWT.LEFT);
		cbLevel = new Combo(comp2c, SWT.READ_ONLY); 
		tLevel = new Text(comp2c, SWT.BORDER); 
		label = new Label(comp2c, SWT.NONE); sh.labelStyle1(label, "To", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tLevel2 = new Text(comp2c, SWT.BORDER); 
  		label = new Label(comp2, SWT.NONE);
  		label = new Label(comp2, SWT.NONE); sh.labelStyle1(label, "Route", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
  		tRoute = new Text(comp2, SWT.BORDER);
  		
//  		sh.RadioCheckStyle(sent, "Sent", "Sent messages");
//  		sh.RadioCheckStyle(recv, "Recv", "Receive messages");
  		sh.RadioCheckStyle(bATS, "ATS", "ATS messages");
  		sh.RadioCheckStyle(bNTM, "NOTAM", "NOTAM messages");
  		sh.RadioCheckStyle(bMET, "METAR", "METAR messages");
  		
  		sh.RadioCheckStyle(notdep, "Not", "Not included");
  		sh.RadioCheckStyle(notdest, "Not", "Not included");
  		
  		sh.comboStyle(cbATS, "ATS Type", 65, SWT.CENTER);
  		sh.comboStyle(cbNTM, "NOTAM Type", 100, SWT.CENTER);
  		sh.comboStyle(cbLevel, "Cruising Level", 65, SWT.CENTER);
		
  		for (int i=0;i<sMsgTypeATS.length;i++) cbATS.add(sMsgTypeATS[i]); 
  		for (int i=0;i<sMsgTypeNTM.length;i++) cbNTM.add(sMsgTypeNTM[i]); 
  		for (int i=0;i<sLevel.length;i++) cbLevel.add(sLevel[i]);
  		
  		int iTextWidth=120;
  		sh.textStyle(tDate, 60, 7, SWT.LEFT, SWT.CENTER, "", "Year and month", true); tDate.setText("0000/00");
  		sh.textStyle(tOrg, 100, 8, SWT.LEFT, SWT.CENTER, sh.letter, "Originator", true);
  		sh.textStyle(tAircraft, iTextWidth, 100, SWT.LEFT, SWT.CENTER, sh.alphanum1, Balloon.b7a, true);
  		sh.textStyle(tTypeAircraft, iTextWidth, 100, SWT.LEFT, SWT.CENTER, sh.alphanum1, Balloon.b9b, true);
  		sh.textStyle(tDepAd, iTextWidth, 100, SWT.LEFT, SWT.CENTER, sh.letter1, Balloon.b13a, true);
  		sh.textStyle(tDestAd, iTextWidth, 100, SWT.LEFT, SWT.CENTER, sh.letter1, Balloon.b16a, true);
		sh.textStyle(tLevel, 30, 3, SWT.LEFT, SWT.CENTER, sh.numeric, Balloon.b15b, true);
		sh.textStyle(tLevel2, 30, 3, SWT.LEFT, SWT.CENTER, sh.numeric, Balloon.b15b, true);
		sh.textStyle(tRoute, iTextWidth, 100, SWT.LEFT, SWT.CENTER, sh.upper, Balloon.b15c, true);
		sh.textStyle(tSerie, iTextWidth, 100, SWT.LEFT, SWT.CENTER, sh.letter1, "NOTAM ID Serie", true);
		sh.textStyle(tLoc, iTextWidth, 100, SWT.LEFT, SWT.CENTER, sh.letter1, "Location", true);
  		
		SearchListener();

		Serie=tSerie;
		Originator=tOrg;
		Location=tLoc;
	}
	
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//Listener
	private void discard() {
//		//First, we make sure the user does not see what we're doing.
//		Lists.table.setRedraw( false );
//		//Then we remove all columns.
//		while ( Lists.table.getColumnCount() > 0 ) {
//			Lists.table.getColumns()[ 0 ].dispose();
//		}
//		Lists.table.removeAll();
//		//And finally we let the user see what we did.
//		Lists.table.setRedraw( true );
//		
//		sTblNm="";sIdName="";sID=""; new Lists().col(sID);
		
//		sent.setSelection(false);
//		recv.setSelection(false);
		bATS.setSelection(false);
		bNTM.setSelection(false);
		bMET.setSelection(false);
		notdep.setSelection(false);
		notdest.setSelection(false);
		
		cAts.tgl();
		tDate.setText(cAts.YYYY+"/"+cAts.MM);
		tOrg.setText("");
		tAircraft.setText("");
		tTypeAircraft.setText("");
		tDepAd.setText("");
		tDestAd.setText("");
		tSerie.setText("");
		tLoc.setText("");
		tLevel.setText("");
		tLevel2.setText("");
		tRoute.setText("");
		
		cbATS.setText("");
		cbNTM.setText("");
		cbLevel.setText("");
		tLevel.setEnabled(false);
		tLevel2.setEnabled(false);
		cbATS.setEnabled(false);
		cbNTM.setEnabled(false);
		
		//enable all
//		sent.setEnabled(true);
//		recv.setEnabled(true);
		bATS.setEnabled(true);
		bNTM.setEnabled(true);
		bMET.setEnabled(true);
		tDate.setEnabled(true);
		tOrg.setEnabled(true);
		setDisableATS(true);
		setDisableNTM(true);
		setDisableMET(true);
		
		//untuk sendiri
		tAts.setText("");
		tNtm.setText("");
		tLev.setText("");
		Year.setText(cAts.YYYY);
		tMonth.setText(cAts.MM);
	}
	
	void valueChanged(Text text,Text tDate,Text Year,Text tMonth) {
		if (!text.isFocusControl()) return;
		if (text == tDate) {
	    	String yrmnt = (text.getText());
	  		Year.setText(yrmnt.substring(0, 4));
	  		tMonth.setText(yrmnt.substring(5, 7));
	  	}
	}
  	
	void setDisableLevel(boolean b) {
		tLevel.setEnabled(b);
		tLevel2.setEnabled(b);
	}
	
	void setDisableATS(boolean tr) {
		tAircraft.setEnabled(tr);
		tTypeAircraft.setEnabled(tr);
		tDepAd.setEnabled(tr);
		notdep.setEnabled(tr);
		tDestAd.setEnabled(tr);
		notdest.setEnabled(tr);
		cbLevel.setEnabled(tr);
		tRoute.setEnabled(tr);
	}
	
	void setDisableNTM(boolean fl) {
		tSerie.setEnabled(fl);
		tLoc.setEnabled(fl);
	}
	
	void setDisableMET(boolean fl) {
		tLoc.setEnabled(fl);
	}
	
	void setDiscardATS() {
		tAircraft.setText("");
		tTypeAircraft.setText("");
		tDepAd.setText("");
		tDestAd.setText("");
		cbLevel.setText("");
		tLevel.setText("");
		tLevel2.setText("");
		tRoute.setText("");
		notdep.setSelection(false);
		notdest.setSelection(false);
	}
	
	void setDiscardNTM() {
		tSerie.setText("");
		tLoc.setText("");
	}
	
	void setDiscardMET() {
		tLoc.setText("");
	}
	
	private void SearchListener() {
		setDisableLevel(false);
		cbATS.setEnabled(false);
		cbNTM.setEnabled(false);
		
		setDisableATS(true);
		setDisableNTM(true);
		setDisableMET(true);
		
		cAts.tgl();
		tDate.setText(cAts.YYYY+"/"+cAts.MM);
		Year.setText(cAts.YYYY);
		tMonth.setText(cAts.MM);
		
		ModifyListener regListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				valueChanged((Text) e.widget,tDate,Year,tMonth);
			}
		};
		tDate.addModifyListener(regListener);
		Year.addModifyListener(regListener);
		tMonth.addModifyListener(regListener);
		
		bATS.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bATS.getSelection()==true && bNTM.getSelection()==false && bMET.getSelection()==false) {
					cbATS.setEnabled(true);
					cbNTM.setEnabled(false);
					cbNTM.setText("");
					
					sTblNm="air_message"; 
					sIdName="id_ats";
					
					setDisableATS(true);
					setDisableMET(false);
					setDisableNTM(false);
					//setDiscardATS();
					setDiscardMET();
					setDiscardNTM();
					
					//untuk sendiri
					tNtm.setText("");
				}
			}
		});
		
		bNTM.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bNTM.getSelection()==true && bATS.getSelection()==false && bMET.getSelection()==false) {
					cbNTM.setEnabled(true);
					cbATS.setEnabled(false);
					cbATS.setText("");
					
					sTblNm="notam_multi"; 
					sIdName="id_notam_multi";
					
					setDisableATS(false);
					setDisableMET(false);
					setDisableNTM(true);
					setDiscardATS();
					setDiscardMET();
					//setDiscardNTM();
					setDisableLevel(false);
					
					//untuk sendiri
					tAts.setText("");
				}
			}
		});
		
		bMET.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bMET.getSelection()==true && bATS.getSelection()==false && bNTM.getSelection()==false) {
					cbNTM.setEnabled(false);
					cbATS.setEnabled(false);
					cbNTM.setText("");
					cbATS.setText("");
					
					sTblNm="meteo_metar"; 
					sIdName="id_metar";

					setDisableATS(false);
					setDisableNTM(false);
					setDisableMET(true);
					setDiscardATS();
					setDiscardNTM();
					//setDiscardMET();
					setDisableLevel(false);
					
					//untuk sendiri
					tAts.setText("");
					tNtm.setText("");
				}
			}
		});
		
		cbATS.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bATS.getSelection()==true && bNTM.getSelection()==false && bMET.getSelection()==false) {
					tAts.setText(cbATS.getText());
					tNtm.setText("");
				} else tAts.setText(""); 

				if (cbATS.getText().compareTo("RCF")==0) {
					setDisableATS(false);
					tAircraft.setEnabled(true);
				} else if (cbATS.getText().compareTo("DLA")==0 || cbATS.getText().compareTo("CHG")==0 || cbATS.getText().compareTo("CNL")==0 ||
						cbATS.getText().compareTo("DEP")==0 || cbATS.getText().compareTo("CDN")==0 || cbATS.getText().compareTo("EST")==0 ||
						cbATS.getText().compareTo("ACP")==0 || cbATS.getText().compareTo("RQP")==0 || cbATS.getText().compareTo("RQS")==0 ||
						cbATS.getText().compareTo("SPL")==0) {
					setDisableATS(false);
					tAircraft.setEnabled(true);
					tDepAd.setEnabled(true);
					notdep.setEnabled(true);
					tDestAd.setEnabled(true);
					notdest.setEnabled(true);
				} else if (cbATS.getText().compareTo("ARR")==0) {
					setDisableATS(false);
					tAircraft.setEnabled(true);
					tDepAd.setEnabled(true);
					notdep.setEnabled(true);
				} else if (cbATS.getText().compareTo("LAM")==0) {
					setDisableATS(false);
				} else {
					setDisableATS(true);
				}
			}
		});
		
		cbNTM.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bATS.getSelection()==false && bNTM.getSelection()==true && bMET.getSelection()==false) {
					tNtm.setText(cbNTM.getText());
					tAts.setText("");
				} else tNtm.setText(""); 
			}
		});
		
		cbLevel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				tLev.setText(cbLevel.getText());
		
				int m = cbLevel.getSelectionIndex();
				if (m == 0 || m == 5) {
					setDisableLevel(false);
					tLevel.setText("");
					tLevel2.setText("");
				} else {
					setDisableLevel(true);
					tLevel.setText("");
					tLevel2.setText("");
					
					if (m == 1 || m == 3) {
						tLevel.setTextLimit(3);
						tLevel2.setTextLimit(3);
					} else if (m == 2 || m == 4) {
						tLevel.setTextLimit(4);
						tLevel2.setTextLimit(4);
					}
				}
			}
		});
		
		final Calendar calFrom = Calendar.getInstance();
		tDate.addListener(SWT.Verify, new Listener() {
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
							case 3: { buffer.append('0'); break; }//* YYY[Y] *  { buffer.append('Y'); break; }
							case 4: { buffer.append('/'); break; }//* YYYY[-]MM * 
							case 5: { buffer.append('0'); break; }//* [M]M *
							case 6: { buffer.append('0'); break; }//* M[M] * { buffer.append('M'); break; }
							default: return;
						}
					}
					tDate.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tDate.insert(buffer.toString());
					ignore = false;
					tDate.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 15) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (start + index == 4 || start + index == 7) {
						if (chars[i] == '/') { index++; continue; }
						buffer.insert(index++, '/');
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
				StringBuffer date = new StringBuffer(tDate.getText());
				date.replace(e.start, e.start + length, newText);

				calFrom.set(Calendar.YEAR, 1901);
				calFrom.set(Calendar.MONTH, Calendar.JANUARY);
				
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
				else {
					if (calFrom.get(Calendar.MONTH)  == Calendar.FEBRUARY) {
						char firstChar = date.charAt(8);
						if (firstChar != '0' && '2' < firstChar) return;
					}
				}
				tDate.setSelection(e.start, e.start + length);
				ignore = true;
				tDate.insert(newText);
				ignore = false;
			}
		});
		
		bChart.addSelectionListener(new SelectionAdapter() { 
			public void widgetSelected(SelectionEvent e) { 
				if (shell.isDisposed()) {
					shell = new Shell(AmscSplashScreen2.display,SWT.DIALOG_TRIM);
				}
				if (!shell.isVisible()) {
					chartMain form = new chartMain();
					form.run(shell);
				} else {
					shell.close();
					shell = new Shell(AmscSplashScreen2.display,SWT.DIALOG_TRIM);
					chartMain form = new chartMain();
					form.run(shell);
				}				
			} 
		});
		
		bClear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MainForm.clearTbl();
				discard();
				Lists.discard();
			}
		});
		
		bFind.addSelectionListener(new SelectionAdapter() { 
			public void widgetSelected(SelectionEvent e) { searchData(); } });
		
		tDate.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tOrg.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tAircraft.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tTypeAircraft.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tDepAd.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tDestAd.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tSerie.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tLoc.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tLevel.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tLevel2.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tRoute.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
	}
	
	void searchData() {
//		sTblNm="air_message_free_text"; //depend on radio stat type 
//		sIdName="id_free_text";
		sID=Titles.stStatistic;
		MainForm.clearTbl(); 
		new Lists().col(sID);
		
		if (bATS.getSelection()==false && bNTM.getSelection()==false && bMET.getSelection()==false) {
			DialogFactory.openWarningRequired(MainForm.shell, "Statistic", "Statistic type");
		} else FindListener();
	}
	
	void enter(KeyEvent e) {
		if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) { searchData(); }
	}
	
	private void FindListener() {
		if (bATS.getSelection()==true && bNTM.getSelection()==false && bMET.getSelection()==false) {
			if (cbATS.getText().equals("")) { msgType = ""; } 
			else { msgType = cbATS.getText().toString(); }
			
			String st_sent="1";
//			if (sent.getSelection()) st_sent="1";
//			else if (recv.getSelection()) st_sent="0";
			String MonthAts="",year="";
			
			MonthAts = tDate.getText().substring(5, 7);
			year = Year.getText().toString();
			if (shellATS.isDisposed()) { shellATS = new Shell(SWT.NONE); }
			yyear=year;
			mmonth=MonthAts;
			if (!shellATS.isVisible()) {
				TableStatisticATS form = new TableStatisticATS();
				form.koneksiDB(shellATS,year, MonthAts, msgType, st_sent);
			}
			else {
				shellATS.close();
				shellATS = new Shell(SWT.NONE);
				TableStatisticATS form = new TableStatisticATS();
				form.koneksiDB(shellATS,year, MonthAts, msgType, st_sent);
			}
			
		} else if (bNTM.getSelection()==true && bATS.getSelection()==false && bMET.getSelection()==false) {
			String msgTypeNtm,yearNtm,serie,origin,loc;
			String MonthAts="";
			if (tNtm.getText().equals("")) { msgTypeNtm = ""; } else { msgTypeNtm = tNtm.getText().toString(); }
			if (Year.getText().equals("")) { yearNtm = ""; } else { yearNtm = Year.getText().toString(); }
			if (Serie.getText().equals("")) { serie = ""; } else { serie = Serie.getText().toString(); }
			if (Originator.getText().equals("")) { origin = ""; } else { origin = Originator.getText().toString(); }
			if (Location.getText().equals("")) { loc = ""; } else { loc = Location.getText().toString(); }
			if (tMonth.getText().equals("")) { MonthAts = ""; }
			else { MonthAts = tMonth.getText().toString(); }

			String st_sent="1";
//			if (sent.getSelection()) st_sent="1";
//			else if (recv.getSelection()) st_sent="0";

			if (tMonth.getText().equals("")) { DialogFactory.openInfoDialog("Search Statistics Messages","Field Month should be filled !!"); }
			else if (Year.getText().equals("")) { DialogFactory.openInfoDialog("Search Statistics Messages","Field Year should be filled !!"); }
			else {
				if (shellNOTAM.isDisposed()) { shellNOTAM = new Shell(SWT.NONE); }
				if (!shellNOTAM.isVisible()) {
					TableStatisticNOTAM form = new TableStatisticNOTAM();
					form.NOTAM_STATISTIC(shellNOTAM,msgTypeNtm,MonthAts,yearNtm,serie,origin,loc,st_sent);
				}	
				else {
					shellNOTAM.close();
					shellNOTAM = new Shell(SWT.NONE);
					TableStatisticNOTAM form = new TableStatisticNOTAM();
					form.NOTAM_STATISTIC(shellNOTAM,msgTypeNtm,MonthAts,yearNtm,serie,origin,loc,st_sent);
				}
			}
			
		} else if (bMET.getSelection()==true && bATS.getSelection()==false && bNTM.getSelection()==false) {
			String origin="",loc="",yearMetar=""; 
			if (Originator.getText().equals("")) { origin = ""; } else { origin = Originator.getText().toString(); }
			if (Location.getText().equals("")) { loc = ""; } else { loc = Location.getText().toString(); }
			
			String MonthAts="";
			if (Year.getText().equals("")) { yearMetar = ""; } else { yearMetar = Year.getText().toString(); }
			if (tMonth.getText().equals("")) { MonthAts = ""; }
			else { MonthAts = tMonth.getText().toString(); }

			String st_sent="1";
//			if (sent.getSelection()) st_sent="1";
//			else if (recv.getSelection()) st_sent="0";

			if (shellMETEO.isDisposed()) { shellMETEO = new Shell(SWT.NONE); }
			if (!shellMETEO.isVisible()) {
				TableStatisticMETEO form = new TableStatisticMETEO();
				form.METEO_STATISTIC(shellMETEO,yearMetar, MonthAts, msgType,st_sent,loc);
			}
			else {
				shellMETEO.close();
				shellMETEO = new Shell(SWT.NONE);
				TableStatisticMETEO form = new TableStatisticMETEO();
				form.METEO_STATISTIC(shellMETEO,yearMetar, MonthAts, msgType,st_sent,loc);
			}
		}

//		String critFree="";
//
//		// Date Range
//		String pDate = "";//tFrom.getText().toString();
//		String pDateTo = "";//tTo.getText().toString();
//		//Data
//		String pMsgAll = "";//tFreeText.getText().toString();
//		if (pMsgAll.compareTo("") != 0) {
//            String[] sub = new String[1000];
//            String beda="",aa="";
//
//            critFree = " AND";
//            if (pMsgAll.contains(", ")) pMsgAll=pMsgAll.replaceAll(", ", ",");
//            else if (pMsgAll.contains(" , ")) pMsgAll=pMsgAll.replaceAll(" , ", ",");
//            
//            if (pMsgAll.contains(",")) { //kriteria yang dicari : banyak
//            	sub = pMsgAll.split(",");
//	            for (int i = 0; i < sub.length; i++) {
////	            	if (sub[i].contains(" ")) sub[i] = sub[i].replaceFirst("\\s+", "");
//	            	String koma = " OR (manual_ats LIKE '%"+sub[i]+"%')";
//	            	if (!koma.equals(beda)) {
//						aa+=koma+"";
//					}
//					beda=koma;
//	            }
//	            
//	            if (aa.startsWith(" OR ")) {
//	            	aa = aa.replaceFirst(" OR ", "");
//	            	critFree+=" ("+aa+")";
//	            }
//            } else if (pMsgAll.contains("&")) { //berpasangan
//            	critFree = " AND manual_ats LIKE '%";
//            	sub = pMsgAll.split("&");
//	            for (int i = 0; i < sub.length; i++) {
////	            	if (sub[i].contains(" ")) sub[i] = sub[i].replaceFirst("\\s+", "");
//	            	System.out.println("sub[" + i + "]" + sub[i]);
//	            	String koma = sub[i]+"%";
//	            	if (!koma.equals(beda)) {
//						aa+=koma;
//					}
//					beda=koma;
//	            }
//	            
//	            critFree+=aa+"'";
//            } else { 
//            	critFree = " AND manual_ats LIKE '%"+pMsgAll+"%'";
//            } 
//		}
//		
////		if (!tFreeText.getText().isEmpty()) critFree+=" AND manual_ats LIKE '%"+tFreeText.getText()+"%'";
//		sCriteria=critFree;
//		new Lists(sTblNm,sIdName,sID,sCriteria,pDate,pDateTo).Find();
	}
}