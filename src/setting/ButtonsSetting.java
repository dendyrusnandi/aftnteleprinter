package setting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import readwrite.ReadFromFile;




public class ButtonsSetting {

	Shorten sh = new Shorten();
	TextSetting ts = new TextSetting();
	ReadFromFile rff = new ReadFromFile();
	 
	int width100=100;
	public int widthBtn=110;
	int widthBtnFPL = 50;
//	public int heightBtn=30;
	public int widthNavBtn=40;
	public final Color colorBtn = Colors.DarkGrey;
	
	
	public ButtonsSetting() {
		
	}
	
	void button(Button btn, String strText) { sh.buttonStyle(btn,strText,"Click here to create a new "+strText+" Message from this FPL Message",widthBtnFPL,colorBtn,SWT.CENTER,SWT.CENTER,null);
//		sh.buttonStyle(btn, strText, "Click here to create a new "+strText+" Message from this FPL Message", widthBtnFPL, heightBtn, colorBtn);
	}

	void view(Button btn) { sh.buttonStyle(btn,"&View","View the selected message",widthBtn,colorBtn,SWT.CENTER,SWT.CENTER,Images.img_view);
//		sh.buttonStyle(btn, "&View", "View the selected message", widthBtn, heightBtn, colorBtn, Images.img_view);
	}

	void edit(Button btn) { sh.buttonStyle(btn,"&Edit","Edit the selected message",widthBtn,colorBtn,SWT.CENTER,SWT.CENTER,Images.img_edit);
//		sh.buttonStyle(btn, "&Edit", "Edit the selected message", widthBtn, heightBtn, colorBtn, Images.img_edit);
	}

	void delete(Button btn) { sh.buttonStyle(btn,"&Delete","Delete one or more the selected message(s)",widthBtn,colorBtn,SWT.CENTER,SWT.CENTER,Images.img_delete);
//		sh.buttonStyle(btn, "&Delete", "Delete one or more the selected message(s)", widthBtn, heightBtn, colorBtn, Images.img_delete);
	}

	void deleteall(Button btn) { sh.buttonStyle(btn,"Delete &all","Delete all messages from the table",widthBtn,colorBtn,SWT.CENTER,SWT.CENTER,Images.img_deleteall);
//		sh.buttonStyle(btn, "Delete &all", "Delete all messages from the table", widthBtn, heightBtn, colorBtn, Images.img_deleteall);
	}
	
	void exportpdf(Button btn) { sh.buttonStyle(btn,"","Export one or more the selected message(s) to PDF file",widthNavBtn,colorBtn,SWT.CENTER,SWT.CENTER,Images.img_exportpdf);
//		sh.buttonStyle(btn, "", "Export one or more the selected message(s) to PDF file", widthNavBtn, heightBtn, colorBtn, Images.img_exportpdf);
	}

	void exportpdfs(Button btn) { sh.buttonStyle(btn,"","Export all messages to PDF file",widthNavBtn,colorBtn,SWT.CENTER,SWT.CENTER,Images.img_exportpdfs);
//		sh.buttonStyle(btn, "", "Export all messages to PDF file", widthNavBtn, heightBtn, colorBtn, Images.img_exportpdfs);
	}
	
	void exportword(Button btn) { sh.buttonStyle(btn,"","Export one or more the selected message(s) to Word file",widthNavBtn,colorBtn,SWT.CENTER,SWT.CENTER,Images.img_exportword);
//		sh.buttonStyle(btn, "", "Export one or more the selected message(s) to Word file", widthNavBtn, heightBtn, colorBtn, Images.img_exportword);
	}

	void exportwords(Button btn) { sh.buttonStyle(btn,"","Export all messages to Word file",widthNavBtn,colorBtn,SWT.CENTER,SWT.CENTER,Images.img_exportwords);
//		sh.buttonStyle(btn, "", "Export all messages to Word file", widthNavBtn, heightBtn, colorBtn, Images.img_exportwords);
	}
	
//	void view1(Button btn) {
//		sh.buttonStyle(btn, "View", "View the selected message", widthBtn, heightBtn, colorBtn);
//	}
//
//	void edit1(Button btn) {
//		sh.buttonStyle(btn, "Edit", "Edit the selected message", widthBtn, heightBtn, colorBtn);
//	}
//	
//	void print1(Button btn) {
//		sh.buttonStyle(btn, "Print", "Print one or more selected message via PDF", widthBtn, heightBtn, colorBtn);
//	}
//
//	void printall1(Button btn) {
//		sh.buttonStyle(btn, "Print All", "Print all messages via PDF", widthBtn, heightBtn, colorBtn);
//	}
//
//	void delete1(Button btn) {
//		sh.buttonStyle(btn, "Delete", "Delete one or more selected message", widthBtn, heightBtn, colorBtn);
//	}
//
//	void deleteall1(Button btn) {
//		sh.buttonStyle(btn, "Delete All", "Delete all messages from the table", widthBtn, heightBtn, colorBtn);
//	}
	
	void save(Button btn) { sh.buttonStyle(btn,"&Save","Save the data into the database",widthBtn,colorBtn,SWT.CENTER,SWT.CENTER,Images.img_save16);
//		sh.buttonStyle(btn, "&Save", "Save the data into the database", widthBtn, heightBtn, colorBtn, Images.img_save16);
	}
	
	void update(Button btn) { sh.buttonStyle(btn,"&Update","Update the selected data from the table",widthBtn,colorBtn,SWT.CENTER,SWT.CENTER,Images.img_update16);
//		sh.buttonStyle(btn, "&Update", "Update the selected data from the table", widthBtn, heightBtn, colorBtn, Images.img_update16);
	}
	
	void clear(Button btn) { sh.buttonStyle(btn,"&Clear","Clear all items in this template",widthBtn,colorBtn,SWT.CENTER,SWT.CENTER,Images.img_clear16);
//		sh.buttonStyle(btn, "&Clear", "Clear all items in this template", widthBtn, heightBtn, colorBtn, Images.img_clear16);
	}
	
	void clear1(Button btn) { sh.buttonStyle(btn,"Clea&r","Clear all items in this template",widthBtn,colorBtn,SWT.CENTER,SWT.CENTER,Images.img_clear16);
//		sh.buttonStyle(btn, "Clea&r", "Clear all items in this template", widthBtn, heightBtn, colorBtn, Images.img_clear16);
	}
	
	void search(Button btn) { sh.buttonStyle(btn,"Searc&h","Search data",widthBtn,colorBtn,SWT.CENTER,SWT.CENTER,Images.img_search16);
//		sh.buttonStyle(btn, "Searc&h", "Search data", widthBtn, heightBtn, colorBtn, Images.img_search16);
	}
	
	void add(Button btn) { sh.buttonStyle(btn,"&Add","Add checklist indicator value",width100,colorBtn,SWT.CENTER,SWT.CENTER,null);
//		sh.buttonStyle(btn, "&Add", "Add checklist indicator value", width100, heightBtn, colorBtn);
	}
	
	void close(Button btn) { sh.buttonStyle(btn,"Cl&ose","Close",width100,colorBtn,SWT.CENTER,SWT.CENTER,null);
//		sh.buttonStyle(btn, "Cl&ose", "Close", width100, heightBtn, colorBtn);
	}
	
	//form : mini PBN,STS,10a,10b
	public void InputCancel(Button Input, Button Cancel) {
		sh.buttonStyle(Input,"&Input","Input indicator value",widthBtn,colorBtn,SWT.CENTER,SWT.CENTER,null);
		sh.buttonStyle(Cancel,"&Cancel","Cancel input indicator value",widthBtn,colorBtn,SWT.CENTER,SWT.CENTER,null);
		
//		sh.buttonStyle(Input, "&Input", "Input indicator value", widthBtn, heightBtn, colorBtn);
//		sh.buttonStyle(Cancel, "&Cancel", "Cancel input indicator value", widthBtn, heightBtn, colorBtn);
	}
	
	public void ACC(Button Add, Button Clear, Button Close) {
		add(Add);
		close(Close);
		sh.buttonStyle(Clear,"&Clear","Clear all items in this template",width100,colorBtn,SWT.CENTER,SWT.CENTER,null);
//		sh.buttonStyle(Clear, "&Clear", "Clear all items in this template", width100, heightBtn, colorBtn);
	}
	
	public void AC(Button Add, Button Close) {
		add(Add);
		close(Close);
	}
	
	void PP(Button exPDF,Button exPDFs) {//*
		sh.buttonStyle(exPDF, "&PDF", "Export one or more selected message into PDF file", widthBtn, colorBtn, SWT.CENTER, SWT.LEFT, Images.img_exportpdf);
		sh.buttonStyle(exPDFs, "PD&Fs", "Export all messages from the table into PDF file", widthBtn, colorBtn, SWT.CENTER, SWT.LEFT, Images.img_exportpdfs);
		
//		sh.button(exPDF, "&PDF       ", "Export one or more selected message into PDF file", colorBtn, SWT.CENTER, SWT.LEFT, Images.img_exportpdf);
//		sh.button(exPDFs, "PD&Fs      ", "Export all messages from the table into PDF file", colorBtn, SWT.CENTER, SWT.LEFT, Images.img_exportpdfs);
	}
	
	//form : set Printer
	public void SetCancel(Button Set, Button OK, Button Cancel) {
		sh.buttonStyle(Set, "&Set up", "Open printer configuration", widthBtn, colorBtn, SWT.CENTER, SWT.LEFT, Images.img_setup16);
		sh.buttonStyle(OK, "&Ok", "Set printer type", widthBtn, colorBtn, SWT.CENTER, SWT.LEFT, Images.img_ok16);
		sh.buttonStyle(Cancel, "&Cancel", "Cancel setting printer type", widthBtn, colorBtn, SWT.CENTER, SWT.LEFT, Images.img_clear16);
	}
	
	//form : search ATS,ROUTE,TYPE9B,REG/,OUTBOX,ABBR,LOCIND,QUEUE
	public void SC1(Button Search, Button Clear) {
		search(Search);
		clear(Clear);
	}
	
	public void SC(Button Search, Button Clear) {
		sh.buttonStyle(Search,"","Search data",widthNavBtn,colorBtn,SWT.CENTER,SWT.CENTER,Images.img_search16);
		sh.buttonStyle(Clear,"","Clear all items in this template",widthNavBtn,colorBtn,SWT.CENTER,SWT.CENTER,Images.img_clear16);
//		search(Search);
//		clear(Clear);
	}
	
	//form : table ROUTE,TYPE9B,REG/,LOCIND
	public void SUC(Button Save, Button Update, Button Clear) {
		save(Save);
		update(Update);
		clear1(Clear);
	} 
	
	//form : table ROUTE,TYPE9B,REG/,LOCIND
	public void EDD(Button Edit, Button Delete, Button DeleteAll) {
		edit(Edit);
		delete(Delete);
		deleteall(DeleteAll);
	}
	
	//form : table ATS*
	public void ButtonTableAFTN(Button View,Button Edit,Button Delete,Button DeleteAll,Button exPDF,Button exPDFs,Button exDOC,Button exDOCs) {//,Button exXLS,Button exXLSes) {
		int widthBtn=100;
		sh.buttonStyle(View, "&View", "View the selected message", widthBtn, colorBtn, SWT.CENTER, SWT.LEFT, Images.img_view);
		sh.buttonStyle(Edit, "&Edit", "Edit the selected message", widthBtn, colorBtn, SWT.CENTER, SWT.LEFT, Images.img_edit);
		sh.buttonStyle(Delete, "&Delete", "Delete one or more selected message", widthBtn, colorBtn, SWT.CENTER, SWT.LEFT, Images.img_delete);
		sh.buttonStyle(DeleteAll, "Delete &all", "Delete all messages from the table", widthBtn, colorBtn, SWT.CENTER, SWT.LEFT, Images.img_deleteall);
		sh.buttonStyle(exDOC, "D&OC", "Export one or more selected message into DOC file", widthBtn, colorBtn, SWT.CENTER, SWT.LEFT, Images.img_exportword);
		sh.buttonStyle(exDOCs, "DOC&s", "Export all messages from the table into DOC file", widthBtn, colorBtn, SWT.CENTER, SWT.LEFT, Images.img_exportwords);
		PP(exPDF, exPDFs);
		
//		sh.button(View, "   &View   ", "View the selected message", colorBtn, SWT.CENTER, SWT.CENTER, Images.img_view);
//		sh.button(Edit, "   &Edit   ", "Edit the selected message", colorBtn, SWT.CENTER, SWT.CENTER, Images.img_edit);
//		sh.button(Delete, "  &Delete  ", "Delete one or more selected message", colorBtn, SWT.CENTER, SWT.CENTER, Images.img_delete);
//		sh.button(DeleteAll, "Delete &all", "Delete all messages from the table", colorBtn, SWT.CENTER, SWT.CENTER, Images.img_deleteall);
//		sh.button(exDOC, "   D&OC    ", "Export one or more selected message into DOC file", colorBtn, SWT.CENTER, SWT.CENTER, Images.img_exportword);
//		sh.button(exDOCs, "   DOC&s   ", "Export all messages from the table into DOC file", colorBtn, SWT.CENTER, SWT.RIGHT, Images.img_exportwords);
//		
//		sh.button(View, "   &View   ", "View the selected message", colorBtn, SWT.CENTER, SWT.CENTER, null);
//		sh.button(Edit, "   &Edit   ", "Edit the selected message", colorBtn, SWT.CENTER, SWT.CENTER, null);
//		sh.button(Delete, "  &Delete  ", "Delete one or more selected message", colorBtn, SWT.CENTER, SWT.CENTER, null);
//		sh.button(DeleteAll, "Delete &all", "Delete all messages from the table", colorBtn, SWT.CENTER, SWT.CENTER, null);
//		sh.button(exDOC, "   D&OC    ", "Export one or more selected message into DOC file", colorBtn, SWT.CENTER, SWT.CENTER, null);
//		sh.button(exDOCs, "   DOC&s   ", "Export all messages from the table into DOC file", colorBtn, SWT.CENTER, SWT.RIGHT, null);
		
	}	
	
	//form : table QUEUE
	public void PDD(Button Print, Button Delete, Button DeleteAll) {
		sh.buttonStyle(Print,"&Print","Print all queue messages",widthBtn,colorBtn,SWT.CENTER,SWT.CENTER,Images.img_printer16);
		delete(Delete);
		deleteall(DeleteAll);
	}
	
	public void DD(Button Delete, Button DeleteAll) {
		delete(Delete);
		deleteall(DeleteAll);
	}
	
	
	//form : table OUTBOX
	public void VEDD(Button View, Button Edit, Button Delete, Button DeleteAll) {
		view(View);
		edit(Edit);
		delete(Delete);
		deleteall(DeleteAll);
	}
	
	//form : table WARNING
	public void DDPP(Button Delete, Button DeleteAll,Button exPDF,Button exPDFs) {
		delete(Delete);
		deleteall(DeleteAll);
		PP(exPDF, exPDFs);
	}
	
	//form : all table
	public void arrow(Button first, Button prev, Button next, Button last) {
//		sh.buttonStyle(first, "", "Go to the first page", widthNavBtn, heightBtn, colorBtn, Images.img_first);
//		sh.buttonStyle(prev, "", "Go to the previous page", widthNavBtn, heightBtn, colorBtn, Images.img_previous);
//		sh.buttonStyle(next, "", "Go to the next page", widthNavBtn, heightBtn, colorBtn, Images.img_next);
//		sh.buttonStyle(last, "", "Go to the last page", widthNavBtn, heightBtn, colorBtn, Images.img_last);
		
		sh.buttonStyle(first, "", "Go to the first page", widthNavBtn, colorBtn, SWT.CENTER, SWT.LEFT, Images.img_first);
		sh.buttonStyle(prev, "", "Go to the previous page", widthNavBtn, colorBtn, SWT.CENTER, SWT.LEFT, Images.img_previous);
		sh.buttonStyle(next, "", "Go to the next page", widthNavBtn, colorBtn, SWT.CENTER, SWT.LEFT, Images.img_next);
		sh.buttonStyle(last, "", "Go to the last page", widthNavBtn, colorBtn, SWT.CENTER, SWT.LEFT, Images.img_last);
		
	}
	
	public void Navigation(Text tPage, Text tTotPage, Button first, Button prev, Button next, Button last, Text tGoToHal) {
		sh.textStyle(tPage, 40, 5, SWT.LEFT, SWT.LEFT, sh.numeric, "Page", true);
		sh.textStyle(tTotPage, 40, 5, SWT.LEFT, SWT.LEFT, sh.numeric, "Total Page", true);
		sh.textStyle(tGoToHal, 38, 5, SWT.LEFT, SWT.LEFT, sh.numeric, "Go to Page", true);
		
		first.setEnabled(false);
		prev.setEnabled(false);
		tPage.setEnabled(false);
		tTotPage.setEnabled(false);
		
		arrow(first, prev, next, last);
		
		tGoToHal.setText("1");
	}
	
	//form : miniFPL
	public void miniFPL(Button btnDLA, Button btnCHG, Button btnCNL, Button btnDEP, Button btnARR) {
		button(btnDLA, "DLA");
		button(btnCHG, "CHG");
		button(btnCNL, "CNL");
		button(btnDEP, "DEP");
		button(btnARR, "ARR");
	}
}
