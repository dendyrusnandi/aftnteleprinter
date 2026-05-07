package actions;

//STEP 1. Import required packages
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import com.mysql.jdbc.ResultSet;

import readwrite.ReadFromFile;
import setting.Criteria;
import setting.Titles;
import displays.ConnectTo;
import displays.Lists;
import displays.MainForm;

public class Jdbc {
	
	static ViewATS vATS = new ViewATS();
	static ViewNOTAM vNOTAM = new ViewNOTAM();
	static ViewMETEO vMETEO = new ViewMETEO();
	static ViewRetrieve vRET = new ViewRetrieve();
	
	public static String sfree="",salr="",srcf="",sfpl="",sdla="",schg="",scnl="",sdep="",sarr="",scdn="",scpl="",sest="",sacp="",slam="",
	srqp="",srqs="",sspl="",snotam="",ssnowtam="",sashtam="",sbirdtam="",srqn="",smetar="",ssigmet="",sairep="",srqm="",sret="",srettrace="",
	sreject="",line="",
	id_rjt_msg="",textmsg="",errmsg="",typemsg="",dbprio="",dbdest1="",dbdest2="",dbdest3="",dbdest4="",dbdest5="",dbdest6="",dbdest7="",
	dbdest8="",dbdest9="",dbdest10="",dbdest11="",dbdest12="",dbdest13="",dbdest14="",dbdest15="",dbdest16="",dbdest17="",
	dbdest18="",dbdest19="",dbdest20="",dbdest21="",dbfiling="",dboriginator="",dboriref="",
	dbsNotamNo="",dbidentifiers="",dbsRefNotamNo="",dbsNotamCode="",dbpurpose="",dbscope="",dbA="",dbsStatusNotam="",
	dbsStatusRqntm="",dbstate="",dbsn1="",dblocind="",dbtimeobs="",dboptgrp="",dbsn2="",
	dbissued="",dbbirdnum="",dbefftime="",dbexptime="",dbintlev="",
	dbreqid="",dbreqmsg="",
	dborigin="",dbmsgID="",dblocation="",dboption="",dbtext="",dbdata="",dbtrace="",dbatd="",dbsReg="",dbsRmk="",dbfiledby="";

	public static String dbtype3b="",dbtype3c="",dbtype7a="",dbtype7c="",dbtype9b="",dbtype13a="",dbtype13b="",dbtype14a="",dbtype14b="",
	dbtype15a="",dbtype15b="",dbtype15c="",dbtype16a="",dbtype16b="",dbtype17a="",dbtype17b="",dbtype18="",dbtype21="",dbtype22="",dbfree="";
	
	public Jdbc() {

	}
	
	public static void conn() {
//		System.out.println("test");
		if (ConnectTo.sServer.compareTo("A")==0) {
			String path=MainForm.sFolder+"ipA.txt";
			new ReadFromFile().readDB(path);
			System.out.println("a.Connect to Server "+ConnectTo.sServer+"...");
		} else if (ConnectTo.sServer.compareTo("B")==0) {
			String path=MainForm.sFolder+"ipB.txt";
			new ReadFromFile().readDB(path);
			System.out.println("b.Connect to Server "+ConnectTo.sServer+"...");
		}
	}
	
	public static void selectREJECT(String sQuery,String dburl,String dbuser,String dbpass) {
		Connection conn = null;
		Statement stmt = null;

		try {
			//STEP 2: Register JDBC driver
			Class.forName(ConnectTo.JDBC_DRIVER);

			//STEP 3: Open a connection
			conn = DriverManager.getConnection(dburl,dbuser,dbpass);

			//STEP 4: Execute a query
			stmt = conn.createStatement();
			System.out.println("sQuery::" + sQuery + "#");
			stmt.execute(sQuery);
			
			//STEP 5: Clean-up environment
		    stmt.close();
		    conn.close();
		    
		} catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		} catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		} finally{
			//finally block used to close resources
			try {
				if(stmt!=null) stmt.close();
			} catch(SQLException se2) { }// nothing we can do
			try {
				if(conn!=null) conn.close();
			} catch(SQLException se) {
				se.printStackTrace();
			}//end finally try
		}//end try
	}

	public static void selectREJECT(String sQuery,String id_rjt_msg,String dburl,String dbuser,String dbpass) {
		Connection conn = null;
		Statement stmt = null;

		try {
			//STEP 2: Register JDBC driver
			Class.forName(ConnectTo.JDBC_DRIVER);

			//STEP 3: Open a connection
			conn = DriverManager.getConnection(dburl,dbuser,dbpass);

			//STEP 4: Execute a query
			stmt = conn.createStatement();
			System.out.println("sQuery:" + sQuery + "#");
			stmt.execute(sQuery);
			
			//STEP 5: Clean-up environment
		    stmt.close();
		    conn.close();
		    
		} catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		} catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		} finally{
			//finally block used to close resources
			try {
				if(stmt!=null) stmt.close();
			} catch(SQLException se2) { }// nothing we can do
			try {
				if(conn!=null) conn.close();
			} catch(SQLException se) {
				se.printStackTrace();
			}//end finally try
		}//end try
	}

	public static void selectID(String id,String tanggal,String filing,
			String sQueryID,String sQueryUPDT,String sQueryINS1,String sQueryINS2,
			String dburl,String dbuser,String dbpass) {
		Connection conn = null;
		Statement stmt = null;

		try {
			//STEP 2: Register JDBC driver
			Class.forName(ConnectTo.JDBC_DRIVER);

			//STEP 3: Open a connection
			System.out.println("sQuery:" + sQueryID + "#");
			conn = DriverManager.getConnection(dburl,dbuser,dbpass);

			//STEP 4: Execute a query
			stmt = conn.createStatement();
			
			//STEP 5: Extract data from result set (depend on query type)
			boolean hasilExe = stmt.execute(sQueryID);
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else { 
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					id = rs.getString("id_rjt_msg"); if (id==null) id="";
					String err = rs.getString("free_text_error_message"); if (err==null) err="";
				}
				System.out.println(">>id REJECT::" + id + "#");
				String sQueryUPDATE=sQueryUPDT+tanggal+"' WHERE id_rjt_msg="+id;
				selectREJECT(sQueryUPDATE, id, dburl, dbuser, dbpass);
				
				String sQueryINSERT=sQueryINS1+id+sQueryINS2+",'"+filing+"'" +//jam
				",'"+filing+"'" +//filing_time
				",'"+tanggal+"')";//tgl;
				selectREJECT(sQueryINSERT, id, dburl, dbuser, dbpass);
			}
			
			//STEP 6: Clean-up environment
		    stmt.close();
		    conn.close();
		    
		} catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		} catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		} finally{
			//finally block used to close resources
			try {
				if(stmt!=null) stmt.close();
			} catch(SQLException se2) { }// nothing we can do
			try {
				if(conn!=null) conn.close();
			} catch(SQLException se) {
				se.printStackTrace();
			}//end finally try
		}//end try
	}
	
	public static void select(String sQuery, String type) {
		Connection conn = null;
		Statement stmt = null;

		try {

			//STEP 2: Register JDBC driver
			Class.forName(ConnectTo.JDBC_DRIVER);

//			//STEP 3: Open a connection
//			conn = DriverManager.getConnection(ConnectTo.DB_NAME,ConnectTo.DB_USER,ConnectTo.DB_PASS);
//
//			//STEP 4: Execute a query
//			stmt = conn.createStatement();
//			stmt.execute(sQuery);
			
			if (!type.isEmpty() && type.compareTo("REJMSG")==0) {
				System.out.println("Server A");
				new ReadFromFile().readDBRej(MainForm.sFolder+"ipA.txt");
				//STEP 3: Open a connection
				System.out.println("--URL:" + ConnectTo.DB_URL_REJ + "--");
				Connection conn1 = DriverManager.getConnection(ConnectTo.DB_URL_REJ,ConnectTo.DB_USER_REJ,ConnectTo.DB_PASS_REJ);
				//STEP 4: Execute a query
				Statement stmt1 = conn1.createStatement();
				System.out.println("Query:" + sQuery + "#");
				stmt1.execute(sQuery);
				stmt1.close();
				conn1.close();
				
				System.out.println("Server B");
				new ReadFromFile().readDBRej(MainForm.sFolder+"ipB.txt"); 
				//STEP 3: Open a connection
				System.out.println("--URL:" + ConnectTo.DB_URL_REJ + "--");
				Connection conn2 = DriverManager.getConnection(ConnectTo.DB_URL_REJ,ConnectTo.DB_USER_REJ,ConnectTo.DB_PASS_REJ);
				//STEP 4: Execute a query
				Statement stmt2 = conn2.createStatement();
				System.out.println("Query:" + sQuery + "#");
				stmt2.execute(sQuery);
				stmt2.close();
				conn2.close();
				
				conn();
			} else {
				//STEP 3: Open a connection
				conn = DriverManager.getConnection(ConnectTo.DB_NAME,ConnectTo.DB_USER,ConnectTo.DB_PASS);

				//STEP 4: Execute a query
				stmt = conn.createStatement();
				System.out.println("Query:" + sQuery + "#");
				stmt.execute(sQuery);	
			}
			
			
			//STEP 5: Extract data from result set (depend on query type)
			if (!type.isEmpty() && type.compareTo("REJMSG")!=0) { //Query: View
				if (type.compareTo(Titles.stFREETEXT)==0) { 
					vATS.viewATS(stmt,Titles.stFREETEXT); sfree=vATS.pfree; if (!sfree.isEmpty()) sfree+=line; 
					dbfree=vATS.strFree; }
				//------------------------------ ATS MESSAGES ------------------------------
				if (type.compareTo(Titles.stALR)==0) { 
					vATS.viewATS(stmt,Titles.stALR); salr=vATS.palr; if (!salr.isEmpty()) salr+=line; 
					dbtype7a=vATS.type7a;
					dbtype9b=vATS.type9b;
					dbtype13a=vATS.type13a;
					dbtype13b=vATS.type13b;
					dbtype15b=vATS.type15b;
					dbtype15c=vATS.type15c;
					dbtype16a=vATS.type16a; }
				
				if (type.compareTo(Titles.stRCF)==0) { 
					vATS.viewATS(stmt,Titles.stRCF); srcf=vATS.prcf; if (!srcf.isEmpty()) srcf+=line; 
					dbtype7a=vATS.type7a;
					dbtype7c=vATS.type7c;
					dbtype21=vATS.type21; }
				
				if (type.compareTo(Titles.stFPL)==0) { 
					vATS.viewATS(stmt,Titles.stFPL); sfpl=vATS.pfpl; if (!sfpl.isEmpty()) sfpl+=line; 
					dbtype7a=vATS.type7a;
					dbtype9b=vATS.type9b;
					dbtype13a=vATS.type13a;
					dbtype13b=vATS.type13b;
					dbtype15a=vATS.type15a;
					dbtype15b=vATS.type15b;
					dbtype15c=vATS.type15c;
					dbtype16a=vATS.type16a;
					dbtype16b=vATS.type16b;
					
					dbsReg=vATS.sReg;
					dbsRmk=vATS.sRmk;
					dbfiledby=vATS.filedby;
					
					dbtype18=vATS.type18; }
				
				if (type.compareTo(Titles.stDLA)==0) { 
					vATS.viewATS(stmt,Titles.stDLA); sdla=vATS.pdla; if (!sdla.isEmpty()) sdla+=line;  
					dbtype7a=vATS.type7a;
					dbtype13a=vATS.type13a;
					dbtype13b=vATS.type13b;
					dbtype16a=vATS.type16a;
					dbtype18=vATS.type18; }

				if (type.compareTo(Titles.stCHG)==0) { 
					vATS.viewATS(stmt,Titles.stCHG); schg=vATS.pchg; if (!schg.isEmpty()) schg+=line; 
					dbtype7a=vATS.type7a;
					dbtype13a=vATS.type13a;
					dbtype13b=vATS.type13b;
					dbtype16a=vATS.type16a;
					dbtype18=vATS.type18; }
				
				if (type.compareTo(Titles.stCNL)==0) { 
					vATS.viewATS(stmt,Titles.stCNL); scnl=vATS.pcnl; if (!scnl.isEmpty()) scnl+=line;  
					dbtype7a=vATS.type7a;
					dbtype13a=vATS.type13a;
					dbtype13b=vATS.type13b;
					dbtype16a=vATS.type16a;
					dbtype18=vATS.type18; }
				
				if (type.compareTo(Titles.stDEP)==0) { 
					vATS.viewATS(stmt,Titles.stDEP); sdep=vATS.pdep; if (!sdep.isEmpty()) sdep+=line;   
					dbtype7a=vATS.type7a;
					dbtype13a=vATS.type13a;
					dbtype13b=vATS.type13b;
					dbtype16a=vATS.type16a;
					dbtype18=vATS.type18; }
				
				if (type.compareTo(Titles.stARR)==0) { 
					vATS.viewATS(stmt,Titles.stARR); sarr=vATS.parr; if (!sarr.isEmpty()) sarr+=line; 
					dbtype7a=vATS.type7a;
					dbtype13a=vATS.type13a;
					dbtype13b=vATS.type13b;
					dbtype17a=vATS.type17a;
					dbtype17b=vATS.type17b; }
				
				if (type.compareTo(Titles.stCDN)==0) { 
					vATS.viewATS(stmt,Titles.stCDN); scdn=vATS.pcdn; if (!scdn.isEmpty()) scdn+=line; 
					dbtype7a=vATS.type7a;
					dbtype13a=vATS.type13a;
					dbtype13b=vATS.type13b;
					dbtype16a=vATS.type16a;
					dbtype22=vATS.type22; }
				
				if (type.compareTo(Titles.stCPL)==0) { 
					vATS.viewATS(stmt,Titles.stCPL); scpl=vATS.pcpl; if (!scpl.isEmpty()) scpl+=line; 
					dbtype7a=vATS.type7a;
					dbtype9b=vATS.type9b;
					dbtype13a=vATS.type13a;
					dbtype13b=vATS.type13b;
					dbtype15b=vATS.type15b;
					dbtype15c=vATS.type15c;
					dbtype16a=vATS.type16a; }
				
				if (type.compareTo(Titles.stEST)==0) { 
					vATS.viewATS(stmt,Titles.stEST); sest=vATS.pest; if (!sest.isEmpty()) sest+=line; 
					dbtype7a=vATS.type7a;
					dbtype13a=vATS.type13a;
					dbtype13b=vATS.type13b;
					dbtype14a=vATS.type14a;
					dbtype14b=vATS.type14b;
					dbtype16a=vATS.type16a; }
				
				if (type.compareTo(Titles.stACP)==0) { 
					vATS.viewATS(stmt,Titles.stACP); sacp=vATS.pacp; if (!sacp.isEmpty()) sacp+=line; 
					dbtype7a=vATS.type7a;
					dbtype13a=vATS.type13a;
					dbtype13b=vATS.type13b;
					dbtype16a=vATS.type16a; }
					
				if (type.compareTo(Titles.stLAM)==0) { 
					vATS.viewATS(stmt,Titles.stLAM); slam=vATS.plam; if (!slam.isEmpty()) slam+=line; 
					dbtype3b=vATS.type3b;
					dbtype3c=vATS.type3c; }
				
				if (type.compareTo(Titles.stRQP)==0) { 
					vATS.viewATS(stmt,Titles.stRQP); srqp=vATS.prqp; if (!srqp.isEmpty()) srqp+=line;   
					dbtype7a=vATS.type7a;
					dbtype13a=vATS.type13a;
					dbtype13b=vATS.type13b;
					dbtype16a=vATS.type16a;
					dbtype18=vATS.type18; }
				
				if (type.compareTo(Titles.stRQS)==0) { 
					vATS.viewATS(stmt,Titles.stRQS); srqs=vATS.prqs; if (!srqs.isEmpty()) srqs+=line;   
					dbtype7a=vATS.type7a;
					dbtype13a=vATS.type13a;
					dbtype13b=vATS.type13b;
					dbtype16a=vATS.type16a;
					dbtype18=vATS.type18; }
				
				if (type.compareTo(Titles.stSPL)==0) { 
					vATS.viewATS(stmt,Titles.stSPL); sspl=vATS.pspl; if (!sspl.isEmpty()) sspl+=line; 
					dbtype7a=vATS.type7a;
					dbtype13a=vATS.type13a;
					dbtype13b=vATS.type13b;
					dbtype16a=vATS.type16a; }
					
				//------------------------------ NOTAM MESSAGES ------------------------------
				if (type.compareTo(Titles.stNOTAM)==0 || type.compareTo(Titles.stRQNTM)==0 || type.compareTo(Titles.stCHKNTM)==0 || 
					type.compareTo(Titles.stACTNTM)==0 || type.compareTo(Titles.stEXPNTM)==0) { 
					vNOTAM.viewNOTAM(stmt,Titles.stNOTAM); snotam = vNOTAM.pnotam; if (!snotam.isEmpty()) snotam+=line; 
					dboriginator=vATS.originator;
					dbsNotamNo=vNOTAM.sNotamNo;
					dbidentifiers=vNOTAM.identifiers;
    				dbsRefNotamNo=vNOTAM.sRefNotamNo;
    				dbsNotamCode=vNOTAM.sNotamCode;
    				dbpurpose=vNOTAM.purpose;
    				dbscope=vNOTAM.scope;
    				dbA=vNOTAM.A;
    				dbsStatusNotam=vNOTAM.sStatusNotam;	
    				dbsStatusRqntm=vNOTAM.sStatusRqntm;}
				
				if (type.compareTo(Titles.stSNOWTAM)==0) { 
					vNOTAM.viewNOTAM(stmt,Titles.stSNOWTAM); ssnowtam = vNOTAM.psnowtam; if (!ssnowtam.isEmpty()) ssnowtam+=line; 
					dbstate=vNOTAM.state;
					dbsn1=vNOTAM.sn1;
					dblocind=vNOTAM.locind;
					dbtimeobs=vNOTAM.timeobs;
					dboptgrp=vNOTAM.optgrp;
					dbsn2=vNOTAM.sn2; }
				
				if (type.compareTo(Titles.stASHTAM)==0) {
					vNOTAM.viewNOTAM(stmt, Titles.stASHTAM); sashtam = vNOTAM.pashtam; if (!sashtam.isEmpty()) sashtam+=line; 
					dbstate=vNOTAM.state;
					dbsn1=vNOTAM.sn1;
					dblocind=vNOTAM.locind;
					dbissued=vNOTAM.issued;
					dboptgrp=vNOTAM.optgrp;
					dbsn2=vNOTAM.sn2; }
				
				if (type.compareTo(Titles.stBIRDTAM)==0) { 
					vNOTAM.viewNOTAM(stmt,Titles.stBIRDTAM); sbirdtam = vNOTAM.pbirdtam; if (!sbirdtam.isEmpty()) sbirdtam+=line; 
					dbbirdnum=vNOTAM.birdnum;
					dbefftime=vNOTAM.efftime;
					dbexptime=vNOTAM.exptime;
					dbintlev=vNOTAM.intlev; }
				
				if (type.compareTo(Titles.stRQN)==0 || type.compareTo(Titles.stRQL)==0) { 
					vNOTAM.viewNOTAM(stmt,Titles.stRQN); srqn = vNOTAM.prqn; if (!srqn.isEmpty()) srqn+=line; 
					dbreqid=vNOTAM.reqid;
					dblocind=vNOTAM.locind;
					dbreqmsg=vNOTAM.reqmsg; }

				//------------------------------ METEO MESSAGES ------------------------------
				if (type.compareTo(Titles.stRQM)==0) {
					vMETEO.viewMETEO(stmt, type); srqm = vMETEO.pfreemet; if (!srqm.isEmpty()) srqm+=line; 
					dbfree=vMETEO.text; }
				
				if (type.compareTo(Titles.stMETAR)==0 || type.compareTo(Titles.stSPECI)==0) {
					vMETEO.viewMETEO(stmt, type); smetar = vMETEO.pmetar; if (!smetar.isEmpty()) smetar+=line; 
					dborigin=vMETEO.origin;
					dbmsgID=vMETEO.msgID;
					dblocation=vMETEO.location;
					dbtimeobs=vMETEO.timeobs;
					dbissued=vMETEO.issued;
					dboption=vMETEO.option;
					dbtext=vMETEO.text; }

				if (type.compareTo(Titles.stSIGMET)==0 || type.compareTo(Titles.stAIRMET)==0 || type.compareTo(Titles.stWINSWAR)==0) { 
					vMETEO.viewMETEO(stmt, type); ssigmet = vMETEO.psigmet; if (!ssigmet.isEmpty()) ssigmet+=line; 
					dborigin=vMETEO.origin;
					dbmsgID=vMETEO.msgID;
					dblocation=vMETEO.location;
					dbissued=vMETEO.issued;
					dboption=vMETEO.option;
					dbtext=vMETEO.text; }
				
				if (type.compareTo(Titles.stAIREP)==0 || type.compareTo(Titles.stTAFOR)==0 || type.compareTo(Titles.stARFOR)==0 || 
						type.compareTo(Titles.stROFOR)==0 || type.compareTo(Titles.stWINTEM)==0 || type.compareTo(Titles.stADV)==0 ||
						type.compareTo(Titles.stSYNOP)==0 || type.compareTo(Titles.stWARSYN)==0) { 
					vMETEO.viewMETEO(stmt, type); sairep = vMETEO.pairep; if (!sairep.isEmpty()) sairep+=line;
					dborigin=vMETEO.origin;
					dbmsgID=vMETEO.msgID;
					dbissued=vMETEO.issued;
					dboption=vMETEO.option;
					dbtext=vMETEO.text; } 

				//------------------------------ Retrieve ------------------------------
				if (type.compareTo(Titles.stIncoming)==0 || type.compareTo(Titles.stOutgoing)==0) { 
					vRET.view(stmt, type); 
					sret=vRET.pret; if (!sret.isEmpty()) sret+=line; 
					srettrace=vRET.prettrace; if (!srettrace.isEmpty()) srettrace+=line; 
					boolean hasilExe = stmt.execute(sQuery);
					if (hasilExe == true) { editFieldsRETRIEVAL(stmt, hasilExe); }
				}
				//------------------------------ Reject ------------------------------
				if (type.compareTo(Titles.stReject)==0) { 
					vRET.view(stmt, type); 
					sreject=vRET.preject; if (!sreject.isEmpty()) sreject+=line;
					boolean hasilExe = stmt.execute(sQuery);
					if (hasilExe == true) { editFieldsREJECT(stmt, hasilExe); }
				}
				//------------------------------ Reject ------------------------------
				if (type.compareTo("fpldla")==0) { 
					boolean hasilExe = stmt.execute(sQuery);
					if (hasilExe == true) { editFieldsFPLDLA(stmt, hasilExe); }
				}
				
			}
			//STEP 6: Clean-up environment
//		    stmt.close();
//		    conn.close();
		    if (type.compareTo("REJMSG")!=0) {
		    	stmt.close();
			    conn.close();
		    }
		    
		} catch(SQLException se){
			//Handle errors for JDBC
//			se.printStackTrace();
			se.getMessage();
		} catch(Exception e){
			//Handle errors for Class.forName
//			e.printStackTrace();
			e.getMessage();
		} finally{
			//finally block used to close resources
			try {
				if(stmt!=null) stmt.close();
			} catch(SQLException se2) { }// nothing we can do
			try {
				if(conn!=null) conn.close();
			} catch(SQLException se) {
				se.printStackTrace();
			}//end finally try
		}//end try
	
			
	}

	static void editFieldsRETRIEVAL(Statement stmt, boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else { 
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					dbdata = rs.getString("data"); if (dbdata==null) dbdata="";
					dbtrace = rs.getString("trace"); if (dbtrace==null) dbtrace="";
				}
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}

	static void editFieldsFPLDLA(Statement stmt, boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else { 
				ResultSet rs=stmt.getResultSet();
				dbatd="";
				while (rs.next()) {
					dbatd = rs.getString("type13b"); if (dbatd==null) dbatd="";
				}
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editFieldsREJECT(Statement stmt, boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else { 
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					id_rjt_msg = rs.getString("id_rjt_msg"); if (id_rjt_msg==null) id_rjt_msg="";
					
					textmsg = rs.getString("origin_message"); if (textmsg==null) textmsg="";
					errmsg = rs.getString("free_text_error_message"); if (errmsg==null) errmsg="";
					typemsg = rs.getString("type_message"); if (typemsg==null) typemsg="";
					
					dbprio = rs.getString("priority"); if (dbprio==null) dbprio="";
					dbfiling = rs.getString("filing_time"); if (dbfiling==null) dbfiling="";
					dboriginator = rs.getString("originator"); if (dboriginator==null) dboriginator="";
					dboriref = rs.getString("ori_ref"); if (dboriref==null) dboriref="";

					dbdest1 = rs.getString("destination1"); if (dbdest1==null) dbdest1="";
					dbdest2 = rs.getString("destination2"); if (dbdest2==null) dbdest2="";
					dbdest3 = rs.getString("destination3"); if (dbdest3==null) dbdest3="";
					dbdest4 = rs.getString("destination4"); if (dbdest4==null) dbdest4="";
					dbdest5 = rs.getString("destination5"); if (dbdest5==null) dbdest5="";
					dbdest6 = rs.getString("destination6"); if (dbdest6==null) dbdest6="";
					dbdest7 = rs.getString("destination7"); if (dbdest7==null) dbdest7="";
					dbdest8 = rs.getString("destination8"); if (dbdest8==null) dbdest8="";
					dbdest9 = rs.getString("destination9"); if (dbdest9==null) dbdest9="";
					dbdest10 = rs.getString("destination10"); if (dbdest10==null) dbdest10="";
					dbdest11 = rs.getString("destination11"); if (dbdest11==null) dbdest11="";
					dbdest12 = rs.getString("destination12"); if (dbdest12==null) dbdest12="";
					dbdest13 = rs.getString("destination13"); if (dbdest13==null) dbdest13="";
					dbdest14 = rs.getString("destination14"); if (dbdest14==null) dbdest14="";
					dbdest15 = rs.getString("destination15"); if (dbdest15==null) dbdest15="";
					dbdest16 = rs.getString("destination16"); if (dbdest16==null) dbdest16="";
					dbdest17 = rs.getString("destination17"); if (dbdest17==null) dbdest17="";
					dbdest18 = rs.getString("destination18"); if (dbdest18==null) dbdest18="";
					dbdest19 = rs.getString("destination19"); if (dbdest19==null) dbdest19="";
					dbdest20 = rs.getString("destination20"); if (dbdest20==null) dbdest20="";
					dbdest21 = rs.getString("destination21"); if (dbdest21==null) dbdest21="";
				}
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
}//end jdbc


//public static void selectASLI(String sQuery, String type) {
//System.out.println("Query:" + sQuery);
//Connection conn = null;
//Statement stmt = null;
//
//try {
//
//	//STEP 2: Register JDBC driver
//	Class.forName(ConnectTo.JDBC_DRIVER);
//
//	//STEP 3: Open a connection
//	conn = DriverManager.getConnection(ConnectTo.DB_NAME,ConnectTo.DB_USER,ConnectTo.DB_PASS);
//
//	//STEP 4: Execute a query
//	stmt = conn.createStatement();
//	stmt.execute(sQuery);
//	
//	//STEP 5: Extract data from result set (depend on query type)
//	if (!type.isEmpty()) { //Query: View
//		if (type.compareTo(Titles.stFREETEXT)==0) { vATS.viewATS(stmt,Titles.stFREETEXT); sfree=vATS.pfree; if (!sfree.isEmpty()) sfree+=line; }
//		//------------------------------ ATS MESSAGES ------------------------------
//		if (type.compareTo(Titles.stALR)==0) { vATS.viewATS(stmt,Titles.stALR); salr=vATS.palr; if (!salr.isEmpty()) salr+=line; }
//		if (type.compareTo(Titles.stRCF)==0) { vATS.viewATS(stmt,Titles.stRCF); srcf=vATS.prcf; if (!srcf.isEmpty()) srcf+=line; }
//		if (type.compareTo(Titles.stFPL)==0) { vATS.viewATS(stmt,Titles.stFPL); sfpl=vATS.pfpl; if (!sfpl.isEmpty()) sfpl+=line; }
//		if (type.compareTo(Titles.stDLA)==0) { vATS.viewATS(stmt,Titles.stDLA); sdla=vATS.pdla; if (!sdla.isEmpty()) sdla+=line; }
//		if (type.compareTo(Titles.stCHG)==0) { vATS.viewATS(stmt,Titles.stCHG); schg=vATS.pchg; if (!schg.isEmpty()) schg+=line; }
//		if (type.compareTo(Titles.stCNL)==0) { vATS.viewATS(stmt,Titles.stCNL); scnl=vATS.pcnl; if (!scnl.isEmpty()) scnl+=line; }
//		if (type.compareTo(Titles.stDEP)==0) { vATS.viewATS(stmt,Titles.stDEP); sdep=vATS.pdep; if (!sdep.isEmpty()) sdep+=line; }
//		if (type.compareTo(Titles.stARR)==0) { vATS.viewATS(stmt,Titles.stARR); sarr=vATS.parr; if (!sarr.isEmpty()) sarr+=line; }
//		if (type.compareTo(Titles.stCDN)==0) { vATS.viewATS(stmt,Titles.stCDN); scdn=vATS.pcdn; if (!scdn.isEmpty()) scdn+=line; }
//		if (type.compareTo(Titles.stCPL)==0) { vATS.viewATS(stmt,Titles.stCPL); scpl=vATS.pcpl; if (!scpl.isEmpty()) scpl+=line; }
//		if (type.compareTo(Titles.stEST)==0) { vATS.viewATS(stmt,Titles.stEST); sest=vATS.pest; if (!sest.isEmpty()) sest+=line; }
//		if (type.compareTo(Titles.stACP)==0) { vATS.viewATS(stmt,Titles.stACP); sacp=vATS.pacp; if (!sacp.isEmpty()) sacp+=line; }
//		if (type.compareTo(Titles.stLAM)==0) { vATS.viewATS(stmt,Titles.stLAM); slam=vATS.plam; if (!slam.isEmpty()) slam+=line; }
//		if (type.compareTo(Titles.stRQP)==0) { vATS.viewATS(stmt,Titles.stRQP); srqp=vATS.prqp; if (!srqp.isEmpty()) srqp+=line; }
//		if (type.compareTo(Titles.stRQS)==0) { vATS.viewATS(stmt,Titles.stRQS); srqs=vATS.prqs; if (!srqs.isEmpty()) srqs+=line; }
//		if (type.compareTo(Titles.stSPL)==0) { vATS.viewATS(stmt,Titles.stSPL); sspl=vATS.pspl; if (!sspl.isEmpty()) sspl+=line; }
//		//------------------------------ NOTAM MESSAGES ------------------------------
//		if (type.compareTo(Titles.stNOTAM)==0 || type.compareTo(Titles.stRQNTM)==0 || type.compareTo(Titles.stCHKNTM)==0 || 
//			type.compareTo(Titles.stACTNTM)==0 || type.compareTo(Titles.stEXPNTM)==0) { 
//			vNOTAM.viewNOTAM(stmt,Titles.stNOTAM); snotam = vNOTAM.pnotam; if (!snotam.isEmpty()) snotam+=line; }
//		if (type.compareTo(Titles.stSNOWTAM)==0) { 
//			vNOTAM.viewNOTAM(stmt,Titles.stSNOWTAM); ssnowtam = vNOTAM.psnowtam; if (!ssnowtam.isEmpty()) ssnowtam+=line; }
//		if (type.compareTo(Titles.stASHTAM)==0) {
//			vNOTAM.viewNOTAM(stmt, Titles.stASHTAM); sashtam = vNOTAM.pashtam; if (!sashtam.isEmpty()) sashtam+=line; }
//		if (type.compareTo(Titles.stBIRDTAM)==0) { 
//			vNOTAM.viewNOTAM(stmt,Titles.stBIRDTAM); sbirdtam = vNOTAM.pbirdtam; if (!sbirdtam.isEmpty()) sbirdtam+=line; }
//		if (type.compareTo(Titles.stRQN)==0 || type.compareTo(Titles.stRQL)==0) { 
//			vNOTAM.viewNOTAM(stmt,Titles.stRQN); srqn = vNOTAM.prqn; if (!srqn.isEmpty()) srqn+=line; }
//		//------------------------------ METEO MESSAGES ------------------------------
//		if (type.compareTo(Titles.stRQM)==0) {
//			vMETEO.viewMETEO(stmt, type); srqm = vMETEO.pfreemet; if (!srqm.isEmpty()) srqm+=line; }
//		
//		if (type.compareTo(Titles.stMETAR)==0 || type.compareTo(Titles.stSPECI)==0) {
//			vMETEO.viewMETEO(stmt, type); smetar = vMETEO.pmetar; if (!smetar.isEmpty()) smetar+=line; }
//
//		if (type.compareTo(Titles.stSIGMET)==0 || type.compareTo(Titles.stAIRMET)==0 || type.compareTo(Titles.stWINSWAR)==0) { 
//			vMETEO.viewMETEO(stmt, type); ssigmet = vMETEO.psigmet; if (!ssigmet.isEmpty()) ssigmet+=line; }
//		
//		if (type.compareTo(Titles.stAIREP)==0 || type.compareTo(Titles.stTAFOR)==0 || type.compareTo(Titles.stARFOR)==0 || 
//				type.compareTo(Titles.stROFOR)==0 || type.compareTo(Titles.stWINTEM)==0 || type.compareTo(Titles.stADV)==0 ||
//				type.compareTo(Titles.stSYNOP)==0 || type.compareTo(Titles.stWARSYN)==0) { 
//			vMETEO.viewMETEO(stmt, type); sairep = vMETEO.pairep; if (!sairep.isEmpty()) sairep+=line; }
//		//------------------------------ Retrieve ------------------------------
//		if (type.compareTo(Titles.stIncoming)==0 || type.compareTo(Titles.stOutgoing)==0) { 
//			vRET.view(stmt, type); 
//			sret=vRET.pret; if (!sret.isEmpty()) sret+=line; 
//			srettrace=vRET.prettrace; if (!srettrace.isEmpty()) srettrace+=line; }
//		//------------------------------ Reject ------------------------------
//		if (type.compareTo(Titles.stReject)==0) { 
//			vRET.view(stmt, type); 
//			sreject=vRET.preject; if (!sreject.isEmpty()) sreject+=line; 
//			boolean hasilExe = stmt.execute(sQuery);
//			if (hasilExe == true) {	
//				editFields(stmt, hasilExe);						
//			}
//		}
//		
//	}
//	//STEP 6: Clean-up environment
//    stmt.close();
//    conn.close();
//    
//} catch(SQLException se){
//	//Handle errors for JDBC
//	se.printStackTrace();
//} catch(Exception e){
//	//Handle errors for Class.forName
//	e.printStackTrace();
//} finally{
//	//finally block used to close resources
//	try {
//		if(stmt!=null) stmt.close();
//	} catch(SQLException se2) { }// nothing we can do
//	try {
//		if(conn!=null) conn.close();
//	} catch(SQLException se) {
//		se.printStackTrace();
//	}//end finally try
//}//end try
//}//end method

//public static void selectSUDAH_BETUL_TAPI_REJECT_BEDA_ID_MASIH_SALAH(String sQuery, String type) {
//	
//	Connection conn = null;
//	Statement stmt = null;
//
//	try {
//
//		//STEP 2: Register JDBC driver
//		Class.forName(ConnectTo.JDBC_DRIVER);
//
////		//STEP 3: Open a connection
////		conn = DriverManager.getConnection(ConnectTo.DB_NAME,ConnectTo.DB_USER,ConnectTo.DB_PASS);
////
////		//STEP 4: Execute a query
////		stmt = conn.createStatement();
////		stmt.execute(sQuery);
//		
////		System.out.println("--first connection to server:" + ConnectTo.sServer + "--");
//		if (!type.isEmpty() && type.compareTo("REJMSG")==0) {
//			System.out.println("Server A");
//			new ReadFromFile().readDBRej(MainForm.sFolder+"ipA.txt");
//			//STEP 3: Open a connection
//			System.out.println("--URL:" + ConnectTo.DB_URL_REJ + "--");
//			Connection conn1 = DriverManager.getConnection(ConnectTo.DB_URL_REJ,ConnectTo.DB_USER_REJ,ConnectTo.DB_PASS_REJ);
//			//STEP 4: Execute a query
//			Statement stmt1 = conn1.createStatement();
//			System.out.println("Query:" + sQuery);
//			stmt1.execute(sQuery);
//			stmt1.close();
//			conn1.close();
//			
//			System.out.println("Server B");
//			new ReadFromFile().readDBRej(MainForm.sFolder+"ipB.txt"); 
//			//STEP 3: Open a connection
//			System.out.println("--URL:" + ConnectTo.DB_URL_REJ + "--");
//			Connection conn2 = DriverManager.getConnection(ConnectTo.DB_URL_REJ,ConnectTo.DB_USER_REJ,ConnectTo.DB_PASS_REJ);
//			//STEP 4: Execute a query
//			Statement stmt2 = conn2.createStatement();
//			System.out.println("Query:" + sQuery);
//			stmt2.execute(sQuery);
//			stmt2.close();
//			conn2.close();
//			
//			conn();
//						
//		} else {
//			//STEP 3: Open a connection
//			conn = DriverManager.getConnection(ConnectTo.DB_NAME,ConnectTo.DB_USER,ConnectTo.DB_PASS);
//
//			//STEP 4: Execute a query
//			stmt = conn.createStatement();
//			System.out.println("Query:" + sQuery);
//			stmt.execute(sQuery);	
//		}
//		
//		
//		//STEP 5: Extract data from result set (depend on query type)
//		if (!type.isEmpty() && type.compareTo("REJMSG")!=0) { //Query: View
//			if (type.compareTo(Titles.stFREETEXT)==0) { vATS.viewATS(stmt,Titles.stFREETEXT); sfree=vATS.pfree; if (!sfree.isEmpty()) sfree+=line; }
//			//------------------------------ ATS MESSAGES ------------------------------
//			if (type.compareTo(Titles.stALR)==0) { vATS.viewATS(stmt,Titles.stALR); salr=vATS.palr; if (!salr.isEmpty()) salr+=line; }
//			if (type.compareTo(Titles.stRCF)==0) { vATS.viewATS(stmt,Titles.stRCF); srcf=vATS.prcf; if (!srcf.isEmpty()) srcf+=line; }
//			if (type.compareTo(Titles.stFPL)==0) { vATS.viewATS(stmt,Titles.stFPL); sfpl=vATS.pfpl; if (!sfpl.isEmpty()) sfpl+=line; }
//			if (type.compareTo(Titles.stDLA)==0) { vATS.viewATS(stmt,Titles.stDLA); sdla=vATS.pdla; if (!sdla.isEmpty()) sdla+=line; }
//			if (type.compareTo(Titles.stCHG)==0) { vATS.viewATS(stmt,Titles.stCHG); schg=vATS.pchg; if (!schg.isEmpty()) schg+=line; }
//			if (type.compareTo(Titles.stCNL)==0) { vATS.viewATS(stmt,Titles.stCNL); scnl=vATS.pcnl; if (!scnl.isEmpty()) scnl+=line; }
//			if (type.compareTo(Titles.stDEP)==0) { vATS.viewATS(stmt,Titles.stDEP); sdep=vATS.pdep; if (!sdep.isEmpty()) sdep+=line; }
//			if (type.compareTo(Titles.stARR)==0) { vATS.viewATS(stmt,Titles.stARR); sarr=vATS.parr; if (!sarr.isEmpty()) sarr+=line; }
//			if (type.compareTo(Titles.stCDN)==0) { vATS.viewATS(stmt,Titles.stCDN); scdn=vATS.pcdn; if (!scdn.isEmpty()) scdn+=line; }
//			if (type.compareTo(Titles.stCPL)==0) { vATS.viewATS(stmt,Titles.stCPL); scpl=vATS.pcpl; if (!scpl.isEmpty()) scpl+=line; }
//			if (type.compareTo(Titles.stEST)==0) { vATS.viewATS(stmt,Titles.stEST); sest=vATS.pest; if (!sest.isEmpty()) sest+=line; }
//			if (type.compareTo(Titles.stACP)==0) { vATS.viewATS(stmt,Titles.stACP); sacp=vATS.pacp; if (!sacp.isEmpty()) sacp+=line; }
//			if (type.compareTo(Titles.stLAM)==0) { vATS.viewATS(stmt,Titles.stLAM); slam=vATS.plam; if (!slam.isEmpty()) slam+=line; }
//			if (type.compareTo(Titles.stRQP)==0) { vATS.viewATS(stmt,Titles.stRQP); srqp=vATS.prqp; if (!srqp.isEmpty()) srqp+=line; }
//			if (type.compareTo(Titles.stRQS)==0) { vATS.viewATS(stmt,Titles.stRQS); srqs=vATS.prqs; if (!srqs.isEmpty()) srqs+=line; }
//			if (type.compareTo(Titles.stSPL)==0) { vATS.viewATS(stmt,Titles.stSPL); sspl=vATS.pspl; if (!sspl.isEmpty()) sspl+=line; }
//			//------------------------------ NOTAM MESSAGES ------------------------------
//			if (type.compareTo(Titles.stNOTAM)==0 || type.compareTo(Titles.stRQNTM)==0 || type.compareTo(Titles.stCHKNTM)==0 || 
//				type.compareTo(Titles.stACTNTM)==0 || type.compareTo(Titles.stEXPNTM)==0) { 
//				vNOTAM.viewNOTAM(stmt,Titles.stNOTAM); snotam = vNOTAM.pnotam; if (!snotam.isEmpty()) snotam+=line; }
//			if (type.compareTo(Titles.stSNOWTAM)==0) { 
//				vNOTAM.viewNOTAM(stmt,Titles.stSNOWTAM); ssnowtam = vNOTAM.psnowtam; if (!ssnowtam.isEmpty()) ssnowtam+=line; }
//			if (type.compareTo(Titles.stASHTAM)==0) {
//				vNOTAM.viewNOTAM(stmt, Titles.stASHTAM); sashtam = vNOTAM.pashtam; if (!sashtam.isEmpty()) sashtam+=line; }
//			if (type.compareTo(Titles.stBIRDTAM)==0) { 
//				vNOTAM.viewNOTAM(stmt,Titles.stBIRDTAM); sbirdtam = vNOTAM.pbirdtam; if (!sbirdtam.isEmpty()) sbirdtam+=line; }
//			if (type.compareTo(Titles.stRQN)==0 || type.compareTo(Titles.stRQL)==0) { 
//				vNOTAM.viewNOTAM(stmt,Titles.stRQN); srqn = vNOTAM.prqn; if (!srqn.isEmpty()) srqn+=line; }
//			//------------------------------ METEO MESSAGES ------------------------------
//			if (type.compareTo(Titles.stRQM)==0) {
//				vMETEO.viewMETEO(stmt, type); srqm = vMETEO.pfreemet; if (!srqm.isEmpty()) srqm+=line; }
//			
//			if (type.compareTo(Titles.stMETAR)==0 || type.compareTo(Titles.stSPECI)==0) {
//				vMETEO.viewMETEO(stmt, type); smetar = vMETEO.pmetar; if (!smetar.isEmpty()) smetar+=line; }
//
//			if (type.compareTo(Titles.stSIGMET)==0 || type.compareTo(Titles.stAIRMET)==0 || type.compareTo(Titles.stWINSWAR)==0) { 
//				vMETEO.viewMETEO(stmt, type); ssigmet = vMETEO.psigmet; if (!ssigmet.isEmpty()) ssigmet+=line; }
//			
//			if (type.compareTo(Titles.stAIREP)==0 || type.compareTo(Titles.stTAFOR)==0 || type.compareTo(Titles.stARFOR)==0 || 
//					type.compareTo(Titles.stROFOR)==0 || type.compareTo(Titles.stWINTEM)==0 || type.compareTo(Titles.stADV)==0 ||
//					type.compareTo(Titles.stSYNOP)==0 || type.compareTo(Titles.stWARSYN)==0) { 
//				vMETEO.viewMETEO(stmt, type); sairep = vMETEO.pairep; if (!sairep.isEmpty()) sairep+=line; }
//			//------------------------------ Retrieve ------------------------------
//			if (type.compareTo(Titles.stIncoming)==0 || type.compareTo(Titles.stOutgoing)==0) { 
//				vRET.view(stmt, type); 
//				sret=vRET.pret; if (!sret.isEmpty()) sret+=line; 
//				srettrace=vRET.prettrace; if (!srettrace.isEmpty()) srettrace+=line; }
//			//------------------------------ Reject ------------------------------
//			if (type.compareTo(Titles.stReject)==0) { 
//				vRET.view(stmt, type); 
//				sreject=vRET.preject; if (!sreject.isEmpty()) sreject+=line; 
//				boolean hasilExe = stmt.execute(sQuery);
//				if (hasilExe == true) {	
//					editFields(stmt, hasilExe);						
//				}
//			}
//			
//		}
//		//STEP 6: Clean-up environment
////	    stmt.close();
////	    conn.close();
//	    if (type.compareTo("REJMSG")!=0) {
//	    	stmt.close();
//		    conn.close();
//	    }
//	    
//	} catch(SQLException se){
//		//Handle errors for JDBC
//		se.printStackTrace();
//	} catch(Exception e){
//		//Handle errors for Class.forName
//		e.printStackTrace();
//	} finally{
//		//finally block used to close resources
//		try {
//			if(stmt!=null) stmt.close();
//		} catch(SQLException se2) { }// nothing we can do
//		try {
//			if(conn!=null) conn.close();
//		} catch(SQLException se) {
//			se.printStackTrace();
//		}//end finally try
//	}//end try
//
//}