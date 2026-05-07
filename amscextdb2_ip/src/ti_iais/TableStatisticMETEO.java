package ti_iais;

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
import displays.tabs.TabSTATISTIC;

import readwrite.ReadFromFile;
import readwrite.WriteToTXT;
import setting.Shorten;


public class TableStatisticMETEO {
	HeaderFooter fp = new HeaderFooter();
//	ButtonsSetting bs = new ButtonsSetting();
	WriteExcel writexls = new WriteExcel();
	Shorten sh = new Shorten();
	
	Table table;
	Text Month,Year,torg;
	Combo month;
	Button alr,rcf,fpl,dla,chg,cnl,dep,arr,cdn,cpl,est,acp,lam,send_m,receive,PrintToPDF,PrintToXLS;
	String select,select2;
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
	String year,MonthAts,msgType;
	Font font;
	int tot;
	String st_sent;

	static String loc;
	Shell shell;
//	static PrintAction printExec = new PrintAction();
	boolean flg=false;
	
	int iLoop;
	String sOrigin;
	public TableStatisticMETEO(){
		sOrigin="";
//		sOrigin=TableStatisticATS.getdt("/aftn/InOutbound.txt");
		sOrigin=TableStatisticATS.getdt("/aed/InOutbound.txt");
		torg=TabSTATISTIC.tOrg;

	}

	public void METEO_STATISTIC(Shell pshell,String pyear, String pMonthAts, String pmsgType, String pst_sent, String loc) {
		shell=pshell;
		year=pyear;
		MonthAts=pMonthAts;
		msgType=pmsgType;
		st_sent=pst_sent;
		this.loc=loc;
		
	    if (loc.isEmpty()) shell.setText("Metar Messages Statistic");
	    else shell.setText("Metar Message Statistic");
		shell.setLayout(new GridLayout());
	    new ReadFromFile().readConfiguration();
	    shell.setLocation(new ReadFromFile().getXstat(),new ReadFromFile().getYstat());
	    
	    font = new Font(AmscSplashScreen2.display, "Courier", 10, SWT.BOLD);
	    table = new Table (shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION); sh.tableStyle(table, true, true, 270);
		table.setFont(font);
		
		TableColumn LocationT = new TableColumn(table,SWT.LEFT);
		LocationT.setText("Loca.");
		LocationT.setToolTipText("Location");
		LocationT.setWidth(50);
		
		TableColumn tgl[] = new TableColumn[31];
		for (int iCnt=0;iCnt<31;iCnt++) {
			tgl[iCnt]=new TableColumn(table,SWT.RIGHT);
			tgl[iCnt].setText(Integer.toString(iCnt+1));
			tgl[iCnt].setWidth(35);
		}
		
		TableColumn total = new TableColumn(table,SWT.RIGHT);
		total.setText("SubTot");
		total.setWidth(60);
		
	  	koneksiDB();

		//---------------------------------------------------------
	  	Composite typeB = new Composite(shell, SWT.NONE); sh.composeStyle(typeB, 3, SWT.CENTER, SWT.CENTER);
		PrintToPDF = new Button(typeB, SWT.PUSH);
		PrintToPDF.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String sTitle="Metar ";
				if (TableStatisticMETEO.loc.isEmpty()) { sTitle+="Messages Statistic: "; }
				else { sTitle+="Message Statistic: "; }
				
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
				printToFile(sTitle,"pdf");
			}
		});
		
		PrintToXLS= new Button(typeB, SWT.PUSH);
		PrintToXLS.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String sTitle="Metar ";
				if (TableStatisticMETEO.loc.isEmpty()) { sTitle+="Messages Statistic: "; }
				else { sTitle+="Message Statistic: "; }
				
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
					String yearMonths="'"+year+"-"+MonthAts+"-";
					String date,date1="0",sTime="00:00:00";
					String sQuery1="";

					String sQueryLoc="select location from meteo_metar"+year+"_"+MonthAts+" where location !='' ";
					if (loc.compareTo("")!=0) {
						String sArr[] = loc.split(",");
						for (int i=0;i<sArr.length;i++) {
							if (i==0) sQueryLoc+=" and (";
							else sQueryLoc+=" or ";
							sQueryLoc+="location='"+sArr[i]+"' ";
							if (i==sArr.length-1) sQueryLoc+=")";
						}
					}
					sQueryLoc+="and tgl >="+yearMonths+"01' ";
					int imonths = Integer.parseInt(MonthAts)+1;
					String yearMonths1="";
					if (imonths<13) {
						String months0="0";
						String sMonths = Integer.toString(imonths);
						yearMonths1 = "'"+year+"-";
						if (sMonths.length()==1) yearMonths1 += (months0+sMonths);
						else yearMonths1 += sMonths;
						yearMonths1+="-";
					}
					else {
						int iyear = Integer.parseInt(year)+1;
						yearMonths1 = "'"+Integer.toString(iyear)+"-"+"01"+"-";
					}
					sQueryLoc+="and tgl < "+yearMonths1+"01' ";
					if (!st_sent.isEmpty()) {
						if (st_sent.compareTo("1")==0) sQueryLoc+=" and originator like '"+sOrigin+"%'";
						else if (st_sent.compareTo("0")==0) sQueryLoc+=" and originator not like '"+sOrigin+"%'";
					}
					sQueryLoc+=" group by location";
					System.out.println(sQueryLoc);
					ResultSet rs2 = stmt.executeQuery(sQueryLoc);
					
					rs2.last();
					tot=rs2.getRow();
					System.out.println(tot+" rows");
					progbr.create(31+(2*tot));
					
					TableItem item[] = new TableItem[tot+1]; 
					String sAl[] = new String[tot+1];
					int cnt=0;
					rs2.beforeFirst();
					while (rs2.next()) {
						if (rs2.getString("location")!=null){
							item[cnt] = new TableItem (table, SWT.NONE);
							sAl[cnt]=(rs2.getString("location"));
							item[cnt].setText(0,sAl[cnt]);
							for (int iLp=0;iLp<31;iLp++) item[cnt].setText(iLp+1,"0"); 
							progbr.bar.setSelection(cnt);
						}
						else {sAl[cnt]="";}
						//System.out.println(cnt+sAl[cnt]);
						cnt++;
					}		
					item[cnt] = new TableItem (table, SWT.NONE);
					item[cnt].setText(0,"Total");
					
					int totalH[]=new int[tot];
					for (iLoop=1;iLoop<32;iLoop++) {
						int totalV=0;
						String sQuery="select location,count(id_metar)as count from meteo_metar"+year+"_"+MonthAts+" where tgl>";
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
						
						if (!torg.getText().isEmpty()) {
							sQuery+=" and (";
							String sOrg[] = torg.getText().split(",");
							for (int id=0;id<sOrg.length;id++) {
								if (id!=0) {
									sQuery+=" or ";
								}
								sQuery+="originator ";
								sQuery+=" like";
								sQuery+=" '"+sOrg[id]+"%'";
							}
							sQuery+=")";
						}

						sQuery+=" group by location";
						System.out.println(sQuery+sQuery1);
						ResultSet rs = stmt.executeQuery(sQuery+sQuery1);
						while (rs.next()) {
							///System.out.println("count"+rs.getString("count")+rs.getString("location"));
							if ((rs.getString("count")!=null) && (rs.getString("location")!=null)) {
								for (int iCnt=0;iCnt<tot;iCnt++) {
									if (rs.getString("location").compareTo(sAl[iCnt])==0) {
										///System.out.println(iCnt+sAl[iCnt]+" "+rs.getString("count"));
										item[iCnt].setText(iLoop,rs.getString("count"));
										totalV+=Integer.parseInt(rs.getString("count"));
										totalH[iCnt]+=Integer.parseInt(rs.getString("count"));
									}
								}
							}
						}
						rs.close();
						item[tot].setText(iLoop,Integer.toString(totalV));
						progbr.bar.setSelection(iLoop+cnt);
					}
					int totalSub=0;
					for (int iLoop1=0;iLoop1<tot;iLoop1++) {
						item[iLoop1].setText(32,Integer.toString(totalH[iLoop1]));
						totalSub+=totalH[iLoop1];
						progbr.bar.setSelection(iLoop+iLoop1+cnt);
					}
					item[tot].setText(32,Integer.toString(totalSub));
					progbr.finish();
					stmt.close();
					conn.close();
					System.out.println("finished");
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
  	
  	void printToFile(final String sTitle,final String sType) {
		final cProgressBar progbr = new cProgressBar("");
		new Thread() {
	        public void run() {
	          if (AmscSplashScreen2.display.isDisposed()) return;
		      AmscSplashScreen2.display.asyncExec(new Runnable() {
		      public void run() {
				progbr.create(table.getColumnCount()+table.getItemCount()-1);
		  		System.out.println("tclm:"+table.getColumnCount()+" trow:"+table.getItemCount());
		  		
//		  		WriteToAFile writeAToFile = new WriteToAFile(); 
//		  		writeAToFile.open("/tmp/ramdisk0/statistic.txt");
//				//String sData=sTitle+"\n\n";
//				writeAToFile.write(sTitle+"\n\n");

				WriteToTXT writeAToFile = new WriteToTXT(); //WriteToAFile writeAToFile = new WriteToAFile(); 
		  		writeAToFile.open("/tmp/ramdisk0/statistic.txt");
  				writeAToFile.write(sTitle+"\n\n");
  				
				int iCntBar=0;
				for (int iCnt=0;iCnt<table.getColumnCount();iCnt++) {
		  			//sData+=table.getColumn(iCnt).getText();
		  			writeAToFile.write(table.getColumn(iCnt).getText());
		  			int imaxtrim=6;
		  			int itrim=table.getColumn(iCnt).getText().length();
		  			for(int ilp=0;ilp<imaxtrim-itrim;ilp++) {
		  				//sData+=" ";
		  				writeAToFile.write(" ");
		  			}
					progbr.bar.setSelection(iCnt);
					iCntBar=iCnt;
		  		}
		  		//sData+="\n\n";
		  		writeAToFile.write("\n\n");
				
		  		String alldata="",tes="";
		  		for (int iCnt1=0;iCnt1<table.getItemCount();iCnt1++) {
		  			for (int iCnt=0;iCnt<table.getColumnCount();iCnt++) {
		  				//sData+=table.getItem(iCnt1).getText(iCnt); 
		  				writeAToFile.write(table.getItem(iCnt1).getText(iCnt));
		  				//alldata+=table.getItem(iCnt1).getText(iCnt)+";";
		  				if (iCnt==0) tes+="\n";
		  				tes+=table.getItem(iCnt1).getText(iCnt)+";";
		  				//-
		  				alldata+=table.getItem(iCnt1).getText(iCnt)+";";
		  				//-
		  				
		  				int imaxtrim=6;
		  				int itrim=table.getItem(iCnt1).getText(iCnt).length();
		  				for(int ilp=0;ilp<imaxtrim-itrim;ilp++) {
		  					writeAToFile.write(" ");
		  					//sData+=" ";
		  				}
		  			}
		  			writeAToFile.write("\n");
		  			//sData+="\n";
					progbr.bar.setSelection(iCnt1+iCntBar);
		  		}
//		  		fp.setData("", sTitle, "\n", "metar", alldata, "","/tmp/ramdisk0/statisticMetar.pdf", "/tmp/ramdisk0/statisticMetar.pdf","volmet");
//		  		writeAToFile.close();
//		    	try {
//		    		Runtime.getRuntime().exec(jdbc.getPdfCmd() + " /tmp/ramdisk0/statisticMetar.pdf");
//		    	}
//		    	catch (IOException e) {System.err.println("Error: " + e.getMessage());}
		  		String dtSearch = "metar";
		  		
		  		if (sType.compareToIgnoreCase("pdf")==0) {
		  			fp.setData("", sTitle, "\n", dtSearch, alldata, "","/tmp/ramdisk0/statisticMeta.pdf", "/tmp/ramdisk0/statisticMetar.pdf","volmet");
		  			try {
//			    		Runtime.getRuntime().exec(jdbc.getPdfCmd() + " /tmp/ramdisk0/statisticMetar.pdf");
			    		Runtime.getRuntime().exec(Lists.cmdPDF + " /tmp/ramdisk0/statisticMetar.pdf");
			    	}
		  			catch (IOException e) {System.err.println("Error: " + e.getMessage());}
		  		} else if (sType.compareToIgnoreCase("xls")==0) {
		  			writexls.setData(dtSearch, sTitle, tes, "/tmp/ramdisk0/statisticMetar.xls");
		  			try {
//		  				Runtime.getRuntime().exec(jdbc.getXlsCmd() + " /tmp/ramdisk0/statisticMetar.xls");
		  				Runtime.getRuntime().exec(Lists.cmdXLS + " /tmp/ramdisk0/statisticMetar.xls");
			    	}
		  			catch (IOException e) {System.err.println("Error: " + e.getMessage());}
		  		}
		  		writeAToFile.close();
		  		
		    	System.out.println("open file /tmp/ramdisk0/statistic.txt OK!!!");
		    	progbr.finish();
		      }
		    });
	       }
	    }.start();
	    progbr.begin();
  	}

}