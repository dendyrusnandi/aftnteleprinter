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

import setting.Images;

import actions.Timer;


public class AmscSplashScreen2 {
	
	public static Display display;
	public static int xpos,ypos;
	
	public static void main(String[] args) {
		display = new Display();
		final int[] count = new int[] { 10 };
		
		final Shell splash = new Shell(SWT.ON_TOP);

		final ProgressBar bar = new ProgressBar(splash, SWT.NONE);
		bar.setMaximum(count[0]);
		
		Label label = new Label(splash, SWT.BORDER); label.setImage(Images.imgSPLASH);
    
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

	    		Timer.time();
	    		new ConnectTo(display);
//	    		new MainForm(display);
	    	}
	    });
	    
	    while (count[0] != 0) {
	    	if (!display.readAndDispatch())
	    		display.sleep();
	    	}
	    display.dispose();
	}
}