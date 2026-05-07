package actions;

import org.eclipse.swt.widgets.Button;

import displays.forms.ATSForms;
import displays.forms.HeaderComposite;


public class Discard {
    String data="";
    
    public static void amo() { ATSForms.discardAMO(); }
    public static void free() { ATSForms.discardFREE(); }
    
    public static void aftn() {
    	HeaderComposite.discardHeaderAFTN(); 
		ATSForms.discardAFTN();
    }
    
    public static void alr() {
    	HeaderComposite.discardHeaderALR(); 
		ATSForms.discardALR(); 
		discardNavBtn(ATSForms.firstALR,ATSForms.prevALR,ATSForms.nextALR,ATSForms.lastALR);	
    }
    
    public static void rcf() {
    	HeaderComposite.discardHeaderRCF(); 
		ATSForms.discardRCF();
    }
    
    public static void fpl() {
    	HeaderComposite.discardHeaderFPL(); 
		ATSForms.discardFPL();  
		discardNavBtn(ATSForms.firstFPL,ATSForms.prevFPL,ATSForms.nextFPL,ATSForms.lastFPL);
    }
    
    public static void dla() {
    	HeaderComposite.discardHeaderDLA(); 
    	ATSForms.discardDLA(); 
		discardNavBtn(ATSForms.firstDLA,ATSForms.prevDLA,ATSForms.nextDLA,ATSForms.lastDLA);
    }
    
    public static void chg() {
    	HeaderComposite.discardHeaderCHG();
    	ATSForms.discardCHG(); 
		discardNavBtn(ATSForms.firstCHG,ATSForms.prevCHG,ATSForms.nextCHG,ATSForms.lastCHG);
    }
    
    public static void cnl() {
    	HeaderComposite.discardHeaderCNL();
    	ATSForms.discardCNL();  
		discardNavBtn(ATSForms.firstCNL,ATSForms.prevCNL,ATSForms.nextCNL,ATSForms.lastCNL);
    }
    
    public static void dep() {
    	HeaderComposite.discardHeaderDEP(); 
    	ATSForms.discardDEP(); 
		discardNavBtn(ATSForms.firstDEP,ATSForms.prevDEP,ATSForms.nextDEP,ATSForms.lastDEP);
    }
    
    public static void arr() {
    	HeaderComposite.discardHeaderARR(); 
    	ATSForms.discardARR(); 
		discardNavBtn(ATSForms.firstARR,ATSForms.prevARR,ATSForms.nextARR,ATSForms.lastARR);
    }
    
    public static void cdn() {
    	HeaderComposite.discardHeaderCDN(); 
    	ATSForms.discardCDN(); 
		discardNavBtn(ATSForms.firstCDN,ATSForms.prevCDN,ATSForms.nextCDN,ATSForms.lastCDN);
    }
    
    public static void cpl() {
    	HeaderComposite.discardHeaderCPL(); 
    	ATSForms.discardCPL(); 
		discardNavBtn(ATSForms.firstCPL,ATSForms.prevCPL,ATSForms.nextCPL,ATSForms.lastCPL);
    }
    
    public static void est() {
    	HeaderComposite.discardHeaderEST(); 
    	ATSForms.discardEST(); 
		discardNavBtn(ATSForms.firstEST,ATSForms.prevEST,ATSForms.nextEST,ATSForms.lastEST);
    }
    
    public static void acp() {
    	HeaderComposite.discardHeaderACP(); 
    	ATSForms.discardACP(); 
		discardNavBtn(ATSForms.firstACP,ATSForms.prevACP,ATSForms.nextACP,ATSForms.lastACP);
    }
    
    public static void lam() {
    	HeaderComposite.discardHeaderLAM(); 
    	ATSForms.discardLAM();
    }
    
    public static void rqp() {
    	HeaderComposite.discardHeaderRQP();
    	ATSForms.discardRQP();  
    }
    
    public static void rqs() {
    	HeaderComposite.discardHeaderRQS();
    	ATSForms.discardRQS(); 
		discardNavBtn(ATSForms.firstRQS,ATSForms.prevRQS,ATSForms.nextRQS,ATSForms.lastRQS);
    }
    
    public static void spl() {
    	HeaderComposite.discardHeaderSPL();
    	ATSForms.discardSPL();  
		discardNavBtn(ATSForms.firstSPL,ATSForms.prevSPL,ATSForms.nextSPL,ATSForms.lastSPL);
    }
    
    public Discard(String sData) {
        data = sData;
    	System.out.println("\nID Discard=" + data);
    	
    	if (data.endsWith("AMO")) { amo(); } 
    	else if (data.endsWith("FREE")) { free(); } 
        else if (data.endsWith("AFTN")) { aftn(); } 
    	else if (data.endsWith("ALR")) { alr(); } 
    	else if (data.endsWith("RCF")) { rcf(); } 
    	else if (data.endsWith("FPL")) { fpl(); } 
    	else if (data.endsWith("DLA")) { dla(); } 
    	else if (data.endsWith("CHG")) { chg(); } 
    	else if (data.endsWith("CNL")) { cnl(); } 
    	else if (data.endsWith("DEP")) { dep(); } 
    	else if (data.endsWith("ARR")) { arr(); } 
    	else if (data.endsWith("CDN")) { cdn(); } 
    	else if (data.endsWith("CPL")) { cpl(); } 
    	else if (data.endsWith("EST")) { est(); } 
    	else if (data.endsWith("ACP")) { acp(); } 
    	else if (data.endsWith("LAM")) { lam(); } 
    	else if (data.endsWith("RQP")) { rqp(); } 
    	else if (data.endsWith("RQS")) { rqs(); } 
    	else if (data.endsWith("SPL")) { spl(); }
    }
    
    static void discardNavBtn(Button f, Button p,Button n,Button l) {
    	f.setEnabled(false); p.setEnabled(false); n.setEnabled(false); l.setEnabled(false);
    }
}