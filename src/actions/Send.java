/**
 * re created by Mega Lestari
 * on Monday, 10 February 2014
 * descriptions:
 * sending message
 */
package actions;

import java.sql.Connection;

import org.eclipse.swt.widgets.Shell;

import readwrite.ReadFromFile;
import setting.ErrMsg;
import setting.Shells;
import setting.Times;
import databases.jdbc;
import displays.DialogFactory;
import displays.MainForm;
import displays.forms.ATSForms;
import displays.forms.HeaderComposite;
import displays.tables.TableOutgoing;


public class Send {
    private ReadFromFile rff = new ReadFromFile();
    private SendSave ss = new SendSave();
    private ErrMsg em = new ErrMsg();
    private Times time = new Times();
    ViewATSFunction vats = new ViewATSFunction();
    
    public static Connection gConnNTM;
    public static String data="",sStatus="waiting",autoprint="";
//    final String messageSend = "Message sent.";
//    final String messageSave = "Message saved.";
    final String warningOutboxMsg = "Send is not allowed from outbox message !!";
    
    String kType7a="",kType13a="",kType13b="",kType16a="",kType18b,kJam="",t15a="",t15b="",t14c="",t14d="",t14e="",sDiscard="",sendType="";
    
    int iLength=0;
    
    String getText() {
    	return "Information";
    }
    
    public Send(String sData, String sType) {
    	data = sData;
    	sendType = sType;
    	System.out.println("\nID Send=" + data + "\t*Sendtype=" + sType + "#");
    	ss.getTbl(data); 
    	
    	rff.readConfiguration();
    	sDiscard = rff.getDiscard();
    	iLength = rff.getMsglength();
    	
    	autoprint = ReadFromFile.ReadInputFile1("/opt/autoprint.txt");
    	
    	/**
    	 * MESSAGE FROM OUTBOX NOT ALLOWED TO BE SENT
    	 */
    	
    	if (data.compareToIgnoreCase("OutAMO")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nAMO, getText(), warningOutboxMsg); 
    	else if (data.compareToIgnoreCase("OutFREE")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nFREE, getText(), warningOutboxMsg); 
    	else if (data.compareToIgnoreCase("OutAFTN")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nAFTN, getText(), warningOutboxMsg); 
    	else if (data.compareToIgnoreCase("OutALR")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), warningOutboxMsg);
    	else if (data.compareToIgnoreCase("OutRCF")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nRCF, getText(), warningOutboxMsg);
    	else if (data.compareToIgnoreCase("OutFPL")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), warningOutboxMsg);
    	else if (data.compareToIgnoreCase("OutDLA")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nDLA, getText(), warningOutboxMsg);
    	else if (data.compareToIgnoreCase("OutCHG")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nCHG, getText(), warningOutboxMsg);
    	else if (data.compareToIgnoreCase("OutCNL")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nCNL, getText(), warningOutboxMsg);
    	else if (data.compareToIgnoreCase("OutDEP")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nDEP, getText(), warningOutboxMsg);
    	else if (data.compareToIgnoreCase("OutARR")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nARR, getText(), warningOutboxMsg);
    	else if (data.compareToIgnoreCase("OutCDN")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nCDN, getText(), warningOutboxMsg);
    	else if (data.compareToIgnoreCase("OutCPL")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), warningOutboxMsg);
    	else if (data.compareToIgnoreCase("OutEST")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), warningOutboxMsg);
    	else if (data.compareToIgnoreCase("OutACP")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nACP, getText(), warningOutboxMsg);
    	else if (data.compareToIgnoreCase("OutLAM")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nLAM, getText(), warningOutboxMsg);
    	else if (data.compareToIgnoreCase("OutRQP")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nRQP, getText(), warningOutboxMsg);
    	else if (data.compareToIgnoreCase("OutRQS")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nRQS, getText(), warningOutboxMsg);
    	else if (data.compareToIgnoreCase("OutSPL")==0 && sType.compareTo("save")!=0) DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), warningOutboxMsg);
    	
    	/**
    	 * NORMAL MESSAGE ALLOWED TO BE SENT
    	 * OUTBOX MESSAGE ALLOWED TO BE UPDATED (Save)
    	 */
    	else if (data.compareToIgnoreCase("NewAMO")==0 || data.compareToIgnoreCase("EditAMO")==0 || (data.compareToIgnoreCase("OutAMO")==0 && sType.compareTo("save")==0)) sAMO();
    	else if (data.compareToIgnoreCase("NewFREE")==0 || data.compareToIgnoreCase("EditFREE")==0 || (data.compareToIgnoreCase("OutFREE")==0 && sType.compareTo("save")==0)) sFREE();
    	else if (data.compareToIgnoreCase("NewAFTN")==0 || data.compareToIgnoreCase("EditAFTN")==0 || (data.compareToIgnoreCase("OutAFTN")==0 && sType.compareTo("save")==0)) sAFTN();
    	else if (data.compareToIgnoreCase("NewALR")==0 || data.compareToIgnoreCase("EditALR")==0 || (data.compareToIgnoreCase("OutALR")==0 && sType.compareTo("save")==0)) sALR();
    	else if (data.compareToIgnoreCase("NewRCF")==0 || data.compareToIgnoreCase("EditRCF")==0 || (data.compareToIgnoreCase("OutRCF")==0 && sType.compareTo("save")==0)) sRCF();
    	else if (data.compareToIgnoreCase("NewFPL")==0 || data.compareToIgnoreCase("EditFPL")==0 || (data.compareToIgnoreCase("OutFPL")==0 && sType.compareTo("save")==0)) sFPL();
    	else if (data.compareToIgnoreCase("NewDLA")==0 || data.compareToIgnoreCase("EditDLA")==0 || (data.compareToIgnoreCase("OutDLA")==0 && sType.compareTo("save")==0)) sDLA();
    	else if (data.compareToIgnoreCase("NewCHG")==0 || data.compareToIgnoreCase("EditCHG")==0 || (data.compareToIgnoreCase("OutCHG")==0 && sType.compareTo("save")==0)) sCHG();
    	else if (data.compareToIgnoreCase("NewCNL")==0 || data.compareToIgnoreCase("EditCNL")==0 || (data.compareToIgnoreCase("OutCNL")==0 && sType.compareTo("save")==0)) sCNL();
    	else if (data.compareToIgnoreCase("NewDEP")==0 || data.compareToIgnoreCase("EditDEP")==0 || (data.compareToIgnoreCase("OutDEP")==0 && sType.compareTo("save")==0)) sDEP();	
    	else if (data.compareToIgnoreCase("NewARR")==0 || data.compareToIgnoreCase("EditARR")==0 || (data.compareToIgnoreCase("OutARR")==0 && sType.compareTo("save")==0)) sARR(); 
    	else if (data.compareToIgnoreCase("NewCDN")==0 || data.compareToIgnoreCase("EditCDN")==0 || (data.compareToIgnoreCase("OutCDN")==0 && sType.compareTo("save")==0)) sCDN(); 
     	else if (data.compareToIgnoreCase("NewCPL")==0 || data.compareToIgnoreCase("EditCPL")==0 || (data.compareToIgnoreCase("OutCPL")==0 && sType.compareTo("save")==0)) sCPL();
     	else if (data.compareToIgnoreCase("NewEST")==0 || data.compareToIgnoreCase("EditEST")==0 || (data.compareToIgnoreCase("OutEST")==0 && sType.compareTo("save")==0)) sEST();	
     	else if (data.compareToIgnoreCase("NewACP")==0 || data.compareToIgnoreCase("EditACP")==0 || (data.compareToIgnoreCase("OutACP")==0 && sType.compareTo("save")==0)) sACP();
     	else if (data.compareToIgnoreCase("NewLAM")==0 || data.compareToIgnoreCase("EditLAM")==0 || (data.compareToIgnoreCase("OutLAM")==0 && sType.compareTo("save")==0)) sLAM();
     	else if (data.compareToIgnoreCase("NewRQP")==0 || data.compareToIgnoreCase("EditRQP")==0 || (data.compareToIgnoreCase("OutRQP")==0 && sType.compareTo("save")==0)) sRQP();	
     	else if (data.compareToIgnoreCase("NewRQS")==0 || data.compareToIgnoreCase("EditRQS")==0 || (data.compareToIgnoreCase("OutRQS")==0 && sType.compareTo("save")==0)) sRQS();     		
     	else if (data.compareToIgnoreCase("NewSPL")==0 || data.compareToIgnoreCase("EditSPL")==0 || (data.compareToIgnoreCase("OutSPL")==0 && sType.compareTo("save")==0)) sSPL();
    }
    
    void infoDuplicateAdd(Shell shl) {
    	DialogFactory.openWarningDialog(shl, getText(), "Duplicate address !"); 
    }
    
    void clearBodyTemplate(String sID) {
    	if (sID.compareToIgnoreCase("amo")==0) Discard.amo(); 
    	else if (sID.compareToIgnoreCase("free")==0) Discard.free(); 
    	else if (sID.compareToIgnoreCase("aftn")==0) Discard.aftn(); 
		else if (sID.compareToIgnoreCase("alr")==0) Discard.alr(); 
		else if (sID.compareToIgnoreCase("rcf")==0) Discard.rcf(); 
		else if (sID.compareToIgnoreCase("fpl")==0) Discard.fpl(); 
		else if (sID.compareToIgnoreCase("dla")==0) Discard.dla(); 
		else if (sID.compareToIgnoreCase("chg")==0) Discard.chg(); 
		else if (sID.compareToIgnoreCase("cnl")==0) Discard.cnl(); 
		else if (sID.compareToIgnoreCase("dep")==0) Discard.dep(); 
		else if (sID.compareToIgnoreCase("arr")==0) Discard.arr(); 
		else if (sID.compareToIgnoreCase("cdn")==0) Discard.cdn(); 
		else if (sID.compareToIgnoreCase("cpl")==0) Discard.cpl(); 
		else if (sID.compareToIgnoreCase("est")==0) Discard.est(); 
		else if (sID.compareToIgnoreCase("acp")==0) Discard.acp(); 
		else if (sID.compareToIgnoreCase("lam")==0) Discard.lam(); 
		else if (sID.compareToIgnoreCase("rqp")==0) Discard.rqp(); 
		else if (sID.compareToIgnoreCase("rqs")==0) Discard.rqs(); 
		else if (sID.compareToIgnoreCase("spl")==0) Discard.spl(); 
    }
    
    void setFocusDest1(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest1AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest1ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest1RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest1FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest1DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest1CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest1CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest1DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest1ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest1CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest1CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest1ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest1ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest1LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest1RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest1RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest1SPLFocus(); 
    }
    
    void setFocusDest2(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest2AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest2ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest2RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest2FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest2DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest2CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest2CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest2DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest2ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest2CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest2CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest2ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest2ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest2LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest2RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest2RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest2SPLFocus(); 
    }
    
    void setFocusDest3(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest3AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest3ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest3RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest3FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest3DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest3CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest3CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest3DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest3ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest3CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest3CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest3ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest3ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest3LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest3RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest3RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest3SPLFocus(); 
    }
    
    void setFocusDest4(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest4AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest4ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest4RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest4FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest4DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest4CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest4CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest4DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest4ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest4CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest4CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest4ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest4ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest4LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest4RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest4RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest4SPLFocus(); 
    }
    
    void setFocusDest5(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest5AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest5ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest5RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest5FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest5DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest5CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest5CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest5DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest5ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest5CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest5CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest5ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest5ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest5LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest5RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest5RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest5SPLFocus(); 
    }
    
    void setFocusDest6(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest6AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest6ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest6RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest6FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest6DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest6CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest6CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest6DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest6ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest6CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest6CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest6ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest6ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest6LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest6RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest6RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest6SPLFocus(); 
    }
    
    void setFocusDest7(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest7AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest7ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest7RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest7FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest7DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest7CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest7CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest7DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest7ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest7CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest7CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest7ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest7ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest7LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest7RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest7RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest7SPLFocus(); 
    }
    
    void setFocusDest8(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest8AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest8ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest8RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest8FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest8DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest8CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest8CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest8DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest8ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest8CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest8CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest8ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest8ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest8LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest8RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest8RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest8SPLFocus(); 
    }
    
    void setFocusDest9(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest9AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest9ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest9RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest9FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest9DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest9CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest9CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest9DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest9ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest9CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest9CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest9ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest9ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest9LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest9RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest9RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest9SPLFocus(); 
    }
    
    void setFocusDest10(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest10AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest10ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest10RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest10FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest10DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest10CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest10CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest10DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest10ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest10CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest10CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest10ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest10ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest10LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest10RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest10RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest10SPLFocus(); 
    }
    
    void setFocusDest11(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest11AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest11ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest11RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest11FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest11DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest11CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest11CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest11DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest11ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest11CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest11CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest11ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest11ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest11LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest11RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest11RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest11SPLFocus(); 
    }
    
    void setFocusDest12(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest12AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest12ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest12RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest12FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest12DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest12CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest12CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest12DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest12ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest12CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest12CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest12ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest12ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest12LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest12RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest12RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest12SPLFocus(); 
    }
    
    void setFocusDest13(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest13AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest13ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest13RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest13FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest13DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest13CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest13CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest13DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest13ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest13CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest13CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest13ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest13ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest13LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest13RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest13RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest13SPLFocus(); 
    }
    
    void setFocusDest14(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest14AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest14ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest14RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest14FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest14DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest14CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest14CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest14DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest14ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest14CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest14CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest14ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest14ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest14LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest14RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest14RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest14SPLFocus(); 
    }
    
    void setFocusDest15(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest15AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest15ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest15RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest15FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest15DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest15CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest15CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest15DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest15ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest15CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest15CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest15ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest15ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest15LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest15RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest15RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest15SPLFocus(); 
    }
    
    void setFocusDest16(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest16AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest16ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest16RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest16FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest16DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest16CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest16CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest16DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest16ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest16CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest16CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest16ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest16ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest16LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest16RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest16RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest16SPLFocus(); 
    }
    
    void setFocusDest17(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest17AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest17ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest17RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest17FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest17DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest17CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest17CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest17DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest17ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest17CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest17CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest17ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest17ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest17LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest17RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest17RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest17SPLFocus(); 
    }
    
    void setFocusDest18(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest18AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest18ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest18RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest18FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest18DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest18CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest18CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest18DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest18ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest18CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest18CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest18ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest18ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest18LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest18RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest18RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest18SPLFocus(); 
    }
    
    void setFocusDest19(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest19AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest19ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest19RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest19FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest19DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest19CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest19CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest19DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest19ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest19CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest19CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest19ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest19ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest19LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest19RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest19RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest19SPLFocus(); 
    }
    
    void setFocusDest20(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest20AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest20ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest20RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest20FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest20DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest20CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest20CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest20DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest20ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest20CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest20CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest20ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest20ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest20LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest20RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest20RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest20SPLFocus(); 
    }
    
    void setFocusDest21(String sID) {
    	if (sID.compareToIgnoreCase("aftn")==0) HeaderComposite.tDest21AFTNFocus(); 
		else if (sID.compareToIgnoreCase("alr")==0) HeaderComposite.tDest21ALRFocus(); 
		else if (sID.compareToIgnoreCase("rcf")==0) HeaderComposite.tDest21RCFFocus(); 
		else if (sID.compareToIgnoreCase("fpl")==0) HeaderComposite.tDest21FPLFocus(); 
		else if (sID.compareToIgnoreCase("dla")==0) HeaderComposite.tDest21DLAFocus(); 
		else if (sID.compareToIgnoreCase("chg")==0) HeaderComposite.tDest21CHGFocus(); 
		else if (sID.compareToIgnoreCase("cnl")==0) HeaderComposite.tDest21CNLFocus(); 
		else if (sID.compareToIgnoreCase("dep")==0) HeaderComposite.tDest21DEPFocus(); 
		else if (sID.compareToIgnoreCase("arr")==0) HeaderComposite.tDest21ARRFocus(); 
		else if (sID.compareToIgnoreCase("cdn")==0) HeaderComposite.tDest21CDNFocus(); 
		else if (sID.compareToIgnoreCase("cpl")==0) HeaderComposite.tDest21CPLFocus(); 
		else if (sID.compareToIgnoreCase("est")==0) HeaderComposite.tDest21ESTFocus(); 
		else if (sID.compareToIgnoreCase("acp")==0) HeaderComposite.tDest21ACPFocus(); 
		else if (sID.compareToIgnoreCase("lam")==0) HeaderComposite.tDest21LAMFocus(); 
		else if (sID.compareToIgnoreCase("rqp")==0) HeaderComposite.tDest21RQPFocus(); 
		else if (sID.compareToIgnoreCase("rqs")==0) HeaderComposite.tDest21RQSFocus(); 
		else if (sID.compareToIgnoreCase("spl")==0) HeaderComposite.tDest21SPLFocus(); 
    }

    void validasiDuplicateAddress(Shell shl,final String sID) {

    	//destination1
    	if (!ss.sDest1.isEmpty() && (ss.sDest1.compareTo(ss.sDest2)==0 || ss.sDest1.compareTo(ss.sDest3)==0 || ss.sDest1.compareTo(ss.sDest4)==0 ||
					ss.sDest1.compareTo(ss.sDest5)==0 || ss.sDest1.compareTo(ss.sDest6)==0 || ss.sDest1.compareTo(ss.sDest7)==0 || 
					ss.sDest1.compareTo(ss.sDest8)==0 || ss.sDest1.compareTo(ss.sDest9)==0 || ss.sDest1.compareTo(ss.sDest10)==0 ||
					ss.sDest1.compareTo(ss.sDest11)==0 || ss.sDest1.compareTo(ss.sDest12)==0 || ss.sDest1.compareTo(ss.sDest13)==0 ||
					ss.sDest1.compareTo(ss.sDest14)==0 || ss.sDest1.compareTo(ss.sDest15)==0 || ss.sDest1.compareTo(ss.sDest16)==0 || 
					ss.sDest1.compareTo(ss.sDest17)==0 || ss.sDest1.compareTo(ss.sDest18)==0 || ss.sDest1.compareTo(ss.sDest19)==0 ||
					ss.sDest1.compareTo(ss.sDest20)==0 || ss.sDest1.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest1(sID); } 
    	//destination2
    	else if (!ss.sDest2.isEmpty() && (ss.sDest2.compareTo(ss.sDest1)==0 || ss.sDest2.compareTo(ss.sDest3)==0 || ss.sDest2.compareTo(ss.sDest4)==0 ||
					ss.sDest2.compareTo(ss.sDest5)==0 || ss.sDest2.compareTo(ss.sDest6)==0 || ss.sDest2.compareTo(ss.sDest7)==0 || 
					ss.sDest2.compareTo(ss.sDest8)==0 || ss.sDest2.compareTo(ss.sDest9)==0 || ss.sDest2.compareTo(ss.sDest10)==0 ||
					ss.sDest2.compareTo(ss.sDest11)==0 || ss.sDest2.compareTo(ss.sDest12)==0 || ss.sDest2.compareTo(ss.sDest13)==0 ||
					ss.sDest2.compareTo(ss.sDest14)==0 || ss.sDest2.compareTo(ss.sDest15)==0 || ss.sDest2.compareTo(ss.sDest16)==0 || 
					ss.sDest2.compareTo(ss.sDest17)==0 || ss.sDest2.compareTo(ss.sDest18)==0 || ss.sDest2.compareTo(ss.sDest19)==0 ||
					ss.sDest2.compareTo(ss.sDest20)==0 || ss.sDest2.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest2(sID); } 
    	//destination3
    	else if (!ss.sDest3.isEmpty() && (ss.sDest3.compareTo(ss.sDest1)==0 || ss.sDest3.compareTo(ss.sDest2)==0 || ss.sDest3.compareTo(ss.sDest4)==0 ||
					ss.sDest3.compareTo(ss.sDest5)==0 || ss.sDest3.compareTo(ss.sDest6)==0 || ss.sDest3.compareTo(ss.sDest7)==0 || 
					ss.sDest3.compareTo(ss.sDest8)==0 || ss.sDest3.compareTo(ss.sDest9)==0 || ss.sDest3.compareTo(ss.sDest10)==0 ||
					ss.sDest3.compareTo(ss.sDest11)==0 || ss.sDest3.compareTo(ss.sDest12)==0 || ss.sDest3.compareTo(ss.sDest13)==0 ||
					ss.sDest3.compareTo(ss.sDest14)==0 || ss.sDest3.compareTo(ss.sDest15)==0 || ss.sDest3.compareTo(ss.sDest16)==0 || 
					ss.sDest3.compareTo(ss.sDest17)==0 || ss.sDest3.compareTo(ss.sDest18)==0 || ss.sDest3.compareTo(ss.sDest19)==0 ||
					ss.sDest3.compareTo(ss.sDest20)==0 || ss.sDest3.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest3(sID); } 
    	//destination4
    	else if (!ss.sDest4.isEmpty() && (ss.sDest4.compareTo(ss.sDest1)==0 || ss.sDest4.compareTo(ss.sDest2)==0 || ss.sDest4.compareTo(ss.sDest3)==0 ||
					ss.sDest4.compareTo(ss.sDest5)==0 || ss.sDest4.compareTo(ss.sDest6)==0 || ss.sDest4.compareTo(ss.sDest7)==0 || 
					ss.sDest4.compareTo(ss.sDest8)==0 || ss.sDest4.compareTo(ss.sDest9)==0 || ss.sDest4.compareTo(ss.sDest10)==0 ||
					ss.sDest4.compareTo(ss.sDest11)==0 || ss.sDest4.compareTo(ss.sDest12)==0 || ss.sDest4.compareTo(ss.sDest13)==0 ||
					ss.sDest4.compareTo(ss.sDest14)==0 || ss.sDest4.compareTo(ss.sDest15)==0 || ss.sDest4.compareTo(ss.sDest16)==0 || 
					ss.sDest4.compareTo(ss.sDest17)==0 || ss.sDest4.compareTo(ss.sDest18)==0 || ss.sDest4.compareTo(ss.sDest19)==0 ||
					ss.sDest4.compareTo(ss.sDest20)==0 || ss.sDest4.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest4(sID); }
    	//destination5
    	else if (!ss.sDest5.isEmpty() && (ss.sDest5.compareTo(ss.sDest1)==0 || ss.sDest5.compareTo(ss.sDest3)==0 || ss.sDest5.compareTo(ss.sDest4)==0 ||
					ss.sDest5.compareTo(ss.sDest2)==0 || ss.sDest5.compareTo(ss.sDest6)==0 || ss.sDest5.compareTo(ss.sDest7)==0 || 
					ss.sDest5.compareTo(ss.sDest8)==0 || ss.sDest5.compareTo(ss.sDest9)==0 || ss.sDest5.compareTo(ss.sDest10)==0 ||
					ss.sDest5.compareTo(ss.sDest11)==0 || ss.sDest5.compareTo(ss.sDest12)==0 || ss.sDest5.compareTo(ss.sDest13)==0 ||
					ss.sDest5.compareTo(ss.sDest14)==0 || ss.sDest5.compareTo(ss.sDest15)==0 || ss.sDest5.compareTo(ss.sDest16)==0 || 
					ss.sDest5.compareTo(ss.sDest17)==0 || ss.sDest5.compareTo(ss.sDest18)==0 || ss.sDest5.compareTo(ss.sDest19)==0 ||
					ss.sDest5.compareTo(ss.sDest20)==0 || ss.sDest5.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest5(sID); }
    	//destination6
    	else if (!ss.sDest6.isEmpty() && (ss.sDest6.compareTo(ss.sDest1)==0 || ss.sDest6.compareTo(ss.sDest3)==0 || ss.sDest6.compareTo(ss.sDest4)==0 ||
					ss.sDest6.compareTo(ss.sDest5)==0 || ss.sDest6.compareTo(ss.sDest2)==0 || ss.sDest6.compareTo(ss.sDest7)==0 || 
					ss.sDest6.compareTo(ss.sDest8)==0 || ss.sDest6.compareTo(ss.sDest9)==0 || ss.sDest6.compareTo(ss.sDest10)==0 ||
					ss.sDest6.compareTo(ss.sDest11)==0 || ss.sDest6.compareTo(ss.sDest12)==0 || ss.sDest6.compareTo(ss.sDest13)==0 ||
					ss.sDest6.compareTo(ss.sDest14)==0 || ss.sDest6.compareTo(ss.sDest15)==0 || ss.sDest6.compareTo(ss.sDest16)==0 || 
					ss.sDest6.compareTo(ss.sDest17)==0 || ss.sDest6.compareTo(ss.sDest18)==0 || ss.sDest6.compareTo(ss.sDest19)==0 ||
					ss.sDest6.compareTo(ss.sDest20)==0 || ss.sDest6.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest6(sID); } 
    	//destination7
    	else if (!ss.sDest7.isEmpty() && (ss.sDest7.compareTo(ss.sDest1)==0 || ss.sDest7.compareTo(ss.sDest3)==0 || ss.sDest7.compareTo(ss.sDest4)==0 ||
					ss.sDest7.compareTo(ss.sDest5)==0 || ss.sDest7.compareTo(ss.sDest6)==0 || ss.sDest7.compareTo(ss.sDest2)==0 || 
					ss.sDest7.compareTo(ss.sDest8)==0 || ss.sDest7.compareTo(ss.sDest9)==0 || ss.sDest7.compareTo(ss.sDest10)==0 ||
					ss.sDest7.compareTo(ss.sDest11)==0 || ss.sDest7.compareTo(ss.sDest12)==0 || ss.sDest7.compareTo(ss.sDest13)==0 ||
					ss.sDest7.compareTo(ss.sDest14)==0 || ss.sDest7.compareTo(ss.sDest15)==0 || ss.sDest7.compareTo(ss.sDest16)==0 || 
					ss.sDest7.compareTo(ss.sDest17)==0 || ss.sDest7.compareTo(ss.sDest18)==0 || ss.sDest7.compareTo(ss.sDest19)==0 ||
					ss.sDest7.compareTo(ss.sDest20)==0 || ss.sDest7.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest7(sID); }
    	//destination8
		else if (!ss.sDest8.isEmpty() && (ss.sDest8.compareTo(ss.sDest1)==0 || ss.sDest8.compareTo(ss.sDest3)==0 || ss.sDest8.compareTo(ss.sDest4)==0 ||
					ss.sDest8.compareTo(ss.sDest5)==0 || ss.sDest8.compareTo(ss.sDest6)==0 || ss.sDest8.compareTo(ss.sDest7)==0 || 
					ss.sDest8.compareTo(ss.sDest2)==0 || ss.sDest8.compareTo(ss.sDest9)==0 || ss.sDest8.compareTo(ss.sDest10)==0 ||
					ss.sDest8.compareTo(ss.sDest11)==0 || ss.sDest8.compareTo(ss.sDest12)==0 || ss.sDest8.compareTo(ss.sDest13)==0 ||
					ss.sDest8.compareTo(ss.sDest14)==0 || ss.sDest8.compareTo(ss.sDest15)==0 || ss.sDest8.compareTo(ss.sDest16)==0 || 
					ss.sDest8.compareTo(ss.sDest17)==0 || ss.sDest8.compareTo(ss.sDest18)==0 || ss.sDest8.compareTo(ss.sDest19)==0 ||
					ss.sDest8.compareTo(ss.sDest20)==0 || ss.sDest8.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest8(sID); }
    	//destination9
		else if (!ss.sDest9.isEmpty() && (ss.sDest9.compareTo(ss.sDest1)==0 || ss.sDest9.compareTo(ss.sDest3)==0 || ss.sDest9.compareTo(ss.sDest4)==0 ||
					ss.sDest9.compareTo(ss.sDest5)==0 || ss.sDest9.compareTo(ss.sDest6)==0 || ss.sDest9.compareTo(ss.sDest7)==0 || 
					ss.sDest9.compareTo(ss.sDest8)==0 || ss.sDest9.compareTo(ss.sDest2)==0 || ss.sDest9.compareTo(ss.sDest10)==0 ||
					ss.sDest9.compareTo(ss.sDest11)==0 || ss.sDest9.compareTo(ss.sDest12)==0 || ss.sDest9.compareTo(ss.sDest13)==0 ||
					ss.sDest9.compareTo(ss.sDest14)==0 || ss.sDest9.compareTo(ss.sDest15)==0 || ss.sDest9.compareTo(ss.sDest16)==0 || 
					ss.sDest9.compareTo(ss.sDest17)==0 || ss.sDest9.compareTo(ss.sDest18)==0 || ss.sDest9.compareTo(ss.sDest19)==0 ||
					ss.sDest9.compareTo(ss.sDest20)==0 || ss.sDest9.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest9(sID); } 
    	//destination10
		else if (!ss.sDest10.isEmpty() && (ss.sDest10.compareTo(ss.sDest1)==0 || ss.sDest10.compareTo(ss.sDest3)==0 || ss.sDest10.compareTo(ss.sDest4)==0 ||
					ss.sDest10.compareTo(ss.sDest5)==0 || ss.sDest10.compareTo(ss.sDest6)==0 || ss.sDest10.compareTo(ss.sDest7)==0 || 
					ss.sDest10.compareTo(ss.sDest8)==0 || ss.sDest10.compareTo(ss.sDest9)==0 || ss.sDest10.compareTo(ss.sDest2)==0 ||
					ss.sDest10.compareTo(ss.sDest11)==0 || ss.sDest10.compareTo(ss.sDest12)==0 || ss.sDest10.compareTo(ss.sDest13)==0 ||
					ss.sDest10.compareTo(ss.sDest14)==0 || ss.sDest10.compareTo(ss.sDest15)==0 || ss.sDest10.compareTo(ss.sDest16)==0 || 
					ss.sDest10.compareTo(ss.sDest17)==0 || ss.sDest10.compareTo(ss.sDest18)==0 || ss.sDest10.compareTo(ss.sDest19)==0 ||
					ss.sDest10.compareTo(ss.sDest20)==0 || ss.sDest10.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest10(sID); } 
    	//destination11
		else if (!ss.sDest11.isEmpty() && (ss.sDest11.compareTo(ss.sDest1)==0 || ss.sDest11.compareTo(ss.sDest3)==0 || ss.sDest11.compareTo(ss.sDest4)==0 ||
					ss.sDest11.compareTo(ss.sDest5)==0 || ss.sDest11.compareTo(ss.sDest6)==0 || ss.sDest11.compareTo(ss.sDest7)==0 || 
					ss.sDest11.compareTo(ss.sDest8)==0 || ss.sDest11.compareTo(ss.sDest9)==0 || ss.sDest11.compareTo(ss.sDest10)==0 ||
					ss.sDest11.compareTo(ss.sDest2)==0 || ss.sDest11.compareTo(ss.sDest12)==0 || ss.sDest11.compareTo(ss.sDest13)==0 ||
					ss.sDest11.compareTo(ss.sDest14)==0 || ss.sDest11.compareTo(ss.sDest15)==0 || ss.sDest11.compareTo(ss.sDest16)==0 || 
					ss.sDest11.compareTo(ss.sDest17)==0 || ss.sDest11.compareTo(ss.sDest18)==0 || ss.sDest11.compareTo(ss.sDest19)==0 ||
					ss.sDest11.compareTo(ss.sDest20)==0 || ss.sDest11.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest11(sID); } 
    	//destination12
		else if (!ss.sDest12.isEmpty() && (ss.sDest12.compareTo(ss.sDest1)==0 || ss.sDest12.compareTo(ss.sDest3)==0 || ss.sDest12.compareTo(ss.sDest4)==0 ||
					ss.sDest12.compareTo(ss.sDest5)==0 || ss.sDest12.compareTo(ss.sDest6)==0 || ss.sDest12.compareTo(ss.sDest7)==0 || 
					ss.sDest12.compareTo(ss.sDest8)==0 || ss.sDest12.compareTo(ss.sDest9)==0 || ss.sDest12.compareTo(ss.sDest10)==0 ||
					ss.sDest12.compareTo(ss.sDest11)==0 || ss.sDest12.compareTo(ss.sDest2)==0 || ss.sDest12.compareTo(ss.sDest13)==0 ||
					ss.sDest12.compareTo(ss.sDest14)==0 || ss.sDest12.compareTo(ss.sDest15)==0 || ss.sDest12.compareTo(ss.sDest16)==0 || 
					ss.sDest12.compareTo(ss.sDest17)==0 || ss.sDest12.compareTo(ss.sDest18)==0 || ss.sDest12.compareTo(ss.sDest19)==0 ||
					ss.sDest12.compareTo(ss.sDest20)==0 || ss.sDest12.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest12(sID); } 
    	//destination13
		else if (!ss.sDest13.isEmpty() && (ss.sDest13.compareTo(ss.sDest1)==0 || ss.sDest13.compareTo(ss.sDest3)==0 || ss.sDest13.compareTo(ss.sDest4)==0 ||
					ss.sDest13.compareTo(ss.sDest5)==0 || ss.sDest13.compareTo(ss.sDest6)==0 || ss.sDest13.compareTo(ss.sDest7)==0 || 
					ss.sDest13.compareTo(ss.sDest8)==0 || ss.sDest13.compareTo(ss.sDest9)==0 || ss.sDest13.compareTo(ss.sDest10)==0 ||
					ss.sDest13.compareTo(ss.sDest11)==0 || ss.sDest13.compareTo(ss.sDest12)==0 || ss.sDest13.compareTo(ss.sDest2)==0 ||
					ss.sDest13.compareTo(ss.sDest14)==0 || ss.sDest13.compareTo(ss.sDest15)==0 || ss.sDest13.compareTo(ss.sDest16)==0 || 
					ss.sDest13.compareTo(ss.sDest17)==0 || ss.sDest13.compareTo(ss.sDest18)==0 || ss.sDest13.compareTo(ss.sDest19)==0 ||
					ss.sDest13.compareTo(ss.sDest20)==0 || ss.sDest13.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest13(sID); } 
    	//destination14
		else if (!ss.sDest14.isEmpty() && (ss.sDest14.compareTo(ss.sDest1)==0 || ss.sDest14.compareTo(ss.sDest3)==0 || ss.sDest14.compareTo(ss.sDest4)==0 ||
					ss.sDest14.compareTo(ss.sDest5)==0 || ss.sDest14.compareTo(ss.sDest6)==0 || ss.sDest14.compareTo(ss.sDest7)==0 || 
					ss.sDest14.compareTo(ss.sDest8)==0 || ss.sDest14.compareTo(ss.sDest9)==0 || ss.sDest14.compareTo(ss.sDest10)==0 ||
					ss.sDest14.compareTo(ss.sDest11)==0 || ss.sDest14.compareTo(ss.sDest12)==0 || ss.sDest14.compareTo(ss.sDest13)==0 ||
					ss.sDest14.compareTo(ss.sDest2)==0 || ss.sDest14.compareTo(ss.sDest15)==0 || ss.sDest14.compareTo(ss.sDest16)==0 || 
					ss.sDest14.compareTo(ss.sDest17)==0 || ss.sDest14.compareTo(ss.sDest18)==0 || ss.sDest14.compareTo(ss.sDest19)==0 ||
					ss.sDest14.compareTo(ss.sDest20)==0 || ss.sDest14.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest14(sID); } 
    	//destination15
		else if (!ss.sDest15.isEmpty() && (ss.sDest15.compareTo(ss.sDest1)==0 || ss.sDest15.compareTo(ss.sDest3)==0 || ss.sDest15.compareTo(ss.sDest4)==0 ||
					ss.sDest15.compareTo(ss.sDest5)==0 || ss.sDest15.compareTo(ss.sDest6)==0 || ss.sDest15.compareTo(ss.sDest7)==0 || 
					ss.sDest15.compareTo(ss.sDest8)==0 || ss.sDest15.compareTo(ss.sDest9)==0 || ss.sDest15.compareTo(ss.sDest10)==0 ||
					ss.sDest15.compareTo(ss.sDest11)==0 || ss.sDest15.compareTo(ss.sDest12)==0 || ss.sDest15.compareTo(ss.sDest13)==0 ||
					ss.sDest15.compareTo(ss.sDest14)==0 || ss.sDest15.compareTo(ss.sDest2)==0 || ss.sDest15.compareTo(ss.sDest16)==0 || 
					ss.sDest15.compareTo(ss.sDest17)==0 || ss.sDest15.compareTo(ss.sDest18)==0 || ss.sDest15.compareTo(ss.sDest19)==0 ||
					ss.sDest15.compareTo(ss.sDest20)==0 || ss.sDest15.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest15(sID); } 
    	//destination16
		else if (!ss.sDest16.isEmpty() && (ss.sDest16.compareTo(ss.sDest1)==0 || ss.sDest16.compareTo(ss.sDest3)==0 || ss.sDest16.compareTo(ss.sDest4)==0 ||
					ss.sDest16.compareTo(ss.sDest5)==0 || ss.sDest16.compareTo(ss.sDest6)==0 || ss.sDest16.compareTo(ss.sDest7)==0 || 
					ss.sDest16.compareTo(ss.sDest8)==0 || ss.sDest16.compareTo(ss.sDest9)==0 || ss.sDest16.compareTo(ss.sDest10)==0 ||
					ss.sDest16.compareTo(ss.sDest11)==0 || ss.sDest16.compareTo(ss.sDest12)==0 || ss.sDest16.compareTo(ss.sDest13)==0 ||
					ss.sDest16.compareTo(ss.sDest14)==0 || ss.sDest16.compareTo(ss.sDest15)==0 || ss.sDest16.compareTo(ss.sDest2)==0 || 
					ss.sDest16.compareTo(ss.sDest17)==0 || ss.sDest16.compareTo(ss.sDest18)==0 || ss.sDest16.compareTo(ss.sDest19)==0 ||
					ss.sDest16.compareTo(ss.sDest20)==0 || ss.sDest16.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest16(sID); } 
    	//destination17
		else if (!ss.sDest17.isEmpty() && (ss.sDest17.compareTo(ss.sDest1)==0 || ss.sDest17.compareTo(ss.sDest3)==0 || ss.sDest17.compareTo(ss.sDest4)==0 ||
					ss.sDest17.compareTo(ss.sDest5)==0 || ss.sDest17.compareTo(ss.sDest6)==0 || ss.sDest17.compareTo(ss.sDest7)==0 || 
					ss.sDest17.compareTo(ss.sDest8)==0 || ss.sDest17.compareTo(ss.sDest9)==0 || ss.sDest17.compareTo(ss.sDest10)==0 ||
					ss.sDest17.compareTo(ss.sDest11)==0 || ss.sDest17.compareTo(ss.sDest12)==0 || ss.sDest17.compareTo(ss.sDest13)==0 ||
					ss.sDest17.compareTo(ss.sDest14)==0 || ss.sDest17.compareTo(ss.sDest15)==0 || ss.sDest17.compareTo(ss.sDest16)==0 || 
					ss.sDest17.compareTo(ss.sDest2)==0 || ss.sDest17.compareTo(ss.sDest18)==0 || ss.sDest17.compareTo(ss.sDest19)==0 ||
					ss.sDest17.compareTo(ss.sDest20)==0 || ss.sDest17.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest17(sID); } 
    	//destination18
		else if (!ss.sDest18.isEmpty() && (ss.sDest18.compareTo(ss.sDest1)==0 || ss.sDest18.compareTo(ss.sDest3)==0 || ss.sDest18.compareTo(ss.sDest4)==0 ||
					ss.sDest18.compareTo(ss.sDest5)==0 || ss.sDest18.compareTo(ss.sDest6)==0 || ss.sDest18.compareTo(ss.sDest7)==0 || 
					ss.sDest18.compareTo(ss.sDest8)==0 || ss.sDest18.compareTo(ss.sDest9)==0 || ss.sDest18.compareTo(ss.sDest10)==0 ||
					ss.sDest18.compareTo(ss.sDest11)==0 || ss.sDest18.compareTo(ss.sDest12)==0 || ss.sDest18.compareTo(ss.sDest13)==0 ||
					ss.sDest18.compareTo(ss.sDest14)==0 || ss.sDest18.compareTo(ss.sDest15)==0 || ss.sDest18.compareTo(ss.sDest16)==0 || 
					ss.sDest18.compareTo(ss.sDest17)==0 || ss.sDest18.compareTo(ss.sDest2)==0 || ss.sDest18.compareTo(ss.sDest19)==0 ||
					ss.sDest18.compareTo(ss.sDest20)==0 || ss.sDest18.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest18(sID); } 
    	//destination19
		else if (!ss.sDest19.isEmpty() && (ss.sDest19.compareTo(ss.sDest1)==0 || ss.sDest19.compareTo(ss.sDest3)==0 || ss.sDest19.compareTo(ss.sDest4)==0 ||
					ss.sDest19.compareTo(ss.sDest5)==0 || ss.sDest19.compareTo(ss.sDest6)==0 || ss.sDest19.compareTo(ss.sDest7)==0 || 
					ss.sDest19.compareTo(ss.sDest8)==0 || ss.sDest19.compareTo(ss.sDest9)==0 || ss.sDest19.compareTo(ss.sDest10)==0 ||
					ss.sDest19.compareTo(ss.sDest11)==0 || ss.sDest19.compareTo(ss.sDest12)==0 || ss.sDest19.compareTo(ss.sDest13)==0 ||
					ss.sDest19.compareTo(ss.sDest14)==0 || ss.sDest19.compareTo(ss.sDest15)==0 || ss.sDest19.compareTo(ss.sDest16)==0 || 
					ss.sDest19.compareTo(ss.sDest17)==0 || ss.sDest19.compareTo(ss.sDest18)==0 || ss.sDest19.compareTo(ss.sDest2)==0 ||
					ss.sDest19.compareTo(ss.sDest20)==0 || ss.sDest19.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest19(sID); } 
    	//destination20
		else if (!ss.sDest20.isEmpty() && (ss.sDest20.compareTo(ss.sDest1)==0 || ss.sDest20.compareTo(ss.sDest3)==0 || ss.sDest20.compareTo(ss.sDest4)==0 ||
					ss.sDest20.compareTo(ss.sDest5)==0 || ss.sDest20.compareTo(ss.sDest6)==0 || ss.sDest20.compareTo(ss.sDest7)==0 || 
					ss.sDest20.compareTo(ss.sDest8)==0 || ss.sDest20.compareTo(ss.sDest9)==0 || ss.sDest20.compareTo(ss.sDest10)==0 ||
					ss.sDest20.compareTo(ss.sDest11)==0 || ss.sDest20.compareTo(ss.sDest12)==0 || ss.sDest20.compareTo(ss.sDest13)==0 ||
					ss.sDest20.compareTo(ss.sDest14)==0 || ss.sDest20.compareTo(ss.sDest15)==0 || ss.sDest20.compareTo(ss.sDest16)==0 || 
					ss.sDest20.compareTo(ss.sDest17)==0 || ss.sDest20.compareTo(ss.sDest18)==0 || ss.sDest20.compareTo(ss.sDest19)==0 ||
					ss.sDest20.compareTo(ss.sDest2)==0 || ss.sDest20.compareTo(ss.sDest21)==0) ) { infoDuplicateAdd(shl); setFocusDest20(sID); } 
    	//destination21
		else if (!ss.sDest21.isEmpty() && (ss.sDest21.compareTo(ss.sDest1)==0 || ss.sDest21.compareTo(ss.sDest3)==0 || ss.sDest21.compareTo(ss.sDest4)==0 ||
					ss.sDest21.compareTo(ss.sDest5)==0 || ss.sDest21.compareTo(ss.sDest6)==0 || ss.sDest21.compareTo(ss.sDest7)==0 || 
					ss.sDest21.compareTo(ss.sDest8)==0 || ss.sDest21.compareTo(ss.sDest9)==0 || ss.sDest21.compareTo(ss.sDest10)==0 ||
					ss.sDest21.compareTo(ss.sDest11)==0 || ss.sDest21.compareTo(ss.sDest12)==0 || ss.sDest21.compareTo(ss.sDest13)==0 ||
					ss.sDest21.compareTo(ss.sDest14)==0 || ss.sDest21.compareTo(ss.sDest15)==0 || ss.sDest21.compareTo(ss.sDest16)==0 || 
					ss.sDest21.compareTo(ss.sDest17)==0 || ss.sDest21.compareTo(ss.sDest18)==0 || ss.sDest21.compareTo(ss.sDest19)==0 ||
					ss.sDest21.compareTo(ss.sDest20)==0 || ss.sDest21.compareTo(ss.sDest2)==0) ) { infoDuplicateAdd(shl); setFocusDest21(sID); } 
		else {
			inputDB(shl, sID);
		}
    }
    
    void inputDB(Shell shl,final String sID) {
    	//*** PROSES INSERT KE DATABASE
		String strMsgType="";
		if (sID.compareToIgnoreCase("aftn")==0 || sID.compareToIgnoreCase("amo")==0 || sID.compareToIgnoreCase("free")==0) { 
			strMsgType="FREE TEXT"; 
		} else strMsgType=ss.s3a;
		//-----------------------------FILING TIME
		String ft="";
		if (sID.compareToIgnoreCase("amo")==0 || sID.compareToIgnoreCase("free")==0) { 
			if (sID.compareToIgnoreCase("amo")==0) {
				ss.sFree="amo"+ss.sFree;
				ss.msgBody="amo"+ss.msgBody;
				ss.sNote="amo";
				ss.sNoteChk="amo";
			} else if (sID.compareToIgnoreCase("FREE")==0) {
				ss.sNote="freetext";
				ss.sNoteChk="freetext";
			} else ss.sNoteChk="";
			
			ss.sBell="0"; 
			ss.sPriority="";ss.sDest1="";ss.sDest2="";ss.sDest3="";ss.sDest4="";ss.sDest5="";ss.sDest6="";ss.sDest7="";ss.sDest8="";ss.sDest9="";ss.sDest10="";ss.sDest11="";ss.sDest12="";ss.sDest13="";ss.sDest14="";ss.sDest15="";ss.sDest16="";ss.sDest17="";ss.sDest18="";ss.sDest19="";ss.sDest20="";ss.sDest21="";ss.sOriginator="";ss.sOriRef="";/*ss.sFiling=""; */ss.sBell="0";
			ft=""; 
		} else ft=ss.sFiling;
		
		//-----------------------------TYPE18
		if (sID.compareToIgnoreCase("alr")==0 || sID.compareToIgnoreCase("fpl")==0 || sID.compareToIgnoreCase("cpl")==0 ||
				sID.compareToIgnoreCase("spl")==0 || sID.compareToIgnoreCase("dla")==0 || sID.compareToIgnoreCase("chg")==0 ||
				sID.compareToIgnoreCase("cnl")==0 || sID.compareToIgnoreCase("dep")==0 || sID.compareToIgnoreCase("rqp")==0 ||
				sID.compareToIgnoreCase("rqs")==0) {
		if (ss.sReg.isEmpty() && ss.sDof.isEmpty() && ss.s18Baru.isEmpty()) ss.s18Baru = "0"; }
		//-----------------------------
		
		if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
			if (sID.compareToIgnoreCase("DLA")==0 || sID.compareToIgnoreCase("DEP")==0) {
				time.s16b="";
				ss.s16c=""; 
				ss.s16d="";
			} else if (sID.compareToIgnoreCase("CHG")==0 || sID.compareToIgnoreCase("CNL")==0 || sID.compareToIgnoreCase("ARR")==0 || 
					sID.compareToIgnoreCase("CPL")==0 || sID.compareToIgnoreCase("EST")==0 || sID.compareToIgnoreCase("CDN")==0 || 
					sID.compareToIgnoreCase("ACP")==0 || sID.compareToIgnoreCase("RQS")==0) {
				
				time.s13b="";
				
				time.s16b="";
				ss.s16c=""; 
				ss.s16d="";
			}
		}
		
		String sfiledby=ATSForms.getFiled_by();
		if (sfiledby.compareTo("")==0) sfiledby=" ";
		ss.sFiledby=sfiledby;
		//send: SEND-CLEAR 
		if (sendType.compareToIgnoreCase("sendclear")==0) {
			ss.setInsertATS(ss.sPriority,ss.sDest1,ss.sDest2,ss.sDest3,ss.sDest4,ss.sDest5,ss.sDest6,ss.sDest7,ss.sDest8,ss.sDest9,ss.sDest10,ss.sDest11,ss.sDest12,ss.sDest13,ss.sDest14,ss.sDest15,ss.sDest16,ss.sDest17,ss.sDest18,ss.sDest19,ss.sDest20,ss.sDest21,ss.sOriginator,ss.sOriRef,ft/*ss.sFiling*/,ss.sJam,time.tanggal,ss.sFiledby,ss.sTblName,sStatus,ss.s3a,ss.s3b,ss.s3c,ss.s5a,ss.s5b,ss.s5c,ss.s7a,ss.s7b,ss.s7c,ss.s8a,ss.s8b,ss.s9a,ss.s9b,ss.s9c,ss.t10a,ss.t10b,ss.s13a,time.s13b,ss.s14a,ss.s14b,ss.s14c,ss.s14d,ss.s14e,ss.s15a,ss.s15b,ss.s15c,ss.s16a,time.s16b,ss.s16c,ss.s16d,ss.s17a,ss.s17b,ss.s17c,ss.sReg,ss.sDof,ss.s18Baru,time.s19a,ss.s19b,ss.s19cUHF,ss.s19cVHF,ss.s19cELT,ss.s19dS,ss.s19dP,ss.s19dD,ss.s19dM,ss.s19dJ,ss.s19eJ,ss.s19eL,ss.s19eF,ss.s19eU,ss.s19eV,ss.s19fD,ss.s19fNum,ss.s19fCap,ss.s19fCov,ss.s19fCol,ss.s19g,ss.s19h,ss.s19Rmk,ss.s19i,ss.s20,ss.s21,ss.s22,ss.sSpace,ss.sTrkName,ss.sTrkId,ss.sTrkDate,ss.sTrkStatus,ss.sTrkAct,ss.sTrkExp,ss.sTrkWay,ss.sTrkLev,ss.sTrkRts,ss.sTrkRmk,ss.sFree,ss.msgBody,ss.sBell,ss.sNote);
			ss.setInsertCheck(jdbc.getKey(), ss.sTblName, ss.sFiling, ss.sJam, time.tanggal, ss.s7a, strMsgType, "", ss.sOriginator, ss.msg, "", ss.sFiledby, ss.sPriority, ss.sNoteChk);
			clearBodyTemplate(sID); } 
		//send: SEND ONLY
		else if (sendType.compareToIgnoreCase("send")==0) {
			ss.setInsertATS(ss.sPriority,ss.sDest1,ss.sDest2,ss.sDest3,ss.sDest4,ss.sDest5,ss.sDest6,ss.sDest7,ss.sDest8,ss.sDest9,ss.sDest10,ss.sDest11,ss.sDest12,ss.sDest13,ss.sDest14,ss.sDest15,ss.sDest16,ss.sDest17,ss.sDest18,ss.sDest19,ss.sDest20,ss.sDest21,ss.sOriginator,ss.sOriRef,ft/*ss.sFiling*/,ss.sJam,time.tanggal,ss.sFiledby,ss.sTblName,sStatus,ss.s3a,ss.s3b,ss.s3c,ss.s5a,ss.s5b,ss.s5c,ss.s7a,ss.s7b,ss.s7c,ss.s8a,ss.s8b,ss.s9a,ss.s9b,ss.s9c,ss.t10a,ss.t10b,ss.s13a,time.s13b,ss.s14a,ss.s14b,ss.s14c,ss.s14d,ss.s14e,ss.s15a,ss.s15b,ss.s15c,ss.s16a,time.s16b,ss.s16c,ss.s16d,ss.s17a,ss.s17b,ss.s17c,ss.sReg,ss.sDof,ss.s18Baru,time.s19a,ss.s19b,ss.s19cUHF,ss.s19cVHF,ss.s19cELT,ss.s19dS,ss.s19dP,ss.s19dD,ss.s19dM,ss.s19dJ,ss.s19eJ,ss.s19eL,ss.s19eF,ss.s19eU,ss.s19eV,ss.s19fD,ss.s19fNum,ss.s19fCap,ss.s19fCov,ss.s19fCol,ss.s19g,ss.s19h,ss.s19Rmk,ss.s19i,ss.s20,ss.s21,ss.s22,ss.sSpace,ss.sTrkName,ss.sTrkId,ss.sTrkDate,ss.sTrkStatus,ss.sTrkAct,ss.sTrkExp,ss.sTrkWay,ss.sTrkLev,ss.sTrkRts,ss.sTrkRmk,ss.sFree,ss.msgBody,ss.sBell,ss.sNote);
			ss.setInsertCheck(jdbc.getKey(), ss.sTblName, ss.sFiling, ss.sJam, time.tanggal, ss.s7a, strMsgType, "", ss.sOriginator, ss.msg, "", ss.sFiledby, ss.sPriority, ss.sNoteChk);
			if (sDiscard.compareToIgnoreCase("yes")==0) clearBodyTemplate(sID); } 
		//send: SAVE ONLY
		else if (sendType.compareToIgnoreCase("save")==0) {
			if (data.startsWith("Out")) { //Edit from Outbox
				ss.setUpdateATS(ss.sPriority,ss.sDest1,ss.sDest2,ss.sDest3,ss.sDest4,ss.sDest5,ss.sDest6,ss.sDest7,ss.sDest8,ss.sDest9,ss.sDest10,ss.sDest11,ss.sDest12,ss.sDest13,ss.sDest14,ss.sDest15,ss.sDest16,ss.sDest17,ss.sDest18,ss.sDest19,ss.sDest20,ss.sDest21,ss.sOriginator,ss.sOriRef,ft/*ss.sFiling*/,ss.sJam,time.tanggal,ss.sFiledby,TableOutgoing.GetTblName(),sStatus,ss.s3a,ss.s3b,ss.s3c,ss.s5a,ss.s5b,ss.s5c,ss.s7a,ss.s7b,ss.s7c,ss.s8a,ss.s8b,ss.s9a,ss.s9b,ss.s9c,ss.t10a,ss.t10b,ss.s13a,time.s13b,ss.s14a,ss.s14b,ss.s14c,ss.s14d,ss.s14e,ss.s15a,ss.s15b,ss.s15c,ss.s16a,time.s16b,ss.s16c,ss.s16d,ss.s17a,ss.s17b,ss.s17c,ss.sReg,ss.sDof,ss.s18Baru,time.s19a,ss.s19b,ss.s19cUHF,ss.s19cVHF,ss.s19cELT,ss.s19dS,ss.s19dP,ss.s19dD,ss.s19dM,ss.s19dJ,ss.s19eJ,ss.s19eL,ss.s19eF,ss.s19eU,ss.s19eV,ss.s19fD,ss.s19fNum,ss.s19fCap,ss.s19fCov,ss.s19fCol,ss.s19g,ss.s19h,ss.s19Rmk,ss.s19i,ss.s20,ss.s21,ss.s22,ss.sSpace,ss.sTrkName,ss.sTrkId,ss.sTrkDate,ss.sTrkStatus,ss.sTrkAct,ss.sTrkExp,ss.sTrkWay,ss.sTrkLev,ss.sTrkRts,ss.sTrkRmk,ss.sFree,ss.msgBody,TableOutgoing.GetString2(),TableOutgoing.GetMsgType(),"",ss.sBell);
				ss.setUpdateCheck(ss.sJam, time.tanggal, ss.sFiling, ss.sOriginator, ss.sFiledby, "ATS", ss.s7a, strMsgType, ss.msg, ss.sPriority);
				shl.close();
				RefreshTable.refreshTableOutbox(""); } 
			else {
				ss.setInsertATS(ss.sPriority,ss.sDest1,ss.sDest2,ss.sDest3,ss.sDest4,ss.sDest5,ss.sDest6,ss.sDest7,ss.sDest8,ss.sDest9,ss.sDest10,ss.sDest11,ss.sDest12,ss.sDest13,ss.sDest14,ss.sDest15,ss.sDest16,ss.sDest17,ss.sDest18,ss.sDest19,ss.sDest20,ss.sDest21,ss.sOriginator,ss.sOriRef,ft/*ss.sFiling*/,ss.sJam,time.tanggal,ss.sFiledby,ss.sTblName,"",ss.s3a,ss.s3b,ss.s3c,ss.s5a,ss.s5b,ss.s5c,ss.s7a,ss.s7b,ss.s7c,ss.s8a,ss.s8b,ss.s9a,ss.s9b,ss.s9c,ss.t10a,ss.t10b,ss.s13a,time.s13b,ss.s14a,ss.s14b,ss.s14c,ss.s14d,ss.s14e,ss.s15a,ss.s15b,ss.s15c,ss.s16a,time.s16b,ss.s16c,ss.s16d,ss.s17a,ss.s17b,ss.s17c,ss.sReg,ss.sDof,ss.s18Baru,time.s19a,ss.s19b,ss.s19cUHF,ss.s19cVHF,ss.s19cELT,ss.s19dS,ss.s19dP,ss.s19dD,ss.s19dM,ss.s19dJ,ss.s19eJ,ss.s19eL,ss.s19eF,ss.s19eU,ss.s19eV,ss.s19fD,ss.s19fNum,ss.s19fCap,ss.s19fCov,ss.s19fCol,ss.s19g,ss.s19h,ss.s19Rmk,ss.s19i,ss.s20,ss.s21,ss.s22,ss.sSpace,ss.sTrkName,ss.sTrkId,ss.sTrkDate,ss.sTrkStatus,ss.sTrkAct,ss.sTrkExp,ss.sTrkWay,ss.sTrkLev,ss.sTrkRts,ss.sTrkRmk,ss.sFree,ss.msgBody,ss.sBell,ss.sNote);
				if (sDiscard.compareToIgnoreCase("yes")==0) clearBodyTemplate(sID); } }	
		//send: AMO
//		else if (sendType.compareToIgnoreCase("sendamo")==0) {
//			ss.sFree="amo"+ss.sFree;
//			ss.msgBody="amo"+ss.msgBody;
//			ss.sPriority="";ss.sDest1="";ss.sDest2="";ss.sDest3="";ss.sDest4="";ss.sDest5="";ss.sDest6="";ss.sDest7="";ss.sDest8="";ss.sDest9="";ss.sDest10="";ss.sDest11="";ss.sDest12="";ss.sDest13="";ss.sDest14="";ss.sDest15="";ss.sDest16="";ss.sDest17="";ss.sDest18="";ss.sDest19="";ss.sDest20="";ss.sDest21="";ss.sOriginator="";ss.sOriRef="";/*ss.sFiling=""; */ss.sBell="0";
//			
//			ss.setInsertATS(ss.sPriority,ss.sDest1,ss.sDest2,ss.sDest3,ss.sDest4,ss.sDest5,ss.sDest6,ss.sDest7,ss.sDest8,ss.sDest9,ss.sDest10,ss.sDest11,ss.sDest12,ss.sDest13,ss.sDest14,ss.sDest15,ss.sDest16,ss.sDest17,ss.sDest18,ss.sDest19,ss.sDest20,ss.sDest21,ss.sOriginator,ss.sOriRef,""/*ss.sFiling*/,ss.sJam,time.tanggal,ss.sFiledby,ss.sTblName,sStatus,ss.s3a,ss.s3b,ss.s3c,ss.s5a,ss.s5b,ss.s5c,ss.s7a,ss.s7b,ss.s7c,ss.s8a,ss.s8b,ss.s9a,ss.s9b,ss.s9c,ss.s10a,ss.s10b,ss.s13a,time.s13b,ss.s14a,ss.s14b,ss.s14c,ss.s14d,ss.s14e,ss.s15a,ss.s15b,ss.s15c,ss.s16a,time.s16b,ss.s16c,ss.s16d,ss.s17a,ss.s17b,ss.s17c,ss.sReg,ss.sDof,ss.s18Baru,time.s19a,ss.s19b,ss.s19cUHF,ss.s19cVHF,ss.s19cELT,ss.s19dS,ss.s19dP,ss.s19dD,ss.s19dM,ss.s19dJ,ss.s19eJ,ss.s19eL,ss.s19eF,ss.s19eU,ss.s19eV,ss.s19fD,ss.s19fNum,ss.s19fCap,ss.s19fCov,ss.s19fCol,ss.s19g,ss.s19h,ss.s19Rmk,ss.s19i,ss.s20,ss.s21,ss.s22,ss.sSpace,ss.sTrkName,ss.sTrkId,ss.sTrkDate,ss.sTrkStatus,ss.sTrkAct,ss.sTrkExp,ss.sTrkWay,ss.sTrkLev,ss.sTrkRts,ss.sTrkRmk,ss.sFree,ss.msgBody,ss.sBell,ss.sNote);
//			ss.setInsertCheck(jdbc.getKey(), ss.sTblName, ss.sFiling, ss.sJam, time.tanggal, ss.s7a, strMsgType, "", ss.sOriginator, ss.msg, "", ss.sFiledby, ss.sPriority);
//			if (sDiscard.compareToIgnoreCase("yes")==0) clearBodyTemplate(sID); }
    }
    
    void sAMO() {
    	ATSForms.getAMOValue(); time.tgl(); 
    	ss.sFiling=ss.sJam=time.filing_time;
    	ss.sAFTN();
    	ss.data(ATSForms.getBodyAMO());
    	if (ATSForms.getBodyAMO().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nAMO, getText(),em.req(ATSForms.getBodyAMO().length())); }
    	else inputDB(Shells.sh_nAMO, "AMO");
    }
    
    void sFREE() {
    	ATSForms.getFREEValue(); time.tgl(); 
    	ss.sFiling=ss.sJam=time.filing_time;
    	ss.sAFTN();
    	ss.data(ATSForms.getBodyFREE());
    	String len = rff.ReadInputFile1(MainForm.sFolder+"free.txt");
    	if (ATSForms.getBodyFREE().length() > Integer.parseInt(len)) { 
//    		DialogFactory.openWarningDialog(Shells.sh_nFREE, getText(),em.req(ATSForms.getBodyFREE().length())); }
    		DialogFactory.openWarningDialog(Shells.sh_nFREE, getText(), "Your data is "+ATSForms.getBodyFREE().length()+" characters. Transmitting data maximum is "+len+" characters !"); }
    	else inputDB(Shells.sh_nFREE, "FREE");
    }
    
    void sAFTN() {
		ATSForms.getAFTNValue(); time.tgl(); 
		//---------------------------------------- [header mandatory] ----------------------------------------
		if (HeaderComposite.getPriorityAFTN().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nAFTN, getText(), em.reqPriority); HeaderComposite.tPriorityAFTNFocus(); }
		else if (HeaderComposite.getDest1AFTN().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nAFTN, getText(), em.reqAddress); HeaderComposite.tDest1AFTNFocus(); }
		else if ((!HeaderComposite.getDest1AFTN().equals("")) && (HeaderComposite.getDest1AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest1AFTNFocus(); }
		else if ((!HeaderComposite.getDest2AFTN().equals("")) && (HeaderComposite.getDest2AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest2AFTNFocus(); }
		else if ((!HeaderComposite.getDest3AFTN().equals("")) && (HeaderComposite.getDest3AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest3AFTNFocus(); }
		else if ((!HeaderComposite.getDest4AFTN().equals("")) && (HeaderComposite.getDest4AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest4AFTNFocus(); }
		else if ((!HeaderComposite.getDest5AFTN().equals("")) && (HeaderComposite.getDest5AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest5AFTNFocus(); }
		else if ((!HeaderComposite.getDest6AFTN().equals("")) && (HeaderComposite.getDest6AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest6AFTNFocus(); }
		else if ((!HeaderComposite.getDest7AFTN().equals("")) && (HeaderComposite.getDest7AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest7AFTNFocus(); }
		else if ((!HeaderComposite.getDest8AFTN().equals("")) && (HeaderComposite.getDest8AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest8AFTNFocus(); }
		else if ((!HeaderComposite.getDest9AFTN().equals("")) && (HeaderComposite.getDest9AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest9AFTNFocus(); }
		else if ((!HeaderComposite.getDest10AFTN().equals("")) && (HeaderComposite.getDest10AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest10AFTNFocus(); }
		else if ((!HeaderComposite.getDest11AFTN().equals("")) && (HeaderComposite.getDest11AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest11AFTNFocus(); }
		else if ((!HeaderComposite.getDest12AFTN().equals("")) && (HeaderComposite.getDest12AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest12AFTNFocus(); }
		else if ((!HeaderComposite.getDest13AFTN().equals("")) && (HeaderComposite.getDest13AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest13AFTNFocus(); }
		else if ((!HeaderComposite.getDest14AFTN().equals("")) && (HeaderComposite.getDest14AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest14AFTNFocus(); }
		else if ((!HeaderComposite.getDest15AFTN().equals("")) && (HeaderComposite.getDest15AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest15AFTNFocus(); }
		else if ((!HeaderComposite.getDest16AFTN().equals("")) && (HeaderComposite.getDest16AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest16AFTNFocus(); }
		else if ((!HeaderComposite.getDest17AFTN().equals("")) && (HeaderComposite.getDest17AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest17AFTNFocus(); }
		else if ((!HeaderComposite.getDest18AFTN().equals("")) && (HeaderComposite.getDest18AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest18AFTNFocus(); }
		else if ((!HeaderComposite.getDest19AFTN().equals("")) && (HeaderComposite.getDest19AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest19AFTNFocus(); }
		else if ((!HeaderComposite.getDest20AFTN().equals("")) && (HeaderComposite.getDest20AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest20AFTNFocus(); }
		else if ((!HeaderComposite.getDest21AFTN().equals("")) && (HeaderComposite.getDest21AFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoAddress); HeaderComposite.tDest21AFTNFocus(); }
		else if (HeaderComposite.getOriginatorAFTN().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nAFTN, getText(), em.reqOriginator); HeaderComposite.tOriginatorAFTNFocus(); }
 		else if ((!HeaderComposite.getOriginatorAFTN().equals("")) && (HeaderComposite.getOriginatorAFTN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nAFTN, getText(), em.infoOriginator); HeaderComposite.tOriginatorAFTNFocus(); }
 		else if (ATSForms.getBodyAFTN().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nAFTN, getText(),em.req(ATSForms.getBodyAFTN().length())); }
 		else {
 			// mendapatkan isi masing2 field
 			ss.hAFTN(); ss.sAFTN();
 			ss.data(ATSForms.getBodyAFTN());
 			validasiDuplicateAddress(Shells.sh_nAFTN,"AFTN");
    	}
    }
    
    void sALR() {
    	ATSForms.getALRValue(); time.tgl();
    	ss.t10b=""; ss.sdbguv=""; ss.kriteria=""; ss.sprotek10a=""; ss.sprotekpbn=""; ss.s18Baru="";
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
    	ss.cek9b(t9b, data);
    	
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
    	if (HeaderComposite.getPriorityALR().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.reqPriority); HeaderComposite.tPriorityALRFocus(); }
		else if (HeaderComposite.getDest1ALR().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.reqAddress); HeaderComposite.tDest1ALRFocus(); }
		else if ((!HeaderComposite.getDest1ALR().equals("")) && (HeaderComposite.getDest1ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest1ALRFocus(); }
		else if ((!HeaderComposite.getDest2ALR().equals("")) && (HeaderComposite.getDest2ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest2ALRFocus(); }
		else if ((!HeaderComposite.getDest3ALR().equals("")) && (HeaderComposite.getDest3ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest3ALRFocus(); }
		else if ((!HeaderComposite.getDest4ALR().equals("")) && (HeaderComposite.getDest4ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest4ALRFocus(); }
		else if ((!HeaderComposite.getDest5ALR().equals("")) && (HeaderComposite.getDest5ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest5ALRFocus(); }
		else if ((!HeaderComposite.getDest6ALR().equals("")) && (HeaderComposite.getDest6ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest6ALRFocus(); }
		else if ((!HeaderComposite.getDest7ALR().equals("")) && (HeaderComposite.getDest7ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest7ALRFocus(); }
		else if ((!HeaderComposite.getDest8ALR().equals("")) && (HeaderComposite.getDest8ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest8ALRFocus(); }
		else if ((!HeaderComposite.getDest9ALR().equals("")) && (HeaderComposite.getDest9ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest9ALRFocus(); }
		else if ((!HeaderComposite.getDest10ALR().equals("")) && (HeaderComposite.getDest10ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest10ALRFocus(); }
		else if ((!HeaderComposite.getDest11ALR().equals("")) && (HeaderComposite.getDest11ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest11ALRFocus(); }
		else if ((!HeaderComposite.getDest12ALR().equals("")) && (HeaderComposite.getDest12ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest12ALRFocus(); }
		else if ((!HeaderComposite.getDest13ALR().equals("")) && (HeaderComposite.getDest13ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest13ALRFocus(); }
		else if ((!HeaderComposite.getDest14ALR().equals("")) && (HeaderComposite.getDest14ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest14ALRFocus(); }
		else if ((!HeaderComposite.getDest15ALR().equals("")) && (HeaderComposite.getDest15ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest15ALRFocus(); }
		else if ((!HeaderComposite.getDest16ALR().equals("")) && (HeaderComposite.getDest16ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest16ALRFocus(); }
		else if ((!HeaderComposite.getDest17ALR().equals("")) && (HeaderComposite.getDest17ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest17ALRFocus(); }
		else if ((!HeaderComposite.getDest18ALR().equals("")) && (HeaderComposite.getDest18ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest18ALRFocus(); }
		else if ((!HeaderComposite.getDest19ALR().equals("")) && (HeaderComposite.getDest19ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest19ALRFocus(); }
		else if ((!HeaderComposite.getDest20ALR().equals("")) && (HeaderComposite.getDest20ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest20ALRFocus(); }
		else if ((!HeaderComposite.getDest21ALR().equals("")) && (HeaderComposite.getDest21ALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoAddress); HeaderComposite.tDest21ALRFocus(); }
		else if (HeaderComposite.getOriginatorALR().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.reqOriginator); HeaderComposite.tOriginatorALRFocus(); }
 		else if ((!HeaderComposite.getOriginatorALR().equals("")) && (HeaderComposite.getOriginatorALR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoOriginator); HeaderComposite.tOriginatorALRFocus(); }
    	//---------------------------------------- [body mandatory] ----------------------------------------
 		else if (ATSForms.get3a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req3a); ATSForms.focus3aALR(); }
 		//5
 		else if (ATSForms.get5a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req5a); ATSForms.focus5aALR(); } 
		else if (ATSForms.get5b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req5b); ATSForms.focus5bALR(); }
		else if (ATSForms.get5b().length() < 8) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.info5b); ATSForms.focus5bALR(); }
		else if (ATSForms.get5c().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req5c); ATSForms.focus5cALR(); }
        //7
 		else if (ATSForms.get7a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req7a); ATSForms.focus7aALR(); }
 		else if (ATSForms.get7b().equals("A") && (ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req7c); ATSForms.focus7cALR(); }
        else if (!ATSForms.get7b().equals("A") && (!ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req7b); }
        else if (!ATSForms.get7c().equals("") && (ATSForms.get7c().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.info7c); ATSForms.focus7cALR(); }
    	//8
 		else if (ATSForms.get8a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req8a); ATSForms.focus8aALR(); }
        else if (ATSForms.get8b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req8b); ATSForms.focus8bALR(); }
        //9
        else if (t9b.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req9b); ATSForms.focus9bALR(); }
        else if (t9b.length() < 2) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.info9b); ATSForms.focus9bALR(); }
        else if (ATSForms.get9c().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req9c); ATSForms.focus9cALR(); }
        else if (!ss.wtc.equals("") && ATSForms.get9c().compareTo(ss.wtc) != 0) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(),em.wtc(ss.wtc, t9b)); ATSForms.focus9cALR(); }
    	//10
        else if (ss.t10a.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req10); ATSForms.focus10aALR(); }
		else if (!ss.t10a.equals("") && (ss.sprotek10a.contains("ngaco"))) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoInvalid10aFix); ATSForms.focus10aALR(); }		
		else if (ss.t10b.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req10); ATSForms.focus10bBaruALR(); }
		else if (!ss.t10b.equals("") && (ss.sdbguv.contains("tdksesuai"))) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoInvalid10b+"\nInsert B1 or B2 or U1 or U2 or V1 or V2 or D1 or G1"); ATSForms.focus10bBaruALR(); }		
		else if ((ss.t10b.contains("B1") || ss.t10b.contains("U1") || ss.t10b.contains("V1")) && (ss.t10b.contains("B2") || ss.t10b.contains("U2") || ss.t10b.contains("V2"))) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoInvalid10b); ATSForms.focus10bBaruALR(); }
		else if (ss.t10a.contains("N") && ss.t10a.contains("S")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoInvalid10a); ATSForms.focus10aALR(); }
		else if (ss.t10b.contains("N") && (ss.t10b.contains("A") || ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X") || ss.t10b.contains("B1") || ss.t10b.contains("B2") || ss.t10b.contains("U1") || ss.t10b.contains("U2") || ss.t10b.contains("V1") || ss.t10b.contains("V2") || ss.t10b.contains("D1") || ss.t10b.contains("G1"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(),"Invalid EQUIPMENT AND CAPABILITIES value.\n\nSSR Modes A and C and SSR Mode S are not allowed,\nif 'N' has inserted into field EQUIPMENT AND CAPABILITIES and vice versa.\n\nSee the instruction below the EQUIPMENT AND CAPABILITIES text !"); ATSForms.focus10bBaruALR(); }
		else if (ss.t10a.contains("W") && ATSForms.get18tSts().contains("NONRVSM")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoInvalid10aSTS); ATSForms.focus18tStsALR(); }
    	//13
		else if (ATSForms.get13a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req13a); ATSForms.focus13aALR(); }
		else if (ATSForms.get13a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.info13a); ATSForms.focus13aALR(); }
        else if (ATSForms.get13b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req13b); ATSForms.focus13bALR(); }
        else if (ATSForms.get13b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.info13b); ATSForms.focus13bALR(); }
        else if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4 && (time.iHh13>23) || (time.iMM13>59)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoInvalid13b); ATSForms.focus13bALR(); }
		//15
        else if (ATSForms.get15a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req15a); ATSForms.focus15aALR(); }
		else if ((!t15a.startsWith("K")) && (!t15a.startsWith("N")) && (!t15a.startsWith("M"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoInvalid15a); ATSForms.focus15aALR(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (t15a.length()<5)) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info15aKN); ATSForms.focus15aALR(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (!time.t15a2.startsWith("0")) && (!time.t15a2.startsWith("1")) && (!time.t15a2.startsWith("2")) && (!time.t15a2.startsWith("3")) && (!time.t15a2.startsWith("4")) && (!time.t15a2.startsWith("5")) && (!time.t15a2.startsWith("6")) && (!time.t15a2.startsWith("7")) && (!time.t15a2.startsWith("8")) && (!time.t15a2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info15aKN); ATSForms.focus15aALR(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (!time.t15a3.startsWith("0")) && (!time.t15a3.startsWith("1")) && (!time.t15a3.startsWith("2")) && (!time.t15a3.startsWith("3")) && (!time.t15a3.startsWith("4")) && (!time.t15a3.startsWith("5")) && (!time.t15a3.startsWith("6")) && (!time.t15a3.startsWith("7")) && (!time.t15a3.startsWith("8")) && (!time.t15a3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info15aKN); ATSForms.focus15aALR(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (!time.t15a4.startsWith("0")) && (!time.t15a4.startsWith("1")) && (!time.t15a4.startsWith("2")) && (!time.t15a4.startsWith("3")) && (!time.t15a4.startsWith("4")) && (!time.t15a4.startsWith("5")) && (!time.t15a4.startsWith("6")) && (!time.t15a4.startsWith("7")) && (!time.t15a4.startsWith("8")) && (!time.t15a4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info15aKN); ATSForms.focus15aALR(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (!time.t15a5.startsWith("0")) && (!time.t15a5.startsWith("1")) && (!time.t15a5.startsWith("2")) && (!time.t15a5.startsWith("3")) && (!time.t15a5.startsWith("4")) && (!time.t15a5.startsWith("5")) && (!time.t15a5.startsWith("6")) && (!time.t15a5.startsWith("7")) && (!time.t15a5.startsWith("8")) && (!time.t15a5.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info15aKN); ATSForms.focus15aALR(); }
		else if ((t15a.startsWith("M")) && ((t15a.length()>4) || (t15a.length()<4))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info15aM); ATSForms.focus15aALR(); }
		else if (((t15a.startsWith("M"))) && (!time.t15a2.startsWith("0")) && (!time.t15a2.startsWith("1")) && (!time.t15a2.startsWith("2")) && (!time.t15a2.startsWith("3")) && (!time.t15a2.startsWith("4")) && (!time.t15a2.startsWith("5")) && (!time.t15a2.startsWith("6")) && (!time.t15a2.startsWith("7")) && (!time.t15a2.startsWith("8")) && (!time.t15a2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info15aM); ATSForms.focus15aALR(); }
		else if (((t15a.startsWith("M"))) && (!time.t15a3.startsWith("0")) && (!time.t15a3.startsWith("1")) && (!time.t15a3.startsWith("2")) && (!time.t15a3.startsWith("3")) && (!time.t15a3.startsWith("4")) && (!time.t15a3.startsWith("5")) && (!time.t15a3.startsWith("6")) && (!time.t15a3.startsWith("7")) && (!time.t15a3.startsWith("8")) && (!time.t15a3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info15aM); ATSForms.focus15aALR(); }
		else if (((t15a.startsWith("M"))) && (!time.t15a4.startsWith("0")) && (!time.t15a4.startsWith("1")) && (!time.t15a4.startsWith("2")) && (!time.t15a4.startsWith("3")) && (!time.t15a4.startsWith("4")) && (!time.t15a4.startsWith("5")) && (!time.t15a4.startsWith("6")) && (!time.t15a4.startsWith("7")) && (!time.t15a4.startsWith("8")) && (!time.t15a4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info15aM); ATSForms.focus15aALR(); }
		else if (ATSForms.get15b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req15b); ATSForms.focus15bALR(); }
		else if ((!t15b.startsWith("F")) && (!t15b.startsWith("S")) && (!t15b.startsWith("A")) && (!t15b.startsWith("M")) && (!t15b.equals("VFR"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoInvalid15b); ATSForms.focus15bALR(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (t15b.length()<5)) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info14cSM); ATSForms.focus15bALR(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (!time.t15b2.startsWith("0")) && (!time.t15b2.startsWith("1")) && (!time.t15b2.startsWith("2")) && (!time.t15b2.startsWith("3")) && (!time.t15b2.startsWith("4")) && (!time.t15b2.startsWith("5")) && (!time.t15b2.startsWith("6")) && (!time.t15b2.startsWith("7")) && (!time.t15b2.startsWith("8")) && (!time.t15b2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info14cSM); ATSForms.focus15bALR(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (!time.t15b3.startsWith("0")) && (!time.t15b3.startsWith("1")) && (!time.t15b3.startsWith("2")) && (!time.t15b3.startsWith("3")) && (!time.t15b3.startsWith("4")) && (!time.t15b3.startsWith("5")) && (!time.t15b3.startsWith("6")) && (!time.t15b3.startsWith("7")) && (!time.t15b3.startsWith("8")) && (!time.t15b3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info14cSM); ATSForms.focus15bALR(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (!time.t15b4.startsWith("0")) && (!time.t15b4.startsWith("1")) && (!time.t15b4.startsWith("2")) && (!time.t15b4.startsWith("3")) && (!time.t15b4.startsWith("4")) && (!time.t15b4.startsWith("5")) && (!time.t15b4.startsWith("6")) && (!time.t15b4.startsWith("7")) && (!time.t15b4.startsWith("8")) && (!time.t15b4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info14cSM); ATSForms.focus15bALR(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (!time.t15b5.startsWith("0")) && (!time.t15b5.startsWith("1")) && (!time.t15b5.startsWith("2")) && (!time.t15b5.startsWith("3")) && (!time.t15b5.startsWith("4")) && (!time.t15b5.startsWith("5")) && (!time.t15b5.startsWith("6")) && (!time.t15b5.startsWith("7")) && (!time.t15b5.startsWith("8")) && (!time.t15b5.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info14cSM); ATSForms.focus15bALR(); }
		else if (((t15b.startsWith("F")) || (t15b.startsWith("A"))) && ((t15b.length()>4) || (t15b.length()<4))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info14cFA); ATSForms.focus15bALR(); }
		else if (((t15b.startsWith("F")) || (t15b.startsWith("A"))) && (!time.t15b2.startsWith("0")) && (!time.t15b2.startsWith("1")) && (!time.t15b2.startsWith("2")) && (!time.t15b2.startsWith("3")) && (!time.t15b2.startsWith("4")) && (!time.t15b2.startsWith("5")) && (!time.t15b2.startsWith("6")) && (!time.t15b2.startsWith("7")) && (!time.t15b2.startsWith("8")) && (!time.t15b2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info14cFA); ATSForms.focus15bALR(); }
		else if (((t15b.startsWith("F")) || (t15b.startsWith("A"))) && (!time.t15b3.startsWith("0")) && (!time.t15b3.startsWith("1")) && (!time.t15b3.startsWith("2")) && (!time.t15b3.startsWith("3")) && (!time.t15b3.startsWith("4")) && (!time.t15b3.startsWith("5")) && (!time.t15b3.startsWith("6")) && (!time.t15b3.startsWith("7")) && (!time.t15b3.startsWith("8")) && (!time.t15b3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info14cFA); ATSForms.focus15bALR(); }
		else if (((t15b.startsWith("F")) || (t15b.startsWith("A"))) && (!time.t15b4.startsWith("0")) && (!time.t15b4.startsWith("1")) && (!time.t15b4.startsWith("2")) && (!time.t15b4.startsWith("3")) && (!time.t15b4.startsWith("4")) && (!time.t15b4.startsWith("5")) && (!time.t15b4.startsWith("6")) && (!time.t15b4.startsWith("7")) && (!time.t15b4.startsWith("8")) && (!time.t15b4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info14cFA); ATSForms.focus15bALR(); }
		else if (ATSForms.get15c().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req15c); ATSForms.focus15cALR(); }
    	//16
		else if (ATSForms.get16a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req16a); ATSForms.focus16aALR(); }
		else if (ATSForms.get16a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.info16a); ATSForms.focus16aALR(); }
		else if (ATSForms.get16b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req16b); ATSForms.focus16bALR(); }
		else if (ATSForms.get16b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.info16b); ATSForms.focus16bALR(); }
		else if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4 && (time.iHh16>23) || (time.iMM16>59)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoInvalid16b); ATSForms.focus16bALR(); }
		else if (ATSForms.get16c().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req16c); ATSForms.focus16cALR(); }
		else if (ATSForms.get16c().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.info16c); ATSForms.focus16cALR(); }
		else if (!ATSForms.get16c2nd().equals("") && ATSForms.get16c2nd().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.info16d); ATSForms.focus16dALR(); }
    	//tambahan
		else if (!ATSForms.get16c2nd().isEmpty()&&ATSForms.get16c2nd().length()==4 && ATSForms.get16c().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info16cd); ATSForms.focus16cALR(); }
    	//18 indicator TYP/
        else if (ATSForms.get9b().equals("ZZZZ") && ATSForms.get18tTyp().equals("")) {  DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoTyp9b); ATSForms.focus18tTypALR(); }
        else if (!ATSForms.get9b().contains("ZZZZ") && !ATSForms.get18tTyp().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info9bTyp); ATSForms.focus9bALR(); }
    	//indicator COM/, DAT/, NAV/, SUR/ [link ke Item 10]
        else if (ss.t10a.contains("Z") && ss.s10aZ.contains("ngaco"))  { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoCom10); ATSForms.focus18tComALR(); }
        else if (ss.kriteria.contains("ngaco") && ATSForms.get18tSur().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoSur10); ATSForms.focus18tSurALR(); }
    	//indicator DEP/
        else if ((ATSForms.get13a().equals("ZZZZ") || ATSForms.get13a().equals("AFIL")) && ATSForms.get18tDep().equals("")) {  DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoDep13); ATSForms.focus18tDepALR(); }
        else if (!ATSForms.get13a().contains("ZZZZ") && !ATSForms.get13a().contains("AFIL") && !ATSForms.get18tDep().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info13Dep); ATSForms.focus13aALR(); }
    	//indicator DEST/
        else if (ATSForms.get16a().equals("ZZZZ") && ATSForms.get18tDest().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoDest16); ATSForms.focus18tDestALR(); }
        else if (!ATSForms.get16a().contains("ZZZZ") && !ATSForms.get18tDest().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info16Dest); ATSForms.focus16aALR(); }
    	//indicator ALTN/
        else if (ATSForms.get16c().equals("ZZZZ") && ATSForms.get18tAltn().equals("")) {  DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoAltn16); ATSForms.focus18tAltnALR(); }
        else if (!ATSForms.get16c().contains("ZZZZ") && !ATSForms.get18tAltn().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info16Altn); ATSForms.focus16cALR(); }
    	//indicator REG/, DOF/
        else if (!ATSForms.get18tReg().isEmpty() && ATSForms.get18tReg().equals(ATSForms.get7a())) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoInvalidReg); ATSForms.focus18tRegALR(); }
        else if (!ATSForms.get18tReg().isEmpty() && !ATSForms.get18tOpr().equals("") && ATSForms.get18tReg().equals(ATSForms.get18tOpr())) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoInvalidOpr); ATSForms.focus18tOprALR(); }
        else if (!ATSForms.get18tReg().isEmpty() && (ATSForms.get18tReg().length() > 7 || ATSForms.get18tReg().contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoInvalidReg); ATSForms.focus18tRegALR(); }
		else if (ATSForms.get18tDof().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.reqDof); ATSForms.focus18tDofALR(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()<6) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoInvalidDof); ATSForms.focus18tDofALR(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && (time.sYy.equals("00") || time.sHh.equals("00") || time.sMm.equals("00") || time.iHh>12 || time.iMM>31)) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoInvalid18Dof); ATSForms.focus18tDofALR(); }
		else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && Integer.parseInt(datedof) < Integer.parseInt(time.datenow)) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.infoInvalid18DofVal); ATSForms.focus18tDofALR(); }
		//indicator STS/, PER/, CODE/, SEL/, PBN/
        else if (!ATSForms.get18tSts().equals("") && ss.criteria_sts.contains("ngaco")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoInvalidSts); ATSForms.focus18tStsALR(); }
        else if (!ATSForms.get18tPer().equals("") && ATSForms.get18tPer().length() > 1) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoInvalidPer); ATSForms.focus18tPerALR(); }
        else if (!ATSForms.get18tCode().equals("") && (ATSForms.get18tCode().length() > 6 || ATSForms.get18tCode().contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoInvalidCode); ATSForms.focus18tCodeALR(); }
        else if (!ATSForms.get18tSel().equals("") && (ATSForms.get18tSel().length() > 4 || ATSForms.get18tSel().contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoInvalidSel); ATSForms.focus18tSelALR(); }
    	//PBN
        else if (!ss.tpbn.equals("") && (ss.tpbn.length() > 16 || ss.tpbn.contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoInvalidPbn); ATSForms.focus18tPbnALR(); }
        else if (!ss.tpbn.equals("") && ss.sprotekpbn.contains("ngaco")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoInvalidPbnFix); ATSForms.focus18tPbnALR(); }
		else if (ss.t10a.contains("R") && ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoPbn10); ATSForms.focus18tPbnALR(); }
		else if (!ss.t10a.contains("R") && !ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info10Pbn); ATSForms.focus10aALR(); }
    	//G
		else if (!ss.t10a.contains("G") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info10G); ATSForms.focus10aALR(); }
		else if (!ss.t10a.contains("G") && !ss.krit2.equals("") && (ss.krit2.contains("B2") || ss.krit2.contains("C2") || ss.krit2.contains("D2") || ss.krit2.contains("O2"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info10G); ATSForms.focus10aALR(); }
		//D
		else if (!ss.t10a.contains("D") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info10D); ATSForms.focus10aALR(); }
		else if (!ss.t10a.contains("D") && !ss.krit3.equals("") && (ss.krit3.contains("B3") || ss.krit3.contains("C3") || ss.krit3.contains("D3") || ss.krit3.contains("O3"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info10D); ATSForms.focus10aALR(); }
    	//I
		else if (!ss.t10a.contains("I") && !ss.krit5.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info10I); ATSForms.focus10aALR(); }
		else if (!ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1"))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info10I); ATSForms.focus10aALR(); }
		//DI
		else if (!ss.t10a.contains("D") && !ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info10DI); ATSForms.focus10aALR(); }
		else if (!ss.t10a.contains("D") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info10DI); ATSForms.focus10aALR(); }
		else if (!ss.t10a.contains("I") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info10DI); ATSForms.focus10aALR(); }
		else if (!ss.t10a.contains("I") && (ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info10DI); ATSForms.focus10aALR(); }
    	//OD / SD
		else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit1.contains("B1")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info10OD); ATSForms.focus10aALR(); }
		else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info10OD); ATSForms.focus10aALR(); }
		else if (!ss.t10a.contains("D") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info10OD); ATSForms.focus10aALR(); }
		else if (!ss.t10a.contains("D") && ss.krit1.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.info10OD); ATSForms.focus10aALR(); }
    	//19
		else if (!ATSForms.get19a().equals("") && ATSForms.get19a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.info19a); ATSForms.focus19aALR(); } 
		else if (!ATSForms.get19a().isEmpty() && ATSForms.get19a().length()==4 && ((time.iHh19>23) || (time.iMM19>59))) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.infoInvalid19a); ATSForms.focus19aALR(); }
		else if (!ATSForms.get19fNum().equals("") && ATSForms.get19fNum().length() < 2) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.info19fNum); ATSForms.focus19NumALR(); } 
		else if (!ATSForms.get19fCap().equals("") && ATSForms.get19fCap().length() < 3) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.info19fCap); ATSForms.focus19CapALR(); } 
		else if ((!ATSForms.get19fNum().equals(""))&&(ATSForms.get19fCap().equals(""))) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.info19f); ATSForms.focus19CapALR(); }
		else if ((!ATSForms.get19fCap().equals(""))&&(ATSForms.get19fNum().equals(""))) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.info19f); ATSForms.focus19NumALR(); }
		else if ((!ATSForms.get19fCov().equals("")) && ((ATSForms.get19fNum().equals("")) || (ATSForms.get19fCap().equals("")))) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.info19f); ATSForms.focus19NumALR(); }
		else if ((!ATSForms.get19fCol().equals("")) && ((ATSForms.get19fNum().equals("")) || (ATSForms.get19fCap().equals("")))) { DialogFactory.openInfoDialog(Shells.sh_nALR, getText(), em.info19f); ATSForms.focus19NumALR(); }
		//20
		else if (ATSForms.get20().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(), em.req20); ATSForms.focus20(); }
    	//length
		else if (ATSForms.getBodyALR().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nALR, getText(),em.req(ATSForms.getBodyALR().length())); }
 		else {
 			// mendapatkan isi masing2 field
			ss.hALR();ss.s3();ss.s5();ss.s7();ss.s8();ss.s9();ss.s13();ss.s15();ss.s16();ss.s19();ss.s20();
 			ss.data(ATSForms.getBodyALR());
 			if (ss.toa.compareTo("") == 0) { //NOT REGISTERED TYPE OF AIRCRAFT
				DialogFactory.openYesNoDialog(Shells.sh_nALR, "Type of Aircraft","Type of aircraft is not registered. Send it anyway ?");
	
				boolean tentu = DialogFactory.getPenentuan(); 
				if (tentu == true) {
					insalr(strDof);
				} //end of if == tentu
				else { System.out.println(">> Sending has been canceled."); }
			} //end of toa=""
			else { //REGISTERED TYPE OF AIRCRAFT
				insalr(strDof);
			}
		}
    }
    
    void insalr(String strDof) {
    	if ((ss.sReg.compareTo("REG/0")==0 || ss.sReg.compareTo("")==0) &&  (ss.sDof.compareTo("DOF/0")==0 || ss.sDof.compareTo("")==0) && ss.s18Baru.compareTo("")==0) { ss.s18Baru="0"; }
    	validasiDuplicateAddress(Shells.sh_nALR,"ALR");
		// tutup koneksi GetRoute
		ss.connClose("ALR");
    }
    
    void sRCF() {
		ATSForms.getRCFValue(); time.tgl();
    	//---------------------------------------- [header mandatory] ----------------------------------------
    	if (HeaderComposite.getPriorityRCF().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRCF, getText(), em.reqPriority); HeaderComposite.tPriorityRCFFocus(); }
		else if (HeaderComposite.getDest1RCF().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRCF, getText(), em.reqAddress); HeaderComposite.tDest1RCFFocus(); }
		else if ((!HeaderComposite.getDest1RCF().equals("")) && (HeaderComposite.getDest1RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest1RCFFocus(); }
		else if ((!HeaderComposite.getDest2RCF().equals("")) && (HeaderComposite.getDest2RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest2RCFFocus(); }
		else if ((!HeaderComposite.getDest3RCF().equals("")) && (HeaderComposite.getDest3RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest3RCFFocus(); }
		else if ((!HeaderComposite.getDest4RCF().equals("")) && (HeaderComposite.getDest4RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest4RCFFocus(); }
		else if ((!HeaderComposite.getDest5RCF().equals("")) && (HeaderComposite.getDest5RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest5RCFFocus(); }
		else if ((!HeaderComposite.getDest6RCF().equals("")) && (HeaderComposite.getDest6RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest6RCFFocus(); }
		else if ((!HeaderComposite.getDest7RCF().equals("")) && (HeaderComposite.getDest7RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest7RCFFocus(); }
		else if ((!HeaderComposite.getDest8RCF().equals("")) && (HeaderComposite.getDest8RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest8RCFFocus(); }
		else if ((!HeaderComposite.getDest9RCF().equals("")) && (HeaderComposite.getDest9RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest9RCFFocus(); }
		else if ((!HeaderComposite.getDest10RCF().equals("")) && (HeaderComposite.getDest10RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest10RCFFocus(); }
		else if ((!HeaderComposite.getDest11RCF().equals("")) && (HeaderComposite.getDest11RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest11RCFFocus(); }
		else if ((!HeaderComposite.getDest12RCF().equals("")) && (HeaderComposite.getDest12RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest12RCFFocus(); }
		else if ((!HeaderComposite.getDest13RCF().equals("")) && (HeaderComposite.getDest13RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest13RCFFocus(); }
		else if ((!HeaderComposite.getDest14RCF().equals("")) && (HeaderComposite.getDest14RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest14RCFFocus(); }
		else if ((!HeaderComposite.getDest15RCF().equals("")) && (HeaderComposite.getDest15RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest15RCFFocus(); }
		else if ((!HeaderComposite.getDest16RCF().equals("")) && (HeaderComposite.getDest16RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest16RCFFocus(); }
		else if ((!HeaderComposite.getDest17RCF().equals("")) && (HeaderComposite.getDest17RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest17RCFFocus(); }
		else if ((!HeaderComposite.getDest18RCF().equals("")) && (HeaderComposite.getDest18RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest18RCFFocus(); }
		else if ((!HeaderComposite.getDest19RCF().equals("")) && (HeaderComposite.getDest19RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest19RCFFocus(); }
		else if ((!HeaderComposite.getDest20RCF().equals("")) && (HeaderComposite.getDest20RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest20RCFFocus(); }
		else if ((!HeaderComposite.getDest21RCF().equals("")) && (HeaderComposite.getDest21RCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoAddress); HeaderComposite.tDest21RCFFocus(); }
		else if (HeaderComposite.getOriginatorRCF().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRCF, getText(), em.reqOriginator); HeaderComposite.tOriginatorRCFFocus(); }
 		else if ((!HeaderComposite.getOriginatorRCF().equals("")) && (HeaderComposite.getOriginatorRCF().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.infoOriginator); HeaderComposite.tOriginatorRCFFocus(); }
    	//---------------------------------------- [body mandatory] ----------------------------------------
 		else if (ATSForms.get3a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRCF, getText(), em.req3a); ATSForms.focus3aRCF(); }
        else if (ATSForms.get7a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRCF, getText(), em.req7a); ATSForms.focus7aRCF(); }
        else if (ATSForms.get7b().equals("A") && (ATSForms.get7c().equals(""))) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.req7c); ATSForms.focus7cRCF(); }
        else if (!ATSForms.get7b().equals("A") && (!ATSForms.get7c().equals(""))) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.req7b); }
        else if (!ATSForms.get7c().equals("") && (ATSForms.get7c().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nRCF, getText(), em.info7c); ATSForms.focus7cRCF(); }
		else if (ATSForms.get21().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRCF, getText(), em.req21); ATSForms.focus21(); }
		else if (ATSForms.getBodyRCF().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nRCF, getText(),em.req(ATSForms.getBodyRCF().length())); }
 		else {
 			// mendapatkan isi masing2 field
			ss.hRCF(); ss.s3(); ss.s7(); ss.s21();
 			ss.data(ATSForms.getBodyRCF());
 			validasiDuplicateAddress(Shells.sh_nRCF,"RCF");
    	}
 	}
    
    void sFPL() {
    	ATSForms.getFPLValue(); time.tgl();
    	ss.t10b=""; ss.sdbguv=""; ss.kriteria=""; ss.sprotek10a=""; ss.sprotekpbn=""; ss.s18Baru="";
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
    	ss.cek9b(t9b, data);
    	
    	String t7a = ATSForms.get7a();
    	ss.cek7a("DEP",t7a);

    	if (!ss.s_Reg.isEmpty()) ss.s_Reg = ss.s_Reg.replace(" REG/", "");
    	if (!ss.s_Dof.isEmpty()) ss.s_Dof = ss.s_Dof.replace(" DOF/", "");
    	//----------------------------------------------------------------
    	if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4) { time.t13b(ATSForms.get13b()); }
    	if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4) { time.t16b(ATSForms.get16b()); }
    	if (!ATSForms.get19a().isEmpty() && ATSForms.get19a().length()==4) { time.t19a(ATSForms.get19a()); }
		String strDof="";
		//untuk mendapatkan isi tipe18 - khusus FPL
		if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6) ss.tipe18baru();
		else if (ATSForms.get18tDof().isEmpty() && 
				(!ATSForms.get18tSts().isEmpty() || !ATSForms.get18tPbn().isEmpty() || !ATSForms.get18tNav().isEmpty() || 
						!ATSForms.get18tCom().isEmpty() || !ATSForms.get18tDat().isEmpty() || !ATSForms.get18tSur().isEmpty() ||
						!ATSForms.get18tDep().isEmpty() || !ATSForms.get18tDest().isEmpty() || 
						!ATSForms.get18tReg().isEmpty() || !ATSForms.get18tEet().isEmpty() || !ATSForms.get18tSel().isEmpty() ||
						!ATSForms.get18tTyp().isEmpty() || !ATSForms.get18tCode().isEmpty() || !ATSForms.get18tDle().isEmpty() ||
						!ATSForms.get18tOpr().isEmpty() || !ATSForms.get18tOrgn().isEmpty() || !ATSForms.get18tPer().isEmpty() ||
						!ATSForms.get18tAltn().isEmpty() || !ATSForms.get18tRalt().isEmpty() || !ATSForms.get18tTalt().isEmpty() ||
						!ATSForms.get18tRif().isEmpty() || !ATSForms.get18tRmk().isEmpty() )) ss.tipe18baru();						
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
    	if (HeaderComposite.getPriorityFPL().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.reqPriority); HeaderComposite.tPriorityFPLFocus(); }
		else if (HeaderComposite.getDest1FPL().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.reqAddress); HeaderComposite.tDest1FPLFocus(); }
		else if ((!HeaderComposite.getDest1FPL().equals("")) && (HeaderComposite.getDest1FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest1FPLFocus(); }
		else if ((!HeaderComposite.getDest2FPL().equals("")) && (HeaderComposite.getDest2FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest2FPLFocus(); }
		else if ((!HeaderComposite.getDest3FPL().equals("")) && (HeaderComposite.getDest3FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest3FPLFocus(); }
		else if ((!HeaderComposite.getDest4FPL().equals("")) && (HeaderComposite.getDest4FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest4FPLFocus(); }
		else if ((!HeaderComposite.getDest5FPL().equals("")) && (HeaderComposite.getDest5FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest5FPLFocus(); }
		else if ((!HeaderComposite.getDest6FPL().equals("")) && (HeaderComposite.getDest6FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest6FPLFocus(); }
		else if ((!HeaderComposite.getDest7FPL().equals("")) && (HeaderComposite.getDest7FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest7FPLFocus(); }
		else if ((!HeaderComposite.getDest8FPL().equals("")) && (HeaderComposite.getDest8FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest8FPLFocus(); }
		else if ((!HeaderComposite.getDest9FPL().equals("")) && (HeaderComposite.getDest9FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest9FPLFocus(); }
		else if ((!HeaderComposite.getDest10FPL().equals("")) && (HeaderComposite.getDest10FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest10FPLFocus(); }
		else if ((!HeaderComposite.getDest11FPL().equals("")) && (HeaderComposite.getDest11FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest11FPLFocus(); }
		else if ((!HeaderComposite.getDest12FPL().equals("")) && (HeaderComposite.getDest12FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest12FPLFocus(); }
		else if ((!HeaderComposite.getDest13FPL().equals("")) && (HeaderComposite.getDest13FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest13FPLFocus(); }
		else if ((!HeaderComposite.getDest14FPL().equals("")) && (HeaderComposite.getDest14FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest14FPLFocus(); }
		else if ((!HeaderComposite.getDest15FPL().equals("")) && (HeaderComposite.getDest15FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest15FPLFocus(); }
		else if ((!HeaderComposite.getDest16FPL().equals("")) && (HeaderComposite.getDest16FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest16FPLFocus(); }
		else if ((!HeaderComposite.getDest17FPL().equals("")) && (HeaderComposite.getDest17FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest17FPLFocus(); }
		else if ((!HeaderComposite.getDest18FPL().equals("")) && (HeaderComposite.getDest18FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest18FPLFocus(); }
		else if ((!HeaderComposite.getDest19FPL().equals("")) && (HeaderComposite.getDest19FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest19FPLFocus(); }
		else if ((!HeaderComposite.getDest20FPL().equals("")) && (HeaderComposite.getDest20FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest20FPLFocus(); }
		else if ((!HeaderComposite.getDest21FPL().equals("")) && (HeaderComposite.getDest21FPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoAddress); HeaderComposite.tDest21FPLFocus(); }
		else if (HeaderComposite.getOriginatorFPL().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.reqOriginator); HeaderComposite.tOriginatorFPLFocus(); }
 		else if ((!HeaderComposite.getOriginatorFPL().equals("")) && (HeaderComposite.getOriginatorFPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoOriginator); HeaderComposite.tOriginatorFPLFocus(); }
    	//---------------------------------------- [body mandatory] ----------------------------------------
 		else if (ATSForms.get3a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.req3a); ATSForms.focus3aFPL(); }
        //7
 		else if (ATSForms.get7a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.req7a); ATSForms.focus7aFPL(); }
 		else if (ATSForms.get7b().equals("A") && (ATSForms.get7c().equals(""))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.req7c); ATSForms.focus7cFPL(); }
        else if (!ATSForms.get7b().equals("A") && (!ATSForms.get7c().equals(""))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.req7b); }
        else if (!ATSForms.get7c().equals("") && (ATSForms.get7c().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info7c); ATSForms.focus7cFPL(); }
    	//8
 		else if (ATSForms.get8a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.req8a); ATSForms.focus8aFPL(); }
        else if (ATSForms.get8b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.req8b); ATSForms.focus8bFPL(); }
        //9
        else if (t9b.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.req9b); ATSForms.focus9bFPL(); }
        else if (t9b.length() < 2) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info9b); ATSForms.focus9bFPL(); }
        else if (ATSForms.get9c().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.req9c); ATSForms.focus9cFPL(); }
        else if (!ss.wtc.equals("") && ATSForms.get9c().compareTo(ss.wtc) != 0) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(),em.wtc(ss.wtc, t9b)); ATSForms.focus9cFPL(); }
    	//10
        else if (ss.t10a.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.req10); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.equals("") && (ss.sprotek10a.contains("ngaco"))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoInvalid10aFix); ATSForms.focus10aFPL(); }		
		else if (ss.t10b.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.req10); ATSForms.focus10bBaruFPL(); }
		else if (!ss.t10b.equals("") && (ss.sdbguv.contains("tdksesuai"))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoInvalid10b+"\nInsert B1 or B2 or U1 or U2 or V1 or V2 or D1 or G1"); ATSForms.focus10bBaruFPL(); }		
		else if ((ss.t10b.contains("B1") || ss.t10b.contains("U1") || ss.t10b.contains("V1")) && (ss.t10b.contains("B2") || ss.t10b.contains("U2") || ss.t10b.contains("V2"))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10a.contains("N") && ss.t10a.contains("S")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalid10a); ATSForms.focus10aFPL(); }
		else if (ss.t10b.contains("N") && (ss.t10b.contains("A") || ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X") || ss.t10b.contains("B1") || ss.t10b.contains("B2") || ss.t10b.contains("U1") || ss.t10b.contains("U2") || ss.t10b.contains("V1") || ss.t10b.contains("V2") || ss.t10b.contains("D1") || ss.t10b.contains("G1"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(),"Invalid EQUIPMENT AND CAPABILITIES value.\n\nSSR Modes A and C and SSR Mode S are not allowed,\nif 'N' has inserted into field EQUIPMENT AND CAPABILITIES and vice versa.\n\nSee the instruction below the EQUIPMENT AND CAPABILITIES text !"); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10a.contains("W") && ATSForms.get18tSts().contains("NONRVSM")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalid10aSTS); ATSForms.focus18tStsFPL(); }
		else if (ss.t10b.contains("N") && ss.t10b.length()>1) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("A") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("C") && (ss.t10b.contains("A") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("E") && (ss.t10b.contains("C") || ss.t10b.contains("A") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("H") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("A") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("I") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("A") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("L") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("A") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("P") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("A") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("S") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("A") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
		else if (ss.t10b.contains("X") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("A"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalid10b); ATSForms.focus10bBaruFPL(); }
    	//13
		else if (ATSForms.get13a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.req13a); ATSForms.focus13aFPL(); }
		else if (ATSForms.get13a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info13a); ATSForms.focus13aFPL(); }
        else if (ATSForms.get13b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.req13b); ATSForms.focus13bFPL(); }
        else if (ATSForms.get13b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info13b); ATSForms.focus13bFPL(); }
        else if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4 && (time.iHh13>23) || (time.iMM13>59)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoInvalid13b); ATSForms.focus13bFPL(); }
		//15
        else if (ATSForms.get15a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.req15a); ATSForms.focus15aFPL(); }
		else if ((!t15a.startsWith("K")) && (!t15a.startsWith("N")) && (!t15a.startsWith("M"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalid15a); ATSForms.focus15aFPL(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (t15a.length()<5)) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info15aKN); ATSForms.focus15aFPL(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (!time.t15a2.startsWith("0")) && (!time.t15a2.startsWith("1")) && (!time.t15a2.startsWith("2")) && (!time.t15a2.startsWith("3")) && (!time.t15a2.startsWith("4")) && (!time.t15a2.startsWith("5")) && (!time.t15a2.startsWith("6")) && (!time.t15a2.startsWith("7")) && (!time.t15a2.startsWith("8")) && (!time.t15a2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info15aKN); ATSForms.focus15aFPL(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (!time.t15a3.startsWith("0")) && (!time.t15a3.startsWith("1")) && (!time.t15a3.startsWith("2")) && (!time.t15a3.startsWith("3")) && (!time.t15a3.startsWith("4")) && (!time.t15a3.startsWith("5")) && (!time.t15a3.startsWith("6")) && (!time.t15a3.startsWith("7")) && (!time.t15a3.startsWith("8")) && (!time.t15a3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info15aKN); ATSForms.focus15aFPL(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (!time.t15a4.startsWith("0")) && (!time.t15a4.startsWith("1")) && (!time.t15a4.startsWith("2")) && (!time.t15a4.startsWith("3")) && (!time.t15a4.startsWith("4")) && (!time.t15a4.startsWith("5")) && (!time.t15a4.startsWith("6")) && (!time.t15a4.startsWith("7")) && (!time.t15a4.startsWith("8")) && (!time.t15a4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info15aKN); ATSForms.focus15aFPL(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (!time.t15a5.startsWith("0")) && (!time.t15a5.startsWith("1")) && (!time.t15a5.startsWith("2")) && (!time.t15a5.startsWith("3")) && (!time.t15a5.startsWith("4")) && (!time.t15a5.startsWith("5")) && (!time.t15a5.startsWith("6")) && (!time.t15a5.startsWith("7")) && (!time.t15a5.startsWith("8")) && (!time.t15a5.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info15aKN); ATSForms.focus15aFPL(); }
		else if ((t15a.startsWith("M")) && ((t15a.length()>4) || (t15a.length()<4))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info15aM); ATSForms.focus15aFPL(); }
		else if (((t15a.startsWith("M"))) && (!time.t15a2.startsWith("0")) && (!time.t15a2.startsWith("1")) && (!time.t15a2.startsWith("2")) && (!time.t15a2.startsWith("3")) && (!time.t15a2.startsWith("4")) && (!time.t15a2.startsWith("5")) && (!time.t15a2.startsWith("6")) && (!time.t15a2.startsWith("7")) && (!time.t15a2.startsWith("8")) && (!time.t15a2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info15aM); ATSForms.focus15aFPL(); }
		else if (((t15a.startsWith("M"))) && (!time.t15a3.startsWith("0")) && (!time.t15a3.startsWith("1")) && (!time.t15a3.startsWith("2")) && (!time.t15a3.startsWith("3")) && (!time.t15a3.startsWith("4")) && (!time.t15a3.startsWith("5")) && (!time.t15a3.startsWith("6")) && (!time.t15a3.startsWith("7")) && (!time.t15a3.startsWith("8")) && (!time.t15a3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info15aM); ATSForms.focus15aFPL(); }
		else if (((t15a.startsWith("M"))) && (!time.t15a4.startsWith("0")) && (!time.t15a4.startsWith("1")) && (!time.t15a4.startsWith("2")) && (!time.t15a4.startsWith("3")) && (!time.t15a4.startsWith("4")) && (!time.t15a4.startsWith("5")) && (!time.t15a4.startsWith("6")) && (!time.t15a4.startsWith("7")) && (!time.t15a4.startsWith("8")) && (!time.t15a4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info15aM); ATSForms.focus15aFPL(); }
		else if (ATSForms.get15b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.req15b); ATSForms.focus15bFPL(); }
		else if ((!t15b.startsWith("F")) && (!t15b.startsWith("S")) && (!t15b.startsWith("A")) && (!t15b.startsWith("M")) && (!t15b.equals("VFR"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalid15b); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (t15b.length()<5)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info14cSM); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (!time.t15b2.startsWith("0")) && (!time.t15b2.startsWith("1")) && (!time.t15b2.startsWith("2")) && (!time.t15b2.startsWith("3")) && (!time.t15b2.startsWith("4")) && (!time.t15b2.startsWith("5")) && (!time.t15b2.startsWith("6")) && (!time.t15b2.startsWith("7")) && (!time.t15b2.startsWith("8")) && (!time.t15b2.startsWith("9"))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info14cSM); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (!time.t15b3.startsWith("0")) && (!time.t15b3.startsWith("1")) && (!time.t15b3.startsWith("2")) && (!time.t15b3.startsWith("3")) && (!time.t15b3.startsWith("4")) && (!time.t15b3.startsWith("5")) && (!time.t15b3.startsWith("6")) && (!time.t15b3.startsWith("7")) && (!time.t15b3.startsWith("8")) && (!time.t15b3.startsWith("9"))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info14cSM); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (!time.t15b4.startsWith("0")) && (!time.t15b4.startsWith("1")) && (!time.t15b4.startsWith("2")) && (!time.t15b4.startsWith("3")) && (!time.t15b4.startsWith("4")) && (!time.t15b4.startsWith("5")) && (!time.t15b4.startsWith("6")) && (!time.t15b4.startsWith("7")) && (!time.t15b4.startsWith("8")) && (!time.t15b4.startsWith("9"))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info14cSM); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (!time.t15b5.startsWith("0")) && (!time.t15b5.startsWith("1")) && (!time.t15b5.startsWith("2")) && (!time.t15b5.startsWith("3")) && (!time.t15b5.startsWith("4")) && (!time.t15b5.startsWith("5")) && (!time.t15b5.startsWith("6")) && (!time.t15b5.startsWith("7")) && (!time.t15b5.startsWith("8")) && (!time.t15b5.startsWith("9"))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info14cSM); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("F")) || (t15b.startsWith("A"))) && ((t15b.length()>4) || (t15b.length()<4))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info14cFA); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("F")) || (t15b.startsWith("A"))) && (!time.t15b2.startsWith("0")) && (!time.t15b2.startsWith("1")) && (!time.t15b2.startsWith("2")) && (!time.t15b2.startsWith("3")) && (!time.t15b2.startsWith("4")) && (!time.t15b2.startsWith("5")) && (!time.t15b2.startsWith("6")) && (!time.t15b2.startsWith("7")) && (!time.t15b2.startsWith("8")) && (!time.t15b2.startsWith("9"))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info14cFA); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("F")) || (t15b.startsWith("A"))) && (!time.t15b3.startsWith("0")) && (!time.t15b3.startsWith("1")) && (!time.t15b3.startsWith("2")) && (!time.t15b3.startsWith("3")) && (!time.t15b3.startsWith("4")) && (!time.t15b3.startsWith("5")) && (!time.t15b3.startsWith("6")) && (!time.t15b3.startsWith("7")) && (!time.t15b3.startsWith("8")) && (!time.t15b3.startsWith("9"))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info14cFA); ATSForms.focus15bFPL(); }
		else if (((t15b.startsWith("F")) || (t15b.startsWith("A"))) && (!time.t15b4.startsWith("0")) && (!time.t15b4.startsWith("1")) && (!time.t15b4.startsWith("2")) && (!time.t15b4.startsWith("3")) && (!time.t15b4.startsWith("4")) && (!time.t15b4.startsWith("5")) && (!time.t15b4.startsWith("6")) && (!time.t15b4.startsWith("7")) && (!time.t15b4.startsWith("8")) && (!time.t15b4.startsWith("9"))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info14cFA); ATSForms.focus15bFPL(); }
		else if (ATSForms.get15c().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.req15c); ATSForms.focus15cFPL(); }
    	//16
		else if (ATSForms.get16a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.req16a); ATSForms.focus16aFPL(); }
		else if (ATSForms.get16a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info16a); ATSForms.focus16aFPL(); }
		else if (ATSForms.get16b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.req16b); ATSForms.focus16bFPL(); }
		else if (ATSForms.get16b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info16b); ATSForms.focus16bFPL(); }
		else if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4 && (time.iHh16>23) || (time.iMM16>59)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoInvalid16b); ATSForms.focus16bFPL(); }
		else if (!ATSForms.get16c().equals("") && ATSForms.get16c().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info16c); ATSForms.focus16cFPL(); }
		else if (!ATSForms.get16c2nd().equals("") && ATSForms.get16c2nd().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info16d); ATSForms.focus16dFPL(); }
		//tambahan
		else if (!ATSForms.get16c2nd().isEmpty()&&ATSForms.get16c2nd().length()==4 && ATSForms.get16c().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info16cd); ATSForms.focus16cFPL(); }
    	else if (ReadFromFile.ReadInputFile1(MainForm.sFolder+ "altnad.txt").compareTo("1")==0 &&
    			ATSForms.get16c().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.req16c); ATSForms.focus16cFPL(); }
    	
    	//18 indicator TYP/
        else if (ATSForms.get9b().equals("ZZZZ") && ATSForms.get18tTyp().equals("")) {  DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoTyp9b); ATSForms.focus18tTypFPL(); }
        else if (!ATSForms.get9b().contains("ZZZZ") && !ATSForms.get18tTyp().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info9bTyp); ATSForms.focus9bFPL(); }
    	//indicator COM/, DAT/, NAV/, SUR/ [link ke Item 10]
        else if (ss.t10a.contains("Z") && ss.s10aZ.contains("ngaco"))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoCom10); ATSForms.focus18tComFPL(); }
        else if (ss.kriteria.contains("ngaco") && ATSForms.get18tSur().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoSur10); ATSForms.focus18tSurFPL(); }
    	//indicator DEP/
        else if ((ATSForms.get13a().equals("ZZZZ") || ATSForms.get13a().equals("AFIL")) && ATSForms.get18tDep().equals("")) {  DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoDep13); ATSForms.focus18tDepFPL(); }
        else if (!ATSForms.get13a().contains("ZZZZ") && !ATSForms.get13a().contains("AFIL") && !ATSForms.get18tDep().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info13Dep); ATSForms.focus13aFPL(); }
    	//indicator DEST/
        else if (ATSForms.get16a().equals("ZZZZ") && ATSForms.get18tDest().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoDest16); ATSForms.focus18tDestFPL(); }
        else if (!ATSForms.get16a().contains("ZZZZ") && !ATSForms.get18tDest().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info16Dest); ATSForms.focus16aFPL(); }
    	//indicator ALTN/
        else if (ATSForms.get16c().equals("ZZZZ") && ATSForms.get18tAltn().equals("")) {  DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoAltn16); ATSForms.focus18tAltnFPL(); }
        else if (!ATSForms.get16c().contains("ZZZZ") && !ATSForms.get18tAltn().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info16Altn); ATSForms.focus16cFPL(); }
    	//indicator REG/, DOF/
        else if (!ATSForms.get18tReg().isEmpty() && ATSForms.get18tReg().equals(ATSForms.get7a())) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalidReg); ATSForms.focus18tRegFPL(); }
        else if (!ATSForms.get18tReg().isEmpty() && !ATSForms.get18tOpr().equals("") && ATSForms.get18tReg().equals(ATSForms.get18tOpr())) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalidOpr); ATSForms.focus18tOprFPL(); }
        else if (!ATSForms.get18tReg().isEmpty() && (ATSForms.get18tReg().length() > 7 || ATSForms.get18tReg().contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalidReg); ATSForms.focus18tRegFPL(); }
		//else if (ATSForms.get18tDof().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.reqDof); ATSForms.focus18tDofFPL(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()<6) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalidDof); ATSForms.focus18tDofFPL(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && (time.sYy.equals("00") || time.sHh.equals("00") || time.sMm.equals("00") || time.iHh>12 || time.iMM>31)) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalid18Dof); ATSForms.focus18tDofFPL(); }
		else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && Integer.parseInt(datedof) < Integer.parseInt(time.datenow)) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.infoInvalid18DofVal); ATSForms.focus18tDofFPL(); }
		//indicator STS/, PER/, CODE/, SEL/, PBN/
        else if (!ATSForms.get18tSts().equals("") && ss.criteria_sts.contains("ngaco")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalidSts); ATSForms.focus18tStsFPL(); }
        else if (!ATSForms.get18tPer().equals("") && ATSForms.get18tPer().length() > 1) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalidPer); ATSForms.focus18tPerFPL(); }
        else if (!ATSForms.get18tCode().equals("") && (ATSForms.get18tCode().length() > 6 || ATSForms.get18tCode().contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalidCode); ATSForms.focus18tCodeFPL(); }
        else if (!ATSForms.get18tSel().equals("") && (ATSForms.get18tSel().length() > 4 || ATSForms.get18tSel().contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalidSel); ATSForms.focus18tSelFPL(); }
    	//PBN
        else if (!ss.tpbn.equals("") && (ss.tpbn.length() > 16 || ss.tpbn.contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalidPbn); ATSForms.focus18tPbnFPL(); }
        else if (!ss.tpbn.equals("") && ss.sprotekpbn.contains("ngaco")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalidPbnFix); ATSForms.focus18tPbnFPL(); }
		else if (ss.t10a.contains("R") && ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoPbn10); ATSForms.focus18tPbnFPL(); }
		else if (!ss.t10a.contains("R") && !ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info10Pbn); ATSForms.focus10aFPL(); }
    	//G
		else if (!ss.t10a.contains("G") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info10G); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("G") && !ss.krit2.equals("") && (ss.krit2.contains("B2") || ss.krit2.contains("C2") || ss.krit2.contains("D2") || ss.krit2.contains("O2"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info10G); ATSForms.focus10aFPL(); }
		//D
		else if (!ss.t10a.contains("D") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info10D); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("D") && !ss.krit3.equals("") && (ss.krit3.contains("B3") || ss.krit3.contains("C3") || ss.krit3.contains("D3") || ss.krit3.contains("O3"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info10D); ATSForms.focus10aFPL(); }
    	//I
		else if (!ss.t10a.contains("I") && !ss.krit5.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info10I); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1"))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info10I); ATSForms.focus10aFPL(); }
		//DI
		else if (!ss.t10a.contains("D") && !ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info10DI); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("D") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info10DI); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("I") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info10DI); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("I") && (ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info10DI); ATSForms.focus10aFPL(); }
    	//OD / SD
		else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit1.contains("B1")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info10OD); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info10OD); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("D") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info10OD); ATSForms.focus10aFPL(); }
		else if (!ss.t10a.contains("D") && ss.krit1.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.info10OD); ATSForms.focus10aFPL(); }
    	//19
		else if (!ATSForms.get19a().equals("") && ATSForms.get19a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info19a); ATSForms.focus19aFPL(); } 
		else if (!ATSForms.get19a().isEmpty() && ATSForms.get19a().length()==4 && ((time.iHh19>23) || (time.iMM19>59))) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(), em.infoInvalid19a); ATSForms.focus19aFPL(); }
		else if (!ATSForms.get19fNum().equals("") && ATSForms.get19fNum().length() < 2) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info19fNum); ATSForms.focus19NumFPL(); } 
		else if (!ATSForms.get19fCap().equals("") && ATSForms.get19fCap().length() < 3) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info19fCap); ATSForms.focus19CapFPL(); } 
		else if ((!ATSForms.get19fNum().equals("")) && (ATSForms.get19fCap().equals(""))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info19f); ATSForms.focus19CapFPL(); }
		else if ((!ATSForms.get19fCap().equals("")) && (ATSForms.get19fNum().equals(""))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info19f); ATSForms.focus19NumFPL(); }
		else if ((!ATSForms.get19fCov().equals("")) && ((ATSForms.get19fNum().equals("")) || (ATSForms.get19fCap().equals("")))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info19f); ATSForms.focus19NumFPL(); }
		else if ((!ATSForms.get19fCol().equals("")) && ((ATSForms.get19fNum().equals("")) || (ATSForms.get19fCap().equals("")))) { DialogFactory.openInfoDialog(Shells.sh_nFPL, getText(), em.info19f); ATSForms.focus19NumFPL(); }
    	//length
		else if (ATSForms.getBodyFPL().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nFPL, getText(),em.req(ATSForms.getBodyFPL().length())); }
 		else {
 			// mendapatkan isi masing2 field
			ss.hFPL();ss.s3();ss.s7();ss.s8();ss.s9();ss.s13();ss.s15();ss.s16();ss.s19();ss.sSpace();
 			ss.data(ATSForms.getBodyFPL());
			
			if (ss.acft_id.compareTo(t7a) == 0) { //SAME AIRCRAFT
				DialogFactory.openYesNoDialog(Shells.sh_nFPL, "Aircraft Identification","Aircraft Id has been sent today. Send it anyway ?");
				boolean tentu = DialogFactory.getPenentuan(); 
				if (tentu == true) {
					
					if (ss.toa.compareTo("") == 0) { //NOT REGISTERED TYPE OF AIRCRAFT
	    				DialogFactory.openYesNoDialog(Shells.sh_nFPL, "Type of Aircraft","Type of aircraft is not registered. Send it anyway ?");
	    				boolean ten = DialogFactory.getPenentuan(); 
	    				if (ten == true) {
	    					insfpl(strDof);
	    				} //end of if == tentu
	    				else { System.out.println(">> Sending has been canceled."); }
					} //end of toa=""
					else if (ss.toa.compareTo("") != 0) { //REGISTERED TYPE OF AIRCRAFT
	    				insfpl(strDof);
	    			} //end of registered toa
				} //end of if == tentu
				else { System.out.println(">> Sending has been canceled."); }
			} //end of acft_id=""
			
			else if (ss.acft_id.compareTo(t7a) != 0) { //DIFFERENT AIRCRAFT
				if (ss.toa.compareTo("") == 0) { //NOT REGISTERED TYPE OF AIRCRAFT
					DialogFactory.openYesNoDialog(Shells.sh_nFPL, "Type of Aircraft","Type of aircraft is not registered. Send it anyway ?");
    				boolean ten = DialogFactory.getPenentuan(); 
    				if (ten == true) {
    					insfpl(strDof);
    				} //end of if == tentu
    				else { System.out.println(">> Sending has been canceled."); }
    			} //end of toa=""
    			else if (ss.toa.compareTo("") != 0) { //REGISTERED TYPE OF AIRCRAFT
    				insfpl(strDof);
    			} //end of registered toa
			} //end of if == tentu
			else { System.out.println(">> Sending has been canceled."); }
		}
    }
    
    void insfpl(String strDof) {
    	if ((ss.sReg.compareTo("REG/0")==0 || ss.sReg.compareTo("")==0) &&  (ss.sDof.compareTo("DOF/0")==0 || ss.sDof.compareTo("")==0) && ss.s18Baru.compareTo("")==0) { ss.s18Baru="0"; }
		if (ss.sDof.startsWith(" ")) { ss.sDof=ss.sDof.replaceFirst("\\s+", ""); }
		if (ss.sReg.startsWith(" ")) { ss.sReg=ss.sReg.replaceFirst("\\s+", ""); }
		//------------------------------------------------
		validasiDuplicateAddress(Shells.sh_nFPL,"FPL");
		// tutup koneksi GetRoute
		ss.connClose("FPL");
    }
    
    void sDLA() { 
    	ss.sReg=""; ss.sDof=""; ss.s18Baru="";
    	ATSForms.getDLAValue(); time.tgl();
    	ss.sDof = ATSForms.get18tDof();
    	ss.proteksi18();
    	//----------------------------------------------------------------
    	if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4) { time.t13b(ATSForms.get13b()); }
    	if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4) { time.t16b(ATSForms.get16b()); }
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
		if (strDof.startsWith(" ")) { strDof = strDof.replaceFirst("\\s+", ""); }
		if (strDof.compareTo("DOF/000000")==0) strDof = "0";
    	//---------------------------------------- [header mandatory] ----------------------------------------
    	if (HeaderComposite.getPriorityDLA().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nDLA, getText(), em.reqPriority); HeaderComposite.tPriorityDLAFocus(); }
		else if (HeaderComposite.getDest1DLA().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nDLA, getText(), em.reqAddress); HeaderComposite.tDest1DLAFocus(); }
		else if ((!HeaderComposite.getDest1DLA().equals("")) && (HeaderComposite.getDest1DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest1DLAFocus(); }
		else if ((!HeaderComposite.getDest2DLA().equals("")) && (HeaderComposite.getDest2DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest2DLAFocus(); }
		else if ((!HeaderComposite.getDest3DLA().equals("")) && (HeaderComposite.getDest3DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest3DLAFocus(); }
		else if ((!HeaderComposite.getDest4DLA().equals("")) && (HeaderComposite.getDest4DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest4DLAFocus(); }
		else if ((!HeaderComposite.getDest5DLA().equals("")) && (HeaderComposite.getDest5DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest5DLAFocus(); }
		else if ((!HeaderComposite.getDest6DLA().equals("")) && (HeaderComposite.getDest6DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest6DLAFocus(); }
		else if ((!HeaderComposite.getDest7DLA().equals("")) && (HeaderComposite.getDest7DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest7DLAFocus(); }
		else if ((!HeaderComposite.getDest8DLA().equals("")) && (HeaderComposite.getDest8DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest8DLAFocus(); }
		else if ((!HeaderComposite.getDest9DLA().equals("")) && (HeaderComposite.getDest9DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest9DLAFocus(); }
		else if ((!HeaderComposite.getDest10DLA().equals("")) && (HeaderComposite.getDest10DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest10DLAFocus(); }
		else if ((!HeaderComposite.getDest11DLA().equals("")) && (HeaderComposite.getDest11DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest11DLAFocus(); }
		else if ((!HeaderComposite.getDest12DLA().equals("")) && (HeaderComposite.getDest12DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest12DLAFocus(); }
		else if ((!HeaderComposite.getDest13DLA().equals("")) && (HeaderComposite.getDest13DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest13DLAFocus(); }
		else if ((!HeaderComposite.getDest14DLA().equals("")) && (HeaderComposite.getDest14DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest14DLAFocus(); }
		else if ((!HeaderComposite.getDest15DLA().equals("")) && (HeaderComposite.getDest15DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest15DLAFocus(); }
		else if ((!HeaderComposite.getDest16DLA().equals("")) && (HeaderComposite.getDest16DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest16DLAFocus(); }
		else if ((!HeaderComposite.getDest17DLA().equals("")) && (HeaderComposite.getDest17DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest17DLAFocus(); }
		else if ((!HeaderComposite.getDest18DLA().equals("")) && (HeaderComposite.getDest18DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest18DLAFocus(); }
		else if ((!HeaderComposite.getDest19DLA().equals("")) && (HeaderComposite.getDest19DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest19DLAFocus(); }
		else if ((!HeaderComposite.getDest20DLA().equals("")) && (HeaderComposite.getDest20DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest20DLAFocus(); }
		else if ((!HeaderComposite.getDest21DLA().equals("")) && (HeaderComposite.getDest21DLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoAddress); HeaderComposite.tDest21DLAFocus(); }
		else if (HeaderComposite.getOriginatorDLA().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nDLA, getText(), em.reqOriginator); HeaderComposite.tOriginatorDLAFocus(); }
 		else if ((!HeaderComposite.getOriginatorDLA().equals("")) && (HeaderComposite.getOriginatorDLA().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoOriginator); HeaderComposite.tOriginatorDLAFocus(); }
    	//---------------------------------------- [body mandatory] ----------------------------------------
 		else if (ATSForms.get3a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nDLA, getText(), em.req3a); ATSForms.focus3aDLA(); }
        else if (ATSForms.get7a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nDLA, getText(), em.req7a); ATSForms.focus7aDLA(); }
        else if (ATSForms.get7b().equals("A") && (ATSForms.get7c().equals(""))) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.req7c); ATSForms.focus7cDLA(); }
        else if (!ATSForms.get7b().equals("A") && (!ATSForms.get7c().equals(""))) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.req7b); }
        else if (!ATSForms.get7c().equals("") && (ATSForms.get7c().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.info7c); ATSForms.focus7cDLA(); }
        else if (ATSForms.get13a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nDLA, getText(), em.req13a); ATSForms.focus13aDLA(); }
        else if (ATSForms.get13a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.info13a); ATSForms.focus13aDLA(); }
		else if (ATSForms.get13b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nDLA, getText(), em.req13b); ATSForms.focus13bDLA(); }
		else if (ATSForms.get13b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.info13b); ATSForms.focus13bDLA(); }
		else if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4 && (time.iHh13>23) || (time.iMM13>59)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoInvalid13b); ATSForms.focus13bDLA(); }
		else if (ATSForms.get16a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nDLA, getText(), em.req16a); ATSForms.focus16aDLA(); }
		else if (ATSForms.get16a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.info16a); ATSForms.focus16aDLA(); }
		else if (!ATSForms.get16b().equals("") && ATSForms.get16b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.info16b); ATSForms.focus16bDLA(); }
		else if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4 && (time.iHh16>23) || (time.iMM16>59)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoInvalid16b); ATSForms.focus16bDLA(); }
		else if (!ATSForms.get16c().equals("") && ATSForms.get16c().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.info16c); ATSForms.focus16cDLA(); }
		else if (!ATSForms.get16c2nd().equals("") && ATSForms.get16c2nd().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.info16d); ATSForms.focus16dDLA(); }
		else if (ATSForms.get18tDof().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nDLA, getText(), em.reqDof); ATSForms.focus18tDofDLA(); }
        else if (!ATSForms.get18tDof().isEmpty() && !ATSForms.get18tDof().startsWith("0") && ATSForms.get18tDof().length()<6) { DialogFactory.openWarningDialog(Shells.sh_nDLA, getText(), em.infoInvalidDof); ATSForms.focus18tDofDLA(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().startsWith("0") && ATSForms.get18tDof().length()>1) { DialogFactory.openWarningDialog(Shells.sh_nDLA, getText(), em.infoInvalidDof); ATSForms.focus18tDofDLA(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && (time.sYy.equals("00") || time.sHh.equals("00") || time.sMm.equals("00") || time.iHh>12 || time.iMM>31)) { DialogFactory.openWarningDialog(Shells.sh_nDLA, getText(), em.infoInvalid18Dof); ATSForms.focus18tDofDLA(); }
		else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && Integer.parseInt(datedof) < Integer.parseInt(time.datenow)) { DialogFactory.openInfoDialog(Shells.sh_nDLA, getText(), em.infoInvalid18DofVal); ATSForms.focus18tDofDLA(); }
		else if (ATSForms.getBodyDLA().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nDLA, getText(),em.req(ATSForms.getBodyDLA().length())); }
 		else {
			// mendapatkan isi masing2 field
			ss.hDLA();ss.s3();ss.s7();ss.s13();ss.s16();
			if (ss.s18Baru.contains("DOF/000000")) ss.s18Baru = ss.s18Baru.replace("DOF/000000", "0");
			if (ss.s18Baru.startsWith(" ")) { ss.s18Baru=ss.s18Baru.replaceFirst("\\s+", ""); }
 			ss.data(ATSForms.getBodyDLA());
 			validasiDuplicateAddress(Shells.sh_nDLA,"DLA");
			// tutup koneksi GetFPL
    		ss.connClose("DLA");
		}
 	}
    
    void sCHG() {
    	ss.sReg=""; ss.sDof=""; ss.s18Baru="";
    	ATSForms.getCHGValue(); time.tgl();
    	ss.sDof = ATSForms.get18tDof();
    	ss.proteksi18();
    	//----------------------------------------------------------------
    	if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4) { time.t13b(ATSForms.get13b()); }
    	if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4) { time.t16b(ATSForms.get16b()); }
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
		if (strDof.startsWith(" ")) { strDof = strDof.replaceFirst("\\s+", ""); }
		if (strDof.compareTo("DOF/000000")==0) strDof = "0";
    	//---------------------------------------- [header mandatory] ----------------------------------------
    	if (HeaderComposite.getPriorityCHG().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCHG, getText(), em.reqPriority); HeaderComposite.tPriorityCHGFocus(); }
		else if (HeaderComposite.getDest1CHG().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCHG, getText(), em.reqAddress); HeaderComposite.tDest1CHGFocus(); }
		else if ((!HeaderComposite.getDest1CHG().equals("")) && (HeaderComposite.getDest1CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest1CHGFocus(); }
		else if ((!HeaderComposite.getDest2CHG().equals("")) && (HeaderComposite.getDest2CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest2CHGFocus(); }
		else if ((!HeaderComposite.getDest3CHG().equals("")) && (HeaderComposite.getDest3CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest3CHGFocus(); }
		else if ((!HeaderComposite.getDest4CHG().equals("")) && (HeaderComposite.getDest4CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest4CHGFocus(); }
		else if ((!HeaderComposite.getDest5CHG().equals("")) && (HeaderComposite.getDest5CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest5CHGFocus(); }
		else if ((!HeaderComposite.getDest6CHG().equals("")) && (HeaderComposite.getDest6CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest6CHGFocus(); }
		else if ((!HeaderComposite.getDest7CHG().equals("")) && (HeaderComposite.getDest7CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest7CHGFocus(); }
		else if ((!HeaderComposite.getDest8CHG().equals("")) && (HeaderComposite.getDest8CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest8CHGFocus(); }
		else if ((!HeaderComposite.getDest9CHG().equals("")) && (HeaderComposite.getDest9CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest9CHGFocus(); }
		else if ((!HeaderComposite.getDest10CHG().equals("")) && (HeaderComposite.getDest10CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest10CHGFocus(); }
		else if ((!HeaderComposite.getDest11CHG().equals("")) && (HeaderComposite.getDest11CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest11CHGFocus(); }
		else if ((!HeaderComposite.getDest12CHG().equals("")) && (HeaderComposite.getDest12CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest12CHGFocus(); }
		else if ((!HeaderComposite.getDest13CHG().equals("")) && (HeaderComposite.getDest13CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest13CHGFocus(); }
		else if ((!HeaderComposite.getDest14CHG().equals("")) && (HeaderComposite.getDest14CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest14CHGFocus(); }
		else if ((!HeaderComposite.getDest15CHG().equals("")) && (HeaderComposite.getDest15CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest15CHGFocus(); }
		else if ((!HeaderComposite.getDest16CHG().equals("")) && (HeaderComposite.getDest16CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest16CHGFocus(); }
		else if ((!HeaderComposite.getDest17CHG().equals("")) && (HeaderComposite.getDest17CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest17CHGFocus(); }
		else if ((!HeaderComposite.getDest18CHG().equals("")) && (HeaderComposite.getDest18CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest18CHGFocus(); }
		else if ((!HeaderComposite.getDest19CHG().equals("")) && (HeaderComposite.getDest19CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest19CHGFocus(); }
		else if ((!HeaderComposite.getDest20CHG().equals("")) && (HeaderComposite.getDest20CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest20CHGFocus(); }
		else if ((!HeaderComposite.getDest21CHG().equals("")) && (HeaderComposite.getDest21CHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoAddress); HeaderComposite.tDest21CHGFocus(); }
		else if (HeaderComposite.getOriginatorCHG().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCHG, getText(), em.reqOriginator); HeaderComposite.tOriginatorCHGFocus(); }
 		else if ((!HeaderComposite.getOriginatorCHG().equals("")) && (HeaderComposite.getOriginatorCHG().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoOriginator); HeaderComposite.tOriginatorCHGFocus(); }
    	//---------------------------------------- [body mandatory] ----------------------------------------
 		else if (ATSForms.get3a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCHG, getText(), em.req3a); ATSForms.focus3aCHG(); }
        else if (ATSForms.get7a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCHG, getText(), em.req7a); ATSForms.focus7aCHG(); }
        else if (ATSForms.get7b().equals("A") && (ATSForms.get7c().equals(""))) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.req7c); ATSForms.focus7cCHG(); }
        else if (!ATSForms.get7b().equals("A") && (!ATSForms.get7c().equals(""))) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.req7b); }
        else if (!ATSForms.get7c().equals("") && (ATSForms.get7c().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.info7c); ATSForms.focus7cCHG(); }
        else if (ATSForms.get13a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCHG, getText(), em.req13a); ATSForms.focus13aCHG(); }
        else if (ATSForms.get13a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.info13a); ATSForms.focus13aCHG(); }
        else if ((!ATSForms.get13b().equals("")) && (ATSForms.get13b().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.info13b); ATSForms.focus13bCHG(); }
        else if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4 && (time.iHh13>23) || (time.iMM13>59)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoInvalid13b); ATSForms.focus13bCHG(); }
		else if (ATSForms.get16a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCHG, getText(), em.req16a); ATSForms.focus16aCHG(); }
		else if (ATSForms.get16a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.info16a); ATSForms.focus16aCHG(); }
		else if (!ATSForms.get16b().equals("") && ATSForms.get16b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.info16b); ATSForms.focus16bCHG(); }
		else if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4 && (time.iHh16>23) || (time.iMM16>59)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoInvalid16b); ATSForms.focus16bCHG(); }
		else if (!ATSForms.get16c().equals("") && ATSForms.get16c().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.info16c); ATSForms.focus16cCHG(); }
		else if (!ATSForms.get16c2nd().equals("") && ATSForms.get16c2nd().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.info16d); ATSForms.focus16dCHG(); }
		else if (ATSForms.get18tDof().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nCHG, getText(), em.reqDof); ATSForms.focus18tDofCHG(); }
        else if (!ATSForms.get18tDof().isEmpty() && !ATSForms.get18tDof().startsWith("0") && ATSForms.get18tDof().length()<6) { DialogFactory.openWarningDialog(Shells.sh_nCHG, getText(), em.infoInvalidDof); ATSForms.focus18tDofCHG(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().startsWith("0") && ATSForms.get18tDof().length()>1) { DialogFactory.openWarningDialog(Shells.sh_nCHG, getText(), em.infoInvalidDof); ATSForms.focus18tDofCHG(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && (time.sYy.equals("00") || time.sHh.equals("00") || time.sMm.equals("00") || time.iHh>12 || time.iMM>31)) { DialogFactory.openWarningDialog(Shells.sh_nCHG, getText(), em.infoInvalid18Dof); ATSForms.focus18tDofCHG(); }
		else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && Integer.parseInt(datedof) < Integer.parseInt(time.datenow)) { DialogFactory.openInfoDialog(Shells.sh_nCHG, getText(), em.infoInvalid18DofVal); ATSForms.focus18tDofCHG(); }
		else if (ATSForms.get22().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCHG, getText(), em.req22); ATSForms.focus22CHG(); }
        else if (ATSForms.getBodyCHG().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nCHG, getText(),em.req(ATSForms.getBodyCHG().length())); }
		else {
			// mendapatkan isi masing2 field
			ss.hCHG();ss.s3();ss.s7();ss.s13();ss.s16();ss.s22();
			if (ss.s18Baru.contains("DOF/000000")) ss.s18Baru = ss.s18Baru.replace("DOF/000000", "0");
			if (ss.s18Baru.startsWith(" ")) { ss.s18Baru=ss.s18Baru.replaceFirst("\\s+", ""); }
 			ss.data(ATSForms.getBodyCHG());
 			validasiDuplicateAddress(Shells.sh_nCHG,"CHG");
			// tutup koneksi GetFPL
    		ss.connClose("CHG");
		}
    }
    
    void sCNL() { 
    	ss.sReg=""; ss.sDof=""; ss.s18Baru="";
    	ATSForms.getCNLValue(); time.tgl(); 
    	ss.sDof = ATSForms.get18tDof();
    	ss.proteksi18();
    	//----------------------------------------------------------------
    	if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4) { time.t13b(ATSForms.get13b()); }
    	if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4) { time.t16b(ATSForms.get16b()); }
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
		if (strDof.startsWith(" ")) { strDof = strDof.replaceFirst("\\s+", ""); }
		if (strDof.compareTo("DOF/000000")==0) strDof = "0";
    	//---------------------------------------- [header mandatory] ----------------------------------------
    	if (HeaderComposite.getPriorityCNL().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCNL, getText(), em.reqPriority); HeaderComposite.tPriorityCNLFocus(); }
		else if (HeaderComposite.getDest1CNL().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCNL, getText(), em.reqAddress); HeaderComposite.tDest1CNLFocus(); }
		else if ((!HeaderComposite.getDest1CNL().equals("")) && (HeaderComposite.getDest1CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest1CNLFocus(); }
		else if ((!HeaderComposite.getDest2CNL().equals("")) && (HeaderComposite.getDest2CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest2CNLFocus(); }
		else if ((!HeaderComposite.getDest3CNL().equals("")) && (HeaderComposite.getDest3CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest3CNLFocus(); }
		else if ((!HeaderComposite.getDest4CNL().equals("")) && (HeaderComposite.getDest4CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest4CNLFocus(); }
		else if ((!HeaderComposite.getDest5CNL().equals("")) && (HeaderComposite.getDest5CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest5CNLFocus(); }
		else if ((!HeaderComposite.getDest6CNL().equals("")) && (HeaderComposite.getDest6CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest6CNLFocus(); }
		else if ((!HeaderComposite.getDest7CNL().equals("")) && (HeaderComposite.getDest7CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest7CNLFocus(); }
		else if ((!HeaderComposite.getDest8CNL().equals("")) && (HeaderComposite.getDest8CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest8CNLFocus(); }
		else if ((!HeaderComposite.getDest9CNL().equals("")) && (HeaderComposite.getDest9CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest9CNLFocus(); }
		else if ((!HeaderComposite.getDest10CNL().equals("")) && (HeaderComposite.getDest10CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest10CNLFocus(); }
		else if ((!HeaderComposite.getDest11CNL().equals("")) && (HeaderComposite.getDest11CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest11CNLFocus(); }
		else if ((!HeaderComposite.getDest12CNL().equals("")) && (HeaderComposite.getDest12CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest12CNLFocus(); }
		else if ((!HeaderComposite.getDest13CNL().equals("")) && (HeaderComposite.getDest13CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest13CNLFocus(); }
		else if ((!HeaderComposite.getDest14CNL().equals("")) && (HeaderComposite.getDest14CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest14CNLFocus(); }
		else if ((!HeaderComposite.getDest15CNL().equals("")) && (HeaderComposite.getDest15CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest15CNLFocus(); }
		else if ((!HeaderComposite.getDest16CNL().equals("")) && (HeaderComposite.getDest16CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest16CNLFocus(); }
		else if ((!HeaderComposite.getDest17CNL().equals("")) && (HeaderComposite.getDest17CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest17CNLFocus(); }
		else if ((!HeaderComposite.getDest18CNL().equals("")) && (HeaderComposite.getDest18CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest18CNLFocus(); }
		else if ((!HeaderComposite.getDest19CNL().equals("")) && (HeaderComposite.getDest19CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest19CNLFocus(); }
		else if ((!HeaderComposite.getDest20CNL().equals("")) && (HeaderComposite.getDest20CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest20CNLFocus(); }
		else if ((!HeaderComposite.getDest21CNL().equals("")) && (HeaderComposite.getDest21CNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoAddress); HeaderComposite.tDest21CNLFocus(); }
		else if (HeaderComposite.getOriginatorCNL().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCNL, getText(), em.reqOriginator); HeaderComposite.tOriginatorCNLFocus(); }
 		else if ((!HeaderComposite.getOriginatorCNL().equals("")) && (HeaderComposite.getOriginatorCNL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoOriginator); HeaderComposite.tOriginatorCNLFocus(); }
    	//---------------------------------------- [body mandatory] ----------------------------------------
 		else if (ATSForms.get3a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCNL, getText(), em.req3a); ATSForms.focus3aCNL(); }
        else if (ATSForms.get7a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCNL, getText(), em.req7a); ATSForms.focus7aCNL(); }
        else if (ATSForms.get7b().equals("A") && (ATSForms.get7c().equals(""))) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.req7c); ATSForms.focus7cCNL(); }
        else if (!ATSForms.get7b().equals("A") && (!ATSForms.get7c().equals(""))) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.req7b); }
        else if (!ATSForms.get7c().equals("") && (ATSForms.get7c().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.info7c); ATSForms.focus7cCNL(); }
        else if (ATSForms.get13a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCNL, getText(), em.req13a); ATSForms.focus13aCNL(); }
        else if (ATSForms.get13a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.info13a); ATSForms.focus13aCNL(); }
        else if ((!ATSForms.get13b().equals("")) && (ATSForms.get13b().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.info13b); ATSForms.focus13bCNL(); }
        else if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4 && (time.iHh13>23) || (time.iMM13>59)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoInvalid13b); ATSForms.focus13bCNL(); }
		else if (ATSForms.get16a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCNL, getText(), em.req16a); ATSForms.focus16aCNL(); }
		else if (ATSForms.get16a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.info16a); ATSForms.focus16aCNL(); }
		else if (!ATSForms.get16b().equals("") && ATSForms.get16b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.info16b); ATSForms.focus16bCNL(); }
		else if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4 && (time.iHh16>23) || (time.iMM16>59)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoInvalid16b); ATSForms.focus16bCNL(); }
		else if (!ATSForms.get16c().equals("") && ATSForms.get16c().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.info16c); ATSForms.focus16cCNL(); }
		else if (!ATSForms.get16c2nd().equals("") && ATSForms.get16c2nd().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.info16d); ATSForms.focus16dCNL(); }
		else if (ATSForms.get18tDof().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nCNL, getText(), em.reqDof); ATSForms.focus18tDofCNL(); }
        else if (!ATSForms.get18tDof().isEmpty() && !ATSForms.get18tDof().startsWith("0") && ATSForms.get18tDof().length()<6) { DialogFactory.openWarningDialog(Shells.sh_nCNL, getText(), em.infoInvalidDof); ATSForms.focus18tDofCNL(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().startsWith("0") && ATSForms.get18tDof().length()>1) { DialogFactory.openWarningDialog(Shells.sh_nCNL, getText(), em.infoInvalidDof); ATSForms.focus18tDofCNL(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && (time.sYy.equals("00") || time.sHh.equals("00") || time.sMm.equals("00") || time.iHh>12 || time.iMM>31)) { DialogFactory.openWarningDialog(Shells.sh_nCNL, getText(), em.infoInvalid18Dof); ATSForms.focus18tDofCNL(); }
		else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && Integer.parseInt(datedof) < Integer.parseInt(time.datenow)) { DialogFactory.openInfoDialog(Shells.sh_nCNL, getText(), em.infoInvalid18DofVal); ATSForms.focus18tDofCNL(); }
		else if (ATSForms.getBodyCNL().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nCNL, getText(),em.req(ATSForms.getBodyCNL().length())); }
 		else {
 			// mendapatkan isi masing2 field
			ss.hCNL();ss.s3();ss.s7();ss.s13();ss.s16();
			if (ss.s18Baru.contains("DOF/000000")) ss.s18Baru = ss.s18Baru.replace("DOF/000000", "0");
			if (ss.s18Baru.startsWith(" ")) { ss.s18Baru=ss.s18Baru.replaceFirst("\\s+", ""); }
 			ss.data(ATSForms.getBodyCNL());
 			validasiDuplicateAddress(Shells.sh_nCNL,"CNL");
			// tutup koneksi GetFPL
    		ss.connClose("CNL");
		}
 	}
    
    void sDEP() {
    	ss.sReg=""; ss.sDof=""; ss.s18Baru="";
    	ATSForms.getDEPValue(); time.tgl();
    	String str7a = ATSForms.get7a();
    	String str13a = ATSForms.get13a();
    	String str16a = ATSForms.get16a();
    	ss.sDof = ATSForms.get18tDof();
    	ss.proteksi18();
    	//----------------------------------------------------------------
    	if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4) { time.t13b(ATSForms.get13b()); }
    	if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4) { time.t16b(ATSForms.get16b()); }
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
		if (strDof.startsWith(" ")) { strDof = strDof.replaceFirst("\\s+", ""); }
		if (strDof.compareTo("DOF/000000")==0) strDof = "0";
    	//---------------------------------------- [header mandatory] ----------------------------------------
    	if (HeaderComposite.getPriorityDEP().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nDEP, getText(), em.reqPriority); HeaderComposite.tPriorityDEPFocus(); }
		else if (HeaderComposite.getDest1DEP().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nDEP, getText(), em.reqAddress); HeaderComposite.tDest1DEPFocus(); }
		else if ((!HeaderComposite.getDest1DEP().equals("")) && (HeaderComposite.getDest1DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest1DEPFocus(); }
		else if ((!HeaderComposite.getDest2DEP().equals("")) && (HeaderComposite.getDest2DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest2DEPFocus(); }
		else if ((!HeaderComposite.getDest3DEP().equals("")) && (HeaderComposite.getDest3DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest3DEPFocus(); }
		else if ((!HeaderComposite.getDest4DEP().equals("")) && (HeaderComposite.getDest4DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest4DEPFocus(); }
		else if ((!HeaderComposite.getDest5DEP().equals("")) && (HeaderComposite.getDest5DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest5DEPFocus(); }
		else if ((!HeaderComposite.getDest6DEP().equals("")) && (HeaderComposite.getDest6DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest6DEPFocus(); }
		else if ((!HeaderComposite.getDest7DEP().equals("")) && (HeaderComposite.getDest7DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest7DEPFocus(); }
		else if ((!HeaderComposite.getDest8DEP().equals("")) && (HeaderComposite.getDest8DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest8DEPFocus(); }
		else if ((!HeaderComposite.getDest9DEP().equals("")) && (HeaderComposite.getDest9DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest9DEPFocus(); }
		else if ((!HeaderComposite.getDest10DEP().equals("")) && (HeaderComposite.getDest10DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest10DEPFocus(); }
		else if ((!HeaderComposite.getDest11DEP().equals("")) && (HeaderComposite.getDest11DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest11DEPFocus(); }
		else if ((!HeaderComposite.getDest12DEP().equals("")) && (HeaderComposite.getDest12DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest12DEPFocus(); }
		else if ((!HeaderComposite.getDest13DEP().equals("")) && (HeaderComposite.getDest13DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest13DEPFocus(); }
		else if ((!HeaderComposite.getDest14DEP().equals("")) && (HeaderComposite.getDest14DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest14DEPFocus(); }
		else if ((!HeaderComposite.getDest15DEP().equals("")) && (HeaderComposite.getDest15DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest15DEPFocus(); }
		else if ((!HeaderComposite.getDest16DEP().equals("")) && (HeaderComposite.getDest16DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest16DEPFocus(); }
		else if ((!HeaderComposite.getDest17DEP().equals("")) && (HeaderComposite.getDest17DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest17DEPFocus(); }
		else if ((!HeaderComposite.getDest18DEP().equals("")) && (HeaderComposite.getDest18DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest18DEPFocus(); }
		else if ((!HeaderComposite.getDest19DEP().equals("")) && (HeaderComposite.getDest19DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest19DEPFocus(); }
		else if ((!HeaderComposite.getDest20DEP().equals("")) && (HeaderComposite.getDest20DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest20DEPFocus(); }
		else if ((!HeaderComposite.getDest21DEP().equals("")) && (HeaderComposite.getDest21DEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoAddress); HeaderComposite.tDest21DEPFocus(); }
		else if (HeaderComposite.getOriginatorDEP().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nDEP, getText(), em.reqOriginator); HeaderComposite.tOriginatorDEPFocus(); }
 		else if ((!HeaderComposite.getOriginatorDEP().equals("")) && (HeaderComposite.getOriginatorDEP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoOriginator); HeaderComposite.tOriginatorDEPFocus(); }
    	//---------------------------------------- [body mandatory] ----------------------------------------
 		else if (ATSForms.get3a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nDEP, getText(), em.req3a); ATSForms.focus3aDEP(); }
        else if (ATSForms.get7a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nDEP, getText(), em.req7a); ATSForms.focus7aDEP(); }
        else if (ATSForms.get7b().equals("A") && (ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nDEP, getText(), em.req7c); ATSForms.focus7cDEP(); }
        else if (!ATSForms.get7b().equals("A") && (!ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nDEP, getText(), em.req7b); }
        else if (!ATSForms.get7c().equals("") && (ATSForms.get7c().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.info7c); ATSForms.focus7cDEP(); }
        else if (ATSForms.get13a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nDEP, getText(), em.req13a); ATSForms.focus13aDEP(); }
        else if (ATSForms.get13a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.info13a); ATSForms.focus13aDEP(); }
		else if (ATSForms.get13b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nDEP, getText(), em.req13b); ATSForms.focus13bDEP(); }
		else if (ATSForms.get13b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.info13b); ATSForms.focus13bDEP(); }
		else if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4 && (time.iHh13>23) || (time.iMM13>59)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoInvalid13b); ATSForms.focus13bDEP(); }
		else if (ATSForms.get16a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nDEP, getText(), em.req16a); ATSForms.focus16aDEP(); }
		else if (ATSForms.get16a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.info16a); ATSForms.focus16aDEP(); }
		else if (!ATSForms.get16b().equals("") && ATSForms.get16b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.info16b); ATSForms.focus16bDEP(); }
		else if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4 && (time.iHh16>23) || (time.iMM16>59)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoInvalid16b); ATSForms.focus16bDEP(); }
		else if (!ATSForms.get16c().equals("") && ATSForms.get16c().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.info16c); ATSForms.focus16cDEP(); }
		else if (!ATSForms.get16c2nd().equals("") && ATSForms.get16c2nd().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.info16d); ATSForms.focus16dDEP(); }
		else if (ATSForms.get18tDof().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nDEP, getText(), em.reqDof); ATSForms.focus18tDofDEP(); }
        else if (!ATSForms.get18tDof().isEmpty() && !ATSForms.get18tDof().startsWith("0") && ATSForms.get18tDof().length()<6) { DialogFactory.openWarningDialog(Shells.sh_nDEP, getText(), em.infoInvalidDof); ATSForms.focus18tDofDEP(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().startsWith("0") && ATSForms.get18tDof().length()>1) { DialogFactory.openWarningDialog(Shells.sh_nDEP, getText(), em.infoInvalidDof); ATSForms.focus18tDofDEP(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && (time.sYy.equals("00") || time.sHh.equals("00") || time.sMm.equals("00") || time.iHh>12 || time.iMM>31)) { DialogFactory.openWarningDialog(Shells.sh_nDEP, getText(), em.infoInvalid18Dof); ATSForms.focus18tDofDEP(); }
		else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && Integer.parseInt(datedof) < Integer.parseInt(time.datenow)) { DialogFactory.openInfoDialog(Shells.sh_nDEP, getText(), em.infoInvalid18DofVal); ATSForms.focus18tDofDEP(); }
		else if (ATSForms.getBodyDEP().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nDEP, getText(),em.req(ATSForms.getBodyDEP().length())); }
 		else {
 			ss.kType7a=""; ss.kType13a=""; ss.kType16a="";ss.acft_id="";
			int iJam=0,ikJam=0;
			try {
				iJam = Integer.parseInt(HeaderComposite.getSendAtDEP());
				if (ss.kJam.isEmpty()) ss.kJam="0";
				ikJam = Integer.parseInt(ss.kJam);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			
			ss.depCheck(str7a, str13a, str16a);
			if (ss.kType7a.isEmpty() && str7a.compareTo(ss.kType7a)!=0 && 
					ss.kType13a.isEmpty() && str13a.compareTo(ss.kType13a)!=0 && 
					ss.kType16a.isEmpty() && str16a.compareTo(ss.kType16a)!=0) {
				System.err.println(" -- Condition: 1 -- ");
				DialogFactory.openYesNoDialog(Shells.sh_nDEP, getText(), 
						"FPL with this requirement below is required." +
						"\n\tAircraft Id : "+ATSForms.get7a()+"," +
						"\n\tDEP AD : "+ATSForms.get13a()+" and" +
						"\n\tDEST AD : "+ATSForms.get16a()+
						"\nPlease send the FPL Message first. Send it anyway ?");
				if (DialogFactory.getPenentuan()==true) { insdep(strDof); }
				
		   	} else if (str7a.compareTo(ss.kType7a)==0 &&
					str13a.compareTo(ss.kType13a)==0 &&
					str16a.compareTo(ss.kType16a)==0 && 
					ikJam > iJam) {
		   		System.err.println(" -- Condition: 2 -- ");
		   		DialogFactory.openYesNoDialog(Shells.sh_nDEP, getText(), 
						"FPL with this requirement below is required." +
						"\n\tAircraft Id : "+ATSForms.get7a()+"," +
						"\n\tDEP AD : "+ATSForms.get13a()+" and" +
						"\n\tDEST AD : "+ATSForms.get16a()+
						"\nPlease send the FPL Message first. Send it anyway ?");
				if (DialogFactory.getPenentuan()==true) { insdep(strDof); }
		   	} else { 
		   		insdep(strDof);
	    	}
		}
 	}
    
    void insdep(String strDof) {
    	// mendapatkan isi masing2 field
		ss.hDEP();ss.s3();ss.s7();ss.s13();ss.s16();
		if (ss.s18Baru.contains("DOF/000000")) ss.s18Baru = ss.s18Baru.replace("DOF/000000", "0");
		if (ss.s18Baru.startsWith(" ")) { ss.s18Baru=ss.s18Baru.replaceFirst("\\s+", ""); }	    				
		ss.data(ATSForms.getBodyDEP());         		
		validasiDuplicateAddress(Shells.sh_nDEP,"DEP");
		// tutup koneksi GetFPL
		ss.connClose("DEP");
    }
    
    void sARR() {
    	ATSForms.getARRValue(); time.tgl();
    	//----------------------------------------------------------------
    	if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4) { time.t13b(ATSForms.get13b()); }
    	if (!ATSForms.get17b().isEmpty() && ATSForms.get17b().length()==4) { time.t17b(ATSForms.get17b()); }
    	//---------------------------------------- [header mandatory] ----------------------------------------
    	if (HeaderComposite.getPriorityARR().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nARR, getText(), em.reqPriority); HeaderComposite.tPriorityARRFocus(); }
		else if (HeaderComposite.getDest1ARR().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nARR, getText(), em.reqAddress); HeaderComposite.tDest1ARRFocus(); }
		else if ((!HeaderComposite.getDest1ARR().equals("")) && (HeaderComposite.getDest1ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest1ARRFocus(); }
		else if ((!HeaderComposite.getDest2ARR().equals("")) && (HeaderComposite.getDest2ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest2ARRFocus(); }
		else if ((!HeaderComposite.getDest3ARR().equals("")) && (HeaderComposite.getDest3ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest3ARRFocus(); }
		else if ((!HeaderComposite.getDest4ARR().equals("")) && (HeaderComposite.getDest4ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest4ARRFocus(); }
		else if ((!HeaderComposite.getDest5ARR().equals("")) && (HeaderComposite.getDest5ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest5ARRFocus(); }
		else if ((!HeaderComposite.getDest6ARR().equals("")) && (HeaderComposite.getDest6ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest6ARRFocus(); }
		else if ((!HeaderComposite.getDest7ARR().equals("")) && (HeaderComposite.getDest7ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest7ARRFocus(); }
		else if ((!HeaderComposite.getDest8ARR().equals("")) && (HeaderComposite.getDest8ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest8ARRFocus(); }
		else if ((!HeaderComposite.getDest9ARR().equals("")) && (HeaderComposite.getDest9ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest9ARRFocus(); }
		else if ((!HeaderComposite.getDest10ARR().equals("")) && (HeaderComposite.getDest10ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest10ARRFocus(); }
		else if ((!HeaderComposite.getDest11ARR().equals("")) && (HeaderComposite.getDest11ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest11ARRFocus(); }
		else if ((!HeaderComposite.getDest12ARR().equals("")) && (HeaderComposite.getDest12ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest12ARRFocus(); }
		else if ((!HeaderComposite.getDest13ARR().equals("")) && (HeaderComposite.getDest13ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest13ARRFocus(); }
		else if ((!HeaderComposite.getDest14ARR().equals("")) && (HeaderComposite.getDest14ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest14ARRFocus(); }
		else if ((!HeaderComposite.getDest15ARR().equals("")) && (HeaderComposite.getDest15ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest15ARRFocus(); }
		else if ((!HeaderComposite.getDest16ARR().equals("")) && (HeaderComposite.getDest16ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest16ARRFocus(); }
		else if ((!HeaderComposite.getDest17ARR().equals("")) && (HeaderComposite.getDest17ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest17ARRFocus(); }
		else if ((!HeaderComposite.getDest18ARR().equals("")) && (HeaderComposite.getDest18ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest18ARRFocus(); }
		else if ((!HeaderComposite.getDest19ARR().equals("")) && (HeaderComposite.getDest19ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest19ARRFocus(); }
		else if ((!HeaderComposite.getDest20ARR().equals("")) && (HeaderComposite.getDest20ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest20ARRFocus(); }
		else if ((!HeaderComposite.getDest21ARR().equals("")) && (HeaderComposite.getDest21ARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoAddress); HeaderComposite.tDest21ARRFocus(); }
		else if (HeaderComposite.getOriginatorARR().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nARR, getText(), em.reqOriginator); HeaderComposite.tOriginatorARRFocus(); }
 		else if ((!HeaderComposite.getOriginatorARR().equals("")) && (HeaderComposite.getOriginatorARR().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoOriginator); HeaderComposite.tOriginatorARRFocus(); }
    	//---------------------------------------- [body mandatory] ----------------------------------------
 		else if (ATSForms.get3a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nARR, getText(), em.req3a); ATSForms.focus3aARR(); }
        else if (ATSForms.get7a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nARR, getText(), em.req7a); ATSForms.focus7aARR(); }
        else if (ATSForms.get7b().equals("A") && (ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nARR, getText(), em.req7c); ATSForms.focus7cARR(); }
        else if (!ATSForms.get7b().equals("A") && (!ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nARR, getText(), em.req7b); }
        else if (!ATSForms.get7c().equals("") && (ATSForms.get7c().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.info7c); ATSForms.focus7cARR(); }
        else if (ATSForms.get13a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nARR, getText(), em.req13a); ATSForms.focus13aARR(); }
        else if (ATSForms.get13a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.info13a); ATSForms.focus13aARR(); }
        else if ((!ATSForms.get13b().equals("")) && (ATSForms.get13b().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.info13b); ATSForms.focus13bARR(); }
        else if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4 && (time.iHh13>23) || (time.iMM13>59)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoInvalid13b); ATSForms.focus13bARR(); }
		else if (ATSForms.get17a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nARR, getText(), em.req17a); ATSForms.focus17a(); }
		else if (ATSForms.get17a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.info17a); ATSForms.focus17a(); }
		else if (ATSForms.get17b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nARR, getText(), em.req13b); ATSForms.focus17b(); }
		else if (ATSForms.get17b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.info13b); ATSForms.focus17b(); }
		else if (!ATSForms.get17b().isEmpty() && ATSForms.get17b().length()==4 && (time.iHh17>23) || (time.iMM17>59)) { DialogFactory.openInfoDialog(Shells.sh_nARR, getText(), em.infoInvalid13b); ATSForms.focus17b(); }
		else if ((!ATSForms.get17c().equals("")) && (!ATSForms.get17a().equals("ZZZZ"))) { DialogFactory.openWarningDialog(Shells.sh_nARR, getText(), em.info17cvv); ATSForms.focus17c(); }
		else if (ATSForms.get17c().isEmpty() && ATSForms.get17a().equals("ZZZZ")) { DialogFactory.openWarningDialog(Shells.sh_nARR, getText(), em.info17c); ATSForms.focus17c(); }
		else if (ATSForms.getBodyARR().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nARR, getText(),em.req(ATSForms.getBodyARR().length())); }
 		else {
 			// mendapatkan isi masing2 field
			ss.hARR();ss.s3();ss.s7();ss.s13();ss.s17();
 			ss.data(ATSForms.getBodyARR());
 			validasiDuplicateAddress(Shells.sh_nARR,"ARR");
			// tutup koneksi GetFPL
    		ss.connClose("ARR");
		}
 	}
    
    void sCDN() {
 		ATSForms.getCDNValue(); time.tgl();
    	//----------------------------------------------------------------
    	if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4) { time.t13b(ATSForms.get13b()); }
    	if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4) { time.t16b(ATSForms.get16b()); }
    	//---------------------------------------- [header mandatory] ----------------------------------------
    	if (HeaderComposite.getPriorityCDN().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCDN, getText(), em.reqPriority); HeaderComposite.tPriorityCDNFocus(); }
		else if (HeaderComposite.getDest1CDN().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCDN, getText(), em.reqAddress); HeaderComposite.tDest1CDNFocus(); }
		else if ((!HeaderComposite.getDest1CDN().equals("")) && (HeaderComposite.getDest1CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest1CDNFocus(); }
		else if ((!HeaderComposite.getDest2CDN().equals("")) && (HeaderComposite.getDest2CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest2CDNFocus(); }
		else if ((!HeaderComposite.getDest3CDN().equals("")) && (HeaderComposite.getDest3CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest3CDNFocus(); }
		else if ((!HeaderComposite.getDest4CDN().equals("")) && (HeaderComposite.getDest4CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest4CDNFocus(); }
		else if ((!HeaderComposite.getDest5CDN().equals("")) && (HeaderComposite.getDest5CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest5CDNFocus(); }
		else if ((!HeaderComposite.getDest6CDN().equals("")) && (HeaderComposite.getDest6CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest6CDNFocus(); }
		else if ((!HeaderComposite.getDest7CDN().equals("")) && (HeaderComposite.getDest7CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest7CDNFocus(); }
		else if ((!HeaderComposite.getDest8CDN().equals("")) && (HeaderComposite.getDest8CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest8CDNFocus(); }
		else if ((!HeaderComposite.getDest9CDN().equals("")) && (HeaderComposite.getDest9CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest9CDNFocus(); }
		else if ((!HeaderComposite.getDest10CDN().equals("")) && (HeaderComposite.getDest10CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest10CDNFocus(); }
		else if ((!HeaderComposite.getDest11CDN().equals("")) && (HeaderComposite.getDest11CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest11CDNFocus(); }
		else if ((!HeaderComposite.getDest12CDN().equals("")) && (HeaderComposite.getDest12CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest12CDNFocus(); }
		else if ((!HeaderComposite.getDest13CDN().equals("")) && (HeaderComposite.getDest13CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest13CDNFocus(); }
		else if ((!HeaderComposite.getDest14CDN().equals("")) && (HeaderComposite.getDest14CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest14CDNFocus(); }
		else if ((!HeaderComposite.getDest15CDN().equals("")) && (HeaderComposite.getDest15CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest15CDNFocus(); }
		else if ((!HeaderComposite.getDest16CDN().equals("")) && (HeaderComposite.getDest16CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest16CDNFocus(); }
		else if ((!HeaderComposite.getDest17CDN().equals("")) && (HeaderComposite.getDest17CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest17CDNFocus(); }
		else if ((!HeaderComposite.getDest18CDN().equals("")) && (HeaderComposite.getDest18CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest18CDNFocus(); }
		else if ((!HeaderComposite.getDest19CDN().equals("")) && (HeaderComposite.getDest19CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest19CDNFocus(); }
		else if ((!HeaderComposite.getDest20CDN().equals("")) && (HeaderComposite.getDest20CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest20CDNFocus(); }
		else if ((!HeaderComposite.getDest21CDN().equals("")) && (HeaderComposite.getDest21CDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoAddress); HeaderComposite.tDest21CDNFocus(); }
		else if (HeaderComposite.getOriginatorCDN().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCDN, getText(), em.reqOriginator); HeaderComposite.tOriginatorCDNFocus(); }
 		else if ((!HeaderComposite.getOriginatorCDN().equals("")) && (HeaderComposite.getOriginatorCDN().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoOriginator); HeaderComposite.tOriginatorCDNFocus(); }
    	//---------------------------------------- [body mandatory] ----------------------------------------
 		else if (ATSForms.get3a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCDN, getText(), em.req3a); ATSForms.focus3aCDN(); }
        else if (ATSForms.get7a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCDN, getText(), em.req7a); ATSForms.focus7aCDN(); }
        else if (ATSForms.get7b().equals("A") && (ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nCDN, getText(), em.req7c); ATSForms.focus7cCDN(); }
        else if (!ATSForms.get7b().equals("A") && (!ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nCDN, getText(), em.req7b); }
        else if (!ATSForms.get7c().equals("") && (ATSForms.get7c().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.info7c); ATSForms.focus7cCDN(); }
        else if (ATSForms.get13a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCDN, getText(), em.req13a); ATSForms.focus13aCDN(); }
        else if (ATSForms.get13a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.info13a); ATSForms.focus13aCDN(); }
		else if (!ATSForms.get13b().equals("") && ATSForms.get13b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.info13b); ATSForms.focus13bCDN(); }
		else if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4 && (time.iHh13>23) || (time.iMM13>59)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoInvalid13b); ATSForms.focus13bCDN(); }
		else if (ATSForms.get16a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.info16a); ATSForms.focus16aCDN(); }
		else if (!ATSForms.get16b().equals("") && ATSForms.get16b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.info16b); ATSForms.focus16bCDN(); }
		else if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4 && (time.iHh16>23) || (time.iMM16>59)) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.infoInvalid16b); ATSForms.focus16bCDN(); }
		else if (!ATSForms.get16c().equals("") && ATSForms.get16c().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.info16c); ATSForms.focus16cCDN(); }
		else if (!ATSForms.get16c2nd().equals("") && ATSForms.get16c2nd().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCDN, getText(), em.info16d); ATSForms.focus16dCDN(); }
		else if (ATSForms.get22().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCDN, getText(), em.req22); ATSForms.focus22CDN(); }
		else if (ATSForms.getBodyCDN().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nCDN, getText(),em.req(ATSForms.getBodyCDN().length())); }
 		else {
 			// mendapatkan isi masing2 field
			ss.hCDN();ss.s3();ss.s7();ss.s13();ss.s16();ss.s22();
 			ss.data(ATSForms.getBodyCDN());
 			validasiDuplicateAddress(Shells.sh_nCDN,"CDN");
			// tutup koneksi GetFPL
    		ss.connClose("CDN");
		}
 	}
    
    void sCPL() {
    	ATSForms.getCPLValue(); time.tgl();
    	
    	String t9b = ATSForms.get9b();
    	ss.cek9b(t9b, data);
    	
    	ss.t10b=""; ss.sdbguv=""; ss.kriteria=""; ss.sprotek10a=""; ss.sprotekpbn=""; ss.s18Baru="";
    	ss.t10b = ATSForms.get10bBaru();
    	ss.sReg = ATSForms.get18tReg();
    	ss.sDof = ATSForms.get18tDof();
    	ss.tpbn = ATSForms.get18tPbn();
    	
    	t14c = ATSForms.get14c();
		t14d = ATSForms.get14d();
		time.t14(t14c, t14d);
		t14e = ATSForms.get14e();
		
    	t15a = ATSForms.get15a();
    	t15b = ATSForms.get15b();
		time.t15(t15a, t15b);
		
		ss.t10a = ATSForms.get10a();
		ss.validasi10a(ss.t10a);
		ss.validasi18_10(ss.t10b, ss.t10a);
    	ss.proteksi18();
    	ss.validasipbn(ss.tpbn);
    	
    	if (!ss.s_Reg.isEmpty()) ss.s_Reg = ss.s_Reg.replace(" REG/", "");
    	if (!ss.s_Dof.isEmpty()) ss.s_Dof = ss.s_Dof.replace(" DOF/", "");
    	//----------------------------------------------------------------
    	if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4) { time.t13b(ATSForms.get13b()); }
    	if (!ATSForms.get14b().isEmpty() && ATSForms.get14b().length()==4) { time.t14b(ATSForms.get14b()); }
		if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4) { time.t16b(ATSForms.get16b()); }
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
    	if (HeaderComposite.getPriorityCPL().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.reqPriority); HeaderComposite.tPriorityCPLFocus(); }
		else if (HeaderComposite.getDest1CPL().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.reqAddress); HeaderComposite.tDest1CPLFocus(); }
		else if ((!HeaderComposite.getDest1CPL().equals("")) && (HeaderComposite.getDest1CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest1CPLFocus(); }
		else if ((!HeaderComposite.getDest2CPL().equals("")) && (HeaderComposite.getDest2CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest2CPLFocus(); }
		else if ((!HeaderComposite.getDest3CPL().equals("")) && (HeaderComposite.getDest3CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest3CPLFocus(); }
		else if ((!HeaderComposite.getDest4CPL().equals("")) && (HeaderComposite.getDest4CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest4CPLFocus(); }
		else if ((!HeaderComposite.getDest5CPL().equals("")) && (HeaderComposite.getDest5CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest5CPLFocus(); }
		else if ((!HeaderComposite.getDest6CPL().equals("")) && (HeaderComposite.getDest6CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest6CPLFocus(); }
		else if ((!HeaderComposite.getDest7CPL().equals("")) && (HeaderComposite.getDest7CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest7CPLFocus(); }
		else if ((!HeaderComposite.getDest8CPL().equals("")) && (HeaderComposite.getDest8CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest8CPLFocus(); }
		else if ((!HeaderComposite.getDest9CPL().equals("")) && (HeaderComposite.getDest9CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest9CPLFocus(); }
		else if ((!HeaderComposite.getDest10CPL().equals("")) && (HeaderComposite.getDest10CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest10CPLFocus(); }
		else if ((!HeaderComposite.getDest11CPL().equals("")) && (HeaderComposite.getDest11CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest11CPLFocus(); }
		else if ((!HeaderComposite.getDest12CPL().equals("")) && (HeaderComposite.getDest12CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest12CPLFocus(); }
		else if ((!HeaderComposite.getDest13CPL().equals("")) && (HeaderComposite.getDest13CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest13CPLFocus(); }
		else if ((!HeaderComposite.getDest14CPL().equals("")) && (HeaderComposite.getDest14CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest14CPLFocus(); }
		else if ((!HeaderComposite.getDest15CPL().equals("")) && (HeaderComposite.getDest15CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest15CPLFocus(); }
		else if ((!HeaderComposite.getDest16CPL().equals("")) && (HeaderComposite.getDest16CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest16CPLFocus(); }
		else if ((!HeaderComposite.getDest17CPL().equals("")) && (HeaderComposite.getDest17CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest17CPLFocus(); }
		else if ((!HeaderComposite.getDest18CPL().equals("")) && (HeaderComposite.getDest18CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest18CPLFocus(); }
		else if ((!HeaderComposite.getDest19CPL().equals("")) && (HeaderComposite.getDest19CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest19CPLFocus(); }
		else if ((!HeaderComposite.getDest20CPL().equals("")) && (HeaderComposite.getDest20CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest20CPLFocus(); }
		else if ((!HeaderComposite.getDest21CPL().equals("")) && (HeaderComposite.getDest21CPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoAddress); HeaderComposite.tDest21CPLFocus(); }
		else if (HeaderComposite.getOriginatorCPL().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.reqOriginator); HeaderComposite.tOriginatorCPLFocus(); }
 		else if ((!HeaderComposite.getOriginatorCPL().equals("")) && (HeaderComposite.getOriginatorCPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoOriginator); HeaderComposite.tOriginatorCPLFocus(); }
    	//---------------------------------------- [body mandatory] ----------------------------------------
 		else if (ATSForms.get3a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req3a); ATSForms.focus3aCPL(); }
        //7
 		else if (ATSForms.get7a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req7a); ATSForms.focus7aCPL(); }
        else if (ATSForms.get7b().equals("A") && (ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req7c); ATSForms.focus7cCPL(); }
        else if (!ATSForms.get7b().equals("A") && (!ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req7b); }
        else if (!ATSForms.get7c().equals("") && (ATSForms.get7c().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.info7c); ATSForms.focus7cCPL(); }
        //8
        else if (ATSForms.get8a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req8a); ATSForms.focus8aCPL(); }
        else if (ATSForms.get8b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req8b); ATSForms.focus8bCPL(); }
        //9
        else if (t9b.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req9b); ATSForms.focus9bCPL(); }
        else if (t9b.length() < 2) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.info9b); ATSForms.focus9bCPL(); }
        else if (ATSForms.get9c().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req9c); ATSForms.focus9cCPL(); }
        else if (!ss.wtc.equals("") && ATSForms.get9c().compareTo(ss.wtc) != 0) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(),em.wtc(ss.wtc, t9b)); ATSForms.focus9cCPL(); }
    	//10
		else if (ss.t10a.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req10); ATSForms.focus10aCPL(); }
		else if (!ss.t10a.equals("") && (ss.sprotek10a.contains("ngaco"))) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoInvalid10aFix); ATSForms.focus10aCPL(); }		
		else if (ss.t10b.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req10); ATSForms.focus10bBaruCPL(); }
		else if (!ss.t10b.equals("") && (ss.sdbguv.contains("tdksesuai"))) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoInvalid10b+"\nInsert B1 or B2 or U1 or U2 or V1 or V2 or D1 or G1"); ATSForms.focus10bBaruCPL(); }		
		else if ((ss.t10b.contains("B1") || ss.t10b.contains("U1") || ss.t10b.contains("V1")) && (ss.t10b.contains("B2") || ss.t10b.contains("U2") || ss.t10b.contains("V2"))) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoInvalid10b); ATSForms.focus10bBaruCPL(); }
		else if (ss.t10a.contains("N") && ss.t10a.contains("S")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalid10a); ATSForms.focus10aCPL(); }
		else if (ss.t10b.contains("N") && (ss.t10b.contains("A") || ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X") || ss.t10b.contains("B1") || ss.t10b.contains("B2") || ss.t10b.contains("U1") || ss.t10b.contains("U2") || ss.t10b.contains("V1") || ss.t10b.contains("V2") || ss.t10b.contains("D1") || ss.t10b.contains("G1"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(),"Invalid EQUIPMENT AND CAPABILITIES value.\n\nSSR Modes A and C and SSR Mode S are not allowed,\nif 'N' has inserted into field EQUIPMENT AND CAPABILITIES and vice versa.\n\nSee the instruction below the EQUIPMENT AND CAPABILITIES text !"); ATSForms.focus10bBaruCPL(); }
		else if (ss.t10a.contains("W") && ATSForms.get18tSts().contains("NONRVSM")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalid10aSTS); ATSForms.focus18tStsCPL(); }
    	//13
		else if (ATSForms.get13a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req13a); ATSForms.focus13aCPL(); }
        else if (ATSForms.get13a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.info13a); ATSForms.focus13aCPL(); }
		else if (!ATSForms.get13b().equals("") && ATSForms.get13b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.info13b); ATSForms.focus13bCPL(); }
		else if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4 && (time.iHh13>23) || (time.iMM13>59)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoInvalid13b); ATSForms.focus13bCPL(); }
		//14
        else if (ATSForms.get14a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req14a); ATSForms.focus14aCPL(); }
        else if (ATSForms.get14a().length() < 2) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.info14a); ATSForms.focus14aCPL(); }
        else if (ATSForms.get14b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req14b); ATSForms.focus14bCPL(); }
		else if (ATSForms.get14b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.info14b); ATSForms.focus14bCPL(); }
		else if (!ATSForms.get14b().equals("") && ATSForms.get14b().length()==4 && (time.iHh14>23) || (time.iMM14>59)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoInvalid14b); ATSForms.focus14bCPL(); }
		else if (ATSForms.get14c().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req14c); ATSForms.focus14cCPL(); }
		else if ((!t14c.startsWith("F")) && (!t14c.startsWith("S")) && (!t14c.startsWith("A")) && (!t14c.startsWith("M"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalid14c); ATSForms.focus14cCPL(); }
		else if (((t14c.startsWith("S")) || (t14c.startsWith("M"))) && (t14c.length()<5)) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cSM); ATSForms.focus14cCPL(); }
		else if (((t14c.startsWith("S")) || (t14c.startsWith("M"))) && (!time.t14c2.startsWith("0")) && (!time.t14c2.startsWith("1")) && (!time.t14c2.startsWith("2")) && (!time.t14c2.startsWith("3")) && (!time.t14c2.startsWith("4")) && (!time.t14c2.startsWith("5")) && (!time.t14c2.startsWith("6")) && (!time.t14c2.startsWith("7")) && (!time.t14c2.startsWith("8")) && (!time.t14c2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cSM); ATSForms.focus14cCPL(); }
		else if (((t14c.startsWith("S")) || (t14c.startsWith("M"))) && (!time.t14c3.startsWith("0")) && (!time.t14c3.startsWith("1")) && (!time.t14c3.startsWith("2")) && (!time.t14c3.startsWith("3")) && (!time.t14c3.startsWith("4")) && (!time.t14c3.startsWith("5")) && (!time.t14c3.startsWith("6")) && (!time.t14c3.startsWith("7")) && (!time.t14c3.startsWith("8")) && (!time.t14c3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cSM); ATSForms.focus14cCPL(); }
		else if (((t14c.startsWith("S")) || (t14c.startsWith("M"))) && (!time.t14c4.startsWith("0")) && (!time.t14c4.startsWith("1")) && (!time.t14c4.startsWith("2")) && (!time.t14c4.startsWith("3")) && (!time.t14c4.startsWith("4")) && (!time.t14c4.startsWith("5")) && (!time.t14c4.startsWith("6")) && (!time.t14c4.startsWith("7")) && (!time.t14c4.startsWith("8")) && (!time.t14c4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cSM); ATSForms.focus14cCPL(); }
		else if (((t14c.startsWith("S")) || (t14c.startsWith("M"))) && (!time.t14c5.startsWith("0")) && (!time.t14c5.startsWith("1")) && (!time.t14c5.startsWith("2")) && (!time.t14c5.startsWith("3")) && (!time.t14c5.startsWith("4")) && (!time.t14c5.startsWith("5")) && (!time.t14c5.startsWith("6")) && (!time.t14c5.startsWith("7")) && (!time.t14c5.startsWith("8")) && (!time.t14c5.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cSM); ATSForms.focus14cCPL(); }
		else if (((t14c.startsWith("F")) || (t14c.startsWith("A"))) && ((t14c.length()>4) || (t14c.length()<4))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cFA); ATSForms.focus14cCPL(); }
		else if (((t14c.startsWith("F")) || (t14c.startsWith("A"))) && (!time.t14c2.startsWith("0")) && (!time.t14c2.startsWith("1")) && (!time.t14c2.startsWith("2")) && (!time.t14c2.startsWith("3")) && (!time.t14c2.startsWith("4")) && (!time.t14c2.startsWith("5")) && (!time.t14c2.startsWith("6")) && (!time.t14c2.startsWith("7")) && (!time.t14c2.startsWith("8")) && (!time.t14c2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cFA); ATSForms.focus14cCPL(); }
		else if (((t14c.startsWith("F")) || (t14c.startsWith("A"))) && (!time.t14c3.startsWith("0")) && (!time.t14c3.startsWith("1")) && (!time.t14c3.startsWith("2")) && (!time.t14c3.startsWith("3")) && (!time.t14c3.startsWith("4")) && (!time.t14c3.startsWith("5")) && (!time.t14c3.startsWith("6")) && (!time.t14c3.startsWith("7")) && (!time.t14c3.startsWith("8")) && (!time.t14c3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cFA); ATSForms.focus14cCPL(); }
		else if (((t14c.startsWith("F")) || (t14c.startsWith("A"))) && (!time.t14c4.startsWith("0")) && (!time.t14c4.startsWith("1")) && (!time.t14c4.startsWith("2")) && (!time.t14c4.startsWith("3")) && (!time.t14c4.startsWith("4")) && (!time.t14c4.startsWith("5")) && (!time.t14c4.startsWith("6")) && (!time.t14c4.startsWith("7")) && (!time.t14c4.startsWith("8")) && (!time.t14c4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cFA); ATSForms.focus14cCPL(); }
		else if ((!t14d.equals("")) && (!t14d.startsWith("F")) && (!t14d.startsWith("S")) && (!t14d.startsWith("A")) && (!t14d.startsWith("M"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalid14d); ATSForms.focus14dCPL(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("S")) || (t14d.startsWith("M"))) && (t14d.length()<5)) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cSM); ATSForms.focus14dCPL(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("S")) || (t14d.startsWith("M"))) && (!time.t14d2.startsWith("0")) && (!time.t14d2.startsWith("1")) && (!time.t14d2.startsWith("2")) && (!time.t14d2.startsWith("3")) && (!time.t14d2.startsWith("4")) && (!time.t14d2.startsWith("5")) && (!time.t14d2.startsWith("6")) && (!time.t14d2.startsWith("7")) && (!time.t14d2.startsWith("8")) && (!time.t14d2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cSM); ATSForms.focus14dCPL(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("S")) || (t14d.startsWith("M"))) && (!time.t14d3.startsWith("0")) && (!time.t14d3.startsWith("1")) && (!time.t14d3.startsWith("2")) && (!time.t14d3.startsWith("3")) && (!time.t14d3.startsWith("4")) && (!time.t14d3.startsWith("5")) && (!time.t14d3.startsWith("6")) && (!time.t14d3.startsWith("7")) && (!time.t14d3.startsWith("8")) && (!time.t14d3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cSM); ATSForms.focus14dCPL(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("S")) || (t14d.startsWith("M"))) && (!time.t14d4.startsWith("0")) && (!time.t14d4.startsWith("1")) && (!time.t14d4.startsWith("2")) && (!time.t14d4.startsWith("3")) && (!time.t14d4.startsWith("4")) && (!time.t14d4.startsWith("5")) && (!time.t14d4.startsWith("6")) && (!time.t14d4.startsWith("7")) && (!time.t14d4.startsWith("8")) && (!time.t14d4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cSM); ATSForms.focus14dCPL(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("S")) || (t14d.startsWith("M"))) && (!time.t14d5.startsWith("0")) && (!time.t14d5.startsWith("1")) && (!time.t14d5.startsWith("2")) && (!time.t14d5.startsWith("3")) && (!time.t14d5.startsWith("4")) && (!time.t14d5.startsWith("5")) && (!time.t14d5.startsWith("6")) && (!time.t14d5.startsWith("7")) && (!time.t14d5.startsWith("8")) && (!time.t14d5.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cSM); ATSForms.focus14dCPL(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("F")) || (t14d.startsWith("A"))) && ((t14d.length()>4) || (t14d.length()<4))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cFA); ATSForms.focus14dCPL(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("F")) || (t14d.startsWith("A"))) && (!time.t14d2.startsWith("0")) && (!time.t14d2.startsWith("1")) && (!time.t14d2.startsWith("2")) && (!time.t14d2.startsWith("3")) && (!time.t14d2.startsWith("4")) && (!time.t14d2.startsWith("5")) && (!time.t14d2.startsWith("6")) && (!time.t14d2.startsWith("7")) && (!time.t14d2.startsWith("8")) && (!time.t14d2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cFA); ATSForms.focus14dCPL(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("F")) || (t14d.startsWith("A"))) && (!time.t14d3.startsWith("0")) && (!time.t14d3.startsWith("1")) && (!time.t14d3.startsWith("2")) && (!time.t14d3.startsWith("3")) && (!time.t14d3.startsWith("4")) && (!time.t14d3.startsWith("5")) && (!time.t14d3.startsWith("6")) && (!time.t14d3.startsWith("7")) && (!time.t14d3.startsWith("8")) && (!time.t14d3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cFA); ATSForms.focus14dCPL(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("F")) || (t14d.startsWith("A"))) && (!time.t14d4.startsWith("0")) && (!time.t14d4.startsWith("1")) && (!time.t14d4.startsWith("2")) && (!time.t14d4.startsWith("3")) && (!time.t14d4.startsWith("4")) && (!time.t14d4.startsWith("5")) && (!time.t14d4.startsWith("6")) && (!time.t14d4.startsWith("7")) && (!time.t14d4.startsWith("8")) && (!time.t14d4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cFA); ATSForms.focus14dCPL(); }
		else if ((!t14e.equals("")) && (!t14e.equals("A")) && (!t14e.equals("B"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalid14e); ATSForms.focus14eCPL(); }
    	//15
		else if (ATSForms.get15a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req15a); ATSForms.focus15aCPL(); }
		else if ((!t15a.startsWith("K")) && (!t15a.startsWith("N")) && (!t15a.startsWith("M"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalid15a); ATSForms.focus15aCPL(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (t15a.length()<5)) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info15aKN); ATSForms.focus15aCPL(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (!time.t15a2.startsWith("0")) && (!time.t15a2.startsWith("1")) && (!time.t15a2.startsWith("2")) && (!time.t15a2.startsWith("3")) && (!time.t15a2.startsWith("4")) && (!time.t15a2.startsWith("5")) && (!time.t15a2.startsWith("6")) && (!time.t15a2.startsWith("7")) && (!time.t15a2.startsWith("8")) && (!time.t15a2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info15aKN); ATSForms.focus15aCPL(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (!time.t15a3.startsWith("0")) && (!time.t15a3.startsWith("1")) && (!time.t15a3.startsWith("2")) && (!time.t15a3.startsWith("3")) && (!time.t15a3.startsWith("4")) && (!time.t15a3.startsWith("5")) && (!time.t15a3.startsWith("6")) && (!time.t15a3.startsWith("7")) && (!time.t15a3.startsWith("8")) && (!time.t15a3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info15aKN); ATSForms.focus15aCPL(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (!time.t15a4.startsWith("0")) && (!time.t15a4.startsWith("1")) && (!time.t15a4.startsWith("2")) && (!time.t15a4.startsWith("3")) && (!time.t15a4.startsWith("4")) && (!time.t15a4.startsWith("5")) && (!time.t15a4.startsWith("6")) && (!time.t15a4.startsWith("7")) && (!time.t15a4.startsWith("8")) && (!time.t15a4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info15aKN); ATSForms.focus15aCPL(); }
		else if (((t15a.startsWith("K")) || (t15a.startsWith("N"))) && (!time.t15a5.startsWith("0")) && (!time.t15a5.startsWith("1")) && (!time.t15a5.startsWith("2")) && (!time.t15a5.startsWith("3")) && (!time.t15a5.startsWith("4")) && (!time.t15a5.startsWith("5")) && (!time.t15a5.startsWith("6")) && (!time.t15a5.startsWith("7")) && (!time.t15a5.startsWith("8")) && (!time.t15a5.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info15aKN); ATSForms.focus15aCPL(); }
		else if ((t15a.startsWith("M")) && ((t15a.length()>4) || (t15a.length()<4))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info15aM); ATSForms.focus15aCPL(); }
		else if (((t15a.startsWith("M"))) && (!time.t15a2.startsWith("0")) && (!time.t15a2.startsWith("1")) && (!time.t15a2.startsWith("2")) && (!time.t15a2.startsWith("3")) && (!time.t15a2.startsWith("4")) && (!time.t15a2.startsWith("5")) && (!time.t15a2.startsWith("6")) && (!time.t15a2.startsWith("7")) && (!time.t15a2.startsWith("8")) && (!time.t15a2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info15aM); ATSForms.focus15aCPL(); }
		else if (((t15a.startsWith("M"))) && (!time.t15a3.startsWith("0")) && (!time.t15a3.startsWith("1")) && (!time.t15a3.startsWith("2")) && (!time.t15a3.startsWith("3")) && (!time.t15a3.startsWith("4")) && (!time.t15a3.startsWith("5")) && (!time.t15a3.startsWith("6")) && (!time.t15a3.startsWith("7")) && (!time.t15a3.startsWith("8")) && (!time.t15a3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info15aM); ATSForms.focus15aCPL(); }
		else if (((t15a.startsWith("M"))) && (!time.t15a4.startsWith("0")) && (!time.t15a4.startsWith("1")) && (!time.t15a4.startsWith("2")) && (!time.t15a4.startsWith("3")) && (!time.t15a4.startsWith("4")) && (!time.t15a4.startsWith("5")) && (!time.t15a4.startsWith("6")) && (!time.t15a4.startsWith("7")) && (!time.t15a4.startsWith("8")) && (!time.t15a4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info15aM); ATSForms.focus15aCPL(); }
		else if (ATSForms.get15b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req15b); ATSForms.focus15bCPL(); }
		else if ((!t15b.startsWith("F")) && (!t15b.startsWith("S")) && (!t15b.startsWith("A")) && (!t15b.startsWith("M")) && (!t15b.equals("VFR"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalid15b); ATSForms.focus15bCPL(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (t15b.length()<5)) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cSM); ATSForms.focus15bCPL(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (!time.t15b2.startsWith("0")) && (!time.t15b2.startsWith("1")) && (!time.t15b2.startsWith("2")) && (!time.t15b2.startsWith("3")) && (!time.t15b2.startsWith("4")) && (!time.t15b2.startsWith("5")) && (!time.t15b2.startsWith("6")) && (!time.t15b2.startsWith("7")) && (!time.t15b2.startsWith("8")) && (!time.t15b2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cSM); ATSForms.focus15bCPL(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (!time.t15b3.startsWith("0")) && (!time.t15b3.startsWith("1")) && (!time.t15b3.startsWith("2")) && (!time.t15b3.startsWith("3")) && (!time.t15b3.startsWith("4")) && (!time.t15b3.startsWith("5")) && (!time.t15b3.startsWith("6")) && (!time.t15b3.startsWith("7")) && (!time.t15b3.startsWith("8")) && (!time.t15b3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cSM); ATSForms.focus15bCPL(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (!time.t15b4.startsWith("0")) && (!time.t15b4.startsWith("1")) && (!time.t15b4.startsWith("2")) && (!time.t15b4.startsWith("3")) && (!time.t15b4.startsWith("4")) && (!time.t15b4.startsWith("5")) && (!time.t15b4.startsWith("6")) && (!time.t15b4.startsWith("7")) && (!time.t15b4.startsWith("8")) && (!time.t15b4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cSM); ATSForms.focus15bCPL(); }
		else if (((t15b.startsWith("S")) || (t15b.startsWith("M"))) && (!time.t15b5.startsWith("0")) && (!time.t15b5.startsWith("1")) && (!time.t15b5.startsWith("2")) && (!time.t15b5.startsWith("3")) && (!time.t15b5.startsWith("4")) && (!time.t15b5.startsWith("5")) && (!time.t15b5.startsWith("6")) && (!time.t15b5.startsWith("7")) && (!time.t15b5.startsWith("8")) && (!time.t15b5.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cSM); ATSForms.focus15bCPL(); }
		else if (((t15b.startsWith("F")) || (t15b.startsWith("A"))) && ((t15b.length()>4) || (t15b.length()<4))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cFA); ATSForms.focus15bCPL(); }
		else if (((t15b.startsWith("F")) || (t15b.startsWith("A"))) && (!time.t15b2.startsWith("0")) && (!time.t15b2.startsWith("1")) && (!time.t15b2.startsWith("2")) && (!time.t15b2.startsWith("3")) && (!time.t15b2.startsWith("4")) && (!time.t15b2.startsWith("5")) && (!time.t15b2.startsWith("6")) && (!time.t15b2.startsWith("7")) && (!time.t15b2.startsWith("8")) && (!time.t15b2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cFA); ATSForms.focus15bCPL(); }
		else if (((t15b.startsWith("F")) || (t15b.startsWith("A"))) && (!time.t15b3.startsWith("0")) && (!time.t15b3.startsWith("1")) && (!time.t15b3.startsWith("2")) && (!time.t15b3.startsWith("3")) && (!time.t15b3.startsWith("4")) && (!time.t15b3.startsWith("5")) && (!time.t15b3.startsWith("6")) && (!time.t15b3.startsWith("7")) && (!time.t15b3.startsWith("8")) && (!time.t15b3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cFA); ATSForms.focus15bCPL(); }
		else if (((t15b.startsWith("F")) || (t15b.startsWith("A"))) && (!time.t15b4.startsWith("0")) && (!time.t15b4.startsWith("1")) && (!time.t15b4.startsWith("2")) && (!time.t15b4.startsWith("3")) && (!time.t15b4.startsWith("4")) && (!time.t15b4.startsWith("5")) && (!time.t15b4.startsWith("6")) && (!time.t15b4.startsWith("7")) && (!time.t15b4.startsWith("8")) && (!time.t15b4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info14cFA); ATSForms.focus15bCPL(); }
		else if (ATSForms.get15c().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req15c); ATSForms.focus15cCPL(); }
    	//16
		else if (ATSForms.get16a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.req16a); ATSForms.focus16aCPL(); }
		else if (ATSForms.get16a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.info16a); ATSForms.focus16aCPL(); }
		else if (!ATSForms.get16b().equals("") && ATSForms.get16b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.info16b); ATSForms.focus16bCPL(); }
		else if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4 && (time.iHh16>23) || (time.iMM16>59)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoInvalid16b); ATSForms.focus16bCPL(); }
		else if (!ATSForms.get16c().equals("") && ATSForms.get16c().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.info16c); ATSForms.focus16cCPL(); }
		else if (!ATSForms.get16c2nd().equals("") && ATSForms.get16c2nd().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.info16d); ATSForms.focus16dCPL(); }
    	//tambahan
		else if (!ATSForms.get16c2nd().isEmpty()&&ATSForms.get16c2nd().length()==4 && ATSForms.get16c().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info16cd); ATSForms.focus16cCPL(); }
    	//18 indicator TYP/
        else if (ATSForms.get9b().equals("ZZZZ") && ATSForms.get18tTyp().equals("")) {  DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoTyp9b); ATSForms.focus18tTypCPL(); }
        else if (!ATSForms.get9b().contains("ZZZZ") && !ATSForms.get18tTyp().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info9bTyp); ATSForms.focus9bCPL(); }
    	//indicator COM/, DAT/, NAV/, SUR/ [link ke Item 10]
        else if (ss.t10a.contains("Z") && ss.s10aZ.contains("ngaco"))  { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoCom10); ATSForms.focus18tComCPL(); }
        else if (ss.kriteria.contains("ngaco") && ATSForms.get18tSur().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoSur10); ATSForms.focus18tSurCPL(); }
    	//indicator DEP/
        else if ((ATSForms.get13a().equals("ZZZZ") || ATSForms.get13a().equals("AFIL")) && ATSForms.get18tDep().equals("")) {  DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoDep13); ATSForms.focus18tDepCPL(); }
        else if (!ATSForms.get13a().contains("ZZZZ") && !ATSForms.get13a().contains("AFIL") && !ATSForms.get18tDep().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info13Dep); ATSForms.focus13aCPL(); }
    	//indicator DEST/
        else if (ATSForms.get16a().equals("ZZZZ") && ATSForms.get18tDest().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoDest16); ATSForms.focus18tDestCPL(); }
        else if (!ATSForms.get16a().contains("ZZZZ") && !ATSForms.get18tDest().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info16Dest); ATSForms.focus16aCPL(); }
    	//indicator ALTN/
        else if (ATSForms.get16c().equals("ZZZZ") && ATSForms.get18tAltn().equals("")) {  DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoAltn16); ATSForms.focus18tAltnCPL(); }
        else if (!ATSForms.get16c().contains("ZZZZ") && !ATSForms.get18tAltn().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info16Altn); ATSForms.focus16cCPL(); }
    	//indicator REG/, DOF/
        else if (!ATSForms.get18tReg().isEmpty() && ATSForms.get18tReg().equals(ATSForms.get7a())) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalidReg); ATSForms.focus18tRegCPL(); }
        else if (!ATSForms.get18tReg().isEmpty() && !ATSForms.get18tOpr().equals("") && ATSForms.get18tReg().equals(ATSForms.get18tOpr())) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalidOpr); ATSForms.focus18tOprCPL(); }
        else if (!ATSForms.get18tReg().isEmpty() && (ATSForms.get18tReg().length() > 7 || ATSForms.get18tReg().contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalidReg); ATSForms.focus18tRegCPL(); }
		else if (ATSForms.get18tDof().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.reqDof); ATSForms.focus18tDofCPL(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()<6) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalidDof); ATSForms.focus18tDofCPL(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && (time.sYy.equals("00") || time.sHh.equals("00") || time.sMm.equals("00") || time.iHh>12 || time.iMM>31)) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalid18Dof); ATSForms.focus18tDofCPL(); }
		else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && Integer.parseInt(datedof) < Integer.parseInt(time.datenow)) { DialogFactory.openInfoDialog(Shells.sh_nCPL, getText(), em.infoInvalid18DofVal); ATSForms.focus18tDofCPL(); }
		//indicator STS/, PER/, CODE/, SEL/, PBN/
        else if (!ATSForms.get18tSts().equals("") && ss.criteria_sts.contains("ngaco")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalidSts); ATSForms.focus18tStsCPL(); }
        else if (!ATSForms.get18tPer().equals("") && ATSForms.get18tPer().length() > 1) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalidPer); ATSForms.focus18tPerCPL(); }
        else if (!ATSForms.get18tCode().equals("") && (ATSForms.get18tCode().length() > 6 || ATSForms.get18tCode().contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalidCode); ATSForms.focus18tCodeCPL(); }
        else if (!ATSForms.get18tSel().equals("") && (ATSForms.get18tSel().length() > 4 || ATSForms.get18tSel().contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalidSel); ATSForms.focus18tSelCPL(); }
    	//PBN
        else if (!ss.tpbn.equals("") && (ss.tpbn.length() > 16 || ss.tpbn.contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalidPbn); ATSForms.focus18tPbnCPL(); }
        else if (!ss.tpbn.equals("") && ss.sprotekpbn.contains("ngaco")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoInvalidPbnFix); ATSForms.focus18tPbnCPL(); }
		else if (ss.t10a.contains("R") && ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.infoPbn10); ATSForms.focus18tPbnCPL(); }
		else if (!ss.t10a.contains("R") && !ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info10Pbn); ATSForms.focus10aCPL(); }
    	//G
		else if (!ss.t10a.contains("G") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info10G); ATSForms.focus10aCPL(); }
		else if (!ss.t10a.contains("G") && !ss.krit2.equals("") && (ss.krit2.contains("B2") || ss.krit2.contains("C2") || ss.krit2.contains("D2") || ss.krit2.contains("O2"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info10G); ATSForms.focus10aCPL(); }
		//D
		else if (!ss.t10a.contains("D") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info10D); ATSForms.focus10aCPL(); }
		else if (!ss.t10a.contains("D") && !ss.krit3.equals("") && (ss.krit3.contains("B3") || ss.krit3.contains("C3") || ss.krit3.contains("D3") || ss.krit3.contains("O3"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info10D); ATSForms.focus10aCPL(); }
    	//I
		else if (!ss.t10a.contains("I") && !ss.krit5.equals("")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info10I); ATSForms.focus10aCPL(); }
		else if (!ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1"))) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info10I); ATSForms.focus10aCPL(); }
		//DI
		else if (!ss.t10a.contains("D") && !ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info10DI); ATSForms.focus10aCPL(); }
		else if (!ss.t10a.contains("D") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info10DI); ATSForms.focus10aCPL(); }
		else if (!ss.t10a.contains("I") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info10DI); ATSForms.focus10aCPL(); }
		else if (!ss.t10a.contains("I") && (ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info10DI); ATSForms.focus10aCPL(); }
    	//OD / SD
		else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit1.contains("B1")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info10OD); ATSForms.focus10aCPL(); }
		else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info10OD); ATSForms.focus10aCPL(); }
		else if (!ss.t10a.contains("D") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info10OD); ATSForms.focus10aCPL(); }
		else if (!ss.t10a.contains("D") && ss.krit1.contains("B4")) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(), em.info10OD); ATSForms.focus10aCPL(); }
    	//length
		else if (ATSForms.getBodyCPL().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nCPL, getText(),em.req(ATSForms.getBodyCPL().length())); }
 		else {
			// mendapatkan isi masing2 field
			ss.hCPL();ss.s3();ss.s7();ss.s8();ss.s9();ss.s13();ss.s14();ss.s15();ss.s16();
 			ss.data(ATSForms.getBodyCPL());
 			if (ss.toa.compareTo("") == 0) { //NOT REGISTERED TYPE OF AIRCRAFT
				DialogFactory.openYesNoDialog(Shells.sh_nCPL, "Type of Aircraft","Type of aircraft is not registered. Send it anyway ?");
	
				boolean tentu = DialogFactory.getPenentuan(); 
				if (tentu == true) {
					inscpl(strDof);
				} //end of if == tentu
				else { System.out.println(">> Sending has been canceled."); }
			} //end of toa=""
			else { //REGISTERED TYPE OF AIRCRAFT
				inscpl(strDof);
			}
		}
    }
    
    void inscpl(String strDof) {
    	if ((ss.sReg.compareTo("REG/0")==0 || ss.sReg.compareTo("")==0) &&  (ss.sDof.compareTo("DOF/0")==0 || ss.sDof.compareTo("")==0) && ss.s18Baru.compareTo("")==0) { ss.s18Baru="0"; }
    	validasiDuplicateAddress(Shells.sh_nCPL,"CPL");
		// tutup koneksi GetRoute
		ss.connClose("CPL");
    }
    
    void sEST() {
 		ATSForms.getESTValue(); time.tgl();
    	t14c = ATSForms.get14c();
		t14d = ATSForms.get14d();
		time.t14(t14c, t14d);
		t14e = ATSForms.get14e();
		//----------------------------------------------------------------
    	if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4) { time.t13b(ATSForms.get13b()); }
    	if (!ATSForms.get14b().isEmpty() && ATSForms.get14b().length()==4) { time.t14b(ATSForms.get14b()); }
    	if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4) { time.t16b(ATSForms.get16b()); }
    	//---------------------------------------- [header mandatory] ----------------------------------------
    	if (HeaderComposite.getPriorityEST().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.reqPriority); HeaderComposite.tPriorityESTFocus(); }
		else if (HeaderComposite.getDest1EST().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.reqAddress); HeaderComposite.tDest1ESTFocus(); }
		else if ((!HeaderComposite.getDest1EST().equals("")) && (HeaderComposite.getDest1EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest1ESTFocus(); }
		else if ((!HeaderComposite.getDest2EST().equals("")) && (HeaderComposite.getDest2EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest2ESTFocus(); }
		else if ((!HeaderComposite.getDest3EST().equals("")) && (HeaderComposite.getDest3EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest3ESTFocus(); }
		else if ((!HeaderComposite.getDest4EST().equals("")) && (HeaderComposite.getDest4EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest4ESTFocus(); }
		else if ((!HeaderComposite.getDest5EST().equals("")) && (HeaderComposite.getDest5EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest5ESTFocus(); }
		else if ((!HeaderComposite.getDest6EST().equals("")) && (HeaderComposite.getDest6EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest6ESTFocus(); }
		else if ((!HeaderComposite.getDest7EST().equals("")) && (HeaderComposite.getDest7EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest7ESTFocus(); }
		else if ((!HeaderComposite.getDest8EST().equals("")) && (HeaderComposite.getDest8EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest8ESTFocus(); }
		else if ((!HeaderComposite.getDest9EST().equals("")) && (HeaderComposite.getDest9EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest9ESTFocus(); }
		else if ((!HeaderComposite.getDest10EST().equals("")) && (HeaderComposite.getDest10EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest10ESTFocus(); }
		else if ((!HeaderComposite.getDest11EST().equals("")) && (HeaderComposite.getDest11EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest11ESTFocus(); }
		else if ((!HeaderComposite.getDest12EST().equals("")) && (HeaderComposite.getDest12EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest12ESTFocus(); }
		else if ((!HeaderComposite.getDest13EST().equals("")) && (HeaderComposite.getDest13EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest13ESTFocus(); }
		else if ((!HeaderComposite.getDest14EST().equals("")) && (HeaderComposite.getDest14EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest14ESTFocus(); }
		else if ((!HeaderComposite.getDest15EST().equals("")) && (HeaderComposite.getDest15EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest15ESTFocus(); }
		else if ((!HeaderComposite.getDest16EST().equals("")) && (HeaderComposite.getDest16EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest16ESTFocus(); }
		else if ((!HeaderComposite.getDest17EST().equals("")) && (HeaderComposite.getDest17EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest17ESTFocus(); }
		else if ((!HeaderComposite.getDest18EST().equals("")) && (HeaderComposite.getDest18EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest18ESTFocus(); }
		else if ((!HeaderComposite.getDest19EST().equals("")) && (HeaderComposite.getDest19EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest19ESTFocus(); }
		else if ((!HeaderComposite.getDest20EST().equals("")) && (HeaderComposite.getDest20EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest20ESTFocus(); }
		else if ((!HeaderComposite.getDest21EST().equals("")) && (HeaderComposite.getDest21EST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoAddress); HeaderComposite.tDest21ESTFocus(); }
		else if (HeaderComposite.getOriginatorEST().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.reqOriginator); HeaderComposite.tOriginatorESTFocus(); }
 		else if ((!HeaderComposite.getOriginatorEST().equals("")) && (HeaderComposite.getOriginatorEST().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoOriginator); HeaderComposite.tOriginatorESTFocus(); }
    	//---------------------------------------- [body mandatory] ----------------------------------------
 		else if (ATSForms.get3a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.req3a); ATSForms.focus3aEST(); }
        else if (ATSForms.get7a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.req7a); ATSForms.focus7aEST(); }
        else if (ATSForms.get7b().equals("A") && (ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.req7c); ATSForms.focus7cEST(); }
        else if (!ATSForms.get7b().equals("A") && (!ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.req7b); }
        else if (!ATSForms.get7c().equals("") && (ATSForms.get7c().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.info7c); ATSForms.focus7cEST(); }
        else if (ATSForms.get13a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.req13a); ATSForms.focus13aEST(); }
        else if (ATSForms.get13a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.info13a); ATSForms.focus13aEST(); }
		else if (!ATSForms.get13b().equals("") && ATSForms.get13b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.info13b); ATSForms.focus13bEST(); }
		else if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4 && (time.iHh13>23) || (time.iMM13>59)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoInvalid13b); ATSForms.focus13bEST(); }
		else if (ATSForms.get14a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.req14a); ATSForms.focus14aEST(); }
        else if (ATSForms.get14a().length() < 2) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.info14a); ATSForms.focus14aEST(); }
        else if (ATSForms.get14b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.req14b); ATSForms.focus14bEST(); }
		else if (ATSForms.get14b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.info14b); ATSForms.focus14bEST(); }
		else if (!ATSForms.get14b().equals("") && ATSForms.get14b().length()==4 && (time.iHh14>23) || (time.iMM14>59)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoInvalid14b); ATSForms.focus14bEST(); }
		else if (ATSForms.get14c().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.req14c); ATSForms.focus14cEST(); }
		else if ((!t14c.startsWith("F")) && (!t14c.startsWith("S")) && (!t14c.startsWith("A")) && (!t14c.startsWith("M"))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.infoInvalid14c); ATSForms.focus14cEST(); }
		else if (((t14c.startsWith("S")) || (t14c.startsWith("M"))) && (t14c.length()<5)) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cSM); ATSForms.focus14cEST(); }
		else if (((t14c.startsWith("S")) || (t14c.startsWith("M"))) && (!time.t14c2.startsWith("0")) && (!time.t14c2.startsWith("1")) && (!time.t14c2.startsWith("2")) && (!time.t14c2.startsWith("3")) && (!time.t14c2.startsWith("4")) && (!time.t14c2.startsWith("5")) && (!time.t14c2.startsWith("6")) && (!time.t14c2.startsWith("7")) && (!time.t14c2.startsWith("8")) && (!time.t14c2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cSM); ATSForms.focus14cEST(); }
		else if (((t14c.startsWith("S")) || (t14c.startsWith("M"))) && (!time.t14c3.startsWith("0")) && (!time.t14c3.startsWith("1")) && (!time.t14c3.startsWith("2")) && (!time.t14c3.startsWith("3")) && (!time.t14c3.startsWith("4")) && (!time.t14c3.startsWith("5")) && (!time.t14c3.startsWith("6")) && (!time.t14c3.startsWith("7")) && (!time.t14c3.startsWith("8")) && (!time.t14c3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cSM); ATSForms.focus14cEST(); }
		else if (((t14c.startsWith("S")) || (t14c.startsWith("M"))) && (!time.t14c4.startsWith("0")) && (!time.t14c4.startsWith("1")) && (!time.t14c4.startsWith("2")) && (!time.t14c4.startsWith("3")) && (!time.t14c4.startsWith("4")) && (!time.t14c4.startsWith("5")) && (!time.t14c4.startsWith("6")) && (!time.t14c4.startsWith("7")) && (!time.t14c4.startsWith("8")) && (!time.t14c4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cSM); ATSForms.focus14cEST(); }
		else if (((t14c.startsWith("S")) || (t14c.startsWith("M"))) && (!time.t14c5.startsWith("0")) && (!time.t14c5.startsWith("1")) && (!time.t14c5.startsWith("2")) && (!time.t14c5.startsWith("3")) && (!time.t14c5.startsWith("4")) && (!time.t14c5.startsWith("5")) && (!time.t14c5.startsWith("6")) && (!time.t14c5.startsWith("7")) && (!time.t14c5.startsWith("8")) && (!time.t14c5.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cSM); ATSForms.focus14cEST(); }
		else if (((t14c.startsWith("F")) || (t14c.startsWith("A"))) && ((t14c.length()>4) || (t14c.length()<4))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cFA); ATSForms.focus14cEST(); }
		else if (((t14c.startsWith("F")) || (t14c.startsWith("A"))) && (!time.t14c2.startsWith("0")) && (!time.t14c2.startsWith("1")) && (!time.t14c2.startsWith("2")) && (!time.t14c2.startsWith("3")) && (!time.t14c2.startsWith("4")) && (!time.t14c2.startsWith("5")) && (!time.t14c2.startsWith("6")) && (!time.t14c2.startsWith("7")) && (!time.t14c2.startsWith("8")) && (!time.t14c2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cFA); ATSForms.focus14cEST(); }
		else if (((t14c.startsWith("F")) || (t14c.startsWith("A"))) && (!time.t14c3.startsWith("0")) && (!time.t14c3.startsWith("1")) && (!time.t14c3.startsWith("2")) && (!time.t14c3.startsWith("3")) && (!time.t14c3.startsWith("4")) && (!time.t14c3.startsWith("5")) && (!time.t14c3.startsWith("6")) && (!time.t14c3.startsWith("7")) && (!time.t14c3.startsWith("8")) && (!time.t14c3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cFA); ATSForms.focus14cEST(); }
		else if (((t14c.startsWith("F")) || (t14c.startsWith("A"))) && (!time.t14c4.startsWith("0")) && (!time.t14c4.startsWith("1")) && (!time.t14c4.startsWith("2")) && (!time.t14c4.startsWith("3")) && (!time.t14c4.startsWith("4")) && (!time.t14c4.startsWith("5")) && (!time.t14c4.startsWith("6")) && (!time.t14c4.startsWith("7")) && (!time.t14c4.startsWith("8")) && (!time.t14c4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cFA); ATSForms.focus14cEST(); }
		else if ((!t14d.equals("")) && (!t14d.startsWith("F")) && (!t14d.startsWith("S")) && (!t14d.startsWith("A")) && (!t14d.startsWith("M"))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.infoInvalid14d); ATSForms.focus14dEST(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("S")) || (t14d.startsWith("M"))) && (t14d.length()<5)) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cSM); ATSForms.focus14dEST(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("S")) || (t14d.startsWith("M"))) && (!time.t14d2.startsWith("0")) && (!time.t14d2.startsWith("1")) && (!time.t14d2.startsWith("2")) && (!time.t14d2.startsWith("3")) && (!time.t14d2.startsWith("4")) && (!time.t14d2.startsWith("5")) && (!time.t14d2.startsWith("6")) && (!time.t14d2.startsWith("7")) && (!time.t14d2.startsWith("8")) && (!time.t14d2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cSM); ATSForms.focus14dEST(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("S")) || (t14d.startsWith("M"))) && (!time.t14d3.startsWith("0")) && (!time.t14d3.startsWith("1")) && (!time.t14d3.startsWith("2")) && (!time.t14d3.startsWith("3")) && (!time.t14d3.startsWith("4")) && (!time.t14d3.startsWith("5")) && (!time.t14d3.startsWith("6")) && (!time.t14d3.startsWith("7")) && (!time.t14d3.startsWith("8")) && (!time.t14d3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cSM); ATSForms.focus14dEST(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("S")) || (t14d.startsWith("M"))) && (!time.t14d4.startsWith("0")) && (!time.t14d4.startsWith("1")) && (!time.t14d4.startsWith("2")) && (!time.t14d4.startsWith("3")) && (!time.t14d4.startsWith("4")) && (!time.t14d4.startsWith("5")) && (!time.t14d4.startsWith("6")) && (!time.t14d4.startsWith("7")) && (!time.t14d4.startsWith("8")) && (!time.t14d4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cSM); ATSForms.focus14dEST(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("S")) || (t14d.startsWith("M"))) && (!time.t14d5.startsWith("0")) && (!time.t14d5.startsWith("1")) && (!time.t14d5.startsWith("2")) && (!time.t14d5.startsWith("3")) && (!time.t14d5.startsWith("4")) && (!time.t14d5.startsWith("5")) && (!time.t14d5.startsWith("6")) && (!time.t14d5.startsWith("7")) && (!time.t14d5.startsWith("8")) && (!time.t14d5.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cSM); ATSForms.focus14dEST(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("F")) || (t14d.startsWith("A"))) && ((t14d.length()>4) || (t14d.length()<4))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cFA); ATSForms.focus14dEST(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("F")) || (t14d.startsWith("A"))) && (!time.t14d2.startsWith("0")) && (!time.t14d2.startsWith("1")) && (!time.t14d2.startsWith("2")) && (!time.t14d2.startsWith("3")) && (!time.t14d2.startsWith("4")) && (!time.t14d2.startsWith("5")) && (!time.t14d2.startsWith("6")) && (!time.t14d2.startsWith("7")) && (!time.t14d2.startsWith("8")) && (!time.t14d2.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cFA); ATSForms.focus14dEST(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("F")) || (t14d.startsWith("A"))) && (!time.t14d3.startsWith("0")) && (!time.t14d3.startsWith("1")) && (!time.t14d3.startsWith("2")) && (!time.t14d3.startsWith("3")) && (!time.t14d3.startsWith("4")) && (!time.t14d3.startsWith("5")) && (!time.t14d3.startsWith("6")) && (!time.t14d3.startsWith("7")) && (!time.t14d3.startsWith("8")) && (!time.t14d3.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cFA); ATSForms.focus14dEST(); }
		else if ((!t14d.equals("")) && ((t14d.startsWith("F")) || (t14d.startsWith("A"))) && (!time.t14d4.startsWith("0")) && (!time.t14d4.startsWith("1")) && (!time.t14d4.startsWith("2")) && (!time.t14d4.startsWith("3")) && (!time.t14d4.startsWith("4")) && (!time.t14d4.startsWith("5")) && (!time.t14d4.startsWith("6")) && (!time.t14d4.startsWith("7")) && (!time.t14d4.startsWith("8")) && (!time.t14d4.startsWith("9"))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.info14cFA); ATSForms.focus14dEST(); }
		else if ((!t14e.equals("")) && (!t14e.equals("A")) && (!t14e.equals("B"))) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.infoInvalid14e); ATSForms.focus14eEST(); }
		else if (ATSForms.get16a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(), em.req16a); ATSForms.focus16aEST(); }
		else if (ATSForms.get16a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.info16a); ATSForms.focus16aEST(); }
		else if (!ATSForms.get16b().equals("") && ATSForms.get16b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.info16b); ATSForms.focus16bEST(); }
		else if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4 && (time.iHh16>23) || (time.iMM16>59)) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.infoInvalid16b); ATSForms.focus16bEST(); }
		else if (!ATSForms.get16c().equals("") && ATSForms.get16c().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.info16c); ATSForms.focus16cEST(); }
		else if (!ATSForms.get16c2nd().equals("") && ATSForms.get16c2nd().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nEST, getText(), em.info16d); ATSForms.focus16dEST(); }
		else if (ATSForms.getBodyEST().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nEST, getText(),em.req(ATSForms.getBodyEST().length())); }
 		else {
 			// mendapatkan isi masing2 field
			ss.hEST();ss.s3();ss.s7();ss.s13();ss.s14();ss.s16();
 			ss.data(ATSForms.getBodyEST());
 			validasiDuplicateAddress(Shells.sh_nEST,"EST");
			// tutup koneksi GetFPL
    		ss.connClose("EST");
		}
 	}
    
    void sACP() {
 		ATSForms.getACPValue(); time.tgl();
    	//----------------------------------------------------------------
    	if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4) { time.t13b(ATSForms.get13b()); }
    	if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4) { time.t16b(ATSForms.get16b()); }
    	//---------------------------------------- [header mandatory] ----------------------------------------
    	if (HeaderComposite.getPriorityACP().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nACP, getText(), em.reqPriority); HeaderComposite.tPriorityACPFocus(); }
		else if (HeaderComposite.getDest1ACP().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nACP, getText(), em.reqAddress); HeaderComposite.tDest1ACPFocus(); }
		else if ((!HeaderComposite.getDest1ACP().equals("")) && (HeaderComposite.getDest1ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest1ACPFocus(); }
		else if ((!HeaderComposite.getDest2ACP().equals("")) && (HeaderComposite.getDest2ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest2ACPFocus(); }
		else if ((!HeaderComposite.getDest3ACP().equals("")) && (HeaderComposite.getDest3ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest3ACPFocus(); }
		else if ((!HeaderComposite.getDest4ACP().equals("")) && (HeaderComposite.getDest4ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest4ACPFocus(); }
		else if ((!HeaderComposite.getDest5ACP().equals("")) && (HeaderComposite.getDest5ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest5ACPFocus(); }
		else if ((!HeaderComposite.getDest6ACP().equals("")) && (HeaderComposite.getDest6ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest6ACPFocus(); }
		else if ((!HeaderComposite.getDest7ACP().equals("")) && (HeaderComposite.getDest7ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest7ACPFocus(); }
		else if ((!HeaderComposite.getDest8ACP().equals("")) && (HeaderComposite.getDest8ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest8ACPFocus(); }
		else if ((!HeaderComposite.getDest9ACP().equals("")) && (HeaderComposite.getDest9ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest9ACPFocus(); }
		else if ((!HeaderComposite.getDest10ACP().equals("")) && (HeaderComposite.getDest10ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest10ACPFocus(); }
		else if ((!HeaderComposite.getDest11ACP().equals("")) && (HeaderComposite.getDest11ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest11ACPFocus(); }
		else if ((!HeaderComposite.getDest12ACP().equals("")) && (HeaderComposite.getDest12ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest12ACPFocus(); }
		else if ((!HeaderComposite.getDest13ACP().equals("")) && (HeaderComposite.getDest13ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest13ACPFocus(); }
		else if ((!HeaderComposite.getDest14ACP().equals("")) && (HeaderComposite.getDest14ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest14ACPFocus(); }
		else if ((!HeaderComposite.getDest15ACP().equals("")) && (HeaderComposite.getDest15ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest15ACPFocus(); }
		else if ((!HeaderComposite.getDest16ACP().equals("")) && (HeaderComposite.getDest16ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest16ACPFocus(); }
		else if ((!HeaderComposite.getDest17ACP().equals("")) && (HeaderComposite.getDest17ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest17ACPFocus(); }
		else if ((!HeaderComposite.getDest18ACP().equals("")) && (HeaderComposite.getDest18ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest18ACPFocus(); }
		else if ((!HeaderComposite.getDest19ACP().equals("")) && (HeaderComposite.getDest19ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest19ACPFocus(); }
		else if ((!HeaderComposite.getDest20ACP().equals("")) && (HeaderComposite.getDest20ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest20ACPFocus(); }
		else if ((!HeaderComposite.getDest21ACP().equals("")) && (HeaderComposite.getDest21ACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoAddress); HeaderComposite.tDest21ACPFocus(); }
		else if (HeaderComposite.getOriginatorACP().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nACP, getText(), em.reqOriginator); HeaderComposite.tOriginatorACPFocus(); }
 		else if ((!HeaderComposite.getOriginatorACP().equals("")) && (HeaderComposite.getOriginatorACP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoOriginator); HeaderComposite.tOriginatorACPFocus(); }
    	//---------------------------------------- [body mandatory] ----------------------------------------
 		else if (ATSForms.get3a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nACP, getText(), em.req3a); ATSForms.focus3aACP(); }
        else if (ATSForms.get7a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nACP, getText(), em.req7a); ATSForms.focus7aACP(); }
        else if (ATSForms.get7b().equals("A") && (ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nACP, getText(), em.req7c); ATSForms.focus7cACP(); }
        else if (!ATSForms.get7b().equals("A") && (!ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nACP, getText(), em.req7b); }
        else if (!ATSForms.get7c().equals("") && (ATSForms.get7c().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.info7c); ATSForms.focus7cACP(); }
        else if (ATSForms.get13a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nACP, getText(), em.req13a); ATSForms.focus13aACP(); }
        else if (ATSForms.get13a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.info13a); ATSForms.focus13aACP(); }
		else if (!ATSForms.get13b().equals("") && ATSForms.get13b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.info13b); ATSForms.focus13bACP(); }
        else if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4 && (time.iHh13>23) || (time.iMM13>59)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoInvalid13b); ATSForms.focus13bACP(); }
		else if (ATSForms.get16a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nACP, getText(), em.req16a); ATSForms.focus16aACP(); }
		else if (ATSForms.get16a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.info16a); ATSForms.focus16aACP(); }
		else if (!ATSForms.get16b().equals("") && ATSForms.get16b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.info16b); ATSForms.focus16bACP(); }
		else if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4 && (time.iHh16>23) || (time.iMM16>59)) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.infoInvalid16b); ATSForms.focus16bACP(); }
		else if (!ATSForms.get16c().equals("") && ATSForms.get16c().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.info16c); ATSForms.focus16cACP(); }
		else if (!ATSForms.get16c2nd().equals("") && ATSForms.get16c2nd().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nACP, getText(), em.info16d); ATSForms.focus16dACP(); }
		else if (ATSForms.getBodyACP().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nACP, getText(),em.req(ATSForms.getBodyACP().length())); }
 		else {
 			// mendapatkan isi masing2 field
			ss.hACP();ss.s3();ss.s7();ss.s13();ss.s16();
 			ss.data(ATSForms.getBodyACP());
 			validasiDuplicateAddress(Shells.sh_nACP,"ACP");
			// tutup koneksi GetFPL
    		ss.connClose("ACP");
 		}
 	}
    
    void sLAM() {
 		ATSForms.getLAMValue(); time.tgl();
    	//---------------------------------------- [header mandatory] ----------------------------------------
    	if (HeaderComposite.getPriorityLAM().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nLAM, getText(), em.reqPriority); HeaderComposite.tPriorityLAMFocus(); }
		else if (HeaderComposite.getDest1LAM().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nLAM, getText(), em.reqAddress); HeaderComposite.tDest1LAMFocus(); }
		else if ((!HeaderComposite.getDest1LAM().equals("")) && (HeaderComposite.getDest1LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest1LAMFocus(); }
		else if ((!HeaderComposite.getDest2LAM().equals("")) && (HeaderComposite.getDest2LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest2LAMFocus(); }
		else if ((!HeaderComposite.getDest3LAM().equals("")) && (HeaderComposite.getDest3LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest3LAMFocus(); }
		else if ((!HeaderComposite.getDest4LAM().equals("")) && (HeaderComposite.getDest4LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest4LAMFocus(); }
		else if ((!HeaderComposite.getDest5LAM().equals("")) && (HeaderComposite.getDest5LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest5LAMFocus(); }
		else if ((!HeaderComposite.getDest6LAM().equals("")) && (HeaderComposite.getDest6LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest6LAMFocus(); }
		else if ((!HeaderComposite.getDest7LAM().equals("")) && (HeaderComposite.getDest7LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest7LAMFocus(); }
		else if ((!HeaderComposite.getDest8LAM().equals("")) && (HeaderComposite.getDest8LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest8LAMFocus(); }
		else if ((!HeaderComposite.getDest9LAM().equals("")) && (HeaderComposite.getDest9LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest9LAMFocus(); }
		else if ((!HeaderComposite.getDest10LAM().equals("")) && (HeaderComposite.getDest10LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest10LAMFocus(); }
		else if ((!HeaderComposite.getDest11LAM().equals("")) && (HeaderComposite.getDest11LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest11LAMFocus(); }
		else if ((!HeaderComposite.getDest12LAM().equals("")) && (HeaderComposite.getDest12LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest12LAMFocus(); }
		else if ((!HeaderComposite.getDest13LAM().equals("")) && (HeaderComposite.getDest13LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest13LAMFocus(); }
		else if ((!HeaderComposite.getDest14LAM().equals("")) && (HeaderComposite.getDest14LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest14LAMFocus(); }
		else if ((!HeaderComposite.getDest15LAM().equals("")) && (HeaderComposite.getDest15LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest15LAMFocus(); }
		else if ((!HeaderComposite.getDest16LAM().equals("")) && (HeaderComposite.getDest16LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest16LAMFocus(); }
		else if ((!HeaderComposite.getDest17LAM().equals("")) && (HeaderComposite.getDest17LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest17LAMFocus(); }
		else if ((!HeaderComposite.getDest18LAM().equals("")) && (HeaderComposite.getDest18LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest18LAMFocus(); }
		else if ((!HeaderComposite.getDest19LAM().equals("")) && (HeaderComposite.getDest19LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest19LAMFocus(); }
		else if ((!HeaderComposite.getDest20LAM().equals("")) && (HeaderComposite.getDest20LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest20LAMFocus(); }
		else if ((!HeaderComposite.getDest21LAM().equals("")) && (HeaderComposite.getDest21LAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoAddress); HeaderComposite.tDest21LAMFocus(); }
		else if (HeaderComposite.getOriginatorLAM().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nLAM, getText(), em.reqOriginator); HeaderComposite.tOriginatorLAMFocus(); }
 		else if ((!HeaderComposite.getOriginatorLAM().equals("")) && (HeaderComposite.getOriginatorLAM().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nLAM, getText(), em.infoOriginator); HeaderComposite.tOriginatorLAMFocus(); }
    	//---------------------------------------- [body mandatory] ----------------------------------------
 		else if (ATSForms.get3a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nLAM, getText(), em.req3a); ATSForms.focus3aLAM(); }
		else if (ATSForms.get3b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nLAM, getText(), em.req3b); ATSForms.focus3bLAM(); }
		else if (ATSForms.get3c().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nLAM, getText(), em.req3c); ATSForms.focus3cLAM(); }
		else if (ATSForms.getBodyLAM().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nLAM, getText(),em.req(ATSForms.getBodyLAM().length())); }
 		else {
 			// mendapatkan isi masing2 field
			ss.hLAM();ss.s3();
 			ss.data(ATSForms.getBodyLAM());
 			validasiDuplicateAddress(Shells.sh_nLAM,"LAM");
		}
    }
    
	void sRQP() {
 		ss.sReg=""; ss.sDof=""; ss.s18Baru="";
    	ATSForms.getRQPValue(); time.tgl();
    	ss.sDof = ATSForms.get18tDof();
    	ss.proteksi18();
    	//----------------------------------------------------------------
    	if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4) { time.t13b(ATSForms.get13b()); }
    	if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4) { time.t16b(ATSForms.get16b()); }
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
		if (strDof.startsWith(" ")) { strDof = strDof.replaceFirst("\\s+", ""); }
		if (strDof.compareTo("DOF/000000")==0) strDof = "0";
    	//---------------------------------------- [header mandatory] ----------------------------------------
    	if (HeaderComposite.getPriorityRQP().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRQP, getText(), em.reqPriority); HeaderComposite.tPriorityRQPFocus(); }
		else if (HeaderComposite.getDest1RQP().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRQP, getText(), em.reqAddress); HeaderComposite.tDest1RQPFocus(); }
		else if ((!HeaderComposite.getDest1RQP().equals("")) && (HeaderComposite.getDest1RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest1RQPFocus(); }
		else if ((!HeaderComposite.getDest2RQP().equals("")) && (HeaderComposite.getDest2RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest2RQPFocus(); }
		else if ((!HeaderComposite.getDest3RQP().equals("")) && (HeaderComposite.getDest3RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest3RQPFocus(); }
		else if ((!HeaderComposite.getDest4RQP().equals("")) && (HeaderComposite.getDest4RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest4RQPFocus(); }
		else if ((!HeaderComposite.getDest5RQP().equals("")) && (HeaderComposite.getDest5RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest5RQPFocus(); }
		else if ((!HeaderComposite.getDest6RQP().equals("")) && (HeaderComposite.getDest6RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest6RQPFocus(); }
		else if ((!HeaderComposite.getDest7RQP().equals("")) && (HeaderComposite.getDest7RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest7RQPFocus(); }
		else if ((!HeaderComposite.getDest8RQP().equals("")) && (HeaderComposite.getDest8RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest8RQPFocus(); }
		else if ((!HeaderComposite.getDest9RQP().equals("")) && (HeaderComposite.getDest9RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest9RQPFocus(); }
		else if ((!HeaderComposite.getDest10RQP().equals("")) && (HeaderComposite.getDest10RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest10RQPFocus(); }
		else if ((!HeaderComposite.getDest11RQP().equals("")) && (HeaderComposite.getDest11RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest11RQPFocus(); }
		else if ((!HeaderComposite.getDest12RQP().equals("")) && (HeaderComposite.getDest12RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest12RQPFocus(); }
		else if ((!HeaderComposite.getDest13RQP().equals("")) && (HeaderComposite.getDest13RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest13RQPFocus(); }
		else if ((!HeaderComposite.getDest14RQP().equals("")) && (HeaderComposite.getDest14RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest14RQPFocus(); }
		else if ((!HeaderComposite.getDest15RQP().equals("")) && (HeaderComposite.getDest15RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest15RQPFocus(); }
		else if ((!HeaderComposite.getDest16RQP().equals("")) && (HeaderComposite.getDest16RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest16RQPFocus(); }
		else if ((!HeaderComposite.getDest17RQP().equals("")) && (HeaderComposite.getDest17RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest17RQPFocus(); }
		else if ((!HeaderComposite.getDest18RQP().equals("")) && (HeaderComposite.getDest18RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest18RQPFocus(); }
		else if ((!HeaderComposite.getDest19RQP().equals("")) && (HeaderComposite.getDest19RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest19RQPFocus(); }
		else if ((!HeaderComposite.getDest20RQP().equals("")) && (HeaderComposite.getDest20RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest20RQPFocus(); }
		else if ((!HeaderComposite.getDest21RQP().equals("")) && (HeaderComposite.getDest21RQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoAddress); HeaderComposite.tDest21RQPFocus(); }
		else if (HeaderComposite.getOriginatorRQP().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRQP, getText(), em.reqOriginator); HeaderComposite.tOriginatorRQPFocus(); }
 		else if ((!HeaderComposite.getOriginatorRQP().equals("")) && (HeaderComposite.getOriginatorRQP().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoOriginator); HeaderComposite.tOriginatorRQPFocus(); }
    	//---------------------------------------- [body mandatory] ----------------------------------------
 		else if (ATSForms.get3a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRQP, getText(), em.req3a); ATSForms.focus3aRQP(); }
        else if (ATSForms.get7a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRQP, getText(), em.req7a); ATSForms.focus7aRQP(); }
        else if (ATSForms.get7b().equals("A") && (ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nRQP, getText(), em.req7c); ATSForms.focus7cRQP(); }
        else if (!ATSForms.get7b().equals("A") && (!ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nRQP, getText(), em.req7b); }
        else if (!ATSForms.get7c().equals("") && (ATSForms.get7c().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.info7c); ATSForms.focus7cRQP(); }
        else if (ATSForms.get13a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRQP, getText(), em.req13a); ATSForms.focus13aRQP(); }
        else if (ATSForms.get13a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.info13a); ATSForms.focus13aRQP(); }
		else if ((!ATSForms.get13b().equals("")) && (ATSForms.get13b().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.info13b); ATSForms.focus13bRQP(); }
		else if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4 && (time.iHh13>23) || (time.iMM13>59)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoInvalid13b); ATSForms.focus13bRQP(); }
		else if (ATSForms.get16a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRQP, getText(), em.req16a); ATSForms.focus16aRQP(); }
		else if (ATSForms.get16a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.info16a); ATSForms.focus16aRQP(); }
		else if (!ATSForms.get16b().equals("") && ATSForms.get16b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.info16b); ATSForms.focus16bRQP(); }
		else if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4 && (time.iHh16>23) || (time.iMM16>59)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoInvalid16b); ATSForms.focus16bRQP(); }
		else if (!ATSForms.get16c().equals("") && ATSForms.get16c().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.info16c); ATSForms.focus16cRQP(); }
		else if (!ATSForms.get16c2nd().equals("") && ATSForms.get16c2nd().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.info16d); ATSForms.focus16dRQP(); }
		else if (ATSForms.get18tDof().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nRQP, getText(), em.reqDof); ATSForms.focus18tDofRQP(); }
        else if (!ATSForms.get18tDof().isEmpty() && !ATSForms.get18tDof().startsWith("0") && ATSForms.get18tDof().length()<6) { DialogFactory.openWarningDialog(Shells.sh_nRQP, getText(), em.infoInvalidDof); ATSForms.focus18tDofRQP(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().startsWith("0") && ATSForms.get18tDof().length()>1) { DialogFactory.openWarningDialog(Shells.sh_nRQP, getText(), em.infoInvalidDof); ATSForms.focus18tDofRQP(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && (time.sYy.equals("00") || time.sHh.equals("00") || time.sMm.equals("00") || time.iHh>12 || time.iMM>31)) { DialogFactory.openWarningDialog(Shells.sh_nRQP, getText(), em.infoInvalid18Dof); ATSForms.focus18tDofRQP(); }
		else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && Integer.parseInt(datedof) < Integer.parseInt(time.datenow)) { DialogFactory.openInfoDialog(Shells.sh_nRQP, getText(), em.infoInvalid18DofVal); ATSForms.focus18tDofRQP(); }
		else if (ATSForms.getBodyRQP().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nRQP, getText(),em.req(ATSForms.getBodyRQP().length())); }
 		else {
 			// mendapatkan isi masing2 field
			ss.hRQP();ss.s3();ss.s7();ss.s13();ss.s16();
			if (ss.s18Baru.contains("DOF/000000")) ss.s18Baru = ss.s18Baru.replace("DOF/000000", "0");
			if (ss.s18Baru.startsWith(" ")) { ss.s18Baru=ss.s18Baru.replaceFirst("\\s+", ""); }
 			ss.data(ATSForms.getBodyRQP());
 			validasiDuplicateAddress(Shells.sh_nRQP,"RQP");
		}
 	}
	
	void sRQS() {
 		ss.sReg=""; ss.sDof=""; ss.s18Baru="";
    	ATSForms.getRQSValue(); time.tgl();
    	ss.sDof = ATSForms.get18tDof();
    	ss.proteksi18();
    	//----------------------------------------------------------------
    	if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4) { time.t13b(ATSForms.get13b()); }
    	if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4) { time.t16b(ATSForms.get16b()); }
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
		if (strDof.startsWith(" ")) { strDof = strDof.replaceFirst("\\s+", ""); }
		if (strDof.compareTo("DOF/000000")==0) strDof = "0";
    	//---------------------------------------- [header mandatory] ----------------------------------------
    	if (HeaderComposite.getPriorityRQS().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRQS, getText(), em.reqPriority); HeaderComposite.tPriorityRQSFocus(); }
		else if (HeaderComposite.getDest1RQS().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRQS, getText(), em.reqAddress); HeaderComposite.tDest1RQSFocus(); }
		else if ((!HeaderComposite.getDest1RQS().equals("")) && (HeaderComposite.getDest1RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest1RQSFocus(); }
		else if ((!HeaderComposite.getDest2RQS().equals("")) && (HeaderComposite.getDest2RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest2RQSFocus(); }
		else if ((!HeaderComposite.getDest3RQS().equals("")) && (HeaderComposite.getDest3RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest3RQSFocus(); }
		else if ((!HeaderComposite.getDest4RQS().equals("")) && (HeaderComposite.getDest4RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest4RQSFocus(); }
		else if ((!HeaderComposite.getDest5RQS().equals("")) && (HeaderComposite.getDest5RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest5RQSFocus(); }
		else if ((!HeaderComposite.getDest6RQS().equals("")) && (HeaderComposite.getDest6RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest6RQSFocus(); }
		else if ((!HeaderComposite.getDest7RQS().equals("")) && (HeaderComposite.getDest7RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest7RQSFocus(); }
		else if ((!HeaderComposite.getDest8RQS().equals("")) && (HeaderComposite.getDest8RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest8RQSFocus(); }
		else if ((!HeaderComposite.getDest9RQS().equals("")) && (HeaderComposite.getDest9RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest9RQSFocus(); }
		else if ((!HeaderComposite.getDest10RQS().equals("")) && (HeaderComposite.getDest10RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest10RQSFocus(); }
		else if ((!HeaderComposite.getDest11RQS().equals("")) && (HeaderComposite.getDest11RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest11RQSFocus(); }
		else if ((!HeaderComposite.getDest12RQS().equals("")) && (HeaderComposite.getDest12RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest12RQSFocus(); }
		else if ((!HeaderComposite.getDest13RQS().equals("")) && (HeaderComposite.getDest13RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest13RQSFocus(); }
		else if ((!HeaderComposite.getDest14RQS().equals("")) && (HeaderComposite.getDest14RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest14RQSFocus(); }
		else if ((!HeaderComposite.getDest15RQS().equals("")) && (HeaderComposite.getDest15RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest15RQSFocus(); }
		else if ((!HeaderComposite.getDest16RQS().equals("")) && (HeaderComposite.getDest16RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest16RQSFocus(); }
		else if ((!HeaderComposite.getDest17RQS().equals("")) && (HeaderComposite.getDest17RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest17RQSFocus(); }
		else if ((!HeaderComposite.getDest18RQS().equals("")) && (HeaderComposite.getDest18RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest18RQSFocus(); }
		else if ((!HeaderComposite.getDest19RQS().equals("")) && (HeaderComposite.getDest19RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest19RQSFocus(); }
		else if ((!HeaderComposite.getDest20RQS().equals("")) && (HeaderComposite.getDest20RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest20RQSFocus(); }
		else if ((!HeaderComposite.getDest21RQS().equals("")) && (HeaderComposite.getDest21RQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoAddress); HeaderComposite.tDest21RQSFocus(); }
		else if (HeaderComposite.getOriginatorRQS().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRQS, getText(), em.reqOriginator); HeaderComposite.tOriginatorRQSFocus(); }
 		else if ((!HeaderComposite.getOriginatorRQS().equals("")) && (HeaderComposite.getOriginatorRQS().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoOriginator); HeaderComposite.tOriginatorRQSFocus(); }
    	//---------------------------------------- [body mandatory] ----------------------------------------
 		else if (ATSForms.get3a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRQS, getText(), em.req3a); ATSForms.focus3aRQS(); }
        else if (ATSForms.get7a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRQS, getText(), em.req7a); ATSForms.focus7aRQS(); }
        else if (ATSForms.get7b().equals("A") && (ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nRQS, getText(), em.req7c); ATSForms.focus7cRQS(); }
        else if (!ATSForms.get7b().equals("A") && (!ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nRQS, getText(), em.req7b); }
        else if (!ATSForms.get7c().equals("") && (ATSForms.get7c().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.info7c); ATSForms.focus7cRQS(); }
        else if (ATSForms.get13a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRQS, getText(), em.req13a); ATSForms.focus13aRQS(); }
        else if (ATSForms.get13a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.info13a); ATSForms.focus13aRQS(); }
        else if ((!ATSForms.get13b().equals("")) && (ATSForms.get13b().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.info13b); ATSForms.focus13bRQS(); }
        else if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4 && (time.iHh13>23) || (time.iMM13>59)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoInvalid13b); ATSForms.focus13bRQS(); }
		else if (ATSForms.get16a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nRQS, getText(), em.req16a); ATSForms.focus16aRQS(); }
		else if (ATSForms.get16a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.info16a); ATSForms.focus16aRQS(); }
		else if (!ATSForms.get16b().equals("") && ATSForms.get16b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.info16b); ATSForms.focus16bRQS(); }
		else if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4 && (time.iHh16>23) || (time.iMM16>59)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoInvalid16b); ATSForms.focus16bRQS(); }
		else if (!ATSForms.get16c().equals("") && ATSForms.get16c().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.info16c); ATSForms.focus16cRQS(); }
		else if (!ATSForms.get16c2nd().equals("") && ATSForms.get16c2nd().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.info16d); ATSForms.focus16dRQS(); }
		else if (ATSForms.get18tDof().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nRQS, getText(), em.reqDof); ATSForms.focus18tDofRQS(); }
        else if (!ATSForms.get18tDof().isEmpty() && !ATSForms.get18tDof().startsWith("0") && ATSForms.get18tDof().length()<6) { DialogFactory.openWarningDialog(Shells.sh_nRQS, getText(), em.infoInvalidDof); ATSForms.focus18tDofRQS(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().startsWith("0") && ATSForms.get18tDof().length()>1) { DialogFactory.openWarningDialog(Shells.sh_nRQS, getText(), em.infoInvalidDof); ATSForms.focus18tDofRQS(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && (time.sYy.equals("00") || time.sHh.equals("00") || time.sMm.equals("00") || time.iHh>12 || time.iMM>31)) { DialogFactory.openWarningDialog(Shells.sh_nRQS, getText(), em.infoInvalid18Dof); ATSForms.focus18tDofRQS(); }
		else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && Integer.parseInt(datedof) < Integer.parseInt(time.datenow)) { DialogFactory.openInfoDialog(Shells.sh_nRQS, getText(), em.infoInvalid18DofVal); ATSForms.focus18tDofRQS(); }
		else if (ATSForms.getBodyRQS().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nRQS, getText(),em.req(ATSForms.getBodyRQS().length())); }
 		else {
 			// mendapatkan isi masing2 field
			ss.hRQS();ss.s3();ss.s7();ss.s13();ss.s16();
			if (ss.s18Baru.contains("DOF/000000")) ss.s18Baru = ss.s18Baru.replace("DOF/000000", "0");
			if (ss.s18Baru.startsWith(" ")) { ss.s18Baru=ss.s18Baru.replaceFirst("\\s+", ""); }
 			ss.data(ATSForms.getBodyRQS());
 			validasiDuplicateAddress(Shells.sh_nRQS,"RQS");
			// tutup koneksi GetFPL
    		ss.connClose("RQS");
		}
 	}
	
    void sSPL() {
    	//ss.setfree(); time.setfree();
    	ss.criteria_sts=""; ss.sprotekpbn=""; ss.s18Baru="";
    	ATSForms.getSPLValue(); time.tgl();
    	ss.sReg = ATSForms.get18tReg();
    	ss.sDof = ATSForms.get18tDof();
    	ss.tpbn = ATSForms.get18tPbn();
    	
    	t15a = ATSForms.get15a();
    	t15b = ATSForms.get15b();
		time.t15(t15a, t15b);

		ss.proteksi18();
		ss.validasipbn(ss.tpbn);
		
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
		System.out.println("ATSForms.get19fCol():"+ATSForms.get19fCol());
		System.out.println("ATSForms.get19fNum():"+ATSForms.get19fNum());
		System.out.println("ATSForms.get19fCap():"+ATSForms.get19fCap());
    	//---------------------------------------- [header mandatory] ----------------------------------------
    	if (HeaderComposite.getPrioritySPL().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.reqPriority); HeaderComposite.tPrioritySPLFocus(); }
		else if (HeaderComposite.getDest1SPL().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.reqAddress); HeaderComposite.tDest1SPLFocus(); }
		else if ((!HeaderComposite.getDest1SPL().equals("")) && (HeaderComposite.getDest1SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest1SPLFocus(); }
		else if ((!HeaderComposite.getDest2SPL().equals("")) && (HeaderComposite.getDest2SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest2SPLFocus(); }
		else if ((!HeaderComposite.getDest3SPL().equals("")) && (HeaderComposite.getDest3SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest3SPLFocus(); }
		else if ((!HeaderComposite.getDest4SPL().equals("")) && (HeaderComposite.getDest4SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest4SPLFocus(); }
		else if ((!HeaderComposite.getDest5SPL().equals("")) && (HeaderComposite.getDest5SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest5SPLFocus(); }
		else if ((!HeaderComposite.getDest6SPL().equals("")) && (HeaderComposite.getDest6SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest6SPLFocus(); }
		else if ((!HeaderComposite.getDest7SPL().equals("")) && (HeaderComposite.getDest7SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest7SPLFocus(); }
		else if ((!HeaderComposite.getDest8SPL().equals("")) && (HeaderComposite.getDest8SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest8SPLFocus(); }
		else if ((!HeaderComposite.getDest9SPL().equals("")) && (HeaderComposite.getDest9SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest9SPLFocus(); }
		else if ((!HeaderComposite.getDest10SPL().equals("")) && (HeaderComposite.getDest10SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest10SPLFocus(); }
		else if ((!HeaderComposite.getDest11SPL().equals("")) && (HeaderComposite.getDest11SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest11SPLFocus(); }
		else if ((!HeaderComposite.getDest12SPL().equals("")) && (HeaderComposite.getDest12SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest12SPLFocus(); }
		else if ((!HeaderComposite.getDest13SPL().equals("")) && (HeaderComposite.getDest13SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest13SPLFocus(); }
		else if ((!HeaderComposite.getDest14SPL().equals("")) && (HeaderComposite.getDest14SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest14SPLFocus(); }
		else if ((!HeaderComposite.getDest15SPL().equals("")) && (HeaderComposite.getDest15SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest15SPLFocus(); }
		else if ((!HeaderComposite.getDest16SPL().equals("")) && (HeaderComposite.getDest16SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest16SPLFocus(); }
		else if ((!HeaderComposite.getDest17SPL().equals("")) && (HeaderComposite.getDest17SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest17SPLFocus(); }
		else if ((!HeaderComposite.getDest18SPL().equals("")) && (HeaderComposite.getDest18SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest18SPLFocus(); }
		else if ((!HeaderComposite.getDest19SPL().equals("")) && (HeaderComposite.getDest19SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest19SPLFocus(); }
		else if ((!HeaderComposite.getDest20SPL().equals("")) && (HeaderComposite.getDest20SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest20SPLFocus(); }
		else if ((!HeaderComposite.getDest21SPL().equals("")) && (HeaderComposite.getDest21SPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoAddress); HeaderComposite.tDest21SPLFocus(); }
		else if (HeaderComposite.getOriginatorSPL().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.reqOriginator); HeaderComposite.tOriginatorSPLFocus(); }
 		else if ((!HeaderComposite.getOriginatorSPL().equals("")) && (HeaderComposite.getOriginatorSPL().length() < 8)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoOriginator); HeaderComposite.tOriginatorSPLFocus(); }
    	//---------------------------------------- [body mandatory] ----------------------------------------
 		else if (ATSForms.get3a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.req3a); ATSForms.focus3aSPL(); }
        //7
 		else if (ATSForms.get7a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.req7a); ATSForms.focus7aSPL(); }
        else if (ATSForms.get7b().equals("A") && (ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.req7c); ATSForms.focus7cSPL(); }
        else if (!ATSForms.get7b().equals("A") && (!ATSForms.get7c().equals(""))) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.req7b); }
        else if (!ATSForms.get7c().equals("") && (ATSForms.get7c().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.info7c); ATSForms.focus7cSPL(); }
    	//13
        else if (ATSForms.get13a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.req13a); ATSForms.focus13aSPL(); }
        else if (ATSForms.get13a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.info13a); ATSForms.focus13aSPL(); }
		else if (ATSForms.get13b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.req13b); ATSForms.focus13bSPL(); }
		else if (ATSForms.get13b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.info13b); ATSForms.focus13bSPL(); }
		else if (!ATSForms.get13b().isEmpty() && ATSForms.get13b().length()==4 && (time.iHh13>23) || (time.iMM13>59)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoInvalid13b); ATSForms.focus13bSPL(); }
		//16
		else if (ATSForms.get16a().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.req16a); ATSForms.focus16aSPL(); }
		else if (ATSForms.get16a().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.info16a); ATSForms.focus16aSPL(); }
		else if (ATSForms.get16b().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.req16b); ATSForms.focus16bSPL(); }
		else if (ATSForms.get16b().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.info16b); ATSForms.focus16bSPL(); }
		else if (!ATSForms.get16b().isEmpty() && ATSForms.get16b().length()==4 && (time.iHh16>23) || (time.iMM16>59)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoInvalid16b); ATSForms.focus16bSPL(); }
		else if (ATSForms.get16c().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.req16c); ATSForms.focus16cSPL(); }
		else if (ATSForms.get16c().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.info16c); ATSForms.focus16cSPL(); }
		else if (!ATSForms.get16c2nd().equals("") && ATSForms.get16c2nd().length() < 4) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.info16d); ATSForms.focus16dSPL(); }
    	//tambahan
		else if (!ATSForms.get16c2nd().isEmpty()&&ATSForms.get16c2nd().length()==4 && ATSForms.get16c().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.info16cd); ATSForms.focus16cSPL(); }
    	//18
		else if (!ATSForms.get18tTyp().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(),"There is no field TYPE OF AIRCRAFT, so field OTHER INFORMATION with TYP/ indicator can't be filled !!"); ATSForms.focus18tTypSPL(); }
    	//indicator DEP/
        else if ((ATSForms.get13a().equals("ZZZZ") || ATSForms.get13a().equals("AFIL")) && ATSForms.get18tDep().equals("")) {  DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.infoDep13); ATSForms.focus18tDepSPL(); }
        else if (!ATSForms.get13a().contains("ZZZZ") && !ATSForms.get13a().contains("AFIL") && !ATSForms.get18tDep().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.info13Dep); ATSForms.focus13aSPL(); }
    	//indicator DEST/
        else if (ATSForms.get16a().equals("ZZZZ") && ATSForms.get18tDest().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.infoDest16); ATSForms.focus18tDestSPL(); }
        else if (!ATSForms.get16a().contains("ZZZZ") && !ATSForms.get18tDest().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.info16Dest); ATSForms.focus16aSPL(); }
    	//indicator ALTN/
        else if (ATSForms.get16c().equals("ZZZZ") && ATSForms.get18tAltn().equals("")) {  DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.infoAltn16); ATSForms.focus18tAltnSPL(); }
        else if (!ATSForms.get16c().contains("ZZZZ") && !ATSForms.get18tAltn().equals("")) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.info16Altn); ATSForms.focus16cSPL(); }
    	//indicator REG/, DOF/
        else if (!ATSForms.get18tReg().isEmpty() && ATSForms.get18tReg().equals(ATSForms.get7a())) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.infoInvalidReg); ATSForms.focus18tRegSPL(); }
        else if (!ATSForms.get18tReg().isEmpty() && !ATSForms.get18tOpr().equals("") && ATSForms.get18tReg().equals(ATSForms.get18tOpr())) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.infoInvalidOpr); ATSForms.focus18tOprSPL(); }
        else if (!ATSForms.get18tReg().isEmpty() && (ATSForms.get18tReg().length() > 7 || ATSForms.get18tReg().contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.infoInvalidReg); ATSForms.focus18tRegSPL(); }
		else if (ATSForms.get18tDof().isEmpty()) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.reqDof); ATSForms.focus18tDofSPL(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()<6) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.infoInvalidDof); ATSForms.focus18tDofSPL(); }
        else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && (time.sYy.equals("00") || time.sHh.equals("00") || time.sMm.equals("00") || time.iHh>12 || time.iMM>31)) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.infoInvalid18Dof); ATSForms.focus18tDofSPL(); }
		else if (!ATSForms.get18tDof().isEmpty() && ATSForms.get18tDof().length()==6 && !strDof.equals("") && !strDof.equals("DOF/0") && Integer.parseInt(datedof) < Integer.parseInt(time.datenow)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.infoInvalid18DofVal); ATSForms.focus18tDofSPL(); }
		//indicator STS/, PER/, CODE/, SEL/, PBN/
        else if (!ATSForms.get18tSts().equals("") && ss.criteria_sts.contains("ngaco")) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.infoInvalidSts); ATSForms.focus18tStsSPL(); }
        else if (!ATSForms.get18tPer().equals("") && ATSForms.get18tPer().length() > 1) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.infoInvalidPer); ATSForms.focus18tPerSPL(); }
        else if (!ATSForms.get18tCode().equals("") && (ATSForms.get18tCode().length() > 6 || ATSForms.get18tCode().contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.infoInvalidCode); ATSForms.focus18tCodeSPL(); }
        else if (!ATSForms.get18tSel().equals("") && (ATSForms.get18tSel().length() > 4 || ATSForms.get18tSel().contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.infoInvalidSel); ATSForms.focus18tSelSPL(); }
        else if (!ss.tpbn.equals("") && (ss.tpbn.length() > 16 || ss.tpbn.contains("\n"))) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.infoInvalidPbn); ATSForms.focus18tPbnSPL(); }
        else if (!ss.tpbn.equals("") && ss.sprotekpbn.contains("ngaco")) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.infoInvalidPbnFix); ATSForms.focus18tPbnSPL(); }
		//19
		else if ((!ATSForms.get19a().equals("")) && (ATSForms.get19a().length() < 4)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.info19a); ATSForms.focus19aSPL(); } 
		else if (!ATSForms.get19a().isEmpty() && ATSForms.get19a().length()==4 && ((time.iHh19>23) || (time.iMM19>59))) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(), em.infoInvalid19a); ATSForms.focus19aSPL(); }
		else if ((!ATSForms.get19fNum().equals("")) && (ATSForms.get19fNum().length() < 2)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.info19fNum); ATSForms.focus19NumSPL(); } 
		else if ((!ATSForms.get19fCap().equals("")) && (ATSForms.get19fCap().length() < 3)) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.info19fCap); ATSForms.focus19CapSPL(); }
		else if ((!ATSForms.get19fNum().equals(""))&&(ATSForms.get19fCap().equals(""))) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.info19f); ATSForms.focus19CapSPL(); }
		else if ((!ATSForms.get19fCap().equals(""))&&(ATSForms.get19fNum().equals(""))) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.info19f); ATSForms.focus19NumSPL(); }
		else if ((!ATSForms.get19fCov().equals("")) && ((ATSForms.get19fNum().equals("")) || (ATSForms.get19fCap().equals("")))) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.info19f); ATSForms.focus19NumSPL(); }
		else if ((!ATSForms.get19fCol().equals("")) && ((ATSForms.get19fNum().equals("")) || (ATSForms.get19fCap().equals("")))) { DialogFactory.openInfoDialog(Shells.sh_nSPL, getText(), em.info19f); ATSForms.focus19NumSPL(); }
		else if (ATSForms.getBodySPL().length() > iLength) { DialogFactory.openWarningDialog(Shells.sh_nSPL, getText(),em.req(ATSForms.getBodySPL().length())); }
 		else {
 			// mendapatkan isi masing2 field
			ss.hSPL();ss.s3();ss.s7();ss.s13();ss.s16();ss.s19();
 			ss.data(ATSForms.getBodySPL());
 			validasiDuplicateAddress(Shells.sh_nSPL,"SPL");	
    		// tutup koneksi GetFPL
    		ss.connClose("SPL");
		}
    }
}
