package actions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import setting.Titles;


public class ViewRetrieve {
	ViewATS vATS = new ViewATS();
	
	public String preject="",pret="",prettrace="";
	
	 
	public void view(Statement stmt, String ID) {
		try {
			ResultSet rs=stmt.getResultSet();
			while (rs.next()) { 
				set(rs, ID);
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} 
	
	public void set(ResultSet rs, String sID) {
		try {
			if (sID.compareTo(Titles.stIncoming)==0 || sID.compareTo(Titles.stOutgoing)==0) {
				String cid = rs.getString("cid"); if (cid==null) cid="";
				String seq = rs.getString("seq"); if (seq==null) seq="";
				
				String dt = rs.getString("data"); if (dt==null) dt="";
				form(sID,cid,seq,dt);//form(dt);
				
				String trc = rs.getString("trace"); if (trc==null) trc="";
				formtrc(trc); }
			
			if (sID.compareTo(Titles.stReject)==0) {
				String dt = rs.getString("origin_message"); if (dt==null) dt="";
				formReject(dt);
			}
			
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void form(String sID,String cid,String seq,String text) {
		if (sID.compareTo(Titles.stOutgoing)==0) {
			if (text.startsWith(cid+seq)) {
				if (text.contains("\n")) {
					int index = text.indexOf("\n");
					text=text.substring(index+1, text.length());
				}
			}	
//		} else if (sID.compareTo(Titles.stIncoming)==0) {
			
		}
		
//		if (text.startsWith(cid+seq)) {
//			if (text.contains("\n")) {
//				int index = text.indexOf("\n");
//				text=text.substring(index+1, text.length());
//			}
//		}
		pret=text+"\n"; }
	
//	public void form(String text) { pret=text+"\n"; }
	
	public void formtrc(String text) { prettrace=text+"\n"; }
	
	public void formReject(String text) { preject=text+"\n"; }
}
