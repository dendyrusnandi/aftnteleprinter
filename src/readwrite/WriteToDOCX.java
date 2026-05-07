package readwrite;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.StringTokenizer;

import org.apache.log4j.BasicConfigurator;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.AltChunkType;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import displays.MainForm;
import org.docx4j.openpackaging.io.SaveToZipFile;

public class WriteToDOCX {
	
//   public static void main(String[] args) throws Exception {
	public WriteToDOCX(String flnm, String data) throws Exception {
		data=data.replaceAll("\n\n", "``");
		/*data=data.replaceAll("\n", "\r\n\r\n\r\n");
		org.apache.log4j.BasicConfigurator.configure();
		
	   //Blank Document
	   XWPFDocument document= new XWPFDocument(); 
	        
	   //Write the Document in file system
	   FileOutputStream out = new FileOutputStream(new File(MainForm.sPath+flnm+".docx"));//"/mega/workspace/java2word/fontstyle.docx"));
	        
	   //create paragraph
	   XWPFParagraph paragraph = document.createParagraph();
	        
	   //Set Bold an Italic
	   XWPFRun paragraphOneRunOne = paragraph.createRun();
	   paragraphOneRunOne.setBold(true);
	   paragraphOneRunOne.setItalic(true);
	   paragraphOneRunOne.setText(data);
	   paragraphOneRunOne.setFontFamily("Monospace");
	   paragraphOneRunOne.addCarriageReturn();
	   paragraphOneRunOne.addBreak();
	        
//	   //Set text Position
//	   XWPFRun paragraphOneRunTwo = paragraph.createRun();
//	   paragraphOneRunTwo.setText("Font Style two");
//	   paragraphOneRunTwo.setTextPosition(100);
//	 
//	   //Set Strike through and Font Size and Subscript
//	   XWPFRun paragraphOneRunThree = paragraph.createRun();
//	   paragraphOneRunThree.setStrike(true);
//	   paragraphOneRunThree.setFontSize(20);
//	   paragraphOneRunTwo.setFontFamily("Arial");
//	   paragraphOneRunThree.setSubscript(VerticalAlign.SUBSCRIPT);
//	   paragraphOneRunThree.setText(" Different Font Styles");
	   document.write(out);
	   out.close();
	   System.out.println(MainForm.sPath+flnm+".docx written successully");*/
		
    	System.out.println("data:"+data);

		BasicConfigurator.configure();
	    XWPFDocument pdfTextDocument = new XWPFDocument();
	    XWPFParagraph pdfTextParagraph = pdfTextDocument.createParagraph();;
	    XWPFRun pdfTextParagraphCharacterRun = pdfTextParagraph.createRun();

	    StringTokenizer pdfTextReader = new StringTokenizer(data);
	    String pdfTextLine = null;
	    while(pdfTextReader.hasMoreTokens()){
	       pdfTextLine = pdfTextReader.nextToken("\n");
	       String s=pdfTextLine;
	       System.out.println("parsing:"+pdfTextLine);
	       pdfTextLine=pdfTextLine.replaceAll("``", "\n\n");
	       pdfTextParagraphCharacterRun.setText(pdfTextLine);
	       pdfTextParagraphCharacterRun.addCarriageReturn();
	       if (s.contains("``")) {
		       pdfTextParagraphCharacterRun.setText(" ");
		       pdfTextParagraphCharacterRun.addCarriageReturn();
		       pdfTextParagraphCharacterRun.setText(" ");
		       pdfTextParagraphCharacterRun.addCarriageReturn();
	       }
	    }
	    try{
	    	System.out.println("flnm:"+MainForm.sPath+flnm);
	        pdfTextDocument.write(new FileOutputStream(new File(MainForm.sPath+flnm+".docx")));
	    }
	    catch(Exception e){
	        System.err.println("An exception occured in creating the DOCX document."+ e.getMessage());
	    }
   }


}