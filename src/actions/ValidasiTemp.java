package actions;

import org.eclipse.swt.widgets.Shell;

import displays.DialogFactory;
import displays.forms.ATSForms;
import displays.forms.HeaderComposite;
import setting.ErrMsg;
import setting.Shells;
import setting.Times;


public class ValidasiTemp {


    private SendSave ss = new SendSave();
    private ErrMsg em = new ErrMsg();
    private Times time = new Times();
    
    Shell shell;
 
    String t15a="",t15b="";
	
	public ValidasiTemp() {
	}
    
	String getText() {
		return "Validation";
	}
	
    public void validasi(String msgType) {
//    	if (msgType.compareToIgnoreCase("DLA")==0) { shell = Shells.sh_nDLA; }
//    	if (msgType.compareToIgnoreCase("CHG")==0) { shell = Shells.sh_nCHG; }
//    	if (msgType.compareToIgnoreCase("CNL")==0) { shell = Shells.sh_nCNL; }
//    	if (msgType.compareToIgnoreCase("DEP")==0) { shell = Shells.sh_nDEP; }
//    	if (msgType.compareToIgnoreCase("ARR")==0) { shell = Shells.sh_nARR; }
    	
    	shell = Shells.sh_nFPL;
    	ATSForms.getFPLValue(); time.tgl();
    	ss.t10b=""; ss.sdbguv=""; ss.kriteria=""; ss.sprotek10a=""; ss.sprotekpbn="";
    	ss.t10b = ATSForms.get10bBaru();
    	ss.sReg = ATSForms.get18tReg();
    	ss.sDof = ATSForms.get18tDof();
    	ss.tpbn = ATSForms.get18tPbn();
    	//--
    	t15a = ATSForms.get15a();
    	t15b = ATSForms.get15b();
		time.t15(t15a, t15b);

		ss.t10a = ATSForms.get10a();
		ss.validasi10a(ss.t10a);
		ss.validasi18_10(ss.t10b, ss.t10a);
		ss.proteksi18();
		ss.validasipbn(ss.tpbn);
		
    	String t9b = ATSForms.get9b();
    	ss.cek9b(t9b, "FPL");
    	
    	String t7a = ATSForms.get7a();
    	ss.cek7a("DEP",t7a);

    	if (!ss.s_Reg.isEmpty()) ss.s_Reg = ss.s_Reg.replace(" REG/", "");
    	if (!ss.s_Dof.isEmpty()) ss.s_Dof = ss.s_Dof.replace(" DOF/", "");
    	//----------------------------------------------------------------
    	if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4) { time.t13b(ATSForms.get13b()); }
    	if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4) { time.t16b(ATSForms.get16b()); }
    	if (!ATSForms.get19a().isEmpty() && ATSForms.get19a().length()==4) { time.t19a(ATSForms.get19a()); }
		String strDof="";
		//untuk mendapatkan isi tipe18
		if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6) ss.tipe18baru();
		//untuk melakukan seleksi, jika format pengisian indikator DOF/ tidak sesuai YYMMDD
		if (ss.sDof.startsWith(" ")) ss.sDof=ss.sDof.replaceFirst("\\s+", "");    			
		strDof = ss.sDof;
		//dijadikan kosong, karena tipe New Reg & Dof masuk ke tipe 18, jadi tipe18a(sReg) & tipe18b(sDof) dikosongkan
		ss.sReg="";ss.sDof="";
		if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6) { time.t18b(strDof); }
		String datedof="";
		if (!strDof.equals("") && !strDof.equals("DOF/0")) { datedof = time.sYy+time.sHh+time.sMm; }
		System.out.println("\ndatedof:"+datedof+"*datenow:"+time.datenow);
    	//---------------------------------------- [header mandatory] ----------------------------------------
    	if (HeaderComposite.getPriorityFPL().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.reqPriority); HeaderComposite.tPriorityFPLFocus(); }
		else if (HeaderComposite.getDest1FPL().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.reqAddress); HeaderComposite.tDest1FPLFocus(); }
		else if ((!HeaderComposite.getDest1FPL().equals("")) && (HeaderComposite.getDest1FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest1FPLFocus(); }
		else if ((!HeaderComposite.getDest2FPL().equals("")) && (HeaderComposite.getDest2FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest2FPLFocus(); }
		else if ((!HeaderComposite.getDest3FPL().equals("")) && (HeaderComposite.getDest3FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest3FPLFocus(); }
		else if ((!HeaderComposite.getDest4FPL().equals("")) && (HeaderComposite.getDest4FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest4FPLFocus(); }
		else if ((!HeaderComposite.getDest5FPL().equals("")) && (HeaderComposite.getDest5FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest5FPLFocus(); }
		else if ((!HeaderComposite.getDest6FPL().equals("")) && (HeaderComposite.getDest6FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest6FPLFocus(); }
		else if ((!HeaderComposite.getDest7FPL().equals("")) && (HeaderComposite.getDest7FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest7FPLFocus(); }
		else if ((!HeaderComposite.getDest8FPL().equals("")) && (HeaderComposite.getDest8FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest8FPLFocus(); }
		else if ((!HeaderComposite.getDest9FPL().equals("")) && (HeaderComposite.getDest9FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest9FPLFocus(); }
		else if ((!HeaderComposite.getDest10FPL().equals("")) && (HeaderComposite.getDest10FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest10FPLFocus(); }
		else if ((!HeaderComposite.getDest11FPL().equals("")) && (HeaderComposite.getDest11FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest11FPLFocus(); }
		else if ((!HeaderComposite.getDest12FPL().equals("")) && (HeaderComposite.getDest12FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest12FPLFocus(); }
		else if ((!HeaderComposite.getDest13FPL().equals("")) && (HeaderComposite.getDest13FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest13FPLFocus(); }
		else if ((!HeaderComposite.getDest14FPL().equals("")) && (HeaderComposite.getDest14FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest14FPLFocus(); }
		else if ((!HeaderComposite.getDest15FPL().equals("")) && (HeaderComposite.getDest15FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest15FPLFocus(); }
		else if ((!HeaderComposite.getDest16FPL().equals("")) && (HeaderComposite.getDest16FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest16FPLFocus(); }
		else if ((!HeaderComposite.getDest17FPL().equals("")) && (HeaderComposite.getDest17FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest17FPLFocus(); }
		else if ((!HeaderComposite.getDest18FPL().equals("")) && (HeaderComposite.getDest18FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest18FPLFocus(); }
		else if ((!HeaderComposite.getDest19FPL().equals("")) && (HeaderComposite.getDest19FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest19FPLFocus(); }
		else if ((!HeaderComposite.getDest20FPL().equals("")) && (HeaderComposite.getDest20FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest20FPLFocus(); }
		else if ((!HeaderComposite.getDest21FPL().equals("")) && (HeaderComposite.getDest21FPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoAddress); HeaderComposite.tDest21FPLFocus(); }
		else if (HeaderComposite.getOriginatorFPL().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.reqOriginator); HeaderComposite.tOriginatorFPLFocus(); }
 		else if ((!HeaderComposite.getOriginatorFPL().equals("")) && (HeaderComposite.getOriginatorFPL().length() < 8)) { DialogFactory.openInfoDialog(shell, getText(), em.infoOriginator); HeaderComposite.tOriginatorFPLFocus(); }
    	//---------------------------------------- [body mandatory] ----------------------------------------
 		else if (ATSForms.get3a().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.req3a); ATSForms.focus3aFPL(); }
        //7
 		else if (ATSForms.get7a().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.req7a); ATSForms.focus7aFPL(); }
 		else if (ATSForms.get7b().equals("A") && (ATSForms.get7c().equals(""))) { DialogFactory.openInfoDialog(shell, getText(), em.req7c); ATSForms.focus7cFPL(); }
        else if (!ATSForms.get7b().equals("A") && (!ATSForms.get7c().equals(""))) { DialogFactory.openInfoDialog(shell, getText(), em.req7b); }
        else if (!ATSForms.get7c().equals("") && (ATSForms.get7c().length() < 4)) { DialogFactory.openInfoDialog(shell, getText(), em.info7c); ATSForms.focus7cFPL(); }
    	//8
 		else if (ATSForms.get8a().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.req8a); ATSForms.focus8aFPL(); }
        else if (ATSForms.get8b().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.req8b); ATSForms.focus8bFPL(); }
        //9
        else if (t9b.equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.req9b); ATSForms.focus9bFPL(); }
        else if (t9b.length() < 2) { DialogFactory.openInfoDialog(shell, getText(), em.info9b); ATSForms.focus9bFPL(); }
        else if (ATSForms.get9c().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.req9c); ATSForms.focus9cFPL(); }
        else if (!ss.wtc.equals("") && ATSForms.get9c().compareTo(ss.wtc) != 0) { DialogFactory.openWarningDialog(shell, getText(),"The value in field WAKE TURBULENCE CAT. is incorrect !!\nChoose \nWake Turbulence Category : "+ss.wtc+" for \nType of Aircraft : "+t9b); ATSForms.focus9cFPL(); }
    	//10
        else if (ss.t10a.equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.req10); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.equals("") && (ss.sprotek10a.contains("ngaco"))) { DialogFactory.openInfoDialog(shell, getText(), em.infoInvalid10aFix); ATSForms.focus10aFPL(); }		
		else if (ss.t10b.equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.req10); ATSForms.focus10bBaruFPL(); }
		else if (!ss.t10b.equals("") && (ss.sdbguv.contains("tdksesuai"))) { DialogFactory.openInfoDialog(shell, getText(), em.infoInvalid10b+"\nInsert B1 or B2 or U1 or U2 or V1 or V2 or D1 or G1"); ATSForms.focus10bBaruFPL(); }		
		else if ((ss.t10b.contains("B1") || ss.t10b.contains("U1") || ss.t10b.contains("V1")) && (ss.t10b.contains("B2") || ss.t10b.contains("U2") || ss.t10b.contains("V2"))) { DialogFactory.openInfoDialog(shell, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10a.contains("N") && ss.t10a.contains("S")) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalid10a); ATSForms.focus10aFPL(); }
		else if (ss.t10b.contains("N") && (ss.t10b.contains("A") || ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X") || ss.t10b.contains("B1") || ss.t10b.contains("B2") || ss.t10b.contains("U1") || ss.t10b.contains("U2") || ss.t10b.contains("V1") || ss.t10b.contains("V2") || ss.t10b.contains("D1") || ss.t10b.contains("G1"))) { DialogFactory.openWarningDialog(shell, getText(), "Invalid EQUIPMENT AND CAPABILITIES value.\n\nSSR Modes A and C and SSR Mode S are not allowed,\nif 'N' has inserted into field EQUIPMENT AND CAPABILITIES and vice versa.\n\nSee the instruction below the EQUIPMENT AND CAPABILITIES text !"); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10a.contains("W") && ATSForms.get18tSts().contains("NONRVSM")) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalid10aSTS); ATSForms.focus18tStsFPL(); }
		else if (ss.t10b.contains("N") && ss.t10b.length()>1) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("A") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("C") && (ss.t10b.contains("A") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("E") && (ss.t10b.contains("C") || ss.t10b.contains("A") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("H") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("A") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("I") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("A") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("L") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("A") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("P") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("A") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("S") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("A") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("X") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("A"))) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
    	//13
		else if (ATSForms.get13a().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.req13a); ATSForms.focus13aFPL(); }
		else if (ATSForms.get13a().length() < 4) { DialogFactory.openInfoDialog(shell, getText(), em.info13a); ATSForms.focus13aFPL(); }
        else if (ATSForms.get13b().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.req13b); ATSForms.focus13bFPL(); }
        else if (ATSForms.get13b().length() < 4) { DialogFactory.openInfoDialog(shell, getText(), em.info13b); ATSForms.focus13bFPL(); }
        else if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4 && (time.iHh13>23) || (time.iMM13>59)) { DialogFactory.openInfoDialog(shell, getText(), em.infoInvalid13b); ATSForms.focus13bFPL(); }
		//15
        else if (ATSForms.get15a().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.req15a); ATSForms.focus15aFPL(); }
		else if ((!t15a.startsWith("K")) && (!t15a.startsWith("N")) && (!t15a.startsWith("M"))) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalid15a); ATSForms.focus15aFPL(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (t15a.length()<5)) { DialogFactory.openWarningDialog(shell, getText(), em.info15aKN); ATSForms.focus15aFPL(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (!time.t15a2.startsWith("0")) && (!time.t15a2.startsWith("1")) && (!time.t15a2.startsWith("2")) && (!time.t15a2.startsWith("3")) && (!time.t15a2.startsWith("4")) && (!time.t15a2.startsWith("5")) && (!time.t15a2.startsWith("6")) && (!time.t15a2.startsWith("7")) && (!time.t15a2.startsWith("8")) && (!time.t15a2.startsWith("9"))) { DialogFactory.openWarningDialog(shell, getText(), em.info15aKN); ATSForms.focus15aFPL(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (!time.t15a3.startsWith("0")) && (!time.t15a3.startsWith("1")) && (!time.t15a3.startsWith("2")) && (!time.t15a3.startsWith("3")) && (!time.t15a3.startsWith("4")) && (!time.t15a3.startsWith("5")) && (!time.t15a3.startsWith("6")) && (!time.t15a3.startsWith("7")) && (!time.t15a3.startsWith("8")) && (!time.t15a3.startsWith("9"))) { DialogFactory.openWarningDialog(shell, getText(), em.info15aKN); ATSForms.focus15aFPL(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (!time.t15a4.startsWith("0")) && (!time.t15a4.startsWith("1")) && (!time.t15a4.startsWith("2")) && (!time.t15a4.startsWith("3")) && (!time.t15a4.startsWith("4")) && (!time.t15a4.startsWith("5")) && (!time.t15a4.startsWith("6")) && (!time.t15a4.startsWith("7")) && (!time.t15a4.startsWith("8")) && (!time.t15a4.startsWith("9"))) { DialogFactory.openWarningDialog(shell, getText(), em.info15aKN); ATSForms.focus15aFPL(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (!time.t15a5.startsWith("0")) && (!time.t15a5.startsWith("1")) && (!time.t15a5.startsWith("2")) && (!time.t15a5.startsWith("3")) && (!time.t15a5.startsWith("4")) && (!time.t15a5.startsWith("5")) && (!time.t15a5.startsWith("6")) && (!time.t15a5.startsWith("7")) && (!time.t15a5.startsWith("8")) && (!time.t15a5.startsWith("9"))) { DialogFactory.openWarningDialog(shell, getText(), em.info15aKN); ATSForms.focus15aFPL(); }
		else if ((t15a.startsWith("M")) && ((t15a.length()>4) || (t15a.length()<4))) { DialogFactory.openWarningDialog(shell, getText(), em.info15aM); ATSForms.focus15aFPL(); }
		else if (((t15a.startsWith("M"))) && (!time.t15a2.startsWith("0")) && (!time.t15a2.startsWith("1")) && (!time.t15a2.startsWith("2")) && (!time.t15a2.startsWith("3")) && (!time.t15a2.startsWith("4")) && (!time.t15a2.startsWith("5")) && (!time.t15a2.startsWith("6")) && (!time.t15a2.startsWith("7")) && (!time.t15a2.startsWith("8")) && (!time.t15a2.startsWith("9"))) { DialogFactory.openWarningDialog(shell, getText(), em.info15aM); ATSForms.focus15aFPL(); }
		else if (((t15a.startsWith("M"))) && (!time.t15a3.startsWith("0")) && (!time.t15a3.startsWith("1")) && (!time.t15a3.startsWith("2")) && (!time.t15a3.startsWith("3")) && (!time.t15a3.startsWith("4")) && (!time.t15a3.startsWith("5")) && (!time.t15a3.startsWith("6")) && (!time.t15a3.startsWith("7")) && (!time.t15a3.startsWith("8")) && (!time.t15a3.startsWith("9"))) { DialogFactory.openWarningDialog(shell, getText(), em.info15aM); ATSForms.focus15aFPL(); }
		else if (((t15a.startsWith("M"))) && (!time.t15a4.startsWith("0")) && (!time.t15a4.startsWith("1")) && (!time.t15a4.startsWith("2")) && (!time.t15a4.startsWith("3")) && (!time.t15a4.startsWith("4")) && (!time.t15a4.startsWith("5")) && (!time.t15a4.startsWith("6")) && (!time.t15a4.startsWith("7")) && (!time.t15a4.startsWith("8")) && (!time.t15a4.startsWith("9"))) { DialogFactory.openWarningDialog(shell, getText(), em.info15aM); ATSForms.focus15aFPL(); }
		else if (ATSForms.get15b().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.req15b); ATSForms.focus15bFPL(); }
		else if ((!t15b.startsWith("F")) && (!t15b.startsWith("S")) && (!t15b.startsWith("A")) && (!t15b.startsWith("M")) && (!t15b.equals("VFR"))) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalid15b); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (t15b.length()<5)) { DialogFactory.openInfoDialog(shell, getText(), em.info14cSM); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (!time.t15b2.startsWith("0")) && (!time.t15b2.startsWith("1")) && (!time.t15b2.startsWith("2")) && (!time.t15b2.startsWith("3")) && (!time.t15b2.startsWith("4")) && (!time.t15b2.startsWith("5")) && (!time.t15b2.startsWith("6")) && (!time.t15b2.startsWith("7")) && (!time.t15b2.startsWith("8")) && (!time.t15b2.startsWith("9"))) { DialogFactory.openInfoDialog(shell, getText(), em.info14cSM); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (!time.t15b3.startsWith("0")) && (!time.t15b3.startsWith("1")) && (!time.t15b3.startsWith("2")) && (!time.t15b3.startsWith("3")) && (!time.t15b3.startsWith("4")) && (!time.t15b3.startsWith("5")) && (!time.t15b3.startsWith("6")) && (!time.t15b3.startsWith("7")) && (!time.t15b3.startsWith("8")) && (!time.t15b3.startsWith("9"))) { DialogFactory.openInfoDialog(shell, getText(), em.info14cSM); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (!time.t15b4.startsWith("0")) && (!time.t15b4.startsWith("1")) && (!time.t15b4.startsWith("2")) && (!time.t15b4.startsWith("3")) && (!time.t15b4.startsWith("4")) && (!time.t15b4.startsWith("5")) && (!time.t15b4.startsWith("6")) && (!time.t15b4.startsWith("7")) && (!time.t15b4.startsWith("8")) && (!time.t15b4.startsWith("9"))) { DialogFactory.openInfoDialog(shell, getText(), em.info14cSM); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (!time.t15b5.startsWith("0")) && (!time.t15b5.startsWith("1")) && (!time.t15b5.startsWith("2")) && (!time.t15b5.startsWith("3")) && (!time.t15b5.startsWith("4")) && (!time.t15b5.startsWith("5")) && (!time.t15b5.startsWith("6")) && (!time.t15b5.startsWith("7")) && (!time.t15b5.startsWith("8")) && (!time.t15b5.startsWith("9"))) { DialogFactory.openInfoDialog(shell, getText(), em.info14cSM); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("F")) || (t15b.startsWith("A"))) && ((t15b.length()>4) || (t15b.length()<4))) { DialogFactory.openInfoDialog(shell, getText(), em.info14cFA); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("F")) || (t15b.startsWith("A"))) && (!time.t15b2.startsWith("0")) && (!time.t15b2.startsWith("1")) && (!time.t15b2.startsWith("2")) && (!time.t15b2.startsWith("3")) && (!time.t15b2.startsWith("4")) && (!time.t15b2.startsWith("5")) && (!time.t15b2.startsWith("6")) && (!time.t15b2.startsWith("7")) && (!time.t15b2.startsWith("8")) && (!time.t15b2.startsWith("9"))) { DialogFactory.openInfoDialog(shell, getText(), em.info14cFA); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("F")) || (t15b.startsWith("A"))) && (!time.t15b3.startsWith("0")) && (!time.t15b3.startsWith("1")) && (!time.t15b3.startsWith("2")) && (!time.t15b3.startsWith("3")) && (!time.t15b3.startsWith("4")) && (!time.t15b3.startsWith("5")) && (!time.t15b3.startsWith("6")) && (!time.t15b3.startsWith("7")) && (!time.t15b3.startsWith("8")) && (!time.t15b3.startsWith("9"))) { DialogFactory.openInfoDialog(shell, getText(), em.info14cFA); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("F")) || (t15b.startsWith("A"))) && (!time.t15b4.startsWith("0")) && (!time.t15b4.startsWith("1")) && (!time.t15b4.startsWith("2")) && (!time.t15b4.startsWith("3")) && (!time.t15b4.startsWith("4")) && (!time.t15b4.startsWith("5")) && (!time.t15b4.startsWith("6")) && (!time.t15b4.startsWith("7")) && (!time.t15b4.startsWith("8")) && (!time.t15b4.startsWith("9"))) { DialogFactory.openInfoDialog(shell, getText(), em.info14cFA); ATSForms.focus15bFPL(); }
		else if (ATSForms.get15c().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.req15c); ATSForms.focus15cFPL(); }
    	//16
		else if (ATSForms.get16a().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.req16a); ATSForms.focus16aFPL(); }
		else if (ATSForms.get16a().length() < 4) { DialogFactory.openInfoDialog(shell, getText(), em.info16a); ATSForms.focus16aFPL(); }
		else if (ATSForms.get16b().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.req16b); ATSForms.focus16bFPL(); }
		else if (ATSForms.get16b().length() < 4) { DialogFactory.openInfoDialog(shell, getText(), em.info16b); ATSForms.focus16bFPL(); }
		else if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4 && (time.iHh16>23) || (time.iMM16>59)) { DialogFactory.openInfoDialog(shell, getText(), em.infoInvalid16b); ATSForms.focus16bFPL(); }
		else if (!ATSForms.get16c().equals("") && ATSForms.get16c().length() < 4) { DialogFactory.openInfoDialog(shell, getText(), em.info16c); ATSForms.focus16cFPL(); }
		else if (!ATSForms.get16c2nd().equals("") && ATSForms.get16c2nd().length() < 4) { DialogFactory.openInfoDialog(shell, getText(), em.info16d); ATSForms.focus16dFPL(); }
    	//18 indicator TYP/
        else if (ATSForms.get9b().equals("ZZZZ") && ATSForms.get18tTyp().equals("")) {  DialogFactory.openWarningDialog(shell, getText(), em.infoTyp9b); ATSForms.focus18tTypFPL(); }
        else if (!ATSForms.get9b().contains("ZZZZ") && !ATSForms.get18tTyp().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.info9bTyp); ATSForms.focus9bFPL(); }
    	//indicator COM/, DAT/, NAV/, SUR/ [link ke Item 10]
        else if (ss.t10a.contains("Z") && ss.s10aZ.contains("ngaco"))  { DialogFactory.openWarningDialog(shell, getText(), em.infoCom10); ATSForms.focus18tComFPL(); }
        else if (ss.kriteria.contains("ngaco") && ATSForms.get18tSur().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.infoSur10); ATSForms.focus18tSurFPL(); }
    	//indicator DEP/
        else if ((ATSForms.get13a().equals("ZZZZ") || ATSForms.get13a().equals("AFIL")) && ATSForms.get18tDep().equals("")) {  DialogFactory.openWarningDialog(shell, getText(), em.infoDep13); ATSForms.focus18tDepFPL(); }
        else if (!ATSForms.get13a().contains("ZZZZ") && !ATSForms.get13a().contains("AFIL") && !ATSForms.get18tDep().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.info13Dep); ATSForms.focus13aFPL(); }
    	//indicator DEST/
        else if (ATSForms.get16a().equals("ZZZZ") && ATSForms.get18tDest().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.infoDest16); ATSForms.focus18tDestFPL(); }
        else if (!ATSForms.get16a().contains("ZZZZ") && !ATSForms.get18tDest().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.info16Dest); ATSForms.focus16aFPL(); }
    	//indicator ALTN/
        else if (ATSForms.get16c().equals("ZZZZ") && ATSForms.get18tAltn().equals("")) {  DialogFactory.openWarningDialog(shell, getText(), em.infoAltn16); ATSForms.focus18tAltnFPL(); }
        else if (!ATSForms.get16c().contains("ZZZZ") && !ATSForms.get18tAltn().equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.info16Altn); ATSForms.focus16cFPL(); }
    	//indicator REG/, DOF/
        else if (!ATSForms.get18tReg().isEmpty() && ATSForms.get18tReg().equals(ATSForms.get7a())) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalidReg); ATSForms.focus18tRegFPL(); }
        else if (!ATSForms.get18tReg().isEmpty() && !ATSForms.get18tOpr().equals("") && ATSForms.get18tReg().equals(ATSForms.get18tOpr())) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalidOpr); ATSForms.focus18tOprFPL(); }
        else if (!ATSForms.get18tReg().isEmpty() && (ATSForms.get18tReg().length() > 7 || ATSForms.get18tReg().contains("\n"))) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalidReg); ATSForms.focus18tRegFPL(); }
		//else if (ATSForms.get18tDof().isEmpty()) { DialogFactory.openWarningDialog(shell, getText(), em.reqDof); ATSForms.focus18tDofFPL(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()<6) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalidDof); ATSForms.focus18tDofFPL(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && (time.sYy.equals("00") || time.sHh.equals("00") || time.sMm.equals("00") || time.iHh>12 || time.iMM>31)) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalid18Dof); ATSForms.focus18tDofFPL(); }
		else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && Integer.parseInt(datedof) < Integer.parseInt(time.datenow)) { DialogFactory.openInfoDialog(shell, getText(), em.infoInvalid18DofVal); ATSForms.focus18tDofFPL(); }
		//indicator STS/, PER/, CODE/, SEL/, PBN/
        else if (!ATSForms.get18tSts().equals("") && ss.criteria_sts.contains("ngaco")) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalidSts); ATSForms.focus18tStsFPL(); }
        else if (!ATSForms.get18tPer().equals("") && ATSForms.get18tPer().length() > 1) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalidPer); ATSForms.focus18tPerFPL(); }
        else if (!ATSForms.get18tCode().equals("") && (ATSForms.get18tCode().length() > 6 || ATSForms.get18tCode().contains("\n"))) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalidCode); ATSForms.focus18tCodeFPL(); }
        else if (!ATSForms.get18tSel().equals("") && (ATSForms.get18tSel().length() > 4 || ATSForms.get18tSel().contains("\n"))) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalidSel); ATSForms.focus18tSelFPL(); }
    	//PBN
        else if (!ss.tpbn.equals("") && (ss.tpbn.length() > 16 || ss.tpbn.contains("\n"))) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalidPbn); ATSForms.focus18tPbnFPL(); }
        else if (!ss.tpbn.equals("") && ss.sprotekpbn.contains("ngaco")) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalidPbnFix); ATSForms.focus18tPbnFPL(); }
		else if (ss.t10a.contains("R") && ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(shell, getText(), em.infoPbn10); ATSForms.focus18tPbnFPL(); }
		else if (!ss.t10a.contains("R") && !ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(shell, getText(), em.info10Pbn); ATSForms.focus10aFPL(); }
    	//G
		else if (!ss.t10a.contains("G") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(shell, getText(), em.info10G); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("G") && !ss.krit2.equals("") && (ss.krit2.contains("B2") || ss.krit2.contains("C2") || ss.krit2.contains("D2") || ss.krit2.contains("O2"))) { DialogFactory.openWarningDialog(shell, getText(), em.info10G); ATSForms.focus10aFPL(); }
		//D
		else if (!ss.t10a.contains("D") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(shell, getText(), em.info10D); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("D") && !ss.krit3.equals("") && (ss.krit3.contains("B3") || ss.krit3.contains("C3") || ss.krit3.contains("D3") || ss.krit3.contains("O3"))) { DialogFactory.openWarningDialog(shell, getText(), em.info10D); ATSForms.focus10aFPL(); }
    	//I
		else if (!ss.t10a.contains("I") && !ss.krit5.equals("")) { DialogFactory.openWarningDialog(shell, getText(), em.info10I); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1"))) { DialogFactory.openWarningDialog(shell, getText(), em.info10I); ATSForms.focus10aFPL(); }
		//DI
		else if (!ss.t10a.contains("D") && !ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(shell, getText(), em.info10DI); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("D") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(shell, getText(), em.info10DI); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("I") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(shell, getText(), em.info10DI); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("I") && (ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(shell, getText(), em.info10DI); ATSForms.focus10aFPL(); }
    	//OD / SD
		else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit1.contains("B1")) { DialogFactory.openWarningDialog(shell, getText(), em.info10OD); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(shell, getText(), em.info10OD); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("D") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(shell, getText(), em.info10OD); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("D") && ss.krit1.contains("B4")) { DialogFactory.openWarningDialog(shell, getText(), em.info10OD); ATSForms.focus10aFPL(); }
    	//19
		else if (!ATSForms.get19a().equals("") && ATSForms.get19a().length() < 4) { DialogFactory.openInfoDialog(shell, getText(), em.info19a); ATSForms.focus19aFPL(); } 
		else if (!ATSForms.get19a().isEmpty() && ATSForms.get19a().length()==4 && ((time.iHh19>23) || (time.iMM19>59))) { DialogFactory.openWarningDialog(shell, getText(), em.infoInvalid19a); ATSForms.focus19aFPL(); }
		else if (!ATSForms.get19fNum().equals("") && ATSForms.get19fNum().length() < 2) { DialogFactory.openInfoDialog(shell, getText(), em.info19fNum); ATSForms.focus19NumFPL(); } 
		else if (!ATSForms.get19fCap().equals("") && ATSForms.get19fCap().length() < 3) { DialogFactory.openInfoDialog(shell, getText(), em.info19fCap); ATSForms.focus19CapFPL(); } 
    	//length
		else if (ATSForms.getBodyFPL().length() > 1800) { DialogFactory.openWarningDialog(shell, getText(), "Your data is "+ATSForms.getBodyFPL().length()+" characters. Transmitting data maximum is 1800 characters !!"); }
 		else {
 			if (msgType.compareTo("DLA")==0) { openDLA(); }
			else if (msgType.compareTo("CHG")==0) { openCHG(); }
			else if (msgType.compareTo("CNL")==0) { openCNL(); }
			else if (msgType.compareTo("DEP")==0) { openDEP(); }
			else if (msgType.compareTo("ARR")==0) { openARR(); }
 		}
	}
	
	void openDLA() {
		RefreshTable.refreshNewDLA("NewDLA","");
		ATSForms.editDLAFromFPL();
	}
	
	void openCHG() {
		RefreshTable.refreshNewCHG("NewCHG","");
		ATSForms.editCHGFromFPL();
	}
	
	void openCNL() {
		RefreshTable.refreshNewCNL("NewCNL","");
		ATSForms.editCNLFromFPL();
	}
	
	void openDEP() {
		RefreshTable.refreshNewDEP("NewDEP","");
		ATSForms.editDEPFromFPL();
	}
	
	void openARR() {
		RefreshTable.refreshNewARR("NewARR","");
		ATSForms.editARRFromFPL();
	}
}
