package displays.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
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
import setting.Times;
import actions.RefreshTable;
import actions.SendSave;
import databases.jdbc;
import displays.DialogFactory;
import displays.MainForm;
import displays.pid.setting;


public class FormMini {

	Shorten sh = new Shorten();
	ButtonsSetting bs = new ButtonsSetting();
	ErrMsg em = new ErrMsg();
	SendSave ss = new SendSave();
	ATSComponentSetting acs = new ATSComponentSetting();
//	Conn c = new Conn();
	Times time = new Times();
	ReadFromFile rff = new ReadFromFile();
	
	Shell shell;
	String title="";
	String ID="";
	String ver="new";
	Text t;
	int iSEPARATOR = 400;
	
	public static String[] cType5a = new String[] {"","INCERFA","ALERFA","DETRESFA"};
	public static String[] cType8a = new String[] {"","I","V","Y","Z"};
	public static String[] cType8b = new String[] {"","S","N","G","M","X"};
	public static String[] cType9c = new String[] {"","H","M","L","J"};//Y; dari mana? lupa
	
	
	Button Save,Clear;
	//route
	private Text tDepAd_Route,tDestAd_Route,tRoute_Route;
	private static Text tDest1_Route,tDest2_Route,tDest3_Route,tDest4_Route,tDest5_Route,tDest6_Route,tDest7_Route;
	private static Text tDest8_Route,tDest9_Route,tDest10_Route,tDest11_Route,tDest12_Route,tDest13_Route,tDest14_Route;
	private static Text tDest15_Route,tDest16_Route,tDest17_Route,tDest18_Route,tDest19_Route,tDest20_Route,tDest21_Route;
	//type9b
	private Text tTOA_Type9b;
	private List lWTC_Type9b;
	static String s="";
	static String sdb="";
	static int is=0;
	static ScrollBar sbWTC;
	static int sbiWTC,lsbiWTC,initWTC;
	//reg
	public static Text t10a_REG,tPbn_REG;
	Text t9b_REG,tReg_REG,t10b_REG,tOpr_REG;
	Button b10a_REG,b10b_REG,bPBN_REG;
	//locind
	Text tIndicator_Locind,tLocation_Locind;
	//deltbl
	public static Text tDelTbl;
	//STS
	Button bSTS1,bSTS2,bSTS3,bSTS4,bSTS5,bSTS6,bSTS7,bSTS8,bSTS9,bSTS10,bSTS11,bSTS12,bSTS13;
	//PBN
	Button bPBN1,bPBN2,bPBN3,bPBN4,bPBN5,bPBN6,bPBN7,bPBN8,bPBN9,bPBN10,bPBN11,bPBN12,bPBN13,bPBN14,bPBN15,bPBN16,bPBN17,bPBN18,bPBN19,
	bPBN20,bPBN21,bPBN22,bPBN23,bPBN24;
	
	
	public FormMini(String sid, Text txt) {
		ID = sid;
		t = txt;
		if (ID.compareToIgnoreCase("priority")==0) {
			shell = Shells.sh_mPriority;
			title += "Priority";
			compPriority(); }
		else if (ID.compareToIgnoreCase("baudrate")==0) {
			shell = Shells.sh_mBaudrate;
			title += "Baudrate";
			compBaudrate(); }
		else if (ID.compareToIgnoreCase("5a")==0) {
			shell = Shells.sh_m5a;
			title += "Phase of Emergency Value";
			comp5a(); }
		else if (ID.compareToIgnoreCase("8a")==0) {
			shell = Shells.sh_m8a;
			title += "Flight Rules Value";
			comp8a(); }
		else if (ID.compareToIgnoreCase("8b")==0) {
			shell = Shells.sh_m8b;
			title += "Type of Flight Value";
			comp8b(); }
		else if (ID.compareToIgnoreCase("9c")==0) {
			shell = Shells.sh_m9c;
			title += "Wake Turb. Cat. Value";
			comp9c(); }

		else if (ID.compareToIgnoreCase("deltbl")==0) {
			shell = Shells.sh_mDeltbl;
			title += "Delete Table";
			compDelTbl(); }
		
		else if (ID.compareToIgnoreCase("10a")==0) {
			shell = Shells.sh_m10a;
			title += "Equipment and Capabilities (Item 10a) Value";
			comp10a(); }
		else if (ID.compareToIgnoreCase("10b")==0) {
			shell = Shells.sh_m10b;
			title += "Equipment and Capabilities (Item 10b) Value";
			comp10b(); }
		else if (ID.compareToIgnoreCase("pbn")==0) {
			shell = Shells.sh_mPBN;
			title += "PBN/ Indicator Value";
			compPBN(); }
		else if (ID.compareToIgnoreCase("sts")==0) {
			shell = Shells.sh_mSTS;
			title += "STS/ Indicator Value";
			compSTS(); }

		ReadFromFile.readConfiguration();
		sh.shellStyle(shell, title);
	}
	
	static ScrollBar sbPrio;
	static int sbiPrio,lsbiPrio,initPrio;
	void comp(Group grp,Composite comp,final List list,final Label label,String str,int iWidL,int iWList,int iHList,String sList,String[] sDt) {
//		sh.label2(label, str, iWidL, SWT.LEFT, SWT.CENTER, comp);
		sh.labelStyle(label, str, iWidL, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		
		sh.list(list, iWList, iHList, sList, sDt, comp);
		
		sbPrio = list.getVerticalBar();
		lsbiPrio=0;sbiPrio=0;initPrio=1;
		sbPrio.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (initPrio==1) {
					if (lsbiPrio>sbPrio.getSelection()) {if (sbiPrio>0) sbiPrio--;list.select(sbiPrio);/*System.out.println("up");*/}
					else if (lsbiPrio<sbPrio.getSelection()) {if (sbiPrio<list.getItemCount()-1) sbiPrio++;list.select(sbiPrio);}
					list.showSelection();
				}
				else {lsbiPrio=1+(list.getSelectionIndex()*26);sbiPrio=list.getSelectionIndex();}
				lsbiPrio=sbPrio.getSelection();
				
				initPrio=1;
			}
		});	
		
		Composite typeA = new Composite(grp, SWT.NONE); sh.compositeStyle(typeA, 1, SWT.CENTER, SWT.CENTER);
		Label lbl = new Label(typeA, SWT.SEPARATOR | SWT.HORIZONTAL); 
		sh.labelStyle(lbl, "", 100, SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
		lbl.setVisible(false);
		Composite typeB = new Composite(grp, SWT.NONE); sh.compositeStyle(typeB, 1, SWT.CENTER, SWT.CENTER);
		lbl = new Label(typeB, SWT.SEPARATOR | SWT.HORIZONTAL);
		sh.labelStyle(lbl, "", 100, SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
		
		Composite compSts2 = new Composite(grp, SWT.NONE); sh.compositeStyle(compSts2, 2, SWT.CENTER, SWT.CENTER);
		Button Input = new Button(compSts2, SWT.PUSH);
		Button Cancel = new Button(compSts2, SWT.PUSH);
		bs.InputCancel(Input, Cancel);
		
		Input.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
//				String str = "";
//				if (list.getSelectionCount()>0) str=list.getSelection()[0];
//				t.setText(str);
//				shell.close();
				openEdit(list);
			}
		});
		
		Cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		
		list.addMouseListener(new MouseAdapter() {
  			public void mouseDoubleClick(MouseEvent e) {
  				openEdit(list);
  			}
  		});
	}	
	
	void comp1(Group grp,Composite comp,final List list,int iWList,int iHList,String sList,String[] sDt) {
		sh.listStyle(list, iWList, iHList, sDt);
		
		sbPrio = list.getVerticalBar();
		lsbiPrio=0;sbiPrio=0;initPrio=1;
		sbPrio.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (initPrio==1) {
					if (lsbiPrio>sbPrio.getSelection()) {if (sbiPrio>0) sbiPrio--;list.select(sbiPrio);/*System.out.println("up");*/}
					else if (lsbiPrio<sbPrio.getSelection()) {if (sbiPrio<list.getItemCount()-1) sbiPrio++;list.select(sbiPrio);}
					list.showSelection();
				}
				else {lsbiPrio=1+(list.getSelectionIndex()*26);sbiPrio=list.getSelectionIndex();}
				lsbiPrio=sbPrio.getSelection();
				
				initPrio=1;
			}
		});	
		
		Composite typeB = new Composite(grp, SWT.NONE); sh.compositeStyle(typeB, 1, SWT.CENTER, SWT.CENTER);
		Label lbl = new Label(typeB, SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelStyle(lbl, "", 200, SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
		
		Composite compSts2 = new Composite(grp, SWT.NONE); sh.compositeStyle(compSts2, 2, SWT.CENTER, SWT.CENTER);
		Button Input = new Button(compSts2, SWT.PUSH);
		Button Cancel = new Button(compSts2, SWT.PUSH);
		bs.AC(Input, Cancel);
		
		Input.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				openEdit(list);
			}
		});
		
		Cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		
		list.addMouseListener(new MouseAdapter() {
  			public void mouseDoubleClick(MouseEvent e) {
  				openEdit(list);
  			}
  		});
	}	

	void openEdit(List list) {
		String str = "";
		if (list.getSelectionCount()>0) str=list.getSelection()[0];
		t.setText(str);
		shell.close();	
	}
	
	void compPriority() {
		final Group Group = new Group(shell, SWT.CENTER); sh.groupStyle(Group, 1, "");
		Composite comp = new Composite(Group, SWT.NONE); sh.compositeStyle(comp, 1, SWT.LEFT, SWT.CENTER);
		Label label = new Label(comp, SWT.NONE); 
		List list = new List(comp, SWT.BORDER|SWT.V_SCROLL); 
		comp(Group, comp, list, label, "Select Priority", 200, 200, 140, "", setting.sPriority);
	}
	
	void compBaudrate() {
		final Group Group = new Group(shell, SWT.CENTER); sh.groupStyle(Group, 1, "");
		Composite comp = new Composite(Group, SWT.NONE); sh.compositeStyle(comp, 1, SWT.LEFT, SWT.CENTER);
		Label label = new Label(comp, SWT.NONE); 
		List list = new List(comp, SWT.BORDER|SWT.V_SCROLL); 
		comp(Group, comp, list, label, "Select Baudrate", 200, 200, 100, "", setting.sBaud);
	}
	
	void comp5a() {
		final Group Group = new Group(shell, SWT.CENTER); sh.groupStyle(Group, 1, "");
		Composite comp = new Composite(Group, SWT.NONE); sh.compositeStyle(comp, 1, SWT.CENTER, SWT.CENTER);
		List list = new List(comp, SWT.BORDER | SWT.V_SCROLL); 
		comp1(Group, comp, list, 200, 105, "", cType5a);
	}
	
	void comp8a() {
		final Group Group = new Group(shell, SWT.CENTER); sh.groupStyle(Group, 1, "");
		Composite comp = new Composite(Group, SWT.NONE); sh.compositeStyle(comp, 1, SWT.CENTER, SWT.CENTER);
		List list = new List(comp, SWT.BORDER | SWT.V_SCROLL); 
		comp1(Group, comp, list, 200, 130, "", cType8a);
	}

	void comp8b() {
		final Group Group = new Group(shell, SWT.CENTER); sh.groupStyle(Group, 1, "");
		Composite comp = new Composite(Group, SWT.NONE); sh.compositeStyle(comp, 1, SWT.CENTER, SWT.CENTER);
		List list = new List(comp, SWT.BORDER | SWT.V_SCROLL); 
		comp1(Group, comp, list, 200, 155, "", cType8b);
	}

	void comp9c() {
		final Group Group = new Group(shell, SWT.CENTER); sh.groupStyle(Group, 1, "");
		Composite comp = new Composite(Group, SWT.NONE); sh.compositeStyle(comp, 1, SWT.CENTER, SWT.CENTER);
		List list = new List(comp, SWT.BORDER | SWT.V_SCROLL); 
		comp1(Group, comp, list, 200, 130, "", cType9c);
	}
	
	void comp10a() {
		final Group Group = new Group(shell, SWT.CENTER); sh.groupStyle(Group, 1, "");
		Label label = new Label(Group, SWT.NONE); 
		sh.labelStyle1(label, "Insert one letter as follows", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		
		Composite compNS = new Composite(Group, SWT.NONE); sh.compositeStyle(compNS, 3, SWT.LEFT, SWT.CENTER);
		final Button bS1 = new Button(compNS, SWT.CHECK | SWT.BORDER); button(bS1, "N"); 
		label = new Label(compNS, SWT.NONE); sh.labelStyle(label, "OR", 45, SWT.CENTER, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		final Button bS2 = new Button(compNS, SWT.CHECK); button(bS2, "S");
		
		label = new Label(Group, SWT.NONE); 
		sh.labelStyle1(label, "AND/OR INSERT one or more of the following letters to indicate\n" +
				"the serviciable COM/NAV/approach aid equipment and capabilities available: ", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		
		Composite comp1 = new Composite(Group, SWT.NONE); sh.compositeStyle(comp1, 8, SWT.LEFT, SWT.CENTER);
		final Button bS3 = new Button(comp1, SWT.CHECK); button(bS3, "A"); 
		final Button bS4 = new Button(comp1, SWT.CHECK); button(bS4, "B"); 
		final Button bS5 = new Button(comp1, SWT.CHECK); button(bS5, "C"); 
		final Button bS6 = new Button(comp1, SWT.CHECK); button(bS6, "D"); 
		final Button bS7 = new Button(comp1, SWT.CHECK); button(bS7, "E1");
		final Button bS8 = new Button(comp1, SWT.CHECK); button(bS8, "E2"); 
		final Button bS9 = new Button(comp1, SWT.CHECK); button(bS9, "E3"); 
		final Button bS10 = new Button(comp1, SWT.CHECK); button(bS10, "F");
		final Button bS11 = new Button(comp1, SWT.CHECK); button(bS11, "G"); 
		final Button bS12 = new Button(comp1, SWT.CHECK); button(bS12, "H"); 
		final Button bS13 = new Button(comp1, SWT.CHECK); button(bS13, "I"); 
		final Button bS14 = new Button(comp1, SWT.CHECK); button(bS14, "J1");
		final Button bS15 = new Button(comp1, SWT.CHECK); button(bS15, "J2"); 
		final Button bS16 = new Button(comp1, SWT.CHECK); button(bS16, "J3"); 
		final Button bS17 = new Button(comp1, SWT.CHECK); button(bS17, "J4"); 
		final Button bS18 = new Button(comp1, SWT.CHECK); button(bS18, "J5"); 
		final Button bS19 = new Button(comp1, SWT.CHECK); button(bS19, "J6"); 
		final Button bS20 = new Button(comp1, SWT.CHECK); button(bS20, "J7");
		final Button bS21 = new Button(comp1, SWT.CHECK); button(bS21, "K"); 
		final Button bS22 = new Button(comp1, SWT.CHECK); button(bS22, "L"); 
		final Button bS23 = new Button(comp1, SWT.CHECK); button(bS23, "M1"); 
		final Button bS24 = new Button(comp1, SWT.CHECK); button(bS24, "M2");
		final Button bS25 = new Button(comp1, SWT.CHECK); button(bS25, "M3"); 
		final Button bS26 = new Button(comp1, SWT.CHECK); button(bS26, "O");
		final Button bS27 = new Button(comp1, SWT.CHECK); button(bS27, "P1"); 
		final Button bS28 = new Button(comp1, SWT.CHECK); button(bS28, "P2"); 
		final Button bS29 = new Button(comp1, SWT.CHECK); button(bS29, "P3"); 
		final Button bS30 = new Button(comp1, SWT.CHECK); button(bS30, "P4");
		final Button bS31 = new Button(comp1, SWT.CHECK); button(bS31, "P5"); 
		final Button bS32 = new Button(comp1, SWT.CHECK); button(bS32, "P6");
		final Button bS33 = new Button(comp1, SWT.CHECK); button(bS33, "P7");
		final Button bS34 = new Button(comp1, SWT.CHECK); button(bS34, "P8"); 
		final Button bS35 = new Button(comp1, SWT.CHECK); button(bS35, "P9"); 
		final Button bS36 = new Button(comp1, SWT.CHECK); button(bS36, "R"); 
		final Button bS37 = new Button(comp1, SWT.CHECK); button(bS37, "T"); 
		final Button bS38 = new Button(comp1, SWT.CHECK); button(bS38, "U");
		final Button bS39 = new Button(comp1, SWT.CHECK); button(bS39, "V"); 
		final Button bS40 = new Button(comp1, SWT.CHECK); button(bS40, "W"); 
		final Button bS41 = new Button(comp1, SWT.CHECK); button(bS41, "X"); 
		final Button bS42 = new Button(comp1, SWT.CHECK); button(bS42, "Y"); 
		final Button bS43 = new Button(comp1, SWT.CHECK); button(bS43, "Z"); 

		label = new Label(Group, SWT.NONE); 
		sh.labelStyle1(label, "Any alphanumeric characters not indicated above are reserved.", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		
		Composite typeB = new Composite(Group, SWT.NONE); sh.compositeStyle(typeB, 1, SWT.CENTER, SWT.CENTER);
		label = new Label(typeB, SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelStyle(label, "", 360, SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
		
		Composite typeE = new Composite(Group, SWT.NONE); sh.compositeStyle(typeE, 3, SWT.CENTER, SWT.CENTER);
		Button bAdd = new Button(typeE, SWT.PUSH);
		Button bClear = new Button(typeE, SWT.PUSH);
		Button bClose = new Button(typeE, SWT.PUSH);
		bs.ACC(bAdd, bClear,bClose);
		//------------------------------------------------------------
		String sub = t.getText();
		if (sub.contains("N")) bS1.setSelection(true);
		if (sub.contains("A")) bS3.setSelection(true);
		if (sub.contains("B")) bS4.setSelection(true);
		if (sub.contains("C")) bS5.setSelection(true);
		if (sub.contains("D")) bS6.setSelection(true);
		if (sub.contains("E1")) bS7.setSelection(true);
		if (sub.contains("E2")) bS8.setSelection(true);
		if (sub.contains("E3")) bS9.setSelection(true);
		if (sub.contains("F")) bS10.setSelection(true);
		if (sub.contains("G")) bS11.setSelection(true);
		if (sub.contains("H")) bS12.setSelection(true);
		if (sub.contains("I")) bS13.setSelection(true);
		if (sub.contains("J1")) bS14.setSelection(true);
		if (sub.contains("J2")) bS15.setSelection(true);
		if (sub.contains("J3")) bS16.setSelection(true);
		if (sub.contains("J4")) bS17.setSelection(true);
		if (sub.contains("J5")) bS18.setSelection(true);
		if (sub.contains("J6")) bS19.setSelection(true);
		if (sub.contains("J7")) bS20.setSelection(true);
		if (sub.contains("K")) bS21.setSelection(true);
		if (sub.contains("L")) bS22.setSelection(true);
		if (sub.contains("M1")) bS23.setSelection(true);
		if (sub.contains("M2")) bS24.setSelection(true);
		if (sub.contains("M3")) bS25.setSelection(true);
		if (sub.contains("O")) bS26.setSelection(true);
		if (sub.contains("P1")) bS27.setSelection(true);
		if (sub.contains("P2")) bS28.setSelection(true);
		if (sub.contains("P3")) bS29.setSelection(true);
		if (sub.contains("P4")) bS30.setSelection(true);
		if (sub.contains("P5")) bS31.setSelection(true);
		if (sub.contains("P6")) bS32.setSelection(true);
		if (sub.contains("P7")) bS33.setSelection(true);
		if (sub.contains("P8")) bS34.setSelection(true);
		if (sub.contains("P9")) bS35.setSelection(true);
		if (sub.contains("R")) bS36.setSelection(true);
		if (sub.contains("T")) bS37.setSelection(true);
		if (sub.contains("U")) bS38.setSelection(true);
		if (sub.contains("V")) bS39.setSelection(true);
		if (sub.contains("W")) bS40.setSelection(true);
		if (sub.contains("X")) bS41.setSelection(true);
		if (sub.contains("Y")) bS42.setSelection(true);
		if (sub.contains("Z")) bS43.setSelection(true);
		
		if (sub.contains("N")) { bS1.setSelection(true); bS2.setEnabled(false); } 
		else { if (sub.contains("S")) bS2.setSelection(true); }
		if (sub.contains("S")) { bS2.setSelection(true); bS1.setEnabled(false); } 
		else { if (sub.contains("N")) bS1.setSelection(true); }
			
		//ToolTipText
		bS1.setToolTipText("N: if no COM/NAV/approach aid equipment for the route to be flown is carried, or the equipment is unserviceable");
		bS2.setToolTipText("S: if standard COM/NAV/approach aid equipment for the route to be flown is carried and serviceable\n\n" +
				"Note: If the letter S is used, standard equipment is considered to be VHF, RTF, VOR and ILS, " +
				"unless another combination is prescribed by the ATS authority.");
		bS3.setToolTipText("A: GBAS landing system");
		bS4.setToolTipText("B: LPV (APV with SBAS)");
		bS5.setToolTipText("C: LORAN C");
		bS6.setToolTipText("D: DME");
		bS7.setToolTipText("E1: FMC WPR ACARS");
		bS8.setToolTipText("E2: D-FIS ACARS");
		bS9.setToolTipText("E3: PDC ACARS");
		bS10.setToolTipText("F: ADF");
		bS11.setToolTipText("G: GNSS");
		bS12.setToolTipText("H: HF RTF");
		bS13.setToolTipText("I: Inertial Navigation");
		bS14.setToolTipText("J1: CPDLC ATN Mode 2");
		bS15.setToolTipText("J2: CPDLC FANS 1/A HFDL");
		bS16.setToolTipText("J3: CPDLC FANS 1/A VDL Mode A");
		bS17.setToolTipText("J4: CPDLC FANS 1/A VDL Mode 2");
		bS18.setToolTipText("J5: CPDLC FANS 1/A SATCOM (INMARSAT)");
		bS19.setToolTipText("J6: CPDLC FANS 1/A SATCOM (MTSAT)");
		bS20.setToolTipText("J7: CPDLC FANS 1/A SATCOM (Iridium)");
		bS21.setToolTipText("K: MLS");
		bS22.setToolTipText("L: ILS");
		bS23.setToolTipText("M1: ATC RTF SATCOM (INMARSAT)");
		bS24.setToolTipText("M2: ATC RTF (MTSAT)");
		bS25.setToolTipText("M3: ATC RTF (Iridium)");
		bS26.setToolTipText("O: VOR");
		bS27.setToolTipText("P1: Reserved for RCP");
		bS28.setToolTipText("P2: Reserved for RCP");
		bS29.setToolTipText("P3: Reserved for RCP");
		bS30.setToolTipText("P4: Reserved for RCP");
		bS31.setToolTipText("P5: Reserved for RCP");
		bS32.setToolTipText("P6: Reserved for RCP");
		bS33.setToolTipText("P7: Reserved for RCP");
		bS34.setToolTipText("P8: Reserved for RCP");
		bS35.setToolTipText("P9: Reserved for RCP");
		bS36.setToolTipText("R: PBN approved");
		bS37.setToolTipText("T: TACAN");
		bS38.setToolTipText("U: UHF RTF");
		bS39.setToolTipText("V: VHF RTF");
		bS40.setToolTipText("W: RVSM approved");
		bS41.setToolTipText("X: MNPS approved");
		bS42.setToolTipText("Y: VHF with 8.33 kHz channel spacing capability");
		
		//addListeners
		bS1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS1.getSelection()==true) bS2.setEnabled(false);
				else if (bS1.getSelection()==false) bS2.setEnabled(true);
			}
		});
		bS2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS2.getSelection()==true) bS1.setEnabled(false);
				else if (bS2.getSelection()==false) bS1.setEnabled(true);
			}
		});
		
		bAdd.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String s1="",s2="",s3="",s4="",s5="",s6="",s7="",s8="",s9="",s10="",s11="",s12="",s13="",s14="",
				s15="",s16="",s17="",s18="",s19="",s20="",s21="",s22="",s23="",s24="",s25="",s26="",s27="",s28="",
				s29="",s30="",s31="",s32="",s33="",s34="",s35="",s36="",s37="",s38="",s39="",s40="",s41="",s42="",s43="",all="";
				
				if (bS1.getSelection() == true) s1=bS1.getText(); if (s1.isEmpty()) s1="";
				if (bS2.getSelection() == true) s2=bS2.getText(); if (s2.isEmpty()) s2="";
				if (bS3.getSelection() == true) s3=bS3.getText(); if (s3.isEmpty()) s3="";
				if (bS4.getSelection() == true) s4=bS4.getText(); if (s4.isEmpty()) s4="";
				if (bS5.getSelection() == true) s5=bS5.getText(); if (s5.isEmpty()) s5="";
				if (bS6.getSelection() == true) s6=bS6.getText(); if (s6.isEmpty()) s6="";
				if (bS7.getSelection() == true) s7=bS7.getText(); if (s7.isEmpty()) s7="";
				if (bS8.getSelection() == true) s8=bS8.getText(); if (s8.isEmpty()) s8="";
				if (bS9.getSelection() == true) s9=bS9.getText(); if (s9.isEmpty()) s9="";
				if (bS10.getSelection() == true) s10=bS10.getText(); if (s10.isEmpty()) s10="";
				if (bS11.getSelection() == true) s11=bS11.getText(); if (s11.isEmpty()) s11="";
				if (bS12.getSelection() == true) s12=bS12.getText(); if (s12.isEmpty()) s12="";
				if (bS13.getSelection() == true) s13=bS13.getText(); if (s13.isEmpty()) s13="";
				if (bS14.getSelection() == true) s14=bS14.getText(); if (s14.isEmpty()) s14="";
				if (bS15.getSelection() == true) s15=bS15.getText(); if (s15.isEmpty()) s15="";
				if (bS16.getSelection() == true) s16=bS16.getText(); if (s16.isEmpty()) s16="";
				if (bS17.getSelection() == true) s17=bS17.getText(); if (s17.isEmpty()) s17="";
				if (bS18.getSelection() == true) s18=bS18.getText(); if (s18.isEmpty()) s18="";
				if (bS19.getSelection() == true) s19=bS19.getText(); if (s19.isEmpty()) s19="";
				if (bS20.getSelection() == true) s20=bS20.getText(); if (s20.isEmpty()) s20="";
				if (bS21.getSelection() == true) s21=bS21.getText(); if (s21.isEmpty()) s21="";
				if (bS22.getSelection() == true) s22=bS22.getText(); if (s22.isEmpty()) s22="";
				if (bS23.getSelection() == true) s23=bS23.getText(); if (s23.isEmpty()) s23="";
				if (bS24.getSelection() == true) s24=bS24.getText(); if (s24.isEmpty()) s24="";
				if (bS25.getSelection() == true) s25=bS25.getText(); if (s25.isEmpty()) s25="";
				if (bS26.getSelection() == true) s26=bS26.getText(); if (s26.isEmpty()) s26="";
				if (bS27.getSelection() == true) s27=bS27.getText(); if (s27.isEmpty()) s27="";
				if (bS28.getSelection() == true) s28=bS28.getText(); if (s28.isEmpty()) s28="";
				if (bS29.getSelection() == true) s29=bS29.getText(); if (s29.isEmpty()) s29="";
				if (bS30.getSelection() == true) s30=bS30.getText(); if (s30.isEmpty()) s30="";
				if (bS31.getSelection() == true) s31=bS31.getText(); if (s31.isEmpty()) s31="";
				if (bS32.getSelection() == true) s32=bS32.getText(); if (s32.isEmpty()) s32="";
				if (bS33.getSelection() == true) s33=bS33.getText(); if (s33.isEmpty()) s33="";
				if (bS34.getSelection() == true) s34=bS34.getText(); if (s34.isEmpty()) s34="";
				if (bS35.getSelection() == true) s35=bS35.getText(); if (s35.isEmpty()) s35="";
				if (bS36.getSelection() == true) s36=bS36.getText(); if (s36.isEmpty()) s36="";
				if (bS37.getSelection() == true) s37=bS37.getText(); if (s37.isEmpty()) s37="";
				if (bS38.getSelection() == true) s38=bS38.getText(); if (s38.isEmpty()) s38="";
				if (bS39.getSelection() == true) s39=bS39.getText(); if (s39.isEmpty()) s39="";
				if (bS40.getSelection() == true) s40=bS40.getText(); if (s40.isEmpty()) s40="";
				if (bS41.getSelection() == true) s41=bS41.getText(); if (s41.isEmpty()) s41="";
				if (bS42.getSelection() == true) s42=bS42.getText(); if (s42.isEmpty()) s42="";
				if (bS43.getSelection() == true) s43=bS43.getText(); if (s43.isEmpty()) s43="";
				
				all=s1+s2+s3+s4+s5+s6+s7+s8+s9+s10+s11+s12+s13+s14+s15+s16+s17+s18+s19+s20+s21+s22+s23+s24+s25+s26+s27+s28+s29+s30+
				s31+s32+s33+s34+s35+s36+s37+s38+s39+s40+s41+s42+s43;
				t.setText(all);

				if (s1.compareTo("")!=0 && s2.compareTo("")!=0) {
					DialogFactory.openWarningDialog(Shells.sh_m10a,"Input","Invalid EQUIPMENT AND CAPABILITIES value.\n'N' is not allowed, if 'S' has inserted into field EQUIPMENT AND CAPABILITIES and vice versa.");
				} else {
					shell.close();
				}
			}
		});
		
		bClear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				bS1.setEnabled(true); 
				bS2.setEnabled(true);
				
				if (bS1.getSelection() == true) bS1.setSelection(false); 
				if (bS2.getSelection() == true) bS2.setSelection(false);
				if (bS3.getSelection() == true) bS3.setSelection(false);
				if (bS4.getSelection() == true) bS4.setSelection(false);
				if (bS5.getSelection() == true) bS5.setSelection(false);
				if (bS6.getSelection() == true) bS6.setSelection(false);
				if (bS7.getSelection() == true) bS7.setSelection(false);
				if (bS8.getSelection() == true) bS8.setSelection(false);
				if (bS9.getSelection() == true) bS9.setSelection(false);
				if (bS10.getSelection() == true) bS10.setSelection(false);
				if (bS11.getSelection() == true) bS11.setSelection(false);
				if (bS12.getSelection() == true) bS12.setSelection(false);
				if (bS13.getSelection() == true) bS13.setSelection(false);
				if (bS14.getSelection() == true) bS14.setSelection(false);
				if (bS15.getSelection() == true) bS15.setSelection(false);
				if (bS16.getSelection() == true) bS16.setSelection(false);
				if (bS17.getSelection() == true) bS17.setSelection(false);
				if (bS18.getSelection() == true) bS18.setSelection(false);
				if (bS19.getSelection() == true) bS19.setSelection(false);
				if (bS20.getSelection() == true) bS20.setSelection(false);
				if (bS21.getSelection() == true) bS21.setSelection(false);
				if (bS22.getSelection() == true) bS22.setSelection(false);
				if (bS23.getSelection() == true) bS23.setSelection(false);
				if (bS24.getSelection() == true) bS24.setSelection(false);
				if (bS25.getSelection() == true) bS25.setSelection(false);
				if (bS26.getSelection() == true) bS26.setSelection(false);
				if (bS27.getSelection() == true) bS27.setSelection(false);
				if (bS28.getSelection() == true) bS28.setSelection(false);
				if (bS29.getSelection() == true) bS29.setSelection(false);
				if (bS30.getSelection() == true) bS30.setSelection(false);
				if (bS31.getSelection() == true) bS31.setSelection(false);
				if (bS32.getSelection() == true) bS32.setSelection(false);
				if (bS33.getSelection() == true) bS33.setSelection(false);
				if (bS34.getSelection() == true) bS34.setSelection(false);
				if (bS35.getSelection() == true) bS35.setSelection(false);
				if (bS36.getSelection() == true) bS36.setSelection(false);
				if (bS37.getSelection() == true) bS37.setSelection(false);
				if (bS38.getSelection() == true) bS38.setSelection(false);
				if (bS39.getSelection() == true) bS39.setSelection(false);
				if (bS40.getSelection() == true) bS40.setSelection(false);
				if (bS41.getSelection() == true) bS41.setSelection(false);
				if (bS42.getSelection() == true) bS42.setSelection(false);
				if (bS43.getSelection() == true) bS43.setSelection(false);
				t.setText("");
			}
		});
		
		bClose.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
	}
	
	void button(Button b, String str) {
		sh.buttonRCStyle(b, str, str, 55);//, 27);
	}
	
	void buttonSts(Button b, String str) {
		sh.buttonRCStyle(b, str, str, 120);//, 27);
	}
	
	void comp10b() {
		final Group Group = new Group(shell, SWT.CENTER); sh.groupStyle(Group, 1, "");
		Composite comp1 = new Composite(Group, SWT.NONE); sh.compositeStyle(comp1, 2, SWT.LEFT, SWT.CENTER); 
		Label label = new Label(comp1, SWT.NONE); sh.labelStyle1(label, "INSERT", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		final Button bS1 = new Button(comp1, SWT.CHECK); button(bS1, "N"); 

		label = new Label(Group, SWT.NONE); 
		sh.labelStyle1(label, "OR INSERT one or more of the following descriptors, to a maximum of 20 characters,\n" +
				"to describe the serviceable surveillance equipment and/or capabilities on board:", 
				SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		
		int iSpace=150;
		Composite line1 = new Composite(Group, SWT.NONE); sh.compositeStyle(line1, 3, SWT.LEFT, SWT.CENTER);
		label = new Label(line1, SWT.RIGHT); 
		sh.labelStyle(label, "SSR Modes A and C  ", iSpace, SWT.LEFT, SWT.CENTER, SWT.ITALIC | SWT.BOLD, Colors.NORMAL);
		final Button bS2 = new Button(line1, SWT.CHECK); button(bS2, "A"); 
		final Button bS3 = new Button(line1, SWT.CHECK); button(bS3, "C"); 

		Composite line2 = new Composite(Group, SWT.NONE); sh.compositeStyle(line2, 8, SWT.LEFT, SWT.CENTER);
		label = new Label(line2, SWT.RIGHT);
		sh.labelStyle(label, "SSR Modes S  ", iSpace, SWT.LEFT, SWT.CENTER, SWT.ITALIC | SWT.BOLD, Colors.NORMAL);
		final Button bS4 = new Button(line2, SWT.CHECK); button(bS4, "E"); 
		final Button bS5 = new Button(line2, SWT.CHECK); button(bS5, "H"); 
		final Button bS6 = new Button(line2, SWT.CHECK); button(bS6, "I"); 
		final Button bS7 = new Button(line2, SWT.CHECK); button(bS7, "L");
		final Button bS8 = new Button(line2, SWT.CHECK); button(bS8, "P");
		final Button bS9 = new Button(line2, SWT.CHECK); button(bS9, "S"); 
		final Button bS10 = new Button(line2, SWT.CHECK); button(bS10, "X"); 
		
		Composite line3 = new Composite(Group, SWT.NONE); sh.compositeStyle(line3, 7, SWT.LEFT, SWT.CENTER);
		label = new Label(line3, SWT.RIGHT); 
		sh.labelStyle(label, "ADS-B  ", iSpace, SWT.LEFT, SWT.CENTER, SWT.ITALIC | SWT.BOLD, Colors.NORMAL);
		final Button bS11 = new Button(line3, SWT.CHECK); button(bS11, "B1"); 
		final Button bS12 = new Button(line3, SWT.CHECK); button(bS12, "B2");
		final Button bS13 = new Button(line3, SWT.CHECK); button(bS13, "U1");
		final Button bS14 = new Button(line3, SWT.CHECK); button(bS14, "U2");
		final Button bS15 = new Button(line3, SWT.CHECK); button(bS15, "V1");
		final Button bS16 = new Button(line3, SWT.CHECK); button(bS16, "V2");
		
		Composite line4 = new Composite(Group, SWT.NONE); sh.compositeStyle(line4, 3, SWT.LEFT, SWT.CENTER);
		label = new Label(line4, SWT.RIGHT); 
		sh.labelStyle(label, "ADS-C  ", iSpace, SWT.LEFT, SWT.CENTER, SWT.ITALIC | SWT.BOLD, Colors.NORMAL);
		final Button bS17 = new Button(line4, SWT.CHECK); button(bS17, "D1");
		final Button bS18 = new Button(line4, SWT.CHECK); button(bS18, "G1");
		
		label = new Label(Group, SWT.NONE); 
		sh.labelStyle1(label, "Notes: Enhanced surveillance capability is the ability of the aircraft to down-link\n" +
				"aircraft derived data via a Mode S transponder.", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(Group, SWT.NONE); 
		sh.labelStyle1(label, "Alphanumeric characters not indicated above are reserved.", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(Group, SWT.NONE); 
		sh.labelStyle1(label, "Example: ADE3RV/HB2U2V2G1", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		label = new Label(Group, SWT.NONE); 
		sh.labelStyle1(label, "Notes: Additional surveillance application should be listed in Item 18 following\n" +
				"the indicator SUR/.", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		
		Composite typeB = new Composite(Group, SWT.NONE); sh.compositeStyle(typeB, 1, SWT.CENTER, SWT.CENTER);
		label = new Label(typeB, SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelStyle(label, "", 360, SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
		
		Composite typeE = new Composite(Group, SWT.NONE); sh.compositeStyle(typeE, 3, SWT.CENTER, SWT.CENTER);
		Button bAdd = new Button(typeE, SWT.PUSH);
		Button bClear = new Button(typeE, SWT.PUSH);
		Button bClose = new Button(typeE, SWT.PUSH);
		bs.ACC(bAdd, bClear,bClose);
		//------------------------------------------------------------
		String sub = t.getText();
		if (sub.contains("N")) {
			bS1.setSelection(true);
			bS2.setEnabled(false);
			bS3.setEnabled(false);
			bS4.setEnabled(false);
			bS5.setEnabled(false);
			bS6.setEnabled(false);
			bS7.setEnabled(false);
			bS8.setEnabled(false);
			bS9.setEnabled(false);
			bS10.setEnabled(false);
			bS11.setEnabled(false);
			bS12.setEnabled(false);
			bS13.setEnabled(false);
			bS14.setEnabled(false);
			bS15.setEnabled(false);
			bS16.setEnabled(false);
			bS17.setEnabled(false);
			bS18.setEnabled(false);
		} else if (sub.contains("A")) {
			bS1.setEnabled(false);
			bS2.setSelection(true);
			bS3.setEnabled(false);
			bS4.setEnabled(false);
			bS5.setEnabled(false);
			bS6.setEnabled(false);
			bS7.setEnabled(false);
			bS8.setEnabled(false);
			bS9.setEnabled(false);
			bS10.setEnabled(false);
		} else if (sub.contains("C")) {
			bS1.setEnabled(false);
			bS2.setEnabled(false);
			bS3.setSelection(true);
			bS4.setEnabled(false);
			bS5.setEnabled(false);
			bS6.setEnabled(false);
			bS7.setEnabled(false);
			bS8.setEnabled(false);
			bS9.setEnabled(false);
			bS10.setEnabled(false);
		} else if (sub.contains("E")) {
			bS1.setEnabled(false);
			bS2.setEnabled(false);
			bS3.setEnabled(false);
			bS4.setSelection(true);
			bS5.setEnabled(false);
			bS6.setEnabled(false);
			bS7.setEnabled(false);
			bS8.setEnabled(false);
			bS9.setEnabled(false);
			bS10.setEnabled(false);
		} else if (sub.contains("H")) {
			bS1.setEnabled(false);
			bS2.setEnabled(false);
			bS3.setEnabled(false);
			bS4.setEnabled(false);
			bS5.setSelection(true);
			bS6.setEnabled(false);
			bS7.setEnabled(false);
			bS8.setEnabled(false);
			bS9.setEnabled(false);
			bS10.setEnabled(false);
		} else if (sub.contains("I")) {
			bS1.setEnabled(false);
			bS2.setEnabled(false);
			bS3.setEnabled(false);
			bS4.setEnabled(false);
			bS5.setEnabled(false);
			bS6.setSelection(true);
			bS7.setEnabled(false);
			bS8.setEnabled(false);
			bS9.setEnabled(false);
			bS10.setEnabled(false);
		} else if (sub.contains("L")) {
			bS1.setEnabled(false);
			bS2.setEnabled(false);
			bS3.setEnabled(false);
			bS4.setEnabled(false);
			bS5.setEnabled(false);
			bS6.setEnabled(false);
			bS7.setSelection(true);
			bS8.setEnabled(false);
			bS9.setEnabled(false);
			bS10.setEnabled(false);
		} else if (sub.contains("P")) {
			bS1.setEnabled(false);
			bS2.setEnabled(false);
			bS3.setEnabled(false);
			bS4.setEnabled(false);
			bS5.setEnabled(false);
			bS6.setEnabled(false);
			bS7.setEnabled(false);
			bS8.setSelection(true);
			bS9.setEnabled(false);
			bS10.setEnabled(false);
		} else if (sub.contains("S")) {
			bS1.setEnabled(false);
			bS2.setEnabled(false);
			bS3.setEnabled(false);
			bS4.setEnabled(false);
			bS5.setEnabled(false);
			bS6.setEnabled(false);
			bS7.setEnabled(false);
			bS8.setEnabled(false);
			bS9.setSelection(true);
			bS10.setEnabled(false);
		} else if (sub.contains("X")) {
			bS1.setEnabled(false);
			bS2.setEnabled(false);
			bS3.setEnabled(false);
			bS4.setEnabled(false);
			bS5.setEnabled(false);
			bS6.setEnabled(false);
			bS7.setEnabled(false);
			bS8.setEnabled(false);
			bS9.setEnabled(false);
			bS10.setSelection(true);
		}

		if (sub.contains("B1")) bS11.setSelection(true);
		if (sub.contains("B2")) bS12.setSelection(true);
		if (sub.contains("U1")) bS13.setSelection(true);
		if (sub.contains("U2")) bS14.setSelection(true);
		if (sub.contains("V1")) bS15.setSelection(true);
		if (sub.contains("V2")) bS16.setSelection(true);
		if (sub.contains("D1")) bS17.setSelection(true);
		if (sub.contains("G1")) bS18.setSelection(true);
		
		if (sub.contains("B1") || sub.contains("U1") || sub.contains("V1")) {
			bS12.setEnabled(false);
			bS14.setEnabled(false);
			bS16.setEnabled(false);
		} else if (sub.contains("B2") || sub.contains("U2") || sub.contains("V2")) {
			bS11.setEnabled(false);
			bS13.setEnabled(false);
			bS15.setEnabled(false);
		}
			
		//ToolTipText
		bS1.setToolTipText("N: if no surveillance equipment for the route to be flown is carried, or the equipment is unserviceable.");
		bS2.setToolTipText("A: Transponder - Mode A (4 digits - 4096 codes)");
		bS3.setToolTipText("C: Transponder - Mode A (4 digits - 4096 codes) and Mode C");
		bS4.setToolTipText("E: Transponder - Mode S, including aircraft identification, pressure-altitude and extended squitter (ADS-B) capability");
		bS5.setToolTipText("H: Transponder - Mode S, including aircraft identification, pressure-altitude and enhanced surveillance capability");
		bS6.setToolTipText("I: Transponder - Mode S, including aircraft identification, but no pressure-altitude capability");
		bS7.setToolTipText("L: Transponder - Mode S, including aircraft identification, pressure-altitude, extended squitter (ADS-B) and enhanced surveillance capability");
		bS8.setToolTipText("P: Transponder - Mode S, including pressure-altitude, but no aircraft identification capability");
		bS9.setToolTipText("S: Transponder - Mode S, including both pressure-altitude and aircraft identification capability");
		bS10.setToolTipText("X: Transponder - Mode S, with neither aircraft identification nor pressure-altitude capability");
		bS11.setToolTipText("B1: ADS-B with dedicated 1090 MHz ADS-B 'out' capability");
		bS12.setToolTipText("B2: ADS-B with dedicated 1090 MHz ADS-B 'out' and 'in' capability");
		bS13.setToolTipText("U1: ADS-B 'out' capability using UAT");
		bS14.setToolTipText("U2: ADS-B 'out' and 'in' capability using UAT");
		bS15.setToolTipText("V1: ADS-B 'out' capability using VDL Mode 4");
		bS16.setToolTipText("V2: ADS-B 'out' and 'in' capability using VDL Mode 4");
		bS17.setToolTipText("D1: ADS-C with FANS 1/A capabilities");
		bS18.setToolTipText("G1: ADS-C with ATN capabilities");
		
		//addListeners
		bS1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS1.getSelection()==true) {
					bS2.setEnabled(false);
					bS3.setEnabled(false);
					bS4.setEnabled(false);
					bS5.setEnabled(false);
					bS6.setEnabled(false);
					bS7.setEnabled(false);
					bS8.setEnabled(false);
					bS9.setEnabled(false);
					bS10.setEnabled(false);
					bS11.setEnabled(false);
					bS12.setEnabled(false);
					bS13.setEnabled(false);
					bS14.setEnabled(false);
					bS15.setEnabled(false);
					bS16.setEnabled(false);
					bS17.setEnabled(false);
					bS18.setEnabled(false);
				}
				else if (bS1.getSelection()==false) {
					bS2.setEnabled(true);
					bS3.setEnabled(true);
					bS4.setEnabled(true);
					bS5.setEnabled(true);
					bS6.setEnabled(true);
					bS7.setEnabled(true);
					bS8.setEnabled(true);
					bS9.setEnabled(true);
					bS10.setEnabled(true);
					bS11.setEnabled(true);
					bS12.setEnabled(true);
					bS13.setEnabled(true);
					bS14.setEnabled(true);
					bS15.setEnabled(true);
					bS16.setEnabled(true);
					bS17.setEnabled(true);
					bS18.setEnabled(true);
				}
			}
		});
		bS2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS2.getSelection()==true) {
					bS1.setEnabled(false);
					bS3.setEnabled(false);
					bS4.setEnabled(false);
					bS5.setEnabled(false);
					bS6.setEnabled(false);
					bS7.setEnabled(false);
					bS8.setEnabled(false);
					bS9.setEnabled(false);
					bS10.setEnabled(false);
				} else if (bS2.getSelection()==false) {
					bS1.setEnabled(true);
					bS3.setEnabled(true);
					bS4.setEnabled(true);
					bS5.setEnabled(true);
					bS6.setEnabled(true);
					bS7.setEnabled(true);
					bS8.setEnabled(true);
					bS9.setEnabled(true);
					bS10.setEnabled(true);
				}
			}
		});
		bS3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS3.getSelection()==true) {
					bS1.setEnabled(false);
					bS2.setEnabled(false);
					bS4.setEnabled(false);
					bS5.setEnabled(false);
					bS6.setEnabled(false);
					bS7.setEnabled(false);
					bS8.setEnabled(false);
					bS9.setEnabled(false);
					bS10.setEnabled(false);
				} else if (bS3.getSelection()==false) {
					bS1.setEnabled(true);
					bS2.setEnabled(true);
					bS4.setEnabled(true);
					bS5.setEnabled(true);
					bS6.setEnabled(true);
					bS7.setEnabled(true);
					bS8.setEnabled(true);
					bS9.setEnabled(true);
					bS10.setEnabled(true);
				}
			}
		});
		bS4.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS4.getSelection()==true) {
					bS1.setEnabled(false);
					bS2.setEnabled(false);
					bS3.setEnabled(false);
					bS5.setEnabled(false);
					bS6.setEnabled(false);
					bS7.setEnabled(false);
					bS8.setEnabled(false);
					bS9.setEnabled(false);
					bS10.setEnabled(false);
				} else if (bS4.getSelection()==false) {
					bS1.setEnabled(true);
					bS2.setEnabled(true);
					bS3.setEnabled(true);
					bS5.setEnabled(true);
					bS6.setEnabled(true);
					bS7.setEnabled(true);
					bS8.setEnabled(true);
					bS9.setEnabled(true);
					bS10.setEnabled(true);
				} 
			}
		});
		bS5.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS5.getSelection()==true) {
					bS1.setEnabled(false);
					bS2.setEnabled(false);
					bS3.setEnabled(false);
					bS4.setEnabled(false);
					bS6.setEnabled(false);
					bS7.setEnabled(false);
					bS8.setEnabled(false);
					bS9.setEnabled(false);
					bS10.setEnabled(false);
				} else if (bS5.getSelection()==false) {
					bS1.setEnabled(true);
					bS2.setEnabled(true);
					bS3.setEnabled(true);
					bS4.setEnabled(true);
					bS6.setEnabled(true);
					bS7.setEnabled(true);
					bS8.setEnabled(true);
					bS9.setEnabled(true);
					bS10.setEnabled(true);
				}
			}
		});
		bS6.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS6.getSelection()==true) {
					bS1.setEnabled(false);
					bS2.setEnabled(false);
					bS3.setEnabled(false);
					bS4.setEnabled(false);
					bS5.setEnabled(false);
					bS7.setEnabled(false);
					bS8.setEnabled(false);
					bS9.setEnabled(false);
					bS10.setEnabled(false);
				} else if (bS6.getSelection()==false) {
					bS1.setEnabled(true);
					bS2.setEnabled(true);
					bS3.setEnabled(true);
					bS4.setEnabled(true);
					bS5.setEnabled(true);
					bS7.setEnabled(true);
					bS8.setEnabled(true);
					bS9.setEnabled(true);
					bS10.setEnabled(true);
				} 
			}
		});
		bS7.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS7.getSelection()==true) {
					bS1.setEnabled(false);
					bS2.setEnabled(false);
					bS3.setEnabled(false);
					bS4.setEnabled(false);
					bS5.setEnabled(false);
					bS6.setEnabled(false);
					bS8.setEnabled(false);
					bS9.setEnabled(false);
					bS10.setEnabled(false);
				} else if (bS7.getSelection()==false) {
					bS1.setEnabled(true);
					bS2.setEnabled(true);
					bS3.setEnabled(true);
					bS4.setEnabled(true);
					bS5.setEnabled(true);
					bS6.setEnabled(true);
					bS8.setEnabled(true);
					bS9.setEnabled(true);
					bS10.setEnabled(true);
				}
			}
		});
		bS8.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS8.getSelection()==true) {
					bS1.setEnabled(false);
					bS2.setEnabled(false);
					bS3.setEnabled(false);
					bS4.setEnabled(false);
					bS5.setEnabled(false);
					bS6.setEnabled(false);
					bS7.setEnabled(false);
					bS9.setEnabled(false);
					bS10.setEnabled(false);
				} else if (bS8.getSelection()==false) {
					bS1.setEnabled(true);
					bS2.setEnabled(true);
					bS3.setEnabled(true);
					bS4.setEnabled(true);
					bS5.setEnabled(true);
					bS6.setEnabled(true);
					bS7.setEnabled(true);
					bS9.setEnabled(true);
					bS10.setEnabled(true);
				} 
			}
		});
		bS9.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS9.getSelection()==true) {
					bS1.setEnabled(false);
					bS2.setEnabled(false);
					bS3.setEnabled(false);
					bS4.setEnabled(false);
					bS5.setEnabled(false);
					bS6.setEnabled(false);
					bS7.setEnabled(false);
					bS8.setEnabled(false);
					bS10.setEnabled(false);
				} else if (bS9.getSelection()==false) {
					bS1.setEnabled(true);
					bS2.setEnabled(true);
					bS3.setEnabled(true);
					bS4.setEnabled(true);
					bS5.setEnabled(true);
					bS6.setEnabled(true);
					bS7.setEnabled(true);
					bS8.setEnabled(true);
					bS10.setEnabled(true);
				}
			}
		});
		bS10.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS10.getSelection()==true) {
					bS1.setEnabled(false);
					bS2.setEnabled(false);
					bS3.setEnabled(false);
					bS4.setEnabled(false);
					bS5.setEnabled(false);
					bS6.setEnabled(false);
					bS7.setEnabled(false);
					bS8.setEnabled(false);
					bS9.setEnabled(false);
				} else if (bS10.getSelection()==false) {
					bS1.setEnabled(true);
					bS2.setEnabled(true);
					bS3.setEnabled(true);
					bS4.setEnabled(true);
					bS5.setEnabled(true);
					bS6.setEnabled(true);
					bS7.setEnabled(true);
					bS8.setEnabled(true);
					bS9.setEnabled(true);
				} 
			}
		});
		bS11.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS11.getSelection()==true || bS13.getSelection()==true || bS15.getSelection()==true) {
					bS12.setEnabled(false);
					bS14.setEnabled(false);
					bS16.setEnabled(false);
				} else if (bS11.getSelection()==false || bS13.getSelection()==false || bS15.getSelection()==false) {
					bS12.setEnabled(true);
					bS14.setEnabled(true);
					bS16.setEnabled(true);
				} 
			}
		});
		bS13.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS11.getSelection()==true || bS13.getSelection()==true || bS15.getSelection()==true) {
					bS12.setEnabled(false);
					bS14.setEnabled(false);
					bS16.setEnabled(false);
				} else if (bS11.getSelection()==false || bS13.getSelection()==false || bS15.getSelection()==false) {
					bS12.setEnabled(true);
					bS14.setEnabled(true);
					bS16.setEnabled(true);
				} 
			}
		});
		bS15.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS11.getSelection()==true || bS13.getSelection()==true || bS15.getSelection()==true) {
					bS12.setEnabled(false);
					bS14.setEnabled(false);
					bS16.setEnabled(false);
				} else if (bS11.getSelection()==false || bS13.getSelection()==false || bS15.getSelection()==false) {
					bS12.setEnabled(true);
					bS14.setEnabled(true);
					bS16.setEnabled(true);
				} 
			}
		});
		//---------------------------------------------------
		bS12.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS12.getSelection()==true || bS14.getSelection()==true || bS16.getSelection()==true) {
					bS11.setEnabled(false);
					bS13.setEnabled(false);
					bS15.setEnabled(false);
				} else if (bS12.getSelection()==false || bS14.getSelection()==false || bS16.getSelection()==false) {
					bS11.setEnabled(true);
					bS13.setEnabled(true);
					bS15.setEnabled(true);
				} 
			}
		});
		bS14.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS12.getSelection()==true || bS14.getSelection()==true || bS16.getSelection()==true) {
					bS11.setEnabled(false);
					bS13.setEnabled(false);
					bS15.setEnabled(false);
				} else if (bS12.getSelection()==false || bS14.getSelection()==false || bS16.getSelection()==false) {
					bS11.setEnabled(true);
					bS13.setEnabled(true);
					bS15.setEnabled(true);
				} 
			}
		});
		bS16.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS12.getSelection()==true || bS14.getSelection()==true || bS16.getSelection()==true) {
					bS11.setEnabled(false);
					bS13.setEnabled(false);
					bS15.setEnabled(false);
				} else if (bS12.getSelection()==false || bS14.getSelection()==false || bS16.getSelection()==false) {
					bS11.setEnabled(true);
					bS13.setEnabled(true);
					bS15.setEnabled(true);
				} 
			}
		});
		
		bAdd.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String s1="",s2="",s3="",s4="",s5="",s6="",s7="",s8="",s9="",s10="",s11="",s12="",s13="",s14="",
				s15="",s16="",s17="",s18="",all="";
				
				if (bS1.getSelection() == true) s1=bS1.getText(); if (s1.isEmpty()) s1="";
				if (bS2.getSelection() == true) s2=bS2.getText(); if (s2.isEmpty()) s2="";
				if (bS3.getSelection() == true) s3=bS3.getText(); if (s3.isEmpty()) s3="";
				if (bS4.getSelection() == true) s4=bS4.getText(); if (s4.isEmpty()) s4="";
				if (bS5.getSelection() == true) s5=bS5.getText(); if (s5.isEmpty()) s5="";
				if (bS6.getSelection() == true) s6=bS6.getText(); if (s6.isEmpty()) s6="";
				if (bS7.getSelection() == true) s7=bS7.getText(); if (s7.isEmpty()) s7="";
				if (bS8.getSelection() == true) s8=bS8.getText(); if (s8.isEmpty()) s8="";
				if (bS9.getSelection() == true) s9=bS9.getText(); if (s9.isEmpty()) s9="";
				if (bS10.getSelection() == true) s10=bS10.getText(); if (s10.isEmpty()) s10="";
				if (bS11.getSelection() == true) s11=bS11.getText(); if (s11.isEmpty()) s11="";
				if (bS12.getSelection() == true) s12=bS12.getText(); if (s12.isEmpty()) s12="";
				if (bS13.getSelection() == true) s13=bS13.getText(); if (s13.isEmpty()) s13="";
				if (bS14.getSelection() == true) s14=bS14.getText(); if (s14.isEmpty()) s14="";
				if (bS15.getSelection() == true) s15=bS15.getText(); if (s15.isEmpty()) s15="";
				if (bS16.getSelection() == true) s16=bS16.getText(); if (s16.isEmpty()) s16="";
				if (bS17.getSelection() == true) s17=bS17.getText(); if (s17.isEmpty()) s17="";
				if (bS18.getSelection() == true) s18=bS18.getText(); if (s18.isEmpty()) s18="";
				
				if (bS1.getSelection()==true) { s2="";s3="";s4="";s5="";s6="";s7="";s8="";s9="";s10="";
					s11="";s12="";s13="";s14="";s15="";s16="";s17="";s18=""; } 
				if (bS2.getSelection()==true) { s1="";s3="";s4="";s5="";s6="";s7="";s8="";s9="";s10=""; } 
				if (bS3.getSelection()==true) { s1="";s2="";s4="";s5="";s6="";s7="";s8="";s9="";s10=""; } 
				if (bS4.getSelection()==true) { s1="";s2="";s3="";s5="";s6="";s7="";s8="";s9="";s10=""; } 
				if (bS5.getSelection()==true) { s1="";s2="";s3="";s4="";s6="";s7="";s8="";s9="";s10=""; } 
				if (bS6.getSelection()==true) { s1="";s2="";s3="";s4="";s5="";s7="";s8="";s9="";s10=""; } 
				if (bS7.getSelection()==true) { s1="";s2="";s3="";s4="";s5="";s6="";s8="";s9="";s10=""; }
				if (bS8.getSelection()==true) { s1="";s2="";s3="";s4="";s5="";s6="";s7="";s9="";s10=""; } 
				if (bS9.getSelection()==true) { s1="";s2="";s3="";s4="";s5="";s6="";s7="";s8="";s10=""; } 
				if (bS10.getSelection()==true) { s1="";s2="";s3="";s4="";s5="";s6="";s7="";s8="";s9=""; }
				
				//percobaan baru
				if (bS11.getSelection()==true || bS13.getSelection()==true || bS15.getSelection()==true) { s12="";s14="";s16=""; }
				else if (bS12.getSelection()==true || bS14.getSelection()==true || bS16.getSelection()==true) { s11="";s13="";s15=""; }
				
				all=s1+s2+s3+s4+s5+s6+s7+s8+s9+s10+s11+s12+s13+s14+s15+s16+s17+s18;
				t.setText(all);
				shell.close();
			}
		});
		
		bClear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSelection10b(false, bS1, bS2, bS3, bS4, bS5, bS6, bS7, bS8, bS9, bS10, bS11, bS12, bS13, bS14, bS15, bS16, bS17, bS18);
				setEnable10b(true, bS1, bS2, bS3, bS4, bS5, bS6, bS7, bS8, bS9, bS10, bS11, bS12, bS13, bS14, bS15, bS16, bS17, bS18);
				t.setText("");
			}
		});
		
		bClose.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
	}
	
	void setSelection10b(boolean b,Button bS1,Button bS2,Button bS3,Button bS4,Button bS5,Button bS6,Button bS7,Button bS8,Button bS9,
			Button bS10,Button bS11,Button bS12,Button bS13,Button bS14,Button bS15,Button bS16,Button bS17,Button bS18) {
		bS1.setSelection(b); 
		bS2.setSelection(b);
		bS3.setSelection(b);
		bS4.setSelection(b);
		bS5.setSelection(b);
		bS6.setSelection(b);
		bS7.setSelection(b);
		bS8.setSelection(b);
		bS9.setSelection(b);
		bS10.setSelection(b);
		bS11.setSelection(b);
		bS12.setSelection(b);
		bS13.setSelection(b);
		bS14.setSelection(b);
		bS15.setSelection(b);
		bS16.setSelection(b);
		bS17.setSelection(b);
		bS18.setSelection(b);
	}
	
	void setEnable10b(boolean b,Button bS1,Button bS2,Button bS3,Button bS4,Button bS5,Button bS6,Button bS7,Button bS8,Button bS9,Button bS10,
			Button bS11,Button bS12,Button bS13,Button bS14,Button bS15,Button bS16,Button bS17,Button bS18) {
		bS1.setEnabled(b); 
		bS2.setEnabled(b);
		bS3.setEnabled(b);
		bS4.setEnabled(b);
		bS5.setEnabled(b);
		bS6.setEnabled(b);
		bS7.setEnabled(b);
		bS8.setEnabled(b);
		bS9.setEnabled(b);
		bS10.setEnabled(b);
		bS11.setEnabled(b);
		bS12.setEnabled(b);
		bS13.setEnabled(b);
		bS14.setEnabled(b);
		bS15.setEnabled(b);
		bS16.setEnabled(b);
		bS17.setEnabled(b);
		bS18.setEnabled(b);
	}
	
	void compPBN() {
		final Group Group = new Group(shell, SWT.CENTER); sh.groupStyle(Group, 1, "");
		Label label = new Label(Group, SWT.NONE); sh.labelStyle1(label, "RNAV SPECIFICATIONS", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		
		Composite compA = new Composite(Group, SWT.NONE); sh.compositeStyle(compA, 1, SWT.LEFT, SWT.CENTER);
		bPBN1 = new Button(compA, SWT.CHECK); button(bPBN1, "A1"); 
		Composite compB = new Composite(Group, SWT.NONE); sh.compositeStyle(compB, 6, SWT.LEFT, SWT.CENTER);
		bPBN2 = new Button(compB, SWT.CHECK); button(bPBN2, "B1");
		bPBN3 = new Button(compB, SWT.CHECK); button(bPBN3, "B2");
		bPBN4 = new Button(compB, SWT.CHECK); button(bPBN4, "B3");
		bPBN5 = new Button(compB, SWT.CHECK); button(bPBN5, "B4");
		bPBN6 = new Button(compB, SWT.CHECK); button(bPBN6, "B5");
		bPBN7 = new Button(compB, SWT.CHECK); button(bPBN7, "B6");
		Composite compC = new Composite(Group, SWT.NONE); sh.compositeStyle(compC, 4, SWT.LEFT, SWT.CENTER);
		bPBN8 = new Button(compC, SWT.CHECK); button(bPBN8, "C1");
		bPBN9 = new Button(compC, SWT.CHECK); button(bPBN9, "C2");
		bPBN10 = new Button(compC, SWT.CHECK); button(bPBN10, "C3");
		bPBN11 = new Button(compC, SWT.CHECK); button(bPBN11, "C4");
		Composite compD = new Composite(Group, SWT.NONE); sh.compositeStyle(compD, 4, SWT.LEFT, SWT.CENTER);
		bPBN12 = new Button(compD, SWT.CHECK); button(bPBN12, "D1");
		bPBN13 = new Button(compD, SWT.CHECK); button(bPBN13, "D2");
		bPBN14 = new Button(compD, SWT.CHECK); button(bPBN14, "D3");
		bPBN15 = new Button(compD, SWT.CHECK); button(bPBN15, "D4");
		
		label = new Label(Group, SWT.NONE); sh.labelStyle1(label, "\nRNP SPECIFICATIONS", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		Composite compE = new Composite(Group, SWT.NONE); sh.compositeStyle(compE, 1, SWT.LEFT, SWT.CENTER);
		bPBN16 = new Button(compE, SWT.CHECK); button(bPBN16, "L1"); 
		Composite compF = new Composite(Group, SWT.NONE); sh.compositeStyle(compF, 4, SWT.LEFT, SWT.CENTER);
		bPBN17 = new Button(compF, SWT.CHECK); button(bPBN17, "O1");
		bPBN18 = new Button(compF, SWT.CHECK); button(bPBN18, "O2");
		bPBN19 = new Button(compF, SWT.CHECK); button(bPBN19, "O3");
		bPBN20 = new Button(compF, SWT.CHECK); button(bPBN20, "O4");
		Composite compG = new Composite(Group, SWT.NONE); sh.compositeStyle(compG, 2, SWT.LEFT, SWT.CENTER);
		bPBN21 = new Button(compG, SWT.CHECK); button(bPBN21, "S1");
		bPBN22 = new Button(compG, SWT.CHECK); button(bPBN22, "S2");
		Composite compH = new Composite(Group, SWT.NONE); sh.compositeStyle(compH, 2, SWT.LEFT, SWT.CENTER);
		bPBN23 = new Button(compH, SWT.CHECK); button(bPBN23, "T1");
		bPBN24 = new Button(compH, SWT.CHECK); button(bPBN24, "T2");
		
		Composite typeB = new Composite(Group, SWT.NONE); sh.compositeStyle(typeB, 1, SWT.CENTER, SWT.CENTER);
		label = new Label(typeB, SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelStyle(label, "", 360, SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
		
		Composite typeE = new Composite(Group, SWT.NONE); sh.compositeStyle(typeE, 3, SWT.CENTER, SWT.CENTER);
		Button bAdd = new Button(typeE, SWT.PUSH);
		Button bClear = new Button(typeE, SWT.PUSH);
		Button bClose = new Button(typeE, SWT.PUSH);
		bs.ACC(bAdd, bClear,bClose);
		
		String sub = t.getText();
		if (sub.contains("A1")) bPBN1.setSelection(true);
		if (sub.contains("B1")) bPBN2.setSelection(true);
		if (sub.contains("B2")) bPBN3.setSelection(true);
		if (sub.contains("B3")) bPBN4.setSelection(true);
		if (sub.contains("B4")) bPBN5.setSelection(true);
		if (sub.contains("B5")) bPBN6.setSelection(true);
		if (sub.contains("B6")) bPBN7.setSelection(true);
		if (sub.contains("C1")) bPBN8.setSelection(true);
		if (sub.contains("C2")) bPBN9.setSelection(true);
		if (sub.contains("C3")) bPBN10.setSelection(true);
		if (sub.contains("C4")) bPBN11.setSelection(true);
		if (sub.contains("D1")) bPBN12.setSelection(true);
		if (sub.contains("D2")) bPBN13.setSelection(true);
		if (sub.contains("D3")) bPBN14.setSelection(true);
		if (sub.contains("D4")) bPBN15.setSelection(true);
		if (sub.contains("L1")) bPBN16.setSelection(true);
		if (sub.contains("O1")) bPBN17.setSelection(true);
		if (sub.contains("O2")) bPBN18.setSelection(true);
		if (sub.contains("O3")) bPBN19.setSelection(true);
		if (sub.contains("O4")) bPBN20.setSelection(true);
		if (sub.contains("S1")) bPBN21.setSelection(true);
		if (sub.contains("S2")) bPBN22.setSelection(true);
		if (sub.contains("T1")) bPBN23.setSelection(true);
		if (sub.contains("T2")) bPBN24.setSelection(true);
		
		//ToolTipText
		bPBN1.setToolTipText("A1: RNAV 10 (RNP 10)");
		bPBN2.setToolTipText("B1: RNAV 5 all permitted sensors");
		bPBN3.setToolTipText("B2: RNAV 5 GNSS");
		bPBN4.setToolTipText("B3: RNAV 5 DME/DME");
		bPBN5.setToolTipText("B4: RNAV 5 VOR/DME");
		bPBN6.setToolTipText("B5: RNAV 5 INS or IRS");
		bPBN7.setToolTipText("B6: RNAV 5 LORANC");
		bPBN8.setToolTipText("C1: RNAV 2 all permitted sensors");
		bPBN9.setToolTipText("C2: RNAV 2 GNSS");
		bPBN10.setToolTipText("C3: RNAV 2 DME/DME");
		bPBN11.setToolTipText("C4: RNAV 2 DME/DME/IRU");
		bPBN12.setToolTipText("D1: RNAV 1 all permitted sensors");
		bPBN13.setToolTipText("D2: RNAV 1 GNSS");
		bPBN14.setToolTipText("D3: RNAV 1 DME/DME");
		bPBN15.setToolTipText("D4: RNAV 1 DME/DME/IRU");
		bPBN16.setToolTipText("L1: RNP 4");
		bPBN17.setToolTipText("O1: Basic RNP 1 all permitted sensors");
		bPBN18.setToolTipText("O2: Basic RNP 1 GNSS");
		bPBN19.setToolTipText("O3: Basic RNP 1 DME/DME");
		bPBN20.setToolTipText("O4: Basic RNP 1 DME/DME/IRU");
		bPBN21.setToolTipText("S1: RNP APCH");
		bPBN22.setToolTipText("S2: RNP APCH with BARO-VNAV");
		bPBN23.setToolTipText("T1: RNP AR APCH with AF (special authorization required)");
		bPBN24.setToolTipText("T2: RNP AR APCH without AF (special authorization required)");
		
		//addListeners
		bAdd.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String s1="",s2="",s3="",s4="",s5="",s6="",s7="",s8="",s9="",s10="",s11="",s12="",s13="",s14="",
				s15="",s16="",s17="",s18="",s19="",s20="",s21="",s22="",s23="",s24="",all="";
				
				if (bPBN1.getSelection() == true) s1=bPBN1.getText(); if (s1.isEmpty()) s1="";
				if (bPBN2.getSelection() == true) s2=bPBN2.getText(); if (s2.isEmpty()) s2="";
				if (bPBN3.getSelection() == true) s3=bPBN3.getText(); if (s3.isEmpty()) s3="";
				if (bPBN4.getSelection() == true) s4=bPBN4.getText(); if (s4.isEmpty()) s4="";
				if (bPBN5.getSelection() == true) s5=bPBN5.getText(); if (s5.isEmpty()) s5="";
				if (bPBN6.getSelection() == true) s6=bPBN6.getText(); if (s6.isEmpty()) s6="";
				if (bPBN7.getSelection() == true) s7=bPBN7.getText(); if (s7.isEmpty()) s7="";
				if (bPBN8.getSelection() == true) s8=bPBN8.getText(); if (s8.isEmpty()) s8="";
				if (bPBN9.getSelection() == true) s9=bPBN9.getText(); if (s9.isEmpty()) s9="";
				if (bPBN10.getSelection() == true) s10=bPBN10.getText(); if (s10.isEmpty()) s10="";
				if (bPBN11.getSelection() == true) s11=bPBN11.getText(); if (s11.isEmpty()) s11="";
				if (bPBN12.getSelection() == true) s12=bPBN12.getText(); if (s12.isEmpty()) s12="";
				if (bPBN13.getSelection() == true) s13=bPBN13.getText(); if (s13.isEmpty()) s13="";
				if (bPBN14.getSelection() == true) s14=bPBN14.getText(); if (s14.isEmpty()) s14="";
				if (bPBN15.getSelection() == true) s15=bPBN15.getText(); if (s15.isEmpty()) s15="";
				if (bPBN16.getSelection() == true) s16=bPBN16.getText(); if (s16.isEmpty()) s16="";
				if (bPBN17.getSelection() == true) s17=bPBN17.getText(); if (s17.isEmpty()) s17="";
				if (bPBN18.getSelection() == true) s18=bPBN18.getText(); if (s18.isEmpty()) s18="";
				if (bPBN19.getSelection() == true) s19=bPBN19.getText(); if (s19.isEmpty()) s19="";
				if (bPBN20.getSelection() == true) s20=bPBN20.getText(); if (s20.isEmpty()) s20="";
				if (bPBN21.getSelection() == true) s21=bPBN21.getText(); if (s21.isEmpty()) s21="";
				if (bPBN22.getSelection() == true) s22=bPBN22.getText(); if (s22.isEmpty()) s22="";
				if (bPBN23.getSelection() == true) s23=bPBN23.getText(); if (s23.isEmpty()) s23="";
				if (bPBN24.getSelection() == true) s24=bPBN24.getText(); if (s24.isEmpty()) s24="";
				
				all=s1+s2+s3+s4+s5+s6+s7+s8+s9+s10+s11+s12+s13+s14+s15+s16+s17+s18+s19+s20+s21+s22+s23+s24;
				t.setText(all);

				if (all.length()>16) {
					DialogFactory.openWarningDialog(Shells.sh_mPBN, "Input","Please insert maximum 8 entries, i.e. a total of not more than 16 characters !!");
				} else {
					shell.close();
				}
			}
		});
		
		bClear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bPBN1.getSelection() == true) bPBN1.setSelection(false); 
				if (bPBN2.getSelection() == true) bPBN2.setSelection(false);
				if (bPBN3.getSelection() == true) bPBN3.setSelection(false);
				if (bPBN4.getSelection() == true) bPBN4.setSelection(false);
				if (bPBN5.getSelection() == true) bPBN5.setSelection(false);
				if (bPBN6.getSelection() == true) bPBN6.setSelection(false);
				if (bPBN7.getSelection() == true) bPBN7.setSelection(false);
				if (bPBN8.getSelection() == true) bPBN8.setSelection(false);
				if (bPBN9.getSelection() == true) bPBN9.setSelection(false);
				if (bPBN10.getSelection() == true) bPBN10.setSelection(false);
				if (bPBN11.getSelection() == true) bPBN11.setSelection(false);
				if (bPBN12.getSelection() == true) bPBN12.setSelection(false);
				if (bPBN13.getSelection() == true) bPBN13.setSelection(false);
				if (bPBN14.getSelection() == true) bPBN14.setSelection(false);
				if (bPBN15.getSelection() == true) bPBN15.setSelection(false);
				if (bPBN16.getSelection() == true) bPBN16.setSelection(false);
				if (bPBN17.getSelection() == true) bPBN17.setSelection(false);
				if (bPBN18.getSelection() == true) bPBN18.setSelection(false);
				if (bPBN19.getSelection() == true) bPBN19.setSelection(false);
				if (bPBN20.getSelection() == true) bPBN20.setSelection(false);
				if (bPBN21.getSelection() == true) bPBN21.setSelection(false);
				if (bPBN22.getSelection() == true) bPBN22.setSelection(false);
				if (bPBN23.getSelection() == true) bPBN23.setSelection(false);
				if (bPBN24.getSelection() == true) bPBN24.setSelection(false);
				t.setText("");
			}
		});
		
		bClose.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
	}
	
	void compPBN_ASLI() {
		final Group Group = new Group(shell, SWT.CENTER); sh.groupStyle(Group, 1, "");
		Label label = new Label(Group, SWT.NONE); sh.labelStyle1(label, "RNAV SPECIFICATIONS", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		
		Composite compA = new Composite(Group, SWT.NONE); sh.compositeStyle(compA, 1, SWT.LEFT, SWT.CENTER);
		bPBN1 = new Button(compA, SWT.CHECK); button(bPBN1, "A1"); 
		Composite compB = new Composite(Group, SWT.NONE); sh.compositeStyle(compB, 6, SWT.LEFT, SWT.CENTER);
		bPBN2 = new Button(compB, SWT.CHECK); button(bPBN2, "B1");
		bPBN3 = new Button(compB, SWT.CHECK); button(bPBN3, "B2");
		bPBN4 = new Button(compB, SWT.CHECK); button(bPBN4, "B3");
		bPBN5 = new Button(compB, SWT.CHECK); button(bPBN5, "B4");
		bPBN6 = new Button(compB, SWT.CHECK); button(bPBN6, "B5");
		bPBN7 = new Button(compB, SWT.CHECK); button(bPBN7, "B6");
		Composite compC = new Composite(Group, SWT.NONE); sh.compositeStyle(compC, 4, SWT.LEFT, SWT.CENTER);
		bPBN8 = new Button(compC, SWT.CHECK); button(bPBN8, "C1");
		bPBN9 = new Button(compC, SWT.CHECK); button(bPBN9, "C2");
		bPBN10 = new Button(compC, SWT.CHECK); button(bPBN10, "C3");
		bPBN11 = new Button(compC, SWT.CHECK); button(bPBN11, "C4");
		Composite compD = new Composite(Group, SWT.NONE); sh.compositeStyle(compD, 4, SWT.LEFT, SWT.CENTER);
		bPBN12 = new Button(compD, SWT.CHECK); button(bPBN12, "D1");
		bPBN13 = new Button(compD, SWT.CHECK); button(bPBN13, "D2");
		bPBN14 = new Button(compD, SWT.CHECK); button(bPBN14, "D3");
		bPBN15 = new Button(compD, SWT.CHECK); button(bPBN15, "D4");
		
		label = new Label(Group, SWT.NONE); sh.labelStyle1(label, "RNP SPECIFICATIONS", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		Composite compE = new Composite(Group, SWT.NONE); sh.compositeStyle(compE, 1, SWT.LEFT, SWT.CENTER);
		bPBN16 = new Button(compE, SWT.CHECK); button(bPBN16, "L1"); 
		Composite compF = new Composite(Group, SWT.NONE); sh.compositeStyle(compF, 4, SWT.LEFT, SWT.CENTER);
		bPBN17 = new Button(compF, SWT.CHECK); button(bPBN17, "O1");
		bPBN18 = new Button(compF, SWT.CHECK); button(bPBN18, "O2");
		bPBN19 = new Button(compF, SWT.CHECK); button(bPBN19, "O3");
		bPBN20 = new Button(compF, SWT.CHECK); button(bPBN20, "O4");
		Composite compG = new Composite(Group, SWT.NONE); sh.compositeStyle(compG, 2, SWT.LEFT, SWT.CENTER);
		bPBN21 = new Button(compG, SWT.CHECK); button(bPBN21, "S1");
		bPBN22 = new Button(compG, SWT.CHECK); button(bPBN22, "S2");
		Composite compH = new Composite(Group, SWT.NONE); sh.compositeStyle(compH, 2, SWT.LEFT, SWT.CENTER);
		bPBN23 = new Button(compH, SWT.CHECK); button(bPBN23, "T1");
		bPBN24 = new Button(compH, SWT.CHECK); button(bPBN24, "T2");
		
		Composite typeB = new Composite(Group, SWT.NONE); sh.compositeStyle(typeB, 1, SWT.CENTER, SWT.CENTER);
		label = new Label(typeB, SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelStyle(label, "", 360, SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
		
//		Composite typeC = new Composite(Group, SWT.NONE); sh.compositeStyle(typeC, 2, SWT.LEFT, SWT.CENTER);
//		Button bCheckAll = new Button(typeC, SWT.RADIO); sh.buttonRCStyle(bCheckAll, "Check all", "Check all", 120, 27);
//		Button bUncheckAll = new Button(typeC, SWT.RADIO); sh.buttonRCStyle(bUncheckAll, "Uncheck all", "Uncheck all", 120, 27);
		Composite compUn = new Composite(Group, SWT.NONE); sh.compositeStyle(compUn, 1, SWT.LEFT, SWT.CENTER);
		Button bUncheck = new Button(compUn, SWT.CHECK); sh.buttonRCStyle(bUncheck, "Uncheck all", "Uncheck all", 120);//, bs.heightBtn);
		bUncheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		
		Composite typeD = new Composite(Group, SWT.NONE); sh.compositeStyle(typeD, 1, SWT.CENTER, SWT.CENTER);
		label = new Label(typeD, SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelStyle(label, "", 360, SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
		
		Composite typeE = new Composite(Group, SWT.NONE); sh.compositeStyle(typeE, 2, SWT.CENTER, SWT.CENTER);
		Button Input = new Button(typeE, SWT.PUSH);
		Button Cancel = new Button(typeE, SWT.PUSH);
		
//		Composite compPbn1 = new Composite(Group, SWT.NONE); sh.compositeStyle(compPbn1, 7, SWT.LEFT, SWT.CENTER);
//		final Button bS1 = new Button(compPbn1, SWT.CHECK); button(bS1, "A1"); label = new Label(compPbn1, SWT.NONE); label.setText("\t"); 
//		final Button bS2 = new Button(compPbn1, SWT.CHECK); button(bS2, "B1"); label = new Label(compPbn1, SWT.NONE); label.setText("\t");
//		final Button bS8 = new Button(compPbn1, SWT.CHECK); button(bS8, "C1"); label = new Label(compPbn1, SWT.NONE); label.setText("\t");
//		final Button bS12 = new Button(compPbn1, SWT.CHECK); button(bS12, "D1");
//		//--
//		label = new Label(compPbn1, SWT.NONE);  label = new Label(compPbn1, SWT.NONE);
//		final Button bS3 = new Button(compPbn1, SWT.CHECK); button(bS3, "B2"); label = new Label(compPbn1, SWT.NONE);
//		final Button bS9 = new Button(compPbn1, SWT.CHECK); button(bS9, "C2"); label = new Label(compPbn1, SWT.NONE);
//		final Button bS13 = new Button(compPbn1, SWT.CHECK); button(bS13, "D2");
//		//--
//		label = new Label(compPbn1, SWT.NONE);  label = new Label(compPbn1, SWT.NONE);
//		final Button bS4 = new Button(compPbn1, SWT.CHECK); button(bS4, "B3"); label = new Label(compPbn1, SWT.NONE);
//		final Button bS10 = new Button(compPbn1, SWT.CHECK); button(bS10, "C3"); label = new Label(compPbn1, SWT.NONE);
//		final Button bS14 = new Button(compPbn1, SWT.CHECK); button(bS14, "D3");
//		//--
//		label = new Label(compPbn1, SWT.NONE);  label = new Label(compPbn1, SWT.NONE);
//		final Button bS5 = new Button(compPbn1, SWT.CHECK); button(bS5, "B4"); label = new Label(compPbn1, SWT.NONE);
//		final Button bS11 = new Button(compPbn1, SWT.CHECK); button(bS11, "C4"); label = new Label(compPbn1, SWT.NONE);
//		final Button bS15 = new Button(compPbn1, SWT.CHECK); button(bS15, "D4");
//		//--
//		label = new Label(compPbn1, SWT.NONE); label = new Label(compPbn1, SWT.NONE); 
//		final Button bS6 = new Button(compPbn1, SWT.CHECK); button(bS6, "B5"); label = new Label(compPbn1, SWT.NONE);
//		label = new Label(compPbn1, SWT.NONE);  label = new Label(compPbn1, SWT.NONE);
//		label = new Label(compPbn1, SWT.NONE);
//		//--
//		label = new Label(compPbn1, SWT.NONE);  label = new Label(compPbn1, SWT.NONE);
//		final Button bS7 = new Button(compPbn1, SWT.CHECK); button(bS7, "B6"); label = new Label(compPbn1, SWT.NONE);
//		label = new Label(compPbn1, SWT.NONE); label = new Label(compPbn1, SWT.NONE);
//		label = new Label(compPbn1, SWT.NONE); 
//		//------------------------------------------------------------
//		label = new Label(Group, SWT.NONE); sh.labelStyle1(label, "RNP SPECIFICATIONS", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
//		
//		Composite compPbn2 = new Composite(Group, SWT.NONE); sh.compositeStyle(compPbn2, 7, SWT.LEFT, SWT.CENTER);
//		final Button bS16 = new Button(compPbn2, SWT.CHECK); button(bS16, "L1"); label = new Label(compPbn2, SWT.NONE); label.setText("\t");
//		final Button bS17 = new Button(compPbn2, SWT.CHECK); button(bS17, "O1"); label = new Label(compPbn2, SWT.NONE); label.setText("\t");
//		final Button bS21 = new Button(compPbn2, SWT.CHECK); button(bS21, "S1"); label = new Label(compPbn2, SWT.NONE); label.setText("\t");
//		final Button bS23 = new Button(compPbn2, SWT.CHECK); button(bS23, "T1");
//		//--
//		label = new Label(compPbn2, SWT.NONE); label = new Label(compPbn2, SWT.NONE);
//		final Button bS18 = new Button(compPbn2, SWT.CHECK); button(bS18, "O2"); label = new Label(compPbn2, SWT.NONE);
//		final Button bS22 = new Button(compPbn2, SWT.CHECK); button(bS22, "S2"); label = new Label(compPbn2, SWT.NONE);
//		final Button bS24 = new Button(compPbn2, SWT.CHECK); button(bS24, "T2");
//		//--
//		label = new Label(compPbn2, SWT.NONE); label = new Label(compPbn2, SWT.NONE); 
//		final Button bS19 = new Button(compPbn2, SWT.CHECK); button(bS19, "O3"); label = new Label(compPbn2, SWT.NONE);
//		label = new Label(compPbn2, SWT.NONE); label = new Label(compPbn2, SWT.NONE);
//		label = new Label(compPbn2, SWT.NONE);
//		//--
//		label = new Label(compPbn2, SWT.NONE); label = new Label(compPbn2, SWT.NONE); 
//		final Button bS20 = new Button(compPbn2, SWT.CHECK); button(bS20, "O4"); label = new Label(compPbn2, SWT.NONE);
//		label = new Label(compPbn2, SWT.NONE); label = new Label(compPbn2, SWT.NONE);
//		label = new Label(compPbn2, SWT.NONE);
		//------------------------------------------------------------
		String sub = t.getText();
//		String sub[] = ATSForms.tPbn.getText().split(" ");//ATSForms.tPbnFPL.getText().split(" ");
//		for (int i=0; i<sub.length; i++) {
			if (sub.contains("A1")) bPBN1.setSelection(true);
			if (sub.contains("B1")) bPBN2.setSelection(true);
			if (sub.contains("B2")) bPBN3.setSelection(true);
			if (sub.contains("B3")) bPBN4.setSelection(true);
			if (sub.contains("B4")) bPBN5.setSelection(true);
			if (sub.contains("B5")) bPBN6.setSelection(true);
			if (sub.contains("B6")) bPBN7.setSelection(true);
			if (sub.contains("C1")) bPBN8.setSelection(true);
			if (sub.contains("C2")) bPBN9.setSelection(true);
			if (sub.contains("C3")) bPBN10.setSelection(true);
			if (sub.contains("C4")) bPBN11.setSelection(true);
			if (sub.contains("D1")) bPBN12.setSelection(true);
			if (sub.contains("D2")) bPBN13.setSelection(true);
			if (sub.contains("D3")) bPBN14.setSelection(true);
			if (sub.contains("D4")) bPBN15.setSelection(true);
			if (sub.contains("L1")) bPBN16.setSelection(true);
			if (sub.contains("O1")) bPBN17.setSelection(true);
			if (sub.contains("O2")) bPBN18.setSelection(true);
			if (sub.contains("O3")) bPBN19.setSelection(true);
			if (sub.contains("O4")) bPBN20.setSelection(true);
			if (sub.contains("S1")) bPBN21.setSelection(true);
			if (sub.contains("S2")) bPBN22.setSelection(true);
			if (sub.contains("T1")) bPBN23.setSelection(true);
			if (sub.contains("T2")) bPBN24.setSelection(true);
//		} //end of for sub
		
		//ToolTipText
		bPBN1.setToolTipText("A1: RNAV 10 (RNP 10)");
		
		bPBN2.setToolTipText("B1: RNAV 5 all permitted sensors");
		bPBN3.setToolTipText("B2: RNAV 5 GNSS");
		bPBN4.setToolTipText("B3: RNAV 5 DME/DME");
		bPBN5.setToolTipText("B4: RNAV 5 VOR/DME");
		bPBN6.setToolTipText("B5: RNAV 5 INS or IRS");
		bPBN7.setToolTipText("B6: RNAV 5 LORANC");
		
		bPBN8.setToolTipText("C1: RNAV 2 all permitted sensors");
		bPBN9.setToolTipText("C2: RNAV 2 GNSS");
		bPBN10.setToolTipText("C3: RNAV 2 DME/DME");
		bPBN11.setToolTipText("C4: RNAV 2 DME/DME/IRU");
		
		bPBN12.setToolTipText("D1: RNAV 1 all permitted sensors");
		bPBN13.setToolTipText("D2: RNAV 1 GNSS");
		bPBN14.setToolTipText("D3: RNAV 1 DME/DME");
		bPBN15.setToolTipText("D4: RNAV 1 DME/DME/IRU");
		
		bPBN16.setToolTipText("L1: RNP 4");
		
		bPBN17.setToolTipText("O1: Basic RNP 1 all permitted sensors");
		bPBN18.setToolTipText("O2: Basic RNP 1 GNSS");
		bPBN19.setToolTipText("O3: Basic RNP 1 DME/DME");
		bPBN20.setToolTipText("O4: Basic RNP 1 DME/DME/IRU");
		
		bPBN21.setToolTipText("S1: RNP APCH");
		bPBN22.setToolTipText("S2: RNP APCH with BARO-VNAV");
		
		bPBN23.setToolTipText("T1: RNP AR APCH with AF (special authorization required)");
		bPBN24.setToolTipText("T2: RNP AR APCH without AF (special authorization required)");
		
//		bCheckAll.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				checkPBN(true);
//			}
//		});
		
		bUncheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				checkPBN(false);
			}
		});
		
		Input.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String s1="",s2="",s3="",s4="",s5="",s6="",s7="",s8="",s9="",s10="",s11="",s12="",s13="",s14="",
				s15="",s16="",s17="",s18="",s19="",s20="",s21="",s22="",s23="",s24="",all="";
				
				if (bPBN1.getSelection() == true) s1=bPBN1.getText(); if (s1.isEmpty()) s1="";
				if (bPBN2.getSelection() == true) s2=bPBN2.getText(); if (s2.isEmpty()) s2="";
				if (bPBN3.getSelection() == true) s3=bPBN3.getText(); if (s3.isEmpty()) s3="";
				if (bPBN4.getSelection() == true) s4=bPBN4.getText(); if (s4.isEmpty()) s4="";
				if (bPBN5.getSelection() == true) s5=bPBN5.getText(); if (s5.isEmpty()) s5="";
				if (bPBN6.getSelection() == true) s6=bPBN6.getText(); if (s6.isEmpty()) s6="";
				if (bPBN7.getSelection() == true) s7=bPBN7.getText(); if (s7.isEmpty()) s7="";
				if (bPBN8.getSelection() == true) s8=bPBN8.getText(); if (s8.isEmpty()) s8="";
				if (bPBN9.getSelection() == true) s9=bPBN9.getText(); if (s9.isEmpty()) s9="";
				if (bPBN10.getSelection() == true) s10=bPBN10.getText(); if (s10.isEmpty()) s10="";
				if (bPBN11.getSelection() == true) s11=bPBN11.getText(); if (s11.isEmpty()) s11="";
				if (bPBN12.getSelection() == true) s12=bPBN12.getText(); if (s12.isEmpty()) s12="";
				if (bPBN13.getSelection() == true) s13=bPBN13.getText(); if (s13.isEmpty()) s13="";
				if (bPBN14.getSelection() == true) s14=bPBN14.getText(); if (s14.isEmpty()) s14="";
				if (bPBN15.getSelection() == true) s15=bPBN15.getText(); if (s15.isEmpty()) s15="";
				if (bPBN16.getSelection() == true) s16=bPBN16.getText(); if (s16.isEmpty()) s16="";
				if (bPBN17.getSelection() == true) s17=bPBN17.getText(); if (s17.isEmpty()) s17="";
				if (bPBN18.getSelection() == true) s18=bPBN18.getText(); if (s18.isEmpty()) s18="";
				if (bPBN19.getSelection() == true) s19=bPBN19.getText(); if (s19.isEmpty()) s19="";
				if (bPBN20.getSelection() == true) s20=bPBN20.getText(); if (s20.isEmpty()) s20="";
				if (bPBN21.getSelection() == true) s21=bPBN21.getText(); if (s21.isEmpty()) s21="";
				if (bPBN22.getSelection() == true) s22=bPBN22.getText(); if (s22.isEmpty()) s22="";
				if (bPBN23.getSelection() == true) s23=bPBN23.getText(); if (s23.isEmpty()) s23="";
				if (bPBN24.getSelection() == true) s24=bPBN24.getText(); if (s24.isEmpty()) s24="";
				
				all=s1+s2+s3+s4+s5+s6+s7+s8+s9+s10+s11+s12+s13+s14+s15+s16+s17+s18+s19+s20+s21+s22+s23+s24;
//				int index = all.lastIndexOf(" ");
//				all = all.substring(0, index);
//				ATSForms.tPbnFPL.setText(all);
				t.setText(all);

				if (all.length()>16) {
					DialogFactory.openWarningDialog(Shells.sh_mPBN, "Input","Please insert maximum 8 entries, i.e. a total of not more than 16 characters !!");
				} else {
					shell.close();
				}
			}
		});
		
		Cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bPBN1.getSelection() == true) bPBN1.setSelection(false); 
				if (bPBN2.getSelection() == true) bPBN2.setSelection(false);
				if (bPBN3.getSelection() == true) bPBN3.setSelection(false);
				if (bPBN4.getSelection() == true) bPBN4.setSelection(false);
				if (bPBN5.getSelection() == true) bPBN5.setSelection(false);
				if (bPBN6.getSelection() == true) bPBN6.setSelection(false);
				if (bPBN7.getSelection() == true) bPBN7.setSelection(false);
				if (bPBN8.getSelection() == true) bPBN8.setSelection(false);
				if (bPBN9.getSelection() == true) bPBN9.setSelection(false);
				if (bPBN10.getSelection() == true) bPBN10.setSelection(false);
				if (bPBN11.getSelection() == true) bPBN11.setSelection(false);
				if (bPBN12.getSelection() == true) bPBN12.setSelection(false);
				if (bPBN13.getSelection() == true) bPBN13.setSelection(false);
				if (bPBN14.getSelection() == true) bPBN14.setSelection(false);
				if (bPBN15.getSelection() == true) bPBN15.setSelection(false);
				if (bPBN16.getSelection() == true) bPBN16.setSelection(false);
				if (bPBN17.getSelection() == true) bPBN17.setSelection(false);
				if (bPBN18.getSelection() == true) bPBN18.setSelection(false);
				if (bPBN19.getSelection() == true) bPBN19.setSelection(false);
				if (bPBN20.getSelection() == true) bPBN20.setSelection(false);
				if (bPBN21.getSelection() == true) bPBN21.setSelection(false);
				if (bPBN22.getSelection() == true) bPBN22.setSelection(false);
				if (bPBN23.getSelection() == true) bPBN23.setSelection(false);
				if (bPBN24.getSelection() == true) bPBN24.setSelection(false);
				shell.close();
			}
		});
		bs.InputCancel(Input, Cancel);

		//if (Colors.NORMAL!=null) Colors.NORMAL.dispose();
	
	}
	
	void compPBN1() {
		final Group Group = new Group(shell, SWT.CENTER); sh.groupStyle(Group, 1, "");
		Label label = new Label(Group, SWT.NONE); sh.labelStyle1(label, "RNAV SPECIFICATIONS", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);

		Composite compPbn1 = new Composite(Group, SWT.NONE); sh.compositeStyle(compPbn1, 7, SWT.LEFT, SWT.CENTER);
		final Button bS1 = new Button(compPbn1, SWT.CHECK); button(bS1, "A1"); label = new Label(compPbn1, SWT.NONE); label.setText("\t"); 
		final Button bS2 = new Button(compPbn1, SWT.CHECK); button(bS2, "B1"); label = new Label(compPbn1, SWT.NONE); label.setText("\t");
		final Button bS8 = new Button(compPbn1, SWT.CHECK); button(bS8, "C1"); label = new Label(compPbn1, SWT.NONE); label.setText("\t");
		final Button bS12 = new Button(compPbn1, SWT.CHECK); button(bS12, "D1");
		//--
		label = new Label(compPbn1, SWT.NONE);  label = new Label(compPbn1, SWT.NONE);
		final Button bS3 = new Button(compPbn1, SWT.CHECK); button(bS3, "B2"); label = new Label(compPbn1, SWT.NONE);
		final Button bS9 = new Button(compPbn1, SWT.CHECK); button(bS9, "C2"); label = new Label(compPbn1, SWT.NONE);
		final Button bS13 = new Button(compPbn1, SWT.CHECK); button(bS13, "D2");
		//--
		label = new Label(compPbn1, SWT.NONE);  label = new Label(compPbn1, SWT.NONE);
		final Button bS4 = new Button(compPbn1, SWT.CHECK); button(bS4, "B3"); label = new Label(compPbn1, SWT.NONE);
		final Button bS10 = new Button(compPbn1, SWT.CHECK); button(bS10, "C3"); label = new Label(compPbn1, SWT.NONE);
		final Button bS14 = new Button(compPbn1, SWT.CHECK); button(bS14, "D3");
		//--
		label = new Label(compPbn1, SWT.NONE);  label = new Label(compPbn1, SWT.NONE);
		final Button bS5 = new Button(compPbn1, SWT.CHECK); button(bS5, "B4"); label = new Label(compPbn1, SWT.NONE);
		final Button bS11 = new Button(compPbn1, SWT.CHECK); button(bS11, "C4"); label = new Label(compPbn1, SWT.NONE);
		final Button bS15 = new Button(compPbn1, SWT.CHECK); button(bS15, "D4");
		//--
		label = new Label(compPbn1, SWT.NONE); label = new Label(compPbn1, SWT.NONE); 
		final Button bS6 = new Button(compPbn1, SWT.CHECK); button(bS6, "B5"); label = new Label(compPbn1, SWT.NONE);
		label = new Label(compPbn1, SWT.NONE);  label = new Label(compPbn1, SWT.NONE);
		label = new Label(compPbn1, SWT.NONE);
		//--
		label = new Label(compPbn1, SWT.NONE);  label = new Label(compPbn1, SWT.NONE);
		final Button bS7 = new Button(compPbn1, SWT.CHECK); button(bS7, "B6"); label = new Label(compPbn1, SWT.NONE);
		label = new Label(compPbn1, SWT.NONE); label = new Label(compPbn1, SWT.NONE);
		label = new Label(compPbn1, SWT.NONE); 
		//------------------------------------------------------------
		label = new Label(Group, SWT.NONE); sh.labelStyle1(label, "RNP SPECIFICATIONS", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
		
		Composite compPbn2 = new Composite(Group, SWT.NONE); sh.compositeStyle(compPbn2, 7, SWT.LEFT, SWT.CENTER);
		final Button bS16 = new Button(compPbn2, SWT.CHECK); button(bS16, "L1"); label = new Label(compPbn2, SWT.NONE); label.setText("\t");
		final Button bS17 = new Button(compPbn2, SWT.CHECK); button(bS17, "O1"); label = new Label(compPbn2, SWT.NONE); label.setText("\t");
		final Button bS21 = new Button(compPbn2, SWT.CHECK); button(bS21, "S1"); label = new Label(compPbn2, SWT.NONE); label.setText("\t");
		final Button bS23 = new Button(compPbn2, SWT.CHECK); button(bS23, "T1");
		//--
		label = new Label(compPbn2, SWT.NONE); label = new Label(compPbn2, SWT.NONE);
		final Button bS18 = new Button(compPbn2, SWT.CHECK); button(bS18, "O2"); label = new Label(compPbn2, SWT.NONE);
		final Button bS22 = new Button(compPbn2, SWT.CHECK); button(bS22, "S2"); label = new Label(compPbn2, SWT.NONE);
		final Button bS24 = new Button(compPbn2, SWT.CHECK); button(bS24, "T2");
		//--
		label = new Label(compPbn2, SWT.NONE); label = new Label(compPbn2, SWT.NONE); 
		final Button bS19 = new Button(compPbn2, SWT.CHECK); button(bS19, "O3"); label = new Label(compPbn2, SWT.NONE);
		label = new Label(compPbn2, SWT.NONE); label = new Label(compPbn2, SWT.NONE);
		label = new Label(compPbn2, SWT.NONE);
		//--
		label = new Label(compPbn2, SWT.NONE); label = new Label(compPbn2, SWT.NONE); 
		final Button bS20 = new Button(compPbn2, SWT.CHECK); button(bS20, "O4"); label = new Label(compPbn2, SWT.NONE);
		label = new Label(compPbn2, SWT.NONE); label = new Label(compPbn2, SWT.NONE);
		label = new Label(compPbn2, SWT.NONE);
		//------------------------------------------------------------
		String sub = t.getText();
//		String sub[] = ATSForms.tPbn.getText().split(" ");//ATSForms.tPbnFPL.getText().split(" ");
//		for (int i=0; i<sub.length; i++) {
			if (sub.contains("A1")) bS1.setSelection(true);
			if (sub.contains("B1")) bS2.setSelection(true);
			if (sub.contains("B2")) bS3.setSelection(true);
			if (sub.contains("B3")) bS4.setSelection(true);
			if (sub.contains("B4")) bS5.setSelection(true);
			if (sub.contains("B5")) bS6.setSelection(true);
			if (sub.contains("B6")) bS7.setSelection(true);
			if (sub.contains("C1")) bS8.setSelection(true);
			if (sub.contains("C2")) bS9.setSelection(true);
			if (sub.contains("C3")) bS10.setSelection(true);
			if (sub.contains("C4")) bS11.setSelection(true);
			if (sub.contains("D1")) bS12.setSelection(true);
			if (sub.contains("D2")) bS13.setSelection(true);
			if (sub.contains("D3")) bS14.setSelection(true);
			if (sub.contains("D4")) bS15.setSelection(true);
			if (sub.contains("L1")) bS16.setSelection(true);
			if (sub.contains("O1")) bS17.setSelection(true);
			if (sub.contains("O2")) bS18.setSelection(true);
			if (sub.contains("O3")) bS19.setSelection(true);
			if (sub.contains("O4")) bS20.setSelection(true);
			if (sub.contains("S1")) bS21.setSelection(true);
			if (sub.contains("S2")) bS22.setSelection(true);
			if (sub.contains("T1")) bS23.setSelection(true);
			if (sub.contains("T2")) bS24.setSelection(true);
//		} //end of for sub
		
		//ToolTipText
		bS1.setToolTipText("A1: RNAV 10 (RNP 10)");
		
		bS2.setToolTipText("B1: RNAV 5 all permitted sensors");
		bS3.setToolTipText("B2: RNAV 5 GNSS");
		bS4.setToolTipText("B3: RNAV 5 DME/DME");
		bS5.setToolTipText("B4: RNAV 5 VOR/DME");
		bS6.setToolTipText("B5: RNAV 5 INS or IRS");
		bS7.setToolTipText("B6: RNAV 5 LORANC");
		
		bS8.setToolTipText("C1: RNAV 2 all permitted sensors");
		bS9.setToolTipText("C2: RNAV 2 GNSS");
		bS10.setToolTipText("C3: RNAV 2 DME/DME");
		bS11.setToolTipText("C4: RNAV 2 DME/DME/IRU");
		
		bS12.setToolTipText("D1: RNAV 1 all permitted sensors");
		bS13.setToolTipText("D2: RNAV 1 GNSS");
		bS14.setToolTipText("D3: RNAV 1 DME/DME");
		bS15.setToolTipText("D4: RNAV 1 DME/DME/IRU");
		
		bS16.setToolTipText("L1: RNP 4");
		
		bS17.setToolTipText("O1: Basic RNP 1 all permitted sensors");
		bS18.setToolTipText("O2: Basic RNP 1 GNSS");
		bS19.setToolTipText("O3: Basic RNP 1 DME/DME");
		bS20.setToolTipText("O4: Basic RNP 1 DME/DME/IRU");
		
		bS21.setToolTipText("S1: RNP APCH");
		bS22.setToolTipText("S2: RNP APCH with BARO-VNAV");
		
		bS23.setToolTipText("T1: RNP AR APCH with AF (special authorization required)");
		bS24.setToolTipText("T2: RNP AR APCH without AF (special authorization required)");
		
		Composite compBtn = new Composite(Group, SWT.NONE); sh.compositeStyle(compBtn, 2, SWT.CENTER, SWT.CENTER);
		Button Input = new Button(compBtn, SWT.PUSH);
		Input.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String s1="",s2="",s3="",s4="",s5="",s6="",s7="",s8="",s9="",s10="",s11="",s12="",s13="",s14="",
				s15="",s16="",s17="",s18="",s19="",s20="",s21="",s22="",s23="",s24="",all="";
				
				if (bS1.getSelection() == true) s1=bS1.getText(); if (s1.isEmpty()) s1="";
				if (bS2.getSelection() == true) s2=bS2.getText(); if (s2.isEmpty()) s2="";
				if (bS3.getSelection() == true) s3=bS3.getText(); if (s3.isEmpty()) s3="";
				if (bS4.getSelection() == true) s4=bS4.getText(); if (s4.isEmpty()) s4="";
				if (bS5.getSelection() == true) s5=bS5.getText(); if (s5.isEmpty()) s5="";
				if (bS6.getSelection() == true) s6=bS6.getText(); if (s6.isEmpty()) s6="";
				if (bS7.getSelection() == true) s7=bS7.getText(); if (s7.isEmpty()) s7="";
				if (bS8.getSelection() == true) s8=bS8.getText(); if (s8.isEmpty()) s8="";
				if (bS9.getSelection() == true) s9=bS9.getText(); if (s9.isEmpty()) s9="";
				if (bS10.getSelection() == true) s10=bS10.getText(); if (s10.isEmpty()) s10="";
				if (bS11.getSelection() == true) s11=bS11.getText(); if (s11.isEmpty()) s11="";
				if (bS12.getSelection() == true) s12=bS12.getText(); if (s12.isEmpty()) s12="";
				if (bS13.getSelection() == true) s13=bS13.getText(); if (s13.isEmpty()) s13="";
				if (bS14.getSelection() == true) s14=bS14.getText(); if (s14.isEmpty()) s14="";
				if (bS15.getSelection() == true) s15=bS15.getText(); if (s15.isEmpty()) s15="";
				if (bS16.getSelection() == true) s16=bS16.getText(); if (s16.isEmpty()) s16="";
				if (bS17.getSelection() == true) s17=bS17.getText(); if (s17.isEmpty()) s17="";
				if (bS18.getSelection() == true) s18=bS18.getText(); if (s18.isEmpty()) s18="";
				if (bS19.getSelection() == true) s19=bS19.getText(); if (s19.isEmpty()) s19="";
				if (bS20.getSelection() == true) s20=bS20.getText(); if (s20.isEmpty()) s20="";
				if (bS21.getSelection() == true) s21=bS21.getText(); if (s21.isEmpty()) s21="";
				if (bS22.getSelection() == true) s22=bS22.getText(); if (s22.isEmpty()) s22="";
				if (bS23.getSelection() == true) s23=bS23.getText(); if (s23.isEmpty()) s23="";
				if (bS24.getSelection() == true) s24=bS24.getText(); if (s24.isEmpty()) s24="";
				
				all=s1+s2+s3+s4+s5+s6+s7+s8+s9+s10+s11+s12+s13+s14+s15+s16+s17+s18+s19+s20+s21+s22+s23+s24;
//				int index = all.lastIndexOf(" ");
//				all = all.substring(0, index);
//				ATSForms.tPbnFPL.setText(all);
				t.setText(all);

				if (all.length()>16) {
					DialogFactory.openWarningDialog(Shells.sh_mPBN, "Input","Please insert maximum 8 entries, i.e. a total of not more than 16 characters !!");
				} else {
					shell.close();
				}
			}
		});
		
		Button Cancel = new Button(compBtn, SWT.PUSH);
		Cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bS1.getSelection() == true) bS1.setSelection(false); 
				if (bS2.getSelection() == true) bS2.setSelection(false);
				if (bS3.getSelection() == true) bS3.setSelection(false);
				if (bS4.getSelection() == true) bS4.setSelection(false);
				if (bS5.getSelection() == true) bS5.setSelection(false);
				if (bS6.getSelection() == true) bS6.setSelection(false);
				if (bS7.getSelection() == true) bS7.setSelection(false);
				if (bS8.getSelection() == true) bS8.setSelection(false);
				if (bS9.getSelection() == true) bS9.setSelection(false);
				if (bS10.getSelection() == true) bS10.setSelection(false);
				if (bS11.getSelection() == true) bS11.setSelection(false);
				if (bS12.getSelection() == true) bS12.setSelection(false);
				if (bS13.getSelection() == true) bS13.setSelection(false);
				if (bS14.getSelection() == true) bS14.setSelection(false);
				if (bS15.getSelection() == true) bS15.setSelection(false);
				if (bS16.getSelection() == true) bS16.setSelection(false);
				if (bS17.getSelection() == true) bS17.setSelection(false);
				if (bS18.getSelection() == true) bS18.setSelection(false);
				if (bS19.getSelection() == true) bS19.setSelection(false);
				if (bS20.getSelection() == true) bS20.setSelection(false);
				if (bS21.getSelection() == true) bS21.setSelection(false);
				if (bS22.getSelection() == true) bS22.setSelection(false);
				if (bS23.getSelection() == true) bS23.setSelection(false);
				if (bS24.getSelection() == true) bS24.setSelection(false);
				shell.close();
			}
		});
		bs.InputCancel(Input, Cancel);

		//if (Colors.NORMAL!=null) Colors.NORMAL.dispose();
	}
	
	void compSTS() {
		final Group Group = new Group(shell, SWT.CENTER); sh.groupStyle(Group, 1, "");
		Composite compA = new Composite(Group, SWT.NONE); sh.compositeStyle(compA, 3, SWT.LEFT, SWT.LEFT);
		bSTS1 = new Button(compA, SWT.CHECK); buttonSts(bSTS1, "ALTRV");
		bSTS2 = new Button(compA, SWT.CHECK); buttonSts(bSTS2, "ATFMX");
		bSTS3 = new Button(compA, SWT.CHECK); buttonSts(bSTS3, "FFR");
		bSTS4 = new Button(compA, SWT.CHECK); buttonSts(bSTS4, "FLTCK");
		bSTS5 = new Button(compA, SWT.CHECK); buttonSts(bSTS5, "HAZMAT");
		bSTS6 = new Button(compA, SWT.CHECK); buttonSts(bSTS6, "HEAD");
		bSTS7 = new Button(compA, SWT.CHECK); buttonSts(bSTS7, "HOSP");
		bSTS8 = new Button(compA, SWT.CHECK); buttonSts(bSTS8, "HUM");
		bSTS9 = new Button(compA, SWT.CHECK); buttonSts(bSTS9, "MARSA");
		bSTS10 = new Button(compA, SWT.CHECK); buttonSts(bSTS10, "MEDEVAC");
		bSTS11 = new Button(compA, SWT.CHECK); buttonSts(bSTS11, "NONRVSM");
		bSTS12 = new Button(compA, SWT.CHECK); buttonSts(bSTS12, "SAR");
		bSTS13 = new Button(compA, SWT.CHECK); buttonSts(bSTS13, "STATE");
		
		Composite typeB = new Composite(Group, SWT.NONE); sh.compositeStyle(typeB, 1, SWT.CENTER, SWT.CENTER);
		Label label = new Label(typeB, SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelStyle(label, "", 360, SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
		
		Composite typeE = new Composite(Group, SWT.NONE); sh.compositeStyle(typeE, 3, SWT.CENTER, SWT.CENTER);
		Button bAdd = new Button(typeE, SWT.PUSH);
		Button bClear = new Button(typeE, SWT.PUSH);
		Button bClose = new Button(typeE, SWT.PUSH);
		bs.ACC(bAdd, bClear,bClose);
		
		bSTS1.setToolTipText("ALTRV: for a flight operated in accordance with an altitude reservation");
		bSTS2.setToolTipText("ATFMX: for a flight approved for exemption from ATFM measures by the appropriate ATS authority");
		bSTS3.setToolTipText("FFR: fire-fighting");
		bSTS4.setToolTipText("FLTCK: flight check for calibration of navaids");
		bSTS5.setToolTipText("HAZMAT: for a flight carrying hazardous material");
		bSTS6.setToolTipText("HEAD: a flight with Head of State status");
		bSTS7.setToolTipText("HOSP: for medical flight declared by medical authorities");
		bSTS8.setToolTipText("HUM: for a flight operating on a humanitarian mission");
		bSTS9.setToolTipText("MARSA: for a flight for which a military entity assumes responsibility for separation of military aircraft");
		bSTS10.setToolTipText("MEDEVAC: for a life critical medical emergency evacuation");
		bSTS11.setToolTipText("NONRVSM: for non-RVSM capable flight intending to operate in RVSM airspace");
		bSTS12.setToolTipText("SAR: for a flight engaged in a search and rescue mission");
		bSTS13.setToolTipText("STATE: for a flight engaged in military, customs or police services.");

		String sub[] = t.getText().split(" ");
		for (int i=0; i<sub.length; i++) {
			if (sub[i].compareTo("ALTRV")==0) bSTS1.setSelection(true);
			if (sub[i].compareTo("ATFMX")==0) bSTS2.setSelection(true);
			if (sub[i].compareTo("FFR")==0) bSTS3.setSelection(true);
			if (sub[i].compareTo("FLTCK")==0) bSTS4.setSelection(true);
			if (sub[i].compareTo("HAZMAT")==0) bSTS5.setSelection(true);
			if (sub[i].compareTo("HEAD")==0) bSTS6.setSelection(true);
			if (sub[i].compareTo("HOSP")==0) bSTS7.setSelection(true);
			if (sub[i].compareTo("HUM")==0) bSTS8.setSelection(true);
			if (sub[i].compareTo("MARSA")==0) bSTS9.setSelection(true);
			if (sub[i].compareTo("MEDEVAC")==0) bSTS10.setSelection(true);
			if (sub[i].compareTo("NONRVSM")==0) bSTS11.setSelection(true);
			if (sub[i].compareTo("SAR")==0) bSTS12.setSelection(true);
			if (sub[i].compareTo("STATE")==0) bSTS13.setSelection(true);
		} //end of for sub[i]
		
//		bCheckAll.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				checkSTS(true);
//			}
//		});
//		
//		bUncheckAll.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				checkSTS(false);
//			}
//		});
		
		//addListeners
		bAdd.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String s1="",s2="",s3="",s4="",s5="",s6="",s7="",s8="",s9="",s10="",s11="",s12="",s13="",all="";
				if (bSTS1.getSelection() == true) s1=bSTS1.getText(); if (!s1.isEmpty()) s1=s1.concat(" "); else s1="";
				if (bSTS2.getSelection() == true) s2=bSTS2.getText(); if (!s2.isEmpty()) s2=s2.concat(" "); else s2="";
				if (bSTS3.getSelection() == true) s3=bSTS3.getText(); if (!s3.isEmpty()) s3=s3.concat(" "); else s3="";
				if (bSTS4.getSelection() == true) s4=bSTS4.getText(); if (!s4.isEmpty()) s4=s4.concat(" "); else s4="";
				if (bSTS5.getSelection() == true) s5=bSTS5.getText(); if (!s5.isEmpty()) s5=s5.concat(" "); else s5="";
				if (bSTS6.getSelection() == true) s6=bSTS6.getText(); if (!s6.isEmpty()) s6=s6.concat(" "); else s6="";
				if (bSTS7.getSelection() == true) s7=bSTS7.getText(); if (!s7.isEmpty()) s7=s7.concat(" "); else s7="";
				if (bSTS8.getSelection() == true) s8=bSTS8.getText(); if (!s8.isEmpty()) s8=s8.concat(" "); else s8="";
				if (bSTS9.getSelection() == true) s9=bSTS9.getText(); if (!s9.isEmpty()) s9=s9.concat(" "); else s9="";
				if (bSTS10.getSelection() == true) s10=bSTS10.getText(); if (!s10.isEmpty()) s10=s10.concat(" "); else s10="";
				if (bSTS11.getSelection() == true) s11=bSTS11.getText(); if (!s11.isEmpty()) s11=s11.concat(" "); else s11="";
				if (bSTS12.getSelection() == true) s12=bSTS12.getText(); if (!s12.isEmpty()) s12=s12.concat(" "); else s12="";
				if (bSTS13.getSelection() == true) s13=bSTS13.getText(); if (!s13.isEmpty()) s13=s13.concat(" "); else s13="";
				
				all=s1+s2+s3+s4+s5+s6+s7+s8+s9+s10+s11+s12+s13;
				if (!all.isEmpty()) {
					int index = all.lastIndexOf(" ");
					all = all.substring(0, index);
				}
				t.setText(all);
				shell.close();
			}
		});
		
		bClear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bSTS1.getSelection() == true) bSTS1.setSelection(false); 
				if (bSTS2.getSelection() == true) bSTS2.setSelection(false);
				if (bSTS3.getSelection() == true) bSTS3.setSelection(false);
				if (bSTS4.getSelection() == true) bSTS4.setSelection(false);
				if (bSTS5.getSelection() == true) bSTS5.setSelection(false);
				if (bSTS6.getSelection() == true) bSTS6.setSelection(false);
				if (bSTS7.getSelection() == true) bSTS7.setSelection(false);
				if (bSTS8.getSelection() == true) bSTS8.setSelection(false);
				if (bSTS9.getSelection() == true) bSTS9.setSelection(false);
				if (bSTS10.getSelection() == true) bSTS10.setSelection(false);
				if (bSTS11.getSelection() == true) bSTS11.setSelection(false);
				if (bSTS12.getSelection() == true) bSTS12.setSelection(false);
				if (bSTS13.getSelection() == true) bSTS13.setSelection(false);
				t.setText("");
			}
		});

		bClose.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
	}
	

	void checkPBN(boolean b) {
		bPBN1.setSelection(b);
		bPBN2.setSelection(b);
		bPBN3.setSelection(b);
		bPBN4.setSelection(b);
		bPBN5.setSelection(b);
		bPBN6.setSelection(b);
		bPBN7.setSelection(b);
		bPBN8.setSelection(b);
		bPBN9.setSelection(b);
		bPBN10.setSelection(b);
		bPBN11.setSelection(b);
		bPBN12.setSelection(b);
		bPBN13.setSelection(b);
		bPBN14.setSelection(b);
		bPBN15.setSelection(b);
		bPBN16.setSelection(b);
		bPBN17.setSelection(b);
		bPBN18.setSelection(b);
		bPBN19.setSelection(b);
		bPBN20.setSelection(b);
		bPBN21.setSelection(b);
		bPBN22.setSelection(b);
		bPBN23.setSelection(b);
		bPBN24.setSelection(b);
	}

	void checkSTS(boolean b) {
		bSTS1.setSelection(b);
		bSTS2.setSelection(b);
		bSTS3.setSelection(b);
		bSTS4.setSelection(b);
		bSTS5.setSelection(b);
		bSTS6.setSelection(b);
		bSTS7.setSelection(b);
		bSTS8.setSelection(b);
		bSTS9.setSelection(b);
		bSTS10.setSelection(b);
		bSTS11.setSelection(b);
		bSTS12.setSelection(b);
		bSTS13.setSelection(b);
	}
	
	void compDelTbl() {
		final Group group = new Group(shell, SWT.NONE); sh.groupStyle(group, 1, "");
		Label label = new Label(group, SWT.CENTER); sh.labelStyle1(label, "Please select one or more table(s)\nfrom the list below, then click Delete", SWT.CENTER, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Composite comp = new Composite(group, SWT.NONE); sh.compositeStyle(comp, 1, SWT.LEFT, SWT.CENTER); 
		tDelTbl = new Text(comp, SWT.BORDER); sh.textStyle(tDelTbl, 300, 100, SWT.LEFT, SWT.CENTER, "", "", false); 
	    tDelTbl.setVisible(false);
	    final Table table = new Table (comp, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL); sh.tableStyle(table, false, false, 100, 400); 

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try { conn = jdbc.getDBConnection(); } 
		catch (Exception e) { e.printStackTrace(); }
		
	    try {
			int jumlah=0; //inisialisasi
		    int rowNo = 0;
			String lihatsql = "SHOW TABLES FROM "+jdbc.getDbName()+" LIKE 'air_message_______'";
			
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery(lihatsql);
			rs.last();
			jumlah += rs.getRow();
			System.out.println("Jumlah data=" + jumlah);
			rs.beforeFirst();
			while (rs.next()) {
				rowNo++;
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(rs.getString(1));
			}

			rs.close();
			stmt.close();
			conn.close();
	    } catch (SQLException e) {
			e.printStackTrace();
		}
		
	    table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				String all="",beda="",koma;
				TableItem[] selection = table.getSelection();
				for (int i=0; i<selection.length; i++) {
					koma = selection[i].getText(0);
				
					if (!koma.equals(beda)) {
						all+=koma+",";
					}
					beda=koma;
				}
				// input pilihan ke text
				if (all.endsWith(",")) {
					int index = all.lastIndexOf(",");
					all = all.substring(0, index);
				}
				tDelTbl.setText(all);
			}
		});
	    
	    Composite compBtn = new Composite(group, SWT.NONE); sh.compositeStyle(compBtn, 2, SWT.CENTER, SWT.CENTER); 
	    Button bDelete = new Button(compBtn,SWT.PUSH);
	    bDelete.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//memilih baris yang akan di hapus -update 20101119
				TableItem[] selection;
				selection = table.getSelection(); 
				if (selection.length == 0) { DialogFactory.openInfoAtLeast(Shells.sh_mDeltbl,DialogFactory.delete); } 
				else {
					String tblName="";
					
					DialogFactory.openYesNoDelete(Shells.sh_mDeltbl);
					boolean tentu = DialogFactory.getPenentuan(); 
					if (tentu == true) {
						for (int i=0; i<selection.length; i++) { //banyak baris yang akan dihapus
							tblName = selection[i].getText(0);
							String dele = "DROP TABLE "+tblName;
							jdbc.delete(dele);
						}
						
						//refreshing table
//						if (getJumlah() > 1) {
							table.removeAll();
							RefreshTable.refreshDelTbl();	
//						} else if (getJumlah() == 1) {
//							shell.close();
//						}
					} //end of if
					
					
				} //end of else
			}
		});
	    
		Button bClose = new Button(compBtn,SWT.PUSH); 
		bClose.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});

		sh.buttonStyle(bDelete,"&Delete","Delete one or more selected table(s) from the database",bs.widthBtn,Colors.DarkGrey,SWT.CENTER,SWT.CENTER,Images.img_delete);
		sh.buttonStyle(bClose,"&Cancel","Click Cancel to cancel deleting table(s)",bs.widthBtn,Colors.DarkGrey,SWT.CENTER,SWT.CENTER,Images.img_clear16);
		
//	    sh.buttonStyle(bDelete, "&Delete", "Delete one or more selected table(s) from the database", bs.widthBtn, bs.colorBtn, Images.img_delete);
//	    sh.buttonStyle(bClose, "&Cancel", "Click Cancel to cancel deleting table(s)", bs.widthBtn, bs.colorBtn, Images.img_clear16);
	    
		//if (Colors.NORMAL!=null) Colors.NORMAL.dispose();
		//if (bs.colorBtn!=null) bs.colorBtn.dispose();
	}
	
	
//	void addListener() {
//		//id : ROUTE
//		if (ID.compareToIgnoreCase("route")==0) {
//			Save.addSelectionListener(new SelectionAdapter() {
//				public void widgetSelected(SelectionEvent e) {	
//					if (tDepAd_Route.getText().isEmpty()) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "The value in field DEP AD is required."); tDepAd_Route.setFocus(); }
//					else if (!tDepAd_Route.getText().isEmpty() && tDepAd_Route.getText().length()<4) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 4 LETTERS for field DEP AD !!"); tDepAd_Route.setFocus(); }
//					else if (tDestAd_Route.getText().isEmpty()) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "The value in field DEST AD is required."); tDestAd_Route.setFocus(); }
//					else if (!tDestAd_Route.getText().isEmpty() && tDestAd_Route.getText().length()<4) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 4 LETTERS for field DEST AD !!"); tDestAd_Route.setFocus(); }
//					else if (tRoute_Route.getText().isEmpty()) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "The value in field ROUTE is required."); tRoute_Route.setFocus(); }
//					else if ((!tDest1_Route.getText().equals("")) && (tDest1_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest1_Route.setFocus(); }
//					else if ((!tDest2_Route.getText().equals("")) && (tDest2_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest2_Route.setFocus(); }
//					else if ((!tDest3_Route.getText().equals("")) && (tDest3_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest3_Route.setFocus(); }
//					else if ((!tDest4_Route.getText().equals("")) && (tDest4_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest4_Route.setFocus(); }
//					else if ((!tDest5_Route.getText().equals("")) && (tDest5_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest5_Route.setFocus(); }
//					else if ((!tDest6_Route.getText().equals("")) && (tDest6_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest6_Route.setFocus(); }
//					else if ((!tDest7_Route.getText().equals("")) && (tDest7_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest7_Route.setFocus(); }
//					
//					else if ((!tDest8_Route.getText().equals("")) && (tDest8_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest8_Route.setFocus(); }
//					else if ((!tDest9_Route.getText().equals("")) && (tDest9_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest9_Route.setFocus(); }
//					else if ((!tDest10_Route.getText().equals("")) && (tDest10_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest10_Route.setFocus(); }
//					else if ((!tDest11_Route.getText().equals("")) && (tDest11_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest11_Route.setFocus(); }
//					else if ((!tDest12_Route.getText().equals("")) && (tDest12_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest12_Route.setFocus(); }
//					else if ((!tDest13_Route.getText().equals("")) && (tDest13_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest13_Route.setFocus(); }
//					else if ((!tDest14_Route.getText().equals("")) && (tDest14_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest14_Route.setFocus(); }
//					
//					else if ((!tDest15_Route.getText().equals("")) && (tDest15_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest15_Route.setFocus(); }
//					else if ((!tDest16_Route.getText().equals("")) && (tDest16_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest16_Route.setFocus(); }
//					else if ((!tDest17_Route.getText().equals("")) && (tDest17_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest17_Route.setFocus(); }
//					else if ((!tDest18_Route.getText().equals("")) && (tDest18_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest18_Route.setFocus(); }
//					else if ((!tDest19_Route.getText().equals("")) && (tDest19_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest19_Route.setFocus(); }
//					else if ((!tDest20_Route.getText().equals("")) && (tDest20_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest20_Route.setFocus(); }
//					else if ((!tDest21_Route.getText().equals("")) && (tDest21_Route.getText().length() < 8)) { DialogFactory.openInfoDialog(Shells.shell_nroute, "Save Message", "Please insert 8 characters for ADDRESS's field !!"); tDest21_Route.setFocus(); }
//					else {
//						String insert = "INSERT INTO route (type13a,type16a,type15c,destination1,destination2,destination3,destination4,destination5,destination6,destination7,destination8,destination9,destination10,destination11,destination12,destination13,destination14,destination15,destination16,destination17,destination18,destination19,destination20,destination21) VALUES ('"+tDepAd_Route.getText()+"','"+tDestAd_Route.getText()+"','"+tRoute_Route.getText()+"','"+tDest1_Route.getText()+"','"+tDest2_Route.getText()+"','"+tDest3_Route.getText()+"','"+tDest4_Route.getText()+"','"+tDest5_Route.getText()+"','"+tDest6_Route.getText()+"','"+tDest7_Route.getText()+"','"+tDest8_Route.getText()+"','"+tDest9_Route.getText()+"','"+tDest10_Route.getText()+"','"+tDest11_Route.getText()+"','"+tDest12_Route.getText()+"','"+tDest13_Route.getText()+"','"+tDest14_Route.getText()+"','"+tDest15_Route.getText()+"','"+tDest16_Route.getText()+"','"+tDest17_Route.getText()+"','"+tDest18_Route.getText()+"','"+tDest19_Route.getText()+"','"+tDest20_Route.getText()+"','"+tDest21_Route.getText()+"')";
//						jdbc.connect(insert);
//						shell.close();
//					}
//				}
//			});
//			
//			Clear.addSelectionListener(new SelectionAdapter() {
//				public void widgetSelected(SelectionEvent e) {	
//					discardRoute();
//				}
//			});
//		
//			//id : TYPE9B
//		} else if (ID.compareToIgnoreCase("type9b")==0) {
//			sbWTC = lWTC_Type9b.getVerticalBar();
//			lsbiWTC=0;sbiWTC=0;initWTC=1;
//			sbWTC.addSelectionListener(new SelectionAdapter() {
//				public void widgetSelected(SelectionEvent e) {
//					if (initWTC==1) {
//						if (lsbiWTC>sbWTC.getSelection()) {if (sbiWTC>0) sbiWTC--;lWTC_Type9b.select(sbiWTC);/*System.out.println("up");*/}
//						else if (lsbiWTC<sbWTC.getSelection()) {if (sbiWTC<lWTC_Type9b.getItemCount()-1) sbiWTC++;lWTC_Type9b.select(sbiWTC);/*System.out.println("down");*/}
//						lWTC_Type9b.showSelection();
//					}
//					else {lsbiWTC=1+(lWTC_Type9b.getSelectionIndex()*26);sbiWTC=lWTC_Type9b.getSelectionIndex();}
//					lsbiWTC=sbWTC.getSelection();
//					initWTC=1;
//				}
//			});
//			
//			Save.addSelectionListener(new SelectionAdapter() {
//				public void widgetSelected(SelectionEvent e) {
//					String sWTC="";
//					if (lWTC_Type9b.getSelectionCount()>0) sWTC=lWTC_Type9b.getSelection()[0];
//					if (tTOA_Type9b.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.shell_ntype9b, "Save Message", "The value in field Type of Aircraft is required."); tTOA_Type9b.setFocus(); }
//					else if (!tTOA_Type9b.getText().isEmpty() && tTOA_Type9b.getText().length()<2) { DialogFactory.openInfoDialog(Shells.shell_ntype9b, "Save Message", "Please insert 2 - 4 CHARACTERS for field Type of Aircraft !!"); tTOA_Type9b.setFocus(); }
//					else if (sWTC.isEmpty()) { DialogFactory.openWarningDialog(Shells.shell_ntype9b, "Save Message", "The value in field Wake Turbulence Category is required."); lWTC_Type9b.setFocus(); }
//					else {
//						String insert = "INSERT INTO aircraft_wtc (type9b,type9c) VALUES ('"+tTOA_Type9b.getText()+"','"+sWTC+"')";
//						jdbc.connect(insert);
//						shell.close();
//					}
//				}
//			});
//			
//			Clear.addSelectionListener(new SelectionAdapter() {
//				public void widgetSelected(SelectionEvent e) {	
//					discardType9b();
//				}
//			});
//			
//			//id : REG/
//		} else if (ID.compareToIgnoreCase("reg")==0) {
//			b10a_REG.addSelectionListener(new SelectionAdapter() {
//				public void widgetSelected(SelectionEvent e) {
////					if (shells.shell10a.isDisposed()) {
////						shells.shell10a = new Shell();
////					}
////					if (!shells.shell10a.isVisible()) {
////						FormItem10aValue form = new FormItem10aValue();
////						form.run(shells.shell10a, t10a_REG, "nreg");
////					} else {
////						shells.shell10a.close();
////						shells.shell10a = new Shell();
////						FormItem10aValue form = new FormItem10aValue();
////						form.run(shells.shell10a, t10a_REG, "nreg");
////					}
//				}
//			});
//			
//			b10b_REG.addSelectionListener(new SelectionAdapter() {
//				public void widgetSelected(SelectionEvent e) {
////					if (shells.shell10b.isDisposed()) {
////						shells.shell10b = new Shell();
////					}
////					if (!shells.shell10b.isVisible()) {
////						FormItem10bValue form = new FormItem10bValue();
////						form.run(shells.shell10b, t10b_REG);
////					} else {
////						shells.shell10b.close();
////						shells.shell10b = new Shell();
////						FormItem10bValue form = new FormItem10bValue();
////						form.run(shells.shell10b, t10b_REG);
////					}
//				}
//			});
//			
//			bPBN_REG.addSelectionListener(new SelectionAdapter() {
//				public void widgetSelected(SelectionEvent e) {
////					if (shells.shellPBN.isDisposed()) {
////						shells.shellPBN = new Shell();
////					}
////					if (!shells.shellPBN.isVisible()) {
////						FormPBNValue form = new FormPBNValue();
////						form.run(shells.shellPBN, tPbn_REG, "nreg");
////					} else {
////						shells.shellPBN.close();
////						shells.shellPBN = new Shell();
////						FormPBNValue form = new FormPBNValue();
////						form.run(shells.shellPBN, tPbn_REG, "nreg");
////					}
//				}
//			});
//			
//			Save.addSelectionListener(new SelectionAdapter() {
//				public void widgetSelected(SelectionEvent e) {	
////					ss.krit1="";ss.krit2="";ss.krit3="";ss.krit4="";ss.krit5="";
////					ss.t10b=""; ss.sdbguv=""; ss.sprotek10a=""; ss.sprotekpbn="";
////					ss.t10b = t10b_REG.getText(); 
////					ss.t10a = t10a_REG.getText();
////					ss.tpbn = tPbn_REG.getText();
////					
////					ss.validasi10a(ss.t10a);
////					ss.validasi18_10(ss.t10b, ss.t10a);
////					ss.proteksi18();
////					ss.validasipbn(ss.tpbn);
////			    	ss.proteksi18REG(ss.tpbn);
////			    	
////					//9
////					if (tReg_REG.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message", "The value in field REG/ is required."); tReg_REG.setFocus(); }
////					else if (t9b_REG.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message", "The value in field TYPE OF AIRCRAFT is required."); t9b_REG.setFocus(); }
////					else if (!t9b_REG.getText().isEmpty() && t9b_REG.getText().length()<2) { DialogFactory.openInfoDialog(Shells.shell_nreg, "Save Message", "Please insert 2 - 4 CHARACTERS for field TYPE OF AIRCRAFT !!"); t9b_REG.setFocus(); }
////					//OPR
////					else if (tOpr_REG.getText().isEmpty()) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message", "The value in field OPR/ is required."); tOpr_REG.setFocus(); }
////					//10
////			        else if (ss.t10a.equals("")) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","The value in field EQUIPMENT AND CAPABILITIES is required."); t10a_REG.setFocus(); }
////					else if (ss.t10b.equals("")) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","The value in field EQUIPMENT AND CAPABILITIES is required."); t10b_REG.setFocus(); }
////					else if ( (ss.t10b.contains("B1") || ss.t10b.contains("U1") || ss.t10b.contains("V1")) && (ss.t10b.contains("B2") || ss.t10b.contains("U2") || ss.t10b.contains("V2"))) { DialogFactory.openInfoDialog(Shells.shell_nreg, "Save Message","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
////					else if (ss.t10a.contains("N") && ss.t10a.contains("S")) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","Invalid EQUIPMENT AND CAPABILITIES value.\n\n'N' is not allowed, if 'S' has inserted into field EQUIPMENT AND CAPABILITIES and vice versa.\n\nSee the instruction below the EQUIPMENT AND CAPABILITIES text !"); t10a_REG.setFocus(); }
////					else if (ss.t10b.contains("N") && (ss.t10b.contains("A") || ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X") || ss.t10b.contains("B1") || ss.t10b.contains("B2") || ss.t10b.contains("U1") || ss.t10b.contains("U2") || ss.t10b.contains("V1") || ss.t10b.contains("V2") || ss.t10b.contains("D1") || ss.t10b.contains("G1"))) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","Invalid EQUIPMENT AND CAPABILITIES value.\n\nSSR Modes A and C and SSR Mode S are not allowed,\nif 'N' has inserted into field EQUIPMENT AND CAPABILITIES and vice versa.\n\nSee the instruction below the EQUIPMENT AND CAPABILITIES text !"); t10b_REG.setFocus(); }
////					else if (!ss.t10b.equals("") && (ss.sdbguv.contains("tdksesuai"))) { DialogFactory.openInfoDialog(Shells.shell_nreg, "Save Message", em.infoInvalid10b+"\nInsert B1 or B2 or U1 or U2 or V1 or V2 or D1 or G1"); t10b_REG.setFocus(); }		
////					else if (!ss.t10a.equals("") && (ss.sprotek10a.contains("ngaco"))) { DialogFactory.openInfoDialog(Shells.shell_nreg, "Save Message", em.infoInvalid10aFix); t10a_REG.setFocus(); }		
////					//NEW
////					else if (ss.t10b.contains("N") && ss.t10b.length()>1) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
////					else if (ss.t10b.contains("A") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
////					else if (ss.t10b.contains("C") && (ss.t10b.contains("A") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
////					else if (ss.t10b.contains("E") && (ss.t10b.contains("C") || ss.t10b.contains("A") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
////					else if (ss.t10b.contains("H") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("A") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
////					else if (ss.t10b.contains("I") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("A") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
////					else if (ss.t10b.contains("L") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("A") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
////					else if (ss.t10b.contains("P") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("A") || ss.t10b.contains("S") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
////					else if (ss.t10b.contains("S") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("A") || ss.t10b.contains("X"))) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
////					else if (ss.t10b.contains("X") && (ss.t10b.contains("C") || ss.t10b.contains("E") || ss.t10b.contains("H") || ss.t10b.contains("I") || ss.t10b.contains("L") || ss.t10b.contains("P") || ss.t10b.contains("S") || ss.t10b.contains("A"))) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","The value in field EQUIPMENT AND CAPABILITIES is not match !!"); t10b_REG.setFocus(); }
////			    	//PBN
////			        else if (!ss.tpbn.equals("") && (ss.tpbn.length() > 16 || ss.tpbn.contains("\n"))) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","Invalid PBN/ indicator value.\nSee the instruction below the PBN/ indicator text !"); tPbn_REG.setFocus(); }
////			        else if (!ss.tpbn.equals("") && ss.sprotekpbn.contains("ngaco")) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message", em.infoInvalidPbnFix); tPbn_REG.setFocus(); }
////					else if (ss.t10a.contains("R") && ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","Field OTHER INFORMATION with indicator PBN/ should be filled, if the letter 'R' is used in field EQUIPMENT AND CAPABILITIES."); tPbn_REG.setFocus(); }
////					else if (!ss.t10a.contains("R") && !ss.tpbn.equals(""))  { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","Field EQUIPMENT AND CAPABILITIES should be contained the letter 'R', if field OTHER INFORMATION is filled."); t10a_REG.setFocus(); }
////			    	//G
////					else if (!ss.t10a.contains("G") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","If B1, B2, C1, C2, D1, D2, O1 or O2 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'G' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
////					else if (!ss.t10a.contains("G") && !ss.krit2.equals("") && (ss.krit2.contains("B2") || ss.krit2.contains("C2") || ss.krit2.contains("D2") || ss.krit2.contains("O2"))) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","If B1, B2, C1, C2, D1, D2, O1 or O2 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'G' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
////					//D
////					else if (!ss.t10a.contains("D") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1"))) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","If B1, B3, C1, C3, D1, D3, O1 or O3 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
////					else if (!ss.t10a.contains("D") && !ss.krit3.equals("") && (ss.krit3.contains("B3") || ss.krit3.contains("C3") || ss.krit3.contains("D3") || ss.krit3.contains("O3"))) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","If B1, B3, C1, C3, D1, D3, O1 or O3 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
////			    	//I
////					else if (!ss.t10a.contains("I") && !ss.krit5.equals("")) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","If B1, B5 or C1 are filled in field OTHER INFORMATION with PBN/ indicator, then an 'I' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
////					else if (!ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("B1") || ss.krit1.contains("C1"))) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","If B1, B5 or C1 are filled in field OTHER INFORMATION with PBN/ indicator, then an 'I' must be included in field EQUIPMENT AND CAPABILITIES !"); t10a_REG.setFocus(); }
////					//DI
////					else if (!ss.t10a.contains("D") && !ss.t10a.contains("I") && !ss.krit1.equals("") && (ss.krit1.contains("C1") || ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10a_REG.setFocus(); }
////					else if (!ss.t10a.contains("D") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10a_REG.setFocus(); }
////					else if (!ss.t10a.contains("I") && !ss.krit4.equals("") && (ss.krit4.contains("C4") || ss.krit4.contains("D4") || ss.krit4.contains("O4")))  { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10a_REG.setFocus(); }
////					else if (!ss.t10a.contains("I") && (ss.krit1.contains("D1") || ss.krit1.contains("O1")))  { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message","If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)"); t10a_REG.setFocus(); }
////			    	//OD / SD
////					else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit1.contains("B1")) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10a_REG.setFocus(); }
////					else if (!ss.t10a.contains("S") && !ss.t10a.contains("O") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10a_REG.setFocus(); }
////					else if (!ss.t10a.contains("D") && ss.krit4.contains("B4")) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10a_REG.setFocus(); }
////					else if (!ss.t10a.contains("D") && ss.krit1.contains("B4")) { DialogFactory.openWarningDialog(Shells.shell_nreg, "Save Message", "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)"); t10a_REG.setFocus(); }
////			    	
////					else {
////						String insert = "INSERT INTO aircraft_reg (type9b,type18,type10a,type10b,type18Opr,type18Pbn) VALUES ('"+t9b_REG.getText()+"','"+tReg_REG.getText()+"','"+t10a_REG.getText()+"','"+ss.t10b+"','"+tOpr_REG.getText()+"','"+ss.tpbn+"')";
////						jdbc.connect(insert);
////						shell.close();
////					}
//				}
//			});
//			
//			Clear.addSelectionListener(new SelectionAdapter() {
//				public void widgetSelected(SelectionEvent e) {	
//					discardReg();
//				}
//			});
//			
//			//id : LOCIND
//		} else if (ID.compareToIgnoreCase("locind")==0) {
//			Save.addSelectionListener(new SelectionAdapter() {
//				public void widgetSelected(SelectionEvent e) {
//					if (tIndicator_Locind.getText().equals("")) { DialogFactory.openWarningDialog(Shells.shell_nlocind, "Save Message","The value in field INDICATOR is required."); tIndicator_Locind.setFocus(); }
//					else if (!tIndicator_Locind.getText().equals("") && tIndicator_Locind.getText().length()<4) { DialogFactory.openWarningDialog(Shells.shell_nlocind, "Save Message","Please insert 4 LETTERS for field INDICATOR !!"); tIndicator_Locind.setFocus(); }
//		    		else if (tLocation_Locind.getText().equals("")) { DialogFactory.openWarningDialog(Shells.shell_nlocind, "Save Message","The value in field LOCATION is required."); tLocation_Locind.setFocus(); }
//		    		else {
//		    			String insert = "INSERT INTO location_indicator (indicator,location) VALUES ('"+tIndicator_Locind.getText()+"','"+tLocation_Locind.getText()+"')";
//		    			jdbc.connect(insert);
//		    			shell.close();
//		    		}
//				}
//			});
//			
//			Clear.addSelectionListener(new SelectionAdapter() {
//				public void widgetSelected(SelectionEvent e) {	
//					discardLocind();
//				}
//			});
//		}
//	}
	
	/**
	 * discard
	 */
	void discardRoute() {
		if (tDepAd_Route.getText() != null) tDepAd_Route.setText("");
		if (tDestAd_Route.getText() != null) tDestAd_Route.setText("");
		if (tRoute_Route.getText() != null) tRoute_Route.setText("");
		if (tDest1_Route.getText() != "") tDest1_Route.setText("");
		if (tDest2_Route.getText() != "") tDest2_Route.setText("");
		if (tDest3_Route.getText() != "") tDest3_Route.setText("");
		if (tDest4_Route.getText() != "") tDest4_Route.setText("");
		if (tDest5_Route.getText() != "") tDest5_Route.setText("");
		if (tDest6_Route.getText() != "") tDest6_Route.setText("");
		if (tDest7_Route.getText() != "") tDest7_Route.setText("");
		if (tDest8_Route.getText() != "") tDest8_Route.setText("");
		if (tDest9_Route.getText() != "") tDest9_Route.setText("");
		if (tDest10_Route.getText() != "") tDest10_Route.setText("");
		if (tDest11_Route.getText() != "") tDest11_Route.setText("");
		if (tDest12_Route.getText() != "") tDest12_Route.setText("");
		if (tDest13_Route.getText() != "") tDest13_Route.setText("");
		if (tDest14_Route.getText() != "") tDest14_Route.setText("");
		if (tDest15_Route.getText() != "") tDest15_Route.setText("");
		if (tDest16_Route.getText() != "") tDest16_Route.setText("");
		if (tDest17_Route.getText() != "") tDest17_Route.setText("");
		if (tDest18_Route.getText() != "") tDest18_Route.setText("");
		if (tDest19_Route.getText() != "") tDest19_Route.setText("");
		if (tDest20_Route.getText() != "") tDest20_Route.setText("");
		if (tDest21_Route.getText() != "") tDest21_Route.setText("");
	}

	void discardType9b() {
		if (tTOA_Type9b.getText() != null) tTOA_Type9b.setText("");
		initWTC=0;
		if (lWTC_Type9b.getSelectionCount()>0) {
			if (lWTC_Type9b.getSelection()[0] != " ") {
				lWTC_Type9b.setSelection(0);
				lWTC_Type9b.removeAll();
				lWTC_Type9b.setItems(Combos.cType9c);
			}
		}
	}

	void discardReg() {
		if (tReg_REG.getText() != "") tReg_REG.setText("");
  		if (t9b_REG.getText() != null) t9b_REG.setText("");
		if (t10a_REG.getText() != "") t10a_REG.setText("");
		if (t10b_REG.getText() != "") t10b_REG.setText("");
		if (tOpr_REG.getText() != "") tOpr_REG.setText("");
		if (tPbn_REG.getText() != "") tPbn_REG.setText("");
	}

	void discardLocind() {
		if (tIndicator_Locind.getText() != null) tIndicator_Locind.setText("");
		if (tLocation_Locind.getText() != null) tLocation_Locind.setText("");
	}
}



//void compSTS1() {
//	final Group Group = new Group(shell, SWT.CENTER); sh.groupStyle(Group, 1, "");
////	Group.setLayout(new GridLayout(1,false));
////	GridData gdGroup = new GridData(SWT.CENTER);
////	Group.setLayoutData(gdGroup);
//	
//	Composite compSts = new Composite(Group, SWT.NONE); sh.composeStyle(compSts, 3, SWT.LEFT, SWT.LEFT, 0);
////	GridLayout laySts = new GridLayout();
////	laySts.numColumns = 3;
////	compSts.setLayout(laySts);
////	GridData gdSts = new GridData();
////	compSts.setLayoutData(gdSts);
//	
//	final Button bS1 = new Button(compSts, SWT.CHECK); buttonSts(bS1, "ALTRV");
//	final Button bS2 = new Button(compSts, SWT.CHECK); buttonSts(bS2, "ATFMX");
//	final Button bS3 = new Button(compSts, SWT.CHECK); buttonSts(bS3, "FFR");
//	final Button bS4 = new Button(compSts, SWT.CHECK); buttonSts(bS4, "FLTCK");
//	final Button bS5 = new Button(compSts, SWT.CHECK); buttonSts(bS5, "HAZMAT");
//	final Button bS6 = new Button(compSts, SWT.CHECK); buttonSts(bS6, "HEAD");
//	final Button bS7 = new Button(compSts, SWT.CHECK); buttonSts(bS7, "HOSP");
//	final Button bS8 = new Button(compSts, SWT.CHECK); buttonSts(bS8, "HUM");
//	final Button bS9 = new Button(compSts, SWT.CHECK); buttonSts(bS9, "MARSA");
//	final Button bS10 = new Button(compSts, SWT.CHECK); buttonSts(bS10, "MEDEVAC");
//	final Button bS11 = new Button(compSts, SWT.CHECK); buttonSts(bS11, "NONRVSM");
//	final Button bS12 = new Button(compSts, SWT.CHECK); buttonSts(bS12, "SAR");
//	final Button bS13 = new Button(compSts, SWT.CHECK); buttonSts(bS13, "STATE");
//	
//	bS1.setToolTipText("ALTRV: for a flight operated in accordance with an altitude reservation");
//	bS2.setToolTipText("ATFMX: for a flight approved for exemption from ATFM measures by the appropriate ATS authority");
//	bS3.setToolTipText("FFR: fire-fighting");
//	bS4.setToolTipText("FLTCK: flight check for calibration of navaids");
//	bS5.setToolTipText("HAZMAT: for a flight carrying hazardous material");
//	bS6.setToolTipText("HEAD: a flight with Head of State status");
//	bS7.setToolTipText("HOSP: for medical flight declared by medical authorities");
//	bS8.setToolTipText("HUM: for a flight operating on a humanitarian mission");
//	bS9.setToolTipText("MARSA: for a flight for which a military entity assumes responsibility for separation of military aircraft");
//	bS10.setToolTipText("MEDEVAC: for a life critical medical emergency evacuation");
//	bS11.setToolTipText("NONRVSM: for non-RVSM capable flight intending to operate in RVSM airspace");
//	bS12.setToolTipText("SAR: for a flight engaged in a search and rescue mission");
//	bS13.setToolTipText("STATE: for a flight engaged in military, customs or police services.");
//
//	String sub[] = t.getText().split(" ");//ATSForms.tStsFPL.getText().split(" ");
//	for (int i=0; i<sub.length; i++) {
//		if (sub[i].compareTo("ALTRV")==0) bS1.setSelection(true);
//		if (sub[i].compareTo("ATFMX")==0) bS2.setSelection(true);
//		if (sub[i].compareTo("FFR")==0) bS3.setSelection(true);
//		if (sub[i].compareTo("FLTCK")==0) bS4.setSelection(true);
//		if (sub[i].compareTo("HAZMAT")==0) bS5.setSelection(true);
//		if (sub[i].compareTo("HEAD")==0) bS6.setSelection(true);
//		if (sub[i].compareTo("HOSP")==0) bS7.setSelection(true);
//		if (sub[i].compareTo("HUM")==0) bS8.setSelection(true);
//		if (sub[i].compareTo("MARSA")==0) bS9.setSelection(true);
//		if (sub[i].compareTo("MEDEVAC")==0) bS10.setSelection(true);
//		if (sub[i].compareTo("NONRVSM")==0) bS11.setSelection(true);
//		if (sub[i].compareTo("SAR")==0) bS12.setSelection(true);
//		if (sub[i].compareTo("STATE")==0) bS13.setSelection(true);
//	} //end of for sub[i]
//	
//	Composite compSts2 = new Composite(Group, SWT.NONE); sh.composeStyle(compSts2, 2, SWT.CENTER, SWT.CENTER, 0);
//	Button Input = new Button(compSts2, SWT.PUSH);
//	Input.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			String s1="",s2="",s3="",s4="",s5="",s6="",s7="",s8="",s9="",s10="",s11="",s12="",s13="",all="";
//			if (bS1.getSelection() == true) s1=bS1.getText(); if (!s1.isEmpty()) s1=s1.concat(" "); else s1="";
//			if (bS2.getSelection() == true) s2=bS2.getText(); if (!s2.isEmpty()) s2=s2.concat(" "); else s2="";
//			if (bS3.getSelection() == true) s3=bS3.getText(); if (!s3.isEmpty()) s3=s3.concat(" "); else s3="";
//			if (bS4.getSelection() == true) s4=bS4.getText(); if (!s4.isEmpty()) s4=s4.concat(" "); else s4="";
//			if (bS5.getSelection() == true) s5=bS5.getText(); if (!s5.isEmpty()) s5=s5.concat(" "); else s5="";
//			if (bS6.getSelection() == true) s6=bS6.getText(); if (!s6.isEmpty()) s6=s6.concat(" "); else s6="";
//			if (bS7.getSelection() == true) s7=bS7.getText(); if (!s7.isEmpty()) s7=s7.concat(" "); else s7="";
//			if (bS8.getSelection() == true) s8=bS8.getText(); if (!s8.isEmpty()) s8=s8.concat(" "); else s8="";
//			if (bS9.getSelection() == true) s9=bS9.getText(); if (!s9.isEmpty()) s9=s9.concat(" "); else s9="";
//			if (bS10.getSelection() == true) s10=bS10.getText(); if (!s10.isEmpty()) s10=s10.concat(" "); else s10="";
//			if (bS11.getSelection() == true) s11=bS11.getText(); if (!s11.isEmpty()) s11=s11.concat(" "); else s11="";
//			if (bS12.getSelection() == true) s12=bS12.getText(); if (!s12.isEmpty()) s12=s12.concat(" "); else s12="";
//			if (bS13.getSelection() == true) s13=bS13.getText(); if (!s13.isEmpty()) s13=s13.concat(" "); else s13="";
//			
//			all=s1+s2+s3+s4+s5+s6+s7+s8+s9+s10+s11+s12+s13;
//			int index = all.lastIndexOf(" ");
//			all = all.substring(0, index);
//			//ATSForms.tStsFPL.setText(all);
//			t.setText(all);
//			shell.close();
//		}
//	});
//	
//	Button Cancel = new Button(compSts2, SWT.PUSH);
//	Cancel.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS1.getSelection() == true) bS1.setSelection(false); 
//			if (bS2.getSelection() == true) bS2.setSelection(false);
//			if (bS3.getSelection() == true) bS3.setSelection(false);
//			if (bS4.getSelection() == true) bS4.setSelection(false);
//			if (bS5.getSelection() == true) bS5.setSelection(false);
//			if (bS6.getSelection() == true) bS6.setSelection(false);
//			if (bS7.getSelection() == true) bS7.setSelection(false);
//			if (bS8.getSelection() == true) bS8.setSelection(false);
//			if (bS9.getSelection() == true) bS9.setSelection(false);
//			if (bS10.getSelection() == true) bS10.setSelection(false);
//			if (bS11.getSelection() == true) bS11.setSelection(false);
//			if (bS12.getSelection() == true) bS12.setSelection(false);
//			if (bS13.getSelection() == true) bS13.setSelection(false);
//			shell.close();
//		}
//	});
//	bs.InputCancel(Input, Cancel);
//}
//void comp10a_asli() {
//	final Group Group = new Group(shell, SWT.CENTER); sh.groupStyle(Group, 1, "");
//	Label label = new Label(Group, SWT.NONE); sh.labelStyle1(label, "Insert one letter as follows", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
//	
//	Composite compNS = new Composite(Group, SWT.NONE); sh.composeStyle(compNS, 3, SWT.LEFT, SWT.CENTER);
//	final Button bS1 = new Button(compNS, SWT.CHECK | SWT.BORDER); button(bS1, "N"); 
//	label = new Label(compNS, SWT.NONE); sh.labelStyle(label, "OR", 55, SWT.CENTER, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
//	final Button bS2 = new Button(compNS, SWT.CHECK); button(bS2, "S");
//	
//	label = new Label(Group, SWT.NONE); sh.labelStyle1(label, "AND/OR INSERT one or more of the following letters to indicate\nthe serviciable COM/NAV/approach aid equipment and capabilities available: ", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
//	label = new Label(Group, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.CENTER); sh.labelSeparator(label, 530, 2);
//	
//	Composite comp1 = new Composite(Group, SWT.NONE); sh.composeStyle(comp1, 11, SWT.LEFT, SWT.CENTER);
//	final Button bS3 = new Button(comp1, SWT.CHECK); button(bS3, "A"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS11 = new Button(comp1, SWT.CHECK); button(bS11, "G"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS19 = new Button(comp1, SWT.CHECK); button(bS19, "J6"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS27 = new Button(comp1, SWT.CHECK); button(bS27, "P1"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS35 = new Button(comp1, SWT.CHECK); button(bS35, "P9"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS43 = new Button(comp1, SWT.CHECK); button(bS43, "Z"); 
//	//--
//	final Button bS4 = new Button(comp1, SWT.CHECK); button(bS4, "B"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS12 = new Button(comp1, SWT.CHECK); button(bS12, "H"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS20 = new Button(comp1, SWT.CHECK); button(bS20, "J7");
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS28 = new Button(comp1, SWT.CHECK); button(bS28, "P2"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS36 = new Button(comp1, SWT.CHECK); button(bS36, "R"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS44 = new Button(comp1, SWT.CHECK); button(bS44, "E");
//	bS44.setVisible(false);
//	//--
//	final Button bS5 = new Button(comp1, SWT.CHECK); button(bS5, "C"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS13 = new Button(comp1, SWT.CHECK); button(bS13, "I"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS21 = new Button(comp1, SWT.CHECK); button(bS21, "K"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS29 = new Button(comp1, SWT.CHECK); button(bS29, "P3"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS37 = new Button(comp1, SWT.CHECK); button(bS37, "T"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS45 = new Button(comp1, SWT.CHECK); button(bS45, "J");
//	bS45.setVisible(false);
//	//--
//	final Button bS6 = new Button(comp1, SWT.CHECK); button(bS6, "D"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS14 = new Button(comp1, SWT.CHECK); button(bS14, "J1");
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS22 = new Button(comp1, SWT.CHECK); button(bS22, "L"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS30 = new Button(comp1, SWT.CHECK); button(bS30, "P4");
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS38 = new Button(comp1, SWT.CHECK); button(bS38, "U");
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS46 = new Button(comp1, SWT.CHECK); button(bS46, "M");
//	bS46.setVisible(false);
//	//--
//	final Button bS7 = new Button(comp1, SWT.CHECK); button(bS7, "E1"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS15 = new Button(comp1, SWT.CHECK); button(bS15, "J2"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS23 = new Button(comp1, SWT.CHECK); button(bS23, "M1"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS31 = new Button(comp1, SWT.CHECK); button(bS31, "P5"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS39 = new Button(comp1, SWT.CHECK); button(bS39, "V"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS47 = new Button(comp1, SWT.CHECK); button(bS47, "P");
//	bS47.setVisible(false);
//	//--
//	final Button bS8 = new Button(comp1, SWT.CHECK); button(bS8, "E2"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS16 = new Button(comp1, SWT.CHECK); button(bS16, "J3"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS24 = new Button(comp1, SWT.CHECK); button(bS24, "M2");
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS32 = new Button(comp1, SWT.CHECK); button(bS32, "P6");
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS40 = new Button(comp1, SWT.CHECK); button(bS40, "W"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS48 = new Button(comp1, SWT.CHECK); button(bS48, "Q");
//	bS48.setVisible(false);
//	//--
//	final Button bS9 = new Button(comp1, SWT.CHECK); button(bS9, "E3"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS17 = new Button(comp1, SWT.CHECK); button(bS17, "J4"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS25 = new Button(comp1, SWT.CHECK); button(bS25, "M3"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS33 = new Button(comp1, SWT.CHECK); button(bS33, "P7");
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS41 = new Button(comp1, SWT.CHECK); button(bS41, "X"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	//--
//	final Button bS10 = new Button(comp1, SWT.CHECK); button(bS10, "F"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS18 = new Button(comp1, SWT.CHECK); button(bS18, "J5"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS26 = new Button(comp1, SWT.CHECK); button(bS26, "O");
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS34 = new Button(comp1, SWT.CHECK); button(bS34, "P8"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	final Button bS42 = new Button(comp1, SWT.CHECK); button(bS42, "Y"); 
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	label = new Label(comp1, SWT.NONE); label.setText("\t");
//	//------------------------------------------------------------
//	String sub = t.getText();
//		if (sub.contains("N")) bS1.setSelection(true);
//		
//		if (sub.contains("A")) bS3.setSelection(true);
//		if (sub.contains("B")) bS4.setSelection(true);
//		if (sub.contains("C")) bS5.setSelection(true);
//		if (sub.contains("D")) bS6.setSelection(true);
//		if (sub.contains("E1")) bS7.setSelection(true);
//		if (sub.contains("E2")) bS8.setSelection(true);
//		if (sub.contains("E3")) bS9.setSelection(true);
//		if (sub.contains("F")) bS10.setSelection(true);
//		if (sub.contains("G")) bS11.setSelection(true);
//		if (sub.contains("H")) bS12.setSelection(true);
//		if (sub.contains("I")) bS13.setSelection(true);
//		if (sub.contains("J1")) bS14.setSelection(true);
//		if (sub.contains("J2")) bS15.setSelection(true);
//		if (sub.contains("J3")) bS16.setSelection(true);
//		if (sub.contains("J4")) bS17.setSelection(true);
//		if (sub.contains("J5")) bS18.setSelection(true);
//		if (sub.contains("J6")) bS19.setSelection(true);
//		if (sub.contains("J7")) bS20.setSelection(true);
//		if (sub.contains("K")) bS21.setSelection(true);
//		if (sub.contains("L")) bS22.setSelection(true);
//		if (sub.contains("M1")) bS23.setSelection(true);
//		if (sub.contains("M2")) bS24.setSelection(true);
//		if (sub.contains("M3")) bS25.setSelection(true);
//		if (sub.contains("O")) bS26.setSelection(true);
//		if (sub.contains("P1")) bS27.setSelection(true);
//		if (sub.contains("P2")) bS28.setSelection(true);
//		if (sub.contains("P3")) bS29.setSelection(true);
//		if (sub.contains("P4")) bS30.setSelection(true);
//		if (sub.contains("P5")) bS31.setSelection(true);
//		if (sub.contains("P6")) bS32.setSelection(true);
//		if (sub.contains("P7")) bS33.setSelection(true);
//		if (sub.contains("P8")) bS34.setSelection(true);
//		if (sub.contains("P9")) bS35.setSelection(true);
//		if (sub.contains("R")) bS36.setSelection(true);
//		if (sub.contains("T")) bS37.setSelection(true);
//		if (sub.contains("U")) bS38.setSelection(true);
//		if (sub.contains("V")) bS39.setSelection(true);
//		if (sub.contains("W")) bS40.setSelection(true);
//		if (sub.contains("X")) bS41.setSelection(true);
//		if (sub.contains("Y")) bS42.setSelection(true);
//		if (sub.contains("Z")) bS43.setSelection(true);
//		if (sub.contains("E")) bS44.setSelection(true);
//		if (sub.contains("J")) bS45.setSelection(true);
//		if (sub.contains("M")) bS46.setSelection(true);
//		if (sub.contains("P")) bS47.setSelection(true);
//		if (sub.contains("Q")) bS48.setSelection(true);
////	} //end of for sub
//	
//		if (sub.contains("N")) {
//			bS1.setSelection(true);
//			bS2.setEnabled(false);
//		} else {
//			if (sub.contains("S")) bS2.setSelection(true);
//		}
//
//		if (sub.contains("S")) {
//			bS2.setSelection(true);
//			bS1.setEnabled(false);
//		} else {
//			if (sub.contains("N")) bS1.setSelection(true);
//		}
//		
//	//ToolTipText
//	bS1.setToolTipText("N: if no COM/NAV/approach aid equipment for the route to be flown is carried, or the equipment is unserviceable");
//	bS2.setToolTipText("S: if standard COM/NAV/approach aid equipment for the route to be flown is carried and serviceable\n\nNote: If the letter S is used, standard equipment is considered to be VHF, RTF, VOR and ILS, unless another combination is prescribed by the ATS authority.");
////	bS3.setToolTipText("A: GBAS landing system *new version\n" +
////			"A: (Not allocated) *current version");
////	bS4.setToolTipText("B: LPV (APV with SBAS) *new version\n" +
////			"B: (Not allocated) *current version");
//	bS5.setToolTipText("C: LORAN C");
//	bS6.setToolTipText("D: DME");
//	bS7.setToolTipText("E1: FMC WPR ACARS");
//	bS8.setToolTipText("E2: D-FIS ACARS");
//	bS9.setToolTipText("E3: PDC ACARS");
//	bS10.setToolTipText("F: ADF");
//	
//	bS11.setToolTipText("G: GNSS");
//	bS12.setToolTipText("H: HF RTF");
//	bS13.setToolTipText("I: Inertial Navigation");
//	bS14.setToolTipText("J1: CPDLC ATN Mode 2");
//	bS15.setToolTipText("J2: CPDLC FANS 1/A HFDL");
//	bS16.setToolTipText("J3: CPDLC FANS 1/A VDL Mode A");
//	bS17.setToolTipText("J4: CPDLC FANS 1/A VDL Mode 2");
//	bS18.setToolTipText("J5: CPDLC FANS 1/A SATCOM (INMARSAT)");
//
//	bS19.setToolTipText("J6: CPDLC FANS 1/A SATCOM (MTSAT)");
//	bS20.setToolTipText("J7: CPDLC FANS 1/A SATCOM (Iridium)");
//	bS21.setToolTipText("K: MLS");
//	bS22.setToolTipText("L: ILS");
//	bS23.setToolTipText("M1: ATC RTF SATCOM (INMARSAT)");
//	bS24.setToolTipText("M2: ATC RTF (MTSAT)");
//	bS25.setToolTipText("M3: ATC RTF (Iridium)");
//	bS26.setToolTipText("O: VOR");
//	
//	bS27.setToolTipText("P1: Reserved for RCP");
//	bS28.setToolTipText("P2: Reserved for RCP");
//	bS29.setToolTipText("P3: Reserved for RCP");
//	bS30.setToolTipText("P4: Reserved for RCP");
//	bS31.setToolTipText("P5: Reserved for RCP");
//	bS32.setToolTipText("P6: Reserved for RCP");
//	bS33.setToolTipText("P7: Reserved for RCP");
//	bS34.setToolTipText("P8: Reserved for RCP");
//	
//	bS35.setToolTipText("P9: Reserved for RCP");
//	bS36.setToolTipText("R: PBN approved");
//	bS37.setToolTipText("T: TACAN");
//	bS38.setToolTipText("U: UHF RTF");
//	bS39.setToolTipText("V: VHF RTF");
//	bS40.setToolTipText("W: RVSM approved");
//	bS41.setToolTipText("X: MNPS approved");
//	bS42.setToolTipText("Y: VHF with 8.33 kHz channel spacing capability");
//	
//	bS43.setToolTipText("Z: Other equipment carried or other capabilities");
//	bS44.setToolTipText("E: (Not allocated) *current version");
//	bS45.setToolTipText("J: If the letter J is used, the equipment carried is to be specified in Item 18, preceded by COM/ and/or NAV/, as appropriate *current version");
//	bS46.setToolTipText("M: Omega *current version");
//	bS47.setToolTipText("P: (Not allocated) *current version");
//	bS48.setToolTipText("Q: (Not allocated) *current version");
//
//	bS1.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS1.getSelection()==true) bS2.setEnabled(false);
//			else if (bS1.getSelection()==false) bS2.setEnabled(true);
//		}
//	});
//	bS2.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS2.getSelection()==true) bS1.setEnabled(false);
//			else if (bS2.getSelection()==false) bS1.setEnabled(true);
//		}
//	});
//	
//	
//	if (ver.compareTo("new")==0) {
//		bS7.setEnabled(true);
//		bS8.setEnabled(true);
//		bS9.setEnabled(true);
//		
//		bS14.setEnabled(true);
//		bS15.setEnabled(true);
//		bS16.setEnabled(true);
//		bS17.setEnabled(true);
//		bS18.setEnabled(true);
//		bS19.setEnabled(true);
//		bS20.setEnabled(true);	
//		
//		bS23.setEnabled(true);
//		bS24.setEnabled(true);
//		bS25.setEnabled(true);	
//		
//		bS27.setEnabled(true);	
//		bS28.setEnabled(true);	
//		bS29.setEnabled(true);	
//		bS30.setEnabled(true);	
//		bS31.setEnabled(true);	
//		bS32.setEnabled(true);	
//		bS33.setEnabled(true);	
//		bS34.setEnabled(true);	
//		bS35.setEnabled(true);	
//		
//		bS44.setEnabled(false);
//		bS45.setEnabled(false);
//		bS46.setEnabled(false);
//		bS47.setEnabled(false);
//		bS48.setEnabled(false);
//		
//		bS3.setToolTipText("A: GBAS landing system");
//		bS4.setToolTipText("B: LPV (APV with SBAS)");
//	} else if (ver.compareTo("present")==0) {
//		bS3.setToolTipText("A: (Not allocated)");
//		bS4.setToolTipText("B: (Not allocated)");
//		
//		bS7.setEnabled(false);
//		bS8.setEnabled(false);
//		bS9.setEnabled(false);
//		
//		bS14.setEnabled(false);
//		bS15.setEnabled(false);
//		bS16.setEnabled(false);
//		bS17.setEnabled(false);
//		bS18.setEnabled(false);
//		bS19.setEnabled(false);
//		bS20.setEnabled(false);	
//		
//		bS23.setEnabled(false);
//		bS24.setEnabled(false);
//		bS25.setEnabled(false);	
//		
//		bS27.setEnabled(false);	
//		bS28.setEnabled(false);	
//		bS29.setEnabled(false);	
//		bS30.setEnabled(false);	
//		bS31.setEnabled(false);	
//		bS32.setEnabled(false);	
//		bS33.setEnabled(false);	
//		bS34.setEnabled(false);	
//		bS35.setEnabled(false);	
//		
//		bS44.setEnabled(true);
//		bS45.setEnabled(true);
//		bS46.setEnabled(true);
//		bS47.setEnabled(true);
//		bS48.setEnabled(true);
//	}
//	
//	label = new Label(Group, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.CENTER); sh.labelSeparator(label, 530, 2);
//	label = new Label(Group, SWT.NONE); sh.labelStyle1(label, "Any alphanumeric characters not indicated above are reserved.", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
//	
////	Composite compUn = new Composite(Group, SWT.NONE); sh.composeStyle(compUn, 1, SWT.LEFT, SWT.CENTER);
////	Button bUncheck = new Button(compUn, SWT.CHECK); sh.buttonRCStyle(bUncheck, "Uncheck all", "Uncheck all", 120, bs.heightBtn);
////	bUncheck.addSelectionListener(new SelectionAdapter() {
////		public void widgetSelected(SelectionEvent e) {
////			bS7.setSelection(false);
////			bS8.setSelection(false);
////			bS9.setSelection(false);
////			
////			bS14.setSelection(false);
////			bS15.setSelection(false);
////			bS16.setSelection(false);
////			bS17.setSelection(false);
////			bS18.setSelection(false);
////			bS19.setSelection(false);
////			bS20.setSelection(false);	
////			
////			bS23.setSelection(false);
////			bS24.setSelection(false);
////			bS25.setSelection(false);	
////			
////			bS27.setSelection(false);	
////			bS28.setSelection(false);	
////			bS29.setSelection(false);	
////			bS30.setSelection(false);	
////			bS31.setSelection(false);	
////			bS32.setSelection(false);	
////			bS33.setSelection(false);	
////			bS34.setSelection(false);	
////			bS35.setSelection(false);	
////			
////			bS44.setSelection(true);
////			bS45.setSelection(true);
////			bS46.setSelection(true);
////			bS47.setSelection(true);
////			bS48.setSelection(true);
////		}
////	});
//	
//	Composite typeB = new Composite(Group, SWT.NONE); sh.composeStyle(typeB, 1, SWT.CENTER, SWT.CENTER);
//	label = new Label(typeB, SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelStyle(label, "", 360, SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
//	
//	Composite typeE = new Composite(Group, SWT.NONE); sh.composeStyle(typeE, 3, SWT.CENTER, SWT.CENTER);
//	Button bAdd = new Button(typeE, SWT.PUSH);
//	Button bClear = new Button(typeE, SWT.PUSH);
//	Button bClose = new Button(typeE, SWT.PUSH);
//	bs.ACC(bAdd, bClear,bClose);
//	
////	Composite compBtn = new Composite(Group, SWT.NONE); sh.composeStyle(compBtn, 2, SWT.CENTER, SWT.CENTER);
////	Button Input = new Button(compBtn, SWT.PUSH);
//	bAdd.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			String s1="",s2="",s3="",s4="",s5="",s6="",s7="",s8="",s9="",s10="",s11="",s12="",s13="",s14="",
//			s15="",s16="",s17="",s18="",s19="",s20="",s21="",s22="",s23="",s24="",s25="",s26="",s27="",s28="",
//			s29="",s30="",s31="",s32="",s33="",s34="",s35="",s36="",s37="",s38="",s39="",s40="",s41="",s42="",s43="",
//			s44="",s45="",s46="",s47="",s48="",all="";
//			
//			if (bS1.getSelection() == true) s1=bS1.getText(); if (s1.isEmpty()) s1="";
//			if (bS2.getSelection() == true) s2=bS2.getText(); if (s2.isEmpty()) s2="";
//			if (bS3.getSelection() == true) s3=bS3.getText(); if (s3.isEmpty()) s3="";
//			if (bS4.getSelection() == true) s4=bS4.getText(); if (s4.isEmpty()) s4="";
//			if (bS5.getSelection() == true) s5=bS5.getText(); if (s5.isEmpty()) s5="";
//			if (bS6.getSelection() == true) s6=bS6.getText(); if (s6.isEmpty()) s6="";
//			if (bS7.getSelection() == true) s7=bS7.getText(); if (s7.isEmpty()) s7="";
//			if (bS8.getSelection() == true) s8=bS8.getText(); if (s8.isEmpty()) s8="";
//			if (bS9.getSelection() == true) s9=bS9.getText(); if (s9.isEmpty()) s9="";
//			if (bS10.getSelection() == true) s10=bS10.getText(); if (s10.isEmpty()) s10="";
//			if (bS11.getSelection() == true) s11=bS11.getText(); if (s11.isEmpty()) s11="";
//			if (bS12.getSelection() == true) s12=bS12.getText(); if (s12.isEmpty()) s12="";
//			if (bS13.getSelection() == true) s13=bS13.getText(); if (s13.isEmpty()) s13="";
//			if (bS14.getSelection() == true) s14=bS14.getText(); if (s14.isEmpty()) s14="";
//			if (bS15.getSelection() == true) s15=bS15.getText(); if (s15.isEmpty()) s15="";
//			if (bS16.getSelection() == true) s16=bS16.getText(); if (s16.isEmpty()) s16="";
//			if (bS17.getSelection() == true) s17=bS17.getText(); if (s17.isEmpty()) s17="";
//			if (bS18.getSelection() == true) s18=bS18.getText(); if (s18.isEmpty()) s18="";
//			if (bS19.getSelection() == true) s19=bS19.getText(); if (s19.isEmpty()) s19="";
//			if (bS20.getSelection() == true) s20=bS20.getText(); if (s20.isEmpty()) s20="";
//			if (bS21.getSelection() == true) s21=bS21.getText(); if (s21.isEmpty()) s21="";
//			if (bS22.getSelection() == true) s22=bS22.getText(); if (s22.isEmpty()) s22="";
//			if (bS23.getSelection() == true) s23=bS23.getText(); if (s23.isEmpty()) s23="";
//			if (bS24.getSelection() == true) s24=bS24.getText(); if (s24.isEmpty()) s24="";
//			if (bS25.getSelection() == true) s25=bS25.getText(); if (s25.isEmpty()) s25="";
//			if (bS26.getSelection() == true) s26=bS26.getText(); if (s26.isEmpty()) s26="";
//			if (bS27.getSelection() == true) s27=bS27.getText(); if (s27.isEmpty()) s27="";
//			if (bS28.getSelection() == true) s28=bS28.getText(); if (s28.isEmpty()) s28="";
//			if (bS29.getSelection() == true) s29=bS29.getText(); if (s29.isEmpty()) s29="";
//			if (bS30.getSelection() == true) s30=bS30.getText(); if (s30.isEmpty()) s30="";
//			if (bS31.getSelection() == true) s31=bS31.getText(); if (s31.isEmpty()) s31="";
//			if (bS32.getSelection() == true) s32=bS32.getText(); if (s32.isEmpty()) s32="";
//			if (bS33.getSelection() == true) s33=bS33.getText(); if (s33.isEmpty()) s33="";
//			if (bS34.getSelection() == true) s34=bS34.getText(); if (s34.isEmpty()) s34="";
//			if (bS35.getSelection() == true) s35=bS35.getText(); if (s35.isEmpty()) s35="";
//			if (bS36.getSelection() == true) s36=bS36.getText(); if (s36.isEmpty()) s36="";
//			if (bS37.getSelection() == true) s37=bS37.getText(); if (s37.isEmpty()) s37="";
//			if (bS38.getSelection() == true) s38=bS38.getText(); if (s38.isEmpty()) s38="";
//			if (bS39.getSelection() == true) s39=bS39.getText(); if (s39.isEmpty()) s39="";
//			if (bS40.getSelection() == true) s40=bS40.getText(); if (s40.isEmpty()) s40="";
//			if (bS41.getSelection() == true) s41=bS41.getText(); if (s41.isEmpty()) s41="";
//			if (bS42.getSelection() == true) s42=bS42.getText(); if (s42.isEmpty()) s42="";
//			if (bS43.getSelection() == true) s43=bS43.getText(); if (s43.isEmpty()) s43="";
//			if (bS44.getSelection() == true) s44=bS44.getText(); if (s44.isEmpty()) s44="";
//			if (bS45.getSelection() == true) s45=bS45.getText(); if (s45.isEmpty()) s45="";
//			if (bS46.getSelection() == true) s46=bS46.getText(); if (s46.isEmpty()) s46="";
//			if (bS47.getSelection() == true) s47=bS47.getText(); if (s47.isEmpty()) s47="";
//			if (bS48.getSelection() == true) s48=bS48.getText(); if (s48.isEmpty()) s48="";
//			
//			if (ver.compareTo("new")==0) {
//				s44="";s45="";s46="";s47="";s48=""; //E,J,M,P,Q
//			} else if (ver.compareTo("present")==0) {
//				s7="";s8="";s9="";//E
//				s14="";s15="";s16="";s17="";s18="";s19="";s20="";//J	
//				s23="";s24="";s25="";//M
//				s27="";s28="";s29="";s30="";s31="";s32="";s33="";s34="";s35="";//P	
//			}
//			
//			all=s1+s2+s3+s4+s5+s6+s7+s8+s9+s10+s11+s12+s13+s14+s15+s16+s17+s18+s19+s20+s21+
//			s22+s23+s24+s25+s26+s27+s28+s29+s30+s31+s32+s33+s34+s35+s36+s37+s38+s39+s40+s41+s42+
//			s43+s44+s45+s46+s47+s48;
//			t.setText(all);
//
//			if (s1.compareTo("")!=0 && s2.compareTo("")!=0) {
//				DialogFactory.openWarningDialog(Shells.sh_m10a,"Input","Invalid EQUIPMENT AND CAPABILITIES value.\n'N' is not allowed, if 'S' has inserted into field EQUIPMENT AND CAPABILITIES and vice versa.");
//			} else {
//				shell.close();
//			}
//		}
//	});
//	
////	Button Cancel = new Button(compBtn, SWT.PUSH);
//	bClear.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS1.getSelection() == true) bS1.setSelection(false); 
//			if (bS2.getSelection() == true) bS2.setSelection(false);
//			if (bS3.getSelection() == true) bS3.setSelection(false);
//			if (bS4.getSelection() == true) bS4.setSelection(false);
//			if (bS5.getSelection() == true) bS5.setSelection(false);
//			if (bS6.getSelection() == true) bS6.setSelection(false);
//			if (bS7.getSelection() == true) bS7.setSelection(false);
//			if (bS8.getSelection() == true) bS8.setSelection(false);
//			if (bS9.getSelection() == true) bS9.setSelection(false);
//			if (bS10.getSelection() == true) bS10.setSelection(false);
//			if (bS11.getSelection() == true) bS11.setSelection(false);
//			if (bS12.getSelection() == true) bS12.setSelection(false);
//			if (bS13.getSelection() == true) bS13.setSelection(false);
//			if (bS14.getSelection() == true) bS14.setSelection(false);
//			if (bS15.getSelection() == true) bS15.setSelection(false);
//			if (bS16.getSelection() == true) bS16.setSelection(false);
//			if (bS17.getSelection() == true) bS17.setSelection(false);
//			if (bS18.getSelection() == true) bS18.setSelection(false);
//			if (bS19.getSelection() == true) bS19.setSelection(false);
//			if (bS20.getSelection() == true) bS20.setSelection(false);
//			if (bS21.getSelection() == true) bS21.setSelection(false);
//			if (bS22.getSelection() == true) bS22.setSelection(false);
//			if (bS23.getSelection() == true) bS23.setSelection(false);
//			if (bS24.getSelection() == true) bS24.setSelection(false);
//			if (bS25.getSelection() == true) bS25.setSelection(false);
//			if (bS26.getSelection() == true) bS26.setSelection(false);
//			if (bS27.getSelection() == true) bS27.setSelection(false);
//			if (bS28.getSelection() == true) bS28.setSelection(false);
//			if (bS29.getSelection() == true) bS29.setSelection(false);
//			if (bS30.getSelection() == true) bS30.setSelection(false);
//			if (bS31.getSelection() == true) bS31.setSelection(false);
//			if (bS32.getSelection() == true) bS32.setSelection(false);
//			if (bS33.getSelection() == true) bS33.setSelection(false);
//			if (bS34.getSelection() == true) bS34.setSelection(false);
//			if (bS35.getSelection() == true) bS35.setSelection(false);
//			if (bS36.getSelection() == true) bS36.setSelection(false);
//			if (bS37.getSelection() == true) bS37.setSelection(false);
//			if (bS38.getSelection() == true) bS38.setSelection(false);
//			if (bS39.getSelection() == true) bS39.setSelection(false);
//			if (bS40.getSelection() == true) bS40.setSelection(false);
//			if (bS41.getSelection() == true) bS41.setSelection(false);
//			if (bS42.getSelection() == true) bS42.setSelection(false);
//			if (bS43.getSelection() == true) bS43.setSelection(false);
//			if (bS44.getSelection() == true) bS44.setSelection(false);
//			if (bS45.getSelection() == true) bS45.setSelection(false);
//			if (bS46.getSelection() == true) bS46.setSelection(false);
//			if (bS47.getSelection() == true) bS47.setSelection(false);
//			if (bS48.getSelection() == true) bS48.setSelection(false);
//			t.setText("");
//		}
//	});
//	
//	bClose.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			shell.close();
//		}
//	});
//	
////	bs.InputCancel(Input, Cancel);
//	//if (Colors.NORMAL!=null) Colors.NORMAL.dispose();
//}
//void comp10b_asli() {
//	final Group Group = new Group(shell, SWT.CENTER); sh.groupStyle(Group, 1, "");
//	Composite comp1 = new Composite(Group, SWT.NONE); sh.composeStyle(comp1, 2, SWT.LEFT, SWT.CENTER); 
//	Label label = new Label(comp1, SWT.NONE); sh.labelStyle1(label, "INSERT", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
//	final Button bS1 = new Button(comp1, SWT.CHECK); button(bS1, "N"); 
//
//	label = new Label(Group, SWT.NONE); sh.labelStyle1(label, "OR INSERT one or more of the following descriptors, to a maximum of 20 characters,\nto describe the serviceable surveillance equipment and/or capabilities on board:", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL); 
//	label = new Label(Group, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.CENTER); sh.labelSeparator(label, 530, 2);
//	
//	Composite comp3 = new Composite(Group, SWT.NONE); sh.composeStyle(comp3, 7, SWT.LEFT, SWT.CENTER);
//	Composite comp3a = new Composite(comp3, SWT.NONE); sh.composeStyle(comp3a, 1, SWT.LEFT, SWT.TOP);
//	label = new Label(comp3a, SWT.NONE); sh.labelStyle1(label, "SSR Modes A and C", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
//	final Button bS2 = new Button(comp3a, SWT.CHECK); button(bS2, "A"); 
//	final Button bS3 = new Button(comp3a, SWT.CHECK); button(bS3, "C"); 
//
//	label = new Label(comp3, SWT.SEPARATOR | SWT.VERTICAL); sh.labelSeparator(label, 20, 230);
//	Composite comp3b = new Composite(comp3, SWT.NONE); sh.composeStyle(comp3b, 1, SWT.LEFT, SWT.TOP);
//	label = new Label(comp3b, SWT.NONE); sh.labelStyle1(label, "SSR Modes S", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
//	final Button bS4 = new Button(comp3b, SWT.CHECK); button(bS4, "E"); 
//	final Button bS5 = new Button(comp3b, SWT.CHECK); button(bS5, "H"); 
//	final Button bS6 = new Button(comp3b, SWT.CHECK); button(bS6, "I"); 
//	final Button bS7 = new Button(comp3b, SWT.CHECK); button(bS7, "L");
//	final Button bS8 = new Button(comp3b, SWT.CHECK); button(bS8, "P");
//	final Button bS9 = new Button(comp3b, SWT.CHECK); button(bS9, "S"); 
//	final Button bS10 = new Button(comp3b, SWT.CHECK); button(bS10, "X"); 
//	
//	label = new Label(comp3, SWT.SEPARATOR | SWT.VERTICAL); sh.labelSeparator(label, 20, 230);
//	Composite comp3c = new Composite(comp3, SWT.NONE); sh.composeStyle(comp3c, 1, SWT.LEFT, SWT.TOP);
//	label = new Label(comp3c, SWT.NONE); sh.labelStyle1(label, "ADS-B", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
//	final Button bS11 = new Button(comp3c, SWT.CHECK); button(bS11, "B1"); 
//	final Button bS12 = new Button(comp3c, SWT.CHECK); button(bS12, "B2");
//	final Button bS13 = new Button(comp3c, SWT.CHECK); button(bS13, "U1");
//	final Button bS14 = new Button(comp3c, SWT.CHECK); button(bS14, "U2");
//	final Button bS15 = new Button(comp3c, SWT.CHECK); button(bS15, "V1");
//	final Button bS16 = new Button(comp3c, SWT.CHECK); button(bS16, "V2");
//	
//	label = new Label(comp3, SWT.SEPARATOR | SWT.VERTICAL); sh.labelSeparator(label, 20, 230);
//	Composite comp3d = new Composite(comp3, SWT.NONE); sh.composeStyle(comp3d, 1, SWT.LEFT, SWT.TOP);
//	label = new Label(comp3d, SWT.NONE); sh.labelStyle1(label, "ADS-C", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
//	final Button bS17 = new Button(comp3d, SWT.CHECK); button(bS17, "D1");
//	final Button bS18 = new Button(comp3d, SWT.CHECK); button(bS18, "G1");
//	
//	label = new Label(Group, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.CENTER); sh.labelSeparator(label, 530, 2);
//	label = new Label(Group, SWT.NONE); sh.labelStyle1(label, "Notes: Enhanced surveillance capability is the ability of the aircraft to down-link\naircraft derived data via a Mode S transponder.", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
//	label = new Label(Group, SWT.NONE); sh.labelStyle1(label, "Alphanumeric characters not indicated above are reserved.", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
//	label = new Label(Group, SWT.NONE); sh.labelStyle1(label, "Example: ADE3RV/HB2U2V2G1", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
//	label = new Label(Group, SWT.NONE); sh.labelStyle1(label, "Notes: Additional surveillance application should be listed in Item 18 following\nthe indicator SUR/.", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
//	
//	//------------------------------------------------------------
//	String sub = t.getText();
////	String sub[] = ATSForms.t.getText().split(" ");//ATSForms.tFPL.getText().split(" ");
////	for (int i=0; i<sub.length; i++) {
//		if (sub.contains("N")) {
//			bS1.setSelection(true);
//			bS2.setEnabled(false);
//			bS3.setEnabled(false);
//			bS4.setEnabled(false);
//			bS5.setEnabled(false);
//			bS6.setEnabled(false);
//			bS7.setEnabled(false);
//			bS8.setEnabled(false);
//			bS9.setEnabled(false);
//			bS10.setEnabled(false);
//			bS11.setEnabled(false);
//			bS12.setEnabled(false);
//			bS13.setEnabled(false);
//			bS14.setEnabled(false);
//			bS15.setEnabled(false);
//			bS16.setEnabled(false);
//			bS17.setEnabled(false);
//			bS18.setEnabled(false);
//		}
//		//--percobaan
//		else if (sub.contains("A")) {
//			bS1.setEnabled(false);
//			bS2.setSelection(true);
//			bS3.setEnabled(false);
//			bS4.setEnabled(false);
//			bS5.setEnabled(false);
//			bS6.setEnabled(false);
//			bS7.setEnabled(false);
//			bS8.setEnabled(false);
//			bS9.setEnabled(false);
//			bS10.setEnabled(false);
//		} else if (sub.contains("C")) {
//			bS1.setEnabled(false);
//			bS2.setEnabled(false);
//			bS3.setSelection(true);
//			bS4.setEnabled(false);
//			bS5.setEnabled(false);
//			bS6.setEnabled(false);
//			bS7.setEnabled(false);
//			bS8.setEnabled(false);
//			bS9.setEnabled(false);
//			bS10.setEnabled(false);
//		} else if (sub.contains("E")) {
//			bS1.setEnabled(false);
//			bS2.setEnabled(false);
//			bS3.setEnabled(false);
//			bS4.setSelection(true);
//			bS5.setEnabled(false);
//			bS6.setEnabled(false);
//			bS7.setEnabled(false);
//			bS8.setEnabled(false);
//			bS9.setEnabled(false);
//			bS10.setEnabled(false);
//		} else if (sub.contains("H")) {
//			bS1.setEnabled(false);
//			bS2.setEnabled(false);
//			bS3.setEnabled(false);
//			bS4.setEnabled(false);
//			bS5.setSelection(true);
//			bS6.setEnabled(false);
//			bS7.setEnabled(false);
//			bS8.setEnabled(false);
//			bS9.setEnabled(false);
//			bS10.setEnabled(false);
//		} else if (sub.contains("I")) {
//			bS1.setEnabled(false);
//			bS2.setEnabled(false);
//			bS3.setEnabled(false);
//			bS4.setEnabled(false);
//			bS5.setEnabled(false);
//			bS6.setSelection(true);
//			bS7.setEnabled(false);
//			bS8.setEnabled(false);
//			bS9.setEnabled(false);
//			bS10.setEnabled(false);
//		} else if (sub.contains("L")) {
//			bS1.setEnabled(false);
//			bS2.setEnabled(false);
//			bS3.setEnabled(false);
//			bS4.setEnabled(false);
//			bS5.setEnabled(false);
//			bS6.setEnabled(false);
//			bS7.setSelection(true);
//			bS8.setEnabled(false);
//			bS9.setEnabled(false);
//			bS10.setEnabled(false);
//		} else if (sub.contains("P")) {
//			bS1.setEnabled(false);
//			bS2.setEnabled(false);
//			bS3.setEnabled(false);
//			bS4.setEnabled(false);
//			bS5.setEnabled(false);
//			bS6.setEnabled(false);
//			bS7.setEnabled(false);
//			bS8.setSelection(true);
//			bS9.setEnabled(false);
//			bS10.setEnabled(false);
//		} else if (sub.contains("S")) {
//			bS1.setEnabled(false);
//			bS2.setEnabled(false);
//			bS3.setEnabled(false);
//			bS4.setEnabled(false);
//			bS5.setEnabled(false);
//			bS6.setEnabled(false);
//			bS7.setEnabled(false);
//			bS8.setEnabled(false);
//			bS9.setSelection(true);
//			bS10.setEnabled(false);
//		} else if (sub.contains("X")) {
//			bS1.setEnabled(false);
//			bS2.setEnabled(false);
//			bS3.setEnabled(false);
//			bS4.setEnabled(false);
//			bS5.setEnabled(false);
//			bS6.setEnabled(false);
//			bS7.setEnabled(false);
//			bS8.setEnabled(false);
//			bS9.setEnabled(false);
//			bS10.setSelection(true);
//		}
//		//--
////		else {
////			if (sub.contains("A")) bS2.setSelection(true);
////			if (sub.contains("C")) bS3.setSelection(true);
////			if (sub.contains("E")) bS4.setSelection(true);
////			if (sub.contains("H")) bS5.setSelection(true);
////			if (sub.contains("I")) bS6.setSelection(true);
////			if (sub.contains("L")) bS7.setSelection(true);
////			if (sub.contains("P")) bS8.setSelection(true);
////			if (sub.contains("S")) bS9.setSelection(true);
////			if (sub.contains("X")) bS10.setSelection(true);
//		if (sub.contains("B1")) bS11.setSelection(true);
//		if (sub.contains("B2")) bS12.setSelection(true);
//		if (sub.contains("U1")) bS13.setSelection(true);
//		if (sub.contains("U2")) bS14.setSelection(true);
//		if (sub.contains("V1")) bS15.setSelection(true);
//		if (sub.contains("V2")) bS16.setSelection(true);
//		if (sub.contains("D1")) bS17.setSelection(true);
//		if (sub.contains("G1")) bS18.setSelection(true);
//		
//		//041011
//		if (sub.contains("B1") || sub.contains("U1") || sub.contains("V1")) {
//			bS12.setEnabled(false);
//			bS14.setEnabled(false);
//			bS16.setEnabled(false);
//		}
//		else if (sub.contains("B2") || sub.contains("U2") || sub.contains("V2")) {
//			bS11.setEnabled(false);
//			bS13.setEnabled(false);
//			bS15.setEnabled(false);
//		}
////		}
////	} //end of for sub
//	
////		if (bS1.getSelection()==true) {
////			bS2.setEnabled(false);
////			bS3.setEnabled(false);
////			bS4.setEnabled(false);
////			bS5.setEnabled(false);
////			bS6.setEnabled(false);
////			bS7.setEnabled(false);
////			bS8.setEnabled(false);
////			bS9.setEnabled(false);
////			bS10.setEnabled(false);
////			bS11.setEnabled(false);
////			bS12.setEnabled(false);
////			bS13.setEnabled(false);
////			bS14.setEnabled(false);
////			bS15.setEnabled(false);
////			bS16.setEnabled(false);
////			bS17.setEnabled(false);
////			bS18.setEnabled(false);
////		} else if (bS1.getSelection()==false) {
////			bS2.setEnabled(true);
////			bS3.setEnabled(true);
////			bS4.setEnabled(true);
////			bS5.setEnabled(true);
////			bS6.setEnabled(true);
////			bS7.setEnabled(true);
////			bS8.setEnabled(true);
////			bS9.setEnabled(true);
////			bS10.setEnabled(true);
////			bS11.setEnabled(true);
////			bS12.setEnabled(true);
////			bS13.setEnabled(true);
////			bS14.setEnabled(true);
////			bS15.setEnabled(true);
////			bS16.setEnabled(true);
////			bS17.setEnabled(true);
////			bS18.setEnabled(true);
////		}
//		
//	//ToolTipText
//	bS1.setToolTipText("N: if no surveillance equipment for the route to be flown is carried, or the equipment is unserviceable.");
//	
//	bS2.setToolTipText("A: Transponder - Mode A (4 digits - 4096 codes)");
//	bS3.setToolTipText("C: Transponder - Mode A (4 digits - 4096 codes) and Mode C");
//
//	bS4.setToolTipText("E: Transponder - Mode S, including aircraft identification, pressure-altitude and extended squitter (ADS-B) capability");
//	bS5.setToolTipText("H: Transponder - Mode S, including aircraft identification, pressure-altitude and enhanced surveillance capability");
//	bS6.setToolTipText("I: Transponder - Mode S, including aircraft identification, but no pressure-altitude capability");
//	bS7.setToolTipText("L: Transponder - Mode S, including aircraft identification, pressure-altitude, extended squitter (ADS-B) and enhanced surveillance capability");
//	bS8.setToolTipText("P: Transponder - Mode S, including pressure-altitude, but no aircraft identification capability");
//	bS9.setToolTipText("S: Transponder - Mode S, including both pressure-altitude and aircraft identification capability");
//	bS10.setToolTipText("X: Transponder - Mode S, with neither aircraft identification nor pressure-altitude capability");
//	
//	bS11.setToolTipText("B1: ADS-B with dedicated 1090 MHz ADS-B 'out' capability");
//	bS12.setToolTipText("B2: ADS-B with dedicated 1090 MHz ADS-B 'out' and 'in' capability");
//	bS13.setToolTipText("U1: ADS-B 'out' capability using UAT");
//	bS14.setToolTipText("U2: ADS-B 'out' and 'in' capability using UAT");
//	bS15.setToolTipText("V1: ADS-B 'out' capability using VDL Mode 4");
//	bS16.setToolTipText("V2: ADS-B 'out' and 'in' capability using VDL Mode 4");
//	
//	bS17.setToolTipText("D1: ADS-C with FANS 1/A capabilities");
//	bS18.setToolTipText("G1: ADS-C with ATN capabilities");
//	
//	bS1.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS1.getSelection()==true) {
//				bS2.setEnabled(false);
//				bS3.setEnabled(false);
//				bS4.setEnabled(false);
//				bS5.setEnabled(false);
//				bS6.setEnabled(false);
//				bS7.setEnabled(false);
//				bS8.setEnabled(false);
//				bS9.setEnabled(false);
//				bS10.setEnabled(false);
//				bS11.setEnabled(false);
//				bS12.setEnabled(false);
//				bS13.setEnabled(false);
//				bS14.setEnabled(false);
//				bS15.setEnabled(false);
//				bS16.setEnabled(false);
//				bS17.setEnabled(false);
//				bS18.setEnabled(false);
//			}
//			else if (bS1.getSelection()==false) {
//				bS2.setEnabled(true);
//				bS3.setEnabled(true);
//				bS4.setEnabled(true);
//				bS5.setEnabled(true);
//				bS6.setEnabled(true);
//				bS7.setEnabled(true);
//				bS8.setEnabled(true);
//				bS9.setEnabled(true);
//				bS10.setEnabled(true);
//				bS11.setEnabled(true);
//				bS12.setEnabled(true);
//				bS13.setEnabled(true);
//				bS14.setEnabled(true);
//				bS15.setEnabled(true);
//				bS16.setEnabled(true);
//				bS17.setEnabled(true);
//				bS18.setEnabled(true);
//			}
//		}
//	});
//	//percobaan
//	bS2.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS2.getSelection()==true) {
//				bS1.setEnabled(false);
//				bS3.setEnabled(false);
//				bS4.setEnabled(false);
//				bS5.setEnabled(false);
//				bS6.setEnabled(false);
//				bS7.setEnabled(false);
//				bS8.setEnabled(false);
//				bS9.setEnabled(false);
//				bS10.setEnabled(false);
//			} else if (bS2.getSelection()==false) {
//				bS1.setEnabled(true);
//				bS3.setEnabled(true);
//				bS4.setEnabled(true);
//				bS5.setEnabled(true);
//				bS6.setEnabled(true);
//				bS7.setEnabled(true);
//				bS8.setEnabled(true);
//				bS9.setEnabled(true);
//				bS10.setEnabled(true);
//			}
//		}
//	});
//	bS3.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS3.getSelection()==true) {
//				bS1.setEnabled(false);
//				bS2.setEnabled(false);
//				bS4.setEnabled(false);
//				bS5.setEnabled(false);
//				bS6.setEnabled(false);
//				bS7.setEnabled(false);
//				bS8.setEnabled(false);
//				bS9.setEnabled(false);
//				bS10.setEnabled(false);
//			} else if (bS3.getSelection()==false) {
//				bS1.setEnabled(true);
//				bS2.setEnabled(true);
//				bS4.setEnabled(true);
//				bS5.setEnabled(true);
//				bS6.setEnabled(true);
//				bS7.setEnabled(true);
//				bS8.setEnabled(true);
//				bS9.setEnabled(true);
//				bS10.setEnabled(true);
//			}
//		}
//	});
//	bS4.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS4.getSelection()==true) {
//				bS1.setEnabled(false);
//				bS2.setEnabled(false);
//				bS3.setEnabled(false);
//				bS5.setEnabled(false);
//				bS6.setEnabled(false);
//				bS7.setEnabled(false);
//				bS8.setEnabled(false);
//				bS9.setEnabled(false);
//				bS10.setEnabled(false);
//			} else if (bS4.getSelection()==false) {
//				bS1.setEnabled(true);
//				bS2.setEnabled(true);
//				bS3.setEnabled(true);
//				bS5.setEnabled(true);
//				bS6.setEnabled(true);
//				bS7.setEnabled(true);
//				bS8.setEnabled(true);
//				bS9.setEnabled(true);
//				bS10.setEnabled(true);
//			} 
//		}
//	});
//	bS5.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS5.getSelection()==true) {
//				bS1.setEnabled(false);
//				bS2.setEnabled(false);
//				bS3.setEnabled(false);
//				bS4.setEnabled(false);
//				bS6.setEnabled(false);
//				bS7.setEnabled(false);
//				bS8.setEnabled(false);
//				bS9.setEnabled(false);
//				bS10.setEnabled(false);
//			} else if (bS5.getSelection()==false) {
//				bS1.setEnabled(true);
//				bS2.setEnabled(true);
//				bS3.setEnabled(true);
//				bS4.setEnabled(true);
//				bS6.setEnabled(true);
//				bS7.setEnabled(true);
//				bS8.setEnabled(true);
//				bS9.setEnabled(true);
//				bS10.setEnabled(true);
//			}
//		}
//	});
//	bS6.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS6.getSelection()==true) {
//				bS1.setEnabled(false);
//				bS2.setEnabled(false);
//				bS3.setEnabled(false);
//				bS4.setEnabled(false);
//				bS5.setEnabled(false);
//				bS7.setEnabled(false);
//				bS8.setEnabled(false);
//				bS9.setEnabled(false);
//				bS10.setEnabled(false);
//			} else if (bS6.getSelection()==false) {
//				bS1.setEnabled(true);
//				bS2.setEnabled(true);
//				bS3.setEnabled(true);
//				bS4.setEnabled(true);
//				bS5.setEnabled(true);
//				bS7.setEnabled(true);
//				bS8.setEnabled(true);
//				bS9.setEnabled(true);
//				bS10.setEnabled(true);
//			} 
//		}
//	});
//	bS7.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS7.getSelection()==true) {
//				bS1.setEnabled(false);
//				bS2.setEnabled(false);
//				bS3.setEnabled(false);
//				bS4.setEnabled(false);
//				bS5.setEnabled(false);
//				bS6.setEnabled(false);
//				bS8.setEnabled(false);
//				bS9.setEnabled(false);
//				bS10.setEnabled(false);
//			} else if (bS7.getSelection()==false) {
//				bS1.setEnabled(true);
//				bS2.setEnabled(true);
//				bS3.setEnabled(true);
//				bS4.setEnabled(true);
//				bS5.setEnabled(true);
//				bS6.setEnabled(true);
//				bS8.setEnabled(true);
//				bS9.setEnabled(true);
//				bS10.setEnabled(true);
//			}
//		}
//	});
//	bS8.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS8.getSelection()==true) {
//				bS1.setEnabled(false);
//				bS2.setEnabled(false);
//				bS3.setEnabled(false);
//				bS4.setEnabled(false);
//				bS5.setEnabled(false);
//				bS6.setEnabled(false);
//				bS7.setEnabled(false);
//				bS9.setEnabled(false);
//				bS10.setEnabled(false);
//			} else if (bS8.getSelection()==false) {
//				bS1.setEnabled(true);
//				bS2.setEnabled(true);
//				bS3.setEnabled(true);
//				bS4.setEnabled(true);
//				bS5.setEnabled(true);
//				bS6.setEnabled(true);
//				bS7.setEnabled(true);
//				bS9.setEnabled(true);
//				bS10.setEnabled(true);
//			} 
//		}
//	});
//	bS9.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS9.getSelection()==true) {
//				bS1.setEnabled(false);
//				bS2.setEnabled(false);
//				bS3.setEnabled(false);
//				bS4.setEnabled(false);
//				bS5.setEnabled(false);
//				bS6.setEnabled(false);
//				bS7.setEnabled(false);
//				bS8.setEnabled(false);
//				bS10.setEnabled(false);
//			} else if (bS9.getSelection()==false) {
//				bS1.setEnabled(true);
//				bS2.setEnabled(true);
//				bS3.setEnabled(true);
//				bS4.setEnabled(true);
//				bS5.setEnabled(true);
//				bS6.setEnabled(true);
//				bS7.setEnabled(true);
//				bS8.setEnabled(true);
//				bS10.setEnabled(true);
//			}
//		}
//	});
//	bS10.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS10.getSelection()==true) {
//				bS1.setEnabled(false);
//				bS2.setEnabled(false);
//				bS3.setEnabled(false);
//				bS4.setEnabled(false);
//				bS5.setEnabled(false);
//				bS6.setEnabled(false);
//				bS7.setEnabled(false);
//				bS8.setEnabled(false);
//				bS9.setEnabled(false);
//			} else if (bS10.getSelection()==false) {
//				bS1.setEnabled(true);
//				bS2.setEnabled(true);
//				bS3.setEnabled(true);
//				bS4.setEnabled(true);
//				bS5.setEnabled(true);
//				bS6.setEnabled(true);
//				bS7.setEnabled(true);
//				bS8.setEnabled(true);
//				bS9.setEnabled(true);
//			} 
//		}
//	});
//	//041011
//	bS11.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS11.getSelection()==true || bS13.getSelection()==true || bS15.getSelection()==true) {
//				bS12.setEnabled(false);
//				bS14.setEnabled(false);
//				bS16.setEnabled(false);
//			} else if (bS11.getSelection()==false || bS13.getSelection()==false || bS15.getSelection()==false) {
//				bS12.setEnabled(true);
//				bS14.setEnabled(true);
//				bS16.setEnabled(true);
//			} 
//		}
//	});
//	bS13.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS11.getSelection()==true || bS13.getSelection()==true || bS15.getSelection()==true) {
//				bS12.setEnabled(false);
//				bS14.setEnabled(false);
//				bS16.setEnabled(false);
//			} else if (bS11.getSelection()==false || bS13.getSelection()==false || bS15.getSelection()==false) {
//				bS12.setEnabled(true);
//				bS14.setEnabled(true);
//				bS16.setEnabled(true);
//			} 
//		}
//	});
//	bS15.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS11.getSelection()==true || bS13.getSelection()==true || bS15.getSelection()==true) {
//				bS12.setEnabled(false);
//				bS14.setEnabled(false);
//				bS16.setEnabled(false);
//			} else if (bS11.getSelection()==false || bS13.getSelection()==false || bS15.getSelection()==false) {
//				bS12.setEnabled(true);
//				bS14.setEnabled(true);
//				bS16.setEnabled(true);
//			} 
//		}
//	});
//	//---------------------------------------------------
//	bS12.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS12.getSelection()==true || bS14.getSelection()==true || bS16.getSelection()==true) {
//				bS11.setEnabled(false);
//				bS13.setEnabled(false);
//				bS15.setEnabled(false);
//			} else if (bS12.getSelection()==false || bS14.getSelection()==false || bS16.getSelection()==false) {
//				bS11.setEnabled(true);
//				bS13.setEnabled(true);
//				bS15.setEnabled(true);
//			} 
//		}
//	});
//	bS14.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS12.getSelection()==true || bS14.getSelection()==true || bS16.getSelection()==true) {
//				bS11.setEnabled(false);
//				bS13.setEnabled(false);
//				bS15.setEnabled(false);
//			} else if (bS12.getSelection()==false || bS14.getSelection()==false || bS16.getSelection()==false) {
//				bS11.setEnabled(true);
//				bS13.setEnabled(true);
//				bS15.setEnabled(true);
//			} 
//		}
//	});
//	bS16.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS12.getSelection()==true || bS14.getSelection()==true || bS16.getSelection()==true) {
//				bS11.setEnabled(false);
//				bS13.setEnabled(false);
//				bS15.setEnabled(false);
//			} else if (bS12.getSelection()==false || bS14.getSelection()==false || bS16.getSelection()==false) {
//				bS11.setEnabled(true);
//				bS13.setEnabled(true);
//				bS15.setEnabled(true);
//			} 
//		}
//	});
//	
//	
//	Composite compBtn = new Composite(Group, SWT.NONE); sh.composeStyle(compBtn, 2, SWT.CENTER, SWT.CENTER);
////	GridLayout layBtn = new GridLayout();
////	layBtn.numColumns = 2;
////	compBtn.setLayout(layBtn);
////	GridData gdBtn = new GridData();
////	gdBtn.horizontalAlignment = SWT.CENTER;
////	compBtn.setLayoutData(gdBtn);
//	
//	Button Input = new Button(compBtn, SWT.PUSH);
//	Input.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			String s1="",s2="",s3="",s4="",s5="",s6="",s7="",s8="",s9="",s10="",s11="",s12="",s13="",s14="",
//			s15="",s16="",s17="",s18="",all="";
//			
////			if (bS1.getSelection() == true) s1=bS1.getText(); if (s1.isEmpty()) s1="";
////			if (bS2.getSelection() == true) s2=bS2.getText(); if (s2.isEmpty()) s2="";
////			if (bS3.getSelection() == true) s3=bS3.getText(); if (s3.isEmpty()) s3="";
////			if (bS4.getSelection() == true) s4=bS4.getText(); if (s4.isEmpty()) s4="";
////			if (bS5.getSelection() == true) s5=bS5.getText(); if (s5.isEmpty()) s5="";
////			if (bS6.getSelection() == true) s6=bS6.getText(); if (s6.isEmpty()) s6="";
////			if (bS7.getSelection() == true) s7=bS7.getText(); if (s7.isEmpty()) s7="";
////			if (bS8.getSelection() == true) s8=bS8.getText(); if (s8.isEmpty()) s8="";
////			if (bS9.getSelection() == true) s9=bS9.getText(); if (s9.isEmpty()) s9="";
////			if (bS10.getSelection() == true) s10=bS10.getText(); if (s10.isEmpty()) s10="";
////			if (bS11.getSelection() == true) s11=bS11.getText(); if (s11.isEmpty()) s11="";
////			if (bS12.getSelection() == true) s12=bS12.getText(); if (s12.isEmpty()) s12="";
////			if (bS13.getSelection() == true) s13=bS13.getText(); if (s13.isEmpty()) s13="";
////			if (bS14.getSelection() == true) s14=bS14.getText(); if (s14.isEmpty()) s14="";
////			if (bS15.getSelection() == true) s15=bS15.getText(); if (s15.isEmpty()) s15="";
////			if (bS16.getSelection() == true) s16=bS16.getText(); if (s16.isEmpty()) s16="";
////			if (bS17.getSelection() == true) s17=bS17.getText(); if (s17.isEmpty()) s17="";
////			if (bS18.getSelection() == true) s18=bS18.getText(); if (s18.isEmpty()) s18="";
//			
////			if (bS1.getSelection()==true) {
////				s1=bS1.getText(); s1=bS1.getText(); if (s1.isEmpty()) s1="";
////				s2="";//bS2.getText();
////				s3="";//bS3.getText();
////				s4="";//bS4.getText();
////				s5="";//bS5.getText();
////				s6="";//bS6.getText();
////				s7="";//bS7.getText();
////				s8="";//bS8.getText();
////				s9="";//bS9.getText();
////				s10="";//bS10.getText();
////				s11="";//bS11.getText();
////				s12="";//bS12.getText();
////				s13="";//bS13.getText();
////				s14="";//bS14.getText();
////				s15="";//bS15.getText();
////				s16="";//bS16.getText();
////				s17="";//bS17.getText();
////				s18="";//bS18.getText();
////			} else if (bS1.getSelection()==false) {
////				s1="";//
////				if (bS2.getSelection() == true) s2=bS2.getText(); if (s2.isEmpty()) s2="";
////				if (bS3.getSelection() == true) s3=bS3.getText(); if (s3.isEmpty()) s3="";
////				if (bS4.getSelection() == true) s4=bS4.getText(); if (s4.isEmpty()) s4="";
////				if (bS5.getSelection() == true) s5=bS5.getText(); if (s5.isEmpty()) s5="";
////				if (bS6.getSelection() == true) s6=bS6.getText(); if (s6.isEmpty()) s6="";
////				if (bS7.getSelection() == true) s7=bS7.getText(); if (s7.isEmpty()) s7="";
////				if (bS8.getSelection() == true) s8=bS8.getText(); if (s8.isEmpty()) s8="";
////				if (bS9.getSelection() == true) s9=bS9.getText(); if (s9.isEmpty()) s9="";
////				if (bS10.getSelection() == true) s10=bS10.getText(); if (s10.isEmpty()) s10="";
////				if (bS11.getSelection() == true) s11=bS11.getText(); if (s11.isEmpty()) s11="";
////				if (bS12.getSelection() == true) s12=bS12.getText(); if (s12.isEmpty()) s12="";
////				if (bS13.getSelection() == true) s13=bS13.getText(); if (s13.isEmpty()) s13="";
////				if (bS14.getSelection() == true) s14=bS14.getText(); if (s14.isEmpty()) s14="";
////				if (bS15.getSelection() == true) s15=bS15.getText(); if (s15.isEmpty()) s15="";
////				if (bS16.getSelection() == true) s16=bS16.getText(); if (s16.isEmpty()) s16="";
////				if (bS17.getSelection() == true) s17=bS17.getText(); if (s17.isEmpty()) s17="";
////				if (bS18.getSelection() == true) s18=bS18.getText(); if (s18.isEmpty()) s18="";
////			}
//			
//			
//			//percobaan
//			if (bS1.getSelection() == true) s1=bS1.getText(); if (s1.isEmpty()) s1="";
//			if (bS2.getSelection() == true) s2=bS2.getText(); if (s2.isEmpty()) s2="";
//			if (bS3.getSelection() == true) s3=bS3.getText(); if (s3.isEmpty()) s3="";
//			if (bS4.getSelection() == true) s4=bS4.getText(); if (s4.isEmpty()) s4="";
//			if (bS5.getSelection() == true) s5=bS5.getText(); if (s5.isEmpty()) s5="";
//			if (bS6.getSelection() == true) s6=bS6.getText(); if (s6.isEmpty()) s6="";
//			if (bS7.getSelection() == true) s7=bS7.getText(); if (s7.isEmpty()) s7="";
//			if (bS8.getSelection() == true) s8=bS8.getText(); if (s8.isEmpty()) s8="";
//			if (bS9.getSelection() == true) s9=bS9.getText(); if (s9.isEmpty()) s9="";
//			if (bS10.getSelection() == true) s10=bS10.getText(); if (s10.isEmpty()) s10="";
//			if (bS11.getSelection() == true) s11=bS11.getText(); if (s11.isEmpty()) s11="";
//			if (bS12.getSelection() == true) s12=bS12.getText(); if (s12.isEmpty()) s12="";
//			if (bS13.getSelection() == true) s13=bS13.getText(); if (s13.isEmpty()) s13="";
//			if (bS14.getSelection() == true) s14=bS14.getText(); if (s14.isEmpty()) s14="";
//			if (bS15.getSelection() == true) s15=bS15.getText(); if (s15.isEmpty()) s15="";
//			if (bS16.getSelection() == true) s16=bS16.getText(); if (s16.isEmpty()) s16="";
//			if (bS17.getSelection() == true) s17=bS17.getText(); if (s17.isEmpty()) s17="";
//			if (bS18.getSelection() == true) s18=bS18.getText(); if (s18.isEmpty()) s18="";
//			
//			if (bS1.getSelection()==true) { s2="";s3="";s4="";s5="";s6="";s7="";s8="";s9="";s10="";s11="";s12="";s13="";s14="";s15="";s16="";s17="";s18=""; } 
//			//--percobaan
//			if (bS2.getSelection()==true) { s1="";s3="";s4="";s5="";s6="";s7="";s8="";s9="";s10=""; } 
//			if (bS3.getSelection()==true) { s1="";s2="";s4="";s5="";s6="";s7="";s8="";s9="";s10=""; } 
//			if (bS4.getSelection()==true) { s1="";s2="";s3="";s5="";s6="";s7="";s8="";s9="";s10=""; } 
//			if (bS5.getSelection()==true) { s1="";s2="";s3="";s4="";s6="";s7="";s8="";s9="";s10=""; } 
//			if (bS6.getSelection()==true) { s1="";s2="";s3="";s4="";s5="";s7="";s8="";s9="";s10=""; } 
//			if (bS7.getSelection()==true) { s1="";s2="";s3="";s4="";s5="";s6="";s8="";s9="";s10=""; }
//			if (bS8.getSelection()==true) { s1="";s2="";s3="";s4="";s5="";s6="";s7="";s9="";s10=""; } 
//			if (bS9.getSelection()==true) { s1="";s2="";s3="";s4="";s5="";s6="";s7="";s8="";s10=""; } 
//			if (bS10.getSelection()==true) { s1="";s2="";s3="";s4="";s5="";s6="";s7="";s8="";s9=""; }
//			
//			//percobaan baru
//			if (bS11.getSelection()==true || bS13.getSelection()==true || bS15.getSelection()==true) { s12="";s14="";s16=""; }
//			else if (bS12.getSelection()==true || bS14.getSelection()==true || bS16.getSelection()==true) { s11="";s13="";s15=""; }
//			
//			all=s1+s2+s3+s4+s5+s6+s7+s8+s9+s10+s11+s12+s13+s14+s15+s16+s17+s18;
//			t.setText(all);
//			shell.close();
//		}
//	});
//	
//	Button Cancel = new Button(compBtn, SWT.PUSH);
//	Cancel.addSelectionListener(new SelectionAdapter() {
//		public void widgetSelected(SelectionEvent e) {
//			if (bS1.getSelection() == true) bS1.setSelection(false); 
//			if (bS2.getSelection() == true) bS2.setSelection(false);
//			if (bS3.getSelection() == true) bS3.setSelection(false);
//			if (bS4.getSelection() == true) bS4.setSelection(false);
//			if (bS5.getSelection() == true) bS5.setSelection(false);
//			if (bS6.getSelection() == true) bS6.setSelection(false);
//			if (bS7.getSelection() == true) bS7.setSelection(false);
//			if (bS8.getSelection() == true) bS8.setSelection(false);
//			if (bS9.getSelection() == true) bS9.setSelection(false);
//			if (bS10.getSelection() == true) bS10.setSelection(false);
//			if (bS11.getSelection() == true) bS11.setSelection(false);
//			if (bS12.getSelection() == true) bS12.setSelection(false);
//			if (bS13.getSelection() == true) bS13.setSelection(false);
//			if (bS14.getSelection() == true) bS14.setSelection(false);
//			if (bS15.getSelection() == true) bS15.setSelection(false);
//			if (bS16.getSelection() == true) bS16.setSelection(false);
//			if (bS17.getSelection() == true) bS17.setSelection(false);
//			if (bS18.getSelection() == true) bS18.setSelection(false);
//			shell.close();
//		}
//	});
//	bs.InputCancel(Input, Cancel);
//
//	//if (Colors.NORMAL!=null) Colors.NORMAL.dispose();
//}