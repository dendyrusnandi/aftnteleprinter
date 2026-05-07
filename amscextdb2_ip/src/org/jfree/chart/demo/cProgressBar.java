package org.jfree.chart.demo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.*;

import displays.AmscSplashScreen2;

//import com.isode.xuxa.desktop.XuxaMain;

public class cProgressBar {
	Shell shell;
	ProgressBar bar;
	Label label;
	boolean flg=false;
	String sTitleB="";
	cProgressBar(String sTitle) {
		shell = new Shell(SWT.CLOSE);
		shell.setText(sTitle);
	    bar = new ProgressBar(shell, SWT.SMOOTH);
	    bar.setForeground(new Color(AmscSplashScreen2.display,255,0,31));
	    bar.setBounds(5, 8, 500, 13);
		label = new Label(shell, SWT.LEFT);
		label.setText("Please wait...");
		label.setBounds(5,23,200,20);
		//label.setForeground(new Color(label.getShell().getDisplay(),255,0,31));
		shell.setBounds(400,300,520, 70);
	}
	
	public void create(int max) {
	    shell.open();
	    bar.setMaximum(max);
	}
	
	public void labelChange(String sTitle){
		/*label.setText("");
		label = new Label(shell, SWT.CENTER);
		label.setText(sTitle);
		label.setBounds(15,23,80,20);
		System.out.println("chang to "+sTitle);*/
	}
	
	public void begin(){
		while ((!shell.isDisposed()) && (!flg)){
		    if (!AmscSplashScreen2.display.readAndDispatch()) {
		    	AmscSplashScreen2.display.sleep();
		    	//System.out.println("sTitle:"+sTitleB);
		    }
		    
	    	label.setText(sTitleB);
		}
		shell.close();
	}
	
	public void finish(){
		flg=true;
	}
}
