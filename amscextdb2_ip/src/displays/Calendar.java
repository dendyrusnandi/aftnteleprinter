package displays;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import setting.Shorten;

public class Calendar {

	Shell shell;
	
	Shorten sh = new Shorten();
	
	public static CLabel clTimer;
	
	public Calendar(Shell shl) {
		shell = shl;
		formSetting();
		sh.shellStyle(shell, "Date/Time Properties", 0, 0);//sh.xpos, sh.yposTbl);
		shell.setLocation(MainForm.setLocX(shell), MainForm.setLocY(shell));
	}
	
	String getText() {
		return "Calendar";
	}

	void formSetting() {
		
		final Group dialog = new Group(shell, SWT.NONE); sh.groupStyle(dialog, 1, "");
		
		Group gDate = new Group(dialog, SWT.NONE); sh.groupStyle(gDate, 1, "&Date");
		final DateTime calendar = new DateTime (gDate, SWT.CALENDAR | SWT.BORDER);
		Group gTime = new Group(dialog, SWT.NONE); sh.groupStyle(gTime, 2, "&Time");
		Label label = new Label(gTime, SWT.NONE);
		sh.labelStyle1(label, "Current Time : ", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		clTimer = new CLabel(gTime, SWT.NONE);
		sh.clabelStyle(clTimer, "", 200, SWT.LEFT, SWT.CENTER, SWT.BOLD, sh.BLACK, null);//Images.img_time);
		label = new Label(gTime, SWT.NONE); 
		sh.labelStyle1(label, "Set new time : ", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		Composite cNewTime = new Composite(gTime, SWT.NONE); sh.composeStyle(cNewTime, 6, SWT.LEFT, SWT.CENTER);
		final Text tHour = new Text(cNewTime, SWT.BORDER); sh.textStyle(tHour, 25, 2, SWT.CENTER, SWT.CENTER, sh.numeric, "set Hour", true);
		label = new Label(cNewTime, SWT.NONE); sh.labelStyle1(label, ":", SWT.CENTER, SWT.CENTER, SWT.BOLD, sh.BLACK);
		final Text tMinute = new Text(cNewTime, SWT.BORDER); sh.textStyle(tMinute, 25, 2, SWT.CENTER, SWT.CENTER, sh.numeric, "set Minute", true);
		label = new Label(cNewTime, SWT.NONE); sh.labelStyle1(label, ":", SWT.CENTER, SWT.CENTER, SWT.BOLD, sh.BLACK);
		final Text tSecond = new Text(cNewTime, SWT.BORDER); sh.textStyle(tSecond, 25, 2, SWT.CENTER, SWT.CENTER, sh.numeric, "set Second", true);
		
		java.util.Date tgl = new java.util.Date();
		long waktujava = tgl.getTime();
		java.sql.Timestamp stamp = new java.sql.Timestamp(waktujava);
		String str = stamp.toString();
        String hh = str.substring(11,13);
        String mm = str.substring(14,16);
        String ss = str.substring(17,19);
		tHour.setText(hh);
		tMinute.setText(mm);
		tSecond.setText(ss);
		
		Composite comp = new Composite(dialog, SWT.NONE); sh.composeStyle(comp, 3, SWT.CENTER, SWT.CENTER); 
		Button bOk = new Button(comp, SWT.PUSH); 
		Button bCancel = new Button(comp, SWT.PUSH); 
		
		sh.buttonStyle(bOk,"&OK","Set new date/time",90,sh.DarkGrey,SWT.CENTER,SWT.CENTER,null);//Images.img_clear16);
		sh.buttonStyle(bCancel,"&Cancel","Cancel set new date/time",90,sh.DarkGrey,SWT.CENTER,SWT.CENTER,null);//Images.img_clear16);
		
		bOk.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
//				if (!tHour.getText().isEmpty() && Integer.parseInt(tHour.getText())>23) { DialogFactory.openInfoDialog(shell, getText(), "Only 23 Hours !"); tHour.setFocus(); } 
//				else if (!tMinute.getText().isEmpty() && Integer.parseInt(tMinute.getText())>59) { DialogFactory.openInfoDialog(shell, getText(), "Only 59 Minutes !"); tMinute.setFocus(); } 
//				else if (!tSecond.getText().isEmpty() && Integer.parseInt(tSecond.getText())>59) { DialogFactory.openInfoDialog(shell, getText(), "Only 59 Seconds !"); tSecond.setFocus(); } 
//				else {
	    		  String sDateTime="";
		          String sMM = Integer.toString((calendar.getMonth()+1)); if (sMM.length()==1) sMM = "0"+sMM;
		          String sDD = Integer.toString(calendar.getDay()); if (sDD.length()==1) sDD = "0"+sDD;
		          String sYYYY = Integer.toString(calendar.getYear());
//		          String shh = Integer.toString(time.getHours()); if (shh.length()==1) shh = "0"+shh;
//		          String smm = Integer.toString(time.getMinutes()); if (smm.length()==1) smm = "0"+smm;
//		          String sss = Integer.toString(time.getSeconds()); if (sss.length()==1) sss = "0"+sss;
		         
		          String shh = tHour.getText(); if (shh.length()==1) shh = "0"+shh;
		          String smm = tMinute.getText(); if (smm.length()==1) smm = "0"+smm;
		          String sss = tSecond.getText(); if (sss.length()==1) sss = "0"+sss;
		          
		          if (sMM.compareToIgnoreCase("01")==0) sMM="JAN";
		          else if (sMM.compareToIgnoreCase("02")==0) sMM="FEB";
		          else if (sMM.compareToIgnoreCase("03")==0) sMM="MAR";
		          else if (sMM.compareToIgnoreCase("04")==0) sMM="APR";
		          else if (sMM.compareToIgnoreCase("05")==0) sMM="MAY";
		          else if (sMM.compareToIgnoreCase("06")==0) sMM="JUN";
		          else if (sMM.compareToIgnoreCase("07")==0) sMM="JUL";
		          else if (sMM.compareToIgnoreCase("08")==0) sMM="AUG";
		          else if (sMM.compareToIgnoreCase("09")==0) sMM="SEP";
		          else if (sMM.compareToIgnoreCase("10")==0) sMM="OCT";
		          else if (sMM.compareToIgnoreCase("11")==0) sMM="NOV";
		          else if (sMM.compareToIgnoreCase("12")==0) sMM="DEC";
		          
		          sDateTime = sDD+" "+sMM+" "+sYYYY+" "+shh+":"+smm+":"+sss;
		          System.out.println("sdatetime:" + sDateTime + "#");
	    		  try {
	    			  Runtime.getRuntime().exec(new String[] { "date", "-s", sDateTime/*"1 Apr 2015 12:05:30"*/ } );
		          } catch (IOException ioe) {
		        	  System.err.println("Error: " + ioe.getMessage());
		          }
//				}
	    	  }	
        });
	      
	    bCancel.addSelectionListener (new SelectionAdapter () {
	    	public void widgetSelected (SelectionEvent e) {
	    		shell.close();
	    	}
	    });
	    		  
	}
}
