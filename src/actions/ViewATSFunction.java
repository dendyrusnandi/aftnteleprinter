package actions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Shell;

import setting.Shells;
import setting.Title;
import databases.jdbc;

public class ViewATSFunction {
//	static check69 cekmn = new check69();
	SendSave ss = new SendSave();

	final int posX=0, posY=50;
	
	static String ch69="",s18Luar="",s20Luar="",s21Luar="",s22Luar="",sSpaceLuar="",sFreeLuar="";
	public String alr="",rcf="",fpl="",dla="",chg="",cnl="",dep="",arr="",cdn="",cpl="",est="",lam="",acp="",spl="",rqp="",rqs="",freeText="";
	public String palr="",prcf="",pfpl="",pdla="",pchg="",pcnl="",pdep="",parr="",pcdn="",pcpl="",pest="",plam="",pacp="",pspl="",prqp="",prqs="",pfreeText="";

	static String sendAt="",priority="",originator="",filed_by="",ori_ref="";
	static String type3aATS="",type3bATS="",type3cATS="",type5a="",type5b="",type5c="",type7a="",type7b="",type7c="",type8a="",type8b="";
	static String type9a="",type9b="",type9c="",type10a="",type10b="",type13a="",type13b="";
	static String type14a="",type14b="",type14c="",type14d="",type14e="",type15a="",type15b="",type15c="";
	static String type16a="",type16b="",type16c="",type16c2nd="",type17a="",type17b="",type17c="",type18="",type18a="",type18b="";
	static String type19a="",type19b="",type19cUHF="",type19cVHF="",type19cELT="",type19dS="",type19dP="",type19dD="",type19dM="",type19dJ="";
	static String type19eJ="",type19eL="",type19eF="",type19eU="",type19eV="";
	static String type19fD="",type19f_number="",type19f_capacity="",type19f_cover="",type19f_colour="";
	static String type19i="",type19g="",type19hN="",type19Remarks="",type20="",type21="",type22="",space_reserved="";
	
	public String str3="",str5="",str7="",str8="",str9="",str10="",str13="",str14="",str15="",str16="",str17="",str18="",str19="",
		str20="",str21="",str22="",strSpace="",strFree="",strFb="",
		sType18a="",sType18b="",sType18="",sType20="",sType21="",sType22="",sFree="",io="",header="",atstyp="";

	public ViewATSFunction() {
		
	}
	
	// mendapatkan isi dari database, kemudian disusun sesuai format
	void setHeader(ResultSet rs) {
		try {
			String adrs1 = rs.getString("destination1"); if (adrs1 == null) adrs1="";
			String adrs2 = rs.getString("destination2"); if (adrs2 == null) adrs2="";
			String adrs3 = rs.getString("destination3"); if (adrs3 == null) adrs3="";
			String adrs4 = rs.getString("destination4"); if (adrs4 == null) adrs4="";
			String adrs5 = rs.getString("destination5"); if (adrs5 == null) adrs5="";
			String adrs6 = rs.getString("destination6"); if (adrs6 == null) adrs6="";
			String adrs7 = rs.getString("destination7"); if (adrs7 == null) adrs7="";
			
			String adrs8 = rs.getString("destination8"); if (adrs8 == null) adrs8="";
			String adrs9 = rs.getString("destination9"); if (adrs9 == null) adrs9="";
			String adrs10 = rs.getString("destination10"); if (adrs10 == null) adrs10="";
			String adrs11 = rs.getString("destination11"); if (adrs11 == null) adrs11="";
			String adrs12 = rs.getString("destination12"); if (adrs12 == null) adrs12="";
			String adrs13 = rs.getString("destination13"); if (adrs13 == null) adrs13="";
			String adrs14 = rs.getString("destination14"); if (adrs14 == null) adrs14="";
							
			String adrs15 = rs.getString("destination15"); if (adrs15 == null) adrs15="";
			String adrs16 = rs.getString("destination16"); if (adrs16 == null) adrs16="";
			String adrs17 = rs.getString("destination17"); if (adrs17 == null) adrs17="";
			String adrs18 = rs.getString("destination18"); if (adrs18 == null) adrs18="";
			String adrs19 = rs.getString("destination19"); if (adrs19 == null) adrs19="";
			String adrs20 = rs.getString("destination20"); if (adrs20 == null) adrs20="";
			String adrs21 = rs.getString("destination21"); if (adrs21 == null) adrs21="";
			
			priority = rs.getString("priority"); if (priority == null) priority="";
			sendAt = rs.getString("filing_time"); if (sendAt == null) sendAt="";
			originator = rs.getString("originator"); if (originator == null) originator="";
			ori_ref = rs.getString("ori_ref"); if (ori_ref == null) ori_ref="";
	
			String cid = rs.getString("cid"); if (cid == null) cid="";
			String seq = rs.getString("seq"); if (seq == null) seq="";
			String tms = rs.getString("tms"); if (tms == null) tms="";
			
			//TAMBAHAN untuk incoming FREE TEXT
			if (priority.compareToIgnoreCase("xx")==0) priority="";
			
			formHeader(adrs1,adrs2,adrs3,adrs4,adrs5,adrs6,adrs7,
					adrs8,adrs9,adrs10,adrs11,adrs12,adrs13,adrs14,
					adrs15,adrs16,adrs17,adrs18,adrs19,adrs20,adrs21,
					priority,sendAt,originator,ori_ref,cid,seq,tms);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void formHeader(String adrs1,String adrs2,String adrs3,String adrs4,String adrs5,String adrs6,String adrs7,
			String adrs8,String adrs9,String adrs10,String adrs11,String adrs12,String adrs13,String adrs14,
			String adrs15,String adrs16,String adrs17,String adrs18,String adrs19,String adrs20,String adrs21,
			String priority,String sendAt,String originator,String ori_ref,String cid,String seq,String tms) {
		//-------------------------------------
		if (adrs1.equals("")) adrs1 = "";
		//----
		if ((!adrs1.equals("")) && (!adrs2.equals(""))) { adrs2 = " "+adrs2; }
		else if ((!adrs1.equals("")) && (!adrs3.equals(""))) { adrs2 = ""; adrs3=adrs2+" "+adrs3; } 
		else if ((!adrs1.equals("")) && (!adrs4.equals(""))) { adrs2 = ""; adrs4=adrs2+" "+adrs4; } 
		else if ((!adrs1.equals("")) && (!adrs5.equals(""))) { adrs2 = ""; adrs5=adrs2+" "+adrs5; }
		else if ((!adrs1.equals("")) && (!adrs6.equals(""))) { adrs2 = ""; adrs6=adrs2+" "+adrs6; }
		else if ((!adrs1.equals("")) && (!adrs7.equals(""))) { adrs2 = ""; adrs7=adrs2+" "+adrs7; }
		else if ((!adrs1.equals("")) && (!adrs8.equals(""))) { adrs2 = ""; adrs8=adrs2+" "+adrs8; }
		else if ((!adrs1.equals("")) && (!adrs9.equals(""))) { adrs2 = ""; adrs9=adrs2+" "+adrs9; }
		else if ((!adrs1.equals("")) && (!adrs10.equals(""))) { adrs2 = ""; adrs10=adrs2+" "+adrs10; }
		else if ((!adrs1.equals("")) && (!adrs11.equals(""))) { adrs2 = ""; adrs11=adrs2+" "+adrs11; }
		else if ((!adrs1.equals("")) && (!adrs12.equals(""))) { adrs2 = ""; adrs12=adrs2+" "+adrs12; }
		else if ((!adrs1.equals("")) && (!adrs13.equals(""))) { adrs2 = ""; adrs13=adrs2+" "+adrs13; }
		else if ((!adrs1.equals("")) && (!adrs14.equals(""))) { adrs2 = ""; adrs14=adrs2+" "+adrs14; }
		else if ((!adrs1.equals("")) && (!adrs15.equals(""))) { adrs2 = ""; adrs15=adrs2+" "+adrs15; }
		else if ((!adrs1.equals("")) && (!adrs16.equals(""))) { adrs2 = ""; adrs16=adrs2+" "+adrs16; }
		else if ((!adrs1.equals("")) && (!adrs17.equals(""))) { adrs2 = ""; adrs17=adrs2+" "+adrs17; }
		else if ((!adrs1.equals("")) && (!adrs18.equals(""))) { adrs2 = ""; adrs18=adrs2+" "+adrs18; }
		else if ((!adrs1.equals("")) && (!adrs19.equals(""))) { adrs2 = ""; adrs19=adrs2+" "+adrs19; }
		else if ((!adrs1.equals("")) && (!adrs20.equals(""))) { adrs2 = ""; adrs20=adrs2+" "+adrs20; }
		else if ((!adrs1.equals("")) && (!adrs21.equals(""))) { adrs2 = ""; adrs21=adrs2+" "+adrs21; }
		else { adrs2 = ""+adrs2; }
		//----
		if ((!adrs2.equals("")) && (!adrs3.equals(""))) { adrs3 = " "+adrs3; }
		else if ((!adrs2.equals("")) && (!adrs4.equals(""))) { adrs3 = ""; adrs4=adrs3+" "+adrs4; }
		else if ((!adrs2.equals("")) && (!adrs5.equals(""))) { adrs3 = ""; adrs5=adrs3+" "+adrs5; }
		else if ((!adrs2.equals("")) && (!adrs6.equals(""))) { adrs3 = ""; adrs6=adrs3+" "+adrs6; }
		else if ((!adrs2.equals("")) && (!adrs7.equals(""))) { adrs3 = ""; adrs7=adrs3+" "+adrs7; }
		else if ((!adrs2.equals("")) && (!adrs8.equals(""))) { adrs3 = ""; adrs8=adrs3+" "+adrs8; }
		else if ((!adrs2.equals("")) && (!adrs9.equals(""))) { adrs3 = ""; adrs9=adrs3+" "+adrs9; }
		else if ((!adrs2.equals("")) && (!adrs10.equals(""))) { adrs3 = ""; adrs10=adrs3+" "+adrs10; }
		else if ((!adrs2.equals("")) && (!adrs11.equals(""))) { adrs3 = ""; adrs11=adrs3+" "+adrs11; }
		else if ((!adrs2.equals("")) && (!adrs12.equals(""))) { adrs3 = ""; adrs12=adrs3+" "+adrs12; }
		else if ((!adrs2.equals("")) && (!adrs13.equals(""))) { adrs3 = ""; adrs13=adrs3+" "+adrs13; }
		else if ((!adrs2.equals("")) && (!adrs14.equals(""))) { adrs3 = ""; adrs14=adrs3+" "+adrs14; }
		else if ((!adrs2.equals("")) && (!adrs15.equals(""))) { adrs3 = ""; adrs15=adrs3+" "+adrs15; }
		else if ((!adrs2.equals("")) && (!adrs16.equals(""))) { adrs3 = ""; adrs16=adrs3+" "+adrs16; }
		else if ((!adrs2.equals("")) && (!adrs17.equals(""))) { adrs3 = ""; adrs17=adrs3+" "+adrs17; }
		else if ((!adrs2.equals("")) && (!adrs18.equals(""))) { adrs3 = ""; adrs18=adrs3+" "+adrs18; }
		else if ((!adrs2.equals("")) && (!adrs19.equals(""))) { adrs3 = ""; adrs19=adrs3+" "+adrs19; }
		else if ((!adrs2.equals("")) && (!adrs20.equals(""))) { adrs3 = ""; adrs20=adrs3+" "+adrs20; }
		else if ((!adrs2.equals("")) && (!adrs21.equals(""))) { adrs3 = ""; adrs21=adrs3+" "+adrs21; }
		else { adrs3 = ""+adrs3; }
		//----
		if ((!adrs3.equals("")) && (!adrs4.equals(""))) { adrs4 = " "+adrs4; }
		else if ((!adrs3.equals("")) && (!adrs5.equals(""))) { adrs4 = ""; adrs5=adrs4+" "+adrs5; }
		else if ((!adrs3.equals("")) && (!adrs6.equals(""))) { adrs4 = ""; adrs6=adrs4+" "+adrs6; }
		else if ((!adrs3.equals("")) && (!adrs7.equals(""))) { adrs4 = ""; adrs7=adrs4+" "+adrs7; }
		else if ((!adrs3.equals("")) && (!adrs8.equals(""))) { adrs4 = ""; adrs8=adrs4+" "+adrs8; }
		else if ((!adrs3.equals("")) && (!adrs9.equals(""))) { adrs4 = ""; adrs9=adrs4+" "+adrs9; }
		else if ((!adrs3.equals("")) && (!adrs10.equals(""))) { adrs4 = ""; adrs10=adrs4+" "+adrs10; }
		else if ((!adrs3.equals("")) && (!adrs11.equals(""))) { adrs4 = ""; adrs11=adrs4+" "+adrs11; }
		else if ((!adrs3.equals("")) && (!adrs12.equals(""))) { adrs4 = ""; adrs12=adrs4+" "+adrs12; }
		else if ((!adrs3.equals("")) && (!adrs13.equals(""))) { adrs4 = ""; adrs13=adrs4+" "+adrs13; }
		else if ((!adrs3.equals("")) && (!adrs14.equals(""))) { adrs4 = ""; adrs14=adrs4+" "+adrs14; }
		else if ((!adrs3.equals("")) && (!adrs15.equals(""))) { adrs4 = ""; adrs15=adrs4+" "+adrs15; }
		else if ((!adrs3.equals("")) && (!adrs16.equals(""))) { adrs4 = ""; adrs16=adrs4+" "+adrs16; }
		else if ((!adrs3.equals("")) && (!adrs17.equals(""))) { adrs4 = ""; adrs17=adrs4+" "+adrs17; }
		else if ((!adrs3.equals("")) && (!adrs18.equals(""))) { adrs4 = ""; adrs18=adrs4+" "+adrs18; }
		else if ((!adrs3.equals("")) && (!adrs19.equals(""))) { adrs4 = ""; adrs19=adrs4+" "+adrs19; }
		else if ((!adrs3.equals("")) && (!adrs20.equals(""))) { adrs4 = ""; adrs20=adrs4+" "+adrs20; }
		else if ((!adrs3.equals("")) && (!adrs21.equals(""))) { adrs4 = ""; adrs21=adrs4+" "+adrs21; }
		else { adrs4 = ""+adrs4; }
		//----
		if ((!adrs4.equals("")) && (!adrs5.equals(""))) { adrs5 = " "+adrs5; }
		else if ((!adrs4.equals("")) && (!adrs6.equals(""))) { adrs5 = ""; adrs6=adrs5+" "+adrs6; }
		else if ((!adrs4.equals("")) && (!adrs7.equals(""))) { adrs5 = ""; adrs7=adrs5+" "+adrs7; }
		else if ((!adrs4.equals("")) && (!adrs8.equals(""))) { adrs5 = ""; adrs8=adrs5+" "+adrs8; }
		else if ((!adrs4.equals("")) && (!adrs9.equals(""))) { adrs5 = ""; adrs9=adrs5+" "+adrs9; }
		else if ((!adrs4.equals("")) && (!adrs10.equals(""))) { adrs5 = ""; adrs10=adrs5+" "+adrs10; }
		else if ((!adrs4.equals("")) && (!adrs11.equals(""))) { adrs5 = ""; adrs11=adrs5+" "+adrs11; }
		else if ((!adrs4.equals("")) && (!adrs12.equals(""))) { adrs5 = ""; adrs12=adrs5+" "+adrs12; }
		else if ((!adrs4.equals("")) && (!adrs13.equals(""))) { adrs5 = ""; adrs13=adrs5+" "+adrs13; }
		else if ((!adrs4.equals("")) && (!adrs14.equals(""))) { adrs5 = ""; adrs14=adrs5+" "+adrs14; }
		else if ((!adrs4.equals("")) && (!adrs15.equals(""))) { adrs5 = ""; adrs15=adrs5+" "+adrs15; }
		else if ((!adrs4.equals("")) && (!adrs16.equals(""))) { adrs5 = ""; adrs16=adrs5+" "+adrs16; }
		else if ((!adrs4.equals("")) && (!adrs17.equals(""))) { adrs5 = ""; adrs17=adrs5+" "+adrs17; }
		else if ((!adrs4.equals("")) && (!adrs18.equals(""))) { adrs5 = ""; adrs18=adrs5+" "+adrs18; }
		else if ((!adrs4.equals("")) && (!adrs19.equals(""))) { adrs5 = ""; adrs19=adrs5+" "+adrs19; }
		else if ((!adrs4.equals("")) && (!adrs20.equals(""))) { adrs5 = ""; adrs20=adrs5+" "+adrs20; }
		else if ((!adrs4.equals("")) && (!adrs21.equals(""))) { adrs5 = ""; adrs21=adrs5+" "+adrs21; }
		else { adrs5 = ""+adrs5; }
		//----
		if ((!adrs5.equals("")) && (!adrs6.equals(""))) { adrs6 = " "+adrs6; }
		else if ((!adrs5.equals("")) && (!adrs7.equals(""))) { adrs6 = ""; adrs7=adrs6+" "+adrs7; }
		else if ((!adrs5.equals("")) && (!adrs8.equals(""))) { adrs6 = ""; adrs8=adrs6+" "+adrs8; }
		else if ((!adrs5.equals("")) && (!adrs9.equals(""))) { adrs6 = ""; adrs9=adrs6+" "+adrs9; }
		else if ((!adrs5.equals("")) && (!adrs10.equals(""))) { adrs6 = ""; adrs10=adrs6+" "+adrs10; }
		else if ((!adrs5.equals("")) && (!adrs11.equals(""))) { adrs6 = ""; adrs11=adrs6+" "+adrs11; }
		else if ((!adrs5.equals("")) && (!adrs12.equals(""))) { adrs6 = ""; adrs12=adrs6+" "+adrs12; }
		else if ((!adrs5.equals("")) && (!adrs13.equals(""))) { adrs6 = ""; adrs13=adrs6+" "+adrs13; }
		else if ((!adrs5.equals("")) && (!adrs14.equals(""))) { adrs6 = ""; adrs14=adrs6+" "+adrs14; }
		else if ((!adrs5.equals("")) && (!adrs15.equals(""))) { adrs6 = ""; adrs15=adrs6+" "+adrs15; }
		else if ((!adrs5.equals("")) && (!adrs16.equals(""))) { adrs6 = ""; adrs16=adrs6+" "+adrs16; }
		else if ((!adrs5.equals("")) && (!adrs17.equals(""))) { adrs6 = ""; adrs17=adrs6+" "+adrs17; }
		else if ((!adrs5.equals("")) && (!adrs18.equals(""))) { adrs6 = ""; adrs18=adrs6+" "+adrs18; }
		else if ((!adrs5.equals("")) && (!adrs19.equals(""))) { adrs6 = ""; adrs19=adrs6+" "+adrs19; }
		else if ((!adrs5.equals("")) && (!adrs20.equals(""))) { adrs6 = ""; adrs20=adrs6+" "+adrs20; }
		else if ((!adrs5.equals("")) && (!adrs21.equals(""))) { adrs6 = ""; adrs21=adrs6+" "+adrs21; }
		else { adrs6 = ""+adrs6; }
		//----
		if ((!adrs6.equals("")) && (!adrs7.equals(""))) { adrs7 = " "+adrs7; }
		else if ((!adrs6.equals("")) && (!adrs8.equals(""))) { adrs7 = ""; adrs8=adrs7+" "+adrs8; }
		else if ((!adrs6.equals("")) && (!adrs9.equals(""))) { adrs7 = ""; adrs9=adrs7+" "+adrs9; }
		else if ((!adrs6.equals("")) && (!adrs10.equals(""))) { adrs7 = ""; adrs10=adrs7+" "+adrs10; }
		else if ((!adrs6.equals("")) && (!adrs11.equals(""))) { adrs7 = ""; adrs11=adrs7+" "+adrs11; }
		else if ((!adrs6.equals("")) && (!adrs12.equals(""))) { adrs7 = ""; adrs12=adrs7+" "+adrs12; }
		else if ((!adrs6.equals("")) && (!adrs13.equals(""))) { adrs7 = ""; adrs13=adrs7+" "+adrs13; }
		else if ((!adrs6.equals("")) && (!adrs14.equals(""))) { adrs7 = ""; adrs14=adrs7+" "+adrs14; }
		else if ((!adrs6.equals("")) && (!adrs15.equals(""))) { adrs7 = ""; adrs15=adrs7+" "+adrs15; }
		else if ((!adrs6.equals("")) && (!adrs16.equals(""))) { adrs7 = ""; adrs16=adrs7+" "+adrs16; }
		else if ((!adrs6.equals("")) && (!adrs17.equals(""))) { adrs7 = ""; adrs17=adrs7+" "+adrs17; }
		else if ((!adrs6.equals("")) && (!adrs18.equals(""))) { adrs7 = ""; adrs18=adrs7+" "+adrs18; }
		else if ((!adrs6.equals("")) && (!adrs19.equals(""))) { adrs7 = ""; adrs19=adrs7+" "+adrs19; }
		else if ((!adrs6.equals("")) && (!adrs20.equals(""))) { adrs7 = ""; adrs20=adrs7+" "+adrs20; }
		else if ((!adrs6.equals("")) && (!adrs21.equals(""))) { adrs7 = ""; adrs21=adrs7+" "+adrs21; }
		else { adrs7 = ""+adrs7; }
		//----
		if ((!adrs7.equals("")) && (!adrs8.equals(""))) { adrs8 = " "+adrs8; }
		else if ((!adrs7.equals("")) && (!adrs9.equals(""))) { adrs8 = ""; adrs9=adrs8+" "+adrs9; }
		else if ((!adrs7.equals("")) && (!adrs10.equals(""))) { adrs8 = ""; adrs10=adrs8+" "+adrs10; }
		else if ((!adrs7.equals("")) && (!adrs11.equals(""))) { adrs8 = ""; adrs11=adrs8+" "+adrs11; }
		else if ((!adrs7.equals("")) && (!adrs12.equals(""))) { adrs8 = ""; adrs12=adrs8+" "+adrs12; }
		else if ((!adrs7.equals("")) && (!adrs13.equals(""))) { adrs8 = ""; adrs13=adrs8+" "+adrs13; }
		else if ((!adrs7.equals("")) && (!adrs14.equals(""))) { adrs8 = ""; adrs14=adrs8+" "+adrs14; }
		else if ((!adrs7.equals("")) && (!adrs15.equals(""))) { adrs8 = ""; adrs15=adrs8+" "+adrs15; }
		else if ((!adrs7.equals("")) && (!adrs16.equals(""))) { adrs8 = ""; adrs16=adrs8+" "+adrs16; }
		else if ((!adrs7.equals("")) && (!adrs17.equals(""))) { adrs8 = ""; adrs17=adrs8+" "+adrs17; }
		else if ((!adrs7.equals("")) && (!adrs18.equals(""))) { adrs8 = ""; adrs18=adrs8+" "+adrs18; }
		else if ((!adrs7.equals("")) && (!adrs19.equals(""))) { adrs8 = ""; adrs19=adrs8+" "+adrs19; }
		else if ((!adrs7.equals("")) && (!adrs20.equals(""))) { adrs8 = ""; adrs20=adrs8+" "+adrs20; }
		else if ((!adrs7.equals("")) && (!adrs21.equals(""))) { adrs8 = ""; adrs21=adrs8+" "+adrs21; }
		else { adrs8 = ""+adrs8; }
		//----
		if ((!adrs8.equals("")) && (!adrs9.equals(""))) { adrs9 = " "+adrs9; }
		else if ((!adrs8.equals("")) && (!adrs10.equals(""))) { adrs9 = ""; adrs10=adrs9+" "+adrs10; }
		else if ((!adrs8.equals("")) && (!adrs11.equals(""))) { adrs9 = ""; adrs11=adrs9+" "+adrs11; }
		else if ((!adrs8.equals("")) && (!adrs12.equals(""))) { adrs9 = ""; adrs12=adrs9+" "+adrs12; }
		else if ((!adrs8.equals("")) && (!adrs13.equals(""))) { adrs9 = ""; adrs13=adrs9+" "+adrs13; }
		else if ((!adrs8.equals("")) && (!adrs14.equals(""))) { adrs9 = ""; adrs14=adrs9+" "+adrs14; }
		else if ((!adrs8.equals("")) && (!adrs15.equals(""))) { adrs9 = ""; adrs15=adrs9+" "+adrs15; }
		else if ((!adrs8.equals("")) && (!adrs16.equals(""))) { adrs9 = ""; adrs16=adrs9+" "+adrs16; }
		else if ((!adrs8.equals("")) && (!adrs17.equals(""))) { adrs9 = ""; adrs17=adrs9+" "+adrs17; }
		else if ((!adrs8.equals("")) && (!adrs18.equals(""))) { adrs9 = ""; adrs18=adrs9+" "+adrs18; }
		else if ((!adrs8.equals("")) && (!adrs19.equals(""))) { adrs9 = ""; adrs19=adrs9+" "+adrs19; }
		else if ((!adrs8.equals("")) && (!adrs20.equals(""))) { adrs9 = ""; adrs20=adrs9+" "+adrs20; }
		else if ((!adrs8.equals("")) && (!adrs21.equals(""))) { adrs9 = ""; adrs21=adrs9+" "+adrs21; }
		else { adrs9 = ""+adrs9; }
		//----
		if ((!adrs9.equals("")) && (!adrs10.equals(""))) { adrs10 = " "+adrs10; }
		else if ((!adrs9.equals("")) && (!adrs11.equals(""))) { adrs10 = ""; adrs11=adrs10+" "+adrs11; }
		else if ((!adrs9.equals("")) && (!adrs12.equals(""))) { adrs10 = ""; adrs12=adrs10+" "+adrs12; }
		else if ((!adrs9.equals("")) && (!adrs13.equals(""))) { adrs10 = ""; adrs13=adrs10+" "+adrs13; }
		else if ((!adrs9.equals("")) && (!adrs14.equals(""))) { adrs10 = ""; adrs14=adrs10+" "+adrs14; }
		else if ((!adrs9.equals("")) && (!adrs15.equals(""))) { adrs10 = ""; adrs15=adrs10+" "+adrs15; }
		else if ((!adrs9.equals("")) && (!adrs16.equals(""))) { adrs10 = ""; adrs16=adrs10+" "+adrs16; }
		else if ((!adrs9.equals("")) && (!adrs17.equals(""))) { adrs10 = ""; adrs17=adrs10+" "+adrs17; }
		else if ((!adrs9.equals("")) && (!adrs18.equals(""))) { adrs10 = ""; adrs18=adrs10+" "+adrs18; }
		else if ((!adrs9.equals("")) && (!adrs19.equals(""))) { adrs10 = ""; adrs19=adrs10+" "+adrs19; }
		else if ((!adrs9.equals("")) && (!adrs20.equals(""))) { adrs10 = ""; adrs20=adrs10+" "+adrs20; }
		else if ((!adrs9.equals("")) && (!adrs21.equals(""))) { adrs10 = ""; adrs21=adrs10+" "+adrs21; }
		else { adrs10 = ""+adrs10; }
		//----
		if ((!adrs10.equals("")) && (!adrs11.equals(""))) { adrs11 = " "+adrs11; }
		else if ((!adrs10.equals("")) && (!adrs12.equals(""))) { adrs11 = ""; adrs12=adrs11+" "+adrs12; }
		else if ((!adrs10.equals("")) && (!adrs13.equals(""))) { adrs11 = ""; adrs13=adrs11+" "+adrs13; }
		else if ((!adrs10.equals("")) && (!adrs14.equals(""))) { adrs11 = ""; adrs14=adrs11+" "+adrs14; }
		else if ((!adrs10.equals("")) && (!adrs15.equals(""))) { adrs11 = ""; adrs15=adrs11+" "+adrs15; }
		else if ((!adrs10.equals("")) && (!adrs16.equals(""))) { adrs11 = ""; adrs16=adrs11+" "+adrs16; }
		else if ((!adrs10.equals("")) && (!adrs17.equals(""))) { adrs11 = ""; adrs17=adrs11+" "+adrs17; }
		else if ((!adrs10.equals("")) && (!adrs18.equals(""))) { adrs11 = ""; adrs18=adrs11+" "+adrs18; }
		else if ((!adrs10.equals("")) && (!adrs19.equals(""))) { adrs11 = ""; adrs19=adrs11+" "+adrs19; }
		else if ((!adrs10.equals("")) && (!adrs20.equals(""))) { adrs11 = ""; adrs20=adrs11+" "+adrs20; }
		else if ((!adrs10.equals("")) && (!adrs21.equals(""))) { adrs11 = ""; adrs21=adrs11+" "+adrs21; }
		else { adrs11 = ""+adrs11; }
		//----
		if ((!adrs11.equals("")) && (!adrs12.equals(""))) { adrs12 = " "+adrs12; }
		else if ((!adrs11.equals("")) && (!adrs13.equals(""))) { adrs12 = ""; adrs13=adrs12+" "+adrs13; }
		else if ((!adrs11.equals("")) && (!adrs14.equals(""))) { adrs12 = ""; adrs14=adrs12+" "+adrs14; }
		else if ((!adrs11.equals("")) && (!adrs15.equals(""))) { adrs12 = ""; adrs15=adrs12+" "+adrs15; }
		else if ((!adrs11.equals("")) && (!adrs16.equals(""))) { adrs12 = ""; adrs16=adrs12+" "+adrs16; }
		else if ((!adrs11.equals("")) && (!adrs17.equals(""))) { adrs12 = ""; adrs17=adrs12+" "+adrs17; }
		else if ((!adrs11.equals("")) && (!adrs18.equals(""))) { adrs12 = ""; adrs18=adrs12+" "+adrs18; }
		else if ((!adrs11.equals("")) && (!adrs19.equals(""))) { adrs12 = ""; adrs19=adrs12+" "+adrs19; }
		else if ((!adrs11.equals("")) && (!adrs20.equals(""))) { adrs12 = ""; adrs20=adrs12+" "+adrs20; }
		else if ((!adrs11.equals("")) && (!adrs21.equals(""))) { adrs12 = ""; adrs21=adrs12+" "+adrs21; }
		else { adrs12 = ""+adrs12; }
		//----
		if ((!adrs12.equals("")) && (!adrs13.equals(""))) { adrs13 = " "+adrs13; }
		else if ((!adrs12.equals("")) && (!adrs14.equals(""))) { adrs13 = ""; adrs14=adrs13+" "+adrs14; }
		else if ((!adrs12.equals("")) && (!adrs15.equals(""))) { adrs13 = ""; adrs15=adrs13+" "+adrs15; }
		else if ((!adrs12.equals("")) && (!adrs16.equals(""))) { adrs13 = ""; adrs16=adrs13+" "+adrs16; }
		else if ((!adrs12.equals("")) && (!adrs17.equals(""))) { adrs13 = ""; adrs17=adrs13+" "+adrs17; }
		else if ((!adrs12.equals("")) && (!adrs18.equals(""))) { adrs13 = ""; adrs18=adrs13+" "+adrs18; }
		else if ((!adrs12.equals("")) && (!adrs19.equals(""))) { adrs13 = ""; adrs19=adrs13+" "+adrs19; }
		else if ((!adrs12.equals("")) && (!adrs20.equals(""))) { adrs13 = ""; adrs20=adrs13+" "+adrs20; }
		else if ((!adrs12.equals("")) && (!adrs21.equals(""))) { adrs13 = ""; adrs21=adrs13+" "+adrs21; }
		else { adrs13 = ""+adrs13; }
		//----
		if ((!adrs13.equals("")) && (!adrs14.equals(""))) { adrs14 = " "+adrs14; }
		else if ((!adrs13.equals("")) && (!adrs15.equals(""))) { adrs14 = ""; adrs15=adrs14+" "+adrs15; }
		else if ((!adrs13.equals("")) && (!adrs16.equals(""))) { adrs14 = ""; adrs16=adrs14+" "+adrs16; }
		else if ((!adrs13.equals("")) && (!adrs17.equals(""))) { adrs14 = ""; adrs17=adrs14+" "+adrs17; }
		else if ((!adrs13.equals("")) && (!adrs18.equals(""))) { adrs14 = ""; adrs18=adrs14+" "+adrs18; }
		else if ((!adrs13.equals("")) && (!adrs19.equals(""))) { adrs14 = ""; adrs19=adrs14+" "+adrs19; }
		else if ((!adrs13.equals("")) && (!adrs20.equals(""))) { adrs14 = ""; adrs20=adrs14+" "+adrs20; }
		else if ((!adrs13.equals("")) && (!adrs21.equals(""))) { adrs14 = ""; adrs21=adrs14+" "+adrs21; }
		else { adrs14 = ""+adrs14; }
		//----
		if ((!adrs14.equals("")) && (!adrs15.equals(""))) { adrs15 = " "+adrs15; }
		else if ((!adrs14.equals("")) && (!adrs16.equals(""))) { adrs15 = ""; adrs16=adrs15+" "+adrs16; }
		else if ((!adrs14.equals("")) && (!adrs17.equals(""))) { adrs15 = ""; adrs17=adrs15+" "+adrs17; }
		else if ((!adrs14.equals("")) && (!adrs18.equals(""))) { adrs15 = ""; adrs18=adrs15+" "+adrs18; }
		else if ((!adrs14.equals("")) && (!adrs19.equals(""))) { adrs15 = ""; adrs19=adrs15+" "+adrs19; }
		else if ((!adrs14.equals("")) && (!adrs20.equals(""))) { adrs15 = ""; adrs20=adrs15+" "+adrs20; }
		else if ((!adrs14.equals("")) && (!adrs21.equals(""))) { adrs15 = ""; adrs21=adrs15+" "+adrs21; }
		else { adrs15 = ""+adrs15; }
		//----
		if ((!adrs15.equals("")) && (!adrs16.equals(""))) { adrs16 = " "+adrs16; }
		else if ((!adrs15.equals("")) && (!adrs17.equals(""))) { adrs16 = ""; adrs17=adrs16+" "+adrs17; }
		else if ((!adrs15.equals("")) && (!adrs18.equals(""))) { adrs16 = ""; adrs18=adrs16+" "+adrs18; }
		else if ((!adrs15.equals("")) && (!adrs19.equals(""))) { adrs16 = ""; adrs19=adrs16+" "+adrs19; }
		else if ((!adrs15.equals("")) && (!adrs20.equals(""))) { adrs16 = ""; adrs20=adrs16+" "+adrs20; }
		else if ((!adrs15.equals("")) && (!adrs21.equals(""))) { adrs16 = ""; adrs21=adrs16+" "+adrs21; }
		else { adrs16 = ""+adrs16; }
		//----
		if ((!adrs16.equals("")) && (!adrs17.equals(""))) { adrs17 = " "+adrs17; }
		else if ((!adrs16.equals("")) && (!adrs18.equals(""))) { adrs17 = ""; adrs18=adrs17+" "+adrs18; }
		else if ((!adrs16.equals("")) && (!adrs19.equals(""))) { adrs17 = ""; adrs19=adrs17+" "+adrs19; }
		else if ((!adrs16.equals("")) && (!adrs20.equals(""))) { adrs17 = ""; adrs20=adrs17+" "+adrs20; }
		else if ((!adrs16.equals("")) && (!adrs21.equals(""))) { adrs17 = ""; adrs21=adrs17+" "+adrs21; }
		else { adrs17 = ""+adrs17; }
		//----
		if ((!adrs17.equals("")) && (!adrs18.equals(""))) { adrs18 = " "+adrs18; }
		else if ((!adrs17.equals("")) && (!adrs19.equals(""))) { adrs18 = ""; adrs19=adrs18+" "+adrs19; }
		else if ((!adrs17.equals("")) && (!adrs20.equals(""))) { adrs18 = ""; adrs20=adrs18+" "+adrs20; }
		else if ((!adrs17.equals("")) && (!adrs21.equals(""))) { adrs18 = ""; adrs21=adrs18+" "+adrs21; }
		else { adrs18 = ""+adrs18; }
		//----
		if ((!adrs18.equals("")) && (!adrs19.equals(""))) { adrs19 = " "+adrs19; }
		else if ((!adrs18.equals("")) && (!adrs20.equals(""))) { adrs19 = ""; adrs20=adrs19+" "+adrs20; }
		else if ((!adrs18.equals("")) && (!adrs21.equals(""))) { adrs19 = ""; adrs21=adrs19+" "+adrs21; }
		else { adrs19 = ""+adrs19; }
		//----
		if ((!adrs19.equals("")) && (!adrs20.equals(""))) { adrs20 = " "+adrs20; }
		else if ((!adrs19.equals("")) && (!adrs21.equals(""))) { adrs20 = ""; adrs21=adrs20+" "+adrs21; }
		else { adrs20 = ""+adrs20; }
		//----
		if ((!adrs20.equals("")) && (!adrs21.equals(""))) { adrs21 = " "+adrs21; }
		else { adrs21 = ""+adrs21; }

		String len1 = adrs1+adrs2+adrs3+adrs4+adrs5+adrs6+adrs7;
		String len2 = adrs8+adrs9+adrs10+adrs11+adrs12+adrs13+adrs14;
		String len3 =  adrs15+adrs16+adrs17+adrs18+adrs19+adrs20+adrs21;
		String adrs="";
		if (len1.equals("")) { 
			if (len2.equals("")) { 
				if (len3.equals("")) { adrs = ""; } else { adrs = len3; }
			} else {
				if (len3.equals("")) { adrs = len2; } else { adrs = len2+"\n  "+len3; }
			}
		} else { 
			if (len2.equals("")) {
				if (len3.equals("")) { adrs = len1; } else { adrs = len1+"\n  "+len3; }
			} else {
				if (len3.equals("")) { adrs = len1+"\n  "+len2; } 
				else { adrs = len1+"\n  "+len2+"\n  "+len3; }
			}
		}
		
		// disusun sesuai format
		String baris1="";
		 if (!priority.equals("")) {
			 if (!adrs.equals("")) { baris1 = priority+" "+adrs+"\n"; } else { baris1 = priority+"\n"; }
		 } else {
			 if (!adrs.equals("")) { baris1 = adrs+"\n"; } else { baris1 = ""; }
		 }	
	
		 String baris2="";
		 if (!sendAt.equals("")) {
			 if (!originator.equals("")) {
				 if (!ori_ref.equals("")) { baris2 = sendAt+" "+originator+" "+ori_ref+"\n"; } 
				 else {  baris2 = sendAt+" "+originator+"\n"; }
			 } else { 
				 if (!ori_ref.equals("")) { baris2 = sendAt+" "+ori_ref+"\n"; } 
				 else { baris2 = sendAt+"\n"; }
			 }
		 } else { 
			 if (!originator.equals("")) {
				 if (!ori_ref.equals("")) { baris2 = originator+" "+ori_ref+"\n"; } else { baris2 = originator+"\n"; }
			 } else {
				 if (!ori_ref.equals("")) { baris2 = ori_ref+"\n"; } else { baris2 = ""; }
			 }
		 } 
		 
		 String h1="",h2="";
		 if (!cid.isEmpty() && !seq.isEmpty()) h1=cid+seq;
		 else if (!cid.isEmpty() && seq.isEmpty()) h1=cid;
		 else if (cid.isEmpty() && !seq.isEmpty()) h1=seq;
		 //--
		 if (!h1.isEmpty() && !tms.isEmpty()) h2=h1+" "+tms;
		 else if (!h1.isEmpty() && tms.isEmpty()) h2=h1;
		 else if (h1.isEmpty() && !tms.isEmpty()) h2=tms;
		 //--
		 if (!h2.isEmpty()) h2+="\n"; else h2="";
		 //------------------------------------------------------------------------------------
		 if (!baris1.equals("")) {
			 if (!baris2.equals("")) { header = h2+baris1+baris2; } else { header = h2+baris1; }
		 } else {
			 if (!baris2.equals("")) { header = h2+baris2; } else { header = h2+""; }
		 }
	}
	
	//void set.... dipakai untuk list ATS Message versi AFTN void set-> public void set... 
	public void set3(ResultSet rs) {
		try {
			String type3a = rs.getString("type3a"); if (type3a == null) type3a="";
			String type3b = rs.getString("type3b");  if (type3b == null) type3b="";
			String type3c = rs.getString("type3c"); if (type3c == null) type3c="";
			form3(type3a, type3b, type3c);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void set5(ResultSet rs) {
		try {
			String type5a = rs.getString("type5a"); if (type5a == null) type5a="";
			String type5b = rs.getString("type5b"); if (type5b == null) type5b="";
			String type5c = rs.getString("type5c"); if (type5c == null) type5c="";
			if (type5c.contains("`")) type5c = type5c.replace("`", "'");
			form5(type5a, type5b, type5c);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void set7(ResultSet rs) {
		try {
			String type7a = rs.getString("type7a"); if (type7a == null) type7a="";
			String type7b = rs.getString("type7b"); if (type7b == null) type7b="";
			String type7c = rs.getString("type7c"); if (type7c == null) type7c="";
			form7(type7a, type7b, type7c);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void set8(ResultSet rs) {
		try {
			String type8a = rs.getString("type8a"); if (type8a == null) type8a="";
			String type8b = rs.getString("type8b"); if (type8b == null) type8b="";
			form8(type8a, type8b);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void set9(ResultSet rs) {
		try {
			String type9a = rs.getString("type9a"); if (type9a == null) type9a="";
			String type9b = rs.getString("type9b"); if (type9b == null) type9b="";
			String type9c = rs.getString("type9c"); if (type9c == null) type9c="";
			form9(type9a, type9b, type9c);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void set10(ResultSet rs) {
		try {
			String type10a = rs.getString("type10a"); if (type10a == null) type10a="";
			String type10b = rs.getString("type10b"); if (type10b == null) type10b="";
			form10(type10a, type10b);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void set13(ResultSet rs) {
		try {
			String type13a = rs.getString("type13a"); if (type13a == null) type13a="";
			String type13b = rs.getString("type13b"); if (type13b == null) type13b="";
			form13(type13a, type13b);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void set14(ResultSet rs) {
		try {
			String type14a = rs.getString("type14a"); if (type14a == null) type14a="";
			String type14b = rs.getString("type14b"); if (type14b == null) type14b="";
			String type14c = rs.getString("type14c"); if (type14c == null) type14c="";
			String type14d = rs.getString("type14d"); if (type14d == null) type14d="";
			String type14e = rs.getString("type14e"); if (type14e == null) type14e="";
			form14(type14a, type14b, type14c, type14d, type14e);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void set15(ResultSet rs) {
		try {
			String type15a = rs.getString("type15a"); if (type15a == null) type15a="";
			String type15b = rs.getString("type15b"); if (type15b == null) type15b="";
			String type15c = rs.getString("type15c"); if (type15c == null) type15c="";
			if (type15c.contains("`")) type15c = type15c.replace("`", "'");
			form15(type15a, type15b, type15c);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void set16(ResultSet rs) {
		try {
			String type16a = rs.getString("type16a"); if (type16a == null) type16a="";
			String type16b = rs.getString("type16b"); if (type16b == null) type16b="";
			String type16c = rs.getString("type16c");  if (type16c == null) type16c="";
			String type16c2nd = rs.getString("type16c_2nd"); if (type16c2nd== null) type16c2nd="";
			form16(type16a, type16b, type16c, type16c2nd);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void set17(ResultSet rs) {
		try {
			String type17a = rs.getString("type17a"); if (type17a == null) type17a="";
			String type17b = rs.getString("type17b"); if (type17b == null) type17b="";
			String type17c = rs.getString("type17c");  if (type17c == null) type17c="";
			if (type17c.contains("`")) type17c = type17c.replace("`", "'");
			form17(type17a, type17b, type17c);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void set18(ResultSet rs) {
		try {
			String type18 = sType18 = rs.getString("type18"); if (type18 == null) type18 =""; if (sType18 == null) sType18 ="";
			type18 = type18.replaceAll("/", "~");
			if (type18.contains("`")) type18 = type18.replace("`", "'");
			String type18a = sType18a = rs.getString("type18a"); if (type18a == null) type18a=""; if (sType18a == null) sType18a="";
			String type18b = sType18b = rs.getString("type18b"); if (type18b == null) type18b=""; if (sType18b == null) sType18b="";
			form18(type18a, type18b, type18);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void set19(ResultSet rs) {
		try {
			String type19a = rs.getString("type19a"); if (type19a == null) type19a="";
			String type19b = rs.getString("type19b"); if (type19b == null) type19b="";
			String type19cUHF = rs.getString("type19cUHF"); if (type19cUHF == null) type19cUHF="";
			String type19cVHF = rs.getString("type19cVHF"); if (type19cVHF == null) type19cVHF="";
			String type19cELT = rs.getString("type19cELT"); if (type19cELT == null) type19cELT="";
			String type19dS = rs.getString("type19dS"); if (type19dS == null) type19dS="";
			String type19dP = rs.getString("type19dP"); if (type19dP == null) type19dP="";
			String type19dD = rs.getString("type19dD"); if (type19dD == null) type19dD="";
			String type19dM = rs.getString("type19dM"); if (type19dM == null) type19dM="";
			String type19dJ = rs.getString("type19dJ"); if (type19dJ == null) type19dJ="";
			String type19eJ = rs.getString("type19eJ"); if (type19eJ == null) type19eJ="";
			String type19eL = rs.getString("type19eL"); if (type19eL == null) type19eL="";
			String type19eF = rs.getString("type19eF"); if (type19eF == null) type19eF="";
			String type19eU = rs.getString("type19eU"); if (type19eU == null) type19eU="";
			String type19eV = rs.getString("type19eV"); if (type19eV == null) type19eV="";
			String type19fD = rs.getString("type19fD"); if (type19fD == null) type19fD="";
			String type19f_number = rs.getString("type19f_number"); if (type19f_number == null) type19f_number="";
			String type19f_capacity = rs.getString("type19f_capacity"); if (type19f_capacity == null) type19f_capacity="";
			String type19f_cover = rs.getString("type19f_cover"); if (type19f_cover == null) type19f_cover="";
			String type19f_colour = rs.getString("type19f_colour"); if (type19f_colour == null) type19f_colour="";
			String type19g = rs.getString("type19g"); if (type19g == null) type19g="";
			String type19hN = rs.getString("type19hN"); if (type19hN == null) type19hN="";
			String type19Remarks = rs.getString("type19Remarks"); if (type19Remarks == null) type19Remarks="";
			String type19i = rs.getString("type19i") ; if (type19i == null) type19i="";
			
			form19(type19a, type19b, type19cUHF, type19cVHF, type19cELT,
					type19dS, type19dP, type19dD, type19dM, type19dJ, 
					type19eJ, type19eL, type19eF, type19eU, type19eV,  
					type19f_number, type19f_capacity, type19f_cover, 
					type19f_colour, type19g, type19Remarks, type19i);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void set20(ResultSet rs) {
		try {
			String type20 = sType20 = rs.getString("type20"); if (type20 == null) type20="";
			if (type20.contains("`")) type20 = type20.replace("`", "'");
			form20(type20);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void set21(ResultSet rs) {
		try {
			String type21 = sType21 = rs.getString("type21"); if (type21 == null) type21="";
			if (type21.contains("`")) type21 = type21.replace("`", "'");
			form21(type21);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void set22(ResultSet rs) {
		try {
			String type22 = sType22 = rs.getString("type22"); if (type22 == null) type22="";
			if (type22.contains("`")) type22 = type22.replace("`", "'");
			form22(type22);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void setSpace(ResultSet rs) {
		try {//25 maret error kalo di buka setelah coba insert traf
			String space = space_reserved = rs.getString("space_reserved"); if (space_reserved == null) space_reserved=""; 
			//if (space.contains("`")) space = space.replace("`", "'"); else space="";
//			formSpace(space);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	void setFree(ResultSet rs) {
		try {
			String free = sFree = rs.getString("freetext"); if (free == null) free="";
			if (free.contains("`")) free = free.replace("`", "'");
			formFreeText(free);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	void setFb(ResultSet rs) {
		try {
			filed_by = rs.getString("filedby"); if (filed_by == null || filed_by.compareTo("") == 0) filed_by="SYSTEM";
			strFb = "FILLED BY : "+filed_by+"\n----------------------------------------\n";
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void setIO(ResultSet rs) throws SQLException {
		try {
			io = rs.getString("io"); if (io == null) io="";
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}

	// beritanya dirangkai sesuai format
	public void form3(String type3a, String type3b, String type3c) {
		str3=type3a+type3b+type3c;
	}
	
	public void form5(String type5a, String type5b, String type5c) {
		if (!type5a.isEmpty()) type5a += "/"; else type5a="";
		if (!type5b.isEmpty()) type5b += "/"; else type5b="";
		if (!type5c.isEmpty()) type5c += ""; else type5c="";
		str5 = type5a+type5b+type5c;
		if (!str5.isEmpty()) str5 = "-"+str5; else str5="";
	}
	
	public void form7(String type7a, String type7b, String type7c) {
		if (!type7a.isEmpty()) type7a+=""; else type7a="";
		if (!type7b.isEmpty()) type7b="/"+type7b; else type7b="";
		if (!type7c.isEmpty()) type7c+=""; else type7c="";
		str7 = type7a+type7b+type7c;
		if (!str7.isEmpty()) str7 = "-"+str7; else str7="";
	}
	
	public void form8(String type8a, String type8b) {
		if (!type8a.isEmpty()) type8a+=""; else type8a="";
		if (!type8b.isEmpty()) type8b+=""; else type8b="";
		str8 = type8a+type8b;
		if (!str8.isEmpty()) str8 = "-"+str8; else str8="";
	}
	
	public void form9(String type9a, String type9b, String type9c) {
		if (!type9a.isEmpty()) type9a+=""; else type9a="";
		if (!type9b.isEmpty()) type9b+=""; else type9b="";
		if (!type9c.isEmpty()) type9c="/"+type9c; else type9c="";
		str9 = type9a+type9b+type9c;
		if (!str9.isEmpty()) str9 = "-"+str9; else str9="";
	}
	
	public void form10(String type10a, String type10b) {
		if (!type10a.isEmpty()) type10a+=""; else type10a="";
		if (!type10b.isEmpty()) type10b="/"+type10b; else type10b="";
		str10 = type10a+type10b;
		if (!str10.isEmpty()) str10 = "-"+str10; else str10="";
	}
	
	public void form13(String type13a, String type13b) {
		if (!type13a.isEmpty()) type13a+=""; else type13a="";
		if (!type13b.isEmpty()) type13b+=""; else type13b="";
		str13 = type13a+type13b;
		if (!str13.isEmpty()) str13 = "-"+str13; else str13="";
	}
	
	public void form14(String type14a, String type14b, String type14c, String type14d, String type14e) {
		if (!type14a.isEmpty()) type14a+="/"; else type14a="";
		if (!type14b.isEmpty()) type14b+=""; else type14b="";
		if (!type14c.isEmpty()) type14c=""+type14c; else type14c="";
		if (!type14d.isEmpty()) type14d=""+type14d; else type14d="";
		if (!type14e.isEmpty()) type14e=""+type14e; else type14e="";
		str14 = type14a+type14b+type14c+type14d+type14e;
		if (!str14.isEmpty()) str14 = "-"+str14; else str14="";
	}
	
	public void form15(String type15a, String type15b, String type15c) {
		if (!type15a.isEmpty()) type15a+=""; else type15a="";
		if (!type15b.isEmpty()) type15b+=""; else type15b="";
		if (!type15c.isEmpty()) type15c=" "+type15c; else type15c="";
		str15 = type15a+type15b+type15c;
		if (!str15.isEmpty()) str15 = "-"+str15; else str15="";
	}
	
	public void form16(String type16a, String type16b, String type16c, String type16d) {
		if (!type16a.isEmpty()) type16a+=""; else type16a="";
		if (!type16b.isEmpty()) type16b+=""; else type16b="";
		if (!type16c.isEmpty()) type16c=" "+type16c; else type16c="";
		if (!type16d.isEmpty()) type16d=" "+type16d; else type16d="";
		str16 = type16a+type16b+type16c+type16d;
		if (!str16.isEmpty()) str16 = "-"+str16; else str16="";
	}
	
	public void form17(String type17a, String type17b, String type17c) {
		if (!type17a.isEmpty()) type17a+=""; else type17a="";
		if (!type17b.isEmpty()) type17b+=""; else type17b="";
		if (!type17c.isEmpty()) type17c=" "+type17c; else type17c="";
		str17 = type17a+type17b+type17c;
		if (!str17.isEmpty()) str17 = "-"+str17; else str17="";
	}

	
	public void form19(String type19a, String type19b, String type19cUHF, String type19cVHF, String type19cELT,
			String type19dS, String type19dP, String type19dD, String type19dM, String type19dJ, 
			String type19eJ, String type19eL, String type19eF, String type19eU, String type19eV,  
			String type19f_number, String type19f_capacity, String type19f_cover, 
			String type19f_colour, String type19Aircraft, String type19Remarks, String type19i) {
		
		// unused : type19fD, type19f_cover, type19hN
		// 19AB
		if (!type19a.equals("")) type19a="E/"+type19a+" "; else type19a="";
		if (!type19b.equals("")) type19b="P/"+type19b+" "; else type19b="";
		// 19C [R/UVE] 	
		String cUHF="",cVHF="",cELT="",type19c="";
		if (type19cUHF != "") cUHF=type19cUHF.replaceAll("R/U","U"); else cUHF="";
		if (type19cVHF != "") cVHF=type19cVHF.replaceAll("R/V","V"); else cVHF="";
		if (type19cELT != "") cELT=type19cELT.replaceAll("R/E","E"); else cELT="";
		type19c = cUHF+cVHF+cELT;
		if (!type19c.isEmpty()) type19c = "R/"+type19c+" ";
		// 19D [S/PDMJ]
		String dP="",dD="",dM="",dJ="",type19d="";
		if (type19dP != "") dP=type19dP.replaceAll("S/P","P"); else dP="";
		if (type19dD != "") dD=type19dD.replaceAll("S/D","D"); else dD="";
		if (type19dM != "") dM=type19dM.replaceAll("S/M","M"); else dM="";
		if (type19dJ != "") dJ=type19dJ.replaceAll("S/J","J"); else dJ="";
		type19d = dP+dD+dM+dJ;
		if (!type19d.isEmpty()) type19d = "S/"+type19d+" ";
		// 19E [J/LFUV] 
		String eL="",eF="",eU="",eV="",type19e="";
		if (type19eL != "") eL=type19eL.replaceAll("J/L","L"); else eL="";
		if (type19eF != "") eF=type19eF.replaceAll("J/F","F"); else eF="";
		if (type19eU != "") eU=type19eU.replaceAll("J/U","U"); else eU="";
		if (type19eV != "") eV=type19eV.replaceAll("J/V","V"); else eV="";
		type19e = eL+eF+eU+eV;
		if (!type19e.isEmpty()) type19e = "J/"+type19e+" ";
		// 19F [number,capacity,cover,color]
		String type19f="";
		if (!type19f_number.equals("")) type19f_number="D/"+type19f_number+" "; else type19f_number="";
		if (!type19f_capacity.equals("")) type19f_capacity+=" "; else type19f_capacity="";
		if (!type19f_colour.equals("")) type19f_colour+=" "; else type19f_colour="";
		if (type19f_number.equals("")) {
			type19f_capacity="";
			type19f_colour="";
		}
		type19f = type19f_number+type19f_capacity;
		if (!type19f_cover.isEmpty()) type19f+=type19f_cover+" ";
		type19f+=type19f_colour;
		if (!type19f.isEmpty() && type19f.endsWith(" ")) {
			int index = type19f.lastIndexOf(" ");
			type19f = type19f.substring(0, index); 
		}
		// 19GH
		String type19g="";
		if (!type19Aircraft.equals("")) type19Aircraft="A/"+type19Aircraft+" "; else type19Aircraft="";
		if (!type19Remarks.equals("")) type19Remarks="N/"+type19Remarks+" "; else type19Remarks="";
		type19g = type19Aircraft+type19Remarks;
		if (!type19g.isEmpty() && type19g.endsWith(" ")) {
			int index = type19g.lastIndexOf(" ");
			type19g = type19g.substring(0, index); 
		}
		
		String line1 = type19a+type19b+type19c+type19d+type19e+type19f;
		if (!line1.isEmpty()) {
			if (line1.endsWith(" ")) {
				int index = line1.lastIndexOf(" ");
				line1 = line1.substring(0, index);
			}
			line1+="\n";
		}
		String line2 = type19g;
		if (!line2.isEmpty()) {
			if (line2.endsWith(" ")) {
				int index = line2.lastIndexOf(" ");
				line2 = line2.substring(0, index);
			}
			line2=" "+line2+"\n";
		}
		// 19I
		if (!type19i.equals("")) type19i=" C/"+type19i; else type19i="";
		
		str19 = line1+line2+type19i;
		if (!str19.isEmpty() && str19.endsWith("\n")) {
			int index = str19.lastIndexOf("\n");
			str19 = str19.substring(0, index);
		}
		if (!str19.isEmpty()) str19 = "-" + str19; else str19="";
//		System.out.println("str19=\n" + str19 + "#");
	}
	
	public void form20(String type20) {
		if (!type20.isEmpty()) str20 = "-"+type20; else str20="";
	}
	
	public void form21(String type21) {
		if (!type21.isEmpty()) str21 = "-"+type21; else str21="";
	}
	
	public void form22(String type22) {
		if (!type22.isEmpty()) str22 = "-"+type22; else str22="";
	}
	
	public void formSpace(String free) {
		if (!free.isEmpty()) strSpace="\n"+free; else strSpace="";
	}
	
	public void formFreeText(String free) {
		if (!free.isEmpty()) strFree=free; else strFree="";
	}
	
//	public void form20(String type20) {
//		System.out.println("io20=" + io + "#");
//		if (io.compareTo("1")==0) {
//			String str="";
//			for (String part : getParts(type20, 69)) {
//				str+="\n "+part;
//	        }	
//			if (str.startsWith("\n ")) str = str.replaceFirst("\n ", "");
//			type20 = str;	
//		} else {
//			if (type20.isEmpty()) type20="";
//		}
//		if (!type20.isEmpty()) str20 = "-"+type20; else str20="";
//		System.out.println("str20=\n" + str20 + "#");
//	}
//	
//	public void form21(String type21) {
//		System.out.println("io21=" + io + "#");
//		if (io.compareTo("1")==0) {
//			String str="";
//			for (String part : getParts(type21, 69)) {
//				str+="\n "+part;
//	        }	
//			if (str.startsWith("\n ")) str = str.replaceFirst("\n ", "");
//			type21 = str;	
//		} else {
//			if (type21.isEmpty()) type21="";
//		}
//		if (!type21.isEmpty()) str21 = "-"+type21; else str21="";
//		System.out.println("str21=\n" + str21 + "#");
//	}
//	
//	public void form22(String type22) {
//		System.out.println("io22=" + io + "#");
//		if (io.compareTo("1")==0) {
//			String str="";
//			for (String part : getParts(type22, 69)) {
//				str+="\n "+part;
//	        }	
//			if (str.startsWith("\n ")) str = str.replaceFirst("\n ", "");
//			type22 = str;	
//		} else {
//			if (type22.isEmpty()) type22="";
//		}
//		if (!type22.isEmpty()) str22 = "-"+type22; else str22="";
//		System.out.println("str22=\n" + str22 + "#");
//	}
//	
//	public void formSpace(String free) {
//		System.out.println("ioSpace=" + io + "#");
//		if (io.compareTo("1")==0) {
//			String str="";
//			for (String part : getParts(free, 69)) {
//				str+="\n"+part;
//	        }	
//			if (str.startsWith("\n")) str = str.replaceFirst("\n", "");
//			free = str;	
//		} else if (io.compareTo("0")==0 || io.isEmpty()) {
//			if (free.isEmpty()) free="";
//		}
//		if (!free.isEmpty()) strSpace="\n"+free; else strSpace="";
//		System.out.println("strSpace=\n" + strSpace + "#");
//	}
//	
//	public void formFreeText(String free) {
//		System.out.println("ioFree=" + io + "#");
//		if (io.compareTo("1")==0) {
//			System.out.println(">>> if");
//			String str="";
//			for (String part : getParts(free, 69)) {
//				str+="\n"+part;
////				System.out.println("str:" + str + "@");
//	        }	
//			if (str.startsWith("\n")) str = str.replaceFirst("\n", "");
//			free = str;	
//		} else if (io.compareTo("0")==0 || io.isEmpty()) {
//			System.out.println(">>> else");
//			if (free.isEmpty()) free="";
//		}
//		if (!free.isEmpty()) strFree=free; else strFree="";
//		System.out.println("strFree=\n" + strFree + "#");
//	}
	
	public void form18(String type18a, String type18b, String type18) {
//		System.out.println("masuk form18");
//		System.out.println("@type18a:" + type18a + "@");
//		System.out.println("@type18b:" + type18b + "@");
//		System.out.println("@type18c:" + type18 + "@");

		if (type18a.compareTo("")!=0 && !type18a.startsWith("REG/")) type18a="REG/"+type18a; 
		if (type18b.compareTo("")!=0 && !type18b.startsWith("DOF/")) type18b="DOF/"+type18b;
		
		String other="",sSts="",sPbn="",sNav="", sCom="",sDat="",sSur="", sDep="",sDest="",sDof="", sReg="",sEet="",
		sSel="",sTyp="",sCode="",sDle="",sOpr="",sOrgn="",sPer="", sAltn="",sRalt="",sTalt="", sRif="",sRmk="",x="";
		str18="";
		//dari db untuk fungsi view
		if (type18.contains("STS~")) type18 = type18.replace("STS~", "STS/");
		if (type18.contains("PBN~")) type18 = type18.replace("PBN~", "PBN/");
		if (type18.contains("NAV~")) type18 = type18.replace("NAV~", "NAV/");
		if (type18.contains("COM~")) type18 = type18.replace("COM~", "COM/");
		if (type18.contains("DAT~")) type18 = type18.replace("DAT~", "DAT/");
		if (type18.contains("SUR~")) type18 = type18.replace("SUR~", "SUR/");
		if (type18.contains("DEP~")) type18 = type18.replace("DEP~", "DEP/");
		if (type18.contains("DEST~")) type18 = type18.replace("DEST~", "DEST/");
		if (type18.contains("DOF~")) type18 = type18.replace("DOF~", "DOF/");
		if (type18.contains("REG~")) type18 = type18.replace("REG~", "REG/");
		if (type18.contains("EET~")) type18 = type18.replace("EET~", "EET/");
		if (type18.contains("SEL~")) type18 = type18.replace("SEL~", "SEL/");
		if (type18.contains("TYP~")) type18 = type18.replace("TYP~", "TYP/");
		if (type18.contains("CODE~")) type18 = type18.replace("CODE~", "CODE/");
		if (type18.contains("DLE~")) type18 = type18.replace("DLE~", "DLE/");
		if (type18.contains("OPR~")) type18 = type18.replace("OPR~", "OPR/");
		if (type18.contains("ORGN~")) type18 = type18.replace("ORGN~", "ORGN/");
		if (type18.contains("PER~")) type18 = type18.replace("PER~", "PER/");
		if (type18.contains("ALTN~")) type18 = type18.replace("ALTN~", "ALTN/");
		if (type18.contains("RALT~")) type18 = type18.replace("RALT~", "RALT/");
		if (type18.contains("TALT~")) type18 = type18.replace("TALT~", "TALT/");
		if (type18.contains("RIF~")) type18 = type18.replace("RIF~", "RIF/");
		if (type18.contains("RMK~")) type18 = type18.replace("RMK~", "RMK/");
		//versi lama
		if (type18a.equals("REG/0")) sType18a = type18a="";
		if (type18b.equals("DOF/0")) sType18b = type18b="";
		//versi baru
		if (type18.contains("REG/0")) type18 = type18.replace("REG/0", "");
		if (type18.contains("DOF/0")) type18 = type18.replace("DOF/0", "");
		//versi lama-baru
		if (type18.compareTo("0")==0) type18="";
		if (type18.startsWith(" ")) type18 = type18.replaceFirst("\\s+", "");

		sSts="";sPbn="";sNav=""; sCom="";sDat="";sSur=""; sDep="";sDest="";sDof=""; sReg="";sEet="";sSel=""; sTyp="";sCode="";sDle="";
		sOpr="";sOrgn="";sPer=""; sAltn="";sRalt="";sTalt=""; sRif="";sRmk="";
		if (!type18.equals("")) {
			if (type18.contains("\n")) type18 = type18.replaceAll("\n", "^");

			String sub[] = type18.split(" ");
			for (int n=0; n<sub.length; n++) {
				if (sub[n].contains("/")) { x=sub[n]; } 
				else { x+=" "+sub[n]; }
				
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
				else if (x.startsWith("ALTN/")) sAltn = x.substring(5, x.length());
				else if (x.startsWith("RALT/")) sRalt = x.substring(5, x.length());
				else if (x.startsWith("TALT/")) sTalt = x.substring(5, x.length());
				else if (x.startsWith("RIF/")) sRif = x.substring(4, x.length());
				else if (x.startsWith("RMK/")) sRmk = x.substring(4, x.length());
				else if (x.startsWith("PER/")) sPer = x.substring(4, x.length());
				else { other+= " "+x; }
			} //end of for n
			
			if (other.startsWith(" ")) other=other.replaceFirst(" ", "");
			if (sRmk.compareTo("")==0) { sRmk=other; } else { sRmk+=" "+other; }
		} else {
			sSts="";sPbn="";sNav=""; sCom="";sDat="";sSur=""; sDep="";sDest="";sDof=""; sReg="";sEet="";sSel=""; sTyp="";sCode="";sDle="";
			sOpr="";sOrgn="";sPer=""; sAltn="";sRalt="";sTalt=""; sRif="";sRmk="";
		}
		
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
		//---------------------------------------------------------
		if (sSts.compareTo("")!=0) sSts=" STS/"+sSts; else sSts="";
		if (sPbn.compareTo("")!=0) sPbn=" PBN/"+sPbn; else sPbn="";
		if (sNav.compareTo("")!=0) sNav=" NAV/"+sNav; else sNav="";
		if (sCom.compareTo("")!=0) sCom=" COM/"+sCom; else sCom="";
		if (sDat.compareTo("")!=0) sDat=" DAT/"+sDat; else sDat="";
		if (sSur.compareTo("")!=0) sSur=" SUR/"+sSur; else sSur="";
		if (sDep.compareTo("")!=0) sDep=" DEP/"+sDep; else sDep="";
		if (sDest.compareTo("")!=0) sDest=" DEST/"+sDest; else sDest="";
		if (sReg.compareTo("")!=0) sReg=" REG/"+sReg; else sReg="";
		if (sDof.compareTo("")!=0) sDof=" DOF/"+sDof; else sDof="";
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
		
		//tipe18
		str18 = sSts+sPbn+sNav+ sCom+sDat+sSur+ sDep+sDest+sDof+ sReg+sEet+sSel+ sTyp+sCode+sDle+ sOpr+sOrgn+sPer+ sAltn+sRalt+sTalt+ sRif+sRmk;
		if (str18.startsWith(" ")) str18 = str18.replaceFirst(" ", "");
		//tipe18a,b
		if (type18a.compareTo("")!=0) type18a=type18a+" "; else type18a=""; 
		if (type18b.compareTo("")!=0) type18b=type18b+" "; else type18b=""; 
		//kalo type18a,type18b,type18 kosong, maka tampilkan -0
		String sRegDof = type18a+type18b;
		if (sRegDof.isEmpty()) sRegDof="";
		if (!sRegDof.isEmpty() && str18.isEmpty()) {
				int iRegDof = sRegDof.lastIndexOf(" ");
				sRegDof=sRegDof.substring(0, iRegDof);
		} 
		if (!str18.isEmpty()) {
			if (str18.endsWith(" ")) {
				int ind18c = str18.lastIndexOf(" ");
				str18 = str18.substring(0, ind18c);
			}
		}
		str18 = sRegDof + str18;
//		System.out.println("io18=" + io + "#");
		if (io.compareTo("0")==0) {
			editType18(sType18a,sType18b,sType18);
			if (sType18a.compareTo("")!=0) sType18a=sType18a+" "; else sType18a=""; 
			if (sType18b.compareTo("")!=0) sType18b=sType18b+" "; else sType18b=""; 
			if (s18Luar.compareTo("")==0) s18Luar="";
			str18=sType18a+sType18b+s18Luar;
		} else {
			String str="";
			for (String part : getParts(str18, 69)) {
				str+="\n "+part;
	        }	
			if (str.startsWith("\n ")) str = str.replaceFirst("\n ", "");
			str18 = str;	
		}
		if (str18.contains("^")) str18 = str18.replace("^", "\n");
		if (!str18.equals("")) str18="-"+str18; else str18="-0";
	}
	
	public static List<String> getParts(String string, int partitionSize) {
		String test="";
		String parrrts="";
		int icobain=0;
		
        List<String> parts = new ArrayList<String>();
        int len = string.length();
//        System.out.println("len:" + len + "#");
        for (int i=0; i<len; i+=partitionSize) {
        	//parts.add(string.substring(i, Math.min(len, i + partitionSize))); //ASLI
        	if (string.contains("\n")) string = string.replaceAll("\n", "~");
        	
        	parrrts = test = string.substring(i, Math.min(len, i + partitionSize));
//        	System.out.println("TEST:" + test + "#");
        	if (test.contains(" ") && test.length()==69) {
//        		System.out.println(">>> if");
        		int index = test.lastIndexOf(" ");
        		test = test.substring(0, index);
        		
        		int indexp = parrrts.lastIndexOf(" ");
        		String cobain = parrrts.substring(indexp+1, parrrts.length());
        		icobain += cobain.length();
        		string = cobain+string;
        	} else {
        		test = string.substring(i, Math.min(len, i + partitionSize)+icobain);
        	}

        	if (test.contains("~")) test = test.replaceAll("~", "\n");
        	parts.add(test);
        }
        return parts;
    }
	
	public static List<String> getPartsASLI(String string, int partitionSize) {
		String test="";
		String parrrts="";
		int icobain=0;
		
        List<String> parts = new ArrayList<String>();
        int len = string.length();
        System.out.println("len:" + len + "#");
        for (int i=0; i<len; i+=partitionSize) {
        	//parts.add(string.substring(i, Math.min(len, i + partitionSize))); //ASLI
        	parrrts = test = string.substring(i, Math.min(len, i + partitionSize));
        	
        	if (test.contains(" ") && test.length()==69) {
//        		System.out.println(">>> if");
        		int index = test.lastIndexOf(" ");
        		test = test.substring(0, index);
        		
        		int indexp = parrrts.lastIndexOf(" ");
        		String cobain = parrrts.substring(indexp+1, parrrts.length());
        		icobain += cobain.length();
        		string = cobain+string;
        	} else {
        		test = string.substring(i, Math.min(len, i + partitionSize)+icobain);
        	}
        	
        	parts.add(test);
        }
        return parts;
    }
	
	
	/** isi berita masing-masing tipe, dipanggil pada saat :
	 * 1. getBody___ untuk di tabel: check_status
	 * 2. view masing-masing tipe 
	 */
	public void msgALR() {
		String str35=str3+str5; 
		String str78=str7+str8;
		String str910=str9+str10;
		String str379="";
		if (!str35.equals("")) {
			if (!str78.equals("")) {
				if (!str910.equals("")) { str379=str35+"\n"+str78+"\n"+str910; } else { str379=str35+"\n"+str78; }
			} else {
				if (!str910.equals("")) { str379=str35+"\n"+str910; } else { str379=str35; }
			} 
		} else {
			if (!str78.equals("")) {
				if (!str910.equals("")) { str379=str78+"\n"+str910; } else { str379=str78; }
			} else {
				if (!str910.equals("")) { str379=str910; } else { str379=""; }
			}
		}
		
		String str131516="";
		if (!str13.equals("")) {
			if (!str15.equals("")) {
				if (!str16.equals("")) { str131516=str13+"\n"+str15+"\n"+str16; } else { str131516=str13+"\n"+str15; }
			} else {
				if (!str16.equals("")) { str131516=str13+"\n"+str16; } else { str131516=str13; }
			} 
		} else {
			if (!str15.equals("")) {
				if (!str16.equals("")) { str131516=str15+"\n"+str16; } else { str131516=str15; }
			} else {
				if (!str16.equals("")) { str131516=str16; } else { str131516=""; }
			}
		}

		String str181920="";
		if (!str18.equals("")) {
			if (!str19.equals("")) {
				if (!str20.equals("")) { str181920=str18+"\n"+str19+"\n"+str20; } else { str181920=str18+"\n"+str19; }
			} else {
				if (!str20.equals("")) { str181920=str18+"\n"+str20; } else { str181920=str18; }
			} 
		} else {
			if (!str19.equals("")) {
				if (!str20.equals("")) { str181920=str19+"\n"+str20; } else { str181920=str19; }
			} else {
				if (!str20.equals("")) { str181920=str20; } else { str181920=""; }
			}
		}

		alr="";
		if (!str379.equals("")) {
			if (!str131516.equals("")) {
				if (!str181920.equals("")) { alr=str379+"\n"+str131516+"\n"+str181920; } else { alr=str379+"\n"+str131516; }
			} else {
				if (!str181920.equals("")) { alr=str379+"\n"+str181920; } else { alr=str379; }
			} 
		} else {
			if (!str131516.equals("")) {
				if (!str181920.equals("")) { alr=str131516+"\n"+str181920; } else { alr=str131516; }
			} else {
				if (!str181920.equals("")) { alr=str181920; } else { alr=""; }
			}
		}
		alr = "(" + alr + ")";
	}
	
	public void msgRCF() {
		rcf="";
		if (!str3.equals("")) {
			if (!str7.equals("")) {
				if (!str21.equals("")) { rcf=str3+str7+"\n"+str21; } else { rcf=str3+str7; }
			} else {
				if (!str21.equals("")) { rcf=str3+"\n"+str21; } else { rcf=str3; }
			}
		} else {
			if (!str7.equals("")) {
				if (!str21.equals("")) { rcf=str7+"\n"+str21; } else { rcf=str7; }
			} else {
				if (!str21.equals("")) { rcf=str21; } else { rcf=""; }
			}
		}
		rcf = "(" + rcf + ")";
	}
	
	public void msgFPL() {
		String str378=str3+str7+str8;
		String str910=str9+str10;
		String str3913="";
		if (!str378.equals("")) {
			if (!str910.equals("")) {
				if (!str13.equals("")) { str3913=str378+"\n"+str910+"\n"+str13; } else { str3913=str378+"\n"+str910; }
			} else {
				if (!str13.equals("")) { str3913=str378+"\n"+str13; } else { str3913=str378; }
			}				 
		} else {
			if (!str910.equals("")) {
				if (!str13.equals("")) { str3913=str910+"\n"+str13; } else { str3913=str910; }
			} else {
				if (!str13.equals("")) { str3913=str13; } else { str3913=""; }
			}
		}
		 
		String str151618="";
		if (!str15.equals("")) {
			if (!str16.equals("")) {
				if (!str18.equals("")) { str151618=str15+"\n"+str16+"\n"+str18; } else { str151618=str15+"\n"+str16; }
			} else {
				if (!str18.equals("")) { str151618=str15+"\n"+str18; } else { str151618=str15; }
			}				 
		} else {
			if (!str16.equals("")) {
				if (!str18.equals("")) { str151618=str16+"\n"+str18; } else { str151618=str16; }
			} else {
				if (!str18.equals("")) { str151618=str18; } else { str151618=""; }
			}
		}
		 
		fpl="";
		if (!str3913.equals("")) {
			if (!str151618.equals("")) { 
				if (!str19.equals("")) { fpl=str3913+"\n"+str151618+")"+"\n"+str19; } else { fpl=str3913+"\n"+str151618; }
			} else { 
				if (!str19.equals("")) { fpl=str3913+"\n"+str19; } else { fpl=str3913; } 
			}				 
		} else {
			if (!str151618.equals("")) { 
				if (!str19.equals("")) { fpl=str151618+")"+"\n"+str19; } else { fpl=str151618; }
			} else { 
				if (!str19.equals("")) { fpl=str19; } else { fpl=""; } 
			}
		}
		fpl = "(" + fpl + ")";
	}
	
	public void msgDLA() {
		dla=""; dla = "(" + str3 + str7 + str13 + str16 + str18 + ")";
	}

	public void msgCHG() {
		chg=""; chg = "(" + str3 + str7 + str13 + str16 + str18 + str22 + ")";
	}

	public void msgCNL() {
		cnl=""; cnl = "(" + str3 + str7 + str13 + str16 + str18 + ")";
	}
	
	public void msgDEP() {
		dep=""; dep = "(" + str3 + str7 + str13 + str16 + str18 + ")";
	}
	
	public void msgARR() {
		arr=""; arr = "(" + str3 + str7 + str13 + str17 + ")";
	}
	
	public void msgCDN() {
		cdn=""; cdn = "(" + str3 + str7 + str13 + str16 + str22 + ")";
	}
	
	public void msgCPL() {
		String str378=str3+str7+str8;
		String str910=str9+str10;
		String str1314=str13+str14;
		 
		String str3913="";
		if (!str378.equals("")) {
			if (!str910.equals("")) {
				if (!str1314.equals("")) { str3913=str378+"\n"+str910+"\n"+str1314; } else { str3913=str378+"\n"+str910; }
			} else {
				if (!str1314.equals("")) { str3913=str378+"\n"+str1314; } else { str3913=str378; }
			} 
		} else {
			if (!str910.equals("")) {
				if (!str1314.equals("")) { str3913=str910+"\n"+str1314; } else { str3913=str910; }
			} else {
				if (!str1314.equals("")) { str3913=str1314; } else { str3913=""; }
			}
		}

		String str151618="";
		if (!str15.equals("")) {
			if (!str16.equals("")) {
				if (!str18.equals("")) { str151618=str15+"\n"+str16+"\n"+str18; } else { str151618=str15+"\n"+str16; }
			} else {
				if (!str18.equals("")) { str151618=str15+"\n"+str18; } else { str151618=str15; }
			} 
		} else {
			if (!str16.equals("")) {
				if (!str18.equals("")) { str151618=str16+"\n"+str18; } else { str151618=str16; }
			} else {
				if (!str18.equals("")) { str151618=str18; } else { str151618=""; }
			}
		}

		cpl="";
		if (!str3913.equals("")) {
			if (!str151618.equals("")) { cpl=str3913+"\n"+str151618; } else { cpl=str3913; }	 
		} else {
			if (!str151618.equals("")) { cpl=str151618; } else { cpl=""; }
		}
		cpl = "(" + cpl + ")";
	}
	
	public void msgEST() {
		est=""; est = "(" + str3 + str7 + str13 + str14 + str16 + ")";
	}
	
	public void msgACP() {
		acp=""; acp = "(" + str3 + str7 + str13 + str16 + ")";
	}
	
	public void msgLAM() {
		lam=""; lam = "(" + str3 + ")";
	}
	
	public void msgRQP() {
		rqp=""; rqp = "(" + str3 + str7 + str13 + str16 + str18 + ")";
	}
	
	public void msgRQS() {
		rqs=""; rqs = "(" + str3 + str7 + str13 + str16 + str18 + ")";
	}
	
	public void msgSPL() {
		String str3713=str3+str7+str13;
		String str161819="";
		if (!str16.equals("")) {
			if (!str18.equals("")) {
				if (!str19.equals("")) { str161819=str16+"\n"+str18+"\n"+str19; } else { str161819=str16+"\n"+str18; }
			} else {
				if (!str19.equals("")) { str161819=str16+"\n"+str19; } else { str161819=str16; }
			}
		} else {
			if (!str18.equals("")) {
				if (!str19.equals("")) { str161819=str18+"\n"+str19; } else { str161819=str18; }
			} else {
				if (!str19.equals("")) { str161819=str19; } else { str161819=""; }
			}
		}
		 
		spl="";
		if (!str3713.equals("")) {
			if (!str161819.equals("")) { spl=str3713+"\n"+str161819; } else { spl=str3713; }
		} else {
			if (!str161819.equals("")) { spl=str161819; } else { spl=""; }
		}		
		spl = "(" + spl + ")";
	}
	
	public void vqueue(String freeText, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = "Queue Message(s) View";
		if (Shells.sh_vQueue.isDisposed()) { Shells.sh_vQueue = new Shell(); }
		if (!Shells.sh_vQueue.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vQueue, sTitle, "Queue.txt", freeText);
		} else {
			Shells.sh_vQueue.close();
			Shells.sh_vQueue = new Shell();
			ViewAction.view(sKet, Shells.sh_vQueue, sTitle, "Queue.txt", freeText);
		} // end of shell isVisible
	}
	
	public void vfree(String freeText, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = Title.tAFTN;
		if (Shells.sh_vAFTN.isDisposed()) { Shells.sh_vAFTN = new Shell(); }
		if (!Shells.sh_vAFTN.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vAFTN, sTitle, "AFTN.txt", freeText);
		} else {
			Shells.sh_vAFTN.close();
			Shells.sh_vAFTN = new Shell();
			ViewAction.view(sKet, Shells.sh_vAFTN, sTitle, "AFTN.txt", freeText);
		} // end of shell isVisible
	}
	
	public void valr(String alr, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = Title.tALR;
		if (Shells.sh_vALR.isDisposed()) { Shells.sh_vALR = new Shell(); }
		if (!Shells.sh_vALR.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vALR, sTitle, "ALR.txt", alr);
		} else {
			Shells.sh_vALR.close();
			Shells.sh_vALR = new Shell();
			ViewAction.view(sKet, Shells.sh_vALR, sTitle, "ALR.txt", alr);
		} // end of shell isVisible	
	}
	
	public void vrcf(String rcf, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = Title.tRCF;
		if (Shells.sh_vRCF.isDisposed()) { Shells.sh_vRCF = new Shell(); }
		if (!Shells.sh_vRCF.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vRCF, sTitle, "RCF.txt", rcf);
		} else {
			Shells.sh_vRCF.close();
			Shells.sh_vRCF = new Shell();
			ViewAction.view(sKet, Shells.sh_vRCF, sTitle, "RCF.txt", rcf);
		} // end of shell isVisible
	}
	
	public void vfpl(String fpl, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = Title.tFPL;
		if (Shells.sh_vFPL.isDisposed()) { Shells.sh_vFPL = new Shell(); }
		if (!Shells.sh_vFPL.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vFPL, sTitle, "FPL.txt", fpl);
		} else {
			Shells.sh_vFPL.close();
			Shells.sh_vFPL = new Shell();
			ViewAction.view(sKet, Shells.sh_vFPL, sTitle, "FPL.txt", fpl);
		} // end of shell isVisible
	}
	
	public void vdla(String dla, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = Title.tDLA;
		if (Shells.sh_vDLA.isDisposed()) { Shells.sh_vDLA = new Shell(); }
		if (!Shells.sh_vDLA.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vDLA, sTitle, "DLA.txt", dla);
		} else {
			Shells.sh_vDLA.close();
			Shells.sh_vDLA = new Shell();
			ViewAction.view(sKet, Shells.sh_vDLA, sTitle, "DLA.txt", dla);
		} // end of shell isVisible
	}
	
	public void vchg(String chg, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = Title.tCHG;
		if (Shells.sh_vCHG.isDisposed()) { Shells.sh_vCHG = new Shell(); }
		if (!Shells.sh_vCHG.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vCHG, sTitle, "CHG.txt", chg);
		} else {
			Shells.sh_vCHG.close();
			Shells.sh_vCHG = new Shell();
			ViewAction.view(sKet, Shells.sh_vCHG, sTitle, "CHG.txt", chg);
		} // end of shell isVisible
	}
	
	public void vcnl(String cnl, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = Title.tCNL;
		if (Shells.sh_vCNL.isDisposed()) { Shells.sh_vCNL = new Shell(); }
		if (!Shells.sh_vCNL.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vCNL, sTitle, "CNL.txt", cnl);
		} else {
			Shells.sh_vCNL.close();
			Shells.sh_vCNL = new Shell();
			ViewAction.view(sKet, Shells.sh_vCNL, sTitle, "CNL.txt", cnl);
		} // end of shell isVisible
	}
	
	public void vdep(String dep, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = Title.tDEP;
		if (Shells.sh_vDEP.isDisposed()) { Shells.sh_vDEP = new Shell(); }
		if (!Shells.sh_vDEP.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vDEP, sTitle, "DEP.txt", dep);
		} else {
			Shells.sh_vDEP.close();
			Shells.sh_vDEP = new Shell();
			ViewAction.view(sKet, Shells.sh_vDEP, sTitle, "DEP.txt", dep);
		} // end of shell isVisible
	}
	
	public void varr(String arr, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = Title.tARR;
		if (Shells.sh_vARR.isDisposed()) { Shells.sh_vARR = new Shell(); }
		if (!Shells.sh_vARR.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vARR, sTitle, "ARR.txt", arr);
		} else {
			Shells.sh_vARR.close();
			Shells.sh_vARR = new Shell();
			ViewAction.view(sKet, Shells.sh_vARR, sTitle, "ARR.txt", arr);
		} // end of shell isVisible
	}
	
	public void vcdn(String cdn, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = Title.tCDN;
		if (Shells.sh_vCDN.isDisposed()) { Shells.sh_vCDN = new Shell(); }
		if (!Shells.sh_vCDN.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vCDN, sTitle, "CDN.txt", cdn);
		} else {
			Shells.sh_vCDN.close();
			Shells.sh_vCDN = new Shell();
			ViewAction.view(sKet, Shells.sh_vCDN, sTitle, "CDN.txt", cdn);
		} // end of shell isVisible
	}
	
	public void vcpl(String cpl, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = Title.tCPL;
		if (Shells.sh_vCPL.isDisposed()) { Shells.sh_vCPL = new Shell(); }
		if (!Shells.sh_vCPL.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vCPL, sTitle, "CPL.txt", cpl);
		} else {
			Shells.sh_vCPL.close();
			Shells.sh_vCPL = new Shell();
			ViewAction.view(sKet, Shells.sh_vCPL, sTitle, "CPL.txt", cpl);
		} // end of shell isVisible
	}
	
	public void vest(String est, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = Title.tEST;
		if (Shells.sh_vEST.isDisposed()) { Shells.sh_vEST = new Shell(); }
		if (!Shells.sh_vEST.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vEST, sTitle, "EST.txt", est);
		} else {
			Shells.sh_vEST.close();
			Shells.sh_vEST = new Shell();
			ViewAction.view(sKet, Shells.sh_vEST, sTitle, "EST.txt", est);
		} // end of shell isVisible
	}
	
	public void vacp(String acp, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = Title.tACP;
		if (Shells.sh_vACP.isDisposed()) { Shells.sh_vACP = new Shell(); }
		if (!Shells.sh_vACP.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vACP, sTitle, "ACP.txt", acp);
		} else {
			Shells.sh_vACP.close();
			Shells.sh_vACP = new Shell();
			ViewAction.view(sKet, Shells.sh_vACP, sTitle, "ACP.txt", acp);
		} // end of shell isVisible
	}
	
	public void vlam(String lam, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = Title.tLAM;
		if (Shells.sh_vLAM.isDisposed()) { Shells.sh_vLAM = new Shell(); }
		if (!Shells.sh_vLAM.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vLAM, sTitle, "LAM.txt", lam);
		} else {
			Shells.sh_vLAM.close();
			Shells.sh_vLAM = new Shell();
			ViewAction.view(sKet, Shells.sh_vLAM, sTitle, "LAM.txt", lam);
		} // end of shell isVisible
	}
	
	public void vrqp(String rqp, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = Title.tRQP;
		if (Shells.sh_vRQP.isDisposed()) { Shells.sh_vRQP = new Shell(); }
		if (!Shells.sh_vRQP.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vRQP, sTitle, "RQP.txt", rqp);
		} else {
			Shells.sh_vRQP.close();
			Shells.sh_vRQP = new Shell();
			ViewAction.view(sKet, Shells.sh_vRQP, sTitle, "RQP.txt", rqp);
		} // end of shell isVisible
	}
	
	public void vrqs(String rqs, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = Title.tRQS;
		if (Shells.sh_vRQS.isDisposed()) { Shells.sh_vRQS = new Shell(); }
		if (!Shells.sh_vRQS.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vRQS, sTitle, "RQS.txt", rqs);
		} else {
			Shells.sh_vRQS.close();
			Shells.sh_vRQS = new Shell();
			ViewAction.view(sKet, Shells.sh_vRQS, sTitle, "RQS.txt", rqs);
		} // end of shell isVisible
	}
	
	public void vspl(String spl, String sTitle, String sKet) {
		if (sTitle.isEmpty()) sTitle = Title.tSPL;
		if (Shells.sh_vSPL.isDisposed()) { Shells.sh_vSPL = new Shell(); }
		if (!Shells.sh_vSPL.isVisible()) {
			ViewAction.view(sKet, Shells.sh_vSPL, sTitle, "SPL.txt", spl);
		} else {
			Shells.sh_vSPL.close();
			Shells.sh_vSPL = new Shell();
			ViewAction.view(sKet, Shells.sh_vSPL, sTitle, "SPL.txt", spl);
		} // end of shell isVisible
	}
	
	// fungsi yang dipanggil untuk menampilkan / view masing-masing tipe berita
	public void viewQueue(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					String queuedata="";
					try {
						queuedata = rs.getString("data"); if (queuedata == null) queuedata="";
					} catch (SQLException sqlex) {
						System.err.println(sqlex.getMessage());
					}
					
					pfreeText=freeText=queuedata;
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view

	public void viewWarning(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					String msg="",reason="",tgl="";
					try {
						msg = rs.getString("message"); if (msg == null) msg="";
						reason = rs.getString("reason"); if (reason == null) reason="";
						tgl = rs.getString("tgl"); if (tgl == null) tgl="";
						
					} catch (SQLException sqlex) {
						System.err.println(sqlex.getMessage());
					}
					
					pfreeText=freeText=tgl+"\nMessage:\n"+msg+"\nReason:\n"+reason+"\n";
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view
	
		
	public void viewATS(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					setHeader(rs); setIO(rs); setFree(rs); setFb(rs);
					
					String sFreetext=strFree;
					if (sFreetext.startsWith("amo")) sFreetext=sFreetext.replaceFirst("amo", "");

					pfreeText=header+sFreetext+"\n";
					freeText=strFb+header+sFreetext+"\n";
					
//					pfreeText = header + strFree + "\n";
//					freeText = header + strFree + strFb;
					
//					if (btn.compareToIgnoreCase("print")!=0) {
//						if (Shells.sh_vFREE.isDisposed()) { Shells.sh_vFREE = new Shell(); }
//						if (!Shells.sh_vFREE.isVisible()) {
//							ViewAction.view(sKet, Shells.sh_vFREE, "Free Text ATS", "FreeATS.txt", freeText, posX, posY, "");
//						} else {
//							Shells.sh_vFREE.close();
//							Shells.sh_vFREE = new Shell();
//							ViewAction.view(sKet, Shells.sh_vFREE, "Free Text ATS", "FreeATS.txt", freeText, posX, posY, "");
//						} // end of shell isVisible
//					}
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view
	
	public void viewALR(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					setHeader(rs); setIO(rs); set3(rs); set5(rs); set7(rs); set8(rs); set9(rs); set10(rs); set13(rs); 
					set15(rs); set16(rs); set18(rs); set19(rs); set20(rs); setFb(rs);
					msgALR();
					palr=header+alr+"\n";
					alr=strFb+header+alr+"\n";
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view
	
	public void viewRCF(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					setHeader(rs); setIO(rs); set3(rs); set7(rs); set21(rs); setFb(rs);
					msgRCF();
					prcf=header+rcf+"\n";
					rcf=strFb+header+rcf+"\n";
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view
	
	public void viewFPL(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					setHeader(rs); setIO(rs); set3(rs); set7(rs); set8(rs); set9(rs); set10(rs); set13(rs); set15(rs); set16(rs); set18(rs); 
					set19(rs); setSpace(rs); setFb(rs);
					msgFPL();
					pfpl=header+fpl+strSpace+"\n";
					fpl=strFb+header+fpl+strSpace+"\n";
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view
	
	public void viewDLA(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					setHeader(rs); setIO(rs); set3(rs); set7(rs); set13(rs); set16(rs); set18(rs); setFb(rs);
					if (atstyp.compareTo("0")==0) { str18=""; }
					dla=""; dla = "(" + str3 + str7 + str13 + str16 + str18 + ")";
					//msgDLA();
					pdla=header+dla+"\n";
					dla=strFb+header+dla+"\n";
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view
	
	public void viewCHG(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					setHeader(rs); setIO(rs); set3(rs); set7(rs); set13(rs); set16(rs); set18(rs); set22(rs); setFb(rs);
					
					if (atstyp.compareTo("0")==0) { str18=""; }
					chg=""; chg = "(" + str3 + str7 + str13 + str16 + str18 + str22 + ")";
					//msgCHG();
					pchg=header+chg+"\n";
					chg=strFb+header+chg+"\n";
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view

	public void viewCNL(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					setHeader(rs); setIO(rs); set3(rs); set7(rs); set13(rs); set16(rs); set18(rs); setFb(rs);
					
					if (atstyp.compareTo("0")==0) { str18=""; }
					cnl=""; cnl = "(" + str3 + str7 + str13 + str16 + str18 + ")";
					//msgCNL();
					pcnl=header+cnl+"\n";
					cnl=strFb+header+cnl+"\n";
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view
	
	public void viewDEP(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					setHeader(rs); setIO(rs); set3(rs); set7(rs); set13(rs); set16(rs); set18(rs); setFb(rs);
					
					if (atstyp.compareTo("0")==0) { str18=""; }
					dep=""; dep = "(" + str3 + str7 + str13 + str16 + str18 + ")";
					//msgDEP();
					pdep=header+dep+"\n";
					dep=strFb+header+dep+"\n";
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view
	
	public void viewARR(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					setHeader(rs); setIO(rs); set3(rs); set7(rs); set13(rs); set17(rs); setFb(rs);
					msgARR();
					parr=header+arr+"\n";
					arr=strFb+header+arr+"\n";
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view
	
	public void viewCDN(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					setHeader(rs); setIO(rs); set3(rs); set7(rs); set13(rs); set16(rs); set22(rs); setFb(rs);
					msgCDN();
					pcdn=header + cdn + "\n";
					cdn=strFb+header+cdn+"\n";
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view
	
	public void viewCPL(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					setHeader(rs); setIO(rs); set3(rs); set7(rs); set8(rs); set9(rs); set10(rs); set13(rs); set14(rs); 
					set15(rs); set16(rs); set18(rs); setFb(rs);
					msgCPL();
					pcpl=header+cpl+"\n";
					cpl=strFb+header+cpl+"\n";
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view
	
	public void viewEST(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					setHeader(rs); setIO(rs); set3(rs); set7(rs); set13(rs); set14(rs); set16(rs); setFb(rs);
					msgEST(); 
					pest=header+est+"\n";
					est=strFb+header+est+"\n";
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view
	
	public void viewACP(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					setHeader(rs); setIO(rs); set3(rs); set7(rs); set13(rs); set16(rs); setFb(rs);
					msgACP();
					pacp=header+acp+"\n";
					acp=strFb+header+acp+"\n";
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view
	
	public void viewLAM(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					setHeader(rs); setIO(rs); set3(rs); setFb(rs);
					msgLAM();
					plam=header+lam+"\n";
					lam=strFb+header+lam+"\n";
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view
	
	public void viewRQP(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					setHeader(rs); setIO(rs); set3(rs); set7(rs); set13(rs); set16(rs); set18(rs); setFb(rs);
					
					if (atstyp.compareTo("0")==0) { str18=""; }
					rqp=""; rqp = "(" + str3 + str7 + str13 + str16 + str18 + ")";
					//msgRQP();
					prqp=header+rqp+"\n";
					rqp=strFb+header+rqp+"\n";
					
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view

	public void viewRQS(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					setHeader(rs); setIO(rs); set3(rs); set7(rs); set13(rs); set16(rs); set18(rs); setFb(rs);
					
					if (atstyp.compareTo("0")==0) { str18=""; }
					rqs=""; rqs = "(" + str3 + str7 + str13 + str16 + str18 + ")";
					//msgRQS();
					prqs=header+rqs+"\n";
					rqs=strFb+header+rqs+"\n";
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view
	
	public void viewSPL(Statement stmt,boolean hasilExe,String btn) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount(); System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					setHeader(rs); setIO(rs); set3(rs); set7(rs); set13(rs); set16(rs); set18(rs); set19(rs); setFb(rs);
					msgSPL();
					pspl=header+spl+"\n";
					spl=strFb+header+spl+"\n";
				} // end of while 
			} // end of if
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} // end of view

	void editType20(String t20) { s20Luar=t20; }
	void editType21(String t21) { s21Luar=t21; }
	void editType22(String t22) { s22Luar=t22; }
	void editSpace(String tSpace) { sSpaceLuar=tSpace; }
	void editFree(String tFree) { sFreeLuar=tFree; }
	
	void editType18(String t18a, String t18b, String t18c) {
		/**
		 * untuk versi lama [current] :
		 * (type18a="") ? REG/0 : REG/[isinya]
		 * (type18b="") ? DOF/0 : DOF/[isinya]
		 * 
		 * untuk versi baru [new] :
		 * type18a=""
		 * type18b=""
		 * 
		 * 
		 */
		
		//REG/
		String sReg="",sDof="",type18="";
		if (t18a==null) t18a="";
		if (t18b==null) t18b="";
		if (t18c==null) t18c="";
		
		if (t18a.compareTo("REG/0")==0) {//versi lama
			sReg=jdbc.get18Reg(); //080611
		} else { //versi baru
			sReg=jdbc.get18a();
		}
		if (sReg.equals("0")) sReg="";
//		t18a.setText(sReg);
		
		//DOF/
		if (t18b.compareTo("DOF/0")==0) {
			sDof=jdbc.get18Dof(); //080611
		} else {
			sDof=jdbc.get18b();
		}
		if (sDof.equals("0")) sDof="";
//		t18b.setText(sDof);
		
		//ALL18/
		String s_Dof=""; String s_Reg="";
		type18 = t18c;
		if (!type18.equals("")) {
			int i=0;
	        String x="";
			String sub[] = type18.split(" ");
			
			for (i=0; i<sub.length; i++) {
				if (sub[i].contains("/")) {
					x=sub[i];
				} else {
					x+=" "+sub[i];
				}

				if (x.startsWith("DOF/")) s_Dof = x.substring(0, x.length());
				else if (x.startsWith("REG/")) s_Reg = x.substring(0, x.length());
			} //end of for sub[i]
		} else {
			s_Dof=""; s_Reg="";
		}
		
//		System.out.println("s_Dof="+s_Dof+"*s_Reg="+s_Reg);
		if (type18.startsWith(" ")) type18 = type18.replaceFirst(" ", "");
		s18Luar=type18;
	}
}
