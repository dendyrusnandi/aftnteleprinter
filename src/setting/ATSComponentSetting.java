package setting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import readwrite.ReadFromFile;
import displays.TeleSplashScreen2016IP;
import displays.forms.FormTemplates;


public class ATSComponentSetting {

	Shorten sh = new Shorten();
	TextSetting ts = new TextSetting();
	ReadFromFile rff = new ReadFromFile();

	//balloons

	public static String bFiled = "Filled by";
	
	public static String b3a = "Message Type\n\n" +
			"3 LETTERS as follows:\n\n" +
			"ALR - Alerting\n" +
			"RCF - Radio Communication Failure\n\n" +
			"FPL - Filed Flight Plan\n" +
			"DLA - Delay\n" +
			"CHG - Modification\n" +
			"CNL - Cancellation\n" +
			"DEP - Departure\n" +
			"ARR - Arrival\n\n" +
			"CDN - Co-ordination\n" +
			"CPL - Current Flight Plan\n" +
			"EST - Estimate\n" +
			"ACP - Acceptance\n\n" +
			"LAM - Logical Acknowledgement\n\n" +
			"RQP - Request Flight Plan\n"+
			"RQS - Request Supplementary Flight Plan\n" +
			"SPL - Supplementary Flight Plan";
	public static String b3b = "Message Number\n\n" +
			"1 to 4 LETTER(S) identifying the sending\n" +
			"ATS unit, followed by\n\n" +
			"OBLIQUE STROKE (/) followed by\n\n" +
			"1 to 4 LETTER(S) identifying the receiving\n" +
			"ATS unit, followed by\n\n" +
			"3 DECIMAL NUMERICS giving the serial number\n" +
			"of this message in the sequence of messages\n" +
			"transmitted by this unit to the indicated\n" +
			"receiving ATS unit.";
	public static String b3c = "Reference Data\n\n" +
			"1 to 4 LETTER(S) followed by OBLIQUE STROKE (/)\n" +
			"followed by 1 to 4 LETTER(S) followed by\n\n" +
			"3 DECIMAL NUMERICS, giving the 'message number'\n" +
			"contained in element (b) of the operational\n" +
			"message which began the sequence of messages of\n" +
			"which this message is a part.";
	
	public static String b5a = "Phase of Emergency\n---------------------------\n" +
			"INCERFA if an uncertainly phase, or\n" +
			"ALERFA if an alert phase, or\n" +
			"DETRESFA if a distress phase\n\n" +
			"has been declared in respect of the aircraft concerned.";
	public static String b5b = "Originator of Message\n------------------------------\n" +
			"8 LETTERS, being the 4-letter ICAO location indicator plus " +
			"the 3-letter designator of the ATS unit originating the " +
			"message followed by the letter X or, if applicable, the " +
			"one-letter designator identifying the division of the " +
			"ATS unit originating the message.";
	public static String b5c = "Nature of Emergency\n-----------------------------\n" +
			"SHORT PLAIN LANGUAGE TEXT, as necessary to explain the " +
			"nature of the emergency, with natural spaces between " +
			"the words.";
	
	public static String b7a = "Aircraft Identification" +
			"\n\nINSERT one of the following aircraft identification, not exceeding 7 alphanumeric characters and without hypens or symbols:" +
			"\n\na) the ICAO designator for the aircraft operating agency followed by the flight identification (e.g. KLM511, NGA213, JTR25) when in radiotelephony the call sign to be used by the aircraft will consist of the ICAO telephony designator for the operating agency followed by the flight identification (e.g. KLM511, NIGERIA 213, JESTER 25);" +
			"\n\nb) in the radiotelephony the call sign to be used by the aircraft will consist of this identification alone (e.g. CGAJS), or preceded by the ICAO telephony designator for the aircraft operating agency (e.g. BLIZZARD CGAJS);";
	public static String b7b = "SSR Mode\n\n1 LETTER giving the SSR Mode related to (c).";
	public static String b7c = "SSR Code\n\n" +
			"4 NUMERICS giving the SSR Code assigned to the aircraft " +
			"by ATS and transmitted in the Mode given in (b).";
	
	public static String b8a = "Flight Rules\n\n" +
			"INSERT one of the following letters to denote the category of flight rules with which the pilot intends to comply:\n\n" +
			"- I if it is intended that the entire flight will be operated under the IFR\n" +
			"- V if it is intended that the entire flight will be operated under the VFR\n" +
			"- Y if the flight initially will be operated under the IFR, followed by one or more subsequent changes of flight rules\n" +
			"- Z if the flight initially will be operated under the VFR, followed by one or more subsequent changes of flight rules" +
			"\n\nSpecify in Item 15 the point or points at which a change of flight rules is planned.";
	public static String b8b = "Type of Flight\n\n" +
			"INSERT one of the following letters to denote the type of flight when so required by the appropriate ATS authority:\n\n" +
			"- S if scheduled air service\n" +
			"- N if non-scheduled air transport operation\n" +
			"- G if general aviation\n" +
			"- M if military\n" +
			"- X if other than any of the defined categories above." +
			"\n\nSpecify status of a flight following the indicator STS in Item 18, or when necessary to denote other reasons for specific handling by ATS, indicate the reason following the indicator RMK/ in Item 18.";

	public static String b9a = "Number of Aircraft (if more than one)\n\n" +
		"1 to 2 NUMERICS giving the number of aircraft in the flight.";
	public static String b9b = "Type of Aircraft\n\n" +
		"2 to 4 CHARACTERS, being the appropriate designator chosen " +
		"from ICAO Doc 8643, Aircraft Type Designators, or \n\n" +
		"ZZZZ if no designator has been assigned or if there is more " +
		"than one type of aircraft in the flight.";
	public static String b9c = "Wake Turbulence Category\n\n" +
		"1 LETTER to indicate maximum certificated " +
		"take-off mass of the aircraft:\n\n" +
		"H - Heavy\n" +
		"M - Medium\n" +
		"L - Light\n" +
		"J - Jumbo";
	

	public static String b10a = "Radio Communication, Navigation and Approach Aid Equipment and Capabilities\n\n" +
			"1 LETTER as follows:\n" +
			"N: no COM/NAV/approach aid equipment for the route to be\n    " +
			"flown is carried, or the equipment is unserviceable, OR\n" +
		
			"S: Standard COM/NAV/approach aid equipment for the route\n    " +
			"to be flown is carried and serviceable, AND/OR INSERT\n\n" +
			
			"ONE OR MORE OF THE FOLLOWING LETTERS to indicate the serviceable COM/NAV/approach aid equipment and capabilities:" +
			"\nA : GBAS landing system" +					"\t\tJ7: CPDLC FANS 1/A" +
			"\n           " + 				"\t\t\t\t\t    SATCOM (Iridium)" + 	//lanjutan
			"\nB : LPV (APV with SBAS)" +					"\t\tK : MLS" +
			"\nC : LORAN C" +						"\t\t\t\tL : ILS" +
			"\nD : DME" +							"\t\t\t\t\tM1: ATC RTF SATCOM" +
			"\n                   " + 				"\t\t\t\t     (INMARSAT)" +//lanjutan
			"\nE1: FMC WPR ACARS" +						"\t\tM2: ATC RTF (MTSAT)" +
			"\nE2: D-FIS ACARS" +					"\t\t\tM3: ATC RTF (Iridium)"+
			"\nE3: PDC ACARS" +						"\t\t\tO: VOR" +
			"\nF : ADF" +							"\t\t\t\t\tP1-P9: Reserved for RCP"+
			"\nG : GNSS" +							
			"\nH : HF RTF" +						"\t\t\t\tR: PBN approved" +
			"\nI : Inertial Navigation" +			"\t\t\tT: TACAN" +
			"\nJ1: CPDLC ATN VDL Mode 2" +					"\tU: UHF RTF" +
			"\nJ2: CPDLC FANS 1/A HFDL" +			"\tV: VHF RTF" +
			"\nJ3: CPDLC FANS 1/A VDL" +			"\tW: RVSM approved" +
			"\n     Mode A" + 														//lanjutan
			"\nJ4: CPDLC FANS 1/A VDL" +			"\tX: MNPS approved" +
			"\n     Mode 2" + 														//lanjutan
			"\nJ5: CPDLC FANS 1/A" +				"\t\tY: VHF with 8.33 kHz" + 		
			"\n     SATCOM (INMARSAT)" +			"\t\t    channel spacing" + 		//lanjutan
			"\n		                 " +			"\t\t    capability"+//lanjutan
			"\nJ6: CPDLC FANS 1/A" +				"\t\tZ: Other equipment carried or" + 		
			"\n     SATCOM (MTSAT)" +				"\t\t    other capabilities";
	public static String b10b = "Surveillance equipment and capabilities\n\n" +
			"N   if no surveillance equipment for the route to be flown is\n     carried, or the equipment is unserviceable\n" +
			"A   Transponder - Mode A (4 digits - 4 096 codes)\n" +
			"C   Transponder - Mode A (4 digits - 4 096 codes) and Mode C\n" +
			"E   Transponder - Mode S, including aircraft identification,\n     pressure-altitude and extend squitter (ADS-B) capability\n" +
			"H   Transponder - Mode S, including aircraft identification,\n     pressure-altitude and enhanced survilliance capability\n" +
			"I   Transponder - Mode S, including aircraft identification,\n     but no pressure-altitude capability\n" +
			"L   Transponder - Mode S, including aircraft identification,\n     pressure-altitude, extend squitter (ADS-B) and enhanced\n     survilliance capability\n" +
			"P   Transponder - Mode S, including pressure-altitude, but no\n     aircraft identification capability\n" +
			"S   Transponder - Mode S, including both pressure altitude and\n     aircraft identification capability\n" +
			"X   Transponder - Mode S with neither aircraft identification\n     nor pressure-altitude capability\n\n" +
			
			"ADS B\n"+
			"B1	 ADS-B with dedicated 1090 MHz ADS-B 'out' capability\n"+
			"B2	 ADS-B with dedicated 1090 MHz ADS-B 'out' and 'in'\n\t capability\n"+
			"U1	 ADS-B 'out' capability using UAT\n"+
			"U2	 ADS-B 'out' and 'in' capability using UAT\n"+
			"V1	 ADS-B 'out' capability using VDL Mode 4\n"+
			"V2	 ADS-B 'out' and 'in' capability using VDL Mode 4\n\n"+
			
			"ADS C\n"+
			"D1  ADS-C with FANS 1/A capabilities\n"+
			"G1  ADS-C with ATN capabilities\n\n"+
			"Alphanumeric characters not indicated above are reserved.";
	
	public static String b13a = "Departure Aerodrome\n\n" +
			"INSERT the ICAO four-letter location indicator of the departure aerodrome as specified in Doc 7910, Location Indicators,\n\n" +
			"OR if no location indicator has been assigned,\n\n" +
			"INSERT ZZZZ and SPECIFY, in Item 18, the name and location of the aerodrome preceded by DEP/\n\n" +
			"OR the first point of the route or the marker radio beacon preceded by DEP/..., if the aircraft has not taken off from the aerodrome,\n\n" +
			"OR if the flight plan is received from an aircraft in flight,\n\n" +
			"INSERT AFIL and SPECIFY, in Item 18, the ICAO four-letter location indicator of the location ATS unit from which supplementary flight plan data can be obtained, preceded by DEP/";
	public static String b13b = "Time\n\n" +
			"4 NUMERICS giving\n\n" +
			"the estimated off-block time at the aerodrome in (a) " +
			"in FPL and DLA messages transmitted before departure " +
			"and in RQP message, if known, or\n\n" +
			"the actual time of departure from the aerodrome in (a) " +
			"in ALR, DEP and SPL messages, or\n\n" +
			"the actual or estimated time of departure from the first point shown in " +
			"the Route Field (see Field Type 15) in FPL messages derived from " +
			"flight plans filed in the air, as shown by the letters AFIL in (a).";
	
	public static String b14a = "Boundary Point\n\n" +
			"The BOUNDARY POINT expressed either by a designator " +
			"consisting of 2 to 5 characters, in Geographical Coordinates, " +
			"in Abbreviated Geographical Coordinates, or by bearing and " +
			"distance from a designated point (e.g. a VOR).";
			String b14b = "Time at Boundary Point\n\n" +
			"4 NUMERICS giving the estimated time at the Boundary Point."; 
	public static String b14c = "Cleared Level\n\n" +
			"F followed by 3 NUMERICS, or\n" +
			"S followed by 4 NUMERICS, or\n" +
			"A followed by 3 NUMERICS, or\n" +
			"M followed by 4 NUMERICS\n\n" +
			"giving the cleared level at which the aircraft will cross " +
			"the Boundary Point, if in level cruising flight, or the " +
			"cleared level to which it is proceeding, if climbing or " +
			"descending at the Boundary Point.";
	public static String b14d = "Supplementary Crossing Data\n\n" +
			"A LEVEL, expressed as in (c), at or above which or at or " +
			"below which (see (e)) the aircraft will cross the Boundary Point.";
	public static String b14e = "Crossing Condition\n\n" +
			"1 LETTER as follows:\n\n" +
			"A if the aircraft will cross the Boundary Point at or above " +
			"the level in (d), or\n\n" +
			"B if the aircraft will cross the Boundary Point at or below " +
			"the level in (d).";
	
	public static String b15a = "Cruising speed (maximum 5 characters)\n\n" +
			"INSERT the True Air Speed for the first or the whole cruising portion of the flight, in terms of:\n" +
			"- Kilometres per hour, expressed as K followed by 4 figures\n  (e.g. K0830), or\n" +
			"- Knots, expressed as N followed by 4 figures (e.g. N0485), or\n" +
			"- True Mach number, when so prescribed by the appropriate\n" +
			"  ATS authority, to the nearest hundredth of unit Mach,\n" +
			"  expressed as M followed by 3 figures (e.g M082).";
	public static String b15b = "Cruising level (maximum 5 characters)\n\n" +
			"INSERT the planned cruising level for the first or the whole portion of the route to be flown, in terms if:\n" +
			"- Flight level, expressed as F followed by 3 figures (e.g.\n" +
			"  F085; F330), or\n" +
			"- *Standard Metric Level in tens of metres, expressed as\n" +
			"  S followed by 4 figures (e.g. S1130), or\n" +
			"- Altitude in hundreds of feet, expressed as A followed by\n" +
			"  3 figures (e.g. A045; A100), or\n" +
			"- Altitude in tens of metres, expressed as M followed by 4\n" +
			"  figures (e.g. M0840), or\n" +
			"- for uncontrolled VFR flights, the letters VFR.\n\n" +
			"*When so prescribed by the appropriate ATS authorities.";
	public static String b15c = "Route (including changes of speed, level and/or flight rules)\n\n" +
			"Flights along designated ATS routes\n" +
			"1. ATS route (2 to 7 characters)\n" +
			"  The coded designator assigned to the route or route segment\n" +
			"  including, where appropriate, the coded designator\n" +
			"  assigned to the standard departure or arrival route\n" +
			"  (e.g. BCN1, B1, R14, UB10, KODAP2A)\n\n" +
			
			"2. Significant point (2 to 11 characters)\n" +
			"   The coded designator (2 to 5 chars) assigned to the point\n" +
			"   (e.g. LN, MAY, HADDY), or if no coded designator has been\n" +
			"   assigned, one of the following ways:\n" +
			"   - Degress only (7 chars), e.g. 46N078W\n" +
			"   - Degress and minutes (11 chars), e.g. 4620N07805W\n" +
			"   - Bearing distance from significant point, e.g. DUB180040\n\n" +
			
			"3. Change of speed or level (maximum 21 characters)\n" +
			"   The point at which a change of speed (5% TAS or 0.01 Mach\n" +
			"   or more) or a change of level is planned to commence,\n" +
			"   expressed exactly as in (2) above.\n" +
			"   e.g. LN/N0284A045; MAY/N0305F330; HADDY/\n" +
			"   N0420F330; 4602N07805W/N0500F350; 46N078W/\n" +
			"   M082F330; DUB180040/N0350M0840\n\n" +
			
			"4. Change of flight rules (maximum 3 characters)\n" +
			"   The point at which the change of flight rules is planned,\n" +
			"   expressed exactly as in (2) or (3) above as appropriate,\n" +
			"   followed by a space and one of the following:\n" +
			"   - VFR if from IFR to VFR\t- IFR if from VFR to IFR\n" +
			"   e.g. LN VFR; LN/N0284A050 IFR\n\n" +
			
			"5. Cruise climb (maximum 28 characters)\n" +
			"   e.g. C/48N050W/M082F290F350; C/48N050W/\n" +
			"   M082F290PLUS; C/52N050W/M220F580F620;\n";
	
	public static String b16a = "Destination aerodrome\n\n" +
		"INSERT the ICAO four-letter location indicator of the destination aerodrome as specfied in Doc 7910, Location Indicators,\n\n" +
		"OR, if no location indicator has been assigned,\n\n" +
		"INSERT ZZZZ and SPECIFY in Item 18 the name and location of the aerodrome, preceded by DEST/";
	public static String b16b = "Total Estimated Elapsed Time\n\n" +
		"4 NUMERICS, giving the total estimated elapsed time.";
	public static String b16c = "Destination alternate aerodrome(s)\n\n" +
		"INSERT the ICAO four-letter location indicator(s) of not more than two destination alternate aerodromes, as specfied in Doc 7910, Location Indicators, separated by a space,\n\n" +
		"OR, if no location indicator has been assigned to the destination alternate aerodrome(s),\n\n" +
		"INSERT ZZZZ and SPECIFY in Item 18 the name and location of the destination alternate aerodrome(s), preceded by ALTN/";
	public static String b16d = "Destination alternate aerodrome(s)\n\n" +
		"INSERT the ICAO four-letter location indicator(s) of not more than two destination alternate aerodromes, as specfied in Doc 7910, Location Indicators, separated by a space,\n\n" +
		"OR, if no location indicator has been assigned to the destination alternate aerodrome(s),\n\n" +
		"INSERT ZZZZ and SPECIFY in Item 18 the name and location of the destination alternate aerodrome(s), preceded by ALTN/";
	
	public static String b17a = "Arrival Aerodrome\n\n" +
			"4 LETTERS, being\n\n" +
			"the ICAO four-letter location indicator allocated " +
			"to the arrival aerodrome, or\n\n" +
			"ZZZZ if no ICAO location indicator has been allocated.";
	public static String b17b = "Time of Arrival\n\n" +
			"4 NUMERICS, giving the actual time no arrival.";
	public static String b17c = "Arrival Aerodrome\n\n" +
			"Name of arrival aerodrome, if ZZZZ is inserted in (a).";
	
	public static String b18sts = "Reason for special handling by ATS, e.g. a search and rescue mission, as follows:\n\n"+
		"ALTRV:\tfor a flight operated in accordance with an altitude\n\t\treservation;\n"+
		"ATFMX:\tfor a flight approved for exemption from ATFM\n\t\tmeasures by the appropriate ATS authority;\n"+
		"FFR:\tfire-fighting;\n"+
		"FLTCK:\tflight check for calibration of navaids;\n"+
		"HAZMAT:for a flight carrying hazardous material;\n"+
		"HEAD:\ta flight with Head of State status;\n"+
		"HOSP:\tfor medical flight declared by medical authorities;\n"+
		"HUM:\tfor a flight operating on a humanitarian mission;\n"+
		"MARSA:for a flight for which a military entity assumes\n\t\tresponsibility for separation of military aircraft;\n"+
		"MEDEVAC: for a life critical medical emergency evacuation;\n"+
		"NONRVSM: for non-RVSM capable flight intending to operate\n\t\tin RVSM airspace;\n"+
		"SAR:\tfor a flight engaged in a search and rescue mission;\n"+
		"STATE:\tfor a flight engaged in military, customs or police\n\t\tservices.";
	public static String b18pbn = "Indication of RNAV and/or RNP capabilities. Include as many of the descriptors below, as apply to the flight, up to maximum 8 entries, i.e. a total of not more than 16 characters.\n\n" +
			"RNAV SPECIFICATION\n" +
			"A1: RNAV 10 (RNP 10)" + 					"\tC1: RNAV 2 all permitted sensors\n" +
			"B1: RNAV 5 all permitted" +				"\tC2: RNAV 2 GNSS\n" +
			"\tsensors"+								"\t\t\tC3: RNAV 2 DME/DME\n" +
			"B2: RNAV 5 GNSS" +							"\t\tC4: RNAV 2 DME/DME/IRU\n" +
			"B3: RNAV 5 DME/DME" +						"\tD1: RNAV 1 all permitted sensors\n" +
			"B4: RNAV 5 VOR/DME" +						"\tD2: RNAV 1 GNSS\n" +
			"B5: RNAV 5 INS or IRS" +					"\tD3: RNAV 1 DME/DME\n" +
			"B6: RNAV 5 LORANC" +						"\tD4: RNAV 1 DME/DME/IRU\n\n" +
														
			"RNP SPECIFICATION\n" +
			"L1: RNP 4" +								"\t\t\t\t\tS1: RNP APCH\n" +
			"O1: Basic RNP 1 all permitted" +			"\tS2: RNP APCH with\n" +
			"\tsensors"+								"\t\t\t\t\tBAR-VNAV\n"+
			"O2: Basic RNP 1 GNSS" +					"\t\tT1: RNP AR APCH with RF\n" +
			"O3: Basic RNP 1 DME/DME" +					"\t\t(special auth. req.)\n" +
			"O4: Basic RNP 1 DME/DME/" +				"\tT2: RNP AR APCH without\n" +
			"\tIRU" +									"\t\t\t\t\t\tRF (special auth. req.)\n\n" +
			
			"Combinations of alphanumeric characters not indicated above are reserved.\n\n" +
			"- If B1, B2, C1, C2, D1, D2, O1 or O2 are filed, then a 'G'\n  must be included in Field 10a\n" +
			"- If B1, B3, C1, C3, D1, D3, O1 or O3 are filed, then a 'D'\n  must be included in Field 10a\n" +
			"- If B1 or B4 is filed, then an 'O' or 'S' and a 'D' must be\n  included in Field 10a (i.e., 'OD' or 'SD' must appear in 10a)\n" +
			"- If B1, B5 or C1 are filed, then an 'I' must be included in\n  Field 10a; and\n" +
			"- If C1, C4, D1, D4, O1 or O4 are filed, then a 'D' and an 'I'\n  must be included in Field 10a (i.e., 'DI' must appear in 10a)";
	public static String b18nav = "Significant data related to navigation equipment, other than specified in PBN/, as required by the appropriate ATS authority. Indicate GNSS augmentation under this indicator, with a space between two or more methods of augmentation, e.g. NAV/GBAS SBAS.";
	
	public static String b18com = "Indicate communications applications or capabilities not specified in Item 10a.";
	public static String b18dat = "Indicate data applications or capabilities not specified in Item 10a.";
	public static String b18sur = "Include surveillance applications or capabilities not specified in Item 10b.";
	
	public static String b18dep = "Name and location of departure aerodrome, if ZZZZ is inserted in Item 13, or the ATS unit from which supplementary flight plan data can be obtained, if AFIL is inserted in Item 13. For aerodromes not listed in the relevant AIP, indicate location as follows:\n\n" +
			"With 4 figures describing latitude in degrees and tens and units of minutes followed by 'N' (North) or 'S' (South), followed by 5 figures describing longitude in degrees and tens and units of minutes, followed by 'E' (East) or 'W' (West). Make up the correct number of figures, where necessary, by insertion of zeros, e.g. 4620N07805W (11 characters).\n\n" +
			"OR, Bearing and distance from the nearest significant point, as follows:\n\n" +
			"The identification of the significant point followed by the bearing from the point in the form of 3 figures giving degrees magnetic, followed by the distance from the point in the form of 3 figures expressing nautical miles. In areas of high latitude where it is determined by the appropriate authority that reference to degrees magnetic is impractical, degrees true may be used. Make up the correct number of figures, where necessary, by insertion of zeros, e.g. a point of 180 degrees magnetic at a distance of 40 nautical miles from VOR 'DUB' should be expressed as DUB180040.\n\n" +
			"OR, The first point of the route (name or LAT/LONG) or the marker radio beacon, if the aircraft has not taken off from an aerodrome.";
	public static String b18dest = "Name and location of destination aerodrome, if ZZZZ is inserted in Item 16. For aerodromes not listed in the relevant AIP, indicate location in LAT/LONG or bearing and distance from the nearest significant point, as described under DEP/.";
	public static String b18dof = "The date of flight departure in six figure format (YYMMDD, where YY equals the year, MM equals the month and DD equals the day).";
	
	public static String b18reg = "The nationality or common mark and registration mark of the aircraft, if different from the aircraft identification in field AIRCRAFT ID.";
	public static String b18eet = "Significant point of FIR boundary designators and accumulated estimated elapsed times from take-off to such points or FIR boundaries, when so prescribed on the basis of regional air navigation agreements, or by the appropriate ATS authority.\n\n" +
			"e.g., EET/CAP0745 XYZ080 or EET/EINN0204";
	public static String b18sel = "SELCAL Code, for aircraft so equipped. A single string of four letters."; 
	
	public static String b18typ = "Type(s) of aircraft, preceded if necessary without a space by number(s) of aircraft and separated by one space, if ZZZZ is inserted in Item 9.\n\n" +
			"e.g., TYP/2F15 5F5 3B2"; 
	public static String b18code = "Aircraft address (expressed in the form of an alphanumerical code of six hexadecimal characters) when required by the appropriate ATS authority. Example: 'F00001' is the lowest aircraft address contained in the specific block administered by ICAO.";
	public static String b18dle = "Enroute delay or holding, insert the significant point(s) on the route where a delay is planned to occur, followed by the length of delay using four figure time in hours and minutes (hhmm).\n\n" +
			"e.g., DLE/MDG0030";
	
	public static String b18opr = "ICAO designator or name of the aircraft operating agency, if different from the aircraft identification in item 7.";
	public static String b18orgn = "The originator's 8 letter AFTN address or other appropriate contact details, in cases where the originator of the flight plan may not be readily identified, as required by the appropriate ATS authority.";
	public static String b18per = "Aircraft performance data, indicated by a single letter, as shown below:" +
			"\n* Cat. A : < 169 km/h (91 kt) indicated airspeed (IAS)" +
			"\n* Cat. B : 169 km/h (91 kt) or more but < 224 km/h (121 kt)" +
			"\n* Cat. C : 224 km/h (121 kt) or more but < 261 km/h (141 kt)" +
			"\n* Cat. D : 261 km/h (141 kt) or more but < 307 km/h (166 kt)" +
			"\n* Cat. E : 307 km/h (166 kt) or more but < 391 km/h (211 kt)" +
			"\n* Cat. H : Specific procedures for Helicopters";
	public static String b18altn = "Name of destination alternate aerodrome(s), if ZZZZ is inserted in Item 16. For aerodromes not listed in the relevant AIP, indicate location in LAT/LONG or bearing and distance from the nearest significant point, as described in DEP/.";
	public static String b18ralt = "ICAO four letter indicator(s) for en-route alternate(s), as specified in Doc 7910, Location Indicators, or name(s) of en-route alternate aerodrome(s), if no indicator is allocated. For aerodromes not listed in the relevant AIP, indicate located in LAT/LONG or bearing and distance from the nearest significant point, as described in DEP/.";
	public static String b18talt = "ICAO four letter indicator(s) for take-off alternate, as specified in Doc 7910, Location Indicators, or name of take-off alternate aerodrome, if no indicator is allocated. For aerodromes not listed in the relevant AIP, indicate located in LAT/LONG or bearing and distance from the nearest significant point, as described in DEP/.";
	
	public static String b18rif = "The route details to the revised destination aerodrome, following by the ICAO four-letter location indicator of the aerodrome. The revised route is subject to reclearance in flight.\n\n" +
			"e.g., RIF/DTA HEC KLAX or RIF/ESP G94 CLA YPPH";
	public static String b18rmk = "Other reasons for special handling by ATS. Any other plain language remarks when required by the appropriate ATS authority or deemed necessary.";
	
	public static String b19a = "4 NUMERICS giving the fuel endurance in hours and minutes.";
	public static String b19b = "1, 2 or 3 NUMERICS giving the total " +
			"number of persons on board, when so prescribed by " +
			"the appropriate ATS authority.";
	
	public static String b19cuhf = "If frequency 243.0 MHz (UHF) is available.";
	public static String b19cvhf = "If frequency 121.5 MHz (VHF) is available.";
	public static String b19celt = "If emergency location beacon-aircraft (ELBA) is available.";
	
	public static String b19dp = "If polar survival equipment is carried.";
	public static String b19dd = "If desert survival equipment is carried.";
	public static String b19dm = "If maritime survival equipment is carried.";
	public static String b19dj = "If jungle survival equipment is carried.";

	public static String b19el = "If the life jackets are equipped with lights.";
	public static String b19ef = "If they are equipped with fluorscein.";
	public static String b19eu = "If any life jacket radio is equipped with UHF on frequency 243.0 MHz.";
	public static String b19ev = "If any life jacket radio is equipped with VHF on frequency 121.5 MHz.";

	public static String b19num = "2 NUMERICS giving the number of dinghies carried.";
	public static String b19cap = "3 NUMERICS giving the total capacity, in persons carried, all of dinghies.";
	public static String b19col = "The colour of the dinghies.";
	public static String b19air = "The colour of the aircraft.\n" +
			"Significant markings (this may include the aircraft registration).";
	public static String b19rmk = "Plain language indicating any other survival " +
			"equipment carried and any other useful remarks.";
	public static String b19pil = "The name of the pilot-in-command.";
	
	public static String b20 = "Alerting Search and Rescue Information\n\n" +
			"This field consists of the following specified sequence of " +
			"elements separated by spaces. Any information not available " +
			"should be shown as 'NIL' or 'NOT KNOWN' and not simply omitted.";
	
	public static String b21 = "Radio Failure Information\n\n" +
			"This field consist of the following specified sequence of " +
			"elements preceded by a single hypen and separated by spaces. " +
			"Any information not available is to be shown as 'NIL' or " +
			"'NOT KNOWN' and not simply omitted.\n\n" +
			"(a) Time of Last Two-way Contact : 4 NUMERICS giving the time of the last two-way contact with the aircraft.\n" +
			"(b) Frequency of Last Contact : NUMERICS as necessary giving the transmitting/receiving frequency of the last two-way contact with the aircraft.\n" +
			"(c) Last Reported Position : The last reported position expressed in one of the data conventions of 1.6 of this Appendix.\n" +
			"(d) Time at Last Reported Position : 4 NUMERICS giving the time at the last reported position.\n" +
			"(e) Remaining COM Capability : LETTERS as necessary identifying the remaining COM capability of the aircraft, " +
			"if known, using the convention of Filed Type 10, or in plain language.\n" +
			"(f) Any Necessary Remarks : Plain language text as necessary.";
			
			String b22 = "Field Indicator\n\n" +
			"ONE OR TWO NUMERICS giving the type number of the " +
			"field to be amanded.\n\n" +
			"Amended Data\n\n" +
			"The complete and amended data of the field indicated in " +
			"(a), constructed as specified for that field.\n\n" +
			"Separated by OBLIQUE STROKE.";
	
	public static String bSpace = "Space Reserved\n\ngiving for additional requirements.";
	public static String bOthInf = "Other Information";

	
	void t3a(Text t3a) {
		sh.textStyle(t3a, 40, 3, SWT.LEFT, SWT.CENTER, sh.letter, b3a, false);
	}
	
	void t3(Text t3a, Text t3b, Text t3c) {
		t3a(t3a);
		sh.textStyle(t3b, 120, 12, SWT.LEFT, SWT.CENTER, sh.alphaNumStroke, b3b, true);
		sh.textStyle(t3c, 120, 12, SWT.LEFT, SWT.CENTER, sh.alphaNumStroke, b3c, true);
	}
	
	void t3ALR(Text t3a, Text t3b, Text t3c) {
		sh.textStyle(t3a, 40, 3, SWT.LEFT, SWT.TOP, sh.letter, b3a, false);
		sh.textStyle(t3b, 120, 12, SWT.LEFT, SWT.TOP, sh.alphaNumStroke, b3b, true);
		sh.textStyle(t3c, 120, 12, SWT.LEFT, SWT.TOP, sh.alphaNumStroke, b3c, true);
	}
	
	void t5(Text t5a, Text t5b, Text t5c) {
		sh.textStyle(t5a, 80, 10, SWT.LEFT, SWT.TOP, sh.letter, b5a, false);
		sh.textStyle(t5b, 100, 8, SWT.LEFT, SWT.TOP, sh.letter, b5b, true);
		sh.textAreaStyle(t5c, 150, 35, SWT.LEFT, SWT.TOP, sh.upper, b5c);
	}
	
	void t7(Text t7a, Button t7b, Text t7c) {
		sh.textStyle(t7a, 80, 7, SWT.LEFT, SWT.CENTER, sh.alphanum, b7a, true);
		sh.buttonRCStyle(t7b, "A", b7b, 40);//, 25);
		sh.textStyle(t7c, 40, 4, SWT.LEFT, SWT.CENTER, sh.numeric, b7c, true);
	}
	
	void t8(Text t8a, Text t8b) {
		sh.textStyle(t8a, 20, 1, SWT.LEFT, SWT.LEFT, sh.letter, b8a, false);
		sh.textStyle(t8b, 20, 1, SWT.LEFT, SWT.LEFT, sh.letter, b8b, false);
	}
	
	void t9b(Text t9b) {
		sh.textStyle(t9b, 40, 4, SWT.LEFT, SWT.LEFT, sh.alphanum, b9b, true);
	}

	void t9(Text t9a, Text t9b, Text t9c) {
		sh.textStyle(t9a, 20, 2, SWT.LEFT, SWT.LEFT, sh.numeric, b9a, true);
		t9b(t9b);
		sh.textStyle(t9c, 20, 1, SWT.LEFT, SWT.LEFT, sh.letter, b9c, false);
	}
	
	void t10(Text t10a, Text t10b) {
		sh.textStyle(t10a, 170, 200, SWT.LEFT, SWT.LEFT, sh.alphanum, b10a, true);
		sh.textStyle(t10b, 150, 20, SWT.LEFT, SWT.LEFT, sh.alphanum, b10b, true);
		t10a.setEditable(false);
		t10b.setEditable(false);
	}
	
	void t13a(Text t13a) {
		sh.textStyle(t13a, 50, 4, SWT.LEFT, SWT.LEFT, sh.letter, b13a, true);
	}
	
	void t13b(Text t13b) {
		sh.textStyle(t13b, 40, 4, SWT.LEFT, SWT.LEFT, sh.numeric, b13b, true);
	}
	
	void t13(Text t13a, Text t13b) {
		t13a(t13a);
		t13b(t13b);
	}
	
	void t14(Text t14a, Text t14b, Text t14c, Text t14d, Text t14e) {
		sh.textStyle(t14a, 50, 5, SWT.LEFT, SWT.LEFT, sh.alphanum, b14a, true);
		sh.textStyle(t14b, 40, 4, SWT.LEFT, SWT.LEFT, sh.numeric, b14b, true);		
		sh.textStyle(t14c, 50, 5, SWT.LEFT, SWT.LEFT, sh.alphanum, b14c, true);
		sh.textStyle(t14d, 50, 5, SWT.LEFT, SWT.LEFT, sh.alphanum, b14d, true);
		sh.textStyle(t14e, 10, 1, SWT.LEFT, SWT.LEFT, sh.letter, b14e, true);
	}
	
	void t15(Text t15a, Text t15b, Text t15c) {
		sh.textStyle(t15a, 50, 5, SWT.TOP, SWT.LEFT, sh.alphanum, b15a, true);
		sh.textStyle(t15b, 50, 5, SWT.TOP, SWT.LEFT, sh.alphanum, b15b, true);
		sh.textAreaStyle(t15c, 300, 70, SWT.TOP, SWT.LEFT, sh.upper, b15c);//50
	}
	
	void t16a(Text t16a) {
		sh.textStyle(t16a, 50, 4, SWT.LEFT, SWT.CENTER, sh.letter, b16a, true);
	}
	
	void t16b(Text t16b) {
		sh.textStyle(t16b, 40, 4, SWT.LEFT, SWT.CENTER, sh.numeric, b16b, true);
	}
	
	void t16(Text t16a, Text t16b, Text t16c, Text t16d) {
		t16a(t16a);
		t16b(t16b);
		sh.textStyle(t16c, 50, 4, SWT.LEFT, SWT.CENTER, sh.letter, b16c, true);
		sh.textStyle(t16d, 50, 4, SWT.LEFT, SWT.CENTER, sh.letter, b16d, true);
	}
	
	void t17(Text t17a, Text t17b, Text t17c) {
		sh.textStyle(t17a, 40, 4, SWT.LEFT, SWT.TOP, sh.letter, b17a, true);
		sh.textStyle(t17b, 40, 4, SWT.LEFT, SWT.TOP, sh.numeric, b17b, true);
		sh.textAreaStyle(t17c, 300, 50, SWT.LEFT, SWT.TOP, sh.upper, b17c);
	}
	
	void t18Reg(String str,Text tReg) {
		int iWidth=0;
		if (str.compareToIgnoreCase("fpl")==0 || str.compareToIgnoreCase("")==0) iWidth=170;
		else iWidth=200;
		sh.textStyle(tReg, iWidth, 7, SWT.TOP, SWT.LEFT, sh.alphanum, b18reg, true);
	}
	
	void t18Dof(Text tDof) {
		sh.textStyle(tDof, 200, 6, SWT.TOP, SWT.LEFT, sh.numeric, b18dof, true);
	}
	
	void t18Opr(Text tOpr) {
		sh.textAreaStyle(tOpr, 200, 50, SWT.LEFT, SWT.TOP, sh.upper, b18opr);
	}
	
	void t18Pbn(Text tPbn) {
		sh.textStyle(tPbn, 170, 16, SWT.LEFT, SWT.TOP, sh.alphanum, b18pbn, true);
		tPbn.setEditable(false);
	}
	
	void t18Sel(Text tSel) {
		sh.textStyle(tSel, 200, 4, SWT.TOP, SWT.LEFT, sh.letter, b18sel, true);
	}
	
	void tDof(Text tDof) {
		sh.textStyle(tDof, 60, 6, SWT.TOP, SWT.LEFT, sh.numeric, b18dof, true);
	}
	
	void t18(String str,Text tNav, Text tCom, Text tDat, Text tSur, Text tDep, Text tDest, Text tDof, Text tReg, Text tEet, Text tSel,
			Text tTyp, Text tCode, Text tDle, Text tOpr, Text tOrgn, Text tPer, Text tAltn, Text tRalt, Text tTalt, Text tRif, Text tRmk, 
			Text tPbn, Text tSts) {
		sh.textAreaStyle(tSts, 150, 50, SWT.LEFT, SWT.TOP, sh.letterSpace, b18sts);
		tSts.setEditable(false);
		
		t18Pbn(tPbn);
		sh.textAreaStyle(tNav, 200, 50, SWT.LEFT, SWT.TOP, sh.upper, b18nav);

		sh.textAreaStyle(tCom, 200, 50, SWT.LEFT, SWT.TOP, sh.upper, b18com);
		sh.textAreaStyle(tDat, 200, 50, SWT.LEFT, SWT.TOP, sh.upper, b18dat);
		sh.textAreaStyle(tSur, 200, 50, SWT.LEFT, SWT.TOP, sh.upper, b18sur);
		
		sh.textAreaStyle(tDep, 200, 50, SWT.LEFT, SWT.TOP, sh.upper, b18dep);
		sh.textAreaStyle(tDest, 200, 50, SWT.LEFT, SWT.TOP, sh.upper, b18dest);
		t18Dof(tDof);
		
		t18Reg(str,tReg);
		sh.textAreaStyle(tEet, 200, 50, SWT.LEFT, SWT.TOP, sh.alphaNumSpace, b18eet);
		t18Sel(tSel);		
		
		sh.textAreaStyle(tTyp, 200, 50, SWT.LEFT, SWT.TOP, sh.upper, b18typ);
		sh.textStyle(tCode, 200, 6, SWT.TOP, SWT.LEFT, sh.hexadecimal, b18code, true);
		sh.textAreaStyle(tDle, 200, 50, SWT.LEFT, SWT.TOP, sh.alphaNumSpace, b18dle);
		
		t18Opr(tOpr);
		sh.textAreaStyle(tOrgn, 200, 50, SWT.LEFT, SWT.TOP, sh.upper, b18orgn);
		sh.textStyle(tPer, 200, 1, SWT.TOP, SWT.LEFT, sh.per, b18per, true);		
		
		sh.textAreaStyle(tAltn, 200, 50, SWT.LEFT, SWT.TOP, sh.upper, b18altn);
		sh.textAreaStyle(tRalt, 200, 50, SWT.LEFT, SWT.TOP, sh.upper, b18ralt);
		sh.textAreaStyle(tTalt, 200, 50, SWT.LEFT, SWT.TOP, sh.upper, b18talt);
		
		sh.textAreaStyle(tRif, 200, 50, SWT.LEFT, SWT.TOP, sh.upper, b18rif);
		sh.textAreaStyle(tRmk, 200, 50, SWT.LEFT, SWT.TOP, sh.upper, b18rmk);
	}
	
	void t19(Text t19a, Text t19b, Text t19cUhf, Text t19cVhf, Text t19cElt, Text t19dP, Text t19dD, Text t19dM, Text t19dJ, 
			Text t19eL, Text t19eF, Text t19eU, Text t19eV, Text t19Num, Text t19Cap, Text t19Col, Text t19Air, Text t19Rem, Text t19Pil) {
		
		sh.textStyle(t19a, 40, 4, SWT.LEFT, SWT.CENTER, sh.numeric, b19a, true);
		sh.textStyle(t19b, 30, 3, SWT.LEFT, SWT.CENTER, sh.numeric, b19b, true);
		
		sh.textStyle(t19cUhf, 10, 4, SWT.LEFT, SWT.CENTER, sh.alphaText, b19cuhf, true);
		sh.textStyle(t19cVhf, 10, 4, SWT.LEFT, SWT.CENTER, sh.alphaText, b19cvhf, true);
		sh.textStyle(t19cElt, 10, 4, SWT.LEFT, SWT.CENTER, sh.alphaText, b19celt, true);

		sh.textStyle(t19dP, 10, 4, SWT.LEFT, SWT.CENTER, sh.alphaText, b19dp, true);
		sh.textStyle(t19dD, 10, 4, SWT.LEFT, SWT.CENTER, sh.alphaText, b19dd, true);
		sh.textStyle(t19dM, 10, 4, SWT.LEFT, SWT.CENTER, sh.alphaText, b19dm, true);
		sh.textStyle(t19dJ, 10, 4, SWT.LEFT, SWT.CENTER, sh.alphaText, b19dj, true);

		sh.textStyle(t19eL, 10, 4, SWT.LEFT, SWT.CENTER, sh.alphaText, b19el, true);
		sh.textStyle(t19eF, 10, 4, SWT.LEFT, SWT.CENTER, sh.alphaText, b19ef, true);
		sh.textStyle(t19eU, 10, 4, SWT.LEFT, SWT.CENTER, sh.alphaText, b19eu, true);
		sh.textStyle(t19eV, 10, 4, SWT.LEFT, SWT.CENTER, sh.alphaText, b19ev, true);
		
		sh.textStyle(t19Num, 20, 2, SWT.TOP, SWT.LEFT, sh.numeric, b19num, true);
		sh.textStyle(t19Cap, 30, 3, SWT.TOP, SWT.LEFT, sh.numeric, b19cap, true);
		sh.textStyle(t19Col, 250, 100, SWT.TOP, SWT.LEFT, sh.upper, b19col, true);
		sh.textStyle(t19Air, 600, 69, SWT.TOP, SWT.LEFT, sh.upper, b19air, true);
		sh.textStyle(t19Rem, 600, 69, SWT.TOP, SWT.LEFT, sh.upper, b19rmk, true);
		sh.textStyle(t19Pil, 600, 55, SWT.TOP, SWT.LEFT, sh.upper, b19pil, true);
	}

	void t20(Text t20) {
		sh.textAreaStyle(t20, 610, 50, SWT.LEFT, SWT.CENTER, sh.upper, b20);
	}

	void t21(Text t21) {
		sh.textAreaStyle(t21, 675, 70, SWT.LEFT, SWT.CENTER, sh.upper, b21);
	}

	void t22(Text t22) {
		sh.textAreaStyle(t22, 390, 75, SWT.LEFT, SWT.CENTER, sh.upper, b22);
	}

	void tSpace(Text tSpace) {
		sh.textAreaStyle(tSpace, 630, 50, SWT.LEFT, SWT.CENTER, sh.upper, bSpace);
	}

	void tFreetext(Text text) {
		rff.readConfiguration();
//		sh.textAreaStyle(text, 900, 250, SWT.LEFT, SWT.LEFT, sh.upper, "Free text message");
		sh.textAreaStyle(text, (FormTemplates.iWidthComp-60), 250, SWT.LEFT, SWT.LEFT, sh.upper, "Free text message");
		Font font = new Font(TeleSplashScreen2016IP.display, new FontData(rff.getFontface(), rff.getFontsize(), SWT.NORMAL));
		text.setFont(font);
	}
	
	void tFb(Text tFiledBy) {
		sh.textStyle(tFiledBy, 150, 20, SWT.LEFT, SWT.LEFT, sh.upper, bFiled, true);
	}
	
	//COMPONENT SETTING
	public void setCompFree(Text tFree, Text tFb) {
		tFreetext(tFree);
		tFb(tFb);
	}
	
	public void setCompALR(Text t3a,Text t3b,Text t3c,Text t5a,Text t5b,Text t5c,Text t7a,Button t7b,Text t7c,Text t8a,Text t8b,
			Text t9a,Text t9b,Text t9c,Text t10a,Text t10b,Text t13a,Text t13b,Text t15a,Text t15b,Text t15c,Text t16a,Text t16b,
			Text t16c,Text t16d,Text tNav,Text tCom,Text tDat,Text tSur,Text tDep,Text tDest,Text tDof,Text tReg,Text tEet,Text tSel,
			Text tTyp,Text tCode,Text tDle,Text tOpr,Text tOrgn,Text tPer,Text tAltn,Text tRalt,Text tTalt,Text tRif,Text tRmk,Text tPbn,
			Text tSts,Text t19a,Text t19b,Text t19cUhf,Text t19cVhf,Text t19cElt,Text t19dP,Text t19dD,Text t19dM,Text t19dJ,
			Text t19eL,Text t19eF,Text t19eU,Text t19eV,Text t19Num,Text t19Cap,Text t19Col,Text t19Air,Text t19Rem,Text t19Pil,
			Text t20,Text tFb) {
		t3ALR(t3a,t3b,t3c);
		t5(t5a,t5b,t5c);
		t7(t7a,t7b,t7c);
		t8(t8a,t8b);
		t9(t9a,t9b,t9c);
		t10(t10a,t10b);
		t13(t13a,t13b);
		t15(t15a,t15b,t15c);
		t16(t16a,t16b,t16c,t16d);
		t18("ALR",tNav,tCom,tDat,tSur,tDep,tDest,tDof,tReg,tEet,tSel,tTyp,tCode,tDle,tOpr,tOrgn,tPer,tAltn,tRalt,tTalt,tRif,tRmk,tPbn,tSts);
		t19(t19a,t19b,t19cUhf,t19cVhf,t19cElt,t19dP,t19dD,t19dM,t19dJ,t19eL,t19eF,t19eU,t19eV,t19Num,t19Cap,t19Col,t19Air,t19Rem,t19Pil);
		t20(t20);
		tFb(tFb);
	}
	
	public void setCompRCF(Text t3a,Text t3b,Text t3c,Text t7a,Button t7b,Text t7c,Text t21,Text tFb) {
		t3(t3a,t3b,t3c);
		t7(t7a,t7b,t7c);
		t21(t21);
		tFb(tFb);
	}
	
	public void setCompFPL(Text t3a,Text t3b,Text t3c,Text t7a,Button t7b,Text t7c,Text t8a,Text t8b,Text t9a,Text t9b,Text t9c,
			Text t10a,Text t10b,Text t13a,Text t13b,Text t15a,Text t15b,Text t15c,Text t16a,Text t16b,Text t16c,Text t16d,
			Text tNav,Text tCom,Text tDat,Text tSur,Text tDep,Text tDest,Text tDof,Text tReg,Text tEet,Text tSel,Text tTyp,Text tCode,
			Text tDle,Text tOpr,Text tOrgn,Text tPer,Text tAltn,Text tRalt,Text tTalt,Text tRif,Text tRmk,Text tPbn,Text tSts,
			Text t19a,Text t19b,Text t19cUhf,Text t19cVhf,Text t19cElt,Text t19dP,Text t19dD,Text t19dM,Text t19dJ,
			Text t19eL,Text t19eF,Text t19eU,Text t19eV,Text t19Num,Text t19Cap,Text t19Col,Text t19Air,Text t19Rem,Text t19Pil,
			Text tSpace,Text tFb) {
		t3(t3a,t3b,t3c);
		t7(t7a,t7b,t7c);
		t8(t8a,t8b);
		t9(t9a,t9b,t9c);
		t10(t10a,t10b);
		t13(t13a,t13b);
		t15(t15a,t15b,t15c);
		t16(t16a,t16b,t16c,t16d);
		t18("FPL",tNav,tCom,tDat,tSur,tDep,tDest,tDof,tReg,tEet,tSel,tTyp,tCode,tDle,tOpr,tOrgn,tPer,tAltn,tRalt,tTalt,tRif,tRmk,tPbn,tSts);
		t19(t19a,t19b,t19cUhf,t19cVhf,t19cElt,t19dP,t19dD,t19dM,t19dJ,t19eL,t19eF,t19eU,t19eV,t19Num,t19Cap,t19Col,t19Air,t19Rem,t19Pil);
		tSpace(tSpace);
		tFb(tFb);
	}
	
	public void setCompMiniFPL(Text t3a,Text t3b,Text t3c,Text t7a,Button t7b,Text t7c,Text t13a,Text t13b,Text t16a,Text t16b,Text t16c,
			Text t16d,Text tDof,Text tFb) {
		t3(t3a,t3b,t3c);
		t7(t7a,t7b,t7c);
		t13(t13a,t13b);
		t16(t16a,t16b,t16c,t16d);
		tDof(tDof);
		tFb(tFb);
	}
	
	public void setCompARR(Text t3a,Text t3b,Text t3c,Text t7a,Button t7b,Text t7c,Text t13a,Text t13b,Text t17a,Text t17b,
			Text t17c,Text tFb) {
		t3(t3a,t3b,t3c);
		t7(t7a,t7b,t7c);
		t13(t13a,t13b);
		t17(t17a,t17b,t17c);
		tFb(tFb);
	}
	
	public void setCompCHG(Text t3a,Text t3b,Text t3c,Text t7a,Button t7b,Text t7c,Text t13a,Text t13b,Text t16a,Text t16b,Text t16c,
			Text t16d,Text tDof,Text t22,Text tFb) {
		t3(t3a,t3b,t3c);
		t7(t7a,t7b,t7c);
		t13(t13a,t13b);
		t16(t16a,t16b,t16c,t16d);
		tDof(tDof);
		t22(t22);
		tFb(tFb);
	}
	
	public void setCompCDN(Text t3a,Text t3b,Text t3c,Text t7a,Button t7b,Text t7c,Text t13a,Text t13b,Text t16a,Text t16b,Text t16c,
			Text t16d,Text t22,Text tFb) {
		t3(t3a,t3b,t3c);
		t7(t7a,t7b,t7c);
		t13(t13a,t13b);
		t16(t16a,t16b,t16c,t16d);
		t22(t22);
		tFb(tFb);
	}
	
	public void setCompCPL(Text t3a,Text t3b,Text t3c,Text t7a,Button t7b,Text t7c,Text t8a,Text t8b,Text t9a,Text t9b,Text t9c,
			Text t10a,Text t10b,Text t13a,Text t13b,Text t14a,Text t14b,Text t14c,Text t14d,Text t14e,Text t15a,Text t15b,Text t15c,
			Text t16a,Text t16b,Text t16c,Text t16d,Text tNav,Text tCom,Text tDat,Text tSur,Text tDep,Text tDest,Text tDof,Text tReg,
			Text tEet,Text tSel,Text tTyp,Text tCode,Text tDle,Text tOpr,Text tOrgn,Text tPer,Text tAltn,Text tRalt,Text tTalt,Text tRif,
			Text tRmk,Text tPbn,Text tSts,Text tFb) {
		t3(t3a,t3b,t3c);
		t7(t7a,t7b,t7c);
		t8(t8a,t8b);
		t9(t9a,t9b,t9c);
		t10(t10a,t10b);
		t13(t13a,t13b);
		t14(t14a,t14b,t14c,t14d,t14e);
		t15(t15a,t15b,t15c);
		t16(t16a,t16b,t16c,t16d);
		t18("CPL",tNav,tCom,tDat,tSur,tDep,tDest,tDof,tReg,tEet,tSel,tTyp,tCode,tDle,tOpr,tOrgn,tPer,tAltn,tRalt,tTalt,tRif,tRmk,tPbn,tSts);
		tFb(tFb);
	}
	
	public void setCompEST(Text t3a,Text t3b,Text t3c,Text t7a,Button t7b,Text t7c,Text t13a,Text t13b,Text t14a,Text t14b,Text t14c,
			Text t14d,Text t14e,Text t16a,Text t16b,Text t16c,Text t16d,Text tFb) {
		t3(t3a,t3b,t3c);
		t7(t7a,t7b,t7c);
		t13(t13a,t13b);
		t14(t14a,t14b,t14c,t14d,t14e);
		t16(t16a,t16b,t16c,t16d);
		tFb(tFb);
	}
	
	public void setCompACP(Text t3a,Text t3b,Text t3c,Text t7a,Button t7b,Text t7c,Text t13a,Text t13b,Text t16a,Text t16b,
			Text t16c,Text t16d,Text tFb) {
		t3(t3a,t3b,t3c);
		t7(t7a,t7b,t7c);
		t13(t13a,t13b);
		t16(t16a,t16b,t16c,t16d);
		tFb(tFb);
	}
	
	public void setCompLAM(Text t3a,Text t3b,Text t3c,Text tFb) {
		t3(t3a,t3b,t3c);
		tFb(tFb);
	}
	
	public void setCompSPL(Text t3a,Text t3b,Text t3c,Text t7a,Button t7b,Text t7c,Text t13a,Text t13b,Text t16a,Text t16b,Text t16c,Text t16d,
			Text tNav,Text tCom,Text tDat,Text tSur,Text tDep,Text tDest,Text tDof,Text tReg,Text tEet,Text tSel,Text tTyp,Text tCode,
			Text tDle,Text tOpr,Text tOrgn,Text tPer,Text tAltn,Text tRalt,Text tTalt,Text tRif,Text tRmk,Text tPbn,Text tSts,Text t19a,
			Text t19b,Text t19cUhf,Text t19cVhf,Text t19cElt,Text t19dP,Text t19dD,Text t19dM,Text t19dJ,Text t19eL,
			Text t19eF,Text t19eU,Text t19eV,Text t19Num,Text t19Cap,Text t19Col,Text t19Air,Text t19Rem,Text t19Pil,Text tFb) {
		t3(t3a,t3b,t3c);
		t7(t7a,t7b,t7c);
		t13(t13a,t13b);
		t16(t16a,t16b,t16c,t16d);
		t18("SPL",tNav,tCom,tDat,tSur,tDep,tDest,tDof,tReg,tEet,tSel,tTyp,tCode,tDle,tOpr,tOrgn,tPer,tAltn,tRalt,tTalt,tRif,tRmk,tPbn,tSts);
		t19(t19a,t19b,t19cUhf,t19cVhf,t19cElt,t19dP,t19dD,t19dM,t19dJ,t19eL,t19eF,t19eU,t19eV,t19Num,t19Cap,t19Col,t19Air,t19Rem,t19Pil);
		tFb(tFb);
	}

	public void setCompReg(Text tReg,Text t9b,Text t10a,Text t10b,Text tOpr,Text tPbn,Text tSel) {
		t18Reg("",tReg);
		t9b(t9b);
		t10(t10a,t10b);
		t18Opr(tOpr);
		t18Pbn(tPbn);
		t18Sel(tSel);
	}
}
