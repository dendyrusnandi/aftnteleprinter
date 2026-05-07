package displays;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import com.itextpdf.text.List;

import readwrite.ReadFromFile;
import setting.Shorten;
import setting.Titles;
import actions.Jdbc;
import displays.tabs.TabATS;
import displays.tabs.TabFREE;
import displays.tabs.TabIN;
import displays.tabs.TabMETEO;
import displays.tabs.TabNOTAM;
import displays.tabs.TabOUT;
import displays.tabs.TabREJECT;
import displays.tabs.TabSTATISTIC;

public class MainForm {
	Shorten sh = new Shorten();
	ReadFromFile rff = new ReadFromFile();
	
	public static Shell shell;
//	public static Shell shQuit = new Shell(AmscSplashScreen.display, SWT.DIALOG_TRIM);
	public static Shell shLogout = new Shell(AmscSplashScreen2.display, SWT.DIALOG_TRIM);
	
	public static String sFolder="/aed/";
	public static String sFolderPics="/aed/pics/";
	public static String path="/tmp/ramdisk0/";
	
	public static Label lServer,lTimer;
	public static Shell shCalendar = new Shell(AmscSplashScreen2.display, SWT.DIALOG_TRIM);
	
	public static int dx,dy,widthTxt;
	
	public static String title="";
	
	
	public static void rerun() {
		String run = ReadFromFile.ReadInputFile2("/aed/runamscextdb");
//		System.out.println("run:" + run + "#");
		if (run.isEmpty()) run = "/aed/runamscextdb";
		else if (!run.isEmpty() && run.contains("\n")) {
			String sub[] = run.split("\n");
			for (int i=0; i<sub.length; i++) {
				System.out.println("cmd"+i+":" + sub[i]+ "#");
				try {
					Runtime.getRuntime().exec(sub[i]);
				} catch (IOException ioe) {
					System.err.println("Runtime info:" + ioe.getMessage());
				}
			}
			System.exit(0);	
		}
	}
	
	public MainForm(Display display) {
		ConnectTo.DB_NAME = ConnectTo.DB_URL;
		rff.readConfiguration();
		title = rff.getTitle(); if (title.isEmpty()) title="AMSC External Database";
		
		shell = new Shell (display, SWT.NO_TRIM);
		shell.setLayout(new GridLayout());
		shell.setText(title);
		shell.setMinimized(false);
		shell.setMaximized(false);
		shell.addListener (SWT.Close, new Listener () {
			public void handleEvent (Event event) {
				//rerun();
//				if (run.isEmpty()) run = "/aed/runamscextdb";
//				try {
//					Runtime.getRuntime().exec(run);
//				} catch (IOException ioe) {
//					System.err.println("Runtime info:" + ioe.getMessage());
//				}
//				System.exit(0);
				
//				new ConnectTo(AmscSplashScreen.display);
//				shell.close();
				System.exit(0);
			}
		});
		
		
		String clientAreaa = display.getClientArea().toString();
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
		dy = Integer.parseInt(subs[3])-25;
		System.out.println("Resolution display="+dx+"x"+dy);
		
		Group g = new Group(shell, SWT.BORDER); sh.groupStyle(g, 1, ""); 
		Composite grp1 = new Composite(g, SWT.NONE); sh.composeStyle(grp1, 1);
		Label label = new Label(grp1, SWT.BORDER | SWT.CENTER); sh.labelStyle0(label, title, sh.BLACK);
		
		FontData fontData = label.getFont().getFontData()[0];
        final Font mainFont = new Font(display, new FontData(fontData.getName(), fontData.getHeight(), SWT.BOLD));
        final Font subFont = new Font(display, new FontData(fontData.getName(), fontData.getHeight(), SWT.NORMAL));
        final Font mainActiveFont = new Font(display, new FontData(fontData.getName(), fontData.getHeight()+2, SWT.BOLD));
        final Font subActiveFont = new Font(display, new FontData(fontData.getName(), fontData.getHeight()+2, SWT.NORMAL));
        
		Composite grp = new Composite(g, SWT.NONE); sh.composeStyle(grp, 5);
		lServer = new Label(grp, SWT.CENTER | SWT.BORDER); sh.labelStyle1(lServer, "  Connect to : Database Server "+ConnectTo.sServer+"  ", SWT.LEFT, SWT.CENTER, SWT.BOLD, sh.BLACK);
		lTimer = new Label(grp, SWT.CENTER | SWT.BORDER); sh.labelStyle1(lTimer, "  Date/Time : EEE, 0000-00-00 00:00:00  ", SWT.LEFT, SWT.CENTER, SWT.BOLD, sh.BLACK);
		Label llabel = new Label(grp, SWT.NONE); sh.labelStyle0(llabel, "", sh.BLACK);
		Button bQuit = new Button(grp, SWT.PUSH); sh.buttonStyle(bQuit, "&Quit", "Quit", sh.widthBtn, sh.DarkGrey, SWT.CENTER, SWT.CENTER, null);
		lTimer.setToolTipText("Day, yyyy-MM-dd HH:mm:ss");
		
		label.setBackground(sh.ActiveTabColor);
		label.setFont(mainActiveFont);
//		lServer.setFont(mainFont);
//		lTimer.setFont(mainFont);
		bQuit.setFont(mainFont);
		
		lTimer.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) { }
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { 
				if (shCalendar.isDisposed()) {
					shCalendar = new Shell(AmscSplashScreen2.display, SWT.DIALOG_TRIM);
				}
				if (!shCalendar.isVisible()) {
					new Calendar(shCalendar);
				} else {
					shCalendar.close();
					shCalendar = new Shell(AmscSplashScreen2.display, SWT.DIALOG_TRIM);
					new Calendar(shCalendar);
				}
			}
		});
		
		bQuit.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
//				new ConnectTo(AmscSplashScreen.display);
				shell.close();
			} });
		
		final CTabFolder ctf_main=new CTabFolder(shell,SWT.LEFT);
		final CTabItem tbiDATA=new CTabItem(ctf_main,SWT.NONE); tbiDATA.setText("Partial &Data          ");
		final CTabItem tbiRETRIEVAL=new CTabItem(ctf_main,SWT.NONE); tbiRETRIEVAL.setText("&Retrieval          ");
		
		final CTabFolder ctf_data=new CTabFolder(ctf_main,SWT.LEFT);
        final CTabItem tbiFREE=new CTabItem(ctf_data,SWT.FLAT); tbiFREE.setText("Freete&xt          ");
        final CTabItem tbiATS=new CTabItem(ctf_data,SWT.FLAT); tbiATS.setText("&ATS Messages          ");
        final CTabItem tbiNOTAM=new CTabItem(ctf_data,SWT.FLAT); tbiNOTAM.setText("&NOTAM          ");
        final CTabItem tbiMETEO=new CTabItem(ctf_data,SWT.FLAT); tbiMETEO.setText("&METEO          ");
        final CTabItem tbiREJECT=new CTabItem(ctf_data,SWT.FLAT); tbiREJECT.setText("Re&ject          ");
        final CTabItem tbiSTATISTIC=new CTabItem(ctf_data,SWT.NONE); tbiSTATISTIC.setText("&Statistic          ");
		
        final CTabFolder ctf_retrieval=new CTabFolder(ctf_main,SWT.LEFT);
        final CTabItem tbiIN=new CTabItem(ctf_retrieval,SWT.FLAT); tbiIN.setText("&Incoming          ");
        final CTabItem tbiOUT=new CTabItem(ctf_retrieval,SWT.FLAT); tbiOUT.setText("&Outgoing          ");
        
        ctf_main.setSimple(false);
        ctf_main.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        ctf_main.pack();
        
        ctf_data.setSimple(false);
        ctf_data.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        ctf_data.pack();
        
        ctf_retrieval.setSimple(false);
        ctf_retrieval.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        ctf_retrieval.pack();
        
        tbiDATA.setFont(mainFont);
        tbiRETRIEVAL.setFont(mainFont);
        tbiFREE.setFont(subFont);
        tbiATS.setFont(subFont);
        tbiNOTAM.setFont(subFont);
        tbiMETEO.setFont(subFont);
        tbiREJECT.setFont(subFont);
        tbiSTATISTIC.setFont(subFont);
        tbiIN.setFont(subFont);
        tbiOUT.setFont(subFont);
        
        Composite CompFREE = new Composite(ctf_data, SWT.NONE); sh.composeStyle(CompFREE, 1, SWT.LEFT, SWT.LEFT);
        Composite CompATS = new Composite(ctf_data, SWT.NONE); sh.composeStyle(CompATS, 1, SWT.LEFT, SWT.LEFT);
        Composite CompNOTAM = new Composite(ctf_data, SWT.NONE); sh.composeStyle(CompNOTAM, 1, SWT.LEFT, SWT.LEFT);
		Composite CompMETEO = new Composite(ctf_data, SWT.NONE); sh.composeStyle(CompMETEO, 1, SWT.LEFT, SWT.LEFT);
		Composite CompREJECT = new Composite(ctf_data, SWT.NONE); sh.composeStyle(CompREJECT, 1, SWT.LEFT, SWT.LEFT);
		Composite CompSTATISTIC = new Composite(ctf_data, SWT.NONE); sh.composeStyle(CompSTATISTIC, 1, SWT.LEFT, SWT.LEFT);
		Composite CompIN = new Composite(ctf_retrieval, SWT.NONE); sh.composeStyle(CompIN, 1, SWT.LEFT, SWT.LEFT);
        Composite CompOUT = new Composite(ctf_retrieval, SWT.NONE); sh.composeStyle(CompOUT, 1, SWT.LEFT, SWT.LEFT);
        
        new TabFREE().comp(CompFREE);
        new TabATS().comp(CompATS);
		new TabNOTAM().comp(CompNOTAM);
		new TabMETEO().comp(CompMETEO);
		new TabREJECT().comp(CompREJECT);
		new TabSTATISTIC().comp(CompSTATISTIC);
		new TabIN().comp(CompIN);
		new TabOUT().comp(CompOUT);
		
		tbiDATA.setControl(ctf_data);
        tbiRETRIEVAL.setControl(ctf_retrieval);
        tbiFREE.setControl(CompFREE);
		tbiATS.setControl(CompATS);
		tbiNOTAM.setControl(CompNOTAM);
		tbiMETEO.setControl(CompMETEO);
		tbiREJECT.setControl(CompREJECT);
        tbiSTATISTIC.setControl(CompSTATISTIC);
        tbiIN.setControl(CompIN);
		tbiOUT.setControl(CompOUT);

		ctf_main.setSelectionBackground(new Color[]{new Color(display, new RGB(242, 244, 247)), sh.ActiveTabColor}, new int[]{100}, true);
		ctf_data.setSelectionBackground(new Color[]{new Color(display, new RGB(242, 244, 247)), sh.ActiveTabColor}, new int[]{100}, true);
		ctf_retrieval.setSelectionBackground(new Color[]{new Color(display, new RGB(242, 244, 247)), sh.ActiveTabColor}, new int[]{100}, true);
		
		ctf_main.setSelectionForeground(sh.BLUE);
		ctf_data.setSelectionForeground(sh.BLUE);
		ctf_retrieval.setSelectionForeground(sh.BLUE);
		
		ctf_main.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent event) {
				String tabName = ctf_main.getSelection().toString().replace("CTabItem {", "").replace("}", "");
//				System.out.println("Tab:"+tabName+" is active");
				
				if (tabName.compareToIgnoreCase("Partial &Data          ")==0) {
					Jdbc.conn();
					activeTabMain(tbiDATA, tbiRETRIEVAL, mainActiveFont, mainFont);
					
					//Tab partial yang aktif: Tab Freetext
					ctf_data.setSelection(tbiFREE);
					//add
					setJmlDataNol();
					activeTabData(tbiFREE, tbiATS, tbiNOTAM, tbiMETEO, tbiREJECT, tbiSTATISTIC, subActiveFont, subFont);
					TabFREE.sTblNm="air_message_free_text"; 
					TabFREE.sIdName="id_free_text"; 
					TabFREE.sID=Titles.stFREETEXT;
					clearTbl();
					Lists.discard();
					new Lists().col(TabFREE.sID);
					
					//Koneksi untuk tab Partial Data ke database ais
					ConnectTo.DB_NAME = ConnectTo.DB_URL; 
					setPopup(); 
					setListView(true);	
				}
				
				else if (tabName.compareToIgnoreCase("&Retrieval          ")==0) {
					Jdbc.conn();
					activeTabMain(tbiRETRIEVAL, tbiDATA, mainActiveFont, mainFont);
					
					//Tab partial yang aktif: Tab Incoming
					ctf_retrieval.setSelection(tbiIN);
					//add
					setJmlDataNol();
					activeTabRetrieve(tbiIN, tbiOUT, subActiveFont, subFont);
					TabIN.sTblNm="aftnrdI"; 
					TabIN.sIdName="idcnt"; 
					TabIN.sID=Titles.stIncoming;
					clearTbl(); 
					Lists.discard();
					new Lists().col(TabIN.sID);
					
					//Koneksi untuk tab Partial Data ke database amsc
					String strURL = ConnectTo.DB_URL;
					String strDB = strURL.substring(strURL.lastIndexOf("/")+1,strURL.length());
					strDB = strDB.replace(strDB, "amsc");
					String strURL1 = strURL.substring(0, strURL.lastIndexOf("/")+1).concat(strDB);
					ConnectTo.DB_NAME = strURL1;  
					setPopup(); 
					setListView(true);	
				}
			}
		});

		ctf_data.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent event) {
				String tabName = ctf_data.getSelection().toString().replace("CTabItem {", "").replace("}", "");
//				System.out.println("Tab:"+tabName+" is active");
				
				if (tabName.compareToIgnoreCase("Freete&xt          ")==0) { 
					setAwal(tbiFREE, tbiATS, tbiNOTAM, tbiMETEO, tbiREJECT, tbiSTATISTIC, subActiveFont, subFont);
				}
			
				if (tabName.compareToIgnoreCase("&ATS Messages          ")==0) { Jdbc.conn();//default: FPL
					setJmlDataNol();
					activeTabData(tbiATS, tbiFREE, tbiNOTAM, tbiMETEO, tbiREJECT, tbiSTATISTIC, subActiveFont, subFont);
					TabATS.sTblNm="air_message"; 
					TabATS.sIdName="id_ats"; 
					TabATS.sID=Titles.stFPL;
					//default pencarian
					TabATS.cbMsgType.setText("FPL");
					TabATS.alr();
					
					clearTbl(); 
					Lists.discard();
					new Lists().col(TabATS.sID); 
					setPopup(); 
					setListView(true);
				}
				
				if (tabName.compareToIgnoreCase("&NOTAM          ")==0) { Jdbc.conn();//default: NOTAM
					setJmlDataNol();
					activeTabData(tbiNOTAM, tbiATS, tbiFREE, tbiMETEO, tbiREJECT, tbiSTATISTIC, subActiveFont, subFont);
					TabNOTAM.sTblNm="notam_multi"; 
					TabNOTAM.sIdName="id_notam_multi"; 
					TabNOTAM.sID=Titles.stNOTAM;
					//default pencarian
					TabNOTAM.cbMsgType.setText("NOTAM");
					TabNOTAM.notam();
					
					clearTbl(); 
					Lists.discard();
					new Lists().col(TabNOTAM.sID); 
					setPopup(); 
					setListView(true);
				} 
				
				if (tabName.compareToIgnoreCase("&METEO          ")==0) { Jdbc.conn();//default: METEO
					setJmlDataNol();
					activeTabData(tbiMETEO, tbiATS, tbiNOTAM, tbiFREE, tbiREJECT, tbiSTATISTIC, subActiveFont, subFont);
					TabMETEO.sTblNm="meteo_metar"; 
					TabMETEO.sIdName="id_metar"; 
					TabMETEO.sID=Titles.stMETAR;
					//default pencarian
					TabMETEO.cbMsgType.setText("METAR");
					TabMETEO.enabledData();
					
					clearTbl(); 
					Lists.discard();
					new Lists().col(TabMETEO.sID); 
					setPopup(); 
					setListView(true);
				}

				if (tabName.compareToIgnoreCase("Re&ject          ")==0) { Jdbc.conn();//default: REJECTED ATS
					setJmlDataNol();
					activeTabData(tbiREJECT, tbiATS, tbiNOTAM, tbiMETEO, tbiFREE, tbiSTATISTIC, subActiveFont, subFont);
					TabREJECT.sTblNm="reject_message_ats"; 
					TabREJECT.sIdName="id_rjt_msg"; 
					TabREJECT.sID=Titles.stReject;
					clearTbl(); 
					Lists.discard();
					new Lists().col(TabREJECT.sID); 
					setPopup(); 
					setListView(true);
				} 

				if (tabName.compareToIgnoreCase("&Statistic          ")==0) { Jdbc.conn();//default: REJECTED ATS
					setJmlDataNol();
					activeTabData(tbiSTATISTIC, tbiREJECT, tbiATS, tbiNOTAM, tbiMETEO, tbiFREE, subActiveFont, subFont);
//					TabREJECT.sTblNm="reject_message_ats"; //sesuai message type yang nanti akan dipilih 
//					TabREJECT.sIdName="id_rjt_msg"; //sesuai message type yang nanti akan dipilih
					TabSTATISTIC.sID=Titles.stStatistic;
					clearTbl(); 
					Lists.discard();
					new Lists().col(TabSTATISTIC.sID); 
					setPopup(); 
					setListView(false);
				} 
			}
		});
		
		ctf_retrieval.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent event) {
				String tabName = ctf_retrieval.getSelection().toString().replace("CTabItem {", "").replace("}", "");
//				System.out.println("Tab:"+tabName+" is active");

				if (tabName.compareToIgnoreCase("&Incoming          ")==0) { Jdbc.conn();
					setJmlDataNol();
					activeTabRetrieve(tbiIN, tbiOUT, subActiveFont, subFont);
					TabIN.sTblNm="aftnrdI"; 
					TabIN.sIdName="idcnt"; 
					TabIN.sID=Titles.stIncoming;
					clearTbl(); 
					Lists.discard();
					new Lists().col(TabIN.sID); 
					setPopup(); 
					setListView(true);
				}
				
				else if (tabName.compareToIgnoreCase("&Outgoing          ")==0) { Jdbc.conn();
					setJmlDataNol();
					activeTabRetrieve(tbiOUT, tbiIN, subActiveFont, subFont);
					TabOUT.sTblNm="aftnrdO"; 
					TabOUT.sIdName="idcnt"; 
					TabOUT.sID=Titles.stOutgoing;
					clearTbl(); 
					Lists.discard();
					new Lists().col(TabOUT.sID); 
					setPopup(); 
					setListView(true);
				}
			}
		});
		
		// Tab folder yang aktif pertama kali: Partial Data (mainform), Freetext (tab:Partial Data), Incoming (tab:Retieval)
		ctf_main.setSelection(tbiDATA);
		ctf_data.setSelection(tbiFREE);
		ctf_retrieval.setSelection(tbiIN);

		
		activeTabData(tbiFREE, tbiATS, tbiNOTAM, tbiMETEO, tbiREJECT, tbiSTATISTIC, subActiveFont, subFont);
		activeTabRetrieve(tbiIN, tbiOUT, subActiveFont, subFont);
		activeTabMain(tbiDATA, tbiRETRIEVAL, mainActiveFont, mainFont);
		
		new Lists("","","","","0000-00-00 00:00","0000-00-00 00:00").compList(shell);
		setAwal(tbiFREE, tbiATS, tbiNOTAM, tbiMETEO, tbiREJECT, tbiSTATISTIC, subActiveFont, subFont);
		
		shell.setSize(dx, dy);
		shell.setLocation(xx, yy);
		shell.open();
	}
	
	static void setListView(boolean b) {
		Lists.visibleInfoPage(b);
		Lists.visibleInfoRow(b);
		Lists.table.setVisible(b);
		Lists.tTextual.setVisible(b);
		Lists.tTrace.setVisible(b);
		Lists.lTextual.setVisible(b);
		Lists.lTrace.setVisible(b);
	}
	
	static void setAwal(CTabItem tbiFREE, CTabItem tbiATS, CTabItem tbiNOTAM, CTabItem tbiMETEO, CTabItem tbiREJECT, CTabItem tbiSTATISTIC, 
			Font subActiveFont, Font subFont) {
		Jdbc.conn();
		setJmlDataNol();
		activeTabData(tbiFREE, tbiATS, tbiNOTAM, tbiMETEO, tbiREJECT, tbiSTATISTIC, subActiveFont, subFont);
		TabFREE.sTblNm="air_message_free_text"; 
		TabFREE.sIdName="id_free_text"; 
		TabFREE.sID=Titles.stFREETEXT;
		clearTbl();
		Lists.discard();
		new Lists().col(TabFREE.sID);  
		setPopup(); 
		setListView(true);	
	}
	
	private static void setPopup() {
		//disabled popup menu
		Lists.activatePopup(false, "");
	}
	
	private static void setJmlDataNol() {
		Lists.iJmlData=0;
	}
	
	private static void activeTabData(CTabItem tbi1,CTabItem tbi2,CTabItem tbi3,CTabItem tbi4,CTabItem tbi5,CTabItem tbi6,
			Font act,Font nonact) {
		tbi1.setFont(act);
        tbi2.setFont(nonact);
        tbi3.setFont(nonact);
        tbi4.setFont(nonact);
        tbi5.setFont(nonact);
        tbi6.setFont(nonact);
	}
	
	private static void activeTabRetrieve(CTabItem tbi1,CTabItem tbi2,Font act,Font nonact) {
		tbi1.setFont(act);
        tbi2.setFont(nonact);
	}
	
	private static void activeTabMain(CTabItem tbi1,CTabItem tbi2,Font act,Font nonact) {
		tbi1.setFont(act);
        tbi2.setFont(nonact);
	}
	
	public static void clearTbl() {
		//First, we make sure the user does not see what we're doing.
		Lists.table.setRedraw( false );
		//Then we remove all columns.
		while ( Lists.table.getColumnCount() > 0 ) {
			Lists.table.getColumns()[ 0 ].dispose();
		}
		Lists.table.removeAll();
		//And finally we let the user see what we did.
		Lists.table.setRedraw( true );
	}
	
	public static int setLocX(Shell shell) {
		Monitor primary = AmscSplashScreen2.display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		
		int x = bounds.x + (bounds.width - rect.width) / 2;
//		int y = bounds.y + (bounds.height - rect.height) / 2;
		
		return x;
	}
	
	public static int setLocY(Shell shell) {
		Monitor primary = AmscSplashScreen2.display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		
//		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		
		return y;
	}
}


//final CTabFolder folder = new CTabFolder(shell, SWT.BORDER);
//folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//folder.setSimple(false);
//folder.setUnselectedImageVisible(false);
//folder.setUnselectedCloseVisible(false);
//
//CTabItem itemATS = new CTabItem(folder, SWT.MIN | SWT.MAX); itemATS.setText("&ATS Messages");
//Composite compATS = new Composite(folder, SWT.BORDER);
//sh.composeStyle(compATS, 1, SWT.LEFT, SWT.LEFT);
//new TabATS().comp(compATS);
//itemATS.setControl(compATS);
//
//CTabItem item&NOTAM = new CTabItem(folder, SWT.MIN | SWT.MAX); itemNOTAM.setText("&NOTAM");
//Composite comp&NOTAM = new Composite(folder, SWT.BORDER);
//sh.composeStyle(compNOTAM, 1, SWT.LEFT, SWT.LEFT);
//new TabNOTAM().comp(compNOTAM);
//itemNOTAM.setControl(compNOTAM);
//
//CTabItem itemMETEO = new CTabItem(folder, SWT.MIN | SWT.MAX); itemMETEO.setText("&METEO");
//Composite compMETEO = new Composite(folder, SWT.BORDER);
//sh.composeStyle(compMETEO, 1, SWT.LEFT, SWT.LEFT);
//new TabMETEO().comp(compMETEO);
//itemMETEO.setControl(compMETEO);
//
//CTabItem itemIN= new CTabItem(folder, SWT.MIN | SWT.MAX); itemIN.setText("&Incoming");
//Composite compIN= new Composite(folder, SWT.BORDER);
//sh.composeStyle(compIN, 1, SWT.LEFT, SWT.LEFT);
//new TabIN().comp(compIN);
//itemIN.setControl(compIN);
//
//CTabItem itemOUT= new CTabItem(folder, SWT.MIN | SWT.MAX); itemOUT.setText("&Outgoing");
//Composite compOUT= new Composite(folder, SWT.BORDER);
//sh.composeStyle(compOUT, 1, SWT.LEFT, SWT.LEFT);
//new TabOUT().comp(compOUT);
//itemOUT.setControl(compOUT);
//
//folder.setSelection(itemATS);
//folder.setMinimizeVisible(false);
//folder.setMaximizeVisible(false);
//folder.addCTabFolder2Listener(new CTabFolder2Adapter() {
//	@Override
//	public void minimize(CTabFolderEvent event) {
//		folder.setMinimized(true);
//		folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//		shell.layout(true);
//	}
//	@Override
//	public void maximize(CTabFolderEvent event) {
//		folder.setMaximized(true);
//		folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		shell.layout(true);
//	}
//	@Override
//	public void restore(CTabFolderEvent event) {
//		folder.setMinimized(false);
//		folder.setMaximized(false);
//		folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//		shell.layout(true);
//	}
//});

//folder.addSelectionListener(new SelectionAdapter() {
//	public void widgetSelected(org.eclipse.swt.events.SelectionEvent event) {
//		String tabName = folder.getSelection().toString().replace("CTabItem {", "").replace("}", "");
////		System.out.println("Tab:"+tabName+" is active");
//		
//		if (tabName.compareToIgnoreCase("&METEO")==0 || tabName.compareToIgnoreCase("&NOTAM")==0 ||
//				tabName.compareToIgnoreCase("&ATS Messages")==0) {
//			ConnectTo.DB_NAME = ConnectTo.DB_URL; }
//		
//		if (tabName.compareToIgnoreCase("&Incoming")==0) {
//			clearTbl();
//			new Lists().col(Titles.stIncoming);
//			
//			String strURL = ConnectTo.DB_URL;
//			String strDB = strURL.substring(strURL.lastIndexOf("/")+1,strURL.length());
//			strDB = strDB.replace(strDB, "amsc");
//			String strURL1 = strURL.substring(0, strURL.lastIndexOf("/")+1).concat(strDB);
//			ConnectTo.DB_NAME = strURL1; } 
//		
//		if (tabName.compareToIgnoreCase("&Outgoing")==0) {
//			clearTbl();
//			new Lists().col(Titles.stOutgoing);
//			
//			String strURL = ConnectTo.DB_URL;
//			String strDB = strURL.substring(strURL.lastIndexOf("/")+1,strURL.length());
//			strDB = strDB.replace(strDB, "amsc");
//			String strURL1 = strURL.substring(0, strURL.lastIndexOf("/")+1).concat(strDB);
//			ConnectTo.DB_NAME = strURL1; }
//	}
//});