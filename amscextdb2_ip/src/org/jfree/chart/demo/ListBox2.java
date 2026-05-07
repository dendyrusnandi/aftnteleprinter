package org.jfree.chart.demo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.demo.chartMain;
import org.jfree.chart.demo.criteria;
public class ListBox2 {
	Shell shell;
	Composite comp;
	int width,height;
	List li_1;
	String sContents[];
	Button btn;
	int init=0;
	int x,y;
	int typelist;
	criteria cri;
	public ListBox2(Shell shell,int width,int height,String sContents[],Button btn,int x,int y,int typelist)
	{
		this.shell=shell;
		this.width=width;
		this.height=height;
		this.sContents=sContents;
		this.btn=btn;
		this.x=x;
		this.y=y;
		this.typelist=typelist;
		contents();
		cri=chartMain.criteria1;
	}
	public void contents(){
		shell.setSize(width, height);
		li_1=new List(shell,SWT.BORDER|SWT.V_SCROLL);
		li_1.setBounds(2,2,width-4,height-4);
		li_1.setItems(sContents);
		init=0;
		li_1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println("init:"+init);
				if (init==1) {
					btn.setText(li_1.getSelection()[0]);
					if (typelist==1) {
						cri.listField.removeAll();
						cri.listX.removeAll();
						int length=0;
						criteria.sFilename="";
						cri.b_setkey.setVisible(true);
						cri.b_delkey.setVisible(false);
						if (cri.C_typchart.getText().compareTo("ATS")==0) {
							criteria.g_sTname="air_message";
							length=cri.getdt(cri.sFnmfield+"fieldATS");
							criteria.sFilename=cri.sFnmfield+"fieldATS";
							System.out.println("sFilename=" + criteria.sFilename);
							//ListXval[0]="FPL";ListXval[1]="2010";ListXval[2]="JANUARY";
							criteria.g_sFile=cri.sFnmfield+"chartATS";
						}
						else if (cri.C_typchart.getText().compareTo("NOTAM")==0) {
							criteria.g_sTname="notam_multi";
							length=cri.getdt(cri.sFnmfield+"fieldNTM");
							criteria.sFilename=cri.sFnmfield+"fieldNTM";
							//ListXval[0]="A";ListXval[1]="2010";ListXval[2]="JANUARY";
							criteria.g_sFile=cri.sFnmfield+"chartNTM";
						}
						else if (cri.C_typchart.getText().compareTo("METAR")==0) {
							criteria.g_sTname="meteo_metar";
							length=cri.getdt(cri.sFnmfield+"fieldMET");
							criteria.sFilename=cri.sFnmfield+"fieldMET";
							//ListXval[0]="WAAA";ListXval[1]="2010";ListXval[2]="JANUARY";
							criteria.g_sFile=cri.sFnmfield+"chartMET";
						}
						for (int iCnt=0;iCnt<length;iCnt++) cri.listField.add(cri.s_items[iCnt]);
						for (int iLoop=0;iLoop<3;iLoop++){
							String s = cri.gtdt(criteria.sFilename,iLoop+1);
							System.out.println("s:"+s);
							cri.listX.add(s+":"+cri.ListXval[iLoop]);
							cri.listX.setItem(iLoop,s+":");//+ListXval[iLoop]);
						}
				        System.out.println("sFilename:"+criteria.sFilename);
					}
					else if (typelist==2) {
						String s = cri.listX.getItem(Integer.parseInt(cri.outTextX));
						if (s.contains("Month")) {
							if (!cri.T_addV.getText().isEmpty()) {
								cri.T_addV.append(",");
							}
						}
						if (s.contains("Msg Type")) {
							if (!cri.T_addV.getText().isEmpty()) cri.T_addV.append(",");
						}
						cri.T_addV.setText(cri.T_addV.getText()+cri.C_month.getText());
						cri.T_addV.setSelection(cri.T_addV.getText().length());
						cri.T_addV.setFocus();
						String a = cri.T_addV.getText();
						cri.T_addV.setText(a);
						cri.T_addV.setText(a.replaceAll("-,", "-"));
		      			if (cri.listX.getItemCount()>0) {
		      				cri.listX.setItem(Integer.parseInt(cri.outTextX),cri.L_name.getText()+cri.T_addV.getText());
		      			}
		      			cri.T_addV.setSelection(cri.T_addV.getText().length());
					}
					shell.close();
				}
				init=1;
			}
		});
		shell.setLocation(x, y);
		shell.open();
	}
}
