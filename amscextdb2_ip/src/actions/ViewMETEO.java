package actions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import setting.Titles;


public class ViewMETEO {
	ViewATS vATS = new ViewATS();
	
	static String meteoMetar="",meteoSigmet="",meteoAirep="";
	public String pfreemet="",pmetar="",psigmet="",pairep="";
	
	public String origin="",msgID="",location="",timeobs="",issued="",option="",text="",meteo_wo="";
	static String message_id="",sequence="",valid_from="",valid_to="",reqId="",reqMsg="";	
	 
	public void viewMETEO(Statement stmt, String ID) {
		try {
			ResultSet rs=stmt.getResultSet();
			while (rs.next()) { 
				vATS.setHeader(rs); 
				setMETEO(rs, ID);
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} 
	
	public void setMETEO(ResultSet rs, String sID) {
		try {
			
			
			String type="";
			if (sID.compareTo(Titles.stFREETEXT)==0 || sID.compareTo(Titles.stVOL)==0 || sID.compareTo(Titles.stRQM)==0) {
				if (sID.compareTo(Titles.stRQM)==0) { text = rs.getString("request_message"); if (text==null) text=""; }
				else if (sID.compareTo(Titles.stVOL)==0) { text = rs.getString("text"); if (text==null) text=""; }
				else { text = rs.getString("manual_ats"); if (text==null) text=""; }
				
				if (text.contains("`")) text = text.replace("`", "'");
				if (text.contains("\n")) text = text.substring(0, text.indexOf("\n")).concat(" ...");
				else if (text.length()>=50) text = text.substring(0, 50).concat(" ...");
				
				if (sID.compareTo(Titles.stRQM)==0 && !text.isEmpty()) text="RQM/"+text;
				formFREE(); }
			else {
				String msg12 = rs.getString("message_id_12"); if (msg12 == null) msg12="";
				String msg34 = rs.getString("message_id_34"); if (msg34 == null) msg34="";
				String msg56 = rs.getString("message_id_56"); if (msg56 == null) msg56="";
				origin = rs.getString("originator_message"); if (origin == null) origin="";
				issued = rs.getString("issued_message"); if (issued == null) issued="";
				option = rs.getString("correction_meteo"); if (option == null) option="";
				
				msgID=msg12+msg34+msg56;
				
				if (sID.compareTo(Titles.stMETAR)==0 || sID.compareTo(Titles.stSPECI)==0) { 
					type = rs.getString("Type"); if (type == null) type="";
					location = rs.getString("Location"); if (location == null) location="";
					timeobs = rs.getString("time_observation"); if (timeobs == null) timeobs="";
					text = rs.getString("text"); if (text == null) text=""; if (text.contains("`")) text = text.replace("`", "'");
					formMETAR(msg12, msg34, msg56, origin, issued, option, type, location, timeobs, text); }

				else if (sID.compareTo(Titles.stSIGMET)==0 || sID.compareTo(Titles.stAIRMET)==0 ||
						sID.compareTo(Titles.stWINSWAR)==0) { 
					type = rs.getString("message_id"); if (type == null) type="";//MESSAGE_ID
					if (sID.compareTo(Titles.stWINSWAR)==0) { location = rs.getString("location"); if (location == null) location=""; }
					else { location = rs.getString("Location_ats"); if (location == null) location="";}
					text = rs.getString("text"); if (text == null) text=""; if (text.contains("`")) text = text.replace("`", "'");
					
					String sequence="",valid_from="",valid_to="",meteo_wo="";
					if (sID.compareTo(Titles.stSIGMET)==0 || sID.compareTo(Titles.stAIRMET)==0) {
						sequence = rs.getString("seq_number"); if (sequence == null) sequence="";
						valid_from = rs.getString("valid_from"); if (valid_from == null) valid_from="";
						valid_to = rs.getString("valid_to"); if (valid_to == null) valid_to="";
						meteo_wo = rs.getString("meteo_wo"); if (meteo_wo == null) meteo_wo="";
					} else if (sID.compareTo(Titles.stWINSWAR)==0) {
						sequence = "";// uda ada di atas rs.getString("message_id"); if (sequence == null) sequence="";
						valid_from = rs.getString("from1"); if (valid_from == null) valid_from="";
						valid_to = rs.getString("to1"); if (valid_to == null) valid_to="";
					}
					
					formSIGMET(msg12, msg34, msg56, origin, issued, option, type, location, text,sequence,valid_from,valid_to,meteo_wo); }
				
				else if (sID.compareTo(Titles.stAIREP)==0 || sID.compareTo(Titles.stTAFOR)==0 ||
						sID.compareTo(Titles.stARFOR)==0 || sID.compareTo(Titles.stROFOR)==0 ||
						sID.compareTo(Titles.stWINTEM)==0 || sID.compareTo(Titles.stADV)==0 ||
						sID.compareTo(Titles.stSYNOP)==0 || sID.compareTo(Titles.stWARSYN)==0) { 
					if (sID.compareTo(Titles.stWARSYN)==0) { 
						text = rs.getString("warning_synop"); if (text == null) text=""; if (text.contains("`")) text = text.replace("`", "'");
					} else { text = rs.getString("text"); if (text == null) text=""; if (text.contains("`")) text = text.replace("`", "'"); }
					formAIREP(msg12, msg34, msg56, origin, issued, option, type, text); }			
			
			}			
			
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void formFREE() {
		pfreemet= ViewATS.header + text + "\n";
	}
	
	public void formMETAR(String msg12, String msg34, String msg56, String origin, String issued, String corr, String type, String location,
			String timeobs, String text) {

		String MsgId = msg12+msg34+msg56;
		if (MsgId.equals("")) { MsgId=""; }
		 
		String OIC="";
		if (!origin.equals("")) {
			if(!issued.equals("")) {
				if(!corr.equals("")) { OIC=origin+" "+issued+" "+corr; } else { OIC=origin+" "+issued; }
			} else {
				if(!corr.equals("")) { OIC=origin+" "+corr; } else { OIC=origin; }
			}
		} else {
			if(!issued.equals("")) {
				if(!corr.equals("")) { OIC=issued+" "+corr; } else { OIC=issued; }
			} else {
				if(!corr.equals("")) { OIC=corr; } else { OIC=""; }
			}
		}
		 
		String MsgOIC="";
		if (!MsgId.equals("")) {
			if (!OIC.equals("")) { MsgOIC=MsgId+" "+OIC; } else { MsgOIC=MsgId; }
		} else {
			if (!OIC.equals("")) { MsgOIC=OIC; } else { MsgOIC=""; }					 
		}
		//-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
		String MLO="";
		if (!type.equals("")) {
			if(!location.equals("")) {
				if(!timeobs.equals("")) { MLO=type+" "+location+" "+timeobs+"Z"; } else { MLO=type+" "+location; }
			} else {
				if(!timeobs.equals("")) { MLO=type+" "+timeobs+"Z"; } else { MLO=type; }
			}
		} else {
			if(!location.equals("")) {
				if(!timeobs.equals("")) { MLO=location+" "+timeobs+"Z"; } else { MLO=location; }
			} else {
				if(!timeobs.equals("")) { MLO=timeobs+"Z"; } else { MLO=""; }
			}
		}	
		//-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
		meteoMetar="";
		if (!MsgOIC.equals("")) {
			if (!MLO.equals("")) {
				if (!text.equals("")) { meteoMetar = MsgOIC+"\n"+MLO+" "+text; } 
				else { meteoMetar = MsgOIC+"\n"+MLO; }
			} else {
				if (!text.equals("")) { meteoMetar = MsgOIC+"\n"+text; } 
				else { meteoMetar = MsgOIC; }
			}
		} else {
			if (!MLO.equals("")) {
				if (!text.equals("")) { meteoMetar = MLO+" "+text; } 
				else { meteoMetar = MLO; }
			} else {
				if (!text.equals("")) { meteoMetar = text; } 
				else { meteoMetar = ""; }
			 }
		}	
		pmetar = ViewATS.header + meteoMetar + "\n";
	}

		
	public void formSIGMET(String msg12,String msg34,String msg56,String origin,String issued,String corr,String message_id,String location,
			String text,String sequence,String valid_from,String valid_to,String meteo_wo) {

		String MsgId = msg12+msg34+msg56;
		if (MsgId.equals("")) { MsgId=""; }
		 
		String OIC="";
		if (!origin.equals("")) {
			if(!issued.equals("")) {
				if(!corr.equals("")) { OIC=origin+" "+issued+" "+corr; } else { OIC=origin+" "+issued; }
			} else {
				if(!corr.equals("")) { OIC=origin+" "+corr; } else { OIC=origin; }
			}	
		} else {
			if(!issued.equals("")) {
				if(!corr.equals("")) { OIC=issued+" "+corr; } else { OIC=issued; }
			} else {
				if(!corr.equals("")) { OIC=corr; } else { OIC=""; }
			}
		}
		 
		String MsgOIC="";
		if (!MsgId.equals("")) {
			if (!OIC.equals("")) { MsgOIC=MsgId+" "+OIC; } else { MsgOIC=MsgId; }
		} else {
			if (!OIC.equals("")) { MsgOIC=OIC; } else { MsgOIC=""; }					 
		}	
		//-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
		String LTS="";
		if (!location.equals("")) {
			if(!message_id.equals("")) {
				if(!sequence.equals("")) { LTS=location+" "+message_id+" "+sequence; } else { LTS=location+" "+message_id; }
			} else {
				if(!sequence.equals("")) { LTS=location+" "+sequence; } else { LTS=location; }
			}
		} else {
			if(!message_id.equals("")) {
				if(!sequence.equals("")) { LTS=message_id+" "+sequence; } else { LTS=message_id; }
			} else {
				if(!sequence.equals("")) { LTS=sequence; } else { LTS=""; }
			}
		}
			
		String VM="";
		if (!valid_from.equals("")) {
			if(!valid_to.equals("")) {
				if(!meteo_wo.equals("")) { VM="VALID "+valid_from+"/"+valid_to+" "+meteo_wo; } else { VM="VALID "+valid_from+"/"+valid_to; }
			} else {
				if(!meteo_wo.equals("")) { VM="VALID "+valid_from+" "+meteo_wo; } else { VM="VALID "+valid_from; }
			}	
		} else {
			if(!valid_to.equals("")) {
				if(!meteo_wo.equals("")) { VM="VALID /"+valid_to+" "+meteo_wo; } else { VM="VALID /"+valid_to; }
			} else {
				if(!meteo_wo.equals("")) { VM=meteo_wo; } else { VM=""; }
			}
		}

		String LTSVM="";
		if (!LTS.equals("")) {
			if (!VM.equals("")) { LTSVM=LTS+" "+VM+"-"; } else { LTSVM=LTS+"-"; }
		} else {
			if (!VM.equals("")) { LTSVM=VM+"-"; } else { LTSVM=""; }					 
		} 
		
		if (message_id.compareTo("WS WRNG")==0) {
			if (!LTSVM.isEmpty() && LTSVM.endsWith("-")) { LTSVM=LTSVM.substring(0, LTSVM.lastIndexOf("-")); }
		}
		//-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
		meteoSigmet="";
		if (!MsgOIC.equals("")) {
			if (!LTSVM.equals("")) {
				if (!text.equals("")) { meteoSigmet = MsgOIC+"\n"+LTSVM+"\n"+text; } 
				else { meteoSigmet = MsgOIC+"\n"+LTSVM; }
			} else {
				if (!text.equals("")) { meteoSigmet = MsgOIC+"\n"+text; } 
				else { meteoSigmet = MsgOIC; }
			}
		} else {
			if (!LTSVM.equals("")) {
				if (!text.equals("")) { meteoSigmet = LTSVM+"\n"+text; } 
				else { meteoSigmet = LTSVM; }
			} else {
				if (!text.equals("")) { meteoSigmet = text; } 
				else { meteoSigmet = ""; }
			}
		}
		psigmet = ViewATS.header + meteoSigmet+ "\n";
	}

	public void formAIREP(String msg12,String msg34,String msg56,String origin,String issued,String corr,String type,String text) {
		//BODY
		String MsgId = msg12+msg34+msg56;
		if (MsgId.equals("")) { MsgId=""; }
		
		String OIC="";
		if (!origin.equals("")) {
			if(!issued.equals("")) {
				if(!corr.equals("")) { OIC=origin+" "+issued+" "+corr; } else { OIC=origin+" "+issued; }
			} else {
				if(!corr.equals("")) { OIC=origin+" "+corr; } else { OIC=origin; }
			}
		} else {
			if(!issued.equals("")) {
				if(!corr.equals("")) { OIC=issued+" "+corr; } else { OIC=issued; }
			} else {
				if(!corr.equals("")) { OIC=corr; } else { OIC=""; }
			}
		}
		 
		String MsgOIC="";
		if (!MsgId.equals("")) {
			if (!OIC.equals("")) { MsgOIC=MsgId+" "+OIC; } else { MsgOIC=MsgId; }
		} else {
			if (!OIC.equals("")) { MsgOIC=OIC; } else { MsgOIC=""; }					 
		}	
		//-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
		meteoAirep="";
		if (!MsgOIC.equals("")) {
			if (!text.equals("")) { meteoAirep = MsgOIC+"\n"+text; } else { meteoAirep = MsgOIC; }
		} else {
			if (!text.equals("")) { meteoAirep = text; } else { meteoAirep = ""; }
		}
		pairep = ViewATS.header + meteoAirep + "\n";
	}	 
}
