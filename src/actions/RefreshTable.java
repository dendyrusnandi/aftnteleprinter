package actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import setting.Shells;
import displays.OpenDialog;
import displays.OpenPassword;
import displays.PrinterSetting;
import displays.TeleSplashScreen2016IP;
import displays.forms.FormMini;
import displays.forms.FormTemplates;
import displays.forms.HeaderComposite;
import displays.forms.HeaderFooterPDF;
import displays.pid.setting;
import displays.pid.rs232ipsetup;
import displays.pid.shdisp;
import displays.pid.testmsg;
import displays.tables.TableATS;
import displays.tables.TableAbbreviations;
import displays.tables.TableQueueLP;
import displays.tables.TableWarning;
import displays.tables.TableRoute;
import displays.tables.TableTypeOfAircraft;
import displays.tables.TableLocInd;
import displays.tables.TableOutgoing;
import displays.tables.TableReg;

public class RefreshTable {

	public RefreshTable() {
		
		
	}
	
	// open : FORM SEARCH
//	public static void refreshSearchATS() {
//		if (Shells.shell_sats.isDisposed()) {
//			Shells.shell_sats = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//		}
//		if (!Shells.shell_sats.isVisible()) {
//			new FormSearch("ats");
//		} else {
//			Shells.shell_sats.close();
//			Shells.shell_sats = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//			new FormSearch("ats");
//		}	
//	}
	
//	public static void refreshSearchRoute() {
//		if (Shells.shell_sroute.isDisposed()) {
//			Shells.shell_sroute = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//		}
//		if (!Shells.shell_sroute.isVisible()) {
//			new FormSearch("route");
//		} else {
//			Shells.shell_sroute.close();
//			Shells.shell_sroute = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//			new FormSearch("route");
//		}
//	}	
    
//	public static void refreshSearchType9b() {
//		if (Shells.shell_stype9b.isDisposed()) {
//			Shells.shell_stype9b = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//		}
//		if (!Shells.shell_stype9b.isVisible()) {
//			new FormSearch("type9b");
//		} else {
//			Shells.shell_stype9b.close();
//			Shells.shell_stype9b = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//			new FormSearch("type9b");
//		}
//	}
    
//	public static void refreshSearchReg() {
//		if (Shells.shell_sreg.isDisposed()) {
//			Shells.shell_sreg = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//		}
//		if (!Shells.shell_sreg.isVisible()) {
//			new FormSearch("reg");
//		} else {
//			Shells.shell_sreg.close();
//			Shells.shell_sreg = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//			new FormSearch("reg");
//		}
//	}
    
//	public static void refreshSearchOutbox() {
//		if (Shells.shell_soutbox.isDisposed()) {
//			Shells.shell_soutbox = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//		}
//		if (!Shells.shell_soutbox.isVisible()) {
//			new FormSearch("outbox");
//		} else {
//			Shells.shell_soutbox.close();
//			Shells.shell_soutbox = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//			new FormSearch("outbox");
//		}
//	}
    
//	public static void refreshSearchAbbr() {
//		if (Shells.shell_sabbr.isDisposed()) {
//			Shells.shell_sabbr = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//		}
//		if (!Shells.shell_sabbr.isVisible()) {
//			new FormSearch("abbr");
//		} else {
//			Shells.shell_sabbr.close();
//			Shells.shell_sabbr = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//			new FormSearch("abbr");
//		}
//	}
    
//	public static void refreshSearchLocind() {
//		if (Shells.shell_slocind.isDisposed()) {
//			Shells.shell_slocind = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//		}
//		if (!Shells.shell_slocind.isVisible()) {
//			new FormSearch("locind");
//		} else {
//			Shells.shell_slocind.close();
//			Shells.shell_slocind = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//			new FormSearch("locind");
//		}
//	}
	
	// open : FORM NEW
//	public static void refreshNewRoute() {
//		if (Shells.sh_nroute.isDisposed()) {
//			Shells.sh_nroute = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//		}
//		if (!Shells.sh_nroute.isVisible()) {
//			new FormNew("route");
//		} else {
//			Shells.sh_nroute.close();
//			Shells.sh_nroute = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//			new FormNew("route");
//		}		
//	}
	
//	public static void refreshNewType9b() {
//		if (Shells.sh_ntype9b.isDisposed()) {
//			Shells.sh_ntype9b = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//		}
//		if (!Shells.sh_ntype9b.isVisible()) {
//			new FormNew("type9b");
//		} else {
//			Shells.sh_ntype9b.close();
//			Shells.sh_ntype9b = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//			new FormNew("type9b");
//		}
//	}
	
//	public static void refreshNewReg() {
//		if (Shells.sh_nreg.isDisposed()) {
//			Shells.sh_nreg = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//		}
//		if (!Shells.sh_nreg.isVisible()) {
//			new FormNew("reg");
//		} else {
//			Shells.sh_nreg.close();
//			Shells.sh_nreg = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//			new FormNew("reg");
//		}
//	}
	
//	public static void refreshNewLocind() {
//		if (Shells.sh_nlocind.isDisposed()) {
//			Shells.sh_nlocind = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//		}
//		if (!Shells.sh_nlocind.isVisible()) {
//			new FormNew("locind");
//		} else {
//			Shells.sh_nlocind.close();
//			Shells.sh_nlocind = new Shell(TeleSplashScreen.display, SWT.DIALOG_TRIM);
//			new FormNew("locind");
//		}		
//	}
    
	// open : FORM NEW TEMPLATE
	public static void refreshNewAMO(String ID, String idedit) {
		if (Shells.sh_nAMO.isDisposed()) {
			Shells.sh_nAMO = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nAMO.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nAMO, ID, idedit);
		} else {
			Shells.sh_nAMO.close();
			Shells.sh_nAMO = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nAMO, ID, idedit);
		}
	}
	
	public static void refreshNewFREE(String ID, String idedit) {
		if (Shells.sh_nFREE.isDisposed()) {
			Shells.sh_nFREE = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nFREE.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nFREE, ID, idedit);
		} else {
			Shells.sh_nFREE.close();
			Shells.sh_nFREE = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nFREE, ID, idedit);
		}
	}
	
	public static void refreshNewAFTN(String ID, String idedit) {
		if (Shells.sh_nAFTN.isDisposed()) {
			Shells.sh_nAFTN = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nAFTN.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nAFTN, ID, idedit);
		} else {
			Shells.sh_nAFTN.close();
			Shells.sh_nAFTN = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nAFTN, ID, idedit);
		}
	}
    
	public static void refreshNewALR(String ID, String idedit) {
		if (Shells.sh_nALR.isDisposed()) {
			Shells.sh_nALR = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nALR.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nALR, ID, idedit);
		} else {
			Shells.sh_nALR.close();
			Shells.sh_nALR = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nALR, ID, idedit);
		}
	}
    
	public static void refreshNewRCF(String ID, String idedit) {
		if (Shells.sh_nRCF.isDisposed()) {
			Shells.sh_nRCF = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nRCF.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nRCF, ID, idedit);
		} else {
			Shells.sh_nRCF.close();
			Shells.sh_nRCF = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nRCF, ID, idedit);
		}
	}
	
	public static void refreshNewFPL(String ID, String idedit) {
		if (Shells.sh_nFPL.isDisposed()) {
			Shells.sh_nFPL = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nFPL.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nFPL, ID, idedit);
			if (FormTemplates.ID.compareToIgnoreCase("NewFPL")==0) {
				HeaderComposite.btnDLA.setEnabled(false);
				HeaderComposite.btnCHG.setEnabled(false);
				HeaderComposite.btnCNL.setEnabled(false);
				HeaderComposite.btnDEP.setEnabled(false);
				HeaderComposite.btnARR.setEnabled(false);
			}
		} else {
			Shells.sh_nFPL.close();
			Shells.sh_nFPL = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nFPL, ID, idedit);
			if (FormTemplates.ID.compareToIgnoreCase("NewFPL")==0) {
				HeaderComposite.btnDLA.setEnabled(false);
				HeaderComposite.btnCHG.setEnabled(false);
				HeaderComposite.btnCNL.setEnabled(false);
				HeaderComposite.btnDEP.setEnabled(false);
				HeaderComposite.btnARR.setEnabled(false);
			}
		}
	}
    
	public static void refreshNewDLA(String ID, String idedit) {
		if (Shells.sh_nDLA.isDisposed()) {
			Shells.sh_nDLA = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nDLA.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nDLA, ID, idedit);
		} else {
			Shells.sh_nDLA.close();
			Shells.sh_nDLA = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nDLA, ID, idedit);
		}
	}
    
	public static void refreshNewCHG(String ID, String idedit) {
		if (Shells.sh_nCHG.isDisposed()) {
			Shells.sh_nCHG = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nCHG.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nCHG, ID, idedit);
		} else {
			Shells.sh_nCHG.close();
			Shells.sh_nCHG = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nCHG, ID, idedit);
		}
	}
    
	public static void refreshNewCNL(String ID, String idedit) {
		if (Shells.sh_nCNL.isDisposed()) {
			Shells.sh_nCNL = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nCNL.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nCNL, ID, idedit);
		} else {
			Shells.sh_nCNL.close();
			Shells.sh_nCNL = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nCNL, ID, idedit);
		}
	}
    
	public static void refreshNewDEP(String ID, String idedit) {
		if (Shells.sh_nDEP.isDisposed()) {
			Shells.sh_nDEP = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nDEP.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nDEP, ID, idedit);
		} else {
			Shells.sh_nDEP.close();
			Shells.sh_nDEP = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nDEP, ID, idedit);
		}
	}
    
	public static void refreshNewARR(String ID, String idedit) {
		if (Shells.sh_nARR.isDisposed()) {
			Shells.sh_nARR = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nARR.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nARR, ID, idedit);
		} else {
			Shells.sh_nARR.close();
			Shells.sh_nARR = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nARR, ID, idedit);
		}
	}
    
	public static void refreshNewCDN(String ID, String idedit) {
		if (Shells.sh_nCDN.isDisposed()) {
			Shells.sh_nCDN = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nCDN.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nCDN, ID, idedit);
		} else {
			Shells.sh_nCDN.close();
			Shells.sh_nCDN = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nCDN, ID, idedit);
		}
	}
    
	public static void refreshNewCPL(String ID, String idedit) {
		if (Shells.sh_nCPL.isDisposed()) {
			Shells.sh_nCPL = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nCPL.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nCPL, ID, idedit);
		} else {
			Shells.sh_nCPL.close();
			Shells.sh_nCPL = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nCPL, ID, idedit);
		}
	}
    
	public static void refreshNewEST(String ID, String idedit) {
		if (Shells.sh_nEST.isDisposed()) {
			Shells.sh_nEST = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nEST.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nEST, ID, idedit);
		} else {
			Shells.sh_nEST.close();
			Shells.sh_nEST = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nEST, ID, idedit);
		}
	}
    
	public static void refreshNewACP(String ID, String idedit) {
		if (Shells.sh_nACP.isDisposed()) {
			Shells.sh_nACP = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nACP.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nACP, ID, idedit);
		} else {
			Shells.sh_nACP.close();
			Shells.sh_nACP = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nACP, ID, idedit);
		}
	}
    
	public static void refreshNewLAM(String ID, String idedit) {
		if (Shells.sh_nLAM.isDisposed()) {
			Shells.sh_nLAM = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nLAM.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nLAM, ID, idedit);
		} else {
			Shells.sh_nLAM.close();
			Shells.sh_nLAM = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nLAM, ID, idedit);
		}
	}
    
	public static void refreshNewRQP(String ID, String idedit) {
		if (Shells.sh_nRQP.isDisposed()) {
			Shells.sh_nRQP = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nRQP.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nRQP, ID, idedit);
		} else {
			Shells.sh_nRQP.close();
			Shells.sh_nRQP = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nRQP, ID, idedit);
		}
	}
    
	public static void refreshNewRQS(String ID, String idedit) {
		if (Shells.sh_nRQS.isDisposed()) {
			Shells.sh_nRQS = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nRQS.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nRQS, ID, idedit);
		} else {
			Shells.sh_nRQS.close();
			Shells.sh_nRQS = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nRQS, ID, idedit);
		}
	}
    
	public static void refreshNewSPL(String ID, String idedit) {
		if (Shells.sh_nSPL.isDisposed()) {
			Shells.sh_nSPL = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_nSPL.isVisible()) {
			new FormTemplates().newtemp(Shells.sh_nSPL, ID, idedit);
		} else {
			Shells.sh_nSPL.close();
			Shells.sh_nSPL = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormTemplates().newtemp(Shells.sh_nSPL, ID, idedit);
		}
	}
	

    
	public static void refreshPriority(Text t) {
		if (Shells.sh_mPriority.isDisposed()) {
			Shells.sh_mPriority = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_mPriority.isVisible()) {
			new FormMini("priority", t);
		} else {
			Shells.sh_mPriority.close();
			Shells.sh_mPriority = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormMini("priority", t);
		}
	}
	
	// MAINTENANCE
	public static void refreshDelTbl() {
		if (Shells.sh_mDeltbl.isDisposed()) {
			Shells.sh_mDeltbl = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM); 
		}
		if (!Shells.sh_mDeltbl.isVisible()) {
			new FormMini("deltbl", FormMini.tDelTbl);
		} else {
			Shells.sh_mDeltbl.close();
			Shells.sh_mDeltbl = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormMini("deltbl", FormMini.tDelTbl);
		}
	}
    
	// OTHER
	public static void refresh10a(Text t) {
		if (Shells.sh_m10a.isDisposed()) {
			Shells.sh_m10a = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_m10a.isVisible()) {
			new FormMini("10a", t);
		} else {
			Shells.sh_m10a.close();
			Shells.sh_m10a = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormMini("10a", t);
		}
	}
	
	public static void refresh10b(Text t) {
		if (Shells.sh_m10b.isDisposed()) {
			Shells.sh_m10b = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_m10b.isVisible()) {
			new FormMini("10b", t);
		} else {
			Shells.sh_m10b.close();
			Shells.sh_m10b = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormMini("10b", t);
		}
	}
	
	public static void refreshPBN(Text t) {
		if (Shells.sh_mPBN.isDisposed()) {
			Shells.sh_mPBN = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_mPBN.isVisible()) {
			new FormMini("pbn", t);
		} else {
			Shells.sh_mPBN.close();
			Shells.sh_mPBN = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormMini("pbn", t);
		}
	}
	
	public static void refreshSTS(Text t) {
		if (Shells.sh_mSTS.isDisposed()) {
			Shells.sh_mSTS = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_mSTS.isVisible()) {
			new FormMini("sts", t);
		} else {
			Shells.sh_mSTS.close();
			Shells.sh_mSTS = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormMini("sts", t);
		}
	}
    
	public static void refresh5a(Text t) {
		if (Shells.sh_m5a.isDisposed()) {
			Shells.sh_m5a = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_m5a.isVisible()) {
			new FormMini("5a", t);
		} else {
			Shells.sh_m5a.close();
			Shells.sh_m5a = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormMini("5a", t);
		}
	}
    
	public static void refresh8a(Text t) {
		if (Shells.sh_m8a.isDisposed()) {
			Shells.sh_m8a = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_m8a.isVisible()) {
			new FormMini("8a", t);
		} else {
			Shells.sh_m8a.close();
			Shells.sh_m8a = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormMini("8a", t);
		}
	}
    
	public static void refresh8b(Text t) {
		if (Shells.sh_m8b.isDisposed()) {
			Shells.sh_m8b = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_m8b.isVisible()) {
			new FormMini("8b", t);
		} else {
			Shells.sh_m8b.close();
			Shells.sh_m8b = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormMini("8b", t);
		}
	}
    
	public static void refreshBaudrate(Text t) {
		if (Shells.sh_mBaudrate.isDisposed()) {
			Shells.sh_mBaudrate = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_mBaudrate.isVisible()) {
			new FormMini("baudrate", t);
		} else {
			Shells.sh_mBaudrate.close();
			Shells.sh_mBaudrate = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormMini("baudrate", t);
		}
	}
    
	public static void refresh9c(Text t) {
		if (Shells.sh_m9c.isDisposed()) {
			Shells.sh_m9c = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_m9c.isVisible()) {
			new FormMini("9c", t);
		} else {
			Shells.sh_m9c.close();
			Shells.sh_m9c = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new FormMini("9c", t);
		}
	}
    
	public static void refreshPDFSetting() {
		if (Shells.sh_pdfSetting.isDisposed()) {
			Shells.sh_pdfSetting = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_pdfSetting.isVisible()) {
			new HeaderFooterPDF(Shells.sh_pdfSetting);
		} else {
			Shells.sh_pdfSetting.close();
			Shells.sh_pdfSetting = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new HeaderFooterPDF(Shells.sh_pdfSetting);
		}
	}
    
	// open : FORM TABLE
	public static void refreshTableAFTN(String pDate,String pDateTo,String pCid,String pSeqFr,String pSeqTo,String pMsgAll,String pFiled,
			String pIO) {
		if (Shells.sh_tAFTN.isDisposed()) {
			Shells.sh_tAFTN = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_tAFTN.isVisible()) {
			new TableATS().koneksiDB(Shells.sh_tAFTN, pDate, pDateTo, pCid, pSeqFr, pSeqTo, pMsgAll, pFiled, pIO);
		} else {
			Shells.sh_tAFTN.close();
			Shells.sh_tAFTN = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new TableATS().koneksiDB(Shells.sh_tAFTN, pDate, pDateTo, pCid, pSeqFr, pSeqTo, pMsgAll, pFiled, pIO);
		}
	}
	
	public static void refreshTableRoute(String t13a, String t16a) {
		if (Shells.sh_tRoute.isDisposed()) {
			Shells.sh_tRoute = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_tRoute.isVisible()) {
			new TableRoute().koneksiDB(Shells.sh_tRoute,t13a,t16a);
		} else {
			Shells.sh_tRoute.close();
			Shells.sh_tRoute = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new TableRoute().koneksiDB(Shells.sh_tRoute,t13a,t16a);
		}
	}
	
	public static void refreshTableType9b(String sToa, String sWtc) {
		if (Shells.sh_tType9b.isDisposed()) {
			Shells.sh_tType9b = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_tType9b.isVisible()) {
			new TableTypeOfAircraft().koneksiDB(Shells.sh_tType9b, sToa, sWtc);
		} else {
			Shells.sh_tType9b.close();
			Shells.sh_tType9b = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new TableTypeOfAircraft().koneksiDB(Shells.sh_tType9b, sToa, sWtc);
		}
	}

	public static void TestMsgAction() {
		if (Shells.sh_pidTest.isDisposed()) {
			Shells.sh_pidTest = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_pidTest.isVisible()) {
			new testmsg(Shells.sh_pidTest);
		} else {
			Shells.sh_pidTest.close();
			Shells.sh_pidTest = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new testmsg(Shells.sh_pidTest);
		}
	}
	
	public static void CommSetupAction() {
		if (!Shells.sh_pidSetting.isDisposed()) Shells.sh_pidSetting.close();
		Shells.sh_pidSetting = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		OpenPassword pass = new OpenPassword();
		if (pass.open(Shells.sh_pidSetting)) {
			if (!Shells.sh_pidSetting.isDisposed()) Shells.sh_pidSetting.close();
			Shells.sh_pidSetting = new Shell(shdisp.getDisplay(),SWT.MIN);
			//new setting(Shells.sh_pidSetting).open();
			new rs232ipsetup(Shells.sh_pidSetting).open();
		}
	}
	
	public static void reboot() {
		if (!Shells.sh_Reboot.isDisposed()) Shells.sh_Reboot.close();
		Shells.sh_Reboot = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		OpenDialog pass = new OpenDialog();
		if (pass.open(Shells.sh_Reboot,"Reboot")) {
			if (!Shells.sh_Reboot.isDisposed()) Shells.sh_Reboot.close();
			Shells.sh_Reboot = new Shell(shdisp.getDisplay(),SWT.MIN);
		}
	}
	
	public static void shutdown() {
		if (!Shells.sh_Shutdown.isDisposed()) Shells.sh_Shutdown.close();
		Shells.sh_Shutdown = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		OpenDialog pass = new OpenDialog();
		if (pass.open(Shells.sh_Shutdown,"Shutdown")) {
			if (!Shells.sh_Shutdown.isDisposed()) Shells.sh_Shutdown.close();
			Shells.sh_Shutdown = new Shell(shdisp.getDisplay(),SWT.MIN);
		}
	}
	
	public static void refreshTableReg(String sToA, String sReg) {
		if (Shells.sh_tReg.isDisposed()) {
			Shells.sh_tReg = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_tReg.isVisible()) {
			new TableReg().koneksiDB(Shells.sh_tReg, sToA, sReg);
		} else {
			Shells.sh_tReg.close();
			Shells.sh_tReg = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new TableReg().koneksiDB(Shells.sh_tReg, sToA, sReg);
		}
	}
	
	public static void refreshTableOutbox(String sUser) {
		if (Shells.sh_tOutbox.isDisposed()) {
			Shells.sh_tOutbox = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_tOutbox.isVisible()) {
			new TableOutgoing().koneksiDB(Shells.sh_tOutbox, sUser);
		} else {
			Shells.sh_tOutbox.close();
			Shells.sh_tOutbox = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new TableOutgoing().koneksiDB(Shells.sh_tOutbox, sUser);
		}
	}
	
	public static void refreshTableWarning(String sMessage,String sReason,String sTgl,String sTglTo) {
		if (Shells.sh_tWarning.isDisposed()) {
			Shells.sh_tWarning = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_tWarning.isVisible()) {
			new TableWarning().koneksiDB(Shells.sh_tWarning, sMessage, sReason, sTgl, sTglTo);
		} else {
			Shells.sh_tWarning.close();
			Shells.sh_tWarning = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new TableWarning().koneksiDB(Shells.sh_tWarning, sMessage, sReason, sTgl, sTglTo);
		}
	}
	
	public static void refreshTableQueue(String sData, String sDate, String sDateTo) {
		if (Shells.sh_tQueue.isDisposed()) {
			Shells.sh_tQueue = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_tQueue.isVisible()) {
			new TableQueueLP().koneksiDB(Shells.sh_tQueue, sData, sDate, sDateTo);
		} else {
			Shells.sh_tQueue.close();
			Shells.sh_tQueue = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new TableQueueLP().koneksiDB(Shells.sh_tQueue, sData, sDate, sDateTo);
		}
	}
	
	public static void refreshPrinter() {
		if (Shells.sh_mPrinter.isDisposed()) {
			Shells.sh_mPrinter = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_mPrinter.isVisible()) {
			new PrinterSetting(Shells.sh_mPrinter);
		} else {
			Shells.sh_mPrinter.close();
			Shells.sh_mPrinter = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new PrinterSetting(Shells.sh_mPrinter);
		}
	}
	
	public static void refreshCalendar() {
		if (Shells.sh_mCalendar.isDisposed()) {
			Shells.sh_mCalendar = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_mCalendar.isVisible()) {
			new Calendar(Shells.sh_mCalendar);
		} else {
			Shells.sh_mCalendar.close();
			Shells.sh_mCalendar = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new Calendar(Shells.sh_mCalendar);
		}
	}
	
	public static void refreshTableAbbr(String sAbbr, String sMean) {
		if (Shells.sh_tAbbr.isDisposed()) {
			Shells.sh_tAbbr = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_tAbbr.isVisible()) {
			new TableAbbreviations().koneksiDB(Shells.sh_tAbbr, sAbbr, sMean);
		} else {
			Shells.sh_tAbbr.close();
			Shells.sh_tAbbr = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new TableAbbreviations().koneksiDB(Shells.sh_tAbbr, sAbbr, sMean);
		}
	}
	
	public static void refreshTableLocInd(String sInd, String sLoc) {
		if (Shells.sh_tLocind.isDisposed()) {
			Shells.sh_tLocind = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
		}
		if (!Shells.sh_tLocind.isVisible()) {
			new TableLocInd().koneksiDB(Shells.sh_tLocind, sInd, sLoc);
		} else {
			Shells.sh_tLocind.close();
			Shells.sh_tLocind = new Shell(TeleSplashScreen2016IP.display, SWT.DIALOG_TRIM);
			new TableLocInd().koneksiDB(Shells.sh_tLocind, sInd, sLoc);
		}
	}
	
}
