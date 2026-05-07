package actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import displays.AmscSplashScreen2;
import displays.MainForm;

import setting.Images;
import setting.Shorten;

public class BusyCursorSwt {
	
	Shorten sh = new Shorten();
	
	static Display display = AmscSplashScreen2.display;
	public static Shell shell = new Shell(display, SWT.NONE);
	  
	public BusyCursorSwt(String str) {
		init();
	    
//		shell.setText("The "+str+" will take some time to complete.");
		shell.setSize(500, 200);
		shell.setLocation(MainForm.setLocX(shell), MainForm.setLocY(shell));
		
		Label label = new Label(shell, SWT.CENTER); //sh.labelStyle1(label, "Wait until 3 seconds to "+str+"...", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label.setText("The "+str+" will take some time to complete");
		label.setBounds(0, 0, 400, 20);
		
	    ProgressBar pb3 = new ProgressBar(shell, SWT.INDETERMINATE);
	    pb3.setBounds(0, 20, 400, 20);
	    
	    shell.pack();
	    shell.open();

	    // Set up the event loop.
//	    while (!shell.isDisposed()) {
//	      if (!display.readAndDispatch()) {
//	        // If no more entries in event queue
//	        display.sleep();
//	      }
//	    }

//	    display.dispose();
	  }

	  private void init() {

	  }	
	}
	
//	public BusyCursorSwt(String str) {
//	    final Display display = AmscSplashScreen.display;
//	    final Shell shell = new Shell(SWT.DIALOG_TRIM);
//	    shell.setLayout(new GridLayout());
//	    shell.setText("Wait until 3 seconds to "+str+"...");
//	    
////	    final int[] nextId = new int[1];
//	    final Group grp = new Group(shell, SWT.NONE); sh.groupStyle1 (grp, 1, "");
//	    Composite comp = new Composite(grp, SWT.NONE); sh.compositeStyle(comp, 2, SWT.CENTER, SWT.CENTER);
////	    Label limg = new Label(comp, SWT.NONE); limg.setImage(Images.img_info48);
////		Label label = new Label(comp, SWT.NONE); sh.labelStyle1(label, "Wait until 3 seconds to "+str+"...", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
//		
//	    shell.setSize(350, 60);
//	    shell.setLocation(MainForm.setLocX(shell), MainForm.setLocY(shell));
//	   
//	    final int[] count = new int[] { 10 };
//	    final ProgressBar bar = new ProgressBar(shell, SWT.SMOOTH); 
//	    bar.setMaximum(count[0]);
////	    bar.setBounds(10, 10, 300, 32);
//	    shell.open();
////	    final int maximum = bar.getMaximum();
//
//	    new Thread() {
//	    	public void run() {
//	    		for (int i = 0; i < count[0]; i++) {
//	    			bar.setSelection(i + 1);
//	    			try {
//	    				Thread.sleep(300);
//	    			} catch (Throwable e) {
//	    				System.out.println(e.getMessage());
//	    			}
//	    		}
//	    		
////	    		for (final int[] i = new int[1]; i[0] <= maximum; i[0]++) {
////	    			try {
////	    				Thread.sleep(300);
////	    			} catch (Throwable th) { }
////	    			
////	    			if (display.isDisposed())
////	    				return;
////	    		  
////	    			display.asyncExec(new Runnable() {
////	    				public void run() {
////	    					if (bar.isDisposed())
////	    						return;
////	    					bar.setSelection(i[0]);
////	    				}
////	    			});
////	    		}
//	    	}
//	    }.start();
////	    shell.open();
////	    Runnable longJob = new Runnable() {
////			boolean done = false;
////			int id;
////
////			public void run() {
////				Thread thread = new Thread(new Runnable() {
////					public void run() {
////						id = nextId[0]++;
////						display.syncExec(new Runnable() {
////							public void run() {
//////								if (text.isDisposed()) return;
//////								text.append("\nStart long running task " + id);
////							}
////						});
////						
////						for (int i = 0; i < 10000; i++) {
////							if (display.isDisposed()) return;
//////							System.out.println("do task that takes a long time in a separate thread " + id);
////						}
////        
////						if (display.isDisposed()) return;
////						display.syncExec(new Runnable() {
////							public void run() {
//////								if (text.isDisposed()) return;
//////								text.append("\nCompleted long running task " + id);
////							}
////						});
////        
////						done = true;
////						display.wake();
////					}
////				});
////				thread.start();
////    
////				while (!done && !shell.isDisposed()) {
////					if (!display.readAndDispatch())
////						display.sleep();
////				}
////			}
////		};
////		BusyIndicator.showWhile(display, longJob);
//	    
////	    while (!shell.isDisposed()) {
////	    	if (!display.readAndDispatch())
////	    		display.sleep();
////	    	}
////	    display.dispose();
//	}
//}
