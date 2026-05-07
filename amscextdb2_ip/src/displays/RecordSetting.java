package displays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import readwrite.WriteToTXT;
import setting.Shorten;

public class RecordSetting {

	Shell shell;
	String ID="";
	
	Shorten sh = new Shorten();
	
	public static CLabel clTimer;
	static Text tRec;
	
	public RecordSetting(Shell shl) {
		shell=shl;
		formSetting();
		sh.shellStyle(shell, "Limit Record Setting", 0, 0);
		shell.setLocation(MainForm.setLocX(shell), MainForm.setLocY(shell));
	}
	
	void formSetting() {
		
		final Group dialog = new Group(shell, SWT.NONE); sh.groupStyle(dialog, 6, "");
		Label label = new Label(dialog, SWT.NONE); sh.labelStyle1(label, "Limit Rec : ", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		tRec = new Text(dialog, SWT.BORDER); sh.textStyle(tRec, 50, 6, SWT.LEFT, SWT.CENTER, sh.numeric, "limit number", true);
		label = new Label(dialog, SWT.NONE); sh.labelStyle1(label, "records per page", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		label = new Label(dialog, SWT.NONE); sh.labelStyle(label, "", 10, SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		Button bOK = new Button(dialog, SWT.PUSH); sh.buttonStyle(bOK, "OK", "Set record limit", 80, sh.DarkGrey, SWT.CENTER, SWT.CENTER, null);
		Button bCancel = new Button(dialog, SWT.PUSH); sh.buttonStyle(bCancel, "Cancel", "Cancel setting", 80, sh.DarkGrey, SWT.CENTER, SWT.CENTER, null);
		
		tRec.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) { setLimit(); } }
	    });
		
		bOK.addSelectionListener(new SelectionAdapter() { public void widgetSelected(SelectionEvent e) { setLimit(); } });
		bCancel.addSelectionListener(new SelectionAdapter() { public void widgetSelected(SelectionEvent e) { shell.close(); } });
	}
	
	void setLimit() {
		if (tRec.getText().isEmpty()) {
			DialogFactory.openWarningRequired(shell, "Record Setting", "'Limit Rec'"); 
			tRec.setFocus();			
		} else {
			String s2="/aed/rowlimit.txt";
			WriteToTXT wr = new WriteToTXT();
			wr.open(s2);
			wr.write(tRec.getText());
			wr.close();
			shell.close(); }
	}
}
