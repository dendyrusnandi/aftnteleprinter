package displays.pid;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

public class viewExpNOTAM {
	Table table;
	Shell shell;
	Shell shellMessage;
	Label label;

	void createContent(Shell pshell){
		shell=pshell;
		if (!shdisp.getDisplay().isDisposed()) {
			shdisp.getDisplay().syncExec(new Runnable() {
				public void run() {
					//shell = new Shell(SWT.MAX);
					shell.setText("List of Expired NOTAM");
					label = new Label(shell, SWT.LEFT);
					label.setText("List of Expired NOTAM");
					label.setBounds(10,10,145,20);
					table = new Table (shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);;
					//table.setLinesVisible (true);
					table.setHeaderVisible (true);
					table.setBounds(10,35,730,150);
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

					final TableColumn seqNtm = new TableColumn(table,SWT.LEFT);
					seqNtm.setText("Seq. NOTAM");
					seqNtm.setWidth(90);
					seqNtm.setResizable(false);

					final TableColumn idntfr = new TableColumn(table,SWT.LEFT);
					idntfr.setText("Identifier");
					idntfr.setWidth(80);
					idntfr.setResizable(false);

					final TableColumn refSeqNtm = new TableColumn(table,SWT.LEFT);
					refSeqNtm.setText("Ref.Seq.NOTAM");
					refSeqNtm.setWidth(110);
					refSeqNtm.setResizable(false);

					final TableColumn loc = new TableColumn(table,SWT.LEFT);
					loc.setText("Loca.");
					loc.setWidth(60);
					loc.setResizable(false);

					final TableColumn dfrom = new TableColumn(table,SWT.LEFT);
					dfrom.setText("From");
					dfrom.setWidth(100);
					dfrom.setResizable(false);

					final TableColumn dto = new TableColumn(table,SWT.LEFT);
					dto.setText("To");
					dto.setWidth(100);
					dto.setResizable(false);

					final TableColumn id = new TableColumn(table,SWT.LEFT);
					id.setText("ID");
					id.setWidth(0);
					id.setResizable(false);

					final TableColumn tgl = new TableColumn(table,SWT.LEFT);
					tgl.setText("To");
					tgl.setWidth(0);
					tgl.setResizable(false);

					final TableColumn tname = new TableColumn(table,SWT.LEFT);
					tname.setText("tname");
					tname.setWidth(0);
					tname.setResizable(false);

					/*final TableColumn text = new TableColumn(table,SWT.LEFT);
					text.setText("Text");
					text.setWidth(690);
					text.setResizable(false);*/
					//jdbc.getExpiredNotam("SELECT * FROM notam_multi where tgl like '2008%' and (part='' and part_nr='')", table);
					jdbc.getExpiredNotam("SELECT * FROM notam_multi where (st='0' or st=0) order by tgl desc", table);

					shell.setBounds(600,800,750,200);
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
							System.out.println("selection "+selection[0].getText(7));
							System.out.println("selection "+selection[0].getText(8));
							System.out.println("selection "+selection[0].getText(9));
							System.out.println("selection "+selection[0].getText(10));
							System.out.println("selection "+selection[0].getText(11));
							selection[0].setBackground(new Color(shdisp.getDisplay(),192,192,192));

							String sQuery="";
							if (selection[0].getText(4)!=null) {
								String sid="",tbnm="",typ="",stgl="";
								if (selection[0].getText(0)!=null) sid=selection[0].getText(9);
								tbnm=selection[0].getText(11);
								typ="NOTAM";
								if (selection[0].getText(6)!=null) stgl=selection[0].getText(10);
								IOSR cIOSR = new IOSR();
								sQuery=cIOSR.createQuery(tbnm,sid,typ,stgl);
								System.out.println("sQuery:"+sQuery);
								if (shellMessage.isDisposed()) {
									shellMessage = new Shell(SWT.MAX | SWT.CENTER);
								}
								shellMessage.close();
								shellMessage = new Shell(SWT.MAX | SWT.CENTER);
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
}
