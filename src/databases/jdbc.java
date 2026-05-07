package databases;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import displays.MainForm;

import readwrite.ReadFromFile;
import setting.Times;
import actions.ViewATSFunction;


public class jdbc {
//	private static String[] tmp3a = new String[1000];
	static ViewATSFunction vATS = new ViewATSFunction();
	static Times time = new Times();
	ReadFromFile rff = new ReadFromFile();
	
	private static String sTableATS="";
	final public static String sTitle = "Warning: MySQL Connection";
	final public static String sMessage = "Please close some unused forms !!";
	
	static boolean www;
	static int index,i;
	static String contentStr="",key="",k="",x="",manual_ats="",tblName="",io="",filingTime="",sendAt="",priority="",
	originator="",filed_by="",bell="",ori_ref="",sDbName="",line="",all="",sfree="",salr="",srcf="",sfpl="",sdla="",schg="",scnl="",sdep="",sarr="",
	scdn="",scpl="",sest="",sacp="",slam="",srqp="",srqs="",sspl="",
	alr="",rcf="",fpl="",dla="",chg="",cnl="",dep="",arr="",cdn="",cpl="",est="",lam="",acp="",spl="",rqp="",rqs="",freeText="",
	type3aATS="",type3bATS="",type3cATS="",type5a="",type5b="",type5c="",type7a="",type7b="",type7c="",type8a="",type8b="",type9a="",
	type9b="",type9c="",type10a="",type10b="",type13a="",type13b="",type14a="",type14b="",type14c="",type14d="",type14e="",type15a="",
	type15b="",type15c="",type16a="",type16b="",type16c="",type16c2nd="",type17a="",type17b="",type17c="",type18="",type18a="",type18b="",
	type18Baru="",type19a="",type19b="",type19cUHF="",type19cVHF="",type19cELT="",type19dS="",type19dP="",type19dD="",type19dM="",type19dJ="",
	type19eJ="",type19eL="",type19eF="",type19eU="",type19eV="",type19fD="",type19f_number="",type19f_capacity="",type19f_cover="",
	type19f_colour="",type19i="",type19g="",type19hN="",type19Remarks="",type20="",type21="",type22="",space_reserved="",type21a="",type21b="",
	type21c="",type21d="",type21ef="",track_name="",track_id="",track_date="",track_status="",track_act="",track_exp="",track_waypoint="",
	track_level="",track_rts="",track_rmk="",sNav="",sCom="",sDat="",sSur="",sDep="",sDest="",sDof="",sReg="",sEet="",sSel="",sTyp="",sCode="",
	sDle="",sOpr="",sOrgn="",sPer="",sAltn="",sRalt="",sTalt="",sRif="",sRmk="",sPbn="",sSts="",depER="",destER="",routeER="",addressER="",
	des1="",des2="",des3="",des4="",des5="",des6="",des7="", des8="",des9="",des10="",des11="",des12="",des13="",des14="", des15="",des16="",
	des17="",des18="",des19="",des20="",des21="",status="",username="",password="",repass="",level_user="",origin_user="",name="",indicator="",
	location_ind="",queuedata="";

	public static Connection conn = null; // connection object
    private static Statement stmt = null; // statement object
    private static ResultSet rs = null; // result set object
    
    static int jmldata=0;
    public static String lines = "------------------------------\n";
    
    private static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String DB_CONNECTION = "";//"jdbc:oracle:thin:@localhost:1521:MKYONG";
	private static String DB_USER = "";//"user";
	private static String DB_PASSWORD = "";//"password";
	
    
    public static void setJmlData(int i) {
		jmldata = i;
	}
	
	public static int getJmlData() {
		return jmldata;
	}
    
	public static String naming() {
		time.tgl();
		String datetime = time.tanggal;
		if (datetime.contains("-")) datetime = datetime.replaceAll("-", "");
		if (datetime.contains(":")) datetime = datetime.replaceAll(":", "");
		if (datetime.contains(" ")) datetime = datetime.replaceAll(" ", "");
		String flnm="";
		flnm+=datetime; 
		return flnm;
	}
	
//	public static Connection getConnection() throws Exception {
//		// load the JDBC Driver
//	    Class.forName("com.mysql.jdbc.Driver");
//	    // define database connection parameters
//	    return DriverManager.getConnection(getUrl(), getUser(), getPass());
//	}

	public static void connClose() {
		try {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		 } catch(SQLException se) {
			 se.printStackTrace();
		 }
	}

	public static Connection getDBConnection() {
		Connection dbConnection = null;
 
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
 
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
 
		return dbConnection;
	}
	
	public jdbc() {
		rff.readConfiguration();
		DB_CONNECTION = rff.getDBUrl();
		DB_USER = rff.getDBUser();
		DB_PASSWORD = rff.getDBPass();
	}        
	 
	public static String getKey() { return (key); }
	public static String getUrl() { return DB_CONNECTION; }//tmp3a[0]; }
	public static String getUser() { return DB_USER; }//(tmp3a[1]);}
	public static String getPass() { return DB_PASSWORD; }//(tmp3a[2]);}
//	public static String getVersion() { return (tmp3a[4]);}
//	public static String getLength() { return (tmp3a[5]); }
//	public static int getMaxConn() { return (Integer.parseInt(tmp3a[6])); }
//	public static String getHeader() { return (tmp3a[7]); }
//	public static String getFooter() { return (tmp3a[8]); }
//	public static String getPdfCmd() { return (tmp3a[9]); }
//	public static String getNOTAMOffice() { return (tmp3a[10]); }
//	public static long getLoopConn() { return Integer.parseInt((tmp3a[11])); }
//	public static String getStatus() { return status; }
	public static String getDbName() { 

		int iDbName = getUrl().lastIndexOf("/");
		sDbName = getUrl().substring(iDbName+1, getUrl().length());
		System.out.println("sdbname;:" + sDbName + "#");
		return sDbName; }
	
	public static void connect(String insert) {
		java.util.Date tgl = new java.util.Date();
		long waktujava = tgl.getTime();
		java.sql.Timestamp stemp = new java.sql.Timestamp(waktujava);
		String yearMonth = stemp.toString().substring(0, 7);
		yearMonth = yearMonth.replace("-", "_");

		/**
		 * Nama tabel per bulan
		 */
		sTableATS = "air_message" + yearMonth;
		for (int ii=0;ii<2;ii++)  {
			try {
				/** 
				 * namaTabel, untuk FIELD tbl_name:check_status & masing-masing tabel
				 */
				// ATS MESSAGE
				if (insert.contains("'air_message'") || (insert.contains("air_message(")) || (insert.contains("air_message (")) || (insert.contains("air_message ( ")) || (insert.contains("air_message( "))) insert=insert.replace("air_message",sTableATS);
				
				System.out.println("\ninsert Query: " + insert);
				conn = getDBConnection();//getConnection();
				stmt = conn.createStatement();
				boolean hasilExe = stmt.execute(insert,Statement.RETURN_GENERATED_KEYS);
				rs = stmt.getGeneratedKeys();
				int updateCount = stmt.getUpdateCount();
				System.out.println(updateCount > 1 ? updateCount + " rows have inserted into the table." : updateCount+" row has inserted into the table.");
				//System.out.println("hasil execute=" + hasilExe);
				if (rs.next()) {
					ResultSetMetaData rsmd = rs.getMetaData();
					int colcount = rsmd.getColumnCount();
					do {
						for (int i = 1; i <= colcount; i++) {
							key = rs.getString(i);
						}
					} while (rs.next());
				}
				
				connClose();
				break;
			} catch (SQLException se) {
				connClose();
				System.out.println(se.getMessage());
				int iDbName = getUrl().lastIndexOf("/");
				sDbName = getUrl().substring(iDbName+1, getUrl().length());
				
				/**
				 * Membuat table per bulan, dengan format namaTabelTahun_Bulan
				 */
				// ATS Message
				if (se.getMessage().contains("'"+sDbName+"."+sTableATS+"' doesn't exist")) { createNewTable(MainForm.sFolder+"createATS.txt"); } 
	            else break;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				connClose();
			}//end of try
		} //end of for
	}
	
    static void createNewTable(String sFname) {
        String sSQL="";
        try {
        	// Membaca file createATS.txt => perintah untuk membuat tabel
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(sFname)));
            String lineStr = bf.readLine();
            while (lineStr!=null) {
                sSQL+=lineStr+"\n";
                lineStr=bf.readLine();
            }

			//ATS Message
            if (sFname.compareTo(MainForm.sFolder+"createATS.txt") == 0) sSQL=sSQL.replace("`air_message`", "`"+sTableATS+"`");
            
            String sComma[] = sSQL.split(";");
            for (int iC=0; iC<sComma.length; iC++) {
            	if (sComma[iC].length()>5) connect1(sComma[iC]);
            }
            System.out.println("-- create table finished --");
        } catch (Exception e) {
        	System.err.println("Error file:" + e.getMessage());
        } //end of try
    }

    // koneksi untuk create new table 
    public static void connect1(String insert) {
    	try {
    		conn = getDBConnection();//getConnection();
    		stmt = conn.createStatement();
    		boolean hasilExe = stmt.execute(insert, Statement.RETURN_GENERATED_KEYS);
    		//System.out.println("hasil execute=" + hasilExe);
    		connClose();
    	} catch (SQLException se) {
    		System.err.print("se: "+se.getMessage());
    	} catch (Exception e) {
    		System.err.print("e: "+e.getMessage());
    	} finally {
    		connClose();
    	}//end of try
    }
	
	// koneksi untuk delete
	public static void delete(String del) {
		System.out.println("\ndel Query: " + del);
        try {
        	conn = getDBConnection();//getConnection();
			stmt = conn.createStatement();
            boolean hasilExe = stmt.execute(del);
            //System.out.println("hasil execute=" + hasilExe);
            int updateCount = stmt.getUpdateCount();
            if (del.startsWith("DROP")) {
            	System.out.println("table has deleted from the database");	
            } else {
            	System.out.println(updateCount > 1 ? updateCount + " rows have deleted from the table" : updateCount+" row has deleted from the table");
            }
            
            connClose();
        } catch (SQLException se) {
        	//DialogFactory.openInfoDialog("Delete Message", se.toString());
			se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	connClose();
        }//end of try
    }
	
	// koneksi untuk alter
	public static void alter(String alter) {
		System.out.println("\nalter Query: " + alter);
		boolean hasilExe;
		try {	
			conn = getDBConnection();//getConnection();
			stmt = conn.createStatement();
			hasilExe = stmt.execute(alter);
			if (hasilExe!=true) { System.out.println("[ -- Altering column succeed -- ]\n"); } 
			else { System.out.println("[ -- Altering column failed -- ]\n"); }
			
			connClose();
		} catch (SQLException se) {
			//DialogFactory.openInfoDialog("Alter Message", se.getMessage());
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connClose();
		}
	}
	
	// koneksi untuk update
	public static void update(String update) {
        java.util.Date tgl = new java.util.Date();
		long waktujava = tgl.getTime();
		java.sql.Timestamp stemp = new java.sql.Timestamp(waktujava);
		String yearMonth = stemp.toString().substring(0, 7);
		yearMonth = yearMonth.replace("-", "_");

		// ATS Message
		sTableATS = "air_message" + yearMonth;
        if (update.contains("tbl_name='air_message'")) { update=update.replace("tbl_name='air_message'","tbl_name='"+sTableATS+"'"); }
        try {
        	System.out.println("\nupdate Query: " + update);
            conn = getDBConnection();//getConnection();
			stmt = conn.createStatement();
            boolean hasilExe = stmt.execute(update);
            int updateCount = stmt.getUpdateCount();
            System.out.println(updateCount > 1 ? updateCount + " rows have updated into the table" : updateCount+" row has updated into the table");
            //System.out.println("hasil execute=" + hasilExe);
            connClose();
        } catch (SQLException se) {
        	//DialogFactory.openInfoDialog("Update Message", se.toString());
			se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	connClose();
        }//end of try
    }

	public static String getPrintMsg() {
		String all="",free="",alr="",rcf="",fpl="",dla="",chg="",cnl="",dep="",arr="",cdn="",cpl="",est="",acp="",lam="",rqp="",rqs="",spl="";
		if (!free.isEmpty()) free="";
		if (!alr.isEmpty()) alr="";
		if (!rcf.isEmpty()) rcf="";
		if (!fpl.isEmpty()) fpl="";
		if (!dla.isEmpty()) dla="";
		if (!chg.isEmpty()) chg="";
		if (!cnl.isEmpty()) cnl="";
		if (!dep.isEmpty()) dep="";
		if (!arr.isEmpty()) arr="";
		if (!cdn.isEmpty()) cdn="";
		if (!cpl.isEmpty()) cpl="";
		if (!est.isEmpty()) est="";
		if (!acp.isEmpty()) acp="";
		if (!lam.isEmpty()) lam="";
		if (!rqp.isEmpty()) rqp="";
		if (!rqs.isEmpty()) rqs="";
		if (!spl.isEmpty()) spl="";
		
		free = vATS.freeText; if (!free.isEmpty()) free+=line;
		alr = vATS.alr; if (!alr.isEmpty()) alr+=line;
		rcf = vATS.rcf; if (!rcf.isEmpty()) rcf+=line;
		fpl = vATS.fpl; if (!fpl.isEmpty()) fpl+=line;
		dla = vATS.dla; if (!dla.isEmpty()) dla+=line;
		chg = vATS.chg; if (!chg.isEmpty()) chg+=line;
		cnl = vATS.cnl; if (!cnl.isEmpty()) cnl+=line;
		dep = vATS.dep; if (!dep.isEmpty()) dep+=line;
		arr = vATS.arr; if (!arr.isEmpty()) arr+=line;
		cdn = vATS.cdn; if (!cdn.isEmpty()) cdn+=line;
		cpl = vATS.cpl; if (!cpl.isEmpty()) cpl+=line;
		est = vATS.est; if (!est.isEmpty()) est+=line;
		acp = vATS.acp; if (!acp.isEmpty()) acp+=line;
		lam = vATS.lam; if (!lam.isEmpty()) lam+=line;
		rqp = vATS.rqp; if (!rqp.isEmpty()) rqp+=line;
		rqs = vATS.rqs; if (!rqs.isEmpty()) rqs+=line;
		spl = vATS.spl; if (!spl.isEmpty()) spl+=line;
		
		all = free + alr + rcf + fpl + dla + chg + cnl + dep + arr + cdn + cpl + est + acp + lam + rqp + rqs + spl;
		
		if (!vATS.freeText.isEmpty()) vATS.freeText="";
		if (!vATS.alr.isEmpty()) vATS.alr="";
		if (!vATS.rcf.isEmpty()) vATS.rcf="";
		if (!vATS.fpl.isEmpty()) vATS.fpl="";
		if (!vATS.dla.isEmpty()) vATS.dla="";
		if (!vATS.chg.isEmpty()) vATS.chg="";
		if (!vATS.cnl.isEmpty()) vATS.cnl="";
		if (!vATS.dep.isEmpty()) vATS.dep="";
		if (!vATS.arr.isEmpty()) vATS.arr="";
		if (!vATS.cdn.isEmpty()) vATS.cdn="";
		if (!vATS.cpl.isEmpty()) vATS.cpl="";
		if (!vATS.est.isEmpty()) vATS.est="";
		if (!vATS.acp.isEmpty()) vATS.acp="";
		if (!vATS.lam.isEmpty()) vATS.lam="";
		if (!vATS.rqp.isEmpty()) vATS.rqp="";
		if (!vATS.rqs.isEmpty()) vATS.rqs="";
		if (!vATS.spl.isEmpty()) vATS.spl="";
		
		return all;
	}
	
	public static void select(String select, String tbl_name, String type, String btn) {
		System.out.println("\nslct Query:" + select);
//		Connection conn = null; // connection object
//	    Statement stmt = null; // statement object
		boolean hasilExe;
		
		try {	
			conn = getDBConnection();//getConnection();
			stmt = conn.createStatement();
			String SQL = select;
			hasilExe = stmt.execute(SQL);
			if (hasilExe == true) {	
//				if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE id_free_text='")) vATS.viewFreeATS(stmt,hasilExe,btn);
				
				if(SQL.startsWith("SELECT * FROM queuelp WHERE idcnt='")) vATS.viewQueue(stmt,hasilExe,btn);
				else if(SQL.startsWith("SELECT * FROM warning WHERE idcnt=")) vATS.viewWarning(stmt,hasilExe,btn);
				
				else if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE id_ats=")) vATS.viewATS(stmt,hasilExe,btn);
				else if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='ALR'")) vATS.viewALR(stmt,hasilExe,btn);
				else if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='RCF'")) vATS.viewRCF(stmt,hasilExe,btn);
				else if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='FPL'")) vATS.viewFPL(stmt,hasilExe,btn);
				else if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='DLA'")) vATS.viewDLA(stmt,hasilExe,btn);
				else if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='CHG'")) vATS.viewCHG(stmt,hasilExe,btn);
				else if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='CNL'")) vATS.viewCNL(stmt,hasilExe,btn);
				else if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='DEP'")) vATS.viewDEP(stmt,hasilExe,btn);
				else if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='ARR'")) vATS.viewARR(stmt,hasilExe,btn);
				else if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='CDN'")) vATS.viewCDN(stmt,hasilExe,btn);
				else if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='CPL'")) vATS.viewCPL(stmt,hasilExe,btn);
				else if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='EST'")) vATS.viewEST(stmt,hasilExe,btn);
				else if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='ACP'")) vATS.viewACP(stmt,hasilExe,btn);
				else if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='LAM'")) vATS.viewLAM(stmt,hasilExe,btn);
				else if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='RQP'")) vATS.viewRQP(stmt,hasilExe,btn);
				else if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='RQS'")) vATS.viewRQS(stmt,hasilExe,btn);
				else if(SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='SPL'")) vATS.viewSPL(stmt,hasilExe,btn);
				
//				System.out.println("type::" + type);
				if (type.compareToIgnoreCase("")==0 || type.compareToIgnoreCase("queue")==0 || type.compareToIgnoreCase("warning")==0 || 
						type.compareToIgnoreCase("FREETEXT")==0 || type.compareToIgnoreCase("FREE TEXT")==0) { 
					if (ReadFromFile.ReadInputFile1("/tp/template/filedby.txt").compareTo("1")==0) {sfree=vATS.freeText;} else sfree=vATS.pfreeText;
					if (!sfree.isEmpty()) sfree+=line; }
				if (type.compareToIgnoreCase("alr")==0) { 
					if (ReadFromFile.ReadInputFile1("/tp/template/filedby.txt").compareTo("1")==0) {salr=vATS.alr;} else salr=vATS.palr;
					if (!salr.isEmpty()) salr+=line; }
				if (type.compareToIgnoreCase("rcf")==0) { 
					if (ReadFromFile.ReadInputFile1("/tp/template/filedby.txt").compareTo("1")==0) {srcf=vATS.rcf;} else srcf=vATS.prcf;
					if (!srcf.isEmpty()) srcf+=line; }
				if (type.compareToIgnoreCase("fpl")==0) { 
					if (ReadFromFile.ReadInputFile1("/tp/template/filedby.txt").compareTo("1")==0) {sfpl=vATS.fpl;} else sfpl=vATS.pfpl;
					if (!sfpl.isEmpty()) sfpl+=line; }
				if (type.compareToIgnoreCase("dla")==0) { 
					if (ReadFromFile.ReadInputFile1("/tp/template/filedby.txt").compareTo("1")==0) {sdla=vATS.dla;} else sdla=vATS.pdla;
					if (!sdla.isEmpty()) sdla+=line; }
				if (type.compareToIgnoreCase("chg")==0) { 
					if (ReadFromFile.ReadInputFile1("/tp/template/filedby.txt").compareTo("1")==0) {schg=vATS.chg;} else schg=vATS.pchg;
					if (!schg.isEmpty()) schg+=line; }
				if (type.compareToIgnoreCase("cnl")==0) { 
					if (ReadFromFile.ReadInputFile1("/tp/template/filedby.txt").compareTo("1")==0) {scnl=vATS.cnl;} else scnl=vATS.pcnl;
					if (!scnl.isEmpty()) scnl+=line; }
				if (type.compareToIgnoreCase("dep")==0) { 
					if (ReadFromFile.ReadInputFile1("/tp/template/filedby.txt").compareTo("1")==0) {sdep=vATS.dep;} else sdep=vATS.pdep;
					if (!sdep.isEmpty()) sdep+=line; }
				if (type.compareToIgnoreCase("arr")==0) { 
					if (ReadFromFile.ReadInputFile1("/tp/template/filedby.txt").compareTo("1")==0) {sarr=vATS.arr;} else sarr=vATS.parr;
					if (!sarr.isEmpty()) sarr+=line; }
				if (type.compareToIgnoreCase("cdn")==0) { 
					if (ReadFromFile.ReadInputFile1("/tp/template/filedby.txt").compareTo("1")==0) {scdn=vATS.cdn;} else scdn=vATS.pcdn;
					if (!scdn.isEmpty()) scdn+=line; }
				if (type.compareToIgnoreCase("cpl")==0) { 
					if (ReadFromFile.ReadInputFile1("/tp/template/filedby.txt").compareTo("1")==0) {scpl=vATS.cpl;} else scpl=vATS.pcpl;
					if (!scpl.isEmpty()) scpl+=line; }
				if (type.compareToIgnoreCase("est")==0) { 
					if (ReadFromFile.ReadInputFile1("/tp/template/filedby.txt").compareTo("1")==0) {sest=vATS.est;} else sest=vATS.pest;
					if (!sest.isEmpty()) sest+=line; }
				if (type.compareToIgnoreCase("acp")==0) { 
					if (ReadFromFile.ReadInputFile1("/tp/template/filedby.txt").compareTo("1")==0) {sacp=vATS.acp;} else sacp=vATS.pacp;
					if (!sacp.isEmpty()) sacp+=line; }
				if (type.compareToIgnoreCase("lam")==0) { 
					if (ReadFromFile.ReadInputFile1("/tp/template/filedby.txt").compareTo("1")==0) {slam=vATS.lam;} else slam=vATS.plam;
					if (!slam.isEmpty()) slam+=line; }
				if (type.compareToIgnoreCase("rqp")==0) { 
					if (ReadFromFile.ReadInputFile1("/tp/template/filedby.txt").compareTo("1")==0) {srqp=vATS.rqp;} else srqp=vATS.prqp;
					if (!srqp.isEmpty()) srqp+=line; }
				if (type.compareToIgnoreCase("rqs")==0) { 
					if (ReadFromFile.ReadInputFile1("/tp/template/filedby.txt").compareTo("1")==0) {srqs=vATS.rqs;} else srqs=vATS.prqs;
					if (!srqs.isEmpty()) srqs+=line; }
				if (type.compareToIgnoreCase("spl")==0) { 
					if (ReadFromFile.ReadInputFile1("/tp/template/filedby.txt").compareTo("1")==0) {sspl=vATS.spl;} else sspl=vATS.pspl;
					if (!sspl.isEmpty()) sspl+=line; }
//				if (type.compareToIgnoreCase("RCF")==0) { srcf = vATS.prcf; if (!srcf.isEmpty()) srcf+=line; }
//				if (type.compareToIgnoreCase("FPL")==0) { sfpl = vATS.pfpl; if (!sfpl.isEmpty()) sfpl+=line; }
//				if (type.compareToIgnoreCase("DLA")==0) { sdla = vATS.pdla; if (!sdla.isEmpty()) sdla+=line; }
//				if (type.compareToIgnoreCase("CHG")==0) { schg = vATS.pchg; if (!schg.isEmpty()) schg+=line; }
//				if (type.compareToIgnoreCase("CNL")==0) { scnl = vATS.pcnl; if (!scnl.isEmpty()) scnl+=line; }
//				if (type.compareToIgnoreCase("DEP")==0) { sdep = vATS.pdep; if (!sdep.isEmpty()) sdep+=line; }
//				if (type.compareToIgnoreCase("ARR")==0) { sarr = vATS.parr; if (!sarr.isEmpty()) sarr+=line; }
//				if (type.compareToIgnoreCase("CDN")==0) { scdn = vATS.pcdn; if (!scdn.isEmpty()) scdn+=line; }
//				if (type.compareToIgnoreCase("CPL")==0) { scpl = vATS.pcpl; if (!scpl.isEmpty()) scpl+=line; }
//				if (type.compareToIgnoreCase("EST")==0) { sest = vATS.pest; if (!sest.isEmpty()) sest+=line; }
//				if (type.compareToIgnoreCase("ACP")==0) { sacp = vATS.pacp; if (!sacp.isEmpty()) sacp+=line; }
//				if (type.compareToIgnoreCase("LAM")==0) { slam = vATS.plam; if (!slam.isEmpty()) slam+=line; }
//				if (type.compareToIgnoreCase("RQP")==0) { srqp = vATS.prqp; if (!srqp.isEmpty()) srqp+=line; }
//				if (type.compareToIgnoreCase("RQS")==0) { srqs = vATS.prqs; if (!srqs.isEmpty()) srqs+=line; }
//				if (type.compareToIgnoreCase("SPL")==0) { sspl = vATS.pspl; if (!sspl.isEmpty()) sspl+=line; }
				
			}
			
			connClose();
		} catch (SQLException se) {
			//DialogFactory.openInfoDialog("View Message", se.getMessage());
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connClose();
		}
	}

	public static String getaftn() { return sfree; }
	public static String getalr() { return salr; }
	public static String getrcf() { return srcf; }
	public static String getfpl() { return sfpl; }
	public static String getdla() { return sdla; }
	public static String getchg() { return schg; }
	public static String getcnl() { return scnl; }
	public static String getdep() { return sdep; }
	public static String getarr() { return sarr; }
	public static String getcdn() { return scdn; }
	public static String getcpl() { return scpl; }
	public static String getest() { return sest; }
	public static String getacp() { return sacp; }
	public static String getlam() { return slam; }
	public static String getrqp() { return srqp; }
	public static String getrqs() { return srqs; }
	public static String getspl() { return sspl; }
	
	// koneksi untuk edit
	public static void select2(String select, String tbl_name) {
		System.out.println("\nslct Query: " + select);
		tblName=tbl_name;
//		Connection conn = null; // connection object
//	    Statement stmt = null; // statement object
		boolean hasilExe;
		
		try {	
			conn = getDBConnection();//getConnection();
			stmt = conn.createStatement();
			String SQL = select;
			hasilExe = stmt.execute(SQL);
			if (hasilExe == true) {	
				if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE id_free_text='")) editATSFree(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE id_ats=")) editATS(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='ALR'")) editALR(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='RCF'")) editRCF(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='FPL'")) editFPL(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='DLA'")) editDLA(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='CHG'")) editCHG(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='CNL'")) editCNL(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='DEP'")) editDEP(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='ARR'")) editARR(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='CDN'")) editCDN(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='CPL'")) editCPL(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='EST'")) editEST(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='ACP'")) editACP(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='LAM'")) editLAM(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='SPL'")) editSPL(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='RQP'")) editRQP(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM "+tbl_name+" WHERE type3a='RQS'")) editRQS(stmt,hasilExe);
				 
				else if (SQL.startsWith("SELECT * FROM route WHERE id_number='")) editEntryRoute(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM login_tabel")) editLogin(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM location_indicator WHERE id='")) editLocInd(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM aircraft_wtc WHERE id='")) editType9(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM aircraft_reg WHERE id='")) editReg(stmt,hasilExe);
				else if (SQL.startsWith("SELECT * FROM queuelp WHERE idcnt='")) editQueue(stmt,hasilExe);
			}
			
			connClose();
		} catch (SQLException se) {
			//DialogFactory.openInfoDialog("Edit Message", se.getMessage());
			se.printStackTrace();
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			connClose();
		}
	}

	static void Header(ResultSet rs) {
		try {
			des1=rs.getString("destination1"); if (des1 == null) des1="";
			des2=rs.getString("destination2"); if (des2 == null) des2="";
			des3=rs.getString("destination3"); if (des3 == null) des3="";
			des4=rs.getString("destination4"); if (des4 == null) des4="";
			des5=rs.getString("destination5"); if (des5 == null) des5="";
			des6=rs.getString("destination6"); if (des6 == null) des6="";
			des7=rs.getString("destination7"); if (des7 == null) des7="";
			des8=rs.getString("destination8"); if (des8 == null) des8="";
			des9=rs.getString("destination9"); if (des9 == null) des9="";
			des10=rs.getString("destination10"); if (des10 == null) des10="";
			des11=rs.getString("destination11"); if (des11 == null) des11="";
			des12=rs.getString("destination12"); if (des12 == null) des12="";
			des13=rs.getString("destination13"); if (des13 == null) des13="";
			des14=rs.getString("destination14"); if (des14 == null) des14="";
			des15=rs.getString("destination15"); if (des15 == null) des15="";
			des16=rs.getString("destination16"); if (des16 == null) des16="";
			des17=rs.getString("destination17"); if (des17 == null) des17="";
			des18=rs.getString("destination18"); if (des18 == null) des18="";
			des19=rs.getString("destination19"); if (des19 == null) des19="";
			des20=rs.getString("destination20"); if (des20 == null) des20="";
			des21=rs.getString("destination21"); if (des21 == null) des21="";
			
			priority = rs.getString("priority"); if (priority == null) priority="";
			originator = rs.getString("originator"); if (originator == null) originator="";
			filingTime = rs.getString("filing_time"); if (filingTime == null) filingTime="";
			sendAt = rs.getString("jam"); if (sendAt == null) sendAt="";
			ori_ref = rs.getString("ori_ref"); if (ori_ref == null) ori_ref="";
			
			bell = rs.getString("bell"); if (bell== null) bell="";
			
			//TAMBAHAN untuk incoming FREE TEXT
			if (priority.compareToIgnoreCase("xx")==0) priority="";
			
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editHeader(ResultSet rs) {
		try {
			Header(rs);
			filed_by = rs.getString("filedby"); if (filed_by == null) filed_by="";
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editHeader2(ResultSet rs) { // untuk ats n freetext [filled_by] 
		try {
			Header(rs);
			filed_by = rs.getString("filedby"); if (filed_by == null) filed_by="";
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editType3(ResultSet rs) {
		try {
			type3aATS = rs.getString("type3a"); if (type3aATS == null) type3aATS="";
			type3bATS = rs.getString("type3b");  if (type3bATS == null) type3bATS="";
			type3cATS = rs.getString("type3c"); if (type3cATS == null) type3cATS="";
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editType5(ResultSet rs) {
		try {
			type5a = rs.getString("type5a"); if (type5a == null) type5a="";
			type5b = rs.getString("type5b"); if (type5b == null) type5b="";
			type5c = rs.getString("type5c"); if (type5c == null) type5c="";
			if (type5c.contains("`")) type5c = type5c.replace("`", "'");
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editType7(ResultSet rs) {
		try {
			type7a = rs.getString("type7a"); if (type7a == null) type7a="";
			type7b = rs.getString("type7b"); if (type7b == null) type7b="";
			type7c = rs.getString("type7c"); if (type7c == null) type7c="";
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editType8(ResultSet rs) {
		try {
			type8a = rs.getString("type8a"); if (type8a == null) type8a="";
			type8b = rs.getString("type8b"); if (type8b == null) type8b="";
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editType9(ResultSet rs) {
		try {
			type9a = rs.getString("type9a"); if (type9a == null) type9a="";
			type9b = rs.getString("type9b"); if (type9b == null) type9b="";
			type9c = rs.getString("type9c"); if (type9c == null) type9c="";
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editType10(ResultSet rs) {
		try {
			type10a = rs.getString("type10a"); if (type10a == null) type10a="";
			type10b = rs.getString("type10b"); if (type10b == null) type10b="";
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editType13(ResultSet rs) {
		try {
			type13a = rs.getString("type13a"); if (type13a == null) type13a="";
			type13b = rs.getString("type13b"); if (type13b == null) type13b="";
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editType14(ResultSet rs) {
		try {
			type14a = rs.getString("type14a"); if (type14a == null) type14a="";
			type14b = rs.getString("type14b"); if (type14b == null) type14b="";
			type14c = rs.getString("type14c"); if (type14c == null) type14c="";
			type14d = rs.getString("type14d"); if (type14d == null) type14d="";
			type14e = rs.getString("type14e"); if (type14e == null) type14e="";
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editType15(ResultSet rs) {
		try {
			type15a = rs.getString("type15a"); if (type15a == null) type15a="";
			type15b = rs.getString("type15b"); if (type15b == null) type15b="";
			type15c = rs.getString("type15c"); if (type15c == null) type15c="";
			if (type15c.contains("`")) type15c = type15c.replace("`", "'");
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editType16(ResultSet rs) {
		try {
			type16a = rs.getString("type16a"); if (type16a == null) type16a="";
			type16b = rs.getString("type16b"); if (type16b == null) type16b="";
			type16c = rs.getString("type16c");  if (type16c == null) type16c="";
			type16c2nd	= rs.getString("type16c_2nd"); if (type16c2nd== null) type16c2nd="";
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editType17(ResultSet rs) {
		try {
			type17a = rs.getString("type17a"); if (type17a == null) type17a="";
			type17b = rs.getString("type17b"); if (type17b == null) type17b="";
			type17c = rs.getString("type17c");  if (type17c == null) type17c="";
			if (type17c.contains("`")) type17c = type17c.replace("`", "'");
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editType18(ResultSet rs, String type) {
		try {
			String other="";
			sSts="";sPbn="";sNav=""; sCom="";sDat="";sSur=""; sDep="";sDest="";sDof=""; sReg="";sEet="";sSel=""; sTyp="";sCode="";sDle="";
	     	sOpr="";sOrgn="";sPer=""; sAltn="";sRalt="";sTalt=""; sRif="";sRmk="";
	     
	     	String type18 = "";
	     	if (type.compareTo("RPL") == 0) {
	     		type18 = rs.getString("Q_remarks");if (type18 == null) type18 ="";	
	     	} else {
	     		type18 = rs.getString("type18");if (type18 == null) type18 ="";
	     	}
	     	jdbc.type18 = type18;
			if (type18.contains("`")) type18 = type18.replace("`", "'");
	     	
	     	type18 = type18.replaceAll("/", "~");
			if (type18.startsWith(" ")) type18=type18.replaceFirst("\\s+", "");
			//if (type18.compareTo("0")==0) type18="";
	
			//percobaan
			if (type.compareToIgnoreCase("dla")==0 || type.compareToIgnoreCase("chg")==0 || type.compareToIgnoreCase("cnl")==0 ||
					type.compareToIgnoreCase("dep")==0 || type.compareToIgnoreCase("rqp")==0 || type.compareToIgnoreCase("rqs")==0) {
				if (type18.compareTo("0")==0) type18="DOF/0";
			} else if (type18.compareTo("0")==0) type18="";
		     			
			//dari db untuk fungsi view
			if (type18.contains("STS~")) type18 = type18.replace("STS~", "STS/");
			if (type18.contains("PBN~")) type18 = type18.replace("PBN~", "PBN/");
			if (type18.contains("NAV~")) type18 = type18.replace("NAV~", "NAV/");
			if (type18.contains("COM~")) type18 = type18.replace("COM~", "COM/");
			if (type18.contains("DAT~")) type18 = type18.replace("DAT~", "DAT/");
			if (type18.contains("SUR~")) type18 = type18.replace("SUR~", "SUR/");
			if (type18.contains("DEP~")) type18 = type18.replace("DEP~", "DEP/");
			if (type18.contains("DEST~")) type18 = type18.replace("DEST~", "DEST/");
			if (type18.contains("REG~")) type18 = type18.replace("REG~", "REG/");
			if (type18.contains("DOF~")) type18 = type18.replace("DOF~", "DOF/");
			if (type18.contains("EET~")) type18 = type18.replace("EET~", "EET/");
			if (type18.contains("SEL~")) type18 = type18.replace("SEL~", "SEL/");
			if (type18.contains("TYP~")) type18 = type18.replace("TYP~", "TYP/");
			if (type18.contains("CODE~")) type18 = type18.replace("CODE~", "CODE/");
			if (type18.contains("DLE~")) type18 = type18.replace("DLE~", "DLE/");
			if (type18.contains("OPR~")) type18 = type18.replace("OPR~", "OPR/");
			if (type18.contains("ORGN~")) type18 = type18.replace("ORGN~", "ORGN/");
			if (type18.contains("PER~")) type18 = type18.replace("PER~", "PER/");
			if (type18.contains("ALTN~")) type18 = type18.replace("ALTN~", "ALTN/");
			if (type18.contains("RALT~")) type18 = type18.replace("RALT~", "RALT/");
			if (type18.contains("TALT~")) type18 = type18.replace("TALT~", "TALT/");
			if (type18.contains("RIF~")) type18 = type18.replace("RIF~", "RIF/");
			if (type18.contains("RMK~")) type18 = type18.replace("RMK~", "RMK/");
			
			if (!type18.equals("")) {
				if (type18.contains("\n")) type18 = type18.replaceAll("\n", "^");
	
				String sub[] = type18.split(" ");
				for (int n=0; n<sub.length; n++) {
					if (sub[n].contains("/")) { x=sub[n]; } 
					else { x+=" "+sub[n]; }
					
					if (x.startsWith("STS/")) sSts = x.substring(4, x.length());
					else if (x.startsWith("PBN/")) sPbn = x.substring(4, x.length());
					else if (x.startsWith("NAV/")) sNav = x.substring(4, x.length());
					else if (x.startsWith("COM/")) sCom = x.substring(4, x.length());
					else if (x.startsWith("DAT/")) sDat = x.substring(4, x.length());
					else if (x.startsWith("SUR/")) sSur = x.substring(4, x.length());
					else if (x.startsWith("DEP/")) sDep = x.substring(4, x.length());
					else if (x.startsWith("DEST/")) sDest = x.substring(5, x.length());
					else if (x.startsWith("DOF/")) sDof = x.substring(4, x.length());
					else if (x.startsWith("REG/")) sReg = x.substring(4, x.length());
					else if (x.startsWith("EET/")) sEet = x.substring(4, x.length());
					else if (x.startsWith("SEL/")) sSel = x.substring(4, x.length());
					else if (x.startsWith("TYP/")) sTyp = x.substring(4, x.length());
					else if (x.startsWith("CODE/")) sCode = x.substring(5, x.length());
					else if (x.startsWith("DLE/")) sDle = x.substring(4, x.length());
					else if (x.startsWith("OPR/")) sOpr = x.substring(4, x.length());
					else if (x.startsWith("ORGN/")) sOrgn = x.substring(5, x.length());
					else if (x.startsWith("ALTN/")) sAltn = x.substring(5, x.length());
					else if (x.startsWith("RALT/")) sRalt = x.substring(5, x.length());
					else if (x.startsWith("TALT/")) sTalt = x.substring(5, x.length());
					else if (x.startsWith("RIF/")) sRif = x.substring(4, x.length());
					else if (x.startsWith("RMK/")) sRmk = x.substring(4, x.length());
					else if (x.startsWith("PER/")) sPer = x.substring(4, x.length());
					else { other+= " "+x; }
				} //end of for n
				
				if (other.startsWith(" ")) other=other.replaceFirst(" ", "");
				if (sRmk.compareTo("")==0) { sRmk=other; } else { sRmk+=" "+other; }
			} else {
				sSts="";sPbn="";sNav=""; sCom="";sDat="";sSur=""; sDep="";sDest="";sDof=""; sReg="";sEet="";sSel=""; sTyp="";sCode="";sDle="";
				sOpr="";sOrgn="";sPer=""; sAltn="";sRalt="";sTalt=""; sRif="";sRmk="";
			}
			
			// mengembalikan tanda '/' untuk field freetext type18baru
			if (sNav.contains("~")) sNav = sNav.replaceAll("~", "/");
			if (sCom.contains("~")) sCom = sCom.replaceAll("~", "/");
			if (sDat.contains("~")) sDat = sDat.replaceAll("~", "/");
			if (sSur.contains("~")) sSur = sSur.replaceAll("~", "/");
			if (sDep.contains("~")) sDep = sDep.replaceAll("~", "/");
			if (sDest.contains("~")) sDest = sDest.replaceAll("~", "/");
			if (sTyp.contains("~")) sTyp = sTyp.replaceAll("~", "/");
			if (sOpr.contains("~")) sOpr = sOpr.replaceAll("~", "/");
			if (sOrgn.contains("~")) sOrgn = sOrgn.replaceAll("~", "/");
			if (sAltn.contains("~")) sAltn = sAltn.replaceAll("~", "/");
			if (sRalt.contains("~")) sRalt = sRalt.replaceAll("~", "/");
			if (sTalt.contains("~")) sTalt = sTalt.replaceAll("~", "/");
			if (sRif.contains("~")) sRif = sRif.replaceAll("~", "/");
			if (sRmk.contains("~")) sRmk = sRmk.replaceAll("~", "/");
			// mengembalikan tanda '\n' untuk field freetext type18baru
			if (sNav.contains("^")) sNav = sNav.replace("^", "\n");
			if (sCom.contains("^")) sCom = sCom.replace("^", "\n");
			if (sDat.contains("^")) sDat = sDat.replace("^", "\n");
			if (sSur.contains("^")) sSur = sSur.replace("^", "\n");
			if (sDep.contains("^")) sDep = sDep.replace("^", "\n");
			if (sDest.contains("^")) sDest = sDest.replace("^", "\n");
			if (sTyp.contains("^")) sTyp = sTyp.replace("^", "\n");
			if (sOpr.contains("^"))  sOpr = sOpr.replace("^", "\n");
			if (sOrgn.contains("^")) sOrgn = sOrgn.replace("^", "\n");
			if (sAltn.contains("^")) sAltn = sAltn.replace("^", "\n");
			if (sRalt.contains("^")) sRalt = sRalt.replace("^", "\n");
			if (sTalt.contains("^")) sTalt = sTalt.replace("^", "\n");
			if (sRif.contains("^")) sRif = sRif.replace("^", "\n");
			if (sRmk.contains("^")) sRmk = sRmk.replace("^", "\n");
			// menghilangkan tanda ^ khusus untuk indikator di bawah ini
			if (sSts.contains("^")) sSts = sSts.replace("^", " ");
			if (sPbn.contains("^")) sPbn = sPbn.replace("^", "");
			if (sReg.contains("^")) sReg = sReg.replace("^", "");
			if (sDof.contains("^")) sDof = sDof.replace("^", "");
			if (sEet.contains("^")) sEet = sEet.replace("^", " ");
			if (sSel.contains("^")) sSel = sSel.replace("^", "");
			if (sCode.contains("^")) sCode = sCode.replace("^", "");
			if (sDle.contains("^")) sDle = sDle.replace("^", "");
			if (sPer.contains("^")) sPer = sPer.replace("^", "");
			//---------------------------------------------------------
			if (sSts.compareTo("")==0) /*sSts=" STS/"+sSts; else*/ sSts="";
			if (sPbn.compareTo("")==0) /*sPbn=" PBN/"+sPbn; else*/ sPbn="";
			if (sNav.compareTo("")==0) /*sNav=" NAV/"+sNav; else*/ sNav="";
			 
			if (sCom.compareTo("")==0) /*sCom=" COM/"+sCom; else*/ sCom="";
			if (sDat.compareTo("")==0) /*sDat=" DAT/"+sDat; else*/ sDat="";
			if (sSur.compareTo("")==0) /*sSur=" SUR/"+sSur; else*/ sSur="";
				
			if (sDep.compareTo("")==0) /*sDep=" DEP/"+sDep; else*/ sDep="";
			if (sDest.compareTo("")==0) /*sDest=" DEST/"+sDest; else*/ sDest="";
			if (sReg.compareTo("")==0) /*sReg=" REG/"+sReg; else*/ sReg="";
				
			if (sDof.compareTo("")==0) /*sDof=" DOF/"+sDof; else*/ sDof="";
			if (sEet.compareTo("")==0) /*sEet=" EET/"+sEet; else*/ sEet="";
			if (sSel.compareTo("")==0) /*sSel=" SEL/"+sSel; else*/ sSel="";
				
			if (sTyp.compareTo("")==0) /*sTyp=" TYP/"+sTyp; else*/ sTyp="";
			if (sCode.compareTo("")==0) /*sCode=" CODE/"+sCode; else*/ sCode="";
			if (sDle.compareTo("")==0) /*sDle=" DLE/"+sDle; else*/ sDle="";
				
			if (sOpr.compareTo("")==0) /*sOpr=" OPR/"+sOpr; else*/ sOpr="";
			if (sOrgn.compareTo("")==0) /*sOrgn=" ORGN/"+sOrgn; else*/ sOrgn="";
			if (sPer.compareTo("")==0) /*sPer=" PER/"+sPer; else*/ sPer="";
				
			if (sAltn.compareTo("")==0) /*sAltn=" ALTN/"+sAltn; else*/ sAltn="";
			if (sRalt.compareTo("")==0) /*sRalt=" RALT/"+sRalt; else*/ sRalt="";
			if (sTalt.compareTo("")==0) /*sTalt=" TALT/"+sTalt; else*/ sTalt="";
				
			if (sRif.compareTo("")==0) /*sRif=" RIF/"+sRif; else*/ sRif="";
			if (sRmk.compareTo("")==0) /*sRmk=" RMK/"+sRmk; else*/ sRmk="";	
			//---------------------------------------------------------
			if (!sRmk.isEmpty() && sRmk.endsWith(" ")) {
				int ind = sRmk.lastIndexOf(" ");
				sRmk = sRmk.substring(0, ind);
			}
			if (!sPer.isEmpty() && sPer.length()>1) {
				sPer = sPer.substring(0, 1);
			}
			if (type.compareTo("RPL") != 0) {
				type18a = rs.getString("type18a"); if (type18a == null) type18a="";
				if (type18a.equals("REG/0")) type18a=""; 
				else if (!type18a.equals("") && !type18a.equals("REG/0") && type18a.startsWith("REG/")) type18a = type18a.replaceFirst("REG/", "");
				//---------------------------------------------------------
				editType18b(rs);
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editType18b(ResultSet rs) {
		try {
			type18b = rs.getString("type18b"); if (type18b == null) type18b="";
			if (type18b.equals("DOF/0")) type18b=""; 
			else if (!type18b.equals("") && !type18b.equals("DOF/0") && type18b.startsWith("DOF/")) type18b = type18b.replaceFirst("DOF/", "");
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editType19(ResultSet rs) {
		try {
			type19a = rs.getString("type19a"); if (type19a == null) type19a="";
			type19b = rs.getString("type19b"); if (type19b == null) type19b="";
			type19cUHF = rs.getString("type19cUHF"); if (type19cUHF == null) type19cUHF="";
			type19cVHF = rs.getString("type19cVHF"); if (type19cVHF == null) type19cVHF="";
			type19cELT = rs.getString("type19cELT"); if (type19cELT == null) type19cELT="";
			type19dS = rs.getString("type19dS"); if (type19dS == null) type19dS="";
			type19dP = rs.getString("type19dP"); if (type19dP == null) type19dP="";
			type19dD = rs.getString("type19dD"); if (type19dD == null) type19dD="";
			type19dM = rs.getString("type19dM"); if (type19dM == null) type19dM="";
			type19dJ = rs.getString("type19dJ"); if (type19dJ == null) type19dJ="";
			type19eJ = rs.getString("type19eJ"); if (type19eJ == null) type19eJ="";
			type19eL = rs.getString("type19eL"); if (type19eL == null) type19eL="";
			type19eF = rs.getString("type19eF"); if (type19eF == null) type19eF="";
			type19eU = rs.getString("type19eU"); if (type19eU == null) type19eU="";
			type19eV = rs.getString("type19eV"); if (type19eV == null) type19eV="";
			type19fD = rs.getString("type19fD"); if (type19fD == null) type19fD="";
			type19f_number = rs.getString("type19f_number"); if (type19f_number == null) type19f_number="";
			type19f_capacity = rs.getString("type19f_capacity"); if (type19f_capacity == null) type19f_capacity="";
			type19f_cover = rs.getString("type19f_cover"); if (type19f_cover == null) type19f_cover="";
			type19f_colour = rs.getString("type19f_colour"); if (type19f_colour == null) type19f_colour="";
			type19g = rs.getString("type19g"); if (type19g == null) type19g="";
			type19hN = rs.getString("type19hN"); if (type19hN == null) type19hN="";
			type19Remarks = rs.getString("type19Remarks"); if (type19Remarks == null) type19Remarks="";
			type19i = rs.getString("type19i") ; if (type19i == null) type19i="";
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editType20(ResultSet rs) {
		try {
			type20 = rs.getString("type20") ; if (type20 == null) type20="";
			if (type20.contains("`")) type20 = type20.replace("`", "'");
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editType21(ResultSet rs) {
		try {
			type21 = rs.getString("type21") ; if (type21 == null) type21="";
			if (type21.contains("`")) type21 = type21.replace("`", "'");
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	static void editType22(ResultSet rs) {
		try {
			type22 = rs.getString("type22") ; if (type22 == null) type22="";
			if (type22.contains("`")) type22 = type22.replace("`", "'");
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}

	public static void editATS(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					editHeader2(rs);
					io = rs.getString("io"); if (io == null) io="";
					manual_ats = rs.getString("freetext"); if (manual_ats == null) manual_ats="";
					if (manual_ats.contains("`")) manual_ats = manual_ats.replace("`", "'");
					editType3(rs);
					editType5(rs);
					editType7(rs);
					editType8(rs);
					editType9(rs);
					editType10(rs);
					editType13(rs);
					editType14(rs);
					editType15(rs);
					editType16(rs);
					editType17(rs);
					editType18(rs,"ATS");
					editType19(rs);
					editType20(rs);
					editType21(rs);
					editType22(rs);
					space_reserved	= rs.getString("space_reserved"); if (space_reserved == null) { space_reserved = ""; }
				}
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public static void editATSFree(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					editHeader2(rs);
					manual_ats = rs.getString("manual_ats"); if (manual_ats == null) manual_ats="";
					if (manual_ats.contains("`")) manual_ats = manual_ats.replace("`", "'");
				}
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public static void editALR(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					editHeader2(rs);
					io = rs.getString("io"); if (io == null) io="";
					editType3(rs);
					editType5(rs);
					editType7(rs);
					editType8(rs);
					editType9(rs);
					editType10(rs);
					editType13(rs);
					editType15(rs);
					editType16(rs);
					editType18(rs,"ALR");
					editType19(rs);
					editType20(rs);
				}
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	 
	public static void editRCF(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					editHeader2(rs);
					io = rs.getString("io"); if (io == null) io="";
					editType3(rs);
					editType7(rs);
					editType21(rs);
				 }	
			 }
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	 
	public static void editFPL(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					editHeader2(rs);
					io = rs.getString("io"); if (io == null) io="";
					editType3(rs);
					editType7(rs);
					editType8(rs);
					editType9(rs);
					editType10(rs);
					editType13(rs);
					editType15(rs);
					editType16(rs);
					editType18(rs,"FPL");
					editType19(rs);
					space_reserved	= rs.getString("space_reserved"); if (space_reserved == null) { space_reserved = ""; }
				}
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public static void editDLA(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					editHeader2(rs);
					io = rs.getString("io"); if (io == null) io="";
					editType3(rs);
					editType7(rs);
					editType13(rs);
					editType16(rs);
					editType18(rs, "DLA");
				 }	
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public static void editCHG(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					editHeader2(rs);
					io = rs.getString("io"); if (io == null) io="";
					editType3(rs);
					editType7(rs);
					editType13(rs);
					editType16(rs);
					editType18(rs, "CHG");
					editType22(rs);
				 }	
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public static void editCNL(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					editHeader2(rs);
					io = rs.getString("io"); if (io == null) io="";
					editType3(rs);
					editType7(rs);
					editType13(rs);
					editType16(rs);
					editType18(rs, "CNL");
				 }	
			 }
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public static void editDEP(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					editHeader2(rs);
					io = rs.getString("io"); if (io == null) io="";
					editType3(rs);
					editType7(rs);
					editType13(rs);
					editType16(rs);
					editType18(rs, "DEP");
				 }	
			 }
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public static void editARR(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				 while (rs.next()) {
					 editHeader2(rs);
					 io = rs.getString("io"); if (io == null) io="";
					 editType3(rs);
					 editType7(rs);
					 editType13(rs);
					 editType17(rs);
				 }
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	 
	public static void editCDN(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					editHeader2(rs);
					io = rs.getString("io"); if (io == null) io="";
					editType3(rs);
					editType7(rs);
					editType13(rs);
					editType16(rs);
					editType22(rs);
				 }	
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	 
	public static void editCPL(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					editHeader2(rs);
					io = rs.getString("io"); if (io == null) io="";
					editType3(rs);
					editType7(rs);
					editType8(rs);
					editType9(rs);
					editType10(rs);
					editType13(rs);
					editType14(rs);
					editType15(rs);
					editType16(rs);
					editType18(rs,"CPL");
				 }		
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}

	public static void editEST(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					editHeader2(rs);
					io = rs.getString("io"); if (io == null) io="";
					editType3(rs);
					editType7(rs);
					editType13(rs);
					editType14(rs);
					editType16(rs);
				 }
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public static void editACP(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				 int updateCount=stmt.getUpdateCount();
				 System.out.println(updateCount);
			 } else {
				 ResultSet rs=stmt.getResultSet();
				 while (rs.next()) {
					 editHeader2(rs);
					 io = rs.getString("io"); if (io == null) io="";
					 editType3(rs);
					 editType7(rs);
					 editType13(rs);
					 editType16(rs);
				 }	
			 }
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	 
	public static void editLAM(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					editHeader2(rs);
					io = rs.getString("io"); if (io == null) io="";
					editType3(rs);
				 }
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public static void editRQP(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					editHeader2(rs);
					io = rs.getString("io"); if (io == null) io="";
					editType3(rs);
					editType7(rs);
					editType13(rs);
					editType16(rs);
					editType18(rs, "RQP");
				 }	
			 }
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public static void editRQS(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					editHeader2(rs);
					io = rs.getString("io"); if (io == null) io="";
					editType3(rs);
					editType7(rs);
					editType13(rs);
					editType16(rs);
					editType18(rs, "RQS");
				 }	
			 }
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}

	public static void editSPL(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount=stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs=stmt.getResultSet();
				while (rs.next()) {
					editHeader2(rs);
					io = rs.getString("io"); if (io == null) io="";
					editType3(rs);
					editType7(rs);
					editType13(rs);
					editType16(rs);
					editType18(rs,"SPL");
					editType19(rs);
				}		
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public static void editEntryRoute(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount = stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs = stmt.getResultSet();
				while (rs.next()) {
					depER = rs.getString("type13a"); if (depER == null) depER = "";		
					destER = rs.getString("type16a"); if (destER == null) destER = "";	
					routeER = rs.getString("type15c");	if (routeER == null) routeER = "";			
					//addressER = rs.getString("address"); if (addressER == null) addressER = ""; 					
					//kalo pake dest1-21
					des1=rs.getString("destination1"); if (des1 == null) des1="";
					des2=rs.getString("destination2"); if (des2 == null) des2="";
					des3=rs.getString("destination3"); if (des3 == null) des3="";
					des4=rs.getString("destination4"); if (des4 == null) des4="";
					des5=rs.getString("destination5"); if (des5 == null) des5="";
					des6=rs.getString("destination6"); if (des6 == null) des6="";
					des7=rs.getString("destination7"); if (des7 == null) des7="";
					des8=rs.getString("destination8"); if (des8 == null) des8="";
					des9=rs.getString("destination9"); if (des9 == null) des9="";
					des10=rs.getString("destination10"); if (des10 == null) des10="";
					des11=rs.getString("destination11"); if (des11 == null) des11="";
					des12=rs.getString("destination12"); if (des12 == null) des12="";
					des13=rs.getString("destination13"); if (des13 == null) des13="";
					des14=rs.getString("destination14"); if (des14 == null) des14="";
					des15=rs.getString("destination15"); if (des15 == null) des15="";
					des16=rs.getString("destination16"); if (des16 == null) des16="";
					des17=rs.getString("destination17"); if (des17 == null) des17="";
					des18=rs.getString("destination18"); if (des18 == null) des18="";
					des19=rs.getString("destination19"); if (des19 == null) des19="";
					des20=rs.getString("destination20"); if (des20 == null) des20="";
					des21=rs.getString("destination21"); if (des21 == null) des21="";
				}
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}

	public static void editLogin(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount = stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs = stmt.getResultSet();
				while (rs.next()) {
					username = rs.getString("user_login"); if (username == null) { username=""; }
					password = rs.getString("password_login"); if (password == null) { password=""; }
					repass = rs.getString("re_type_password"); if (repass == null) { repass=""; }
					name = rs.getString("name"); if (name == null) { name=""; }
					level_user = rs.getString("level_user"); if (level_user == null) { level_user=""; }
					origin_user = rs.getString("origin_user"); if (origin_user == null) { origin_user=""; }
				}
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public static void editLocInd(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount = stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs = stmt.getResultSet();
				while (rs.next()) {
					indicator = rs.getString("indicator"); if (indicator == null) { indicator=""; }
					location_ind = rs.getString("location"); if (location_ind == null) { location_ind=""; }
				}
				rs.close();
				stmt.close();
				connClose();
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public static void editType9(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount = stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs = stmt.getResultSet();
				while (rs.next()) {
					type9b = rs.getString("type9b"); if (type9b == null) { type9b=""; }
					type9c = rs.getString("type9c"); if (type9c == null) { type9c=""; }
				}
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public static void editReg(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount = stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs = stmt.getResultSet();
				while (rs.next()) {
					type9b = rs.getString("type9b"); if (type9b == null) { type9b=""; }
					type18 = rs.getString("type18"); if (type18 == null) { type18=""; }
					type10a = rs.getString("type10a"); if (type10a == null) { type10a=""; }
					type10b = rs.getString("type10b"); if (type10b == null) { type10b=""; }
					sOpr = rs.getString("type18Opr"); if (sOpr == null) { sOpr=""; }
					sPbn = rs.getString("type18Pbn"); if (sPbn == null) { sPbn=""; }
					sSel = rs.getString("type18Sel"); if (sSel == null) { sSel=""; }
				}
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public static void editQueue(Statement stmt,boolean hasilExe) {
		try {
			if (!hasilExe) {
				int updateCount = stmt.getUpdateCount();
				System.out.println(updateCount);
			} else {
				ResultSet rs = stmt.getResultSet();
				while (rs.next()) {
					queuedata = rs.getString("data"); if (queuedata == null) { queuedata=""; }
				}
			}
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		}
	}
	
	public static String getPriority() { return priority; }
	public static String getFilingTime() { return filingTime; }
	public static String getSendAt() { return sendAt; }
	public static String getFiled_by() { return filed_by; }
	public static String getOriginator() { return originator; }
	public static String getOriRef() { return ori_ref; }
	public static String getIO() { return io; }
	//queue
	public static String getQueueData() { return queuedata; }
	//freetext
	public static String getManual_ats() { return manual_ats; }
	//type3
	public static String get3a() { return type3aATS; }
	public static String get3b() { return type3bATS; }
	public static String get3c() { return type3cATS; }
	//type5
	public static String get5a() { return type5a; }
	public static String get5b() { return type5b; }
	public static String get5c() { return type5c; }
	//type7
	public static String get7a() { return type7a; }
	public static String get7b() { return type7b; }
	public static String get7c() { return type7c; }
	//type8
	public static String get8a() { return type8a; }
	public static String get8b() { return type8b; }
	//type9
	public static String get9a() { return type9a; }
	public static String get9b() { return type9b; }
	public static String get9c() { return type9c; }
	//type10
	public static String get10a() { return type10a; }
	public static String get10b() { return type10b; }
	//type13
	public static String get13a() { return type13a; }
	public static String get13b() { return type13b; }
	//type14
	public static String get14a() { return type14a; }
	public static String get14b() { return type14b; }
	public static String get14c() { return type14c; }
	public static String get14d() { return type14d; }
	public static String get14e() { return type14e; }
	//type15
	public static String get15a() { return type15a; }
	public static String get15b() { return type15b; }
	public static String get15c() { return type15c; }
	//type16
	public static String get16a() { return type16a; }
	public static String get16b() { return type16b; }
	public static String get16c() { return type16c; }
	public static String get16c2nd() { return type16c2nd; }
	//type17
	public static String get17a() { return type17a; }
	public static String get17b() { return type17b; }
	public static String get17c() { return type17c; }
	//type18
	public static String get18() { return type18; }
	public static String get18a() { if (type18a.equals("")) type18a="REG/0"; return type18a; }
	public static String get18b() { if (type18b.equals("")) type18b="DOF/0"; return type18b; }
	public static String get18Baru() { return type18Baru; }
	//new
	public static String get18Sts() { return sSts; }
	public static String get18Pbn() { return sPbn; }
	public static String get18Nav() { return sNav; }
	
	public static String get18Com() { return sCom; }
	public static String get18Dat() { return sDat; }
	public static String get18Sur() { return sSur; }
	
	public static String get18Dep() { return sDep; }
	public static String get18Dest() { return sDest; }
	public static String get18Reg() { return sReg; }
	
	public static String get18Dof() { return sDof; }
	public static String get18Eet() { return sEet; }
	public static String get18Sel() { return sSel; }
	
	public static String get18Typ() { return sTyp; }
	public static String get18Code() { return sCode; }
	public static String get18Dle() { return sDle; }
	
	public static String get18Opr() { return sOpr; }
	public static String get18Orgn() { return sOrgn; }
	public static String get18Per() { return sPer; }
	
	public static String get18Altn() { return sAltn; }
	public static String get18Ralt() { return sRalt; }
	public static String get18Talt() { return sTalt; }
	
	public static String get18Rif() { return sRif; }
	public static String get18Rmk() { return sRmk; }
	
	//type19
	public static String get19a() { return type19a; }
	public static String get19b() { return type19b; }
	//cUHF,cVHF,cELT
	public static String get19cUHF() { return type19cUHF; }
	public static String get19cVHF() { return type19cVHF; }
	public static String get19cELT() { return type19cELT; }
	//dSPDMJ
	public static String get19dS() { return type19dS; }
	public static String get19dP() { return type19dP; }
	public static String get19dD() { return type19dD; }
	public static String get19dM() { return type19dM; }
	public static String get19dJ() { return type19dJ; }
	//eJLFUV
	public static String get19eJ() { return type19eJ; }
	public static String get19eL() { return type19eL; }
	public static String get19eF() { return type19eF; }
	public static String get19eU() { return type19eU; }
	public static String get19eV() { return type19eV; }
	//fD,fNumber,fCapacity,fCover,fColour
	public static String get19fD() { return type19fD; }
	public static String get19fNum() { return type19f_number; }
	public static String get19fCap() { return type19f_capacity; }
	public static String get19fCov() { return type19f_cover; }
	public static String get19fCol() { return type19f_colour; }
	//Aircraft,Remarks,Pilot
	public static String get19g() { return type19g; }
	public static String get19hN() { return type19hN; }
	public static String get19Rem() { return type19Remarks; }
	public static String get19i() { return type19i; }
	//type20
	public static String get20() { return type20; }
	//type21
	public static String get21() { return type21; }
	//percobaan
	public static String get21a() { return type21a; }
	public static String get21b() { return type21b; }
	public static String get21c() { return type21c; }
	public static String get21d() { return type21d; }
	public static String get21ef() { return type21ef; }
	//type22
	public static String get22() { return type22; }
	//space reserved
	public static String getSpace() { return space_reserved; }
	//tdm
	public static String getTrackname() { return track_name; }
	public static String getTrackid() { return track_id; }
	public static String getTrackdate() { return track_date; }
	public static String getTrackstatus() { return track_status; }
	public static String getTrackact() { return track_act; }
	public static String getTrackexp() { return track_exp; }
	public static String getTrackwaypoint() { return track_waypoint; }
	public static String getTracklevel() { return track_level; }
	public static String getTrackrts() { return track_rts; }
	public static String getTrackrmk() { return track_rmk; }
	//entry route
	public static String getDepER() { return depER; }
	public static String getDestER() { return destER; }
	public static String getRouteER() { return routeER; }
	//public static String getAddressER() { return addressER; }
	//kalo pake dest1-21
	public static String getDes1() { return des1; }
	public static String getDes2() { return des2; }
	public static String getDes3() { return des3; }
	public static String getDes4() { return des4; }
	public static String getDes5() { return des5; }
	public static String getDes6() { return des6; }
	public static String getDes7() { return des7; }
	
	public static String getDes8() { return des8; }
	public static String getDes9() { return des9; }
	public static String getDes10() { return des10; }
	public static String getDes11() { return des11; }
	public static String getDes12() { return des12; }
	public static String getDes13() { return des13; }
	public static String getDes14() { return des14; }
	
	public static String getDes15() { return des15; }
	public static String getDes16() { return des16; }
	public static String getDes17() { return des17; }
	public static String getDes18() { return des18; }
	public static String getDes19() { return des19; }
	public static String getDes20() { return des20; }
	public static String getDes21() { return des21; }
	public static String getBell() { return bell; }
	
	public static String getUsername() { return username; }
	public static String getPassword() { return password; }
	public static String getRepass() { return repass; }
	public static String getName() { return name; }
	public static String getLevelUser() { return level_user; }
	public static String getOriginUser() { return origin_user; }
	
	public static String getIndicator() { return indicator; }
	public static String getLocationInd() { return location_ind; }
}
