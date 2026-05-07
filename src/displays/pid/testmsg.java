package displays.pid;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import setting.ButtonsSetting;
import setting.Colors;
import setting.Shorten;
import actions.SendSave;
import databases.jdbc;
import displays.DialogFactory;
import displays.MainForm;


public class testmsg {
	Shorten sh = new Shorten();
	ButtonsSetting bs = new ButtonsSetting();
	SendSave ss = new SendSave();
	
	Shell shl;
	Button bstart,bstop;
	Text ttimes,torg,taddress,tsvc;
	static Text tout;
	static int count=0;
	static String smsg="";
	String test1="",test2="",test3="";
	Button bq1,bq2,bq3;
	String address="",org="",s_message;
	static Label lcounter;
	
	String getText() {
		return "Test Message & SVC TRAF";
	}

	public testmsg(Shell shel) {
		conf();
		shl = shel;
		Group grp = new Group(shl, SWT.BORDER); sh.groupStyle(grp, 1, "Test Message");

		Composite comp = new Composite(grp, SWT.NONE); sh.compositeStyle(comp, 2, SWT.LEFT, SWT.CENTER);
		Label label = new Label(comp, SWT.NONE); sh.labelStyle1(label, "State : ", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		Composite comp1 = new Composite(comp, SWT.NONE); sh.compositeStyle(comp1, 5, SWT.LEFT, SWT.CENTER);
		bstart = new Button(comp1, SWT.RADIO); 
		bstop = new Button(comp1, SWT.RADIO); 
		ttimes = new Text(comp1, SWT.BORDER); 
		label = new Label(comp1, SWT.NONE); sh.labelStyle1(label, "times", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		label = new Label(comp1, SWT.NONE); sh.labelStyle1(label, " ( Insert 0 for continuous )", SWT.LEFT, SWT.CENTER, SWT.ITALIC, Colors.NORMAL);
		
		label = new Label(comp, SWT.NONE); sh.labelStyle1(label, "Test format : ", SWT.LEFT, SWT.TOP, SWT.BOLD, Colors.NORMAL);
		Composite comp2 = new Composite(comp, SWT.NONE); sh.compositeStyle(comp2, 1, SWT.LEFT, SWT.CENTER);
		bq1 = new Button (comp2,SWT.RADIO); 
		bq2 = new Button (comp2,SWT.RADIO); 
		bq3 = new Button (comp2,SWT.RADIO); 
		
		label = new Label(comp, SWT.NONE); sh.labelStyle1(label, "Origin : ", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		Composite comp3 = new Composite(comp, SWT.NONE); sh.compositeStyle(comp3, 6, SWT.LEFT, SWT.CENTER);
		torg = new Text(comp3, SWT.BORDER); 
		Button bok = new Button(comp3,SWT.PUSH); 
		Button bcancel = new Button(comp3,SWT.PUSH); 
		
//		Composite comp1 = new Composite(grp, SWT.NONE); sh.compositeStyle(comp1, 6, SWT.LEFT, SWT.CENTER);
//		Label label = new Label(comp1, SWT.NONE); sh.labelStyle(label, "State : ", 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
//		bstart = new Button(comp1, SWT.RADIO); 
//		bstop = new Button(comp1, SWT.RADIO); 
//		ttimes = new Text(comp1, SWT.BORDER); 
//		label = new Label(comp1, SWT.NONE); sh.labelStyle1(label, "times", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
//		label = new Label(comp1, SWT.NONE); sh.labelStyle1(label, " ( Insert 0 for continuous )", SWT.LEFT, SWT.CENTER, SWT.ITALIC, Colors.NORMAL);
		
//		Composite comp2 = new Composite(grp, SWT.NONE); sh.compositeStyle(comp2, 2, SWT.LEFT, SWT.CENTER);
//		label = new Label(comp2, SWT.NONE); sh.labelStyle(label, "Test format : ", 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
//		bq1 = new Button (comp2,SWT.RADIO); 
//		label = new Label(comp2, SWT.NONE); 
//		bq2 = new Button (comp2,SWT.RADIO); 
//		label = new Label(comp2, SWT.NONE); 
//		bq3 = new Button (comp2,SWT.RADIO); 
		
//		Composite comp3 = new Composite(grp, SWT.NONE); sh.compositeStyle(comp3, 6, SWT.LEFT, SWT.CENTER);
//		label = new Label(comp3, SWT.NONE); sh.labelStyle(label, "Origin : ", 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
//		torg = new Text(comp3, SWT.BORDER); 
//		Button bok = new Button(comp3,SWT.PUSH); 
//		Button bcancel = new Button(comp3,SWT.PUSH); 

		torg.setText(org);
		
//		sh.buttonRCStyle(bstart, "Start", "", 60);//, 30);
//		sh.buttonRCStyle(bstop, "Stop", "", 60);//, 30);
		sh.textStyle(ttimes, 60, 10, SWT.LEFT, SWT.CENTER, sh.numeric, "", true);
//		sh.buttonRCStyle(bq1, "QJH RYRY...", "", 250);//, 30);
//		sh.buttonRCStyle(bq2, "QUICK BROWN FOX...", "", 250);//, 30);
//		sh.buttonRCStyle(bq3, "DE RYRY...", "", 250);//, 30);
		sh.textStyle(torg, 120, 8, SWT.LEFT, SWT.CENTER, sh.letter, "", true);
		sh.buttonStyle(bok,"&OK","",bs.widthBtn,Colors.DarkGrey,SWT.CENTER,SWT.CENTER,null);//Images.img_clear16);
		sh.buttonStyle(bcancel,"&Cancel","",bs.widthBtn,Colors.DarkGrey,SWT.CENTER,SWT.CENTER,null);//Images.img_clear16);
		
		sh.RadioCheckStyle(bstart, "Start", "");
		sh.RadioCheckStyle(bstop, "Stop", "");
		sh.RadioCheckStyle(bq1, "QJH RYRY...", "");
		sh.RadioCheckStyle(bq2, "QUICK BROWN FOX...", "");
		sh.RadioCheckStyle(bq3, "DE RYRY...", "");
		
		bstart.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				bstart.setSelection(true);
				bstop.setSelection(false);
			}
		});

		bstop.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				bstart.setSelection(false);
				bstop.setSelection(true);
				timeoutext.g_start=false;
			}
		});

		ttimes.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296)) && (bstart.getSelection()) && (!ttimes.getText().isEmpty()) ) {
					String scode = DisplayMain.readInputFile("/tp/databits.txt");
					if (!scode.isEmpty()) {
						String sarr[] = scode.split("\n");
						scode="";
						for (int i=0;i<1;i++) {
							scode=sarr[i];
						}
					}
					String fn="";
					if (scode.compareTo("5")==0) fn="/tp/serialita2.conf";
					else fn="/tp/serial.conf";

					test1 = DisplayMain.readInputFile(fn);
					if (!test1.isEmpty()) {
						String sarr1[] = test1.split("-");
						test1="";
						for (int i=0;i<sarr1.length;i++) {
							if (sarr1[i].contains("TEST1")) {
								test1=sarr1[i];break;
							}
						}
						String sarr[] = test1.split("=");
						for (int i=0;i<sarr.length;i++) {
							if (i!=0) test1=sarr[i];
						}
						sarr = test1.split("\n");
						test1="";
						for (int i=0;i<sarr.length;i++) {
							if (!test1.isEmpty()) test1+="\r\n";
							if (i==0) test1+=sarr[i]+" "+org;
							else test1+=sarr[i];
						}
					}
					System.out.println("***TEST1:"+test1);
					test2 = DisplayMain.readInputFile(fn);
					if (!test2.isEmpty()) {
						String sarr1[] = test2.split("-");
						test2="";
						for (int i=0;i<sarr1.length;i++) {
							if (sarr1[i].contains("TEST2")) {
								test2=sarr1[i];break;
							}
						}
						String sarr[] = test2.split("=");
						for (int i=0;i<sarr.length;i++) {
							if (i!=0) test2=sarr[i];
						}
						sarr = test2.split("\n");
						test2="";
						for (int i=0;i<sarr.length;i++) {
							if (!test2.isEmpty()) test2+="\r\n";
							if (i==0) test2+=sarr[i]+" "+org;
							else test2+=sarr[i];
						}
					}
					System.out.println("***TEST2:"+test2);
					test3 = DisplayMain.readInputFile(fn);
					if (!test3.isEmpty()) {
						String sarr1[] = test3.split("-");
						test3="";
						for (int i=0;i<sarr1.length;i++) {
							if (sarr1[i].contains("TEST3")) {
								test3=sarr1[i];break;
							}
						}
						String sarr[] = test3.split("=");
						for (int i=0;i<sarr.length;i++) {
							if (i!=0) test3=sarr[i];
						}
						sarr = test3.split("\n");
						test3="";
						for (int i=0;i<sarr.length;i++) {
							if (!test3.isEmpty()) test3+="\r\n";
							if (i==0) test3+=sarr[i]+" "+org+" "+org+" "+org;
							else test3+=sarr[i];
						}
					}
					System.out.println("***TEST3:"+test3);
					smsg="";
					if (bq1.getSelection()) smsg=test1;
					else if (bq2.getSelection()) smsg=test2;
					else if (bq3.getSelection()) smsg=test3;
					if (!smsg.isEmpty()) {
						System.out.println("sent serial...\n");
						count=Integer.parseInt(ttimes.getText());
						timeoutext.g_start=true;
					}
      			}
			}
		});

		bq1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				bq1.setSelection(true);
				bq2.setSelection(false);
				bq3.setSelection(false);
			}
		});

		bq2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				bq1.setSelection(false);
				bq2.setSelection(true);
				bq3.setSelection(false);
			}
		});

		bq3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				bq1.setSelection(false);
				bq2.setSelection(false);
				bq3.setSelection(true);
			}
		});

//		//asli
//		 bok.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				if (!torg.getText().isEmpty()) {
//					if (torg.getText().length()!=8) {
//						MessageBox mb = new MessageBox(shl);
//						mb.setMessage("Please insert 8 letters for field Origin !!!");
//						mb.setText("Warning...!!!");
//						mb.open();
//					}
//					else {
//						WriteToAFile wr = new WriteToAFile();
//						wr.open("/tp/originator.txt");
//						wr.write(torg.getText());
//						wr.close();
//					}
//				}
//
//				if (!taddress.getText().isEmpty()) {
//					if (taddress.getText().length()!=8) {
//						MessageBox mb = new MessageBox(shl);
//						mb.setMessage("Please insert 8 letters for field Address !!!");
//						mb.setText("Warning...!!!");
//						mb.open();
//					}
//					else {
//						WriteToAFile wr1 = new WriteToAFile();
//						wr1.open("/tp/address.txt");
//						wr1.write(taddress.getText());
//						wr1.close();
//					}
//				}
//
//				conf();
//				if ((bstart.getSelection()) && (!ttimes.getText().isEmpty())){
//					String scode = DisplayMain.readInputFile("/tp/databits.txt");
//					if (!scode.isEmpty()) {
//						String sarr[] = scode.split("\n");
//						scode="";
//						for (int i=0;i<1;i++) {
//							scode=sarr[i];
//						}
//					}
//					String fn="";
//					if (scode.compareTo("5")==0) fn="/tp/serialita2.conf";
//					else fn="/tp/serial.conf";
//
//					test1 = DisplayMain.readInputFile(fn);
//					if (!test1.isEmpty()) {
//						String sarr1[] = test1.split("-");
//						test1="";
//						for (int i=0;i<sarr1.length;i++) {
//							if (sarr1[i].contains("TEST1")) {
//								test1=sarr1[i];break;
//							}
//						}
//						String sarr[] = test1.split("=");
//						for (int i=0;i<sarr.length;i++) {
//							if (i!=0) test1=sarr[i];
//						}
//						sarr = test1.split("\n");
//						test1="";
//						for (int i=0;i<sarr.length;i++) {
//							if (!test1.isEmpty()) test1+="\r\n";
//							if (i==0) test1+=sarr[i]+" "+org;
//							else test1+=sarr[i];
//						}
//					}
//					System.out.println("***TEST1:"+test1);
//					test2 = DisplayMain.readInputFile(fn);
//					if (!test2.isEmpty()) {
//						String sarr1[] = test2.split("-");
//						test2="";
//						for (int i=0;i<sarr1.length;i++) {
//							if (sarr1[i].contains("TEST2")) {
//								test2=sarr1[i];break;
//							}
//						}
//						String sarr[] = test2.split("=");
//						for (int i=0;i<sarr.length;i++) {
//							if (i!=0) test2=sarr[i];
//						}
//						sarr = test2.split("\n");
//						test2="";
//						for (int i=0;i<sarr.length;i++) {
//							if (!test2.isEmpty()) test2+="\r\n";
//							if (i==0) test2+=sarr[i]+" "+org;
//							else test2+=sarr[i];
//						}
//					}
//					System.out.println("***TEST2:"+test2);
//					test3 = DisplayMain.readInputFile(fn);
//					if (!test3.isEmpty()) {
//						String sarr1[] = test3.split("-");
//						test3="";
//						for (int i=0;i<sarr1.length;i++) {
//							if (sarr1[i].contains("TEST3")) {
//								test3=sarr1[i];break;
//							}
//						}
//						String sarr[] = test3.split("=");
//						for (int i=0;i<sarr.length;i++) {
//							if (i!=0) test3=sarr[i];
//						}
//						sarr = test3.split("\n");
//						test3="";
//						for (int i=0;i<sarr.length;i++) {
//							if (!test3.isEmpty()) test3+="\r\n";
//							if (i==0) test3+=sarr[i]+" "+org+" "+org+" "+org;
//							else test3+=sarr[i];
//						}
//					}
//					System.out.println("***TEST3:"+test3);
//					smsg="";
//					if (bq1.getSelection()) smsg=test1;
//					else if (bq2.getSelection()) smsg=test2;
//					else if (bq3.getSelection()) smsg=test3;
//					if (!smsg.isEmpty()) {
//						System.out.println("sent serial...\n");
//						count=Integer.parseInt(ttimes.getText());
//						timeoutext.g_start=true;
//					}
//				}
//			}
//		});

//		 /*** percobaan
		 bok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				if (bstart.getSelection()==false && bstop.getSelection()==false) {
					DialogFactory.openInfoDialog(shl, getText(), "The value in field State is required."); bstart.setFocus(); }
				else if (ttimes.getText().isEmpty()) {
					DialogFactory.openInfoDialog(shl, getText(), "The value in field Times is required."); ttimes.setFocus(); }
				else if (bq1.getSelection()==false && bq2.getSelection()==false && bq3.getSelection()==false) {
					DialogFactory.openInfoDialog(shl, getText(), "The value in field Test format is required."); bq1.setFocus(); }
				else if (torg.getText().isEmpty()) {
					DialogFactory.openInfoDialog(shl, getText(), "The value in field Origin is required."); torg.setFocus(); }
				else if (!torg.getText().isEmpty() && torg.getText().length()<8) {
					DialogFactory.openInfoDialog(shl, getText(), "Please insert 8 LETTERS for field Origin !"); torg.setFocus(); }
				else if (taddress.getText().isEmpty()) {
					DialogFactory.openInfoDialog(shl, getText(), "The value in field Address is required."); taddress.setFocus(); }
				else if (!taddress.getText().isEmpty() && taddress.getText().length()<8) {
					DialogFactory.openInfoDialog(shl, getText(), "Please insert 8 LETTERS for field Address !"); taddress.setFocus(); }
				else {
					if (!torg.getText().isEmpty()) {
						if (torg.getText().length()!=8) {
							MessageBox mb = new MessageBox(shl);
							mb.setMessage("Please insert 8 LETTERS for field Origin !");
							mb.setText("Warning...!!!");
							mb.open();
						} else {
							WriteToAFile wr = new WriteToAFile();
							wr.open("/tp/originator.txt");
							wr.write(torg.getText());
							wr.close();
						}
					}

					if (!taddress.getText().isEmpty()) {
						if (taddress.getText().length()!=8) {
							MessageBox mb = new MessageBox(shl);
							mb.setMessage("Please insert 8 LETTERS for field Address !");
							mb.setText("Warning...!!!");
							mb.open();
						}
						else {
							WriteToAFile wr1 = new WriteToAFile();
							wr1.open("/tp/address.txt");
							wr1.write(taddress.getText());
							wr1.close();
						}
					}

					conf();
					if ((bstart.getSelection()) && (!ttimes.getText().isEmpty())){
						String scode = DisplayMain.readInputFile("/tp/databits.txt");
						if (!scode.isEmpty()) {
							String sarr[] = scode.split("\n");
							scode="";
							for (int i=0;i<1;i++) {
								scode=sarr[i];
							}
						}
						String fn="";
						if (scode.compareTo("5")==0) fn="/tp/serialita2.conf";
						else fn="/tp/serial.conf";

						test1 = DisplayMain.readInputFile(fn);
						if (!test1.isEmpty()) {
							String sarr1[] = test1.split("-");
							test1="";
							for (int i=0;i<sarr1.length;i++) {
								if (sarr1[i].contains("TEST1")) {
									test1=sarr1[i];break;
								}
							}
							String sarr[] = test1.split("=");
							for (int i=0;i<sarr.length;i++) {
								if (i!=0) test1=sarr[i];
							}
							sarr = test1.split("\n");
							test1="";
							for (int i=0;i<sarr.length;i++) {
								if (!test1.isEmpty()) test1+="\r\n";
								if (i==0) test1+=sarr[i]+" "+org;
								else test1+=sarr[i];
							}
						}
						System.out.println("***TEST1:"+test1);
						test2 = DisplayMain.readInputFile(fn);
						if (!test2.isEmpty()) {
							String sarr1[] = test2.split("-");
							test2="";
							for (int i=0;i<sarr1.length;i++) {
								if (sarr1[i].contains("TEST2")) {
									test2=sarr1[i];break;
								}
							}
							String sarr[] = test2.split("=");
							for (int i=0;i<sarr.length;i++) {
								if (i!=0) test2=sarr[i];
							}
							sarr = test2.split("\n");
							test2="";
							for (int i=0;i<sarr.length;i++) {
								if (!test2.isEmpty()) test2+="\r\n";
								if (i==0) test2+=sarr[i]+" "+org;
								else test2+=sarr[i];
							}
						}
						System.out.println("***TEST2:"+test2);
						test3 = DisplayMain.readInputFile(fn);
						if (!test3.isEmpty()) {
							String sarr1[] = test3.split("-");
							test3="";
							for (int i=0;i<sarr1.length;i++) {
								if (sarr1[i].contains("TEST3")) {
									test3=sarr1[i];break;
								}
							}
							String sarr[] = test3.split("=");
							for (int i=0;i<sarr.length;i++) {
								if (i!=0) test3=sarr[i];
							}
							sarr = test3.split("\n");
							test3="";
							for (int i=0;i<sarr.length;i++) {
								if (!test3.isEmpty()) test3+="\r\n";
								if (i==0) test3+=sarr[i]+" "+org+" "+org+" "+org;
								else test3+=sarr[i];
							}
						}
						System.out.println("***TEST3:"+test3);
						smsg="";
						if (bq1.getSelection()) smsg=test1;
						else if (bq2.getSelection()) smsg=test2;
						else if (bq3.getSelection()) smsg=test3;
						if (!smsg.isEmpty()) {
							System.out.println("sent serial...\n");
							count=Integer.parseInt(ttimes.getText());
							timeoutext.g_start=true;
						}
					}
				}
			}
		});
		 
//		  */
		bcancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shl.close();
			}
		});

		torg.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296)) && (!torg.getText().isEmpty()) ) {
					if (torg.getText().length()!=8) {
						MessageBox mb = new MessageBox(shl);
						mb.setMessage("Please insert 8 LETTERS for field Origin !");
						mb.setText("Warning...!!!");
						mb.open();
					}
					else {
						WriteToAFile wr = new WriteToAFile();
						wr.open("/tp/originator.txt");
						wr.write(torg.getText());
						wr.close();
					}
      			}
			}
		});

		svctraf();
		
//		Composite comp4 = new Composite(shl, SWT.NONE); sh.composeStyle(comp4, 1, SWT.RIGHT, SWT.CENTER);
//		lcounter = new Label(comp4, SWT.RIGHT); lcounter.setText("");
		
		outbox();
		
		sh.shellStyle(shl, "Test Message & SVC TRAF");
	}
	

	void svctraf() {
		Group grp = new Group(shl, SWT.BORDER); sh.groupStyle(grp, 1, "SVC TRAF");
		Composite comp1 = new Composite(grp, SWT.NONE); sh.compositeStyle(comp1, 2, SWT.LEFT, SWT.CENTER);
		Label label = new Label(comp1, SWT.NONE); 
//		sh.labelStyle(label, "Address : ", 90, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		sh.labelStyle1(label, "Address : ", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		taddress = new Text(comp1, SWT.BORDER); sh.textStyle(taddress, 120, 8, SWT.LEFT, SWT.CENTER, sh.letter, "", true);
		Composite comp2 = new Composite(grp, SWT.NONE); sh.compositeStyle(comp2, 3, SWT.LEFT, SWT.CENTER);
		tsvc = new Text(comp2, SWT.BORDER); sh.textStyle(tsvc, 215, 100, SWT.LEFT, SWT.CENTER, sh.upper, "", false);
		Button bsend = new Button(comp2,SWT.PUSH); 
//		sh.buttonStyle(bsend, "&Send", "", bs.widthBtn, bs.heightBtn, Colors.DarkGrey);
		sh.buttonStyle(bsend,"&Send","",bs.widthBtn,Colors.DarkGrey,SWT.CENTER,SWT.CENTER,null);//Images.img_clear16);
		Composite comp3 = new Composite(comp2, SWT.NONE); sh.compositeStyle(comp3, 1, SWT.LEFT, SWT.CENTER);
		lcounter = new Label(comp3, SWT.RIGHT); //lcounter.setText("123");
		sh.labelStyle(lcounter, "", 150, SWT.RIGHT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		
		taddress.setText(address);
		taddress.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296)) && (!taddress.getText().isEmpty()) ) {
					if (taddress.getText().length()!=8) {
						MessageBox mb = new MessageBox(shl);
						mb.setMessage("Please insert 8 LETTERS for field Address !");
						mb.setText("Warning...!!!");
						mb.open();
					}
					else {
						WriteToAFile wr = new WriteToAFile();
						wr.open("/tp/address.txt");
						wr.write(taddress.getText());
						wr.close();
					}
      			}
			}
		});

		tsvc.setText(s_message.substring(1, s_message.length()));
		bsend.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (!tsvc.getText().isEmpty()) {
					System.out.println("sent serial...\n");
					count=1;
					String filingtime="";
					DateUtils du = new DateUtils();
					filingtime=du.now();
					filingtime=filingtime.substring(8, 16).replace(" ", "").replace(":", "");
					smsg="FF"+" "+taddress.getText()+"\r\n";
					smsg+=filingtime+" "+torg.getText()+"\r\n";
					smsg+=tsvc.getText();
					testmsg.tout.setText(smsg);

					String smsg1="FF"+" "+taddress.getText()+"\r\n";
					smsg1+=filingtime+" "+torg.getText()+"\r\n";
					smsg1+=s_message;
					//senddata("100",smsg1,smsg1.length());//102
					//timeoutext.g_start=true;
					
					//insert db
//					jdbc.connect("INSERT INTO air_message (priority,destination1,filing_time,originator,tgl,tbl_name,msgall) VALUES " +
//							"('"+"FF"+"','"+taddress.getText()+"','"+filingtime+"','"+torg.getText()+"','"+du.now()+"','"+"air_message"+"','"+
//							tsvc.getText()+"')");
					
					ss.sPriority="FF";
					ss.sDest1=taddress.getText();
					ss.sFiling=filingtime;
					ss.sOriginator=torg.getText();
					ss.sTblName="air_message";
					ss.sFree=tsvc.getText();
					ss.msgBody=ss.sFree;
					ss.sBell="0";
					ss.s3a="";
					
					ss.setInsertATS(ss.sPriority,ss.sDest1,ss.sDest2,ss.sDest3,ss.sDest4,ss.sDest5,ss.sDest6,ss.sDest7,ss.sDest8,ss.sDest9,ss.sDest10,ss.sDest11,ss.sDest12,ss.sDest13,ss.sDest14,ss.sDest15,ss.sDest16,ss.sDest17,ss.sDest18,ss.sDest19,ss.sDest20,ss.sDest21,ss.sOriginator,ss.sOriRef,ss.sFiling,ss.sJam,du.now()/*time.tanggal*/,ss.sFiledby,ss.sTblName,"waiting"/*sStatus*/,ss.s3a,ss.s3b,ss.s3c,ss.s5a,ss.s5b,ss.s5c,ss.s7a,ss.s7b,ss.s7c,ss.s8a,ss.s8b,ss.s9a,ss.s9b,ss.s9c,ss.s10a,ss.s10b,ss.s13a,""/*time.s13b*/,ss.s14a,ss.s14b,ss.s14c,ss.s14d,ss.s14e,ss.s15a,ss.s15b,ss.s15c,ss.s16a,""/*time.s16b*/,ss.s16c,ss.s16d,ss.s17a,ss.s17b,ss.s17c,ss.sReg,ss.sDof,ss.s18Baru,""/*time.s19a*/,ss.s19b,ss.s19cUHF,ss.s19cVHF,ss.s19cELT,ss.s19dS,ss.s19dP,ss.s19dD,ss.s19dM,ss.s19dJ,ss.s19eJ,ss.s19eL,ss.s19eF,ss.s19eU,ss.s19eV,ss.s19fD,ss.s19fNum,ss.s19fCap,ss.s19fCov,ss.s19fCol,ss.s19g,ss.s19h,ss.s19Rmk,ss.s19i,ss.s20,ss.s21,ss.s22,ss.sSpace,ss.sTrkName,ss.sTrkId,ss.sTrkDate,ss.sTrkStatus,ss.sTrkAct,ss.sTrkExp,ss.sTrkWay,ss.sTrkLev,ss.sTrkRts,ss.sTrkRmk,ss.sFree,ss.msgBody,ss.sBell,ss.sNote);
					ss.setInsertCheck(jdbc.getKey(), ss.sTblName, ss.sFiling, ss.sFiling, du.now(), "", "", "", ss.sOriginator, ss.sFree, "", "", ss.sPriority, ss.sNoteChk);
					
				}
			}
		});

	}
	
	void outbox() {
		Group grp = new Group(shl, SWT.BORDER); sh.groupStyle(grp, 1, "Outbox");
		tout = new Text(grp, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		sh.textAreaStyle(tout, 480, 250, SWT.LEFT, SWT.TOP, "", "");
	}

	public void open() {
		shl.open();
	}
	
	void conf() {
		test1 = "";test2="";test3="";org="";address="";s_message="";
		org = DisplayMain.readInputFile("/tp/originator.txt");
		if (!org.isEmpty()) {
			String sarr[] = org.split("\n");
			org="";
			for (int i=0;i<sarr.length;i++) {
				if (!org.isEmpty()) org+="\n";
				org+=sarr[i];
			}
		}
		address = DisplayMain.readInputFile("/tp/address.txt");
		if (!address.isEmpty()) {
			String sarr[] = address.split("\n");
			address="";
			for (int i=0;i<sarr.length;i++) {
				if (!address.isEmpty()) address+="\n";
				address+=sarr[i];
			}
		}
		s_message = DisplayMain.readInputFile("/tp/message.txt");
		if (!s_message.isEmpty()) {
			String sarr[] = s_message.split("\n");
			s_message="";
			for (int i=0;i<sarr.length;i++) {
				if (!s_message.isEmpty()) s_message+="\n";
				s_message+=sarr[i];
			}
		}
//		test1 = DisplayMain.readInputFile("/tp/serial.conf");
//		if (!test1.isEmpty()) {
//			String sarr1[] = test1.split("-");
//			test1="";
//			for (int i=0;i<sarr1.length;i++) {
//				if (sarr1[i].contains("TEST1")) {
//					test1=sarr1[i];break;
//				}
//			}
//			String sarr[] = test1.split("=");
//			for (int i=0;i<sarr.length;i++) {
//				if (i!=0) test1=sarr[i];
//			}
//			sarr = test1.split("\n");
//			test1="";
//			for (int i=0;i<sarr.length;i++) {
//				if (!test1.isEmpty()) test1+="\r\n";
//				if (i==0) test1+=sarr[i]+" "+org;
//				else test1+=sarr[i];
//			}
//		}
//		System.out.println("***TEST1:"+test1);
//		test2 = DisplayMain.readInputFile("/tp/serial.conf");
//		if (!test2.isEmpty()) {
//			String sarr1[] = test2.split("-");
//			test2="";
//			for (int i=0;i<sarr1.length;i++) {
//				if (sarr1[i].contains("TEST2")) {
//					test2=sarr1[i];break;
//				}
//			}
//			String sarr[] = test2.split("=");
//			for (int i=0;i<sarr.length;i++) {
//				if (i!=0) test2=sarr[i];
//			}
//			sarr = test2.split("\n");
//			test2="";
//			for (int i=0;i<sarr.length;i++) {
//				if (!test2.isEmpty()) test2+="\r\n";
//				if (i==0) test2+=sarr[i]+" "+org;
//				else test2+=sarr[i];
//			}
//		}
//		System.out.println("***TEST2:"+test2);
//		test3 = DisplayMain.readInputFile("/tp/serial.conf");
//		if (!test3.isEmpty()) {
//			String sarr1[] = test3.split("-");
//			test3="";
//			for (int i=0;i<sarr1.length;i++) {
//				if (sarr1[i].contains("TEST3")) {
//					test3=sarr1[i];break;
//				}
//			}
//			String sarr[] = test3.split("=");
//			for (int i=0;i<sarr.length;i++) {
//				if (i!=0) test3=sarr[i];
//			}
//			sarr = test3.split("\n");
//			test3="";
//			for (int i=0;i<sarr.length;i++) {
//				if (!test3.isEmpty()) test3+="\r\n";
//				if (i==0) test3+=sarr[i]+" "+org+" "+org+" "+org;
//				else test3+=sarr[i];
//			}
//		}
//		System.out.println("***TEST3:"+test3);
	}
	
//    public void senddata(String sPort,String sData,int length) {
//  	  try {
//  		  String sIPadrp="127.0.0.1";
//  		  DatagramSocket s = new DatagramSocket();
//            byte[] line = new byte[10000];
//			  System.out.println("To   : "+sIPadrp+" Data:"+sData);
//            InetAddress dest = InetAddress.getByName(sIPadrp);
//            line = sData.getBytes();
//            int len = line.length;
//            DatagramPacket pkt = new DatagramPacket(line, len, dest, Integer.parseInt(sPort));
//			  System.out.println("Send : "+len+" bytes ");
//            s.send(pkt);
//            s.close();
//        } catch (Exception err) {
//            System.out.println("err:" + err);
//        }
//	}

}


//Font fonttext = new Font(shdisp.getDisplay(),"Arial",12,SWT.BOLD);
//Font fonttext1 = new Font(shdisp.getDisplay(),"Arial",15,SWT.NORMAL);
//boolean cknumeric(char s){
//	boolean b=false;
//	if ((s>='0') && (s<='9')) b=true;
//	System.out.println("b:"+b);
//	return b;
//}
//
//void numeric(Text text) {
//	text.addListener(SWT.Verify, new Listener() {
//		public void handleEvent(Event e) {
//			String string = e.text;
//			char[] chars = new char[string.length()];
//			string.getChars(0, chars.length, chars, 0);
//			for (int i = 0; i < chars.length; i++) {
//				if (!('0' <= chars[i] && chars[i] <= '9')) {
//					e.doit = false;
//					return;
//				}
//			}
//		}
//	});
//}
//
//void letter(Text text) {
//	text.addListener(SWT.Verify, new Listener() {
//		public void handleEvent(Event e) {
//			String string = e.text;
//			char[] chars2 = new char[string.length()];
//			string.getChars(0, chars2.length, chars2, 0);
//			for (int i = 0; i < chars2.length; i++) {
//				e.text = e.text.toUpperCase();
//				if (!('A' <= chars2[i] && chars2[i] <= 'Z')
//						&& !('a' <= chars2[i] && chars2[i] <= 'z')) {
//					e.doit = false;
//					return;
//				}
//			}
//		}
//	});
//}
//
//public void upper(Text text) {
//	text.addVerifyListener(new VerifyListener() {
//		public void verifyText(VerifyEvent e) {
//			e.text = e.text.toUpperCase();
//		}
//	});
//}