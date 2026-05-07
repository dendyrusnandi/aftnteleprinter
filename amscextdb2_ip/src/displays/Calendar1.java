package displays;

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

import setting.Shorten;

public class Calendar1 {

	Shell shell;
	String ID="";
	
	Shorten sh = new Shorten();
	
	public static CLabel clTimer;
	
	public Calendar1(Shell shl,String id) {
		ID=id;
		shell = shl;
		formSetting();
		sh.shellStyle(shell, "Date/Time Properties", 0, 0);
		shell.setLocation(MainForm.setLocX(shell), MainForm.setLocY(shell));
	}
	
	void formSetting() {
		
		final Group dialog = new Group(shell, SWT.NONE); sh.groupStyle(dialog, 1, "");
		
		Group gDate = new Group(dialog, SWT.NONE); sh.groupStyle(gDate, 1, "Date/Time");
		final DateTime calendar = new DateTime (gDate, SWT.CALENDAR | SWT.BORDER);
		Composite compTime = new Composite(gDate, SWT.NONE); sh.composeStyle(compTime, 2, SWT.CENTER, SWT.CENTER);
//		Group gTime = new Group(dialog, SWT.NONE); sh.groupStyle(gTime, 2, "&Time");
		Label label = new Label(compTime, SWT.NONE); 
		sh.labelStyle1(label, "Time : ", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		final DateTime time = new DateTime (compTime, SWT.TIME | SWT.BORDER | SWT.SHORT);// : hhmm
		
		
		Composite comp = new Composite(dialog, SWT.NONE); sh.composeStyle(comp, 3, SWT.CENTER, SWT.CENTER); 
		Button bOk = new Button(comp, SWT.PUSH); 
		Button bCancel = new Button(comp, SWT.PUSH); 
		
		sh.buttonStyle(bOk,"&OK","Set new date/time",90,sh.DarkGrey,SWT.CENTER,SWT.CENTER,null);//Images.img_clear16);
		sh.buttonStyle(bCancel,"&Cancel","Cancel set new date/time",90,sh.DarkGrey,SWT.CENTER,SWT.CENTER,null);//Images.img_clear16);
		
		bOk.addSelectionListener (new SelectionAdapter () {
	    	  public void widgetSelected (SelectionEvent e) {
	    		  String sDateTime="";
		          String sMM = Integer.toString((calendar.getMonth()+1)); if (sMM.length()==1) sMM = "0"+sMM;
		          String sDD = Integer.toString(calendar.getDay()); if (sDD.length()==1) sDD = "0"+sDD;
		          String sYYYY = Integer.toString(calendar.getYear());
		          String shh = Integer.toString(time.getHours()); if (shh.length()==1) shh = "0"+shh;
		          String smm = Integer.toString(time.getMinutes()); if (smm.length()==1) smm = "0"+smm;
		          String sss = Integer.toString(time.getSeconds()); if (sss.length()==1) sss = "0"+sss;
		         
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
		          System.out.println("sdatetime:::" + sDateTime + "#");
		          
//		          if (ID.compareToIgnoreCase("fr_ats")==0) {
//		        	  Tabs.tFrDD.setText(sDD);
//		        	  Tabs.tFrMM.setText(sMM);
//		        	  Tabs.tFrYYYY.setText(sYYYY);
//		        	  Tabs.tFrhh.setText(shh);
//		        	  Tabs.tFrmm.setText(smm); } 
//		          else if (ID.compareToIgnoreCase("to_ats")==0) {
//		        	  Tabs.tToDD.setText(sDD);
//		        	  Tabs.tToMM.setText(sMM);
//		        	  Tabs.tToYYYY.setText(sYYYY);
//		        	  Tabs.tTohh.setText(shh);
//		        	  Tabs.tTomm.setText(smm); } 
//		          
//		          else if (ID.compareToIgnoreCase("fr_notam")==0) {
//		        	  TabNOTAM.tFrDD.setText(sDD);
//		        	  TabNOTAM.tFrMM.setText(sMM);
//		        	  TabNOTAM.tFrYYYY.setText(sYYYY);
//		        	  TabNOTAM.tFrhh.setText(shh);
//		        	  TabNOTAM.tFrmm.setText(smm); } 
//		          else if (ID.compareToIgnoreCase("to_notam")==0) {
//		        	  TabNOTAM.tToDD.setText(sDD);
//		        	  TabNOTAM.tToMM.setText(sMM);
//		        	  TabNOTAM.tToYYYY.setText(sYYYY);
//		        	  TabNOTAM.tTohh.setText(shh);
//		        	  TabNOTAM.tTomm.setText(smm); }

		          shell.close();
	    	  }	
		});
	      
	    bCancel.addSelectionListener (new SelectionAdapter () {
	    	public void widgetSelected (SelectionEvent e) {
	    		shell.close();
	    	}
	    });
	    		  
	}
}
