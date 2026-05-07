package readwrite;

import java.io.FileOutputStream;

import org.eclipse.swt.widgets.Display;

import actions.RuntimeCmd;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
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
import displays.Lists;
import displays.MainForm;


public class WriteToPDF {
	PdfPTable tbl;
	PdfPCell c;
	private static String HEADER = "";
	private static String FOOTER = "";
	
//	if (FILE.compareTo(MainForm.path+"Incoming.pdf")==0 || FILE.compareTo(MainForm.path+"Outgoing.pdf")==0 || 
//			FILE.compareTo(MainForm.path+"Rejected.pdf")==0) {
//		text.setFont(new Font(AmscSplashScreen.display, new FontData("Monospace", 8, SWT.NORMAL)));
	
	private static Font MESSAGES_FONT_BOLD_RETRIEVE = new Font(Font.FontFamily.COURIER, 11, Font.BOLD);
	private static Font MESSAGES_FONT_RETRIEVE = new Font(Font.FontFamily.COURIER, 11, Font.NORMAL);
	private static Font MESSAGES_FONT_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
	private static Font MESSAGES_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
	private static Font MESSAGES_ITALIC_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.ITALIC);
	private static Font HEADER_FOOTER_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
	private static Font PAGEOF_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.NORMAL);
	
//	protected BaseFont helv;
	
	Display display = AmscSplashScreen2.display;
	String data="",sFlight="",sTotal="",jmldata="",sFile="",sTrace="";
	
	ReadFromFile rff = new ReadFromFile();
	

	/** Constructor */
	public WriteToPDF() {
		rff.readConfiguration();
	}
	
	public void setPrint(String dt, String jmldt, String FILE, String strTrace) { //ATS Messages Export to PDF & Export to PDFs
//		System.out.println("dt:" + dt + "#\njmldt:" + jmldt + "#\nfile:" + FILE + "#");
		sFile=FILE;
		FILE=MainForm.path+FILE+".pdf";
		data=dt;
		jmldata=jmldt;
		sTrace=strTrace;
		//----------
		if (!rff.getPDFHeader().isEmpty()) HEADER=rff.getPDFHeader();
		if (!rff.getPDFFooter().isEmpty()) FOOTER=rff.getPDFFooter();
		//----------
		try {
//			System.out.println("file:" + FILE + "#");
			if (FILE.compareTo(MainForm.path+"Incoming.pdf")==0 || FILE.compareTo(MainForm.path+"Outgoing.pdf")==0 || 
					FILE.compareTo(MainForm.path+"Rejected.pdf")==0) {
				
				Document document = new Document(PageSize.A4, rff.getPDFLeft(), rff.getPDFRight(), rff.getPDFTop(), rff.getPDFBottom());
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
		        TableHeader event = new TableHeader();
		        writer.setPageEvent(event);
		        TableFooter event1 = new TableFooter();
		        writer.setPageEvent(event1);

				document.open();
				event.setHeader(HEADER);
				event1.setFooter(FOOTER);
				addTitlePage(document);
				document.close();
			} else {
				Document document = new Document(PageSize.A4, rff.getPDFLeft(), rff.getPDFRight(), rff.getPDFTop(), rff.getPDFBottom());
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
			}
					
//			Document document = new Document(PageSize.A4, rff.getPDFLeft(), rff.getPDFRight(), rff.getPDFTop(), rff.getPDFBottom());
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
//	        TableHeader event = new TableHeader();
//	        writer.setPageEvent(event);
//	        TableFooter event1 = new TableFooter();
//	        writer.setPageEvent(event1);
//
//			document.open();
//			event.setHeader(HEADER);
//			event1.setFooter(FOOTER);
//			addTitlePagePrint(document);
//			document.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		new RuntimeCmd(cmdPDF, MainForm.path+getFlnm(sID)+".pdf");
		new RuntimeCmd(Lists.cmdPDF, FILE);
	}
	
	///mulai dari sini percobaan retrieval
	String delim1 = "#";
	String delim2 = ";";
	String delim3 = "=";
	String[] tmp3 = new String[2000];
	int jmlData=0;
	
	public void kosong() {
		for (int i=0; i<100; i++) { tmp3[i]=""; }
	}

	public void strtok(String data) {
//		System.out.println("data:" + data + "@");
		if (data.contains("#")) {
			int index = data.lastIndexOf("#");
			data = data.substring(0, index);
			//System.out.println("strtok:" + data);
		}
		
        try {
            String[] sub = new String[2000];
            String[] sub1 = new String[2000];
            String[] sub2 = new String[2000];
            String[] F = new String[2000];
            
            //System.out.println("\n\n\n\n\n\n\n\n");
            System.out.println("\n[ --- Parsing Data --- ]");
            
            sub = data.split(delim1);
            for (int i=0; i<30; i++) { tmp3[i] = ""; }
            
            jmlData=0;
            for (int i=0; i<sub.length; i++) {
//            	System.out.println("delim1["+i+"]>>"+sub[i]);

            	if (sub[i].contains(delim2)) {
            		sub1 = sub[i].split(delim2);
                    for (int x=0; x<sub1.length; x++) {
//                    	System.out.println("\tdelim2["+x+"]:"+sub1[x]);   
                    	
                    	if (sub1[x].contains(delim3)) {
                    		sub2 = sub1[x].split(delim3);
                    		int index = sub1[x].indexOf(delim3);
                    		F[i] = sub1[x].substring(index+1, sub1[x].length());
                    		
                    		for (int y=0; y<sub2.length; y++) {
//                    			System.out.println("\t\tdelim3["+y+"]::"+sub2[y]);

//                    			if (sub2[y].compareTo("data") == 0) { tmp3[y] = F[i]; }
//                            	else if (sub2[y].compareTo("trace") == 0) { tmp3[y] = F[i]; }
                    			if (sub2[y].compareTo("data") == 0) { 
                    				c = new PdfPCell(new Paragraph(F[i], MESSAGES_FONT_RETRIEVE));
                    				c.setHorizontalAlignment(Element.ALIGN_LEFT);
                    				tbl.addCell(c);
                    			}
                            	else if (sub2[y].compareTo("trace") == 0) { 
                            		c = new PdfPCell(new Paragraph(F[i], MESSAGES_FONT_RETRIEVE));
                    				c.setHorizontalAlignment(Element.ALIGN_LEFT);
                    				tbl.addCell(c);
                            	}

                    			jmlData+=y;
                    		} //end of for y
                    	} //end if delim3
                    } //end of for x
            	} //end if delim2
            } //end of for i
        } catch (Exception e) {
            //System.out.println(e);
        	e.printStackTrace();
        }

    }
	
	private void addTitlePage(Document document) throws DocumentException {
		String col1="",col2="";
		if (sFile.compareToIgnoreCase("rejected")==0||((sFile.compareToIgnoreCase("incoming")==0||sFile.compareToIgnoreCase("outgoing")==0)
				&& sTrace.compareToIgnoreCase("trace")==0)) {
			
			if (sFile.compareToIgnoreCase("rejected")==0) { col1="Error message"; col2="Message"; } 
			if (sFile.compareToIgnoreCase("incoming")==0||sFile.compareToIgnoreCase("outgoing")==0) { col1="Data"; col2="Trace"; } 
		
			//membuat header: address
			PdfPTable table = new PdfPTable(2);
			
			PdfPCell cell1 = new PdfPCell(new Paragraph(col1, MESSAGES_FONT_BOLD_RETRIEVE));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell1.setBorder(Rectangle.BOX);   // removes border
	        table.addCell(cell1);
	        
	        PdfPCell cell2 = new PdfPCell(new Paragraph(col2, MESSAGES_FONT_BOLD_RETRIEVE));
	        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell2.setBorder(Rectangle.BOX);   // removes border
	        table.addCell(cell2);
	
	        float[] colWidthTable = {2f, 2f};
			table.setWidths(colWidthTable);
			table.setWidthPercentage(100);
	        document.add(table);
	        
	        //percobaan
	        tbl = new PdfPTable(2); //number of columns.
			tbl.setHorizontalAlignment(100);
			if (!data.isEmpty()) {
				kosong();
				jmlData=0;
				strtok(data);
			}
	
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
	
			float[] columnWidths = {2f, 2f};
			tbl.setWidths(columnWidths);
			tbl.setWidthPercentage(100);
			document.add(tbl);
		        
			PdfPTable tableJml = new PdfPTable(1);
			PdfPCell cellJml = new PdfPCell(new Paragraph(jmldata + " row(s) in this file", MESSAGES_FONT_BOLD_RETRIEVE));
			cellJml.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        cellJml.setBorder(Rectangle.BOX);   // removes border
	        tableJml.addCell(cellJml);
			tableJml.setWidths(new float[] {2f});
			tableJml.setWidthPercentage(100);
	        document.add(tableJml);
		} else if ((sFile.compareToIgnoreCase("incoming")==0||sFile.compareToIgnoreCase("outgoing")==0) 
				&& sTrace.compareToIgnoreCase("notrace")==0) {
			
			col1="Data"; 
			
			//membuat header: address
			PdfPTable table = new PdfPTable(1);
			
			PdfPCell cell1 = new PdfPCell(new Paragraph(col1, MESSAGES_FONT_BOLD_RETRIEVE));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell1.setBorder(Rectangle.BOX);   // removes border
	        table.addCell(cell1);
	        
	        float[] colWidthTable = {2f};
			table.setWidths(colWidthTable);
			table.setWidthPercentage(100);
	        document.add(table);
	        
	        //percobaan
	        tbl = new PdfPTable(1); //number of columns.
			tbl.setHorizontalAlignment(100);
			if (!data.isEmpty()) {
				kosong();
				jmlData=0;
				strtok(data);
			}
	
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
	
			float[] columnWidths = {2f};
			tbl.setWidths(columnWidths);
			tbl.setWidthPercentage(100);
			document.add(tbl);
		        
			PdfPTable tableJml = new PdfPTable(1);
			PdfPCell cellJml = new PdfPCell(new Paragraph(jmldata + " row(s) in this file", MESSAGES_FONT_BOLD_RETRIEVE));
			cellJml.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        cellJml.setBorder(Rectangle.BOX);   // removes border
	        tableJml.addCell(cellJml);
			tableJml.setWidths(new float[] {2f});
			tableJml.setWidthPercentage(100);
	        document.add(tableJml);
	        
		}
	}
	
	private void addTitlePageASLI(Document document) throws DocumentException {
		//membuat header: address
		PdfPTable table = new PdfPTable(2);
		String col1="",col2="";
		if (sFile.compareToIgnoreCase("rejected")==0) {
			col1="Error message"; col2="Message"; }
		else if (sFile.compareToIgnoreCase("incoming")==0 || sFile.compareToIgnoreCase("outgoing")==0) {
			col1="Data"; col2="Trace"; }
		
		PdfPCell cell1 = new PdfPCell(new Paragraph(col1, MESSAGES_FONT_BOLD_RETRIEVE));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setBorder(Rectangle.BOX);   // removes border
        table.addCell(cell1);
        
        PdfPCell cell2 = new PdfPCell(new Paragraph(col2, MESSAGES_FONT_BOLD_RETRIEVE));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setBorder(Rectangle.BOX);   // removes border
        table.addCell(cell2);

        float[] colWidthTable = {2f, 2f};
		table.setWidths(colWidthTable);
		table.setWidthPercentage(100);
        document.add(table);
        
        //percobaan
        tbl = new PdfPTable(2); //number of columns.
		tbl.setHorizontalAlignment(100);
		if (!data.isEmpty()) {
			kosong();
			jmlData=0;
			strtok(data);
		}

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

		float[] columnWidths = {2f, 2f};
		tbl.setWidths(columnWidths);
		tbl.setWidthPercentage(100);
		document.add(tbl);
	        
		PdfPTable tableJml = new PdfPTable(1);
		PdfPCell cellJml = new PdfPCell(new Paragraph(jmldata + " row(s) in this file", MESSAGES_FONT_BOLD_RETRIEVE));
		cellJml.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellJml.setBorder(Rectangle.BOX);   // removes border
        tableJml.addCell(cellJml);
		tableJml.setWidths(new float[] {2f});
		tableJml.setWidthPercentage(100);
        document.add(tableJml);
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
        
		Paragraph dt = new Paragraph(data, MESSAGES_FONT);
		document.add(dt);
		
		Paragraph jmldt = new Paragraph(jmldata + " row(s) in this file", MESSAGES_ITALIC_FONT);
		jmldt.setAlignment(Element.ALIGN_RIGHT);
		document.add(jmldt);
	}
	
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
//            PdfPTable tblHead = new PdfPTable(4);
//            try {
//            	tblHead.setWidths(new int[]{26, 35, 24, 2});
//                tblHead.setTotalWidth(rff.getPDFWidth());
//                tblHead.setLockedWidth(true);
//                tblHead.getDefaultCell().setFixedHeight(rff.getPDFHeight());
//                tblHead.getDefaultCell().setBorder(Rectangle.BOTTOM);
//                tblHead.addCell(new Phrase(header, HEADER_FOOTER_FONT));
//                tblHead.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
//                tblHead.addCell(new Phrase(sFlight, HEADER_FOOTER_FONT));
//                tblHead.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
//                tblHead.addCell(new Phrase(String.format("Page %d of ", writer.getPageNumber()), HEADER_FOOTER_FONT));
//                PdfPCell cell = new PdfPCell(Image.getInstance(total)); //asli
////                cell.setBorder(Rectangle.BOTTOM); //asli
//                cell.setBorder(Rectangle.BOX);
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                tblHead.addCell(cell); //asli
//                tblHead.writeSelectedRows(0, -1, 34, rff.getPDFHeadheight(), writer.getDirectContent());
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
//        	ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
//                    new Phrase(String.valueOf(writer.getPageNumber() - 1), HEADER_FOOTER_FONT), //headerfooterfont
//                    2, 5, 0);
//        }
//    }
    
	 /**
     * Inner class to add a table as header.
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
            PdfPTable table = new PdfPTable(3);
            try {
                table.setWidths(new int[]{40, 8, 2});
                table.setTotalWidth(rff.getPDFWidth());
                table.setLockedWidth(true);
                
                table.getDefaultCell().setFixedHeight(rff.getPDFHeight());
                table.getDefaultCell().setBorder(Rectangle.BOTTOM);
                table.addCell(new Phrase(header, HEADER_FOOTER_FONT));
                
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(new Phrase(String.format("Page %d of ", writer.getPageNumber()), HEADER_FOOTER_FONT));
                
                PdfPCell cell = new PdfPCell(Image.getInstance(total));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                table.addCell(cell);
                
                table.writeSelectedRows(0, -1, 34, rff.getPDFHeadheight(), writer.getDirectContent());
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
//            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
//                    new Phrase(String.valueOf(writer.getPageNumber() - 1)),
//                    2, 2, 0);
            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
            		new Phrase(String.valueOf(writer.getPageNumber() - 1), PAGEOF_FONT), 2, 0, 0);
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
                tblFoot.setWidths(new int[]{rff.getPDFWidth(), 0, 0});
                tblFoot.setTotalWidth(rff.getPDFWidth());
                tblFoot.setLockedWidth(true);
                tblFoot.getDefaultCell().setFixedHeight(rff.getPDFHeight());
                tblFoot.getDefaultCell().setBorder(Rectangle.TOP);
                tblFoot.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                tblFoot.addCell(new Phrase(footer, HEADER_FOOTER_FONT));
                tblFoot.addCell(new Phrase(""));
                PdfPCell cell = new PdfPCell(Image.getInstance(total));
                cell.setBorder(Rectangle.TOP);
                tblFoot.addCell(cell);
                tblFoot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(), writer.getDirectContent());
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
