package setting;


public class Criteria {

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
			} else { //MENIT KOSONG [12:00 -> 11:59:59] direvisi supaya data yang dikirim di menit 00 bisa ditampilkan, misal kirim jam 08.00
//				int newHour = Integer.parseInt(hh)-1;//rev
//				hh = Integer.toString(newHour);//rev
//				if (hh.length()==1) hh = "0"+hh;//rev
//				hhmm = " "+hh+":59:59"; //rev
				hhmm = " "+hh+":00:59";
			}
			Date = YYYY+"-"+MM+"-"+DD+hhmm;
		} else { //JAM KOSONG
			if (!mm.equals("00")) { //MENIT ADA [00:01:59]
				hhmm = " 00:"+mm+":59";
			} else { //MENIT KOSONG [00:00:59]
				hhmm = " 23:59:59";
				if (!MM.equals("00")) {
					if (!DD.equals("00")) { //[juli-31/07-31]
						int newDate = Integer.parseInt(DD);//-1;
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
	
	public void DateToBENER1(String pDateTo) { // digunakan jika settingan field DATE 0000-00-00 00:00
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
	
	
	
	
//	
//	public void tgl() { 
//		java.util.Date tgl = new java.util.Date();
//        long waktujava = tgl.getTime();
//        java.sql.Timestamp stamp = new java.sql.Timestamp(waktujava);
//        
//        String stampp = stamp.toString();
//        tg = stampp.substring(0,10);
//        tanggal = stampp.substring(0,19);
//        
//        String YY = stampp.substring(2, 4);
//        String MM = stampp.substring(5, 7);
//        String DD = stampp.substring(8, 10);
//        String hh = stampp.substring(11, 13);
//        String mm = stampp.substring(14, 16);
//        yymmddhhmm = YY+MM+DD+hh+mm;
//        filing_time = DD+hh+mm;
//        
//        
//    	String YYYY2 = stampp.substring(0, 4);
//    	String MM2 = stampp.substring(5, 7);
//    	String DD2 = stampp.substring(8, 10);
//    		
//    	if (!YYYY2.equals("0000")) { //tahun ada
//    		if (!MM2.equals("00")) { //bulan ada
//    			if (!DD2.equals("00")) { //tanggal ada
//    				dDate = YYYY2+"-"+MM2+"-"+DD2+"%";
//    			} else { //tanggal kosong
//    				dDate = YYYY2+"-"+MM2+"-%";
//    			}
//    		} else { //bulan kosong
//    			dDate = YYYY2+"-%";
//    		}
//   		}
//	}
//	
//	//Date/Time From-To update-20101217
//	public void Date(String pDate) { // digunakan jika settingan field DATE 0000-00-00 00:00
//		Date=pDate;
//		String YYYY = Date.substring(0, 4);
//		String MM = Date.substring(5, 7);
//		String DD = Date.substring(8, 10);
//		String hh = Date.substring(11, 13);
//		String mm = Date.substring(14, 16); // System.out.println("Potongan Date:: "+YYYY + " " + MM + " " + DD + " " + hh + " " + mm);
//		String hhmm;
//		
//		if (YYYY.contains("0000")) YYYY = YYYY.replace("0000", "");
//		if (MM.contains("00")) MM = MM.replace("00", "01");
//		if (DD.contains("00")) DD = DD.replace("00", "01");
//
//		if (!hh.equals("00")) {
//			if (mm.equals("00")) { hhmm = " " + hh + "";}//"%";} 
//			else { hhmm = " " + hh + ":"+ mm; }
//		} else {
//			if (mm.equals("00")) { hhmm = " 00:00:00";}//"%";} 
//			else { hhmm = " 00" + ":" +mm+":00"; }
//		}
//		
//		if (!MM.equals("")) MM="-"+MM; else MM="";
//		if (!DD.equals("")) DD="-"+DD; else DD="";
//		
//		Date = YYYY+MM+DD+hhmm;//hh+mm; // System.out.println("Perubahan Date:: " + Date);
//	}
//
//	public void DateTo(String pDateTo) { // digunakan jika settingan field DATE 0000-00-00 00:00
//		Date=pDateTo;
////		System.err.println("date to>>" + Date);
//		String YYYY = Date.substring(0, 4);
//		String MM = Date.substring(5, 7);
//		String DD = Date.substring(8, 10);
//		String hh = Date.substring(11, 13);
//		String mm = Date.substring(14, 16);
//		String hhmm="";
//		
//		if (!hh.equals("00")) { //JAM ADA
//			if (!mm.equals("00")) { //MENIT ADA [12:36:59]
//				hhmm = " "+hh+":"+mm+":59";
//			} else { //MENIT KOSONG [12:00 -> 11:59:59]
//				int newHour = Integer.parseInt(hh)-1;
//				hh = Integer.toString(newHour);
//				if (hh.length()==1) hh = "0"+hh;
//				hhmm = " "+hh+":59:59"; 
//			}
//			Date = YYYY+"-"+MM+"-"+DD+hhmm;
//		} else { //JAM KOSONG
//			if (!mm.equals("00")) { //MENIT ADA [00:01:59]
//				hhmm = " 00:"+mm+":59";
//			} else { //MENIT KOSONG [00:00:59]
//				hhmm = " 23:59:59";
//				if (!MM.equals("00")) {
//					if (!DD.equals("00")) {
//						int newDate = Integer.parseInt(DD)-1;
//						DD = Integer.toString(newDate);
//						if (DD.length()==1) DD = "0"+DD;
//						
//						if (DD.equals("00")) { //SETELAH DIKURANGI JAM
//							DD="31";
//							int newMonth = Integer.parseInt(MM)-1;
//							MM = Integer.toString(newMonth);
//							if (MM.length()==1) MM = "0"+MM;
//							
//							if (MM.equals("00")) {
//								MM="12";
//								int newYear = Integer.parseInt(YYYY)-1;
//								YYYY = Integer.toString(newYear);
//							}
//						}
//					} else {
//							DD="31";
//					}
//				} else { //BULAN KOSONG
//					MM="12";
//					DD="31";
//				}
//			}
//			Date = YYYY+"-"+MM+"-"+DD+hhmm;
//		}
//	}
}
