package displays.pid;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class pidSetup {
	static String g_sid="";
	public static void view(Shell shell){
		shell.setText("PID Setup");
		String sQuery="select * from pid";
	    final Combo cmb_mtyp=new Combo(shell,SWT.READ_ONLY);
		final Label loca=new Label(shell,SWT.LEFT);
		final Combo cmb_sbjct = new Combo(shell,SWT.READ_ONLY);
		final Text textm= new Text(shell, SWT.MULTI | SWT.BORDER );
		final Text text= new Text(shell, SWT.MULTI | SWT.BORDER );;
		final Text textf= new Text(shell, SWT.MULTI | SWT.BORDER );;
		final Button b_store= new Button(shell, SWT.PUSH);

		final Table table;		
		table = new Table (shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		table.setBounds(10,10,985,510);
		GridData data = new GridData(SWT.DragDetect, SWT.FILL, true, true);
		data.heightHint = 1;
		data.widthHint = 1;
		table.setLayoutData(data);
		final TableColumn ID = new TableColumn(table,SWT.LEFT);
		ID.setText("ID");
		ID.setWidth(40);
		ID.setResizable(false);

		final TableColumn ft = new TableColumn(table,SWT.LEFT);
		ft.setText("MESSAGE TYPE");
		ft.setWidth(150);
		ft.setResizable(false);

		final TableColumn st = new TableColumn(table,SWT.LEFT);
		st.setText("LOC. A)");
		st.setWidth(150);
		st.setResizable(false);

		final TableColumn dtm = new TableColumn(table,SWT.LEFT);
		dtm.setText("SUBJECT");
		dtm.setWidth(150);
		dtm.setResizable(false);
		
		final TableColumn typ = new TableColumn(table,SWT.LEFT);
		typ.setText("LOC. METEO");
		typ.setWidth(100);
		typ.setResizable(false);

		final TableColumn ser = new TableColumn(table,SWT.LEFT);
		ser.setText("FREE TEXT");
		ser.setWidth(150);
		ser.setResizable(false);

	    table.removeAll();
	    table.setRedraw(true);
	    jdbc.getPID(sQuery,table);
	    
		int b_x=8;
		int b_y=15+510;
		int b_width=600;
		int b_height=185;
		edit(shell,table,cmb_mtyp,loca,cmb_sbjct,textm,text,textf,b_store);
		final Group tableGroup = new Group(shell, SWT.BORDER);
		tableGroup.setText(" Edit ");
		tableGroup.setLayout(new GridLayout(2,false));
		tableGroup.setBounds(b_x, b_y, b_width, b_height+10);
		b_store.setEnabled(false);
		
	    table.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				TableItem [] selection = table.getSelection();
				/*System.out.println("selection "+selection[0].getText(0));
				System.out.println("selection "+selection[0].getText(1));
				System.out.println("selection "+selection[0].getText(2));
				System.out.println("selection "+selection[0].getText(3));
				System.out.println("selection "+selection[0].getText(4));
				System.out.println("selection "+selection[0].getText(5));*/
				g_sid=selection[0].getText(0);
				cmb_mtyp.setText(selection[0].getText(1));
			    text.setText(selection[0].getText(2));
				cmb_sbjct.setText(selection[0].getText(3));
				textm.setText(selection[0].getText(4));
				textf.setText(selection[0].getText(5));
				
				b_store.setEnabled(true);
			    text.setEnabled(false);
				cmb_sbjct.setEnabled(false);
				textm.setEnabled(false);
				textf.setEnabled(false);
				cmb_mtyp.setEnabled(true);
				String sCode=jdbc.getPIDcode("select code from master_type where type='"+cmb_mtyp.getText()+"'");
				System.out.println("sCode:"+sCode+sCode.length());
				if (sCode.startsWith("NOTIFICATION")) {textf.setEnabled(true);System.out.println("OK");}
				else if (sCode.startsWith("NOTAM")) {cmb_sbjct.setEnabled(true);text.setEnabled(true);}
				else if (sCode.startsWith("METEO")) textm.setEnabled(true);
			}
		});

		shell.setSize(1020, 735);
		shell.open();
	}	
	
	public static void edit(Composite tableGroup,final Table table,	final Combo cmb_mtyp,Label loca,final Combo cmb_sbjct,final Text textm,final Text text,final Text textf,final Button b_store){
		int b_x=18;
		int b_y=545;
		int b_width=150;
		int b_height=20;
		int y_lbl=7;
		int b_y_sp=10;
		Label mtyp=new Label(tableGroup,SWT.LEFT);
		mtyp.setText("Message Type");
		mtyp.setBounds(b_x,b_y+y_lbl,b_width,b_height);
		
		//type message combo
		jdbc.getPIDtyp("select type from master_type group by type", cmb_mtyp);
		cmb_mtyp.setBounds(b_x+b_width-1,b_y,b_width,b_height);
		
		loca.setText("Location A) NOTAM");
		loca.setBounds(b_x,b_y+b_height+(2*y_lbl),b_width,b_height);

		//location A) text
		text.setBounds(b_x+b_width,b_y+b_height+b_y_sp,400,b_height);
		
		Label subject=new Label(tableGroup,SWT.LEFT);
		subject.setText("Subject");
		subject.setBounds(b_x,b_y+(2*b_height)+(3*y_lbl),b_width,b_height);

		//subject combo
		jdbc.getPIDtyp("select code_23 from notam_criteria group by code_23", cmb_sbjct);
		cmb_sbjct.setBounds(b_x+b_width-1,b_y+(2*b_height)+(b_y_sp+3),b_width,b_height);

		Label locm=new Label(tableGroup,SWT.LEFT);
		locm.setText("Location Meteo");
		locm.setBounds(b_x,b_y+(3*b_height)+(4*y_lbl),b_width,b_height);

		//location meteo text
		textm.setBounds(b_x+b_width,b_y+(3*b_height)+(b_y_sp+13),b_width,b_height);

		Label freet=new Label(tableGroup,SWT.LEFT);
		freet.setText("Free Text");
		freet.setBounds(b_x,b_y+(4*b_height)+(5*y_lbl),b_width,b_height);

		//free text Text
		textf.setBounds(b_x+b_width,b_y+(4*b_height)+(b_y_sp+18),400,(b_height*2)+20);
		
		cmb_mtyp.setEnabled(false);
	    text.setEnabled(false);
		cmb_sbjct.setEnabled(false);
		textm.setEnabled(false);
		textf.setEnabled(false);

		b_store.setText("Store");
		b_store.setBounds(b_x+600,532,120,40);
		String s_id = new String("1");
		b_store.setData(s_id);
		System.out.println("b_store.data"+b_store.getData());
		b_store.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try{
					String sQuery="update pid set";
					if (cmb_mtyp.getText()!=null) {
						sQuery +=" type_message='"+cmb_mtyp.getText()+"',";
					}
					if (text.getText()!=null) {
						sQuery +=" criteria='"+text.getText()+"',";
					}
					if (cmb_sbjct.getText()!=null) {
						sQuery +=" E='"+cmb_sbjct.getText()+"',";
					}
					if (textm.getText()!=null) {
						sQuery +=" location_meteo='"+textm.getText()+"',";
					}
					if (textf.getText()!=null) {
						sQuery +=" free_text='"+textf.getText()+"'";
					}
					sQuery+=" where id="+g_sid;
					jdbc.updtPID(sQuery);
					sQuery="update pidmsg set message=";
					if (textf.getText()!=null) {
						sQuery +="'"+textf.getText()+"'";
					}
					sQuery+=" where nopid="+g_sid;
					jdbc.updtPID(sQuery);
					jdbc.getPID("select * from pid", table);
					pid.setBtn();
	            } catch (Exception ee) {
	                ee.printStackTrace();
	            }
			}
		});
		
		cmb_mtyp.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try{
					b_store.setEnabled(true);
				    text.setEnabled(false);
					cmb_sbjct.setEnabled(false);
					textm.setEnabled(false);
					textf.setEnabled(false);
					cmb_mtyp.setEnabled(true);
					String sCode=jdbc.getPIDcode("select code from master_type where type='"+cmb_mtyp.getText()+"'");
					System.out.println("sCode:"+sCode);
					if (sCode.startsWith("NOTIFICATION")) {textf.setEnabled(true);System.out.println("OK");}
					else if (sCode.startsWith("NOTAM")) {cmb_sbjct.setEnabled(true);text.setEnabled(true);}
					else if (sCode.startsWith("METEO")) textm.setEnabled(true);
	            } catch (Exception ee) {
	                ee.printStackTrace();
	            }
			}
		});


	}

}
