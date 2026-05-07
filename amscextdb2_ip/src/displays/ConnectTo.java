package displays;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import readwrite.ReadFromFile;
import setting.Images;
import setting.Shorten;

public class ConnectTo {

	Shorten sh = new Shorten();
	
	public static Shell shell;
	Button bServerA, bServerB;
	Label lServerA, lServerB;
	
	// JDBC driver name and database URL
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	public static String DB_URL = "";//"jdbc:mysql://localhost/aed";
	public static String DB_NAME = "";
	//  Database credentials
	public static String DB_USER = "";//"root";
	public static String DB_PASS = "";//"elsaei";
	//untuk reject
	public static String DB_URL_REJ = "";//"jdbc:mysql://localhost/aed";
	public static String DB_USER_REJ = "";//"root";
	public static String DB_PASS_REJ = "";//"elsaei";

	public static String sServer="";
	
	
	public ConnectTo(Display display) {
		shell = new Shell (display, SWT.TITLE);
		shell.setLayout(new GridLayout());
		shell.setText("Connect to...");
		
		Composite grp = new Composite(shell, SWT.NONE); sh.composeStyle(grp, 2, SWT.CENTER, SWT.CENTER);
		
		lServerA = new Label(grp, SWT.CENTER); 
		sh.labelStyle1(lServerA, "Server A", SWT.CENTER, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		lServerB = new Label(grp, SWT.CENTER); 
		sh.labelStyle1(lServerB, "Server B", SWT.CENTER, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		
		bServerA = new Button(grp, SWT.PUSH); 
		bServerA.setToolTipText("Connect to Database Server A");
		bServerA.setImage(Images.imgServer256);
		bServerB = new Button(grp, SWT.PUSH); 
		bServerB.setImage(Images.imgServer256);
		bServerB.setToolTipText("Connect to Database Server B");
		addListener();
	    
	    shell.setSize(300, 200);
		shell.setLocation(MainForm.setLocX(shell), MainForm.setLocY(shell));
		shell.open();
		shell.addListener (SWT.Close, new Listener () {
			public void handleEvent (Event event) {
				//MainForm.rerun();
				System.exit(0);
			}
		});
	}
	
	
	void set(boolean b1, boolean b2, Color c1, Color c2, String server) {
		bServerA.setEnabled(b1); bServerB.setEnabled(b2);	
		lServerA.setBackground(c1);
		lServerB.setBackground(c2);
//		shell.close();
		shell.dispose();//preventing display ConnTo 2 times, because adding swt.close

		sServer=server;
		String flnm="";
		if (server.compareTo("A")==0) flnm="ipA.txt";
		else if (server.compareTo("B")==0) flnm="ipB.txt";
		
		String path=MainForm.sFolder+flnm;
		new ReadFromFile().readDB(path);
		System.out.println("Connect to Server "+server+"...");
		new MainForm(AmscSplashScreen2.display);
	}
	
	void addListener() {
		bServerA.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				set(false, true, sh.GREEN, sh.Grey, "A");
			}
		});
		
		bServerB.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				set(true, false, sh.Grey, sh.GREEN, "B");
			}
		});
			
	}
}
