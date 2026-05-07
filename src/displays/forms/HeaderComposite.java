package displays.forms;

import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import readwrite.ReadFromFile;
import setting.ButtonsSetting;
import setting.Colors;
import setting.Images;
import setting.Shorten;
import setting.Times;
import actions.ATSAuto;
import actions.RefreshTable;
import actions.ValidasiTemp;

public class HeaderComposite {//extends Composite {

	static Label label;
	
	static Times time = new Times();
	static Shorten sh = new Shorten();
	static ButtonsSetting bs = new ButtonsSetting();
	static ReadFromFile rff = new ReadFromFile();
	static ATSAuto atsAuto = new ATSAuto();	
	
	static Label lSendAt,lNote;
//	static Text tSendAt;
	public static Button bSendAt;
	static Composite compBtn,compBot,compTop,grpSendAt,compHeader;
	
	public static Text regText;
	public static Button btnDLA,btnCHG,btnCNL,btnDEP,btnARR;
	
	public static Text tSendAtAFTN,tSendAtALR,tSendAtRCF,tSendAtFPL,tSendAtDLA,tSendAtCHG,tSendAtCNL,tSendAtDEP,tSendAtARR,tSendAtCDN,
		tSendAtCPL,tSendAtEST,tSendAtACP,tSendAtLAM,tSendAtRQP,tSendAtRQS,tSendAtSPL,
		tPriorityAFTN,tPriorityALR,tPriorityRCF,tPriorityFPL,tPriorityDLA,tPriorityCHG,tPriorityCNL,tPriorityDEP,tPriorityARR,
		tPriorityCDN,tPriorityCPL,tPriorityEST,tPriorityACP,tPriorityLAM,tPriorityRQP,tPriorityRQS,tPrioritySPL,
		tFilingTimeAFTN,tFilingTimeALR,tFilingTimeRCF,tFilingTimeFPL,tFilingTimeDLA,tFilingTimeCHG,tFilingTimeCNL,tFilingTimeDEP,
		tFilingTimeARR,tFilingTimeCDN,tFilingTimeCPL,tFilingTimeEST,tFilingTimeACP,tFilingTimeLAM,tFilingTimeRQP,tFilingTimeRQS,tFilingTimeSPL,
		tOriginatorAFTN,tOriginatorALR,tOriginatorRCF,tOriginatorFPL,tOriginatorDLA,tOriginatorCHG,tOriginatorCNL,tOriginatorDEP,
		tOriginatorARR,tOriginatorCDN,tOriginatorCPL,tOriginatorEST,tOriginatorACP,tOriginatorLAM,tOriginatorRQP,tOriginatorRQS,tOriginatorSPL,
		tOriRefAFTN,tOriRefALR,tOriRefRCF,tOriRefFPL,tOriRefDLA,tOriRefCHG,tOriRefCNL,tOriRefDEP,tOriRefARR,
		tOriRefCDN,tOriRefCPL,tOriRefEST,tOriRefACP,tOriRefLAM,tOriRefRQP,tOriRefRQS,tOriRefSPL;
	
	public static Button bSendAtAFTN,bSendAtALR,bSendAtRCF,bSendAtFPL,bSendAtDLA,bSendAtCHG,bSendAtCNL,bSendAtDEP,bSendAtARR,bSendAtCDN,
		bSendAtCPL,bSendAtEST,bSendAtACP,bSendAtLAM,bSendAtRQP,bSendAtRQS,bSendAtSPL,
		bPriorityAFTN,bPriorityALR,bPriorityRCF,bPriorityFPL,bPriorityDLA,bPriorityCHG,bPriorityCNL,bPriorityDEP,bPriorityARR,
		bPriorityCDN,bPriorityCPL,bPriorityEST,bPriorityACP,bPriorityLAM,bPriorityRQP,bPriorityRQS,bPrioritySPL,
		bBellAFTN,bBellALR,bBellRCF,bBellFPL,bBellDLA,bBellCHG,bBellCNL,bBellDEP,bBellARR,bBellCDN,bBellCPL,bBellEST,bBellACP,bBellLAM,
		bBellRQP,bBellRQS,bBellSPL,
		bTimeATS,bTimeALR,bTimeRCF,bTimeFPL,bTimeDLA,bTimeCHG,bTimeCNL,bTimeDEP,bTimeARR,bTimeCDN,bTimeCPL,bTimeEST,
		bTimeACP,bTimeLAM,bTimeRQP,bTimeRQS,bTimeSPL;
	
	//AFTN Header Components
	public static Text tDest1AFTN,tDest2AFTN,tDest3AFTN,tDest4AFTN,tDest5AFTN,tDest6AFTN,tDest7AFTN;
	public static Text tDest8AFTN,tDest9AFTN,tDest10AFTN,tDest11AFTN,tDest12AFTN,tDest13AFTN,tDest14AFTN;
	public static Text tDest15AFTN,tDest16AFTN,tDest17AFTN,tDest18AFTN,tDest19AFTN,tDest20AFTN,tDest21AFTN;
	//ALR Header Components
	public static Text tDest1ALR,tDest2ALR,tDest3ALR,tDest4ALR,tDest5ALR,tDest6ALR,tDest7ALR;
	public static Text tDest8ALR,tDest9ALR,tDest10ALR,tDest11ALR,tDest12ALR,tDest13ALR,tDest14ALR;
	public static Text tDest15ALR,tDest16ALR,tDest17ALR,tDest18ALR,tDest19ALR,tDest20ALR,tDest21ALR;
	//RCF Header Components
	public static Text tDest1RCF,tDest2RCF,tDest3RCF,tDest4RCF,tDest5RCF,tDest6RCF,tDest7RCF;
	public static Text tDest8RCF,tDest9RCF,tDest10RCF,tDest11RCF,tDest12RCF,tDest13RCF,tDest14RCF;
	public static Text tDest15RCF,tDest16RCF,tDest17RCF,tDest18RCF,tDest19RCF,tDest20RCF,tDest21RCF;
	//FPL Header Components
	public static Text tDest1FPL,tDest2FPL,tDest3FPL,tDest4FPL,tDest5FPL,tDest6FPL,tDest7FPL;
	public static Text tDest8FPL,tDest9FPL,tDest10FPL,tDest11FPL,tDest12FPL,tDest13FPL,tDest14FPL;
	public static Text tDest15FPL,tDest16FPL,tDest17FPL,tDest18FPL,tDest19FPL,tDest20FPL,tDest21FPL;
	//DLA Header Components
	public static Text tDest1DLA,tDest2DLA,tDest3DLA,tDest4DLA,tDest5DLA,tDest6DLA,tDest7DLA;
	public static Text tDest8DLA,tDest9DLA,tDest10DLA,tDest11DLA,tDest12DLA,tDest13DLA,tDest14DLA;
	public static Text tDest15DLA,tDest16DLA,tDest17DLA,tDest18DLA,tDest19DLA,tDest20DLA,tDest21DLA;
	//CHG Header Components
	public static Text tDest1CHG,tDest2CHG,tDest3CHG,tDest4CHG,tDest5CHG,tDest6CHG,tDest7CHG;
	public static Text tDest8CHG,tDest9CHG,tDest10CHG,tDest11CHG,tDest12CHG,tDest13CHG,tDest14CHG;
	public static Text tDest15CHG,tDest16CHG,tDest17CHG,tDest18CHG,tDest19CHG,tDest20CHG,tDest21CHG;
	//CNL Header Components
	public static Text tDest1CNL,tDest2CNL,tDest3CNL,tDest4CNL,tDest5CNL,tDest6CNL,tDest7CNL;
	public static Text tDest8CNL,tDest9CNL,tDest10CNL,tDest11CNL,tDest12CNL,tDest13CNL,tDest14CNL;
	public static Text tDest15CNL,tDest16CNL,tDest17CNL,tDest18CNL,tDest19CNL,tDest20CNL,tDest21CNL;
	//DEP Header Components
	public static Text tDest1DEP,tDest2DEP,tDest3DEP,tDest4DEP,tDest5DEP,tDest6DEP,tDest7DEP;
	public static Text tDest8DEP,tDest9DEP,tDest10DEP,tDest11DEP,tDest12DEP,tDest13DEP,tDest14DEP;
	public static Text tDest15DEP,tDest16DEP,tDest17DEP,tDest18DEP,tDest19DEP,tDest20DEP,tDest21DEP;
	//ARR Header Components
	public static Text tDest1ARR,tDest2ARR,tDest3ARR,tDest4ARR,tDest5ARR,tDest6ARR,tDest7ARR;
	public static Text tDest8ARR,tDest9ARR,tDest10ARR,tDest11ARR,tDest12ARR,tDest13ARR,tDest14ARR;
	public static Text tDest15ARR,tDest16ARR,tDest17ARR,tDest18ARR,tDest19ARR,tDest20ARR,tDest21ARR;
	//CDN Header Components
	public static Text tDest1CDN,tDest2CDN,tDest3CDN,tDest4CDN,tDest5CDN,tDest6CDN,tDest7CDN;
	public static Text tDest8CDN,tDest9CDN,tDest10CDN,tDest11CDN,tDest12CDN,tDest13CDN,tDest14CDN;
	public static Text tDest15CDN,tDest16CDN,tDest17CDN,tDest18CDN,tDest19CDN,tDest20CDN,tDest21CDN;
	//CPL Header Components
	public static Text tDest1CPL,tDest2CPL,tDest3CPL,tDest4CPL,tDest5CPL,tDest6CPL,tDest7CPL;
	public static Text tDest8CPL,tDest9CPL,tDest10CPL,tDest11CPL,tDest12CPL,tDest13CPL,tDest14CPL;
	public static Text tDest15CPL,tDest16CPL,tDest17CPL,tDest18CPL,tDest19CPL,tDest20CPL,tDest21CPL;
	//EST Header Components
	public static Text tDest1EST,tDest2EST,tDest3EST,tDest4EST,tDest5EST,tDest6EST,tDest7EST;
	public static Text tDest8EST,tDest9EST,tDest10EST,tDest11EST,tDest12EST,tDest13EST,tDest14EST;
	public static Text tDest15EST,tDest16EST,tDest17EST,tDest18EST,tDest19EST,tDest20EST,tDest21EST;
	//ACP Header Components
	public static Text tDest1ACP,tDest2ACP,tDest3ACP,tDest4ACP,tDest5ACP,tDest6ACP,tDest7ACP;
	public static Text tDest8ACP,tDest9ACP,tDest10ACP,tDest11ACP,tDest12ACP,tDest13ACP,tDest14ACP;
	public static Text tDest15ACP,tDest16ACP,tDest17ACP,tDest18ACP,tDest19ACP,tDest20ACP,tDest21ACP;
	//LAM Header Components
	public static Text tDest1LAM,tDest2LAM,tDest3LAM,tDest4LAM,tDest5LAM,tDest6LAM,tDest7LAM;
	public static Text tDest8LAM,tDest9LAM,tDest10LAM,tDest11LAM,tDest12LAM,tDest13LAM,tDest14LAM;
	public static Text tDest15LAM,tDest16LAM,tDest17LAM,tDest18LAM,tDest19LAM,tDest20LAM,tDest21LAM;
	//RQP Header Components
	public static Text tDest1RQP,tDest2RQP,tDest3RQP,tDest4RQP,tDest5RQP,tDest6RQP,tDest7RQP;
	public static Text tDest8RQP,tDest9RQP,tDest10RQP,tDest11RQP,tDest12RQP,tDest13RQP,tDest14RQP;
	public static Text tDest15RQP,tDest16RQP,tDest17RQP,tDest18RQP,tDest19RQP,tDest20RQP,tDest21RQP;
	//RQS Header Components
	public static Text tDest1RQS,tDest2RQS,tDest3RQS,tDest4RQS,tDest5RQS,tDest6RQS,tDest7RQS;
	public static Text tDest8RQS,tDest9RQS,tDest10RQS,tDest11RQS,tDest12RQS,tDest13RQS,tDest14RQS;
	public static Text tDest15RQS,tDest16RQS,tDest17RQS,tDest18RQS,tDest19RQS,tDest20RQS,tDest21RQS;
	//SPL Header Components
	public static Text tDest1SPL,tDest2SPL,tDest3SPL,tDest4SPL,tDest5SPL,tDest6SPL,tDest7SPL;
	public static Text tDest8SPL,tDest9SPL,tDest10SPL,tDest11SPL,tDest12SPL,tDest13SPL,tDest14SPL;
	public static Text tDest15SPL,tDest16SPL,tDest17SPL,tDest18SPL,tDest19SPL,tDest20SPL,tDest21SPL;
	
	static String FT="",PRIO="",jam="";
	public static String fta,ftb,ftc,ftd,fte,ftf,SA="",send_at,saa,sab,sac,sad,sae,saf;
	static int initAFTN=0;
	

//	public HeaderComposite(Composite parent, int style) {
//		super(parent,style);
//	}
	
	//------------------------------------- HEADER AFTN -------------------------------------
	static void setAddr(Text text) { sh.textStyle(text, 80, 8, SWT.LEFT, SWT.CENTER, sh.letter, "", true); }

	public static void headerT(Label lPriority,final Text tPriority,Button bPriority,Label la,Label lAddress,Text tD1,Text tD2,Text tD3,Text tD4,
			Text tD5,Text tD6,Text tD7,Text tD8,Text tD9,Text tD10,Text tD11,Text tD12,Text tD13,Text tD14,Text tD15,Text tD16,Text tD17,
			Text tD18,Text tD19,Text tD20,Text tD21,Label lFiling,final Text tFilingTime,Button bFiling,Label lb,Label lOriginator,
			Text tOriginator, Label lOriref,Text tOriRef,Button bBell) {
		
		//Priority
		sh.labelStyle1(lPriority, "Priority", SWT.RIGHT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		sh.textStyle(tPriority, 25, 2, SWT.LEFT, SWT.TOP, sh.letter, "Priority", false);
		tPriority.setText("FF");
		sh.buttonStyle(bPriority, "", "Priority", bs.widthNavBtn, Colors.DarkGrey, SWT.CENTER, SWT.TOP, Images.img_LIST);
		bPriority.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RefreshTable.refreshPriority(tPriority);
			}
		});
		
		sh.labelStyle(la, "", 20, SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.MANDATORY);
		
		//Address
		sh.labelStyle1(lAddress, "Address", SWT.RIGHT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		setAddr(tD1); tD1.setFocus(); // menempatkan kursor di posisi textfield tD1
		setAddr(tD2);
		setAddr(tD3);
		setAddr(tD4);
		setAddr(tD5);
		setAddr(tD6);
		setAddr(tD7);
		setAddr(tD8);
		setAddr(tD9);
		setAddr(tD10);
		setAddr(tD11);
		setAddr(tD12);
		setAddr(tD13);
		setAddr(tD14);
		setAddr(tD15);
		setAddr(tD16);
		setAddr(tD17);
		setAddr(tD18);
		setAddr(tD19);
		setAddr(tD20);
		setAddr(tD21);
	
		//Filing Time
		sh.labelStyle1(lFiling, "Filing Time", SWT.RIGHT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		sh.textStyle(tFilingTime, 50, 6, SWT.LEFT, SWT.CENTER, "", "Filing Time (DDhhmm)", true);
		time.tgl(); tFilingTime.setText(time.filing_time);
		final Calendar caltFilingTime = Calendar.getInstance();
		tFilingTime.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tFilingTime.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tFilingTime.insert(buffer.toString());
					ignore = false;
					tFilingTime.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tFilingTime.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltFilingTime.set(Calendar.YEAR, 1901);
				caltFilingTime.set(Calendar.MONTH, Calendar.JANUARY);
				caltFilingTime.set(Calendar.DATE, 1);
				caltFilingTime.set(Calendar.HOUR_OF_DAY, 00);
				caltFilingTime.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltFilingTime.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltFilingTime.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltFilingTime.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltFilingTime.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltFilingTime.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltFilingTime.set(Calendar.MINUTE, minute);
				}
				tFilingTime.setSelection(e.start, e.start + length);
				ignore = true;
				tFilingTime.insert(newText);
				ignore = false;
			}
		});
		sh.buttonStyle(bFiling,"","Get current time (DDhhmm)",bs.widthNavBtn,bs.colorBtn,SWT.CENTER,SWT.CENTER,Images.img_time);
		bFiling.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				time.tgl();
				tFilingTime.setText(time.DD + time.hh + time.mm);
			}
		});

		sh.labelStyle(lb, "", 20, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		
		//Originator
		sh.labelStyle1(lOriginator, "Originator", SWT.RIGHT, SWT.CENTER, SWT.BOLD, Colors.MANDATORY);
		setAddr(tOriginator);
		rff.readConfiguration();
		tOriginator.setText(ReadFromFile.ReadInputFile1("/tp/originator.txt"));
		sh.labelStyle1(lOriref, "Originator's Reference", SWT.RIGHT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		sh.textStyle(tOriRef, rff.getWidthTextOriRef(), 69, SWT.LEFT, SWT.CENTER, "upper", "Originator's Reference", true);
		
		//Bell
		bBell.setImage(Images.img_BELL); bBell.setToolTipText("Check if you want to add Bell");
	}
	
	//------------------------------------- SEND AT -------------------------------------
	static Label setLabelSendAt(Label label, Composite comp) {
		label = new Label(comp,SWT.NONE); sh.labelStyle1(label, "Send At", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.red);
		return label;
	}
	
//	static Text setTextSendAt(Text tsendat, Composite comp) {
//    	tSendAt = tsendat;
//		tSendAt = new Text(comp, SWT.BORDER); sh.textStyle(tSendAt, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
//		time.tgl(); tSendAt.setText(time.filing_time);
//		final Calendar caltSendAt = Calendar.getInstance();
//		tSendAt.addListener(SWT.Verify, new Listener() {
//			boolean ignore;
//			public void handleEvent(Event e) {
//				if (ignore) return;
//				e.doit = false;
//				StringBuffer buffer = new StringBuffer(e.text);
//				char[] chars = new char[buffer.length()];
//				buffer.getChars(0, chars.length, chars, 0);
//				if (e.character == '\b') {
//					for (int i = e.start; i < e.end; i++) {
//						switch (i) {
//							case 0: { buffer.append('0'); break; } //* [D]D *
//							case 1: { buffer.append('0'); break; } //* D[D] *
//							case 2: { buffer.append('0'); break; } //* [H]H *
//							case 3: { buffer.append('0'); break; } //* H[H] *
//							case 4: { buffer.append('0'); break; } //* [M]M * 
//							case 5: { buffer.append('0'); break; } //* M[M] *
//							default: return;
//						}
//					}
//					tSendAt.setSelection(e.start, e.start + buffer.length());
//					ignore = true;
//					tSendAt.insert(buffer.toString());
//					ignore = false;
//					tSendAt.setSelection(e.start, e.start);
//					return;
//				}
//			
//				int start = e.start;
//				if (start > 10) return;
//				int index = 0;
//				for (int i = 0; i < chars.length; i++) {
//					if (chars[i] < '0' || '9' < chars[i]) return;
//					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
//					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
//					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
//					index++;
//				}
//				
//				String newText = buffer.toString();
//				int length = newText.length();
//				StringBuffer date = new StringBuffer(tSendAt.getText());
//				date.replace(e.start, e.start + length, newText);
//				
//				caltSendAt.set(Calendar.YEAR, 1901);
//				caltSendAt.set(Calendar.MONTH, Calendar.JANUARY);
//				caltSendAt.set(Calendar.DATE, 1);
//				caltSendAt.set(Calendar.HOUR_OF_DAY, 00);
//				caltSendAt.set(Calendar.MINUTE, 00);
//				
//				String dd = date.substring(0,2); 
//				if (dd.indexOf('0') == -1) {
//					int day = Integer.parseInt(dd);
//					int maxDay = caltSendAt.getActualMaximum(Calendar.DATE);
//					if (1 > day || day > maxDay) return;
//					caltSendAt.set(Calendar.DATE, day);
//				} 
//				String hh = date.substring(2,4); 
//				if (hh.indexOf('0') == -1) {
//					int hour = Integer.parseInt(hh);
//					int maxHour = caltSendAt.getActualMaximum(Calendar.HOUR_OF_DAY);
//					if (0 > hour || hour > maxHour) return;
//					caltSendAt.set(Calendar.HOUR_OF_DAY, hour);
//				}
//				String mm = date.substring(4,6); 
//				if (mm.indexOf('0') == -1) {
//					int minute = Integer.parseInt(mm);
//					int maxMinute = caltSendAt.getActualMaximum(Calendar.MINUTE);
//					if (0 > minute || minute > maxMinute) return;
//					caltSendAt.set(Calendar.MINUTE, minute);
//				}
//				tSendAt.setSelection(e.start, e.start + length);
//				ignore = true;
//				tSendAt.insert(newText);
//				ignore = false;
//			}
//		});
//		return tSendAt;
//    }
	
	static Button setButtonSendAt(Button bsendat, Composite comp) {
    	bSendAt = bsendat;
    	bSendAt = new Button(comp, SWT.CHECK); sh.buttonRCStyle(bSendAt, "Change", "Change send at (DDhhmm)");
    	ReadFromFile.readConfiguration();
    	if (ReadFromFile.getSendat().compareToIgnoreCase("yes")==0) bSendAt.setSelection(true);
    	else if (ReadFromFile.getSendat().compareToIgnoreCase("no")==0) bSendAt.setSelection(false);
    	return bSendAt;
    }
	
	static Label setLabelNote(Label label, Composite comp) {
		label = new Label(comp,SWT.NONE); 
		sh.labelStyle1(label, "Blue field indicates required field.", SWT.RIGHT, SWT.BOTTOM, SWT.ITALIC, Colors.MANDATORY);
		return label;
	}
	
	static Composite setCompLeft(Composite comp, Composite Group) {
		comp = new Composite(Group, SWT.NONE); sh.compositeStyle(comp, 3, SWT.LEFT, SWT.CENTER);
		return comp;
	}
	
	static Composite setCompRight(Composite comp, Composite Group) {
		comp = new Composite(Group, SWT.NONE); sh.compositeStyle(comp, 1, SWT.LEFT, SWT.CENTER);
		return comp;
	}
	
	static Composite setGroup(Composite comp, Composite Group) {
		comp = new Composite(Group, SWT.NONE); sh.compositeStyle(comp, 1, SWT.LEFT, SWT.CENTER);
		return comp;
	}
	
	public static void sendatAFTN(Composite comp) {
		grpSendAt = setGroup(grpSendAt, comp);
		compTop = setCompRight(compTop, grpSendAt);
		compBot = setCompLeft(compBot, grpSendAt);
		lNote = setLabelNote(lNote, compTop);
		lSendAt = setLabelSendAt(lSendAt, compBot);
//		tSendAtAFTN = setTextSendAt(tSendAtAFTN, compBot);
		tSendAtAFTN = setTextSendAtAFTN(compBot);
		bSendAtAFTN = setButtonSendAt(bSendAtAFTN, compBot);
	}
	
	public static void sendatALR(Composite comp) {
		grpSendAt = setGroup(grpSendAt, comp);
		compTop = setCompRight(compTop, grpSendAt);
		compBot = setCompLeft(compBot, grpSendAt);
		lNote = setLabelNote(lNote, compTop);
		lSendAt = setLabelSendAt(lSendAt, compBot);
//		tSendAtALR = setTextSendAt(tSendAtALR, compBot);
		tSendAtALR = setTextSendAtALR(compBot);
		bSendAtALR = setButtonSendAt(bSendAtALR, compBot);
	}
	
	public static void sendatRCF(Composite comp) {
		grpSendAt = setGroup(grpSendAt, comp);
		compTop = setCompRight(compTop, grpSendAt);
		compBot = setCompLeft(compBot, grpSendAt);
		lNote = setLabelNote(lNote, compTop);
		lSendAt = setLabelSendAt(lSendAt, compBot);
//		tSendAtRCF = setTextSendAt(tSendAtRCF, compBot);
		tSendAtRCF = setTextSendAtRCF(compBot);
		bSendAtRCF = setButtonSendAt(bSendAtRCF, compBot);
	}
	
	public static void sendatFPL(Composite comp) {
		grpSendAt = setGroup(grpSendAt, comp);
		compTop = setCompRight(compTop, grpSendAt);
		compBot = setCompLeft(compBot, grpSendAt);
		lNote = setLabelNote(lNote, compTop);
		lSendAt = setLabelSendAt(lSendAt, compBot);
//		tSendAtFPL = setTextSendAt(tSendAtFPL, compBot);
		tSendAtFPL = setTextSendAtFPL(compBot);
		bSendAtFPL = setButtonSendAt(bSendAtFPL, compBot);
	}
	
	public static void sendatDLA(Composite comp) {
		grpSendAt = setGroup(grpSendAt, comp);
		compTop = setCompRight(compTop, grpSendAt);
		compBot = setCompLeft(compBot, grpSendAt);
		lNote = setLabelNote(lNote, compTop);
		lSendAt = setLabelSendAt(lSendAt, compBot);
//		tSendAtDLA = setTextSendAt(tSendAtDLA, compBot);
		tSendAtDLA = setTextSendAtDLA(compBot);
		bSendAtDLA = setButtonSendAt(bSendAtDLA, compBot);
	}
	
	public static void sendatCHG(Composite comp) {
		grpSendAt = setGroup(grpSendAt, comp);
		compTop = setCompRight(compTop, grpSendAt);
		compBot = setCompLeft(compBot, grpSendAt);
		lNote = setLabelNote(lNote, compTop);
		lSendAt = setLabelSendAt(lSendAt, compBot);
//		tSendAtCHG = setTextSendAt(tSendAtCHG, compBot);
		tSendAtCHG = setTextSendAtCHG(compBot);
		bSendAtCHG = setButtonSendAt(bSendAtCHG, compBot);
	}
	
	public static void sendatCNL(Composite comp) {
		grpSendAt = setGroup(grpSendAt, comp);
		compTop = setCompRight(compTop, grpSendAt);
		compBot = setCompLeft(compBot, grpSendAt);
		lNote = setLabelNote(lNote, compTop);
		lSendAt = setLabelSendAt(lSendAt, compBot);
//		tSendAtCNL = setTextSendAt(tSendAtCNL, compBot);
		tSendAtCNL = setTextSendAtCNL(compBot);
		bSendAtCNL = setButtonSendAt(bSendAtCNL, compBot);
	}
	
	public static void sendatDEP(Composite comp) {
		grpSendAt = setGroup(grpSendAt, comp);
		compTop = setCompRight(compTop, grpSendAt);
		compBot = setCompLeft(compBot, grpSendAt);
		lNote = setLabelNote(lNote, compTop);
		lSendAt = setLabelSendAt(lSendAt, compBot);
//		tSendAtDEP = setTextSendAt(tSendAtDEP, compBot);
		tSendAtDEP = setTextSendAtDEP(compBot);
		bSendAtDEP = setButtonSendAt(bSendAtDEP, compBot);
	}
	
	public static void sendatARR(Composite comp) {
		grpSendAt = setGroup(grpSendAt, comp);
		compTop = setCompRight(compTop, grpSendAt);
		compBot = setCompLeft(compBot, grpSendAt);
		lNote = setLabelNote(lNote, compTop);
		lSendAt = setLabelSendAt(lSendAt, compBot);
//		tSendAtARR = setTextSendAt(tSendAtARR, compBot);
		tSendAtARR = setTextSendAtARR(compBot);
		bSendAtARR = setButtonSendAt(bSendAtARR, compBot);
	}
	
	public static void sendatCDN(Composite comp) {
		grpSendAt = setGroup(grpSendAt, comp);
		compTop = setCompRight(compTop, grpSendAt);
		compBot = setCompLeft(compBot, grpSendAt);
		lNote = setLabelNote(lNote, compTop);
		lSendAt = setLabelSendAt(lSendAt, compBot);
//		tSendAtCDN = setTextSendAt(tSendAtCDN, compBot);
		tSendAtCDN = setTextSendAtCDN(compBot);
		bSendAtCDN = setButtonSendAt(bSendAtCDN, compBot);
	}
	
	public static void sendatCPL(Composite comp) {
		grpSendAt = setGroup(grpSendAt, comp);
		compTop = setCompRight(compTop, grpSendAt);
		compBot = setCompLeft(compBot, grpSendAt);
		lNote = setLabelNote(lNote, compTop);
		lSendAt = setLabelSendAt(lSendAt, compBot);
//		tSendAtCPL = setTextSendAt(tSendAtCPL, compBot);
		tSendAtCPL = setTextSendAtCPL(compBot);
		bSendAtCPL = setButtonSendAt(bSendAtCPL, compBot);
	}
	
	public static void sendatEST(Composite comp) {
		grpSendAt = setGroup(grpSendAt, comp);
		compTop = setCompRight(compTop, grpSendAt);
		compBot = setCompLeft(compBot, grpSendAt);
		lNote = setLabelNote(lNote, compTop);
		lSendAt = setLabelSendAt(lSendAt, compBot);
//		tSendAtEST = setTextSendAt(tSendAtEST, compBot);
		tSendAtEST = setTextSendAtEST(compBot);
		bSendAtEST = setButtonSendAt(bSendAtEST, compBot);
	}
	
	public static void sendatACP(Composite comp) {
		grpSendAt = setGroup(grpSendAt, comp);
		compTop = setCompRight(compTop, grpSendAt);
		compBot = setCompLeft(compBot, grpSendAt);
		lNote = setLabelNote(lNote, compTop);
		lSendAt = setLabelSendAt(lSendAt, compBot);
//		tSendAtACP = setTextSendAt(tSendAtACP, compBot);
		tSendAtACP = setTextSendAtACP(compBot);
		bSendAtACP = setButtonSendAt(bSendAtACP, compBot);
	}
	
	public static void sendatLAM(Composite comp) {
		grpSendAt = setGroup(grpSendAt, comp);
		compTop = setCompRight(compTop, grpSendAt);
		compBot = setCompLeft(compBot, grpSendAt);
		lNote = setLabelNote(lNote, compTop);
		lSendAt = setLabelSendAt(lSendAt, compBot);
//		tSendAtLAM = setTextSendAt(tSendAtLAM, compBot);
		tSendAtLAM = setTextSendAtLAM(compBot);
		bSendAtLAM = setButtonSendAt(bSendAtLAM, compBot);
	}
	
	public static void sendatRQP(Composite comp) {
		grpSendAt = setGroup(grpSendAt, comp);
		compTop = setCompRight(compTop, grpSendAt);
		compBot = setCompLeft(compBot, grpSendAt);
		lNote = setLabelNote(lNote, compTop);
		lSendAt = setLabelSendAt(lSendAt, compBot);
//		tSendAtRQP = setTextSendAt(tSendAtRQP, compBot);
		tSendAtRQP = setTextSendAtRQP(compBot);
		bSendAtRQP = setButtonSendAt(bSendAtRQP, compBot);
	}
	
	public static void sendatRQS(Composite comp) {
		grpSendAt = setGroup(grpSendAt, comp);
		compTop = setCompRight(compTop, grpSendAt);
		compBot = setCompLeft(compBot, grpSendAt);
		lNote = setLabelNote(lNote, compTop);
		lSendAt = setLabelSendAt(lSendAt, compBot);
//		tSendAtRQS = setTextSendAt(tSendAtRQS, compBot);
		tSendAtRQS = setTextSendAtRQS(compBot);
		bSendAtRQS = setButtonSendAt(bSendAtRQS, compBot);
	}
	
	public static void sendatSPL(Composite comp) {
		grpSendAt = setGroup(grpSendAt, comp);
		compTop = setCompRight(compTop, grpSendAt);
		compBot = setCompLeft(compBot, grpSendAt);
		lNote = setLabelNote(lNote, compTop);
		lSendAt = setLabelSendAt(lSendAt, compBot);
//		tSendAtSPL = setTextSendAt(tSendAtSPL, compBot);
		tSendAtSPL = setTextSendAtSPL(compBot);
		bSendAtSPL = setButtonSendAt(bSendAtSPL, compBot);
	}
	
	
	//percobaan
	static Text setTextSendAtDLA(Composite compBot) {
		tSendAtDLA = new Text(compBot, SWT.BORDER); sh.textStyle(tSendAtDLA, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
		time.tgl(); tSendAtDLA.setText(time.filing_time);
		final Calendar caltSendAtDLA = Calendar.getInstance();
		tSendAtDLA.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tSendAtDLA.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tSendAtDLA.insert(buffer.toString());
					ignore = false;
					tSendAtDLA.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tSendAtDLA.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltSendAtDLA.set(Calendar.YEAR, 1901);
				caltSendAtDLA.set(Calendar.MONTH, Calendar.JANUARY);
				caltSendAtDLA.set(Calendar.DATE, 1);
				caltSendAtDLA.set(Calendar.HOUR_OF_DAY, 00);
				caltSendAtDLA.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltSendAtDLA.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltSendAtDLA.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltSendAtDLA.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltSendAtDLA.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltSendAtDLA.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltSendAtDLA.set(Calendar.MINUTE, minute);
				}
				tSendAtDLA.setSelection(e.start, e.start + length);
				ignore = true;
				tSendAtDLA.insert(newText);
				ignore = false;
			}
		});
		return tSendAtDLA;
    }
	

	static Text setTextSendAtFPL(Composite compBot) {
		tSendAtFPL = new Text(compBot, SWT.BORDER); sh.textStyle(tSendAtFPL, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
		time.tgl(); tSendAtFPL.setText(time.filing_time);
		final Calendar caltSendAtFPL = Calendar.getInstance();
		tSendAtFPL.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tSendAtFPL.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tSendAtFPL.insert(buffer.toString());
					ignore = false;
					tSendAtFPL.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tSendAtFPL.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltSendAtFPL.set(Calendar.YEAR, 1901);
				caltSendAtFPL.set(Calendar.MONTH, Calendar.JANUARY);
				caltSendAtFPL.set(Calendar.DATE, 1);
				caltSendAtFPL.set(Calendar.HOUR_OF_DAY, 00);
				caltSendAtFPL.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltSendAtFPL.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltSendAtFPL.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltSendAtFPL.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltSendAtFPL.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltSendAtFPL.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltSendAtFPL.set(Calendar.MINUTE, minute);
				}
				tSendAtFPL.setSelection(e.start, e.start + length);
				ignore = true;
				tSendAtFPL.insert(newText);
				ignore = false;
			}
		});
		return tSendAtFPL;
    }
	
	static Text setTextSendAtCHG(Composite compBot) {
		tSendAtCHG = new Text(compBot, SWT.BORDER); sh.textStyle(tSendAtCHG, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
		time.tgl(); tSendAtCHG.setText(time.filing_time);
		final Calendar caltSendAtCHG = Calendar.getInstance();
		tSendAtCHG.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tSendAtCHG.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tSendAtCHG.insert(buffer.toString());
					ignore = false;
					tSendAtCHG.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tSendAtCHG.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltSendAtCHG.set(Calendar.YEAR, 1901);
				caltSendAtCHG.set(Calendar.MONTH, Calendar.JANUARY);
				caltSendAtCHG.set(Calendar.DATE, 1);
				caltSendAtCHG.set(Calendar.HOUR_OF_DAY, 00);
				caltSendAtCHG.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltSendAtCHG.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltSendAtCHG.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltSendAtCHG.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltSendAtCHG.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltSendAtCHG.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltSendAtCHG.set(Calendar.MINUTE, minute);
				}
				tSendAtCHG.setSelection(e.start, e.start + length);
				ignore = true;
				tSendAtCHG.insert(newText);
				ignore = false;
			}
		});
		return tSendAtCHG;
    }
	
	static Text setTextSendAtCNL(Composite compBot) {
		tSendAtCNL = new Text(compBot, SWT.BORDER); sh.textStyle(tSendAtCNL, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
		time.tgl(); tSendAtCNL.setText(time.filing_time);
		final Calendar caltSendAtCNL = Calendar.getInstance();
		tSendAtCNL.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tSendAtCNL.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tSendAtCNL.insert(buffer.toString());
					ignore = false;
					tSendAtCNL.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tSendAtCNL.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltSendAtCNL.set(Calendar.YEAR, 1901);
				caltSendAtCNL.set(Calendar.MONTH, Calendar.JANUARY);
				caltSendAtCNL.set(Calendar.DATE, 1);
				caltSendAtCNL.set(Calendar.HOUR_OF_DAY, 00);
				caltSendAtCNL.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltSendAtCNL.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltSendAtCNL.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltSendAtCNL.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltSendAtCNL.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltSendAtCNL.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltSendAtCNL.set(Calendar.MINUTE, minute);
				}
				tSendAtCNL.setSelection(e.start, e.start + length);
				ignore = true;
				tSendAtCNL.insert(newText);
				ignore = false;
			}
		});
		return tSendAtCNL;
    }
	
	static Text setTextSendAtDEP(Composite compBot) {
		tSendAtDEP = new Text(compBot, SWT.BORDER); sh.textStyle(tSendAtDEP, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
		time.tgl(); tSendAtDEP.setText(time.filing_time);
		final Calendar caltSendAtDEP = Calendar.getInstance();
		tSendAtDEP.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tSendAtDEP.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tSendAtDEP.insert(buffer.toString());
					ignore = false;
					tSendAtDEP.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tSendAtDEP.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltSendAtDEP.set(Calendar.YEAR, 1901);
				caltSendAtDEP.set(Calendar.MONTH, Calendar.JANUARY);
				caltSendAtDEP.set(Calendar.DATE, 1);
				caltSendAtDEP.set(Calendar.HOUR_OF_DAY, 00);
				caltSendAtDEP.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltSendAtDEP.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltSendAtDEP.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltSendAtDEP.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltSendAtDEP.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltSendAtDEP.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltSendAtDEP.set(Calendar.MINUTE, minute);
				}
				tSendAtDEP.setSelection(e.start, e.start + length);
				ignore = true;
				tSendAtDEP.insert(newText);
				ignore = false;
			}
		});
		return tSendAtDEP;
    }
	
	static Text setTextSendAtARR(Composite compBot) {
		tSendAtARR = new Text(compBot, SWT.BORDER); sh.textStyle(tSendAtARR, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
		time.tgl(); tSendAtARR.setText(time.filing_time);
		final Calendar caltSendAtARR = Calendar.getInstance();
		tSendAtARR.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tSendAtARR.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tSendAtARR.insert(buffer.toString());
					ignore = false;
					tSendAtARR.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tSendAtARR.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltSendAtARR.set(Calendar.YEAR, 1901);
				caltSendAtARR.set(Calendar.MONTH, Calendar.JANUARY);
				caltSendAtARR.set(Calendar.DATE, 1);
				caltSendAtARR.set(Calendar.HOUR_OF_DAY, 00);
				caltSendAtARR.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltSendAtARR.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltSendAtARR.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltSendAtARR.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltSendAtARR.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltSendAtARR.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltSendAtARR.set(Calendar.MINUTE, minute);
				}
				tSendAtARR.setSelection(e.start, e.start + length);
				ignore = true;
				tSendAtARR.insert(newText);
				ignore = false;
			}
		});
		return tSendAtARR;
    }
	
	static Text setTextSendAtCDN(Composite compBot) {
		tSendAtCDN = new Text(compBot, SWT.BORDER); sh.textStyle(tSendAtCDN, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
		time.tgl(); tSendAtCDN.setText(time.filing_time);
		final Calendar caltSendAtCDN = Calendar.getInstance();
		tSendAtCDN.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tSendAtCDN.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tSendAtCDN.insert(buffer.toString());
					ignore = false;
					tSendAtCDN.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tSendAtCDN.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltSendAtCDN.set(Calendar.YEAR, 1901);
				caltSendAtCDN.set(Calendar.MONTH, Calendar.JANUARY);
				caltSendAtCDN.set(Calendar.DATE, 1);
				caltSendAtCDN.set(Calendar.HOUR_OF_DAY, 00);
				caltSendAtCDN.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltSendAtCDN.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltSendAtCDN.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltSendAtCDN.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltSendAtCDN.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltSendAtCDN.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltSendAtCDN.set(Calendar.MINUTE, minute);
				}
				tSendAtCDN.setSelection(e.start, e.start + length);
				ignore = true;
				tSendAtCDN.insert(newText);
				ignore = false;
			}
		});
		return tSendAtCDN;
    }
	
	static Text setTextSendAtCPL(Composite compBot) {
		tSendAtCPL = new Text(compBot, SWT.BORDER); sh.textStyle(tSendAtCPL, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
		time.tgl(); tSendAtCPL.setText(time.filing_time);
		final Calendar caltSendAtCPL = Calendar.getInstance();
		tSendAtCPL.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tSendAtCPL.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tSendAtCPL.insert(buffer.toString());
					ignore = false;
					tSendAtCPL.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tSendAtCPL.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltSendAtCPL.set(Calendar.YEAR, 1901);
				caltSendAtCPL.set(Calendar.MONTH, Calendar.JANUARY);
				caltSendAtCPL.set(Calendar.DATE, 1);
				caltSendAtCPL.set(Calendar.HOUR_OF_DAY, 00);
				caltSendAtCPL.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltSendAtCPL.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltSendAtCPL.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltSendAtCPL.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltSendAtCPL.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltSendAtCPL.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltSendAtCPL.set(Calendar.MINUTE, minute);
				}
				tSendAtCPL.setSelection(e.start, e.start + length);
				ignore = true;
				tSendAtCPL.insert(newText);
				ignore = false;
			}
		});
		return tSendAtCPL;
    }
	
	static Text setTextSendAtEST(Composite compBot) {
		tSendAtEST = new Text(compBot, SWT.BORDER); sh.textStyle(tSendAtEST, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
		time.tgl(); tSendAtEST.setText(time.filing_time);
		final Calendar caltSendAtEST = Calendar.getInstance();
		tSendAtEST.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tSendAtEST.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tSendAtEST.insert(buffer.toString());
					ignore = false;
					tSendAtEST.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tSendAtEST.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltSendAtEST.set(Calendar.YEAR, 1901);
				caltSendAtEST.set(Calendar.MONTH, Calendar.JANUARY);
				caltSendAtEST.set(Calendar.DATE, 1);
				caltSendAtEST.set(Calendar.HOUR_OF_DAY, 00);
				caltSendAtEST.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltSendAtEST.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltSendAtEST.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltSendAtEST.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltSendAtEST.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltSendAtEST.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltSendAtEST.set(Calendar.MINUTE, minute);
				}
				tSendAtEST.setSelection(e.start, e.start + length);
				ignore = true;
				tSendAtEST.insert(newText);
				ignore = false;
			}
		});
		return tSendAtEST;
    }
	
	static Text setTextSendAtACP(Composite compBot) {
		tSendAtACP = new Text(compBot, SWT.BORDER); sh.textStyle(tSendAtACP, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
		time.tgl(); tSendAtACP.setText(time.filing_time);
		final Calendar caltSendAtACP = Calendar.getInstance();
		tSendAtACP.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tSendAtACP.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tSendAtACP.insert(buffer.toString());
					ignore = false;
					tSendAtACP.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tSendAtACP.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltSendAtACP.set(Calendar.YEAR, 1901);
				caltSendAtACP.set(Calendar.MONTH, Calendar.JANUARY);
				caltSendAtACP.set(Calendar.DATE, 1);
				caltSendAtACP.set(Calendar.HOUR_OF_DAY, 00);
				caltSendAtACP.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltSendAtACP.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltSendAtACP.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltSendAtACP.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltSendAtACP.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltSendAtACP.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltSendAtACP.set(Calendar.MINUTE, minute);
				}
				tSendAtACP.setSelection(e.start, e.start + length);
				ignore = true;
				tSendAtACP.insert(newText);
				ignore = false;
			}
		});
		return tSendAtACP;
    }
	
	static Text setTextSendAtLAM(Composite compBot) {
		tSendAtLAM = new Text(compBot, SWT.BORDER); sh.textStyle(tSendAtLAM, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
		time.tgl(); tSendAtLAM.setText(time.filing_time);
		final Calendar caltSendAtLAM = Calendar.getInstance();
		tSendAtLAM.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tSendAtLAM.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tSendAtLAM.insert(buffer.toString());
					ignore = false;
					tSendAtLAM.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tSendAtLAM.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltSendAtLAM.set(Calendar.YEAR, 1901);
				caltSendAtLAM.set(Calendar.MONTH, Calendar.JANUARY);
				caltSendAtLAM.set(Calendar.DATE, 1);
				caltSendAtLAM.set(Calendar.HOUR_OF_DAY, 00);
				caltSendAtLAM.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltSendAtLAM.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltSendAtLAM.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltSendAtLAM.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltSendAtLAM.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltSendAtLAM.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltSendAtLAM.set(Calendar.MINUTE, minute);
				}
				tSendAtLAM.setSelection(e.start, e.start + length);
				ignore = true;
				tSendAtLAM.insert(newText);
				ignore = false;
			}
		});
		return tSendAtLAM;
    }
	
	static Text setTextSendAtRQP(Composite compBot) {
		tSendAtRQP = new Text(compBot, SWT.BORDER); sh.textStyle(tSendAtRQP, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
		time.tgl(); tSendAtRQP.setText(time.filing_time);
		final Calendar caltSendAtRQP = Calendar.getInstance();
		tSendAtRQP.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tSendAtRQP.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tSendAtRQP.insert(buffer.toString());
					ignore = false;
					tSendAtRQP.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tSendAtRQP.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltSendAtRQP.set(Calendar.YEAR, 1901);
				caltSendAtRQP.set(Calendar.MONTH, Calendar.JANUARY);
				caltSendAtRQP.set(Calendar.DATE, 1);
				caltSendAtRQP.set(Calendar.HOUR_OF_DAY, 00);
				caltSendAtRQP.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltSendAtRQP.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltSendAtRQP.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltSendAtRQP.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltSendAtRQP.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltSendAtRQP.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltSendAtRQP.set(Calendar.MINUTE, minute);
				}
				tSendAtRQP.setSelection(e.start, e.start + length);
				ignore = true;
				tSendAtRQP.insert(newText);
				ignore = false;
			}
		});
		return tSendAtRQP;
    }
	
	static Text setTextSendAtRQS(Composite compBot) {
		tSendAtRQS = new Text(compBot, SWT.BORDER); sh.textStyle(tSendAtRQS, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
		time.tgl(); tSendAtRQS.setText(time.filing_time);
		final Calendar caltSendAtRQS = Calendar.getInstance();
		tSendAtRQS.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tSendAtRQS.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tSendAtRQS.insert(buffer.toString());
					ignore = false;
					tSendAtRQS.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tSendAtRQS.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltSendAtRQS.set(Calendar.YEAR, 1901);
				caltSendAtRQS.set(Calendar.MONTH, Calendar.JANUARY);
				caltSendAtRQS.set(Calendar.DATE, 1);
				caltSendAtRQS.set(Calendar.HOUR_OF_DAY, 00);
				caltSendAtRQS.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltSendAtRQS.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltSendAtRQS.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltSendAtRQS.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltSendAtRQS.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltSendAtRQS.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltSendAtRQS.set(Calendar.MINUTE, minute);
				}
				tSendAtRQS.setSelection(e.start, e.start + length);
				ignore = true;
				tSendAtRQS.insert(newText);
				ignore = false;
			}
		});
		return tSendAtRQS;
    }
	
	static Text setTextSendAtSPL(Composite compBot) {
		tSendAtSPL = new Text(compBot, SWT.BORDER); sh.textStyle(tSendAtSPL, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
		time.tgl(); tSendAtSPL.setText(time.filing_time);
		final Calendar caltSendAtSPL = Calendar.getInstance();
		tSendAtSPL.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tSendAtSPL.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tSendAtSPL.insert(buffer.toString());
					ignore = false;
					tSendAtSPL.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tSendAtSPL.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltSendAtSPL.set(Calendar.YEAR, 1901);
				caltSendAtSPL.set(Calendar.MONTH, Calendar.JANUARY);
				caltSendAtSPL.set(Calendar.DATE, 1);
				caltSendAtSPL.set(Calendar.HOUR_OF_DAY, 00);
				caltSendAtSPL.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltSendAtSPL.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltSendAtSPL.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltSendAtSPL.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltSendAtSPL.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltSendAtSPL.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltSendAtSPL.set(Calendar.MINUTE, minute);
				}
				tSendAtSPL.setSelection(e.start, e.start + length);
				ignore = true;
				tSendAtSPL.insert(newText);
				ignore = false;
			}
		});
		return tSendAtSPL;
    }
	
	static Text setTextSendAtAFTN(Composite compBot) {
		tSendAtAFTN = new Text(compBot, SWT.BORDER); sh.textStyle(tSendAtAFTN, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
		time.tgl(); tSendAtAFTN.setText(time.filing_time);
		final Calendar caltSendAtAFTN = Calendar.getInstance();
		tSendAtAFTN.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tSendAtAFTN.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tSendAtAFTN.insert(buffer.toString());
					ignore = false;
					tSendAtAFTN.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tSendAtAFTN.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltSendAtAFTN.set(Calendar.YEAR, 1901);
				caltSendAtAFTN.set(Calendar.MONTH, Calendar.JANUARY);
				caltSendAtAFTN.set(Calendar.DATE, 1);
				caltSendAtAFTN.set(Calendar.HOUR_OF_DAY, 00);
				caltSendAtAFTN.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltSendAtAFTN.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltSendAtAFTN.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltSendAtAFTN.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltSendAtAFTN.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltSendAtAFTN.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltSendAtAFTN.set(Calendar.MINUTE, minute);
				}
				tSendAtAFTN.setSelection(e.start, e.start + length);
				ignore = true;
				tSendAtAFTN.insert(newText);
				ignore = false;
			}
		});
		return tSendAtAFTN;
    }
	
	static Text setTextSendAtALR(Composite compBot) {
		tSendAtALR = new Text(compBot, SWT.BORDER); sh.textStyle(tSendAtALR, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
		time.tgl(); tSendAtALR.setText(time.filing_time);
		final Calendar caltSendAtALR = Calendar.getInstance();
		tSendAtALR.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tSendAtALR.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tSendAtALR.insert(buffer.toString());
					ignore = false;
					tSendAtALR.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tSendAtALR.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltSendAtALR.set(Calendar.YEAR, 1901);
				caltSendAtALR.set(Calendar.MONTH, Calendar.JANUARY);
				caltSendAtALR.set(Calendar.DATE, 1);
				caltSendAtALR.set(Calendar.HOUR_OF_DAY, 00);
				caltSendAtALR.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltSendAtALR.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltSendAtALR.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltSendAtALR.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltSendAtALR.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltSendAtALR.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltSendAtALR.set(Calendar.MINUTE, minute);
				}
				tSendAtALR.setSelection(e.start, e.start + length);
				ignore = true;
				tSendAtALR.insert(newText);
				ignore = false;
			}
		});
		return tSendAtALR;
    }
	
	static Text setTextSendAtRCF(Composite compBot) {
		tSendAtRCF = new Text(compBot, SWT.BORDER); sh.textStyle(tSendAtRCF, 50, 6, SWT.LEFT, SWT.CENTER, "", "Send At (DDhhmm)", true);
		time.tgl(); tSendAtRCF.setText(time.filing_time);
		final Calendar caltSendAtRCF = Calendar.getInstance();
		tSendAtRCF.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; } //* [D]D *
							case 1: { buffer.append('0'); break; } //* D[D] *
							case 2: { buffer.append('0'); break; } //* [H]H *
							case 3: { buffer.append('0'); break; } //* H[H] *
							case 4: { buffer.append('0'); break; } //* [M]M * 
							case 5: { buffer.append('0'); break; } //* M[M] *
							default: return;
						}
					}
					tSendAtRCF.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tSendAtRCF.insert(buffer.toString());
					ignore = false;
					tSendAtRCF.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 10) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 0 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 2 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 4 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tSendAtRCF.getText());
				date.replace(e.start, e.start + length, newText);
				
				caltSendAtRCF.set(Calendar.YEAR, 1901);
				caltSendAtRCF.set(Calendar.MONTH, Calendar.JANUARY);
				caltSendAtRCF.set(Calendar.DATE, 1);
				caltSendAtRCF.set(Calendar.HOUR_OF_DAY, 00);
				caltSendAtRCF.set(Calendar.MINUTE, 00);
				
				String dd = date.substring(0,2); 
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = caltSendAtRCF.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					caltSendAtRCF.set(Calendar.DATE, day);
				} 
				String hh = date.substring(2,4); 
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = caltSendAtRCF.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					caltSendAtRCF.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(4,6); 
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = caltSendAtRCF.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					caltSendAtRCF.set(Calendar.MINUTE, minute);
				}
				tSendAtRCF.setSelection(e.start, e.start + length);
				ignore = true;
				tSendAtRCF.insert(newText);
				ignore = false;
			}
		});
		return tSendAtRCF;
    }
	
	//end percobaan
	
	
	
	

	//------------------------------------- HEADER ATS Messages -------------------------------------
	public static void headerAFTN(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPriorityAFTN = new Text(compP,SWT.BORDER);
		bPriorityAFTN = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1AFTN = new Text(compD,SWT.BORDER); 
		tDest2AFTN = new Text(compD,SWT.BORDER); 
		tDest3AFTN = new Text(compD,SWT.BORDER); 
		tDest4AFTN = new Text(compD,SWT.BORDER); 
		tDest5AFTN = new Text(compD,SWT.BORDER); 
		tDest6AFTN = new Text(compD,SWT.BORDER); 
		tDest7AFTN = new Text(compD,SWT.BORDER); 
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD1 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD1, 7, SWT.LEFT, SWT.CENTER);
		tDest8AFTN = new Text(compD1,SWT.BORDER); 
		tDest9AFTN = new Text(compD1,SWT.BORDER); 
		tDest10AFTN = new Text(compD1,SWT.BORDER);
		tDest11AFTN = new Text(compD1,SWT.BORDER);
		tDest12AFTN = new Text(compD1,SWT.BORDER);
		tDest13AFTN = new Text(compD1,SWT.BORDER);
		tDest14AFTN = new Text(compD1,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD2 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD2, 7, SWT.LEFT, SWT.CENTER);
		tDest15AFTN = new Text(compD2,SWT.BORDER);
		tDest16AFTN = new Text(compD2,SWT.BORDER);
		tDest17AFTN = new Text(compD2,SWT.BORDER);
		tDest18AFTN = new Text(compD2,SWT.BORDER);
		tDest19AFTN = new Text(compD2,SWT.BORDER);
		tDest20AFTN = new Text(compD2,SWT.BORDER);
		tDest21AFTN = new Text(compD2,SWT.BORDER);
		
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeAFTN = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorAFTN = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefAFTN = new Text(compO,SWT.BORDER);
		bBellAFTN = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPriorityAFTN,bPriorityAFTN,la,lAddress,tDest1AFTN,tDest2AFTN,tDest3AFTN,tDest4AFTN,tDest5AFTN,tDest6AFTN,tDest7AFTN,
				tDest8AFTN,tDest9AFTN,tDest10AFTN,tDest11AFTN,tDest12AFTN,tDest13AFTN,tDest14AFTN,tDest15AFTN,tDest16AFTN,tDest17AFTN,
				tDest18AFTN,tDest19AFTN,tDest20AFTN,tDest21AFTN,lFiling,tFilingTimeAFTN,bTimeATS,lb,lOriginator,tOriginatorAFTN,lOriref,
				tOriRefAFTN,bBellAFTN);
	}
	public static void headerAFTN1(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPriorityAFTN = new Text(compP,SWT.BORDER);
		bPriorityAFTN = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1AFTN = new Text(compD,SWT.BORDER); 
		tDest2AFTN = new Text(compD,SWT.BORDER); 
		tDest3AFTN = new Text(compD,SWT.BORDER); 
		tDest4AFTN = new Text(compD,SWT.BORDER); 
		tDest5AFTN = new Text(compD,SWT.BORDER); 
		tDest6AFTN = new Text(compD,SWT.BORDER); 
		tDest7AFTN = new Text(compD,SWT.BORDER); 
		tDest8AFTN = new Text(compD,SWT.BORDER); 
		tDest9AFTN = new Text(compD,SWT.BORDER); 
		tDest10AFTN = new Text(compD,SWT.BORDER);
		tDest11AFTN = new Text(compD,SWT.BORDER);
		tDest12AFTN = new Text(compD,SWT.BORDER);
		tDest13AFTN = new Text(compD,SWT.BORDER);
		tDest14AFTN = new Text(compD,SWT.BORDER);
		tDest15AFTN = new Text(compD,SWT.BORDER);
		tDest16AFTN = new Text(compD,SWT.BORDER);
		tDest17AFTN = new Text(compD,SWT.BORDER);
		tDest18AFTN = new Text(compD,SWT.BORDER);
		tDest19AFTN = new Text(compD,SWT.BORDER);
		tDest20AFTN = new Text(compD,SWT.BORDER);
		tDest21AFTN = new Text(compD,SWT.BORDER);
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeAFTN = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorAFTN = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefAFTN = new Text(compO,SWT.BORDER);
		bBellAFTN = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPriorityAFTN,bPriorityAFTN,la,lAddress,tDest1AFTN,tDest2AFTN,tDest3AFTN,tDest4AFTN,tDest5AFTN,tDest6AFTN,tDest7AFTN,
				tDest8AFTN,tDest9AFTN,tDest10AFTN,tDest11AFTN,tDest12AFTN,tDest13AFTN,tDest14AFTN,tDest15AFTN,tDest16AFTN,tDest17AFTN,
				tDest18AFTN,tDest19AFTN,tDest20AFTN,tDest21AFTN,lFiling,tFilingTimeAFTN,bTimeATS,lb,lOriginator,tOriginatorAFTN,lOriref,
				tOriRefAFTN,bBellAFTN);
	}
	public static void headerALR(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPriorityALR = new Text(compP,SWT.BORDER);
		bPriorityALR = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1ALR = new Text(compD,SWT.BORDER); 
		tDest2ALR = new Text(compD,SWT.BORDER); 
		tDest3ALR = new Text(compD,SWT.BORDER); 
		tDest4ALR = new Text(compD,SWT.BORDER); 
		tDest5ALR = new Text(compD,SWT.BORDER); 
		tDest6ALR = new Text(compD,SWT.BORDER); 
		tDest7ALR = new Text(compD,SWT.BORDER); 
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD1 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD1, 7, SWT.LEFT, SWT.CENTER);
		tDest8ALR = new Text(compD1,SWT.BORDER); 
		tDest9ALR = new Text(compD1,SWT.BORDER); 
		tDest10ALR = new Text(compD1,SWT.BORDER);
		tDest11ALR = new Text(compD1,SWT.BORDER);
		tDest12ALR = new Text(compD1,SWT.BORDER);
		tDest13ALR = new Text(compD1,SWT.BORDER);
		tDest14ALR = new Text(compD1,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD2 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD2, 7, SWT.LEFT, SWT.CENTER);
		tDest15ALR = new Text(compD2,SWT.BORDER);
		tDest16ALR = new Text(compD2,SWT.BORDER);
		tDest17ALR = new Text(compD2,SWT.BORDER);
		tDest18ALR = new Text(compD2,SWT.BORDER);
		tDest19ALR = new Text(compD2,SWT.BORDER);
		tDest20ALR = new Text(compD2,SWT.BORDER);
		tDest21ALR = new Text(compD2,SWT.BORDER);
		
//		tDest8ALR = new Text(compD,SWT.BORDER); 
//		tDest9ALR = new Text(compD,SWT.BORDER); 
//		tDest10ALR = new Text(compD,SWT.BORDER);
//		tDest11ALR = new Text(compD,SWT.BORDER);
//		tDest12ALR = new Text(compD,SWT.BORDER);
//		tDest13ALR = new Text(compD,SWT.BORDER);
//		tDest14ALR = new Text(compD,SWT.BORDER);
//		tDest15ALR = new Text(compD,SWT.BORDER);
//		tDest16ALR = new Text(compD,SWT.BORDER);
//		tDest17ALR = new Text(compD,SWT.BORDER);
//		tDest18ALR = new Text(compD,SWT.BORDER);
//		tDest19ALR = new Text(compD,SWT.BORDER);
//		tDest20ALR = new Text(compD,SWT.BORDER);
//		tDest21ALR = new Text(compD,SWT.BORDER);
		
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeALR = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorALR = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefALR = new Text(compO,SWT.BORDER);
		bBellALR = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPriorityALR,bPriorityALR,la,lAddress,tDest1ALR,tDest2ALR,tDest3ALR,tDest4ALR,tDest5ALR,tDest6ALR,tDest7ALR,
				tDest8ALR,tDest9ALR,tDest10ALR,tDest11ALR,tDest12ALR,tDest13ALR,tDest14ALR,tDest15ALR,tDest16ALR,tDest17ALR,
				tDest18ALR,tDest19ALR,tDest20ALR,tDest21ALR,lFiling,tFilingTimeALR,bTimeATS,lb,lOriginator,tOriginatorALR,lOriref,
				tOriRefALR,bBellALR);
	}
	
	public static void headerRCF(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPriorityRCF = new Text(compP,SWT.BORDER);
		bPriorityRCF = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1RCF = new Text(compD,SWT.BORDER); 
		tDest2RCF = new Text(compD,SWT.BORDER); 
		tDest3RCF = new Text(compD,SWT.BORDER); 
		tDest4RCF = new Text(compD,SWT.BORDER); 
		tDest5RCF = new Text(compD,SWT.BORDER); 
		tDest6RCF = new Text(compD,SWT.BORDER); 
		tDest7RCF = new Text(compD,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD1 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD1, 7, SWT.LEFT, SWT.CENTER);
		tDest8RCF = new Text(compD1,SWT.BORDER); 
		tDest9RCF = new Text(compD1,SWT.BORDER); 
		tDest10RCF = new Text(compD1,SWT.BORDER);
		tDest11RCF = new Text(compD1,SWT.BORDER);
		tDest12RCF = new Text(compD1,SWT.BORDER);
		tDest13RCF = new Text(compD1,SWT.BORDER);
		tDest14RCF = new Text(compD1,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD2 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD2, 7, SWT.LEFT, SWT.CENTER);
		tDest15RCF = new Text(compD2,SWT.BORDER);
		tDest16RCF = new Text(compD2,SWT.BORDER);
		tDest17RCF = new Text(compD2,SWT.BORDER);
		tDest18RCF = new Text(compD2,SWT.BORDER);
		tDest19RCF = new Text(compD2,SWT.BORDER);
		tDest20RCF = new Text(compD2,SWT.BORDER);
		tDest21RCF = new Text(compD2,SWT.BORDER);
		 
//		tDest8RCF = new Text(compD,SWT.BORDER); 
//		tDest9RCF = new Text(compD,SWT.BORDER); 
//		tDest10RCF = new Text(compD,SWT.BORDER);
//		tDest11RCF = new Text(compD,SWT.BORDER);
//		tDest12RCF = new Text(compD,SWT.BORDER);
//		tDest13RCF = new Text(compD,SWT.BORDER);
//		tDest14RCF = new Text(compD,SWT.BORDER);
//		tDest15RCF = new Text(compD,SWT.BORDER);
//		tDest16RCF = new Text(compD,SWT.BORDER);
//		tDest17RCF = new Text(compD,SWT.BORDER);
//		tDest18RCF = new Text(compD,SWT.BORDER);
//		tDest19RCF = new Text(compD,SWT.BORDER);
//		tDest20RCF = new Text(compD,SWT.BORDER);
//		tDest21RCF = new Text(compD,SWT.BORDER);
		
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeRCF = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorRCF = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefRCF = new Text(compO,SWT.BORDER);
		bBellRCF = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPriorityRCF,bPriorityRCF,la,lAddress,tDest1RCF,tDest2RCF,tDest3RCF,tDest4RCF,tDest5RCF,tDest6RCF,tDest7RCF,
				tDest8RCF,tDest9RCF,tDest10RCF,tDest11RCF,tDest12RCF,tDest13RCF,tDest14RCF,tDest15RCF,tDest16RCF,tDest17RCF,
				tDest18RCF,tDest19RCF,tDest20RCF,tDest21RCF,lFiling,tFilingTimeRCF,bTimeATS,lb,lOriginator,tOriginatorRCF,lOriref,
				tOriRefRCF,bBellRCF);
	}
	
	public static void headerFPL(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPriorityFPL = new Text(compP,SWT.BORDER);
		bPriorityFPL = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1FPL = new Text(compD,SWT.BORDER); 
		tDest2FPL = new Text(compD,SWT.BORDER); 
		tDest3FPL = new Text(compD,SWT.BORDER); 
		tDest4FPL = new Text(compD,SWT.BORDER); 
		tDest5FPL = new Text(compD,SWT.BORDER); 
		tDest6FPL = new Text(compD,SWT.BORDER); 
		tDest7FPL = new Text(compD,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD1 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD1, 7, SWT.LEFT, SWT.CENTER);
		tDest8FPL = new Text(compD1,SWT.BORDER); 
		tDest9FPL = new Text(compD1,SWT.BORDER); 
		tDest10FPL = new Text(compD1,SWT.BORDER);
		tDest11FPL = new Text(compD1,SWT.BORDER);
		tDest12FPL = new Text(compD1,SWT.BORDER);
		tDest13FPL = new Text(compD1,SWT.BORDER);
		tDest14FPL = new Text(compD1,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD2 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD2, 7, SWT.LEFT, SWT.CENTER);
		tDest15FPL = new Text(compD2,SWT.BORDER);
		tDest16FPL = new Text(compD2,SWT.BORDER);
		tDest17FPL = new Text(compD2,SWT.BORDER);
		tDest18FPL = new Text(compD2,SWT.BORDER);
		tDest19FPL = new Text(compD2,SWT.BORDER);
		tDest20FPL = new Text(compD2,SWT.BORDER);
		tDest21FPL = new Text(compD2,SWT.BORDER);
		 
//		tDest8FPL = new Text(compD,SWT.BORDER); 
//		tDest9FPL = new Text(compD,SWT.BORDER); 
//		tDest10FPL = new Text(compD,SWT.BORDER);
//		tDest11FPL = new Text(compD,SWT.BORDER);
//		tDest12FPL = new Text(compD,SWT.BORDER);
//		tDest13FPL = new Text(compD,SWT.BORDER);
//		tDest14FPL = new Text(compD,SWT.BORDER);
//		tDest15FPL = new Text(compD,SWT.BORDER);
//		tDest16FPL = new Text(compD,SWT.BORDER);
//		tDest17FPL = new Text(compD,SWT.BORDER);
//		tDest18FPL = new Text(compD,SWT.BORDER);
//		tDest19FPL = new Text(compD,SWT.BORDER);
//		tDest20FPL = new Text(compD,SWT.BORDER);
//		tDest21FPL = new Text(compD,SWT.BORDER);
		
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeFPL = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorFPL = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefFPL = new Text(compO,SWT.BORDER);
		bBellFPL = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPriorityFPL,bPriorityFPL,la,lAddress,tDest1FPL,tDest2FPL,tDest3FPL,tDest4FPL,tDest5FPL,tDest6FPL,tDest7FPL,
				tDest8FPL,tDest9FPL,tDest10FPL,tDest11FPL,tDest12FPL,tDest13FPL,tDest14FPL,tDest15FPL,tDest16FPL,tDest17FPL,
				tDest18FPL,tDest19FPL,tDest20FPL,tDest21FPL,lFiling,tFilingTimeFPL,bTimeATS,lb,lOriginator,tOriginatorFPL,lOriref,
				tOriRefFPL,bBellFPL);
		
		compBtn = new Composite(comp, SWT.BORDER); sh.compositeStyle(compBtn, 2, SWT.LEFT, SWT.CENTER, FormTemplates.iWidthComp-10);
		compTop = new Composite(compBtn, SWT.NONE); sh.compositeStyle(compTop, 2, SWT.LEFT, SWT.CENTER);
		compBot = new Composite(compBtn, SWT.NONE); sh.compositeStyle(compBot, 5, SWT.RIGHT, SWT.CENTER);
		
		btnDLA = new Button(compBot, SWT.PUSH);
		btnCHG = new Button(compBot, SWT.PUSH);
		btnCNL = new Button(compBot, SWT.PUSH);
		btnDEP = new Button(compBot, SWT.PUSH);
		btnARR = new Button(compBot, SWT.PUSH);
		addMiniBtnListener();
		bs.miniFPL(btnDLA, btnCHG, btnCNL, btnDEP, btnARR);
		
		Label label = new Label(compTop, SWT.RIGHT); sh.labelStyle1(label, "REG/", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		regText = new Text(compTop, SWT.BORDER); sh.textStyle(regText, 70, 7, SWT.LEFT, SWT.CENTER, sh.alphanum, "Search by REG/ indicator in Item 18", true);
	}
	
	public static void headerDLA(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPriorityDLA = new Text(compP,SWT.BORDER);
		bPriorityDLA = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1DLA = new Text(compD,SWT.BORDER); 
		tDest2DLA = new Text(compD,SWT.BORDER); 
		tDest3DLA = new Text(compD,SWT.BORDER); 
		tDest4DLA = new Text(compD,SWT.BORDER); 
		tDest5DLA = new Text(compD,SWT.BORDER); 
		tDest6DLA = new Text(compD,SWT.BORDER); 
		tDest7DLA = new Text(compD,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD1 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD1, 7, SWT.LEFT, SWT.CENTER);
		tDest8DLA = new Text(compD1,SWT.BORDER); 
		tDest9DLA = new Text(compD1,SWT.BORDER); 
		tDest10DLA = new Text(compD1,SWT.BORDER);
		tDest11DLA = new Text(compD1,SWT.BORDER);
		tDest12DLA = new Text(compD1,SWT.BORDER);
		tDest13DLA = new Text(compD1,SWT.BORDER);
		tDest14DLA = new Text(compD1,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD2 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD2, 7, SWT.LEFT, SWT.CENTER);
		tDest15DLA = new Text(compD2,SWT.BORDER);
		tDest16DLA = new Text(compD2,SWT.BORDER);
		tDest17DLA = new Text(compD2,SWT.BORDER);
		tDest18DLA = new Text(compD2,SWT.BORDER);
		tDest19DLA = new Text(compD2,SWT.BORDER);
		tDest20DLA = new Text(compD2,SWT.BORDER);
		tDest21DLA = new Text(compD2,SWT.BORDER);
		 
//		tDest8DLA = new Text(compD,SWT.BORDER); 
//		tDest9DLA = new Text(compD,SWT.BORDER); 
//		tDest10DLA = new Text(compD,SWT.BORDER);
//		tDest11DLA = new Text(compD,SWT.BORDER);
//		tDest12DLA = new Text(compD,SWT.BORDER);
//		tDest13DLA = new Text(compD,SWT.BORDER);
//		tDest14DLA = new Text(compD,SWT.BORDER);
//		tDest15DLA = new Text(compD,SWT.BORDER);
//		tDest16DLA = new Text(compD,SWT.BORDER);
//		tDest17DLA = new Text(compD,SWT.BORDER);
//		tDest18DLA = new Text(compD,SWT.BORDER);
//		tDest19DLA = new Text(compD,SWT.BORDER);
//		tDest20DLA = new Text(compD,SWT.BORDER);
//		tDest21DLA = new Text(compD,SWT.BORDER);
		
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeDLA = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorDLA = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefDLA = new Text(compO,SWT.BORDER);
		bBellDLA = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPriorityDLA,bPriorityDLA,la,lAddress,tDest1DLA,tDest2DLA,tDest3DLA,tDest4DLA,tDest5DLA,tDest6DLA,tDest7DLA,
				tDest8DLA,tDest9DLA,tDest10DLA,tDest11DLA,tDest12DLA,tDest13DLA,tDest14DLA,tDest15DLA,tDest16DLA,tDest17DLA,
				tDest18DLA,tDest19DLA,tDest20DLA,tDest21DLA,lFiling,tFilingTimeDLA,bTimeATS,lb,lOriginator,tOriginatorDLA,lOriref,
				tOriRefDLA,bBellDLA);
	}
	
	public static void headerCHG(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPriorityCHG = new Text(compP,SWT.BORDER);
		bPriorityCHG = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1CHG = new Text(compD,SWT.BORDER); 
		tDest2CHG = new Text(compD,SWT.BORDER); 
		tDest3CHG = new Text(compD,SWT.BORDER); 
		tDest4CHG = new Text(compD,SWT.BORDER); 
		tDest5CHG = new Text(compD,SWT.BORDER); 
		tDest6CHG = new Text(compD,SWT.BORDER); 
		tDest7CHG = new Text(compD,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD1 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD1, 7, SWT.LEFT, SWT.CENTER);
		tDest8CHG = new Text(compD1,SWT.BORDER); 
		tDest9CHG = new Text(compD1,SWT.BORDER); 
		tDest10CHG = new Text(compD1,SWT.BORDER);
		tDest11CHG = new Text(compD1,SWT.BORDER);
		tDest12CHG = new Text(compD1,SWT.BORDER);
		tDest13CHG = new Text(compD1,SWT.BORDER);
		tDest14CHG = new Text(compD1,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD2 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD2, 7, SWT.LEFT, SWT.CENTER);
		tDest15CHG = new Text(compD2,SWT.BORDER);
		tDest16CHG = new Text(compD2,SWT.BORDER);
		tDest17CHG = new Text(compD2,SWT.BORDER);
		tDest18CHG = new Text(compD2,SWT.BORDER);
		tDest19CHG = new Text(compD2,SWT.BORDER);
		tDest20CHG = new Text(compD2,SWT.BORDER);
		tDest21CHG = new Text(compD2,SWT.BORDER);
		 
//		tDest8CHG = new Text(compD,SWT.BORDER); 
//		tDest9CHG = new Text(compD,SWT.BORDER); 
//		tDest10CHG = new Text(compD,SWT.BORDER);
//		tDest11CHG = new Text(compD,SWT.BORDER);
//		tDest12CHG = new Text(compD,SWT.BORDER);
//		tDest13CHG = new Text(compD,SWT.BORDER);
//		tDest14CHG = new Text(compD,SWT.BORDER);
//		tDest15CHG = new Text(compD,SWT.BORDER);
//		tDest16CHG = new Text(compD,SWT.BORDER);
//		tDest17CHG = new Text(compD,SWT.BORDER);
//		tDest18CHG = new Text(compD,SWT.BORDER);
//		tDest19CHG = new Text(compD,SWT.BORDER);
//		tDest20CHG = new Text(compD,SWT.BORDER);
//		tDest21CHG = new Text(compD,SWT.BORDER);
		
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeCHG = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorCHG = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefCHG = new Text(compO,SWT.BORDER);
		bBellCHG = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPriorityCHG,bPriorityCHG,la,lAddress,tDest1CHG,tDest2CHG,tDest3CHG,tDest4CHG,tDest5CHG,tDest6CHG,tDest7CHG,
				tDest8CHG,tDest9CHG,tDest10CHG,tDest11CHG,tDest12CHG,tDest13CHG,tDest14CHG,tDest15CHG,tDest16CHG,tDest17CHG,
				tDest18CHG,tDest19CHG,tDest20CHG,tDest21CHG,lFiling,tFilingTimeCHG,bTimeATS,lb,lOriginator,tOriginatorCHG,lOriref,
				tOriRefCHG,bBellCHG);
	}
	
	public static void headerCNL(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPriorityCNL = new Text(compP,SWT.BORDER);
		bPriorityCNL = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1CNL = new Text(compD,SWT.BORDER); 
		tDest2CNL = new Text(compD,SWT.BORDER); 
		tDest3CNL = new Text(compD,SWT.BORDER); 
		tDest4CNL = new Text(compD,SWT.BORDER); 
		tDest5CNL = new Text(compD,SWT.BORDER); 
		tDest6CNL = new Text(compD,SWT.BORDER); 
		tDest7CNL = new Text(compD,SWT.BORDER); 
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD1 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD1, 7, SWT.LEFT, SWT.CENTER);
		tDest8CNL = new Text(compD1,SWT.BORDER); 
		tDest9CNL = new Text(compD1,SWT.BORDER); 
		tDest10CNL = new Text(compD1,SWT.BORDER);
		tDest11CNL = new Text(compD1,SWT.BORDER);
		tDest12CNL = new Text(compD1,SWT.BORDER);
		tDest13CNL = new Text(compD1,SWT.BORDER);
		tDest14CNL = new Text(compD1,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD2 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD2, 7, SWT.LEFT, SWT.CENTER);
		tDest15CNL = new Text(compD2,SWT.BORDER);
		tDest16CNL = new Text(compD2,SWT.BORDER);
		tDest17CNL = new Text(compD2,SWT.BORDER);
		tDest18CNL = new Text(compD2,SWT.BORDER);
		tDest19CNL = new Text(compD2,SWT.BORDER);
		tDest20CNL = new Text(compD2,SWT.BORDER);
		tDest21CNL = new Text(compD2,SWT.BORDER);
		
//		tDest8CNL = new Text(compD,SWT.BORDER); 
//		tDest9CNL = new Text(compD,SWT.BORDER); 
//		tDest10CNL = new Text(compD,SWT.BORDER);
//		tDest11CNL = new Text(compD,SWT.BORDER);
//		tDest12CNL = new Text(compD,SWT.BORDER);
//		tDest13CNL = new Text(compD,SWT.BORDER);
//		tDest14CNL = new Text(compD,SWT.BORDER);
//		tDest15CNL = new Text(compD,SWT.BORDER);
//		tDest16CNL = new Text(compD,SWT.BORDER);
//		tDest17CNL = new Text(compD,SWT.BORDER);
//		tDest18CNL = new Text(compD,SWT.BORDER);
//		tDest19CNL = new Text(compD,SWT.BORDER);
//		tDest20CNL = new Text(compD,SWT.BORDER);
//		tDest21CNL = new Text(compD,SWT.BORDER);
		
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeCNL = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorCNL = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefCNL = new Text(compO,SWT.BORDER);
		bBellCNL = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPriorityCNL,bPriorityCNL,la,lAddress,tDest1CNL,tDest2CNL,tDest3CNL,tDest4CNL,tDest5CNL,tDest6CNL,tDest7CNL,
				tDest8CNL,tDest9CNL,tDest10CNL,tDest11CNL,tDest12CNL,tDest13CNL,tDest14CNL,tDest15CNL,tDest16CNL,tDest17CNL,
				tDest18CNL,tDest19CNL,tDest20CNL,tDest21CNL,lFiling,tFilingTimeCNL,bTimeATS,lb,lOriginator,tOriginatorCNL,lOriref,
				tOriRefCNL,bBellCNL);
	}
	
	public static void headerDEP(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPriorityDEP = new Text(compP,SWT.BORDER);
		bPriorityDEP = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1DEP = new Text(compD,SWT.BORDER); 
		tDest2DEP = new Text(compD,SWT.BORDER); 
		tDest3DEP = new Text(compD,SWT.BORDER); 
		tDest4DEP = new Text(compD,SWT.BORDER); 
		tDest5DEP = new Text(compD,SWT.BORDER); 
		tDest6DEP = new Text(compD,SWT.BORDER); 
		tDest7DEP = new Text(compD,SWT.BORDER); 
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD1 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD1, 7, SWT.LEFT, SWT.CENTER);
		tDest8DEP = new Text(compD1,SWT.BORDER); 
		tDest9DEP = new Text(compD1,SWT.BORDER); 
		tDest10DEP = new Text(compD1,SWT.BORDER);
		tDest11DEP = new Text(compD1,SWT.BORDER);
		tDest12DEP = new Text(compD1,SWT.BORDER);
		tDest13DEP = new Text(compD1,SWT.BORDER);
		tDest14DEP = new Text(compD1,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD2 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD2, 7, SWT.LEFT, SWT.CENTER);
		tDest15DEP = new Text(compD2,SWT.BORDER);
		tDest16DEP = new Text(compD2,SWT.BORDER);
		tDest17DEP = new Text(compD2,SWT.BORDER);
		tDest18DEP = new Text(compD2,SWT.BORDER);
		tDest19DEP = new Text(compD2,SWT.BORDER);
		tDest20DEP = new Text(compD2,SWT.BORDER);
		tDest21DEP = new Text(compD2,SWT.BORDER);
		
//		tDest8DEP = new Text(compD,SWT.BORDER); 
//		tDest9DEP = new Text(compD,SWT.BORDER); 
//		tDest10DEP = new Text(compD,SWT.BORDER);
//		tDest11DEP = new Text(compD,SWT.BORDER);
//		tDest12DEP = new Text(compD,SWT.BORDER);
//		tDest13DEP = new Text(compD,SWT.BORDER);
//		tDest14DEP = new Text(compD,SWT.BORDER);
//		tDest15DEP = new Text(compD,SWT.BORDER);
//		tDest16DEP = new Text(compD,SWT.BORDER);
//		tDest17DEP = new Text(compD,SWT.BORDER);
//		tDest18DEP = new Text(compD,SWT.BORDER);
//		tDest19DEP = new Text(compD,SWT.BORDER);
//		tDest20DEP = new Text(compD,SWT.BORDER);
//		tDest21DEP = new Text(compD,SWT.BORDER);
		
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeDEP = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorDEP = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefDEP = new Text(compO,SWT.BORDER);
		bBellDEP = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPriorityDEP,bPriorityDEP,la,lAddress,tDest1DEP,tDest2DEP,tDest3DEP,tDest4DEP,tDest5DEP,tDest6DEP,tDest7DEP,
				tDest8DEP,tDest9DEP,tDest10DEP,tDest11DEP,tDest12DEP,tDest13DEP,tDest14DEP,tDest15DEP,tDest16DEP,tDest17DEP,
				tDest18DEP,tDest19DEP,tDest20DEP,tDest21DEP,lFiling,tFilingTimeDEP,bTimeATS,lb,lOriginator,tOriginatorDEP,lOriref,
				tOriRefDEP,bBellDEP);
	}
	
	public static void headerARR(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPriorityARR = new Text(compP,SWT.BORDER);
		bPriorityARR = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1ARR = new Text(compD,SWT.BORDER); 
		tDest2ARR = new Text(compD,SWT.BORDER); 
		tDest3ARR = new Text(compD,SWT.BORDER); 
		tDest4ARR = new Text(compD,SWT.BORDER); 
		tDest5ARR = new Text(compD,SWT.BORDER); 
		tDest6ARR = new Text(compD,SWT.BORDER); 
		tDest7ARR = new Text(compD,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD1 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD1, 7, SWT.LEFT, SWT.CENTER);
		tDest8ARR = new Text(compD1,SWT.BORDER); 
		tDest9ARR = new Text(compD1,SWT.BORDER); 
		tDest10ARR = new Text(compD1,SWT.BORDER);
		tDest11ARR = new Text(compD1,SWT.BORDER);
		tDest12ARR = new Text(compD1,SWT.BORDER);
		tDest13ARR = new Text(compD1,SWT.BORDER);
		tDest14ARR = new Text(compD1,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD2 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD2, 7, SWT.LEFT, SWT.CENTER);
		tDest15ARR = new Text(compD2,SWT.BORDER);
		tDest16ARR = new Text(compD2,SWT.BORDER);
		tDest17ARR = new Text(compD2,SWT.BORDER);
		tDest18ARR = new Text(compD2,SWT.BORDER);
		tDest19ARR = new Text(compD2,SWT.BORDER);
		tDest20ARR = new Text(compD2,SWT.BORDER);
		tDest21ARR = new Text(compD2,SWT.BORDER);
		 
//		tDest8ARR = new Text(compD,SWT.BORDER); 
//		tDest9ARR = new Text(compD,SWT.BORDER); 
//		tDest10ARR = new Text(compD,SWT.BORDER);
//		tDest11ARR = new Text(compD,SWT.BORDER);
//		tDest12ARR = new Text(compD,SWT.BORDER);
//		tDest13ARR = new Text(compD,SWT.BORDER);
//		tDest14ARR = new Text(compD,SWT.BORDER);
//		tDest15ARR = new Text(compD,SWT.BORDER);
//		tDest16ARR = new Text(compD,SWT.BORDER);
//		tDest17ARR = new Text(compD,SWT.BORDER);
//		tDest18ARR = new Text(compD,SWT.BORDER);
//		tDest19ARR = new Text(compD,SWT.BORDER);
//		tDest20ARR = new Text(compD,SWT.BORDER);
//		tDest21ARR = new Text(compD,SWT.BORDER);
		
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeARR = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorARR = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefARR = new Text(compO,SWT.BORDER);
		bBellARR = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPriorityARR,bPriorityARR,la,lAddress,tDest1ARR,tDest2ARR,tDest3ARR,tDest4ARR,tDest5ARR,tDest6ARR,tDest7ARR,
				tDest8ARR,tDest9ARR,tDest10ARR,tDest11ARR,tDest12ARR,tDest13ARR,tDest14ARR,tDest15ARR,tDest16ARR,tDest17ARR,
				tDest18ARR,tDest19ARR,tDest20ARR,tDest21ARR,lFiling,tFilingTimeARR,bTimeATS,lb,lOriginator,tOriginatorARR,lOriref,
				tOriRefARR,bBellARR);
	}
	
	public static void headerCDN(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPriorityCDN = new Text(compP,SWT.BORDER);
		bPriorityCDN = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1CDN = new Text(compD,SWT.BORDER); 
		tDest2CDN = new Text(compD,SWT.BORDER); 
		tDest3CDN = new Text(compD,SWT.BORDER); 
		tDest4CDN = new Text(compD,SWT.BORDER); 
		tDest5CDN = new Text(compD,SWT.BORDER); 
		tDest6CDN = new Text(compD,SWT.BORDER); 
		tDest7CDN = new Text(compD,SWT.BORDER); 
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD1 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD1, 7, SWT.LEFT, SWT.CENTER);
		tDest8CDN = new Text(compD1,SWT.BORDER); 
		tDest9CDN = new Text(compD1,SWT.BORDER); 
		tDest10CDN = new Text(compD1,SWT.BORDER);
		tDest11CDN = new Text(compD1,SWT.BORDER);
		tDest12CDN = new Text(compD1,SWT.BORDER);
		tDest13CDN = new Text(compD1,SWT.BORDER);
		tDest14CDN = new Text(compD1,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD2 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD2, 7, SWT.LEFT, SWT.CENTER);
		tDest15CDN = new Text(compD2,SWT.BORDER);
		tDest16CDN = new Text(compD2,SWT.BORDER);
		tDest17CDN = new Text(compD2,SWT.BORDER);
		tDest18CDN = new Text(compD2,SWT.BORDER);
		tDest19CDN = new Text(compD2,SWT.BORDER);
		tDest20CDN = new Text(compD2,SWT.BORDER);
		tDest21CDN = new Text(compD2,SWT.BORDER);
		
//		tDest8CDN = new Text(compD,SWT.BORDER); 
//		tDest9CDN = new Text(compD,SWT.BORDER); 
//		tDest10CDN = new Text(compD,SWT.BORDER);
//		tDest11CDN = new Text(compD,SWT.BORDER);
//		tDest12CDN = new Text(compD,SWT.BORDER);
//		tDest13CDN = new Text(compD,SWT.BORDER);
//		tDest14CDN = new Text(compD,SWT.BORDER);
//		tDest15CDN = new Text(compD,SWT.BORDER);
//		tDest16CDN = new Text(compD,SWT.BORDER);
//		tDest17CDN = new Text(compD,SWT.BORDER);
//		tDest18CDN = new Text(compD,SWT.BORDER);
//		tDest19CDN = new Text(compD,SWT.BORDER);
//		tDest20CDN = new Text(compD,SWT.BORDER);
//		tDest21CDN = new Text(compD,SWT.BORDER);
		
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeCDN = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorCDN = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefCDN = new Text(compO,SWT.BORDER);
		bBellCDN = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPriorityCDN,bPriorityCDN,la,lAddress,tDest1CDN,tDest2CDN,tDest3CDN,tDest4CDN,tDest5CDN,tDest6CDN,tDest7CDN,
				tDest8CDN,tDest9CDN,tDest10CDN,tDest11CDN,tDest12CDN,tDest13CDN,tDest14CDN,tDest15CDN,tDest16CDN,tDest17CDN,
				tDest18CDN,tDest19CDN,tDest20CDN,tDest21CDN,lFiling,tFilingTimeCDN,bTimeATS,lb,lOriginator,tOriginatorCDN,lOriref,
				tOriRefCDN,bBellCDN);
	}
	
	public static void headerCPL(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPriorityCPL = new Text(compP,SWT.BORDER);
		bPriorityCPL = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1CPL = new Text(compD,SWT.BORDER); 
		tDest2CPL = new Text(compD,SWT.BORDER); 
		tDest3CPL = new Text(compD,SWT.BORDER); 
		tDest4CPL = new Text(compD,SWT.BORDER); 
		tDest5CPL = new Text(compD,SWT.BORDER); 
		tDest6CPL = new Text(compD,SWT.BORDER); 
		tDest7CPL = new Text(compD,SWT.BORDER); 
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD1 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD1, 7, SWT.LEFT, SWT.CENTER);
		tDest8CPL = new Text(compD1,SWT.BORDER); 
		tDest9CPL = new Text(compD1,SWT.BORDER); 
		tDest10CPL = new Text(compD1,SWT.BORDER);
		tDest11CPL = new Text(compD1,SWT.BORDER);
		tDest12CPL = new Text(compD1,SWT.BORDER);
		tDest13CPL = new Text(compD1,SWT.BORDER);
		tDest14CPL = new Text(compD1,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD2 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD2, 7, SWT.LEFT, SWT.CENTER);
		tDest15CPL = new Text(compD2,SWT.BORDER);
		tDest16CPL = new Text(compD2,SWT.BORDER);
		tDest17CPL = new Text(compD2,SWT.BORDER);
		tDest18CPL = new Text(compD2,SWT.BORDER);
		tDest19CPL = new Text(compD2,SWT.BORDER);
		tDest20CPL = new Text(compD2,SWT.BORDER);
		tDest21CPL = new Text(compD2,SWT.BORDER);
		
//		tDest8CPL = new Text(compD,SWT.BORDER); 
//		tDest9CPL = new Text(compD,SWT.BORDER); 
//		tDest10CPL = new Text(compD,SWT.BORDER);
//		tDest11CPL = new Text(compD,SWT.BORDER);
//		tDest12CPL = new Text(compD,SWT.BORDER);
//		tDest13CPL = new Text(compD,SWT.BORDER);
//		tDest14CPL = new Text(compD,SWT.BORDER);
//		tDest15CPL = new Text(compD,SWT.BORDER);
//		tDest16CPL = new Text(compD,SWT.BORDER);
//		tDest17CPL = new Text(compD,SWT.BORDER);
//		tDest18CPL = new Text(compD,SWT.BORDER);
//		tDest19CPL = new Text(compD,SWT.BORDER);
//		tDest20CPL = new Text(compD,SWT.BORDER);
//		tDest21CPL = new Text(compD,SWT.BORDER);
		
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeCPL = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorCPL = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefCPL = new Text(compO,SWT.BORDER);
		bBellCPL = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPriorityCPL,bPriorityCPL,la,lAddress,tDest1CPL,tDest2CPL,tDest3CPL,tDest4CPL,tDest5CPL,tDest6CPL,tDest7CPL,
				tDest8CPL,tDest9CPL,tDest10CPL,tDest11CPL,tDest12CPL,tDest13CPL,tDest14CPL,tDest15CPL,tDest16CPL,tDest17CPL,
				tDest18CPL,tDest19CPL,tDest20CPL,tDest21CPL,lFiling,tFilingTimeCPL,bTimeATS,lb,lOriginator,tOriginatorCPL,lOriref,
				tOriRefCPL,bBellCPL);
	}
	
	public static void headerEST(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPriorityEST = new Text(compP,SWT.BORDER);
		bPriorityEST = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1EST = new Text(compD,SWT.BORDER); 
		tDest2EST = new Text(compD,SWT.BORDER); 
		tDest3EST = new Text(compD,SWT.BORDER); 
		tDest4EST = new Text(compD,SWT.BORDER); 
		tDest5EST = new Text(compD,SWT.BORDER); 
		tDest6EST = new Text(compD,SWT.BORDER); 
		tDest7EST = new Text(compD,SWT.BORDER); 
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD1 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD1, 7, SWT.LEFT, SWT.CENTER);
		tDest8EST = new Text(compD1,SWT.BORDER); 
		tDest9EST = new Text(compD1,SWT.BORDER); 
		tDest10EST = new Text(compD1,SWT.BORDER);
		tDest11EST = new Text(compD1,SWT.BORDER);
		tDest12EST = new Text(compD1,SWT.BORDER);
		tDest13EST = new Text(compD1,SWT.BORDER);
		tDest14EST = new Text(compD1,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD2 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD2, 7, SWT.LEFT, SWT.CENTER);
		tDest15EST = new Text(compD2,SWT.BORDER);
		tDest16EST = new Text(compD2,SWT.BORDER);
		tDest17EST = new Text(compD2,SWT.BORDER);
		tDest18EST = new Text(compD2,SWT.BORDER);
		tDest19EST = new Text(compD2,SWT.BORDER);
		tDest20EST = new Text(compD2,SWT.BORDER);
		tDest21EST = new Text(compD2,SWT.BORDER);
		
//		tDest8EST = new Text(compD,SWT.BORDER); 
//		tDest9EST = new Text(compD,SWT.BORDER); 
//		tDest10EST = new Text(compD,SWT.BORDER);
//		tDest11EST = new Text(compD,SWT.BORDER);
//		tDest12EST = new Text(compD,SWT.BORDER);
//		tDest13EST = new Text(compD,SWT.BORDER);
//		tDest14EST = new Text(compD,SWT.BORDER);
//		tDest15EST = new Text(compD,SWT.BORDER);
//		tDest16EST = new Text(compD,SWT.BORDER);
//		tDest17EST = new Text(compD,SWT.BORDER);
//		tDest18EST = new Text(compD,SWT.BORDER);
//		tDest19EST = new Text(compD,SWT.BORDER);
//		tDest20EST = new Text(compD,SWT.BORDER);
//		tDest21EST = new Text(compD,SWT.BORDER);
		
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeEST = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorEST = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefEST = new Text(compO,SWT.BORDER);
		bBellEST = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPriorityEST,bPriorityEST,la,lAddress,tDest1EST,tDest2EST,tDest3EST,tDest4EST,tDest5EST,tDest6EST,tDest7EST,
				tDest8EST,tDest9EST,tDest10EST,tDest11EST,tDest12EST,tDest13EST,tDest14EST,tDest15EST,tDest16EST,tDest17EST,
				tDest18EST,tDest19EST,tDest20EST,tDest21EST,lFiling,tFilingTimeEST,bTimeATS,lb,lOriginator,tOriginatorEST,lOriref,
				tOriRefEST,bBellEST);
	}
	
	public static void headerACP(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPriorityACP = new Text(compP,SWT.BORDER);
		bPriorityACP = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1ACP = new Text(compD,SWT.BORDER); 
		tDest2ACP = new Text(compD,SWT.BORDER); 
		tDest3ACP = new Text(compD,SWT.BORDER); 
		tDest4ACP = new Text(compD,SWT.BORDER); 
		tDest5ACP = new Text(compD,SWT.BORDER); 
		tDest6ACP = new Text(compD,SWT.BORDER); 
		tDest7ACP = new Text(compD,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD1 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD1, 7, SWT.LEFT, SWT.CENTER);
		tDest8ACP = new Text(compD1,SWT.BORDER); 
		tDest9ACP = new Text(compD1,SWT.BORDER); 
		tDest10ACP = new Text(compD1,SWT.BORDER);
		tDest11ACP = new Text(compD1,SWT.BORDER);
		tDest12ACP = new Text(compD1,SWT.BORDER);
		tDest13ACP = new Text(compD1,SWT.BORDER);
		tDest14ACP = new Text(compD1,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD2 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD2, 7, SWT.LEFT, SWT.CENTER);
		tDest15ACP = new Text(compD2,SWT.BORDER);
		tDest16ACP = new Text(compD2,SWT.BORDER);
		tDest17ACP = new Text(compD2,SWT.BORDER);
		tDest18ACP = new Text(compD2,SWT.BORDER);
		tDest19ACP = new Text(compD2,SWT.BORDER);
		tDest20ACP = new Text(compD2,SWT.BORDER);
		tDest21ACP = new Text(compD2,SWT.BORDER);
		 
//		tDest8ACP = new Text(compD,SWT.BORDER); 
//		tDest9ACP = new Text(compD,SWT.BORDER); 
//		tDest10ACP = new Text(compD,SWT.BORDER);
//		tDest11ACP = new Text(compD,SWT.BORDER);
//		tDest12ACP = new Text(compD,SWT.BORDER);
//		tDest13ACP = new Text(compD,SWT.BORDER);
//		tDest14ACP = new Text(compD,SWT.BORDER);
//		tDest15ACP = new Text(compD,SWT.BORDER);
//		tDest16ACP = new Text(compD,SWT.BORDER);
//		tDest17ACP = new Text(compD,SWT.BORDER);
//		tDest18ACP = new Text(compD,SWT.BORDER);
//		tDest19ACP = new Text(compD,SWT.BORDER);
//		tDest20ACP = new Text(compD,SWT.BORDER);
//		tDest21ACP = new Text(compD,SWT.BORDER);
		
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeACP = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorACP = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefACP = new Text(compO,SWT.BORDER);
		bBellACP = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPriorityACP,bPriorityACP,la,lAddress,tDest1ACP,tDest2ACP,tDest3ACP,tDest4ACP,tDest5ACP,tDest6ACP,tDest7ACP,
				tDest8ACP,tDest9ACP,tDest10ACP,tDest11ACP,tDest12ACP,tDest13ACP,tDest14ACP,tDest15ACP,tDest16ACP,tDest17ACP,
				tDest18ACP,tDest19ACP,tDest20ACP,tDest21ACP,lFiling,tFilingTimeACP,bTimeATS,lb,lOriginator,tOriginatorACP,lOriref,
				tOriRefACP,bBellACP);
	}
	
	public static void headerLAM(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPriorityLAM = new Text(compP,SWT.BORDER);
		bPriorityLAM = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1LAM = new Text(compD,SWT.BORDER); 
		tDest2LAM = new Text(compD,SWT.BORDER); 
		tDest3LAM = new Text(compD,SWT.BORDER); 
		tDest4LAM = new Text(compD,SWT.BORDER); 
		tDest5LAM = new Text(compD,SWT.BORDER); 
		tDest6LAM = new Text(compD,SWT.BORDER); 
		tDest7LAM = new Text(compD,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD1 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD1, 7, SWT.LEFT, SWT.CENTER);
		tDest8LAM = new Text(compD1,SWT.BORDER); 
		tDest9LAM = new Text(compD1,SWT.BORDER); 
		tDest10LAM = new Text(compD1,SWT.BORDER);
		tDest11LAM = new Text(compD1,SWT.BORDER);
		tDest12LAM = new Text(compD1,SWT.BORDER);
		tDest13LAM = new Text(compD1,SWT.BORDER);
		tDest14LAM = new Text(compD1,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD2 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD2, 7, SWT.LEFT, SWT.CENTER);
		tDest15LAM = new Text(compD2,SWT.BORDER);
		tDest16LAM = new Text(compD2,SWT.BORDER);
		tDest17LAM = new Text(compD2,SWT.BORDER);
		tDest18LAM = new Text(compD2,SWT.BORDER);
		tDest19LAM = new Text(compD2,SWT.BORDER);
		tDest20LAM = new Text(compD2,SWT.BORDER);
		tDest21LAM = new Text(compD2,SWT.BORDER);
		 
//		tDest8LAM = new Text(compD,SWT.BORDER); 
//		tDest9LAM = new Text(compD,SWT.BORDER); 
//		tDest10LAM = new Text(compD,SWT.BORDER);
//		tDest11LAM = new Text(compD,SWT.BORDER);
//		tDest12LAM = new Text(compD,SWT.BORDER);
//		tDest13LAM = new Text(compD,SWT.BORDER);
//		tDest14LAM = new Text(compD,SWT.BORDER);
//		tDest15LAM = new Text(compD,SWT.BORDER);
//		tDest16LAM = new Text(compD,SWT.BORDER);
//		tDest17LAM = new Text(compD,SWT.BORDER);
//		tDest18LAM = new Text(compD,SWT.BORDER);
//		tDest19LAM = new Text(compD,SWT.BORDER);
//		tDest20LAM = new Text(compD,SWT.BORDER);
//		tDest21LAM = new Text(compD,SWT.BORDER);
		
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeLAM = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorLAM = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefLAM = new Text(compO,SWT.BORDER);
		bBellLAM = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPriorityLAM,bPriorityLAM,la,lAddress,tDest1LAM,tDest2LAM,tDest3LAM,tDest4LAM,tDest5LAM,tDest6LAM,tDest7LAM,
				tDest8LAM,tDest9LAM,tDest10LAM,tDest11LAM,tDest12LAM,tDest13LAM,tDest14LAM,tDest15LAM,tDest16LAM,tDest17LAM,
				tDest18LAM,tDest19LAM,tDest20LAM,tDest21LAM,lFiling,tFilingTimeLAM,bTimeATS,lb,lOriginator,tOriginatorLAM,lOriref,
				tOriRefLAM,bBellLAM);
	}
	
	public static void headerRQP(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPriorityRQP = new Text(compP,SWT.BORDER);
		bPriorityRQP = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1RQP = new Text(compD,SWT.BORDER); 
		tDest2RQP = new Text(compD,SWT.BORDER); 
		tDest3RQP = new Text(compD,SWT.BORDER); 
		tDest4RQP = new Text(compD,SWT.BORDER); 
		tDest5RQP = new Text(compD,SWT.BORDER); 
		tDest6RQP = new Text(compD,SWT.BORDER); 
		tDest7RQP = new Text(compD,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD1 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD1, 7, SWT.LEFT, SWT.CENTER);
		tDest8RQP = new Text(compD1,SWT.BORDER); 
		tDest9RQP = new Text(compD1,SWT.BORDER); 
		tDest10RQP = new Text(compD1,SWT.BORDER);
		tDest11RQP = new Text(compD1,SWT.BORDER);
		tDest12RQP = new Text(compD1,SWT.BORDER);
		tDest13RQP = new Text(compD1,SWT.BORDER);
		tDest14RQP = new Text(compD1,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD2 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD2, 7, SWT.LEFT, SWT.CENTER);
		tDest15RQP = new Text(compD2,SWT.BORDER);
		tDest16RQP = new Text(compD2,SWT.BORDER);
		tDest17RQP = new Text(compD2,SWT.BORDER);
		tDest18RQP = new Text(compD2,SWT.BORDER);
		tDest19RQP = new Text(compD2,SWT.BORDER);
		tDest20RQP = new Text(compD2,SWT.BORDER);
		tDest21RQP = new Text(compD2,SWT.BORDER);
		 
//		tDest8RQP = new Text(compD,SWT.BORDER); 
//		tDest9RQP = new Text(compD,SWT.BORDER); 
//		tDest10RQP = new Text(compD,SWT.BORDER);
//		tDest11RQP = new Text(compD,SWT.BORDER);
//		tDest12RQP = new Text(compD,SWT.BORDER);
//		tDest13RQP = new Text(compD,SWT.BORDER);
//		tDest14RQP = new Text(compD,SWT.BORDER);
//		tDest15RQP = new Text(compD,SWT.BORDER);
//		tDest16RQP = new Text(compD,SWT.BORDER);
//		tDest17RQP = new Text(compD,SWT.BORDER);
//		tDest18RQP = new Text(compD,SWT.BORDER);
//		tDest19RQP = new Text(compD,SWT.BORDER);
//		tDest20RQP = new Text(compD,SWT.BORDER);
//		tDest21RQP = new Text(compD,SWT.BORDER);
		
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeRQP = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorRQP = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefRQP = new Text(compO,SWT.BORDER);
		bBellRQP = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPriorityRQP,bPriorityRQP,la,lAddress,tDest1RQP,tDest2RQP,tDest3RQP,tDest4RQP,tDest5RQP,tDest6RQP,tDest7RQP,
				tDest8RQP,tDest9RQP,tDest10RQP,tDest11RQP,tDest12RQP,tDest13RQP,tDest14RQP,tDest15RQP,tDest16RQP,tDest17RQP,
				tDest18RQP,tDest19RQP,tDest20RQP,tDest21RQP,lFiling,tFilingTimeRQP,bTimeATS,lb,lOriginator,tOriginatorRQP,lOriref,
				tOriRefRQP,bBellRQP);
	}
	
	public static void headerRQS(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPriorityRQS = new Text(compP,SWT.BORDER);
		bPriorityRQS = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1RQS = new Text(compD,SWT.BORDER); 
		tDest2RQS = new Text(compD,SWT.BORDER); 
		tDest3RQS = new Text(compD,SWT.BORDER); 
		tDest4RQS = new Text(compD,SWT.BORDER); 
		tDest5RQS = new Text(compD,SWT.BORDER); 
		tDest6RQS = new Text(compD,SWT.BORDER); 
		tDest7RQS = new Text(compD,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD1 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD1, 7, SWT.LEFT, SWT.CENTER);
		tDest8RQS = new Text(compD1,SWT.BORDER); 
		tDest9RQS = new Text(compD1,SWT.BORDER); 
		tDest10RQS = new Text(compD1,SWT.BORDER);
		tDest11RQS = new Text(compD1,SWT.BORDER);
		tDest12RQS = new Text(compD1,SWT.BORDER);
		tDest13RQS = new Text(compD1,SWT.BORDER);
		tDest14RQS = new Text(compD1,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD2 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD2, 7, SWT.LEFT, SWT.CENTER);
		tDest15RQS = new Text(compD2,SWT.BORDER);
		tDest16RQS = new Text(compD2,SWT.BORDER);
		tDest17RQS = new Text(compD2,SWT.BORDER);
		tDest18RQS = new Text(compD2,SWT.BORDER);
		tDest19RQS = new Text(compD2,SWT.BORDER);
		tDest20RQS = new Text(compD2,SWT.BORDER);
		tDest21RQS = new Text(compD2,SWT.BORDER);
		 
//		tDest8RQS = new Text(compD,SWT.BORDER); 
//		tDest9RQS = new Text(compD,SWT.BORDER); 
//		tDest10RQS = new Text(compD,SWT.BORDER);
//		tDest11RQS = new Text(compD,SWT.BORDER);
//		tDest12RQS = new Text(compD,SWT.BORDER);
//		tDest13RQS = new Text(compD,SWT.BORDER);
//		tDest14RQS = new Text(compD,SWT.BORDER);
//		tDest15RQS = new Text(compD,SWT.BORDER);
//		tDest16RQS = new Text(compD,SWT.BORDER);
//		tDest17RQS = new Text(compD,SWT.BORDER);
//		tDest18RQS = new Text(compD,SWT.BORDER);
//		tDest19RQS = new Text(compD,SWT.BORDER);
//		tDest20RQS = new Text(compD,SWT.BORDER);
//		tDest21RQS = new Text(compD,SWT.BORDER);
		
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeRQS = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorRQS = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefRQS = new Text(compO,SWT.BORDER);
		bBellRQS = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPriorityRQS,bPriorityRQS,la,lAddress,tDest1RQS,tDest2RQS,tDest3RQS,tDest4RQS,tDest5RQS,tDest6RQS,tDest7RQS,
				tDest8RQS,tDest9RQS,tDest10RQS,tDest11RQS,tDest12RQS,tDest13RQS,tDest14RQS,tDest15RQS,tDest16RQS,tDest17RQS,
				tDest18RQS,tDest19RQS,tDest20RQS,tDest21RQS,lFiling,tFilingTimeRQS,bTimeATS,lb,lOriginator,tOriginatorRQS,lOriref,
				tOriRefRQS,bBellRQS);
	}
	
	public static void headerSPL(Composite comp) {
		Composite comph = new Composite(comp, SWT.NONE); sh.compositeStyle(comph, 5, SWT.LEFT, SWT.TOP);
		Label lPriority = new Label(comph,SWT.NONE);
		Composite compP = new Composite(comph, SWT.NONE); sh.compositeStyle(compP, 2, SWT.LEFT, SWT.TOP);
		tPrioritySPL = new Text(compP,SWT.BORDER);
		bPrioritySPL = new Button(compP,SWT.BORDER);
		Label la = new Label(comph,SWT.NONE);
		Label lAddress = new Label(comph,SWT.NONE);
		Composite compD = new Composite(comph, SWT.NONE); sh.compositeStyle(compD, 7, SWT.LEFT, SWT.CENTER);
		tDest1SPL = new Text(compD,SWT.BORDER); 
		tDest2SPL = new Text(compD,SWT.BORDER); 
		tDest3SPL = new Text(compD,SWT.BORDER); 
		tDest4SPL = new Text(compD,SWT.BORDER); 
		tDest5SPL = new Text(compD,SWT.BORDER); 
		tDest6SPL = new Text(compD,SWT.BORDER); 
		tDest7SPL = new Text(compD,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD1 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD1, 7, SWT.LEFT, SWT.CENTER);
		tDest8SPL = new Text(compD1,SWT.BORDER); 
		tDest9SPL = new Text(compD1,SWT.BORDER); 
		tDest10SPL = new Text(compD1,SWT.BORDER);
		tDest11SPL = new Text(compD1,SWT.BORDER);
		tDest12SPL = new Text(compD1,SWT.BORDER);
		tDest13SPL = new Text(compD1,SWT.BORDER);
		tDest14SPL = new Text(compD1,SWT.BORDER);
		
		for (int i=0;i<4;i++) label = new Label(comph,SWT.NONE);
		Composite compD2 = new Composite(comph, SWT.NONE); sh.compositeStyle(compD2, 7, SWT.LEFT, SWT.CENTER);
		tDest15SPL = new Text(compD2,SWT.BORDER);
		tDest16SPL = new Text(compD2,SWT.BORDER);
		tDest17SPL = new Text(compD2,SWT.BORDER);
		tDest18SPL = new Text(compD2,SWT.BORDER);
		tDest19SPL = new Text(compD2,SWT.BORDER);
		tDest20SPL = new Text(compD2,SWT.BORDER);
		tDest21SPL = new Text(compD2,SWT.BORDER);
		
//		tDest8SPL = new Text(compD,SWT.BORDER); 
//		tDest9SPL = new Text(compD,SWT.BORDER); 
//		tDest10SPL = new Text(compD,SWT.BORDER);
//		tDest11SPL = new Text(compD,SWT.BORDER);
//		tDest12SPL = new Text(compD,SWT.BORDER);
//		tDest13SPL = new Text(compD,SWT.BORDER);
//		tDest14SPL = new Text(compD,SWT.BORDER);
//		tDest15SPL = new Text(compD,SWT.BORDER);
//		tDest16SPL = new Text(compD,SWT.BORDER);
//		tDest17SPL = new Text(compD,SWT.BORDER);
//		tDest18SPL = new Text(compD,SWT.BORDER);
//		tDest19SPL = new Text(compD,SWT.BORDER);
//		tDest20SPL = new Text(compD,SWT.BORDER);
//		tDest21SPL = new Text(compD,SWT.BORDER);
		
		Label lFiling = new Label(comph,SWT.NONE);
		Composite compF = new Composite(comph, SWT.NONE); sh.compositeStyle(compF, 2, SWT.LEFT, SWT.CENTER);
		tFilingTimeSPL = new Text(compF,SWT.BORDER);
		bTimeATS = new Button(compF, SWT.PUSH); 
		Label lb = new Label(comph,SWT.NONE);
		Label lOriginator = new Label(comph,SWT.NONE);
		Composite compO = new Composite(comph, SWT.NONE); sh.compositeStyle(compO, 4, SWT.LEFT, SWT.CENTER);
		tOriginatorSPL = new Text(compO,SWT.BORDER);
		Label lOriref = new Label(compO,SWT.NONE);
		tOriRefSPL = new Text(compO,SWT.BORDER);
		bBellSPL = new Button(compO, SWT.CHECK); 
		headerT(lPriority,tPrioritySPL,bPrioritySPL,la,lAddress,tDest1SPL,tDest2SPL,tDest3SPL,tDest4SPL,tDest5SPL,tDest6SPL,tDest7SPL,
				tDest8SPL,tDest9SPL,tDest10SPL,tDest11SPL,tDest12SPL,tDest13SPL,tDest14SPL,tDest15SPL,tDest16SPL,tDest17SPL,
				tDest18SPL,tDest19SPL,tDest20SPL,tDest21SPL,lFiling,tFilingTimeSPL,bTimeATS,lb,lOriginator,tOriginatorSPL,lOriref,
				tOriRefSPL,bBellSPL);
	}

	static void addMiniBtnListener() {
		btnCHG.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ValidasiTemp vt = new ValidasiTemp();
				vt.validasi("CHG");
			}
		});
		btnDLA.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ValidasiTemp vt = new ValidasiTemp();
				vt.validasi("DLA");
			}
		});
		btnDEP.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ValidasiTemp vt = new ValidasiTemp();
				vt.validasi("DEP");
			}
		});
		btnCNL.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ValidasiTemp vt = new ValidasiTemp();
				vt.validasi("CNL");
			}
		});
		btnARR.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ValidasiTemp vt = new ValidasiTemp();
				vt.validasi("ARR");
			}
		});
	}
	
	//------------------------------------- SEND - STORE ATS -------------------------------------
	static String sendat(Button b, Text t) {
		String str="";
		time.tgl();
		if (b.getSelection()==true) str = t.getText();
		else str = time.filing_time;
		return str;
	}

	public static String getPriorityAFTN() { return tPriorityAFTN.getText(); }
	public static String getPriorityALR() { return tPriorityALR.getText(); }
	public static String getPriorityRCF() { return tPriorityRCF.getText(); }
	public static String getPriorityFPL() { return tPriorityFPL.getText(); }
	public static String getPriorityDLA() { return tPriorityDLA.getText(); }
	public static String getPriorityCHG() { return tPriorityCHG.getText(); }
	public static String getPriorityCNL() { return tPriorityCNL.getText(); }
	public static String getPriorityDEP() { return tPriorityDEP.getText(); }
	public static String getPriorityARR() { return tPriorityARR.getText(); }
	public static String getPriorityCDN() { return tPriorityCDN.getText(); }
	public static String getPriorityCPL() { return tPriorityCPL.getText(); }
	public static String getPriorityEST() { return tPriorityEST.getText(); }
	public static String getPriorityACP() { return tPriorityACP.getText(); }
	public static String getPriorityLAM() { return tPriorityLAM.getText(); }
	public static String getPriorityRQP() { return tPriorityRQP.getText(); }
	public static String getPriorityRQS() { return tPriorityRQS.getText(); }
	public static String getPrioritySPL() { return tPrioritySPL.getText(); }
	
	public static String getOriginatorAFTN() { return tOriginatorAFTN.getText(); }
	public static String getOriginatorALR() { return tOriginatorALR.getText(); }
	public static String getOriginatorRCF() { return tOriginatorRCF.getText(); }
	public static String getOriginatorFPL() { return tOriginatorFPL.getText(); }
	public static String getOriginatorDLA() { return tOriginatorDLA.getText(); }
	public static String getOriginatorCHG() { return tOriginatorCHG.getText(); }
	public static String getOriginatorCNL() { return tOriginatorCNL.getText(); }
	public static String getOriginatorDEP() { return tOriginatorDEP.getText(); }
	public static String getOriginatorARR() { return tOriginatorARR.getText(); }
	public static String getOriginatorCDN() { return tOriginatorCDN.getText(); }
	public static String getOriginatorCPL() { return tOriginatorCPL.getText(); }
	public static String getOriginatorEST() { return tOriginatorEST.getText(); }
	public static String getOriginatorACP() { return tOriginatorACP.getText(); }
	public static String getOriginatorLAM() { return tOriginatorLAM.getText(); }
	public static String getOriginatorRQP() { return tOriginatorRQP.getText(); }
	public static String getOriginatorRQS() { return tOriginatorRQS.getText(); }
	public static String getOriginatorSPL() { return tOriginatorSPL.getText(); }
	
	public static String getOriRefAFTN() { return tOriRefAFTN.getText(); }
	public static String getOriRefALR() { return tOriRefALR.getText(); }
	public static String getOriRefRCF() { return tOriRefRCF.getText(); }
	public static String getOriRefFPL() { return tOriRefFPL.getText(); }
	public static String getOriRefDLA() { return tOriRefDLA.getText(); }
	public static String getOriRefCHG() { return tOriRefCHG.getText(); }
	public static String getOriRefCNL() { return tOriRefCNL.getText(); }
	public static String getOriRefDEP() { return tOriRefDEP.getText(); }
	public static String getOriRefARR() { return tOriRefARR.getText(); }
	public static String getOriRefCDN() { return tOriRefCDN.getText(); }
	public static String getOriRefCPL() { return tOriRefCPL.getText(); }
	public static String getOriRefEST() { return tOriRefEST.getText(); }
	public static String getOriRefACP() { return tOriRefACP.getText(); }
	public static String getOriRefLAM() { return tOriRefLAM.getText(); }
	public static String getOriRefRQP() { return tOriRefRQP.getText(); }
	public static String getOriRefRQS() { return tOriRefRQS.getText(); }
	public static String getOriRefSPL() { return tOriRefSPL.getText(); }
	
	private static String setFilingTime(Text t) {
		FT = t.getText().toString(); 
		time.tgl(); 
		if (FT.equals("000000")) FT = time.filing_time; 
		return FT;
	}
	public static String getFilingTimeAFTN() { return setFilingTime(tFilingTimeAFTN); }
	public static String getFilingTimeALR() { return setFilingTime(tFilingTimeALR); }
	public static String getFilingTimeRCF() { return setFilingTime(tFilingTimeRCF); }
	public static String getFilingTimeFPL() { return setFilingTime(tFilingTimeFPL); }
	public static String getFilingTimeDLA() { return setFilingTime(tFilingTimeDLA); }
	public static String getFilingTimeCHG() { return setFilingTime(tFilingTimeCHG); }
	public static String getFilingTimeCNL() { return setFilingTime(tFilingTimeCNL); }
	public static String getFilingTimeDEP() { return setFilingTime(tFilingTimeDEP); }
	public static String getFilingTimeARR() { return setFilingTime(tFilingTimeARR); }
	public static String getFilingTimeCDN() { return setFilingTime(tFilingTimeCDN); }
	public static String getFilingTimeCPL() { return setFilingTime(tFilingTimeCPL); }
	public static String getFilingTimeEST() { return setFilingTime(tFilingTimeEST); }
	public static String getFilingTimeACP() { return setFilingTime(tFilingTimeACP); }
	public static String getFilingTimeLAM() { return setFilingTime(tFilingTimeLAM); }
	public static String getFilingTimeRQP() { return setFilingTime(tFilingTimeRQP); }
	public static String getFilingTimeRQS() { return setFilingTime(tFilingTimeRQS); }
	public static String getFilingTimeSPL() { return setFilingTime(tFilingTimeSPL); }
	

	public static String getSendAtAFTN() { return sendat(bSendAtAFTN, tSendAtAFTN); } 
	public static String getSendAtALR() { return sendat(bSendAtALR, tSendAtALR); } 
	public static String getSendAtRCF() { return sendat(bSendAtRCF, tSendAtRCF); } 
	public static String getSendAtFPL() { return sendat(bSendAtFPL, tSendAtFPL); } 
	public static String getSendAtDLA() { return sendat(bSendAtDLA, tSendAtDLA); } 
	public static String getSendAtCHG() { return sendat(bSendAtCHG, tSendAtCHG); } 
	public static String getSendAtCNL() { return sendat(bSendAtCNL, tSendAtCNL); } 
	public static String getSendAtDEP() { return sendat(bSendAtDEP, tSendAtDEP); } 
	public static String getSendAtARR() { return sendat(bSendAtARR, tSendAtARR); } 
	public static String getSendAtCDN() { return sendat(bSendAtCDN, tSendAtCDN); } 
	public static String getSendAtCPL() { return sendat(bSendAtCPL, tSendAtCPL); } 
	public static String getSendAtEST() { return sendat(bSendAtEST, tSendAtEST); } 
	public static String getSendAtACP() { return sendat(bSendAtACP, tSendAtACP); } 
	public static String getSendAtLAM() { return sendat(bSendAtLAM, tSendAtLAM); } 
	public static String getSendAtRQP() { return sendat(bSendAtRQP, tSendAtRQP); } 
	public static String getSendAtRQS() { return sendat(bSendAtRQS, tSendAtRQS); } 
	public static String getSendAtSPL() { return sendat(bSendAtSPL, tSendAtSPL); }
	
	private static String setBell(Button b) {
		String bel=""; 
		if (b.getSelection()==true) bel="1"; else bel="0"; 
		return bel;
	}
	public static String getBellAFTN() { return setBell(bBellAFTN); }
	public static String getBellALR() { return setBell(bBellALR); }
	public static String getBellRCF() { return setBell(bBellRCF); }
	public static String getBellFPL() { return setBell(bBellFPL); }
	public static String getBellDLA() { return setBell(bBellDLA); }
	public static String getBellCHG() { return setBell(bBellCHG); }
	public static String getBellCNL() { return setBell(bBellCNL); }
	public static String getBellDEP() { return setBell(bBellDEP); }
	public static String getBellARR() { return setBell(bBellARR); }
	public static String getBellCDN() { return setBell(bBellCDN); }
	public static String getBellCPL() { return setBell(bBellCPL); }
	public static String getBellEST() { return setBell(bBellEST); }
	public static String getBellACP() { return setBell(bBellACP); }
	public static String getBellLAM() { return setBell(bBellLAM); }
	public static String getBellRQP() { return setBell(bBellRQP); }
	public static String getBellRQS() { return setBell(bBellRQS); }
	public static String getBellSPL() { return setBell(bBellSPL); }
	
	//AFTN
	public static String getDest1AFTN() { return tDest1AFTN.getText(); }
	public static String getDest2AFTN() { return tDest2AFTN.getText(); }
	public static String getDest3AFTN() { return tDest3AFTN.getText(); }
	public static String getDest4AFTN() { return tDest4AFTN.getText(); }
	public static String getDest5AFTN() { return tDest5AFTN.getText(); }
	public static String getDest6AFTN() { return tDest6AFTN.getText(); }
	public static String getDest7AFTN() { return tDest7AFTN.getText(); }
	public static String getDest8AFTN() { return tDest8AFTN.getText(); }
	public static String getDest9AFTN() { return tDest9AFTN.getText(); }
	public static String getDest10AFTN() { return tDest10AFTN.getText(); }
	public static String getDest11AFTN() { return tDest11AFTN.getText(); }
	public static String getDest12AFTN() { return tDest12AFTN.getText(); }
	public static String getDest13AFTN() { return tDest13AFTN.getText(); }
	public static String getDest14AFTN() { return tDest14AFTN.getText(); }
	public static String getDest15AFTN() { return tDest15AFTN.getText(); }
	public static String getDest16AFTN() { return tDest16AFTN.getText(); }
	public static String getDest17AFTN() { return tDest17AFTN.getText(); }
	public static String getDest18AFTN() { return tDest18AFTN.getText(); }
	public static String getDest19AFTN() { return tDest19AFTN.getText(); }
	public static String getDest20AFTN() { return tDest20AFTN.getText(); }
	public static String getDest21AFTN() { return tDest21AFTN.getText(); }
	//ALR
	public static String getDest1ALR() { return tDest1ALR.getText(); }
	public static String getDest2ALR() { return tDest2ALR.getText(); }
	public static String getDest3ALR() { return tDest3ALR.getText(); }
	public static String getDest4ALR() { return tDest4ALR.getText(); }
	public static String getDest5ALR() { return tDest5ALR.getText(); }
	public static String getDest6ALR() { return tDest6ALR.getText(); }
	public static String getDest7ALR() { return tDest7ALR.getText(); }
	public static String getDest8ALR() { return tDest8ALR.getText(); }
	public static String getDest9ALR() { return tDest9ALR.getText(); }
	public static String getDest10ALR() { return tDest10ALR.getText(); }
	public static String getDest11ALR() { return tDest11ALR.getText(); }
	public static String getDest12ALR() { return tDest12ALR.getText(); }
	public static String getDest13ALR() { return tDest13ALR.getText(); }
	public static String getDest14ALR() { return tDest14ALR.getText(); }
	public static String getDest15ALR() { return tDest15ALR.getText(); }
	public static String getDest16ALR() { return tDest16ALR.getText(); }
	public static String getDest17ALR() { return tDest17ALR.getText(); }
	public static String getDest18ALR() { return tDest18ALR.getText(); }
	public static String getDest19ALR() { return tDest19ALR.getText(); }
	public static String getDest20ALR() { return tDest20ALR.getText(); }
	public static String getDest21ALR() { return tDest21ALR.getText(); }
	//RCF
	public static String getDest1RCF() { return tDest1RCF.getText(); }
	public static String getDest2RCF() { return tDest2RCF.getText(); }
	public static String getDest3RCF() { return tDest3RCF.getText(); }
	public static String getDest4RCF() { return tDest4RCF.getText(); }
	public static String getDest5RCF() { return tDest5RCF.getText(); }
	public static String getDest6RCF() { return tDest6RCF.getText(); }
	public static String getDest7RCF() { return tDest7RCF.getText(); }
	public static String getDest8RCF() { return tDest8RCF.getText(); }
	public static String getDest9RCF() { return tDest9RCF.getText(); }
	public static String getDest10RCF() { return tDest10RCF.getText(); }
	public static String getDest11RCF() { return tDest11RCF.getText(); }
	public static String getDest12RCF() { return tDest12RCF.getText(); }
	public static String getDest13RCF() { return tDest13RCF.getText(); }
	public static String getDest14RCF() { return tDest14RCF.getText(); }
	public static String getDest15RCF() { return tDest15RCF.getText(); }
	public static String getDest16RCF() { return tDest16RCF.getText(); }
	public static String getDest17RCF() { return tDest17RCF.getText(); }
	public static String getDest18RCF() { return tDest18RCF.getText(); }
	public static String getDest19RCF() { return tDest19RCF.getText(); }
	public static String getDest20RCF() { return tDest20RCF.getText(); }
	public static String getDest21RCF() { return tDest21RCF.getText(); }
	// FPL
	public static String getDest1FPL() { return tDest1FPL.getText(); }
	public static String getDest2FPL() { return tDest2FPL.getText(); }
	public static String getDest3FPL() { return tDest3FPL.getText(); }
	public static String getDest4FPL() { return tDest4FPL.getText(); }
	public static String getDest5FPL() { return tDest5FPL.getText(); }
	public static String getDest6FPL() { return tDest6FPL.getText(); }
	public static String getDest7FPL() { return tDest7FPL.getText(); }
	public static String getDest8FPL() { return tDest8FPL.getText(); }
	public static String getDest9FPL() { return tDest9FPL.getText(); }
	public static String getDest10FPL() { return tDest10FPL.getText(); }
	public static String getDest11FPL() { return tDest11FPL.getText(); }
	public static String getDest12FPL() { return tDest12FPL.getText(); }
	public static String getDest13FPL() { return tDest13FPL.getText(); }
	public static String getDest14FPL() { return tDest14FPL.getText(); }
	public static String getDest15FPL() { return tDest15FPL.getText(); }
	public static String getDest16FPL() { return tDest16FPL.getText(); }
	public static String getDest17FPL() { return tDest17FPL.getText(); }
	public static String getDest18FPL() { return tDest18FPL.getText(); }
	public static String getDest19FPL() { return tDest19FPL.getText(); }
	public static String getDest20FPL() { return tDest20FPL.getText(); }
	public static String getDest21FPL() { return tDest21FPL.getText(); }
	//DLA
	public static String getDest1DLA() { return tDest1DLA.getText(); }
	public static String getDest2DLA() { return tDest2DLA.getText(); }
	public static String getDest3DLA() { return tDest3DLA.getText(); }
	public static String getDest4DLA() { return tDest4DLA.getText(); }
	public static String getDest5DLA() { return tDest5DLA.getText(); }
	public static String getDest6DLA() { return tDest6DLA.getText(); }
	public static String getDest7DLA() { return tDest7DLA.getText(); }
	public static String getDest8DLA() { return tDest8DLA.getText(); }
	public static String getDest9DLA() { return tDest9DLA.getText(); }
	public static String getDest10DLA() { return tDest10DLA.getText(); }
	public static String getDest11DLA() { return tDest11DLA.getText(); }
	public static String getDest12DLA() { return tDest12DLA.getText(); }
	public static String getDest13DLA() { return tDest13DLA.getText(); }
	public static String getDest14DLA() { return tDest14DLA.getText(); }
	public static String getDest15DLA() { return tDest15DLA.getText(); }
	public static String getDest16DLA() { return tDest16DLA.getText(); }
	public static String getDest17DLA() { return tDest17DLA.getText(); }
	public static String getDest18DLA() { return tDest18DLA.getText(); }
	public static String getDest19DLA() { return tDest19DLA.getText(); }
	public static String getDest20DLA() { return tDest20DLA.getText(); }
	public static String getDest21DLA() { return tDest21DLA.getText(); }
	//CHG
	public static String getDest1CHG() { return tDest1CHG.getText(); }
	public static String getDest2CHG() { return tDest2CHG.getText(); }
	public static String getDest3CHG() { return tDest3CHG.getText(); }
	public static String getDest4CHG() { return tDest4CHG.getText(); }
	public static String getDest5CHG() { return tDest5CHG.getText(); }
	public static String getDest6CHG() { return tDest6CHG.getText(); }
	public static String getDest7CHG() { return tDest7CHG.getText(); }
	public static String getDest8CHG() { return tDest8CHG.getText(); }
	public static String getDest9CHG() { return tDest9CHG.getText(); }
	public static String getDest10CHG() { return tDest10CHG.getText(); }
	public static String getDest11CHG() { return tDest11CHG.getText(); }
	public static String getDest12CHG() { return tDest12CHG.getText(); }
	public static String getDest13CHG() { return tDest13CHG.getText(); }
	public static String getDest14CHG() { return tDest14CHG.getText(); }
	public static String getDest15CHG() { return tDest15CHG.getText(); }
	public static String getDest16CHG() { return tDest16CHG.getText(); }
	public static String getDest17CHG() { return tDest17CHG.getText(); }
	public static String getDest18CHG() { return tDest18CHG.getText(); }
	public static String getDest19CHG() { return tDest19CHG.getText(); }
	public static String getDest20CHG() { return tDest20CHG.getText(); }
	public static String getDest21CHG() { return tDest21CHG.getText(); }
	//CNL
	public static String getDest1CNL() { return tDest1CNL.getText(); }
	public static String getDest2CNL() { return tDest2CNL.getText(); }
	public static String getDest3CNL() { return tDest3CNL.getText(); }
	public static String getDest4CNL() { return tDest4CNL.getText(); }
	public static String getDest5CNL() { return tDest5CNL.getText(); }
	public static String getDest6CNL() { return tDest6CNL.getText(); }
	public static String getDest7CNL() { return tDest7CNL.getText(); }
	public static String getDest8CNL() { return tDest8CNL.getText(); }
	public static String getDest9CNL() { return tDest9CNL.getText(); }
	public static String getDest10CNL() { return tDest10CNL.getText(); }
	public static String getDest11CNL() { return tDest11CNL.getText(); }
	public static String getDest12CNL() { return tDest12CNL.getText(); }
	public static String getDest13CNL() { return tDest13CNL.getText(); }
	public static String getDest14CNL() { return tDest14CNL.getText(); }
	public static String getDest15CNL() { return tDest15CNL.getText(); }
	public static String getDest16CNL() { return tDest16CNL.getText(); }
	public static String getDest17CNL() { return tDest17CNL.getText(); }
	public static String getDest18CNL() { return tDest18CNL.getText(); }
	public static String getDest19CNL() { return tDest19CNL.getText(); }
	public static String getDest20CNL() { return tDest20CNL.getText(); }
	public static String getDest21CNL() { return tDest21CNL.getText(); }
	//DEP
	public static String getDest1DEP() { return tDest1DEP.getText(); }
	public static String getDest2DEP() { return tDest2DEP.getText(); }
	public static String getDest3DEP() { return tDest3DEP.getText(); }
	public static String getDest4DEP() { return tDest4DEP.getText(); }
	public static String getDest5DEP() { return tDest5DEP.getText(); }
	public static String getDest6DEP() { return tDest6DEP.getText(); }
	public static String getDest7DEP() { return tDest7DEP.getText(); }
	public static String getDest8DEP() { return tDest8DEP.getText(); }
	public static String getDest9DEP() { return tDest9DEP.getText(); }
	public static String getDest10DEP() { return tDest10DEP.getText(); }
	public static String getDest11DEP() { return tDest11DEP.getText(); }
	public static String getDest12DEP() { return tDest12DEP.getText(); }
	public static String getDest13DEP() { return tDest13DEP.getText(); }
	public static String getDest14DEP() { return tDest14DEP.getText(); }
	public static String getDest15DEP() { return tDest15DEP.getText(); }
	public static String getDest16DEP() { return tDest16DEP.getText(); }
	public static String getDest17DEP() { return tDest17DEP.getText(); }
	public static String getDest18DEP() { return tDest18DEP.getText(); }
	public static String getDest19DEP() { return tDest19DEP.getText(); }
	public static String getDest20DEP() { return tDest20DEP.getText(); }
	public static String getDest21DEP() { return tDest21DEP.getText(); }
	//ARR
	public static String getDest1ARR() { return tDest1ARR.getText(); }
	public static String getDest2ARR() { return tDest2ARR.getText(); }
	public static String getDest3ARR() { return tDest3ARR.getText(); }
	public static String getDest4ARR() { return tDest4ARR.getText(); }
	public static String getDest5ARR() { return tDest5ARR.getText(); }
	public static String getDest6ARR() { return tDest6ARR.getText(); }
	public static String getDest7ARR() { return tDest7ARR.getText(); }
	public static String getDest8ARR() { return tDest8ARR.getText(); }
	public static String getDest9ARR() { return tDest9ARR.getText(); }
	public static String getDest10ARR() { return tDest10ARR.getText(); }
	public static String getDest11ARR() { return tDest11ARR.getText(); }
	public static String getDest12ARR() { return tDest12ARR.getText(); }
	public static String getDest13ARR() { return tDest13ARR.getText(); }
	public static String getDest14ARR() { return tDest14ARR.getText(); }
	public static String getDest15ARR() { return tDest15ARR.getText(); }
	public static String getDest16ARR() { return tDest16ARR.getText(); }
	public static String getDest17ARR() { return tDest17ARR.getText(); }
	public static String getDest18ARR() { return tDest18ARR.getText(); }
	public static String getDest19ARR() { return tDest19ARR.getText(); }
	public static String getDest20ARR() { return tDest20ARR.getText(); }
	public static String getDest21ARR() { return tDest21ARR.getText(); }
	//CDN
	public static String getDest1CDN() { return tDest1CDN.getText(); }
	public static String getDest2CDN() { return tDest2CDN.getText(); }
	public static String getDest3CDN() { return tDest3CDN.getText(); }
	public static String getDest4CDN() { return tDest4CDN.getText(); }
	public static String getDest5CDN() { return tDest5CDN.getText(); }
	public static String getDest6CDN() { return tDest6CDN.getText(); }
	public static String getDest7CDN() { return tDest7CDN.getText(); }
	public static String getDest8CDN() { return tDest8CDN.getText(); }
	public static String getDest9CDN() { return tDest9CDN.getText(); }
	public static String getDest10CDN() { return tDest10CDN.getText(); }
	public static String getDest11CDN() { return tDest11CDN.getText(); }
	public static String getDest12CDN() { return tDest12CDN.getText(); }
	public static String getDest13CDN() { return tDest13CDN.getText(); }
	public static String getDest14CDN() { return tDest14CDN.getText(); }
	public static String getDest15CDN() { return tDest15CDN.getText(); }
	public static String getDest16CDN() { return tDest16CDN.getText(); }
	public static String getDest17CDN() { return tDest17CDN.getText(); }
	public static String getDest18CDN() { return tDest18CDN.getText(); }
	public static String getDest19CDN() { return tDest19CDN.getText(); }
	public static String getDest20CDN() { return tDest20CDN.getText(); }
	public static String getDest21CDN() { return tDest21CDN.getText(); }
	//CPL
	public static String getDest1CPL() { return tDest1CPL.getText(); }
	public static String getDest2CPL() { return tDest2CPL.getText(); }
	public static String getDest3CPL() { return tDest3CPL.getText(); }
	public static String getDest4CPL() { return tDest4CPL.getText(); }
	public static String getDest5CPL() { return tDest5CPL.getText(); }
	public static String getDest6CPL() { return tDest6CPL.getText(); }
	public static String getDest7CPL() { return tDest7CPL.getText(); }
	public static String getDest8CPL() { return tDest8CPL.getText(); }
	public static String getDest9CPL() { return tDest9CPL.getText(); }
	public static String getDest10CPL() { return tDest10CPL.getText(); }
	public static String getDest11CPL() { return tDest11CPL.getText(); }
	public static String getDest12CPL() { return tDest12CPL.getText(); }
	public static String getDest13CPL() { return tDest13CPL.getText(); }
	public static String getDest14CPL() { return tDest14CPL.getText(); }
	public static String getDest15CPL() { return tDest15CPL.getText(); }
	public static String getDest16CPL() { return tDest16CPL.getText(); }
	public static String getDest17CPL() { return tDest17CPL.getText(); }
	public static String getDest18CPL() { return tDest18CPL.getText(); }
	public static String getDest19CPL() { return tDest19CPL.getText(); }
	public static String getDest20CPL() { return tDest20CPL.getText(); }
	public static String getDest21CPL() { return tDest21CPL.getText(); }
	//EST
	public static String getDest1EST() { return tDest1EST.getText(); }
	public static String getDest2EST() { return tDest2EST.getText(); }
	public static String getDest3EST() { return tDest3EST.getText(); }
	public static String getDest4EST() { return tDest4EST.getText(); }
	public static String getDest5EST() { return tDest5EST.getText(); }
	public static String getDest6EST() { return tDest6EST.getText(); }
	public static String getDest7EST() { return tDest7EST.getText(); }
	public static String getDest8EST() { return tDest8EST.getText(); }
	public static String getDest9EST() { return tDest9EST.getText(); }
	public static String getDest10EST() { return tDest10EST.getText(); }
	public static String getDest11EST() { return tDest11EST.getText(); }
	public static String getDest12EST() { return tDest12EST.getText(); }
	public static String getDest13EST() { return tDest13EST.getText(); }
	public static String getDest14EST() { return tDest14EST.getText(); }
	public static String getDest15EST() { return tDest15EST.getText(); }
	public static String getDest16EST() { return tDest16EST.getText(); }
	public static String getDest17EST() { return tDest17EST.getText(); }
	public static String getDest18EST() { return tDest18EST.getText(); }
	public static String getDest19EST() { return tDest19EST.getText(); }
	public static String getDest20EST() { return tDest20EST.getText(); }
	public static String getDest21EST() { return tDest21EST.getText(); }
	//ACP
	public static String getDest1ACP() { return tDest1ACP.getText(); }
	public static String getDest2ACP() { return tDest2ACP.getText(); }
	public static String getDest3ACP() { return tDest3ACP.getText(); }
	public static String getDest4ACP() { return tDest4ACP.getText(); }
	public static String getDest5ACP() { return tDest5ACP.getText(); }
	public static String getDest6ACP() { return tDest6ACP.getText(); }
	public static String getDest7ACP() { return tDest7ACP.getText(); }
	public static String getDest8ACP() { return tDest8ACP.getText(); }
	public static String getDest9ACP() { return tDest9ACP.getText(); }
	public static String getDest10ACP() { return tDest10ACP.getText(); }
	public static String getDest11ACP() { return tDest11ACP.getText(); }
	public static String getDest12ACP() { return tDest12ACP.getText(); }
	public static String getDest13ACP() { return tDest13ACP.getText(); }
	public static String getDest14ACP() { return tDest14ACP.getText(); }
	public static String getDest15ACP() { return tDest15ACP.getText(); }
	public static String getDest16ACP() { return tDest16ACP.getText(); }
	public static String getDest17ACP() { return tDest17ACP.getText(); }
	public static String getDest18ACP() { return tDest18ACP.getText(); }
	public static String getDest19ACP() { return tDest19ACP.getText(); }
	public static String getDest20ACP() { return tDest20ACP.getText(); }
	public static String getDest21ACP() { return tDest21ACP.getText(); }
	//LAM
	public static String getDest1LAM() { return tDest1LAM.getText(); }
	public static String getDest2LAM() { return tDest2LAM.getText(); }
	public static String getDest3LAM() { return tDest3LAM.getText(); }
	public static String getDest4LAM() { return tDest4LAM.getText(); }
	public static String getDest5LAM() { return tDest5LAM.getText(); }
	public static String getDest6LAM() { return tDest6LAM.getText(); }
	public static String getDest7LAM() { return tDest7LAM.getText(); }
	public static String getDest8LAM() { return tDest8LAM.getText(); }
	public static String getDest9LAM() { return tDest9LAM.getText(); }
	public static String getDest10LAM() { return tDest10LAM.getText(); }
	public static String getDest11LAM() { return tDest11LAM.getText(); }
	public static String getDest12LAM() { return tDest12LAM.getText(); }
	public static String getDest13LAM() { return tDest13LAM.getText(); }
	public static String getDest14LAM() { return tDest14LAM.getText(); }
	public static String getDest15LAM() { return tDest15LAM.getText(); }
	public static String getDest16LAM() { return tDest16LAM.getText(); }
	public static String getDest17LAM() { return tDest17LAM.getText(); }
	public static String getDest18LAM() { return tDest18LAM.getText(); }
	public static String getDest19LAM() { return tDest19LAM.getText(); }
	public static String getDest20LAM() { return tDest20LAM.getText(); }
	public static String getDest21LAM() { return tDest21LAM.getText(); }
	//RQP
	public static String getDest1RQP() { return tDest1RQP.getText(); }
	public static String getDest2RQP() { return tDest2RQP.getText(); }
	public static String getDest3RQP() { return tDest3RQP.getText(); }
	public static String getDest4RQP() { return tDest4RQP.getText(); }
	public static String getDest5RQP() { return tDest5RQP.getText(); }
	public static String getDest6RQP() { return tDest6RQP.getText(); }
	public static String getDest7RQP() { return tDest7RQP.getText(); }
	public static String getDest8RQP() { return tDest8RQP.getText(); }
	public static String getDest9RQP() { return tDest9RQP.getText(); }
	public static String getDest10RQP() { return tDest10RQP.getText(); }
	public static String getDest11RQP() { return tDest11RQP.getText(); }
	public static String getDest12RQP() { return tDest12RQP.getText(); }
	public static String getDest13RQP() { return tDest13RQP.getText(); }
	public static String getDest14RQP() { return tDest14RQP.getText(); }
	public static String getDest15RQP() { return tDest15RQP.getText(); }
	public static String getDest16RQP() { return tDest16RQP.getText(); }
	public static String getDest17RQP() { return tDest17RQP.getText(); }
	public static String getDest18RQP() { return tDest18RQP.getText(); }
	public static String getDest19RQP() { return tDest19RQP.getText(); }
	public static String getDest20RQP() { return tDest20RQP.getText(); }
	public static String getDest21RQP() { return tDest21RQP.getText(); }
	//RQS
	public static String getDest1RQS() { return tDest1RQS.getText(); }
	public static String getDest2RQS() { return tDest2RQS.getText(); }
	public static String getDest3RQS() { return tDest3RQS.getText(); }
	public static String getDest4RQS() { return tDest4RQS.getText(); }
	public static String getDest5RQS() { return tDest5RQS.getText(); }
	public static String getDest6RQS() { return tDest6RQS.getText(); }
	public static String getDest7RQS() { return tDest7RQS.getText(); }
	public static String getDest8RQS() { return tDest8RQS.getText(); }
	public static String getDest9RQS() { return tDest9RQS.getText(); }
	public static String getDest10RQS() { return tDest10RQS.getText(); }
	public static String getDest11RQS() { return tDest11RQS.getText(); }
	public static String getDest12RQS() { return tDest12RQS.getText(); }
	public static String getDest13RQS() { return tDest13RQS.getText(); }
	public static String getDest14RQS() { return tDest14RQS.getText(); }
	public static String getDest15RQS() { return tDest15RQS.getText(); }
	public static String getDest16RQS() { return tDest16RQS.getText(); }
	public static String getDest17RQS() { return tDest17RQS.getText(); }
	public static String getDest18RQS() { return tDest18RQS.getText(); }
	public static String getDest19RQS() { return tDest19RQS.getText(); }
	public static String getDest20RQS() { return tDest20RQS.getText(); }
	public static String getDest21RQS() { return tDest21RQS.getText(); }
	//SPL
	public static String getDest1SPL() { return tDest1SPL.getText(); }
	public static String getDest2SPL() { return tDest2SPL.getText(); }
	public static String getDest3SPL() { return tDest3SPL.getText(); }
	public static String getDest4SPL() { return tDest4SPL.getText(); }
	public static String getDest5SPL() { return tDest5SPL.getText(); }
	public static String getDest6SPL() { return tDest6SPL.getText(); }
	public static String getDest7SPL() { return tDest7SPL.getText(); }
	public static String getDest8SPL() { return tDest8SPL.getText(); }
	public static String getDest9SPL() { return tDest9SPL.getText(); }
	public static String getDest10SPL() { return tDest10SPL.getText(); }
	public static String getDest11SPL() { return tDest11SPL.getText(); }
	public static String getDest12SPL() { return tDest12SPL.getText(); }
	public static String getDest13SPL() { return tDest13SPL.getText(); }
	public static String getDest14SPL() { return tDest14SPL.getText(); }
	public static String getDest15SPL() { return tDest15SPL.getText(); }
	public static String getDest16SPL() { return tDest16SPL.getText(); }
	public static String getDest17SPL() { return tDest17SPL.getText(); }
	public static String getDest18SPL() { return tDest18SPL.getText(); }
	public static String getDest19SPL() { return tDest19SPL.getText(); }
	public static String getDest20SPL() { return tDest20SPL.getText(); }
	public static String getDest21SPL() { return tDest21SPL.getText(); }
	
	//------------------------------------- DISCARD HEADER AFTN -------------------------------------
	public static void discard(Text tFb,Text tSendAt,Button bSendAt,Text tPriority,Text tFilingTime,Text tOriginator,Text tOriRef,Button bBell,
			Text tDest1,Text tDest2,Text tDest3,Text tDest4,Text tDest5,Text tDest6,Text tDest7,
			Text tDest8,Text tDest9,Text tDest10,Text tDest11,Text tDest12,Text tDest13,Text tDest14,
			Text tDest15,Text tDest16,Text tDest17,Text tDest18,Text tDest19,Text tDest20,Text tDest21) {
		
		tFb.setText("");
		time.tgl(); 
		tSendAt.setText(time.filing_time);
		bSendAt.setSelection(false);
		tPriority.setText("FF");
		tFilingTime.setText(time.filing_time);
		tOriginator.setText(ReadFromFile.ReadInputFile1("/aftn/originator.txt"));
		tOriRef.setText("");
		bBell.setSelection(false);
		tDest1.setText("");
		tDest2.setText("");
		tDest3.setText("");
		tDest4.setText("");
		tDest5.setText("");
		tDest6.setText("");
		tDest7.setText("");
		tDest8.setText("");
		tDest9.setText("");
		tDest10.setText("");
		tDest11.setText("");
		tDest12.setText("");
		tDest13.setText("");
		tDest14.setText("");
		tDest15.setText("");
		tDest16.setText("");
		tDest17.setText("");
		tDest18.setText("");
		tDest19.setText("");
		tDest20.setText("");
		tDest21.setText("");
	}
	
	
	//------------------------------------- DISCARD ATS -------------------------------------
	public static void discardHeaderAFTN() {
		discard(ATSForms.tFbAFTN,tSendAtAFTN,bSendAtAFTN,tPriorityAFTN,tFilingTimeAFTN,tOriginatorAFTN,tOriRefAFTN,bBellAFTN,
				tDest1AFTN,tDest2AFTN,tDest3AFTN,tDest4AFTN,tDest5AFTN,tDest6AFTN,tDest7AFTN,
				tDest8AFTN,tDest9AFTN,tDest10AFTN,tDest11AFTN,tDest12AFTN,tDest13AFTN,tDest14AFTN,
				tDest15AFTN,tDest16AFTN,tDest17AFTN,tDest18AFTN,tDest19AFTN,tDest20AFTN,tDest21AFTN); }
	
	public static void discardHeaderALR() {
		discard(ATSForms.tFbALR,tSendAtALR,bSendAtALR,tPriorityALR,tFilingTimeALR,tOriginatorALR,tOriRefALR,bBellALR,
				tDest1ALR,tDest2ALR,tDest3ALR,tDest4ALR,tDest5ALR,tDest6ALR,tDest7ALR,
				tDest8ALR,tDest9ALR,tDest10ALR,tDest11ALR,tDest12ALR,tDest13ALR,tDest14ALR,
				tDest15ALR,tDest16ALR,tDest17ALR,tDest18ALR,tDest19ALR,tDest20ALR,tDest21ALR); }
	
	public static void discardHeaderRCF() {
		discard(ATSForms.tFbRCF,tSendAtRCF,bSendAtRCF,tPriorityRCF,tFilingTimeRCF,tOriginatorRCF,tOriRefRCF,bBellRCF,
				tDest1RCF,tDest2RCF,tDest3RCF,tDest4RCF,tDest5RCF,tDest6RCF,tDest7RCF,
				tDest8RCF,tDest9RCF,tDest10RCF,tDest11RCF,tDest12RCF,tDest13RCF,tDest14RCF,
				tDest15RCF,tDest16RCF,tDest17RCF,tDest18RCF,tDest19RCF,tDest20RCF,tDest21RCF); }
	
	public static void discardHeaderFPL() {
		discard(ATSForms.tFbFPL,tSendAtFPL,bSendAtFPL,tPriorityFPL,tFilingTimeFPL,tOriginatorFPL,tOriRefFPL,bBellFPL,
				tDest1FPL,tDest2FPL,tDest3FPL,tDest4FPL,tDest5FPL,tDest6FPL,tDest7FPL,
				tDest8FPL,tDest9FPL,tDest10FPL,tDest11FPL,tDest12FPL,tDest13FPL,tDest14FPL,
				tDest15FPL,tDest16FPL,tDest17FPL,tDest18FPL,tDest19FPL,tDest20FPL,tDest21FPL); }
	
	public static void discardHeaderDLA() {
		discard(ATSForms.tFbDLA,tSendAtDLA,bSendAtDLA,tPriorityDLA,tFilingTimeDLA,tOriginatorDLA,tOriRefDLA,bBellDLA,
				tDest1DLA,tDest2DLA,tDest3DLA,tDest4DLA,tDest5DLA,tDest6DLA,tDest7DLA,
				tDest8DLA,tDest9DLA,tDest10DLA,tDest11DLA,tDest12DLA,tDest13DLA,tDest14DLA,
				tDest15DLA,tDest16DLA,tDest17DLA,tDest18DLA,tDest19DLA,tDest20DLA,tDest21DLA); }
	
	public static void discardHeaderCHG() {
		discard(ATSForms.tFbCHG,tSendAtCHG,bSendAtCHG,tPriorityCHG,tFilingTimeCHG,tOriginatorCHG,tOriRefCHG,bBellCHG,
				tDest1CHG,tDest2CHG,tDest3CHG,tDest4CHG,tDest5CHG,tDest6CHG,tDest7CHG,
				tDest8CHG,tDest9CHG,tDest10CHG,tDest11CHG,tDest12CHG,tDest13CHG,tDest14CHG,
				tDest15CHG,tDest16CHG,tDest17CHG,tDest18CHG,tDest19CHG,tDest20CHG,tDest21CHG); }
	
	public static void discardHeaderCNL() {
		discard(ATSForms.tFbCNL,tSendAtCNL,bSendAtCNL,tPriorityCNL,tFilingTimeCNL,tOriginatorCNL,tOriRefCNL,bBellCNL,
				tDest1CNL,tDest2CNL,tDest3CNL,tDest4CNL,tDest5CNL,tDest6CNL,tDest7CNL,
				tDest8CNL,tDest9CNL,tDest10CNL,tDest11CNL,tDest12CNL,tDest13CNL,tDest14CNL,
				tDest15CNL,tDest16CNL,tDest17CNL,tDest18CNL,tDest19CNL,tDest20CNL,tDest21CNL); }
	
	public static void discardHeaderDEP() {
		discard(ATSForms.tFbDEP,tSendAtDEP,bSendAtDEP,tPriorityDEP,tFilingTimeDEP,tOriginatorDEP,tOriRefDEP,bBellDEP,
				tDest1DEP,tDest2DEP,tDest3DEP,tDest4DEP,tDest5DEP,tDest6DEP,tDest7DEP,
				tDest8DEP,tDest9DEP,tDest10DEP,tDest11DEP,tDest12DEP,tDest13DEP,tDest14DEP,
				tDest15DEP,tDest16DEP,tDest17DEP,tDest18DEP,tDest19DEP,tDest20DEP,tDest21DEP); }
	
	public static void discardHeaderARR() {
		discard(ATSForms.tFbARR,tSendAtARR,bSendAtARR,tPriorityARR,tFilingTimeARR,tOriginatorARR,tOriRefARR,bBellARR,
				tDest1ARR,tDest2ARR,tDest3ARR,tDest4ARR,tDest5ARR,tDest6ARR,tDest7ARR,
				tDest8ARR,tDest9ARR,tDest10ARR,tDest11ARR,tDest12ARR,tDest13ARR,tDest14ARR,
				tDest15ARR,tDest16ARR,tDest17ARR,tDest18ARR,tDest19ARR,tDest20ARR,tDest21ARR); }
	
	public static void discardHeaderCDN() {
		discard(ATSForms.tFbCDN,tSendAtCDN,bSendAtCDN,tPriorityCDN,tFilingTimeCDN,tOriginatorCDN,tOriRefCDN,bBellCDN,
				tDest1CDN,tDest2CDN,tDest3CDN,tDest4CDN,tDest5CDN,tDest6CDN,tDest7CDN,
				tDest8CDN,tDest9CDN,tDest10CDN,tDest11CDN,tDest12CDN,tDest13CDN,tDest14CDN,
				tDest15CDN,tDest16CDN,tDest17CDN,tDest18CDN,tDest19CDN,tDest20CDN,tDest21CDN); }
	
	public static void discardHeaderCPL() {
		discard(ATSForms.tFbCPL,tSendAtCPL,bSendAtCPL,tPriorityCPL,tFilingTimeCPL,tOriginatorCPL,tOriRefCPL,bBellCPL,
				tDest1CPL,tDest2CPL,tDest3CPL,tDest4CPL,tDest5CPL,tDest6CPL,tDest7CPL,
				tDest8CPL,tDest9CPL,tDest10CPL,tDest11CPL,tDest12CPL,tDest13CPL,tDest14CPL,
				tDest15CPL,tDest16CPL,tDest17CPL,tDest18CPL,tDest19CPL,tDest20CPL,tDest21CPL); }
	
	public static void discardHeaderEST() {
		discard(ATSForms.tFbEST,tSendAtEST,bSendAtEST,tPriorityEST,tFilingTimeEST,tOriginatorEST,tOriRefEST,bBellEST,
				tDest1EST,tDest2EST,tDest3EST,tDest4EST,tDest5EST,tDest6EST,tDest7EST,
				tDest8EST,tDest9EST,tDest10EST,tDest11EST,tDest12EST,tDest13EST,tDest14EST,
				tDest15EST,tDest16EST,tDest17EST,tDest18EST,tDest19EST,tDest20EST,tDest21EST); }
	
	public static void discardHeaderACP() {
		discard(ATSForms.tFbACP,tSendAtACP,bSendAtACP,tPriorityACP,tFilingTimeACP,tOriginatorACP,tOriRefACP,bBellACP,
				tDest1ACP,tDest2ACP,tDest3ACP,tDest4ACP,tDest5ACP,tDest6ACP,tDest7ACP,
				tDest8ACP,tDest9ACP,tDest10ACP,tDest11ACP,tDest12ACP,tDest13ACP,tDest14ACP,
				tDest15ACP,tDest16ACP,tDest17ACP,tDest18ACP,tDest19ACP,tDest20ACP,tDest21ACP); }
	
	public static void discardHeaderLAM() {
		discard(ATSForms.tFbLAM,tSendAtLAM,bSendAtLAM,tPriorityLAM,tFilingTimeLAM,tOriginatorLAM,tOriRefLAM,bBellLAM,
				tDest1LAM,tDest2LAM,tDest3LAM,tDest4LAM,tDest5LAM,tDest6LAM,tDest7LAM,
				tDest8LAM,tDest9LAM,tDest10LAM,tDest11LAM,tDest12LAM,tDest13LAM,tDest14LAM,
				tDest15LAM,tDest16LAM,tDest17LAM,tDest18LAM,tDest19LAM,tDest20LAM,tDest21LAM); }
	
	public static void discardHeaderRQP() {
		discard(ATSForms.tFbRQP,tSendAtRQP,bSendAtRQP,tPriorityRQP,tFilingTimeRQP,tOriginatorRQP,tOriRefRQP,bBellRQP,
				tDest1RQP,tDest2RQP,tDest3RQP,tDest4RQP,tDest5RQP,tDest6RQP,tDest7RQP,
				tDest8RQP,tDest9RQP,tDest10RQP,tDest11RQP,tDest12RQP,tDest13RQP,tDest14RQP,
				tDest15RQP,tDest16RQP,tDest17RQP,tDest18RQP,tDest19RQP,tDest20RQP,tDest21RQP); }
	
	public static void discardHeaderRQS() {
		discard(ATSForms.tFbRQS,tSendAtRQS,bSendAtRQS,tPriorityRQS,tFilingTimeRQS,tOriginatorRQS,tOriRefRQS,bBellRQS,
				tDest1RQS,tDest2RQS,tDest3RQS,tDest4RQS,tDest5RQS,tDest6RQS,tDest7RQS,
				tDest8RQS,tDest9RQS,tDest10RQS,tDest11RQS,tDest12RQS,tDest13RQS,tDest14RQS,
				tDest15RQS,tDest16RQS,tDest17RQS,tDest18RQS,tDest19RQS,tDest20RQS,tDest21RQS); }
	
	public static void discardHeaderSPL() {
		discard(ATSForms.tFbSPL,tSendAtSPL,bSendAtSPL,tPrioritySPL,tFilingTimeSPL,tOriginatorSPL,tOriRefSPL,bBellSPL,
				tDest1SPL,tDest2SPL,tDest3SPL,tDest4SPL,tDest5SPL,tDest6SPL,tDest7SPL,
				tDest8SPL,tDest9SPL,tDest10SPL,tDest11SPL,tDest12SPL,tDest13SPL,tDest14SPL,
				tDest15SPL,tDest16SPL,tDest17SPL,tDest18SPL,tDest19SPL,tDest20SPL,tDest21SPL); }
	
	//------------------------- SET FOCUS ATS -------------------------
	// AFTN
	public static void tPriorityAFTNFocus() { tPriorityAFTN.setFocus(); }
	public static void tOriginatorAFTNFocus() { tOriginatorAFTN.setFocus(); }
	public static void tDest1AFTNFocus() { tDest1AFTN.setFocus(); }
	public static void tDest2AFTNFocus() { tDest2AFTN.setFocus(); }
	public static void tDest3AFTNFocus() { tDest3AFTN.setFocus(); }
	public static void tDest4AFTNFocus() { tDest4AFTN.setFocus(); }
	public static void tDest5AFTNFocus() { tDest5AFTN.setFocus(); }
	public static void tDest6AFTNFocus() { tDest6AFTN.setFocus(); }
	public static void tDest7AFTNFocus() { tDest7AFTN.setFocus(); }
	public static void tDest8AFTNFocus() { tDest8AFTN.setFocus(); }
	public static void tDest9AFTNFocus() { tDest9AFTN.setFocus(); }
	public static void tDest10AFTNFocus() { tDest10AFTN.setFocus(); }
	public static void tDest11AFTNFocus() { tDest11AFTN.setFocus(); }
	public static void tDest12AFTNFocus() { tDest12AFTN.setFocus(); }
	public static void tDest13AFTNFocus() { tDest13AFTN.setFocus(); }
	public static void tDest14AFTNFocus() { tDest14AFTN.setFocus(); }
	public static void tDest15AFTNFocus() { tDest15AFTN.setFocus(); }
	public static void tDest16AFTNFocus() { tDest16AFTN.setFocus(); }
	public static void tDest17AFTNFocus() { tDest17AFTN.setFocus(); }
	public static void tDest18AFTNFocus() { tDest18AFTN.setFocus(); }
	public static void tDest19AFTNFocus() { tDest19AFTN.setFocus(); }
	public static void tDest20AFTNFocus() { tDest20AFTN.setFocus(); }
	public static void tDest21AFTNFocus() { tDest21AFTN.setFocus(); }
	// ALR
	public static void tPriorityALRFocus() { tPriorityALR.setFocus(); }
	public static void tOriginatorALRFocus() { tOriginatorALR.setFocus(); }
	public static void tDest1ALRFocus() { tDest1ALR.setFocus(); }
	public static void tDest2ALRFocus() { tDest2ALR.setFocus(); }
	public static void tDest3ALRFocus() { tDest3ALR.setFocus(); }
	public static void tDest4ALRFocus() { tDest4ALR.setFocus(); }
	public static void tDest5ALRFocus() { tDest5ALR.setFocus(); }
	public static void tDest6ALRFocus() { tDest6ALR.setFocus(); }
	public static void tDest7ALRFocus() { tDest7ALR.setFocus(); }
	public static void tDest8ALRFocus() { tDest8ALR.setFocus(); }
	public static void tDest9ALRFocus() { tDest9ALR.setFocus(); }
	public static void tDest10ALRFocus() { tDest10ALR.setFocus(); }
	public static void tDest11ALRFocus() { tDest11ALR.setFocus(); }
	public static void tDest12ALRFocus() { tDest12ALR.setFocus(); }
	public static void tDest13ALRFocus() { tDest13ALR.setFocus(); }
	public static void tDest14ALRFocus() { tDest14ALR.setFocus(); }
	public static void tDest15ALRFocus() { tDest15ALR.setFocus(); }
	public static void tDest16ALRFocus() { tDest16ALR.setFocus(); }
	public static void tDest17ALRFocus() { tDest17ALR.setFocus(); }
	public static void tDest18ALRFocus() { tDest18ALR.setFocus(); }
	public static void tDest19ALRFocus() { tDest19ALR.setFocus(); }
	public static void tDest20ALRFocus() { tDest20ALR.setFocus(); }
	public static void tDest21ALRFocus() { tDest21ALR.setFocus(); }
	// RCF
	public static void tPriorityRCFFocus() { tPriorityRCF.setFocus(); }
	public static void tOriginatorRCFFocus() { tOriginatorRCF.setFocus(); }
	public static void tDest1RCFFocus() { tDest1RCF.setFocus(); }
	public static void tDest2RCFFocus() { tDest2RCF.setFocus(); }
	public static void tDest3RCFFocus() { tDest3RCF.setFocus(); }
	public static void tDest4RCFFocus() { tDest4RCF.setFocus(); }
	public static void tDest5RCFFocus() { tDest5RCF.setFocus(); }
	public static void tDest6RCFFocus() { tDest6RCF.setFocus(); }
	public static void tDest7RCFFocus() { tDest7RCF.setFocus(); }
	public static void tDest8RCFFocus() { tDest8RCF.setFocus(); }
	public static void tDest9RCFFocus() { tDest9RCF.setFocus(); }
	public static void tDest10RCFFocus() { tDest10RCF.setFocus(); }
	public static void tDest11RCFFocus() { tDest11RCF.setFocus(); }
	public static void tDest12RCFFocus() { tDest12RCF.setFocus(); }
	public static void tDest13RCFFocus() { tDest13RCF.setFocus(); }
	public static void tDest14RCFFocus() { tDest14RCF.setFocus(); }
	public static void tDest15RCFFocus() { tDest15RCF.setFocus(); }
	public static void tDest16RCFFocus() { tDest16RCF.setFocus(); }
	public static void tDest17RCFFocus() { tDest17RCF.setFocus(); }
	public static void tDest18RCFFocus() { tDest18RCF.setFocus(); }
	public static void tDest19RCFFocus() { tDest19RCF.setFocus(); }
	public static void tDest20RCFFocus() { tDest20RCF.setFocus(); }
	public static void tDest21RCFFocus() { tDest21RCF.setFocus(); }
	// FPL
	public static void tPriorityFPLFocus() { tPriorityFPL.setFocus(); }
	public static void tOriginatorFPLFocus() { tOriginatorFPL.setFocus(); }
	public static void tDest1FPLFocus() { tDest1FPL.setFocus(); }
	public static void tDest2FPLFocus() { tDest2FPL.setFocus(); }
	public static void tDest3FPLFocus() { tDest3FPL.setFocus(); }
	public static void tDest4FPLFocus() { tDest4FPL.setFocus(); }
	public static void tDest5FPLFocus() { tDest5FPL.setFocus(); }
	public static void tDest6FPLFocus() { tDest6FPL.setFocus(); }
	public static void tDest7FPLFocus() { tDest7FPL.setFocus(); }
	public static void tDest8FPLFocus() { tDest8FPL.setFocus(); }
	public static void tDest9FPLFocus() { tDest9FPL.setFocus(); }
	public static void tDest10FPLFocus() { tDest10FPL.setFocus(); }
	public static void tDest11FPLFocus() { tDest11FPL.setFocus(); }
	public static void tDest12FPLFocus() { tDest12FPL.setFocus(); }
	public static void tDest13FPLFocus() { tDest13FPL.setFocus(); }
	public static void tDest14FPLFocus() { tDest14FPL.setFocus(); }
	public static void tDest15FPLFocus() { tDest15FPL.setFocus(); }
	public static void tDest16FPLFocus() { tDest16FPL.setFocus(); }
	public static void tDest17FPLFocus() { tDest17FPL.setFocus(); }
	public static void tDest18FPLFocus() { tDest18FPL.setFocus(); }
	public static void tDest19FPLFocus() { tDest19FPL.setFocus(); }
	public static void tDest20FPLFocus() { tDest20FPL.setFocus(); }
	public static void tDest21FPLFocus() { tDest21FPL.setFocus(); }
	// DLA
	public static void tPriorityDLAFocus() { tPriorityDLA.setFocus(); }
	public static void tOriginatorDLAFocus() { tOriginatorDLA.setFocus(); }
	public static void tDest1DLAFocus() { tDest1DLA.setFocus(); }
	public static void tDest2DLAFocus() { tDest2DLA.setFocus(); }
	public static void tDest3DLAFocus() { tDest3DLA.setFocus(); }
	public static void tDest4DLAFocus() { tDest4DLA.setFocus(); }
	public static void tDest5DLAFocus() { tDest5DLA.setFocus(); }
	public static void tDest6DLAFocus() { tDest6DLA.setFocus(); }
	public static void tDest7DLAFocus() { tDest7DLA.setFocus(); }
	public static void tDest8DLAFocus() { tDest8DLA.setFocus(); }
	public static void tDest9DLAFocus() { tDest9DLA.setFocus(); }
	public static void tDest10DLAFocus() { tDest10DLA.setFocus(); }
	public static void tDest11DLAFocus() { tDest11DLA.setFocus(); }
	public static void tDest12DLAFocus() { tDest12DLA.setFocus(); }
	public static void tDest13DLAFocus() { tDest13DLA.setFocus(); }
	public static void tDest14DLAFocus() { tDest14DLA.setFocus(); }
	public static void tDest15DLAFocus() { tDest15DLA.setFocus(); }
	public static void tDest16DLAFocus() { tDest16DLA.setFocus(); }
	public static void tDest17DLAFocus() { tDest17DLA.setFocus(); }
	public static void tDest18DLAFocus() { tDest18DLA.setFocus(); }
	public static void tDest19DLAFocus() { tDest19DLA.setFocus(); }
	public static void tDest20DLAFocus() { tDest20DLA.setFocus(); }
	public static void tDest21DLAFocus() { tDest21DLA.setFocus(); }
	// CHG
	public static void tPriorityCHGFocus() { tPriorityCHG.setFocus(); }
	public static void tOriginatorCHGFocus() { tOriginatorCHG.setFocus(); }
	public static void tDest1CHGFocus() { tDest1CHG.setFocus(); }
	public static void tDest2CHGFocus() { tDest2CHG.setFocus(); }
	public static void tDest3CHGFocus() { tDest3CHG.setFocus(); }
	public static void tDest4CHGFocus() { tDest4CHG.setFocus(); }
	public static void tDest5CHGFocus() { tDest5CHG.setFocus(); }
	public static void tDest6CHGFocus() { tDest6CHG.setFocus(); }
	public static void tDest7CHGFocus() { tDest7CHG.setFocus(); }
	public static void tDest8CHGFocus() { tDest8CHG.setFocus(); }
	public static void tDest9CHGFocus() { tDest9CHG.setFocus(); }
	public static void tDest10CHGFocus() { tDest10CHG.setFocus(); }
	public static void tDest11CHGFocus() { tDest11CHG.setFocus(); }
	public static void tDest12CHGFocus() { tDest12CHG.setFocus(); }
	public static void tDest13CHGFocus() { tDest13CHG.setFocus(); }
	public static void tDest14CHGFocus() { tDest14CHG.setFocus(); }
	public static void tDest15CHGFocus() { tDest15CHG.setFocus(); }
	public static void tDest16CHGFocus() { tDest16CHG.setFocus(); }
	public static void tDest17CHGFocus() { tDest17CHG.setFocus(); }
	public static void tDest18CHGFocus() { tDest18CHG.setFocus(); }
	public static void tDest19CHGFocus() { tDest19CHG.setFocus(); }
	public static void tDest20CHGFocus() { tDest20CHG.setFocus(); }
	public static void tDest21CHGFocus() { tDest21CHG.setFocus(); }
	// CNL
	public static void tPriorityCNLFocus() { tPriorityCNL.setFocus(); }
	public static void tOriginatorCNLFocus() { tOriginatorCNL.setFocus(); }
	public static void tDest1CNLFocus() { tDest1CNL.setFocus(); }
	public static void tDest2CNLFocus() { tDest2CNL.setFocus(); }
	public static void tDest3CNLFocus() { tDest3CNL.setFocus(); }
	public static void tDest4CNLFocus() { tDest4CNL.setFocus(); }
	public static void tDest5CNLFocus() { tDest5CNL.setFocus(); }
	public static void tDest6CNLFocus() { tDest6CNL.setFocus(); }
	public static void tDest7CNLFocus() { tDest7CNL.setFocus(); }
	public static void tDest8CNLFocus() { tDest8CNL.setFocus(); }
	public static void tDest9CNLFocus() { tDest9CNL.setFocus(); }
	public static void tDest10CNLFocus() { tDest10CNL.setFocus(); }
	public static void tDest11CNLFocus() { tDest11CNL.setFocus(); }
	public static void tDest12CNLFocus() { tDest12CNL.setFocus(); }
	public static void tDest13CNLFocus() { tDest13CNL.setFocus(); }
	public static void tDest14CNLFocus() { tDest14CNL.setFocus(); }
	public static void tDest15CNLFocus() { tDest15CNL.setFocus(); }
	public static void tDest16CNLFocus() { tDest16CNL.setFocus(); }
	public static void tDest17CNLFocus() { tDest17CNL.setFocus(); }
	public static void tDest18CNLFocus() { tDest18CNL.setFocus(); }
	public static void tDest19CNLFocus() { tDest19CNL.setFocus(); }
	public static void tDest20CNLFocus() { tDest20CNL.setFocus(); }
	public static void tDest21CNLFocus() { tDest21CNL.setFocus(); }
	// DEP
	public static void tPriorityDEPFocus() { tPriorityDEP.setFocus(); }
	public static void tOriginatorDEPFocus() { tOriginatorDEP.setFocus(); }
	public static void tDest1DEPFocus() { tDest1DEP.setFocus(); }
	public static void tDest2DEPFocus() { tDest2DEP.setFocus(); }
	public static void tDest3DEPFocus() { tDest3DEP.setFocus(); }
	public static void tDest4DEPFocus() { tDest4DEP.setFocus(); }
	public static void tDest5DEPFocus() { tDest5DEP.setFocus(); }
	public static void tDest6DEPFocus() { tDest6DEP.setFocus(); }
	public static void tDest7DEPFocus() { tDest7DEP.setFocus(); }
	public static void tDest8DEPFocus() { tDest8DEP.setFocus(); }
	public static void tDest9DEPFocus() { tDest9DEP.setFocus(); }
	public static void tDest10DEPFocus() { tDest10DEP.setFocus(); }
	public static void tDest11DEPFocus() { tDest11DEP.setFocus(); }
	public static void tDest12DEPFocus() { tDest12DEP.setFocus(); }
	public static void tDest13DEPFocus() { tDest13DEP.setFocus(); }
	public static void tDest14DEPFocus() { tDest14DEP.setFocus(); }
	public static void tDest15DEPFocus() { tDest15DEP.setFocus(); }
	public static void tDest16DEPFocus() { tDest16DEP.setFocus(); }
	public static void tDest17DEPFocus() { tDest17DEP.setFocus(); }
	public static void tDest18DEPFocus() { tDest18DEP.setFocus(); }
	public static void tDest19DEPFocus() { tDest19DEP.setFocus(); }
	public static void tDest20DEPFocus() { tDest20DEP.setFocus(); }
	public static void tDest21DEPFocus() { tDest21DEP.setFocus(); }
	// ARR
	public static void tPriorityARRFocus() { tPriorityARR.setFocus(); }
	public static void tOriginatorARRFocus() { tOriginatorARR.setFocus(); }
	public static void tDest1ARRFocus() { tDest1ARR.setFocus(); }
	public static void tDest2ARRFocus() { tDest2ARR.setFocus(); }
	public static void tDest3ARRFocus() { tDest3ARR.setFocus(); }
	public static void tDest4ARRFocus() { tDest4ARR.setFocus(); }
	public static void tDest5ARRFocus() { tDest5ARR.setFocus(); }
	public static void tDest6ARRFocus() { tDest6ARR.setFocus(); }
	public static void tDest7ARRFocus() { tDest7ARR.setFocus(); }
	public static void tDest8ARRFocus() { tDest8ARR.setFocus(); }
	public static void tDest9ARRFocus() { tDest9ARR.setFocus(); }
	public static void tDest10ARRFocus() { tDest10ARR.setFocus(); }
	public static void tDest11ARRFocus() { tDest11ARR.setFocus(); }
	public static void tDest12ARRFocus() { tDest12ARR.setFocus(); }
	public static void tDest13ARRFocus() { tDest13ARR.setFocus(); }
	public static void tDest14ARRFocus() { tDest14ARR.setFocus(); }
	public static void tDest15ARRFocus() { tDest15ARR.setFocus(); }
	public static void tDest16ARRFocus() { tDest16ARR.setFocus(); }
	public static void tDest17ARRFocus() { tDest17ARR.setFocus(); }
	public static void tDest18ARRFocus() { tDest18ARR.setFocus(); }
	public static void tDest19ARRFocus() { tDest19ARR.setFocus(); }
	public static void tDest20ARRFocus() { tDest20ARR.setFocus(); }
	public static void tDest21ARRFocus() { tDest21ARR.setFocus(); }
	// CDN
	public static void tPriorityCDNFocus() { tPriorityCDN.setFocus(); }
	public static void tOriginatorCDNFocus() { tOriginatorCDN.setFocus(); }
	public static void tDest1CDNFocus() { tDest1CDN.setFocus(); }
	public static void tDest2CDNFocus() { tDest2CDN.setFocus(); }
	public static void tDest3CDNFocus() { tDest3CDN.setFocus(); }
	public static void tDest4CDNFocus() { tDest4CDN.setFocus(); }
	public static void tDest5CDNFocus() { tDest5CDN.setFocus(); }
	public static void tDest6CDNFocus() { tDest6CDN.setFocus(); }
	public static void tDest7CDNFocus() { tDest7CDN.setFocus(); }
	public static void tDest8CDNFocus() { tDest8CDN.setFocus(); }
	public static void tDest9CDNFocus() { tDest9CDN.setFocus(); }
	public static void tDest10CDNFocus() { tDest10CDN.setFocus(); }
	public static void tDest11CDNFocus() { tDest11CDN.setFocus(); }
	public static void tDest12CDNFocus() { tDest12CDN.setFocus(); }
	public static void tDest13CDNFocus() { tDest13CDN.setFocus(); }
	public static void tDest14CDNFocus() { tDest14CDN.setFocus(); }
	public static void tDest15CDNFocus() { tDest15CDN.setFocus(); }
	public static void tDest16CDNFocus() { tDest16CDN.setFocus(); }
	public static void tDest17CDNFocus() { tDest17CDN.setFocus(); }
	public static void tDest18CDNFocus() { tDest18CDN.setFocus(); }
	public static void tDest19CDNFocus() { tDest19CDN.setFocus(); }
	public static void tDest20CDNFocus() { tDest20CDN.setFocus(); }
	public static void tDest21CDNFocus() { tDest21CDN.setFocus(); }
	// CPL
	public static void tPriorityCPLFocus() { tPriorityCPL.setFocus(); }
	public static void tOriginatorCPLFocus() { tOriginatorCPL.setFocus(); }
	public static void tDest1CPLFocus() { tDest1CPL.setFocus(); }
	public static void tDest2CPLFocus() { tDest2CPL.setFocus(); }
	public static void tDest3CPLFocus() { tDest3CPL.setFocus(); }
	public static void tDest4CPLFocus() { tDest4CPL.setFocus(); }
	public static void tDest5CPLFocus() { tDest5CPL.setFocus(); }
	public static void tDest6CPLFocus() { tDest6CPL.setFocus(); }
	public static void tDest7CPLFocus() { tDest7CPL.setFocus(); }
	public static void tDest8CPLFocus() { tDest8CPL.setFocus(); }
	public static void tDest9CPLFocus() { tDest9CPL.setFocus(); }
	public static void tDest10CPLFocus() { tDest10CPL.setFocus(); }
	public static void tDest11CPLFocus() { tDest11CPL.setFocus(); }
	public static void tDest12CPLFocus() { tDest12CPL.setFocus(); }
	public static void tDest13CPLFocus() { tDest13CPL.setFocus(); }
	public static void tDest14CPLFocus() { tDest14CPL.setFocus(); }
	public static void tDest15CPLFocus() { tDest15CPL.setFocus(); }
	public static void tDest16CPLFocus() { tDest16CPL.setFocus(); }
	public static void tDest17CPLFocus() { tDest17CPL.setFocus(); }
	public static void tDest18CPLFocus() { tDest18CPL.setFocus(); }
	public static void tDest19CPLFocus() { tDest19CPL.setFocus(); }
	public static void tDest20CPLFocus() { tDest20CPL.setFocus(); }
	public static void tDest21CPLFocus() { tDest21CPL.setFocus(); }
	// EST
	public static void tPriorityESTFocus() { tPriorityEST.setFocus(); }
	public static void tOriginatorESTFocus() { tOriginatorEST.setFocus(); }
	public static void tDest1ESTFocus() { tDest1EST.setFocus(); }
	public static void tDest2ESTFocus() { tDest2EST.setFocus(); }
	public static void tDest3ESTFocus() { tDest3EST.setFocus(); }
	public static void tDest4ESTFocus() { tDest4EST.setFocus(); }
	public static void tDest5ESTFocus() { tDest5EST.setFocus(); }
	public static void tDest6ESTFocus() { tDest6EST.setFocus(); }
	public static void tDest7ESTFocus() { tDest7EST.setFocus(); }
	public static void tDest8ESTFocus() { tDest8EST.setFocus(); }
	public static void tDest9ESTFocus() { tDest9EST.setFocus(); }
	public static void tDest10ESTFocus() { tDest10EST.setFocus(); }
	public static void tDest11ESTFocus() { tDest11EST.setFocus(); }
	public static void tDest12ESTFocus() { tDest12EST.setFocus(); }
	public static void tDest13ESTFocus() { tDest13EST.setFocus(); }
	public static void tDest14ESTFocus() { tDest14EST.setFocus(); }
	public static void tDest15ESTFocus() { tDest15EST.setFocus(); }
	public static void tDest16ESTFocus() { tDest16EST.setFocus(); }
	public static void tDest17ESTFocus() { tDest17EST.setFocus(); }
	public static void tDest18ESTFocus() { tDest18EST.setFocus(); }
	public static void tDest19ESTFocus() { tDest19EST.setFocus(); }
	public static void tDest20ESTFocus() { tDest20EST.setFocus(); }
	public static void tDest21ESTFocus() { tDest21EST.setFocus(); }
	// ACP
	public static void tPriorityACPFocus() { tPriorityACP.setFocus(); }
	public static void tOriginatorACPFocus() { tOriginatorACP.setFocus(); }
	public static void tDest1ACPFocus() { tDest1ACP.setFocus(); }
	public static void tDest2ACPFocus() { tDest2ACP.setFocus(); }
	public static void tDest3ACPFocus() { tDest3ACP.setFocus(); }
	public static void tDest4ACPFocus() { tDest4ACP.setFocus(); }
	public static void tDest5ACPFocus() { tDest5ACP.setFocus(); }
	public static void tDest6ACPFocus() { tDest6ACP.setFocus(); }
	public static void tDest7ACPFocus() { tDest7ACP.setFocus(); }
	public static void tDest8ACPFocus() { tDest8ACP.setFocus(); }
	public static void tDest9ACPFocus() { tDest9ACP.setFocus(); }
	public static void tDest10ACPFocus() { tDest10ACP.setFocus(); }
	public static void tDest11ACPFocus() { tDest11ACP.setFocus(); }
	public static void tDest12ACPFocus() { tDest12ACP.setFocus(); }
	public static void tDest13ACPFocus() { tDest13ACP.setFocus(); }
	public static void tDest14ACPFocus() { tDest14ACP.setFocus(); }
	public static void tDest15ACPFocus() { tDest15ACP.setFocus(); }
	public static void tDest16ACPFocus() { tDest16ACP.setFocus(); }
	public static void tDest17ACPFocus() { tDest17ACP.setFocus(); }
	public static void tDest18ACPFocus() { tDest18ACP.setFocus(); }
	public static void tDest19ACPFocus() { tDest19ACP.setFocus(); }
	public static void tDest20ACPFocus() { tDest20ACP.setFocus(); }
	public static void tDest21ACPFocus() { tDest21ACP.setFocus(); }
	// LAM
	public static void tPriorityLAMFocus() { tPriorityLAM.setFocus(); }
	public static void tOriginatorLAMFocus() { tOriginatorLAM.setFocus(); }
	public static void tDest1LAMFocus() { tDest1LAM.setFocus(); }
	public static void tDest2LAMFocus() { tDest2LAM.setFocus(); }
	public static void tDest3LAMFocus() { tDest3LAM.setFocus(); }
	public static void tDest4LAMFocus() { tDest4LAM.setFocus(); }
	public static void tDest5LAMFocus() { tDest5LAM.setFocus(); }
	public static void tDest6LAMFocus() { tDest6LAM.setFocus(); }
	public static void tDest7LAMFocus() { tDest7LAM.setFocus(); }
	public static void tDest8LAMFocus() { tDest8LAM.setFocus(); }
	public static void tDest9LAMFocus() { tDest9LAM.setFocus(); }
	public static void tDest10LAMFocus() { tDest10LAM.setFocus(); }
	public static void tDest11LAMFocus() { tDest11LAM.setFocus(); }
	public static void tDest12LAMFocus() { tDest12LAM.setFocus(); }
	public static void tDest13LAMFocus() { tDest13LAM.setFocus(); }
	public static void tDest14LAMFocus() { tDest14LAM.setFocus(); }
	public static void tDest15LAMFocus() { tDest15LAM.setFocus(); }
	public static void tDest16LAMFocus() { tDest16LAM.setFocus(); }
	public static void tDest17LAMFocus() { tDest17LAM.setFocus(); }
	public static void tDest18LAMFocus() { tDest18LAM.setFocus(); }
	public static void tDest19LAMFocus() { tDest19LAM.setFocus(); }
	public static void tDest20LAMFocus() { tDest20LAM.setFocus(); }
	public static void tDest21LAMFocus() { tDest21LAM.setFocus(); }
	// RQP
	public static void tPriorityRQPFocus() { tPriorityRQP.setFocus(); }
	public static void tOriginatorRQPFocus() { tOriginatorRQP.setFocus(); }
	public static void tDest1RQPFocus() { tDest1RQP.setFocus(); }
	public static void tDest2RQPFocus() { tDest2RQP.setFocus(); }
	public static void tDest3RQPFocus() { tDest3RQP.setFocus(); }
	public static void tDest4RQPFocus() { tDest4RQP.setFocus(); }
	public static void tDest5RQPFocus() { tDest5RQP.setFocus(); }
	public static void tDest6RQPFocus() { tDest6RQP.setFocus(); }
	public static void tDest7RQPFocus() { tDest7RQP.setFocus(); }
	public static void tDest8RQPFocus() { tDest8RQP.setFocus(); }
	public static void tDest9RQPFocus() { tDest9RQP.setFocus(); }
	public static void tDest10RQPFocus() { tDest10RQP.setFocus(); }
	public static void tDest11RQPFocus() { tDest11RQP.setFocus(); }
	public static void tDest12RQPFocus() { tDest12RQP.setFocus(); }
	public static void tDest13RQPFocus() { tDest13RQP.setFocus(); }
	public static void tDest14RQPFocus() { tDest14RQP.setFocus(); }
	public static void tDest15RQPFocus() { tDest15RQP.setFocus(); }
	public static void tDest16RQPFocus() { tDest16RQP.setFocus(); }
	public static void tDest17RQPFocus() { tDest17RQP.setFocus(); }
	public static void tDest18RQPFocus() { tDest18RQP.setFocus(); }
	public static void tDest19RQPFocus() { tDest19RQP.setFocus(); }
	public static void tDest20RQPFocus() { tDest20RQP.setFocus(); }
	public static void tDest21RQPFocus() { tDest21RQP.setFocus(); }
	// RQS
	public static void tPriorityRQSFocus() { tPriorityRQS.setFocus(); }
	public static void tOriginatorRQSFocus() { tOriginatorRQS.setFocus(); }
	public static void tDest1RQSFocus() { tDest1RQS.setFocus(); }
	public static void tDest2RQSFocus() { tDest2RQS.setFocus(); }
	public static void tDest3RQSFocus() { tDest3RQS.setFocus(); }
	public static void tDest4RQSFocus() { tDest4RQS.setFocus(); }
	public static void tDest5RQSFocus() { tDest5RQS.setFocus(); }
	public static void tDest6RQSFocus() { tDest6RQS.setFocus(); }
	public static void tDest7RQSFocus() { tDest7RQS.setFocus(); }
	public static void tDest8RQSFocus() { tDest8RQS.setFocus(); }
	public static void tDest9RQSFocus() { tDest9RQS.setFocus(); }
	public static void tDest10RQSFocus() { tDest10RQS.setFocus(); }
	public static void tDest11RQSFocus() { tDest11RQS.setFocus(); }
	public static void tDest12RQSFocus() { tDest12RQS.setFocus(); }
	public static void tDest13RQSFocus() { tDest13RQS.setFocus(); }
	public static void tDest14RQSFocus() { tDest14RQS.setFocus(); }
	public static void tDest15RQSFocus() { tDest15RQS.setFocus(); }
	public static void tDest16RQSFocus() { tDest16RQS.setFocus(); }
	public static void tDest17RQSFocus() { tDest17RQS.setFocus(); }
	public static void tDest18RQSFocus() { tDest18RQS.setFocus(); }
	public static void tDest19RQSFocus() { tDest19RQS.setFocus(); }
	public static void tDest20RQSFocus() { tDest20RQS.setFocus(); }
	public static void tDest21RQSFocus() { tDest21RQS.setFocus(); }
	// SPL
	public static void tPrioritySPLFocus() { tPrioritySPL.setFocus(); }
	public static void tOriginatorSPLFocus() { tOriginatorSPL.setFocus(); }
	public static void tDest1SPLFocus() { tDest1SPL.setFocus(); }
	public static void tDest2SPLFocus() { tDest2SPL.setFocus(); }
	public static void tDest3SPLFocus() { tDest3SPL.setFocus(); }
	public static void tDest4SPLFocus() { tDest4SPL.setFocus(); }
	public static void tDest5SPLFocus() { tDest5SPL.setFocus(); }
	public static void tDest6SPLFocus() { tDest6SPL.setFocus(); }
	public static void tDest7SPLFocus() { tDest7SPL.setFocus(); }
	public static void tDest8SPLFocus() { tDest8SPL.setFocus(); }
	public static void tDest9SPLFocus() { tDest9SPL.setFocus(); }
	public static void tDest10SPLFocus() { tDest10SPL.setFocus(); }
	public static void tDest11SPLFocus() { tDest11SPL.setFocus(); }
	public static void tDest12SPLFocus() { tDest12SPL.setFocus(); }
	public static void tDest13SPLFocus() { tDest13SPL.setFocus(); }
	public static void tDest14SPLFocus() { tDest14SPL.setFocus(); }
	public static void tDest15SPLFocus() { tDest15SPL.setFocus(); }
	public static void tDest16SPLFocus() { tDest16SPL.setFocus(); }
	public static void tDest17SPLFocus() { tDest17SPL.setFocus(); }
	public static void tDest18SPLFocus() { tDest18SPL.setFocus(); }
	public static void tDest19SPLFocus() { tDest19SPL.setFocus(); }
	public static void tDest20SPLFocus() { tDest20SPL.setFocus(); }
	public static void tDest21SPLFocus() { tDest21SPL.setFocus(); }
	
}