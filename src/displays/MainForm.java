package displays;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import readwrite.ReadFromFile;
import setting.ButtonsSetting;
import setting.Colors;
import setting.Images;
import setting.Shorten;
import setting.Times;
import actions.Close;
import actions.RefreshTable;
import displays.pid.DisplayMain;
import displays.pid.WriteToAFile;
import displays.tables.TableOutgoing;


public class MainForm {
	
	ReadFromFile rff = new ReadFromFile();
	static Shorten sh = new Shorten();
	Times cAts = new Times();
	ButtonsSetting bs = new ButtonsSetting();
	
	public static final String sFolder = "/tp/template/";
	public static final String sFolderPics = sFolder+"pics/";
	public static final String sPath = "/tmp/ramdisk0/";
	
	public static String queryQueuelp = "SELECT * FROM queuelp WHERE tgl!='0000-00-00 00:00:00'";
	public static String queryWarning = "SELECT * FROM warning";
	
	public static Shell shell;
	public static Composite Height_MainForm;

	public static int ixLine=0,ixBaudrate=0,ixCode=0,ixSvcMsgGen=0,ixtransid=0,ixDigit=0,ixTseq=0,ixRseq=0,ixQueue=0,ixWarning=0,ixfree=0,
	ixPrinterType=0,ixTime=0,ixNis=0,ixRefuse=0,ixIP=0;
	static int iWidthRightTop=0;
	
	ToolItem itemAFTN,itemFPL,itemDLA,itemCHG,itemCNL,itemDEP,itemARR,itemROUTE,itemACFT,itemREG,itemTestMsg,itemCommSetup,itemBell;
	
	Menu menuBar, mnAFTNMsg, mnNewATS, mnMaintenance, mnView, mnStatus, mnWindow;
	public static MenuItem miQueue;
	MenuItem mi,miAFTNMsg, miNewAFTN, miMaintenance, miView, miStatus, miCloseWindow,
	miSrchAFTN,miAMO,miFREE,miaftn,mialr,mircf,mifpl,midla,michg,micnl,midep,miarr,micdn,micpl,miest,miacp,milam,mirqp,mirqs,mispl,miTest,
	miRoute,miType9b,miReg,miLocind,miAbbr,miWarning,miCommunication,miPrinter,miPDFSetting,miOutgoing,miWindow;

	public static DatagramSocket socket;
	//public static CLabel clLine,clBaudrate,clCode,clSvcMsgGen,clCID,clDigit,clTseq,clRseq,clQueue,clWarning,clFree,clPrinterType,clTimer,
		//clPQueue,clNis,clRefuse,clLocalIP,clDestIP;
	public static CLabel clLine,clBaudrate,clCID,clDigit,clTseq,clQueue,clWarning,clFree,clPrinterType,clTimer,
	clPQueue,clNis,clLocalIP,clDestIP;
	public static Button bRefresh;
	
	private Display disp;
	
	
	public static int dx=0,dy=0;

	Text tCid_ATS,tSeq_ATS,tFreeText_ATS,tFrom_ATS,tTo_ATS;
	Button bIncoming_ATS,bOutgoing_ATS,Search,Clear;
	
	
	public static int getDx() { return dx; }
	public static int getDy() { return dy; }
	public static int getWidthRightTop() {
		iWidthRightTop = dx-100; return iWidthRightTop; }
	
	public static Composite separator(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		sh.compositeStyle(composite, 1, SWT.FILL, SWT.TOP);
        Label line = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
        line.setLayoutData(new GridData(SWT.FILL, SWT.END, true, true));
        return composite;
	}
	
	public MainForm() {
		ReadFromFile.readConfiguration();
//		freeamo = rff.ReadInputFile1("/tp/template/freeamo.txt");
		disp = TeleSplashScreen2016IP.display;

		String clientAreaa = disp.getClientArea().toString();
		if (clientAreaa.startsWith("Rectangle")) clientAreaa = clientAreaa.replace("Rectangle {", "");
		if (clientAreaa.endsWith("}")) clientAreaa = clientAreaa.replace("}", "");
		String subs[] = null;
		subs = clientAreaa.split(",");
		for (int ica=0; ica<subs.length; ica++) {
			if (subs[ica].startsWith(" ")) subs[ica] = subs[ica].replace(" ", "");
		}
		int xx = Integer.parseInt(subs[0]);
		int yy = Integer.parseInt(subs[1]);
		dx = Integer.parseInt(subs[2]);
		dy = Integer.parseInt(subs[3]);
		System.out.println("Resolution display="+dx+"x"+dy);
		
		String sTitle = ReadFromFile.getTitle();
		if (sTitle==null || sTitle.isEmpty()) sTitle = "Intelligent AFTN Teleprinter [ ELSA AFTN ]";
		shell = new Shell(disp, SWT.TITLE);
		shell.setLocation(xx, yy);
		shell.setText(sTitle);
		shell.setImage(Images.img_workstation);
		shell.setLayout(new GridLayout(1, true));
		
	    //create a menu bar
	    menubar(shell);
	    
	    //create a tool bar
	    coolbar1(shell);
	    
	    ixLine = ReadFromFile.getXline();
	    ixBaudrate = ReadFromFile.getXbaud();
	    ixCode = ReadFromFile.getXcode();
	    ixSvcMsgGen= ReadFromFile.getWidthSrvMsgGen();
	    ixtransid = ReadFromFile.getXcid();
	    ixDigit = ReadFromFile.getXdigit();
	    ixTseq = ReadFromFile.getXtseq();
	    ixRseq = ReadFromFile.getXrseq();
	    ixQueue = ReadFromFile.getXqueue();
	    ixWarning = ReadFromFile.getXwarning();
	    ixfree = ReadFromFile.getXfree();
	    ixPrinterType = ReadFromFile.getXprintertype();
	    ixTime = ReadFromFile.getXtime();
	    ixNis = ReadFromFile.getXnis();
	    ixRefuse = ReadFromFile.getXrefuse();
	    ixIP = ReadFromFile.getXip();
	    
	    Group grpMain = new Group(shell, SWT.NONE); sh.groupStyle(grpMain, 1, "");
	    Group g1 = new Group(grpMain, SWT.NONE); sh.groupStyle(g1, 1, "Search AFTN Messages"); g1(g1);
		Group g2 = new Group(grpMain, SWT.BORDER); sh.groupStyle(g2, 1, ""); g2(g2);
		Group g3 = new Group(grpMain, SWT.BORDER); sh.groupStyle(g3, 1, ""); g3(g3);
		
	    shell.pack();
	    shell.open();

	    addListeners(shell);
	    
	    while (!shell.isDisposed()) {
	    	if (!disp.readAndDispatch()) {
	    		disp.sleep();
	    	}
	    }
	}	
	
	void g1(Group g1) { //Search AFTN Messages
		Composite g1c1 = new Composite(g1, SWT.NONE); sh.compositeStyle(g1c1, 13, SWT.LEFT, SWT.CENTER);
	    Label label = new Label(g1c1,SWT.NONE); sh.labelStyle1(label, "Freete&xt", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFreeText_ATS = new Text(g1c1,SWT.BORDER); 
		label = new Label(g1c1,SWT.NONE); sh.labelStyle1(label, "   F&rom", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFrom_ATS = new Text(g1c1,SWT.BORDER); 
		label = new Label(g1c1,SWT.NONE); sh.labelStyle1(label, "   T&o", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tTo_ATS = new Text(g1c1,SWT.BORDER); 
		label = new Label(g1c1,SWT.NONE); sh.labelStyle1(label, "   ", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		bIncoming_ATS = new Button(g1c1, SWT.RADIO); 
		bOutgoing_ATS = new Button(g1c1, SWT.RADIO); 
		label = new Label(g1c1,SWT.NONE); sh.labelStyle1(label, "   ", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Search = new Button(g1c1, SWT.PUSH);
		Clear = new Button(g1c1, SWT.PUSH);
		bs.SC(Search, Clear);
		
//		sh.textStyle(tFreeText_ATS, 200, 500, SWT.LEFT, SWT.LEFT, sh.upper, "Free Text Message", true);
//		sh.textStyle(tFrom_ATS, 110, 16, SWT.LEFT, SWT.LEFT, "", "Date/Time From (YYYY-MM-DD hh:mm)", true);
//		sh.textStyle(tTo_ATS, 110, 16, SWT.LEFT, SWT.LEFT, "", "Date/Time To (YYYY-MM-DD hh:mm)", true);
//		sh.buttonRCStyle(bIncoming_ATS, "&Incoming", "Incoming Message", 85);//, bs.heightBtn);
//		sh.buttonRCStyle(bOutgoing_ATS, "O&utgoing", "Outgoing Message", 85);//, bs.heightBtn);
		
		sh.text1(tFreeText_ATS, 200, 500, SWT.LEFT, SWT.CENTER, sh.upper, "Free Text Message", true);
		sh.text(tFrom_ATS, 16, SWT.CENTER, SWT.CENTER, "", "Date/Time From (YYYY-MM-DD hh:mm)", true);
		sh.text(tTo_ATS, 16, SWT.CENTER, SWT.CENTER, "", "Date/Time To (YYYY-MM-DD hh:mm)", true);
		sh.RadioCheckStyle(bIncoming_ATS, "&Incoming", "Incoming Message");
		sh.RadioCheckStyle(bOutgoing_ATS, "O&utgoing", "Outgoing Message");
		
		addListenerATS();
		tFreeText_ATS.setFocus();
		Composite g1c2 = new Composite(g1, SWT.NONE); sh.compositeStyle(g1c2, 1, SWT.LEFT, SWT.CENTER);
		label = new Label(g1c2, SWT.NONE); sh.labelStyle1(label, "Select the text box by clicking on it or press ALT+underscore character.", SWT.LEFT, SWT.CENTER, SWT.ITALIC, Colors.NORMAL);	
	}
	
	void g2(Group g2) {//Page Information Display
		ReadFromFile.readConfiguration();
		Height_MainForm = new Composite(g2, SWT.NONE); 
		sh.compositeStyle1(Height_MainForm, 1, ReadFromFile.getHeight_MainForm(), ReadFromFile.getWidth_MainForm());
		
		new DisplayMain(new String[]{"/tp/url.txt","/tp/uconfig.txt"});

		String org = DisplayMain.readInputFile("/tp/originator.txt");
		if (!org.isEmpty()) {
			String sarr[] = org.split("\n");
			org="";
			for (int i=0;i<sarr.length;i++) {
				if (!org.isEmpty()) org+="\n";
				org+=sarr[i];
			}
		}
		String prevst = DisplayMain.readInputFile("/tp/prevst.txt");
		if (!prevst.isEmpty()) {
			String sarr[] = prevst.split("\n");
			prevst="";
			for (int i=0;i<sarr.length;i++) {
				if (!prevst.isEmpty()) prevst+="\n";
				prevst+=sarr[i];
			}
		}
		String s_message = DisplayMain.readInputFile("/tp/message.txt");
		if (!s_message.isEmpty()) {
			String sarr[] = s_message.split("\n");
			s_message="";
			for (int i=0;i<sarr.length;i++) {
				if (!s_message.isEmpty()) s_message+="\n";
				s_message+=sarr[i];
			}
		}
	}
	
	void g3(Group cLeft) {
		String sbaud = DisplayMain.readInputFile("/tp/baudrate.txt");
		if (!sbaud.isEmpty()) {
			String sarr[] = sbaud.split("\n");
			sbaud="";
			for (int i=0;i<1;i++) {
				sbaud=sarr[i];
			}
		}
		
		String sdigit = DisplayMain.readInputFile("/tp/digit.txt");
		if (!sdigit.isEmpty()) {
			String sarr[] = sdigit.split("\n");
			sdigit="";
			for (int i=0;i<1;i++) {
				sdigit=sarr[i];
			}
		}
		
		String stransid = DisplayMain.readInputFile("/tp/transid.txt");
		if (!stransid.isEmpty()) {
			String sarr[] = stransid.split("\n");
			stransid="";
			for (int i=0;i<1;i++) {
				stransid=sarr[i];
			}
		}

		String stsec = DisplayMain.readInputFile("/tp/tseq.txt");
		if (!stsec.isEmpty()) {
			String sarr[] = stsec.split("\n");
			stsec="";
			for (int i=0;i<1;i++) {
				stsec=sarr[i];
			}
		}

		String srseq = DisplayMain.readInputFile("/tp/rseq.txt");
		if (!srseq.isEmpty()) {
			String sarr[] = srseq.split("\n");
			srseq="";
			for (int i=0;i<1;i++) {
				srseq=sarr[i];
			}
		}
		
		String ssvcmsg = DisplayMain.readInputFile("/tp/svcmsg.txt");
		if (!ssvcmsg.isEmpty()) {
			String sarr[] = ssvcmsg.split("\n");
			ssvcmsg="";
			for (int i=0;i<1;i++) {
				ssvcmsg=sarr[i];
			}
		}
		if (ssvcmsg.compareTo("1")==0) ssvcmsg="ON";
		else if (ssvcmsg.compareTo("0")==0) ssvcmsg="OFF";
		
		String scode1="";
		String scode = DisplayMain.readInputFile("/tp/databits.txt");
		if (!scode.isEmpty()) {
			String sarr[] = scode.split("\n");
			scode="";
			for (int i=0;i<1;i++) {
				scode=sarr[i];
			}
		}
		if (scode.compareTo("5")==0) scode1="ITA 2";
		else if (scode.compareTo("8")==0) scode1="IA 5";
		
		String sfree = DisplayMain.readInputFile("/tp/freespace.txt");
		if (!sfree.isEmpty()) {
			String sarr[] = sfree.split("\n");
			sfree="";
			for (int i=0;i<1;i++) {
				sfree=sarr[i];
			}
		}
		
		String snis = DisplayMain.readInputFile("/tp/nis.txt");
		if (!snis.isEmpty()) {
			String sarr[] = snis.split("\n");
			snis="";
			for (int i=0;i<1;i++) {
				snis=sarr[i];
			}
		}
		String strnis="";
		if (snis.compareTo("0")==0) { strnis="RS232"; }
		if (snis.compareTo("2")==0) { strnis="UDP"; }
		if (snis.compareTo("1")==0) { strnis="TCP"; }
		if (snis.compareTo("3")==0) { strnis="RAW"; }
		
		
		//1: Continuous printer
		//0: Standard printer
		String printerType = ReadFromFile.ReadInputFile1("/tp/stlp.txt");
		if (!printerType.isEmpty() && printerType.compareTo("1")==0) {
			printerType="Cont.";
			ReadFromFile.readConfiguration();
			if (ReadFromFile.getPrint().compareToIgnoreCase("yes")==0 || ReadFromFile.getPrint().compareToIgnoreCase("y")==0) {
//				itemQueue.setEnabled(true);
				clPQueue.setEnabled(true);
				clPQueue.setForeground(Colors.NORMAL);
				miQueue.setEnabled(true);
			}
		} else {
			printerType="Std.";
			ReadFromFile.readConfiguration();
			if (ReadFromFile.getPrint().compareToIgnoreCase("yes")==0 || ReadFromFile.getPrint().compareToIgnoreCase("y")==0) {
//				itemQueue.setEnabled(false);
				clPQueue.setEnabled(false);
				clPQueue.setForeground(Colors.DarkGrey);
				miQueue.setEnabled(false);
			}
		}
		
		Composite comp = new Composite(cLeft, SWT.NONE); sh.compositeStyle(comp, 16, SWT.LEFT, SWT.CENTER);
		clNis = new CLabel(comp, SWT.BORDER | SWT.CENTER); 
		clLine = new CLabel(comp, SWT.BORDER | SWT.CENTER); 
		clTseq = new CLabel(comp, SWT.BORDER | SWT.CENTER); 
		clQueue = new CLabel(comp, SWT.BORDER | SWT.CENTER); 
		clCID = new CLabel(comp, SWT.BORDER | SWT.CENTER); 
		clDigit = new CLabel(comp, SWT.BORDER | SWT.CENTER); 
		clLocalIP = new CLabel(comp, SWT.BORDER | SWT.CENTER); 
		bRefresh = new Button(comp, SWT.PUSH); 
		sh.buttonStyle(bRefresh, "", "", bs.widthNavBtn-15, bs.widthNavBtn-20, Colors.DarkGrey, SWT.CENTER, SWT.CENTER, Images.img_refresh16);
		bRefresh.setToolTipText("Refresh network connection");
		clDestIP = new CLabel(comp, SWT.BORDER | SWT.CENTER); 
		clFree = new CLabel(comp, SWT.BORDER | SWT.CENTER); 
		clTimer = new CLabel(comp, SWT.BORDER | SWT.CENTER); 
		
		clBaudrate = new CLabel(comp, SWT.BORDER | SWT.CENTER);  
		clPrinterType = new CLabel(comp, SWT.BORDER | SWT.CENTER);
		clWarning = new CLabel(comp, SWT.BORDER | SWT.CENTER);
		Button bReboot = new Button(comp, SWT.PUSH); sh.buttonStyle(bReboot, "", "Reboot", bs.widthNavBtn, Colors.DarkGrey, SWT.CENTER, SWT.CENTER, Images.img_reboot16);
		Button bShutdown = new Button(comp, SWT.PUSH); sh.buttonStyle(bShutdown, "", "Shutdown", bs.widthNavBtn, Colors.DarkGrey, SWT.CENTER, SWT.CENTER, Images.img_shutdown16);

		sh.clabelStyle(clNis, strnis, ixNis, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		sh.clabelStyle(clLine, "Line : ", ixLine, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
		sh.clabelStyle(clTseq, "Tseq : "+stsec, ixTseq, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
		Integer sQ=query(TableOutgoing.queryQueue);
		sh.clabelStyle(clQueue, "Queue : "+sQ, ixQueue, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		if (sQ>=1) MainForm.clQueue.setForeground(Colors.red);
		else MainForm.clQueue.setForeground(Colors.NORMAL);
		sh.clabelStyle(clCID, "CID : "+stransid, ixtransid, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		sh.clabelStyle(clDigit, "Digit seq : "+sdigit, ixDigit, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		sh.clabelStyle(clLocalIP, strLocalIP(), ixIP, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		sh.clabelStyle(clDestIP, strDestIP(), ixIP, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		sh.clabelStyle(clFree, "Free : "+sfree, ixfree, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		sh.clabelStyle(clTimer, "", ixTime, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		
		sh.clabelStyle(clWarning, "Incm wrng msg : "+query(queryWarning), ixWarning, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		if (strnis.compareTo("RS232")!=0) sbaud="-";
		else bRefresh.setEnabled(false);
		sh.clabelStyle(clBaudrate, "Baudrate : "+sbaud, ixBaudrate, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		if (ReadFromFile.getPrint().compareToIgnoreCase("yes")==0 || ReadFromFile.getPrint().compareToIgnoreCase("y")==0) {
			sh.clabelStyle(clPrinterType, printerType, ixPrinterType, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, Images.img_printer16); } 
		else if (ReadFromFile.getPrint().compareToIgnoreCase("no")==0 || ReadFromFile.getPrint().compareToIgnoreCase("n")==0) {
			sh.clabelStyle(clPrinterType, "", ixPrinterType, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null); }
			
		//Composite compTop = new Composite(cLeft, SWT.NONE); sh.compositeStyle(compTop, 8, SWT.LEFT, SWT.CENTER);
		//Composite compBot = new Composite(cLeft, SWT.NONE); sh.compositeStyle(compBot, 12, SWT.LEFT, SWT.CENTER);
		
		/*clLine = new CLabel(compTop, SWT.BORDER); 
		clTseq = new CLabel(compTop, SWT.BORDER); 
		clRseq = new CLabel(compTop, SWT.BORDER); 
		clNis = new CLabel(compTop, SWT.BORDER); 
		clQueue = new CLabel(compTop, SWT.BORDER); 
		clRefuse = new CLabel(compTop, SWT.BORDER); 
		clLocalIP = new CLabel(compTop, SWT.BORDER); 
		clDestIP = new CLabel(compTop, SWT.BORDER); 
		
		clBaudrate = new CLabel(compBot, SWT.BORDER);  
		clCode = new CLabel(compBot, SWT.BORDER); 
		clCID = new CLabel(compBot, SWT.BORDER); 
		clDigit = new CLabel(compBot, SWT.BORDER); 
		clFree = new CLabel(compBot, SWT.BORDER); 
		clPrinterType = new CLabel(compBot, SWT.BORDER);
		clSvcMsgGen = new CLabel(compBot, SWT.BORDER); 
		clWarning = new CLabel(compBot, SWT.BORDER);
		clTimer = new CLabel(compBot, SWT.BORDER | SWT.CENTER); 
		Button bReboot = new Button(compBot, SWT.PUSH); sh.buttonStyle(bReboot, "", "Reboot", bs.widthNavBtn, Colors.DarkGrey, SWT.CENTER, SWT.CENTER, Images.img_reboot16);
		Button bShutdown = new Button(compBot, SWT.PUSH); sh.buttonStyle(bShutdown, "", "Shutdown", bs.widthNavBtn, Colors.DarkGrey, SWT.CENTER, SWT.CENTER, Images.img_shutdown16);

		sh.clabelStyle(clLine, "Line : ", ixLine, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
		sh.clabelStyle(clTseq, "Tseq : "+stsec, ixTseq, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
		sh.clabelStyle(clRseq, "Rseq : "+srseq, ixRseq, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
		sh.clabelStyle(clNis, strnis, ixNis, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
		Integer sQ=query(TableOutgoing.queryQueue);
		sh.clabelStyle(clQueue, "Queue : "+sQ, ixQueue, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
		if (sQ>=1) MainForm.clQueue.setForeground(Colors.red);
		else MainForm.clQueue.setForeground(Colors.BLUE);
		sh.clabelStyle(clRefuse, "", ixRefuse, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.red, null);
		sh.clabelStyle(clLocalIP, strLocalIP(), ixIP, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
		sh.clabelStyle(clDestIP, strDestIP(), ixIP, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.BLUE, null);
		
		sh.clabelStyle(clWarning, "Incm wrng msg : "+query(queryWarning), ixWarning, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		sh.clabelStyle(clFree, "Free : "+sfree, ixfree, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		sh.clabelStyle(clBaudrate, "Baudrate : "+sbaud, ixBaudrate, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		sh.clabelStyle(clCode, "Code : "+scode1, ixCode, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		sh.clabelStyle(clSvcMsgGen, "Svc msg gen : "+ssvcmsg, ixSvcMsgGen, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		sh.clabelStyle(clCID, "CID : "+stransid, ixtransid, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		sh.clabelStyle(clDigit, "Digit seq : "+sdigit, ixDigit, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		sh.clabelStyle(clTimer, "", ixTime, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null);
		if (ReadFromFile.getPrint().compareToIgnoreCase("yes")==0 || ReadFromFile.getPrint().compareToIgnoreCase("y")==0) {
			sh.clabelStyle(clPrinterType, printerType, ixPrinterType, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, Images.img_printer16); } 
		else if (ReadFromFile.getPrint().compareToIgnoreCase("no")==0 || ReadFromFile.getPrint().compareToIgnoreCase("n")==0) {
			sh.clabelStyle(clPrinterType, "", ixPrinterType, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL, null); }*/
				
		bRefresh.addSelectionListener(new SelectionAdapter() {
    		public void widgetSelected(SelectionEvent e) {
				try{
					String s="service networking restart";
					Runtime.getRuntime().exec(s);
				}
				catch(IOException er){
					System.err.println(er.getMessage());
				}
    		}
    	});

		bReboot.addSelectionListener(new SelectionAdapter() {
    		public void widgetSelected(SelectionEvent e) {
    			RefreshTable.reboot();
    		}
    	});

    	bShutdown.addSelectionListener(new SelectionAdapter() {
    		public void widgetSelected(SelectionEvent e) {
    			RefreshTable.shutdown();
    		}
    	});
	}
	
	public static String strLocalIP() {
		String iplocal = DisplayMain.readInputFile("/tp/iplocal.txt");
		if (iplocal.contains("\n")) iplocal=iplocal.replace("\n", "");
		if (!iplocal.isEmpty()) {
			String sarr[] = iplocal.split("\n");
			iplocal="";
			for (int i=0;i<1;i++) {
				iplocal=sarr[i];
			}
		} else iplocal="Not available";
		
		String portg = DisplayMain.readInputFile("/tp/portg.txt");
		if (!portg.isEmpty()) {
			String sarr[] = portg.split("\n");
			portg="";
			for (int i=0;i<1;i++) {
				portg=sarr[i];
			}
		}
		
		String strIP="";
		String snis = DisplayMain.readInputFile("/tp/nis.txt");
		if (!snis.isEmpty()) {
			String sarr[] = snis.split("\n");
			snis="";
			for (int i=0;i<1;i++) {
				snis=sarr[i];
			}
		}
		
		if (snis.compareTo("1")==0 || snis.compareTo("2")==0)
			strIP="Local : "+iplocal+":"+portg;
		else if (snis.compareTo("3")==0)
			strIP="Local : "+iplocal;
		else strIP="";
		
		return strIP;
	}
	
	public static String strDestIP() {
		String adrg = DisplayMain.readInputFile("/tp/adrg.txt");
		if (!adrg.isEmpty()) {
			String sarr[] = adrg.split("\n");
			adrg="";
			for (int i=0;i<1;i++) {
				adrg=sarr[i];
			}
		}
		String portg1 = DisplayMain.readInputFile("/tp/portg1.txt");
		if (!portg1.isEmpty()) {
			String sarr[] = portg1.split("\n");
			portg1="";
			for (int i=0;i<1;i++) {
				portg1=sarr[i];
			}
		}
		
		String strIP="";
		String snis = DisplayMain.readInputFile("/tp/nis.txt");
		if (!snis.isEmpty()) {
			String sarr[] = snis.split("\n");
			snis="";
			for (int i=0;i<1;i++) {
				snis=sarr[i];
			}
		}
		
		if (snis.compareTo("1")==0 || snis.compareTo("2")==0)
			strIP="Destination : "+adrg+":"+portg1;
		else if (snis.compareTo("3")==0)
			strIP="Destination : "+adrg;
		else strIP="";
		
		return strIP;
	}
	
	void addListenerATS() {
		final Calendar calFrom = Calendar.getInstance();
		cAts.tgl();
		tFrom_ATS.setText(cAts.YYYY+"-"+cAts.MM+"-00 00:00");
		tFrom_ATS.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; }//* [Y]YYY *
							case 1: { buffer.append('0'); break; }//* Y[Y]YY *
							case 2: { buffer.append('0'); break; }//* YY[Y]Y *
							case 3: { buffer.append('0'); break; }//* YYY[Y] *  { buffer.append('Y'); break; }
							case 4: { buffer.append('-'); break; }//* YYYY[-]MM * 
							case 5: { buffer.append('0'); break; }//* [M]M *
							case 6: { buffer.append('0'); break; }//* M[M] * { buffer.append('M'); break; }
							case 7: { buffer.append('-'); break; }//* MM[-]DD * 
							case 8: { buffer.append('0'); break; }//* [D]D *
							case 9: { buffer.append('0'); break; }//* D[D] *  { buffer.append('D'); break; }
							case 10: { buffer.append(' '); break; }//*DD[ ]hh* 
							case 11: { buffer.append('0'); break; }//* [h]h *
							case 12: { buffer.append('0'); break; }//* h[h] *  { buffer.append('h'); break; }
							case 13: { buffer.append(':'); break; }//*hh[:]mm* 
							case 14: { buffer.append('0'); break; }//* [m]m *
							case 15: { buffer.append('0'); break; }//* m[m] *  { buffer.append('m'); break; }
							default: return;
						}
					}
					tFrom_ATS.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tFrom_ATS.insert(buffer.toString());
					ignore = false;
					tFrom_ATS.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 15) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (start + index == 4 || start + index == 7) {
						if (chars[i] == '-') { index++; continue; }
						buffer.insert(index++, '-');
					}
					if (start + index == 10) {
						if (chars[i] == ' ') { index++; continue; }
						buffer.insert(index++, ' ');
					}
					if (start + index == 13) {
						if (chars[i] == ':') { index++; continue; }
						buffer.insert(index++, ':');
					}
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 5 &&  '1' < chars[i]) return; //* [M]M *
					if (start + index == 8 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 11 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 14 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tFrom_ATS.getText());
				date.replace(e.start, e.start + length, newText);

				calFrom.set(Calendar.YEAR, 1901);
				calFrom.set(Calendar.MONTH, Calendar.JANUARY);
				calFrom.set(Calendar.DATE, 1);
				calFrom.set(Calendar.HOUR_OF_DAY, 00);
				calFrom.set(Calendar.MINUTE, 00);
				
				String yyyy = date.substring(0, 4);
				if (yyyy.indexOf('0') == -1) {
					int year = Integer.parseInt(yyyy);
					calFrom.set(Calendar.YEAR, year);
				}
				String MM = date.substring(5, 7);
				if (MM.indexOf('0') == -1) {
					int month =  Integer.parseInt(MM) - 1;
					int maxMonth = calFrom.getActualMaximum(Calendar.MONTH);
					if (0 > month || month > maxMonth) return;
					calFrom.set(Calendar.MONTH, month);
				}
				String dd = date.substring(8,10);
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = calFrom.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					calFrom.set(Calendar.DATE, day);
				} 
				String hh = date.substring(11,13);
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = calFrom.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					calFrom.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(14,16);
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = calFrom.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					calFrom.set(Calendar.MINUTE, minute);
				}
				else {
					if (calFrom.get(Calendar.MONTH)  == Calendar.FEBRUARY) {
						char firstChar = date.charAt(8);
						if (firstChar != '0' && '2' < firstChar) return;
					}
				}
				tFrom_ATS.setSelection(e.start, e.start + length);
				ignore = true;
				tFrom_ATS.insert(newText);
				ignore = false;
			}
		});
		
		final Calendar calTo = Calendar.getInstance();
		tTo_ATS.setText("0000-00-00 00:00");//YYYY-MM-DD hh:mm
		tTo_ATS.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
						case 0: { buffer.append('0'); break; }//* [Y]YYY *
						case 1: { buffer.append('0'); break; }//* Y[Y]YY *
						case 2: { buffer.append('0'); break; }//* YY[Y]Y *
						case 3: { buffer.append('0'); break; }//* YYY[Y] *  { buffer.append('Y'); break; }
						case 4: { buffer.append('-'); break; }//* YYYY[-]MM * 
						case 5: { buffer.append('0'); break; }//* [M]M *
						case 6: { buffer.append('0'); break; }//* M[M] * { buffer.append('M'); break; }
						case 7: { buffer.append('-'); break; }//* MM[-]DD * 
						case 8: { buffer.append('0'); break; }//* [D]D *
						case 9: { buffer.append('0'); break; }//* D[D] *  { buffer.append('D'); break; }
						case 10: { buffer.append(' '); break; }//*DD[ ]hh* 
						case 11: { buffer.append('0'); break; }//* [h]h *
						case 12: { buffer.append('0'); break; }//* h[h] *  { buffer.append('h'); break; }
						case 13: { buffer.append(':'); break; }//*hh[:]mm* 
						case 14: { buffer.append('0'); break; }//* [m]m *
						case 15: { buffer.append('0'); break; }//* m[m] *  { buffer.append('m'); break; }
						default: return;
						}
					}
					tTo_ATS.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tTo_ATS.insert(buffer.toString());
					ignore = false;
					tTo_ATS.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 15) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (start + index == 4 || start + index == 7) {
						if (chars[i] == '-') { index++; continue; }
						buffer.insert(index++, '-');
					}
					if (start + index == 10) {
						if (chars[i] == ' ') { index++; continue; }
						buffer.insert(index++, ' ');
					}
					if (start + index == 13) {
						if (chars[i] == ':') { index++; continue; }
						buffer.insert(index++, ':');
					}
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 5 &&  '1' < chars[i]) return; //* [M]M *
					if (start + index == 8 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 11 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 14 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tTo_ATS.getText());
				date.replace(e.start, e.start + length, newText);

				calTo.set(Calendar.YEAR, 1901);
				calTo.set(Calendar.MONTH, Calendar.JANUARY);
				calTo.set(Calendar.DATE, 1);
				calTo.set(Calendar.HOUR_OF_DAY, 00);
				calTo.set(Calendar.MINUTE, 00);
				
				String yyyy = date.substring(0, 4);
				if (yyyy.indexOf('0') == -1) {
					int year = Integer.parseInt(yyyy);
					calTo.set(Calendar.YEAR, year);
				}
				String MM = date.substring(5, 7);
				if (MM.indexOf('0') == -1) {
					int month =  Integer.parseInt(MM) - 1;
					int maxMonth = calTo.getActualMaximum(Calendar.MONTH);
					if (0 > month || month > maxMonth) return;
					calTo.set(Calendar.MONTH, month);
				}
				String dd = date.substring(8,10);
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = calTo.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					calTo.set(Calendar.DATE, day);
				} 
				String hh = date.substring(11,13);
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = calTo.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					calTo.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(14,16);
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = calTo.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					calTo.set(Calendar.MINUTE, minute);
				}
				else {
					if (calTo.get(Calendar.MONTH)  == Calendar.FEBRUARY) {
						char firstChar = date.charAt(8);
						if (firstChar != '0' && '2' < firstChar) return;
					}
				}
				tTo_ATS.setSelection(e.start, e.start + length);
				ignore = true;
				tTo_ATS.insert(newText);
				ignore = false;
			}
		});
	
		tFreeText_ATS.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) { searchATS(); } }
	    });
		
		tFrom_ATS.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) { searchATS(); } }
	    });
		
		tTo_ATS.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) { searchATS(); } }
	    });
		
		Search.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {	
				searchATS();
			}
		});
		
		Clear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {	
				cAts.tgl();
				tFrom_ATS.setText(cAts.YYYY+"-"+cAts.MM+"-00 00:00");
				if (!tTo_ATS.getText().equals("0000-00-00 00:00")) tTo_ATS.setText("0000-00-00 00:00");
				if (tFreeText_ATS.getText() != null) tFreeText_ATS.setText("");
				bIncoming_ATS.setSelection(false);
				bOutgoing_ATS.setSelection(false);
			}
		});
	}
	
	void searchATS() {
		String freetext = tFreeText_ATS.getText().toString();
		String tgl = tFrom_ATS.getText().toString();
		String tglTo = tTo_ATS.getText().toString();
		String io="";
		if (bIncoming_ATS.getSelection()==true) io="0";
		else if (bOutgoing_ATS.getSelection()==true) io="1";
		tFreeText_ATS.setFocus();
		RefreshTable.refreshTableAFTN(tgl, tglTo, "", "", "", freetext, "", io);
	}
	
	void searchATS(String freetext) {
		freetext+=tFreeText_ATS.getText().toString();
		String tgl = tFrom_ATS.getText().toString();
		String tglTo = tTo_ATS.getText().toString();
		String io="";
		if (bIncoming_ATS.getSelection()==true) io="0";
		else if (bOutgoing_ATS.getSelection()==true) io="1";
		tFreeText_ATS.setFocus();
		RefreshTable.refreshTableAFTN(tgl, tglTo, "", "", "", freetext, "", io);
	}

	void addMenuListeners() {
		//AFTN Messages
		miSrchAFTN.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				cAts.tgl(); RefreshTable.refreshTableAFTN(cAts.YYYY+"-"+cAts.MM+"-00 00:00", "0000-00-00 00:00", "", "", "", "", "", ""); } });	
		
		if (ReadFromFile.ReadInputFile1("/tp/template/freeamo.txt").compareTo("1")==0) {
			miAMO.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewAMO("NewAMO",""); } });
			
			miFREE.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewFREE("NewFREE",""); } });
		}
		
		miaftn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewAFTN("NewAFTN",""); } });
		
		mialr.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewALR("NewALR",""); } });
		
		mircf.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewRCF("NewRCF",""); } });
		
		mifpl.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewFPL("NewFPL",""); } });
		
		midla.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewDLA("NewDLA",""); } });
		
		michg.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewCHG("NewCHG",""); } });
		
		micnl.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewCNL("NewCNL",""); } });
		
		midep.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewDEP("NewDEP",""); } });
		
		miarr.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewARR("NewARR",""); } });
		
		micdn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewCDN("NewCDN",""); } });
		
		micpl.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewCPL("NewCPL",""); } });
		
		miest.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewEST("NewEST",""); } });
		
		miacp.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewACP("NewACP",""); } });
		
		milam.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewLAM("NewLAM",""); } });
		
		mirqp.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewRQP("NewRQP",""); } });
		
		mirqs.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewRQS("NewRQS",""); } });
		
		mispl.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshNewSPL("NewSPL",""); } });
	    
		miTest.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.TestMsgAction(); } });
	
		//Maintenance
		miRoute.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshTableRoute("", ""); } });

		miType9b.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshTableType9b("", ""); } });
	    
		miReg.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshTableReg("", ""); } });
	    
		miLocind.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshTableLocInd("", ""); } });
	    
		miCommunication.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.CommSetupAction(); } });
		
		ReadFromFile.readConfiguration();
		if (ReadFromFile.getPrint().compareToIgnoreCase("yes")==0 || ReadFromFile.getPrint().compareToIgnoreCase("y")==0) { 
			miPrinter.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) { RefreshTable.refreshPrinter(); } }); 
		}
		
		miPDFSetting.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshPDFSetting(); } });
		
		//View
		miAbbr.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshTableAbbr("", ""); } });
	    
		miWarning.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshTableWarning("","", "1111-11-11 11:11", "1111-11-11 11:11"); } });
		
		ReadFromFile.readConfiguration();
		if (ReadFromFile.getPrint().compareToIgnoreCase("yes")==0 || ReadFromFile.getPrint().compareToIgnoreCase("y")==0) {
			miQueue.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) { cAts.tgl(); RefreshTable.refreshTableQueue("", "1111-11-11 11:11", "1111-11-11 11:11"); } });
		}
		
		//Status
		miOutgoing.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.refreshTableOutbox(""); } });
		
		//Window
		miWindow.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { Close.closeWindows(); } });	
		
	}
	
	void addListeners(final Shell shell) {
		
		clNis.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) { }
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { RefreshTable.CommSetupAction(); }
		});
		clBaudrate.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) { }
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { RefreshTable.CommSetupAction(); }
		});
		clDigit.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) { }
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { RefreshTable.CommSetupAction(); }
		});
		clCID.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) { }
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { RefreshTable.CommSetupAction(); }
		});
		clTseq.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) { }
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { RefreshTable.CommSetupAction(); }
		});
		/*clCode.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) { }
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { RefreshTable.CommSetupAction(); }
		});
		clSvcMsgGen.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) { }
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { RefreshTable.CommSetupAction(); }
		});*/
		
		clQueue.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) { }
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { RefreshTable.refreshTableOutbox(""); }
		});
		
		clLocalIP.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) { }
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) {
				try{
					String s="nm-connection-editor";
					Runtime.getRuntime().exec(s);
				}
				catch(IOException er){
					System.err.println(er.getMessage());
				}
			}
		});

		clDestIP.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) { }
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { RefreshTable.CommSetupAction(); }
		});
		
		clWarning.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) { }
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { RefreshTable.refreshTableWarning("","", "1111-11-11 11:11", "1111-11-11 11:11"); }
		});
		
		ReadFromFile.readConfiguration();
		if (ReadFromFile.getPrint().compareToIgnoreCase("yes")==0 || ReadFromFile.getPrint().compareToIgnoreCase("y")==0) {
			clPrinterType.addMouseListener(new MouseListener() {
				public void mouseDown(MouseEvent e) { }
				public void mouseUp(MouseEvent e) { }
				public void mouseDoubleClick(MouseEvent e) { RefreshTable.refreshPrinter(); }
			}); }
		
		clTimer.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) { }
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { RefreshTable.refreshCalendar(); }
		});
		
		// event for prevent a shell from closing (prompt the user) 
		// such as Alt+F4 / close toolbox in the application
		shell.addListener (SWT.Close, new Listener () {
			@Override
			public void handleEvent (Event event) {
				rerun();
			}
		});
	}
	
	public static void rerun() {
//		String run = ReadFromFile.ReadInputFile2("/tp/runteleprintertemplate");
////		System.out.println("run:" + run + "#");
//		if (run.isEmpty()) run = "/tp/runteleprintertemplate";
//		else if (!run.isEmpty() && run.contains("\n")) {
//			String sub[] = run.split("\n");
//			for (int i=0; i<sub.length; i++) {
//				System.out.println("cmd"+i+":" + sub[i]+ "#");
//				try {
//					Runtime.getRuntime().exec(sub[i]);
//				} catch (IOException ioe) {
//					System.err.println("Runtime info:" + ioe.getMessage());
//				}
//			}
			System.exit(0);	
//		}
	}
	
	ToolItem setToolBarItem(CoolBar coolBar, ToolItem item, String str, String tooltip, Image img) {
		final CoolItem CoolItem = new CoolItem(coolBar, SWT.NONE);
        final ToolBar ToolBar = new ToolBar(coolBar,SWT.HORIZONTAL);
        item = new ToolItem(ToolBar, SWT.PUSH | SWT.BORDER); sh.toolitemStyle(item, str, tooltip, img);
		ToolBar.pack(); 
        Point size = ToolBar.getSize();
        CoolItem.setControl(ToolBar);
        CoolItem.setSize(CoolItem.computeSize(size.x, size.y));
        return item;
	}
	
	private Control createCLabel(Composite composite) {
		Composite c = new Composite(composite, SWT.NONE);
		c.setLayout(new FillLayout());
		clPQueue = new CLabel(c, SWT.BORDER | SWT.CENTER);
		clPQueue.setText("Print Queue \n"+query(queryQueuelp));
		clPQueue.setImage(Images.img_printer32);
		clPQueue.setFont(Shorten.getFont());
		clPQueue.setForeground(Colors.NORMAL);
		return c;
	}
	
	private void calcSize(CoolItem item) {
		Control control = item.getControl();
		Point pt = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		pt = item.computeSize(pt.x, pt.y);
		item.setSize(pt);
	}
	
	void coolbar1(Shell shell) {
		final CoolBar coolBar = new CoolBar(shell, SWT.NONE);
        coolBar.setSize(395,70);
        coolBar.setLocation(0,0);
        coolBar.setLocked(true);
    	
        itemAFTN = setToolBarItem(coolBar, itemAFTN, "Free", "Search Free Text Messages", Images.img_search32);
        itemFPL = setToolBarItem(coolBar, itemFPL, "FPL", "Search FPL Messages", Images.img_search32);
        itemDLA = setToolBarItem(coolBar, itemDLA, "DLA", "Search DLA Messages", Images.img_search32);
        itemCHG = setToolBarItem(coolBar, itemCHG, "CHG", "Search CHG Messages", Images.img_search32);
        itemCNL = setToolBarItem(coolBar, itemCNL, "CNL", "Search CNL Messages", Images.img_search32);
        itemDEP = setToolBarItem(coolBar, itemDEP, "DEP", "Search DEP Messages", Images.img_search32);
        itemARR = setToolBarItem(coolBar, itemARR, "ARR", "Search ARR Messages", Images.img_search32);
        
        itemROUTE = setToolBarItem(coolBar, itemROUTE, "Rout&e", "Search Route", Images.img_search32);
        itemACFT = setToolBarItem(coolBar, itemACFT, "T&ype", "Search Type of Aircraft", Images.img_search32);
        itemREG = setToolBarItem(coolBar, itemREG, "RE&G/", "Search REG/", Images.img_search32);
        
        itemTestMsg = setToolBarItem(coolBar, itemTestMsg, "&Test && SVC TRAF", "Test Message & SVC TRAF", Images.img_testmsg32);
        itemCommSetup = setToolBarItem(coolBar, itemCommSetup, "&Comm. Setup", "Communication Setup", Images.img_setting32);
		itemBell = setToolBarItem(coolBar, itemBell, "", "ON/OFF", Images.img_sound32);
		
		ReadFromFile.readConfiguration();
		if (ReadFromFile.getPrint().compareToIgnoreCase("yes")==0 || ReadFromFile.getPrint().compareToIgnoreCase("y")==0) { 
//			itemQueue = setToolBarItem(coolBar, itemQueue, "Print &Queue:"+query(queryQueuelp)+"      ", TableQueueLP.getText(), Images.img_printer32);
//			itemQueue.addSelectionListener(new SelectionAdapter() {
//	        	public void widgetSelected(SelectionEvent e) {
//	        		cAts.tgl(); RefreshTable.refreshTableQueue("", "1111-11-11 11:11", "1111-11-11 11:11"); } });

			// Create CLabel coolitem
			CoolItem item2 = new CoolItem(coolBar, SWT.NONE);
			item2.setControl(createCLabel(coolBar));
			calcSize(item2);
			clPQueue.addMouseListener(new MouseListener() {
				public void mouseDown(MouseEvent e) { }
				public void mouseUp(MouseEvent e) { }
				public void mouseDoubleClick(MouseEvent e) { cAts.tgl(); RefreshTable.refreshTableQueue("", "1111-11-11 11:11", "1111-11-11 11:11"); }
			});
		}

        itemAFTN.addSelectionListener(new SelectionAdapter() {
    		public void widgetSelected(SelectionEvent e) { 
    			searchATS(""); 
    		} 
    	});
    	
    	itemFPL.addSelectionListener(new SelectionAdapter() {
    		public void widgetSelected(SelectionEvent e) {
				String sfind="FPL";
    			if (!tFreeText_ATS.getText().isEmpty()) {
    				sfind+=",";
    			}
    			searchATS(sfind); 
    		} 
    	});

    	itemDLA.addSelectionListener(new SelectionAdapter() {
    		public void widgetSelected(SelectionEvent e) { 
				String sfind="DLA";
    			if (!tFreeText_ATS.getText().isEmpty()) {
    				sfind+=",";
    			}
    			searchATS(sfind); 
    		} 
    	});

    	itemCHG.addSelectionListener(new SelectionAdapter() {
    		public void widgetSelected(SelectionEvent e) { 
				String sfind="CHG";
    			if (!tFreeText_ATS.getText().isEmpty()) {
    				sfind+=",";
    			}
    			searchATS(sfind); 
    		} 
    	});

    	itemCNL.addSelectionListener(new SelectionAdapter() {
    		public void widgetSelected(SelectionEvent e) { 
				String sfind="CNL";
    			if (!tFreeText_ATS.getText().isEmpty()) {
    				sfind+=",";
    			}
    			searchATS(sfind); 
    		} 
    	});

    	itemDEP.addSelectionListener(new SelectionAdapter() {
    		public void widgetSelected(SelectionEvent e) { 
				String sfind="DEP";
    			if (!tFreeText_ATS.getText().isEmpty()) {
    				sfind+=",";
    			}
    			searchATS(sfind); 
    		} 
    	});

    	itemARR.addSelectionListener(new SelectionAdapter() {
    		public void widgetSelected(SelectionEvent e) { 
				String sfind="ARR";
    			if (!tFreeText_ATS.getText().isEmpty()) {
    				sfind+=",";
    			}
    			searchATS(sfind); 
    		} 
    	});

    	itemROUTE.addSelectionListener(new SelectionAdapter() {
    		public void widgetSelected(SelectionEvent e) { RefreshTable.refreshTableRoute("", ""); } });

    	itemACFT.addSelectionListener(new SelectionAdapter() {
    		public void widgetSelected(SelectionEvent e) { RefreshTable.refreshTableType9b("", ""); } });
            
    	itemREG.addSelectionListener(new SelectionAdapter() {
    		public void widgetSelected(SelectionEvent e) { RefreshTable.refreshTableReg("", ""); } });
    	
		itemTestMsg.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.TestMsgAction(); } });
		
		itemCommSetup.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { RefreshTable.CommSetupAction(); } });
		
		String s2="/tp/bell1.txt";
		WriteToAFile wr = new WriteToAFile();
		wr.open(s2);
		wr.write("1");
		wr.close();
		itemBell.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String s1="/tp/bell.txt"; //SS
				String s2="/tp/bell1.txt"; //incoming
				File f = new File(s1);
				if (!f.exists()) { 
					if (itemBell.getImage()==Images.img_nosound32) {
						File f1 = new File(s1);
						boolean b = f1.delete();
						System.err.println("delete:"+s1+" res:"+b);
						
						itemBell.setImage(Images.img_sound32);
						//itemBell.setText("O&N");
						itemBell.setToolTipText("O&N");
						WriteToAFile wr = new WriteToAFile();
						wr.open(s2);
						wr.write("1");
						wr.close();
					} else {
						File f2 = new File(s2);
						boolean b2 = f2.delete();
						System.err.println("delete1:"+s2+" res:"+b2);
						
						itemBell.setImage(Images.img_nosound32);
						//itemBell.setText("O&FF");
						itemBell.setToolTipText("O&FF");
					}
				} else { //Priority=SS (bell.txt)
					
					if (itemBell.getImage()==Images.img_nosound32) {
						itemBell.setImage(Images.img_sound32);
						//itemBell.setText("O&N");
						itemBell.setToolTipText("O&N");
						
						WriteToAFile wr = new WriteToAFile();
						wr.open(s2);
						wr.write("1");
						wr.close();
					} else {
						File f1 = new File(s1);
						boolean b = f1.delete();
						System.err.println("delete2:"+s1+" res:"+b);
						
						File f2 = new File(s2);
						boolean b2 = f2.delete();
						System.err.println("delete3:"+s2+" res:"+b2);
						
						itemBell.setImage(Images.img_nosound32);
						//itemBell.setText("O&FF");
						itemBell.setToolTipText("O&FF");

					}
				}
			}
		});
	}
	
	public static int query(String strQuery) {
		Connection conn = null; // connection object
	    Statement stmt = null; // statement object
	    ResultSet rs = null; // result set object
	    int jumlah=0;
	    
		try { conn = databases.jdbc.getDBConnection(); } 
		catch (Exception e) { e.printStackTrace(); }
			
		System.out.println("\nQuery: " + strQuery);
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strQuery);
			rs.last();
			jumlah = rs.getRow();
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jumlah;						
	}
	
	MenuItem setSeparator(MenuItem mi, Menu menu) {
		mi = new MenuItem(menu, SWT.SEPARATOR);
	    return mi;
	}
	
	MenuItem setMenuItem(MenuItem mi, Menu menu, String str, int iAccelerator) {
		mi = new MenuItem(menu, SWT.PUSH);
	    mi.setText(str);
	    mi.setAccelerator(iAccelerator);
	    return mi;
	}
	
	MenuItem setMenuItemHeader(MenuItem mi, Menu mn, String str, Menu menu, Shell shl) {
		mi = new MenuItem(mn, SWT.CASCADE);
	    mi.setText(str);
	    return mi;
	}
	
	Menu setMenu(Menu menu, MenuItem mi, Shell shl) {
		menu = new Menu(shl, SWT.DROP_DOWN);
	    mi.setMenu(menu);
	    return menu;
	}
	
	void menubar(Shell shell) {
		menuBar = new Menu(shell, SWT.BAR);
		
	    miAFTNMsg = setMenuItemHeader(miAFTNMsg, menuBar, "&AFTN Messages", mnAFTNMsg, shell);
	    mnAFTNMsg = setMenu(mnAFTNMsg, miAFTNMsg, shell);
	    miNewAFTN = setMenuItemHeader(miNewAFTN, mnAFTNMsg, "&New AFTN Message", mnNewATS, shell);
	    mnNewATS = setMenu(mnNewATS, miNewAFTN, shell);
	    miMaintenance = setMenuItemHeader(miMaintenance, menuBar, "&Maintenance", mnMaintenance, shell);
	    mnMaintenance = setMenu(mnMaintenance, miMaintenance, shell);
	    miView = setMenuItemHeader(miView, menuBar, "&View", mnView, shell);
	    mnView = setMenu(mnView, miView, shell);
	    miStatus = setMenuItemHeader(miStatus, menuBar, "&Status", mnStatus, shell);
	    mnStatus = setMenu(mnStatus, miStatus, shell);
	    miCloseWindow = setMenuItemHeader(miCloseWindow, menuBar, "&Window", mnWindow, shell);
	    mnWindow = setMenu(mnWindow, miCloseWindow, shell);
	    
	    //MENU: SEARCH AFTN MESSAGE
	    miSrchAFTN = setMenuItem(miSrchAFTN, mnAFTNMsg, "&Search AFTN Messages\tALT+SHIFT+A", SWT.SHIFT+SWT.ALT+'A');
	    //MENU: NEW AFTN MESSAGE
	    if (ReadFromFile.ReadInputFile1("/tp/template/freeamo.txt").compareTo("1")==0) {
	    	miAMO = setMenuItem(miAMO, mnNewATS, "AMO\tCTRL+X", SWT.CTRL+'X');
		    mi = setSeparator(mi, mnNewATS);
		    miFREE = setMenuItem(miFREE, mnNewATS, "Free Text\tCTRL+Y", SWT.CTRL+'Y');
		    mi = setSeparator(mi, mnNewATS);
	    }
	    miaftn = setMenuItem(miaftn, mnNewATS, "AFTN Free Text\tCTRL+A", SWT.CTRL+'A');
	    mi = setSeparator(mi, mnNewATS);
	    mialr = setMenuItem(mialr, mnNewATS, "ALR\tCTRL+B", SWT.CTRL+'B');
	    mircf = setMenuItem(mircf, mnNewATS, "RCF\tCTRL+C", SWT.CTRL+'C');
	    mi = setSeparator(mi, mnNewATS);
	    mifpl = setMenuItem(mifpl, mnNewATS, "FPL\tCTRL+D", SWT.CTRL+'D');
	    midla = setMenuItem(midla, mnNewATS, "DLA\tCTRL+E", SWT.CTRL+'E');
	    michg = setMenuItem(michg, mnNewATS, "CHG\tCTRL+F", SWT.CTRL+'F');
	    micnl = setMenuItem(micnl, mnNewATS, "CNL\tCTRL+G", SWT.CTRL+'G');
	    midep = setMenuItem(midep, mnNewATS, "DEP\tCTRL+H", SWT.CTRL+'H');
	    miarr = setMenuItem(miarr, mnNewATS, "ARR\tCTRL+I", SWT.CTRL+'I');
	    mi = setSeparator(mi, mnNewATS);
	    micdn = setMenuItem(micdn, mnNewATS, "CDN\tCTRL+J", SWT.CTRL+'J');
	    micpl = setMenuItem(micpl, mnNewATS, "CPL\tCTRL+K", SWT.CTRL+'K');
	    miest = setMenuItem(miest, mnNewATS, "EST\tCTRL+L", SWT.CTRL+'L');
	    miacp = setMenuItem(miacp, mnNewATS, "ACP\tCTRL+M", SWT.CTRL+'M');
	    milam = setMenuItem(milam, mnNewATS, "LAM\tCTRL+N", SWT.CTRL+'N');
	    mi = setSeparator(mi, mnNewATS);
	    mirqp = setMenuItem(mirqp, mnNewATS, "RQP\tCTRL+O", SWT.CTRL+'O');
	    mirqs = setMenuItem(mirqs, mnNewATS, "RQS\tCTRL+P", SWT.CTRL+'P');
	    mispl = setMenuItem(mispl, mnNewATS, "SPL\tCTRL+Q", SWT.CTRL+'Q');
	    mi = setSeparator(mi, mnAFTNMsg);
	    miTest = setMenuItem(miTest, mnAFTNMsg, "&Test && SVC TRAF\tALT+SHIFT+T", SWT.SHIFT+SWT.ALT+'T');
	    //MENU: MAINTENANCE
	    miRoute = setMenuItem(miRoute, mnMaintenance, "Ro&ute\tSHIFT+U", SWT.SHIFT+'U');
	    miType9b = setMenuItem(miType9b, mnMaintenance, "&Type of Aircraft\tSHIFT+T", SWT.SHIFT+'T');
	    miReg = setMenuItem(miReg, mnMaintenance, "&REG/\tSHIFT+R", SWT.SHIFT+'R');
	    miLocind = setMenuItem(miLocind, mnMaintenance, "ICAO &Location Indicator\tSHIFT+L", SWT.SHIFT+'L');
	    mi = setSeparator(mi, mnMaintenance);
	    miCommunication = setMenuItem(miCommunication, mnMaintenance, "Communication &Setup\tSHIFT+S", SWT.SHIFT+'S');
	    
	    ReadFromFile.readConfiguration();
		if (ReadFromFile.getPrint().compareToIgnoreCase("yes")==0 || ReadFromFile.getPrint().compareToIgnoreCase("y")==0) {
			miPrinter = setMenuItem(miPrinter, mnMaintenance, "Pr&inting\tSHIFT+I", SWT.SHIFT+'I');
		}
	    mi = setSeparator(mi, mnMaintenance);
	    miPDFSetting = setMenuItem(miPDFSetting, mnMaintenance, "PD&F Setting\tSHIFT+F", SWT.SHIFT+'F');
	    //MENU: VIEW
	    miAbbr = setMenuItem(miAbbr, mnView, "ICAO A&bbreviations and Codes\tSHIFT+B", SWT.SHIFT+'B');
	    miWarning = setMenuItem(miWarning, mnView, "Incoming &Warning Messages\tSHIFT+W", SWT.SHIFT+'W');
	    ReadFromFile.readConfiguration();
		if (ReadFromFile.getPrint().compareToIgnoreCase("yes")==0 || ReadFromFile.getPrint().compareToIgnoreCase("y")==0) {
			miQueue = setMenuItem(miQueue, mnView, "&Queue Printing Messages\tSHIFT+Q", SWT.SHIFT+'Q');
		}
	    //MENU: STATUS
	    miOutgoing = setMenuItem(miOutgoing, mnStatus, "Queue/N&ot Delivered Messages\tSHIFT+O", SWT.SHIFT+'O');
	    //MENU: WINDOWS
	    miWindow = new MenuItem(mnWindow, SWT.PUSH);
	    miWindow.setText("Close acti&ve windows\tSHIFT+V");
	    miWindow.setAccelerator(SWT.SHIFT+'V');
	    
	    addMenuListeners();
	    shell.setMenuBar(menuBar);
	}
	
	public static int setLocX(Shell shell) {
		Monitor primary = TeleSplashScreen2016IP.display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		return x;
	}
	
	public static int setLocY(Shell shell) {
		Monitor primary = TeleSplashScreen2016IP.display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int y = bounds.y + (bounds.height - rect.height) / 2;
		return y;
	}
	
    void sndudp(String sData) {
    	  try {
  		  DatagramSocket s = new DatagramSocket();
            byte[] line = new byte[1000];
            InetAddress dest = InetAddress.getByName("127.0.0.1");
            //System.out.println("getbytes");
            line = sData.getBytes();
            int len = line.length;
            DatagramPacket pkt = new DatagramPacket(line, len, dest, Integer.parseInt("100"));
  		  System.out.println("Send : "+len+" bytes "+"Data:"+sData);
  		  System.out.println("To   : "+"local"+" Port:"+100);
            s.send(pkt);
            s.close();
        } catch (Exception err) {
            System.out.println("err dgram" + err);
        }
      }

}
