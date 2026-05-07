package databases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import readwrite.ReadFromFile;
import setting.Shells;
import setting.Times;

import displays.DialogFactory;
import displays.MainForm;
import displays.forms.ATSForms;
import displays.forms.HeaderComposite;


public class Auto {

	static Times time = new Times();
	
	private static Connection conn = null; // connection object
    private static Statement stmt = null; // statement object
    private static ResultSet rs = null; // result set object
    public static int jumlah=0,p=0,rowNo=0,baris=1,qq=0;
    public static String isiHal="",tblName="";

    
    
    public static String akhir() { return isiHal; }
	public static int getJumlah() { return jumlah; }
	
	public static void koneksiDB_ROUTE(String ID, String query) {
		System.out.println("\nRoute Query: " + query);
		try {
			try { conn = jdbc.getDBConnection(); } 
			catch (Exception e) { e.printStackTrace(); }
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.last();
			jumlah = rs.getRow();
			System.out.println("Jumlah route:" + jumlah);
			rs.beforeFirst();

			p=0;
			rowNo=0;
			while (rs.next() && (rowNo < baris)) {
				rowNo++;

				String t13a="",t16a="",t15c="",des1="",des2="",des3="",des4="",des5="",des6="",des7="",des8="",des9="",des10="",des11="",des12="",des13="",des14="",des15="",des16="",des17="",des18="",des19="",des20="",des21="";
				t13a = rs.getString("type13a"); if (t13a == null) t13a="";
				t16a = rs.getString("type16a"); if (t16a == null) t16a="";
				t15c = rs.getString("type15c"); if (t15c == null) t15c="";
				des1 = rs.getString("destination1"); if (des1 == null) des1="";
				des2 = rs.getString("destination2"); if (des2 == null) des2="";
				des3 = rs.getString("destination3"); if (des3 == null) des3="";
				des4 = rs.getString("destination4"); if (des4 == null) des4="";
				des5 = rs.getString("destination5"); if (des5 == null) des5="";
				des6 = rs.getString("destination6"); if (des6 == null) des6="";
				des7 = rs.getString("destination7"); if (des7 == null) des7="";
				des8 = rs.getString("destination8"); if (des8 == null) des8="";
				des9 = rs.getString("destination9"); if (des9 == null) des9="";
				des10 = rs.getString("destination10"); if (des10 == null) des10="";
				des11 = rs.getString("destination11"); if (des11 == null) des11="";
				des12 = rs.getString("destination12"); if (des12 == null) des12="";
				des13 = rs.getString("destination13"); if (des13 == null) des13="";
				des14 = rs.getString("destination14"); if (des14 == null) des14="";
				des15 = rs.getString("destination15"); if (des15 == null) des15="";
				des16 = rs.getString("destination16"); if (des16 == null) des16="";
				des17 = rs.getString("destination17"); if (des17 == null) des17="";
				des18 = rs.getString("destination18"); if (des18 == null) des18="";
				des19 = rs.getString("destination19"); if (des19 == null) des19="";
				des20 = rs.getString("destination20"); if (des20 == null) des20="";
				des21 = rs.getString("destination21"); if (des21 == null) des21="";

				if (ID.endsWith("ALR")) {
					// BODY
					ATSForms.t13aALR.setText(t13a);
					ATSForms.t16aALR.setText(t16a);
					ATSForms.t15cALR.setText(t15c);
					// HEADER
					HeaderComposite.tDest1ALR.setText(des1);
					HeaderComposite.tDest2ALR.setText(des2);
					HeaderComposite.tDest3ALR.setText(des3);
					HeaderComposite.tDest4ALR.setText(des4);
					HeaderComposite.tDest5ALR.setText(des5);
					HeaderComposite.tDest6ALR.setText(des6);
					HeaderComposite.tDest7ALR.setText(des7);
					HeaderComposite.tDest8ALR.setText(des8);
					HeaderComposite.tDest9ALR.setText(des9);
					HeaderComposite.tDest10ALR.setText(des10);
					HeaderComposite.tDest11ALR.setText(des11);
					HeaderComposite.tDest12ALR.setText(des12);
					HeaderComposite.tDest13ALR.setText(des13);
					HeaderComposite.tDest14ALR.setText(des14);
					HeaderComposite.tDest15ALR.setText(des15);
					HeaderComposite.tDest16ALR.setText(des16);
					HeaderComposite.tDest17ALR.setText(des17);
					HeaderComposite.tDest18ALR.setText(des18);
					HeaderComposite.tDest19ALR.setText(des19);
					HeaderComposite.tDest20ALR.setText(des20);
					HeaderComposite.tDest21ALR.setText(des21);
				} else if (ID.endsWith("FPL")) {
					// BODY
					ATSForms.t13aFPL.setText(t13a);
					ATSForms.t16aFPL.setText(t16a);
					ATSForms.t15cFPL.setText(t15c);
					// HEADER
					HeaderComposite.tDest1FPL.setText(des1);
					HeaderComposite.tDest2FPL.setText(des2);
					HeaderComposite.tDest3FPL.setText(des3);
					HeaderComposite.tDest4FPL.setText(des4);
					HeaderComposite.tDest5FPL.setText(des5);
					HeaderComposite.tDest6FPL.setText(des6);
					HeaderComposite.tDest7FPL.setText(des7);
					HeaderComposite.tDest8FPL.setText(des8);
					HeaderComposite.tDest9FPL.setText(des9);
					HeaderComposite.tDest10FPL.setText(des10);
					HeaderComposite.tDest11FPL.setText(des11);
					HeaderComposite.tDest12FPL.setText(des12);
					HeaderComposite.tDest13FPL.setText(des13);
					HeaderComposite.tDest14FPL.setText(des14);
					HeaderComposite.tDest15FPL.setText(des15);
					HeaderComposite.tDest16FPL.setText(des16);
					HeaderComposite.tDest17FPL.setText(des17);
					HeaderComposite.tDest18FPL.setText(des18);
					HeaderComposite.tDest19FPL.setText(des19);
					HeaderComposite.tDest20FPL.setText(des20);
					HeaderComposite.tDest21FPL.setText(des21);
				} else if (ID.endsWith("CPL")) {
					// BODY
					ATSForms.t13aCPL.setText(t13a);
					ATSForms.t16aCPL.setText(t16a);
					ATSForms.t15cCPL.setText(t15c);
					// HEADER
					HeaderComposite.tDest1CPL.setText(des1);
					HeaderComposite.tDest2CPL.setText(des2);
					HeaderComposite.tDest3CPL.setText(des3);
					HeaderComposite.tDest4CPL.setText(des4);
					HeaderComposite.tDest5CPL.setText(des5);
					HeaderComposite.tDest6CPL.setText(des6);
					HeaderComposite.tDest7CPL.setText(des7);
					HeaderComposite.tDest8CPL.setText(des8);
					HeaderComposite.tDest9CPL.setText(des9);
					HeaderComposite.tDest10CPL.setText(des10);
					HeaderComposite.tDest11CPL.setText(des11);
					HeaderComposite.tDest12CPL.setText(des12);
					HeaderComposite.tDest13CPL.setText(des13);
					HeaderComposite.tDest14CPL.setText(des14);
					HeaderComposite.tDest15CPL.setText(des15);
					HeaderComposite.tDest16CPL.setText(des16);
					HeaderComposite.tDest17CPL.setText(des17);
					HeaderComposite.tDest18CPL.setText(des18);
					HeaderComposite.tDest19CPL.setText(des19);
					HeaderComposite.tDest20CPL.setText(des20);
					HeaderComposite.tDest21CPL.setText(des21);
				}
				p++;
			}
		} catch (SQLException s) {
			s.printStackTrace();
		} catch (java.lang.OutOfMemoryError hs) {
			if (ID.endsWith("ALR")) DialogFactory.openInfoDialog(Shells.sh_nALR, "Get Route", "Out of memory !!\n Please fill another criteria search !! ");
			if (ID.endsWith("FPL")) DialogFactory.openInfoDialog(Shells.sh_nFPL, "Get Route", "Out of memory !!\n Please fill another criteria search !! ");
			if (ID.endsWith("CPL")) DialogFactory.openInfoDialog(Shells.sh_nCPL, "Get Route", "Out of memory !!\n Please fill another criteria search !! ");
		}
	}

	public static void Next_ROUTE(String ID) {
		try {
			rs.last();
			jumlah = rs.getRow();
			rs.beforeFirst();

			for (rowNo=0; rowNo<(qq*baris); rowNo++) rs.next();
			p=0;
			rowNo=0;
			while (rs.next() && (rowNo < baris)) {
				rowNo++;

				String t13a="",t16a="",t15c="",des1="",des2="",des3="",des4="",des5="",des6="",des7="",des8="",des9="",des10="",des11="",des12="",des13="",des14="",des15="",des16="",des17="",des18="",des19="",des20="",des21="";
				t13a = rs.getString("type13a"); if (t13a == null) t13a="";
				t16a = rs.getString("type16a"); if (t16a == null) t16a="";
				t15c = rs.getString("type15c"); if (t15c == null) t15c="";
				des1 = rs.getString("destination1"); if (des1 == null) des1="";
				des2 = rs.getString("destination2"); if (des2 == null) des2="";
				des3 = rs.getString("destination3"); if (des3 == null) des3="";
				des4 = rs.getString("destination4"); if (des4 == null) des4="";
				des5 = rs.getString("destination5"); if (des5 == null) des5="";
				des6 = rs.getString("destination6"); if (des6 == null) des6="";
				des7 = rs.getString("destination7"); if (des7 == null) des7="";
				des8 = rs.getString("destination8"); if (des8 == null) des8="";
				des9 = rs.getString("destination9"); if (des9 == null) des9="";
				des10 = rs.getString("destination10"); if (des10 == null) des10="";
				des11 = rs.getString("destination11"); if (des11 == null) des11="";
				des12 = rs.getString("destination12"); if (des12 == null) des12="";
				des13 = rs.getString("destination13"); if (des13 == null) des13="";
				des14 = rs.getString("destination14"); if (des14 == null) des14="";
				des15 = rs.getString("destination15"); if (des15 == null) des15="";
				des16 = rs.getString("destination16"); if (des16 == null) des16="";
				des17 = rs.getString("destination17"); if (des17 == null) des17="";
				des18 = rs.getString("destination18"); if (des18 == null) des18="";
				des19 = rs.getString("destination19"); if (des19 == null) des19="";
				des20 = rs.getString("destination20"); if (des20 == null) des20="";
				des21 = rs.getString("destination21"); if (des21 == null) des21="";

				if (ID.endsWith("ALR")) {
					// BODY
					ATSForms.t13aALR.setText(t13a);
					ATSForms.t16aALR.setText(t16a);
					ATSForms.t15cALR.setText(t15c);
					// HEADER
					HeaderComposite.tDest1ALR.setText(des1);
					HeaderComposite.tDest2ALR.setText(des2);
					HeaderComposite.tDest3ALR.setText(des3);
					HeaderComposite.tDest4ALR.setText(des4);
					HeaderComposite.tDest5ALR.setText(des5);
					HeaderComposite.tDest6ALR.setText(des6);
					HeaderComposite.tDest7ALR.setText(des7);
					HeaderComposite.tDest8ALR.setText(des8);
					HeaderComposite.tDest9ALR.setText(des9);
					HeaderComposite.tDest10ALR.setText(des10);
					HeaderComposite.tDest11ALR.setText(des11);
					HeaderComposite.tDest12ALR.setText(des12);
					HeaderComposite.tDest13ALR.setText(des13);
					HeaderComposite.tDest14ALR.setText(des14);
					HeaderComposite.tDest15ALR.setText(des15);
					HeaderComposite.tDest16ALR.setText(des16);
					HeaderComposite.tDest17ALR.setText(des17);
					HeaderComposite.tDest18ALR.setText(des18);
					HeaderComposite.tDest19ALR.setText(des19);
					HeaderComposite.tDest20ALR.setText(des20);
					HeaderComposite.tDest21ALR.setText(des21);
				} else if (ID.endsWith("FPL")) {
					// BODY
					ATSForms.t13aFPL.setText(t13a);
					ATSForms.t16aFPL.setText(t16a);
					ATSForms.t15cFPL.setText(t15c);
					// HEADER
					HeaderComposite.tDest1FPL.setText(des1);
					HeaderComposite.tDest2FPL.setText(des2);
					HeaderComposite.tDest3FPL.setText(des3);
					HeaderComposite.tDest4FPL.setText(des4);
					HeaderComposite.tDest5FPL.setText(des5);
					HeaderComposite.tDest6FPL.setText(des6);
					HeaderComposite.tDest7FPL.setText(des7);
					HeaderComposite.tDest8FPL.setText(des8);
					HeaderComposite.tDest9FPL.setText(des9);
					HeaderComposite.tDest10FPL.setText(des10);
					HeaderComposite.tDest11FPL.setText(des11);
					HeaderComposite.tDest12FPL.setText(des12);
					HeaderComposite.tDest13FPL.setText(des13);
					HeaderComposite.tDest14FPL.setText(des14);
					HeaderComposite.tDest15FPL.setText(des15);
					HeaderComposite.tDest16FPL.setText(des16);
					HeaderComposite.tDest17FPL.setText(des17);
					HeaderComposite.tDest18FPL.setText(des18);
					HeaderComposite.tDest19FPL.setText(des19);
					HeaderComposite.tDest20FPL.setText(des20);
					HeaderComposite.tDest21FPL.setText(des21);
				} else if (ID.endsWith("CPL")) {
					// BODY
					ATSForms.t13aCPL.setText(t13a);
					ATSForms.t16aCPL.setText(t16a);
					ATSForms.t15cCPL.setText(t15c);
					// HEADER
					HeaderComposite.tDest1CPL.setText(des1);
					HeaderComposite.tDest2CPL.setText(des2);
					HeaderComposite.tDest3CPL.setText(des3);
					HeaderComposite.tDest4CPL.setText(des4);
					HeaderComposite.tDest5CPL.setText(des5);
					HeaderComposite.tDest6CPL.setText(des6);
					HeaderComposite.tDest7CPL.setText(des7);
					HeaderComposite.tDest8CPL.setText(des8);
					HeaderComposite.tDest9CPL.setText(des9);
					HeaderComposite.tDest10CPL.setText(des10);
					HeaderComposite.tDest11CPL.setText(des11);
					HeaderComposite.tDest12CPL.setText(des12);
					HeaderComposite.tDest13CPL.setText(des13);
					HeaderComposite.tDest14CPL.setText(des14);
					HeaderComposite.tDest15CPL.setText(des15);
					HeaderComposite.tDest16CPL.setText(des16);
					HeaderComposite.tDest17CPL.setText(des17);
					HeaderComposite.tDest18CPL.setText(des18);
					HeaderComposite.tDest19CPL.setText(des19);
					HeaderComposite.tDest20CPL.setText(des20);
					HeaderComposite.tDest21CPL.setText(des21);
				}
				p++;
			}
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}

	public static void koneksiDB_FPL(String ID, String query) {
		Shell sh = null;
		try {
			time.tgl();
			tblName = "air_message" + time.yearMonth;
			
			if (query.contains("air_message")) query = query.replace("air_message", tblName);
			System.out.println("\nFPL Query: " + query);
			try { conn = jdbc.getDBConnection(); } 
			catch (Exception e) { e.printStackTrace(); }
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.last();
			jumlah = rs.getRow();
			System.out.println("Jumlah FPL=" + jumlah);
			rs.beforeFirst();

			p=0;
			rowNo=0;
			while (rs.next() && (rowNo < baris)) {
				rowNo++;
				
				String des1="",des2="",des3="",des4="",des5="",des6="",des7="",des8="",des9="",des10="",des11="",des12="",des13="",des14="",
				des15="",des16="",des17="",des18="",des19="",des20="",des21="",
				t3a="",t3b="",t3c="",t7a="",t7b="",t7c="",t13a="",t13b="",t16a="",t16b="",t16c="",t16d="",t17a="",t17b="",t17c="",t18="",t22="",
				t19a="",t19b="",t19cUHF="",t19cVHF="",t19cELT="",t19dS="",t19dP="",t19dD="",t19dM="",t19dJ="",t19eJ="",t19eL="",t19eF="",t19eU="",t19eV="",t19fD="",t19f_number="",t19f_capacity="",t19f_cover="",t19f_colour="",t19g="",t19hN="",t19Remarks="",t19i="";
				
				des1 = rs.getString("destination1"); if (des1 == null) des1="";
				des2 = rs.getString("destination2"); if (des2 == null) des2="";
				des3 = rs.getString("destination3"); if (des3 == null) des3="";
				des4 = rs.getString("destination4"); if (des4 == null) des4="";
				des5 = rs.getString("destination5"); if (des5 == null) des5="";
				des6 = rs.getString("destination6"); if (des6 == null) des6="";
				des7 = rs.getString("destination7"); if (des7 == null) des7="";
				des8 = rs.getString("destination8"); if (des8 == null) des8="";
				des9 = rs.getString("destination9"); if (des9 == null) des9="";
				des10 = rs.getString("destination10"); if (des10 == null) des10="";
				des11 = rs.getString("destination11"); if (des11 == null) des11="";
				des12 = rs.getString("destination12"); if (des12 == null) des12="";
				des13 = rs.getString("destination13"); if (des13 == null) des13="";
				des14 = rs.getString("destination14"); if (des14 == null) des14="";
				des15 = rs.getString("destination15"); if (des15 == null) des15="";
				des16 = rs.getString("destination16"); if (des16 == null) des16="";
				des17 = rs.getString("destination17"); if (des17 == null) des17="";
				des18 = rs.getString("destination18"); if (des18 == null) des18="";
				des19 = rs.getString("destination19"); if (des19 == null) des19="";
				des20 = rs.getString("destination20"); if (des20 == null) des20="";
				des21 = rs.getString("destination21"); if (des21 == null) des21="";
				
				t3a = rs.getString("type3a"); if (t3a == null) t3a="";
				t3b = rs.getString("type3b"); if (t3b == null) t3b="";
				t3c = rs.getString("type3c"); if (t3c == null) t3c="";
				
				t7a = rs.getString("type7a"); if (t7a == null) t7a="";
				t7b = rs.getString("type7b"); if (t7b == null) t7b="";
				t7c = rs.getString("type7c"); if (t7c == null) t7c="";
				
				t13a = rs.getString("type13a"); if (t13a == null) t13a="";
				t13b = rs.getString("type13b"); if (t13b == null) t13b="";
				
				t16a = rs.getString("type16a"); if (t16a == null) t16a="";
				t16b = rs.getString("type16b"); if (t16b == null) t16b="";
				t16c = rs.getString("type16c"); if (t16c == null) t16c="";
				t16d = rs.getString("type16c_2nd"); if (t16d == null) t16d="";

				t17a = rs.getString("type17a"); if (t17a == null) t17a="";
				t17b = rs.getString("type17b"); if (t17b == null) t17b="";
				t17c = rs.getString("type17c"); if (t17c == null) t17c="";

				t18 = rs.getString("type18"); if (t18 == null) t18="";
				t22 = rs.getString("type22"); if (t22 == null) t22="";
				
				t19a = rs.getString("type19a"); if (t19a == null) t19a="";
				t19b = rs.getString("type19b"); if (t19b == null) t19b="";
				t19cUHF = rs.getString("type19cUHF"); if (t19cUHF == null) t19cUHF="";
				t19cVHF = rs.getString("type19cVHF"); if (t19cVHF == null) t19cVHF="";
				t19cELT = rs.getString("type19cELT"); if (t19cELT == null) t19cELT="";
				t19dS = rs.getString("type19dS"); if (t19dS == null) t19dS="";
				t19dP = rs.getString("type19dP"); if (t19dP == null) t19dP="";
				t19dD = rs.getString("type19dD"); if (t19dD == null) t19dD="";
				t19dM = rs.getString("type19dM"); if (t19dM == null) t19dM="";
				t19dJ = rs.getString("type19dJ"); if (t19dJ == null) t19dJ="";
				t19eJ = rs.getString("type19eJ"); if (t19eJ == null) t19eJ="";
				t19eL = rs.getString("type19eL"); if (t19eL == null) t19eL="";
				t19eF = rs.getString("type19eF"); if (t19eF == null) t19eF="";
				t19eU = rs.getString("type19eU"); if (t19eU == null) t19eU="";
				t19eV = rs.getString("type19eV"); if (t19eV == null) t19eV="";
				t19fD = rs.getString("type19fD"); if (t19fD == null) t19fD="";
				t19f_number = rs.getString("type19f_number"); if (t19f_number == null) t19f_number="";
				t19f_capacity = rs.getString("type19f_capacity"); if (t19f_capacity == null) t19f_capacity="";
				t19f_cover = rs.getString("type19f_cover"); if (t19f_cover == null) t19f_cover="";
				t19f_colour = rs.getString("type19f_colour"); if (t19f_colour == null) t19f_colour="";
				t19g = rs.getString("type19g"); if (t19g == null) t19g="";
				t19hN = rs.getString("type19hN"); if (t19hN == null) t19hN="";
				t19Remarks = rs.getString("type19Remarks"); if (t19Remarks == null) t19Remarks="";
				t19i = rs.getString("type19i"); if (t19i == null) t19i="";

				String tFiledby = rs.getString("filedby"); if (tFiledby == null) tFiledby="";
				
				//----------- begin of type18
				String sNav="",sCom="",sDat="",sSur="",sDep="",sDest="",sDof="",
				sReg="",sEet="",sSel="",sTyp="",sCode="",sDle="",sOpr="",sOrgn="",
				sPer="",sAltn="",sRalt="",sTalt="",sRif="",sRmk="",sPbn="",sSts="",other="";
				
		     	String type18 = t18;			     	
				if (type18.startsWith(" ")) type18=type18.replaceFirst("\\s+", "");
				if (type18.compareTo("0")==0) type18="";
		     	if (!type18.equals("")) {
					int i=0;
					String x="";
				        
					if (type18.contains("\n")) {
			        	String subi[] = type18.split("\n");
			        	
			        	other="";sSts="";sPbn="";sNav=""; sCom="";sDat="";sSur=""; sDep="";sDest="";sDof=""; sReg="";sEet="";sSel=""; sTyp="";
			        	sCode="";sDle="";sOpr="";sOrgn="";sPer=""; sAltn="";sRalt="";sTalt=""; sRif="";sRmk="";
			 	   			
			        	for (int ii=0; ii<subi.length; ii++) {
			        		String sub[] = subi[ii].split(" ");
			        		
			 	   			for (i=0; i<sub.length; i++) {
			 	   				if (sub[i].contains("/")) {
			 	   					x=sub[i];
			 	   				} else {
			 	   					x+=" "+sub[i];
			 	   				}
			
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
								else if (x.startsWith("PER/")) sPer = x.substring(4, x.length());
								
								else if (x.startsWith("ALTN/")) sAltn = x.substring(5, x.length());
								else if (x.startsWith("RALT/")) sRalt = x.substring(5, x.length());
								else if (x.startsWith("TALT/")) sTalt = x.substring(5, x.length());
								
								else if (x.startsWith("RIF/")) sRif = x.substring(4, x.length());
								else if (x.startsWith("RMK/")) sRmk = x.substring(4, x.length());
			 	   				else { other+= " "+x; }
			 	   			} //end of for sub[i]
			 				if (other.startsWith(" ")) other=other.replaceFirst(" ", "");
			 				if (sRmk.compareTo("")==0) { sRmk=other; } else { sRmk+=" "+other; }
			        	} //end of for subi[i]
			        } //end of if "\n"
			        else { //ELSE of if "\n"
			        	other="";sSts="";sPbn="";sNav=""; sCom="";sDat="";sSur=""; sDep="";sDest="";sDof=""; sReg="";sEet="";sSel=""; sTyp="";
			        	sCode="";sDle="";sOpr="";sOrgn="";sPer=""; sAltn="";sRalt="";sTalt=""; sRif="";sRmk="";
			   			
						String sub[] = type18.split(" ");
						for (i=0; i<sub.length; i++) {
							if (sub[i].contains("/")) {
								x=sub[i];
							} else {
								x+=" "+sub[i];
							}

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
							else if (x.startsWith("PER/")) sPer = x.substring(4, x.length());
							
							else if (x.startsWith("ALTN/")) sAltn = x.substring(5, x.length());
							else if (x.startsWith("RALT/")) sRalt = x.substring(5, x.length());
							else if (x.startsWith("TALT/")) sTalt = x.substring(5, x.length());
							
							else if (x.startsWith("RIF/")) sRif = x.substring(4, x.length());
							else if (x.startsWith("RMK/")) sRmk = x.substring(4, x.length());
							else { other+= " "+x; }
						} //end of for sub[i]
					 
						if (other.startsWith(" ")) other=other.replaceFirst(" ", "");
						if (sRmk.compareTo("")==0) { sRmk=other; } else { sRmk+=" "+other; }
			        }
				} else {
					sSts="";sPbn="";sNav=""; sCom="";sDat="";sSur=""; sDep="";sDest="";sDof=""; sReg="";sEet="";sSel=""; sTyp="";sCode="";sDle="";
					sOpr="";sOrgn="";sPer=""; sAltn="";sRalt="";sTalt=""; sRif="";sRmk="";
				}
				//---------------------------------------------------------
				if (sSts.compareTo("")==0) /*sSts=" STS/"+sSts; else*/ sSts="";
				if (sPbn.compareTo("")==0) /*sPbn=" PBN/"+sPbn; else*/ sPbn="";
				if (sNav.compareTo("")==0) /*sNav=" NAV/"+sNav; else*/ sNav="";
				 
				if (sCom.compareTo("")==0) /*sCom=" COM/"+sCom; else*/ sCom="";
				if (sDat.compareTo("")==0) /*sDat=" DAT/"+sDat; else*/ sDat="";
				if (sSur.compareTo("")==0) /*sSur=" SUR/"+sSur; else*/ sSur="";
					
				if (sDep.compareTo("")==0) /*sDep=" DEP/"+sDep; else*/ sDep="";
				if (sDest.compareTo("")==0) /*sDest=" DEST/"+sDest; else*/ sDest="";
				if (sReg.compareTo("")==0) /*sReg=" REG/"+sReg; else*/ sReg="";
					
				if (sDof.compareTo("")==0) /*sDof=" DOF/"+sDof; else*/ sDof="";
				if (sEet.compareTo("")==0) /*sEet=" EET/"+sEet; else*/ sEet="";
				if (sSel.compareTo("")==0) /*sSel=" SEL/"+sSel; else*/ sSel="";
					
				if (sTyp.compareTo("")==0) /*sTyp=" TYP/"+sTyp; else*/ sTyp="";
				if (sCode.compareTo("")==0) /*sCode=" CODE/"+sCode; else*/ sCode="";
				if (sDle.compareTo("")==0) /*sDle=" DLE/"+sDle; else*/ sDle="";
					
				if (sOpr.compareTo("")==0) /*sOpr=" OPR/"+sOpr; else*/ sOpr="";
				if (sOrgn.compareTo("")==0) /*sOrgn=" ORGN/"+sOrgn; else*/ sOrgn="";
				if (sPer.compareTo("")==0) /*sPer=" PER/"+sPer; else*/ sPer="";
					
				if (sAltn.compareTo("")==0) /*sAltn=" ALTN/"+sAltn; else*/ sAltn="";
				if (sRalt.compareTo("")==0) /*sRalt=" RALT/"+sRalt; else*/ sRalt="";
				if (sTalt.compareTo("")==0) /*sTalt=" TALT/"+sTalt; else*/ sTalt="";
					
				if (sRif.compareTo("")==0) /*sRif=" RIF/"+sRif; else*/ sRif="";
				if (sRmk.compareTo("")==0) /*sRmk=" RMK/"+sRmk; else*/ sRmk="";	
				//---------------------------------------------------------
				if (!sRmk.isEmpty() && sRmk.endsWith(" ")) {
					int ind = sRmk.lastIndexOf(" ");
					sRmk = sRmk.substring(0, ind);
				}
				if (!sPer.isEmpty() && sPer.length()>1) {
					sPer = sPer.substring(0, 1);
				}
				//---------------------------------------------------------
//				String type18a = P37;//rs.getString("type18a"); if (type18a == null) type18a="";
//				if (type18a.equals("REG/0")) type18a=""; 
//				else if (!type18a.equals("") && !type18a.equals("REG/0") && type18a.startsWith("REG/")) type18a = type18a.replaceFirst("REG/", "");
//				//---------------------------------------------------------
//				String type18b = P38;//rs.getString("type18b"); if (type18b == null) type18b="";
//				if (type18b.equals("DOF/0")) type18b=""; 
//				else if (!type18b.equals("") && !type18b.equals("DOF/0") && type18b.startsWith("DOF/")) type18b = type18b.replaceFirst("DOF/", "");
				//---------------------------------------------------------
				
				//REG/
				String s_Reg="",s_Dof="";
//				if (type18a.compareTo("")==0) {
					s_Reg=sReg;
//				} else {
//					s_Reg=type18a;
//				}
				if (s_Reg.equals("0")) s_Reg="";
				//DOF/
//				if (type18b.compareTo("")==0) {
					s_Dof=sDof;
//				} else {
//					s_Dof=type18b;
//				}
				if (s_Dof.equals("0")) s_Dof="";
				
				//ALL18/
				String aChar="",strDof="",strReg="",strPbn="",strSur="",strCode="",strDle="",strOrgn="",strTalt="";
				String strType18 = t18;
				if (strType18.startsWith(" ")) strType18=strType18.replaceFirst("\\s+", "");
				if (strType18.compareTo("0")==0) strType18="";
				if (!strType18.equals("")) {
					if (strType18.startsWith(" ")) strType18 = strType18.replaceFirst("\\s+", ""); //hapus spasi di awal Item 18
		 	        if (strType18.contains("\n")) {
		 	        	aChar = new Character((char)126).toString(); //kode ASCII 126='~'
		 	        	strType18 = strType18.replaceAll("\n", aChar);
		 	        } 

		 	        strPbn="";strSur="";strDof="";strReg="";strCode="";strDle="";strOrgn="";strTalt="";

		 	        String x="";
		 	        String sub[] = strType18.split(" ");
		 	        for (int i=0; i<sub.length; i++) {
		 	        	if (sub[i].contains("/")) { x=sub[i]; } 
		 	        	else { x+=" "+sub[i]; }
		 	        	//-------------------------------------
		 	        	if (x.startsWith("DOF/")) strDof = x.substring(4, x.length());
		 	        	else if (x.startsWith("~DOF/")) strDof = x.substring(5, x.length());
		 	        	//--
						if (x.startsWith("REG/")) strReg = x.substring(4, x.length());
						else if (x.startsWith("~REG/")) strReg = x.substring(5, x.length());
						//--
						if (x.startsWith("PBN/")) strPbn = x.substring(4, x.length());
						else if (x.startsWith("~PBN/")) strPbn = x.substring(5, x.length());
						//--
						if (x.startsWith("SUR/")) strSur = x.substring(4, x.length());
						else if (x.startsWith("~SUR/")) strSur = x.substring(5, x.length());
						//--
		 	        	if (x.startsWith("CODE/")) strCode = x.substring(5, x.length());
		 	        	else if (x.startsWith("~CODE/")) strCode = x.substring(6, x.length());
		 	        	//--
						if (x.startsWith("DLE/")) strDle = x.substring(4, x.length());
						else if (x.startsWith("~DLE/")) strDle = x.substring(5, x.length());
						//--
						if (x.startsWith("ORGN/")) strOrgn = x.substring(5, x.length());
						else if (x.startsWith("~ORGN/")) strOrgn = x.substring(6, x.length());
						//--
						if (x.startsWith("TALT/")) strTalt = x.substring(5, x.length());
						else if (x.startsWith("~TALT/")) strTalt = x.substring(6, x.length());
		 	        } //end of for sub[i]
				} else { //else of strType18==null
		 	        strPbn="";strSur="";strDof="";strReg="";strCode="";strDle="";strOrgn="";strTalt="";
				}
				//------------------------------------------------------------------
				//--mengembalikan enter
				if (strPbn.contains(aChar)) strPbn = strPbn.replaceFirst(aChar, "\n");
				if (strSur.contains(aChar)) strSur = strSur.replaceFirst(aChar, "\n");
				if (strDof.contains(aChar)) strDof = strDof.replaceFirst(aChar, "\n");
				if (strReg.contains(aChar)) strReg = strReg.replaceFirst(aChar, "\n");
				if (strCode.contains(aChar)) strCode = strCode.replaceFirst(aChar, "\n");
				if (strDle.contains(aChar)) strDle = strDle.replaceFirst(aChar, "\n");
				if (strOrgn.contains(aChar)) strOrgn = strOrgn.replaceFirst(aChar, "\n");
				if (strTalt.contains(aChar)) strTalt = strTalt.replaceFirst(aChar, "\n");
				//--hapus enter di awal berita 
				if (strPbn.startsWith("\n")) strPbn = strPbn.replaceFirst("\n", "");
				if (strSur.startsWith("\n")) strSur = strSur.replaceFirst("\n", "");
				if (strDof.startsWith("\n")) strDof = strDof.replaceFirst("\n", "");
				if (strReg.startsWith("\n")) strReg = strReg.replaceFirst("\n", "");
				if (strCode.startsWith("\n")) strCode = strCode.replaceFirst("\n", "");
				if (strDle.startsWith("\n")) strDle = strDle.replaceFirst("\n", "");
				if (strOrgn.startsWith("\n")) strOrgn = strOrgn.replaceFirst("\n", "");
				if (strTalt.startsWith("\n")) strTalt = strTalt.replaceFirst("\n", "");
				//--hapus spasi di awal berita 
				if (strPbn.startsWith(" ")) strPbn = strPbn.replaceFirst("\\s+", "");
				if (strSur.startsWith(" ")) strSur = strSur.replaceFirst("\\s+", "");
				if (strDof.startsWith(" ")) strDof = strDof.replaceFirst("\\s+", "");
				if (strReg.startsWith(" ")) strReg = strReg.replaceFirst("\\s+", "");
				if (strCode.startsWith(" ")) strCode = strCode.replaceFirst("\\s+", "");
				if (strDle.startsWith(" ")) strDle = strDle.replaceFirst("\\s+", "");
				if (strOrgn.startsWith(" ")) strOrgn = strOrgn.replaceFirst("\\s+", "");
				if (strTalt.startsWith(" ")) strTalt = strTalt.replaceFirst("\\s+", "");
				
				//kalo ada REG/ atau DOF/ di tipe18, tidak di tampilkan di Text 18 karena di tampilkan di Text REG dan di Text DOF
				if (strType18.contains(" REG/"+s_Reg)) { strType18 = strType18.replace(" REG/"+s_Reg, ""); } 
				else if (strType18.contains("REG/"+s_Reg)) { strType18 = strType18.replace("REG/"+s_Reg, ""); }
				
				if (strType18.contains(" DOF/"+s_Dof)) { strType18 = strType18.replace(" DOF/"+s_Dof, ""); } 
				else if (strType18.contains("DOF/"+s_Dof)) { strType18 = strType18.replace("DOF/"+s_Dof, ""); }
				
				//untuk PBN/ SUR/ CODE/ DLE/ ORGN/ TALT tidak perlu di tampilkan di Text 18 
				if (strType18.contains(" PBN/"+strPbn)) { strType18 = strType18.replace(" PBN/"+strPbn, ""); } 
				else if (strType18.contains("PBN/"+strPbn)) { strType18 = strType18.replace("PBN/"+strPbn, ""); }
				
				if (strType18.contains(" SUR/"+strSur)) { strType18 = strType18.replace(" SUR/"+strSur, ""); } 
				else if (strType18.contains("SUR/"+strSur)) { strType18 = strType18.replace("SUR/"+strSur, ""); }
				
				if (strType18.contains(" CODE/"+strCode)) { strType18 = strType18.replace(" CODE/"+strCode, ""); } 
				else if (strType18.contains("CODE/"+strCode)) { strType18 = strType18.replace("CODE/"+strCode, ""); }
				
				if (strType18.contains(" DLE/"+strDle)) { strType18 = strType18.replace(" DLE/"+strDle, ""); } 
				else if (strType18.contains("DLE/"+strDle)) { strType18 = strType18.replace("DLE/"+strDle, ""); }
				
				if (strType18.contains(" ORGN/"+strOrgn)) { strType18 = strType18.replace(" ORGN/"+strOrgn, ""); } 
				else if (strType18.contains("ORGN/"+strOrgn)) { strType18 = strType18.replace("ORGN/"+strOrgn, ""); }
				
				if (strType18.contains(" TALT/"+strTalt)) { strType18 = strType18.replace(" TALT/"+strTalt, ""); } 
				else if (strType18.contains("TALT/"+strTalt)) { strType18 = strType18.replace("TALT/"+strTalt, ""); }
				
				if (strType18.startsWith(" ")) strType18 = strType18.replaceFirst("\\s+", "");
				// -------------------------------------------------------
				if (ID.endsWith("DLA")) {
					ATSForms.discardDLA();
					
					sh = Shells.sh_nDLA;
					ATSForms.t3aDLA.setText("DLA"); ATSForms.t3bDLA.setText(t3b); ATSForms.t3cDLA.setText(t3c);
					ATSForms.t7aDLA.setText(t7a); if (t7b.equals("A")) ATSForms.t7bDLA.setSelection(true); ATSForms.t7cDLA.setText(t7c);
					ATSForms.t13aDLA.setText(t13a); ATSForms.t13bDLA.setText(t13b);
					ATSForms.t16aDLA.setText(t16a); //ATSForms.t16bDLA.setText(t16b); ATSForms.t16cDLA.setText(t16c); ATSForms.t16dDLA.setText(t16d);
					ATSForms.tDofDLA.setText(s_Dof);
					ATSForms.tFbDLA.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t16bDLA.setText(""); ATSForms.t16cDLA.setText(""); ATSForms.t16dDLA.setText("");
					} else {
						ATSForms.t16bDLA.setText(t16b); ATSForms.t16cDLA.setText(t16c); ATSForms.t16dDLA.setText(t16d);
					}
					
					HeaderComposite.tDest1DLA.setText(des1);
					HeaderComposite.tDest2DLA.setText(des2);
					HeaderComposite.tDest3DLA.setText(des3);
					HeaderComposite.tDest4DLA.setText(des4);
					HeaderComposite.tDest5DLA.setText(des5);
					HeaderComposite.tDest6DLA.setText(des6);
					HeaderComposite.tDest7DLA.setText(des7);
					HeaderComposite.tDest8DLA.setText(des8);
					HeaderComposite.tDest9DLA.setText(des9);
					HeaderComposite.tDest10DLA.setText(des10);
					HeaderComposite.tDest11DLA.setText(des11);
					HeaderComposite.tDest12DLA.setText(des12);
					HeaderComposite.tDest13DLA.setText(des13);
					HeaderComposite.tDest14DLA.setText(des14);
					HeaderComposite.tDest15DLA.setText(des15);
					HeaderComposite.tDest16DLA.setText(des16);
					HeaderComposite.tDest17DLA.setText(des17);
					HeaderComposite.tDest18DLA.setText(des18);
					HeaderComposite.tDest19DLA.setText(des19);
					HeaderComposite.tDest20DLA.setText(des20);
					HeaderComposite.tDest21DLA.setText(des21);
				} else if (ID.endsWith("CHG")) {
					ATSForms.discardCHG();
					
					sh = Shells.sh_nCHG;
					ATSForms.t3aCHG.setText("CHG"); ATSForms.t3bCHG.setText(t3b); ATSForms.t3cCHG.setText(t3c);
					ATSForms.t7aCHG.setText(t7a); if (t7b.equals("A")) ATSForms.t7bCHG.setSelection(true); ATSForms.t7cCHG.setText(t7c);
					ATSForms.t13aCHG.setText(t13a); //ATSForms.t13bCHG.setText(t13b);
					ATSForms.t16aCHG.setText(t16a); //ATSForms.t16bCHG.setText(t16b); ATSForms.t16cCHG.setText(t16c); ATSForms.t16dCHG.setText(t16d);
					ATSForms.tDofCHG.setText(s_Dof);
					ATSForms.t22CHG.setText(t22);
					ATSForms.tFbCHG.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t13bCHG.setText("");
						ATSForms.t16bCHG.setText(""); ATSForms.t16cCHG.setText(""); ATSForms.t16dCHG.setText("");
					} else {
						ATSForms.t13bCHG.setText(t13b);
						ATSForms.t16bCHG.setText(t16b); ATSForms.t16cCHG.setText(t16c); ATSForms.t16dCHG.setText(t16d);
					}
					
					HeaderComposite.tDest1CHG.setText(des1);
					HeaderComposite.tDest2CHG.setText(des2);
					HeaderComposite.tDest3CHG.setText(des3);
					HeaderComposite.tDest4CHG.setText(des4);
					HeaderComposite.tDest5CHG.setText(des5);
					HeaderComposite.tDest6CHG.setText(des6);
					HeaderComposite.tDest7CHG.setText(des7);
					HeaderComposite.tDest8CHG.setText(des8);
					HeaderComposite.tDest9CHG.setText(des9);
					HeaderComposite.tDest10CHG.setText(des10);
					HeaderComposite.tDest11CHG.setText(des11);
					HeaderComposite.tDest12CHG.setText(des12);
					HeaderComposite.tDest13CHG.setText(des13);
					HeaderComposite.tDest14CHG.setText(des14);
					HeaderComposite.tDest15CHG.setText(des15);
					HeaderComposite.tDest16CHG.setText(des16);
					HeaderComposite.tDest17CHG.setText(des17);
					HeaderComposite.tDest18CHG.setText(des18);
					HeaderComposite.tDest19CHG.setText(des19);
					HeaderComposite.tDest20CHG.setText(des20);
					HeaderComposite.tDest21CHG.setText(des21);
				} else if (ID.endsWith("CNL")) {
					ATSForms.discardCNL();
					
					sh = Shells.sh_nCNL;
					ATSForms.t3aCNL.setText("CNL"); ATSForms.t3bCNL.setText(t3b); ATSForms.t3cCNL.setText(t3c);
					ATSForms.t7aCNL.setText(t7a); if (t7b.equals("A")) ATSForms.t7bCNL.setSelection(true); ATSForms.t7cCNL.setText(t7c);
					ATSForms.t13aCNL.setText(t13a); //ATSForms.t13bCNL.setText(t13b);
					ATSForms.t16aCNL.setText(t16a); //ATSForms.t16bCNL.setText(t16b); ATSForms.t16cCNL.setText(t16c); ATSForms.t16dCNL.setText(t16d);
					ATSForms.tDofCNL.setText(s_Dof);
					ATSForms.tFbCNL.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t13bCNL.setText("");
						ATSForms.t16bCNL.setText(""); ATSForms.t16cCNL.setText(""); ATSForms.t16dCNL.setText("");
					} else {
						ATSForms.t13bCNL.setText(t13b);
						ATSForms.t16bCNL.setText(t16b); ATSForms.t16cCNL.setText(t16c); ATSForms.t16dCNL.setText(t16d);
					}
					
					HeaderComposite.tDest1CNL.setText(des1);
					HeaderComposite.tDest2CNL.setText(des2);
					HeaderComposite.tDest3CNL.setText(des3);
					HeaderComposite.tDest4CNL.setText(des4);
					HeaderComposite.tDest5CNL.setText(des5);
					HeaderComposite.tDest6CNL.setText(des6);
					HeaderComposite.tDest7CNL.setText(des7);
					HeaderComposite.tDest8CNL.setText(des8);
					HeaderComposite.tDest9CNL.setText(des9);
					HeaderComposite.tDest10CNL.setText(des10);
					HeaderComposite.tDest11CNL.setText(des11);
					HeaderComposite.tDest12CNL.setText(des12);
					HeaderComposite.tDest13CNL.setText(des13);
					HeaderComposite.tDest14CNL.setText(des14);
					HeaderComposite.tDest15CNL.setText(des15);
					HeaderComposite.tDest16CNL.setText(des16);
					HeaderComposite.tDest17CNL.setText(des17);
					HeaderComposite.tDest18CNL.setText(des18);
					HeaderComposite.tDest19CNL.setText(des19);
					HeaderComposite.tDest20CNL.setText(des20);
					HeaderComposite.tDest21CNL.setText(des21);
				} else if (ID.endsWith("DEP")) {
					ATSForms.discardDEP();
					
					sh = Shells.sh_nDEP;
					ATSForms.t3aDEP.setText("DEP"); ATSForms.t3bDEP.setText(t3b); ATSForms.t3cDEP.setText(t3c);
					ATSForms.t7aDEP.setText(t7a); if (t7b.equals("A")) ATSForms.t7bDEP.setSelection(true); ATSForms.t7cDEP.setText(t7c);
					ATSForms.t13aDEP.setText(t13a); ATSForms.t13bDEP.setText(t13b);
					ATSForms.t16aDEP.setText(t16a); //ATSForms.t16bDEP.setText(t16b); ATSForms.t16cDEP.setText(t16c); ATSForms.t16dDEP.setText(t16d);
					ATSForms.tDofDEP.setText(s_Dof);
					ATSForms.tFbDEP.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t16bDEP.setText(""); ATSForms.t16cDEP.setText(""); ATSForms.t16dDEP.setText("");
					} else {
						ATSForms.t16bDEP.setText(t16b); ATSForms.t16cDEP.setText(t16c); ATSForms.t16dDEP.setText(t16d);
					}
					
					HeaderComposite.tDest1DEP.setText(des1);
					HeaderComposite.tDest2DEP.setText(des2);
					HeaderComposite.tDest3DEP.setText(des3);
					HeaderComposite.tDest4DEP.setText(des4);
					HeaderComposite.tDest5DEP.setText(des5);
					HeaderComposite.tDest6DEP.setText(des6);
					HeaderComposite.tDest7DEP.setText(des7);
					HeaderComposite.tDest8DEP.setText(des8);
					HeaderComposite.tDest9DEP.setText(des9);
					HeaderComposite.tDest10DEP.setText(des10);
					HeaderComposite.tDest11DEP.setText(des11);
					HeaderComposite.tDest12DEP.setText(des12);
					HeaderComposite.tDest13DEP.setText(des13);
					HeaderComposite.tDest14DEP.setText(des14);
					HeaderComposite.tDest15DEP.setText(des15);
					HeaderComposite.tDest16DEP.setText(des16);
					HeaderComposite.tDest17DEP.setText(des17);
					HeaderComposite.tDest18DEP.setText(des18);
					HeaderComposite.tDest19DEP.setText(des19);
					HeaderComposite.tDest20DEP.setText(des20);
					HeaderComposite.tDest21DEP.setText(des21);
				} else if (ID.endsWith("ARR")) {
					ATSForms.discardARR();
					
					sh = Shells.sh_nARR;
					ATSForms.t3aARR.setText("ARR"); ATSForms.t3bARR.setText(t3b); ATSForms.t3cARR.setText(t3c);
					ATSForms.t7aARR.setText(t7a); if (t7b.equals("A")) ATSForms.t7bARR.setSelection(true); ATSForms.t7cARR.setText(t7c);
					ATSForms.t13aARR.setText(t13a); //ATSForms.t13bARR.setText(t13b);
					ATSForms.t17aARR.setText(t17a); ATSForms.t17bARR.setText(t17b); ATSForms.t17cARR.setText(t17c);
					ATSForms.tFbARR.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t13bARR.setText("");
					} else {
						ATSForms.t13bARR.setText(t13b);
					}
					
					HeaderComposite.tDest1ARR.setText(des1);
					HeaderComposite.tDest2ARR.setText(des2);
					HeaderComposite.tDest3ARR.setText(des3);
					HeaderComposite.tDest4ARR.setText(des4);
					HeaderComposite.tDest5ARR.setText(des5);
					HeaderComposite.tDest6ARR.setText(des6);
					HeaderComposite.tDest7ARR.setText(des7);
					HeaderComposite.tDest8ARR.setText(des8);
					HeaderComposite.tDest9ARR.setText(des9);
					HeaderComposite.tDest10ARR.setText(des10);
					HeaderComposite.tDest11ARR.setText(des11);
					HeaderComposite.tDest12ARR.setText(des12);
					HeaderComposite.tDest13ARR.setText(des13);
					HeaderComposite.tDest14ARR.setText(des14);
					HeaderComposite.tDest15ARR.setText(des15);
					HeaderComposite.tDest16ARR.setText(des16);
					HeaderComposite.tDest17ARR.setText(des17);
					HeaderComposite.tDest18ARR.setText(des18);
					HeaderComposite.tDest19ARR.setText(des19);
					HeaderComposite.tDest20ARR.setText(des20);
					HeaderComposite.tDest21ARR.setText(des21);
				} else if (ID.endsWith("CDN")) {
					ATSForms.discardCDN();
					
					sh = Shells.sh_nCDN;
					ATSForms.t3aCDN.setText("CDN"); ATSForms.t3bCDN.setText(t3b); ATSForms.t3cCDN.setText(t3c);
					ATSForms.t7aCDN.setText(t7a); if (t7b.equals("A")) ATSForms.t7bCDN.setSelection(true); ATSForms.t7cCDN.setText(t7c);
					ATSForms.t13aCDN.setText(t13a); //ATSForms.t13bCDN.setText(t13b);
					ATSForms.t16aCDN.setText(t16a); //ATSForms.t16bCDN.setText(t16b); ATSForms.t16cCDN.setText(t16c); ATSForms.t16dCDN.setText(t16d);
					ATSForms.t22CDN.setText(t22);
					ATSForms.tFbCDN.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t13bCDN.setText("");
						ATSForms.t16bCDN.setText(""); ATSForms.t16cCDN.setText(""); ATSForms.t16dCDN.setText("");
					} else {
						ATSForms.t13bCDN.setText(t13b);
						ATSForms.t16bCDN.setText(t16b); ATSForms.t16cCDN.setText(t16c); ATSForms.t16dCDN.setText(t16d);
					}
					
					HeaderComposite.tDest1CDN.setText(des1);
					HeaderComposite.tDest2CDN.setText(des2);
					HeaderComposite.tDest3CDN.setText(des3);
					HeaderComposite.tDest4CDN.setText(des4);
					HeaderComposite.tDest5CDN.setText(des5);
					HeaderComposite.tDest6CDN.setText(des6);
					HeaderComposite.tDest7CDN.setText(des7);
					HeaderComposite.tDest8CDN.setText(des8);
					HeaderComposite.tDest9CDN.setText(des9);
					HeaderComposite.tDest10CDN.setText(des10);
					HeaderComposite.tDest11CDN.setText(des11);
					HeaderComposite.tDest12CDN.setText(des12);
					HeaderComposite.tDest13CDN.setText(des13);
					HeaderComposite.tDest14CDN.setText(des14);
					HeaderComposite.tDest15CDN.setText(des15);
					HeaderComposite.tDest16CDN.setText(des16);
					HeaderComposite.tDest17CDN.setText(des17);
					HeaderComposite.tDest18CDN.setText(des18);
					HeaderComposite.tDest19CDN.setText(des19);
					HeaderComposite.tDest20CDN.setText(des20);
					HeaderComposite.tDest21CDN.setText(des21);
				} else if (ID.endsWith("ACP")) {
					ATSForms.discardACP();
					
					sh = Shells.sh_nACP;
					ATSForms.t3aACP.setText("ACP"); ATSForms.t3bACP.setText(t3b); ATSForms.t3cACP.setText(t3c);
					ATSForms.t7aACP.setText(t7a); if (t7b.equals("A")) ATSForms.t7bACP.setSelection(true); ATSForms.t7cACP.setText(t7c);
					ATSForms.t13aACP.setText(t13a); //ATSForms.t13bACP.setText(t13b);
					ATSForms.t16aACP.setText(t16a); //ATSForms.t16bACP.setText(t16b); ATSForms.t16cACP.setText(t16c); ATSForms.t16dACP.setText(t16d);
					ATSForms.tFbACP.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t13bACP.setText("");
						ATSForms.t16bACP.setText(""); ATSForms.t16cACP.setText(""); ATSForms.t16dACP.setText("");
					} else {
						ATSForms.t13bACP.setText(t13b);
						ATSForms.t16bACP.setText(t16b); ATSForms.t16cACP.setText(t16c); ATSForms.t16dACP.setText(t16d);
					}
					
					HeaderComposite.tDest1ACP.setText(des1);
					HeaderComposite.tDest2ACP.setText(des2);
					HeaderComposite.tDest3ACP.setText(des3);
					HeaderComposite.tDest4ACP.setText(des4);
					HeaderComposite.tDest5ACP.setText(des5);
					HeaderComposite.tDest6ACP.setText(des6);
					HeaderComposite.tDest7ACP.setText(des7);
					HeaderComposite.tDest8ACP.setText(des8);
					HeaderComposite.tDest9ACP.setText(des9);
					HeaderComposite.tDest10ACP.setText(des10);
					HeaderComposite.tDest11ACP.setText(des11);
					HeaderComposite.tDest12ACP.setText(des12);
					HeaderComposite.tDest13ACP.setText(des13);
					HeaderComposite.tDest14ACP.setText(des14);
					HeaderComposite.tDest15ACP.setText(des15);
					HeaderComposite.tDest16ACP.setText(des16);
					HeaderComposite.tDest17ACP.setText(des17);
					HeaderComposite.tDest18ACP.setText(des18);
					HeaderComposite.tDest19ACP.setText(des19);
					HeaderComposite.tDest20ACP.setText(des20);
					HeaderComposite.tDest21ACP.setText(des21);
				} else if (ID.endsWith("EST")) {
					ATSForms.discardEST();
					
					sh = Shells.sh_nEST;
					ATSForms.t3aEST.setText("EST"); ATSForms.t3bEST.setText(t3b); ATSForms.t3cEST.setText(t3c);
					ATSForms.t7aEST.setText(t7a); if (t7b.equals("A")) ATSForms.t7bEST.setSelection(true); ATSForms.t7cEST.setText(t7c);
					ATSForms.t13aEST.setText(t13a); //ATSForms.t13bEST.setText(t13b);
					ATSForms.t16aEST.setText(t16a); //ATSForms.t16bEST.setText(t16b); ATSForms.t16cEST.setText(t16c); ATSForms.t16dEST.setText(t16d);
					ATSForms.tFbEST.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t13bEST.setText("");
						ATSForms.t16bEST.setText(""); ATSForms.t16cEST.setText(""); ATSForms.t16dEST.setText("");
					} else {
						ATSForms.t13bEST.setText(t13b);
						ATSForms.t16bEST.setText(t16b); ATSForms.t16cEST.setText(t16c); ATSForms.t16dEST.setText(t16d);
					}
					
					HeaderComposite.tDest1EST.setText(des1);
					HeaderComposite.tDest2EST.setText(des2);
					HeaderComposite.tDest3EST.setText(des3);
					HeaderComposite.tDest4EST.setText(des4);
					HeaderComposite.tDest5EST.setText(des5);
					HeaderComposite.tDest6EST.setText(des6);
					HeaderComposite.tDest7EST.setText(des7);
					HeaderComposite.tDest8EST.setText(des8);
					HeaderComposite.tDest9EST.setText(des9);
					HeaderComposite.tDest10EST.setText(des10);
					HeaderComposite.tDest11EST.setText(des11);
					HeaderComposite.tDest12EST.setText(des12);
					HeaderComposite.tDest13EST.setText(des13);
					HeaderComposite.tDest14EST.setText(des14);
					HeaderComposite.tDest15EST.setText(des15);
					HeaderComposite.tDest16EST.setText(des16);
					HeaderComposite.tDest17EST.setText(des17);
					HeaderComposite.tDest18EST.setText(des18);
					HeaderComposite.tDest19EST.setText(des19);
					HeaderComposite.tDest20EST.setText(des20);
					HeaderComposite.tDest21EST.setText(des21);
				} else if (ID.endsWith("RQS")) {
					ATSForms.discardRQS();
					
					sh = Shells.sh_nRQS;
					ATSForms.t3aRQS.setText("RQS"); ATSForms.t3bRQS.setText(t3b); ATSForms.t3cRQS.setText(t3c);
					ATSForms.t7aRQS.setText(t7a); if (t7b.equals("A")) ATSForms.t7bRQS.setSelection(true); ATSForms.t7cRQS.setText(t7c);
					ATSForms.t13aRQS.setText(t13a); //ATSForms.t13bRQS.setText(t13b);
					ATSForms.t16aRQS.setText(t16a); //ATSForms.t16bRQS.setText(t16b); ATSForms.t16cRQS.setText(t16c); ATSForms.t16dRQS.setText(t16d);
					ATSForms.tDofRQS.setText(s_Dof);
					ATSForms.tFbRQS.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t13bRQS.setText("");
						ATSForms.t16bRQS.setText(""); ATSForms.t16cRQS.setText(""); ATSForms.t16dRQS.setText("");
					} else {
						ATSForms.t13bRQS.setText(t13b);
						ATSForms.t16bRQS.setText(t16b); ATSForms.t16cRQS.setText(t16c); ATSForms.t16dRQS.setText(t16d);
					}
					
					HeaderComposite.tDest1RQS.setText(des1);
					HeaderComposite.tDest2RQS.setText(des2);
					HeaderComposite.tDest3RQS.setText(des3);
					HeaderComposite.tDest4RQS.setText(des4);
					HeaderComposite.tDest5RQS.setText(des5);
					HeaderComposite.tDest6RQS.setText(des6);
					HeaderComposite.tDest7RQS.setText(des7);
					HeaderComposite.tDest8RQS.setText(des8);
					HeaderComposite.tDest9RQS.setText(des9);
					HeaderComposite.tDest10RQS.setText(des10);
					HeaderComposite.tDest11RQS.setText(des11);
					HeaderComposite.tDest12RQS.setText(des12);
					HeaderComposite.tDest13RQS.setText(des13);
					HeaderComposite.tDest14RQS.setText(des14);
					HeaderComposite.tDest15RQS.setText(des15);
					HeaderComposite.tDest16RQS.setText(des16);
					HeaderComposite.tDest17RQS.setText(des17);
					HeaderComposite.tDest18RQS.setText(des18);
					HeaderComposite.tDest19RQS.setText(des19);
					HeaderComposite.tDest20RQS.setText(des20);
					HeaderComposite.tDest21RQS.setText(des21);
				} else if (ID.endsWith("SPL")) {
					ATSForms.discardSPL();
					
					sh = Shells.sh_nSPL;
					ATSForms.t3aSPL.setText("SPL"); ATSForms.t3bSPL.setText(t3b); ATSForms.t3cSPL.setText(t3c);
					ATSForms.t7aSPL.setText(t7a); if (t7b.equals("A")) ATSForms.t7bSPL.setSelection(true); ATSForms.t7cSPL.setText(t7c);
					ATSForms.t13aSPL.setText(t13a); ATSForms.t13bSPL.setText(t13b);
					ATSForms.t16aSPL.setText(t16a); ATSForms.t16bSPL.setText(t16b); ATSForms.t16cSPL.setText(t16c); ATSForms.t16dSPL.setText(t16d);
					ATSForms.tDofSPL.setText(s_Dof);
					ATSForms.tFbSPL.setText(tFiledby);
					
					//----------- start of type18
					ATSForms.tRegSPL.setText(s_Reg);
					ATSForms.tDofSPL.setText(s_Dof);
					ATSForms.tNavSPL.setText(sNav);
					ATSForms.tComSPL.setText(sCom);
					ATSForms.tDatSPL.setText(sDat);
					ATSForms.tSurSPL.setText(sSur);
					ATSForms.tDepSPL.setText(sDep);
					ATSForms.tDestSPL.setText(sDest);
					ATSForms.tEetSPL.setText(sEet);
					ATSForms.tSelSPL.setText(sSel);
					ATSForms.tTypSPL.setText(sTyp);
					ATSForms.tCodeSPL.setText(sCode);
					ATSForms.tDleSPL.setText(sDle);
					ATSForms.tOprSPL.setText(sOpr);
					ATSForms.tOrgnSPL.setText(sOrgn);
					ATSForms.tPerSPL.setText(sPer);
					ATSForms.tAltnSPL.setText(sAltn);
					ATSForms.tRaltSPL.setText(sRalt);
					ATSForms.tTaltSPL.setText(sTalt);
					ATSForms.tRifSPL.setText(sRif);
					ATSForms.tRmkSPL.setText(sRmk);
					ATSForms.tPbnSPL.setText(sPbn);
					ATSForms.tStsSPL.setText(sSts);
					//----------- end of type18
					ATSForms.t19aSPL.setText(t19a); ATSForms.t19bSPL.setText(t19b);

					if (t19dP.equals("S/P")) ATSForms.t19dPSPL.setText("P");
					else ATSForms.t19dPSPL.setText("X");
					if (t19dD.equals("S/D")) ATSForms.t19dDSPL.setText("D");
					else ATSForms.t19dDSPL.setText("X");
					if (t19dM.equals("S/M")) ATSForms.t19dMSPL.setText("M");
					else ATSForms.t19dMSPL.setText("X");
					if (t19dJ.equals("S/J")) ATSForms.t19dJSPL.setText("J");
					else ATSForms.t19dJSPL.setText("X");
					
					if (t19eL.equals("J/L")) ATSForms.t19eLSPL.setText("L");
					else ATSForms.t19eLSPL.setText("X");
					if (t19eF.equals("J/F")) ATSForms.t19eFSPL.setText("F");
					else ATSForms.t19eFSPL.setText("X");
					if (t19eU.equals("J/U")) ATSForms.t19eUSPL.setText("U");
					else ATSForms.t19eUSPL.setText("X");
					if (t19eV.equals("J/V")) ATSForms.t19eVSPL.setText("V");
					else ATSForms.t19eVSPL.setText("X");
					
					if (t19cUHF.equals("R/U")) ATSForms.t19cUhfSPL.setText("U");
					else ATSForms.t19cUhfSPL.setText("X");
					if (t19cVHF.equals("R/V")) ATSForms.t19cVhfSPL.setText("V");
					else ATSForms.t19cVhfSPL.setText("X");
					if (t19cELT.equals("R/E")) ATSForms.t19cEltSPL.setText("E");
					else ATSForms.t19cVhfSPL.setText("X");
					
					/*if (t19dP.equals("S/P")) ATSForms.t19dPSPL.setText("P");
					if (t19dD.equals("S/D")) ATSForms.t19dDSPL.setText("D");
					if (t19dM.equals("S/M")) ATSForms.t19dMSPL.setText("M");
					if (t19dJ.equals("S/J")) ATSForms.t19dJSPL.setText("J");
					
					if (t19eL.equals("J/L")) ATSForms.t19eLSPL.setText("L");
					if (t19eF.equals("J/F")) ATSForms.t19eFSPL.setText("F");
					if (t19eU.equals("J/U")) ATSForms.t19eUSPL.setText("U");
					if (t19eV.equals("J/V")) ATSForms.t19eVSPL.setText("V");*/
					
					ATSForms.t19NumSPL.setText(t19f_number); ATSForms.t19CapSPL.setText(t19f_capacity); 
					if (t19f_cover.isEmpty()) ATSForms.t19cSPL.setText("X");
					else ATSForms.t19cSPL.setText(t19f_cover);
					ATSForms.t19ColSPL.setText(t19f_colour);
					ATSForms.t19AirSPL.setText(t19g); ATSForms.t19RemSPL.setText(t19Remarks); ATSForms.t19PilSPL.setText(t19i);
					HeaderComposite.tDest1SPL.setText(des1);
					HeaderComposite.tDest2SPL.setText(des2);
					HeaderComposite.tDest3SPL.setText(des3);
					HeaderComposite.tDest4SPL.setText(des4);
					HeaderComposite.tDest5SPL.setText(des5);
					HeaderComposite.tDest6SPL.setText(des6);
					HeaderComposite.tDest7SPL.setText(des7);
					HeaderComposite.tDest8SPL.setText(des8);
					HeaderComposite.tDest9SPL.setText(des9);
					HeaderComposite.tDest10SPL.setText(des10);
					HeaderComposite.tDest11SPL.setText(des11);
					HeaderComposite.tDest12SPL.setText(des12);
					HeaderComposite.tDest13SPL.setText(des13);
					HeaderComposite.tDest14SPL.setText(des14);
					HeaderComposite.tDest15SPL.setText(des15);
					HeaderComposite.tDest16SPL.setText(des16);
					HeaderComposite.tDest17SPL.setText(des17);
					HeaderComposite.tDest18SPL.setText(des18);
					HeaderComposite.tDest19SPL.setText(des19);
					HeaderComposite.tDest20SPL.setText(des20);
					HeaderComposite.tDest21SPL.setText(des21);
				}
				p++;
			}
		} catch (SQLException s) {
//			s.printStackTrace();
			if (s.getMessage().contains("Table '"+jdbc.getDbName()+".air_message"+time.yearMonth+"' doesn't exist")) {
				System.err.println(s.getMessage());	
			}
		} catch (java.lang.OutOfMemoryError hs) {
			DialogFactory.openInfoDialog(sh, "Get FPL", "Out of memory !!\nPlease fill another criteria search !! ");
		}
	}
	
	static void headClr(String str,Text tDest1,Text tDest2,Text tDest3,Text tDest4,Text tDest5,Text tDest6,Text tDest7,
			Text tDest8,Text tDest9,Text tDest10,Text tDest11,Text tDest12,Text tDest13,Text tDest14,
			Text tDest15,Text tDest16,Text tDest17,Text tDest18,Text tDest19,Text tDest20,Text tDest21) {
		tDest1.setText(str);
		tDest2.setText(str);
		tDest3.setText(str);
		tDest4.setText(str);
		tDest5.setText(str);
		tDest6.setText(str);
		tDest7.setText(str);
		tDest8.setText(str);
		tDest9.setText(str);
		tDest10.setText(str);
		tDest11.setText(str);
		tDest12.setText(str);
		tDest13.setText(str);
		tDest14.setText(str);
		tDest15.setText(str);
		tDest16.setText(str);
		tDest17.setText(str);
		tDest18.setText(str);
		tDest19.setText(str);
		tDest20.setText(str);
		tDest21.setText(str);
	}
	
	static void bodyClr(String str,Text t3b,Text t3c,Button t7b,Text t7c,Text t13a,Text t13b,Text t16a,Text t16b,Text t16c,Text t16d) {
		t3b.setText(str); t3c.setText(str);
		t7b.setSelection(false); t7c.setText(str);
		t13a.setText(str); t13b.setText(str);
		t16a.setText(str); t16b.setText(str); t16c.setText(str); t16d.setText(str);
	}
	
	static void b18Clr(String str,Text tDof) {
		tDof.setText(str);
	}
	
	static void b18Clr(String str,Text tReg,Text tDof,Text tNav,Text tCom,Text tDat,Text tSur,Text tDep,Text tDest,Text tEet,
			Text tSel,Text tTyp,Text tCode,Text tDle,Text tOpr,Text tOrgn,Text tPer,Text tAltn,Text tRalt,Text tTalt,Text tRif,
			Text tRmk,Text tPbn,Text tSts) {
		tReg.setText(str);
		tDof.setText(str);
		tNav.setText(str);
		tCom.setText(str);
		tDat.setText(str);
		tSur.setText(str);
		tDep.setText(str);
		tDest.setText(str);
		tEet.setText(str);
		tSel.setText(str);
		tTyp.setText(str);
		tCode.setText(str);
		tDle.setText(str);
		tOpr.setText(str);
		tOrgn.setText(str);
		tPer.setText(str);
		tAltn.setText(str);
		tRalt.setText(str);
		tTalt.setText(str);
		tRif.setText(str);
		tRmk.setText(str);
		tPbn.setText(str);
		tSts.setText(str);
	}
	
	public static void Next_FPL(String ID) {
		try {
			rs.last();
			jumlah = rs.getRow();
			rs.beforeFirst();

			for (rowNo=0; rowNo < (qq * baris); rowNo++) rs.next();
			p=0;
			rowNo=0;
			if (ID.endsWith("DLA")) {
				ATSForms.discardDLA();
				
				ATSForms.t3aDLA.setText("DLA"); 
				bodyClr("",ATSForms.t3bDLA,ATSForms.t3cDLA,ATSForms.t7bDLA,ATSForms.t7cDLA,ATSForms.t13aDLA,ATSForms.t13bDLA,ATSForms.t16aDLA,ATSForms.t16bDLA,ATSForms.t16cDLA,ATSForms.t16dDLA);
				headClr("",HeaderComposite.tDest1DLA,HeaderComposite.tDest2DLA,HeaderComposite.tDest3DLA,HeaderComposite.tDest4DLA,
						HeaderComposite.tDest5DLA,HeaderComposite.tDest6DLA,HeaderComposite.tDest7DLA,
						HeaderComposite.tDest8DLA, HeaderComposite.tDest9DLA, HeaderComposite.tDest10DLA, HeaderComposite.tDest11DLA,
						HeaderComposite.tDest12DLA, HeaderComposite.tDest13DLA, HeaderComposite.tDest14DLA, HeaderComposite.tDest15DLA,
						HeaderComposite.tDest16DLA, HeaderComposite.tDest17DLA, HeaderComposite.tDest18DLA, HeaderComposite.tDest19DLA,
						HeaderComposite.tDest20DLA, HeaderComposite.tDest21DLA);
				b18Clr("",ATSForms.tDofDLA);
			} else if (ID.endsWith("CHG")) {
				ATSForms.discardCHG();
				
				ATSForms.t3aCHG.setText("CHG");
				bodyClr("",ATSForms.t3bCHG,ATSForms.t3cCHG,ATSForms.t7bCHG,ATSForms.t7cCHG,ATSForms.t13aCHG,ATSForms.t13bCHG,ATSForms.t16aCHG,ATSForms.t16bCHG,ATSForms.t16cCHG,ATSForms.t16dCHG);
				ATSForms.t22CHG.setText("");
				headClr("",HeaderComposite.tDest1CHG,HeaderComposite.tDest2CHG,HeaderComposite.tDest3CHG,HeaderComposite.tDest4CHG,
						HeaderComposite.tDest5CHG,HeaderComposite.tDest6CHG,HeaderComposite.tDest7CHG,
						HeaderComposite.tDest8CHG, HeaderComposite.tDest9CHG, HeaderComposite.tDest10CHG, HeaderComposite.tDest11CHG,
						HeaderComposite.tDest12CHG, HeaderComposite.tDest13CHG, HeaderComposite.tDest14CHG, HeaderComposite.tDest15CHG,
						HeaderComposite.tDest16CHG, HeaderComposite.tDest17CHG, HeaderComposite.tDest18CHG, HeaderComposite.tDest19CHG,
						HeaderComposite.tDest20CHG, HeaderComposite.tDest21CHG);
				b18Clr("",ATSForms.tDofCHG);
			} else if (ID.endsWith("CNL")) {
				ATSForms.discardCNL();
				
				ATSForms.t3aCNL.setText("CNL");
				bodyClr("",ATSForms.t3bCNL,ATSForms.t3cCNL,ATSForms.t7bCNL,ATSForms.t7cCNL,ATSForms.t13aCNL,ATSForms.t13bCNL,ATSForms.t16aCNL,ATSForms.t16bCNL,ATSForms.t16cCNL,ATSForms.t16dCNL);
				headClr("",HeaderComposite.tDest1CNL,HeaderComposite.tDest2CNL,HeaderComposite.tDest3CNL,HeaderComposite.tDest4CNL,
						HeaderComposite.tDest5CNL,HeaderComposite.tDest6CNL,HeaderComposite.tDest7CNL,
						HeaderComposite.tDest8CNL, HeaderComposite.tDest9CNL, HeaderComposite.tDest10CNL, HeaderComposite.tDest11CNL,
						HeaderComposite.tDest12CNL, HeaderComposite.tDest13CNL, HeaderComposite.tDest14CNL, HeaderComposite.tDest15CNL,
						HeaderComposite.tDest16CNL, HeaderComposite.tDest17CNL, HeaderComposite.tDest18CNL, HeaderComposite.tDest19CNL,
						HeaderComposite.tDest20CNL, HeaderComposite.tDest21CNL);
				b18Clr("",ATSForms.tDofCNL);
			} else if (ID.endsWith("DEP")) {
				ATSForms.discardDEP();
				
				ATSForms.t3aDEP.setText("DEP");
				bodyClr("",ATSForms.t3bDEP,ATSForms.t3cDEP,ATSForms.t7bDEP,ATSForms.t7cDEP,ATSForms.t13aDEP,ATSForms.t13bDEP,ATSForms.t16aDEP,ATSForms.t16bDEP,ATSForms.t16cDEP,ATSForms.t16dDEP);
				headClr("",HeaderComposite.tDest1DEP,HeaderComposite.tDest2DEP,HeaderComposite.tDest3DEP,HeaderComposite.tDest4DEP,
						HeaderComposite.tDest5DEP,HeaderComposite.tDest6DEP,HeaderComposite.tDest7DEP,
						HeaderComposite.tDest8DEP, HeaderComposite.tDest9DEP, HeaderComposite.tDest10DEP, HeaderComposite.tDest11DEP,
						HeaderComposite.tDest12DEP, HeaderComposite.tDest13DEP, HeaderComposite.tDest14DEP, HeaderComposite.tDest15DEP,
						HeaderComposite.tDest16DEP, HeaderComposite.tDest17DEP, HeaderComposite.tDest18DEP, HeaderComposite.tDest19DEP,
						HeaderComposite.tDest20DEP, HeaderComposite.tDest21DEP);
				b18Clr("",ATSForms.tDofDEP);
			} else if (ID.endsWith("ARR")) {
				ATSForms.discardARR();
				
				ATSForms.t3aARR.setText("ARR"); ATSForms.t3bARR.setText(""); ATSForms.t3cARR.setText("");
				ATSForms.t7bARR.setSelection(false); ATSForms.t7cARR.setText(""); 
				ATSForms.t13aARR.setText(""); ATSForms.t13bARR.setText("");
				ATSForms.t17aARR.setText(""); ATSForms.t17bARR.setText(""); ATSForms.t17cARR.setText("");
				headClr("",HeaderComposite.tDest1ARR,HeaderComposite.tDest2ARR,HeaderComposite.tDest3ARR,HeaderComposite.tDest4ARR,
						HeaderComposite.tDest5ARR,HeaderComposite.tDest6ARR,HeaderComposite.tDest7ARR,
						HeaderComposite.tDest8ARR, HeaderComposite.tDest9ARR, HeaderComposite.tDest10ARR, HeaderComposite.tDest11ARR,
						HeaderComposite.tDest12ARR, HeaderComposite.tDest13ARR, HeaderComposite.tDest14ARR, HeaderComposite.tDest15ARR,
						HeaderComposite.tDest16ARR, HeaderComposite.tDest17ARR, HeaderComposite.tDest18ARR, HeaderComposite.tDest19ARR,
						HeaderComposite.tDest20ARR, HeaderComposite.tDest21ARR);
			} else if (ID.endsWith("CDN")) {
				ATSForms.discardCDN();
				
				ATSForms.t3aCDN.setText("CDN");
				bodyClr("",ATSForms.t3bCDN,ATSForms.t3cCDN,ATSForms.t7bCDN,ATSForms.t7cCDN,ATSForms.t13aCDN,ATSForms.t13bCDN,ATSForms.t16aCDN,ATSForms.t16bCDN,ATSForms.t16cCDN,ATSForms.t16dCDN);
				ATSForms.t22CDN.setText("");
				headClr("",HeaderComposite.tDest1CDN,HeaderComposite.tDest2CDN,HeaderComposite.tDest3CDN,HeaderComposite.tDest4CDN,
						HeaderComposite.tDest5CDN,HeaderComposite.tDest6CDN,HeaderComposite.tDest7CDN,
						HeaderComposite.tDest8CDN, HeaderComposite.tDest9CDN, HeaderComposite.tDest10CDN, HeaderComposite.tDest11CDN,
						HeaderComposite.tDest12CDN, HeaderComposite.tDest13CDN, HeaderComposite.tDest14CDN, HeaderComposite.tDest15CDN,
						HeaderComposite.tDest16CDN, HeaderComposite.tDest17CDN, HeaderComposite.tDest18CDN, HeaderComposite.tDest19CDN,
						HeaderComposite.tDest20CDN, HeaderComposite.tDest21CDN);
			} else if (ID.endsWith("ACP")) {
				ATSForms.discardACP();
				
				ATSForms.t3aACP.setText("ACP");
				bodyClr("",ATSForms.t3bACP,ATSForms.t3cACP,ATSForms.t7bACP,ATSForms.t7cACP,ATSForms.t13aACP,ATSForms.t13bACP,ATSForms.t16aACP,ATSForms.t16bACP,ATSForms.t16cACP,ATSForms.t16dACP);
				headClr("",HeaderComposite.tDest1ACP,HeaderComposite.tDest2ACP,HeaderComposite.tDest3ACP,HeaderComposite.tDest4ACP,
						HeaderComposite.tDest5ACP,HeaderComposite.tDest6ACP,HeaderComposite.tDest7ACP,
						HeaderComposite.tDest8ACP, HeaderComposite.tDest9ACP, HeaderComposite.tDest10ACP, HeaderComposite.tDest11ACP,
						HeaderComposite.tDest12ACP, HeaderComposite.tDest13ACP, HeaderComposite.tDest14ACP, HeaderComposite.tDest15ACP,
						HeaderComposite.tDest16ACP, HeaderComposite.tDest17ACP, HeaderComposite.tDest18ACP, HeaderComposite.tDest19ACP,
						HeaderComposite.tDest20ACP, HeaderComposite.tDest21ACP);
			} else if (ID.endsWith("EST")) {
				ATSForms.discardEST();
				
				ATSForms.t3aEST.setText("EST");
				bodyClr("",ATSForms.t3bEST,ATSForms.t3cEST,ATSForms.t7bEST,ATSForms.t7cEST,ATSForms.t13aEST,ATSForms.t13bEST,ATSForms.t16aEST,ATSForms.t16bEST,ATSForms.t16cEST,ATSForms.t16dEST);
				headClr("",HeaderComposite.tDest1EST,HeaderComposite.tDest2EST,HeaderComposite.tDest3EST,HeaderComposite.tDest4EST,
						HeaderComposite.tDest5EST,HeaderComposite.tDest6EST,HeaderComposite.tDest7EST,
						HeaderComposite.tDest8EST, HeaderComposite.tDest9EST, HeaderComposite.tDest10EST, HeaderComposite.tDest11EST,
						HeaderComposite.tDest12EST, HeaderComposite.tDest13EST, HeaderComposite.tDest14EST, HeaderComposite.tDest15EST,
						HeaderComposite.tDest16EST, HeaderComposite.tDest17EST, HeaderComposite.tDest18EST, HeaderComposite.tDest19EST,
						HeaderComposite.tDest20EST, HeaderComposite.tDest21EST);
			} else if (ID.endsWith("RQS")) {
				ATSForms.discardRQS();
				
				ATSForms.t3aRQS.setText("RQS");
				bodyClr("",ATSForms.t3bRQS,ATSForms.t3cRQS,ATSForms.t7bRQS,ATSForms.t7cRQS,ATSForms.t13aRQS,ATSForms.t13bRQS,ATSForms.t16aRQS,ATSForms.t16bRQS,ATSForms.t16cRQS,ATSForms.t16dRQS);
				headClr("",HeaderComposite.tDest1RQS,HeaderComposite.tDest2RQS,HeaderComposite.tDest3RQS,HeaderComposite.tDest4RQS,
						HeaderComposite.tDest5RQS,HeaderComposite.tDest6RQS,HeaderComposite.tDest7RQS,
						HeaderComposite.tDest8RQS, HeaderComposite.tDest9RQS, HeaderComposite.tDest10RQS, HeaderComposite.tDest11RQS,
						HeaderComposite.tDest12RQS, HeaderComposite.tDest13RQS, HeaderComposite.tDest14RQS, HeaderComposite.tDest15RQS,
						HeaderComposite.tDest16RQS, HeaderComposite.tDest17RQS, HeaderComposite.tDest18RQS, HeaderComposite.tDest19RQS,
						HeaderComposite.tDest20RQS, HeaderComposite.tDest21RQS);
				b18Clr("",ATSForms.tDofRQS);
			} else if (ID.endsWith("SPL")) {
				ATSForms.discardSPL();
				
				ATSForms.t3aSPL.setText("SPL"); 
				bodyClr("",ATSForms.t3bSPL,ATSForms.t3cSPL,ATSForms.t7bSPL,ATSForms.t7cSPL,ATSForms.t13aSPL,ATSForms.t13bSPL,ATSForms.t16aSPL,ATSForms.t16bSPL,ATSForms.t16cSPL,ATSForms.t16dSPL);
				
				ATSForms.tRegSPL.setText("");
				ATSForms.tDofSPL.setText("");
				
				ATSForms.t19aSPL.setText(""); ATSForms.t19bSPL.setText("");
				ATSForms.t19cUhfSPL.setText("X");
				ATSForms.t19cVhfSPL.setText("X");
				ATSForms.t19cEltSPL.setText("X");
				
				ATSForms.t19dPSPL.setText("X");
				ATSForms.t19dDSPL.setText("X");
				ATSForms.t19dMSPL.setText("X");
				ATSForms.t19dJSPL.setText("X");
				
				ATSForms.t19eLSPL.setText("X");
				ATSForms.t19eFSPL.setText("X");
				ATSForms.t19eUSPL.setText("X");
				ATSForms.t19eVSPL.setText("X");
				
				ATSForms.t19NumSPL.setText(""); ATSForms.t19CapSPL.setText(""); ATSForms.t19ColSPL.setText(""); 
				ATSForms.t19AirSPL.setText(""); ATSForms.t19RemSPL.setText(""); ATSForms.t19PilSPL.setText("");
				headClr("",HeaderComposite.tDest1SPL,HeaderComposite.tDest2SPL,HeaderComposite.tDest3SPL,HeaderComposite.tDest4SPL,
						HeaderComposite.tDest5SPL,HeaderComposite.tDest6SPL,HeaderComposite.tDest7SPL,
						HeaderComposite.tDest8SPL, HeaderComposite.tDest9SPL, HeaderComposite.tDest10SPL, HeaderComposite.tDest11SPL,
						HeaderComposite.tDest12SPL, HeaderComposite.tDest13SPL, HeaderComposite.tDest14SPL, HeaderComposite.tDest15SPL,
						HeaderComposite.tDest16SPL, HeaderComposite.tDest17SPL, HeaderComposite.tDest18SPL, HeaderComposite.tDest19SPL,
						HeaderComposite.tDest20SPL, HeaderComposite.tDest21SPL);
				b18Clr("",ATSForms.tRegSPL,ATSForms.tDofSPL,ATSForms.tNavSPL,ATSForms.tComSPL,ATSForms.tDatSPL,ATSForms.tSurSPL,ATSForms.tDepSPL,ATSForms.tDestSPL,ATSForms.tEetSPL,ATSForms.
						tSelSPL,ATSForms.tTypSPL,ATSForms.tCodeSPL,ATSForms.tDleSPL,ATSForms.tOprSPL,ATSForms.tOrgnSPL,ATSForms.tPerSPL,ATSForms.tAltnSPL,ATSForms.tRaltSPL,ATSForms.tTaltSPL,ATSForms.tRifSPL,ATSForms.
						tRmkSPL,ATSForms.tPbnSPL,ATSForms.tStsSPL);
			}

			while (rs.next() && (rowNo < baris)) {
				rowNo++;

				String des1="",des2="",des3="",des4="",des5="",des6="",des7="",des8="",des9="",des10="",des11="",des12="",des13="",des14="",
				des15="",des16="",des17="",des18="",des19="",des20="",des21="",
				t3a="",t3b="",t3c="",t7a="",t7b="",t7c="",t13a="",t13b="",t16a="",t16b="",t16c="",t16d="",t17a="",t17b="",t17c="",t18="",t22="",
				t19a="",t19b="",t19cUHF="",t19cVHF="",t19cELT="",t19dS="",t19dP="",t19dD="",t19dM="",t19dJ="",t19eJ="",t19eL="",t19eF="",t19eU="",t19eV="",t19fD="",t19f_number="",t19f_capacity="",t19f_cover="",t19f_colour="",t19g="",t19hN="",t19Remarks="",t19i="";
				
				des1 = rs.getString("destination1"); if (des1 == null) des1="";
				des2 = rs.getString("destination2"); if (des2 == null) des2="";
				des3 = rs.getString("destination3"); if (des3 == null) des3="";
				des4 = rs.getString("destination4"); if (des4 == null) des4="";
				des5 = rs.getString("destination5"); if (des5 == null) des5="";
				des6 = rs.getString("destination6"); if (des6 == null) des6="";
				des7 = rs.getString("destination7"); if (des7 == null) des7="";
				des8 = rs.getString("destination8"); if (des8 == null) des8="";
				des9 = rs.getString("destination9"); if (des9 == null) des9="";
				des10 = rs.getString("destination10"); if (des10 == null) des10="";
				des11 = rs.getString("destination11"); if (des11 == null) des11="";
				des12 = rs.getString("destination12"); if (des12 == null) des12="";
				des13 = rs.getString("destination13"); if (des13 == null) des13="";
				des14 = rs.getString("destination14"); if (des14 == null) des14="";
				des15 = rs.getString("destination15"); if (des15 == null) des15="";
				des16 = rs.getString("destination16"); if (des16 == null) des16="";
				des17 = rs.getString("destination17"); if (des17 == null) des17="";
				des18 = rs.getString("destination18"); if (des18 == null) des18="";
				des19 = rs.getString("destination19"); if (des19 == null) des19="";
				des20 = rs.getString("destination20"); if (des20 == null) des20="";
				des21 = rs.getString("destination21"); if (des21 == null) des21="";
				
				t3a = rs.getString("type3a"); if (t3a == null) t3a="";
				t3b = rs.getString("type3b"); if (t3b == null) t3b="";
				t3c = rs.getString("type3c"); if (t3c == null) t3c="";
				
				t7a = rs.getString("type7a"); if (t7a == null) t7a="";
				t7b = rs.getString("type7b"); if (t7b == null) t7b="";
				t7c = rs.getString("type7c"); if (t7c == null) t7c="";
				
				t13a = rs.getString("type13a"); if (t13a == null) t13a="";
				t13b = rs.getString("type13b"); if (t13b == null) t13b="";
				
				t16a = rs.getString("type16a"); if (t16a == null) t16a="";
				t16b = rs.getString("type16b"); if (t16b == null) t16b="";
				t16c = rs.getString("type16c"); if (t16c == null) t16c="";
				t16d = rs.getString("type16c_2nd"); if (t16d == null) t16d="";

				t17a = rs.getString("type17a"); if (t17a == null) t17a="";
				t17b = rs.getString("type17b"); if (t17b == null) t17b="";
				t17c = rs.getString("type17c"); if (t17c == null) t17c="";

				t18 = rs.getString("type18"); if (t18 == null) t18="";
				t22 = rs.getString("type22"); if (t22 == null) t22="";
				
				t19a = rs.getString("type19a"); if (t19a == null) t19a="";
				t19b = rs.getString("type19b"); if (t19b == null) t19b="";
				t19cUHF = rs.getString("type19cUHF"); if (t19cUHF == null) t19cUHF="";
				t19cVHF = rs.getString("type19cVHF"); if (t19cVHF == null) t19cVHF="";
				t19cELT = rs.getString("type19cELT"); if (t19cELT == null) t19cELT="";
				t19dS = rs.getString("type19dS"); if (t19dS == null) t19dS="";
				t19dP = rs.getString("type19dP"); if (t19dP == null) t19dP="";
				t19dD = rs.getString("type19dD"); if (t19dD == null) t19dD="";
				t19dM = rs.getString("type19dM"); if (t19dM == null) t19dM="";
				t19dJ = rs.getString("type19dJ"); if (t19dJ == null) t19dJ="";
				t19eJ = rs.getString("type19eJ"); if (t19eJ == null) t19eJ="";
				t19eL = rs.getString("type19eL"); if (t19eL == null) t19eL="";
				t19eF = rs.getString("type19eF"); if (t19eF == null) t19eF="";
				t19eU = rs.getString("type19eU"); if (t19eU == null) t19eU="";
				t19eV = rs.getString("type19eV"); if (t19eV == null) t19eV="";
				t19fD = rs.getString("type19fD"); if (t19fD == null) t19fD="";
				t19f_number = rs.getString("type19f_number"); if (t19f_number == null) t19f_number="";
				t19f_capacity = rs.getString("type19f_capacity"); if (t19f_capacity == null) t19f_capacity="";
				t19f_cover = rs.getString("type19f_cover"); if (t19f_cover == null) t19f_cover="";
				t19f_colour = rs.getString("type19f_colour"); if (t19f_colour == null) t19f_colour="";
				t19g = rs.getString("type19g"); if (t19g == null) t19g="";
				t19hN = rs.getString("type19hN"); if (t19hN == null) t19hN="";
				t19Remarks = rs.getString("type19Remarks"); if (t19Remarks == null) t19Remarks="";
				t19i = rs.getString("type19i"); if (t19i == null) t19i="";
				
				String tFiledby = rs.getString("filedby"); if (tFiledby == null) tFiledby="";
				
				// -------------------------------------------------------
				//----------- begin of type18
				String sNav="",sCom="",sDat="",sSur="",sDep="",sDest="",sDof="",
				sReg="",sEet="",sSel="",sTyp="",sCode="",sDle="",sOpr="",sOrgn="",
				sPer="",sAltn="",sRalt="",sTalt="",sRif="",sRmk="",sPbn="",sSts="",other="";
				
		     	String type18 = t18;			     	
				if (type18.startsWith(" ")) type18=type18.replaceFirst("\\s+", "");
				if (type18.compareTo("0")==0) type18="";
		     	if (!type18.equals("")) {
					int i=0;
					String x="";
				        
					if (type18.contains("\n")) {
			        	String subi[] = type18.split("\n");
			        	
			        	other="";sSts="";sPbn="";sNav=""; sCom="";sDat="";sSur=""; sDep="";sDest="";sDof=""; sReg="";sEet="";sSel=""; sTyp="";
			        	sCode="";sDle="";sOpr="";sOrgn="";sPer=""; sAltn="";sRalt="";sTalt=""; sRif="";sRmk="";
			 	   			
			        	for (int ii=0; ii<subi.length; ii++) {
			        		String sub[] = subi[ii].split(" ");
			        		
			 	   			for (i=0; i<sub.length; i++) {
			 	   				if (sub[i].contains("/")) {
			 	   					x=sub[i];
			 	   				} else {
			 	   					x+=" "+sub[i];
			 	   				}
			
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
								else if (x.startsWith("PER/")) sPer = x.substring(4, x.length());
								
								else if (x.startsWith("ALTN/")) sAltn = x.substring(5, x.length());
								else if (x.startsWith("RALT/")) sRalt = x.substring(5, x.length());
								else if (x.startsWith("TALT/")) sTalt = x.substring(5, x.length());
								
								else if (x.startsWith("RIF/")) sRif = x.substring(4, x.length());
								else if (x.startsWith("RMK/")) sRmk = x.substring(4, x.length());
			 	   				else { other+= " "+x; }
			 	   			} //end of for sub[i]
			 				if (other.startsWith(" ")) other=other.replaceFirst(" ", "");
			 				if (sRmk.compareTo("")==0) { sRmk=other; } else { sRmk+=" "+other; }
			        	} //end of for subi[i]
			        } //end of if "\n"
			        else { //ELSE of if "\n"
			        	other="";sSts="";sPbn="";sNav=""; sCom="";sDat="";sSur=""; sDep="";sDest="";sDof=""; sReg="";sEet="";sSel=""; sTyp="";
			        	sCode="";sDle="";sOpr="";sOrgn="";sPer=""; sAltn="";sRalt="";sTalt=""; sRif="";sRmk="";
			   			
						String sub[] = type18.split(" ");
						for (i=0; i<sub.length; i++) {
							if (sub[i].contains("/")) {
								x=sub[i];
							} else {
								x+=" "+sub[i];
							}

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
							else if (x.startsWith("PER/")) sPer = x.substring(4, x.length());
							
							else if (x.startsWith("ALTN/")) sAltn = x.substring(5, x.length());
							else if (x.startsWith("RALT/")) sRalt = x.substring(5, x.length());
							else if (x.startsWith("TALT/")) sTalt = x.substring(5, x.length());
							
							else if (x.startsWith("RIF/")) sRif = x.substring(4, x.length());
							else if (x.startsWith("RMK/")) sRmk = x.substring(4, x.length());
							else { other+= " "+x; }
						} //end of for sub[i]
					 
						if (other.startsWith(" ")) other=other.replaceFirst(" ", "");
						if (sRmk.compareTo("")==0) { sRmk=other; } else { sRmk+=" "+other; }
			        }
				} else {
					sSts="";sPbn="";sNav=""; sCom="";sDat="";sSur=""; sDep="";sDest="";sDof=""; sReg="";sEet="";sSel=""; sTyp="";sCode="";sDle="";
					sOpr="";sOrgn="";sPer=""; sAltn="";sRalt="";sTalt=""; sRif="";sRmk="";
				}
				//---------------------------------------------------------
				if (sSts.compareTo("")==0) /*sSts=" STS/"+sSts; else*/ sSts="";
				if (sPbn.compareTo("")==0) /*sPbn=" PBN/"+sPbn; else*/ sPbn="";
				if (sNav.compareTo("")==0) /*sNav=" NAV/"+sNav; else*/ sNav="";
				 
				if (sCom.compareTo("")==0) /*sCom=" COM/"+sCom; else*/ sCom="";
				if (sDat.compareTo("")==0) /*sDat=" DAT/"+sDat; else*/ sDat="";
				if (sSur.compareTo("")==0) /*sSur=" SUR/"+sSur; else*/ sSur="";
					
				if (sDep.compareTo("")==0) /*sDep=" DEP/"+sDep; else*/ sDep="";
				if (sDest.compareTo("")==0) /*sDest=" DEST/"+sDest; else*/ sDest="";
				if (sReg.compareTo("")==0) /*sReg=" REG/"+sReg; else*/ sReg="";
					
				if (sDof.compareTo("")==0) /*sDof=" DOF/"+sDof; else*/ sDof="";
				if (sEet.compareTo("")==0) /*sEet=" EET/"+sEet; else*/ sEet="";
				if (sSel.compareTo("")==0) /*sSel=" SEL/"+sSel; else*/ sSel="";
					
				if (sTyp.compareTo("")==0) /*sTyp=" TYP/"+sTyp; else*/ sTyp="";
				if (sCode.compareTo("")==0) /*sCode=" CODE/"+sCode; else*/ sCode="";
				if (sDle.compareTo("")==0) /*sDle=" DLE/"+sDle; else*/ sDle="";
					
				if (sOpr.compareTo("")==0) /*sOpr=" OPR/"+sOpr; else*/ sOpr="";
				if (sOrgn.compareTo("")==0) /*sOrgn=" ORGN/"+sOrgn; else*/ sOrgn="";
				if (sPer.compareTo("")==0) /*sPer=" PER/"+sPer; else*/ sPer="";
					
				if (sAltn.compareTo("")==0) /*sAltn=" ALTN/"+sAltn; else*/ sAltn="";
				if (sRalt.compareTo("")==0) /*sRalt=" RALT/"+sRalt; else*/ sRalt="";
				if (sTalt.compareTo("")==0) /*sTalt=" TALT/"+sTalt; else*/ sTalt="";
					
				if (sRif.compareTo("")==0) /*sRif=" RIF/"+sRif; else*/ sRif="";
				if (sRmk.compareTo("")==0) /*sRmk=" RMK/"+sRmk; else*/ sRmk="";	
				//---------------------------------------------------------
				if (!sRmk.isEmpty() && sRmk.endsWith(" ")) {
					int ind = sRmk.lastIndexOf(" ");
					sRmk = sRmk.substring(0, ind);
				}
				if (!sPer.isEmpty() && sPer.length()>1) {
					sPer = sPer.substring(0, 1);
				}
//				//---------------------------------------------------------
//				String type18a = P37;//rs.getString("type18a"); if (type18a == null) type18a="";
//				if (type18a.equals("REG/0")) type18a=""; 
//				else if (!type18a.equals("") && !type18a.equals("REG/0") && type18a.startsWith("REG/")) type18a = type18a.replaceFirst("REG/", "");
//				//---------------------------------------------------------
//				String type18b = P38;//rs.getString("type18b"); if (type18b == null) type18b="";
//				if (type18b.equals("DOF/0")) type18b=""; 
//				else if (!type18b.equals("") && !type18b.equals("DOF/0") && type18b.startsWith("DOF/")) type18b = type18b.replaceFirst("DOF/", "");
//				//---------------------------------------------------------
				
				//REG/
				String s_Reg="",s_Dof="";
//				if (type18a.compareTo("")==0) {
					s_Reg=sReg;
//				} else {
//					s_Reg=type18a;
//				}
				if (s_Reg.equals("0")) s_Reg="";
				//DOF/
//				if (type18b.compareTo("")==0) {
					s_Dof=sDof;
//				} else {
//					s_Dof=type18b;
//				}
				if (s_Dof.equals("0")) s_Dof="";
				
				//ALL18/
				String aChar="",strDof="",strReg="",strPbn="",strSur="",strCode="",strDle="",strOrgn="",strTalt="";
				String strType18 = t18;
				if (strType18.startsWith(" ")) strType18=strType18.replaceFirst("\\s+", "");
				if (strType18.compareTo("0")==0) strType18="";
				if (!strType18.equals("")) {
					if (strType18.startsWith(" ")) strType18 = strType18.replaceFirst("\\s+", ""); //hapus spasi di awal Item 18
		 	        if (strType18.contains("\n")) {
		 	        	aChar = new Character((char)126).toString(); //kode ASCII 126='~'
		 	        	strType18 = strType18.replaceAll("\n", aChar);
		 	        } 

		 	        strPbn="";strSur="";strDof="";strReg="";strCode="";strDle="";strOrgn="";strTalt="";

		 	        String x="";
		 	        String sub[] = strType18.split(" ");
		 	        for (int i=0; i<sub.length; i++) {
		 	        	if (sub[i].contains("/")) { x=sub[i]; } 
		 	        	else { x+=" "+sub[i]; }
		 	        	//-------------------------------------
		 	        	if (x.startsWith("DOF/")) strDof = x.substring(4, x.length());
		 	        	else if (x.startsWith("~DOF/")) strDof = x.substring(5, x.length());
		 	        	//--
						if (x.startsWith("REG/")) strReg = x.substring(4, x.length());
						else if (x.startsWith("~REG/")) strReg = x.substring(5, x.length());
						//--
						if (x.startsWith("PBN/")) strPbn = x.substring(4, x.length());
						else if (x.startsWith("~PBN/")) strPbn = x.substring(5, x.length());
						//--
						if (x.startsWith("SUR/")) strSur = x.substring(4, x.length());
						else if (x.startsWith("~SUR/")) strSur = x.substring(5, x.length());
						//--
		 	        	if (x.startsWith("CODE/")) strCode = x.substring(5, x.length());
		 	        	else if (x.startsWith("~CODE/")) strCode = x.substring(6, x.length());
		 	        	//--
						if (x.startsWith("DLE/")) strDle = x.substring(4, x.length());
						else if (x.startsWith("~DLE/")) strDle = x.substring(5, x.length());
						//--
						if (x.startsWith("ORGN/")) strOrgn = x.substring(5, x.length());
						else if (x.startsWith("~ORGN/")) strOrgn = x.substring(6, x.length());
						//--
						if (x.startsWith("TALT/")) strTalt = x.substring(5, x.length());
						else if (x.startsWith("~TALT/")) strTalt = x.substring(6, x.length());
		 	        } //end of for sub[i]
				} else { //else of strType18==null
		 	        strPbn="";strSur="";strDof="";strReg="";strCode="";strDle="";strOrgn="";strTalt="";
				}
				//------------------------------------------------------------------
				//--mengembalikan enter
				if (strPbn.contains(aChar)) strPbn = strPbn.replaceFirst(aChar, "\n");
				if (strSur.contains(aChar)) strSur = strSur.replaceFirst(aChar, "\n");
				if (strDof.contains(aChar)) strDof = strDof.replaceFirst(aChar, "\n");
				if (strReg.contains(aChar)) strReg = strReg.replaceFirst(aChar, "\n");
				if (strCode.contains(aChar)) strCode = strCode.replaceFirst(aChar, "\n");
				if (strDle.contains(aChar)) strDle = strDle.replaceFirst(aChar, "\n");
				if (strOrgn.contains(aChar)) strOrgn = strOrgn.replaceFirst(aChar, "\n");
				if (strTalt.contains(aChar)) strTalt = strTalt.replaceFirst(aChar, "\n");
				//--hapus enter di awal berita 
				if (strPbn.startsWith("\n")) strPbn = strPbn.replaceFirst("\n", "");
				if (strSur.startsWith("\n")) strSur = strSur.replaceFirst("\n", "");
				if (strDof.startsWith("\n")) strDof = strDof.replaceFirst("\n", "");
				if (strReg.startsWith("\n")) strReg = strReg.replaceFirst("\n", "");
				if (strCode.startsWith("\n")) strCode = strCode.replaceFirst("\n", "");
				if (strDle.startsWith("\n")) strDle = strDle.replaceFirst("\n", "");
				if (strOrgn.startsWith("\n")) strOrgn = strOrgn.replaceFirst("\n", "");
				if (strTalt.startsWith("\n")) strTalt = strTalt.replaceFirst("\n", "");
				//--hapus spasi di awal berita 
				if (strPbn.startsWith(" ")) strPbn = strPbn.replaceFirst("\\s+", "");
				if (strSur.startsWith(" ")) strSur = strSur.replaceFirst("\\s+", "");
				if (strDof.startsWith(" ")) strDof = strDof.replaceFirst("\\s+", "");
				if (strReg.startsWith(" ")) strReg = strReg.replaceFirst("\\s+", "");
				if (strCode.startsWith(" ")) strCode = strCode.replaceFirst("\\s+", "");
				if (strDle.startsWith(" ")) strDle = strDle.replaceFirst("\\s+", "");
				if (strOrgn.startsWith(" ")) strOrgn = strOrgn.replaceFirst("\\s+", "");
				if (strTalt.startsWith(" ")) strTalt = strTalt.replaceFirst("\\s+", "");
				
				//kalo ada REG/ atau DOF/ di tipe18, tidak di tampilkan di Text 18 karena di tampilkan di Text REG dan di Text DOF
				if (strType18.contains(" REG/"+s_Reg)) { strType18 = strType18.replace(" REG/"+s_Reg, ""); } 
				else if (strType18.contains("REG/"+s_Reg)) { strType18 = strType18.replace("REG/"+s_Reg, ""); }
				
				if (strType18.contains(" DOF/"+s_Dof)) { strType18 = strType18.replace(" DOF/"+s_Dof, ""); } 
				else if (strType18.contains("DOF/"+s_Dof)) { strType18 = strType18.replace("DOF/"+s_Dof, ""); }
				
				//untuk PBN/ SUR/ CODE/ DLE/ ORGN/ TALT tidak perlu di tampilkan di Text 18 
				if (strType18.contains(" PBN/"+strPbn)) { strType18 = strType18.replace(" PBN/"+strPbn, ""); } 
				else if (strType18.contains("PBN/"+strPbn)) { strType18 = strType18.replace("PBN/"+strPbn, ""); }
				
				if (strType18.contains(" SUR/"+strSur)) { strType18 = strType18.replace(" SUR/"+strSur, ""); } 
				else if (strType18.contains("SUR/"+strSur)) { strType18 = strType18.replace("SUR/"+strSur, ""); }
				
				if (strType18.contains(" CODE/"+strCode)) { strType18 = strType18.replace(" CODE/"+strCode, ""); } 
				else if (strType18.contains("CODE/"+strCode)) { strType18 = strType18.replace("CODE/"+strCode, ""); }
				
				if (strType18.contains(" DLE/"+strDle)) { strType18 = strType18.replace(" DLE/"+strDle, ""); } 
				else if (strType18.contains("DLE/"+strDle)) { strType18 = strType18.replace("DLE/"+strDle, ""); }
				
				if (strType18.contains(" ORGN/"+strOrgn)) { strType18 = strType18.replace(" ORGN/"+strOrgn, ""); } 
				else if (strType18.contains("ORGN/"+strOrgn)) { strType18 = strType18.replace("ORGN/"+strOrgn, ""); }
				
				if (strType18.contains(" TALT/"+strTalt)) { strType18 = strType18.replace(" TALT/"+strTalt, ""); } 
				else if (strType18.contains("TALT/"+strTalt)) { strType18 = strType18.replace("TALT/"+strTalt, ""); }
				
				if (strType18.startsWith(" ")) strType18 = strType18.replaceFirst("\\s+", "");
				// -------------------------------------------------------
				if (ID.endsWith("DLA")) {
					ATSForms.t3aDLA.setText("DLA"); ATSForms.t3bDLA.setText(t3b); ATSForms.t3cDLA.setText(t3c);
					ATSForms.t7aDLA.setText(t7a); if (t7b.equals("A")) ATSForms.t7bDLA.setSelection(true); ATSForms.t7cDLA.setText(t7c);
					ATSForms.t13aDLA.setText(t13a); ATSForms.t13bDLA.setText(t13b);
					ATSForms.t16aDLA.setText(t16a); //ATSForms.t16bDLA.setText(t16b); ATSForms.t16cDLA.setText(t16c); ATSForms.t16dDLA.setText(t16d);
					ATSForms.tDofDLA.setText(s_Dof);
					ATSForms.tFbDLA.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t16bDLA.setText(""); ATSForms.t16cDLA.setText(""); ATSForms.t16dDLA.setText("");
					} else {
						ATSForms.t16bDLA.setText(t16b); ATSForms.t16cDLA.setText(t16c); ATSForms.t16dDLA.setText(t16d);
					}
					
					HeaderComposite.tDest1DLA.setText(des1);
					HeaderComposite.tDest2DLA.setText(des2);
					HeaderComposite.tDest3DLA.setText(des3);
					HeaderComposite.tDest4DLA.setText(des4);
					HeaderComposite.tDest5DLA.setText(des5);
					HeaderComposite.tDest6DLA.setText(des6);
					HeaderComposite.tDest7DLA.setText(des7);
					HeaderComposite.tDest8DLA.setText(des8);
					HeaderComposite.tDest9DLA.setText(des9);
					HeaderComposite.tDest10DLA.setText(des10);
					HeaderComposite.tDest11DLA.setText(des11);
					HeaderComposite.tDest12DLA.setText(des12);
					HeaderComposite.tDest13DLA.setText(des13);
					HeaderComposite.tDest14DLA.setText(des14);
					HeaderComposite.tDest15DLA.setText(des15);
					HeaderComposite.tDest16DLA.setText(des16);
					HeaderComposite.tDest17DLA.setText(des17);
					HeaderComposite.tDest18DLA.setText(des18);
					HeaderComposite.tDest19DLA.setText(des19);
					HeaderComposite.tDest20DLA.setText(des20);
					HeaderComposite.tDest21DLA.setText(des21);
				} else if (ID.endsWith("CHG")) {
					ATSForms.t3aCHG.setText("CHG"); ATSForms.t3bCHG.setText(t3b); ATSForms.t3cCHG.setText(t3c);
					ATSForms.t7aCHG.setText(t7a); if (t7b.equals("A")) ATSForms.t7bCHG.setSelection(true); ATSForms.t7cCHG.setText(t7c);
					ATSForms.t13aCHG.setText(t13a); //ATSForms.t13bCHG.setText(t13b);
					ATSForms.t16aCHG.setText(t16a); //ATSForms.t16bCHG.setText(t16b); ATSForms.t16cCHG.setText(t16c); ATSForms.t16dCHG.setText(t16d);
					ATSForms.tDofCHG.setText(s_Dof);
					ATSForms.t22CHG.setText(t22);
					ATSForms.tFbCHG.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t13bCHG.setText("");
						ATSForms.t16bCHG.setText(""); ATSForms.t16cCHG.setText(""); ATSForms.t16dCHG.setText("");
					} else {
						ATSForms.t13bCHG.setText(t13b);
						ATSForms.t16bCHG.setText(t16b); ATSForms.t16cCHG.setText(t16c); ATSForms.t16dCHG.setText(t16d);
					}
					
					HeaderComposite.tDest1CHG.setText(des1);
					HeaderComposite.tDest2CHG.setText(des2);
					HeaderComposite.tDest3CHG.setText(des3);
					HeaderComposite.tDest4CHG.setText(des4);
					HeaderComposite.tDest5CHG.setText(des5);
					HeaderComposite.tDest6CHG.setText(des6);
					HeaderComposite.tDest7CHG.setText(des7);
					HeaderComposite.tDest8CHG.setText(des8);
					HeaderComposite.tDest9CHG.setText(des9);
					HeaderComposite.tDest10CHG.setText(des10);
					HeaderComposite.tDest11CHG.setText(des11);
					HeaderComposite.tDest12CHG.setText(des12);
					HeaderComposite.tDest13CHG.setText(des13);
					HeaderComposite.tDest14CHG.setText(des14);
					HeaderComposite.tDest15CHG.setText(des15);
					HeaderComposite.tDest16CHG.setText(des16);
					HeaderComposite.tDest17CHG.setText(des17);
					HeaderComposite.tDest18CHG.setText(des18);
					HeaderComposite.tDest19CHG.setText(des19);
					HeaderComposite.tDest20CHG.setText(des20);
					HeaderComposite.tDest21CHG.setText(des21);
				} else if (ID.endsWith("CNL")) {
					ATSForms.t3aCNL.setText("CNL"); ATSForms.t3bCNL.setText(t3b); ATSForms.t3cCNL.setText(t3c);
					ATSForms.t7aCNL.setText(t7a); if (t7b.equals("A")) ATSForms.t7bCNL.setSelection(true); ATSForms.t7cCNL.setText(t7c);
					ATSForms.t13aCNL.setText(t13a); //ATSForms.t13bCNL.setText(t13b);
					ATSForms.t16aCNL.setText(t16a); //ATSForms.t16bCNL.setText(t16b); ATSForms.t16cCNL.setText(t16c); ATSForms.t16dCNL.setText(t16d);
					ATSForms.tDofCNL.setText(s_Dof);
					ATSForms.tFbCNL.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t13bCNL.setText("");
						ATSForms.t16bCNL.setText(""); ATSForms.t16cCNL.setText(""); ATSForms.t16dCNL.setText("");
					} else {
						ATSForms.t13bCNL.setText(t13b);
						ATSForms.t16bCNL.setText(t16b); ATSForms.t16cCNL.setText(t16c); ATSForms.t16dCNL.setText(t16d);
					}
					
					HeaderComposite.tDest1CNL.setText(des1);
					HeaderComposite.tDest2CNL.setText(des2);
					HeaderComposite.tDest3CNL.setText(des3);
					HeaderComposite.tDest4CNL.setText(des4);
					HeaderComposite.tDest5CNL.setText(des5);
					HeaderComposite.tDest6CNL.setText(des6);
					HeaderComposite.tDest7CNL.setText(des7);
					HeaderComposite.tDest8CNL.setText(des8);
					HeaderComposite.tDest9CNL.setText(des9);
					HeaderComposite.tDest10CNL.setText(des10);
					HeaderComposite.tDest11CNL.setText(des11);
					HeaderComposite.tDest12CNL.setText(des12);
					HeaderComposite.tDest13CNL.setText(des13);
					HeaderComposite.tDest14CNL.setText(des14);
					HeaderComposite.tDest15CNL.setText(des15);
					HeaderComposite.tDest16CNL.setText(des16);
					HeaderComposite.tDest17CNL.setText(des17);
					HeaderComposite.tDest18CNL.setText(des18);
					HeaderComposite.tDest19CNL.setText(des19);
					HeaderComposite.tDest20CNL.setText(des20);
					HeaderComposite.tDest21CNL.setText(des21);
				} else if (ID.endsWith("DEP")) {
					ATSForms.t3aDEP.setText("DEP"); ATSForms.t3bDEP.setText(t3b); ATSForms.t3cDEP.setText(t3c);
					ATSForms.t7aDEP.setText(t7a); if (t7b.equals("A")) ATSForms.t7bDEP.setSelection(true); ATSForms.t7cDEP.setText(t7c);
					ATSForms.t13aDEP.setText(t13a); ATSForms.t13bDEP.setText(t13b);
					ATSForms.t16aDEP.setText(t16a); //ATSForms.t16bDEP.setText(t16b); ATSForms.t16cDEP.setText(t16c); ATSForms.t16dDEP.setText(t16d);
					ATSForms.tDofDEP.setText(s_Dof);
					ATSForms.tFbDEP.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t16bDEP.setText(""); ATSForms.t16cDEP.setText(""); ATSForms.t16dDEP.setText("");
					} else {
						ATSForms.t16bDEP.setText(t16b); ATSForms.t16cDEP.setText(t16c); ATSForms.t16dDEP.setText(t16d);
					}
					
					HeaderComposite.tDest1DEP.setText(des1);
					HeaderComposite.tDest2DEP.setText(des2);
					HeaderComposite.tDest3DEP.setText(des3);
					HeaderComposite.tDest4DEP.setText(des4);
					HeaderComposite.tDest5DEP.setText(des5);
					HeaderComposite.tDest6DEP.setText(des6);
					HeaderComposite.tDest7DEP.setText(des7);
					HeaderComposite.tDest8DEP.setText(des8);
					HeaderComposite.tDest9DEP.setText(des9);
					HeaderComposite.tDest10DEP.setText(des10);
					HeaderComposite.tDest11DEP.setText(des11);
					HeaderComposite.tDest12DEP.setText(des12);
					HeaderComposite.tDest13DEP.setText(des13);
					HeaderComposite.tDest14DEP.setText(des14);
					HeaderComposite.tDest15DEP.setText(des15);
					HeaderComposite.tDest16DEP.setText(des16);
					HeaderComposite.tDest17DEP.setText(des17);
					HeaderComposite.tDest18DEP.setText(des18);
					HeaderComposite.tDest19DEP.setText(des19);
					HeaderComposite.tDest20DEP.setText(des20);
					HeaderComposite.tDest21DEP.setText(des21);
				} else if (ID.endsWith("ARR")) {
					ATSForms.t3aARR.setText("ARR"); ATSForms.t3bARR.setText(t3b); ATSForms.t3cARR.setText(t3c);
					ATSForms.t7aARR.setText(t7a); if (t7b.equals("A")) ATSForms.t7bARR.setSelection(true); ATSForms.t7cARR.setText(t7c);
					ATSForms.t13aARR.setText(t13a); //ATSForms.t13bARR.setText(t13b);
					ATSForms.t17aARR.setText(t17a); ATSForms.t17bARR.setText(t17b); ATSForms.t17cARR.setText(t17c);
					ATSForms.tFbARR.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t13bARR.setText("");
					} else {
						ATSForms.t13bARR.setText(t13b);
					}
					
					HeaderComposite.tDest1ARR.setText(des1);
					HeaderComposite.tDest2ARR.setText(des2);
					HeaderComposite.tDest3ARR.setText(des3);
					HeaderComposite.tDest4ARR.setText(des4);
					HeaderComposite.tDest5ARR.setText(des5);
					HeaderComposite.tDest6ARR.setText(des6);
					HeaderComposite.tDest7ARR.setText(des7);
					HeaderComposite.tDest8ARR.setText(des8);
					HeaderComposite.tDest9ARR.setText(des9);
					HeaderComposite.tDest10ARR.setText(des10);
					HeaderComposite.tDest11ARR.setText(des11);
					HeaderComposite.tDest12ARR.setText(des12);
					HeaderComposite.tDest13ARR.setText(des13);
					HeaderComposite.tDest14ARR.setText(des14);
					HeaderComposite.tDest15ARR.setText(des15);
					HeaderComposite.tDest16ARR.setText(des16);
					HeaderComposite.tDest17ARR.setText(des17);
					HeaderComposite.tDest18ARR.setText(des18);
					HeaderComposite.tDest19ARR.setText(des19);
					HeaderComposite.tDest20ARR.setText(des20);
					HeaderComposite.tDest21ARR.setText(des21);
				} else if (ID.endsWith("CDN")) {
					ATSForms.t3aCDN.setText("CDN"); ATSForms.t3bCDN.setText(t3b); ATSForms.t3cCDN.setText(t3c);
					ATSForms.t7aCDN.setText(t7a); if (t7b.equals("A")) ATSForms.t7bCDN.setSelection(true); ATSForms.t7cCDN.setText(t7c);
					ATSForms.t13aCDN.setText(t13a); //ATSForms.t13bCDN.setText(t13b);
					ATSForms.t16aCDN.setText(t16a); //ATSForms.t16bCDN.setText(t16b); ATSForms.t16cCDN.setText(t16c); ATSForms.t16dCDN.setText(t16d);
					ATSForms.t22CDN.setText(t22);
					ATSForms.tFbCDN.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t13bCDN.setText("");
						ATSForms.t16bCDN.setText(""); ATSForms.t16cCDN.setText(""); ATSForms.t16dCDN.setText("");
					} else {
						ATSForms.t13bCDN.setText(t13b);
						ATSForms.t16bCDN.setText(t16b); ATSForms.t16cCDN.setText(t16c); ATSForms.t16dCDN.setText(t16d);
					}
					
					HeaderComposite.tDest1CDN.setText(des1);
					HeaderComposite.tDest2CDN.setText(des2);
					HeaderComposite.tDest3CDN.setText(des3);
					HeaderComposite.tDest4CDN.setText(des4);
					HeaderComposite.tDest5CDN.setText(des5);
					HeaderComposite.tDest6CDN.setText(des6);
					HeaderComposite.tDest7CDN.setText(des7);
					HeaderComposite.tDest8CDN.setText(des8);
					HeaderComposite.tDest9CDN.setText(des9);
					HeaderComposite.tDest10CDN.setText(des10);
					HeaderComposite.tDest11CDN.setText(des11);
					HeaderComposite.tDest12CDN.setText(des12);
					HeaderComposite.tDest13CDN.setText(des13);
					HeaderComposite.tDest14CDN.setText(des14);
					HeaderComposite.tDest15CDN.setText(des15);
					HeaderComposite.tDest16CDN.setText(des16);
					HeaderComposite.tDest17CDN.setText(des17);
					HeaderComposite.tDest18CDN.setText(des18);
					HeaderComposite.tDest19CDN.setText(des19);
					HeaderComposite.tDest20CDN.setText(des20);
					HeaderComposite.tDest21CDN.setText(des21);
				} else if (ID.endsWith("ACP")) {
					ATSForms.t3aACP.setText("ACP"); ATSForms.t3bACP.setText(t3b); ATSForms.t3cACP.setText(t3c);
					ATSForms.t7aACP.setText(t7a); if (t7b.equals("A")) ATSForms.t7bACP.setSelection(true); ATSForms.t7cACP.setText(t7c);
					ATSForms.t13aACP.setText(t13a); //ATSForms.t13bACP.setText(t13b);
					ATSForms.t16aACP.setText(t16a); //ATSForms.t16bACP.setText(t16b); ATSForms.t16cACP.setText(t16c); ATSForms.t16dACP.setText(t16d);
					ATSForms.tFbACP.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t13bACP.setText("");
						ATSForms.t16bACP.setText(""); ATSForms.t16cACP.setText(""); ATSForms.t16dACP.setText("");
					} else {
						ATSForms.t13bACP.setText(t13b);
						ATSForms.t16bACP.setText(t16b); ATSForms.t16cACP.setText(t16c); ATSForms.t16dACP.setText(t16d);
					}
					
					HeaderComposite.tDest1ACP.setText(des1);
					HeaderComposite.tDest2ACP.setText(des2);
					HeaderComposite.tDest3ACP.setText(des3);
					HeaderComposite.tDest4ACP.setText(des4);
					HeaderComposite.tDest5ACP.setText(des5);
					HeaderComposite.tDest6ACP.setText(des6);
					HeaderComposite.tDest7ACP.setText(des7);
					HeaderComposite.tDest8ACP.setText(des8);
					HeaderComposite.tDest9ACP.setText(des9);
					HeaderComposite.tDest10ACP.setText(des10);
					HeaderComposite.tDest11ACP.setText(des11);
					HeaderComposite.tDest12ACP.setText(des12);
					HeaderComposite.tDest13ACP.setText(des13);
					HeaderComposite.tDest14ACP.setText(des14);
					HeaderComposite.tDest15ACP.setText(des15);
					HeaderComposite.tDest16ACP.setText(des16);
					HeaderComposite.tDest17ACP.setText(des17);
					HeaderComposite.tDest18ACP.setText(des18);
					HeaderComposite.tDest19ACP.setText(des19);
					HeaderComposite.tDest20ACP.setText(des20);
					HeaderComposite.tDest21ACP.setText(des21);
				} else if (ID.endsWith("EST")) {
					ATSForms.t3aEST.setText("EST"); ATSForms.t3bEST.setText(t3b); ATSForms.t3cEST.setText(t3c);
					ATSForms.t7aEST.setText(t7a); if (t7b.equals("A")) ATSForms.t7bEST.setSelection(true); ATSForms.t7cEST.setText(t7c);
					ATSForms.t13aEST.setText(t13a); //ATSForms.t13bEST.setText(t13b);
					ATSForms.t16aEST.setText(t16a); //ATSForms.t16bEST.setText(t16b); ATSForms.t16cEST.setText(t16c); ATSForms.t16dEST.setText(t16d);
					ATSForms.tFbEST.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t13bEST.setText("");
						ATSForms.t16bEST.setText(""); ATSForms.t16cEST.setText(""); ATSForms.t16dEST.setText("");
					} else {
						ATSForms.t13bEST.setText(t13b);
						ATSForms.t16bEST.setText(t16b); ATSForms.t16cEST.setText(t16c); ATSForms.t16dEST.setText(t16d);
					}
					
					HeaderComposite.tDest1EST.setText(des1);
					HeaderComposite.tDest2EST.setText(des2);
					HeaderComposite.tDest3EST.setText(des3);
					HeaderComposite.tDest4EST.setText(des4);
					HeaderComposite.tDest5EST.setText(des5);
					HeaderComposite.tDest6EST.setText(des6);
					HeaderComposite.tDest7EST.setText(des7);
					HeaderComposite.tDest8EST.setText(des8);
					HeaderComposite.tDest9EST.setText(des9);
					HeaderComposite.tDest10EST.setText(des10);
					HeaderComposite.tDest11EST.setText(des11);
					HeaderComposite.tDest12EST.setText(des12);
					HeaderComposite.tDest13EST.setText(des13);
					HeaderComposite.tDest14EST.setText(des14);
					HeaderComposite.tDest15EST.setText(des15);
					HeaderComposite.tDest16EST.setText(des16);
					HeaderComposite.tDest17EST.setText(des17);
					HeaderComposite.tDest18EST.setText(des18);
					HeaderComposite.tDest19EST.setText(des19);
					HeaderComposite.tDest20EST.setText(des20);
					HeaderComposite.tDest21EST.setText(des21);
				} else if (ID.endsWith("RQS")) {
					ATSForms.t3aRQS.setText("RQS"); ATSForms.t3bRQS.setText(t3b); ATSForms.t3cRQS.setText(t3c);
					ATSForms.t7aRQS.setText(t7a); if (t7b.equals("A")) ATSForms.t7bRQS.setSelection(true); ATSForms.t7cRQS.setText(t7c);
					ATSForms.t13aRQS.setText(t13a); //ATSForms.t13bRQS.setText(t13b);
					ATSForms.t16aRQS.setText(t16a); //ATSForms.t16bRQS.setText(t16b); ATSForms.t16cRQS.setText(t16c); ATSForms.t16dRQS.setText(t16d);
					ATSForms.tDofRQS.setText(s_Dof);
					ATSForms.tFbRQS.setText(tFiledby);
					
					if (ReadFromFile.ReadInputFile1(MainForm.sFolder+"terminate.txt").compareTo("1")==0) { //terminated here
						ATSForms.t13bRQS.setText("");
						ATSForms.t16bRQS.setText(""); ATSForms.t16cRQS.setText(""); ATSForms.t16dRQS.setText("");
					} else {
						ATSForms.t13bRQS.setText(t13b);
						ATSForms.t16bRQS.setText(t16b); ATSForms.t16cRQS.setText(t16c); ATSForms.t16dRQS.setText(t16d);
					}
					
					HeaderComposite.tDest1RQS.setText(des1);
					HeaderComposite.tDest2RQS.setText(des2);
					HeaderComposite.tDest3RQS.setText(des3);
					HeaderComposite.tDest4RQS.setText(des4);
					HeaderComposite.tDest5RQS.setText(des5);
					HeaderComposite.tDest6RQS.setText(des6);
					HeaderComposite.tDest7RQS.setText(des7);
					HeaderComposite.tDest8RQS.setText(des8);
					HeaderComposite.tDest9RQS.setText(des9);
					HeaderComposite.tDest10RQS.setText(des10);
					HeaderComposite.tDest11RQS.setText(des11);
					HeaderComposite.tDest12RQS.setText(des12);
					HeaderComposite.tDest13RQS.setText(des13);
					HeaderComposite.tDest14RQS.setText(des14);
					HeaderComposite.tDest15RQS.setText(des15);
					HeaderComposite.tDest16RQS.setText(des16);
					HeaderComposite.tDest17RQS.setText(des17);
					HeaderComposite.tDest18RQS.setText(des18);
					HeaderComposite.tDest19RQS.setText(des19);
					HeaderComposite.tDest20RQS.setText(des20);
					HeaderComposite.tDest21RQS.setText(des21);
				} else if (ID.endsWith("SPL")) {
					ATSForms.t3aSPL.setText("SPL"); ATSForms.t3bSPL.setText(t3b); ATSForms.t3cSPL.setText(t3c);
					ATSForms.t7aSPL.setText(t7a); if (t7b.equals("A")) ATSForms.t7bSPL.setSelection(true); ATSForms.t7cSPL.setText(t7c);
					ATSForms.t13aSPL.setText(t13a); ATSForms.t13bSPL.setText(t13b);
					ATSForms.t16aSPL.setText(t16a); ATSForms.t16bSPL.setText(t16b); ATSForms.t16cSPL.setText(t16c); ATSForms.t16dSPL.setText(t16d);
					ATSForms.tDofSPL.setText(s_Dof);
					ATSForms.tFbSPL.setText(tFiledby);
					
					//----------- start of type18
					ATSForms.tRegSPL.setText(s_Reg);
					ATSForms.tDofSPL.setText(s_Dof);
					ATSForms.tNavSPL.setText(sNav);
					ATSForms.tComSPL.setText(sCom);
					ATSForms.tDatSPL.setText(sDat);
					ATSForms.tSurSPL.setText(sSur);
					ATSForms.tDepSPL.setText(sDep);
					ATSForms.tDestSPL.setText(sDest);
					ATSForms.tEetSPL.setText(sEet);
					ATSForms.tSelSPL.setText(sSel);
					ATSForms.tTypSPL.setText(sTyp);
					ATSForms.tCodeSPL.setText(sCode);
					ATSForms.tDleSPL.setText(sDle);
					ATSForms.tOprSPL.setText(sOpr);
					ATSForms.tOrgnSPL.setText(sOrgn);
					ATSForms.tPerSPL.setText(sPer);
					ATSForms.tAltnSPL.setText(sAltn);
					ATSForms.tRaltSPL.setText(sRalt);
					ATSForms.tTaltSPL.setText(sTalt);
					ATSForms.tRifSPL.setText(sRif);
					ATSForms.tRmkSPL.setText(sRmk);
					ATSForms.tPbnSPL.setText(sPbn);
					ATSForms.tStsSPL.setText(sSts);
					//----------- end of type18
					ATSForms.t19aSPL.setText(t19a); ATSForms.t19bSPL.setText(t19b);
					
					if (t19dP.equals("S/P")) ATSForms.t19dPSPL.setText("P");
					else ATSForms.t19dPSPL.setText("X");
					if (t19dD.equals("S/D")) ATSForms.t19dDSPL.setText("D");
					else ATSForms.t19dDSPL.setText("X");
					if (t19dM.equals("S/M")) ATSForms.t19dMSPL.setText("M");
					else ATSForms.t19dMSPL.setText("X");
					if (t19dJ.equals("S/J")) ATSForms.t19dJSPL.setText("J");
					else ATSForms.t19dJSPL.setText("X");
					
					if (t19eL.equals("J/L")) ATSForms.t19eLSPL.setText("L");
					else ATSForms.t19eLSPL.setText("X");
					if (t19eF.equals("J/F")) ATSForms.t19eFSPL.setText("F");
					else ATSForms.t19eFSPL.setText("X");
					if (t19eU.equals("J/U")) ATSForms.t19eUSPL.setText("U");
					else ATSForms.t19eUSPL.setText("X");
					if (t19eV.equals("J/V")) ATSForms.t19eVSPL.setText("V");
					else ATSForms.t19eVSPL.setText("X");
					
					if (t19cUHF.equals("R/U")) ATSForms.t19cUhfSPL.setText("U");
					else ATSForms.t19cUhfSPL.setText("X");
					if (t19cVHF.equals("R/V")) ATSForms.t19cVhfSPL.setText("V");
					else ATSForms.t19cVhfSPL.setText("X");
					if (t19cELT.equals("R/E")) ATSForms.t19cEltSPL.setText("E");
					else ATSForms.t19cVhfSPL.setText("X");
					
					ATSForms.t19NumSPL.setText(t19f_number); ATSForms.t19CapSPL.setText(t19f_capacity); 
					ATSForms.t19cSPL.setText("C");
					ATSForms.t19ColSPL.setText(t19f_colour);
					ATSForms.t19AirSPL.setText(t19g); ATSForms.t19RemSPL.setText(t19Remarks); ATSForms.t19PilSPL.setText(t19i);
					
					HeaderComposite.tDest1SPL.setText(des1);
					HeaderComposite.tDest2SPL.setText(des2);
					HeaderComposite.tDest3SPL.setText(des3);
					HeaderComposite.tDest4SPL.setText(des4);
					HeaderComposite.tDest5SPL.setText(des5);
					HeaderComposite.tDest6SPL.setText(des6);
					HeaderComposite.tDest7SPL.setText(des7);
					HeaderComposite.tDest8SPL.setText(des8);
					HeaderComposite.tDest9SPL.setText(des9);
					HeaderComposite.tDest10SPL.setText(des10);
					HeaderComposite.tDest11SPL.setText(des11);
					HeaderComposite.tDest12SPL.setText(des12);
					HeaderComposite.tDest13SPL.setText(des13);
					HeaderComposite.tDest14SPL.setText(des14);
					HeaderComposite.tDest15SPL.setText(des15);
					HeaderComposite.tDest16SPL.setText(des16);
					HeaderComposite.tDest17SPL.setText(des17);
					HeaderComposite.tDest18SPL.setText(des18);
					HeaderComposite.tDest19SPL.setText(des19);
					HeaderComposite.tDest20SPL.setText(des20);
					HeaderComposite.tDest21SPL.setText(des21);
				}
				p++;
			}
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}
	
	
}
