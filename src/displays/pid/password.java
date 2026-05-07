package displays.pid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class password {
	boolean flgin;
	Shell shell;
	Label label;
	public boolean open(Shell pshell) {
		flgin=false;
	    ///Font font = new Font(shdisp.getDisplay, "Courier", 10, SWT.BOLD);
	    
		shell=pshell;
		shell.setSize(280, 200);
		shell.setLocation(600, 20);
		shell.setText("Password");
		
		label = new Label(shell, SWT.CENTER);
		label.setText("Please insert password !");
		label.setBounds(40,110,220,20);
		
		final Text text= new Text(shell, SWT.BORDER);
		text.setBounds(65,50,170,20);
		text.setEchoChar('*');
		text.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				label.setForeground(new Color(label.getShell().getDisplay(),0,0,0));
				label.setText("Please insert ADMIN password !");
				if (e.keyCode == SWT.CR) {
					System.out.println("your password:"+text.getText());
					String pass = DisplayMain.readInputFile("/tp/pass.txt");
					if (!pass.isEmpty()) {
						String sarr[] = pass.split("\n");
						pass="";
						for (int i=0;i<1;i++) {
							pass=sarr[i];
						}
						if (pass.compareTo(text.getText())==0) {
							System.out.println("OK");
							flgin=true;
							text.setVisible(false);
							label.setVisible(false);
						}
						else {
							label.setText("Incorrect. Try again !!");System.out.println("wrong");
							label.setForeground(new Color(label.getShell().getDisplay(),255,0,31));
						}
					}
					text.setText("");
      			}
			}
		});
		
		shell.open();
		while (!shell.isDisposed() && (!flgin)) {
			if (!shdisp.getDisplay().readAndDispatch())
				shdisp.getDisplay().sleep();
		}

		return flgin;
	}
}
