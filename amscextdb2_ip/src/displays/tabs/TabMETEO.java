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

import setting.Criteria;
import setting.Shorten;
import setting.Titles;
import displays.DialogFactory;
import displays.Lists;
import displays.MainForm;

public class TabMETEO {
	
	Shorten sh = new Shorten();
	Criteria cAts = new Criteria();
	
	Label label;
	public static Combo cbMsgType;
	static Text tFrom,tTo,tFreeText,/*tCID,tCSN,tTMS,*/tMsgId1,tMsgId2,tMsgId3,tLocation,tTimeObs,tIssued,tOption;
	Button bClear,bFind;
	
	public static String sTblNm="",sIdName="",sID="",sCriteria="";
	

	public TabMETEO() { }
	
	public void comp(Composite shell) {
		int iSpace=1;
		String[] sMsgType = new String[] {"METAR","SPECI","SIGMET","AIRMET","AIREP","TAFOR","SYNOP","ARFOR","ROFOR","Wind Shear Warning","WINTEM","Advisory Vulcano","RQM","Warning Synop"};
		
		final Composite Group = new Composite(shell, SWT.NONE); sh.composeStyle(Group, 3, SWT.CENTER, SWT.LEFT);
		Group grp1 = new Group(Group, SWT.NONE); sh.groupStyle(grp1, 2, "Date Range");
		Group grp3 = new Group(Group, SWT.NONE); sh.groupStyle(grp3, 11, "Data");
		final Composite grp2 = new Composite(Group, SWT.NONE); sh.composeStyle(grp2, 1, SWT.CENTER, SWT.CENTER);
//		Group grp2 = new Group(Group, SWT.NONE); sh.groupStyle(grp2, 1, " ");
		
		label = new Label(grp1, SWT.RIGHT); sh.labelStyle1(label, "From", SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tFrom = new Text(grp1, SWT.BORDER); sh.textStyle(tFrom, 130, 16, SWT.LEFT, SWT.LEFT, "", "Date/Time From (YYYY-MM-DD hh:mm)", true);
		label = new Label(grp1, SWT.RIGHT); sh.labelStyle1(label, "To", SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tTo = new Text(grp1, SWT.BORDER); sh.textStyle(tTo, 130, 16, SWT.LEFT, SWT.LEFT, "", "Date/Time To (YYYY-MM-DD hh:mm)", true);
		
		bFind = new Button(grp2, SWT.PUSH); 
		bClear = new Button(grp2, SWT.PUSH); sh.setCF(bClear, bFind);
		
		label = new Label(grp3, SWT.LEFT); sh.labelStyle1(label, "Msg Type", SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		cbMsgType = new Combo(grp3, SWT.READ_ONLY); sh.comboStyle(cbMsgType, "Message Type", 160, SWT.CENTER);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		Label label = new Label(grp3, SWT.NONE); sh.labelStyle1(label, "Msg ID", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		Composite compMsgId = new Composite(grp3, SWT.NONE); sh.composeStyle(compMsgId, 3, SWT.CENTER, SWT.LEFT);
		tMsgId1 = new Text(compMsgId, SWT.BORDER); sh.textStyle(tMsgId1, 20, 2, SWT.CENTER, SWT.LEFT, sh.upper, "Message Id", true);
		tMsgId2 = new Text(compMsgId, SWT.BORDER); sh.textStyle(tMsgId2, 20, 2, SWT.CENTER, SWT.LEFT, sh.upper, "Message Id", true);
		tMsgId3 = new Text(compMsgId, SWT.BORDER); sh.textStyle(tMsgId3, 20, 2, SWT.CENTER, SWT.LEFT, sh.upper, "Message Id", true);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "",iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.NONE); sh.labelStyle1(label, "Loca.", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tLocation = new Text(grp3, SWT.BORDER); sh.textStyle(tLocation, 50, 4, SWT.CENTER, SWT.LEFT, sh.letter, "Location", true);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "",iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.NONE); sh.labelStyle1(label, "Option", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tOption = new Text(grp3, SWT.BORDER); sh.textStyle(tOption, 40, 3, SWT.CENTER, SWT.LEFT, sh.upper, "Issued (DDhhmm)", true);
		
		label = new Label(grp3, SWT.NONE); sh.labelStyle1(label, "Text", SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tFreeText = new Text(grp3, SWT.BORDER); sh.textStyle(tFreeText, 150, 50, SWT.CENTER, SWT.LEFT, sh.upper, "Text", true);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "",iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.NONE); sh.labelStyle1(label, "Time Obs.", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		Composite compTime = new Composite(grp3, SWT.NONE); sh.composeStyle(compTime, 2, SWT.LEFT, SWT.CENTER);
		tTimeObs = new Text(compTime, SWT.BORDER); sh.textStyle(tTimeObs, 50, 6, SWT.CENTER, SWT.LEFT, "", "Time Observation (DDhhmm)", true);
		label = new Label(compTime, SWT.NONE); sh.labelStyle1(label, "Z", SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "",iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.NONE); sh.labelStyle1(label, "Issued", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tIssued = new Text(grp3, SWT.BORDER); sh.textStyle(tIssued, 50, 6, SWT.CENTER, SWT.LEFT, "", "Issued (DDhhmm)", true);
		label = new Label(grp3, SWT.NONE);
		
		for (int i=0;i<sMsgType.length;i++) cbMsgType.add(sMsgType[i]);
		cbMsgType.select(0);
		SearchListener();
	}
	
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//Listener
	private void discardData() {
		tMsgId1.setText("");
		tMsgId2.setText("");
		tMsgId3.setText("");
		tLocation.setText("");
		tTimeObs.setText("");
		tIssued.setText("");
		tOption.setText("");
		tFreeText.setText("");
	}
	
	public static void enabledData() {
		tMsgId1.setEnabled(true);
		tMsgId2.setEnabled(true);
		tMsgId3.setEnabled(true);
		tLocation.setEnabled(true);
		tTimeObs.setEnabled(true);
		tIssued.setEnabled(true);
		tOption.setEnabled(true);
		tFreeText.setEnabled(true);
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
		
		sTblNm="meteo_metar"; sIdName="id_metar"; sID=Titles.stMETAR; new Lists().col(sID);
		cbMsgType.select(0);
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
				enabledData();
				//sTblNm & sIdName are use in another selection listener
				if (msgType.compareToIgnoreCase("free")==0 || msgType.compareToIgnoreCase("Volcanic Activity")==0 ||
						msgType.compareToIgnoreCase("rqm")==0) { 

//					if (msgType.compareToIgnoreCase("free")==0) {
//						sTblNm="air_message_free_text_meteo"; sIdName="id_free_text"; sID=Titles.stFREEMET; new Lists().col(sID); }
					if (msgType.compareToIgnoreCase("Volcanic Activity")==0) { 
						sTblNm="volcanic_act"; sIdName="id_va"; sID=Titles.stVOL; new Lists().col(sID); }
					if (msgType.compareToIgnoreCase("rqm")==0) { 
						sTblNm="rqm"; sIdName="id_rqm"; sID=Titles.stRQM; new Lists().col(sID); }
					
					tMsgId1.setEnabled(false);
					tMsgId2.setEnabled(false);
					tMsgId3.setEnabled(false);
					tLocation.setEnabled(false);
					tTimeObs.setEnabled(false);
					tIssued.setEnabled(false);
					tOption.setEnabled(false);
					discardData(); }
				
				if (msgType.compareToIgnoreCase("metar")==0 || msgType.compareToIgnoreCase("speci")==0) { 
					
					if (msgType.compareToIgnoreCase("metar")==0) { 
						sTblNm="meteo_metar"; sIdName="id_metar"; sID=Titles.stMETAR; new Lists().col(sID); }
					if (msgType.compareToIgnoreCase("speci")==0) { 
						sTblNm="meteo_speci"; sIdName="id_speci"; sID=Titles.stSPECI; new Lists().col(sID);}
					
					discardData(); }

				if (msgType.compareToIgnoreCase("sigmet")==0 || msgType.compareToIgnoreCase("airmet")==0 ||
						msgType.compareToIgnoreCase("wind shear warning")==0) {  

					if (msgType.compareToIgnoreCase("sigmet")==0) { 
						sTblNm="meteo_sigmet"; sIdName="id_sigmet"; sID=Titles.stSIGMET; new Lists().col(sID); }
					if (msgType.compareToIgnoreCase("airmet")==0) { 
						sTblNm="meteo_airmet"; sIdName="id_airmet"; sID=Titles.stAIRMET; new Lists().col(sID);}
					if (msgType.compareToIgnoreCase("Wind Shear Warning")==0) { 
						sTblNm="meteo_wins_war"; sIdName="id_wins_war"; sID=Titles.stWINSWAR; new Lists().col(sID); } 
					
					tTimeObs.setEnabled(false); 
					discardData(); }
				
				if (msgType.compareToIgnoreCase("airep")==0 || msgType.compareToIgnoreCase("tafor")==0 ||
						msgType.compareToIgnoreCase("synop")==0 || msgType.compareToIgnoreCase("arfor")==0 ||
						msgType.compareToIgnoreCase("rofor")==0 || msgType.compareToIgnoreCase("wintem")==0 ||
						msgType.compareToIgnoreCase("Advisory Vulcano")==0 || msgType.compareToIgnoreCase("Warning Synop")==0) { 
					
					if (msgType.compareToIgnoreCase("airep")==0) { 
						sTblNm="meteo_airep"; sIdName="id_airep"; sID=Titles.stAIREP; new Lists().col(sID); }
					if (msgType.compareToIgnoreCase("tafor")==0) { 
						sTblNm="meteo_tafor"; sIdName="id_tafor"; sID=Titles.stTAFOR; new Lists().col(sID); }
					if (msgType.compareToIgnoreCase("synop")==0) { 
						sTblNm="meteo_synop"; sIdName="id_synop"; sID=Titles.stSYNOP; new Lists().col(sID); }
					if (msgType.compareToIgnoreCase("arfor")==0) {
						sTblNm="meteo_arfor"; sIdName="id_arfor"; sID=Titles.stARFOR; new Lists().col(sID); }
					if (msgType.compareToIgnoreCase("rofor")==0) { 
						sTblNm="meteo_rofor"; sIdName="id_rofor"; sID=Titles.stROFOR; new Lists().col(sID); }
					if (msgType.compareToIgnoreCase("wintem")==0) { 
						sTblNm="meteo_wintem"; sIdName="id_wintem"; sID=Titles.stWINTEM; new Lists().col(sID); }
					if (msgType.compareToIgnoreCase("Advisory Vulcano")==0) { 
						sTblNm="vulcano_adv"; sIdName="id_vulcano_adv"; sID=Titles.stADV; new Lists().col(sID); }
					if (msgType.compareToIgnoreCase("Warning Synop")==0) { 
						sTblNm="meteo_wsynop"; sIdName="id_synop"; sID=Titles.stWARSYN; new Lists().col(sID); }
					
					tLocation.setEnabled(false);
					tTimeObs.setEnabled(false); 
					discardData(); }
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
		
//		bClear.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				sh.groupStyle(Lists.Group, 1, "List");
//				Lists.table.removeAll();
//				//First, we make sure the user does not see what we're doing.
//				Lists.table.setRedraw(false);
//				//Then we remove all columns.
//				while ( Lists.table.getColumnCount() > 0 ) {
//					Lists.table.getColumns()[ 0 ].dispose();
//				}
//				//And finally we let the user see what we did.
//				Lists.table.setRedraw(true);
//				
//				//Data
//				discardData();
//				tFreeText.setEnabled(true);
//				
//				//General Criteria
//				discardGeneralCriteria();
//				
//				//Other Component
//				Lists.discard();
//			}
//		});
		
		bFind.addSelectionListener(new SelectionAdapter() { 
			public void widgetSelected(SelectionEvent e) { FindListener(); } });
		
		tFrom.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tTo.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tMsgId1.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tMsgId2.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tMsgId3.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tLocation.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tOption.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tFreeText.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tTimeObs.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tIssued.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
	}
	
	void discard() {
		discardData();
		tFreeText.setEnabled(true);
		discardGeneralCriteria();
	}
	
	void enter(KeyEvent e) {
		if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) { FindListener(); }
	}
	
	private void FindListener() {
		String critMsgtyp="",critCID="",critCSN="",critTMS="",critFree="",critMsgId1="",critMsgId2="",critMsgId3="",
		critLoca="",critTime="",critIssued="",critOption="";

		String msgType = cbMsgType.getText();
		if (msgType.isEmpty()) { DialogFactory.openWarningRequired(DialogFactory.find, "Message Type is required."); }					
		else {
			// Date Range
			String pDate = tFrom.getText().toString();
			String pDateTo = tTo.getText().toString();
			// Data
			String fieldFree="",fieldLoca="";
//			if (sID.compareToIgnoreCase(Titles.stFREEMET)==0) fieldFree="manual_ats"; else 
			if (sID.compareToIgnoreCase(Titles.stRQM)==0) fieldFree="request_message";
			else if (sID.compareToIgnoreCase(Titles.stWARSYN)==0) fieldFree="warning_synop";
			else fieldFree="text";
			//--------------------------------------------------------------------------
			if (sID.compareToIgnoreCase(Titles.stMETAR)==0 || sID.compareToIgnoreCase(Titles.stSPECI)==0) fieldLoca="Location";
			else if (sID.compareToIgnoreCase(Titles.stSIGMET)==0 || sID.compareToIgnoreCase(Titles.stAIRMET)==0) fieldLoca="Location_ats";
			else if (sID.compareToIgnoreCase(Titles.stWINSWAR)==0) fieldLoca="location";
			//--------------------------------------------------------------------------
//			if (!tCID.getText().isEmpty()) critCID=" AND cid LIKE '"+tCID.getText()+"%'";
//			if (!tCSN.getText().isEmpty()) critCSN=" AND csn LIKE '"+tCSN.getText()+"%'";
//			if (!tTMS.getText().isEmpty()) critTMS=" AND tms LIKE '"+tTMS.getText()+"%'";
			if (!tFreeText.getText().isEmpty()) critFree+=" AND "+fieldFree+" LIKE '%"+tFreeText.getText()+"%'";
			if (!tMsgId1.getText().isEmpty()) critMsgId1=" AND message_id_12 LIKE '"+tMsgId1.getText()+"%'";
			if (!tMsgId2.getText().isEmpty()) critMsgId2=" AND message_id_34 LIKE '"+tMsgId2.getText()+"%'";
			if (!tMsgId3.getText().isEmpty()) critMsgId3=" AND message_id_56 LIKE '"+tMsgId3.getText()+"%'";
			if (!tLocation.getText().isEmpty()) critLoca=" AND "+fieldLoca+" LIKE '"+tLocation.getText()+"%'";
			if (!tTimeObs.getText().isEmpty()) critTime=" AND time_observation LIKE '"+tTimeObs.getText()+"%'";
			if (!tIssued.getText().isEmpty()) critIssued=" AND issued_message LIKE '"+tIssued.getText()+"%'";
			if (!tOption.getText().isEmpty()) critOption=" AND correction_meteo LIKE '"+tOption.getText()+"%'";
			
			sCriteria=critMsgtyp+critCID+critCSN+critTMS+critFree+critMsgId1+critMsgId2+critMsgId3+critLoca+critTime+critIssued+critOption;
			new Lists(sTblNm,sIdName,sID,sCriteria,pDate,pDateTo).Find();
		}
	}
}