package org.jfree.chart.demo;

import java.io.*;

public class WriteToAFile {

	String sData="";
	File newTextFile;
	FileWriter fw;
	
	void open (String sFname) {
	    try {
	    	newTextFile = new File(sFname);
	    	fw = new FileWriter(sFname);
	    } catch (IOException iox) {
	        iox.printStackTrace();
	    }
	}
	
	void write(String str) {
	    try {
	    	fw.write(str);
	    } catch (IOException iox) {
	        iox.printStackTrace();
	    }
	}
	
	void close(){
	    try {
	    	fw.close();
	    } catch (IOException iox) {
	        iox.printStackTrace();
	    }
	}

}
