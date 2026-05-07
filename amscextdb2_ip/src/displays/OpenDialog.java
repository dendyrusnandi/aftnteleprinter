package displays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import readwrite.ReadFromFile;
import setting.Images;
import setting.Shorten;


public class OpenDialog {
	public boolean flgin;
	Shell shell;
	Label label;
	
	Shorten sh = new Shorten();
	
	Font font = new Font(AmscSplashScreen2 .display, new FontData(sh.FONTFACE, sh.NORMAL, sh.NORMAL));
	ReadFromFile rff = new ReadFromFile();
	
	
	public OpenDialog() {
		
	}
	
	public boolean open(Shell pshell,final String sTitle) {
		flgin=false;
		
		shell=pshell;
		rff.readConfiguration();
		shell.setText(sTitle);
		shell.setSize(223, 135);
		shell.setLocation(MainForm.setLocX(shell), MainForm.setLocY(shell));
		
		Label limg = new Label(shell, SWT.NONE);
		limg.setImage(Images.imgInfo48);
		
		label = new Label(shell, SWT.NONE);
		label.setText("Are you sure ?");
		label.setFont(font);
		
		Button bOk = new Button(shell, SWT.PUSH); 
		Button bCancel = new Button(shell, SWT.PUSH);
		bCancel.setFocus();
		
		bOk.setText("&OK");
		bCancel.setText("&Cancel");
		
		bOk.setImage(null);//Images.img_ok16);
		bCancel.setImage(null);//Images.img_clear16);
		
		limg.setBounds(25,5,55,55);
		label.setBounds(95,25,100,25);
		
		bOk.setBounds(5,70,100,27);
		bCancel.setBounds(110,70,100,27);
		
		bOk.setBackground(sh.DarkGrey);
		bCancel.setBackground(sh.DarkGrey);
		
		bOk.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (sTitle.compareToIgnoreCase("quit")==0) System.exit(0);
			}
		});
		
		bCancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println("--cancelled--");
				shell.close();
			}
		});
		
		shell.open();
		while (!shell.isDisposed() && (!flgin)) {
			if (!AmscSplashScreen2.display.readAndDispatch())
				AmscSplashScreen2.display.sleep();
		}

//		System.out.println("disini:" + flgin + "@");
		return flgin;
	}
}
