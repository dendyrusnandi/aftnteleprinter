package displays.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import displays.MainForm;

import readwrite.ReadFromFile;
import readwrite.WriteToTXT;
import setting.ButtonsSetting;
import setting.Colors;
import setting.Images;
import setting.Shorten;

public class HeaderFooterPDF {

	Shell shell;
	Shorten sh = new Shorten();
	ButtonsSetting bs = new ButtonsSetting();
	
	Text tHeader,tFooter;
	Button bOK,bCancel;
	
	public HeaderFooterPDF(Shell shl) {
		shell = shl;
		formSetting();
		sh.shellStyle(shell, "Setting PDF");
	}
	
	void formSetting() {
		
		final Group Group = new Group(shell, SWT.NONE); sh.groupStyle(Group, 1, "");
		Composite c1 = new Composite(Group, SWT.NONE); sh.compositeStyle(c1, 2, SWT.LEFT, SWT.CENTER);
		Label label = new Label(c1,SWT.NONE); sh.labelStyle1(label, "Header", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tHeader = new Text(c1,SWT.BORDER); sh.textStyle(tHeader, 400, 100, SWT.LEFT, SWT.LEFT, sh.upper, "PDF Header", true);
		label = new Label(c1,SWT.NONE); sh.labelStyle1(label, "Footer", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFooter = new Text(c1,SWT.BORDER); sh.textStyle(tFooter, 400, 100, SWT.LEFT, SWT.LEFT, sh.upper, "PDF Footer", true);

		Composite typeB = new Composite(Group, SWT.NONE); sh.compositeStyle(typeB, 1, SWT.CENTER, SWT.CENTER);
		label = new Label(typeB, SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelStyle(label, "", (MainForm.getWidthRightTop()+20)/3, SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
		
		Composite c2 = new Composite(Group, SWT.NONE); sh.compositeStyle(c2, 2, SWT.CENTER, SWT.CENTER);
		bOK = new Button(c2, SWT.PUSH); 
		bCancel = new Button(c2, SWT.PUSH); 
		
//		sh.buttonStyle(bOK, "&OK", "Set PDF header and footer", 80, 30, Colors.DarkGrey);
//		sh.buttonStyle(bCancel, "&Cancel", "Cancelling set PDF header and footer", 80, 30, Colors.DarkGrey);
		sh.buttonStyle(bOK, "&OK", "Set PDF header and footer", 80, Colors.DarkGrey, SWT.CENTER, SWT.LEFT, null);
		sh.buttonStyle(bCancel, "&Cancel", "Cancelling set PDF header and footer", 80, Colors.DarkGrey, SWT.CENTER, SWT.LEFT, null);
		
		String readFile = ReadFromFile.ReadInputFile2(MainForm.sFolder+"headerpdf.txt");
		if (!readFile.isEmpty() && readFile.contains("\n")) {
			String sub[] = readFile.split("\n");
			for (int i=0; i<sub.length; i++) {
				if (sub[i].startsWith("header=")) tHeader.setText(sub[i].replace("header=", ""));
				else if (sub[i].startsWith("footer=")) tFooter.setText(sub[i].replace("footer=", ""));
			}
		}
		
		bOK.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String data="",shead="",sfoot="";
				if (!tHeader.getText().isEmpty()) shead="header="+tHeader.getText()+"\n"; else shead="header=AIRNAV\n";
				if (!tFooter.getText().isEmpty()) sfoot="footer="+tFooter.getText(); else sfoot="footer=ELSA Intelligent AFTN Teleprinter";
				data=shead+sfoot;	
				WriteToTXT.write("headerpdf.txt",data);
				shell.close();
			}
		});	
		
		bCancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { shell.close(); } });	
	}
}
