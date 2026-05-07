package ti_iais;

import java.io.FileOutputStream;

import jxl.format.Colour;

import org.eclipse.swt.widgets.Display;

import readwrite.ReadFromFile;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import displays.AmscSplashScreen2;

public class HeaderFooter {
//	private static String FILE = "/tmp/ramdisk0/PIB.pdf";
//	private static String FILEALL = "/tmp/ramdisk0/PIBAll.pdf";
	
	private static String HEADER = "test header";
	private static String FOOTER = "test footer";
	String sFlight="",sID="";
	
	//font setting for statistic
	private static Font FONT_BOLD = new Font(Font.FontFamily.COURIER, 8, Font.BOLD);
	private static Font FONT_BIGGER_BOLD = new Font(Font.FontFamily.COURIER, 10, Font.BOLD);

	//font for ATS,NOTAM,METEO,BULLETIN MESSAGE
	private static Font FONT_MESSAGE = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
	
	//font setting for bulletin	
	private static Font biggerBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);//11 // CGK 7 Juni 2012, untuk MKS buka lagi
	private static Font biggestBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);//14 // CGK 7 Juni 2012, untuk MKS buka lagi
//	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static Font smallestBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	private static Font HEADER_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
	private static Font BODY_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
	private static Font smallestFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);	
	
	Display display = AmscSplashScreen2.display;//new Display();
	String sAd1="",sAd2="",sAd3="",dtSearch="",jmldata="",data="",dataAll="";
	
	Font fontStyleFooters = FontFactory.getFont(FontFactory.COURIER, 9, Font.NORMAL);
	ReadFromFile rff = new ReadFromFile();

	int ijml=0;
	

	/** Constructor */
	public HeaderFooter() {
//		rff.readPDF();
		rff.readConfiguration();
	}
	
	// start : button Print & Print All in ATS, NOTAM, METEO Messages -----
	public void setPrint(String dt,String FILE,String jml) {
		data=dt;
		jmldata=jml;
		
//		if (!jdbc.getHeader().isEmpty()) HEADER=jdbc.getHeader();
//		if (!jdbc.getFooter().isEmpty()) FOOTER=jdbc.getFooter();
		if (!rff.getPDFHeader().isEmpty()) HEADER=rff.getPDFHeader();
		if (!rff.getPDFFooter().isEmpty()) FOOTER=rff.getPDFFooter();

		try {
			Document document = new Document(PageSize.A4, rff.getPDFleft(), rff.getPDFright(), rff.getPDFtop(), rff.getPDFbottom());
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
	        TableHeader event = new TableHeader();
	        writer.setPageEvent(event);
	        TableFooter event1 = new TableFooter();
	        writer.setPageEvent(event1);

			document.open();
			event.setHeader(HEADER);
			event1.setFooter(FOOTER);
			addTitlePagePrint(document);
			document.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addTitlePagePrint(Document document) throws DocumentException {
		//membuat header: address
		PdfPTable table = new PdfPTable(2);
		table.setHorizontalAlignment(100);
	
		PdfPCell cella = new PdfPCell(new Paragraph(""));
		PdfPCell cellb = new PdfPCell(new Paragraph(""));
		PdfPCell cellc = new PdfPCell(new Paragraph(""));
		PdfPCell celld = new PdfPCell(new Paragraph(""));

        cella.setBorder(Rectangle.NO_BORDER);   // removes border
        cellb.setBorder(Rectangle.NO_BORDER);   // removes border
        cellc.setBorder(Rectangle.NO_BORDER);   // removes border
        celld.setBorder(Rectangle.NO_BORDER);   // removes border
        
        table.setWidthPercentage(100);
        table.addCell(cella);
        table.addCell(cellb);
        table.addCell(cellc);
        table.addCell(celld);
        
		Paragraph dt = new Paragraph(data, FONT_MESSAGE);
		document.add(dt);
        
		Paragraph dtJML = new Paragraph(jmldata, FONT_MESSAGE);
		document.add(dtJML);
	}
	// end : button Print & Print All in ATS, NOTAM, METEO Messages -----
	
	
	int getJml() {
		if (dtSearch.compareToIgnoreCase("status")==0) ijml=35;
		else if (dtSearch.compareToIgnoreCase("level")==0) ijml=35;
		else if (dtSearch.compareToIgnoreCase("route")==0) ijml=35;
        else if (dtSearch.compareToIgnoreCase("sats1")==0) ijml=34;
        else if (dtSearch.compareToIgnoreCase("sats")==0) ijml=33;
        else if (dtSearch.compareToIgnoreCase("metar")==0) ijml=33;
        else if (dtSearch.compareToIgnoreCase("notam1")==0) ijml=34;
        else if (dtSearch.compareToIgnoreCase("notam1orig")==0) ijml=35;
        else if (dtSearch.compareToIgnoreCase("notam1serie")==0) ijml=35;
        else if (dtSearch.compareToIgnoreCase("notam1origserie")==0) ijml=36;
		return ijml;
	}
	
	public void setData(String ad1,String ad2,String ad3,String dt,String dtAero,String dtAeroAll,String FILE,String FILEALL, String ID) {
		sAd1=ad1;
		sAd2=ad2;//*title
		sAd3=ad3;//*lf
		dtSearch=dt;//*status,level,route,ats,ats1,metar,notam1,notam1orig,notam1serie,notam1origserie
		data=dtAero;//*msgbody
		dataAll=dtAeroAll;	
		sID=ID;//*for all statistic table using VOLMET
		
		// ===== start : setting msgbody for NOTAM Statistic
		if (dtSearch.compareToIgnoreCase("notam1")==0) {
			if (data.contains(";;Total;;")) data = data.replace(";;Total;;", "=Total=");
			if (data.contains(";;")) data = data.replace(";;", ";");
//			if (data.contains("=Total=")) data = data.replace("=Total=", ";Total;");
			if (data.contains("=Total=")) data = data.replace("=Total=", "Total;;");
			
		} else if (dtSearch.compareToIgnoreCase("notam1orig")==0) {
			if (data.contains("Total;;;;")) data = data.replace("Total;;;;", ";Total;;;");
			
			if (data.contains(";Total;;;")) data = data.replace(";Total;;;", "=Total=");
			if (data.contains(";;")) data = data.replace(";;", ";");
			if (data.contains("=Total=")) data = data.replace("=Total=", ";;Total;");
		
		} else if (dtSearch.compareToIgnoreCase("notam1serie")==0) {
//			System.err.println("data::\n" + data + "#");
			if (data.contains(";Total;;;;")) { data = data.replace(";Total;;;;", "=Total="); }
			if (data.contains(";;")) data = data.replace(";;", ";");
			if (data.contains("Total;;")) data = data.replace("Total;;", "Total;;;");
			
//			if (data.contains(";;Total;;")) { data = data.replace(";;Total;;", ";;;Total;"); }
//			
//			if (data.contains(";Total;;;")) data = data.replace(";Total;;;", "=Total=");
//			if (data.contains(";;")) data = data.replace(";;", ";");
////			if (data.contains("=Total=")) data = data.replace("=Total=", ";Total;;");
//			if (data.contains("=Total=")) data = data.replace("=Total=", "Total;;;;");
			
//			System.err.println("ubah::\n" + data + "#");
			
		} else if (dtSearch.compareToIgnoreCase("notam1origserie")==0) {
			if (data.contains("Total;;;;")) data = data.replace("Total;;;;", ";Total;;;");
			
			if (data.contains(";Total;;;")) data = data.replace(";Total;;;", "=Total=");
			if (data.contains(";;")) data = data.replace(";;", ";");
			if (data.contains("=Total=")) data = data.replace("=Total=", "Total;;;;");
		}
		// ===== end : setting msgbody for NOTAM Statistic
		
//		System.out.println("DATA:" + data + "#");
//		if (data.compareToIgnoreCase("NOTAMN;WAAA,WIII;WAAA;0;0;0;0;0;0;0;0;3;0;0;0;0;0;1;0;0;0;0;0;1;0;3;0;0;0;0;0;0;0;0;8;NOTAMN;WAAA,WIII;WAJJ;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;NOTAMN;WAAA,WIII;WAOP;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;2;0;0;2;NOTAMN;WAAA,WIII;WASS;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;NOTAMN;WAAA,WIII;WIII;0;2;0;0;0;0;6;0;0;0;0;0;0;0;1;0;0;0;0;0;0;0;1;0;0;0;0;1;1;0;0;12;NOTAMN;WAAA,WIII;WIII WIMM WIPP;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;1;NOTAMN;WAAA,WIII;WIPP;0;0;0;0;0;0;2;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;2;NOTAMR;WAAA,WIII;WARJ;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;1;RQNTMN;WAAA,WIII;WIII;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;1;RQNTMC;WAAA,WIII;WAAA;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;1;;;Total;;0;2;0;0;0;0;10;0;3;0;0;0;0;0;2;0;0;0;0;0;1;0;5;0;0;0;0;3;4;0;0;30;")==0) {
//			data = data.replace("NOTAMN;WAAA,WIII;WAAA;0;0;0;0;0;0;0;0;3;0;0;0;0;0;1;0;0;0;0;0;1;0;3;0;0;0;0;0;0;0;0;8;NOTAMN;WAAA,WIII;WAJJ;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;NOTAMN;WAAA,WIII;WAOP;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;2;0;0;2;NOTAMN;WAAA,WIII;WASS;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;NOTAMN;WAAA,WIII;WIII;0;2;0;0;0;0;6;0;0;0;0;0;0;0;1;0;0;0;0;0;0;0;1;0;0;0;0;1;1;0;0;12;NOTAMN;WAAA,WIII;WIII WIMM WIPP;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;1;NOTAMN;WAAA,WIII;WIPP;0;0;0;0;0;0;2;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;2;NOTAMR;WAAA,WIII;WARJ;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;1;RQNTMN;WAAA,WIII;WIII;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;1;RQNTMC;WAAA,WIII;WAAA;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;1;;;Total;;0;2;0;0;0;0;10;0;3;0;0;0;0;0;2;0;0;0;0;0;1;0;5;0;0;0;0;3;4;0;0;30;", "NOTAMN;WAAA,WIII;WAAA;0;0;0;0;0;0;0;0;3;0;0;0;0;0;1;0;0;0;0;0;1;0;3;0;0;0;0;0;0;0;0;8;NOTAMN;WAAA,WIII;WAJJ;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;NOTAMN;WAAA,WIII;WAOP;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;2;0;0;2;NOTAMN;WAAA,WIII;WASS;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;NOTAMN;WAAA,WIII;WIII;0;2;0;0;0;0;6;0;0;0;0;0;0;0;1;0;0;0;0;0;0;0;1;0;0;0;0;1;1;0;0;12;NOTAMN;WAAA,WIII;WIII WIMM WIPP;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;1;NOTAMN;WAAA,WIII;WIPP;0;0;0;0;0;0;2;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;2;NOTAMR;WAAA,WIII;WARJ;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;1;RQNTMN;WAAA,WIII;WIII;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;1;RQNTMC;WAAA,WIII;WAAA;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;1;;;Total;;0;2;0;0;0;0;10;0;3;0;0;0;0;0;2;0;0;0;0;0;1;0;5;0;0;0;0;3;4;123;456;123456;");
//		}
		
//		if (data.contains("Total")) data=data.replace("Total", "Tot");
		
//		if (ID.compareTo("PRE") == 0) {
//			sFlight = "";//"Flight Number : ";
//			if(!FormSearchBulletin.getFlight().isEmpty()) sFlight+=FormSearchBulletin.getFlight();
//			else if(FormSearchBulletin.getFlight().isEmpty()) sFlight+="";//"-";	
//		} else sFlight="";
		
		//----------
//		if (!jdbc.getHeader().isEmpty()) HEADER=jdbc.getHeader();
//		if (!jdbc.getFooter().isEmpty()) FOOTER=jdbc.getFooter();
		if (!rff.getPDFHeader().isEmpty()) HEADER=rff.getPDFHeader();
		if (!rff.getPDFFooter().isEmpty()) FOOTER=rff.getPDFFooter();
		//----------
		// Judul untuk NOTAM Summary beda
		String smry="";
		if (ID.compareTo("SUM") == 0) { smry="SUM"; } else smry="";
		if (ID.compareToIgnoreCase("volmet") == 0) { smry="volmet"; } else smry="";
		
		try {
			if (ID.compareToIgnoreCase("volmet") != 0) {
	    		// ONE PAGE
				Document document = new Document(PageSize.A4, rff.getPDFleft(), rff.getPDFright(), rff.getPDFtop(), rff.getPDFbottom());
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
		        TableHeader event = new TableHeader();
		        writer.setPageEvent(event);
		        TableFooter event1 = new TableFooter();
		        writer.setPageEvent(event1);
	
				document.open();
				event.setHeader(HEADER);
				event1.setFooter(FOOTER);
				addTitlePage(document,smry);
				document.close();
				
				// ALL PAGES
				Document document1 = new Document(PageSize.A4, rff.getPDFleft(), rff.getPDFright(), rff.getPDFtop(), rff.getPDFbottom());
				PdfWriter writer1 = PdfWriter.getInstance(document1, new FileOutputStream(FILEALL));
		        TableHeader eventAll = new TableHeader();
		        writer1.setPageEvent(eventAll);
		        TableFooter event1All = new TableFooter();
		        writer1.setPageEvent(event1All);
		        
				document1.open();
				eventAll.setHeader(HEADER);
				event1All.setFooter(FOOTER);
				addTitlePageAll(document1,smry);
				document1.close();
			} else {
				// TABLE PAGES LANDSCAPE *most of statistic tables
				Document docTbl = new Document(PageSize.A4.rotate(), rff.getPDFLeft(), rff.getPDFRight(), rff.getPDFTop(), rff.getPDFBottom());
				PdfWriter wriTbl = PdfWriter.getInstance(docTbl, new FileOutputStream(FILEALL));
		        TableHeader thTbl = new TableHeader();
		        wriTbl.setPageEvent(thTbl);
		        TableFooter tfTbl = new TableFooter();
		        wriTbl.setPageEvent(tfTbl);
		        
				docTbl.open();
				thTbl.setHeader(HEADER);
				tfTbl.setFooter(FOOTER);
				addTitlePageTbl(docTbl,smry);
				docTbl.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	String delim1 = "#";
	String delim2 = ";";
	String delim3 = "=";
	String[] tmp3 = new String[200000];
	int jmlData=0;
	
	public void kosong() {
        for (int i=0; i<getJml(); i++) {//for (int i=0; i<100; i++) { 
			tmp3[i]=""; 
		}
	}
	
	public void strtok(String data) {
		if (data.contains(";")) {
			int index = data.lastIndexOf(";");
			data = data.substring(0, index);
		}
		
        try {
            String[] sub = new String[200000];
            System.out.println("\n[ --- Parsing Data --- ]");
            for (int i=0; i<getJml(); i++) {//for (int i=0; i<35; i++) { 
            	tmp3[i] = ""; 
            }
            sub = data.split(delim2);
            jmlData=0;
            int i=0; 
            for (;i<sub.length; i++) {
            	tmp3[i] = sub[i];
//            	System.out.println("tmp3[" + i + "]=" + tmp3[i] + "@");
            } //end of for i
            jmlData=i;
        } catch (Exception e) {
        	e.printStackTrace();
        }

    }
	
	private void addTitlePageTbl(Document document, String SUM) throws DocumentException { //*for statistic table
		//membuat header: address
		PdfPTable table = new PdfPTable(1);
		table.setHorizontalAlignment(100);
        table.setWidthPercentage(100);
        
		Paragraph pTitle = new Paragraph(sAd2, FONT_BIGGER_BOLD);
		pTitle.setAlignment(Element.ALIGN_CENTER);
		if (SUM.compareTo("SUM")!=0) document.add(pTitle);
		
		Paragraph pLF = new Paragraph(sAd3, FONT_BOLD);
		pLF.setAlignment(Element.ALIGN_CENTER);
		if (SUM.compareTo("SUM")!=0) document.add(pLF);
		
		String judul="";
		if (dtSearch.compareToIgnoreCase("status")==0) {
			judul="Msg Typ;Aircraft ID;Status;";
		} else if (dtSearch.compareToIgnoreCase("level")==0) {
			judul="Msg Typ;Aircraft ID;Level;";
		} else if (dtSearch.compareToIgnoreCase("route")==0) {
			judul="Msg Typ;Aircraft ID;Route;";
		} else if (dtSearch.compareToIgnoreCase("sats1")==0) {
			judul="Msg Type;Type of Aircraft;";
		} else if (dtSearch.compareToIgnoreCase("sats")==0) {
			judul="Msg Type;";
		} else if (dtSearch.compareToIgnoreCase("metar")==0) {
			judul="Location;";
		} else if (dtSearch.compareToIgnoreCase("notam1")==0) {
			judul="Type;Location;";
		} else if (dtSearch.compareToIgnoreCase("notam1orig")==0) {
			judul="Type;Originator;Location;";
		} else if (dtSearch.compareToIgnoreCase("notam1serie")==0) {
			judul="Type;Location;Serie;";
		} else if (dtSearch.compareToIgnoreCase("notam1origserie")==0) {
			judul="Type;Originator;Location;Serie;";
		}
		judul+="1;2;3;4;5;6;7;8;9;10;11;12;13;14;15;16;17;18;19;20;21;22;23;24;25;26;27;28;29;30;31;Sub Total";
		
		if (SUM.compareTo("volmet")==0) {
			PdfPTable tbl = new PdfPTable(getJml()); //PdfPTable tbl = new PdfPTable(35); //n columns.
			if (!data.isEmpty()) {
				kosong();
				jmlData=0;
				strtok(data);
			}
			System.out.println("jumlah data=" + jmlData);
			if (jmlData>0 && jmlData<=10) jmlData=10;
			else if (jmlData>10 && jmlData<=20) jmlData=20;
			else if (jmlData>20 && jmlData<=30) jmlData=30;
			else if (jmlData>30 && jmlData<=40) jmlData=40;
			else if (jmlData>40 && jmlData<=50) jmlData=50;
			else if (jmlData>50 && jmlData<=60) jmlData=60;
			else if (jmlData>60 && jmlData<=70) jmlData=70;
			else if (jmlData>70 && jmlData<=80) jmlData=80;
			else if (jmlData>80 && jmlData<=90) jmlData=90;
			else if (jmlData>90 && jmlData<=100) jmlData=100;
			
			String[] sab = new String[200000];
			sab = judul.split(delim2);
			for (int i=0; i<sab.length; i++) {
//				System.out.println("judul["+i+"]>>"+sab[i]);
			} //end if delim2
			for (int i=0; i<getJml(); i++) { //*HEADER
				PdfPCell c = new PdfPCell(new Paragraph(sab[i], FONT_BOLD));
				c.setHorizontalAlignment(Element.ALIGN_CENTER);
				c.setBackgroundColor(BaseColor.LIGHT_GRAY);
				tbl.addCell(c);
			}
			for (int i=0; i<jmlData; i++) { //*BODY
				PdfPCell c = new PdfPCell(new Paragraph(tmp3[i], FONT_BOLD));
				c.setHorizontalAlignment(Element.ALIGN_CENTER);
				tbl.addCell(c);
			}
			
			float t3a = 0.5f;
			float t7a = 0.7f;
			float tntm = 0.7f;
			float tgl = 0.3f;
			float stot = 0.5f;
			//column width
			if (dtSearch.compareToIgnoreCase("status")==0 ||  dtSearch.compareToIgnoreCase("level")==0 ){
				float[] columnWidths = {t3a,t7a,0.6f,
				tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,stot};
				tbl.setWidths(columnWidths);	
			} else if (dtSearch.compareToIgnoreCase("route")==0) {
				float[] columnWidths = {t3a,t7a,1f,
				tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,stot};
				tbl.setWidths(columnWidths);	
			} else if (dtSearch.compareToIgnoreCase("sats1")==0) {
				float[] columnWidths = {t3a,1f,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,stot};
				tbl.setWidths(columnWidths);	
			} else if (dtSearch.compareToIgnoreCase("sats")==0) {
				float[] columnWidths = {t3a,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,stot};
				tbl.setWidths(columnWidths);	
			} else if (dtSearch.compareToIgnoreCase("metar")==0) {
				float[] columnWidths = {1f,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,stot};
				tbl.setWidths(columnWidths);	
			} else if (dtSearch.compareToIgnoreCase("notam1")==0) {
				float[] columnWidths = {tntm,1.3f,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,stot};
				tbl.setWidths(columnWidths);	
			} else if (dtSearch.compareToIgnoreCase("notam1orig")==0) {
				float[] columnWidths = {tntm,1f,1f,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,stot};
				tbl.setWidths(columnWidths);	
			} else if (dtSearch.compareToIgnoreCase("notam1serie")==0) {
				float[] columnWidths = {tntm,1f,0.6f,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,stot};
				tbl.setWidths(columnWidths);	
			} else if (dtSearch.compareToIgnoreCase("notam1origserie")==0) {
				float[] columnWidths = {tntm,1f,1f,0.6f,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,stot};
				tbl.setWidths(columnWidths);	
			}
			tbl.setWidthPercentage(100);
	        document.add(tbl);
//		} else {
//			Paragraph parData = new Paragraph(dtSearch, FONT_MESSAGE);
//			if (SUM.compareTo("SUM")!=0) parData.setAlignment(Element.ALIGN_JUSTIFIED);
//			else if (SUM.compareTo("SUM")==0) parData.setAlignment(Element.ALIGN_LEFT);
//			document.add(parData);
//			
//			Paragraph parDataAero = new Paragraph(data, FONT_MESSAGE);
//			if (SUM.compareTo("SUM")!=0) parDataAero.setAlignment(Element.ALIGN_JUSTIFIED);
//			else if (SUM.compareTo("SUM")==0) parDataAero.setAlignment(Element.ALIGN_LEFT);
//			document.add(parDataAero);
		}
        //--
		
	}
	
//	private void addTitlePageTblAsli(Document document, String SUM) throws DocumentException {
//		//membuat header: address
//		PdfPTable table = new PdfPTable(1);
//		table.setHorizontalAlignment(100);
//
////		PdfPCell cella = new PdfPCell(new Paragraph(""));
////		PdfPCell cellb = new PdfPCell(new Paragraph(""));
////		PdfPCell cellc = new PdfPCell(new Paragraph(""));
////		PdfPCell celld = new PdfPCell(new Paragraph(""));
//
////		PdfPCell cell1 = new PdfPCell(new Paragraph(sAd1, biggestBoldFont));
////        PdfPCell cell2 = new PdfPCell(new Paragraph(sAd2, biggerBoldFont));
//        
////        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
////        cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
////        cell1.setBackgroundColor(BaseColor.RED);
//        
////        cella.setBorder(Rectangle.NO_BORDER);   // removes border
////        cellb.setBorder(Rectangle.NO_BORDER);   // removes border
////        cellc.setBorder(Rectangle.NO_BORDER);   // removes border
////        celld.setBorder(Rectangle.NO_BORDER);   // removes border
////        cell1.setBorder(Rectangle.NO_BORDER);   // removes border
////        cell2.setBorder(Rectangle.NO_BORDER);   // removes border
//        
//        table.setWidthPercentage(100);
//        
////        table.addCell(cella);
////        table.addCell(cellb);
////        table.addCell(cellc);
////        table.addCell(celld);
////        table.addCell(cell1);
////        table.addCell(cell2);
////        if (SUM.compareTo("SUM")==0) document.add(table);
//        
////        Paragraph parTitleUp = new Paragraph(sAd1, biggestBoldFont);
////		parTitleUp.setAlignment(Element.ALIGN_CENTER);
////		if (SUM.compareTo("SUM")!=0) document.add(parTitleUp);
//		
//		Paragraph parTitleMid = new Paragraph(sAd2, biggestBoldFont);
//		parTitleMid.setAlignment(Element.ALIGN_CENTER);
//		if (SUM.compareTo("SUM")!=0) document.add(parTitleMid);
//		
//		Paragraph parTitleDown = new Paragraph(sAd3, HEADER_FONT);
//		parTitleDown.setAlignment(Element.ALIGN_CENTER);
//		document.add(parTitleDown);
//		
//		String judul="";
//		if (dtSearch.compareToIgnoreCase("status")==0) {
//			judul="Msg Typ;Acft ID;St;";
//		} else if (dtSearch.compareToIgnoreCase("level")==0) {
//			judul="Msg Typ;Acft ID;Level;";
//		} else if (dtSearch.compareToIgnoreCase("route")==0) {
//			judul="Msg Typ;Acft ID;Route;";
//		} else if (dtSearch.compareToIgnoreCase("sats1")==0) {
//			judul="Msg Type;Type of Acft;";
//		} else if (dtSearch.compareToIgnoreCase("sats")==0) {
//			judul="Msg Type;";
//		} else if (dtSearch.compareToIgnoreCase("metar")==0) {
//			judul="Loca;";
//		} else if (dtSearch.compareToIgnoreCase("notam1")==0) {
//			judul="Type;Loca;";
//		} else if (dtSearch.compareToIgnoreCase("notam1orig")==0) {
//			judul="Type;Orig;Loca;";
//		} else if (dtSearch.compareToIgnoreCase("notam1serie")==0) {
//			judul="Type;Loca;Ser;";
//		} else if (dtSearch.compareToIgnoreCase("notam1origserie")==0) {
//			judul="Type;Orig;Loca;Ser;";
//		}
//		judul+="1;2;3;4;5;6;7;8;9;10;11;12;13;14;15;16;17;18;19;20;21;22;23;24;25;26;27;28;29;30;31;Sub Tot";
//		
//		if (SUM.compareTo("volmet")==0) {
//			PdfPTable tbl = new PdfPTable(getJml()); //PdfPTable tbl = new PdfPTable(35); //n columns.
//			if (!data.isEmpty()) {
//				kosong();
//				jmlData=0;
//				strtok(data);
//			}
//			System.out.println("jumlah data=" + jmlData);
//			if (jmlData>0 && jmlData<=10) jmlData=10;
//			else if (jmlData>10 && jmlData<=20) jmlData=20;
//			else if (jmlData>20 && jmlData<=30) jmlData=30;
//			else if (jmlData>30 && jmlData<=40) jmlData=40;
//			else if (jmlData>40 && jmlData<=50) jmlData=50;
//			else if (jmlData>50 && jmlData<=60) jmlData=60;
//			else if (jmlData>60 && jmlData<=70) jmlData=70;
//			else if (jmlData>70 && jmlData<=80) jmlData=80;
//			else if (jmlData>80 && jmlData<=90) jmlData=90;
//			else if (jmlData>90 && jmlData<=100) jmlData=100;
//			
//			String[] sab = new String[200000];
//			sab = judul.split(delim2);
//			for (int i=0; i<sab.length; i++) {
////				System.out.println("judul["+i+"]>>"+sab[i]);
//			} //end if delim2
//			for (int i=0; i<getJml(); i++) { //for (int i=0; i<35; i++) {
//				PdfPCell c = new PdfPCell(new Paragraph(sab[i], HEADER_FONT));
//				c.setHorizontalAlignment(Element.ALIGN_CENTER);
//				tbl.addCell(c);
//			}
//			for (int i=0; i<jmlData; i++) {
//				PdfPCell c = new PdfPCell(new Paragraph(tmp3[i], HEADER_FONT));
//				c.setHorizontalAlignment(Element.ALIGN_CENTER);
//				tbl.addCell(c);
//			}
//			
//			float tgl = 0.6f;
//			//column width
//			if (dtSearch.compareToIgnoreCase("status")==0 || 
//					dtSearch.compareToIgnoreCase("level")==0 ||
//					dtSearch.compareToIgnoreCase("route")==0) {
//				float[] columnWidths = {0.6f,1.2f,0.5f,
//				tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,tgl,
//				0.9f};
//				tbl.setWidths(columnWidths);	
//			} else if (dtSearch.compareToIgnoreCase("sats1")==0) {
//				float[] columnWidths = {0.5f,1f,/*0.6f,*/0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,1.0f};
//				tbl.setWidths(columnWidths);	
//			} else if (dtSearch.compareToIgnoreCase("sats")==0) {
//				float[] columnWidths = {0.5f,/*1f,0.6f,*/0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,1.0f};
//				tbl.setWidths(columnWidths);	
//			} else if (dtSearch.compareToIgnoreCase("metar")==0) {
//				float[] columnWidths = {0.5f,/*1f,0.6f,*/0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,1.0f};
//				tbl.setWidths(columnWidths);	
//			} else if (dtSearch.compareToIgnoreCase("notam1")==0) {
//				float[] columnWidths = {1f,1f,/*0.6f,*/0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,1.0f};
//				tbl.setWidths(columnWidths);	
//			} else if (dtSearch.compareToIgnoreCase("notam1orig")==0) {
//				float[] columnWidths = {1f,1.2f,1f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,1.0f};
//				tbl.setWidths(columnWidths);	
//			} else if (dtSearch.compareToIgnoreCase("notam1serie")==0) {
//				float[] columnWidths = {1f,1f,0.6f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,1.0f};
//				tbl.setWidths(columnWidths);	
//			} else if (dtSearch.compareToIgnoreCase("notam1origserie")==0) {
//				float[] columnWidths = {1f,1.2f,1f,0.6f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,
//						0.4f,0.4f,0.4f,0.4f,0.4f,1.0f};
//				tbl.setWidths(columnWidths);	
//			}
////			float[] columnWidths = {0.5f,1f,0.6f,0.4f,0.4f,
////									0.4f,0.4f,0.4f,0.4f,0.4f,
////									0.4f,0.4f,0.4f,0.4f,0.4f,
////									0.4f,0.4f,0.4f,0.4f,0.4f,
////									0.4f,0.4f,0.4f,0.4f,0.4f,
////									0.4f,0.4f,0.4f,0.4f,0.4f,
////									0.4f,0.4f,0.4f,0.4f,1.0f};
////			tbl.setWidths(columnWidths);
//			tbl.setWidthPercentage(100);
//	        document.add(tbl);
//		} else {
//			Paragraph parData = new Paragraph(dtSearch, FONT_MESSAGE);
//			if (SUM.compareTo("SUM")!=0) parData.setAlignment(Element.ALIGN_JUSTIFIED);
//			else if (SUM.compareTo("SUM")==0) parData.setAlignment(Element.ALIGN_LEFT);
//			document.add(parData);
//			
//			Paragraph parDataAero = new Paragraph(data, FONT_MESSAGE);
//			if (SUM.compareTo("SUM")!=0) parDataAero.setAlignment(Element.ALIGN_JUSTIFIED);
//			else if (SUM.compareTo("SUM")==0) parDataAero.setAlignment(Element.ALIGN_LEFT);
//			document.add(parDataAero);
//		}
//        //--
//		
//	}
	
	private void addTitlePage(Document document, String SUM) throws DocumentException {
		//membuat header: address
		PdfPTable table = new PdfPTable(2);
		table.setHorizontalAlignment(100);
	
		PdfPCell cella = new PdfPCell(new Paragraph(""));
		PdfPCell cellb = new PdfPCell(new Paragraph(""));
		PdfPCell cellc = new PdfPCell(new Paragraph(""));
		PdfPCell celld = new PdfPCell(new Paragraph(""));

        PdfPCell cell1 = new PdfPCell(new Paragraph(sAd1+"\n", biggerBoldFont));
        PdfPCell cell2 = new PdfPCell(new Paragraph(sAd2, biggestBoldFont));

        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        cella.setBorder(Rectangle.NO_BORDER);   // removes border
        cellb.setBorder(Rectangle.NO_BORDER);   // removes border
        cellc.setBorder(Rectangle.NO_BORDER);   // removes border
        celld.setBorder(Rectangle.NO_BORDER);   // removes border
        cell1.setBorder(Rectangle.NO_BORDER);   // removes border
        cell2.setBorder(Rectangle.NO_BORDER);   // removes border
        
        table.setWidthPercentage(100);
        
        table.addCell(cella);
        table.addCell(cellb);
        table.addCell(cellc);
        table.addCell(celld);
        table.addCell(cell1);
        table.addCell(cell2);
        if (SUM.compareTo("SUM")==0) document.add(table);
        
		Paragraph parTitleUp = new Paragraph(sAd1, biggerBoldFont);
		parTitleUp.setAlignment(Element.ALIGN_CENTER);
		if (SUM.compareTo("SUM")!=0) document.add(parTitleUp);
		
		Paragraph parTitleMid = new Paragraph(sAd2, biggestBoldFont);
		parTitleMid.setAlignment(Element.ALIGN_CENTER);
		if (SUM.compareTo("SUM")!=0) document.add(parTitleMid);
		
//		Paragraph parTitleUp = new Paragraph(sAd1, biggerBoldFont);
////		parTitleUp.setAlignment(Element.ALIGN_CENTER); //--ASLINYA
//		if (SUM.compareTo("SUM")!=0) parTitleUp.setAlignment(Element.ALIGN_CENTER);
//		else if (SUM.compareTo("SUM")==0) parTitleUp.setAlignment(Element.ALIGN_LEFT);
//		document.add(parTitleUp);
//		
//		Paragraph parTitleMid = new Paragraph(sAd2, biggestBoldFont);
////		parTitleMid.setAlignment(Element.ALIGN_CENTER); //--ASLINYA
//		if (SUM.compareTo("SUM")!=0) parTitleMid.setAlignment(Element.ALIGN_CENTER);
//		else if (SUM.compareTo("SUM")==0) parTitleMid.setAlignment(Element.ALIGN_RIGHT);
//		document.add(parTitleMid);
		
		Paragraph parTitleDown = new Paragraph(sAd3, biggerBoldFont);
		parTitleDown.setAlignment(Element.ALIGN_CENTER);
		document.add(parTitleDown);
		
		Paragraph parData = new Paragraph(dtSearch, FONT_MESSAGE);
//		parData.setAlignment(Element.ALIGN_JUSTIFIED); //--ASLINYA
		if (SUM.compareTo("SUM")!=0) parData.setAlignment(Element.ALIGN_JUSTIFIED);
		else if (SUM.compareTo("SUM")==0) parData.setAlignment(Element.ALIGN_LEFT);
		document.add(parData);
		
		Paragraph parDataAero = new Paragraph(data, FONT_MESSAGE);
		//parDataAero.setAlignment(Element.ALIGN_JUSTIFIED); //--ASLINYA
		if (SUM.compareTo("SUM")!=0) parDataAero.setAlignment(Element.ALIGN_JUSTIFIED);
		else if (SUM.compareTo("SUM")==0) parDataAero.setAlignment(Element.ALIGN_LEFT);
		document.add(parDataAero);
	}
	
	private void addTitlePageAll(Document document, String SUM) throws DocumentException {
		//membuat header: address
		PdfPTable table = new PdfPTable(2);
		table.setHorizontalAlignment(100);
	
		PdfPCell cella = new PdfPCell(new Paragraph(""));
		PdfPCell cellb = new PdfPCell(new Paragraph(""));
		PdfPCell cellc = new PdfPCell(new Paragraph(""));
		PdfPCell celld = new PdfPCell(new Paragraph(""));

        PdfPCell cell1 = new PdfPCell(new Paragraph(sAd1+"\n", biggerBoldFont));
        PdfPCell cell2 = new PdfPCell(new Paragraph(sAd2, biggestBoldFont));

        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        cella.setBorder(Rectangle.NO_BORDER);   // removes border
        cellb.setBorder(Rectangle.NO_BORDER);   // removes border
        cellc.setBorder(Rectangle.NO_BORDER);   // removes border
        celld.setBorder(Rectangle.NO_BORDER);   // removes border
        cell1.setBorder(Rectangle.NO_BORDER);   // removes border
        cell2.setBorder(Rectangle.NO_BORDER);   // removes border
        
        table.setWidthPercentage(100);
        
        table.addCell(cella);
        table.addCell(cellb);
        table.addCell(cellc);
        table.addCell(celld);
        table.addCell(cell1);
        table.addCell(cell2);
        if (SUM.compareTo("SUM")==0) document.add(table);
        
		Paragraph parTitleUp = new Paragraph(sAd1, biggerBoldFont);
		parTitleUp.setAlignment(Element.ALIGN_CENTER);
		if (SUM.compareTo("SUM")!=0) document.add(parTitleUp);
		
		Paragraph parTitleMid = new Paragraph(sAd2, biggestBoldFont);
		parTitleMid.setAlignment(Element.ALIGN_CENTER);
		if (SUM.compareTo("SUM")!=0) document.add(parTitleMid);
		
		Paragraph parTitleDown = new Paragraph(sAd3, biggerBoldFont);
		parTitleDown.setAlignment(Element.ALIGN_CENTER);
		document.add(parTitleDown);
		
		Paragraph parData = new Paragraph(dtSearch, FONT_MESSAGE);
		//parData.setAlignment(Element.ALIGN_JUSTIFIED); //--ASLINYA
		if (SUM.compareTo("SUM")!=0) parData.setAlignment(Element.ALIGN_JUSTIFIED);
		else if (SUM.compareTo("SUM")==0) parData.setAlignment(Element.ALIGN_LEFT);
		document.add(parData);
		
		Paragraph parDataAero = new Paragraph(dataAll, FONT_MESSAGE);
		//parDataAero.setAlignment(Element.ALIGN_JUSTIFIED); //--ASLINYA
		if (SUM.compareTo("SUM")!=0) parDataAero.setAlignment(Element.ALIGN_JUSTIFIED);
		else if (SUM.compareTo("SUM")==0) parDataAero.setAlignment(Element.ALIGN_LEFT);
		document.add(parDataAero);
	}
	
    /**
     * Inner class to add a tblHead as header.
     */
    class TableHeader extends PdfPageEventHelper {
        /** The header text. */
        String header;
        /** The template with the total number of pages. */
        PdfTemplate total;

        /**
         * Allows us to change the content of the header.
         * @param header The new header String
         */
        public void setHeader(String header) {
            this.header = header;
        }
        
        /**
         * Creates the PdfTemplate that will hold the total number of pages.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onOpenDocument(PdfWriter writer, Document document) {
            total = writer.getDirectContent().createTemplate(30, 16);
        }

        /**
         * Adds a header to every page
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable tblHead = new PdfPTable(4);
            try {
            	int iWidth[]=null;
            	int totWidth=0,totHeight=0;
            	
            	if (sID.compareToIgnoreCase("volmet")!=0) {
            		iWidth=new int[]{26, 35, 24, 2};
            		totWidth = rff.getPDFwidth();
            		totHeight = rff.getPDFheadHeight();
            	} else if (sID.compareToIgnoreCase("volmet")==0) {
            		iWidth=new int[]{100, 250, 100, 20};
            		totWidth = rff.getPDFwidthlandscape();
            		totHeight = rff.getPDFheadHeightlandscape();
            	}
            	
            	tblHead.setWidths(iWidth); //(new int[]{26, 35, 24, 2});
                tblHead.setTotalWidth(totWidth); //(rff.getPDFwidth());
                tblHead.setLockedWidth(true);
                tblHead.getDefaultCell().setFixedHeight(rff.getPDFheight());
                tblHead.getDefaultCell().setBorder(Rectangle.BOTTOM);
//                tblHead.getDefaultCell().setBorderColor(BaseColor.ORANGE);
                
                tblHead.addCell(new Phrase(header, fontStyleFooters));
                tblHead.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

                tblHead.addCell(new Phrase(sFlight, fontStyleFooters));
                tblHead.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

                tblHead.addCell(new Phrase(String.format("Page %d of", writer.getPageNumber()), fontStyleFooters));
                PdfPCell cell = new PdfPCell(Image.getInstance(total));
                cell.setBorder(Rectangle.BOTTOM);
                
                tblHead.addCell(cell);
                tblHead.writeSelectedRows(0, -1, 34, totHeight, writer.getDirectContent());
            }
            catch(DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }
        
        /**
         * Fills out the total number of pages before the document is closed.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onCloseDocument(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                    new Phrase(String.valueOf(writer.getPageNumber() - 1), fontStyleFooters),
                    2, 5, 0);
        }
    }
    
    /**
     * Inner class to add a tblFoot as footer.
     */
    class TableFooter extends PdfPageEventHelper {
        /** The footer text. */
        String footer;
        /** The template with the total number of pages. */
        PdfTemplate total;

        /**
         * Allows us to change the content of the footer.
         * @param footer The new footer String
         */
        public void setFooter(String footer) {
            this.footer = footer;
        }
        
        /**
         * Creates the PdfTemplate that will hold the total number of pages.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onOpenDocument(PdfWriter writer, Document document) {
            total = writer.getDirectContent().createTemplate(30, 16);
        }

        /**
         * Adds a footer to every page
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable tblFoot = new PdfPTable(3);
            try {
            	int iWidth=0;
            	if (sID.compareToIgnoreCase("volmet")!=0) {
            		iWidth=rff.getPDFwidth();
            	} else if (sID.compareToIgnoreCase("volmet")==0) {
            		iWidth=rff.getPDFwidthlandscape();
            	}
                tblFoot.setWidths(new int[]{iWidth, 0, 0});
                tblFoot.setTotalWidth(iWidth);
                tblFoot.setLockedWidth(true);
                tblFoot.getDefaultCell().setFixedHeight(rff.getPDFheight());
                tblFoot.getDefaultCell().setBorder(Rectangle.TOP);
                tblFoot.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                tblFoot.addCell(new Phrase(footer, fontStyleFooters));
                tblFoot.addCell(new Phrase(""));
                PdfPCell cell = new PdfPCell(Image.getInstance(total));
                cell.setBorder(Rectangle.TOP);
                tblFoot.addCell(cell);
                tblFoot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(),
                        writer.getDirectContent());
            }
            catch(DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }
        
        /**
         * Fills out the total number of pages before the document is closed.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onCloseDocument(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(total, Element.ALIGN_CENTER, new Phrase(""), 2, 5, 0);
        }
    }
}


//ASLI
////update: 160312
//package com.isode.xuxa.desktop.actions.compose;
//
//import java.io.FileOutputStream;
//
//import org.eclipse.swt.widgets.Display;
//
//import com.isode.desktop.database.jdbc;
//import com.isode.xuxa.desktop.XuxaMain;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.ExceptionConverter;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.ColumnText;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfPageEventHelper;
//import com.itextpdf.text.pdf.PdfTemplate;
//import com.itextpdf.text.pdf.PdfWriter;
//
//public class HeaderFooter {
////	private static String FILE = "/tmp/ramdisk0/PIB.pdf";
////	private static String FILEALL = "/tmp/ramdisk0/PIBAll.pdf";
//	
//	private static String HEADER = "test header";
//	private static String FOOTER = "test footer";
//	
//	private static Font biggerBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
//	private static Font FONT_MESSAGE = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
//	private static Font biggestBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
////	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
//	
//	Display display = XuxaMain.display;//new Display();
//	String sAd1="",sAd2="",sAd3="",dtSearch="",data="",dataAll="";
//	
//	Font fontStyleFooters = FontFactory.getFont(FontFactory.TIMES, 9, Font.NORMAL);
//	
//	/** The resulting PDF file. */
////    public static final String RESULT = "/mega/example_result1.pdf";
//
//	/** Constructor */
//	public HeaderFooter() {
//		
//	}
//	
//	public void setData(String ad1,String ad2,String ad3,String dt,String dtAero,String dtAeroAll,String FILE,String FILEALL) {
//		sAd1=ad1;
//		sAd2=ad2;
//		sAd3=ad3;
//		dtSearch=dt;
//		data=dtAero;
//		dataAll=dtAeroAll;	
//		
//		if (!jdbc.getHeader().isEmpty()) HEADER=jdbc.getHeader();
//		if (!jdbc.getFooter().isEmpty()) FOOTER=jdbc.getFooter();
//		
//		try {
//    		// ONE PAGE
//			Document document = new Document(PageSize.A4, 36, 36, 54, 36);
////			PdfWriter.getInstance(document, new FileOutputStream(FILE));
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
//	        TableHeader event = new TableHeader();
//	        writer.setPageEvent(event);
//	        TableFooter event1 = new TableFooter();
//	        writer.setPageEvent(event1);
//
//			document.open();
//			event.setHeader(HEADER);
//			event1.setFooter(FOOTER);
//			addTitlePage(document);
//			document.close();
//			
//			// ALL PAGES
//			Document document1 = new Document(PageSize.A4, 36, 36, 54, 36);
////			PdfWriter.getInstance(document1, new FileOutputStream(FILEALL));
//			PdfWriter writer1 = PdfWriter.getInstance(document1, new FileOutputStream(FILEALL));
//	        TableHeader eventAll = new TableHeader();
//	        writer1.setPageEvent(eventAll);
//	        TableFooter event1All = new TableFooter();
//	        writer1.setPageEvent(event1All);
//	        
//			document1.open();
//			eventAll.setHeader(HEADER);
//			event1All.setFooter(FOOTER);
//			addTitlePageAll(document1);
//			document1.close();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private void addTitlePage(Document document) throws DocumentException {
//		//membuat header: address
//		Paragraph parTitleUp = new Paragraph(sAd1, biggerBoldFont);
//		parTitleUp.setAlignment(Element.ALIGN_CENTER);
//		document.add(parTitleUp);
//		
//		Paragraph parTitleMid = new Paragraph(sAd2, biggestBoldFont);
//		parTitleMid.setAlignment(Element.ALIGN_CENTER);
//		document.add(parTitleMid);
//		
//		Paragraph parTitleDown = new Paragraph(sAd3, biggerBoldFont);
//		parTitleDown.setAlignment(Element.ALIGN_CENTER);
//		document.add(parTitleDown);
//		
//		Paragraph parData = new Paragraph(dtSearch, FONT_MESSAGE);
//		parData.setAlignment(Element.ALIGN_JUSTIFIED);
//		document.add(parData);
//		
//		Paragraph parDataAero = new Paragraph(data, FONT_MESSAGE);
//		parDataAero.setAlignment(Element.ALIGN_JUSTIFIED);
//		document.add(parDataAero);
//	}
//	
//	private void addTitlePageAll(Document document) throws DocumentException {
//		//membuat header: address
//		Paragraph parTitleUp = new Paragraph(sAd1, biggerBoldFont);
//		parTitleUp.setAlignment(Element.ALIGN_CENTER);
//		document.add(parTitleUp);
//		
//		Paragraph parTitleMid = new Paragraph(sAd2, biggestBoldFont);
//		parTitleMid.setAlignment(Element.ALIGN_CENTER);
//		document.add(parTitleMid);
//		
//		Paragraph parTitleDown = new Paragraph(sAd3, biggerBoldFont);
//		parTitleDown.setAlignment(Element.ALIGN_CENTER);
//		document.add(parTitleDown);
//		
//		Paragraph parData = new Paragraph(dtSearch, FONT_MESSAGE);
//		parData.setAlignment(Element.ALIGN_JUSTIFIED);
//		document.add(parData);
//		
//		Paragraph parDataAero = new Paragraph(dataAll, FONT_MESSAGE);
//		parDataAero.setAlignment(Element.ALIGN_JUSTIFIED);
//		document.add(parDataAero);
//	}
//	
//    /**
//     * Inner class to add a tblHead as header.
//     */
//    class TableHeader extends PdfPageEventHelper {
//        /** The header text. */
//        String header;
//        /** The template with the total number of pages. */
//        PdfTemplate total;
//
//        /**
//         * Allows us to change the content of the header.
//         * @param header The new header String
//         */
//        public void setHeader(String header) {
//            this.header = header;
//        }
//        
//        /**
//         * Creates the PdfTemplate that will hold the total number of pages.
//         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
//         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
//         */
//        public void onOpenDocument(PdfWriter writer, Document document) {
//            total = writer.getDirectContent().createTemplate(30, 16);
//        }
//
//        /**
//         * Adds a header to every page
//         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
//         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
//         */
//        public void onEndPage(PdfWriter writer, Document document) {
//            PdfPTable tblHead = new PdfPTable(3);
//            try {
//                tblHead.setWidths(new int[]{24, 24, 2});
//                tblHead.setTotalWidth(527);
//                tblHead.setLockedWidth(true);
//                tblHead.getDefaultCell().setFixedHeight(rff.getPDFheight());
//                tblHead.getDefaultCell().setBorder(Rectangle.BOTTOM);
////                tblHead.addCell(header);
//                tblHead.addCell(new Phrase(header, fontStyleFooters));
//                tblHead.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
////                tblHead.addCell(String.format("Page %d of", writer.getPageNumber()));
//                tblHead.addCell(new Phrase(String.format("Page %d of", writer.getPageNumber()), fontStyleFooters));
//                PdfPCell cell = new PdfPCell(Image.getInstance(total));
//                cell.setBorder(Rectangle.BOTTOM);
//                tblHead.addCell(cell);
////                System.out.println("halaman:"+writer.getDirectContent()+"*");
//                tblHead.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
//            }
//            catch(DocumentException de) {
//                throw new ExceptionConverter(de);
//            }
//        }
//        
//        /**
//         * Fills out the total number of pages before the document is closed.
//         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
//         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
//         */
//        public void onCloseDocument(PdfWriter writer, Document document) {
//            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
//                    new Phrase(String.valueOf(writer.getPageNumber() - 1), fontStyleFooters),
//                    2, 5, 0);
//        }
//    }
//    
//    /**
//     * Inner class to add a tblFoot as footer.
//     */
//    class TableFooter extends PdfPageEventHelper {
//        /** The footer text. */
//        String footer;
//        /** The template with the total number of pages. */
//        PdfTemplate total;
//
//        /**
//         * Allows us to change the content of the footer.
//         * @param footer The new footer String
//         */
//        public void setFooter(String footer) {
//            this.footer = footer;
//        }
//        
//        /**
//         * Creates the PdfTemplate that will hold the total number of pages.
//         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
//         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
//         */
//        public void onOpenDocument(PdfWriter writer, Document document) {
//            total = writer.getDirectContent().createTemplate(30, 16);
//        }
//
//        /**
//         * Adds a footer to every page
//         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
//         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
//         */
//        public void onEndPage(PdfWriter writer, Document document) {
//            PdfPTable tblFoot = new PdfPTable(3);
//            try {
//                tblFoot.setWidths(new int[]{527, 0, 0});
//                tblFoot.setTotalWidth(527);
//                tblFoot.setLockedWidth(true);
//                tblFoot.getDefaultCell().setFixedHeight(rff.getPDFheight());
//                tblFoot.getDefaultCell().setBorder(Rectangle.TOP);
////              tblFoot.addCell(footer);
//                tblFoot.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
//                tblFoot.addCell(new Phrase(footer, fontStyleFooters));
////                tblFoot.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
////              tblFoot.addCell(String.format("Page %d of", writer.getPageNumber()));
//                tblFoot.addCell(new Phrase(""));
//                PdfPCell cell = new PdfPCell(Image.getInstance(total));
//                cell.setBorder(Rectangle.TOP);
//                tblFoot.addCell(cell);
////                tblFoot.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
//                tblFoot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(),
//                        writer.getDirectContent());
//            }
//            catch(DocumentException de) {
//                throw new ExceptionConverter(de);
//            }
//        }
//        
//        /**
//         * Fills out the total number of pages before the document is closed.
//         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
//         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
//         */
//        public void onCloseDocument(PdfWriter writer, Document document) {
////            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
////                    new Phrase(String.valueOf(writer.getPageNumber() - 1), fontStyleFooters),
////                    2, 5, 0);
//            ColumnText.showTextAligned(total, Element.ALIGN_CENTER, new Phrase(""), 2, 5, 0);
//        }
//    }
//}
