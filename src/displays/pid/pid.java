package displays.pid;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;


public class pid {
//	static Image image=new Image(shdisp.getDisplay(),"/aftn/nosound.gif");
//	static Image image1=new Image(shdisp.getDisplay(),"/aftn/sound.gif");
	static Button btn[]=new Button[21];
//	static Button btnbell;
//	static String g_sCurBtn="";
//	static DatagramSocket socket;
//	static Shell sqlshell[]=new Shell[13];
//	Shell shPIDSetup;
//	int ifalse[]={1,2,4,5,7,8,10,11};
//	int itrue[]={0,3,6,9};
//	String[] g_btnText = new String[]{"Messages","NOTAM Messages","METEO Messages","PID Setup"};
//	String[] g_shellText = new String[]{"Inbox ","Outbox ","Sent ","Reject "};
//	String[] g_btnText1 = new String[]{"Inbox","Outbox","Sent","Reject","PID Setup"};
//	String[] g_sQuery = new String[]{"SELECT * FROM incoming_msg ORDER BY tgl desc,id DESC",
//			"SELECT * FROM incoming_msg WHERE (table_name='reject_message_notam' OR table_name='notam_multi' OR table_name='notam_cl' OR table_name='notam_criteria' OR table_name='rqn' OR table_name='rql' OR table_name='ashtam' OR table_name='birdtam' OR table_name='snowtam') ORDER BY tgl DESC",
//			"SELECT * FROM incoming_msg WHERE (table_name='reject_message' OR table_name='air_message_free_text_meteo' OR table_name='meteo_metar' OR table_name='meteo_speci' OR table_name='meteo_sigmet' OR table_name='meteo_airmet' OR table_name='meteo_airep' OR table_name='meteo_tafor' OR table_name='meteo_synop' OR table_name='meteo_arfor' OR table_name='meteo_rofor' OR table_name='meteo_wins_war' OR table_name='meteo_wintem' OR table_name='vulcano_adv' OR table_name='volcanic_act' OR table_name='rqm' OR table_name='meteo_wsynop') ORDER BY tgl DESC",
//			"SELECT * FROM check_status WHERE (tbl_name='rpl_content' OR tbl_name='air_message' OR tbl_name='air_message_free_text') and (status='waiting' OR status='not sent') ORDER BY tgl DESC",
//			"SELECT * FROM check_status WHERE (tbl_name='notam_multi' OR tbl_name='notam_cl' OR tbl_name='notam_criteria' OR tbl_name='rqn' OR tbl_name='rql' OR tbl_name='ashtam' OR tbl_name='birdtam' OR tbl_name='snowtam') and (status='waiting' OR status='not sent') ORDER BY tgl DESC",
//			"SELECT * FROM check_status WHERE (tbl_name='air_message_free_text_meteo' OR tbl_name='meteo_metar' OR tbl_name='meteo_speci' OR tbl_name='meteo_sigmet' OR tbl_name='meteo_airmet' OR tbl_name='meteo_airep' OR tbl_name='meteo_tafor' OR tbl_name='meteo_synop' OR tbl_name='meteo_arfor' OR tbl_name='meteo_rofor' OR tbl_name='meteo_wins_war' OR tbl_name='meteo_wintem' OR tbl_name='vulcano_adv' OR tbl_name='volcanic_act' OR tbl_name='rqm' OR tbl_name='meteo_wsynop') and (status='waiting' OR status='not sent') ORDER BY tgl DESC",
//			"SELECT * FROM check_status WHERE (tbl_name='rpl_content' OR tbl_name='air_message' OR tbl_name='air_message_free_text') and (status='sent' OR status='expired') ORDER BY tgl DESC",
//			"SELECT * FROM check_status WHERE (tbl_name='notam_multi' OR tbl_name='notam_cl' OR tbl_name='notam_criteria' OR tbl_name='rqn' OR tbl_name='rql' OR tbl_name='ashtam' OR tbl_name='birdtam' OR tbl_name='snowtam') and (status='sent' OR status='expired') ORDER BY tgl DESC",
//			"SELECT * FROM check_status WHERE (tbl_name='air_message_free_text_meteo' OR tbl_name='meteo_metar' OR tbl_name='meteo_speci' OR tbl_name='meteo_sigmet' OR tbl_name='meteo_airmet' OR tbl_name='meteo_airep' OR tbl_name='meteo_tafor' OR tbl_name='meteo_synop' OR tbl_name='meteo_arfor' OR tbl_name='meteo_rofor' OR tbl_name='meteo_wins_war' OR tbl_name='meteo_wintem' OR tbl_name='vulcano_adv' OR tbl_name='volcanic_act' OR tbl_name='rqm' OR tbl_name='meteo_wsynop') and (status='sent' OR status='expired') ORDER BY tgl DESC",
//			"SELECT * FROM reject_message_ats ORDER BY tgl DESC",
//			"SELECT * FROM reject_message_notam ORDER BY tgl DESC",
//			"SELECT * FROM reject_message ORDER BY tgl DESC",
//	};
//	Shell sh,sh1;
//	static Label ldevice,lbaudrate,ltsec,lrseq,ldsec,lopenl,ltransid,lqueue;
//	Font font = new Font(shdisp.getDisplay(),"Sans",9,SWT.BOLD);
//	static Button bsett;
	
	public pid() {
		
	}
	
//	public void crtPID(Shell shell){ //add mega
//		shdisp.crtText(shell);
//		shell.setText("Page Information Display - ELSA");
//		shell.setLocation(0,0);
//		
//		int b_width=114; //2
//		int b_height=32; //1
//		int b_x=120; //2
//		int b_y=620; //1 105
//		int b_x2=35;
//		int rowSpac=5;
		
		
//		if (DisplayMain.pid20_y>0) b_y=DisplayMain.pid20_y;
//		if (DisplayMain.pid20_space_row>0) rowSpac=DisplayMain.pid20_space_row;
//		if (DisplayMain.pid20_w>0) b_width=DisplayMain.pid20_w;
//		if (DisplayMain.pid20_space_column>0) b_x=DisplayMain.pid20_space_column;
		
//		String sQueryp=new String("select * from pidmsg where nopid='1'");
//		final String sMsg=jdbc.getmsgpid(sQueryp);
//		if (sMsg!=null) shdisp.setTextSyncExec(sMsg);
//
//		int h=100,w=1100;
////		if (DisplayMain.text_h!=0) h=DisplayMain.shell_h;
////		if (DisplayMain.text_w!=0) w=DisplayMain.shell_w;
//		shell.setSize(w,h);
//		shdisp.open(shell);
//		socket.close();
//		System.out.println("socket closed");
//		Dtgram.setstclose(true);
//		timeoutext.setstclose(true);
//		timeoutline.setstclose(true);
//	}
	
	//add mega: grp1 & grp2
//	void crtgrp(Shell shell){
//		int b_x=10;
//		int b_y=8;
//		int b_width=265;
//		int b_height=(143/2)+4;
//		int b_spc=1;
//		
//		final Group grpview = new Group(shell, SWT.BORDER);
//		grpview.setText(" ");
//		grpview.setLayout(new GridLayout(2,false));
//		grpview.setBounds(b_x+(2*b_width)+20, b_y, b_width+220, b_height);
////		grpview.setBounds(b_x, DisplayMain.text_h+b_height+15, b_width+220, b_height);
//
//		String sdevi="";String sbaud="";String sdigit="";String stransid="";String srseq="",tsec="";
//		sdevi = "";
//
//		sbaud = DisplayMain.readInputFile("/tp/baudrate.txt");
//		if (!sbaud.isEmpty()) {
//			String sarr[] = sbaud.split("\n");
//			sbaud="";
//			for (int i=0;i<1;i++) {
//				sbaud=sarr[i];
//			}
//		}
//		sdigit = DisplayMain.readInputFile("/tp/digit.txt");
//		if (!sdigit.isEmpty()) {
//			String sarr[] = sdigit.split("\n");
//			sdigit="";
//			for (int i=0;i<1;i++) {
//				sdigit=sarr[i];
//			}
//		}
//		stransid = DisplayMain.readInputFile("/tp/transid.txt");
//		if (!stransid.isEmpty()) {
//			String sarr[] = stransid.split("\n");
//			stransid="";
//			for (int i=0;i<1;i++) {
//				stransid=sarr[i];
//			}
//		}
//
//		tsec = DisplayMain.readInputFile("/tp/tseq.txt");
//		if (!srseq.isEmpty()) {
//			String sarr[] = srseq.split("\n");
//			srseq="";
//			for (int i=0;i<1;i++) {
//				srseq=sarr[i];
//			}
//		}
//
//		srseq = DisplayMain.readInputFile("/tp/rseq.txt");
//		if (!srseq.isEmpty()) {
//			String sarr[] = srseq.split("\n");
//			srseq="";
//			for (int i=0;i<1;i++) {
//				srseq=sarr[i];
//			}
//		}
//
//		ldevice = new Label(grpview, SWT.LEFT);
//		ldevice.setText("Device : "+sdevi);
//
//		lbaudrate = new Label(grpview, SWT.LEFT);
//		lbaudrate.setText("Baudrate : "+sbaud);
//		lbaudrate.setBounds(15, 23, 150, 18);
//		lbaudrate.setFont(font);
//
//		ltsec = new Label(grpview, SWT.LEFT);
//		ltsec.setText("Tseq : "+tsec);
//		ltsec.setBounds(175, 23, 100, 18);
//		ltsec.setFont(font);
//
//		lrseq = new Label(grpview, SWT.LEFT);
//		lrseq.setText("Rseq : "+srseq);
//		lrseq.setBounds(175, 44, 100, 18);
//		lrseq.setFont(font);
//
//		ldsec = new Label(grpview, SWT.LEFT);
//		ldsec.setText("Digit seq. : "+sdigit);
//		ldsec.setBounds(275, 23, 100, 18);
//		ldsec.setFont(font);
//
//		lopenl = new Label(grpview, SWT.LEFT);
//		lopenl.setText("Line : ");
//		lopenl.setBounds(275, 44, 100, 18);
//		lopenl.setFont(font);
//		
//		ltransid = new Label(grpview, SWT.LEFT);
//		ltransid.setText("CID : "+stransid);
//		ltransid.setBounds(395, 23, 80, 18);
//		ltransid.setBounds(15, 44, 140, 18);
//		ltransid.setFont(font);
//		senddata("101","tsec",4);
//
//		lqueue = new Label(grpview, SWT.LEFT);
//		lqueue.setText("Queue : ");
//		lqueue.setBounds(395, 44, 80, 18);
//		lqueue.setBounds(395, 23, 80, 18);
//		lqueue.setFont(font);
//
//		final Group grptest = new Group(shell, SWT.BORDER);
//		grptest.setText(" ");
//		grptest.setLayout(new GridLayout(2,false));
////		grptest.setBounds(b_x+b_width+230, DisplayMain.text_h+b_height+15, b_width, b_height);
//		
//		Button btest = new Button(grptest,SWT.PUSH);
//		btest.setText("Test message");
//		btest.setBounds(10, 23, 120, 37);
//		sh = new Shell(shdisp.getDisplay(),SWT.MIN);
//		btest.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				if (!sh.isDisposed()) sh.close();
//				sh = new Shell(shdisp.getDisplay(),SWT.MIN);
//				new testmsg(sh).open();
//			}
//		});
//		
//		bsett = new Button(grptest,SWT.PUSH);
//		bsett.setText("Setting");
//		bsett.setBounds(135, 23, 120, 37);
//		sh1 = new Shell(shdisp.getDisplay(),SWT.MIN);
//		bsett.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				if (!sh1.isDisposed()) sh1.close();
//				sh1 = new Shell(shdisp.getDisplay(),SWT.MIN);
//				password pass = new password();
//				if (pass.open(sh1)) {
//					if (!sh1.isDisposed()) sh1.close();
//					sh1 = new Shell(shdisp.getDisplay(),SWT.MIN);
//					new setting(sh1).open();
//				}
//			}
//		});
//	}

	//add mega: grp3
//	void crtIOSR(Shell shell){
//		int b_x1=10;
//		int b_width1=265;
//		int b_height1=(143/2)+4;
//		final Group tableGroupInb = new Group(shell, SWT.BORDER);
//		tableGroupInb.setText("Inbox");
//		tableGroupInb.setLayout(new GridLayout(2,false));
////		tableGroupInb.setBounds(b_x1+(2*b_width1)+240, DisplayMain.text_h+b_height1+15, b_width1, b_height1);
//		tableGroupInb.setFont(font);
//
//		int b_x=19,b_y=25,b_width=120,b_height=35,b_spc=4,b_spc_y=25;
//		final Button btn[]=new Button[13];
//		
//		int i_btnText=0,i_shText=0;
//		Label label[] = new Label[12];
//		label = shdisp.createLabel(tableGroupInb);
//		//shMessage=shdisp.getShell();
//		int iL=0,iCount=0;
//		int iL1=0;
//
//		for (int iLoop=0;iLoop<1;iLoop++) {
//			//label Last Msgs
//			label[iLoop].setText(new DateUtils().now().substring(11));
//			label[iLoop].setFont(font);
//			btn[iLoop]= new Button(tableGroupInb, SWT.PUSH);
//			btnbell= new Button(tableGroupInb, SWT.PUSH);
//			Label sTlabel = new Label(tableGroupInb, SWT.LEFT);
//			sTlabel.setText("Last Msg");
//			sTlabel.setFont(font);
//			if (iLoop==0) {
//				sTlabel.setBounds(b_x-3, (b_y+(iLoop*b_height)+((iLoop+1)*b_spc)), b_width-53, b_height-15);
//				sTlabel.setBounds(15, 22, b_width-53, b_height-15);
//			}
//			if ((iLoop==3) || (iLoop==6) || (iLoop==9)) {
//				b_y+=b_spc_y;i_btnText=0;i_shText++;
//				sTlabel.setBounds(b_x-3, (b_y+(iLoop*b_height)+((iLoop+1)*b_spc)), b_width-53, b_height-15);
//				sTlabel.setBounds(15, 22, b_width-53, b_height-15);
//			}
//			else if (iLoop==12) {b_y+=b_spc_y;i_btnText=3;}
//			btn[iLoop].setText(g_btnText[i_btnText]);i_btnText++;
//			sqlshell[iLoop]=shdisp.getShell1();
//			sqlshell[iLoop].setText(g_shellText[i_shText]);
//			//sqlshell[iLoop].setText(g_shellText[i_shText]+btn[iLoop].getText());
//			
////			if (iLoop!=0) {
////				for (int iLp=0;iLp<itrue.length;iLp++) {  
////					if (iLoop==itrue[iLp]) {
////						b_y+=4;
////						iL-=3;
////						System.out.println("*****************iL1:"+iL1);
////						btn[iLoop].setText(g_btnText1[iL1]);
////						iL1++;
////						break;
////					}
////					else sTlabel.setVisible(false);
////				}
//// 		    }
////			else {
////				btn[iLoop].setText(g_btnText1[iL1]);
////				iL1++;
////			}
//			
//			btn[iLoop].setBounds(b_x+74, (b_y+(iL*b_height)+((iL+1)*b_spc)), b_width, b_height+2);
//			btn[iLoop].setBounds(90, 23, b_width, b_height+2);
//			label[iLoop].setBounds(b_x, (b_y+(iL*b_height)+((iL+1)*b_spc))+22, b_width-60, b_height-15);
//			label[iLoop].setBounds(15, 45, b_width-60, b_height-15);
//
//			String s2="/tp/bell1.txt";
//			WriteToAFile wr = new WriteToAFile();
//			wr.open(s2);
//			wr.write("1");
//			wr.close();
//
//			btnbell.setBounds(9+b_width+10, (b_y+(iL*b_height)+((iL+1)*b_spc)), 40, b_height+2);
//			btnbell.setBounds(90+b_width+7, 23, 40, b_height+2);
//			btnbell.setImage(image);
//			//btnbell.setBackgroundImage(image);
//			btnbell.addSelectionListener(new SelectionAdapter() {
//				public void widgetSelected(SelectionEvent e) {
//					String s1="/tp/bell.txt";
//					String s2="/tp/bell1.txt";
//					File f = new File(s1);
//					if (!f.exists()) {
//						if (btnbell.getImage()==image) {
//							File f1 = new File(s1);
//							boolean b = f1.delete();
//							System.err.println("delete:"+s1+" res:"+b);
//							
//							File f2 = new File(s2);
//							boolean b2 = f2.delete();
//							System.err.println("delete:"+s2+" res:"+b2);
//							btnbell.setImage(image1);
//						}
//						else {
//							btnbell.setImage(image);
//							WriteToAFile wr = new WriteToAFile();
//							wr.open(s2);
//							wr.write("1");
//							wr.close();
//						}
//					}
//					else {
//						File f1 = new File(s1);
//						boolean b = f1.delete();
//						System.err.println("delete:"+s1+" res:"+b);
//						
//						File f2 = new File(s2);
//						boolean b2 = f2.delete();
//						System.err.println("delete:"+s2+" res:"+b2);
//						btnbell.setImage(image1);
//						btnbell.setImage(image1);
//					}
//
//					/*try {
//						String s = "echo elsaelsa | sudo -S rm -f /tp/bell.txt"; 
//						Runtime.getRuntime().exec(s);
//						System.err.println("s:"+s);
//					}
//					catch(IOException err) {
//						System.err.println("err:"+err.getMessage());
//					}*/
//				}
//			});
//				
//
////			btn[iLoop].setBounds(b_x+74, (b_y+(iLoop*b_height)+((iLoop+1)*b_spc)), b_width, b_height);
////			label[iLoop].setBounds(b_x, (b_y+(iLoop*b_height)+((iLoop+1)*b_spc))+22, b_width-60, b_height-15);
//			
//			//setfvisible(iLoop,btn[iLoop]);
//			//setfvisible(iLoop,label[iLoop]);
//
//			btn[iLoop].addSelectionListener(new SelectionAdapter() {
//				public void widgetSelected(SelectionEvent e) {
//					try{
//						int i_sh=12;
//						for (int iLoop=0;iLoop<12;iLoop++) {if (e.widget==btn[iLoop]) {i_sh=iLoop;break;}}
//						if (sqlshell[i_sh].isDisposed()) {
//							System.out.println("disposed");
//							sqlshell[i_sh]=shdisp.getShell1();
//							int i_shTxt=0;
//							if ((i_sh>=3) && (i_sh<=5)) i_shTxt=1;
//							else if ((i_sh>=6) && (i_sh<=8)) i_shTxt=2;
//							else if ((i_sh>=9) && (i_sh<=11)) i_shTxt=3;
//							
//							//sqlshell[i_sh].setText(g_shellText[i_shTxt]+btn[i_sh].getText());
//							sqlshell[i_sh].setText(btn[i_sh].getText());
//						}
//						System.out.println("sh"+i_sh+" "+sqlshell[i_sh]+" "+sqlshell[i_sh].isVisible());
//						//shMessage.close();
//						//shMessage=shdisp.getShell();
//						
//						String sQueryCh=g_sQuery[i_sh];
//			            System.out.println("***Before sQueryCh:"+sQueryCh);
//						Dtgram.getYearMonth();
//			            /*if (g_sQuery[i_sh].contains("reject_message_ats")) 
//			            	sQueryCh=sQueryCh.replace("reject_message_ats",Dtgram.sTable2);
//			            if (g_sQuery[i_sh].contains("'air_message_free_text'")) 
//			            	sQueryCh=sQueryCh.replace("air_message_free_text",Dtgram.sTable1);
//			            if (g_sQuery[i_sh].contains("'air_message'"))
//			            	sQueryCh=sQueryCh.replace("'air_message'","'"+Dtgram.sTable+"'");
//			            if (g_sQuery[i_sh].contains("incoming_msg"))
//			            	sQueryCh=sQueryCh.replace("incoming_msg",""+Dtgram.sTable+"");
//			            */
//			            sQueryCh = replaceTbl(sQueryCh);
//			            System.out.println("***After sQueryCh:"+sQueryCh);
//						if (!sqlshell[i_sh].isVisible()) {
//							if ((i_sh>=0) && (i_sh<=2)) new IOSR().koneksiDB(sqlshell[i_sh],sQueryCh,i_sh);
//							//else if ((i_sh>=3) && (i_sh<=8)) new IOSR().koneksiDBout(sqlshell[i_sh],sQueryCh,i_sh);
//							//else if ((i_sh>=9) && (i_sh<=11)) new IOSR().koneksiDBrej(sqlshell[i_sh],sQueryCh,i_sh);
//							///else if (i_sh==12) pidSetup.view(sqlshell[i_sh]);
//						}
//						else {
//							sqlshell[i_sh].close();
//							sqlshell[i_sh]=shdisp.getShell1();
//							//int i_shTxt=0;
//							//if ((i_sh>=3) && (i_sh<=5)) i_shTxt=1;
//							//else if ((i_sh>=6) && (i_sh<=8)) i_shTxt=2;
//							//else if ((i_sh>=9) && (i_sh<=11)) i_shTxt=3;
//							//sqlshell[i_sh].setText(g_shellText[i_shTxt]+btn[i_sh].getText());
//							sqlshell[i_sh].setText(btn[i_sh].getText());
//							if ((i_sh>=0) && (i_sh<=2)) new IOSR().koneksiDB(sqlshell[i_sh],sQueryCh,i_sh);
//							//else if ((i_sh>=3) && (i_sh<=8)) new IOSR().koneksiDBout(sqlshell[i_sh],sQueryCh,i_sh);
//							//else if ((i_sh>=9) && (i_sh<=11)) new IOSR().koneksiDBrej(sqlshell[i_sh],sQueryCh,i_sh);
//							//sqlshell[i_sh].setSize(1280, 600);
//							sqlshell[i_sh].setVisible(true);
//						}
//						shdisp.getLabel(i_sh).setForeground(new Color(shdisp.getLabel(i_sh).getShell().getDisplay(),shdisp.red,shdisp.blue,shdisp.green));
//		            } catch (Exception ee) {
//		                ee.printStackTrace();
//		            }
//				}
//			});
//			iL++;
//		}
//	}

	static void setBtn(){ //dipanggil di displays.pid.pidSetup
		jdbc.setBtnPID("select id,type_message from pid", btn);
	}

//	private void setfvisible(int iLoop,Button btn){
//		for (int i=0;i<ifalse.length;i++) {
//			if (iLoop==ifalse[i]) btn.setVisible(false);
//		}
//	}
//
//	private void setfvisible(int iLoop,Label label){
//		for (int i=0;i<ifalse.length;i++) {
//			if (iLoop==ifalse[i]) label.setVisible(false);
//		}
//	}
	
	public static String replaceTbl(String sQueryCh){ //dipanggil di grp3 displays.MainForm
		String sRet="";
		String yearMonth = Dtgram.gtMonth();
        if (sQueryCh.contains(Dtgram.sMetTable[17]+" "))
        	sQueryCh=sQueryCh.replace(Dtgram.sMetTable[17],Dtgram.sMetTable[17]+yearMonth);
        else if (sQueryCh.contains(Dtgram.sNtmTable[6]+" "))
        	sQueryCh=sQueryCh.replace(Dtgram.sNtmTable[6],Dtgram.sNtmTable[6]+yearMonth);

        if ((sQueryCh.contains("meteo")) || (sQueryCh.contains("METEO"))) {
			for (int i=0;i<Dtgram.sMetTable.length;i++) {
		        if (sQueryCh.contains("'"+Dtgram.sMetTable[i]+"'"))
		        	sQueryCh=sQueryCh.replace("'"+Dtgram.sMetTable[i]+"'","'"+Dtgram.sMetTable[i]+yearMonth+"'");
			}
		}
		else if ((sQueryCh.contains("notam")) || (sQueryCh.contains("NOTAM"))) {
			for (int i=0;i<Dtgram.sNtmTable.length;i++) {
	            if (sQueryCh.contains("'"+Dtgram.sNtmTable[i]+"'"))
	            	sQueryCh=sQueryCh.replace("'"+Dtgram.sNtmTable[i]+"'","'"+Dtgram.sNtmTable[i]+yearMonth+"'");
			}
		}
		sRet=sQueryCh;
		return sRet;
	}
	
//    public static void senddata(String sPort,String sData,int length) { //dipanggil di grp1 displays.MainForm
//  	  try {
//  		  String sIPadrp="127.0.0.1";
//  		  DatagramSocket s = new DatagramSocket();
//            byte[] line = new byte[10000];
//			  System.out.println("To   : "+sIPadrp+" Data:"+sData);
//            InetAddress dest = InetAddress.getByName(sIPadrp);
//            line = sData.getBytes();
//            int len = line.length;
//            DatagramPacket pkt = new DatagramPacket(line, len, dest, Integer.parseInt(sPort));
//			  System.out.println("Send : "+len+" bytes ");
//            s.send(pkt);
//            s.close();
//        } catch (Exception err) {
//            System.out.println("err:" + err);
//        }
//	}

}

