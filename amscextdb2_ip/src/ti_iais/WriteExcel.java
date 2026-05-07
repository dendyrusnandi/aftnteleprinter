package ti_iais;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.jfree.ui.HorizontalAlignment;

import displays.tabs.TabSTATISTIC;


import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.PageOrder;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class WriteExcel {
	
	private WritableFont WF_BOLD;
	private WritableFont WF_BIGGER_BOLD;
	
	private WritableCellFormat FONT_BIGGER_BOLD;
	private WritableCellFormat FONT_BOLD;
	private WritableCellFormat FONT_NORMAL;
	private String inputFile;
	
	//add
	static String stitle="",smsgBody="",sflnm="",sDtSearch="",strmax="",widthCol="";
	
	
	public void setOutputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void setData(String dt, String title, String msgBody, String flnm) {
		stitle = title;
		smsgBody = msgBody;
		sflnm = flnm;
		sDtSearch = dt;
		
		// ===== start : setting msgbody for NOTAM Statistic
		if (sDtSearch.compareToIgnoreCase("notam1")==0) {
			if (smsgBody.contains(";;Total;;")) smsgBody = smsgBody.replace(";;Total;;", "=Total=");
			if (smsgBody.contains(";;")) smsgBody = smsgBody.replace(";;", ";");
//			if (smsgBody.contains("=Total=")) smsgBody = smsgBody.replace("=Total=", ";Total;");
			if (smsgBody.contains("=Total=")) smsgBody = smsgBody.replace("=Total=", "Total;;");
			
		} else if (sDtSearch.compareToIgnoreCase("notam1orig")==0) {
			if (smsgBody.contains("Total;;;;")) smsgBody = smsgBody.replace("Total;;;;", ";Total;;;");
			
			if (smsgBody.contains(";Total;;;")) smsgBody = smsgBody.replace(";Total;;;", "=Total=");
			if (smsgBody.contains(";;")) smsgBody = smsgBody.replace(";;", ";");
			if (smsgBody.contains("=Total=")) smsgBody = smsgBody.replace("=Total=", ";;Total;");
		
		} else if (sDtSearch.compareToIgnoreCase("notam1serie")==0) {
//			System.err.println("smsgBody::\n" + smsgBody + "#");
			if (smsgBody.contains(";Total;;;;")) { smsgBody = smsgBody.replace(";Total;;;;", "=Total="); }
			if (smsgBody.contains(";;")) smsgBody = smsgBody.replace(";;", ";");
			if (smsgBody.contains("Total;;")) smsgBody = smsgBody.replace("Total;;", "Total;;;");
			
//			if (smsgBody.contains(";;Total;;")) { smsgBody = smsgBody.replace(";;Total;;", ";;;Total;"); }
//			
//			if (smsgBody.contains(";Total;;;")) smsgBody = smsgBody.replace(";Total;;;", "=Total=");
//			if (smsgBody.contains(";;")) smsgBody = smsgBody.replace(";;", ";");
////			if (smsgBody.contains("=Total=")) smsgBody = smsgBody.replace("=Total=", ";Total;;");
//			if (smsgBody.contains("=Total=")) smsgBody = smsgBody.replace("=Total=", "Total;;;;");
			
//			System.err.println("ubah::\n" + smsgBody + "#");
			
		} else if (sDtSearch.compareToIgnoreCase("notam1origserie")==0) {
			if (smsgBody.contains("Total;;;;")) smsgBody = smsgBody.replace("Total;;;;", ";Total;;;");
			
			if (smsgBody.contains(";Total;;;")) smsgBody = smsgBody.replace(";Total;;;", "=Total=");
			if (smsgBody.contains(";;")) smsgBody = smsgBody.replace(";;", ";");
			if (smsgBody.contains("=Total=")) smsgBody = smsgBody.replace("=Total=", "Total;;;;");
		}
		// ===== end : setting msgbody for NOTAM Statistic
		
		WriteExcel test = new WriteExcel();
		test.setOutputFile(sflnm);
		try {
			test.write();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Please check the result file under "+flnm);
	}

	// ===== start : writing to xls file, setting the page layout
	public void write() throws IOException, WriteException {
	    File file = new File(inputFile);
	    WorkbookSettings wbSettings = new WorkbookSettings();
	
	    wbSettings.setLocale(new Locale("en", "EN"));
	
	    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
	    workbook.createSheet("Report", 0);
	    WritableSheet excelSheet = workbook.getSheet(0);
	    createLabel(excelSheet);
	    createContent(excelSheet);
	    
	    // Set the orientation and the margins
	    excelSheet.getSettings().setPaperSize(PaperSize.A4);
	    excelSheet.getSettings().setOrientation(PageOrientation.LANDSCAPE);
	    
	    excelSheet.getSettings().setPageOrder(PageOrder.DOWN_THEN_RIGHT);
	    excelSheet.getSettings().setTopMargin(0.05);
	    excelSheet.getSettings().setBottomMargin(0.05);
	    excelSheet.getSettings().setLeftMargin(0);
	    excelSheet.getSettings().setRightMargin(0);
	    excelSheet.getSettings().setFitToPages(true);
	    excelSheet.getSettings().setHorizontalCentre(true);
	    
	    workbook.write();
	    workbook.close();
	}
	// ===== end : writing to xls file, setting the page layout
	
	
	// ===== start : create Title of the XLS file
	private void createLabel(WritableSheet sheet) throws WriteException {
		// Lets create a FONT_NORMAL font
	    WritableFont WF_NORMAL = new WritableFont(WritableFont.COURIER, 8);
	    // Define the cell format
	    FONT_NORMAL = new WritableCellFormat(WF_NORMAL);
	    // Lets automatically wrap the cells
	    FONT_NORMAL.setWrap(true);
	
	    // create font setting for content
	    WF_BOLD = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
	    WF_BOLD.setColour(Colour.BLACK);
	    FONT_BOLD = new WritableCellFormat(WF_BOLD);
	    // Lets automatically wrap the cells
	    FONT_BOLD.setWrap(true);
	
	    // create font setting for title
	    WF_BIGGER_BOLD = new WritableFont(WritableFont.COURIER, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
	    FONT_BIGGER_BOLD = new WritableCellFormat(WF_BIGGER_BOLD);
	    // Lets automatically wrap the cells
	    FONT_BIGGER_BOLD.setWrap(true);
	    
	    CellView cv = new CellView();
	    cv.setFormat(FONT_BOLD);
	    cv.setAutosize(true);
	
	    // Write a few headers
	    addCaption(sheet, 0, 0, stitle);
	}
	// ===== end : create Title of the XLS file
	
	
	// ===== start : create Content of the XLS file
	private void createContent(WritableSheet sheet) throws WriteException, RowsExceededException {
		String judul="",msgBody="";
		int witdhTgl = 4;
	    int rowTitle = 0;
	    int row = 2;
	    
		if (sDtSearch.compareToIgnoreCase("status")==0) {
			judul="Msg Typ;Acft ID;Stat;";
			 
			int hTitle = 40*20;
			sheet.setRowView(rowTitle, hTitle);
			 
			int hHeader = 30*20;
			sheet.setRowView(row, hHeader);
			
			sheet.setColumnView(0, witdhTgl);
			sheet.setColumnView(1, witdhTgl+3);
			sheet.setColumnView(2, witdhTgl+2);
			for (int i=3; i<=33; i++) sheet.setColumnView(i, witdhTgl);
			sheet.setColumnView(34, witdhTgl+3);
			
		} else if (sDtSearch.compareToIgnoreCase("level")==0) {
			judul="Msg Typ;Acft ID;Level;";
			 
			int hHeader = 30*20;
			sheet.setRowView(row, hHeader);
			
			sheet.setColumnView(0, witdhTgl);
			sheet.setColumnView(1, witdhTgl+3);
			sheet.setColumnView(2, witdhTgl+2);
			for (int i=3; i<=33; i++) sheet.setColumnView(i, witdhTgl);
			sheet.setColumnView(34, witdhTgl+3);
			
			
		} else if (sDtSearch.compareToIgnoreCase("route")==0) {
			judul="Msg Typ;Acft ID;Route;";
			 
			int hHeader = 30*20;
			sheet.setRowView(row, hHeader);
			
			sheet.setColumnView(0, witdhTgl);
			sheet.setColumnView(1, witdhTgl+3);
			sheet.setColumnView(2, witdhTgl+2);
			for (int i=3; i<=33; i++) sheet.setColumnView(i, witdhTgl);
			sheet.setColumnView(34, witdhTgl+3);
			
		} else if (sDtSearch.compareToIgnoreCase("sats1")==0) {
			judul="Msg Type;Type of Aircraft;";
			 
//			if (TabSTATISTIC.sent.getSelection() == true || TabSTATISTIC.recv.getSelection() == true) {
//				int hTitle = 40*20;
//				sheet.setRowView(rowTitle, hTitle);
//			}
			 
			int hHeader = 30*20;
			sheet.setRowView(row, hHeader);
			
			sheet.setColumnView(0, witdhTgl+2);
			sheet.setColumnView(1, witdhTgl+4);
			for (int i=2; i<=32; i++) sheet.setColumnView(i, witdhTgl);
			sheet.setColumnView(33, witdhTgl+3);
			
		} else if (sDtSearch.compareToIgnoreCase("sats")==0) {
			judul="Msg Type;";
			 
//			if (TabSTATISTIC.sent.getSelection() == true || TabSTATISTIC.recv.getSelection() == true) {
//				int hTitle = 40*20;
//				sheet.setRowView(rowTitle, hTitle);
//			}
			
			int hHeader = 30*20;
			sheet.setRowView(row, hHeader);
			
			sheet.setColumnView(0, witdhTgl+2);
			for (int i=1; i<=31; i++) sheet.setColumnView(i, witdhTgl);
			sheet.setColumnView(32, witdhTgl+3);
			
		} else if (sDtSearch.compareToIgnoreCase("metar")==0) {
			judul="Location;";
			 
//			if (TabSTATISTIC.sent.getSelection() == true || TabSTATISTIC.recv.getSelection() == true) {
//				int hTitle = 40*20;
//				sheet.setRowView(rowTitle, hTitle);
//			}
			 
			int hHeader = 30*20;
			sheet.setRowView(row, hHeader);
			
			sheet.setColumnView(0, witdhTgl+6);
			for (int i=1; i<=31; i++) sheet.setColumnView(i, witdhTgl);
			sheet.setColumnView(32, witdhTgl+3);
			
		} else if (sDtSearch.compareToIgnoreCase("notam1")==0) {
			judul="Type;Loca;";
			 
//			if (TabSTATISTIC.sent.getSelection() == true || TabSTATISTIC.recv.getSelection() == true) {
//				int hTitle = 40*20;
//				sheet.setRowView(rowTitle, hTitle);
//			}
			 
			int hHeader = 30*20;
			sheet.setRowView(row, hHeader);
			
			sheet.setColumnView(0, witdhTgl+4);
			sheet.setColumnView(1, witdhTgl+2);
			for (int i=2; i<=32; i++) sheet.setColumnView(i, witdhTgl);
			sheet.setColumnView(33, witdhTgl+3);
			
		} else if (sDtSearch.compareToIgnoreCase("notam1orig")==0) {
			judul="Type;Orig;Loca;";
			int hTitle=0;
			int ih=0;
//			if (TabSTATISTIC.sent.getSelection() == true || TabSTATISTIC.recv.getSelection() == true) {
//				ih += 30;
//			}
			if (!TabSTATISTIC.Originator.getText().isEmpty()) {
				ih += 30;
			}
			 
			hTitle = ih*20;
			sheet.setRowView(rowTitle, hTitle);
			
			int hHeader = 30*20;
			sheet.setRowView(row, hHeader);
			
			sheet.setColumnView(0, witdhTgl+2);
			sheet.setColumnView(1, witdhTgl+1);
			sheet.setColumnView(2, witdhTgl+2);
			for (int i=3; i<=33; i++) sheet.setColumnView(i, witdhTgl);
			sheet.setColumnView(34, witdhTgl+2);
			
		} else if (sDtSearch.compareToIgnoreCase("notam1serie")==0) {
			judul="Type;Loca;Serie;";
			 
			int hTitle=0;
			int ih=0;
//			if (TabSTATISTIC.sent.getSelection() == true || TabSTATISTIC.recv.getSelection() == true) {
//				ih += 30;
//			}
			if (!TabSTATISTIC.Serie.getText().isEmpty()) {
				ih += 30;
			}
			 
			hTitle = ih*20;
			sheet.setRowView(rowTitle, hTitle);
			 
			int hHeader = 30*20;
			sheet.setRowView(row, hHeader);
			
			sheet.setColumnView(0, witdhTgl+2);
			sheet.setColumnView(1, witdhTgl+1);
			sheet.setColumnView(2, witdhTgl+2);
			for (int i=3; i<=33; i++) sheet.setColumnView(i, witdhTgl);
			sheet.setColumnView(34, witdhTgl+2);
			
		} else if (sDtSearch.compareToIgnoreCase("notam1origserie")==0) {
			judul="Type;Org;Loc;Sr;";
			 
			int hTitle=0;
			int ih=0;
//			if (TabSTATISTIC.sent.getSelection() == true || TabSTATISTIC.recv.getSelection() == true) {
//				ih += 30;
//			}
			if (!TabSTATISTIC.Originator.getText().isEmpty()) {
				ih += 30;
			}
			if (!TabSTATISTIC.Serie.getText().isEmpty()) {
				ih += 30;
			}
			 
			hTitle = ih*20;
			sheet.setRowView(rowTitle, hTitle);
			 
			int hHeader = 30*20;
			sheet.setRowView(row, hHeader);
			
			sheet.setColumnView(0, witdhTgl+2);
			sheet.setColumnView(1, witdhTgl);
			sheet.setColumnView(2, witdhTgl);
			sheet.setColumnView(3, witdhTgl-1);
			for (int i=4; i<=34; i++) sheet.setColumnView(i, witdhTgl);
			sheet.setColumnView(35, witdhTgl+2);
			
		} 
		judul+="1;2;3;4;5;6;7;8;9;10;11;12;13;14;15;16;17;18;19;20;21;22;23;24;25;26;27;28;29;30;31;Sub Total";
		
		
//		String headerAFTN = "Prio;Dest1;Dest2;Dest3;Dest4;Dest5;Dest6;Dest7;Dest8;Dest9;Dest10;Dest11;Dest12;Dest13;Dest14;Dest15;" +
//				"\n;Dest16;Dest17;Dest18;Dest19;Dest20;Dest21;Filing;Orig.;Orig Ref;";
		
//		if (sDtSearch.startsWith("msg")) { sheet.setColumnView(0, 5); for (int i=1; i<=16; i++) sheet.setColumnView(i, 9);} 
		if (sDtSearch.compareToIgnoreCase("msgfreeats")==0) {
			//judul = headerAFTN+"Freetext; ; ; ; ; ;\n";
			widthCol+="50;20;20;15;";
			judul="Text;Date/Time;Filed by;Status;\n";
			setWidthColumn(widthCol, sheet);
		}
		
		else if (sDtSearch.compareToIgnoreCase("msgalr")==0) { 
//			judul = headerAFTN+"type3a;type3b;type3c;type5a;type5b;type5c;" +
//					"\n;type7a;type7b;type7c;type8a;type8b;type9a;type9b;type9c;type10a;type10b;type13a;type13b;type15a;type15b;type15c;" +
//					"\n;type16a;type16b;type16c;type16d;type18;type19;type20; ; ; ; ; ; ; ; ;\n";
			widthCol+="11;5;5;6;7;5;6;30;7;5;15;15;15;";
			judul="Aircraft ID;Mode;Code;Type;DEP AD;ETD;Level;Route;DEST AD;EET;Date/Time;Filed by;Status;\n";
			setWidthColumn(widthCol, sheet);
		}
		else if (sDtSearch.compareToIgnoreCase("msgrcf")==0) { 
//			judul = headerAFTN+"type3a;type3b;type3c;type7a;type7b;type7c;" +
//					"\n;type21; ; ; ; ; ; ; ; ; ; ; ; ; ; ;\n";
			widthCol+="11;11;11;15;15;15;";
			judul="Aircraft ID;SSR Mode;SRR Code;Date/Time;Filed by;Status;\n";
			setWidthColumn(widthCol, sheet);
		}
		else if (sDtSearch.compareToIgnoreCase("msgfpl")==0) { 
//			judul = headerAFTN+"type3a;type3b;type3c;type7a;type7b;type7c;" +
//					"\n;type8a;type8b;type9a;type9b;type9c;type10a;type10b;type13a;type13b;type15a;type15b;type15c;type16a;type16b;type16c;" +
//					"\n;type16d;type18;type19;space; ; ; ; ; ; ; ; ; ; ; ;\n";
			widthCol+="10;5;6;7;5;6;20;7;5;10;15;15;15;";
			judul="Acft ID;Mode;Type;DEP AD;ETD;Level;Route;DEST AD;EET;REG;Date/Time;Filed by;Status;\n";
			setWidthColumn(widthCol, sheet);
		}
		else if (sDtSearch.compareToIgnoreCase("msgdla")==0 ||
				sDtSearch.compareToIgnoreCase("msgcnl")==0 || 
				sDtSearch.compareToIgnoreCase("msgdep")==0 || 
				sDtSearch.compareToIgnoreCase("msgrqp")==0 || 
		 		sDtSearch.compareToIgnoreCase("msgrqs")==0) { 
//			judul = headerAFTN+"type3a;type3b;type3c;type7a;type7b;type7c;" +
//					"\n;type13a;type13b;type16a;type16b;type16c;type16d;type18; ; ; ; ; ; ; ; ;\n";
			widthCol+="11;8;8;8;7;8;11;15;15;15;";
			judul="Aircraft ID;SSR Mode;SSR Code;DEP AD;ETD;DEST AD;DOF;Date/Time;Filed by;Status;\n";
			setWidthColumn(widthCol, sheet);
		}
		else if (sDtSearch.compareToIgnoreCase("msgchg")==0) {
//			judul = headerAFTN+"type3a;type3b;type3c;type7a;type7b;type7c;" +
//					"\n;type13a;type13b;type16a;type16b;type16c;type16d;type18;type22; ; ; ; ; ; ; ;\n";
			widthCol+="10;10;11;5;5;7;5;7;11;15;15;15;";
			judul="Number;Ref.Data;Aircraft ID;Mode;Code;DEP AD;ETD;DEST AD;DOF;Date/Time;Filed by;Status;\n";
			setWidthColumn(widthCol, sheet);
		}
		else if (sDtSearch.compareToIgnoreCase("msgarr")==0) { 
//			judul = headerAFTN+"type3a;type3b;type3c;type7a;type7b;type7c;" +
//					"\n;type13a;type13b;type17a;type17b;type17c; ; ; ; ; ; ; ; ; ; ;\n";
			widthCol+="11;5;5;7;5;7;5;15;15;15;";
			judul="Aircraft ID;Mode;Code;DEP AD;ETD;ARR AD;ATD;Date/Time;Filed by;Status;\n";
			setWidthColumn(widthCol, sheet);
		}
		else if (sDtSearch.compareToIgnoreCase("msgcdn")==0) { 
//			judul = headerAFTN+"type3a;type3b;type3c;type7a;type7b;type7c;" +
//					"\n;type13a;type13b;type16a;type16b;type16c;type16d;type22; ; ; ; ; ; ; ; ;\n";
			widthCol+="11;11;11;5;5;7;7;25;15;15;15;";
			judul="Number;Ref.Data;Aircraft ID;Mode;Code;DEP AD;DEST AD;Amendment;Date/Time;Filed by;Status;\n";
			setWidthColumn(widthCol, sheet);
		}
		else if (sDtSearch.compareToIgnoreCase("msgcpl")==0) { 
//			judul = headerAFTN+"type3a;type3b;type3c;type7a;type7b;type7c;" +
//					"\n;type8a;type8b;type9a;type9b;type9c;type10a;type10b;type13a;type13b;type14a;type14b;type14c;type14d;type14e;type15a;" +
//					"\n;type15b;type15c;type16a;type16b;type16c;type16d;type18; ; ; ; ; ; ; ; ;\n";
			widthCol+="11;5;5;6;7;7;5;7;25;7;15;15;15;";
			judul="Aircraft ID;Mode;Code;Type;DEP AD;Point;Time;Level;Route;DEST AD;Date/Time;Filed by;Status;\n";
			setWidthColumn(widthCol, sheet);
		}
		else if (sDtSearch.compareToIgnoreCase("msgest")==0) {
//			judul = headerAFTN+"type3a;type3b;type3c;type7a;type7b;type7c;" +
//					"\n;type13a;type13b;type14a;type14b;type14c;type14d;type14e;type16a;type16b;type16c;type16d; ; ; ; ;\n";
			widthCol+="11;11;11;5;5;7;7;5;6;7;15;15;15;";
			judul="Number;Ref.Data;Aircraft ID;Mode;Code;DEP AD;Point;Time;Level;DEST AD;Date/Time;Filed by;Status;\n";
			setWidthColumn(widthCol, sheet);
		}
		else if (sDtSearch.compareToIgnoreCase("msgacp")==0) { 
//			judul = headerAFTN+"type3a;type3b;type3c;type7a;type7b;type7c;" +
//					"\n;type13a;type13b;type16a;type16b;type16c;type16d; ; ; ; ; ; ; ; ; ;\n";
			widthCol+="11;11;11;5;5;7;7;15;15;15;";
			judul="Number;Ref.Data;Aircraft ID;Mode;Code;DEP AD;DEST AD;Date/Time;Filed by;Status;\n";
			setWidthColumn(widthCol, sheet);
		}
		else if (sDtSearch.compareToIgnoreCase("msglam")==0) {
//			judul = headerAFTN+"type3a;type3b;type3c; ; ; ;\n";
			widthCol+="11;11;15;15;15;";
			judul="Number;Ref.Data;Date/Time;Filed by;Status;\n";
			setWidthColumn(widthCol, sheet);
		}
		else if (sDtSearch.compareToIgnoreCase("msgspl")==0) { 
//			judul = headerAFTN+"type3a;type3b;type3c;type7a;type7b;type7c;" +
//					"\n;type13a;type13b;type16a;type16b;type16c;type16d;type18;type19; ; ; ; ; ; ; ;\n";
			widthCol+="11;6;6;7;5;7;5;15;15;15;";
			judul="Aircraft ID;Mode;Code;DEP AD;ETD;DEST AD;EET;Date/Time;Filed by;Status;\n";
			setWidthColumn(widthCol, sheet);
		}
		
		String suby[] = null;
		msgBody = judul+smsgBody;
		
		suby = msgBody.split("\n");
		for (int y=0; y<suby.length; y++) {
		    String subx[] = null;
		    subx = suby[y].split(";");
		    for (int i=0; i<subx.length; i++) {
		    	//asli
//		    	if (y==0) addLabelBold(sheet, i, y+2, subx[i]);
//				else addLabel(sheet, i, y+2, subx[i]);
		    	
		    	//percobaan
		    	if (sDtSearch.startsWith("msg")) {
		    		if (subx[i].contains("~")) subx[i] = subx[i].replaceAll("~", "\n");
		    		addLabel(sheet, i, y+2, subx[i]);
		    	} else {
		    		if (sDtSearch.compareToIgnoreCase("status")==0 || sDtSearch.compareToIgnoreCase("level")==0 || sDtSearch.compareToIgnoreCase("route")==0) {
		    			if (subx[i].compareToIgnoreCase("Total")==0) subx[i] = "Tot";
		    		}
		    		
					if (y==0) addLabelBold(sheet, i, y+2, subx[i]);
					else addLabel(sheet, i, y+2, subx[i]);
		    	}
		    }
	    
		}
	}
	// ===== end : create Content of the XLS file
	
	
	
	void setWidthColumn(String widthCol, WritableSheet sheet) {
		if (!widthCol.isEmpty()) {
			String sub[] = widthCol.split(";");
			for (int i=0; i<sub.length; i++) {
				sheet.setColumnView(i, Integer.parseInt(sub[i]));	
			}
		}
	}
	
	
	// ===== start : setting Title (cell format, such as : font face, font size, font style, cell vertical alignment, cell horizontal alignment, cell merge)
	private void addCaption(WritableSheet sheet, int column, int row, String s) throws RowsExceededException, WriteException {
		Label label;

		WritableCellFormat cellFormat = new WritableCellFormat(FONT_BIGGER_BOLD);
	    cellFormat.setAlignment(Alignment.CENTRE);
	    cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		int iCnt=0;
		String judul="";
		if (sDtSearch.compareToIgnoreCase("status")==0) {
			judul="Msg Typ;Acft ID;St;";
		} else if (sDtSearch.compareToIgnoreCase("level")==0) {
			judul="Msg Typ;Acft ID;Level;";
		} else if (sDtSearch.compareToIgnoreCase("route")==0) {
			judul="Msg Typ;Acft ID;Route;";
		} else if (sDtSearch.compareToIgnoreCase("sats1")==0) {
			judul="Msg Type;Type of Acft;";
		} else if (sDtSearch.compareToIgnoreCase("sats")==0) {
			judul="Msg Type;";
		} else if (sDtSearch.compareToIgnoreCase("metar")==0) {
			judul="Loca;";
		} else if (sDtSearch.compareToIgnoreCase("notam1")==0) {
			judul="Type;Loca;";
		} else if (sDtSearch.compareToIgnoreCase("notam1orig")==0) {
			judul="Type;Orig;Loca;";
		} else if (sDtSearch.compareToIgnoreCase("notam1serie")==0) {
			judul="Type;Loca;Ser;";
		} else if (sDtSearch.compareToIgnoreCase("notam1origserie")==0) {
			judul="Type;Orig;Loca;Ser;";
		}
		judul+="1;2;3;4;5;6;7;8;9;10;11;12;13;14;15;16;17;18;19;20;21;22;23;24;25;26;27;28;29;30;31;Sub Total";
		
//		if (sDtSearch.startsWith("msg")) judul="Prio;Dest1;Dest2;Dest3;Dest4;Dest5;Dest6;Dest7;Dest8;Dest9;Dest10;Dest11;Dest12;Dest13;Dest14;Dest15;";
		if (sDtSearch.compareToIgnoreCase("msgfreeats")==0) judul="Text;Date/Time;Filed by;Status;";
		if (sDtSearch.compareToIgnoreCase("msgalr")==0) judul="Aircraft ID;Mode;Code;Type;DEP AD;ETD;Level;Route;DEST AD;EET;Date/Time;Filed by;Status;";
		if (sDtSearch.compareToIgnoreCase("msgrcf")==0) judul="Aircraft ID;SSR Mode;SRR Code;Date/Time;Filed by;Status;";
		if (sDtSearch.compareToIgnoreCase("msgfpl")==0) judul="Acft ID;Mode;Type;DEP AD;ETD;Level;Route;DEST AD;EET;REG;Date/Time;Filed by;Status;";
		if (sDtSearch.compareToIgnoreCase("msgdla")==0 || sDtSearch.compareToIgnoreCase("msgcnl")==0 || 
				sDtSearch.compareToIgnoreCase("msgdep")==0 || sDtSearch.compareToIgnoreCase("msgrqp")==0 ||
				sDtSearch.compareToIgnoreCase("msgrqs")==0) judul="Aircraft ID;SSR Mode;SSR Code;DEP AD;ETD;DEST AD;DOF;Date/Time;Filed by;Status;";
		if (sDtSearch.compareToIgnoreCase("msgchg")==0) judul="Number;Ref.Data;Aircraft ID;Mode;Code;DEP AD;ETD;DEST AD;DOF;Date/Time;Filed by;Status;";
		if (sDtSearch.compareToIgnoreCase("msgarr")==0) judul="Aircraft ID;Mode;Code;DEP AD;ETD;ARR AD;ATD;Date/Time;Filed by;Status;";
		if (sDtSearch.compareToIgnoreCase("msgcdn")==0) judul="Number;Ref.Data;Aircraft ID;Mode;Code;DEP AD;DEST AD;Amendment;Date/Time;Filed by;Status;";
		if (sDtSearch.compareToIgnoreCase("msgcpl")==0) judul="Aircraft ID;Mode;Code;Type;DEP AD;Point;Time;Level;Route;DEST AD;Date/Time;Filed by;Status;";
		if (sDtSearch.compareToIgnoreCase("msgest")==0) judul="Number;Ref.Data;Aircraft ID;Mode;Code;DEP AD;Point;Time;Level;DEST AD;Date/Time;Filed by;Status;";
		if (sDtSearch.compareToIgnoreCase("msgacp")==0) judul="Number;Ref.Data;Aircraft ID;Mode;Code;DEP AD;DEST AD;Date/Time;Filed by;Status;";
		if (sDtSearch.compareToIgnoreCase("msglam")==0) judul="Number;Ref.Data;Date/Time;Filed by;Status;";
		if (sDtSearch.compareToIgnoreCase("msgspl")==0) judul="Aircraft ID;Mode;Code;DEP AD;ETD;DEST AD;EET;Date/Time;Filed by;Status;";
		
		String sub[] = null;
		if (!judul.isEmpty()) {
			sub = judul.split(";");
			for (int i=0; i<sub.length; i++) {
				iCnt++;
			}
		}
		sheet.mergeCells(0, 0, (sub.length-1), 0); //merging all Statistic's Title
    	label = new Label(column, row, s, cellFormat); //set each cell
		sheet.addCell(label); //add all label into XLS sheet
	}
	// ===== end : setting Title

	
	void cellFormatStandard(WritableCellFormat cellFormat) {
		try {
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		    cellFormat.setBorder(Border.ALL, BorderLineStyle.HAIR);
			cellFormat.setAlignment(Alignment.CENTRE); //asli
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// ===== start : setting Content
	private void addLabel(WritableSheet sheet, int column, int row, String s) throws WriteException, RowsExceededException {
		WritableCellFormat cellFormat = new WritableCellFormat(FONT_BOLD);
		
		int irow = 0;
		
//		System.out.println("sDtSearch:" + sDtSearch + "#");
		
		if (sDtSearch.startsWith("msg")) {
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setAlignment(Alignment.LEFT);
			cellFormat.setBorder(Border.ALL, BorderLineStyle.HAIR);
			cellFormat.setBackground(Colour.WHITE);
			if (row == 2) cellFormat.setBackground(Colour.GRAY_25);
		}

		
		else if (sDtSearch.startsWith("sats")||sDtSearch.compareToIgnoreCase("metar")==0) {
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setBorder(Border.ALL, BorderLineStyle.HAIR);
			cellFormat.setBackground(Colour.WHITE);
		}
		
//		if (sDtSearch.compareToIgnoreCase("msgfreeats")==0 ||
//				sDtSearch.compareToIgnoreCase("msglam")==0) {
//
//			cellFormat.setVerticalAlignment(VerticalAlignment.TOP);
//			cellFormat.setAlignment(Alignment.LEFT);
//			cellFormat.setBorder(Border.ALL, BorderLineStyle.HAIR);
//			if (row % 2 == 0) cellFormat.setBackground(Colour.GRAY_25);
//			else cellFormat.setBackground(Colour.WHITE);
//		
//		} else if (sDtSearch.compareToIgnoreCase("msgalr")==0 || 
//				sDtSearch.compareToIgnoreCase("msgfpl")==0 || 
//				sDtSearch.compareToIgnoreCase("msgcpl")==0) {
//			
//			cellFormat.setVerticalAlignment(VerticalAlignment.TOP);
//			cellFormat.setAlignment(Alignment.LEFT);
//			cellFormat.setBorder(Border.ALL, BorderLineStyle.HAIR);
//			if (row % 4 == 2) cellFormat.setBackground(Colour.GREY_25_PERCENT);
//			else cellFormat.setBackground(Colour.WHITE);
//		
//		} else if (sDtSearch.compareToIgnoreCase("msgrcf")==0 ||
//				sDtSearch.compareToIgnoreCase("msgdla")==0 ||
//				sDtSearch.compareToIgnoreCase("msgchg")==0 ||
//				sDtSearch.compareToIgnoreCase("msgcnl")==0 ||
//				sDtSearch.compareToIgnoreCase("msgdep")==0 ||
//				sDtSearch.compareToIgnoreCase("msgarr")==0 ||
//				sDtSearch.compareToIgnoreCase("msgcdn")==0 ||
//				sDtSearch.compareToIgnoreCase("msgest")==0 ||
//				sDtSearch.compareToIgnoreCase("msgacp")==0 ||
//				sDtSearch.compareToIgnoreCase("msgrqp")==0 ||
//				sDtSearch.compareToIgnoreCase("msgrqs")==0 ||
//				sDtSearch.compareToIgnoreCase("msgspl")==0) {
//			cellFormat.setVerticalAlignment(VerticalAlignment.TOP);
//			cellFormat.setAlignment(Alignment.LEFT);
//			cellFormat.setBorder(Border.ALL, BorderLineStyle.HAIR);
//			if (row % 3 == 2) cellFormat.setBackground(Colour.GREY_25_PERCENT);
//			else cellFormat.setBackground(Colour.WHITE);
//		} else 
			
		if (sDtSearch.compareToIgnoreCase("route")==0) {
			cellFormatStandard(cellFormat);
			if (s.length()>4 && column==2) {
				int icnt=0;
				for (String part : getParts(s, 4)) {
					System.out.println(part);
					icnt++;
				}
				//System.out.println(icnt+" jmlnya");
				irow = (icnt*15)*20;
				sheet.setRowView(row, irow);
			}
			
		} else if (sDtSearch.compareToIgnoreCase("notam1")==0) {
			cellFormatStandard(cellFormat);
			if (s.length()>4 && column==1 && s.compareTo("Total")!=0) {
				int icnt=0;
				for (String part : getParts(s, 4)) {
					System.out.println(part);
					icnt++;
				}
				//System.out.println(icnt+" jmlnya");
				irow = (icnt*15)*20;
				sheet.setRowView(row, irow);
			}
			
		} else if (sDtSearch.compareToIgnoreCase("notam1orig")==0) {
			cellFormatStandard(cellFormat);
			if (s.length()>4 && (column==1 || (column==2 && s.compareTo("Total")!=0))) {
				int icnt=0;
				for (String part : getParts(s, 4)) {
					System.out.println(part);
					icnt++;
				}
				//System.out.println(icnt+" jmlnya");
				irow = (icnt*15)*20;
				sheet.setRowView(row, irow);
			} 
			
		} else if (sDtSearch.compareToIgnoreCase("notam1serie")==0) {
			cellFormatStandard(cellFormat);
			if (s.length()>4 && column==1) {
				int icnt=0;
				for (String part : getParts(s, 4)) {
					System.out.println(part);
					icnt++;
				}
				//System.out.println(icnt+" jmlnya");
				irow = (icnt*15)*20;
				sheet.setRowView(row, irow);
			} 
			
		} else if (sDtSearch.compareToIgnoreCase("notam1origserie")==0) {
			cellFormatStandard(cellFormat);
			if (s.length()>4 && (column==1 || column==2)) {
				int icnt=0;
				for (String part : getParts(s, 4)) {
					System.out.println(part);
					icnt++;
				}
				//System.out.println(icnt+" jmlnya");
				irow = (icnt*15)*20;
				sheet.setRowView(row, irow);
			} 
			
//		} else {
//			cellFormatStandard(cellFormat);
		}
		
		Label label;
		label = new Label(column, row, s, cellFormat);
		sheet.addCell(label);
		
	}
	// ===== end : setting Content
	
	
	 private static List<String> getParts(String string, int partitionSize) {
		 List<String> parts = new ArrayList<String>();
		 int len = string.length();
		 for (int i=0; i<len; i+=partitionSize) {
			 parts.add(string.substring(i, Math.min(len, i + partitionSize)));
		 }
		 return parts;
	 }
	 
	 
	// ===== start : setting Content Header (for statistic) and all content (for ATS, NOTAM, METEO Messages)
	private void addLabelBold(WritableSheet sheet, int column, int row, String s) throws WriteException, RowsExceededException {
		// create font setting for content
		WritableFont WF_BOLD = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat FONT_BOLD = new WritableCellFormat(WF_BOLD);
	    FONT_BOLD.setWrap(true);
	    
		WritableCellFormat cellFormat = new WritableCellFormat(FONT_BOLD);
	    cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
	    cellFormat.setBackground(Colour.GRAY_25); //asli
	    cellFormat.setAlignment(Alignment.CENTRE);//asli
	    cellFormat.setBorder(Border.ALL, BorderLineStyle.HAIR); //asli
	    
	    Label label;
		label = new Label(column, row, s, cellFormat);
		sheet.addCell(label);
	}
	// ===== end : setting Content Header
} 