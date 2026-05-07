package displays.tabs;

import java.util.Calendar;

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
import org.eclipse.swt.widgets.Text;

import setting.Criteria;
import setting.Shorten;
import setting.Titles;
import displays.Lists;
import displays.MainForm;

public class TabFREE {
	
	Shorten sh = new Shorten();
	Criteria cAts = new Criteria();
	
	Label label;
	Text tFrom,tTo,tFreeText;
	Button bClear,bFind;
	
	public static String sTblNm="air_message_free_text",sIdName="id_free_text",sID=Titles.stFREETEXT,sCriteria="";
	

	public TabFREE() { }
	
	public void comp(Composite shell) {
		final Composite Group = new Composite(shell, SWT.NONE); sh.composeStyle(Group, 3, SWT.CENTER, SWT.LEFT);
		Group grp1 = new Group(Group, SWT.NONE); sh.groupStyle(grp1, 2, "Date Range");
		Group grp3 = new Group(Group, SWT.NONE); sh.groupStyle(grp3, 2, "Data");
		final Composite grp2 = new Composite(Group, SWT.NONE); sh.composeStyle(grp2, 1, SWT.CENTER, SWT.CENTER);
//		Group grp2 = new Group(Group, SWT.NONE); sh.groupStyle(grp2, 1, " ");
		
		label = new Label(grp1, SWT.RIGHT); sh.labelStyle1(label, "From", SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tFrom = new Text(grp1, SWT.BORDER); sh.textStyle(tFrom, 130, 16, SWT.LEFT, SWT.LEFT, "", "Date/Time From (YYYY-MM-DD hh:mm)", true);
		label = new Label(grp1, SWT.RIGHT); sh.labelStyle1(label, "To", SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tTo = new Text(grp1, SWT.BORDER); sh.textStyle(tTo, 130, 16, SWT.LEFT, SWT.LEFT, "", "Date/Time To (YYYY-MM-DD hh:mm)", true);
		
		bFind = new Button(grp2, SWT.PUSH); 
		bClear = new Button(grp2, SWT.PUSH); sh.setCF(bClear, bFind);
		
		label = new Label(grp3, SWT.NONE); sh.labelStyle1(label, "Freetext", SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tFreeText = new Text(grp3, SWT.BORDER); sh.textStyle(tFreeText, 250, 500, SWT.LEFT, SWT.CENTER, sh.upper, "Free text", true);

		Label ll = new Label(grp3, SWT.NONE); ll.setVisible(false);
		Text tText = new Text(grp3, SWT.BORDER); tText.setVisible(false);
		
		SearchListener();
	}
	
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//Listener
	private void discard() {
		//First, we make sure the user does not see what we're doing.
		Lists.table.setRedraw( false );
		//Then we remove all columns.
		while ( Lists.table.getColumnCount() > 0 ) {
			Lists.table.getColumns()[ 0 ].dispose();
		}
		Lists.table.removeAll();
		//And finally we let the user see what we did.
		Lists.table.setRedraw( true );
		
		sTblNm="air_message_free_text";sIdName="id_free_text";sID=Titles.stFREETEXT; new Lists().col(sID);
		
//		tCID.setText("");
//		tCSN.setText("");
//		tTMS.setText("");
		tFreeText.setText("");
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
		
		bClear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MainForm.clearTbl();
				discard();
				Lists.discard();
			}
		});
		
		bFind.addSelectionListener(new SelectionAdapter() { 
			public void widgetSelected(SelectionEvent e) { searchData(); } });
		
		tFrom.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tTo.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
		
		tFreeText.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { enter(e); } });
	}
	
	void searchData() {
		sTblNm="air_message_free_text"; 
		sIdName="id_free_text"; 
		sID=Titles.stFREETEXT;
		MainForm.clearTbl(); 
		new Lists().col(sID);
		FindListener();
	}
	
	void enter(KeyEvent e) {
		if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) { searchData(); }
	}
	
	private void FindListener() {

		String critFree="";

		// Date Range
		String pDate = tFrom.getText().toString();
		String pDateTo = tTo.getText().toString();
		//Data
		String pMsgAll = tFreeText.getText().toString();
		if (!pMsgAll.isEmpty()) {
			String kriteria="";
            
			//OR
            if (pMsgAll.contains("/ ")) pMsgAll=pMsgAll.replaceAll("/ ", "/");
            if (pMsgAll.contains(" /")) pMsgAll=pMsgAll.replaceAll(" /", "/");
            if (pMsgAll.contains(" / ")) pMsgAll=pMsgAll.replaceAll(" / ", "/");
            //AND
            if (pMsgAll.contains(", ")) pMsgAll=pMsgAll.replaceAll(", ", ",");
            if (pMsgAll.contains(" ,")) pMsgAll=pMsgAll.replaceAll(" ,", ",");
            if (pMsgAll.contains(" , ")) pMsgAll=pMsgAll.replaceAll(" , ", ",");
            
            //TANPA OR-AND
            if (!pMsgAll.contains("/") && !pMsgAll.contains(",")) { 
            	kriteria = " AND manual_ats LIKE '%"+pMsgAll+"%'";
            } 
            // DENGAN OR
            else if (pMsgAll.contains("/") && !pMsgAll.contains(",")) { 
            	kriteria=" AND (";
				String sarrOR[] = pMsgAll.split("/");
				for (int idOR=0;idOR<sarrOR.length;idOR++) {
					if (idOR!=0) { kriteria+=" OR "; }
					kriteria+="manual_ats LIKE ";
					kriteria+="'%"+sarrOR[idOR]+"%'";
				}
				kriteria+=")";            	
            } 
            // DENGAN AND
            else if (pMsgAll.contains(",") && !pMsgAll.contains("/")) { 
            	kriteria=" AND (";
				String sarrAND[] = pMsgAll.split(",");
				for (int idAND=0;idAND<sarrAND.length;idAND++) {
					if (idAND!=0) { kriteria+=" AND "; }
					kriteria+="manual_ats LIKE ";
					kriteria+="'%"+sarrAND[idAND]+"%'";
				}
				kriteria+=")";  
            } 
            // DENGAN OR-AND
            else if (pMsgAll.contains("/") && pMsgAll.contains(",")) {
            	String tempMsgAl = pMsgAll;
            	
            	kriteria=" AND (";
				String sarrOR[] = tempMsgAl.split("/");
				for (int idOR=0;idOR<sarrOR.length;idOR++) {
					if (idOR!=0) { kriteria+=" OR"; }

					String sand="";
					if (sarrOR[idOR].contains(",")) {
						sand=" (";
						String sarrAND[] = sarrOR[idOR].split(",");
						for (int idAND=0;idAND<sarrAND.length;idAND++) {
							if (idAND!=0) { sand+=" AND "; }
							sand+="manual_ats LIKE '%"+sarrAND[idAND]+"%'";
						}
						sand+=")";  
					} else {
						sand=" (";
						sand+="manual_ats LIKE '%"+sarrOR[idOR]+"%'";
						sand+=")";
					}

					kriteria+=sand;
				}
				kriteria+=" )";  
				
            }
            critFree = kriteria;
		}
		
		sCriteria=critFree;
		new Lists(sTblNm,sIdName,sID,sCriteria,pDate,pDateTo).Find();
		
	}
	
	private void FindListenerASLI() {
		String critFree="";

		// Date Range
		String pDate = tFrom.getText().toString();
		String pDateTo = tTo.getText().toString();
		//Data
		String pMsgAll = tFreeText.getText().toString();
		if (pMsgAll.compareTo("") != 0) {
            String[] sub = new String[1000];
            String beda="",aa="";

            critFree = " AND";
            if (pMsgAll.contains(", ")) pMsgAll=pMsgAll.replaceAll(", ", ",");
            else if (pMsgAll.contains(" , ")) pMsgAll=pMsgAll.replaceAll(" , ", ",");
            
            if (pMsgAll.contains(",")) { //kriteria yang dicari : banyak
            	sub = pMsgAll.split(",");
	            for (int i = 0; i < sub.length; i++) {
//	            	if (sub[i].contains(" ")) sub[i] = sub[i].replaceFirst("\\s+", "");
	            	String koma = " OR (manual_ats LIKE '%"+sub[i]+"%')";
	            	if (!koma.equals(beda)) {
						aa+=koma+"";
					}
					beda=koma;
	            }
	            
	            if (aa.startsWith(" OR ")) {
	            	aa = aa.replaceFirst(" OR ", "");
	            	critFree+=" ("+aa+")";
	            }
            } else if (pMsgAll.contains("&")) { //berpasangan
            	critFree = " AND manual_ats LIKE '%";
            	sub = pMsgAll.split("&");
	            for (int i = 0; i < sub.length; i++) {
//	            	if (sub[i].contains(" ")) sub[i] = sub[i].replaceFirst("\\s+", "");
	            	System.out.println("sub[" + i + "]" + sub[i]);
	            	String koma = sub[i]+"%";
	            	if (!koma.equals(beda)) {
						aa+=koma;
					}
					beda=koma;
	            }
	            
	            critFree+=aa+"'";
            } else { 
            	critFree = " AND manual_ats LIKE '%"+pMsgAll+"%'";
            } 
		}
		
//		if (!tFreeText.getText().isEmpty()) critFree+=" AND manual_ats LIKE '%"+tFreeText.getText()+"%'";
		sCriteria=critFree;
		new Lists(sTblNm,sIdName,sID,sCriteria,pDate,pDateTo).Find();
	}
}