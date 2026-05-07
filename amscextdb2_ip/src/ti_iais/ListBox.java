package ti_iais;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class ListBox {
	Shell shell;
	Composite comp;
	int width,height;
	List li_1;
	String sContents[];
	Button btn;
	int init=0;
	int x,y;
	public ListBox(Shell shell,int width,int height,String sContents[],Button btn,int x,int y)
	{
		this.shell=shell;
		this.width=width;
		this.height=height;
		this.sContents=sContents;
		this.btn=btn;
		this.x=x;
		this.y=y;
		contents();
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
					shell.close();
				}
				init=1;
			}
		});
		shell.setLocation(x, y);
		shell.open();
	}
}
