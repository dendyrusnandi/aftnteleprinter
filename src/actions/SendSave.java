package actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.widgets.Shell;

import setting.Shells;
import setting.Times;
import databases.jdbc;
import displays.DialogFactory;
import displays.forms.ATSForms;
import displays.forms.HeaderComposite;
import displays.tables.TableOutgoing;

public class SendSave {

    private Times time = new Times();
    
    
    public static Connection conn = null; // connection object
	public static Statement stmt = null; // statement object
	public static ResultSet rs = null; // result set object
	
	public String msgBody="",msg="",sNoteChk="";
//	String[] sub = new String[1000];
//	String message="",x="",F="",k="",msg="";
//	int index;
	
	public String kType7a="",kType13a="",kType13b="",kType16a="",kType18b="",kJam="";
	//HEADER
	public String sPriority="",sDest1="",sDest2="",sDest3="",sDest4="",sDest5="",sDest6="",sDest7="",sDest8="",sDest9="",sDest10="",sDest11="",
	sDest12="",sDest13="",sDest14="",sDest15="",sDest16="",sDest17="",sDest18="",sDest19="",sDest20="",sDest21="",sOriginator="",sFiling="",
	sJam="",sOriRef="",sBell="",sNote="",sFiledby="",sTblName="",
	// ATS Message
	s3a="",s3b="",s3c="",s5a="",s5b="",s5c="",s7a="",s7b="",s7c="",s8a="",s8b="",s9a="",s9b="",s9c="",
	s10a="",s10b="",
	s13a="",
	s14a="",s14b="",s14c="",s14d="",s14e="",
	s15a="",s15b="",s15c="",s16a="",
	s16c="",s16d="",s17a="",s17b="",s17c="",
	s18aidc="",
	s19b="",s19cUHF="",s19cVHF="",s19cELT="",s19dS="",s19dP="",s19dD="",s19dM="",s19dJ="",
	s19eJ="",s19eL="",s19eF="",s19eU="",s19eV="",s19fD="",s19fNum="",s19fCap="",s19fCov="",s19fCol="",s19g="",s19h="",s19Rmk="",s19i="",
	s20="",s21="",s22="",sSpace="",
	sTrkName="",sTrkId="",sTrkDate="",sTrkStatus="",sTrkAct="",sTrkExp="",sTrkWay="",sTrkLev="",sTrkRts="",sTrkRmk="",sFree="",
	toa="",wtc="",acft_id="",
	// variabel untuk proteksi18 dan validasi18_10
	kriteria10a="",criteria18="",sprotek10a="",sprotekpbn="",sdbguv="",kriteria="",criteria_sts="",krit1="",krit2="",krit3="",krit4="",krit5="",s10aZ="",
	s_Reg="",s_Dof="",s_Typ="",s_Nav="",s_Com="",s_Dat="",s_Dep="",s_Dest="",s_Altn="",tpbn="",t10a="",t10b="",abal="",ebel="",
	sReg="",sDof="",s18Baru="";
	
	public SendSave() {
		
	}
	
	public void setfree() {
		kType7a="";kType13a="";kType13b="";kType16a="";kType18b="";kJam="";
		//HEADER
		sPriority="";sDest1="";sDest2="";sDest3="";sDest4="";sDest5="";sDest6="";sDest7="";sDest8="";sDest9="";sDest10="";sDest11="";
		sDest12="";sDest13="";sDest14="";sDest15="";sDest16="";sDest17="";sDest18="";sDest19="";sDest20="";sDest21="";sOriginator="";sFiling="";
		sJam="";sOriRef="";sFiledby="";sTblName="";
		// ATS Message
		s3a="";s3b="";s3c="";s5a="";s5b="";s5c="";s7a="";s7b="";s7c="";s8a="";s8b="";s9a="";s9b="";s9c="";
		s10a="";s10b="";
		s13a="";
		s14a="";s14b="";s14c="";s14d="";s14e="";
		s15a="";s15b="";s15c="";s16a="";
		s16c="";s16d="";s17a="";s17b="";s17c="";
		s18aidc="";
		s19b="";s19cUHF="";s19cVHF="";s19cELT="";s19dS="";s19dP="";s19dD="";s19dM="";s19dJ="";
		s19eJ="";s19eL="";s19eF="";s19eU="";s19eV="";s19fD="";s19fNum="";s19fCap="";s19fCov="";s19fCol="";s19g="";s19h="";s19Rmk="";s19i="";
		s20="";s21="";s22="";sSpace="";
		sTrkName="";sTrkId="";sTrkDate="";sTrkStatus="";sTrkAct="";sTrkExp="";sTrkWay="";sTrkLev="";sTrkRts="";sTrkRmk="";sFree="";
		toa="";wtc="";acft_id="";
		// variabel untuk proteksi18 dan validasi18_10
		kriteria10a="";criteria18="";kriteria="";criteria_sts="";krit1="";krit2="";krit3="";krit4="";krit5="";s10aZ="";
		s_Reg="";s_Dof="";s_Typ="";s_Nav="";s_Com="";s_Dat="";s_Dep="";s_Dest="";s_Altn="";t10a="";t10b="";abal="";ebel="";
		sReg="";sDof="";s18Baru="";
	}

	// HEADER
	void header(String pr,String d1,String d2,String d3,String d4,String d5,String d6,String d7,
			String d8,String d9,String d10,String d11,String d12,String d13,String d14,String d15,String d16,String d17,
			String d18,String d19,String d20,String d21,String ori,String ft,String jam,String oriref,String bell) {
		sPriority=pr; sDest1=d1; sDest2=d2; sDest3=d3; sDest4=d4; sDest5=d5; sDest6=d6; sDest7=d7; sDest8=d8; sDest9=d9; 
		sDest10=d10; sDest11=d11; sDest12=d12; sDest13=d13; sDest14=d14; sDest15=d15; sDest16=d16; sDest17=d17; sDest18=d18; 
		sDest19=d19; sDest20=d20; sDest21=d21; sOriginator=ori; sFiling = ft; sJam = jam; 
		sOriRef = oriref; if (sOriRef.startsWith(" ")) { sOriRef = sOriRef.replaceFirst("\\s+", ""); }
		sBell = bell;
	}

	// HEADER ATS Message
    public void hAFTN() {
		header(HeaderComposite.getPriorityAFTN(),HeaderComposite.getDest1AFTN(),HeaderComposite.getDest2AFTN(),
				HeaderComposite.getDest3AFTN(),HeaderComposite.getDest4AFTN(),HeaderComposite.getDest5AFTN(),
				HeaderComposite.getDest6AFTN(),HeaderComposite.getDest7AFTN(),HeaderComposite.getDest8AFTN(),
				HeaderComposite.getDest9AFTN(),HeaderComposite.getDest10AFTN(),HeaderComposite.getDest11AFTN(),
				HeaderComposite.getDest12AFTN(),HeaderComposite.getDest13AFTN(),HeaderComposite.getDest14AFTN(),
				HeaderComposite.getDest15AFTN(),HeaderComposite.getDest16AFTN(),HeaderComposite.getDest17AFTN(),
				HeaderComposite.getDest18AFTN(),HeaderComposite.getDest19AFTN(),HeaderComposite.getDest20AFTN(),
				HeaderComposite.getDest21AFTN(),HeaderComposite.getOriginatorAFTN(),HeaderComposite.getFilingTimeAFTN(),
				HeaderComposite.getSendAtAFTN(),HeaderComposite.getOriRefAFTN(),HeaderComposite.getBellAFTN());
	}
	
	public void hALR() {
		header(HeaderComposite.getPriorityALR(),HeaderComposite.getDest1ALR(),HeaderComposite.getDest2ALR(),
				HeaderComposite.getDest3ALR(),HeaderComposite.getDest4ALR(),HeaderComposite.getDest5ALR(),
				HeaderComposite.getDest6ALR(),HeaderComposite.getDest7ALR(),HeaderComposite.getDest8ALR(),
				HeaderComposite.getDest9ALR(),HeaderComposite.getDest10ALR(),HeaderComposite.getDest11ALR(),
				HeaderComposite.getDest12ALR(),HeaderComposite.getDest13ALR(),HeaderComposite.getDest14ALR(),
				HeaderComposite.getDest15ALR(),HeaderComposite.getDest16ALR(),HeaderComposite.getDest17ALR(),
				HeaderComposite.getDest18ALR(),HeaderComposite.getDest19ALR(),HeaderComposite.getDest20ALR(),
				HeaderComposite.getDest21ALR(),HeaderComposite.getOriginatorALR(),HeaderComposite.getFilingTimeALR(),
				HeaderComposite.getSendAtALR(),HeaderComposite.getOriRefALR(),HeaderComposite.getBellALR());
	}
	
	public void hRCF() {
		header(HeaderComposite.getPriorityRCF(),HeaderComposite.getDest1RCF(),HeaderComposite.getDest2RCF(),
				HeaderComposite.getDest3RCF(),HeaderComposite.getDest4RCF(),HeaderComposite.getDest5RCF(),
				HeaderComposite.getDest6RCF(),HeaderComposite.getDest7RCF(),HeaderComposite.getDest8RCF(),
				HeaderComposite.getDest9RCF(),HeaderComposite.getDest10RCF(),HeaderComposite.getDest11RCF(),
				HeaderComposite.getDest12RCF(),HeaderComposite.getDest13RCF(),HeaderComposite.getDest14RCF(),
				HeaderComposite.getDest15RCF(),HeaderComposite.getDest16RCF(),HeaderComposite.getDest17RCF(),
				HeaderComposite.getDest18RCF(),HeaderComposite.getDest19RCF(),HeaderComposite.getDest20RCF(),
				HeaderComposite.getDest21RCF(),HeaderComposite.getOriginatorRCF(),HeaderComposite.getFilingTimeRCF(),
				HeaderComposite.getSendAtRCF(),HeaderComposite.getOriRefRCF(),HeaderComposite.getBellRCF());
	}
	
	public void hFPL() {
		header(HeaderComposite.getPriorityFPL(),HeaderComposite.getDest1FPL(),HeaderComposite.getDest2FPL(),
				HeaderComposite.getDest3FPL(),HeaderComposite.getDest4FPL(),HeaderComposite.getDest5FPL(),
				HeaderComposite.getDest6FPL(),HeaderComposite.getDest7FPL(),HeaderComposite.getDest8FPL(),
				HeaderComposite.getDest9FPL(),HeaderComposite.getDest10FPL(),HeaderComposite.getDest11FPL(),
				HeaderComposite.getDest12FPL(),HeaderComposite.getDest13FPL(),HeaderComposite.getDest14FPL(),
				HeaderComposite.getDest15FPL(),HeaderComposite.getDest16FPL(),HeaderComposite.getDest17FPL(),
				HeaderComposite.getDest18FPL(),HeaderComposite.getDest19FPL(),HeaderComposite.getDest20FPL(),
				HeaderComposite.getDest21FPL(),HeaderComposite.getOriginatorFPL(),HeaderComposite.getFilingTimeFPL(),
				HeaderComposite.getSendAtFPL(),HeaderComposite.getOriRefFPL(),HeaderComposite.getBellFPL());
	}
	
	public void hDLA() {
		header(HeaderComposite.getPriorityDLA(),HeaderComposite.getDest1DLA(),HeaderComposite.getDest2DLA(),
				HeaderComposite.getDest3DLA(),HeaderComposite.getDest4DLA(),HeaderComposite.getDest5DLA(),
				HeaderComposite.getDest6DLA(),HeaderComposite.getDest7DLA(),HeaderComposite.getDest8DLA(),
				HeaderComposite.getDest9DLA(),HeaderComposite.getDest10DLA(),HeaderComposite.getDest11DLA(),
				HeaderComposite.getDest12DLA(),HeaderComposite.getDest13DLA(),HeaderComposite.getDest14DLA(),
				HeaderComposite.getDest15DLA(),HeaderComposite.getDest16DLA(),HeaderComposite.getDest17DLA(),
				HeaderComposite.getDest18DLA(),HeaderComposite.getDest19DLA(),HeaderComposite.getDest20DLA(),
				HeaderComposite.getDest21DLA(),HeaderComposite.getOriginatorDLA(),HeaderComposite.getFilingTimeDLA(),
				HeaderComposite.getSendAtDLA(),HeaderComposite.getOriRefDLA(),HeaderComposite.getBellDLA());
	}
	
	public void hCHG() {
		header(HeaderComposite.getPriorityCHG(),HeaderComposite.getDest1CHG(),HeaderComposite.getDest2CHG(),
				HeaderComposite.getDest3CHG(),HeaderComposite.getDest4CHG(),HeaderComposite.getDest5CHG(),
				HeaderComposite.getDest6CHG(),HeaderComposite.getDest7CHG(),HeaderComposite.getDest8CHG(),
				HeaderComposite.getDest9CHG(),HeaderComposite.getDest10CHG(),HeaderComposite.getDest11CHG(),
				HeaderComposite.getDest12CHG(),HeaderComposite.getDest13CHG(),HeaderComposite.getDest14CHG(),
				HeaderComposite.getDest15CHG(),HeaderComposite.getDest16CHG(),HeaderComposite.getDest17CHG(),
				HeaderComposite.getDest18CHG(),HeaderComposite.getDest19CHG(),HeaderComposite.getDest20CHG(),
				HeaderComposite.getDest21CHG(),HeaderComposite.getOriginatorCHG(),HeaderComposite.getFilingTimeCHG(),
				HeaderComposite.getSendAtCHG(),HeaderComposite.getOriRefCHG(),HeaderComposite.getBellCHG());
	}
	
	public void hCNL() {
		header(HeaderComposite.getPriorityCNL(),HeaderComposite.getDest1CNL(),HeaderComposite.getDest2CNL(),
				HeaderComposite.getDest3CNL(),HeaderComposite.getDest4CNL(),HeaderComposite.getDest5CNL(),
				HeaderComposite.getDest6CNL(),HeaderComposite.getDest7CNL(),HeaderComposite.getDest8CNL(),
				HeaderComposite.getDest9CNL(),HeaderComposite.getDest10CNL(),HeaderComposite.getDest11CNL(),
				HeaderComposite.getDest12CNL(),HeaderComposite.getDest13CNL(),HeaderComposite.getDest14CNL(),
				HeaderComposite.getDest15CNL(),HeaderComposite.getDest16CNL(),HeaderComposite.getDest17CNL(),
				HeaderComposite.getDest18CNL(),HeaderComposite.getDest19CNL(),HeaderComposite.getDest20CNL(),
				HeaderComposite.getDest21CNL(),HeaderComposite.getOriginatorCNL(),HeaderComposite.getFilingTimeCNL(),
				HeaderComposite.getSendAtCNL(),HeaderComposite.getOriRefCNL(),HeaderComposite.getBellCNL());
	}
	
	public void hDEP() {
		header(HeaderComposite.getPriorityDEP(),HeaderComposite.getDest1DEP(),HeaderComposite.getDest2DEP(),
				HeaderComposite.getDest3DEP(),HeaderComposite.getDest4DEP(),HeaderComposite.getDest5DEP(),
				HeaderComposite.getDest6DEP(),HeaderComposite.getDest7DEP(),HeaderComposite.getDest8DEP(),
				HeaderComposite.getDest9DEP(),HeaderComposite.getDest10DEP(),HeaderComposite.getDest11DEP(),
				HeaderComposite.getDest12DEP(),HeaderComposite.getDest13DEP(),HeaderComposite.getDest14DEP(),
				HeaderComposite.getDest15DEP(),HeaderComposite.getDest16DEP(),HeaderComposite.getDest17DEP(),
				HeaderComposite.getDest18DEP(),HeaderComposite.getDest19DEP(),HeaderComposite.getDest20DEP(),
				HeaderComposite.getDest21DEP(),HeaderComposite.getOriginatorDEP(),HeaderComposite.getFilingTimeDEP(),
				HeaderComposite.getSendAtDEP(),HeaderComposite.getOriRefDEP(),HeaderComposite.getBellDEP());
	}
	
	public void hARR() {
		header(HeaderComposite.getPriorityARR(),HeaderComposite.getDest1ARR(),HeaderComposite.getDest2ARR(),
				HeaderComposite.getDest3ARR(),HeaderComposite.getDest4ARR(),HeaderComposite.getDest5ARR(),
				HeaderComposite.getDest6ARR(),HeaderComposite.getDest7ARR(),HeaderComposite.getDest8ARR(),
				HeaderComposite.getDest9ARR(),HeaderComposite.getDest10ARR(),HeaderComposite.getDest11ARR(),
				HeaderComposite.getDest12ARR(),HeaderComposite.getDest13ARR(),HeaderComposite.getDest14ARR(),
				HeaderComposite.getDest15ARR(),HeaderComposite.getDest16ARR(),HeaderComposite.getDest17ARR(),
				HeaderComposite.getDest18ARR(),HeaderComposite.getDest19ARR(),HeaderComposite.getDest20ARR(),
				HeaderComposite.getDest21ARR(),HeaderComposite.getOriginatorARR(),HeaderComposite.getFilingTimeARR(),
				HeaderComposite.getSendAtARR(),HeaderComposite.getOriRefARR(),HeaderComposite.getBellARR());
	}
	
	public void hCDN() {
		header(HeaderComposite.getPriorityCDN(),HeaderComposite.getDest1CDN(),HeaderComposite.getDest2CDN(),
				HeaderComposite.getDest3CDN(),HeaderComposite.getDest4CDN(),HeaderComposite.getDest5CDN(),
				HeaderComposite.getDest6CDN(),HeaderComposite.getDest7CDN(),HeaderComposite.getDest8CDN(),
				HeaderComposite.getDest9CDN(),HeaderComposite.getDest10CDN(),HeaderComposite.getDest11CDN(),
				HeaderComposite.getDest12CDN(),HeaderComposite.getDest13CDN(),HeaderComposite.getDest14CDN(),
				HeaderComposite.getDest15CDN(),HeaderComposite.getDest16CDN(),HeaderComposite.getDest17CDN(),
				HeaderComposite.getDest18CDN(),HeaderComposite.getDest19CDN(),HeaderComposite.getDest20CDN(),
				HeaderComposite.getDest21CDN(),HeaderComposite.getOriginatorCDN(),HeaderComposite.getFilingTimeCDN(),
				HeaderComposite.getSendAtCDN(),HeaderComposite.getOriRefCDN(),HeaderComposite.getBellCDN());
	}
	
	public void hCPL() {
		header(HeaderComposite.getPriorityCPL(),HeaderComposite.getDest1CPL(),HeaderComposite.getDest2CPL(),
				HeaderComposite.getDest3CPL(),HeaderComposite.getDest4CPL(),HeaderComposite.getDest5CPL(),
				HeaderComposite.getDest6CPL(),HeaderComposite.getDest7CPL(),HeaderComposite.getDest8CPL(),
				HeaderComposite.getDest9CPL(),HeaderComposite.getDest10CPL(),HeaderComposite.getDest11CPL(),
				HeaderComposite.getDest12CPL(),HeaderComposite.getDest13CPL(),HeaderComposite.getDest14CPL(),
				HeaderComposite.getDest15CPL(),HeaderComposite.getDest16CPL(),HeaderComposite.getDest17CPL(),
				HeaderComposite.getDest18CPL(),HeaderComposite.getDest19CPL(),HeaderComposite.getDest20CPL(),
				HeaderComposite.getDest21CPL(),HeaderComposite.getOriginatorCPL(),HeaderComposite.getFilingTimeCPL(),
				HeaderComposite.getSendAtCPL(),HeaderComposite.getOriRefCPL(),HeaderComposite.getBellCPL());
	}
	
	public void hEST() {
		header(HeaderComposite.getPriorityEST(),HeaderComposite.getDest1EST(),HeaderComposite.getDest2EST(),
				HeaderComposite.getDest3EST(),HeaderComposite.getDest4EST(),HeaderComposite.getDest5EST(),
				HeaderComposite.getDest6EST(),HeaderComposite.getDest7EST(),HeaderComposite.getDest8EST(),
				HeaderComposite.getDest9EST(),HeaderComposite.getDest10EST(),HeaderComposite.getDest11EST(),
				HeaderComposite.getDest12EST(),HeaderComposite.getDest13EST(),HeaderComposite.getDest14EST(),
				HeaderComposite.getDest15EST(),HeaderComposite.getDest16EST(),HeaderComposite.getDest17EST(),
				HeaderComposite.getDest18EST(),HeaderComposite.getDest19EST(),HeaderComposite.getDest20EST(),
				HeaderComposite.getDest21EST(),HeaderComposite.getOriginatorEST(),HeaderComposite.getFilingTimeEST(),
				HeaderComposite.getSendAtEST(),HeaderComposite.getOriRefEST(),HeaderComposite.getBellEST());
	}
	
	public void hACP() {
		header(HeaderComposite.getPriorityACP(),HeaderComposite.getDest1ACP(),HeaderComposite.getDest2ACP(),
				HeaderComposite.getDest3ACP(),HeaderComposite.getDest4ACP(),HeaderComposite.getDest5ACP(),
				HeaderComposite.getDest6ACP(),HeaderComposite.getDest7ACP(),HeaderComposite.getDest8ACP(),
				HeaderComposite.getDest9ACP(),HeaderComposite.getDest10ACP(),HeaderComposite.getDest11ACP(),
				HeaderComposite.getDest12ACP(),HeaderComposite.getDest13ACP(),HeaderComposite.getDest14ACP(),
				HeaderComposite.getDest15ACP(),HeaderComposite.getDest16ACP(),HeaderComposite.getDest17ACP(),
				HeaderComposite.getDest18ACP(),HeaderComposite.getDest19ACP(),HeaderComposite.getDest20ACP(),
				HeaderComposite.getDest21ACP(),HeaderComposite.getOriginatorACP(),HeaderComposite.getFilingTimeACP(),
				HeaderComposite.getSendAtACP(),HeaderComposite.getOriRefACP(),HeaderComposite.getBellACP());
	}
	
	public void hLAM() {
		header(HeaderComposite.getPriorityLAM(),HeaderComposite.getDest1LAM(),HeaderComposite.getDest2LAM(),
				HeaderComposite.getDest3LAM(),HeaderComposite.getDest4LAM(),HeaderComposite.getDest5LAM(),
				HeaderComposite.getDest6LAM(),HeaderComposite.getDest7LAM(),HeaderComposite.getDest8LAM(),
				HeaderComposite.getDest9LAM(),HeaderComposite.getDest10LAM(),HeaderComposite.getDest11LAM(),
				HeaderComposite.getDest12LAM(),HeaderComposite.getDest13LAM(),HeaderComposite.getDest14LAM(),
				HeaderComposite.getDest15LAM(),HeaderComposite.getDest16LAM(),HeaderComposite.getDest17LAM(),
				HeaderComposite.getDest18LAM(),HeaderComposite.getDest19LAM(),HeaderComposite.getDest20LAM(),
				HeaderComposite.getDest21LAM(),HeaderComposite.getOriginatorLAM(),HeaderComposite.getFilingTimeLAM(),
				HeaderComposite.getSendAtLAM(),HeaderComposite.getOriRefLAM(),HeaderComposite.getBellLAM());
	}
	
	public void hRQP() {
		header(HeaderComposite.getPriorityRQP(),HeaderComposite.getDest1RQP(),HeaderComposite.getDest2RQP(),
				HeaderComposite.getDest3RQP(),HeaderComposite.getDest4RQP(),HeaderComposite.getDest5RQP(),
				HeaderComposite.getDest6RQP(),HeaderComposite.getDest7RQP(),HeaderComposite.getDest8RQP(),
				HeaderComposite.getDest9RQP(),HeaderComposite.getDest10RQP(),HeaderComposite.getDest11RQP(),
				HeaderComposite.getDest12RQP(),HeaderComposite.getDest13RQP(),HeaderComposite.getDest14RQP(),
				HeaderComposite.getDest15RQP(),HeaderComposite.getDest16RQP(),HeaderComposite.getDest17RQP(),
				HeaderComposite.getDest18RQP(),HeaderComposite.getDest19RQP(),HeaderComposite.getDest20RQP(),
				HeaderComposite.getDest21RQP(),HeaderComposite.getOriginatorRQP(),HeaderComposite.getFilingTimeRQP(),
				HeaderComposite.getSendAtRQP(),HeaderComposite.getOriRefRQP(),HeaderComposite.getBellRQP());
	}
	
	public void hRQS() {
		header(HeaderComposite.getPriorityRQS(),HeaderComposite.getDest1RQS(),HeaderComposite.getDest2RQS(),
				HeaderComposite.getDest3RQS(),HeaderComposite.getDest4RQS(),HeaderComposite.getDest5RQS(),
				HeaderComposite.getDest6RQS(),HeaderComposite.getDest7RQS(),HeaderComposite.getDest8RQS(),
				HeaderComposite.getDest9RQS(),HeaderComposite.getDest10RQS(),HeaderComposite.getDest11RQS(),
				HeaderComposite.getDest12RQS(),HeaderComposite.getDest13RQS(),HeaderComposite.getDest14RQS(),
				HeaderComposite.getDest15RQS(),HeaderComposite.getDest16RQS(),HeaderComposite.getDest17RQS(),
				HeaderComposite.getDest18RQS(),HeaderComposite.getDest19RQS(),HeaderComposite.getDest20RQS(),
				HeaderComposite.getDest21RQS(),HeaderComposite.getOriginatorRQS(),HeaderComposite.getFilingTimeRQS(),
				HeaderComposite.getSendAtRQS(),HeaderComposite.getOriRefRQS(),HeaderComposite.getBellRQS());
	}
	
	public void hSPL() {
		header(HeaderComposite.getPrioritySPL(),HeaderComposite.getDest1SPL(),HeaderComposite.getDest2SPL(),
				HeaderComposite.getDest3SPL(),HeaderComposite.getDest4SPL(),HeaderComposite.getDest5SPL(),
				HeaderComposite.getDest6SPL(),HeaderComposite.getDest7SPL(),HeaderComposite.getDest8SPL(),
				HeaderComposite.getDest9SPL(),HeaderComposite.getDest10SPL(),HeaderComposite.getDest11SPL(),
				HeaderComposite.getDest12SPL(),HeaderComposite.getDest13SPL(),HeaderComposite.getDest14SPL(),
				HeaderComposite.getDest15SPL(),HeaderComposite.getDest16SPL(),HeaderComposite.getDest17SPL(),
				HeaderComposite.getDest18SPL(),HeaderComposite.getDest19SPL(),HeaderComposite.getDest20SPL(),
				HeaderComposite.getDest21SPL(),HeaderComposite.getOriginatorSPL(),HeaderComposite.getFilingTimeSPL(),
				HeaderComposite.getSendAtSPL(),HeaderComposite.getOriRefSPL(),HeaderComposite.getBellSPL());
	}
	
	public void sAFTN() {
		sFree = ATSForms.getFreeA();
	}
	
	public void s3() {
		s3a = ATSForms.get3a();
		s3b = ATSForms.get3b();
		s3c = ATSForms.get3c();
	}
	
	public void s5() {
		s5a = ATSForms.get5a();
		s5b = ATSForms.get5b();
		s5c = ATSForms.get5c(); if (s5c.startsWith(" ")) { s5c = s5c.replaceFirst("\\s+", ""); }
	}
	
	public void s7() {
		s7a = ATSForms.get7a();
		s7b = ATSForms.get7b();
		s7c = ATSForms.get7c();
	}
	
	public void s8() {
		s8a = ATSForms.get8a();
		s8b = ATSForms.get8b();
	}
	
	public void s9() {
		s9a = ATSForms.get9a();
		s9b = ATSForms.get9b();
		s9c = ATSForms.get9c();
	}
	
	public void s10() {
		//Item10a=t10a, Item10b=t10b
	}
	
	public void s13() {
		s13a = ATSForms.get13a(); 
		//Item13b = s13b
	}
	
	public void s14() {
		s14a = ATSForms.get14a();
		s14b = ATSForms.get14b();
		s14c = ATSForms.get14c();
		s14d = ATSForms.get14d();
		s14e = ATSForms.get14e();
	}
	
	public void s15() {
		s15a = ATSForms.get15a();
		s15b = ATSForms.get15b(); 
		s15c = ATSForms.get15c().toString(); if (s15c.startsWith(" ")) { s15c = s15c.replaceFirst("\\s+", ""); }
	}
	
	public void s16a() {
		s16a = ATSForms.get16a(); 
	}
	
	public void s16() {
		s16a();
		//Item16b = s16b
		s16c = ATSForms.get16c();
		s16d = ATSForms.get16c2nd();
	}
	
	public void s17() {
		s17a = ATSForms.get17a();
		s17b = ATSForms.get17b();
		s17c = ATSForms.get17c();
	}
	
	public void proteksi18REG(String sPbn) {
    	//parsing PBN/
    	String s_PBN="", cobaPBN="";
    	String tPBN = sPbn;//FormNewReg.tPbn.getText();
    	char c_PBN[] = tPBN.toCharArray();
    	for (int i=0; i<tPBN.length(); i++) {
    		s_PBN = Character.toString(c_PBN[i]);	

    		//B1C1D1O1
    		if ((s_PBN.equals("1")) && (cobaPBN.equals("B") || cobaPBN.equals("C") || cobaPBN.equals("D") || cobaPBN.equals("O"))) { krit1+=cobaPBN+s_PBN; }
    		//B2C2D2O2
    		else if ((s_PBN.equals("2")) && (cobaPBN.equals("B") || cobaPBN.equals("C") || cobaPBN.equals("D") || cobaPBN.equals("O"))) { krit2+=cobaPBN+s_PBN; }
    		//B3C3D3O3
    		else if ((s_PBN.equals("3")) && (cobaPBN.equals("B") || cobaPBN.equals("C") || cobaPBN.equals("D") || cobaPBN.equals("O"))) { krit3+=cobaPBN+s_PBN; }
    		//B4C4D4O4
    		else if ((s_PBN.equals("4")) && (cobaPBN.equals("B") || cobaPBN.equals("C") || cobaPBN.equals("D") || cobaPBN.equals("O"))) { krit4+=cobaPBN+s_PBN; }
    		//B5C5
//    		else if ((s_PBN.equals("5")) && (cobaPBN.equals("B") || cobaPBN.equals("C"))) { krit5+=cobaPBN+s_PBN; }
    		else if ((s_PBN.equals("5")) && (cobaPBN.equals("B"))) { krit5+=cobaPBN+s_PBN; }
    		
    		cobaPBN=s_PBN;
    		s_PBN="";
    	} //end of for*/
    }
	
	public void validasipbn(String spbn) {
		tpbn = spbn;
		String s_tpbn="", crit="", coba="";
    	char c_tpbn[] = tpbn.toCharArray();
    	for (int i=0; i<tpbn.length(); i++) {
    		crit="";
    		s_tpbn = Character.toString(c_tpbn[i]);	
    	    
    		//single ABCDLOST
    		if ((tpbn.equals("A") || tpbn.equals("B") || tpbn.equals("C") || tpbn.equals("D") || tpbn.equals("L") || tpbn.equals("O") || tpbn.equals("S") || tpbn.equals("T")) && tpbn.length()==1) { crit="ngaco"; }  
    		//anything + ends ABCDLOST
    		else if (tpbn.endsWith("A") || tpbn.endsWith("B") || tpbn.endsWith("C") || tpbn.endsWith("D") || tpbn.endsWith("L") || tpbn.endsWith("O") || tpbn.endsWith("S") || tpbn.endsWith("T")) { crit="ngaco"; }
    		//reserved value A1,B1-6,C1-4,D1-4,L1,O1-4,S1-2,T1-2
    		else if ((s_tpbn.equals("1")) && (coba.equals("A") || coba.equals("L") || coba.equals("S") || coba.equals("T") || coba.equals("C") || coba.equals("D") || coba.equals("O") || coba.equals("B"))) { crit="sesuai"; }
    		else if ((s_tpbn.equals("2")) && (coba.equals("S") || coba.equals("T") || coba.equals("C") || coba.equals("D") || coba.equals("O") || coba.equals("B"))) { crit="sesuai"; }
    		else if ((s_tpbn.equals("3")) && (coba.equals("C") || coba.equals("D") || coba.equals("O") || coba.equals("B"))) { crit="sesuai"; }
    		else if ((s_tpbn.equals("4")) && (coba.equals("C") || coba.equals("D") || coba.equals("O") || coba.equals("B"))) { crit="sesuai"; }
    		else if ((s_tpbn.equals("5")) && (coba.equals("B"))) { crit="sesuai"; }
    		else if ((s_tpbn.equals("6")) && (coba.equals("B"))) { crit="sesuai"; }
    		//anything except ABCDLOST + 1-6
    		else if ((s_tpbn.equals("1")) && (!coba.equals("A") || !coba.equals("L") || !coba.equals("S") || !coba.equals("T") || !coba.equals("C") || !coba.equals("D") || !coba.equals("O") || !coba.equals("B"))) { crit="ngaco"; }
    		else if ((s_tpbn.equals("2")) && (!coba.equals("S") || !coba.equals("T") || !coba.equals("C") || !coba.equals("D") || !coba.equals("O") || !coba.equals("B"))) { crit="ngaco"; }
    		else if ((s_tpbn.equals("3")) && (!coba.equals("C") || !coba.equals("D") || !coba.equals("O") || !coba.equals("B"))) { crit="ngaco"; }
    		else if ((s_tpbn.equals("4")) && (!coba.equals("C") || !coba.equals("D") || !coba.equals("O") || !coba.equals("B"))) { crit="ngaco"; }
    		else if ((s_tpbn.equals("5")) && (!coba.equals("B"))) { crit="ngaco"; }
    		else if ((s_tpbn.equals("6")) && (!coba.equals("B"))) { crit="ngaco"; }
    		//ABCDLOST + !1-6
    		else if ((coba.equals("A") || coba.equals("L") || coba.equals("S") || coba.equals("T") || coba.equals("C") || coba.equals("D") || coba.equals("O") || coba.equals("B")) && !s_tpbn.equals("1")) { crit="ngaco"; }
    		else if ((coba.equals("S") || coba.equals("T") || coba.equals("C") || coba.equals("D") || coba.equals("O") || coba.equals("B")) && !s_tpbn.equals("1")) { crit="ngaco"; }
    		else if ((coba.equals("C") || coba.equals("D") || coba.equals("O") || coba.equals("B")) && !s_tpbn.equals("1")) { crit="ngaco"; }
    		else if ((coba.equals("C") || coba.equals("D") || coba.equals("O") || coba.equals("B")) && !s_tpbn.equals("1")) { crit="ngaco"; }
    		else if ((coba.equals("B")) && !s_tpbn.equals("1")) { crit="ngaco"; }
    		else if ((coba.equals("B")) && !s_tpbn.equals("1")) { crit="ngaco"; }
    		//![available]
    		else if (s_tpbn.equals("E") || s_tpbn.equals("F") || s_tpbn.equals("G") || s_tpbn.equals("H") || s_tpbn.equals("I") 
    				|| s_tpbn.equals("J") || s_tpbn.equals("K") || s_tpbn.equals("M") || s_tpbn.equals("N") || s_tpbn.equals("P") 
    				|| s_tpbn.equals("Q") || s_tpbn.equals("R") || s_tpbn.equals("U") || s_tpbn.equals("V") || s_tpbn.equals("W") 
    				|| s_tpbn.equals("X") || s_tpbn.equals("Y") || s_tpbn.equals("Z") 
    				|| s_tpbn.equals("0") || s_tpbn.equals("7") || s_tpbn.equals("8") || s_tpbn.equals("9")) { crit="ngaco"; }
    		
    		coba=s_tpbn;
    		s_tpbn="";
        	sprotekpbn+=crit;
    	} //end of for*/
	}
	
	public void validasi10a(String s10a) {
		t10a = s10a;
		String s_t10a="", crit="", coba="";
    	char c_t10a[] = t10a.toCharArray();
    	for (int i=0; i<t10a.length(); i++) {
    		crit="";
    		s_t10a = Character.toString(c_t10a[i]);	
    	    
    		//singlw EJMP
    		if ((t10a.equals("E") || t10a.equals("J") || t10a.equals("M") || t10a.equals("P")) && t10a.length()==1) { crit="ngaco"; }  
    		//anything + ends EJMP
    		else if (t10a.endsWith("E") || t10a.endsWith("J") || t10a.endsWith("M") || t10a.endsWith("P")) { crit="ngaco"; }
    		//reserved value E1-E3,J1-J7,M1-M3,P1-P9
    		else if ((s_t10a.equals("1")) && (coba.equals("E") || coba.equals("J") || coba.equals("M") || coba.equals("P"))) { crit="sesuai"; }
    		else if ((s_t10a.equals("2")) && (coba.equals("E") || coba.equals("J") || coba.equals("M") || coba.equals("P"))) { crit="sesuai"; }
    		else if ((s_t10a.equals("3")) && (coba.equals("E") || coba.equals("J") || coba.equals("M") || coba.equals("P"))) { crit="sesuai"; }
    		else if ((s_t10a.equals("4")) && (coba.equals("J") || coba.equals("P"))) { crit="sesuai"; }
    		else if ((s_t10a.equals("5")) && (coba.equals("J") || coba.equals("P"))) { crit="sesuai"; }
    		else if ((s_t10a.equals("6")) && (coba.equals("J") || coba.equals("P"))) { crit="sesuai"; }
    		else if ((s_t10a.equals("7")) && (coba.equals("J") || coba.equals("P"))) { crit="sesuai"; }
    		else if ((s_t10a.equals("8")) && (coba.equals("P"))) { crit="sesuai"; }
    		else if ((s_t10a.equals("9")) && (coba.equals("P"))) { crit="sesuai"; }
    		//anything except EJMP + 1-9
    		else if ((s_t10a.equals("1")) && (!coba.equals("E") || !coba.equals("J") || !coba.equals("M") || !coba.equals("P"))) { crit="ngaco"; }
    		else if ((s_t10a.equals("2")) && (!coba.equals("E") || !coba.equals("J") || !coba.equals("M") || !coba.equals("P"))) { crit="ngaco"; }
    		else if ((s_t10a.equals("3")) && (!coba.equals("E") || !coba.equals("J") || !coba.equals("M") || !coba.equals("P"))) { crit="ngaco"; }
    		else if ((s_t10a.equals("4")) && (!coba.equals("J") || !coba.equals("P"))) { crit="ngaco"; }
    		else if ((s_t10a.equals("5")) && (!coba.equals("J") || !coba.equals("P"))) { crit="ngaco"; }
    		else if ((s_t10a.equals("6")) && (!coba.equals("J") || !coba.equals("P"))) { crit="ngaco"; }
    		else if ((s_t10a.equals("7")) && (!coba.equals("J") || !coba.equals("P"))) { crit="ngaco"; }
    		else if ((s_t10a.equals("8")) && (!coba.equals("P"))) { crit="ngaco"; }
    		else if ((s_t10a.equals("9")) && (!coba.equals("P"))) { crit="ngaco"; }
    		//EJMP + !1-9
    		else if ((coba.equals("E") || coba.equals("J") || coba.equals("M") || coba.equals("P")) && !s_t10a.equals("1")) { crit="ngaco"; }
    		else if ((coba.equals("E") || coba.equals("J") || coba.equals("M") || coba.equals("P")) && !s_t10a.equals("2")) { crit="ngaco"; }
    		else if ((coba.equals("E") || coba.equals("J") || coba.equals("M") || coba.equals("P")) && !s_t10a.equals("3")) { crit="ngaco"; }
    		else if (( coba.equals("J") || coba.equals("P")) && !s_t10a.equals("4")) { crit="ngaco"; }
    		else if (( coba.equals("J") || coba.equals("P")) && !s_t10a.equals("5")) { crit="ngaco"; }
    		else if (( coba.equals("J") || coba.equals("P")) && !s_t10a.equals("6")) { crit="ngaco"; }
    		else if (( coba.equals("J") || coba.equals("P")) && !s_t10a.equals("7")) { crit="ngaco"; }
    		else if (( coba.equals("P")) && !s_t10a.equals("8")) { crit="ngaco"; }
    		else if (( coba.equals("P")) && !s_t10a.equals("9")) { crit="ngaco"; }
    		//unreserved value
    		else if (s_t10a.equals("Q")) { crit="ngaco"; } 
    		//another single reserved value
    		else if (s_t10a.equals("A") || s_t10a.equals("B") || s_t10a.equals("C") || s_t10a.equals("D") || s_t10a.equals("F") || s_t10a.equals("G") 
    				|| s_t10a.equals("H") || s_t10a.equals("I") || s_t10a.equals("K") || s_t10a.equals("L") || s_t10a.equals("O") || s_t10a.equals("R")
    				|| s_t10a.equals("T") || s_t10a.equals("U") || s_t10a.equals("V") || s_t10a.equals("W") || s_t10a.equals("X") || s_t10a.equals("Y")
    				|| s_t10a.equals("Z") || s_t10a.equals("N")) { crit="sesuai"; }
    		
    		coba=s_t10a;
    		s_t10a="";
        	sprotek10a+=crit;
    	} //end of for*/
	}
	
	public void validasi18_10(String s10b, String s10a) {
		String tes="new";
		kriteria10a="";criteria18="";kriteria="";criteria_sts="";krit1="";krit2="";krit3="";krit4="";krit5="";
		s_Reg="";s_Dof="";s_Typ="";s_Nav="";s_Com="";s_Dat="";s_Dep="";s_Dest="";s_Altn="";t10a="";t10b="";abal="";
			
    	t10b = s10b;
    	t10a = s10a;
    	//parsing tipe10a untuk COM/,DAT/,NAV
    	String s_t10a="", crit10a="", kriteria10a="",strdbguv="";
    	char c_t10a[] = t10a/*ATSForms.get10a()*/.toCharArray();

    	for (int i=0; i<t10a/*ATSForms.get10a()*/.length(); i++) {
    		crit10a="";
    		s_t10a = Character.toString(c_t10a[i]);	

    		if (s_t10a.equals("Z")) { crit10a="Z"; }
    		else if (s_t10a.equals("J")) { crit10a="J"; } 
    		else crit10a="sesuai";
    		s_t10a="";
        	kriteria10a+=crit10a;
    	}
    	//parsing tipe10b untuk SUR/
    	String s_t10b="", crit="", coba="";
    	char c_t10b[] = t10b.toCharArray();
    	for (int i=0; i<t10b.length(); i++) {
    		crit="";
    		s_t10b = Character.toString(c_t10b[i]);	
    	    
    		//DGBUV
    		if ((t10b.equals("D") || t10b.equals("G") || t10b.equals("B") || t10b.equals("U") || t10b.equals("V")) && t10b.length()==1) { crit="ngaco"; strdbguv="tdksesuai"; }  
    		// NACEHILPSX/FJKMOQRTWYZ + DGBUV
    		else if (t10b.endsWith("D") || t10b.endsWith("G") || t10b.endsWith("B") || t10b.endsWith("U") || t10b.endsWith("V")) { crit="ngaco"; strdbguv="tdksesuai";}
    		//D1 or G1
    		else if ((s_t10b.equals("1")) && (coba.equals("D") || coba.equals("G") || coba.equals("B") || coba.equals("U") || coba.equals("V"))) { crit="sesuai"; }
    		else if ((s_t10b.equals("2")) && (coba.equals("B") || coba.equals("U") || coba.equals("V"))) { crit="sesuai"; }
    		//NACEHILPSX,FJKMOQRTWYZ
    		else if ((s_t10b.equals("1")) && (!coba.equals("D") || !coba.equals("G"))) { crit="ngaco"; strdbguv="tdksesuai"; }
    		//BUKAN D1 or G1 [D/G2-dst / D/GA-dst]
    		else if ((coba.equals("D") || coba.equals("G")) && !s_t10b.equals("1")) { crit="ngaco"; strdbguv="tdksesuai"; }
    		//BUKAN B/U/V1/2
    		else if ( ((coba.equals("B") || coba.equals("U") || coba.equals("V")) && !s_t10b.equals("1")) || ((coba.equals("B") || coba.equals("U") || coba.equals("V")) && !s_t10b.equals("2"))) { crit="ngaco"; strdbguv="tdksesuai"; }
    		else if ((s_t10b.equals("1") || s_t10b.equals("2")) && (coba.equals("B") || coba.equals("U") || coba.equals("V"))) { crit="sesuai"; }
    		else if ((s_t10b.equals("2")) && (!coba.equals("B") || !coba.equals("U") || !coba.equals("V"))) { crit="ngaco"; strdbguv="tdksesuai"; } 
    		//--------------------
    		else if (s_t10b.equals("F") || s_t10b.equals("J") || s_t10b.equals("K") || s_t10b.equals("M") || s_t10b.equals("O") || s_t10b.equals("Q") || s_t10b.equals("R") || s_t10b.equals("T") || s_t10b.equals("W") || s_t10b.equals("Y") || s_t10b.equals("Z") || s_t10b.equals("0") || s_t10b.equals("3") || s_t10b.equals("4") || s_t10b.equals("5") || s_t10b.equals("6") || s_t10b.equals("7") || s_t10b.equals("8") || s_t10b.equals("9")) { crit="ngaco"; } 
    		else if (s_t10b.equals("N") || s_t10b.equals("A") || s_t10b.equals("C") || s_t10b.equals("E") || s_t10b.equals("H") || s_t10b.equals("I") || s_t10b.equals("L") || s_t10b.equals("P") || s_t10b.equals("S") || s_t10b.equals("X")) { crit="sesuai"; }
    		
    		coba=s_t10b;
    		s_t10b="";
        	kriteria+=crit;
        	sdbguv+=strdbguv;
    	} //end of for*/
    	//parsing tipe10a untuk DUPLIKAT
    	String str_t10a="", crit_t10a="", kriteria_t10a="", coba_t10a="",test="";
    	t10a = s10a/*ATSForms.get10a()*/;
    	if (!t10a.isEmpty()) {
	    	char ch_t10a[] = t10a.toCharArray();
	    	for (int i=0; i<t10a.length(); i++) {
	    		crit_t10a="";
	    		str_t10a = Character.toString(ch_t10a[i]);	

	    		//E123
	        	if (tes.compareTo("new")==0) {
		    		if (t10a.contains("E1")) { 
		    			if (t10a.indexOf("E1")!=-1) { 
		    				test = t10a.replaceFirst("E1", "~");
		    				test = test.replaceAll("E1", "");
		    				test = test.replaceFirst("~", "E1");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("E2")) { 
		    			if (t10a.indexOf("E2")!=-1) { 
		    				test = t10a.replaceFirst("E2", "~");
		    				test = test.replaceAll("E2", "");
		    				test = test.replaceFirst("~", "E2");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("E3")) {  
		    			if (t10a.indexOf("E3")!=-1) { 
		    				test = t10a.replaceFirst("E3", "~");
		    				test = test.replaceAll("E3", "");
		    				test = test.replaceFirst("~", "E3");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("M1")) { 
		    			if (t10a.indexOf("M1")!=-1) { 
		    				test = t10a.replaceFirst("M1", "~");
		    				test = test.replaceAll("M1", "");
		    				test = test.replaceFirst("~", "M1");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("M2")) { 
		    			if (t10a.indexOf("M2")!=-1) { 
		    				test = t10a.replaceFirst("M2", "~");
		    				test = test.replaceAll("M2", "");
		    				test = test.replaceFirst("~", "M2");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("M3")) {  
		    			if (t10a.indexOf("M3")!=-1) { 
		    				test = t10a.replaceFirst("M3", "~");
		    				test = test.replaceAll("M3", "");
		    				test = test.replaceFirst("~", "M3");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("J1")) { 
		    			if (t10a.indexOf("J1")!=-1) { 
		    				test = t10a.replaceFirst("J1", "~");
		    				test = test.replaceAll("J1", "");
		    				test = test.replaceFirst("~", "J1");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("J2")) { 
		    			if (t10a.indexOf("J2")!=-1) { 
		    				test = t10a.replaceFirst("J2", "~");
		    				test = test.replaceAll("J2", "");
		    				test = test.replaceFirst("~", "J2");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("J3")) {  
		    			if (t10a.indexOf("J3")!=-1) { 
		    				test = t10a.replaceFirst("J3", "~");
		    				test = test.replaceAll("J3", "");
		    				test = test.replaceFirst("~", "J3");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("J4")) { 
		    			if (t10a.indexOf("J4")!=-1) { 
		    				test = t10a.replaceFirst("J4", "~");
		    				test = test.replaceAll("J4", "");
		    				test = test.replaceFirst("~", "J4");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("J5")) { 
		    			if (t10a.indexOf("J5")!=-1) { 
		    				test = t10a.replaceFirst("J5", "~");
		    				test = test.replaceAll("J5", "");
		    				test = test.replaceFirst("~", "J5");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("J6")) {  
		    			if (t10a.indexOf("J6")!=-1) { 
		    				test = t10a.replaceFirst("J6", "~");
		    				test = test.replaceAll("J6", "");
		    				test = test.replaceFirst("~", "J6");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("J7")) { 
		    			if (t10a.indexOf("J7")!=-1) { 
		    				test = t10a.replaceFirst("J7", "~");
		    				test = test.replaceAll("J7", "");
		    				test = test.replaceFirst("~", "J7");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("P1")) { 
		    			if (t10a.indexOf("P1")!=-1) { 
		    				test = t10a.replaceFirst("P1", "~");
		    				test = test.replaceAll("P1", "");
		    				test = test.replaceFirst("~", "P1");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("P2")) { 
		    			if (t10a.indexOf("P2")!=-1) { 
		    				test = t10a.replaceFirst("P2", "~");
		    				test = test.replaceAll("P2", "");
		    				test = test.replaceFirst("~", "P2");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("P3")) {  
		    			if (t10a.indexOf("P3")!=-1) { 
		    				test = t10a.replaceFirst("P3", "~");
		    				test = test.replaceAll("P3", "");
		    				test = test.replaceFirst("~", "P3");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("P4")) { 
		    			if (t10a.indexOf("P4")!=-1) { 
		    				test = t10a.replaceFirst("P4", "~");
		    				test = test.replaceAll("P4", "");
		    				test = test.replaceFirst("~", "P4");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("P5")) { 
		    			if (t10a.indexOf("P5")!=-1) { 
		    				test = t10a.replaceFirst("P5", "~");
		    				test = test.replaceAll("P5", "");
		    				test = test.replaceFirst("~", "P5");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("P6")) {  
		    			if (t10a.indexOf("P6")!=-1) { 
		    				test = t10a.replaceFirst("P6", "~");
		    				test = test.replaceAll("P6", "");
		    				test = test.replaceFirst("~", "P6");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("P7")) { 
		    			if (t10a.indexOf("P7")!=-1) { 
		    				test = t10a.replaceFirst("P7", "~");
		    				test = test.replaceAll("P7", "");
		    				test = test.replaceFirst("~", "P7");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("P8")) {  
		    			if (t10a.indexOf("P8")!=-1) { 
		    				test = t10a.replaceFirst("P8", "~");
		    				test = test.replaceAll("P8", "");
		    				test = test.replaceFirst("~", "P8");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("P9")) { 
		    			if (t10a.indexOf("P9")!=-1) { 
		    				test = t10a.replaceFirst("P9", "~");
		    				test = test.replaceAll("P9", "");
		    				test = test.replaceFirst("~", "P9");
		    				t10a = test;
		    			}
		    		}
		    		
	    		} else if (tes.compareTo("present")==0) {
	    			if (t10a.contains("E")) {  
		    			if (t10a.indexOf("E")!=-1) { 
		    				test = t10a.replaceFirst("E", "~");
		    				test = test.replaceAll("E", "");
		    				test = test.replaceFirst("~", "E");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("M")) {  
		    			if (t10a.indexOf("M")!=-1) { 
		    				test = t10a.replaceFirst("M", "~");
		    				test = test.replaceAll("M", "");
		    				test = test.replaceFirst("~", "M");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("J")) {  
		    			if (t10a.indexOf("J")!=-1) { 
		    				test = t10a.replaceFirst("J", "~");
		    				test = test.replaceAll("J", "");
		    				test = test.replaceFirst("~", "J");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("P")) {  
		    			if (t10a.indexOf("P")!=-1) { 
		    				test = t10a.replaceFirst("P", "~");
		    				test = test.replaceAll("P", "");
		    				test = test.replaceFirst("~", "P");
		    				t10a = test;
		    			}
		    		}
		    		if (t10a.contains("Q")) { 
		    			if (t10a.indexOf("Q")!=-1) { 
		    				test = t10a.replaceFirst("Q", "~");
		    				test = test.replaceAll("Q", "");
		    				test = test.replaceFirst("~", "Q");
		    				t10a = test;
		    			}
		    		}
	        	}
	    		if (t10a.contains("S")) { 
	    			if (t10a.indexOf("S")!=-1) { 
	    				test = t10a.replaceFirst("S", "~");
	    				test = test.replaceAll("S", "");
	    				test = test.replaceFirst("~", "S");
	    				t10a = test;
	    			}
	    		}

	    		if (t10a.contains("A")) { 
	    			if (t10a.indexOf("A")!=-1) { 
	    				test = t10a.replaceFirst("A", "~");
	    				test = test.replaceAll("A", "");
	    				test = test.replaceFirst("~", "A");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("B")) { 
	    			if (t10a.indexOf("B")!=-1) { 
	    				test = t10a.replaceFirst("B", "~");
	    				test = test.replaceAll("B", "");
	    				test = test.replaceFirst("~", "B");
	    				t10a = test;
	    			}
	    		}	    		
	    		if (t10a.contains("C")) { 
	    			if (t10a.indexOf("C")!=-1) { 
	    				test = t10a.replaceFirst("C", "~");
	    				test = test.replaceAll("C", "");
	    				test = test.replaceFirst("~", "C");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("D")) { 
	    			if (t10a.indexOf("D")!=-1) { 
	    				test = t10a.replaceFirst("D", "~");
	    				test = test.replaceAll("D", "");
	    				test = test.replaceFirst("~", "D");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("F")) { 
	    			if (t10a.indexOf("F")!=-1) { 
	    				test = t10a.replaceFirst("F", "~");
	    				test = test.replaceAll("F", "");
	    				test = test.replaceFirst("~", "F");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("G")) { 
	    			if (t10a.indexOf("G")!=-1) { 
	    				test = t10a.replaceFirst("G", "~");
	    				test = test.replaceAll("G", "");
	    				test = test.replaceFirst("~", "G");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("H")) { 
	    			if (t10a.indexOf("H")!=-1) { 
	    				test = t10a.replaceFirst("H", "~");
	    				test = test.replaceAll("H", "");
	    				test = test.replaceFirst("~", "H");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("I")) { 
	    			if (t10a.indexOf("I")!=-1) { 
	    				test = t10a.replaceFirst("I", "~");
	    				test = test.replaceAll("I", "");
	    				test = test.replaceFirst("~", "I");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("K")) { 
	    			if (t10a.indexOf("K")!=-1) { 
	    				test = t10a.replaceFirst("K", "~");
	    				test = test.replaceAll("K", "");
	    				test = test.replaceFirst("~", "K");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("L")) { 
	    			if (t10a.indexOf("L")!=-1) { 
	    				test = t10a.replaceFirst("L", "~");
	    				test = test.replaceAll("L", "");
	    				test = test.replaceFirst("~", "L");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("N")) { 
	    			if (t10a.indexOf("N")!=-1) { 
	    				test = t10a.replaceFirst("N", "~");
	    				test = test.replaceAll("N", "");
	    				test = test.replaceFirst("~", "N");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("O")) { 
	    			if (t10a.indexOf("O")!=-1) { 
	    				test = t10a.replaceFirst("O", "~");
	    				test = test.replaceAll("O", "");
	    				test = test.replaceFirst("~", "O");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("R")) { 
	    			if (t10a.indexOf("R")!=-1) { 
	    				test = t10a.replaceFirst("R", "~");
	    				test = test.replaceAll("R", "");
	    				test = test.replaceFirst("~", "R");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("T")) { 
	    			if (t10a.indexOf("T")!=-1) { 
	    				test = t10a.replaceFirst("T", "~");
	    				test = test.replaceAll("T", "");
	    				test = test.replaceFirst("~", "T");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("U")) { 
	    			if (t10a.indexOf("U")!=-1) { 
	    				test = t10a.replaceFirst("U", "~");
	    				test = test.replaceAll("U", "");
	    				test = test.replaceFirst("~", "U");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("V")) { 
	    			if (t10a.indexOf("V")!=-1) { 
	    				test = t10a.replaceFirst("V", "~");
	    				test = test.replaceAll("V", "");
	    				test = test.replaceFirst("~", "V");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("W")) { 
	    			if (t10a.indexOf("W")!=-1) { 
	    				test = t10a.replaceFirst("W", "~");
	    				test = test.replaceAll("W", "");
	    				test = test.replaceFirst("~", "W");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("X")) { 
	    			if (t10a.indexOf("X")!=-1) { 
	    				test = t10a.replaceFirst("X", "~");
	    				test = test.replaceAll("X", "");
	    				test = test.replaceFirst("~", "X");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("Y")) { 
	    			if (t10a.indexOf("Y")!=-1) { 
	    				test = t10a.replaceFirst("Y", "~");
	    				test = test.replaceAll("Y", "");
	    				test = test.replaceFirst("~", "Y");
	    				t10a = test;
	    			}
	    		}
	    		if (t10a.contains("Z")) { 
	    			if (t10a.indexOf("Z")!=-1) { 
	    				test = t10a.replaceFirst("Z", "~");
	    				test = test.replaceAll("Z", "");
	    				test = test.replaceFirst("~", "Z");
	    				t10a = test;
	    			}
	    		}
//	    		System.out.println("coba_t10a:" + coba_t10a + "\tstr_t10a:" + str_t10a);
	    		coba_t10a=str_t10a;
	    		str_t10a="";
	        	kriteria_t10a+=crit_t10a;
	    	} //end of for*/
    	} //end of !t10a.isEmpty()
    	//parsing tipe10bBaru untuk DUPLIKAT
    	String str_t10b="", crit_t10b="", kriteria_t10b="", coba_t10b="";
    	if (!t10b.isEmpty()) {
	    	char ch_t10b[] = t10b.toCharArray();
	    	for (int i=0; i<t10b.length(); i++) {
	    		crit_t10b="";
	    		str_t10b = Character.toString(ch_t10b[i]);	
	    	    
	    		if (t10b.contains("A")) { 
	    			if (t10b.indexOf("A")!=-1) { 
	    				test = t10b.replaceFirst("A", "~");
	    				test = test.replaceAll("A", "");
	    				test = test.replaceFirst("~", "A");
	    				t10b = test;
	    			}
	    		}	    			    		
	    		if (t10b.contains("C")) { 
	    			if (t10b.indexOf("C")!=-1) { 
	    				test = t10b.replaceFirst("C", "~");
	    				test = test.replaceAll("C", "");
	    				test = test.replaceFirst("~", "C");
	    				t10b = test;
	    			}
	    		}
	    		if (t10b.contains("E")) { 
	    			if (t10b.indexOf("E")!=-1) { 
	    				test = t10b.replaceFirst("E", "~");
	    				test = test.replaceAll("E", "");
	    				test = test.replaceFirst("~", "E");
	    				t10b = test;
	    			}
	    		}
	    		if (t10b.contains("H")) { 
	    			if (t10b.indexOf("H")!=-1) { 
	    				test = t10b.replaceFirst("H", "~");
	    				test = test.replaceAll("H", "");
	    				test = test.replaceFirst("~", "H");
	    				t10b = test;
	    			}
	    		}
	    		if (t10b.contains("I")) { 
	    			if (t10b.indexOf("I")!=-1) { 
	    				test = t10b.replaceFirst("I", "~");
	    				test = test.replaceAll("I", "");
	    				test = test.replaceFirst("~", "I");
	    				t10b = test;
	    			}
	    		}
	    		if (t10b.contains("L")) { 
	    			if (t10b.indexOf("L")!=-1) { 
	    				test = t10b.replaceFirst("L", "~");
	    				test = test.replaceAll("L", "");
	    				test = test.replaceFirst("~", "L");
	    				t10b = test;
	    			}
	    		}
	    		if (t10b.contains("P")) { 
	    			if (t10b.indexOf("P")!=-1) { 
	    				test = t10b.replaceFirst("P", "~");
	    				test = test.replaceAll("P", "");
	    				test = test.replaceFirst("~", "P");
	    				t10b = test;
	    			}
	    		}
	    		if (t10b.contains("S")) { 
	    			if (t10b.indexOf("S")!=-1) { 
	    				test = t10b.replaceFirst("S", "~");
	    				test = test.replaceAll("S", "");
	    				test = test.replaceFirst("~", "S");
	    				t10b = test;
	    			}
	    		}
	    		if (t10b.contains("X")) { 
	    			if (t10b.indexOf("X")!=-1) { 
	    				test = t10b.replaceFirst("X", "~");
	    				test = test.replaceAll("X", "");
	    				test = test.replaceFirst("~", "X");
	    				t10b = test;
	    			}
	    		}
	    		if (t10b.contains("B1")) { 
	    			if (t10b.indexOf("B1")!=-1) { 
	    				test = t10b.replaceFirst("B1", "~");
	    				test = test.replaceAll("B1", "");
	    				test = test.replaceFirst("~", "B1");
	    				t10b = test;
	    			}
	    		}
	    		if (t10b.contains("B2")) { 
	    			if (t10b.indexOf("B2")!=-1) { 
	    				test = t10b.replaceFirst("B2", "~");
	    				test = test.replaceAll("B2", "");
	    				test = test.replaceFirst("~", "B2");
	    				t10b = test;
	    			}
	    		}
	    		if (t10b.contains("U1")) { 
	    			if (t10b.indexOf("U1")!=-1) { 
	    				test = t10b.replaceFirst("U1", "~");
	    				test = test.replaceAll("U1", "");
	    				test = test.replaceFirst("~", "U1");
	    				t10b = test;
	    			}
	    		}
	    		if (t10b.contains("U2")) { 
	    			if (t10b.indexOf("U2")!=-1) { 
	    				test = t10b.replaceFirst("U2", "~");
	    				test = test.replaceAll("U2", "");
	    				test = test.replaceFirst("~", "U2");
	    				t10b = test;
	    			}
	    		}
	    		if (t10b.contains("V1")) { 
	    			if (t10b.indexOf("V1")!=-1) { 
	    				test = t10b.replaceFirst("V1", "~");
	    				test = test.replaceAll("V1", "");
	    				test = test.replaceFirst("~", "V1");
	    				t10b = test;
	    			}
	    		}
	    		if (t10b.contains("V2")) { 
	    			if (t10b.indexOf("V2")!=-1) { 
	    				test = t10b.replaceFirst("V2", "~");
	    				test = test.replaceAll("V2", "");
	    				test = test.replaceFirst("~", "V2");
	    				t10b = test;
	    			}
	    		}
	    		if (t10b.contains("D1")) { 
	    			if (t10b.indexOf("D1")!=-1) { 
	    				test = t10b.replaceFirst("D1", "~");
	    				test = test.replaceAll("D1", "");
	    				test = test.replaceFirst("~", "D1");
	    				t10b = test;
	    			}
	    		}
	    		if (t10b.contains("G1")) { 
	    			if (t10b.indexOf("G1")!=-1) { 
	    				test = t10b.replaceFirst("G1", "~");
	    				test = test.replaceAll("G1", "");
	    				test = test.replaceFirst("~", "G1");
	    				t10b = test;
	    			}
	    		}
	    		
//	    		System.out.println("coba_t10b:" + coba_t10b + "\tstr_t10b:" + str_t10b);
	    		coba_t10b=str_t10b;
	    		str_t10b="";
	        	kriteria_t10b+=crit_t10b;
	    	} //end of for*/
    	} //end of !t10b.isEmpty()
    }
	
    public void proteksi18() {
    	//parsing tipe18c - hanya untuk proteksi
		String crit18="", criteria18="", s_Opr=""/*,s_Dof="", s_Reg=""*/, s_Eet="", s_Sel="", s_Per="",s_Ralt="", s_Rif="", s_Rmk="", s_Sts="";
		String type18 = ATSForms.get18();
		if (!type18.equals("")) {
			if (type18.startsWith(" ")) type18 = type18.replaceFirst(" ", "");
	        String x="";
			String sub[] = type18.split("\\s+");
			
			for (String token:sub) { //pisahin / sama isinya
				if (token.isEmpty()) token="";
  	        	if (token.contains("/")) { x=token; } else { x+=" "+token; }
				//-------------------------------------------------------------
				if (x.startsWith("EET/")) s_Eet = x.substring(4, x.length());
				else if (x.startsWith("RIF/")) s_Rif = x.substring(4, x.length());
				else if (x.startsWith("SEL/")) s_Sel = x.substring(4, x.length());
				else if (x.startsWith("OPR/")) s_Opr = x.substring(4, x.length());
				else if (x.startsWith("STS/")) s_Sts = x.substring(4, x.length());
				else if (x.startsWith("TYP/")) s_Typ = x.substring(4, x.length());
				else if (x.startsWith("PER/")) s_Per = x.substring(4, x.length());
				else if (x.startsWith("COM/")) s_Com = x.substring(4, x.length());
				else if (x.startsWith("DAT/")) s_Dat = x.substring(4, x.length());
				else if (x.startsWith("NAV/")) s_Nav = x.substring(4, x.length());
				else if (x.startsWith("DEP/")) s_Dep = x.substring(4, x.length());
				else if (x.startsWith("DEST/")) s_Dest = x.substring(5, x.length());
				else if (x.startsWith("ALTN/")) s_Altn = x.substring(5, x.length());
				else if (x.startsWith("RALT/")) s_Ralt = x.substring(5, x.length());
				else if (x.startsWith("RMK/")) s_Rmk = x.substring(4, x.length());
				//add
				else if (x.startsWith("REG/")) s_Reg = x.substring(4, x.length());
				else if (x.startsWith("DOF/")) s_Dof = x.substring(4, x.length());
				
				else if (x.contains("\n") || x.contains("\t") ||  x.contains(" ")) crit18="sesuai";
				else if (!x.startsWith("EET/") || !x.startsWith("RIF/") || !x.startsWith("SEL/") || !x.startsWith("OPR/") || 
						!x.startsWith("STS/") || !x.startsWith("TYP/") || !x.startsWith("PER/") || !x.startsWith("COM/") || 
						!x.startsWith("DAT/") || !x.startsWith("NAV/") || !x.startsWith("DEP/") || !x.startsWith("DEST/") || 
						!x.startsWith("ALTN/") || !x.startsWith("RALT/") || !x.startsWith("RMK/")) crit18="ngaco";
				
				criteria18+=" "+crit18;
			} //end of for sub[i]
		} else {
			s_Opr="";s_Nav="";s_Com="";s_Dat="";s_Dep="";s_Dest="";s_Dof="";s_Reg="";s_Eet="";s_Sel="";s_Typ="";s_Per="";s_Altn="";s_Ralt="";s_Rif="";s_Rmk="";s_Sts="";
		}
		
		if (s_Eet.startsWith(" ")) s_Eet = s_Eet.replaceFirst(" ", "");
		if (s_Rif.startsWith(" ")) s_Rif = s_Rif.replaceFirst(" ", "");
		if (s_Sel.startsWith(" ")) s_Sel = s_Sel.replaceFirst(" ", "");
		if (s_Opr.startsWith(" ")) s_Opr = s_Opr.replaceFirst(" ", "");
		if (s_Sts.startsWith(" ")) s_Sts = s_Sts.replaceFirst(" ", "");
		if (s_Typ.startsWith(" ")) s_Typ = s_Typ.replaceFirst(" ", "");
		if (s_Per.startsWith(" ")) s_Per = s_Per.replaceFirst(" ", "");
		if (s_Com.startsWith(" ")) s_Com = s_Com.replaceFirst(" ", "");
		if (s_Dat.startsWith(" ")) s_Dat = s_Dat.replaceFirst(" ", "");
		if (s_Nav.startsWith(" ")) s_Nav = s_Nav.replaceFirst(" ", "");
		if (s_Dep.startsWith(" ")) s_Dep = s_Dep.replaceFirst(" ", "");
		if (s_Dest.startsWith(" ")) s_Dest = s_Dest.replaceFirst(" ", "");
		if (s_Altn.startsWith(" ")) s_Altn = s_Altn.replaceFirst(" ", "");
		if (s_Ralt.startsWith(" ")) s_Ralt = s_Ralt.replaceFirst(" ", "");
		if (s_Rmk.startsWith(" ")) s_Rmk = s_Rmk.replaceFirst(" ", "");
		//add
		if (s_Reg.startsWith(" ")) s_Reg = s_Reg.replaceFirst(" ", "");
		if (s_Dof.startsWith(" ")) s_Dof = s_Dof.replaceFirst(" ", "");
		//disusun
		if (s_Eet.isEmpty()) s_Eet=""; else s_Eet=" EET/"+s_Eet;
		if (s_Rif.isEmpty()) s_Rif=""; else s_Rif=" RIF/"+s_Rif;
		if (s_Sel.isEmpty()) s_Sel=""; else s_Sel=" SEL/"+s_Sel;
		if (s_Opr.isEmpty()) s_Opr=""; else s_Opr=" OPR/"+s_Opr;
		if (s_Sts.isEmpty()) s_Sts=""; else s_Sts=" STS/"+s_Sts;
		if (s_Typ.isEmpty()) s_Typ=""; else s_Typ=" TYP/"+s_Typ;
		if (s_Per.isEmpty()) s_Per=""; else s_Per=" PER/"+s_Per;
		if (s_Com.isEmpty()) s_Com=""; else s_Com=" COM/"+s_Com;
		if (s_Dat.isEmpty()) s_Dat=""; else s_Dat=" DAT/"+s_Dat;
		if (s_Nav.isEmpty()) s_Nav=""; else s_Nav=" NAV/"+s_Nav;
		if (s_Dep.isEmpty()) s_Dep=""; else s_Dep=" DEP/"+s_Dep;
		if (s_Dest.isEmpty()) s_Dest=""; else s_Dest=" DEST/"+s_Dest;
		if (s_Altn.isEmpty()) s_Altn=""; else s_Altn=" ALTN/"+s_Altn;
		if (s_Ralt.isEmpty()) s_Ralt=""; else s_Ralt=" RALT/"+s_Ralt;
		if (s_Rmk.isEmpty()) s_Rmk=""; else s_Rmk=" RMK/"+s_Rmk;
		//add
		if (s_Reg.isEmpty()) s_Reg=""; else s_Reg=" REG/"+s_Reg;
		if (s_Dof.isEmpty()) s_Dof=""; else s_Dof=" DOF/"+s_Dof;
		//--
		abal = s_Eet+s_Rif+s_Sel+s_Opr+s_Sts+s_Typ+s_Per+s_Com+s_Dat+s_Nav+s_Dep+s_Dest+s_Altn+s_Ralt+s_Rmk;
		if (abal.startsWith(" ")) abal = abal.replaceFirst(" ", "");
		//parsing STS/
    	String crit_sts="";
    	String s_sts = ATSForms.get18tSts();
		if (!s_sts.equals("")) {
	        String x="";
			String sub[] = s_sts.split(" ");
			
			for (int i=0; i<sub.length; i++) {
				x=sub[i];
				if (x.equals("ALTRV") || x.equals("ATFMX") || x.equals("FFR") || x.equals("FLTCK") || x.equals("HAZMAT") || x.equals("HEAD") || x.equals("HOSP") || x.equals("HUM") || x.equals("MARSA") || x.equals("MEDEVAC") || x.equals("NONRVSM") || x.equals("SAR") || x.equals("STATE")) crit_sts="sesuai"; else crit_sts="ngaco";
				criteria_sts+=" "+crit_sts;
			} //end of for sub[i]
		}
    	//parsing PBN/
    	String s_PBN="", cobaPBN="";
    	String tPBN = ATSForms.get18tPbn();
    	char c_PBN[] = tPBN.toCharArray();
    	for (int i=0; i<tPBN.length(); i++) {
    		s_PBN = Character.toString(c_PBN[i]);	

    		//B1C1D1O1
    		if ((s_PBN.equals("1")) && (cobaPBN.equals("B") || cobaPBN.equals("C") || cobaPBN.equals("D") || cobaPBN.equals("O"))) { krit1+=cobaPBN+s_PBN; }
    		//B2C2D2O2
    		else if ((s_PBN.equals("2")) && (cobaPBN.equals("B") || cobaPBN.equals("C") || cobaPBN.equals("D") || cobaPBN.equals("O"))) { krit2+=cobaPBN+s_PBN; }
    		//B3C3D3O3
    		else if ((s_PBN.equals("3")) && (cobaPBN.equals("B") || cobaPBN.equals("C") || cobaPBN.equals("D") || cobaPBN.equals("O"))) { krit3+=cobaPBN+s_PBN; }
    		//B4C4D4O4
    		else if ((s_PBN.equals("4")) && (cobaPBN.equals("B") || cobaPBN.equals("C") || cobaPBN.equals("D") || cobaPBN.equals("O"))) { krit4+=cobaPBN+s_PBN; }
    		//B5C5
//    		else if ((s_PBN.equals("5")) && (cobaPBN.equals("B") || cobaPBN.equals("C"))) { krit5+=cobaPBN+s_PBN; }
    		else if ((s_PBN.equals("5")) && (cobaPBN.equals("B"))) { krit5+=cobaPBN+s_PBN; }
    		
    		cobaPBN=s_PBN;
    		s_PBN="";
    	} //end of for*/
    	
    	String s10a = ATSForms.get10a();
    	String strCom = ATSForms.get18tCom();
    	String strNav = ATSForms.get18tNav();
    	String strDat = ATSForms.get18tDat();
    	
    	if (s10a.contains("Z") && strCom.isEmpty() && strNav.isEmpty() && strDat.isEmpty()) {
    		s10aZ="ngaco";
    	}
    	else if (s10a.contains("Z") && (!strCom.isEmpty() || !strNav.isEmpty() || !strDat.isEmpty())) {
    		s10aZ="ok";
    	}
    	
    	//System.out.println("COM::" + strCom + "@" + "NAV::" + strNav + "@" + "DAT::" + strDat + "@");
    }
    
//    public void proteksi18msgall(String msgBody) {
//    	//parsing tipe18c - hanya untuk proteksi
//		String crit18="", criteria18="", s_Opr="",s_Dof="", s_Reg="", s_Eet="", s_Sel="", s_Per="",s_Ralt="", s_Rif="", s_Rmk="", s_Sts="";
//		String type18 = msgBody;
//		if (!type18.equals("")) {
//			if (type18.startsWith(" ")) type18 = type18.replaceFirst(" ", "");
//	        String x="";
//			String sub[] = type18.split("\\s+");
//			
//			for (String token:sub) { //pisahin / sama isinya
//				if (token.isEmpty()) token="";
//  	        	if (token.contains("/")) { x=token; } else { x+=" "+token; }
//				//-------------------------------------------------------------
//				if (x.startsWith("EET/")) s_Eet = x.substring(4, x.length());
//				else if (x.startsWith("RIF/")) s_Rif = x.substring(4, x.length());
//				else if (x.startsWith("SEL/")) s_Sel = x.substring(4, x.length());
//				else if (x.startsWith("OPR/")) s_Opr = x.substring(4, x.length());
//				else if (x.startsWith("STS/")) s_Sts = x.substring(4, x.length());
//				else if (x.startsWith("TYP/")) s_Typ = x.substring(4, x.length());
//				else if (x.startsWith("PER/")) s_Per = x.substring(4, x.length());
//				else if (x.startsWith("COM/")) s_Com = x.substring(4, x.length());
//				else if (x.startsWith("DAT/")) s_Dat = x.substring(4, x.length());
//				else if (x.startsWith("NAV/")) s_Nav = x.substring(4, x.length());
//				else if (x.startsWith("DEP/")) s_Dep = x.substring(4, x.length());
//				else if (x.startsWith("DEST/")) s_Dest = x.substring(5, x.length());
//				else if (x.startsWith("ALTN/")) s_Altn = x.substring(5, x.length());
//				else if (x.startsWith("RALT/")) s_Ralt = x.substring(5, x.length());
//				else if (x.startsWith("RMK/")) s_Rmk = x.substring(4, x.length());
//
//				else if (x.contains("\n") || x.contains("\t") ||  x.contains(" ")) crit18="sesuai";
//				else if (!x.startsWith("EET/") || !x.startsWith("RIF/") || !x.startsWith("SEL/") || !x.startsWith("OPR/") || 
//						!x.startsWith("STS/") || !x.startsWith("TYP/") || !x.startsWith("PER/") || !x.startsWith("COM/") || 
//						!x.startsWith("DAT/") || !x.startsWith("NAV/") || !x.startsWith("DEP/") || !x.startsWith("DEST/") || 
//						!x.startsWith("ALTN/") || !x.startsWith("RALT/") || !x.startsWith("RMK/")) crit18="ngaco";
//				
//				criteria18+=" "+crit18;
//			} //end of for sub[i]
//		} else {
//			s_Opr="";s_Nav="";s_Com="";s_Dat="";s_Dep="";s_Dest="";s_Dof="";s_Reg="";s_Eet="";s_Sel="";s_Typ="";s_Per="";s_Altn="";s_Ralt="";s_Rif="";s_Rmk="";s_Sts="";
//		}
//		
//		if (s_Eet.startsWith(" ")) s_Eet = s_Eet.replaceFirst(" ", "");
//		if (s_Rif.startsWith(" ")) s_Rif = s_Rif.replaceFirst(" ", "");
//		if (s_Sel.startsWith(" ")) s_Sel = s_Sel.replaceFirst(" ", "");
//		if (s_Opr.startsWith(" ")) s_Opr = s_Opr.replaceFirst(" ", "");
//		if (s_Sts.startsWith(" ")) s_Sts = s_Sts.replaceFirst(" ", "");
//		if (s_Typ.startsWith(" ")) s_Typ = s_Typ.replaceFirst(" ", "");
//		if (s_Per.startsWith(" ")) s_Per = s_Per.replaceFirst(" ", "");
//		if (s_Com.startsWith(" ")) s_Com = s_Com.replaceFirst(" ", "");
//		if (s_Dat.startsWith(" ")) s_Dat = s_Dat.replaceFirst(" ", "");
//		if (s_Nav.startsWith(" ")) s_Nav = s_Nav.replaceFirst(" ", "");
//		if (s_Dep.startsWith(" ")) s_Dep = s_Dep.replaceFirst(" ", "");
//		if (s_Dest.startsWith(" ")) s_Dest = s_Dest.replaceFirst(" ", "");
//		if (s_Altn.startsWith(" ")) s_Altn = s_Altn.replaceFirst(" ", "");
//		if (s_Ralt.startsWith(" ")) s_Ralt = s_Ralt.replaceFirst(" ", "");
//		if (s_Rmk.startsWith(" ")) s_Rmk = s_Rmk.replaceFirst(" ", "");
//		//disusun
//		if (s_Eet.isEmpty()) s_Eet=""; else s_Eet=" EET/"+s_Eet;
//		if (s_Rif.isEmpty()) s_Rif=""; else s_Rif=" RIF/"+s_Rif;
//		if (s_Sel.isEmpty()) s_Sel=""; else s_Sel=" SEL/"+s_Sel;
//		if (s_Opr.isEmpty()) s_Opr=""; else s_Opr=" OPR/"+s_Opr;
//		if (s_Sts.isEmpty()) s_Sts=""; else s_Sts=" STS/"+s_Sts;
//		if (s_Typ.isEmpty()) s_Typ=""; else s_Typ=" TYP/"+s_Typ;
//		if (s_Per.isEmpty()) s_Per=""; else s_Per=" PER/"+s_Per;
//		if (s_Com.isEmpty()) s_Com=""; else s_Com=" COM/"+s_Com;
//		if (s_Dat.isEmpty()) s_Dat=""; else s_Dat=" DAT/"+s_Dat;
//		if (s_Nav.isEmpty()) s_Nav=""; else s_Nav=" NAV/"+s_Nav;
//		if (s_Dep.isEmpty()) s_Dep=""; else s_Dep=" DEP/"+s_Dep;
//		if (s_Dest.isEmpty()) s_Dest=""; else s_Dest=" DEST/"+s_Dest;
//		if (s_Altn.isEmpty()) s_Altn=""; else s_Altn=" ALTN/"+s_Altn;
//		if (s_Ralt.isEmpty()) s_Ralt=""; else s_Ralt=" RALT/"+s_Ralt;
//		if (s_Rmk.isEmpty()) s_Rmk=""; else s_Rmk=" RMK/"+s_Rmk;
//		//--
//		ebel = s_Eet+s_Rif+s_Sel+s_Opr+s_Sts+s_Typ+s_Per+s_Com+s_Dat+s_Nav+s_Dep+s_Dest+s_Altn+s_Ralt+s_Rmk;
//		if (ebel.startsWith(" ")) ebel = ebel.replaceFirst(" ", "");
//		//parsing STS/
//    	String crit_sts="";
//    	String s_sts = ATSForms.get18tSts();
//		if (!s_sts.equals("")) {
//	        String x="";
//			String sub[] = s_sts.split(" ");
//			
//			for (int i=0; i<sub.length; i++) {
//				x=sub[i];
//				if (x.equals("ALTRV") || x.equals("ATFMX") || x.equals("FFR") || x.equals("FLTCK") || x.equals("HAZMAT") || x.equals("HEAD") || x.equals("HOSP") || x.equals("HUM") || x.equals("MARSA") || x.equals("MEDEVAC") || x.equals("NONRVSM") || x.equals("SAR") || x.equals("STATE")) crit_sts="sesuai"; else crit_sts="ngaco";
//				criteria_sts+=" "+crit_sts;
//			} //end of for sub[i]
//		}
//    	//parsing PBN/
//    	String s_PBN="", cobaPBN="";
//    	String tPBN = ATSForms.get18tPbn();
//    	char c_PBN[] = tPBN.toCharArray();
//    	for (int i=0; i<tPBN.length(); i++) {
//    		s_PBN = Character.toString(c_PBN[i]);	
//
//    		//B1C1D1O1
//    		if ((s_PBN.equals("1")) && (cobaPBN.equals("B") || cobaPBN.equals("C") || cobaPBN.equals("D") || cobaPBN.equals("O"))) { krit1+=cobaPBN+s_PBN; }
//    		//B2C2D2O2
//    		else if ((s_PBN.equals("2")) && (cobaPBN.equals("B") || cobaPBN.equals("C") || cobaPBN.equals("D") || cobaPBN.equals("O"))) { krit2+=cobaPBN+s_PBN; }
//    		//B3C3D3O3
//    		else if ((s_PBN.equals("3")) && (cobaPBN.equals("B") || cobaPBN.equals("C") || cobaPBN.equals("D") || cobaPBN.equals("O"))) { krit3+=cobaPBN+s_PBN; }
//    		//B4C4D4O4
//    		else if ((s_PBN.equals("4")) && (cobaPBN.equals("B") || cobaPBN.equals("C") || cobaPBN.equals("D") || cobaPBN.equals("O"))) { krit4+=cobaPBN+s_PBN; }
//    		//B5C5
////    		else if ((s_PBN.equals("5")) && (cobaPBN.equals("B") || cobaPBN.equals("C"))) { krit5+=cobaPBN+s_PBN; }
//    		else if ((s_PBN.equals("5")) && (cobaPBN.equals("B"))) { krit5+=cobaPBN+s_PBN; }
//    		
//    		cobaPBN=s_PBN;
//    		s_PBN="";
//    	} //end of for*/
//    	
//    	String s10a = ATSForms.get10a();
//    	String strCom = ATSForms.get18tCom();
//    	String strNav = ATSForms.get18tNav();
//    	String strDat = ATSForms.get18tDat();
//    	
//    	if (s10a.contains("Z") && strCom.isEmpty() && strNav.isEmpty() && strDat.isEmpty()) {
//    		s10aZ="ngaco";
//    	}
//    	else if (s10a.contains("Z") && (!strCom.isEmpty() || !strNav.isEmpty() || !strDat.isEmpty())) {
//    		s10aZ="ok";
//    	}
//    	
//    	//System.out.println("COM::" + strCom + "@" + "NAV::" + strNav + "@" + "DAT::" + strDat + "@");
//    }
	
	public void s19() {
		//Item19a=s19a
		s19b = ATSForms.get19b();
		s19cUHF = ATSForms.get19cUHF();
		s19cVHF = ATSForms.get19cVHF();
		s19cELT = ATSForms.get19cELT();
		s19dS = ATSForms.get19dS();
		s19dP = ATSForms.get19dP();
		s19dD = ATSForms.get19dD();
		s19dM = ATSForms.get19dM();
		s19dJ = ATSForms.get19dJ();
		s19eJ = ATSForms.get19eJ();
		s19eL = ATSForms.get19eL();
		s19eF = ATSForms.get19eF();
		s19eU = ATSForms.get19eU();
		s19eV = ATSForms.get19eV();
		s19fD = ATSForms.get19fD();
		s19fNum = ATSForms.get19fNum();
		s19fCap = ATSForms.get19fCap();
		if (s19fNum.isEmpty()) s19fCap="";
		s19fCov = ATSForms.get19fCov();
		s19fCol = ATSForms.get19fCol().toString(); if (s19fCol.startsWith(" ")) { s19fCol = s19fCol.replaceFirst("\\s+", ""); }
		s19g = ATSForms.get19g().toString(); if (s19g.startsWith(" ")) { s19g = s19g.replaceFirst("\\s+", ""); }
		s19h = ATSForms.get19hN();
		s19Rmk = ATSForms.get19Rem().toString(); if (s19Rmk.startsWith(" ")) { s19Rmk = s19Rmk.replaceFirst("\\s+", ""); }
		s19i = ATSForms.get19i().toString(); if (s19i.startsWith(" ")) { s19i = s19i.replaceFirst("\\s+", ""); }
	}
	
	public void s20() {
		s20 = ATSForms.get20();
	}
	
	public void s21() {
		s21 = ATSForms.get21();
	}
	
	public void s22() {
		s22 = ATSForms.get22();
	}
	
	public void sSpace() {
		sSpace = ATSForms.getSpace(); if (sSpace.startsWith(" ")) { sSpace = sSpace.replaceFirst("\\s+", ""); }
	}
	
	public void sFreeATS() {
		sFree = ATSForms.getFreeA();
	}
	
	// tabel: air_message
	// msgall diisi segeluntung berita yang akan dikirim
	public void setInsertATS(
			String sPriority,String sDest1,String sDest2,String sDest3,String sDest4,String sDest5,String sDest6,
			String sDest7,String sDest8,String sDest9,String sDest10,String sDest11,String sDest12,String sDest13,String sDest14,
			String sDest15,String sDest16,String sDest17,String sDest18,String sDest19,String sDest20,String sDest21,
			String sOriginator,String sOriRef,String sFiling,String sJam,String tanggal,String sFiledby,String sTblName,String sStatus,
			String s3a,String s3b,String s3c,String s5a,String s5b,String s5c,String s7a,String s7b,String s7c,
			String s8a,String s8b,String s9a,String s9b,String s9c,String s10a,String s10b,String s13a,String s13b,
			String s14a,String s14b,String s14c,String s14d,String s14e,String s15a,String s15b,String s15c,
			String s16a,String s16b,String s16c,String s16d,String s17a,String s17b,String s17c,
			String s18a,String s18b,String s18c,String s19a,String s19b,String s19cUHF,String s19cVHF,String s19cELT,
	    	String s19dS,String s19dP,String s19dD,String s19dM,String s19dJ,String s19eJ,
			String s19eL,String s19eF,String s19eU,String s19eV,String s19fD,
			String s19fNum,String s19fCap,String s19fCov,String s19fCol,String s19g,String s19h,String s19Rem,String s19i,
			String s20,String s21,String s22,String sSpace,
			String sTrkName,String sTrkId,String sTrkDate,String sTrkStatus,String sTrkAct,String sTrkExp,String sTrkWay,
			String sTrkLev,String sTrkRts,String sTrkRmk,String sFree,/*String sSeqNo,*/String sMsgAll,String sBell,String sNote) {

		String sIo="1";
		String atstyp="1";
//		System.out.println("tipe18=" + s18c + "#");
		//perintah untuk meng-insert berita ATS dan AIDC Message (tabel: air_message)
		String insert = "INSERT INTO air_message (priority,destination1,destination2,destination3,destination4,destination5,destination6," +
				"destination7,destination8,destination9,destination10,destination11,destination12,destination13,destination14," +
				"destination15,destination16,destination17,destination18,destination19,destination20,destination21,originator,ori_ref," +
				"filing_time,jam,tgl,filedby,tbl_name,io,status," +
				"type3a,type3b,type3c,type5a,type5b,type5c,type7a,type7b,type7c,type8a,type8b,type9a,type9b,type9c,type10a,type10b," +
				"type13a,type13b,type14a,type14b,type14c,type14d,type14e,type15a,type15b,type15c,type16a,type16b,type16c,type16c_2nd," +
				"type17a,type17b,type17c,type18a,type18b,type18,type19a,type19b,type19cUHF,type19cVHF,type19cELT," +
				"type19dS,type19dP,type19dD,type19dM,type19dJ,type19eJ,type19eL,type19eF,type19eU,type19eV," +
				"type19fD,type19f_number,type19f_capacity,type19f_cover,type19f_colour,type19g,type19hN,type19Remarks,type19i," +
				"type20,type21,type22," +
				"track_name,track_id,track_date,track_status,track_act,track_exp,track_waypoint,track_level,track_rts,freetext," +
				"space_reserved,atstyp,"/*sequence_no,*/+"msgall,bell,note) VALUES " +
				"('"+sPriority+"','"+sDest1+"','"+sDest2+"','"+sDest3+"','"+sDest4+"','"+sDest5+"','"+sDest6+"','"+sDest7+"','"+sDest8+"','"+
				sDest9+"','"+sDest10+"','"+sDest11+"','"+sDest12+"','"+sDest13+"','"+sDest14+"','"+sDest15+"','"+sDest16+"','"+sDest17+"','"+
				sDest18+"','"+sDest19+"','"+sDest20+"','"+sDest21+"','"+sOriginator+"','"+sOriRef+"','"+
				sFiling+"','"+sJam+"','"+tanggal+"','"+sFiledby+"','"+sTblName+"','"+sIo+"','"+sStatus+"','"+
				s3a+"','"+s3b+"','"+s3c+"','"+s5a+"','"+s5b+"','"+s5c+"','"+s7a+"','"+s7b+"','"+s7c+"','"+s8a+"','"+s8b+"','"+
				s9a+"','"+s9b+"','"+s9c+"','"+s10a+"','"+s10b+"','"+s13a+"','"+s13b+"','"+s14a+"','"+s14b+"','"+s14c+"','"+s14d+"','"+s14e+"','"+
				s15a+"','"+s15b+"','"+s15c+"','"+s16a+"','"+s16b+"','"+s16c+"','"+s16d+"','"+s17a+"','"+s17b+"','"+s17c+"','"+
				s18a+"','"+s18b+"','"+s18c+"','"+s19a+"','"+s19b+"','"+s19cUHF+"','"+s19cVHF+"','"+s19cELT+"','"+
				s19dS+"','"+s19dP+"','"+s19dD+"','"+s19dM+"','"+s19dJ+"','"+s19eJ+"','"+s19eL+"','"+s19eF+"','"+s19eU+"','"+s19eV+"','"+
				s19fD+"','"+s19fNum+"','"+s19fCap+"','"+s19fCov+"','"+s19fCol+"','"+s19g+"','"+s19h+"','"+s19Rem+"','"+s19i+"','"+
				s20+"','"+s21+"','"+s22+"','"+
				sTrkName+"','"+sTrkId+"','"+sTrkDate+"','"+sTrkStatus+"','"+sTrkAct+"','"+sTrkExp+"','"+sTrkWay+"','"+sTrkLev+"','"+sTrkRts+"','"+
				sFree+"','"+sSpace+"','"+atstyp+"','"+/*sSeqNo+"','"+*/sMsgAll+"','"+sBell+"','"+sNote+"')";
		//System.out.println("sblm masuk:" + insert + "*");
		jdbc.connect(insert);
	}
	
	public void setUpdateATS(
			String sPriority,String sDest1,String sDest2,String sDest3,String sDest4,String sDest5,String sDest6,
			String sDest7,String sDest8,String sDest9,String sDest10,String sDest11,String sDest12,String sDest13,String sDest14,
			String sDest15,String sDest16,String sDest17,String sDest18,String sDest19,String sDest20,String sDest21,
			String sOriginator,String sOriRef,String sFiling,String sJam,String tanggal,String sFiledby,String sTblName,String sStatus,
			String s3a,String s3b,String s3c,String s5a,String s5b,String s5c,String s7a,String s7b,String s7c,
			String s8a,String s8b,String s9a,String s9b,String s9c,String s10a,String s10b,String s13a,String s13b,
			String s14a,String s14b,String s14c,String s14d,String s14e,String s15a,String s15b,String s15c,
			String s16a,String s16b,String s16c,String s16d,String s17a,String s17b,String s17c,
			String s18a,String s18b,String s18c,String s19a,String s19b,String s19cUHF,String s19cVHF,String s19cELT,
	    	String s19dS,String s19dP,String s19dD,String s19dM,String s19dJ,String s19eJ,
			String s19eL,String s19eF,String s19eU,String s19eV,String s19fD,
			String s19fNum,String s19fCap,String s19fCov,String s19fCol,String s19g,String s19h,String s19Rem,String s19i,
			String s20,String s21,String s22,String sSpace,
			String sTrkName,String sTrkId,String sTrkDate,String sTrkStatus,String sTrkAct,String sTrkExp,String sTrkWay,
			String sTrkLev,String sTrkRts,String sTrkRmk,String sFree,String sMsgAll,
			String ID,String data,String sAtstyp,String sBell) {
		
		sAtstyp="1"; // FPL new version
		String tipe = TableOutgoing.GetMsgType();
		
		sFiledby="";
		if (tipe.endsWith("ALR") || tipe.endsWith("RCF") || tipe.endsWith("FPL") ||
				tipe.endsWith("DLA") || tipe.endsWith("CHG") || tipe.endsWith("CNL") ||
				tipe.endsWith("DEP") || tipe.endsWith("ARR") || tipe.endsWith("CDN") ||
				tipe.endsWith("CPL") || tipe.endsWith("EST") || tipe.endsWith("ACP") ||
				tipe.endsWith("LAM") || tipe.endsWith("RQP") || tipe.endsWith("RQS") ||
				tipe.endsWith("SPL") || tipe.endsWith("AFTN")) {
			sFiledby = ATSForms.getFiled_by();
		}  
		
		//perintah untuk meng-update berita ATS Message (tabel: air_message)
		String update = "UPDATE "+sTblName+" SET " +
				"priority='"+sPriority+"',"+
				"destination1='"+sDest1+"',"+
				"destination2='"+sDest2+"',"+
				"destination3='"+sDest3+"',"+
				"destination4='"+sDest4+"',"+
				"destination5='"+sDest5+"',"+
				"destination6='"+sDest6+"',"+
				"destination7='"+sDest7+"',"+
				"destination8='"+sDest8+"',"+
				"destination9='"+sDest9+"',"+
				"destination10='"+sDest10+"',"+
			  	"destination11='"+sDest11+"',"+
			  	"destination12='"+sDest12+"',"+
			  	"destination13='"+sDest13+"',"+
			  	"destination14='"+sDest14+"',"+
			  	"destination15='"+sDest15+"',"+
			  	"destination16='"+sDest16+"',"+
			  	"destination17='"+sDest17+"',"+
			  	"destination18='"+sDest18+"',"+
			  	"destination19='"+sDest19+"',"+
			  	"destination20='"+sDest20+"',"+
			  	"destination21='"+sDest21+"'," +
			  	"originator='"+sOriginator+"'," +
				"ori_ref='"+sOriRef+"'," +
				"filled_by='"+sFiledby+"'," +
				"filing_time='"+sFiling+"'," +
				"jam='"+sJam+"'," +
				"tgl='"+tanggal+"'," +
				"bell='"+sBell+"'," +
		  
				"type3a='"+s3a+"'," +
				"type3b='"+s3b+"'," +
				"type3c='"+s3c+"'," +
				"type5a='"+s5a+"'," +
				"type5b='"+s5b+"'," +
				"type5c='"+s5c+"'," +
				"type7a='"+s7a+"'," +
				"type7b='"+s7b+"'," +
				"type7c='"+s7c+"'," +
				"type8a='"+s8a+"'," +
				"type8b='"+s8b+"'," +
				"type9a='"+s9a+"'," +
				"type9b='"+s9b+"'," +
				"type9c='"+s9c+"'," +
				"type10a='"+s10a+"'," +
				"type10b='"+s10b+"'," +
				"type13a='"+s13a+"'," +
				"type13b='"+s13b+"'," +
				"type14a='"+s14a+"'," +
				"type14b='"+s14b+"'," +
				"type14c='"+s14c+"'," +
				"type14d='"+s14d+"'," +
				"type14e='"+s14e+"'," +
				"type15a='"+s15a+"'," +
				"type15b='"+s15b+"'," +
				"type15c='"+s15c+"'," +
				"type16a='"+s16a+"'," +
				"type16b='"+s16b+"'," +
				"type16c='"+s16c+"'," +
				"type16c_2nd='"+s16d+"'," +
				"type17a='"+s17a+"'," +
				"type17b='"+s17b+"'," +
				"type17c='"+s17c+"'," +
				"type18a='"+s18a+"'," +
				"type18b='"+s18b+"'," +
				"type18='"+s18c+"'," +
				"type19a='"+s19a+"'," +
				"type19b='"+s19b+"'," +
				"type19cUHF='"+s19cUHF+"'," +
				"type19cVHF='"+s19cVHF+"'," +
				"type19cELT='"+s19cELT+"'," +
				"type19dS='"+s19dS+"'," +
				"type19dP='"+s19dP+"'," +
				"type19dD='"+s19dD+"'," +
				"type19dM='"+s19dM+"'," +
				"type19dJ='"+s19dJ+"'," +
				"type19eJ='"+s19eJ+"'," +
				"type19eL='"+s19eL+"'," +
				"type19eF='"+s19eF+"'," +
				"type19eU='"+s19eU+"'," +
				"type19eV='"+s19eV+"'," +
				"type19fD='"+s19fD+"'," +
				"type19f_number='"+s19fNum+"'," +
				"type19f_capacity='"+s19fCap+"'," +
				"type19f_cover='"+s19fCov+"'," +
				"type19f_colour='"+s19fCol+"'," +
				"type19g='"+s19g+"'," +
				"type19hN='"+s19h+"'," +
				"type19Remarks='"+s19Rem+"'," +
				"type19i='"+s19i+"'," +
				"type20='"+s20+"'," +
				"type21='"+s21+"'," +
				"type22='"+s22+"'," +
				"track_name='"+sTrkName+"'," +
				"track_id='"+sTrkId+"'," +
				"track_date='"+sTrkDate+"'," +
				"track_status='"+sTrkStatus+"'," +
				"track_act='"+sTrkAct+"'," +
				"track_exp='"+sTrkExp+"'," +
				"track_waypoint='"+sTrkWay+"'," +
				"track_level='"+sTrkLev+"'," +
				"track_rts='"+sTrkRts+"'," +
				"track_rmk='"+sTrkRmk+"'," +
				"freetext='"+sFree+"'," +
				"space_reserved='"+sSpace+"'," +
				"atstyp='"+sAtstyp+"'," +
				"msgall='"+sMsgAll+"'" +
				" WHERE id_ats="+ID;
		
		jdbc.update(update);
	}

	// tabel: check_status
	public void setInsertCheck(String id, String tbl_name, String filing_time, String jam, String tanggal, String aircraft_id, 
    		String type, String series, String originator, String message, String strDof, String sFiledby, String sPriority, String sNote) {

    	String flag = "0";
    	String status = "waiting"; 
    	//field: dof
    	String dof="",atstyp="";
    	if (strDof.compareTo("DOF/0")==0 || strDof.compareTo("")==0) {
			String thn = tanggal.substring(2, 4);
			String bln = tanggal.substring(5, 7);
			String tgl = tanggal.substring(8, 10);
			
			dof = "DOF/"+thn+bln+tgl;
		} else dof = strDof;
		if (dof.startsWith(" ")) { dof = dof.replaceFirst("\\s+", ""); }
		
    	//field: atstyp
    	atstyp="1";
    	
    	//field: filed_by
    	String filed_by="";
    	if (type.compareTo("RPL")==0) filed_by = "RPL"; else filed_by = sFiledby;
    	
    	//idcnt akan terisi otomatis (auto increment)
    	String check = "INSERT INTO check_status " +
    			"(id,tbl_name,filing_time,status,jam,flag,tgl,aircraft_id,type,series,originator,message,dof,atstyp,filed_by,priority,note) VALUES " +
    			"('"+id+"','"+tbl_name+"','"+filing_time+"','"+status+"','"+jam+"','"+flag+"','"+tanggal+"','"+aircraft_id+"','"+type+"','"+
    	    	series+"','"+originator+"','"+message+"','"+dof+"','"+atstyp+"','"+filed_by+"','"+sPriority+"','"+sNote+"')";
		jdbc.connect(check); 
//		TriggerAction.setUpNetworking();
		TriggerAction.trigger("elsa", 105);
    }
	
	public void setUpdateCheck(String jam,String tanggal,String filing,String orig,String filed,String outbox,String aircraft_id,
			String series,String msg,String sPriority) {
		String id="",tbl_name="",type="",tgl="";
		if (outbox.compareTo("ATS") == 0) {
			id = TableOutgoing.GetString2();
			tbl_name = TableOutgoing.GetTblName();
			type = TableOutgoing.GetMsgType();
			tgl = TableOutgoing.GetDateTime();
			series="";
		}

		//perintah untuk meng-update (tabel: check_status)
		String update = "UPDATE check_status " +
			"SET id='"+id+"'," +
			"tbl_name='"+tbl_name+"'," +
			"status='waiting'," +
			"flag='0'," +
			"aircraft_id='"+aircraft_id+"'," +
			"type='"+type+"'," +
			"series='"+series+"'," +
			"message='"+msg+"'," +
			"filing_time='"+filing+"'," +
			"jam='"+jam+"'," +
			"tgl='"+tanggal+"'," +
			"originator='"+orig+"'," +
			"priority='"+sPriority+"'," +
			"filed_by='"+filed+"'" +
			" WHERE id='"+id+"' AND tbl_name='"+tbl_name+"' AND tgl LIKE '"+tgl+"%'";

		jdbc.update(update);
	}
	
	// tutup koneksi dan shell
    void closeMiniForm() {
    	if (!Shells.sh_mSTS.isDisposed()) { Shells.sh_mSTS.close(); }
    	if (!Shells.sh_mPBN.isDisposed()) { Shells.sh_mPBN.close(); }
    	if (!Shells.sh_m10a.isDisposed()) { Shells.sh_m10a.close(); }
    	if (!Shells.sh_m10b.isDisposed()) { Shells.sh_m10b.close(); }
    }
    
    public void connClose(String type) {
//    	ATSForms.connClose();
    	//GetFPL
    	if (type.endsWith("DLA")) {
    		if (!ATSForms.nextDLA.isDisposed()) ATSForms.nextDLA.setEnabled(false);
    		if (!ATSForms.prevDLA.isDisposed()) ATSForms.prevDLA.setEnabled(false);
    		if (!ATSForms.firstDLA.isDisposed()) ATSForms.firstDLA.setEnabled(false);
    		if (!ATSForms.lastDLA.isDisposed()) ATSForms.lastDLA.setEnabled(false);	
    	} else if (type.endsWith("CHG")) {
    		if (!ATSForms.nextCHG.isDisposed()) ATSForms.nextCHG.setEnabled(false);
    		if (!ATSForms.prevCHG.isDisposed()) ATSForms.prevCHG.setEnabled(false);
    		if (!ATSForms.firstCHG.isDisposed()) ATSForms.firstCHG.setEnabled(false);
    		if (!ATSForms.lastCHG.isDisposed()) ATSForms.lastCHG.setEnabled(false);	
    	} else if (type.endsWith("CNL")) {
    		if (!ATSForms.nextCNL.isDisposed()) ATSForms.nextCNL.setEnabled(false);
    		if (!ATSForms.prevCNL.isDisposed()) ATSForms.prevCNL.setEnabled(false);
    		if (!ATSForms.firstCNL.isDisposed()) ATSForms.firstCNL.setEnabled(false);
    		if (!ATSForms.lastCNL.isDisposed()) ATSForms.lastCNL.setEnabled(false);	
    	} else if (type.endsWith("DEP")) {
    		if (!ATSForms.nextDEP.isDisposed()) ATSForms.nextDEP.setEnabled(false);
    		if (!ATSForms.prevDEP.isDisposed()) ATSForms.prevDEP.setEnabled(false);
    		if (!ATSForms.firstDEP.isDisposed()) ATSForms.firstDEP.setEnabled(false);
    		if (!ATSForms.lastDEP.isDisposed()) ATSForms.lastDEP.setEnabled(false);	
    	} else if (type.endsWith("ARR")) {
    		if (!ATSForms.nextARR.isDisposed()) ATSForms.nextARR.setEnabled(false);
    		if (!ATSForms.prevARR.isDisposed()) ATSForms.prevARR.setEnabled(false);
    		if (!ATSForms.firstARR.isDisposed()) ATSForms.firstARR.setEnabled(false);
    		if (!ATSForms.lastARR.isDisposed()) ATSForms.lastARR.setEnabled(false);	
    	} else if (type.endsWith("CDN")) {
    		if (!ATSForms.nextCDN.isDisposed()) ATSForms.nextCDN.setEnabled(false);
    		if (!ATSForms.prevCDN.isDisposed()) ATSForms.prevCDN.setEnabled(false);
    		if (!ATSForms.firstCDN.isDisposed()) ATSForms.firstCDN.setEnabled(false);
    		if (!ATSForms.lastCDN.isDisposed()) ATSForms.lastCDN.setEnabled(false);	
    	} else if (type.endsWith("EST")) {
    		if (!ATSForms.nextEST.isDisposed()) ATSForms.nextEST.setEnabled(false);
    		if (!ATSForms.prevEST.isDisposed()) ATSForms.prevEST.setEnabled(false);
    		if (!ATSForms.firstEST.isDisposed()) ATSForms.firstEST.setEnabled(false);
    		if (!ATSForms.lastEST.isDisposed()) ATSForms.lastEST.setEnabled(false);	
    	} else if (type.endsWith("ACP")) {
    		if (!ATSForms.nextACP.isDisposed()) ATSForms.nextACP.setEnabled(false);
    		if (!ATSForms.prevACP.isDisposed()) ATSForms.prevACP.setEnabled(false);
    		if (!ATSForms.firstACP.isDisposed()) ATSForms.firstACP.setEnabled(false);
    		if (!ATSForms.lastACP.isDisposed()) ATSForms.lastACP.setEnabled(false);	
    	} else if (type.endsWith("RQS")) {
    		if (!ATSForms.nextRQS.isDisposed()) ATSForms.nextRQS.setEnabled(false);
    		if (!ATSForms.prevRQS.isDisposed()) ATSForms.prevRQS.setEnabled(false);
    		if (!ATSForms.firstRQS.isDisposed()) ATSForms.firstRQS.setEnabled(false);
    		if (!ATSForms.lastRQS.isDisposed()) ATSForms.lastRQS.setEnabled(false);	
    	} else if (type.endsWith("SPL")) {
    		if (!ATSForms.nextSPL.isDisposed()) ATSForms.nextSPL.setEnabled(false);
    		if (!ATSForms.prevSPL.isDisposed()) ATSForms.prevSPL.setEnabled(false);
    		if (!ATSForms.firstSPL.isDisposed()) ATSForms.firstSPL.setEnabled(false);
    		if (!ATSForms.lastSPL.isDisposed()) ATSForms.lastSPL.setEnabled(false);		
    	}
    	//GetRoute
    	else if (type.endsWith("ALR")) {
    		closeMiniForm();
    		if (!ATSForms.nextALR.isDisposed()) ATSForms.nextALR.setEnabled(false);
    		if (!ATSForms.prevALR.isDisposed()) ATSForms.prevALR.setEnabled(false);
    		if (!ATSForms.firstALR.isDisposed()) ATSForms.firstALR.setEnabled(false);
    		if (!ATSForms.lastALR.isDisposed()) ATSForms.lastALR.setEnabled(false);	
    	} else if (type.endsWith("FPL")) {
    		closeMiniForm();
    		if (!ATSForms.nextFPL.isDisposed()) ATSForms.nextFPL.setEnabled(false);
    		if (!ATSForms.prevFPL.isDisposed()) ATSForms.prevFPL.setEnabled(false);
    		if (!ATSForms.firstFPL.isDisposed()) ATSForms.firstFPL.setEnabled(false);
    		if (!ATSForms.lastFPL.isDisposed()) ATSForms.lastFPL.setEnabled(false);	
    	} else if (type.endsWith("CPL")) {
    		closeMiniForm();
    		if (!ATSForms.nextCPL.isDisposed()) ATSForms.nextCPL.setEnabled(false);
    		if (!ATSForms.prevCPL.isDisposed()) ATSForms.prevCPL.setEnabled(false);
    		if (!ATSForms.firstCPL.isDisposed()) ATSForms.firstCPL.setEnabled(false);
    		if (!ATSForms.lastCPL.isDisposed()) ATSForms.lastCPL.setEnabled(false);	
    	} else if (type.endsWith("SPL")) {
    		closeMiniForm();
    	}
    }
    
    // cek field: Type of Aircraft - Wake Turbulence Category 
    public void cek9b(String t9b, String ID) {
    	Shell shell = null;
    	if (ID.endsWith("ALR")) shell = Shells.sh_nALR;
    	if (ID.endsWith("FPL")) shell = Shells.sh_nFPL;
    	if (ID.endsWith("CPL")) shell = Shells.sh_nCPL;
    	
		try {
			String lihatsql = "SELECT * FROM aircraft_wtc WHERE type9b='"+t9b+"'";
			System.out.println("Query: " + lihatsql);
			
			try { conn = jdbc.getDBConnection(); } 
			catch (Exception e) { e.printStackTrace(); }
			stmt = conn.createStatement();
			rs = stmt.executeQuery(lihatsql);
			rs.last();
			int jumlah = rs.getRow();
			System.out.println("Jumlah data:" + jumlah);
			rs.beforeFirst();

			int p=0;
			int rowNo=0;
			wtc=""; toa="";
			while (rs.next()) {
				rowNo++;
				toa = rs.getString("type9b"); if (toa == null) toa="";
				wtc = rs.getString("type9c"); if (wtc == null) wtc="";
				p++;
			}
			
			connClose();
		} catch (SQLException s) {
			s.printStackTrace();
		} catch (java.lang.OutOfMemoryError hs) {
			DialogFactory .openInfoDialog(shell, "Get Wake Turbulance Category", "Out of memory !!");
		}
    }
    
    public void getTbl(String data) {
//    	System.out.println("ID sTblName=" + data);
    	// ATS Message
    	if (data.endsWith("ALR") || data.endsWith("RCF") || data.endsWith("FPL") || 
    			data.endsWith("DLA") || data.endsWith("CHG") || data.endsWith("CNL") || 
    			data.endsWith("DEP") || data.endsWith("ARR") || data.endsWith("CDN") || 
    			data.endsWith("CPL") || data.endsWith("EST") || data.endsWith("ACP") || 
    			data.endsWith("LAM") || data.endsWith("RQP") || data.endsWith("RQS") || 
        		data.endsWith("SPL") || data.endsWith("AFTN") || data.endsWith("AMO") || data.endsWith("FREE") ) { 
    		sTblName="air_message";
    	}
    }
    
    // cek field: Aircraft Id (1x dalam 1hari - FPL,DEP) 
    public void cek7a(String t3a, String t7a) {
		time.tgl();
        String datenow = time.YYYY+"-"+time.MM+"-"+time.DD;
        
		try {
			String lihatsql = "SELECT * FROM air_message"+time.yearMonth+" WHERE type3a='"+t3a+"' AND type7a='"+t7a+"' and tgl LIKE '"+datenow+"%'";
			System.out.println("\nQuery: " + lihatsql);

			try { conn = jdbc.getDBConnection(); } 
			catch (Exception e) { e.printStackTrace(); }
			stmt = conn.createStatement();
			rs = stmt.executeQuery(lihatsql);
			rs.last();
			int jumlah = rs.getRow();
			System.out.println("Jumlah data:" + jumlah);
			rs.beforeFirst();

			int p=0;
			int rowNo=0;
			acft_id=""; 
			while (rs.next()) {
				rowNo++;
				acft_id = rs.getString("type7a"); if (acft_id == null) acft_id="";
				System.out.println("acft_id::" + acft_id+"*");
				p++;
			}
			
			connClose();
		} catch (SQLException s) {
			s.printStackTrace();
		} catch (java.lang.OutOfMemoryError hs) {
			DialogFactory .openInfoDialog(Shells.sh_nFPL, "Get Aircraft Identification", "Out of memory !!");
		}
    }
    
    public void depCheck(String sType7a, String sType13a, String sType16a) {
		String tblName="",yearFr="",monthFr="",yearTo="",monthTo="",lihatsql="";
		int iCnt=0,jumlah=0,rowNo=0;
		
		try {
			time.tgl();
			tblName = "air_message"+time.yearMonth;

			try { conn = jdbc.getDBConnection(); } 
			catch (Exception e) { e.printStackTrace(); }

			//Query semua tabel notam--------------------------------------------
		    String sDbName = jdbc.getDbName();
			Statement stmtTbl = conn.createStatement();
			String lihatTbl = "SHOW TABLES FROM "+sDbName+" LIKE 'air_message"+time.YYYY+"___'";
			System.out.println("\nQuery: " + lihatTbl);
			ResultSet rsTbl = stmtTbl.executeQuery(lihatTbl);
		
			rsTbl.last();
			int jumlahTbl = rsTbl.getRow();
			System.out.println("Jumlah tabel=" + jumlahTbl);
			rsTbl.beforeFirst();
			int rowNoTbl = 0;
			
			if (jumlahTbl == 0) { //percobaan
				
			} else {
				String tbl_nm="",tbl_nmBaru="",semua_tbl="";
				while (rsTbl.next()) {
					rowNoTbl++;
					tbl_nm = rsTbl.getString(1); if (tbl_nm == null) tbl_nm="";
					System.out.println(rowNoTbl + ". " + tbl_nm);
					
					if (!tbl_nmBaru.equals(tbl_nm)) {
						semua_tbl+=tbl_nm+",";
					}
					tbl_nmBaru = tbl_nm;
				}
				
				//update -20101224
				String sFrom="",sTo="";
				if (jumlahTbl == 1) { //jika jumlah tabel hanya 1
					int indexKoma = semua_tbl.lastIndexOf(",");
					semua_tbl = semua_tbl.substring(0, indexKoma);
					if (semua_tbl.contains("air_message")) semua_tbl = semua_tbl.replaceAll("air_message", "");
					 
					sFrom=sTo=semua_tbl;
					int indexTo = sTo.lastIndexOf(",");
					sTo = sTo.substring(indexTo+1, sTo.length());
					
					yearFr = yearTo = sTo.substring(0, 4);
					monthFr = monthTo = sTo.substring(5, 7);
				} else { //jika jumlah tabel banyak (>1)
					int indexKoma = semua_tbl.lastIndexOf(",");
					semua_tbl = semua_tbl.substring(0, indexKoma);
					if (semua_tbl.contains("air_message")) semua_tbl = semua_tbl.replaceAll("air_message", "");
					 
					sFrom=sTo=semua_tbl;
					int indexFrom = sFrom.indexOf(",");
					sFrom = sFrom.substring(0, indexFrom);
					
					int indexTo = sTo.lastIndexOf(",");
					sTo = sTo.substring(indexTo+1, sTo.length());
					
					yearFr = sFrom.substring(0, 4);
					monthFr = sFrom.substring(5, 7);
					
					yearTo = sTo.substring(0, 4);
					monthTo = sTo.substring(5, 7);
				}
				
				jumlah=0; //inisialisasi
				iCnt=0;
				rowNo=0;
				//menentukan tblName berdasarkan Date/Time
				System.out.println("Date From::" + yearFr + "-" + monthFr + "*");
				System.out.println("Date To::" + yearTo + "-" + monthTo + "*");
				
				boolean flg=false;
				for (int iYear=Integer.parseInt(yearFr); iYear<=Integer.parseInt(yearTo); iYear++) {
					int iMtemp=0;
					int n=0;
					int nn=0;
					int iMonth=0;
					
					tblName = "air_message"+iYear;
					if (iYear==Integer.parseInt(yearFr)) {
						iMtemp=Integer.parseInt(monthFr);
						n=12-iMtemp;
					} else if ((iYear>Integer.parseInt(yearFr)) && (iYear < Integer.parseInt(yearTo))) {
						iMtemp=1;
						n=12-iMtemp;
					} else if (iYear == Integer.parseInt(yearTo)) {
						iMtemp= Integer.parseInt(monthTo);
						n=iMtemp;
						iMtemp=0;
						flg=true;
					}
					
					for (iMonth=iMtemp; iMonth<=n+iMtemp; iMonth++) {
						if ((flg) && (iMonth>=12)) { 
							nn++; 
							if ((n==iMtemp) && (nn>12)) break; 
						} else nn=iMonth;
						
						String sMonth = Integer.toString(nn);
						if (sMonth.length()==1) sMonth = sMonth.replace(sMonth, "0"+sMonth);
						tblName = tblName.replace(tblName, "air_message"+iYear+"_"+sMonth);
						
						stmt = conn.createStatement();
						try { //biar kalo ada table yang doesn't exist ga mati.
							lihatsql = "SELECT * FROM "+tblName+" WHERE type3a='FPL' AND type7a='" + sType7a + "'" +
							" AND type13a='" + sType13a + "' AND type16a='" + sType16a + "'";
							System.out.println("\nQuery: " + lihatsql);
							rs = stmt.executeQuery(lihatsql);
							rs.last();
							jumlah += rs.getRow();
							System.out.println("Jumlah=" + jumlah);
							rs.beforeFirst();
						
							while (rs.next()) {
								rowNo++;
								kType7a = rs.getString("type7a"); if (kType7a==null) { kType7a=""; }
								kType13a = rs.getString("type13a"); if (kType13a==null) { kType13a=""; }
								kType13b = rs.getString("type13b"); if (kType13b==null) { kType13b=""; }
								kType16a = rs.getString("type16a"); if (kType16a==null) { kType16a=""; }
								kType18b = rs.getString("type18b"); if (kType18b==null) { kType18b=""; }
								kJam = rs.getString("jam"); if (kJam==null) { kJam=""; }
							} //end of while
						} catch (SQLException s) { // biar kalo ada table yang doesn't exist ga mati.
							System.out.println("Error: " + s.getMessage());
						}
						iCnt++; //counter rs
					} //end of for2
				} //end of for1
			} //end of else [percobaan]
			
			rsTbl.close();
			stmtTbl.close();
			connClose();
		} catch (SQLException s) {
			DialogFactory.openInfoDialog(Shells.sh_nDEP, "Search Message", s.toString());
			s.printStackTrace();
		} catch (java.lang.OutOfMemoryError hs) {
			DialogFactory.openInfoDialog(Shells.sh_nDEP, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
		 }//end of try
    }
    
    public void connClose() {
		try {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
	        if (conn!=null) conn.close();
		 } catch(SQLException se) {
			 se.printStackTrace();
		 }
	}
    
    void tipe18baruFPL() {
//    	sReg = ATSForms.get18a(); if (sReg.compareTo("")!=0) sReg=" REG/"+sReg; else sReg="";
//		sDof = ATSForms.get18b(); if (sDof.compareTo("")!=0) { time.t18b(sDof); } else sDof="";

    	//di current tidak ada indikator berikut: (PBN, SUR, CODE, DLE, ORGN, TALT)
    	String sSts = ATSForms.get18tSts(); 
    	String sPbn = "";//ATSForms.get18tPbn(); 
    	String sNav = ATSForms.get18tNav(); 
    	String sCom = ATSForms.get18tCom(); 
		String sDat = ATSForms.get18tDat(); 
		String sSur = "";//ATSForms.get18tSur(); 
		String sDep = ATSForms.get18tDep(); 
		String sDest = ATSForms.get18tDest(); 
		sDof = ATSForms.get18tDof(); 
		sReg = ATSForms.get18tReg(); 
		String sEet = ATSForms.get18tEet(); 
		String sSel = ATSForms.get18tSel(); 
		String sTyp = ATSForms.get18tTyp(); 
		String sCode = "";//ATSForms.get18tCode(); 
		String sDle = "";//ATSForms.get18tDle(); 
		String sOpr = ATSForms.get18tOpr(); 
		String sOrgn = "";//ATSForms.get18tOrgn(); 
		String sPer = ATSForms.get18tPer(); 
		String sAltn = ATSForms.get18tAltn(); 
		String sRalt = ATSForms.get18tRalt(); 
		String sTalt = "";//ATSForms.get18tTalt(); 
		String sRif = ATSForms.get18tRif(); 
		String sRmk = ATSForms.get18tRmk(); 
		//---------------------------------
		if (sSts.startsWith(" ")) sSts=sSts.replaceFirst("\\s+", "");
		if (sPbn.startsWith(" ")) sPbn=sPbn.replaceFirst("\\s+", "");
		if (sNav.startsWith(" ")) sNav=sNav.replaceFirst("\\s+", "");
		if (sCom.startsWith(" ")) sCom=sCom.replaceFirst("\\s+", "");
		if (sDat.startsWith(" ")) sDat=sDat.replaceFirst("\\s+", "");
		if (sSur.startsWith(" ")) sSur=sSur.replaceFirst("\\s+", "");
		if (sDep.startsWith(" ")) sDep=sDep.replaceFirst("\\s+", "");
		if (sDest.startsWith(" ")) sDest=sDest.replaceFirst("\\s+", "");
		if (sDof.startsWith(" ")) sDof=sDof.replaceFirst("\\s+", ""); time.t18b(sDof);
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
//		if (sDof.compareTo("")!=0) sDof=" DOF/"+sDof; else sDof="";
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
		// mengembalikan tanda '/' untuk field freetext type18baru
		if (sNav.contains("~")) sNav = sNav.replaceAll("~", "/");
		if (sCom.contains("~")) sCom = sCom.replaceAll("~", "/");
		if (sDat.contains("~")) sDat = sDat.replaceAll("~", "/");
		if (sSur.contains("~")) sSur = sSur.replaceAll("~", "/");
		if (sDep.contains("~")) sDep = sDep.replaceAll("~", "/");
		if (sDest.contains("~")) sDest = sDest.replaceAll("~", "/");
		if (sTyp.contains("~")) sTyp = sTyp.replaceAll("~", "/");
		if (sOpr.contains("~")) sOpr = sOpr.replaceAll("~", "/");
		if (sOrgn.contains("~")) sOrgn = sOrgn.replaceAll("~", "/");
		if (sAltn.contains("~")) sAltn = sAltn.replaceAll("~", "/");
		if (sRalt.contains("~")) sRalt = sRalt.replaceAll("~", "/");
		if (sTalt.contains("~")) sTalt = sTalt.replaceAll("~", "/");
		if (sRif.contains("~")) sRif = sRif.replaceAll("~", "/");
		if (sRmk.contains("~")) sRmk = sRmk.replaceAll("~", "/");
		// mengembalikan tanda '\n' untuk field freetext type18baru
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
		s18Baru=sSts+sPbn+sNav+sCom+sDat+sSur+sDep+sDest+sEet+sSel+sTyp+sCode+sDle+sOpr+sOrgn+sPer+sAltn+sRalt+sTalt+sRif+sRmk;
		if (s18Baru.startsWith(" ")) s18Baru = s18Baru.replaceFirst("\\s+", "");
    }
    
    public void tipe18baru() {
//    	sReg = ATSForms.get18a(); if (sReg.compareTo("")!=0) sReg=" REG/"+sReg; else sReg="";
//		sDof = ATSForms.get18b(); if (sDof.compareTo("")!=0) { time.t18b(sDof); } else sDof="";
		
    	String sSts = ATSForms.get18tSts(); 
    	String sPbn = ATSForms.get18tPbn(); 
    	String sNav = ATSForms.get18tNav(); 
    	String sCom = ATSForms.get18tCom(); 
		String sDat = ATSForms.get18tDat(); 
		String sSur = ATSForms.get18tSur(); 
		String sDep = ATSForms.get18tDep(); 
		String sDest = ATSForms.get18tDest(); 
		sDof = ATSForms.get18tDof(); if (sDof.compareTo("0")==0) sDof="000000";
		sReg = ATSForms.get18tReg(); 
		String sEet = ATSForms.get18tEet(); 
		String sSel = ATSForms.get18tSel(); 
		String sTyp = ATSForms.get18tTyp(); 
		String sCode = ATSForms.get18tCode(); 
		String sDle = ATSForms.get18tDle(); 
		String sOpr = ATSForms.get18tOpr(); 
		String sOrgn = ATSForms.get18tOrgn(); 
		String sPer = ATSForms.get18tPer(); 
		String sAltn = ATSForms.get18tAltn(); 
		String sRalt = ATSForms.get18tRalt(); 
		String sTalt = ATSForms.get18tTalt(); 
		String sRif = ATSForms.get18tRif(); 
		String sRmk = ATSForms.get18tRmk(); 
		//---------------------------------
		if (sSts.startsWith(" ")) sSts=sSts.replaceFirst("\\s+", "");
		if (sPbn.startsWith(" ")) sPbn=sPbn.replaceFirst("\\s+", "");
		if (sNav.startsWith(" ")) sNav=sNav.replaceFirst("\\s+", "");
		if (sCom.startsWith(" ")) sCom=sCom.replaceFirst("\\s+", "");
		if (sDat.startsWith(" ")) sDat=sDat.replaceFirst("\\s+", "");
		if (sSur.startsWith(" ")) sSur=sSur.replaceFirst("\\s+", "");
		if (sDep.startsWith(" ")) sDep=sDep.replaceFirst("\\s+", "");
		if (sDest.startsWith(" ")) sDest=sDest.replaceFirst("\\s+", "");
		if (sDof.startsWith(" ")) sDof=sDof.replaceFirst("\\s+", ""); time.t18b(sDof);
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
//		if (sDof.compareTo("")!=0) sDof=" DOF/"+sDof; else sDof="";
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
		// mengembalikan tanda '/' untuk field freetext type18baru
		if (sNav.contains("~")) sNav = sNav.replaceAll("~", "/");
		if (sCom.contains("~")) sCom = sCom.replaceAll("~", "/");
		if (sDat.contains("~")) sDat = sDat.replaceAll("~", "/");
		if (sSur.contains("~")) sSur = sSur.replaceAll("~", "/");
		if (sDep.contains("~")) sDep = sDep.replaceAll("~", "/");
		if (sDest.contains("~")) sDest = sDest.replaceAll("~", "/");
		if (sTyp.contains("~")) sTyp = sTyp.replaceAll("~", "/");
		if (sOpr.contains("~")) sOpr = sOpr.replaceAll("~", "/");
		if (sOrgn.contains("~")) sOrgn = sOrgn.replaceAll("~", "/");
		if (sAltn.contains("~")) sAltn = sAltn.replaceAll("~", "/");
		if (sRalt.contains("~")) sRalt = sRalt.replaceAll("~", "/");
		if (sTalt.contains("~")) sTalt = sTalt.replaceAll("~", "/");
		if (sRif.contains("~")) sRif = sRif.replaceAll("~", "/");
		if (sRmk.contains("~")) sRmk = sRmk.replaceAll("~", "/");
		// mengembalikan tanda '\n' untuk field freetext type18baru
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
		s18Baru=sSts+sPbn+sNav+sCom+sDat+sSur+sDep+sDest+time.sDof+sReg+sEet+sSel+sTyp+sCode+sDle+sOpr+sOrgn+sPer+sAltn+sRalt+sTalt+sRif+sRmk;
		if (s18Baru.startsWith(" ")) s18Baru = s18Baru.replaceFirst("\\s+", "");
    }
    
    public void data(String message) { //untuk menghilangkan [ ( ] pada awal isi data
    	String x="",F="",k="";
    	String sub[] = null;
    	int index=0;
    	
    	msgBody = message;
		if (message.contains("\n")) {
			sub = message.split("\n");
			msg = sub[0];
			if (msg.startsWith("(")) {
				x = msg;
				index = x.indexOf("(");
				F = msg.substring(index, msg.length());
				k = msg.substring(0, index );
				F = F.replace("(", "");
				msg=k+F;
			} 
			if (msg.endsWith(")")) {
				x = msg;
				index = x.indexOf(")");
				F = msg.substring(index, msg.length());
				k = msg.substring(0, index );
				F = F.replace(")", "");
				msg=k+F;
			} 
		} else {
			msg = message;
			if (msg.startsWith("(")) {
				x = msg;
				index = x.indexOf("(");
				F = msg.substring(index, msg.length());
				k = msg.substring(0, index );
				F = F.replace("(", "");
				msg=k+F;
			}  
			if (msg.endsWith(")")) {
				x = msg;
				index = x.indexOf(")");
				F = msg.substring(index, msg.length());
				k = msg.substring(0, index );
				F = F.replace(")", "");
				msg=k+F;
			} 
		}
		
		if (msg.length() > 69) {
			msg = msg.substring(0, 65).concat(" ...");
		}
    }
}

// tabel: air_message_free_text dan tabel: air_message_free_text_meteo
//public void setInsertFree(String sPriority,String sDest1,String sDest2,String sDest3,String sDest4,String sDest5,
//		String sDest6,String sDest7,String sDest8,String sDest9,String sDest10,String sDest11,String sDest12,
//		String sDest13,String sDest14,String sDest15,String sDest16,String sDest17,String sDest18,String sDest19,
//		String sDest20,String sDest21,String sOriginator,String sOriRef,String sFiling,String sJam,String tanggal,
//		String sFiledby,String sTblName,String sStatus,
//		String sFree ) {
//	
//	String filedby="",freetext="";
//	if (sTblName.startsWith("air_message")) {
//		filedby = "filled_by";
//		freetext = "manual_ats";
//	} else if (sTblName.startsWith("volcanic_act")) {
//		filedby = "filed_by";
//		freetext = "text";
//	}
//	
//	//perintah untuk meng-insert berita Freetext (tabel: air_message_free_text dan air_message_free_text_meteo)
//	String insert = "INSERT INTO "+sTblName+" (priority,destination1,destination2,destination3,destination4,destination5," +
//			"destination6,destination7,destination8,destination9,destination10,destination11,destination12,destination13," +
//			"destination14,destination15,destination16,destination17,destination18,destination19,destination20,destination21," +
//			"originator,ori_ref,filing_time,jam,tgl,"+filedby+",tbl_name,status," +
//			freetext+") VALUES " +
//			"('"+sPriority+"','"+sDest1+"','"+sDest2+"','"+sDest3+"','"+sDest4+"','"+sDest5+"','"+sDest6+"','"+sDest7+"','"+
//			sDest8+"','"+sDest9+"','"+sDest10+"','"+sDest11+"','"+sDest12+"','"+sDest13+"','"+sDest14+"','"+sDest15+"','"+
//			sDest16+"','"+sDest17+"','"+sDest18+"','"+sDest19+"','"+sDest20+"','"+sDest21+"','"+sOriginator+"','"+
//			sOriRef+"','"+sFiling+"','"+sJam+"','"+tanggal+"','"+sFiledby+"','"+sTblName+"','"+sStatus+"','"+
//			sFree+"')";
//	jdbc.connect(insert);
//}

//public void setUpdateFree(String outbox,String sPriority,String sDest1,String sDest2,String sDest3,String sDest4,String sDest5,
//		String sDest6,String sDest7,String sDest8,String sDest9,String sDest10,String sDest11,String sDest12,
//		String sDest13,String sDest14,String sDest15,String sDest16,String sDest17,String sDest18,String sDest19,
//		String sDest20,String sDest21,String sOriginator,String sOriRef,String sFiling,String sJam,String tanggal,
//		String sFiledby,String sTblName,String sStatus,
//		String sFree,String ID) {
//    	
//	sFiledby="";
//	if (outbox.compareTo("ATS") == 0) { // berita AIDC juga masuk ATS
//		if (TableOutgoing.GetMsgType().compareTo("RPL") == 0) sFiledby = "RPL"; else sFiledby = ATSForms.getFiled_by();	
//	} else if (outbox.compareTo("NOTAM") == 0) {
//		sFiledby = "";//NOTAMForms.getFiled_by();	
//	} else if (outbox.compareTo("METEO") == 0) {
//		sFiledby = "";//METEOForms.getFiled_by();	
//	}
//	
//	String filedby="",freetext="",id_tbl="";
//	if (sTblName.startsWith("air_message")) {
//		filedby = "filled_by";
//		freetext = "manual_ats";
//		id_tbl = "id_free_text";
//	} else if (sTblName.startsWith("volcanic_act")) {
//		filedby = "filed_by";
//		freetext = "text";
//		id_tbl = "id_va";
//	}
//	
//	//perintah untuk meng-update berita Freetext (tabel: air_message_free_text dan air_message_free_text_meteo)
//	String update = "UPDATE "+sTblName+" SET " +
//			"priority='"+sPriority+"',"+
//			"destination1='"+sDest1+"',"+
//			"destination2='"+sDest2+"',"+
//			"destination3='"+sDest3+"',"+
//			"destination4='"+sDest4+"',"+
//			"destination5='"+sDest5+"',"+
//			"destination6='"+sDest6+"',"+
//			"destination7='"+sDest7+"',"+
//			"destination8='"+sDest8+"',"+
//			"destination9='"+sDest9+"',"+
//			"destination10='"+sDest10+"',"+
//		  	"destination11='"+sDest11+"',"+
//		  	"destination12='"+sDest12+"',"+
//		  	"destination13='"+sDest13+"',"+
//		  	"destination14='"+sDest14+"',"+
//		  	"destination15='"+sDest15+"',"+
//		  	"destination16='"+sDest16+"',"+
//		  	"destination17='"+sDest17+"',"+
//		  	"destination18='"+sDest18+"',"+
//		  	"destination19='"+sDest19+"',"+
//		  	"destination20='"+sDest20+"',"+
//		  	"destination21='"+sDest21+"'," +
//		  	//"filing_time='"+sFilingTime+"',"+ //ga boleh di Edit
//		  	"originator='"+sOriginator+"'," +
//			"ori_ref='"+sOriRef+"'," +
//	  
//			freetext+"='"+sFree+"'," +
//			filedby+"='"+sFiledby+"'" +
//			//`time_control` timestamp NOT NULL default '0000-00-00 00:00:00',
//			//`actual_flight` enum('0','1','2') default '0',
//			//`jam` varchar(6) default NULL,
//			//`tgl` datetime default NULL,
//			//`st` enum('0','1') default '1',
//			//`flg` enum('0','1') default '0',
//			//`st1` enum('1') default '1',
//			//`address` text,
//			//`tbl_name` varchar(69) default NULL,
//			//`io` enum('0','1') NOT NULL default '0',
//			" WHERE "+id_tbl+"="+ID;
//	
//	jdbc.update(update);
//}
