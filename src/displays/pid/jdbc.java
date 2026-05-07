package displays.pid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;

public class jdbc {
    private static String key;
    private static String[] tmp3a = new String[1000];
    private static String contentStr;
    private static String sLastID[] = new String[12];
    
    public static void readInputFile(String path) {
        contentStr = "";
        String lineStr = "";
            try {
                    BufferedReader bf = new BufferedReader(
                            new InputStreamReader(new FileInputStream(path)));

                    lineStr = bf.readLine();

                    while (lineStr != null) {
                        contentStr += lineStr + "\n";
                        lineStr = bf.readLine();
                    }
                    bf.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void parsing() {
        String tmp;
        String[] tmp1 = new String[100];
        String[] tmp2 = new String[100];
        String[] data = new String[100];
        String[] sub = new String[100];

        sub = contentStr.split("\n");
        for (int x = 0; x < sub.length; x++) {
            tmp = sub[x];
//            System.out.println("data"+tmp);
            if (tmp.indexOf("=") != -1) {
                data = tmp.split("=");
                tmp1[x] = data[0];
                tmp2[x] = data[1];

                if (tmp1[x].compareTo("Url") == 0) {
                    tmp3a[0] = tmp2[x];
                   //System.out.println("tmp3a[0]"+tmp3a[0]);
                } else if (tmp1[x].compareTo("User") == 0) {
                    tmp3a[1] = tmp2[x];
                   // System.out.println("tmp3a[1]"+tmp3a[1]);
                } else if (tmp1[x].compareTo("Pass") == 0) {
                    tmp3a[2] = tmp2[x];
                   // System.out.println("tmp3a[2]"+tmp3a[2]);
                }
            }
        }
    }

    public static void getfl() {
        System.out.println("file:"+DisplayMain.g_sURL);
        readInputFile(DisplayMain.g_sURL);
        parsing();
     }

    public static String getmsgpid(String selects) {
        String resmsg="";
        try {
            System.out.println("Connecting To Database Inb..."+getUrl()+":"+getUser()+":"+getPass());
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();

            System.out.println("query : " + selects);
            ResultSet resultSet = stmt1.executeQuery(selects);
            resmsg="";
            if (resultSet.next()) resmsg+=resultSet.getString(2);
            resultSet.close();
			stmt1.close();
			conn1.close();
            System.out.println("[ -- EXECUTE SUCCESS -- ]");
       } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return(resmsg);
    }

    public static String getidpid(String selects) {
        String resmsg="";
        try {
            System.out.println("Connecting To Database Inb...");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();

            System.out.println("query : " + selects);
            ResultSet resultSet = stmt1.executeQuery(selects);
            if(resultSet.next()) {
            	System.out.println(resultSet.getString(1));
            	resmsg=resultSet.getString(1);
            }
            resultSet.close();
			stmt1.close();
			conn1.close();
           System.out.println("[ -- EXECUTE SUCCESS -- ]");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return(resmsg);
    }

    public static String getPID(String selects, Table table) {
    	String sRet="";
        try {
            System.out.println("Connecting To Database Inb...");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();

            System.out.println("query : " + selects);
            table.removeAll();
            ResultSet resultSet = stmt1.executeQuery(selects);
            while(resultSet.next()) {
           		TableItem item = new TableItem(table, SWT.NONE);
           		int i_cnt=0;
   	    		for (int iLoop=0;iLoop<7;iLoop++) {
   	    			if ((iLoop!=0) || (iLoop!=3)) {
   	    				if (resultSet.getString(iLoop+1)!=null) {
       	    	    		///System.out.println("pid clmn:"+(iLoop+1)+" "+resultSet.getString(iLoop+1)+" icnt"+i_cnt);
   	    					item.setText(i_cnt++, resultSet.getString(iLoop+1));
   	    				}
   	    			}
   	    		}
            }
            resultSet.close();
			stmt1.close();
			conn1.close();
           System.out.println("[ -- EXECUTE SUCCESS -- ]");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sRet;
    }

    public static String getPIDcode(String selects) {
    	String sRet="";
        try {
            System.out.println("query : " + selects);
            System.out.println("Connecting To Database Inb...");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();

    		ResultSet resultSet = stmt1.executeQuery(selects);
            while(resultSet.next()) {
   				if (resultSet.getString(1)!=null) {
   					sRet=resultSet.getString(1);break;
   				}
            }
            resultSet.close();
			stmt1.close();
			conn1.close();
           System.out.println("[ -- EXECUTE SUCCESS -- ]");
        } catch (SQLException se) {
           se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sRet;
    }

    public static String getPIDtyp(String selects, Combo cmb) {
    	String sRet="";
        try {
            System.out.println("Connecting To Database Inb...");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();

            System.out.println("query : " + selects);
            cmb.removeAll();
    		cmb.add("");
    		ResultSet resultSet = stmt1.executeQuery(selects);
            while(resultSet.next()) {
   				if (resultSet.getString(1)!=null) {
   					cmb.add(resultSet.getString(1));
   				}
            }
            resultSet.close();
			stmt1.close();
			conn1.close();
            System.out.println("[ -- EXECUTE SUCCESS -- ]");
        } catch (SQLException se) {
           se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sRet;
    }

    public static void updtPID(String selects) {
        try {
            System.out.println("Connecting To Database Inb...");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();

            System.out.println("query : " + selects);
            stmt1.execute(selects);
			stmt1.close();
			conn1.close();
            System.out.println("[ -- EXECUTE SUCCESS -- ]");
       } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setBtnPID(String selects, Button btn[]) {
        try {
            System.out.println("Connecting To Database Inb...");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();

            System.out.println("queryBtn : " + selects);
            ResultSet resultSet = stmt1.executeQuery(selects);
            int cnt=0;
            while(resultSet.next()) {
            	if ((resultSet.getString(1)!=null) && (resultSet.getString(2)!=null)) {
            		cnt=Integer.parseInt(resultSet.getString(1));
            		if (cnt>0) {
            			String s_dt=new String(resultSet.getString(1));
            			String sBtnText = "";
                        System.out.println("res : " + resultSet.getString(2));
                		if (resultSet.getString(2).startsWith("BULLETIN_")) {
                			sBtnText = resultSet.getString(2).substring(0, 4) + ".";
                			sBtnText += resultSet.getString(2).substring(9, resultSet.getString(2).length());
                			//btn[cnt-1].setText(sBtnText);
                		}
                		else {
                			sBtnText = resultSet.getString(2);
                			//btn[cnt-1].setText(resultSet.getString(2));
                			///System.out.println("btn"+(cnt-1)+" data:"+btn[cnt-1].getData()+" text:"+btn[cnt-1].getText());
                		}
                		btn[cnt-1].setText(sBtnText);
            			btn[cnt-1].setData(s_dt);

            		}
            	}
            }
            resultSet.close();
			stmt1.close();
			conn1.close();
           System.out.println("[ -- EXECUTE SUCCESS -- ]");
        } catch (SQLException se) {
           se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setBtnPID(String selects, Button btn) {
        try {
            System.out.println("Connecting To Database Inb...");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();

            System.out.println("query : " + selects);
            ResultSet resultSet = stmt1.executeQuery(selects);
            if(resultSet.next()) {
            	if ((resultSet.getString(1)!=null) && (resultSet.getString(2)!=null)) {
           			String s_dt=new String(resultSet.getString(1));
           			String sBtnText = "";
            		if (resultSet.getString(2).startsWith("BULLETIN_")) {
            			sBtnText = resultSet.getString(2).substring(0, 4) + ". ";
            			sBtnText += resultSet.getString(2).substring(9, resultSet.getString(2).length());
            		}
            		else 
            			sBtnText = resultSet.getString(2);
               		btn.setData(s_dt);
           			btn.setText(sBtnText);
           			System.out.println("btn"+" data:"+btn.getData()+" text:"+btn.getText());
            	}
            }
            resultSet.close();
			stmt1.close();
			conn1.close();
            System.out.println("[ -- EXECUTE SUCCESS -- ]");
        } catch (SQLException se) {
           se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int inbox(String selects,java.util.List playersInbATS1_S,int id){
		int cnt=0;
        try {
            System.out.println("Connecting To Database Inb...");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();

            System.out.println("query : " + selects);
			ResultSet rs;
			rs = stmt1.executeQuery(selects);
			String p1="";String p2="";String p3="";String p4="";
			playersInbATS1_S.clear();
			while (rs.next()) {
	    		if (rs.getString("id")!=null) p1=rs.getString("id");
	    		if (rs.getString("message")!=null) {
	    			p2=rs.getString("message")+"...";
	    			p2=p2.replaceAll("`", "'");
	    		}
	    		if (rs.getString("message1")!=null) {
	    			p3=rs.getString("message1");
	    			p3=p3.replaceAll("`", "'");
	    		}
	    		if (rs.getString("tgl")!=null) {
	    			p4=rs.getString("tgl");
	    			//p4=p4.substring(8, p4.length()-2);
	    		}
	    		System.out.println(p1+"|"+p2+"|"+p3+"|"+p4);
				playersInbATS1_S.add(new PlayerInbATS1_S(p1,p2,p3,p4));
				if (cnt==0) sLastID[id]=p1;
				cnt++;
				if (cnt>=DisplayMain.g_maxrowinfo) break;
			}
			rs.close();
			stmt1.close();
			conn1.close();
	        System.out.println("[ -- EXECUTE SUCCESS -- ]");
        } catch (SQLException se) {
           se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cnt;
    }
    
//    public static void oneByone(String selects,java.util.List playersInbATS1_S,PlayerComparatorInbATS1_S comparatorInbATS1_S,Table table, Label label, int id){ 
//        try {
//        	if (sLastID[id]==null) sLastID[id]="0";
//	            System.out.println("Connecting To Database Inb...");
//	            String driver = "com.mysql.jdbc.Driver";
//	            Class.forName(driver);
//	            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
//	            Statement stmt1 = conn1.createStatement();
//	
//				ResultSet rs;
//	            System.out.println("query : " + selects);
//				rs = stmt1.executeQuery(selects);
//				String p1="";String p2="";String p3="";String p4="";
//				if (rs.next()) {
//		    		if (rs.getString("id")!=null) p1=rs.getString("id");
//		    		if (rs.getString("message")!=null) {
//		    			p2=rs.getString("message")+"...";
//		    			p2=p2.replaceAll("`", "'");
//		    		}
//		    		if (rs.getString("message1")!=null) {
//		    			p3=rs.getString("message1");
//		    			p3=p3.replaceAll("`", "'");
//		    		}
//		    		if (rs.getString("tgl")!=null) {
//		    			p4=rs.getString("tgl");
//		    			//p4=p4.substring(8, p4.length()-2);
//		    		}
//		    		System.out.println(p1+"|"+p2+"|"+p3+"|"+p4);
//	
//		    		System.out.println("LastID:"+sLastID[id]+sLastID[id].length());
//			    	System.out.println("newID :"+p1+"-"+p1.length());
//		    		if (sLastID[id].compareTo(p1)!=0){ 
//						///set blue color
//			    		System.out.println("setColorBlue");
//						label.setText(new DateUtils().now().substring(11));
//						label.setForeground(new Color(label.getShell().getDisplay(),0,64,255));
//					    sLastID[id]=p1;
//					    if ((playersInbATS1_S!=null) && (table!=null) && (!table.isDisposed()) ){
//							playersInbATS1_S.add(new PlayerInbATS1_S(p1,p2,p3,p4));
//							comparatorInbATS1_S.setColumn(PlayerComparatorInbATS1_S.TGL);
//					       	IOSR.fillTable(table,playersInbATS1_S,comparatorInbATS1_S);
//					    	System.out.println("sorting...");
//					       	TableColumn currentColumn = table.getColumn(3);
//				        	table.setSortColumn(currentColumn);
//					        IOSR.dir = SWT.DOWN;
//							//System.out.println("added");
//							//IOSR.ckrow(table);
//					        System.out.println("[ -- EXECUTE SUCCESS -- ]");
//				        }
//		    		}
//				}
//	            rs.close();
//				stmt1.close();
//				conn1.close();
//        } catch (SQLException se) {
//           se.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    static int outboxsent(String selects,java.util.List playersOutATS1_S,int id){
		int cnt=0;
        try {
            System.out.println("Connecting To Database Inb...");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();

			ResultSet rs;
            System.out.println("query : " + selects);
			rs = stmt1.executeQuery(selects);
			String p1="",p2="",p3="",p4="",p5="",p6="",p7="",p8="",p9="",p11="";
			while (rs.next()) {
	    		if (rs.getString(1)!=null) p11=rs.getString(1);
	    		if (rs.getString(3)!=null) p1=rs.getString(3);
	    		if (rs.getString(4)!=null) p2=rs.getString(4);
	    		if (rs.getString(5)!=null) p3=rs.getString(5);
	    		if (rs.getString(8)!=null) p4=rs.getString(8);
	    		if (rs.getString(9)!=null) {
		    		if (rs.getString(9).compareTo("WINS WARNING")==0) p5="WS WRNG";
		    		else p5=rs.getString(9);
	    		}
	    		if (rs.getString(11)!=null) p6=rs.getString(11);
	    		if (rs.getString(12)!=null) p7=rs.getString(12);
	    		if (rs.getString("tbl_name")!=null) p8=rs.getString("tbl_name");
	    		if (rs.getString("tgl")!=null) p9=rs.getString("tgl").substring(0, 19);
	    		System.out.println(p11+" ^ "+p8+" ^ "+p9);
	    		
				playersOutATS1_S.add(new PlayerOutATS1_S(p1,p2,p3,p4,p5,p6,p7,p11,p8,p9));
				if (cnt==0) sLastID[id]=p11;
				cnt++;
				if (cnt>=DisplayMain.g_maxrowinfo) break;
			}
            rs.close();
			stmt1.close();
			conn1.close();
	        System.out.println("[ -- EXECUTE SUCCESS -- ]");
        } catch (SQLException se) {
           se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return cnt;
    }
    
//    static void oneByone(String selects,java.util.List playersOutATS1_S,PlayerComparatorOutATS1_S comparatorOutATS1_S,Table table, Label label, int id){
//        try {
//        	if (sLastID[id]==null) sLastID[id]="0";
//            System.out.println("Connecting To Database Inb...");
//            String driver = "com.mysql.jdbc.Driver";
//            Class.forName(driver);
//            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
//            Statement stmt1 = conn1.createStatement();
//
//			ResultSet rs;
//            System.out.println("query : " + selects);
//			rs = stmt1.executeQuery(selects);
//			String p1="",p2="",p3="",p4="",p5="",p6="",p7="",p11="",p8="",p9="";
//			if (rs.next()) {
//	    		if (rs.getString(1)!=null) p11=rs.getString(1);
//	    		if (rs.getString(3)!=null) p1=rs.getString(3);
//	    		if (rs.getString(4)!=null) p2=rs.getString(4);
//	    		if (rs.getString(7)!=null) p3=rs.getString(5);
//	    		if (rs.getString(8)!=null) p4=rs.getString(8);
//	    		if (rs.getString(9)!=null) {
//		    		if (rs.getString(9).compareTo("WINS WARNING")==0) p5="WS WRNG";
//		    		else p5=rs.getString(9);
//	    		}
//	    		if (rs.getString(11)!=null) p6=rs.getString(11);
//	    		if (rs.getString(12)!=null) p7=rs.getString(12);
//	    		if (rs.getString("tbl_name")!=null) p8=rs.getString("tbl_name");
//	    		if (rs.getString("tgl")!=null) p9=rs.getString("tgl").substring(0, 19);
//		    	System.out.println("LastID:"+sLastID[id]+sLastID[id].length());
//		    	System.out.println("newID :"+p11+p11.length());
//	    		if (sLastID[id].compareTo(p11)!=0) { 
//					///set blue color
//		    		System.out.println("setColorBlue");
//					label.setText(new DateUtils().now().substring(11));
//					label.setForeground(new Color(label.getShell().getDisplay(),0,64,255));
//	    			sLastID[id]=p11;
//				    if (playersOutATS1_S!=null) {
//						playersOutATS1_S.add(new PlayerOutATS1_S(p1,p2,p3,p4,p5,p6,p7,p11,p8,p9));
//						comparatorOutATS1_S.setColumn(PlayerComparatorOutATS1_S.DTM);
//						IOSR.fillTable(table,playersOutATS1_S,comparatorOutATS1_S);
//			    		System.out.println("sorting...");
//			        	TableColumn currentColumn = table.getColumn(2);
//		        		table.setSortColumn(currentColumn);
//		        		IOSR.dir = SWT.DOWN;
//						System.out.println("added");
//						IOSR.ckrow(table);
//						label.setText(new DateUtils().now().substring(11));
//				        System.out.println("[ -- EXECUTE SUCCESS -- ]");
//				    }
//	    		}
//			}
//            rs.close();
//			stmt1.close();
//			conn1.close();
//        } catch (SQLException se) {
//           se.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    static int reject(String selects,java.util.List playersRejATS1_S,int id){
		int cnt=0;
        try {
            System.out.println("Connecting To Database Inb...");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();

			ResultSet rs;
            System.out.println("query : " + selects);
			rs = stmt1.executeQuery(selects);
			String p1="",p2="",p3="",p4="",p5="",p6="",p7="",p11="";
			while (rs.next()) {
	    		if (rs.getString(1)!=null) p11=rs.getString(1);
	    		if (rs.getString(2)!=null) p1=rs.getString(2);
	    		if (rs.getString(26)!=null) p2=rs.getString(26);
	    		if (rs.getString(28)!=null) p3=rs.getString(28);
	    		if (rs.getString(27)!=null) p4=rs.getString(27);
	    		if (rs.getString(31)!=null) p5=rs.getString(31).substring(0,19);
	    		if (rs.getString(33)!=null) p6=rs.getString(33);
	    		if (id==9) p7="reject_message_ats"+Dtgram.gtMonth();
	    		else if (id==10) p7="reject_message_notam"+Dtgram.gtMonth();
	    		else if (id==11) p7="reject_message"+Dtgram.gtMonth();
				playersRejATS1_S.add(new PlayerRejATS1_S(p6,p1,p2,p4,p3,p5,p11,p7));
				if (cnt==0) sLastID[id]=p11;
				cnt++;
				if (cnt>=DisplayMain.g_maxrowinfo) break;
			}
            rs.close();
			stmt1.close();
			conn1.close();
	        System.out.println("[ -- EXECUTE SUCCESS -- ]");
        } catch (SQLException se) {
           se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cnt;
    }
    
    static void oenByone(String selects,java.util.List playersRejATS1_S,PlayerComparatorRejATS1_S comparatorRejATS1_S,Table table, Label label,int id){
        try {
        	if (sLastID[id]==null) sLastID[id]="0";
            System.out.println("Connecting To Database Inb...");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();

			ResultSet rs;
            System.out.println("query : " + selects);
			rs = stmt1.executeQuery(selects);
			String p1="",p2="",p3="",p4="",p5="",p6="",p7="",p8="",p11="";
			if (rs.next()) {
	    		if (rs.getString(1)!=null) p11=rs.getString(1);
	    		if (rs.getString(2)!=null) p1=rs.getString(2);
	    		if (rs.getString(26)!=null) p2=rs.getString(26);
	    		if (rs.getString(28)!=null) p3=rs.getString(28);
	    		if (rs.getString(27)!=null) p4=rs.getString(27);
	    		if (rs.getString(31)!=null) p5=rs.getString(31).substring(0,19);
	    		if (rs.getString(33)!=null) p6=rs.getString(33);
		    	System.out.println("LastID:"+sLastID[id]+sLastID[id].length());
		    	System.out.println("newID :"+p11+p11.length());
	    		if (sLastID[id].compareTo(p11)!=0) { 
					///set blue color
		    		System.out.println("setColorRed");
					label.setText(new DateUtils().now().substring(11));
					label.setForeground(new Color(label.getShell().getDisplay(),255,0,31));
	    			sLastID[id]=p11;
				    if (playersRejATS1_S!=null) {
			    		if (id==9) p8="reject_message_ats";
			    		else if (id==10) p8="reject_message_notam";
			    		else if (id==11) p8="reject_message";
			    		playersRejATS1_S.add(new PlayerRejATS1_S(p6,p1,p2,p4,p3,p5,p11,p8));
						comparatorRejATS1_S.setColumn(PlayerComparatorRejATS1_S.DTM);
						//comparatorRejATS1_S.reverseDirection();
						IOSR.fillTable(table,playersRejATS1_S,comparatorRejATS1_S);
			    		System.out.println("sorting...");
			        	TableColumn currentColumn = table.getColumn(5);
		        		table.setSortColumn(currentColumn);
		        		IOSR.dir = SWT.DOWN;
						System.out.println("added");
						IOSR.ckrow(table);
				        System.out.println("[ -- EXECUTE SUCCESS -- ]");
				    }
	    		}
	    	}
            rs.close();
			stmt1.close();
			conn1.close();
        } catch (SQLException se) {
           se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	static boolean getPassword(String sPassword){
		boolean flg=false;
	    try {
           System.out.println("Connecting To Database Inb...");
           String driver = "com.mysql.jdbc.Driver";
           Class.forName(driver);
           Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
           Statement stmt1 = conn1.createStatement();

   		   ResultSet rs = stmt1.executeQuery("select * from login_tabel");
   		   while (rs.next()) {
   				if ((rs.getString("password_login")!=null) && (rs.getString("level_user")!=null)) {
   					//System.out.println(sPassword+" "+"level:"+rs.getString("level_user")+" password:"+rs.getString("password_login"));
   					if ((rs.getString("level_user").compareTo("ADMIN")==0) && (rs.getString("password_login").compareTo(sPassword)==0)) {flg=true;break;}
   				}
   		   }
   		   rs.close();
   		   stmt1.close();
   		   conn1.close();
   	    } catch (SQLException se) {
   	        se.printStackTrace();
   	    } catch (Exception e) {
   	        e.printStackTrace();
	   	}
		return flg;
	}
	
    static void getExpiredNotam(String sQuery,Table table){
        try {
            System.out.println("Connecting To Database Exp NOTAM...");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();
            Statement stmt2 = conn1.createStatement();

            String sQueryNtm = "show tables from ais like 'notam_multi%'";
            ResultSet rs2;
            System.out.println("query : " + sQueryNtm);
			rs2 = stmt1.executeQuery(sQueryNtm);
			String sTempQuery =  sQuery;
			while (rs2.next()) {
				if (rs2.getString(1)!=null){
					sQuery=sTempQuery;
					sQuery=sQuery.replace("notam_multi", rs2.getString(1));
		            System.out.println("query : " + sQuery);
		            ResultSet rs = stmt2.executeQuery(sQuery);
					String p1="",p2="",p3="",p4="",p5="",p6="",p7="",p8="",p9="",p10="",p11="",p12="",p13="",p14="",p15="",p16="",p17="";
					p17=rs2.getString(1);
					int cnt=0;
					while (rs.next()) {
			    		if (rs.getString("priority")!=null) p1=rs.getString("priority");
			    		if (rs.getString("filing_time")!=null) p2=rs.getString("filing_time");
			    		if (rs.getString("originator")!=null) p3=rs.getString("originator");
			    		if (rs.getString("ntm_id_serie")!=null) {p4=rs.getString("ntm_id_serie");}
			    		if (rs.getString("ntm_id_sequence")!=null) p5=rs.getString("ntm_id_sequence");
			    		if (rs.getString("ntm_id_years")!=null) p6=rs.getString("ntm_id_years");
			    		if (rs.getString("identifiers")!=null) p7=rs.getString("identifiers");
			    		if (rs.getString("ref_ntm_id_serie")!=null) p8=rs.getString("ref_ntm_id_serie");
			    		if (rs.getString("ref_ntm_id_sequence")!=null) p9=rs.getString("ref_ntm_id_sequence");
			    		if (rs.getString("ref_ntm_id_years")!=null) p10=rs.getString("ref_ntm_id_years");
			    		if (rs.getString("A")!=null) p11=rs.getString("A");
			    		if (rs.getString("B")!=null) p12=rs.getString("B");
			    		if (rs.getString("C")!=null) p13=rs.getString("C");
			    		if (rs.getString("id_notam_multi")!=null) p15=rs.getString("id_notam_multi");
			    		if (rs.getString("tgl")!=null) p16=rs.getString("tgl");
			    		//if (rs.getString("E")!=null) p14=rs.getString("E");
				    	TableItem item = new TableItem(table, SWT.NONE);
				    	int c = 0;
				    	item.setText(c++, p1);
				    	item.setText(c++, p2);
				    	item.setText(c++, p3);
				    	if ((p4.compareTo("")!=0) || (p5.compareTo("")!=0) || (p6.compareTo("")!=0))
				    		item.setText(c++, p4+p5+"/"+p6);
				    	else item.setText(c++, "");
				    	item.setText(c++, p7);
				    	if ((p8.compareTo("")!=0) || (p9.compareTo("")!=0) || (p10.compareTo("")!=0))
				    	   	item.setText(c++, p8+p9+"/"+p10);
				    	else item.setText(c++, "");
				    	item.setText(c++, p11);
				    	item.setText(c++, p12);
				    	item.setText(c++, p13);
				    	//item.setText(c++, p14);
				    	item.setText(c++, p15);
				    	item.setText(c++, p16);
				    	item.setText(c++, p17);
						cnt++;
						if (cnt>=DisplayMain.g_maxrowinfo) break;
					}
		            rs.close();
				}
			}
			rs2.close();
			
			stmt1.close();
			conn1.close();
	        System.out.println("[ -- EXECUTE SUCCESS -- ]");
        } catch (SQLException se) {
           se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String getFilingTimeRPL(String sQuery){
    	String sRet="";
        try {
            System.out.println("Connecting To Database Get Filing time RPL from Check status...");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();

			ResultSet rs;
            System.out.println("query : " + sQuery);
			rs = stmt1.executeQuery(sQuery);
			while (rs.next()) {
				if (rs.getString("filing_time")!=null) {
					sRet=rs.getString("filing_time");
					System.out.println("got:"+sRet);
					break;
				}
			}
            rs.close();
			stmt1.close();
			conn1.close();
	        System.out.println("[ -- EXECUTE SUCCESS -- ]");
        } catch (SQLException se) {
           se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sRet;
    }

    static void getExpiredFPL(String sQuery,Table table){
        try {
            System.out.println("Connecting To Database Exp FPL...");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();

			ResultSet rs;
            System.out.println("query : " + sQuery);
			rs = stmt1.executeQuery(sQuery);
			String p1="",p2="",p3="",p4="",p5="",p6="";
			int cnt=0;
			while (rs.next()) {
	    		if (rs.getString("priority")!=null) p1=rs.getString("priority");
	    		if (rs.getString("filing_time")!=null) p2=rs.getString("filing_time");
	    		if (rs.getString("originator")!=null) p3=rs.getString("originator");
	    		if (rs.getString("type7a")!=null) {p4=rs.getString("type7a");}
	    		if (rs.getString("tgl")!=null) p5=rs.getString("tgl");
	    		if (rs.getString("id_ats")!=null) p6=rs.getString("id_ats");
		    	TableItem item = new TableItem(table, SWT.NONE);
		    	int c = 0;
		    	item.setText(c++, p1);
		    	item.setText(c++, p2);
		    	item.setText(c++, p3);
		    	item.setText(c++, p4);
		    	item.setText(c++, p5);
		    	item.setText(c++, p6);
				cnt++;
				if (cnt>=DisplayMain.g_maxrowinfo) break;
			}
            rs.close();
			stmt1.close();
			conn1.close();
	        System.out.println("[ -- EXECUTE SUCCESS -- ]");
        } catch (SQLException se) {
           se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void getExpiredFPL1(String sQuery,Table table){
		Dtgram.getYearMonth();
        try {
            System.out.println("Connecting To Database Exp FPL...");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();

			ResultSet rs;
            System.out.println("query : " + sQuery);
			rs = stmt1.executeQuery(sQuery);
			String id="",type="",tgl="",fltm="";
			while (rs.next()) {
	    		if (rs.getString("id")!=null) id=rs.getString("id");
	    		if (rs.getString("type")!=null) type=rs.getString("type");
	    		if (rs.getString("tgl")!=null) tgl=rs.getString("tgl");
	    		if (rs.getString("filing_time")!=null) fltm=rs.getString("filing_time");
				String sQueryT = "SELECT * FROM ";
				if (type.compareTo("FPL")==0) {
					sQueryT+=Dtgram.sTable+" WHERE id_ats="+id;
					//sQueryT+=" AND st='0'";
				}
				if (type.compareTo("RPL")==0) {
					sQueryT+="rpl_content WHERE id_rpl_cont="+id;
					//sQueryT+=" AND status='expired'";
				}
				sQueryT+=" AND tgl='"+tgl+"'";
	    		
				int cnt=0;
				ResultSet rs1;
	            Statement stmt2 = conn1.createStatement();
				rs1 = stmt2.executeQuery(sQueryT);
				System.out.println("fltm:"+fltm+" "+sQueryT);
				if (rs1.next()) {
					String p1="",p2="",p3="",p4="",p5="",p6="",p7="";
					if (rs1.getString("priority")!=null) p1=rs1.getString("priority");
					p2=fltm;
					if (rs1.getString("originator")!=null) p3=rs1.getString("originator");
					if (rs1.getString("tgl")!=null) p5=rs1.getString("tgl");
					if (type.compareTo("RPL")==0) {
						if (rs1.getString("L_aircraft_id")!=null) {p4=rs1.getString("L_aircraft_id");}
						if (rs1.getString("id_rpl_cont")!=null) p6=rs1.getString("id_rpl_cont");
						p7="RPL";
					}
					else if (type.compareTo("FPL")==0) {
						if (rs1.getString("type7a")!=null) {p4=rs1.getString("type7a");}
						if (rs1.getString("id_ats")!=null) p6=rs1.getString("id_ats");
						p7="FPL";
					}
					TableItem item = new TableItem(table, SWT.NONE);
					int c = 0;
			    	item.setText(c++, p1);
			    	item.setText(c++, p2);
			    	item.setText(c++, p3);
			    	item.setText(c++, p4);
			    	item.setText(c++, p5);
			    	item.setText(c++, p6);
			    	item.setText(c++, p7);
					cnt++;
					if (cnt>=DisplayMain.g_maxrowinfo) break;
				}
	            rs1.close();
	            stmt2.close();
			}
            rs.close();
			stmt1.close();
			conn1.close();
	        System.out.println("[ -- EXECUTE SUCCESS -- ]");
        } catch (SQLException se) {
           se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static boolean getExpiredDEP1(String sQuery,Table table){
		boolean flg=false;
    	Dtgram.getYearMonth();
        try {
            System.out.println("Connecting To Database Exp FPL...");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();

			ResultSet rs;
            System.out.println("query : " + sQuery);
			rs = stmt1.executeQuery(sQuery);
			String id="",type="",tgl="",fltm="";
			while (rs.next()) {
	    		if (rs.getString("id")!=null) id=rs.getString("id");
	    		if (rs.getString("type")!=null) type=rs.getString("type");
	    		if (rs.getString("tgl")!=null) tgl=rs.getString("tgl");
	    		if (rs.getString("filing_time")!=null) fltm=rs.getString("filing_time");
				String sQueryT = "SELECT * FROM ";
				sQueryT+=Dtgram.sTable+" WHERE id_ats="+id;
				sQueryT+=" AND tgl='"+tgl+"'";
	    		
				int cnt=0;
				ResultSet rs1;
	            Statement stmt2 = conn1.createStatement();
				rs1 = stmt2.executeQuery(sQueryT);
				System.out.println("fltm:"+fltm+" "+sQueryT);
				if (rs1.next()) {
					String p1="",p2="",p3="",p4="",p5="",p6="",p7="";
					if (rs1.getString("priority")!=null) p1=rs1.getString("priority");
					p2=fltm;
					if (rs1.getString("originator")!=null) p3=rs1.getString("originator");
					if (rs1.getString("tgl")!=null) p5=rs1.getString("tgl");
					if (rs1.getString("type7a")!=null) {p4=rs1.getString("type7a");}
					if (rs1.getString("id_ats")!=null) p6=rs1.getString("id_ats");
					p7=type;
					TableItem item = new TableItem(table, SWT.NONE);
					int c = 0;
			    	item.setText(c++, p1);
			    	item.setText(c++, p2);
			    	item.setText(c++, p3);
			    	item.setText(c++, p4);
			    	item.setText(c++, p5);
			    	item.setText(c++, p6);
			    	item.setText(c++, p7);
					cnt++;
					if (cnt>=DisplayMain.g_maxrowinfo) break;
				}
	            rs1.close();
	            stmt2.close();
			}
            rs.close();
			stmt1.close();
			conn1.close();
			flg=true;
	        System.out.println("[ -- EXECUTE SUCCESS -- ]");
        } catch (SQLException se) {
           se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flg;
    }
    
    public static String insert(String selects) {
    	String sRet="";
        try {
            System.out.println("query : " + selects);
            System.out.println("Connecting To Database Inb...");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            Connection conn1 = DriverManager.getConnection(getUrl(), getUser(), getPass());
            Statement stmt1 = conn1.createStatement();

    		boolean b = stmt1.execute(selects);
            System.out.println("sQuery"+selects);
    		if (b) { 
    	       System.out.println("ok");
    		}
    		else System.out.println("failed");
			stmt1.close();
			conn1.close();
           System.out.println("[ -- EXECUTE SUCCESS -- ]");
        } catch (SQLException se) {
           se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sRet;
    }


    public static String getKey() {
        return (key);
    }

    public static String getUrl() {
        return (tmp3a[0]);
    }

    public static String getUser() {
        return (tmp3a[1]);
    }

    public static String getPass() {
        return (tmp3a[2]);
    }

}
