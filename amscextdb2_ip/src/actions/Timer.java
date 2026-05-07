package actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;


import readwrite.ReadFromFile;
import setting.Images;
import setting.Shorten;
import displays.AmscSplashScreen2;
import displays.Calendar;
import displays.ConnectTo;
import displays.MainForm;

public class Timer {

	static Shorten sh = new Shorten();
	static ReadFromFile rff = new ReadFromFile();
	
	static String str="";
	public static Thread threadtime;
	
	public Timer() {
	
	}
	
	public static void time() {
		threadtime = new Thread() {
            public void run() {
                while (true) {
                    AmscSplashScreen2.display.syncExec(new Runnable() {
                        @Override
                        public void run() {
//                        	final DateFormat datetimeFormat = new SimpleDateFormat("EEE, dd/MM/yyyy HH:mm:ss");// aaa");
                        	final DateFormat datetimeFormat = new SimpleDateFormat("EEE, yyyy-MM-dd HH:mm:ss");// aaa");
                        	if (MainForm.lTimer!=null) {
                        		if (!MainForm.lTimer.isDisposed()) {
                        			Date date = new Date();
                                    str = datetimeFormat.format(date);
                                    sh.labelStyle1(MainForm.lTimer, "  Date/Time : "+str+"  ", SWT.LEFT, SWT.CENTER, SWT.BOLD, sh.BLACK);
                        		}
                        		
                        	}
                        	
                        	final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");// aaa");                        	
                        	if (Calendar.clTimer!=null) {
                        		if (!Calendar.clTimer.isDisposed()) {
                        			Date date = new Date();
                                    String str = timeFormat.format(date);
                                    sh.clabelStyle(Calendar.clTimer, str, 200, SWT.LEFT, SWT.CENTER, SWT.BOLD, sh.BLUE, null);
                        		}
                        		
                        	}
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        threadtime.setDaemon(true);
        threadtime.start();
        
	}
}
