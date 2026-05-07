package readwrite;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;

import actions.RuntimeCmd;

import displays.Lists;
import displays.MainForm;

public class WriteToXLS2 {
//	public static final java.lang.String FONT_ARIAL="";
	public static String FONT_ARIAL="Courier New";
	static ReadFromFile rff = new ReadFromFile();
	static String strFILENAME="",strTITLE="",strHEAD="",strWIDTH_HEAD="",strBODY="",strJML_BODY="";
	
//	public static void main(String[] args) {
	public WriteToXLS2(String sJudul, String sMsgType, String sData, String sJmldt, String sFlnm, String sTrace) {
		
		try {
			strFILENAME=MainForm.path+sFlnm+".xls";
			strTITLE=sJudul;
			strBODY=sData;
			strJML_BODY=sJmldt;
			if (!strJML_BODY.isEmpty()) strJML_BODY+=" row(s) data in this file"; else strJML_BODY="";
			
			rff.readTblCol();
			if (sMsgType.compareToIgnoreCase("msgFreetext")==0 || sMsgType.compareToIgnoreCase("msgrqm")==0 ) 
			{ strWIDTH_HEAD=rff.getXlsWidthFREEATS(); strHEAD=rff.getColnmFREEATS();  
			if (sMsgType.compareToIgnoreCase("msgrqm")==0 && strHEAD.contains(",Freetext")) strHEAD=strHEAD.replaceFirst(",Freetext", ",Request Message");
				
			 }
			else if (sMsgType.compareToIgnoreCase("msgALR")==0 || sMsgType.compareToIgnoreCase("msgCPL")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthALR(); strHEAD=rff.getColnmALR(); }
			else if (sMsgType.compareToIgnoreCase("msgRCF")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthRCF(); strHEAD=rff.getColnmRCF(); }
			else if (sMsgType.compareToIgnoreCase("msgFPL")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthFPL(); strHEAD=rff.getColnmFPL(); }
			else if (sMsgType.compareToIgnoreCase("msgDLA")==0 || sMsgType.compareToIgnoreCase("msgCHG")==0 ||
					sMsgType.compareToIgnoreCase("msgCNL")==0 || sMsgType.compareToIgnoreCase("msgDEP")==0 ||
					sMsgType.compareToIgnoreCase("msgRQP")==0 || sMsgType.compareToIgnoreCase("msgRQS")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthDLA(); strHEAD=rff.getColnmDLA(); }
			else if (sMsgType.compareToIgnoreCase("msgARR")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthARR(); strHEAD=rff.getColnmARR(); }
			else if (sMsgType.compareToIgnoreCase("msgCDN")==0)
			{ strWIDTH_HEAD=rff.getXlsWidthCDN(); strHEAD=rff.getColnmCDN(); }
			else if (sMsgType.compareToIgnoreCase("msgEST")==0)
			{ strWIDTH_HEAD=rff.getXlsWidthEST(); strHEAD=rff.getColnmEST(); }
			else if (sMsgType.compareToIgnoreCase("msgACP")==0 || sMsgType.compareToIgnoreCase("msgSPL")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthACP(); strHEAD=rff.getColnmACP(); }
			else if (sMsgType.compareToIgnoreCase("msgLAM")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthLAM(); strHEAD=rff.getColnmLAM(); }
			//NOTAM 
			else if (sMsgType.compareToIgnoreCase("msgNOTAM")==0 || sMsgType.compareToIgnoreCase("msgExpired_NTM")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthNOTAM(); strHEAD=rff.getColnmNOTAM(); }
			else if (sMsgType.compareToIgnoreCase("msgRQNTM")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthRQNTM(); strHEAD=rff.getColnmRQNTM(); }
//			else if (sMsgType.compareToIgnoreCase("msgChecklist")==0 || sMsgType.compareToIgnoreCase("msgActive_NTM")==0 ||
//					sMsgType.compareToIgnoreCase("msgExpired_NTM")==0)
			else if (sMsgType.compareToIgnoreCase("msgChecklist")==0 || sMsgType.compareToIgnoreCase("msgActive_NTM")==0)
			{ strWIDTH_HEAD=rff.getXlsWidthCHKNTM(); strHEAD=rff.getColnmCHKNTM(); }
			else if (sMsgType.compareToIgnoreCase("msgSNOWTAM")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthSNOWTAM(); strHEAD=rff.getColnmSNOWTAM(); }		
			else if (sMsgType.compareToIgnoreCase("msgASHTAM")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthASHTAM(); strHEAD=rff.getColnmASHTAM(); }		
			else if (sMsgType.compareToIgnoreCase("msgBIRDTAM")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthBIRDTAM(); strHEAD=rff.getColnmBIRDTAM(); }		
			else if (sMsgType.compareToIgnoreCase("msgRQN")==0 || sMsgType.compareToIgnoreCase("msgRQL")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthRQN(); strHEAD=rff.getColnmRQN(); }	
			//METEO
			else if (sMsgType.compareToIgnoreCase("msgMETAR")==0 || sMsgType.compareToIgnoreCase("msgSPECI")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthMETAR(); strHEAD=rff.getColnmMETAR(); }
			else if (sMsgType.compareToIgnoreCase("msgSIGMET")==0 || sMsgType.compareToIgnoreCase("msgAIRMET")==0 || 
					sMsgType.compareToIgnoreCase("msgWINSWAR")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthSIGMET(); strHEAD=rff.getColnmSIGMET(); }
			else if (sMsgType.compareToIgnoreCase("msgAIREP")==0 || sMsgType.compareToIgnoreCase("msgTAFOR")==0 ||
					sMsgType.compareToIgnoreCase("msgSYNOP")==0 || sMsgType.compareToIgnoreCase("msgARFOR")==0 ||
					sMsgType.compareToIgnoreCase("msgROFOR")==0 || sMsgType.compareToIgnoreCase("msgWINTEM")==0 ||
					sMsgType.compareToIgnoreCase("msgADV")==0 || sMsgType.compareToIgnoreCase("msgWARSYN")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthAIREP(); strHEAD=rff.getColnmAIREP(); }
			//Retrieval
			else if ((sMsgType.compareToIgnoreCase("msgIncoming")==0 || sMsgType.compareToIgnoreCase("msgOutgoing")==0) 
					&& sTrace.compareToIgnoreCase("trace")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthIncoming(); strHEAD=rff.getColnmIncoming(); }
			else if ((sMsgType.compareToIgnoreCase("msgIncoming")==0 || sMsgType.compareToIgnoreCase("msgOutgoing")==0) 
					&& sTrace.compareToIgnoreCase("notrace")==0) { 
				String width = rff.getXlsWidthIncoming();
				if (!width.isEmpty() && width.contains(",")) {
					int index = width.lastIndexOf(",");
					width = width.substring(0, index);
					strWIDTH_HEAD = width;
				}
				
				String jdl = rff.getColnmIncoming();
				if (!jdl.isEmpty() && jdl.contains(",")) {
					int index = jdl.lastIndexOf(",");
					jdl = jdl.substring(0, index);
					strHEAD = jdl;
				}
			}
			//Reject
			else if (sMsgType.compareToIgnoreCase("msgRejected")==0) 
			{ strWIDTH_HEAD=rff.getXlsWidthReject(); strHEAD=rff.getColnmReject(); }
			//Statistic
//			else if (sMsgType.compareToIgnoreCase("msgStatistic")==0) 
//			{ strWIDTH_HEAD=rff.getXlsWidthStatistic(); strHEAD=rff.getColnmStatistic(); }
//			
//			System.out.println("strWIDTH_HEAD::" + strWIDTH_HEAD + "@");
//			System.out.println("strHEAD::" + strHEAD + "@");
			
			String subTitle[]=strHEAD.split(",");
			String subWidth[]=strWIDTH_HEAD.split(",");
			String subData[]=strBODY.split("#");
			
			FileOutputStream fileOut = new FileOutputStream(strFILENAME);
			HSSFWorkbook workbook = new HSSFWorkbook();
			if (strTITLE.contains("/")) strTITLE=strTITLE.replace("/", "-");
			HSSFSheet worksheet = workbook.createSheet(strTITLE);
			
			PrintSetup ps=worksheet.getPrintSetup();
			ps.setLandscape(true);
			ps.setPaperSize(PrintSetup.A4_PAPERSIZE);
			ps.setFitWidth((short)1);
			ps.setFitHeight((short)0);
			ps.setFooterMargin(0.05);
			ps.setHeaderMargin(0.05);
			worksheet.setMargin(Sheet.LeftMargin,0.25);
			worksheet.setMargin(Sheet.RightMargin,0.25);
			worksheet.setMargin(Sheet.TopMargin,0.05);
			worksheet.setMargin(Sheet.BottomMargin,0.25);
			worksheet.setHorizontallyCenter(true);
			worksheet.setVerticallyCenter(false);
			worksheet.createFreezePane(0,1,0,1);

			HSSFFont fontTITLE = workbook.createFont();		getFont(fontTITLE, HSSFFont.BOLDWEIGHT_BOLD, (short) 12);
			HSSFFont fontHEAD = workbook.createFont(); 		getFont(fontHEAD, HSSFFont.BOLDWEIGHT_BOLD, (short) 10);
			HSSFFont fontCONTENT = workbook.createFont(); 	getFont(fontCONTENT, HSSFFont.BOLDWEIGHT_NORMAL, (short) 10);
			HSSFFont fontINFO = workbook.createFont(); 		getFont(fontINFO, HSSFFont.BOLDWEIGHT_BOLD, (short) 10);
			fontINFO.setItalic(true);
			
			//TITLE
			HSSFRow rowTitle = worksheet.createRow((short) 0);
			HSSFCell cellTitle = rowTitle.createCell((short) 0);
			cellTitle.setCellValue(strTITLE);
			HSSFCellStyle cellStyleTitle = workbook.createCellStyle();
			cellStyleTitle.setFillForegroundColor(HSSFColor.WHITE.index);
			cellStyleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyleTitle.setFont(fontTITLE);
			cellStyleTitle.setBorderBottom(HSSFCellStyle.BORDER_HAIR);
			cellTitle.setCellStyle(cellStyleTitle);
			worksheet.addMergedRegion(new CellRangeAddress(0,0,0,(subTitle.length-1)));//first row(0-based all),last row,first column,last column
			//INFO
			HSSFRow rowInfo = worksheet.createRow((short) 1);
			HSSFCell cellInfo = rowInfo.createCell((short) 0);
			cellInfo.setCellValue(strJML_BODY);
			HSSFCellStyle cellStyleInfo = workbook.createCellStyle();
			cellStyleInfo.setFillForegroundColor(HSSFColor.WHITE.index);
			cellStyleInfo.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyleInfo.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			cellStyleInfo.setFont(fontINFO);
			cellInfo.setCellStyle(cellStyleInfo);
			worksheet.addMergedRegion(new CellRangeAddress(1,1,0,(subTitle.length-1)));//first row(0-based all),last row,first column,last column
			//HEAD
			HSSFRow rowHead = worksheet.createRow((short) 2);
			for (int x=0; x<subTitle.length; x++) {
				HSSFCell cell = rowHead.createCell((short) x);
				cell.setCellValue(subTitle[x]);
				worksheet.setColumnWidth(x, Integer.parseInt(subWidth[x]));
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				getCellStyle(cellStyle,HSSFColor.GREY_25_PERCENT.index,HSSFCellStyle.ALIGN_CENTER,fontHEAD);				
				cell.setCellStyle(cellStyle);
			}
			//BODY
			for (int i=0; i<subData.length; i++) {
				String cellValue[] = subData[i].split(";");
				HSSFRow rowBody = worksheet.createRow((short) i+3);
				for (int y=0; y<cellValue.length; y++) {
					HSSFCell cell = rowBody.createCell((short) y);
					cell.setCellValue(cellValue[y]);
					HSSFCellStyle cellStyle = workbook.createCellStyle();
					cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
					getCellStyle(cellStyle,HSSFColor.AUTOMATIC.index,HSSFCellStyle.ALIGN_LEFT,fontCONTENT);
					cell.setCellStyle(cellStyle);
				}
			}
			
			//Get current Date and Time
			Date date = new Date(System.currentTimeMillis());
			DateFormat df = new SimpleDateFormat("EEE, dd/MMM/yyyy HH:mm:ss");
			   
			HSSFHeader header=worksheet.getHeader();
			header.setRight(df.format(date));
			
			final HSSFFooter footer=worksheet.getFooter();
			footer.setRight("Page " + HSSFFooter.page() + " of "+ HSSFFooter.numPages() );
			
//			int sheetIndex, int startColumn, int endColumn, int startRow, int endRow).
			workbook.setRepeatingRowsAndColumns(0,-1,-1,0,2);
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		new RuntimeCmd(cmdXLS, MainForm.path+getFlnm(sID)+".xls");
		new RuntimeCmd(Lists.cmdXLS, strFILENAME);
	}
	
	private static void getCellStyle(HSSFCellStyle cellStyle,short frColor,short align,HSSFFont font) {
//		cellStyle.setFillForegroundColor(frColor);
//		cellStyle.setFillBackgroundColor(frColor);
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setAlignment(align);
		cellStyle.setFont(font);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_HAIR);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_HAIR);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_HAIR);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_HAIR);
	}
	
	private static void getFont(HSSFFont font,short bw,short height) {
		font.setBoldweight(bw);
		font.setColor(HSSFColor.BLACK.index);
		font.setFontName(FONT_ARIAL);
		font.setFontHeightInPoints(height);
	}
}


//String title="tblnm,id,CID,CSN,TMS,Date/Time,Acft ID,Type,DEP,ETD,Level,Route,DEST,REG/";
//String width="0,0,1500,1500,2000,4700,2500,2000,2000,1500,2000,6000,2000,2500";
//String data=
//	"air_message2015_09,1,RCA,0001, ,2015-09-01 15:14:59,GIA100,B737,WIII,1515,F300,CGK TO MKS\ntes line2\ntes line3\ntes line4,WAAA,BTV147#" +
//	"air_message2015_09,2,LIA,0002,011515,2015-09-01 15:15:00,GIA101,DOVE,WIBB,1516,F210,JMB TO SRG,WASS,BTV258#" +
//	"air_message2015_09,3,CBC,0003,011516,2015-09-01 15:16:38,GIA102,A142,WICC, ,F330,BDO TO PKG\nw11 hsg n846\ntest,WALR,BTV369#" +
//	"air_message2015_09,4,CRA,0004,011517,2015-09-01 15:18:59,GIA103,B776,WARR,1518, ,SBY TO BDO,WICC,BTV123#" +
//	"air_message2015_09,5,CCA,0005,011518,2015-09-01 15:19:59,GIA104,B733,WALR,1519,F300,PLK TO DPS,WADD,BTV456#"+
//	"air_message2015_09,1,RCA,0001, ,2015-09-01 15:14:59,GIA100,B737,WIII,1515,F300,CGK TO MKS\ntes line2\ntes line3\ntes line4,WAAA,BTV147#" +
//	"air_message2015_09,2,LIA,0002,011515,2015-09-01 15:15:00,GIA101,DOVE,WIBB,1516,F210,JMB TO SRG,WASS,BTV258#" +
//	"air_message2015_09,3,CBC,0003,011516,2015-09-01 15:16:38,GIA102,A142,WICC, ,F330,BDO TO PKG\nw11 hsg n846\ntest,WALR,BTV369#" +
//	"air_message2015_09,4,CRA,0004,011517,2015-09-01 15:18:59,GIA103,B776,WARR,1518, ,SBY TO BDO,WICC,BTV123#" +
//	"air_message2015_09,5,CCA,0005,011518,2015-09-01 15:19:59,GIA104,B733,WALR,1519,F300,PLK TO DPS,WADD,BTV456#"+
//	"air_message2015_09,1,RCA,0001, ,2015-09-01 15:14:59,GIA100,B737,WIII,1515,F300,CGK TO MKS\ntes line2\ntes line3\ntes line4,WAAA,BTV147#" +
//	"air_message2015_09,2,LIA,0002,011515,2015-09-01 15:15:00,GIA101,DOVE,WIBB,1516,F210,JMB TO SRG,WASS,BTV258#" +
//	"air_message2015_09,3,CBC,0003,011516,2015-09-01 15:16:38,GIA102,A142,WICC, ,F330,BDO TO PKG\nw11 hsg n846\ntest,WALR,BTV369#" +
//	"air_message2015_09,4,CRA,0004,011517,2015-09-01 15:18:59,GIA103,B776,WARR,1518, ,SBY TO BDO,WICC,BTV123#" +
//	"air_message2015_09,5,CCA,0005,011518,2015-09-01 15:19:59,GIA104,B733,WALR,1519,F300,PLK TO DPS,WADD,BTV456#"+
//	"air_message2015_09,1,RCA,0001, ,2015-09-01 15:14:59,GIA100,B737,WIII,1515,F300,CGK TO MKS\ntes line2\ntes line3\ntes line4,WAAA,BTV147#" +
//	"air_message2015_09,2,LIA,0002,011515,2015-09-01 15:15:00,GIA101,DOVE,WIBB,1516,F210,JMB TO SRG,WASS,BTV258#" +
//	"air_message2015_09,3,CBC,0003,011516,2015-09-01 15:16:38,GIA102,A142,WICC, ,F330,BDO TO PKG\nw11 hsg n846\ntest,WALR,BTV369#" +
//	"air_message2015_09,4,CRA,0004,011517,2015-09-01 15:18:59,GIA103,B776,WARR,1518, ,SBY TO BDO,WICC,BTV123#" +
//	"air_message2015_09,5,CCA,0005,011518,2015-09-01 15:19:59,GIA104,B733,WALR,1519,F300,PLK TO DPS,WADD,BTV456#"+
//	"air_message2015_09,1,RCA,0001, ,2015-09-01 15:14:59,GIA100,B737,WIII,1515,F300,CGK TO MKS\ntes line2\ntes line3\ntes line4,WAAA,BTV147#" +
//	"air_message2015_09,2,LIA,0002,011515,2015-09-01 15:15:00,GIA101,DOVE,WIBB,1516,F210,JMB TO SRG,WASS,BTV258#" +
//	"air_message2015_09,3,CBC,0003,011516,2015-09-01 15:16:38,GIA102,A142,WICC, ,F330,BDO TO PKG\nw11 hsg n846\ntest,WALR,BTV369#" +
//	"air_message2015_09,4,CRA,0004,011517,2015-09-01 15:18:59,GIA103,B776,WARR,1518, ,SBY TO BDO,WICC,BTV123#" +
//	"air_message2015_09,5,CCA,0005,011518,2015-09-01 15:19:59,GIA104,B733,WALR,1519,F300,PLK TO DPS,WADD,BTV456#";
//String judul="List of FPL Messages";
//String jmldt="5 row(s)";