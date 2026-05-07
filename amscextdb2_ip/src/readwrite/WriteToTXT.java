package readwrite;



import java.io.*;

public class WriteToTXT {

	String sData="";
	File newTextFile;
	FileWriter fw;
	
	public void open(String sFname) {
	    try {
	    	newTextFile = new File(sFname);
	    	fw = new FileWriter(sFname);
	    } catch (IOException iox) {
	        iox.printStackTrace();
	    }
	}
	
	public void write(String str) {
	    try {
	    	fw.write(str);
	    } catch (IOException iox) {
	        iox.printStackTrace();
	    }
	}
	
	public void close(){
	    try {
	    	fw.close();
	    } catch (IOException iox) {
	        iox.printStackTrace();
	    }
	}

}
