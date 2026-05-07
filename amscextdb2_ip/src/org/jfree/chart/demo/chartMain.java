package org.jfree.chart.demo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import displays.AmscSplashScreen2;


public class chartMain {

	/**
	 * @param args
	 */
	static Display display = AmscSplashScreen2.display;//XuxaMain.getDisplay();
	public static criteria criteria1;
	
	public static void main(String[] args) {
	}
	
	public static void run(Shell shell) {
		criteria1=new criteria(display,shell);
		criteria1.open();
  	}
	
	static Display getDisplay(){
		return display;
	}
}
