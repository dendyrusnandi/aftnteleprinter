package readwrite;

import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

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
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import displays.MainForm;
import displays.TeleSplashScreen2016IP;

import org.eclipse.swt.graphics.FontData;

public class WriteToPDF {
	
	private static String HEADER = "";
	private static String FOOTER = "";
	
	private static Font MESSAGES_FONT = new Font(Font.FontFamily.COURIER, 11, Font.NORMAL);
	private static Font MESSAGES_ITALIC_FONT = new Font(Font.FontFamily.COURIER, 11, Font.ITALIC);
	private static Font HEADER_FOOTER_FONT = new Font(Font.FontFamily.COURIER, 9, Font.NORMAL);
	private static Font PAGEOF_FONT = new Font(Font.FontFamily.COURIER, 13, Font.NORMAL);
	
//	protected BaseFont helv;
	
	Display display = TeleSplashScreen2016IP.display;
	String data="",sFlight="",sTotal="",jmldata="";
	
	ReadFromFile rff = new ReadFromFile();
	

	/** Constructor */
	public WriteToPDF() {
		rff.readConfiguration();
		
//		try {
//			helv = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
//		} catch (Exception e) {
//			throw new ExceptionConverter(e);
//		}
	}
	
	public void setPrint(String dt, String jmldt, String FILE) { //ATS Messages Export to PDF & Export to PDFs
		data=dt;
		jmldata=jmldt;
		
		String readFile = ReadFromFile.ReadInputFile2(MainForm.sFolder+"headerpdf.txt");
		if (!readFile.isEmpty() && readFile.contains("\n")) {
			String sub[] = readFile.split("\n");
			for (int i=0; i<sub.length; i++) {
				if (sub[i].startsWith("header=")) HEADER = sub[i].replace("header=", "");
				else if (sub[i].startsWith("footer=")) FOOTER = sub[i].replace("footer=", "");
			}
		}
		
		//----------
//		if (!rff.getPDFHeader().isEmpty()) HEADER=rff.getPDFHeader();
//		if (!rff.getPDFFooter().isEmpty()) FOOTER=rff.getPDFFooter();
		//----------
		try {
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
        
		Paragraph dt = new Paragraph(data, MESSAGES_FONT);
		document.add(dt);
		
		Paragraph jmldt = new Paragraph(jmldata + " Element(s) in this file", MESSAGES_ITALIC_FONT);
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
