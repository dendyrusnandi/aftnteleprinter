package displays.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import readwrite.ReadFromFile;
import setting.ButtonsSetting;
import setting.Images;
import setting.Shorten;
import setting.Title;
import actions.Close;
import actions.Discard;
import actions.Send;
import displays.MainForm;
import displays.TeleSplashScreen2016IP;
import displays.tables.TableATS;
import displays.tables.TableOutgoing;


public class FormTemplates {
	ButtonsSetting bs = new ButtonsSetting();
	Shorten sh = new Shorten();
	ReadFromFile rff = new ReadFromFile();

	static Shell shell = new Shell();
	static ToolItem toolSend,toolSendClear,toolSendAMO,toolSave,toolDiscard,toolClose;
	static Label lNote;
	
	public static int iWidthComp=0; 
	public static String ID="";
	
	
	public FormTemplates() {
		
	}
	
	void createSeparator() {
		int iSeparatorFB = MainForm.getWidthRightTop();
        Composite typeA = new Composite(shell, SWT.NONE); sh.compositeStyle(typeA, 1, SWT.LEFT, SWT.TOP);
		Label ls = new Label(typeA, SWT.BORDER | SWT.TOP | SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelSeparator(ls, iSeparatorFB, 10);
	}

	public void newtemp(Shell pShell, String id, final String idedit) {
		ReadFromFile.readConfiguration();
		int iWidthShell = ReadFromFile.getWidth_Template();//(MainForm.getWidthRightTop()-100);
		int iHeightShell = ReadFromFile.getHeight_Template();//MainForm.dy-150;
		iWidthComp = iWidthShell-20;

		shell = pShell;
		ID = id;
		System.out.println("\nID template=" + ID);
		ReadFromFile.readConfiguration();
        shell.setLayout(new FillLayout(SWT.VERTICAL));
       
        shell.addListener (SWT.Close, new Listener () {
			public void handleEvent (Event event) {
				Close.closeMiniforms();
				if (idedit.compareTo("ATS")==0) { TableATS.setTableFocus(); }
				else if (idedit.compareTo("OUT")==0) { TableOutgoing.setTableFocus(); }
				
				if (ID.endsWith("AFTN")) { 
					String telestat = TeleSplashScreen2016IP.telestat;//ReadFromFile.ReadInputFile1("/tp/template/telestat.txt");
		    		if (telestat.isEmpty()) telestat="1";
		    		if (telestat.compareTo("1")==0) {
		    		} else if (telestat.compareTo("0")==0) {
		    			MainForm.rerun();
		    		}
				}
			}
		});
        
        SashForm form = new SashForm(shell,SWT.VERTICAL); form.setLayout(new FillLayout());

        Group sashTOP = new Group(form, SWT.NONE); sh.groupStyle(sashTOP, 1, "");
        Composite cToolbar = new Composite(sashTOP, SWT.NONE); sh.compositeStyle(cToolbar, 2, SWT.LEFT, SWT.CENTER, iWidthComp); 
        Composite cSendat = new Composite(cToolbar, SWT.NONE); sh.compositeStyle(cSendat, 1, SWT.LEFT, SWT.CENTER); 
        Composite cToolitem = new Composite(cToolbar, SWT.NONE); sh.compositeStyle(cToolitem, 1, SWT.RIGHT, SWT.CENTER);
        
        Group sashMID = new Group(form, SWT.NONE); sh.groupStyle(sashMID, 1, "AFTN Header");
        Composite cHeader = new Composite(sashMID, SWT.NONE); sh.compositeStyle(cHeader, 1, SWT.LEFT, SWT.CENTER);
        
        ScrolledComposite sashBOT = new ScrolledComposite(form, SWT.V_SCROLL | SWT.TOP | SWT.LEFT); sashBOT.setAlwaysShowScrollBars(true);
        Composite cBody = new Composite(sashBOT, SWT.NONE); sh.compositeStyle(cBody, 1, SWT.LEFT, SWT.CENTER);
        
        // sash: TOP
        if (ID.endsWith("AFTN")) { HeaderComposite.sendatAFTN(cSendat); }
        else if (ID.endsWith("ALR")) { HeaderComposite.sendatALR(cSendat); }
        else if (ID.endsWith("RCF")) { HeaderComposite.sendatRCF(cSendat); }
        else if (ID.endsWith("FPL")) { HeaderComposite.sendatFPL(cSendat); }
        else if (ID.endsWith("DLA")) { HeaderComposite.sendatDLA(cSendat); }
        else if (ID.endsWith("CHG")) { HeaderComposite.sendatCHG(cSendat); }
        else if (ID.endsWith("CNL")) { HeaderComposite.sendatCNL(cSendat); }
        else if (ID.endsWith("DEP")) { HeaderComposite.sendatDEP(cSendat); }
        else if (ID.endsWith("ARR")) { HeaderComposite.sendatARR(cSendat); }
        else if (ID.endsWith("CDN")) { HeaderComposite.sendatCDN(cSendat); }
        else if (ID.endsWith("CPL")) { HeaderComposite.sendatCPL(cSendat); }
        else if (ID.endsWith("EST")) { HeaderComposite.sendatEST(cSendat); }
        else if (ID.endsWith("ACP")) { HeaderComposite.sendatACP(cSendat); }
        else if (ID.endsWith("LAM")) { HeaderComposite.sendatLAM(cSendat); }
        else if (ID.endsWith("RQP")) { HeaderComposite.sendatRQP(cSendat); }
        else if (ID.endsWith("RQS")) { HeaderComposite.sendatRQS(cSendat); }
        else if (ID.endsWith("SPL")) { HeaderComposite.sendatSPL(cSendat); }
        
        ToolBar bar1 = new ToolBar (cToolitem, SWT.NONE | SWT.FLAT);
        toolSendClear = new ToolItem(bar1, 0); sh.toolitemStyle(toolSendClear, "&Send+Clear", "Send and save the message, then clear the template", Images.img_SEND_CLEAR);
        toolSend = new ToolItem(bar1, 0); sh.toolitemStyle(toolSend, "S&end", "Send and save the message", Images.img_SEND);
        toolSave = new ToolItem(bar1, 0); sh.toolitemStyle(toolSave, "Sa&ve", "Save message", Images.img_SAVE);
        toolDiscard = new ToolItem(bar1, 0); sh.toolitemStyle(toolDiscard, "&Discard", "Clear all items in this template", Images.img_DISCARD);
        toolClose = new ToolItem(bar1, 0); sh.toolitemStyle(toolClose, "&Close", "Close window", Images.img_CLOSE_WINDOW);
        
        // sash: MIDDLE (Header AFTN) - sash: BOTTOM (Body AFTN) 
		String title = "";
//		int iMinHeightTemp=0;
		
		if (ID.endsWith("AMO")) {
			addListener(ID); 
			title += Title.tAMO; 
			//iMinHeightTemp = rff.getFreeATS();
			ATSForms.initNewAMO(cBody, ID); }
		
		else if (ID.endsWith("FREE")) {
			addListener(ID); 
			title += Title.tFREE;  
			//iMinHeightTemp = rff.getFreeATS(); 
			ATSForms.initNewFREE(cBody, ID); }
		
		else if (ID.endsWith("AFTN")) {
			addListener(ID); 
			title += Title.tAFTN; 
			//iMinHeightTemp = rff.getFreeATS();  
			HeaderComposite.headerAFTN(cHeader); ATSForms.initNewAFTN(cBody, ID); }
		
		else if (ID.endsWith("ALR")) { 
			addListener(ID); 
			title += Title.tALR; 
			//iMinHeightTemp = rff.getALR();  
			HeaderComposite.headerALR(cHeader); ATSForms.initNewALR(cBody, ID); }

		else if (ID.endsWith("RCF")) { 
			addListener(ID); 
			title += Title.tRCF; 
			//iMinHeightTemp = rff.getRCF();  
			HeaderComposite.headerRCF(cHeader); ATSForms.initNewRCF(cBody, ID); }
		
		else if (ID.endsWith("FPL")) { 
			addListener(ID); 
			title += Title.tFPL; 
			//iMinHeightTemp = rff.getFPL();  
			HeaderComposite.headerFPL(cHeader); ATSForms.initNewFPL(cBody, ID); }
		
		else if (ID.endsWith("DLA")) { 
			addListener(ID); 
			title += Title.tDLA; 
			//iMinHeightTemp = rff.getDLA();  
			HeaderComposite.headerDLA(cHeader); ATSForms.initNewDLA(cBody, ID); }
		
		else if (ID.endsWith("CHG")) { 
			addListener(ID); 
			title += Title.tCHG; 
			//iMinHeightTemp = rff.getCHG();  
			HeaderComposite.headerCHG(cHeader); ATSForms.initNewCHG(cBody, ID); }
		
		else if (ID.endsWith("CNL")) { 
			addListener(ID); 
			title += Title.tCNL; 
			//iMinHeightTemp = rff.getCNL();  
			HeaderComposite.headerCNL(cHeader); ATSForms.initNewCNL(cBody, ID); }
		
		else if (ID.endsWith("DEP")) { 
			addListener(ID); 
			title += Title.tDEP; 
			//iMinHeightTemp = rff.getDEP();  
			HeaderComposite.headerDEP(cHeader); ATSForms.initNewDEP(cBody, ID); }
		
		else if (ID.endsWith("ARR")) { 
			addListener(ID); 
			title += Title.tARR; 
			//iMinHeightTemp = rff.getARR();  
			HeaderComposite.headerARR(cHeader); ATSForms.initNewARR(cBody, ID); }
		
		else if (ID.endsWith("CDN")) { 
			addListener(ID); 
			title += Title.tCDN; 
			//iMinHeightTemp = rff.getCDN();  
			HeaderComposite.headerCDN(cHeader); ATSForms.initNewCDN(cBody, ID); }
		
		else if (ID.endsWith("CPL")) { 
			addListener(ID); 
			title += Title.tCPL; 
			//iMinHeightTemp = rff.getCPL();  
			HeaderComposite.headerCPL(cHeader); ATSForms.initNewCPL(cBody, ID); }
		
		else if (ID.endsWith("EST")) { 
			addListener(ID); 
			title += Title.tEST; 
			//iMinHeightTemp = rff.getEST();  
			HeaderComposite.headerEST(cHeader); ATSForms.initNewEST(cBody, ID); }
		
		else if (ID.endsWith("ACP")) { 
			addListener(ID); 
			title += Title.tACP; 
			//iMinHeightTemp = rff.getACP();  
			HeaderComposite.headerACP(cHeader); ATSForms.initNewACP(cBody, ID); }
		
		else if (ID.endsWith("LAM")) { 
			addListener(ID); 
			title += Title.tLAM; 
			//iMinHeightTemp = rff.getLAM();  
			HeaderComposite.headerLAM(cHeader); ATSForms.initNewLAM(cBody, ID); }
		
		else if (ID.endsWith("RQP")) { 
			addListener(ID); 
			title += Title.tRQP; 
			//iMinHeightTemp = rff.getRQP();  
			HeaderComposite.headerRQP(cHeader); ATSForms.initNewRQP(cBody, ID); }
		
		else if (ID.endsWith("RQS")) { 
			addListener(ID); 
			title += Title.tRQS; 
			//iMinHeightTemp = rff.getRQS();  
			HeaderComposite.headerRQS(cHeader); ATSForms.initNewRQS(cBody, ID); }
		
		else if (ID.endsWith("SPL")) { 
			addListener(ID); 
			title += Title.tSPL; 
			//iMinHeightTemp = rff.getSPL();  
			HeaderComposite.headerSPL(cHeader); ATSForms.initNewSPL(cBody, ID); }

		
    
	    title+=" Message";
//	    cBody.setSize(iWidthShell-30,iMinHeightTemp);
	    cBody.setSize(cBody.computeSize(iWidthShell-30, SWT.DEFAULT));
	    sashBOT.setContent(cBody);
	    
//	    ReadFromFile.readConfiguration();
//		iWidthShell = ReadFromFile.getWidth_Template();
//		Float fTop,fMid,fBot;
//		if (ID.endsWith("AMO") || ID.endsWith("FREE")) {
//			fTop = new Float((0.10*iWidthShell));
//			fMid = new Float((0.00*iWidthShell));
//			fBot = new Float((0.90*iWidthShell));
//		} else if (ID.endsWith("FPL")) {
//			fTop = new Float((0.10*iWidthShell));
//			fMid = new Float((0.23*iWidthShell));
//			fBot = new Float((0.67*iWidthShell));
//		} else {
//			fTop = new Float((0.10*iWidthShell));
//			fMid = new Float((0.20*iWidthShell));
//			fBot = new Float((0.70*iWidthShell));
//		}
//		int iTop = fTop.intValue();
//		int iMid = fMid.intValue();
//		int iBot = fBot.intValue();
//	    form.setWeights(new int[] {iTop, iMid, iBot});
	    
		int iSashTOP=0;
		int iSashMID=0;
		int iSashBOT=0;
	    if (ID.endsWith("AMO") || ID.endsWith("FREE")) {
//	    	iHeightShell=iMinHeightTemp+130;
	    	iSashTOP=ReadFromFile.getSashTOP_AMO();
	    	iSashMID=ReadFromFile.getSashMID_AMO();
	    	iSashBOT=ReadFromFile.getSashBOT_AMO(); }
	    else if (ID.endsWith("FPL")) { 
	    	iSashTOP=ReadFromFile.getSashTOP_FPL();
	    	iSashMID=ReadFromFile.getSashMID_FPL();
	    	iSashBOT=ReadFromFile.getSashBOT_FPL(); }
	    else { 
	    	iSashTOP=ReadFromFile.getSashTOP();
	    	iSashMID=ReadFromFile.getSashMID();
	    	iSashBOT=ReadFromFile.getSashBOT(); }
	    form.setWeights(new int[] {iSashTOP, iSashMID, iSashBOT});
	    
	    shell.setSize(iWidthShell, iHeightShell);
	    shell.setLocation(ReadFromFile.getxLoc_Template(), ReadFromFile.getyLoc_Template());
	    shell.setText(title);
	    shell.open();
	}
	
	void addListener(final String ID) {
		toolSendClear.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) { new Send(ID, "sendclear"); }
			public void widgetDefaultSelected(SelectionEvent event) { } });

		toolSend.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) { new Send(ID, "send"); }
			public void widgetDefaultSelected(SelectionEvent event) { } });

		toolSave.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) { new Send(ID, "save"); }
			public void widgetDefaultSelected(SelectionEvent event) { } });        	
		
		toolDiscard.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) { new Discard(ID); }
			public void widgetDefaultSelected(SelectionEvent event) { } });

//		if (ID.endsWith("AMO")) {
//			toolSendAMO.addSelectionListener(new SelectionListener() {
//				public void widgetSelected(SelectionEvent event) { new Send(ID, "sendamo"); }
//				public void widgetDefaultSelected(SelectionEvent event) { } });
//		}
		
		toolClose.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) { new Close(ID); }
			public void widgetDefaultSelected(SelectionEvent event) { } });
	}
}





//package displays.forms;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.custom.SashForm;
//import org.eclipse.swt.custom.ScrolledComposite;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.events.SelectionListener;
//import org.eclipse.swt.layout.FillLayout;
//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.widgets.ToolBar;
//import org.eclipse.swt.widgets.ToolItem;
//
//import readwrite.ReadFromFile;
//import setting.ButtonsSetting;
//import setting.Images;
//import setting.Shorten;
//import setting.Title;
//import actions.Close;
//import actions.Discard;
//import actions.Send;
//import displays.MainForm;
//
//
//public class FormTemplates {
//	ButtonsSetting bs = new ButtonsSetting();
//	Shorten sh = new Shorten();
//	ReadFromFile rff = new ReadFromFile();
//
//	static Shell shell = new Shell();
//	static ToolItem toolSend,toolSendClear,toolSendAMO,toolSave,toolDiscard,toolClose;
//	
//	public static String ID="";
//	
//	Composite comp = null;
//	
//	public FormTemplates() {
//		
//	}
//	
//	public void newtemp(Shell pShell, String id) {
//		shell = pShell;
//		ID = id;
//		System.out.println("\nID template=" + ID);
//
//        rff.readConfiguration();
//        shell.setLayout(new FillLayout(SWT.VERTICAL));
//        shell.setSize(MainForm.getDx()-rff.getWidthTemp(), rff.getHeightTemp());
//        shell.setLocation(MainForm.setLocX(shell), MainForm.setLocY(shell));//shell.setLocation(rff.getxTemp(), rff.getyTemp());
//
//        SashForm form = new SashForm(shell,SWT.VERTICAL);
//    	form.setLayout(new FillLayout());
//    	
//        Composite ctool = new Composite(form, SWT.NONE); sh.composeStyle(ctool, 1, SWT.LEFT, SWT.CENTER);
//        ToolBar bar1 = new ToolBar (ctool, SWT.NONE | SWT.FLAT);
//        
//        ScrolledComposite c1 = new ScrolledComposite(form, SWT.NONE | SWT.H_SCROLL | SWT.V_SCROLL);
//        Composite composite = new Composite(c1, SWT.NONE);
//        composite.setLayout(new GridLayout(1, false));
//        
//toolSendClear = new ToolItem(bar1, 0); sh.toolitemStyle(toolSendClear, "", "Send and save the message, then clear the template", Images.img_SEND_CLEAR);
//toolSend = new ToolItem(bar1, 0); sh.toolitemStyle(toolSend, "", "Send and save the message", Images.img_SEND);
//toolSave = new ToolItem(bar1, 0); sh.toolitemStyle(toolSave, "", "Save message", Images.img_SAVE);
//toolDiscard = new ToolItem(bar1, 0); sh.toolitemStyle(toolDiscard, "", "Clear all items in this template", Images.img_DISCARD);
//if (ID.endsWith("AMO")) { toolSendAMO = new ToolItem(bar1, 0); sh.toolitemStyle(toolSendAMO, "Send AMO", "Send AMO", Images.img_SENDAMO); } 
//toolClose = new ToolItem(bar1, 0); sh.toolitemStyle(toolClose, "", "Close window", Images.img_CLOSE_WINDOW);

//        toolSendClear = new ToolItem(bar1, 0); sh.toolitemStyle(toolSendClear, "", "Send and save the message, then clear the template", Images.img_SEND_CLEAR);
//		toolSend = new ToolItem(bar1, 0); sh.toolitemStyle(toolSend, "", "Send and save the message", Images.img_SEND);
//		toolSave = new ToolItem(bar1, 0); sh.toolitemStyle(toolSave, "", "Save message", Images.img_SAVE);
//		toolDiscard = new ToolItem(bar1, 0); sh.toolitemStyle(toolDiscard, "", "Clear all items in selected template", Images.img_DISCARD);
////		if (ID.compareToIgnoreCase("newaftn")==0 || ID.compareToIgnoreCase("editaftn")==0 || ID.compareToIgnoreCase("outaftn")==0) { 
////			toolSendAMO = new ToolItem(bar1, 0); sh.toolitemStyle(toolSendAMO, "", "Send AMO", Images.img_SENDAMO); }
//		toolClose = new ToolItem(bar1, 0); sh.toolitemStyle(toolClose, "", "Close window", Images.img_CLOSE_WINDOW);
//		String title = "";
//		int iMinHeightTemp=0;
//	    
////		if (ID.startsWith("New")) title = "New ";
////		else if (ID.startsWith("Edit")) title = "Edit ";
////		else if (ID.startsWith("Out")) title = "Outbox ";
//		
//		if (ID.endsWith("AFTN")) {
//			addListener(ID); 
//			HeaderComposite.sendatAFTN(ctool);
//			HeaderComposite.headerAFTN(ctool);
//			title += Title.tAFTN; 
//			ATSForms.initNewAFTN(composite, ID); 
//			//iMinHeightTemp = rff.getFreeATS(); 
//		}
//		else if (ID.endsWith("ALR")) { 
//			addListener(ID); 
//			HeaderComposite.sendatALR(ctool); 
//			HeaderComposite.headerALR(ctool); 
//			title += Title.tALR; 
//			ATSForms.initNewALR(composite, ID); 
//			//iMinHeightTemp = rff.getALR(); 
//		}
//		else if (ID.endsWith("RCF")) { 
//			addListener(ID); 
//			HeaderComposite.sendatRCF(ctool); 
//			HeaderComposite.headerRCF(ctool);
//			title += Title.tRCF; 
//			ATSForms.initNewRCF(composite, ID); 
//			//iMinHeightTemp = rff.getRCF();
//		}
//		else if (ID.endsWith("FPL")) { 
//			addListener(ID); 
//			HeaderComposite.sendatFPL(ctool); 
//			HeaderComposite.headerFPL(ctool); 
//			title += Title.tFPL; 
//			ATSForms.initNewFPL(composite, ID); 
//			//iMinHeightTemp = rff.getFPL();
//		}
//		else if (ID.endsWith("DLA")) {
//			addListener(ID); 
//			HeaderComposite.sendatDLA(ctool);
//			HeaderComposite.headerDLA(ctool); 
//			title += Title.tDLA; 
//			ATSForms.initNewDLA(composite, ID);
//			//iMinHeightTemp = rff.getDLA();
//		}
//		else if (ID.endsWith("CHG")) { 
//			addListener(ID); 
//			HeaderComposite.sendatCHG(ctool);
//			HeaderComposite.headerCHG(ctool);
//			title += Title.tCHG; 
//			ATSForms.initNewCHG(composite, ID);
//			//iMinHeightTemp = rff.getCHG();
//		}
//		else if (ID.endsWith("CNL")) {
//			addListener(ID);
//			HeaderComposite.sendatCNL(ctool); 
//			HeaderComposite.headerCNL(ctool); 
//			title += Title.tCNL; 
//			ATSForms.initNewCNL(composite, ID);
//			//iMinHeightTemp = rff.getCNL();
//		}
//		else if (ID.endsWith("DEP")) { 
//			addListener(ID); 
//			HeaderComposite.sendatDEP(ctool); 
//			HeaderComposite.headerDEP(ctool);
//			title += Title.tDEP; 
//			ATSForms.initNewDEP(composite, ID); 
//			//iMinHeightTemp = rff.getDEP();
//		}
//		else if (ID.endsWith("ARR")) { 
//			addListener(ID); 
//			HeaderComposite.sendatARR(ctool); 
//			HeaderComposite.headerARR(ctool);
//			title += Title.tARR; 
//			ATSForms.initNewARR(composite, ID); 
//			//iMinHeightTemp = rff.getARR();
//		}
//		else if (ID.endsWith("CDN")) { 
//			addListener(ID); 
//			HeaderComposite.sendatCDN(ctool);
//			HeaderComposite.headerCDN(ctool); 
//			title += Title.tCDN;
//			ATSForms.initNewCDN(composite, ID); 
//			//iMinHeightTemp = rff.getCDN();
//		}
//		else if (ID.endsWith("CPL")) { 
//			addListener(ID);
//			HeaderComposite.sendatCPL(ctool); 
//			HeaderComposite.headerCPL(ctool); 
//			title += Title.tCPL; 
//			ATSForms.initNewCPL(composite, ID);
//			//iMinHeightTemp = rff.getCPL(); 	
//		}
//		else if (ID.endsWith("EST")) {
//			addListener(ID);
//			HeaderComposite.sendatEST(ctool); 
//			HeaderComposite.headerEST(ctool); 
//			title += Title.tEST; 
//			ATSForms.initNewEST(composite, ID);
//			//iMinHeightTemp = rff.getEST();	
//		}
//		else if (ID.endsWith("ACP")) { 
//			addListener(ID);
//			HeaderComposite.sendatACP(ctool);
//			HeaderComposite.headerACP(ctool); 
//			title += Title.tACP; 
//			ATSForms.initNewACP(composite, ID);
//			//iMinHeightTemp = rff.getACP(); 	
//		}
//		else if (ID.endsWith("LAM")) { 
//			addListener(ID); 
//			HeaderComposite.sendatLAM(ctool);
//			HeaderComposite.headerLAM(ctool); 
//			title += Title.tLAM;  
//			ATSForms.initNewLAM(composite, ID);
//			//iMinHeightTemp = rff.getLAM();	
//		}
//		else if (ID.endsWith("RQP")) {
//			addListener(ID); 
//			HeaderComposite.sendatRQP(ctool); 
//			HeaderComposite.headerRQP(ctool); 
//			title += Title.tRQP; 
//			ATSForms.initNewRQP(composite, ID);
//			//iMinHeightTemp = rff.getRQP(); 	
//		}
//		else if (ID.endsWith("RQS")) { 
//			addListener(ID); 
//			HeaderComposite.sendatRQS(ctool); 
//			HeaderComposite.headerRQS(ctool); 
//			title += Title.tRQS; 
//			ATSForms.initNewRQS(composite, ID); 
//			//iMinHeightTemp = rff.getRQS();	
//		}
//		else if (ID.endsWith("SPL")) { 
//			addListener(ID); 
//			HeaderComposite.sendatSPL(ctool); 
//			HeaderComposite.headerSPL(ctool); 
//			title += Title.tSPL; 
//			ATSForms.initNewSPL(composite, ID); 
//			//iMinHeightTemp = rff.getSPL();	
//		}
//
//	    title += " Message";
//	    
//	    composite.setSize((MainForm.getDx()-rff.getMinWidthTemp()),iMinHeightTemp);
//	    c1.setContent(composite);
//	    
////	    ctool.setBackground(Colors.MANDATORY);
////      bar1.setBackground(Colors.red);
////      c1.setBackground(Colors.red);
////	    composite.setBackground(Colors.DarkGrey);
//	    
//	    int iheader=0,ibody=0;
//	    iheader = 0;//rff.getHeaderHeight();
//	    if (ID.compareToIgnoreCase("newfpl")==0 || ID.compareToIgnoreCase("editfpl")==0 || ID.compareToIgnoreCase("outfpl")==0) {
//        	iheader = rff.getHeaderHeightFPL();
//        } else iheader = rff.getHeaderHeight();
//	    
////	    System.out.println("iheader=" + iheader + "#");
//	    
//	    ibody = 100 - iheader; 
//	    form.setWeights(new int[] {iheader, ibody});
//	    shell.setText(title);
//	    shell.open();
//	}
//	
//	void addListener(final String ID) {
//		
////		if (ID.compareToIgnoreCase("newaftn")==0 || ID.compareToIgnoreCase("editaftn")==0 || ID.compareToIgnoreCase("outaftn")==0) {
////			toolSendAMO.addSelectionListener(new SelectionListener() {
////				public void widgetSelected(SelectionEvent event) {
////					new Send(ID, "sendamo");
////				}
////	
////				public void widgetDefaultSelected(SelectionEvent event) {
////		 	     }
////			});
////		}
//		
//		toolSendClear.addSelectionListener(new SelectionListener() {
//			public void widgetSelected(SelectionEvent event) {
//				new Send(ID, "sendclear");
//			}
//
//			public void widgetDefaultSelected(SelectionEvent event) {
//	 	     }
//		});
//
//		toolSend.addSelectionListener(new SelectionListener() {
//			public void widgetSelected(SelectionEvent event) {
//				new Send(ID, "send");
//			}
//
//			public void widgetDefaultSelected(SelectionEvent event) {
//	 	     }
//		});
//
//		toolSave.addSelectionListener(new SelectionListener() {
//			public void widgetSelected(SelectionEvent event) {
//				new Send(ID, "save");
//			}
//
//			public void widgetDefaultSelected(SelectionEvent event) {
//	 	     }
//		});
//		
//		toolDiscard.addSelectionListener(new SelectionListener() {
//			public void widgetSelected(SelectionEvent event) {
//				new Discard(ID);
//			}
//
//			public void widgetDefaultSelected(SelectionEvent event) {
//	 	     }
//		});
//		
//		toolClose.addSelectionListener(new SelectionListener() {
//			public void widgetSelected(SelectionEvent event) {
//				new Close(ID);
//			}
//
//			public void widgetDefaultSelected(SelectionEvent event) {
//	 	     }
//		});
//	}
//}
