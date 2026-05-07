package displays;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import displays.tabs.TabREJECT;

import actions.Jdbc;

import readwrite.ReadFromFile;
import setting.Criteria;
import setting.Shorten;
import setting.Titles;


public class RejectForm {
	public boolean flgin;
	Shell shell;
	Label label;
	
	Shorten sh = new Shorten();
	Criteria crit = new Criteria();
	
	Font font = new Font(AmscSplashScreen2 .display, new FontData(sh.FONTFACE, sh.NORMAL, sh.NORMAL));
	ReadFromFile rff = new ReadFromFile();
	
	Text tPriority,tFiling,tOriginator,tOri_Ref,text,tError,tType;
	Text tDest1,tDest2,tDest3,tDest4,tDest5,tDest6,tDest7;
	Text tDest8,tDest9,tDest10,tDest11,tDest12,tDest13,tDest14;
	Text tDest15,tDest16,tDest17,tDest18,tDest19,tDest20,tDest21;
	
	public RejectForm(Shell shl) {
		shell=shl;
		createForm();
		sh.shellStyle(shell, "Rejected Form", 0, 0);//sh.xpos, sh.yposTbl);
		shell.setLocation(MainForm.setLocX(shell), MainForm.setLocY(shell));
	}
  	
  	void locText(Text text) {
  		sh.textStyle(text, 80, 8, SWT.LEFT, SWT.CENTER, sh.letter, "", false);
  	}
	
  	void enableText(boolean b) {
  		tPriority.setEnabled(b);tFiling.setEnabled(b);tOriginator.setEnabled(b);tOri_Ref.setEnabled(b);/*text.setEnabled(b);tError.setEnabled(b);*/tType.setEnabled(b);
  		tDest1.setEnabled(b);tDest2.setEnabled(b);tDest3.setEnabled(b);tDest4.setEnabled(b);tDest5.setEnabled(b);tDest6.setEnabled(b);tDest7.setEnabled(b);
  		tDest8.setEnabled(b);tDest9.setEnabled(b);tDest10.setEnabled(b);tDest11.setEnabled(b);tDest12.setEnabled(b);tDest13.setEnabled(b);tDest14.setEnabled(b);
  		tDest15.setEnabled(b);tDest16.setEnabled(b);tDest17.setEnabled(b);tDest18.setEnabled(b);tDest19.setEnabled(b);tDest20.setEnabled(b);tDest21.setEnabled(b);
  	}
  	
	void createForm() {
		crit.tgl();

		final Group Group = new Group(shell,SWT.NONE); sh.groupStyle(Group, 1, "");
		final Group GroupHeader = new Group(Group,SWT.NONE); sh.groupStyle(GroupHeader, 5, "Header AFTN");
		final Group GroupBody = new Group(Group,SWT.NONE); sh.groupStyle(GroupBody, 2, "Rejected Message");		
		
		Label label = new Label(GroupHeader,SWT.RIGHT); sh.labelStyle1(label, "Priority", SWT.RIGHT, SWT.TOP, SWT.BOLD, sh.BLUE);
		tPriority = new Text(GroupHeader,SWT.BORDER); sh.textStyle(tPriority, 20, 2, SWT.LEFT, SWT.TOP, sh.letter, "", false);
		label = new Label(GroupHeader,SWT.NONE); sh.labelStyle(label, "", 30, SWT.TOP, SWT.LEFT, SWT.BOLD, sh.BLUE);
		label = new Label(GroupHeader,SWT.RIGHT); sh.labelStyle1(label, "Address", SWT.RIGHT, SWT.TOP, SWT.BOLD, sh.BLUE);
		Composite compAdd = new Composite(GroupHeader, SWT.NONE); sh.composeStyle(compAdd, 7, SWT.LEFT, SWT.CENTER);
		tDest1 = new Text(compAdd,SWT.BORDER); tDest1.setFocus(); locText(tDest1);
		tDest2 = new Text(compAdd,SWT.BORDER); locText(tDest2);
		tDest3 = new Text(compAdd,SWT.BORDER); locText(tDest3);
		tDest4 = new Text(compAdd,SWT.BORDER); locText(tDest4);
		tDest5 = new Text(compAdd,SWT.BORDER); locText(tDest5);
		tDest6 = new Text(compAdd,SWT.BORDER); locText(tDest6);
		tDest7 = new Text(compAdd,SWT.BORDER); locText(tDest7);
		//--------------------------------------
		tDest8 = new Text(compAdd,SWT.BORDER); locText(tDest8);
		tDest9 = new Text(compAdd,SWT.BORDER); locText(tDest9);
		tDest10 = new Text(compAdd,SWT.BORDER); locText(tDest10);
		tDest11 = new Text(compAdd,SWT.BORDER); locText(tDest11);
		tDest12 = new Text(compAdd,SWT.BORDER); locText(tDest12);
		tDest13 = new Text(compAdd,SWT.BORDER); locText(tDest13);
		tDest14 = new Text(compAdd,SWT.BORDER); locText(tDest14);
		//--------------------------------------
		tDest15 = new Text(compAdd,SWT.BORDER); locText(tDest15);
		tDest16 = new Text(compAdd,SWT.BORDER); locText(tDest16);
		tDest17 = new Text(compAdd,SWT.BORDER); locText(tDest17);
		tDest18 = new Text(compAdd,SWT.BORDER); locText(tDest18);
		tDest19 = new Text(compAdd,SWT.BORDER); locText(tDest19);
		tDest20 = new Text(compAdd,SWT.BORDER); locText(tDest20);
		tDest21 = new Text(compAdd,SWT.BORDER); locText(tDest21);
		
		label = new Label(GroupHeader,SWT.RIGHT); sh.labelStyle1(label, "Filing Time", SWT.RIGHT, SWT.CENTER, SWT.BOLD, sh.BLACK);
		tFiling = new Text(GroupHeader,SWT.BORDER); sh.textStyle(tFiling, 50, 6, SWT.LEFT, SWT.CENTER, "", "(DDhhmm)", false);
		tFiling.setText(crit.filing_time);
		label = new Label(GroupHeader,SWT.NONE); sh.labelStyle(label, "", 30, SWT.TOP, SWT.LEFT, SWT.BOLD, sh.BLUE);
		label = new Label(GroupHeader,SWT.RIGHT); sh.labelStyle1(label, "Originator", SWT.RIGHT, SWT.CENTER, SWT.BOLD, sh.BLUE);
		Composite compOri = new Composite(GroupHeader, SWT.NONE); sh.composeStyle(compOri, 3, SWT.LEFT, SWT.CENTER);
		tOriginator = new Text(compOri,SWT.BORDER); locText(tOriginator);		
		label = new Label(compOri,SWT.RIGHT); sh.labelStyle1(label, "Originator's Reference", SWT.RIGHT, SWT.CENTER, SWT.BOLD, sh.BLACK);
		tOri_Ref = new Text(compOri,SWT.BORDER); sh.textStyle(tOri_Ref, 300, 61, SWT.LEFT, SWT.CENTER, sh.upper, "", false);		
		
		label = new Label(GroupBody,SWT.NONE); sh.labelStyle1(label, "Text Message", SWT.TOP, SWT.LEFT, SWT.BOLD, sh.BLUE);
		text = new Text(GroupBody, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER); sh.textAreaStyle(text, 500, 100, SWT.LEFT, SWT.CENTER, sh.upper, "");
		label = new Label(GroupBody,SWT.NONE); sh.labelStyle1(label, "Error Message", SWT.TOP, SWT.LEFT, SWT.BOLD, sh.BLACK);
		tError = new Text(GroupBody, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER); sh.textAreaStyle(tError, 500, 40, SWT.LEFT, SWT.CENTER, sh.upper, "");
		label = new Label(GroupBody,SWT.NONE); sh.labelStyle1(label, "Type of Message", SWT.LEFT, SWT.CENTER, SWT.BOLD, sh.BLACK);
		tType = new Text(GroupBody, SWT.BORDER); sh.textStyle(tType, 200, 200, SWT.LEFT, SWT.CENTER, sh.upper, "", false);
		
		enableText(false);

		tError.setEditable(false);
		
		text.setText(Jdbc.textmsg);
		tError.setText(Jdbc.errmsg);
		tType.setText(Jdbc.typemsg);
		
		tPriority.setText(Jdbc.dbprio);
		tFiling.setText(Jdbc.dbfiling);
		tOriginator.setText(Jdbc.dboriginator);
		tOri_Ref.setText(Jdbc.dboriref);
		
//		tDest1.setText(Jdbc.dbdest1);
		tDest1.setText(new ReadFromFile().ReadInputFile1(MainForm.sFolder+"destination.txt"));
		tDest2.setText("");//Jdbc.dbdest2);
		tDest3.setText("");//Jdbc.dbdest3);
		tDest4.setText("");//Jdbc.dbdest4);
		tDest5.setText("");//Jdbc.dbdest5);
		tDest6.setText("");//Jdbc.dbdest6);
		tDest7.setText("");//Jdbc.dbdest7);
		tDest8.setText("");//Jdbc.dbdest8);
		tDest9.setText("");//Jdbc.dbdest9);
		tDest10.setText("");//Jdbc.dbdest10);
		tDest11.setText("");//Jdbc.dbdest11);
		tDest12.setText("");//Jdbc.dbdest12);
		tDest13.setText("");//Jdbc.dbdest13);
		tDest14.setText("");//Jdbc.dbdest14);
		tDest15.setText("");//Jdbc.dbdest15);
		tDest16.setText("");//Jdbc.dbdest16);
		tDest17.setText("");//Jdbc.dbdest17);
		tDest18.setText("");//Jdbc.dbdest18);
		tDest19.setText("");//Jdbc.dbdest19);
		tDest20.setText("");//Jdbc.dbdest20);
		tDest21.setText("");//Jdbc.dbdest21);
		
	    Composite type = new Composite(Group, SWT.NONE); sh.composeStyle(type, 2, SWT.CENTER, SWT.CENTER);
		Button Send = new Button(type,SWT.PUSH); sh.buttonStyle(Send, "&Send", "", sh.widthBtn, sh.DarkGrey, SWT.CENTER, SWT.CENTER, null); 
		Button Clear = new Button(type,SWT.PUSH ); sh.buttonStyle(Clear, "&Clear", "", sh.widthBtn, sh.DarkGrey, SWT.CENTER, SWT.CENTER, null);
		
//		SUDAH_BETUL_TAPI_REJECT_BEDA_ID_MASIH_SALAH
//		Send.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				
//				if (text.getText().equals("")) {
//					DialogFactory.dialogInfo(shell, "Send Message","The value in field Text Message is required."); text.setFocus();
//				} else {
//					crit.tgl();
//					// update table reject
//					String msg = text.getText();
//					if (msg.contains("'")) msg = msg.replace("'", "`");
//					
//					String priority = "";
//					if (priority.equals("")) { priority = "FF"; } 
//					
//					String update = "UPDATE "+Lists.tblName+" SET priority='"+priority
//					+"',destination1='"+tDest1.getText()+"',destination2='"+tDest2.getText()+"',destination3='"+tDest3.getText()+"',destination4='"+tDest4.getText()+"',destination5='"+tDest5.getText()+"',destination6='"+tDest6.getText()+"',destination7='"+tDest7.getText()
//					+"',destination8='"+tDest8.getText()+"',destination9='"+tDest9.getText()+"',destination10='"+tDest10.getText()+"',destination11='"+tDest11.getText()+"',destination12='"+tDest12.getText()+"',destination13='"+tDest13.getText()+"',destination14='"+tDest14.getText()
//					+"',destination15='"+tDest15.getText()+"',destination16='"+tDest16.getText()+"',destination17='"+tDest17.getText()+"',destination18='"+tDest18.getText()+"',destination19='"+tDest19.getText()+"',destination20='"+tDest20.getText()+"',destination21='"+tDest21.getText()
//					+"',filing_time='"+tFiling.getText()+"',originator='"+tOriginator.getText()+"',ori_ref='"+tOri_Ref.getText()
//					+"',origin_message='"+msg+"',tgl='"+crit.tanggal+"' WHERE id_rjt_msg='"+Lists.sIdVal+"'";
//					Jdbc.select(update, "REJMSG");
//							
//					// insert into table check_status
//					String message = text.getText().toString();
//					if (message.length() >= 69) { message = message.substring(0,69); }
//						
//					String rejectType="";
//					if (TabREJECT.bATS.getSelection()==true && TabREJECT.bNOTAM.getSelection()==false && TabREJECT.bMETEO.getSelection()==false ) { rejectType="REJECT ATS"; }					
//					if (TabREJECT.bATS.getSelection()==false && TabREJECT.bNOTAM.getSelection()==true && TabREJECT.bMETEO.getSelection()==false ) { rejectType="REJECT NOTAM"; }
//					if (TabREJECT.bATS.getSelection()==false && TabREJECT.bNOTAM.getSelection()==false && TabREJECT.bMETEO.getSelection()==true ) { rejectType="REJECT METEO"; }
//
//					String insert = "INSERT INTO check_status (id,tbl_name,filing_time,status,jam,flag,tgl,aircraft_id,type," +
//							"series,originator,message,dof) VALUES ('"+Lists.sIdVal+"','"+Lists.tblName+"','"+crit.filing_time+
//							"','"+"waiting"+"','"+crit.filing_time+"','"+"0"+"','"+crit.tanggal+"','','"+rejectType+"','','"+
//							tOriginator.getText()+"','"+message+"','')";
//					Jdbc.select(insert, "REJMSG");
//					
//					triggerToServer();
//					
//					//refreshing table
//    				Lists.table.setRedraw(false);
//    				Lists.table.removeAll();
//    				TabREJECT.searchData();
//    				Lists.table.setRedraw(true);
//    				
//					shell.close();
//				}
//			}
//		});
		
		Send.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				if (text.getText().equals("")) {
					DialogFactory.openWarningRequired(shell, DialogFactory.send, "'Text Message'"); 
					text.setFocus();
				} else {
					
					System.out.println("Server A");
					new ReadFromFile().readDBRej(MainForm.sFolder+"ipA.txt");
					//STEP 3: Open a connection
					System.out.println("--URL:" + ConnectTo.DB_URL_REJ + "--");
					send(ConnectTo.DB_URL_REJ,ConnectTo.DB_USER_REJ,ConnectTo.DB_PASS_REJ);
					
					System.out.println("\nServer B");
					new ReadFromFile().readDBRej(MainForm.sFolder+"ipB.txt"); 
					//STEP 3: Open a connection
					System.out.println("--URL:" + ConnectTo.DB_URL_REJ + "--");
					send(ConnectTo.DB_URL_REJ,ConnectTo.DB_USER_REJ,ConnectTo.DB_PASS_REJ);
					
					triggerToServer();
					System.out.println("\n");
					
					//refreshing table
    				Lists.table.setRedraw(false);
    				Lists.table.removeAll();
    				TabREJECT.searchData();
    				Lists.table.setRedraw(true);
    				
					shell.close();
				}
			}
		});

		Clear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				text.setText(Jdbc.textmsg);
			}
		});
	}
	
	void send(String dburl,String dbuser,String dbpass) {
		crit.tgl();
		String msg = text.getText();
		if (msg.contains("'")) msg = msg.replace("'", "`");
		
		String priority = "";
		if (priority.equals("")) { priority = "FF"; } 
		
		String sQueryUPDT = "UPDATE "+Lists.tblName+" SET priority='"+priority
		+"',destination1='"+tDest1.getText()+"',destination2='"+tDest2.getText()+"',destination3='"+tDest3.getText()
		+"',destination4='"+tDest4.getText()+"',destination5='"+tDest5.getText()+"',destination6='"+tDest6.getText()
		+"',destination7='"+tDest7.getText()+"',destination8='"+tDest8.getText()+"',destination9='"+tDest9.getText()
		+"',destination10='"+tDest10.getText()+"',destination11='"+tDest11.getText()+"',destination12='"+tDest12.getText()
		+"',destination13='"+tDest13.getText()+"',destination14='"+tDest14.getText()+"',destination15='"+tDest15.getText()
		+"',destination16='"+tDest16.getText()+"',destination17='"+tDest17.getText()+"',destination18='"+tDest18.getText()
		+"',destination19='"+tDest19.getText()+"',destination20='"+tDest20.getText()+"',destination21='"+tDest21.getText()
		+"',originator='"+tOriginator.getText()+"',ori_ref='"+tOri_Ref.getText()
		+"',filing_time='"+tFiling.getText()+"',origin_message='"+msg+"',tgl='";
		
		String message = text.getText().toString();
		if (message.length() >= 69) { message = message.substring(0,69); }
	
		String rejectType="";
		if (TabREJECT.bATS.getSelection()==true && TabREJECT.bNOTAM.getSelection()==false && TabREJECT.bMETEO.getSelection()==false ) { rejectType="REJECT ATS"; }					
		if (TabREJECT.bATS.getSelection()==false && TabREJECT.bNOTAM.getSelection()==true && TabREJECT.bMETEO.getSelection()==false ) { rejectType="REJECT NOTAM"; }
		if (TabREJECT.bATS.getSelection()==false && TabREJECT.bNOTAM.getSelection()==false && TabREJECT.bMETEO.getSelection()==true ) { rejectType="REJECT METEO"; }

		String sQueryINS1 = "INSERT INTO check_status (id,tbl_name,status,flag,aircraft_id,series,dof,type,originator,message,jam,filing_time,tgl) VALUES ('";
		String sQueryINS2 = "'" +//id
				",'"+Lists.tblName+"'" +//tbl_name
				",'"+"waiting"+"'" +//status
				",'0'" +//flag
				",''" +//aircraft_id
				",''" +//series
				",''" +//dof
				",'"+rejectType+"'" +//type
				",'"+tOriginator.getText()+"'" +//originator
				",'"+message+"'" ;//+//message
//				",'"+crit.filing_time+"'" +//jam
//				",'"+crit.filing_time+"'" +//filing_time
//				",'"+crit.tanggal+"')";//tgl

		String tgl_pendek=Lists.rej_tgl.substring(0,16);
		String sQueryID="SELECT * FROM "+Lists.tblName+" WHERE " +
		"tbl_name='"+Lists.rej_tbl+"' AND "+
//		"id_rjt_msg='"+Lists.rej_id+"' AND "+
		"cid='"+Lists.rej_cid+"' AND "+
		"seq='"+Lists.rej_csn+"' AND "+
//		"tms='"+Lists.rej_tms+"' AND "+
//		"tgl='"+Lists.rej_tgl+"' AND "+
		"tgl LIKE '"+tgl_pendek+"%' AND "+
		"type_message='"+Lists.rej_typ+"' AND "+
		"free_text_error_message='"+Lists.rej_err+"' AND "+
		"origin_message LIKE '"+Lists.rej_msg+"%'";
		Jdbc.selectID("",crit.tanggal,crit.filing_time,sQueryID,sQueryUPDT,sQueryINS1,sQueryINS2,dburl,dbuser,dbpass);
	}
	
	void triggerToServer() {
		String ipAdds="";
		new ReadFromFile().readDB(MainForm.sFolder+"ipA.txt");
		String ipAdd1=getIP(ConnectTo.DB_URL);
		new ReadFromFile().readDB(MainForm.sFolder+"ipB.txt"); 
		String ipAdd2=getIP(ConnectTo.DB_URL);
		ipAdds=ipAdd1+","+ipAdd2;
		
		if (!ipAdds.isEmpty() && ipAdds.contains(",")) {
			String subIP[] = ipAdds.split(",");
			for (int iSub=0; iSub<subIP.length; iSub++) {
//				System.out.println("ip"+iSub+":"+subIP[iSub]+"^");
				try {
					DatagramSocket s = new DatagramSocket();
					byte[] line = new byte[10000];
					InetAddress dest = InetAddress.getByName(subIP[iSub]);//ipAdd);
					String data="elsa";
					line = data.getBytes();
					int len = line.length;
					DatagramPacket pkt = new DatagramPacket(line, len, dest, 1428);
					System.out.println("**Trigger data:"+data+"*len:"+len+"*dest:"+dest+"*port:"+1428);
					s.send(pkt);
					s.close();
				} catch (Exception err) {
					System.out.println("err" + err);
				}
			}
		}
		
	}
	
	String getIP(String ipAdd) {
		if (!ipAdd.isEmpty()) {
			int indSlash = ipAdd.indexOf("/");
			ipAdd=ipAdd.substring(indSlash+2, ipAdd.length());
			int indLastSlash = ipAdd.lastIndexOf("/");
			ipAdd=ipAdd.substring(0, indLastSlash);
		}
		return ipAdd;
	}
}

//tFiling = new Text(comp,SWT.BORDER);
//GridData gd1 = new GridData();
//gd1.widthHint = 50;
//tFiling.setTextLimit(6);
//tFiling.setLayoutData(gd1);
//tFiling.setText(crit.filing_time);
//final Calendar caltFiling = Calendar.getInstance();
//tFiling.addListener(SWT.Verify, new Listener() {
//	boolean ignore;
//	public void handleEvent(Event e) {
//		if (ignore) return;
//		e.doit = false;
//		StringBuffer buffer = new StringBuffer(e.text);
//		char[] chars = new char[buffer.length()];
//		buffer.getChars(0, chars.length, chars, 0);
//		if (e.character == '\b') {
//			for (int i = e.start; i < e.end; i++) {
//				switch (i) {
//					case 0: { buffer.append('0'); break; } //* [D]D *
//					case 1: { buffer.append('0'); break; } //* D[D] *
//					
//					case 2: { buffer.append('0'); break; } //* [H]H *
//					case 3: { buffer.append('0'); break; } //* H[H] *
//					
//					case 4: { buffer.append('0'); break; } //* [M]M * 
//					case 5: { buffer.append('0'); break; } //* M[M] *
//					
//					default:
//						return;
//				}
//			}
//			tFiling.setSelection(e.start, e.start + buffer.length());
//			ignore = true;
//			tFiling.insert(buffer.toString());
//			ignore = false;
//			tFiling.setSelection(e.start, e.start);
//			return;
//		}
//	
//		int start = e.start;
//		if (start > 10) return;
//		int index = 0;
//		for (int i = 0; i < chars.length; i++) {
//			if (chars[i] < '0' || '9' < chars[i]) return;
//			if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
//			if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
//			if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
//			index++;
//		}
//		
//		String newText = buffer.toString();
//		int length = newText.length();
//		StringBuffer date = new StringBuffer(tFiling.getText());
//		date.replace(e.start, e.start + length, newText);
//		
//		caltFiling.set(Calendar.YEAR, 1901);
//		caltFiling.set(Calendar.MONTH, Calendar.JANUARY);
//		caltFiling.set(Calendar.DATE, 1);
//		caltFiling.set(Calendar.HOUR_OF_DAY, 00);
//		caltFiling.set(Calendar.MINUTE, 00);
//		
//		String dd = date.substring(0,2); 
//		if (dd.indexOf('0') == -1) {
//			int day = Integer.parseInt(dd);
//			int maxDay = caltFiling.getActualMaximum(Calendar.DATE);
//			if (1 > day || day > maxDay) return;
//			caltFiling.set(Calendar.DATE, day);
//		} 
//		String hh = date.substring(2,4); 
//		if (hh.indexOf('0') == -1) {
//			int hour = Integer.parseInt(hh);
//			int maxHour = caltFiling.getActualMaximum(Calendar.HOUR_OF_DAY);
//			if (0 > hour || hour > maxHour) return;
//			caltFiling.set(Calendar.HOUR_OF_DAY, hour);
//		}
//		String mm = date.substring(4,6); 
//		if (mm.indexOf('0') == -1) {
//			int minute = Integer.parseInt(mm);
//			int maxMinute = caltFiling.getActualMaximum(Calendar.MINUTE);
//			if (0 > minute || minute > maxMinute) return;
//			caltFiling.set(Calendar.MINUTE, minute);
//		}
//		tFiling.setSelection(e.start, e.start + length);
//		ignore = true;
//		tFiling.insert(newText);
//		ignore = false;
//	}
//});