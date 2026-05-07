package setting;

public class Times {
	
	public String tanggal="",datenow="",filing_time="",yearMonth="",datetime="",yymmddhhmm="",dDate="",Date="",tg="",
	DD="",MM="",YY="",YYYY="",hh="",mm="",ss="";
	
	public int iHh13=0,iMM13=0,iHh14=0,iMM14=0,iHh16=0,iMM16=0,iHh17=0,iMM17=0,iHh19=0,iMM19=0,iHh21a=0,iMM21a=0,iHh21d=0,iMM21d=0,iHh=0,iMM=0,iYy=0;
	public String sDof="",
			t15a="", t15a2="", t15a3="", t15a4="", t15a5="", t15b="", t15b2="", t15b3="", t15b4="", t15b5="",
			t14c="", t14c2="", t14c3="", t14c4="", t14c5="", t14d="", t14d2="", t14d3="", t14d4="", t14d5="", t14e="",
			insert="",check="",t13b="",t14b="",t16b="",t17b="",t19a="",
		    s21a="",hh21a="",mm21a="",sHh21a="", sMm21a="",
			s21d="",hh21d="",mm21d="",sHh21d="", sMm21d="",
			s13b="",hh13="",mm13="",sHh13="", sMm13="",
			s14b="",hh14="",mm14="",sHh14="", sMm14="",
			s16b="",hh16="",mm16="",sHh16="", sMm16="",
			s17b="",hh17="",mm17="",sHh17="", sMm17="",
			s19a="",hh19="",mm19="",sHh19="", sMm19="",
			sHh="",sMm="",s="",yy="",sYy="";
	
	
	public Times() {
		
	}
	
	public void tgl() {
		java.util.Date tgl = new java.util.Date();
		long waktujava = tgl.getTime();
		java.sql.Timestamp stamp = new java.sql.Timestamp(waktujava);
		
		String str = stamp.toString();
		tg = str.substring(0,10);
		tanggal = str.substring(0, 19);
		//----------------------------
		YY= str.substring(2, 4);
		YYYY = str.substring(0, 4);
		DD = str.substring(8, 10);
		MM = str.substring(5, 7);
		datenow = YY + MM + DD;
		//----------------------------
        hh = str.substring(11,13);
        mm = str.substring(14,16);
        ss = str.substring(17,19);
        filing_time = DD+hh+mm; 
		//----------------------------
		yearMonth = stamp.toString().substring(0, 7);
		yearMonth = yearMonth.replace("-", "_");
		//----------------------------
        datetime = DD+"/"+MM+"/"+YYYY+" "+hh+":"+mm+":"+ss;
        //----------------------------
        yymmddhhmm = YY+MM+DD+hh+mm;
        String YYYY2 = str.substring(0, 4);
    	String MM2 = str.substring(5, 7);
    	String DD2 = str.substring(8, 10);	
    	if (!YYYY2.equals("0000")) { //tahun ada
    		if (!MM2.equals("00")) { //bulan ada
    			if (!DD2.equals("00")) { //tanggal ada
    				dDate = YYYY2+"-"+MM2+"-"+DD2+"%";
    			} else { //tanggal kosong
    				dDate = YYYY2+"-"+MM2+"-%";
    			}
    		} else { //bulan kosong
    			dDate = YYYY2+"-%";
    		}
   		}	
	}
	
	public void Date(String pDate) { // digunakan jika settingan field DATE 0000-00-00 00:00
		Date=pDate;
		String YYYY = Date.substring(0, 4);
		String MM = Date.substring(5, 7);
		String DD = Date.substring(8, 10);
		String hh = Date.substring(11, 13);
		String mm = Date.substring(14, 16); // System.out.println("Potongan Date:: "+YYYY + " " + MM + " " + DD + " " + hh + " " + mm);
		String hhmm;
		
		if (YYYY.contains("0000")) YYYY = YYYY.replace("0000", "");
		if (MM.contains("00")) MM = MM.replace("00", "01");
		if (DD.contains("00")) DD = DD.replace("00", "01");

		if (!hh.equals("00")) {
			if (mm.equals("00")) { hhmm = " " + hh + "";}//"%";} 
			else { hhmm = " " + hh + ":"+ mm; }
		} else {
			if (mm.equals("00")) { hhmm = " 00:00:00";}//"%";} 
			else { hhmm = " 00" + ":" +mm+":00"; }
		}
		
		if (!MM.equals("")) MM="-"+MM; else MM="";
		if (!DD.equals("")) DD="-"+DD; else DD="";
		
		Date = YYYY+MM+DD+hhmm;//hh+mm; // System.out.println("Perubahan Date:: " + Date);
	}

	public void DateTo(String pDateTo) { // digunakan jika settingan field DATE 0000-00-00 00:00
		Date=pDateTo;
//		System.err.println("date to>>" + Date);
		String YYYY = Date.substring(0, 4);
		String MM = Date.substring(5, 7);
		String DD = Date.substring(8, 10);
		String hh = Date.substring(11, 13);
		String mm = Date.substring(14, 16);
		String hhmm="";
		
		if (!hh.equals("00")) { //JAM ADA
			if (!mm.equals("00")) { //MENIT ADA [12:36:59]
				hhmm = " "+hh+":"+mm+":59";
			} else { //MENIT KOSONG [12:00 -> 11:59:59]
//				int newHour = Integer.parseInt(hh)-1;
//				hh = Integer.toString(newHour);
//				if (hh.length()==1) hh = "0"+hh;
//				hhmm = " "+hh+":59:59"; 
				hhmm = " "+hh+":00:59";
			}
			Date = YYYY+"-"+MM+"-"+DD+hhmm;
		} else { //JAM KOSONG
			if (!mm.equals("00")) { //MENIT ADA [00:01:59]
				hhmm = " 00:"+mm+":59";
			} else { //MENIT KOSONG [00:00:59]
				hhmm = " 23:59:59";
				if (!MM.equals("00")) {
					if (!DD.equals("00")) {
						int newDate = Integer.parseInt(DD)-1;
						DD = Integer.toString(newDate);
						if (DD.length()==1) DD = "0"+DD;
						
						if (DD.equals("00")) { //SETELAH DIKURANGI JAM
							DD="31";
							int newMonth = Integer.parseInt(MM)-1;
							MM = Integer.toString(newMonth);
							if (MM.length()==1) MM = "0"+MM;
							
							if (MM.equals("00")) {
								MM="12";
								int newYear = Integer.parseInt(YYYY)-1;
								YYYY = Integer.toString(newYear);
							}
						}
					} else {
							DD="31";
					}
				} else { //BULAN KOSONG
					MM="12";
					DD="31";
				}
			}
			Date = YYYY+"-"+MM+"-"+DD+hhmm;
		}
	}
	
	public void DateToASLI(String pDateTo) { // digunakan jika settingan field DATE 0000-00-00 00:00
		Date=pDateTo;
//		System.err.println("date to>>" + Date);
		String YYYY = Date.substring(0, 4);
		String MM = Date.substring(5, 7);
		String DD = Date.substring(8, 10);
		String hh = Date.substring(11, 13);
		String mm = Date.substring(14, 16);
		String hhmm="";
		
		if (!hh.equals("00")) { //JAM ADA
			if (!mm.equals("00")) { //MENIT ADA [12:36:59]
				hhmm = " "+hh+":"+mm+":59";
			} else { //MENIT KOSONG [12:00 -> 11:59:59]
				int newHour = Integer.parseInt(hh)-1;
				hh = Integer.toString(newHour);
				if (hh.length()==1) hh = "0"+hh;
				hhmm = " "+hh+":59:59"; 
			}
			Date = YYYY+"-"+MM+"-"+DD+hhmm;
		} else { //JAM KOSONG
			if (!mm.equals("00")) { //MENIT ADA [00:01:59]
				hhmm = " 00:"+mm+":59";
			} else { //MENIT KOSONG [00:00:59]
				hhmm = " 23:59:59";
				if (!MM.equals("00")) {
					if (!DD.equals("00")) {
						int newDate = Integer.parseInt(DD)-1;
						DD = Integer.toString(newDate);
						if (DD.length()==1) DD = "0"+DD;
						
						if (DD.equals("00")) { //SETELAH DIKURANGI JAM
							DD="31";
							int newMonth = Integer.parseInt(MM)-1;
							MM = Integer.toString(newMonth);
							if (MM.length()==1) MM = "0"+MM;
							
							if (MM.equals("00")) {
								MM="12";
								int newYear = Integer.parseInt(YYYY)-1;
								YYYY = Integer.toString(newYear);
							}
						}
					} else {
							DD="31";
					}
				} else { //BULAN KOSONG
					MM="12";
					DD="31";
				}
			}
			Date = YYYY+"-"+MM+"-"+DD+hhmm;
		}
	}
	
	public void t14(String t14c, String t14d) {
    	if (t14c.length()>=4) {
			t14c2 = t14c.substring(1, t14c.length());
			t14c3 = t14c.substring(2, t14c.length());
			t14c4 = t14c.substring(3, t14c.length());
			t14c5 = t14c.substring(4, t14c.length());
		}
		if (t14d.length()>=4) {
			t14d2 = t14d.substring(1, t14d.length());
			t14d3 = t14d.substring(2, t14d.length());
			t14d4 = t14d.substring(3, t14d.length());
			t14d5 = t14d.substring(4, t14d.length());
		}
    }
	
    public void t13b(String t13b) {
		if (t13b == null) t13b="";
		if (t13b.compareTo("") != 0) {
			hh13 = t13b.substring(0,2); if (hh13.equals("00")) { sHh13="00"; }
			mm13 = t13b.substring(2,4); if (mm13.equals("00")) { sMm13="00"; }
			iHh13 = Integer.parseInt(hh13);
    		iMM13 = Integer.parseInt(mm13);
    		
    		sHh13 = Integer.toString(iHh13); if (sHh13.length() == 1) { sHh13="0"+sHh13; } 
    		sMm13 = Integer.toString(iMM13); if (sMm13.length() == 1) { sMm13="0"+sMm13; }
    		s13b=sHh13+sMm13;
		} else s13b="";
		t13b="";
    }
    
    public void t14b(String t14b) {
		if (t14b == null) t14b="";
		if (t14b.compareTo("") != 0) {
			hh14 = t14b.substring(0,2); if (hh14.equals("00")) { sHh14="00"; }
			mm14 = t14b.substring(2,4); if (mm14.equals("00")) { sMm14="00"; }
			iHh14 = Integer.parseInt(hh14);
			iMM14 = Integer.parseInt(mm14);
			
			sHh14 = Integer.toString(iHh14); if (sHh14.length() == 1) { sHh14="0"+sHh14; } 
			sMm14 = Integer.toString(iMM14); if (sMm14.length() == 1) { sMm14="0"+sMm14; }  
			s14b=sHh14+sMm14;
		} else s14b="";
		t14b="";
    }
	
    public void t15(String t15a, String t15b) {
    	if (t15a.length()>=4) {
			t15a2 = t15a.substring(1, t15a.length());
			t15a3 = t15a.substring(2, t15a.length());
			t15a4 = t15a.substring(3, t15a.length());
			t15a5 = t15a.substring(4, t15a.length());
		}
		if (t15b.length()>=4) {
			t15b2 = t15b.substring(1, t15b.length());
			t15b3 = t15b.substring(2, t15b.length());
			t15b4 = t15b.substring(3, t15b.length());
			t15b5 = t15b.substring(4, t15b.length());
		}
    }
    
    public void t16b(String t16b) {
		if (t16b == null) t16b="";
		if (!t16b.equals("")) {
			hh16 = t16b.substring(0,2); if (hh16.equals("00")) { sHh16="00"; }
			mm16 = t16b.substring(2,4); if (mm16.equals("00")) { sMm16="00"; }
			iHh16 = Integer.parseInt(hh16);
			iMM16 = Integer.parseInt(mm16);
			
			sHh16 = Integer.toString(iHh16); if (sHh16.length() == 1) { sHh16="0"+sHh16; } 
			sMm16 = Integer.toString(iMM16); if (sMm16.length() == 1) { sMm16="0"+sMm16; }  
			s16b=sHh16+sMm16;
		} else s16b="";
    }
    
    public void t17b(String t17b) {
		if (t17b == null) t17b="";
		if (t17b.compareTo("") != 0) {
			hh17 = t17b.substring(0,2); if (hh17.equals("00")) { sHh17="00"; }
			mm17 = t17b.substring(2,4); if (mm17.equals("00")) { sMm17="00"; }
			iHh17 = Integer.parseInt(hh17);
			iMM17 = Integer.parseInt(mm17);
	    		
			sHh17 = Integer.toString(iHh17); if (sHh17.length() == 1) { sHh17="0"+sHh17; } 
			sMm17 = Integer.toString(iMM17); if (sMm17.length() == 1) { sMm17="0"+sMm17; }  
			s17b=sHh17+sMm17;
		} else s17b="";
		t17b="";
    }
    
    public void t18b(String t18b) {
    	if (t18b == null) t18b="";
		if (t18b.compareTo("") != 0) {
			yy = t18b.substring(0,2); if (yy.equals("00")) { sYy="00"; }
			hh = t18b.substring(2,4); if (hh.equals("00")) { sHh="00"; }
			mm = t18b.substring(4,6); if (mm.equals("00")) { sMm="00"; }
			iYy = Integer.parseInt(yy);
			iHh = Integer.parseInt(hh);
			iMM = Integer.parseInt(mm);
	    	
			sYy = Integer.toString(iYy); if (sYy.length() == 1) { sYy="0"+sYy; } 
			sHh = Integer.toString(iHh); if (sHh.length() == 1) { sHh="0"+sHh; } 
			sMm = Integer.toString(iMM); if (sMm.length() == 1) { sMm="0"+sMm; }  
			sDof=" DOF/"+sYy+sHh+sMm;
		} else sDof="";
		t18b="";
    }
    
    public void t19a(String t19a) {
		if (t19a == null) t19a="";
		if (t19a.compareTo("") != 0) {
    		hh19 = t19a.substring(0,2); if (hh19.equals("00")) { sHh19="00"; }
			mm19 = t19a.substring(2,4); if (mm19.equals("00")) { sMm19="00"; }
			iHh19 = Integer.parseInt(hh19);
    		iMM19 = Integer.parseInt(mm19);
    		
    		sHh19 = Integer.toString(iHh19); if (sHh19.length() == 1) { sHh19="0"+sHh19; } 
    		sMm19 = Integer.toString(iMM19); if (sMm19.length() == 1) { sMm19="0"+sMm19; }  
    		s19a=sHh19+sMm19;
		} else t19a="";
		t19a="";
    }
    
    public void hhmm(String hhmm) {
		if (hhmm == null) hhmm="";
		if (hhmm.compareTo("") != 0) {
			hh = hhmm.substring(0,2); if (hh.equals("00")) { sHh="00"; }
			mm = hhmm.substring(2,4); if (mm.equals("00")) { sMm="00"; }
			iHh = Integer.parseInt(hh);
    		iMM = Integer.parseInt(mm);
    		
    		sHh = Integer.toString(iHh); if (sHh.length() == 1) { sHh="0"+sHh; } 
    		sMm = Integer.toString(iMM); if (sMm.length() == 1) { sMm="0"+sMm; }
    		s=sHh+sMm;
		} else s="";
		hhmm="";
    }
}
