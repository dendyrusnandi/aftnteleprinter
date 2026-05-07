package displays.pid;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * This class demonstrates the MessageBox class
 */
public class ShowMessageBox {
  void open(Shell shell,String sTitle,String sMessage) {
	  	int style = 1;

        // Determine which icon was selected and
        // add it to the style
        /*switch (icons.getSelectionIndex()) {
        case 0:
          style |= SWT.ICON_ERROR;
          break;
        case 1:
          style |= SWT.ICON_INFORMATION;
          break;
        case 2:
          style |= SWT.ICON_QUESTION;
          break;
        case 3:
          style |= SWT.ICON_WARNING;
          break;
        case 4:
          style |= SWT.ICON_WORKING;
          break;
        }*/

        // Determine which set of buttons was selected
        // and add it to the style
        /*switch (buttons.getSelectionIndex()) {
        case 0:
          style |= SWT.OK;
          break;
        case 1:
          style |= SWT.OK | SWT.CANCEL;
          break;
        case 2:
          style |= SWT.YES | SWT.NO;
          break;
        case 3:
          style |= SWT.YES | SWT.NO | SWT.CANCEL;
          break;
        case 4:
          style |= SWT.RETRY | SWT.CANCEL;
          break;
        case 5:
          style |= SWT.ABORT | SWT.RETRY | SWT.IGNORE;
          break;
        }
        */

        // Display the message box
        //MessageBox mb = new MessageBox(shell, style);
        MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION);
        mb.setText(sTitle);
        mb.setMessage(sMessage);
        int val = mb.open();
        /*String valString = "";
        switch (val) // val contains the constant of the selected button
        {
        case SWT.OK:
          valString = "SWT.OK";
          break;
        case SWT.CANCEL:
          valString = "SWT.CANCEL";
          break;
        case SWT.YES:
          valString = "SWT.YES";
          break;
        case SWT.NO:
          valString = "SWT.NO";
          break;
        case SWT.RETRY:
          valString = "SWT.RETRY";
          break;
        case SWT.ABORT:
          valString = "SWT.ABORT";
          break;
        case SWT.IGNORE:
          valString = "SWT.IGNORE";
          break;
        }*/
  }
}
