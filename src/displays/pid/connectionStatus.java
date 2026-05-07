package displays.pid;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.*;

public class connectionStatus {
	Label lblStatus;
	Label lblClock;
	Font font;
	connectionStatus (Shell shell) {
  	    font = new Font(shdisp.getDisplay(), "Courier", 30, SWT.BOLD);
		shell.setText("Server");
		lblStatus = new Label(shell, SWT.CENTER);
		lblStatus.setText("DISCONNECTED");
		lblStatus.setBounds(30, 30, 500, 40);
		lblStatus.setFont(font);
		lblStatus.setForeground(new Color(lblStatus.getShell().getDisplay(),255,0,31));
		
  	    font = new Font(shdisp.getDisplay(), "Courier", 20, SWT.BOLD);
		lblClock = new Label(shell, SWT.CENTER);
		lblClock.setText("AT "+new DateUtils().now().substring(11));
		lblClock.setBounds(30, 80, 500, 30);
		lblClock.setFont(font);
		lblClock.setForeground(new Color(lblClock.getShell().getDisplay(),255,0,31));

		shell.setBounds(400, 300, 550, 170);
		shell.open();
	}
}
