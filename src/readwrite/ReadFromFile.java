package readwrite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import displays.MainForm;

public class ReadFromFile {
	
	String contentStr="";
	String lineStr = null;
	private static String[] arrConf = new String[100000];
	private static String width_mainform="0";
	
	public ReadFromFile() {
		
	}
	
	public static String ReadInputFile1(String filename) { //for open password only (/tp/pass.txt)
		String lineStr="";
		try {
			File myFile = new File(filename);
		    FileReader fileReader = new FileReader(myFile);

		    BufferedReader buffReader = new BufferedReader(fileReader);
		    
		    lineStr = buffReader.readLine();
//		    lineStr+="\n";
		    buffReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lineStr;
	}
	
	public static String ReadInputFile2(String filename) { //for print
		String lineStr="",content="";
		try {
			File myFile = new File(filename);
		    FileReader fileReader = new FileReader(myFile);
		    BufferedReader buffReader = new BufferedReader(fileReader);
		    while ((lineStr = buffReader.readLine()) != null) {
		    	content+=lineStr+"\n";		    	
		    }
		    buffReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
	
	public static void readConfiguration() {
		try {
			File myFile = new File(MainForm.sFolder+"configuration.txt");
			FileReader fileReader = new FileReader(myFile);
			BufferedReader buffReader = new BufferedReader(fileReader);
			String line = null;
			
			while ((line = buffReader.readLine()) != null) {
				parsingConfiguration(line);
			}	
			buffReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void parsingConfiguration(String line) {
		 String tmp;
		 String[] tmp1 = new String[100];
		 String[] tmp2 = new String[100];
		 String[] data = new String[100];
		 String[] sub = new String[100];
		 
		 sub = line.split("\n");
		 for (int x = 0; x < sub.length; x++) {
			 data = sub[x].split("=");
	     }

		 for (int x = 0; x < sub.length; x++) {
			 tmp = sub[x];
			 if (tmp.indexOf("=") != -1) {
				 data = tmp.split("=");
				 tmp1[x] = data[0];
				 tmp2[x] = data[1];
				 
				 // ========== set DATABASE
				 if (tmp1[x].compareToIgnoreCase("url") == 0) { arrConf[0] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("user") == 0) { arrConf[1] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("pass") == 0) { arrConf[2] = tmp2[x]; }

				 // ========== set JUDUL di FORM UTAMA (tanpa continuous printer: atas)
				 else if (tmp1[x].compareToIgnoreCase("title") == 0) { arrConf[3] = tmp2[x]; }

				 // ========== set TINGGI di FORM UTAMA (semakin besar, semakin tinggi)
				 else if (tmp1[x].compareToIgnoreCase("Height_MainForm") == 0) { arrConf[4] = tmp2[x]; }

				 // ========== set LEBAR label di FORM UTAMA (semakin besar, semakin lebar)
				 else if (tmp1[x].compareToIgnoreCase("xline") == 0) { arrConf[22] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xtseq") == 0) { arrConf[23] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xrseq") == 0) { arrConf[24] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xnis") == 0) { arrConf[25] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xqueue") == 0) { arrConf[26] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xrefuse") == 0) { arrConf[27] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xip") == 0) { arrConf[46] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xbaud") == 0) { arrConf[28] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xcode") == 0) { arrConf[29] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xcid") == 0) { arrConf[30] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xdigit") == 0) { arrConf[31] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xfree") == 0) { arrConf[32] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xprintertype") == 0) { arrConf[33] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xSvcMsgGen") == 0) { arrConf[34] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xwarning") == 0) { arrConf[35] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xtime") == 0) { arrConf[36] = tmp2[x]; }
				 
				 // ========== set PDF
				 else if (tmp1[x].compareToIgnoreCase("pdfcmd") == 0) { arrConf[5] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("pdfline") == 0) { arrConf[6] = tmp2[x]; }

				 // ========== set DOC
				 else if (tmp1[x].compareToIgnoreCase("wordcmd") == 0) { arrConf[7] = tmp2[x]; }

				 // ========== set FUNGSI
				 else if (tmp1[x].compareToIgnoreCase("discard") == 0) { arrConf[8] = tmp2[x]; }

				 // ========== set KONFIGURASI printer
				 else if (tmp1[x].compareToIgnoreCase("print") == 0) { arrConf[9] = tmp2[x]; }

				 // ========== set OTOMATIS checklist pada Send at
				 else if (tmp1[x].compareToIgnoreCase("sendat") == 0) { arrConf[10] = tmp2[x]; }

				 // ========== set FONT untuk textbox Freetext
				 else if (tmp1[x].compareToIgnoreCase("fontface") == 0) { arrConf[11] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("fontsize") == 0) { arrConf[12] = tmp2[x]; }

				 // ========== set JUMLAH karakter berita yang akan dikirim
				 else if (tmp1[x].compareToIgnoreCase("length") == 0) { arrConf[13] = tmp2[x]; }

				 // ========== set TEMPLATE
				 else if (tmp1[x].compareToIgnoreCase("Height_Template") == 0) { arrConf[14] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("Width_Template") == 0) { arrConf[15] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xLoc_Template") == 0) { arrConf[16] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("yLoc_Template") == 0) { arrConf[17] = tmp2[x]; }

				 else if (tmp1[x].compareToIgnoreCase("sashTOP") == 0) { arrConf[37] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sashMID") == 0) { arrConf[38] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sashBOT") == 0) { arrConf[39] = tmp2[x]; }
				 
				 else if (tmp1[x].compareToIgnoreCase("sashTOP_FPL") == 0) { arrConf[40] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sashMID_FPL") == 0) { arrConf[41] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sashBOT_FPL") == 0) { arrConf[42] = tmp2[x]; }
				 
				 else if (tmp1[x].compareToIgnoreCase("sashTOP_AMO") == 0) { arrConf[43] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sashMID_AMO") == 0) { arrConf[44] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sashBOT_AMO") == 0) { arrConf[45] = tmp2[x]; }

				 // ========== set LEBAR textbox OriRef
				 else if (tmp1[x].compareToIgnoreCase("wtextoriref") == 0) { arrConf[18] = tmp2[x]; }

				 // ========== set TABLE ATS: tinggi,nama kolom dan panjang kolom
				 else if (tmp1[x].compareToIgnoreCase("HeightTableATS") == 0) { arrConf[19] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wATS") == 0) { arrConf[20] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sATS") == 0) { arrConf[21] = tmp2[x]; }
				 //22-36 di set LEBAR label di FORM UTAMA
				 //37-45 di set TEMPLATE
				 else if (tmp1[x].compareToIgnoreCase("Width_MainForm") == 0) { width_mainform = tmp2[x]; }

			 }
		 }
	}

	// ========== set DATABASE
	public static String getDBUrl() { return arrConf[0]; }
	public static String getDBUser() { return arrConf[1]; }
	public static String getDBPass() { return arrConf[2]; }

	// ========== set JUDUL di FORM UTAMA (tanpa continuous printer: atas)
	public static String getTitle() { return arrConf[3]; }

	// ========== set TINGGI di FORM UTAMA (semakin besar, semakin tinggi)
	public static int getHeight_MainForm() { return Integer.parseInt(arrConf[4]); }
	public static int getWidth_MainForm() { 
		return Integer.parseInt(width_mainform);
	}

	// ========== set LEBAR label di FORM UTAMA (semakin besar, semakin lebar)
	public static int getXline() { if (arrConf[22]==null) arrConf[22]="0"; return Integer.parseInt(arrConf[22]); }
	public static int getXtseq() { if (arrConf[23]==null) arrConf[23]="0"; return Integer.parseInt(arrConf[23]); }
	public static int getXrseq() { if (arrConf[24]==null) arrConf[24]="0"; return Integer.parseInt(arrConf[24]); }
	public static int getXnis() { if (arrConf[25]==null) arrConf[25]="0"; return Integer.parseInt(arrConf[25]); }
	public static int getXqueue() { if (arrConf[26]==null) arrConf[26]="0"; return Integer.parseInt(arrConf[26]); }
	public static int getXrefuse() { if (arrConf[27]==null) arrConf[27]="0"; return Integer.parseInt(arrConf[27]); }
	public static int getXip() { if (arrConf[46]==null) arrConf[46]="0"; return Integer.parseInt(arrConf[46]); }
	public static int getXbaud() { if (arrConf[28]==null) arrConf[28]="0"; return Integer.parseInt(arrConf[28]); }
	public static int getXcode() { if (arrConf[29]==null) arrConf[29]="0"; return Integer.parseInt(arrConf[29]); }
	public static int getXcid() { if (arrConf[30]==null) arrConf[30]="0"; return Integer.parseInt(arrConf[30]); }
	public static int getXdigit() { if (arrConf[31]==null) arrConf[31]="0"; return Integer.parseInt(arrConf[31]); }
	public static int getXfree() { if (arrConf[32]==null) arrConf[32]="0"; return Integer.parseInt(arrConf[32]); }
	public static int getXprintertype() { if (arrConf[33]==null) arrConf[33]="0"; return Integer.parseInt(arrConf[33]); }
	public static int getWidthSrvMsgGen() { if (arrConf[34]==null) arrConf[34]="0"; return Integer.parseInt(arrConf[34]); }
	public static int getXwarning() { if (arrConf[35]==null) arrConf[35]="0"; return Integer.parseInt(arrConf[35]); }
	public static int getXtime() { if (arrConf[36]==null) arrConf[36]="0"; return Integer.parseInt(arrConf[36]); }
	
	// ========== set PDF
	public static String getPDFCmd() { return arrConf[5]; }
	public static int getPDFLine() { if (arrConf[6]==null) arrConf[6]="0"; return Integer.parseInt(arrConf[6]); }

	// ========== set DOC
	public static String getWordCmd() { return arrConf[7]; }

	// ========== set FUNGSI
	public static String getDiscard() { return arrConf[8]; }

	// ========== set KONFIGURASI printer
	public static String getPrint() { return arrConf[9]; }

	// ========== set OTOMATIS checklist pada Send at
	public static String getSendat() { return arrConf[10]; }

	// ========== set FONT untuk textbox Freetext
	public static String getFontface() { return arrConf[11].toUpperCase(); }
	public static int getFontsize() { if (arrConf[12]==null) arrConf[12]="0"; return Integer.parseInt(arrConf[12]); }

	// ========== set JUMLAH karakter berita yang akan dikirim
	public static int getMsglength() { if (arrConf[13]==null) arrConf[13]="0"; return Integer.parseInt(arrConf[13]); }

	// ========== set TEMPLATE
	public static int getHeight_Template() { return Integer.parseInt(arrConf[14]); }
	public static int getWidth_Template() { return Integer.parseInt(arrConf[15]); }
	public static int getxLoc_Template() { return Integer.parseInt(arrConf[16]); }
	public static int getyLoc_Template() { return Integer.parseInt(arrConf[17]); }

	public static int getSashTOP() { if (arrConf[37]==null) arrConf[37]="0"; return Integer.parseInt(arrConf[37]); }
	public static int getSashMID() { if (arrConf[38]==null) arrConf[38]="0"; return Integer.parseInt(arrConf[38]); }
	public static int getSashBOT() { if (arrConf[39]==null) arrConf[39]="0"; return Integer.parseInt(arrConf[39]); }
	
	public static int getSashTOP_FPL() { if (arrConf[40]==null) arrConf[40]="0"; return Integer.parseInt(arrConf[40]); }
	public static int getSashMID_FPL() { if (arrConf[41]==null) arrConf[41]="0"; return Integer.parseInt(arrConf[41]); }
	public static int getSashBOT_FPL() { if (arrConf[42]==null) arrConf[42]="0"; return Integer.parseInt(arrConf[42]); }
	
	public static int getSashTOP_AMO() { if (arrConf[43]==null) arrConf[43]="0"; return Integer.parseInt(arrConf[43]); }
	public static int getSashMID_AMO() { if (arrConf[44]==null) arrConf[44]="0"; return Integer.parseInt(arrConf[44]); }
	public static int getSashBOT_AMO() { if (arrConf[45]==null) arrConf[45]="0"; return Integer.parseInt(arrConf[45]); }
	
	// ========== set LEBAR textbox OriRef
	public static int getWidthTextOriRef() { if (arrConf[18]==null) arrConf[18]="0"; return Integer.parseInt(arrConf[18]); }

	// ========== set TABLE ATS: tinggi,nama kolom dan panjang kolom
	public static int getHeightTblATS() { return Integer.parseInt(arrConf[19]); }
	public static String getWidthATS() { return arrConf[20]; }
	public static String getColnmATS() { return arrConf[21]; }

	//22-36 di set LEBAR label di FORM UTAMA
	//37-45 di set TEMPLATE
	//46 terusan di set LEBAR label di FORM UTAMA
	
	// PDF page setup - it used to read the configuration.txt file
	int pdfleft=36;
	int pdfright=36;
	int pdftop=90;
	int pdfbottom=36;
	int pdfheight=20;
	int pdfwidth=527;
	int pdfheadheight=773;
	int pdfwidthlandscape=770;
	int pdfheadheightlandscape=540;
	
	public int getPDFLeft() { return pdfleft; }
	public int getPDFRight() { return pdfright; }
	public int getPDFTop() { return pdftop; }
	public int getPDFBottom() { return pdfbottom; }
	public int getPDFHeight() { return pdfheight; }
	public int getPDFWidth() { return pdfwidth; }
	public int getPDFHeadheight() { return pdfheadheight; }
	public int getPDFWidthlandscape() { return pdfwidthlandscape; }
	public int getPDFHeadheightlandscape() { return pdfheadheightlandscape; }
	
}


//package readwrite;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//
//import displays.MainForm;
//
//public class ReadFromFile {
//	
//	String contentStr="";
//	String lineStr = null;
//	private static String[] arrConf = new String[100000];
//	
//	public ReadFromFile() {
//		
//	}
//	
//	public static String ReadInputFile1(String filename) { //for open password only (/tp/pass.txt)
//		String lineStr="";
//		try {
//			File myFile = new File(filename);
//		    FileReader fileReader = new FileReader(myFile);
//
//		    BufferedReader buffReader = new BufferedReader(fileReader);
//		    
//		    lineStr = buffReader.readLine();
////		    lineStr+="\n";
//		    buffReader.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return lineStr;
//	}
//	
//	public static String ReadInputFile2(String filename) { //for print
//		String lineStr="",content="";
//		try {
//			File myFile = new File(filename);
//		    FileReader fileReader = new FileReader(myFile);
//		    BufferedReader buffReader = new BufferedReader(fileReader);
//		    while ((lineStr = buffReader.readLine()) != null) {
//		    	content+=lineStr+"\n";		    	
//		    }
//		    buffReader.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return content;
//	}
//	
//	public static void readConfiguration() {
//		try {
//			File myFile = new File(MainForm.sFolder+"configuration.txt");
//			FileReader fileReader = new FileReader(myFile);
//			BufferedReader buffReader = new BufferedReader(fileReader);
//			String line = null;
//			
//			while ((line = buffReader.readLine()) != null) {
//				parsingConfiguration(line);
//			}	
//			buffReader.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	static void parsingConfiguration(String line) {
//		 String tmp;
//		 String[] tmp1 = new String[100];
//		 String[] tmp2 = new String[100];
//		 String[] data = new String[100];
//		 String[] sub = new String[100];
//		 
//		 sub = line.split("\n");
//		 for (int x = 0; x < sub.length; x++) {
//			 data = sub[x].split("=");
//	     }
//
//		 for (int x = 0; x < sub.length; x++) {
//			 tmp = sub[x];
//			 if (tmp.indexOf("=") != -1) {
//				 data = tmp.split("=");
//				 tmp1[x] = data[0];
//				 tmp2[x] = data[1];
//				 
//				 // ========== set DATABASE
//				 if (tmp1[x].compareToIgnoreCase("url") == 0) { arrConf[0] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("user") == 0) { arrConf[1] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("pass") == 0) { arrConf[2] = tmp2[x]; }
//
//				 // ========== set JUDUL di FORM UTAMA (tanpa continuous printer: atas)
//				 else if (tmp1[x].compareToIgnoreCase("title") == 0) { arrConf[3] = tmp2[x]; }
//
//				 // ========== set TINGGI di FORM UTAMA (semakin besar, semakin tinggi)
//				 else if (tmp1[x].compareToIgnoreCase("Height_MainForm") == 0) { arrConf[86] = tmp2[x]; }
//
//				 // ========== set LEBAR label di FORM UTAMA (semakin besar, semakin lebar)
//				 else if (tmp1[x].compareToIgnoreCase("xline") == 0) { arrConf[75] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xbaud") == 0) { arrConf[69] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xcode") == 0) { arrConf[76] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xSvcMsgGen") == 0) { arrConf[79] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xcid") == 0) { arrConf[71] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xdigit") == 0) { arrConf[70] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xtseq") == 0) { arrConf[72] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xrseq") == 0) { arrConf[73] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xqueue") == 0) { arrConf[74] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xwarning") == 0) { arrConf[78] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xfree") == 0) { arrConf[80] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xprintertype") == 0) { arrConf[77] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xtime") == 0) { arrConf[68] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xnis") == 0) { arrConf[200] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xrefuse") == 0) { arrConf[300] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xkosong") == 0) { arrConf[400] = tmp2[x]; }
//				 
//				 // ========== set FONT label di FORM UTAMA
////				 else if (tmp1[x].compareToIgnoreCase("font_statusbar") == 0) { arrConf[24] = tmp2[x]; }
//
//				 // ========== set PDF
//				 else if (tmp1[x].compareToIgnoreCase("pdfcmd") == 0) { arrConf[34] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("pdfline") == 0) { arrConf[37] = tmp2[x]; }
//				
//
//				 // ========== set DOC
//				 else if (tmp1[x].compareToIgnoreCase("wordcmd") == 0) { arrConf[56] = tmp2[x]; }
//
//				 // ========== set FUNGSI
//				 else if (tmp1[x].compareToIgnoreCase("discard") == 0) { arrConf[53] = tmp2[x]; }
//
//				 // ========== set KONFIGURASI printer
//				 else if (tmp1[x].compareToIgnoreCase("print") == 0) { arrConf[84] = tmp2[x]; }
//
//				 // ========== set OTOMATIS checklist pada Send at
//				 else if (tmp1[x].compareToIgnoreCase("sendat") == 0) { arrConf[81] = tmp2[x]; }
//
//				 // ========== set FONT untuk textbox Freetext
//				 else if (tmp1[x].compareToIgnoreCase("fontface") == 0) { arrConf[57] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("fontsize") == 0) { arrConf[58] = tmp2[x]; }
//
//				 // ========== set JUMLAH karakter berita yang akan dikirim
//				 else if (tmp1[x].compareToIgnoreCase("length") == 0) { arrConf[54] = tmp2[x]; }
//
//				 // ========== set TEMPLATE
//				 else if (tmp1[x].compareToIgnoreCase("Height_Template") == 0) { arrConf[87] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("Width_Template") == 0) { arrConf[88] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xLoc_Template") == 0) { arrConf[89] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("yLoc_Template") == 0) { arrConf[90] = tmp2[x]; }
//
////				 else if (tmp1[x].compareToIgnoreCase("sashTOP") == 0) { arrConf[21] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("sashMID") == 0) { arrConf[22] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("sashBOT") == 0) { arrConf[23] = tmp2[x]; }
////				 
////				 else if (tmp1[x].compareToIgnoreCase("sashTOP_FPL") == 0) { arrConf[25] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("sashMID_FPL") == 0) { arrConf[26] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("sashBOT_FPL") == 0) { arrConf[27] = tmp2[x]; }
////				 
////				 else if (tmp1[x].compareToIgnoreCase("sashTOP_AMO") == 0) { arrConf[28] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("sashMID_AMO") == 0) { arrConf[29] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("sashBOT_AMO") == 0) { arrConf[30] = tmp2[x]; }
//
//				 // ========== set TINGGI scroll BODY TEMPLATE
////				 else if (tmp1[x].compareToIgnoreCase("FREEATS") == 0) { arrConf[4] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("ALR") == 0) { arrConf[5] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("RCF") == 0) { arrConf[6] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("FPL") == 0) { arrConf[7] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("DLA") == 0) { arrConf[8] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("CHG") == 0) { arrConf[9] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("CNL") == 0) { arrConf[10] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("DEP") == 0) { arrConf[11] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("ARR") == 0) { arrConf[12] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("CDN") == 0) { arrConf[13] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("CPL") == 0) { arrConf[14] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("EST") == 0) { arrConf[15] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("ACP") == 0) { arrConf[16] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("LAM") == 0) { arrConf[17] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("RQP") == 0) { arrConf[18] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("RQS") == 0) { arrConf[19] = tmp2[x]; }
////				 else if (tmp1[x].compareToIgnoreCase("SPL") == 0) { arrConf[20] = tmp2[x]; }
//
//				 // ========== set LEBAR textbox OriRef
//				 else if (tmp1[x].compareToIgnoreCase("wtextoriref") == 0) { arrConf[66] = tmp2[x]; }
//
//				 // ========== set TABLE ATS: tinggi,nama kolom dan panjang kolom
//				 else if (tmp1[x].compareToIgnoreCase("HeightTableATS") == 0) { arrConf[85] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("wATS") == 0) { arrConf[82] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("sATS") == 0) { arrConf[83] = tmp2[x]; }
//			 }
//		 }
//	}
//	
//	
//
//	// ========== set DATABASE
//	public static String getDBUrl() { return arrConf[0]; }
//	public static String getDBUser() { return arrConf[1]; }
//	public static String getDBPass() { return arrConf[2]; }
//
//	// ========== set JUDUL di FORM UTAMA (tanpa continuous printer: atas)
//	public static String getTitle() { return arrConf[3]; }
//
//	// ========== set TINGGI di FORM UTAMA (semakin besar, semakin tinggi)
//	public static int getHeight_MainForm() { return Integer.parseInt(arrConf[86]); }
//
//	// ========== set LEBAR label di FORM UTAMA (semakin besar, semakin lebar)
//	public static int getXline() { if (arrConf[75]==null) arrConf[75]="0"; return Integer.parseInt(arrConf[75]); }
//	public static int getXbaud() { if (arrConf[69]==null) arrConf[69]="0"; return Integer.parseInt(arrConf[69]); }
//	public static int getXcode() { if (arrConf[76]==null) arrConf[76]="0"; return Integer.parseInt(arrConf[76]); }
//	public static int getWidthSrvMsgGen() { if (arrConf[79]==null) arrConf[79]="0"; return Integer.parseInt(arrConf[79]); }
//	public static int getXcid() { if (arrConf[71]==null) arrConf[71]="0"; return Integer.parseInt(arrConf[71]); }
//	public static int getXdigit() { if (arrConf[70]==null) arrConf[70]="0"; return Integer.parseInt(arrConf[70]); }
//	public static int getXtseq() { if (arrConf[72]==null) arrConf[72]="0"; return Integer.parseInt(arrConf[72]); }
//	public static int getXrseq() { if (arrConf[73]==null) arrConf[73]="0"; return Integer.parseInt(arrConf[73]); }
//	public static int getXqueue() { if (arrConf[74]==null) arrConf[74]="0"; return Integer.parseInt(arrConf[74]); }
//	public static int getXwarning() { if (arrConf[78]==null) arrConf[78]="0"; return Integer.parseInt(arrConf[78]); }
//	public static int getXfree() { if (arrConf[80]==null) arrConf[80]="0"; return Integer.parseInt(arrConf[80]); }
//	public static int getXprintertype() { if (arrConf[77]==null) arrConf[77]="0"; return Integer.parseInt(arrConf[77]); }
//	public static int getXtime() { if (arrConf[68]==null) arrConf[68]="0"; return Integer.parseInt(arrConf[68]); }
//	public static int getXnis() { if (arrConf[200]==null) arrConf[200]="0"; return Integer.parseInt(arrConf[200]); }
//	public static int getXrefuse() { if (arrConf[300]==null) arrConf[300]="0"; return Integer.parseInt(arrConf[300]); }
//	public static int getXkosong() { if (arrConf[400]==null) arrConf[400]="0"; return Integer.parseInt(arrConf[400]); }
//	
//	// ========== set FONT label di FORM UTAMA
////	public static int getFontSizeStatusBar() { if (arrConf[24]==null) arrConf[24]="0"; return Integer.parseInt(arrConf[24]); }
//
//	// ========== set PDF
//	public static String getPDFCmd() { return arrConf[34]; }
//	public static int getPDFLine() { if (arrConf[37]==null) arrConf[37]="0"; return Integer.parseInt(arrConf[37]); }
//
//	// ========== set DOC
//	public static String getWordCmd() { return arrConf[56]; }
//
//	// ========== set FUNGSI
//	public static String getDiscard() { return arrConf[53]; }
//
//	// ========== set KONFIGURASI printer
//	public static String getPrint() { return arrConf[84]; }
//
//	// ========== set OTOMATIS checklist pada Send at
//	public static String getSendat() { return arrConf[81]; }
//
//	// ========== set FONT untuk textbox Freetext
//	public static String getFontface() { return arrConf[57].toUpperCase(); }
//	public static int getFontsize() { if (arrConf[58]==null) arrConf[58]="0"; return Integer.parseInt(arrConf[58]); }
//
//	// ========== set JUMLAH karakter berita yang akan dikirim
//	public static int getMsglength() { if (arrConf[54]==null) arrConf[54]="0"; return Integer.parseInt(arrConf[54]); }
//
//	// ========== set TEMPLATE
//	public static int getHeight_Template() { return Integer.parseInt(arrConf[87]); }
//	public static int getWidth_Template() { return Integer.parseInt(arrConf[88]); }
//	public static int getxLoc_Template() { return Integer.parseInt(arrConf[89]); }
//	public static int getyLoc_Template() { return Integer.parseInt(arrConf[90]); }
//
////	public static int getSashTOP() { if (arrConf[21]==null) arrConf[21]="0"; return Integer.parseInt(arrConf[21]); }
////	public static int getSashMID() { if (arrConf[22]==null) arrConf[22]="0"; return Integer.parseInt(arrConf[22]); }
////	public static int getSashBOT() { if (arrConf[23]==null) arrConf[23]="0"; return Integer.parseInt(arrConf[23]); }
////	
////	public static int getSashTOP_FPL() { if (arrConf[25]==null) arrConf[25]="0"; return Integer.parseInt(arrConf[25]); }
////	public static int getSashMID_FPL() { if (arrConf[26]==null) arrConf[26]="0"; return Integer.parseInt(arrConf[26]); }
////	public static int getSashBOT_FPL() { if (arrConf[27]==null) arrConf[27]="0"; return Integer.parseInt(arrConf[27]); }
////	
////	public static int getSashTOP_AMO() { if (arrConf[28]==null) arrConf[28]="0"; return Integer.parseInt(arrConf[28]); }
////	public static int getSashMID_AMO() { if (arrConf[29]==null) arrConf[29]="0"; return Integer.parseInt(arrConf[29]); }
////	public static int getSashBOT_AMO() { if (arrConf[30]==null) arrConf[30]="0"; return Integer.parseInt(arrConf[30]); }
//
//	// ========== set TINGGI scroll BODY TEMPLATE
////	public static int getFreeATS() { if (arrConf[4]==null) arrConf[4]="0"; return Integer.parseInt(arrConf[4]); }
////	public static int getALR() { if (arrConf[5]==null) arrConf[5]="0"; return Integer.parseInt(arrConf[5]); }
////	public static int getRCF() { if (arrConf[6]==null) arrConf[6]="0"; return Integer.parseInt(arrConf[6]); }
////	public static int getFPL() { if (arrConf[7]==null) arrConf[7]="0"; return Integer.parseInt(arrConf[7]); }
////	public static int getDLA() { if (arrConf[8]==null) arrConf[8]="0"; return Integer.parseInt(arrConf[8]); }
////	public static int getCHG() { if (arrConf[9]==null) arrConf[9]="0"; return Integer.parseInt(arrConf[9]); }
////	public static int getCNL() { if (arrConf[10]==null) arrConf[10]="0"; return Integer.parseInt(arrConf[10]); }
////	public static int getDEP() { if (arrConf[11]==null) arrConf[11]="0"; return Integer.parseInt(arrConf[11]); }
////	public static int getARR() { if (arrConf[12]==null) arrConf[12]="0"; return Integer.parseInt(arrConf[12]); }
////	public static int getCDN() { if (arrConf[13]==null) arrConf[13]="0"; return Integer.parseInt(arrConf[13]); }
////	public static int getCPL() { if (arrConf[14]==null) arrConf[14]="0"; return Integer.parseInt(arrConf[14]); }
////	public static int getEST() { if (arrConf[15]==null) arrConf[15]="0"; return Integer.parseInt(arrConf[15]); }
////	public static int getACP() { if (arrConf[16]==null) arrConf[16]="0"; return Integer.parseInt(arrConf[16]); }
////	public static int getLAM() { if (arrConf[17]==null) arrConf[17]="0"; return Integer.parseInt(arrConf[17]); }
////	public static int getRQP() { if (arrConf[18]==null) arrConf[18]="0"; return Integer.parseInt(arrConf[18]); }
////	public static int getRQS() { if (arrConf[19]==null) arrConf[19]="0"; return Integer.parseInt(arrConf[19]); }
////	public static int getSPL() { if (arrConf[20]==null) arrConf[20]="0"; return Integer.parseInt(arrConf[20]); }
//
//	// ========== set LEBAR textbox OriRef
//	public static int getWidthTextOriRef() { if (arrConf[66]==null) arrConf[66]="0"; return Integer.parseInt(arrConf[66]); }
//
//	// ========== set TABLE ATS: tinggi,nama kolom dan panjang kolom
//	public static int getHeightTblATS() { return Integer.parseInt(arrConf[85]); }
//	public static String getWidthATS() { return arrConf[82]; }
//	public static String getColnmATS() { return arrConf[83]; }
//
//	// PDF page setup - it used to read the configuration.txt file
//	int pdfleft=36;
//	int pdfright=36;
//	int pdftop=90;
//	int pdfbottom=36;
//	int pdfheight=20;
//	int pdfwidth=527;
//	int pdfheadheight=773;
//	int pdfwidthlandscape=770;
//	int pdfheadheightlandscape=540;
//	
//	public int getPDFLeft() { return pdfleft; }
//	public int getPDFRight() { return pdfright; }
//	public int getPDFTop() { return pdftop; }
//	public int getPDFBottom() { return pdfbottom; }
//	public int getPDFHeight() { return pdfheight; }
//	public int getPDFWidth() { return pdfwidth; }
//	public int getPDFHeadheight() { return pdfheadheight; }
//	public int getPDFWidthlandscape() { return pdfwidthlandscape; }
//	public int getPDFHeadheightlandscape() { return pdfheadheightlandscape; }
//	
//}
