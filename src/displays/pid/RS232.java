package displays.pid;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import setting.ButtonsSetting;
import setting.Colors;
import setting.Images;
import setting.Shorten;
import actions.RefreshTable;
import actions.TriggerAction;
import displays.DialogFactory;
import displays.MainForm;


public class RS232 {
	
	Shorten sh = new Shorten();
	ButtonsSetting bs = new ButtonsSetting();
	
	
	Shell shl;
	Label lbl;
	Text tbaud,tdigit,tcid,torg,tprevst;
	Button bON,bOFF,bONch,bOFFch,bEnable,bDisable,bITA2,bIA5,b3,b4,bBaud;
	static Text tseq;
	String sdevi="",sbaud="",sdigit="",stransid="",stseq="",sorg="",scode="",sprevst="",ssvcmsggen="",schnl="",ssvc="",sarof="";
	public static String sBaud[]={"50","75","150","300","600","1200","2400","4800","9600","19200"};
	public static String sPriority[]={"DD","FF","GG","KK","SS"};
	String scode1="",baud1="";
	int iSpace=10;

	
	String getText() {
		return "Communication Setup";
	}
	
	public RS232(Shell shell) {
		conf();
		shl=shell;
		Group group = new Group(shl, SWT.BORDER); sh.groupStyle(group, 1, "");
		Composite grp = new Composite(group, SWT.NONE); sh.compositeStyle(grp, 3, SWT.LEFT, SWT.CENTER);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle1(lbl, "Baudrate", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		tbaud = new Text(grp, SWT.BORDER); sh.textStyle(tbaud, 40, 6, SWT.LEFT, SWT.CENTER, sh.numeric, "Baudrate", false);
		bBaud = new Button(grp, SWT.PUSH); sh.buttonStyle(bBaud,"","Select Baudrate",bs.widthNavBtn,Colors.DarkGrey,SWT.CENTER,SWT.CENTER,Images.img_LIST);
		MainForm.separator(group);
		Button bok = new Button(group, SWT.PUSH); sh.buttonStyle(bok,"&OK","Click OK to apply",bs.widthBtn,Colors.DarkGrey,SWT.CENTER,SWT.CENTER,null);//Images.img_clear16);

		bBaud.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RefreshTable.refreshBaudrate(tbaud);
			}
		});
		tbaud.setText(sbaud);
		
		bok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//---------- validasi
				if (tbaud.getText().isEmpty()) {
					DialogFactory.openInfoDialog(shl, getText(), "The value in field Baudrate is required."); tbaud.setFocus(); }
				else {
					//---------- BAUDRATE
					if ((!tbaud.getText().isEmpty())&&(tbaud.getText().compareTo(sbaud)!=0)) {
						WriteToAFile wr = new WriteToAFile();
						wr.open("/tp/baudrate.txt");
						wr.write(tbaud.getText());
						wr.close();
						TriggerAction.trigger("baud,"+tbaud.getText(), 100);
						sbaud=tbaud.getText();
					}
					if (!shdisp.getDisplay().isDisposed()) {
		    			shdisp.getDisplay().syncExec(new Runnable() {
		    				public void run() {
		    					conf();
		    					MainForm.clBaudrate.setText("Baudrate : "+sbaud);
		    					shl.close();
		    				}
		    			});
		    		}
				}
			}
		});
	}
	
	public void open() {
		sh.shellStyle(shl, "RS232 Setup");
		shl.setLocation(MainForm.setLocX(shl), MainForm.setLocY(shl));
	}
	
	void conf() {
		sbaud="";
		sbaud = DisplayMain.readInputFile("/tp/baudrate.txt");
		if (!sbaud.isEmpty()) {
			String sarr[] = sbaud.split("\n");
			sbaud="";
			for (int i=0;i<1;i++) {
				sbaud=sarr[i];
			}
		}
	}
	
}

