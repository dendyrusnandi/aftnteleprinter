package setting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;

import readwrite.ReadFromFile;
import displays.MainForm;
import displays.TeleSplashScreen2016IP;

public class Shorten {
	
	TextSetting ts = new TextSetting();
	ReadFromFile rff = new ReadFromFile();
	
	public final String hexadecimal="hexadecimal";
	public final String per="per";
	public final String alphanum="alphanum";
	public final String alphanumEnter="alphanumEnter";
	public final String alphanum1="alphanum1";
	public final String alphaNumStroke="alphaNumStroke";
	public final String alphaNumSpace="alphaNumSpace";
	public final String alphaNumSTS="alphaNumSTS";
	public final String alphaText="alphaText";
	public final String letter="letter";
	public final String letter18="letter18";
	public final String letterSpace="letterSpace";
	public final String letter1="letter1";
	public final String upper="upper";
	public final String numeric="numeric";
	public final String numeric18="numeric18";
	public final String numericdot="numericdot";
	
	public final static String FONTFACE = "Arial";
	public final static int NORMAL = 10;

	
	public Shorten() { }

	public static Font getFont() {
		ReadFromFile.readConfiguration();
		Font font = new Font(TeleSplashScreen2016IP.display, new FontData(FONTFACE, NORMAL, SWT.NORMAL));
		return font;
	}
	
//	public static Font getFont(int iStyle) {
//		ReadFromFile.readConfiguration();
//		Font font = new Font(TeleSplashScreen2016IP.display, new FontData(FONTFACE, ReadFromFile.getFontSizeStatusBar(), iStyle));
//		return font;
//	}
	
//	public static Font getFontCLabel(int iStyle) {
//		ReadFromFile.readConfiguration();
//		Font font = new Font(TeleSplashScreen2016IP.display, new FontData(FONTFACE, (ReadFromFile.getFontSizeStatusBar()-1), iStyle));
//		return font;
//	}
	
	public static Font getFont(int iStyle) {
		ReadFromFile.readConfiguration();
		Font font = new Font(TeleSplashScreen2016IP.display, new FontData(FONTFACE, NORMAL, iStyle));
		return font;
	}
	
	public static Font getFontCLabel(int iStyle) {
		ReadFromFile.readConfiguration();
		Font font = new Font(TeleSplashScreen2016IP.display, new FontData(FONTFACE, NORMAL-2, iStyle));
		return font;
	}
	
	Text settextstyle(Text text, String rule) {
		if (rule.compareToIgnoreCase("hexadecimal")==0) ts.hexadecimal(text);
		else if (rule.compareToIgnoreCase("per")==0) ts.per(text);
		else if (rule.compareToIgnoreCase("alphanum")==0) ts.alphanum(text);
		else if (rule.compareToIgnoreCase("alphanumEnter")==0) ts.alphanumEnter(text);
		else if (rule.compareToIgnoreCase("alphanum1")==0) ts.alphanum1(text);
		else if (rule.compareToIgnoreCase("alphaNumStroke")==0) ts.alphaNumStroke(text);
		else if (rule.compareToIgnoreCase("alphaNumSpace")==0) ts.alphaNumSpace(text);
		else if (rule.compareToIgnoreCase("alphaNumSTS")==0) ts.alphaNumSTS(text);
		else if (rule.compareToIgnoreCase("alphaText")==0) ts.alphaText(text);
		else if (rule.compareToIgnoreCase("letter")==0) ts.letter(text);
		else if (rule.compareToIgnoreCase("letter18")==0) ts.letter18(text);
		else if (rule.compareToIgnoreCase("letterSpace")==0) ts.letterSpace(text);
		else if (rule.compareToIgnoreCase("letter1")==0) ts.letter1(text);
		else if (rule.compareToIgnoreCase("upper")==0) ts.upper(text);
		else if (rule.compareToIgnoreCase("numeric")==0) ts.numeric(text);
		else if (rule.compareToIgnoreCase("numeric18")==0) ts.numeric18(text);
		else if (rule.compareToIgnoreCase("numericdot")==0) ts.numericdot(text);
		return text;
	}

	//---------style : TOOLITEM
	public void toolitemStyle(ToolItem item, String str, String strTool, Image img) {
		item.setText(str);
		item.setToolTipText(strTool);
		item.setImage(img);
	}

	//---------style : GROUP
	public void groupStyle(Group group, int iCol, String sTitle) {
		group.setLayout(new GridLayout(iCol, false));
		group.setText(sTitle);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);//GridData.FILL_BOTH);
		group.setLayoutData(gd);
		group.setFont(getFont(SWT.NORMAL));
	}

	public void groupStyle1(Group group, int iCol, String sTitle) {
		group.setLayout(new GridLayout(iCol, false));
		group.setText(sTitle);
		GridData gd = new GridData(GridData.FILL_BOTH);
		group.setLayoutData(gd);
		group.setFont(getFont(SWT.NORMAL));
	}

	//---------style : SHELL
	public void shellStyle(Shell shell, String str, int x, int y) {
		shell.setText(str);
		shell.setLocation(x, y);
		shell.setLayout(new GridLayout());
		shell.pack();
		shell.open();
		shell.setFont(getFont(SWT.NORMAL));
	}
	
	public void shellStyle(Shell shell, String str) {
		shell.setText(str);
		shell.setLayout(new GridLayout());
		shell.pack();
		shell.setFont(getFont(SWT.NORMAL));
		shell.setLocation(MainForm.setLocX(shell), MainForm.setLocY(shell));
		shell.open();
	}
	
	//---------style : BUTTON
	public void RadioCheckStyle(Button btn, String str, String sTool) {
		GridData gd = new GridData();
		btn.setLayoutData(gd);
		btn.setText(str);
		btn.setToolTipText(sTool);
		btn.setFont(getFont(SWT.NORMAL));
		btn.setSize(SWT.DEFAULT, SWT.DEFAULT);
//		btn.setBackground(Colors.DarkGrey);
	}
	
	public void buttonRCStyle(Button btn, String str, String sTool, int iWidth) {
		GridData gd = new GridData();
		gd.widthHint = iWidth;
//		gd.heightHint = iHeight;
		btn.setText(str);
		btn.setToolTipText(sTool);
//		btn.setFont(new Font(TeleSplashScreen.display, FONTFACE, NORMAL, SWT.NORMAL));
		btn.setLayoutData(gd);
		btn.setFont(getFont(SWT.NORMAL));
	}
	
	public void buttonRCStyle(Button btn, String str, String sTool) {
		GridData gd = new GridData();
		btn.setText(str);
		btn.setToolTipText(sTool);
//		btn.setFont(new Font(TeleSplashScreen.display, FONTFACE, NORMAL, SWT.NORMAL));
		btn.setLayoutData(gd);
		btn.setFont(getFont(SWT.NORMAL));
	}
	
	public void buttonStyle(Button btn, String str, String sTool, int iWidth, Color bgColor, int iVer, int iHor, Image img) {
		GridData gd = new GridData();
		gd.widthHint = iWidth;
//		gd.heightHint = iHeight;
		gd.verticalAlignment = iVer;
		gd.horizontalAlignment = iHor;
		btn.setText(str);
		btn.setToolTipText(sTool);
		btn.setBackground(bgColor);
		btn.setLayoutData(gd);	
		btn.setImage(img);
		btn.setFont(getFont(SWT.BOLD));
	}
	
	public void buttonStyle(Button btn, String str, String sTool, int iWidth, int iHeight, Color bgColor, int iVer, int iHor, Image img) {
		GridData gd = new GridData();
		gd.widthHint = iWidth;
		gd.heightHint = iHeight;
		gd.verticalAlignment = iVer;
		gd.horizontalAlignment = iHor;
		btn.setText(str);
		btn.setToolTipText(sTool);
		btn.setBackground(bgColor);
		btn.setLayoutData(gd);	
		btn.setImage(img);
		btn.setFont(getFont(SWT.BOLD));
	}

	public void button(Button btn,String str,String sTool,Color bgColor,int iVer,int iHor,Image img) {
		GridData gd = new GridData();
		gd.verticalAlignment = iVer;
		gd.horizontalAlignment = iHor;
		btn.setText(str);
		btn.setToolTipText(sTool);
		btn.setBackground(bgColor);
		btn.setLayoutData(gd);	
		btn.setImage(img);
		btn.setFont(getFont(SWT.BOLD));
	}
	
	// button di MainForm ROUTE,TYPE,REG dan button mini FPL
	public void buttonStyle(Button btn, String str, String sTool, int iWidth, int iHeight, Color bgColor) {
		GridData gd = new GridData();
		gd.widthHint = iWidth;
		gd.heightHint = iHeight;
		btn.setText(str);
		btn.setToolTipText(sTool);
//		btn.setBackground(bgColor);
		btn.setLayoutData(gd);	
		btn.setFont(getFont(SWT.BOLD));
	}

	//---------style : TABLE
	public void tableStyle(Table table, boolean bLines, boolean bHeader, int iHeight, int iWidth) {
		table.setLinesVisible(bLines);
		table.setHeaderVisible(bHeader);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = iHeight;
		data.widthHint = iWidth;
		table.setFont(getFont(SWT.NORMAL));
		table.setLayoutData(data);
	}
	
	//---------style : TABLE COLUMN
	public void tablecol(final TableColumn tc, String str, String strTool, int iWidth, boolean bResize) {
		tc.setText(str);
		tc.setToolTipText(strTool);
		tc.setWidth(iWidth);
		tc.setResizable(bResize);
	}

	//---------style : LIST
	public void list(List list, int iWidth, int iHeight, String tool, String[] data, Composite comp) {
		GridData gd = new GridData(); 
		gd.widthHint = iWidth;
		gd.heightHint = iHeight;
		list.setToolTipText(tool);
		list.setItems(data);
		list.setFont(getFont(SWT.NORMAL));
		list.setLayoutData(gd);
	}
	
	public void listStyle(List list, int iWidth, int iHeight, String[] data) {
		GridData gd = new GridData(); 
		gd.widthHint = iWidth;
		gd.heightHint = iHeight;
		gd.horizontalAlignment = SWT.LEFT;
		gd.verticalAlignment = SWT.TOP;
		list.setItems(data);
		list.setFont(getFont(SWT.NORMAL));
		list.setLayoutData(gd);
	}
	
	//---------style : LABEL
	public void labelimg(Label label, Image img, int iVer, int iHor, int iWidth) {
		GridData gd = new GridData(iVer, iHor, false, false);
		gd.widthHint = iWidth;
		label.setImage(img);
		label.setLayoutData(gd);
	}
	
	public void labelSeparator(Label label, int iWidth, int iHeight) {
		GridData gd = new GridData();
		gd.widthHint = iWidth;
		gd.heightHint = iHeight;
		label.setLayoutData(gd);
	}
	
	public void label(Label label, String str, int iVer, int iHor, int iStyle, Color frColor) {
		GridData gd = new GridData(iVer, iHor, false, false);
		label.setText(str); 
		label.setForeground(frColor);
		label.setLayoutData(gd);
		label.setFont(getFont(SWT.NORMAL));
//		label.setBackground(Colors.red);
	}
	
	public void labelStyle(Label label, String str, int iWidth, int iVer, int iHor, int iStyle, Color frColor) {
		GridData gd = new GridData(iVer, iHor, false, false);
		gd.widthHint = iWidth;
		label.setText(str); 
		label.setForeground(frColor);
		label.setLayoutData(gd);
		label.setFont(getFont(iStyle));
//		label.setBackground(Colors.white);
	}
	
	public void labelStyle1(Label label, String str, int iVer, int iHor, int iStyle, Color frColor) {
		GridData gd = new GridData(iVer, iHor, false, false);
		label.setText(str); 
		label.setForeground(frColor);
		label.setLayoutData(gd);
		label.setFont(getFont(iStyle));
//		label.setBackground(Colors.DarkGrey);
	}
	
	public void buttonStyle1(Label label, String str, int iVer, int iHor, int iStyle, Color frColor) {
		GridData gd = new GridData(iVer, iHor, false, false);
		label.setText(str); 
		label.setForeground(frColor);
		label.setLayoutData(gd);
		label.setFont(getFont(iStyle));
//		label.setBackground(Colors.DarkGrey);
	}
	public void clabel(CLabel label, String str, int iVer, int iHor, Color frColor) {
		GridData gd = new GridData(iVer, iHor, false, false);
		label.setText(str); 
		label.setForeground(frColor);
		label.setLayoutData(gd);
		label.setFont(getFont(SWT.BOLD));
//		label.setBackground(Colors.DarkGrey);
	}
	
	public void clabelStyle(CLabel label, String str, int iWidth, int iVer, int iHor, int iStyle, Color frColor, Image img) {
		GridData gd = new GridData(iVer, iHor, false, false);
		gd.widthHint = iWidth;
		label.setText(str); 
		label.setImage(img);
		label.setForeground(frColor);
		label.setLayoutData(gd);
		label.setFont(getFontCLabel(SWT.BOLD));
	}
	
	//---------style : TEXT
	public void text(Text text, int iLimit, int iHor, int iVer, String rule, String sTool, boolean enabled) {
		settextstyle(text, rule);
		GridData gd = new GridData(iVer, iHor, false, false);
		text.setFont(getFont(SWT.NORMAL));
		text.setTextLimit(iLimit);
		text.setToolTipText(sTool);
		text.setEditable(enabled);
		text.setLayoutData(gd);
	}
	
	public void text1(Text text, int iWidth, int iLimit, int iHor, int iVer, String rule, String sTool, boolean enabled) {
		GridData gd = new GridData(iVer, iHor, false, false);
		gd.widthHint = iWidth;
		settextstyle(text, rule);
		text.setFont(getFont(SWT.NORMAL));
		text.setTextLimit(iLimit);
		text.setToolTipText(sTool);
		text.setEditable(enabled);
		text.setLayoutData(gd);
	}
	
	public void textStyle(Text text, int iWidth, int iLimit, int iHor, int iVer, String rule, String sTool, boolean enabled) {
		GridData gd = new GridData(iVer, iHor, false, false);
		gd.widthHint = iWidth;
//		gd.heightHint = heightText;
		gd.verticalAlignment = iVer;
		gd.horizontalAlignment = iHor;
		
		settextstyle(text, rule);
		text.setFont(getFont(SWT.NORMAL));
		text.setTextLimit(iLimit);
		text.setToolTipText(sTool);
		text.setEditable(enabled);
		text.setLayoutData(gd);
	}
	
	public void buttonStyle(Button b, int iWidth, int iLimit, int iHor, int iVer, String rule, String sTool, boolean enabled) {
		GridData gd = new GridData(iVer, iHor, false, false);
		gd.widthHint = iWidth;
//		gd.heightHint = heightText;
		gd.verticalAlignment = iVer;
		gd.horizontalAlignment = iHor;
		
		b.setFont(getFont(SWT.NORMAL));
		b.setToolTipText(sTool);
		b.setText(sTool);
		b.setLayoutData(gd);
	}

	public void textAreaStyle(Text text, int iWidth, int iHeight, int iHor, int iVer, String rule, String sTool) {
		GridData gd = new GridData();
		gd.widthHint = iWidth;
		gd.heightHint = iHeight;
		gd.verticalAlignment = iVer;
		gd.horizontalAlignment = iHor;
		
		settextstyle(text, rule);
		text.setToolTipText(sTool);
		text.setFont(getFont(SWT.NORMAL));
		text.setLayoutData(gd);
	}
	
	//---------style : COMPOSITE
	public void compositeStyle(Composite comp, int iCol, int iHor, int iVer) {
		GridLayout gl = new GridLayout();
		gl.marginBottom=0; gl.marginHeight=0; gl.marginLeft=0; gl.marginRight=0; gl.marginTop=0; gl.marginWidth=0; gl.numColumns = iCol;
		comp.setLayout(gl);
		comp.setLayoutData(new GridData(iHor, iVer, true, true, 1, 1));//SWT.RIGHT, SWT.BOTTOM
		comp.setFont(getFont(SWT.NORMAL));
	}
	
	public void compositeStyle(Composite comp, int iCol, int iHor, int iVer, int iWidth) {
		GridLayout gl = new GridLayout();
		gl.marginBottom=0; gl.marginHeight=0; gl.marginLeft=0; gl.marginRight=0; gl.marginTop=0; gl.marginWidth=0; gl.numColumns = iCol;
		comp.setLayout(gl);
		GridData gd = new GridData();
		gd.widthHint=iWidth;
		gd.verticalAlignment=iVer;
		gd.horizontalAlignment=iHor;
		comp.setLayoutData(gd);
		comp.setFont(getFont(SWT.NORMAL));
	}
	
	public void compositeStyle1(Composite comp, int iCol, int iHeight, int iWidth) {
		GridLayout gl = new GridLayout();
		gl.marginBottom=0; gl.marginHeight=0; gl.marginLeft=0; gl.marginRight=0; gl.marginTop=0; gl.marginWidth=0; gl.numColumns = iCol;
		comp.setLayout(gl);
		GridData gd = new GridData();
		if (iWidth!=0) gd.widthHint=iWidth;
		gd.heightHint=iHeight;
//		gd.verticalAlignment=iVer;
//		gd.horizontalAlignment=iHor;
		comp.setLayoutData(gd);
		comp.setFont(getFont(SWT.NORMAL));
	}
	
}
