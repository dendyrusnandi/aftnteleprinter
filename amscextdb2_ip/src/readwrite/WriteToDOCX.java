package readwrite;


import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import actions.RuntimeCmd;

import displays.Lists;
import displays.MainForm;

public class WriteToDOCX {
	
//   public static void main(String[] args) throws Exception {
	public WriteToDOCX(String flnm, String data) throws Exception {
		
		org.apache.log4j.BasicConfigurator.configure();
		
	   //Blank Document
	   XWPFDocument document= new XWPFDocument(); 
	        
	   //Write the Document in file system
	   FileOutputStream out = new FileOutputStream(new File(MainForm.path+flnm+".docx"));//"/mega/workspace/java2word/fontstyle.docx"));
	        
	   //create paragraph
	   XWPFParagraph paragraph = document.createParagraph();
	        
	   //Set Bold an Italic
	   XWPFRun paragraphOneRunOne = paragraph.createRun();
	   paragraphOneRunOne.setBold(false);
	   paragraphOneRunOne.setItalic(false);
	   paragraphOneRunOne.setText(data);
	   paragraphOneRunOne.setFontFamily("Monospace");
//	   paragraphOneRunOne.addBreak();
	        
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
	   System.out.println(MainForm.path+flnm+".docx written successully");
	   
	   new RuntimeCmd(Lists.cmdDOC, MainForm.path+flnm+".docx");
   }
}