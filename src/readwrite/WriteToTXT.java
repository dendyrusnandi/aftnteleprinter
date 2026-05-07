package readwrite;

import java.io.*;

import setting.Shells;

import displays.DialogFactory;
import displays.MainForm;


public class WriteToTXT {
    
	public static void write(String namaFile, String data) {
		String path="";
		if (namaFile.compareToIgnoreCase("stlp.txt")==0) path="/tp/"; 
		else if (namaFile.compareToIgnoreCase("headerpdf.txt")==0) path=MainForm.sFolder;
		else path=MainForm.sPath;
		
		try {
			File newTextFile = new File(path+namaFile);
			FileWriter writer = new FileWriter(newTextFile);
			writer.write(data);
			writer.close();
			
			if (namaFile.compareToIgnoreCase("stlp.txt")==0) 
				DialogFactory.openInfoDialog(Shells.sh_mPrinter, "Printer setting...","Data has been set."); 
				
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}