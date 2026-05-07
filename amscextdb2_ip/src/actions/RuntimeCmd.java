package actions;

import java.io.IOException;

import displays.AmscSplashScreen2;


public class RuntimeCmd {

	int max=1000;
	
	public RuntimeCmd(final String cmd, final String param) {
		System.out.println("Command:" + cmd + param + "#");
		
		final cProgressBar progbr = new cProgressBar("loading ...");
		new Thread() {
			public void run() {
				if (AmscSplashScreen2.display.isDisposed()) return;
				AmscSplashScreen2.display.asyncExec(new Runnable() {
					public void run() {
						progbr.create(max);
						int rowNo = 0;
						for (int i=0; i<=max;i++) {
							rowNo++;
							progbr.bar.setSelection(rowNo);
						} //end of while
						progbr.finish();
							
						try {
							Runtime.getRuntime().exec(cmd+param);
						} catch (IOException ioe) {
							System.err.println("Runtime info:" + ioe.getMessage());
						}
					}
				});
			}
		}.start();
		progbr.begin();
		
//		new BusyCursorSwt("Exporting");
//		//asli
//		try {
//			Runtime.getRuntime().exec(cmd+param);
//		} catch (IOException ioe) {
//			System.err.println("Runtime info:" + ioe.getMessage());
//		}
	}
}
