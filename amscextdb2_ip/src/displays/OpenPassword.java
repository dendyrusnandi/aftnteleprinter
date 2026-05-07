package displays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
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
	Font font = new Font(AmscSplashScreen2.display, new FontData(sh.FONTFACE, sh.NORMAL, sh.NORMAL));
	static ReadFromFile rff = new ReadFromFile();
	
	
	public OpenPassword() {
		
	}
	
	public boolean open(Shell pshell) {
		flgin=false;
		
		shell=pshell;
		rff.readConfiguration();
//		shell.setLocation(rff.getxLocformsearch(), rff.getyLocformsearch());
		shell.setText("Password");
		shell.setSize(340, 170);
		shell.setLocation(MainForm.setLocX(shell), MainForm.setLocY(shell));
		shell.setBackground(sh.DarkGrey);
		
		label = new Label(shell, SWT.CENTER);
		label.setText("Please insert password !");
		label.setFont(font);
		label.setBounds(0,110,345,25);
//		label.setBounds(40,110,220,20);
		
		final Text text= new Text(shell, SWT.BORDER);
		text.setBounds(35,50,270,25);
		text.setFont(font);
		text.setEchoChar('*');
		text.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
		
			public void keyReleased(KeyEvent e) {
				label.setForeground(new Color(label.getShell().getDisplay(),0,0,0));
				label.setText("Please insert password !");
				if (e.keyCode == SWT.CR) {
					System.out.println("password="+text.getText());
					String pass = rff.ReadInputFile1("/tp/pass.txt");
					if (!pass.isEmpty()) {
						String sarr[] = pass.split("\n");
//						pass="";
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
			if (!AmscSplashScreen2.display.readAndDispatch())
				AmscSplashScreen2.display.sleep();
		}

//		System.out.println("disini:" + flgin + "@");
		return flgin;
	}
}
