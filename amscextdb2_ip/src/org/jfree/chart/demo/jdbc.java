package org.jfree.chart.demo;


import java.io.*;
import java.sql.*;

//no one call it
public class jdbc {
	String[] tmp3a = new String[1000];
	String key;
	public jdbc() {
		try {
		      File myFile = new File("/aftn/URL/url.txt");
		      FileReader fileReader = new FileReader(myFile);

		      BufferedReader buffReader = new BufferedReader(fileReader);

		      String line = null;

		      while ((line = buffReader.readLine()) != null) {
		        //System.out.println("DATA:: "+line);
		        parsing(line);
		      }
		      buffReader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void parsing(String line) {
		 String tmp;
		 String[] tmp1 = new String[100];
		 String[] tmp2 = new String[100];
		 String[] data = new String[100];
		 String[] sub = new String[100];
		 
		 //sub = contentStr.split("\n"); //pake ReadInputFile
		 sub = line.split("\n");
		 for (int x = 0; x < sub.length; x++) {
			 data = sub[x].split("=");
	     }

		 for (int x = 0; x < sub.length; x++) {
			 tmp = sub[x];
			 if (tmp.indexOf("=") != -1) {
				 data = tmp.split("=");
				 tmp1[x] = data[0];
				 tmp2[x] = data[1];

				 if (tmp1[x].compareTo("Url") == 0) {
					 tmp3a[0] = tmp2[x]; //System.out.println("URL:::" + tmp3a[0]);
				 } else if (tmp1[x].compareTo("User") == 0) {
					 tmp3a[1] = tmp2[x]; //System.out.println("USER:::" + tmp3a[1]);
				 } else if (tmp1[x].compareTo("Pass") == 0) {
					 tmp3a[2] = tmp2[x]; //System.out.println("PASS:::" + tmp3a[2]);
				 } else if (tmp1[x].compareTo("Url1") == 0) {
					 tmp3a[3] = tmp2[x]; //System.out.println("URL KEDUA:::" + tmp3a[3]);
				 } 
			 }
		 }
	}
//	public String getUrl() { return (tmp3a[0]); }
//	public String getUser() { return (tmp3a[1]);}
//	public String getPass() { return (tmp3a[2]);}
//	public String getUrl1() { return (tmp3a[3]); }
}
