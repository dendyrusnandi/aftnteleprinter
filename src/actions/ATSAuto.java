package actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import setting.Shells;
import databases.jdbc;
import displays.DialogFactory;
import displays.forms.ATSForms;

public class ATSAuto {

	Connection conn = null; // connection object
    Statement stmt = null; // statement object
    ResultSet rs = null; // result set object
	
	String sDbName="";
	String tblName="",id_air_message="",yearMonth="",yearFr="",monthFr="",yearTo="",monthTo="",select="",where="",order="";
	int jumlah=0,rowNo=0; //counter ResultSet
	
	Shell shell;
	
	static String s="";
	static String sdb="";
	static int is=0;

	public void valueChangedReg(Text text,Text regText,Text t9b,Text t18b,Text tReg,Text t10a,Text t10bBaru,Text tOpr,Text tPbn,Text tSel) {
		String toa="",id="",type18="",type9b="",type10a="",type10b="",typeOpr="",typePbn="",typeSel="";
		
		if (!text.isFocusControl()) return;
		if (text == regText) { //REG/ diisi, type18 juga otomatis keisi (incl Current and New REG/)
	    	toa = (text.getText());
	    	//------------------------------------ KONEKSI DB ------------------------------------
			try { conn = jdbc.getDBConnection(); } 
			catch (Exception e) { e.printStackTrace(); }
				
			try {
				String lihatsql = "SELECT * FROM aircraft_reg WHERE type18='"+toa+"'";
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
					type18 = rs.getString("type18"); if (type18==null) { type18=""; }
					type9b = rs.getString("type9b"); if (type9b==null) { type9b=""; }
					type10a = rs.getString("type10a"); if (type10a==null) { type10a=""; }
					type10b = rs.getString("type10b"); if (type10b==null) { type10b=""; }
					typeOpr = rs.getString("type18Opr"); if (typeOpr==null) { typeOpr=""; }
					typePbn = rs.getString("type18Pbn"); if (typePbn==null) { typePbn=""; }
					typeSel = rs.getString("type18Sel"); if (typeSel==null) { typeSel=""; }
				}
				connClose();
			} catch (SQLException s) {
				DialogFactory.openInfoDialog(Shells.sh_nFPL, "Search Message", s.toString());
				s.printStackTrace();
			} catch (java.lang.OutOfMemoryError hs) {
				DialogFactory.openInfoDialog(Shells.sh_nFPL, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
			}
	  		t9b.setText(type9b);
	  		t18b.setText(type18);
	  		tReg.setText(type18);
	  		t10a.setText(type10a);
	  		t10bBaru.setText(type10b);
	  		tOpr.setText(typeOpr);
	  		tPbn.setText(typePbn);
	  		tSel.setText(typeSel);
	  		ATSForms.setDateNow(ATSForms.tDofFPL);
	  	}
	}
	
	public void auto9bTo9c(Text text, Text t9b, Text t9c, String ID) {
		if (ID.endsWith("ALR")) shell = Shells.sh_nALR;
  		if (ID.endsWith("FPL")) shell = Shells.sh_nFPL; 
  		if (ID.endsWith("CPL")) shell = Shells.sh_nCPL;
  		
		String toa="",id="",type9b="",type9c="";
//		if (!text.isFocusControl()) return;
		if (text == t9b) { //type9b diisi, type9cs juga otomatis keisi
	    	toa = (text.getText());
	    	//------------------------------------ KONEKSI DB ------------------------------------
	    	try { conn = jdbc.getDBConnection(); } 
			catch (Exception e) { e.printStackTrace(); }
			
	    	try {
				String lihatsql = "SELECT * FROM aircraft_wtc WHERE type9b='"+toa+"'";
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
				connClose();
			} catch (SQLException s) {
				DialogFactory.openInfoDialog(shell, "Search Message", s.toString());
				s.printStackTrace();
			} catch (java.lang.OutOfMemoryError hs) {
				DialogFactory.openInfoDialog(shell, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
			}
	  		t9c.setText(type9c);
	  		
	  		if (ID.endsWith("ALR")) { ATSForms.t9cALR.setText(type9c); }
	  		if (ID.endsWith("FPL")) { ATSForms.t9cFPL.setText(type9c); }
	  		if (ID.endsWith("CPL")) { ATSForms.t9cCPL.setText(type9c); }
	  	}
	}
	
//	public void valueChangedToA(Text text, Text t9b, Text c9c, String ID, Combo combo) {
//		if (ID.endsWith("ALR")) shell = Shells.sh_nALR;
//  		if (ID.endsWith("FPL")) shell = Shells.sh_nFPL; 
//  		if (ID.endsWith("CPL")) shell = Shells.sh_nCPL;
//  		
//		String toa="",id="",type9b="",type9c="";
////		if (!text.isFocusControl()) return;
//		if (text == t9b) { //type9b diisi, type9cs juga otomatis keisi
//	    	toa = (text.getText());
//	    	//------------------------------------ KONEKSI DB ------------------------------------
//	    	try { conn = jdbc.getDBConnection(); } 
//			catch (Exception e) { e.printStackTrace(); }
//			
//	    	try {
//				String lihatsql = "SELECT * FROM aircraft_wtc WHERE type9b='"+toa+"'";
//				stmt = conn.createStatement();
//				rs = stmt.executeQuery(lihatsql);
//				rs.last();
//				int jumlah = rs.getRow();
//				System.out.println("Jumlah data=" + jumlah);
//				rs.beforeFirst();
//				
//				int rowNo = 0;
//				while (rs.next()) {
//					rowNo++;
//					
//					id = rs.getString("id"); if (id==null) { id=""; }
//					type9b = rs.getString("type9b"); if (type9b==null) { type9b=""; }
//					type9c = rs.getString("type9c"); if (type9c==null) { type9c=""; }
//				}
//				connClose();
//			} catch (SQLException s) {
//				DialogFactory.openInfoDialog(shell, "Search Message", s.toString());
//				s.printStackTrace();
//			} catch (java.lang.OutOfMemoryError hs) {
//				DialogFactory.openInfoDialog(shell, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
//			}
//	  		c9c.setText(type9c);
//	  		
////	  		if (ID.endsWith("ALR")) { ATSForms.c9cALR.setText(type9c); }
////	  		if (ID.endsWith("FPL")) { ATSForms.c9cFPL.setText(type9c); }
////	  		if (ID.endsWith("CPL")) { ATSForms.c9cCPL.setText(type9c); }
//	  	}
//	}
	
	//percobaan
	public void enabledBtn(Text text, Text txt1, Text txt2, String ID, Button btn) {
//		System.out.println(">>masuk sini");
		if (ID.endsWith("ALR")) shell = Shells.sh_nALR;
  		if (ID.endsWith("FPL")) shell = Shells.sh_nFPL; 
  		if (ID.endsWith("CPL")) shell = Shells.sh_nCPL;
  		
		String toa="";
		if (text == txt1) toa = (text.getText());
		if (!toa.isEmpty() && !txt2.getText().isEmpty()) {
			btn.setEnabled(true);
		}
		else {
			btn.setEnabled(false);
		}
		
	}
	
	public void Enable9b(Text txt1, Text txt2, String ID, Button btn) {
		if (ID.compareToIgnoreCase("ALR")==0) shell = Shells.sh_nALR;
  		if (ID.compareToIgnoreCase("FPL")==0) shell = Shells.sh_nFPL; 
  		if (ID.compareToIgnoreCase("CPL")==0) shell = Shells.sh_nCPL;
  		
		if (!txt1.getText().isEmpty() && !txt2.getText().isEmpty()) { btn.setEnabled(true); }
		else { btn.setEnabled(false); }
	}
	
//	public void enabledBtn(String txt1, String txt2, String ID, Button btn) {
//		if (!txt1.isEmpty() && !txt2.isEmpty()) btn.setEnabled(true);
//		else btn.setEnabled(false);
//		
//	}
	
	public void enabledBtn(String txt1, String txt2, String txt3, Button btn) {
		if (!txt1.isEmpty() && !txt2.isEmpty() && !txt3.isEmpty()) btn.setEnabled(true);
		else btn.setEnabled(false);
		
	}
	
	public void enabledBtn(String txt1, String txt2, String txt3, String txt4, String txt5, Button btn) {
		if (!txt1.isEmpty() && !txt2.isEmpty() && !txt3.isEmpty() && !txt4.isEmpty() && !txt5.isEmpty()) btn.setEnabled(true);
		else btn.setEnabled(false);
		
	}
	
//	public void valueChangedToAList(Text text, Text t9b, Text c9c, String ID, List list) {
//		if (ID.endsWith("ALR")) shell = Shells.sh_nALR;
//  		if (ID.endsWith("FPL")) shell = Shells.sh_nFPL; 
//  		if (ID.endsWith("CPL")) shell = Shells.sh_nCPL;
//  		
//		String toa="",id="",type9b="",type9c="";
////		if (!text.isFocusControl()) return;
//		if (text == t9b) { //type9b diisi, type9cs juga otomatis keisi
//	    	toa = (text.getText());
//	    	//------------------------------------ KONEKSI DB ------------------------------------
//	    	try { conn = jdbc.getDBConnection(); } 
//			catch (Exception e) { e.printStackTrace(); }
//			
//	    	try {
//				String lihatsql = "SELECT * FROM aircraft_wtc WHERE type9b='"+toa+"'";
//				stmt = conn.createStatement();
//				rs = stmt.executeQuery(lihatsql);
//				rs.last();
//				int jumlah = rs.getRow();
//				System.out.println("Jumlah data=" + jumlah);
//				rs.beforeFirst();
//				
//				int rowNo = 0;
//				while (rs.next()) {
//					rowNo++;
//					
//					id = rs.getString("id"); if (id==null) { id=""; }
//					type9b = rs.getString("type9b"); if (type9b==null) { type9b=""; }
//					type9c = rs.getString("type9c"); if (type9c==null) { type9c=""; }
//				}
//				connClose();
//			} catch (SQLException s) {
//				DialogFactory.openInfoDialog(shell, "Search Message", s.toString());
//				s.printStackTrace();
//			} catch (java.lang.OutOfMemoryError hs) {
//				DialogFactory.openInfoDialog(shell, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
//			}
//	  		c9c.setText(type9c);
//	  		
//			s="";
//			sdb=jdbc.get9c();
//			is=0;
//			for (is=0;is<Combos.cType9c.length;is++) {
//				if (sdb.compareTo(Combos.cType9c[is])==0) {s=Combos.cType9c[is];break;}
//			}
//			list.setSelection(is);
//			list.showSelection();
//			ATSForms.init9cFPL=0;
//			ATSForms.init9cALR=0;
//			ATSForms.init9cCPL=0;
//			
//	  		if (ID.endsWith("ALR")) {
//				s="";
//				sdb=c9c.getText();
//				is=0;
//				for (is=0;is<Combos.cType9c.length;is++) {
//					if (sdb.compareTo(Combos.cType9c[is])==0) {s=Combos.cType9c[is];break;}
//				}
//	  			ATSForms.t9cALR.setSelection(is);
//	  		}
//	  		if (ID.endsWith("FPL")) {
//				s="";
//				sdb=c9c.getText();
//				is=0;
//				for (is=0;is<Combos.cType9c.length;is++) {
//					if (sdb.compareTo(Combos.cType9c[is])==0) {s=Combos.cType9c[is];break;}
//				}
//	  			ATSForms.t9cFPL.setSelection(is);
//	  		}
//	  		if (ID.endsWith("CPL")) {
//				s="";
//				sdb=c9c.getText();
//				is=0;
//				for (is=0;is<Combos.cType9c.length;is++) {
//					if (sdb.compareTo(Combos.cType9c[is])==0) {s=Combos.cType9c[is];break;}
//				}
//	  			ATSForms.t9cCPL.setSelection(is);
//	  		}
//	  	}
//	}
	
	public void connClose() {
		try {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		} catch(SQLException se) {
			se.printStackTrace();
		}
	}
}
