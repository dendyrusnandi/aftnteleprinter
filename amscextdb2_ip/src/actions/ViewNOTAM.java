package actions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import setting.Titles;


public class ViewNOTAM {

	ViewATS vATS = new ViewATS();
	
	public String locind="",issued="",state="",sn1="",timeobs="",optgrp="",sn2="",identifiers="",purpose="",scope="",A="",birdnum="",efftime="",
	exptime="",intlev="",reqid="",reqmsg="",sNotamNo="",sRefNotamNo="",sNotamCode="",sStatusNotam="",sStatusRqntm="",
	snowtam="",ashtam="",birdtam="",rqn="",rql="",pnotam="",psnowtam="",pashtam="",pbirdtam="",prqn="",prql="";
	
	//void set.... dipakai untuk list ATS Message versi AFTN void set-> public void set... 
	public void setNOTAM(ResultSet rs) {
		try {
			String ntm_id_serie = rs.getString("ntm_id_serie"); if (ntm_id_serie == null) ntm_id_serie="";
			String ntm_id_sequence = rs.getString("ntm_id_sequence"); if (ntm_id_sequence == null) ntm_id_sequence="";
			String ntm_id_years = rs.getString("ntm_id_years"); if (ntm_id_years == null) ntm_id_years="";
			sNotamNo=ntm_id_serie+ntm_id_sequence+"/"+ntm_id_years;
			if (sNotamNo.compareTo("/")==0) sNotamNo="";
			identifiers = rs.getString("identifiers"); if (identifiers == null) identifiers="";
			String part= rs.getString("part"); if (part == null) part="";
			String part_nr = rs.getString("part_nr"); if (part_nr == null) part_nr="";
			String ref_ntm_id_serie = rs.getString("ref_ntm_id_serie"); if (ref_ntm_id_serie == null) ref_ntm_id_serie="";
			String ref_ntm_id_sequence = rs.getString("ref_ntm_id_sequence"); if (ref_ntm_id_sequence == null) ref_ntm_id_sequence="";
			String ref_ntm_id_years = rs.getString("ref_ntm_id_years"); if (ref_ntm_id_years == null) ref_ntm_id_years="";
			if (!ref_ntm_id_serie.isEmpty() || !ref_ntm_id_sequence.isEmpty() || !ref_ntm_id_years.isEmpty()) {
			sRefNotamNo=ref_ntm_id_serie+ref_ntm_id_sequence+"/"+ref_ntm_id_years; } else sRefNotamNo="";
			if (sRefNotamNo.compareTo("/")==0) sRefNotamNo="";
			String ref_part = rs.getString("ref_part"); if (ref_part == null) ref_part="";
			String ref_part_nr = rs.getString("ref_part_nr"); if (ref_part_nr == null) ref_part_nr="";
			String fir = rs.getString("fir"); if (fir == null) fir="";
			String code23 = rs.getString("code_23"); if (code23 == null) code23="";
			String code45 = rs.getString("code_45"); if (code45 == null) code45="";
			sNotamCode=code23+code45;
			String trafic = rs.getString("trafic"); if (trafic == null) trafic="";
			purpose = rs.getString("purpose"); if (purpose == null) purpose="";
			scope = rs.getString("scope"); if (scope == null) scope="";
			String lower = rs.getString("lower"); if (lower == null) lower="";
			String upper = rs.getString("upper"); if (upper == null) upper="";
			String coordinates = rs.getString("coordinates"); if (coordinates == null) coordinates="";
			String radius = rs.getString("radius"); if (radius == null) radius="";
			A = rs.getString("A"); if (A == null) A="";
			String B = rs.getString("B"); if (B == null) B="";
			String C = rs.getString("C"); if (C == null) C=""; 
			String est_perm = rs.getString("est_perm"); if (est_perm == null) est_perm="";
			String D = rs.getString("D"); if (D == null) D="";
			String E = rs.getString("E"); if (E == null) E="";
			String FNot = rs.getString("F"); if (FNot == null) FNot=""; 
			String G = rs.getString("G"); if (G == null) G="";
			sStatusNotam = rs.getString("note"); if (sStatusNotam == null) sStatusNotam=" ";
			sStatusRqntm = rs.getString("n_rqntm"); if (sStatusRqntm == null) sStatusRqntm=" ";
			
			if (A.contains("`")) A = A.replace("`", " ");
			if (D.contains("`")) D = D.replace("`", "'");
			if (E.contains("`")) E = E.replace("`", "'");
			
			formNOTAM(ntm_id_serie, ntm_id_sequence, ntm_id_years, identifiers, part, part_nr, ref_ntm_id_serie, ref_ntm_id_sequence, 
					ref_ntm_id_years, ref_part, ref_part_nr, fir, code23, code45, trafic, purpose, scope, lower, upper, coordinates, radius,
					A,B,C,est_perm,D,E,FNot,G);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void setSNOWTAM(ResultSet rs) {
		String A,B,C1,C2,C3,D1,D2,D3,E1,E2,E3,F1,F2,F3,G1,G2,G3,H1,H2,H3,J1,J2,J3,K1,K2,K3,L1,L2,L3,M1,M2,M3,
		N1,N2,N3,P1,P2,P3,R,S,T,code_sw,snow/*,state,serial_number,location_indicator,oservation,opt_group,
		snow,sn_serial_nr*/;
		try {
			code_sw = rs.getString("code_sw"); if (code_sw == null) code_sw="";
			state = rs.getString("state"); if (state == null) state="";
			sn1 = rs.getString("serial_number"); if (sn1 == null) sn1=""; 
			locind = rs.getString("location_indicator"); if (locind == null) locind=""; 
			timeobs = rs.getString("oservation"); if (timeobs == null) timeobs="";
			optgrp = rs.getString("opt_group"); if (optgrp == null) optgrp=""; 
			snow = rs.getString("snowtam"); if (snow == null) snow=""; 
			sn2 = rs.getString("sn_serial_nr"); if (sn2 == null) sn2=""; 
			A = rs.getString("A");   B  = rs.getString("B");
			C1 = rs.getString("C1"); C2 = rs.getString("C2"); C3 = rs.getString("C3");
			D1 = rs.getString("D1"); D2 = rs.getString("D2"); D3 = rs.getString("D3");
			E1 = rs.getString("E1"); E2 = rs.getString("E2"); E3 = rs.getString("E3");
			F1 = rs.getString("F1"); F2 = rs.getString("F2"); F3 = rs.getString("F3");
			G1 = rs.getString("G1"); G2 = rs.getString("G2"); G3 = rs.getString("G3");
			H1 = rs.getString("H1"); H2 = rs.getString("H2"); H3 = rs.getString("H3");
			J1 = rs.getString("J1"); J2 = rs.getString("J2"); J3 = rs.getString("J3");
			K1 = rs.getString("K1"); K2 = rs.getString("K2"); K3 = rs.getString("K3");
			L1 = rs.getString("L1"); L2 = rs.getString("L2"); L3 = rs.getString("L3");
			M1 = rs.getString("M1"); M2 = rs.getString("M2"); M3 = rs.getString("M3");
			N1 = rs.getString("N1"); N2 = rs.getString("N2"); N3 = rs.getString("N3"); 
			P1 = rs.getString("P1"); P2 = rs.getString("P2"); P3 = rs.getString("P3");
			R  = rs.getString("R");  S = rs.getString("S");   T = rs.getString("T");
			
			if (A == null) A="";   if (B == null) B="";
			if (C1 == null) C1=""; if (C2 == null) C2=""; if (C3 == null) C3="";
			if (D1 == null) D1=""; if (D2 == null) D2=""; if (D3 == null) D3="";
			if (E1 == null) E1=""; if (E2 == null) E2=""; if (E3 == null) E3="";
			if (F1 == null) F1=""; if (F2 == null) F2=""; if (F3 == null) F3="";
			if (G1 == null) G1=""; if (G2 == null) G2=""; if (G3 == null) G3="";
			if (H1 == null) H1=""; if (H2 == null) H2=""; if (H3 == null) H3="";
			if (J1 == null) J1=""; if (J2 == null) J2=""; if (J3 == null) J3="";
			if (K1 == null) K1=""; if (K2 == null) K2=""; if (K3 == null) K3="";
			if (L1 == null) L1=""; if (L2 == null) L2=""; if (L3 == null) L3=""; 
			if (M1 == null) M1=""; if (M2 == null) M2=""; if (M3 == null) M3="";
			if (N1 == null) N1=""; if (N2 == null) N2=""; if (N3 == null) N3="";
			if (P1 == null) P1=""; if (P2 == null) P2=""; if (P3 == null) P3="";
			if (R == null) R =""; if (S  == null) S =""; if (T  == null) T ="";
			
			if (C1.contains("`")) C1 = C1.replace("`", "'");
			if (D1.contains("`")) D1 = D1.replace("`", "'");
			if (E1.contains("`")) E1 = E1.replace("`", "'");
			if (F1.contains("`")) F1 = F1.replace("`", "'");
			if (G1.contains("`")) G1 = G1.replace("`", "'");
			if (H1.contains("`")) H1 = H1.replace("`", "'");
			if (J1.contains("`")) J1 = J1.replace("`", "'");
			if (K1.contains("`")) K1 = K1.replace("`", "'");
			if (L1.contains("`")) L1 = L1.replace("`", "'");
			if (M1.contains("`")) M1 = M1.replace("`", "'");
			if (N1.contains("`")) N1 = N1.replace("`", "'");
			if (P1.contains("`")) P1 = P1.replace("`", "'");
			if (C2.contains("`")) C2 = C2.replace("`", "'");
			if (D2.contains("`")) D2 = D2.replace("`", "'");
			if (E2.contains("`")) E2 = E2.replace("`", "'");
			if (F2.contains("`")) F2 = F2.replace("`", "'");
			if (G2.contains("`")) G2 = G2.replace("`", "'");
			if (H2.contains("`")) H2 = H2.replace("`", "'");
			if (J2.contains("`")) J2 = J2.replace("`", "'");
			if (K2.contains("`")) K2 = K2.replace("`", "'");
			if (L2.contains("`")) L2 = L2.replace("`", "'");
			if (M2.contains("`")) M2 = M2.replace("`", "'");
			if (N2.contains("`")) N2 = N2.replace("`", "'");
			if (P2.contains("`")) P2 = P2.replace("`", "'");
			if (C3.contains("`")) C3 = C3.replace("`", "'");
			if (D3.contains("`")) D3 = D3.replace("`", "'");
			if (E3.contains("`")) E3 = E3.replace("`", "'");
			if (F3.contains("`")) F3 = F3.replace("`", "'");
			if (G3.contains("`")) G3 = G3.replace("`", "'");
			if (H3.contains("`")) H3 = H3.replace("`", "'");
			if (J3.contains("`")) J3 = J3.replace("`", "'");
			if (K3.contains("`")) K3 = K3.replace("`", "'");
			if (L3.contains("`")) L3 = L3.replace("`", "'");
			if (M3.contains("`")) M3 = M3.replace("`", "'");
			if (N3.contains("`")) N3 = N3.replace("`", "'");
			if (P3.contains("`")) P3 = P3.replace("`", "'");
			if (R.contains("`")) R = R.replace("`", "'");
			if (T.contains("`")) T = T.replace("`", "'");
			
			formSNOWTAM(A,B,C1,C2,C3,D1,D2,D3,E1,E2,E3,F1,F2,F3,G1,G2,G3,H1,H2,H3,J1,J2,J3,K1,K2,K3,L1,L2,L3,M1,M2,M3,
				N1,N2,N3,P1,P2,P3,R,S,T,code_sw,state,sn1,locind,timeobs,optgrp,snow,sn2);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void setASHTAM(ResultSet rs) {
		try {
			String va = rs.getString("VA"); if (va == null) va=""; 
			state = rs.getString("state"); if (state == null) state=""; 
			sn1 = rs.getString("serial_nr"); if (sn1 == null) sn1="";
			locind = rs.getString("location"); if (locind == null) locind="";
			issued = rs.getString("issued"); if (issued == null) issued=""; 
			optgrp = rs.getString("opt_group"); if (optgrp == null) optgrp="";  
			String ash = rs.getString("ashtam"); if (ash == null) ash=""; 
			sn2 = rs.getString("sn_serial_nr"); if (sn2 == null) sn2="";
			String A = rs.getString("A"); if (A == null) A="";
			String B = rs.getString("B"); if (B == null) B="";
			String C = rs.getString("C"); if (C == null) C=""; 
			String D = rs.getString("D"); if (D == null) D="";
			String E = rs.getString("E"); if (E == null) E=""; 
			String F = rs.getString("F"); if (F == null) F=""; 
			String G = rs.getString("G"); if (G == null) G=""; 
			String H = rs.getString("H"); if (H == null) H="";
			String I = rs.getString("I"); if (I == null) I=""; 
			String J = rs.getString("J"); if (J == null) J="";
			String K = rs.getString("K"); if (K == null) K="";
			if (F.contains("`")) F = F.replace("`", "'");
			if (G.contains("`")) G = G.replace("`", "'");
			if (H.contains("`")) H = H.replace("`", "'");
			if (I.contains("`")) I = I.replace("`", "'");
			if (J.contains("`")) J = J.replace("`", "'");
			if (K.contains("`")) K = K.replace("`", "'");
			formASHTAM(va,state,sn1,locind,issued,optgrp,ash,sn2,A,B,C,D,E,F,G,H,I,J,K);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void setBIRDTAM(ResultSet rs) {
		try {
			birdnum = rs.getString("birdtam_nr"); if (birdnum == null) birdnum="";
			efftime = rs.getString("effective_time"); if (efftime == null) efftime="";
			exptime = rs.getString("expiration_time"); if (exptime == null) exptime="";
			intlev = rs.getString("intensity_level"); if (intlev == null) intlev="";
			String affected_area = rs.getString("affected_area"); if (affected_area == null) affected_area="";
			String low_altitude = rs.getString("low_altitude"); if (low_altitude == null) low_altitude="";
			String high_altitude = rs.getString("high_altitude"); if (high_altitude == null) high_altitude="";
	
			if (intlev.contains("`")) intlev = intlev.replace("`", "'");
			if (affected_area.contains("`")) affected_area = affected_area.replace("`", "'");
			if (low_altitude.contains("`")) low_altitude = low_altitude.replace("`", "'");
			if (high_altitude.contains("`")) high_altitude = high_altitude.replace("`", "'");
			formBIRDTAM(birdnum,efftime,exptime,intlev,affected_area,low_altitude,high_altitude);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public void setRQN(ResultSet rs) {
		try {	
			reqid = rs.getString("request_id"); if (reqid == null) reqid="";
			locind = rs.getString("nof_or_type"); if (locind == null) locind="";
			reqmsg = rs.getString("request_message"); if (reqmsg == null) reqmsg="";
			if (reqmsg.contains("`")) reqmsg = reqmsg.replace("`", "'");
			formRQN(reqid,locind,reqmsg);
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	// beritanya dirangkai sesuai format
	public void formNOTAM(String ntm_id_serie, String ntm_id_sequence, String ntm_id_years, String identifiers, String part, String part_nr, 
			String ref_ntm_id_serie, String ref_ntm_id_sequence, String ref_ntm_id_years, String ref_part, String ref_part_nr, String fir, 
			String code23, String code45, String trafic, String purpose, String scope, String lower, String upper, String coordinates, 
			String radius, String A, String B, String C, String est_perm, String D, String E, String FNot, String G) {
		
		//SERIES,SEQUENCE,YEAR,PART,PART_NR
		 String SSYPP="";
		 if (!ntm_id_serie.equals("")) {
			if (!ntm_id_sequence.equals("")) {
				if (!ntm_id_years.equals("")) {
					if (!part.equals("")) {
						if (!part_nr.equals("")) { SSYPP=ntm_id_serie+ntm_id_sequence+"/"+ntm_id_years+part+part_nr; }
						else { SSYPP=ntm_id_serie+ntm_id_sequence+"/"+ntm_id_years+part; }
					} else {
						if (!part_nr.equals("")) { SSYPP=ntm_id_serie+ntm_id_sequence+"/"+ntm_id_years+part_nr; }
						else { SSYPP=ntm_id_serie+ntm_id_sequence+"/"+ntm_id_years; }
					}	
				} else {
					if (!part.equals("")) {
						if (!part_nr.equals("")) { SSYPP=ntm_id_serie+ntm_id_sequence+"/"+part+part_nr; }
						else { SSYPP=ntm_id_serie+ntm_id_sequence+"/"+part; }
					} else {
						if (!part_nr.equals("")) { SSYPP=ntm_id_serie+ntm_id_sequence+"/"+part_nr; }
						else { SSYPP=ntm_id_serie+ntm_id_sequence; }
					}
				}
			} else {
				if (!ntm_id_years.equals("")) {
					if (!part.equals("")) {
						if (!part_nr.equals("")) { SSYPP=ntm_id_serie+"/"+ntm_id_years+part+part_nr; }
						else { SSYPP=ntm_id_serie+"/"+ntm_id_years+part; }
					} else {
						if (!part_nr.equals("")) { SSYPP=ntm_id_serie+"/"+ntm_id_years+part_nr; }
						else { SSYPP=ntm_id_serie+"/"+ntm_id_years; }
					}	
				} else {
					if (!part.equals("")) {
						if (!part_nr.equals("")) { SSYPP=ntm_id_serie+"/"+part+part_nr; }
						else { SSYPP=ntm_id_serie+"/"+part; }
					} else {
						if (!part_nr.equals("")) { SSYPP=ntm_id_serie+"/"+part_nr; }
						else { SSYPP=ntm_id_serie; }
					}
				}
			}
		 } else {
			 if (!ntm_id_sequence.equals("")) {
				 if (!ntm_id_years.equals("")) {
					 if (!part.equals("")) {
						 if (!part_nr.equals("")) { SSYPP=ntm_id_serie+ntm_id_sequence+"/"+ntm_id_years+part+part_nr; }
						 else { SSYPP=ntm_id_serie+ntm_id_sequence+"/"+ntm_id_years+part; }
					 } else {
						 if (!part_nr.equals("")) { SSYPP=ntm_id_serie+ntm_id_sequence+"/"+ntm_id_years+part_nr; }
						 else { SSYPP=ntm_id_serie+ntm_id_sequence+"/"+ntm_id_years; }
					 }	
				 } else {
					 if (!part.equals("")) {
						 if (!part_nr.equals("")) { SSYPP=ntm_id_serie+ntm_id_sequence+"/"+part+part_nr; }
						 else { SSYPP=ntm_id_serie+ntm_id_sequence+"/"+part; }
					 } else {
						 if (!part_nr.equals("")) { SSYPP=ntm_id_serie+ntm_id_sequence+"/"+part_nr; }
						 else { SSYPP=ntm_id_serie+ntm_id_sequence; }
					 }
				 }
			 } else {
				 if (!ntm_id_years.equals("")) {
					 if (!part.equals("")) {
						 if (!part_nr.equals("")) { SSYPP=ntm_id_serie+"/"+ntm_id_years+part+part_nr; }
						 else { SSYPP=ntm_id_serie+"/"+ntm_id_years+part; }
					 } else {
						 if (!part_nr.equals("")) { SSYPP=ntm_id_serie+"/"+ntm_id_years+part_nr; }
						 else { SSYPP=ntm_id_serie+"/"+ntm_id_years; }
					 }	
				 } else {
					 if (!part.equals("")) {
						 if (!part_nr.equals("")) { SSYPP=ntm_id_serie+"/"+part+part_nr; }
						 else { SSYPP=ntm_id_serie+"/"+part; }
					 } else {
						 if (!part_nr.equals("")) { SSYPP=ntm_id_serie+"/"+part_nr; }
						 else { SSYPP=ntm_id_serie; }
					 }
				 }
			 }
		 }
		
		//REFERENCE SERIES,SEQUENCE,YEAR,PART,PART_NR
		String RSSYPP="";
		if (!ref_ntm_id_serie.equals("")) {
			if (!ref_ntm_id_sequence.equals("")) {
				if (!ref_ntm_id_years.equals("")) {
					if (!ref_part.equals("")) {
						if (!ref_part_nr.equals("")) { RSSYPP=ref_ntm_id_serie+ref_ntm_id_sequence+"/"+ref_ntm_id_years+ref_part+ref_part_nr; }
						else { RSSYPP=ref_ntm_id_serie+ref_ntm_id_sequence+"/"+ref_ntm_id_years+ref_part; }
					} else {
						if (!ref_part_nr.equals("")) { RSSYPP=ref_ntm_id_serie+ref_ntm_id_sequence+"/"+ref_ntm_id_years+ref_part_nr; }
						else { RSSYPP=ref_ntm_id_serie+ref_ntm_id_sequence+"/"+ref_ntm_id_years; }
					}	
				} else {
					if (!ref_part.equals("")) {
						if (!ref_part_nr.equals("")) { RSSYPP=ref_ntm_id_serie+ref_ntm_id_sequence+"/"+ref_part+ref_part_nr; }
						else { RSSYPP=ref_ntm_id_serie+ref_ntm_id_sequence+"/"+ref_part; }
					} else {
						if (!ref_part_nr.equals("")) { RSSYPP=ref_ntm_id_serie+ref_ntm_id_sequence+"/"+ref_part_nr; }
						else { RSSYPP=ref_ntm_id_serie+ref_ntm_id_sequence; }
					}
				}
			} else {
				if (!ref_ntm_id_years.equals("")) {
					if (!ref_part.equals("")) {
						if (!ref_part_nr.equals("")) { RSSYPP=ref_ntm_id_serie+"/"+ref_ntm_id_years+ref_part+ref_part_nr; }
						else { RSSYPP=ref_ntm_id_serie+"/"+ref_ntm_id_years+ref_part; }
					} else {
						if (!ref_part_nr.equals("")) { RSSYPP=ref_ntm_id_serie+"/"+ref_ntm_id_years+ref_part_nr; }
						else { RSSYPP=ref_ntm_id_serie+"/"+ref_ntm_id_years; }
					}	
				} else {
					if (!ref_part.equals("")) {
						if (!ref_part_nr.equals("")) { RSSYPP=ref_ntm_id_serie+"/"+ref_part+ref_part_nr; }
						else { RSSYPP=ref_ntm_id_serie+"/"+ref_part; }
					} else {
						if (!ref_part_nr.equals("")) { RSSYPP=ref_ntm_id_serie+"/"+ref_part_nr; }
						else { RSSYPP=ref_ntm_id_serie; }
					}
				}
			}
		 } else {
			 if (!ref_ntm_id_sequence.equals("")) {
				 if (!ref_ntm_id_years.equals("")) {
					 if (!ref_part.equals("")) {
						 if (!ref_part_nr.equals("")) { RSSYPP=ref_ntm_id_serie+ref_ntm_id_sequence+"/"+ref_ntm_id_years+ref_part+ref_part_nr; }
						 else { RSSYPP=ref_ntm_id_serie+ref_ntm_id_sequence+"/"+ref_ntm_id_years+ref_part; }
					 } else {
						 if (!ref_part_nr.equals("")) { RSSYPP=ref_ntm_id_serie+ref_ntm_id_sequence+"/"+ref_ntm_id_years+ref_part_nr; }
						 else { RSSYPP=ref_ntm_id_serie+ref_ntm_id_sequence+"/"+ref_ntm_id_years; }
					 }	
				 } else {
					 if (!ref_part.equals("")) {
						 if (!ref_part_nr.equals("")) { RSSYPP=ref_ntm_id_serie+ref_ntm_id_sequence+"/"+ref_part+ref_part_nr; }
						 else { RSSYPP=ref_ntm_id_serie+ref_ntm_id_sequence+"/"+ref_part; }
					 } else {
						 if (!ref_part_nr.equals("")) { RSSYPP=ref_ntm_id_serie+ref_ntm_id_sequence+"/"+ref_part_nr; }
						 else { RSSYPP=ref_ntm_id_serie+ref_ntm_id_sequence; }
					 }
				 }
			 } else {
				 if (!ref_ntm_id_years.equals("")) {
					 if (!ref_part.equals("")) {
						 if (!ref_part_nr.equals("")) { RSSYPP=ref_ntm_id_serie+"/"+ref_ntm_id_years+ref_part+ref_part_nr; }
						 else { RSSYPP=ref_ntm_id_serie+"/"+ref_ntm_id_years+ref_part; }
					 } else {
						 if (!ref_part_nr.equals("")) { RSSYPP=ref_ntm_id_serie+"/"+ref_ntm_id_years+ref_part_nr; }
						 else { RSSYPP=ref_ntm_id_serie+"/"+ref_ntm_id_years; }
					 }	
				 } else {
					 if (!ref_part.equals("")) {
						 if (!ref_part_nr.equals("")) { RSSYPP=ref_ntm_id_serie+"/"+ref_part+ref_part_nr; }
						 else { RSSYPP=ref_ntm_id_serie+"/"+ref_part; }
					 } else {
						 if (!ref_part_nr.equals("")) { RSSYPP=ref_ntm_id_serie+"/"+ref_part_nr; }
						 else { RSSYPP=ref_ntm_id_serie; }
					 }
				 }
			 }
		 }
		
		//SERIES IDENTIFIERS REFERENCE
		String series="";
		if (!SSYPP.equals("")) {
			if (!identifiers.equals("")) {
				if (!RSSYPP.equals("")) { series=SSYPP+" "+identifiers+" "+RSSYPP; } 
				else { series=SSYPP+" "+identifiers; }
			} else {
				if (!RSSYPP.equals("")) { series=SSYPP+" "+RSSYPP; } 
				else { series=SSYPP; }
			}
		} else {
			if (!identifiers.equals("")) {
				if (!RSSYPP.equals("")) { series=identifiers+" "+RSSYPP; } 
				else { series=identifiers; }
			} else {
				if (!RSSYPP.equals("")) { series=RSSYPP; } 
				else { series=""; }
			}
		}
		
		//FIR,CODE23,CODE45,TRAFFIC,PURPOSE
		String FCCTP="";
		if (!fir.equals("")) {
			if (!code23.equals("")) {
				if (!code45.equals("")) {
					if (!trafic.equals("")) {
						if (!purpose.equals("")) { FCCTP=fir+"/"+code23+code45+"/"+trafic+"/"+purpose; } 
						else { FCCTP=fir+"/"+code23+code45+"/"+trafic; }
					} else {
						if (!purpose.equals("")) { FCCTP=fir+"/"+code23+code45+"/"+purpose; } 
						else { FCCTP=fir+"/"+code23+code45; }
					}
				} else {
					if (!trafic.equals("")) {
						if (!purpose.equals("")) { FCCTP=fir+"/"+code23+"/"+trafic+"/"+purpose; } 
						else { FCCTP=fir+"/"+code23+"/"+trafic; }
					} else {
						if (!purpose.equals("")) { FCCTP=fir+"/"+code23+"/"+purpose; } 
						else { FCCTP=fir+"/"+code23; }
					}
				}
			} else {
				if (!code45.equals("")) {
					if (!trafic.equals("")) {
						if (!purpose.equals("")) { FCCTP=fir+"/"+code45+"/"+trafic+"/"+purpose; } 
						else { FCCTP=fir+"/"+code45+"/"+trafic; }
					} else {
						if (!purpose.equals("")) { FCCTP=fir+"/"+code45+"/"+purpose; } 
						else { FCCTP=fir+"/"+code45; }
					}
				} else {
					if (!trafic.equals("")) {
						if (!purpose.equals("")) { FCCTP=fir+"/"+trafic+"/"+purpose; } 
						else { FCCTP=fir+"/"+trafic; }
					} else {
						if (!purpose.equals("")) { FCCTP=fir+"/"+purpose; } 
						else { FCCTP=fir; }
					}
				}
			}
		} else {
			if (!code23.equals("")) {
				if (!code45.equals("")) {
					if (!trafic.equals("")) {
						if (!purpose.equals("")) { FCCTP=code23+code45+"/"+trafic+"/"+purpose; } 
						else { FCCTP=code23+code45+"/"+trafic; }
					} else {
						if (!purpose.equals("")) { FCCTP=code23+code45+"/"+purpose; } 
						else { FCCTP=code23+code45; }
					}
				} else {
					if (!trafic.equals("")) {
						if (!purpose.equals("")) { FCCTP=code23+"/"+trafic+"/"+purpose; } 
						else { FCCTP=code23+"/"+trafic; }
					} else {
						if (!purpose.equals("")) { FCCTP=code23+"/"+purpose; } 
						else { FCCTP=code23; }
					}
				}
			} else {
				if (!code45.equals("")) {
					if (!trafic.equals("")) {
						if (!purpose.equals("")) { FCCTP=code45+"/"+trafic+"/"+purpose; } 
						else { FCCTP=code45+"/"+trafic; }
					} else {
						if (!purpose.equals("")) { FCCTP=code45+"/"+purpose; } 
						else { FCCTP=code45; }
					}
				} else {
					if (!trafic.equals("")) {
						if (!purpose.equals("")) { FCCTP=trafic+"/"+purpose; } 
						else { FCCTP=trafic; }
					} else {
						if (!purpose.equals("")) { FCCTP=purpose; } 
						else { FCCTP=""; }
					}
				}
			}
		}
		
		//SCOPE,LOWER,UPPER,CO-ORDINATES,RADIUS
		String SLUCR="";
		if (!scope.equals("")) {
			if (!lower.equals("")) {
				if (!upper.equals("")) {
					if (!coordinates.equals("")) {
						if (!radius.equals("")) { SLUCR=scope+"/"+lower+"/"+upper+"/"+coordinates+radius; } 
						else { SLUCR=scope+"/"+lower+"/"+upper+"/"+coordinates; }
					} else {
						if (!radius.equals("")) { SLUCR=scope+"/"+lower+"/"+upper+"/"+radius; } 
						else { SLUCR=scope+"/"+lower+"/"+upper; }
					}
				} else {
					if (!coordinates.equals("")) {
						if (!radius.equals("")) { SLUCR=scope+"/"+lower+"/"+coordinates+radius; } 
						else { SLUCR=scope+"/"+lower+"/"+coordinates; }
					} else {
						if (!radius.equals("")) { SLUCR=scope+"/"+lower+"/"+radius; } 
						else { SLUCR=scope+"/"+lower; }
					}
				}
			} else {
				if (!upper.equals("")) {
					if (!coordinates.equals("")) {
						if (!radius.equals("")) { SLUCR=scope+"/"+upper+"/"+coordinates+radius; } 
						else { SLUCR=scope+"/"+upper+"/"+coordinates; }
					} else {
						if (!radius.equals("")) { SLUCR=scope+"/"+upper+"/"+radius; } 
						else { SLUCR=scope+"/"+upper; }
					}
				} else {
					if (!coordinates.equals("")) {
						if (!radius.equals("")) { SLUCR=scope+"/"+coordinates+radius; } 
						else { SLUCR=scope+"/"+coordinates; }
					} else {
						if (!radius.equals("")) { SLUCR=scope+"/"+radius; } 
						else { SLUCR=scope; }
					}
				}
			}
		} else {
			if (!lower.equals("")) {
				if (!upper.equals("")) {
					if (!coordinates.equals("")) {
						if (!radius.equals("")) { SLUCR=lower+"/"+upper+"/"+coordinates+radius; } 
						else { SLUCR=lower+"/"+upper+"/"+coordinates; }
					} else {
						if (!radius.equals("")) { SLUCR=lower+"/"+upper+"/"+radius; } 
						else { SLUCR=lower+"/"+upper; }
					}
				} else {
					if (!coordinates.equals("")) {
						if (!radius.equals("")) { SLUCR=lower+"/"+coordinates+radius; } 
						else { SLUCR=lower+"/"+coordinates; }
					} else {
						if (!radius.equals("")) { SLUCR=lower+"/"+radius; } 
						else { SLUCR=lower; }
					}
				}
			} else {
				if (!upper.equals("")) {
					if (!coordinates.equals("")) {
						if (!radius.equals("")) { SLUCR=upper+"/"+coordinates+radius; } 
						else { SLUCR=upper+"/"+coordinates; }
					} else {
						if (!radius.equals("")) { SLUCR=upper+"/"+radius; } 
						else { SLUCR=upper; }
					}
				} else {
					if (!coordinates.equals("")) {
						if (!radius.equals("")) { SLUCR=coordinates+radius; } 
						else { SLUCR=coordinates; }
					} else {
						if (!radius.equals("")) { SLUCR=radius; } 
						else { SLUCR=""; }
					}
				}
			}
		}
		
		//QUALIFIERS
		String qualifiers="";
		if (!FCCTP.equals("")) {
			if (!SLUCR.equals("")) { qualifiers=FCCTP+"/"+SLUCR; } else { qualifiers=FCCTP; }
		} else {
			if (!SLUCR.equals("")) { qualifiers="/"+SLUCR; } else { qualifiers=""; }	
		}
		if (!qualifiers.equals("")) qualifiers=qualifiers.replaceAll(qualifiers, "Q)"+qualifiers);

		//A)LOCATION
		if (!A.equals("")) { A="A)"+A; } else { A=""; }
		//B)FROM
		if (!B.equals("")) { B="B)"+B; } else { B=""; }
		//C)TO,EST-PERM
		if (!C.equals("")) {
			if (!est_perm.equals("")) { C="C)"+C+est_perm; } else { C="C)"+C; }
		} else { 
			if (!est_perm.equals("")) { C="C)"+est_perm; } else { C=""; }
		}
		//D)TIME SCHEDULE
		if (D.endsWith("\n")) {
			 int index = D.lastIndexOf("\n");
			 String F = D.substring(index, D.length());
			 String k = D.substring(0,index-1);
			 //F = F.replaceAll("\n", "");
			 D = k+F; 
		}
		if (!D.equals("")) { D="D)"+D; } else { D=""; }
		//E)TEXT OF NOTAM
		if (E.endsWith("\n")) {
			 int index = E.lastIndexOf("\n");
			 String F = E.substring(index, E.length());
			 String k = E.substring(0,index-1);
			 //F = F.replaceAll("\n", "");
			 E = k+F; 
		}
		if (!E.equals("")) { E="E)"+E; } else { E=""; }
		//F)LOWER LIMIT
		if (!FNot.equals("")) { FNot="F)"+FNot; } else { FNot=""; }
		//G)UPPER LIMIT
		if (!G.equals("")) { G="G)"+G; } else { G=""; }
		
		String ABC="";
		if (!A.equals("")) {
			if (!B.equals("")) {
				if (!C.equals("")) { ABC=A+" "+B+" "+C; } else { ABC=A+" "+B; }	
			} else {
				if (!C.equals("")) { ABC=A+" "+C; } else { ABC=A; }
			}	
		} else {
			if (!B.equals("")) {
				if (!C.equals("")) { ABC=B+" "+C; } else { ABC=B; }	
			} else {
				if (!C.equals("")) { ABC=C; } else { ABC=""; }
			}
		}
		
		String DE="";
		if (!D.equals("")) {
			if (!E.equals("")) { DE=D+"\n"+E; } else { DE=D; }	
		} else {
			if (!E.equals("")) { DE=E; } else { DE=""; }
		}
		
		String FG="";
		if (!FNot.equals("")) {
			if (!G.equals("")) { FG=FNot+" "+G; } else { FG=FNot; }	
		} else {
			if (!G.equals("")) { FG=G; } else { FG=""; }
		}
		//***************************** NOTAM-RQNTM-MULTIPART *****************************
		String SQ="";
		if (!series.equals("")) {
			if (!qualifiers.equals("")) { SQ=series+"\n"+qualifiers; } else { SQ=series; }	
		} else {
			if (!qualifiers.equals("")) { SQ=qualifiers; } else { SQ=""; }
		}
		
		String ABCDEFG="";
		if (!ABC.equals("")) {
			if (!DE.equals("")) {
				if (!FG.equals("")) { ABCDEFG=ABC+"\n"+DE+"\n"+FG; } else { ABCDEFG=ABC+"\n"+DE; }
			} else {
				if (!FG.equals("")) { ABCDEFG=ABC+"\n"+FG; } else { ABCDEFG=ABC; }
			}
		} else {
			if (!DE.equals("")) {
				if (!FG.equals("")) { ABCDEFG=DE+"\n"+FG; } else { ABCDEFG=DE; }
			} else {
				if (!FG.equals("")) { ABCDEFG=FG; } else { ABCDEFG=""; }
			}
		}
		
		String multipart="";
		if (!SQ.equals("")) {
			if (!ABCDEFG.equals("")) { multipart=SQ+"\n"+ABCDEFG; } else { multipart=SQ; }	
		} else {
			if (!ABCDEFG.equals("")) { multipart=ABCDEFG; } else { multipart=""; }
		}
		pnotam = ViewATS.header+"("+multipart+")\n";
	}
	
	public void formSNOWTAM(String A, String B, String C1, String C2, String C3, String D1, String D2, String D3, String E1, String E2, 
			String E3, String F1, String F2, String F3, String G1, String G2, String G3, String H1, String H2, String H3, String J1, String J2,
			String J3, String K1, String K2, String K3, String L1, String L2, String L3, String M1, String M2, String M3, String N1, String N2, 
			String N3, String P1, String P2, String P3, String R, String S, String T, String code_sw, String state, String serial_number, 
			String location_indicator, String oservation, String opt_group, String snow, String sn_serial_nr) {
		String CSS = "";
		if (serial_number.equals("0")) {
			serial_number = "";
		}
		CSS = code_sw + state + serial_number;

		String LOO = "";
		if (!location_indicator.equals("")) {
			if (!oservation.equals("")) {
				if (!opt_group.equals("")) {
					LOO = location_indicator + " " + oservation + " "
							+ opt_group;
				} else {
					LOO = location_indicator + " " + oservation;
				}
			} else {
				if (!opt_group.equals("")) {
					LOO = location_indicator + " " + opt_group;
				} else {
					LOO = location_indicator;
				}
			}
		} else {
			if (!oservation.equals("")) {
				if (!opt_group.equals("")) {
					LOO = oservation + " " + opt_group;
				} else {
					LOO = oservation;
				}
			} else {
				if (!opt_group.equals("")) {
					LOO = opt_group;
				} else {
					LOO = "";
				}
			}
		}

		String CSSLOO = "";
		if (!CSS.equals("")) {
			if (!LOO.equals("")) {
				CSSLOO = CSS + " " + LOO;
			} else {
				CSSLOO = CSS;
			}
		} else {
			if (!LOO.equals("")) {
				CSSLOO = LOO;
			} else {
				CSSLOO = "";
			}
		}

		String SS = "";
		if (!snow.equals("")) {
			if (!sn_serial_nr.equals("")) {
				SS = "(" + snow + " " + sn_serial_nr;
			} else {
				SS = "(" + snow;
			}
		}

		if (!A.equals("")) A = "A)" + A; else A = "";
		if (!B.equals("")) B = "B)" + B; else B = "";
		String AB = "";
		if (!A.equals("")) {
			if (!B.equals("")) { AB = A + " " + B; } else { AB = A; }
		} else {
			if (!B.equals("")) { AB = B; } else { AB = ""; }
		}
		
		if (!C1.equals("")) C1 = "C)" + C1; else C1 = "";
		if (!C2.equals("")) C2 = "C)" + C2; else C2 = "";
		if (!C3.equals("")) C3 = "C)" + C3; else C3 = "";
		String C="";
		if (!C1.equals("")) {
			if (!C2.equals("")) {
				if (!C3.equals("")) { C=C1+" "+C2+" "+C3; } else { C=C1+" "+C2; }
			} else {
				if (!C3.equals("")) { C=C1+" "+C3; } else { C=C1; }
			}
		} else {
			if (!C2.equals("")) {
				if (!C3.equals("")) { C=C2+" "+C3; } else { C=C2; }
			} else {
				if (!C3.equals("")) { C=C3; } else { C=""; }
			}
		}
		
		if (!D1.equals("")) D1 = "D)" + D1; else D1 = "";
		if (!D2.equals("")) D2 = "D)" + D2; else D2 = "";
		if (!D3.equals("")) D3 = "D)" + D3; else D3 = "";
		String D="";
		if (!D1.equals("")) {
			if (!D2.equals("")) {
				if (!D3.equals("")) { D=D1+" "+D2+" "+D3; } else { D=D1+" "+D2; }
			} else {
				if (!D3.equals("")) { D=D1+" "+D3; } else { D=D1; }
			}
		} else {
			if (!D2.equals("")) {
				if (!D3.equals("")) { D=D2+" "+D3; } else { D=D2; }
			} else {
				if (!D3.equals("")) { D=D3; } else { D=""; }
			}
		}
		
		if (!E1.equals("")) E1 = "E)" + E1; else E1 = "";
		if (!E2.equals("")) E2 = "E)" + E2; else E2 = "";
		if (!E3.equals("")) E3 = "E)" + E3; else E3 = "";
		String E="";
		if (!E1.equals("")) {
			if (!E2.equals("")) {
				if (!E3.equals("")) { E=E1+" "+E2+" "+E3; } else { E=E1+" "+E2; }
			} else {
				if (!E3.equals("")) { E=E1+" "+E3; } else { E=E1; }
			}
		} else {
			if (!E2.equals("")) {
				if (!E3.equals("")) { E=E2+" "+E3; } else { E=E2; }
			} else {
				if (!E3.equals("")) { E=E3; } else { E=""; }
			}
		}
		
		if (!F1.equals("")) F1 = "F)" + F1; else F1 = "";
		if (!F2.equals("")) F2 = "F)" + F2; else F2 = "";
		if (!F3.equals("")) F3 = "F)" + F3; else F3 = "";
		String F="";
		if (!F1.equals("")) {
			if (!F2.equals("")) {
				if (!F3.equals("")) { F=F1+" "+F2+" "+F3; } else { F=F1+" "+F2; }
			} else {
				if (!F3.equals("")) { F=F1+" "+F3; } else { F=F1; }
			}
		} else {
			if (!F2.equals("")) {
				if (!F3.equals("")) { F=F2+" "+F3; } else { F=F2; }
			} else {
				if (!F3.equals("")) { F=F3; } else { F=""; }
			}
		}
		
		if (!G1.equals("")) G1 = "G)" + G1; else G1 = "";
		if (!G2.equals("")) G2 = "G)" + G2; else G2 = "";
		if (!G3.equals("")) G3 = "G)" + G3; else G3 = "";
		String G="";
		if (!G1.equals("")) {
			if (!G2.equals("")) {
				if (!G3.equals("")) { G=G1+" "+G2+" "+G3; } else { G=G1+" "+G2; }
			} else {
				if (!G3.equals("")) { G=G1+" "+G3; } else { G=G1; }
			}
		} else {
			if (!G2.equals("")) {
				if (!G3.equals("")) { G=G2+" "+G3; } else { G=G2; }
			} else {
				if (!G3.equals("")) { G=G3; } else { G=""; }
			}
		}
		
		if (!H1.equals("")) H1 = "H)" + H1; else H1 = "";
		if (!H2.equals("")) H2 = "H)" + H2; else H2 = "";
		if (!H3.equals("")) H3 = "H)" + H3; else H3 = "";
		String H="";
		if (!H1.equals("")) {
			if (!H2.equals("")) {
				if (!H3.equals("")) { H=H1+" "+H2+" "+H3; } else { H=H1+" "+H2; }
			} else {
				if (!H3.equals("")) { H=H1+" "+H3; } else { H=H1; }
			}
		} else {
			if (!H2.equals("")) {
				if (!H3.equals("")) { H=H2+" "+H3; } else { H=H2; }
			} else {
				if (!H3.equals("")) { H=H3; } else { H=""; }
			}
		}
		
		if (!J1.equals("")) J1 = "J)" + J1; else J1 = "";
		if (!J2.equals("")) J2 = "J)" + J2; else J2 = "";
		if (!J3.equals("")) J3 = "J)" + J3; else J3 = "";
		String J="";
		if (!J1.equals("")) {
			if (!J2.equals("")) {
				if (!J3.equals("")) { J=J1+" "+J2+" "+J3; } else { J=J1+" "+J2; }
			} else {
				if (!J3.equals("")) { J=J1+" "+J3; } else { J=J1; }
			}
		} else {
			if (!J2.equals("")) {
				if (!J3.equals("")) { J=J2+" "+J3; } else { J=J2; }
			} else {
				if (!J3.equals("")) { J=J3; } else { J=""; }
			}
		}
		
		if (!K1.equals("")) K1 = "K)" + K1; else K1 = "";
		if (!K2.equals("")) K2 = "K)" + K2; else K2 = "";
		if (!K3.equals("")) K3 = "K)" + K3; else K3 = "";
		String K="";
		if (!K1.equals("")) {
			if (!K2.equals("")) {
				if (!K3.equals("")) { K=K1+" "+K2+" "+K3; } else { K=K1+" "+K2; }
			} else {
				if (!K3.equals("")) { K=K1+" "+K3; } else { K=K1; }
			}
		} else {
			if (!K2.equals("")) {
				if (!K3.equals("")) { K=K2+" "+K3; } else { K=K2; }
			} else {
				if (!K3.equals("")) { K=K3; } else { K=""; }
			}
		}
		
		if (!L1.equals("")) L1 = "L)" + L1; else L1 = "";
		if (!L2.equals("")) L2 = "L)" + L2; else L2 = "";
		if (!L3.equals("")) L3 = "L)" + L3; else L3 = "";
		String L="";
		if (!L1.equals("")) {
			if (!L2.equals("")) {
				if (!L3.equals("")) { L=L1+" "+L2+" "+L3; } else { L=L1+" "+L2; }
			} else {
				if (!L3.equals("")) { L=L1+" "+L3; } else { L=L1; }
			}
		} else {
			if (!L2.equals("")) {
				if (!L3.equals("")) { L=L2+" "+L3; } else { L=L2; }
			} else {
				if (!L3.equals("")) { L=L3; } else { L=""; }
			}
		}
		
		if (!M1.equals("")) M1 = "M)" + M1; else M1 = "";
		if (!M2.equals("")) M2 = "M)" + M2; else M2 = "";
		if (!M3.equals("")) M3 = "M)" + M3; else M3 = "";
		String M="";
		if (!M1.equals("")) {
			if (!M2.equals("")) {
				if (!M3.equals("")) { M=M1+" "+M2+" "+M3; } else { M=M1+" "+M2; }
			} else {
				if (!M3.equals("")) { M=M1+" "+M3; } else { M=M1; }
			}
		} else {
			if (!M2.equals("")) {
				if (!M3.equals("")) { M=M2+" "+M3; } else { M=M2; }
			} else {
				if (!M3.equals("")) { M=M3; } else { M=""; }
			}
		}
		
		if (!N1.equals("")) N1 = "N)" + N1; else N1 = "";
		if (!N2.equals("")) N2 = "N)" + N2; else N2 = "";
		if (!N3.equals("")) N3 = "N)" + N3; else N3 = "";
		String N="";
		if (!N1.equals("")) {
			if (!N2.equals("")) {
				if (!N3.equals("")) { N=N1+" "+N2+" "+N3; } else { N=N1+" "+N2; }
			} else {
				if (!N3.equals("")) { N=N1+" "+N3; } else { N=N1; }
			}
		} else {
			if (!N2.equals("")) {
				if (!N3.equals("")) { N=N2+" "+N3; } else { N=N2; }
			} else {
				if (!N3.equals("")) { N=N3; } else { N=""; }
			}
		}
		
		if (!P1.equals("")) P1 = "P)" + P1; else P1 = "";
		if (!P2.equals("")) P2 = "P)" + P2; else P2 = "";
		if (!P3.equals("")) P3 = "P)" + P3; else P3 = "";
		String P="";
		if (!P1.equals("")) {
			if (!P2.equals("")) {
				if (!P3.equals("")) { P=P1+" "+P2+" "+P3; } else { P=P1+" "+P2; }
			} else {
				if (!P3.equals("")) { P=P1+" "+P3; } else { P=P1; }
			}
		} else {
			if (!P2.equals("")) {
				if (!P3.equals("")) { P=P2+" "+P3; } else { P=P2; }
			} else {
				if (!P3.equals("")) { P=P3; } else { P=""; }
			}
		}
		
		if (!R.equals("")) R = "R)"+R+"\n"; else R = "";
		if (!S.equals("")) S = "S)"+S+"\n"; else S = "";
		if (!T.equals("")) T = "T)"+T; else T = "";
		if (T.endsWith(")")) {
			int index = T.lastIndexOf(")");
			String Ft = T.substring(index, T.length());
			String k = T.substring(0, index);
			Ft = Ft.replaceAll(")", "");
			T = k + Ft;
		}

		if (!CSSLOO.equals("")) {
			if (!SS.equals("")) { snowtam = CSSLOO+"\n"+SS+"\n"; } 
			else { snowtam = CSSLOO+"\n"; }	
		} else {
			if (!SS.equals("")) { snowtam = SS+"\n"; } 
			else { snowtam = ""; }	
		}
		
		if (!AB.equals("")) AB+="\n"; else AB="";
		if (!C.equals("")) C+="\n"; else C="";
		if (!D.equals("")) D+="\n"; else D="";
		if (!E.equals("")) E+="\n"; else E="";
		if (!F.equals("")) F+="\n"; else F="";
		if (!G.equals("")) G+="\n"; else G="";
		if (!H.equals("")) H+="\n"; else H="";
		if (!J.equals("")) J+="\n"; else J="";
		if (!K.equals("")) K+="\n"; else K="";
		if (!L.equals("")) L+="\n"; else L="";
		if (!M.equals("")) M+="\n"; else M="";
		if (!N.equals("")) N+="\n"; else N="";
		if (!P.equals("")) P+="\n"; else P="";
		
		snowtam+= AB+C+D+E+F+G+H+J+K+L+M+N+P+R+S+T;
		psnowtam=ViewATS.header+snowtam+")\n";
	}
	
	public void formASHTAM(String va,String state,String serial_nr,String location,String issued,String opt_group,String ash,
			String sn_serial_nr,String A,String B,String C,String D,String E,String F,String G,String H,String I,String J,String K) {
		 String VSSL="";
		 if (!va.equals("")) {
			 if (!state.equals("")) {
				 if (!serial_nr.equals("")) {
					 if (!location.equals("")) {
						 if (!issued.equals("")) {
							 if (!opt_group.equals("")) { VSSL=va+state+""+serial_nr+" "+location+" "+issued+" "+opt_group; }		 
							 else { VSSL=va+state+""+serial_nr+" "+location+" "+issued; }
						 } else {
							 if (!opt_group.equals("")) { VSSL=va+state+""+serial_nr+" "+location+" "+opt_group; }		 
							 else { VSSL=va+state+""+serial_nr+" "+location; }							 
						 }
					 } else {
						 if (!issued.equals("")) {
							 if (!opt_group.equals("")) { VSSL=va+state+""+serial_nr+" "+issued+" "+opt_group; }		 
							 else { VSSL=va+state+""+serial_nr+" "+issued; }
						 } else {
							 if (!opt_group.equals("")) { VSSL=va+state+""+serial_nr+" "+opt_group; }		 
							 else { VSSL=va+state+""+serial_nr; }							 
						 }								 
					 }
				 } else {
					 if (!location.equals("")) {
						 if (!issued.equals("")) {
							 if (!opt_group.equals("")) { VSSL=va+state+" "+location+" "+issued+" "+opt_group; }		 
							 else { VSSL=va+state+" "+location+" "+issued; }
						 } else {
							 if (!opt_group.equals("")) { VSSL=va+state+" "+location+" "+opt_group; }		 
							 else { VSSL=va+state+" "+location; }							 
						 }
					 } else {
						 if (!issued.equals("")) {
							 if (!opt_group.equals("")) { VSSL=va+state+" "+issued+" "+opt_group; }		 
							 else { VSSL=va+state+" "+issued; }
						 } else {
							 if (!opt_group.equals("")) { VSSL=va+state+" "+opt_group; }		 
							 else { VSSL=va+state; }							 
						 }								 
					 }
				 }
			 } else {
				 if (!serial_nr.equals("")) {
					 if (!location.equals("")) {
						 if (!issued.equals("")) {
							 if (!opt_group.equals("")) { VSSL=va+""+serial_nr+" "+location+" "+issued+" "+opt_group; }		 
							 else { VSSL=va+""+serial_nr+" "+location+" "+issued; }
						 } else {
							 if (!opt_group.equals("")) { VSSL=va+""+serial_nr+" "+location+" "+opt_group; }		 
							 else { VSSL=va+""+serial_nr+" "+location; }							 
						 }
					 } else {
						 if (!issued.equals("")) {
							 if (!opt_group.equals("")) { VSSL=va+""+serial_nr+" "+issued+" "+opt_group; }		 
							 else { VSSL=va+""+serial_nr+" "+issued; }
						 } else {
							 if (!opt_group.equals("")) { VSSL=va+""+serial_nr+" "+opt_group; }		 
							 else { VSSL=va+""+serial_nr; }							 
						 }								 
					 }
				 } else {
					 if (!location.equals("")) {
						 if (!issued.equals("")) {
							 if (!opt_group.equals("")) { VSSL=va+" "+location+" "+issued+" "+opt_group; }		 
							 else { VSSL=va+" "+location+" "+issued; }
						 } else {
							 if (!opt_group.equals("")) { VSSL=va+" "+location+" "+opt_group; }		 
							 else { VSSL=va+" "+location; }							 
						 }
					 } else {
						 if (!issued.equals("")) {
							 if (!opt_group.equals("")) { VSSL=va+" "+issued+" "+opt_group; }		 
							 else { VSSL=va+" "+issued; }
						 } else {
							 if (!opt_group.equals("")) { VSSL=va+" "+opt_group; }		 
							 else { VSSL=va; }							 
						 }								 
					 }
				 }
			 } 
		 } else {
			 if (!state.equals("")) {
				 if (!serial_nr.equals("")) {
					 if (!location.equals("")) {
						 if (!issued.equals("")) {
							 if (!opt_group.equals("")) { VSSL=state+""+serial_nr+" "+location+" "+issued+" "+opt_group; }		 
							 else { VSSL=state+""+serial_nr+" "+location+" "+issued; }
						 } else {
							 if (!opt_group.equals("")) { VSSL=state+""+serial_nr+" "+location+" "+opt_group; }		 
							 else { VSSL=state+""+serial_nr+" "+location; }							 
						 }
					 } else {
						 if (!issued.equals("")) {
							 if (!opt_group.equals("")) { VSSL=state+""+serial_nr+" "+issued+" "+opt_group; }		 
							 else { VSSL=state+""+serial_nr+" "+issued; }
						 } else {
							 if (!opt_group.equals("")) { VSSL=state+""+serial_nr+" "+opt_group; }		 
							 else { VSSL=state+""+serial_nr; }							 
						 }								 
					 }
				 } else {
					 if (!location.equals("")) {
						 if (!issued.equals("")) {
							 if (!opt_group.equals("")) { VSSL=state+" "+location+" "+issued+" "+opt_group; }		 
							 else { VSSL=state+" "+location+" "+issued; }
						 } else {
							 if (!opt_group.equals("")) { VSSL=state+" "+location+" "+opt_group; }		 
							 else { VSSL=state+" "+location; }							 
						 }
					 } else {
						 if (!issued.equals("")) {
							 if (!opt_group.equals("")) { VSSL=state+" "+issued+" "+opt_group; }		 
							 else { VSSL=state+" "+issued; }
						 } else {
							 if (!opt_group.equals("")) { VSSL=state+" "+opt_group; }		 
							 else { VSSL=state; }							 
						 }								 
					 }
				 }
			 } else {
				 if (!serial_nr.equals("")) {
					 if (!location.equals("")) {
						 if (!issued.equals("")) {
							 if (!opt_group.equals("")) { VSSL=serial_nr+" "+location+" "+issued+" "+opt_group; }		 
							 else { VSSL=serial_nr+" "+location+" "+issued; }
						 } else {
							 if (!opt_group.equals("")) { VSSL=serial_nr+" "+location+" "+opt_group; }		 
							 else { VSSL=serial_nr+" "+location; }							 
						 }
					 } else {
						 if (!issued.equals("")) {
							 if (!opt_group.equals("")) { VSSL=serial_nr+" "+issued+" "+opt_group; }		 
							 else { VSSL=serial_nr+" "+issued; }
						 } else {
							 if (!opt_group.equals("")) { VSSL=serial_nr+" "+opt_group; }		 
							 else { VSSL=serial_nr; }							 
						 }								 
					 }
				 } else {
					 if (!location.equals("")) {
						 if (!issued.equals("")) {
							 if (!opt_group.equals("")) { VSSL=location+" "+issued+" "+opt_group; }		 
							 else { VSSL=location+" "+issued; }
						 } else {
							 if (!opt_group.equals("")) { VSSL=location+" "+opt_group; }		 
							 else { VSSL=location; }							 
						 }
					 } else {
						 if (!issued.equals("")) {
							 if (!opt_group.equals("")) { VSSL=issued+" "+opt_group; }		 
							 else { VSSL=issued; }
						 } else {
							 if (!opt_group.equals("")) { VSSL=opt_group; }		 
							 else { VSSL=""; }							 
						 }								 
					 }
				 }
			 }
		 }
		 
		String AS="";
		if (!ash.equals("")) {
			if (!sn_serial_nr.equals("")) { AS=ash+" "+sn_serial_nr; } else { AS=ash; }	
		} else {
			if (!sn_serial_nr.equals("")) { AS=sn_serial_nr; } else { AS=""; }		
		}
		
		String VSSLAS="";
		if (!VSSL.equals("")) {
			if (!AS.equals("")) { VSSLAS=VSSL+"\n("+AS; } else { VSSLAS=VSSL; }	
		} else {
			if (!AS.equals("")) { VSSLAS="("+AS; } else { VSSLAS=""; }
		}
		if (!VSSLAS.equals("")) { VSSLAS+="\n"; }
		
		String ABCDE="";
		if (!A.equals("")) {
			if (!B.equals("")) {
				if (!C.equals("")) {
					if (!D.equals("")) {
						if (!E.equals("")) { ABCDE="A)"+A+"\nB)"+B+"\nC)"+C+"\nD)"+D+"\nE)"+E; }
						else { ABCDE="A)"+A+"\nB)"+B+"\nC)"+C+"\nD)"+D; }	
					} else {
						if (!E.equals("")) { ABCDE="A)"+A+"\nB)"+B+"\nC)"+C+"\nE)"+E; }
						else { ABCDE="A)"+A+"\nB)"+B+"\nC)"+C; }
					}
				} else {
					if (!D.equals("")) {
						if (!E.equals("")) { ABCDE="A)"+A+"\nB)"+B+"\nD)"+D+"\nE)"+E; }
						else { ABCDE="A)"+A+"\nB)"+B+"\nD)"+D; }	
					} else {
						if (!E.equals("")) { ABCDE="A)"+A+"\nB)"+B+"\nE)"+E; }
						else { ABCDE="A)"+A+"\nB)"+B; }
					}
				}
			} else {
				if (!C.equals("")) {
					if (!D.equals("")) {
						if (!E.equals("")) { ABCDE="A)"+A+"\nC)"+C+"\nD)"+D+"\nE)"+E; }
						else { ABCDE="A)"+A+"\nC)"+C+"\nD)"+D; }	
					} else {
						if (!E.equals("")) { ABCDE="A)"+A+"\nC)"+C+"\nE)"+E; }
						else { ABCDE="A)"+A+"\nC)"+C; }
					}
				} else {
					if (!D.equals("")) {
						if (!E.equals("")) { ABCDE="A)"+A+"\nD)"+D+"\nE)"+E; }
						else { ABCDE="A)"+A+"\nD)"+D; }	
					} else {
						if (!E.equals("")) { ABCDE="A)"+A+"\nE)"+E; }
						else { ABCDE="A)"+A; }
					}
				}
			}				
		} else {
			if (!B.equals("")) {
				if (!C.equals("")) {
					if (!D.equals("")) {
						if (!E.equals("")) { ABCDE="B)"+B+"\nC)"+C+"\nD)"+D+"\nE)"+E; }
						else { ABCDE="B)"+B+"\nC)"+C+"\nD)"+D; }	
					} else {
						if (!E.equals("")) { ABCDE="B)"+B+"\nC)"+C+"\nE)"+E; }
						else { ABCDE="B)"+B+"\nC)"+C; }
					}
				} else {
					if (!D.equals("")) {
						if (!E.equals("")) { ABCDE="B)"+B+"\nD)"+D+"\nE)"+E; }
						else { ABCDE="B)"+B+"\nD)"+D; }	
					} else {
						if (!E.equals("")) { ABCDE="B)"+B+"\nE)"+E; }
						else { ABCDE="B)"+B; }
					}
				}
			} else {
				if (!C.equals("")) {
					if (!D.equals("")) {
						if (!E.equals("")) { ABCDE="C)"+C+"\nD)"+D+"\nE)"+E; }
						else { ABCDE="C)"+C+"\nD)"+D; }	
					} else {
						if (!E.equals("")) { ABCDE="C)"+C+"\nE)"+E; }
						else { ABCDE="C)"+C; }
					}
				} else {
					if (!D.equals("")) {
						if (!E.equals("")) { ABCDE="D)"+D+"\nE)"+E; }
						else { ABCDE="D)"+D; }	
					} else {
						if (!E.equals("")) { ABCDE="E)"+E; }
						else { ABCDE=""; }
					}
				}
			}
		}
		
		String FGHIJK="";
		if (!F.equals("")){
			if (!G.equals("")){
				if (!H.equals("")){
					if (!I.equals("")){
						if (!J.equals("")){
							if (!K.equals("")){ FGHIJK="F)"+F+"\nG)"+G+"\nH)"+H+"\nI)"+I+"\nJ)"+J+"\nK)"+K; }
							else { FGHIJK="F)"+F+"\nG)"+G+"\nH)"+H+"\nI)"+I+"\nJ)"+J; }
						} else {
							if (!K.equals("")){ FGHIJK="F)"+F+"\nG)"+G+"\nH)"+H+"\nI)"+I+"\nK)"+K; }
							else { FGHIJK="F)"+F+"\nG)"+G+"\nH)"+H+"\nI)"+I; }
						}
					} else {
						if (!J.equals("")){
							if (!K.equals("")){ FGHIJK="F)"+F+"\nG)"+G+"\nH)"+H+"\nJ)"+J+"\nK)"+K; }
							else { FGHIJK="F)"+F+"\nG)"+G+"\nH)"+H+"\nJ)"+J; }
						} else {
							if (!K.equals("")){ FGHIJK="F)"+F+"\nG)"+G+"\nH)"+H+"\nK)"+K; }
							else { FGHIJK="F)"+F+"\nG)"+G+"\nH)"+H; }
						}
					}
				} else {
					if (!I.equals("")){
						if (!J.equals("")){
							if (!K.equals("")){ FGHIJK="F)"+F+"\nG)"+G+"\nI)"+I+"\nJ)"+J+"\nK)"+K; }
							else { FGHIJK="F)"+F+"\nG)"+G+"\nI)"+I+"\nJ)"+J; }
						} else {
							if (!K.equals("")){ FGHIJK="F)"+F+"\nG)"+G+"\nI)"+I+"\nK)"+K; }
							else { FGHIJK="F)"+F+"\nG)"+G+"\nI)"+I; }
						}
					} else {
						if (!J.equals("")){
							if (!K.equals("")){ FGHIJK="F)"+F+"\nG)"+G+"\nJ)"+J+"\nK)"+K; }
							else { FGHIJK="F)"+F+"\nG)"+G+"\nJ)"+J; }
						} else {
							if (!K.equals("")){ FGHIJK="F)"+F+"\nG)"+G+"\nK)"+K; }
							else { FGHIJK="F)"+F+"\nG)"+G; }
						}
					}
				}
			} else {
				if (!H.equals("")){
					if (!I.equals("")){
						if (!J.equals("")){
							if (!K.equals("")){ FGHIJK="F)"+F+"\nG)"+G+"\nH)"+H+"\nI)"+I+"\nJ)"+J+"\nK)"+K; }
							else { FGHIJK="F)"+F+"\nH)"+H+"\nI)"+I+"\nJ)"+J; }
						} else {
							if (!K.equals("")){ FGHIJK="F)"+F+"\nH)"+H+"\nI)"+I+"\nK)"+K; }
							else { FGHIJK="F)"+F+"\nH)"+H+"\nI)"+I; }
						}
					} else {
						if (!J.equals("")){
							if (!K.equals("")){ FGHIJK="F)"+F+"\nH)"+H+"\nJ)"+J+"\nK)"+K; }
							else { FGHIJK="F)"+F+"\nH)"+H+"\nJ)"+J; }
						} else {
							if (!K.equals("")){ FGHIJK="F)"+F+"\nH)"+H+"\nK)"+K; }
							else { FGHIJK="F)"+F+"\nH)"+H; }
						}
					}
				} else {
					if (!I.equals("")){
						if (!J.equals("")){
							if (!K.equals("")){ FGHIJK="F)"+F+"\nI)"+I+"\nJ)"+J+"\nK)"+K; }
							else { FGHIJK="F)"+F+"\nI)"+I+"\nJ)"+J; }
						} else {
							if (!K.equals("")){ FGHIJK="F)"+F+"\nI)"+I+"\nK)"+K; }
							else { FGHIJK="F)"+F+"\nI)"+I; }
						}
					} else {
						if (!J.equals("")){
							if (!K.equals("")){ FGHIJK="F)"+F+"\nJ)"+J+"\nK)"+K; }
							else { FGHIJK="F)"+F+"\nJ)"+J; }
						} else {
							if (!K.equals("")){ FGHIJK="F)"+F+"\nK)"+K; }
							else { FGHIJK="F)"+F; }
						}
					}
				}
			}
		} else {
			if (!G.equals("")){
				if (!H.equals("")){
					if (!I.equals("")){
						if (!J.equals("")){
							if (!K.equals("")){ FGHIJK="G)"+G+"\nH)"+H+"\nI)"+I+"\nJ)"+J+"\nK)"+K; }
							else { FGHIJK="G)"+G+"\nH)"+H+"\nI)"+I+"\nJ)"+J; }
						} else {
							if (!K.equals("")){ FGHIJK="G)"+G+"\nH)"+H+"\nI)"+I+"\nK)"+K; }
							else { FGHIJK="G)"+G+"\nH)"+H+"\nI)"+I; }
						}
					} else {
						if (!J.equals("")){
							if (!K.equals("")){ FGHIJK="G)"+G+"\nH)"+H+"\nJ)"+J+"\nK)"+K; }
							else { FGHIJK="G)"+G+"\nH)"+H+"\nJ)"+J; }
						} else {
							if (!K.equals("")){ FGHIJK="G)"+G+"\nH)"+H+"\nK)"+K; }
							else { FGHIJK="G)"+G+"\nH)"+H; }
						}
					}
				} else {
					if (!I.equals("")){
						if (!J.equals("")){
							if (!K.equals("")){ FGHIJK="G)"+G+"\nI)"+I+"\nJ)"+J+"\nK)"+K; }
							else { FGHIJK="G)"+G+"\nI)"+I+"\nJ)"+J; }
						} else {
							if (!K.equals("")){ FGHIJK="G)"+G+"\nI)"+I+"\nK)"+K; }
							else { FGHIJK="G)"+G+"\nI)"+I; }
						}
					} else {
						if (!J.equals("")){
							if (!K.equals("")){ FGHIJK="G)"+G+"\nJ)"+J+"\nK)"+K; }
							else { FGHIJK="G)"+G+"\nJ)"+J; }
						} else {
							if (!K.equals("")){ FGHIJK="G)"+G+"\nK)"+K; }
							else { FGHIJK="G)"+G; }
						}
					}
				}
			} else {
				if (!H.equals("")){
					if (!I.equals("")){
						if (!J.equals("")){
							if (!K.equals("")){ FGHIJK="G)"+G+"\nH)"+H+"\nI)"+I+"\nJ)"+J+"\nK)"+K; }
							else { FGHIJK="H)"+H+"\nI)"+I+"\nJ)"+J; }
						} else {
							if (!K.equals("")){ FGHIJK="H)"+H+"\nI)"+I+"\nK)"+K; }
							else { FGHIJK="H)"+H+"\nI)"+I; }
						}
					} else {
						if (!J.equals("")){
							if (!K.equals("")){ FGHIJK="H)"+H+"\nJ)"+J+"\nK)"+K; }
							else { FGHIJK="H)"+H+"\nJ)"+J; }
						} else {
							if (!K.equals("")){ FGHIJK="H)"+H+"\nK)"+K; }
							else { FGHIJK="H)"+H; }
						}
					}
				} else {
					if (!I.equals("")){
						if (!J.equals("")){
							if (!K.equals("")){ FGHIJK="I)"+I+"\nJ)"+J+"\nK)"+K; }
							else { FGHIJK="I)"+I+"\nJ)"+J; }
						} else {
							if (!K.equals("")){ FGHIJK="I)"+I+"\nK)"+K; }
							else { FGHIJK="I)"+I; }
						}
					} else {
						if (!J.equals("")){
							if (!K.equals("")){ FGHIJK="J)"+J+"\nK)"+K; }
							else { FGHIJK="J)"+J; }
						} else {
							if (!K.equals("")){ FGHIJK="F)"+F+"\nK)"+K; }
							else { FGHIJK=""; }
						}
					}
				}
			}
		}
		
		ashtam="";
		if (!ABCDE.equals("")) {
			if (!FGHIJK.equals("")) { ashtam=ABCDE+"\n"+FGHIJK; } else { ashtam=ABCDE; }	
		} else {
			if (!FGHIJK.equals("")) { ashtam=FGHIJK; } else { ashtam=""; }
		}
		pashtam=ViewATS.header+VSSLAS+ashtam+")\n";
	}
	
	public void formBIRDTAM(String birdtam_nr,String effective_time,String expiration_time,String intensity_level,String affected_area,
			String low_altitude,String high_altitude) {
		String BEE="";
		 if (!birdtam_nr.equals("")) {
			if (!effective_time.equals("")) {
				if (!expiration_time.equals("")) { BEE="BIRDTAM NUMBER : "+birdtam_nr+"\nEFFECTIVE TIME : "+effective_time+"\nEXPIRATION TIME : "+expiration_time; }
				else { BEE="BIRDTAM NUMBER : "+birdtam_nr+"\nEFFECTIVE TIME : "+effective_time; }
			} else {
				if (!expiration_time.equals("")) { BEE="BIRDTAM NUMBER : "+birdtam_nr+"\nEXPIRATION TIME : "+expiration_time; }
				else { BEE="BIRDTAM NUMBER : "+birdtam_nr; }
			}
		} else {
			if (!effective_time.equals("")) {
				if (!expiration_time.equals("")) { BEE="\nEFFECTIVE TIME : "+effective_time+"\nEXPIRATION TIME : "+expiration_time; }
				else { BEE="\nEFFECTIVE TIME : "+effective_time; }
			} else {
				if (!expiration_time.equals("")) { BEE="\nEXPIRATION TIME : "+expiration_time; }
				else { BEE=""; }
			}
		}
		
		String IALH="";
		if (!intensity_level.equals("")) {
			if (!affected_area.equals("")) {
				if (!low_altitude.equals("")) {
					if (!high_altitude.equals("")) { IALH="INTENSITY LEVEL : "+intensity_level+"\nAFFECTED AREA : "+affected_area+"\nLOW ALTITUDE : "+low_altitude+"\nHIGH ALTITUDE : "+high_altitude; }
					else { IALH="INTENSITY LEVEL : "+intensity_level+"\nAFFECTED AREA : "+affected_area+"\nLOW ALTITUDE : "+low_altitude; }
				} else {
					if (!high_altitude.equals("")) { IALH="INTENSITY LEVEL : "+intensity_level+"\nAFFECTED AREA : "+affected_area+"\nHIGH ALTITUDE : "+high_altitude; }
					else { IALH="INTENSITY LEVEL : "+intensity_level+"\nAFFECTED AREA : "+affected_area; }	
				}
			} else {
				if (!low_altitude.equals("")) {
					if (!high_altitude.equals("")) { IALH="INTENSITY LEVEL : "+intensity_level+"\nLOW ALTITUDE : "+low_altitude+"\nHIGH ALTITUDE : "+high_altitude; }
					else { IALH="INTENSITY LEVEL : "+intensity_level+"\nLOW ALTITUDE : "+low_altitude; }
				} else {
					if (!high_altitude.equals("")) { IALH="INTENSITY LEVEL : "+intensity_level+"\nHIGH ALTITUDE : "+high_altitude; }
					else { IALH="INTENSITY LEVEL : "+intensity_level; }	
				}
			}
		} else {
			if (!affected_area.equals("")) {
				if (!low_altitude.equals("")) {
					if (!high_altitude.equals("")) { IALH="AFFECTED AREA : "+affected_area+"\nLOW ALTITUDE : "+low_altitude+"\nHIGH ALTITUDE : "+high_altitude; }
					else { IALH="AFFECTED AREA : "+affected_area+"\nLOW ALTITUDE : "+low_altitude; }
				} else {
					if (!high_altitude.equals("")) { IALH="AFFECTED AREA : "+affected_area+"\nHIGH ALTITUDE : "+high_altitude; }
					else { IALH="AFFECTED AREA : "+affected_area; }	
				}
			} else {
				if (!low_altitude.equals("")) {
					if (!high_altitude.equals("")) { IALH="LOW ALTITUDE : "+low_altitude+"\nHIGH ALTITUDE : "+high_altitude; }
					else { IALH="LOW ALTITUDE : "+low_altitude; }
				} else {
					if (!high_altitude.equals("")) { IALH="HIGH ALTITUDE : "+high_altitude; }
					else { IALH=""; }	
				}
			}
		}
		
		String BEEIALH="";
		if (!BEE.equals("")) {
			if (!IALH.equals("")) { BEEIALH=BEE+"\n"+IALH; } else { BEEIALH=BEE; }
		} else {
			if (!IALH.equals("")) { BEEIALH=IALH; } else { BEEIALH=""; }
		}

		pbirdtam = ViewATS.header+BEEIALH+"\n"; 
	}
	
	public void formRQN(String request_id,String nof_or_type,String request_message) {
		String REQ="";
		if (!request_id.equals("")) {
			if (!nof_or_type.equals("")) REQ=request_id+" "+nof_or_type; else REQ=request_id;
		} else {
			if (!nof_or_type.equals("")) REQ=nof_or_type; else REQ=""; 
		}
		rqn="";
		if (!REQ.equals("")) {
			if (!request_message.equals("")) { rqn=REQ+"\n"+request_message; } else { rqn=REQ; }	
		} else {
			if (!request_message.equals("")) { rqn=request_message; } else { rqn=""; }
		}
		
		prqn=ViewATS.header+rqn+"\n";
	}
	
	public void viewNOTAM(Statement stmt, String ID) {
		try {
			ResultSet rs=stmt.getResultSet();
			while (rs.next()) { 
				vATS.setHeader(rs); 
				if (ID.compareToIgnoreCase(Titles.stNOTAM)==0) setNOTAM(rs); 
				else if (ID.compareToIgnoreCase(Titles.stSNOWTAM)==0) setSNOWTAM(rs);
				else if (ID.compareToIgnoreCase(Titles.stASHTAM)==0) setASHTAM(rs);
				else if (ID.compareToIgnoreCase(Titles.stBIRDTAM)==0) setBIRDTAM(rs);
				else if (ID.compareToIgnoreCase(Titles.stRQN)==0) setRQN(rs);
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	} 
	
}
