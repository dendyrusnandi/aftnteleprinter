package readwrite;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

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
import displays.MainForm;


public class WriteToXLS {
	
	ReadFromFile rff = new ReadFromFile();
	
	WritableFont WF_NORMAL = new WritableFont(WritableFont.COURIER, 8);
	WritableFont WF_ITALIC = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD, true);
	WritableFont WF_BOLD = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
	WritableFont WF_BIGGER_BOLD = new WritableFont(WritableFont.COURIER, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
	
	WritableCellFormat FONT_NORMAL = new WritableCellFormat(WF_NORMAL);
	WritableCellFormat FONT_ITALIC = new WritableCellFormat(WF_ITALIC);
	WritableCellFormat FONT_BOLD = new WritableCellFormat(WF_BOLD);
	WritableCellFormat FONT_BIGGER_BOLD = new WritableCellFormat(WF_BIGGER_BOLD);
	 
	private String inputFile;
	static String sTitle="",sMsgType="",sData="",sJmlData="",sFlnm="",widthCol="",judul="",sTrace="";
	
	
	public void setOutputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	
	
	public void setData(String title, String msgtyp, String data, String jmldt, String flnm, String strTrace) {
		sTitle = title;
		sMsgType = msgtyp;
		sData = data;
		sJmlData = jmldt;
		sFlnm = MainForm.path+flnm+".xls";
		sTrace = strTrace;
		
//		System.out.println("sTitle:" + sTitle + "#");
//		System.out.println("sMsgType:" + sMsgType + "#");
//		System.out.println("sData:" + sData + "#");
//		System.out.println("sJmlData:" + sJmlData + "#");
//		System.out.println("sFlnm:" + sFlnm + "#");
//		System.out.println("sTrace:" + sTrace + "#");
		
		if (!sJmlData.isEmpty()) sJmlData+=" row(s) in this file"; else sJmlData="";
		
		WriteToXLS test = new WriteToXLS();
		test.setOutputFile(sFlnm);
		try {
			test.write();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	    createLabel1(excelSheet);
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
	

	private void createLabel1(WritableSheet sheet) throws WriteException {
	    FONT_NORMAL.setWrap(true); 
	    CellView cv = new CellView();
	    cv.setFormat(FONT_NORMAL);
	    cv.setAutosize(true);
	    // Write a few headers
	    addCaption1(sheet, 0, 0, sJmlData);
	}
	
	// ===== start : create Title of the XLS file
	private void createLabel(WritableSheet sheet) throws WriteException {
	    FONT_BOLD.setWrap(true); 
	    CellView cv = new CellView();
	    cv.setFormat(FONT_BOLD);
	    cv.setAutosize(true);
	    // Write a few headers
	    addCaption(sheet, 0, 0, sTitle);
	}
	
	void sMsgType() {
		rff.readTblCol();
		if (sMsgType.compareToIgnoreCase("msgFreetext")==0 || sMsgType.compareToIgnoreCase("msgrqm")==0 ) 
		{ widthCol = rff.getXlsWidthFREEATS(); judul = rff.getColnmFREEATS(); }
		else if (sMsgType.compareToIgnoreCase("msgALR")==0 || sMsgType.compareToIgnoreCase("msgCPL")==0) 
		{ widthCol = rff.getXlsWidthALR(); judul = rff.getColnmALR(); }
		else if (sMsgType.compareToIgnoreCase("msgRCF")==0) 
		{ widthCol = rff.getXlsWidthRCF(); judul = rff.getColnmRCF(); }
		else if (sMsgType.compareToIgnoreCase("msgFPL")==0) 
		{ widthCol = rff.getXlsWidthFPL(); judul = rff.getColnmFPL(); }
		else if (sMsgType.compareToIgnoreCase("msgDLA")==0 || sMsgType.compareToIgnoreCase("msgCHG")==0 ||
				sMsgType.compareToIgnoreCase("msgCNL")==0 || sMsgType.compareToIgnoreCase("msgDEP")==0 ||
				sMsgType.compareToIgnoreCase("msgRQP")==0 || sMsgType.compareToIgnoreCase("msgRQS")==0) 
		{ widthCol = rff.getXlsWidthDLA(); judul = rff.getColnmDLA(); }
		else if (sMsgType.compareToIgnoreCase("msgARR")==0) 
		{ widthCol = rff.getXlsWidthARR(); judul = rff.getColnmARR(); }
		else if (sMsgType.compareToIgnoreCase("msgCDN")==0)
		{ widthCol = rff.getXlsWidthCDN(); judul = rff.getColnmCDN(); }
		else if (sMsgType.compareToIgnoreCase("msgEST")==0)
		{ widthCol = rff.getXlsWidthEST(); judul = rff.getColnmEST(); }
		else if (sMsgType.compareToIgnoreCase("msgACP")==0 || sMsgType.compareToIgnoreCase("msgSPL")==0) 
		{ widthCol = rff.getXlsWidthACP(); judul = rff.getColnmACP(); }
		else if (sMsgType.compareToIgnoreCase("msgLAM")==0) 
		{ widthCol = rff.getXlsWidthLAM(); judul = rff.getColnmLAM(); }
		//NOTAM 
		else if (sMsgType.compareToIgnoreCase("msgNOTAM")==0) 
		{ widthCol = rff.getXlsWidthNOTAM(); judul = rff.getColnmNOTAM(); }
		else if (sMsgType.compareToIgnoreCase("msgRQNTM")==0) 
		{ widthCol = rff.getXlsWidthRQNTM(); judul = rff.getColnmRQNTM(); }
		else if (sMsgType.compareToIgnoreCase("msgChecklist")==0 || sMsgType.compareToIgnoreCase("msgActive_NTM")==0 ||
				sMsgType.compareToIgnoreCase("msgExpired_NTM")==0) 
		{ widthCol = rff.getXlsWidthCHKNTM(); judul = rff.getColnmCHKNTM(); }
		else if (sMsgType.compareToIgnoreCase("msgSNOWTAM")==0) 
		{ widthCol = rff.getXlsWidthSNOWTAM(); judul = rff.getColnmSNOWTAM(); }		
		else if (sMsgType.compareToIgnoreCase("msgASHTAM")==0) 
		{ widthCol = rff.getXlsWidthASHTAM(); judul = rff.getColnmASHTAM(); }		
		else if (sMsgType.compareToIgnoreCase("msgBIRDTAM")==0) 
		{ widthCol = rff.getXlsWidthBIRDTAM(); judul = rff.getColnmBIRDTAM(); }		
		else if (sMsgType.compareToIgnoreCase("msgRQN")==0 || sMsgType.compareToIgnoreCase("msgRQL")==0) 
		{ widthCol = rff.getXlsWidthRQN(); judul = rff.getColnmRQN(); }	
		//METEO
		else if (sMsgType.compareToIgnoreCase("msgMETAR")==0 || sMsgType.compareToIgnoreCase("msgSPECI")==0) 
		{ widthCol = rff.getXlsWidthMETAR(); judul = rff.getColnmMETAR(); }
		else if (sMsgType.compareToIgnoreCase("msgSIGMET")==0 || sMsgType.compareToIgnoreCase("msgAIRMET")==0 || 
				sMsgType.compareToIgnoreCase("msgWINSWAR")==0) 
		{ widthCol = rff.getXlsWidthSIGMET(); judul = rff.getColnmSIGMET(); }
		else if (sMsgType.compareToIgnoreCase("msgAIREP")==0 || sMsgType.compareToIgnoreCase("msgTAFOR")==0 ||
				sMsgType.compareToIgnoreCase("msgSYNOP")==0 || sMsgType.compareToIgnoreCase("msgARFOR")==0 ||
				sMsgType.compareToIgnoreCase("msgROFOR")==0 || sMsgType.compareToIgnoreCase("msgWINTEM")==0 ||
				sMsgType.compareToIgnoreCase("msgADV")==0 || sMsgType.compareToIgnoreCase("msgWARSYN")==0) 
		{ widthCol = rff.getXlsWidthAIREP(); judul = rff.getColnmAIREP(); }
		//Retrieval
		else if ((sMsgType.compareToIgnoreCase("msgIncoming")==0 || sMsgType.compareToIgnoreCase("msgOutgoing")==0) 
				&& sTrace.compareToIgnoreCase("trace")==0) 
		{ widthCol = rff.getXlsWidthIncoming(); judul = rff.getColnmIncoming(); }
		else if ((sMsgType.compareToIgnoreCase("msgIncoming")==0 || sMsgType.compareToIgnoreCase("msgOutgoing")==0) 
				&& sTrace.compareToIgnoreCase("notrace")==0) 
		{ 
			String width = rff.getXlsWidthIncoming();
			if (!width.isEmpty() && width.contains(",")) {
				int index = width.lastIndexOf(",");
				width = width.substring(0, index);
				widthCol = width;
			}
//			widthCol = rff.getXlsWidthIncoming(); 
//			System.out.println("widthcol=" + widthCol + "#");
			
			String jdl = rff.getColnmIncoming();
			if (!jdl.isEmpty() && jdl.contains(",")) {
				int index = jdl.lastIndexOf(",");
				jdl = jdl.substring(0, index);
				judul = jdl;
			}
//			judul = rff.getColnmIncoming(); 
//			System.out.println("judul=" + judul + "#");
		}
		//Reject
		else if (sMsgType.compareToIgnoreCase("msgRejected")==0) 
		{ widthCol = rff.getXlsWidthReject(); judul = rff.getColnmReject(); }
	}
	
	// ===== start : create Content of the XLS file
	private void createContent(WritableSheet sheet) throws WriteException, RowsExceededException {
		String msgBody="";
		sMsgType();
		setWidthColumn(widthCol, sheet); 
		
		String suby[] = null;
		if (judul.contains(",")) judul=judul.replaceAll(",", ";").concat(";\n");
		msgBody = judul+sData;
		
		suby = msgBody.split("\n");
		for (int y=0; y<suby.length; y++) {
		    String subx[] = null;
		    subx = suby[y].split(";");
		    for (int i=0; i<subx.length; i++) {
		    	if (sMsgType.startsWith("msg")) {
		    		if (subx[i].contains("~")) subx[i] = subx[i].replaceAll("~", "\n");
		    		addLabel(sheet, i, y+2, subx[i]);
		    	}
		    }
		}
	}
	
	void setWidthColumn(String widthCol, WritableSheet sheet) {
		if (!widthCol.isEmpty()) {
			String sub[] = widthCol.split(",");
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
		
		int iCnt=0, iMerge=0;
		sMsgType();
		if (judul.contains(",")) judul=judul.replaceAll(",", ";").concat(";\n");
		String sub[] = null;
		if (!judul.isEmpty()) {
			sub = judul.split(";");
			for (int i=0; i<sub.length; i++) {
				iCnt++;
			}
		}
		
		iMerge=(sub.length-2);
		sheet.mergeCells(0, 0, iMerge, 0); //merging all Statistic's Title
    	label = new Label(column, row, s, cellFormat); //set each cell
		sheet.addCell(label); //add all label into XLS sheet
	}
	
	// ===== start : setting Title (cell format, such as : font face, font size, font style, cell vertical alignment, cell horizontal alignment, cell merge)
	private void addCaption1(WritableSheet sheet, int column, int row, String s) throws RowsExceededException, WriteException {
		WritableCellFormat cellFormat = new WritableCellFormat(FONT_ITALIC);
	    cellFormat.setAlignment(Alignment.LEFT);
	    cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		int iCnt=0;
		sMsgType();
		if (judul.contains(",")) judul=judul.replaceAll(",", ";").concat(";\n");
		String sub[] = null;
		if (!judul.isEmpty()) {
			sub = judul.split(";");
			for (int i=0; i<sub.length; i++) {
				iCnt++;
			}
		}

		sheet.mergeCells(1, 1, (sub.length-1), 1); //merging all Statistic's Title
    	Label label1 = new Label(1, 1, sJmlData, cellFormat); //set each cell
		sheet.addCell(label1); //add all label into XLS sheet
	}
	
	void cellFormatStandard(WritableCellFormat cellFormat) {
		try {
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		    cellFormat.setBorder(Border.ALL, BorderLineStyle.HAIR);
			cellFormat.setAlignment(Alignment.CENTRE); //asli
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}
	
	// ===== start : setting Content
	private void addLabel(WritableSheet sheet, int column, int row, String s) throws WriteException, RowsExceededException {
		WritableCellFormat cellFormat = new WritableCellFormat(FONT_BOLD);
		
		if (sMsgType.startsWith("msg")) {
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setAlignment(Alignment.LEFT);
			cellFormat.setBorder(Border.ALL, BorderLineStyle.HAIR);
			cellFormat.setBackground(Colour.WHITE);
			if (row == 2) cellFormat.setBackground(Colour.GRAY_25);
		}
		
		Label label;
		label = new Label(column, row, s, cellFormat);
		int heightInPoints = 20*20;
	    sheet.setRowView(row, heightInPoints);//sheet.setRowView(row, heightInPoints, false);
		sheet.addCell(label);
		
	}
} 
