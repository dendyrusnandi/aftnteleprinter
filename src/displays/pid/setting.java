package displays.pid;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import setting.ButtonsSetting;
import setting.Colors;
import setting.Images;
import setting.Shorten;
import actions.RefreshTable;
import actions.TriggerAction;
import displays.DialogFactory;
import displays.MainForm;


public class setting {
	
	Shorten sh = new Shorten();
	ButtonsSetting bs = new ButtonsSetting();
	
	
	Shell shl;
	Label lbl;
	Text tbaud,tdigit,tcid,torg,tprevst;
	Button bON,bOFF,bONch,bOFFch,bEnable,bDisable,bITA2,bIA5,b3,b4,bBaud;
	static Text tseq;
	String sdevi="",sbaud="",sdigit="",stransid="",stseq="",sorg="",scode="",sprevst="",ssvcmsggen="",schnl="",ssvc="",sarof="";
	public static String sBaud[]={"50","75","150","300","600","1200","2400","4800","9600","19200"};
	public static String sPriority[]={"DD","FF","GG","KK","SS"};
	String scode1="",baud1="";
	int iSpace=10;
	String scomm[]={"RS232","UDP"};

	
	String getText() {
		return "Communication Setup";
	}
	
	public setting(Shell shell) {
		conf();
		shl=shell;
		Group group = new Group(shl, SWT.BORDER); sh.groupStyle(group, 1, "");
		Composite grp = new Composite(group, SWT.NONE); sh.compositeStyle(grp, 9, SWT.LEFT, SWT.CENTER);
		
//		lbl = new Label(grp, SWT.NONE); sh.labelStyle1(lbl, "Baudrate", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
//		lbl = new Label(grp, SWT.NONE); sh.labelStyle(lbl, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle1(lbl, "Code", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle(lbl, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle1(lbl, "Digit seq.", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle(lbl, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle1(lbl, "CID", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle(lbl, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle1(lbl, "Tseq", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle(lbl, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); //sh.labelStyle1(lbl, "Baudrate", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		//--------
//		Composite compBaud = new Composite(grp, SWT.NONE); sh.compositeStyle(compBaud, 2, SWT.LEFT, SWT.CENTER);
//		lbl = new Label(grp, SWT.NONE);
		Composite compCode = new Composite(grp, SWT.NONE); sh.compositeStyle(compCode, 2, SWT.LEFT, SWT.CENTER);
		lbl = new Label(grp, SWT.NONE);
		Composite compDigit = new Composite(grp, SWT.NONE); sh.compositeStyle(compDigit, 2, SWT.LEFT, SWT.CENTER);
		lbl = new Label(grp, SWT.NONE);
		tcid = new Text(grp,SWT.BORDER); sh.textStyle(tcid, 40, 3, SWT.LEFT, SWT.CENTER, sh.letter, "CID", true);
		lbl = new Label(grp, SWT.NONE);
		tseq = new Text(grp,SWT.BORDER); sh.textStyle(tseq, 40, 4, SWT.LEFT, SWT.CENTER, sh.numeric, "Tseq", true);
		lbl = new Label(grp, SWT.NONE);
		Composite compBaud = new Composite(grp, SWT.NONE); sh.compositeStyle(compBaud, 2, SWT.LEFT, SWT.CENTER);
		//--------
		tbaud = new Text(compBaud, SWT.BORDER); sh.textStyle(tbaud, 40, 6, SWT.LEFT, SWT.CENTER, sh.numeric, "Baudrate", false);
		bBaud = new Button(compBaud, SWT.PUSH); sh.buttonStyle(bBaud,"","Select Baudrate",bs.widthNavBtn,Colors.DarkGrey,SWT.CENTER,SWT.CENTER,Images.img_LIST);
		bITA2 = new Button(compCode, SWT.RADIO); 
		bIA5 = new Button(compCode, SWT.RADIO); 
		b3 = new Button(compDigit, SWT.RADIO); 
		b4 = new Button(compDigit, SWT.RADIO); 
		
		tbaud.setVisible(false);
		bBaud.setVisible(false);
		
		//--------
//		lbl = new Label(grp, SWT.NONE); sh.labelStyle1(lbl, "Originator", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
//		lbl = new Label(grp, SWT.NONE); sh.labelStyle(lbl, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle1(lbl, "Svc msg generation", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle(lbl, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle1(lbl, "Prev.st", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle(lbl, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle1(lbl, "Channel check", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle(lbl, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle1(lbl, "Automatic Repeat", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle(lbl, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle1(lbl, "Originator", SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		
		Composite compSvc = new Composite(grp, SWT.NONE); sh.compositeStyle(compSvc, 2, SWT.LEFT, SWT.CENTER);
		lbl = new Label(grp, SWT.NONE); 
		tprevst = new Text(grp,SWT.BORDER); sh.textStyle(tprevst, 80, 8, SWT.LEFT, SWT.CENTER, sh.letter, "Prev.st", true);
		lbl = new Label(grp, SWT.NONE); sh.labelStyle(lbl, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.BOLD, Colors.NORMAL);
		Composite compCh = new Composite(grp, SWT.NONE); sh.compositeStyle(compCh, 2, SWT.LEFT, SWT.CENTER);
		lbl = new Label(grp, SWT.NONE); 
		Composite compAROF = new Composite(grp, SWT.NONE); sh.compositeStyle(compAROF, 2, SWT.LEFT, SWT.CENTER);
		lbl = new Label(grp, SWT.NONE); 
		torg = new Text(grp,SWT.BORDER); sh.textStyle(torg, 80, 8, SWT.LEFT, SWT.CENTER, sh.letter, "Originator", true);
		
		bON = new Button(compSvc, SWT.RADIO); 
		bOFF = new Button(compSvc, SWT.RADIO); 
		bONch = new Button(compCh, SWT.RADIO); 
		bOFFch = new Button(compCh, SWT.RADIO); 
		bEnable = new Button(compAROF, SWT.RADIO); 
		bDisable = new Button(compAROF, SWT.RADIO); 
		
//		sh.buttonRCStyle(bITA2, "ITA 2", "ITA 2", 60);
//		sh.buttonRCStyle(bIA5, "IA 5", "IA 5", 60);
//		sh.buttonRCStyle(b3, "3", "3", 60);
//		sh.buttonRCStyle(b4, "4", "4", 60);
//		sh.buttonRCStyle(bON, "ON", "Service msg generation Activate", 60);
//		sh.buttonRCStyle(bOFF, "OFF", "Service msg generation Deactivate", 60);
//		sh.buttonRCStyle(bONch, "ON", "Channel check Activate", 60);
//		sh.buttonRCStyle(bOFFch, "OFF", "Channel check Deactivate", 60);
//		sh.buttonRCStyle(bEnable, "Enabled", "Enabled AROF", 80);
//		sh.buttonRCStyle(bDisable, "Disabled", "Disabled AROF", 80);
		
		sh.RadioCheckStyle(bITA2, "ITA 2", "ITA 2");
		sh.RadioCheckStyle(bIA5, "IA 5", "IA 5");
		sh.RadioCheckStyle(b3, "3", "3");
		sh.RadioCheckStyle(b4, "4", "4");
		sh.RadioCheckStyle(bON, "ON", "Service msg generation Activate");
		sh.RadioCheckStyle(bOFF, "OFF", "Service msg generation Deactivate");
		sh.RadioCheckStyle(bONch, "ON", "Channel check Activate");
		sh.RadioCheckStyle(bOFFch, "OFF", "Channel check Deactivate");
		sh.RadioCheckStyle(bEnable, "Enabled", "Enabled AROF");
		sh.RadioCheckStyle(bDisable, "Disabled", "Disabled AROF");
		
		Composite comp = new Composite(shl, SWT.NONE); sh.compositeStyle(comp, 1, SWT.CENTER, SWT.CENTER);
		Button bok = new Button(comp, SWT.PUSH); sh.buttonStyle(bok,"&OK","Click OK to apply",bs.widthBtn,Colors.DarkGrey,SWT.CENTER,SWT.CENTER,null);//Images.img_clear16);

		bBaud.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RefreshTable.refreshBaudrate(tbaud);
			}
		});
		
		tbaud.setText(sbaud);
		
		if (sdigit.compareTo("3")==0) { b3.setSelection(true); b4.setSelection(false); } 
		else if (sdigit.compareTo("4")==0) { b4.setSelection(true); b3.setSelection(false); }

		tcid.setText(stransid);
	
		tseq.setText(stseq);
		if (sdigit.compareTo("3")==0) tseq.setTextLimit(3);
		else if (sdigit.compareTo("4")==0) tseq.setTextLimit(4);
		
		torg.setText(sorg);

		if (scode.compareTo("5")==0) { bITA2.setSelection(true); bIA5.setSelection(false); }
		else if (scode.compareTo("8")==0) { bITA2.setSelection(false); bIA5.setSelection(true); }
		
		tprevst.setText(sprevst);
		
		if (ssvcmsggen.compareTo("1")==0) { bON.setSelection(true); bOFF.setSelection(false); tprevst.setEnabled(true); }
		else if (ssvcmsggen.compareTo("0")==0) { bON.setSelection(false); bOFF.setSelection(true); tprevst.setEnabled(false); }
		
		bON.addSelectionListener(new SelectionAdapter() { public void widgetSelected(SelectionEvent e) { tprevst.setEnabled(true); } });
		bOFF.addSelectionListener(new SelectionAdapter() { public void widgetSelected(SelectionEvent e) { tprevst.setEnabled(false); } });
		
		b3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (tseq.getText().length()==4) {
					System.out.println("change to 3");
					tseq.setText(tseq.getText().substring(1,tseq.getText().length())); 
				}
				tseq.setTextLimit(3);
			}
		});

		b4.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				tseq.setTextLimit(4);
				String s = tseq.getText();
				if (s.length()==3) {
					s="0"+s;
					tseq.setText(s);
//					System.out.println("s:"+s);
				}
			}
		});
		
		if (schnl.compareTo("1")==0) { bONch.setSelection(true); bOFFch.setSelection(false); }
		else if (schnl.compareTo("0")==0) { bONch.setSelection(false); bOFFch.setSelection(true); }
		
		if (sarof.compareTo("1")==0) { bEnable.setSelection(true); bDisable.setSelection(false); }
		else if (sarof.compareTo("0")==0) { bEnable.setSelection(false); bDisable.setSelection(true); }
		
		bok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//---------- validasi
				if (tbaud.getText().isEmpty()) {
					DialogFactory.openInfoDialog(shl, getText(), "The value in field Baudrate is required."); tbaud.setFocus(); }
				else if (bITA2.getSelection()==false && bIA5.getSelection()==false) {
					DialogFactory.openInfoDialog(shl, getText(), "The value in field Code is required."); bITA2.setFocus(); }
				else if (b3.getSelection()==false && b4.getSelection()==false) {
					DialogFactory.openInfoDialog(shl, getText(), "The value in field Digit seq. is required."); b3.setFocus(); }
				else if (tcid.getText().isEmpty()) {
					DialogFactory.openInfoDialog(shl, getText(), "The value in field CID is required."); tcid.setFocus(); }
				else if (tseq.getText().isEmpty()) {
					DialogFactory.openInfoDialog(shl, getText(), "The value in field Tseq is required."); tseq.setFocus(); }
				else if (b3.getSelection()==true && tseq.getText().length()<3) {
					DialogFactory.openInfoDialog(shl, getText(), "Please insert 3 NUMERICS for field Tseq !"); tseq.setFocus(); }
				else if (b4.getSelection()==true && tseq.getText().length()<4) {
					DialogFactory.openInfoDialog(shl, getText(), "Please insert 4 NUMERICS for field Tseq !"); tseq.setFocus(); }
				else if (bON.getSelection()==false && bOFF.getSelection()==false) {
					DialogFactory.openInfoDialog(shl, getText(), "The value in field Svc msg generation is required."); bON.setFocus(); }
				else if (bON.getSelection()==true && tprevst.getText().isEmpty()) {
					DialogFactory.openInfoDialog(shl, getText(), "The value in field Prev.st is required."); tprevst.setFocus(); }
				else if (bON.getSelection()==true && !tprevst.getText().isEmpty() && tprevst.getText().length()<8) {
					DialogFactory.openInfoDialog(shl, getText(), "Please insert 8 LETTERS for field Prev.st !"); tprevst.setFocus(); }
				else if (torg.getText().isEmpty()) {
					DialogFactory.openInfoDialog(shl, getText(), "The value in field Originator is required."); torg.setFocus(); }
				else if (!torg.getText().isEmpty() && torg.getText().length()<8) {
					DialogFactory.openInfoDialog(shl, getText(), "Please insert 8 LETTERS for field Originator !"); torg.setFocus(); }
				else {
					//---------- BAUDRATE
					if ((!tbaud.getText().isEmpty())&&(tbaud.getText().compareTo(sbaud)!=0)) {
						WriteToAFile wr = new WriteToAFile();
						wr.open("/tp/baudrate.txt");
						wr.write(tbaud.getText());
						wr.close();
						TriggerAction.trigger("baud,"+tbaud.getText(), 100);
						sbaud=tbaud.getText();
					}
					
					//---------- DIGIT SEQ.
					boolean flg=false;
					String cdigit="";
					if (b3.getSelection()==true && b4.getSelection()==false) cdigit="3";
					else if (b4.getSelection()==true && b3.getSelection()==false) cdigit="4";
					
					if (!cdigit.isEmpty()) {
						if ((cdigit.compareTo("3")==0) || (cdigit.compareTo("4")==0)) {
							WriteToAFile wr = new WriteToAFile();
							wr.open("/tp/digit.txt");
							wr.write(cdigit);
							wr.close();
							TriggerAction.trigger("digit,"+cdigit, 100);
							flg=true;
						}
						else {
							DialogFactory.openInfoDialog(shl, getText(), "Please insert 3 or 4 NUMERICS for field Digit seq. !"); 
//							MessageBox mb = new MessageBox(shl);
//							mb.setMessage("Please insert 3 or 4 NUMERICS for field Digit seq. !!");
//							mb.setText("Warning...!!!");
//							mb.open();
						}
					}
	
					if (!tseq.getText().isEmpty()) {
						boolean flg1=false;
						
						if (cdigit.compareTo("3")==0) {
							if (tseq.getText().length()==4) {
								tseq.setText(tseq.getText().substring(1,tseq.getText().length()));flg1=true;}
							tseq.setTextLimit(3);
						}
						else if (cdigit.compareTo("4")==0) {
							tseq.setTextLimit(4);
							String s = tseq.getText();
							if (s.length()==3) {
								s="0"+s;
								tseq.setText(s);flg1=true;
//								System.out.println("s:"+s);
							}
						}
						
						WriteToAFile wr = new WriteToAFile();
						wr.open("/tp/tseq.txt");
						wr.write(tseq.getText());
						wr.close();
						TriggerAction.trigger("Tseq,"+tseq.getText(), 101);
					}
	
					//---------- CID
					if (!tcid.getText().isEmpty()) {
						WriteToAFile wr = new WriteToAFile();
						wr.open("/tp/transid.txt");
						wr.write(tcid.getText());
						wr.close();
					}
	
					//---------- CODE
					if (scode.compareTo("5")==0) scode1="ITA 2";
					else if (scode.compareTo("8")==0) scode1="IA 5";
					
					String ccode="";
					if (bITA2.getSelection()==true && bIA5.getSelection()==false) ccode="ITA 2";
					else if (bIA5.getSelection()==true && bITA2.getSelection()==false) ccode="IA 5";
					
					if ((!ccode.isEmpty())&&(ccode.compareTo(scode1)!=0)) {
						String dtc="";
						if (ccode.compareTo("IA 5")==0) {dtc="8";scode1="IA 5";}
						else if (ccode.compareTo("ITA 2")==0) {dtc="5";scode1="ITA 2";}
						WriteToAFile wr = new WriteToAFile();
						wr.open("/tp/databits.txt");
						wr.write(dtc);
						wr.close();
						TriggerAction.trigger("ia5ita2,"+dtc, 100);
					}
					
					//---------- ORIGIN
					if (!torg.getText().isEmpty()) {
						WriteToAFile wr = new WriteToAFile();
						wr.open("/tp/originator.txt");
						wr.write(torg.getText());
						wr.close();
					}
					
					//---------- PREV.ST
					if (!tprevst.getText().isEmpty()) {
						WriteToAFile wr = new WriteToAFile();
						wr.open("/tp/prevst.txt");
						wr.write(tprevst.getText());
						wr.close();
					}
					
					//---------- SVC MSG GENERATION
					if (bON.getSelection()==true && bOFF.getSelection()==false) ssvcmsggen="1";
					else if (bOFF.getSelection()==true && bON.getSelection()==false) ssvcmsggen="0";
					if (!ssvcmsggen.isEmpty()) {
						WriteToAFile wr = new WriteToAFile();
						wr.open("/tp/svcmsg.txt");
						wr.write(ssvcmsggen);
						wr.close();
					}
					
					//---------- CHANNEL CHECK
					if (bONch.getSelection()==true && bOFFch.getSelection()==false) schnl="1";
					else if (bOFFch.getSelection()==true && bONch.getSelection()==false) schnl="0";
					if (!schnl.isEmpty()) {
						WriteToAFile wr = new WriteToAFile();
						wr.open("/tp/chcheck.txt");
						wr.write(schnl);
						wr.close();
					}
					
					//---------- AROF
					if (bEnable.getSelection()==true && bDisable.getSelection()==false) sarof="1";
					else if (bDisable.getSelection()==true && bEnable.getSelection()==false) sarof="0";
					if (!sarof.isEmpty()) {
						WriteToAFile wr = new WriteToAFile();
						wr.open("/tp/arof.txt");
						wr.write(sarof);
						wr.close();
					}
	
					if (!shdisp.getDisplay().isDisposed()) {
		    			shdisp.getDisplay().syncExec(new Runnable() {
		    				public void run() {
		    					conf();
		    					
		    					if (ssvcmsggen.compareTo("1")==0) ssvcmsggen="ON";
		    					else if (ssvcmsggen.compareTo("0")==0) ssvcmsggen="OFF";
		    					
								File file=new File("/tp/nis.txt");
								String s2="";
								try{
									s2 = readFileAsAString(file);
								} catch (IOException er) { System.err.println("nis.txt"); };
								System.out.println("s2"+s2);
								if (s2.compareTo(scomm[0])==0) MainForm.clBaudrate.setText("Baudrate : "+sbaud);
		    					MainForm.clDigit.setText("Digit seq : "+sdigit);
		    					MainForm.clCID.setText("CID : "+stransid);
		    					//MainForm.clCode.setText("Code : "+scode1);
		    					//MainForm.clSvcMsgGen.setText("Svc msg gen : "+ssvcmsggen);
		    				}
		    			});
		    		}
					
					if (flg) shl.close();
				}
			}
		});

		sh.shellStyle(shl, getText());
	}
	
	public void open() {
		shl.open();
	}
	
	void conf() {
		sbaud="";sdigit="";stransid="";stseq="";sorg="";scode="";sprevst="";ssvcmsggen="";schnl="";sarof="";
		
		sbaud = DisplayMain.readInputFile("/tp/baudrate.txt");
		if (!sbaud.isEmpty()) {
			String sarr[] = sbaud.split("\n");
			sbaud="";
			for (int i=0;i<1;i++) {
				sbaud=sarr[i];
			}
		}
		sdigit = DisplayMain.readInputFile("/tp/digit.txt");
		if (!sdigit.isEmpty()) {
			String sarr[] = sdigit.split("\n");
			sdigit="";
			for (int i=0;i<1;i++) {
				sdigit=sarr[i];
			}
		}
		stransid = DisplayMain.readInputFile("/tp/transid.txt");
		if (!stransid.isEmpty()) {
			String sarr[] = stransid.split("\n");
			stransid="";
			for (int i=0;i<1;i++) {
				stransid=sarr[i];
			}
		}
		stseq = DisplayMain.readInputFile("/tp/tseq.txt");
		if (!stseq.isEmpty()) {
			String sarr[] = stseq.split("\n");
			stseq="";
			for (int i=0;i<1;i++) {
				stseq=sarr[i];
			}
		}
		sorg = DisplayMain.readInputFile("/tp/originator.txt");
		if (!sorg.isEmpty()) {
			String sarr[] = sorg.split("\n");
			sorg="";
			for (int i=0;i<1;i++) {
				sorg=sarr[i];
			}
		}
		scode = DisplayMain.readInputFile("/tp/databits.txt");
		if (!scode.isEmpty()) {
			String sarr[] = scode.split("\n");
			scode="";
			for (int i=0;i<1;i++) {
				scode=sarr[i];
			}
		}
		sprevst = DisplayMain.readInputFile("/tp/prevst.txt");
		if (!sprevst.isEmpty()) {
			String sarr[] = sprevst.split("\n");
			sprevst="";
			for (int i=0;i<1;i++) {
				sprevst=sarr[i];
			}
		}
		ssvcmsggen = DisplayMain.readInputFile("/tp/svcmsg.txt");
		if (!ssvcmsggen.isEmpty()) {
			String sarr[] = ssvcmsggen.split("\n");
			ssvcmsggen="";
			for (int i=0;i<1;i++) {
				ssvcmsggen=sarr[i];
			}
		}
		schnl = DisplayMain.readInputFile("/tp/chcheck.txt");
		if (!schnl.isEmpty()) {
			String sarr[] = schnl.split("\n");
			schnl="";
			for (int i=0;i<1;i++) {
				schnl=sarr[i];
			}
		}
		sarof = DisplayMain.readInputFile("/tp/arof.txt");
		if (!sarof.isEmpty()) {
			String sarr[] = sarof.split("\n");
			sarof="";
			for (int i=0;i<1;i++) {
				sarof=sarr[i];
			}
		}
	}
	public String readFileAsAString(File file) throws IOException {
		return new String(getBytesFromFile(file));
	}
	
	public byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		long length=file.length();
		if (length>Integer.MAX_VALUE)
			throw new IllegalArgumentException("File is too large");
		byte[] bytes = new byte[(int)length];
		int offset=0;
		int numread=0;
		while(offset<bytes.length && (numread=is.read(bytes,offset,bytes.length-offset))>=0){
			offset+=numread;
		}
		if (offset<bytes.length) {
			throw new IOException("Could not completely read file"+file.getName());
		}
		is.close();
		return bytes;
		
	}

}

