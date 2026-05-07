package displays.pid;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.*;

import setting.Colors;

import displays.MainForm;
import displays.TeleSplashScreen2016IP;

public class shdisp {
//	private static Display display=new Display(); //add mega
	private static Display display=TeleSplashScreen2016IP.display;
	private static Text text;
	private static Table table[]=new Table[13];
	private static PlayerComparatorInbATS1_S comparatorInbATS1_S[]=new PlayerComparatorInbATS1_S[13];
	private static PlayerComparatorOutATS1_S comparatorOutATS1_S[]=new PlayerComparatorOutATS1_S[13];
	private static PlayerComparatorRejATS1_S comparatorRejATS1_S[]=new PlayerComparatorRejATS1_S[13];
	private static java.util.List playersInbATS1_S[]=new java.util.List[13];
	private static java.util.List playersOutATS1_S[]=new java.util.List[13];
	private static java.util.List playersRejATS1_S[]=new java.util.List[13];
	private static Label label[] = new Label[12];
	static Display dsp = display;
	public static int red=0,blue=0, green=0;
		
	public static Shell getShell(){
		Shell shell = new Shell(display,SWT.MIN);
		return shell;
	}
	
	public static Shell getShell1(){
		Shell shell = new Shell(display,SWT.MIN);
		return shell;
	}

	public static Label[] createLabel(Composite shell){
		for (int iCnt=0;iCnt<12;iCnt++) {
			label[iCnt] = new Label(shell, SWT.LEFT);
			red=label[iCnt].getForeground().getRed();
			blue=label[iCnt].getForeground().getBlue();
			green=label[iCnt].getForeground().getGreen();
		}
		return label;
	}
	
	public static Label getLabel(int id) {
		return label[id];
	}
	
	public static void open(Shell shell){
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()){
				display.sleep();
			}
		}
		display.dispose();
	}
	
	/*****Page Information Display-PID*****/
	public static void crtText(Shell shell) {
		text = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		Color c1=text.getForeground();
		text.setBackground(Colors.Grey);//pid.bsett.getBackground());
		Font font = new Font(display,"FreeMono",14,SWT.BOLD);
		text.setFont(font);
		int h=568,w=1110;
//		if (DisplayMain.text_h>0) h=DisplayMain.text_h;
//		if (DisplayMain.text_w>0) w=DisplayMain.text_w;
		text.setBounds(10,13,w,h+77);
		text.setBounds(160,10,1265,300);
		text.setEditable(false);
	}
	
	public static void setTextSyncExec(final String sMsg) {
		if (!TeleSplashScreen2016IP.display.isDisposed()) {
			TeleSplashScreen2016IP.display.syncExec(new Runnable() {
				public void run() {
//					if (text.isDisposed ()) return;
//					text.setText(sMsg);
//					if (MainForm.text.isDisposed ()) return; //text tidak ditampilkan - permintaan acep 9 maret 2015 
//					MainForm.text.setText(sMsg);
					System.out.println("PID updated");
				}
			});
		}
	}
	/*****End of PID*****/
	
	/*****Information Message*****/
	public static Table crtTable(Shell shell,int typ){
		table[typ] = new Table (shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);;
		table[typ].setLinesVisible (true);
		table[typ].setHeaderVisible (true);
		int i_width=665;
		int i_height=0;//DisplayMain.inbox_h-5;//add mega
		if (i_height==0) i_height=560;
		if ((typ>=3) && (typ<=5)) i_width=10;
		table[typ].setBounds(8,9,i_width,i_height);
		table[typ].removeAll();
		System.out.println("crtTable:"+typ+" width:"+i_width);
		return table[typ];
	}

	public static PlayerComparatorInbATS1_S crtPlayerInb(int typ){
		comparatorInbATS1_S[typ] = new PlayerComparatorInbATS1_S();
	    comparatorInbATS1_S[typ].setColumn(PlayerComparatorInbATS1_S.ID);
	    comparatorInbATS1_S[typ].setDirection(PlayerComparatorInbATS1_S.ASCENDING);
	    playersInbATS1_S[typ] = new ArrayList();
	    System.out.println("crt crtPlayerInb"+typ);
		return comparatorInbATS1_S[typ];
	}
	
	public static PlayerComparatorOutATS1_S crtPlayerOut(int typ){
	    comparatorOutATS1_S[typ] = new PlayerComparatorOutATS1_S();
	    comparatorOutATS1_S[typ].setColumn(PlayerComparatorOutATS1_S.DTM);
	    comparatorOutATS1_S[typ].setDirection(PlayerComparatorOutATS1_S.ASCENDING);
	    playersOutATS1_S[typ] = new ArrayList();
	    System.out.println("crt crtPlayerOut"+typ);
	    return comparatorOutATS1_S[typ];
	}

	public static PlayerComparatorRejATS1_S crtPlayerRej(int typ){
	    comparatorRejATS1_S[typ] = new PlayerComparatorRejATS1_S();
	    comparatorRejATS1_S[typ].setColumn(PlayerComparatorRejATS1_S.ID);
	    comparatorRejATS1_S[typ].setDirection(PlayerComparatorRejATS1_S.ASCENDING);
	    playersRejATS1_S[typ] = new ArrayList();
	    System.out.println("crt crtPlayerRej");
	    return comparatorRejATS1_S[typ];
	}

	public static java.util.List getplayersInbATS1_S(int typ){
		return playersInbATS1_S[typ];
	}
	
	public static java.util.List getplayersOutATS1_S(int typ){
		return playersOutATS1_S[typ];
	}

	public static java.util.List getplayersRejATS1_S(int typ){
		return playersRejATS1_S[typ];
	}

	public static void OneByOne(final int typ,final String sQuery,final int typ1) {
		if (!display.isDisposed()) {
			display.syncExec(new Runnable() {
				public void run() {
//					try {
//						System.out.println("typ1:"+typ1+"[-playerInb:"+playersInbATS1_S[typ1]+"-] [-playerOut:"+playersOutATS1_S[typ1]+"-] [-playerRej:"+playersRejATS1_S[typ1]+"-]");
//						if (typ==1)	{
////							jdbc.oneByone(sQuery, playersInbATS1_S[typ1], comparatorInbATS1_S[typ1], table[typ1] , label[typ1], typ1);
//						}
//						else if (typ==2) {
////							jdbc.oneByone(sQuery, playersOutATS1_S[typ1], comparatorOutATS1_S[typ1], table[typ1] , label[typ1], typ1); 
//						}
//						else if (typ==3) {
//							jdbc.oenByone(sQuery, playersRejATS1_S[typ1], comparatorRejATS1_S[typ1], table[typ1], label[typ1], typ1);
//						}
//					} catch (Exception s) {
//						System.out.println(""+s);
//					}
				}
			});
		}
  	}
	/*****End of information Message*****/
	
	public static Display getDisplay() {
		return display;
	}
}
