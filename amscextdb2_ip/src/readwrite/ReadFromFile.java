package readwrite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import displays.ConnectTo;
import displays.MainForm;

public class ReadFromFile {
	
	String contentStr="";
	String lineStr = null;
	private static String[] arrConf = new String[100000];
	private static String[] arrTblcol = new String[100000];

	WriteToTXT writeTXT = new WriteToTXT();
	
	public ReadFromFile() {
		
	}
	int left=36;
	int right=36;
	int top=90;
	int bottom=36;
	int height=20;
	int width=527;
	int widthlandscape=770;
	int headHeight=773;
	int headheightlandscape=560;
	
	// PDF Margin LAMA
	public int getPDFtop() { return top;}//Integer.parseInt(tmp6a[0]); }
	public int getPDFbottom() { return bottom;}//Integer.parseInt(tmp6a[1]); }
	public int getPDFleft() { return left;}//Integer.parseInt(tmp6a[2]); }
	public int getPDFright() { return right;}//Integer.parseInt(tmp6a[3]); }
	public int getPDFheight() { return height;}//Integer.parseInt(tmp6a[4]); }
	public int getPDFwidth() { return width;}//Integer.parseInt(tmp6a[5]); }
	public int getPDFheadHeight() { return headHeight;}//Integer.parseInt(tmp6a[6]); }
	public int getPDFwidthlandscape() { return widthlandscape;}//Integer.parseInt(tmp6a[7]); }
	public int getPDFheadHeightlandscape() { return headheightlandscape;}//Integer.parseInt(tmp6a[8]); }
	
	//---------------------------------------------------------------------
	public void readDB(String filename) { 
		String lineStr="";
		try {
			File myFile = new File(filename);
		    FileReader fileReader = new FileReader(myFile);
		    BufferedReader buffReader = new BufferedReader(fileReader);
		    String line = null;
			while ((line = buffReader.readLine()) != null) {
				parsingDB(line);
			}	
		    buffReader.close();
		    parsingDB(lineStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void parsingDB(String line) {
		String[] sub = new String[100];
		sub = line.split("\n");
		if (line.startsWith("#")) line="";
		for (int x = 0; x < sub.length; x++) {
			if (sub[x].startsWith("url")) { ConnectTo.DB_URL=sub[x].substring(4, sub[x].length()); }
			if (sub[x].startsWith("user")) { ConnectTo.DB_USER=sub[x].substring(5, sub[x].length()); }
			if (sub[x].startsWith("pass")) { ConnectTo.DB_PASS=sub[x].substring(5, sub[x].length()); }
		}
	}
	
	public void readDBRej(String filename) { 
		String lineStr="";
		try {
			File myFile = new File(filename);
		    FileReader fileReader = new FileReader(myFile);
		    BufferedReader buffReader = new BufferedReader(fileReader);
		    String line = null;
			while ((line = buffReader.readLine()) != null) {
				parsingDBRej(line);
			}	
		    buffReader.close();
		    parsingDBRej(lineStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void parsingDBRej(String line) {
		String[] sub = new String[100];
		sub = line.split("\n");
		if (line.startsWith("#")) line="";
		for (int x = 0; x < sub.length; x++) {
			if (sub[x].startsWith("url")) { ConnectTo.DB_URL_REJ=sub[x].substring(4, sub[x].length()); }
			if (sub[x].startsWith("user")) { ConnectTo.DB_USER_REJ=sub[x].substring(5, sub[x].length()); }
			if (sub[x].startsWith("pass")) { ConnectTo.DB_PASS_REJ=sub[x].substring(5, sub[x].length()); }
		}
	}
	
	
	//---------------------------------------------------------------------
	public String ReadInputFile1(String filename) { //for open password only (/tp/pass.txt)
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
			if (e.getMessage().compareToIgnoreCase(MainForm.sFolder+"rowlimit.txt (No such file or directory)")==0) {
				writeTXT.open(MainForm.sFolder+"rowlimit.txt");
				writeTXT.write("10");
				writeTXT.close();
			}
			
			if (e.getMessage().compareToIgnoreCase(MainForm.sFolder+"destination.txt (No such file or directory)")==0) {
				writeTXT.open(MainForm.sFolder+"destination.txt");
				writeTXT.write("WICCREJT");
				writeTXT.close();
			}
			
			if (e.getMessage().compareToIgnoreCase(MainForm.sFolder+"maxexport.txt (No such file or directory)")==0) {
				writeTXT.open(MainForm.sFolder+"maxexport.txt");
				writeTXT.write("100");
				writeTXT.close();
			}
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
	
	public void readConfiguration() {
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
	
	void parsingConfiguration(String line) {
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
				 
				 // PDF page set up
				 if (tmp1[x].compareToIgnoreCase("pdfleft") == 0) { arrConf[1] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("pdfright") == 0) { arrConf[2] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("pdftop") == 0) { arrConf[3] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("pdfbottom") == 0) { arrConf[4] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("pdfheight") == 0) { arrConf[5] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("pdfwidth") == 0) { arrConf[6] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("pdfheadheight") == 0) { arrConf[7] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("pdfwidthlandscape") == 0) { arrConf[8] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("pdfheadheightlandscape") == 0) { arrConf[9] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("pdfheader") == 0) { arrConf[10] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("pdffooter") == 0) { arrConf[11] = tmp2[x]; }
				 
				 else if (tmp1[x].compareToIgnoreCase("title") == 0) { arrConf[12] = tmp2[x]; }
				 
				 // Command
				 else if (tmp1[x].compareToIgnoreCase("cmdpdf") == 0) { arrConf[13] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("cmdxls") == 0) { arrConf[14] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("cmddoc") == 0) { arrConf[15] = tmp2[x]; }
				 
				 // Stat
				 else if (tmp1[x].compareToIgnoreCase("xstat") == 0) { arrConf[16] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("ystat") == 0) { arrConf[17] = tmp2[x]; }
			 }
		 }
	}
	
	// PDF page setup
	public int getPDFLeft() { if (arrConf[1]==null) arrConf[1]="0"; return Integer.parseInt(arrConf[1]); }
	public int getPDFRight() { if (arrConf[2]==null) arrConf[2]="0"; return Integer.parseInt(arrConf[2]); }
	public int getPDFTop() { if (arrConf[3]==null) arrConf[3]="0"; return Integer.parseInt(arrConf[3]); }
	public int getPDFBottom() { if (arrConf[4]==null) arrConf[4]="0"; return Integer.parseInt(arrConf[4]); }
	public int getPDFHeight() { if (arrConf[5]==null) arrConf[5]="0"; return Integer.parseInt(arrConf[5]); }
	public int getPDFWidth() { if (arrConf[6]==null) arrConf[6]="0"; return Integer.parseInt(arrConf[6]); }
	public int getPDFHeadheight() { if (arrConf[7]==null) arrConf[7]="0"; return Integer.parseInt(arrConf[7]); }
	public int getPDFWidthlandscape() { if (arrConf[8]==null) arrConf[8]="0"; return Integer.parseInt(arrConf[8]); }
	public int getPDFHeadheightlandscape() { if (arrConf[9]==null) arrConf[9]="0"; return Integer.parseInt(arrConf[9]); }
	public String getPDFHeader() { return arrConf[10]; }
	public String getPDFFooter() { return arrConf[11]; }

	public String getTitle() { return arrConf[12]; }

	public String getCmdPDF() { return arrConf[13]; }
	public String getCmdXLS() { return arrConf[14]; }
	public String getCmdDOC() { return arrConf[15]; }

	public int getXstat() { return Integer.parseInt(arrConf[16]); }
	public int getYstat() { return Integer.parseInt(arrConf[17]); }
	
	//---------------------------------------------------------------------
	public void readTblCol() {
		try {
			File myFile = new File(MainForm.sFolder+"tblcol.txt");
			FileReader fileReader = new FileReader(myFile);
			BufferedReader buffReader = new BufferedReader(fileReader);
			String line = null;
			
			while ((line = buffReader.readLine()) != null) {
				parsingTblCol(line);
			}	
			buffReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void parsingTblCol(String line) {
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
				 
				 // Template size set up
				 if (tmp1[x].compareToIgnoreCase("sFREEATS") == 0) { arrTblcol[1] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sALR") == 0) { arrTblcol[2] = tmp2[x]; }//=CPL
				 else if (tmp1[x].compareToIgnoreCase("sRCF") == 0) { arrTblcol[3] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sFPL") == 0) { arrTblcol[4] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sDLA") == 0) { arrTblcol[5] = tmp2[x]; }//=CHG,CNL,DEP,RQP,RQS
				 else if (tmp1[x].compareToIgnoreCase("sCDN") == 0) { arrTblcol[6] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sARR") == 0) { arrTblcol[7] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sEST") == 0) { arrTblcol[8] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sACP") == 0) { arrTblcol[9] = tmp2[x]; }//SPL
				 else if (tmp1[x].compareToIgnoreCase("sLAM") == 0) { arrTblcol[10] = tmp2[x]; }
				 
				 else if (tmp1[x].compareToIgnoreCase("wFREEATS") == 0) { arrTblcol[11] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wALR") == 0) { arrTblcol[12] = tmp2[x]; }//=CPL
				 else if (tmp1[x].compareToIgnoreCase("wRCF") == 0) { arrTblcol[13] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wFPL") == 0) { arrTblcol[14] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wDLA") == 0) { arrTblcol[15] = tmp2[x]; }//=CHG,CNL,DEP,RQP,RQS
				 else if (tmp1[x].compareToIgnoreCase("wCDN") == 0) { arrTblcol[16] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wARR") == 0) { arrTblcol[17] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wEST") == 0) { arrTblcol[18] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wACP") == 0) { arrTblcol[19] = tmp2[x]; }//SPL
				 else if (tmp1[x].compareToIgnoreCase("wLAM") == 0) { arrTblcol[20] = tmp2[x]; }
				 
				 else if (tmp1[x].compareToIgnoreCase("xFREEATS") == 0) { arrTblcol[21] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xALR") == 0) { arrTblcol[22] = tmp2[x]; }//=CPL
				 else if (tmp1[x].compareToIgnoreCase("xRCF") == 0) { arrTblcol[23] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xFPL") == 0) { arrTblcol[24] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xDLA") == 0) { arrTblcol[25] = tmp2[x]; }//=CHG,CNL,DEP,RQP,RQS
				 else if (tmp1[x].compareToIgnoreCase("xCDN") == 0) { arrTblcol[26] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xARR") == 0) { arrTblcol[27] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xEST") == 0) { arrTblcol[28] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xACP") == 0) { arrTblcol[29] = tmp2[x]; }//SPL
				 else if (tmp1[x].compareToIgnoreCase("xLAM") == 0) { arrTblcol[30] = tmp2[x]; }
				 
				 else if (tmp1[x].compareToIgnoreCase("sNOTAM") == 0) { arrTblcol[31] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sRQNTM") == 0) { arrTblcol[32] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("sACTNTM") == 0) { arrTblcol[33] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("sEXPNTM") == 0) { arrTblcol[34] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sCHKNTM") == 0) { arrTblcol[35] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sSNOWTAM") == 0) { arrTblcol[36] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sASHTAM") == 0) { arrTblcol[37] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sBIRDTAM") == 0) { arrTblcol[38] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sRQN") == 0) { arrTblcol[39] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sRQL") == 0) { arrTblcol[40] = tmp2[x]; }
				 
				 else if (tmp1[x].compareToIgnoreCase("wNOTAM") == 0) { arrTblcol[41] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wRQNTM") == 0) { arrTblcol[42] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("wACTNTM") == 0) { arrTblcol[43] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("wEXPNTM") == 0) { arrTblcol[44] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wCHKNTM") == 0) { arrTblcol[45] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wSNOWTAM") == 0) { arrTblcol[46] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wASHTAM") == 0) { arrTblcol[47] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wBIRDTAM") == 0) { arrTblcol[48] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wRQN") == 0) { arrTblcol[49] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wRQL") == 0) { arrTblcol[50] = tmp2[x]; }
				 
				 else if (tmp1[x].compareToIgnoreCase("xNOTAM") == 0) { arrTblcol[51] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xRQNTM") == 0) { arrTblcol[52] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xACTNTM") == 0) { arrTblcol[53] = tmp2[x]; }
//				 else if (tmp1[x].compareToIgnoreCase("xEXPNTM") == 0) { arrTblcol[54] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xCHKNTM") == 0) { arrTblcol[55] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xSNOWTAM") == 0) { arrTblcol[56] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xASHTAM") == 0) { arrTblcol[57] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xBIRDTAM") == 0) { arrTblcol[58] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xRQN") == 0) { arrTblcol[59] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xRQL") == 0) { arrTblcol[60] = tmp2[x]; }

				 else if (tmp1[x].compareToIgnoreCase("sMETAR") == 0) { arrTblcol[61] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sSIGMET") == 0) { arrTblcol[62] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("sAIREP") == 0) { arrTblcol[63] = tmp2[x]; }

				 else if (tmp1[x].compareToIgnoreCase("wMETAR") == 0) { arrTblcol[64] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wSIGMET") == 0) { arrTblcol[65] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wAIREP") == 0) { arrTblcol[66] = tmp2[x]; }

				 else if (tmp1[x].compareToIgnoreCase("xMETAR") == 0) { arrTblcol[67] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xSIGMET") == 0) { arrTblcol[68] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xAIREP") == 0) { arrTblcol[69] = tmp2[x]; }
				 
				 else if (tmp1[x].compareToIgnoreCase("sRet") == 0) { arrTblcol[70] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wRet") == 0) { arrTblcol[71] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xRet") == 0) { arrTblcol[72] = tmp2[x]; }

				 else if (tmp1[x].compareToIgnoreCase("sReject") == 0) { arrTblcol[73] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wReject") == 0) { arrTblcol[74] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xReject") == 0) { arrTblcol[75] = tmp2[x]; }
				 
				 else if (tmp1[x].compareToIgnoreCase("sStatistic") == 0) { arrTblcol[76] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("wStatistic") == 0) { arrTblcol[77] = tmp2[x]; }
				 else if (tmp1[x].compareToIgnoreCase("xStatistic") == 0) { arrTblcol[78] = tmp2[x]; }
			 }
		 }
	}
	
	// cols
	public String getColnmFREEATS() { return arrTblcol[1]; }
	public String getColnmALR() { return arrTblcol[2]; }
	public String getColnmRCF() { return arrTblcol[3]; }
	public String getColnmFPL() { return arrTblcol[4]; }
	public String getColnmDLA() { return arrTblcol[5]; }
	public String getColnmCDN() { return arrTblcol[6]; }
	public String getColnmARR() { return arrTblcol[7]; }
	public String getColnmEST() { return arrTblcol[8]; }
	public String getColnmACP() { return arrTblcol[9]; }
	public String getColnmLAM() { return arrTblcol[10]; }
	
	public String getWidthFREEATS() { return arrTblcol[11]; }
	public String getWidthALR() { return arrTblcol[12]; }
	public String getWidthRCF() { return arrTblcol[13]; }
	public String getWidthFPL() { return arrTblcol[14]; }
	public String getWidthDLA() { return arrTblcol[15]; }
	public String getWidthCDN() { return arrTblcol[16]; }
	public String getWidthARR() { return arrTblcol[17]; }
	public String getWidthEST() { return arrTblcol[18]; }
	public String getWidthACP() { return arrTblcol[19]; }
	public String getWidthLAM() { return arrTblcol[20]; }
	
	public String getXlsWidthFREEATS() { return arrTblcol[21]; }
	public String getXlsWidthALR() { return arrTblcol[22]; }
	public String getXlsWidthRCF() { return arrTblcol[23]; }
	public String getXlsWidthFPL() { return arrTblcol[24]; }
	public String getXlsWidthDLA() { return arrTblcol[25]; }
	public String getXlsWidthCDN() { return arrTblcol[26]; }
	public String getXlsWidthARR() { return arrTblcol[27]; }
	public String getXlsWidthEST() { return arrTblcol[28]; }
	public String getXlsWidthACP() { return arrTblcol[29]; }
	public String getXlsWidthLAM() { return arrTblcol[30]; }
	
	public String getColnmNOTAM() { return arrTblcol[31]; }
	public String getColnmRQNTM() { return arrTblcol[32]; }
//	public String getColnmACTNTM() { return arrTblcol[33]; }
//	public String getColnmEXPNTM() { return arrTblcol[34]; }
	public String getColnmCHKNTM() { return arrTblcol[35]; }
	public String getColnmSNOWTAM() { return arrTblcol[36]; }
	public String getColnmASHTAM() { return arrTblcol[37]; }
	public String getColnmBIRDTAM() { return arrTblcol[38]; }
	public String getColnmRQN() { return arrTblcol[39]; }
	public String getColnmRQL() { return arrTblcol[40]; }
	
	public String getWidthNOTAM() { return arrTblcol[41]; }
	public String getWidthRQNTM() { return arrTblcol[42]; }
//	public String getWidthACTNTM() { return arrTblcol[43]; }
//	public String getWidthEXPNTM() { return arrTblcol[44]; }
	public String getWidthCHKNTM() { return arrTblcol[45]; }
	public String getWidthSNOWTAM() { return arrTblcol[46]; }
	public String getWidthASHTAM() { return arrTblcol[47]; }
	public String getWidthBIRDTAM() { return arrTblcol[48]; }
	public String getWidthRQN() { return arrTblcol[49]; }
	public String getWidthRQL() { return arrTblcol[50]; }
	
	public String getXlsWidthNOTAM() { return arrTblcol[51]; }
	public String getXlsWidthRQNTM() { return arrTblcol[52]; }
//	public String getXlsWidthACTNTM() { return arrTblcol[53]; }
//	public String getXlsWidthEXPNTM() { return arrTblcol[54]; }
	public String getXlsWidthCHKNTM() { return arrTblcol[55]; }
	public String getXlsWidthSNOWTAM() { return arrTblcol[56]; }
	public String getXlsWidthASHTAM() { return arrTblcol[57]; }
	public String getXlsWidthBIRDTAM() { return arrTblcol[58]; }
	public String getXlsWidthRQN() { return arrTblcol[59]; }
	public String getXlsWidthRQL() { return arrTblcol[60]; }

	public String getColnmMETAR() { return arrTblcol[61]; }
	public String getColnmSIGMET() { return arrTblcol[62]; }
	public String getColnmAIREP() { return arrTblcol[63]; }

	public String getWidthMETAR() { return arrTblcol[64]; }
	public String getWidthSIGMET() { return arrTblcol[65]; }
	public String getWidthAIREP() { return arrTblcol[66]; }

	public String getXlsWidthMETAR() { return arrTblcol[67]; }
	public String getXlsWidthSIGMET() { return arrTblcol[68]; }
	public String getXlsWidthAIREP() { return arrTblcol[69]; }
	
	public String getColnmIncoming() { return arrTblcol[70]; }
	public String getWidthIncoming() { return arrTblcol[71]; }
	public String getXlsWidthIncoming() { return arrTblcol[72]; }
	
	public String getColnmReject() { return arrTblcol[73]; }
	public String getWidthReject() { return arrTblcol[74]; }
	public String getXlsWidthReject() { return arrTblcol[75]; }
	
	public String getColnmStatistic() { return arrTblcol[76]; }
	public String getWidthStatistic() { return arrTblcol[77]; }
	public String getXlsWidthStatistic() { return arrTblcol[78]; }
	
}
