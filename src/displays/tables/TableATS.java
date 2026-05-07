package displays.tables;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import readwrite.ReadFromFile;
import readwrite.WriteToDOC;
import readwrite.WriteToDOCX;
import readwrite.WriteToPDF;
import setting.ButtonsSetting;
import setting.Colors;
import setting.ErrMsg;
import setting.Shells;
import setting.Shorten;
import setting.Times;
import threads.SendDtgram;
import actions.RefreshTable;
import actions.ViewATSFunction;
import actions.cProgressBar;
import databases.jdbc;
import displays.DialogFactory;
import displays.MainForm;
import displays.TeleSplashScreen2016IP;
import displays.forms.ATSForms;

public class TableATS {
	@SuppressWarnings("unchecked")
	private java.util.List playersATS;
	private PlayerATSComparatorATS comparatorATS;
	
//	public static ComposeWindow window = new ComposeWindow(true);
	ButtonsSetting bs = new ButtonsSetting();
	Times cAts = new Times();
	ViewATSFunction vaf = new ViewATSFunction();
	WriteToPDF writetopdf = new WriteToPDF();
	Shorten sh = new Shorten();
	ReadFromFile rff = new ReadFromFile();
	ErrMsg em = new ErrMsg(); 
	ViewATSFunction vATS = new ViewATSFunction();
	
	//untuk refresh table
	Shell shell;
	static String pDate="",pDateTo="",pCid="",pSeqFr="",pSeqTo="",pMsgAll="",pFiled="",pIO="";//,lines="";
	//untuk table
	Connection conn;
	Statement stmt;
	static Table table;
	TableItem[] selection;
	Button next, prev, first, last;//, go;
	Text tPage, tTotPage, tGoToHal;
	static String //id="",P0="",P1="",P2="",P3="",P4="",P5="",P6="",P7="",P8="",P9="",
	tbl_name="",sqlSelect="",string="",isiHal="",jml_item="";
	static int baris=20,qq=0,jumlah=0,rowNo=0,qu=0;
	int dir=0,p=0,yu=0;
	int hasil=0;	
	//periode pencarian tabel (e.g. : 2010_06 s.d. 2011_04)
	Connection conn1[];
	ResultSet rs[];
	int iCnt=0; //counter ResultSet
	String note="",tblName="",id_ats="",msgType="",yearMonth="",yearFr="",monthFr="",yearTo="",monthTo="",select="",where="",order="";
	String lihatsql="",tgl="",tglTo="",id12="",id34="",id56="",loc="",time="",issued="",corr="",free="",critCid="",
	critFiled="",critSeq="",critIO="",jum="";
	static String line="";//,freeamo="";//"\n------------------------------\n";

	Text tCid_Search,tSeqFr_Search,tSeqTo_Search,tFreeText_Search,tFrom_Search,tTo_Search,tFiled_Search;
	Button bIncoming_Search,bOutgoing_Search;
	
	Button View,Edit,Delete,DeleteAll,Pdf,Pdfs,Word,Words;
	String strr="ATS";
	
	public TableATS() {
//		freeamo = rff.ReadInputFile1("/tp/template/freeamo.txt");
//		lines="-";
//		ReadFromFile.readConfiguration();
//		for (int i=0; i<ReadFromFile.getPDFLine(); i++) {
//			lines+="-";
//		}
	}

	public void koneksiDB(Shell pShell,String Date,String DateTo,String Cid,String SeqFr,String SeqTo,String Freetext,String filed,String io) {
		shell=pShell;
		pDate=Date;
		pDateTo=DateTo;
		pCid=Cid;
		pSeqFr=SeqFr;
		pSeqTo=SeqTo;
		pMsgAll=Freetext;
		pFiled=filed;
		pIO=io;
		// ------------------------------------ KONEKSI DB ------------------------------------
		try { conn = jdbc.getDBConnection(); } 
		catch (Exception e) { e.printStackTrace(); }
		
		final cProgressBar progbr = new cProgressBar("Selecting data ...");
		new Thread() {
			public void run() {
				if (TeleSplashScreen2016IP.display.isDisposed()) return;
				TeleSplashScreen2016IP.display.asyncExec(new Runnable() {
					@SuppressWarnings("unchecked")
					public void run() {
						try {
							progbr.create(baris);
							// Create the comparatorATS used for sorting
						    comparatorATS = new PlayerATSComparatorATS();
						    comparatorATS.setColumn(PlayerATSComparatorATS.TGL);
						    comparatorATS.setDirection(PlayerATSComparatorATS.ASCENDING);
					    
						    // Create the playersATS
						    playersATS = new ArrayList();
						    
						    select = "SELECT * FROM ";
							//where = " WHERE tgl!='0000-00-00 00:00:00'";
							where = " WHERE id_ats!=0";
							order = " ORDER BY id_ats DESC,tgl DESC";
							
							cAts.tgl();
							System.out.println("\nDate Now::" + cAts.tg);
							yearMonth = cAts.tg.substring(0, 7);
							if (yearMonth.contains("-")) yearMonth = yearMonth.replace("-", "_");
							tblName="air_message"+yearMonth;
						
							//tgl update20101119 [pencarian bisa sampai tahun, bulan, tanggal, menit, detik]
							if (pDate.compareTo("0000-00-00 00:00") != 0) { //tampilkan data dari Date/Time ini
								cAts.Date(pDate);
								tgl = " AND tgl>='"+cAts.Date+"'"; 
							
								if (cAts.Date.length()>=4) yearFr = cAts.Date.substring(0, 4); else yearFr=yearMonth.substring(0, 4);
								if (cAts.Date.length()>=7) monthFr = cAts.Date.substring(5, 7); else monthFr="01";
								//Field Date/Time To tidak diisi. -update 20101206
								if (pDateTo.compareTo("0000-00-00 00:00") == 0) {
									yearTo=yearMonth.substring(0, 4);
									monthTo=yearMonth.substring(5, 7);
		
									String ddhhmm = cAts.tanggal.substring(7, cAts.tanggal.length());
									cAts.DateTo(yearTo+"-"+monthTo+ddhhmm);
									tglTo = " AND tgl<='"+cAts.Date+"'";
								}
							} 
							if (pDateTo.compareTo("0000-00-00 00:00") != 0) { //tampilkan data sampai Date/Time ini
								cAts.DateTo(pDateTo);
								tglTo = " AND tgl<='"+cAts.Date+"'";
								
								if (cAts.Date.length()>=4) yearTo = cAts.Date.substring(0, 4); else yearTo=yearMonth.substring(0, 4);
								if (cAts.Date.length()>=7) monthTo = cAts.Date.substring(5, 7); else monthTo="12"; 
								String yeartoo=yearTo;
								//Field Date/Time From tidak diisi. 
								if (pDate.compareTo("0000-00-00 00:00") == 0) {
									yearFr=yeartoo;//yearMonth.substring(0, 4);
									monthFr="01";//yearMonth.substring(5, 7);
									tgl = " AND tgl>='"+yeartoo+"-01-01 00:00:00"/*cAts.Date*/+"'";
								}
							}
							if (pDate.compareTo("0000-00-00 00:00") == 0 && pDateTo.compareTo("0000-00-00 00:00") == 0) { //tampilkan data hari ini
								tgl = " AND tgl>='"+cAts.tg+" 00:00:00'"; 
								tglTo = " AND tgl<='"+cAts.tg+" 23:59:59'";
								tblName="air_message"+yearMonth;
								yearFr=yearTo=yearMonth.substring(0, 4);
								monthFr=monthTo=yearMonth.substring(5, 7);
							}
							//free - PERCOBAAN
							if (!pMsgAll.isEmpty()) {
								String kriteria="";
					            
								//OR
					            if (pMsgAll.contains("/ ")) pMsgAll=pMsgAll.replaceAll("/ ", "/");
					            if (pMsgAll.contains(" /")) pMsgAll=pMsgAll.replaceAll(" /", "/");
					            if (pMsgAll.contains(" / ")) pMsgAll=pMsgAll.replaceAll(" / ", "/");
					            //AND
					            if (pMsgAll.contains(", ")) pMsgAll=pMsgAll.replaceAll(", ", ",");
					            if (pMsgAll.contains(" ,")) pMsgAll=pMsgAll.replaceAll(" ,", ",");
					            if (pMsgAll.contains(" , ")) pMsgAll=pMsgAll.replaceAll(" , ", ",");
					            
					            //TANPA OR-AND
					            if (!pMsgAll.contains("/") && !pMsgAll.contains(",")) { 
					            	System.err.println("masuk 0");
//					            	kriteria = " AND manual_ats LIKE '%"+pMsgAll+"%'";
					            	kriteria = " AND (msgall LIKE '%"+pMsgAll+"%' " +
				            			"OR priority='"+pMsgAll+"' OR originator LIKE '"+pMsgAll+"%' " +
				            			"OR destination1 LIKE '"+pMsgAll+"%' OR destination2 LIKE '"+pMsgAll+"%' " +
				            			"OR destination3 LIKE '"+pMsgAll+"%' OR destination4 LIKE '"+pMsgAll+"%' " +
				            			"OR destination5 LIKE '"+pMsgAll+"%' OR destination6 LIKE '"+pMsgAll+"%' " +
				            			"OR destination7 LIKE '"+pMsgAll+"%' OR destination8 LIKE '"+pMsgAll+"%' " +
				            			"OR destination9 LIKE '"+pMsgAll+"%' OR destination10 LIKE '"+pMsgAll+"%' " +
				            			"OR destination11 LIKE '"+pMsgAll+"%' OR destination12 LIKE '"+pMsgAll+"%' " +
				            			"OR destination13 LIKE '"+pMsgAll+"%' OR destination14 LIKE '"+pMsgAll+"%' " +
				            			"OR destination15 LIKE '"+pMsgAll+"%' OR destination16 LIKE '"+pMsgAll+"%' " +
				            			"OR destination17 LIKE '"+pMsgAll+"%' OR destination18 LIKE '"+pMsgAll+"%' " +
				            			"OR destination19 LIKE '"+pMsgAll+"%' OR destination20 LIKE '"+pMsgAll+"%' " +
				            			"OR destination21 LIKE '"+pMsgAll+"%')";
					            } 
					            // DENGAN OR
					            else if (pMsgAll.contains("/") && !pMsgAll.contains(",")) { 
					            	System.err.println("masuk 1");
					            	kriteria=" AND (";
									String sarrOR[] = pMsgAll.split("/");
									for (int idOR=0;idOR<sarrOR.length;idOR++) {
										if (idOR!=0) { kriteria+=" OR "; }
//										kriteria+="manual_ats LIKE ";
//										kriteria+="'%"+sarrOR[idOR]+"%'";
										kriteria += "(msgall LIKE '%"+sarrOR[idOR]+"%' " +
					            			"OR priority='"+sarrOR[idOR]+"' OR originator LIKE '"+sarrOR[idOR]+"%' " +
					            			"OR destination1 LIKE '"+sarrOR[idOR]+"%' OR destination2 LIKE '"+sarrOR[idOR]+"%' " +
					            			"OR destination3 LIKE '"+sarrOR[idOR]+"%' OR destination4 LIKE '"+sarrOR[idOR]+"%' " +
					            			"OR destination5 LIKE '"+sarrOR[idOR]+"%' OR destination6 LIKE '"+sarrOR[idOR]+"%' " +
					            			"OR destination7 LIKE '"+sarrOR[idOR]+"%' OR destination8 LIKE '"+sarrOR[idOR]+"%' " +
					            			"OR destination9 LIKE '"+sarrOR[idOR]+"%' OR destination10 LIKE '"+sarrOR[idOR]+"%' " +
					            			"OR destination11 LIKE '"+sarrOR[idOR]+"%' OR destination12 LIKE '"+sarrOR[idOR]+"%' " +
					            			"OR destination13 LIKE '"+sarrOR[idOR]+"%' OR destination14 LIKE '"+sarrOR[idOR]+"%' " +
					            			"OR destination15 LIKE '"+sarrOR[idOR]+"%' OR destination16 LIKE '"+sarrOR[idOR]+"%' " +
					            			"OR destination17 LIKE '"+sarrOR[idOR]+"%' OR destination18 LIKE '"+sarrOR[idOR]+"%' " +
					            			"OR destination19 LIKE '"+sarrOR[idOR]+"%' OR destination20 LIKE '"+sarrOR[idOR]+"%' " +
					            			"OR destination21 LIKE '"+sarrOR[idOR]+"%')";
									}
									kriteria+=")";            	
					            } 
					            // DENGAN AND
					            else if (pMsgAll.contains(",") && !pMsgAll.contains("/")) { 
					            	System.err.println("masuk 2");
					            	kriteria=" AND (";
									String sarrAND[] = pMsgAll.split(",");
									for (int idAND=0;idAND<sarrAND.length;idAND++) {
										if (idAND!=0) { kriteria+=" AND "; }
										kriteria += " (msgall LIKE '%"+sarrAND[idAND]+"%' " +
					            			"OR priority='"+sarrAND[idAND]+"' OR originator LIKE '"+sarrAND[idAND]+"%' " +
					            			"OR destination1 LIKE '"+sarrAND[idAND]+"%' OR destination2 LIKE '"+sarrAND[idAND]+"%' " +
					            			"OR destination3 LIKE '"+sarrAND[idAND]+"%' OR destination4 LIKE '"+sarrAND[idAND]+"%' " +
					            			"OR destination5 LIKE '"+sarrAND[idAND]+"%' OR destination6 LIKE '"+sarrAND[idAND]+"%' " +
					            			"OR destination7 LIKE '"+sarrAND[idAND]+"%' OR destination8 LIKE '"+sarrAND[idAND]+"%' " +
					            			"OR destination9 LIKE '"+sarrAND[idAND]+"%' OR destination10 LIKE '"+sarrAND[idAND]+"%' " +
					            			"OR destination11 LIKE '"+sarrAND[idAND]+"%' OR destination12 LIKE '"+sarrAND[idAND]+"%' " +
					            			"OR destination13 LIKE '"+sarrAND[idAND]+"%' OR destination14 LIKE '"+sarrAND[idAND]+"%' " +
					            			"OR destination15 LIKE '"+sarrAND[idAND]+"%' OR destination16 LIKE '"+sarrAND[idAND]+"%' " +
					            			"OR destination17 LIKE '"+sarrAND[idAND]+"%' OR destination18 LIKE '"+sarrAND[idAND]+"%' " +
					            			"OR destination19 LIKE '"+sarrAND[idAND]+"%' OR destination20 LIKE '"+sarrAND[idAND]+"%' " +
					            			"OR destination21 LIKE '"+sarrAND[idAND]+"%')";
									}
									kriteria+=")";  
					            } 
					            // DENGAN OR-AND
					            else if (pMsgAll.contains("/") && pMsgAll.contains(",")) {
					            	String tempMsgAl = pMsgAll;
					            	
					            	kriteria=" AND (";
									String sarrOR[] = tempMsgAl.split("/");
									for (int idOR=0;idOR<sarrOR.length;idOR++) {
										if (idOR!=0) { kriteria+=" OR"; }

										String sand="";
										if (sarrOR[idOR].contains(",")) {
											sand=" (";
											String sarrAND[] = sarrOR[idOR].split(",");
											for (int idAND=0;idAND<sarrAND.length;idAND++) {
												if (idAND!=0) { sand+=" AND "; }
												//sand+="msgall LIKE '%"+sarrAND[idAND]+"%'";
												sand += " (msgall LIKE '%"+sarrAND[idAND]+"%' " +
							            			"OR priority='"+sarrAND[idAND]+"' OR originator LIKE '"+sarrAND[idAND]+"%' " +
							            			"OR destination1 LIKE '"+sarrAND[idAND]+"%' OR destination2 LIKE '"+sarrAND[idAND]+"%' " +
							            			"OR destination3 LIKE '"+sarrAND[idAND]+"%' OR destination4 LIKE '"+sarrAND[idAND]+"%' " +
							            			"OR destination5 LIKE '"+sarrAND[idAND]+"%' OR destination6 LIKE '"+sarrAND[idAND]+"%' " +
							            			"OR destination7 LIKE '"+sarrAND[idAND]+"%' OR destination8 LIKE '"+sarrAND[idAND]+"%' " +
							            			"OR destination9 LIKE '"+sarrAND[idAND]+"%' OR destination10 LIKE '"+sarrAND[idAND]+"%' " +
							            			"OR destination11 LIKE '"+sarrAND[idAND]+"%' OR destination12 LIKE '"+sarrAND[idAND]+"%' " +
							            			"OR destination13 LIKE '"+sarrAND[idAND]+"%' OR destination14 LIKE '"+sarrAND[idAND]+"%' " +
							            			"OR destination15 LIKE '"+sarrAND[idAND]+"%' OR destination16 LIKE '"+sarrAND[idAND]+"%' " +
							            			"OR destination17 LIKE '"+sarrAND[idAND]+"%' OR destination18 LIKE '"+sarrAND[idAND]+"%' " +
							            			"OR destination19 LIKE '"+sarrAND[idAND]+"%' OR destination20 LIKE '"+sarrAND[idAND]+"%' " +
							            			"OR destination21 LIKE '"+sarrAND[idAND]+"%')";
											}
											sand+=")";  
										} else {
											sand=" (";
											sand += "(msgall LIKE '%"+sarrOR[idOR]+"%' " +
						            			"OR priority='"+sarrOR[idOR]+"' OR originator LIKE '"+sarrOR[idOR]+"%' " +
						            			"OR destination1 LIKE '"+sarrOR[idOR]+"%' OR destination2 LIKE '"+sarrOR[idOR]+"%' " +
						            			"OR destination3 LIKE '"+sarrOR[idOR]+"%' OR destination4 LIKE '"+sarrOR[idOR]+"%' " +
						            			"OR destination5 LIKE '"+sarrOR[idOR]+"%' OR destination6 LIKE '"+sarrOR[idOR]+"%' " +
						            			"OR destination7 LIKE '"+sarrOR[idOR]+"%' OR destination8 LIKE '"+sarrOR[idOR]+"%' " +
						            			"OR destination9 LIKE '"+sarrOR[idOR]+"%' OR destination10 LIKE '"+sarrOR[idOR]+"%' " +
						            			"OR destination11 LIKE '"+sarrOR[idOR]+"%' OR destination12 LIKE '"+sarrOR[idOR]+"%' " +
						            			"OR destination13 LIKE '"+sarrOR[idOR]+"%' OR destination14 LIKE '"+sarrOR[idOR]+"%' " +
						            			"OR destination15 LIKE '"+sarrOR[idOR]+"%' OR destination16 LIKE '"+sarrOR[idOR]+"%' " +
						            			"OR destination17 LIKE '"+sarrOR[idOR]+"%' OR destination18 LIKE '"+sarrOR[idOR]+"%' " +
						            			"OR destination19 LIKE '"+sarrOR[idOR]+"%' OR destination20 LIKE '"+sarrOR[idOR]+"%' " +
						            			"OR destination21 LIKE '"+sarrOR[idOR]+"%')";
											sand+=")";
										}

										kriteria+=sand;
									}
									kriteria+=" )";  
									
					            }
					            free = kriteria;
							}
							
//							if (!free.isEmpty()) free=" AND"+free;
							
							
//							//free - ASLI
//							if (pMsgAll.compareTo("") != 0) {
//					            String[] sub = new String[1000];
//					            String beda="",aa="";
//		
//					            free = " AND";
//					            if (pMsgAll.contains(",")) { //kriteria yang dicari : banyak
//					            	sub = pMsgAll.split(",");
//						            for (int i = 0; i < sub.length; i++) {
//						            	if (sub[i].contains(" ")) sub[i] = sub[i].replaceFirst("\\s+", "");
//						            	String koma = " OR (msgall LIKE '%"+sub[i]+"%' " +
//						            			"OR priority='"+sub[i]+"' OR originator LIKE '"+sub[i]+"%' " +
//						            			"OR destination1 LIKE '"+sub[i]+"%' OR destination2 LIKE '"+sub[i]+"%' " +
//						            			"OR destination3 LIKE '"+sub[i]+"%' OR destination4 LIKE '"+sub[i]+"%' " +
//						            			"OR destination5 LIKE '"+sub[i]+"%' OR destination6 LIKE '"+sub[i]+"%' " +
//						            			"OR destination7 LIKE '"+sub[i]+"%' OR destination8 LIKE '"+sub[i]+"%' " +
//						            			"OR destination9 LIKE '"+sub[i]+"%' OR destination10 LIKE '"+sub[i]+"%' " +
//						            			"OR destination11 LIKE '"+sub[i]+"%' OR destination12 LIKE '"+sub[i]+"%' " +
//						            			"OR destination13 LIKE '"+sub[i]+"%' OR destination14 LIKE '"+sub[i]+"%' " +
//						            			"OR destination15 LIKE '"+sub[i]+"%' OR destination16 LIKE '"+sub[i]+"%' " +
//						            			"OR destination17 LIKE '"+sub[i]+"%' OR destination18 LIKE '"+sub[i]+"%' " +
//						            			"OR destination19 LIKE '"+sub[i]+"%' OR destination20 LIKE '"+sub[i]+"%' " +
//						            			"OR destination21 LIKE '"+sub[i]+"%')";
//						            	if (!koma.equals(beda)) {
//											aa+=koma+"";
//										}
//										beda=koma;
//						            }
//						            
//						            if (aa.startsWith(" OR ")) {
//						            	aa = aa.replaceFirst(" OR ", "");
//						            	free+=" ("+aa+")";
//						            }
//					            } else if (pMsgAll.contains("&")) { //berpasangan
//					            	free = " AND msgall LIKE '%";
//					            	sub = pMsgAll.split("&");
//						            for (int i = 0; i < sub.length; i++) {
//						            	if (sub[i].contains(" ")) sub[i] = sub[i].replaceFirst("\\s+", "");
//						            	System.out.println("sub[" + i + "]" + sub[i]);
//						            	String koma = sub[i]+"%";
//						            	if (!koma.equals(beda)) {
//											aa+=koma;
//										}
//										beda=koma;
//						            }
//						            
//						            free+=aa+"'";
//					            } 
//					            else { //yang dicari : satu
////					            	free+=" msgall LIKE '%"+pMsgAll+"%'";
//					            	free+=" (msgall LIKE '%"+pMsgAll+"%' " +
//			            			"OR priority='"+pMsgAll+"' OR originator LIKE '"+pMsgAll+"%' " +
//			            			"OR destination1 LIKE '"+pMsgAll+"%' OR destination2 LIKE '"+pMsgAll+"%' " +
//			            			"OR destination3 LIKE '"+pMsgAll+"%' OR destination4 LIKE '"+pMsgAll+"%' " +
//			            			"OR destination5 LIKE '"+pMsgAll+"%' OR destination6 LIKE '"+pMsgAll+"%' " +
//			            			"OR destination7 LIKE '"+pMsgAll+"%' OR destination8 LIKE '"+pMsgAll+"%' " +
//			            			"OR destination9 LIKE '"+pMsgAll+"%' OR destination10 LIKE '"+pMsgAll+"%' " +
//			            			"OR destination11 LIKE '"+pMsgAll+"%' OR destination12 LIKE '"+pMsgAll+"%' " +
//			            			"OR destination13 LIKE '"+pMsgAll+"%' OR destination14 LIKE '"+pMsgAll+"%' " +
//			            			"OR destination15 LIKE '"+pMsgAll+"%' OR destination16 LIKE '"+pMsgAll+"%' " +
//			            			"OR destination17 LIKE '"+pMsgAll+"%' OR destination18 LIKE '"+pMsgAll+"%' " +
//			            			"OR destination19 LIKE '"+pMsgAll+"%' OR destination20 LIKE '"+pMsgAll+"%' " +
//			            			"OR destination21 LIKE '"+pMsgAll+"%')";
//					            }
//							}
							//Filed
							if (pFiled.compareTo("") != 0) { critFiled = " AND filedby LIKE '"+pFiled+"%'"; } else { critFiled=""; }
							//CID
							if (pCid.compareTo("") != 0) { critCid = " AND cid LIKE '"+pCid+"%'"; } else { critCid=""; }
							//SEQ
							String seqfr="",seqto="";
							if (pSeqFr.compareTo("") != 0) { seqfr = " AND seq >= '"+pSeqFr+"'"; } else seqfr="";
							if (pSeqTo.compareTo("") != 0) { seqto = " AND seq <= '"+pSeqTo+"'"; } else seqto="";
							critSeq=seqfr+seqto;
							
							//incoming/outgoing
							if (pIO.compareTo("") != 0) { critIO = " AND io='"+pIO+"'"; } else { critIO=""; }

							jumlah=0; //inisialisasi
							iCnt=0;
							rowNo = 0;
							//menentukan tblName berdasarkan Date/Time
							System.out.println("Date From::" + yearFr + "-" + monthFr + "*");
							System.out.println("Date To::" + yearTo + "-" + monthTo + "*");
							
							conn1 = new Connection[Integer.parseInt(yearTo)*Integer.parseInt(monthTo)];
							rs = new ResultSet[Integer.parseInt(yearTo)*Integer.parseInt(monthTo)];
	
							boolean flg=false;
							for (int iYear=Integer.parseInt(yearFr); iYear<=Integer.parseInt(yearTo); iYear++) {
								int iMtemp=0;
								int n=0;
								int nn=0;
								int iMonth=0;
								
								tblName = "air_message"+iYear;
								if (iYear==Integer.parseInt(yearFr)) {
									iMtemp=Integer.parseInt(monthFr);
									n=12-iMtemp;
								} else if ((iYear>Integer.parseInt(yearFr)) && (iYear < Integer.parseInt(yearTo))) {
									iMtemp=1;
									n=12-iMtemp;
								} else if (iYear == Integer.parseInt(yearTo)) {
									iMtemp= Integer.parseInt(monthTo);
									n=iMtemp;
									iMtemp=0;
									flg=true;
								}
							
								for (iMonth=iMtemp; iMonth<=n+iMtemp; iMonth++) {
									if ((flg) && (iMonth>=12)) { 
										nn++; 
										if ((n==iMtemp) && (nn>12)) break; 
									} else nn=iMonth;
									
									String sMonth = Integer.toString(nn);
									if (sMonth.length()==1) sMonth = sMonth.replace(sMonth, "0"+sMonth);
									tblName = tblName.replace(tblName, "air_message"+iYear+"_"+sMonth);
									
									conn1[iCnt] = conn;
									stmt = conn1[iCnt].createStatement();
								
									try { //biar kalo ada table yang doesn't exist ga mati.
										lihatsql = select+tblName+where+tgl+tglTo+free+critCid+critSeq+critFiled+critIO+order;
										System.out.println("\nQuery: " + lihatsql);
										rs[iCnt] = stmt.executeQuery(lihatsql);
										rs[iCnt].last();
										jumlah += rs[iCnt].getRow();
										rs[iCnt].beforeFirst();
									
										final String datatabel[][] = new String[baris][10];
										p = 0;
										String sID="", sCID="", sSEQ="", sMSG_TYPE="", sMANUAL_ATS="", sTGL="", sSTATUS="", sNOTE="",
										sFILED="",sTBL_NAME="";
										
										while (rs[iCnt].next() && (rowNo < baris)) {
											rowNo++;
											
											datatabel[p][0] = rs[iCnt].getString("id_ats"); sID=datatabel[p][0]; if (sID==null) sID="";
											datatabel[p][1] = rs[iCnt].getString("cid"); sCID=datatabel[p][1]; if (sCID==null) sCID="";
											datatabel[p][2] = rs[iCnt].getString("seq"); sSEQ=datatabel[p][2]; if (sSEQ==null) sSEQ="";
											datatabel[p][3] = rs[iCnt].getString("type3a"); sMSG_TYPE=datatabel[p][3]; if (sMSG_TYPE==null) sMSG_TYPE="";
											datatabel[p][4] = rs[iCnt].getString("msgall"); sMANUAL_ATS=datatabel[p][4]; if (sMANUAL_ATS==null) sMANUAL_ATS="";
											datatabel[p][5] = rs[iCnt].getString("tgl"); sTGL=datatabel[p][5].substring(0, 19); if (sTGL==null) sTGL="";
											datatabel[p][6] = rs[iCnt].getString("status"); sSTATUS=datatabel[p][6]; if (sSTATUS==null) sSTATUS="";
											datatabel[p][7] = rs[iCnt].getString("note"); sNOTE=datatabel[p][7]; if (sNOTE==null) sNOTE="";
											datatabel[p][8] = rs[iCnt].getString("filedby"); sFILED=datatabel[p][8]; if (sFILED==null) sFILED="";
											datatabel[p][9] = rs[iCnt].getString("tbl_name"); sTBL_NAME=datatabel[p][9]; if (sTBL_NAME==null) sTBL_NAME="";
											
											if (sMANUAL_ATS.contains("`")) sMANUAL_ATS = sMANUAL_ATS.replace("`", "'");
											if (sMANUAL_ATS.contains("\n")) {
												int index = sMANUAL_ATS.indexOf("\n");
//												System.out.println("index::" + index + "#");
												if (index!=0) sMANUAL_ATS = sMANUAL_ATS.substring(0, (index-1)).concat(" ...");
											} else {
												if (sMANUAL_ATS.length()>20) sMANUAL_ATS = sMANUAL_ATS.substring(0, 20).concat(" ...");
											}

											if (sMANUAL_ATS.startsWith("amo")) sMANUAL_ATS=sMANUAL_ATS.replaceFirst("amo", "");
											
											p++;
											playersATS.add(new PlayerATS(sID,sCID,sSEQ,sMSG_TYPE,sMANUAL_ATS,sTGL,sSTATUS,sNOTE,sFILED,sTBL_NAME));
											progbr.bar.setSelection(rowNo);

										} //end of while
									} catch (SQLException s) { // biar kalo ada table yang doesn't exist ga mati.
										System.out.println("Info: " + s.getMessage());
									}
									iCnt++; //counter rs
								} //end of for2
							} //end of for1
							progbr.finish();
			
//							if (getJumlah() != 0) {
								if (getJumlah()==0) DialogFactory.openInfoDialog(Shells.sh_tAFTN, "Search Message","Data not found, your search has no result !!");
								else {
								formSearch();
							    formList();
							    
							    sh.shellStyle(shell, "List of AFTN Messages");
							    shell.addListener(SWT.Close, new Listener() {
									public void handleEvent(Event event) {
										connClose();
										
										if (!Shells.sh_vAFTN.isDisposed()) Shells.sh_vAFTN.close();
										if (!Shells.sh_vALR.isDisposed()) Shells.sh_vALR.close();
										if (!Shells.sh_vRCF.isDisposed()) Shells.sh_vRCF.close();
										if (!Shells.sh_vFPL.isDisposed()) Shells.sh_vFPL.close();
										if (!Shells.sh_vDLA.isDisposed()) Shells.sh_vDLA.close();
										if (!Shells.sh_vCHG.isDisposed()) Shells.sh_vCHG.close();
										if (!Shells.sh_vCNL.isDisposed()) Shells.sh_vCNL.close();
										if (!Shells.sh_vDEP.isDisposed()) Shells.sh_vDEP.close();
										if (!Shells.sh_vARR.isDisposed()) Shells.sh_vARR.close();
										if (!Shells.sh_vCDN.isDisposed()) Shells.sh_vCDN.close();
										if (!Shells.sh_vCPL.isDisposed()) Shells.sh_vCPL.close();
										if (!Shells.sh_vEST.isDisposed()) Shells.sh_vEST.close();
										if (!Shells.sh_vACP.isDisposed()) Shells.sh_vACP.close();
										if (!Shells.sh_vLAM.isDisposed()) Shells.sh_vLAM.close();
										if (!Shells.sh_vRQP.isDisposed()) Shells.sh_vRQP.close();
										if (!Shells.sh_vRQS.isDisposed()) Shells.sh_vRQS.close();
										if (!Shells.sh_vSPL.isDisposed()) Shells.sh_vSPL.close();
									}
								});
								}
//					  		} else {
//					  			DialogFactory.openInfoDialog(Shells.sh_tAFTN, "Search Message","Data not found, your search has no result !!");
//					  		}
						} catch (SQLException s) {
							DialogFactory.openInfoDialog(Shells.sh_tAFTN, "Search Message", s.toString());
							s.printStackTrace();
							progbr.finish();
						} catch (java.lang.OutOfMemoryError hs) {
							DialogFactory.openInfoDialog(Shells.sh_tAFTN, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
							progbr.finish();
						}
					}
				});
			}
		}.start();
		progbr.begin();
	}
	
	void Next() {
		final cProgressBar progbr = new cProgressBar("Selecting data ...");
		new Thread() {
			public void run() {
				if (TeleSplashScreen2016IP.display.isDisposed()) return;
				TeleSplashScreen2016IP.display.asyncExec(new Runnable() {
					@SuppressWarnings("unchecked")
					public void run() {
						try {
							progbr.create(baris);
						    playersATS.clear();
							rowNo = 0;
							
							for (int i=0; i<iCnt; i++) {
								try {
									if (rs[i]!=null) {
										final String datatabel[][] = new String[baris][10];
										p = 0;
										String sID="", sCID="", sSEQ="", sMSG_TYPE="", sMANUAL_ATS="", sTGL="", sSTATUS="", sNOTE="",
										sFILED="",sTBL_NAME="";

										rs[i].beforeFirst();
										while (rs[i].next()) {
											rowNo++;
											if ((rowNo>(qq*baris)) && (rowNo<=((qq*baris)+baris))) {
												
												datatabel[p][0] = rs[i].getString("id_ats"); sID=datatabel[p][0]; if (sID==null) sID="";
												datatabel[p][1] = rs[i].getString("cid"); sCID=datatabel[p][1]; if (sCID==null) sCID="";
												datatabel[p][2] = rs[i].getString("seq"); sSEQ=datatabel[p][2]; if (sSEQ==null) sSEQ="";
												datatabel[p][3] = rs[i].getString("type3a"); sMSG_TYPE=datatabel[p][3]; if (sMSG_TYPE==null) sMSG_TYPE="";
												datatabel[p][4] = rs[i].getString("msgall"); sMANUAL_ATS=datatabel[p][4]; if (sMANUAL_ATS==null) sMANUAL_ATS="";
												datatabel[p][5] = rs[i].getString("tgl"); sTGL=datatabel[p][5].substring(0, 19); if (sTGL==null) sTGL="";
												datatabel[p][6] = rs[i].getString("status"); sSTATUS=datatabel[p][6]; if (sSTATUS==null) sSTATUS="";
												datatabel[p][7] = rs[i].getString("note"); sNOTE=datatabel[p][7]; if (sNOTE==null) sNOTE="";
												datatabel[p][8] = rs[i].getString("filedby"); sFILED=datatabel[p][8]; if (sFILED==null) sFILED="";
												datatabel[p][9] = rs[i].getString("tbl_name"); sTBL_NAME=datatabel[p][9]; if (sTBL_NAME==null) sTBL_NAME="";
												
												
												if (sMANUAL_ATS.contains("`")) sMANUAL_ATS = sMANUAL_ATS.replace("`", "'");
												if (sMANUAL_ATS.contains("\n")) {
													int index = sMANUAL_ATS.indexOf("\n");
//													System.out.println("index::" + index + "#");
													if (index!=0) sMANUAL_ATS = sMANUAL_ATS.substring(0, (index-1)).concat(" ...");
												} else {
													if (sMANUAL_ATS.length()>20) sMANUAL_ATS = sMANUAL_ATS.substring(0, 20).concat(" ...");
												}
												
												if (sMANUAL_ATS.startsWith("amo")) sMANUAL_ATS=sMANUAL_ATS.replaceFirst("amo", "");
												
												TableItem item = new TableItem(table, SWT.NONE);
												item.setText(0,sID);
												item.setText(1,sCID);
												item.setText(2,sSEQ);
												item.setText(3,sMSG_TYPE);
												item.setText(4,sMANUAL_ATS);
												item.setText(5,sTGL);
												item.setText(6,sSTATUS);
												item.setText(7,sNOTE);
												item.setText(8,sFILED);
												item.setText(9,sTBL_NAME);
												
												p++;
												playersATS.add(new PlayerATS(sID,sCID,sSEQ,sMSG_TYPE,sMANUAL_ATS,sTGL,sSTATUS,sNOTE,sFILED,sTBL_NAME));
												progbr.bar.setSelection(rowNo);
												
											} //end of if
											if (rowNo >= ((qq*baris)+baris)) break;
										} //end of while
										if (rowNo >= ((qq*baris)+baris)) break;
									} //end of if
								} catch (SQLException s) {
									DialogFactory.openInfoDialog(Shells.sh_tAFTN, "Search Message", s.getMessage());
									s.printStackTrace();
								} //enf of try
							} //end of for
							progbr.finish();
						} catch (java.lang.OutOfMemoryError hs) {
		        			hs.printStackTrace();
							DialogFactory.openInfoDialog(Shells.sh_tAFTN, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
							progbr.finish();
						} //end of try
					} //end of run
				}); //end of runnable
			}
		}.start();
		progbr.begin();
	}
	
	void searchData() {
		String io="";
		if (bIncoming_Search.getSelection()==true) io="0";
		else if (bOutgoing_Search.getSelection()==true) io="1";
		tFreeText_Search.setFocus();
		RefreshTable.refreshTableAFTN(tFrom_Search.getText(), tTo_Search.getText(), tCid_Search.getText(), tSeqFr_Search.getText(),
				tSeqTo_Search.getText(), tFreeText_Search.getText(), tFiled_Search.getText(), io);
	}

	void formSearch() {
		int iSpace=5;
		
		final Group Group = new Group(shell, SWT.NONE); sh.groupStyle(Group, 1, "");
		Composite c1 = new Composite(Group, SWT.NONE); sh.compositeStyle(c1, 26, SWT.LEFT, SWT.CENTER);
		Label label = new Label(c1,SWT.NONE); sh.labelStyle1(label, "CID", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tCid_Search = new Text(c1,SWT.BORDER); sh.textStyle(tCid_Search, 30, 3, SWT.LEFT, SWT.LEFT, sh.letter, "Circuit ID", true);
		
		label = new Label(c1,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(c1,SWT.NONE); sh.labelStyle1(label, "Seq. From", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tSeqFr_Search = new Text(c1,SWT.BORDER); sh.textStyle(tSeqFr_Search, 40, 4, SWT.LEFT, SWT.LEFT, sh.numeric, "Sequence Number", true);
		label = new Label(c1,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(c1,SWT.NONE); sh.labelStyle1(label, "To", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tSeqTo_Search = new Text(c1,SWT.BORDER); sh.textStyle(tSeqTo_Search, 40, 4, SWT.LEFT, SWT.LEFT, sh.numeric, "Sequence Number", true);
		label = new Label(c1,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(c1,SWT.NONE); sh.labelStyle1(label, "Text", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFreeText_Search = new Text(c1,SWT.BORDER); sh.textStyle(tFreeText_Search, 150, 500, SWT.LEFT, SWT.LEFT, sh.upper, "Free Text Message", true);
		label = new Label(c1,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(c1,SWT.NONE); sh.labelStyle1(label, "From", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFrom_Search = new Text(c1,SWT.BORDER); 
		label = new Label(c1,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(c1,SWT.NONE); sh.labelStyle1(label, "To", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tTo_Search = new Text(c1,SWT.BORDER); 
		
		label = new Label(c1,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		label = new Label(c1,SWT.NONE); sh.labelStyle1(label, "Filled by", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		tFiled_Search = new Text(c1,SWT.BORDER); sh.textStyle(tFiled_Search, 100, 25, SWT.LEFT, SWT.LEFT, sh.upper, "Filed by", true);
		
		label = new Label(c1,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		bIncoming_Search = new Button(c1, SWT.RADIO); 
		bOutgoing_Search = new Button(c1, SWT.RADIO);
		label = new Label(c1,SWT.NONE); sh.labelStyle(label, "", iSpace, SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		Button Search = new Button(c1, SWT.PUSH);
		Button Clear = new Button(c1, SWT.PUSH);
		bs.SC(Search, Clear);

		
//		sh.textStyle(tFrom_Search, 120, 16, SWT.LEFT, SWT.LEFT, "", "Date/Time From (YYYY-MM-DD hh:mm)", true);
//		sh.textStyle(tTo_Search, 120, 16, SWT.LEFT, SWT.LEFT, "", "Date/Time To (YYYY-MM-DD hh:mm)", true);
//		sh.buttonRCStyle(bIncoming_Search, "In", "Incoming Message", 50);//85);//, 27);
//		sh.buttonRCStyle(bOutgoing_Search, "Out", "Outgoing Message",50);// 85);//, 27);
		
		sh.text(tFrom_Search, 16, SWT.CENTER, SWT.CENTER, "", "Date/Time From (YYYY-MM-DD hh:mm)", true);
		sh.text(tTo_Search, 16, SWT.CENTER, SWT.CENTER, "", "Date/Time To (YYYY-MM-DD hh:mm)", true);
		sh.RadioCheckStyle(bIncoming_Search, "In", "Incoming Message");
		sh.RadioCheckStyle(bOutgoing_Search, "Out", "Outgoing Message");
		
		final Calendar calFrom = Calendar.getInstance();
		cAts.tgl();
		tFrom_Search.setText(cAts.YYYY+"-"+cAts.MM+"-00 00:00");
		tFrom_Search.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; }//* [Y]YYY *
							case 1: { buffer.append('0'); break; }//* Y[Y]YY *
							case 2: { buffer.append('0'); break; }//* YY[Y]Y *
							case 3: { buffer.append('0'); break; }//* YYY[Y] *  
							case 4: { buffer.append('-'); break; }//* YYYY[-]MM * 
							case 5: { buffer.append('0'); break; }//* [M]M *
							case 6: { buffer.append('0'); break; }//* M[M] * 
							case 7: { buffer.append('-'); break; }//* MM[-]DD * 
							case 8: { buffer.append('0'); break; }//* [D]D *
							case 9: { buffer.append('0'); break; }//* D[D] * 
							case 10: { buffer.append(' '); break; }//*DD[ ]hh* 
							case 11: { buffer.append('0'); break; }//* [h]h *
							case 12: { buffer.append('0'); break; }//* h[h] *
							case 13: { buffer.append(':'); break; }//*hh[:]mm* 
							case 14: { buffer.append('0'); break; }//* [m]m *
							case 15: { buffer.append('0'); break; }//* m[m] *
							default: return;
						}
					}
					tFrom_Search.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tFrom_Search.insert(buffer.toString());
					ignore = false;
					tFrom_Search.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 15) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (start + index == 4 || start + index == 7) {
						if (chars[i] == '-') { index++; continue; }
						buffer.insert(index++, '-');
					}
					if (start + index == 10) {
						if (chars[i] == ' ') { index++; continue; }
						buffer.insert(index++, ' ');
					}
					if (start + index == 13) {
						if (chars[i] == ':') { index++; continue; }
						buffer.insert(index++, ':');
					}
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 5 &&  '1' < chars[i]) return; //* [M]M *
					if (start + index == 8 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 11 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 14 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tFrom_Search.getText());
				date.replace(e.start, e.start + length, newText);

				calFrom.set(Calendar.YEAR, 1901);
				calFrom.set(Calendar.MONTH, Calendar.JANUARY);
				calFrom.set(Calendar.DATE, 1);
				calFrom.set(Calendar.HOUR_OF_DAY, 00);
				calFrom.set(Calendar.MINUTE, 00);
				
				String yyyy = date.substring(0, 4);
				if (yyyy.indexOf('0') == -1) {
					int year = Integer.parseInt(yyyy);
					calFrom.set(Calendar.YEAR, year);
				}
				String MM = date.substring(5, 7);
				if (MM.indexOf('0') == -1) {
					int month =  Integer.parseInt(MM) - 1;
					int maxMonth = calFrom.getActualMaximum(Calendar.MONTH);
					if (0 > month || month > maxMonth) return;
					calFrom.set(Calendar.MONTH, month);
				}
				String dd = date.substring(8,10);
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = calFrom.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					calFrom.set(Calendar.DATE, day);
				} 
				String hh = date.substring(11,13);
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = calFrom.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					calFrom.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(14,16);
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = calFrom.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					calFrom.set(Calendar.MINUTE, minute);
				}
				else {
					if (calFrom.get(Calendar.MONTH)  == Calendar.FEBRUARY) {
						char firstChar = date.charAt(8);
						if (firstChar != '0' && '2' < firstChar) return;
					}
				}
				tFrom_Search.setSelection(e.start, e.start + length);
				ignore = true;
				tFrom_Search.insert(newText);
				ignore = false;
			}
		});
		
		final Calendar calTo = Calendar.getInstance();
		tTo_Search.setText("0000-00-00 00:00");//YYYY-MM-DD hh:mm
		tTo_Search.addListener(SWT.Verify, new Listener() {
			boolean ignore;
			public void handleEvent(Event e) {
				if (ignore) return;
				e.doit = false;
				StringBuffer buffer = new StringBuffer(e.text);
				char[] chars = new char[buffer.length()];
				buffer.getChars(0, chars.length, chars, 0);
				if (e.character == '\b') {
					for (int i = e.start; i < e.end; i++) {
						switch (i) {
							case 0: { buffer.append('0'); break; }//* [Y]YYY *
							case 1: { buffer.append('0'); break; }//* Y[Y]YY *
							case 2: { buffer.append('0'); break; }//* YY[Y]Y *
							case 3: { buffer.append('0'); break; }//* YYY[Y] *  
							case 4: { buffer.append('-'); break; }//* YYYY[-]MM * 
							case 5: { buffer.append('0'); break; }//* [M]M *
							case 6: { buffer.append('0'); break; }//* M[M] * 
							case 7: { buffer.append('-'); break; }//* MM[-]DD * 
							case 8: { buffer.append('0'); break; }//* [D]D *
							case 9: { buffer.append('0'); break; }//* D[D] * 
							case 10: { buffer.append(' '); break; }//*DD[ ]hh* 
							case 11: { buffer.append('0'); break; }//* [h]h *
							case 12: { buffer.append('0'); break; }//* h[h] *
							case 13: { buffer.append(':'); break; }//*hh[:]mm* 
							case 14: { buffer.append('0'); break; }//* [m]m *
							case 15: { buffer.append('0'); break; }//* m[m] *
							default: return;
						}
					}
					tTo_Search.setSelection(e.start, e.start + buffer.length());
					ignore = true;
					tTo_Search.insert(buffer.toString());
					ignore = false;
					tTo_Search.setSelection(e.start, e.start);
					return;
				}
			
				int start = e.start;
				if (start > 15) return;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (start + index == 4 || start + index == 7) {
						if (chars[i] == '-') { index++; continue; }
						buffer.insert(index++, '-');
					}
					if (start + index == 10) {
						if (chars[i] == ' ') { index++; continue; }
						buffer.insert(index++, ' ');
					}
					if (start + index == 13) {
						if (chars[i] == ':') { index++; continue; }
						buffer.insert(index++, ':');
					}
					if (chars[i] < '0' || '9' < chars[i]) return;
					if (start + index == 5 &&  '1' < chars[i]) return; //* [M]M *
					if (start + index == 8 &&  '3' < chars[i]) return; //* [D]D *
					if (start + index == 11 &&  '2' < chars[i]) return; //* [h]h *
					if (start + index == 14 &&  '5' < chars[i]) return; //* [m]m *
					index++;
				}
				
				String newText = buffer.toString();
				int length = newText.length();
				StringBuffer date = new StringBuffer(tTo_Search.getText());
				date.replace(e.start, e.start + length, newText);

				calTo.set(Calendar.YEAR, 1901);
				calTo.set(Calendar.MONTH, Calendar.JANUARY);
				calTo.set(Calendar.DATE, 1);
				calTo.set(Calendar.HOUR_OF_DAY, 00);
				calTo.set(Calendar.MINUTE, 00);
				
				String yyyy = date.substring(0, 4);
				if (yyyy.indexOf('0') == -1) {
					int year = Integer.parseInt(yyyy);
					calTo.set(Calendar.YEAR, year);
				}
				String MM = date.substring(5, 7);
				if (MM.indexOf('0') == -1) {
					int month =  Integer.parseInt(MM) - 1;
					int maxMonth = calTo.getActualMaximum(Calendar.MONTH);
					if (0 > month || month > maxMonth) return;
					calTo.set(Calendar.MONTH, month);
				}
				String dd = date.substring(8,10);
				if (dd.indexOf('0') == -1) {
					int day = Integer.parseInt(dd);
					int maxDay = calTo.getActualMaximum(Calendar.DATE);
					if (1 > day || day > maxDay) return;
					calTo.set(Calendar.DATE, day);
				} 
				String hh = date.substring(11,13);
				if (hh.indexOf('0') == -1) {
					int hour = Integer.parseInt(hh);
					int maxHour = calTo.getActualMaximum(Calendar.HOUR_OF_DAY);
					if (0 > hour || hour > maxHour) return;
					calTo.set(Calendar.HOUR_OF_DAY, hour);
				}
				String mm = date.substring(14,16);
				if (mm.indexOf('0') == -1) {
					int minute = Integer.parseInt(mm);
					int maxMinute = calTo.getActualMaximum(Calendar.MINUTE);
					if (0 > minute || minute > maxMinute) return;
					calTo.set(Calendar.MINUTE, minute);
				}
				else {
					if (calTo.get(Calendar.MONTH)  == Calendar.FEBRUARY) {
						char firstChar = date.charAt(8);
						if (firstChar != '0' && '2' < firstChar) return;
					}
				}
				tTo_Search.setSelection(e.start, e.start + length);
				ignore = true;
				tTo_Search.insert(newText);
				ignore = false;
			}
		});
		
		tFiled_Search.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { if (((e.keyCode==SWT.CR) || (e.keyCode==16777296))) searchData(); } });
		
		tCid_Search.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { if (((e.keyCode==SWT.CR) || (e.keyCode==16777296))) searchData(); } });
		
		tSeqFr_Search.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { if (((e.keyCode==SWT.CR) || (e.keyCode==16777296))) searchData(); } });
		
		tSeqTo_Search.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { if (((e.keyCode==SWT.CR) || (e.keyCode==16777296))) searchData(); } });
		
		tFreeText_Search.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { if (((e.keyCode==SWT.CR) || (e.keyCode==16777296))) searchData(); } });
		
		tFrom_Search.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { if (((e.keyCode==SWT.CR) || (e.keyCode==16777296))) searchData(); } });
		
		tTo_Search.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { if (((e.keyCode==SWT.CR) || (e.keyCode==16777296))) searchData(); } });
		
		
		Search.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				searchData();
			}
		});
		
		Clear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				bIncoming_Search.setSelection(false);
				bOutgoing_Search.setSelection(false);
				
				tCid_Search.setText("");
				tSeqFr_Search.setText("");
				tSeqTo_Search.setText("");
				tFreeText_Search.setText("");
				tFiled_Search.setText("");
				
				cAts.tgl();
				tFrom_Search.setText(cAts.YYYY+"-"+cAts.MM+"-00 00:00");
				
				tTo_Search.setText("0000-00-00 00:00");
				String io="";
				if (bIncoming_Search.getSelection()==true) io="0";
				else if (bOutgoing_Search.getSelection()==true) io="1";
				tFreeText_Search.setFocus();
//				RefreshTable.refreshTableAFTN(tFrom_Search.getText(), tTo_Search.getText(), tCid_Search.getText(), tSeqFr_Search.getText(), tSeqTo_Search.getText(), tFreeText_Search.getText(), io);
			}
		});
		
		if (!pDate.isEmpty() && pDate.compareTo("0000-00-00 00:00")!=0) tFrom_Search.setText(pDate);
		else tFrom_Search.setText(cAts.YYYY+"-"+cAts.MM+"-00 00:00"); 
		if (!pDateTo.isEmpty() && pDateTo.compareTo("0000-00-00 00:00")!=0) tTo_Search.setText(pDateTo);
		else tTo_Search.setText("0000-00-00 00:00"); 
		if (!pCid.isEmpty()) tCid_Search.setText(pCid);
		if (!pSeqFr.isEmpty()) tSeqFr_Search.setText(pSeqFr);
		if (!pSeqTo.isEmpty()) tSeqTo_Search.setText(pSeqTo);
		if (!pMsgAll.isEmpty()) tFreeText_Search.setText(pMsgAll);
		if (!pIO.isEmpty() && pIO.compareTo("1")==0) bOutgoing_Search.setSelection(true);
		else if (!pIO.isEmpty() && pIO.compareTo("0")==0) bIncoming_Search.setSelection(true);
		if (!pFiled.isEmpty()) tFiled_Search.setText(pFiled);
	}

	private void formList() {
	    ReadFromFile.readConfiguration(); 
  		final Group composite = new Group(shell, SWT.NONE); sh.groupStyle(composite, 1, "");
  		table = new Table(composite, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
  		sh.tableStyle(table, true, true, ReadFromFile.getHeightTblATS(), MainForm.getWidthRightTop());
  		table.setFocus();
  		
  		Listener sortListener = new Listener() {
  			public void handleEvent(Event e) {
 	    		// determine new sort column and direction
 	        	TableColumn sortColumn = table.getSortColumn();
 	        	TableColumn currentColumn = (TableColumn) e.widget;
 	        	dir = table.getSortDirection();
 	        	
 	        	if (sortColumn == currentColumn) {
 	        		dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
 	        	} else {
 	        		table.setSortColumn(currentColumn);
 		            dir = SWT.UP;
 	        	}
 	    	}
 	    };
	    // Create each of the columns, adding an event listener 
	    // that will set the appropriate fields into the comparatorATS 
	    // and then call the fillTable helper method
  		String iWidth[] = null;
  		String iName[] = null;
  		String width = ReadFromFile.getWidthATS();
  		if (!width.isEmpty() && width.contains(",")) {
  			iWidth = width.split(",");
  		}
  		
  		String name = ReadFromFile.getColnmATS();
  		if (!name.isEmpty() && name.contains(",")) {
  			iName = name.split(",");
  			TableColumn[] columns = new TableColumn[iName.length];
  			for (int i=0; i<iName.length; i++) {
  				TableColumn tblcol = new TableColumn(table,SWT.LEFT); sh.tablecol(tblcol, iName[i], iName[i], Integer.parseInt(iWidth[i]), true);
  				columns[i] = tblcol;
  			    columns[i].addListener(SWT.Selection, sortListener);
  			    table.setSortColumn(columns[0]);
  			}
  			
  			//ID,Submission ID,Sequence Number,Subject,...,Attachment,I/O,Status,Date/Time,Table Name
  			columns[0].addSelectionListener(new SelectionAdapter() {
  				public void widgetSelected(SelectionEvent event) {
					comparatorATS.setColumn(PlayerATSComparatorATS.ID);
					comparatorATS.reverseDirection();
					fillTable(table);
				}
  			});
  			columns[1].addSelectionListener(new SelectionAdapter() {
  				public void widgetSelected(SelectionEvent event) {
					comparatorATS.setColumn(PlayerATSComparatorATS.CID);
					comparatorATS.reverseDirection();
					fillTable(table);
				}
  			});
  			columns[2].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					comparatorATS.setColumn(PlayerATSComparatorATS.SEQ);
					comparatorATS.reverseDirection();
					fillTable(table);
				}
			});
			columns[3].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					comparatorATS.setColumn(PlayerATSComparatorATS.MSG_TYPE);
					comparatorATS.reverseDirection();
					fillTable(table);
				}
			});
			columns[4].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					comparatorATS.setColumn(PlayerATSComparatorATS.MANUAL_ATS);
					comparatorATS.reverseDirection();
					fillTable(table);
				}
			});
			columns[5].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					comparatorATS.setColumn(PlayerATSComparatorATS.TGL);
					comparatorATS.reverseDirection();
					fillTable(table);
				}
			});
			columns[6].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					comparatorATS.setColumn(PlayerATSComparatorATS.STATUS);
					comparatorATS.reverseDirection();
					fillTable(table);
				}
			});
			columns[7].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					comparatorATS.setColumn(PlayerATSComparatorATS.NOTE);
					comparatorATS.reverseDirection();
					fillTable(table);
				}
			});
			columns[8].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					comparatorATS.setColumn(PlayerATSComparatorATS.FILED);
					comparatorATS.reverseDirection();
					fillTable(table);
				}
			});
			columns[9].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					comparatorATS.setColumn(PlayerATSComparatorATS.TBL_NAME);
					comparatorATS.reverseDirection();
					fillTable(table);
				}
			});
  		}
  		
  		// Do the initial fill of the table
	    fillTable(table);
	    table.setSortDirection(SWT.UP);
	      
	    // To get ID of the row
	    table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				string = "";
				selection = table.getSelection();
	          	//20101119
				id_ats="";
				tblName="";
				msgType="";
				for (int i=0; i<selection.length; i++) {
//					System.out.println("[id]:"+selection[i].getText(0));
//					System.out.println("[cid]:"+selection[i].getText(1));
//					System.out.println("[seq]:"+selection[i].getText(2));
//					System.out.println("[num]:"+selection[i].getText(3));
//					System.out.println("[fre]:"+selection[i].getText(4));
//					System.out.println("[tgl]:"+selection[i].getText(5));
//					System.out.println("[stt]:"+selection[i].getText(6));
//					System.out.println("[not]:"+selection[i].getText(7));
//					System.out.println("[fil]:"+selection[i].getText(8));
//					System.out.println("[tbl]:"+selection[i].getText(9));
					
					tblName = selection[i].getText(9);//table.getColumnCount()-1);
					id_ats = selection[i].getText(0);
					msgType = selection[i].getText(3);
					note = selection[i].getText(7);//table.getColumnCount()-2);
					string += selection [i] + " ";
				}
				
				String template="";
				if (msgType.compareTo("")==0) template = " WHERE";
				else if (msgType.compareTo("ALR")==0) template = " WHERE type3a='ALR' AND"; 
				else if (msgType.compareTo("RCF")==0) template = " WHERE type3a='RCF' AND";
				else if (msgType.compareTo("FPL")==0) template = " WHERE type3a='FPL' AND";
				else if (msgType.compareTo("DLA")==0) template = " WHERE type3a='DLA' AND";
				else if (msgType.compareTo("CHG")==0) template = " WHERE type3a='CHG' AND";
				else if (msgType.compareTo("CNL")==0) template = " WHERE type3a='CNL' AND";
				else if (msgType.compareTo("DEP")==0) template = " WHERE type3a='DEP' AND";
				else if (msgType.compareTo("ARR")==0) template = " WHERE type3a='ARR' AND";
				else if (msgType.compareTo("CDN")==0) template = " WHERE type3a='CDN' AND";
				else if (msgType.compareTo("CPL")==0) template = " WHERE type3a='CPL' AND";
				else if (msgType.compareTo("EST")==0) template = " WHERE type3a='EST' AND";
				else if (msgType.compareTo("ACP")==0) template = " WHERE type3a='ACP' AND";
				else if (msgType.compareTo("LAM")==0) template = " WHERE type3a='LAM' AND";
				else if (msgType.compareTo("RQP")==0) template = " WHERE type3a='RQP' AND";
				else if (msgType.compareTo("RQS")==0) template = " WHERE type3a='RQS' AND";
				else if (msgType.compareTo("SPL")==0) template = " WHERE type3a='SPL' AND";

				if (string.contains("TableItem {")) {
					string = string.replace("TableItem {", "");
					if (string.contains("}")) string = string.replaceAll("} ", "");
					sqlSelect="SELECT * FROM "+tblName+template+" id_ats="+id_ats; //untuk fungsi View,Edit
					
					jdbc.select(sqlSelect, tblName, msgType,"");
				}
			}
		});
	    
	    Composite crow = new Composite(composite, SWT.NONE); sh.compositeStyle(crow, 1, SWT.RIGHT, SWT.CENTER);
	    Label lrow = new Label(crow, SWT.NONE); 
		sh.labelStyle1(lrow, getJumlah() + " Element(s) in this table ", SWT.LEFT, SWT.CENTER, SWT.ITALIC | SWT.BOLD, Colors.MANDATORY);
		jdbc.setJmlData(getJumlah());

		Composite typeB = new Composite(composite, SWT.NONE); sh.compositeStyle(typeB, 1, SWT.CENTER, SWT.CENTER);
		Label label = new Label(typeB, SWT.SEPARATOR | SWT.HORIZONTAL); sh.labelStyle(label, "", MainForm.getWidthRightTop(), SWT.LEFT, SWT.DOWN, SWT.BOLD, Colors.Grey);
		
		Composite type1b = new Composite(composite, SWT.NONE); sh.compositeStyle(type1b, 10, SWT.CENTER, SWT.CENTER);
		Label hal = new Label(type1b,SWT.LEFT); sh.labelStyle1(hal, "Page", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		yu = 0; qq = 0;
		yu = qq+1;
		isiHal = "";
		isiHal = Integer.toString(yu);
		tPage = new Text(type1b, SWT.BORDER);
		tPage.setText(isiHal);
		hal = new Label(type1b,SWT.LEFT); sh.labelStyle1(hal, "of", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		int jml;
		if (jumlah % baris == 0) {
			jml = jumlah / baris;
		} else {
			jml = jumlah / baris + 1;
		}
		String jum = Integer.toString(jml);
		tTotPage = new Text(type1b, SWT.BORDER);
		tTotPage.setText(jum);

		first = new Button(type1b,SWT.PUSH);
		prev = new Button(type1b,SWT.PUSH);
		next = new Button(type1b,SWT.PUSH);
		last = new Button(type1b,SWT.PUSH);	
		hal = new Label(type1b,SWT.LEFT); sh.labelStyle1(hal, "Go to", SWT.LEFT, SWT.CENTER, SWT.NORMAL, Colors.NORMAL);
		
		tGoToHal = new Text(type1b, SWT.BORDER);
		
        if (jumlah % baris == 0) { hasil = jumlah/baris; }
        else { hasil = jumlah/baris+1; }
        
        if (hasil == 0) { disableListButton(false); }

        if (hasil == 1) {
        	first.setEnabled(false);
        	prev.setEnabled(false);
            next.setEnabled(false);
            last.setEnabled(false);
            
            tGoToHal.setEnabled(false);
        } 
		
		if (jumlah <= baris) {
			first.setEnabled(false);
			prev.setEnabled(false);
			next.setEnabled(false);
			last.setEnabled(false);
			
			tGoToHal.setEnabled(false);
		}
		
		Composite type1a = new Composite(composite, SWT.NONE); sh.compositeStyle(type1a, 8, SWT.CENTER, SWT.CENTER);
		View = new Button(type1a,SWT.PUSH); 
		Edit = new Button(type1a,SWT.PUSH);
		Delete = new Button(type1a,SWT.PUSH);
		DeleteAll = new Button(type1a,SWT.PUSH);
		Pdf = new Button(type1a,SWT.PUSH);
		Pdfs = new Button(type1a,SWT.PUSH);
		Word = new Button(type1a,SWT.PUSH);
		Words = new Button(type1a,SWT.PUSH);
		
		bs.Navigation(tPage, tTotPage, first, prev, next, last, tGoToHal);//, go);
		bs.ButtonTableAFTN(View, Edit, Delete, DeleteAll, Pdf, Pdfs, Word, Words);//, exXLS, exXLSes);
		addListener();
  	}
  	
  	@SuppressWarnings("unchecked")
	private void fillTable(Table table) {
	    // Turn off drawing to avoid flicker
	    table.setRedraw(false);
        
	    // We remove all the table entries, sort our rows, then add the entries
	    table.removeAll();
	    
	    Collections.sort(playersATS, comparatorATS);
	    for (Iterator itr = playersATS.iterator(); itr.hasNext();) {
	    	PlayerATS player = (PlayerATS) itr.next();
	    	TableItem item = new TableItem(table, SWT.NONE);
	    	int c = 0;
	    	item.setText(c++, player.getID());
	    	item.setText(c++, player.getCID());
	    	item.setText(c++, player.getSEQ());
	    	item.setText(c++, player.getMSG_TYPE());
	    	item.setText(c++, player.getMANUAL_ATS());
	    	item.setText(c++, player.getTGL());
			item.setText(c++, player.getSTATUS());
			item.setText(c++, player.getNOTE());
			item.setText(c++, player.getFILED());
			item.setText(c++, player.getTBL_NAME());
	    }
	    
	    // sort indicator
	    table.setSortDirection(dir);
        //table.clearAll();
        
	    // Turn drawing back on
	    table.setRedraw(true);
  	}

  	public static String akhir() { return isiHal; }
	public static String GetSqlSelect() { return sqlSelect; }
	public static String GetString() { return string; }
	public static int getJumlah() { return jumlah; }
	
	void setgo() {
		String sGoToHal = tGoToHal.getText();
        if ((tGoToHal.getText().equals("")) || (tGoToHal.getText().equals("0"))) {
            sGoToHal = "1";
            tGoToHal.setText("1");
        }
        
        int m = Integer.parseInt(sGoToHal);
        int nHasil = hasil-1;
        m-=1;

        if (m == 0) {
        	first.setEnabled(false);
        	prev.setEnabled(false);
            next.setEnabled(true);
            last.setEnabled(true);
        }
        if (m == nHasil) {
        	first.setEnabled(true);
        	prev.setEnabled(true);
        	next.setEnabled(false);
            last.setEnabled(false);
        }
        if ((m != 0) && (m != nHasil)) {
            first.setEnabled(true);
            prev.setEnabled(true);
            next.setEnabled(true);                        
            last.setEnabled(true);
        }
        if (m > nHasil) {
            m = nHasil;
            first.setEnabled(true);
        	prev.setEnabled(true);
        	next.setEnabled(false);
            last.setEnabled(false);
            tGoToHal.setText(Integer.toString(hasil));
        }

        m = m-1;
        qq = m;
        qq++;

        table.removeAll();
        int as=qq+1;
        isiHal=Integer.toString(as);
        tPage.setText(isiHal);
        Next();
	}
  	
  	void disableListButton(boolean b) {
  		View.setEnabled(b);
  		Edit.setEnabled(b);
  		Delete.setEnabled(b);
  		DeleteAll.setEnabled(b);
  		Pdf.setEnabled(b);
  		Pdfs.setEnabled(b);
  		Word.setEnabled(b);
  		Words.setEnabled(b);
  		
  		first.setEnabled(b);
    	prev.setEnabled(b);
        next.setEnabled(b);
        last.setEnabled(b);
        tGoToHal.setEnabled(b);
  	}
	
	void openEdit() {
		if ((selection.length == 0) || (sqlSelect.equals(""))) {
			DialogFactory.openInfoAtLeast(Shells.sh_tAFTN,DialogFactory.edit);
		} else if (selection.length > 1) {
			DialogFactory.openInfoEditMode(Shells.sh_tAFTN,DialogFactory.edit);
		} else if (selection.length == 1) {
			if (sqlSelect == null) { sqlSelect = ""; }
			if (sqlSelect != "") {
				jdbc.select2(GetSqlSelect(), tblName);

//				System.out.println("MEGAMEN msgType:" + msgType + "#\tNOTE:" + note + "#\tfreeamo:"+freeamo+"#");
				
				//AFTN messages
				if (msgType.compareTo("")==0 && note.compareToIgnoreCase("amo")!=0 && note.compareToIgnoreCase("freetext")!=0) { 
					RefreshTable.refreshNewAFTN("EditAFTN",strr); ATSForms.setCurrentAFTN(); }
				
				//AMO messages
				else if (msgType.compareTo("")==0 && note.compareToIgnoreCase("amo")==0 && 
						ReadFromFile.ReadInputFile1("/tp/template/freeamo.txt").compareTo("1")==0) { 
					RefreshTable.refreshNewAMO("EditAMO",strr); ATSForms.setCurrentAMO(); }
				else if (msgType.compareTo("")==0 && note.compareToIgnoreCase("amo")==0 && 
						ReadFromFile.ReadInputFile1("/tp/template/freeamo.txt").compareTo("0")==0) { 
					DialogFactory.openInfoDialog(Shells.sh_tAFTN, "Edit Message", "Cannot edit amo/freetext messages."); }
				
				//Freetext messages
				else if (msgType.compareTo("")==0 && note.compareToIgnoreCase("freetext")==0 && 
						ReadFromFile.ReadInputFile1("/tp/template/freeamo.txt").compareTo("1")==0) { 
					RefreshTable.refreshNewFREE("EditFREE",strr); ATSForms.setCurrentFREE(); }
				else if (msgType.compareTo("")==0 && note.compareToIgnoreCase("freetext")==0 && 
						ReadFromFile.ReadInputFile1("/tp/template/freeamo.txt").compareTo("0")==0) { 
					DialogFactory.openInfoDialog(Shells.sh_tAFTN, "Edit Message", "Cannot edit amo/freetext messages."); }

				//ATS messsages
				else if (msgType.compareTo("ALR")==0) { RefreshTable.refreshNewALR("EditALR",strr); ATSForms.setCurrentALR(); }
				else if (msgType.compareTo("RCF")==0) { RefreshTable.refreshNewRCF("EditRCF",strr); ATSForms.setCurrentRCF(); }
				else if (msgType.compareTo("FPL")==0) { RefreshTable.refreshNewFPL("EditFPL",strr); ATSForms.setCurrentFPL(); }
				else if (msgType.compareTo("DLA")==0) { RefreshTable.refreshNewDLA("EditDLA",strr); ATSForms.setCurrentDLA(); }
				else if (msgType.compareTo("CHG")==0) { RefreshTable.refreshNewCHG("EditCHG",strr); ATSForms.setCurrentCHG(); }
				else if (msgType.compareTo("CNL")==0) { RefreshTable.refreshNewCNL("EditCNL",strr); ATSForms.setCurrentCNL(); }
				else if (msgType.compareTo("DEP")==0) { RefreshTable.refreshNewDEP("EditDEP",strr); ATSForms.setCurrentDEP(); }
				else if (msgType.compareTo("ARR")==0) { RefreshTable.refreshNewARR("EditARR",strr); ATSForms.setCurrentARR(); }
				else if (msgType.compareTo("CDN")==0) { RefreshTable.refreshNewCDN("EditCDN",strr); ATSForms.setCurrentCDN(); }
				else if (msgType.compareTo("CPL")==0) { RefreshTable.refreshNewCPL("EditCPL",strr); ATSForms.setCurrentCPL(); }
				else if (msgType.compareTo("EST")==0) { RefreshTable.refreshNewEST("EditEST",strr); ATSForms.setCurrentEST(); }
				else if (msgType.compareTo("ACP")==0) { RefreshTable.refreshNewACP("EditACP",strr); ATSForms.setCurrentACP(); }
				else if (msgType.compareTo("LAM")==0) { RefreshTable.refreshNewLAM("EditLAM",strr); ATSForms.setCurrentLAM(); }
				else if (msgType.compareTo("RQP")==0) { RefreshTable.refreshNewRQP("EditRQP",strr); ATSForms.setCurrentRQP(); }
				else if (msgType.compareTo("RQS")==0) { RefreshTable.refreshNewRQS("EditRQS",strr); ATSForms.setCurrentRQS(); }
				else if (msgType.compareTo("SPL")==0) { RefreshTable.refreshNewSPL("EditSPL",strr); ATSForms.setCurrentSPL(); }
				sqlSelect = "";
			} else { DialogFactory.openInfoAtLeast(Shells.sh_tAFTN,DialogFactory.edit); }
		}	
	}
	
	void addListener() {
	    
		table.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				openEdit();
			}
		});
	    
		table.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					openEdit();
				}
			}
	    });
		
		first.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (first.getEnabled() == true) {
					first.setEnabled(false); prev.setEnabled(false); next.setEnabled(true);

					if (last.getEnabled() == false) {
						last.setEnabled(true);	
					}
				}
				table.removeAll();
				qq = 0;
				isiHal = Integer.toString(qq);
				tPage.setText("1");
				tGoToHal.setText("1");
				Next();
			}
		});
		
		prev.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (prev.getEnabled() == true) {
					next.setEnabled(true); first.setEnabled(true); last.setEnabled(true);
				}
				
				qq--;
				if (qq == 0) {
					prev.setEnabled(false); first.setEnabled(false);
				}
				
				if (qq < 0) {
					qq = 0;
				} else {
					table.removeAll();
					int as = Integer.parseInt(akhir().trim())-1;
					isiHal=Integer.toString(as);
					tPage.setText(isiHal);
					Next();	
				}
				tGoToHal.setText(isiHal);
			}
		});
		
		next.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				prev.setEnabled(true); first.setEnabled(true);

				qq++;
				int jml;
				if (jumlah % baris == 0) { 
					jml = jumlah/baris; 
				} else { 
					jml = jumlah/baris+1;
				}
				
				int all = jml;
				if (all-1 == qq) {
					next.setEnabled(false); last.setEnabled(false);
				}
				
				if (qq < jml) {
					table.removeAll();
					int as = qq + 1;
					isiHal = Integer.toString(as);
					tPage.setText(isiHal);
					Next();
				}
				tGoToHal.setText(isiHal);
			}
		});

		last.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				next.setEnabled(false); prev.setEnabled(true); first.setEnabled(true);
				
				table.removeAll();
				if (jumlah % baris == 0) qq = jumlah/baris - 1;
				else qq = jumlah/baris;
				
				isiHal = Integer.toString(qq+1);
				tPage.setText(isiHal);
				Next();
				last.setEnabled(false);
				tGoToHal.setText(isiHal);
			}
		});		
		
		tGoToHal.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				if (((e.keyCode == SWT.CR) || (e.keyCode == 16777296))) {
					setgo();
				}
			}
	    });
		
		View.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String allMsgType="";
				selection = table.getSelection(); 
				if (selection.length==0 || sqlSelect.equals("")) { DialogFactory.openInfoAtLeast(Shells.sh_tAFTN,DialogFactory.view); } 
				else {
					id_ats="";
					tblName="";
					String msg="",msgall="",printable="";
					for (int i=0; i<selection.length; i++) { //banyak baris yang akan di print
						tblName = selection[i].getText(table.getColumnCount()-1);
						id_ats = selection[i].getText(0);
						msgType = selection[i].getText(3);
						
						String template="";
						if (msgType.compareTo("")==0) template = " WHERE";
						else if (msgType.compareTo("ALR")==0) template = " WHERE type3a='ALR' AND"; 
						else if (msgType.compareTo("RCF")==0) template = " WHERE type3a='RCF' AND";
						else if (msgType.compareTo("FPL")==0) template = " WHERE type3a='FPL' AND";
						else if (msgType.compareTo("DLA")==0) template = " WHERE type3a='DLA' AND";
						else if (msgType.compareTo("CHG")==0) template = " WHERE type3a='CHG' AND";
						else if (msgType.compareTo("CNL")==0) template = " WHERE type3a='CNL' AND";
						else if (msgType.compareTo("DEP")==0) template = " WHERE type3a='DEP' AND";
						else if (msgType.compareTo("ARR")==0) template = " WHERE type3a='ARR' AND";
						else if (msgType.compareTo("CDN")==0) template = " WHERE type3a='CDN' AND";
						else if (msgType.compareTo("CPL")==0) template = " WHERE type3a='CPL' AND";
						else if (msgType.compareTo("EST")==0) template = " WHERE type3a='EST' AND";
						else if (msgType.compareTo("ACP")==0) template = " WHERE type3a='ACP' AND";
						else if (msgType.compareTo("LAM")==0) template = " WHERE type3a='LAM' AND";
						else if (msgType.compareTo("RQP")==0) template = " WHERE type3a='RQP' AND";
						else if (msgType.compareTo("RQS")==0) template = " WHERE type3a='RQS' AND";
						else if (msgType.compareTo("SPL")==0) template = " WHERE type3a='SPL' AND";
						String sel = "SELECT * FROM "+tblName+template+" id_ats="+id_ats;
						jdbc.select(sel, tblName, msgType, "");
						
						if (msgType.compareTo("")==0) msg = jdbc.getaftn();
						else if (msgType.compareTo("ALR")==0) msg = jdbc.getalr(); 
						else if (msgType.compareTo("RCF")==0) msg = jdbc.getrcf();
						else if (msgType.compareTo("FPL")==0) msg = jdbc.getfpl();
						else if (msgType.compareTo("DLA")==0) msg = jdbc.getdla();
						else if (msgType.compareTo("CHG")==0) msg = jdbc.getchg();
						else if (msgType.compareTo("CNL")==0) msg = jdbc.getcnl();
						else if (msgType.compareTo("DEP")==0) msg = jdbc.getdep();
						else if (msgType.compareTo("ARR")==0) msg = jdbc.getarr();
						else if (msgType.compareTo("CDN")==0) msg = jdbc.getcdn();
						else if (msgType.compareTo("CPL")==0) msg = jdbc.getcpl();
						else if (msgType.compareTo("EST")==0) msg = jdbc.getest();
						else if (msgType.compareTo("ACP")==0) msg = jdbc.getacp();
						else if (msgType.compareTo("LAM")==0) msg = jdbc.getlam();
						else if (msgType.compareTo("RQP")==0) msg = jdbc.getrqp();
						else if (msgType.compareTo("RQS")==0) msg = jdbc.getrqs();
						else if (msgType.compareTo("SPL")==0) msg = jdbc.getspl();
						
						if (msgall.compareTo(msg)!=0) {
							printable+=msg+line+"\n";
						} else {
							printable+=msg+line+"\n";
						}
						
						allMsgType+=","+msgType;
					}
					
					if (allMsgType.contains(",")) {
						int index = allMsgType.lastIndexOf(",");
						allMsgType = allMsgType.substring(0, index);
					}

					String titleview="";
					if (allMsgType.contains(",")) titleview = "View Selected Message(s)"; else titleview="";
					
					if (msgType.compareTo("")==0) vATS.vfree(printable,titleview,strr);
					else if (msgType.compareTo("ALR")==0) vATS.valr(printable,titleview,strr);
					else if (msgType.compareTo("RCF")==0) vATS.vrcf(printable,titleview,strr);
					else if (msgType.compareTo("FPL")==0) vATS.vfpl(printable,titleview,strr);
					else if (msgType.compareTo("DLA")==0) vATS.vdla(printable,titleview,strr);
					else if (msgType.compareTo("CHG")==0) vATS.vchg(printable,titleview,strr);
					else if (msgType.compareTo("CNL")==0) vATS.vcnl(printable,titleview,strr);
					else if (msgType.compareTo("DEP")==0) vATS.vdep(printable,titleview,strr);
					else if (msgType.compareTo("ARR")==0) vATS.varr(printable,titleview,strr);
					else if (msgType.compareTo("CDN")==0) vATS.vcdn(printable,titleview,strr);
					else if (msgType.compareTo("CPL")==0) vATS.vcpl(printable,titleview,strr);
					else if (msgType.compareTo("EST")==0) vATS.vest(printable,titleview,strr);
					else if (msgType.compareTo("ACP")==0) vATS.vacp(printable,titleview,strr);
					else if (msgType.compareTo("LAM")==0) vATS.vlam(printable,titleview,strr);
					else if (msgType.compareTo("RQP")==0) vATS.vrqp(printable,titleview,strr);
					else if (msgType.compareTo("RQS")==0) vATS.vrqs(printable,titleview,strr);
					else if (msgType.compareTo("SPL")==0) vATS.vspl(printable,titleview,strr);
					
				} //end of else
			}
		});
		
		Edit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selection = table.getSelection();
				openEdit();
			}
		});
		
		Delete.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selection = table.getSelection(); 
				if (selection.length==0 || sqlSelect.equals("")) { DialogFactory.openInfoAtLeast(Shells.sh_tAFTN,DialogFactory.delete); } 
				else {
					id_ats="";
					tblName="";
					
					DialogFactory.openYesNoDelete(Shells.sh_tAFTN);
					boolean tentu = DialogFactory.getPenentuan(); 
					if (tentu == true) {
						for (int i=0; i<selection.length; i++) { //banyak baris yang akan dihapus
							tblName = selection[i].getText(table.getColumnCount()-1);
							id_ats = selection[i].getText(0);
							//Delete di tabel masing-masing
							String dele = "DELETE FROM "+tblName+" WHERE id_ats="+id_ats;
							jdbc.delete(dele);
							//Delete di tabel check_status
							String del = "DELETE FROM check_status WHERE tbl_name='"+tblName+"' AND id="+id_ats;
							jdbc.delete(del);
						}

						//refreshing table
						if (getJumlah() > 1) {
							string=""; sqlSelect="";
							table.removeAll(); playersATS.clear();
							RefreshTable.refreshTableAFTN(pDate,pDateTo,pCid,pSeqFr,pSeqTo,pMsgAll,pFiled,pIO);	
						} else if (getJumlah() == 1) {
							string=""; sqlSelect=""; shell.close();
						}
						
						new SendDtgram("Queu,"+MainForm.query(TableOutgoing.queryQueue));
					} //end of if
					
					setTableFocus();
					
				} //end of else
			}
		});
		
		DeleteAll.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					tblName="";
				
					DialogFactory.openYesNoDelete(Shells.sh_tAFTN);
					boolean tentu = DialogFactory.getPenentuan(); 
					if (tentu == true) {
						jumlah=0; //inisialisasi
						iCnt=0;
						rowNo = 0;
						//menentukan tblName berdasarkan Date/Time
						System.out.println("Date From::" + yearFr + "-" + monthFr + "*");
						System.out.println("Date To::" + yearTo + "-" + monthTo + "*");
						
						conn1 = new Connection[Integer.parseInt(yearTo)*Integer.parseInt(monthTo)];
						rs = new ResultSet[Integer.parseInt(yearTo)*Integer.parseInt(monthTo)];
						
						for (int iYear=Integer.parseInt(yearFr); iYear<=Integer.parseInt(yearTo); iYear++) {
							tblName = "air_message"+iYear;
							
							int iMtemp=0;
							int n=0;
							boolean flg=false;
							if (iYear==Integer.parseInt(yearFr)) {
								iMtemp=Integer.parseInt(monthFr);
								n=12-iMtemp;
							}
							else if ((iYear>Integer.parseInt(yearFr)) && (iYear < Integer.parseInt(yearTo))) {
								iMtemp=1;
								n=12-iMtemp;
							}
							else if (iYear == Integer.parseInt(yearTo)) {
								iMtemp= Integer.parseInt(monthTo);
								n=iMtemp;
								iMtemp=0;
								flg=true;
							}
							
							int nn=0;
							for (int iMonth=iMtemp; iMonth<=n+iMtemp; iMonth++) {
								
								if ((flg) && (iMonth>=12)) { 
									nn++; 
									if ((n==iMtemp) && (nn>12)) break; 
								} else nn=iMonth;
								
								String sMonth = Integer.toString(nn);//iMonth);
								if (sMonth.length()==1) sMonth = sMonth.replace(sMonth, "0"+sMonth);
								tblName = tblName.replace(tblName, "air_message"+iYear+"_"+sMonth);

								conn1[iCnt] = conn;
								stmt = conn1[iCnt].createStatement();
								try { //biar kalo ada table yang doesn't exist ga mati.
									lihatsql = select+tblName+where+tgl+tglTo+free+critCid+critSeq+critIO+order;
									rs[iCnt] = stmt.executeQuery(lihatsql);

									while (rs[iCnt].next()) {
										rowNo++;
					
										String id = rs[iCnt].getString("id_ats"); if (id==null) { id=""; }
										String tgl = rs[iCnt].getString("tgl"); if (tgl==null){ tgl=""; } else tgl = tgl.substring(0, 19);
										String tbl_name = rs[iCnt].getString("tbl_name"); if (tbl_name==null) { tbl_name=""; }

										String del = "DELETE FROM check_status WHERE tbl_name='"+tbl_name+"' AND tgl='"+tgl+"' AND id="+id;
										jdbc.delete(del);
									} //end of while
									
									if (lihatsql.contains("SELECT * FROM")) lihatsql = lihatsql.replace("SELECT * FROM", "DELETE FROM");
									jdbc.delete(lihatsql);
								} catch (SQLException s) { // biar kalo ada table yang doesn't exist ga mati.
									System.out.println("Error: " + s.getMessage());
								}
								iCnt++; //counter rs
							} //end of for2
						} //end of for1
						shell.close();
					
						new SendDtgram("Queu,");
					} //end of if
					
					setTableFocus();
					
				} catch (SQLException s) {
					s.printStackTrace();
        		} catch (java.lang.OutOfMemoryError hs) {
        			hs.printStackTrace();
        		}
			}
		});
		
		Pdf.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//memilih baris yang akan di print
				selection = table.getSelection(); 
				if ((selection.length == 0) || (sqlSelect.equals(""))) {
					DialogFactory.openInfoAtLeast(Shells.sh_tAFTN,DialogFactory.export);
				} else {
									
					final cProgressBar progbr = new cProgressBar("Selecting data ...");
					new Thread() {
						public void run() {
							if (TeleSplashScreen2016IP.display.isDisposed()) return;
							TeleSplashScreen2016IP.display.asyncExec(new Runnable() {
								@SuppressWarnings("unchecked")
								public void run() {
									try {
										progbr.create(100);
	
										id_ats="";
										tblName="";
										String msg="",msgall="",printable="";
										for (int i=0; i<selection.length; i++) { //banyak baris yang akan di print
											tblName = selection[i].getText(table.getColumnCount()-1);
											id_ats = selection[i].getText(0);
											msgType = selection[i].getText(3);
											
											String template="";
											if (msgType.compareTo("")==0) template = " WHERE";
											else if (msgType.compareTo("ALR")==0) template = " WHERE type3a='ALR' AND"; 
											else if (msgType.compareTo("RCF")==0) template = " WHERE type3a='RCF' AND";
											else if (msgType.compareTo("FPL")==0) template = " WHERE type3a='FPL' AND";
											else if (msgType.compareTo("DLA")==0) template = " WHERE type3a='DLA' AND";
											else if (msgType.compareTo("CHG")==0) template = " WHERE type3a='CHG' AND";
											else if (msgType.compareTo("CNL")==0) template = " WHERE type3a='CNL' AND";
											else if (msgType.compareTo("DEP")==0) template = " WHERE type3a='DEP' AND";
											else if (msgType.compareTo("ARR")==0) template = " WHERE type3a='ARR' AND";
											else if (msgType.compareTo("CDN")==0) template = " WHERE type3a='CDN' AND";
											else if (msgType.compareTo("CPL")==0) template = " WHERE type3a='CPL' AND";
											else if (msgType.compareTo("EST")==0) template = " WHERE type3a='EST' AND";
											else if (msgType.compareTo("ACP")==0) template = " WHERE type3a='ACP' AND";
											else if (msgType.compareTo("LAM")==0) template = " WHERE type3a='LAM' AND";
											else if (msgType.compareTo("RQP")==0) template = " WHERE type3a='RQP' AND";
											else if (msgType.compareTo("RQS")==0) template = " WHERE type3a='RQS' AND";
											else if (msgType.compareTo("SPL")==0) template = " WHERE type3a='SPL' AND";
											String sel = "SELECT * FROM "+tblName+template+" id_ats="+id_ats;
											jdbc.select(sel, tblName, msgType, "print");
											
											if (msgType.compareTo("")==0) msg = jdbc.getaftn();
											else if (msgType.compareTo("ALR")==0) msg = jdbc.getalr(); 
											else if (msgType.compareTo("RCF")==0) msg = jdbc.getrcf();
											else if (msgType.compareTo("FPL")==0) msg = jdbc.getfpl();
											else if (msgType.compareTo("DLA")==0) msg = jdbc.getdla();
											else if (msgType.compareTo("CHG")==0) msg = jdbc.getchg();
											else if (msgType.compareTo("CNL")==0) msg = jdbc.getcnl();
											else if (msgType.compareTo("DEP")==0) msg = jdbc.getdep();
											else if (msgType.compareTo("ARR")==0) msg = jdbc.getarr();
											else if (msgType.compareTo("CDN")==0) msg = jdbc.getcdn();
											else if (msgType.compareTo("CPL")==0) msg = jdbc.getcpl();
											else if (msgType.compareTo("EST")==0) msg = jdbc.getest();
											else if (msgType.compareTo("ACP")==0) msg = jdbc.getacp();
											else if (msgType.compareTo("LAM")==0) msg = jdbc.getlam();
											else if (msgType.compareTo("RQP")==0) msg = jdbc.getrqp();
											else if (msgType.compareTo("RQS")==0) msg = jdbc.getrqs();
											else if (msgType.compareTo("SPL")==0) msg = jdbc.getspl();
											
											if (msgall.compareTo(msg)!=0) {
												printable+=msg+line+"\n";
											} else {
												printable+=msg+line+"\n";
											}
										}
										String lines="-";
										ReadFromFile.readConfiguration();
										for (int i=0; i<ReadFromFile.getPDFLine(); i++) { lines+="-"; }
										writetopdf.setPrint(printable, lines+"\n"+Integer.toString(selection.length), MainForm.sPath + "AFTNmsg.pdf");
										msg="";
										try {
											ReadFromFile.readConfiguration();
											String cmd = ReadFromFile.getPDFCmd() + " /tmp/ramdisk0/AFTNmsg.pdf";
											System.out.println(cmd);
											Runtime.getRuntime().exec(cmd);
										} catch (IOException ioe) {
											System.err.println("open pdf err: " + ioe.getMessage());
										}
										
										progbr.finish();
									} catch (java.lang.OutOfMemoryError hs) {
					        			hs.printStackTrace();
										DialogFactory.openInfoDialog(Shells.sh_tAFTN, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
										progbr.finish();
									} //end of try
								} //end of run
							}); //end of runnable
						}
					}.start();
					progbr.begin();	
				
				} //end of else
			}
		});
		
		Pdfs.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				final cProgressBar progbr = new cProgressBar("Selecting data ...");
				new Thread() {
					public void run() {
						if (TeleSplashScreen2016IP.display.isDisposed()) return;
						TeleSplashScreen2016IP.display.asyncExec(new Runnable() {
							@SuppressWarnings("unchecked")
							public void run() {
								try {
									progbr.create(100);

									String msg="",/*msgall="",*/printable="";
									try {
										tblName="";
									
//										int jumlah=0; //inisialisasi
										int iCnt=0;
										int rowNo = 0;
										//menentukan tblName berdasarkan Date/Time
										System.out.println("Date From::" + yearFr + "-" + monthFr + "*");
										System.out.println("Date To::" + yearTo + "-" + monthTo + "*");
										
										conn1 = new Connection[Integer.parseInt(yearTo)*Integer.parseInt(monthTo)];
										rs = new ResultSet[Integer.parseInt(yearTo)*Integer.parseInt(monthTo)];
										
										for (int iYear=Integer.parseInt(yearFr); iYear<=Integer.parseInt(yearTo); iYear++) {
											tblName = "air_message"+iYear;
											
											int iMtemp=0;
											int n=0;
											boolean flg=false;
											if (iYear==Integer.parseInt(yearFr)) {
												iMtemp=Integer.parseInt(monthFr);
												n=12-iMtemp;
											}
											else if ((iYear>Integer.parseInt(yearFr)) && (iYear < Integer.parseInt(yearTo))) {
												iMtemp=1;
												n=12-iMtemp;
											}
											else if (iYear == Integer.parseInt(yearTo)) {
												iMtemp= Integer.parseInt(monthTo);
												n=iMtemp;
												iMtemp=0;
												flg=true;
											}
											
											int nn=0;
											for (int iMonth=iMtemp; iMonth<=n+iMtemp; iMonth++) {
												
												if ((flg) && (iMonth>=12)) { 
													nn++; 
													if ((n==iMtemp) && (nn>12)) break; 
												} else nn=iMonth;
												
												String sMonth = Integer.toString(nn);
												if (sMonth.length()==1) sMonth = sMonth.replace(sMonth, "0"+sMonth);
												tblName = tblName.replace(tblName, "air_message"+iYear+"_"+sMonth);

												conn1[iCnt] = conn;
												stmt = conn1[iCnt].createStatement();
												Statement stmt = conn1[iCnt].createStatement();
												try { //biar kalo ada table yang doesn't exist ga mati.
													lihatsql = select+tblName+where+tgl+tglTo+free+critCid+critSeq+critIO+order;
													rs[iCnt] = stmt.executeQuery(lihatsql);

													while (rs[iCnt].next()) {
														rowNo++;
									
														String id = rs[iCnt].getString("id_ats"); if (id==null) { id=""; }
														String tgl = rs[iCnt].getString("tgl"); if (tgl==null){ tgl=""; } else tgl = tgl.substring(0, 19);
														String tbl_name = rs[iCnt].getString("tbl_name"); if (tbl_name==null) { tbl_name=""; }
														String msgType = rs[iCnt].getString("type3a"); if (tbl_name==null) { tbl_name=""; }

														String template="";
														if (msgType.compareTo("")==0) template = " WHERE";
														else if (msgType.compareTo("ALR")==0) template = " WHERE type3a='ALR' AND"; 
														else if (msgType.compareTo("RCF")==0) template = " WHERE type3a='RCF' AND";
														else if (msgType.compareTo("FPL")==0) template = " WHERE type3a='FPL' AND";
														else if (msgType.compareTo("DLA")==0) template = " WHERE type3a='DLA' AND";
														else if (msgType.compareTo("CHG")==0) template = " WHERE type3a='CHG' AND";
														else if (msgType.compareTo("CNL")==0) template = " WHERE type3a='CNL' AND";
														else if (msgType.compareTo("DEP")==0) template = " WHERE type3a='DEP' AND";
														else if (msgType.compareTo("ARR")==0) template = " WHERE type3a='ARR' AND";
														else if (msgType.compareTo("CDN")==0) template = " WHERE type3a='CDN' AND";
														else if (msgType.compareTo("CPL")==0) template = " WHERE type3a='CPL' AND";
														else if (msgType.compareTo("EST")==0) template = " WHERE type3a='EST' AND";
														else if (msgType.compareTo("ACP")==0) template = " WHERE type3a='ACP' AND";
														else if (msgType.compareTo("LAM")==0) template = " WHERE type3a='LAM' AND";
														else if (msgType.compareTo("RQP")==0) template = " WHERE type3a='RQP' AND";
														else if (msgType.compareTo("RQS")==0) template = " WHERE type3a='RQS' AND";
														else if (msgType.compareTo("SPL")==0) template = " WHERE type3a='SPL' AND";
														String sel = "SELECT * FROM "+tblName+template+" id_ats="+id;
														jdbc.select(sel, tblName, msgType, "print");
														
														String msgfree="",msgalr="",msgrcf="",msgfpl="",msgdla="",msgchg="",msgcnl="",msgdep="",msgarr="",msgcdn="",msgcpl="",msgest="",msgacp="",msglam="",msgrqp="",msgrqs="",msgspl="";
														if (msgType.compareTo("")==0) msgfree = jdbc.getaftn();
														if (msgType.compareTo("ALR")==0) msgalr = jdbc.getalr(); 
														if (msgType.compareTo("RCF")==0) msgrcf = jdbc.getrcf();
														if (msgType.compareTo("FPL")==0) msgfpl = jdbc.getfpl();
														if (msgType.compareTo("DLA")==0) msgdla = jdbc.getdla();
														if (msgType.compareTo("CHG")==0) msgchg = jdbc.getchg();
														if (msgType.compareTo("CNL")==0) msgcnl = jdbc.getcnl();
														if (msgType.compareTo("DEP")==0) msgdep = jdbc.getdep();
														if (msgType.compareTo("ARR")==0) msgarr = jdbc.getarr();
														if (msgType.compareTo("CDN")==0) msgcdn = jdbc.getcdn();
														if (msgType.compareTo("CPL")==0) msgcpl = jdbc.getcpl();
														if (msgType.compareTo("EST")==0) msgest = jdbc.getest();
														if (msgType.compareTo("ACP")==0) msgacp = jdbc.getacp();
														if (msgType.compareTo("LAM")==0) msglam = jdbc.getlam();
														if (msgType.compareTo("RQP")==0) msgrqp = jdbc.getrqp();
														if (msgType.compareTo("RQS")==0) msgrqs = jdbc.getrqs();
														if (msgType.compareTo("SPL")==0) msgspl = jdbc.getspl();
														
														msg = msgfree+msgalr+msgrcf+msgfpl+msgdla+msgchg+msgcnl+msgdep+msgarr+msgcdn+msgcpl+msgest+msgacp+msglam+msgrqp+msgrqs+msgspl;
														printable+=msg+line+"\n";

													} //end of while
													
													} catch (SQLException s) { // biar kalo ada table yang doesn't exist ga mati.
														System.out.println("info: " + s.getMessage());
													}
												iCnt++; //counter rs
											} //end of for2
										} //end of for1
//										shell.close();
										String lines="-";
										ReadFromFile.readConfiguration();
										for (int i=0; i<ReadFromFile.getPDFLine(); i++) { lines+="-"; }
										writetopdf.setPrint(printable, lines+"\n"+Integer.toString(jdbc.getJmlData()), MainForm.sPath + "AFTNmsgs.pdf");
										msg="";
										try {
											ReadFromFile.readConfiguration();
											String cmd = ReadFromFile.getPDFCmd() + " "+MainForm.sPath+"AFTNmsgs.pdf";
											System.out.println(cmd);
											Runtime.getRuntime().exec(cmd);
										} catch (IOException ioe) {
											System.err.println("open pdf err: " + ioe.getMessage());
										}
									} catch (SQLException s) {
										s.printStackTrace();
					        		} catch (java.lang.OutOfMemoryError hs) {
					        			hs.printStackTrace();
					        		}
									
									progbr.finish();
								} catch (java.lang.OutOfMemoryError hs) {
				        			hs.printStackTrace();
									DialogFactory.openInfoDialog(Shells.sh_tAFTN, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
									progbr.finish();
								} //end of try
							} //end of run
						}); //end of runnable
					}
				}.start();
				progbr.begin();	
			}
		});

		Word.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//memilih baris yang akan di print
				selection = table.getSelection(); 
				if ((selection.length == 0) || (sqlSelect.equals(""))) {
					DialogFactory.openInfoAtLeast(Shells.sh_tAFTN,DialogFactory.export);
				} else {
					
					final cProgressBar progbr = new cProgressBar("Selecting data ...");
					new Thread() {
						public void run() {
							if (TeleSplashScreen2016IP.display.isDisposed()) return;
							TeleSplashScreen2016IP.display.asyncExec(new Runnable() {
								@SuppressWarnings("unchecked")
								public void run() {
									try {
										progbr.create(100);
	
										id_ats="";
										tblName="";
										String msg="",msgall="",printable="";
										for (int i=0; i<selection.length; i++) { //banyak baris yang akan di print
											tblName = selection[i].getText(table.getColumnCount()-1);
											id_ats = selection[i].getText(0);
											msgType = selection[i].getText(3);
											
											String template="";
											if (msgType.compareTo("")==0) template = " WHERE";
											else if (msgType.compareTo("ALR")==0) template = " WHERE type3a='ALR' AND"; 
											else if (msgType.compareTo("RCF")==0) template = " WHERE type3a='RCF' AND";
											else if (msgType.compareTo("FPL")==0) template = " WHERE type3a='FPL' AND";
											else if (msgType.compareTo("DLA")==0) template = " WHERE type3a='DLA' AND";
											else if (msgType.compareTo("CHG")==0) template = " WHERE type3a='CHG' AND";
											else if (msgType.compareTo("CNL")==0) template = " WHERE type3a='CNL' AND";
											else if (msgType.compareTo("DEP")==0) template = " WHERE type3a='DEP' AND";
											else if (msgType.compareTo("ARR")==0) template = " WHERE type3a='ARR' AND";
											else if (msgType.compareTo("CDN")==0) template = " WHERE type3a='CDN' AND";
											else if (msgType.compareTo("CPL")==0) template = " WHERE type3a='CPL' AND";
											else if (msgType.compareTo("EST")==0) template = " WHERE type3a='EST' AND";
											else if (msgType.compareTo("ACP")==0) template = " WHERE type3a='ACP' AND";
											else if (msgType.compareTo("LAM")==0) template = " WHERE type3a='LAM' AND";
											else if (msgType.compareTo("RQP")==0) template = " WHERE type3a='RQP' AND";
											else if (msgType.compareTo("RQS")==0) template = " WHERE type3a='RQS' AND";
											else if (msgType.compareTo("SPL")==0) template = " WHERE type3a='SPL' AND";
											String sel = "SELECT * FROM "+tblName+template+" id_ats="+id_ats;
											jdbc.select(sel, tblName, msgType, "print");
											
											if (msgType.compareTo("")==0) msg = jdbc.getaftn();
											else if (msgType.compareTo("ALR")==0) msg = jdbc.getalr(); 
											else if (msgType.compareTo("RCF")==0) msg = jdbc.getrcf();
											else if (msgType.compareTo("FPL")==0) msg = jdbc.getfpl();
											else if (msgType.compareTo("DLA")==0) msg = jdbc.getdla();
											else if (msgType.compareTo("CHG")==0) msg = jdbc.getchg();
											else if (msgType.compareTo("CNL")==0) msg = jdbc.getcnl();
											else if (msgType.compareTo("DEP")==0) msg = jdbc.getdep();
											else if (msgType.compareTo("ARR")==0) msg = jdbc.getarr();
											else if (msgType.compareTo("CDN")==0) msg = jdbc.getcdn();
											else if (msgType.compareTo("CPL")==0) msg = jdbc.getcpl();
											else if (msgType.compareTo("EST")==0) msg = jdbc.getest();
											else if (msgType.compareTo("ACP")==0) msg = jdbc.getacp();
											else if (msgType.compareTo("LAM")==0) msg = jdbc.getlam();
											else if (msgType.compareTo("RQP")==0) msg = jdbc.getrqp();
											else if (msgType.compareTo("RQS")==0) msg = jdbc.getrqs();
											else if (msgType.compareTo("SPL")==0) msg = jdbc.getspl();
											
											if (msgall.compareTo(msg)!=0) {
												printable+=msg+line+"\n";
											} else {
												printable+=msg+line+"\n";
											}
										}
										String lines="-";
										ReadFromFile.readConfiguration();
										for (int i=0; i<ReadFromFile.getPDFLine(); i++) { lines+="-"; }
										String jmldata = lines + "\n" + selection.length + " Element(s) in this file";
										//new WriteToDOC("AFTNmsg", printable + jmldata);
										try {
											new WriteToDOCX("AFTNmsg", printable + jmldata);
										} catch (Exception e) {
											e.printStackTrace();
										}
										msg="";
										try {
											ReadFromFile.readConfiguration();
											String cmd = ReadFromFile.getWordCmd() + " /tmp/ramdisk0/AFTNmsg.docx";
											System.out.println(cmd);
											Runtime.getRuntime().exec(cmd);
										} catch (IOException ioe) {
											System.err.println("open word err: " + ioe.getMessage());
										}
										
										progbr.finish();
									} catch (java.lang.OutOfMemoryError hs) {
					        			hs.printStackTrace();
										DialogFactory.openInfoDialog(Shells.sh_tAFTN, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
										progbr.finish();
									} //end of try
								} //end of run
							}); //end of runnable
						}
					}.start();
					progbr.begin();	
					
				} //end of else
			}
		});


		
		Words.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				final cProgressBar progbr = new cProgressBar("Selecting data ...");
				new Thread() {
					public void run() {
						if (TeleSplashScreen2016IP.display.isDisposed()) return;
						TeleSplashScreen2016IP.display.asyncExec(new Runnable() {
							@SuppressWarnings("unchecked")
							public void run() {
								try {
									progbr.create(100);

									String msg="",/*msgall="",*/printable="";
									try {
										tblName="";
									
//										int jumlah=0; //inisialisasi
										int iCnt=0;
										int rowNo = 0;
										//menentukan tblName berdasarkan Date/Time
										System.out.println("Date From::" + yearFr + "-" + monthFr + "*");
										System.out.println("Date To::" + yearTo + "-" + monthTo + "*");
										
										conn1 = new Connection[Integer.parseInt(yearTo)*Integer.parseInt(monthTo)];
										rs = new ResultSet[Integer.parseInt(yearTo)*Integer.parseInt(monthTo)];
										
										for (int iYear=Integer.parseInt(yearFr); iYear<=Integer.parseInt(yearTo); iYear++) {
											tblName = "air_message"+iYear;
											
											int iMtemp=0;
											int n=0;
											boolean flg=false;
											if (iYear==Integer.parseInt(yearFr)) {
												iMtemp=Integer.parseInt(monthFr);
												n=12-iMtemp;
											}
											else if ((iYear>Integer.parseInt(yearFr)) && (iYear < Integer.parseInt(yearTo))) {
												iMtemp=1;
												n=12-iMtemp;
											}
											else if (iYear == Integer.parseInt(yearTo)) {
												iMtemp= Integer.parseInt(monthTo);
												n=iMtemp;
												iMtemp=0;
												flg=true;
											}
											
											int nn=0;
											for (int iMonth=iMtemp; iMonth<=n+iMtemp; iMonth++) {
												
												if ((flg) && (iMonth>=12)) { 
													nn++; 
													if ((n==iMtemp) && (nn>12)) break; 
												} else nn=iMonth;
												
												String sMonth = Integer.toString(nn);
												if (sMonth.length()==1) sMonth = sMonth.replace(sMonth, "0"+sMonth);
												tblName = tblName.replace(tblName, "air_message"+iYear+"_"+sMonth);

												conn1[iCnt] = conn;
												stmt = conn1[iCnt].createStatement();
												Statement stmt = conn1[iCnt].createStatement();
												try { //biar kalo ada table yang doesn't exist ga mati.
													lihatsql = select+tblName+where+tgl+tglTo+free+critCid+critSeq+critIO+order;
													rs[iCnt] = stmt.executeQuery(lihatsql);

													while (rs[iCnt].next()) {
														rowNo++;
									
														String id = rs[iCnt].getString("id_ats"); if (id==null) { id=""; }
														String tgl = rs[iCnt].getString("tgl"); if (tgl==null){ tgl=""; } else tgl = tgl.substring(0, 19);
														String tbl_name = rs[iCnt].getString("tbl_name"); if (tbl_name==null) { tbl_name=""; }
														String msgType = rs[iCnt].getString("type3a"); if (tbl_name==null) { tbl_name=""; }

														String template="";
														if (msgType.compareTo("")==0) template = " WHERE";
														else if (msgType.compareTo("ALR")==0) template = " WHERE type3a='ALR' AND"; 
														else if (msgType.compareTo("RCF")==0) template = " WHERE type3a='RCF' AND";
														else if (msgType.compareTo("FPL")==0) template = " WHERE type3a='FPL' AND";
														else if (msgType.compareTo("DLA")==0) template = " WHERE type3a='DLA' AND";
														else if (msgType.compareTo("CHG")==0) template = " WHERE type3a='CHG' AND";
														else if (msgType.compareTo("CNL")==0) template = " WHERE type3a='CNL' AND";
														else if (msgType.compareTo("DEP")==0) template = " WHERE type3a='DEP' AND";
														else if (msgType.compareTo("ARR")==0) template = " WHERE type3a='ARR' AND";
														else if (msgType.compareTo("CDN")==0) template = " WHERE type3a='CDN' AND";
														else if (msgType.compareTo("CPL")==0) template = " WHERE type3a='CPL' AND";
														else if (msgType.compareTo("EST")==0) template = " WHERE type3a='EST' AND";
														else if (msgType.compareTo("ACP")==0) template = " WHERE type3a='ACP' AND";
														else if (msgType.compareTo("LAM")==0) template = " WHERE type3a='LAM' AND";
														else if (msgType.compareTo("RQP")==0) template = " WHERE type3a='RQP' AND";
														else if (msgType.compareTo("RQS")==0) template = " WHERE type3a='RQS' AND";
														else if (msgType.compareTo("SPL")==0) template = " WHERE type3a='SPL' AND";
														String sel = "SELECT * FROM "+tblName+template+" id_ats="+id;
														jdbc.select(sel, tblName, msgType, "print");
														
														String msgfree="",msgalr="",msgrcf="",msgfpl="",msgdla="",msgchg="",msgcnl="",msgdep="",msgarr="",msgcdn="",msgcpl="",msgest="",msgacp="",msglam="",msgrqp="",msgrqs="",msgspl="";
														if (msgType.compareTo("")==0) msgfree = jdbc.getaftn();
														if (msgType.compareTo("ALR")==0) msgalr = jdbc.getalr(); 
														if (msgType.compareTo("RCF")==0) msgrcf = jdbc.getrcf();
														if (msgType.compareTo("FPL")==0) msgfpl = jdbc.getfpl();
														if (msgType.compareTo("DLA")==0) msgdla = jdbc.getdla();
														if (msgType.compareTo("CHG")==0) msgchg = jdbc.getchg();
														if (msgType.compareTo("CNL")==0) msgcnl = jdbc.getcnl();
														if (msgType.compareTo("DEP")==0) msgdep = jdbc.getdep();
														if (msgType.compareTo("ARR")==0) msgarr = jdbc.getarr();
														if (msgType.compareTo("CDN")==0) msgcdn = jdbc.getcdn();
														if (msgType.compareTo("CPL")==0) msgcpl = jdbc.getcpl();
														if (msgType.compareTo("EST")==0) msgest = jdbc.getest();
														if (msgType.compareTo("ACP")==0) msgacp = jdbc.getacp();
														if (msgType.compareTo("LAM")==0) msglam = jdbc.getlam();
														if (msgType.compareTo("RQP")==0) msgrqp = jdbc.getrqp();
														if (msgType.compareTo("RQS")==0) msgrqs = jdbc.getrqs();
														if (msgType.compareTo("SPL")==0) msgspl = jdbc.getspl();
														
														msg = msgfree+msgalr+msgrcf+msgfpl+msgdla+msgchg+msgcnl+msgdep+msgarr+msgcdn+msgcpl+msgest+msgacp+msglam+msgrqp+msgrqs+msgspl;
														printable+=msg+line+"\n";

													} //end of while
													
												} catch (SQLException s) { // biar kalo ada table yang doesn't exist ga mati.
													System.out.println("info: " + s.getMessage());
												}
												iCnt++; //counter rs
											} //end of for2
										} //end of for1
//										shell.close();
										String lines="-";
										ReadFromFile.readConfiguration();
										for (int i=0; i<ReadFromFile.getPDFLine(); i++) { lines+="-"; }
										String jmldata = lines + "\n" + jdbc.getJmlData() + " Element(s) in this file";
										//new WriteToDOC("AFTNmsgs", printable + jmldata);
										
										try {
											new WriteToDOCX("AFTNmsgs", printable + jmldata);
										} catch (Exception e) {
											e.printStackTrace();
										}
										
										msg="";
										try {
											ReadFromFile.readConfiguration();
											String cmd = ReadFromFile.getWordCmd() + " /tmp/ramdisk0/AFTNmsgs.docx";
											System.out.println(cmd);
											Runtime.getRuntime().exec(cmd);
										} catch (IOException ioe) {
											System.err.println("open word err: " + ioe.getMessage());
										}
									} catch (SQLException s) {
										s.printStackTrace();
					        		} catch (java.lang.OutOfMemoryError hs) {
					        			hs.printStackTrace();
					        		}
									
									progbr.finish();
								} catch (java.lang.OutOfMemoryError hs) {
				        			hs.printStackTrace();
									DialogFactory.openInfoDialog(Shells.sh_tAFTN, "Search Message", "Out of memory !!\nPlease fill another criteria search !! ");
									progbr.finish();
								} //end of try
							} //end of run
						}); //end of runnable
					}
				}.start();
				progbr.begin();	
				
			}
		});
	}
	
	public static void setTableFocus() {
		// set focus for table
		if (!table.isDisposed()) { table.setFocus(); }
	}
	
	void connClose() {
		try {
//			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		} catch(SQLException se) {
			se.printStackTrace();
		}
	}
}

/**
 * This class represents a player.
 */
class PlayerATS {
	private String ID;
	private String CID;
	private String SEQ;
	private String MSG_TYPE;
	private String MANUAL_ATS;
	private String TGL;
	private String STATUS;
	private String NOTE;
	private String FILED;
	private String TBL_NAME;
	 
	public PlayerATS(String ID,String CID,String SEQ,String MSG_TYPE,String MANUAL_ATS,String TGL,String STATUS,String NOTE,String FILED,
			String TBL_NAME) {
		this.ID = ID;
		this.CID = CID;
		this.SEQ = SEQ;
		this.MSG_TYPE = MSG_TYPE;
		this.MANUAL_ATS = MANUAL_ATS;
		this.TGL = TGL;
		this.STATUS = STATUS;
		this.NOTE = NOTE;
		this.FILED = FILED;
		this.TBL_NAME = TBL_NAME;
	}

	public String getID() { return ID; }
	public String getCID() { return CID; }
	public String getSEQ() { return SEQ; }
	public String getMSG_TYPE() { return MSG_TYPE; }
	public String getMANUAL_ATS() { return MANUAL_ATS; }
	public String getTGL() { return TGL; }
	public String getSTATUS() { return STATUS; }
	public String getNOTE() { return NOTE; }
	public String getFILED() { return FILED; }
	public String getTBL_NAME() { return TBL_NAME; }
}

@SuppressWarnings("unchecked")
class PlayerATSComparatorATS implements Comparator {
	public static final int ID = 0;
	public static final int CID = 1;
	public static final int SEQ = 2;
	public static final int MSG_TYPE = 3;
	public static final int MANUAL_ATS = 4;
	public static final int TGL = 5;
	public static final int STATUS = 6;
	public static final int NOTE = 7;
	public static final int FILED = 8;
	public static final int TBL_NAME = 9;
	
	public static final int ASCENDING = 0;
	public static final int DESCENDING = 1;
	
	private int column;
	private int direction;
	
	/**
	 * Compares two PlayerATS objects
	 * 
	 * @param obj1 the first PlayerATS
	 * @param obj2 the second PlayerATS
	 * @return int
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	
	public int compare(Object obj1, Object obj2) {
		int rc = 0;
	    PlayerATS p1 = (PlayerATS) obj1;
	    PlayerATS p2 = (PlayerATS) obj2;
	
	    // Determine which field to sort on, then sort on that field
	    switch (column) {
		    case ID:
		    	rc = p1.getID().compareTo(p2.getID());
		    	break;
		    case CID:
		    	rc = p1.getCID().compareTo(p2.getCID());
		    	break;
		    case SEQ:
		    	rc = p1.getSEQ().compareTo(p2.getSEQ());
		    	break;
		    case MSG_TYPE:
		    	rc = p1.getMSG_TYPE().compareTo(p2.getMSG_TYPE());
		    	break;
		    case MANUAL_ATS:
		    	rc = p1.getMANUAL_ATS().compareTo(p2.getMANUAL_ATS());
		    	break;	   
		    case TGL:
		    	rc = p1.getTGL().compareTo(p2.getTGL());
		    	break;
			case STATUS:
				rc = p1.getSTATUS().compareTo(p2.getSTATUS());
				break;
			case NOTE:
				rc = p1.getNOTE().compareTo(p2.getNOTE());
				break;
			case FILED:
	    		rc = p1.getFILED().compareTo(p2.getFILED());
		    	break;
			case TBL_NAME:
	    		rc = p1.getTBL_NAME().compareTo(p2.getTBL_NAME());
		    	break;
	    }
	
	    // Check the direction for sort and flip the sign if appropriate
	    if (direction == ASCENDING) 
	    	rc = -rc;
	    return rc;
	}
	
	// Sets the column for sorting
	public void setColumn(int column) {
		this.column = column;
	}
	
	// Sets the direction for sorting
	public void setDirection(int direction) {
		this.direction = direction;
	}	
	
	// Reverses the direction
	public void reverseDirection() {
		direction = 1 - direction;
	}
}