package setting;

import readwrite.ReadFromFile;


public class ErrMsg {
	ReadFromFile rff = new ReadFromFile();
	
	private final String val = "The value in field ";
	private final String req = " is required.";
	private final String ins = " Please insert ";
	private final String ins2 =  " for field ";
	private final String mark =  " !";
	
	public final String reqPriority = val+"PRIORITY"+req;
	public final String reqAddress = val+"ADDRESS"+req;
	public final String reqOriginator = val+"ORIGINATOR"+req;
	
	public final String req3a = val+"MESSAGE TYPE"+req;
	public final String req3b = val+"NUMBER"+req;
	public final String req3c = val+"REFERENCE DATA"+req;
	
	public final String req5a = val+"PHASE OF EMERGENCY"+req;
	public final String req5b = val+"ORIGINATOR OF MESSAGE"+req;
	public final String req5c = val+"NATURE OF EMERGENCY"+req;
	
	public final String req7a = val+"AIRCRAFT ID"+req;
	public final String req7c = "Field SSR Code should be filled if field SSR Mode is checked.";
	public final String req7b = "Field SSR Mode should be checked if field SSR Code is filled.";
	
	public final String req8a = val+"FLIGHT RULES"+req;
	public final String req8b = val+"TYPE OF FLIGHT"+req;
	
	public final String req9b = val+"TYPE OF AIRCRAFT"+req;
	public final String req9c = val+"WAKE TURB. CAT."+req;
	
	public final String req10 = val+"EQUIPMENT AND CAPABILITIES"+req; 
	
	public final String req13a = val+"DEP AD"+req;
	public final String req13b = val+"TIME"+req;
	
	public final String req14a = val+"BOUNDARY POINT"+req;
	public final String req14b = val+"TIME BOUNDARY"+req;
	public final String req14c = val+"CLEARED LEVEL"+req;
	
	public final String req15a = val+"CRUISING SPEED"+req;
	public final String req15b = val+"CRUISING LEVEL"+req;
	public final String req15c = val+"ROUTE"+req;
	
	public final String req16a = val+"DEST AD"+req;
	public final String req16b = val+"TOTAL EET"+req;
	public final String req16c = val+"DEST ALTN AD"+req;
	public final String req17a = val+"ARR AD"+req;
	public final String req20 = val+"ALERTING SEARCH AND RESCUE INFORMATION"+req;
	public final String req21 = val+"RADIO FAILURE INFORMATION"+req;
	public final String req22 = val+"AMENDMENT"+req;
	public final String reqDof = val+"OTHER INFORMATION (DOF/)"+req;
	
	public String req(int body) {
		rff.readConfiguration();
		return "Your data is "+body+" characters. Transmitting data maximum is "+rff.getMsglength()+" characters !";
	}

	public String wtc(String wtc, String t9b) {
		return "The value in field WAKE TURBULENCE CAT. is incorrect !!\nChoose \nWake Turbulence Category : "+wtc+" for \nType of Aircraft : "+t9b;
	}
	
	public final String infoAddress = ins+"8 LETTERS"+ins2+"ADDRESS"+mark;
	public final String infoOriginator = ins+"8 LETTERS"+ins2+"ORIGINATOR"+mark;
	public final String infoFreetext = val+"FREE TEXT should not more than 1500 characters"+mark;
	
	public final String info5b = ins+"8 LETTERS"+ins2+"ORIGINATOR OF MESSAGE"+mark;
	
	public final String info7c = ins+"4 NUMERICS"+ins2+"CODE"+mark;
	
	public final String info9b = ins+"2 - 4 CHARACTERS"+ins2+"TYPE OF AIRCRAFT"+mark;

	public final String info13a = ins+"4 LETTERS"+ins2+"DEP AD"+mark;
	public final String info13b = ins+"4 NUMERICS"+ins2+"TIME"+mark;

	public final String info14a = ins+"2 - 5 CHARACTERS"+ins2+"BOUNDARY POINT"+mark;
	public final String info14b = ins+"4 NUMERICS"+ins2+"TIME BOUNDARY"+mark;
	public final String info14cSM = "S or M should be followed by 4 NUMERICS.";
	public final String info14cFA = "F or A should be followed by 3 NUMERICS.";
	public final String info15aKN = "K or N should be followed by 4 NUMERICS.";
	public final String info15aM = "M should be followed by 3 NUMERICS.";
	
	
	public final String info16a = ins+"4 LETTERS"+ins2+"DEST AD"+mark;
	public final String info16b = ins+"4 NUMERICS"+ins2+"TOTAL EET"+mark;
	public final String info16c = ins+"4 LETTERS"+ins2+"DEST ALTN AD"+mark;
	public final String info16d = ins+"4 LETTERS"+ins2+"2ND DEST ALTN AD"+mark;
	//tambahan
	public final String info16cd = "Field DEST ALTN AD should be filled if field 2ND DEST ALTN AD is filled.";
	
	public final String info17a = ins+"4 LETTERS"+ins2+"ARR AD"+mark;
	public final String info17c = "ARRIVAL AERODROME should be filled if the value of ARR AD is ZZZZ.";
	public final String info17cvv = "ARR AD should be filled ZZZZ if the ARRIVAL AERODROME is filled.";
	public final String info19a = ins+"4 NUMERICS"+ins2+"ENDURANCE TIME"+mark;
	public final String info19fNum = ins+"2 NUMERICS"+ins2+"NUMBER"+mark;
	public final String info19fCap = ins+"3 NUMERICS"+ins2+"CAPACITY"+mark;
	public final String info19f = ins+" Number and Capacity of Dinghies";
	

	//public final String info1800 = "Your data is "+ATSForms.getBodyATSFree().length()+" characters. Transmitting data maximum is 1800 characters !!";
	public String info1800(int body) {
		return "Your data is "+body+" characters. Transmitting data maximum is 1800 characters !!";
	}
	
	/**
	 * invalid
	 */
	private final String inv =  "Invalid ";
	private final String see = " value.\nSee the instruction below the ";
	private final String mark2 = " text !";
	private final String hhmm = " is four figure format (hhmm, where hh equals the hour and mm equals the minute).";
	private final String yymmdd = " is six figure format (YYMMDD, where YY equals the year, MM equals the month and DD equals the day).";
	private final String valf = " value.\nField ";
	
	public final String infoInvalidDof = inv+"DOF/ indicator"+see+"DOF/ indicator"+mark2;
	public final String infoInvalidReg = inv+"REG/ indicator"+see+"REG/ indicator"+mark2;
	public final String infoInvalidOpr = inv+"OPR/ indicator"+see+"OPR/ indicator"+mark2;
	public final String infoInvalidSts = inv+"STS/ indicator"+see+"STS/ indicator"+mark2+"\n\nnote: Other reasons for special handling by ATS shall be denoted under the designator RMK/.";
	public final String infoInvalidPer = inv+"PER/ indicator"+see+"PER/ indicator"+mark2;
	public final String infoInvalidCode = inv+"CODE/ indicator"+see+"CODE/ indicator"+mark2;
	public final String infoInvalidSel = inv+"SEL/ indicator"+see+"SEL/ indicator"+mark2;
	public final String infoInvalidPbn = inv+"PBN/ indicator"+see+"PBN/ indicator"+mark2;
	public final String infoInvalidPbnFix = inv+"PBN/ indicator"+see+"PBN/ indicator"+mark2+"\n\nInsert A1 or B1-B6 or C1-C4 or D1-D4 or L1 or O1-O4 or S1-S2 or T1-T2";
	
	public final String infoInvalid10 = inv+"EQUIPMENT AND CAPABILITIES"+see+"EQUIPMENT AND CAPABILITIES"+mark2;
	public final String infoInvalid14c = inv+"CLEARED LEVEL"+see+"CLEARED LEVEL"+mark2;
	public final String infoInvalid14d = inv+"SPL CROSSING DATA"+see+"SPL CROSSING DATA"+mark2;
	public final String infoInvalid14e = inv+"CROSSING CONDITION"+see+"CROSSING CONDITION"+mark2;
	public final String infoInvalid15a = inv+"CRUISING SPEED"+see+"CRUISING SPEED"+mark2;
	public final String infoInvalid15b = inv+"CRUISING LEVEL"+see+"CRUISING LEVEL"+mark2;

	public final String infoInvalid13b = inv+"TIME"+valf+"TIME"+hhmm;
	public final String infoInvalid14b = inv+"TIME BOUNDARY"+valf+"TIME BOUNDARY"+hhmm;
	public final String infoInvalid16b = inv+"TOTAL EET"+valf+"TOTAL EET"+hhmm;
	public final String infoInvalid19a = inv+"ENDURANCE TIME"+valf+"ENDURANCE TIME"+hhmm;
	public final String infoInvalid18Dof = inv+"DOF/ indicator"+valf+"OTHER INFORMATION with DOF/ indicator"+yymmdd;
	
	public final String infoInvalid18DofVal = "Invalid DOF/ indicator value.\nDOF/ shall be equal or later, than the actual date/time of creation !!";
	public final String infoInvalid10b =  "The value in field EQUIPMENT AND CAPABILITIES is not match !!";
	public final String infoInvalid10a = inv+"EQUIPMENT AND CAPABILITIES"+see+"EQUIPMENT AND CAPABILITIES"+mark2+"\n\nnote: 'N' is not allowed, if 'S' has inserted into field EQUIPMENT AND CAPABILITIES and vice versa.";		
	public final String infoInvalid10aFix = inv+"EQUIPMENT AND CAPABILITIES"+see+"EQUIPMENT AND CAPABILITIES"+mark2+"\n\nInsert E1-E3 or J1-J7 or M1-M3 or P1-P9";
	public final String infoInvalid10aSTS =  "If 'W' is inserted in field EQUIPMENT AND CAPABILITIES, then 'NONRVSM' in field OTHER INFORMATION STS/ indicator is not allowed !!";
	
	public final String infoTyp9b = "If ZZZZ is inserted in field TYPE OF AIRCRAFT, then complete field OTHER INFORMATION with TYP/ indicator !!";
	public final String info9bTyp = "Field TYPE OF AIRCRAFT should be filled with ZZZZ, if field OTHER INFORMATION with TYP/ indicator is specified.";
	
	public final String infoCom10 = "Field OTHER INFORMATION with indicator COM/, NAV/ and/or DAT/ should be filled, if the letter 'Z' is used in field EQUIPMENT AND CAPABILITIES.";
	public final String infoSur10 = "Field OTHER INFORMATION with indicator SUR/ should be filled, if there is an additional surveillance in field EQUIPMENT AND CAPABILITIES.";
	
	public final String infoDep13 = "If ZZZZ or AFIL is inserted in field DEP AD, then complete field OTHER INFORMATION with DEP/ indicator !!";
	public final String info13Dep = "Field DEP AD should be filled with ZZZZ or AFIL, if field OTHER INFORMATION with DEP/ indicator is specified.";
	
	public final String infoDest16 = "If ZZZZ is inserted in field DEST AD, then complete field OTHER INFORMATION with DEST/ indicator !!";
	public final String info16Dest = "Field DEST AD should be filled with ZZZZ, if field OTHER INFORMATION with DEST/ indicator is specified.";
	
	public final String infoAltn16 = "If ZZZZ is inserted in field DEST ALTN AD, then complete field OTHER INFORMATION with ALTN/ indicator !!";
	public final String info16Altn = "Field DEST ALTN AD should be filled with ZZZZ, if field OTHER INFORMATION with ALTN/ indicator is specified.";

	public final String infoPbn10 = "Field OTHER INFORMATION with PBN/ indicator should be filled, if the letter 'R' is used in field EQUIPMENT AND CAPABILITIES.";
	public final String info10Pbn = "Field EQUIPMENT AND CAPABILITIES should be contained the letter 'R', if field OTHER INFORMATION with PBN/ indicator is filled.";
	
	public final String info10G = "If B1, B2, C1, C2, D1, D2, O1 or O2 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'G' must be included in field EQUIPMENT AND CAPABILITIES !";
	public final String info10D = "If B1, B3, C1, C3, D1, D3, O1 or O3 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' must be included in field EQUIPMENT AND CAPABILITIES !";
	public final String info10I = "If B1, B5 or C1 are filled in field OTHER INFORMATION with PBN/ indicator, then an 'I' must be included in field EQUIPMENT AND CAPABILITIES !";
	public final String info10DI = "If C1, C4, D1, D4, O1 or O4 are filled in field OTHER INFORMATION with PBN/ indicator, then a 'D' and an 'I' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'DI' must appear in 10a)";
	public final String info10OD = "If B1 or B4 is filled in field OTHER INFORMATION with PBN/ indicator, then an 'O' or 'S' and a 'D' must be included in field EQUIPMENT AND CAPABILITIES !\n\n(i.e., 'OD' or 'SD' must appear in 10a)";
    
	// In List
	public final static String LIST_INFO_AT_LEAST = "At least one row should be selected.";
	public final static String LIST_INFO_ARE_YOU_SURE = "Are you sure you want to delete ?";
	public final static String LIST_INFO_EDIT_SINGLE_SELECTION = "Edit mode for only one row.";
	
//	public final String LIST_INFO1 = "At least one row should be selected.";
//	public final String LIST_INFO2 = "Just select one row to edit message !!";
//	public final String LIST_INFO3 = "Are you sure you want to delete selected message(s) ?";
//	public final String LIST_INFO4 = "Are you sure want to delete all messages ?";
	
	public ErrMsg() {
		
	}
}
