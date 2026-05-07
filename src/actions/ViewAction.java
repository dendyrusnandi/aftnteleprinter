/**
 * =======================================================================================================
 * show full message of the selected message from the List of AFTN Messages
 * feature : 
 * 1. set up the font
 * 2. print the data
 * 3. write the data into .txt file and save it in /tmp/ramdisk0/
 *    note : 
 *    filename rules = depend on the Message Type of the selected message
 *    e.g. = CNL.txt 
 * =======================================================================================================
 */
package actions;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import readwrite.ReadFromFile;
import readwrite.WriteToTXT;
import setting.ButtonsSetting;
import setting.Colors;
import setting.Shells;
import setting.Shorten;
import databases.jdbc;
import displays.DialogFactory;
import displays.MainForm;
import displays.TeleSplashScreen2016IP;
import displays.tables.TableATS;
import displays.tables.TableOutgoing;
import displays.tables.TableQueueLP;


public class ViewAction {
	static PrintAction printExec = new PrintAction();
	static Shorten sh = new Shorten();
	static ButtonsSetting bs = new ButtonsSetting();
	static ReadFromFile rff = new ReadFromFile();
	
	public static void view(final String idview, final Shell shell, String shellName, final String filename, final String data) {

		WriteToTXT.write(filename, data);
	    sh.shellStyle(shell, shellName);
        
	    rff.readConfiguration();
        int width = MainForm.getWidthRightTop()-400;
        int height = (rff.getHeightTblATS()-50);
        
        Composite comp = new Composite(shell, SWT.NONE); sh.compositeStyle(comp, 1, SWT.CENTER, SWT.CENTER);
		final Text text = new Text(comp, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		sh.textAreaStyle(text, width, height, SWT.CENTER, SWT.CENTER, "", "View Full Message");
		text.setEditable(false);
		text.setBackground(Colors.Grey);
		text.setFont(new Font(TeleSplashScreen2016IP.display, "Monospace", 10, SWT.NORMAL));
		
		Composite comp1 = new Composite(shell, SWT.NONE); sh.compositeStyle(comp1, 2, SWT.CENTER, SWT.CENTER);
		Button bPrint = new Button(comp1,SWT.PUSH); 
		sh.buttonStyle(bPrint,"Print","Print this data",bs.widthBtn,Colors.DarkGrey,SWT.CENTER,SWT.CENTER,null);
		bPrint.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				//1: Continuous printer
				//0: Standard printer
				String printerType = ReadFromFile.ReadInputFile1("/tp/stlp.txt");
				if (!printerType.isEmpty() && printerType.compareTo("1")==0) {
					DialogFactory.openInfoDialog(shell, "View Message", "Cannot printing while using continuous printer.");
					TriggerAction.trigger(data, 103);
					if (filename.compareToIgnoreCase("queue.txt")==0) {
						//delete from tabel queuelp
						String idcnt = TableQueueLP.id_all;
						if (!idcnt.isEmpty() && idcnt.contains(",")) {
							String subId[] = idcnt.split(",");
							for (int i=0; i<subId.length; i++) {
								jdbc.delete("DELETE FROM queuelp WHERE idcnt="+subId[i]);		
							}
							
							TableQueueLP.table.removeAll(); TableQueueLP.playersQueue.clear();
							RefreshTable.refreshTableQueue(TableQueueLP.pData, TableQueueLP.pDate, TableQueueLP.pDateTo);
						}
								
						//trigger untuk menampilkan jumlah data di tabel queuelp di tool item
						TriggerAction.trigger("QPrint", 101);
					}
				} else {
					try {
						String cmd = ReadFromFile.ReadInputFile1(MainForm.sFolder+"runprint.txt")+" "+
							MainForm.sPath+filename;
						System.out.println(cmd);
						Runtime.getRuntime().exec(cmd);
					} catch (IOException ioe) {
						System.err.println("err printing: " + ioe.getMessage());
					}	
				}
				
				
			}
		});

		
		if (data!=null) { text.setText(data); }
		
//		shell.setSize(width+45,height+95);
		shell.setSize(width+50,height+100);
		shell.setLocation(MainForm.setLocX(shell), MainForm.setLocY(shell));
		shell.open();
	
		shell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				if (idview.compareTo("ATS")==0) { TableATS.setTableFocus(); }
				else if (idview.compareTo("OUT")==0) { TableOutgoing.setTableFocus(); }
			}
		});
		
	}
	
//	public static void view(final Shell shell, String shellName, final String filename, final String data, int x, int y, final String attach) {
//		WriteToTXT.write(filename, data);
//	    rff.readConfiguration();
////	    x = rff.getxLocview();
////	    y = rff.getyLocview();
//        sh.shellStyle(shell, shellName, 0, 0);
//        
//        int width=rff.getWidthTextView();
//        int height=rff.getHeightTextView();
//        
//        Composite comp = new Composite(shell, SWT.NONE); sh.composeStyle(comp, 1, SWT.CENTER, SWT.CENTER);
//		final Text text = new Text(comp, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
////		sh.textAreaStyle(text, rff.getWidthTextView(), rff.getHeightTextView(), SWT.CENTER, SWT.CENTER, "", "View Full Message");
//		sh.textAreaStyle(text, width, height, SWT.CENTER, SWT.CENTER, "", "View Full Message");
//		text.setEditable(false);
//		text.setBackground(Colors.Grey);
//		
//		Composite comp1 = new Composite(shell, SWT.NONE); sh.composeStyle(comp1, 2, SWT.CENTER, SWT.CENTER);
//		Button bPrint = new Button(comp1,SWT.PUSH); 
////		sh.buttonStyle(bPrint, "Print", "Print this data", bs.widthBtn, bs.heightBtn, bs.colorBtn);
//		sh.buttonStyle(bPrint,"Print","Print this data",bs.widthBtn,Colors.DarkGrey,SWT.CENTER,SWT.CENTER,null);//Images.img_clear16);
//		bPrint.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				
//				//1: Continuous printer
//				//0: Standard printer
//				String printerType = ReadFromFile.ReadInputFile1("/tp/stlp.txt");
//				if (!printerType.isEmpty() && printerType.compareTo("1")==0) {
////					TriggerAction.triggerPrinter(data);
//					TriggerAction.trigger(data, 103);
//					if (filename.compareToIgnoreCase("queue.txt")==0) {
//						//delete from tabel queuelp
//						String idcnt = TableQueueLP.id_all;
//						if (!idcnt.isEmpty() && idcnt.contains(",")) {
//							String subId[] = idcnt.split(",");
//							for (int i=0; i<subId.length; i++) {
//								jdbc.delete("DELETE FROM queuelp WHERE idcnt="+subId[i]);		
//							}
//							
//							TableQueueLP.table.removeAll(); TableQueueLP.playersQueue.clear();
//							RefreshTable.refreshTableQueue(TableQueueLP.pData, TableQueueLP.pDate, TableQueueLP.pDateTo);
//						}
//								
//						//trigger untuk menampilkan jumlah data di tabel queuelp di tool item
//						TriggerAction.trigger("QPrint", 101);
//					}
//				} else {
//					
////					new Print(MainForm.sPath+filename);
//					
//					try {
////						rff.readConfiguration();
////						// cat /tmp/ramdisk0/filename > /dev/usb/lp0
////						String cmd = "cat "+MainForm.sPath + filename+" > "+rff.getPrinterCmd();
//						
//						String cmd = ReadFromFile.ReadInputFile1(MainForm.sFolder+"runprint.txt")+" "+MainForm.sPath+filename;
//						System.out.println(cmd);
//						Runtime.getRuntime().exec(cmd);
//					} catch (IOException ioe) {
//						System.err.println("err printing: " + ioe.getMessage());
//					}	
//				}
//				
//				
//			}
//		});
//
//		
//		if (data!=null) { text.setText(data); }
//		
////		shell.setSize(rff.getWidthView(),rff.getHeightView());
//		shell.setSize(width+45,height+95);
//		shell.setLocation(MainForm.setLocX(shell), MainForm.setLocY(shell));
//		shell.open();
//	}
}

