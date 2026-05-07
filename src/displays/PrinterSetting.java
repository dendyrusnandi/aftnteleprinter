package displays;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import readwrite.ReadFromFile;
import readwrite.WriteToTXT;
import setting.ButtonsSetting;
import setting.Colors;
import setting.Shorten;

public class PrinterSetting {

	Shell shell;
	
	Shorten sh = new Shorten();
	ButtonsSetting bs = new ButtonsSetting();
	
	String filename="stlp.txt",data="";
	
	public PrinterSetting(Shell shl) {
		shell = shl;
		formSetting();
		sh.shellStyle(shell, "Printing");
	}
	
	void formSetting() {
		final Group grpSearch = new Group(shell, SWT.NONE); sh.groupStyle(grpSearch, 1, "");
  		Label label = new Label(grpSearch,SWT.NONE); sh.labelStyle1(label, "Which type of printer do you want to use?", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
  		
  		final Button bStandard = new Button(grpSearch, SWT.RADIO); 
  		final Button bContinuous = new Button(grpSearch, SWT.RADIO); 
  		
//  		sh.buttonRCStyle(bStandard, "S&tandard printer", "Print using standard printer", 200);//, 27);
//  		sh.buttonRCStyle(bContinuous, "Contin&uous printer", "Print using continuous printer", 200);//, 27);
  		sh.RadioCheckStyle(bStandard, "S&tandard printer", "Print using standard printer");
  		sh.RadioCheckStyle(bContinuous, "Contin&uous printer", "Print using continuous printer");
		
  		Composite typeB = new Composite(grpSearch, SWT.NONE); sh.compositeStyle(typeB, 1, SWT.CENTER, SWT.CENTER);
		label = new Label(typeB, SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelStyle(label, "", (MainForm.getWidthRightTop()+20)/3, SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
		
		Composite typeC = new Composite(grpSearch, SWT.NONE); sh.compositeStyle(typeC, 3, SWT.CENTER, SWT.CENTER);
		Button Setup = new Button(typeC, SWT.PUSH);
		Button Ok = new Button(typeC, SWT.PUSH);
		Button Cancel = new Button(typeC, SWT.PUSH);
		bs.SetCancel(Setup, Ok, Cancel);
		
		String st = ReadFromFile.ReadInputFile1("/tp/"+filename);
  		if (!st.isEmpty() && st.compareTo("1")==0) { bContinuous.setSelection(true); bStandard.setSelection(false); }
  		else if (!st.isEmpty() && st.compareTo("0")==0) { bContinuous.setSelection(false); bStandard.setSelection(true); }
  		
  		Setup.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					Runtime.getRuntime().exec("system-config-printer");
				} catch (IOException ioe) {
					System.err.println("Runtime info:" + ioe.getMessage());
				}
			}
		});
		
		Ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bStandard.getSelection()==true && bContinuous.getSelection()==false) data="0";
				else if (bStandard.getSelection()==false && bContinuous.getSelection()==true) data="1";
				WriteToTXT.write(filename, data);
			}
		});
		
		Cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String st = ReadFromFile.ReadInputFile1("/tp/"+filename);
		  		if (!st.isEmpty() && st.compareTo("1")==0) { bContinuous.setSelection(true); bStandard.setSelection(false); }
		  		else if (!st.isEmpty() && st.compareTo("0")==0) { bContinuous.setSelection(false); bStandard.setSelection(true); }
		  		shell.close();
			}
		});	
	}
}
