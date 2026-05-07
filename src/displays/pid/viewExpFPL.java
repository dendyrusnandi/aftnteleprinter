package displays.pid;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

public class viewExpFPL {
	Table table;
	Shell shell;
	Shell shellMessage;
	Label label;
	String sDateQuery;

	void createContent(Shell pshell){
		shell=pshell;
		if (!shdisp.getDisplay().isDisposed()) {
			shdisp.getDisplay().syncExec(new Runnable() {
				public void run() {
					//shell = new Shell(SWT.MAX);
					shell.setText("List of Expired FPL");
					label = new Label(shell, SWT.LEFT);
					label.setText("List of Expired FPL");
					label.setBounds(10,10,135,20);
					table = new Table (shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);;
					//table.setLinesVisible (true);
					table.setHeaderVisible (true);
					table.setBounds(10,35,530,150);
					GridData data = new GridData(SWT.DragDetect, SWT.FILL, true, true);
					data.heightHint = 1;
					data.widthHint = 1;
					table.setLayoutData(data);

					final TableColumn pri = new TableColumn(table,SWT.LEFT);
					pri.setText("P.");
					pri.setWidth(35);
					pri.setResizable(false);

					final TableColumn fil = new TableColumn(table,SWT.LEFT);
					fil.setText("Fil.Time");
					fil.setWidth(60);
					fil.setResizable(false);

					final TableColumn org = new TableColumn(table,SWT.LEFT);
					org.setText("Originator");
					org.setWidth(90);
					org.setResizable(false);

					final TableColumn aircraftID = new TableColumn(table,SWT.LEFT);
					aircraftID.setText("Aircraft ID");
					aircraftID.setWidth(90);
					aircraftID.setResizable(false);
					
					final TableColumn dtm = new TableColumn(table,SWT.LEFT);
					dtm.setText("Date/Time");
					dtm.setWidth(150);
					dtm.setResizable(false);

					final TableColumn id = new TableColumn(table,SWT.LEFT);
					id.setText("ID");
					id.setWidth(0);
					id.setResizable(false);

					final TableColumn typ = new TableColumn(table,SWT.LEFT);
					typ.setText("Type");
					typ.setWidth(60);
					typ.setResizable(false);

					getDate();
					//String sQuery = "SELECT * FROM air_message where st='0' and type3a='FPL' " + sDateQuery;
				    //sQuery+="ORDER BY tgl DESC"; 
					//jdbc.getExpiredFPL(sQuery, table);
					
					String sQuery = "SELECT * FROM check_status WHERE (type='FPL' OR type='RPL') AND status='expired' order by tgl desc";
					jdbc.getExpiredFPL1(sQuery, table);
					
					shell.setBounds(600, 800, 550, 200);
					shell.open();
					
					shellMessage = new Shell(SWT.MAX | SWT.CENTER);
				    table.addListener (SWT.Selection, new Listener () {
						public void handleEvent (Event e) {
							TableItem [] selection = table.getSelection();
							System.out.println("selection "+selection[0].getText(0));
							System.out.println("selection "+selection[0].getText(1));
							System.out.println("selection "+selection[0].getText(2));
							System.out.println("selection "+selection[0].getText(3));
							System.out.println("selection "+selection[0].getText(4));
							System.out.println("selection "+selection[0].getText(5));
							System.out.println("selection "+selection[0].getText(6));
							System.out.println("e.item    "+e.index+" "+e.item);
							selection[0].setBackground(new Color(shdisp.getDisplay(),192,192,192));
							String sQuery="";
							if (selection[0].getText(4)!=null) {
								String sid="",tbnm="",typ="",stgl="";
								if (selection[0].getText(0)!=null) sid=selection[0].getText(5);
								if (selection[0].getText(6)!=null) {
									if (selection[0].getText(6).compareTo("RPL")==0) {
										tbnm="rpl_content";
										typ="RPL";
									}
									else {tbnm="air_message";
									typ="FPL";}
								}
								else {tbnm="air_message";
								typ="FPL";}
								if (selection[0].getText(6)!=null) stgl=selection[0].getText(4);
								IOSR cIOSR = new IOSR();
								sQuery=cIOSR.createQuery(tbnm,sid,typ,stgl);
								System.out.println("sQuery:"+sQuery);
								if (shellMessage.isDisposed()) {
									shellMessage = new Shell(SWT.MAX | SWT.CENTER);
								}
								shellMessage.close();
								shellMessage = new Shell(SWT.MAX | SWT.CENTER);
								Dtgram.getYearMonth();
					        	sQuery=sQuery.replace("air_message",""+Dtgram.sTable+"");
					        	cIOSR.filing_timeRPL=selection[0].getText(1);
								String sRes=cIOSR.getFullMesssage(sQuery,typ);
								if (sRes!=null) cIOSR.createDispMessage(shellMessage,sRes);
							}
							else System.out.println("no table name\n");
							
						}
					});

				}
			});
		}

	}
	
	void getDate() {
		String sQuery=" and tgl >";
		String sDateTimeNow = new DateUtils().now();
		String sYearNow = new DateUtils().now().substring(0,4);
		String sMounthNow = new DateUtils().now().substring(5, 7);
		String sDateNow = new DateUtils().now().substring(8,10);
		
		System.out.println(sDateTimeNow);
		System.out.println(sYearNow);
		System.out.println(sMounthNow);
		System.out.println(sDateNow);
		
		String date = sDateNow,date1;
		String MonthAts = sMounthNow;
		String yearNtm = sYearNow;
		String yearMonths = "'"+sYearNow+"-"+sMounthNow+"-";
		String sTime = "00:00:00";
		
		//date=Integer.toString(iLoop);
		date1="0";
		if (date.length()==1) date1+=date;
		else date1=date;
		sQuery+=yearMonths+date1+" "+sTime+"'";
	
		if (date1.compareTo("31")==0) {
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
		else date=Integer.toString(Integer.parseInt(date)+1);
		date1="0";
		if (date.length()==1) date1+=date;
		else date1=date;
		sQuery+=" and tgl<"+yearMonths+date1+" "+sTime+"' ";
		sDateQuery = sQuery;
		//System.out.println(sQuery);
	}

}
