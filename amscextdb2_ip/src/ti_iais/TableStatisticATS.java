package ti_iais;

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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import readwrite.ReadFromFile;
import readwrite.WriteToPDF;
import readwrite.WriteToTXT;
import readwrite.WriteToXLS2;
import setting.Shorten;
import setting.Titles;
import actions.cProgressBar;
import displays.AmscSplashScreen2;
import displays.ConnectTo;
import displays.DialogFactory;
import displays.Lists;
import displays.tabs.TabSTATISTIC;


public class TableStatisticATS {
	public static String sID=Titles.stStatistic;
//	ButtonsSetting bs = new ButtonsSetting(); 
	Shorten sh = new Shorten();
	HeaderFooter fp = new HeaderFooter();
	WriteExcel writexls = new WriteExcel();
	
	String tipe="";
	
	Table table;
	TableItem item;
	ResultSet rs;
	String sTypeMsg1[] = {"ALR","RCF","FPL","CHG","CNL","DLA","DEP","ARR","CPL","EST","CDN","ACP","LAM","RQP","RQS","SPL","Total"};//"TOT"};
	String sTypeMsg2[] = {"ALR","RCF","FPL","CHG","CNL","DLA","DEP","ARR","CPL","EST","CDN","ACP","LAM","RQP","RQS","SPL","ABI","MAC","PAC","REJ","TOC","AOC","EMG","MIS","TDM","LRM","TRU","ADS","ASM","FAN","FCN","Total"};//"TOT"};
	String sTypeMsg[];
	Font font;
	Shell shell;
	String year,MonthAts,st_sent;
	String sOrigin;
	//Button cLevel;
	Text tLev,tLevel,tLevel2,tRoute,taircraftid,tdepad,tdestad,tacfttyp,torg;
	Button notdep,notdest,PrintToPDF,PrintToXLS;
	String StRoute;
	String Staircraftid;
	String Stdepad;
	String Stdestad;
	String Stacfttyp;
	String sNO="*NO*";
	String sDisp[][];
	String sST="";
	int g_plus=0;
	int g_plus1=0;
	
	public TableStatisticATS(){
		sOrigin="";
//		sOrigin=getdt("/aftn/InOutbound.txt");
//		sST=getdt1("/aftn/stAIDC.txt");
		sOrigin=getdt("/aed/InOutbound.txt");
		sST=getdt1("/aed/stAIDC.txt");
		if (sST.startsWith("1")) { sTypeMsg=sTypeMsg2; }
		else sTypeMsg=sTypeMsg1;
		
		tLev=TabSTATISTIC.tLev;
		tLevel=TabSTATISTIC.tLevel;
		tLevel2=TabSTATISTIC.tLevel2;
		tRoute=TabSTATISTIC.tRoute;
		taircraftid=TabSTATISTIC.tAircraft;
		tdepad=TabSTATISTIC.tDepAd;
		tdestad=TabSTATISTIC.tDestAd;
		notdep=TabSTATISTIC.notdep;
		notdest=TabSTATISTIC.notdest;
		tacfttyp=TabSTATISTIC.tTypeAircraft;
		StRoute=tRoute.getText();
		Staircraftid=taircraftid.getText();
		Stdepad=tdepad.getText();
		Stdestad=tdestad.getText();
		Stacfttyp=tacfttyp.getText();
		torg=TabSTATISTIC.tOrg;
		sNO="*NO*";
		if (StRoute.isEmpty()) StRoute=sNO;
		if (Staircraftid.isEmpty()) Staircraftid=sNO;
		if (Stdepad.isEmpty()) Stdepad=sNO;
		if (Stdestad.isEmpty()) Stdestad=sNO;
		if (Stacfttyp.isEmpty()) Stacfttyp=sNO;
		
		System.out.println("******aStacfttyp"+Stacfttyp);
		if ( (!TabSTATISTIC.msgType.isEmpty()) && 
				   ((TabSTATISTIC.msgType.compareTo("FPL")==0) 
				|| (TabSTATISTIC.msgType.compareTo("CPL")==0) 
				|| (TabSTATISTIC.msgType.compareTo("ALR")==0)) 
				&& (Stacfttyp.compareTo(sNO)==0)) {
			Stacfttyp=gtAtype();
			System.out.println("******Stacfttyp"+Stacfttyp);
		}
		
		System.out.println("plus:"+gtplus()+" plus1:"+gtplus1());
		g_plus=gtplus();
		g_plus1=gtplus1();
		sDisp = new String[gtplus1()+1][gtplus()+1];
		for (int iS=0;iS<gtplus1()+1;iS++) {
			for (int iS1=0;iS1<gtplus()+1;iS1++) {
				sDisp[iS][iS1]="";
			}
		}
		gtplus2();
	}
	
	public void koneksiDB(Shell pshell,final String pyear, final String pMonthAts,final String msgType,String pst_sent) {
  		//------------------------------------ KONEKSI DB ------------------------------------
		shell=pshell;
		try {
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver).newInstance();
		} catch(ClassNotFoundException c) {
			c.printStackTrace();
		} catch(Exception ex){
			ex.printStackTrace();
		}
		year=pyear;
		MonthAts=pMonthAts;
		st_sent=pst_sent;
		
		final cProgressBar progbr = new cProgressBar("");
		new Thread() {
	        public void run() {
	          if (AmscSplashScreen2.display.isDisposed()) return;
	          AmscSplashScreen2.display.asyncExec(new Runnable() {
		      public void run() {
				try {
					if (msgType.isEmpty()) shell.setText("ATS Messages Statistic");
					else shell.setText("ATS Message Statistic");
				    createContents(shell,msgType);
				    shell.setLayout(new GridLayout());
				    new ReadFromFile().readConfiguration();
				    shell.setLocation(new ReadFromFile().getXstat(),new ReadFromFile().getYstat());
		
					Connection conn = DriverManager.getConnection(ConnectTo.DB_NAME,ConnectTo.DB_USER,ConnectTo.DB_PASS);
					Statement stmt = conn.createStatement();
					String yearMonths="'"+year+"-"+MonthAts+"-";
					String date,date1="0",sTime="00:00:00";
					String sQuery1="";
					
					String sPlus="",sPlus1="";
					if (tLev.getText().compareTo("VFR")==0) {
						sPlus1+=" and type15b like '%VFR%'";
					}
					else {
//						String sLevel = tLevel.getText();
//						String sLevel2 = tLevel2.getText();
//						//FA3 - SM4
//						if (tLev.getText().compareTo("F")==0 || tLev.getText().compareTo("A")==0) {
//							if (sLevel.length()<3) {
//					  			if (sLevel.length()==2) { sLevel="0"+sLevel; }
//					  			else if (sLevel.length()==1) { sLevel="00"+sLevel; }
//					  		}
//							if (sLevel2.length()<3) {
//					  			if (sLevel2.length()==2) { sLevel2="0"+sLevel2; }
//					  			else if (sLevel2.length()==1) { sLevel2="00"+sLevel2; }
//					  		}
//						} else if (tLev.getText().compareTo("S")==0 || tLev.getText().compareTo("M")==0) {
//							if (sLevel.length()<4) {
//					  			if (sLevel.length()==3) { sLevel="0"+sLevel; }
//					  			else if (sLevel.length()==2) { sLevel="00"+sLevel; }
//					  			else if (sLevel.length()==1) { sLevel="000"+sLevel; }
//					  		}
//							if (sLevel2.length()<4) {
//					  			if (sLevel2.length()==3) { sLevel2="0"+sLevel2; }
//					  			else if (sLevel2.length()==2) { sLevel2="00"+sLevel2; }
//					  			else if (sLevel2.length()==1) { sLevel2="000"+sLevel2; }
//					  		}
//						}
//						if ((sLevel.isEmpty()) && (sLevel.isEmpty()) && (!tLev.getText().isEmpty())) {
//							sPlus1+=" and type15b like '%"+tLev.getText()+"%'";
//						} else {
//							if (!sLevel.isEmpty()) {
//								sPlus1+=" and type15b>='"+tLev.getText()+sLevel+"'";
//							}
//							if (!sLevel.isEmpty()) {
//								sPlus1+=" and type15b<='"+tLev.getText()+sLevel2+"'";
//							}
//						}
						
						String scLevel = tLev.getText();
						String t15bFr = tLevel.getText();
						String t15bTo = tLevel2.getText();
						if (scLevel.compareTo("F")==0 || scLevel.compareTo("A")==0) {
							if (t15bFr.length()<3) {
					  			if (t15bFr.length()==2) { t15bFr="0"+t15bFr; }
					  			else if (t15bFr.length()==1) { t15bFr="00"+t15bFr; }
					  		}
							if (t15bTo.length()<3) {
					  			if (t15bTo.length()==2) { t15bTo="0"+t15bTo; }
					  			else if (t15bTo.length()==1) { t15bTo="00"+t15bTo; }
					  		}
						} else if (scLevel.compareTo("S")==0 || scLevel.compareTo("M")==0) {
							if (t15bFr.length()<4) {
					  			if (t15bFr.length()==3) { t15bFr="0"+t15bFr; }
					  			else if (t15bFr.length()==2) { t15bFr="00"+t15bFr; }
					  			else if (t15bFr.length()==1) { t15bFr="000"+t15bFr; }
					  		}
							if (t15bTo.length()<4) {
					  			if (t15bTo.length()==3) { t15bTo="0"+t15bTo; }
					  			else if (t15bTo.length()==2) { t15bTo="00"+t15bTo; }
					  			else if (t15bTo.length()==1) { t15bTo="000"+t15bTo; }
					  		}
						}
						if (scLevel.compareTo("") != 0) sPlus1+=" AND type15b LIKE '"+scLevel+"%'"; 
						if (t15bFr.compareTo("") != 0) sPlus1+=" AND type15b>='"+ scLevel+t15bFr +"'";
						if (t15bTo.compareTo("") != 0) sPlus1+=" AND type15b<='"+ scLevel+t15bTo +"'";
						
						
					}
					if (!tRoute.getText().isEmpty()) {
						String sRoute[] = tRoute.getText().split(",");
						sPlus1+=" and (";
						for (int i=0;i<sRoute.length;i++) {
							if (i!=0) sPlus1+="or";
							sPlus1+=" type15c like '%"+sRoute[i]+"%'";
						}
						sPlus1+=")";
					}
					if (!taircraftid.getText().isEmpty()) {
						String sAcftID[] = taircraftid.getText().split(",");
						sPlus1+=" and (";
						for (int i=0;i<sAcftID.length;i++) {
							if (i!=0) sPlus1+="or";
							sPlus1+=" type7a like '"+sAcftID[i]+"%'";
						}
						sPlus1+=")";
					}
					if (!tdepad.getText().isEmpty()) {
						sPlus1+=" and (";
						String sArrDest[] = tdepad.getText().split(",");
						for (int id=0;id<sArrDest.length;id++) {
							if (id!=0) {
								if (notdep.getSelection()) sPlus1+=" and ";
								else sPlus1+=" or ";
							}
							sPlus1+="type13a ";
							if (notdep.getSelection()) sPlus1+="not";
							sPlus1+=" like";
							sPlus1+=" '"+sArrDest[id]+"%'";
						}
						sPlus1+=")";
					}
					if (!tdestad.getText().isEmpty()) {
						sPlus1+=" and (";
						String sArrDest[] = tdestad.getText().split(",");
						for (int id=0;id<sArrDest.length;id++) {
							if (id!=0) {
								if (notdest.getSelection()) sPlus1+=" and ";
								else sPlus1+=" or ";
							}
							sPlus1+="type16a ";
							if (notdest.getSelection()) sPlus1+="not";
							sPlus1+=" like";
							sPlus1+=" '"+sArrDest[id]+"%'";
						}
						sPlus1+=")";
					}

//					if (!tacfttyp.getText().isEmpty()) {
//						String sAcft[] = tacfttyp.getText().split(",");
//						sPlus+=" and (";
//						for (int i=0;i<sAcft.length;i++) {
//							if (i!=0) sPlus+="or";
//							sPlus+=" type9b like '"+sAcft[i]+"%'";
//						}
//						sPlus+=")";
//					}
					
					if (!torg.getText().isEmpty()) {
						sPlus1+=" and (";
						String sOrg[] = torg.getText().split(",");
						for (int id=0;id<sOrg.length;id++) {
							if (id!=0) {
								sPlus1+=" or ";
							}
							sPlus1+="originator ";
							sPlus1+=" like";
							sPlus1+=" '"+sOrg[id]+"%'";
						}
						sPlus1+=")";
					}

					int i_plus=gtplus();
					int i_plus1=gtplus1();
					if (i_plus1==0) i_plus1=1;
					if (msgType.isEmpty()){
						int iCo=0;
						progbr.create(31+31);
						TableItem item[] = new TableItem[(sTypeMsg.length+1)*i_plus1]; 
						int iCnt1=0;
						int iCntTyp=0;
						for (int iLoop=0;iLoop<(sTypeMsg.length*i_plus1);iLoop++) {
							item[iLoop] = new TableItem (table, SWT.NONE);
							if ((iLoop % i_plus1)==0) {
								item[iLoop].setText(0,sTypeMsg[iCntTyp++]);
//								System.out.println("S["+iLoop+"]"+item[iLoop].getText());
							}
							for (int iLoop1=0;iLoop1<31+i_plus;iLoop1++) {
								if (iLoop1<i_plus) {
//									System.out.println(iLoop+" "+iLoop1+" "+i_plus);
									item[iLoop].setText(iLoop1+1,sDisp[iLoop % i_plus1][iLoop1]);
								}
								else 
									item[iLoop].setText(iLoop1+1,"0");
								progbr.bar.setSelection(iLoop);
							}
							iCnt1=iLoop;
							if (iLoop==sTypeMsg.length*i_plus1) {
								for (int i=0;i<i_plus;i++)
								item[iLoop].setText(i+1,"");
								break;
							}
						}
						int iCnt2=0;
						int totalH[]=new int[(sTypeMsg.length*i_plus1)+1];
						for (int iLoop=1;iLoop<32;iLoop++) {
							sPlus="";
							int totalV=0;
							String sQuery="select type3a,count(id_ats)as count from air_message"+year+"_"+MonthAts;
							sQuery+=" where tgl>";
							date=Integer.toString(iLoop);
							date1="0";
							if (date.length()==1) date1+=date;
							else date1=date;
							sQuery+=yearMonths+date1+" "+sTime+"'";
			
							if (iLoop==31) {
								date=Integer.toString(1);//1st
								int imonth = Integer.parseInt(MonthAts)+1;
								if (imonth<13) {
									String months0="0";
									String sMonths = Integer.toString(imonth);
									yearMonths = "'"+year+"-";
									if (sMonths.length()==1) yearMonths += (months0+sMonths);
									else yearMonths += sMonths;
									yearMonths+="-";
								}
								else {
									int iyear = Integer.parseInt(year)+1;
									yearMonths = "'"+Integer.toString(iyear)+"-"+"01"+"-";
								}
							}
							else date=Integer.toString(iLoop+1);
							date1="0";
							if (date.length()==1) date1+=date;
							else date1=date;
							sQuery+=" and tgl<"+yearMonths+date1+" "+sTime+"'";
							if (!st_sent.isEmpty()) {
								if (st_sent.compareTo("1")==0) sQuery+=" and originator like '"+sOrigin+"%'";
								else if (st_sent.compareTo("0")==0) sQuery+=" and originator not like '"+sOrigin+"%'";
							}
							iCo=0;
							String sQuery2=sQuery;
//							String A_taircraftid[] = Staircraftid.split(",");
//							for (int iL=0;iL<A_taircraftid.length;iL++) {
//								String A_tRoute[] = StRoute.split(",");
//								for (int iL1=0;iL1<A_tRoute.length;iL1++) {
//									String A_tdepad[] = Stdepad.split(",");
//									for (int iL2=0;iL2<A_tdepad.length;iL2++) {
//										String A_tdestad[] = Stdestad.split(",");
//										for (int iL3=0;iL3<A_tdestad.length;iL3++) {
											String A_tacfttyp[] = Stacfttyp.split(",");
											for (int iL4=0;iL4<A_tacfttyp.length;iL4++) {
												boolean flg=false;
//												if (A_taircraftid[iL].compareTo(sNO)!=0) {
//													sPlus+=" and (";
//													sPlus+="type7a like '"+A_taircraftid[iL]+"%')";
//													flg=true;
//												}
//												if (A_tRoute[iL1].compareTo(sNO)!=0) {
//													sPlus+=" and (";
//													sPlus+="type15c like '"+A_tRoute[iL1]+"%')";
//													flg=true;
//												}
//
//												if (A_tdepad[iL2].compareTo(sNO)!=0) {
//													String sNo="";
//													if (notdep.getSelection()) sNo="not";
//													sPlus+=" and (";
//													sPlus+="type13a "+sNo+" like '"+A_tdepad[iL2]+"%')";
//													flg=true;
//												}
//												
//												if (A_tdestad[iL3].compareTo(sNO)!=0) {
//													String sNo="";
//													if (notdest.getSelection()) sNo="not";
//													sPlus+=" and (";
//													sPlus+="type16a "+sNo+" like '"+A_tdestad[iL3]+"%')";
//													flg=true;
//												}
												
												if (A_tacfttyp[iL4].compareTo(sNO)!=0) {
													sPlus+=" and (";
													sPlus+="type9b like '"+A_tacfttyp[iL4]+"%')";
													flg=true;
												}
												sQuery=sQuery2;
												sQuery+=sPlus+sPlus1+" group by type3a";
												sPlus="";
												System.out.println(sQuery+sQuery1);
												ResultSet rs = stmt.executeQuery(sQuery+sQuery1);
												while (rs.next()) {
													System.out.println(i_plus1+" "+rs.getString("type3a")+":"+rs.getString("count"));
													if ((rs.getString("count")!=null) && (rs.getString("type3a")!=null)) {
														for (int iCnt=0;iCnt<sTypeMsg.length;iCnt++) {
															if (rs.getString("type3a").compareTo(sTypeMsg[iCnt])==0) {
																item[(iCnt*i_plus1)+iCo].setText(iLoop+i_plus,rs.getString("count"));
																totalV+=Integer.parseInt(rs.getString("count"));
																totalH[iCnt]+=Integer.parseInt(rs.getString("count"));
																break;
															}
														}
													}
												}
												item[(sTypeMsg.length-1)*i_plus1].setText(iLoop+i_plus,Integer.toString(totalV));
												rs.close();
												if (flg) iCo++;
											}
//										}
//									}
//								}
//							}
							
							//System.out.println("sPlus:"+sPlus);
//							sQuery+=sPlus+" group by type3a";
//							System.out.println(sQuery+sQuery1);
//							ResultSet rs = stmt.executeQuery(sQuery+sQuery1);
//							while (rs.next()) {
//								//System.out.println(rs.getString("count")+rs.getString("type3a"));
//								if ((rs.getString("count")!=null) && (rs.getString("type3a")!=null)) {
//									for (int iCnt=0;iCnt<16;iCnt++) {
//										if (rs.getString("type3a").compareTo(sTypeMsg[iCnt])==0) {
//											item[iCnt].setText(iLoop,rs.getString("count"));
//											totalV+=Integer.parseInt(rs.getString("count"));
//											totalH[iCnt]+=Integer.parseInt(rs.getString("count"));
//											break;
//										}
//									}
//								}
//							}
//							rs.close();
							//item[16].setText(iLoop,Integer.toString(totalV));
							progbr.bar.setSelection(iLoop+iCnt1);
							iCnt2=iLoop;
							//break;
						}
						for (int iTy=0;iTy<sTypeMsg.length*i_plus1;iTy++) {
							//for (int iLoopT=0;iLoopT<iCo;iLoopT++) {
								int iTot=0;
								for (int iLoop=g_plus;iLoop<31+g_plus;iLoop++) {
									//System.out.println("iLoop:"+iLoop+1);
									iTot+=Integer.parseInt(table.getItem(iTy).getText(iLoop+1));
								}
								item[iTy].setText(32+g_plus,Integer.toString(iTot));
							//}
						}

//						int totalSub=0;
//						for (int iLoop1=0;iLoop1<16*i_plus1;iLoop1++) {
//							//item[iLoop1].setText(32+i_plus,Integer.toString(totalH[iLoop1]));
//							totalSub+=totalH[iLoop1];
//							progbr.bar.setSelection(iLoop1+iCnt1+iCnt2);
//						}
//						//item[16*i_plus1].setText(32+i_plus,Integer.toString(totalSub));
//						progbr.finish();
						System.out.println("finished");
					}
					else {
						progbr.create(30+31+30);
						int iCntTyp=0;
						int iCnt=0;

						int iItem;
						if (g_plus1>0) iItem=1;
						else iItem=0;
						TableItem item[] = new TableItem[i_plus1+iItem]; 
						for (int iLoop=0;iLoop<i_plus1+iItem;iLoop++) {
							item[iLoop] = new TableItem (table, SWT.NONE);
							if ((iLoop % i_plus1)==0) {
								item[0].setText(0,msgType);
							}
							for (int iLoop1=0;iLoop1<31+i_plus;iLoop1++) {
								if (iLoop1<i_plus) {
									item[iLoop].setText(iLoop1+1,sDisp[iLoop][iLoop1]);
								}
								else 
									item[iLoop].setText(iLoop1+1,"0");
								progbr.bar.setSelection(iLoop);
								iCnt=iLoop1;
							}
							if (g_plus1>0) {
								if (iLoop==i_plus1) item[iLoop].setText(1,"Total"); //TOT
							}
						}

//						TableItem item = new TableItem (table, SWT.NONE);
//						item.setText(0,msgType);
//						int iCnt=0;
//						for (int iLoop1=0;iLoop1<31;iLoop1++) {
//							item.setText(iLoop1+1,"0");
//							progbr.bar.setSelection(iLoop1);
//							iCnt=iLoop1;
//						}
						int iCnt1=0;
						int iCo=0;
						for (int iLoop=1;iLoop<32;iLoop++) {
							String sQuery="select type3a,count(id_ats)as count from air_message"+year+"_"+MonthAts;
							sQuery+=" where tgl>";
							date=Integer.toString(iLoop);
							date1="0";
							if (date.length()==1) date1+=date;
							else date1=date;
							sQuery+=yearMonths+date1+" "+sTime+"'";
			
							if (iLoop==31) {
								date=Integer.toString(1);//1st
								int imonth = Integer.parseInt(MonthAts)+1;
								if (imonth<13) {
									String months0="0";
									String sMonths = Integer.toString(imonth);
									yearMonths = "'"+year+"-";
									if (sMonths.length()==1) yearMonths += (months0+sMonths);
									else yearMonths += sMonths;
									yearMonths+="-";
								}
								else {
									int iyear = Integer.parseInt(year)+1;
									yearMonths = "'"+Integer.toString(iyear)+"-"+"01"+"-";
								}
							}
							else date=Integer.toString(iLoop+1);
							date1="0";
							if (date.length()==1) date1+=date;
							else date1=date;
							sQuery+=" and tgl<"+yearMonths+date1+" "+sTime+"'";
							sQuery+=" and type3a='"+msgType+"'";
							if (!st_sent.isEmpty()) {
								if (st_sent.compareTo("1")==0) sQuery+=" and originator like '"+sOrigin+"%'";
								else if (st_sent.compareTo("0")==0) sQuery+=" and originator not like '"+sOrigin+"%'";
							}
							//System.out.println("sPlus:"+sPlus);

							String sQuery2=sQuery;
							iCo=0;
//							String A_taircraftid[] = Staircraftid.split(",");
//							for (int iL=0;iL<A_taircraftid.length;iL++) {
//								String A_tRoute[] = StRoute.split(",");
//								for (int iL1=0;iL1<A_tRoute.length;iL1++) {
//									String A_tdepad[] = Stdepad.split(",");
//									for (int iL2=0;iL2<A_tdepad.length;iL2++) {
//										String A_tdestad[] = Stdestad.split(",");
//										for (int iL3=0;iL3<A_tdestad.length;iL3++) {
											String A_tacfttyp[] = Stacfttyp.split(",");
											for (int iL4=0;iL4<A_tacfttyp.length;iL4++) {
//												if (A_taircraftid[iL].compareTo(sNO)!=0) {
//													sPlus+=" and (";
//													sPlus+="type7a like '"+A_taircraftid[iL]+"%')";
//												}
//												if (A_tRoute[iL1].compareTo(sNO)!=0) {
//													sPlus+=" and (";
//													sPlus+="type15c like '"+A_tRoute[iL1]+"%')";
//												}
//
//												if (A_tdepad[iL2].compareTo(sNO)!=0) {
//													String sNo="";
//													if (notdep.getSelection()) sNo="not";
//													sPlus+=" and (";
//													sPlus+="type13a "+sNo+" like '"+A_tdepad[iL2]+"%')";
//												}
//												
//												if (A_tdestad[iL3].compareTo(sNO)!=0) {
//													String sNo="";
//													if (notdest.getSelection()) sNo="not";
//													sPlus+=" and (";
//													sPlus+="type16a "+sNo+" like '"+A_tdestad[iL3]+"%')";
//												}
												
												if (A_tacfttyp[iL4].compareTo(sNO)!=0) {
													sPlus+=" and (";
													sPlus+="type9b like '"+A_tacfttyp[iL4]+"%')";
												}
												sQuery=sQuery2;
												sQuery+=sPlus+sPlus1+" group by type3a";
												sPlus="";
												System.out.println(sQuery+sQuery1);
												ResultSet rs = stmt.executeQuery(sQuery+sQuery1);
												if (rs.next()) {
													System.out.println("res:"+rs.getString("type3a")+rs.getString("count"));
													if ((rs.getString("count")!=null) && (rs.getString("type3a")!=null)) {
														item[iCo].setText(iLoop+g_plus,rs.getString("count"));
													}
												}
												else item[iCo].setText(iLoop+g_plus,"0");
												rs.close();
												iCo++;
											}
//										}
//									}
//								}
//							}
//							sQuery+=sPlus+" group by type3a";
//							System.out.println(sQuery+sQuery1);
//							ResultSet rs = stmt.executeQuery(sQuery+sQuery1);
//							while (rs.next()) {
//								System.out.println("res:"+rs.getString("type3a")+rs.getString("count"));
//								if ((rs.getString("count")!=null) && (rs.getString("type3a")!=null)) {
//									item.setText(iLoop,rs.getString("count"));
//								}
//							}
//							rs.close();
							progbr.bar.setSelection(iLoop+iCnt);
							iCnt1=iLoop;
						}
						int iTot=0;
						System.out.println("iCo:"+iCo+" g_plus:"+g_plus);

						int iTemp=0;
						for (int iLoopT=0;iLoopT<iCo;iLoopT++) {
							iTot=0;
							for (int iLoop=g_plus;iLoop<31+g_plus;iLoop++) {
								//System.out.println("iLoop:"+iLoop+1);
								iTot+=Integer.parseInt(table.getItem(iLoopT).getText(iLoop+1));
								progbr.bar.setSelection(iLoop+iCnt+iCnt1);
							}
							item[iLoopT].setText(32+g_plus,Integer.toString(iTot));
							iTemp=iLoopT;
						}

						if (g_plus1>0) {
						for (int iLoop=g_plus;iLoop<32+g_plus;iLoop++) {
							iTot=0;
							for (int iLoopT=0;iLoopT<iCo;iLoopT++) {
								//System.out.println("iLoop:"+iLoop+1);
								iTot+=Integer.parseInt(table.getItem(iLoopT).getText(iLoop+1));
								progbr.bar.setSelection(iLoop+iCnt+iCnt1);
							}
							item[iTemp+1].setText(iLoop+1,Integer.toString(iTot));
						}
						}
					}
					stmt.close();
					conn.close();
					progbr.finish();
				    shell.pack();
				    shell.open();
				}
				catch (SQLException s) {
					DialogFactory.openInfoDialog("Search Statistic Messages","There's no data !!");
					progbr.finish();
					//table.dispose();
					//PrintToPDF.dispose();
					shell.dispose();
//					s.printStackTrace();
					System.out.println(s.getMessage());
//					if (s.getMessage().contains("doesn't exist")) {
//						MessageBox mb = new MessageBox(new Shell());
//						mb.setMessage("No Data\n");
//						mb.setText("Warning!!!");
//						mb.open();
//					}
//					progbr.finish();
				}
		      }
		    });
	       }
	    }.start();
	    progbr.begin();
  	}
  	
  	private void createContents(Composite composite,final String msgType) {
	    // Create the table
	    font = new Font(AmscSplashScreen2.display, "Courier", 10, SWT.BOLD);
	    table = new Table (composite, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION); sh.tableStyle(table, true, true, 270);
		table.setFont(font);
		
	    // Create each of the columns, adding an event listener 
	    // that will set the appropriate fields into the comparatorATS 
	    // and then call the fillTable helper method
		final TableColumn Type = new TableColumn(table,SWT.LEFT);
		Type.setText("Msg\nTyp"); // Type
		Type.setWidth(50);
		Type.setResizable(false);
		Type.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
			}
		});
		
		TableColumn plus[] = new TableColumn[gtplus()];
		for (int iCnt=0;iCnt<gtplus();iCnt++) {
			plus[iCnt]=new TableColumn(table,SWT.LEFT);
			plus[iCnt].setText("Type\nAcft"); //A. TYPE
			plus[iCnt].setWidth(50);
		}
		
		TableColumn tgl[] = new TableColumn[31];
		for (int iCnt=0;iCnt<31;iCnt++) {
			tgl[iCnt]=new TableColumn(table,SWT.RIGHT);
			tgl[iCnt].setText(Integer.toString(iCnt+1));
			tgl[iCnt].setWidth(35);//40);
		}
		
		final TableColumn Total = new TableColumn(table,SWT.RIGHT);
		Total.setText("SubTot");//("Sub Total");//SUB TOT.");
		Total.setWidth(60);//80);
		Total.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
			}
		});
	    //---------------------------------------------------------
		Composite typeB = new Composite(composite, SWT.NONE); sh.composeStyle(typeB, 3, SWT.CENTER, SWT.CENTER);
		PrintToPDF = new Button(typeB, SWT.PUSH); 
		PrintToPDF.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String sTitle="ATS ";
				if (msgType.isEmpty()) { sTitle+="Messages Statistic: "; tipe="";}
				else { sTitle+="Message Statistic: "; tipe=msgType; }
				
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
				sTitle+=" "+year;
				
//				if (st_sent.compareTo("")!=0) {
//					sTitle+="\nStatus: ";
//					if (st_sent.compareTo("1")==0) sTitle+="Sent";
//					else sTitle+="Received";
//				}
				//new PrintKTable(table,XuxaMain.display,sTitle,"ATS Statistic Message Preview");
				printToFile(sTitle,"pdf");
			}
		});
		
		PrintToXLS = new Button(typeB, SWT.PUSH);
		PrintToXLS.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String sTitle="ATS ";
				if (msgType.isEmpty()) { sTitle+="Messages Statistic: "; tipe="";}
				else { sTitle+="Message Statistic: "; tipe=msgType; }
				
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
				sTitle+=" "+year;
				
//				if (st_sent.compareTo("")!=0) {
//					sTitle+="\nStatus: ";
//					if (st_sent.compareTo("1")==0) sTitle+="Sent";
//					else sTitle+="Received";
//				}
				//new PrintKTable(table,XuxaMain.display,sTitle,"ATS Statistic Message Preview");
				printToFile(sTitle,"xls");
			}
		});
		
		Button bClose = new Button(typeB, SWT.PUSH); sh.buttonStyle(bClose, "Close", "", 150, sh.DarkGrey, SWT.CENTER, SWT.CENTER, null); 
		bClose.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close(); } });
		
		sh.buttonStyle(PrintToPDF, "Export to PDF", "", 150, sh.DarkGrey, SWT.CENTER, SWT.CENTER, null);
		sh.buttonStyle(PrintToXLS, "Export to XLS", "", 150, sh.DarkGrey, SWT.CENTER, SWT.CENTER, null);
//		bs.Export(typeB, PrintToPDF, PrintToXLS);
//		label = new Label(typeB,SWT.LEFT);
//		GridData gd2 = new GridData();
//		gd2.widthHint = 570; 
//		label.setLayoutData(gd2);
	}
  	
  	void printToFile(final String sTitle,final String sType) {
		final cProgressBar progbr = new cProgressBar("");
		new Thread() {
	        public void run() {
	          if (AmscSplashScreen2.display.isDisposed()) return;
		      AmscSplashScreen2.display.asyncExec(new Runnable() {
		      public void run() {
				progbr.create(table.getColumnCount()+table.getItemCount()+1);
				System.out.println("tclm:"+table.getColumnCount()+" trow:"+table.getItemCount());
		  		
				WriteToTXT writeAToFile = new WriteToTXT(); //WriteToAFile writeAToFile = new WriteToAFile(); 
		  		writeAToFile.open("/tmp/ramdisk0/statistic.txt");
  				writeAToFile.write(sTitle+"\n\n");

				int iCntBar=0;
				for (int iCnt=0;iCnt<table.getColumnCount();iCnt++) {
					writeAToFile.write(table.getColumn(iCnt).getText());
		  			int imaxtrim=6;
		  			int itrim=table.getColumn(iCnt).getText().length();
		  			for(int ilp=0;ilp<imaxtrim-itrim;ilp++) {
		  				writeAToFile.write(" ");
		  			}
					progbr.bar.setSelection(iCnt);
					iCntBar=iCnt;
		  		}
  				writeAToFile.write("\n\n");
				
  				String alldata="",tes="";
		  		for (int iCnt1=0;iCnt1<table.getItemCount();iCnt1++) {
		  			for (int iCnt=0;iCnt<table.getColumnCount();iCnt++) {
		  				writeAToFile.write(table.getItem(iCnt1).getText(iCnt));
		  				if (iCnt==0) tes+="\n";
		  				tes+=table.getItem(iCnt1).getText(iCnt)+";";
		  				//-
		  				alldata+=table.getItem(iCnt1).getText(iCnt)+";";
		  				//-
		  				int imaxtrim=6;
		  				int itrim=table.getItem(iCnt1).getText(iCnt).length();
		  				for(int ilp=0;ilp<imaxtrim-itrim;ilp++) {
		  					writeAToFile.write(" ");
		  				}
		  			}
		  			writeAToFile.write("\n");
					progbr.bar.setSelection(iCntBar+iCnt1);
		  		}
		  		String dtSearch="";
		  		
		  		if (tipe.compareToIgnoreCase("alr")==0 || tipe.compareToIgnoreCase("fpl")==0 || tipe.compareToIgnoreCase("cpl")==0) dtSearch="sats1";
		  		else if (!TabSTATISTIC.tTypeAircraft.getText().isEmpty()) {
		  			dtSearch="sats1";
		  		}
		  		else dtSearch="sats";
		  		
		  		if (sType.compareToIgnoreCase("pdf")==0) {
		  			fp.setData("", sTitle, "\n", dtSearch, alldata, "","/tmp/ramdisk0/statisticATS.pdf", "/tmp/ramdisk0/statisticATS.pdf","volmet");
		  			try {
//		  				Runtime.getRuntime().exec(jdbc.getPdfCmd() + " /tmp/ramdisk0/statisticATS.pdf");
		  				Runtime.getRuntime().exec(Lists.cmdPDF + " /tmp/ramdisk0/statisticATS.pdf");
			    	}
		  			catch (IOException e) {System.err.println("Error: " + e.getMessage());}
		  		} else if (sType.compareToIgnoreCase("xls")==0) {
		  			//new WriteToXLS2(sID+" List", "msg"+Lists.getFlnm(sID), tes, "", Lists.getFlnm(sID), "");
//		  			new RuntimeCmd(Lists.cmdXLS, MainForm.path+Lists.getFlnm(sID)+".xls");
		  			writexls.setData(dtSearch, sTitle, tes, "/tmp/ramdisk0/statisticATS.xls");
		  			try {
//		  				Runtime.getRuntime().exec(jdbc.getXlsCmd() + " /tmp/ramdisk0/statisticATS.xls");
		  				Runtime.getRuntime().exec(Lists.cmdXLS + " /tmp/ramdisk0/statisticATS.xls");
			    	}
		  			catch (IOException e) {System.err.println("Error: " + e.getMessage());}
		  		}
		  		writeAToFile.close();
		    	progbr.finish();
		      }
		    });
	       }
	    }.start();
	    progbr.begin();
  	}
  	
	public static String getdt(String fname) {
		String sOrg="";
        try {
            File myFile = new File(fname);
            FileReader fileReader = new FileReader(myFile);
            BufferedReader buffReader = new BufferedReader(fileReader);
            String buf = null;
            String[] subbuf1 = new String[100];

            while ((buf = buffReader.readLine()) != null) {
                if (!buf.startsWith("#")) {
                    subbuf1 = buf.split("=");
                    System.out.println("originator:" + subbuf1[1]);
                    sOrg = subbuf1[1];
                }            }
            buffReader.close();
        } catch (Exception err) {
            System.out.println("err" + err);
        }
        return sOrg;
    }

	static String getdt1(String fname) {
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

	private int gtplus(){
		int i_plus=0;
//		String A_taircraftid[] = Staircraftid.split(",");
//		for (int iL=0;iL<A_taircraftid.length;iL++) {
//			String A_tRoute[] = StRoute.split(",");
//			for (int iL1=0;iL1<A_tRoute.length;iL1++) {
//				String A_tdepad[] = Stdepad.split(",");
//				for (int iL2=0;iL2<A_tdepad.length;iL2++) {
//					String A_tdestad[] = Stdestad.split(",");
//					for (int iL3=0;iL3<A_tdestad.length;iL3++) {
						String A_tacfttyp[] = Stacfttyp.split(",");
						for (int iL4=0;iL4<A_tacfttyp.length;iL4++) {
//							if (A_taircraftid[iL].compareTo(sNO)!=0) i_plus++;
//							if (A_tRoute[iL1].compareTo(sNO)!=0) i_plus++;
//							if (A_tdepad[iL2].compareTo(sNO)!=0) i_plus++;
//							if (A_tdestad[iL3].compareTo(sNO)!=0) i_plus++;
							if (A_tacfttyp[iL4].compareTo(sNO)!=0) i_plus++;
							return i_plus;
						}
//					}
//				}
//			}
//		}
		return i_plus;
	}

	private int gtplus1(){
		int i_plus=0;
//		String A_taircraftid[] = Staircraftid.split(",");
//		for (int iL=0;iL<A_taircraftid.length;iL++) {
//			String A_tRoute[] = StRoute.split(",");
//			for (int iL1=0;iL1<A_tRoute.length;iL1++) {
//				String A_tdepad[] = Stdepad.split(",");
//				for (int iL2=0;iL2<A_tdepad.length;iL2++) {
//					String A_tdestad[] = Stdestad.split(",");
//					for (int iL3=0;iL3<A_tdestad.length;iL3++) {
						String A_tacfttyp[] = Stacfttyp.split(",");
						for (int iL4=0;iL4<A_tacfttyp.length;iL4++) {
							boolean flg=false;
//							if (A_taircraftid[iL].compareTo(sNO)!=0) flg=true;
//							if (A_tRoute[iL1].compareTo(sNO)!=0) flg=true;
//							if (A_tdepad[iL2].compareTo(sNO)!=0) flg=true;
//							if (A_tdestad[iL3].compareTo(sNO)!=0) flg=true;
							if (A_tacfttyp[iL4].compareTo(sNO)!=0) flg=true;
							if (flg) i_plus++;
						}
					//}
//				}
//			}
//		}
		return i_plus;
	}

	private int gtplus2(){
		int i_plus=0;
//		String A_taircraftid[] = Staircraftid.split(",");
//		for (int iL=0;iL<A_taircraftid.length;iL++) {
//			String A_tRoute[] = StRoute.split(",");
//			for (int iL1=0;iL1<A_tRoute.length;iL1++) {
//				String A_tdepad[] = Stdepad.split(",");
//				for (int iL2=0;iL2<A_tdepad.length;iL2++) {
//					String A_tdestad[] = Stdestad.split(",");
//					for (int iL3=0;iL3<A_tdestad.length;iL3++) {
						String A_tacfttyp[] = Stacfttyp.split(",");
						for (int iL4=0;iL4<A_tacfttyp.length;iL4++) {
							System.out.println("i_plus:"+i_plus);
							boolean flg=false;
							int iCntR=0;
//							if (A_taircraftid[iL].compareTo(sNO)!=0) {
//								sDisp[i_plus][iCntR++]=A_taircraftid[iL];
//								flg=true;
//							}
//							if (A_tRoute[iL1].compareTo(sNO)!=0) {
//								sDisp[i_plus][iCntR++]=A_tRoute[iL1];
//								flg=true;
//							}
//							if (A_tdepad[iL2].compareTo(sNO)!=0) {
//								sDisp[i_plus][iCntR++]=A_tdepad[iL2];
//								flg=true;
//							}
//							if (A_tdestad[iL3].compareTo(sNO)!=0) {
//								sDisp[i_plus][iCntR++]=A_tdestad[iL3];
//								flg=true;
//							}
							if (A_tacfttyp[iL4].compareTo(sNO)!=0) {
								sDisp[i_plus][iCntR++]=A_tacfttyp[iL4];
								flg=true;
							}
							if (flg) i_plus++;
						}
//					}
//				}
//			}
//		}
		for (int iS=0;iS<gtplus1()+1;iS++) {
			for (int iS1=0;iS1<gtplus()+1;iS1++) {
//				System.out.println("sDisp["+iS+"]["+iS1+"]:"+sDisp[iS][iS1]);
			}
		}

		return i_plus;
	}
	
	private String gtAtype(){
		String sRet="";
		try{
			String sQuery="";
			sQuery="select type9b from air_message"+TabSTATISTIC.yyear+"_"+TabSTATISTIC.mmonth;
			if (!TabSTATISTIC.msgType.isEmpty()) sQuery+=" where type3a='"+TabSTATISTIC.msgType+"'";
			sQuery+=" group by type9b";
			Connection conn = DriverManager.getConnection(ConnectTo.DB_NAME,ConnectTo.DB_USER,ConnectTo.DB_PASS);
			Statement stmt = conn.createStatement();
			System.out.println(sQuery);
			ResultSet rs = stmt.executeQuery(sQuery);
			while (rs.next()) {
				if (rs.getString("type9b")!=null) {
					if (!sRet.isEmpty()) sRet+=",";
					sRet+=rs.getString("type9b");
				}
			}
			rs.close();
		}
		catch(SQLException e){
			System.err.println(e.getMessage());
		};
		return sRet;
	}
}

