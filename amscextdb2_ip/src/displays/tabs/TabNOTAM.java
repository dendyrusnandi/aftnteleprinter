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
import actions.ViewNOTAM;
import displays.DialogFactory;
import displays.Lists;
import displays.MainForm;

public class TabNOTAM { 
	
	Shorten sh = new Shorten();
	static Criteria cAts = new Criteria();
	ViewNOTAM vNOTAM = new ViewNOTAM();
	
	// Components
	static Group Group;
	
	Label label,lbJmldt,lbJmlhal;
	public static Combo cbMsgType;
	public static Text tFrom,tTo,/*tCID,tCSN,tTMS,*/tSerie,tSeqFr,tSeqTo,tScope,tPurpose,tSubject,tLocation,tState,tSerial,tBirdNum,tOriginator;
	Button bClear,bFind;

	public static String sTblNm="",sIdName="",sID="",sCriteria="";
	

	public TabNOTAM() { }
	
	public void comp(Composite shell) {
		int iSpace=1;
		String[] sMsgType = new String[] {"NOTAM","Active NTM","Expired NTM","Checklist","RQNTM","SNOWTAM","ASHTAM","BIRDTAM","RQN","RQL"};
		
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
		cbMsgType = new Combo(grp3, SWT.READ_ONLY); sh.comboStyle(cbMsgType, "Message Type", 120, SWT.CENTER); 
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "Bird No.", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tBirdNum = new Text(grp3, SWT.BORDER); sh.textStyle(tBirdNum, 30, 4, SWT.LEFT, SWT.CENTER, sh.numeric, "", true);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "Serial", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tSerial = new Text(grp3, SWT.BORDER); sh.textStyle(tSerial, 30, 4, SWT.LEFT, SWT.CENTER, sh.numeric, "", true);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "State", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tState = new Text(grp3, SWT.BORDER); sh.textStyle(tState, 20, 2, SWT.LEFT, SWT.CENTER, sh.alphanum, "", true);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "Orig.", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tOriginator = new Text(grp3, SWT.BORDER); sh.textStyle(tOriginator, 80, 8, SWT.LEFT, SWT.CENTER, sh.letter, "Originator", true);
		
		//-------------------
//		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "NTM Serie", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
//		Composite compSerie = new Composite(grp3, SWT.NONE); sh.composeStyle(compSerie, 6, SWT.LEFT, SWT.CENTER);
//		tSerie = new Text(compSerie, SWT.BORDER); sh.textStyle(tSerie, 10, 1, SWT.LEFT, SWT.CENTER, sh.letter, "", true);
//		label = new Label(compSerie, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
//		label = new Label(compSerie, SWT.RIGHT); sh.labelStyle1(label, "Seq. Fr", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
//		tSeqFr = new Text(compSerie, SWT.BORDER); sh.textStyle(tSeqFr, 30, 4, SWT.LEFT, SWT.CENTER, sh.numeric, "", true);
//		label = new Label(compSerie, SWT.RIGHT); sh.labelStyle1(label, "To", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
//		tSeqTo = new Text(compSerie, SWT.BORDER); sh.textStyle(tSeqTo, 30, 4, SWT.LEFT, SWT.CENTER, sh.numeric, "", true);
		
		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "NOTAM No.", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		Composite compSerie = new Composite(grp3, SWT.NONE); sh.composeStyle(compSerie, 4, SWT.LEFT, SWT.CENTER);
		tSerie = new Text(compSerie, SWT.BORDER); sh.textStyle(tSerie, 10, 1, SWT.LEFT, SWT.CENTER, sh.letter, "NOTAM Serie", true);
//		label = new Label(compSerie, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
//		label = new Label(compSerie, SWT.RIGHT); sh.labelStyle1(label, "Seq Fr", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tSeqFr = new Text(compSerie, SWT.BORDER); sh.textStyle(tSeqFr, 30, 4, SWT.LEFT, SWT.CENTER, sh.numeric, "NOTAM Sequence From", true);
//		label = new Label(compSerie, SWT.RIGHT); sh.labelStyle1(label, "To", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(compSerie, SWT.RIGHT); sh.labelStyle1(label, "-", SWT.CENTER, SWT.CENTER, SWT.BOLD, sh.BLACK);
		tSeqTo = new Text(compSerie, SWT.BORDER); sh.textStyle(tSeqTo, 30, 4, SWT.LEFT, SWT.CENTER, sh.numeric, "NOTAM Sequence To", true);
		
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "Code", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tSubject = new Text(grp3, SWT.BORDER); sh.textStyle(tSubject, 30, 3, SWT.LEFT, SWT.CENTER, sh.letter, "NOTAM Code", true);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "Purpose", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tPurpose = new Text(grp3, SWT.BORDER); sh.textStyle(tPurpose, 40, 4, SWT.LEFT, SWT.CENTER, sh.letter, "", true);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.NONE); sh.labelStyle1(label, "Scope", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tScope = new Text(grp3, SWT.BORDER); sh.textStyle(tScope, 20, 2, SWT.LEFT, SWT.CENTER, sh.letter, "", true);
		label = new Label(grp3, SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(grp3, SWT.RIGHT); sh.labelStyle1(label, "Loca.", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tLocation = new Text(grp3, SWT.BORDER); sh.textStyle(tLocation, 40, 4, SWT.LEFT, SWT.CENTER, sh.letter, "", true);
		
		for (int i=0;i<sMsgType.length;i++) cbMsgType.add(sMsgType[i]);
		cbMsgType.select(0);
		SearchNOTAMListener();
	}
	
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//Listener
	private void discardDataCriteria() {
		tSerie.setText("");
		tSeqFr.setText("");
		tSeqTo.setText("");
		tSubject.setText("");
		tPurpose.setText("");
		tScope.setText("");
		tBirdNum.setText("");
		tState.setText("");
		tSerial.setText("");
		tLocation.setText("");
		tOriginator.setText("");
		
		tSerie.setEnabled(true);
		tSeqFr.setEnabled(true);
		tSeqTo.setEnabled(true);
		tSubject.setEnabled(true);
		tPurpose.setEnabled(true);
		tScope.setEnabled(true);
		tBirdNum.setEnabled(true);
		tState.setEnabled(true);
		tSerial.setEnabled(true);
		tLocation.setEnabled(true);
		tOriginator.setEnabled(true);
		
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
		
		sTblNm="notam_multi"; sIdName="id_notam_multi"; sID=Titles.stNOTAM; new Lists().col(sID);
		cbMsgType.select(0);
//		tCID.setText("");
//		tCSN.setText("");
//		tTMS.setText("");
		cAts.tgl();
//		tFrom.setText(cAts.YYYY+"-"+cAts.MM+"-00 00:00");
		tFrom.setText(cAts.YYYY+"-"+cAts.MM+"-"+cAts.DD+" 00:00");
		tTo.setText("0000-00-00 00:00");
	}
  	
	public static void notam() {
		tBirdNum.setEnabled(false);
		tState.setEnabled(false);
		tSerial.setEnabled(false);
		
		tSerie.setEnabled(true);
		tSeqFr.setEnabled(true);
		tSeqTo.setEnabled(true);
		tScope.setEnabled(true);
		tPurpose.setEnabled(true);
		tSubject.setEnabled(true);
		tLocation.setEnabled(true);
		tOriginator.setEnabled(true);
		
		tBirdNum.setText("");
		tState.setText("");
		tSerial.setText("");
	}
	
	private void SearchNOTAMListener() {
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
				//sTblNm & sIdName are use in another selection listener
				if (msgType.compareToIgnoreCase("NOTAM")==0 || msgType.compareToIgnoreCase("Active NTM")==0 ||
					msgType.compareToIgnoreCase("Expired NTM")==0 || msgType.compareToIgnoreCase("Checklist")==0 ||
					msgType.compareToIgnoreCase("RQNTM")==0) { 
					
					sTblNm="notam_multi"; sIdName="id_notam_multi"; 
					
					discardDataCriteria();
					notam();
					
					if (msgType.compareToIgnoreCase("NOTAM")==0) { sID=Titles.stNOTAM; new Lists().col(sID); }
					if (msgType.compareToIgnoreCase("Active NTM")==0) { sID=Titles.stACTNTM; new Lists().col(sID); }
					if (msgType.compareToIgnoreCase("Expired NTM")==0) { sID=Titles.stEXPNTM; new Lists().col(sID); }
					if (msgType.compareToIgnoreCase("Checklist")==0) { sID=Titles.stCHKNTM; new Lists().col(sID); }
					if (msgType.compareToIgnoreCase("RQNTM")==0) { sID=Titles.stRQNTM; new Lists().col(sID);
						tSerie.setEnabled(false);
						tSubject.setEnabled(false);
						tPurpose.setEnabled(false);
						tScope.setEnabled(false); }
				}
				
				if (msgType.compareToIgnoreCase("snowtam")==0) { sTblNm="snowtam"; sIdName="id_snowtam"; 
					sID=Titles.stSNOWTAM; new Lists().col(sID);
					
					discardDataCriteria();
					tSerie.setEnabled(false);
					tSeqFr.setEnabled(false);
					tSeqTo.setEnabled(false);
					tSubject.setEnabled(false);
					tPurpose.setEnabled(false);
					tScope.setEnabled(false);
					tBirdNum.setEnabled(false); }
				
				if (msgType.compareToIgnoreCase("ashtam")==0) { sTblNm="ashtam"; sIdName="id_ashtam"; 
					sID=Titles.stASHTAM; new Lists().col(sID);
					
					discardDataCriteria();
					tSerie.setEnabled(false);
					tSeqFr.setEnabled(false);
					tSeqTo.setEnabled(false);
					tSubject.setEnabled(false);
					tPurpose.setEnabled(false);
					tScope.setEnabled(false);
					tBirdNum.setEnabled(false); }
				
				if (msgType.compareToIgnoreCase("birdtam")==0) { sTblNm="birdtam"; sIdName="id_birdtam"; 
					sID=Titles.stBIRDTAM; new Lists().col(sID);
					
					discardDataCriteria();
					tSerie.setEnabled(false);
					tSeqFr.setEnabled(false);
					tSeqTo.setEnabled(false);
					tSubject.setEnabled(false);
					tPurpose.setEnabled(false);
					tScope.setEnabled(false);
					tLocation.setEnabled(false); 
					tState.setEnabled(false); 
					tSerial.setEnabled(false); }
				
				if (msgType.compareToIgnoreCase("rqn")==0 || msgType.compareToIgnoreCase("rql")==0) {  
					if (msgType.compareToIgnoreCase("rqn")==0) { sTblNm="rqn"; sIdName="id_rqn"; sID=Titles.stRQN; new Lists().col(sID); }
					if (msgType.compareToIgnoreCase("rql")==0) { sTblNm="rql"; sIdName="id_rql"; sID=Titles.stRQL; new Lists().col(sID); }
					discardDataCriteria();
					tSerie.setEnabled(false);
					tSeqFr.setEnabled(false);
					tSeqTo.setEnabled(false);
					tSubject.setEnabled(false);
					tPurpose.setEnabled(false);
					tScope.setEnabled(false);
					tBirdNum.setEnabled(false); 
					tState.setEnabled(false); 
					tSerial.setEnabled(false); }
				
//				FindListener();
			}
		});
		
		bClear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				sh.groupStyle(Lists.Group, 1, "List");
				MainForm.clearTbl();
				discard();
				Lists.discard();
				tBirdNum.setEnabled(false);
				tSerial.setEnabled(false);
				tState.setEnabled(false);
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
//				discardDataCriteria();
//				discardGeneralCriteria();
//				
//				//Other Component
//				Lists.discard();
//				tBirdNum.setEnabled(false);
//				tSerial.setEnabled(false);
//				tState.setEnabled(false);
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
		
		tSerie.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tSeqFr.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tSeqTo.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tSubject.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tPurpose.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tScope.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tLocation.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tBirdNum.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tSerial.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tState.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tOriginator.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
	}
	
	void discard() {
		discardDataCriteria();
		discardGeneralCriteria();
	}

	void enter(KeyEvent e) {
		if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) { FindListener(); }
	}
	
	private void FindListener() {
		String critMsgtyp="",critCID="",critCSN="",critTMS="",critSerie="",critSeqFr="",critSeqTo="",critScope="",critPurpose="",critSubject="",
		critLocation="",critState="",critSerial="",critBirdNum="",critOriginator="";
		
		if (cbMsgType.getText().isEmpty()) { DialogFactory.openWarningRequired(DialogFactory.find, "Message Type is required."); }					
		else {
			// Date Range
			String pDate = tFrom.getText().toString();
			String pDateTo = tTo.getText().toString();
			//General Criteria
//			if (!tCID.getText().isEmpty()) critCID=" AND cid LIKE '"+tCID.getText()+"%'";
//			if (!tCSN.getText().isEmpty()) critCSN=" AND csn LIKE '"+tCSN.getText()+"%'";
//			if (!tTMS.getText().isEmpty()) critTMS=" AND tms LIKE '"+tTMS.getText()+"%'";
			//Data
			if (!tSerie.getText().isEmpty()) critSerial+=" AND ntm_id_serie='"+tSerie.getText()+"'";
			if (!tScope.getText().isEmpty()) critScope=" AND scope LIKE '"+tScope.getText()+"%'";
			if (!tPurpose.getText().isEmpty()) critPurpose=" AND purpose LIKE '"+tPurpose.getText()+"%'";
			if (!tSubject.getText().isEmpty()) critSubject=" AND code_23 LIKE '"+tSubject.getText()+"%'";
			
			if (cbMsgType.getText().compareToIgnoreCase("RQN")==0 || cbMsgType.getText().compareToIgnoreCase("RQL")==0) {
				if (!tLocation.getText().isEmpty()) critLocation=" AND nof_or_type LIKE '"+tLocation.getText()+"%'";
			} else if (cbMsgType.getText().compareToIgnoreCase("ASHTAM")==0) {
				if (!tLocation.getText().isEmpty()) critLocation=" AND location LIKE '"+tLocation.getText()+"%'";
			} else if (cbMsgType.getText().compareToIgnoreCase("SNOWTAM")==0) {
				if (!tLocation.getText().isEmpty()) critLocation=" AND location_indicator LIKE '"+tLocation.getText()+"%'";
			} else { 
				if (!tLocation.getText().isEmpty()) critLocation=" AND A LIKE '"+tLocation.getText()+"%'"; }
			
			if (!tState.getText().isEmpty()) critState=" AND state LIKE '"+tState.getText()+"%'";
			if (!tSerial.getText().isEmpty()) critSerial=" AND sn_serial_nr LIKE '"+tSerial.getText()+"%'";
			if (!tBirdNum.getText().isEmpty()) critBirdNum=" AND birdtam_nr LIKE '"+tBirdNum.getText()+"%'";
			if (!tOriginator.getText().isEmpty()) critOriginator=" AND originator LIKE '"+tOriginator.getText()+"%'";
			
			String idSeqFr = tSeqFr.getText().toString();
			String idSeqTo = tSeqTo.getText().toString();
			//SeqFrom
			if (idSeqFr.length() == 3) { idSeqFr = "0"+idSeqFr; }
			else if (idSeqFr.length() == 2) { idSeqFr = "00"+idSeqFr; }
			else if (idSeqFr.length() == 1) { idSeqFr = "000"+idSeqFr; }
			
			//SeqTo
			if (idSeqTo.length() == 3) { idSeqTo = "0"+idSeqTo; }
			else if (idSeqTo.length() == 2) { idSeqTo = "00"+idSeqTo; }
			else if (idSeqTo.length() == 1) { idSeqTo = "000"+idSeqTo; }
			if (idSeqFr.compareTo("") != 0) { critSeqFr = " AND ntm_id_sequence>='"+idSeqFr+"'"; } else critSeqFr="";
			if (idSeqTo.compareTo("") != 0) { critSeqTo = " AND ntm_id_sequence<='"+idSeqTo+"'"; } else critSeqTo="";
			
//			String sNTM="";
			if (cbMsgType.getText().compareToIgnoreCase("NOTAM")==0) { critMsgtyp=" AND identifiers LIKE 'NOTAM%'"; }
			else if (cbMsgType.getText().compareToIgnoreCase("RQNTM")==0) { critMsgtyp=" AND identifiers LIKE 'RQNTM%'"; }
			else if (cbMsgType.getText().compareToIgnoreCase("Checklist")==0) { 
				critMsgtyp=" AND identifiers LIKE 'NOTAM%' AND code_23='QKK' AND code_45='KK'"; }
			else if (cbMsgType.getText().compareToIgnoreCase("Active NTM")==0) { 
				critMsgtyp=" AND identifiers LIKE 'NOTAM%' AND st='1' AND (C>='' OR est_perm='PERM' OR est_perm='EST')"; }
			else if (cbMsgType.getText().compareToIgnoreCase("Expired NTM")==0) { 
				critMsgtyp=" AND identifiers LIKE 'NOTAM%' AND st='0' AND (C<'' OR est_perm!='PERM' OR est_perm!='EST')"; }
			else critMsgtyp="";	
			
			sCriteria=critMsgtyp+critCID+critCSN+critTMS+critSerie+critSeqFr+critSeqTo+critScope+critPurpose+critSubject+critLocation+
			critState+critSerial+critBirdNum+critOriginator;
			new Lists(sTblNm,sIdName,sID,sCriteria,pDate,pDateTo).Find();
		}
	}
}











