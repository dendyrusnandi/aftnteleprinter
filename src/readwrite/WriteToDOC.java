package readwrite;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import displays.MainForm;

public class WriteToDOC {

	public WriteToDOC(String flnm, String data) {
		try {
			FileOutputStream fs = new FileOutputStream(MainForm.sPath+flnm+".doc");
			OutputStreamWriter out = new OutputStreamWriter(fs); 
			out.write(data);
			out.close();
		} catch (IOException e){
			System.err.println(e);
		}
	}
}
