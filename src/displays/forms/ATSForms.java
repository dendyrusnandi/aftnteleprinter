package displays.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import readwrite.ReadFromFile;
import setting.ATSComponentSetting;
import setting.ButtonsSetting;
import setting.Colors;
import setting.Combos;
import setting.ErrMsg;
import setting.Images;
import setting.Shells;
import setting.Shorten;
import setting.TextSetting;
import setting.Times;
import setting.Title;
import actions.ATSAuto;
import actions.RefreshTable;
import actions.SendSave;
import actions.ViewATSFunction;
import databases.Auto;
import databases.jdbc;
import displays.DialogFactory;
import displays.MainForm;
import displays.TeleSplashScreen2016IP;
import displays.tables.TableReg;


public class ATSForms {
	
	static int iSeparatorFB = MainForm.getWidthRightTop();
	static int iSeparator=MainForm.getWidthRightTop()-300;
	static Shell shl;
	static Text t9b,t9c,t10a,t13a,t16a,t15c,tDest1,tDest2,tDest3,tDest4,tDest5,tDest6,tDest7,tDest8,tDest9,tDest10,tDest11,tDest12,tDest13,
	tDest14,tDest15,tDest16,tDest17,tDest18,tDest19,tDest20,tDest21;
	
	static ButtonsSetting bs = new ButtonsSetting();
	static ATSAuto atsAuto = new ATSAuto();
	static ViewATSFunction vATS = new ViewATSFunction();
//	static ReadFromFile rff = new ReadFromFile();
	static SendSave ss = new SendSave();
	static Shorten sh = new Shorten();
	static Times time = new Times();
	static TextSetting ts = new TextSetting();
	static ATSComponentSetting acs = new ATSComponentSetting();
	static ErrMsg em = new ErrMsg();
	
	static String lbl3a = "3.MESSAGE\nTYPE";
	static String lbl3b = "\nNUMBER";
	static String lbl3c = "\nREFERENCE DATA";
	static String lbl5a = "5.PHASE OF\nEMERGENCY";
	static String lbl5b = "ORIGINATOR\nOF MESSAGE";
	static String lbl5c = "\nNATURE OF EMERGENCY";
	static String lbl7a = "7.AIRCRAFT\nID";
	static String lbl7b = "SSR\nMODE";
	static String lbl7c = "\nCODE";
	static String lbl8a = "8.FLIGHT\nRULES";
	static String lbl8b = "TYPE OF\nFLIGHT";
	static String lbl9a = "\n9.NUMBER";
	static String lbl9b = "TYPE OF\nAIRCRAFT";
	static String lbl9c = "WAKE TURB.\nCATEGORY";
	static String lbl10 = "\n10.EQUIPMENT AND CAPABILITIES";
	static String lbl13a = "13.DEP\nAD";
	static String lbl13b = "\nTIME";
	static String lbl14a = "14.BOUNDARY\nPOINT";
	static String lbl14b = "TIME\nBOUNDARY";
	static String lbl14c = "CLEARED\nLEVEL";
	static String lbl14d = "SPL CROSSING\nDATA";
	static String lbl14e = "CROSSING\nCONDITION";
	static String lbl15a = "15.CRUISING\nSPEED";
	static String lbl15b = "CRUISING\nLEVEL";
	static String lbl15c = "\nROUTE";
	static String lbl16a = "16.DEST\nAD";
	static String lbl16b = "TOTAL\nEET";
	static String lbl16c = "DEST\nALTN AD";
	static String lbl16d = "2ND DEST\nALTN AD";
	static String lbl17a = "17.ARR AD";
	static String lbl17b = "TIME";
	static String lbl17c = "ARRIVAL AERODROME";
	static String lbl18 = "18.OTHER INFORMATION";
	static String lbl18dof = "\n18.DOF/";
	static String lblSUPINFO = "SUPPLEMENTARY INFORMATION ( NOT TO BE TRANSMITTED IN FPL MESSAGES )";
	static String lbl19 = "19.ENDURANCE";
	static String lbl19a = "HR/MIN";
	static String lbl19b = "PERSON ON BOARD";
	static String lbl19c = "EMERGENCY RADIO";
	static String lbl19d = "SURVIVAL EQUIPMENT";
	static String lbl19e = "JACKETS";
	static String lbl19fnum = "NUMBER";
	static String lbl19fcap = "CAPACITY";
	static String lbl19fcov = "COVER";
	static String lbl19fcol = "COLOUR";
	static String lbl19g = "AIRCRAFT COLOUR AND MARKINGS";
	static String lbl19h = "REMARKS";
	static String lbl19i = "PILOT IN COMMAND";
	static String lbl20 = "20.ALERTING SEARCH AND RESCUE INFORMATION";
	static String lbl21 = "21.RADIO FAILURE INFORMATION";
	static String lbl22 = "22.AMENDMENT";
	static String lblspace = "SPACE RESERVED";
	static String lblfree = "FREE TEXT";
	static String lblFB = "FILLED BY ";
	
	public static Button getFplDLA,getFplCHG,getFplCNL,getFplDEP,getFplARR,getFplCDN,getFplEST,getFplACP,getFplRQS,getFplSPL,
	getRouteALR,getRouteFPL,getRouteCPL,getRouteRPL,nextALR,prevALR,firstALR,lastALR,nextFPL,prevFPL,firstFPL,lastFPL,
	nextCPL,prevCPL,firstCPL,lastCPL,nextDLA,prevDLA,firstDLA,lastDLA,nextCHG,prevCHG,firstCHG,lastCHG,nextCNL,prevCNL,firstCNL,lastCNL,
	nextDEP,prevDEP,firstDEP,lastDEP,nextARR,prevARR,firstARR,lastARR,nextCDN,prevCDN,firstCDN,lastCDN,nextEST,prevEST,firstEST,lastEST,
	nextACP,prevACP,firstACP,lastACP,nextRQS,prevRQS,firstRQS,lastRQS,nextSPL,prevSPL,firstSPL,lastSPL;

	//------------------------------ Component for ATS Message template ------------------------------ 
	public static Button 
	//SSR MODE
	t7bALR,t7bRCF,t7bFPL,t7bDLA,t7bCHG,t7bCNL,t7bDEP,t7bARR,t7bCDN,t7bCPL,t7bEST,t7bACP,t7bRQP,t7bRQS,t7bSPL,
	//button mini list & button save
	b5aALR,b8aALR,b8bALR,b9cALR,bSave9ALR,b10aALR,b10bALR,btnChkALR,bSave15ALR,bSTSALR,bPBNALR,
	b8aFPL,b8bFPL,b9cFPL,bSave9FPL,b10aFPL,b10bFPL,btnChkFPL,bSave15FPL,bSTSFPL,bPBNFPL,bSaveRegFPL,
	b8aCPL,b8bCPL,b9cCPL,bSave9CPL,b10aCPL,b10bCPL,btnChkCPL,bSave15CPL,bSTSCPL,bPBNCPL,
	bSTSSPL,bPBNSPL,
	//button time
	bTimeDEP,bTimeARR;
	
	// FREE TEXT
	public static Text tFbAMO,tFbTEXT,tFbAFTN,tFbALR,tFbRCF,tFbFPL,tFbDLA,tFbCHG,tFbCNL,tFbDEP,tFbARR,tFbCDN,
	tFbCPL,tFbEST,tFbACP,tFbLAM,tFbRQP,tFbRQS,tFbSPL,
	tFreeAMO,tFreeTEXT,tFreeATS,
	// ALR
	t3aALR,t3bALR,t3cALR,t5aALR,t5bALR,t5cALR,t7aALR,t7cALR,t8aALR,t8bALR,t9aALR,t9bALR,t9cALR,t10aALR,t10bALR,t13aALR,t13bALR,
	t15aALR,t15bALR,t15cALR,t16aALR,t16bALR,t16cALR,t16dALR,tStsALR,tPbnALR,tNavALR,tComALR,tDatALR,tSurALR,tDepALR,tDestALR,tDofALR,tRegALR,
	tEetALR,tSelALR,tTypALR,tCodeALR,tDleALR,tOprALR,tOrgnALR,tPerALR,tAltnALR,tRaltALR,tTaltALR,tRifALR,tRmkALR,t19aALR,t19bALR,t19NumALR,
	t19CapALR,t19ColALR,t19AirALR,t19RemALR,t19PilALR,t20ALR,
	t19cUhfALR,t19cVhfALR,t19cEltALR,
	t19dPALR,t19dDALR,t19dMALR,t19dJALR,
	t19eLALR,t19eFALR,t19eUALR,t19eVALR,t19sALR,t19jALR,t19dALR,t19cALR,t19nALR,
	// RCF
	t3aRCF,t3bRCF,t3cRCF,t7aRCF,t7cRCF,t21RCF,
	// FPL
	t3aFPL,t3bFPL,t3cFPL,t7aFPL,t7cFPL,t8aFPL,t8bFPL,t9aFPL,t9bFPL,t9cFPL,t10aFPL,t10bFPL,t13aFPL,t13bFPL,t15aFPL,t15bFPL,t15cFPL,
	t16aFPL,t16bFPL,t16cFPL,t16dFPL,tStsFPL,tPbnFPL,tNavFPL,tComFPL,tDatFPL,tSurFPL,tDepFPL,tDestFPL,tDofFPL,tRegFPL,tEetFPL,tSelFPL,tTypFPL,
	tCodeFPL,tDleFPL,tOprFPL,tOrgnFPL,tPerFPL,tAltnFPL,tRaltFPL,tTaltFPL,tRifFPL,tRmkFPL,t19aFPL,t19bFPL,t19NumFPL,t19CapFPL,t19ColFPL,
	t19AirFPL,t19RemFPL,t19PilFPL,tSpace,
	t19cUhfFPL,t19cVhfFPL,t19cEltFPL,
	t19dPFPL,t19dDFPL,t19dMFPL,t19dJFPL,
	t19eLFPL,t19eFFPL,t19eUFPL,t19eVFPL,t19sFPL,t19jFPL,t19dFPL,t19cFPL,t19nFPL,
	// DLA
	t3aDLA,t3bDLA,t3cDLA,t7aDLA,t7cDLA,t13aDLA,t13bDLA,t16aDLA,t16bDLA,t16cDLA,t16dDLA,tDofDLA,
	// CHG
	t3aCHG,t3bCHG,t3cCHG,t7aCHG,t7cCHG,t13aCHG,t13bCHG,t16aCHG,t16bCHG,t16cCHG,t16dCHG,tDofCHG,t22CHG,
	// CNL
	t3aCNL,t3bCNL,t3cCNL,t7aCNL,t7cCNL,t13aCNL,t13bCNL,t16aCNL,t16bCNL,t16cCNL,t16dCNL,tDofCNL,
	// DEP
	t3aDEP,t3bDEP,t3cDEP,t7aDEP,t7cDEP,t13aDEP,t13bDEP,t16aDEP,t16bDEP,t16cDEP,t16dDEP,tDofDEP,
	// ARR
	t3aARR,t3bARR,t3cARR,t7aARR,t7cARR,t13aARR,t13bARR,t17aARR,t17bARR,t17cARR,
	// CDN
	t3aCDN,t3bCDN,t3cCDN,t7aCDN,t7cCDN,t13aCDN,t13bCDN,t16aCDN,t16bCDN,t16cCDN,t16dCDN,t22CDN,
	// CPL
	t3aCPL,t3bCPL,t3cCPL,t7aCPL,t7cCPL,t8aCPL,t8bCPL,t9aCPL,t9bCPL,t9cCPL,t10aCPL,t10bCPL,t13aCPL,t13bCPL,t14aCPL,t14bCPL,t14cCPL,t14dCPL,
	t14eCPL,t15aCPL,t15bCPL,t15cCPL,t16aCPL,t16bCPL,t16cCPL,t16dCPL,tStsCPL,tPbnCPL,tNavCPL,tComCPL,tDatCPL,tSurCPL,tDepCPL,tDestCPL,
	tDofCPL,tRegCPL,tEetCPL,tSelCPL,tTypCPL,tCodeCPL,tDleCPL,tOprCPL,tOrgnCPL,tPerCPL,tAltnCPL,tRaltCPL,tTaltCPL,tRifCPL,tRmkCPL,
	// EST
	t3aEST,t3bEST,t3cEST,t7aEST,t7cEST,t13aEST,t13bEST,t14aEST,t14bEST,t14cEST,t14dEST,t14eEST,t16aEST,t16bEST,t16cEST,t16dEST,
	// ACP
	t3aACP,t3bACP,t3cACP,t7aACP,t7cACP,t13aACP,t13bACP,t16aACP,t16bACP,t16cACP,t16dACP,
	// LAM
	t3aLAM,t3bLAM,t3cLAM,
	// RQP
	t3aRQP,t3bRQP,t3cRQP,t7aRQP,t7cRQP,t13aRQP,t13bRQP,t16aRQP,t16bRQP,t16cRQP,t16dRQP,tDofRQP,
	// RQS
	t3aRQS,t3bRQS,t3cRQS,t7aRQS,t7cRQS,t13aRQS,t13bRQS,t16aRQS,t16bRQS,t16cRQS,t16dRQS,tDofRQS,
	// SPL
	t3aSPL,t3bSPL,t3cSPL,t7aSPL,t7cSPL,t13aSPL,t13bSPL,t16aSPL,t16bSPL,t16cSPL,t16dSPL,tStsSPL,tPbnSPL,tNavSPL,tComSPL,
	tDatSPL,tSurSPL,tDepSPL,tDestSPL,tDofSPL,tRegSPL,tEetSPL,tSelSPL,tTypSPL,tCodeSPL,tDleSPL,tOprSPL,tOrgnSPL,tPerSPL,tAltnSPL,tRaltSPL,
	tTaltSPL,tRifSPL,tRmkSPL,t19aSPL,t19bSPL,t19NumSPL,t19CapSPL,t19ColSPL,t19AirSPL,t19RemSPL,t19PilSPL,
	t19cUhfSPL,t19cVhfSPL,t19cEltSPL,
	t19dPSPL,t19dDSPL,t19dMSPL,t19dJSPL,
	t19eLSPL,t19eFSPL,t19eUSPL,t19eVSPL,t19sSPL,t19jSPL,t19dSPL,t19cSPL,t19nSPL;

	/**
	 * ----------------------------------------------------------------------------------------------------------------------------	
	 * Variabel-variabel berikut ini merupakan variabel masing-masing type yang digunakan untuk: 
	 * 1. mengambil isi di setiap text sesuai type-nya -> get___Value() [ ___ -> msgType: ALR,FPL, dst.]
	 * 2. dipakai di send/store sesuai type-nya -> get__() [ __ -> textType: 3a,7a, dst.]
	 * ----------------------------------------------------------------------------------------------------------------------------
	 */
	//digunakan ditiap template
	static String filed_by="",
	type3a="",type3b="",type3c="",type3abc="",type5a="",type5b="",type5c="",type7aARR="",type7a="",type7b="",t7bValue="",type7c="",
	type8a="",type8b="",type9a="",type9b="",type9c="",type10a="",type10b="",type10bBr="",type13a="",type13b="",
	type14a="",type14b="",type14c="",type14d="",type14e="",type15a="",type15b="",type15c="",
	type16a="",type16b="",type16c="",type16c2nd="",type17a="",type17b="",type17c="",
	type18="",type18a="",type18b="",type18tNav="",type18tCom="",type18tDat="",type18tSur="",type18tDep="",type18tDest="",
	type18tDof="",type18tReg="",type18tEet="",type18tSel="",type18tTyp="",type18tCode="",type18tDle="",type18tOpr="",type18tOrgn="",
	type18tPer="",type18tAltn="",type18tRalt="",type18tTalt="",type18tRif="",type18tRmk="",type18tPbn="",type18tSts="",
	type19a="",type19b="",type19cUHF="",type19cVHF="",type19cELT="",type19dS="",type19dP="",type19dD="",type19dM="",type19dJ="",
	type19eJ="",type19eL="",type19eF="",type19eU="",type19eV="",type19fD="",type19f_number="",type19f_capacity="",type19f_cover="",
	type19f_colour="",type19i="",type19g="",type19hN="",type19Remarks="",UHFButtonValue="",VHFButtonValue="",ELTButtonValue="",
	cUHF="",cVHF="",cELT="",R="",S="",J="",type19="",dP="",dD="",dM="",dJ="",eL="",eF="",eU="",eV="",
	type20="",type21="",type22="",manual_ats="",space_res="";
	/**
	 * ----------------------------------------------------------------------------------------------------------------------------	
	 * Variabel-variabel berikut ini merupakan variabel yang digunakan untuk: 
	 * mengambil isi FPL pada saat create CHG,DLA,CNL,ARR,DEP dari template FPL
	 * ----------------------------------------------------------------------------------------------------------------------------
	 */
	static String s3b="",s3c="",s7a="",s7b="",s7c="",s8a="",s8b="",s9a="",s9b="",s9c="",s10a="",s10b="",s10bLama="",s10bBaru="",s13a="",s13b="",
		s15a="",s15b="",s15c="",s16a="",s16b="",s16c="",s16d="",s18aLama="",s18aBaru="",s18bLama="",s18bBaru="",s18a="",s18b="",s18c="",
		s18Baru="",sNav="",sCom="",sDat="",sSur="",sDep="",sDest="",/*tDof="",tReg="",*/sEet="",sSel="",sTyp="",sCode="",
		sDle="",sOpr="",sOrgn="",sPer="",sAltn="",sRalt="",sTalt="",sRif="",sRmk="",sPbn="",sSts="",
		s19a="",s19b="",s19cUHF="",s19cVHF="",s19cELT="",s19dP="",s19dD="",s19dM="",s19dJ="",
    	s19eL="",s19eF="",s19eU="",s19eV="",s19Num="",s19Cap="",s19Cov="",s19Col="",s19Air="",s19Rem="",s19Pil="";

	static Cursor cs1 = new Cursor(Display.getCurrent(), SWT.CURSOR_ARROW);

	public ATSForms() {
		
	}
	
	static void bGetRoute(Button getRoute, final Button first, final Button prev, final Button next, final Button last, final Text t13a, final Text t16a, final String ID) {
		sh.buttonStyle(getRoute, "", "Get Route from database (Item 13a and/or Item 16a) ", bs.widthNavBtn, bs.colorBtn, SWT.TOP, SWT.LEFT, Images.img_route);
		sh.buttonStyle(first, "", "Go to the first page", bs.widthNavBtn, bs.colorBtn, SWT.TOP, SWT.LEFT, Images.img_first);
		sh.buttonStyle(prev, "", "Go to the previous page", bs.widthNavBtn, bs.colorBtn, SWT.TOP, SWT.LEFT, Images.img_previous);
		sh.buttonStyle(next, "", "Go to the next page", bs.widthNavBtn, bs.colorBtn, SWT.TOP, SWT.LEFT, Images.img_next);
		sh.buttonStyle(last, "", "Go to the last page", bs.widthNavBtn, bs.colorBtn, SWT.TOP, SWT.LEFT, Images.img_last);
		
		first.setEnabled(false);
		prev.setEnabled(false);
		next.setEnabled(false);
		last.setEnabled(false);
		
		getRoute.setEnabled(true);
		getRoute.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				Shell sh = null;
				if (ID.endsWith("ALR")) sh = Shells.sh_nALR;
				else if (ID.endsWith("FPL")) sh = Shells.sh_nFPL;
				else if (ID.endsWith("CPL")) sh = Shells.sh_nCPL;
				
				if ((t13a.getText().equals("")) && (t16a.getText().equals(""))) {
					DialogFactory.openInfoDialog(sh, "Get Route", "At least Field DEP AD or DEST AD should be filled !!");
					t13a.setFocus();
				} else {
					String select = "SELECT * FROM route WHERE type13a LIKE '"+t13a.getText()+"%' AND type16a LIKE '"+t16a.getText()+"%' ORDER BY id_number DESC";
					Auto.koneksiDB_ROUTE(ID, select);

					if (Auto.getJumlah() == 0) {
						DialogFactory.openInfoDialog(sh, "Get Route", "Route not found, please check the value in field DEP AD or DEST AD !!");
					} else if (Auto.getJumlah() == 1) {
						first.setEnabled(false);
						prev.setEnabled(false);
						next.setEnabled(false);
						last.setEnabled(false);
					} else {
						first.setEnabled(false);
						prev.setEnabled(false);
						next.setEnabled(true);
						last.setEnabled(true);
					}
				}
			}
		});
		
		first.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (first.getEnabled() == true) {
					first.setEnabled(false);
					prev.setEnabled(false);
					next.setEnabled(true);

					if (last.getEnabled() == false) {
						last.setEnabled(true);
					}
				}
				Auto.qq=0;
				Auto.isiHal = Integer.toString(Auto.qq);
				Auto.Next_ROUTE(ID);
			}
		});

		prev.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (prev.getEnabled() == true) {
					next.setEnabled(true);
					first.setEnabled(true);
					last.setEnabled(true);
				}

				Auto.qq--;
				if (Auto.qq == 0) {
					prev.setEnabled(false);
					first.setEnabled(false);
				}

				if (Auto.qq < 0)
					Auto.qq=0;
				else {
					int as = Integer.parseInt(Auto.akhir().trim()) - 1;
					Auto.isiHal = Integer.toString(as);
					Auto.Next_ROUTE(ID);
				}
			}
		});

		next.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				prev.setEnabled(true);
				first.setEnabled(true);

				Auto.qq++;
				int jml;
				if (Auto.jumlah % Auto.baris == 0) {
					jml = Auto.jumlah / Auto.baris;
				} else {
					jml = Auto.jumlah / Auto.baris + 1;
				}

				int all = jml;
				if ((all - 1) == Auto.qq) {
					next.setEnabled(false);
					last.setEnabled(false);
				}

				if (Auto.qq < jml) {
					int as = Auto.qq + 1;
					Auto.isiHal = Integer.toString(as);
					Auto.Next_ROUTE(ID);
				}
			}
		});

		last.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				next.setEnabled(false);
				prev.setEnabled(true);
				first.setEnabled(true);

				if (Auto.jumlah % Auto.baris == 0)
					Auto.qq = Auto.jumlah / Auto.baris - 1;
				else
					Auto.qq = Auto.jumlah / Auto.baris;

				Auto.isiHal = Integer.toString(Auto.qq + 1);
				Auto.Next_ROUTE(ID);
				last.setEnabled(false);
			}
		});
	}
	
	static void bGetFPL(Button getFpl, final Button first, final Button prev, final Button next, final Button last, final Text t7a, final String ID) {
		getFpl.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Shell sh = null;
				if (ID.endsWith("DLA")) sh = Shells.sh_nDLA;
				else if (ID.endsWith("CHG")) sh = Shells.sh_nCHG;
				else if (ID.endsWith("CNL")) sh = Shells.sh_nCNL;
				else if (ID.endsWith("DEP")) sh = Shells.sh_nDEP;
				else if (ID.endsWith("ARR")) sh = Shells.sh_nARR;
				else if (ID.endsWith("CDN")) sh = Shells.sh_nCDN;
				else if (ID.endsWith("ACP")) sh = Shells.sh_nACP;
				else if (ID.endsWith("EST")) sh = Shells.sh_nEST;
				else if (ID.endsWith("RQS")) sh = Shells.sh_nRQS;
				else if (ID.endsWith("SPL")) sh = Shells.sh_nSPL;
				
				if (t7a.getText().equals("")) {
					DialogFactory.openInfoDialog(sh, "Get FPL", "Field AIRCRAFT ID should be filled !!"); t7a.setFocus();
				} else {
					String select = "SELECT * FROM air_message WHERE type3a='FPL' AND tgl!='0000-00-00 00:00:00' AND type7a LIKE '" + t7a.getText() + "%' order by tgl DESC";// LIMIT 0,20"; 
					Auto.koneksiDB_FPL(ID, select);

					if (Auto.getJumlah() == 0) {
						DialogFactory .openInfoDialog(sh, "Get FPL", "FPL not found, please check the value in field AIRCRAFT ID !!"); t7a.setFocus();
					} else if (Auto.getJumlah() == 1) {
						first.setEnabled(false);
						prev.setEnabled(false);
						next.setEnabled(false);
						last.setEnabled(false);
					} else {
						first.setEnabled(false);
						prev.setEnabled(false);
						next.setEnabled(true);
						last.setEnabled(true);
					}
				}
			}
		});

		first.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (first.getEnabled() == true) {
					first.setEnabled(false);
					prev.setEnabled(false);
					next.setEnabled(true);

					if (last.getEnabled() == false) {
						last.setEnabled(true);
					}
				}
				Auto.qq=0;
				Auto.isiHal = Integer.toString(Auto.qq);
				Auto.Next_FPL(ID);
			}
		});

		prev.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (prev.getEnabled() == true) {
					next.setEnabled(true);
					first.setEnabled(true);
					last.setEnabled(true);
				}

				Auto.qq--;
				if (Auto.qq == 0) {
					prev.setEnabled(false);
					first.setEnabled(false);
				}

				if (Auto.qq < 0)
					Auto.qq=0;
				else {
					int as = Integer.parseInt(Auto.akhir().trim()) - 1;
					Auto.isiHal = Integer.toString(as);
					Auto.Next_FPL(ID);
				}
			}
		});

		next.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				prev.setEnabled(true);
				first.setEnabled(true);

				Auto.qq++;
				int jml;
				if (Auto.jumlah % Auto.baris == 0) {
					jml = Auto.jumlah / Auto.baris;
				} else {
					jml = Auto.jumlah / Auto.baris + 1;
				}

				int all = jml;
				if ((all - 1) == Auto.qq) {
					next.setEnabled(false);
					last.setEnabled(false);
				}

				if (Auto.qq < jml) {
					int as = Auto.qq + 1;
					Auto.isiHal = Integer.toString(as);
					Auto.Next_FPL(ID);
				}
			}
		});

		last.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				next.setEnabled(false);
				prev.setEnabled(true);
				first.setEnabled(true);

				if (Auto.jumlah % Auto.baris == 0)
					Auto.qq = Auto.jumlah / Auto.baris - 1;
				else
					Auto.qq = Auto.jumlah / Auto.baris;

				Auto.isiHal = Integer.toString(Auto.qq + 1);
				Auto.Next_FPL(ID);
				last.setEnabled(false);
			}
		});
		sh.buttonStyle(getFpl, "", "Get FPL data from database", bs.widthNavBtn, bs.colorBtn, SWT.CENTER, SWT.LEFT, Images.img_aircraft);
		sh.buttonStyle(first, "", "Go to the first page", bs.widthNavBtn, bs.colorBtn, SWT.CENTER, SWT.LEFT, Images.img_first);
		sh.buttonStyle(prev, "", "Go to the previous page", bs.widthNavBtn, bs.colorBtn, SWT.CENTER, SWT.LEFT, Images.img_previous);
		sh.buttonStyle(next, "", "Go to the next page", bs.widthNavBtn, bs.colorBtn, SWT.CENTER, SWT.LEFT, Images.img_next);
		sh.buttonStyle(last, "", "Go to the last page", bs.widthNavBtn, bs.colorBtn, SWT.CENTER, SWT.LEFT, Images.img_last);
		
		first.setEnabled(false);
		prev.setEnabled(false);
		next.setEnabled(false);
		last.setEnabled(false);
	}
	
//	public static void bPBN(Button bPBN) {
//		//sh.buttonStyle(bPBN, "PBN/ Value", "Choose PBN/ Indicator Value", 210, 30, bs.colorBtn);
//		sh.buttonStyle(bPBN, "", "Choose PBN/ Indicator Value", bs.widthNavBtn, bs.heightBtn, bs.colorBtn, Images.img_LIST);
//	}
	
//	static void bSTSPBN(Button bSTS, Button bPBN) {
////		sh.buttonStyle(bSTS, "STS/ Value", "Choose STS/ Indicator Value", 210, 30, bs.colorBtn);
//		sh.buttonStyle(bSTS, "", "Choose STS/ Indicator Value", bs.widthNavBtn, bs.heightBtn, bs.colorBtn, SWT.TOP, SWT.LEFT, Images.img_LIST);
//		bPBN(bPBN);
//	}
	
//	public static void b10(Button b10a, Button b10b, Button btnChk, final Text t10a, final String ID) {
////		sh.buttonStyle(b10a, "10a Value", "Choose Equipment and Capabilities Value", 130, 30, bs.colorBtn);
////		sh.buttonStyle(b10b, "10b Value", "Choose Equipment and Capabilities Value", 130, 30, bs.colorBtn);
////		sh.buttonStyle(btnChk, "Check PBN", "Check PBN Suggestion", 130, 30, bs.colorBtn);
//		//if (bs.colorBtn!=null) bs.colorBtn.dispose();
//		
//		bs.bREG(b10a, btnChk, b10b);
//		
//		btnChk.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				String strPbn="",strNull="",strG="",strD="",strOD="",strI="",strDI="",aw="";
//
//				//tidak terdapat salah satu kriteria di bawah.
//				if (!t10a.getText().contains("R") && !t10a.getText().contains("G") 
//						&& !t10a.getText().contains("D") && !t10a.getText().contains("OD") && !t10a.getText().contains("SD") 
//						&& !t10a.getText().contains("I") && !t10a.getText().contains("DI")) { 
//					strNull = "No PBN/ Indicator Suggestion !!"; }
//				else if (t10a.getText().contains("R") && !t10a.getText().contains("G") && !t10a.getText().contains("D") 
//						&& !t10a.getText().contains("OD") && !t10a.getText().contains("SD") && !t10a.getText().contains("I") 
//						&& !t10a.getText().contains("DI")) { 
//					strNull = "PBN/ Indicator should be filled !!"; } 
//				//G
//				if (t10a.getText().contains("G")) { 
//					strG="* B1, B2, C1, C2, D1, D2, O1 or O2 in field PBN/ indicator,\nif field EQUIPMENT AND CAPABILITIES contains 'G'.\n\n"; } else strG="";
//				//D
//				if (t10a.getText().contains("D")) { 
//					strD="* B1, B3, C1, C3, D1, D3, O1 or O3 in field PBN/ indicator,\nif field EQUIPMENT AND CAPABILITIES contains 'D'.\n\n"; } else strD="";
//				//I
//				if (t10a.getText().contains("I")) { 
//					strI="* B1, B5, C1 or C5 in field PBN/ indicator,\nif field EQUIPMENT AND CAPABILITIES contains 'I'.\n\n"; } else strI="";
//				//DI
//				if (t10a.getText().contains("D") && t10a.getText().contains("I")) { 
//					strDI="* C1, C4, D1, D4, O1 or O4 in field PBN/ indicator,\nif field EQUIPMENT AND CAPABILITIES contains 'DI'.\n\n"; } else strDI="";
//				//OD or SD
//				if ((t10a.getText().contains("O") && t10a.getText().contains("D")) || 
//						(t10a.getText().contains("S") && t10a.getText().contains("D"))) { 
//					strOD="* B1 or B4 in field PBN/ indicator,\nif field EQUIPMENT AND CAPABILITIES contains 'OD' or 'SD'.\n\n"; } else strOD="";
//				//Gabungkan
//				if (!strG.isEmpty() || !strD.isEmpty() || !strOD.isEmpty() || !strI.isEmpty() || !strDI.isEmpty()) {
//					aw = "You may insert :\n\n";					
//				} else aw="";
//				strPbn = aw+strNull+strG+strD+strOD+strI+strDI;
////				DialogFactory.openInfoDialog(Shells.sh_nALR, "PBN/ Indicator Suggestion", strPbn);
//				
//				if (ID.endsWith("ALR")) {
//					DialogFactory.openInfoDialog(Shells.sh_nALR, "PBN/ Indicator Suggestion", strPbn); 
//					if (strPbn.contains("No PBN/ Indicator Suggestion !!")) t10aALR.setFocus();
//					else tPbnALR.setFocus();
//				}
//				else if (ID.endsWith("FPL")) {
//					DialogFactory.openInfoDialog(Shells.sh_nFPL, "PBN/ Indicator Suggestion", strPbn); 
//					if (strPbn.contains("No PBN/ Indicator Suggestion !!")) t10aFPL.setFocus();
//					else tPbnFPL.setFocus();
//				}
//				else if (ID.endsWith("CPL")) {
//					DialogFactory.openInfoDialog(Shells.sh_nCPL, "PBN/ Indicator Suggestion", strPbn); 
//					if (strPbn.contains("No PBN/ Indicator Suggestion !!")) t10aCPL.setFocus();
//					else tPbnCPL.setFocus();
//				}
//				else if (ID.compareToIgnoreCase("sREG")==0) {
//					DialogFactory.openInfoDialog(Shells.sh_tReg, "PBN/ Indicator Suggestion", strPbn); 
//					if (strPbn.contains("No PBN/ Indicator Suggestion !!")) TableReg.t10a_REG.setFocus();
//					else TableReg.tPbn_REG.setFocus();
//				}
//			}
//		});
//	}

	static void bTime(Composite comp, Button bTime, final Text tTime) {
//		sh.buttonStyle(bTime, "", "Get time now", bs.widthNavBtn, bs.colorBtn, Images.img_time);
		sh.buttonStyle(bTime,"","Get time now",bs.widthNavBtn,Colors.DarkGrey,SWT.CENTER,SWT.CENTER,Images.img_time);
		bTime.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				time.tgl();
				tTime.setText(time.hh+time.mm);
			}
		});
	}

	static void separatorFB(Group group) {
		Composite typeA = new Composite(group, SWT.NONE); sh.compositeStyle(typeA, 1, SWT.LEFT, SWT.CENTER);
		Label ls = new Label(typeA, SWT.CENTER | SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelSeparator(ls, iSeparatorFB, 10);
	}
	
	//------------------------------ NEW TEMPLATE ------------------------------
	public static void initNewAMO(Composite comp, final String ID) {
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, Title.tAMO);
		tFreeAMO = new Text(group, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
//		acs.setCompFree(tFreeAMO);
		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbAMO = new Text(c, SWT.BORDER);
		acs.setCompFree(tFreeAMO, tFbAMO);	
	}
	
	public static void initNewFREE(Composite comp, final String ID) {
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, Title.tFREE);
		tFreeTEXT = new Text(group, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER); 
//		acs.setCompFree(tFreeTEXT);
		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbTEXT = new Text(c, SWT.BORDER);
		acs.setCompFree(tFreeTEXT, tFbTEXT);
	}
	
	public static void initNewAFTN(Composite comp, final String ID) {
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tAFTN);
		Label label = new Label(group, SWT.NONE); sh.labelStyle(label, lblfree, 100, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFreeATS = new Text(group, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 2, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbAFTN = new Text(c, SWT.BORDER);
		acs.setCompFree(tFreeATS, tFbAFTN);
	}
	
	public static void initNewALR(Composite comp, final String ID) {
//		rff.readConfiguration();
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tALR);
		Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 13, SWT.LEFT, SWT.CENTER); 
		Label label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl5a, 135, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl5b, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl5c, 200, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY); 
		label = new Label(line1, SWT.NONE);
		//
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		t3aALR = new Text(line1, SWT.BORDER); t3aALR.setText("ALR"); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t3bALR = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		t3cALR = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		Composite comp5a = new Composite(line1, SWT.NONE); sh.compositeStyle(comp5a, 2, SWT.LEFT, SWT.TOP);
		t5aALR = new Text(comp5a, SWT.BORDER);
		b5aALR = new Button(comp5a, SWT.PUSH); 
		label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.CENTER, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		t5bALR = new Text(line1, SWT.BORDER); 
		label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.CENTER, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		Composite comp5c = new Composite(line1, SWT.NONE); sh.compositeStyle(comp5c, 2, SWT.LEFT, SWT.TOP);
		t5cALR = new Text(comp5c, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(comp5c, SWT.NONE); sh.labelimg(label, Images.img_enter, SWT.CENTER, SWT.TOP, 30);

		Composite line2 = new Composite(group, SWT.NONE); sh.compositeStyle(line2, 13, SWT.LEFT, SWT.CENTER);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl7a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY); 
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl7b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl7c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line2, SWT.NONE); 
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl8a, 135, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl8b, 60, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		for (int i=0; i<3; i++) label = new Label(line2, SWT.NONE);
		//
		label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t7aALR = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t7bALR = new Button(line2, SWT.CHECK);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		t7cALR = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		Composite comp8a = new Composite(line2, SWT.NONE); sh.compositeStyle(comp8a, 2, SWT.LEFT, SWT.CENTER);
		t8aALR = new Text(comp8a, SWT.BORDER);
		b8aALR = new Button(comp8a, SWT.PUSH); 
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);  
		Composite comp8b = new Composite(line2, SWT.NONE); sh.compositeStyle(comp8b, 3, SWT.LEFT, SWT.CENTER);
		t8bALR = new Text(comp8b, SWT.BORDER);
		b8bALR = new Button(comp8b, SWT.PUSH); 
		label = new Label(comp8b, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.CENTER, SWT.CENTER, 30);
		for (int i=0; i<3; i++) label = new Label(line2, SWT.NONE);
		
		Composite line3 = new Composite(group, SWT.NONE); sh.compositeStyle(line3, 8, SWT.LEFT, SWT.CENTER);
		label = new Label(line3, SWT.NONE);
		label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl9a, 110, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line3, SWT.NONE);
		label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl9b, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line3, SWT.NONE);
		label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl9c, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line3, SWT.NONE);
		label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl10, 260, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		//
		label = new Label(line3, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t9aALR = new Text(line3, SWT.BORDER);
		label = new Label(line3, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t9bALR = new Text(line3, SWT.BORDER);
		label = new Label(line3, SWT.CENTER); sh.labelStyle(label, "/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		Composite comp9c = new Composite(line3, SWT.NONE); sh.compositeStyle(comp9c, 3, SWT.LEFT, SWT.CENTER);
		t9cALR = new Text(comp9c, SWT.BORDER);
		b9cALR = new Button(comp9c, SWT.PUSH); 
		bSave9ALR = new Button(comp9c, SWT.PUSH); bSave9ALR.setEnabled(false);
		label = new Label(line3, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		Composite comp10 = new Composite(line3, SWT.NONE); sh.compositeStyle(comp10, 7, SWT.LEFT, SWT.CENTER);
		t10aALR = new Text(comp10, SWT.BORDER);
		btnChkALR = new Button(comp10, SWT.PUSH);
		b10aALR = new Button(comp10, SWT.PUSH);
		label = new Label(comp10, SWT.CENTER);  sh.labelStyle(label, "/", 14, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t10bALR = new Text(comp10, SWT.BORDER);
		b10bALR = new Button(comp10, SWT.PUSH);
		label = new Label(comp10, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.CENTER, SWT.CENTER, 30);

		Composite line4 = new Composite(group, SWT.NONE); sh.compositeStyle(line4, 13, SWT.LEFT, SWT.CENTER);
		label = new Label(line4, SWT.NONE);
		label = new Label(line4, SWT.NONE); sh.labelStyle(label, lbl13a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY); 
		label = new Label(line4, SWT.NONE);
		label = new Label(line4, SWT.NONE); sh.labelStyle(label, lbl13b, 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		for (int i=0; i<9; i++) label = new Label(line4, SWT.NONE);
		//
		label = new Label(line4, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
		t13aALR = new Text(line4, SWT.BORDER);
		label = new Label(line4, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		t13bALR = new Text(line4, SWT.BORDER);
		label = new Label(line4, SWT.LEFT);  sh.labelimg(label, Images.img_enter, SWT.CENTER, SWT.CENTER, 30);
		for (int i=0; i<8; i++) label = new Label(line4, SWT.NONE);//8
		
		Composite line5 = new Composite(group, SWT.NONE); sh.compositeStyle(line5, 13, SWT.LEFT, SWT.CENTER);
		label = new Label(line5, SWT.NONE);
		label = new Label(line5, SWT.NONE); sh.labelStyle(label, lbl15a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line5, SWT.NONE);
		label = new Label(line5, SWT.NONE); sh.labelStyle(label, lbl15b, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line5, SWT.NONE);
		label = new Label(line5, SWT.NONE); sh.labelStyle(label, lbl15c, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		for (int i=0; i<7; i++) label = new Label(line5, SWT.NONE);
		//
		label = new Label(line5, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		t15aALR = new Text(line5, SWT.BORDER);
		label = new Label(line5, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		t15bALR = new Text(line5, SWT.BORDER);
		label = new Label(line5, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.TOP, 20); 
		t15cALR = new Text(line5, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line5, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.TOP, 30); 
		
		Composite line5b = new Composite(line5, SWT.NONE); sh.compositeStyle(line5b, 6, SWT.LEFT, SWT.TOP);
		bSave15ALR = new Button(line5b, SWT.PUSH); bSave15ALR.setEnabled(false);
		getRouteALR = new Button(line5b, SWT.PUSH);
		firstALR = new Button(line5b, SWT.PUSH);
		prevALR = new Button(line5b, SWT.PUSH);
		nextALR = new Button(line5b, SWT.PUSH);
		lastALR = new Button(line5b, SWT.PUSH);
		
		Composite line6 = new Composite(group, SWT.NONE); sh.compositeStyle(line6, 13, SWT.LEFT, SWT.CENTER);
		label = new Label(line6, SWT.NONE);
		label = new Label(line6, SWT.NONE); sh.labelStyle(label, lbl16a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line6, SWT.NONE);
		label = new Label(line6, SWT.NONE); sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line6, SWT.NONE);
		label = new Label(line6, SWT.NONE); sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line6, SWT.NONE);
		label = new Label(line6, SWT.NONE); sh.labelStyle(label, lbl16d, 75, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<5; i++) label = new Label(line6, SWT.NONE);
		//
		label = new Label(line6, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t16aALR = new Text(line6, SWT.BORDER);
		label = new Label(line6, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t16bALR = new Text(line6, SWT.BORDER);
		label = new Label(line6, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.CENTER, SWT.CENTER, 20);
		t16cALR = new Text(line6, SWT.BORDER);
		label = new Label(line6, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.CENTER, SWT.CENTER, 40);
		Composite comp16d = new Composite(line6, SWT.NONE); sh.compositeStyle(comp16d, 2, SWT.LEFT, SWT.CENTER);
		t16dALR = new Text(comp16d, SWT.BORDER);
		label = new Label(comp16d, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.CENTER, SWT.CENTER, 30);
		for (int i=0; i<5; i++) label = new Label(line6, SWT.NONE);
		
		// type18
		Composite line7 = new Composite(group, SWT.NONE); sh.compositeStyle(line7, 2, SWT.LEFT, SWT.CENTER);
		label = new Label(line7, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line7, SWT.NONE); sh.labelStyle(label, lbl18, 210, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Composite line8 = new Composite(group, SWT.NONE); sh.compositeStyle(line8, 11, SWT.LEFT, SWT.CENTER);
		label = new Label(line8, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "STS/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compSts = new Composite(line8, SWT.NONE); sh.compositeStyle(compSts, 2, SWT.LEFT, SWT.TOP);
		tStsALR = new Text(compSts, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		bSTSALR = new Button(compSts, SWT.PUSH);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "PBN/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compPbn = new Composite(line8, SWT.NONE); sh.compositeStyle(compPbn, 2, SWT.LEFT, SWT.TOP);
		tPbnALR = new Text(compPbn, SWT.BORDER);
		bPBNALR = new Button(compPbn, SWT.PUSH);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "NAV/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tNavALR = new Text(line8, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//--------------
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "COM/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compCom = new Composite(line8, SWT.NONE); sh.compositeStyle(compCom, 1, SWT.LEFT, SWT.TOP);
		tComALR = new Text(compCom, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DAT/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compDat = new Composite(line8, SWT.NONE); sh.compositeStyle(compDat, 1, SWT.LEFT, SWT.TOP);
		tDatALR = new Text(compDat, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "SUR/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tSurALR = new Text(line8, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DEP/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compDep = new Composite(line8, SWT.NONE); sh.compositeStyle(compDep, 1, SWT.LEFT, SWT.TOP);
		tDepALR = new Text(compDep, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DEST/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compDest = new Composite(line8, SWT.NONE); sh.compositeStyle(compDest, 1, SWT.LEFT, SWT.TOP);
		tDestALR = new Text(compDest, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DOF/", 50, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.MANDATORY);
		tDofALR = new Text(line8, SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "REG/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compReg = new Composite(line8, SWT.NONE); sh.compositeStyle(compReg, 1, SWT.LEFT, SWT.TOP);
		tRegALR = new Text(compReg, SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "EET/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compEet = new Composite(line8, SWT.NONE); sh.compositeStyle(compEet, 1, SWT.LEFT, SWT.TOP);
		tEetALR = new Text(compEet, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "SEL/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tSelALR = new Text(line8, SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "TYP/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compTyp = new Composite(line8, SWT.NONE); sh.compositeStyle(compTyp, 1, SWT.LEFT, SWT.TOP);
		tTypALR = new Text(compTyp, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "CODE/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compCode = new Composite(line8, SWT.NONE); sh.compositeStyle(compCode, 1, SWT.LEFT, SWT.TOP);
		tCodeALR = new Text(compCode, SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DLE/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tDleALR = new Text(line8, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "OPR/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compOpr = new Composite(line8, SWT.NONE); sh.compositeStyle(compOpr, 1, SWT.LEFT, SWT.TOP);
		tOprALR = new Text(compOpr, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "ORGN/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compOrgn = new Composite(line8, SWT.NONE); sh.compositeStyle(compOrgn, 1, SWT.LEFT, SWT.TOP);
		tOrgnALR = new Text(compOrgn, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "PER/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tPerALR = new Text(line8, SWT.BORDER); 
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "ALTN/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compAltn = new Composite(line8, SWT.NONE); sh.compositeStyle(compAltn, 1, SWT.LEFT, SWT.TOP);
		tAltnALR = new Text(compAltn, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "RALT/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compRalt = new Composite(line8, SWT.NONE); sh.compositeStyle(compRalt, 1, SWT.LEFT, SWT.TOP);
		tRaltALR = new Text(compRalt, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "TALT/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tTaltALR = new Text(line8, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "RIF/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compRif = new Composite(line8, SWT.NONE); sh.compositeStyle(compRif, 1, SWT.LEFT, SWT.TOP);
		tRifALR = new Text(compRif, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "RMK/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compRmk = new Composite(line8, SWT.NONE); sh.compositeStyle(compRmk, 1, SWT.LEFT, SWT.TOP);
		tRmkALR = new Text(compRmk, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		Composite compSym = new Composite(line8, SWT.NONE); sh.compositeStyle(compSym, 2, SWT.LEFT, SWT.TOP);
		label = new Label(compSym, SWT.NONE); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		label = new Label(compSym, SWT.NONE); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.TOP, 30);
		for (int i=0; i < 4; i++) label = new Label(line8, SWT.NONE);
		
		Composite typeA = new Composite(group, SWT.NONE); sh.compositeStyle(typeA, 1, SWT.CENTER, SWT.CENTER);
		label = new Label(typeA, SWT.CENTER | SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelSeparator(label, iSeparator, 10);
		// type19
		Composite line9 = new Composite(group, SWT.NONE); sh.compositeStyle(line9, 1, SWT.CENTER, SWT.CENTER);
		label = new Label(line9, SWT.CENTER); sh.labelStyle(label, lblSUPINFO, iSeparator, SWT.CENTER, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		
		Composite line10 = new Composite(group, SWT.NONE); sh.compositeStyle(line10, 2, SWT.LEFT, SWT.CENTER);
		label = new Label(line10, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line10, SWT.NONE); sh.labelStyle(label, lbl19, 120, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		
		Composite line11 = new Composite(group, SWT.NONE); sh.compositeStyle(line11, 9, SWT.LEFT, SWT.CENTER);
		// type19abc
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, lbl19a, 178, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, lbl19b, 221, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, lbl19c, 180, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		// 19abc
		label = new Label(line11, SWT.RIGHT); sh.labelStyle(label, "-", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "E/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19aALR = new Text(line11, SWT.BORDER);
		label = new Label(line11, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.CENTER, SWT.CENTER, 20);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "P/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19bALR = new Text(line11, SWT.BORDER);
		label = new Label(line11, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.CENTER, SWT.CENTER, 20);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "R/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		Composite cbtn19c = new Composite(line11, SWT.NONE); sh.compositeStyle(cbtn19c, 8, SWT.LEFT, SWT.CENTER);
		t19cUhfALR = new Text(cbtn19c, SWT.BORDER);
		t19cUhfALR.setCursor(cs1);
		t19cUhfALR.setEditable(false);
		t19cUhfALR.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19cUhfALR.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19cUhfALR.getText().compareTo("U")==0)
					t19cUhfALR.setText("X");
				else t19cUhfALR.setText("U");
				t19bALR.setFocus();
			}
		});
		t19cUhfALR.setText("U");
	    Label l_1 = new Label(cbtn19c,SWT.LEFT);
	    l_1.setText("UHF		");
		
		t19cVhfALR = new Text(cbtn19c, SWT.BORDER);
		t19cVhfALR.setCursor(cs1);
		t19cVhfALR.setEditable(false);
		t19cVhfALR.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19cVhfALR.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19cVhfALR.getText().compareTo("V")==0)
					t19cVhfALR.setText("X");
				else t19cVhfALR.setText("V");
				t19bALR.setFocus();
			}
		});
		t19cVhfALR.setText("V");
	    Label l_2 = new Label(cbtn19c,SWT.LEFT);
	    l_2.setText("VHF		");
	    
		t19cEltALR = new Text(cbtn19c, SWT.BORDER);
		t19cEltALR.setCursor(cs1);
		t19cEltALR.setEditable(false);
		t19cEltALR.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19cEltALR.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19cEltALR.getText().compareTo("E")==0)
					t19cEltALR.setText("X");
				else t19cEltALR.setText("E");
				t19bALR.setFocus();
			}
		});
		t19cEltALR.setText("E");
	    Label l_3 = new Label(cbtn19c,SWT.LEFT);
	    l_3.setText("ELT		");		
	    
		Composite line12 = new Composite(group, SWT.NONE); sh.compositeStyle(line12, 10, SWT.LEFT, SWT.CENTER);
		// type19de
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, lbl19d, 190, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 55, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, lbl19e, 80, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		// de
		Composite line122 = new Composite(group, SWT.NONE); sh.compositeStyle(line122, 10, SWT.LEFT, SWT.CENTER);
		label = new Label(line122, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
		t19sALR = new Text(line122, SWT.BORDER);
		t19sALR.setCursor(cs1);
		t19sALR.setEditable(false);
		t19sALR.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19sALR.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if ((t19sALR.getText().isEmpty())||(t19sALR.getText().compareTo("S")==0)) {
					t19sALR.setText("X");
					t19dPALR.setText("X");
					t19dDALR.setText("X");
					t19dMALR.setText("X");
					t19dJALR.setText("X");
				}
				else {
					t19sALR.setText("S");
					t19dPALR.setText("P");
					t19dDALR.setText("D");
					t19dMALR.setText("M");
					t19dJALR.setText("J");
				}
				t19bALR.setFocus();
			}
		});
		t19sALR.setText("S");
		label = new Label(line122, SWT.NONE); sh.labelStyle(label, " / ", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);

		Composite cbtn19d = new Composite(line122, SWT.NONE); sh.compositeStyle(cbtn19d, 8, SWT.LEFT, SWT.CENTER);

		t19dPALR = new Text(cbtn19d, SWT.BORDER);
		t19dPALR.setCursor(cs1);
		t19dPALR.setEditable(false);
		t19dPALR.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19dPALR.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19dPALR.getText().compareTo("P")==0)
					t19dPALR.setText("X");
				else if (t19sALR.getText().compareTo("S")==0) t19dPALR.setText("P");
				t19bALR.setFocus();
			}
		});
		t19dPALR.setText("P");
	    Label l_4 = new Label(cbtn19d,SWT.LEFT);
	    l_4.setText("POLAR	");
	    
	    t19dDALR = new Text(cbtn19d, SWT.BORDER);
		t19dDALR.setCursor(cs1);
		t19dDALR.setEditable(false);
		t19dDALR.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19dDALR.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19dDALR.getText().compareTo("D")==0)
					t19dDALR.setText("X");
				else if (t19sALR.getText().compareTo("S")==0) t19dDALR.setText("D");
				t19bALR.setFocus();
			}
		});
		t19dDALR.setText("D");
	    Label l_5 = new Label(cbtn19d,SWT.LEFT);
	    l_5.setText("DESERT	");

		t19dMALR = new Text(cbtn19d, SWT.BORDER);
		t19dMALR.setCursor(cs1);
		t19dMALR.setEditable(false);
		t19dMALR.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19dMALR.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19dMALR.getText().compareTo("M")==0)
					t19dMALR.setText("X");
				else if (t19sALR.getText().compareTo("S")==0) t19dMALR.setText("M");
				t19bALR.setFocus();
			}
		});
		t19dMALR.setText("M");
	    Label l_6 = new Label(cbtn19d,SWT.LEFT);
	    l_6.setText("MARITIME	");

		t19dJALR = new Text(cbtn19d, SWT.BORDER);
		t19dJALR.setCursor(cs1);
		t19dJALR.setEditable(false);
		t19dJALR.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19dJALR.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19dJALR.getText().compareTo("J")==0)
					t19dJALR.setText("X");
				else if (t19sALR.getText().compareTo("S")==0) t19dJALR.setText("J");
				t19bALR.setFocus();
			}
		});
		t19dJALR.setText("J");
	    Label l_7 = new Label(cbtn19d,SWT.LEFT);
	    l_7.setText("JUNGLE		");

		label = new Label(line122, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.CENTER, SWT.CENTER, 20);
		//label = new Label(line12, SWT.NONE); sh.labelStyle(label, "J/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19jALR = new Text(line122, SWT.BORDER);
		t19jALR.setCursor(cs1);
		t19jALR.setEditable(false);
		t19jALR.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19jALR.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if ((t19jALR.getText().isEmpty())||(t19jALR.getText().compareTo("J")==0)) {
					t19jALR.setText("X");
					t19eLALR.setText("X");
					t19eFALR.setText("X");
					t19eUALR.setText("X");
					t19eVALR.setText("X");
				}
				else {
					t19jALR.setText("J");
					t19eLALR.setText("L");
					t19eFALR.setText("F");
					t19eUALR.setText("U");
					t19eVALR.setText("V");
				}
				t19bALR.setFocus();
			}
		});
		t19jALR.setText("J");
		label = new Label(line122, SWT.NONE); sh.labelStyle(label, " / ", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);

		Composite cbtn19e = new Composite(line122, SWT.NONE); sh.compositeStyle(cbtn19e, 8, SWT.LEFT, SWT.CENTER);

		t19eLALR = new Text(cbtn19e, SWT.BORDER);
		t19eLALR.setCursor(cs1);
		t19eLALR.setEditable(false);
		t19eLALR.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19eLALR.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19eLALR.getText().compareTo("L")==0)
					t19eLALR.setText("X");
				else if (t19jALR.getText().compareTo("J")==0) t19eLALR.setText("L");
				t19bALR.setFocus();
			}
		});
		t19eLALR.setText("L");
	    Label l_8 = new Label(cbtn19e,SWT.LEFT);
	    l_8.setText("LIGHT	");
	    
		t19eFALR = new Text(cbtn19e, SWT.BORDER);
		t19eFALR.setCursor(cs1);
		t19eFALR.setEditable(false);
		t19eFALR.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19eFALR.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19eFALR.getText().compareTo("F")==0)
					t19eFALR.setText("X");
				else if (t19jALR.getText().compareTo("J")==0) t19eFALR.setText("F");
				t19bALR.setFocus();
			}
		});
		t19eFALR.setText("F");
	    Label l_9 = new Label(cbtn19e,SWT.LEFT);
	    l_9.setText("FLOURES		");

		t19eUALR = new Text(cbtn19e, SWT.BORDER);
		t19eUALR.setCursor(cs1);
		t19eUALR.setEditable(false);
		t19eUALR.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19eUALR.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19eUALR.getText().compareTo("U")==0)
					t19eUALR.setText("X");
				else if (t19jALR.getText().compareTo("J")==0) t19eUALR.setText("U");
				t19bALR.setFocus();
			}
		});
		t19eUALR.setText("U");
	    Label l_10 = new Label(cbtn19e,SWT.LEFT);
	    l_10.setText("UHF		");
	    
		t19eVALR = new Text(cbtn19e, SWT.BORDER);
		t19eVALR.setCursor(cs1);
		t19eVALR.setEditable(false);
		t19eVALR.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19eVALR.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19eVALR.getText().compareTo("V")==0)
					t19eVALR.setText("X");
				else if (t19jALR.getText().compareTo("J")==0) t19eVALR.setText("V");
				t19bALR.setFocus();
			}
		});
		t19eVALR.setText("V");
	    Label l_11 = new Label(cbtn19e,SWT.LEFT);
	    l_11.setText("VHF	");		
	    
	    for (int i=0; i<2; i++) label = new Label(line122, SWT.NONE); 
		
		Composite line13 = new Composite(group, SWT.NONE); sh.compositeStyle(line13, 10, SWT.LEFT, SWT.CENTER);
		// type19f
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Composite clbl19f = new Composite(line13, SWT.NONE); sh.compositeStyle(clbl19f, 5, SWT.LEFT, SWT.CENTER);
		label = new Label(clbl19f, SWT.NONE); sh.labelStyle(label, lbl19fnum, 100, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(clbl19f, SWT.NONE); sh.labelStyle(label, "", 8, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(clbl19f, SWT.NONE); sh.labelStyle(label, lbl19fcap, 90, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(clbl19f, SWT.NONE); sh.labelStyle(label, "", 15, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(clbl19f, SWT.NONE); sh.labelStyle(label, lbl19fcov, 61, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, lbl19fcol, 70, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<3; i++) label = new Label(line13, SWT.NONE);
		
		// f
		Composite line133 = new Composite(group, SWT.NONE); sh.compositeStyle(line133, 10, SWT.LEFT, SWT.CENTER);
		label = new Label(line133, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);

		//label = new Label(line13, SWT.NONE); sh.labelStyle(label, "D/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19dALR = new Text(line133, SWT.BORDER);
		t19dALR.setCursor(cs1);
		t19dALR.setEditable(false);
		t19dALR.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19dALR.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if ((t19dALR.getText().isEmpty())||(t19dALR.getText().compareTo("D")==0)) {
					t19dALR.setText("X");
					t19NumALR.setText("");
					t19CapALR.setText("");
					t19ColALR.setText("");
					t19cALR.setText("X");
					t19NumALR.setEnabled(false);
					t19CapALR.setEnabled(false);
					t19ColALR.setEnabled(false);
					t19cALR.setEnabled(false);
				}
				else {
					t19dALR.setText("D");
					t19cALR.setText("C");
					t19NumALR.setEnabled(true);
					t19CapALR.setEnabled(true);
					t19ColALR.setEnabled(true);
					t19cALR.setEnabled(true);
				}
				t19bALR.setFocus();
			}
		});
		t19dALR.setText("D");
		label = new Label(line133, SWT.NONE); sh.labelStyle(label, " / " , 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);

		Composite ctxt19f = new Composite(line133, SWT.NONE); sh.compositeStyle(ctxt19f, 5, SWT.LEFT, SWT.CENTER);
		t19NumALR = new Text(ctxt19f, SWT.BORDER);
		label = new Label(ctxt19f, SWT.NONE); sh.labelStyle(label, "", 77, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t19CapALR = new Text(ctxt19f, SWT.BORDER);
		
		label = new Label(ctxt19f, SWT.NONE); sh.labelStyle(label, "", 27, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);

		label = new Label(ctxt19f, SWT.CENTER); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19cALR = new Text(line133, SWT.BORDER);
		t19cALR.setCursor(cs1);
		t19cALR.setEditable(false);
		t19cALR.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19cALR.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if ((t19cALR.getText().isEmpty())||(t19cALR.getText().compareTo("C")==0)) {
					t19cALR.setText("X");
					//t19ColALR.setEnabled(false);
					//t19ColALR.setText("");
				}
				else {
					t19cALR.setText("C");
					//t19ColALR.setEnabled(true);
				}
				t19bALR.setFocus();
			}
		});
		t19cALR.setText("X");
		
		label = new Label(line133, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line133, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.CENTER, SWT.CENTER, 20);
		t19ColALR = new Text(line133, SWT.BORDER);
		label = new Label(line133, SWT.NONE); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
		//for (int i=0; i<2; i++) label = new Label(line133, SWT.NONE); 

		Composite line14 = new Composite(group, SWT.NONE); sh.compositeStyle(line14, 9, SWT.LEFT, SWT.CENTER);
		// type19g
		label = new Label(line14, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line14, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line14, SWT.NONE); sh.labelStyle(label, lbl19g, 310, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<6; i++) label = new Label(line14, SWT.NONE); 
		// g
		label = new Label(line14, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line14, SWT.NONE); sh.labelStyle(label, "A/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19AirALR = new Text(line14, SWT.BORDER);
		for (int i=0; i<6; i++) label = new Label(line14, SWT.NONE); 

		Composite line15 = new Composite(group, SWT.NONE); sh.compositeStyle(line15, 10, SWT.LEFT, SWT.CENTER);
		// type19h
		label = new Label(line15, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line15, SWT.NONE); sh.labelStyle(label, lbl19h, 90, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line15, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<7; i++) label = new Label(line15, SWT.NONE); 
		// h
		
		Composite line155 = new Composite(group, SWT.NONE); sh.compositeStyle(line155, 10, SWT.LEFT, SWT.CENTER);
		label = new Label(line155, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
		//label = new Label(line15, SWT.NONE); sh.labelStyle(label, "N/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19nALR = new Text(line155, SWT.BORDER);
		t19nALR.setCursor(cs1);
		t19nALR.setEditable(false);
		t19nALR.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19nALR.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if ((t19nALR.getText().isEmpty())||(t19nALR.getText().compareTo("N")==0)) {
					t19nALR.setText("X");
					t19RemALR.setEnabled(false);
					t19RemALR.setText("");
				}
				else {
					t19nALR.setText("N");
					t19RemALR.setEnabled(true);
				}
				t19bALR.setFocus();
			}
		});
		t19nALR.setText("N");
		label = new Label(line155, SWT.NONE); sh.labelStyle(label, " / ", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);

		t19RemALR = new Text(line155, SWT.BORDER);
		label = new Label(line155, SWT.NONE); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
		for (int i=0; i<5; i++) label = new Label(line155, SWT.NONE); 

		Composite line16 = new Composite(group, SWT.NONE); sh.compositeStyle(line16, 9, SWT.LEFT, SWT.CENTER);
		// type19i
		label = new Label(line16, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line16, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line16, SWT.NONE); sh.labelStyle(label, lbl19i, 180, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<6; i++) label = new Label(line16, SWT.NONE); 
		// i
		label = new Label(line16, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line16, SWT.NONE); sh.labelStyle(label, "C/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19PilALR = new Text(line16, SWT.BORDER);
		label = new Label(line16, SWT.NONE); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
		for (int i=0; i<5; i++) label = new Label(line16, SWT.NONE); 

		Composite lineX = new Composite(group, SWT.NONE); sh.compositeStyle(lineX, 4, SWT.LEFT, SWT.CENTER);
		// type20
		label = new Label(lineX, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(lineX, SWT.NONE);  sh.labelStyle(label, lbl20, 420, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		for (int i=0; i<2; i++) label = new Label(lineX, SWT.NONE);
		// 20
		label = new Label(lineX, SWT.NONE); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		t20ALR = new Text(lineX, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(lineX, SWT.NONE); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		label = new Label(lineX, SWT.NONE); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.TOP, 30);
	
		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbALR = new Text(c, SWT.BORDER);
		
		addListenerALR();
		acs.setCompALR(t3aALR, t3bALR, t3cALR, t5aALR, t5bALR, t5cALR, t7aALR, t7bALR, t7cALR, t8aALR, t8bALR, t9aALR, t9bALR, t9cALR, t10aALR, t10bALR, t13aALR, t13bALR, t15aALR, t15bALR, t15cALR, t16aALR, t16bALR, t16cALR, t16dALR, tNavALR, tComALR, tDatALR, tSurALR, tDepALR, tDestALR, tDofALR, tRegALR, tEetALR, tSelALR, tTypALR, tCodeALR, tDleALR, tOprALR, tOrgnALR, tPerALR, tAltnALR, tRaltALR, tTaltALR, tRifALR, tRmkALR, tPbnALR, tStsALR, t19aALR, t19bALR, t19cUhfALR, t19cVhfALR, t19cEltALR, t19dPALR, t19dDALR, t19dMALR, t19dJALR, t19eLALR, t19eFALR, t19eUALR, t19eVALR, t19NumALR, t19CapALR, t19ColALR, t19AirALR, t19RemALR, t19PilALR, t20ALR, tFbALR);
	}
	
	public static void initNewRCF(Composite comp, final String ID) {
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tRCF);
		Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 13, SWT.LEFT, SWT.TOP);
		Composite line2 = new Composite(group, SWT.NONE); sh.compositeStyle(line2, 4, SWT.LEFT, SWT.TOP);
		
		Label label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY); 
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY); 
		label = new Label(line1, SWT.NONE);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE);
		//
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t3aRCF = new Text(line1, SWT.BORDER); t3aRCF.setText("RCF"); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		t3bRCF = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t3cRCF = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
		t7aRCF = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
		t7bRCF = new Button(line1, SWT.CHECK);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		t7cRCF = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.TOP, 30);
		
		label = new Label(line2, SWT.NONE); 
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl21, 300, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY); 
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); 
		label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		t21RCF = new Text(line2, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		label = new Label(line2, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		label = new Label(line2, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.TOP, 30);

		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbRCF = new Text(c, SWT.BORDER);
		
		acs.setCompRCF(t3aRCF, t3bRCF, t3cRCF, t7aRCF, t7bRCF, t7cRCF, t21RCF, tFbRCF);
	}

	public static void initNewFPL(Composite comp, final String ID) {
//		rff.readConfiguration();
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tFPL);
		Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 17, SWT.LEFT, SWT.CENTER);
		Label label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl8a, 65, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl8b, 65, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE);
		//
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t3aFPL = new Text(line1, SWT.BORDER); t3aFPL.setText("FPL"); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t3bFPL = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		t3cFPL = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t7aFPL = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
		t7bFPL = new Button(line1, SWT.CHECK); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		t7cFPL = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		Composite comp8a = new Composite(line1, SWT.NONE); sh.compositeStyle(comp8a, 2, SWT.LEFT, SWT.CENTER);
		t8aFPL = new Text(comp8a, SWT.BORDER);
		b8aFPL = new Button(comp8a, SWT.PUSH); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);  
		Composite comp8b = new Composite(line1, SWT.NONE); sh.compositeStyle(comp8b, 3, SWT.LEFT, SWT.CENTER);
		t8bFPL = new Text(comp8b, SWT.BORDER);
		b8bFPL = new Button(comp8b, SWT.PUSH); 
		label = new Label(comp8b, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
		
		Composite line3 = new Composite(group, SWT.NONE); sh.compositeStyle(line3, 8, SWT.LEFT, SWT.CENTER);
		label = new Label(line3, SWT.NONE);
		label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl9a, 110, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line3, SWT.NONE);
		label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl9b, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line3, SWT.NONE);
		label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl9c, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line3, SWT.NONE);
		label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl10, 260, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		//
		label = new Label(line3, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t9aFPL = new Text(line3, SWT.BORDER);
		label = new Label(line3, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t9bFPL = new Text(line3, SWT.BORDER);
		label = new Label(line3, SWT.CENTER); sh.labelStyle(label, "/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		Composite comp9c = new Composite(line3, SWT.NONE); sh.compositeStyle(comp9c, 3, SWT.LEFT, SWT.CENTER);
		t9cFPL = new Text(comp9c, SWT.BORDER);
		b9cFPL = new Button(comp9c, SWT.PUSH); 
		bSave9FPL = new Button(comp9c, SWT.PUSH); bSave9FPL.setEnabled(false);
		label = new Label(line3, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		Composite comp10 = new Composite(line3, SWT.NONE); sh.compositeStyle(comp10, 7, SWT.LEFT, SWT.CENTER);
		t10aFPL = new Text(comp10, SWT.BORDER);
		btnChkFPL = new Button(comp10, SWT.PUSH);
		b10aFPL = new Button(comp10, SWT.PUSH);
		label = new Label(comp10, SWT.CENTER);  sh.labelStyle(label, "/", 14, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t10bFPL = new Text(comp10, SWT.BORDER);
		b10bFPL = new Button(comp10, SWT.PUSH);
		label = new Label(comp10, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.CENTER, SWT.CENTER, 30);

		Composite line4 = new Composite(group, SWT.NONE); sh.compositeStyle(line4, 13, SWT.LEFT, SWT.CENTER);
		label = new Label(line4, SWT.NONE);
		label = new Label(line4, SWT.NONE); sh.labelStyle(label, lbl13a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line4, SWT.NONE);
		label = new Label(line4, SWT.NONE); sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		for (int i=0; i<9; i++) label = new Label(line4, SWT.NONE);
		// 
		label = new Label(line4, SWT.NONE); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t13aFPL = new Text(line4, SWT.BORDER);
		label = new Label(line4, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t13bFPL = new Text(line4, SWT.BORDER);
		label = new Label(line4, SWT.CENTER);  sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
		for (int i=0; i<8; i++) label = new Label(line4, SWT.NONE);
		
		Composite line5 = new Composite(group, SWT.NONE); sh.compositeStyle(line5, 13, SWT.LEFT, SWT.CENTER);
		label = new Label(line5, SWT.NONE);
		label = new Label(line5, SWT.NONE); sh.labelStyle(label, lbl15a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line5, SWT.NONE);
		label = new Label(line5, SWT.NONE); sh.labelStyle(label, lbl15b, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line5, SWT.NONE);
		label = new Label(line5, SWT.NONE); sh.labelStyle(label, lbl15c, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		for (int i=0; i<7; i++) label = new Label(line5, SWT.NONE);
		label = new Label(line5, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		t15aFPL = new Text(line5, SWT.BORDER);
		label = new Label(line5, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		t15bFPL = new Text(line5, SWT.BORDER);
		label = new Label(line5, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.TOP, 20); 
		t15cFPL = new Text(line5, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line5, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.TOP, 30); 
		Composite line5b = new Composite(line5, SWT.NONE); sh.compositeStyle(line5b, 6, SWT.LEFT, SWT.TOP);
		bSave15FPL = new Button(line5b, SWT.PUSH); bSave15FPL.setEnabled(false); 
		getRouteFPL = new Button(line5b, SWT.PUSH);
		firstFPL = new Button(line5b, SWT.PUSH);
		prevFPL = new Button(line5b, SWT.PUSH);
		nextFPL = new Button(line5b, SWT.PUSH);
		lastFPL = new Button(line5b, SWT.PUSH);
		
		Composite line6 = new Composite(group, SWT.NONE); sh.compositeStyle(line6, 13, SWT.LEFT, SWT.CENTER);
		label = new Label(line6, SWT.NONE);
		label = new Label(line6, SWT.NONE); sh.labelStyle(label, lbl16a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line6, SWT.NONE);
		label = new Label(line6, SWT.NONE); sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line6, SWT.NONE);
		label = new Label(line6, SWT.NONE); //sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+ "altnad.txt").compareTo("1")==0)
			sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		else sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line6, SWT.NONE);
		label = new Label(line6, SWT.NONE); sh.labelStyle(label, lbl16d, 75, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<5; i++) label = new Label(line6, SWT.NONE);
		// 
		label = new Label(line6, SWT.NONE); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t16aFPL = new Text(line6, SWT.BORDER);
		label = new Label(line6, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t16bFPL = new Text(line6, SWT.BORDER);
		label = new Label(line6, SWT.CENTER); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
		t16cFPL = new Text(line6, SWT.BORDER);
		label = new Label(line6, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
		t16dFPL = new Text(line6, SWT.BORDER);
		label = new Label(line6, SWT.CENTER); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
		for (int i=0; i<4; i++) label = new Label(line6, SWT.NONE);
		
		// type18
		Composite line7 = new Composite(group, SWT.NONE); sh.compositeStyle(line7, 2, SWT.LEFT, SWT.CENTER);
		label = new Label(line7, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line7, SWT.NONE); sh.labelStyle(label, lbl18, 210, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Composite line8 = new Composite(group, SWT.NONE); sh.compositeStyle(line8, 11, SWT.LEFT, SWT.CENTER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "STS/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		
		Composite compSts = new Composite(line8, SWT.NONE); sh.compositeStyle(compSts, 2, SWT.LEFT, SWT.TOP);
		tStsFPL = new Text(compSts, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		bSTSFPL = new Button(compSts, SWT.PUSH);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "PBN/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compPbn = new Composite(line8, SWT.NONE); sh.compositeStyle(compPbn, 2, SWT.LEFT, SWT.TOP);
		tPbnFPL = new Text(compPbn, SWT.BORDER);
		bPBNFPL = new Button(compPbn, SWT.PUSH);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "NAV/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tNavFPL = new Text(line8, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//--------------
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "COM/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compCom = new Composite(line8, SWT.NONE); sh.compositeStyle(compCom, 1, SWT.LEFT, SWT.TOP);
		tComFPL = new Text(compCom, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DAT/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compDat = new Composite(line8, SWT.NONE); sh.compositeStyle(compDat, 1, SWT.LEFT, SWT.TOP);
		tDatFPL = new Text(compDat, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "SUR/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tSurFPL = new Text(line8, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DEP/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compDep = new Composite(line8, SWT.NONE); sh.compositeStyle(compDep, 1, SWT.LEFT, SWT.TOP);
		tDepFPL = new Text(compDep, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DEST/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compDest = new Composite(line8, SWT.NONE); sh.compositeStyle(compDest, 1, SWT.LEFT, SWT.TOP);
		tDestFPL = new Text(compDest, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DOF/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tDofFPL = new Text(line8, SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "REG/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compReg = new Composite(line8, SWT.NONE); sh.compositeStyle(compReg, 2, SWT.LEFT, SWT.TOP);
		tRegFPL = new Text(compReg, SWT.BORDER);
		bSaveRegFPL = new Button(compReg, SWT.PUSH); 
		//-----------------------------
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "EET/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compEet = new Composite(line8, SWT.NONE); sh.compositeStyle(compEet, 1, SWT.LEFT, SWT.TOP);
		tEetFPL = new Text(compEet, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "SEL/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tSelFPL = new Text(line8, SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "TYP/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compTyp = new Composite(line8, SWT.NONE); sh.compositeStyle(compTyp, 1, SWT.LEFT, SWT.TOP);
		tTypFPL = new Text(compTyp, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "CODE/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compCode = new Composite(line8, SWT.NONE); sh.compositeStyle(compCode, 1, SWT.LEFT, SWT.TOP);
		tCodeFPL = new Text(compCode, SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DLE/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tDleFPL = new Text(line8, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "OPR/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compOpr = new Composite(line8, SWT.NONE); sh.compositeStyle(compOpr, 1, SWT.LEFT, SWT.TOP);
		tOprFPL = new Text(compOpr, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "ORGN/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compOrgn = new Composite(line8, SWT.NONE); sh.compositeStyle(compOrgn, 1, SWT.LEFT, SWT.TOP);
		tOrgnFPL = new Text(compOrgn, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "PER/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tPerFPL = new Text(line8, SWT.BORDER); 
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "ALTN/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compAltn = new Composite(line8, SWT.NONE); sh.compositeStyle(compAltn, 1, SWT.LEFT, SWT.TOP);
		tAltnFPL = new Text(compAltn, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "RALT/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compRalt = new Composite(line8, SWT.NONE); sh.compositeStyle(compRalt, 1, SWT.LEFT, SWT.TOP);
		tRaltFPL = new Text(compRalt, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "TALT/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tTaltFPL = new Text(line8, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "RIF/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compRif = new Composite(line8, SWT.NONE); sh.compositeStyle(compRif, 1, SWT.LEFT, SWT.TOP);
		tRifFPL = new Text(compRif, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "RMK/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compRmk = new Composite(line8, SWT.NONE); sh.compositeStyle(compRmk, 1, SWT.LEFT, SWT.TOP);
		tRmkFPL = new Text(compRmk, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		Composite compSym = new Composite(line8, SWT.NONE); sh.compositeStyle(compSym, 2, SWT.LEFT, SWT.TOP);
		label = new Label(compSym, SWT.NONE); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		label = new Label(compSym, SWT.NONE); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.TOP, 30);
		for (int i=0; i < 4; i++) label = new Label(line8, SWT.NONE);

		Composite typeA = new Composite(group, SWT.NONE); sh.compositeStyle(typeA, 1, SWT.CENTER, SWT.CENTER);
		label = new Label(typeA, SWT.CENTER | SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelSeparator(label, iSeparator, 10);
		// type19
		Composite line9 = new Composite(group, SWT.NONE); sh.compositeStyle(line9, 1, SWT.CENTER, SWT.CENTER);
		label = new Label(line9, SWT.CENTER); sh.labelStyle(label, lblSUPINFO, iSeparator, SWT.CENTER, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		
		Composite line10 = new Composite(group, SWT.NONE); sh.compositeStyle(line10, 2, SWT.LEFT, SWT.CENTER);
		label = new Label(line10, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line10, SWT.NONE); sh.labelStyle(label, lbl19, 120, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		
		Composite line11 = new Composite(group, SWT.NONE); sh.compositeStyle(line11, 9, SWT.LEFT, SWT.CENTER);
		// type19abc
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, lbl19a, 178, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, lbl19b, 221, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, lbl19c, 180, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		// 19abc
		label = new Label(line11, SWT.RIGHT); sh.labelStyle(label, "-", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "E/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19aFPL = new Text(line11, SWT.BORDER);
		label = new Label(line11, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.CENTER, SWT.CENTER, 20);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "P/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19bFPL = new Text(line11, SWT.BORDER);
		label = new Label(line11, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.CENTER, SWT.CENTER, 20);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "R/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		Composite cbtn19c = new Composite(line11, SWT.NONE); sh.compositeStyle(cbtn19c, 6, SWT.LEFT, SWT.CENTER);
		t19cUhfFPL = new Text(cbtn19c, SWT.BORDER);
		t19cUhfFPL.setCursor(cs1);
		t19cUhfFPL.setEditable(false);
		t19cUhfFPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19cUhfFPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19cUhfFPL.getText().compareTo("U")==0)
					t19cUhfFPL.setText("X");
				else t19cUhfFPL.setText("U");
				t19bFPL.setFocus();
			}
		});
		t19cUhfFPL.setText("U");
	    Label l_1 = new Label(cbtn19c,SWT.LEFT);
	    l_1.setText("UHF		");
		
		t19cVhfFPL = new Text(cbtn19c, SWT.BORDER);
		t19cVhfFPL.setCursor(cs1);
		t19cVhfFPL.setEditable(false);
		t19cVhfFPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19cVhfFPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19cVhfFPL.getText().compareTo("V")==0)
					t19cVhfFPL.setText("X");
				else t19cVhfFPL.setText("V");
				t19bFPL.setFocus();
			}
		});
		t19cVhfFPL.setText("V");
	    Label l_2 = new Label(cbtn19c,SWT.LEFT);
	    l_2.setText("VHF		");
	    
		t19cEltFPL = new Text(cbtn19c, SWT.BORDER);
		t19cEltFPL.setCursor(cs1);
		t19cEltFPL.setEditable(false);
		t19cEltFPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19cEltFPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19cEltFPL.getText().compareTo("E")==0)
					t19cEltFPL.setText("X");
				else t19cEltFPL.setText("E");
				t19bFPL.setFocus();
			}
		});
		t19cEltFPL.setText("E");
	    Label l_3 = new Label(cbtn19c,SWT.LEFT);
	    l_3.setText("ELT		");
		
		Composite line12 = new Composite(group, SWT.NONE); sh.compositeStyle(line12, 10, SWT.LEFT, SWT.CENTER);
		// type19de
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, lbl19d, 190, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 55, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, lbl19e, 80, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		// de
		Composite line122 = new Composite(group, SWT.NONE); sh.compositeStyle(line122, 10, SWT.LEFT, SWT.CENTER);
		label = new Label(line122, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
		t19sFPL = new Text(line122, SWT.BORDER);
		t19sFPL.setCursor(cs1);
		t19sFPL.setEditable(false);
		t19sFPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19sFPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if ((t19sFPL.getText().isEmpty())||(t19sFPL.getText().compareTo("S")==0)) {
					t19sFPL.setText("X");
					t19dPFPL.setText("X");
					t19dDFPL.setText("X");
					t19dMFPL.setText("X");
					t19dJFPL.setText("X");
				}
				else {
					t19sFPL.setText("S");
					t19dPFPL.setText("P");
					t19dDFPL.setText("D");
					t19dMFPL.setText("M");
					t19dJFPL.setText("J");
				}
				t19bFPL.setFocus();
			}
		});
		t19sFPL.setText("S");
		label = new Label(line122, SWT.NONE); sh.labelStyle(label, " / ", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);

		Composite cbtn19d = new Composite(line122, SWT.NONE); sh.compositeStyle(cbtn19d, 8, SWT.LEFT, SWT.CENTER);

		t19dPFPL = new Text(cbtn19d, SWT.BORDER);
		t19dPFPL.setCursor(cs1);
		t19dPFPL.setEditable(false);
		t19dPFPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19dPFPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19dPFPL.getText().compareTo("P")==0)
					t19dPFPL.setText("X");
				else if (t19sFPL.getText().compareTo("S")==0) t19dPFPL.setText("P");
				t19bFPL.setFocus();
			}
		});
		t19dPFPL.setText("P");
	    Label l_4 = new Label(cbtn19d,SWT.LEFT);
	    l_4.setText("POLAR	");
	    
	    t19dDFPL = new Text(cbtn19d, SWT.BORDER);
		t19dDFPL.setCursor(cs1);
		t19dDFPL.setEditable(false);
		t19dDFPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19dDFPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19dDFPL.getText().compareTo("D")==0)
					t19dDFPL.setText("X");
				else if (t19sFPL.getText().compareTo("S")==0) t19dDFPL.setText("D");
				t19bFPL.setFocus();
			}
		});
		t19dDFPL.setText("D");
	    Label l_5 = new Label(cbtn19d,SWT.LEFT);
	    l_5.setText("DESERT	");

		t19dMFPL = new Text(cbtn19d, SWT.BORDER);
		t19dMFPL.setCursor(cs1);
		t19dMFPL.setEditable(false);
		t19dMFPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19dMFPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19dMFPL.getText().compareTo("M")==0)
					t19dMFPL.setText("X");
				else if (t19sFPL.getText().compareTo("S")==0) t19dMFPL.setText("M");
				t19bFPL.setFocus();
			}
		});
		t19dMFPL.setText("M");
	    Label l_6 = new Label(cbtn19d,SWT.LEFT);
	    l_6.setText("MARITIME	");

		t19dJFPL = new Text(cbtn19d, SWT.BORDER);
		t19dJFPL.setCursor(cs1);
		t19dJFPL.setEditable(false);
		t19dJFPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19dJFPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19dJFPL.getText().compareTo("J")==0)
					t19dJFPL.setText("X");
				else if (t19sFPL.getText().compareTo("S")==0) t19dJFPL.setText("J");
				t19bFPL.setFocus();
			}
		});
		t19dJFPL.setText("J");
	    Label l_7 = new Label(cbtn19d,SWT.LEFT);
	    l_7.setText("JUNGLE		");

		label = new Label(line122, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.CENTER, SWT.CENTER, 20);
		//label = new Label(line12, SWT.NONE); sh.labelStyle(label, "J/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19jFPL = new Text(line122, SWT.BORDER);
		t19jFPL.setCursor(cs1);
		t19jFPL.setEditable(false);
		t19jFPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19jFPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if ((t19jFPL.getText().isEmpty())||(t19jFPL.getText().compareTo("J")==0)) {
					t19jFPL.setText("X");
					t19eLFPL.setText("X");
					t19eFFPL.setText("X");
					t19eUFPL.setText("X");
					t19eVFPL.setText("X");
				}
				else {
					t19jFPL.setText("J");
					t19eLFPL.setText("L");
					t19eFFPL.setText("F");
					t19eUFPL.setText("U");
					t19eVFPL.setText("V");
				}
				t19bFPL.setFocus();
			}
		});
		label = new Label(line122, SWT.NONE); sh.labelStyle(label, " / ", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t19jFPL.setText("J");
		
		Composite cbtn19e = new Composite(line122, SWT.NONE); sh.compositeStyle(cbtn19e, 8, SWT.LEFT, SWT.CENTER);

		t19eLFPL = new Text(cbtn19e, SWT.BORDER);
		t19eLFPL.setCursor(cs1);
		t19eLFPL.setEditable(false);
		t19eLFPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19eLFPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19eLFPL.getText().compareTo("L")==0)
					t19eLFPL.setText("X");
				else if (t19jFPL.getText().compareTo("J")==0) t19eLFPL.setText("L");
				t19bFPL.setFocus();
			}
		});
		t19eLFPL.setText("L");
	    Label l_8 = new Label(cbtn19e,SWT.LEFT);
	    l_8.setText("LIGHT	");
	    
		t19eFFPL = new Text(cbtn19e, SWT.BORDER);
		t19eFFPL.setCursor(cs1);
		t19eFFPL.setEditable(false);
		t19eFFPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19eFFPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19eFFPL.getText().compareTo("F")==0)
					t19eFFPL.setText("X");
				else if (t19jFPL.getText().compareTo("J")==0) t19eFFPL.setText("F");
				t19bFPL.setFocus();
			}
		});
		t19eFFPL.setText("F");
	    Label l_9 = new Label(cbtn19e,SWT.LEFT);
	    l_9.setText("FLOURES		");

		t19eUFPL = new Text(cbtn19e, SWT.BORDER);
		t19eUFPL.setCursor(cs1);
		t19eUFPL.setEditable(false);
		t19eUFPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19eUFPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19eUFPL.getText().compareTo("U")==0)
					t19eUFPL.setText("X");
				else if (t19jFPL.getText().compareTo("J")==0) t19eUFPL.setText("U");
				t19bFPL.setFocus();
			}
		});
		t19eUFPL.setText("U");
	    Label l_10 = new Label(cbtn19e,SWT.LEFT);
	    l_10.setText("UHF		");
	    
		t19eVFPL = new Text(cbtn19e, SWT.BORDER);
		t19eVFPL.setCursor(cs1);
		t19eVFPL.setEditable(false);
		t19eVFPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19eVFPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19eVFPL.getText().compareTo("V")==0)
					t19eVFPL.setText("X");
				else if (t19jFPL.getText().compareTo("J")==0) t19eVFPL.setText("V");
				t19bFPL.setFocus();
			}
		});
		t19eVFPL.setText("V");
	    Label l_11 = new Label(cbtn19e,SWT.LEFT);
	    l_11.setText("VHF	");		
	    
	    for (int i=0; i<2; i++) label = new Label(line122, SWT.NONE); 
		
		Composite line13 = new Composite(group, SWT.NONE); sh.compositeStyle(line13, 10, SWT.LEFT, SWT.CENTER);
		// type19f
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Composite clbl19f = new Composite(line13, SWT.NONE); sh.compositeStyle(clbl19f, 5, SWT.LEFT, SWT.CENTER);
		label = new Label(clbl19f, SWT.NONE); sh.labelStyle(label, lbl19fnum, 100, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(clbl19f, SWT.NONE); sh.labelStyle(label, "", 8, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(clbl19f, SWT.NONE); sh.labelStyle(label, lbl19fcap, 90, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(clbl19f, SWT.NONE); sh.labelStyle(label, "", 15, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(clbl19f, SWT.NONE); sh.labelStyle(label, lbl19fcov, 61, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, lbl19fcol, 70, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<3; i++) label = new Label(line13, SWT.NONE);
		
		// f
		Composite line133 = new Composite(group, SWT.NONE); sh.compositeStyle(line133, 10, SWT.LEFT, SWT.CENTER);
		label = new Label(line133, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);

		//label = new Label(line13, SWT.NONE); sh.labelStyle(label, "D/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19dFPL = new Text(line133, SWT.BORDER);
		t19dFPL.setCursor(cs1);
		t19dFPL.setEditable(false);
		t19dFPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19dFPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if ((t19dFPL.getText().isEmpty())||(t19dFPL.getText().compareTo("D")==0)) {
					t19dFPL.setText("X");
					t19NumFPL.setText("");
					t19CapFPL.setText("");
					t19ColFPL.setText("");
					t19cFPL.setText("X");
					t19NumFPL.setEnabled(false);
					t19CapFPL.setEnabled(false);
					t19ColFPL.setEnabled(false);
					t19cFPL.setEnabled(false);
				}
				else {
					t19dFPL.setText("D");
					t19cFPL.setText("C");
					t19NumFPL.setEnabled(true);
					t19CapFPL.setEnabled(true);
					t19ColFPL.setEnabled(true);
					t19cFPL.setEnabled(true);
				}
				t19bFPL.setFocus();
			}
		});
		t19dFPL.setText("D");
		label = new Label(line133, SWT.NONE); sh.labelStyle(label, " / " , 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);

		Composite ctxt19f = new Composite(line133, SWT.NONE); sh.compositeStyle(ctxt19f, 5, SWT.LEFT, SWT.CENTER);
		t19NumFPL = new Text(ctxt19f, SWT.BORDER);
		label = new Label(ctxt19f, SWT.NONE); sh.labelStyle(label, "", 77, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t19CapFPL = new Text(ctxt19f, SWT.BORDER);
		
		label = new Label(ctxt19f, SWT.NONE); sh.labelStyle(label, "", 27, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);

		label = new Label(ctxt19f, SWT.CENTER); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19cFPL = new Text(line133, SWT.BORDER);
		t19cFPL.setCursor(cs1);
		t19cFPL.setEditable(false);
		t19cFPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19cFPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if ((t19cFPL.getText().isEmpty())||(t19cFPL.getText().compareTo("C")==0)) {
					t19cFPL.setText("X");
					//t19ColFPL.setText("");
					//t19ColFPL.setEnabled(false);
				}
				else {
					t19cFPL.setText("C");
					//t19ColFPL.setEnabled(true);
				}
				t19bFPL.setFocus();
			}
		});
		t19cFPL.setText("X");
		
		label = new Label(line133, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line133, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.CENTER, SWT.CENTER, 20);
		t19ColFPL = new Text(line133, SWT.BORDER);
		label = new Label(line133, SWT.NONE); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
		//for (int i=0; i<2; i++) label = new Label(line133, SWT.NONE); 

		Composite line14 = new Composite(group, SWT.NONE); sh.compositeStyle(line14, 9, SWT.LEFT, SWT.CENTER);
		// type19g
		label = new Label(line14, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line14, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line14, SWT.NONE); sh.labelStyle(label, lbl19g, 310, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<6; i++) label = new Label(line14, SWT.NONE); 
		// g
		label = new Label(line14, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line14, SWT.NONE); sh.labelStyle(label, "A/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19AirFPL = new Text(line14, SWT.BORDER);
		for (int i=0; i<6; i++) label = new Label(line14, SWT.NONE); 

		Composite line15 = new Composite(group, SWT.NONE); sh.compositeStyle(line15, 10, SWT.LEFT, SWT.CENTER);
		// type19h
		label = new Label(line15, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line15, SWT.NONE); sh.labelStyle(label, lbl19h, 90, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line15, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<7; i++) label = new Label(line15, SWT.NONE); 
		// h
		
		Composite line155 = new Composite(group, SWT.NONE); sh.compositeStyle(line155, 10, SWT.LEFT, SWT.CENTER);
		label = new Label(line155, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
		//label = new Label(line15, SWT.NONE); sh.labelStyle(label, "N/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19nFPL = new Text(line155, SWT.BORDER);
		t19nFPL.setCursor(cs1);
		t19nFPL.setEditable(false);
		t19nFPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19nFPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if ((t19nFPL.getText().isEmpty())||(t19nFPL.getText().compareTo("N")==0)) {
					t19nFPL.setText("X");
					t19RemFPL.setEnabled(false);
					t19RemFPL.setText("");
				}
				else {
					t19nFPL.setText("N");
					t19RemFPL.setEnabled(true);
				}
				t19bFPL.setFocus();
			}
		});
		t19nFPL.setText("N");
		label = new Label(line155, SWT.NONE); sh.labelStyle(label, " / ", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);

		t19RemFPL = new Text(line155, SWT.BORDER);
		label = new Label(line155, SWT.NONE); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
		for (int i=0; i<5; i++) label = new Label(line155, SWT.NONE); 

		Composite line16 = new Composite(group, SWT.NONE); sh.compositeStyle(line16, 9, SWT.LEFT, SWT.CENTER);
		// type19i
		label = new Label(line16, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line16, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line16, SWT.NONE); sh.labelStyle(label, lbl19i, 180, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<6; i++) label = new Label(line16, SWT.NONE); 
		// i
		label = new Label(line16, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line16, SWT.NONE); sh.labelStyle(label, "C/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19PilFPL = new Text(line16, SWT.BORDER);
		label = new Label(line16, SWT.NONE); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
		for (int i=0; i<5; i++) label = new Label(line16, SWT.NONE); 

		Composite line17 = new Composite(group, SWT.NONE); sh.compositeStyle(line17, 1, SWT.LEFT, SWT.CENTER);
		// space
		label = new Label(line17, SWT.NONE); sh.labelStyle(label, lblspace, 300, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tSpace = new Text(line17, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);

		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbFPL = new Text(c, SWT.BORDER);
			
		addListenerFPL();
		acs.setCompFPL(t3aFPL, t3bFPL, t3cFPL, t7aFPL, t7bFPL, t7cFPL, t8aFPL, t8bFPL, t9aFPL, t9bFPL, t9cFPL, t10aFPL, t10bFPL, t13aFPL, t13bFPL, t15aFPL, t15bFPL, t15cFPL, t16aFPL, t16bFPL, t16cFPL, t16dFPL, tNavFPL, tComFPL, tDatFPL, tSurFPL, tDepFPL, tDestFPL, tDofFPL, tRegFPL, tEetFPL, tSelFPL, tTypFPL, tCodeFPL, tDleFPL, tOprFPL, tOrgnFPL, tPerFPL, tAltnFPL, tRaltFPL, tTaltFPL, tRifFPL, tRmkFPL, tPbnFPL, tStsFPL, t19aFPL, t19bFPL, t19cUhfFPL, t19cVhfFPL, t19cEltFPL, t19dPFPL, t19dDFPL, t19dMFPL, t19dJFPL, t19eLFPL, t19eFFPL, t19eUFPL, t19eVFPL, t19NumFPL, t19CapFPL, t19ColFPL, t19AirFPL, t19RemFPL, t19PilFPL, tSpace, tFbFPL);
	}
	
	public static void initNewDLA(Composite comp, final String ID) {

		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tDLA);
		Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 17, SWT.LEFT, SWT.TOP);
		Composite line2 = new Composite(group, SWT.NONE); sh.compositeStyle(line2, 13, SWT.LEFT, SWT.TOP);
		
		Label label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13a, 60, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE);

		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t3aDLA = new Text(line1, SWT.BORDER); t3aDLA.setText("DLA"); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t3bDLA = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		t3cDLA = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t7aDLA = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
		t7bDLA = new Button(line1, SWT.CHECK); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);  
		t7cDLA = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t13aDLA = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t13bDLA = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER);
			
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) {//terminated here
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl18dof, 70, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			for (int i=0; i<9; i++) label = new Label(line2, SWT.NONE);
			
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aDLA = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			tDofDLA = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			label = new Label(line2, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
			Composite type = new Composite(line2, SWT.NONE); sh.compositeStyle(type, 5, SWT.CENTER, SWT.CENTER);
			getFplDLA = new Button(type, SWT.PUSH);
			firstDLA = new Button(type, SWT.PUSH);
			prevDLA = new Button(type, SWT.PUSH);
			nextDLA = new Button(type, SWT.PUSH);
			lastDLA = new Button(type, SWT.PUSH);
			bGetFPL(getFplDLA, firstDLA, prevDLA, nextDLA, lastDLA, t7aDLA, ID);
			label = new Label(line2, SWT.NONE);//unvisible 
			t16bDLA = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT);
			t16cDLA = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT);
			t16dDLA = new Text(line2, SWT.BORDER);
			disableDEST(t16bDLA, t16cDLA, t16dDLA); 
		} else {
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16d, 100, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl18dof, 70, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			for (int i=0; i<3; i++) label = new Label(line2, SWT.NONE);

			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aDLA = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t16bDLA = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
			t16cDLA = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
			t16dDLA = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			tDofDLA = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			label = new Label(line2, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
			Composite type = new Composite(line2, SWT.NONE); sh.compositeStyle(type, 5, SWT.CENTER, SWT.CENTER);
			getFplDLA = new Button(type, SWT.PUSH);
			firstDLA = new Button(type, SWT.PUSH);
			prevDLA = new Button(type, SWT.PUSH);
			nextDLA = new Button(type, SWT.PUSH);
			lastDLA = new Button(type, SWT.PUSH);
			bGetFPL(getFplDLA, firstDLA, prevDLA, nextDLA, lastDLA, t7aDLA, ID);
		}
		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbDLA = new Text(c, SWT.BORDER);
		
		acs.setCompMiniFPL(t3aDLA, t3bDLA, t3cDLA, t7aDLA, t7bDLA, t7cDLA, t13aDLA, t13bDLA, t16aDLA, t16bDLA, t16cDLA, t16dDLA, tDofDLA, tFbDLA);
	
	}
	
	public static void initNewDLA_ASLI(Composite comp, final String ID) {
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tDLA);
		Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 17, SWT.LEFT, SWT.TOP);
		Composite line2 = new Composite(group, SWT.NONE); sh.compositeStyle(line2, 13, SWT.LEFT, SWT.TOP);
		
		Label label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13a, 60, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE);

		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t3aDLA = new Text(line1, SWT.BORDER); t3aDLA.setText("DLA"); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t3bDLA = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		t3cDLA = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t7aDLA = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
		t7bDLA = new Button(line1, SWT.CHECK); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);  
		t7cDLA = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t13aDLA = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t13bDLA = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER);
			
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16d, 100, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl18dof, 70, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		for (int i=0; i<3; i++) label = new Label(line2, SWT.NONE);

		label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t16aDLA = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t16bDLA = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
		t16cDLA = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
		t16dDLA = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		tDofDLA = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		label = new Label(line2, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
		Composite type = new Composite(line2, SWT.NONE); sh.compositeStyle(type, 5, SWT.CENTER, SWT.CENTER);
		getFplDLA = new Button(type, SWT.PUSH);
		firstDLA = new Button(type, SWT.PUSH);
		prevDLA = new Button(type, SWT.PUSH);
		nextDLA = new Button(type, SWT.PUSH);
		lastDLA = new Button(type, SWT.PUSH);
		bGetFPL(getFplDLA, firstDLA, prevDLA, nextDLA, lastDLA, t7aDLA, ID);

		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbDLA = new Text(c, SWT.BORDER);
	
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) {//terminated here
			disableDEST(t16bDLA, t16cDLA, t16dDLA); }
		
		acs.setCompMiniFPL(t3aDLA, t3bDLA, t3cDLA, t7aDLA, t7bDLA, t7cDLA, t13aDLA, t13bDLA, t16aDLA, t16bDLA, t16cDLA, t16dDLA, tDofDLA, tFbDLA);
	}
	
	public static void initNewCHG(Composite comp, final String ID) {
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tCHG);
		Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 17, SWT.LEFT, SWT.TOP);
		Composite line2 = new Composite(group, SWT.NONE); sh.compositeStyle(line2, 13, SWT.LEFT, SWT.TOP);
		Composite line3 = new Composite(group, SWT.NONE); sh.compositeStyle(line3, 4, SWT.LEFT, SWT.TOP);
		
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) {//terminated here
			Label label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13a, 60, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE);
			label = new Label(line1, SWT.NONE);// sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE);
	
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t3aCHG = new Text(line1, SWT.BORDER); t3aCHG.setText("CHG"); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t3bCHG = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			t3cCHG = new Text(line1, SWT.BORDER);
			// 7
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t7aCHG = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
			t7bCHG = new Button(line1, SWT.CHECK); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			t7cCHG = new Text(line1, SWT.BORDER);
			// 13
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t13aCHG = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t13bCHG = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.CENTER);
				
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl18dof, 70, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			for (int i=0; i<3; i++) label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE);// sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE);// sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE);// sh.labelStyle(label, lbl16d, 100, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aCHG = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			tDofCHG = new Text(line2, SWT.BORDER);
			for (int i=0; i<2; i++) label = new Label(line2, SWT.NONE); 
			Composite type = new Composite(line2, SWT.NONE); sh.compositeStyle(type, 5, SWT.LEFT, SWT.CENTER);
			getFplCHG = new Button(type, SWT.PUSH);
			firstCHG = new Button(type, SWT.PUSH);
			prevCHG = new Button(type, SWT.PUSH);
			nextCHG = new Button(type, SWT.PUSH);
			lastCHG = new Button(type, SWT.PUSH);
			bGetFPL(getFplCHG, firstCHG, prevCHG, nextCHG, lastCHG, t7aCHG, ID);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t16bCHG = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); //sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
			t16cCHG = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); //sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
			t16dCHG = new Text(line2, SWT.BORDER);
			
			label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl22, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			for (int i=0; i<2; i++) label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
			t22CHG = new Text(line3, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
			label = new Label(line3, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
			label = new Label(line3, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.TOP, SWT.LEFT, 30);
			
			disableDEP(t13bCHG);
			disableDEST(t16bCHG, t16cCHG, t16dCHG);
		} else {
			Label label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13a, 60, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE);

			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t3aCHG = new Text(line1, SWT.BORDER); t3aCHG.setText("CHG"); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t3bCHG = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			t3cCHG = new Text(line1, SWT.BORDER);
			// 7
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t7aCHG = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
			t7bCHG = new Button(line1, SWT.CHECK); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			t7cCHG = new Text(line1, SWT.BORDER);
			// 13
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t13aCHG = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t13bCHG = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.CENTER);
				
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16d, 100, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl18dof, 70, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			for (int i=0; i<3; i++) label = new Label(line2, SWT.NONE);

			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aCHG = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t16bCHG = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
			t16cCHG = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
			t16dCHG = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			tDofCHG = new Text(line2, SWT.BORDER);
			for (int i=0; i<2; i++) label = new Label(line2, SWT.NONE); 
			Composite type = new Composite(line2, SWT.NONE); sh.compositeStyle(type, 5, SWT.LEFT, SWT.CENTER);
			getFplCHG = new Button(type, SWT.PUSH);
			firstCHG = new Button(type, SWT.PUSH);
			prevCHG = new Button(type, SWT.PUSH);
			nextCHG = new Button(type, SWT.PUSH);
			lastCHG = new Button(type, SWT.PUSH);
			bGetFPL(getFplCHG, firstCHG, prevCHG, nextCHG, lastCHG, t7aCHG, ID);
			
			label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl22, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			for (int i=0; i<2; i++) label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
			t22CHG = new Text(line3, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
			label = new Label(line3, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
			label = new Label(line3, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.TOP, SWT.LEFT, 30);
		}
		
		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbCHG = new Text(c, SWT.BORDER);
			
		acs.setCompCHG(t3aCHG, t3bCHG, t3cCHG, t7aCHG, t7bCHG, t7cCHG, t13aCHG, t13bCHG, t16aCHG, t16bCHG, t16cCHG, t16dCHG, tDofCHG, t22CHG, tFbCHG);
	}

	static void disableDEP(Text t1) {
//		t1.setEnabled(false);
		t1.setVisible(false);
	}

	static void disableDEST(Text t1,Text t2,Text t3) {
//		t1.setEnabled(false);
//		t2.setEnabled(false);
//		t3.setEnabled(false);
		
		t1.setVisible(false);
		t2.setVisible(false);
		t3.setVisible(false);
	}
	
	public static void initNewCNL(Composite comp, final String ID) {
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tCNL);
		Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 17, SWT.LEFT, SWT.TOP);
		Composite line2 = new Composite(group, SWT.NONE); sh.compositeStyle(line2, 13, SWT.LEFT, SWT.TOP);
		
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) {//terminated here
			Label label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13a, 60, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE);
			label = new Label(line1, SWT.NONE); //sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE);
	
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t3aCNL = new Text(line1, SWT.BORDER); t3aCNL.setText("CNL"); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t3bCNL = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			t3cCNL = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t7aCNL = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
			t7bCNL = new Button(line1, SWT.CHECK); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);  
			t7cCNL = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t13aCNL = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t13bCNL = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.CENTER);
				
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl18dof, 70, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			for (int i=0; i<3; i++) label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); //sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); //sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); //sh.labelStyle(label, lbl16d, 100, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aCNL = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			tDofCNL = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			label = new Label(line2, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
			Composite type = new Composite(line2, SWT.NONE); sh.compositeStyle(type, 5, SWT.CENTER, SWT.CENTER);
			getFplCNL = new Button(type, SWT.PUSH);
			firstCNL = new Button(type, SWT.PUSH);
			prevCNL = new Button(type, SWT.PUSH);
			nextCNL = new Button(type, SWT.PUSH);
			lastCNL = new Button(type, SWT.PUSH);
			bGetFPL(getFplCNL, firstCNL, prevCNL, nextCNL, lastCNL, t7aCNL, ID);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t16bCNL = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT);// sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
			t16cCNL = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT);// sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
			t16dCNL = new Text(line2, SWT.BORDER);
			
			disableDEP(t13bCNL);
			disableDEST(t16bCNL, t16cCNL, t16dCNL); 
		} else {
			Label label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13a, 60, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE);

			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t3aCNL = new Text(line1, SWT.BORDER); t3aCNL.setText("CNL"); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t3bCNL = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			t3cCNL = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t7aCNL = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
			t7bCNL = new Button(line1, SWT.CHECK); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);  
			t7cCNL = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t13aCNL = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t13bCNL = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.CENTER);
				
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16d, 100, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl18dof, 70, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			for (int i=0; i<3; i++) label = new Label(line2, SWT.NONE);

			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aCNL = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t16bCNL = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
			t16cCNL = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
			t16dCNL = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			tDofCNL = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			label = new Label(line2, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
			Composite type = new Composite(line2, SWT.NONE); sh.compositeStyle(type, 5, SWT.CENTER, SWT.CENTER);
			getFplCNL = new Button(type, SWT.PUSH);
			firstCNL = new Button(type, SWT.PUSH);
			prevCNL = new Button(type, SWT.PUSH);
			nextCNL = new Button(type, SWT.PUSH);
			lastCNL = new Button(type, SWT.PUSH);
			bGetFPL(getFplCNL, firstCNL, prevCNL, nextCNL, lastCNL, t7aCNL, ID);
		}
		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbCNL = new Text(c, SWT.BORDER);

		acs.setCompMiniFPL(t3aCNL, t3bCNL, t3cCNL, t7aCNL, t7bCNL, t7cCNL, t13aCNL, t13bCNL, t16aCNL, t16bCNL, t16cCNL, t16dCNL, tDofCNL, tFbCNL);
	}
	
	public static void initNewDEP(Composite comp, final String ID) {
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tDEP);
		Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 17, SWT.LEFT, SWT.TOP);
		Composite line2 = new Composite(group, SWT.NONE); sh.compositeStyle(line2, 13, SWT.LEFT, SWT.TOP);
		
		Label label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13a, 60, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE);

		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t3aDEP = new Text(line1, SWT.BORDER); t3aDEP.setText("DEP"); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t3bDEP = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		t3cDEP = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t7aDEP = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
		t7bDEP = new Button(line1, SWT.CHECK); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);  
		t7cDEP = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t13aDEP = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Composite ctxt17b = new Composite(line1, SWT.NONE); sh.compositeStyle(ctxt17b, 2, SWT.LEFT, SWT.TOP);
		t13bDEP = new Text(ctxt17b, SWT.BORDER);
		bTimeDEP = new Button(ctxt17b, SWT.PUSH); bTime(line1, bTimeDEP, t13bDEP);
		label = new Label(line1, SWT.CENTER);
		
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) {//terminated here
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl18dof, 70, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			for (int i=0; i<9; i++) label = new Label(line2, SWT.NONE);

			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aDEP = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			tDofDEP = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			label = new Label(line2, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
			Composite type = new Composite(line2, SWT.NONE); sh.compositeStyle(type, 5, SWT.CENTER, SWT.CENTER);
			getFplDEP = new Button(type, SWT.PUSH);
			firstDEP = new Button(type, SWT.PUSH);
			prevDEP = new Button(type, SWT.PUSH);
			nextDEP = new Button(type, SWT.PUSH);
			lastDEP = new Button(type, SWT.PUSH);
			bGetFPL(getFplDEP, firstDEP, prevDEP, nextDEP, lastDEP, t7aDEP, ID);
			label = new Label(line2, SWT.NONE); //unvisible
			t16bDEP = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT);
			t16cDEP = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT);
			t16dDEP = new Text(line2, SWT.BORDER);
			
			disableDEST(t16bDEP, t16cDEP, t16dDEP);
		} else {
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16d, 100, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl18dof, 70, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			for (int i=0; i<3; i++) label = new Label(line2, SWT.NONE);
	
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aDEP = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t16bDEP = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
			t16cDEP = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
			t16dDEP = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			tDofDEP = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			label = new Label(line2, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
			Composite type = new Composite(line2, SWT.NONE); sh.compositeStyle(type, 5, SWT.CENTER, SWT.CENTER);
			getFplDEP = new Button(type, SWT.PUSH);
			firstDEP = new Button(type, SWT.PUSH);
			prevDEP = new Button(type, SWT.PUSH);
			nextDEP = new Button(type, SWT.PUSH);
			lastDEP = new Button(type, SWT.PUSH);
			bGetFPL(getFplDEP, firstDEP, prevDEP, nextDEP, lastDEP, t7aDEP, ID);
		}
		
		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbDEP = new Text(c, SWT.BORDER);
					
		acs.setCompMiniFPL(t3aDEP, t3bDEP, t3cDEP, t7aDEP, t7bDEP, t7cDEP, t13aDEP, t13bDEP, t16aDEP, t16bDEP, t16cDEP, t16dDEP, tDofDEP, tFbDEP);
	}
	
	public static void initNewARR(Composite comp, final String ID) {
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tARR);
		Label label;
		
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 17, SWT.LEFT, SWT.CENTER);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13a, 60, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE);

			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t3aARR = new Text(line1, SWT.BORDER); t3aARR.setText("ARR"); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t3bARR = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			t3cARR = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t7aARR = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
			t7bARR = new Button(line1, SWT.CHECK); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			t7cARR = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t13aARR = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE); //unvisible
			t13bARR = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE);
			disableDEP(t13bARR); 
		} else {
			Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 17, SWT.LEFT, SWT.CENTER);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13a, 60, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE);

			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t3aARR = new Text(line1, SWT.BORDER); t3aARR.setText("ARR"); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t3bARR = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			t3cARR = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t7aARR = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
			t7bARR = new Button(line1, SWT.CHECK); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			t7cARR = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t13aARR = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t13bARR = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE);
		}
		
		Composite line2 = new Composite(group, SWT.NONE); sh.compositeStyle(line2, 9, SWT.LEFT, SWT.CENTER);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl17a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl17b, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl17c, 240, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<3; i++) label = new Label(line2, SWT.NONE);
		// 17
		label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		t17aARR = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Composite ctxt17b = new Composite(line2, SWT.NONE); sh.compositeStyle(ctxt17b, 2, SWT.LEFT, SWT.TOP);
		t17bARR = new Text(ctxt17b, SWT.BORDER);
		bTimeARR = new Button(ctxt17b, SWT.PUSH); bTime(line2, bTimeARR, t17bARR);
		label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.TOP, 20);
		t17cARR = new Text(line2, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line2, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		label = new Label(line2, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.TOP, 30);
//		for (int i=0; i<4; i++) label = new Label(line2, SWT.NONE);
		Composite type = new Composite(line2, SWT.NONE); sh.compositeStyle(type, 5, SWT.LEFT, SWT.TOP);
		getFplARR = new Button(type, SWT.PUSH);
		firstARR = new Button(type, SWT.PUSH);
		prevARR = new Button(type, SWT.PUSH);
		nextARR = new Button(type, SWT.PUSH);
		lastARR = new Button(type, SWT.PUSH);
		bGetFPL(getFplARR, firstARR, prevARR, nextARR, lastARR, t7aARR, ID);

		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbARR = new Text(c, SWT.BORDER);
		
		acs.setCompARR(t3aARR, t3bARR, t3cARR, t7aARR, t7bARR, t7cARR, t13aARR, t13bARR, t17aARR, t17bARR, t17cARR, tFbARR);
	}
	
	public static void initNewCDN(Composite comp, final String ID) {
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tCDN);
		Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 17, SWT.LEFT, SWT.CENTER);
	
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) {//terminated here
			Label label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13a, 60, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE);
			label = new Label(line1, SWT.NONE);// sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE);
	
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t3aCDN = new Text(line1, SWT.BORDER); t3aCDN.setText("CDN"); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t3bCDN = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			t3cCDN = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t7aCDN = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
			t7bCDN = new Button(line1, SWT.CHECK); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			t7cCDN = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t13aCDN = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t13bCDN = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.CENTER);
				
			Composite line2 = new Composite(group, SWT.NONE); sh.compositeStyle(line2, 9, SWT.LEFT, SWT.CENTER);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); //sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); //sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); //sh.labelStyle(label, lbl16d, 100, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aCDN = new Text(line2, SWT.BORDER);
			Composite type = new Composite(line2, SWT.NONE); sh.compositeStyle(type, 5, SWT.LEFT, SWT.CENTER);
			getFplCDN = new Button(type, SWT.PUSH);
			firstCDN = new Button(type, SWT.PUSH);
			prevCDN = new Button(type, SWT.PUSH);
			nextCDN = new Button(type, SWT.PUSH);
			lastCDN = new Button(type, SWT.PUSH);
			bGetFPL(getFplCDN, firstCDN, prevCDN, nextCDN, lastCDN, t7aCDN, ID);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t16bCDN = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); //sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
			t16cCDN = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); //sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
			t16dCDN = new Text(line2, SWT.BORDER);
			
			Composite line3 = new Composite(group, SWT.NONE); sh.compositeStyle(line3, 4, SWT.LEFT, SWT.CENTER);
			label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl22, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			for (int i=0; i<2; i++) label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
			t22CDN = new Text(line3, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
			label = new Label(line3, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
			label = new Label(line3, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.TOP, SWT.LEFT, 30);
			
			disableDEP(t13bCDN);
			disableDEST(t16bCDN, t16cCDN, t16dCDN); 
		} else {
			Label label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			label = new Label(line1, SWT.NONE); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13a, 60, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line1, SWT.NONE);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line1, SWT.NONE);
	
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t3aCDN = new Text(line1, SWT.BORDER); t3aCDN.setText("CDN"); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t3bCDN = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			t3cCDN = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t7aCDN = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
			t7bCDN = new Button(line1, SWT.CHECK); 
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
			t7cCDN = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t13aCDN = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t13bCDN = new Text(line1, SWT.BORDER);
			label = new Label(line1, SWT.CENTER);
				
			Composite line2 = new Composite(group, SWT.NONE); sh.compositeStyle(line2, 9, SWT.LEFT, SWT.CENTER);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16d, 100, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
	
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aCDN = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t16bCDN = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
			t16cCDN = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
			t16dCDN = new Text(line2, SWT.BORDER);
			Composite type = new Composite(line2, SWT.NONE); sh.compositeStyle(type, 5, SWT.LEFT, SWT.CENTER);
			getFplCDN = new Button(type, SWT.PUSH);
			firstCDN = new Button(type, SWT.PUSH);
			prevCDN = new Button(type, SWT.PUSH);
			nextCDN = new Button(type, SWT.PUSH);
			lastCDN = new Button(type, SWT.PUSH);
			bGetFPL(getFplCDN, firstCDN, prevCDN, nextCDN, lastCDN, t7aCDN, ID);
			
			Composite line3 = new Composite(group, SWT.NONE); sh.compositeStyle(line3, 4, SWT.LEFT, SWT.CENTER);
			label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl22, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			for (int i=0; i<2; i++) label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
			t22CDN = new Text(line3, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
			label = new Label(line3, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
			label = new Label(line3, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.TOP, SWT.LEFT, 30);
		}
		
		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbCDN = new Text(c, SWT.BORDER);
	
		acs.setCompCDN(t3aCDN, t3bCDN, t3cCDN, t7aCDN, t7bCDN, t7cCDN, t13aCDN, t13bCDN, t16aCDN, t16bCDN, t16cCDN, t16dCDN, t22CDN, tFbCDN);
	}
	
	public static void initNewCPL(Composite comp, final String ID) {
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tCPL);
		Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 17, SWT.LEFT, SWT.CENTER);
		Label label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 60, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 80, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl8a, 65, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl8b, 65, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t3aCPL = new Text(line1, SWT.BORDER); t3aCPL.setText("CPL"); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t3bCPL = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		t3cCPL = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t7aCPL = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
		t7bCPL = new Button(line1, SWT.CHECK); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		t7cCPL = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		Composite comp8a = new Composite(line1, SWT.NONE); sh.compositeStyle(comp8a, 2, SWT.LEFT, SWT.CENTER);
		t8aCPL = new Text(comp8a, SWT.BORDER);
		b8aCPL = new Button(comp8a, SWT.PUSH); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);  
		Composite comp8b = new Composite(line1, SWT.NONE); sh.compositeStyle(comp8b, 3, SWT.LEFT, SWT.CENTER);
		t8bCPL = new Text(comp8b, SWT.BORDER);
		b8bCPL = new Button(comp8b, SWT.PUSH); 
		label = new Label(comp8b, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
		
		Composite line3 = new Composite(group, SWT.NONE); sh.compositeStyle(line3, 8, SWT.LEFT, SWT.CENTER);
		label = new Label(line3, SWT.NONE);
		label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl9a, 110, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line3, SWT.NONE);
		label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl9b, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line3, SWT.NONE);
		label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl9c, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line3, SWT.NONE);
		label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl10, 260, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		
		label = new Label(line3, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t9aCPL = new Text(line3, SWT.BORDER);
		label = new Label(line3, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t9bCPL = new Text(line3, SWT.BORDER);
		label = new Label(line3, SWT.CENTER); sh.labelStyle(label, "/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		Composite comp9c = new Composite(line3, SWT.NONE); sh.compositeStyle(comp9c, 3, SWT.LEFT, SWT.CENTER);
		t9cCPL = new Text(comp9c, SWT.BORDER);
		b9cCPL = new Button(comp9c, SWT.PUSH); 
		bSave9CPL = new Button(comp9c, SWT.PUSH); bSave9CPL.setEnabled(false);
		label = new Label(line3, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		Composite comp10 = new Composite(line3, SWT.NONE); sh.compositeStyle(comp10, 7, SWT.LEFT, SWT.CENTER);
		t10aCPL = new Text(comp10, SWT.BORDER);
		btnChkCPL = new Button(comp10, SWT.PUSH);
		b10aCPL = new Button(comp10, SWT.PUSH);
		label = new Label(comp10, SWT.CENTER);  sh.labelStyle(label, "/", 14, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t10bCPL = new Text(comp10, SWT.BORDER);
		b10bCPL = new Button(comp10, SWT.PUSH);
		label = new Label(comp10, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.CENTER, SWT.CENTER, 30);
		
		Composite line4 = new Composite(group, SWT.NONE); sh.compositeStyle(line4, 16, SWT.LEFT, SWT.CENTER);
		label = new Label(line4, SWT.NONE);
		label = new Label(line4, SWT.NONE); sh.labelStyle(label, lbl13a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line4, SWT.NONE);
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			label = new Label(line4, SWT.NONE); sh.labelStyle(label, "", 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);	
		} else {
			label = new Label(line4, SWT.NONE); sh.labelStyle(label, lbl13b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		}
//		label = new Label(line4, SWT.NONE); sh.labelStyle(label, lbl13b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line4, SWT.NONE);
		label = new Label(line4, SWT.NONE); sh.labelStyle(label, lbl14a, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line4, SWT.NONE);
		label = new Label(line4, SWT.NONE); sh.labelStyle(label, lbl14b, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line4, SWT.NONE);
		label = new Label(line4, SWT.NONE); sh.labelStyle(label, lbl14c, 70, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line4, SWT.NONE);
		label = new Label(line4, SWT.NONE); sh.labelStyle(label, lbl14d, 125, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line4, SWT.NONE); sh.labelStyle(label, lbl14e, 80, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<3; i++) label = new Label(line4, SWT.NONE);
		label = new Label(line4, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t13aCPL = new Text(line4, SWT.BORDER);
		label = new Label(line4, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t13bCPL = new Text(line4, SWT.BORDER);
		label = new Label(line4, SWT.RIGHT); sh.labelStyle(label, "-", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t14aCPL = new Text(line4, SWT.BORDER);
		label = new Label(line4, SWT.RIGHT); sh.labelStyle(label, "/", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t14bCPL = new Text(line4, SWT.BORDER);
		label = new Label(line4, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t14cCPL = new Text(line4, SWT.BORDER);
		label = new Label(line4, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t14dCPL = new Text(line4, SWT.BORDER);
		Composite comp14e = new Composite(line4, SWT.NONE); sh.compositeStyle(comp14e, 13, SWT.LEFT, SWT.CENTER);
		t14eCPL = new Text(comp14e, SWT.BORDER);
		label = new Label(comp14e, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
		for (int i=0; i<3; i++) label = new Label(line4, SWT.NONE);
		
		Composite line5 = new Composite(group, SWT.NONE); sh.compositeStyle(line5, 13, SWT.LEFT, SWT.CENTER);
		label = new Label(line5, SWT.NONE);
		label = new Label(line5, SWT.NONE); sh.labelStyle(label, lbl15a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line5, SWT.NONE);
		label = new Label(line5, SWT.NONE); sh.labelStyle(label, lbl15b, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line5, SWT.NONE);
		label = new Label(line5, SWT.NONE); sh.labelStyle(label, lbl15c, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		for (int i=0; i<7; i++) label = new Label(line5, SWT.NONE);
		label = new Label(line5, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		t15aCPL = new Text(line5, SWT.BORDER);
		label = new Label(line5, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		t15bCPL = new Text(line5, SWT.BORDER);
		label = new Label(line5, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.TOP, 20); 
		t15cCPL = new Text(line5, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line5, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.TOP, 30); 
		Composite line5b = new Composite(line5, SWT.NONE); sh.compositeStyle(line5b, 6, SWT.LEFT, SWT.TOP);
		bSave15CPL = new Button(line5b, SWT.PUSH); bSave15CPL.setEnabled(false); 
		getRouteCPL = new Button(line5b, SWT.PUSH);
		firstCPL = new Button(line5b, SWT.PUSH);
		prevCPL = new Button(line5b, SWT.PUSH);
		nextCPL = new Button(line5b, SWT.PUSH);
		lastCPL = new Button(line5b, SWT.PUSH);
		
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			Composite line6 = new Composite(group, SWT.NONE); sh.compositeStyle(line6, 6, SWT.LEFT, SWT.CENTER);
			label = new Label(line6, SWT.NONE);
			label = new Label(line6, SWT.NONE); sh.labelStyle(label, lbl16a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line6, SWT.NONE);
			for (int i=0; i<3; i++) label = new Label(line6, SWT.NONE);
			
			label = new Label(line6, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aCPL = new Text(line6, SWT.BORDER);
			label = new Label(line6, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
			t16bCPL = new Text(line6, SWT.BORDER);
			t16cCPL = new Text(line6, SWT.BORDER);
			t16dCPL = new Text(line6, SWT.BORDER);
			
		} else {
			Composite line6 = new Composite(group, SWT.NONE); sh.compositeStyle(line6, 13, SWT.LEFT, SWT.CENTER);
			label = new Label(line6, SWT.NONE);
			label = new Label(line6, SWT.NONE); sh.labelStyle(label, lbl16a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line6, SWT.NONE);
			label = new Label(line6, SWT.NONE); sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line6, SWT.NONE);
			label = new Label(line6, SWT.NONE); sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line6, SWT.NONE);
			label = new Label(line6, SWT.NONE); sh.labelStyle(label, lbl16d, 80, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			for (int i=0; i<5; i++) label = new Label(line6, SWT.NONE);
			label = new Label(line6, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aCPL = new Text(line6, SWT.BORDER);
			label = new Label(line6, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t16bCPL = new Text(line6, SWT.BORDER);
			label = new Label(line6, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
			t16cCPL = new Text(line6, SWT.BORDER);
			label = new Label(line6, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
			Composite comp16d = new Composite(line6, SWT.NONE); sh.compositeStyle(comp16d, 2, SWT.LEFT, SWT.CENTER);
			t16dCPL = new Text(comp16d, SWT.BORDER);
			label = new Label(comp16d, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
			for (int i=0; i<5; i++) label = new Label(line6, SWT.NONE);
		}
		
		
		// type18
		Composite line7 = new Composite(group, SWT.NONE); sh.compositeStyle(line7, 2, SWT.LEFT, SWT.CENTER);
		label = new Label(line7, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line7, SWT.NONE); sh.labelStyle(label, lbl18, 210, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Composite line8 = new Composite(group, SWT.NONE); sh.compositeStyle(line8, 11, SWT.LEFT, SWT.CENTER);
		label = new Label(line8, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "STS/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compSts = new Composite(line8, SWT.NONE); sh.compositeStyle(compSts, 2, SWT.LEFT, SWT.TOP);
		tStsCPL = new Text(compSts, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		bSTSCPL = new Button(compSts, SWT.PUSH);
		
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "PBN/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compPbn = new Composite(line8, SWT.NONE); sh.compositeStyle(compPbn, 2, SWT.LEFT, SWT.TOP);
		tPbnCPL = new Text(compPbn, SWT.BORDER);
		bPBNCPL = new Button(compPbn, SWT.PUSH);
		
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "NAV/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tNavCPL = new Text(line8, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//--------------
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "COM/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compCom = new Composite(line8, SWT.NONE); sh.compositeStyle(compCom, 1, SWT.CENTER, SWT.TOP);
		tComCPL = new Text(compCom, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DAT/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compDat = new Composite(line8, SWT.NONE); sh.compositeStyle(compDat, 1, SWT.CENTER, SWT.TOP);
		tDatCPL = new Text(compDat, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "SUR/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tSurCPL = new Text(line8, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DEP/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compDep = new Composite(line8, SWT.NONE); sh.compositeStyle(compDep, 1, SWT.CENTER, SWT.TOP);
		tDepCPL = new Text(compDep, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DEST/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compDest = new Composite(line8, SWT.NONE); sh.compositeStyle(compDest, 1, SWT.CENTER, SWT.TOP);
		tDestCPL = new Text(compDest, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DOF/", 50, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.MANDATORY);
		tDofCPL = new Text(line8, SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "REG/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compReg = new Composite(line8, SWT.NONE); sh.compositeStyle(compReg, 1, SWT.LEFT, SWT.TOP);
		tRegCPL = new Text(compReg, SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "EET/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compEet = new Composite(line8, SWT.NONE); sh.compositeStyle(compEet, 1, SWT.CENTER, SWT.TOP);
		tEetCPL = new Text(compEet, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "SEL/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tSelCPL = new Text(line8, SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "TYP/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compTyp = new Composite(line8, SWT.NONE); sh.compositeStyle(compTyp, 1, SWT.CENTER, SWT.TOP);
		tTypCPL = new Text(compTyp, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "CODE/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compCode = new Composite(line8, SWT.NONE); sh.compositeStyle(compCode, 1, SWT.LEFT, SWT.TOP);
		tCodeCPL = new Text(compCode, SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DLE/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tDleCPL = new Text(line8, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "OPR/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compOpr = new Composite(line8, SWT.NONE); sh.compositeStyle(compOpr, 1, SWT.CENTER, SWT.TOP);
		tOprCPL = new Text(compOpr, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "ORGN/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compOrgn = new Composite(line8, SWT.NONE); sh.compositeStyle(compOrgn, 1, SWT.CENTER, SWT.TOP);
		tOrgnCPL = new Text(compOrgn, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "PER/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tPerCPL = new Text(line8, SWT.BORDER); 
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "ALTN/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compAltn = new Composite(line8, SWT.NONE); sh.compositeStyle(compAltn, 1, SWT.CENTER, SWT.TOP);
		tAltnCPL = new Text(compAltn, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "RALT/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compRalt = new Composite(line8, SWT.NONE); sh.compositeStyle(compRalt, 1, SWT.CENTER, SWT.TOP);
		tRaltCPL = new Text(compRalt, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "TALT/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tTaltCPL = new Text(line8, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "RIF/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compRif = new Composite(line8, SWT.NONE); sh.compositeStyle(compRif, 1, SWT.CENTER, SWT.TOP);
		tRifCPL = new Text(compRif, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "RMK/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compRmk = new Composite(line8, SWT.NONE); sh.compositeStyle(compRmk, 1, SWT.CENTER, SWT.TOP);
		tRmkCPL = new Text(compRmk, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		Composite compSym = new Composite(line8, SWT.NONE); sh.compositeStyle(compSym, 2, SWT.CENTER, SWT.TOP);
		label = new Label(compSym, SWT.NONE); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		label = new Label(compSym, SWT.NONE); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.TOP, 30);
		for (int i=0; i < 4; i++) label = new Label(line8, SWT.NONE); 

		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbCPL = new Text(c, SWT.BORDER);
	
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			disableDEP(t13bCPL);
			disableDEST(t16bCPL, t16cCPL, t16dCPL); }
		
		addListenerCPL();
		acs.setCompCPL(t3aCPL, t3bCPL, t3cCPL, t7aCPL, t7bCPL, t7cCPL, t8aCPL, t8bCPL, t9aCPL, t9bCPL, t9cCPL, t10aCPL, t10bCPL, t13aCPL, t13bCPL, t14aCPL, t14bCPL, t14cCPL, t14dCPL, t14eCPL, t15aCPL, t15bCPL, t15cCPL, t16aCPL, t16bCPL, t16cCPL, t16dCPL, tNavCPL, tComCPL, tDatCPL, tSurCPL, tDepCPL, tDestCPL, tDofCPL, tRegCPL, tEetCPL, tSelCPL, tTypCPL, tCodeCPL, tDleCPL, tOprCPL, tOrgnCPL, tPerCPL, tAltnCPL, tRaltCPL, tTaltCPL, tRifCPL, tRmkCPL, tPbnCPL, tStsCPL, tFbCPL);
	}
	
	//------------------------------------------------------------------------------------------------------------------------
	public static void setDateNow(Text t) {
		String DATE_FORMAT_NOW = "yyMMdd";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		t.setText(sdf.format(cal.getTime()));
//		System.out.println("tg0:"+sdf.format(cal.getTime()));
	}
	
	static void addButton(final String ID,Button b8a,Button b8b,Button b9c,Button b10a,Button btnChk,Button b10b,Button bSTS,Button bPBN,
			final Button bSave9,final Button bSave15,final Button getRoute,final Button first,final Button prev,final Button next,
			final Button last) {
		sh.buttonStyle(b8a, "", "Choose Flight Rules Value", bs.widthNavBtn, bs.colorBtn, SWT.CENTER, SWT.LEFT, Images.img_LIST);
		sh.buttonStyle(b8b, "", "Choose Type of Flight Value", bs.widthNavBtn, bs.colorBtn, SWT.CENTER, SWT.LEFT, Images.img_LIST);
		sh.buttonStyle(b9c, "", "Choose Wake Turb. Cat. Value", bs.widthNavBtn, bs.colorBtn, SWT.CENTER, SWT.LEFT, Images.img_LIST);
		listener10(ID, b10a, b10b, btnChk);
		listenerSTSPBN(ID, bPBN, bSTS);
		sh.buttonStyle(bSave9, "", "Save/Update Type of Aircraft criteria", bs.widthNavBtn, bs.colorBtn, SWT.CENTER, SWT.LEFT, Images.img_save16);
		sh.buttonStyle(bSave15, "", "Save/Update Route criteria", bs.widthNavBtn, bs.colorBtn, SWT.CENTER, SWT.LEFT, Images.img_save16);
		
		if (ID.compareToIgnoreCase("ALR")==0) { 
			shl=Shells.sh_nALR; t9b=t9bALR; t9c=t9cALR; 
			t13a=t13aALR; t16a=t16aALR; t15c=t15cALR;
			tDest1=HeaderComposite.tDest1ALR;
			tDest2=HeaderComposite.tDest2ALR;
			tDest3=HeaderComposite.tDest3ALR;
			tDest4=HeaderComposite.tDest4ALR;
			tDest5=HeaderComposite.tDest5ALR;
			tDest6=HeaderComposite.tDest6ALR;
			tDest7=HeaderComposite.tDest7ALR;
			tDest8=HeaderComposite.tDest8ALR;
			tDest9=HeaderComposite.tDest9ALR;
			tDest10=HeaderComposite.tDest10ALR;
			tDest11=HeaderComposite.tDest11ALR;
			tDest12=HeaderComposite.tDest12ALR;
			tDest13=HeaderComposite.tDest13ALR;
			tDest14=HeaderComposite.tDest14ALR;
			tDest15=HeaderComposite.tDest15ALR;
			tDest16=HeaderComposite.tDest16ALR;
			tDest17=HeaderComposite.tDest17ALR;
			tDest18=HeaderComposite.tDest18ALR;
			tDest19=HeaderComposite.tDest19ALR;
			tDest20=HeaderComposite.tDest20ALR;
			tDest21=HeaderComposite.tDest21ALR;

		} else if (ID.compareToIgnoreCase("FPL")==0) { 
			shl=Shells.sh_nFPL; t9b=t9bFPL; t9c=t9cFPL; 
			t13a=t13aFPL; t16a=t16aFPL; t15c=t15cFPL;
			tDest1=HeaderComposite.tDest1FPL;
			tDest2=HeaderComposite.tDest2FPL;
			tDest3=HeaderComposite.tDest3FPL;
			tDest4=HeaderComposite.tDest4FPL;
			tDest5=HeaderComposite.tDest5FPL;
			tDest6=HeaderComposite.tDest6FPL;
			tDest7=HeaderComposite.tDest7FPL;
			tDest8=HeaderComposite.tDest8FPL;
			tDest9=HeaderComposite.tDest9FPL;
			tDest10=HeaderComposite.tDest10FPL;
			tDest11=HeaderComposite.tDest11FPL;
			tDest12=HeaderComposite.tDest12FPL;
			tDest13=HeaderComposite.tDest13FPL;
			tDest14=HeaderComposite.tDest14FPL;
			tDest15=HeaderComposite.tDest15FPL;
			tDest16=HeaderComposite.tDest16FPL;
			tDest17=HeaderComposite.tDest17FPL;
			tDest18=HeaderComposite.tDest18FPL;
			tDest19=HeaderComposite.tDest19FPL;
			tDest20=HeaderComposite.tDest20FPL;
			tDest21=HeaderComposite.tDest21FPL;
			
		} else if (ID.compareToIgnoreCase("CPL")==0) { 
			shl=Shells.sh_nCPL; t9b=t9bCPL; t9c=t9cCPL; 
			t13a=t13aCPL; t16a=t16aCPL; t15c=t15cCPL;
			tDest1=HeaderComposite.tDest1CPL;
			tDest2=HeaderComposite.tDest2CPL;
			tDest3=HeaderComposite.tDest3CPL;
			tDest4=HeaderComposite.tDest4CPL;
			tDest5=HeaderComposite.tDest5CPL;
			tDest6=HeaderComposite.tDest6CPL;
			tDest7=HeaderComposite.tDest7CPL;
			tDest8=HeaderComposite.tDest8CPL;
			tDest9=HeaderComposite.tDest9CPL;
			tDest10=HeaderComposite.tDest10CPL;
			tDest11=HeaderComposite.tDest11CPL;
			tDest12=HeaderComposite.tDest12CPL;
			tDest13=HeaderComposite.tDest13CPL;
			tDest14=HeaderComposite.tDest14CPL;
			tDest15=HeaderComposite.tDest15CPL;
			tDest16=HeaderComposite.tDest16CPL;
			tDest17=HeaderComposite.tDest17CPL;
			tDest18=HeaderComposite.tDest18CPL;
			tDest19=HeaderComposite.tDest19CPL;
			tDest20=HeaderComposite.tDest20CPL;
			tDest21=HeaderComposite.tDest21CPL;
			
		}
		
		
		b8a.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (ID.compareToIgnoreCase("ALR")==0) RefreshTable.refresh8a(t8aALR);
				else if (ID.compareToIgnoreCase("FPL")==0) RefreshTable.refresh8a(t8aFPL);
				else if (ID.compareToIgnoreCase("CPL")==0) RefreshTable.refresh8a(t8aCPL);
			}
		});
		
		b8b.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (ID.compareToIgnoreCase("ALR")==0) RefreshTable.refresh8b(t8bALR);
				else if (ID.compareToIgnoreCase("FPL")==0) RefreshTable.refresh8b(t8bFPL);
				else if (ID.compareToIgnoreCase("CPL")==0) RefreshTable.refresh8b(t8bCPL);
			}
		});
	
		//----------------------------
		ModifyListener lis9bTo9c = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				atsAuto.auto9bTo9c((Text) e.widget, t9b, t9c, ID);
			}
		};
		t9b.addModifyListener(lis9bTo9c);
		t9c.addModifyListener(lis9bTo9c);
		
		b9c.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (ID.compareToIgnoreCase("ALR")==0) RefreshTable.refresh9c(t9cALR);
				else if (ID.compareToIgnoreCase("FPL")==0) RefreshTable.refresh9c(t9cFPL);
				else if (ID.compareToIgnoreCase("CPL")==0) RefreshTable.refresh9c(t9cCPL);
			}
		});
		
		ModifyListener lisEnable9b = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				atsAuto.Enable9b(t9b, t9c, ID, bSave9);
			}
		};
		t9b.addModifyListener(lisEnable9b);
		t9c.addModifyListener(lisEnable9b);
	
		bSave9.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				t9b.setBackground(Colors.Grey);
				t9c.setBackground(Colors.Grey);
				
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				String id="",type9b="",type9c="";
				
				try { conn = jdbc.getDBConnection(); } 
				catch (Exception ex) { ex.printStackTrace(); }
				
		    	try {
					String lihatsql = "SELECT * FROM aircraft_wtc WHERE type9b='"+t9b.getText()+"'";// and type9c='"+c9cCPL.getText()+"'";
					stmt = conn.createStatement();
					rs = stmt.executeQuery(lihatsql);
					rs.last();
					int jumlah = rs.getRow();
					System.out.println("Jumlah data=" + jumlah);
					rs.beforeFirst();
					
					int rowNo = 0;
					while (rs.next()) {
						rowNo++;
						
						id = rs.getString("id"); if (id==null) { id=""; }
						type9b = rs.getString("type9b"); if (type9b==null) { type9b=""; }
						type9c = rs.getString("type9c"); if (type9c==null) { type9c=""; }
					}
					rs.close();
					stmt.close();
					conn.close();
		    	} catch (SQLException s) {
					DialogFactory.openInfoDialog(shl, "Search Data", s.toString());
					s.printStackTrace();
				} catch (java.lang.OutOfMemoryError hs) {
					DialogFactory.openInfoDialog(shl, "Search Data", "Out of memory !!\nPlease fill another criteria search !! ");
				}
				
				if (type9b.compareTo(t9b.getText())==0) {
					String update = "UPDATE aircraft_wtc SET type9b='"+t9b.getText()+"',type9c='"+t9c.getText()+"' WHERE id='"+id+"'";
					jdbc.update(update);
					DialogFactory.openInfoDialog(shl, "Update Data", "TYPE OF AIRCRAFT criteria has been updated.");
				} else {
					String insert = "INSERT INTO aircraft_wtc (type9b,type9c) VALUES ('"+t9b.getText()+"','"+t9c.getText()+"')";
					jdbc.connect(insert);
					DialogFactory.openInfoDialog(shl, "Save Data", "New TYPE OF AIRCRAFT criteria has been saved.");
				}
				
				bSave9.setEnabled(false);
				t9b.setBackground(Colors.white);
				t9c.setBackground(Colors.white);
			}
		});
		//----------------------------
		bGetRoute(getRoute, first, prev, next, last, t13a, t16a, ID);

		ModifyListener lisEnable15c = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				atsAuto.enabledBtn(t13a.getText(), t16a.getText(), t15c.getText(), bSave15);
			}
		};
		t13a.addModifyListener(lisEnable15c);
		t16a.addModifyListener(lisEnable15c);
		t15c.addModifyListener(lisEnable15c);
		
		bSave15.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				t13a.setBackground(Colors.Grey);
				t15c.setBackground(Colors.Grey);
				t16a.setBackground(Colors.Grey);
				
				tDest1.setBackground(Colors.Grey);
				tDest2.setBackground(Colors.Grey);
				tDest3.setBackground(Colors.Grey);
				tDest4.setBackground(Colors.Grey);
				tDest5.setBackground(Colors.Grey);
				tDest6.setBackground(Colors.Grey);
				tDest7.setBackground(Colors.Grey);
				tDest8.setBackground(Colors.Grey);
				tDest9.setBackground(Colors.Grey);
				tDest10.setBackground(Colors.Grey);
				tDest11.setBackground(Colors.Grey);
				tDest12.setBackground(Colors.Grey);
				tDest13.setBackground(Colors.Grey);
				tDest14.setBackground(Colors.Grey);
				tDest15.setBackground(Colors.Grey);
				tDest16.setBackground(Colors.Grey);
				tDest17.setBackground(Colors.Grey);
				tDest18.setBackground(Colors.Grey);
				tDest19.setBackground(Colors.Grey);
				tDest20.setBackground(Colors.Grey);
				tDest21.setBackground(Colors.Grey);
				
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				String id="",type13a="",type15c="",type16a="";
				
				try { conn = jdbc.getDBConnection(); } 
				catch (Exception ex) { ex.printStackTrace(); }
				
		    	try {
					String lihatsql = "SELECT * FROM route WHERE type13a='"+t13a.getText()+"' and type16a='"+t16a.getText()+"'";
					stmt = conn.createStatement();
					rs = stmt.executeQuery(lihatsql);
					rs.last();
					int jumlah = rs.getRow();
					System.out.println("Jumlah data=" + jumlah);
					rs.beforeFirst();
					
					int rowNo = 0;
					while (rs.next()) {
						rowNo++;
						
						id = rs.getString("id_number"); if (id==null) { id=""; }
						type13a = rs.getString("type13a"); if (type13a==null) { type13a=""; }
						type15c = rs.getString("type15c"); if (type15c==null) { type15c=""; }
						type16a = rs.getString("type16a"); if (type16a==null) { type16a=""; }
					}
					rs.close();
					stmt.close();
					conn.close();
		    	} catch (SQLException s) {
					DialogFactory.openInfoDialog(shl, "Search Data", s.toString());
					s.printStackTrace();
				} catch (java.lang.OutOfMemoryError hs) {
					DialogFactory.openInfoDialog(shl, "Search Data", "Out of memory !!\nPlease fill another criteria search !! ");
				}
				
				if (type13a.compareTo(t13a.getText())==0 && type16a.compareTo(t16a.getText())==0) {
					if (t13a.getText().isEmpty()) { DialogFactory.openInfoDialog(shl, "Update Data", "The value in field DEP AD is required."); t13a.setFocus(); }
					else if (!t13a.getText().isEmpty() && t13a.getText().length()<4) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 4 LETTERS for field DEP AD !!"); t13a.setFocus(); }
					else if (t16a.getText().isEmpty()) { DialogFactory.openInfoDialog(shl, "Update Data", "The value in field DEST AD is required."); t16a.setFocus(); }
					else if (!t16a.getText().isEmpty() && t16a.getText().length()<4) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 4 LETTERS for field DEST AD !!"); t16a.setFocus(); }
					else if (t15c.getText().isEmpty()) { DialogFactory.openInfoDialog(shl, "Update Data", "The value in field ROUTE is required."); t15c.setFocus(); }
					else if ((!tDest1.getText().equals("")) && (tDest1.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest1.setFocus(); }
					else if ((!tDest2.getText().equals("")) && (tDest2.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest2.setFocus(); }
					else if ((!tDest3.getText().equals("")) && (tDest3.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest3.setFocus(); }
					else if ((!tDest4.getText().equals("")) && (tDest4.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest4.setFocus(); }
					else if ((!tDest5.getText().equals("")) && (tDest5.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest5.setFocus(); }
					else if ((!tDest6.getText().equals("")) && (tDest6.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest6.setFocus(); }
					else if ((!tDest7.getText().equals("")) && (tDest7.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest7.setFocus(); }
					
					else if ((!tDest8.getText().equals("")) && (tDest8.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest8.setFocus(); }
					else if ((!tDest9.getText().equals("")) && (tDest9.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest9.setFocus(); }
					else if ((!tDest10.getText().equals("")) && (tDest10.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest10.setFocus(); }
					else if ((!tDest11.getText().equals("")) && (tDest11.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest11.setFocus(); }
					else if ((!tDest12.getText().equals("")) && (tDest12.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest12.setFocus(); }
					else if ((!tDest13.getText().equals("")) && (tDest13.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest13.setFocus(); }
					else if ((!tDest14.getText().equals("")) && (tDest14.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest14.setFocus(); }
					
					else if ((!tDest15.getText().equals("")) && (tDest15.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest15.setFocus(); }
					else if ((!tDest16.getText().equals("")) && (tDest16.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest16.setFocus(); }
					else if ((!tDest17.getText().equals("")) && (tDest17.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest17.setFocus(); }
					else if ((!tDest18.getText().equals("")) && (tDest18.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest18.setFocus(); }
					else if ((!tDest19.getText().equals("")) && (tDest19.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest19.setFocus(); }
					else if ((!tDest20.getText().equals("")) && (tDest20.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest20.setFocus(); }
					else if ((!tDest21.getText().equals("")) && (tDest21.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Update Data", "Please insert 8 characters for ADDRESS's field !!"); tDest21.setFocus(); }
					else {
						String update = "UPDATE route SET " +
							"type13a='"+t13a.getText()+"',type16a='"+t16a.getText()+"',type15c='"+t15c.getText()+
							"',destination1='"+tDest1.getText()+"',destination2='"+tDest2.getText()+
							"',destination3='"+tDest3.getText()+"',destination4='"+tDest4.getText()+
							"',destination5='"+tDest5.getText()+"',destination6='"+tDest6.getText()+
							"',destination7='"+tDest7.getText()+"',destination8='"+tDest8.getText()+
							"',destination9='"+tDest9.getText()+"',destination10='"+tDest10.getText()+
							"',destination11='"+tDest11.getText()+"',destination12='"+tDest12.getText()+
							"',destination13='"+tDest13.getText()+"',destination14='"+tDest14.getText()+
							"',destination15='"+tDest15.getText()+"',destination16='"+tDest16.getText()+
							"',destination17='"+tDest17.getText()+"',destination18='"+tDest18.getText()+
							"',destination19='"+tDest19.getText()+"',destination20='"+tDest20.getText()+
							"',destination21='"+tDest21.getText()+"' WHERE id_number='"+id+"'";
					
						jdbc.update(update);
						DialogFactory.openInfoDialog(shl, "Update Data", "Route criteria has been updated.");
					}
					
				} else {
					if (t13a.getText().isEmpty()) { DialogFactory.openInfoDialog(shl, "Save Data", "The value in field DEP AD is required."); t13a.setFocus(); }
					else if (!t13a.getText().isEmpty() && t13a.getText().length()<4) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 4 LETTERS for field DEP AD !!"); t13a.setFocus(); }
					else if (t16a.getText().isEmpty()) { DialogFactory.openInfoDialog(shl, "Save Data", "The value in field DEST AD is required."); t16a.setFocus(); }
					else if (!t16a.getText().isEmpty() && t16a.getText().length()<4) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 4 LETTERS for field DEST AD !!"); t16a.setFocus(); }
					else if (t15c.getText().isEmpty()) { DialogFactory.openInfoDialog(shl, "Save Data", "The value in field ROUTE is required."); t15c.setFocus(); }
					else if ((!tDest1.getText().equals("")) && (tDest1.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest1.setFocus(); }
					else if ((!tDest2.getText().equals("")) && (tDest2.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest2.setFocus(); }
					else if ((!tDest3.getText().equals("")) && (tDest3.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest3.setFocus(); }
					else if ((!tDest4.getText().equals("")) && (tDest4.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest4.setFocus(); }
					else if ((!tDest5.getText().equals("")) && (tDest5.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest5.setFocus(); }
					else if ((!tDest6.getText().equals("")) && (tDest6.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest6.setFocus(); }
					else if ((!tDest7.getText().equals("")) && (tDest7.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest7.setFocus(); }
					
					else if ((!tDest8.getText().equals("")) && (tDest8.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest8.setFocus(); }
					else if ((!tDest9.getText().equals("")) && (tDest9.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest9.setFocus(); }
					else if ((!tDest10.getText().equals("")) && (tDest10.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest10.setFocus(); }
					else if ((!tDest11.getText().equals("")) && (tDest11.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest11.setFocus(); }
					else if ((!tDest12.getText().equals("")) && (tDest12.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest12.setFocus(); }
					else if ((!tDest13.getText().equals("")) && (tDest13.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest13.setFocus(); }
					else if ((!tDest14.getText().equals("")) && (tDest14.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest14.setFocus(); }
					
					else if ((!tDest15.getText().equals("")) && (tDest15.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest15.setFocus(); }
					else if ((!tDest16.getText().equals("")) && (tDest16.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest16.setFocus(); }
					else if ((!tDest17.getText().equals("")) && (tDest17.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest17.setFocus(); }
					else if ((!tDest18.getText().equals("")) && (tDest18.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest18.setFocus(); }
					else if ((!tDest19.getText().equals("")) && (tDest19.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest19.setFocus(); }
					else if ((!tDest20.getText().equals("")) && (tDest20.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest20.setFocus(); }
					else if ((!tDest21.getText().equals("")) && (tDest21.getText().length() < 8)) { DialogFactory.openInfoDialog(shl, "Save Data", "Please insert 8 characters for ADDRESS's field !!"); tDest21.setFocus(); }
					else {
						String insert = "INSERT INTO route (type13a,type16a,type15c,destination1,destination2,destination3,destination4," +
							"destination5,destination6,destination7,destination8,destination9,destination10,destination11,destination12," +
							"destination13,destination14,destination15,destination16,destination17,destination18,destination19,destination20," +
							"destination21) VALUES ('"+t13a.getText()+"','"+t16a.getText()+"','"+t15c.getText()+"','"+
							tDest1.getText()+"','"+tDest2.getText()+"','"+
							tDest3.getText()+"','"+tDest4.getText()+"','"+
							tDest5.getText()+"','"+tDest6.getText()+"','"+
							tDest7.getText()+"','"+tDest8.getText()+"','"+
							tDest9.getText()+"','"+tDest10.getText()+"','"+
							tDest11.getText()+"','"+tDest12.getText()+"','"+
							tDest13.getText()+"','"+tDest14.getText()+"','"+
							tDest15.getText()+"','"+tDest16.getText()+"','"+
							tDest17.getText()+"','"+tDest18.getText()+"','"+
							tDest19.getText()+"','"+tDest20.getText()+"','"+
							tDest21.getText()+"')";
						jdbc.connect(insert);
						DialogFactory.openInfoDialog(shl, "Save Data", "New Route criteria has been saved.");
					}
				}
				
				bSave15.setEnabled(false);
				t13a.setBackground(Colors.white);
				t15c.setBackground(Colors.white);
				t16a.setBackground(Colors.white);
				tDest1.setBackground(Colors.white);
				tDest2.setBackground(Colors.white);
				tDest3.setBackground(Colors.white);
				tDest4.setBackground(Colors.white);
				tDest5.setBackground(Colors.white);
				tDest6.setBackground(Colors.white);
				tDest7.setBackground(Colors.white);
				tDest8.setBackground(Colors.white);
				tDest9.setBackground(Colors.white);
				tDest10.setBackground(Colors.white);
				tDest11.setBackground(Colors.white);
				tDest12.setBackground(Colors.white);
				tDest13.setBackground(Colors.white);
				tDest14.setBackground(Colors.white);
				tDest15.setBackground(Colors.white);
				tDest16.setBackground(Colors.white);
				tDest17.setBackground(Colors.white);
				tDest18.setBackground(Colors.white);
				tDest19.setBackground(Colors.white);
				tDest20.setBackground(Colors.white);
				tDest21.setBackground(Colors.white);
			}
		});
		//----------------------------
		if (ID.endsWith("ALR")) setDateNow(tDofALR);
		else if (ID.endsWith("FPL")) setDateNow(tDofFPL);
		else if (ID.endsWith("CPL")) setDateNow(tDofCPL);
		else if (ID.endsWith("SPL")) setDateNow(tDofSPL);
		
//		sh.buttonStyle(getRoute, "Get Route", "Get Route from DEP AD and DEST AD", bs.widthBtn, bs.heightBtn, bs.colorBtn, Images.img_route);
//		bs.arrow(first, prev, next, last);
//		getRoute.setEnabled(true); first.setEnabled(false); prev.setEnabled(false); next.setEnabled(false); last.setEnabled(false);
	}
	
	public static void listener10(final String ID,Button b10a,Button b10b,Button btnChk) {
		sh.buttonStyle(b10a, "", "Choose Equipment and Capabilities Value", bs.widthNavBtn, bs.colorBtn, SWT.CENTER, SWT.LEFT, Images.img_LIST);
		sh.buttonStyle(btnChk, "", "Check PBN Suggestion", bs.widthNavBtn, bs.colorBtn, SWT.CENTER, SWT.LEFT, Images.img_CHECK);
		sh.buttonStyle(b10b, "", "Choose Equipment and Capabilities Value", bs.widthNavBtn, bs.colorBtn, SWT.CENTER, SWT.LEFT, Images.img_LIST);
		
		b10a.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (ID.compareToIgnoreCase("ALR")==0) RefreshTable.refresh10a(t10aALR);
				else if (ID.compareToIgnoreCase("FPL")==0) RefreshTable.refresh10a(t10aFPL);
				else if (ID.compareToIgnoreCase("CPL")==0) RefreshTable.refresh10a(t10aCPL);
				else if (ID.compareToIgnoreCase("REG")==0) RefreshTable.refresh10a(TableReg.t10a_REG);
			}
		});
		
		b10b.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (ID.compareToIgnoreCase("ALR")==0) RefreshTable.refresh10b(t10bALR);
				else if (ID.compareToIgnoreCase("FPL")==0) RefreshTable.refresh10b(t10bFPL);
				else if (ID.compareToIgnoreCase("CPL")==0) RefreshTable.refresh10b(t10bCPL);
				else if (ID.compareToIgnoreCase("REG")==0) RefreshTable.refresh10b(TableReg.t10b_REG);
			}
		});
		
		if (ID.compareToIgnoreCase("ALR")==0) t10a = t10aALR;
		else if (ID.compareToIgnoreCase("FPL")==0) t10a = t10aFPL;
		else if (ID.compareToIgnoreCase("CPL")==0) t10a = t10aCPL;
		else if (ID.compareToIgnoreCase("REG")==0) t10a = TableReg.t10a_REG;
		btnChk.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String strPbn="",strNull="",strG="",strD="",strOD="",strI="",strDI="",aw="";

				//tidak terdapat salah satu kriteria di bawah.
				if (!t10a.getText().contains("R") && !t10a.getText().contains("G") 
						&& !t10a.getText().contains("D") && !t10a.getText().contains("OD") && !t10a.getText().contains("SD") 
						&& !t10a.getText().contains("I") && !t10a.getText().contains("DI")) { 
					strNull = "No PBN/ Indicator Suggestion !!"; }
				else if (t10a.getText().contains("R") && !t10a.getText().contains("G") && !t10a.getText().contains("D") 
						&& !t10a.getText().contains("OD") && !t10a.getText().contains("SD") && !t10a.getText().contains("I") 
						&& !t10a.getText().contains("DI")) { 
					strNull = "PBN/ Indicator should be filled !!"; } 
				//G
				if (t10a.getText().contains("G")) { 
					strG="* B1, B2, C1, C2, D1, D2, O1 or O2 in field PBN/ indicator,\nif field EQUIPMENT AND CAPABILITIES contains 'G'.\n\n"; } else strG="";
				//D
				if (t10a.getText().contains("D")) { 
					strD="* B1, B3, C1, C3, D1, D3, O1 or O3 in field PBN/ indicator,\nif field EQUIPMENT AND CAPABILITIES contains 'D'.\n\n"; } else strD="";
				//I
				if (t10a.getText().contains("I")) { 
					strI="* B1, B5, C1 or C5 in field PBN/ indicator,\nif field EQUIPMENT AND CAPABILITIES contains 'I'.\n\n"; } else strI="";
				//DI
				if (t10a.getText().contains("D") && t10a.getText().contains("I")) { 
					strDI="* C1, C4, D1, D4, O1 or O4 in field PBN/ indicator,\nif field EQUIPMENT AND CAPABILITIES contains 'DI'.\n\n"; } else strDI="";
				//OD or SD
				if ((t10a.getText().contains("O") && t10a.getText().contains("D")) || 
						(t10a.getText().contains("S") && t10a.getText().contains("D"))) { 
					strOD="* B1 or B4 in field PBN/ indicator,\nif field EQUIPMENT AND CAPABILITIES contains 'OD' or 'SD'.\n\n"; } else strOD="";
				//Gabungkan
				if (!strG.isEmpty() || !strD.isEmpty() || !strOD.isEmpty() || !strI.isEmpty() || !strDI.isEmpty()) {
					aw = "You may insert :\n\n";					
				} else aw="";
				strPbn = aw+strNull+strG+strD+strOD+strI+strDI;
//				DialogFactory.openInfoDialog(Shells.sh_nALR, "PBN/ Indicator Suggestion", strPbn);
				
				if (ID.endsWith("ALR")) {
					DialogFactory.openInfoDialog(Shells.sh_nALR, "PBN/ Indicator Suggestion", strPbn); 
					if (strPbn.contains("No PBN/ Indicator Suggestion !!")) t10aALR.setFocus();
					else tPbnALR.setFocus();
				}
				else if (ID.endsWith("FPL")) {
					DialogFactory.openInfoDialog(Shells.sh_nFPL, "PBN/ Indicator Suggestion", strPbn); 
					if (strPbn.contains("No PBN/ Indicator Suggestion !!")) t10aFPL.setFocus();
					else tPbnFPL.setFocus();
				}
				else if (ID.endsWith("CPL")) {
					DialogFactory.openInfoDialog(Shells.sh_nCPL, "PBN/ Indicator Suggestion", strPbn); 
					if (strPbn.contains("No PBN/ Indicator Suggestion !!")) t10aCPL.setFocus();
					else tPbnCPL.setFocus();
				}
				else if (ID.compareToIgnoreCase("sREG")==0) {
					DialogFactory.openInfoDialog(Shells.sh_tReg, "PBN/ Indicator Suggestion", strPbn); 
					if (strPbn.contains("No PBN/ Indicator Suggestion !!")) TableReg.t10a_REG.setFocus();
					else TableReg.tPbn_REG.setFocus();
				}
			}
		});
	}
	
	public static void listenerPBN(final String ID,Button bPBN) {
		sh.buttonStyle(bPBN, "", "Choose PBN/ Indicator Value", bs.widthNavBtn, bs.colorBtn, SWT.TOP, SWT.LEFT, Images.img_LIST);
		bPBN.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (ID.compareToIgnoreCase("ALR")==0) RefreshTable.refreshPBN(tPbnALR);
				else if (ID.compareToIgnoreCase("FPL")==0) RefreshTable.refreshPBN(tPbnFPL);
				else if (ID.compareToIgnoreCase("CPL")==0) RefreshTable.refreshPBN(tPbnCPL);
				else if (ID.compareToIgnoreCase("SPL")==0) RefreshTable.refreshPBN(tPbnSPL);
				else if (ID.compareToIgnoreCase("REG")==0) RefreshTable.refreshPBN(TableReg.tPbn_REG);
			}
		});
	}
	
	public static void listenerSTSPBN(final String ID,Button bPBN,Button bSTS) {
		listenerPBN(ID, bPBN);
		sh.buttonStyle(bSTS, "", "Choose STS/ Indicator Value", bs.widthNavBtn, bs.colorBtn, SWT.TOP, SWT.LEFT, Images.img_LIST);
		bSTS.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (ID.compareToIgnoreCase("ALR")==0) RefreshTable.refreshSTS(tStsALR);
				else if (ID.compareToIgnoreCase("FPL")==0) RefreshTable.refreshSTS(tStsFPL);
				else if (ID.compareToIgnoreCase("CPL")==0) RefreshTable.refreshSTS(tStsCPL);
				else if (ID.compareToIgnoreCase("SPL")==0) RefreshTable.refreshSTS(tStsSPL);
			}
		});
	}
	
	//------------------------------------------------------------------------------------------------------------------------
	static void addListenerSPL() {
		listenerSTSPBN("SPL", bPBNSPL, bSTSSPL);
	}
	
	static void addListenerALR() {
		sh.buttonStyle(b5aALR, "", "Choose Phase of Emergency Value", bs.widthNavBtn, bs.colorBtn, SWT.TOP, SWT.LEFT, Images.img_LIST);
		b5aALR.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RefreshTable.refresh5a(t5aALR);
			}
		});
		
		addButton("ALR", b8aALR, b8bALR, b9cALR, b10aALR, btnChkALR, b10bALR, bSTSALR, bPBNALR, bSave9ALR, bSave15ALR, 
				getRouteALR,firstALR,prevALR,nextALR,lastALR);
		
	}
	
	static void addListenerFPL() {
		addButton("FPL", b8aFPL, b8bFPL, b9cFPL, b10aFPL, btnChkFPL, b10bFPL, bSTSFPL, bPBNFPL, bSave9FPL, bSave15FPL,
				getRouteFPL,firstFPL,prevFPL,nextFPL,lastFPL);
		
		//percobaan
		sh.buttonStyle(bSaveRegFPL, "", "Save/Update REG/ criteria", bs.widthNavBtn, bs.colorBtn, SWT.CENTER, SWT.LEFT, Images.img_save16);
		
		bSaveRegFPL.setEnabled(false);
		bSaveRegFPL.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				tRegFPL.setBackground(Colors.Grey);
				t9bFPL.setBackground(Colors.Grey);
				t10aFPL.setBackground(Colors.Grey);
				t10bFPL.setBackground(Colors.Grey);
				tOprFPL.setBackground(Colors.Grey);
				tPbnFPL.setBackground(Colors.Grey);
				tSelFPL.setBackground(Colors.Grey);
				
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				String id="",type9b="",type18="",type10a="",type10b="",type18Opr="",type18Pbn="",type18Sel="";
				
				try { conn = jdbc.getDBConnection(); } 
				catch (Exception ex) { ex.printStackTrace(); }
				
		    	try {
					String lihatsql = "SELECT * FROM aircraft_reg WHERE type18='"+tRegFPL.getText()+"'";
					stmt = conn.createStatement();
					rs = stmt.executeQuery(lihatsql);
					rs.last();
//					int jumlah = rs.getRow();
//					System.out.println("Jumlah data=" + jumlah);
					rs.beforeFirst();
					
					int rowNo = 0;
					while (rs.next()) {
						rowNo++;
						
						id = rs.getString("id"); if (id==null) { id=""; }
						type9b = rs.getString("type9b"); if (type9b==null) { type9b=""; }
						type18 = rs.getString("type18"); if (type18==null) { type18=""; }
						type10a = rs.getString("type10a"); if (type10a==null) { type10a=""; }
						type10b = rs.getString("type10b"); if (type10b==null) { type10b=""; }
						type18Opr = rs.getString("type18Opr"); if (type18Opr==null) { type18Opr=""; }
						type18Pbn = rs.getString("type18Pbn"); if (type18Pbn==null) { type18Pbn=""; }
						type18Sel = rs.getString("type18Sel"); if (type18Sel==null) { type18Sel=""; }
						
					}
					rs.close();
					stmt.close();
					conn.close();
		    	} catch (SQLException s) {
					DialogFactory.openInfoDialog(Shells.sh_nFPL, "Search Data", s.toString());
					s.printStackTrace();
				} catch (java.lang.OutOfMemoryError hs) {
					DialogFactory.openInfoDialog(Shells.sh_nFPL, "Search Data", "Out of memory !!\nPlease fill another criteria search !! ");
				}
				
				if (type18.compareTo(tRegFPL.getText())==0) {
					ss.krit1="";ss.krit2="";ss.krit3="";ss.krit4="";ss.krit5="";
					ss.t10b=""; ss.sdbguv=""; ss.sprotek10a=""; ss.sprotekpbn="";
					ss.t10b = t10bFPL.getText(); 
					ss.t10a = t10aFPL.getText();
					ss.tpbn = tPbnFPL.getText();

					ss.validasi10a(ss.t10a);
					ss.validasi18_10(ss.t10b, ss.t10a);
					ss.proteksi18();
					ss.validasipbn(ss.tpbn);
			    	ss.proteksi18REG(ss.tpbn);
			    			    	
			    	if (id.equals("")) { DialogFactory.openInfoDialog(Shells.sh_nFPL, "Update Data","At least one row must be selected if you want to update your message !!"); }
					//9
			    	else if (tRegFPL.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data", "The value in field REG/ is required."); tRegFPL.setFocus(); }
					else if (t9bFPL.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data", "The value in field TYPE OF AIRCRAFT is required."); t9bFPL.setFocus(); }
					else if (!t9bFPL.getText().isEmpty() && t9bFPL.getText().length()<2) { DialogFactory.openInfoDialog(Shells.sh_nFPL, "Update Data", "Please insert 2 - 4 CHARACTERS for field TYPE OF AIRCRAFT !!"); t9bFPL.setFocus(); }
			    	//OPR
					else if (tOprFPL.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data", "The value in field OPR/ is required."); tOprFPL.setFocus(); }
//					else if (ss.tpbn.isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data", "The value in field PBN/ is required."); tPbnFPL.setFocus(); }
					//10
			        else if (ss.t10a.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is required."); t10aFPL.setFocus(); }
					else if (ss.t10b.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is required."); t10bFPL.setFocus(); }
					else if ( (ss.t10b.contains("B1") || ss.t10b.contains("U1") || ss.t10b.contains("V1")) && (ss.t10b.contains("B2") || ss.t10b.contains("U2") || ss.t10b.contains("V2"))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10a.contains("N") && ss.t10a.contains("S")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","Invalid EQUIPMENT AND CAPABILITIES value.\n\n'N' is not allowed, if 'S' has inserted into field EQUIPMENT AND CAPABILITIES and vice versa.\n\nSee the instruction below the EQUIPMENT AND CAPABILITIES text !"); t10aFPL.setFocus(); }
					else if (ss.t10b.contains("N") && (ss.t10b.contains("A") || ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X") || ss.t10b.contains("B1") || ss.t10b.contains("B2") || ss.t10b.contains("U1") || ss.t10b.contains("U2") || ss.t10b.contains("V1") || ss.t10b.contains("V2") || ss.t10b.contains("D1") || ss.t10b.contains("G1"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","Invalid EQUIPMENT AND CAPABILITIES value.\n\nSSR Modes A and C and SSR Mode S are not allowed,\nif 'N' has inserted into field EQUIPMENT AND CAPABILITIES and vice versa.\n\nSee the instruction below the EQUIPMENT AND CAPABILITIES text !"); t10bFPL.setFocus(); }
					else if (!ss.t10b.equals("") && (ss.sdbguv.contains("tdksesuai"))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, "Update Data", em.infoInvalid10b+"\nInsert B1 or B2 or U1 or U2 or V1 or V2 or D1 or G1"); t10bFPL.setFocus();; }		
					else if (!ss.t10a.equals("") && (ss.sprotek10a.contains("ngaco"))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, "Update Data", em.infoInvalid10aFix); t10aFPL.setFocus(); }		
					//NEW
			    	//N, harus sendiri
					else if (ss.t10b.contains("N") && ss.t10b.length()>1) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("A") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("C") && (ss.t10b.contains("A") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("E") && (ss.t10b.contains("C") || ss.t10b.contains("A") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("H") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("A") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("I") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("A") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("L") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("A") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("P") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("A") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("S") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("A") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("X") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("A"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
			    	//PBN
			        else if (!ss.tpbn.equals("") && (ss.tpbn.length() > 16 || ss.tpbn.contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","Invalid PBN/ indicator value.\nSee the instruction below the PBN/ indicator text !"); tPbnFPL.setFocus(); }
			        else if (!ss.tpbn.equals("") && ss.sprotekpbn.contains("ngaco")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data", em.infoInvalidPbnFix); tPbnFPL.setFocus(); }
					else if (ss.t10a.contains("R") && ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","Field OTHER INFORMATION with indicator PBN/ should be filled, if the letter 'R' is used in field EQUIPMENT AND CAPABILITIES."); tPbnFPL.setFocus(); }
					else if (!ss.t10a.contains("R") && !ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","Field EQUIPMENT AND CAPABILITIES should be contained the letter 'R', if field OTHER INFORMATION is filled."); t10aFPL.setFocus(); }
			    	//G
					else if (!ss.t10a.contains("G") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","If B1, B2, C1, C2, D1, D2, O1 or O2 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'G' must be included in field EQUIPMENT AND CAPABILITIES !"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("G") && !ss.krit2.equals("") && (ss.krit2.contains("B2") || ss.krit2.contains("C2") || ss.krit2.contains("D2") || ss.krit2.contains("O2"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","If B1, B2, C1, C2, D1, D2, O1 or O2 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'G' must be included in field EQUIPMENT AND CAPABILITIES !"); t10aFPL.setFocus(); }
					//D
					else if (!ss.t10a.contains("D") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","If B1, B3, C1, C3, D1, D3, O1 or O3 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' must be included in field EQUIPMENT AND CAPABILITIES !"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("D") && !ss.krit3.equals("") && (ss.krit3.contains("B3") || ss.krit3.contains("C3") || ss.krit3.contains("D3") || ss.krit3.contains("O3"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","If B1, B3, C1, C3, D1, D3, O1 or O3 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' must be included in field EQUIPMENT AND CAPABILITIES !"); t10aFPL.setFocus(); }
			    	//I
					else if (!ss.t10a.contains("I") && !ss.krit5.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","If B1, B5 or C1 are filled in field OTHER INFORMATION with PBN/ indicator, then an 'I' must be included in field EQUIPMENT AND CAPABILITIES !"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","If B1, B5 or C1 are filled in field OTHER INFORMATION with PBN/ indicator, then an 'I' must be included in field EQUIPMENT AND CAPABILITIES !"); t10aFPL.setFocus(); }
					//DI
					else if (!ss.t10a.contains("D") && !ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("D") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("I") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("I") && (ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10aFPL.setFocus(); }
			    	//OD / SD
					else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit1.contains("B1")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("D") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("D") && ss.krit1.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Update Data", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10aFPL.setFocus(); }
			    	
					else {
						String update = "UPDATE aircraft_reg SET type9b='"+t9bFPL.getText()+"',type10a='"+t10aFPL.getText()+"',type10b='"+ss.t10b+"'," +
								"type18='"+tRegFPL.getText()+"',type18Opr='"+tOprFPL.getText()+"',type18Pbn='"+ss.tpbn+"',type18Sel='"+tSelFPL.getText()+"' WHERE id='"+id+"'";
						jdbc.update(update);
						DialogFactory.openInfoDialog(Shells.sh_nFPL, "Update Data", "REG/ criteria has been updated.");
					}
				} else {
					ss.krit1="";ss.krit2="";ss.krit3="";ss.krit4="";ss.krit5="";
					ss.t10b=""; ss.sdbguv=""; ss.sprotek10a=""; ss.sprotekpbn="";
					ss.t10b = t10bFPL.getText(); 
					ss.t10a = t10aFPL.getText();
					ss.tpbn = tPbnFPL.getText();;

					ss.validasi10a(ss.t10a);
					ss.validasi18_10(ss.t10b, ss.t10a);
					ss.proteksi18();
					ss.validasipbn(ss.tpbn);
			    	ss.proteksi18REG(ss.tpbn);
			    	
					//9
					if (tRegFPL.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data", "The value in field REG/ is required."); tRegFPL.setFocus(); }
					else if (t9bFPL.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data", "The value in field TYPE OF AIRCRAFT is required."); t9bFPL.setFocus(); }
					else if (!t9bFPL.getText().isEmpty() && t9bFPL.getText().length()<2) { DialogFactory.openInfoDialog(Shells.sh_nFPL, "Save Data", "Please insert 2 - 4 CHARACTERS for field TYPE OF AIRCRAFT !!"); t9bFPL.setFocus(); }
					//OPR
					else if (tOprFPL.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data", "The value in field OPR/ is required."); tOprFPL.setFocus(); }
//					else if (ss.tpbn.isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data", "The value in field PBN/ is required."); tPbnFPL.setFocus(); }
					//10
			        else if (ss.t10a.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is required."); t10aFPL.setFocus(); }
					else if (ss.t10b.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is required."); t10bFPL.setFocus(); }
					else if ( (ss.t10b.contains("B1") || ss.t10b.contains("U1") || ss.t10b.contains("V1")) && (ss.t10b.contains("B2") || ss.t10b.contains("U2") || ss.t10b.contains("V2"))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10a.contains("N") && ss.t10a.contains("S")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","Invalid EQUIPMENT AND CAPABILITIES value.\n\n'N' is not allowed, if 'S' has inserted into field EQUIPMENT AND CAPABILITIES and vice versa.\n\nSee the instruction below the EQUIPMENT AND CAPABILITIES text !"); t10aFPL.setFocus(); }
					else if (ss.t10b.contains("N") && (ss.t10b.contains("A") || ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X") || ss.t10b.contains("B1") || ss.t10b.contains("B2") || ss.t10b.contains("U1") || ss.t10b.contains("U2") || ss.t10b.contains("V1") || ss.t10b.contains("V2") || ss.t10b.contains("D1") || ss.t10b.contains("G1"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","Invalid EQUIPMENT AND CAPABILITIES value.\n\nSSR Modes A and C and SSR Mode S are not allowed,\nif 'N' has inserted into field EQUIPMENT AND CAPABILITIES and vice versa.\n\nSee the instruction below the EQUIPMENT AND CAPABILITIES text !"); t10bFPL.setFocus(); }
					else if (!ss.t10b.equals("") && (ss.sdbguv.contains("tdksesuai"))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, "Save Data", em.infoInvalid10b+"\nInsert B1 or B2 or U1 or U2 or V1 or V2 or D1 or G1"); t10bFPL.setFocus();; }		
					else if (!ss.t10a.equals("") && (ss.sprotek10a.contains("ngaco"))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, "Save Data", em.infoInvalid10aFix); t10aFPL.setFocus(); }		
					//N, harus sendiri
					else if (ss.t10b.contains("N") && ss.t10b.length()>1) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("A") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("C") && (ss.t10b.contains("A") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("E") && (ss.t10b.contains("C") || ss.t10b.contains("A") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("H") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("A") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("I") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("A") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("L") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("A") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("P") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("A") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("S") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("A") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
					else if (ss.t10b.contains("X") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("A"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10bFPL.setFocus(); }
			    	//PBN
			        else if (!ss.tpbn.equals("") && (ss.tpbn.length() > 16 || ss.tpbn.contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","Invalid PBN/ indicator value.\nSee the instruction below the PBN/ indicator text !"); tPbnFPL.setFocus(); }
			        else if (!ss.tpbn.equals("") && ss.sprotekpbn.contains("ngaco")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data", em.infoInvalidPbnFix); tPbnFPL.setFocus(); }
					else if (ss.t10a.contains("R") && ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","Field OTHER INFORMATION with indicator PBN/ should be filled, if the letter 'R' is used in field EQUIPMENT AND CAPABILITIES."); tPbnFPL.setFocus(); }
					else if (!ss.t10a.contains("R") && !ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","Field EQUIPMENT AND CAPABILITIES should be contained the letter 'R', if field OTHER INFORMATION is filled."); t10aFPL.setFocus(); }
			    	//G
					else if (!ss.t10a.contains("G") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","If B1, B2, C1, C2, D1, D2, O1 or O2 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'G' must be included in field EQUIPMENT AND CAPABILITIES !"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("G") && !ss.krit2.equals("") && (ss.krit2.contains("B2") || ss.krit2.contains("C2") || ss.krit2.contains("D2") || ss.krit2.contains("O2"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","If B1, B2, C1, C2, D1, D2, O1 or O2 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'G' must be included in field EQUIPMENT AND CAPABILITIES !"); t10aFPL.setFocus(); }
					//D
					else if (!ss.t10a.contains("D") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","If B1, B3, C1, C3, D1, D3, O1 or O3 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' must be included in field EQUIPMENT AND CAPABILITIES !"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("D") && !ss.krit3.equals("") && (ss.krit3.contains("B3") || ss.krit3.contains("C3") || ss.krit3.contains("D3") || ss.krit3.contains("O3"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","If B1, B3, C1, C3, D1, D3, O1 or O3 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' must be included in field EQUIPMENT AND CAPABILITIES !"); t10aFPL.setFocus(); }
			    	//I
					else if (!ss.t10a.contains("I") && !ss.krit5.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","If B1, B5 or C1 are filled in field OTHER INFORMATION with PBN/ indicator, then an 'I' must be included in field EQUIPMENT AND CAPABILITIES !"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","If B1, B5 or C1 are filled in field OTHER INFORMATION with PBN/ indicator, then an 'I' must be included in field EQUIPMENT AND CAPABILITIES !"); t10aFPL.setFocus(); }
					//DI
					else if (!ss.t10a.contains("D") && !ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("D") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("I") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("I") && (ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10aFPL.setFocus(); }
			    	//OD / SD
					else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit1.contains("B1")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("D") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10aFPL.setFocus(); }
					else if (!ss.t10a.contains("D") && ss.krit1.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, "Save Data", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10aFPL.setFocus(); }
			    	
					else {
						String insert = "INSERT INTO aircraft_reg (type9b,type18,type10a,type10b,type18Opr,type18Pbn,type18Sel) VALUES " +
								"('"+t9bFPL.getText()+"','"+tRegFPL.getText()+"','"+t10aFPL.getText()+"','"+ss.t10b+"','"+
								tOprFPL.getText()+"','"+ss.tpbn+"','"+tSelFPL.getText()+"')";
						jdbc.connect(insert);
						DialogFactory.openInfoDialog(Shells.sh_nFPL, "Save Data", "New REG/ criteria has been saved.");
					}
					
				}
				
				bSaveRegFPL.setEnabled(false);
				tRegFPL.setBackground(Colors.white);
				t9bFPL.setBackground(Colors.white);
				t10aFPL.setBackground(Colors.white);
				t10bFPL.setBackground(Colors.white);
				tOprFPL.setBackground(Colors.white);
				tPbnFPL.setBackground(Colors.white);
				tSelFPL.setBackground(Colors.white);
			}
		});
		
		ModifyListener enReg = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				atsAuto.enabledBtn(tRegFPL.getText(), t9bFPL.getText(), t10aFPL.getText(), t10bFPL.getText(), tOprFPL.getText(), bSaveRegFPL);
			}
		};
		tRegFPL.addModifyListener(enReg);
		t9bFPL.addModifyListener(enReg);
		t10aFPL.addModifyListener(enReg);
		t10bFPL.addModifyListener(enReg);
		tOprFPL.addModifyListener(enReg);
		
		ModifyListener regListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				atsAuto.valueChangedReg((Text) e.widget,HeaderComposite.regText,t9bFPL,tDofFPL,tRegFPL,t10aFPL,t10bFPL,tOprFPL,tPbnFPL,tSelFPL);
			}
		};
		HeaderComposite.regText.addModifyListener(regListener);
		t9bFPL.addModifyListener(regListener);
		tRegFPL.addModifyListener(regListener);
		t10aFPL.addModifyListener(regListener);
		t10bFPL.addModifyListener(regListener);
		tOprFPL.addModifyListener(regListener);
	}
	
	static void addListenerCPL() {
		addButton("CPL", b8aCPL, b8bCPL, b9cCPL, b10aCPL, btnChkCPL, b10bCPL, bSTSCPL, bPBNCPL, bSave9CPL, bSave15CPL,
				getRouteCPL,firstCPL,prevCPL,nextCPL,lastCPL);
	}
	
	public static void initNewEST(Composite comp, final String ID) {
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tEST);
		Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 17, SWT.LEFT, SWT.CENTER);
		Label label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 125, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL); 
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13a, 70, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE);
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			label = new Label(line1, SWT.NONE); //sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		} else {
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		}
		label = new Label(line1, SWT.NONE);

		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t3aEST = new Text(line1, SWT.BORDER); t3aEST.setText("EST"); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		t3bEST = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL); 
		t3cEST = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t7aEST = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
		t7bEST = new Button(line1, SWT.CHECK); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL); 
		t7cEST = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t13aEST = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		t13bEST = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER);
		
		Composite line2 = new Composite(group, SWT.NONE); sh.compositeStyle(line2, 13, SWT.LEFT, SWT.CENTER);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl14a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl14b, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl14c, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line2, SWT.NONE); 
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl14d, 125, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line2, SWT.NONE); 
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl14e, 100, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<3; i++) label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t14aEST = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		t14bEST = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.CENTER); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL); 
		t14cEST = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.CENTER); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
		t14dEST = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.CENTER); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		t14eEST = new Text(line2, SWT.BORDER);
		for (int i=0; i<3; i++) label = new Label(line2, SWT.NONE);
		
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			Composite line3 = new Composite(group, SWT.NONE); sh.compositeStyle(line3, 13, SWT.LEFT, SWT.CENTER);
			label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl16a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line3, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			for (int i=0; i<4; i++) label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.NONE); //sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.NONE); //sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.NONE); //sh.labelStyle(label, lbl16d, 80, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			
			label = new Label(line3, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aEST = new Text(line3, SWT.BORDER);
			label = new Label(line3, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			label = new Label(line3, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
			for (int i=0; i<4; i++) label = new Label(line3, SWT.NONE);
			Composite type = new Composite(line3, SWT.NONE); sh.compositeStyle(type, 5, SWT.LEFT, SWT.CENTER);
			getFplEST = new Button(type, SWT.PUSH);
			firstEST = new Button(type, SWT.PUSH);
			prevEST = new Button(type, SWT.PUSH);
			nextEST = new Button(type, SWT.PUSH);
			lastEST = new Button(type, SWT.PUSH);
			bGetFPL(getFplEST, firstEST, prevEST, nextEST, lastEST, t7aEST, ID);
			label = new Label(line3, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			t16bEST = new Text(line3, SWT.BORDER);
			label = new Label(line3, SWT.RIGHT); //sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
			t16cEST = new Text(line3, SWT.BORDER);
			label = new Label(line3, SWT.RIGHT); //sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
			Composite comp16 = new Composite(line3, SWT.NONE); sh.compositeStyle(comp16, 3, SWT.LEFT, SWT.CENTER);
			t16dEST = new Text(comp16, SWT.BORDER);
			
			disableDEP(t13bEST);
			disableDEST(t16bEST, t16cEST, t16dEST);
		} else {
			Composite line3 = new Composite(group, SWT.NONE); sh.compositeStyle(line3, 13, SWT.LEFT, SWT.CENTER);
			label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl16a, 110, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.NONE); sh.labelStyle(label, lbl16d, 80, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line3, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			for (int i=0; i<4; i++) label = new Label(line3, SWT.NONE);
			label = new Label(line3, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aEST = new Text(line3, SWT.BORDER);
			label = new Label(line3, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			t16bEST = new Text(line3, SWT.BORDER);
			label = new Label(line3, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
			t16cEST = new Text(line3, SWT.BORDER);
			label = new Label(line3, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
			Composite comp16 = new Composite(line3, SWT.NONE); sh.compositeStyle(comp16, 3, SWT.LEFT, SWT.CENTER);
			t16dEST = new Text(comp16, SWT.BORDER);
			label = new Label(comp16, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			label = new Label(comp16, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
			for (int i=0; i<4; i++) label = new Label(line3, SWT.NONE);
			Composite type = new Composite(line3, SWT.NONE); sh.compositeStyle(type, 5, SWT.LEFT, SWT.CENTER);
			getFplEST = new Button(type, SWT.PUSH);
			firstEST = new Button(type, SWT.PUSH);
			prevEST = new Button(type, SWT.PUSH);
			nextEST = new Button(type, SWT.PUSH);
			lastEST = new Button(type, SWT.PUSH);
			bGetFPL(getFplEST, firstEST, prevEST, nextEST, lastEST, t7aEST, ID);
		}

		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbEST = new Text(c, SWT.BORDER);
		
		acs.setCompEST(t3aEST, t3bEST, t3cEST, t7aEST, t7bEST, t7cEST, t13aEST, t13bEST, t14aEST, t14bEST, t14cEST, t14dEST, t14eEST, t16aEST, t16bEST, t16cEST, t16dEST, tFbEST);
	}
	
	public static void initNewACP(Composite comp, final String ID) {
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tACP);
		Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 17, SWT.LEFT, SWT.CENTER);
		Label label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL); 
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13a, 60, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE);
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		} else {
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		}
//		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE);

		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t3aACP = new Text(line1, SWT.BORDER); t3aACP.setText("ACP"); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		t3bACP = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL); 
		t3cACP = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t7aACP = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
		t7bACP = new Button(line1, SWT.CHECK); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL); 
		t7cACP = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t13aACP = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		t13bACP = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER);
	
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			Composite line2 = new Composite(group, SWT.NONE); sh.compositeStyle(line2, 13, SWT.LEFT, SWT.CENTER);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line2, SWT.NONE); 
			for (int i=0; i<4; i++) label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); //sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); //sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); //sh.labelStyle(label, lbl16d, 80, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aACP = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			label = new Label(line2, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
			Composite type = new Composite(line2, SWT.NONE); sh.compositeStyle(type, 5, SWT.LEFT, SWT.CENTER);
			getFplACP = new Button(type, SWT.PUSH);
			firstACP = new Button(type, SWT.PUSH);
			prevACP = new Button(type, SWT.PUSH);
			nextACP = new Button(type, SWT.PUSH);
			lastACP = new Button(type, SWT.PUSH);
			bGetFPL(getFplACP, firstACP, prevACP, nextACP, lastACP, t7aACP, ID);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			t16bACP = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); //sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
			t16cACP = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); //sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
			Composite comp16 = new Composite(line2, SWT.NONE); sh.compositeStyle(comp16, 3, SWT.LEFT, SWT.CENTER);
			t16dACP = new Text(comp16, SWT.BORDER);
			
			disableDEP(t13bACP);
			disableDEST(t16bACP, t16cACP, t16dACP);
		} else {
			Composite line2 = new Composite(group, SWT.NONE); sh.compositeStyle(line2, 13, SWT.LEFT, SWT.CENTER);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16d, 80, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE); 
			for (int i=0; i<4; i++) label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aACP = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
			t16bACP = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
			t16cACP = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
			Composite comp16 = new Composite(line2, SWT.NONE); sh.compositeStyle(comp16, 3, SWT.LEFT, SWT.CENTER);
			t16dACP = new Text(comp16, SWT.BORDER);
			label = new Label(comp16, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			label = new Label(comp16, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
			Composite type = new Composite(line2, SWT.NONE); sh.compositeStyle(type, 5, SWT.LEFT, SWT.CENTER);
			getFplACP = new Button(type, SWT.PUSH);
			firstACP = new Button(type, SWT.PUSH);
			prevACP = new Button(type, SWT.PUSH);
			nextACP = new Button(type, SWT.PUSH);
			lastACP = new Button(type, SWT.PUSH);
			bGetFPL(getFplACP, firstACP, prevACP, nextACP, lastACP, t7aACP, ID);
		}

		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbACP = new Text(c, SWT.BORDER);
		
		acs.setCompACP(t3aACP, t3bACP, t3cACP, t7aACP, t7bACP, t7cACP, t13aACP, t13bACP, t16aACP, t16bACP, t16cACP, t16dACP, tFbACP);
	}
	
	public static void initNewLAM(Composite comp, final String ID) {
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tLAM);
		Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 8, SWT.LEFT, SWT.CENTER);
		Label label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle1(label, lbl3c, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		for (int i=0; i<2; i++) label = new Label(line1, SWT.NONE);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t3aLAM = new Text(line1, SWT.BORDER); t3aLAM.setText("LAM"); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t3bLAM = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		t3cLAM = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		label = new Label(line1, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);

		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbLAM = new Text(c, SWT.BORDER);
			
		acs.setCompLAM(t3aLAM, t3bLAM, t3cLAM, tFbLAM);
	}
	
	public static void initNewRQP(Composite comp, final String ID) {
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tRQP);
		Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 17, SWT.LEFT, SWT.CENTER);
		Label label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13a, 60, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t3aRQP = new Text(line1, SWT.BORDER); t3aRQP.setText("RQP"); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t3bRQP = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		t3cRQP = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t7aRQP = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
		t7bRQP = new Button(line1, SWT.CHECK); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);  
		t7cRQP = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t13aRQP = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t13bRQP = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER);
		Composite line2 = new Composite(group, SWT.NONE); sh.compositeStyle(line2, 13, SWT.LEFT, SWT.CENTER);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16d, 100, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl18dof, 70, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		for (int i=0; i<3; i++) label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t16aRQP = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t16bRQP = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
		t16cRQP = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
		t16dRQP = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		tDofRQP = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		label = new Label(line2, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);

		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbRQP = new Text(c, SWT.BORDER);
			
		acs.setCompMiniFPL(t3aRQP, t3bRQP, t3cRQP, t7aRQP, t7bRQP, t7cRQP, t13aRQP, t13bRQP, t16aRQP, t16bRQP, t16cRQP, t16dRQP, tDofRQP, tFbRQP);
	}
	
	public static void initNewRQS(Composite comp, final String ID) {
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tRQS);
		Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 17, SWT.LEFT, SWT.CENTER);
		Label label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 100, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13a, 60, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE);
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			label = new Label(line1, SWT.NONE); //sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		} else {
			label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		}
		label = new Label(line1, SWT.NONE);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t3aRQS = new Text(line1, SWT.BORDER); t3aRQS.setText("RQS"); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t3bRQS = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		t3cRQS = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t7aRQS = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
		t7bRQS = new Button(line1, SWT.CHECK); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);  
		t7cRQS = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t13aRQS = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t13bRQS = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER);
	
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			Composite line2 = new Composite(group, SWT.NONE); sh.compositeStyle(line2, 13, SWT.LEFT, SWT.CENTER);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl18dof, 70, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			for (int i=0; i<3; i++) label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); //sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); //sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); //sh.labelStyle(label, lbl16d, 100, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			
			
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aRQS = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			tDofRQS = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			label = new Label(line2, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
			Composite type = new Composite(line2, SWT.NONE); sh.compositeStyle(type, 5, SWT.CENTER, SWT.CENTER);
			getFplRQS = new Button(type, SWT.PUSH);
			firstRQS = new Button(type, SWT.PUSH);
			prevRQS = new Button(type, SWT.PUSH);
			nextRQS = new Button(type, SWT.PUSH);
			lastRQS = new Button(type, SWT.PUSH);
			bGetFPL(getFplRQS, firstRQS, prevRQS, nextRQS, lastRQS, t7aRQS, ID);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t16bRQS = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); //sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
			t16cRQS = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); //sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
			t16dRQS = new Text(line2, SWT.BORDER);
			
			disableDEP(t13bRQS);
			disableDEST(t16bRQS, t16cRQS, t16dRQS); 
		} else {
			Composite line2 = new Composite(group, SWT.NONE); sh.compositeStyle(line2, 13, SWT.LEFT, SWT.CENTER);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16d, 100, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl18dof, 70, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
			for (int i=0; i<3; i++) label = new Label(line2, SWT.NONE);
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			t16aRQS = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
			t16bRQS = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
			t16cRQS = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
			t16dRQS = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			tDofRQS = new Text(line2, SWT.BORDER);
			label = new Label(line2, SWT.LEFT); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
			label = new Label(line2, SWT.LEFT); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
			Composite type = new Composite(line2, SWT.NONE); sh.compositeStyle(type, 5, SWT.CENTER, SWT.CENTER);
			getFplRQS = new Button(type, SWT.PUSH);
			firstRQS = new Button(type, SWT.PUSH);
			prevRQS = new Button(type, SWT.PUSH);
			nextRQS = new Button(type, SWT.PUSH);
			lastRQS = new Button(type, SWT.PUSH);
			bGetFPL(getFplRQS, firstRQS, prevRQS, nextRQS, lastRQS, t7aRQS, ID);
		}
		
		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbRQS = new Text(c, SWT.BORDER);
			
		acs.setCompMiniFPL(t3aRQS, t3bRQS, t3cRQS, t7aRQS, t7bRQS, t7cRQS, t13aRQS, t13bRQS, t16aRQS, t16bRQS, t16cRQS, t16dRQS, tDofRQS, tFbRQS);
	}
	
	public static void initNewSPL(Composite comp, final String ID) {
//		rff.readConfiguration();
		final Group group = new Group(comp, SWT.NONE); sh.groupStyle(group, 1, "");//Title.tSPL);
		Composite line1 = new Composite(group, SWT.NONE); sh.compositeStyle(line1, 17, SWT.LEFT, SWT.CENTER);
		Label label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3b, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl3c, 140, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7a, 85, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7b, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl7c, 50, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		label = new Label(line1, SWT.NONE); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13a, 60, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, lbl13b, 50, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line1, SWT.NONE);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "(", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t3aSPL = new Text(line1, SWT.BORDER); t3aSPL.setText("SPL"); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t3bSPL = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		t3cSPL = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t7aSPL = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER); sh.labelStyle(label, "/", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL); 
		t7bSPL = new Button(line1, SWT.CHECK); 
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		t7cSPL = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.RIGHT); sh.labelStyle(label, "-", 40, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t13aSPL = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t13bSPL = new Text(line1, SWT.BORDER);
		label = new Label(line1, SWT.CENTER);
		Composite line2 = new Composite(group, SWT.NONE); sh.compositeStyle(line2, 13, SWT.LEFT, SWT.CENTER);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16a, 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16b, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16c, 140, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, lbl16d, 80, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<5; i++) label = new Label(line2, SWT.NONE);
		label = new Label(line2, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t16aSPL = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t16bSPL = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
		t16cSPL = new Text(line2, SWT.BORDER);
		label = new Label(line2, SWT.RIGHT); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 40);
		t16dSPL = new Text(line2, SWT.BORDER);
		for (int i=0; i<4; i++) label = new Label(line2, SWT.NONE); 
		Composite type = new Composite(line2, SWT.NONE); sh.compositeStyle(type, 5, SWT.LEFT, SWT.CENTER);
		getFplSPL = new Button(type, SWT.PUSH);
		firstSPL = new Button(type, SWT.PUSH);
		prevSPL = new Button(type, SWT.PUSH);
		nextSPL = new Button(type, SWT.PUSH);
		lastSPL = new Button(type, SWT.PUSH);
		bGetFPL(getFplSPL, firstSPL, prevSPL, nextSPL, lastSPL, t7aSPL, ID);
		
		Composite line7 = new Composite(group, SWT.NONE); sh.compositeStyle(line7, 2, SWT.LEFT, SWT.CENTER);
		label = new Label(line7, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line7, SWT.NONE); sh.labelStyle(label, lbl18, 210, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Composite line8 = new Composite(group, SWT.NONE); sh.compositeStyle(line8, 11, SWT.LEFT, SWT.CENTER);
		label = new Label(line8, SWT.RIGHT); sh.labelStyle(label, "-", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "STS/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compSts = new Composite(line8, SWT.NONE); sh.compositeStyle(compSts, 2, SWT.LEFT, SWT.TOP);
		tStsSPL = new Text(compSts, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		bSTSSPL = new Button(compSts, SWT.PUSH);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "PBN/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compPbn = new Composite(line8, SWT.NONE); sh.compositeStyle(compPbn, 2, SWT.LEFT, SWT.TOP);
		tPbnSPL = new Text(compPbn, SWT.BORDER);
		bPBNSPL = new Button(compPbn, SWT.PUSH);
		
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "NAV/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tNavSPL = new Text(line8, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//--------------
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "COM/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compCom = new Composite(line8, SWT.NONE); sh.compositeStyle(compCom, 1, SWT.CENTER, SWT.TOP);
		tComSPL = new Text(compCom, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DAT/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compDat = new Composite(line8, SWT.NONE); sh.compositeStyle(compDat, 1, SWT.CENTER, SWT.TOP);
		tDatSPL = new Text(compDat, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "SUR/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tSurSPL = new Text(line8, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DEP/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compDep = new Composite(line8, SWT.NONE); sh.compositeStyle(compDep, 1, SWT.CENTER, SWT.TOP);
		tDepSPL = new Text(compDep, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DEST/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compDest = new Composite(line8, SWT.NONE); sh.compositeStyle(compDest, 1, SWT.CENTER, SWT.TOP);
		tDestSPL = new Text(compDest, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DOF/", 50, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.MANDATORY);
		tDofSPL = new Text(line8, SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "REG/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compReg = new Composite(line8, SWT.NONE); sh.compositeStyle(compReg, 1, SWT.LEFT, SWT.TOP);
		tRegSPL = new Text(compReg, SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "EET/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compEet = new Composite(line8, SWT.NONE); sh.compositeStyle(compEet, 1, SWT.CENTER, SWT.TOP);
		tEetSPL = new Text(compEet, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "SEL/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tSelSPL = new Text(line8, SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "TYP/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compTyp = new Composite(line8, SWT.NONE); sh.compositeStyle(compTyp, 1, SWT.CENTER, SWT.TOP);
		tTypSPL = new Text(compTyp, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "CODE/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compCode = new Composite(line8, SWT.NONE); sh.compositeStyle(compCode, 1, SWT.LEFT, SWT.TOP);
		tCodeSPL = new Text(compCode, SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "DLE/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tDleSPL = new Text(line8, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "OPR/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compOpr = new Composite(line8, SWT.NONE); sh.compositeStyle(compOpr, 1, SWT.CENTER, SWT.TOP);
		tOprSPL = new Text(compOpr, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "ORGN/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compOrgn = new Composite(line8, SWT.NONE); sh.compositeStyle(compOrgn, 1, SWT.CENTER, SWT.TOP);
		tOrgnSPL = new Text(compOrgn, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "PER/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tPerSPL = new Text(line8, SWT.BORDER); 
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "ALTN/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compAltn = new Composite(line8, SWT.NONE); sh.compositeStyle(compAltn, 1, SWT.CENTER, SWT.TOP);
		tAltnSPL = new Text(compAltn, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "RALT/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compRalt = new Composite(line8, SWT.NONE); sh.compositeStyle(compRalt, 1, SWT.CENTER, SWT.TOP);
		tRaltSPL = new Text(compRalt, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "TALT/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		tTaltSPL = new Text(line8, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		for (int i=0; i < 2; i++) label = new Label(line8, SWT.NONE);
		//-----
		label = new Label(line8, SWT.NONE);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "RIF/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compRif = new Composite(line8, SWT.NONE); sh.compositeStyle(compRif, 1, SWT.CENTER, SWT.TOP);
		tRifSPL = new Text(compRif, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line8, SWT.NONE); sh.labelStyle(label, "RMK/", 50, SWT.LEFT, SWT.TOP, SWT.NORMAL, Colors.NORMAL);
		Composite compRmk = new Composite(line8, SWT.NONE); sh.compositeStyle(compRmk, 1, SWT.CENTER, SWT.TOP);
		tRmkSPL = new Text(compRmk, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		Composite compSym = new Composite(line8, SWT.NONE); sh.compositeStyle(compSym, 2, SWT.CENTER, SWT.TOP);
		label = new Label(compSym, SWT.NONE); sh.labelStyle(label, ")", 10, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		label = new Label(compSym, SWT.NONE); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.TOP, 30);
		for (int i=0; i < 4; i++) label = new Label(line8, SWT.NONE);
		
		Composite typeA = new Composite(group, SWT.NONE); sh.compositeStyle(typeA, 1, SWT.CENTER, SWT.CENTER);
		label = new Label(typeA, SWT.CENTER | SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelSeparator(label, iSeparator, 10);
		// type19
		Composite line9 = new Composite(group, SWT.NONE); sh.compositeStyle(line9, 1, SWT.LEFT, SWT.CENTER);
		label = new Label(line9, SWT.CENTER); sh.labelStyle(label, lblSUPINFO, iSeparator, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		
		Composite line10 = new Composite(group, SWT.NONE); sh.compositeStyle(line10, 2, SWT.LEFT, SWT.CENTER);
		label = new Label(line10, SWT.NONE); sh.labelStyle(label, "", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line10, SWT.NONE); sh.labelStyle(label, lbl19, 120, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		
		Composite line11 = new Composite(group, SWT.NONE); sh.compositeStyle(line11, 9, SWT.LEFT, SWT.CENTER);
		// type19abc
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, lbl19a, 178, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, lbl19b, 221, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, lbl19c, 180, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		// 19abc
		label = new Label(line11, SWT.RIGHT); sh.labelStyle(label, "-", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "E/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19aSPL = new Text(line11, SWT.BORDER);
		label = new Label(line11, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.CENTER, SWT.CENTER, 20);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "P/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19bSPL = new Text(line11, SWT.BORDER);
		label = new Label(line11, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.CENTER, SWT.CENTER, 20);
		label = new Label(line11, SWT.NONE); sh.labelStyle(label, "R/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		Composite cbtn19c = new Composite(line11, SWT.NONE); sh.compositeStyle(cbtn19c, 6, SWT.LEFT, SWT.CENTER);
		t19cUhfSPL = new Text(cbtn19c, SWT.BORDER);
		t19cUhfSPL.setCursor(cs1);
		t19cUhfSPL.setEditable(false);
		t19cUhfSPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19cUhfSPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19cUhfSPL.getText().compareTo("U")==0)
					t19cUhfSPL.setText("X");
				else t19cUhfSPL.setText("U");
				t19bSPL.setFocus();
			}
		});
		t19cUhfSPL.setText("U");
	    Label l_1 = new Label(cbtn19c,SWT.LEFT);
	    l_1.setText("UHF		");
		
		t19cVhfSPL = new Text(cbtn19c, SWT.BORDER);
		t19cVhfSPL.setCursor(cs1);
		t19cVhfSPL.setEditable(false);
		t19cVhfSPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19cVhfSPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19cVhfSPL.getText().compareTo("V")==0)
					t19cVhfSPL.setText("X");
				else t19cVhfSPL.setText("V");
				t19bSPL.setFocus();
			}
		});
		t19cVhfSPL.setText("V");
	    Label l_2 = new Label(cbtn19c,SWT.LEFT);
	    l_2.setText("VHF		");
	    
		t19cEltSPL = new Text(cbtn19c, SWT.BORDER);
		t19cEltSPL.setCursor(cs1);
		t19cEltSPL.setEditable(false);
		t19cEltSPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19cEltSPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19cEltSPL.getText().compareTo("E")==0)
					t19cEltSPL.setText("X");
				else t19cEltSPL.setText("E");
				t19bSPL.setFocus();
			}
		});
		t19cEltSPL.setText("E");
	    Label l_3 = new Label(cbtn19c,SWT.LEFT);
	    l_3.setText("ELT		");
	    
		Composite line12 = new Composite(group, SWT.NONE); sh.compositeStyle(line12, 10, SWT.LEFT, SWT.CENTER);
		// type19de
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, lbl19d, 190, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 55, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, lbl19e, 80, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line12, SWT.NONE); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		// de
		Composite line122 = new Composite(group, SWT.NONE); sh.compositeStyle(line122, 10, SWT.LEFT, SWT.CENTER);
		label = new Label(line122, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
		t19sSPL = new Text(line122, SWT.BORDER);
		t19sSPL.setCursor(cs1);
		t19sSPL.setEditable(false);
		t19sSPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19sSPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if ((t19sSPL.getText().isEmpty())||(t19sSPL.getText().compareTo("S")==0)) {
					t19sSPL.setText("X");
					t19dPSPL.setText("X");
					t19dDSPL.setText("X");
					t19dMSPL.setText("X");
					t19dJSPL.setText("X");
				}
				else {
					t19sSPL.setText("S");
					t19dPSPL.setText("P");
					t19dDSPL.setText("D");
					t19dMSPL.setText("M");
					t19dJSPL.setText("J");
				}
				t19bSPL.setFocus();
			}
		});
		t19sSPL.setText("S");
		label = new Label(line122, SWT.NONE); sh.labelStyle(label, " / ", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);

		Composite cbtn19d = new Composite(line122, SWT.NONE); sh.compositeStyle(cbtn19d, 8, SWT.LEFT, SWT.CENTER);

		t19dPSPL = new Text(cbtn19d, SWT.BORDER);
		t19dPSPL.setCursor(cs1);
		t19dPSPL.setEditable(false);
		t19dPSPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19dPSPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19dPSPL.getText().compareTo("P")==0)
					t19dPSPL.setText("X");
				else if (t19sSPL.getText().compareTo("S")==0) t19dPSPL.setText("P");
				t19bSPL.setFocus();
			}
		});
		t19dPSPL.setText("P");
	    Label l_4 = new Label(cbtn19d,SWT.LEFT);
	    l_4.setText("POLAR	");
	    
	    t19dDSPL = new Text(cbtn19d, SWT.BORDER);
		t19dDSPL.setCursor(cs1);
		t19dDSPL.setEditable(false);
		t19dDSPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19dDSPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19dDSPL.getText().compareTo("D")==0)
					t19dDSPL.setText("X");
				else if (t19sSPL.getText().compareTo("S")==0) t19dDSPL.setText("D");
				t19bSPL.setFocus();
			}
		});
		t19dDSPL.setText("D");
	    Label l_5 = new Label(cbtn19d,SWT.LEFT);
	    l_5.setText("DESERT	");

		t19dMSPL = new Text(cbtn19d, SWT.BORDER);
		t19dMSPL.setCursor(cs1);
		t19dMSPL.setEditable(false);
		t19dMSPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19dMSPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19dMSPL.getText().compareTo("M")==0)
					t19dMSPL.setText("X");
				else if (t19sSPL.getText().compareTo("S")==0) t19dMSPL.setText("M");
				t19bSPL.setFocus();
			}
		});
		t19dMSPL.setText("M");
	    Label l_6 = new Label(cbtn19d,SWT.LEFT);
	    l_6.setText("MARITIME	");

		t19dJSPL = new Text(cbtn19d, SWT.BORDER);
		t19dJSPL.setCursor(cs1);
		t19dJSPL.setEditable(false);
		t19dJSPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19dJSPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19dJSPL.getText().compareTo("J")==0)
					t19dJSPL.setText("X");
				else if (t19sSPL.getText().compareTo("S")==0) t19dJSPL.setText("J");
				t19bSPL.setFocus();
			}
		});
		t19dJSPL.setText("J");
	    Label l_7 = new Label(cbtn19d,SWT.LEFT);
	    l_7.setText("JUNGLE		");

		label = new Label(line122, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.CENTER, SWT.CENTER, 20);
		//label = new Label(line12, SWT.NONE); sh.labelStyle(label, "J/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19jSPL = new Text(line122, SWT.BORDER);
		t19jSPL.setCursor(cs1);
		t19jSPL.setEditable(false);
		t19jSPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19jSPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if ((t19jSPL.getText().isEmpty())||(t19jSPL.getText().compareTo("J")==0)) {
					t19jSPL.setText("X");
					t19eLSPL.setText("X");
					t19eFSPL.setText("X");
					t19eUSPL.setText("X");
					t19eVSPL.setText("X");
				}
				else {
					t19jSPL.setText("J");
					t19eLSPL.setText("L");
					t19eFSPL.setText("F");
					t19eUSPL.setText("U");
					t19eVSPL.setText("V");
				}
				t19bSPL.setFocus();
			}
		});
		label = new Label(line122, SWT.NONE); sh.labelStyle(label, " / ", 10, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t19jSPL.setText("J");
		
		Composite cbtn19e = new Composite(line122, SWT.NONE); sh.compositeStyle(cbtn19e, 8, SWT.LEFT, SWT.CENTER);

		t19eLSPL = new Text(cbtn19e, SWT.BORDER);
		t19eLSPL.setCursor(cs1);
		t19eLSPL.setEditable(false);
		t19eLSPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19eLSPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19eLSPL.getText().compareTo("L")==0)
					t19eLSPL.setText("X");
				else if (t19jSPL.getText().compareTo("J")==0) t19eLSPL.setText("L");
				t19bSPL.setFocus();
			}
		});
		t19eLSPL.setText("L");
	    Label l_8 = new Label(cbtn19e,SWT.LEFT);
	    l_8.setText("LIGHT	");
	    
		t19eFSPL = new Text(cbtn19e, SWT.BORDER);
		t19eFSPL.setCursor(cs1);
		t19eFSPL.setEditable(false);
		t19eFSPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19eFSPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19eFSPL.getText().compareTo("F")==0)
					t19eFSPL.setText("X");
				else if (t19jSPL.getText().compareTo("J")==0) t19eFSPL.setText("F");
				t19bSPL.setFocus();
			}
		});
		t19eFSPL.setText("F");
	    Label l_9 = new Label(cbtn19e,SWT.LEFT);
	    l_9.setText("FLOURES		");

		t19eUSPL = new Text(cbtn19e, SWT.BORDER);
		t19eUSPL.setCursor(cs1);
		t19eUSPL.setEditable(false);
		t19eUSPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19eUSPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19eUSPL.getText().compareTo("U")==0)
					t19eUSPL.setText("X");
				else if (t19jSPL.getText().compareTo("J")==0) t19eUSPL.setText("U");
				t19bSPL.setFocus();
			}
		});
		t19eUSPL.setText("U");
	    Label l_10 = new Label(cbtn19e,SWT.LEFT);
	    l_10.setText("UHF		");
	    
		t19eVSPL = new Text(cbtn19e, SWT.BORDER);
		t19eVSPL.setCursor(cs1);
		t19eVSPL.setEditable(false);
		t19eVSPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19eVSPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (t19eVSPL.getText().compareTo("V")==0)
					t19eVSPL.setText("X");
				else if (t19jSPL.getText().compareTo("J")==0) t19eVSPL.setText("V");
				t19bSPL.setFocus();
			}
		});
		t19eVSPL.setText("V");
	    Label l_11 = new Label(cbtn19e,SWT.LEFT);
	    l_11.setText("VHF	");		
	    
	    for (int i=0; i<2; i++) label = new Label(line122, SWT.NONE); 
		
		Composite line13 = new Composite(group, SWT.NONE); sh.compositeStyle(line13, 10, SWT.LEFT, SWT.CENTER);
		// type19f
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Composite clbl19f = new Composite(line13, SWT.NONE); sh.compositeStyle(clbl19f, 5, SWT.LEFT, SWT.CENTER);
		label = new Label(clbl19f, SWT.NONE); sh.labelStyle(label, lbl19fnum, 100, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(clbl19f, SWT.NONE); sh.labelStyle(label, "", 8, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(clbl19f, SWT.NONE); sh.labelStyle(label, lbl19fcap, 90, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(clbl19f, SWT.NONE); sh.labelStyle(label, "", 15, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(clbl19f, SWT.NONE); sh.labelStyle(label, lbl19fcov, 61, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, lbl19fcol, 70, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line13, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<3; i++) label = new Label(line13, SWT.NONE);
		
		// f
		Composite line133 = new Composite(group, SWT.NONE); sh.compositeStyle(line133, 10, SWT.LEFT, SWT.CENTER);
		label = new Label(line133, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);

		//label = new Label(line13, SWT.NONE); sh.labelStyle(label, "D/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19dSPL = new Text(line133, SWT.BORDER);
		t19dSPL.setCursor(cs1);
		t19dSPL.setEditable(false);
		t19dSPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19dSPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if ((t19dSPL.getText().isEmpty())||(t19dSPL.getText().compareTo("D")==0)) {
					t19dSPL.setText("X");
					t19NumSPL.setText("");
					t19CapSPL.setText("");
					t19ColSPL.setText("");
					t19cSPL.setText("X");
					t19NumSPL.setEnabled(false);
					t19CapSPL.setEnabled(false);
					t19ColSPL.setEnabled(false);
					t19cSPL.setEnabled(false);
				}
				else {
					t19dSPL.setText("D");
					t19cSPL.setText("C");
					t19NumSPL.setEnabled(true);
					t19CapSPL.setEnabled(true);
					t19ColSPL.setEnabled(true);
					t19cSPL.setEnabled(true);
				}
				t19bSPL.setFocus();
			}
		});
		t19dSPL.setText("D");
		label = new Label(line133, SWT.NONE); sh.labelStyle(label, " / " , 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);

		Composite ctxt19f = new Composite(line133, SWT.NONE); sh.compositeStyle(ctxt19f, 5, SWT.LEFT, SWT.CENTER);
		t19NumSPL = new Text(ctxt19f, SWT.BORDER);
		label = new Label(ctxt19f, SWT.NONE); sh.labelStyle(label, "", 77, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		t19CapSPL = new Text(ctxt19f, SWT.BORDER);
		
		label = new Label(ctxt19f, SWT.NONE); sh.labelStyle(label, "", 27, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);

		label = new Label(ctxt19f, SWT.CENTER); sh.labelStyle(label, "", 45, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19cSPL = new Text(line133, SWT.BORDER);
		t19cSPL.setCursor(cs1);
		t19cSPL.setEditable(false);
		t19cSPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19cSPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if ((t19cSPL.getText().isEmpty())||(t19cSPL.getText().compareTo("C")==0)) {
					t19cSPL.setText("X");
					//t19ColSPL.setText("");
					//t19ColSPL.setEnabled(false);
				}
				else {
					t19cSPL.setText("C");
					//t19ColSPL.setEnabled(true);
				}
				t19bSPL.setFocus();
			}
		});
		t19cSPL.setText("X");
		
		label = new Label(line133, SWT.NONE); sh.labelStyle(label, "", 40, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line133, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.CENTER, SWT.CENTER, 20);
		t19ColSPL = new Text(line133, SWT.BORDER);
		label = new Label(line133, SWT.NONE); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
		//for (int i=0; i<2; i++) label = new Label(line133, SWT.NONE); 

		Composite line14 = new Composite(group, SWT.NONE); sh.compositeStyle(line14, 9, SWT.LEFT, SWT.CENTER);
		// type19g
		label = new Label(line14, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line14, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line14, SWT.NONE); sh.labelStyle(label, lbl19g, 310, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<6; i++) label = new Label(line14, SWT.NONE); 
		// g
		label = new Label(line14, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line14, SWT.NONE); sh.labelStyle(label, "A/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19AirSPL = new Text(line14, SWT.BORDER);
		for (int i=0; i<6; i++) label = new Label(line14, SWT.NONE); 

		Composite line15 = new Composite(group, SWT.NONE); sh.compositeStyle(line15, 10, SWT.LEFT, SWT.CENTER);
		// type19h
		label = new Label(line15, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line15, SWT.NONE); sh.labelStyle(label, lbl19h, 90, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line15, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<7; i++) label = new Label(line15, SWT.NONE); 
		// h
		
		Composite line155 = new Composite(group, SWT.NONE); sh.compositeStyle(line155, 10, SWT.LEFT, SWT.CENTER);
		label = new Label(line155, SWT.NONE); sh.labelimg(label, Images.img_arrow, SWT.LEFT, SWT.CENTER, 20);
		//label = new Label(line15, SWT.NONE); sh.labelStyle(label, "N/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19nSPL = new Text(line155, SWT.BORDER);
		t19nSPL.setCursor(cs1);
		t19nSPL.setEditable(false);
		t19nSPL.setFont(new Font(TeleSplashScreen2016IP.display, "Courier", 12, SWT.BOLD));
		t19nSPL.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if ((t19nSPL.getText().isEmpty())||(t19nSPL.getText().compareTo("N")==0)) {
					t19nSPL.setText("X");
					t19RemSPL.setEnabled(false);
					t19RemSPL.setText("");
				}
				else {
					t19nSPL.setText("N");
					t19RemSPL.setEnabled(true);
				}
				t19bSPL.setFocus();
			}
		});
		t19nSPL.setText("N");
		label = new Label(line155, SWT.NONE); sh.labelStyle(label, " / ", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);

		t19RemSPL = new Text(line155, SWT.BORDER);
		label = new Label(line155, SWT.NONE); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
		for (int i=0; i<5; i++) label = new Label(line155, SWT.NONE); 

		Composite line16 = new Composite(group, SWT.NONE); sh.compositeStyle(line16, 9, SWT.LEFT, SWT.CENTER);
		// type19i
		label = new Label(line16, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line16, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line16, SWT.NONE); sh.labelStyle(label, lbl19i, 180, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		for (int i=0; i<6; i++) label = new Label(line16, SWT.NONE); 
		// i
		label = new Label(line16, SWT.NONE); sh.labelStyle(label, "", 20, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(line16, SWT.NONE); sh.labelStyle(label, "C/", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		t19PilSPL = new Text(line16, SWT.BORDER);
		label = new Label(line16, SWT.NONE); sh.labelimg(label, Images.img_enter, SWT.LEFT, SWT.CENTER, 30);
		for (int i=0; i<5; i++) label = new Label(line16, SWT.NONE); 

		separatorFB(group);
		Composite c = new Composite(group, SWT.NONE); sh.compositeStyle(c, 3, SWT.LEFT, SWT.TOP);
		Label l = new Label(c, SWT.NONE); sh.labelStyle1(l, lblFB, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFbSPL = new Text(c, SWT.BORDER);
				
		addListenerSPL();
		acs.setCompSPL(t3aSPL, t3bSPL, t3cSPL, t7aSPL, t7bSPL, t7cSPL, t13aSPL, t13bSPL, t16aSPL, t16bSPL, t16cSPL, t16dSPL, tNavSPL, tComSPL, tDatSPL, tSurSPL, tDepSPL, tDestSPL, tDofSPL, tRegSPL, tEetSPL, tSelSPL, tTypSPL, tCodeSPL, tDleSPL, tOprSPL, tOrgnSPL, tPerSPL, tAltnSPL, tRaltSPL, tTaltSPL, tRifSPL, tRmkSPL, tPbnSPL, tStsSPL, t19aSPL, t19bSPL, t19cUhfSPL, t19cVhfSPL, t19cEltSPL, t19dPSPL, t19dDSPL, t19dMSPL, t19dJSPL, t19eLSPL, t19eFSPL, t19eUSPL, t19eVSPL, t19NumSPL, t19CapSPL, t19ColSPL, t19AirSPL, t19RemSPL, t19PilSPL, tFbSPL);
	}
	
	/**
	 * ----------------------------------------------------------------------------------------------------------------------------	
	 * mengambil isi masing-masing type dari database, dikelompokkan per type 
	 * ----------------------------------------------------------------------------------------------------------------------------
	 */
	static void editType3(Text t3a, Text t3b, Text t3c) {
		t3a.setText(jdbc.get3a());
		t3b.setText(jdbc.get3b());
		t3c.setText(jdbc.get3c());
	}
	
	static void editType5(Combo c5a, Text t5b, Text t5c) {
		c5a.setText(jdbc.get5a());
		t5b.setText(jdbc.get5b());
		t5c.setText(jdbc.get5c());
		
//		s="";
//		sdb=jdbc.get5a();
//		is=0;
//		for (is=0;is<Combos.cType5a.length;is++) {
//			if (sdb.compareTo(Combos.cType5a[is])==0) {s=Combos.cType5a[is];break;}
//		}
//		t5a.setSelection(is);
//		t5a.showSelection();
//		inilist5aALR=0;
	}
	
	static void editType5(Text t5a, Text t5b, Text t5c) {
		t5a.setText(jdbc.get5a());
		t5b.setText(jdbc.get5b());
		t5c.setText(jdbc.get5c());
		
//		s="";
//		sdb=jdbc.get5a();
//		is=0;
//		for (is=0;is<Combos.cType5a.length;is++) {
//			if (sdb.compareTo(Combos.cType5a[is])==0) {s=Combos.cType5a[is];break;}
//		}
//		t5a.setSelection(is);
//		t5a.showSelection();
//		inilist5aALR=0;
	}
	
	static void editType7(Text t7a, Button t7b, Text t7c) {
		t7a.setText(jdbc.get7a());
		if (jdbc.get7b().toString().equals("A")) t7b.setSelection(true);
		t7c.setText(jdbc.get7c());
	}
	
	static void editType8(Text t8a, Text t8b) {
		t8a.setText(jdbc.get8a());
		t8b.setText(jdbc.get8b());
	}
	
	static void editType8(Combo c8a, Combo c8b) {
		c8a.setText(jdbc.get8a());
		c8b.setText(jdbc.get8b());
//		s="";
//		sdb=jdbc.get8a();
//		is=0;
//		for (is=0;is<Combos.cType8a.length;is++) {
//			if (sdb.compareTo(Combos.cType8a[is])==0) {s=Combos.cType8a[is];break;}
//		}
//		t8a.setSelection(is);
//		t8a.showSelection();
//		inilist8aALR=0;
//		inic8aFPL=0;
//		inic8aCPL=0;
//
//		s="";
//		sdb=jdbc.get8b();
//		is=0;
//		for (is=0;is<Combos.cType8b.length;is++) {
//			if (sdb.compareTo(Combos.cType8b[is])==0) {s=Combos.cType8b[is];break;}
//		}
//		t8b.setSelection(is);
//		t8b.showSelection();
//		inilist8bALR=0;
//		inic8bFPL=0;
//		inic8bCPL=0;
	}
	
	static void editType9(Text t9a, Text t9b, Text t9c) {
		t9a.setText(jdbc.get9a());
		t9b.setText(jdbc.get9b());
		t9c.setText(jdbc.get9c());
	}
	
	static void editType9(Text t9a, Text t9b, Combo c9c) {
		t9a.setText(jdbc.get9a());
		t9b.setText(jdbc.get9b());
		c9c.setText(jdbc.get9c());
		
//		s="";
//		sdb=jdbc.get9c();
//		is=0;
//		for (is=0;is<Combos.cType9c.length;is++) {
//			if (sdb.compareTo(Combos.cType9c[is])==0) {s=Combos.cType9c[is];break;}
//		}
//		t9c.setSelection(is);
//		t9c.showSelection();
//		inic9cFPL=0;
//		inilist9cALR=0;
//		inic9cCPL=0;
	}
	
	static void editType10(Text t10a, Text t10b) {
		t10a.setText(jdbc.get10a());
		t10b.setText(jdbc.get10b());
	}
	
	static void editType13(Text t13a, Text t13b) {
		t13a.setText(jdbc.get13a());
		t13b.setText(jdbc.get13b());
	}
	
	static void editType14(Text t14a, Text t14b, Text t14c, Text t14d, Text t14e) {
		t14a.setText(jdbc.get14a());
		t14b.setText(jdbc.get14b());
		t14c.setText(jdbc.get14c());
		t14d.setText(jdbc.get14d());
		t14e.setText(jdbc.get14e());
	}
	
	static void editType15(Text t15a, Text t15b, Text t15c) {
		t15a.setText(jdbc.get15a());
		t15b.setText(jdbc.get15b());
		t15c.setText(jdbc.get15c());
	}
	
	static void editType16(Text t16a, Text t16b, Text t16c, Text t16d) {
		t16a.setText(jdbc.get16a());
		t16b.setText(jdbc.get16b());
		t16c.setText(jdbc.get16c());
		t16d.setText(jdbc.get16c2nd());
	}
	
	static void editType18(Text tReg) { //ini percobaan
		String s_Reg="",s_Dof="",type18="";//,sEet="",sPer="", sSel="",sSts="",aChar="";//,sDof="",sReg="",sPbn="",sSur="",sCode="",sDle="",sOrgn="",sTalt="";
		String other="",sPbn="",sNav="", sCom="",sDat="",sSur="", sDep="",sDest="",sDof="", sReg="",
		sTyp="",sCode="",sDle="",sOpr="",sOrgn="",sAltn="",sRalt="",sTalt="", sRif="",sRmk="",x="";
		
		//REG/
		if (jdbc.get18a().compareTo("REG/0")==0) { s_Reg=jdbc.get18Reg(); } //versi lama: REG/0, maka ambil versi baru  
		else { s_Reg=jdbc.get18a(); } //ambil versi lama
		if (s_Reg.equals("0")) s_Reg="";
//		t18a.setText(s_Reg);
		tReg.setText(s_Reg);
		//DOF/
		if (jdbc.get18b().compareTo("DOF/0")==0) { s_Dof=jdbc.get18Dof(); } 
		else { s_Dof=jdbc.get18b(); }
		if (s_Dof.equals("0")) s_Dof="";
//		t18b.setText(s_Dof);
//		tDof.setText(s_Dof);
		
		//ALL18/
		type18 = jdbc.get18();
		if (type18.startsWith(" ")) type18=type18.replaceFirst("\\s+", "");
		if (type18.compareTo("0")==0) type18="";
		
		if (!type18.equals("")) {
			if (type18.startsWith(" ")) type18 = type18.replaceFirst("\\s+", ""); //hapus spasi di awal Item 18
 	        if (type18.contains("\n")) {
 	        	//aChar = new Character((char)94).toString(); //kode ASCII 94=^,126='~'
 	        	type18 = type18.replaceAll("\n", "^");//aChar);
 	        } 
 	        type18 = type18.replaceAll("/", "~");
 			//dari db untuk fungsi edit
 			if (type18.contains("STS~")) type18 = type18.replace("STS~", "STS/");
 			if (type18.contains("PBN~")) type18 = type18.replace("PBN~", "PBN/");
 			if (type18.contains("NAV~")) type18 = type18.replace("NAV~", "NAV/");
 			if (type18.contains("COM~")) type18 = type18.replace("COM~", "COM/");
 			if (type18.contains("DAT~")) type18 = type18.replace("DAT~", "DAT/");
 			if (type18.contains("SUR~")) type18 = type18.replace("SUR~", "SUR/");
 			if (type18.contains("DEP~")) type18 = type18.replace("DEP~", "DEP/");
 			if (type18.contains("DEST~")) type18 = type18.replace("DEST~", "DEST/");
 			if (type18.contains("REG~")) type18 = type18.replace("REG~", "REG/");
 			if (type18.contains("DOF~")) type18 = type18.replace("DOF~", "DOF/");
 			if (type18.contains("EET~")) type18 = type18.replace("EET~", "EET/");
 			if (type18.contains("SEL~")) type18 = type18.replace("SEL~", "SEL/");
 			if (type18.contains("TYP~")) type18 = type18.replace("TYP~", "TYP/");
 			if (type18.contains("CODE~")) type18 = type18.replace("CODE~", "CODE/");
 			if (type18.contains("DLE~")) type18 = type18.replace("DLE~", "DLE/");
 			if (type18.contains("OPR~")) type18 = type18.replace("OPR~", "OPR/");
 			if (type18.contains("ORGN~")) type18 = type18.replace("ORGN~", "ORGN/");
 			if (type18.contains("PER~")) type18 = type18.replace("PER~", "PER/");
 			if (type18.contains("ALTN~")) type18 = type18.replace("ALTN~", "ALTN/");
 			if (type18.contains("RALT~")) type18 = type18.replace("RALT~", "RALT/");
 			if (type18.contains("TALT~")) type18 = type18.replace("TALT~", "TALT/");
 			if (type18.contains("RIF~")) type18 = type18.replace("RIF~", "RIF/");
 			if (type18.contains("RMK~")) type18 = type18.replace("RMK~", "RMK/");
 			//-------------------------------------------------------------------
 	        sPbn="";sSur="";sDof="";sReg="";sCode="";sDle="";sOrgn="";sTalt="";
 	        String sub[] = type18.split(" ");
			for (int n=0; n<sub.length; n++) {
				if (sub[n].contains("/")) { x=sub[n]; } 
				else { x+=" "+sub[n]; }
				
				if (x.startsWith("STS/")) sSts = x.substring(4, x.length());
				else if (x.startsWith("PBN/")) sPbn = x.substring(4, x.length());
				else if (x.startsWith("NAV/")) sNav = x.substring(4, x.length());
				else if (x.startsWith("COM/")) sCom = x.substring(4, x.length());
				else if (x.startsWith("DAT/")) sDat = x.substring(4, x.length());
				else if (x.startsWith("SUR/")) sSur = x.substring(4, x.length());
				else if (x.startsWith("DEP/")) sDep = x.substring(4, x.length());
				else if (x.startsWith("DEST/")) sDest = x.substring(5, x.length());
				else if (x.startsWith("DOF/")) sDof = x.substring(4, x.length());
				else if (x.startsWith("REG/")) sReg = x.substring(4, x.length());
				else if (x.startsWith("EET/")) sEet = x.substring(4, x.length());
				else if (x.startsWith("SEL/")) sSel = x.substring(4, x.length());
				else if (x.startsWith("TYP/")) sTyp = x.substring(4, x.length());
				else if (x.startsWith("CODE/")) sCode = x.substring(5, x.length());
				else if (x.startsWith("DLE/")) sDle = x.substring(4, x.length());
				else if (x.startsWith("OPR/")) sOpr = x.substring(4, x.length());
				else if (x.startsWith("ORGN/")) sOrgn = x.substring(5, x.length());
				else if (x.startsWith("ALTN/")) sAltn = x.substring(5, x.length());
				else if (x.startsWith("RALT/")) sRalt = x.substring(5, x.length());
				else if (x.startsWith("TALT/")) sTalt = x.substring(5, x.length());
				else if (x.startsWith("RIF/")) sRif = x.substring(4, x.length());
				else if (x.startsWith("RMK/")) sRmk = x.substring(4, x.length());
				else if (x.startsWith("PER/")) sPer = x.substring(4, x.length());
				else { other+= " "+x; }
			} //end of for n
			
			if (other.startsWith(" ")) other=other.replaceFirst(" ", "");
			if (sRmk.compareTo("")==0) { sRmk=other; } else { sRmk+=" "+other; }
			
// 	        String sub[] = type18.split(" ");
// 	        for (int i=0; i<sub.length; i++) {
// 	        	if (sub[i].contains("/")) { x=sub[i]; } 
// 	        	else { x+=" "+sub[i]; }
// 	        	//-------------------------------------
// 	        	if (x.startsWith("DOF/")) sDof = x.substring(4, x.length());
// 	        	else if (x.startsWith("^DOF/")) sDof = x.substring(5, x.length());
// 	        	//--
//				if (x.startsWith("REG/")) sReg = x.substring(4, x.length());
//				else if (x.startsWith("^REG/")) sReg = x.substring(5, x.length());
//				//--
//				if (x.startsWith("PBN/")) sPbn = x.substring(4, x.length());
//				else if (x.startsWith("^PBN/")) sPbn = x.substring(5, x.length());
//				//--
//				if (x.startsWith("SUR/")) sSur = x.substring(4, x.length());
//				else if (x.startsWith("^SUR/")) sSur = x.substring(5, x.length());
//				//--
// 	        	if (x.startsWith("CODE/")) sCode = x.substring(5, x.length());
// 	        	else if (x.startsWith("^CODE/")) sCode = x.substring(6, x.length());
// 	        	//--
//				if (x.startsWith("DLE/")) sDle = x.substring(4, x.length());
//				else if (x.startsWith("^DLE/")) sDle = x.substring(5, x.length());
//				//--
//				if (x.startsWith("ORGN/")) sOrgn = x.substring(5, x.length());
//				else if (x.startsWith("^ORGN/")) sOrgn = x.substring(6, x.length());
//				//--
//				if (x.startsWith("TALT/")) sTalt = x.substring(5, x.length());
//				else if (x.startsWith("^TALT/")) sTalt = x.substring(6, x.length());
// 	        } //end of for sub[i]
		} else { //else of type18==null
 	        sPbn="";sSur="";sDof="";sReg="";sCode="";sDle="";sOrgn="";sTalt="";
		}
		//------------------------------------------------------------------
		//--mengembalikan enter
		if (sPbn.contains("^")) sPbn = sPbn.replaceFirst("^", "\n");
		if (sSur.contains("^")) sSur = sSur.replaceFirst("^", "\n");
		if (sDof.contains("^")) sDof = sDof.replaceFirst("^", "\n");
		if (sReg.contains("^")) sReg = sReg.replaceFirst("^", "\n");
		if (sCode.contains("^")) sCode = sCode.replaceFirst("^", "\n");
		if (sDle.contains("^")) sDle = sDle.replaceFirst("^", "\n");
		//if (sOrgn.contains("^")) sOrgn = sOrgn.replaceFirst("^", "\n");
		//if (sTalt.contains("^")) sTalt = sTalt.replaceFirst("^", "\n");
		if (sNav.contains("^")) sNav = sNav.replace("^", "\n");
		if (sCom.contains("^")) sCom = sCom.replace("^", "\n");
		if (sDat.contains("^")) sDat = sDat.replace("^", "\n");
		if (sSur.contains("^")) sSur = sSur.replace("^", "\n");
		if (sDep.contains("^")) sDep = sDep.replace("^", "\n");
		if (sDest.contains("^")) sDest = sDest.replace("^", "\n");
		if (sTyp.contains("^")) sTyp = sTyp.replace("^", "\n");
		if (sOpr.contains("^"))  sOpr = sOpr.replace("^", "\n");
		if (sOrgn.contains("^")) sOrgn = sOrgn.replace("^", "\n");
		if (sAltn.contains("^")) sAltn = sAltn.replace("^", "\n");
		if (sRalt.contains("^")) sRalt = sRalt.replace("^", "\n");
		if (sTalt.contains("^")) sTalt = sTalt.replace("^", "\n");
		if (sRif.contains("^")) sRif = sRif.replace("^", "\n");
		if (sRmk.contains("^")) sRmk = sRmk.replace("^", "\n");
		//--hapus enter di awal berita 
		if (sPbn.startsWith("\n")) sPbn = sPbn.replaceFirst("\n", "");
		if (sSur.startsWith("\n")) sSur = sSur.replaceFirst("\n", "");
		if (sDof.startsWith("\n")) sDof = sDof.replaceFirst("\n", "");
		if (sReg.startsWith("\n")) sReg = sReg.replaceFirst("\n", "");
		if (sCode.startsWith("\n")) sCode = sCode.replaceFirst("\n", "");
		if (sDle.startsWith("\n")) sDle = sDle.replaceFirst("\n", "");
		if (sOrgn.startsWith("\n")) sOrgn = sOrgn.replaceFirst("\n", "");
		if (sTalt.startsWith("\n")) sTalt = sTalt.replaceFirst("\n", "");
		//--hapus spasi di awal berita 
		if (sPbn.startsWith(" ")) sPbn = sPbn.replaceFirst("\\s+", "");
		if (sSur.startsWith(" ")) sSur = sSur.replaceFirst("\\s+", "");
		if (sDof.startsWith(" ")) sDof = sDof.replaceFirst("\\s+", "");
		if (sReg.startsWith(" ")) sReg = sReg.replaceFirst("\\s+", "");
		if (sCode.startsWith(" ")) sCode = sCode.replaceFirst("\\s+", "");
		if (sDle.startsWith(" ")) sDle = sDle.replaceFirst("\\s+", "");
		if (sOrgn.startsWith(" ")) sOrgn = sOrgn.replaceFirst("\\s+", "");
		if (sTalt.startsWith(" ")) sTalt = sTalt.replaceFirst("\\s+", "");
				
		//kalo ada REG/ atau DOF/ di tipe18, tidak ditampilkan di Text 18 
		//karena ditampilkan di Text REG dan di Text DOF
		if (type18.contains(" REG/"+s_Reg)) { type18 = type18.replace(" REG/"+s_Reg, ""); } 
		else if (type18.contains("REG/"+s_Reg)) { type18 = type18.replace("REG/"+s_Reg, ""); }
		if (type18.contains(" DOF/"+s_Dof)) { type18 = type18.replace(" DOF/"+s_Dof, ""); } 
		else if (type18.contains("DOF/"+s_Dof)) { type18 = type18.replace("DOF/"+s_Dof, ""); }
		//untuk PBN/ SUR/ CODE/ DLE/ ORGN/ TALT tidak perlu di tampilkan di Text 18 
		if (type18.contains(" PBN/"+sPbn)) { type18 = type18.replace(" PBN/"+sPbn, ""); } 
		else if (type18.contains("PBN/"+sPbn)) { type18 = type18.replace("PBN/"+sPbn, ""); }
		if (type18.contains(" SUR/"+sSur)) { type18 = type18.replace(" SUR/"+sSur, ""); } 
		else if (type18.contains("SUR/"+sSur)) { type18 = type18.replace("SUR/"+sSur, ""); }
		if (type18.contains(" CODE/"+sCode)) { type18 = type18.replace(" CODE/"+sCode, ""); } 
		else if (type18.contains("CODE/"+sCode)) { type18 = type18.replace("CODE/"+sCode, ""); }
		if (type18.contains(" DLE/"+sDle)) { type18 = type18.replace(" DLE/"+sDle, ""); } 
		else if (type18.contains("DLE/"+sDle)) { type18 = type18.replace("DLE/"+sDle, ""); }
		if (type18.contains(" ORGN/"+sOrgn)) { type18 = type18.replace(" ORGN/"+sOrgn, ""); } 
		else if (type18.contains("ORGN/"+sOrgn)) { type18 = type18.replace("ORGN/"+sOrgn, ""); }
		if (type18.contains(" TALT/"+sTalt)) { type18 = type18.replace(" TALT/"+sTalt, ""); } 
		else if (type18.contains("TALT/"+sTalt)) { type18 = type18.replace("TALT/"+sTalt, ""); }
		
		if (type18.startsWith(" ")) type18 = type18.replaceFirst("\\s+", "");
		if (type18.contains("~")) type18 = type18.replaceAll("~", "/");
		if (type18.contains("^")) type18 = type18.replace("^", "\n");
		
		//percobaan
		String io = jdbc.getIO();
		if (io.compareTo("0")==0) {
			if (type18.contains("^")) { type18 = type18.replaceAll("^", "\n"); }
		}
//		t18c.setText(type18);
	}
	
	static void editType18(Text tReg, Text tDof) {
		//aChar="",sDof="",sReg="",sPbn="",sSur="",sCode="",sDle="",sOrgn="",sTalt="",sSts="",sSel="",sPer="",sEet="",;
		String s_Reg="",s_Dof="",type18="",other="",sPbn="",sNav="", sCom="",sDat="",sSur="", sDep="",sDest="",sDof="", sReg="",
		sTyp="",sCode="",sDle="",sOpr="",sOrgn="",sAltn="",sRalt="",sTalt="", sRif="",sRmk="",x="";
		
		//REG/
		if (jdbc.get18a().compareTo("REG/0")==0) { s_Reg=jdbc.get18Reg(); } //versi lama: REG/0, maka ambil versi baru  
		else { s_Reg=jdbc.get18a(); } //ambil versi lama
		if (s_Reg.equals("0")) s_Reg="";
//		t18a.setText(s_Reg);
		tReg.setText(s_Reg);
		//DOF/
		if (jdbc.get18b().compareTo("DOF/0")==0) { s_Dof=jdbc.get18Dof(); } 
		else { s_Dof=jdbc.get18b(); }
		if (s_Dof.equals("0")) s_Dof="";
//		t18b.setText(s_Dof);
		tDof.setText(s_Dof);
		
		time.tgl();
		tDof.setText(time.datenow);
		
		//ALL18/
		type18 = jdbc.get18();
		if (type18.startsWith(" ")) type18=type18.replaceFirst("\\s+", "");
		if (type18.compareTo("0")==0) type18="";
		
		if (!type18.equals("")) {
			if (type18.startsWith(" ")) type18 = type18.replaceFirst("\\s+", ""); //hapus spasi di awal Item 18
 	        if (type18.contains("\n")) {
 	        	//aChar = new Character((char)94).toString(); //kode ASCII 94=^,126='~'
 	        	type18 = type18.replaceAll("\n", "^");//aChar);
 	        } 
 	        type18 = type18.replaceAll("/", "~");
 			//dari db untuk fungsi edit
 			if (type18.contains("STS~")) type18 = type18.replace("STS~", "STS/");
 			if (type18.contains("PBN~")) type18 = type18.replace("PBN~", "PBN/");
 			if (type18.contains("NAV~")) type18 = type18.replace("NAV~", "NAV/");
 			if (type18.contains("COM~")) type18 = type18.replace("COM~", "COM/");
 			if (type18.contains("DAT~")) type18 = type18.replace("DAT~", "DAT/");
 			if (type18.contains("SUR~")) type18 = type18.replace("SUR~", "SUR/");
 			if (type18.contains("DEP~")) type18 = type18.replace("DEP~", "DEP/");
 			if (type18.contains("DEST~")) type18 = type18.replace("DEST~", "DEST/");
 			if (type18.contains("REG~")) type18 = type18.replace("REG~", "REG/");
 			if (type18.contains("DOF~")) type18 = type18.replace("DOF~", "DOF/");
 			if (type18.contains("EET~")) type18 = type18.replace("EET~", "EET/");
 			if (type18.contains("SEL~")) type18 = type18.replace("SEL~", "SEL/");
 			if (type18.contains("TYP~")) type18 = type18.replace("TYP~", "TYP/");
 			if (type18.contains("CODE~")) type18 = type18.replace("CODE~", "CODE/");
 			if (type18.contains("DLE~")) type18 = type18.replace("DLE~", "DLE/");
 			if (type18.contains("OPR~")) type18 = type18.replace("OPR~", "OPR/");
 			if (type18.contains("ORGN~")) type18 = type18.replace("ORGN~", "ORGN/");
 			if (type18.contains("PER~")) type18 = type18.replace("PER~", "PER/");
 			if (type18.contains("ALTN~")) type18 = type18.replace("ALTN~", "ALTN/");
 			if (type18.contains("RALT~")) type18 = type18.replace("RALT~", "RALT/");
 			if (type18.contains("TALT~")) type18 = type18.replace("TALT~", "TALT/");
 			if (type18.contains("RIF~")) type18 = type18.replace("RIF~", "RIF/");
 			if (type18.contains("RMK~")) type18 = type18.replace("RMK~", "RMK/");
 			//-------------------------------------------------------------------
 	        sPbn="";sSur="";sDof="";sReg="";sCode="";sDle="";sOrgn="";sTalt="";
 	        String sub[] = type18.split(" ");
			for (int n=0; n<sub.length; n++) {
				if (sub[n].contains("/")) { x=sub[n]; } 
				else { x+=" "+sub[n]; }
				
				if (x.startsWith("STS/")) sSts = x.substring(4, x.length());
				else if (x.startsWith("PBN/")) sPbn = x.substring(4, x.length());
				else if (x.startsWith("NAV/")) sNav = x.substring(4, x.length());
				else if (x.startsWith("COM/")) sCom = x.substring(4, x.length());
				else if (x.startsWith("DAT/")) sDat = x.substring(4, x.length());
				else if (x.startsWith("SUR/")) sSur = x.substring(4, x.length());
				else if (x.startsWith("DEP/")) sDep = x.substring(4, x.length());
				else if (x.startsWith("DEST/")) sDest = x.substring(5, x.length());
				else if (x.startsWith("DOF/")) sDof = x.substring(4, x.length());
				else if (x.startsWith("REG/")) sReg = x.substring(4, x.length());
				else if (x.startsWith("EET/")) sEet = x.substring(4, x.length());
				else if (x.startsWith("SEL/")) sSel = x.substring(4, x.length());
				else if (x.startsWith("TYP/")) sTyp = x.substring(4, x.length());
				else if (x.startsWith("CODE/")) sCode = x.substring(5, x.length());
				else if (x.startsWith("DLE/")) sDle = x.substring(4, x.length());
				else if (x.startsWith("OPR/")) sOpr = x.substring(4, x.length());
				else if (x.startsWith("ORGN/")) sOrgn = x.substring(5, x.length());
				else if (x.startsWith("ALTN/")) sAltn = x.substring(5, x.length());
				else if (x.startsWith("RALT/")) sRalt = x.substring(5, x.length());
				else if (x.startsWith("TALT/")) sTalt = x.substring(5, x.length());
				else if (x.startsWith("RIF/")) sRif = x.substring(4, x.length());
				else if (x.startsWith("RMK/")) sRmk = x.substring(4, x.length());
				else if (x.startsWith("PER/")) sPer = x.substring(4, x.length());
				else { other+= " "+x; }
			} //end of for n
			
			if (other.startsWith(" ")) other=other.replaceFirst(" ", "");
			if (sRmk.compareTo("")==0) { sRmk=other; } else { sRmk+=" "+other; }
			
// 	        String sub[] = type18.split(" ");
// 	        for (int i=0; i<sub.length; i++) {
// 	        	if (sub[i].contains("/")) { x=sub[i]; } 
// 	        	else { x+=" "+sub[i]; }
// 	        	//-------------------------------------
// 	        	if (x.startsWith("DOF/")) sDof = x.substring(4, x.length());
// 	        	else if (x.startsWith("^DOF/")) sDof = x.substring(5, x.length());
// 	        	//--
//				if (x.startsWith("REG/")) sReg = x.substring(4, x.length());
//				else if (x.startsWith("^REG/")) sReg = x.substring(5, x.length());
//				//--
//				if (x.startsWith("PBN/")) sPbn = x.substring(4, x.length());
//				else if (x.startsWith("^PBN/")) sPbn = x.substring(5, x.length());
//				//--
//				if (x.startsWith("SUR/")) sSur = x.substring(4, x.length());
//				else if (x.startsWith("^SUR/")) sSur = x.substring(5, x.length());
//				//--
// 	        	if (x.startsWith("CODE/")) sCode = x.substring(5, x.length());
// 	        	else if (x.startsWith("^CODE/")) sCode = x.substring(6, x.length());
// 	        	//--
//				if (x.startsWith("DLE/")) sDle = x.substring(4, x.length());
//				else if (x.startsWith("^DLE/")) sDle = x.substring(5, x.length());
//				//--
//				if (x.startsWith("ORGN/")) sOrgn = x.substring(5, x.length());
//				else if (x.startsWith("^ORGN/")) sOrgn = x.substring(6, x.length());
//				//--
//				if (x.startsWith("TALT/")) sTalt = x.substring(5, x.length());
//				else if (x.startsWith("^TALT/")) sTalt = x.substring(6, x.length());
// 	        } //end of for sub[i]
		} else { //else of type18==null
 	        sPbn="";sSur="";sDof="";sReg="";sCode="";sDle="";sOrgn="";sTalt="";
		}
		//------------------------------------------------------------------
		//--mengembalikan enter
		if (sPbn.contains("^")) sPbn = sPbn.replaceFirst("^", "\n");
		if (sSur.contains("^")) sSur = sSur.replaceFirst("^", "\n");
		if (sDof.contains("^")) sDof = sDof.replaceFirst("^", "\n");
		if (sReg.contains("^")) sReg = sReg.replaceFirst("^", "\n");
		if (sCode.contains("^")) sCode = sCode.replaceFirst("^", "\n");
		if (sDle.contains("^")) sDle = sDle.replaceFirst("^", "\n");
		//if (sOrgn.contains("^")) sOrgn = sOrgn.replaceFirst("^", "\n");
		//if (sTalt.contains("^")) sTalt = sTalt.replaceFirst("^", "\n");
		if (sNav.contains("^")) sNav = sNav.replace("^", "\n");
		if (sCom.contains("^")) sCom = sCom.replace("^", "\n");
		if (sDat.contains("^")) sDat = sDat.replace("^", "\n");
		if (sSur.contains("^")) sSur = sSur.replace("^", "\n");
		if (sDep.contains("^")) sDep = sDep.replace("^", "\n");
		if (sDest.contains("^")) sDest = sDest.replace("^", "\n");
		if (sTyp.contains("^")) sTyp = sTyp.replace("^", "\n");
		if (sOpr.contains("^"))  sOpr = sOpr.replace("^", "\n");
		if (sOrgn.contains("^")) sOrgn = sOrgn.replace("^", "\n");
		if (sAltn.contains("^")) sAltn = sAltn.replace("^", "\n");
		if (sRalt.contains("^")) sRalt = sRalt.replace("^", "\n");
		if (sTalt.contains("^")) sTalt = sTalt.replace("^", "\n");
		if (sRif.contains("^")) sRif = sRif.replace("^", "\n");
		if (sRmk.contains("^")) sRmk = sRmk.replace("^", "\n");
		//--hapus enter di awal berita 
		if (sPbn.startsWith("\n")) sPbn = sPbn.replaceFirst("\n", "");
		if (sSur.startsWith("\n")) sSur = sSur.replaceFirst("\n", "");
		if (sDof.startsWith("\n")) sDof = sDof.replaceFirst("\n", "");
		if (sReg.startsWith("\n")) sReg = sReg.replaceFirst("\n", "");
		if (sCode.startsWith("\n")) sCode = sCode.replaceFirst("\n", "");
		if (sDle.startsWith("\n")) sDle = sDle.replaceFirst("\n", "");
		if (sOrgn.startsWith("\n")) sOrgn = sOrgn.replaceFirst("\n", "");
		if (sTalt.startsWith("\n")) sTalt = sTalt.replaceFirst("\n", "");
		//--hapus spasi di awal berita 
		if (sPbn.startsWith(" ")) sPbn = sPbn.replaceFirst("\\s+", "");
		if (sSur.startsWith(" ")) sSur = sSur.replaceFirst("\\s+", "");
		if (sDof.startsWith(" ")) sDof = sDof.replaceFirst("\\s+", "");
		if (sReg.startsWith(" ")) sReg = sReg.replaceFirst("\\s+", "");
		if (sCode.startsWith(" ")) sCode = sCode.replaceFirst("\\s+", "");
		if (sDle.startsWith(" ")) sDle = sDle.replaceFirst("\\s+", "");
		if (sOrgn.startsWith(" ")) sOrgn = sOrgn.replaceFirst("\\s+", "");
		if (sTalt.startsWith(" ")) sTalt = sTalt.replaceFirst("\\s+", "");
				
		//kalo ada REG/ atau DOF/ di tipe18, tidak ditampilkan di Text 18 
		//karena ditampilkan di Text REG dan di Text DOF
		if (type18.contains(" REG/"+s_Reg)) { type18 = type18.replace(" REG/"+s_Reg, ""); } 
		else if (type18.contains("REG/"+s_Reg)) { type18 = type18.replace("REG/"+s_Reg, ""); }
		if (type18.contains(" DOF/"+s_Dof)) { type18 = type18.replace(" DOF/"+s_Dof, ""); } 
		else if (type18.contains("DOF/"+s_Dof)) { type18 = type18.replace("DOF/"+s_Dof, ""); }
		//untuk PBN/ SUR/ CODE/ DLE/ ORGN/ TALT tidak perlu di tampilkan di Text 18 
		if (type18.contains(" PBN/"+sPbn)) { type18 = type18.replace(" PBN/"+sPbn, ""); } 
		else if (type18.contains("PBN/"+sPbn)) { type18 = type18.replace("PBN/"+sPbn, ""); }
		if (type18.contains(" SUR/"+sSur)) { type18 = type18.replace(" SUR/"+sSur, ""); } 
		else if (type18.contains("SUR/"+sSur)) { type18 = type18.replace("SUR/"+sSur, ""); }
		if (type18.contains(" CODE/"+sCode)) { type18 = type18.replace(" CODE/"+sCode, ""); } 
		else if (type18.contains("CODE/"+sCode)) { type18 = type18.replace("CODE/"+sCode, ""); }
		if (type18.contains(" DLE/"+sDle)) { type18 = type18.replace(" DLE/"+sDle, ""); } 
		else if (type18.contains("DLE/"+sDle)) { type18 = type18.replace("DLE/"+sDle, ""); }
		if (type18.contains(" ORGN/"+sOrgn)) { type18 = type18.replace(" ORGN/"+sOrgn, ""); } 
		else if (type18.contains("ORGN/"+sOrgn)) { type18 = type18.replace("ORGN/"+sOrgn, ""); }
		if (type18.contains(" TALT/"+sTalt)) { type18 = type18.replace(" TALT/"+sTalt, ""); } 
		else if (type18.contains("TALT/"+sTalt)) { type18 = type18.replace("TALT/"+sTalt, ""); }
		
		if (type18.startsWith(" ")) type18 = type18.replaceFirst("\\s+", "");
		if (type18.contains("~")) type18 = type18.replaceAll("~", "/");
		if (type18.contains("^")) type18 = type18.replace("^", "\n");
		
		//percobaan
		String io = jdbc.getIO();
		if (io.compareTo("0")==0) {
			if (type18.contains("^")) { type18 = type18.replaceAll("^", "\n"); }
		}
//		t18c.setText(type18);
	}
	
	static void editType19(Text t19a, Text t19b, Text t19cUhf, Text t19cVhf, Text t19cElt, 
			Text t19dP, Text t19dD, Text t19dM, Text t19dJ, Text t19eL, Text t19eF, Text t19eU, Text t19eV, 
			Text t19Num, Text t19Cap, Text t19Cov, Text t19Col, Text t19Air, Text t19Rem, Text t19Pil ) {
		String UHF = jdbc.get19cUHF();
		String VHF = jdbc.get19cVHF();
		String ELT = jdbc.get19cELT();
		String Polar = jdbc.get19dP();
		String Desert = jdbc.get19dD();
		String Maritime = jdbc.get19dM();
		String Jungle = jdbc.get19dJ();
		String Light = jdbc.get19eL();
		String Floures = jdbc.get19eF();
		String jUHF = jdbc.get19eU();
		String jVHF = jdbc.get19eV();
		String cover = jdbc.get19fCov();
		
		t19a.setText(jdbc.get19a());
		t19b.setText(jdbc.get19b());
		
		if (UHF == null) { UHF=""; t19cUhf.setText("X"); } 
		else if ((UHF != null)&&(UHF.isEmpty())) { UHF=""; t19cUhf.setText("X"); } 
		else if (UHF.equals("R/U")) t19cUhf.setText("U");
		
		if (VHF == null) { VHF=""; t19cVhf.setText("X"); } 
		else if ((VHF != null)&&(VHF.isEmpty())) { VHF=""; t19cVhf.setText("X"); } 
		else if (VHF.equals("R/V")) t19cVhf.setText("V");
		
		if (ELT == null) { ELT=""; t19cElt.setText("X"); } 
		else if ((ELT != null)&&(ELT.isEmpty())) { ELT=""; t19cElt.setText("X"); } 
		else if (ELT.equals("R/E")) t19cElt.setText("E");

		if (Polar == null) { Polar=""; t19dP.setText("X"); } 
		else if ((Polar != null)&&(Polar.isEmpty())) { Polar=""; t19dP.setText("X"); } 
		else if (Polar.equals("S/P")) t19dP.setText("P");
		
		if (Desert == null) { Desert=""; t19dD.setText("X"); } 
		else if ((Desert != null)&&(Desert.isEmpty())) { Desert=""; t19dD.setText("X"); } 
		else if (Desert.equals("S/D")) t19dD.setText("D");
		
		if (Maritime == null) { Maritime=""; t19dM.setText("X"); } 
		else if ((Maritime != null)&&(Maritime.isEmpty())) { Maritime=""; t19dM.setText("X"); } 
		else if (Maritime.equals("S/M")) t19dM.setText("M");
		
		if (Jungle == null) { Jungle=""; t19dJ.setText("X"); } 
		else if ((Jungle != null)&&(Jungle.isEmpty())) { Jungle=""; t19dJ.setText("X"); } 
		else if (Jungle.equals("S/J")) t19dJ.setText("J");

		if (Light == null) { Light=""; t19eL.setText("X"); } 
		else if ((Light != null)&&(Light.isEmpty())) { Light=""; t19eL.setText("X"); } 
		else if (Light.equals("J/L")) t19eL.setText("L");
		
		if (Floures == null) { Floures=""; t19eF.setText("X"); } 
		else if ((Floures != null)&&(Floures.isEmpty())) { Floures=""; t19eF.setText("X"); } 
		else if (Floures.equals("J/F")) t19eF.setText("F");
		
		if (jUHF == null) { jUHF=""; t19eU.setText("X"); } 
		else if ((jUHF != null)&&(jUHF.isEmpty())) { jUHF=""; t19eU.setText("X"); } 
		else if (jUHF.equals("J/U")) t19eU.setText("U");
		
		if (jVHF == null) { jVHF=""; t19eV.setText("X"); } 
		else if ((jVHF != null)&&(jVHF.isEmpty())) { jVHF=""; t19eV.setText("X"); } 
		else if (jVHF.equals("J/V")) t19eV.setText("V");
		
		if (cover == null) { cover=""; t19Cov.setText("X"); } 
		else if ((cover != null)&&(cover.isEmpty())) { cover=""; t19Cov.setText("X"); } 
		else if (cover.equals("C")) t19Cov.setText("C");

		t19Num.setText(jdbc.get19fNum());
		t19Cap.setText(jdbc.get19fCap());
		t19Col.setText(jdbc.get19fCol());
		t19Air.setText(jdbc.get19g());
		t19Rem.setText(jdbc.get19Rem());
		t19Pil.setText(jdbc.get19i());
	}
	
	static void editTypeBaru(Text tNav, Text tCom, Text tDat, Text tSur, Text tDep, Text tDest, Text tEet, Text tSel, Text tTyp, Text tCode, 
			Text tDle, Text tOpr, Text tOrgn, Text tPer, Text tAltn, Text tRalt, Text tTalt, Text tRif, Text tRmk, Text tPbn, Text tSts) {
		tNav.setText(jdbc.get18Nav());
        tCom.setText(jdbc.get18Com());
        tDat.setText(jdbc.get18Dat());
        tSur.setText(jdbc.get18Sur());
        tDep.setText(jdbc.get18Dep());
        tDest.setText(jdbc.get18Dest());
        tTyp.setText(jdbc.get18Typ());
        tOpr.setText(jdbc.get18Opr());
        tOrgn.setText(jdbc.get18Orgn());
        tAltn.setText(jdbc.get18Altn());
        tRalt.setText(jdbc.get18Ralt());
        tTalt.setText(jdbc.get18Talt());
        tRif.setText(jdbc.get18Rif());
        tRmk.setText(jdbc.get18Rmk());
        tEet.setText(jdbc.get18Eet());
        tSel.setText(jdbc.get18Sel());
        tCode.setText(jdbc.get18Code());
        tDle.setText(jdbc.get18Dle());
        tPer.setText(jdbc.get18Per());
        tPbn.setText(jdbc.get18Pbn());
        tSts.setText(jdbc.get18Sts());
//        tDof.setText(jdbc.get18Dof());
//        tReg.setText(jdbc.get18Reg());
	}
	
	static void editDof(Text tDof) {
		time.tgl();
		tDof.setText(time.datenow);
//        tDof.setText(jdbc.get18Dof());
	}
	
	/**
	 * ----------------------------------------------------------------------------------------------------------------------------	
	 * initEdit___ [ ___ -> msgType: ALR,FPL, dst.]
	 * tampilan body AFTN sesuai msgType-nya beserta isi dari database berdasarkan id yang di pilih dari tabel masing-masing
	 * ----------------------------------------------------------------------------------------------------------------------------
	 */
	
	static void editHeader(Text tSendAt,Text tPriority,Text tFilingTime,Text tOriginator,Text tOriRef,Text tFiledby,Button bBell,
			Text tDest1,Text tDest2,Text tDest3,Text tDest4,Text tDest5,Text tDest6,Text tDest7,
			Text tDest8,Text tDest9,Text tDest10,Text tDest11,Text tDest12,Text tDest13,Text tDest14,
			Text tDest15,Text tDest16,Text tDest17,Text tDest18,Text tDest19,Text tDest20,Text tDest21) {

		time.tgl();
		tSendAt.setText(time.filing_time);
		tPriority.setText(jdbc.getPriority());
		tFilingTime.setText(time.filing_time);
		tOriginator.setText(ReadFromFile.ReadInputFile1("/tp/originator.txt"));//jdbc.getOriginator());
		tOriRef.setText(jdbc.getOriRef());
		tFiledby.setText(jdbc.getFiled_by());
		String sbell = jdbc.getBell();
		if (sbell.compareToIgnoreCase("1")==0) bBell.setSelection(true); else bBell.setSelection(false);  
		
		tDest1.setText(jdbc.getDes1());
		tDest2.setText(jdbc.getDes2());
		tDest3.setText(jdbc.getDes3());
		tDest4.setText(jdbc.getDes4());
		tDest5.setText(jdbc.getDes5());
		tDest6.setText(jdbc.getDes6());
		tDest7.setText(jdbc.getDes7());
		tDest8.setText(jdbc.getDes8());
		tDest9.setText(jdbc.getDes9());
		tDest10.setText(jdbc.getDes10());
		tDest11.setText(jdbc.getDes11());
		tDest12.setText(jdbc.getDes12());
		tDest13.setText(jdbc.getDes13());
		tDest14.setText(jdbc.getDes14());
		tDest15.setText(jdbc.getDes15());
		tDest16.setText(jdbc.getDes16());
		tDest17.setText(jdbc.getDes17());
		tDest18.setText(jdbc.getDes18());
		tDest19.setText(jdbc.getDes19());
		tDest20.setText(jdbc.getDes20());
		tDest21.setText(jdbc.getDes21());
	}

	static void editHeaderFromFPL(Text tPriority,Text tFilingTime,Text tOriginator,Text tOriRef,Button bBell,
			Text tDest1,Text tDest2,Text tDest3,Text tDest4,Text tDest5,Text tDest6,Text tDest7,
			Text tDest8,Text tDest9,Text tDest10,Text tDest11,Text tDest12,Text tDest13,Text tDest14,
			Text tDest15,Text tDest16,Text tDest17,Text tDest18,Text tDest19,Text tDest20,Text tDest21) {

		tPriority.setText(HeaderComposite.tPriorityFPL.getText());
		tFilingTime.setText(HeaderComposite.tFilingTimeFPL.getText());
		tOriginator.setText(HeaderComposite.tOriginatorFPL.getText());
		tOriRef.setText(HeaderComposite.tOriRefFPL.getText());
		
		String sbell = jdbc.getBell();
		if (sbell.compareToIgnoreCase("1")==0) bBell.setSelection(true); else bBell.setSelection(false);
		
		tDest1.setText(HeaderComposite.tDest1FPL.getText());
		tDest2.setText(HeaderComposite.tDest2FPL.getText());
		tDest3.setText(HeaderComposite.tDest3FPL.getText());
		tDest4.setText(HeaderComposite.tDest4FPL.getText());
		tDest5.setText(HeaderComposite.tDest5FPL.getText());
		tDest6.setText(HeaderComposite.tDest6FPL.getText());
		tDest7.setText(HeaderComposite.tDest7FPL.getText());
		tDest8.setText(HeaderComposite.tDest8FPL.getText());
		tDest9.setText(HeaderComposite.tDest9FPL.getText());
		tDest10.setText(HeaderComposite.tDest10FPL.getText());
		tDest11.setText(HeaderComposite.tDest11FPL.getText());
		tDest12.setText(HeaderComposite.tDest12FPL.getText());
		tDest13.setText(HeaderComposite.tDest13FPL.getText());
		tDest14.setText(HeaderComposite.tDest14FPL.getText());
		tDest15.setText(HeaderComposite.tDest15FPL.getText());
		tDest16.setText(HeaderComposite.tDest16FPL.getText());
		tDest17.setText(HeaderComposite.tDest17FPL.getText());
		tDest18.setText(HeaderComposite.tDest18FPL.getText());
		tDest19.setText(HeaderComposite.tDest19FPL.getText());
		tDest20.setText(HeaderComposite.tDest20FPL.getText());
		tDest21.setText(HeaderComposite.tDest21FPL.getText());
	}
	
	public static void setCurrentAMO() {
		tFbAMO.setText(jdbc.getFiled_by());
		// BODY
		String sFreetext=jdbc.getManual_ats();
		if (sFreetext.startsWith("amo")) sFreetext=sFreetext.replaceFirst("amo", "");
		tFreeAMO.setText(sFreetext);//tFreeATS.setText(jdbc.getManual_ats());
	}
	
	public static void setCurrentFREE() {
		tFbTEXT.setText(jdbc.getFiled_by());
		// BODY
		String sFreetext=jdbc.getManual_ats();
		if (sFreetext.startsWith("amo")) sFreetext=sFreetext.replaceFirst("amo", "");
		tFreeTEXT.setText(sFreetext);//tFreeATS.setText(jdbc.getManual_ats());
	}
	
	public static void setCurrentAFTN() {
		// HEADER
		editHeader(HeaderComposite.tSendAtAFTN,HeaderComposite.tPriorityAFTN,HeaderComposite.tFilingTimeAFTN,HeaderComposite.tOriginatorAFTN,
				HeaderComposite.tOriRefAFTN,tFbAFTN,HeaderComposite.bBellAFTN,HeaderComposite.tDest1AFTN,HeaderComposite.tDest2AFTN,
				HeaderComposite.tDest3AFTN,HeaderComposite.tDest4AFTN,HeaderComposite.tDest5AFTN,HeaderComposite.tDest6AFTN,
				HeaderComposite.tDest7AFTN,HeaderComposite.tDest8AFTN,HeaderComposite.tDest9AFTN,HeaderComposite.tDest10AFTN,
				HeaderComposite.tDest11AFTN,HeaderComposite.tDest12AFTN,HeaderComposite.tDest13AFTN,HeaderComposite.tDest14AFTN,
				HeaderComposite.tDest15AFTN,HeaderComposite.tDest16AFTN,HeaderComposite.tDest17AFTN,HeaderComposite.tDest18AFTN,
				HeaderComposite.tDest19AFTN,HeaderComposite.tDest20AFTN,HeaderComposite.tDest21AFTN);
		// BODY
		String sFreetext=jdbc.getManual_ats();
		if (sFreetext.startsWith("amo")) sFreetext=sFreetext.replaceFirst("amo", "");
		tFreeATS.setText(sFreetext);//tFreeATS.setText(jdbc.getManual_ats());
	}

	public static void setCurrentALR() {
		// HEADER
		editHeader(HeaderComposite.tSendAtALR,HeaderComposite.tPriorityALR,HeaderComposite.tFilingTimeALR,HeaderComposite.tOriginatorALR,
				HeaderComposite.tOriRefALR,tFbALR,HeaderComposite.bBellALR,HeaderComposite.tDest1ALR,HeaderComposite.tDest2ALR,
				HeaderComposite.tDest3ALR,HeaderComposite.tDest4ALR,HeaderComposite.tDest5ALR,HeaderComposite.tDest6ALR,
				HeaderComposite.tDest7ALR,HeaderComposite.tDest8ALR,HeaderComposite.tDest9ALR,HeaderComposite.tDest10ALR,
				HeaderComposite.tDest11ALR,HeaderComposite.tDest12ALR,HeaderComposite.tDest13ALR,HeaderComposite.tDest14ALR,
				HeaderComposite.tDest15ALR,HeaderComposite.tDest16ALR,HeaderComposite.tDest17ALR,HeaderComposite.tDest18ALR,
				HeaderComposite.tDest19ALR,HeaderComposite.tDest20ALR,HeaderComposite.tDest21ALR);
		// BODY
		editType3(t3aALR, t3bALR, t3cALR);
		editType5(t5aALR, t5bALR, t5cALR);
		editType7(t7aALR, t7bALR, t7cALR);
		editType8(t8aALR, t8bALR);
		editType9(t9aALR, t9bALR, t9cALR);
		editType10(t10aALR, t10bALR);
		editType13(t13aALR, t13bALR);
		editType15(t15aALR, t15bALR, t15cALR);
		editType16(t16aALR, t16bALR, t16cALR, t16dALR);
		editType18(tRegALR, tDofALR);
		editType19(t19aALR, t19bALR, t19cUhfALR, t19cVhfALR, t19cEltALR, t19dPALR, t19dDALR, t19dMALR, t19dJALR, t19eLALR, t19eFALR, t19eUALR, t19eVALR, t19NumALR, t19CapALR, t19cALR, t19ColALR, t19AirALR, t19RemALR, t19PilALR);
		editTypeBaru(tNavALR, tComALR, tDatALR, tSurALR, tDepALR, tDestALR, tEetALR, tSelALR, tTypALR, tCodeALR, tDleALR, tOprALR, tOrgnALR, tPerALR, tAltnALR, tRaltALR, tTaltALR, tRifALR, tRmkALR, tPbnALR, tStsALR);
		t20ALR.setText(jdbc.get20());
	}

	public static void setCurrentRCF() {
		// HEADER
		editHeader(HeaderComposite.tSendAtRCF,HeaderComposite.tPriorityRCF,HeaderComposite.tFilingTimeRCF,HeaderComposite.tOriginatorRCF,
				HeaderComposite.tOriRefRCF,tFbRCF,HeaderComposite.bBellRCF,HeaderComposite.tDest1RCF,HeaderComposite.tDest2RCF,
				HeaderComposite.tDest3RCF,HeaderComposite.tDest4RCF,HeaderComposite.tDest5RCF,HeaderComposite.tDest6RCF,
				HeaderComposite.tDest7RCF,HeaderComposite.tDest8RCF,HeaderComposite.tDest9RCF,HeaderComposite.tDest10RCF,
				HeaderComposite.tDest11RCF,HeaderComposite.tDest12RCF,HeaderComposite.tDest13RCF,HeaderComposite.tDest14RCF,
				HeaderComposite.tDest15RCF,HeaderComposite.tDest16RCF,HeaderComposite.tDest17RCF,HeaderComposite.tDest18RCF,
				HeaderComposite.tDest19RCF,HeaderComposite.tDest20RCF,HeaderComposite.tDest21RCF);
		// BODY
		editType3(t3aRCF, t3bRCF, t3cRCF);
		editType7(t7aRCF, t7bRCF, t7cRCF);
		t21RCF.setText(jdbc.get21());
	}

	public static void setCurrentFPL() {
		// HEADER
		editHeader(HeaderComposite.tSendAtFPL,HeaderComposite.tPriorityFPL,HeaderComposite.tFilingTimeFPL,HeaderComposite.tOriginatorFPL,
				HeaderComposite.tOriRefFPL,tFbFPL,HeaderComposite.bBellFPL,HeaderComposite.tDest1FPL,HeaderComposite.tDest2FPL,
				HeaderComposite.tDest3FPL,HeaderComposite.tDest4FPL,HeaderComposite.tDest5FPL,HeaderComposite.tDest6FPL,
				HeaderComposite.tDest7FPL,HeaderComposite.tDest8FPL,HeaderComposite.tDest9FPL,HeaderComposite.tDest10FPL,
				HeaderComposite.tDest11FPL,HeaderComposite.tDest12FPL,HeaderComposite.tDest13FPL,HeaderComposite.tDest14FPL,
				HeaderComposite.tDest15FPL,HeaderComposite.tDest16FPL,HeaderComposite.tDest17FPL,HeaderComposite.tDest18FPL,
				HeaderComposite.tDest19FPL,HeaderComposite.tDest20FPL,HeaderComposite.tDest21FPL);
		// BODY
		editType3(t3aFPL, t3bFPL, t3cFPL);
		editType7(t7aFPL, t7bFPL, t7cFPL);
		editType8(t8aFPL, t8bFPL);
		editType9(t9aFPL, t9bFPL, t9cFPL);
		editType10(t10aFPL, t10bFPL);
		editType13(t13aFPL, t13bFPL);
		editType15(t15aFPL, t15bFPL, t15cFPL);
		editType16(t16aFPL, t16bFPL, t16cFPL, t16dFPL);
		editType18(tRegFPL, tDofFPL);
		editType19(t19aFPL, t19bFPL, t19cUhfFPL, t19cVhfFPL, t19cEltFPL, t19dPFPL, t19dDFPL, t19dMFPL, t19dJFPL, t19eLFPL, t19eFFPL, t19eUFPL, t19eVFPL, t19NumFPL, t19CapFPL, t19cFPL, t19ColFPL, t19AirFPL, t19RemFPL, t19PilFPL);
		editTypeBaru(tNavFPL, tComFPL, tDatFPL, tSurFPL, tDepFPL, tDestFPL, tEetFPL, tSelFPL, tTypFPL, tCodeFPL, tDleFPL, tOprFPL, tOrgnFPL, tPerFPL, tAltnFPL, tRaltFPL, tTaltFPL, tRifFPL, tRmkFPL, tPbnFPL, tStsFPL);
//		editType18(regText);
		tSpace.setText(jdbc.getSpace());
        
        //------------------------------------------------------------------------------------------------------------
        String s7b="",s10bLama="",s10bBaru="",s18aLama="",s18bLama="",s18aBaru="",s18bBaru="",s19cUHF="",s19cVHF="",s19cELT="",
        s19dP="",s19dD="",s19dM="",s19dJ="",s19eL="",s19eF="",s19eU="",s19eV="";

        if (t7bFPL.getSelection()==true) s7b=t7bFPL.getText(); else s7b="";
        //--
        s10bBaru=t10bFPL.getText(); 	
		s10bLama="";//t10bFPL.getText();
		//--
		s18aBaru=tRegFPL.getText(); 	
		s18aLama="";//t18aFPL.getText();
		//--
		s18bBaru=tDofFPL.getText(); 	
		s18bLama="";//t18bFPL.getText();
		//--
        if (t19cUhfFPL.getText().compareTo("U")==0) { s19cUHF = t19cUhfFPL.toString(); s19cUHF = "R/U"; } else s19cUHF="";
		if (t19cVhfFPL.getText().compareTo("V")==0) { s19cVHF = t19cVhfFPL.toString(); s19cVHF = "R/V"; } else s19cVHF="";
		if (t19cEltFPL.getText().compareTo("E")==0) { s19cELT = t19cEltFPL.toString(); s19cELT = "R/E"; } else s19cELT="";
		if (t19dPFPL.getText().compareTo("P")==0) { s19dP = t19dPFPL.toString(); s19dP = "S/P"; } else s19dP="";
		if (t19dDFPL.getText().compareTo("D")==0) { s19dD = t19dDFPL.toString(); s19dD = "S/D"; } else s19dD="";
		if (t19dMFPL.getText().compareTo("M")==0) { s19dM = t19dMFPL.toString(); s19dM = "S/M"; } else s19dM="";
		if (t19dJFPL.getText().compareTo("J")==0) { s19dJ = t19dJFPL.toString(); s19dJ = "S/J"; } else s19dJ="";
		if (t19eLFPL.getText().compareTo("L")==0) { s19eL = t19eLFPL.toString(); s19eL = "J/L"; } else s19eL=""; 
		if (t19eFFPL.getText().compareTo("F")==0) { s19eF = t19eFFPL.toString(); s19eF = "J/F"; } else s19eF="";
		if (t19eUFPL.getText().compareTo("U")==0) { s19eU = t19eUFPL.toString(); s19eU = "J/U"; } else s19eU="";
		if (t19eVFPL.getText().compareTo("V")==0) { s19eV = t19eVFPL.toString(); s19eV = "J/V"; } else s19eV="";
		
		setFPL(t3bFPL.getText(),t3cFPL.getText(),
        		t7aFPL.getText(),s7b,t7cFPL.getText(),
//        		c8aFPL.getSelection()[0],c8bFPL.getSelection()[0],
//        		t9aFPL.getText(),t9bFPL.getText(),c9cFPL.getSelection()[0],
        		t8aFPL.getText(),t8bFPL.getText(),
        		t9aFPL.getText(),t9bFPL.getText(),t9cFPL.getText(),
        		t10aFPL.getText(),s10bLama,s10bBaru,
        		t13aFPL.getText(),t13bFPL.getText(),
        		t15aFPL.getText(),t15bFPL.getText(),t15cFPL.getText(),
        		t16aFPL.getText(),t16bFPL.getText(),t16cFPL.getText(),t16dFPL.getText(),
        		
        		s18aLama,s18aBaru,s18bLama,s18bBaru,"",//t18cFPL.getText(),
        		tNavFPL.getText(),tComFPL.getText(),tDatFPL.getText(),tSurFPL.getText(),tDepFPL.getText(),tDestFPL.getText(),
        		tEetFPL.getText(),tSelFPL.getText(),tTypFPL.getText(),tCodeFPL.getText(),tDleFPL.getText(),tOprFPL.getText(),
        		tOrgnFPL.getText(),tPerFPL.getText(),tAltnFPL.getText(),tRaltFPL.getText(),tTaltFPL.getText(),tRifFPL.getText(),
        		tRmkFPL.getText(),tPbnFPL.getText(),tStsFPL.getText(),
        		
        		t19aFPL.getText(),t19bFPL.getText(),s19cUHF,s19cVHF,s19cELT,s19dP,s19dD,s19dM,s19dJ,s19eL,s19eF,s19eU,s19eV,
        		t19NumFPL.getText(),t19CapFPL.getText(),t19cFPL.getText(),t19ColFPL.getText(),t19AirFPL.getText(),
    			t19RemFPL.getText(),t19PilFPL.getText());
	}
	
	void tipe18baruFPL() {
//    	sReg = ATSForms.get18a(); if (sReg.compareTo("")!=0) sReg=" REG/"+sReg; else sReg="";
//		sDof = ATSForms.get18b(); if (sDof.compareTo("")!=0) { t18b(sDof); } else sDof="";
		
    	String sSts = get18tSts(); 
    	String sPbn = get18tPbn(); 
    	String sNav = get18tNav(); 
    	String sCom = get18tCom(); 
		String sDat = get18tDat(); 
		String sSur = get18tSur(); 
		String sDep = get18tDep(); 
		String sDest = get18tDest(); 
//		sDof = get18tDof(); 
//		sReg = get18tReg(); 
		String sEet = get18tEet(); 
		String sSel = get18tSel(); 
		String sTyp = get18tTyp(); 
		String sCode = get18tCode(); 
		String sDle = get18tDle(); 
		String sOpr = get18tOpr(); 
		String sOrgn = get18tOrgn(); 
		String sPer = get18tPer(); 
		String sAltn = get18tAltn(); 
		String sRalt = get18tRalt(); 
		String sTalt = get18tTalt(); 
		String sRif = get18tRif(); 
		String sRmk = get18tRmk(); 
		//---------------------------------
		if (sSts.startsWith(" ")) sSts=sSts.replaceFirst("\\s+", "");
		if (sPbn.startsWith(" ")) sPbn=sPbn.replaceFirst("\\s+", "");
		if (sNav.startsWith(" ")) sNav=sNav.replaceFirst("\\s+", "");
		if (sCom.startsWith(" ")) sCom=sCom.replaceFirst("\\s+", "");
		if (sDat.startsWith(" ")) sDat=sDat.replaceFirst("\\s+", "");
		if (sSur.startsWith(" ")) sSur=sSur.replaceFirst("\\s+", "");
		if (sDep.startsWith(" ")) sDep=sDep.replaceFirst("\\s+", "");
		if (sDest.startsWith(" ")) sDest=sDest.replaceFirst("\\s+", "");
//		if (sDof.startsWith(" ")) sDof=sDof.replaceFirst("\\s+", ""); t18b(sDof);
//		if (sReg.startsWith(" ")) sReg=sReg.replaceFirst("\\s+", "");
		if (sEet.startsWith(" ")) sEet=sEet.replaceFirst("\\s+", "");
		if (sSel.startsWith(" ")) sSel=sSel.replaceFirst("\\s+", "");
		if (sTyp.startsWith(" ")) sTyp=sTyp.replaceFirst("\\s+", "");
		if (sCode.startsWith(" ")) sCode=sCode.replaceFirst("\\s+", "");
		if (sDle.startsWith(" ")) sDle=sDle.replaceFirst("\\s+", "");
		if (sOpr.startsWith(" ")) sOpr=sOpr.replaceFirst("\\s+", "");
		if (sOrgn.startsWith(" ")) sOrgn=sOrgn.replaceFirst("\\s+", "");
		if (sPer.startsWith(" ")) sPer=sPer.replaceFirst("\\s+", "");
		if (sAltn.startsWith(" ")) sAltn=sAltn.replaceFirst("\\s+", "");
		if (sRalt.startsWith(" ")) sRalt=sRalt.replaceFirst("\\s+", "");
		if (sTalt.startsWith(" ")) sTalt=sTalt.replaceFirst("\\s+", "");
		if (sRif.startsWith(" ")) sRif=sRif.replaceFirst("\\s+", "");
		if (sRmk.startsWith(" ")) sRmk=sRmk.replaceFirst("\\s+", "");
		//---------------------------------
		if (sSts.compareTo("")!=0) sSts=" STS/"+sSts; else sSts="";
		if (sPbn.compareTo("")!=0) sPbn=" PBN/"+sPbn; else sPbn="";
		if (sNav.compareTo("")!=0) sNav=" NAV/"+sNav; else sNav="";
		if (sCom.compareTo("")!=0) sCom=" COM/"+sCom; else sCom="";
		if (sDat.compareTo("")!=0) sDat=" DAT/"+sDat; else sDat="";
		if (sSur.compareTo("")!=0) sSur=" SUR/"+sSur; else sSur="";
		if (sDep.compareTo("")!=0) sDep=" DEP/"+sDep; else sDep="";
		if (sDest.compareTo("")!=0) sDest=" DEST/"+sDest; else sDest="";
//		if (sDof.compareTo("")!=0) sDof=" DOF/"+sDof; else sDof="";
//		if (sReg.compareTo("")!=0) sReg=" REG/"+sReg; else sReg="";
		if (sEet.compareTo("")!=0) sEet=" EET/"+sEet; else sEet="";
		if (sSel.compareTo("")!=0) sSel=" SEL/"+sSel; else sSel="";
		if (sTyp.compareTo("")!=0) sTyp=" TYP/"+sTyp; else sTyp="";
		if (sCode.compareTo("")!=0) sCode=" CODE/"+sCode; else sCode="";
		if (sDle.compareTo("")!=0) sDle=" DLE/"+sDle; else sDle="";
		if (sOpr.compareTo("")!=0) sOpr=" OPR/"+sOpr; else sOpr="";
		if (sOrgn.compareTo("")!=0) sOrgn=" ORGN/"+sOrgn; else sOrgn="";
		if (sPer.compareTo("")!=0) sPer=" PER/"+sPer; else sPer="";
		if (sAltn.compareTo("")!=0) sAltn=" ALTN/"+sAltn; else sAltn="";
		if (sRalt.compareTo("")!=0) sRalt=" RALT/"+sRalt; else sRalt="";
		if (sTalt.compareTo("")!=0) sTalt=" TALT/"+sTalt; else sTalt="";
		if (sRif.compareTo("")!=0) sRif=" RIF/"+sRif; else sRif="";
		if (sRmk.compareTo("")!=0) sRmk=" RMK/"+sRmk; else sRmk="";
		
		s18Baru=sSts+sPbn+sNav+sCom+sDat+sSur+sDep+sDest+sEet+sSel+sTyp+sCode+sDle+sOpr+sOrgn+sPer+sAltn+sRalt+sTalt+sRif+sRmk;
		if (s18Baru.startsWith(" ")) s18Baru = s18Baru.replaceFirst("\\s+", "");
    }
	
	static void setFPL(
			String f3b, String f3c, 
			String f7a, String f7b, String f7c, 
			String f8a, String f8b,
			String f9a, String f9b, String f9c,
			String f10a, String f10bLama, String f10bBaru,
			String f13a, String f13b, 
			String f15a, String f15b, String f15c,
			String f16a, String f16b, String f16c, String f16d,
			
			//String f18a, String f18b, String f18c,
			String f18aLama, String f18aBaru, String f18bLama, String f18bBaru, String f18c,
			String fNav, String fCom, String fDat, String fSur, String fDep, String fDest, 
			String fEet, String fSel, String fTyp, String fCode, String fDle, String fOpr, 
			String fOrgn, String fPer, String fAltn, String fRalt, String fTalt, String fRif, 
			String fRmk, String fPbn, String fSts,
	
			String f19a, String f19b, String f19cUHF, String f19cVHF, String f19cELT,
    		String f19dP, String f19dD, String f19dM, String f19dJ, 
    		String f19eL, String f19eF, String f19eU, String f19eV,
    		String f19Num, String f19Cap, String f19Cov, String f19Col, String f19Air,
			String f19Rem,String f19Pil) {
		
		s3b=f3b; s3c=f3c; 
		s7a=f7a; s7b=f7b; s7c=f7c; 
		s8a=f8a; s8b=f8b;
		s9a=f9a; s9b=f9b; s9c=f9c; 
		s10a=f10a; s10bLama=f10bLama; s10bBaru=f10bBaru; 
		s13a=f13a; s13b=f13b; 
		s15a=f15a; s15b=f15b; s15c=f15c; 
		s16a=f16a; s16b=f16b; s16c=f16c; s16d=f16d; 
		//s18a=f18a; s18b=f18b; s18c=f18c;
		s18aLama=f18aLama; s18aBaru=f18aBaru; s18bLama=f18bLama; s18bBaru=f18bBaru; s18c=f18c;
		sNav=fNav; sCom=fCom; sDat=fDat; sSur=fSur; sDep=fDep; sDest=fDest; 
		sEet=fEet;  sSel=fSel; sTyp=fTyp; sCode=fCode; sDle=fDle; sOpr=fOpr;
		sOrgn=fOrgn; sPer=fPer; sAltn=fAltn; sRalt=fRalt; sTalt=fTalt; sRif=fRif; 
		sRmk=fRmk; sPbn=fPbn; sSts=fSts;
		
		s19a=f19a; s19b=f19b; s19cUHF=f19cUHF; s19cVHF=f19cVHF; s19cELT=f19cELT; 
		s19dP=f19dP; s19dD=f19dD; s19dM=f19dM; s19dJ=f19dJ; 
    	s19eL=f19eL; s19eF=f19eF; s19eU=f19eU; s19eV=f19eV; 
    	s19Num=f19Num; s19Cap=f19Cap; s19Cov=f19Cov; s19Col=f19Col; s19Air=f19Air; 
    	s19Rem=f19Rem; s19Pil=f19Pil;
    	
    	//if (s18b.isEmpty()) s18b="0";
    	if (s18bLama.isEmpty()) s18bLama="0";
    	if (s18bBaru.isEmpty()) s18bBaru="0";
	}
	
	public static void editCHGFromFPL() {
		String str3b=t3bFPL.getText();
		String str3c=t3cFPL.getText();
		
		String str7a=t7aFPL.getText();
		String str7b=""; if (t7bFPL.getSelection()==true) str7b=t7bFPL.getText(); else str7b="";
		String str7c=t7cFPL.getText();
		
//		String str8a=c8aFPL.getSelection()[0];
//		String str8b=c8bFPL.getSelection()[0];
		String str8a=t8aFPL.getText();
		String str8b=t8bFPL.getText();
		
		String str9a=t9aFPL.getText();
		String str9b=t9bFPL.getText();
//		String str9c=c9cFPL.getSelection()[0];
		String str9c=t9cFPL.getText();
		
		String str10a=t10aFPL.getText();
		String str10b=t10bFPL.getText();
		
		s10b=s10bBaru; 
		
		String str13a=t13aFPL.getText();
		String str13b=t13bFPL.getText();
		
		String str15a=t15aFPL.getText();
		String str15b=t15bFPL.getText();
		String str15c=t15cFPL.getText();
		
		String str16a=t16aFPL.getText();
		String str16b=t16bFPL.getText();
		String str16c=t16cFPL.getText();
		String str16d=t16dFPL.getText();

		String str18a=tRegFPL.getText();
		s18a=s18aBaru;

		String str18b=tDofFPL.getText();
		if (str18b.isEmpty()) str18b="0";
		//--
		s18b=s18bBaru;
		if (s18b.isEmpty()) s18b="0";
		//--
//		String str18c="";//t18cFPL.getText();
		
		String strNav=tNavFPL.getText(); String strCom=tComFPL.getText(); String strDat=tDatFPL.getText(); 
		String strSur=tSurFPL.getText(); String strDep=tDepFPL.getText(); String strDest =tDestFPL.getText(); 
		String strEet=tEetFPL.getText();  String strSel=tSelFPL.getText(); String strTyp=tTypFPL.getText(); 
		String strCode=tCodeFPL.getText(); String strDle=tDleFPL.getText(); String strOpr=tOprFPL.getText();
		String strOrgn=tOrgnFPL.getText(); String strPer=tPerFPL.getText(); String strAltn=tAltnFPL.getText(); 
		String strRalt=tRaltFPL.getText(); String strTalt=tTaltFPL.getText(); String strRif=tRifFPL.getText(); 
		String strRmk=tRmkFPL.getText(); String strPbn=tPbnFPL.getText(); String strSts=tStsFPL.getText();

		String str19a = t19aFPL.getText(); if (str19a == null) str19a="";
		String str19b = t19bFPL.getText(); if (str19b == null) str19b="";
		String str19cUHF=""; if (t19cUhfFPL.getText().compareTo("U")==0) { str19cUHF = t19cUhfFPL.toString(); str19cUHF = "R/U"; } else str19cUHF="";
		String str19cVHF=""; if (t19cVhfFPL.getText().compareTo("V")==0) { str19cVHF = t19cVhfFPL.toString(); str19cVHF = "R/V"; } else str19cVHF="";
		String str19cELT=""; if (t19cEltFPL.getText().compareTo("E")==0) { str19cELT = t19cEltFPL.toString(); str19cELT = "R/E"; } else str19cELT="";
		String str19dP=""; if (t19dPFPL.getText().compareTo("P")==0) { str19dP = t19dPFPL.toString(); str19dP = "S/P"; } else str19dP="";
		String str19dD=""; if (t19dDFPL.getText().compareTo("D")==0) { str19dD = t19dDFPL.toString(); str19dD = "S/D"; } else str19dD="";
		String str19dM=""; if (t19dMFPL.getText().compareTo("M")==0) { str19dM = t19dMFPL.toString(); str19dM = "S/M"; } else str19dM="";
		String str19dJ=""; if (t19dJFPL.getText().compareTo("J")==0) { str19dJ = t19dJFPL.toString(); str19dJ = "S/J"; } else str19dJ="";
		String str19eL=""; if (t19eLFPL.getText().compareTo("L")==0) { str19eL = t19eLFPL.toString(); str19eL = "J/L"; } else str19eL=""; 
		String str19eF=""; if (t19eFFPL.getText().compareTo("F")==0) { str19eF = t19eFFPL.toString(); str19eF = "J/F"; } else str19eF="";
		String str19eU=""; if (t19eUFPL.getText().compareTo("U")==0) { str19eU = t19eUFPL.toString(); str19eU = "J/U"; } else str19eU="";
		String str19eV=""; if (t19eVFPL.getText().compareTo("V")==0) { str19eV = t19eVFPL.toString(); str19eV = "J/V"; } else str19eV="";
		String str19f_cover=""; if (t19cFPL.getText().compareTo("C")==0) { str19f_cover = t19cFPL.toString(); str19f_cover = "C"; } else str19f_cover="";
			
		String str19f_number = t19NumFPL.getText(); if (str19f_number == null) str19f_number="";
		String str19f_capacity = t19CapFPL.getText(); if (str19f_capacity == null) str19f_capacity="";
		String str19f_colour = t19ColFPL.getText(); if (str19f_colour == null) str19f_colour="";
		String str19g = t19AirFPL.getText(); if (str19g == null) str19g="";
		String str19Remarks = t19RemFPL.getText(); if (str19Remarks == null) str19Remarks="";
		String str19i = t19PilFPL.getText(); if (str19i == null) str19i="";
		
		tFbCHG.setText(tFbFPL.getText());
		editHeaderFromFPL(HeaderComposite.tPriorityCHG,HeaderComposite.tFilingTimeCHG,HeaderComposite.tOriginatorCHG,HeaderComposite.tOriRefCHG,
				HeaderComposite.bBellCHG,HeaderComposite.tDest1CHG,HeaderComposite.tDest2CHG,HeaderComposite.tDest3CHG,
				HeaderComposite.tDest4CHG,HeaderComposite.tDest5CHG,HeaderComposite.tDest6CHG,HeaderComposite.tDest7CHG,
				HeaderComposite.tDest8CHG,HeaderComposite.tDest9CHG,HeaderComposite.tDest10CHG,HeaderComposite.tDest11CHG,
				HeaderComposite.tDest12CHG,HeaderComposite.tDest13CHG,HeaderComposite.tDest14CHG,HeaderComposite.tDest15CHG,
				HeaderComposite.tDest16CHG,HeaderComposite.tDest17CHG,HeaderComposite.tDest18CHG,HeaderComposite.tDest19CHG,
				HeaderComposite.tDest20CHG,HeaderComposite.tDest21CHG);
		
		t3aCHG.setText("CHG");
		t3bCHG.setText(s3b);
		t3cCHG.setText(s3c);
		t7aCHG.setText(s7a);
		if (s7b.compareTo("")==0) t7bCHG.setSelection(false); else t7bCHG.setSelection(true);
		t7cCHG.setText(s7c);
		t13aCHG.setText(s13a);
		t13bCHG.setText(s13b);
		t16aCHG.setText(s16a);
		t16bCHG.setText(s16b);
		t16cCHG.setText(s16c);
		t16dCHG.setText(s16d);
		tDofCHG.setText(s18b);
		
		String amendment="";
		//--------------------------------------------
		String am3="",am3b="",am3c="";//,s3a="";
		if (s3b.compareTo(str3b)!=0) { am3b=str3b; } else { am3b=s3b; };//s3a=""; }
		if (s3c.compareTo(str3c)!=0) { am3c=str3c; } else { am3c=s3c; };//s3a=""; }
//		System.out.println("s3a="+s3a);
		
		if (s3b.compareTo(str3b)==0 && s3c.compareTo(str3c)==0) am3="";
		else if (s3b.compareTo(str3b)==0 || s3c.compareTo(str3c)==0) am3=am3b+am3c;
		else if (s3b.compareTo(str3b)!=0 && s3c.compareTo(str3c)!=0) am3=am3b+am3c;
		
		if (!am3.isEmpty()) am3 = am3.replaceFirst(am3, "-3/"+am3);
		//--------------------------------------------
		String am7="",am7a="",am7b="",am7c="";
		if (s7a.compareTo(str7a)!=0) { am7a=str7a; } else am7a=s7a;
		if (s7b.compareTo(str7b)!=0) am7b=str7b; else am7b=s7b;
		if (s7c.compareTo(str7c)!=0) am7c=str7c; else am7c=s7c;
		
		if (s7a.compareTo(str7a)==0 && s7b.compareTo(str7b)==0 && s7c.compareTo(str7c)==0) am7="";
		else if (s7a.compareTo(str7a)==0 || s7b.compareTo(str7b)==0 || s7c.compareTo(str7c)==0) {
			if (!am7b.isEmpty() || !am7c.isEmpty()) {
				am7=am7a+"/"+am7b+am7c;	
			} else { am7=am7a; }
		}
		else if (s7a.compareTo(str7a)!=0 && s7b.compareTo(str7b)!=0 && s7c.compareTo(str7c)!=0) {
			if (!am7b.isEmpty() || !am7c.isEmpty()) {
				am7=am7a+"/"+am7b+am7c;	
			} else { am7=am7a; }
		}
		if (!am7.isEmpty()) am7 = am7.replaceFirst(am7, "-7/"+am7);
		//--------------------------------------------
		String am8="",am8a="",am8b="";
		if (s8a.compareTo(str8a)!=0) am8a=str8a; else am8a=s8a;
		if (s8b.compareTo(str8b)!=0) am8b=str8b; else am8b=s8b;
		
		if (s8a.compareTo(str8a)==0 && s8b.compareTo(str8b)==0) am8="";
		else if (s8a.compareTo(str8a)==0 || s8b.compareTo(str8b)==0) am8=am8a+am8b;
		else if (s8a.compareTo(str8a)!=0 && s8b.compareTo(str8b)!=0) am8=am8a+am8b;
		if (!am8.isEmpty()) am8 = am8.replaceFirst(am8, "-8/"+am8);
		//--------------------------------------------
		String am9="",am9a="",am9b="",am9c="";
		if (s9a.compareTo(str9a)!=0) am9a=str9a; else am9a=s9a;
		if (s9b.compareTo(str9b)!=0) am9b=str9b; else am9b=s9b;
		if (s9c.compareTo(str9c)!=0) am9c=str9c; else am9c=s9c;
		
		if (s9a.compareTo(str9a)==0 && s9b.compareTo(str9b)==0 && s9c.compareTo(str9c)==0) am9="";
		else if (s9a.compareTo(str9a)==0 || s9b.compareTo(str9b)==0 || s9c.compareTo(str9c)==0) am9=am9a+am9b+"/"+am9c;
		else if (s9a.compareTo(str9a)!=0 && s9b.compareTo(str9b)!=0 && s9c.compareTo(str9c)!=0) am9=am9a+am9b+"/"+am9c;
		if (!am9.isEmpty()) am9 = am9.replaceFirst(am9, "-9/"+am9);
		//--------------------------------------------
		String am10="",am10a="",am10b="";
		if (s10a.compareTo(str10a)!=0) am10a=str10a; else am10a=s10a;
		if (s10b.compareTo(str10b)!=0) am10b=str10b; else am10b=s10b;
		
		if (s10a.compareTo(str10a)==0 && s10b.compareTo(str10b)==0) am10="";
		else if (s10a.compareTo(str10a)==0 || s10b.compareTo(str10b)==0) am10=am10a+"/"+am10b;
		else if (s10a.compareTo(str10a)!=0 && s10b.compareTo(str10b)!=0) am10=am10a+"/"+am10b;
		if (!am10.isEmpty()) am10 = am10.replaceFirst(am10, "-10/"+am10);
		//--------------------------------------------
		String am13="",am13a="",am13b="";
		if (s13a.compareTo(str13a)!=0) am13a=str13a; else am13a=s13a;
		if (s13b.compareTo(str13b)!=0) am13b=str13b; else am13b=s13b;
		
		if (s13a.compareTo(str13a)==0 && s13b.compareTo(str13b)==0) am13="";
		else if (s13a.compareTo(str13a)==0 || s13b.compareTo(str13b)==0) am13=am13a+am13b;
		else if (s13a.compareTo(str13a)!=0 && s13b.compareTo(str13b)!=0) am13=am13a+am13b;
		if (!am13.isEmpty()) am13 = am13.replaceFirst(am13, "-13/"+am13);
		//--------------------------------------------
		String am15="",am15a="",am15b="",am15c="";
		if (s15a.compareTo(str15a)!=0) am15a=str15a; else am15a=s15a;
		if (s15b.compareTo(str15b)!=0) am15b=str15b; else am15b=s15b;
		if (s15c.compareTo(str15c)!=0) am15c=str15c; else am15c=s15c;
		
		if (s15a.compareTo(str15a)==0 && s15b.compareTo(str15b)==0 && s15c.compareTo(str15c)==0) am15="";
		else if (s15a.compareTo(str15a)==0 || s15b.compareTo(str15b)==0 || s15c.compareTo(str15c)==0) am15=am15a+am15b+" "+am15c;
		else if (s15a.compareTo(str15a)!=0 && s15b.compareTo(str15b)!=0 && s15c.compareTo(str15c)!=0) am15=am15a+am15b+" "+am15c;
		if (!am15.isEmpty()) am15 = am15.replaceFirst(am15, "-15/"+am15);
		//--------------------------------------------
		String am16="",am16a="",am16b="",am16c="",am16d="";
		if (s16a.compareTo(str16a)!=0) am16a=str16a; else am16a=s16a;
		if (s16b.compareTo(str16b)!=0) am16b=str16b; else am16b=s16b;
		if (s16c.compareTo(str16c)!=0) am16c=" "+str16c; else am16c=" "+s16c;
		if (s16d.compareTo(str16d)!=0) am16d=" "+str16d; else am16d=" "+s16d;
		//--
		if (am16c.compareTo(" ")==0) am16c="";
		if (am16d.compareTo(" ")==0) am16d="";
		
		if (s16a.compareTo(str16a)==0 && s16b.compareTo(str16b)==0 && s16c.compareTo(str16c)==0 && s16d.compareTo(str16d)==0) am16="";
		else if (s16a.compareTo(str16a)==0 || s16b.compareTo(str16b)==0 || s16c.compareTo(str16c)==0 || s16d.compareTo(str16d)==0) am16=am16a+am16b+am16c+am16d;
		else if (s16a.compareTo(str16a)!=0 && s16b.compareTo(str16b)!=0  && s16c.compareTo(str16c)!=0 && s16d.compareTo(str16d)!=0) am16=am16a+am16b+am16c+am16d;
		if (!am16.isEmpty()) am16 = am16.replaceFirst(am16, "-16/"+am16);
//		if (am16.endsWith(" ")) {
//			int i = am16.lastIndexOf(" ");
//			am16 = am16.substring(0, i);
//		}
		//--------------------------------------------
		String am18="",am18a="",am18b="",amNav="",amCom="",amDat="",amSur="",amDep="",amDest ="",amEet="",amSel="",amTyp="",amCode="",amDle="",amOpr="",amOrgn="",
		amPer="",amAltn="",amRalt="",amTalt="",amRif="",amRmk="",amPbn="",amSts="";
		if (s18b.compareTo(str18b)!=0) {
			am18b=" DOF/"+str18b;
			//lama
//			if (getClickedFPL().compareToIgnoreCase("present") == 0) {
//				if (s18a.compareTo(str18a)!=0) am18a=" REG/"+str18a; else if (!s18a.isEmpty()) am18a=" REG/"+s18a; else am18a="";
//				if (s18c.compareTo(str18c)!=0) am18cLama=" "+str18c; else if (!s18c.isEmpty()) am18cLama=" "+s18c; else am18cLama="";
//				if (am18b.contains(" DOF/0")) am18b = am18b.replace(" DOF/0", "");
//				am18 = am18a+am18b+am18cLama;
//				if (!am18.isEmpty()) { 
//					if (am18.startsWith(" ")) { am18 = am18.replaceFirst(" ", ""); }
//					am18 = am18.replaceFirst(am18, "-18/"+am18); } else am18="";
//			//baru
//			} else if (getClickedFPL().compareToIgnoreCase("new") == 0) {
				if (sSts.compareTo(strSts)!=0) amSts=" STS/"+strSts; else if (!sSts.isEmpty()) amSts=" STS/"+sSts; else amSts="";
				if (sPbn.compareTo(strPbn)!=0) amPbn=" PBN/"+strPbn; else if (!sPbn.isEmpty()) amPbn=" PBN/"+sPbn; else amPbn="";
				if (sNav.compareTo(strNav)!=0) amNav=" NAV/"+strNav; else if (!sNav.isEmpty()) amNav=" NAV/"+sNav; else amNav="";
				if (sCom.compareTo(strCom)!=0) amCom=" COM/"+strCom; else if (!sCom.isEmpty()) amCom=" COM/"+sCom; else amCom="";
				if (sDat.compareTo(strDat)!=0) amDat=" DAT/"+strDat; else if (!sDat.isEmpty()) amDat=" DAT/"+sDat; else amDat="";
				if (sSur.compareTo(strSur)!=0) amSur=" SUR/"+strSur; else if (!sSur.isEmpty()) amSur=" SUR/"+sSur; else amSur="";
				if (sDep.compareTo(strDep)!=0) amDep=" DEP/"+strDep; else if (!sDep.isEmpty()) amDep=" DEP/"+sDep; else amDep="";
				if (sDest.compareTo(strDest)!=0) amDest=" DEST/"+strDest; else if (!sDest.isEmpty()) amDest=" DEST/"+sDest; else amDest="";
				if (s18a.compareTo(str18a)!=0) am18a=" REG/"+str18a; else if (!s18a.isEmpty()) am18a=" REG/"+s18a; else am18a="";
				//if (s18b.compareTo(str18b)!=0) am18b=" DOF/"+str18b; else if (!s18b.isEmpty()) am18b=""; else am18b="";
				if (sEet.compareTo(strEet)!=0) amEet=" EET/"+strEet; else if (!sEet.isEmpty()) amEet=" EET/"+sEet; else amEet="";
				if (sSel.compareTo(strSel)!=0) amSel=" SEL/"+strSel; else if (!sSel.isEmpty()) amSel=" SEL/"+sSel; else amSel="";
				if (sTyp.compareTo(strTyp)!=0) amTyp=" TYP/"+strTyp; else if (!sTyp.isEmpty()) amTyp=" TYP/"+sTyp; else amTyp="";
				if (sCode.compareTo(strCode)!=0) amCode=" CODE/"+strCode; else if (!sCode.isEmpty()) amCode=" CODE/"+sCode; else amCode="";
				if (sDle.compareTo(strDle)!=0) amDle=" DLE/"+strDle; else if (!sDle.isEmpty()) amDle=" DLE/"+sDle; else amDle="";
				if (sOpr.compareTo(strOpr)!=0) amOpr=" OPR/"+strOpr; else if (!sOpr.isEmpty()) amOpr=" OPR/"+sOpr; else amOpr="";
				if (sOrgn.compareTo(strOrgn)!=0) amOrgn=" ORGN/"+strOrgn; else if (!sOrgn.isEmpty()) amOrgn=" ORGN/"+sOrgn; else amOrgn="";
				if (sPer.compareTo(strPer)!=0) amPer=" PER/"+strPer; else if (!sPer.isEmpty()) amPer=" PER/"+sPer; else amPer="";
				if (sAltn.compareTo(strAltn)!=0) amAltn=" ALTN/"+strAltn; else if (!sAltn.isEmpty()) amAltn=" ALTN/"+sAltn; else amAltn="";
				if (sRalt.compareTo(strRalt)!=0) amRalt=" RALT/"+strRalt; else if (!sRalt.isEmpty()) amRalt=" RALT/"+sRalt; else amRalt="";
				if (sTalt.compareTo(strTalt)!=0) amTalt=" TALT/"+strTalt; else if (!sTalt.isEmpty()) amTalt=" TALT/"+sTalt; else amTalt="";
				if (sRif.compareTo(strRif)!=0) amRif=" RIF/"+strRif; else if (!sRif.isEmpty()) amRif=" RIF/"+sRif; else amRif="";
				if (sRmk.compareTo(strRmk)!=0) amRmk=" RMK/"+strRmk; else if (!sRmk.isEmpty()) amRmk=" RMK/"+sRmk; else amRmk="";
				
				if (am18b.contains(" DOF/0")) am18b = am18b.replace(" DOF/0", "");
				am18 = amSts+amPbn+amNav+amCom+amDat+amSur+amDep+amDest+am18a+am18b+amEet+amSel+amTyp+amCode+amDle+amOpr+amOrgn+amPer+amAltn+amRalt+amTalt+amRif+amRmk;
				if (!am18.isEmpty()) { am18 = am18.replaceFirst(" ", "-18/"); } else am18="";
//			}
		} else {
			am18b="";
			//lama
//			if (getClickedFPL().compareToIgnoreCase("present") == 0) {
//				if (s18a.compareTo(str18a)!=0) am18a=" REG/"+str18a; else am18a="";
//				if (s18c.compareTo(str18c)!=0) {
//					am18cLama=str18c.replace(s18c, "");
//					am18cLama=" "+str18c;
//				} else am18cLama="";
//				
//				if (am18b.contains(" DOF/0")) am18b = am18b.replace(" DOF/0", "");
//				am18 = am18a+am18b+am18cLama;
//				if (!am18.isEmpty()) { 
//					if (am18.startsWith(" ")) { am18 = am18.replaceFirst(" ", ""); }
//					am18 = am18.replaceFirst(am18, "-18/"+am18); } else am18="";
//			//baru
//			} else if (getClickedFPL().compareToIgnoreCase("new") == 0) {
				if (sSts.compareTo(strSts)!=0) amSts=" STS/"+strSts; else amSts="";
				if (sPbn.compareTo(strPbn)!=0) amPbn=" PBN/"+strPbn; else amPbn="";
				if (sNav.compareTo(strNav)!=0) amNav=" NAV/"+strNav; else amNav="";
				if (sCom.compareTo(strCom)!=0) amCom=" COM/"+strCom; else amCom="";
				if (sDat.compareTo(strDat)!=0) amDat=" DAT/"+strDat; else amDat="";
				if (sSur.compareTo(strSur)!=0) amSur=" SUR/"+strSur; else amSur="";
				if (sDep.compareTo(strDep)!=0) amDep=" DEP/"+strDep; else amDep="";
				if (sDest.compareTo(strDest)!=0) amDest=" DEST/"+strDest; else amDest="";
				if (s18a.compareTo(str18a)!=0) am18a=" REG/"+str18a; else am18a="";
				//if (s18b.compareTo(str18b)!=0) am18b=" DOF/"+str18b; else am18b="";
				if (sEet.compareTo(strEet)!=0) amEet=" EET/"+strEet; else amEet="";
				if (sSel.compareTo(strSel)!=0) amSel=" SEL/"+strSel; else amSel="";
				if (sTyp.compareTo(strTyp)!=0) amTyp=" TYP/"+strTyp; else amTyp="";
				if (sCode.compareTo(strCode)!=0) amCode=" CODE/"+strCode; else amCode="";
				if (sDle.compareTo(strDle)!=0) amDle=" DLE/"+strDle; else amDle="";
				if (sOpr.compareTo(strOpr)!=0) amOpr=" OPR/"+strOpr; else amOpr="";
				if (sOrgn.compareTo(strOrgn)!=0) amOrgn=" ORGN/"+strOrgn; else amOrgn="";
				if (sPer.compareTo(strPer)!=0) amPer=" PER/"+strPer; else amPer="";
				if (sAltn.compareTo(strAltn)!=0) amAltn=" ALTN/"+strAltn; else amAltn="";
				if (sRalt.compareTo(strRalt)!=0) amRalt=" RALT/"+strRalt; else amRalt="";
				if (sTalt.compareTo(strTalt)!=0) amTalt=" TALT/"+strTalt; else amTalt="";
				if (sRif.compareTo(strRif)!=0) amRif=" RIF/"+strRif; else amRif="";
				if (sRmk.compareTo(strRmk)!=0) amRmk=" RMK/"+strRmk; else amRmk="";
				
				if (am18b.contains(" DOF/0")) am18b = am18b.replace(" DOF/0", "");
				am18 = amSts+amPbn+amNav+amCom+amDat+amSur+amDep+amDest+am18a+am18b+amEet+amSel+amTyp+amCode+amDle+amOpr+amOrgn+amPer+amAltn+amRalt+amTalt+amRif+amRmk;
				if (!am18.isEmpty()) { am18 = am18.replaceFirst(" ", "-18/"); } else am18="";
//			}
			//if (!am18.isEmpty()) { am18 = am18.replaceFirst(" ", "-18/"); } else am18="";
		}
		//--------------------------------------------
		String am19="",am19a="",am19b="",am19c="",am19d="",am19e="",am19f="",am19g="",am19h="",am19i="";
		if (s19a.compareTo(str19a)!=0) am19a=str19a; else am19a=s19a;
		if (s19b.compareTo(str19b)!=0) am19b=str19b; else am19b=s19b;
		if (!am19a.isEmpty()) am19a="E/"+am19a; else am19a="";
		if (!am19b.isEmpty()) am19b=" P/"+am19b; else am19b="";
		// R/UVE 	
		String cUHF="",cVHF="",cELT="",R="";
		if (s19cUHF.compareTo(str19cUHF)!=0) cUHF=str19cUHF.replaceAll("R/U","U"); else cUHF=s19cUHF.replaceAll("R/U","U");
		if (s19cVHF.compareTo(str19cVHF)!=0) cVHF=str19cVHF.replaceAll("R/V","V"); else cVHF=s19cVHF.replaceAll("R/V","V");
		if (s19cELT.compareTo(str19cELT)!=0) cELT=str19cELT.replaceAll("R/E","E"); else cELT=s19cELT.replaceAll("R/E","E");
		R=cUHF+cVHF+cELT;
		if (!R.isEmpty()) am19c=" R/"+R; else am19c=""; 
		// S/PDMJ
		String dP="",dD="",dM="",dJ="",S="";
		if (s19dP.compareTo(str19dP)!=0) dP=str19dP.replaceAll("S/P","P"); else dP=s19dP.replaceAll("S/P","P");
		if (s19dD.compareTo(str19dD)!=0) dD=str19dD.replaceAll("S/D","D"); else dD=s19dD.replaceAll("S/D","D");
		if (s19dM.compareTo(str19dM)!=0) dM=str19dM.replaceAll("S/M","M"); else dM=s19dM.replaceAll("S/M","M");
		if (s19dJ.compareTo(str19dJ)!=0) dJ=str19dJ.replaceAll("S/J","J"); else dJ=s19dJ.replaceAll("S/J","J");
		S=dP+dD+dM+dJ;
		if (!S.isEmpty()) am19d=" S/"+S; else am19d="";
		// J/LFUV 
		String eL="",eF="",eU="",eV="",J="";
		if (s19eL.compareTo(str19eL)!=0) eL=str19eL.replaceAll("J/L","L"); else eL=s19eL.replaceAll("J/L","L");
		if (s19eF.compareTo(str19eF)!=0) eF=str19eF.replaceAll("J/F","F"); else eF=s19eF.replaceAll("J/F","F");
		if (s19eU.compareTo(str19eU)!=0) eU=str19eU.replaceAll("J/U","U"); else eU=s19eU.replaceAll("J/U","U");
		if (s19eV.compareTo(str19eV)!=0) eV=str19eV.replaceAll("J/V","V"); else eV=s19eV.replaceAll("J/V","V");
		J=eL+eF+eU+eV;
		if (!J.isEmpty()) am19e=" J/"+J; else am19e="";
		// 19fD,f_number,capacity,cover,colour
		String fD="",fCap="",fCov="",fCol="",F="";
		if (s19Num.compareTo(str19f_number)!=0) fD=str19f_number; else fD=s19Num;
		if (s19Cap.compareTo(str19f_capacity)!=0) fCap=str19f_capacity; else fCap=s19Cap;
		if (s19Cov.compareTo(str19f_cover)!=0) fCov=str19f_cover; else fCov=s19Cov;
		if (s19Col.compareTo(str19f_colour)!=0) fCol=str19f_colour; else fCol=s19Col;
		F=fD+" "+fCap+" "+fCov+" "+fCol;
		if (!F.isEmpty()) am19f=" D/"+F; else am19f="";
		// 19g
		String A="";
		if (s19Air.compareTo(str19g)!=0) A=str19g; else A=s19Air;
		if (!A.isEmpty()) am19g="\nA/"+A; else am19g="";
		// 19hN,Remarks
		String N="";
		if (s19Rem.compareTo(str19Remarks)!=0) N=str19Remarks; else N=s19Rem;
		if (!N.isEmpty()) am19h=" N/"+N; else am19h="";
		// 19i
		String C="";
		if (s19Pil.compareTo(str19i)!=0) C=str19i; else C=s19Pil;
		if (!C.isEmpty()) am19i="\nC/"+C+"\n"; else am19i="";
		
		if (s19a.compareTo(str19a)==0 && s19b.compareTo(str19b)==0
				&& s19cUHF.compareTo(str19cUHF)==0 && s19cVHF.compareTo(str19cVHF)==0 && s19cELT.compareTo(str19cELT)==0
				&& s19dP.compareTo(str19dP)==0 && s19dD.compareTo(str19dD)==0 && s19dM.compareTo(str19dM)==0 && s19dJ.compareTo(str19dJ)==0
				&& s19eL.compareTo(str19eL)==0 && s19eF.compareTo(str19eF)==0 && s19eU.compareTo(str19eU)==0 && s19eV.compareTo(str19eV)==0
				&& s19Num.compareTo(str19f_number)==0 && s19Cap.compareTo(str19f_capacity)==0 
				&& s19Cov.compareTo(str19f_cover)==0 && s19Col.compareTo(str19f_colour)==0
				&& s19Air.compareTo(str19g)==0 && s19Rem.compareTo(str19Remarks)==0 && s19Pil.compareTo(str19i)==0) 
			am19="";
		else if (s19a.compareTo(str19a)==0 || s19b.compareTo(str19b)==0 
				|| s19cUHF.compareTo(str19cUHF)==0 || s19cVHF.compareTo(str19cVHF)==0 || s19cELT.compareTo(str19cELT)==0
				|| s19dP.compareTo(str19dP)==0 || s19dD.compareTo(str19dD)==0 || s19dM.compareTo(str19dM)==0 || s19dJ.compareTo(str19dJ)==0
				|| s19eL.compareTo(str19eL)==0 || s19eF.compareTo(str19eF)==0 || s19eU.compareTo(str19eU)==0 || s19eV.compareTo(str19eV)==0
				|| s19Num.compareTo(str19f_number)==0 || s19Cap.compareTo(str19f_capacity)==0 
				|| s19Cov.compareTo(str19f_cover)==0 || s19Col.compareTo(str19f_colour)==0
				|| s19Air.compareTo(str19g)==0 || s19Rem.compareTo(str19Remarks)==0 || s19Pil.compareTo(str19i)==0) 
			am19=am19a+am19b+am19c+am19d+am19e+am19f+am19g+am19h+am19i;
		else if (s19a.compareTo(str19a)!=0 && s19b.compareTo(str19b)!=0 
				&& s19cUHF.compareTo(str19cUHF)!=0 && s19cVHF.compareTo(str19cVHF)!=0 && s19cELT.compareTo(str19cELT)!=0
				&& s19dP.compareTo(str19dP)!=0 && s19dD.compareTo(str19dD)!=0 && s19dM.compareTo(str19dM)!=0 && s19dJ.compareTo(str19dJ)!=0
				&& s19eL.compareTo(str19eL)!=0 && s19eF.compareTo(str19eF)!=0 && s19eU.compareTo(str19eU)!=0 && s19eV.compareTo(str19eV)!=0
				&& s19Num.compareTo(str19f_number)!=0 && s19Cap.compareTo(str19f_capacity)!=0 
				&& s19Cov.compareTo(str19f_cover)!=0 && s19Col.compareTo(str19f_colour)!=0
				&& s19Air.compareTo(str19g)!=0 && s19Rem.compareTo(str19Remarks)!=0 && s19Pil.compareTo(str19i)!=0) 
			am19=am19a+am19b+am19c+am19d+am19e+am19f+am19g+am19h+am19i;
		
		if (!am19.isEmpty()) {
			if (am19.startsWith(" ")) am19=am19.replaceFirst("\\s+", "");
			am19 = am19.replaceFirst(am19, "-19/"+am19);
		}
		
		amendment = am3+am7+am8+am9+am10+am13+am15+am16+am18;//+am19;
		if (amendment.startsWith("-")) amendment = amendment.replaceFirst("-", "");
		t22CHG.setText(amendment);
	}

	public static void editDLAFromFPL() {
		t3aDLA.setText("DLA");
		t3bDLA.setText(s3b);
		t3cDLA.setText(s3c);
		t7aDLA.setText(s7a);
		if (s7b.compareTo("")==0) t7bDLA.setSelection(false); else t7bDLA.setSelection(true);
		t7cDLA.setText(s7c);
		t13aDLA.setText(s13a);
		t13bDLA.setText(s13b);
		t16aDLA.setText(s16a);
		t16bDLA.setText(s16b);
		t16cDLA.setText(s16c);
		t16dDLA.setText(s16d);
		String str18b = tDofFPL.getText();
		if (str18b.isEmpty()) str18b="0";
		tDofDLA.setText(str18b);
		tFbDLA.setText(tFbFPL.getText());
		editHeaderFromFPL(HeaderComposite.tPriorityDLA,HeaderComposite.tFilingTimeDLA,HeaderComposite.tOriginatorDLA,HeaderComposite.tOriRefDLA,
				HeaderComposite.bBellDLA,HeaderComposite.tDest1DLA,HeaderComposite.tDest2DLA,HeaderComposite.tDest3DLA,
				HeaderComposite.tDest4DLA,HeaderComposite.tDest5DLA,HeaderComposite.tDest6DLA,HeaderComposite.tDest7DLA,
				HeaderComposite.tDest8DLA,HeaderComposite.tDest9DLA,HeaderComposite.tDest10DLA,HeaderComposite.tDest11DLA,
				HeaderComposite.tDest12DLA,HeaderComposite.tDest13DLA,HeaderComposite.tDest14DLA,HeaderComposite.tDest15DLA,
				HeaderComposite.tDest16DLA,HeaderComposite.tDest17DLA,HeaderComposite.tDest18DLA,HeaderComposite.tDest19DLA,
				HeaderComposite.tDest20DLA,HeaderComposite.tDest21DLA);
	}
	
	public static void editDEPFromFPL () {
		t3aDEP.setText("DEP");
		t3bDEP.setText(s3b);
		t3cDEP.setText(s3c);
		t7aDEP.setText(s7a);
		if (s7b.compareTo("")==0) t7bDEP.setSelection(false); else t7bDEP.setSelection(true);
		t7cDEP.setText(s7c);
		t13aDEP.setText(s13a);
		t13bDEP.setText(s13b);
		t16aDEP.setText(s16a);
		t16bDEP.setText(s16b);
		t16cDEP.setText(s16c);
		t16dDEP.setText(s16d);
		String str18b = tDofFPL.getText();
		if (str18b.isEmpty()) str18b="0";
		tDofDEP.setText(str18b);
		tFbDEP.setText(tFbFPL.getText());
		editHeaderFromFPL(HeaderComposite.tPriorityDEP,HeaderComposite.tFilingTimeDEP,HeaderComposite.tOriginatorDEP,HeaderComposite.tOriRefDEP,
				HeaderComposite.bBellDEP,HeaderComposite.tDest1DEP,HeaderComposite.tDest2DEP,HeaderComposite.tDest3DEP,
				HeaderComposite.tDest4DEP,HeaderComposite.tDest5DEP,HeaderComposite.tDest6DEP,HeaderComposite.tDest7DEP,
				HeaderComposite.tDest8DEP,HeaderComposite.tDest9DEP,HeaderComposite.tDest10DEP,HeaderComposite.tDest11DEP,
				HeaderComposite.tDest12DEP,HeaderComposite.tDest13DEP,HeaderComposite.tDest14DEP,HeaderComposite.tDest15DEP,
				HeaderComposite.tDest16DEP,HeaderComposite.tDest17DEP,HeaderComposite.tDest18DEP,HeaderComposite.tDest19DEP,
				HeaderComposite.tDest20DEP,HeaderComposite.tDest21DEP);
	}

	public static void editCNLFromFPL () {
		t3aCNL.setText("CNL");
		t3bCNL.setText(s3b);
		t3cCNL.setText(s3c);
		t7aCNL.setText(s7a);
		if (s7b.compareTo("")==0) t7bCNL.setSelection(false); else t7bCNL.setSelection(true);
		t7cCNL.setText(s7c);
		t13aCNL.setText(s13a);
		t13bCNL.setText(s13b);
		t16aCNL.setText(s16a);
		t16bCNL.setText(s16b);
		t16cCNL.setText(s16c);
		t16dCNL.setText(s16d);
		String str18b = tDofFPL.getText();
		if (str18b.isEmpty()) str18b="0";
		tDofCNL.setText(str18b);
		tFbCNL.setText(tFbFPL.getText());
		editHeaderFromFPL(HeaderComposite.tPriorityCNL,HeaderComposite.tFilingTimeCNL,HeaderComposite.tOriginatorCNL,HeaderComposite.tOriRefCNL,
				HeaderComposite.bBellCNL,HeaderComposite.tDest1CNL,HeaderComposite.tDest2CNL,HeaderComposite.tDest3CNL,
				HeaderComposite.tDest4CNL,HeaderComposite.tDest5CNL,HeaderComposite.tDest6CNL,HeaderComposite.tDest7CNL,
				HeaderComposite.tDest8CNL,HeaderComposite.tDest9CNL,HeaderComposite.tDest10CNL,HeaderComposite.tDest11CNL,
				HeaderComposite.tDest12CNL,HeaderComposite.tDest13CNL,HeaderComposite.tDest14CNL,HeaderComposite.tDest15CNL,
				HeaderComposite.tDest16CNL,HeaderComposite.tDest17CNL,HeaderComposite.tDest18CNL,HeaderComposite.tDest19CNL,
				HeaderComposite.tDest20CNL,HeaderComposite.tDest21CNL);
	}
	
	public static void editARRFromFPL () {
		t3aARR.setText("ARR");
		t3bARR.setText(s3b);
		t3cARR.setText(s3c);
		t7aARR.setText(s7a);
		if (s7b.compareTo("")==0) t7bARR.setSelection(false); else t7bARR.setSelection(true);
		t7cARR.setText(s7c);
		t13aARR.setText(s13a);
		t13bARR.setText(s13b);
		tFbARR.setText(tFbFPL.getText());
		editHeaderFromFPL(HeaderComposite.tPriorityARR,HeaderComposite.tFilingTimeARR,HeaderComposite.tOriginatorARR,HeaderComposite.tOriRefARR,
				HeaderComposite.bBellARR,HeaderComposite.tDest1ARR,HeaderComposite.tDest2ARR,HeaderComposite.tDest3ARR,
				HeaderComposite.tDest4ARR,HeaderComposite.tDest5ARR,HeaderComposite.tDest6ARR,HeaderComposite.tDest7ARR,
				HeaderComposite.tDest8ARR,HeaderComposite.tDest9ARR,HeaderComposite.tDest10ARR,HeaderComposite.tDest11ARR,
				HeaderComposite.tDest12ARR,HeaderComposite.tDest13ARR,HeaderComposite.tDest14ARR,HeaderComposite.tDest15ARR,
				HeaderComposite.tDest16ARR,HeaderComposite.tDest17ARR,HeaderComposite.tDest18ARR,HeaderComposite.tDest19ARR,
				HeaderComposite.tDest20ARR,HeaderComposite.tDest21ARR);
	}

	public static void setCurrentDLA() {
		// HEADER
		editHeader(HeaderComposite.tSendAtDLA,HeaderComposite.tPriorityDLA,HeaderComposite.tFilingTimeDLA,HeaderComposite.tOriginatorDLA,
				HeaderComposite.tOriRefDLA,tFbDLA,HeaderComposite.bBellDLA,HeaderComposite.tDest1DLA,HeaderComposite.tDest2DLA,
				HeaderComposite.tDest3DLA,HeaderComposite.tDest4DLA,HeaderComposite.tDest5DLA,HeaderComposite.tDest6DLA,
				HeaderComposite.tDest7DLA,HeaderComposite.tDest8DLA,HeaderComposite.tDest9DLA,HeaderComposite.tDest10DLA,
				HeaderComposite.tDest11DLA,HeaderComposite.tDest12DLA,HeaderComposite.tDest13DLA,HeaderComposite.tDest14DLA,
				HeaderComposite.tDest15DLA,HeaderComposite.tDest16DLA,HeaderComposite.tDest17DLA,HeaderComposite.tDest18DLA,
				HeaderComposite.tDest19DLA,HeaderComposite.tDest20DLA,HeaderComposite.tDest21DLA);
		// BODY
		editType3(t3aDLA, t3bDLA, t3cDLA);
		editType7(t7aDLA, t7bDLA, t7cDLA);
		editType13(t13aDLA, t13bDLA);
		editType16(t16aDLA, t16bDLA, t16cDLA, t16dDLA);
		editDof(tDofDLA);
	}

	public static void setCurrentCHG() {
		// HEADER
		editHeader(HeaderComposite.tSendAtCHG,HeaderComposite.tPriorityCHG,HeaderComposite.tFilingTimeCHG,HeaderComposite.tOriginatorCHG,
				HeaderComposite.tOriRefCHG,tFbCHG,HeaderComposite.bBellCHG,HeaderComposite.tDest1CHG,HeaderComposite.tDest2CHG,
				HeaderComposite.tDest3CHG,HeaderComposite.tDest4CHG,HeaderComposite.tDest5CHG,HeaderComposite.tDest6CHG,
				HeaderComposite.tDest7CHG,HeaderComposite.tDest8CHG,HeaderComposite.tDest9CHG,HeaderComposite.tDest10CHG,
				HeaderComposite.tDest11CHG,HeaderComposite.tDest12CHG,HeaderComposite.tDest13CHG,HeaderComposite.tDest14CHG,
				HeaderComposite.tDest15CHG,HeaderComposite.tDest16CHG,HeaderComposite.tDest17CHG,HeaderComposite.tDest18CHG,
				HeaderComposite.tDest19CHG,HeaderComposite.tDest20CHG,HeaderComposite.tDest21CHG);
		// BODY
		editType3(t3aCHG, t3bCHG, t3cCHG);
		editType7(t7aCHG, t7bCHG, t7cCHG);
		editType13(t13aCHG, t13bCHG);
		editType16(t16aCHG, t16bCHG, t16cCHG, t16dCHG);
		editDof(tDofCHG);
		t22CHG.setText(jdbc.get22());
	}

	public static void setCurrentCNL() {
		// HEADER
		editHeader(HeaderComposite.tSendAtCNL,HeaderComposite.tPriorityCNL,HeaderComposite.tFilingTimeCNL,HeaderComposite.tOriginatorCNL,
				HeaderComposite.tOriRefCNL,tFbCNL,HeaderComposite.bBellCNL,HeaderComposite.tDest1CNL,HeaderComposite.tDest2CNL,
				HeaderComposite.tDest3CNL,HeaderComposite.tDest4CNL,HeaderComposite.tDest5CNL,HeaderComposite.tDest6CNL,
				HeaderComposite.tDest7CNL,HeaderComposite.tDest8CNL,HeaderComposite.tDest9CNL,HeaderComposite.tDest10CNL,
				HeaderComposite.tDest11CNL,HeaderComposite.tDest12CNL,HeaderComposite.tDest13CNL,HeaderComposite.tDest14CNL,
				HeaderComposite.tDest15CNL,HeaderComposite.tDest16CNL,HeaderComposite.tDest17CNL,HeaderComposite.tDest18CNL,
				HeaderComposite.tDest19CNL,HeaderComposite.tDest20CNL,HeaderComposite.tDest21CNL);
		// BODY
		editType3(t3aCNL, t3bCNL, t3cCNL);
		editType7(t7aCNL, t7bCNL, t7cCNL);
		editType13(t13aCNL, t13bCNL);
		editType16(t16aCNL, t16bCNL, t16cCNL, t16dCNL);
		editDof(tDofCNL);
	}

	public static void setCurrentDEP() {
		// HEADER
		editHeader(HeaderComposite.tSendAtDEP,HeaderComposite.tPriorityDEP,HeaderComposite.tFilingTimeDEP,HeaderComposite.tOriginatorDEP,
				HeaderComposite.tOriRefDEP,tFbDEP,HeaderComposite.bBellDEP,HeaderComposite.tDest1DEP,HeaderComposite.tDest2DEP,
				HeaderComposite.tDest3DEP,HeaderComposite.tDest4DEP,HeaderComposite.tDest5DEP,HeaderComposite.tDest6DEP,
				HeaderComposite.tDest7DEP,HeaderComposite.tDest8DEP,HeaderComposite.tDest9DEP,HeaderComposite.tDest10DEP,
				HeaderComposite.tDest11DEP,HeaderComposite.tDest12DEP,HeaderComposite.tDest13DEP,HeaderComposite.tDest14DEP,
				HeaderComposite.tDest15DEP,HeaderComposite.tDest16DEP,HeaderComposite.tDest17DEP,HeaderComposite.tDest18DEP,
				HeaderComposite.tDest19DEP,HeaderComposite.tDest20DEP,HeaderComposite.tDest21DEP);
		// BODY
		editType3(t3aDEP, t3bDEP, t3cDEP);
		editType7(t7aDEP, t7bDEP, t7cDEP);
		editType13(t13aDEP, t13bDEP);
		editType16(t16aDEP, t16bDEP, t16cDEP, t16dDEP);
		editDof(tDofDEP);
	}
	
	public static void setCurrentARR() {
		// HEADER
		editHeader(HeaderComposite.tSendAtARR,HeaderComposite.tPriorityARR,HeaderComposite.tFilingTimeARR,HeaderComposite.tOriginatorARR,
				HeaderComposite.tOriRefARR,tFbARR,HeaderComposite.bBellARR,HeaderComposite.tDest1ARR,HeaderComposite.tDest2ARR,
				HeaderComposite.tDest3ARR,HeaderComposite.tDest4ARR,HeaderComposite.tDest5ARR,HeaderComposite.tDest6ARR,
				HeaderComposite.tDest7ARR,HeaderComposite.tDest8ARR,HeaderComposite.tDest9ARR,HeaderComposite.tDest10ARR,
				HeaderComposite.tDest11ARR,HeaderComposite.tDest12ARR,HeaderComposite.tDest13ARR,HeaderComposite.tDest14ARR,
				HeaderComposite.tDest15ARR,HeaderComposite.tDest16ARR,HeaderComposite.tDest17ARR,HeaderComposite.tDest18ARR,
				HeaderComposite.tDest19ARR,HeaderComposite.tDest20ARR,HeaderComposite.tDest21ARR);
		// BODY
		editType3(t3aARR, t3bARR, t3cARR);
		editType7(t7aARR, t7bARR, t7cARR);
		editType13(t13aARR, t13bARR);
		t17aARR.setText(jdbc.get17a());
		t17bARR.setText(jdbc.get17b());
		t17cARR.setText(jdbc.get17c());
	}

	public static void setCurrentCDN() {
		// HEADER
		editHeader(HeaderComposite.tSendAtCDN,HeaderComposite.tPriorityCDN,HeaderComposite.tFilingTimeCDN,HeaderComposite.tOriginatorCDN,
				HeaderComposite.tOriRefCDN,tFbCDN,HeaderComposite.bBellCDN,HeaderComposite.tDest1CDN,HeaderComposite.tDest2CDN,
				HeaderComposite.tDest3CDN,HeaderComposite.tDest4CDN,HeaderComposite.tDest5CDN,HeaderComposite.tDest6CDN,
				HeaderComposite.tDest7CDN,HeaderComposite.tDest8CDN,HeaderComposite.tDest9CDN,HeaderComposite.tDest10CDN,
				HeaderComposite.tDest11CDN,HeaderComposite.tDest12CDN,HeaderComposite.tDest13CDN,HeaderComposite.tDest14CDN,
				HeaderComposite.tDest15CDN,HeaderComposite.tDest16CDN,HeaderComposite.tDest17CDN,HeaderComposite.tDest18CDN,
				HeaderComposite.tDest19CDN,HeaderComposite.tDest20CDN,HeaderComposite.tDest21CDN);
		// BODY
		editType3(t3aCDN, t3bCDN, t3cCDN);
		editType7(t7aCDN, t7bCDN, t7cCDN);
		editType13(t13aCDN, t13bCDN);
		editType16(t16aCDN, t16bCDN, t16cCDN, t16dCDN);
		t22CDN.setText(jdbc.get22());
	}
	
	public static void setCurrentCPL() {
		// HEADER
		editHeader(HeaderComposite.tSendAtCPL,HeaderComposite.tPriorityCPL,HeaderComposite.tFilingTimeCPL,HeaderComposite.tOriginatorCPL,
				HeaderComposite.tOriRefCPL,tFbCPL,HeaderComposite.bBellCPL,HeaderComposite.tDest1CPL,HeaderComposite.tDest2CPL,
				HeaderComposite.tDest3CPL,HeaderComposite.tDest4CPL,HeaderComposite.tDest5CPL,HeaderComposite.tDest6CPL,
				HeaderComposite.tDest7CPL,HeaderComposite.tDest8CPL,HeaderComposite.tDest9CPL,HeaderComposite.tDest10CPL,
				HeaderComposite.tDest11CPL,HeaderComposite.tDest12CPL,HeaderComposite.tDest13CPL,HeaderComposite.tDest14CPL,
				HeaderComposite.tDest15CPL,HeaderComposite.tDest16CPL,HeaderComposite.tDest17CPL,HeaderComposite.tDest18CPL,
				HeaderComposite.tDest19CPL,HeaderComposite.tDest20CPL,HeaderComposite.tDest21CPL);
		// BODY
		editType3(t3aCPL, t3bCPL, t3cCPL);
		editType7(t7aCPL, t7bCPL, t7cCPL);
		editType8(t8aCPL, t8bCPL);
		editType9(t9aCPL, t9bCPL, t9cCPL);
		editType10(t10aCPL, t10bCPL);
		editType13(t13aCPL, t13bCPL);
		editType14(t14aCPL, t14bCPL, t14cCPL, t14dCPL, t14eCPL);
		editType15(t15aCPL, t15bCPL, t15cCPL);
		editType16(t16aCPL, t16bCPL, t16cCPL, t16dCPL);
		editType18(tRegCPL, tDofCPL);
		editTypeBaru(tNavCPL, tComCPL, tDatCPL, tSurCPL, tDepCPL, tDestCPL, tEetCPL, tSelCPL, tTypCPL, tCodeCPL, tDleCPL, tOprCPL, tOrgnCPL, tPerCPL, tAltnCPL, tRaltCPL, tTaltCPL, tRifCPL, tRmkCPL, tPbnCPL, tStsCPL);
	}

	public static void setCurrentEST() {
		// HEADER
		editHeader(HeaderComposite.tSendAtEST,HeaderComposite.tPriorityEST,HeaderComposite.tFilingTimeEST,HeaderComposite.tOriginatorEST,
				HeaderComposite.tOriRefEST,tFbEST,HeaderComposite.bBellEST,HeaderComposite.tDest1EST,HeaderComposite.tDest2EST,
				HeaderComposite.tDest3EST,HeaderComposite.tDest4EST,HeaderComposite.tDest5EST,HeaderComposite.tDest6EST,
				HeaderComposite.tDest7EST,HeaderComposite.tDest8EST,HeaderComposite.tDest9EST,HeaderComposite.tDest10EST,
				HeaderComposite.tDest11EST,HeaderComposite.tDest12EST,HeaderComposite.tDest13EST,HeaderComposite.tDest14EST,
				HeaderComposite.tDest15EST,HeaderComposite.tDest16EST,HeaderComposite.tDest17EST,HeaderComposite.tDest18EST,
				HeaderComposite.tDest19EST,HeaderComposite.tDest20EST,HeaderComposite.tDest21EST);
		// BODY
		editType3(t3aEST, t3bEST, t3cEST);
		editType7(t7aEST, t7bEST, t7cEST);
		editType13(t13aEST, t13bEST);
		editType14(t14aEST, t14bEST, t14cEST, t14dEST, t14eEST);
		editType16(t16aEST, t16bEST, t16cEST, t16dEST);
	}

	public static void setCurrentACP() {
		// HEADER
		editHeader(HeaderComposite.tSendAtACP,HeaderComposite.tPriorityACP,HeaderComposite.tFilingTimeACP,HeaderComposite.tOriginatorACP,
				HeaderComposite.tOriRefACP,tFbACP,HeaderComposite.bBellACP,HeaderComposite.tDest1ACP,HeaderComposite.tDest2ACP,
				HeaderComposite.tDest3ACP,HeaderComposite.tDest4ACP,HeaderComposite.tDest5ACP,HeaderComposite.tDest6ACP,
				HeaderComposite.tDest7ACP,HeaderComposite.tDest8ACP,HeaderComposite.tDest9ACP,HeaderComposite.tDest10ACP,
				HeaderComposite.tDest11ACP,HeaderComposite.tDest12ACP,HeaderComposite.tDest13ACP,HeaderComposite.tDest14ACP,
				HeaderComposite.tDest15ACP,HeaderComposite.tDest16ACP,HeaderComposite.tDest17ACP,HeaderComposite.tDest18ACP,
				HeaderComposite.tDest19ACP,HeaderComposite.tDest20ACP,HeaderComposite.tDest21ACP);
		// BODY
		editType3(t3aACP, t3bACP, t3cACP);
		editType7(t7aACP, t7bACP, t7cACP);
		editType13(t13aACP, t13bACP);
		editType16(t16aACP, t16bACP, t16cACP, t16dACP);
	}

	public static void setCurrentLAM() {
		// HEADER
		editHeader(HeaderComposite.tSendAtLAM,HeaderComposite.tPriorityLAM,HeaderComposite.tFilingTimeLAM,HeaderComposite.tOriginatorLAM,
				HeaderComposite.tOriRefLAM,tFbLAM,HeaderComposite.bBellLAM,HeaderComposite.tDest1LAM,HeaderComposite.tDest2LAM,
				HeaderComposite.tDest3LAM,HeaderComposite.tDest4LAM,HeaderComposite.tDest5LAM,HeaderComposite.tDest6LAM,
				HeaderComposite.tDest7LAM,HeaderComposite.tDest8LAM,HeaderComposite.tDest9LAM,HeaderComposite.tDest10LAM,
				HeaderComposite.tDest11LAM,HeaderComposite.tDest12LAM,HeaderComposite.tDest13LAM,HeaderComposite.tDest14LAM,
				HeaderComposite.tDest15LAM,HeaderComposite.tDest16LAM,HeaderComposite.tDest17LAM,HeaderComposite.tDest18LAM,
				HeaderComposite.tDest19LAM,HeaderComposite.tDest20LAM,HeaderComposite.tDest21LAM);
		// BODY
		editType3(t3aLAM, t3bLAM, t3cLAM);
	}

	public static void setCurrentRQP() {
		// HEADER
		editHeader(HeaderComposite.tSendAtRQP,HeaderComposite.tPriorityRQP,HeaderComposite.tFilingTimeRQP,HeaderComposite.tOriginatorRQP,
				HeaderComposite.tOriRefRQP,tFbRQP,HeaderComposite.bBellRQP,HeaderComposite.tDest1RQP,HeaderComposite.tDest2RQP,
				HeaderComposite.tDest3RQP,HeaderComposite.tDest4RQP,HeaderComposite.tDest5RQP,HeaderComposite.tDest6RQP,
				HeaderComposite.tDest7RQP,HeaderComposite.tDest8RQP,HeaderComposite.tDest9RQP,HeaderComposite.tDest10RQP,
				HeaderComposite.tDest11RQP,HeaderComposite.tDest12RQP,HeaderComposite.tDest13RQP,HeaderComposite.tDest14RQP,
				HeaderComposite.tDest15RQP,HeaderComposite.tDest16RQP,HeaderComposite.tDest17RQP,HeaderComposite.tDest18RQP,
				HeaderComposite.tDest19RQP,HeaderComposite.tDest20RQP,HeaderComposite.tDest21RQP);
		// BODY
		editType3(t3aRQP, t3bRQP, t3cRQP);
		editType7(t7aRQP, t7bRQP, t7cRQP);
		editType13(t13aRQP, t13bRQP);
		editType16(t16aRQP, t16bRQP, t16cRQP, t16dRQP);
		editDof(tDofRQP);
	}

	public static void setCurrentRQS() {
		// HEADER
		editHeader(HeaderComposite.tSendAtRQS,HeaderComposite.tPriorityRQS,HeaderComposite.tFilingTimeRQS,HeaderComposite.tOriginatorRQS,
				HeaderComposite.tOriRefRQS,tFbRQS,HeaderComposite.bBellRQS,HeaderComposite.tDest1RQS,HeaderComposite.tDest2RQS,
				HeaderComposite.tDest3RQS,HeaderComposite.tDest4RQS,HeaderComposite.tDest5RQS,HeaderComposite.tDest6RQS,
				HeaderComposite.tDest7RQS,HeaderComposite.tDest8RQS,HeaderComposite.tDest9RQS,HeaderComposite.tDest10RQS,
				HeaderComposite.tDest11RQS,HeaderComposite.tDest12RQS,HeaderComposite.tDest13RQS,HeaderComposite.tDest14RQS,
				HeaderComposite.tDest15RQS,HeaderComposite.tDest16RQS,HeaderComposite.tDest17RQS,HeaderComposite.tDest18RQS,
				HeaderComposite.tDest19RQS,HeaderComposite.tDest20RQS,HeaderComposite.tDest21RQS);
		// BODY
		editType3(t3aRQS, t3bRQS, t3cRQS);
		editType7(t7aRQS, t7bRQS, t7cRQS);
		editType13(t13aRQS, t13bRQS);
		editType16(t16aRQS, t16bRQS, t16cRQS, t16dRQS);
		editDof(tDofRQS);
	}

	public static void setCurrentSPL() {
		// HEADER
		editHeader(HeaderComposite.tSendAtSPL,HeaderComposite.tPrioritySPL,HeaderComposite.tFilingTimeSPL,HeaderComposite.tOriginatorSPL,
				HeaderComposite.tOriRefSPL,tFbSPL,HeaderComposite.bBellSPL,HeaderComposite.tDest1SPL,HeaderComposite.tDest2SPL,
				HeaderComposite.tDest3SPL,HeaderComposite.tDest4SPL,HeaderComposite.tDest5SPL,HeaderComposite.tDest6SPL,
				HeaderComposite.tDest7SPL,HeaderComposite.tDest8SPL,HeaderComposite.tDest9SPL,HeaderComposite.tDest10SPL,
				HeaderComposite.tDest11SPL,HeaderComposite.tDest12SPL,HeaderComposite.tDest13SPL,HeaderComposite.tDest14SPL,
				HeaderComposite.tDest15SPL,HeaderComposite.tDest16SPL,HeaderComposite.tDest17SPL,HeaderComposite.tDest18SPL,
				HeaderComposite.tDest19SPL,HeaderComposite.tDest20SPL,HeaderComposite.tDest21SPL);
		// BODY
		editType3(t3aSPL, t3bSPL, t3cSPL);
		editType7(t7aSPL, t7bSPL, t7cSPL);
		editType13(t13aSPL, t13bSPL);
		editType16(t16aSPL, t16bSPL, t16cSPL, t16dSPL);
		editType18(tRegSPL, tDofSPL);
		editType19(t19aSPL, t19bSPL, t19cUhfSPL, t19cVhfSPL, t19cEltSPL, t19dPSPL, t19dDSPL, t19dMSPL, t19dJSPL, t19eLSPL, t19eFSPL, t19eUSPL, t19eVSPL, t19NumSPL, t19CapSPL, t19cSPL, t19ColSPL, t19AirSPL, t19RemSPL, t19PilSPL);
		editTypeBaru(tNavSPL, tComSPL, tDatSPL, tSurSPL, tDepSPL, tDestSPL, tEetSPL, tSelSPL, tTypSPL, tCodeSPL, tDleSPL, tOprSPL, tOrgnSPL, tPerSPL, tAltnSPL, tRaltSPL, tTaltSPL, tRifSPL, tRmkSPL, tPbnSPL, tStsSPL);
	}
	
	/**
	 * ----------------------------------------------------------------------------------------------------------------------------	
	 * menghapus isi String masing-masing type, dikelompokkan per type 
	 * ----------------------------------------------------------------------------------------------------------------------------
	 */
	static void clearType3() { type3a=""; type3b=""; type3c=""; }
	static void clearType5() { type5a=""; type5b=""; type5c=""; }
	static void clearType7() { type7a=""; type7b=""; type7c=""; }
	static void clearType8() { type8a=""; type8b=""; }
	static void clearType9() { type9a=""; type9b=""; type9c=""; }
	static void clearType10() { type10a=""; type10b=""; }
	static void clearType13() { type13a=""; type13b=""; }
	static void clearType14() { type14a=""; type14b=""; type14c=""; type14d=""; type14e=""; }
	static void clearType15() { type15a=""; type15b=""; type15c=""; }
	static void clearType16() { type16a=""; type16b=""; type16c=""; type16c2nd=""; }
	static void clearType17() { type17a=""; type17b=""; type17c=""; }
	static void clearType18() { type18a=""; type18b=""; type18=""; }
	static void clearType19() { type19a="";type19b="";type19cUHF="";type19cVHF="";type19cELT="";type19dS="";type19dP="";type19dD="";type19dM="";type19dJ="";type19eJ="";type19eL="";type19eF="";type19eU="";type19eV="";type19f_number="";type19f_capacity="";type19f_colour="";type19g="";type19Remarks="";type19i=""; }
	static void clearType20() { type20=""; }
	static void clearType21() { type21=""; }
	static void clearType22() { type22=""; }

	/**
	 * ----------------------------------------------------------------------------------------------------------------------------	
	 * ___() [ ___ -> msgType: ALR,FPL, dst.]
	 * menghapus semua body template
	 * ----------------------------------------------------------------------------------------------------------------------------
	 */
	public static void clearAFTN() {
		manual_ats="";
	}

	public static void clearALR() {
		clearType3();
		clearType5();
		clearType7();
		clearType8();
		clearType9();
		clearType10();
		clearType13();
		clearType15();
		clearType16();
		clearType18();
		clearType19();
		clearType20();
	}

	public static void clearRCF() {
		clearType3();
		clearType7();
		clearType21();
	}

	public static void clearFPL() {
		clearType3();
		clearType7();
		clearType8();
		clearType9();
		clearType10();
		clearType13();
		clearType15();
		clearType16();
		clearType18();
		clearType19();
		space_res="";
	}

	public static void clearDLA() {
		clearType3();
		clearType7();
		clearType13();
		clearType16();
		clearType18();
	}

	public static void clearCHG() {
		clearType3();
		clearType7();
		clearType13();
		clearType16();
		clearType18();
		clearType22();
	}
	
	public static void clearCNL() {
		clearType3();
		clearType7();
		clearType13();
		clearType16();
		clearType18();
	}

	public static void clearDEP() {
		clearType3();
		clearType7();
		clearType13();
		clearType16();
		clearType18();
	}

	public static void clearARR() {
		clearType3();
		clearType7();
		clearType13();
		clearType17();
	}

	public static void clearCDN() {
		clearType3();
		clearType7();
		clearType13();
		clearType16();
		clearType22();
	}

	public static void clearCPL() {
		clearType3();
		clearType7();
		clearType8();
		clearType9();
		clearType10();
		clearType13();
		clearType14();
		clearType15();
		clearType16();
		clearType18();
	}

	public static void clearEST() {
		clearType3();
		clearType7();
		clearType13();
		clearType14();
		clearType16();
	}

	public static void clearACP() {
		clearType3();
		clearType7();
		clearType13();
		clearType16();
	}

	public static void clearLAM() {
		clearType3();
	}
	
	public static void clearRQP() {
		clearType3();
		clearType7();
		clearType13();
		clearType16();
		clearType18();
	}
	
	public static void clearRQS() {
		clearType3();
		clearType7();
		clearType13();
		clearType16();
		clearType18();
	}

	public static void clearSPL() {
		clearType3();
		clearType7();
		clearType13();
		clearType16();
		clearType18();
		clearType19();
	}
	/**
	 * ----------------------------------------------------------------------------------------------------------------------------	
	 * menghapus isi text masing-masing type, dikelompokkan per type 
	 * ----------------------------------------------------------------------------------------------------------------------------
	 */
	static void discardType3(String type, Text t3a, Text t3b, Text t3c) {
		if (t3a.getText() != null) t3a.setText(type);
		if (t3b.getText() != null) t3b.setText("");
		if (t3c.getText() != null) t3c.setText("");
	}
	
//	static void discardType5(List t5a, Text t5b, Text t5c) {
//		if (t5a.getSelectionCount()>0) {
//		if (t5a.getSelection()[0] != " ") {
//			t5a.setSelection(0);
//			t5a.removeAll();
//			t5a.setItems(Combos.cType5a);
//		}}
//		if (t5b.getText() != null) t5b.setText("");
//		if (t5c.getText() != null) t5c.setText("");
//	}
	
	static void discardType5(Combo c5a, Text t5b, Text t5c) {
		if (c5a.getText() != null) c5a.setItems(Combos.cType5a);
		if (t5b.getText() != null) t5b.setText("");
		if (t5c.getText() != null) t5c.setText("");
	}
	
	static void discardType5(Text t5a, Text t5b, Text t5c) {
		t5a.setText("");
		t5b.setText("");
		t5c.setText("");
	}
	
	static void discardType7(Text t7a, Button t7b, Text t7c) {
		if (t7a.getText() != null) t7a.setText("");
		if (t7b.getSelection()) t7b.setSelection(false);
		if (t7c.getText() != null) t7c.setText("");
	}
	
//	static void discardType8(List t8a, List t8b) {
//		if (t8a.getSelectionCount()>0) {
//		if (t8a.getSelection()[0] != " ") {
//			t8a.setSelection(0);
//			t8a.removeAll();
//			t8a.setItems(Combos.cType8a);
//		}}
//		if (t8b.getSelectionCount()>0) {
//		if (t8b.getSelection()[0] != " ") {
//			t8b.setSelection(0);
//			t8b.removeAll();
//			t8b.setItems(Combos.cType8b);
//		}}
//	}
	
	static void discardType8(Combo c8a, Combo c8b) {
		if (c8a.getText() != null) c8a.setItems(Combos.cType8a);
		if (c8b.getText() != null) c8b.setItems(Combos.cType8b);
	}
	
	static void discardType8(Text t8a, Text t8b) {
		t8a.setText("");
		t8b.setText("");
	}
	
//	static void discardType9(Text t9a, Text t9b, List t9c) {
//		if (t9a.getText() != null) t9a.setText("");
//		if (t9b.getText() != null) t9b.setText("");
//		if (t9c.getSelectionCount()>0) {
//		if (t9c.getSelection()[0] != " ") {
//			t9c.setSelection(0);
//			t9c.removeAll();
//			t9c.setItems(Combos.cType9c);
//		}}
//	}
	
	static void discardType9(Text t9a, Text t9b, Text t9c) {
		t9a.setText("");
		t9b.setText("");
		t9c.setText("");
	}
	
	static void discardType9(Text t9a, Text t9b, Combo c9c) {
		if (t9a.getText() != null) t9a.setText("");
		if (t9b.getText() != null) t9b.setText("");
		if (c9c.getText() != null) c9c.setItems(Combos.cType9c);
	}
	
	static void discardType10(Text t10a, Text t10b) {
		if (t10a.getText() != null) t10a.setText("");
		if (t10b.getText() != null) t10b.setText("");
	}
	
	static void discardType13(Text t13a, Text t13b) {
		if (t13a.getText() != null) t13a.setText("");
		if (t13b.getText() != null) t13b.setText("");
	}
	
	static void discardType14(Text t14a, Text t14b, Text t14c, Text t14d, Text t14e) {
		if (t14a.getText() != null) t14a.setText("");
		if (t14b.getText() != null) t14b.setText("");
		if (t14c.getText() != null) t14c.setText("");
		if (t14d.getText() != null) t14d.setText("");
		if (t14e.getText() != null) t14e.setText("");
	}
	
	static void discardType15(Text t15a, Text t15b, Text t15c) {
		if (t15a.getText() != null) t15a.setText("");
		if (t15b.getText() != null) t15b.setText("");
		if (t15c.getText() != null) t15c.setText("");
	}
	
	static void discardType16(Text t16a, Text t16b, Text t16c, Text t16d) {
		if (t16a.getText() != null) t16a.setText("");
		if (t16b.getText() != null) t16b.setText("");
		if (t16c.getText() != null) t16c.setText("");
		if (t16d.getText() != null) t16d.setText("");
	}
	
	static void discardType19(Text t19a, Text t19b, Text t19cUhf, Text t19cVhf, Text t19cElt, 
			Text t19dP, Text t19dD, Text t19dM, Text t19dJ, Text t19eL, Text t19eF, Text t19eU, Text t19eV, 
			Text t19Num, Text t19Cap, Text t19Col, Text t19Air, Text t19Rem, Text t19Pil , Text t19s, Text t19j, Text t19d, Text t19c, Text t19n) {
		t19s.setText("S");
		t19j.setText("J");
		t19d.setText("D");
		t19c.setText("X");
		t19n.setText("N");
		t19a.setText("");
		t19b.setText("");
		t19cUhf.setText("U");
		t19cVhf.setText("V");
		t19cElt.setText("E");
		t19dP.setText("P");
		t19dD.setText("D");
		t19dM.setText("M");
		t19dJ.setText("J");
		t19eL.setText("L");
		t19eF.setText("F");
		t19eU.setText("U");
		t19eV.setText("V");
		t19Num.setText("");
		t19Cap.setText("");
		t19Col.setText("");
		t19Air.setText("");
		t19Rem.setText("");
		t19Pil.setText("");
		t19Num.setEnabled(true);
		t19Cap.setEnabled(true);
		t19c.setEnabled(true);
		t19Col.setEnabled(true);
		t19Air.setEnabled(true);
		t19Rem.setEnabled(true);
	}

	static void discardType18(Text tNav, Text tCom, Text tDat, Text tSur, Text tDep, Text tDest, Text tDof, Text tReg, 
			Text tEet, Text tSel, Text tTyp, Text tCode, 
			Text tDle, Text tOpr, Text tOrgn, Text tPer, Text tAltn, Text tRalt, Text tTalt, Text tRif, Text tRmk, Text tPbn, Text tSts) {
		if (tNav.getText() != null) tNav.setText("");
		if (tCom.getText() != null) tCom.setText("");
		if (tDat.getText() != null) tDat.setText("");
		if (tSur.getText() != null) tSur.setText("");
		if (tDep.getText() != null) tDep.setText("");
		if (tDest.getText() != null) tDest.setText("");
		discardTypeDof(tDof);
		if (tReg.getText() != null) tReg.setText("");
		if (tEet.getText() != null) tEet.setText("");
		if (tSel.getText() != null) tSel.setText("");
		if (tTyp.getText() != null) tTyp.setText("");
		if (tCode.getText() != null) tCode.setText("");
		if (tDle.getText() != null) tDle.setText("");
		if (tOpr.getText() != null) tOpr.setText("");
		if (tOrgn.getText() != null) tOrgn.setText("");
		if (tPer.getText() != null) tPer.setText("");
		if (tAltn.getText() != null) tAltn.setText("");
		if (tRalt.getText() != null) tRalt.setText("");
		if (tTalt.getText() != null) tTalt.setText("");
		if (tRif.getText() != null) tRif.setText("");
		if (tRmk.getText() != null) tRmk.setText("");
		if (tPbn.getText() != null) tPbn.setText("");
		if (tSts.getText() != null) tSts.setText("");
	}
	
	static void discardTypeDof(Text tDof) {
		if (tDof.getText() != null) tDof.setText("");
	}
	
	/**
	 * ----------------------------------------------------------------------------------------------------------------------------	
	 * ___() [ ___ -> msgType: ALR,FPL, dst.]
	 * menghapus semua body template
	 * ----------------------------------------------------------------------------------------------------------------------------
	 */
	public static void discardAMO() { tFreeAMO.setText(""); tFbAMO.setText(""); }
	public static void discardFREE() { tFreeTEXT.setText(""); tFbTEXT.setText(""); }
	public static void discardAFTN() { tFreeATS.setText(""); }

	public static void discardALR() {
//		inilist5aALR=0;
//		inilist8aALR=0;
//		inilist8bALR=0;
//		inilist9cALR=0;
		discardType3("ALR", t3aALR, t3bALR, t3cALR);
		discardType5(t5aALR, t5bALR, t5cALR);
		discardType7(t7aALR, t7bALR, t7cALR);
		discardType8(t8aALR, t8bALR);
		discardType9(t9aALR, t9bALR, t9cALR);
		discardType10(t10aALR, t10bALR);
		discardType13(t13aALR, t13bALR);
		discardType15(t15aALR, t15bALR, t15cALR);
		discardType16(t16aALR, t16bALR, t16cALR, t16dALR);
		discardType18(tNavALR, tComALR, tDatALR, tSurALR, tDepALR, tDestALR, tDofALR, tRegALR, tEetALR, tSelALR, tTypALR, tCodeALR, tDleALR, tOprALR, tOrgnALR, tPerALR, tAltnALR, tRaltALR, tTaltALR, tRifALR, tRmkALR, tPbnALR, tStsALR);
		discardType19(t19aALR, t19bALR, t19cUhfALR, t19cVhfALR, t19cEltALR, t19dPALR, t19dDALR, t19dMALR, t19dJALR, t19eLALR, t19eFALR, t19eUALR, t19eVALR, t19NumALR, t19CapALR, t19ColALR, t19AirALR, t19RemALR, t19PilALR, t19sALR, t19jALR, t19dALR, t19cALR, t19nALR);
		if (t20ALR.getText() != null) t20ALR.setText("");
	}

	public static void discardRCF() {
		discardType3("RCF", t3aRCF, t3bRCF, t3cRCF);
		discardType7(t7aRCF, t7bRCF, t7cRCF);
		if (t21RCF.getText() != null) t21RCF.setText("");
	}

	public static void discardFPL() {
//		regText.setText("");
		discardType3("FPL", t3aFPL, t3bFPL, t3cFPL);
		discardType7(t7aFPL, t7bFPL, t7cFPL);
		discardType8(t8aFPL, t8bFPL);
		discardType9(t9aFPL, t9bFPL, t9cFPL);
		discardType10(t10aFPL, t10bFPL);
		discardType13(t13aFPL, t13bFPL);
		discardType15(t15aFPL, t15bFPL, t15cFPL);
		discardType16(t16aFPL, t16bFPL, t16cFPL, t16dFPL);
		discardType18(tNavFPL, tComFPL, tDatFPL, tSurFPL, tDepFPL, tDestFPL, tDofFPL, tRegFPL, tEetFPL, tSelFPL, tTypFPL, tCodeFPL, tDleFPL, tOprFPL, tOrgnFPL, tPerFPL, tAltnFPL, tRaltFPL, tTaltFPL, tRifFPL, tRmkFPL, tPbnFPL, tStsFPL);
		discardType19(t19aFPL, t19bFPL, t19cUhfFPL, t19cVhfFPL, t19cEltFPL, t19dPFPL, t19dDFPL, t19dMFPL, t19dJFPL, t19eLFPL, t19eFFPL, t19eUFPL, t19eVFPL, t19NumFPL, t19CapFPL, t19ColFPL, t19AirFPL, t19RemFPL, t19PilFPL, t19sFPL, t19jFPL, t19dFPL, t19cFPL, t19nFPL);
		if (tSpace.getText() != null) tSpace.setText("");
	}

	public static void discardDLA() {
		discardType3("DLA", t3aDLA, t3bDLA, t3cDLA);
		discardType7(t7aDLA, t7bDLA, t7cDLA);
		discardType13(t13aDLA, t13bDLA);
		discardType16(t16aDLA, t16bDLA, t16cDLA, t16dDLA);
		discardTypeDof(tDofDLA);
	}

	public static void discardCHG() {
		discardType3("CHG", t3aCHG, t3bCHG, t3cCHG);
		discardType7(t7aCHG, t7bCHG, t7cCHG);
		discardType13(t13aCHG, t13bCHG);
		discardType16(t16aCHG, t16bCHG, t16cCHG, t16dCHG);
		discardTypeDof(tDofCHG);
		if (t22CHG.getText() != null) t22CHG.setText("");
	}
	
	public static void discardCNL() {
		discardType3("CNL", t3aCNL, t3bCNL, t3cCNL);
		discardType7(t7aCNL, t7bCNL, t7cCNL);
		discardType13(t13aCNL, t13bCNL);
		discardType16(t16aCNL, t16bCNL, t16cCNL, t16dCNL);
		discardTypeDof(tDofCNL);
	}

	public static void discardDEP() {
		discardType3("DEP", t3aDEP, t3bDEP, t3cDEP);
		discardType7(t7aDEP, t7bDEP, t7cDEP);
		discardType13(t13aDEP, t13bDEP);
		discardType16(t16aDEP, t16bDEP, t16cDEP, t16dDEP);
		discardTypeDof(tDofDEP);
	}

	public static void discardARR() {
		discardType3("ARR", t3aARR, t3bARR, t3cARR);
		discardType7(t7aARR, t7bARR, t7cARR);
		discardType13(t13aARR, t13bARR);
		if (t17aARR.getText() != null) t17aARR.setText("");
		if (t17bARR.getText() != null) t17bARR.setText("");
		if (t17cARR.getText() != null) t17cARR.setText("");
	}

	public static void discardCDN() {
		discardType3("CDN", t3aCDN, t3bCDN, t3cCDN);
		discardType7(t7aCDN, t7bCDN, t7cCDN);
		discardType13(t13aCDN, t13bCDN);
		discardType16(t16aCDN, t16bCDN, t16cCDN, t16dCDN);
		if (t22CDN.getText() != null) t22CDN.setText("");
	}

	public static void discardCPL() {
		discardType3("CPL", t3aCPL, t3bCPL, t3cCPL);
		discardType7(t7aCPL, t7bCPL, t7cCPL);
		discardType8(t8aCPL, t8bCPL);
		discardType9(t9aCPL, t9bCPL, t9cCPL);
		discardType10(t10aCPL, t10bCPL);
		discardType13(t13aCPL, t13bCPL);
		discardType14(t14aCPL, t14bCPL, t14cCPL, t14dCPL, t14eCPL);
		discardType15(t15aCPL, t15bCPL, t15cCPL);
		discardType16(t16aCPL, t16bCPL, t16cCPL, t16dCPL);
		discardType18(tNavCPL, tComCPL, tDatCPL, tSurCPL, tDepCPL, tDestCPL, tDofCPL, tRegCPL, tEetCPL, tSelCPL, tTypCPL, tCodeCPL, tDleCPL, tOprCPL, tOrgnCPL, tPerCPL, tAltnCPL, tRaltCPL, tTaltCPL, tRifCPL, tRmkCPL, tPbnCPL, tStsCPL);
	}

	public static void discardEST() {
		discardType3("EST", t3aEST, t3bEST, t3cEST);
		discardType7(t7aEST, t7bEST, t7cEST);
		discardType13(t13aEST, t13bEST);
		discardType14(t14aEST, t14bEST, t14cEST, t14dEST, t14eEST);
		discardType16(t16aEST, t16bEST, t16cEST, t16dEST);
	}

	public static void discardACP() {
		discardType3("ACP", t3aACP, t3bACP, t3cACP);
		discardType7(t7aACP, t7bACP, t7cACP);
		discardType13(t13aACP, t13bACP);
		discardType16(t16aACP, t16bACP, t16cACP, t16dACP);
	}

	public static void discardLAM() {
		discardType3("LAM", t3aLAM, t3bLAM, t3cLAM);
	}
	
	public static void discardRQP() {
		discardType3("RQP", t3aRQP, t3bRQP, t3cRQP);
		discardType7(t7aRQP, t7bRQP, t7cRQP);
		discardType13(t13aRQP, t13bRQP);
		discardType16(t16aRQP, t16bRQP, t16cRQP, t16dRQP);
		discardTypeDof(tDofRQP);
	}
	
	public static void discardRQS() {
		discardType3("RQS", t3aRQS, t3bRQS, t3cRQS);
		discardType7(t7aRQS, t7bRQS, t7cRQS);
		discardType13(t13aRQS, t13bRQS);
		discardType16(t16aRQS, t16bRQS, t16cRQS, t16dRQS);
		discardTypeDof(tDofRQS);
	}

	public static void discardSPL() {
		discardType3("SPL", t3aSPL, t3bSPL, t3cSPL);
		discardType7(t7aSPL, t7bSPL, t7cSPL);
		discardType13(t13aSPL, t13bSPL);
		discardType16(t16aSPL, t16bSPL, t16cSPL, t16dSPL);
		discardType18(tNavSPL, tComSPL, tDatSPL, tSurSPL, tDepSPL, tDestSPL, tDofSPL, tRegSPL, tEetSPL, tSelSPL, tTypSPL, tCodeSPL, tDleSPL, tOprSPL, tOrgnSPL, tPerSPL, tAltnSPL, tRaltSPL, tTaltSPL, tRifSPL, tRmkSPL, tPbnSPL, tStsSPL);
		discardType19(t19aSPL, t19bSPL, t19cUhfSPL, t19cVhfSPL, t19cEltSPL, t19dPSPL, t19dDSPL, t19dMSPL, t19dJSPL, t19eLSPL, t19eFSPL, t19eUSPL, t19eVSPL, t19NumSPL, t19CapSPL, t19ColSPL, t19AirSPL, t19RemSPL, t19PilSPL, t19sSPL, t19jSPL, t19dSPL, t19cSPL, t19nSPL);
	}
	
	/**
	 * ----------------------------------------------------------------------------------------------------------------------------	
	 * mengambil isi text yang diisikan sesuai template masing-masing
	 * ----------------------------------------------------------------------------------------------------------------------------
	 */
	public static void getAMOValue() {
		clearAFTN();
		filed_by = tFbAMO.getText(); if (filed_by==null) filed_by="";
		manual_ats = tFreeAMO.getText(); if (manual_ats == null) manual_ats="";
		if (manual_ats.contains("'")) manual_ats = manual_ats.replace("'", "`");
	}
	
	public static void getFREEValue() {
		clearAFTN();
		filed_by = tFbTEXT.getText(); if (filed_by==null) filed_by="";
		manual_ats = tFreeTEXT.getText(); if (manual_ats == null) manual_ats="";
		if (manual_ats.contains("'")) manual_ats = manual_ats.replace("'", "`");
	}
	
	public static void getAFTNValue() {
		clearAFTN();
		filed_by = tFbAFTN.getText(); if (filed_by==null) filed_by="";
		manual_ats = tFreeATS.getText(); if (manual_ats == null) manual_ats="";
		if (manual_ats.contains("'")) manual_ats = manual_ats.replace("'", "`");
	}

	public static void getALRValue() {
		clearALR();
		filed_by = tFbALR.getText(); if (filed_by==null) filed_by="";
		String t7bALRValue="",t19cUhfALRValue="",t19cVhfALRValue="",t19cEltALRValue="",t19dPALRValue="",t19dDALRValue="",t19dMALRValue="",
		t19dJALRValue="",t19eLALRValue="",t19eFALRValue="",t19eUALRValue="",t19eVALRValue="";
		
		if (t7bALR.getSelection()) { t7bALRValue = t7bALR.toString(); t7bALRValue = "A"; } else t7bALRValue="";
		if (t19cUhfALR.getText().compareTo("U")==0) { t19cUhfALRValue = t19cUhfALR.toString(); t19cUhfALRValue = "R/U"; } else t19cUhfALRValue="";
		if (t19cVhfALR.getText().compareTo("V")==0) { t19cVhfALRValue = t19cVhfALR.toString(); t19cVhfALRValue = "R/V"; } else t19cVhfALRValue="";
		if (t19cEltALR.getText().compareTo("E")==0) { t19cEltALRValue = t19cEltALR.toString(); t19cEltALRValue = "R/E"; } else t19cEltALRValue="";
		if (t19dPALR.getText().compareTo("P")==0) { t19dPALRValue = t19dPALR.toString(); t19dPALRValue = "S/P"; } else t19dPALRValue="";
		if (t19dDALR.getText().compareTo("D")==0) { t19dDALRValue = t19dDALR.toString(); t19dDALRValue = "S/D"; } else t19dDALRValue="";
		if (t19dMALR.getText().compareTo("M")==0) { t19dMALRValue = t19dMALR.toString(); t19dMALRValue = "S/M"; } else t19dMALRValue="";
		if (t19dJALR.getText().compareTo("J")==0) { t19dJALRValue = t19dJALR.toString(); t19dJALRValue = "S/J"; } else t19dJALRValue="";
		if (t19eLALR.getText().compareTo("L")==0) { t19eLALRValue = t19eLALR.toString(); t19eLALRValue = "J/L"; } else t19eLALRValue=""; 
		if (t19eFALR.getText().compareTo("F")==0) { t19eFALRValue = t19eFALR.toString(); t19eFALRValue = "J/F"; } else t19eFALRValue="";
		if (t19eUALR.getText().compareTo("U")==0) { t19eUALRValue = t19eUALR.toString(); t19eUALRValue = "J/U"; } else t19eUALRValue="";
		if (t19eVALR.getText().compareTo("V")==0) { t19eVALRValue = t19eVALR.toString(); t19eVALRValue = "J/V"; } else t19eVALRValue="";
		if (t19cALR.getText().compareTo("C")==0) { type19f_cover = t19cALR.toString(); type19f_cover = "C"; } else type19f_cover="";
		
		type3a = "ALR"; if (!type3a.equals("ALR")) type3a = "ALR";
		type3b = t3bALR.getText(); if (type3b == null) type3b="";
		type3c = t3cALR.getText(); if (type3c == null) type3c="";
//		type5a="";
//		if (list5aALR.getSelectionCount()>0) {
//		type5a = list5aALR.getSelection()[0]; if (type5a == null) type5a="";}
		type5a = t5aALR.getText(); if (type5a == null) type5a="";
		type5b = t5bALR.getText(); if (type5b == null) type5b="";
		type5c = t5cALR.getText(); if (type5c == null) type5c=""; 
		type7a = t7aALR.getText(); if (type7a == null) type7a="";
		type7b = t7bALRValue; if (type7b == null) type7b="";
		type7c = t7cALR.getText(); if (type7c == null) type7c="";
//		type8a="";
//		if (list8aALR.getSelectionCount()>0) {
//		type8a = list8aALR.getSelection()[0]; if (type8a == null) type8a="";}
//		type8b="";
//		if (list8bALR.getSelectionCount()>0) {
//		type8b = list8bALR.getSelection()[0]; if (type8b == null) type8b="";}
		type8a = t8aALR.getText(); if (type8a == null) type8a="";
		type8b = t8bALR.getText(); if (type8b == null) type8b="";
		type9a = t9aALR.getText(); if (type9a == null) type9a="";
		type9b = t9bALR.getText(); if (type9b == null) type9b="";
//		type9c="";
//		if (list9cALR.getSelectionCount()>0) {
//		type9c = list9cALR.getSelection()[0]; if (type9c == null) type9c="";}
		type9c = t9cALR.getText(); if (type9c == null) type9c="";
		type10a = t10aALR.getText(); if (type10a == null) type10a="";
		type10bBr = t10bALR.getText(); if (type10bBr == null) type10bBr="";
		type13a = t13aALR.getText(); if (type13a == null) type13a="";
		type13b = t13bALR.getText(); if (type13b == null) type13b="";
		type15a = t15aALR.getText(); if (type15a == null) type15a=""; 
		type15b = t15bALR.getText(); if (type15b == null) type15b="";
		type15c = t15cALR.getText(); if (type15c == null) type15c=""; 
		type16a = t16aALR.getText(); if (type16a == null) type16a="";
		type16b = t16bALR.getText(); if (type16b == null) type16b="";
		type16c = t16cALR.getText(); if (type16c == null) type16c="";
		type16c2nd = t16dALR.getText(); if (type16c2nd == null) type16c2nd="";
		type18tNav = tNavALR.getText(); if (type18tNav == null) type18tNav="";
		type18tCom = tComALR.getText(); if (type18tCom == null) type18tCom="";
		type18tDat = tDatALR.getText(); if (type18tDat == null) type18tDat="";
		type18tSur = tSurALR.getText(); if (type18tSur == null) type18tSur="";
		type18tDep = tDepALR.getText(); if (type18tDep == null) type18tDep="";
		type18tDest = tDestALR.getText(); if (type18tDest == null) type18tDest="";
		type18tDof = tDofALR.getText(); if (type18tDof == null) type18tDof="";
		type18tReg = tRegALR.getText(); if (type18tReg == null) type18tReg="";
		type18tEet = tEetALR.getText(); if (type18tEet == null) type18tEet="";
		type18tSel = tSelALR.getText(); if (type18tSel == null) type18tSel="";
		type18tTyp = tTypALR.getText(); if (type18tTyp == null) type18tTyp="";
		type18tCode = tCodeALR.getText(); if (type18tCode == null) type18tCode="";
		type18tDle = tDleALR.getText(); if (type18tDle == null) type18tDle="";
		type18tOpr = tOprALR.getText(); if (type18tOpr == null) type18tOpr="";
		type18tOrgn = tOrgnALR.getText(); if (type18tOrgn == null) type18tOrgn="";
		type18tPer = tPerALR.getText(); if (type18tPer == null) type18tPer="";
		type18tAltn = tAltnALR.getText(); if (type18tAltn == null) type18tAltn="";
		type18tRalt = tRaltALR.getText(); if (type18tRalt == null) type18tRalt="";
		type18tTalt = tTaltALR.getText(); if (type18tTalt == null) type18tTalt="";
		type18tRif = tRifALR.getText(); if (type18tRif == null) type18tRif="";
		type18tRmk = tRmkALR.getText(); if (type18tRmk == null) type18tRmk="";
		type18tPbn = tPbnALR.getText(); if (type18tPbn == null) type18tPbn="";
		type18tSts = tStsALR.getText(); if (type18tSts == null) type18tSts="";
		type19a = t19aALR.getText(); if (type19a == null) type19a="";
		type19b = t19bALR.getText(); if (type19b == null) type19b="";
		type19cUHF = t19cUhfALRValue; if (type19cUHF == null) type19cUHF="";
		type19cVHF = t19cVhfALRValue; if (type19cVHF == null) type19cVHF="";
		type19cELT = t19cEltALRValue; if (type19cELT == null) type19cELT="";
		type19dP = t19dPALRValue; if (type19dP == null) type19dP="";
		type19dD = t19dDALRValue; if (type19dD == null) type19dD="";
		type19dM = t19dMALRValue; if (type19dM == null) type19dM="";
		type19dJ = t19dJALRValue; if (type19dJ == null) type19dJ="";
		type19eL = t19eLALRValue; if (type19eL == null) type19eL="";
		type19eF = t19eFALRValue; if (type19eF == null) type19eF="";
		type19eU = t19eUALRValue; if (type19eU == null) type19eU="";
		type19eV = t19eVALRValue; if (type19eV == null) type19eV="";
		type19f_number = t19NumALR.getText(); if (type19f_number == null) type19f_number="";
		type19f_capacity = t19CapALR.getText(); if (type19f_capacity == null) type19f_capacity="";
		type19f_colour = t19ColALR.getText(); if (type19f_colour == null) type19f_colour="";
		type19g = t19AirALR.getText(); if (type19g == null) type19g="";
		type19Remarks = t19RemALR.getText(); if (type19Remarks == null) type19Remarks="";
		type19i = t19PilALR.getText(); if (type19i == null) type19i="";
		type20 = t20ALR.getText(); if (type20 == null) type20="";
		
		// proteksi kutip untuk field freetext
		if (type5c.contains("'")) type5c = type5c.replaceAll("'", "`");
		if (type15c.contains("'")) type15c = type15c.replaceAll("'", "`");
		if (type20.contains("'")) type20 = type20.replaceAll("'", "`");
		if (type18tSts.contains("'")) type18tSts = type18tSts.replaceAll("'", "`");
		if (type18tNav.contains("'")) type18tNav = type18tNav.replaceAll("'", "`");
		if (type18tCom.contains("'")) type18tCom = type18tCom.replaceAll("'", "`");
		if (type18tDat.contains("'")) type18tDat = type18tDat.replaceAll("'", "`");
		if (type18tSur.contains("'")) type18tSur = type18tSur.replaceAll("'", "`");
		if (type18tDep.contains("'")) type18tDep = type18tDep.replaceAll("'", "`");
		if (type18tDest.contains("'")) type18tDest = type18tDest.replaceAll("'", "`");
		if (type18tEet.contains("'")) type18tEet = type18tEet.replaceAll("'", "`");
		if (type18tTyp.contains("'")) type18tTyp = type18tTyp.replaceAll("'", "`");
		if (type18tDle.contains("'")) type18tDle = type18tDle.replaceAll("'", "`");
		if (type18tOpr.contains("'")) type18tOpr = type18tOpr.replaceAll("'", "`");
		if (type18tOrgn.contains("'")) type18tOrgn = type18tOrgn.replaceAll("'", "`");
		if (type18tAltn.contains("'")) type18tAltn = type18tAltn.replaceAll("'", "`");
		if (type18tRalt.contains("'")) type18tRalt = type18tRalt.replaceAll("'", "`");
		if (type18tTalt.contains("'")) type18tTalt = type18tTalt.replaceAll("'", "`");
		if (type18tRif.contains("'")) type18tRif = type18tRif.replaceAll("'", "`");
		if (type18tRmk.contains("'")) type18tRmk = type18tRmk.replaceAll("'", "`");
		// proteksi '/' untuk field freetext type18baru
		if (type18tNav.contains("/")) type18tNav = type18tNav.replaceAll("/", "~");
		if (type18tCom.contains("/")) type18tCom = type18tCom.replaceAll("/", "~");
		if (type18tDat.contains("/")) type18tDat = type18tDat.replaceAll("/", "~");
		if (type18tSur.contains("/")) type18tSur = type18tSur.replaceAll("/", "~");
		if (type18tDep.contains("/")) type18tDep = type18tDep.replaceAll("/", "~");
		if (type18tDest.contains("/")) type18tDest = type18tDest.replaceAll("/", "~");
		if (type18tTyp.contains("/")) type18tTyp = type18tTyp.replaceAll("/", "~");
		if (type18tOpr.contains("/")) type18tOpr = type18tOpr.replaceAll("/", "~");
		if (type18tOrgn.contains("/")) type18tOrgn = type18tOrgn.replaceAll("/", "~");
		if (type18tAltn.contains("/")) type18tAltn = type18tAltn.replaceAll("/", "~");
		if (type18tRalt.contains("/")) type18tRalt = type18tRalt.replaceAll("/", "~");
		if (type18tTalt.contains("/")) type18tTalt = type18tTalt.replaceAll("/", "~");
		if (type18tRif.contains("/")) type18tRif = type18tRif.replaceAll("/", "~");
		if (type18tRmk.contains("/")) type18tRmk = type18tRmk.replaceAll("/", "~");
		getType18(type18tNav, type18tCom, type18tDat, type18tSur, type18tDep, type18tDest, type18tDof, type18tReg, type18tEet, type18tSel, type18tTyp, type18tCode, type18tDle, type18tOpr, type18tOrgn, type18tPer, type18tAltn, type18tRalt, type18tTalt, type18tRif, type18tRmk, type18tPbn, type18tSts);
	}
	

	public static void getRCFValue() {
		clearRCF();
		filed_by = tFbRCF.getText(); if (filed_by==null) filed_by="";
		String t7bRCFValue="";
		if (t7bRCF.getSelection()) { t7bRCFValue = t7bRCF.toString(); t7bRCFValue = "A"; } else t7bRCFValue="";
		type3a = "RCF"; if (!type3a.equals("RCF")) type3a = "RCF";
		type3b = t3bRCF.getText(); if (type3b == null) type3b="";
		type3c = t3cRCF.getText(); if (type3c == null) type3c="";
		type7a = t7aRCF.getText(); if (type7a == null) type7a="";
		type7b = t7bRCFValue; if (type7b == null) type7b="";
		type7c = t7cRCF.getText(); if (type7c == null) type7c="";
		type21 = t21RCF.getText(); if (type21 == null) type21="";
		// proteksi kutip untuk field freetext
		if (type21.contains("'")) type21 = type21.replaceAll("'", "`");
	}
	
	public static void getFPLValue() {
		clearFPL();
		filed_by = tFbFPL.getText(); if (filed_by==null) filed_by="";
		String t7bFPLValue="",t19cUhfFPLValue="",t19cVhfFPLValue="",t19cEltFPLValue="",t19dPFPLValue="",t19dDFPLValue="",t19dMFPLValue="",
		t19dJFPLValue="",t19eLFPLValue="",t19eFFPLValue="",t19eUFPLValue="",t19eVFPLValue="";
		
		if (t7bFPL.getSelection()) { t7bFPLValue = t7bFPL.toString(); t7bFPLValue = "A"; } else t7bFPLValue="";
		if (t19cUhfFPL.getText().compareTo("U")==0) { t19cUhfFPLValue = t19cUhfFPL.toString(); t19cUhfFPLValue = "R/U"; } else t19cUhfFPLValue="";
		if (t19cVhfFPL.getText().compareTo("V")==0) { t19cVhfFPLValue = t19cVhfFPL.toString(); t19cVhfFPLValue = "R/V"; } else t19cVhfFPLValue="";
		if (t19cEltFPL.getText().compareTo("E")==0) { t19cEltFPLValue = t19cEltFPL.toString(); t19cEltFPLValue = "R/E"; } else t19cEltFPLValue="";
		if (t19dPFPL.getText().compareTo("P")==0) { t19dPFPLValue = t19dPFPL.toString(); t19dPFPLValue = "S/P"; } else t19dPFPLValue="";
		if (t19dDFPL.getText().compareTo("D")==0) { t19dDFPLValue = t19dDFPL.toString(); t19dDFPLValue = "S/D"; } else t19dDFPLValue="";
		if (t19dMFPL.getText().compareTo("M")==0) { t19dMFPLValue = t19dMFPL.toString(); t19dMFPLValue = "S/M"; } else t19dMFPLValue="";
		if (t19dJFPL.getText().compareTo("J")==0) { t19dJFPLValue = t19dJFPL.toString(); t19dJFPLValue = "S/J"; } else t19dJFPLValue="";
		if (t19eLFPL.getText().compareTo("L")==0) { t19eLFPLValue = t19eLFPL.toString(); t19eLFPLValue = "J/L"; } else t19eLFPLValue=""; 
		if (t19eFFPL.getText().compareTo("F")==0) { t19eFFPLValue = t19eFFPL.toString(); t19eFFPLValue = "J/F"; } else t19eFFPLValue="";
		if (t19eUFPL.getText().compareTo("U")==0) { t19eUFPLValue = t19eUFPL.toString(); t19eUFPLValue = "J/U"; } else t19eUFPLValue="";
		if (t19eVFPL.getText().compareTo("V")==0) { t19eVFPLValue = t19eVFPL.toString(); t19eVFPLValue = "J/V"; } else t19eVFPLValue="";
		if (t19cFPL.getText().compareTo("C")==0) { type19f_cover = t19cFPL.toString(); type19f_cover = "C"; } else type19f_cover="";

		type3a = "FPL"; if (!type3a.equals("FPL")) type3a = "FPL";
		type3b = t3bFPL.getText(); if (type3b == null) type3b="";
		type3c = t3cFPL.getText(); if (type3c == null) type3c="";
		type7a = t7aFPL.getText(); if (type7a == null) type7a="";
		type7b = t7bFPLValue; if (type7b == null) type7b="";
		type7c = t7cFPL.getText(); if (type7c == null) type7c="";
		type8a = t8aFPL.getText(); if (type8a == null) type8a="";
		type8b = t8bFPL.getText(); if (type8b == null) type8b="";
		type9a = t9aFPL.getText(); if (type9a == null) type9a="";
		type9b = t9bFPL.getText(); if (type9b == null) type9b="";
		type9c = t9cFPL.getText(); if (type9c == null) type9c="";
		type10a = t10aFPL.getText(); if (type10a == null) type10a="";
		type10bBr = t10bFPL.getText(); if (type10bBr == null) type10bBr="";
		type13a = t13aFPL.getText(); if (type13a == null) type13a="";
		type13b = t13bFPL.getText(); if (type13b == null) type13b="";
		type15a = t15aFPL.getText(); if (type15a == null) type15a=""; 
		type15b = t15bFPL.getText(); if (type15b == null) type15b="";
		type15c = t15cFPL.getText(); if (type15c == null) type15c="";
		type16a = t16aFPL.getText(); if (type16a == null) type16a="";
		type16b = t16bFPL.getText(); if (type16b == null) type16b="";
		type16c = t16cFPL.getText(); if (type16c == null) type16c="";
		type16c2nd = t16dFPL.getText(); if (type16c2nd == null) type16c2nd="";	
		type18tNav = tNavFPL.getText(); if (type18tNav == null) type18tNav="";
		type18tCom = tComFPL.getText(); if (type18tCom == null) type18tCom="";
		type18tDat = tDatFPL.getText(); if (type18tDat == null) type18tDat="";
		type18tSur = tSurFPL.getText(); if (type18tSur == null) type18tSur="";
		type18tDep = tDepFPL.getText(); if (type18tDep == null) type18tDep="";
		type18tDest = tDestFPL.getText(); if (type18tDest == null) type18tDest="";
		type18tDof = tDofFPL.getText(); if (type18tDof == null) type18tDof="";
		type18tReg = tRegFPL.getText(); if (type18tReg == null) type18tReg="";
		type18tEet = tEetFPL.getText(); if (type18tEet == null) type18tEet="";
		type18tSel = tSelFPL.getText(); if (type18tSel == null) type18tSel="";
		type18tTyp = tTypFPL.getText(); if (type18tTyp == null) type18tTyp="";
		type18tCode = tCodeFPL.getText(); if (type18tCode == null) type18tCode="";
		type18tDle = tDleFPL.getText(); if (type18tDle == null) type18tDle="";
		type18tOpr = tOprFPL.getText(); if (type18tOpr == null) type18tOpr="";
		type18tOrgn = tOrgnFPL.getText(); if (type18tOrgn == null) type18tOrgn="";
		type18tPer = tPerFPL.getText(); if (type18tPer == null) type18tPer="";
		type18tAltn = tAltnFPL.getText(); if (type18tAltn == null) type18tAltn="";
		type18tRalt = tRaltFPL.getText(); if (type18tRalt == null) type18tRalt="";
		type18tTalt = tTaltFPL.getText(); if (type18tTalt == null) type18tTalt="";
		type18tRif = tRifFPL.getText(); if (type18tRif == null) type18tRif="";
		type18tRmk = tRmkFPL.getText(); if (type18tRmk == null) type18tRmk="";
		type18tPbn = tPbnFPL.getText(); if (type18tPbn == null) type18tPbn="";
		type18tSts = tStsFPL.getText(); if (type18tSts == null) type18tSts="";
		type19a = t19aFPL.getText(); if (type19a == null) type19a="";
		type19b = t19bFPL.getText(); if (type19b == null) type19b="";
		type19cUHF = t19cUhfFPLValue; if (type19cUHF == null) type19cUHF="";
		type19cVHF = t19cVhfFPLValue; if (type19cVHF == null) type19cVHF="";
		type19cELT = t19cEltFPLValue; if (type19cELT == null) type19cELT="";
		type19dP = t19dPFPLValue; if (type19dP == null) type19dP="";
		type19dD = t19dDFPLValue; if (type19dD == null) type19dD="";
		type19dM = t19dMFPLValue; if (type19dM == null) type19dM="";
		type19dJ = t19dJFPLValue; if (type19dJ == null) type19dJ="";
		type19eL = t19eLFPLValue; if (type19eL == null) type19eL="";
		type19eF = t19eFFPLValue; if (type19eF == null) type19eF="";
		type19eU = t19eUFPLValue; if (type19eU == null) type19eU="";
		type19eV = t19eVFPLValue; if (type19eV == null) type19eV="";
		type19f_number = t19NumFPL.getText(); if (type19f_number == null) type19f_number="";
		type19f_capacity = t19CapFPL.getText(); if (type19f_capacity == null) type19f_capacity="";
		type19f_colour = t19ColFPL.getText(); if (type19f_colour == null) type19f_colour="";
		type19g = t19AirFPL.getText(); if (type19g == null) type19g="";
		type19Remarks = t19RemFPL.getText(); if (type19Remarks == null) type19Remarks="";
		type19i = t19PilFPL.getText(); if (type19i == null) type19i="";
		space_res = tSpace.getText(); if (space_res == null) space_res="";

		// proteksi kutip untuk field freetext
		if (type15c.contains("'")) type15c = type15c.replaceAll("'", "`");
		if (space_res.contains("'")) space_res = space_res.replaceAll("'", "`");
		if (type18tSts.contains("'")) type18tSts = type18tSts.replaceAll("'", "`");
		if (type18tNav.contains("'")) type18tNav = type18tNav.replaceAll("'", "`");
		if (type18tCom.contains("'")) type18tCom = type18tCom.replaceAll("'", "`");
		if (type18tDat.contains("'")) type18tDat = type18tDat.replaceAll("'", "`");
		if (type18tSur.contains("'")) type18tSur = type18tSur.replaceAll("'", "`");
		if (type18tDep.contains("'")) type18tDep = type18tDep.replaceAll("'", "`");
		if (type18tDest.contains("'")) type18tDest = type18tDest.replaceAll("'", "`");
		if (type18tEet.contains("'")) type18tEet = type18tEet.replaceAll("'", "`");
		if (type18tTyp.contains("'")) type18tTyp = type18tTyp.replaceAll("'", "`");
		if (type18tDle.contains("'")) type18tDle = type18tDle.replaceAll("'", "`");
		if (type18tOpr.contains("'")) type18tOpr = type18tOpr.replaceAll("'", "`");
		if (type18tOrgn.contains("'")) type18tOrgn = type18tOrgn.replaceAll("'", "`");
		if (type18tAltn.contains("'")) type18tAltn = type18tAltn.replaceAll("'", "`");
		if (type18tRalt.contains("'")) type18tRalt = type18tRalt.replaceAll("'", "`");
		if (type18tTalt.contains("'")) type18tTalt = type18tTalt.replaceAll("'", "`");
		if (type18tRif.contains("'")) type18tRif = type18tRif.replaceAll("'", "`");
		if (type18tRmk.contains("'")) type18tRmk = type18tRmk.replaceAll("'", "`");
		// proteksi '/' untuk field freetext type18baru
		if (type18tNav.contains("/")) type18tNav = type18tNav.replaceAll("/", "~");
		if (type18tCom.contains("/")) type18tCom = type18tCom.replaceAll("/", "~");
		if (type18tDat.contains("/")) type18tDat = type18tDat.replaceAll("/", "~");
		if (type18tSur.contains("/")) type18tSur = type18tSur.replaceAll("/", "~");
		if (type18tDep.contains("/")) type18tDep = type18tDep.replaceAll("/", "~");
		if (type18tDest.contains("/")) type18tDest = type18tDest.replaceAll("/", "~");
		if (type18tTyp.contains("/")) type18tTyp = type18tTyp.replaceAll("/", "~");
		if (type18tOpr.contains("/")) type18tOpr = type18tOpr.replaceAll("/", "~");
		if (type18tOrgn.contains("/")) type18tOrgn = type18tOrgn.replaceAll("/", "~");
		if (type18tAltn.contains("/")) type18tAltn = type18tAltn.replaceAll("/", "~");
		if (type18tRalt.contains("/")) type18tRalt = type18tRalt.replaceAll("/", "~");
		if (type18tTalt.contains("/")) type18tTalt = type18tTalt.replaceAll("/", "~");
		if (type18tRif.contains("/")) type18tRif = type18tRif.replaceAll("/", "~");
		if (type18tRmk.contains("/")) type18tRmk = type18tRmk.replaceAll("/", "~");
		getType18(type18tNav, type18tCom, type18tDat, type18tSur, type18tDep, type18tDest, type18tDof, type18tReg, type18tEet, type18tSel, type18tTyp, type18tCode, type18tDle, type18tOpr, type18tOrgn, type18tPer, type18tAltn, type18tRalt, type18tTalt, type18tRif, type18tRmk, type18tPbn, type18tSts);
	}
	
	static void getType18(String type18tNav, String type18tCom, String type18tDat, String type18tSur, String type18tDep, String type18tDest, String type18tDof, String type18tReg, String type18tEet, String 
			type18tSel, String type18tTyp, String type18tCode, String type18tDle, String type18tOpr, String type18tOrgn, String type18tPer, String type18tAltn, String type18tRalt, String 
			type18tTalt, String type18tRif, String type18tRmk, String type18tPbn, String type18tSts) {
		
    	String sSts = type18tSts; 
    	String sPbn = type18tPbn; 
    	String sNav = type18tNav; 
    	String sCom = type18tCom; 
		String sDat = type18tDat; 
		String sSur = type18tSur; 
		String sDep = type18tDep; 
		String sDest = type18tDest; 
		String sDof = type18tDof; 
		String sReg = type18tReg; 
		String sEet = type18tEet; 
		String sSel = type18tSel; 
		String sTyp = type18tTyp; 
		String sCode = type18tCode; 
		String sDle = type18tDle; 
		String sOpr = type18tOpr; 
		String sOrgn = type18tOrgn; 
		String sPer = type18tPer; 
		String sAltn = type18tAltn; 
		String sRalt = type18tRalt; 
		String sTalt = type18tTalt; 
		String sRif = type18tRif; 
		String sRmk = type18tRmk; 
		//---------------------------------
		if (sSts.startsWith(" ")) sSts=sSts.replaceFirst("\\s+", "");
		if (sPbn.startsWith(" ")) sPbn=sPbn.replaceFirst("\\s+", "");
		if (sNav.startsWith(" ")) sNav=sNav.replaceFirst("\\s+", "");
		if (sCom.startsWith(" ")) sCom=sCom.replaceFirst("\\s+", "");
		if (sDat.startsWith(" ")) sDat=sDat.replaceFirst("\\s+", "");
		if (sSur.startsWith(" ")) sSur=sSur.replaceFirst("\\s+", "");
		if (sDep.startsWith(" ")) sDep=sDep.replaceFirst("\\s+", "");
		if (sDest.startsWith(" ")) sDest=sDest.replaceFirst("\\s+", "");
		if (sDof.startsWith(" ")) sDof=sDof.replaceFirst("\\s+", ""); //t18b(sDof);
		if (sReg.startsWith(" ")) sReg=sReg.replaceFirst("\\s+", "");
		if (sEet.startsWith(" ")) sEet=sEet.replaceFirst("\\s+", "");
		if (sSel.startsWith(" ")) sSel=sSel.replaceFirst("\\s+", "");
		if (sTyp.startsWith(" ")) sTyp=sTyp.replaceFirst("\\s+", "");
		if (sCode.startsWith(" ")) sCode=sCode.replaceFirst("\\s+", "");
		if (sDle.startsWith(" ")) sDle=sDle.replaceFirst("\\s+", "");
		if (sOpr.startsWith(" ")) sOpr=sOpr.replaceFirst("\\s+", "");
		if (sOrgn.startsWith(" ")) sOrgn=sOrgn.replaceFirst("\\s+", "");
		if (sPer.startsWith(" ")) sPer=sPer.replaceFirst("\\s+", "");
		if (sAltn.startsWith(" ")) sAltn=sAltn.replaceFirst("\\s+", "");
		if (sRalt.startsWith(" ")) sRalt=sRalt.replaceFirst("\\s+", "");
		if (sTalt.startsWith(" ")) sTalt=sTalt.replaceFirst("\\s+", "");
		if (sRif.startsWith(" ")) sRif=sRif.replaceFirst("\\s+", "");
		if (sRmk.startsWith(" ")) sRmk=sRmk.replaceFirst("\\s+", "");
		//---------------------------------
		if (sSts.compareTo("")!=0) sSts=" STS/"+sSts; else sSts="";
		if (sPbn.compareTo("")!=0) sPbn=" PBN/"+sPbn; else sPbn="";
		if (sNav.compareTo("")!=0) sNav=" NAV/"+sNav; else sNav="";
		if (sCom.compareTo("")!=0) sCom=" COM/"+sCom; else sCom="";
		if (sDat.compareTo("")!=0) sDat=" DAT/"+sDat; else sDat="";
		if (sSur.compareTo("")!=0) sSur=" SUR/"+sSur; else sSur="";
		if (sDep.compareTo("")!=0) sDep=" DEP/"+sDep; else sDep="";
		if (sDest.compareTo("")!=0) sDest=" DEST/"+sDest; else sDest="";
		if (sDof.compareTo("")!=0) sDof=" DOF/"+sDof; else sDof="";
		if (sReg.compareTo("")!=0) sReg=" REG/"+sReg; else sReg="";
		if (sEet.compareTo("")!=0) sEet=" EET/"+sEet; else sEet="";
		if (sSel.compareTo("")!=0) sSel=" SEL/"+sSel; else sSel="";
		if (sTyp.compareTo("")!=0) sTyp=" TYP/"+sTyp; else sTyp="";
		if (sCode.compareTo("")!=0) sCode=" CODE/"+sCode; else sCode="";
		if (sDle.compareTo("")!=0) sDle=" DLE/"+sDle; else sDle="";
		if (sOpr.compareTo("")!=0) sOpr=" OPR/"+sOpr; else sOpr="";
		if (sOrgn.compareTo("")!=0) sOrgn=" ORGN/"+sOrgn; else sOrgn="";
		if (sPer.compareTo("")!=0) sPer=" PER/"+sPer; else sPer="";
		if (sAltn.compareTo("")!=0) sAltn=" ALTN/"+sAltn; else sAltn="";
		if (sRalt.compareTo("")!=0) sRalt=" RALT/"+sRalt; else sRalt="";
		if (sTalt.compareTo("")!=0) sTalt=" TALT/"+sTalt; else sTalt="";
		if (sRif.compareTo("")!=0) sRif=" RIF/"+sRif; else sRif="";
		if (sRmk.compareTo("")!=0) sRmk=" RMK/"+sRmk; else sRmk="";
		
		type18 = sNav+sCom+sDat+sSur+sDep+sDest+sDof+sReg+sEet+sSel+sTyp+sCode+sDle+sOpr+sOrgn+sPer+sAltn+sRalt+sTalt+sRif+sRmk+sPbn+sSts;
		if (type18.startsWith(" ")) type18 = type18.replaceFirst("\\s+", "");
	}

	public static void getDLAValue() {
		clearDLA();
		filed_by = tFbDLA.getText(); if (filed_by==null) filed_by="";
		String t7bDLAValue="";
		if (t7bDLA.getSelection()) { t7bDLAValue = t7bDLA.toString(); t7bDLAValue = "A"; } else t7bDLAValue="";
		type3a = "DLA"; if (!type3a.equals("DLA")) type3a = "DLA";
		type3b = t3bDLA.getText(); if (type3b == null) type3b="";
		type3c = t3cDLA.getText(); if (type3c == null) type3c="";
		type7a = t7aDLA.getText(); if (type7a == null) type7a="";
		type7b = t7bDLAValue; if (type7b == null) type7b="";
		type7c = t7cDLA.getText(); if (type7c == null) type7c="";
		type13a = t13aDLA.getText(); if (type13a == null) type13a="";
		type13b = t13bDLA.getText(); if (type13b == null) type13b="";
		type16a = t16aDLA.getText(); if (type16a == null) type16a="";
		type16b = t16bDLA.getText(); if (type16b == null) type16b="";
		type16c = t16cDLA.getText(); if (type16c == null) type16c="";
		type16c2nd = t16dDLA.getText(); if (type16c2nd == null) type16c2nd="";
		type18tDof = tDofDLA.getText(); if (type18tDof == null) type18tDof="";
		
		type18tNav="";type18tCom="";type18tDat="";type18tSur="";type18tDep="";type18tDest="";type18tReg="";
		type18tEet="";type18tSel="";type18tTyp="";type18tCode="";type18tDle="";type18tOpr="";type18tOrgn="";type18tPer="";
		type18tAltn="";type18tRalt="";type18tTalt="";type18tRif="";type18tRmk="";type18tPbn="";type18tSts="";

		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			type16b=""; type16c=""; type16c2nd="";
		}
	}

	public static void getCHGValue() {
		clearCHG();
		filed_by = tFbCHG.getText(); if (filed_by==null) filed_by="";
		String t7bCHGValue="";
		if (t7bCHG.getSelection()) { t7bCHGValue = t7bCHG.toString(); t7bCHGValue = "A"; } else t7bCHGValue="";
		type3a = "CHG"; if (!type3a.equals("CHG")) type3a = "CHG";
		type3b = t3bCHG.getText(); if (type3b == null) type3b="";
		type3c = t3cCHG.getText(); if (type3c == null) type3c="";
		type7a = t7aCHG.getText(); if (type7a == null) type7a="";
		type7b = t7bCHGValue; if (type7b == null) type7b="";
		type7c = t7cCHG.getText(); if (type7c == null) type7c="";
		type13a = t13aCHG.getText(); if (type13a == null) type13a="";
		type13b = t13bCHG.getText(); if (type13b == null) type13b="";
		type16a = t16aCHG.getText(); if (type16a == null) type16a="";
		type16b = t16bCHG.getText(); if (type16b == null) type16b="";
		type16c = t16cCHG.getText(); if (type16c == null) type16c="";
		type16c2nd = t16dCHG.getText(); if (type16c2nd == null) type16c2nd="";
		type18tDof = tDofCHG.getText(); if (type18tDof == null) type18tDof="";
		type22 = t22CHG.getText(); if (type22 == null) type22="";
		
		type18tNav="";type18tCom="";type18tDat="";type18tSur="";type18tDep="";type18tDest="";type18tReg="";
		type18tEet="";type18tSel="";type18tTyp="";type18tCode="";type18tDle="";type18tOpr="";type18tOrgn="";type18tPer="";
		type18tAltn="";type18tRalt="";type18tTalt="";type18tRif="";type18tRmk="";type18tPbn="";type18tSts="";
		
		// proteksi kutip untuk field freetext
		if (type22.contains("'")) type22 = type22.replaceAll("'", "`");
		
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			type13b=""; type16b=""; type16c=""; type16c2nd="";
		}
	}

	public static void getCNLValue() {
		clearCNL();
		filed_by = tFbCNL.getText(); if (filed_by==null) filed_by="";
		String t7bCNLValue="";
		if (t7bCNL.getSelection()) { t7bCNLValue = t7bCNL.toString(); t7bCNLValue = "A"; } else t7bCNLValue="";
		type3a = "CNL"; if (!type3a.equals("CNL")) type3a = "CNL";
		type3b = t3bCNL.getText(); if (type3b == null) type3b="";
		type3c = t3cCNL.getText(); if (type3c == null) type3c="";
		type7a = t7aCNL.getText(); if (type7a == null) type7a="";
		type7b = t7bCNLValue; if (type7b == null) type7b="";
		type7c = t7cCNL.getText(); if (type7c == null) type7c="";
		type13a = t13aCNL.getText(); if (type13a == null) type13a="";
		type13b = t13bCNL.getText(); if (type13b == null) type13b="";
		type16a = t16aCNL.getText(); if (type16a == null) type16a="";
		type16b = t16bCNL.getText(); if (type16b == null) type16b="";
		type16c = t16cCNL.getText(); if (type16c == null) type16c="";
		type16c2nd = t16dCNL.getText(); if (type16c2nd == null) type16c2nd="";
		type18tDof = tDofCNL.getText(); if (type18tDof == null) type18tDof="";
		
		type18tNav="";type18tCom="";type18tDat="";type18tSur="";type18tDep="";type18tDest="";type18tReg="";
		type18tEet="";type18tSel="";type18tTyp="";type18tCode="";type18tDle="";type18tOpr="";type18tOrgn="";type18tPer="";
		type18tAltn="";type18tRalt="";type18tTalt="";type18tRif="";type18tRmk="";type18tPbn="";type18tSts="";
		
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			type13b=""; type16b=""; type16c=""; type16c2nd="";
		}
	}

	public static void getDEPValue() {
		clearDEP();
		filed_by = tFbDEP.getText(); if (filed_by==null) filed_by="";
		String t7bDEPValue="";
		if (t7bDEP.getSelection()) { t7bDEPValue = t7bDEP.toString(); t7bDEPValue = "A"; } else t7bDEPValue="";
		type3a = "DEP"; if (!type3a.equals("DEP")) type3a = "DEP";
		type3b = t3bDEP.getText(); if (type3b == null) type3b="";
		type3c = t3cDEP.getText(); if (type3c == null) type3c="";
		type7a = t7aDEP.getText(); if (type7a == null) type7a="";
		type7b = t7bDEPValue; if (type7b == null) type7b="";
		type7c = t7cDEP.getText(); if (type7c == null) type7c="";
		type13a = t13aDEP.getText(); if (type13a == null) type13a="";
		type13b = t13bDEP.getText(); if (type13b == null) type13b="";
		type16a = t16aDEP.getText(); if (type16a == null) type16a="";
		type16b = t16bDEP.getText(); if (type16b == null) type16b="";
		type16c = t16cDEP.getText(); if (type16c == null) type16c="";
		type16c2nd = t16dDEP.getText(); if (type16c2nd == null) type16c2nd="";
		type18tDof = tDofDEP.getText(); if (type18tDof == null) type18tDof="";
		
		type18tNav="";type18tCom="";type18tDat="";type18tSur="";type18tDep="";type18tDest="";type18tReg="";
		type18tEet="";type18tSel="";type18tTyp="";type18tCode="";type18tDle="";type18tOpr="";type18tOrgn="";type18tPer="";
		type18tAltn="";type18tRalt="";type18tTalt="";type18tRif="";type18tRmk="";type18tPbn="";type18tSts="";
		
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			type16b=""; type16c=""; type16c2nd="";
		}
	}
	
	public static void getARRValue() {
		clearARR();
		filed_by = tFbARR.getText(); if (filed_by==null) filed_by="";
		String t7bARRValue="";
		if (t7bARR.getSelection()) { t7bARRValue = t7bARR.toString(); t7bARRValue = "A"; } else t7bARRValue="";
		type3a = "ARR"; if (!type3a.equals("ARR")) type3a = "ARR";
		type3b = t3bARR.getText(); if (type3b == null) type3b="";
		type3c = t3cARR.getText(); if (type3c == null) type3c="";
		type7a = t7aARR.getText(); if (type7a == null) type7a="";
		type7b = t7bARRValue; if (type7b == null) type7b="";
		type7c = t7cARR.getText(); if (type7c == null) type7c="";
		type13a = t13aARR.getText(); if (type13a == null) type13a="";
		type13b = t13bARR.getText(); if (type13b == null) type13b="";
		type17a = t17aARR.getText(); if (type17a == null) type17a="";
		type17b = t17bARR.getText(); if (type17b == null) type17b="";
		type17c = t17cARR.getText(); if (type17c == null) type17c="";
		
		// proteksi kutip untuk field freetext
		if (type17c.contains("'")) type17c = type17c.replaceAll("'", "`");
		
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			type13b=""; 
		}
	}

	public static void getCDNValue() {
		clearCDN();
		filed_by = tFbCDN.getText(); if (filed_by==null) filed_by="";
		String t7bCDNValue="";
		if (t7bCDN.getSelection()) { t7bCDNValue = t7bCDN.toString(); t7bCDNValue = "A"; } else t7bCDNValue="";
		type3a = "CDN"; if (!type3a.equals("CDN")) type3a = "CDN";
		type3b = t3bCDN.getText(); if (type3b == null) type3b="";
		type3c = t3cCDN.getText(); if (type3c == null) type3c="";
		type7a = t7aCDN.getText(); if (type7a == null) type7a="";
		type7b = t7bCDNValue; if (type7b == null) type7b="";
		type7c = t7cCDN.getText(); if (type7c == null) type7c="";
		type13a = t13aCDN.getText(); if (type13a == null) type13a="";
		type13b = t13bCDN.getText(); if (type13b == null) type13b="";
		type16a = t16aCDN.getText(); if (type16a == null) type16a="";
		type16b = t16bCDN.getText(); if (type16b == null) type16b="";
		type16c = t16cCDN.getText(); if (type16c == null) type16c="";
		type16c2nd = t16dCDN.getText(); if (type16c2nd == null) type16c2nd="";
		type22 = t22CDN.getText(); if (type22 == null) type22="";
		// proteksi kutip untuk field freetext
		if (type22.contains("'")) type22 = type22.replaceAll("'", "`");
		
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			type13b=""; type16b=""; type16c=""; type16c2nd="";
		}
	}

	public static void getCPLValue() {
		clearCPL();
		filed_by = tFbCPL.getText(); if (filed_by==null) filed_by="";
		String t7bCPLValue="";
		if (t7bCPL.getSelection()) { t7bCPLValue = t7bCPL.toString(); t7bCPLValue = "A"; } else t7bCPLValue="";
		type3a = "CPL"; if (!type3a.equals("CPL")) type3a = "CPL";
		type3b = t3bCPL.getText(); if (type3b == null) type3b="";
		type3c = t3cCPL.getText(); if (type3c == null) type3c="";
		type7a = t7aCPL.getText(); if (type7a == null) type7a="";
		type7b = t7bCPLValue; if (type7b == null) type7b="";
		type7c = t7cCPL.getText(); if (type7c == null) type7c="";
		type8a = t8aCPL.getText(); if (type8a == null) type8a="";
		type8b = t8bCPL.getText(); if (type8b == null) type8b="";
		type9a = t9aCPL.getText(); if (type9a == null) type9a="";
		type9b = t9bCPL.getText(); if (type9b == null) type9b="";
		type9c = t9cCPL.getText(); if (type9c == null) type9c="";
		type10a = t10aCPL.getText(); if (type10a == null) type10a="";
		type10bBr = t10bCPL.getText(); if (type10bBr == null) type10bBr="";
		type13a = t13aCPL.getText(); if (type13a == null) type13a="";
		type13b = t13bCPL.getText(); if (type13b == null) type13b="";
		type14a = t14aCPL.getText(); if (type14a == null) type14a="";
		type14b = t14bCPL.getText(); if (type14b == null) type14b="";
		type14c = t14cCPL.getText(); if (type14c == null) type14c="";
		type14d = t14dCPL.getText(); if (type14d == null) type14d="";
		type14e = t14eCPL.getText(); if (type14e == null) type14e="";
		type15a = t15aCPL.getText(); if (type15a == null) type15a="";
		type15b = t15bCPL.getText(); if (type15b == null) type15b="";
		type15c = t15cCPL.getText(); if (type15c == null) type15c="";
		type16a = t16aCPL.getText(); if (type16a == null) type16a="";
		type16b = t16bCPL.getText(); if (type16b == null) type16b="";
		type16c = t16cCPL.getText(); if (type16c == null) type16c="";
		type16c2nd = t16dCPL.getText(); if (type16c2nd == null) type16c2nd="";
		type18tNav = tNavCPL.getText(); if (type18tNav == null) type18tNav="";
		type18tCom = tComCPL.getText(); if (type18tCom == null) type18tCom="";
		type18tDat = tDatCPL.getText(); if (type18tDat == null) type18tDat="";
		type18tSur = tSurCPL.getText(); if (type18tSur == null) type18tSur="";
		type18tDep = tDepCPL.getText(); if (type18tDep == null) type18tDep="";
		type18tDest = tDestCPL.getText(); if (type18tDest == null) type18tDest="";
		type18tDof = tDofCPL.getText(); if (type18tDof == null) type18tDof="";
		type18tReg = tRegCPL.getText(); if (type18tReg == null) type18tReg="";
		type18tEet = tEetCPL.getText(); if (type18tEet == null) type18tEet="";
		type18tSel = tSelCPL.getText(); if (type18tSel == null) type18tSel="";
		type18tTyp = tTypCPL.getText(); if (type18tTyp == null) type18tTyp="";
		type18tCode = tCodeCPL.getText(); if (type18tCode == null) type18tCode="";
		type18tDle = tDleCPL.getText(); if (type18tDle == null) type18tDle="";
		type18tOpr = tOprCPL.getText(); if (type18tOpr == null) type18tOpr="";
		type18tOrgn = tOrgnCPL.getText(); if (type18tOrgn == null) type18tOrgn="";
		type18tPer = tPerCPL.getText(); if (type18tPer == null) type18tPer="";
		type18tAltn = tAltnCPL.getText(); if (type18tAltn == null) type18tAltn="";
		type18tRalt = tRaltCPL.getText(); if (type18tRalt == null) type18tRalt="";
		type18tTalt = tTaltCPL.getText(); if (type18tTalt == null) type18tTalt="";
		type18tRif = tRifCPL.getText(); if (type18tRif == null) type18tRif="";
		type18tRmk = tRmkCPL.getText(); if (type18tRmk == null) type18tRmk="";
		type18tPbn = tPbnCPL.getText(); if (type18tPbn == null) type18tPbn="";
		type18tSts = tStsCPL.getText(); if (type18tSts == null) type18tSts="";
		// proteksi kutip untuk field freetext
		if (type15c.contains("'")) type15c = type15c.replace("'", "`");
		if (type18tSts.contains("'")) type18tSts = type18tSts.replaceAll("'", "`");
		if (type18tNav.contains("'")) type18tNav = type18tNav.replaceAll("'", "`");
		if (type18tCom.contains("'")) type18tCom = type18tCom.replaceAll("'", "`");
		if (type18tDat.contains("'")) type18tDat = type18tDat.replaceAll("'", "`");
		if (type18tSur.contains("'")) type18tSur = type18tSur.replaceAll("'", "`");
		if (type18tDep.contains("'")) type18tDep = type18tDep.replaceAll("'", "`");
		if (type18tDest.contains("'")) type18tDest = type18tDest.replaceAll("'", "`");
		if (type18tEet.contains("'")) type18tEet = type18tEet.replaceAll("'", "`");
		if (type18tTyp.contains("'")) type18tTyp = type18tTyp.replaceAll("'", "`");
		if (type18tDle.contains("'")) type18tDle = type18tDle.replaceAll("'", "`");
		if (type18tOpr.contains("'")) type18tOpr = type18tOpr.replaceAll("'", "`");
		if (type18tOrgn.contains("'")) type18tOrgn = type18tOrgn.replaceAll("'", "`");
		if (type18tAltn.contains("'")) type18tAltn = type18tAltn.replaceAll("'", "`");
		if (type18tRalt.contains("'")) type18tRalt = type18tRalt.replaceAll("'", "`");
		if (type18tTalt.contains("'")) type18tTalt = type18tTalt.replaceAll("'", "`");
		if (type18tRif.contains("'")) type18tRif = type18tRif.replaceAll("'", "`");
		if (type18tRmk.contains("'")) type18tRmk = type18tRmk.replaceAll("'", "`");
		// proteksi '/' untuk field freetext type18baru
		if (type18tNav.contains("/")) type18tNav = type18tNav.replaceAll("/", "~");
		if (type18tCom.contains("/")) type18tCom = type18tCom.replaceAll("/", "~");
		if (type18tDat.contains("/")) type18tDat = type18tDat.replaceAll("/", "~");
		if (type18tSur.contains("/")) type18tSur = type18tSur.replaceAll("/", "~");
		if (type18tDep.contains("/")) type18tDep = type18tDep.replaceAll("/", "~");
		if (type18tDest.contains("/")) type18tDest = type18tDest.replaceAll("/", "~");
		if (type18tTyp.contains("/")) type18tTyp = type18tTyp.replaceAll("/", "~");
		if (type18tOpr.contains("/")) type18tOpr = type18tOpr.replaceAll("/", "~");
		if (type18tOrgn.contains("/")) type18tOrgn = type18tOrgn.replaceAll("/", "~");
		if (type18tAltn.contains("/")) type18tAltn = type18tAltn.replaceAll("/", "~");
		if (type18tRalt.contains("/")) type18tRalt = type18tRalt.replaceAll("/", "~");
		if (type18tTalt.contains("/")) type18tTalt = type18tTalt.replaceAll("/", "~");
		if (type18tRif.contains("/")) type18tRif = type18tRif.replaceAll("/", "~");
		if (type18tRmk.contains("/")) type18tRmk = type18tRmk.replaceAll("/", "~");
		getType18(type18tNav, type18tCom, type18tDat, type18tSur, type18tDep, type18tDest, type18tDof, type18tReg, type18tEet, type18tSel, type18tTyp, type18tCode, type18tDle, type18tOpr, type18tOrgn, type18tPer, type18tAltn, type18tRalt, type18tTalt, type18tRif, type18tRmk, type18tPbn, type18tSts);
	
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			type13b=""; type16b=""; type16c=""; type16c2nd="";
		}
	}

	public static void getESTValue() {
		clearEST();
		filed_by = tFbEST.getText(); if (filed_by==null) filed_by="";
		String t7bESTValue="";
		if (t7bEST.getSelection()) { t7bESTValue = t7bEST.toString(); t7bESTValue = "A"; } else t7bESTValue="";
		type3a = "EST"; if (!type3a.equals("EST")) type3a = "EST";
		type3b = t3bEST.getText(); if (type3b == null) type3b="";
		type3c = t3cEST.getText(); if (type3c == null) type3c="";
		type7a = t7aEST.getText(); if (type7a == null) type7a="";
		type7b = t7bESTValue; if (type7b == null) type7b="";
		type7c = t7cEST.getText(); if (type7c == null) type7c="";
		type13a = t13aEST.getText(); if (type13a == null) type13a="";
		type13b = t13bEST.getText(); if (type13b == null) type13b="";
		type14a = t14aEST.getText(); if (type14a == null) type14a="";
		type14b = t14bEST.getText(); if (type14b == null) type14b="";
		type14c = t14cEST.getText(); if (type14c == null) type14c="";
		type14d = t14dEST.getText(); if (type14d == null) type14d="";
		type14e = t14eEST.getText(); if (type14e == null) type14e="";
		type16a = t16aEST.getText(); if (type16a == null) type16a="";
		type16b = t16bEST.getText(); if (type16b == null) type16b="";
		type16c = t16cEST.getText(); if (type16c == null) type16c="";
		type16c2nd = t16dEST.getText(); if (type16c2nd == null) type16c2nd="";
		
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			type13b=""; type16b=""; type16c=""; type16c2nd="";
		}
	}

	public static void getACPValue() {
		clearACP();
		filed_by = tFbACP.getText(); if (filed_by==null) filed_by="";
		String t7bACPValue="";
		if (t7bACP.getSelection()) { t7bACPValue = t7bACP.toString(); t7bACPValue = "A"; } else t7bACPValue="";
		type3a = "ACP"; if (!type3a.equals("ACP")) type3a = "ACP";
		type3b = t3bACP.getText(); if (type3b == null) type3b="";
		type3c = t3cACP.getText(); if (type3c == null) type3c="";
		type7a = t7aACP.getText(); if (type7a == null) type7a="";
		type7b = t7bACPValue; if (type7b == null) type7b="";
		type7c = t7cACP.getText(); if (type7c == null) type7c="";
		type13a = t13aACP.getText(); if (type13a == null) type13a="";
		type13b = t13bACP.getText(); if (type13b == null) type13b="";
		type16a = t16aACP.getText(); if (type16a == null) type16a="";
		type16b = t16bACP.getText(); if (type16b == null) type16b="";
		type16c = t16cACP.getText(); if (type16c == null) type16c="";
		type16c2nd = t16dACP.getText(); if (type16c2nd == null) type16c2nd="";
		
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			type13b=""; type16b=""; type16c=""; type16c2nd="";
		}
	}

	public static void getLAMValue() {
		clearLAM();
		filed_by = tFbLAM.getText(); if (filed_by==null) filed_by="";
		type3a = "LAM"; if (!type3a.equals("LAM")) type3a = "LAM";
		type3b = t3bLAM.getText(); if (type3b == null) type3b="";
		type3c = t3cLAM.getText(); if (type3c == null) type3c="";
	}

	public static void getRQPValue() {
		clearRQP();
		filed_by = tFbRQP.getText(); if (filed_by==null) filed_by="";
		String t7bRQPValue="";
		if (t7bRQP.getSelection()) { t7bRQPValue = t7bRQP.toString(); t7bRQPValue = "A"; } else t7bRQPValue="";
		type3a = "RQP"; if (!type3a.equals("RQP")) type3a = "RQP";
		type3b = t3bRQP.getText(); if (type3b == null) type3b="";
		type3c = t3cRQP.getText(); if (type3c == null) type3c="";
		type7a = t7aRQP.getText(); if (type7a == null) type7a="";
		type7b = t7bRQPValue; if (type7b == null) type7b="";
		type7c = t7cRQP.getText(); if (type7c == null) type7c="";
		type13a = t13aRQP.getText(); if (type13a == null) type13a="";
		type13b = t13bRQP.getText(); if (type13b == null) type13b="";
		type16a = t16aRQP.getText(); if (type16a == null) type16a="";
		type16b = t16bRQP.getText(); if (type16b == null) type16b="";
		type16c = t16cRQP.getText(); if (type16c == null) type16c="";
		type16c2nd = t16dRQP.getText(); if (type16c2nd == null) type16c2nd="";
		type18tDof = tDofRQP.getText(); if (type18tDof == null) type18tDof="";
		
		type18tNav="";type18tCom="";type18tDat="";type18tSur="";type18tDep="";type18tDest="";type18tReg="";
		type18tEet="";type18tSel="";type18tTyp="";type18tCode="";type18tDle="";type18tOpr="";type18tOrgn="";type18tPer="";
		type18tAltn="";type18tRalt="";type18tTalt="";type18tRif="";type18tRmk="";type18tPbn="";type18tSts="";
	}

	public static void getRQSValue() {
		clearRQS();
		filed_by = tFbRQS.getText(); if (filed_by==null) filed_by="";
		String t7bRQSValue="";
		if (t7bRQS.getSelection()) { t7bRQSValue = t7bRQS.toString(); t7bRQSValue = "A"; } else t7bRQSValue="";
		type3a = "RQS"; if (!type3a.equals("RQS")) type3a = "RQS";
		type3b = t3bRQS.getText(); if (type3b == null) type3b="";
		type3c = t3cRQS.getText(); if (type3c == null) type3c="";
		type7a = t7aRQS.getText(); if (type7a == null) type7a="";
		type7b = t7bRQSValue; if (type7b == null) type7b="";
		type7c = t7cRQS.getText(); if (type7c == null) type7c="";
		type13a = t13aRQS.getText(); if (type13a == null) type13a="";
		type13b = t13bRQS.getText(); if (type13b == null) type13b="";
		type16a = t16aRQS.getText(); if (type16a == null) type16a="";
		type16b = t16bRQS.getText(); if (type16b == null) type16b="";
		type16c = t16cRQS.getText(); if (type16c == null) type16c="";
		type16c2nd = t16dRQS.getText(); if (type16c2nd == null) type16c2nd="";
		type18tDof = tDofRQS.getText(); if (type18tDof == null) type18tDof="";
		
		type18tNav="";type18tCom="";type18tDat="";type18tSur="";type18tDep="";type18tDest="";type18tReg="";
		type18tEet="";type18tSel="";type18tTyp="";type18tCode="";type18tDle="";type18tOpr="";type18tOrgn="";type18tPer="";
		type18tAltn="";type18tRalt="";type18tTalt="";type18tRif="";type18tRmk="";type18tPbn="";type18tSts="";
		
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			type13b=""; type16b=""; type16c=""; type16c2nd="";
		}
	}

	public static void getSPLValue() {
		clearSPL();
		filed_by = tFbSPL.getText(); if (filed_by==null) filed_by="";
		String t7bSPLValue="",t19cUhfSPLValue="",t19cVhfSPLValue="",t19cEltSPLValue="",t19dPSPLValue="",t19dDSPLValue="",t19dMSPLValue="",
		t19dJSPLValue="",t19eLSPLValue="",t19eFSPLValue="",t19eUSPLValue="",t19eVSPLValue="";
		
		if (t7bSPL.getSelection()) { t7bSPLValue = t7bSPL.toString(); t7bSPLValue = "A"; } else t7bSPLValue="";
		if (t19cUhfSPL.getText().compareTo("U")==0) { t19cUhfSPLValue = t19cUhfSPL.toString(); t19cUhfSPLValue = "R/U"; } else t19cUhfSPLValue="";
		if (t19cVhfSPL.getText().compareTo("V")==0) { t19cVhfSPLValue = t19cVhfSPL.toString(); t19cVhfSPLValue = "R/V"; } else t19cVhfSPLValue="";
		if (t19cEltSPL.getText().compareTo("E")==0) { t19cEltSPLValue = t19cEltSPL.toString(); t19cEltSPLValue = "R/E"; } else t19cEltSPLValue="";
		if (t19dPSPL.getText().compareTo("P")==0) { t19dPSPLValue = t19dPSPL.toString(); t19dPSPLValue = "S/P"; } else t19dPSPLValue="";
		if (t19dDSPL.getText().compareTo("D")==0) { t19dDSPLValue = t19dDSPL.toString(); t19dDSPLValue = "S/D"; } else t19dDSPLValue="";
		if (t19dMSPL.getText().compareTo("M")==0) { t19dMSPLValue = t19dMSPL.toString(); t19dMSPLValue = "S/M"; } else t19dMSPLValue="";
		if (t19dJSPL.getText().compareTo("J")==0) { t19dJSPLValue = t19dJSPL.toString(); t19dJSPLValue = "S/J"; } else t19dJSPLValue="";
		if (t19eLSPL.getText().compareTo("L")==0) { t19eLSPLValue = t19eLSPL.toString(); t19eLSPLValue = "J/L"; } else t19eLSPLValue=""; 
		if (t19eFSPL.getText().compareTo("F")==0) { t19eFSPLValue = t19eFSPL.toString(); t19eFSPLValue = "J/F"; } else t19eFSPLValue="";
		if (t19eUSPL.getText().compareTo("U")==0) { t19eUSPLValue = t19eUSPL.toString(); t19eUSPLValue = "J/U"; } else t19eUSPLValue="";
		if (t19eVSPL.getText().compareTo("V")==0) { t19eVSPLValue = t19eVSPL.toString(); t19eVSPLValue = "J/V"; } else t19eVSPLValue="";
		if (t19cSPL.getText().compareTo("C")==0) { type19f_cover = t19cSPL.toString(); type19f_cover = "C"; } else type19f_cover="";

		type3a = "SPL"; if (!type3a.equals("SPL")) type3a = "SPL";
		type3b = t3bSPL.getText(); if (type3b == null) type3b="";
		type3c = t3cSPL.getText(); if (type3c == null) type3c="";
		type7a = t7aSPL.getText(); if (type7a == null) type7a="";
		type7b = t7bSPLValue; if (type7b == null) type7b="";
		type7c = t7cSPL.getText(); if (type7c == null) type7c="";
		type13a = t13aSPL.getText(); if (type13a == null) type13a="";
		type13b = t13bSPL.getText(); if (type13b == null) type13b="";
		type16a = t16aSPL.getText();
		type16a = t16aSPL.getText(); if (type16a == null) type16a="";
		type16b = t16bSPL.getText(); if (type16b == null) type16b="";
		type16c = t16cSPL.getText(); if (type16c == null) type16c="";
		type16c2nd = t16dSPL.getText(); if (type16c2nd == null) type16c2nd="";
		type18tNav = tNavSPL.getText(); if (type18tNav == null) type18tNav="";
		type18tCom = tComSPL.getText(); if (type18tCom == null) type18tCom="";
		type18tDat = tDatSPL.getText(); if (type18tDat == null) type18tDat="";
		type18tSur = tSurSPL.getText(); if (type18tSur == null) type18tSur="";
		type18tDep = tDepSPL.getText(); if (type18tDep == null) type18tDep="";
		type18tDest = tDestSPL.getText(); if (type18tDest == null) type18tDest="";
		type18tDof = tDofSPL.getText(); if (type18tDof == null) type18tDof="";
		type18tReg = tRegSPL.getText(); if (type18tReg == null) type18tReg="";
		type18tEet = tEetSPL.getText(); if (type18tEet == null) type18tEet="";
		type18tSel = tSelSPL.getText(); if (type18tSel == null) type18tSel="";
		type18tTyp = tTypSPL.getText(); if (type18tTyp == null) type18tTyp="";
		type18tCode = tCodeSPL.getText(); if (type18tCode == null) type18tCode="";
		type18tDle = tDleSPL.getText(); if (type18tDle == null) type18tDle="";
		type18tOpr = tOprSPL.getText(); if (type18tOpr == null) type18tOpr="";
		type18tOrgn = tOrgnSPL.getText(); if (type18tOrgn == null) type18tOrgn="";
		type18tPer = tPerSPL.getText(); if (type18tPer == null) type18tPer="";
		type18tAltn = tAltnSPL.getText(); if (type18tAltn == null) type18tAltn="";
		type18tRalt = tRaltSPL.getText(); if (type18tRalt == null) type18tRalt="";
		type18tTalt = tTaltSPL.getText(); if (type18tTalt == null) type18tTalt="";
		type18tRif = tRifSPL.getText(); if (type18tRif == null) type18tRif="";
		type18tRmk = tRmkSPL.getText(); if (type18tRmk == null) type18tRmk="";
		type18tPbn = tPbnSPL.getText(); if (type18tPbn == null) type18tPbn="";
		type18tSts = tStsSPL.getText(); if (type18tSts == null) type18tSts="";
		type19a = t19aSPL.getText(); if (type19a == null) type19a="";
		type19b = t19bSPL.getText(); if (type19b == null) type19b="";
		type19cUHF = t19cUhfSPLValue; if (type19cUHF == null) type19cUHF="";
		type19cVHF = t19cVhfSPLValue; if (type19cVHF == null) type19cVHF="";
		type19cELT = t19cEltSPLValue; if (type19cELT == null) type19cELT="";
		type19dP = t19dPSPLValue; if (type19dP == null) type19dP="";
		type19dD = t19dDSPLValue; if (type19dD == null) type19dD="";
		type19dM = t19dMSPLValue; if (type19dM == null) type19dM="";
		type19dJ = t19dJSPLValue; if (type19dJ == null) type19dJ="";
		type19eL = t19eLSPLValue; if (type19eL == null) type19eL="";
		type19eF = t19eFSPLValue; if (type19eF == null) type19eF="";
		type19eU = t19eUSPLValue; if (type19eU == null) type19eU="";
		type19eV = t19eVSPLValue; if (type19eV == null) type19eV="";
		type19f_number = t19NumSPL.getText(); if (type19f_number == null) type19f_number="";
		type19f_capacity = t19CapSPL.getText(); if (type19f_capacity == null) type19f_capacity="";
		type19f_colour = t19ColSPL.getText(); if (type19f_colour == null) type19f_colour="";
		type19g = t19AirSPL.getText(); if (type19g == null) type19g="";
		type19Remarks = t19RemSPL.getText(); if (type19Remarks == null) type19Remarks="";
		type19i = t19PilSPL.getText(); if (type19i == null) type19i="";
		// proteksi kutip untuk field freetext
		if (type18tSts.contains("'")) type18tSts = type18tSts.replaceAll("'", "`");
		if (type18tNav.contains("'")) type18tNav = type18tNav.replaceAll("'", "`");
		if (type18tCom.contains("'")) type18tCom = type18tCom.replaceAll("'", "`");
		if (type18tDat.contains("'")) type18tDat = type18tDat.replaceAll("'", "`");
		if (type18tSur.contains("'")) type18tSur = type18tSur.replaceAll("'", "`");
		if (type18tDep.contains("'")) type18tDep = type18tDep.replaceAll("'", "`");
		if (type18tDest.contains("'")) type18tDest = type18tDest.replaceAll("'", "`");
		if (type18tEet.contains("'")) type18tEet = type18tEet.replaceAll("'", "`");
		if (type18tTyp.contains("'")) type18tTyp = type18tTyp.replaceAll("'", "`");
		if (type18tDle.contains("'")) type18tDle = type18tDle.replaceAll("'", "`");
		if (type18tOpr.contains("'")) type18tOpr = type18tOpr.replaceAll("'", "`");
		if (type18tOrgn.contains("'")) type18tOrgn = type18tOrgn.replaceAll("'", "`");
		if (type18tAltn.contains("'")) type18tAltn = type18tAltn.replaceAll("'", "`");
		if (type18tRalt.contains("'")) type18tRalt = type18tRalt.replaceAll("'", "`");
		if (type18tTalt.contains("'")) type18tTalt = type18tTalt.replaceAll("'", "`");
		if (type18tRif.contains("'")) type18tRif = type18tRif.replaceAll("'", "`");
		if (type18tRmk.contains("'")) type18tRmk = type18tRmk.replaceAll("'", "`");
		// proteksi '/' untuk field freetext type18baru
		if (type18tNav.contains("/")) type18tNav = type18tNav.replaceAll("/", "~");
		if (type18tCom.contains("/")) type18tCom = type18tCom.replaceAll("/", "~");
		if (type18tDat.contains("/")) type18tDat = type18tDat.replaceAll("/", "~");
		if (type18tSur.contains("/")) type18tSur = type18tSur.replaceAll("/", "~");
		if (type18tDep.contains("/")) type18tDep = type18tDep.replaceAll("/", "~");
		if (type18tDest.contains("/")) type18tDest = type18tDest.replaceAll("/", "~");
		if (type18tTyp.contains("/")) type18tTyp = type18tTyp.replaceAll("/", "~");
		if (type18tOpr.contains("/")) type18tOpr = type18tOpr.replaceAll("/", "~");
		if (type18tOrgn.contains("/")) type18tOrgn = type18tOrgn.replaceAll("/", "~");
		if (type18tAltn.contains("/")) type18tAltn = type18tAltn.replaceAll("/", "~");
		if (type18tRalt.contains("/")) type18tRalt = type18tRalt.replaceAll("/", "~");
		if (type18tTalt.contains("/")) type18tTalt = type18tTalt.replaceAll("/", "~");
		if (type18tRif.contains("/")) type18tRif = type18tRif.replaceAll("/", "~");
		if (type18tRmk.contains("/")) type18tRmk = type18tRmk.replaceAll("/", "~");
		getType18(type18tNav, type18tCom, type18tDat, type18tSur, type18tDep, type18tDest, type18tDof, type18tReg, type18tEet, type18tSel, type18tTyp, type18tCode, type18tDle, type18tOpr, type18tOrgn, type18tPer, type18tAltn, type18tRalt, type18tTalt, type18tRif, type18tRmk, type18tPbn, type18tSts);
	}
	
	/**
	 * ----------------------------------------------------------------------------------------------------------------------------	
	 * merangkai isi text yang diisikan sesuai template masing-masing, untuk di tabel: check_status 
	 * masuk antrian berita yang akan dikirimkan
	 * ----------------------------------------------------------------------------------------------------------------------------
	 */
	public static String getBodyAMO() {
		getAMOValue();
		vATS.formFreeText(manual_ats);
		return vATS.strFree;
	}
	
	public static String getBodyFREE() {
		getFREEValue();
		vATS.formFreeText(manual_ats);
		return vATS.strFree;
	}
	
	public static String getBodyAFTN() {
		getAFTNValue();
		vATS.formFreeText(manual_ats);
		return vATS.strFree;
//		free = manual_ats;
//		return free;
//		return manual_ats;
	}

	public static String getBodyALR() {
		getALRValue();
		vATS.form3(type3a, type3b, type3c);
		vATS.form5(type5a, type5b, type5c);
		vATS.form7(type7a, type7b, type7c);
		vATS.form8(type8a, type8b);
		vATS.form9(type9a, type9b, type9c);
		vATS.form10(type10a, type10bBr);
		vATS.form13(type13a, type13b);
		vATS.form15(type15a, type15b, type15c);
		vATS.form16(type16a, type16b, type16c, type16c2nd);
		vATS.form18(type18a, type18b, type18);
		//vATS.form19(type19a, type19b, type19cUHF, type19cVHF, type19cELT, type19dS, type19dP, type19dD, type19dM, type19dJ, type19eJ, type19eL, type19eF, type19eU, type19eV, type19fD, type19f_number, type19f_capacity, type19f_cover, type19f_colour, type19g, type19hN, type19Remarks, type19i);
		vATS.form19(type19a, type19b, type19cUHF, type19cVHF, type19cELT, type19dS, type19dP, type19dD, type19dM, type19dJ, type19eJ, type19eL, type19eF, type19eU, type19eV, type19f_number, type19f_capacity, type19f_cover, type19f_colour, type19g, type19Remarks, type19i);
		vATS.form20(type20);
		vATS.msgALR();
		
		return vATS.alr;
	}
	
	public static String getBodyRCF() {
		getRCFValue();
		vATS.form3(type3a, type3b, type3c);
		vATS.form7(type7a, type7b, type7c);
		vATS.form21(type21);
		vATS.msgRCF();
		return vATS.rcf;
	}

	public static String getBodyFPL() { 
		getFPLValue();
		vATS.form3(type3a, type3b, type3c);
		vATS.form7(type7a, type7b, type7c);
		vATS.form8(type8a, type8b);
		vATS.form9(type9a, type9b, type9c);
		vATS.form10(type10a, type10bBr);
		vATS.form13(type13a, type13b);
		vATS.form15(type15a, type15b, type15c);
		vATS.form16(type16a, type16b, type16c, type16c2nd);
		vATS.form18(type18a, type18b, type18);
		vATS.form19(type19a, type19b, type19cUHF, type19cVHF, type19cELT, type19dS, type19dP, type19dD, type19dM, type19dJ, type19eJ, type19eL, type19eF, type19eU, type19eV, type19f_number, type19f_capacity, type19f_cover, type19f_colour, type19g, type19Remarks, type19i);
		vATS.msgFPL();
		return vATS.fpl;
	}
	
	public static String getBodyDLA() {
		getDLAValue();
		vATS.form3(type3a, type3b, type3c);
		vATS.form7(type7a, type7b, type7c);
		vATS.form13(type13a, type13b);
		vATS.form16(type16a, type16b, type16c, type16c2nd);
		vATS.form18("", type18tDof, "");
		vATS.msgDLA();
		return vATS.dla;
	}

	public static String getBodyCHG() {
		getCHGValue();
		vATS.form3(type3a, type3b, type3c);
		vATS.form7(type7a, type7b, type7c);
		vATS.form13(type13a, type13b);
		vATS.form16(type16a, type16b, type16c, type16c2nd);
		vATS.form18("", type18tDof, "");
		vATS.form22(type22);
		vATS.msgCHG();
		return vATS.chg;
	}

	public static String getBodyCNL() {
		getCNLValue();
		vATS.form3(type3a, type3b, type3c);
		vATS.form7(type7a, type7b, type7c);
		vATS.form13(type13a, type13b);
		vATS.form16(type16a, type16b, type16c, type16c2nd);
		vATS.form18("", type18tDof, "");
		vATS.msgCNL();
		return vATS.cnl;
	}

	public static String getBodyDEP() {
		getDEPValue();
		vATS.form3(type3a, type3b, type3c);
		vATS.form7(type7a, type7b, type7c);
		vATS.form13(type13a, type13b);
		vATS.form16(type16a, type16b, type16c, type16c2nd);
		vATS.form18("", type18tDof, "");
		vATS.msgDEP();
		return vATS.dep;
	}

	public static String getBodyARR() {
		getARRValue();
		vATS.form3(type3a, type3b, type3c);
		vATS.form7(type7a, type7b, type7c);
		vATS.form13(type13a, type13b);
		vATS.form17(type17a, type17b, type17c);
		vATS.msgARR();
		return vATS.arr;
	}

	public static String getBodyCDN() {
		getCDNValue();
		vATS.form3(type3a, type3b, type3c);
		vATS.form7(type7a, type7b, type7c);
		vATS.form13(type13a, type13b);
		vATS.form16(type16a, type16b, type16c, type16c2nd);
		vATS.form22(type22);
		vATS.msgCDN();
		return vATS.cdn;
	}

	public static String getBodyCPL() {
		getCPLValue();
		vATS.form3(type3a, type3b, type3c);
		vATS.form7(type7a, type7b, type7c);
		vATS.form8(type8a, type8b);
		vATS.form9(type9a, type9b, type9c);
		vATS.form10(type10a, type10bBr);
		vATS.form13(type13a, type13b);
		vATS.form14(type14a, type14b, type14c, type14d, type14e);
		vATS.form15(type15a, type15b, type15c);
		vATS.form16(type16a, type16b, type16c, type16c2nd);
		vATS.form18(type18a, type18b, type18);
		vATS.msgCPL();
		return vATS.cpl;
	}
	
	public static String getBodyEST() {
		getESTValue();
		vATS.form3(type3a, type3b, type3c);
		vATS.form7(type7a, type7b, type7c);
		vATS.form13(type13a, type13b);
		vATS.form14(type14a, type14b, type14c, type14d, type14e);
		vATS.form16(type16a, type16b, type16c, type16c2nd);
		vATS.msgEST();
		return vATS.est;
	}

	public static String getBodyACP() {
		getACPValue();
		vATS.form3(type3a, type3b, type3c);
		vATS.form7(type7a, type7b, type7c);
		vATS.form13(type13a, type13b);
		vATS.form16(type16a, type16b, type16c, type16c2nd);
		vATS.msgACP();
		return vATS.acp;
	}

	public static String getBodyLAM() {
		getLAMValue();
		vATS.form3(type3a, type3b, type3c);
		vATS.msgLAM();
		return vATS.lam;
	}

	public static String getBodyRQP() {
		getRQPValue();
		vATS.form3(type3a, type3b, type3c);
		vATS.form7(type7a, type7b, type7c);
		vATS.form13(type13a, type13b);
		vATS.form16(type16a, type16b, type16c, type16c2nd);
		vATS.form18("", type18tDof, "");
		vATS.msgRQP();
		return vATS.rqp;
	}

	public static String getBodyRQS() {
		getRQSValue();
		vATS.form3(type3a, type3b, type3c);
		vATS.form7(type7a, type7b, type7c);
		vATS.form13(type13a, type13b);
		vATS.form16(type16a, type16b, type16c, type16c2nd);
		vATS.form18("", type18tDof, "");
		vATS.msgRQS();
		return vATS.rqs;
	}

	public static String getBodySPL() {
		getSPLValue();
		vATS.form3(type3a, type3b, type3c);
		vATS.form7(type7a, type7b, type7c);
		vATS.form13(type13a, type13b);
		vATS.form16(type16a, type16b, type16c, type16c2nd);
		vATS.form18(type18a, type18b, type18);
		//vATS.form19(type19a, type19b, type19cUHF, type19cVHF, type19cELT, type19dS, type19dP, type19dD, type19dM, type19dJ, type19eJ, type19eL, type19eF, type19eU, type19eV, type19fD, type19f_number, type19f_capacity, type19f_cover, type19f_colour, type19g, type19hN, type19Remarks, type19i);
		vATS.form19(type19a, type19b, type19cUHF, type19cVHF, type19cELT, type19dS, type19dP, type19dD, type19dM, type19dJ, type19eJ, type19eL, type19eF, type19eU, type19eV, type19f_number, type19f_capacity, type19f_cover, type19f_colour, type19g, type19Remarks, type19i);
		vATS.msgSPL();
		return vATS.spl;
	}
	
	// Set Focus ATS Message Form
	// ALR
	public static void focus3aALR() { t3aALR.setFocus(); }
	public static void focus3bALR() { t3bALR.setFocus(); }
	public static void focus3cALR() { t3cALR.setFocus(); }
	public static void focus5aALR() { t5aALR.setFocus(); }
	public static void focus5bALR() { t5bALR.setFocus(); }
	public static void focus5cALR() { t5cALR.setFocus(); }
	public static void focus7aALR() { t7aALR.setFocus(); }
	public static void focus7cALR() { t7cALR.setFocus(); }
	public static void focus8aALR() { t8aALR.setFocus(); }
	public static void focus8bALR() { t8bALR.setFocus(); }
	public static void focus9aALR() { t9aALR.setFocus(); }
	public static void focus9bALR() { t9bALR.setFocus(); }
	public static void focus9cALR() { t9cALR.setFocus(); }
	public static void focus10aALR() { t10aALR.setFocus(); }
	public static void focus10bBaruALR() { t10bALR.setFocus(); }
	public static void focus13aALR() { t13aALR.setFocus(); }
	public static void focus13bALR() { t13bALR.setFocus(); }
	public static void focus15aALR() { t15aALR.setFocus(); }
	public static void focus15bALR() { t15bALR.setFocus(); }
	public static void focus15cALR() { t15cALR.setFocus(); }
	public static void focus16aALR() { t16aALR.setFocus(); }
	public static void focus16bALR() { t16bALR.setFocus(); }
	public static void focus16cALR() { t16cALR.setFocus(); }
	public static void focus16dALR() { t16dALR.setFocus(); }
	public static void focus18tNavALR() { tNavALR.setFocus(); }
	public static void focus18tComALR() { tComALR.setFocus(); }
	public static void focus18tDatALR() { tDatALR.setFocus(); }
	public static void focus18tSurALR() { tSurALR.setFocus(); }
	public static void focus18tDepALR() { tDepALR.setFocus(); }
	public static void focus18tDestALR() { tDestALR.setFocus(); }
	public static void focus18tDofALR() { tDofALR.setFocus(); }
	public static void focus18tRegALR() { tRegALR.setFocus(); }
	public static void focus18tEetALR() { tEetALR.setFocus(); }
	public static void focus18tSelALR() { tSelALR.setFocus(); }
	public static void focus18tTypALR() { tTypALR.setFocus(); }
	public static void focus18tCodeALR() { tCodeALR.setFocus(); }
	public static void focus18tDleALR() { tDleALR.setFocus(); }
	public static void focus18tOprALR() { tOprALR.setFocus(); }
	public static void focus18tOrgnALR() { tOrgnALR.setFocus(); }
	public static void focus18tPerALR() { tPerALR.setFocus(); }
	public static void focus18tAltnALR() { tAltnALR.setFocus(); }
	public static void focus18tRaltALR() { tRaltALR.setFocus(); }
	public static void focus18tTaltALR() { tTaltALR.setFocus(); }
	public static void focus18tRifALR() { tRifALR.setFocus(); }
	public static void focus18tRmkALR() { tRmkALR.setFocus(); }
	public static void focus18tPbnALR() { tPbnALR.setFocus(); }
	public static void focus18tStsALR() { tStsALR.setFocus(); }
	public static void focus19aALR() { t19aALR.setFocus(); }
	public static void focus19NumALR() { t19NumALR.setFocus(); }
	public static void focus19CapALR() { t19CapALR.setFocus(); }
	public static void focus20() { t20ALR.setFocus(); }
	// RCF
	public static void focus3aRCF() { t3aRCF.setFocus(); }
	public static void focus3bRCF() { t3bRCF.setFocus(); }
	public static void focus3cRCF() { t3cRCF.setFocus(); }
	public static void focus7aRCF() { t7aRCF.setFocus(); }
	public static void focus7cRCF() { t7cRCF.setFocus(); }
	public static void focus21() { t21RCF.setFocus(); }
	// FPL
	public static void focus3aFPL() { t3aFPL.setFocus(); }
	public static void focus3bFPL() { t3bFPL.setFocus(); }
	public static void focus3cFPL() { t3cFPL.setFocus(); }
	public static void focus7aFPL() { t7aFPL.setFocus(); }
	public static void focus7cFPL() { t7cFPL.setFocus(); }
	public static void focus8aFPL() { t8aFPL.setFocus(); }
	public static void focus8bFPL() { t8bFPL.setFocus(); }
	public static void focus9aFPL() { t9aFPL.setFocus(); }
	public static void focus9bFPL() { t9bFPL.setFocus(); }
	public static void focus9cFPL() { t9cFPL.setFocus(); }
	public static void focus10aFPL() { t10aFPL.setFocus(); }
	public static void focus10bBaruFPL() { t10bFPL.setFocus(); }
	public static void focus13aFPL() { t13aFPL.setFocus(); }
	public static void focus13bFPL() { t13bFPL.setFocus(); }
	public static void focus15aFPL() { t15aFPL.setFocus(); }
	public static void focus15bFPL() { t15bFPL.setFocus(); }
	public static void focus15cFPL() { t15cFPL.setFocus(); }
	public static void focus16aFPL() { t16aFPL.setFocus(); }
	public static void focus16bFPL() { t16bFPL.setFocus(); }
	public static void focus16cFPL() { t16cFPL.setFocus(); }
	public static void focus16dFPL() { t16dFPL.setFocus(); }
	public static void focus18tNavFPL() { tNavFPL.setFocus(); }
	public static void focus18tComFPL() { tComFPL.setFocus(); }
	public static void focus18tDatFPL() { tDatFPL.setFocus(); }
	public static void focus18tSurFPL() { tSurFPL.setFocus(); }
	public static void focus18tDepFPL() { tDepFPL.setFocus(); }
	public static void focus18tDestFPL() { tDestFPL.setFocus(); }
	public static void focus18tDofFPL() { tDofFPL.setFocus(); }
	public static void focus18tRegFPL() { tRegFPL.setFocus(); }
	public static void focus18tEetFPL() { tEetFPL.setFocus(); }
	public static void focus18tSelFPL() { tSelFPL.setFocus(); }
	public static void focus18tTypFPL() { tTypFPL.setFocus(); }
	public static void focus18tCodeFPL() { tCodeFPL.setFocus(); }
	public static void focus18tDleFPL() { tDleFPL.setFocus(); }
	public static void focus18tOprFPL() { tOprFPL.setFocus(); }
	public static void focus18tOrgnFPL() { tOrgnFPL.setFocus(); }
	public static void focus18tPerFPL() { tPerFPL.setFocus(); }
	public static void focus18tAltnFPL() { tAltnFPL.setFocus(); }
	public static void focus18tRaltFPL() { tRaltFPL.setFocus(); }
	public static void focus18tTaltFPL() { tTaltFPL.setFocus(); }
	public static void focus18tRifFPL() { tRifFPL.setFocus(); }
	public static void focus18tRmkFPL() { tRmkFPL.setFocus(); }
	public static void focus18tPbnFPL() { tPbnFPL.setFocus(); }
	public static void focus18tStsFPL() { tStsFPL.setFocus(); }
	public static void focus19aFPL() { t19aFPL.setFocus(); }
	public static void focus19NumFPL() { t19NumFPL.setFocus(); }
	public static void focus19CapFPL() { t19CapFPL.setFocus(); }
	// DLA
	public static void focus3aDLA() { t3aDLA.setFocus(); }
	public static void focus3bDLA() { t3bDLA.setFocus(); }
	public static void focus3cDLA() { t3cDLA.setFocus(); }
	public static void focus7aDLA() { t7aDLA.setFocus(); }
	public static void focus7cDLA() { t7cDLA.setFocus(); }
	public static void focus13aDLA() { t13aDLA.setFocus(); }
	public static void focus13bDLA() { t13bDLA.setFocus(); }
	public static void focus16aDLA() { t16aDLA.setFocus(); }
	public static void focus16bDLA() { t16bDLA.setFocus(); }
	public static void focus16cDLA() { t16cDLA.setFocus(); }
	public static void focus16dDLA() { t16dDLA.setFocus(); }
	public static void focus18tDofDLA() { tDofDLA.setFocus(); }
	// CHG
	public static void focus3aCHG() { t3aCHG.setFocus(); }
	public static void focus3bCHG() { t3bCHG.setFocus(); }
	public static void focus3cCHG() { t3cCHG.setFocus(); }
	public static void focus7aCHG() { t7aCHG.setFocus(); }
	public static void focus7cCHG() { t7cCHG.setFocus(); }
	public static void focus13aCHG() { t13aCHG.setFocus(); }
	public static void focus13bCHG() { t13bCHG.setFocus(); }
	public static void focus16aCHG() { t16aCHG.setFocus(); }
	public static void focus16bCHG() { t16bCHG.setFocus(); }
	public static void focus16cCHG() { t16cCHG.setFocus(); }
	public static void focus16dCHG() { t16dCHG.setFocus(); }
	public static void focus18tDofCHG() { tDofCHG.setFocus(); }
	public static void focus22CHG() { t22CHG.setFocus(); }
	// CNL
	public static void focus3aCNL() { t3aCNL.setFocus(); }
	public static void focus3bCNL() { t3bCNL.setFocus(); }
	public static void focus3cCNL() { t3cCNL.setFocus(); }
	public static void focus7aCNL() { t7aCNL.setFocus(); }
	public static void focus7cCNL() { t7cCNL.setFocus(); }
	public static void focus13aCNL() { t13aCNL.setFocus(); }
	public static void focus13bCNL() { t13bCNL.setFocus(); }
	public static void focus16aCNL() { t16aCNL.setFocus(); }
	public static void focus16bCNL() { t16bCNL.setFocus(); }
	public static void focus16cCNL() { t16cCNL.setFocus(); }
	public static void focus16dCNL() { t16dCNL.setFocus(); }
	public static void focus18tDofCNL() { tDofCNL.setFocus(); }
	// DEP
	public static void focus3aDEP() { t3aDEP.setFocus(); }
	public static void focus3bDEP() { t3bDEP.setFocus(); }
	public static void focus3cDEP() { t3cDEP.setFocus(); }
	public static void focus7aDEP() { t7aDEP.setFocus(); }
	public static void focus7cDEP() { t7cDEP.setFocus(); }
	public static void focus13aDEP() { t13aDEP.setFocus(); }
	public static void focus13bDEP() { t13bDEP.setFocus(); }
	public static void focus16aDEP() { t16aDEP.setFocus(); }
	public static void focus16bDEP() { t16bDEP.setFocus(); }
	public static void focus16cDEP() { t16cDEP.setFocus(); }
	public static void focus16dDEP() { t16dDEP.setFocus(); }
	public static void focus18tDofDEP() { tDofDEP.setFocus(); }
	// ARR
	public static void focus3aARR() { t3aARR.setFocus(); }
	public static void focus3bARR() { t3bARR.setFocus(); }
	public static void focus3cARR() { t3cARR.setFocus(); }
	public static void focus7aARR() { t7aARR.setFocus(); }
	public static void focus7cARR() { t7cARR.setFocus(); }
	public static void focus13aARR() { t13aARR.setFocus(); }
	public static void focus13bARR() { t13bARR.setFocus(); }
	public static void focus17a() { t17aARR.setFocus(); }
	public static void focus17b() { t17bARR.setFocus(); }
	public static void focus17c() { t17cARR.setFocus(); }
	// CDN
	public static void focus3aCDN() { t3aCDN.setFocus(); }
	public static void focus3bCDN() { t3bCDN.setFocus(); }
	public static void focus3cCDN() { t3cCDN.setFocus(); }
	public static void focus7aCDN() { t7aCDN.setFocus(); }
	public static void focus7cCDN() { t7cCDN.setFocus(); }
	public static void focus13aCDN() { t13aCDN.setFocus(); }
	public static void focus13bCDN() { t13bCDN.setFocus(); }
	public static void focus16aCDN() { t16aCDN.setFocus(); }
	public static void focus16bCDN() { t16bCDN.setFocus(); }
	public static void focus16cCDN() { t16cCDN.setFocus(); }
	public static void focus16dCDN() { t16dCDN.setFocus(); }
	public static void focus22CDN() { t22CDN.setFocus(); }
	// CPL
	public static void focus3aCPL() { t3aCPL.setFocus(); }
	public static void focus3bCPL() { t3bCPL.setFocus(); }
	public static void focus3cCPL() { t3cCPL.setFocus(); }
	public static void focus7aCPL() { t7aCPL.setFocus(); }
	public static void focus7cCPL() { t7cCPL.setFocus(); }
	public static void focus8aCPL() { t8aCPL.setFocus(); }
	public static void focus8bCPL() { t8bCPL.setFocus(); }
	public static void focus9aCPL() { t9aCPL.setFocus(); }
	public static void focus9bCPL() { t9bCPL.setFocus(); }
	public static void focus9cCPL() { t9cCPL.setFocus(); }
	public static void focus10aCPL() { t10aCPL.setFocus(); }
	public static void focus10bBaruCPL() { t10bCPL.setFocus(); }
	public static void focus13aCPL() { t13aCPL.setFocus(); }
	public static void focus13bCPL() { t13bCPL.setFocus(); }
	public static void focus14aCPL() { t14aCPL.setFocus(); }
	public static void focus14bCPL() { t14bCPL.setFocus(); }
	public static void focus14cCPL() { t14cCPL.setFocus(); }
	public static void focus14dCPL() { t14dCPL.setFocus(); }
	public static void focus14eCPL() { t14eCPL.setFocus(); }
	public static void focus16aCPL() { t16aCPL.setFocus(); }
	public static void focus16bCPL() { t16bCPL.setFocus(); }
	public static void focus16cCPL() { t16cCPL.setFocus(); }
	public static void focus16dCPL() { t16dCPL.setFocus(); }
	public static void focus15aCPL() { t15aCPL.setFocus(); }
	public static void focus15bCPL() { t15bCPL.setFocus(); }
	public static void focus15cCPL() { t15cCPL.setFocus(); }
	public static void focus18tNavCPL() { tNavCPL.setFocus(); }
	public static void focus18tComCPL() { tComCPL.setFocus(); }
	public static void focus18tDatCPL() { tDatCPL.setFocus(); }
	public static void focus18tSurCPL() { tSurCPL.setFocus(); }
	public static void focus18tDepCPL() { tDepCPL.setFocus(); }
	public static void focus18tDestCPL() { tDestCPL.setFocus(); }
	public static void focus18tDofCPL() { tDofCPL.setFocus(); }
	public static void focus18tRegCPL() { tRegCPL.setFocus(); }
	public static void focus18tEetCPL() { tEetCPL.setFocus(); }
	public static void focus18tSelCPL() { tSelCPL.setFocus(); }
	public static void focus18tTypCPL() { tTypCPL.setFocus(); }
	public static void focus18tCodeCPL() { tCodeCPL.setFocus(); }
	public static void focus18tDleCPL() { tDleCPL.setFocus(); }
	public static void focus18tOprCPL() { tOprCPL.setFocus(); }
	public static void focus18tOrgnCPL() { tOrgnCPL.setFocus(); }
	public static void focus18tPerCPL() { tPerCPL.setFocus(); }
	public static void focus18tAltnCPL() { tAltnCPL.setFocus(); }
	public static void focus18tRaltCPL() { tRaltCPL.setFocus(); }
	public static void focus18tTaltCPL() { tTaltCPL.setFocus(); }
	public static void focus18tRifCPL() { tRifCPL.setFocus(); }
	public static void focus18tRmkCPL() { tRmkCPL.setFocus(); }
	public static void focus18tPbnCPL() { tPbnCPL.setFocus(); }
	public static void focus18tStsCPL() { tStsCPL.setFocus(); }
	// EST
	public static void focus3aEST() { t3aEST.setFocus(); }
	public static void focus3bEST() { t3bEST.setFocus(); }
	public static void focus3cEST() { t3cEST.setFocus(); }
	public static void focus7aEST() { t7aEST.setFocus(); }
	public static void focus7cEST() { t7cEST.setFocus(); }
	public static void focus13aEST() { t13aEST.setFocus(); }
	public static void focus13bEST() { t13bEST.setFocus(); }
	public static void focus14aEST() { t14aEST.setFocus(); }
	public static void focus14bEST() { t14bEST.setFocus(); }
	public static void focus14cEST() { t14cEST.setFocus(); }
	public static void focus14dEST() { t14dEST.setFocus(); }
	public static void focus14eEST() { t14eEST.setFocus(); }
	public static void focus16aEST() { t16aEST.setFocus(); }
	public static void focus16bEST() { t16bEST.setFocus(); }
	public static void focus16cEST() { t16cEST.setFocus(); }
	public static void focus16dEST() { t16dEST.setFocus(); }
	// ACP
	public static void focus3aACP() { t3aACP.setFocus(); }
	public static void focus3bACP() { t3bACP.setFocus(); }
	public static void focus3cACP() { t3cACP.setFocus(); }
	public static void focus7aACP() { t7aACP.setFocus(); }
	public static void focus7cACP() { t7cACP.setFocus(); }
	public static void focus13aACP() { t13aACP.setFocus(); }
	public static void focus13bACP() { t13bACP.setFocus(); }
	public static void focus16aACP() { t16aACP.setFocus(); }
	public static void focus16bACP() { t16bACP.setFocus(); }
	public static void focus16cACP() { t16cACP.setFocus(); }
	public static void focus16dACP() { t16dACP.setFocus(); }
	// LAM
	public static void focus3aLAM() { t3aLAM.setFocus(); }
	public static void focus3bLAM() { t3bLAM.setFocus(); }
	public static void focus3cLAM() { t3cLAM.setFocus(); }
	// RQP
	public static void focus3aRQP() { t3aRQP.setFocus(); }
	public static void focus3bRQP() { t3bRQP.setFocus(); }
	public static void focus3cRQP() { t3cRQP.setFocus(); }
	public static void focus7aRQP() { t7aRQP.setFocus(); }
	public static void focus7cRQP() { t7cRQP.setFocus(); }
	public static void focus13aRQP() { t13aRQP.setFocus(); }
	public static void focus13bRQP() { t13bRQP.setFocus(); }
	public static void focus16aRQP() { t16aRQP.setFocus(); }
	public static void focus16bRQP() { t16bRQP.setFocus(); }
	public static void focus16cRQP() { t16cRQP.setFocus(); }
	public static void focus16dRQP() { t16dRQP.setFocus(); }
	public static void focus18tDofRQP() { tDofRQP.setFocus(); }
	// RQS
	public static void focus3aRQS() { t3aRQS.setFocus(); }
	public static void focus3bRQS() { t3bRQS.setFocus(); }
	public static void focus3cRQS() { t3cRQS.setFocus(); }
	public static void focus7aRQS() { t7aRQS.setFocus(); }
	public static void focus7cRQS() { t7cRQS.setFocus(); }
	public static void focus13aRQS() { t13aRQS.setFocus(); }
	public static void focus13bRQS() { t13bRQS.setFocus(); }
	public static void focus16aRQS() { t16aRQS.setFocus(); }
	public static void focus16bRQS() { t16bRQS.setFocus(); }
	public static void focus16cRQS() { t16cRQS.setFocus(); }
	public static void focus16dRQS() { t16dRQS.setFocus(); }
	public static void focus18tDofRQS() { tDofRQS.setFocus(); }
	// SPL
	public static void focus3aSPL() { t3aSPL.setFocus(); }
	public static void focus3bSPL() { t3bSPL.setFocus(); }
	public static void focus3cSPL() { t3cSPL.setFocus(); }
	public static void focus7aSPL() { t7aSPL.setFocus(); }
	public static void focus7cSPL() { t7cSPL.setFocus(); }
	public static void focus13aSPL() { t13aSPL.setFocus(); }
	public static void focus13bSPL() { t13bSPL.setFocus(); }
	public static void focus16aSPL() { t16aSPL.setFocus(); }
	public static void focus16bSPL() { t16bSPL.setFocus(); }
	public static void focus16cSPL() { t16cSPL.setFocus(); }
	public static void focus16dSPL() { t16dSPL.setFocus(); }
	public static void focus18tNavSPL() { tNavSPL.setFocus(); }
	public static void focus18tComSPL() { tComSPL.setFocus(); }
	public static void focus18tDatSPL() { tDatSPL.setFocus(); }
	public static void focus18tSurSPL() { tSurSPL.setFocus(); }
	public static void focus18tDepSPL() { tDepSPL.setFocus(); }
	public static void focus18tDestSPL() { tDestSPL.setFocus(); }
	public static void focus18tDofSPL() { tDofSPL.setFocus(); }
	public static void focus18tRegSPL() { tRegSPL.setFocus(); }
	public static void focus18tEetSPL() { tEetSPL.setFocus(); }
	public static void focus18tSelSPL() { tSelSPL.setFocus(); }
	public static void focus18tTypSPL() { tTypSPL.setFocus(); }
	public static void focus18tCodeSPL() { tCodeSPL.setFocus(); }
	public static void focus18tDleSPL() { tDleSPL.setFocus(); }
	public static void focus18tOprSPL() { tOprSPL.setFocus(); }
	public static void focus18tOrgnSPL() { tOrgnSPL.setFocus(); }
	public static void focus18tPerSPL() { tPerSPL.setFocus(); }
	public static void focus18tAltnSPL() { tAltnSPL.setFocus(); }
	public static void focus18tRaltSPL() { tRaltSPL.setFocus(); }
	public static void focus18tTaltSPL() { tTaltSPL.setFocus(); }
	public static void focus18tRifSPL() { tRifSPL.setFocus(); }
	public static void focus18tRmkSPL() { tRmkSPL.setFocus(); }
	public static void focus18tPbnSPL() { tPbnSPL.setFocus(); }
	public static void focus18tStsSPL() { tStsSPL.setFocus(); }
	public static void focus19aSPL() { t19aSPL.setFocus(); }
	public static void focus19NumSPL() { t19NumSPL.setFocus(); }
	public static void focus19CapSPL() { t19CapSPL.setFocus(); }

	// freetext
	public static String getFreeA() { return manual_ats; }
	// type3
	public static String get3a() { return type3a; }
	public static String get3b() { return type3b; }
	public static String get3c() { return type3c; }
	// type5
	public static String get5a() { return type5a; }
	public static String get5b() { return type5b; }
	public static String get5c() { return type5c; }
	// type7
	public static String get7a() { return type7a; }
	public static String get7b() { return type7b; }
	public static String get7c() { return type7c; }
	//public static String get7aARR() { return type7aARR; }
	// type8
	public static String get8a() { return type8a; }
	public static String get8b() { return type8b; }
	// type9
	public static String get9a() { return type9a; }
	public static String get9b() { return type9b; }
	public static String get9c() { return type9c; }
	// type10
	public static String get10a() { return type10a; }
	public static String get10b() { return type10b; }
	public static String get10bBaru() { return type10bBr; }
	// type13
	public static String get13a() { return type13a; }
	public static String get13b() { return type13b; }
	// type14
	public static String get14a() { return type14a; }
	public static String get14b() { return type14b; }
	public static String get14c() { return type14c; }
	public static String get14d() { return type14d; }
	public static String get14e() { return type14e; }
	// type15
	public static String get15a() { return type15a; }
	public static String get15b() { return type15b; }
	public static String get15c() { return type15c; }
	// type16
	public static String get16a() { return type16a; }
	public static String get16b() { return type16b; }
	public static String get16c() { return type16c; }
	public static String get16c2nd() { return type16c2nd; }
	// type17
	public static String get17a() { return type17a; }
	public static String get17b() { return type17b; }
	public static String get17c() { return type17c; }
	// type18
	public static String get18() { return type18; }
	public static String get18a() { return type18a; }
	public static String get18b() { return type18b; } // digunakan juga untuk DLA,CHG,CNL,DEP,RQP,RQS
	// type18baru -update 20Jan2011 untuk
	public static String get18tNav() { return type18tNav; }
	public static String get18tCom() { return type18tCom; }
	public static String get18tDat() { return type18tDat; }
	public static String get18tSur() { return type18tSur; }
	public static String get18tDep() { return type18tDep; }
	public static String get18tDest() { return type18tDest; }
	public static String get18tDof() { return type18tDof; }
	public static String get18tReg() { return type18tReg; }
	public static String get18tEet() { return type18tEet; }
	public static String get18tSel() { return type18tSel; }
	public static String get18tTyp() { return type18tTyp; }
	public static String get18tCode() { return type18tCode; }
	public static String get18tDle() { return type18tDle; }
	public static String get18tOpr() { return type18tOpr; }
	public static String get18tOrgn() { return type18tOrgn; }
	public static String get18tPer() { return type18tPer; }
	public static String get18tAltn() { return type18tAltn; }
	public static String get18tRalt() { return type18tRalt; }
	public static String get18tTalt() { return type18tTalt; }
	public static String get18tRif() { return type18tRif; }
	public static String get18tRmk() { return type18tRmk; }
	public static String get18tPbn() { return type18tPbn; }
	public static String get18tSts() { return type18tSts; }
	// type19
	public static String get19a() { return type19a; }
	public static String get19b() { return type19b; }
	// cUHF,cVHF,cELT
	public static String get19cUHF() { return type19cUHF; }
	public static String get19cVHF() { return type19cVHF; }
	public static String get19cELT() { return type19cELT; }
	// dSPDMJ
	public static String get19dS() {
		if ((!type19dP.equals("")) || (!type19dP.equals("")) || (!type19dP.equals("")) || (!type19dP.equals(""))) type19dS = "S";
		else type19dS="";
		return type19dS;
	}
	public static String get19dP() { return type19dP; }
	public static String get19dD() { return type19dD; }
	public static String get19dM() { return type19dM; }
	public static String get19dJ() { return type19dJ; }
	// eJLFUV
	public static String get19eJ() {
		if ((!type19eL.equals("")) || (!type19eF.equals("")) || (!type19eU.equals("")) || (!type19eV.equals(""))) type19eJ = "J";
		else type19eJ="";
		return type19eJ;
	}
	public static String get19eL() { return type19eL; }
	public static String get19eF() { return type19eF; }
	public static String get19eU() { return type19eU; }
	public static String get19eV() { return type19eV; }
	// fD,fNumber,fCapacity,fCover,fColour
	public static String get19fD() {
		if ((!type19f_number.equals("")) || (!type19f_capacity.equals(""))) type19fD = "D";
		else type19fD="";
		return type19fD;
	}
	public static String get19fNum() { return type19f_number; }
	public static String get19fCap() { return type19f_capacity; }
	public static String get19fCov() {
		//editcover
		//if (!type19f_colour.equals("")) type19f_cover = "C";
		//else type19f_cover="";
		System.out.println("type19f_cover:"+type19f_cover);
		return ATSForms.type19f_cover;
	}
	public static String get19fCol() { return type19f_colour; }
	// Aircraft,Remarks,Pilot
	public static String get19g() { return type19g; }
	public static String get19hN() {
		if (!type19Remarks.equals("")) type19hN = "N";
		else type19hN="";
		return type19hN;
	}
	public static String get19Rem() { return type19Remarks; }
	public static String get19i() { return type19i; }
	// type20
	public static String get20() { return type20; }
	// type21
	public static String get21() { return type21; }
	// type22
	public static String get22() { return type22; }
	// space reserved
	public static String getSpace() { return space_res; }
	public static String getFiled_by() { return filed_by; }
}