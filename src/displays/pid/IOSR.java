package displays.pid;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class IOSR {
	static int dir;
	Shell shellMessage;
	Shell shellMessage1;
	Shell shellMessage2;
	String filing_timeRPL;
	
	
	public void koneksiDB(Shell shell,final String s_query,final int typ1) {
		Dtgram.getYearMonth();
		try {
			final Table table=shdisp.crtTable(shell,typ1);
			PlayerComparatorInbATS1_S comparatorInbATS1_S=shdisp.crtPlayerInb(typ1);
			java.util.List playersInbATS1_S=shdisp.getplayersInbATS1_S(typ1);

			int w=1110;
			//if (DisplayMain.text_w>0) w=DisplayMain.text_w;//add mega

			shell.setLocation(30,136);

			int i_height=0;//DisplayMain.inbox_h;//add mega
			if (i_height==0) i_height=560;

			shell.setSize(683, i_height+42);
			jdbc.inbox(s_query, playersInbATS1_S,typ1);
			//	new ShowMessageBox().open(shell,"Information", "No Data !!!");
			//else { 
				createContents(table,playersInbATS1_S,comparatorInbATS1_S);
				shell.open();
				shellMessage = new Shell(SWT.MAX | SWT.CENTER);
			    table.addListener (SWT.Selection, new Listener () {
					public void handleEvent (Event e) {
						TableItem [] selection = table.getSelection();
						System.out.println("selection "+selection[0].getText(0));
						System.out.println("selection "+selection[0].getText(1));
						System.out.println("selection "+selection[0].getText(2));
						System.out.println("selection "+selection[0].getText(3));
						Dtgram.getYearMonth();
						String sQuery="select * from "+Dtgram.sTable+" where id_ats='"+selection[0].getText(0)+"'"+" and tgl='"+selection[0].getText(3)+"'";
						Connection conn = null;
						Statement stmt = null;
						boolean hasilExe;
						try{	
							String driver = "com.mysql.jdbc.Driver";
							Class.forName(driver);
							System.out.println("Select table..."+sQuery);
							conn = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUser(), jdbc.getPass());
							//conn = DriverManager.getConnection("jdbc:mysql://192.168.1.111/ais","root","");
							stmt = conn.createStatement();
							hasilExe=stmt.execute(sQuery);
							System.out.println("hasilexe1:"+hasilExe);
							if(hasilExe==true) {
								ResultSet rs = stmt.getResultSet();
								if (rs.next()) {
									System.out.println("next");
									String sRes="";
									 String cid="";
									 if (rs.getString("cid")!=null) cid+=rs.getString("cid");
									 if (rs.getString("seq")!=null) cid+=rs.getString("seq");
									 if (rs.getString("tms")!=null) {
										 cid+=" ";
										 cid+=rs.getString("tms");
									 }
									 if (!cid.isEmpty()) {
										 cid+="\n";
										 sRes=cid;
									 }

									 boolean flg=false;
									 if (rs.getString("priority")!=null) {
										sRes+=rs.getString("priority");
										if (!rs.getString("priority").isEmpty()) flg=true;
									 }
									 if (flg) {
										int naddr=0;
										for (int i=0;i<21;i++) {
											String s = rs.getString("destination"+Integer.toString(i+1));
											if (s!=null) {
												if (!s.isEmpty()) {
													sRes+=" "+s;
													if ((i==6) || (i==13)) sRes+="\n";
													naddr++;
												}
											}
										}
										if (naddr!=14) sRes+="\n";
										if (rs.getString("filing_time")!=null) {
											sRes+=rs.getString("filing_time");
										}
										sRes+=" ";
										if (rs.getString("originator")!=null) {
											sRes+=rs.getString("originator");
										}
										sRes+=" ";
										if (rs.getString("ori_ref")!=null) {
											sRes+=rs.getString("ori_ref");
										}
										sRes+="\n";
									 }
									if (rs.getString("freetext")!=null) {
										sRes+=rs.getString("freetext");
									}
									//System.out.println("free:"+rs.getString("freetext"));
									if (!shellMessage.isDisposed()) 
										shellMessage.close();
									shellMessage = new Shell(SWT.MAX | SWT.CENTER);
									System.out.println("sRes:"+sRes);
									if (sRes!=null) {
										sRes=sRes.replaceAll("`", "'");
										createDispMessage(shellMessage,sRes);
									}
								}
								rs.close();
							}
							else System.out.println("ERROR");
						} catch (SQLException se) {
							se.printStackTrace();
						} catch (Exception err) {
							 err.printStackTrace();
						} finally{
							 try{
								 if (conn!=null)
									 conn.close();
							 } catch(SQLException se){
								 se.printStackTrace();
							 }
						}

						
/*						String sRes=selection[0].getText(2);
						if (!shellMessage.isDisposed()) 
							shellMessage.close();
						shellMessage = new Shell(SWT.MAX | SWT.CENTER);
						if (sRes!=null) createDispMessage(shellMessage,sRes);*/
					}
				});
			//}
		} catch (Exception s) {
			System.out.println(""+s);
		}
  	}
	
	String createQuery(String tbnm, String sid, String typ, String stgl) {
		String sQuery;
		sQuery ="SELECT * FROM "+tbnm+" WHERE ";
		//ATS
		if (tbnm.startsWith("air_message_free_text")) { 
			sQuery+="id_free_text="+sid+" AND ";
		}
		else if (tbnm.startsWith("rpl_content")) { 
			sQuery+="id_rpl_cont="+sid+" AND ";
		}
		else if ((tbnm.startsWith("air_message")) || (tbnm.startsWith("queue"))){
			sQuery+="type3a='"+typ+"' AND ";
			sQuery+="id_ats="+sid+" AND ";
		}
		
		//METEO
		else if (tbnm.startsWith("meteo_metar")) {
			sQuery+="id_metar="+sid+" AND ";
		}
		else if (tbnm.startsWith("meteo_speci")) {
			sQuery+="id_speci="+sid+" AND ";
		}
		else if (tbnm.startsWith("meteo_sigmet")) {
			sQuery+="id_sigmet="+sid+" AND ";
		}
		else if (tbnm.startsWith("meteo_airmet")) {
			sQuery+="id_airmet="+sid+" AND ";
		}
		else if (tbnm.startsWith("meteo_airep")) {
			sQuery+="id_airep="+sid+" AND ";
		}
		else if (tbnm.startsWith("meteo_tafor")) {
			sQuery+="id_tafor="+sid+" AND ";
		}
		else if (tbnm.startsWith("meteo_synop")) {
			sQuery+="id_synop="+sid+" AND ";
		}
		else if (tbnm.startsWith("meteo_arfor")) {
			sQuery+="id_arfor="+sid+" AND ";
		}
		else if (tbnm.startsWith("meteo_rofor")) {
			sQuery+="id_rofor="+sid+" AND ";
		}
		else if (tbnm.startsWith("meteo_wins_war")) {
			sQuery+="id_wins_war="+sid+" AND ";
		}
		else if (tbnm.startsWith("meteo_wintem")) {
			sQuery+="id_wintem="+sid+" AND ";
		}
		else if (tbnm.startsWith("vulcano_adv")) {
			sQuery+="id_vulcano_adv="+sid+" AND ";
		}
		else if (tbnm.startsWith("vulcano_act")) {
			sQuery+="id_va="+sid+" AND ";
		}
		else if (tbnm.startsWith("rqm")) {
			sQuery+="id_rqm="+sid+" AND ";
		}
		else if (tbnm.startsWith("meteo_wsynop")) {
			sQuery+="id_synop="+sid+" AND ";
		}

		//NOTAM
		else if (tbnm.startsWith("notam_multi")) {
			sQuery+="id_notam_multi="+sid+" AND ";
		}
		else if (tbnm.startsWith("snowtam")) {
			sQuery+="id_snowtam="+sid+" AND ";
		}
		else if (tbnm.startsWith("ashtam")) {
			sQuery+="id_ashtam="+sid+" AND ";
		}
		else if (tbnm.startsWith("birdtam")) {
			sQuery+="id_birdtam="+sid+" AND ";
		}
		else if (tbnm.startsWith("rqn")) {
			sQuery+="id_rqn="+sid+" AND ";
		}
		else if (tbnm.startsWith("rql")) {
			sQuery+="id_rql="+sid+" AND ";
		}
		
		//Reject Message
		else if (tbnm.startsWith("reject_message")) {
			sQuery+="id_rjt_msg="+sid+" AND ";
		}
		sQuery+="tgl='"+stgl+"' ";
		return sQuery;
	}
	
	String getFullMesssage(String sQuery, String typ){
		Dtgram.getYearMonth();
		String sQueryMonth=sQuery;
        if (sQuery.contains("reject_message_ats ")) 
        	sQueryMonth=sQueryMonth.replace("reject_message_ats",Dtgram.sTable2);
        if (sQuery.contains("'air_message_free_text'")) 
        	sQueryMonth=sQueryMonth.replace("air_message_free_text",Dtgram.sTable1);
        if (sQuery.contains("'air_message'"))
        	sQueryMonth=sQueryMonth.replace("'air_message'","'"+Dtgram.sTable+"'");
        System.out.println("sQueryCh:"+sQueryMonth);

        sQuery=sQueryMonth;
		String sMessage="";
		merge cGetMsg = new merge();
		Connection conn = null;
		Statement stmt = null;
		boolean hasilExe;
		try{	
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			System.out.println("Select table...");
			conn = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUser(), jdbc.getPass());
			//conn = DriverManager.getConnection("jdbc:mysql://192.168.1.111/ais","root","");
			stmt = conn.createStatement();
			hasilExe=stmt.execute(sQuery);
			System.out.println("sql jdbc:: " + sQuery+" "+hasilExe);
			if(hasilExe==true) {	
				//ATS Message
				if (typ.compareTo("FREETEXT")==0 || typ.compareTo("FREE TEXT")==0) {
					cGetMsg.prosesExeATSFree(stmt,hasilExe);
					System.out.println("freeTextMsg"+cGetMsg.freeText);sMessage=cGetMsg.freeText;}
				else if (typ.compareTo("ALR")==0) {
					cGetMsg.prosesExeALR(stmt,hasilExe);
					System.out.println("ARRMsg"+cGetMsg.alr);sMessage=cGetMsg.alr;}
				else if (typ.compareTo("RCF")==0) {
					cGetMsg.prosesExeRCF(stmt,hasilExe);
					System.out.println("RCFMsg"+cGetMsg.rcf);sMessage=cGetMsg.rcf;}
				else if (typ.compareTo("FPL")==0) {
					cGetMsg.prosesExeFPL(stmt,hasilExe);
					System.out.println("FPLMsg"+cGetMsg.fpl);sMessage=cGetMsg.fpl;}
				else if (typ.compareTo("DLA")==0) {
					cGetMsg.prosesExeDLA(stmt,hasilExe);
					System.out.println("DLAMsg"+cGetMsg.dla);sMessage=cGetMsg.dla;}
				else if (typ.compareTo("CHG")==0) {
					cGetMsg.prosesExeCHG(stmt,hasilExe);
					System.out.println("CHGMsg"+cGetMsg.chg);sMessage=cGetMsg.chg;}
				else if (typ.compareTo("CNL")==0) {
					cGetMsg.prosesExeCNL(stmt,hasilExe);
					System.out.println("CNLMsg"+cGetMsg.cnl);sMessage=cGetMsg.cnl;}
				else if (typ.compareTo("DEP")==0) {
					cGetMsg.prosesExeDEP(stmt,hasilExe);
					System.out.println("DEPMsg"+cGetMsg.dep);sMessage=cGetMsg.dep;}
				else if (typ.compareTo("ARR")==0) {
					cGetMsg.prosesExeARR(stmt,hasilExe);
					System.out.println("ARRMsg"+cGetMsg.arr);sMessage=cGetMsg.arr;}
				else if (typ.compareTo("CDN")==0) {
					cGetMsg.prosesExeCDN(stmt,hasilExe);
					System.out.println("CDNMsg"+cGetMsg.cdn);sMessage=cGetMsg.cdn;}
				else if (typ.compareTo("CPL")==0) {
					cGetMsg.prosesExeCPL(stmt,hasilExe);
					System.out.println("CPLMsg"+cGetMsg.cpl);sMessage=cGetMsg.cpl;}
				else if (typ.compareTo("EST")==0) {
					cGetMsg.prosesExeEST(stmt,hasilExe);
					System.out.println("ESTMsg"+cGetMsg.est);sMessage=cGetMsg.est;}
				else if (typ.compareTo("ACP")==0) {
					cGetMsg.prosesExeACP(stmt,hasilExe);
					System.out.println("ACPMsg"+cGetMsg.acp);sMessage=cGetMsg.acp;}
				else if (typ.compareTo("LAM")==0) {
					cGetMsg.prosesExeLAM(stmt,hasilExe);
					System.out.println("LAMMsg"+cGetMsg.lam);sMessage=cGetMsg.lam;}
				else if (typ.compareTo("SPL")==0) {
					cGetMsg.prosesExeSPL(stmt,hasilExe);
					System.out.println("SPLMsg"+cGetMsg.spl);sMessage=cGetMsg.spl;}
				else if (typ.compareTo("RQP")==0) {
					cGetMsg.prosesExeRQP(stmt,hasilExe);
					System.out.println("RQPMsg"+cGetMsg.rqp);sMessage=cGetMsg.rqp;}
				else if (typ.compareTo("RPL")==0) {
					cGetMsg.prosesMrpl(stmt,hasilExe,filing_timeRPL);
					System.out.println("RPLMsg"+cGetMsg.rpl);sMessage=cGetMsg.rpl;
				}
				else if (typ.compareTo("RQS")==0) {
					cGetMsg.prosesExeRQS(stmt,hasilExe);
					System.out.println("RQSMsg"+cGetMsg.rqs);sMessage=cGetMsg.rqs;}
				
				else if (typ.compareTo("ABI")==0) {
					cGetMsg.prosesExeABI(stmt,hasilExe);
					System.out.println("ABIMsg"+cGetMsg.abi);sMessage=cGetMsg.abi;}
				
				else if (typ.compareTo("MAC")==0) {
					cGetMsg.prosesExeMAC(stmt,hasilExe);
					System.out.println("MACMsg"+cGetMsg.mac);sMessage=cGetMsg.mac;}
				
				else if (typ.compareTo("PAC")==0) {
					cGetMsg.prosesExePAC(stmt,hasilExe);
					System.out.println("PACMsg"+cGetMsg.pac);sMessage=cGetMsg.pac;}
				
				else if (typ.compareTo("REJ")==0) {
					cGetMsg.prosesExeREJ(stmt,hasilExe);
					System.out.println("REJMsg"+cGetMsg.rej);sMessage=cGetMsg.rej;}
				
				else if (typ.compareTo("TOC")==0) {
					cGetMsg.prosesExeTOC(stmt,hasilExe);
					System.out.println("TOCMsg"+cGetMsg.toc);sMessage=cGetMsg.toc;}
				
				else if (typ.compareTo("AOC")==0) {
					cGetMsg.prosesExeAOC(stmt,hasilExe);
					System.out.println("AOCMsg"+cGetMsg.aoc);sMessage=cGetMsg.aoc;}
				
				else if (typ.compareTo("EMG")==0) {
					cGetMsg.prosesExeEMG(stmt,hasilExe);
					System.out.println("EMGMsg"+cGetMsg.emg);sMessage=cGetMsg.emg;}
				
				else if (typ.compareTo("MIS")==0) {
					cGetMsg.prosesExeMIS(stmt,hasilExe);
					System.out.println("MISMsg"+cGetMsg.mis);sMessage=cGetMsg.mis;}
				
				else if (typ.compareTo("TDM")==0) {
					cGetMsg.prosesExeTDM(stmt,hasilExe);
					System.out.println("TDMMsg"+cGetMsg.tdm);sMessage=cGetMsg.tdm;}
				
				else if (typ.compareTo("LRM")==0) {
					cGetMsg.prosesExeLRM(stmt,hasilExe);
					System.out.println("LRMMsg"+cGetMsg.lrm);sMessage=cGetMsg.lrm;}
				
				else if (typ.compareTo("TRU")==0) {
					cGetMsg.prosesExeTRU(stmt,hasilExe);
					System.out.println("TRUMsg"+cGetMsg.tru);sMessage=cGetMsg.tru;}
				
				else if (typ.compareTo("ADS")==0) {
					cGetMsg.prosesExeADS(stmt,hasilExe);
					System.out.println("ADSMsg"+cGetMsg.ads);sMessage=cGetMsg.ads;}
				//METEO Message
				else if (typ.compareTo("FREETEXT METEO")==0) {
					cGetMsg.prosesExeMeteoFree(stmt,hasilExe);
					System.out.println("freeTextMetMsg"+cGetMsg.freeTextMeteo);sMessage=cGetMsg.freeTextMeteo;}
				else if (typ.compareTo("METAR")==0) {
					cGetMsg.prosesExeMETAR(stmt,hasilExe);
					System.out.println("metarMsg"+cGetMsg.meteoMetar);sMessage=cGetMsg.meteoMetar;}
				else if (typ.compareTo("SPECI")==0) {
					cGetMsg.prosesExeSpeci(stmt,hasilExe);
					System.out.println("speciMsg"+cGetMsg.meteoSpeci);sMessage=cGetMsg.meteoSpeci;}
				else if (typ.compareTo("SIGMET")==0) {
					cGetMsg.prosesExeSIGMET(stmt,hasilExe);
					System.out.println("sigmetMsg"+cGetMsg.meteoSigmet);sMessage=cGetMsg.meteoSigmet;}
				else if (typ.compareTo("AIRMET")==0) {
					cGetMsg.prosesExeAIRMET(stmt,hasilExe);
					System.out.println("airmetMsg"+cGetMsg.meteoAirmet);sMessage=cGetMsg.meteoAirmet;}
				else if (typ.compareTo("AIREP")==0) {
					cGetMsg.prosesExeAIREP(stmt,hasilExe);
					System.out.println("airepMsg"+cGetMsg.meteoAirep);sMessage=cGetMsg.meteoAirep;}
				else if (typ.startsWith("TAF")) {
					cGetMsg.prosesExeTAFOR(stmt,hasilExe);
					System.out.println("taforMsg"+cGetMsg.meteoTafor);sMessage=cGetMsg.meteoTafor;}
				else if (typ.compareTo("ROFOR")==0) {
					cGetMsg.prosesExeROFOR(stmt,hasilExe);
					System.out.println("roforMsg"+cGetMsg.meteoRofor);sMessage=cGetMsg.meteoRofor;}
				else if (typ.compareTo("SYNOP")==0) {
					cGetMsg.prosesExeSYNOP(stmt,hasilExe);
					System.out.println("synopMsg"+cGetMsg.meteoSynop);sMessage=cGetMsg.meteoSynop;}
				else if (typ.compareTo("ARFOR")==0) {
					cGetMsg.prosesExeARFOR(stmt,hasilExe);
					System.out.println("arforMsg"+cGetMsg.meteoArfor);sMessage=cGetMsg.meteoArfor;}
				else if (typ.startsWith("WINS WAR")) {
					cGetMsg.prosesExeWINSWAR(stmt,hasilExe);
					System.out.println("winswarMsg"+cGetMsg.meteoWinswar);sMessage=cGetMsg.meteoWinswar;}
				else if (typ.compareTo("WINTEM")==0) {
					cGetMsg.prosesExeWINTEM(stmt,hasilExe);
					System.out.println("wintemMsg"+cGetMsg.meteoWintem);sMessage=cGetMsg.meteoWintem;}
				else if (typ.startsWith("VOLCANO ADV")) {
					cGetMsg.prosesExeADV(stmt,hasilExe);
					System.out.println("vulc_advMsg"+cGetMsg.meteoAdv);sMessage=cGetMsg.meteoAdv;}
				else if (typ.startsWith("VOLCANIC ACT")) {
					cGetMsg.prosesExeVOL(stmt,hasilExe);
					System.out.println("vulc_actMsg"+cGetMsg.meteoVol);sMessage=cGetMsg.meteoVol;}
				else if (typ.compareTo("RQM")==0) {
					cGetMsg.prosesExeRQM(stmt,hasilExe);
					System.out.println("rqmMsg"+cGetMsg.rqm);sMessage=cGetMsg.rqm;}
				else if (typ.startsWith("WARNING")) {
					cGetMsg.prosesExeWARSYN(stmt,hasilExe);
					System.out.println("warning synopMsg"+cGetMsg.meteoWsyn);sMessage=cGetMsg.meteoWsyn;}
				//NOTAM message
				else if ((typ.startsWith("MULTIPART")) || (typ.startsWith("RQNTM")) || (typ.startsWith("NOTAM"))) {
					cGetMsg.prosesExeNOTAM(stmt,hasilExe);
					System.out.println("NOTAMMsg"+cGetMsg.multipart);sMessage=cGetMsg.multipart;}
				else if (typ.compareTo("SNOWTAM")==0) {
					cGetMsg.prosesExeSNOWTAM(stmt,hasilExe);
					System.out.println("SNOWTAMMsg"+cGetMsg.snowtam);sMessage=cGetMsg.snowtam;}
				else if (typ.compareTo("ASHTAM")==0) {
					cGetMsg.prosesExeASHTAM(stmt,hasilExe);
					System.out.println("Msg"+cGetMsg.ashtam);sMessage=cGetMsg.ashtam;}
				else if (typ.compareTo("BIRDTAM")==0) {
					cGetMsg.prosesExeBIRDTAM(stmt,hasilExe);
					System.out.println("Msg"+cGetMsg.birdtam);sMessage=cGetMsg.birdtam;}
				else if (typ.compareTo("RQN")==0) {
					cGetMsg.prosesExeRQN(stmt,hasilExe);
					System.out.println("Msg"+cGetMsg.rqn);sMessage=cGetMsg.rqn;}
				else if (typ.compareTo("RQL")==0) {
					cGetMsg.prosesExeRQL(stmt,hasilExe);
					System.out.println("Msg"+cGetMsg.rql);sMessage=cGetMsg.rql;}
				//Reject Message
				else if ((typ.startsWith("REJECT ATS")) || (typ.compareTo("reject_message_ats")==0)) {
					cGetMsg.prosesExeReject(stmt,hasilExe,1);
					System.out.println("Msg"+cGetMsg.rejectATS);sMessage=cGetMsg.rejectATS;}
				else if ((typ.startsWith("REJECT NOTAM")) || (typ.compareTo("reject_message_notam")==0)) {
					cGetMsg.prosesExeReject(stmt,hasilExe,2);
					System.out.println("Msg"+cGetMsg.rejectNOTAM);sMessage=cGetMsg.rejectNOTAM;}
				else if ((typ.startsWith("REJECT METEO")) || (typ.compareTo("reject_message_meteo")==0)) {
					cGetMsg.prosesExeReject(stmt,hasilExe,3);
					System.out.println("Msg"+cGetMsg.rejectMETEO);sMessage=cGetMsg.rejectMETEO;}
			 } else System.out.println("ERROR");
		} catch (SQLException se) {
			//s.printStackTrace();
		} catch (Exception e) {
			 e.printStackTrace();
		} finally{
			 try{
				 if (conn!=null)
					 conn.close();
			 } catch(SQLException se){
				 se.printStackTrace();
			 }
		}
		return sMessage;
	}
	
	void createDispMessage(Shell shell,String sMessage){
		WriteToAFile wr = new WriteToAFile();
		String sfname="/tmp/ramdisk0/PIDmessage.txt";
		wr.open(sfname);
		wr.write(sMessage);
		wr.close();
		final PrintAction cPrintExec = new PrintAction();
		try {
			cPrintExec.open(shdisp.getDisplay(), sfname);
		}catch (IOException e){
			System.out.println(e);
		}
		
		shell.setText("Message Viewer");
		final Text text= new Text(shell, SWT.MULTI | SWT.BORDER |SWT.H_SCROLL | SWT.V_SCROLL);
		text.setBounds(10,10,580,500);
		System.out.println("TextCreateDispMessage:"+sMessage);
		text.setText(sMessage);
		text.setEditable(false);
		
		Button b_font = new Button(shell,SWT.PUSH);
		b_font.setText("&Font Setup");
		b_font.setBounds(10,520,90,40);
		b_font.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try{
					cPrintExec.menuFont(text);
	            } catch (Exception ee) {
	                ee.printStackTrace();
	            }
			}
		});

		/*Button b_print = new Button(shell,SWT.PUSH);
		b_print.setText("&Print");
		b_print.setBounds(100,520,90,40);
		b_print.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try{
					cPrintExec.menuPrint();
	            } catch (Exception ee) {
	                ee.printStackTrace();
	            }
			}
		});*/

		if (sMessage!=null) {
			text.setText(sMessage);
		}
		int w=1110;
//		if (DisplayMain.text_w>0) w=DisplayMain.text_w;//add mega

		shell.setLocation(w+30,136);
		shell.setSize(610,595);
		shell.open();
	}
	
	void createContents(final Table table,final java.util.List playersInbATS1_S,final PlayerComparatorInbATS1_S comparatorInbATS1_S) {
		System.out.println("createContents\n");
		final TableColumn ID = new TableColumn(table,SWT.LEFT);
		ID.setText("ID");
		ID.setWidth(0);
		ID.setResizable(false);
		ID.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorInbATS1_S.setColumn(PlayerComparatorInbATS1_S.ID);
				comparatorInbATS1_S.reverseDirection();
				fillTable(table,playersInbATS1_S,comparatorInbATS1_S);
			}
		});
		
		final TableColumn MSG = new TableColumn(table,SWT.LEFT);
		MSG.setText("Message");
		MSG.setWidth(500);
		MSG.setResizable(false);
		MSG.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorInbATS1_S.setColumn(PlayerComparatorInbATS1_S.MSG);
				comparatorInbATS1_S.reverseDirection();
				fillTable(table,playersInbATS1_S,comparatorInbATS1_S);
			}
		});

		final TableColumn MSG2 = new TableColumn(table,SWT.LEFT);
		MSG2.setText("Message2");
		MSG2.setWidth(0);
		MSG2.setResizable(false);
		MSG2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorInbATS1_S.setColumn(PlayerComparatorInbATS1_S.MSG);
				comparatorInbATS1_S.reverseDirection();
				fillTable(table,playersInbATS1_S,comparatorInbATS1_S);
			}
		});

		final TableColumn TGL = new TableColumn(table,SWT.LEFT);
		TGL.setText("Date/Time");
		TGL.setWidth(80);
		TGL.setResizable(false);
		TGL.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorInbATS1_S.setColumn(PlayerComparatorInbATS1_S.TGL);
				comparatorInbATS1_S.reverseDirection();
				fillTable(table,playersInbATS1_S,comparatorInbATS1_S);
			}
		});
	    
		// Do the initial fill of the table
	    System.out.println("sortir......");
		comparatorInbATS1_S.setColumn(PlayerComparatorInbATS1_S.TGL);
		//comparatorInbATS1_S.reverseDirection();
		fillTable(table,playersInbATS1_S,comparatorInbATS1_S);
	    ckrow(table);
  	}

	static void fillTable(Table table,java.util.List playersInbATS1_S,PlayerComparatorInbATS1_S comparatorInbATS1_S) {
	    table.setRedraw(false);
 	    table.removeAll();
	    Collections.sort(playersInbATS1_S, comparatorInbATS1_S);
	    int i=0;
	    for (Iterator itr = playersInbATS1_S.iterator(); itr.hasNext();) {
	    	if (i>DisplayMain.g_maxrowinfo) {
	    		playersInbATS1_S.remove(i);
	    		System.out.println("playerdel:"+i);
	    	}
	    	else{
	    		System.out.println("playeradd:"+i);
	    		PlayerInbATS1_S player = (PlayerInbATS1_S) itr.next();
		    	TableItem item = new TableItem(table, SWT.NONE);
		    	int c = 0;
		    	item.setText(c++, player.getID());
		    	item.setText(c++, player.getMSG());
		    	item.setText(c++, player.getMSG2());
		    	item.setText(c++, player.getTGL());
	    	}
	    	i++;
	    }
	    table.setSortDirection(dir);
	    table.setRedraw(true);
  	}

	void koneksiDBout(Shell shell,final String sQuery,final int typ1) {
		try {
			final Table table=shdisp.crtTable(shell,typ1);
			PlayerComparatorOutATS1_S comparatorOutATS1_S=shdisp.crtPlayerOut(typ1);
			java.util.List playersOutATS1_S=shdisp.getplayersOutATS1_S(typ1);
			shell.setLocation(300,100);
			shell.setSize(1280, 600);
			jdbc.outboxsent(sQuery, playersOutATS1_S,typ1); 
			//	new ShowMessageBox().open(shell,"Information", "No Data !!!");
			//else {
				createContents(shell,table,playersOutATS1_S,comparatorOutATS1_S,typ1);
				shell.open();
				shellMessage1 = new Shell(SWT.MAX | SWT.CENTER);
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
						System.out.println("selection "+selection[0].getText(7));
						System.out.println("selection "+selection[0].getText(8));
						System.out.println("selection "+selection[0].getText(9));
						
						String sQuery="";
						if (selection[0].getText(4)!=null) {
							String sid="",tbnm="",typ="",stgl="";
							filing_timeRPL="";
							if (selection[0].getText(7)!=null) sid=selection[0].getText(7);
							if (selection[0].getText(8)!=null) tbnm=selection[0].getText(8);
							if (selection[0].getText(4)!=null) {
								if (selection[0].getText(4).compareTo("WS WRNG")==0)
									typ="WINS WARNING";
								else typ=selection[0].getText(4);
							}
							if (selection[0].getText(9)!=null) stgl=selection[0].getText(9);
							sQuery=createQuery(tbnm,sid,typ,stgl);
							System.out.println("sQuery:"+sQuery);
							if (shellMessage1.isDisposed()) {
								shellMessage1 = new Shell(SWT.MAX | SWT.CENTER);
							}
							shellMessage1.close();
							shellMessage1 = new Shell(SWT.MAX | SWT.CENTER);
							createQuery(tbnm,sid,typ,stgl);
							if (typ.compareTo("RPL")==0) {
								filing_timeRPL = jdbc.getFilingTimeRPL("select filing_time from check_status where type='RPL' and id="+sid+" and tgl='"+stgl+"'");
							}
							String sRes=getFullMesssage(sQuery,typ);
							if (sRes!=null) createDispMessage(shellMessage1,sRes);
						}
						else System.out.println("no table name\n");
	
					}
				});
			//}
		} catch (Exception s) {
			System.out.println(""+s);
		}
  	}
	
	void createContents(Composite composite,final Table table,final java.util.List playersOutATS1_S,final PlayerComparatorOutATS1_S comparatorOutATS1_S,final int typ1) {
		System.out.println("createContents\n");
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		table.setBounds(10,10,1410,810);
		GridData data = new GridData(SWT.DragDetect, SWT.FILL, true, true);
		data.heightHint = 1;
		data.widthHint = 1;
		table.setLayoutData(data);

		TableColumn ft = new TableColumn(table,SWT.LEFT);
		ft.setText("Fil.Time");
		ft.setWidth(80);
		ft.setResizable(false);
		ft.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorOutATS1_S.setColumn(PlayerComparatorOutATS1_S.FT);
				comparatorOutATS1_S.reverseDirection();
				fillTable(table,playersOutATS1_S,comparatorOutATS1_S);
			}
		});

		TableColumn st = new TableColumn(table,SWT.LEFT);
		st.setText("Status");
		st.setWidth(60);
		st.setResizable(false);
		st.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorOutATS1_S.setColumn(PlayerComparatorOutATS1_S.ST);
				comparatorOutATS1_S.reverseDirection();
				fillTable(table,playersOutATS1_S,comparatorOutATS1_S);
			}
		});

		TableColumn dtm = new TableColumn(table,SWT.LEFT);
		dtm.setText("Send at");
		dtm.setWidth(90);
		dtm.setResizable(false);
		dtm.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorOutATS1_S.setColumn(PlayerComparatorOutATS1_S.DTM);
				comparatorOutATS1_S.reverseDirection();
				fillTable(table,playersOutATS1_S,comparatorOutATS1_S);
			}
		});
		
		TableColumn arcid = new TableColumn(table,SWT.LEFT);
		if ((typ1==3) || (typ1==6)) {
			arcid.setText("Aircraft ID");
			arcid.setWidth(90);
			arcid.setResizable(false);
			arcid.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					comparatorOutATS1_S.setColumn(PlayerComparatorOutATS1_S.ARCID);
					comparatorOutATS1_S.reverseDirection();
					fillTable(table,playersOutATS1_S,comparatorOutATS1_S);
				}
			});
		}
		
		TableColumn typ = new TableColumn(table,SWT.LEFT);
		typ.setText("Type");
		typ.setWidth(120);
		typ.setResizable(false);
		typ.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorOutATS1_S.setColumn(PlayerComparatorOutATS1_S.TYP);
				comparatorOutATS1_S.reverseDirection();
				fillTable(table,playersOutATS1_S,comparatorOutATS1_S);
			}
		});

		TableColumn org = new TableColumn(table,SWT.LEFT);
		org.setText("Originator");
		org.setWidth(100);
		org.setResizable(false);
		org.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorOutATS1_S.setColumn(PlayerComparatorOutATS1_S.ORG);
				comparatorOutATS1_S.reverseDirection();
				fillTable(table,playersOutATS1_S,comparatorOutATS1_S);
			}
		});

		TableColumn msg = new TableColumn(table,SWT.LEFT);
		msg.setText("Message");
		msg.setWidth(550);
		msg.setResizable(false);
		msg.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorOutATS1_S.setColumn(PlayerComparatorOutATS1_S.MSG);
				comparatorOutATS1_S.reverseDirection();
				fillTable(table,playersOutATS1_S,comparatorOutATS1_S);
			}
		});
	    
	    
		TableColumn sid = new TableColumn(table,SWT.LEFT);
		sid.setText("ID");
		sid.setWidth(0);
		sid.setResizable(false);

		TableColumn tbnm = new TableColumn(table,SWT.LEFT);
		tbnm.setText("Table");
		tbnm.setWidth(0);
		tbnm.setResizable(false);

		TableColumn tgl = new TableColumn(table,SWT.LEFT);
		tgl.setText("Date/Time");
		tgl.setWidth(0);
		tgl.setResizable(false);

		// Do the initial fill of the table
	    System.out.println("sortir......");
		comparatorOutATS1_S.setColumn(PlayerComparatorOutATS1_S.DTM);
	    if ((typ1>=3) && (typ1<=5)) comparatorOutATS1_S.reverseDirection();
		fillTable(table,playersOutATS1_S,comparatorOutATS1_S);
	    ckrow(table);
  	}

	static void fillTable(Table table,java.util.List playersOutATS1_S,PlayerComparatorOutATS1_S comparatorOutATS1_S) {
	    table.setRedraw(false);
	    table.removeAll();
	    Collections.sort(playersOutATS1_S, comparatorOutATS1_S);
	    for (Iterator itr = playersOutATS1_S.iterator(); itr.hasNext();) {
	    	PlayerOutATS1_S player = (PlayerOutATS1_S) itr.next();
	    	TableItem item = new TableItem(table, SWT.NONE);
	    	int c = 0;
	    	item.setText(c++, player.getFT());
	    	item.setText(c++, player.getST());
	    	item.setText(c++, player.getDTM());
	    	item.setText(c++, player.getARCID());
	    	item.setText(c++, player.getTYP());
	    	item.setText(c++, player.getORG());
	    	item.setText(c++, player.getMSG());
	    	item.setText(c++, player.getID());
	    	item.setText(c++, player.getTABEL());
	    	item.setText(c++, player.getDATETIME());
	    }
	    table.setSortDirection(dir);
	    table.setRedraw(true);
  	}

	void koneksiDBrej(Shell shell,final String sQuery,final int typ1) {
		try {
			final Table table=shdisp.crtTable(shell,typ1);
			PlayerComparatorRejATS1_S comparatorRejATS1_S=shdisp.crtPlayerRej(typ1);
			java.util.List playersRejATS1_S=shdisp.getplayersRejATS1_S(typ1);
			shell.setLocation(300,100);
			shell.setSize(1400, 600);
			jdbc.reject(sQuery, playersRejATS1_S,typ1); 
			//	new ShowMessageBox().open(shell,"Information", "No Data !!!");
			//else {
				createContents(shell,table,playersRejATS1_S,comparatorRejATS1_S);
				shell.open();
				shellMessage2 = new Shell(SWT.MAX | SWT.CENTER);
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
						System.out.println("selection "+selection[0].getText(7));
						
						String sQuery="";
						if (selection[0].getText(4)!=null) {
							String sid="",tbnm="",typ="",stgl="";
							if (selection[0].getText(6)!=null) sid=selection[0].getText(6);
							if (selection[0].getText(7)!=null) tbnm=selection[0].getText(7);
							if (selection[0].getText(5)!=null) stgl=selection[0].getText(5);
							if (tbnm.startsWith("reject_message_ats")) typ="reject_message_ats";
							else if (tbnm.startsWith("reject_message_notam")) typ="reject_message_notam";
							else if (tbnm.startsWith("reject_message")) typ="reject_message_meteo";
							sQuery=createQuery(tbnm,sid,typ,stgl);
							System.out.println("sQuery:"+sQuery);
							if (shellMessage2.isDisposed()) {
								shellMessage2 = new Shell(SWT.MAX | SWT.CENTER);
							}
							shellMessage2.close();
							shellMessage2 = new Shell(SWT.MAX | SWT.CENTER);
							createQuery(tbnm,sid,typ,stgl);
							String sRes=getFullMesssage(sQuery,typ);
							if (sRes!=null) createDispMessage(shellMessage2,sRes);
						}
						else System.out.println("no table name\n");
	
					}
				});
			//}
		} catch (Exception s) {
			System.out.println(""+s);
			//s.printStackTrace();
		}
  	}
	
	void createContents(Composite composite,final Table table,final java.util.List playersRejATS1_S,final PlayerComparatorRejATS1_S comparatorRejATS1_S) {
	    // Create the table
		System.out.println("createContents\n");
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		table.setBounds(10,10,1410,810);
		GridData data = new GridData(SWT.DragDetect, SWT.FILL, true, true);
		data.heightHint = 1;
		data.widthHint = 1;
		table.setLayoutData(data);
		final TableColumn ID = new TableColumn(table,SWT.LEFT);
		ID.setText("SEQ ID");
		ID.setWidth(60);
		ID.setResizable(false);
		ID.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorRejATS1_S.setColumn(PlayerComparatorRejATS1_S.ID);
				comparatorRejATS1_S.reverseDirection();
				fillTable(table,playersRejATS1_S,comparatorRejATS1_S);
			}
		});

		final TableColumn ft = new TableColumn(table,SWT.LEFT);
		ft.setText("SEQ NBR");
		ft.setWidth(70);
		ft.setResizable(false);
		ft.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorRejATS1_S.setColumn(PlayerComparatorRejATS1_S.FT);
				comparatorRejATS1_S.reverseDirection();
				fillTable(table,playersRejATS1_S,comparatorRejATS1_S);
			}
		});

		final TableColumn org = new TableColumn(table,SWT.LEFT);
		org.setText("Originator");
		org.setWidth(100);
		org.setResizable(false);
		org.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorRejATS1_S.setColumn(PlayerComparatorRejATS1_S.ORG);
				comparatorRejATS1_S.reverseDirection();
				fillTable(table,playersRejATS1_S,comparatorRejATS1_S);
			}
		});
		
		final TableColumn tb_nm = new TableColumn(table,SWT.LEFT);
		tb_nm.setText("Error Message");
		tb_nm.setWidth(350);
		tb_nm.setResizable(false);
		tb_nm.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorRejATS1_S.setColumn(PlayerComparatorRejATS1_S.TBNM);
				comparatorRejATS1_S.reverseDirection();
				fillTable(table,playersRejATS1_S,comparatorRejATS1_S);
			}
		});

		final TableColumn msg = new TableColumn(table,SWT.LEFT);
		msg.setText("Message");
		msg.setWidth(600);
		msg.setResizable(false);
		msg.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorRejATS1_S.setColumn(PlayerComparatorRejATS1_S.MSG);
				comparatorRejATS1_S.reverseDirection();
				fillTable(table,playersRejATS1_S,comparatorRejATS1_S);
			}
		});

		final TableColumn dtm = new TableColumn(table,SWT.LEFT);
		dtm.setText("Date/Time");
		dtm.setWidth(120);
		dtm.setResizable(false);
		dtm.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				comparatorRejATS1_S.setColumn(PlayerComparatorRejATS1_S.DTM);
				comparatorRejATS1_S.reverseDirection();
				fillTable(table,playersRejATS1_S,comparatorRejATS1_S);
			}
		});

		final TableColumn id = new TableColumn(table,SWT.LEFT);
		id.setText("ID");
		id.setWidth(0);
		id.setResizable(false);

		final TableColumn tbl = new TableColumn(table,SWT.LEFT);
		tbl.setText("Table");
		tbl.setWidth(0);
		tbl.setResizable(false);

		table.removeAll();
	    table.setRedraw(true);
	    
	    System.out.println("sortir......");
		comparatorRejATS1_S.setColumn(PlayerComparatorRejATS1_S.DTM);
	    //comparatorOutATS1_S.reverseDirection();
		fillTable(table,playersRejATS1_S,comparatorRejATS1_S);
	    ckrow(table);
  	}

	static void fillTable(Table table,java.util.List playersRejATS1_S,PlayerComparatorRejATS1_S comparatorRejATS1_S) {
	    // Turn off drawing to avoid flicker
	    table.setRedraw(false);
        
	    // We remove all the table entries, sort our rows, then add the entries
	    table.removeAll();
	    
	    Collections.sort(playersRejATS1_S, comparatorRejATS1_S);
	    for (Iterator itr = playersRejATS1_S.iterator(); itr.hasNext();) {
	    	PlayerRejATS1_S player = (PlayerRejATS1_S) itr.next();
	    	TableItem item = new TableItem(table, SWT.NONE);
	    	int c = 0;
	    	item.setText(c++, player.getID());
	    	item.setText(c++, player.getFT());
	    	item.setText(c++, player.getORG());
	    	item.setText(c++, player.getTBNM());
	    	item.setText(c++, player.getMSG());
	    	item.setText(c++, player.getDTM());
	    	item.setText(c++, player.getID_T());
	    	item.setText(c++, player.getTABEL());
	    }
	    table.setSortDirection(dir);
	    table.setRedraw(true);
  	}

	static void ckrow(Table table) {
	    int maxrows=DisplayMain.g_maxrowinfo;
	    System.out.println("maxrows"+maxrows);
	    int currentrows=0;
	    int resrows=0;
	    currentrows=table.getItemCount();
	    resrows=currentrows-maxrows;
	    System.out.println(table.getItemCount()+" rows "+"maxrows"+maxrows+" currentrows"+currentrows+" resrows"+resrows);
	    if (resrows>0) {
	    	int itemdl=currentrows-1;
			for (int iLoop=0;iLoop<resrows;iLoop++) {
	    		table.remove(itemdl,itemdl);
	    		System.out.println("row"+itemdl+" removed");
	    		itemdl--;
	    	}
	    }
	    System.out.println("end check rows");
	}
}	
