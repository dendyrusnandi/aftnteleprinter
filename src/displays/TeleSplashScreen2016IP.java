/**
 * create a splash screen
 */
package displays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import actions.RefreshTable;

import readwrite.ReadFromFile;
import setting.Images;
import threads.Timer;
import threads.TimerIPLocal;
import databases.jdbc;
import displays.pid.Dtgram;
import displays.pid.timeoutext;


public class TeleSplashScreen2016IP {
	public static String telestat="";
	
	
	public static Display display;
	public static int xpos,ypos;
	
	public static void main(String[] args) {
		display = new Display();
		final int[] count = new int[] { 10 };
		
		final Shell splash = new Shell(SWT.ON_TOP);

		final ProgressBar bar = new ProgressBar(splash, SWT.NONE);
		bar.setMaximum(count[0]);
		
		Label label = new Label(splash, SWT.BORDER);
		label.setImage(Images.img_SPLASH_BIG);
    
		FormLayout layout = new FormLayout();
		splash.setLayout(layout);
		FormData labelData = new FormData();
		labelData.right = new FormAttachment(100, 0);
		labelData.bottom = new FormAttachment(92, 0);
	    label.setLayoutData(labelData);
	    FormData progressData = new FormData();
	    progressData.left = new FormAttachment(0, 5);
	    progressData.right = new FormAttachment(100, -5);
	    progressData.bottom = new FormAttachment(100, -5);
	    bar.setLayoutData(progressData);
	    splash.pack();
	    
	    Rectangle splashRect = splash.getBounds();
	    Rectangle displayRect = display.getBounds();
	    xpos = (displayRect.width - splashRect.width) / 2;
	    ypos = (displayRect.height - splashRect.height) / 2;
	    splash.setLocation(xpos, ypos);
	    splash.open();
	    
	    display.asyncExec(new Runnable() {
	    	public void run() {
	    		for (int i = 0; i < count[0]; i++) {
	    			bar.setSelection(i + 1);
	    			try {
	    				Thread.sleep(50);
	    			} catch (Throwable e) {
	    				System.out.println(e.getMessage());
	    			}
	    		}
	    		splash.close();
	    		
	    		Runnable theJob=new Dtgram();
	    		Thread t=new Thread(theJob)	;
	    		t.setName("Datagram Thread");
	    		t.start();
	    		
	    		Runnable rmsg=new timeoutext();
	    		Thread tmsg=new Thread(rmsg);
	    		tmsg.setName("Test message Thread");
	    		tmsg.start();
	    		
	    		Timer.time();
	    		TimerIPLocal.time();
	    		new jdbc();
	    		//new MainForm();
	    		
//	    		String 
	    		telestat = ReadFromFile.ReadInputFile1("/tp/template/telestat.txt");
	    		
	    		if (telestat.isEmpty()) telestat="1";
	    		if (telestat.compareTo("1")==0) {
	    			System.out.println("\n--full menu teleprinter--\n");
	    			new MainForm();
	    		} else if (telestat.compareTo("0")==0) {
	    			System.out.println("\n--single menu teleprinter--\n");
	    			RefreshTable.refreshNewAFTN("NewAFTN","");
	    		}
	    	}
	    });
	    
	    while (count[0] != 0) {
	    	if (!display.readAndDispatch())
	    		display.sleep();
	    	}
	    display.dispose();
	}
	
	//add
	public static Display getDisplay() {
		return display;
	}
}