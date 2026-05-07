package setting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
//import org.eclipse.swt.graphics.Font;
//import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;

import displays.AmscSplashScreen2;

public class Shorten {
	
	TextSetting ts = new TextSetting();
	
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
	
	public final String FONTFACE = "Arial";
	public final int NORMAL = 10;

	public int widthBtn=70;
	public int widthNavBtn=40;

	public Color BLACK = new Color(AmscSplashScreen2.display, 0, 0, 0);
	public Color BLUE = new Color(AmscSplashScreen2.display, 0, 0, 255);
	public Color GREEN = new Color(AmscSplashScreen2.display, 0, 255, 0);
	public Color RED = new Color(AmscSplashScreen2.display, 255, 0, 0);
	public Color WHITE = new Color(AmscSplashScreen2.display, 255, 255, 255);

	public Color Grey = new Color(AmscSplashScreen2.display, 224, 224, 224);
	public Color DarkGrey = new Color(AmscSplashScreen2.display, 192, 192, 192);
	
	public Color ActiveTabColor = new Color(AmscSplashScreen2.display, new RGB(157, 167, 195));//new Color(AmscSplashScreen.display, 192, 192, 192);
	
	
	
	public Shorten() { }
	
	void clear(Button bClear) {
		buttonStyle(bClear, "&Clear", "clear the input", widthBtn, DarkGrey, SWT.CENTER, SWT.CENTER, null);//Images.imgClear16);	
	}
	
	public void setCF(Button bClear, Button bFind) {
		clear(bClear);
		buttonStyle(bFind, "&Find", "execute the search", widthBtn, DarkGrey, SWT.CENTER, SWT.CENTER, null);//Images.imgSearch16);
	}
	
	public void setCC(Button bClear, Button bFind) {
		clear(bClear);
		buttonStyle(bFind, "Creat&e", "execute the search", widthBtn, DarkGrey, SWT.CENTER, SWT.CENTER, null);//Images.imgSearch16);
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
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);//GridData.FILL_BOTH);//
		group.setLayoutData(gd);
//		group.setFont(new Font(AmscSplashScreen.display, FONTFACE, NORMAL, SWT.NORMAL));
	}
	
	//---------style : SHELL
	public void shellStyle(Shell shell, String str, int x, int y) {
		shell.setText(str);
		shell.setLocation(x, y);
		shell.setLayout(new GridLayout());
		shell.pack();
		shell.open();
	}
	
	//---------style : COMBO
	public void comboStyle(Combo combo, String sTool, int iWidth, int iVer) {
		GridData gd = new GridData();
		gd.widthHint = iWidth;
		gd.verticalAlignment = iVer;
		combo.setLayoutData(gd);
		combo.setToolTipText(sTool);
//		combo.setFont(new Font(AmscSplashScreen.display, FONTFACE, NORMAL, SWT.NORMAL));
	}
	
	//---------style : BUTTON
	public void buttonRCStyle(Button btn, String str, String sTool, int iWidth) {
		GridData gd = new GridData();
		gd.widthHint = iWidth;
		btn.setText(str);
		btn.setToolTipText(sTool);
//		btn.setFont(new Font(AmscSplashScreen.display, FONTFACE, NORMAL, SWT.NORMAL));
		btn.setLayoutData(gd);	
	}

	public void RadioCheckStyle(Button btn, String str, String sTool) {
		GridData gd = new GridData();
		btn.setLayoutData(gd);
		btn.setText(str);
		btn.setToolTipText(sTool);
		btn.setSize(SWT.DEFAULT, SWT.DEFAULT);
	}
	
	public void buttonStyle(Button btn, String str, String sTool, int iWidth, Color bgColor, int iVer, int iHor, Image img) {
		GridData gd = new GridData();
		gd.widthHint = iWidth;
		gd.verticalAlignment = iVer;
		gd.horizontalAlignment = iHor;
		btn.setText(str);
		btn.setToolTipText(sTool);
		btn.setBackground(bgColor);
		btn.setLayoutData(gd);	
		btn.setImage(img);
	}
	
	//---------style : TABLE
	public void tableStyle(Table table, boolean bLines, boolean bHeader, int iHeight) {//, int iWidth) {
		table.setLinesVisible(bLines);
		table.setHeaderVisible(bHeader);
//		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = iHeight;
//		data.widthHint = iWidth;
//		table.setFont(new Font(AmscSplashScreen.display, FONTFACE, NORMAL, SWT.NORMAL));
		table.setLayoutData(data);
	}
	
	//---------style : LABEL
	public void labelStyle0(Label label, String str, Color frColor) {
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		label.setLayoutData(gd);
		label.setText(str); 
		label.setForeground(frColor);
//		label.setBackground(bgColor);
//		label.setFont(new Font(AmscSplashScreen.display, new FontData(FONTFACE, NORMAL, iStyle)));
	}
	
	public void labelStyle(Label label, String str, int iWidth, int iVer, int iHor, int iStyle, Color frColor) {
		GridData gd = new GridData(iVer, iHor, false, false);
		gd.widthHint = iWidth;
		label.setText(str); 
		label.setForeground(frColor);
		label.setLayoutData(gd);
//		label.setFont(new Font(AmscSplashScreen.display, new FontData(FONTFACE, NORMAL, iStyle)));
	}
	
	public void labelStyle1(Label label, String str, int iVer, int iHor, int iStyle, Color frColor) {
		GridData gd = new GridData(iVer, iHor, false, false);
		label.setText(str); 
		label.setForeground(frColor);
		label.setLayoutData(gd);
		label.setFont(new Font(AmscSplashScreen2.display, new FontData(FONTFACE, NORMAL, iStyle)));
	}
	
	public void clabelStyle(CLabel label, String str, int iWidth, int iVer, int iHor, int iStyle, Color frColor, Image img) {
		GridData gd = new GridData(iVer, iHor, false, false);
		gd.widthHint = iWidth;
		label.setText(str); 
		label.setImage(img);
		label.setForeground(frColor);
		label.setLayoutData(gd);
//		label.setFont(new Font(AmscSplashScreen.display, new FontData(FONTFACE, NORMAL, SWT.NORMAL)));
//		label.setFont(new Font(TeleSplashScreen.display, new FontData(FONTFACE, NORMAL, iStyle)));
//		rff.readConfiguration();
//		int iFontSize = rff.getFontSizeStatusBar();
//		label.setFont(new Font(TeleSplashScreen.display, new FontData(FONTFACE, iFontSize, iStyle)));
	}
	
	//---------style : TEXT
	public void textStyle(Text text, int iWidth, int iLimit, int iHor, int iVer, String rule, String sTool, boolean enabled) {
		GridData gd = new GridData(iVer, iHor, false, false);
		gd.widthHint = iWidth;
		gd.verticalAlignment = iVer;
		gd.horizontalAlignment = iHor;
		
		settextstyle(text, rule);
//		text.setFont(new Font(AmscSplashScreen.display, new FontData(FONTFACE, NORMAL, SWT.NORMAL)));
		text.setTextLimit(iLimit);
		text.setToolTipText(sTool);
		text.setEditable(enabled);
		text.setLayoutData(gd);
	}
	
	public void textAreaStyle(Text text, int iHeight, String sTool) {
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = iHeight;
		settextstyle(text, "upper");
		text.setToolTipText(sTool);
		
		text.setFont(new Font(AmscSplashScreen2.display, new FontData("Monospace", 8, SWT.NORMAL)));
//		text.setFont(new Font(AmscSplashScreen.display, new FontData(FONTFACE, NORMAL, SWT.NORMAL)));
		text.setLayoutData(gd);
	}
	
	public void textAreaStyle(Text text, int iWidth, int iHeight, int iHor, int iVer, String rule, String sTool) {
		GridData gd = new GridData();
		gd.widthHint = iWidth;
		gd.heightHint = iHeight;
		gd.verticalAlignment = iVer;
		gd.horizontalAlignment = iHor;
		
		settextstyle(text, rule);
		text.setToolTipText(sTool);
//		text.setFont(new Font(AmscSplashScreen.display, new FontData(FONTFACE, NORMAL, SWT.NORMAL)));
		text.setLayoutData(gd);
	}
	
	//---------style : COMPOSITE
	
	public void composeStyle(Composite comp, int iCol) {
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		GridLayout layout = new GridLayout();
		layout.marginBottom=0; layout.marginHeight=0; layout.marginLeft=0; layout.marginRight=0; layout.marginTop=0; layout.marginWidth=0;
		layout.numColumns = iCol;
		comp.setLayout(layout);
		comp.setLayoutData(gd);
	}
	
	public void composeStyle(Composite comp, int iCol, int iVer, int iHor) {
		GridData gd = new GridData(iVer, iHor, false, false);
		GridLayout layout = new GridLayout();
		layout.marginBottom=0; layout.marginHeight=0; layout.marginLeft=0; layout.marginRight=0; layout.marginTop=0; layout.marginWidth=0;
		layout.numColumns = iCol;
		comp.setLayout(layout);
		comp.setLayoutData(gd);
	}
	
	//+width
	public void composeStyle1(Composite comp, int iCol, int iVer, int iHor, int iWidth) {
		GridData gd = new GridData(iVer, iHor, false, false);
		GridLayout layout = new GridLayout();
		layout.marginBottom=0; layout.marginHeight=0; layout.marginLeft=0; layout.marginRight=0; layout.marginTop=0; layout.marginWidth=0;
		layout.numColumns = iCol;
		gd.widthHint = iWidth;
		comp.setLayout(layout);
		comp.setLayoutData(gd);
	}
}
