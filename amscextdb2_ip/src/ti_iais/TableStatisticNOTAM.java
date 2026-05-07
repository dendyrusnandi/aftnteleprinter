package ti_iais;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import actions.cProgressBar;

import displays.AmscSplashScreen2;
import displays.ConnectTo;
import displays.DialogFactory;
import displays.Lists;

import readwrite.ReadFromFile;
import setting.Shorten;


public class TableStatisticNOTAM {
	HeaderFooter fp = new HeaderFooter();
//	ButtonsSetting bs = new ButtonsSetting();
	WriteExcel writexls = new WriteExcel();
	Shorten sh = new Shorten();
	
	String sorig="",sserie="";
	
	Table table;
	Text Month,Year;
	Combo month;
	Button alr,rcf,fpl,dla,chg,cnl,dep,arr,cdn,cpl,est,acp,lam,send_m,receive,PrintToPDF,PrintToXLS;
	String select,select2;
	String sTypeMsg1[];
	int iTotLoc;
	TableColumn LocationT;
	TableColumn org;
	TableColumn tcloc;
	TableColumn tcserie;
	String sAl1[];
	
	final String JANUARY="JANUARY";
	final String FEBRUARY="FEBRUARY";
	final String MARCH="MARCH";
	final String APRIL="APRIL";
	final String MAY="MAY";
	final String JUNE="JUNE";
	final String JULY="JULY";
	final String AUGUST="AUGUST";
	final String SEPTEMBER="SEPTEMBER";
	final String OCTOBER="OCTOBER";
	final String NOVEMBER="NOVEMBER";
	final String DECEMBER="DECEMBER";
	
	String[] MONTH = new String[]{JANUARY,FEBRUARY, MARCH, APRIL, MAY, JUNE,JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER};
	String[] sTypNtm = new String[]{"NOTAMN","NOTAMR","NOTAMC","RQNTMN","RQNTMR","RQNTMC"};
	String msgTypeNtm,MonthAts,yearNtm,serie,origin,loc;
    Font font;
    int tot,tot1;
    int iTypNtm=6;
    String st_sent;
    Shell shell;
    
//    static PrintAction printExec = new PrintAction();
	String sOrigin;
	
	public TableStatisticNOTAM(){
		sOrigin="";
//		sOrigin=TableStatisticATS.getdt("/aftn/InOutbound.txt");
		sOrigin=TableStatisticATS.getdt("/aed/InOutbound.txt");
	}
   
	public void NOTAM_STATISTIC(Shell pshell,String pmsgTypeNtm,String pMonthAts,String pyearNtm,String pserie,String porigin,final String ploc,String pst_sent) {
		shell=pshell;
		msgTypeNtm=pmsgTypeNtm;
		MonthAts=pMonthAts;
		yearNtm=pyearNtm;
		serie=pserie;
		origin=porigin;
		loc=ploc;
		st_sent=pst_sent;
		
		if (ploc.isEmpty()) shell.setText("NOTAM Messages Statistic");
		else shell.setText("NOTAM Message Statistic");
		shell.setLayout(new GridLayout());
	    new ReadFromFile().readConfiguration();
	    shell.setLocation(new ReadFromFile().getXstat(),new ReadFromFile().getYstat());
		
	    font = new Font(AmscSplashScreen2.display, "Courier", 9, SWT.BOLD);
	    table = new Table (shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION); sh.tableStyle(table, true, true, 270);
		table.setFont(font);
		
		LocationT = new TableColumn(table,SWT.LEFT);
		LocationT.setText("Type");
		LocationT.setToolTipText("NOTAM Type");
		LocationT.setWidth(50);
		
		org = new TableColumn(table,SWT.LEFT);
		org.setText("Originator");
		org.setToolTipText("Originator");
		org.setWidth(100);

		tcloc = new TableColumn(table,SWT.LEFT);
		tcloc.setText("Loca.");
		tcloc.setToolTipText("Location");
		tcloc.setWidth(50);

		tcserie = new TableColumn(table,SWT.CENTER);
		tcserie.setText("S");
		tcserie.setToolTipText("NOTAM ID Serie");
		tcserie.setWidth(25);

		TableColumn tgl[] = new TableColumn[31];
		for (int iCnt=0;iCnt<31;iCnt++) {
			tgl[iCnt]=new TableColumn(table,SWT.RIGHT);
			tgl[iCnt].setText(Integer.toString(iCnt+1));
			tgl[iCnt].setWidth(30);
		}
		
		TableColumn total = new TableColumn(table,SWT.RIGHT);
		total.setText("SubTot");
		total.setToolTipText("Sub Total");
		total.setWidth(60);

		koneksiDB();
		//---------------------------------------------------------
		Composite typeB = new Composite(shell, SWT.NONE); sh.composeStyle(typeB, 3, SWT.CENTER, SWT.CENTER);
		PrintToPDF = new Button(typeB, SWT.PUSH);
		PrintToPDF.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String sTitle="NOTAM Messages Statistic: ";
//				if (ploc.isEmpty()) { sTitle+="Messages Statistic: "; }
//				else { sTitle+="Message Statistic: "; }
				
				if (MonthAts.compareTo("01")==0) sTitle+="January";
				else if (MonthAts.compareTo("02")==0) sTitle+="February";
				else if (MonthAts.compareTo("03")==0) sTitle+="March";
				else if (MonthAts.compareTo("04")==0) sTitle+="April";
				else if (MonthAts.compareTo("05")==0) sTitle+="May";
				else if (MonthAts.compareTo("06")==0) sTitle+="June";
				else if (MonthAts.compareTo("07")==0) sTitle+="July";
				else if (MonthAts.compareTo("08")==0) sTitle+="August";
				else if (MonthAts.compareTo("09")==0) sTitle+="September";
				else if (MonthAts.compareTo("10")==0) sTitle+="October";
				else if (MonthAts.compareTo("11")==0) sTitle+="November";
				else if (MonthAts.compareTo("12")==0) sTitle+="December";
				sTitle+=" "+yearNtm;
				
//				if (st_sent.compareTo("")!=0) {
//					sTitle+="\nStatus: ";
//					if (st_sent.compareTo("1")==0) sTitle+="Sent";
//					else sTitle+="Received";
//				}
				if (origin.compareTo("")!=0) {
					sTitle+="\nOriginator: "+origin;
					sorig = origin;
				}
				if (ploc.compareTo("")!=0) {
					sTitle+="\nLocation: "+ploc;
				}
				if (serie.compareTo("")!=0) {
					sTitle+="\nSerie: "+serie;
					sserie = serie;
				}
				printToFile(sTitle,"pdf");
			}
		});
		
		PrintToXLS = new Button(typeB, SWT.PUSH);
		PrintToXLS.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String sTitle="NOTAM Messages Statistic: ";
//				if (ploc.isEmpty()) { sTitle+="Messages Statistic: "; }
//				else { sTitle+="Message Statistic: "; }
				
				if (MonthAts.compareTo("01")==0) sTitle+="January";
				else if (MonthAts.compareTo("02")==0) sTitle+="February";
				else if (MonthAts.compareTo("03")==0) sTitle+="March";
				else if (MonthAts.compareTo("04")==0) sTitle+="April";
				else if (MonthAts.compareTo("05")==0) sTitle+="May";
				else if (MonthAts.compareTo("06")==0) sTitle+="June";
				else if (MonthAts.compareTo("07")==0) sTitle+="July";
				else if (MonthAts.compareTo("08")==0) sTitle+="August";
				else if (MonthAts.compareTo("09")==0) sTitle+="September";
				else if (MonthAts.compareTo("10")==0) sTitle+="October";
				else if (MonthAts.compareTo("11")==0) sTitle+="November";
				else if (MonthAts.compareTo("12")==0) sTitle+="December";
				sTitle+=" "+yearNtm;
				
//				if (st_sent.compareTo("")!=0) {
//					sTitle+="\nStatus: ";
//					if (st_sent.compareTo("1")==0) sTitle+="Sent";
//					else sTitle+="Received";
//				}
				if (origin.compareTo("")!=0) {
					sTitle+="\nOriginator: "+origin;
					sorig = origin;
				}
				if (ploc.compareTo("")!=0) {
					sTitle+="\nLocation: "+ploc;
				}
				if (serie.compareTo("")!=0) {
					sTitle+="\nSerie: "+serie;
					sserie = serie;
				}
				printToFile(sTitle,"xls");
			}
		});
//		bs.Export(typeB, PrintToPDF, PrintToXLS);
		Button bClose = new Button(typeB, SWT.PUSH); sh.buttonStyle(bClose, "Close", "", 150, sh.DarkGrey, SWT.CENTER, SWT.CENTER, null); 
		bClose.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close(); } });
		
		sh.buttonStyle(PrintToPDF, "Export to PDF", "", 150, sh.DarkGrey, SWT.CENTER, SWT.CENTER, null);
		sh.buttonStyle(PrintToXLS, "Export to XLS", "", 150, sh.DarkGrey, SWT.CENTER, SWT.CENTER, null);
		
		shell.pack ();
		shell.open ();
	}

  	void koneksiDB() {
  		//------------------------------------ KONEKSI DB ------------------------------------
		try {
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver).newInstance();
		} catch(ClassNotFoundException c) {
			c.printStackTrace();
		} catch(Exception ex){
			ex.printStackTrace();
		}
		
		final cProgressBar progbr = new cProgressBar("");
		new Thread() {
	        public void run() {
	          if (AmscSplashScreen2.display.isDisposed()) return;
		      AmscSplashScreen2.display.asyncExec(new Runnable() {
		      public void run() {
				try {
					Connection conn = DriverManager.getConnection(ConnectTo.DB_NAME,ConnectTo.DB_USER,ConnectTo.DB_PASS);
					Statement stmt = conn.createStatement();
					String yearMonths="'"+yearNtm+"-"+MonthAts+"-";
					String yearMonths2="'"+yearNtm+"-"+MonthAts+"-";
					String date,date1="0",sTime="00:00:00";
					String sQuery1="";
					
					/*if (serie.isEmpty()) {
						String sQueryB="select ntm_id_serie from notam_multi"+yearNtm+"_"+MonthAts+" where ntm_id_serie!=''";
						sQueryB+=" and tgl>="+yearMonths+"01'";
						int imonths = Integer.parseInt(MonthAts)+1;
						String yearMonths1="";
						if (imonths<13) {
							String months0="0";
							String sMonths = Integer.toString(imonths);
							yearMonths1 = "'"+yearNtm+"-";
							if (sMonths.length()==1) yearMonths1 += (months0+sMonths);
							else yearMonths1 += sMonths;
							yearMonths1+="-";
						}
						else {
							int iyear = Integer.parseInt(yearNtm)+1;
							yearMonths1 = "'"+Integer.toString(iyear)+"-"+"01"+"-";
						}
						sQueryB+=" and tgl < "+yearMonths1+"01'";
						if (!loc.isEmpty()) {
							String sArr[] = loc.split(",");
							for (int i=0;i<sArr.length;i++) {
								if (i==0) sQueryB+=" and (";
								else sQueryB+=" or ";
								sQueryB+="A LIKE '"+sArr[i]+"%'";//percobaan
								if (i==sArr.length-1) sQueryB+=")";
							}
						}
						sQueryB+=" group by ntm_id_serie";
						System.out.println(sQueryB);
						ResultSet rs2 = stmt.executeQuery(sQueryB);
						while (rs2.next()) {
							if (rs2.getString("ntm_id_serie")!=null){
								//System.out.println(rs2.getString("ntm_id_serie"));
								serie+=rs2.getString("ntm_id_serie")+",";
							}
						}
						rs2.close();
					}*/
					int lnserie=0;
					if (!serie.isEmpty()) {
						sAl1=serie.split(",");
						lnserie=sAl1.length;
					}
					if (lnserie==0) lnserie=1;
					//System.out.println("serie:"+serie);
					
					String sQueryA="select A from notam_multi"+yearNtm+"_"+MonthAts+" where A!=''";
					//String sQueryA="select A from notam where A!=''";
//					if (!loc.isEmpty()) sQueryA+=" and A='"+loc+"'";//asli
					if (!loc.isEmpty()) {
						String sArr[] = loc.split(",");
						for (int i=0;i<sArr.length;i++) {
							if (i==0) sQueryA+=" and (";
							else sQueryA+=" or ";
							sQueryA+="A LIKE '"+sArr[i]+"%'";//percobaan
							if (i==sArr.length-1) sQueryA+=")";
						}
					}
					sQueryA+=" and tgl>="+yearMonths+"01'";
					int imonths = Integer.parseInt(MonthAts)+1;
					String yearMonths1="";
					if (imonths<13) {
						String months0="0";
						String sMonths = Integer.toString(imonths);
						yearMonths1 = "'"+yearNtm+"-";
						if (sMonths.length()==1) yearMonths1 += (months0+sMonths);
						else yearMonths1 += sMonths;
						yearMonths1+="-";
					}
					else {
						int iyear = Integer.parseInt(yearNtm)+1;
						yearMonths1 = "'"+Integer.toString(iyear)+"-"+"01"+"-";
					}
					sQueryA+=" and tgl < "+yearMonths1+"01'";
					//if (!st_sent.isEmpty()) {
					//	if (st_sent.compareTo("1")==0) sQueryA+=" and originator like '"+sOrigin+"%'";
					//	else if (st_sent.compareTo("0")==0) sQueryA+=" and originator not like '"+sOrigin+"%'";
					//}
					sQueryA+=" group by A";
					System.out.println(sQueryA);
					ResultSet rs2 = stmt.executeQuery(sQueryA);
					rs2.last();
					tot=rs2.getRow();
					//System.out.println(tot+" rows"+" totserie"+lnserie+" total"+(iTypNtm*tot*lnserie)+1);
					//System.out.println("I:"+iTypNtm+" T:"+tot+" L:"+lnserie);
					int max=(2*(iTypNtm*tot*lnserie))+(31*2);
					int icntmax=0;
					progbr.create(max);

					TableItem item[] = new TableItem[(iTypNtm*tot*lnserie)+1]; 
					String sAl[] = new String[(iTypNtm*tot)+1];
					int cnt=0,cntt=0;
					if (msgTypeNtm.compareTo("")!=0) {iTypNtm=1;sTypNtm[0]=msgTypeNtm;}
					else if (!serie.isEmpty()) iTypNtm=3;
					for (int iCntTyp=0;iCntTyp<iTypNtm;iCntTyp++) {
						rs2.beforeFirst();
						while (rs2.next()) {
							if (rs2.getString("A")!=null){
								for (int iC=0;iC<lnserie;iC++) {
									if ((!serie.isEmpty()) && (iC!=0))cnt++; 
									//System.out.println("iCntTyp:"+iCntTyp+" cnt:"+cnt+" iC:"+iC);
									item[cnt] = new TableItem (table, SWT.NONE);
									sAl[cntt]=(rs2.getString("A"));
									if (sAl[cntt].contains("`")) sAl[cntt] = sAl[cntt].replace('`', ' '); //tambahan, biar ga ada kutip ` nya
									if (!msgTypeNtm.isEmpty()) item[cnt].setText(0,msgTypeNtm);
									if (!origin.isEmpty()) item[cnt].setText(1,origin);
									if (!sAl[cntt].isEmpty()) item[cnt].setText(2,sAl[cntt]);
									if (!serie.isEmpty()) item[cnt].setText(3,sAl1[iC]);
									for (int iLp=0;iLp<31;iLp++) item[cnt].setText(iLp+4,"0"); 
									item[cnt].setText(0,sTypNtm[iCntTyp]);
									//System.out.println(cnt+" "+sAl[cnt]);
									//System.out.println(cnt+" sAl1[iC]:"+sAl1[iC]);
									progbr.bar.setSelection(icntmax);
									icntmax++;
								}
							}
							else {sAl[cntt]="A";}
							//System.out.println(cnt+" "+sAl[cnt]);
							cnt++;cntt++;
						}
					}
					rs2.close();
					item[cnt] = new TableItem (table, SWT.NONE);
					int iXtot=2;
					if (!msgTypeNtm.isEmpty()) iXtot=0;
					else if (!origin.isEmpty()) iXtot=1;
					else if (!loc.isEmpty()) iXtot=2;
					else if (!serie.isEmpty()) iXtot=3;
					//System.out.println("iXtot:"+iXtot);
					item[cnt].setText(iXtot,"Total");
					/*int totalH[][]=new int[iTypNtm][tot*lnserie];
					for (int iNt=0;iNt<iTypNtm;iNt++) {
						for (int iNt1=0;iNt1<tot;iNt1++) totalH[iNt][iNt1]=0; 
					}*/
					
					int iLoop;
					for (iLoop=1;iLoop<32;iLoop++) {
						for (int iCntTyp=0;iCntTyp<iTypNtm;iCntTyp++) {
							int totalV=0;
							String sQuery="select identifiers,originator,A,ntm_id_serie,count(id_notam_multi)as count from notam_multi"+yearNtm+"_"+MonthAts+" where tgl>";
							//String sQuery="select identifiers,originator,A,ntm_id_serie,count(id_notam)as count from notam where tgl>";
							date=Integer.toString(iLoop);
							date1="0";
							if (date.length()==1) date1+=date;
							else date1=date;
							sQuery+=yearMonths2+date1+" "+sTime+"'";
			
							if (iLoop==31) {
								date=Integer.toString(1);//1st
								int imonth = Integer.parseInt(MonthAts)+1;
								if (imonth<13) {
									String months0="0";
									String sMonths = Integer.toString(imonth);
									yearMonths = "'"+yearNtm+"-";
									if (sMonths.length()==1) yearMonths += (months0+sMonths);
									else yearMonths += sMonths;
									yearMonths+="-";
								}
								else {
									int iyear = Integer.parseInt(yearNtm)+1;
									yearMonths = "'"+Integer.toString(iyear)+"-"+"01"+"-";
								}
							}
							else date=Integer.toString(iLoop+1);
							date1="0";
							if (date.length()==1) date1+=date;
							else date1=date;
							sQuery+=" and tgl<"+yearMonths+date1+" "+sTime+"'";
							boolean bLoc=false;
							if (!msgTypeNtm.isEmpty()) sQuery+=" and identifiers='"+msgTypeNtm+"'";
							//else LocationT.setWidth(0);
							if (!origin.isEmpty()) {
								String sArr[] = origin.split(",");
								for (int i=0;i<sArr.length;i++) {
									if (i==0) sQuery+=" and (";
									else sQuery+=" or ";
									sQuery+="originator like '"+sArr[i]+"%'";
									if (i==sArr.length-1) sQuery+=")";
								}
							
							}
							else org.setWidth(0);
							if (!serie.isEmpty()) {
								String sArr[] = serie.split(",");
								for (int i=0;i<sArr.length;i++) {
									if (i==0) sQuery+=" and (";
									else sQuery+=" or ";
									sQuery+="ntm_id_serie='"+sArr[i]+"'";
									if (i==sArr.length-1) sQuery+=")";
								}
							}
							else tcserie.setWidth(0);
							if (!loc.isEmpty()) {
								String sArr[] = loc.split(",");
								for (int i=0;i<sArr.length;i++) {
									if (i==0) sQuery+=" and (";
									else sQuery+=" or ";
									sQuery+="A='"+sArr[i]+"'";
									bLoc=true;
									if (i==sArr.length-1) sQuery+=")";
								}
							}
							if (!st_sent.isEmpty()) {
								if (st_sent.compareTo("1")==0) sQuery+=" and originator like '"+sOrigin+"%'";
								else if (st_sent.compareTo("0")==0) sQuery+=" and originator not like '"+sOrigin+"%'";
							}
							sQuery+=" and identifiers='"+sTypNtm[iCntTyp]+"'";
							if (!serie.isEmpty()) sQuery+=" group by ntm_id_serie,A order by count desc";
							else sQuery+=" group by A order by count desc";
							System.out.println(sQuery+sQuery1);
							ResultSet rs = stmt.executeQuery(sQuery+sQuery1);
							while (rs.next()) {
								if ((rs.getString("A")!=null) && (rs.getString("count")!=null) && (rs.getString("identifiers")!=null)){
									String A=rs.getString("A");
									String B=rs.getString("ntm_id_serie");
									String C=rs.getString("identifiers");
									for (int iCnt=0;iCnt<tot;iCnt++) {
										//for (int iC=0;iC<lnserie;iC++) {
											if (((tot*iCntTyp*lnserie)+iCnt)<=cnt) {
												if (!serie.isEmpty()) {
													boolean flg=false;
													for (int iC=0;iC<lnserie;iC++) {
														//System.out.println("tgl:"+iLoop+" count:"+rs.getString("count")+" "+iCnt+" Location:"+rs.getString("A")+" sAl:"+sAl[iCnt]+" iCnt"+iCnt+" identifiers:"+iCntTyp+" lnserie"+lnserie+" serie query:"+sAl1[iC]+" serie:"+B);
														if ((A.compareTo(sAl[iCnt])==0) && (B.compareTo(sAl1[iC])==0) && (C.compareTo(sTypNtm[iCntTyp])==0)) {
															item[(tot*iCntTyp*lnserie)+(iCnt*lnserie)+iC].setText(iLoop+3,rs.getString("count"));
															//totalV+=Integer.parseInt(rs.getString("count"));
															//totalH[iCntTyp][iCnt]+=Integer.parseInt(rs.getString("count"));
															flg=true;
															//System.out.println("got it");
															break;
														}
													}
													if (flg) break;
												}
												else if ((rs.getString("A").compareTo(sAl[iCnt])==0) && (rs.getString("identifiers").compareTo(sTypNtm[iCntTyp])==0)) {
													//System.out.println(iLoop+" "+rs.getString("count")+" "+iCnt+" A:"+rs.getString("A")+" sAl:"+sAl[iCnt]+" iCnt"+iCnt+" iCntTyp"+iCntTyp);													
													int k=(tot*iCntTyp)+iCnt;
													//System.out.println("k"+k);
													item[(tot*iCntTyp)+iCnt].setText(iLoop+3,rs.getString("count"));
													//totalV+=Integer.parseInt(rs.getString("count"));
													//totalH[iCntTyp][iCnt]+=Integer.parseInt(rs.getString("count"));
													break;
												}
											}
										//}
									}
								}
							}
							rs.close();
//							item[tot].setText(iLoop+3,Integer.toString(totalV));
							//break;
							//progbr.bar.setSelection(iLoop+cnt);
						}
						//break;
						progbr.bar.setSelection(icntmax);
						icntmax++;
					}
					
					//horizontal - sub total
					for (int iLoop1=0;iLoop1<tot*iTypNtm*lnserie;iLoop1++) {
						int iSum=0;
						for (int iTgl=4;iTgl<35;iTgl++) {
							//System.out.print("date:"+table.getItem(iLoop1).getText(iTgl)+" ");
							iSum+=Integer.parseInt(table.getItem(iLoop1).getText(iTgl));
						}
						item[iLoop1].setText(35,Integer.toString(iSum));
						progbr.bar.setSelection(icntmax);
						icntmax++;
					}
					for (;;){
						int total=table.getItemCount();
						boolean flg=true;
						for (int i=0;i<total;i++) {
							int iSum=0;
							for (int iTgl=4;iTgl<35;iTgl++) {
								//System.out.print("date:"+table.getItem(iLoop1).getText(iTgl)+" ");
								if (!table.getItem(i).getText(iTgl).isEmpty())
									iSum+=Integer.parseInt(table.getItem(i).getText(iTgl));
							}
							//System.out.println(i+" iSum:"+iSum);
							if (iSum==0) {
								//System.out.println("dispose items["+i+"]:"+table.getItem(i).getText(2));
								table.getItem(i).dispose();
								flg=false;
								break;
							}
						}
						if (flg) break;
					}
					int total=table.getItemCount();
					//System.out.println("total:"+total);
					item[total] = new TableItem (table, SWT.NONE);
					//System.out.println("iXtot:"+iXtot);
					item[total].setText(iXtot,"Total");
					
					total=table.getItemCount();
					for (int k=0;k<table.getItemCount();k++) {
						//System.out.println("items["+k+"]:"+table.getItem(k).getText(2));
					}
					
					//vertical - total
					for (int iTgl=4;iTgl<36;iTgl++) {
						int iSum=0;
						for (int iLoop1=0;iLoop1<total;iLoop1++) {
							//System.out.print("date:"+table.getItem(iLoop1).getText(iTgl)+" ");
							if (!table.getItem(iLoop1).getText(iTgl).isEmpty()) 
								iSum+=Integer.parseInt(table.getItem(iLoop1).getText(iTgl));
						}
						//System.out.println("total-1"+(total-1));
						item[total-1].setText(iTgl,Integer.toString(iSum));
						//progbr.bar.setSelection(cnt+iLoop+cntl+iTgl);
						progbr.bar.setSelection(icntmax);
						icntmax++;
					}
					progbr.finish();
					stmt.close();
					conn.close();
				}
				catch (SQLException s) {
					DialogFactory.openInfoDialog("Search Statistic Messages","There's no data !!");
					shell.dispose();
					s.printStackTrace();
				}
		      }
		    });
		   }
		}.start();
		progbr.begin();
  	}
  	
//  	void printTable(){
//  		System.out.println("Ready to print Table");
//  	    try{
//  	    	 FileWriter fstream = new FileWriter("out.txt");
//  	         BufferedWriter out = new BufferedWriter(fstream);
//  	         String sText="";
//  	         int iLoopBar=0;
//  	         for (int iLoop=0;iLoop<36;iLoop++){
//  	        	 //System.out.println("org.width:"+table.getColumn(iLoop));
// 	        	 if ((iLoop==0) && (!msgTypeNtm.isEmpty())) {
// 	        		 sText+=table.getColumn(iLoop).getText();
// 	        		 sText+="   ";
// 	        	 }
// 	        	 else if ((iLoop==1) && (!origin.isEmpty())) {
// 	        		 sText+=table.getColumn(iLoop).getText();
// 	        		 sText+="   ";
// 	        	 }
// 	        	 else if ((iLoop==3) && (!serie.isEmpty())) {
// 	        		 sText+=table.getColumn(iLoop).getText();
// 	        		 sText+="   ";
// 	        	 }
// 	        	 else if ((iLoop==2) || (iLoop>3)) {
// 	        		 sText+=table.getColumn(iLoop).getText();
// 	        		 sText+="   ";
// 	        	 }
//				 iLoopBar=iLoop;
//  	         }
//  	         //int n=sText.length();
//  	         //sText+="\n";
//  	         //for (int iLoop=0;iLoop<n+2;iLoop++) sText+="-";
//  	         
//  			 for (int iLoop1=0;iLoop1<tot;iLoop1++){
//      	         for (int iLoop=0;iLoop<36;iLoop++){
//      	        	sText+=table.getItem(iLoop1).getText(iLoop);
//      	        	
//      	        	sText+="   ";
//      	         }
//      	         sText+="\n";
//        	 }
//  	         
//  	         out.write(sText);
//  	         System.out.println(sText);
//  	         out.close();
//  	    }catch (Exception e){//Catch exception if any
//  	         System.err.println("Error: " + e.getMessage());
//  	    }
//  	}
  	
  	void printToFile(final String sTitle,final String sType) {
  		final cProgressBar progbr = new cProgressBar("");
  		new Thread() {
	        public void run() {
	        	if (AmscSplashScreen2.display.isDisposed()) return;
	        	AmscSplashScreen2.display.asyncExec(new Runnable() {
	        		public void run() {
	        			progbr.create(table.getColumnCount()+table.getItemCount()-1);
	        			//System.out.println("tclm:"+table.getColumnCount()+" trow:"+table.getItemCount());
		  		
	        			//WriteToAFile writeAToFile = new WriteToAFile(); 
	        			//writeAToFile.open("/tmp/ramdisk0/statistic.txt");
	        			//writeAToFile.write(sTitle+"\n\n");
	        			
	        			int iCntBar=0;boolean flgcnt=false;
	        			for (int iCnt=0;iCnt<table.getColumnCount();iCnt++) {
	        				String sClmn = table.getColumn(iCnt).getText();
	        				//System.out.println("clmn:"+sClmn);
	        				flgcnt=true;
	        				if (iCnt==1) flgcnt=false;
	        				if (serie.isEmpty()) {
	        					if (iCnt==3) flgcnt=false;
	        					}
	        				if (flgcnt) {
	        					//if ((iCnt!=1) && (iCnt!=3)) {
	        					/*if (sClmn.compareTo("Originator")==0) {
									//sData+="Org.";
					  				writeAToFile.write("Org.");
					  				sClmn="Org.";
								} else {
									//sData+=sClmn; 
									writeAToFile.write(sClmn);
								}*/
	        					//writeAToFile.write(table.getColumn(iCnt).getText());
	        					int imaxtrim=6;
	        					if ((sClmn.compareTo("Type")==0) || (sClmn.compareTo("Ser.")==0)) imaxtrim=8;
		  				
	        					int itrim=sClmn.length();
	        					for(int ilp=0;ilp<imaxtrim-itrim;ilp++) {
	        						//writeAToFile.write(" ");
	        					}
	        				}
	        				progbr.bar.setSelection(iCnt);
	        				iCntBar=iCnt;
	        			}
	        			//writeAToFile.write("\n\n");
				
	        			String alldata="",tes="";
	        			for (int iCnt1=0;iCnt1<table.getItemCount();iCnt1++) {
	        				for (int iCnt=0;iCnt<table.getColumnCount();iCnt++) {
	        					String sRow = table.getItem(iCnt1).getText(iCnt);
	        					flgcnt=true;
	        					if (iCnt==1) flgcnt=false;
	        					if (serie.isEmpty()) {
	        						if (iCnt==3) flgcnt=false;
	        					}
	        					if ((flgcnt) || (sRow.compareTo("Total")==0) ) {
	        						int imaxtrim=6;
	        						if ((sRow.compareTo("")==0) && (iCnt1==table.getItemCount()-1) && (iCnt==0)) imaxtrim=8;
	        						else if (sRow.compareTo("")==0) imaxtrim=6; 
	        						//else writeAToFile.write(table.getItem(iCnt1).getText(iCnt));

	        						if ((sRow.startsWith("RQNTM")) || (sRow.startsWith("NOTAM"))) imaxtrim=8;
	        						else if (table.getColumn(iCnt).getText().compareTo("Ser.")==0) imaxtrim=8; //asli
			  				
	        						int itrim=sRow.length();
	        						//for(int ilp=0;ilp<imaxtrim-itrim;ilp++) writeAToFile.write(" ");
	        					}
	        					//alldata+=table.getItem(iCnt1).getText(iCnt)+";"; //asli
	        					if (iCnt==0) tes+="\n";
	    		  				tes+=table.getItem(iCnt1).getText(iCnt)+";";
	    		  				//-
	    		  				alldata+=table.getItem(iCnt1).getText(iCnt)+";";
	    		  				//-
	        				}
	        				//writeAToFile.write("\n");
	        				progbr.bar.setSelection(iCnt1+iCntBar);
	        			}
	        			String dtSearch="notam1";
	        			if (!sorig.isEmpty()) dtSearch+="orig";
	        			if (!sserie.isEmpty()) dtSearch+="serie";
	        			if (!msgTypeNtm.isEmpty()) dtSearch+="";

	        			if (dtSearch.compareToIgnoreCase("notam1")==0) {
	        				if (alldata.contains(";;Total;;")) alldata = alldata.replace(";;Total;;", "=Total=");
	        				if (alldata.contains(";;")) alldata = alldata.replace(";;", ";");
	        				if (alldata.contains("=Total=")) alldata = alldata.replace("=Total=", ";Total;");
	        			} else if (dtSearch.compareToIgnoreCase("notam1orig")==0) {
	        				if (alldata.contains(";Total;;;")) alldata = alldata.replace(";Total;;;", "=Total=");
	        				if (alldata.contains(";;")) alldata = alldata.replace(";;", ";");
	        				if (alldata.contains("=Total=")) alldata = alldata.replace("=Total=", ";;Total;");
	        			} else if (dtSearch.compareToIgnoreCase("notam1serie")==0) {
	        				if (alldata.contains(";;Total;;")) { alldata = alldata.replace(";;Total;;", ";;;Total;"); }
	        				
	        				if (alldata.contains(";;;;Total;")) alldata = alldata.replace(";;;;Total;", "=Total=");
	        				if (alldata.contains(";;")) alldata = alldata.replace(";;", ";");
	        				if (alldata.contains("=Total=")) alldata = alldata.replace("=Total=", ";;;Total;");
	        				
	        			} else if (dtSearch.compareToIgnoreCase("notam1origserie")==0) {
	        				if (alldata.contains(";Total;;;")) alldata = alldata.replace(";Total;;;", "=Total=");
	        				if (alldata.contains(";;")) alldata = alldata.replace(";;", ";");
	        				if (alldata.contains("=Total=")) alldata = alldata.replace("=Total=", ";;;Total;");
	        			}
	        			
//	        			// ===== start : setting msgbody for NOTAM Statistic
//	        			if (dtSearch.compareToIgnoreCase("notam1")==0) {
//	        				if (alldata.contains(";;Total;;")) alldata = alldata.replace(";;Total;;", "=Total=");
//	        				if (alldata.contains(";;")) alldata = alldata.replace(";;", ";");
////	        				if (alldata.contains("=Total=")) alldata = alldata.replace("=Total=", ";Total;");
//	        				if (alldata.contains("=Total=")) alldata = alldata.replace("=Total=", "Total;;");
//	        				
//	        			} else if (dtSearch.compareToIgnoreCase("notam1orig")==0) {
//	        				if (alldata.contains("Total;;;;")) alldata = alldata.replace("Total;;;;", ";Total;;;");
//	        				
//	        				if (alldata.contains(";Total;;;")) alldata = alldata.replace(";Total;;;", "=Total=");
//	        				if (alldata.contains(";;")) alldata = alldata.replace(";;", ";");
//	        				if (alldata.contains("=Total=")) alldata = alldata.replace("=Total=", ";;Total;");
//	        			
//	        			} else if (dtSearch.compareToIgnoreCase("notam1serie")==0) {
//	        				if (alldata.contains(";Total;;;;")) { alldata = alldata.replace(";Total;;;;", "=Total="); }
//	        				if (alldata.contains(";;")) alldata = alldata.replace(";;", ";");
//	        				if (alldata.contains("Total;;")) alldata = alldata.replace("Total;;", "Total;;;");
//
//	        			} else if (dtSearch.compareToIgnoreCase("notam1origserie")==0) {
//	        				if (alldata.contains("Total;;;;")) alldata = alldata.replace("Total;;;;", ";Total;;;");
//	        				
//	        				if (alldata.contains(";Total;;;")) alldata = alldata.replace(";Total;;;", "=Total=");
//	        				if (alldata.contains(";;")) alldata = alldata.replace(";;", ";");
//	        				if (alldata.contains("=Total=")) alldata = alldata.replace("=Total=", "Total;;;;");
//	        			}
//	        			// ===== end : setting msgbody for NOTAM Statistic
				  		
	        			System.err.println("dtSearch::" + dtSearch + "#");
//				  		
				  		if (sType.compareToIgnoreCase("pdf")==0) {
				  			//fp.setData("", sTitle, "\n", dtSearch, alldata, "","/tmp/ramdisk0/statisticNOTAM.pdf", "/tmp/ramdisk0/statisticNOTAM.pdf","volmet");
				  			fp.setData("", sTitle, "\n", dtSearch, tes, "","/tmp/ramdisk0/statisticNOTAM.pdf", "/tmp/ramdisk0/statisticNOTAM.pdf","volmet");
				  			try {
//				  				Runtime.getRuntime().exec(jdbc.getPdfCmd() + " /tmp/ramdisk0/statisticNOTAM.pdf");
				  				Runtime.getRuntime().exec(Lists.cmdPDF + " /tmp/ramdisk0/statisticNOTAM.pdf");
					    	}
				  			catch (IOException e) {System.err.println("Error: " + e.getMessage());}
				  		} else if (sType.compareToIgnoreCase("xls")==0) {
				  			writexls.setData(dtSearch, sTitle, tes, "/tmp/ramdisk0/statisticNOTAM.xls");
				  			try {
				  				Runtime.getRuntime().exec(Lists.cmdXLS + " /tmp/ramdisk0/statisticNOTAM.xls");
					    	}
				  			catch (IOException e) {System.err.println("Error: " + e.getMessage());}
				  		}
//				  		writeAToFile.close();
				  		progbr.finish();
	        		}
	        	});
	        }
  		}.start();
		progbr.begin();
  	}
}