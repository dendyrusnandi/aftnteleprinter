package displays.tabs;

import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import setting.Balloon;
import setting.Criteria;
import setting.Shorten;
import setting.Titles;
import displays.DialogFactory;
import displays.Lists;
import displays.MainForm;

public class TabATS {
	
	Shorten sh = new Shorten();
	Criteria cAts = new Criteria();
	
	Label label;
	public static Combo cbMsgType,cbLevel;
	public static Text tFrom,tTo,/*tFreeText,/*tCID,tCSN,tTMS,*/tAircraft,tType,tDep,tEtd,tDest,tLevel,tLevel2,tRoute,tArr,tTime;
	Button bClear,bFind;
	
	public static String sTblNm="",sIdName="",sID="",sCriteria="";
	

	public TabATS() { }
	
	public void comp(Composite shell) {
		int iSpace=1;
		String[] sMsgType = new String[] {"ALR","RCF","FPL","DLA","CHG","CNL","DEP","ARR","CDN","CPL","EST","ACP","LAM","RQP","RQS","SPL"};
		String[] sLevel = new String[] {"","F","S","A","M","VFR"};
		
		final Composite Group = new Composite(shell, SWT.NONE); sh.composeStyle(Group, 3, SWT.CENTER, SWT.LEFT);
		Group grp1 = new Group(Group, SWT.NONE); sh.groupStyle(grp1, 2, "Date Range");
		Group grp3 = new Group(Group, SWT.NONE); sh.groupStyle(grp3, 14, "Data");
		final Composite grp2 = new Composite(Group, SWT.NONE); sh.composeStyle(grp2, 1, SWT.CENTER, SWT.CENTER);
//		Group grp2 = new Group(Group, SWT.NONE); sh.groupStyle(grp2, 1, " ");
		
		label = new Label(grp1, SWT.RIGHT); sh.labelStyle1(label, "From", SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tFrom = new Text(grp1, SWT.BORDER); sh.textStyle(tFrom, 130, 16, SWT.LEFT, SWT.LEFT, "", "Date/Time From (YYYY-MM-DD hh:mm)", true);
		label = new Label(grp1, SWT.RIGHT); sh.labelStyle1(label, "To", SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tTo = new Text(grp1, SWT.BORDER); sh.textStyle(tTo, 130, 16, SWT.LEFT, SWT.LEFT, "", "Date/Time To (YYYY-MM-DD hh:mm)", true);
		
		bFind = new Button(grp2, SWT.PUSH); 
		bClear = new Button(grp2, SWT.PUSH); sh.setCF(bClear, bFind);
		
		label = new Label(grp3, SWT.LEFT); sh.labelStyle1(label, "Msg Type", SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		cbMsgType = new Combo(grp3, SWT.READ_ONLY); sh.comboStyle(cbMsgType, "Message Type", 65, SWT.CENTER);  
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "DEP", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tDep = new Text(grp3, SWT.BORDER); sh.textStyle(tDep, 40, 4, SWT.LEFT, SWT.CENTER, sh.letter, Balloon.b13a, true);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "DEST", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tDest = new Text(grp3, SWT.BORDER); sh.textStyle(tDest, 40, 4, SWT.LEFT, SWT.CENTER, sh.letter, Balloon.b16a, true);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "Time", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tEtd = new Text(grp3, SWT.BORDER); sh.textStyle(tEtd, 30, 4, SWT.LEFT, SWT.CENTER, sh.numeric, Balloon.b13b, true);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "Level From", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		Composite compGrp3 = new Composite(grp3, SWT.NONE); sh.composeStyle(compGrp3, 4, SWT.LEFT, SWT.CENTER);
		cbLevel = new Combo(compGrp3, SWT.READ_ONLY); sh.comboStyle(cbLevel, "Cruising Level", 65, SWT.CENTER);
		tLevel = new Text(compGrp3, SWT.BORDER); sh.textStyle(tLevel, 30, 3, SWT.LEFT, SWT.CENTER, sh.numeric, Balloon.b15b, true);
		label = new Label(compGrp3, SWT.NONE); sh.labelStyle1(label, "To", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tLevel2 = new Text(compGrp3, SWT.BORDER); sh.textStyle(tLevel2, 30, 3, SWT.LEFT, SWT.CENTER, sh.numeric, Balloon.b15b, true);

		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "Aircraft ID", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tAircraft = new Text(grp3, SWT.BORDER); sh.textStyle(tAircraft, 70, 7, SWT.LEFT, SWT.CENTER, sh.alphanum, Balloon.b7a, true);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "Type", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tType = new Text(grp3, SWT.BORDER); sh.textStyle(tType, 40, 4, SWT.LEFT, SWT.CENTER, sh.alphanum, Balloon.b9b, true);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "ARR", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tArr = new Text(grp3, SWT.BORDER); sh.textStyle(tArr, 40, 4, SWT.LEFT, SWT.CENTER, sh.letter, Balloon.b17a, true);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "Time", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tTime = new Text(grp3, SWT.BORDER); sh.textStyle(tTime, 30, 4, SWT.LEFT, SWT.CENTER, sh.numeric, Balloon.b17b, true);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "Route", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tRoute = new Text(grp3, SWT.BORDER); sh.textStyle(tRoute, 163, 500, SWT.LEFT, SWT.CENTER, sh.upper, "Separated by comma (,) if route more than one.\n"+Balloon.b15c, true);
		
		for (int i=0;i<sMsgType.length;i++) cbMsgType.add(sMsgType[i]); 
		for (int i=0;i<sLevel.length;i++) cbLevel.add(sLevel[i]);
		SearchListener();
		
		cbMsgType.select(2);
	}
	
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//Listener
	private void enabled15b(boolean b) {
		tLevel.setEnabled(b);
		tLevel2.setEnabled(b);
	}
	
	private static void enabledFplData(boolean b) {
		tAircraft.setEnabled(b);
		tType.setEnabled(b);
		tDep.setEnabled(b);
		tArr.setEnabled(b);
		tEtd.setEnabled(b);
		tTime.setEnabled(b);
		tDest.setEnabled(b);
		cbLevel.setEnabled(b);
		tLevel.setEnabled(false);
		tLevel2.setEnabled(false);
		tRoute.setEnabled(b);
	}
	
	private void discardDataCriteria() {
		tAircraft.setText("");
		tType.setText("");
		tDep.setText("");
		tArr.setText("");
		tEtd.setText("");
		tTime.setText("");
		tDest.setText("");
		cbLevel.setText("");
		tLevel.setText("");
		tLevel2.setText("");
		tRoute.setText("");
//		tFreeText.setText("");
	}
	
	private void discardGeneralCriteria() {
		//First, we make sure the user does not see what we're doing.
		Lists.table.setRedraw( false );
		//Then we remove all columns.
		while ( Lists.table.getColumnCount() > 0 ) {
			Lists.table.getColumns()[ 0 ].dispose();
		}
		Lists.table.removeAll();
		//And finally we let the user see what we did.
		Lists.table.setRedraw( true );
		
		sTblNm="air_message"; sIdName="id_ats"; 
		alr(); sID=Titles.stFPL; new Lists().col(sID);
		
		cbMsgType.select(2);
//		tCID.setText("");
//		tCSN.setText("");
//		tTMS.setText("");
		cAts.tgl();
//		tFrom.setText(cAts.YYYY+"-"+cAts.MM+"-00 00:00");
		tFrom.setText(cAts.YYYY+"-"+cAts.MM+"-"+cAts.DD+" 00:00");
		tTo.setText("0000-00-00 00:00");
	}
  	
	private void SearchListener() {
		cAts.tgl();
//		tFrom.setText(cAts.YYYY+"-"+cAts.MM+"-00 00:00");
		tFrom.setText(cAts.YYYY+"-"+cAts.MM+"-"+cAts.DD+" 00:00");
		tTo.setText("0000-00-00 00:00");

		final Calendar calFrom = Calendar.getInstance();
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
							case 3: { buffer.append('0'); break; }//* YYY[Y] *  { buffer.append('Y'); break; }
							case 4: { buffer.append('-'); break; }//* YYYY[-]MM * 
							case 5: { buffer.append('0'); break; }//* [M]M *
							case 6: { buffer.append('0'); break; }//* M[M] * { buffer.append('M'); break; }
							case 7: { buffer.append('-'); break; }//* MM[-]DD * 
							case 8: { buffer.append('0'); break; }//* [D]D *
							case 9: { buffer.append('0'); break; }//* D[D] *  { buffer.append('D'); break; }
							case 10: { buffer.append(' '); break; }//*DD[ ]hh* 
							case 11: { buffer.append('0'); break; }//* [h]h *
							case 12: { buffer.append('0'); break; }//* h[h] *  { buffer.append('h'); break; }
							case 13: { buffer.append(':'); break; }//*hh[:]mm* 
							case 14: { buffer.append('0'); break; }//* [m]m *
							case 15: { buffer.append('0'); break; }//* m[m] *  { buffer.append('m'); break; }
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
						if (firstChar != '0' && '2' < firstChar) return;
					}
				}
				tFrom.setSelection(e.start, e.start + length);
				ignore = true;
				tFrom.insert(newText);
				ignore = false;
			}
		});
		
		final Calendar calTo = Calendar.getInstance();
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
						case 3: { buffer.append('0'); break; }//* YYY[Y] *  { buffer.append('Y'); break; }
						case 4: { buffer.append('-'); break; }//* YYYY[-]MM * 
						case 5: { buffer.append('0'); break; }//* [M]M *
						case 6: { buffer.append('0'); break; }//* M[M] * { buffer.append('M'); break; }
						case 7: { buffer.append('-'); break; }//* MM[-]DD * 
						case 8: { buffer.append('0'); break; }//* [D]D *
						case 9: { buffer.append('0'); break; }//* D[D] *  { buffer.append('D'); break; }
						case 10: { buffer.append(' '); break; }//*DD[ ]hh* 
						case 11: { buffer.append('0'); break; }//* [h]h *
						case 12: { buffer.append('0'); break; }//* h[h] *  { buffer.append('h'); break; }
						case 13: { buffer.append(':'); break; }//*hh[:]mm* 
						case 14: { buffer.append('0'); break; }//* [m]m *
						case 15: { buffer.append('0'); break; }//* m[m] *  { buffer.append('m'); break; }
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
						if (firstChar != '0' && '2' < firstChar) return;
					}
				}
				tTo.setSelection(e.start, e.start + length);
				ignore = true;
				tTo.insert(newText);
				ignore = false;
			}
		});
		
		enabled15b(false);
		cbLevel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int m = cbLevel.getSelectionIndex();
				if (m == 0 || m == 5) {
					enabled15b(false);
					tLevel.setText("");
					tLevel2.setText("");
				} else {
					enabled15b(true);
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
		
		cbMsgType.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//First, we make sure the user does not see what we're doing.
				Lists.table.setRedraw( false );
				//Then we remove all columns.
				while ( Lists.table.getColumnCount() > 0 ) {
					Lists.table.getColumns()[ 0 ].dispose();
				}
				Lists.table.removeAll();
				//And finally we let the user see what we did.
				Lists.table.setRedraw( true );
				
				String msgType = cbMsgType.getText();
				sTblNm="air_message"; sIdName="id_ats"; 
				
				if (msgType.compareToIgnoreCase("alr")==0) { alr(); sID=Titles.stALR; new Lists().col(sID); }
				if (msgType.compareToIgnoreCase("fpl")==0) { alr(); sID=Titles.stFPL; new Lists().col(sID); }
				if (msgType.compareToIgnoreCase("cpl")==0) { alr(); sID=Titles.stCPL; new Lists().col(sID); }
				
				if (msgType.compareToIgnoreCase("rcf")==0) {
					enabledFplData(false);
					tAircraft.setEnabled(true); 
					sID=Titles.stRCF; new Lists().col(sID); }
				
				if (msgType.compareToIgnoreCase("DLA")==0) { dla(); sID=Titles.stDLA; new Lists().col(sID); }
				if (msgType.compareToIgnoreCase("CHG")==0) { dla(); sID=Titles.stCHG; new Lists().col(sID); } 
				if (msgType.compareToIgnoreCase("CNL")==0) { dla(); sID=Titles.stCNL; new Lists().col(sID); } 
				if (msgType.compareToIgnoreCase("DEP")==0) { dla(); sID=Titles.stDEP; new Lists().col(sID); } 
				if (msgType.compareToIgnoreCase("CDN")==0) { dla(); sID=Titles.stCDN; new Lists().col(sID); } 
				if (msgType.compareToIgnoreCase("EST")==0) { dla(); sID=Titles.stEST; new Lists().col(sID); } 
				if (msgType.compareToIgnoreCase("ACP")==0) { dla(); sID=Titles.stACP; new Lists().col(sID); } 
				if (msgType.compareToIgnoreCase("RQP")==0) { dla(); sID=Titles.stRQP; new Lists().col(sID); } 
				if (msgType.compareToIgnoreCase("RQS")==0) { dla(); sID=Titles.stRQS; new Lists().col(sID); } 
				if (msgType.compareToIgnoreCase("SPL")==0) { dla(); sID=Titles.stSPL; new Lists().col(sID); } 
					
				if (msgType.compareToIgnoreCase("arr")==0) {
					enabledFplData(false);
					tAircraft.setEnabled(true);
					tDep.setEnabled(true);
					tEtd.setEnabled(true);
					tArr.setEnabled(true);
					tTime.setEnabled(true); 
					sID=Titles.stARR; new Lists().col(sID); }
				
				if (msgType.compareToIgnoreCase("lam")==0) {
					enabledFplData(false);
					sID=Titles.stLAM; new Lists().col(sID); }

//				FindListener();
			}
		});
		
		bClear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				sh.groupStyle(Lists.Group, 1, "List");
				MainForm.clearTbl();
				discard();
				Lists.discard();
			}
		});
		
		bFind.addSelectionListener(new SelectionAdapter() { 
			public void widgetSelected(SelectionEvent e) { FindListener(); } });
		
		tFrom.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tTo.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tAircraft.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tType.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tDep.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tEtd.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tDest.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tArr.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tTime.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tRoute.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tLevel.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tLevel2.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
	}
	
	void discard() {
		discardDataCriteria();
		enabledFplData(true);
		discardGeneralCriteria();
	}
	
	void enter(KeyEvent e) {
		if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) { FindListener(); }
	}
	
	public static void alr() {
		enabledFplData(true); 
		tArr.setEnabled(false);
		tTime.setEnabled(false); 
		tArr.setText("");
		tTime.setText("");
	}
	
	void dla() {
		enabledFplData(false);
		tAircraft.setEnabled(true);
		tDep.setEnabled(true);
		tEtd.setEnabled(true);
		tDest.setEnabled(true);
	}
	
	private void FindListener() {
		String critMsgtyp="",critCID="",critCSN="",critTMS="",critFree="",critAcft="",critType="",critDep="",
		critArr="",critEtd="",critTime="",critDest="",critLev="",critLevFr="",critLevTo="",critRoute="";

		String msgType = cbMsgType.getText();
		if (msgType.isEmpty()) { DialogFactory.openWarningRequired(DialogFactory.find, "Message Type is required."); }					
		else {
			// Date Range
			String pDate = tFrom.getText().toString();
			String pDateTo = tTo.getText().toString();
			//General Criteria
			if (msgType.compareToIgnoreCase("Free")!=0) critMsgtyp=" AND type3a='"+msgType+"'";
//			if (!tCID.getText().isEmpty()) critCID=" AND cid LIKE '"+tCID.getText()+"%'";
//			if (!tCSN.getText().isEmpty()) critCSN=" AND csn LIKE '"+tCSN.getText()+"%'";
//			if (!tTMS.getText().isEmpty()) critTMS=" AND tms LIKE '"+tTMS.getText()+"%'";
			//Data
//			if (!tFreeText.getText().isEmpty()) critFree+=" AND manual_ats LIKE '%"+tFreeText.getText()+"%'";
			if (!tAircraft.getText().isEmpty()) critAcft=" AND type7a LIKE '"+tAircraft.getText()+"%'";
			if (!tType.getText().isEmpty()) critType=" AND type9b LIKE '"+tType.getText()+"%'";
			if (!tDep.getText().isEmpty()) critDep=" AND type13a LIKE '"+tDep.getText()+"%'";
			if (!tArr.getText().isEmpty()) critArr=" AND type17a LIKE '"+tArr.getText()+"%'";
			if (!tEtd.getText().isEmpty()) critEtd=" AND type13b LIKE '"+tEtd.getText()+"%'";
			if (!tTime.getText().isEmpty()) critTime=" AND type17b LIKE '"+tTime.getText()+"%'";
			if (!tDest.getText().isEmpty()) critDest=" AND type16a LIKE '"+tDest.getText()+"%'";
			if (!tRoute.getText().isEmpty()) critRoute=" AND type15c LIKE '"+tRoute.getText()+"%'";
			String scLevel = cbLevel.getText();
			String t15bFr = tLevel.getText();
			String t15bTo = tLevel2.getText();
			if (scLevel.compareTo("F")==0 || scLevel.compareTo("A")==0) {
				if (t15bFr.length()<3) {
		  			if (t15bFr.length()==2) { t15bFr="0"+t15bFr; }
		  			else if (t15bFr.length()==1) { t15bFr="00"+t15bFr; }
		  		}
				if (t15bTo.length()<3) {
		  			if (t15bTo.length()==2) { t15bTo="0"+t15bTo; }
		  			else if (t15bTo.length()==1) { t15bTo="00"+t15bTo; }
		  		}
			} else if (scLevel.compareTo("S")==0 || scLevel.compareTo("M")==0) {
				if (t15bFr.length()<4) {
		  			if (t15bFr.length()==3) { t15bFr="0"+t15bFr; }
		  			else if (t15bFr.length()==2) { t15bFr="00"+t15bFr; }
		  			else if (t15bFr.length()==1) { t15bFr="000"+t15bFr; }
		  		}
				if (t15bTo.length()<4) {
		  			if (t15bTo.length()==3) { t15bTo="0"+t15bTo; }
		  			else if (t15bTo.length()==2) { t15bTo="00"+t15bTo; }
		  			else if (t15bTo.length()==1) { t15bTo="000"+t15bTo; }
		  		}
			}
			if (scLevel.compareTo("") != 0) critLev=" AND type15b LIKE '"+scLevel+"%'"; 
			if (t15bFr.compareTo("") != 0) critLevFr=" AND type15b>='"+ scLevel+t15bFr +"'";
			if (t15bTo.compareTo("") != 0) critLevTo=" AND type15b<='"+ scLevel+t15bTo +"'";
			
			sCriteria=critMsgtyp+critCID+critCSN+critTMS+critFree+critAcft+critType+critDep+critArr+critEtd+critTime+critDest+critLev+critLevFr+critLevTo+critRoute;
			new Lists(sTblNm,sIdName,sID,sCriteria,pDate,pDateTo).Find();
		}
	}
}