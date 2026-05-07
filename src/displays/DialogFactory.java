package displays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import setting.ErrMsg;


public class DialogFactory {

	static boolean penentuan=false;
	static MessageBox mb;
	
	public static String view = "View Message";
	public static String edit = "Edit Message";
	public static String delete = "Delete Message";
	public static String export = "Export Message";
	
	
	static void mb_set(String title, String message) {
		mb.setText(title);
		mb.setMessage(message);
	}
	
	public static void openErrorDialog(Shell shell, String title, String message) {
		mb = new MessageBox(shell, SWT.ICON_ERROR);
		mb_set(title, message);
		mb.open();
	}
	
	public static void openWarningDialog(Shell shell, String title, String message) {
		mb = new MessageBox(shell, SWT.ICON_WARNING);
		mb_set(title, message);
		mb.open();
	}
	
	public static void openInfoDialog(Shell shell, String title, String message) {
		mb = new MessageBox(shell, SWT.ICON_INFORMATION);
		mb_set(title, message);
		mb.open();
	}
	
	public static void openInfoAtLeast(Shell shell, String title) {
		mb = new MessageBox(shell, SWT.ICON_INFORMATION);
		mb_set(title, ErrMsg.LIST_INFO_AT_LEAST);
		mb.open();
	}
	
	public static void openInfoEditMode(Shell shell, String title) {
		mb = new MessageBox(shell, SWT.ICON_INFORMATION);
		mb_set(title, ErrMsg.LIST_INFO_EDIT_SINGLE_SELECTION);
		mb.open();
	}
	
//	public static void openInfoDeleteAtLeast(Shell shell) {
//		mb = new MessageBox(shell, SWT.ICON_INFORMATION);
//		mb_set("Delete Message", ErrMsg.LIST_INFO_AT_LEAST);
//		mb.open();
//	}
//	
//	public static void openInfoEditAtLeast(Shell shell) {
//		mb = new MessageBox(shell, SWT.ICON_INFORMATION);
//		mb_set("Edit Message", ErrMsg.LIST_INFO_AT_LEAST);
//		mb.open();
//	}
//	
//	public static void openInfoViewAtLeast(Shell shell) {
//		mb = new MessageBox(shell, SWT.ICON_INFORMATION);
//		mb_set("View Message", ErrMsg.LIST_INFO_AT_LEAST);
//		mb.open();
//	}
	
	public static void openYesCancelDialog(Shell shell, String title, String message) {
		mb = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.CANCEL);
		mb_set(title, message);
		int response = mb.open();
	    if (response == SWT.YES) penentuan = true;
	    else if (response == SWT.CANCEL) penentuan = false;
	}
	
	public static void openYesNoDialog(Shell shell, String title, String message) {
		mb = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		mb_set(title, message);
		int response = mb.open();
        if (response == SWT.YES) penentuan = true;
        else if (response == SWT.NO) penentuan = false;
	}
	
	public static void openYesNoDelete(Shell shell) {
		mb = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		mb_set("Delete Message", ErrMsg.LIST_INFO_ARE_YOU_SURE);
		int response = mb.open();
        if (response == SWT.YES) penentuan = true;
        else if (response == SWT.NO) penentuan = false;
	}

	public static boolean getPenentuan() { return penentuan; }
	
//	public static void openErrorDialog(String title, String message) {
//		mb = new MessageBox(MenuUtama.shell, SWT.ICON_ERROR);
//		mb_set(title, message);
//		mb.open();
//	}
//	
//	public static void openWarningDialog(String title, String message) {
//		mb = new MessageBox(MenuUtama.shell, SWT.ICON_WARNING);
//		mb_set(title, message);
//		mb.open();	
//	}
//
//	public static void openInfoDialog(String title, String message) {
//		mb = new MessageBox(MenuUtama.shell, SWT.ICON_INFORMATION);
//		mb_set(title, message);
//		mb.open();
//	}
//	
//	public static void openYesCancelDialog(String title, String message) {
//		mb = new MessageBox(MenuUtama.shell, SWT.ICON_QUESTION | SWT.YES | SWT.CANCEL);
//		mb_set(title, message);
//		int response = mb.open();
//	    if (response == SWT.YES) penentuan = true;
//	    else if (response == SWT.CANCEL) penentuan = false;
////		return penentuan;
//	}
//	
//	public static void openYesNoDialog(String title, String message) {
//		mb = new MessageBox(MenuUtama.shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
//		mb_set(title, message);
//		int response = mb.open();
//        if (response == SWT.YES) penentuan = true;
//        else if (response == SWT.NO) penentuan = false;
////		return penentuan;
//	}
}
