package displays;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.jfree.chart.demo.jdbc;


import readwrite.ReadFromFile;
import readwrite.WriteToDOC;
import readwrite.WriteToDOCX;
import readwrite.WriteToPDF;
import readwrite.WriteToXLS;
import readwrite.WriteToXLS2;
import setting.Criteria;
import setting.Images;
import setting.Shorten;
import setting.Titles;
import actions.Jdbc;
import actions.RuntimeCmd;
import actions.ViewATS;
import actions.ViewMETEO;
import actions.ViewNOTAM;
import actions.cProgressBar;

public class Lists {
	public static TableItem item=null,iin=null;//,iout=null;
	
	cProgressBar progbr;
	static MenuItem miPDF,miDOC,miXLS,miPDF_part,miDOC_part,miXLS_part;
	
	String sTrace="";
	
	public static java.util.List players;
	public static PlayerComparator comparator;
	
	static Shorten sh = new Shorten();
	Criteria cAts = new Criteria();
	ViewATS vATS = new ViewATS();
	ViewNOTAM vNOTAM = new ViewNOTAM();
	ViewMETEO vMETEO = new ViewMETEO();
	ReadFromFile rff = new ReadFromFile();
	
	// Database connection
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs[];
	
	static Connection connExport = null;
	static Statement stmtExport = null;
	static ResultSet rsExport[];
	
	static Connection connDelAll = null;
	static Statement stmtDelAll = null;
	static ResultSet rsDelAll[];
	
	// Components
	TableItem[] selection;
	String iJmlDataExport="";
	
	public static Shell shlPass = new Shell(AmscSplashScreen2.display, SWT.NO_TRIM);
	public static Shell shlReject = new Shell(AmscSplashScreen2.display, SWT.TITLE);
	public static Shell shlRecord = new Shell(AmscSplashScreen2.display, SWT.TITLE);
	public static Group Group;
	public static Table table;
	static TableColumn[] cols = null; 
	static TableItem iout;
	static MenuItem miExport,miExport_part,miDelete,miDeleteAll;
	static Label label,lTextual,lTrace;
	public static Label lblGoto,lbJmlhal,lbJmldt;
	public static Text tGotoPage,tTextual,tTrace;
	static Button bFirst,bPrevious,bNext,bLast,bPDF,bXLS,bDOC,bLimit;
	
	static String printable="",printableXLS="",line="",printableRet="",printableXLSRet="",
	sfree="",salr="",srcf="",sfpl="",sdla="",schg="",scnl="",sdep="",sarr="",scdn="",scpl="",sest="",sacp="",slam="",srqp="",srqs="",sspl="";
	static String sJmlData="",sHalKe="",jmlHal="";
	public static String tblName="";
	static String yearFr="", monthFr="", dayFr="", yearTo="", monthTo="", dayTo="";
	static String sQueryFind="",sCriteria="",sTblNm="",sIdName="",sIdVal="",string="",sID="",sFlnm="",msgType="";
	public static String rej_tbl="",rej_id="",rej_cid="",rej_csn="",rej_tms="",rej_tgl="",rej_typ="",rej_err="",rej_msg="";
	static String tgl="",tglTo="",pDate="",pDateTo="";

	public static int iJmlData=0,iMaxExport=0;
	static int iLimitRow=0,iOfRow=0,qq=0,rowNo=0,qu=0,dir=0,p=0,yu=0,iCnt=0,icount=0,allicount=0;; 

	public static String cmdPDF="",cmdXLS="",cmdDOC="";
	
	String msg="",str0="",str1="",str2="",str3="",str4="",str5="",str6="",str7="",str8="";
	
	static String strCID="",strSEQFR="",strSEQTO="",strTMS="";
	
	
	public Lists() {}
	

	
	public Lists(String strTblNm,String strIdName,String strID,String strCriteria,String strpDate,String strpDateTo,
			String pcid,String pseqfr,String pseqto,String ptms) {

		strCID=pcid;
		strSEQFR=pseqfr;
		strSEQTO=pseqto;
		strTMS=ptms;
		
		new Lists(strTblNm,strIdName,strID,strCriteria,strpDate,strpDateTo);
	}
	
	public Lists(String strTblNm,String strIdName,String strID,String strCriteria,String strpDate,String strpDateTo) {
		
		rff.readConfiguration();
		
		cmdPDF=rff.getCmdPDF()+" ";
		cmdXLS=rff.getCmdXLS()+" ";
		cmdDOC=rff.getCmdDOC()+" ";
		
		String sRowLimit = rff.ReadInputFile1(MainForm.sFolder + "rowlimit.txt");
		if (sRowLimit==null || sRowLimit.isEmpty() || sRowLimit.compareTo("")==0) { sRowLimit="10"; }
		iLimitRow = Integer.parseInt(sRowLimit);
		
		String sMaxExport = rff.ReadInputFile1(MainForm.sFolder + "maxexport.txt");
		if (sMaxExport==null || sMaxExport.isEmpty() || sMaxExport.compareTo("")==0) { sMaxExport="100"; }
		iMaxExport = Integer.parseInt(sMaxExport);
		
		sTblNm=strTblNm;
		sIdName=strIdName;
		sID=strID;
		sCriteria=strCriteria;
		pDate=strpDate;
		pDateTo=strpDateTo;

		//date now
		cAts.tgl();
		String sTanggal = cAts.tg.substring(0, 10);
		
		//part1: FROM terisi [default: FROM terisi]
		if (pDate.compareTo("0000-00-00 00:00") != 0) { //tampilkan data dari FROM 
			cAts.Date(pDate);
			tgl=" AND tgl>='"+cAts.Date+"'";
			//ambil isian TEXT From 
			if (cAts.Date.length()>=4) yearFr = cAts.Date.substring(0,4); else yearFr=sTanggal.substring(0,4);
			if (cAts.Date.length()>=7) monthFr = cAts.Date.substring(5,7); else monthFr="01";
			if (cAts.Date.length()>=10) dayFr = cAts.Date.substring(8,10); else dayFr="01";
			
			//part1: TO kosong [default: TO kosong] - ambil YYYY & MM dari yearMonth
			if (pDateTo.compareTo("0000-00-00 00:00") == 0) { //tampilkan data sampai TO datetime sekarang
				yearTo=sTanggal.substring(0,4);
				monthTo=sTanggal.substring(5,7);
				dayTo=sTanggal.substring(8, 10); //if (sTanggal.length()>=10) dayTo=sTanggal.substring(8, 10); else dayTo="31";

				cAts.DateTo(yearTo+"-"+monthTo+"-"+dayTo+cAts.tanggal.substring(10, cAts.tanggal.length()));
				tglTo=" AND tgl<='"+cAts.Date+"'";
			}
		}
		
		//part2: TO terisi [default: TO kosong]
		if (pDateTo.compareTo("0000-00-00 00:00") != 0) { //tampilkan data sampai TO datetime sekarang
			cAts.DateTo(pDateTo);
			tglTo=" AND tgl<='"+cAts.Date+"'";
			//ambil isian TEXT To
			if (cAts.Date.length()>=4) yearTo = cAts.Date.substring(0,4); else yearTo=sTanggal.substring(0,4);
			if (cAts.Date.length()>=7) monthTo = cAts.Date.substring(5,7); else monthTo="12"; 
			if (cAts.Date.length()>=10) dayTo = cAts.Date.substring(8,10); else dayTo="31";
			
			//part2: FROM kosong [default: FROM terisi] - ambil YYYY dari TO & MM pertama
			String yeartoo=yearTo;
			if (pDate.compareTo("0000-00-00 00:00") == 0) { //tampilkan data dari FROM bulan Januari tahun TO
				yearFr=yeartoo;
				monthFr="01";
				dayFr="01";
				tgl=" AND tgl>='"+yeartoo+"-01-01 00:00:00"+"'";
			}
		}
		
		//part3: FROM & TO kosong
		if (pDate.compareTo("0000-00-00 00:00") == 0 && pDateTo.compareTo("0000-00-00 00:00") == 0) { //tampilkan data hari ini
			tgl=" AND tgl>='"+cAts.tg+" 00:00:00'"; 
			tglTo=" AND tgl<='"+cAts.tg+" 23:59:59'";
			yearFr=yearTo=sTanggal.substring(0,4);
			monthFr=monthTo=sTanggal.substring(5,7); 
			dayFr=dayTo=sTanggal.substring(8,10);//if (cAts.Date.length()>=10) dayFr=dayTo=cAts.Date.substring(8, 10);;
		}
		
//		System.out.println("FR="+yearFr+"-"+monthFr+"-"+dayFr+"#\tTO="+yearTo+"-"+monthTo+"-"+dayTo+"#");
		sCriteria=tgl+tglTo+sCriteria;
	}  
	
	void compList(Shell shell) {
		Group = new Group(shell, SWT.NONE); sh.groupStyle(Group, 1, "List");
		Composite compTOP = new Composite(Group, SWT.NONE); sh.composeStyle(compTOP, 15);
		Composite compMID = new Composite(Group, SWT.NONE); sh.composeStyle(compMID, 1);
		Composite compBOT = new Composite(Group, SWT.NONE); sh.composeStyle(compBOT, 2);

		int iWidthBtn=40;
		bFirst = new Button(compTOP, SWT.PUSH); sh.buttonStyle(bFirst, "<<", "First Page", sh.widthNavBtn, sh.DarkGrey, SWT.CENTER, SWT.CENTER, null);
	    bPrevious = new Button(compTOP, SWT.PUSH); sh.buttonStyle(bPrevious, "<", "Previous Page", sh.widthNavBtn, sh.DarkGrey, SWT.CENTER, SWT.CENTER, null);
	    bNext = new Button(compTOP, SWT.PUSH); sh.buttonStyle(bNext, ">", "Next Page", sh.widthNavBtn, sh.DarkGrey, SWT.CENTER, SWT.CENTER, null);
	    bLast = new Button(compTOP, SWT.PUSH); sh.buttonStyle(bLast, ">>", "Last Page", sh.widthNavBtn, sh.DarkGrey, SWT.CENTER, SWT.CENTER, null);
	    Label label = new Label(compTOP, SWT.CENTER); 
	    lblGoto = new Label(compTOP, SWT.RIGHT); sh.labelStyle1(lblGoto, "Go to Page", SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
	    tGotoPage = new Text(compTOP, SWT.BORDER); sh.textStyle(tGotoPage, 50, 5, SWT.CENTER, SWT.CENTER, sh.numeric, "Go to Page", true);
	    label = new Label(compTOP, SWT.CENTER); 
	    lbJmlhal = new Label(compTOP, SWT.LEFT); 
	    label = new Label(compTOP, SWT.NONE); sh.labelStyle0(label, "", sh.BLACK);
	    lbJmldt = new Label(compTOP, SWT.RIGHT); 
	    bLimit = new Button(compTOP, SWT.PUSH); sh.buttonStyle(bLimit, "", "Limit Record Setting", iWidthBtn, sh.DarkGrey, SWT.CENTER, SWT.LEFT, Images.imgLimit16);
	    bPDF = new Button(compTOP, SWT.PUSH); sh.buttonStyle(bPDF, "", "Export to PDF file", iWidthBtn, sh.DarkGrey, SWT.CENTER, SWT.LEFT, Images.imgPDF32);
	    bXLS = new Button(compTOP, SWT.PUSH); sh.buttonStyle(bXLS, "", "Export to XLS file", iWidthBtn, sh.DarkGrey, SWT.CENTER, SWT.LEFT, Images.imgXLS32);
	    bDOC = new Button(compTOP, SWT.PUSH); sh.buttonStyle(bDOC, "", "Export to DOC file", iWidthBtn, sh.DarkGrey, SWT.CENTER, SWT.LEFT, Images.imgDOC32);

	    table = new Table (compMID, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION); sh.tableStyle(table, true, true, 250);
	    
	    lTextual = new Label(compBOT, SWT.LEFT); sh.labelStyle0(lTextual, "Textual Message", sh.BLACK); 
	    lTrace = new Label(compBOT, SWT.RIGHT); sh.labelStyle0(lTrace, "Trace Message", sh.BLACK); 
	    tTextual = new Text(compBOT, SWT.BORDER | SWT.WRAP | SWT.MULTI | SWT.V_SCROLL); sh.textAreaStyle(tTextual, 150, "Textual Message");
	    tTextual.setBackground(sh.Grey);
	    tTextual.setEditable(false);
	    tTrace = new Text(compBOT, SWT.BORDER | SWT.WRAP | SWT.MULTI | SWT.V_SCROLL); sh.textAreaStyle(tTrace, 150, "Trace Message");
	    tTrace.setBackground(sh.Grey);
	    tTrace.setEditable(false);
	    
	    setInfoRowZero();//percobaan
	    popupMenu();
	    activatePopup(false,"");
	    ListListener();
		
	}
	
	void sTblName() {
		//ATS Messages & AIDC
		if (sTblNm.startsWith("air_message_free_text_meteo")) sTblNm="air_message_free_text_meteo";
		else if (sTblNm.startsWith("air_message_free_text")) sTblNm="air_message_free_text";
		else if (sTblNm.startsWith("air_message")) sTblNm="air_message";
		//NOTAM
		else if (sTblNm.startsWith("notam_multi")) sTblNm="notam_multi";
		else if (sTblNm.startsWith("snowtam")) sTblNm="snowtam";
		else if (sTblNm.startsWith("ashtam")) sTblNm="ashtam";
		else if (sTblNm.startsWith("birdtam")) sTblNm="birdtam";
		else if (sTblNm.startsWith("rqn")) sTblNm="rqn";
		else if (sTblNm.startsWith("rql")) sTblNm="rql";
		//METEO
		else if (sTblNm.startsWith("meteo_metar")) sTblNm="meteo_metar";
		else if (sTblNm.startsWith("meteo_speci")) sTblNm="meteo_speci";
		else if (sTblNm.startsWith("meteo_sigmet")) sTblNm="meteo_sigmet";
		else if (sTblNm.startsWith("meteo_airmet")) sTblNm="meteo_airmet";
		else if (sTblNm.startsWith("meteo_airep")) sTblNm="meteo_airep";
		else if (sTblNm.startsWith("meteo_tafor")) sTblNm="meteo_tafor";
		else if (sTblNm.startsWith("meteo_synop")) sTblNm="meteo_synop";
		else if (sTblNm.startsWith("meteo_arfor")) sTblNm="meteo_arfor";
		else if (sTblNm.startsWith("meteo_rofor")) sTblNm="meteo_rofor";
		else if (sTblNm.startsWith("meteo_wins_war")) sTblNm="meteo_wins_war";
		else if (sTblNm.startsWith("meteo_wintem")) sTblNm="meteo_wintem";
		else if (sTblNm.startsWith("vulcano_adv")) sTblNm="vulcano_adv";
		else if (sTblNm.startsWith("volcanic_act")) sTblNm="volcanic_act";
		else if (sTblNm.startsWith("rqm")) sTblNm="rqm";
		else if (sTblNm.startsWith("meteo_wsynop")) sTblNm="meteo_wsynop";
		//Retrieve
		else if (sTblNm.startsWith("aftnrdI")) sTblNm="aftnrdI";
		else if (sTblNm.startsWith("aftnrdO")) sTblNm="aftnrdO";
		//Reject
		else if (sTblNm.startsWith("reject_message_notam")) sTblNm="reject_message_notam";
		else if (sTblNm.startsWith("reject_message_ats")) sTblNm="reject_message_ats";
		else if (sTblNm.startsWith("reject_message")) sTblNm="reject_message";
	}
	
	public static void activatePopup(boolean b,String tab) {
		bPDF.setEnabled(b);
		bDOC.setEnabled(b);
		bXLS.setEnabled(b);
		
		miExport.setEnabled(b);
		miExport_part.setEnabled(b);
		miDelete.setEnabled(b);
		miDeleteAll.setEnabled(b);
		if (b==true && sID.compareTo(Titles.stReject)!=0) {
			miDelete.setEnabled(false);
			miDeleteAll.setEnabled(false);	
		}
	}
	
	void pdf_all() {
		if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
			DialogFactory.openYesNoExportTrace();
    	    int rc = DialogFactory.getRC();
    	    if (rc==1) { createPDF("trace"); } 
    	    else if (rc==0) { createPDF("notrace"); }
    	    else if (rc==2) { System.out.println("nothing");}
		} else createPDF(""); 
	}

	void pdf_part() {
		if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
			DialogFactory.openYesNoExportTrace();
    	    int rc = DialogFactory.getRC();
    	    if (rc==1) { createPDF_part("trace"); } 
    	    else if (rc==0) { createPDF_part("notrace"); }
    	    else if (rc==2) { System.out.println("nothing");}
		} else createPDF_part("");
	}
	
	void doc_all() {
		if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
			DialogFactory.openYesNoExportTrace();
    	    int rc = DialogFactory.getRC();
    	    if (rc==1) { createDOC("trace"); } 
    	    else if (rc==0) { createDOC("notrace"); }
    	    else if (rc==2) { System.out.println("nothing");}
		} else createDOC("");
	}

	void doc_part() {
		if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
			DialogFactory.openYesNoExportTrace();
    	    int rc = DialogFactory.getRC();
    	    if (rc==1) { createDOC_part("trace"); } 
    	    else if (rc==0) { createDOC_part("notrace"); }
    	    else if (rc==2) { System.out.println("nothing");}
		} else createDOC_part(""); 
	}
	
	void xls_all() {
		if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
			DialogFactory.openYesNoExportTrace();
    	    int rc = DialogFactory.getRC();
    	    if (rc==1) { createXLS("trace"); } 
    	    else if (rc==0) { createXLS("notrace"); }
    	    else if (rc==2) { System.out.println("nothing");}
		} else createXLS("");	
	}

	void xls_part() {
		if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
			DialogFactory.openYesNoExportTrace();
    	    int rc = DialogFactory.getRC();
    	    if (rc==1) { createXLS_part("trace"); } 
    	    else if (rc==0) { createXLS_part("notrace"); }
    	    else if (rc==2) { System.out.println("nothing");}
		} else createXLS_part(""); 
	}
	
	void popupMenu() {
		Menu popupMenu = new Menu(table);
		
		miExport = new MenuItem(popupMenu, SWT.CASCADE); miExport.setText("Export all to file ");
	    Menu menuExport = new Menu(popupMenu); miExport.setMenu(menuExport);
	    miPDF = new MenuItem(menuExport, SWT.NONE); miPDF.setText("PDF");
	    miXLS = new MenuItem(menuExport, SWT.NONE); miXLS.setText("Excel");
	    miDOC = new MenuItem(menuExport, SWT.NONE); miDOC.setText("Word");
	    
	    miExport_part = new MenuItem(popupMenu, SWT.CASCADE); miExport_part.setText("Export selected data(s) to file ");
	    Menu menuExport_part = new Menu(popupMenu); miExport_part.setMenu(menuExport_part);
	    miPDF_part = new MenuItem(menuExport_part, SWT.NONE); miPDF_part.setText("PDF");
	    miXLS_part = new MenuItem(menuExport_part, SWT.NONE); miXLS_part.setText("Excel");
	    miDOC_part = new MenuItem(menuExport_part, SWT.NONE); miDOC_part.setText("Word");
	    
	    miDelete = new MenuItem(popupMenu, SWT.NONE); miDelete.setText("Delete");
	    miDeleteAll = new MenuItem(popupMenu, SWT.NONE); miDeleteAll.setText("Delete all");
	    
	    table.setMenu(popupMenu);
	    
	    bLimit.addSelectionListener(new SelectionAdapter() { public void widgetSelected(SelectionEvent e) { 
	    	if (shlRecord.isDisposed()) { shlRecord = new Shell(AmscSplashScreen2.display, SWT.DIALOG_TRIM); }
			if (!shlRecord.isVisible()) { new RecordSetting(shlRecord); } 
			else {
				shlRecord.close();
				shlRecord = new Shell(AmscSplashScreen2.display, SWT.DIALOG_TRIM);
				new RecordSetting(shlRecord);
			}
	    } });
	    
	    miPDF.addSelectionListener(new SelectionAdapter() { public void widgetSelected(SelectionEvent e) { pdf_all(); } });
	    miPDF_part.addSelectionListener(new SelectionAdapter() { public void widgetSelected(SelectionEvent e) { pdf_part(); } });
	    bPDF.addSelectionListener(new SelectionAdapter() { 
	    	public void widgetSelected(SelectionEvent e) {
	    		TableItem[] selection = table.getSelection(); 
				if (selection.length == 0) { pdf_all(); } 
				else { pdf_part(); }
	    	} 
	    });
	    
	    miDOC.addSelectionListener(new SelectionAdapter() { public void widgetSelected(SelectionEvent e) { doc_all(); } });
	    miDOC_part.addSelectionListener(new SelectionAdapter() { public void widgetSelected(SelectionEvent e) { doc_part(); } });
	    bDOC.addSelectionListener(new SelectionAdapter() { 
	    	public void widgetSelected(SelectionEvent e) {
	    		TableItem[] selection = table.getSelection(); 
				if (selection.length == 0) { doc_all(); } 
				else { doc_part(); }
	    	} 
	    });
	    
	    miXLS.addSelectionListener(new SelectionAdapter() { public void widgetSelected(SelectionEvent e) { xls_all(); } });
	    miXLS_part.addSelectionListener(new SelectionAdapter() { public void widgetSelected(SelectionEvent e) { xls_part(); } });
	    bXLS.addSelectionListener(new SelectionAdapter() { 
	    	public void widgetSelected(SelectionEvent e) {
	    		TableItem[] selection = table.getSelection(); 
				if (selection.length == 0) { xls_all(); } 
				else { xls_part(); }
	    	} 
	    });
	    
	    miDelete.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				TableItem[] selection = table.getSelection(); 
				if (selection.length == 0) {
					DialogFactory.openInfoAtLeast(DialogFactory.delete);
				} else {
//					String sIdVal="";
					sTblNm="";
					
					DialogFactory.openYesNoDelete();
		    	    boolean rc = DialogFactory.getPenentuan();
		    	    if (rc==true) {
		    	    	
		    	    	if (!shlPass.isDisposed()) shlPass.close();
		    			shlPass = new Shell(AmscSplashScreen2.display, SWT.NO_TRIM);
		    			OpenPassword pass = new OpenPassword();
		    			if (pass.open(shlPass)) {
		    				if (!shlPass.isDisposed()) shlPass.close();
		    				shlPass = new Shell(AmscSplashScreen2.display,SWT.MIN);
		    				
		    				//sReject=tblnm,id,CID,CSN,TMS,Date/Time,Type,Error Message,Message
		    				for (int i=0; i<selection.length; i++) { //banyak baris yang akan dihapus
								sTblNm = selection[i].getText(0);
//								sIdVal = selection[i].getText(1);
								
								String tgl_pendek=selection[i].getText(5).substring(0,16);
								
								String sQuery = "DELETE FROM "+sTblNm+" WHERE " +
									"tbl_name='"+selection[i].getText(0)+"' AND "+
//									"id_rjt_msg='"+selection[i].getText(1)+"' AND "+
									"cid='"+selection[i].getText(2)+"' AND "+
									"seq='"+selection[i].getText(3)+"' AND "+
//									"tms='"+selection[i].getText(4)+"' AND "+
//									"tgl='"+selection[i].getText(5)+"' AND "+
									"tgl LIKE '"+tgl_pendek+"%' AND "+
									"type_message='"+selection[i].getText(6)+"' AND "+
									"free_text_error_message='"+selection[i].getText(7)+"' AND "+
									"origin_message LIKE '"+selection[i].getText(8)+"%'";
								Jdbc.select(sQuery, "REJMSG");
								
//								String sQuery = "DELETE FROM "+sTblNm+" WHERE "+sIdName+"="+sIdVal;
//								Jdbc.select(sQuery, "");
							}
		    				System.out.println("**delete:succeed#");
	    				
		    				//refreshing table
		    				table.setRedraw(false);
		    				table.removeAll();
		    				sTblName();
		    				Find();
		    				table.setRedraw(true);
		    			}
	    				
	    				if (!tTextual.getText().isEmpty()) tTextual.setText(""); 
	    				if (iJmlData==0) Lists.activatePopup(false, sID);
	    				else if (iJmlData!=0) Lists.activatePopup(true, sID);
	    				
		    	    }  else System.out.println("**delete:cancelled#");
				} //end of else rc
			}
		});
	    
	    miDeleteAll.addSelectionListener(new SelectionAdapter() {
	    	public void widgetSelected(SelectionEvent e) {
	    		DialogFactory.openYesNoDelete();
	    	    boolean rc = DialogFactory.getPenentuan();
	    	    if (rc==true) {
	    	    	if (!shlPass.isDisposed()) shlPass.close();
	    			shlPass = new Shell(AmscSplashScreen2.display, SWT.NO_TRIM);
	    			OpenPassword pass = new OpenPassword();
	    			if (pass.open(shlPass)) {
	    				if (!shlPass.isDisposed()) shlPass.close();
	    				shlPass = new Shell(AmscSplashScreen2.display,SWT.MIN);
	    				
			    		System.out.println("\nServer A");
						new ReadFromFile().readDBRej(MainForm.sFolder+"ipA.txt");
						//STEP 3: Open a connection
						System.out.println("--URL:" + ConnectTo.DB_URL_REJ + "--");
						deleteall(ConnectTo.DB_URL_REJ,ConnectTo.DB_USER_REJ,ConnectTo.DB_PASS_REJ);
						
						System.out.println("\nServer B");
						new ReadFromFile().readDBRej(MainForm.sFolder+"ipB.txt"); 
						//STEP 3: Open a connection
						System.out.println("--URL:" + ConnectTo.DB_URL_REJ + "--");
						deleteall(ConnectTo.DB_URL_REJ,ConnectTo.DB_USER_REJ,ConnectTo.DB_PASS_REJ);
	    			}
	    			
	    			if (!tTextual.getText().isEmpty()) tTextual.setText("");
	    			if (iJmlData==0) Lists.activatePopup(false, sID);
    				else if (iJmlData!=0) Lists.activatePopup(true, sID);
	    			
	    	    } else System.out.println("**delete:cancelled#");	
	    	}
	    });
	}
	
	void deleteall(String dbname,String dbuser,String dbpass) {
		try {
			//STEP 1: Register JDBC driver
			Class.forName(ConnectTo.JDBC_DRIVER);

			//STEP 2: Open a connection
			connDelAll = DriverManager.getConnection(dbname,dbuser,dbpass);
			
			final cProgressBar progbr = new cProgressBar("Selecting data ...");
			new Thread() {
				public void run() {
					if (AmscSplashScreen2.display.isDisposed()) return;
					AmscSplashScreen2.display.asyncExec(new Runnable() {
						public void run() {
							try {
								progbr.create(iLimitRow);
							    // Search criteria
							    String select = "DELETE FROM ";
								String where = " WHERE tgl!='0000-00-00 00:00:00'";
								
								iJmlData=0; //inisialisasi
								iCnt=0;
								rowNo=0;
								rsDelAll = new ResultSet[Integer.parseInt(yearTo)*Integer.parseInt(monthTo)];
								boolean flg=false;
								
								for (int iYear=Integer.parseInt(yearFr); iYear<=Integer.parseInt(yearTo); iYear++) {
									int iMtemp=0,n=0,nn=0,iMonth=0;
									
									if (iYear==Integer.parseInt(yearFr)) { 
										iMtemp=Integer.parseInt(monthFr); n=12-iMtemp; } 
									else if ((iYear>Integer.parseInt(yearFr)) && (iYear < Integer.parseInt(yearTo))) {
										iMtemp=1; n=12-iMtemp; } 
									else if (iYear == Integer.parseInt(yearTo)) {
										iMtemp= Integer.parseInt(monthTo); n=iMtemp; iMtemp=0; flg=true; }
								
									for (iMonth=iMtemp; iMonth<=n+iMtemp; iMonth++) {
										if ((flg) && (iMonth>=12)) { nn++; if ((n==iMtemp) && (nn>12)) break; } 
										else nn=iMonth;
										
										String sMonth = Integer.toString(nn);
										if (sMonth.length()==1) sMonth = sMonth.replace(sMonth, "0"+sMonth);
										tblName=sTblNm+iYear+"_"+sMonth;
										
										//STEP 3: Execute a query
										stmtDelAll = connDelAll.createStatement();
										
										try { //biar kalo ada table yang doesn't exist ga mati.
											String sQuery = select+tblName+where+sCriteria;//+order;
											System.out.println("Query:" + sQuery + "#");
											stmtDelAll.execute(sQuery);
										} catch (SQLException s) { // biar kalo ada table yang doesn't exist ga mati.
											System.out.println("**Info:" + s.getMessage() + "#");
										}
										iCnt++; //counter rs
									} //end of for2
								} //end of for1
								progbr.finish();
								
							} catch (SQLException s) {
								DialogFactory.openInfo(s.toString());
								s.printStackTrace();
								progbr.finish();
							} catch (java.lang.OutOfMemoryError hs) {
								DialogFactory.openInfo(DialogFactory.LIST_INFO_OUT_OF_MEMORY);
								progbr.finish();
							}
						}
					});
				}
			}.start();
			progbr.begin();
		
			//STEP 5: Clean-up environment
		    stmtDelAll.close();
		    connDelAll.close();
	    
		} catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		} catch(Exception xe){
			//Handle errors for Class.forName
			xe.printStackTrace();
		}
		
		System.out.println("**delete "+dbname+":succeed#"); 
		//refreshing table
		table.setRedraw(false);
		table.removeAll();
		table.setRedraw(true);
		setInfoRowZero();
	}
	
	public void createPDF(String trace) {
		printable=""; printableXLS=""; printableRet=""; printableXLSRet="";
		sTblName();
		exportToFile("pdf",trace);
		printable=""; printableXLS=""; printableRet=""; printableXLSRet="";
	}
	
	void createDOC(String trace) {
		printable=""; printableXLS=""; printableRet=""; printableXLSRet="";
		sTblName();
		exportToFile("docx",trace);
		printable=""; printableXLS=""; printableRet=""; printableXLSRet="";
	}
	
	void createXLS(String trace) {
		printable=""; printableXLS=""; printableRet=""; printableXLSRet="";
		sTblName();
		exportToFile("xls",trace);
		printable=""; printableXLS=""; printableRet=""; printableXLSRet="";
	}
	
	public void createPDF_part(String trace) {
		printable=""; printableXLS=""; printableRet=""; printableXLSRet="";
		sTblName();
		doAction(table.getSelection(), "export_part", "pdf", trace); 
		printable=""; printableXLS=""; printableRet=""; printableXLSRet="";
	}
	
	void createDOC_part(String trace) {
		printable=""; printableXLS=""; printableRet=""; printableXLSRet="";
		sTblName();
		doAction(table.getSelection(), "export_part", "docx", trace); 
		printable=""; printableXLS=""; printableRet=""; printableXLSRet="";
	}
	
	void createXLS_part(String trace) {
		printable=""; printableXLS=""; printableRet=""; printableXLSRet="";
		sTblName();
		doAction(table.getSelection(), "export_part", "xls", trace); 
		printable=""; printableXLS=""; printableRet=""; printableXLSRet="";
	}
	
	
	
	
	// Paginating, Table Info
	void getiOfRow(int as) {
		if ((iLimitRow * as)<iJmlData) { iOfRow=iLimitRow; } 
		else { iOfRow=(iJmlData-(iLimitRow * (as-1))); }
	}
	
	void ListListener() {
		enabledPagination(false);
		
		bFirst.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bFirst.getEnabled() == true) { bFirst.setEnabled(false); bPrevious.setEnabled(false); bNext.setEnabled(true);
					if (bLast.getEnabled() == false) bLast.setEnabled(true); }
				
				table.removeAll();
				qq=0;
				sHalKe=Integer.toString(qq);
				sHalKe="1";
				Next();
				getiOfRow(qq);
				setInfoRow(); } });
		
		bPrevious.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bPrevious.getEnabled() == true) { bNext.setEnabled(true); bFirst.setEnabled(true); bLast.setEnabled(true); }
				
				qq--;
				if (qq==0) { bPrevious.setEnabled(false); bFirst.setEnabled(false); }
				if (qq<0) { qq=0; } 
				else {
					table.removeAll();
					int as=Integer.parseInt(sHalKe)-1; 
					sHalKe=Integer.toString(as);
					
					Next();	
					getiOfRow(as);
					setInfoRow(); } } });
		
		bNext.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				bPrevious.setEnabled(true); bFirst.setEnabled(true);
				qq++;
				
				int iJmlHal;
				if (iJmlData%iLimitRow==0) iJmlHal=iJmlData/iLimitRow;
				else iJmlHal=iJmlData/iLimitRow+1;
				jmlHal=Integer.toString(iJmlHal);
				
				int all = iJmlHal;
				if (all-1 == qq) { bNext.setEnabled(false); bLast.setEnabled(false); }
				if (qq < iJmlHal) {
					table.removeAll();
					int as=qq+1;
					sHalKe=Integer.toString(as);
					Next();
					getiOfRow(as);
					setInfoRow(); } } });
						
		bLast.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				bNext.setEnabled(false); bPrevious.setEnabled(true); bFirst.setEnabled(true);
				table.removeAll();
				if (iJmlData%iLimitRow==0) qq=iJmlData/iLimitRow-1;
				else qq=iJmlData/iLimitRow;
				sHalKe=Integer.toString((qq+1));
				
				Next();
				bLast.setEnabled(false);
				getiOfRow(Integer.parseInt(sHalKe));
				setInfoRow(); } });	
		
		tGotoPage.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					String sGoToHal = tGotoPage.getText();
	                if ((tGotoPage.getText().equals("")) || (tGotoPage.getText().equals("0"))) {
	                    sGoToHal = "1";
	                    tGotoPage.setText("1"); }
	                
	                int m = Integer.parseInt(sGoToHal);
	                int nHasil = Integer.parseInt(jmlHal)-1;
	                m-=1;

	                if (m == 0) {
	                    enabledPagination(false);
	                    bNext.setEnabled(true);
	                    bLast.setEnabled(true);
	                }
	                if (m == nHasil) {
	                	enabledPagination(false);
	                	bFirst.setEnabled(true);
	                	bPrevious.setEnabled(true);
	                }
	                if ((m != 0) && (m != nHasil)) { enabledPagination(true); }
	                if (m > nHasil) {
	                    m = nHasil;
	                    enabledPagination(false);
	                    bFirst.setEnabled(true);
	                	bPrevious.setEnabled(true);
	                    tGotoPage.setText(jmlHal);
	                }

	                m = m-1;
	                qq = m;
	                qq++;

	                table.removeAll();
	                int as=qq+1;
	                sHalKe=Integer.toString(as);
	                Next();
					getiOfRow(Integer.parseInt(sHalKe));
					setInfoRow(); } } });
		
//		table.addSelectionListener( new SelectionAdapter() {
//		    public void widgetDefaultSelected(SelectionEvent e){
//		    	table.setSelection(0);
//		    	doAction(table.getSelection()) ;
//		    }
//		}) ;
		
		table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				doAction(table.getSelection(),"","","");
			}
		});

		table.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (sID.compareTo(Titles.stReject)==0) openEdit(table.getSelection());
			}
		});
	}
	
	void openEdit(TableItem[] selection) {
		for (int i=0; i<selection.length; i++) {
			//tblnm,id,CID,CSN,TMS,Date/Time,Type,Error Message,Message
			tblName = selection[i].getText(0);
			sIdVal = selection[i].getText(1);
			
			rej_tbl = selection[i].getText(0);
			rej_id = selection[i].getText(1);
			rej_cid = selection[i].getText(2);
			rej_csn = selection[i].getText(3);
			rej_tms = selection[i].getText(4);
			rej_tgl = selection[i].getText(5);
			rej_typ = selection[i].getText(6);
			rej_err = selection[i].getText(7);
			rej_msg = selection[i].getText(8);
			
			string+=sIdVal+",";
			
			String sel = "SELECT * FROM "+tblName+" WHERE "+sIdName+"="+sIdVal;
			Jdbc.select(sel, sID);
		}
		
		if (shlReject.isDisposed()) {
			shlReject = new Shell(AmscSplashScreen2.display, SWT.DIALOG_TRIM);
		}
		if (!shlReject.isVisible()) {
			new RejectForm(shlReject);
		} else {
			shlReject.close();
			shlReject = new Shell(AmscSplashScreen2.display, SWT.DIALOG_TRIM);
			new RejectForm(shlReject);
		}
	}
	
//	void doAction(TableItem[] selection) {
	void doAction(TableItem[] selection,String export,final String fileType,final String strTrace) {
		string = ""; 
//		TableItem[] selection = table.getSelection(); 
		
		if (selection.length == 0) { DialogFactory.openInfoAtLeast(DialogFactory.view); } 
		else {
			String msg="",msgData="",msgTrace="",msgret="",printableData="",printableTrace="";
			int jmlData=0;
			for (int i=0; i<selection.length; i++) {
				tblName = selection[i].getText(0);
				sIdVal = selection[i].getText(1);
				string+=sIdVal+",";
				
				String sel = "SELECT * FROM "+tblName+" WHERE "+sIdName+"="+sIdVal;
				Jdbc.select(sel, sID);
				
				//------------------------------ ATS MESSAGES ------------------------------
				if (sID.compareTo(Titles.stFREETEXT)==0) {
					msg = Jdbc.sfree;
					susunFREE(Jdbc.dbfree); }
				
				else if (sID.compareTo(Titles.stALR)==0) {
					msg = Jdbc.salr;
					susunALR(Jdbc.dbtype7a,Jdbc.dbtype9b,Jdbc.dbtype13a,Jdbc.dbtype13b,Jdbc.dbtype15b,Jdbc.dbtype15c,Jdbc.dbtype16a); }
				
				else if (sID.compareTo(Titles.stRCF)==0) {
					msg = Jdbc.srcf;
					susunRCF(Jdbc.dbtype7a, Jdbc.dbtype7c, Jdbc.dbtype21); }
				
				else if (sID.compareTo(Titles.stFPL)==0) {
					ViewATS.sReg="";
					ViewATS.sRmk="";
					
					msg = Jdbc.sfpl; 
					Jdbc.select("SELECT type13b from "+tblName+" WHERE type3a='DEP' AND type7a='"+Jdbc.dbtype7a+"' " +
							"AND type13a='"+Jdbc.dbtype13a+"' " +
							"AND type16a='"+Jdbc.dbtype16a+"' " +
							"AND filled_by='"+Jdbc.dbfiledby+"'", "fpldla");
					susunFPL(Jdbc.dbtype7a,Jdbc.dbsReg,Jdbc.dbfiledby,Jdbc.dbtype9b,Jdbc.dbtype13a,Jdbc.dbtype13b,Jdbc.dbtype15a,
							Jdbc.dbtype15b,Jdbc.dbtype15c,Jdbc.dbtype16a,Jdbc.dbtype16b,Jdbc.dbatd,Jdbc.dbsRmk); }
				
				else if (sID.compareTo(Titles.stDLA)==0) {
					msg = Jdbc.sdla;
					susunDLA(Jdbc.dbtype7a,Jdbc.dbtype13a,Jdbc.dbtype13b,Jdbc.dbtype16a,Jdbc.dbtype18); }
				
				else if (sID.compareTo(Titles.stCHG)==0) {
					msg = Jdbc.schg;
					susunDLA(Jdbc.dbtype7a,Jdbc.dbtype13a,Jdbc.dbtype13b,Jdbc.dbtype16a,Jdbc.dbtype18); }
				
				else if (sID.compareTo(Titles.stCNL)==0) {
					msg = Jdbc.scnl;
					susunDLA(Jdbc.dbtype7a,Jdbc.dbtype13a,Jdbc.dbtype13b,Jdbc.dbtype16a,Jdbc.dbtype18); } 

				else if (sID.compareTo(Titles.stDEP)==0) {
					msg = Jdbc.sdep;
					susunDLA(Jdbc.dbtype7a,Jdbc.dbtype13a,Jdbc.dbtype13b,Jdbc.dbtype16a,Jdbc.dbtype18); }
				
				else if (sID.compareTo(Titles.stARR)==0) {
					msg = Jdbc.sarr;
					susunARR(Jdbc.dbtype7a,Jdbc.dbtype13a,Jdbc.dbtype13b,Jdbc.dbtype17a,Jdbc.dbtype17b); } 
				
				else if (sID.compareTo(Titles.stCDN)==0) {
					msg = Jdbc.scdn;
					susunCDN(Jdbc.dbtype7a,Jdbc.dbtype13a,Jdbc.dbtype13b,Jdbc.dbtype16a,Jdbc.dbtype22); }
				
				else if (sID.compareTo(Titles.stCPL)==0) {
					msg = Jdbc.scpl;
					susunALR(Jdbc.dbtype7a,Jdbc.dbtype9b,Jdbc.dbtype13a,Jdbc.dbtype13b,Jdbc.dbtype15b,Jdbc.dbtype15c,Jdbc.dbtype16a); }
				
				else if (sID.compareTo(Titles.stEST)==0) {
					msg = Jdbc.sest;
					susunEST(Jdbc.dbtype7a,Jdbc.dbtype13a,Jdbc.dbtype13b,Jdbc.dbtype14a,Jdbc.dbtype14b,Jdbc.dbtype16a); }
				
				else if (sID.compareTo(Titles.stACP)==0) {
					msg = Jdbc.sacp;
					susunACP(Jdbc.dbtype7a,Jdbc.dbtype13a,Jdbc.dbtype13b,Jdbc.dbtype16a); }
				
				else if (sID.compareTo(Titles.stLAM)==0) {
					msg = Jdbc.slam;
					susunLAM(Jdbc.dbtype3b, Jdbc.dbtype3c); }
				
				else if (sID.compareTo(Titles.stRQP)==0) {
					msg = Jdbc.srqp;
					susunDLA(Jdbc.dbtype7a,Jdbc.dbtype13a,Jdbc.dbtype13b,Jdbc.dbtype16a,Jdbc.dbtype18); }
				
				else if (sID.compareTo(Titles.stRQS)==0) {
					msg = Jdbc.srqs;
					susunDLA(Jdbc.dbtype7a,Jdbc.dbtype13a,Jdbc.dbtype13b,Jdbc.dbtype16a,Jdbc.dbtype18); }
				
				else if (sID.compareTo(Titles.stSPL)==0) {
					msg = Jdbc.sspl;
					susunACP(Jdbc.dbtype7a,Jdbc.dbtype13a,Jdbc.dbtype13b,Jdbc.dbtype16a); }
				
				//------------------------------ NOTAM MESSAGES ------------------------------
//				else if (sID.compareTo(Titles.stNOTAM)==0 || sID.compareTo(Titles.stRQNTM)==0 || sID.compareTo(Titles.stCHKNTM)==0 || 
//						sID.compareTo(Titles.stACTNTM)==0 ||  sID.compareTo(Titles.stEXPNTM)==0) {
//					msg = Jdbc.snotam;
//				
//					if (sID.compareTo(Titles.stNOTAM)==0) {
//						susunNOTAM(Jdbc.dboriginator,Jdbc.dbsNotamNo,Jdbc.dbidentifiers,Jdbc.dbsRefNotamNo,Jdbc.dbsNotamCode,Jdbc.dbpurpose,
//			    				Jdbc.dbscope,Jdbc.dbA,Jdbc.dbsStatusNotam); }
//					else if (sID.compareTo(Titles.stRQNTM)==0) {
//			    		susunRQNTM(Jdbc.dboriginator,Jdbc.dbsNotamNo,Jdbc.dbidentifiers,Jdbc.dbsRefNotamNo,Jdbc.dbA,Jdbc.dbsStatusRqntm); }
//			    	else if (sID.compareTo(Titles.stCHKNTM)==0 || sID.compareTo(Titles.stACTNTM)==0 || sID.compareTo(Titles.stEXPNTM)==0) {
//						susunACTEXPCHK(Jdbc.dboriginator,Jdbc.dbsNotamNo,Jdbc.dbidentifiers,Jdbc.dbsRefNotamNo,Jdbc.dbsNotamCode,Jdbc.dbpurpose,
//			    				Jdbc.dbscope,Jdbc.dbA); }					
//				}
				
				else if (sID.compareTo(Titles.stNOTAM)==0 || sID.compareTo(Titles.stRQNTM)==0 || sID.compareTo(Titles.stCHKNTM)==0 || 
						sID.compareTo(Titles.stACTNTM)==0 ||  sID.compareTo(Titles.stEXPNTM)==0) {
					msg = Jdbc.snotam;
				
					if (sID.compareTo(Titles.stNOTAM)==0 || sID.compareTo(Titles.stEXPNTM)==0) {
						susunNOTAM(Jdbc.dboriginator,Jdbc.dbsNotamNo,Jdbc.dbidentifiers,Jdbc.dbsRefNotamNo,Jdbc.dbsNotamCode,Jdbc.dbpurpose,
			    				Jdbc.dbscope,Jdbc.dbA,Jdbc.dbsStatusNotam); }
					else if (sID.compareTo(Titles.stRQNTM)==0) {
			    		susunRQNTM(Jdbc.dboriginator,Jdbc.dbsNotamNo,Jdbc.dbidentifiers,Jdbc.dbsRefNotamNo,Jdbc.dbA,Jdbc.dbsStatusRqntm); }
			    	else if (sID.compareTo(Titles.stCHKNTM)==0 || sID.compareTo(Titles.stACTNTM)==0) {
						susunACTEXPCHK(Jdbc.dboriginator,Jdbc.dbsNotamNo,Jdbc.dbidentifiers,Jdbc.dbsRefNotamNo,Jdbc.dbsNotamCode,Jdbc.dbpurpose,
			    				Jdbc.dbscope,Jdbc.dbA); }					
				}
		
				else if (sID.compareTo(Titles.stSNOWTAM)==0) {
					msg = Jdbc.ssnowtam;
					susunSNOWTAM(Jdbc.dbstate,Jdbc.dbsn1,Jdbc.dblocind,Jdbc.dbtimeobs,Jdbc.dboptgrp,Jdbc.dbsn2); }
				
				else if (sID.compareTo(Titles.stASHTAM)==0) {
					msg = Jdbc.sashtam;
					susunASHTAM(Jdbc.dbstate,Jdbc.dbsn1,Jdbc.dblocind,Jdbc.dbissued,Jdbc.dboptgrp,Jdbc.dbsn2); }
				
				else if (sID.compareTo(Titles.stBIRDTAM)==0) {
					msg = Jdbc.sbirdtam;
					susunBIRDTAM(Jdbc.dbbirdnum,Jdbc.dbefftime,Jdbc.dbexptime,Jdbc.dbintlev); }
				
				else if (sID.compareTo(Titles.stRQN)==0 || sID.compareTo(Titles.stRQL)==0) {
					msg = Jdbc.srqn;
					susunRQN(Jdbc.dbreqid,Jdbc.dblocind,Jdbc.dbreqmsg); }
				
				//------------------------------ METEO MESSAGES ------------------------------
				else if (sID.compareTo(Titles.stMETAR)==0 || sID.compareTo(Titles.stSPECI)==0) { 
					msg=Jdbc.smetar; 
					susunMETAR(Jdbc.dborigin,Jdbc.dbmsgID,Jdbc.dblocation,Jdbc.dbtimeobs,Jdbc.dbissued,Jdbc.dboption,Jdbc.dbtext); }

				else if (sID.compareTo(Titles.stSIGMET)==0 || sID.compareTo(Titles.stAIRMET)==0 || sID.compareTo(Titles.stWINSWAR)==0) { 
					msg=Jdbc.ssigmet; 
					susunSIGMET(Jdbc.dborigin,Jdbc.dbmsgID,Jdbc.dblocation,Jdbc.dbissued,Jdbc.dboption,Jdbc.dbtext); }
				
				else if (sID.compareTo(Titles.stAIREP)==0 || sID.compareTo(Titles.stTAFOR)==0 || sID.compareTo(Titles.stARFOR)==0 || 
						sID.compareTo(Titles.stROFOR)==0 || sID.compareTo(Titles.stWINTEM)==0 || sID.compareTo(Titles.stADV)==0 ||
						sID.compareTo(Titles.stSYNOP)==0 || sID.compareTo(Titles.stWARSYN)==0) { 
					msg=Jdbc.sairep; 
					susunAIREP(Jdbc.dborigin,Jdbc.dbmsgID,Jdbc.dbissued,Jdbc.dboption,Jdbc.dbtext); }
				
				else if (sID.compareTo(Titles.stRQM)==0) {
					msg = Jdbc.srqm;
					susunFREE(Jdbc.dbfree); }
				
				//------------------------------ Retrieve ------------------------------
				else if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) { 
					msg=Jdbc.sret; msgret=Jdbc.srettrace; 
					susunRETRIEVAL(sTrace,Jdbc.dbdata,Jdbc.dbtrace); }
				
				//else if (sID.compareTo(Titles.stOutgoing)==0) { msg=Jdbc.sret; msgret=Jdbc.srettrace; }
				//------------------------------ Reject ------------------------------
				else if (sID.compareTo(Titles.stReject)==0) { 
					msg=Jdbc.sreject;
					susunREJECT(Jdbc.typemsg, Jdbc.errmsg, Jdbc.textmsg); }
				
				if (msgData.compareTo(msg)!=0) { printableData+=msg+"\n"; }
				if (msgTrace.compareTo(msgret)!=0) { printableTrace+=msgret+"\n"; }
				
				jmlData++;
			}
			
			tTextual.setText(printableData);
			tTrace.setText(printableTrace);
			
			//exporting to file (_part)
			printable=printableData;
			iJmlDataExport=Integer.toString(selection.length);
			writeToFile(fileType, strTrace, "part");
		} //end of else	
	}
	
	public Table col(String str) {
		sID=str;
		if (sID.compareTo(Titles.stStatistic)!=0) sh.groupStyle(Group, 1, str+" List");
		else if (sID.compareTo(Titles.stStatistic)==0) sh.groupStyle(Group, 1, "");
		
		//First, we make sure the user does not see what we're doing.
		table.setRedraw( false );
		//Then we remove all columns.
		while ( table.getColumnCount() > 0 ) {
		    table.getColumns()[ 0 ].dispose();
		}
		
		//And then we add the needed ones.
		//Read table columns name and width from file /aed/tblcol.txt
		rff.readTblCol();
  		String iWidth[] = null;
  		String iName[] = null;
  		String width="",name="";	
  		
  		//ATS Messages
		if (sID.compareTo(Titles.stFREETEXT)==0 || sID.compareTo(Titles.stRQM)==0) { 	
			width = rff.getWidthFREEATS(); name = rff.getColnmFREEATS();
			if (sID.compareTo(Titles.stRQM)==0) {
				if (name.contains("Freetext")) name = name.replace("Freetext", "Request Message"); } }
		else if (sID.compareTo(Titles.stALR)==0 || sID.compareTo(Titles.stCPL)==0) { width = rff.getWidthALR(); name = rff.getColnmALR(); }
		else if (sID.compareTo(Titles.stRCF)==0) { width = rff.getWidthRCF(); name = rff.getColnmRCF(); }
		else if (sID.compareTo(Titles.stFPL)==0) { width = rff.getWidthFPL(); name = rff.getColnmFPL(); }
		else if (sID.compareTo(Titles.stDLA)==0 || sID.compareTo(Titles.stCHG)==0 || 
				sID.compareTo(Titles.stCNL)==0 || sID.compareTo(Titles.stDEP)==0 || 
				sID.compareTo(Titles.stRQP)==0 || sID.compareTo(Titles.stRQS)==0) { width = rff.getWidthDLA(); name = rff.getColnmDLA(); }
		else if (sID.compareTo(Titles.stCDN)==0) { width = rff.getWidthCDN(); name = rff.getColnmCDN(); }
		else if (sID.compareTo(Titles.stARR)==0) { width = rff.getWidthARR(); name = rff.getColnmARR(); }
		else if (sID.compareTo(Titles.stEST)==0) { width = rff.getWidthEST(); name = rff.getColnmEST(); }
		else if (sID.compareTo(Titles.stACP)==0 || sID.compareTo(Titles.stSPL)==0) { width = rff.getWidthACP(); name = rff.getColnmACP(); }
		else if (sID.compareTo(Titles.stLAM)==0) { width = rff.getWidthLAM(); name = rff.getColnmLAM(); }
		//NOTAM
		else if (sID.compareTo(Titles.stNOTAM)==0 || sID.compareTo(Titles.stEXPNTM)==0) { width = rff.getWidthNOTAM(); name = rff.getColnmNOTAM(); }
		else if (sID.compareTo(Titles.stRQNTM)==0) { width = rff.getWidthRQNTM(); name = rff.getColnmRQNTM(); }
//		else if (sID.compareTo(Titles.stCHKNTM)==0 || sID.compareTo(Titles.stACTNTM)==0 || sID.compareTo(Titles.stEXPNTM)==0) 
//		{ width = rff.getWidthCHKNTM(); name = rff.getColnmCHKNTM(); }
		else if (sID.compareTo(Titles.stCHKNTM)==0 || sID.compareTo(Titles.stACTNTM)==0) 
			{ width = rff.getWidthCHKNTM(); name = rff.getColnmCHKNTM(); }
		else if (sID.compareTo(Titles.stSNOWTAM)==0) { width = rff.getWidthSNOWTAM(); name = rff.getColnmSNOWTAM(); }
		else if (sID.compareTo(Titles.stASHTAM)==0) { width = rff.getWidthASHTAM(); name = rff.getColnmASHTAM(); }
		else if (sID.compareTo(Titles.stBIRDTAM)==0) { width = rff.getWidthBIRDTAM(); name = rff.getColnmBIRDTAM(); }
		else if (sID.compareTo(Titles.stRQN)==0 || sID.compareTo(Titles.stRQL)==0) { width = rff.getWidthRQN(); name = rff.getColnmRQN(); }
		//METEO
		else if (sID.compareTo(Titles.stMETAR)==0 || sID.compareTo(Titles.stSPECI)==0) { width = rff.getWidthMETAR(); name = rff.getColnmMETAR(); }
		else if (sID.compareTo(Titles.stSIGMET)==0 || sID.compareTo(Titles.stAIRMET)==0 || sID.compareTo(Titles.stWINSWAR)==0) 
		{ width = rff.getWidthSIGMET(); name = rff.getColnmSIGMET(); }
		else if (sID.compareTo(Titles.stAIREP)==0 || sID.compareTo(Titles.stTAFOR)==0 || sID.compareTo(Titles.stARFOR)==0 || 
				sID.compareTo(Titles.stROFOR)==0 || sID.compareTo(Titles.stWINTEM)==0 || sID.compareTo(Titles.stADV)==0 ||
				sID.compareTo(Titles.stSYNOP)==0 || sID.compareTo(Titles.stWARSYN)==0) 
		{ width = rff.getWidthAIREP(); name = rff.getColnmAIREP(); }
		//Retrieval
		if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) 
		{ width = rff.getWidthIncoming(); name = rff.getColnmIncoming(); }
		//Rejected
		if (sID.compareTo(Titles.stReject)==0) 
		{ width = rff.getWidthReject(); name = rff.getColnmReject(); }
		//Statistic
//		if (sID.compareTo(Titles.stStatistic)==0) 
//			{ width = rff.getWidthStatistic(); name = rff.getColnmStatistic(); }
		if (sID.compareTo(Titles.stStatistic)==0) { 
			width = rff.getWidthStatistic(); name = rff.getColnmStatistic(); 
			if (name.compareToIgnoreCase("tblnm,id,CID,CSN,TMS,Date/Time,Type,Error Message,Message")==0) name=" , , , , , , , , ";
		}
		
		//Splitting width and name
  		if (!width.isEmpty() && width.contains(",")) { iWidth = width.split(","); }
  		if (!name.isEmpty() && name.contains(",")) {
  			iName = name.split(",");
  		
			TableColumn column;
			cols = new TableColumn[iName.length];
			for (int i=0; i<iName.length; i++) {
				column = new TableColumn(table, SWT.LEFT, i);
				column.setText(iName[i]);
				column.setWidth(Integer.parseInt(iWidth[i]));
				column.setResizable(true);
				cols[i] = column;
			}
  		}
  		
		//sorting with PlayerComparator
		cols[0].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str0); } });//tbl_name
		cols[1].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str1); } });//sIdName
		cols[2].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str2); } });//cid
		cols[3].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str3); } });//csn
		cols[4].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str4); } });//tms
		cols[5].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str5); } });//tgl
		
		if (sID.compareTo(Titles.stFREETEXT)==0 || sID.compareTo(Titles.stVOL)==0 || 
				sID.compareTo(Titles.stRQM)==0) {
			cols[6].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str6); } }); } 
		//-----------------------------------------------------------------------------------------------------
		else if (sID.compareTo(Titles.stLAM)==0 || sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
			cols[6].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str6); } });
			cols[7].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str7); } }); }
		//-----------------------------------------------------------------------------------------------------
		else if (sID.compareTo(Titles.stRCF)==0 || sID.compareTo(Titles.stRQN)==0 || sID.compareTo(Titles.stRQL)==0 || 
				sID.compareTo(Titles.stReject)==0) {
			cols[6].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str6); } });
			cols[7].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str7); } });
			cols[8].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str8); } }); }
		//-----------------------------------------------------------------------------------------------------
		else if (sID.compareTo(Titles.stACP)==0 || sID.compareTo(Titles.stSPL)==0 || sID.compareTo(Titles.stBIRDTAM)==0) {
			cols[6].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str6); } });
			cols[7].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str7); } });
			cols[8].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str8); } });
			cols[9].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str9); } }); }
		//-----------------------------------------------------------------------------------------------------
		else if (sID.compareTo(Titles.stDLA)==0 || sID.compareTo(Titles.stCHG)==0 || sID.compareTo(Titles.stCNL)==0 || 
				sID.compareTo(Titles.stDEP)==0 || sID.compareTo(Titles.stRQP)==0 || sID.compareTo(Titles.stRQS)==0 ||
				sID.compareTo(Titles.stCDN)==0 || sID.compareTo(Titles.stARR)==0 || sID.compareTo(Titles.stAIREP)==0 || 
				sID.compareTo(Titles.stTAFOR)==0 || sID.compareTo(Titles.stSYNOP)==0 || sID.compareTo(Titles.stARFOR)==0 ||
				sID.compareTo(Titles.stROFOR)==0 || sID.compareTo(Titles.stWINTEM)==0 || sID.compareTo(Titles.stADV)==0 || 
				sID.compareTo(Titles.stWARSYN)==0) {
				
			cols[6].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str6); } });
			cols[7].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str7); } });
			cols[8].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str8); } });
			cols[9].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str9); } });
			cols[10].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str10); } }); }
		//-----------------------------------------------------------------------------------------------------
		else if (sID.compareTo(Titles.stEST)==0 || sID.compareTo(Titles.stRQNTM)==0 || sID.compareTo(Titles.stSNOWTAM)==0 ||
				sID.compareTo(Titles.stASHTAM)==0 || sID.compareTo(Titles.stSIGMET)==0 || sID.compareTo(Titles.stAIRMET)==0 ||
				sID.compareTo(Titles.stWINSWAR)==0) {
			cols[6].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str6); } });
			cols[7].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str7); } });
			cols[8].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str8); } });
			cols[9].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str9); } });
			cols[10].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str10); } }); 
			cols[11].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str11); } }); }
		//-----------------------------------------------------------------------------------------------------
		else if (sID.compareTo(Titles.stALR)==0 || sID.compareTo(Titles.stCPL)==0 || sID.compareTo(Titles.stMETAR)==0 || 
				sID.compareTo(Titles.stSPECI)==0) {
			cols[6].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str6); } });
			cols[7].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str7); } });
			cols[8].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str8); } });
			cols[9].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str9); } });
			cols[10].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str10); } });
			cols[11].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str11); } });
			cols[12].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str12); } }); }
		//-----------------------------------------------------------------------------------------------------
		else if (sID.compareTo(Titles.stFPL)==0) {
			cols[6].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str6); } });
			cols[7].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str7); } });
			cols[8].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str8); } });
			cols[9].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str9); } });
			cols[10].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str10); } });
			cols[11].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str11); } });
			cols[12].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str12); } }); 
			cols[13].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str13); } });  
			
			cols[14].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str14); } });  
			cols[15].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str15); } });  
			cols[16].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str16); } });  
			cols[17].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str17); } });  
			cols[18].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str18); } }); }
		//-----------------------------------------------------------------------------------------------------
//		else if (sID.compareTo(Titles.stACTNTM)==0 || sID.compareTo(Titles.stEXPNTM)==0 || 
//				sID.compareTo(Titles.stCHKNTM)==0) {
		else if (sID.compareTo(Titles.stACTNTM)==0 || sID.compareTo(Titles.stCHKNTM)==0) {
			cols[6].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str6); } });
			cols[7].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str7); } });
			cols[8].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str8); } });
			cols[9].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str9); } });
			cols[10].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str10); } });
			cols[11].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str11); } });
			cols[12].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str12); } }); 
			cols[13].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str13); } }); }
		//-----------------------------------------------------------------------------------------------------
		else if (sID.compareTo(Titles.stNOTAM)==0 || sID.compareTo(Titles.stEXPNTM)==0) {
			cols[6].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str6); } });
			cols[7].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str7); } });
			cols[8].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str8); } });
			cols[9].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str9); } });
			cols[10].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str10); } });
			cols[11].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str11); } });
			cols[12].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str12); } }); 
			cols[13].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str13); } }); 
			cols[14].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) { compare(PlayerComparator.str14); } }); }
		
		//And finally we let the user see what we did.
		table.setRedraw( true );
		
		return table;
	}
	
	void compare(int icompare) {
		if (iJmlData!=0) {
			comparator.setColumn(icompare);
	        comparator.reverseDirection();
	        fillTable(table);
		} else {
			System.out.println("no data to sort..");
		}
	}
  	
  	void fillTable(Table table) {
	    // Turn off drawing to avoid flicker
	    table.setRedraw(false);
        
	    // We remove all the table entries, sort our rows, then add the entries
	    table.removeAll();
	    
	    Collections.sort(players, comparator);
	    for (Iterator itr = players.iterator(); itr.hasNext();) {
	    	Player player = (Player) itr.next();
	    	TableItem item = new TableItem(table, SWT.NONE);
	    	int c = 0;
	    	item.setText(c++, player.getstr0()); //tbl_name
	    	item.setText(c++, player.getstr1()); //idName
	    	item.setText(c++, player.getstr2()); //cid
	    	item.setText(c++, player.getstr3()); //csn
	    	item.setText(c++, player.getstr4()); //tms
	    	item.setText(c++, player.getstr5()); //tgl
	    	
	    	
	    	if (sID.compareTo(Titles.stFREETEXT)==0 || sID.compareTo(Titles.stVOL)==0 || sID.compareTo(Titles.stRQM)==0) { 
	    		item.setText(c++, player.getstr6()); } 
	    	//-----------------------------------------------------------------------------------------------------
	    	else if (sID.compareTo(Titles.stLAM)==0 || sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
				item.setText(c++, player.getstr6()); 
				item.setText(c++, player.getstr7()); }
	    	//-----------------------------------------------------------------------------------------------------
	    	else if (sID.compareTo(Titles.stRCF)==0 || sID.compareTo(Titles.stRQN)==0 || sID.compareTo(Titles.stRQL)==0 || 
	    			sID.compareTo(Titles.stReject)==0) {
				item.setText(c++, player.getstr6()); 
				item.setText(c++, player.getstr7()); 
				item.setText(c++, player.getstr8()); }
	    	//-----------------------------------------------------------------------------------------------------
	    	else if (sID.compareTo(Titles.stACP)==0 || sID.compareTo(Titles.stSPL)==0 || sID.compareTo(Titles.stBIRDTAM)==0) {
				item.setText(c++, player.getstr6()); 
				item.setText(c++, player.getstr7()); 
				item.setText(c++, player.getstr8()); 
				item.setText(c++, player.getstr9()); }
	    	//-----------------------------------------------------------------------------------------------------
			else if (sID.compareTo(Titles.stDLA)==0 || sID.compareTo(Titles.stCHG)==0 || sID.compareTo(Titles.stCNL)==0 || 
					sID.compareTo(Titles.stDEP)==0 || sID.compareTo(Titles.stRQP)==0 || sID.compareTo(Titles.stRQS)==0 ||
					sID.compareTo(Titles.stCDN)==0 || sID.compareTo(Titles.stARR)==0 || sID.compareTo(Titles.stAIREP)==0 || 
					sID.compareTo(Titles.stTAFOR)==0 || sID.compareTo(Titles.stSYNOP)==0 || sID.compareTo(Titles.stARFOR)==0 ||
					sID.compareTo(Titles.stROFOR)==0 || sID.compareTo(Titles.stWINTEM)==0 || sID.compareTo(Titles.stADV)==0 || 
					sID.compareTo(Titles.stWARSYN)==0) {
				item.setText(c++, player.getstr6());
				item.setText(c++, player.getstr7()); 
				item.setText(c++, player.getstr8()); 
				item.setText(c++, player.getstr9()); 
				item.setText(c++, player.getstr10()); }
	    	//-----------------------------------------------------------------------------------------------------
	    	else if (sID.compareTo(Titles.stEST)==0 || sID.compareTo(Titles.stRQNTM)==0 || sID.compareTo(Titles.stSNOWTAM)==0 ||
					sID.compareTo(Titles.stASHTAM)==0 || sID.compareTo(Titles.stSIGMET)==0 || sID.compareTo(Titles.stAIRMET)==0 ||
					sID.compareTo(Titles.stWINSWAR)==0) {
				item.setText(c++, player.getstr6());
				item.setText(c++, player.getstr7()); 
				item.setText(c++, player.getstr8()); 
				item.setText(c++, player.getstr9()); 
				item.setText(c++, player.getstr10()); 
				item.setText(c++, player.getstr11()); }
	    	//-----------------------------------------------------------------------------------------------------
	    	else if (sID.compareTo(Titles.stALR)==0 || sID.compareTo(Titles.stCPL)==0 || sID.compareTo(Titles.stMETAR)==0 || 
					sID.compareTo(Titles.stSPECI)==0) {
				item.setText(c++, player.getstr6()); 
				item.setText(c++, player.getstr7()); 
				item.setText(c++, player.getstr8()); 
				item.setText(c++, player.getstr9()); 
				item.setText(c++, player.getstr10()); 
				item.setText(c++, player.getstr11()); 
				item.setText(c++, player.getstr12()); }
	    	//-----------------------------------------------------------------------------------------------------
	    	else if (sID.compareTo(Titles.stFPL)==0) {
				item.setText(c++, player.getstr6());
				item.setText(c++, player.getstr7()); 
				item.setText(c++, player.getstr8()); 
				item.setText(c++, player.getstr9()); 
				item.setText(c++, player.getstr10()); 
				item.setText(c++, player.getstr11()); 
				item.setText(c++, player.getstr12()); 
				item.setText(c++, player.getstr13());  
				item.setText(c++, player.getstr14());  
				item.setText(c++, player.getstr15());  
				item.setText(c++, player.getstr16());  
				item.setText(c++, player.getstr17());  
				item.setText(c++, player.getstr18()); }
	    	//-----------------------------------------------------------------------------------------------------
//			else if (sID.compareTo(Titles.stACTNTM)==0 || sID.compareTo(Titles.stEXPNTM)==0 ||
//					sID.compareTo(Titles.stCHKNTM)==0) {
				else if (sID.compareTo(Titles.stACTNTM)==0 || sID.compareTo(Titles.stCHKNTM)==0) {
				item.setText(c++, player.getstr6());
				item.setText(c++, player.getstr7()); 
				item.setText(c++, player.getstr8()); 
				item.setText(c++, player.getstr9()); 
				item.setText(c++, player.getstr10()); 
				item.setText(c++, player.getstr11()); 
				item.setText(c++, player.getstr12()); 
				item.setText(c++, player.getstr13()); }
	    	//-----------------------------------------------------------------------------------------------------
			else if (sID.compareTo(Titles.stNOTAM)==0 || sID.compareTo(Titles.stEXPNTM)==0) {
				item.setText(c++, player.getstr6());
				item.setText(c++, player.getstr7()); 
				item.setText(c++, player.getstr8()); 
				item.setText(c++, player.getstr9()); 
				item.setText(c++, player.getstr10()); 
				item.setText(c++, player.getstr11()); 
				item.setText(c++, player.getstr12()); 
				item.setText(c++, player.getstr13()); 
				item.setText(c++, player.getstr14()); }
	    }
	    
	    // sort indicator
	    table.setSortDirection(dir);
        
	    // Turn drawing back on
	    table.setRedraw(true);
  	}
  	
	public static void enabledPagination(boolean b) {
		bFirst.setEnabled(b);
	    bPrevious.setEnabled(b);
	    bNext.setEnabled(b);
	    bLast.setEnabled(b);
	    tGotoPage.setEnabled(b);
	}
	
	public static void setInfoRowZero() {
		sh.labelStyle(lbJmlhal, "Page 0 of 0", 130, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
	    sh.labelStyle(lbJmldt, "0 row | Record 0 of 0 in Page 0 ", 300, SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
	}
	
//	public static void setInfoRowZeroOut() {
//		sh.labelStyle(lbJmlhal, "Page 0 of 0", 130, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
//	    sh.labelStyle(lbJmldt, "0 row | Record 0 of 0 ", 300, SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
//	}
	
	void setInfoRow() {
		tGotoPage.setText(sHalKe);
		if (Integer.parseInt(jmlHal)==1) tGotoPage.setEnabled(false);
		else if (Integer.parseInt(jmlHal)>1) tGotoPage.setEnabled(true);
		
		sh.labelStyle(lbJmlhal, "Page "+sHalKe+" of "+jmlHal, 130, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
		sh.labelStyle(lbJmldt, sJmlData+" row(s) | Record "+(table.getSelectionIndex()+1)+" of "+iOfRow+" in Page "+sHalKe+" ", 300, SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
	}
	
//	void setInfoRowOut() {
//		tGotoPage.setText(sHalKe);
//		sh.labelStyle(lbJmlhal, "Page "+sHalKe+" of "+jmlHal, 130, SWT.LEFT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
//		sh.labelStyle(lbJmldt, sJmlData+" row(s) | Record "+(table.getSelectionIndex()+1)+" of "+iOfRow/*+" in Page "+sHalKe+" "*/, 300, SWT.RIGHT, SWT.CENTER, SWT.NORMAL, sh.BLACK);
//	}
	
	public static String getFlnm(String sID) {
		if (sID.compareTo(Titles.stFREETEXT)==0) sFlnm="Freetext";
		
		//ATS Messages
		else if (sID.compareTo(Titles.stALR)==0) sFlnm="ALR";
		else if (sID.compareTo(Titles.stRCF)==0) sFlnm="RCF";
		else if (sID.compareTo(Titles.stFPL)==0) sFlnm="FPL";
		else if (sID.compareTo(Titles.stDLA)==0) sFlnm="DLA";
		else if (sID.compareTo(Titles.stCHG)==0) sFlnm="CHG"; 
		else if (sID.compareTo(Titles.stCNL)==0) sFlnm="CNL";
		else if (sID.compareTo(Titles.stDEP)==0) sFlnm="DEP"; 
		else if (sID.compareTo(Titles.stARR)==0) sFlnm="ARR";
		else if (sID.compareTo(Titles.stCDN)==0) sFlnm="CDN";
		else if (sID.compareTo(Titles.stCPL)==0) sFlnm="CPL";
		else if (sID.compareTo(Titles.stEST)==0) sFlnm="EST";
		else if (sID.compareTo(Titles.stACP)==0) sFlnm="ACP";
		else if (sID.compareTo(Titles.stLAM)==0) sFlnm="LAM";
		else if (sID.compareTo(Titles.stRQP)==0) sFlnm="RQP"; 
		else if (sID.compareTo(Titles.stRQS)==0) sFlnm="RQS";
		else if (sID.compareTo(Titles.stSPL)==0) sFlnm="SPL";
		//NOTAM
		else if (sID.compareTo(Titles.stNOTAM)==0) sFlnm="NOTAM";
		else if (sID.compareTo(Titles.stACTNTM)==0) sFlnm="Active_NTM";
		else if (sID.compareTo(Titles.stEXPNTM)==0) sFlnm="Expired_NTM";
		else if (sID.compareTo(Titles.stCHKNTM)==0) sFlnm="Checklist";
		else if (sID.compareTo(Titles.stRQNTM)==0) sFlnm="RQNTM";
		else if (sID.compareTo(Titles.stSNOWTAM)==0) sFlnm="SNOWTAM";
		else if (sID.compareTo(Titles.stASHTAM)==0) sFlnm="ASHTAM";
		else if (sID.compareTo(Titles.stBIRDTAM)==0) sFlnm="BIRDTAM";
		else if (sID.compareTo(Titles.stRQN)==0) sFlnm="RQN";
		else if (sID.compareTo(Titles.stRQL)==0) sFlnm="RQL";
		//METEO
		else if (sID.compareTo(Titles.stMETAR)==0) sFlnm="METAR";
		else if (sID.compareTo(Titles.stSPECI)==0) sFlnm="SPECI";
		else if (sID.compareTo(Titles.stSIGMET)==0) sFlnm="SIGMET";
		else if (sID.compareTo(Titles.stAIRMET)==0) sFlnm="AIRMET";
		else if (sID.compareTo(Titles.stAIREP)==0) sFlnm="AIREP";
		else if (sID.compareTo(Titles.stTAFOR)==0) sFlnm="TAFOR";
		else if (sID.compareTo(Titles.stSYNOP)==0) sFlnm="SYNOP";
		else if (sID.compareTo(Titles.stARFOR)==0) sFlnm="ARFOR";
		else if (sID.compareTo(Titles.stROFOR)==0) sFlnm="ROFOR";
		else if (sID.compareTo(Titles.stWINSWAR)==0) sFlnm="WINSWAR";
		else if (sID.compareTo(Titles.stWINTEM)==0) sFlnm="WINTEM";
		else if (sID.compareTo(Titles.stADV)==0) sFlnm="ADV";
		else if (sID.compareTo(Titles.stVOL)==0) sFlnm="VOL";
		else if (sID.compareTo(Titles.stRQM)==0) sFlnm="RQM";
		else if (sID.compareTo(Titles.stWARSYN)==0) sFlnm="WARSYN";
		//AIDC
		else if (sID.compareTo(Titles.stABI)==0) sFlnm="ABI";
		else if (sID.compareTo(Titles.stMAC)==0) sFlnm="MAC";
		else if (sID.compareTo(Titles.stPAC)==0) sFlnm="PAC";
		else if (sID.compareTo(Titles.stREJ)==0) sFlnm="REJ";
		else if (sID.compareTo(Titles.stTOC)==0) sFlnm="TOC";
		else if (sID.compareTo(Titles.stAOC)==0) sFlnm="AOC";
		else if (sID.compareTo(Titles.stEMG)==0) sFlnm="EMG";
		else if (sID.compareTo(Titles.stMIS)==0) sFlnm="MIS";
		else if (sID.compareTo(Titles.stTDM)==0) sFlnm="TDM";
		else if (sID.compareTo(Titles.stLRM)==0) sFlnm="LRM";
		else if (sID.compareTo(Titles.stASM)==0) sFlnm="ASM";
		else if (sID.compareTo(Titles.stFAN)==0) sFlnm="FAN";
		else if (sID.compareTo(Titles.stFCN)==0) sFlnm="FCN";
		else if (sID.compareTo(Titles.stTRU)==0) sFlnm="TRU";
		else if (sID.compareTo(Titles.stADS)==0) sFlnm="ADS";
		//Retrieve
		else if (sID.compareTo(Titles.stIncoming)==0) sFlnm="Incoming";
		else if (sID.compareTo(Titles.stOutgoing)==0) sFlnm="Outgoing";
		//Reject
		else if (sID.compareTo(Titles.stReject)==0) sFlnm="Rejected";
		
		return sFlnm;
	}
	
	void exportToFile(final String fileType,final String strTrace) {
		sTrace=strTrace;
		//STEP 1. Clear the table 
		try {
			//STEP 2: Register JDBC driver
			Class.forName(ConnectTo.JDBC_DRIVER);
			//STEP 3: Open a connection
			connExport = DriverManager.getConnection(ConnectTo.DB_NAME,ConnectTo.DB_USER,ConnectTo.DB_PASS);
			
			progbr = new cProgressBar("Selecting data ...");
			new Thread() {
				public void run() {
					if (AmscSplashScreen2.display.isDisposed()) return;
					AmscSplashScreen2.display.asyncExec(new Runnable() {
						public void run() {
							try {
								progbr.create(iLimitRow);
								// Create the comparator used for sorting
							    comparator = new PlayerComparator();
							    comparator.setColumn(PlayerComparator.str6);//tgl
							    comparator.setDirection(PlayerComparator.ASCENDING);
							    // Create the players
							    players = new ArrayList();
							    // Search criteria
							    String select = "SELECT * FROM ";
								String where = " WHERE tgl!='0000-00-00 00:00:00'";
								String order = " ORDER BY tgl DESC, "+sIdName+" DESC";
								
								iJmlData=0; //inisialisasi
								iCnt=0;
								rowNo=0;
								
								int parseYearTo = Integer.parseInt(yearTo);
								int parseYearFr = Integer.parseInt(yearFr);
								int parseMonthTo = Integer.parseInt(monthTo);
								int parseMonthFr = Integer.parseInt(monthFr);
								int parseDayTo = Integer.parseInt(dayTo);
								int parseDayFr = Integer.parseInt(dayFr);
								
								rsExport = new ResultSet[parseYearTo*parseMonthTo*parseDayTo];
								boolean flg=false;
								boolean flgD=false;
								
								for (int iYear=parseYearFr; iYear<=parseYearTo; iYear++) {
									int iMtemp=0,n=0,nn=0;
									
									if (iYear==parseYearFr) {
										iMtemp=parseMonthFr;
										n=12-iMtemp;
									} else if ((iYear>parseYearFr) && (iYear<parseYearTo)) {
										iMtemp=1;
										n=12-iMtemp;
									} else if (iYear == parseYearTo) {
										iMtemp= parseMonthTo;
										n=iMtemp;
										iMtemp=0;
										flg=true;
									}
								
									for (int iMonth=iMtemp; iMonth<=n+iMtemp; iMonth++) {
										if ((flg) && (iMonth>=12)) {
											nn++; 
											if ((n==iMtemp) && (nn>12)) break; 
										} else nn=iMonth;
										
										String sMonth = Integer.toString(nn);
										if (sMonth.length()==1) sMonth = sMonth.replace(sMonth, "0"+sMonth);
										
										int iDtemp=0,d=0,dd=0;
										if (sTblNm.compareToIgnoreCase("aftnrdI")==0 || sTblNm.compareToIgnoreCase("aftnrdO")==0) {
											if (iMonth==parseMonthFr) {
												iDtemp=parseDayFr;
												d=31-iDtemp;
											} else if ((iMonth>parseMonthFr) && (iMonth<parseMonthTo)) {
												iDtemp=1;
												d=31-iDtemp;
											} else if (iMonth == parseMonthTo) {
												iDtemp= parseDayTo;
												d=iDtemp;
												iDtemp=0;
												flgD=true;
											}
										} else {
											iDtemp=1;
										}
										
										//RETRIEVAL
										if (sTblNm.compareToIgnoreCase("aftnrdI")==0 || sTblNm.compareToIgnoreCase("aftnrdO")==0) {
											for (int iDay=31; iDay>=iDtemp; iDay--) {//for (int iDay=iDtemp; iDay<=d+iDtemp; iDay++) {
												if ((flgD) && (iDay>=31)) {
													dd++; 
													if ((d==iDtemp) && (dd>31)) break; 
												} else dd=iDay;
												
												String sDay = Integer.toString(dd);
												if (sDay.length()==1) sDay= sDay.replace(sDay, "0"+sDay);
												
												if (sTblNm.compareToIgnoreCase("aftnrdI")==0||sTblNm.compareToIgnoreCase("aftnrdO")==0) { 
													tblName=sTblNm+iYear+"_"+sMonth+"_"+sDay; } 
												
												//STEP 4: Execute a query
												step4Export(select, where, order, strTrace);
												iCnt++; //counter rs
											} //end of for3=TANGGAL
										} 
										//PARTIAL DATA & STATISTIC 
										else {
											String sDay = Integer.toString(dd);
											if (sDay.length()==1) sDay= sDay.replace(sDay, "0"+sDay);
											tblName=sTblNm+iYear+"_"+sMonth;
											
											//STEP 4: Execute a query
											step4Export(select, where, order, strTrace);
											iCnt++; //counter rs
										}
									} //end of for2=BULAN
								} //end of for1=YEAR

								progbr.finish();
								
								//STEP 4: Write to file
								writeToFile(fileType, strTrace, "");
							} catch (java.lang.OutOfMemoryError hs) {
								DialogFactory.openInfo(DialogFactory.LIST_INFO_OUT_OF_MEMORY);
								progbr.finish();
							}
						}
					});
				}
			}.start();
			progbr.begin();
		
			paginating(); 
			if (iJmlData!=0) { Lists.activatePopup(true, sID); }
			
			//STEP 6: Clean-up environment
		    stmtExport.close();
		    connExport.close();
	    
		} catch (SQLException se) { //Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception xe) { //Handle errors for Class.forName
			xe.printStackTrace();
		}	
	}
	
	void writeToFile(final String fileType,final String strTrace,final String part) {
		String filename="";
		if (part.compareToIgnoreCase("part")!=0) {
			if (iJmlData<=iMaxExport) iJmlDataExport=Integer.toString(iJmlData);
			else if (iJmlData>iMaxExport) iJmlDataExport=Integer.toString(iMaxExport);
			filename = getFlnm(sID);
		} else {
			filename = getFlnm(sID).concat("_part");
		}
		
		//PDF
		if (fileType.compareToIgnoreCase("pdf")==0) {
			if (strTrace.compareToIgnoreCase("trace")==0) {
				new WriteToPDF().setPrint(printable, iJmlDataExport, filename, strTrace); 
				printable=""; 
			} else if (strTrace.compareToIgnoreCase("notrace")==0) {
				new WriteToPDF().setPrint(printableRet, iJmlDataExport, filename, strTrace); 
				printableRet=""; 
			} else {
				new WriteToPDF().setPrint(printable, iJmlDataExport, filename, ""); 
				printable="";
			}
			table.deselectAll(); }
		//WORD
		else if (fileType.compareToIgnoreCase("docx")==0) {
			//Reject Message
			if (sID.compareTo(Titles.stReject)==0) {
				if (printable.startsWith("data=")) { printable=printable.replace("data=","Error Message: "); }
				if (printable.contains(";#")) { printable=printable.replaceAll(";#","\n"); }  
				if (printable.contains(";trace=")) { printable=printable.replaceAll(";trace=","\nMessage: "); }
			}
			
			//Incoming Message
			if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
				if (strTrace.compareToIgnoreCase("trace")==0) {
					if (printable.startsWith("data=")) { printable=printable.replace("data=","Data:\n"); }
					if (printable.contains(";#")) { printable=printable.replaceAll(";#","\n\n"); }  
					if (printable.contains(";trace=")) { printable=printable.replaceAll(";trace=","\nTrace:\n"); }	
				} else if (strTrace.compareToIgnoreCase("notrace")==0) {
					if (printableRet.startsWith("data=")) { printableRet=printableRet.replace("data=",""); }
					if (printableRet.contains(";#")) { printableRet=printableRet.replaceAll(";#","\n\n"); }  
					printable=printableRet;
				}
				
			}
//			new WriteToDOC(filename, printable + iJmlDataExport + " row(s) in this file");
			try {
				new WriteToDOCX(filename, printable + iJmlDataExport + " row(s) in this file");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			printable=""; 
			table.deselectAll(); }
		//EXCEL
		else if (fileType.compareToIgnoreCase("xls")==0) {
			if (strTrace.compareToIgnoreCase("trace")==0) {
				new WriteToXLS2(sID+" List", "msg"+getFlnm(sID), printableXLS, iJmlDataExport, filename, strTrace);
				printableXLS=""; 
			} else if (strTrace.compareToIgnoreCase("notrace")==0) {
				new WriteToXLS2(sID+" List", "msg"+getFlnm(sID), printableXLSRet, iJmlDataExport, filename, strTrace);
				printableXLSRet=""; 
			} else {
				new WriteToXLS2(sID+" List", "msg"+getFlnm(sID), printableXLS, iJmlDataExport, filename, "");
				printableXLS="";
			}
			table.deselectAll(); }
	}
	
	void xls() {
		if (str0.isEmpty()) str0=" "; 
    	if (str1.isEmpty()) str1=" ";
    	if (str2.isEmpty()) str2=" ";
    	if (str3.isEmpty()) str3=" ";
    	if (str4.isEmpty()) str4=" ";
    	if (str5.isEmpty()) str5=" ";
    	msg=str0+";"+str1+";"+str2+";"+str3+";"+str4+";"+str5+";";
	}
	
	public static void visibleInfoRow(boolean b) {
		lbJmldt.setVisible(b);
		bLimit.setVisible(b);
		bDOC.setVisible(b);
		bXLS.setVisible(b);
		bPDF.setVisible(b);
	}
	
	public static void visibleInfoPage(boolean b) {
		bFirst.setVisible(b);
		bPrevious.setVisible(b);
		bNext.setVisible(b);
		bLast.setVisible(b);
		tGotoPage.setVisible(b);
		lblGoto.setVisible(b);
		lbJmlhal.setVisible(b);
	}
	
	void paginating() {
		sJmlData=Integer.toString(iJmlData);
		if (sJmlData.compareTo("0")==0) {
			DialogFactory.openInfoDataNotFound(DialogFactory.find);
			enabledPagination(false);
			tGotoPage.setText("0");
			setInfoRowZero();
		} else { 
			//Lists.table.select(0);
			 
//			if (sID.compareTo(Titles.stOutgoing)==0) { iLimitRow=iJmlData; }//percobaan

			enabledPagination(false);
			bNext.setEnabled(true);
			bLast.setEnabled(true);
			
			yu=0; qq=0;
			yu=qq+1;
			sHalKe=Integer.toString(yu);
			
			int iJmlHal;
			if (iJmlData%iLimitRow==0) iJmlHal=iJmlData/iLimitRow;
			else iJmlHal=iJmlData/iLimitRow+1;
			jmlHal=Integer.toString(iJmlHal);
			
			if (Integer.parseInt(jmlHal)==1 || iJmlData<=iLimitRow) { enabledPagination(false); } 
			
			if (iJmlData<=iLimitRow) iOfRow=iJmlData; else iOfRow=iLimitRow;
			table.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
//					if (sID.compareTo(Titles.stOutgoing)==0) setInfoRowOut();
//					else 
					setInfoRow();
				}
			});
			
//			if (sID.compareTo(Titles.stOutgoing)==0) setInfoRowOut();
//			else 
			setInfoRow();
		}
	}

	public void Find() {
		//STEP 1. Clear the table 
		table.setRedraw(false);
		table.removeAll();
		table.setRedraw(true);
		
		try {
			//STEP 2: Register JDBC driver
			Class.forName(ConnectTo.JDBC_DRIVER);
			//STEP 3: Open a connection
			conn = DriverManager.getConnection(ConnectTo.DB_NAME,ConnectTo.DB_USER,ConnectTo.DB_PASS);
			
			progbr = new cProgressBar("Selecting data ...");
			new Thread() {
				public void run() {
					if (AmscSplashScreen2.display.isDisposed()) return;
					AmscSplashScreen2.display.asyncExec(new Runnable() {
						public void run() {
							try {
								progbr.create(iLimitRow);
								// Create the comparator used for sorting
							    comparator = new PlayerComparator();
							    comparator.setColumn(PlayerComparator.str6);//tgl
							    comparator.setDirection(PlayerComparator.ASCENDING);
							    // Create the players
							    players = new ArrayList();
							    // Search criteria
							    String select = "SELECT * FROM ";
								String where = " WHERE tgl!='0000-00-00 00:00:00'";
								String order = " ORDER BY tgl DESC, "+sIdName+" DESC";
								
								iJmlData=0; //inisialisasi
								iCnt=0;
								rowNo=0;
								
								int parseYearTo = Integer.parseInt(yearTo);
								int parseYearFr = Integer.parseInt(yearFr);
								int parseMonthTo = Integer.parseInt(monthTo);
								int parseMonthFr = Integer.parseInt(monthFr);
								int parseDayTo = Integer.parseInt(dayTo);
								int parseDayFr = Integer.parseInt(dayFr);
								
								rs = new ResultSet[parseYearTo*parseMonthTo*parseDayTo];
								boolean flg=false;
								boolean flgD=false;
								
								for (int iYear=parseYearFr; iYear<=parseYearTo; iYear++) {
									int iMtemp=0,n=0,nn=0;
									
									if (iYear==parseYearFr) {
										iMtemp=parseMonthFr;
										n=12-iMtemp;
									} else if ((iYear>parseYearFr) && (iYear<parseYearTo)) {
										iMtemp=1;
										n=12-iMtemp;
									} else if (iYear == parseYearTo) {
										iMtemp= parseMonthTo;
										n=iMtemp;
										iMtemp=0;
										flg=true;
									}
								
									for (int iMonth=iMtemp; iMonth<=n+iMtemp; iMonth++) {
										if ((flg) && (iMonth>=12)) {
											nn++; 
											if ((n==iMtemp) && (nn>12)) break; 
										} else nn=iMonth;
										
										String sMonth = Integer.toString(nn);
										if (sMonth.length()==1) sMonth = sMonth.replace(sMonth, "0"+sMonth);
										
										int iDtemp=0,d=0,dd=0;
										if (sTblNm.compareToIgnoreCase("aftnrdI")==0 || sTblNm.compareToIgnoreCase("aftnrdO")==0) {
											if (iMonth==parseMonthFr) {
												iDtemp=parseDayFr;
												d=31-iDtemp;
											} else if ((iMonth>parseMonthFr) && (iMonth<parseMonthTo)) {
												iDtemp=1;
												d=31-iDtemp;
											} else if (iMonth == parseMonthTo) {
												iDtemp= parseDayTo;
												d=iDtemp;
												iDtemp=0;
												flgD=true;
											}
										} else {
											iDtemp=1;
										}
										
										//RETRIEVAL
										if (sTblNm.compareToIgnoreCase("aftnrdI")==0 || sTblNm.compareToIgnoreCase("aftnrdO")==0) {
											for (int iDay=31; iDay>=iDtemp; iDay--) {//for (int iDay=iDtemp; iDay<=d+iDtemp; iDay++) {
												if ((flgD) && (iDay>=31)) {
													dd++; 
													if ((d==iDtemp) && (dd>31)) break; 
												} else dd=iDay;
												
												String sDay = Integer.toString(dd);
												if (sDay.length()==1) sDay= sDay.replace(sDay, "0"+sDay);
												
												if (sTblNm.compareToIgnoreCase("aftnrdI")==0||sTblNm.compareToIgnoreCase("aftnrdO")==0) { 
													tblName=sTblNm+iYear+"_"+sMonth+"_"+sDay; } 
												
												//STEP 4: Execute a query
												step4(select, where, order);
												iCnt++; //counter rs
											} //end of for3=TANGGAL
										} 
										//PARTIAL DATA & STATISTIC 
										else {
											String sDay = Integer.toString(dd);
											if (sDay.length()==1) sDay= sDay.replace(sDay, "0"+sDay);
											tblName=sTblNm+iYear+"_"+sMonth;
											
											//STEP 4: Execute a query
											step4(select, where, order);
											iCnt++; //counter rs
										}
									} //end of for2=BULAN
								} //end of for1=YEAR

								progbr.finish();
								
							} catch (java.lang.OutOfMemoryError hs) {
								DialogFactory.openInfo(DialogFactory.LIST_INFO_OUT_OF_MEMORY);
								progbr.finish();
							}
						}
					});
				}
			}.start();
			progbr.begin();
		
			paginating(); 
			if (iJmlData!=0) { Lists.activatePopup(true, sID); }
			
			//STEP 6: Clean-up environment
		    //stmt.close();
		    //conn.close();
	    
		} catch (SQLException se) { //Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception xe) { //Handle errors for Class.forName
			xe.printStackTrace();
		}	
	}
	
	void susunRETRIEVAL(String trace,String str6,String str7) {
		xls();
		if (str6.isEmpty()) str6=" "; 
		if (str7.isEmpty()) str7=" ";

		//str6=""; //kol6: Data
		String sKolom6 = str6; if (sKolom6==null) sKolom6="";
		int index=0,jmldata=0,isub=0;
		String subs[]=null;
		if (sKolom6.contains("\n")) {
			subs = sKolom6.split("\n");
			for (; isub<subs.length; isub++) {
				if (subs[isub].startsWith("0") || subs[isub].startsWith("1") ||
					subs[isub].startsWith("2") || subs[isub].startsWith("3") ||
					subs[isub].startsWith("4") || subs[isub].startsWith("5") ||
					subs[isub].startsWith("6") || subs[isub].startsWith("7") ||
					subs[isub].startsWith("8") || subs[isub].startsWith("9")) {
					
					if (subs[isub].contains(" ")) {
						int indSpace = subs[isub].indexOf(" ");
						String tada = subs[isub].substring(0, indSpace);
						
						if (tada.length()<6) { /*System.out.println(">=6");*/ } 
						else if (tada.length()==6) { index=isub; }
					}
				}
				jmldata++;
			}
		} else { str6=sKolom6; }
		if (jmldata>(index+1)) { str6=subs[index+1]; } 
		else if (jmldata==index) { str6=sKolom6; }
		str6 = str6.replaceAll("\\r\\n|\\r|\\n", " "); //remove CRLF
		
		//str7=""; //kol7: Trace
		String sKolom7 = str7; if (sKolom7==null) sKolom7="";
		if (sKolom7.endsWith("\n")) { sKolom7=sKolom7.substring(0, sKolom7.lastIndexOf("\n")-1); }
		String sub[] = sKolom7.split("\n");
		for (int y=0; y<sub.length; y++) if (sub[y].startsWith("I")) str7=sub[y]; 
		
		//---------------------------------------------------------------------------------------------
		if (trace.compareTo("trace")==0) {
	    	msg+=str6+";"+str7+";";
	    	printableXLS+=msg+"#"; }
		
		else if (trace.compareTo("notrace")==0) {
	    	msg+=str6+";";
	    	printableXLSRet+=msg+"#";
		}
	}
	
	void susunREJECT(String str6,String str7,String str8) {
		xls();
		if (str7.isEmpty()) str7=" "; 
		if (str8.isEmpty()) str8=" ";
		if (!str8.isEmpty() && str8.contains("\n")) {
			int index = str8.indexOf("\n");
			str8 = str8.substring(0, index); 
		}
		msg+=str6+";"+str7+";"+str8+";";
		printableXLS+=msg+"#";
	}
	
	void susunFREE(String strFree) {
		String sFree = strFree;
    	if (sFree.isEmpty()) sFree=" ";
    	String dataXLS = sFree;
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	xls(); 
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#";
	}
	
	void susunALR(String type7a,String type9b,String type13a,String type13b,String type15b,String type15c,String type16a) {//CPL
		xls(); 
    	if (type7a.isEmpty()) type7a=" ";
    	if (type9b.isEmpty()) type9b=" ";
    	if (type13a.isEmpty()) type13a=" ";
    	if (type13b.isEmpty()) type13b=" ";
    	if (type15b.isEmpty()) type15b=" ";
    	if (type15c.isEmpty()) type15c=" "; 
    	if (type16a.isEmpty()) type16a=" ";
		String dataXLS = type7a+";"+type9b+";"+type13a+";"+type13b+";"+type15b+";"+type15c+";"+type16a+";";
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#"; 
	}
	
	void susunRCF(String type7a,String type7c,String type21) {
    	xls(); 
    	if (type7a.isEmpty()) type7a=" ";
    	if (type7c.isEmpty()) type7c=" ";
    	if (type21.isEmpty()) type21=" "; 
    	String dataXLS = type7a+";"+type7c+";"+type21+";";
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#";
	}
	
	void susunFPL(String type7a,String sReg,String filedby,String type9b,String type13a,String type13b,String type15a,String type15b,
			String type15c,String type16a,String type16b,String atd,String sRmk) {
//		System.err.println("sRmk MEGAMEN::" + sRmk + "#");
		xls(); 
    	if (type7a.isEmpty()) type7a=" ";
    	if (sReg.isEmpty()) sReg=" ";
    	if (filedby.isEmpty()) filedby=" ";
    	if (type9b.isEmpty()) type9b=" ";
    	if (type13a.isEmpty()) type13a=" ";
    	if (type13b.isEmpty()) type13b=" ";
    	if (type15a.isEmpty()) type15a=" ";
    	if (type15b.isEmpty()) type15b=" ";
    	if (type15c.isEmpty()) type15c=" ";
    	if (type16a.isEmpty()) type16a=" ";
    	if (type16b.isEmpty()) type16b=" ";
    	if (atd.isEmpty()) atd=" ";
    	if (sRmk.isEmpty()) sRmk=" 3_l_5_4";
    	
    	if (!sReg.isEmpty() && sReg.contains("REG/")) sReg = sReg.replace("REG/", "");
    	if (!sRmk.isEmpty() && sRmk.contains("RMK/")) sRmk = sRmk.replace("RMK/", "");
    	
    	if (!sReg.isEmpty() && sReg.startsWith(" ")) sReg = sReg.replaceFirst(" ", "");
    	if (!sRmk.isEmpty() && sRmk.startsWith(" ")) sRmk = sRmk.replaceFirst(" ", "");
    	if (!sRmk.isEmpty() && sRmk.contains("3_l_5_4")) sRmk = sRmk.replaceFirst("3_l_5_4", " ");
    	
//    	if (type18.isEmpty()) type18=" ";
//    	if (type18.contains("~")) type18= type18.replaceAll("~", "/");
    	String dataXLS = type7a+";"+sReg+";"+filedby+";"+type9b+";"+type13a+";"+type13b+";"+type15a+";"+type15b+";"+type15c+";"+
    		type16a+";"+type16b+";"+atd+";"+sRmk;
    	
//    	String dataXLS = type7a+"/"+vATS.sReg+";"+vATS.filedby+";"+type9b+";"+type13a+"/"+type13b+";"+vATS.type15a+"/"+type15b+";"+type15c+";"+type16a+"/"+vATS.type16b+";"+"ATD;"+vATS.sRmk+";";
    	
    	
    	
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#";
	}
	
	void susunDLA(String type7a,String type13a,String type13b,String type16a,String type18) {//CNL,DEP,RQP,RQS,..CHG
		xls(); 
    	if (type7a.isEmpty()) type7a=" ";
    	if (type13a.isEmpty()) type13a=" ";
    	if (type13b.isEmpty()) type13b=" ";
    	if (type16a.isEmpty()) type16a=" ";
    	if (type18.isEmpty()) type18=" ";
    	if (type18.contains("~")) type18= type18.replaceAll("~", "/");
    	String dataXLS = type7a+";"+type13a+";"+type13b+";"+type16a+";"+type18+";";
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#"; 
	}
	
	void susunARR(String type7a,String type13a,String type13b,String type17a,String type17b) {
		xls(); 
    	if (type7a.isEmpty()) type7a=" ";
    	if (type13a.isEmpty()) type13a=" ";
    	if (type13b.isEmpty()) type13b=" ";
    	if (type17a.isEmpty()) type17a=" ";
    	if (type17b.isEmpty()) type17b=" ";
		String dataXLS = type7a+";"+type13a+";"+type13b+";"+type17a+";"+type17b+";";
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#"; 
	}
	
	void susunCDN(String type7a,String type13a,String type13b,String type16a,String type22) {
		xls(); 
    	if (type7a.isEmpty()) type7a=" ";
    	if (type13a.isEmpty()) type13a=" ";
    	if (type13b.isEmpty()) type13b=" ";
    	if (type16a.isEmpty()) type16a=" ";
    	if (type22.isEmpty()) type22=" "; 
    	String dataXLS = type7a+";"+type13a+";"+type13b+";"+type16a+";"+type22+";";
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#"; 
	}
	
	void susunEST(String type7a,String type13a,String type13b,String type14a,String type14b,String type16a) {
		xls(); 
    	if (type7a.isEmpty()) type7a=" ";
    	if (type13a.isEmpty()) type13a=" ";
    	if (type13b.isEmpty()) type13b=" ";
    	if (type14a.isEmpty()) type14a=" ";
    	if (type14b.isEmpty()) type14b=" ";
    	if (type16a.isEmpty()) type16a=" ";
    	String dataXLS = type7a+";"+type13a+";"+type13b+";"+type14a+";"+type14b+";"+type16a+";";
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#";	
	}
	
	void susunACP(String type7a,String type13a,String type13b,String type16a) {//SPL
		xls(); 
    	if (type7a.isEmpty()) type7a=" ";
    	if (type13a.isEmpty()) type13a=" ";
    	if (type13b.isEmpty()) type13b=" ";
    	if (type16a.isEmpty()) type16a=" ";
		String dataXLS = type7a+";"+type13a+";"+type13b+";"+type16a+";";
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#";	
	}
	
	void susunLAM(String type3b,String type3c) {
		xls(); 
    	if (type3b.isEmpty()) type3b=" ";
    	if (type3c.isEmpty()) type3c=" ";
		String dataXLS = type3b+";"+type3c+";";
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#";
	}
	
	void susunNOTAM(String originator,String sNotamNo,String identifiers,String sRefNotamNo,String sNotamCode,String purpose,String scope,
			String A,String sStatusNotam) {
		xls(); 
    	if (originator.isEmpty()) originator=" ";
    	if (sNotamNo.isEmpty()) sNotamNo=" ";
    	if (identifiers.isEmpty()) identifiers=" ";
    	if (sRefNotamNo.isEmpty()) sRefNotamNo=" ";
    	if (sNotamCode.isEmpty()) sNotamCode=" ";
    	if (purpose.isEmpty()) purpose=" ";
    	if (scope.isEmpty()) scope=" ";
    	if (A.isEmpty()) A=" ";
    	if (sStatusNotam.isEmpty()) sStatusNotam=" ";
    	String dataXLS = originator+";"+sNotamNo+";"+identifiers+";"+sRefNotamNo+";"+sNotamCode+";"+purpose+";"+scope+";"+A+";"+sStatusNotam+";";
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#";
	}
	
	void susunRQNTM(String originator,String sNotamNo,String identifiers,String sRefNotamNo,String A,String sStatusRqntm) {
		xls(); 
    	if (originator.isEmpty()) originator=" ";
    	if (sNotamNo.isEmpty()) sNotamNo=" ";
    	if (identifiers.isEmpty()) identifiers=" ";
    	if (sRefNotamNo.isEmpty()) sRefNotamNo=" ";
    	if (A.isEmpty()) A=" ";
    	if (sStatusRqntm.isEmpty()) sStatusRqntm=" ";
    	String dataXLS = originator+";"+sNotamNo+";"+identifiers+";"+sRefNotamNo+";"+A+";"+sStatusRqntm+";"; 
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#";
	}
	
	void susunACTEXPCHK(String originator,String sNotamNo,String identifiers,String sRefNotamNo,String sNotamCode,String purpose,String scope,
			String A) {
		xls(); 
    	if (originator.isEmpty()) originator=" ";
    	if (sNotamNo.isEmpty()) sNotamNo=" ";
    	if (identifiers.isEmpty()) identifiers=" ";
    	if (sRefNotamNo.isEmpty()) sRefNotamNo=" ";
    	if (sNotamCode.isEmpty()) sNotamCode=" ";
    	if (purpose.isEmpty()) purpose=" ";
    	if (scope.isEmpty()) scope=" ";
    	if (A.isEmpty()) A=" ";
    	String dataXLS = originator+";"+sNotamNo+";"+identifiers+";"+sRefNotamNo+";"+sNotamCode+";"+purpose+";"+scope+";"+A+";";
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#";
	}
	
	void susunSNOWTAM(String state,String sn1,String locind,String timeobs,String optgrp,String sn2) {
		xls(); 
    	if (state.isEmpty()) state=" ";
    	if (sn1.isEmpty()) sn1=" ";
    	if (locind.isEmpty()) locind=" ";
    	if (timeobs.isEmpty()) timeobs=" ";
    	if (optgrp.isEmpty()) optgrp=" ";
    	if (sn2.isEmpty()) sn2=" ";
		String dataXLS = state+";"+sn1+";"+locind+";"+timeobs+";"+optgrp+";"+sn2+";";
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#";
	}
	
	void susunASHTAM(String state,String sn1,String locind,String issued,String optgrp,String sn2) {
		xls(); 
    	if (state.isEmpty()) state=" ";
    	if (sn1.isEmpty()) sn1=" ";
    	if (locind.isEmpty()) locind=" ";
    	if (issued.isEmpty()) issued=" ";
    	if (optgrp.isEmpty()) optgrp=" ";
    	if (sn2.isEmpty()) sn2=" ";
		String dataXLS = state+";"+sn1+";"+locind+";"+issued+";"+optgrp+";"+sn2+";";
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#";
	}
	
	void susunBIRDTAM(String birdnum,String efftime,String exptime,String intlev) {
		xls(); 
    	if (birdnum.isEmpty()) birdnum=" ";
    	if (efftime.isEmpty()) efftime=" ";
    	if (exptime.isEmpty()) exptime=" ";
    	if (intlev.isEmpty()) intlev=" ";
		String dataXLS = birdnum+";"+efftime+";"+exptime+";"+intlev+";";
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#";
	}
	
	void susunRQN(String reqid,String locind,String reqmsg) {
		xls(); 
    	if (reqid.isEmpty()) reqid=" ";
    	if (locind.isEmpty()) locind=" ";
    	if (reqmsg.isEmpty()) reqmsg=" "; 
		String dataXLS = reqid+";"+locind+";"+reqmsg+";";
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#";
	}
	
	void susunMETAR(String origin,String msgID,String location,String timeobs,String issued,String option,String text) {
		xls(); 
    	if (origin.isEmpty()) origin=" ";
    	if (msgID.isEmpty()) msgID=" ";
    	if (location.isEmpty()) location=" ";
    	if (timeobs.isEmpty()) timeobs=" ";
    	if (issued.isEmpty()) issued=" ";
    	if (option.isEmpty()) option=" ";
    	if (text.isEmpty()) text=" "; 
		String dataXLS = origin+";"+msgID+";"+location+";"+timeobs+";"+issued+";"+option+";"+text+";";
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#";
	}
	
	void susunSIGMET(String origin,String msgID,String location,String issued,String option,String text) {
		xls(); 
    	if (origin.isEmpty()) origin=" ";
    	if (msgID.isEmpty()) msgID=" ";
    	if (location.isEmpty()) location=" ";
    	if (issued.isEmpty()) issued=" ";
    	if (option.isEmpty()) option=" ";
    	if (text.isEmpty()) text=" "; 
		String dataXLS = origin+";"+msgID+";"+location+";"+issued+";"+option+";"+text+";";
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#";
	}
	
	void susunAIREP(String origin,String msgID,String issued,String option,String text) {
		xls(); 
    	if (origin.isEmpty()) origin=" ";
    	if (msgID.isEmpty()) msgID=" ";
    	if (issued.isEmpty()) issued=" ";
    	if (option.isEmpty()) option=" ";
    	if (text.isEmpty()) text=" ";
		String dataXLS = origin+";"+msgID+";"+issued+";"+option+";"+text+";";
    	if (dataXLS.endsWith("\n")) {
    		int index = dataXLS.lastIndexOf("\n");
    		dataXLS=dataXLS.substring(0, index);
    	}
    	msg+=dataXLS+";";
    	printableXLS+=msg+"#";
	}
			
	void step4Export(String select,String where,String order,String strTrace) {
		
		try { //biar kalo ada table yang doesn't exist ga mati.
			stmtExport = connExport.createStatement();
			sQueryFind = select+tblName+where+sCriteria+order;
			System.out.println("QueryEx:" + sQueryFind + "#");
			rsExport[iCnt] = stmtExport.executeQuery(sQueryFind);
			rsExport[iCnt].last();
			iJmlData += rsExport[iCnt].getRow();
			System.out.println("**RowEx:" + iJmlData + "#");
			rsExport[iCnt].beforeFirst();
			
			icount=0;
			while (rsExport[iCnt].next() && (rowNo<iMaxExport)) {
				rowNo++;
				
				//---------------------------------------------------------------------------------------
				if (sID.compareTo(Titles.stIncoming)!=0 && sID.compareTo(Titles.stOutgoing)!=0) { 
					str0=rsExport[iCnt].getString("tbl_name"); if (str0==null) str0="";//kol0: tblnm	
					str1=rsExport[iCnt].getString(sIdName); if (str1==null) str1=""; //kol1: idtbl
					str2=""; //kol2: cid
					str3=""; //kol3: csn
					str4=""; //kol4: tms
					str5=rsExport[iCnt].getString("tgl").substring(0, 19); if (str5==null) str5=""; //kol5: tgl
				
				} else if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
					str0="";//kol0: tblnm	
					String tblnm="",yymmdd="",strTgl="";
					strTgl=rsExport[iCnt].getString("tgl").substring(0, 19); if (strTgl==null) strTgl=""; 
					if (!strTgl.isEmpty()&&strTgl.length()==19) { yymmdd=strTgl.substring(0,4)+"_"+strTgl.substring(5,7)+"_"+strTgl.substring(8,10); }
//					tblnm="aftnrdI";
					if (sID.compareTo(Titles.stIncoming)==0) tblnm="aftnrdI";
					else if (sID.compareTo(Titles.stOutgoing)==0) tblnm="aftnrdO";
					str0 = tblnm+yymmdd;
					str1 = rsExport[iCnt].getString(sIdName); if (str1==null) str1=""; //kol1: idtbl
					str2 = rsExport[iCnt].getString("cid"); if (str2==null) str2=""; //kol2: cid
					str3 = rsExport[iCnt].getString("seq"); if (str3==null) str3=""; //kol3: csn

					str4=""; //kol4: tms
					if (sID.compareTo(Titles.stIncoming)==0) {
						String sKolom4 = rsExport[iCnt].getString("data"); if (sKolom4==null) sKolom4="";
						if (!sKolom4.isEmpty() && sKolom4.contains("\n")) {
							sKolom4 = sKolom4.substring(0, sKolom4.indexOf("\n"));
							if (sKolom4.contains(" ") && sKolom4.length()>=14) {
								str4 = sKolom4.substring(sKolom4.indexOf(" ")+1, sKolom4.indexOf(" ")+7);	
							} else str4="";
						}
					} else if (sID.compareTo(Titles.stOutgoing)==0) {
						str4 = rsExport[iCnt].getString("firstdt"); if (str4==null) str4=""; //kol4: tms
						//fix awal firstdt=RCB0001081542xxxxx
						String strOutt = str4;
						if (strOutt.length()>=13) {
							str4 = strOutt.substring(7, 13);
						} else str4="";
						if (str4.compareTo("      ")==0) str4="";
					}
					
					str5=rsExport[iCnt].getString("tgl").substring(0, 19); if (str5==null) str5=""; //kol5: tgl
					
					str6=""; //kol6: Data
					String sKolom6 = rsExport[iCnt].getString("data"); if (sKolom6==null) sKolom6="";
					str6=sKolom6;
					
					str7=""; //kol7: Trace
					String sKolom7 = rsExport[iCnt].getString("trace"); if (sKolom7==null) sKolom7="";
					str7=sKolom7;
				} 
				
				//EXPORT MASING2 TIPE
				if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
					if (strTrace.compareToIgnoreCase("trace")==0) {
						//PDF & WORD
						printable+="data="+str6+";trace="+str7+";"+"#";
						//EXCEL
						susunRETRIEVAL("trace",str6,str7); } 
					
					else if (strTrace.compareToIgnoreCase("notrace")==0) {
						//PDF & WORD
						printableRet+="data="+str6+";#";
						//EXCEL
						susunRETRIEVAL("notrace",str6,str7); }
				}
		
				else if (sID.compareTo(Titles.stReject)==0) {
					str6 = rsExport[iCnt].getString("type_message"); if (str6==null) str6="";
					str7 = rsExport[iCnt].getString("free_text_error_message"); if (str7==null) str7="";
					str8 = rsExport[iCnt].getString("origin_message"); if (str8==null) str8="";
					//PDF & WORD
					printable+="data="+str7+";trace="+str8+";"+"#";
					//EXCEL
			    	susunREJECT(str6,str7,str8); }
				
				else if (sID.compareTo(Titles.stFREETEXT)==0 || sID.compareTo(Titles.stRQM)==0) {
					vATS.setHeader(rsExport[iCnt]);
			    	vATS.setFree(rsExport[iCnt],sID);
			    	vATS.getFREEATS();
			    	//PDF & WORD
			    	String dataPDF = vATS.pfree;
			    	printable+=dataPDF+"\n";
			    	//EXCEL
			    	susunFREE(vATS.strFree); }
					
				else if (sID.compareTo(Titles.stALR)==0) {
					vATS.setHeader(rsExport[iCnt]);
					vATS.set3(rsExport[iCnt]); 
					vATS.set5(rsExport[iCnt]); 
					vATS.set7(rsExport[iCnt]); 
					vATS.set8(rsExport[iCnt]); 
					vATS.set9(rsExport[iCnt]); 
					vATS.set10(rsExport[iCnt]); 
					vATS.set13(rsExport[iCnt]); 
					vATS.set15(rsExport[iCnt]); 
					vATS.set16(rsExport[iCnt]); 
					vATS.set18(rsExport[iCnt]); 
					vATS.set19(rsExport[iCnt]); 
					vATS.set20(rsExport[iCnt]); 
					vATS.msgALR();
					vATS.getALR();
					//PDF & WORD
					String dataPDF = vATS.palr+"\n";
			    	if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n";
			    	//EXCEL
			    	susunALR(vATS.type7a,vATS.type9b,vATS.type13a,vATS.type13b,vATS.type15b,vATS.type15c,vATS.type16a); } 
					
				else if (sID.compareTo(Titles.stRCF)==0) {
					vATS.setHeader(rsExport[iCnt]);
					vATS.set3(rsExport[iCnt]);
					vATS.set7(rsExport[iCnt]);
					vATS.set21(rsExport[iCnt]);
					vATS.msgRCF();
					vATS.getRCF();
					//PDF & WORD
					String dataPDF = vATS.prcf+"\n";
					if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
					printable+=dataPDF+"\n";
					//EXCEL
					susunRCF(vATS.type7a, vATS.type7c, vATS.type21); } 
				
				else if (sID.compareTo(Titles.stFPL)==0) {
					Jdbc.dbatd="";
					ViewATS.sReg="";
					ViewATS.sRmk="";
					vATS.setHeader(rsExport[iCnt]);
					vATS.set3(rsExport[iCnt]); 
					vATS.set7(rsExport[iCnt]); 
					vATS.set8(rsExport[iCnt]); 
					vATS.set9(rsExport[iCnt]); 
					vATS.set10(rsExport[iCnt]); 
					vATS.set13(rsExport[iCnt]); 
					vATS.set15(rsExport[iCnt]); 
					vATS.set16(rsExport[iCnt]); 
					vATS.set18(rsExport[iCnt]); 
					vATS.set19(rsExport[iCnt]); 
					vATS.setSpace(rsExport[iCnt]); 
					vATS.setFiledby(rsExport[iCnt]); 
					vATS.msgFPL();
					vATS.getFPL();
					//PDF & WORD
					String dataPDF = vATS.pfpl+"\n";
			    	if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n";
			    	//EXCEL
			    	Jdbc.select("SELECT type13b from "+tblName+" WHERE type3a='DEP' AND type7a='"+vATS.type7a+"' " +
							"AND type13a='"+vATS.type13a+"' " +
							"AND type16a='"+vATS.type16a+"' " +
							"AND filled_by='"+vATS.filedby+"'", "fpldla");
					susunFPL(vATS.type7a,vATS.sReg,vATS.filedby,vATS.type9b,vATS.type13a,vATS.type13b,vATS.type15a,
							vATS.type15b,vATS.type15c,vATS.type16a,vATS.type16b,Jdbc.dbatd,vATS.sRmk); }
				
				
				else if (sID.compareTo(Titles.stDLA)==0 || sID.compareTo(Titles.stCNL)==0 ||
						sID.compareTo(Titles.stDEP)==0 || sID.compareTo(Titles.stRQP)==0 || 
						sID.compareTo(Titles.stRQS)==0) {
					vATS.setHeader(rsExport[iCnt]);
					vATS.set3(rsExport[iCnt]); 
					vATS.set7(rsExport[iCnt]); 
					vATS.set13(rsExport[iCnt]); 
					vATS.set16(rsExport[iCnt]); 
					vATS.set18(rsExport[iCnt]); 
					vATS.msgDLA();
					vATS.getDLA();
					//PDF & WORD
					String dataPDF = vATS.pdla+"\n";
					if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n";
			    	//EXCEL
			    	susunDLA(vATS.type7a,vATS.type13a,vATS.type13b,vATS.type16a,vATS.type18); } 
				
				else if (sID.compareTo(Titles.stCHG)==0) {
					vATS.setHeader(rsExport[iCnt]);
					vATS.set3(rsExport[iCnt]); 
					vATS.set7(rsExport[iCnt]); 
					vATS.set13(rsExport[iCnt]); 
					vATS.set16(rsExport[iCnt]); 
					vATS.set18(rsExport[iCnt]); 
					vATS.set22(rsExport[iCnt]); 
					vATS.msgCHG();
					vATS.getCHG();
					//PDF & WORD
					String dataPDF = vATS.pchg+"\n";
					if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n";
			    	//EXCEL
			    	susunDLA(vATS.type7a,vATS.type13a,vATS.type13b,vATS.type16a,vATS.type18); } 
				
				else if (sID.compareTo(Titles.stARR)==0) {
					vATS.setHeader(rsExport[iCnt]);
					vATS.set3(rsExport[iCnt]); 
					vATS.set7(rsExport[iCnt]); 
					vATS.set13(rsExport[iCnt]); 
					vATS.set17(rsExport[iCnt]); 
					vATS.msgARR();
					vATS.getARR();
					//PDF & WORD
					String dataPDF = vATS.parr+"\n";
			    	if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n";
			    	//EXCEL
			    	susunARR(vATS.type7a,vATS.type13a,vATS.type13b,vATS.type17a,vATS.type17b); }
				
				else if (sID.compareTo(Titles.stCDN)==0) {
					vATS.setHeader(rsExport[iCnt]);
					vATS.set3(rsExport[iCnt]); 
					vATS.set7(rsExport[iCnt]); 
					vATS.set13(rsExport[iCnt]); 
					vATS.set16(rsExport[iCnt]); 
					vATS.set22(rsExport[iCnt]); 
					vATS.msgCDN();
					vATS.getCDN();
					//PDF & WORD
					String dataPDF = vATS.pcdn+"\n";
					if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n";
			    	//EXCEL
			    	susunCDN(vATS.type7a,vATS.type13a,vATS.type13b,vATS.type16a,vATS.type22); }
				 
				else if (sID.compareTo(Titles.stCPL)==0) {
					vATS.setHeader(rsExport[iCnt]);
					vATS.set3(rsExport[iCnt]); 
					vATS.set7(rsExport[iCnt]); 
					vATS.set8(rsExport[iCnt]); 
					vATS.set9(rsExport[iCnt]); 
					vATS.set10(rsExport[iCnt]); 
					vATS.set13(rsExport[iCnt]); 
					vATS.set14(rsExport[iCnt]); 
					vATS.set15(rsExport[iCnt]); 
					vATS.set16(rsExport[iCnt]); 
					vATS.set18(rsExport[iCnt]); 
					vATS.msgCPL();
					vATS.getCPL();
					//PDF & WORD
					String dataPDF = vATS.pcpl+"\n";
					if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n";
			    	//EXCEL
			    	susunALR(vATS.type7a,vATS.type9b,vATS.type13a,vATS.type13b,vATS.type15b,vATS.type15c,vATS.type16a); }  

				else if (sID.compareTo(Titles.stEST)==0) {
					vATS.setHeader(rsExport[iCnt]);
					vATS.set3(rsExport[iCnt]); 
					vATS.set7(rsExport[iCnt]); 
					vATS.set13(rsExport[iCnt]); 
					vATS.set14(rsExport[iCnt]); 
					vATS.set16(rsExport[iCnt]); 
					vATS.msgEST();
					vATS.getEST();
					//PDF & WORD
					String dataPDF = vATS.pest+"\n";
					if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
					printable+=dataPDF+"\n";
			    	//EXCEL
					susunEST(vATS.type7a,vATS.type13a,vATS.type13b,vATS.type14a,vATS.type14b,vATS.type16a); } 

				else if (sID.compareTo(Titles.stACP)==0) {
					vATS.setHeader(rsExport[iCnt]);
					vATS.set3(rsExport[iCnt]); 
					vATS.set7(rsExport[iCnt]); 
					vATS.set13(rsExport[iCnt]); 
					vATS.set16(rsExport[iCnt]); 
					vATS.msgACP();
					vATS.getACP();
					//PDF & WORD
					String dataPDF = vATS.pacp+"\n";
					if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n";
			    	//EXCEL 
			    	susunACP(vATS.type7a,vATS.type13a,vATS.type13b,vATS.type16a); } 

				else if (sID.compareTo(Titles.stLAM)==0) {
					vATS.setHeader(rsExport[iCnt]);
					vATS.set3(rsExport[iCnt]); 
					vATS.msgLAM();
					vATS.getLAM();
					//PDF & WORD
					String dataPDF = vATS.plam+"\n";
					if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n";
			    	//EXCEL
			    	susunLAM(vATS.type3b, vATS.type3c); } 

				else if (sID.compareTo(Titles.stSPL)==0) {
					vATS.setHeader(rsExport[iCnt]);
					vATS.set3(rsExport[iCnt]); 
					vATS.set7(rsExport[iCnt]); 
					vATS.set13(rsExport[iCnt]); 
					vATS.set16(rsExport[iCnt]); 
					vATS.set18(rsExport[iCnt]); 
					vATS.set19(rsExport[iCnt]); 
					vATS.msgSPL();
					vATS.getSPL();
					//PDF & WORD
					String dataPDF = vATS.pspl+"\n";
					if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n";
			    	//EXCEL 
			    	susunACP(vATS.type7a,vATS.type13a,vATS.type13b,vATS.type16a); } 

				else if (sID.compareTo(Titles.stNOTAM)==0 || sID.compareTo(Titles.stRQNTM)==0 || sID.compareTo(Titles.stCHKNTM)==0 || 
						sID.compareTo(Titles.stACTNTM)==0 || sID.compareTo(Titles.stEXPNTM)==0) {
					vATS.setHeader(rsExport[iCnt]);
					vNOTAM.setNOTAM(rsExport[iCnt]);
					//PDF & WORD
					String dataPDF = vNOTAM.pnotam+"\n";
			    	if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n"; 
			    	//EXCEL
			    	if (sID.compareTo(Titles.stNOTAM)==0 || sID.compareTo(Titles.stEXPNTM)==0) {
			    		susunNOTAM(vATS.originator,vNOTAM.sNotamNo,vNOTAM.identifiers,vNOTAM.sRefNotamNo,vNOTAM.sNotamCode,vNOTAM.purpose,
			    				vNOTAM.scope,vNOTAM.A,vNOTAM.sStatusNotam); }
				
			    	else if (sID.compareTo(Titles.stRQNTM)==0) {
			    		susunRQNTM(vATS.originator,vNOTAM.sNotamNo,vNOTAM.identifiers,vNOTAM.sRefNotamNo,vNOTAM.A,vNOTAM.sStatusRqntm); }
					
			    	else if (sID.compareTo(Titles.stCHKNTM)==0 || sID.compareTo(Titles.stACTNTM)==0) {
						susunACTEXPCHK(vATS.originator,vNOTAM.sNotamNo,vNOTAM.identifiers,vNOTAM.sRefNotamNo,vNOTAM.sNotamCode,vNOTAM.purpose,
			    				vNOTAM.scope,vNOTAM.A); }
			    	
//			    	else if (sID.compareTo(Titles.stCHKNTM)==0 || sID.compareTo(Titles.stACTNTM)==0 || sID.compareTo(Titles.stEXPNTM)==0) {
//						susunACTEXPCHK(vATS.originator,vNOTAM.sNotamNo,vNOTAM.identifiers,vNOTAM.sRefNotamNo,vNOTAM.sNotamCode,vNOTAM.purpose,
//			    				vNOTAM.scope,vNOTAM.A); } 
			    	
				}
				
				else if (sID.compareTo(Titles.stSNOWTAM)==0) {
					vATS.setHeader(rsExport[iCnt]);
					vNOTAM.setSNOWTAM(rsExport[iCnt]);
					//PDF & WORD
					String dataPDF = vNOTAM.psnowtam+"\n";
					if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n";
			    	//EXCEL
			    	susunSNOWTAM(vNOTAM.state,vNOTAM.sn1,vNOTAM.locind,vNOTAM.timeobs,vNOTAM.optgrp,vNOTAM.sn2);
				} 
				
				else if (sID.compareTo(Titles.stASHTAM)==0) {
					vATS.setHeader(rsExport[iCnt]);
					vNOTAM.setASHTAM(rsExport[iCnt]);
					//PDF & WORD
					String dataPDF = vNOTAM.pashtam+"\n";
			    	if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n";
			    	//EXCEL
			    	susunASHTAM(vNOTAM.state,vNOTAM.sn1,vNOTAM.locind,vNOTAM.issued,vNOTAM.optgrp,vNOTAM.sn2);
				} 
				
				else if (sID.compareTo(Titles.stBIRDTAM)==0) {
					vATS.setHeader(rsExport[iCnt]);
					vNOTAM.setBIRDTAM(rsExport[iCnt]);
					//PDF & WORD
					String dataPDF = vNOTAM.pbirdtam+"\n";
					if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n";
			    	//EXCEL
			    	susunBIRDTAM(vNOTAM.birdnum,vNOTAM.efftime,vNOTAM.exptime,vNOTAM.intlev);
				} 
				
				else if (sID.compareTo(Titles.stRQN)==0 || sID.compareTo(Titles.stRQL)==0) {
					vATS.setHeader(rsExport[iCnt]);
					vNOTAM.setRQN(rsExport[iCnt]);
					//PDF & WORD
					String dataPDF = vNOTAM.prqn+"\n";
					if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n";
			    	//EXCEL
			    	susunRQN(vNOTAM.reqid,vNOTAM.locind,vNOTAM.reqmsg);
				} 
				
				else if (sID.compareTo(Titles.stMETAR)==0 || sID.compareTo(Titles.stSPECI)==0) {
					vATS.setHeader(rsExport[iCnt]);
					vMETEO.setMETEO(rsExport[iCnt], sID); 
					//PDF & WORD
					String dataPDF = vMETEO.pmetar+"\n";
					if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n";
			    	//EXCEL
			    	susunMETAR(vMETEO.origin,vMETEO.msgID,vMETEO.location,vMETEO.timeobs,vMETEO.issued,vMETEO.option,vMETEO.text); }
				
				else if (sID.compareTo(Titles.stSIGMET)==0 || sID.compareTo(Titles.stAIRMET)==0 || sID.compareTo(Titles.stWINSWAR)==0) { 
					vATS.setHeader(rsExport[iCnt]);
					vMETEO.setMETEO(rsExport[iCnt], sID); 
					//PDF & WORD
					String dataPDF = vMETEO.psigmet+"\n";
					if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n";
			    	//EXCEL
			    	susunSIGMET(vMETEO.origin,vMETEO.msgID,vMETEO.location,vMETEO.issued,vMETEO.option,vMETEO.text); } 
				
				else if (sID.compareTo(Titles.stAIREP)==0 || sID.compareTo(Titles.stTAFOR)==0 ||
						sID.compareTo(Titles.stARFOR)==0 || sID.compareTo(Titles.stROFOR)==0 ||
						sID.compareTo(Titles.stWINTEM)==0 || sID.compareTo(Titles.stADV)==0 ||
						sID.compareTo(Titles.stSYNOP)==0 || sID.compareTo(Titles.stWARSYN)==0) { 
					vATS.setHeader(rsExport[iCnt]);
					vMETEO.setMETEO(rsExport[iCnt], sID); 
					//PDF & WORD
					String dataPDF = vMETEO.pairep+"\n";
					if (dataPDF.endsWith("\n")) {
			    		int index = dataPDF.lastIndexOf("\n");
			    		dataPDF=dataPDF.substring(0, index);
			    	}
			    	printable+=dataPDF+"\n";
			    	//EXCEL
					susunAIREP(vMETEO.origin,vMETEO.msgID,vMETEO.issued,vMETEO.option,vMETEO.text); } 
			
				progbr.bar.setSelection(rowNo);
			} //end of while
		} catch (SQLException s) { // biar kalo ada table yang doesn't exist ga mati.
			System.out.println("**InfoEx:" + s.getMessage() + "#");
		}
	}
	
	void step4(String select,String where,String order) {
		try { //biar kalo ada table yang doesn't exist ga mati.
			stmt = conn.createStatement();
			sQueryFind = select+tblName+where+sCriteria+order;
			System.out.println("Query:" + sQueryFind + "#");
			rs[iCnt] = stmt.executeQuery(sQueryFind);
			rs[iCnt].last();
			iJmlData += rs[iCnt].getRow();
			System.out.println("**Rows:" + iJmlData + "#");
			rs[iCnt].beforeFirst();
		
			String str0="",str1="",str2="",str3="",str4="",str5="",str6="",str7="",str8="",str9="",str10="",
			str11="",str12="",str13="",str14="",str15="",str16="",str17="",str18="";

//			TableItem item=null,iin=null;//,iout=null;//nyoba statistic
			icount=0;  
			
			while (rs[iCnt].next() && (rowNo<iLimitRow)) {
				rowNo++;
				
//				if (sID.compareTo(Titles.stOutgoing)!=0 && sID.compareTo(Titles.stIncoming)!=0 ) 
//				{ item = new TableItem(Lists.table, SWT.NONE); }
				//---------------------------------------------------------------------------------------
				if (sID.compareTo(Titles.stIncoming)!=0 && sID.compareTo(Titles.stOutgoing)!=0) { 
					item = new TableItem(Lists.table, SWT.NONE); 
					
					str0=rs[iCnt].getString("tbl_name"); if (str0==null) str0="";//kol0: tblnm	
					str1=rs[iCnt].getString(sIdName); if (str1==null) str1=""; //kol1: idtbl
					str2=""; //kol2: cid
					str3=""; //kol3: csn
					str4=""; //kol4: tms
					str5=rs[iCnt].getString("tgl").substring(0, 19); if (str5==null) str5=""; //kol5: tgl
				
					item.setText(0, str0);
					item.setText(1, str1);
					item.setText(2, str2);
					item.setText(3, str3);
					item.setText(4, str4);
					item.setText(5, str5);
					
				} else if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
					str0="";//kol0: tblnm	
					String tblnm="",yymmdd="",strTgl="";
					strTgl=rs[iCnt].getString("tgl").substring(0, 19); if (strTgl==null) strTgl=""; 
					if (!strTgl.isEmpty()&&strTgl.length()==19) { yymmdd=strTgl.substring(0,4)+"_"+strTgl.substring(5,7)+"_"+strTgl.substring(8,10); }
//					tblnm="aftnrdI";
					if (sID.compareTo(Titles.stIncoming)==0) tblnm="aftnrdI";
					else if (sID.compareTo(Titles.stOutgoing)==0) tblnm="aftnrdO";
					str0 = tblnm+yymmdd;
					str1 = rs[iCnt].getString(sIdName); if (str1==null) str1=""; //kol1: idtbl
					str2 = rs[iCnt].getString("cid"); if (str2==null) str2=""; //kol2: cid
					str3 = rs[iCnt].getString("seq"); if (str3==null) str3=""; //kol3: csn
					str4=""; //kol4: tms
					if (sID.compareTo(Titles.stIncoming)==0) {
						String sKolom4 = rs[iCnt].getString("data"); if (sKolom4==null) sKolom4="";
						if (!sKolom4.isEmpty() && sKolom4.contains("\n")) {
							sKolom4 = sKolom4.substring(0, sKolom4.indexOf("\n"));
							if (sKolom4.contains(" ") && sKolom4.length()>=14) {
								str4 = sKolom4.substring(sKolom4.indexOf(" ")+1, sKolom4.indexOf(" ")+7);	
							} else str4="";
						}
					} else if (sID.compareTo(Titles.stOutgoing)==0) {
						str4 = rs[iCnt].getString("firstdt"); if (str4==null) str4=""; //kol4: tms
						//fix awal firstdt=RCB0001081542xxxxx
						String strOutt = str4;
						if (strOutt.length()>=13) {
							str4 = strOutt.substring(7, 13);
						} else str4="";
						if (str4.compareTo("      ")==0) str4="";
					}
						
					str5=rs[iCnt].getString("tgl").substring(0, 19); if (str5==null) str5=""; //kol5: tgl
					
					str6=""; //kol6: Data
					String sKolom6 = rs[iCnt].getString("data"); if (sKolom6==null) sKolom6="";
					int index=0,jmldata=0,isub=0;
					String subs[]=null;
					if (sKolom6.contains("\n")) {
						subs = sKolom6.split("\n");
						for (; isub<subs.length; isub++) {
							if (subs[isub].startsWith("0") || subs[isub].startsWith("1") ||
								subs[isub].startsWith("2") || subs[isub].startsWith("3") ||
								subs[isub].startsWith("4") || subs[isub].startsWith("5") ||
								subs[isub].startsWith("6") || subs[isub].startsWith("7") ||
								subs[isub].startsWith("8") || subs[isub].startsWith("9")) {
								
								if (subs[isub].contains(" ")) {
									int indSpace = subs[isub].indexOf(" ");
									String tada = subs[isub].substring(0, indSpace);
									
									if (tada.length()<6) { /*System.out.println(">=6");*/ } 
									else if (tada.length()==6) { index=isub; }
								}
							}
							jmldata++;
						}
					} else { str6=sKolom6; }
					if (jmldata>(index+1)) { str6=subs[index+1]; } 
					else if (jmldata==index) { str6=sKolom6; }
					str6 = str6.replaceAll("\\r\\n|\\r|\\n", " "); //remove CRLF
					
					str7=""; //kol7: Trace
					String sKolom7 = rs[iCnt].getString("trace"); if (sKolom7==null) sKolom7="";
					if (sKolom7.endsWith("\n")) { sKolom7=sKolom7.substring(0, sKolom7.lastIndexOf("\n")-1); }
					String sub[] = sKolom7.split("\n");
					for (int y=0; y<sub.length; y++) if (sub[y].startsWith("I")) str7=sub[y]; 
					
					iin = new TableItem(Lists.table, SWT.NONE);
					iin.setText(0, str0);
					iin.setText(1, str1);
					iin.setText(2, str2);
					iin.setText(3, str3);
					iin.setText(4, str4);
					iin.setText(5, str5);
					iin.setText(6, str6);	
					iin.setText(7, str7);	
					players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7));														
				}

				//------------------------------ Reject ------------------------------
				if (sID.compareTo(Titles.stReject)==0) {
					str6 = rs[iCnt].getString("type_message"); if (str6==null) str6="";
					str7 = rs[iCnt].getString("free_text_error_message"); if (str7==null) str7="";
					String msg = rs[iCnt].getString("origin_message"); if (msg==null) msg="";
					
					if (msg.contains("\n")) {
						String subs[] = msg.split("\n");
						for (int isub=0; isub<subs.length; isub++) { str8=subs[0]; }
					} else str8=msg;
					
					item.setText(6, str6);	
					item.setText(7, str7);	
					item.setText(8, str8);	
					players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8)); }	
				
				//------------------------------ ATS MESSAGES ------------------------------
				else if (sID.compareTo(Titles.stFREETEXT)==0 || 
						sID.compareTo(Titles.stVOL)==0 || sID.compareTo(Titles.stRQM)==0) {
				
					if (sID.compareTo(Titles.stRQM)==0) { str6 = rs[iCnt].getString("request_message"); }
					else if (sID.compareTo(Titles.stVOL)==0) { str6 = rs[iCnt].getString("text"); }
					else { str6 = rs[iCnt].getString("manual_ats"); }
					
					if (str6==null) str6="";
					if (str6.contains("`")) str6 = str6.replace("`", "'");
					if (str6.contains("\n")) str6=str6.substring(0, str6.indexOf("\n"));
					str6 = str6.replaceAll("\\r\\n|\\r|\\n", " "); //remove CRLF
					if (str6.length()>=50) str6=str6.substring(0,50).concat(" ...");
					
					
					item.setText(6, str6);	
					players.add(new Player(str0,str1,str2,str3,str4,str5,str6)); }
				
				else if (sID.compareTo(Titles.stLAM)==0) {
					str6 = rs[iCnt].getString("type3b"); if (str6==null) str6="";
					str7 = rs[iCnt].getString("type3c"); if (str7==null) str7="";
					
					item.setText(6, str6);	
					item.setText(7, str7);	
					players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7)); }
				
				else if (sID.compareTo(Titles.stRCF)==0) {
					str6 = rs[iCnt].getString("type7a"); if (str6==null) str6="";
					str7 = rs[iCnt].getString("type7c"); if (str7==null) str7="";
					str8 = rs[iCnt].getString("type21"); if (str8==null) str8="";

					item.setText(6, str6);	
					item.setText(7, str7);	
					item.setText(8, str8);	
					players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8)); }
				
				else if (sID.compareTo(Titles.stACP)==0 || sID.compareTo(Titles.stSPL)==0) {
					str6 = rs[iCnt].getString("type7a"); if (str6==null) str6="";
					str7 = rs[iCnt].getString("type13a"); if (str7==null) str7="";
					str8 = rs[iCnt].getString("type13b"); if (str8==null) str8="";
					str9 = rs[iCnt].getString("type16a"); if (str9==null) str9="";
					
					item.setText(6, str6);	
					item.setText(7, str7);	
					item.setText(8, str8);	
					item.setText(9, str9);	
					players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9)); }
				
				else if (sID.compareTo(Titles.stDLA)==0 || sID.compareTo(Titles.stCHG)==0 ||
						sID.compareTo(Titles.stCNL)==0 || sID.compareTo(Titles.stDEP)==0 ||
						sID.compareTo(Titles.stRQP)==0 || sID.compareTo(Titles.stRQS)==0) {
						
						str6 = rs[iCnt].getString("type7a"); if (str6==null) str6="";
						str7 = rs[iCnt].getString("type13a"); if (str7==null) str7="";
						str8 = rs[iCnt].getString("type13b"); if (str8==null) str8="";
						str9 = rs[iCnt].getString("type16a"); if (str9==null) str9="";
						str10 = rs[iCnt].getString("type18"); if (str10==null) str10="";
						
						item.setText(6, str6);	
						item.setText(7, str7);	
						item.setText(8, str8);	
						item.setText(9, str9);	
						item.setText(10, str10);	
						players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10)); }
					
				else if (sID.compareTo(Titles.stCDN)==0) {
					str6 = rs[iCnt].getString("type7a"); if (str6==null) str6="";
					str7 = rs[iCnt].getString("type13a"); if (str7==null) str7="";
					str8 = rs[iCnt].getString("type13b"); if (str8==null) str8="";
					str9 = rs[iCnt].getString("type16a"); if (str9==null) str9="";
					str10 = rs[iCnt].getString("type22"); if (str10==null) str10="";
					
					item.setText(6, str6);	
					item.setText(7, str7);	
					item.setText(8, str8);	
					item.setText(9, str9);	
					item.setText(10, str10);	
					players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10)); }
				
				else if (sID.compareTo(Titles.stARR)==0) {
					str6 = rs[iCnt].getString("type7a"); if (str6==null) str6="";
					str7 = rs[iCnt].getString("type13a"); if (str7==null) str7="";
					str8 = rs[iCnt].getString("type13b"); if (str8==null) str8="";
					str9 = rs[iCnt].getString("type17a"); if (str9==null) str9="";
					str10 = rs[iCnt].getString("type17b"); if (str10==null) str10="";
					
					item.setText(6, str6);	
					item.setText(7, str7);	
					item.setText(8, str8);	
					item.setText(9, str9);	
					item.setText(10, str10);	
					players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10)); }
					
				else if (sID.compareTo(Titles.stEST)==0) {
					str6 = rs[iCnt].getString("type7a"); if (str6==null) str6="";
					str7 = rs[iCnt].getString("type13a"); if (str7==null) str7="";
					str8 = rs[iCnt].getString("type13b"); if (str8==null) str8="";
					str9 = rs[iCnt].getString("type14a"); if (str9==null) str9="";
					str10 = rs[iCnt].getString("type14b"); if (str10==null) str10="";
					str11 = rs[iCnt].getString("type16a"); if (str11==null) str11="";
					
					item.setText(6, str6);	
					item.setText(7, str7);	
					item.setText(8, str8);	
					item.setText(9, str9);	
					item.setText(10, str10);	
					item.setText(11, str11);	
					players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11)); }
				
				else if (sID.compareTo(Titles.stALR)==0 || sID.compareTo(Titles.stCPL)==0) {
					str6 = rs[iCnt].getString("type7a"); if (str6==null) str6="";
					str7 = rs[iCnt].getString("type9b"); if (str7==null) str7="";
					str8 = rs[iCnt].getString("type13a"); if (str8==null) str8="";
					str9 = rs[iCnt].getString("type13b"); if (str9==null) str9="";
					str10 = rs[iCnt].getString("type15b"); if (str10==null) str10="";
					str11 = rs[iCnt].getString("type15c"); if (str11==null) str11="";
					str12 = rs[iCnt].getString("type16a"); if (str12==null) str12="";
					
					item.setText(6, str6);	
					item.setText(7, str7);	
					item.setText(8, str8);	
					item.setText(9, str9);	
					item.setText(10, str10);	
					item.setText(11, str11);	
					item.setText(12, str12);	
					players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12)); }
				
				else if (sID.compareTo(Titles.stFPL)==0) {
					String tbl_name = rs[iCnt].getString("tbl_name"); if (tbl_name==null) tbl_name="";
					String tipe18 = rs[iCnt].getString("type18"); if (tipe18==null) tipe18="";
					str17="";
					ViewATS.sReg="";
					ViewATS.sRmk="";
					ViewATS.form18(tipe18);
					
					str6 = rs[iCnt].getString("type7a"); if (str6==null) str6="";
					str7 = ViewATS.sReg;
					str8 = rs[iCnt].getString("filled_by"); if (str8==null) str8="";
					str9 = rs[iCnt].getString("type9b"); if (str9==null) str9="";
					str10 = rs[iCnt].getString("type13a"); if (str10==null) str10="";
					str11 = rs[iCnt].getString("type13b"); if (str11==null) str11="";
					str12 = rs[iCnt].getString("type15a"); if (str12==null) str12="";
					str13 = rs[iCnt].getString("type15b"); if (str13==null) str13="";
					str14 = rs[iCnt].getString("type15c"); if (str14==null) str14="";
					str15 = rs[iCnt].getString("type16a"); if (str15==null) str15="";
					str16 = rs[iCnt].getString("type16b"); if (str16==null) str16=""; 
					str18 = ViewATS.sRmk;
					String tgl = rs[iCnt].getString("tgl"); if (tgl==null) tgl="";
					Jdbc.select("SELECT type13b from "+tbl_name+" WHERE type3a='DEP' AND type7a='"+str6+"' AND type13a='"+str10+"' " +
							"AND type16a='"+str15+"' AND filled_by='"+str8+"'", "fpldla");
					str17 = Jdbc.dbatd;
					
					if (!str7.isEmpty() && str7.contains("REG/")) str7 = str7.replace("REG/", "");
					if (!str18.isEmpty() && str18.contains("RMK/")) str18 = str18.replace("RMK/", "");
					
					item.setText(6, str6);	
					item.setText(7, str7);	
					item.setText(8, str8);	
					item.setText(9, str9);	
					item.setText(10, str10);	
					item.setText(11, str11);	
					item.setText(12, str12);	
					item.setText(13, str13);
					item.setText(14, str14);
					item.setText(15, str15);
					item.setText(16, str16);
					item.setText(17, str17);
					item.setText(18, str18);
					players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12,str13,str14,str15,str16,str17,str18)); }
				//------------------------------ NOTAM MESSAGES ------------------------------
				else if (sID.compareTo(Titles.stNOTAM)==0 || sID.compareTo(Titles.stACTNTM)==0 || 
						sID.compareTo(Titles.stEXPNTM)==0 || sID.compareTo(Titles.stCHKNTM)==0 || 
						sID.compareTo(Titles.stRQNTM)==0) {
					
					String serie = rs[iCnt].getString("ntm_id_serie"); if (serie==null) serie="";
					String seq = rs[iCnt].getString("ntm_id_sequence"); if (seq==null) seq="";
					String years = rs[iCnt].getString("ntm_id_years"); if (years==null) years="";
					String ref_serie = rs[iCnt].getString("ref_ntm_id_serie"); if (ref_serie==null) ref_serie="";
					String ref_seq = rs[iCnt].getString("ref_ntm_id_sequence"); if (ref_seq==null) ref_seq="";
					String ref_years = rs[iCnt].getString("ref_ntm_id_years"); if (ref_years==null) ref_years="";
					String code23 = rs[iCnt].getString("code_23"); if (code23==null) code23="";
					String code45 = rs[iCnt].getString("code_45"); if (code45==null) code45="";
					
					str6 = rs[iCnt].getString("originator"); if (str6==null) str6="";
					str7 = serie+seq+"/"+years;
					if (str7.compareTo("/")==0) str7="";
					str8 = rs[iCnt].getString("identifiers"); if (str8==null) str8="";
					str9 = ref_serie+ref_seq+"/"+ref_years;
					if (str9.compareTo("/")==0) str9="";
					str10 = code23+code45;
					str11 = rs[iCnt].getString("purpose"); if (str11==null) str11="";
					str12 = rs[iCnt].getString("scope"); if (str12==null) str12="";
					str13 = rs[iCnt].getString("A"); if (str13==null) str13="";
					
					item.setText(6, str6);	
					item.setText(7, str7);
					item.setText(8, str8);
					item.setText(9, str9);
					
//					if (sID.compareTo(Titles.stCHKNTM)==0 || sID.compareTo(Titles.stACTNTM)==0 ||
//							sID.compareTo(Titles.stEXPNTM)==0) {
//						item.setText(10, str10);
//						item.setText(11, str11);
//						item.setText(12, str12);
//						item.setText(13, str13);
//						players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12,str13)); }
					
					if (sID.compareTo(Titles.stCHKNTM)==0 || sID.compareTo(Titles.stACTNTM)==0) {
						item.setText(10, str10);
						item.setText(11, str11);
						item.setText(12, str12);
						item.setText(13, str13);
						players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12,str13)); }
					
					if (sID.compareTo(Titles.stNOTAM)==0 || sID.compareTo(Titles.stEXPNTM)==0) {
						str14 = rs[iCnt].getString("note"); if (str14==null) str14="";
						
						item.setText(10, str10);
						item.setText(11, str11);
						item.setText(12, str12);
						item.setText(13, str13);
						item.setText(14, str14);
						players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12,str13,str14)); }
					
					if (sID.compareTo(Titles.stRQNTM)==0) {
						str10 = str13;
						str11 = rs[iCnt].getString("n_rqntm"); if (str11==null) str11="";
						item.setText(10, str10);
						item.setText(11, str11);
						players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11)); } }
				else if (sID.compareTo(Titles.stSNOWTAM)==0) {
					str6 = rs[iCnt].getString("state"); if (str6==null) str6="";
					str7 = rs[iCnt].getString("sn_serial_nr"); if (str7==null) str7="";
					str8 = rs[iCnt].getString("location_indicator"); if (str8==null) str8="";
					str9 = rs[iCnt].getString("oservation"); if (str9==null) str9="";
					str10 = rs[iCnt].getString("opt_group"); if (str10==null) str10="";
					str11 = rs[iCnt].getString("sn_serial_nr"); if (str11==null) str11="";
					
					item.setText(6, str6);	
					item.setText(7, str7);	
					item.setText(8, str8);	
					item.setText(9, str9);	
					item.setText(10, str10);	
					item.setText(11, str11);	
					players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11)); }
				else if (sID.compareTo(Titles.stASHTAM)==0) {
					str6 = rs[iCnt].getString("state"); if (str6==null) str6="";
					str7 = rs[iCnt].getString("sn_serial_nr"); if (str7==null) str7="";
					str8 = rs[iCnt].getString("location"); if (str8==null) str8="";
					str9 = rs[iCnt].getString("issued"); if (str9==null) str9="";
					str10 = rs[iCnt].getString("opt_group"); if (str10==null) str10="";
					str11 = rs[iCnt].getString("sn_serial_nr"); if (str11==null) str11="";
					
					item.setText(6, str6);	
					item.setText(7, str7);	
					item.setText(8, str8);	
					item.setText(9, str9);	
					item.setText(10, str10);	
					item.setText(11, str11);	
					players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11)); }
				else if (sID.compareTo(Titles.stBIRDTAM)==0) {
					str6 = rs[iCnt].getString("birdtam_nr"); if (str6==null) str6="";
					str7 = rs[iCnt].getString("effective_time"); if (str7==null) str7="";
					str8 = rs[iCnt].getString("expiration_time"); if (str8==null) str8="";
					str9 = rs[iCnt].getString("intensity_level"); if (str9==null) str9="";
					
					item.setText(6, str6);	
					item.setText(7, str7);	
					item.setText(8, str8);	
					item.setText(9, str9);	
					players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9)); }
				else if (sID.compareTo(Titles.stRQN)==0 || sID.compareTo(Titles.stRQL)==0) {
					str6 = rs[iCnt].getString("request_id"); if (str6==null) str6="";
					str7 = rs[iCnt].getString("nof_or_type"); if (str7==null) str7="";
					str8 = rs[iCnt].getString("request_message"); if (str8==null) str8="";
					
					item.setText(6, str6);	
					item.setText(7, str7);	
					item.setText(8, str8);	
					players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8)); }	
				//------------------------------ METEO MESSAGES ------------------------------
				else if (sID.compareTo(Titles.stMETAR)==0 || sID.compareTo(Titles.stSPECI)==0 ||
						sID.compareTo(Titles.stSIGMET)==0 || sID.compareTo(Titles.stAIRMET)==0 ||
						sID.compareTo(Titles.stAIREP)==0 || sID.compareTo(Titles.stTAFOR)==0 ||
						sID.compareTo(Titles.stSYNOP)==0 || sID.compareTo(Titles.stARFOR)==0 ||
						sID.compareTo(Titles.stROFOR)==0 || sID.compareTo(Titles.stWINSWAR)==0 ||
						sID.compareTo(Titles.stWINTEM)==0 || sID.compareTo(Titles.stADV)==0 ||
						sID.compareTo(Titles.stWARSYN)==0) {
					
					str6 = rs[iCnt].getString("originator"); if (str6==null) str6="";
					String msg1 = rs[iCnt].getString("message_id_12"); if (msg1==null) msg1="";
					String msg2 = rs[iCnt].getString("message_id_34"); if (msg2==null) msg2="";
					String msg3 = rs[iCnt].getString("message_id_56"); if (msg3==null) msg3="";
					str7 = msg1+msg2+msg3;
					str10 = rs[iCnt].getString("issued_message"); if (str10==null) str10="";
					str11 = rs[iCnt].getString("correction_meteo"); if (str11==null) str11="";
					
					item.setText(6, str6);	
					item.setText(7, str7);
					
					if (sID.compareTo(Titles.stMETAR)==0 || sID.compareTo(Titles.stSPECI)==0) { 
						str8 = rs[iCnt].getString("Location"); if (str8==null) str8="";	
						str9 = rs[iCnt].getString("time_observation"); if (str9==null) str9="";
						str12 = rs[iCnt].getString("text"); if (str12==null) str12="";	
						
						if (str12.contains("\n")) {
							str12=str12.substring(0, str12.indexOf("\n"));
							if (str12.length()>=50) str12=str12.substring(0,50).concat(" ...");
						} else {
							if (str12.length()>=50) str12=str12.substring(0,50).concat(" ...");
						}
						
						item.setText(8, str8);	
						item.setText(9, str9);
						item.setText(10, str10);
						item.setText(11, str11);
						item.setText(12, str12);
						players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12)); }

					else if (sID.compareTo(Titles.stSIGMET)==0 || sID.compareTo(Titles.stAIRMET)==0 ||
							sID.compareTo(Titles.stWINSWAR)==0) { 
						
						if (sID.compareTo(Titles.stWINSWAR)==0) { 
							str8 = rs[iCnt].getString("location"); if (str8==null) str8=""; }
						else { str8 = rs[iCnt].getString("Location_ats"); if (str8==null) str8=""; }
								
						str9=str10; str10=str11;
						str11 = rs[iCnt].getString("text"); if (str11==null) str11="";	
						if (str11.contains("\n")) {
							str11=str11.substring(0, str11.indexOf("\n"));
							if (str11.length()>=50) str11=str11.substring(0,50).concat(" ...");
						} else {
							if (str11.length()>=50) str11=str11.substring(0,50).concat(" ...");
						}
						
						item.setText(8, str8);	
						item.setText(9, str9);
						item.setText(10, str10);
						item.setText(11, str11);
						players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11)); }
					
					else if (sID.compareTo(Titles.stAIREP)==0 || sID.compareTo(Titles.stTAFOR)==0 ||
							sID.compareTo(Titles.stARFOR)==0 || sID.compareTo(Titles.stROFOR)==0 ||
							sID.compareTo(Titles.stWINTEM)==0 || sID.compareTo(Titles.stADV)==0 ||
							sID.compareTo(Titles.stSYNOP)==0 || sID.compareTo(Titles.stWARSYN)==0) { 
						str8=str10; str9=str11;
						if (sID.compareTo(Titles.stWARSYN)==0) { 
							str10 = rs[iCnt].getString("warning_synop"); if (str10==null) str10="";	
						} else { str10 = rs[iCnt].getString("text"); if (str10==null) str10=""; }

						if (str10.contains("\n")) {
							str10=str10.substring(0, str10.indexOf("\n"));
							if (str10.length()>=50) str10=str10.substring(0,50).concat(" ...");
						} else {
							if (str10.length()>=50) str10=str10.substring(0,50).concat(" ...");
						}
						
						item.setText(8, str8);	
						item.setText(9, str9);
						item.setText(10, str10);
						players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10)); }
				}
				
				progbr.bar.setSelection(rowNo);
			
//				if (rowNo>=Integer.parseInt(rff.ReadInputFile1(MainForm.sFolder+"maxquery.txt"))) { //kalo 1 tbl OK, kalo banyak muncul bbrp kali
////					DialogFactory.openInfo(DialogFactory.LIST_INFO_FILL_ANOTHER_CRITERIA);
//					System.out.println("masuk sini gan.");
//					break;
//				}
			} //end of while
			
		} catch (SQLException s) { // biar kalo ada table yang doesn't exist ga mati.
			System.out.println("**Info:" + s.getMessage() + "#");
		}
	}
	
	void Next() {
		final cProgressBar progbr = new cProgressBar("Selecting data ...");
		new Thread() {
			public void run() {
				if (AmscSplashScreen2.display.isDisposed()) return;
				AmscSplashScreen2.display.asyncExec(new Runnable() {
					public void run() {
						try {
							progbr.create(iLimitRow);
						    players.clear();
							rowNo = 0;
							
							for (int i=0; i<iCnt; i++) {
								try {
									if (rs[i]!=null) {
										rs[i].beforeFirst();
										
										String str0="",str1="",str2="",str3="",str4="",str5="",str6="",str7="",str8="",str9="",str10="",str11="",
										str12="",str13="",str14="",str15="",str16="",str17="",str18="";
										
										TableItem item=null,iin=null;//,iout=null;
										while (rs[i].next()) {
											rowNo++;
											
											
											if ((rowNo>(qq*iLimitRow)) && (rowNo<=((qq*iLimitRow)+iLimitRow))) {
												
												//---------------------------------------------------------------------------------------
												if (sID.compareTo(Titles.stIncoming)!=0 && sID.compareTo(Titles.stOutgoing)!=0) { 
													item = new TableItem(Lists.table, SWT.NONE); 
													
													str0=rs[i].getString("tbl_name"); if (str0==null) str0="";//kol0: tblnm	
													str1=rs[i].getString(sIdName); if (str1==null) str1=""; //kol1: idtbl
													str2=""; //kol2: cid
													str3=""; //kol3: csn
													str4=""; //kol4: tms
													str5=rs[i].getString("tgl").substring(0, 19); if (str5==null) str5=""; //kol5: tgl
												
													item.setText(0, str0);
													item.setText(1, str1);
													item.setText(2, str2);
													item.setText(3, str3);
													item.setText(4, str4);
													item.setText(5, str5);
													
												} else if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
													str0="";//kol0: tblnm	
													String tblnm="",yymmdd="",strTgl="";
													strTgl=rs[i].getString("tgl").substring(0, 19); if (strTgl==null) strTgl=""; 
													if (!strTgl.isEmpty()&&strTgl.length()==19) { yymmdd=strTgl.substring(0,4)+"_"+strTgl.substring(5,7)+"_"+strTgl.substring(8,10); }
//													tblnm="aftnrdI";
													if (sID.compareTo(Titles.stIncoming)==0) tblnm="aftnrdI";
													else if (sID.compareTo(Titles.stOutgoing)==0) tblnm="aftnrdO";
													str0 = tblnm+yymmdd;
													str1 = rs[i].getString(sIdName); if (str1==null) str1=""; //kol1: idtbl
													str2 = rs[i].getString("cid"); if (str2==null) str2=""; //kol2: cid
													str3 = rs[i].getString("seq"); if (str3==null) str3=""; //kol3: csn
													str4=""; //kol4: tms
													if (sID.compareTo(Titles.stIncoming)==0) {
														String sKolom4 = rs[i].getString("data"); if (sKolom4==null) sKolom4="";
														if (!sKolom4.isEmpty() && sKolom4.contains("\n")) {
															sKolom4 = sKolom4.substring(0, sKolom4.indexOf("\n"));
															if (sKolom4.contains(" ") && sKolom4.length()>=14) {
																str4 = sKolom4.substring(sKolom4.indexOf(" ")+1, sKolom4.indexOf(" ")+7);	
															} else str4="";
														}
													} else if (sID.compareTo(Titles.stOutgoing)==0) {
														str4 = rs[i].getString("firstdt"); if (str4==null) str4=""; //kol4: tms
														//fix awal firstdt=RCB0001081542xxxxx
														String strOutt = str4;
														if (strOutt.length()>=13) {
															str4 = strOutt.substring(7, 13);
														} else str4="";
														if (str4.compareTo("      ")==0) str4="";
													}
														
													str5=rs[i].getString("tgl").substring(0, 19); if (str5==null) str5=""; //kol5: tgl
													
													str6=""; //kol6: Data
													String sKolom6 = rs[i].getString("data"); if (sKolom6==null) sKolom6="";
													int index=0,jmldata=0,isub=0;
													String subs[]=null;
													if (sKolom6.contains("\n")) {
														subs = sKolom6.split("\n");
														for (; isub<subs.length; isub++) {
															if (subs[isub].startsWith("0") || subs[isub].startsWith("1") ||
																subs[isub].startsWith("2") || subs[isub].startsWith("3") ||
																subs[isub].startsWith("4") || subs[isub].startsWith("5") ||
																subs[isub].startsWith("6") || subs[isub].startsWith("7") ||
																subs[isub].startsWith("8") || subs[isub].startsWith("9")) {
																
																if (subs[isub].contains(" ")) {
																	int indSpace = subs[isub].indexOf(" ");
																	String tada = subs[isub].substring(0, indSpace);
																	
																	if (tada.length()<6) { /*System.out.println(">=6");*/ } 
																	else if (tada.length()==6) { index=isub; }
																}
															}
															jmldata++;
														}
													} else { str6=sKolom6; }
													if (jmldata>(index+1)) { str6=subs[index+1]; } 
													else if (jmldata==index) { str6=sKolom6; }
													str6 = str6.replaceAll("\\r\\n|\\r|\\n", " "); //remove CRLF
													
													str7=""; //kol7: Trace
													String sKolom7 = rs[i].getString("trace"); if (sKolom7==null) sKolom7="";
													if (sKolom7.endsWith("\n")) { sKolom7=sKolom7.substring(0, sKolom7.lastIndexOf("\n")-1); }
													String sub[] = sKolom7.split("\n");
													for (int y=0; y<sub.length; y++) if (sub[y].startsWith("I")) str7=sub[y]; 
													
													iin = new TableItem(Lists.table, SWT.NONE);
													iin.setText(0, str0);
													iin.setText(1, str1);
													iin.setText(2, str2);
													iin.setText(3, str3);
													iin.setText(4, str4);
													iin.setText(5, str5);
													iin.setText(6, str6);	
													iin.setText(7, str7);	
													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7));														
												}

												//------------------------------ Reject ------------------------------
												if (sID.compareTo(Titles.stReject)==0) {
													str6 = rs[i].getString("type_message"); if (str6==null) str6="";
													str7 = rs[i].getString("free_text_error_message"); if (str7==null) str7="";
													String msg = rs[i].getString("origin_message"); if (msg==null) msg="";
													
													if (msg.contains("\n")) {
														String subs[] = msg.split("\n");
														for (int isub=0; isub<subs.length; isub++) { str8=subs[0]; }
													} else str8=msg;
													
													item.setText(6, str6);	
													item.setText(7, str7);	
													item.setText(8, str8);	
													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8)); }	
												
												//------------------------------ ATS MESSAGES ------------------------------
												else if (sID.compareTo(Titles.stFREETEXT)==0 || 
														sID.compareTo(Titles.stVOL)==0 || sID.compareTo(Titles.stRQM)==0) {
												
													if (sID.compareTo(Titles.stRQM)==0) { str6 = rs[i].getString("request_message"); }
													else if (sID.compareTo(Titles.stVOL)==0) { str6 = rs[i].getString("text"); }
													else { str6 = rs[i].getString("manual_ats"); }
													
													if (str6==null) str6="";
													if (str6.contains("`")) str6 = str6.replace("`", "'");
													if (str6.contains("\n")) str6=str6.substring(0, str6.indexOf("\n"));
													str6 = str6.replaceAll("\\r\\n|\\r|\\n", " "); //remove CRLF
													if (str6.length()>=50) str6=str6.substring(0,50).concat(" ...");
													
													
													item.setText(6, str6);	
													players.add(new Player(str0,str1,str2,str3,str4,str5,str6)); }
												
												else if (sID.compareTo(Titles.stLAM)==0) {
													str6 = rs[i].getString("type3b"); if (str6==null) str6="";
													str7 = rs[i].getString("type3c"); if (str7==null) str7="";
													
													item.setText(6, str6);	
													item.setText(7, str7);	
													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7)); }
												
												else if (sID.compareTo(Titles.stRCF)==0) {
													str6 = rs[i].getString("type7a"); if (str6==null) str6="";
													str7 = rs[i].getString("type7c"); if (str7==null) str7="";
													str8 = rs[i].getString("type21"); if (str8==null) str8="";

													item.setText(6, str6);	
													item.setText(7, str7);	
													item.setText(8, str8);	
													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8)); }
												
												else if (sID.compareTo(Titles.stACP)==0 || sID.compareTo(Titles.stSPL)==0) {
													str6 = rs[i].getString("type7a"); if (str6==null) str6="";
													str7 = rs[i].getString("type13a"); if (str7==null) str7="";
													str8 = rs[i].getString("type13b"); if (str8==null) str8="";
													str9 = rs[i].getString("type16a"); if (str9==null) str9="";
													
													item.setText(6, str6);	
													item.setText(7, str7);	
													item.setText(8, str8);	
													item.setText(9, str9);	
													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9)); }
												
												else if (sID.compareTo(Titles.stDLA)==0 || sID.compareTo(Titles.stCHG)==0 ||
														sID.compareTo(Titles.stCNL)==0 || sID.compareTo(Titles.stDEP)==0 ||
														sID.compareTo(Titles.stRQP)==0 || sID.compareTo(Titles.stRQS)==0) {
														
														str6 = rs[i].getString("type7a"); if (str6==null) str6="";
														str7 = rs[i].getString("type13a"); if (str7==null) str7="";
														str8 = rs[i].getString("type13b"); if (str8==null) str8="";
														str9 = rs[i].getString("type16a"); if (str9==null) str9="";
														str10 = rs[i].getString("type18"); if (str10==null) str10="";
														
														item.setText(6, str6);	
														item.setText(7, str7);	
														item.setText(8, str8);	
														item.setText(9, str9);	
														item.setText(10, str10);	
														players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10)); }
													
												else if (sID.compareTo(Titles.stCDN)==0) {
													str6 = rs[i].getString("type7a"); if (str6==null) str6="";
													str7 = rs[i].getString("type13a"); if (str7==null) str7="";
													str8 = rs[i].getString("type13b"); if (str8==null) str8="";
													str9 = rs[i].getString("type16a"); if (str9==null) str9="";
													str10 = rs[i].getString("type22"); if (str10==null) str10="";
													
													item.setText(6, str6);	
													item.setText(7, str7);	
													item.setText(8, str8);	
													item.setText(9, str9);	
													item.setText(10, str10);	
													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10)); }
												
												else if (sID.compareTo(Titles.stARR)==0) {
													str6 = rs[i].getString("type7a"); if (str6==null) str6="";
													str7 = rs[i].getString("type13a"); if (str7==null) str7="";
													str8 = rs[i].getString("type13b"); if (str8==null) str8="";
													str9 = rs[i].getString("type17a"); if (str9==null) str9="";
													str10 = rs[i].getString("type17b"); if (str10==null) str10="";
													
													item.setText(6, str6);	
													item.setText(7, str7);	
													item.setText(8, str8);	
													item.setText(9, str9);	
													item.setText(10, str10);	
													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10)); }
													
												else if (sID.compareTo(Titles.stEST)==0) {
													str6 = rs[i].getString("type7a"); if (str6==null) str6="";
													str7 = rs[i].getString("type13a"); if (str7==null) str7="";
													str8 = rs[i].getString("type13b"); if (str8==null) str8="";
													str9 = rs[i].getString("type14a"); if (str9==null) str9="";
													str10 = rs[i].getString("type14b"); if (str10==null) str10="";
													str11 = rs[i].getString("type16a"); if (str11==null) str11="";
													
													item.setText(6, str6);	
													item.setText(7, str7);	
													item.setText(8, str8);	
													item.setText(9, str9);	
													item.setText(10, str10);	
													item.setText(11, str11);	
													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11)); }
												
												else if (sID.compareTo(Titles.stALR)==0 || sID.compareTo(Titles.stCPL)==0) {
													str6 = rs[i].getString("type7a"); if (str6==null) str6="";
													str7 = rs[i].getString("type9b"); if (str7==null) str7="";
													str8 = rs[i].getString("type13a"); if (str8==null) str8="";
													str9 = rs[i].getString("type13b"); if (str9==null) str9="";
													str10 = rs[i].getString("type15b"); if (str10==null) str10="";
													str11 = rs[i].getString("type15c"); if (str11==null) str11="";
													str12 = rs[i].getString("type16a"); if (str12==null) str12="";
													
													item.setText(6, str6);	
													item.setText(7, str7);	
													item.setText(8, str8);	
													item.setText(9, str9);	
													item.setText(10, str10);	
													item.setText(11, str11);	
													item.setText(12, str12);	
													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12)); }
												
												else if (sID.compareTo(Titles.stFPL)==0) {
													
													String tbl_name = rs[i].getString("tbl_name"); if (tbl_name==null) tbl_name="";
													String tipe18 = rs[i].getString("type18"); if (tipe18==null) tipe18="";
													str17="";
													ViewATS.sReg="";
													ViewATS.sRmk="";
													ViewATS.form18(tipe18);
													
													str6 = rs[i].getString("type7a"); if (str6==null) str6="";
													str7 = ViewATS.sReg;
													str8 = rs[i].getString("filled_by"); if (str8==null) str8="";
													str9 = rs[i].getString("type9b"); if (str9==null) str9="";
													str10 = rs[i].getString("type13a"); if (str10==null) str10="";
													str11 = rs[i].getString("type13b"); if (str11==null) str11="";
													str12 = rs[i].getString("type15a"); if (str12==null) str12="";
													str13 = rs[i].getString("type15b"); if (str13==null) str13="";
													str14 = rs[i].getString("type15c"); if (str14==null) str14="";
													str15 = rs[i].getString("type16a"); if (str15==null) str15="";
													str16 = rs[i].getString("type16b"); if (str16==null) str16=""; 
													str18 = ViewATS.sRmk;
													String tgl = rs[i].getString("tgl"); if (tgl==null) tgl="";
													Jdbc.select("SELECT type13b from "+tbl_name+" WHERE type3a='DEP' AND type7a='"+str6+"' AND type13a='"+str10+"' " +
															"AND type16a='"+str15+"' AND filled_by='"+str8+"'", "fpldla");
													str17 = Jdbc.dbatd;
													
													if (!str7.isEmpty() && str7.contains("REG/")) str7 = str7.replace("REG/", "");
													if (!str18.isEmpty() && str18.contains("RMK/")) str18 = str18.replace("RMK/", "");
													
													item.setText(6, str6);	
													item.setText(7, str7);	
													item.setText(8, str8);	
													item.setText(9, str9);	
													item.setText(10, str10);	
													item.setText(11, str11);	
													item.setText(12, str12);	
													item.setText(13, str13);
													item.setText(14, str14);
													item.setText(15, str15);
													item.setText(16, str16);
													item.setText(17, str17);
													item.setText(18, str18);
													
													
													
//													String tbl_name = rs[i].getString("tbl_name"); if (tbl_name==null) tbl_name="";
//													String tipe18 = rs[i].getString("type18"); if (tipe18==null) tipe18="";
////													vATS.form18("", "", tipe18);
//													
//													str6 = rs[i].getString("type7a"); if (str6==null) str6="";
//													str7 = vATS.sReg;
//													str8 = rs[i].getString("filled_by"); if (str8==null) str8="";
//													str9 = rs[i].getString("type9b"); if (str9==null) str9="";
//													str10 = rs[i].getString("type13a"); if (str10==null) str10="";
//													str11 = rs[i].getString("type13b"); if (str11==null) str11="";
//													str12 = rs[i].getString("type15a"); if (str12==null) str12="";
//													str13 = rs[i].getString("type15b"); if (str13==null) str13="";
//													str14 = rs[i].getString("type15c"); if (str14==null) str14="";
//													str15 = rs[i].getString("type16a"); if (str15==null) str15="";
//													str16 = rs[i].getString("type16b"); if (str16==null) str16=""; 
//													str18 = vATS.sRmk;
//													String tgl = rs[i].getString("tgl"); if (tgl==null) tgl="";
//													Jdbc.select("SELECT type13b from "+tbl_name+" WHERE type3a='DEP' AND type7a='"+str6+"' AND type13a='"+str10+"' " +
//															"AND type16a='"+str15+"' AND filled_by='"+str8+"'", "fpldla");
//													str17 = Jdbc.dbatd;
//													
//													if (!str7.isEmpty() && str7.contains("REG/")) str7 = str7.replace("REG/", "");
//													if (!str18.isEmpty() && str18.contains("RMK/")) str18 = str18.replace("RMK/", "");
//													if (!str7.isEmpty() && str7.startsWith(" ")) str7 = str7.replaceFirst(" ", "");
//											    	if (!str18.isEmpty() && str18.contains(" ")) str18 = str18.replaceFirst(" ", "");
//													
//													item.setText(6, str6);	
//													item.setText(7, str7);	
//													item.setText(8, str8);	
//													item.setText(9, str9);	
//													item.setText(10, str10);	
//													item.setText(11, str11);	
//													item.setText(12, str12);	
//													item.setText(13, str13);
//													item.setText(14, str14);
//													item.setText(15, str15);
//													item.setText(16, str16);
//													item.setText(17, str17);
//													item.setText(18, str18);
													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12,str13,str14,str15,str16,str17,str18)); }
												//------------------------------ NOTAM MESSAGES ------------------------------
												else if (sID.compareTo(Titles.stNOTAM)==0 || sID.compareTo(Titles.stACTNTM)==0 || 
														sID.compareTo(Titles.stEXPNTM)==0 || sID.compareTo(Titles.stCHKNTM)==0 || 
														sID.compareTo(Titles.stRQNTM)==0) {
													
													String serie = rs[i].getString("ntm_id_serie"); if (serie==null) serie="";
													String seq = rs[i].getString("ntm_id_sequence"); if (seq==null) seq="";
													String years = rs[i].getString("ntm_id_years"); if (years==null) years="";
													String ref_serie = rs[i].getString("ref_ntm_id_serie"); if (ref_serie==null) ref_serie="";
													String ref_seq = rs[i].getString("ref_ntm_id_sequence"); if (ref_seq==null) ref_seq="";
													String ref_years = rs[i].getString("ref_ntm_id_years"); if (ref_years==null) ref_years="";
													String code23 = rs[i].getString("code_23"); if (code23==null) code23="";
													String code45 = rs[i].getString("code_45"); if (code45==null) code45="";
													
													str6 = rs[i].getString("originator"); if (str6==null) str6="";
													str7 = serie+seq+"/"+years;
													if (str7.compareTo("/")==0) str7="";
													str8 = rs[i].getString("identifiers"); if (str8==null) str8="";
													str9 = ref_serie+ref_seq+"/"+ref_years;
													if (str9.compareTo("/")==0) str9="";
													str10 = code23+code45;
													str11 = rs[i].getString("purpose"); if (str11==null) str11="";
													str12 = rs[i].getString("scope"); if (str12==null) str12="";
													str13 = rs[i].getString("A"); if (str13==null) str13="";
													
													item.setText(6, str6);	
													item.setText(7, str7);
													item.setText(8, str8);
													item.setText(9, str9);
													
//													if (sID.compareTo(Titles.stCHKNTM)==0 || sID.compareTo(Titles.stACTNTM)==0 ||
//															sID.compareTo(Titles.stEXPNTM)==0) {
//														item.setText(10, str10);
//														item.setText(11, str11);
//														item.setText(12, str12);
//														item.setText(13, str13);
//														players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12,str13)); }
													
													if (sID.compareTo(Titles.stCHKNTM)==0 || sID.compareTo(Titles.stACTNTM)==0) {
														item.setText(10, str10);
														item.setText(11, str11);
														item.setText(12, str12);
														item.setText(13, str13);
														players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12,str13)); }
													
													
													if (sID.compareTo(Titles.stNOTAM)==0 || sID.compareTo(Titles.stEXPNTM)==0) {
														str14 = rs[i].getString("note"); if (str14==null) str14="";
														
														item.setText(10, str10);
														item.setText(11, str11);
														item.setText(12, str12);
														item.setText(13, str13);
														item.setText(14, str14);
														players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12,str13,str14)); }
													
													if (sID.compareTo(Titles.stRQNTM)==0) {
														str10 = str13;
														str11 = rs[i].getString("n_rqntm"); if (str11==null) str11="";
														item.setText(10, str10);
														item.setText(11, str11);
														players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11)); } }
												else if (sID.compareTo(Titles.stSNOWTAM)==0) {
													str6 = rs[i].getString("state"); if (str6==null) str6="";
													str7 = rs[i].getString("sn_serial_nr"); if (str7==null) str7="";
													str8 = rs[i].getString("location_indicator"); if (str8==null) str8="";
													str9 = rs[i].getString("oservation"); if (str9==null) str9="";
													str10 = rs[i].getString("opt_group"); if (str10==null) str10="";
													str11 = rs[i].getString("sn_serial_nr"); if (str11==null) str11="";
													
													item.setText(6, str6);	
													item.setText(7, str7);	
													item.setText(8, str8);	
													item.setText(9, str9);	
													item.setText(10, str10);	
													item.setText(11, str11);	
													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11)); }
												else if (sID.compareTo(Titles.stASHTAM)==0) {
													str6 = rs[i].getString("state"); if (str6==null) str6="";
													str7 = rs[i].getString("sn_serial_nr"); if (str7==null) str7="";
													str8 = rs[i].getString("location"); if (str8==null) str8="";
													str9 = rs[i].getString("issued"); if (str9==null) str9="";
													str10 = rs[i].getString("opt_group"); if (str10==null) str10="";
													str11 = rs[i].getString("sn_serial_nr"); if (str11==null) str11="";
													
													item.setText(6, str6);	
													item.setText(7, str7);	
													item.setText(8, str8);	
													item.setText(9, str9);	
													item.setText(10, str10);	
													item.setText(11, str11);	
													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11)); }
												else if (sID.compareTo(Titles.stBIRDTAM)==0) {
													str6 = rs[i].getString("birdtam_nr"); if (str6==null) str6="";
													str7 = rs[i].getString("effective_time"); if (str7==null) str7="";
													str8 = rs[i].getString("expiration_time"); if (str8==null) str8="";
													str9 = rs[i].getString("intensity_level"); if (str9==null) str9="";
													
													item.setText(6, str6);	
													item.setText(7, str7);	
													item.setText(8, str8);	
													item.setText(9, str9);	
													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9)); }
												else if (sID.compareTo(Titles.stRQN)==0 || sID.compareTo(Titles.stRQL)==0) {
													str6 = rs[i].getString("request_id"); if (str6==null) str6="";
													str7 = rs[i].getString("nof_or_type"); if (str7==null) str7="";
													str8 = rs[i].getString("request_message"); if (str8==null) str8="";
													
													item.setText(6, str6);	
													item.setText(7, str7);	
													item.setText(8, str8);	
													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8)); }	
												//------------------------------ METEO MESSAGES ------------------------------
												else if (sID.compareTo(Titles.stMETAR)==0 || sID.compareTo(Titles.stSPECI)==0 ||
														sID.compareTo(Titles.stSIGMET)==0 || sID.compareTo(Titles.stAIRMET)==0 ||
														sID.compareTo(Titles.stAIREP)==0 || sID.compareTo(Titles.stTAFOR)==0 ||
														sID.compareTo(Titles.stSYNOP)==0 || sID.compareTo(Titles.stARFOR)==0 ||
														sID.compareTo(Titles.stROFOR)==0 || sID.compareTo(Titles.stWINSWAR)==0 ||
														sID.compareTo(Titles.stWINTEM)==0 || sID.compareTo(Titles.stADV)==0 ||
														sID.compareTo(Titles.stWARSYN)==0) {
													
													str6 = rs[i].getString("originator"); if (str6==null) str6="";
													String msg1 = rs[i].getString("message_id_12"); if (msg1==null) msg1="";
													String msg2 = rs[i].getString("message_id_34"); if (msg2==null) msg2="";
													String msg3 = rs[i].getString("message_id_56"); if (msg3==null) msg3="";
													str7 = msg1+msg2+msg3;
													str10 = rs[i].getString("issued_message"); if (str10==null) str10="";
													str11 = rs[i].getString("correction_meteo"); if (str11==null) str11="";
													
													item.setText(6, str6);	
													item.setText(7, str7);
													
													if (sID.compareTo(Titles.stMETAR)==0 || sID.compareTo(Titles.stSPECI)==0) { 
														str8 = rs[i].getString("Location"); if (str8==null) str8="";	
														str9 = rs[i].getString("time_observation"); if (str9==null) str9="";
														str12 = rs[i].getString("text"); if (str12==null) str12="";	
														
														if (str12.contains("\n")) {
															str12=str12.substring(0, str12.indexOf("\n"));
															if (str12.length()>=50) str12=str12.substring(0,50).concat(" ...");
														} else {
															if (str12.length()>=50) str12=str12.substring(0,50).concat(" ...");
														}
														
														item.setText(8, str8);	
														item.setText(9, str9);
														item.setText(10, str10);
														item.setText(11, str11);
														item.setText(12, str12);
														players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12)); }

													else if (sID.compareTo(Titles.stSIGMET)==0 || sID.compareTo(Titles.stAIRMET)==0 ||
															sID.compareTo(Titles.stWINSWAR)==0) { 
														
														if (sID.compareTo(Titles.stWINSWAR)==0) { 
															str8 = rs[i].getString("location"); if (str8==null) str8=""; }
														else { str8 = rs[i].getString("Location_ats"); if (str8==null) str8=""; }
																
														str9=str10; str10=str11;
														str11 = rs[i].getString("text"); if (str11==null) str11="";	
														if (str11.contains("\n")) {
															str11=str11.substring(0, str11.indexOf("\n"));
															if (str11.length()>=50) str11=str11.substring(0,50).concat(" ...");
														} else {
															if (str11.length()>=50) str11=str11.substring(0,50).concat(" ...");
														}
														
														item.setText(8, str8);	
														item.setText(9, str9);
														item.setText(10, str10);
														item.setText(11, str11);
														players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11)); }
													
													else if (sID.compareTo(Titles.stAIREP)==0 || sID.compareTo(Titles.stTAFOR)==0 ||
															sID.compareTo(Titles.stARFOR)==0 || sID.compareTo(Titles.stROFOR)==0 ||
															sID.compareTo(Titles.stWINTEM)==0 || sID.compareTo(Titles.stADV)==0 ||
															sID.compareTo(Titles.stSYNOP)==0 || sID.compareTo(Titles.stWARSYN)==0) { 
														str8=str10; str9=str11;
														if (sID.compareTo(Titles.stWARSYN)==0) { 
															str10 = rs[i].getString("warning_synop"); if (str10==null) str10="";	
														} else { str10 = rs[i].getString("text"); if (str10==null) str10=""; }

														if (str10.contains("\n")) {
															str10=str10.substring(0, str10.indexOf("\n"));
															if (str10.length()>=50) str10=str10.substring(0,50).concat(" ...");
														} else {
															if (str10.length()>=50) str10=str10.substring(0,50).concat(" ...");
														}
														
														item.setText(8, str8);	
														item.setText(9, str9);
														item.setText(10, str10);
														players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10)); }
												}
												
												
												
												
												
												
//												if (sID.compareTo(Titles.stOutgoing)!=0 && sID.compareTo(Titles.stIncoming)!=0 ) 
//												{ item = new TableItem(Lists.table, SWT.NONE); }
//												//---------------------------------------------------------------------------------------
//												
//												if (sID.compareTo(Titles.stIncoming)!=0 && sID.compareTo(Titles.stOutgoing)!=0) { 
//													str0=rs[i].getString("tbl_name"); if (str0==null) str0="";//kol0: tblnm	
//													str1=rs[i].getString(sIdName); if (str1==null) str1=""; //kol1: idtbl
//													str2=""; //kol2: cid
//													str3=""; //kol3: csn
//													str4=""; //kol4: tms
//													str5=rs[i].getString("tgl").substring(0, 19); if (str5==null) str5=""; //kol5: tgl
//												
//													item.setText(0, str0);
//													item.setText(1, str1);
//													item.setText(2, str2);
//													item.setText(3, str3);
//													item.setText(4, str4);
//													item.setText(5, str5);
//													
//												} else if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
//													str0="";//kol0: tblnm	
//													String tblnm="",yymmdd="",strTgl="";
//													strTgl=rs[i].getString("tgl").substring(0, 19); if (strTgl==null) strTgl=""; 
//													if (!strTgl.isEmpty()&&strTgl.length()==19) { yymmdd=strTgl.substring(0,4)+"_"+strTgl.substring(5,7)+"_"+strTgl.substring(8,10); }
////													tblnm="aftnrdI";
//													if (sID.compareTo(Titles.stIncoming)==0) tblnm="aftnrdI";
//													else if (sID.compareTo(Titles.stOutgoing)==0) tblnm="aftnrdO";
//													str0 = tblnm+yymmdd;
//													str1 = rs[i].getString(sIdName); if (str1==null) str1=""; //kol1: idtbl
//													str2 = rs[i].getString("cid"); if (str2==null) str2=""; //kol2: cid
//													str3 = rs[i].getString("seq"); if (str3==null) str3=""; //kol3: csn
//													str4=""; //kol4: tms
//													if (sID.compareTo(Titles.stIncoming)==0) {
//														String sKolom4 = rs[i].getString("data"); if (sKolom4==null) sKolom4="";
//														if (!sKolom4.isEmpty() && sKolom4.contains("\n")) {
//															sKolom4 = sKolom4.substring(0, sKolom4.indexOf("\n"));
//															if (sKolom4.contains(" ") && sKolom4.length()>=14) {
//																str4 = sKolom4.substring(sKolom4.indexOf(" ")+1, sKolom4.indexOf(" ")+7);	
//															} else str4="";
//														}
//													} else if (sID.compareTo(Titles.stOutgoing)==0) {
//														str4 = rs[i].getString("firstdt"); if (str4==null) str4=""; //kol4: tms
//														//fix awal firstdt=RCB0001081542xxxxx
//														String strOutt = str4;
//														if (strOutt.length()>=13) {
//															str4 = strOutt.substring(7, 13);
//														} else str4="";
//														if (str4.compareTo("      ")==0) str4="";
//													}
//														
//													str5=rs[i].getString("tgl").substring(0, 19); if (str5==null) str5=""; //kol5: tgl
//													
//													str6=""; //kol6: Data
//													String sKolom6 = rs[i].getString("data"); if (sKolom6==null) sKolom6="";
//													int index=0,jmldata=0,isub=0;
//													String subs[]=null;
//													if (sKolom6.contains("\n")) {
//														subs = sKolom6.split("\n");
//														for (; isub<subs.length; isub++) {
//															if (subs[isub].startsWith("0") || subs[isub].startsWith("1") ||
//																subs[isub].startsWith("2") || subs[isub].startsWith("3") ||
//																subs[isub].startsWith("4") || subs[isub].startsWith("5") ||
//																subs[isub].startsWith("6") || subs[isub].startsWith("7") ||
//																subs[isub].startsWith("8") || subs[isub].startsWith("9")) {
//																
//																if (subs[isub].contains(" ")) {
//																	int indSpace = subs[isub].indexOf(" ");
//																	String tada = subs[isub].substring(0, indSpace);
//																	
//																	if (tada.length()<6) { /*System.out.println(">=6");*/ } 
//																	else if (tada.length()==6) { index=isub; }
//																}
//															}
//															jmldata++;
//														}
//													} else { str6=sKolom6; }
//													if (jmldata>(index+1)) { str6=subs[index+1]; } 
//													else if (jmldata==index) { str6=sKolom6; }
//													str6 = str6.replaceAll("\\r\\n|\\r|\\n", " "); //remove CRLF
//													
//													str7=""; //kol7: Trace
//													String sKolom7 = rs[i].getString("trace"); if (sKolom7==null) sKolom7="";
//													if (sKolom7.endsWith("\n")) { sKolom7=sKolom7.substring(0, sKolom7.lastIndexOf("\n")-1); }
//													String sub[] = sKolom7.split("\n");
//													for (int y=0; y<sub.length; y++) if (sub[y].startsWith("I")) str7=sub[y]; 
//													
//													iin = new TableItem(Lists.table, SWT.NONE);
//													iin.setText(0, str0);
//													iin.setText(1, str1);
//													iin.setText(2, str2);
//													iin.setText(3, str3);
//													iin.setText(4, str4);
//													iin.setText(5, str5);
//													iin.setText(6, str6);	
//													iin.setText(7, str7);	
//													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7));														
//												}
//												
//												//------------------------------ Reject ------------------------------
//												if (sID.compareTo(Titles.stReject)==0) {
//													str6 = rs[i].getString("type_message"); if (str6==null) str6="";
//													str7 = rs[i].getString("free_text_error_message"); if (str7==null) str7="";
//													String msg = rs[i].getString("origin_message"); if (msg==null) msg="";
//													
//													if (msg.contains("\n")) {
//														String subs[] = msg.split("\n");
//														for (int isub=0; isub<subs.length; isub++) { str8=subs[0]; }
//													} else str8=msg;
//													
//													item.setText(6, str6);	
//													item.setText(7, str7);	
//													item.setText(8, str8);	
//													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8)); }	
//												
//												//------------------------------ ATS MESSAGES ------------------------------
//												else if (sID.compareTo(Titles.stFREETEXT)==0 || 
//														sID.compareTo(Titles.stVOL)==0 || sID.compareTo(Titles.stRQM)==0) {
//												
//													if (sID.compareTo(Titles.stRQM)==0) {
//														str6 = rs[i].getString("request_message"); if (str6==null) str6=""; }
//													else if (sID.compareTo(Titles.stVOL)==0) {
//														str6 = rs[i].getString("text"); if (str6==null) str6=""; }
//													else { str6 = rs[i].getString("manual_ats"); if (str6==null) str6=""; }
//													
////													if (str6.contains("`")) str6 = str6.replace("`", "'");
////													if (str6.contains("\n")) {
////														str6=str6.substring(0, str6.indexOf("\n"));
////														if (str6.length()>=50) str6=str6.substring(0,50).concat(" ...");
////													} else {
////														if (str6.length()>=50) str6=str6.substring(0,50).concat(" ...");
////													}
//													
//													if (str6.contains("`")) str6 = str6.replace("`", "'");
//													if (str6.contains("\n")) str6=str6.substring(0, str6.indexOf("\n"));
//													str6 = str6.replaceAll("\\r\\n|\\r|\\n", " "); //remove CRLF
//													if (str6.length()>=50) str6=str6.substring(0,50).concat(" ...");
//													
//													
//													item.setText(6, str6);	
//													players.add(new Player(str0,str1,str2,str3,str4,str5,str6)); }
//												
//												else if (sID.compareTo(Titles.stLAM)==0) {
//													str6 = rs[i].getString("type3b"); if (str6==null) str6="";
//													str7 = rs[i].getString("type3c"); if (str7==null) str7="";
//													
//													item.setText(6, str6);	
//													item.setText(7, str7);	
//													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7)); }
//												
//												else if (sID.compareTo(Titles.stRCF)==0) {
//													str6 = rs[i].getString("type7a"); if (str6==null) str6="";
//													str7 = rs[i].getString("type7c"); if (str7==null) str7="";
//													str8 = rs[i].getString("type21"); if (str8==null) str8="";
//
//													item.setText(6, str6);	
//													item.setText(7, str7);	
//													item.setText(8, str8);	
//													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8)); }
//												
//												else if (sID.compareTo(Titles.stACP)==0 || sID.compareTo(Titles.stSPL)==0) {
//													str6 = rs[i].getString("type7a"); if (str6==null) str6="";
//													str7 = rs[i].getString("type13a"); if (str7==null) str7="";
//													str8 = rs[i].getString("type13b"); if (str8==null) str8="";
//													str9 = rs[i].getString("type16a"); if (str9==null) str9="";
//													
//													item.setText(6, str6);	
//													item.setText(7, str7);	
//													item.setText(8, str8);	
//													item.setText(9, str9);	
//													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9)); }
//												
//												else if (sID.compareTo(Titles.stDLA)==0 || sID.compareTo(Titles.stCHG)==0 ||
//														sID.compareTo(Titles.stCNL)==0 || sID.compareTo(Titles.stDEP)==0 ||
//														sID.compareTo(Titles.stRQP)==0 || sID.compareTo(Titles.stRQS)==0) {
//														
//														str6 = rs[i].getString("type7a"); if (str6==null) str6="";
//														str7 = rs[i].getString("type13a"); if (str7==null) str7="";
//														str8 = rs[i].getString("type13b"); if (str8==null) str8="";
//														str9 = rs[i].getString("type16a"); if (str9==null) str9="";
//														str10 = rs[i].getString("type18"); if (str10==null) str10="";
//														
//														item.setText(6, str6);	
//														item.setText(7, str7);	
//														item.setText(8, str8);	
//														item.setText(9, str9);	
//														item.setText(10, str10);	
//														players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10)); }
//													
//												else if (sID.compareTo(Titles.stCDN)==0) {
//													str6 = rs[i].getString("type7a"); if (str6==null) str6="";
//													str7 = rs[i].getString("type13a"); if (str7==null) str7="";
//													str8 = rs[i].getString("type13b"); if (str8==null) str8="";
//													str9 = rs[i].getString("type16a"); if (str9==null) str9="";
//													str10 = rs[i].getString("type22"); if (str10==null) str10="";
//													
//													item.setText(6, str6);	
//													item.setText(7, str7);	
//													item.setText(8, str8);	
//													item.setText(9, str9);	
//													item.setText(10, str10);	
//													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10)); }
//												
//												else if (sID.compareTo(Titles.stARR)==0) {
//													str6 = rs[i].getString("type7a"); if (str6==null) str6="";
//													str7 = rs[i].getString("type13a"); if (str7==null) str7="";
//													str8 = rs[i].getString("type13b"); if (str8==null) str8="";
//													str9 = rs[i].getString("type17a"); if (str9==null) str9="";
//													str10 = rs[i].getString("type17b"); if (str10==null) str10="";
//													
//													item.setText(6, str6);	
//													item.setText(7, str7);	
//													item.setText(8, str8);	
//													item.setText(9, str9);	
//													item.setText(10, str10);	
//													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10)); }
//													
//												else if (sID.compareTo(Titles.stEST)==0) {
//													str6 = rs[i].getString("type7a"); if (str6==null) str6="";
//													str7 = rs[i].getString("type13a"); if (str7==null) str7="";
//													str8 = rs[i].getString("type13b"); if (str8==null) str8="";
//													str9 = rs[i].getString("type14a"); if (str9==null) str9="";
//													str10 = rs[i].getString("type14b"); if (str10==null) str10="";
//													str11 = rs[i].getString("type16a"); if (str11==null) str11="";
//													
//													item.setText(6, str6);	
//													item.setText(7, str7);	
//													item.setText(8, str8);	
//													item.setText(9, str9);	
//													item.setText(10, str10);	
//													item.setText(11, str11);	
//													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11)); }
//												
//												else if (sID.compareTo(Titles.stALR)==0 || sID.compareTo(Titles.stCPL)==0) {
//													str6 = rs[i].getString("type7a"); if (str6==null) str6="";
//													str7 = rs[i].getString("type9b"); if (str7==null) str7="";
//													str8 = rs[i].getString("type13a"); if (str8==null) str8="";
//													str9 = rs[i].getString("type13b"); if (str9==null) str9="";
//													str10 = rs[i].getString("type15b"); if (str10==null) str10="";
//													str11 = rs[i].getString("type15c"); if (str11==null) str11="";
//													str12 = rs[i].getString("type16a"); if (str12==null) str12="";
//													
//													item.setText(6, str6);	
//													item.setText(7, str7);	
//													item.setText(8, str8);	
//													item.setText(9, str9);	
//													item.setText(10, str10);	
//													item.setText(11, str11);	
//													item.setText(12, str12);	
//													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12)); }
//												
//												else if (sID.compareTo(Titles.stFPL)==0) {
//													str6 = rs[i].getString("type7a"); if (str6==null) str6="";
//													str7 = rs[i].getString("type9b"); if (str7==null) str7="";
//													str8 = rs[i].getString("type13a"); if (str8==null) str8="";
//													str9 = rs[i].getString("type13b"); if (str9==null) str9="";
//													str10 = rs[i].getString("type15b"); if (str10==null) str10="";
//													str11 = rs[i].getString("type15c"); if (str11==null) str11="";
//													str12 = rs[i].getString("type16a"); if (str12==null) str12="";
//													str13 = rs[i].getString("type18"); if (str13==null) str13="";
//													
//													item.setText(6, str6);	
//													item.setText(7, str7);	
//													item.setText(8, str8);	
//													item.setText(9, str9);	
//													item.setText(10, str10);	
//													item.setText(11, str11);	
//													item.setText(12, str12);	
//													item.setText(13, str13);	
//													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12,str13)); }
//												
//												//------------------------------ NOTAM MESSAGES ------------------------------
//												else if (sID.compareTo(Titles.stNOTAM)==0 || sID.compareTo(Titles.stACTNTM)==0 || 
//														sID.compareTo(Titles.stEXPNTM)==0 || sID.compareTo(Titles.stCHKNTM)==0 || 
//														sID.compareTo(Titles.stRQNTM)==0) {
//													
//													String serie = rs[i].getString("ntm_id_serie"); if (serie==null) serie="";
//													String seq = rs[i].getString("ntm_id_sequence"); if (seq==null) seq="";
//													String years = rs[i].getString("ntm_id_years"); if (years==null) years="";
//													String ref_serie = rs[i].getString("ref_ntm_id_serie"); if (ref_serie==null) ref_serie="";
//													String ref_seq = rs[i].getString("ref_ntm_id_sequence"); if (ref_seq==null) ref_seq="";
//													String ref_years = rs[i].getString("ref_ntm_id_years"); if (ref_years==null) ref_years="";
//													String code23 = rs[i].getString("code_23"); if (code23==null) code23="";
//													String code45 = rs[i].getString("code_45"); if (code45==null) code45="";
//													
//													str6 = rs[i].getString("originator"); if (str6==null) str6="";
//													str7 = serie+seq+"/"+years;
//													if (str7.compareTo("/")==0) str7="";
//													str8 = rs[i].getString("identifiers"); if (str8==null) str8="";
//													str9 = ref_serie+ref_seq+"/"+ref_years;
//													if (str9.compareTo("/")==0) str9="";
//													str10 = code23+code45;
//													str11 = rs[i].getString("purpose"); if (str11==null) str11="";
//													str12 = rs[i].getString("scope"); if (str12==null) str12="";
//													str13 = rs[i].getString("A"); if (str13==null) str13="";
//													
//													item.setText(6, str6);	
//													item.setText(7, str7);
//													item.setText(8, str8);
//													item.setText(9, str9);
//													
//													if (sID.compareTo(Titles.stCHKNTM)==0 || sID.compareTo(Titles.stACTNTM)==0 ||
//															sID.compareTo(Titles.stEXPNTM)==0) {
//														item.setText(10, str10);
//														item.setText(11, str11);
//														item.setText(12, str12);
//														item.setText(13, str13);
//														players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12,str13)); }
//													
//													if (sID.compareTo(Titles.stNOTAM)==0) {
//														str14 = rs[i].getString("note"); if (str14==null) str14="";
//														
//														item.setText(10, str10);
//														item.setText(11, str11);
//														item.setText(12, str12);
//														item.setText(13, str13);
//														item.setText(14, str14);
//														players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12,str13,str14)); }
//													
//													if (sID.compareTo(Titles.stRQNTM)==0) {
//														str10 = str13;
//														str11 = rs[i].getString("n_rqntm"); if (str11==null) str11="";
//														item.setText(10, str10);
//														item.setText(11, str11);
//														players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11)); } }
//												else if (sID.compareTo(Titles.stSNOWTAM)==0) {
//													str6 = rs[i].getString("state"); if (str6==null) str6="";
//													str7 = rs[i].getString("sn_serial_nr"); if (str7==null) str7="";
//													str8 = rs[i].getString("location_indicator"); if (str8==null) str8="";
//													str9 = rs[i].getString("oservation"); if (str9==null) str9="";
//													str10 = rs[i].getString("opt_group"); if (str10==null) str10="";
//													str11 = rs[i].getString("sn_serial_nr"); if (str11==null) str11="";
//													
//													item.setText(6, str6);	
//													item.setText(7, str7);	
//													item.setText(8, str8);	
//													item.setText(9, str9);	
//													item.setText(10, str10);	
//													item.setText(11, str11);	
//													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11)); }
//												else if (sID.compareTo(Titles.stASHTAM)==0) {
//													str6 = rs[i].getString("state"); if (str6==null) str6="";
//													str7 = rs[i].getString("sn_serial_nr"); if (str7==null) str7="";
//													str8 = rs[i].getString("location"); if (str8==null) str8="";
//													str9 = rs[i].getString("issued"); if (str9==null) str9="";
//													str10 = rs[i].getString("opt_group"); if (str10==null) str10="";
//													str11 = rs[i].getString("sn_serial_nr"); if (str11==null) str11="";
//													
//													item.setText(6, str6);	
//													item.setText(7, str7);	
//													item.setText(8, str8);	
//													item.setText(9, str9);	
//													item.setText(10, str10);	
//													item.setText(11, str11);	
//													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11)); }
//												else if (sID.compareTo(Titles.stBIRDTAM)==0) {
//													str6 = rs[i].getString("birdtam_nr"); if (str6==null) str6="";
//													str7 = rs[i].getString("effective_time"); if (str7==null) str7="";
//													str8 = rs[i].getString("expiration_time"); if (str8==null) str8="";
//													str9 = rs[i].getString("intensity_level"); if (str9==null) str9="";
//													
//													item.setText(6, str6);	
//													item.setText(7, str7);	
//													item.setText(8, str8);	
//													item.setText(9, str9);	
//													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9)); }
//												else if (sID.compareTo(Titles.stRQN)==0 || sID.compareTo(Titles.stRQL)==0) {
//													str6 = rs[i].getString("request_id"); if (str6==null) str6="";
//													str7 = rs[i].getString("nof_or_type"); if (str7==null) str7="";
//													str8 = rs[i].getString("request_message"); if (str8==null) str8="";
//													
//													item.setText(6, str6);	
//													item.setText(7, str7);	
//													item.setText(8, str8);	
//													players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8)); }	
//												//------------------------------ METEO MESSAGES ------------------------------
//												else if (sID.compareTo(Titles.stMETAR)==0 || sID.compareTo(Titles.stSPECI)==0 ||
//														sID.compareTo(Titles.stSIGMET)==0 || sID.compareTo(Titles.stAIRMET)==0 ||
//														sID.compareTo(Titles.stAIREP)==0 || sID.compareTo(Titles.stTAFOR)==0 ||
//														sID.compareTo(Titles.stSYNOP)==0 || sID.compareTo(Titles.stARFOR)==0 ||
//														sID.compareTo(Titles.stROFOR)==0 || sID.compareTo(Titles.stWINSWAR)==0 ||
//														sID.compareTo(Titles.stWINTEM)==0 || sID.compareTo(Titles.stADV)==0 ||
//														sID.compareTo(Titles.stWARSYN)==0) {
//													
//													str6 = rs[i].getString("originator"); if (str6==null) str6="";
//													String msg1 = rs[i].getString("message_id_12"); if (msg1==null) msg1="";
//													String msg2 = rs[i].getString("message_id_34"); if (msg2==null) msg2="";
//													String msg3 = rs[i].getString("message_id_56"); if (msg3==null) msg3="";
//													str7 = msg1+msg2+msg3;
//													str10 = rs[i].getString("issued_message"); if (str10==null) str10="";
//													str11 = rs[i].getString("correction_meteo"); if (str11==null) str11="";
//													
//													item.setText(6, str6);	
//													item.setText(7, str7);
//													
//													if (sID.compareTo(Titles.stMETAR)==0 || sID.compareTo(Titles.stSPECI)==0) { 
//														str8 = rs[i].getString("Location"); if (str8==null) str8="";	
//														str9 = rs[i].getString("time_observation"); if (str9==null) str9="";
//														str12 = rs[i].getString("text"); if (str12==null) str12="";	
//														
//														if (str12.contains("\n")) {
//															str12=str12.substring(0, str12.indexOf("\n"));
//															if (str12.length()>=50) str12=str12.substring(0,50).concat(" ...");
//														} else {
//															if (str12.length()>=50) str12=str12.substring(0,50).concat(" ...");
//														}
//														
//														item.setText(8, str8);	
//														item.setText(9, str9);
//														item.setText(10, str10);
//														item.setText(11, str11);
//														item.setText(12, str12);
//														players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12)); }
//
//													else if (sID.compareTo(Titles.stSIGMET)==0 || sID.compareTo(Titles.stAIRMET)==0 ||
//															sID.compareTo(Titles.stWINSWAR)==0) { 
//														
//														if (sID.compareTo(Titles.stWINSWAR)==0) { 
//															str8 = rs[i].getString("location"); if (str8==null) str8=""; }
//														else { str8 = rs[i].getString("Location_ats"); if (str8==null) str8=""; }
//																
//														str9=str10; str10=str11;
//														str11 = rs[i].getString("text"); if (str11==null) str11="";	
//														if (str11.contains("\n")) {
//															str11=str11.substring(0, str11.indexOf("\n"));
//															if (str11.length()>=50) str11=str11.substring(0,50).concat(" ...");
//														} else {
//															if (str11.length()>=50) str11=str11.substring(0,50).concat(" ...");
//														}
//														
//														item.setText(8, str8);	
//														item.setText(9, str9);
//														item.setText(10, str10);
//														item.setText(11, str11);
//														players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11)); }
//													
//													else if (sID.compareTo(Titles.stAIREP)==0 || sID.compareTo(Titles.stTAFOR)==0 ||
//															sID.compareTo(Titles.stARFOR)==0 || sID.compareTo(Titles.stROFOR)==0 ||
//															sID.compareTo(Titles.stWINTEM)==0 || sID.compareTo(Titles.stADV)==0 ||
//															sID.compareTo(Titles.stSYNOP)==0 || sID.compareTo(Titles.stWARSYN)==0) { 
//														str8=str10; str9=str11;
//														if (sID.compareTo(Titles.stWARSYN)==0) { 
//															str10 = rs[i].getString("warning_synop"); if (str10==null) str10="";	
//														} else { str10 = rs[i].getString("text"); if (str10==null) str10=""; }
//
//														if (str10.contains("\n")) {
//															str10=str10.substring(0, str10.indexOf("\n"));
//															if (str10.length()>=50) str10=str10.substring(0,50).concat(" ...");
//														} else {
//															if (str10.length()>=50) str10=str10.substring(0,50).concat(" ...");
//														}
//														
//														item.setText(8, str8);	
//														item.setText(9, str9);
//														item.setText(10, str10);
//														players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10)); }
//												}
												
												progbr.bar.setSelection(rowNo);
											}
											if (rowNo >= ((qq*iLimitRow)+iLimitRow)) break;
										} //end of while
										if (rowNo >= ((qq*iLimitRow)+iLimitRow)) break;
									} //end of if
								} catch (SQLException s) {
									DialogFactory.openInfo(s.getMessage());
									s.printStackTrace();
								} //enf of try
							} //end of for
							progbr.finish();
						} catch (java.lang.OutOfMemoryError hs) {
		        			hs.printStackTrace();
		        			DialogFactory.openInfo(DialogFactory.LIST_INFO_OUT_OF_MEMORY);
							progbr.finish();
						} //end of try
					} //end of run
				}); //end of runnable
			}
		}.start();
		progbr.begin();
	}
	
	public static void discard() {
		tTextual.setText("");
		tTrace.setText("");
		tGotoPage.setText("");
		enabledPagination(false);
		setInfoRowZero();
	}
	
}

/**
 * This class represents a player.
 */
class Player {
	private String str0;//tbl_name
	private String str1;//idName
	private String str2;//cid
	private String str3;//csn
	private String str4;//tms
	private String str5;//tgl
	private String str6;
	private String str7;
	private String str8;
	private String str9;
	private String str10;
	private String str11;
	private String str12;
	private String str13;
	private String str14;
	private String str15;
	private String str16;
	private String str17;
	private String str18;


	//Retrieve I/O
	public Player(String str0,String str1,String str2,String str3,String str4,String str5) {
		this.str0=str0;this.str1=str1;this.str2=str2;this.str3=str3;this.str4=str4;this.str5=str5;
	}
	
	//FREEATS
	public Player(String str0,String str1,String str2,String str3,String str4,String str5,String str6) {
		this.str0=str0;this.str1=str1;this.str2=str2;this.str3=str3;this.str4=str4;this.str5=str5;this.str6=str6;
	}
	 
	//ALR,CPL
	public Player(String str0,String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8,String str9,
			String str10,String str11,String str12) {
		this.str0=str0;this.str1=str1;this.str2=str2;this.str3=str3;this.str4=str4;this.str5=str5;this.str6=str6;this.str7=str7;this.str8=str8;
		this.str9=str9;this.str10=str10;this.str11=str11;this.str12=str12;
	}
	 
	//RCF
	public Player(String str0,String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8) {
		this.str0=str0;this.str1=str1;this.str2=str2;this.str3=str3;this.str4=str4;this.str5=str5;this.str6=str6;this.str7=str7;this.str8=str8;
	}
	 
	//FPL
	public Player(String str0,String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8,String str9,
			String str10,String str11,String str12,String str13) {
		this.str0=str0;this.str1=str1;this.str2=str2;this.str3=str3;this.str4=str4;this.str5=str5;this.str6=str6;this.str7=str7;this.str8=str8;
		this.str9=str9;this.str10=str10;this.str11=str11;this.str12=str12;this.str13=str13;
	}
	//FPL PERCOBAAN
	public Player(String str0,String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8,String str9,
			String str10,String str11,String str12,String str13,String str14,String str15,String str16,String str17,String str18) {
		this.str0=str0;this.str1=str1;this.str2=str2;this.str3=str3;this.str4=str4;this.str5=str5;this.str6=str6;this.str7=str7;this.str8=str8;
		this.str9=str9;this.str10=str10;this.str11=str11;this.str12=str12;this.str13=str13;this.str14=str14;this.str15=str15;this.str16=str16;
		this.str17=str17;this.str18=str18;
	}
	 
	//DLA,CHG,CNL,DEP,RQP,RQS
	public Player(String str0,String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8,String str9,
			String str10) {
		this.str0=str0;this.str1=str1;this.str2=str2;this.str3=str3;this.str4=str4;this.str5=str5;this.str6=str6;this.str7=str7;this.str8=str8;
		this.str9=str9;this.str10=str10; 
	}
	 
	//EST
	public Player(String str0,String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8,String str9,
			String str10,String str11) {
		this.str0=str0;this.str1=str1;this.str2=str2;this.str3=str3;this.str4=str4;this.str5=str5;this.str6=str6;this.str7=str7;this.str8=str8;
		this.str9=str9;this.str10=str10;this.str11=str11; 
	}
	 
	//ACP,SPL
	public Player(String str0,String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8,String str9) {
		this.str0=str0;this.str1=str1;this.str2=str2;this.str3=str3;this.str4=str4;this.str5=str5;this.str6=str6;this.str7=str7;this.str8=str8;
		this.str9=str9;
	}
	 
	//LAM
	public Player(String str0,String str1,String str2,String str3,String str4,String str5,String str6,String str7) {
		this.str0=str0;this.str1=str1;this.str2=str2;this.str3=str3;this.str4=str4;this.str5=str5;this.str6=str6;this.str7=str7;
	}
	 
	//NOTAM
	public Player(String str0,String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8,String str9,String str10,
			String str11,String str12,String str13,String str14) {
		this.str0=str0;this.str1=str1;this.str2=str2;this.str3=str3;this.str4=str4;this.str5=str5;this.str6=str6;this.str7=str7;this.str8=str8;
		this.str9=str9;this.str10=str10;this.str11=str11;this.str12=str12;this.str13=str13;this.str14=str14; 
	}

	public String getstr0() { if (str0==null) str0=""; return str0; }
	public String getstr1() { if (str1==null) str1=""; return str1; }
	public String getstr2() { if (str2==null) str2=""; return str2; }
	public String getstr3() { if (str3==null) str3=""; return str3; }
	public String getstr4() { if (str4==null) str4=""; return str4; }
	public String getstr5() { if (str5==null) str5=""; return str5; }
	public String getstr6() { if (str6==null) str6=""; return str6; }
	public String getstr7() { if (str7==null) str7=""; return str7; }
	public String getstr8() { if (str8==null) str8=""; return str8; }
	public String getstr9() { if (str9==null) str9=""; return str9; }
	public String getstr10() { if (str10==null) str10=""; return str10; }
	public String getstr11() { if (str11==null) str11=""; return str11; }
	public String getstr12() { if (str12==null) str12=""; return str12; }
	public String getstr13() { if (str13==null) str13=""; return str13; }
	public String getstr14() { if (str14==null) str14=""; return str14; }
	public String getstr15() { if (str15==null) str15=""; return str15; }
	public String getstr16() { if (str16==null) str16=""; return str16; }
	public String getstr17() { if (str17==null) str17=""; return str17; }
	public String getstr18() { if (str18==null) str18=""; return str18; }
}

class PlayerComparator implements Comparator {
	public static final int str0=0;
	public static final int str1=1;
	public static final int str2=2;
	public static final int str3=3;
	public static final int str4=4;
	public static final int str5=5;
	public static final int str6=6;
	public static final int str7=7;
	public static final int str8=8;
	public static final int str9=9;
	public static final int str10=10;
	public static final int str11=11;
	public static final int str12=12;
	public static final int str13=13;
	public static final int str14=14;
	public static final int str15=15;
	public static final int str16=16;
	public static final int str17=17;
	public static final int str18=18;
	
	public static final int ASCENDING = 0;
	public static final int DESCENDING = 1;
	
	private int column;
	private int direction;
	
	/**
	 * Compares two Player objects
	 * 
	 * @param obj1 the first Player
	 * @param obj2 the second Player
	 * @return int
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	
	public int compare(Object obj1, Object obj2) {
		int rc = 0;
	    Player p1 = (Player) obj1;
	    Player p2 = (Player) obj2;
	
	    // Determine which field to sort on, then sort on that field
	    switch (column) {
		    case str0: rc = p1.getstr0().compareTo(p2.getstr0()); break;
		    case str1: rc = p1.getstr1().compareTo(p2.getstr1()); break;
		    case str2: rc = p1.getstr2().compareTo(p2.getstr2()); break;
		    case str3: rc = p1.getstr3().compareTo(p2.getstr3()); break;
		    case str4: rc = p1.getstr4().compareTo(p2.getstr4()); break;
		    case str5: rc = p1.getstr5().compareTo(p2.getstr5()); break;
		    case str6: rc = p1.getstr6().compareTo(p2.getstr6()); break;
		    case str7: rc = p1.getstr7().compareTo(p2.getstr7()); break;
		    case str8: rc = p1.getstr8().compareTo(p2.getstr8()); break;
		    case str9: rc = p1.getstr9().compareTo(p2.getstr9()); break;
		    case str10: rc = p1.getstr10().compareTo(p2.getstr10()); break;
		    case str11: rc = p1.getstr11().compareTo(p2.getstr11()); break;
		    case str12: rc = p1.getstr12().compareTo(p2.getstr12()); break;
		    case str13: rc = p1.getstr13().compareTo(p2.getstr13()); break;
		    case str14: rc = p1.getstr14().compareTo(p2.getstr14()); break;
		    case str15: rc = p1.getstr15().compareTo(p2.getstr15()); break;
		    case str16: rc = p1.getstr16().compareTo(p2.getstr16()); break;
		    case str17: rc = p1.getstr17().compareTo(p2.getstr17()); break;
		    case str18: rc = p1.getstr18().compareTo(p2.getstr18()); break;
	    }
	
	    // Check the direction for sort and flip the sign if appropriate
	    if (direction == ASCENDING) 
	    	rc = -rc;
	    return rc;
	}
	
	// Sets the column for sorting
	public void setColumn(int column) { this.column = column; }
	
	// Sets the direction for sorting
	public void setDirection(int direction) { this.direction = direction; }	
	
	// Reverses the direction
	public void reverseDirection() { direction = 1 - direction; }
}


//miDeleteAll.addSelectionListener(new SelectionAdapter() {
//	public void widgetSelected(SelectionEvent e) {
//		DialogFactory.dialogYesNo(MainForm.shell);
//	    boolean rc = DialogFactory.getPenentuan();
//	    if (rc==true) {
//	    	if (!shlPass.isDisposed()) shlPass.close();
//			shlPass = new Shell(AmscSplashScreen.display, SWT.NO_TRIM);
//			OpenPassword pass = new OpenPassword();
//			if (pass.open(shlPass)) {
//				if (!shlPass.isDisposed()) shlPass.close();
//				shlPass = new Shell(AmscSplashScreen.display,SWT.MIN);
//				
//    			try {
//    				//STEP 1: Register JDBC driver
//    				Class.forName(ConnectTo.JDBC_DRIVER);
//
//    				//STEP 2: Open a connection
//    				connDelAll = DriverManager.getConnection(ConnectTo.DB_NAME,ConnectTo.DB_USER,ConnectTo.DB_PASS);
//    				
//    				final cProgressBar progbr = new cProgressBar("Selecting data ...");
//    				new Thread() {
//    					public void run() {
//    						if (AmscSplashScreen.display.isDisposed()) return;
//    						AmscSplashScreen.display.asyncExec(new Runnable() {
//    							public void run() {
//    								try {
//    									progbr.create(iLimitRow);
//    								    // Search criteria
//    								    String select = "DELETE FROM ";
//    									String where = " WHERE tgl!='0000-00-00 00:00:00'";
//    									
//    									iJmlData=0; //inisialisasi
//    									iCnt=0;
//    									rowNo=0;
//    									rsDelAll = new ResultSet[Integer.parseInt(yearTo)*Integer.parseInt(monthTo)];
//    									boolean flg=false;
//    									
//    									for (int iYear=Integer.parseInt(yearFr); iYear<=Integer.parseInt(yearTo); iYear++) {
//    										int iMtemp=0,n=0,nn=0,iMonth=0;
//    										
//    										if (iYear==Integer.parseInt(yearFr)) { 
//    											iMtemp=Integer.parseInt(monthFr); n=12-iMtemp; } 
//    										else if ((iYear>Integer.parseInt(yearFr)) && (iYear < Integer.parseInt(yearTo))) {
//    											iMtemp=1; n=12-iMtemp; } 
//    										else if (iYear == Integer.parseInt(yearTo)) {
//    											iMtemp= Integer.parseInt(monthTo); n=iMtemp; iMtemp=0; flg=true; }
//    									
//    										for (iMonth=iMtemp; iMonth<=n+iMtemp; iMonth++) {
//    											if ((flg) && (iMonth>=12)) { nn++; if ((n==iMtemp) && (nn>12)) break; } 
//    											else nn=iMonth;
//    											
//    											String sMonth = Integer.toString(nn);
//    											if (sMonth.length()==1) sMonth = sMonth.replace(sMonth, "0"+sMonth);
//    											tblName=sTblNm+iYear+"_"+sMonth;
//    											
//    											//STEP 3: Execute a query
//    											stmtDelAll = connDelAll.createStatement();
//    											
//    											try { //biar kalo ada table yang doesn't exist ga mati.
//    												String sQuery = select+tblName+where+sCriteria;//+order;
//    												System.out.println("Query:" + sQuery + "#");
//    												stmtDelAll.execute(sQuery);
//    											} catch (SQLException s) { // biar kalo ada table yang doesn't exist ga mati.
//    												System.out.println("**Info:" + s.getMessage() + "#");
//    											}
//    											iCnt++; //counter rs
//    										} //end of for2
//    									} //end of for1
//    									progbr.finish();
//    									
//    								} catch (SQLException s) {
//    									DialogFactory.dialogInfo(MainForm.shell, "Delete All Message", s.toString());
//    									s.printStackTrace();
//    									progbr.finish();
//    								} catch (java.lang.OutOfMemoryError hs) {
//    									DialogFactory.dialogInfo(MainForm.shell, "Delete All Message", "Out of memory !\nPlease fill another criteria search ! ");
//    									progbr.finish();
//    								}
//    							}
//    						});
//    					}
//    				}.start();
//    				progbr.begin();
//    			
//    				//STEP 5: Clean-up environment
//    			    stmtDelAll.close();
//    			    connDelAll.close();
//    		    
//    			} catch(SQLException se){
//    				//Handle errors for JDBC
//    				se.printStackTrace();
//    			} catch(Exception xe){
//    				//Handle errors for Class.forName
//    				xe.printStackTrace();
//    			}
//    			
//    			System.out.println("**delete:succeed#"); 
//    			//refreshing table
//				table.setRedraw(false);
//				table.removeAll();
//				table.setRedraw(true);
//				setInfoRowZero();
//			}
//			
//			if (!tTextual.getText().isEmpty()) tTextual.setText("");
//	    } else System.out.println("**delete:cancelled#");
//	}
//});


//void writeForExport(String strTrace) {
//	String cid=this.str2;
//	String seq=this.str3;
//	String text=this.str6;
//	
//	if (text.startsWith(cid+seq)) {
//		if (text.contains("\n")) {
//			int index = text.indexOf("\n");
//			text=text.substring(index+1, text.length());
//		}
//	}
//	
////	printable+="data="+text+";trace="+this.str7+";"+"#";
////	xls();
////	if (str6.isEmpty()) str6=" "; else if (str6.contains("\n")) str6= str6.replaceAll("\n", "~");
////	if (str7.isEmpty()) str7=" "; else if (str7.contains("\n")) str7= str7.replaceAll("\n", "~");
////	msg+=str6+";"+str7+";";
////	printableXLS+=msg+"\n";
//	
//	
//	if (strTrace.compareToIgnoreCase("trace")==0) {
//		printable+="data="+text+";trace="+this.str7+";"+"#";
//		xls();
//		if (str6.isEmpty()) str6=" "; else if (str6.contains("\n")) str6= str6.replaceAll("\n", "~");
//		if (str7.isEmpty()) str7=" "; else if (str7.contains("\n")) str7= str7.replaceAll("\n", "~");
//		msg+=str6+";"+str7+";";
//		printableXLS+=msg+"\n"; 
//	} else if (strTrace.compareToIgnoreCase("notrace")==0) {
//		printableRet+="data="+text+";#";
//		xls();
//		if (str6.isEmpty()) str6=" "; else if (str6.contains("\n")) str6= str6.replaceAll("\n", "~");
//		msg+=str6+";";
//		printableXLSRet+=msg+"\n";
//	}
//}
//
//void writeToListOutgoing(TableItem iout,String str0,String str1,String str2,String str3,String str4,String str5,String str6,String str7) {
////	if (rowNo<=iLimitRow) {
//		iout = new TableItem(Lists.table, SWT.NONE);
//		iout.setText(0, str0);
//		iout.setText(1, str1);
//		iout.setText(2, str2);
//		iout.setText(3, str3);
//		iout.setText(4, str4);
//		iout.setText(5, str5);
//		iout.setText(6, str6);	
//		iout.setText(7, str7);	
//		players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7));	
////	}
//	
////	iout = new TableItem(Lists.table, SWT.NONE);
////	iout.setText(0, str0);
////	iout.setText(1, str1);
////	iout.setText(2, str2);
////	iout.setText(3, str3);
////	iout.setText(4, str4);
////	iout.setText(5, str5);
////	iout.setText(6, str6);	
////	iout.setText(7, str7);	
////	players.add(new Player(str0,str1,str2,str3,str4,str5,str6,str7));
//}

//private void getOutgoing(String sType, int counter,String strOutt,String str0,String str1,String str2,String str3,String str4,String str5,String str6,String str7) throws SQLException {
//private void setOutgoing(String sTBLNM,String sIDCNT,String sCID,String sSEQ,String sTMS,String sTGL,String sDATA,String sTRACE) {
//	String sQuery = "INSERT INTO "+sTBLNM+" (idcnt,cid,seq,firstdt,tgl,data,trace) VALUES " +
//			"('"+sIDCNT+"','"+sCID+"','"+sSEQ+"','"+sTMS+"','"+sTGL+"','"+sDATA+"','"+sTRACE+"')";
//	Jdbc.select(sQuery, "");
//}
//
//private void getOutgoing(String strTrace, String sType, ResultSet[] rs, int counter,String strOutt,
//		String str0,String str1,String str2,String str3,String str4,String str5,String str6,String str7) throws SQLException {
//	
//	if (strOutt.length()==33) {
//		str2 = strOutt.substring(0, 3);
//		str3 = strOutt.substring(3, 7);
//		if (str3.contains(" ")) str3=str3.replace(" ", "");
//		str4 = strOutt.substring(7, 13);
//		str5 = strOutt.substring(14, 33);
//	} else if (strOutt.length()==32) {
//		str2 = strOutt.substring(0, 3);
//		str3 = strOutt.substring(3, 6);
//		str4 = strOutt.substring(6, 12);
//		str5 = strOutt.substring(13, 32);
//	}
//	
//	//tambahan
//	if (str4.compareTo("      ")==0) str4="";
//	
//	if (sType.compareToIgnoreCase("export")==0) {
//		this.str2 = rs[counter].getString("cid"); if (this.str2==null) this.str2="";
//		this.str3 = rs[counter].getString("seq"); if (this.str3==null) this.str3="";
//	}
//	
//	str6=""; //kol6: Data
//	String sKolom6=this.str6 = rs[counter].getString("data"); if (sKolom6==null) sKolom6="";
//	if (sType.compareToIgnoreCase("export")==0) this.str6=sKolom6;
//	else {
//		int index=0,jmldata=0,isub=0;
//		String subs[]=null;
//		if (sKolom6.contains("\n")) {
//			subs = sKolom6.split("\n");
//			for (; isub<subs.length; isub++) {
//				if (subs[isub].startsWith("0") || subs[isub].startsWith("1") ||
//					subs[isub].startsWith("2") || subs[isub].startsWith("3") ||
//					subs[isub].startsWith("4") || subs[isub].startsWith("5") ||
//					subs[isub].startsWith("6") || subs[isub].startsWith("7") ||
//					subs[isub].startsWith("8") || subs[isub].startsWith("9")) {
//					if (subs[isub].contains(" ")) {
//						int indSpace = subs[isub].indexOf(" ");
//						String tada = subs[isub].substring(0, indSpace);
//						if (tada.length()<6) { /*System.out.println(">=6");*/ } 
//						else if (tada.length()==6) { index=isub; }
//					}
//				}
//				jmldata++;
//			}
//		} else {
//			str6=sKolom6;
//		}
//		if (jmldata>(index+1)) { str6=subs[index+1]; } 
//		else if (jmldata==index) { str6=sKolom6; }
//		str6 = str6.replaceAll("\\r\\n|\\r|\\n", " "); //remove CRLF
//	}
//	
//	str7=""; //kol7: Trace
//	String sKolom7=this.str7 = rs[counter].getString("trace"); if (sKolom7==null) sKolom7="";
//	if (sType.compareToIgnoreCase("export")==0) this.str7=sKolom7;
//	else {
//		if (sKolom7.endsWith("\n")) { sKolom7=sKolom7.substring(0, sKolom7.lastIndexOf("\n")-1); }
//		String sub[] = sKolom7.split("\n");
//		for (int y=0; y<sub.length; y++) if (sub[y].startsWith("I")) str7=sub[y]; 
//	}
//	
//	// SEMUA KOSONG
//	if (strCID.isEmpty()&&strSEQFR.isEmpty()&&strSEQTO.isEmpty()&&strTMS.isEmpty()) {
//		if (sType.compareToIgnoreCase("export")!=0) {
//			setOutgoing(str0,str1,str2,str3,str4,str5,this.str6,this.str7);
//			writeToListOutgoing(iout,str0,str1,str2,str3,str4,str5,str6,str7);
//		}
//		else writeForExport(strTrace);
//	}
//	// SEMUA ADA
//	else if (!strCID.isEmpty()&&!strSEQFR.isEmpty()&&!strSEQTO.isEmpty()&&!strTMS.isEmpty()) {
//		if (str2.compareTo(strCID)==0 && 
//				(Integer.parseInt(str3)>=Integer.parseInt(strSEQFR)) &&
//				(Integer.parseInt(str3)<=Integer.parseInt(strSEQTO)) && 
//				str4.compareTo(strTMS)==0) { 
//			if (sType.compareToIgnoreCase("export")!=0) writeToListOutgoing(iout,str0,str1,str2,str3,str4,str5,str6,str7);
//			else writeForExport(strTrace);
//		}
//	}
//	//strCID ADA (str2)
//	else if (!strCID.isEmpty()&&strSEQFR.isEmpty()&&strSEQTO.isEmpty()&&strTMS.isEmpty()) {
//		if (str2.compareTo(strCID)==0) { 
//			if (sType.compareToIgnoreCase("export")!=0) writeToListOutgoing(iout,str0,str1,str2,str3,str4,str5,str6,str7);
//			else writeForExport(strTrace);
//		}
//	}
//	else if (!strCID.isEmpty()&&!strSEQFR.isEmpty()&&strSEQTO.isEmpty()&&strTMS.isEmpty()) {
//		if (str2.compareTo(strCID)==0 && 
//				(Integer.parseInt(str3)>=Integer.parseInt(strSEQFR)) ) {
//			if (sType.compareToIgnoreCase("export")!=0) writeToListOutgoing(iout,str0,str1,str2,str3,str4,str5,str6,str7);
//			else writeForExport(strTrace);
//		}
//	}
//	else if (!strCID.isEmpty()&&!strSEQFR.isEmpty()&&!strSEQTO.isEmpty()&&strTMS.isEmpty()) {
//		if (str2.compareTo(strCID)==0 && 
//				(Integer.parseInt(str3)>=Integer.parseInt(strSEQFR)) &&
//				(Integer.parseInt(str3)<=Integer.parseInt(strSEQTO))) { 
//			if (sType.compareToIgnoreCase("export")!=0) writeToListOutgoing(iout,str0,str1,str2,str3,str4,str5,str6,str7);
//			else writeForExport(strTrace);
//		}
//	}
//	//strSEQFR ADA (str3)
//	else if (strCID.isEmpty()&&!strSEQFR.isEmpty()&&strSEQTO.isEmpty()&&strTMS.isEmpty()) {
//		if ((Integer.parseInt(str3)>=Integer.parseInt(strSEQFR))) { 
//			if (sType.compareToIgnoreCase("export")!=0) writeToListOutgoing(iout,str0,str1,str2,str3,str4,str5,str6,str7);
//			else writeForExport(strTrace);
//		}
//	}
//	else if (strCID.isEmpty()&&!strSEQFR.isEmpty()&&!strSEQTO.isEmpty()&&strTMS.isEmpty()) {
//		if ((Integer.parseInt(str3)>=Integer.parseInt(strSEQFR)) && (Integer.parseInt(str3)<=Integer.parseInt(strSEQTO))) { 
//			if (sType.compareToIgnoreCase("export")!=0) writeToListOutgoing(iout,str0,str1,str2,str3,str4,str5,str6,str7);
//			else writeForExport(strTrace);
//		}		
//	}
//	//6=2,7=3
//	//strSEQTO ADA (str3)
//	else if (strCID.isEmpty()&&strSEQFR.isEmpty()&&!strSEQTO.isEmpty()&&strTMS.isEmpty()) {
//		if ((Integer.parseInt(str3)<=Integer.parseInt(strSEQTO))) { 
//			if (sType.compareToIgnoreCase("export")!=0) writeToListOutgoing(iout,str0,str1,str2,str3,str4,str5,str6,str7);
//			else writeForExport(strTrace);
//		} 
//	}
//	else if (!strCID.isEmpty()&&strSEQFR.isEmpty()&&!strSEQTO.isEmpty()&&strTMS.isEmpty()) {
//		if (str2.compareTo(strCID)==0 && 
//				(Integer.parseInt(str3)<=Integer.parseInt(strSEQTO))) { 
//			if (sType.compareToIgnoreCase("export")!=0) writeToListOutgoing(iout,str0,str1,str2,str3,str4,str5,str6,str7);
//			else writeForExport(strTrace);
//		} 
//	}
//	//11=3
//	//strTMS ADA (str4)
//	else if (strCID.isEmpty()&&strSEQFR.isEmpty()&&strSEQTO.isEmpty()&&!strTMS.isEmpty()) {
//		if (str4.compareTo(strTMS)==0) { 
//			if (sType.compareToIgnoreCase("export")!=0) writeToListOutgoing(iout,str0,str1,str2,str3,str4,str5,str6,str7);
//			else writeForExport(strTrace);
//		} 
//	}
//	else if (!strCID.isEmpty()&&strSEQFR.isEmpty()&&strSEQTO.isEmpty()&&!strTMS.isEmpty()) {
//		if (str2.compareTo(strCID)==0 && 
//				str4.compareTo(strTMS)==0) {
//			if (sType.compareToIgnoreCase("export")!=0) writeToListOutgoing(iout,str0,str1,str2,str3,str4,str5,str6,str7);
//			else writeForExport(strTrace);
//		} 
//	}
//	else if (!strCID.isEmpty()&&!strSEQFR.isEmpty()&&strSEQTO.isEmpty()&&!strTMS.isEmpty()) {
//		if (str2.compareTo(strCID)==0 && 
//				(Integer.parseInt(str3)>=Integer.parseInt(strSEQFR)) &&
//				str4.compareTo(strTMS)==0) { 
//			if (sType.compareToIgnoreCase("export")!=0) writeToListOutgoing(iout,str0,str1,str2,str3,str4,str5,str6,str7);
//			else writeForExport(strTrace);
//		} 
//	}
////	} else System.out.println("exportttttttt");
//	
////	iJmlData=icount;//table.getItemCount();//JUMLAH berita outgoing -kalo dibikin
//	iJmlData=table.getItemCount();//JUMLAH berita outgoing
//}

//void exportToFileASLI(final String fileType,final String strTrace) {
//	try {
//		//STEP 1: Register JDBC driver
//		Class.forName(ConnectTo.JDBC_DRIVER);
//		//STEP 2: Open a connExportection
//		connExport = DriverManager.getConnection(ConnectTo.DB_NAME,ConnectTo.DB_USER,ConnectTo.DB_PASS);
//		
//		final cProgressBar progbr = new cProgressBar("Selecting data ...");
//		new Thread() {
//			public void run() {
//				if (AmscSplashScreen2.display.isDisposed()) return;
//				AmscSplashScreen2.display.asyncExec(new Runnable() {
//					public void run() {
//						try {
//							progbr.create(iLimitRow);
//						    // Search criteria
////							String order = " ORDER BY tgl ASC, "+sIdName+" DESC";
//							String select = "SELECT * FROM ";
//							String where = " WHERE tgl!='0000-00-00 00:00:00'";
//							String order = " ORDER BY tgl DESC, "+sIdName+" DESC";
//							
//							//STEP 3: Execute a query
//							iJmlData=0; //inisialisasi
//							iCnt=0;
//							rowNo=0;
//							
//							int parseYearTo = Integer.parseInt(yearTo);
//							int parseYearFr = Integer.parseInt(yearFr);
//							int parseMonthTo = Integer.parseInt(monthTo);
//							int parseMonthFr = Integer.parseInt(monthFr);
//							int parseDayTo = Integer.parseInt(dayTo);
//							int parseDayFr = Integer.parseInt(dayFr);
//							
//							rsExport = new ResultSet[parseYearTo*parseMonthTo*parseDayTo];
//							boolean flg=false;
//							boolean flgD=false;
//							
//							for (int iYear=parseYearFr; iYear<=parseYearTo; iYear++) {
//								int iMtemp=0,n=0,nn=0;
//								
//								if (iYear==parseYearFr) {//System.out.println("masuk stat A");
//									iMtemp=parseMonthFr;
//									n=12-iMtemp;
//								} else if ((iYear>parseYearFr) && (iYear<parseYearTo)) {//System.out.println("masuk stat B");
//									iMtemp=1;
//									n=12-iMtemp;
//								} else if (iYear == parseYearTo) {//System.out.println("masuk stat C");
//									iMtemp= parseMonthTo;
//									n=iMtemp;
//									iMtemp=0;
//									flg=true;
//								}
//							
//								for (int iMonth=iMtemp; iMonth<=n+iMtemp; iMonth++) {
//									if ((flg) && (iMonth>=12)) {
//										nn++; 
//										if ((n==iMtemp) && (nn>12)) break; 
//									} else nn=iMonth;
//									
//									String sMonth = Integer.toString(nn);
//									if (sMonth.length()==1) sMonth = sMonth.replace(sMonth, "0"+sMonth);
//									
//									int iDtemp=0,d=0,dd=0;
//									if (sTblNm.compareToIgnoreCase("aftnrdI")==0 || sTblNm.compareToIgnoreCase("aftnrdO")==0) {
//										if (iMonth==parseMonthFr) {//System.out.println("masuk stat AA");
//											iDtemp=parseDayFr;
//											d=31-iDtemp;
//										} else if ((iMonth>parseMonthFr) && (iMonth<parseMonthTo)) {//System.out.println("masuk stat BB");
//											iDtemp=1;
//											d=31-iDtemp;
//										} else if (iMonth == parseMonthTo) {//System.out.println("masuk stat CC");
//											iDtemp= parseDayTo;
//											d=iDtemp;
//											iDtemp=0;
//											flgD=true;
//										}
//									} else {
//										iDtemp=1;
//									}
//									
//									for (int iDay=iDtemp; iDay<=d+iDtemp; iDay++) {
//										if ((flgD) && (iDay>=31)) {
//											dd++; 
//											if ((d==iDtemp) && (dd>31)) break; 
//										} else dd=iDay;
//										
//										String sDay = Integer.toString(dd);
//										if (sDay.length()==1) sDay= sDay.replace(sDay, "0"+sDay);
//										
//									
//										if (sTblNm.compareToIgnoreCase("aftnrdI")==0) { tblName=sTblNm+iYear+"_"+sMonth+"_"+sDay; } 
//										else if (sTblNm.compareToIgnoreCase("aftnrdO")==0) { tblName=sTblNm+iYear+"_"+sMonth+"_"+sDay; } 
//										else { tblName=sTblNm+iYear+"_"+sMonth; }
//										
//										stmtExport = connExport.createStatement();
//										try { //biar kalo ada table yang doesn't exist ga mati.
//											sQueryFind = select+tblName+where+sCriteria+order;
////											System.out.println("QueryEx:" + sQueryFind + "#");
//											rsExport[iCnt] = stmtExport.executeQuery(sQueryFind);
//											rsExport[iCnt].last();
//											iJmlData += rsExport[iCnt].getRow();
//											rsExport[iCnt].beforeFirst();
//											
//											icount=0;
//											System.out.println("********iMaxExport::"+ iMaxExport + "#");
//											while (rsExport[iCnt].next() && (rowNo<iMaxExport)) {
//												rowNo++;
//												
//												//---------------------------------------------------------------------------------------
//												if (sID.compareTo(Titles.stIncoming)!=0 && sID.compareTo(Titles.stOutgoing)!=0) { 
//													str0=rsExport[iCnt].getString("tbl_name"); if (str0==null) str0="";//kol0: tblnm	
//													str1=rsExport[iCnt].getString(sIdName); if (str1==null) str1=""; //kol1: idtbl
//													str2=""; //kol2: cid
//													str3=""; //kol3: csn
//													str4=""; //kol4: tms
//													str5=rsExport[iCnt].getString("tgl").substring(0, 19); if (str5==null) str5=""; //kol5: tgl
//												
//												} else if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
//													str0="";//kol0: tblnm	
//													String tblnm="",yymmdd="",strTgl="";
//													strTgl=rsExport[iCnt].getString("tgl").substring(0, 19); if (strTgl==null) strTgl=""; 
//													if (!strTgl.isEmpty()&&strTgl.length()==19) { yymmdd=strTgl.substring(0,4)+"_"+strTgl.substring(5,7)+"_"+strTgl.substring(8,10); }
////													tblnm="aftnrdI";
//													if (sID.compareTo(Titles.stIncoming)==0) tblnm="aftnrdI";
//													else if (sID.compareTo(Titles.stOutgoing)==0) tblnm="aftnrdO";
//													str0 = tblnm+yymmdd;
//													str1 = rsExport[iCnt].getString(sIdName); if (str1==null) str1=""; //kol1: idtbl
//													str2 = rsExport[iCnt].getString("cid"); if (str2==null) str2=""; //kol2: cid
//													str3 = rsExport[iCnt].getString("seq"); if (str3==null) str3=""; //kol3: csn
//
//													str4=""; //kol4: tms
//													if (sID.compareTo(Titles.stIncoming)==0) {
//														String sKolom4 = rsExport[iCnt].getString("data"); if (sKolom4==null) sKolom4="";
//														if (!sKolom4.isEmpty() && sKolom4.contains("\n")) {
//															sKolom4 = sKolom4.substring(0, sKolom4.indexOf("\n"));
//															if (sKolom4.contains(" ") && sKolom4.length()>=14) {
//																str4 = sKolom4.substring(sKolom4.indexOf(" ")+1, sKolom4.indexOf(" ")+7);	
//															} else str4="";
//														}
//													} else if (sID.compareTo(Titles.stOutgoing)==0) {
//														str4 = rsExport[iCnt].getString("firstdt"); if (str4==null) str4=""; //kol4: tms
//													}
//													
//													str5=rsExport[iCnt].getString("tgl").substring(0, 19); if (str5==null) str5=""; //kol5: tgl
//													
//													str6=""; //kol6: Data
//													String sKolom6 = rsExport[iCnt].getString("data"); if (sKolom6==null) sKolom6="";
//													str6=sKolom6;
//													
//													str7=""; //kol7: Trace
//													String sKolom7 = rsExport[iCnt].getString("trace"); if (sKolom7==null) sKolom7="";
//													str7=sKolom7;
//												} 
//												
//												//EXPORT MASING2 TIPE
//												if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
//													if (strTrace.compareToIgnoreCase("trace")==0) {
//														printable+="data="+str6+";trace="+str7+";"+"#";
//	    										    	xls();
//	    										    	if (str6.isEmpty()) str6=" "; //else if (str6.contains("\n")) str6= str6.replaceAll("\n", "~");
//	    										    	if (str7.isEmpty()) str7=" "; //else if (str7.contains("\n")) str7= str7.replaceAll("\n", "~");
//	    										    	msg+=str6+";"+str7+";";
//	    										    	printableXLS+=msg+"#";//printableXLS+=msg+"\n"; 
//													} else if (strTrace.compareToIgnoreCase("notrace")==0) {
//														printableRet+="data="+str6+";#";
//	    										    	xls();
//	    										    	if (str6.isEmpty()) str6=" "; //else if (str6.contains("\n")) str6= str6.replaceAll("\n", "~");
//	    										    	msg+=str6+";";
//	    										    	printableXLSRet+=msg+"#";//printableXLSRet+=msg+"\n";	
//													}
//												}
//										
//												else if (sID.compareTo(Titles.stReject)==0) {
//													str6 = rsExport[iCnt].getString("type_message"); if (str6==null) str6="";
//													str7 = rsExport[iCnt].getString("free_text_error_message"); if (str7==null) str7="";
//													str8 = rsExport[iCnt].getString("origin_message"); if (str8==null) str8="";
//													
//													printable+="data="+str7+";trace="+str8+";"+"#";
//													xls();
//													if (str7.isEmpty()) str7=" "; //else if (str7.contains("\n")) str7= str7.replaceAll("\n", "~");
//													if (str8.isEmpty()) str8=" "; //else if (str8.contains("\n")) str8= str8.replaceAll("\n", "~");
//													msg+=str6+";"+str7+";"+str8+";";
//													printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; }
//												
//												else if (sID.compareTo(Titles.stFREETEXT)==0 || sID.compareTo(Titles.stRQM)==0) {
//    												vATS.setHeader(rsExport[iCnt]);
//    										    	vATS.setFree(rsExport[iCnt],sID);
//    										    	vATS.getFREEATS();
//    										    	
//    										    	String dataPDF = vATS.pfree;
//    										    	String sFree = vATS.strFree;
//    										    	if (sFree.isEmpty()) sFree=" "; //else if (sFree.contains("\n")) sFree= sFree.replaceAll("\n", "~");
//    										    	String dataXLS = sFree;
//    										    	
////    										    	if (dataPDF.endsWith("\n")) {
////    										    		int index = dataPDF.lastIndexOf("\n");
////    										    		dataPDF=dataPDF.substring(0, index);
////    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	xls(); 
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; }
//    												
//												else if (sID.compareTo(Titles.stALR)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vATS.set3(rsExport[iCnt]); 
//													vATS.set5(rsExport[iCnt]); 
//													vATS.set7(rsExport[iCnt]); 
//													vATS.set8(rsExport[iCnt]); 
//													vATS.set9(rsExport[iCnt]); 
//													vATS.set10(rsExport[iCnt]); 
//													vATS.set13(rsExport[iCnt]); 
//													vATS.set15(rsExport[iCnt]); 
//													vATS.set16(rsExport[iCnt]); 
//													vATS.set18(rsExport[iCnt]); 
//													vATS.set19(rsExport[iCnt]); 
//													vATS.set20(rsExport[iCnt]); 
//													vATS.msgALR();
//													vATS.getALR();
//													
//													String dataPDF = vATS.palr+"\n";
//													xls(); 
//    										    	if (vATS.type7a.isEmpty()) vATS.type7a=" ";
//    										    	if (vATS.type9b.isEmpty()) vATS.type9b=" ";
//    										    	if (vATS.type13a.isEmpty()) vATS.type13a=" ";
//    										    	if (vATS.type13b.isEmpty()) vATS.type13b=" ";
//    										    	if (vATS.type15b.isEmpty()) vATS.type15b=" ";
//    										    	if (vATS.type15c.isEmpty()) vATS.type15c=" "; //else if (vATS.type15c.contains("\n")) vATS.type15c= vATS.type15c.replaceAll("\n", "~");
//    										    	if (vATS.type16a.isEmpty()) vATS.type16a=" ";
//    												String dataXLS = vATS.type7a+";"+vATS.type9b+";"+vATS.type13a+";"+vATS.type13b+";"+
//    										    	vATS.type15b+";"+vATS.type15c+";"+vATS.type16a+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//    												
//												else if (sID.compareTo(Titles.stRCF)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vATS.set3(rsExport[iCnt]);
//													vATS.set7(rsExport[iCnt]);
//													vATS.set21(rsExport[iCnt]);
//													vATS.msgRCF();
//													vATS.getRCF();
//													
//													String dataPDF = vATS.prcf+"\n";
//													xls(); 
//    										    	if (vATS.type7a.isEmpty()) vATS.type7a=" ";
//    										    	if (vATS.type7c.isEmpty()) vATS.type7c=" ";
//    										    	if (vATS.type21.isEmpty()) vATS.type21=" "; //else if (vATS.type21.contains("\n")) vATS.type21= vATS.type21.replaceAll("\n", "~");
//    										    	String dataXLS = vATS.type7a+";"+vATS.type7c+";"+vATS.type21+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//												
//												else if (sID.compareTo(Titles.stFPL)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vATS.set3(rsExport[iCnt]); 
//													vATS.set7(rsExport[iCnt]); 
//													vATS.set8(rsExport[iCnt]); 
//													vATS.set9(rsExport[iCnt]); 
//													vATS.set10(rsExport[iCnt]); 
//													vATS.set13(rsExport[iCnt]); 
//													vATS.set15(rsExport[iCnt]); 
//													vATS.set16(rsExport[iCnt]); 
//													vATS.set18(rsExport[iCnt]); 
//													vATS.set19(rsExport[iCnt]); 
//													vATS.setSpace(rsExport[iCnt]); 
//													vATS.msgFPL();
//													vATS.getFPL();
//													
//													String dataPDF = vATS.pfpl+"\n";
//													xls(); 
//    										    	if (vATS.type7a.isEmpty()) vATS.type7a=" ";
//    										    	if (vATS.type9b.isEmpty()) vATS.type9b=" ";
//    										    	if (vATS.type13a.isEmpty()) vATS.type13a=" ";
//    										    	if (vATS.type13b.isEmpty()) vATS.type13b=" ";
//    										    	if (vATS.type15b.isEmpty()) vATS.type15b=" ";
//    										    	if (vATS.type15c.isEmpty()) vATS.type15c=" "; //else if (vATS.type15c.contains("\n")) vATS.type15c= vATS.type15c.replaceAll("\n", "~");
//    										    	if (vATS.type16a.isEmpty()) vATS.type16a=" ";
//    										    	if (vATS.type18.isEmpty()) vATS.type18=" "; //else if (vATS.type18.contains("\n")) vATS.type18= vATS.type18.replaceAll("\n", "~");
//    										    	if (vATS.type18.contains("~")) vATS.type18= vATS.type18.replaceAll("~", "/");
//    										    	String dataXLS = vATS.type7a+";"+vATS.type9b+";"+vATS.type13a+";"+vATS.type13b+";"+
//    										    	vATS.type15b+";"+vATS.type15c+";"+vATS.type16a+";"+vATS.type18+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//												
//												else if (sID.compareTo(Titles.stDLA)==0 || sID.compareTo(Titles.stCNL)==0 ||
//														sID.compareTo(Titles.stDEP)==0 || sID.compareTo(Titles.stRQP)==0 || 
//														sID.compareTo(Titles.stRQS)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vATS.set3(rsExport[iCnt]); 
//													vATS.set7(rsExport[iCnt]); 
//													vATS.set13(rsExport[iCnt]); 
//													vATS.set16(rsExport[iCnt]); 
//													vATS.set18(rsExport[iCnt]); 
//													vATS.msgDLA();
//													vATS.getDLA();
//													
//													String dataPDF = vATS.pdla+"\n";
//													xls(); 
//    										    	if (vATS.type7a.isEmpty()) vATS.type7a=" ";
//    										    	if (vATS.type13a.isEmpty()) vATS.type13a=" ";
//    										    	if (vATS.type13b.isEmpty()) vATS.type13b=" ";
//    										    	if (vATS.type16a.isEmpty()) vATS.type16a=" ";
//    										    	if (vATS.type18.isEmpty()) vATS.type18=" "; //else if (vATS.type18.contains("\n")) vATS.type18= vATS.type18.replaceAll("\n", "~");
//    										    	if (vATS.type18.contains("~")) vATS.type18= vATS.type18.replaceAll("~", "/");
//    										    	String dataXLS = vATS.type7a+";"+vATS.type13a+";"+vATS.type13b+";"+vATS.type16a+";"+
//    										    	vATS.type18+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//												
//												else if (sID.compareTo(Titles.stCHG)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vATS.set3(rsExport[iCnt]); 
//													vATS.set7(rsExport[iCnt]); 
//													vATS.set13(rsExport[iCnt]); 
//													vATS.set16(rsExport[iCnt]); 
//													vATS.set18(rsExport[iCnt]); 
//													vATS.set22(rsExport[iCnt]); 
//													vATS.msgCHG();
//													vATS.getCHG();
//													
//													String dataPDF = vATS.pchg+"\n";
//													xls(); 
//    										    	if (vATS.type7a.isEmpty()) vATS.type7a=" ";
//    										    	if (vATS.type13a.isEmpty()) vATS.type13a=" ";
//    										    	if (vATS.type13b.isEmpty()) vATS.type13b=" ";
//    										    	if (vATS.type16a.isEmpty()) vATS.type16a=" ";
//    										    	if (vATS.type18.isEmpty()) vATS.type18=" "; //else if (vATS.type18.contains("\n")) vATS.type18= vATS.type18.replaceAll("\n", "~");
//    										    	if (vATS.type18.contains("~")) vATS.type18= vATS.type18.replaceAll("~", "/");
//    										    	String dataXLS = vATS.type7a+";"+vATS.type13a+";"+vATS.type13b+";"+vATS.type16a+";"+
//    										    	vATS.type18+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//												
//												else if (sID.compareTo(Titles.stARR)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vATS.set3(rsExport[iCnt]); 
//													vATS.set7(rsExport[iCnt]); 
//													vATS.set13(rsExport[iCnt]); 
//													vATS.set17(rsExport[iCnt]); 
//													vATS.msgARR();
//													vATS.getARR();
//													
//													String dataPDF = vATS.parr+"\n";
//													xls(); 
//    										    	if (vATS.type7a.isEmpty()) vATS.type7a=" ";
//    										    	if (vATS.type13a.isEmpty()) vATS.type13a=" ";
//    										    	if (vATS.type13b.isEmpty()) vATS.type13b=" ";
//    										    	if (vATS.type17a.isEmpty()) vATS.type17a=" ";
//    										    	if (vATS.type17b.isEmpty()) vATS.type17b=" ";
//    												String dataXLS = vATS.type7a+";"+vATS.type13a+";"+vATS.type13b+";"+vATS.type17a+";"+
//    										    	vATS.type17b+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//												
//												else if (sID.compareTo(Titles.stCDN)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vATS.set3(rsExport[iCnt]); 
//													vATS.set7(rsExport[iCnt]); 
//													vATS.set13(rsExport[iCnt]); 
//													vATS.set16(rsExport[iCnt]); 
//													vATS.set22(rsExport[iCnt]); 
//													vATS.msgCDN();
//													vATS.getCDN();
//													
//													String dataPDF = vATS.pcdn+"\n";
//													xls(); 
//    										    	if (vATS.type7a.isEmpty()) vATS.type7a=" ";
//    										    	if (vATS.type13a.isEmpty()) vATS.type13a=" ";
//    										    	if (vATS.type13b.isEmpty()) vATS.type13b=" ";
//    										    	if (vATS.type16a.isEmpty()) vATS.type16a=" ";
//    										    	if (vATS.type22.isEmpty()) vATS.type22=" "; //else if (vATS.type22.contains("\n")) vATS.type22= vATS.type22.replaceAll("\n", "~");
//    										    	String dataXLS = vATS.type7a+";"+vATS.type13a+";"+vATS.type13b+";"+vATS.type16a+";"+
//    										    	vATS.type22+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//												 
//												else if (sID.compareTo(Titles.stCPL)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vATS.set3(rsExport[iCnt]); 
//													vATS.set7(rsExport[iCnt]); 
//													vATS.set8(rsExport[iCnt]); 
//													vATS.set9(rsExport[iCnt]); 
//													vATS.set10(rsExport[iCnt]); 
//													vATS.set13(rsExport[iCnt]); 
//													vATS.set14(rsExport[iCnt]); 
//													vATS.set15(rsExport[iCnt]); 
//													vATS.set16(rsExport[iCnt]); 
//													vATS.set18(rsExport[iCnt]); 
//													vATS.msgCPL();
//													vATS.getCPL();
//													
//													String dataPDF = vATS.pcpl+"\n";
//													xls(); 
//    										    	if (vATS.type7a.isEmpty()) vATS.type7a=" ";
//    										    	if (vATS.type9b.isEmpty()) vATS.type9b=" ";
//    										    	if (vATS.type13a.isEmpty()) vATS.type13a=" ";
//    										    	if (vATS.type13b.isEmpty()) vATS.type13b=" ";
//    										    	if (vATS.type15b.isEmpty()) vATS.type15b=" ";
//    										    	if (vATS.type15c.isEmpty()) vATS.type15c=" "; //else if (vATS.type15c.contains("\n")) vATS.type15c= vATS.type15c.replaceAll("\n", "~");
//    										    	if (vATS.type16a.isEmpty()) vATS.type16a=" ";
//    												String dataXLS = vATS.type7a+";"+vATS.type9b+";"+vATS.type13a+";"+vATS.type13b+";"+
//    										    	vATS.type15b+";"+vATS.type15c+";"+vATS.type16a+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//
//												else if (sID.compareTo(Titles.stEST)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vATS.set3(rsExport[iCnt]); 
//													vATS.set7(rsExport[iCnt]); 
//													vATS.set13(rsExport[iCnt]); 
//													vATS.set14(rsExport[iCnt]); 
//													vATS.set16(rsExport[iCnt]); 
//													vATS.msgEST();
//													vATS.getEST();
//													
//													String dataPDF = vATS.pest+"\n";
//													xls(); 
//    										    	if (vATS.type7a.isEmpty()) vATS.type7a=" ";
//    										    	if (vATS.type13a.isEmpty()) vATS.type13a=" ";
//    										    	if (vATS.type13b.isEmpty()) vATS.type13b=" ";
//    										    	if (vATS.type14a.isEmpty()) vATS.type14a=" ";
//    										    	if (vATS.type14b.isEmpty()) vATS.type14b=" ";
//    										    	if (vATS.type16a.isEmpty()) vATS.type16a=" ";
//    										    	String dataXLS = vATS.type7a+";"+vATS.type13a+";"+vATS.type13b+";"+vATS.type14a+";"+
//    										    	vATS.type14b+";"+vATS.type16a+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//
//												else if (sID.compareTo(Titles.stACP)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vATS.set3(rsExport[iCnt]); 
//													vATS.set7(rsExport[iCnt]); 
//													vATS.set13(rsExport[iCnt]); 
//													vATS.set16(rsExport[iCnt]); 
//													vATS.msgACP();
//													vATS.getACP();
//													
//													String dataPDF = vATS.pacp+"\n";
//													xls(); 
//    										    	if (vATS.type7a.isEmpty()) vATS.type7a=" ";
//    										    	if (vATS.type13a.isEmpty()) vATS.type13a=" ";
//    										    	if (vATS.type13b.isEmpty()) vATS.type13b=" ";
//    										    	if (vATS.type16a.isEmpty()) vATS.type16a=" ";
//    												String dataXLS = vATS.type7a+";"+vATS.type13a+";"+vATS.type13b+";"+vATS.type16a+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//
//												else if (sID.compareTo(Titles.stLAM)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vATS.set3(rsExport[iCnt]); 
//													vATS.msgLAM();
//													vATS.getLAM();
//													
//													String dataPDF = vATS.plam+"\n";
//													xls(); 
//    										    	if (vATS.type3b.isEmpty()) vATS.type3b=" ";
//    										    	if (vATS.type3c.isEmpty()) vATS.type3c=" ";
//    												String dataXLS = vATS.type3b+";"+vATS.type3c+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//
//												else if (sID.compareTo(Titles.stSPL)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vATS.set3(rsExport[iCnt]); 
//													vATS.set7(rsExport[iCnt]); 
//													vATS.set13(rsExport[iCnt]); 
//													vATS.set16(rsExport[iCnt]); 
//													vATS.set18(rsExport[iCnt]); 
//													vATS.set19(rsExport[iCnt]); 
//													vATS.msgSPL();
//													vATS.getSPL();
//													
//													String dataPDF = vATS.pspl+"\n";
//													xls(); 
//    										    	if (vATS.type7a.isEmpty()) vATS.type7a=" ";
//    										    	if (vATS.type13a.isEmpty()) vATS.type13a=" ";
//    										    	if (vATS.type13b.isEmpty()) vATS.type13b=" ";
//    										    	if (vATS.type16a.isEmpty()) vATS.type16a=" ";
//    												String dataXLS = vATS.type7a+";"+vATS.type13a+";"+vATS.type13b+";"+vATS.type16a+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//
//												else if (sID.compareTo(Titles.stNOTAM)==0 || sID.compareTo(Titles.stRQNTM)==0 ||
//														sID.compareTo(Titles.stCHKNTM)==0 || sID.compareTo(Titles.stACTNTM)==0 ||
//														sID.compareTo(Titles.stEXPNTM)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vNOTAM.setNOTAM(rsExport[iCnt]);
//													
//													String dataPDF = vNOTAM.pnotam+"\n";
//    										    	String dataXLS = "";
//    										    	
//    										    	if (sID.compareTo(Titles.stNOTAM)==0) {
//    										    		xls(); 
//        										    	if (vATS.originator.isEmpty()) vATS.originator=" ";
//        										    	if (vNOTAM.sNotamNo.isEmpty()) vNOTAM.sNotamNo=" ";
//        										    	if (vNOTAM.identifiers.isEmpty()) vNOTAM.identifiers=" ";
//        										    	if (vNOTAM.sRefNotamNo.isEmpty()) vNOTAM.sRefNotamNo=" ";
//        										    	if (vNOTAM.sNotamCode.isEmpty()) vNOTAM.sNotamCode=" ";
//        										    	if (vNOTAM.purpose.isEmpty()) vNOTAM.purpose=" ";
//        										    	if (vNOTAM.scope.isEmpty()) vNOTAM.scope=" ";
//        										    	if (vNOTAM.A.isEmpty()) vNOTAM.A=" ";
//        										    	if (vNOTAM.sStatusNotam.isEmpty()) vNOTAM.sStatusNotam=" ";
//        												
//        										    	dataXLS+=vATS.originator+";"+vNOTAM.sNotamNo+";"+vNOTAM.identifiers+";"+
//    										    		vNOTAM.sRefNotamNo+";"+vNOTAM.sNotamCode+";"+vNOTAM.purpose+";"+vNOTAM.scope+";"+
//    										    		vNOTAM.A+";"+vNOTAM.sStatusNotam+";"; }
//												
//    										    	else if (sID.compareTo(Titles.stRQNTM)==0) {
//														xls(); 
//        										    	if (vATS.originator.isEmpty()) vATS.originator=" ";
//        										    	if (vNOTAM.sNotamNo.isEmpty()) vNOTAM.sNotamNo=" ";
//        										    	if (vNOTAM.identifiers.isEmpty()) vNOTAM.identifiers=" ";
//        										    	if (vNOTAM.sRefNotamNo.isEmpty()) vNOTAM.sRefNotamNo=" ";
//        										    	if (vNOTAM.A.isEmpty()) vNOTAM.A=" ";
//        										    	if (vNOTAM.sStatusNotam.isEmpty()) vNOTAM.sStatusNotam=" ";
//        												
//        										    	dataXLS+=vATS.originator+";"+vNOTAM.sNotamNo+";"+vNOTAM.identifiers+";"+
//														vNOTAM.sRefNotamNo+";"+vNOTAM.A+";"+vNOTAM.sStatusRqntm+";"; }
//													
//    										    	else if (sID.compareTo(Titles.stCHKNTM)==0 || sID.compareTo(Titles.stACTNTM)==0 ||
//															sID.compareTo(Titles.stEXPNTM)==0) {
//														xls(); 
//        										    	if (vATS.originator.isEmpty()) vATS.originator=" ";
//        										    	if (vNOTAM.sNotamNo.isEmpty()) vNOTAM.sNotamNo=" ";
//        										    	if (vNOTAM.identifiers.isEmpty()) vNOTAM.identifiers=" ";
//        										    	if (vNOTAM.sRefNotamNo.isEmpty()) vNOTAM.sRefNotamNo=" ";
//        										    	if (vNOTAM.sNotamCode.isEmpty()) vNOTAM.sNotamCode=" ";
//        										    	if (vNOTAM.purpose.isEmpty()) vNOTAM.purpose=" ";
//        										    	if (vNOTAM.scope.isEmpty()) vNOTAM.scope=" ";
//        										    	if (vNOTAM.A.isEmpty()) vNOTAM.A=" ";
//        												
//														dataXLS+=vATS.originator+";"+vNOTAM.sNotamNo+";"+vNOTAM.identifiers+";"+
//														vNOTAM.sRefNotamNo+";"+vNOTAM.sNotamCode+";"+vNOTAM.purpose+";"+vNOTAM.scope+";"+
//														vNOTAM.A+";"; }
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//												
//												else if (sID.compareTo(Titles.stSNOWTAM)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vNOTAM.setSNOWTAM(rsExport[iCnt]);
//													
//													String dataPDF = vNOTAM.psnowtam+"\n";
//													xls(); 
//    										    	if (vNOTAM.state.isEmpty()) vNOTAM.state=" ";
//    										    	if (vNOTAM.sn1.isEmpty()) vNOTAM.sn1=" ";
//    										    	if (vNOTAM.locind.isEmpty()) vNOTAM.locind=" ";
//    										    	if (vNOTAM.timeobs.isEmpty()) vNOTAM.timeobs=" ";
//    										    	if (vNOTAM.optgrp.isEmpty()) vNOTAM.optgrp=" ";
//    										    	if (vNOTAM.sn2.isEmpty()) vNOTAM.sn2=" ";
//    												String dataXLS = vNOTAM.state+";"+vNOTAM.sn1+";"+vNOTAM.locind+";"+vNOTAM.timeobs+";"+
//    										    	vNOTAM.optgrp+";"+vNOTAM.sn2+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//												
//												else if (sID.compareTo(Titles.stASHTAM)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vNOTAM.setASHTAM(rsExport[iCnt]);
//													
//													String dataPDF = vNOTAM.pashtam+"\n";
//													xls(); 
//    										    	if (vNOTAM.state.isEmpty()) vNOTAM.state=" ";
//    										    	if (vNOTAM.sn1.isEmpty()) vNOTAM.sn1=" ";
//    										    	if (vNOTAM.locind.isEmpty()) vNOTAM.locind=" ";
//    										    	if (vNOTAM.issued.isEmpty()) vNOTAM.issued=" ";
//    										    	if (vNOTAM.optgrp.isEmpty()) vNOTAM.optgrp=" ";
//    										    	if (vNOTAM.sn2.isEmpty()) vNOTAM.sn2=" ";
//    												String dataXLS = vNOTAM.state+";"+vNOTAM.sn1+";"+vNOTAM.locind+";"+vNOTAM.issued+";"+
//    										    	vNOTAM.optgrp+";"+vNOTAM.sn2+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//												
//												else if (sID.compareTo(Titles.stBIRDTAM)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vNOTAM.setBIRDTAM(rsExport[iCnt]);
//													
//													String dataPDF = vNOTAM.pbirdtam+"\n";
//													xls(); 
//    										    	if (vNOTAM.birdnum.isEmpty()) vNOTAM.birdnum=" ";
//    										    	if (vNOTAM.efftime.isEmpty()) vNOTAM.efftime=" ";
//    										    	if (vNOTAM.exptime.isEmpty()) vNOTAM.exptime=" ";
//    										    	if (vNOTAM.intlev.isEmpty()) vNOTAM.intlev=" ";
//    												String dataXLS = vNOTAM.birdnum+";"+vNOTAM.efftime+";"+vNOTAM.exptime+";"+vNOTAM.intlev+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//												
//												else if (sID.compareTo(Titles.stRQN)==0 || sID.compareTo(Titles.stRQL)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vNOTAM.setRQN(rsExport[iCnt]);
//													
//													String dataPDF = vNOTAM.prqn+"\n";
//													xls(); 
//    										    	if (vNOTAM.reqid.isEmpty()) vNOTAM.reqid=" ";
//    										    	if (vNOTAM.locind.isEmpty()) vNOTAM.locind=" ";
//    										    	if (vNOTAM.reqmsg.isEmpty()) vNOTAM.reqmsg=" "; //else if (vNOTAM.reqmsg.contains("\n")) vNOTAM.reqmsg= vNOTAM.reqmsg.replaceAll("\n", "~");
//    										    	
//    												String dataXLS = vNOTAM.reqid+";"+vNOTAM.locind+";"+vNOTAM.reqmsg+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//												
//												else if (sID.compareTo(Titles.stMETAR)==0 || sID.compareTo(Titles.stSPECI)==0) {
//													vATS.setHeader(rsExport[iCnt]);
//													vMETEO.setMETEO(rsExport[iCnt], sID); 
//													
//													String dataPDF = vMETEO.pmetar+"\n";
//													xls(); 
//    										    	if (vMETEO.origin.isEmpty()) vMETEO.origin=" ";
//    										    	if (vMETEO.msgID.isEmpty()) vMETEO.msgID=" ";
//    										    	if (vMETEO.location.isEmpty()) vMETEO.location=" ";
//    										    	if (vMETEO.timeobs.isEmpty()) vMETEO.timeobs=" ";
//    										    	if (vMETEO.issued.isEmpty()) vMETEO.issued=" ";
//    										    	if (vMETEO.option.isEmpty()) vMETEO.option=" ";
//    										    	if (vMETEO.text.isEmpty()) vMETEO.text=" "; //else if (vMETEO.text.contains("\n")) vMETEO.text= vMETEO.text.replaceAll("\n", "~");
//    												String dataXLS = vMETEO.origin+";"+vMETEO.msgID+";"+vMETEO.location+";"+vMETEO.timeobs+";"+
//													vMETEO.issued+";"+vMETEO.option+";"+vMETEO.text+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//
//												else if (sID.compareTo(Titles.stSIGMET)==0 || sID.compareTo(Titles.stAIRMET)==0 ||
//														sID.compareTo(Titles.stWINSWAR)==0) { 
//													vATS.setHeader(rsExport[iCnt]);
//													vMETEO.setMETEO(rsExport[iCnt], sID); 
//													
//													String dataPDF = vMETEO.psigmet+"\n";
//													xls(); 
//    										    	if (vMETEO.origin.isEmpty()) vMETEO.origin=" ";
//    										    	if (vMETEO.msgID.isEmpty()) vMETEO.msgID=" ";
//    										    	if (vMETEO.location.isEmpty()) vMETEO.location=" ";
//    										    	if (vMETEO.issued.isEmpty()) vMETEO.issued=" ";
//    										    	if (vMETEO.option.isEmpty()) vMETEO.option=" ";
//    										    	if (vMETEO.text.isEmpty()) vMETEO.text=" "; //else if (vMETEO.text.contains("\n")) vMETEO.text= vMETEO.text.replaceAll("\n", "~");
//    												String dataXLS = vMETEO.origin+";"+vMETEO.msgID+";"+vMETEO.location+";"+vMETEO.issued+";"+
//													vMETEO.option+";"+vMETEO.text+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//												
//												else if (sID.compareTo(Titles.stAIREP)==0 || sID.compareTo(Titles.stTAFOR)==0 ||
//														sID.compareTo(Titles.stARFOR)==0 || sID.compareTo(Titles.stROFOR)==0 ||
//														sID.compareTo(Titles.stWINTEM)==0 || sID.compareTo(Titles.stADV)==0 ||
//														sID.compareTo(Titles.stSYNOP)==0 || sID.compareTo(Titles.stWARSYN)==0) { 
//													vATS.setHeader(rsExport[iCnt]);
//													vMETEO.setMETEO(rsExport[iCnt], sID); 
//													
//													String dataPDF = vMETEO.pairep+"\n";
//													xls(); 
//    										    	if (vMETEO.origin.isEmpty()) vMETEO.origin=" ";
//    										    	if (vMETEO.msgID.isEmpty()) vMETEO.msgID=" ";
//    										    	if (vMETEO.issued.isEmpty()) vMETEO.issued=" ";
//    										    	if (vMETEO.option.isEmpty()) vMETEO.option=" ";
//    										    	if (vMETEO.text.isEmpty()) vMETEO.text=" "; //else if (vMETEO.text.contains("\n")) vMETEO.text= vMETEO.text.replaceAll("\n", "~");
//    												String dataXLS = vMETEO.origin+";"+vMETEO.msgID+";"+vMETEO.issued+";"+vMETEO.option+";"+
//    										    	vMETEO.text+";";
//    										    	
//    										    	if (dataPDF.endsWith("\n")) {
//    										    		int index = dataPDF.lastIndexOf("\n");
//    										    		dataPDF=dataPDF.substring(0, index);
//    										    	}
//    										    	if (dataXLS.endsWith("\n")) {
//    										    		int index = dataXLS.lastIndexOf("\n");
//    										    		dataXLS=dataXLS.substring(0, index);
//    										    	}
//    										    	
//    										    	printable+=dataPDF+"\n";
//    										    	msg+=dataXLS+";";
//    										    	printableXLS+=msg+"#"; }//printableXLS+=msg+"\n"; } 
//											
//												progbr.bar.setSelection(rowNo);
//											} //end of while
//										} catch (SQLException s) { // biar kalo ada table yang doesn't exist ga mati.
//											//System.out.println("**Info:" + s.getMessage() + "#");
//										}
//										iCnt++; //counter rs
//									} //end of for3
//								} //end of for2
//							} //end of for1
//							progbr.finish();
//
//							//STEP 4: Write to file
//							String iJmlDataExport="";
//							if (iJmlData<=iMaxExport) iJmlDataExport=Integer.toString(iJmlData);
//							else if (iJmlData>iMaxExport) iJmlDataExport=Integer.toString(iMaxExport);
//							//PDF
//							if (fileType.compareToIgnoreCase("pdf")==0) {
//								if (strTrace.compareToIgnoreCase("trace")==0) {
//									new WriteToPDF().setPrint(printable, iJmlDataExport, getFlnm(sID), strTrace); 
//									printable=""; 
//								} else if (strTrace.compareToIgnoreCase("notrace")==0) {
//									new WriteToPDF().setPrint(printableRet, iJmlDataExport, getFlnm(sID), strTrace); 
//									printableRet=""; 
//								} else {
//									new WriteToPDF().setPrint(printable, iJmlDataExport, getFlnm(sID), ""); 
//									printable="";
//								}
////								new WriteToPDF().setPrint(printable, iJmlDataExport, getFlnm(sID)); 
////								printable="";
//							}
//							//WORD
//							else if (fileType.compareToIgnoreCase("docx")==0) {
//								//Reject Message
//								if (sID.compareTo(Titles.stReject)==0) {
//									if (printable.startsWith("data=")) { printable=printable.replace("data=","Error Message: "); }
//									if (printable.contains(";#")) { printable=printable.replaceAll(";#","\n"); }  
//									if (printable.contains(";trace=")) { printable=printable.replaceAll(";trace=","\nMessage: "); }
//								}
//								
//								//Incoming Message
//								if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
//									if (strTrace.compareToIgnoreCase("trace")==0) {
//										if (printable.startsWith("data=")) { printable=printable.replace("data=","Data:\n"); }
//										if (printable.contains(";#")) { printable=printable.replaceAll(";#","\n\n"); }  
//										if (printable.contains(";trace=")) { printable=printable.replaceAll(";trace=","\nTrace:\n"); }	
//									} else if (strTrace.compareToIgnoreCase("notrace")==0) {
//										if (printableRet.startsWith("data=")) { printableRet=printableRet.replace("data=",""); }
//										if (printableRet.contains(";#")) { printableRet=printableRet.replaceAll(";#","\n\n"); }  
//										printable=printableRet;
//									}
//									
//								}
//								new WriteToDOC(getFlnm(sID), printable + iJmlDataExport + " row(s) in this file");
//								printable=""; }
//							//EXCEL
//							else if (fileType.compareToIgnoreCase("xls")==0) {
//								if (strTrace.compareToIgnoreCase("trace")==0) {
//									//new WriteToXLS().setData(sID+" List", "msg"+getFlnm(sID), printableXLS, iJmlDataExport, getFlnm(sID), strTrace);
//									new WriteToXLS2(sID+" List", "msg"+getFlnm(sID), printableXLS, iJmlDataExport, getFlnm(sID), strTrace);
//									printableXLS=""; 
//								} else if (strTrace.compareToIgnoreCase("notrace")==0) {
//									//new WriteToXLS().setData(sID+" List", "msg"+getFlnm(sID), printableXLSRet, iJmlDataExport, getFlnm(sID), strTrace);
//									new WriteToXLS2(sID+" List", "msg"+getFlnm(sID), printableXLSRet, iJmlDataExport, getFlnm(sID), strTrace);
//									printableXLSRet=""; 
//								} else {
//									//new WriteToXLS().setData(sID+" List", "msg"+getFlnm(sID), printableXLS, iJmlDataExport, getFlnm(sID), ""); 
//									new WriteToXLS2(sID+" List", "msg"+getFlnm(sID), printableXLS, iJmlDataExport, getFlnm(sID), "");
//									printableXLS="";
//								}
//								
////								new WriteToXLS().setData(sID+" List", "msg"+getFlnm(sID), printableXLS, iJmlDataExport, getFlnm(sID));
////								printableXLS=""; 
//							}
//							
//						} catch (SQLException s) {
//							DialogFactory.openInfo(s.toString());
//							s.printStackTrace();
//							progbr.finish();
//						} catch (java.lang.OutOfMemoryError hs) {
//							DialogFactory.openInfo(DialogFactory.LIST_INFO_OUT_OF_MEMORY);
//							progbr.finish();
//						}
//					}
//				});
//			}
//		}.start();
//		progbr.begin();
//	
//		//STEP 5: Clean-up environment
//	    stmtExport.close();
//	    connExport.close();
//    
//	} catch(SQLException se){
//		//Handle errors for JDBC
//		se.printStackTrace();
//	} catch(Exception xe){
//		//Handle errors for Class.forName
//		xe.printStackTrace();
//	}
//}