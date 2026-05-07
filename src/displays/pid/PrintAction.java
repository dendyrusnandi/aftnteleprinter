package displays.pid;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PrintAction {
	  Display display;
	  Shell shell;
	  Font font;
	  Color foregroundColor, backgroundColor;
	  Printer printer;
	  GC gc;
	  Font printerFont;
	  Color printerForegroundColor, printerBackgroundColor;

	  int lineHeight = 0;
	  int tabWidth = 0;
	  int leftMargin, rightMargin, topMargin, bottomMargin;
	  int x, y;
	  int index, end;
	  String textToPrint;
	  String tabs;
	  StringBuffer wordBuffer;
	  String filenamec;

	  public void open(Display dsp,String filename) throws IOException {
		  display=dsp;
		  shell = new Shell(display);
		  shell.setLayout(new FillLayout());
		  shell.setText("Print Text");
  	      shell.setMaximized(true);
  	      if (filename.compareTo("")!=0) {
  	    	  textToPrint=openfile(filename);
  	    	  filenamec=filename;
  	      }
    	  font = new Font(display, "Courier", 10, SWT.NORMAL);
	  }
	  
	  public void menuFont(Text text) {
		    FontDialog fontDialog = new FontDialog(shell);
		    fontDialog.setFontList(font.getFontData());
		    FontData fontData = fontDialog.open();
		    if (fontData != null) {
		      if (font != null) font.dispose();
		      font = new Font(display, fontData);
		      text.setFont(font);
		    }
		  }

	  public void menuFont(Table table) {
		    FontDialog fontDialog = new FontDialog(shell);
		    fontDialog.setFontList(font.getFontData());
		    FontData fontData = fontDialog.open();
		    if (fontData != null) {
		      if (font != null) font.dispose();
		      font = new Font(display, fontData);
		      table.setFont(font);
		    }
		  }

	  public void menuPrint() {
	    foregroundColor = display.getSystemColor(SWT.COLOR_BLACK);
	    backgroundColor = display.getSystemColor(SWT.COLOR_WHITE);
	    PrintDialog dialog = new PrintDialog(shell, SWT.NULL);
	    PrinterData data = dialog.open();
	    if (data == null) return;
	    if (data.printToFile) {
	      //data.fileName = "print.out"; // you probably want to ask the user for a filename
	    }

	    /* Do the printing in a background thread so that spooling does not freeze the UI. */
	    printer = new Printer(data);
	    Thread printingThread = new Thread("Printing") {
	      public void run() {
	        print(printer);
	        printer.dispose();
	      }
	    };
	    printingThread.start();
	  }
	  
	  void print(Printer printer) {
	    if (printer.startJob("Text")) {   // the string is the job name - shows up in the printer's job list
	      Rectangle clientArea = printer.getClientArea();
	      Rectangle trim = printer.computeTrim(0, 0, 0, 0);
	      Point dpi = printer.getDPI();
	      leftMargin = dpi.x + trim.x; // one inch from left side of paper
	      rightMargin = clientArea.width - dpi.x + trim.x + trim.width; // one inch from right side of paper
	      topMargin = dpi.y + trim.y; // one inch from top edge of paper
	      bottomMargin = clientArea.height - dpi.y + trim.y + trim.height; // one inch from bottom edge of paper
	      /* Create a buffer for computing tab width. */
	      int tabSize = 4; // is tab width a user setting in your UI?
	      StringBuffer tabBuffer = new StringBuffer(tabSize);
	      for (int i = 0; i < tabSize; i++) tabBuffer.append(' ');
	      tabs = tabBuffer.toString();

	      /* Create printer GC, and create and set the printer font & foreground color. */
	      gc = new GC(printer);
	      FontData fontData = font.getFontData()[0];
	      System.out.println(fontData.getName()+" "+fontData.getHeight()+" "+fontData.getStyle());
	      printerFont = new Font(printer, fontData.getName(), fontData.getHeight(), fontData.getStyle());
	      gc.setFont(printerFont);
	      tabWidth = gc.stringExtent(tabs).x;
	      lineHeight = gc.getFontMetrics().getHeight();
	      RGB rgb = foregroundColor.getRGB();      printerForegroundColor = new Color(printer, rgb);
	      gc.setForeground(printerForegroundColor);
	      rgb = backgroundColor.getRGB();
	      printerBackgroundColor = new Color(printer, rgb);
	      gc.setBackground(printerBackgroundColor);
	      /* Print text to current gc using word wrap */
	      System.out.println("printing text...");
	      printText();
	      printer.endJob();
	      System.out.println("OK");

	      /* Cleanup graphics resources used in printing */
	      printerFont.dispose();
	      printerForegroundColor.dispose();
	      printerBackgroundColor.dispose();
	      gc.dispose();
  	      delete(filenamec);
  	      textToPrint="";
	    }
	    else System.out.println("else printer.startJob"+printer.startJob("Text"));
	  }
	  
	  void printText() {
	    printer.startPage();
	    wordBuffer = new StringBuffer();
	    x = leftMargin;
	    y = topMargin;
	    index = 0;
	    end = textToPrint.length();
	    while (index < end) {
	      char c = textToPrint.charAt(index);
	      index++;
	      if (c != 0) {
	        if (c == 0x0a || c == 0x0d) {
	          if (c == 0x0d && index < end && textToPrint.charAt(index) == 0x0a) {
	            index++; // if this is cr-lf, skip the lf
	          }
	          printWordBuffer();
	          newline();
	        } else {
	          if (c != '\t') {
	            wordBuffer.append(c);
	          }
	          if ((Character.isWhitespace(c)) || (end == index)) {
	            printWordBuffer();
	            if (c == '\t') {
	              x += tabWidth;
	            }
	          }
	        }
	      }
	    }
	    if (y + lineHeight <= bottomMargin) {
	      printer.endPage();
	    }
	  }

	  void printWordBuffer() {
	    if (wordBuffer.length() > 0) {
	      String word = wordBuffer.toString();
	      int wordWidth = gc.stringExtent(word).x;
	      if (x + wordWidth > rightMargin) {
	        /* word doesn't fit on current line, so wrap */
	        newline();
	      }
	      gc.drawString(word, x, y, false);
	      x += wordWidth;
	      wordBuffer = new StringBuffer();
	    }
	  }

	  void newline() {
	    x = leftMargin;
	    y += lineHeight;
	    if (y + lineHeight > bottomMargin) {
	      printer.endPage();
	      if (index + 1 < end) {
	        y = topMargin;
	        printer.startPage();
	      }
	    }
	  }
	  
	  void delete(String fileName) {
		  try {
		      // Construct a File object for the file to be deleted.
		      File target = new File(fileName);

		      if (!target.exists()) {
		        System.err.println("File " + fileName
		            + " not present to begin with!");
		        return;
		      }

		      // Quick, now, delete it immediately:
		      if (target.delete())
		        System.err.println("** Deleted " + fileName + " **");
		      else
		        System.err.println("Failed to delete " + fileName);
		    } catch (SecurityException e) {
		      System.err.println("Unable to delete " + fileName + "("
		          + e.getMessage() + ")");
		    }
	  }
	  
	  String openfile(String fileName) throws IOException {
		  String contents ="";
		  try{
			  contents = new Scanner(new File(fileName)).findWithinHorizon(Pattern.compile(".*", Pattern.DOTALL), 0);
			  //System.out.println(contents);
			  //FileOutputStream fos; 
  		      //DataOutputStream dos;
		      //File file= new File("/root/testwr.txt");
		      //fos = new FileOutputStream(file);
		      //dos=new DataOutputStream(fos);
		      //dos.writeInt(2333);
		      //dos.writeChars(contents);
		  }
		  catch (SecurityException e) {
		      System.err.println("Unable to delete " + fileName + "("
		          + e.getMessage() + ")");
		  }		  
		  return contents;
	  }
}
