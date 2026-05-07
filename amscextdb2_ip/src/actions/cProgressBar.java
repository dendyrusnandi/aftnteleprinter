package actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import displays.AmscSplashScreen2;


public class cProgressBar {
	Shell shell;
	public ProgressBar bar;
	Label label;
	boolean flg=false;
	public cProgressBar(String sTitle) {
		shell = new Shell(SWT.CLOSE);
		shell.setText(sTitle);
	    bar = new ProgressBar(shell, SWT.SMOOTH);
	    bar.setForeground(new Color(AmscSplashScreen2.display,255,0,31));
	    bar.setBounds(5, 8, 500, 13);
		label = new Label(shell, SWT.CENTER);
		label.setText("Please wait...");
		label.setBounds(5,23,80,20);
		//label.setForeground(new Color(label.getShell().getDisplay(),255,0,31));
		shell.setBounds(400,300,520, 70);
	}
	
	public void create(int max) {
	    shell.open();
	    bar.setMaximum(max);
	}
	
	public void begin(){
		while ((!shell.isDisposed()) && (!flg)){
		    if (!AmscSplashScreen2.display.readAndDispatch())
		      AmscSplashScreen2.display.sleep();
		}
		shell.close();
	}
	
	public void finish(){
		flg=true;
	}
}
