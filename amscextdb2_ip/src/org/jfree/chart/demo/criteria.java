//update: 231211
package org.jfree.chart.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jfree.text.TextUtilities;
import org.jfree.ui.ApplicationFrame;
import org.jfree.util.Log;
import org.jfree.util.PrintStreamLogTarget;

import displays.ConnectTo;

import ti_iais.ListBox;

//import com.isode.xuxa.desktop.actions.ListBox;


public class criteria {
	static String sKey="*";
	String string;
    Connection conn;
    Statement stmt;
	Display display;
	Shell shell,sh;
	String[] Month = new String[]{"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
	String[] TYPE;
	String[] TYPE1 = new String[]{"ALR","RCF","FPL","DLA","CHG","CNL","DEP","ARR","CDN","CPL","EST","ACP","LAM","RQP","RQS","SPL"};
	String[] TYPE2 = new String[]{"ALR","RCF","FPL","DLA","CHG","CNL","DEP","ARR","CDN","CPL","EST","ACP","LAM","RQP","RQS","SPL","ABI","MAC","PAC","REJ","TOC","AOC","EMG","MIS","TDM","LRM","TRU","ADS","ASM","FAN","FCN"};
	String[] Typechart = new String[] {"Bar Chart","Pie Chart"};
	String[] Tblchart = new String[] {"ATS","NOTAM","METAR"};
	//String[] Tblchart = new String[] {"ATS"};
	String[] ListXval = new String[4];
	Button C_month,C_typchart;
	Text T_year,T_aircraft,T_time;
	Button B_createChart,B_print,b_fieldx,b_fieldy,b_xremove,b_setkey,b_delkey;
	static String g_sFile;
	ApplicationFrame AF;
	List listField,listX,listY;
	Text T_addV;
	String outTextField,outTextX,outTextY;
	static String sFilename;
	String s_items[]=new String[100];
	FPLchartb fplchart = new FPLchartb();
	NOTAMchart notamchart = new NOTAMchart();
	METEOchart meteochart = new METEOchart();
	Button C_tblchart;
	static String g_sTname;
	Label L_name,L_nametyp;
//	String sFnmfield="/aftn/chart/";
	String sFnmfield="/aed/chart/";
	
	Text T_addVy;
	Button b_yremove;
	Button b_yaddv;
	Combo C_month1;

	int x=0,y=0;
	Shell shelllst,shelllst1,shelllst2,shelllst3;
	boolean flgsh,flgsh1,flgsh2,flgsh3=false;

	public criteria(Display pdisplay, Shell shell){
		this.display = pdisplay;
		this.shell = shell;
		shell.setText("Criteria of Chart Messages");
        connect();
        listBOX();
//		String sST=getdt1("/aftn/stAIDC.txt");
        String sST=getdt1("/aed/stAIDC.txt");
		if (sST.startsWith("1")) {
			TYPE=TYPE2;
		}
		else TYPE=TYPE1;

	}
	
	void printPreview(String s_file) {
    	try {
    		Runtime.getRuntime().exec("/usr/bin/eog "+s_file);
    	}
    	catch (IOException e) {System.err.println("Error: " + e.getMessage());}
    	System.out.println("opening file "+s_file);

	}
	
	public void open(){
		shell.setLocation(0, 55);
		shell.setSize(1050, 550);
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        close();
        if (fplchart.pie!=null) fplchart.pie.rotator.stop();
        if (notamchart.pie!=null) notamchart.pie.rotator.stop();
        if (meteochart.pie!=null) meteochart.pie.rotator.stop();
//        display.dispose();
		
	}
	
	void listBOX(){
		String s_itemsx[]={"","","","",""};
		String s_itemsy[]={"","","","",""};
		//int iLength = getdt(XuxaMain.g_sFname);
		
		int xsh=20,ysh=15;
		int i_widthList=200,i_heightList=300;
		int i_widthBtn=100,i_heightBtn=40;
		int i_hspace=10;
		
		Label L_typchart = new Label(shell, SWT.NONE);
		L_typchart.setLocation(xsh-10, ysh+5);
		L_typchart.setSize(85,i_heightBtn-20);
		L_typchart.setText("Statistic : ");

		C_typchart = new Button(shell,SWT.PUSH);
		C_typchart.setSize(i_widthList+22,i_heightBtn-13);
		C_typchart.setLocation(xsh+100, ysh);
		//C_typchart.setItems(Tblchart);
		C_typchart.setText("Select");
		x=shell.getBounds().x;
		y=shell.getBounds().y;
		shelllst=new Shell();
		C_typchart.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (!shelllst.isDisposed()) shelllst.close();
				if (shelllst.isDisposed()) shelllst=new Shell(SWT.BORDER|SWT.NO_TRIM|SWT.ON_TOP); 
				new ListBox2(shelllst,225,80,Tblchart,C_typchart,x+123,y+36,1);
				flgsh=true;
			}
		});
		shell.addListener(SWT.Move, new Listener(){public void handleEvent(Event event) {
			x=shell.getBounds().x;y=shell.getBounds().y;
			if (!shelllst.isDisposed()) shelllst.setLocation(x+123, y+36);
			if (!shelllst1.isDisposed()) shelllst1.setLocation(x+123, y+66);
			if (!shelllst2.isDisposed()) shelllst2.setLocation(x+484,y+460);
			}
		});
		shell.addListener(SWT.Deactivate, new Listener(){public void handleEvent(Event event) {
			//System.out.println("closed");
			if ((!shelllst.isDisposed()) && (!flgsh)) shelllst.close();flgsh=false;
			if ((!shelllst1.isDisposed()) && (!flgsh1)) shelllst1.close();flgsh1=false;
			if ((!shelllst2.isDisposed()) && (!flgsh2)) shelllst2.close();flgsh2=false;
			shell.setFocus();
			}
		});
//		C_typchart.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				listField.removeAll();
//				listX.removeAll();
//				int length=0;
//				sFilename="";
//				b_setkey.setVisible(true);
//				b_delkey.setVisible(false);
//				if (C_typchart.getText().compareTo("ATS")==0) {
//					g_sTname="air_message";
//					length=getdt(sFnmfield+"fieldATS");
//					sFilename=sFnmfield+"fieldATS";
//					System.out.println("sFilename=" + sFilename);
//					//ListXval[0]="FPL";ListXval[1]="2010";ListXval[2]="JANUARY";
//					g_sFile=sFnmfield+"chartATS";
//				}
//				else if (C_typchart.getText().compareTo("NOTAM")==0) {
//					g_sTname="notam_multi";
//					length=getdt(sFnmfield+"fieldNTM");
//					sFilename=sFnmfield+"fieldNTM";
//					//ListXval[0]="A";ListXval[1]="2010";ListXval[2]="JANUARY";
//					g_sFile=sFnmfield+"chartNTM";
//				}
//				else if (C_typchart.getText().compareTo("METAR")==0) {
//					g_sTname="meteo_metar";
//					length=getdt(sFnmfield+"fieldMET");
//					sFilename=sFnmfield+"fieldMET";
//					//ListXval[0]="WAAA";ListXval[1]="2010";ListXval[2]="JANUARY";
//					g_sFile=sFnmfield+"chartMET";
//				}
//				for (int iCnt=0;iCnt<length;iCnt++) listField.add(s_items[iCnt]);
//				for (int iLoop=0;iLoop<3;iLoop++){
//					String s = gtdt(sFilename,iLoop+1);
//					System.out.println("s:"+s);
//					listX.add(s+":"+ListXval[iLoop]);
//					listX.setItem(iLoop,s+":");//+ListXval[iLoop]);
//				}
//		        System.out.println("sFilename:"+sFilename);
//		    }
//		});

		
		Label L_tblchart = new Label(shell, SWT.NONE);
		L_tblchart.setLocation(xsh-10, ysh+35);
		L_tblchart.setSize(95,i_heightBtn-20);
		L_tblchart.setText("Type of chart : ");

		C_tblchart = new Button(shell,SWT.PUSH);
		C_tblchart.setSize(i_widthList+22,i_heightBtn-13);
		C_tblchart.setLocation(xsh+100, ysh+30);
		C_tblchart.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
		    }
		});
		//C_tblchart.setItems(Typechart);
		C_tblchart.setText("Select");
		shelllst1=new Shell();
		C_tblchart.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (!shelllst1.isDisposed()) shelllst1.close();
				if (shelllst1.isDisposed()) shelllst1=new Shell(SWT.BORDER|SWT.NO_TRIM|SWT.ON_TOP); 
				new ListBox(shelllst1,225,60,Typechart,C_tblchart,x+123,y+66);
				flgsh1=true;
			}
		});

		ysh=100;
		
		listField = createList(shell,s_items,0);
		listField.setSize(i_widthList, i_heightList);
		listField.setLocation(xsh, ysh);
		listField.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		        int[] selections = listField.getSelectionIndices();
		        outTextField = "";
		        for (int loopIndex = 0; loopIndex < selections.length; loopIndex++)
		          outTextField += selections[loopIndex] + "";
		        System.out.println("You selected: " + outTextField);
		        b_fieldx.setEnabled(true);
		        b_fieldy.setEnabled(true);
				if ((outTextField.compareTo("0")==0) || (outTextField.compareTo("1")==0) || (outTextField.compareTo("2")==0)) {
			        b_fieldy.setEnabled(false);
				}
		      }

		      public void widgetDefaultSelected(SelectionEvent event) {
		        int[] selections = listField.getSelectionIndices();
		        String outTextField = "";
		        for (int loopIndex = 0; loopIndex < selections.length; loopIndex++)
		          outTextField += selections[loopIndex] + "";
		        //System.out.println("You selected: " + outTextField+"end");
		        b_fieldx.setEnabled(true);
		      }
		 });
		
		B_createChart = new Button(shell, SWT.PUSH);
		B_createChart.setText("Create chart");
		B_createChart.setSize(120, 35);
		B_createChart.setLocation(xsh-2, ysh+i_heightList+i_hspace+25);
		AF = new ApplicationFrame(" ");
		B_createChart.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean flgc=false,flg2=false;
				
				boolean flgbreak=false;
//				for(int iCnt=0;iCnt<listX.getItemCount();iCnt++) {
//					String a = listX.getItem(iCnt);
//					if ((a.contains("-")) && (a.contains("*"))){
//						flgbreak=true;
//					}
//				}

				int typchart1=1;
		        if (C_tblchart.getText().compareTo(Typechart[1])==0) typchart1=2;
		        	
				if ((listY.getItemCount()>=0) || (typchart1==2)){
			
				if (typchart1==2) listY.removeAll();
				if (!flgbreak)  {
					for(int iCnt=0;iCnt<listX.getItemCount();iCnt++) {
						String a = listX.getItem(iCnt);
						if (a.contains("*")) a=a.substring(1,a.length());
						String s2[] = a.split(":");
						if (s2.length==1) {
							if (find(criteria.sFilename,s2[0]).compareTo("type19b")!=0) {
								flg2=true;break;
							}
						}
					}
					
					//if (!flg2) {
						for(int iCnt=0;iCnt<listX.getItemCount();iCnt++) {
							String s2 = listX.getItem(iCnt);
							if (s2.startsWith(criteria.sKey)) {
								flgc=true;
								break;
							}
						}
		
				        if (flgc) {
					        Log.getInstance().addTarget(new PrintStreamLogTarget());
					        TextUtilities.setUseFontMetricsGetStringBounds(false);
					        int typchart=0;
					        if (C_tblchart.getText().isEmpty()) C_tblchart.setText("Bar Chart"); 
					        if (C_tblchart.getText().compareTo(Typechart[0])==0) { 
					        	System.out.println("type "+Typechart[0]);
					        	typchart=1;
								AF.close(); 
								AF = new ApplicationFrame("Chart");
					        }
					        else if (C_tblchart.getText().compareTo(Typechart[1])==0) { 
								System.out.println("type "+Typechart[1]);
					        	typchart=2;
					        }
					        System.out.println("type of chart:"+C_typchart.getText()+" g_sTname:"+g_sTname+" sFilename:"+sFilename);
					        for (int iCnt=0;iCnt<listX.getItemCount();iCnt++)
					        	System.out.println("dt listX["+iCnt+"]:"+listX.getItem(iCnt));
					        if (g_sTname!=null) {
						        if (g_sTname.compareTo("air_message")==0) 
							        fplchart.FPLcreate(conn,stmt,AF,"Chart",listX,typchart,listY);
						        else if (g_sTname.compareTo("notam_multi")==0) 
							        notamchart.NOTAMcreate(conn,stmt,AF,"Chart",listX,typchart,listY);
						        else if (g_sTname.compareTo("meteo_metar")==0) 
							        meteochart.METEOcreate(conn,stmt,AF,"Chart",listX,typchart);
					        }
				        }
				        else {
				        	MessageBox msgbx = new MessageBox(shell,SWT.ICON_WARNING);
				        	msgbx.setText("Warning");
				        	msgbx.setMessage("At least one key must be set !!!");
				        	msgbx.open();
				        }
//					}
//					else {
//			        	MessageBox msgbx = new MessageBox(shell,SWT.ICON_ERROR);
//			        	msgbx.setText("Error");
//			        	msgbx.setMessage("No value !!!");
//			        	msgbx.open();
//					}
				}
				else {
		        	MessageBox msgbx = new MessageBox(shell,SWT.ICON_ERROR);
		        	msgbx.setText("Error");
		        	msgbx.setMessage("Key code with '-' not allowed !!!");
		        	msgbx.open();
				}
				}
				else {
		        	MessageBox msgbx = new MessageBox(shell,SWT.ICON_ERROR);
		        	msgbx.setText("Error");
		        	msgbx.setMessage("List Y is required !!!");
		        	msgbx.open();
				}

		    }
		});
		
		/*B_print = new Button(shell, SWT.PUSH);
		B_print.setText("Print Preview");
		B_print.setSize(120, 35);
		B_print.setLocation(xsh-2+130, ysh+i_heightList+i_hspace+25);
		B_print.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				printPreview(g_sFile+".jpg");
		    }
		});*/

		b_fieldx = new Button(shell,SWT.PUSH);
		b_fieldx.setLocation(xsh+i_widthList+i_hspace,ysh);
		b_fieldx.setSize(i_widthBtn, i_heightBtn);
		b_fieldx.setText("Add Criteria");
		b_fieldx.setEnabled(false);
		b_fieldx.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//System.out.print(outTextField);
				if (outTextField.compareTo("")!=0) {
					boolean ok=true;
					for (int iCnt=0;iCnt<listX.getItemCount();iCnt++) {
						//System.out.println("X     :"+listX.getItem(iCnt));
						//System.out.println("field :"+listField.getItem(Integer.parseInt(outTextField)));
						if ((listX.getItem(iCnt).startsWith(listField.getItem(Integer.parseInt(outTextField))+":")) || (listX.getItem(iCnt).startsWith("*"+listField.getItem(Integer.parseInt(outTextField))+":"))){ 
							ok=false;
							break;
						}
						
					}
					if (ok) {
						String s = listField.getItem(Integer.parseInt(outTextField));
						System.out.println("S:"+s);
						if (find(criteria.sFilename,s).compareTo("type19b")==0)
							;//listX.add(s);
						else listX.add(s+":");
					}
				}
		    }
		});

		b_fieldy = new Button(shell,SWT.PUSH);
		b_fieldy.setLocation(xsh+i_widthList+i_hspace,ysh+i_heightBtn);
		b_fieldy.setSize(i_widthBtn, i_heightBtn);
		b_fieldy.setText("Add Count");
		b_fieldy.setEnabled(false);
		b_fieldy.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//System.out.print(outTextField);
				if (outTextField.compareTo("")!=0) {
					listY.removeAll();
					boolean ok=true;
					for (int iCnt=0;iCnt<listY.getItemCount();iCnt++) {
						//System.out.println("Y     :"+listY.getItem(iCnt));
						//System.out.println("field :"+listField.getItem(Integer.parseInt(outTextField)));
						if (listY.getItem(iCnt).startsWith(listField.getItem(Integer.parseInt(outTextField))+":")) { 
							ok=false;
							break;
						}
						
					}
					System.out.println("Y     :"+listY.getItemCount());
					if (listY.getItemCount()>0) ok=false;
					if (ok) listY.add(listField.getItem(Integer.parseInt(outTextField)));
				}
		    }
		});

		///////GROUP FIELD////////
		Group tableGroupPID = new Group(shell, SWT.BORDER);
		tableGroupPID.setText("Field");
		tableGroupPID.setLayout(new GridLayout(2,false));
		tableGroupPID.setBounds(8, 80, 335, 400);

		xsh+=30;
		//Label L_x = new Label(shell,SWT.LEFT);		
		//L_x.setSize(40, 20);
		//L_x.setLocation(xsh+i_widthList+i_widthBtn+(2*i_hspace), 15);
		//L_x.setText("X");
		
		listX = createList(shell,s_itemsx,0);
		listX.setSize(i_widthList, i_heightList);
		listX.setLocation(xsh+i_widthList+i_widthBtn+(2*i_hspace), ysh);
		/*for (int iLoop=0;iLoop<3;iLoop++){
			String s = gtdt(XuxaMain.g_sFname,iLoop+1);
			System.out.println("***s:"+s);
			listX.add(s+":"+ListXval[iLoop]);
			listX.setItem(iLoop,s+":"+ListXval[iLoop]);
		}*/
		listX.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		        int[] selections = listX.getSelectionIndices();
		        outTextX = "";
		        for (int loopIndex = 0; loopIndex < selections.length; loopIndex++)
		          outTextX += selections[loopIndex] + "";
		        System.out.println("You selected1: " + listX.getItem(Integer.parseInt(outTextX)));
		        T_addV.setEnabled(true);
		        String sPars[] = listX.getItem(Integer.parseInt(outTextX)).split(":");
		        if (sPars.length>1) {
			        if (sPars[0]==null) sPars[0]="";
			        if (sPars[1]==null) sPars[1]="";
			        T_addV.setText(sPars[1]);
		        }
		        else T_addV.setText("");
		        
		        L_name.setText(sPars[0]+":");
		        L_name.setToolTipText(sPars[0]+":");
		        
		        String a = listX.getItem(Integer.parseInt(outTextX));
				
		        if (a.contains("*")) a=a.substring(1,a.length());
		        if (a.contains(":")) {
		        	String ab[] = a.split(":");
		        	a=ab[0];
		        }
				String sFind = find(criteria.sFilename,a);

				if (sFind.contains("type19b")) T_addV.setEnabled(false);
				else T_addV.setEnabled(true);
				
				if ((!sFind.contains("month")) && (!sFind.contains("type3a"))) {
		        	L_nametyp.setVisible(false);
		        	C_month.setVisible(false);
		        }
		        else {
		        	L_nametyp.setVisible(true);
		        	C_month.setVisible(true);
		        }
		        b_xremove.setEnabled(true);
		        b_setkey.setEnabled(true);
		        C_month.setText("");
				if (sFind.contains("month")) {
					C_month.setEnabled(true);
					//C_month.setItems(Month);
					C_month.setText("Select");
				}
				else if (sFind.contains("type3a")) {
					C_month.setEnabled(true);
					//C_month.setItems(TYPE);
					C_month.setText("Select");
				}
				else C_month.setEnabled(false);
		      }

		      public void widgetDefaultSelected(SelectionEvent event) {
		        int[] selections = listX.getSelectionIndices();
		        String outTextX = "";
		        for (int loopIndex = 0; loopIndex < selections.length; loopIndex++)
		          outTextX += selections[loopIndex] + "";
		        //System.out.println("You selected: " + outTextField+"end");
		        b_xremove.setEnabled(true);
		        b_setkey.setEnabled(true);
		      }
		 });
		
		
		L_name = new Label(shell,SWT.LEFT);		
		L_name.setSize(100, 20);
		L_name.setLocation(xsh+i_widthList+i_widthBtn+(2*i_hspace), ysh+i_heightList+i_hspace);
		L_name.setText("Default");

		string="";
		T_addV = new Text(shell, SWT.MULTI | SWT.BORDER);
		T_addV.setSize(i_widthList,i_heightBtn-20);
		T_addV.setLocation(110+xsh+i_widthList+i_widthBtn+(2*i_hspace), ysh+i_heightList+i_hspace);
		T_addV.setEnabled(false);
		T_addV.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				//if (e.stateMask == SWT.CTRL && e.keyCode != SWT.CTRL)
				//System.out.println(fl+" keyReleased:" + e.keyCode);
				switch (e.character) {
					case 0: 
					break;
					case SWT.BS: 
						System.out.println("e "+e.character);
						//if (string.length()!=0)
							//string=string.substring(0, string.length()-1);
						//System.out.println(string);
					break;
					case SWT.CR: 
						//string += "'\\r'";
						//System.out.println("eC "+e.character);
					break;
					//case SWT.DEL: string += " DEL"; break;
					//case SWT.ESC: string += " ESC"; break;
					//case SWT.LF: string += " '\\n'"; break;
					default: 
						System.out.println("e "+e.character);
						//string+=e.character;
						T_addV.setSelection(T_addV.getText().length());
					
					break;
				}
				if ((e.keyCode == SWT.CR) || (e.keyCode == 16777296)){
				  
			      String a = T_addV.getText();
			      a=a.substring(0, a.length()-1);
			      T_addV.setText(a);
			      T_addV.setSelection(a.length());
      			  System.out.println("Command can execute here");
      			  if (listX.getItemCount()>0) {
					//System.out.println("T_addV.getText():"+T_addV.getText());
					listX.setItem(Integer.parseInt(outTextX),L_name.getText()+T_addV.getText());
					//T_addV.setEnabled(false);
					C_month.setEnabled(false);
      			  }
      			}
			}
		});

		L_nametyp = new Label(shell,SWT.LEFT);		
		L_nametyp.setSize(100, 20);
		L_nametyp.setLocation(xsh+2+i_widthList+i_widthBtn+(2*i_hspace)-2, ysh+i_heightList+(2*i_hspace)+i_heightBtn-20);

		C_month = new Button(shell,SWT.PUSH);
		C_month.setSize(i_widthList,i_heightBtn-13);
		C_month.setLocation(110+xsh+i_widthList+i_widthBtn+(2*i_hspace), ysh+i_heightList+(2*i_hspace)+i_heightBtn-20);
		
		C_month.setEnabled(false);
		C_month.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
//				String s = listX.getItem(Integer.parseInt(outTextX));
//				if (s.contains("Month")) {
//					if (!T_addV.getText().isEmpty()) {
//						T_addV.append(",");
//					}
//				}
//				if (s.contains("Msg Type")) {
//					if (!T_addV.getText().isEmpty()) T_addV.append(",");
//				}
//				T_addV.setText(T_addV.getText()+C_month.getText());
//				T_addV.setSelection(T_addV.getText().length());
//				T_addV.setFocus();
//				String a = T_addV.getText();
//			    T_addV.setText(a);
//			    T_addV.setText(a.replaceAll("-,", "-"));
//      			if (listX.getItemCount()>0) {
//					listX.setItem(Integer.parseInt(outTextX),L_name.getText()+T_addV.getText());
//      			}
//			    T_addV.setSelection(T_addV.getText().length());
		    }
		});
    	L_nametyp.setVisible(false);
    	C_month.setVisible(false);
		shelllst2=new Shell();
		C_month.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String a="";
				if (listX.getSelectionCount()>0) a=listX.getSelection()[0];
				if (!a.isEmpty()) {
			        if (a.contains("*")) a=a.substring(1,a.length());
			        if (a.contains(":")) {
			        	String ab[] = a.split(":");
			        	a=ab[0];
			        }
					String sFind = find(criteria.sFilename,a);
					String sty[]={""};
					if (sFind.contains("month")) sty=Month;
					else if (sFind.contains("type3a")) sty=TYPE;
					if (!shelllst2.isDisposed()) shelllst2.close();
					if (shelllst2.isDisposed()) shelllst2=new Shell(SWT.BORDER|SWT.NO_TRIM|SWT.ON_TOP); 
					new ListBox2(shelllst2,200,200,sty,C_month,x+484,y+460,2);
					flgsh2=true;
				}
			}
		});



		b_xremove = new Button(shell,SWT.PUSH);
		b_xremove.setLocation(xsh+(2*i_widthList)+i_widthBtn+(3*i_hspace),ysh);
		b_xremove.setSize(i_widthBtn, i_heightBtn);
		b_xremove.setText("Delete Field");
		b_xremove.setEnabled(false);
		b_xremove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String s = listX.getItem(Integer.parseInt(outTextX));
				System.out.print(s);
				if ((listX.getItemCount()>0) && (!s.contains("Year")) && (!s.contains("Month")) && (!s.contains("Msg Type"))) {
					listX.remove(Integer.parseInt(outTextX));
					T_addV.setText("");
					T_addV.setEnabled(false);
				}
		    }
		});

		b_setkey = new Button(shell,SWT.PUSH);
		b_setkey.setLocation(xsh+(2*i_widthList)+i_widthBtn+(3*i_hspace),ysh+i_heightBtn+5);
		b_setkey.setSize(i_widthBtn, i_heightBtn);
		b_setkey.setText("Set key");
		b_setkey.setEnabled(false);
		b_setkey.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (outTextX!=null) {
				String s = listX.getItem(Integer.parseInt(outTextX));
				boolean flg=false;
				for(int iCnt=0;iCnt<listX.getItemCount();iCnt++) {
					String s2 = listX.getItem(iCnt);
					if (s2.startsWith(criteria.sKey)) {
						//flg=true;
						listX.setItem(iCnt, s2.substring(1, s2.length()));
						break;
					}
				}
				System.out.println("s:"+s);
				if (!s.startsWith(criteria.sKey)) {
				//if (!flg) {
					System.out.println("set key:"+s);
					listX.setItem(Integer.parseInt(outTextX),criteria.sKey+s);
				}
				}
		    }
		});

		b_delkey = new Button(shell,SWT.PUSH);
		b_delkey.setLocation(xsh+(2*i_widthList)+i_widthBtn+(3*i_hspace),ysh+(2*i_heightBtn)+10);
		b_delkey.setSize(i_widthBtn, i_heightBtn);
		b_delkey.setText("Delete key");
		b_delkey.setVisible(false);
		b_delkey.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String s = listX.getItem(Integer.parseInt(outTextX));
				if (s.startsWith(criteria.sKey)) {
					System.out.println("delete key:"+s);
					listX.setItem(Integer.parseInt(outTextX),s.substring(1, s.length()));
				}
		    }
		});

		/*b_xaddv = new Button(shell,SWT.PUSH);
		b_xaddv .setLocation(xsh+(2*i_widthList)+i_widthBtn+(3*i_hspace),ysh+i_heightBtn);
		b_xaddv .setSize(i_widthBtn, i_heightBtn);
		b_xaddv .setText("Add Value X");
		b_xaddv .setEnabled(false);
		b_xaddv .addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//System.out.print(outTextField);
				System.out.println("listX.getItemCount2:"+listX.getItemCount()); 
				if (listX.getItemCount()>0) {
					//System.out.println("T_addV.getText():"+T_addV.getText());
					listX.setItem(Integer.parseInt(outTextX),T_addV.getText());
					T_addV.setEnabled(false);
					C_month.setEnabled(false);
				}
		    }
		});*/

		///////GROUP X////////
		Group tableGroupPIDx = new Group(shell, SWT.BORDER);
		tableGroupPIDx.setText("Field criteria");
		tableGroupPIDx.setLayout(new GridLayout(2,false));
		tableGroupPIDx.setBounds(xsh+i_widthList+i_widthBtn+(2*i_hspace)-13, 80, 335, 400);

		xsh+=30;
//		Label L_y = new Label(shell,SWT.LEFT);		
//		L_y.setSize(40, 20);
//		L_y.setLocation(xsh+(2*i_widthList)+(2*i_widthBtn)+(4*i_hspace), 15);
//		L_y.setText("Y");
		listY = createList(shell,s_itemsy,0);
		listY.setSize(i_widthList, i_heightList-260);
		listY.setLocation(xsh+(2*i_widthList)+(2*i_widthBtn)+(4*i_hspace), ysh);
		listY.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		        int[] selections = listY.getSelectionIndices();
		        outTextY = "";
		        for (int loopIndex = 0; loopIndex < selections.length; loopIndex++)
		          outTextY += selections[loopIndex] + "";
		        System.out.println("You selected: " + outTextField);
		        b_yremove.setEnabled(true);
		        b_yaddv.setVisible(false);
				T_addVy.setEnabled(true);
				
		        String sPars[] = listY.getItem(Integer.parseInt(outTextY)).split(":");
		        if (sPars.length>1) {
			        if (sPars[0]==null) sPars[0]="";
			        if (sPars[1]==null) sPars[1]="";
			        T_addVy.setText(sPars[1]);
		        }
		        else T_addVy.setText("");

		        //L_name.setText(sPars[0]+":");
		        //L_name.setToolTipText(sPars[0]+":");
				
		      }

		      public void widgetDefaultSelected(SelectionEvent event) {
		        int[] selections = listY.getSelectionIndices();
		        String outTextY = "";
		        for (int loopIndex = 0; loopIndex < selections.length; loopIndex++)
		          outTextY += selections[loopIndex] + "";
		        //System.out.println("You selected: " + outTextField+"end");
		        b_yremove.setEnabled(true);
		        b_yaddv.setVisible(false);
				T_addVy.setEnabled(true);
		      }
		 });

		T_addVy = new Text(shell, SWT.MULTI | SWT.BORDER);
		T_addVy.setSize(i_widthList,i_heightBtn-20);
		T_addVy.setLocation(xsh+(2*i_widthList)+(2*i_widthBtn)+(4*i_hspace), ysh+i_heightList+i_hspace);
		T_addVy.setVisible(false);
		T_addVy.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				//if (e.stateMask == SWT.CTRL && e.keyCode != SWT.CTRL)
				//System.out.println(fl+" keyReleased:" + e.keyCode);
				switch (e.character) {
					case 0: 
					break;
					case SWT.BS: 
						System.out.println("e "+e.character);
						//if (string.length()!=0)
							//string=string.substring(0, string.length()-1);
						//System.out.println(string);
					break;
					case SWT.CR: 
						//string += "'\\r'";
						//System.out.println("eC "+e.character);
					break;
					//case SWT.DEL: string += " DEL"; break;
					//case SWT.ESC: string += " ESC"; break;
					//case SWT.LF: string += " '\\n'"; break;
					default: 
						System.out.println("e "+e.character);
						//string+=e.character;
						T_addV.setSelection(T_addV.getText().length());
					
					break;
				}
				if (e.keyCode == SWT.CR) {
			      String a = T_addVy.getText();
			      a=a.substring(0, a.length()-1);
			      T_addVy.setText(a);
			      T_addVy.setSelection(a.length());
      			  System.out.println("Command can execute here");
      			  if (listY.getItemCount()>0) {
					System.out.println("*1:"+Integer.parseInt(outTextY));
					System.out.println("*2:"+L_name.getText());
					System.out.println("*3:"+T_addVy.getText());
					listY.setItem(Integer.parseInt(outTextY),L_name.getText()+T_addVy.getText());
					//T_addV.setEnabled(false);
					//C_month.setEnabled(false);
      			  }
      			}
			}
		});

		b_yremove = new Button(shell,SWT.PUSH);
		b_yremove.setLocation(xsh+(3*i_widthList)+(2*i_widthBtn)+(5*i_hspace),ysh);
		b_yremove.setSize(i_widthBtn, i_heightBtn);
		b_yremove.setText("Remove");
		b_yremove.setEnabled(false);
		b_yremove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//System.out.print(outTextField);
//				if ((listY.getItemCount()>0) && (outTextY!=null)){
//					listY.remove(Integer.parseInt(outTextY));
//					T_addVy.setText("");
//					T_addVy.setEnabled(false);
//				}
				listY.removeAll();
		    }
		});
		
		b_yaddv = new Button(shell,SWT.PUSH);
		b_yaddv .setLocation(xsh+(3*i_widthList)+(2*i_widthBtn)+(5*i_hspace),ysh+i_heightBtn);
		b_yaddv .setSize(i_widthBtn, i_heightBtn);
		b_yaddv .setText("Add Value Y");
		b_yaddv .setEnabled(false);
		b_yaddv .addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//System.out.print(outTextField);
				if (listY.getItemCount()>0) {
					listY.setItem(Integer.parseInt(outTextY),T_addVy.getText());
					T_addVy.setEnabled(false);
				}
		    }
		});
		
		C_month1 = new Combo(shell,SWT.READ_ONLY);
		C_month1.setSize(i_widthList+4,i_heightBtn-20);
		C_month1.setLocation(xsh+(2*i_widthList)+(2*i_widthBtn)+(4*i_hspace)-2, ysh+i_heightList+(2*i_hspace)+i_heightBtn-20);
		
		C_month1.setVisible(false);
		C_month1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				T_addVy.setText(T_addVy.getText()+C_month1.getText());
		    }
		});

		///////GROUP Y////////
		Group tableGroupPIDy = new Group(shell, SWT.BORDER);
		tableGroupPIDy = new Group(shell, SWT.BORDER);
		tableGroupPIDy.setText("Count on");
		tableGroupPIDy.setLayout(new GridLayout(2,false));
		tableGroupPIDy.setBounds(xsh+(2*i_widthList)+(2*i_widthBtn)+(4*i_hspace)-13, 80, 335, 400-325);
		//setVal(false);
		
        b_yremove.setEnabled(true);
        b_yaddv.setVisible(false);
		T_addVy.setEnabled(true);

	}
	
	public String find (String fname,String sSearch) {
        String sRes="";
   	 String[] subbuf = new String[100];
        try {
            File myFile = new File(fname);
            FileReader fileReader = new FileReader(myFile);
            BufferedReader buffReader = new BufferedReader(fileReader);
            String buf = null;

            while ((buf = buffReader.readLine()) != null) {
                if ((!buf.startsWith("#")) &&  (!buf.startsWith("//"))) {
               	 subbuf = buf.split("=");
               	 sRes = subbuf[1];
               	 if (sRes.compareTo(sSearch)==0) {
                   	 sRes = subbuf[0];
                   	 break;
                    }
                }
            }
            buffReader.close();
        } catch (Exception err) {
            Shell sh = new Shell();
            MessageBox mg = new MessageBox(sh);
            mg.setMessage(err.getMessage());
            mg.open();
        }
        return sRes;
    }

    String gtdt (String fname,int line) {
    	String sRes="";
   	 	String[] subbuf = new String[100];
        try {
            File myFile = new File(fname);
            FileReader fileReader = new FileReader(myFile);
            BufferedReader buffReader = new BufferedReader(fileReader);
            String buf = null;
            int cnt=0;
            while ((buf = buffReader.readLine()) != null) {
                if ((!buf.startsWith("#")) &&  (!buf.startsWith("//"))) {
               	 subbuf = buf.split("=");
               	 sRes = subbuf[1];
                }
                cnt++;
                if (line==cnt) break;
            }
            buffReader.close();
        } catch (Exception err) {
            Shell sh = new Shell();
            MessageBox mg = new MessageBox(sh);
            mg.setMessage(err.getMessage());
            mg.open();
        }
        return sRes;
    }

    int getdt (String fname) {
        String[] subbuf = new String[100];
        int iCnt=0;
        try {
            File myFile = new File(fname);
            FileReader fileReader = new FileReader(myFile);
            BufferedReader buffReader = new BufferedReader(fileReader);
            String buf = null;

            while ((buf = buffReader.readLine()) != null) {
                if ((!buf.startsWith("#")) &&  (!buf.startsWith("//"))) {
                    subbuf = buf.split("=");
                    if (subbuf.length>1) {
                    	s_items[iCnt] = subbuf[1];
                    	System.out.println("val:" + s_items[iCnt]);
                    	iCnt++;
                    }
                }
            }
            buffReader.close();
        } catch (Exception err) {
            System.out.println("err" + err);
        }
        return iCnt;
    }
	
	String getdt1(String fname) {
		String sOrg="";
        try {
            File myFile = new File(fname);
            FileReader fileReader = new FileReader(myFile);
            BufferedReader buffReader = new BufferedReader(fileReader);
            String buf = null;

            while ((buf = buffReader.readLine()) != null) {
            	sOrg=buf;
            }
            
            System.out.println(fname+" stAIDC:"+sOrg);
            buffReader.close();
        } catch (Exception err) {
            System.out.println("err" + err);
        }
        return sOrg;
    }

    List createList(Composite shell,String sValue[], int length) {
		final List list = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
		
	    for (int loopIndex = 0; loopIndex < length; loopIndex++) {
	      //list.add(sValue[loopIndex]);
	    }

	    return list;
	}
	
    void connect ()
    {
   	 	try {
//	         jdbc db = new jdbc();
//	         String driver = "com.mysql.jdbc.Driver";
//	         Class.forName(driver);
	         System.out.println("Connecting To Database...");
//	         conn = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());

	         //STEP 2: Register JDBC driver
	         Class.forName(ConnectTo.JDBC_DRIVER);
	         //STEP 3: Open a connection
	         conn = DriverManager.getConnection(ConnectTo.DB_NAME,ConnectTo.DB_USER,ConnectTo.DB_PASS);
				
	     	 stmt = conn.createStatement();
	         System.out.println("OK");
	         getfield();
   	 	} catch (SQLException se) {
	         se.printStackTrace();
	         Shell sh = new Shell();
	         MessageBox mg = new MessageBox(sh);
	         mg.setMessage(se.getMessage());
	         mg.open();
   	 	} catch (Exception e) {
	         e.printStackTrace();
	         Shell sh = new Shell();
	         MessageBox mg = new MessageBox(sh);
	         mg.setMessage(e.getMessage());
	         mg.open();
   	 	}
    }
    
    void getfield()
    {
    	String sfield[]={"fieldATS","fieldNTM","fieldMET"};
    	
    	try {
    		WriteToAFile wr = new WriteToAFile();
    		for (int i=0;i<3;i++) {
	    		String sQuery="select * from "+sfield[i];
	    		ResultSet resultSet = stmt.executeQuery(sQuery);
	    		wr.open(sFnmfield+sfield[i]);
	    		System.out.println("writing to "+sFnmfield+sfield[i]);
	    		if (resultSet.next()) {
	    			if (resultSet.getString("fieldnm")!=null) {
	    				System.out.println("fieldnm:"+resultSet.getString("fieldnm"));
	    				wr.write(resultSet.getString("fieldnm"));
	    				wr.write("\n");
	    			}
	    		}
	    		wr.close();
    		}
    	} catch (SQLException se) {
    		se.printStackTrace();
            Shell sh = new Shell();
            MessageBox mg = new MessageBox(sh);
            mg.setMessage(se.getMessage());
            mg.open();
    	}

    }
    
    void close()
    {
      	 try {
      		 stmt.close();
      		 conn.close();
      		 System.out.println("Database closed");
       	 } catch (SQLException se) {
	         se.printStackTrace();
       	 } catch (Exception e) {
	         e.printStackTrace();
       	 }
    }
// 
}