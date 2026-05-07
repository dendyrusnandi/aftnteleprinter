package displays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import readwrite.ReadFromFile;
import setting.Shorten;


public class OpenPassword {
	public boolean flgin;
	Shell shell;
	Label label;
	
	Shorten sh = new Shorten();
	
	public OpenPassword() {
		
	}
	
	public boolean open(Shell pshell) {
		flgin=false;
		
		shell=pshell;
		ReadFromFile.readConfiguration();
		shell.setText("Password");
		shell.setSize(350, 200);
		shell.setLocation(MainForm.setLocX(shell), MainForm.setLocY(shell));
		
		label = new Label(shell, SWT.CENTER);
		label.setText("Please insert password !");
		label.setFont(Shorten.getFont(SWT.ITALIC));
		label.setBounds(0,110,345,25);
		
		final Text text= new Text(shell, SWT.BORDER | SWT.PASSWORD);
		text.setBounds(35,50,270,25);
		text.setFont(Shorten.getFont(SWT.NORMAL));
		text.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				label.setForeground(new Color(label.getShell().getDisplay(),0,0,0));
				label.setText("Please insert password !");
				if (e.keyCode == SWT.CR) {
					System.out.println("password="+text.getText());
					String pass = ReadFromFile.ReadInputFile1("/tp/pass.txt");
					if (!pass.isEmpty()) {
						String sarr[] = pass.split("\n");
						for (int i=0;i<1;i++) {
							pass=sarr[i];
						}
						if (pass.compareTo(text.getText())==0) {
							System.out.println(">> password is [OK]");
							flgin=true;
							text.setVisible(false);
							label.setVisible(false);
						}
						else {
							label.setText("Incorrect. Try again !!"); System.out.println(">> password is [Incorrect]");
							label.setForeground(new Color(label.getShell().getDisplay(),255,0,31));
						}
					}
					text.setText("");
      			}
			}
		});
		
		shell.open();
		while (!shell.isDisposed() && (!flgin)) {
			if (!TeleSplashScreen2016IP.display.readAndDispatch())
				TeleSplashScreen2016IP.display.sleep();
		}

		return flgin;
	}
}
