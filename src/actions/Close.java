package actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import displays.MainForm;
import displays.TeleSplashScreen2016IP;
import readwrite.ReadFromFile;
import setting.Colors;
import setting.Shells;

public class Close {
    static String data="";
    
    public Close() { }
    
    public Close(String sData) {
    	data = sData;
    	System.out.println("\nID Close=" + data);

    	if (data.endsWith("AMO")) Shells.sh_nAMO.close();  
    	else if (data.endsWith("FREE")) Shells.sh_nFREE.close();  
    	else if (data.endsWith("AFTN")) {
    		Shells.sh_nAFTN.close();
    		
    		String telestat = TeleSplashScreen2016IP.telestat;//ReadFromFile.ReadInputFile1("/tp/template/telestat.txt");
    		System.err.println(telestat);
    		if (telestat.isEmpty()) telestat="1";
    		if (telestat.compareTo("1")==0) {
//    			MainForm.rerun();
    		} else if (telestat.compareTo("0")==0) {
    			MainForm.rerun();
    		}
    	}
 	   	else if (data.endsWith("ALR")) Shells.sh_nALR.close();
	    else if (data.endsWith("RCF")) Shells.sh_nRCF.close(); 
        else if (data.endsWith("FPL")) Shells.sh_nFPL.close(); 
        else if (data.endsWith("DLA")) Shells.sh_nDLA.close();  
        else if (data.endsWith("CHG")) Shells.sh_nCHG.close();  
        else if (data.endsWith("CNL")) Shells.sh_nCNL.close();  
        else if (data.endsWith("DEP")) Shells.sh_nDEP.close();  
        else if (data.endsWith("ARR")) Shells.sh_nARR.close();  
        else if (data.endsWith("CDN")) Shells.sh_nCDN.close();  
        else if (data.endsWith("CPL")) Shells.sh_nCPL.close();  
        else if (data.endsWith("EST")) Shells.sh_nEST.close();  
        else if (data.endsWith("ACP")) Shells.sh_nACP.close();  
        else if (data.endsWith("LAM")) Shells.sh_nLAM.close();  
        else if (data.endsWith("RQP")) Shells.sh_nRQP.close();  
        else if (data.endsWith("RQS")) Shells.sh_nRQS.close();  
        else if (data.endsWith("SPL")) Shells.sh_nSPL.close();  
	    
	    closeMiniforms();
    }

    public static void closeMiniforms() {
		if (!Shells.sh_m5a.isDisposed()) Shells.sh_m5a.close();
		if (!Shells.sh_m8a.isDisposed()) Shells.sh_m8a.close();
		if (!Shells.sh_m8b.isDisposed()) Shells.sh_m8b.close();
		if (!Shells.sh_m9c.isDisposed()) Shells.sh_m9c.close();
		if (!Shells.sh_m10a.isDisposed()) Shells.sh_m10a.close();
		if (!Shells.sh_m10b.isDisposed()) Shells.sh_m10b.close();
		if (!Shells.sh_mPBN.isDisposed()) Shells.sh_mPBN.close();
		if (!Shells.sh_mSTS.isDisposed()) Shells.sh_mSTS.close();
	}
	
    public static void closeWindows() {
		if (!Shells.sh_tAFTN.isDisposed()) Shells.sh_tAFTN.close();
		if (!Shells.sh_tRoute.isDisposed()) Shells.sh_tRoute.close();
		if (!Shells.sh_tType9b.isDisposed()) Shells.sh_tType9b.close();
		if (!Shells.sh_tReg.isDisposed()) Shells.sh_tReg.close();
		if (!Shells.sh_tOutbox.isDisposed()) Shells.sh_tOutbox.close();
		if (!Shells.sh_tAbbr.isDisposed()) Shells.sh_tAbbr.close();
		if (!Shells.sh_tLocind.isDisposed()) Shells.sh_tLocind.close();
		if (!Shells.sh_tWarning.isDisposed()) Shells.sh_tWarning.close();
		if (!Shells.sh_tQueue.isDisposed()) Shells.sh_tQueue.close();
		
		closeMiniforms();
		
		if (!Shells.sh_mDeltbl.isDisposed()) Shells.sh_mDeltbl.close();
		if (!Shells.sh_mPrinter.isDisposed()) Shells.sh_mPrinter.close();
		if (!Shells.sh_mCalendar.isDisposed()) Shells.sh_mCalendar.close();

		if (!Shells.sh_pidTest.isDisposed()) Shells.sh_pidTest.close();
		if (!Shells.sh_pidSetting.isDisposed()) Shells.sh_pidSetting.close();
		if (!Shells.sh_setupIP.isDisposed()) Shells.sh_setupIP.close();
		if (!Shells.sh_setupAFTN.isDisposed()) Shells.sh_setupAFTN.close();
		if (!Shells.sh_pdfSetting.isDisposed()) Shells.sh_pdfSetting.close();

		if (!Shells.sh_Reboot.isDisposed()) Shells.sh_Reboot.close();
		if (!Shells.sh_Shutdown.isDisposed()) Shells.sh_Shutdown.close();
		
		if (!Shells.sh_nAMO.isDisposed()) Shells.sh_nAMO.close();
		if (!Shells.sh_nFREE.isDisposed()) Shells.sh_nFREE.close();
		if (!Shells.sh_nAFTN.isDisposed()) Shells.sh_nAFTN.close();
		if (!Shells.sh_nALR.isDisposed()) Shells.sh_nALR.close();
		if (!Shells.sh_nRCF.isDisposed()) Shells.sh_nRCF.close();
		if (!Shells.sh_nFPL.isDisposed()) Shells.sh_nFPL.close();
		if (!Shells.sh_nDLA.isDisposed()) Shells.sh_nDLA.close();
		if (!Shells.sh_nCHG.isDisposed()) Shells.sh_nCHG.close();
		if (!Shells.sh_nCNL.isDisposed()) Shells.sh_nCNL.close();
		if (!Shells.sh_nDEP.isDisposed()) Shells.sh_nDEP.close();
		if (!Shells.sh_nARR.isDisposed()) Shells.sh_nARR.close();
		if (!Shells.sh_nCDN.isDisposed()) Shells.sh_nCDN.close();
		if (!Shells.sh_nCPL.isDisposed()) Shells.sh_nCPL.close();
		if (!Shells.sh_nEST.isDisposed()) Shells.sh_nEST.close();
		if (!Shells.sh_nACP.isDisposed()) Shells.sh_nACP.close();
		if (!Shells.sh_nLAM.isDisposed()) Shells.sh_nLAM.close();
		if (!Shells.sh_nRQP.isDisposed()) Shells.sh_nRQP.close();
		if (!Shells.sh_nRQS.isDisposed()) Shells.sh_nRQS.close();
		if (!Shells.sh_nSPL.isDisposed()) Shells.sh_nSPL.close();

		if (!Shells.sh_vAFTN.isDisposed()) Shells.sh_vAFTN.close();
		if (!Shells.sh_vALR.isDisposed()) Shells.sh_vALR.close();
		if (!Shells.sh_vRCF.isDisposed()) Shells.sh_vRCF.close();
		if (!Shells.sh_vFPL.isDisposed()) Shells.sh_vFPL.close();
		if (!Shells.sh_vDLA.isDisposed()) Shells.sh_vDLA.close();
		if (!Shells.sh_vCHG.isDisposed()) Shells.sh_vCHG.close();
		if (!Shells.sh_vCNL.isDisposed()) Shells.sh_vCNL.close();
		if (!Shells.sh_vDEP.isDisposed()) Shells.sh_vDEP.close();
		if (!Shells.sh_vARR.isDisposed()) Shells.sh_vARR.close();
		if (!Shells.sh_vCDN.isDisposed()) Shells.sh_vCDN.close();
		if (!Shells.sh_vCPL.isDisposed()) Shells.sh_vCPL.close();
		if (!Shells.sh_vEST.isDisposed()) Shells.sh_vEST.close();
		if (!Shells.sh_vACP.isDisposed()) Shells.sh_vACP.close();
		if (!Shells.sh_vLAM.isDisposed()) Shells.sh_vLAM.close();
		if (!Shells.sh_vRQP.isDisposed()) Shells.sh_vRQP.close();
		if (!Shells.sh_vRQS.isDisposed()) Shells.sh_vRQS.close();
		if (!Shells.sh_vSPL.isDisposed()) Shells.sh_vSPL.close();
	}
}
