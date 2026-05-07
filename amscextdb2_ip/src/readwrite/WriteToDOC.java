package readwrite;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import actions.RuntimeCmd;

import displays.Lists;
import displays.MainForm;

public class WriteToDOC {

	public WriteToDOC(String sflnm, String data) {
		String flnm = MainForm.path+sflnm+".doc";
		try {
			FileOutputStream fs = new FileOutputStream(flnm);
			OutputStreamWriter out = new OutputStreamWriter(fs); 
			out.write(data);
			out.close();
		} catch (IOException e){
			System.err.println(e);
		}
//		new RuntimeCmd(cmdDOC, MainForm.path+getFlnm(sID)+".doc");
		new RuntimeCmd(Lists.cmdDOC, flnm);
	}
}
