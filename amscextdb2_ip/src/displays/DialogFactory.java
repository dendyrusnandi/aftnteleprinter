package displays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;


public class DialogFactory {
	
	static MessageBox mb;
	static boolean penentuan=false;
	static int rc=0;
	
	public DialogFactory() {
		
	}
	
	static void mb_set(String title, String message) {
		mb.setText(title);
		mb.setMessage(message);
	}
	
//	public static void dialogWarning(Shell shell, String title, String message) {
//		mb = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
//		mb_set(title, message);
//		mb.open();
//	}
//	
//	public static void dialogInfo(Shell shell, String title, String message) {
//		mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
//		mb_set(title, message);
//		mb.open();
//	}
//	
//	public static void dialogYesNo(Shell shell, String title, String message) {
//		mb = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
//		mb.setText(title);
//		mb.setMessage(message);
//		int response = mb.open();
//        if (response == SWT.YES) penentuan = true;
//        else if (response == SWT.NO) penentuan = false;
//	}
//	
//	public static void dialogYesNo(Shell shell) {//, String title, String message) {
//		mb = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
////		mb_set(title, message);
//		mb.setText("Delete Item");
//		mb.setMessage("Are you sure ?");
//		int response = mb.open();
//        if (response == SWT.YES) penentuan = true;
//        else if (response == SWT.NO) penentuan = false;
//	}

	public static boolean getPenentuan() { return penentuan; }

	public static int getRC() { return rc; }
	
	public static void openInfoDialog(String title, String message) {
		mb = new MessageBox(MainForm.shell, SWT.ICON_INFORMATION);
		mb_set(title, message);
		mb.open();
	}
	
	
	
	//----------------
	public static String send = "Send Message";
	public static String find = "Find Message";
	public static String view = "View Message";
	public static String edit = "Edit Message";
	public static String delete = "Delete Message";
	public static String export = "Export Message";
	
	
	
	final static String LIST_INFO_AT_LEAST = "At least one row should be selected.";
	final static String LIST_INFO_DATA_NOT_FOUND = "Data not found. Your search didn't return any results.";//Data not found, your search has no result !
	final static String LIST_INFO_EXPORT_TRACE = "Do you want to include the Trace ?";
	final static String LIST_INFO_ARE_YOU_SURE = "Are you sure you want to delete ?";
	public final static String LIST_INFO_FILL_ANOTHER_CRITERIA = "Please fill another search criteria.";
	public final static String LIST_INFO_OUT_OF_MEMORY = "Out of memory.\n"+LIST_INFO_FILL_ANOTHER_CRITERIA;
	
	public static void openInfo(String message) {
		mb = new MessageBox(MainForm.shell, SWT.ICON_INFORMATION);
		mb_set(MainForm.title, message);
		mb.open();
	}
	
	public static void openInfoAtLeast(String title) {
		mb = new MessageBox(MainForm.shell, SWT.ICON_INFORMATION);
		mb_set(title, LIST_INFO_AT_LEAST);
		mb.open();
	}
	
	public static void openInfoOutOfMemory(String title) {
		mb = new MessageBox(MainForm.shell, SWT.ICON_INFORMATION);
		mb_set(title, LIST_INFO_OUT_OF_MEMORY);
		mb.open();
	}
	
	public static void openInfoDataNotFound(String title) {
		mb = new MessageBox(MainForm.shell, SWT.ICON_INFORMATION);
		mb_set(title, LIST_INFO_DATA_NOT_FOUND);
		mb.open();
	}
	
	public static void openWarningRequired(String title, String message) {
		mb = new MessageBox(MainForm.shell, SWT.ICON_WARNING | SWT.OK);
		mb_set(title, message);
		mb.open();
	}
	
	public static void openWarningRequired(Shell shell, String title, String message) {
		mb = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
		mb_set(title, "The value in field "+message+" is required.");
		mb.open();
	}
	
	public static void openWarningRequiredSetting(Shell shell, String title, String setting, String component) {
		mb = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
		mb_set(title, "Please insert "+setting+" for field "+component+".");
		mb.open();
	}
	
	public static void openYesNoExportTrace() {
		mb = new MessageBox(MainForm.shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.NONE);
		mb.setText(export);
		mb.setMessage(LIST_INFO_EXPORT_TRACE);
		int response = mb.open();
//        if (response == SWT.YES) penentuan = true;
//        else if (response == SWT.NO) penentuan = false;
        if (response == SWT.YES) rc= 1;
        else if (response == SWT.NO) rc=0;
        else rc=2;
	}
	
	public static void openYesNoDelete() {
		mb = new MessageBox(MainForm.shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		mb_set(delete, LIST_INFO_ARE_YOU_SURE);
		int response = mb.open();
        if (response == SWT.YES) penentuan = true;
        else if (response == SWT.NO) penentuan = false;
	}
}
