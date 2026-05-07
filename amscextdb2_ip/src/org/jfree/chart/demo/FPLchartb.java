//update: 231211
package org.jfree.chart.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

import displays.AmscSplashScreen2;

//import com.isode.xuxa.desktop.XuxaMain;

public class FPLchartb{
    Connection conn;
    Statement stmt;
	CategoryDataset dataset;
	JFreeChart chart;
	String[] Month = new String[]{"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
	List listX,listY;
	int i_arcft,iMaxArcft=100,iMaxX=31,iCntPars;
	String s_atrct[] = new String[6];
    int i_dt1[][] = new int[iMaxArcft][32];
    String s_time="",sQuery;
    int nCnt,iMaxDate,iMaxDateb;
    String sTitleX="",sTitleY="",series1="",sTitleChart="";
    String sSeriesNm[] = new String[iMaxArcft];
    String T_year="",C_month="",C_monthb="",sDateList="";
    String sType="";
    //String sQueryCrt="";
    String sGroupBy="";
 	String sTypemsg="",sTypemsgY="",sTypemsgb="",sTypeVal="",sMainkey,sMainkey2,sQuerymnth;
 	String sRes7a[] = new String[iMaxArcft];
 	int typChart;
 	ApplicationFrame AF;
 	PieChart3D pie;
 	String sSumCnt;
 	boolean flgmonthkey,flgtypkey,flgyearkey;
	boolean flgtyp19b;
	boolean flgtyp15c;
	String sType15c;
	public void FPLcreate(Connection conn, Statement stmt,ApplicationFrame AF,final String title,List listX,int typChart,List listY) {
		this.typChart=typChart;
		//ApplicationFrame b = new ApplicationFrame("");//super(title);
		if (typChart==2) {
			if (pie!=null) pie.close();
			pie = new PieChart3D("ATS Pie Chart");
		}
		this.listX=listX;
		this.listY=listY;
		this.conn=conn;
		this.stmt=stmt;
		this.AF = AF;
    	if (chart()) {
	        String ext = "jpg";
	        File file = new File(criteria.g_sFile + "." + ext);
	        try {
	        	System.out.println("write "+criteria.g_sFile+" chart:"+chart);
	        	if (chart!=null) ImageIO.write(chart.createBufferedImage(1500, 1000), ext, file);  // ignore returned boolean
	        } catch(IOException e) {
	            System.out.println("Write error for " + file.getPath() +
	                               ": " + e.getMessage());
	             Shell sh = new Shell();
	             MessageBox mg = new MessageBox(sh);
	             mg.setMessage(e.getMessage());
	             mg.open();
	        }
	
	        if (typChart==1) {
	        	final ChartPanel chartPanel = new ChartPanel(chart);
	        	chartPanel.setPreferredSize(new Dimension(1200, 600));
		        AF.setContentPane(chartPanel);
		        AF.pack();
		        RefineryUtilities.centerFrameOnScreen(AF);
		        AF.setVisible(true);
		        AF.setLocation(20, 60);
	        }
	        else if (typChart==2){
    			pie.pack();
    			RefineryUtilities.centerFrameOnScreen(pie);
    	        pie.setVisible(true);
	        }
    	}
	}

	public CategoryDataset createDataset(String sArr[]) {
        // column keys...
        String category[] = new String[32];
      
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        System.out.println("max:"+i_arcft);
        for (int iCntb=0;iCntb<i_arcft;iCntb++) {
    		for (int iCnt=1;iCnt<iMaxDate;iCnt++) {
    			if (!sArr[iCnt-1].contains("-")) {
    				category[iCnt-1] = sArr[iCnt-1];
    				if (flgmonthkey) category[iCnt-1]="";
    				dataset.addValue(i_dt1[iCntb][iCnt-1], sSeriesNm[iCntb], category[iCnt-1]);
    				System.out.println("i_dt1["+iCntb+"]["+(iCnt-1)+"]:"+i_dt1[iCntb][iCnt-1]);
    			}
    			else {
        			String sStrip[] = sArr[iCnt-1].split("-");
        	    	//System.out.println("iMaxDatebStrip2:"+iMaxDateb);
        	    	//System.out.println("sStrip2[0]:"+sStrip[0]);
        	    	//System.out.println("sStrip2[1]:"+sStrip[1]);
    	        	
        	    	for (int iL=Integer.parseInt(sStrip[0]);iL<Integer.parseInt(sStrip[0])+iMaxDateb;iL++) {
	        			//iDate = iL;
	        			//subQuery(iCnt,iCntPars,iDate,stmt);
	    				category[iL] = Integer.toString(iL);
	    				dataset.addValue(i_dt1[iCntb][iL], sSeriesNm[iCntb], category[iL]);
        	    	}
    			}
    			if (flgmonthkey) 
    				break;
        	}
        }
//        String space="";
//        for (int iCntb=i_arcft;iCntb<6;iCntb++) {
//        	space+=" ";
//        	dataset.addValue(0," ", space);
//        }
        return dataset;
    }

	public CategoryDataset createDatasetM(String sArr[]) {
        // column keys...
        String category[] = new String[32];
      
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        System.out.println("no date max:"+i_arcft);
        for (int iCntb=0;iCntb<i_arcft;iCntb++) {
    		for (int iCnt=1;iCnt<iMaxDate;iCnt++) {
    			if (!sArr[iCnt-1].contains("-")) {
    				category[iCnt-1] = sArr[iCnt-1];//Integer.toString(iCnt);
    				//if (flgmonthkey) 
    				category[iCnt-1]="";
    				dataset.addValue(i_dt1[iCntb][iCnt-1], sSeriesNm[iCntb], category[iCnt-1]);
    				System.out.println("i_dt1["+iCntb+"]["+(iCnt-1)+"]:"+i_dt1[iCntb][iCnt-1]+" "+sSeriesNm[iCntb]+" "+category[iCnt-1]);
    			}
    			else {
        			String sStrip[] = sArr[iCnt-1].split("-");
        			int iMontha=0,iMonthb=0;
        			for (int iCn=0;iCn<12;iCn++) {
        	    		if (sStrip[0].compareTo(Month[iCn])==0) {
        	    			iMontha=iCn+1;break;
        	    		} 
        	    	}
        			for (int iCn=0;iCn<12;iCn++) {
        	    		if (sStrip[1].compareTo(Month[iCn])==0) {
        	    			iMonthb=iCn+1;break;
        	    		} 
        	    	}
        			//System.out.println("iMontha:"+iMontha+" iMonthb:"+iMonthb);
        			iMaxDateb = iMonthb-iMontha+1;
        	    	for (int iL=iMontha;iL<=iMaxDateb;iL++) {
	    				category[iL] = Month[iL-1];
	    				dataset.addValue(i_dt1[iCntb][iL], sSeriesNm[iCntb], category[iL]);
        	    	}
    			}
    			break;
    			//if (flgmonthkey) 
    				//break;
        	}
			//if (!flgmonthkey) 
				//break;
        }
//        String space="";
//        for (int iCntb=i_arcft;iCntb<6;iCntb++) {
//        	space+=" ";
//        	dataset.addValue(0," ", space);
//        }

        return dataset;
    }

	public JFreeChart createChart(final CategoryDataset dataset) {
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            "",       // chart title
            sTitleChart,               // domain axis label
            sTitleY,                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                    // include legend
            true,                     // tooltips?
            false                     // URLs?
        );
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);
        
        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.red);
        
//        final IntervalMarker target = new IntervalMarker(4.5, 7.5);
//        target.setLabel("Target Range");
//        target.setLabelFont(new Font("SansSerif", Font.ITALIC, 11));
//        target.setLabelAnchor(RectangleAnchor.LEFT);
//        target.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
//        target.setPaint(new Color(222, 222, 255, 128));
//        plot.addRangeMarker(target, Layer.BACKGROUND);
        
        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(true);
        renderer.setItemMargin(0.10);
        renderer.setMaxBarWidth(0.1);
        
        // set up gradient paints for series...
        Color cl[] = new Color[13];
        cl[0] = Color.black;
        cl[1] = Color.blue;
        cl[2] = Color.cyan;
        cl[3] = Color.green;
        cl[4] = Color.lightGray;
        cl[5] = Color.magenta;
        cl[6] = Color.orange;
        cl[7] = Color.pink;
        cl[8] = Color.red;
        cl[9] = Color.yellow;
        
        for (int iLoop=i_arcft;iLoop<6;iLoop++) 
        	cl[iLoop] = cl[i_arcft-1];

        //        for (int iLp=0;iLp<13;iLp++) {
//        	cl[iLp] = Color.getHSBColor(iLp+50, iLp+100, iLp+150);
//        }
        
        for (int iLp=0;iLp<10;iLp++) {
        	final GradientPaint gp0 = new GradientPaint(
        			0.0f, 0.0f, cl[iLp], 
        			0.0f, 0.0f, Color.lightGray
        	);
            renderer.setSeriesPaint(iLp, gp0);
        }
        //renderer.setLabelGenerator(new BarChartDemo7.LabelGenerator());
        renderer.setItemLabelsVisible(true);
        final ItemLabelPosition p = new ItemLabelPosition(
            ItemLabelAnchor.INSIDE12, TextAnchor.CENTER_LEFT,//.CENTER_RIGHT, 
            TextAnchor.CENTER_LEFT,
            //.CENTER_RIGHT, 
            -Math.PI / 2.0
        );
        renderer.setPositiveItemLabelPosition(p);

        final ItemLabelPosition p2 = new ItemLabelPosition(
            ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT, 
            TextAnchor.CENTER_LEFT, -Math.PI / 2.0
        );
        renderer.setPositiveItemLabelPositionFallback(p2);
        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        // OPTIONAL CUSTOMISATION COMPLETED.
        s_time=sTitleChart;
        
        //if (!s_time.isEmpty()) chart.setTitle(s_time);
        return chart;
    }
    
	public boolean chart(){
        boolean b_ret=false;
       	for (int iCntb=0;iCntb<iMaxArcft;iCntb++) {
         	for (int iCnt=0;iCnt<iMaxX;iCnt++) {
         		i_dt1[iCntb][iCnt] = 0;
         	}
       	}
       	if (listX!=null) {
            if (parseARCFT()) b_ret=true;
       	}
        return b_ret;
    }
     
	public boolean parseARCFT(){
     	boolean flg=true;
        
        //get message type
     	boolean ok=false;
		flgtyp19b=false;
		flgtyp15c=false;
     	flgmonthkey=false;flgyearkey=false;
     	flgtypkey=false;
     	sTitleY = "Value";
     	sTitleChart="";sTypemsg="";sTypeVal="";T_year="";C_month="";sDateList="";
     	sMainkey="";sMainkey2="";

     	String sTypeMaster[] = {"type7a","type8a","type10a","type13a","type13b","type15a","type15b","type15c","type16a","type19b"};

     	for (int i=0;i<listX.getItemCount();i++) {
     		String a = listX.getItem(i);
			if (a.contains("*")) a=a.substring(1,a.length());
//			String s2[] = a.split(":");
//			if (s2.length==1) {
//				if (find(criteria.sFilename,s2[0]).compareTo("type19b")==0) {
//					flgtyp19b=true;
//					sSumCnt="sum(type19b)";
//				}
//			}
//			else if (find(criteria.sFilename,s2[0]).compareTo("type15c")==0) {
//				flgtyp15c=true;
//				sSumCnt="count(type15c)";
//				sType15c=a;
//			}
			System.out.println("sType15c:"+sType15c);
        	if (!flgtyp19b) {
        		sSumCnt="count(*)";
				System.out.println("listX:"+listX.getItem(i));
	        	String sX[] = listX.getItem(i).split(":");
	        	if (sX.length==1) {
	        		String ak = listX.getItem(i);
	        		listX.setItem(i,ak+" ");
	        		sX = listX.getItem(i).split(":");
	        		sX[1]="";
	        	}
	        	if (!ok) {
	        		String sXtemp = sX[0];
	        		if (sX[0].contains(criteria.sKey)) 
	        			sX[0] = sX[0].substring(1,sX[0].length());
	        		sTypemsg = find(criteria.sFilename,sX[0]);
	        		sX[0]=sXtemp;
	        	}
	        	else {
	        		String aSx = sX[0];
	        		if (aSx.contains(criteria.sKey)) aSx=aSx.substring(1,aSx.length()); 
	        		sTypemsgb = find(criteria.sFilename,aSx);
	        	}
	        	System.out.println("sX[0]:"+sX[0]); 
	        	if (ok) {
	        		if ((sX[0].compareTo("*Month")!=0) && (sX[0].compareTo("*Year")!=0) && (!sX[0].contains(criteria.sKey))) 
	        			if ((sX[1].compareTo(" ")==0) || (sX[1].isEmpty()))
	        				sTitleChart +=sX[0]+":"+"ALL"+" ";
	        			else sTitleChart +=sX[0]+":"+sX[1]+" ";
	        	}
	        	System.out.println("sTypemsgb:"+sTypemsgb);
	        	if (sTypemsg.contains("type3a")) {
	        		ok=true;
	        		sTypemsg=sX[0];
	        		sTypeVal=sX[1];
	        		if (sX[0].startsWith(criteria.sKey+"Msg")) sTypeVal="";
	        		sTitleChart +=sTypeVal+" Chart, ";
	        	}
	        	//else if (sTypemsgb.compareTo("type7a")==0) sType7a=sX[0]+":"+sX[1]; 
	     		else if (sTypemsgb.startsWith("year")) T_year = sX[1];
	        	else if (sTypemsgb.startsWith("month")) C_month = sX[1];
	        	else if (sTypemsgb.startsWith("date")) sDateList = sX[1];
	        	if (sX[0].contains(criteria.sKey)) {
	        		String sX2 = sX[0].substring(1, sX[0].length());
	        		sX[0]=sX2;
	        		sMainkey=sX[0]+":"+sX[1];
	        		sMainkey2 = find(criteria.sFilename,sX[0]);
	        		System.out.println("sFind:"+sMainkey2);
	        		if (sX[0].startsWith("Month")) {
	        			C_month = sX[1];
	        			flgmonthkey=true;
	        		}
	        		if (sX[0].startsWith("Year")) {
	        			flgyearkey=true;
	        		}
	        		else if (sX[0].startsWith("Msg Type")) {
	        			flgtypkey=true;
	        		}
	        	}
        	}
        }
     	if ((listY!=null) && (typChart!=2)){
         	for (int iii=0;iii<listY.getItemCount();iii++) {
             	for (int ii=0;ii<sTypeMaster.length;ii++) {
             		if (find(criteria.sFilename,listY.getItem(iii)).compareTo(sTypeMaster[ii])==0) {
             			flgtyp19b=true;
             			if (sTypeMaster[ii].compareTo("type19b")==0)
             				sSumCnt="sum("+sTypeMaster[ii]+")";
             			else sSumCnt="count("+sTypeMaster[ii]+")";
             			sTitleY+="  ("+listY.getItem(iii)+")";
             			break;
             		}
             	}
         	}
     	}
		//if (flgtyp19b) 	sTitleChart+="("+listY.getItem(0)+")";
		System.out.println("flgtyp19b:"+flgtyp19b);
     	C_monthb=C_month;
    	System.out.println("Year:"+T_year+" Month:"+C_month+" Date:"+sDateList);
    	System.out.println("sMainkey:"+sMainkey);
    	sQuerymnth="";
    	
    	C_month=getcomma(C_month);
    	T_year=getcommayear(T_year);
    	C_monthb=C_month;
    	
    	
    	String sMainPeriode="";
    	if ((!flgmonthkey) && (!flgyearkey) && (!flgtypkey)) {
    		if (sMainkey.contains("-")) {
	    		String sK[] = sMainkey.split(":");
	    		if (sK.length>1) {
		    		String sTy[] = sTypeVal.split(",");
		    		String sTyp = "";
		    		for (int iLty=0;iLty<sTy.length;iLty++) {
		    			if (iLty!=0) sTyp += " or ";
		    			sTyp += "type3a = '"+sTy[iLty]+"'";
		    		}

		    		String sKs[] = sK[1].split(",");
		    		for (int isKs=0;isKs<sKs.length;isKs++) {
		    				if (isKs!=0) sMainPeriode+=",";
		    				if (sKs[isKs].contains("-")) {
		    					String sKs2[] = sKs[isKs].split("-");
		    					if (sKs2.length>1) {
					    		String sFind = find(criteria.sFilename,sK[0]);
					    		String sTable = get_table();
					    		System.out.println("******************************sTable:"+sTable);
					    		String sQueryp = "select "+sFind+" from "+sTable+" where ("+sTyp+") and "+sFind+" >= '"+sKs2[0]+"' and "+sFind+" <='"+sKs2[1]+"' "+"group by "+sFind;
					    		
					    		System.out.println("**sQueryp:"+sQueryp);
					        	try {
					        		ResultSet resultSet = stmt.executeQuery(sQueryp);
					        		int i=0;
					        		while (resultSet.next()) {
					        			if (resultSet.getString(sFind)!=null) {
					        				if (i!=0) sMainPeriode+=",";
					        				sMainPeriode+=resultSet.getString(sFind);
					        				i++;
					        			}
					        		}
					        	} catch (SQLException se) {
					        		se.printStackTrace();
					                Shell sh = new Shell();
					                MessageBox mg = new MessageBox(sh);
					                mg.setMessage(se.getMessage());
					                mg.open();
					        	}
		    					}
		    				}
		    				else {
		    					sMainPeriode+=sKs[isKs];
		    				}
		    			}
	    		}
    		}
    	}
    	
    	sQuerymnth="";
    	System.out.println("Year:"+T_year+" Month:"+C_month+" Date:"+sDateList);
     	if (ok) {
	        if (!sMainkey.isEmpty()) {
		        String sPars7a[] = sMainkey.split(":");
		        if (sPars7a.length>1) {
		        	System.out.println("***sPars7a[0]:"+sPars7a[0]);
		        	if (sPars7a[1]!=null) {
		        		if (sPars7a[0].startsWith("Month")) {
		        			sRes7a = C_month.split(",");
		        		}
		        		else if (sPars7a[0].startsWith("Year")) {
		        			sRes7a = T_year.split(",");
		        		}
		        		else if (!sMainPeriode.isEmpty()) {
		        			sRes7a = sMainPeriode.split(",");
		        		}
		        		else {
		        			sRes7a = sPars7a[1].split(",");
		        		}
	        			i_arcft = sRes7a.length;
		        	}
		        }
		        else {sRes7a[0]="";i_arcft=0;}
		        //if (i_arcft>5) i_arcft=iMaxArcft;
	        }
	        else {i_arcft=1;sSeriesNm[0]="All of Aircraft";sRes7a[0]=null;}
	        System.out.println("n aircraft:"+i_arcft);
	        
	        //NO KEY,,MONTH add query
	        if ((!flgmonthkey) && (!flgyearkey)) {
				System.out.println("C_monthb:"+C_monthb);
        		sTitleX = "Month";
    			String sDateArr[] = C_monthb.split(",");
    			iMaxDate = sDateArr.length+1;
	        	int iDate=-1;
	        	int iCnt;
	        	for (iCnt=1;iCnt<iMaxDate;iCnt++) {
	        		if (!sDateArr[iCnt-1].contains("-")) {
	        			//iDate = Integer.parseInt(sDateArr[iCnt-1]);
	        			iDate=-1;
	        			C_month = sDateArr[iCnt-1];
	        			System.out.println("C_month:"+C_month);
	        			String sYear[] = T_year.split(",");
	        			for (int iLp=0;iLp<sYear.length;iLp++) {
	        				if (iLp!=0) sQuerymnth+="or ";
	        				counttm(sYear[iLp]);
	        			}
				    	if ((!sQuerymnth.isEmpty()) && (iCnt!=iMaxDate-1)) 
				    		sQuerymnth+="or ";
	        		}
	        		else {
	        			String sStrip[] = sDateArr[iCnt-1].split("-");
	        			int iMontha=0,iMonthb=0;
	        			for (int iCn=0;iCn<12;iCn++) {
	        	    		if (sStrip[0].compareTo(Month[iCn])==0) {
	        	    			iMontha=iCn+1;break;
	        	    		} 
	        	    	}
	        			for (int iCn=0;iCn<12;iCn++) {
	        	    		if (sStrip[1].compareTo(Month[iCn])==0) {
	        	    			iMonthb=iCn+1;break;
	        	    		} 
	        	    	}
	        			//System.out.println("iMontha:"+iMontha+" iMonthb:"+iMonthb);
	        			iMaxDateb = iMonthb-iMontha+1;
	        	    	for (int iL=iMontha;iL<=iMaxDateb;iL++) {
    	        			iDate = -1;//iL;
    	        			C_month = Month[iL-1];
    	        			String sYear[] = T_year.split(",");
    	        			for (int iLp=0;iLp<sYear.length;iLp++) {
    	        				if (iLp!=0) sQuerymnth+="or ";
    	        				counttm(sYear[iLp]);
    	        			}
    				    	if ((!sQuerymnth.isEmpty()) && (iL!=iMaxDateb-1)) 
    				    		sQuerymnth+="or ";
	    	        	}
	        		}
		        }
	        	//System.out.println("sQuerymnth:"+sQuerymnth);
	        }
	        else {
	        	for (iCntPars=0;iCntPars<i_arcft;iCntPars++) {
	        		sSeriesNm[iCntPars] = sRes7a[iCntPars];
	        	}
	        }
        	//	
        	
	        
			final cProgressBar progbr = new cProgressBar("");
			new Thread() {
    			public void run() {
    				if (AmscSplashScreen2.display.isDisposed()) return;
					AmscSplashScreen2.display.asyncExec(new Runnable() {
	 					public void run() {
	 						progbr.create(i_arcft);
					        for (iCntPars=0;iCntPars<i_arcft;iCntPars++) {
								if (sRes7a[iCntPars]!=null) progbr.sTitleB = "Creating "+sRes7a[iCntPars] +"...";
								else progbr.sTitleB = "Please wait...";
								System.out.println("sRes7a[iCntPars]"+sRes7a[iCntPars]);
								System.out.println("flgtyp19b:"+flgtyp19b);
					        	System.out.println("iCntPars:"+iCntPars);
					        	if ((sRes7a[iCntPars]!=null) && (!flgmonthkey)) sSeriesNm[iCntPars] = sRes7a[iCntPars];
					        	iMaxDate=32;
								System.out.println("C_monthbZ:"+C_monthb);
					        	/*if (!sDateList.isEmpty()) {
								System.out.println("sDateList:"+sDateList);
				        		sTitleX = "Date";
				        		if (!C_monthb.isEmpty()) {
				        			String sC_monthb[] = C_monthb.split(",");
				        			if (sC_monthb[0].contains("-")) sC_monthb = sC_monthb[0].split("-");
				        			C_month = sC_monthb[0];
				        		}
			        			final String sDateArr[] = sDateList.split(",");
			        			iMaxDate = sDateArr.length+1;
	        	
 	    			        	int iCnt;
 	    	    	        	int iDate=-1;
 	    						progbr.create(31);
			    	        	for (iCnt=1;iCnt<iMaxDate;iCnt++) {
			    	        		//System.out.println("sDateArr[iCnt-1]:"+sDateArr[iCnt-1]);
			    	    			progbr.bar.setSelection(iCnt);
			    	    			if (!sDateArr[iCnt-1].contains("-")) {
			    	        			iDate = Integer.parseInt(sDateArr[iCnt-1]);
			    	        			subQuery(iCnt,iCntPars,iDate,stmt);
			    	        		}
			    	        		else {
			    	        			String sStrip[] = sDateArr[iCnt-1].split("-");
			    	        			iMaxDateb = (Integer.parseInt(sStrip[1]) - Integer.parseInt(sStrip[0])) + 1;
			    	        			//System.out.println("From:"+Integer.parseInt(sStrip[0])+" iMaxDateb:"+iMaxDateb);
			    	        	    	for (int iL=Integer.parseInt(sStrip[0]);iL<Integer.parseInt(sStrip[0])+iMaxDateb;iL++) {
				    	        			iDate = iL;
				    	        			subQuery(iL+1,iCntPars,iDate,stmt);
				    	        			progbr.bar.setSelection(iCnt+iL);
			    	    	        	}
			    	        		}
			    		        }
			    	        	if (iCntPars==i_arcft-1) {
			    	        		dataset = createDataset(sDateArr);
			    	        		chart = createChart(dataset);
			    	        	}
			    	        	progbr.bar.setSelection(31);
							    progbr.finish();
	 	    				}
				        	else*/ if (!C_month.isEmpty()) {
								System.out.println("C_monthb:"+C_monthb);
				        		sTitleX = "Month";
			        			String sDateArr[] = C_monthb.split(",");
			        			//iMaxDate = sDateArr.length+1;
			    	        	int iDate=-1;
					        	int iCnt;
				        		if ((flgmonthkey) || (flgyearkey)){
				        		
				        		sDateArr = T_year.split("=");
	    	        			C_month = sRes7a[iCntPars];
	    	        			System.out.println("C_month:"+C_month);
	    	        			subQuery(1,iCntPars,iDate,stmt);
			    	        	/*for (iCnt=1;iCnt<iMaxDate;iCnt++) {
		    	        			progbr.bar.setSelection(iCnt);
			    	        		if (!sDateArr[iCnt-1].contains("-")) {
			    	        			//iDate = Integer.parseInt(sDateArr[iCnt-1]);
			    	        			iDate=-1;
			    	        			C_month = sDateArr[iCnt-1];
			    	        			System.out.println("C_month:"+C_month);
			    	        			subQuery(iCnt,iCntPars,iDate,stmt);
			    	        		}
			    	        		else {
			    	        			String sStrip[] = sDateArr[iCnt-1].split("-");
			    	        			int iMontha=0,iMonthb=0;
			    	        			for (int iCn=0;iCn<12;iCn++) {
			    	        	    		if (sStrip[0].compareTo(Month[iCn])==0) {
			    	        	    			iMontha=iCn+1;break;
			    	        	    		} 
			    	        	    	}
			    	        			for (int iCn=0;iCn<12;iCn++) {
			    	        	    		if (sStrip[1].compareTo(Month[iCn])==0) {
			    	        	    			iMonthb=iCn+1;break;
			    	        	    		} 
			    	        	    	}
			    	        			//System.out.println("iMontha:"+iMontha+" iMonthb:"+iMonthb);
			    	        			iMaxDateb = iMonthb-iMontha+1;
			    	        	    	for (int iL=iMontha;iL<=iMaxDateb;iL++) {
				    	        			iDate = -1;//iL;
				    	        			C_month = Month[iL-1];
				    	        			subQuery(iL+1,iCntPars,iDate,stmt);
				    	        			progbr.bar.setSelection(iCnt+iL);
			    	    	        	}
			    	        		}
			    		        }*/
				        		}
			    	        	else {
				        			sDateArr = T_year.split("=");
			    	        		subQuery(1,iCntPars,iDate,stmt);
			    	        	}

			    	        	if (iCntPars==i_arcft-1) {
			    	        		//subQuery(iL+1,iCntPars,iDate,stmt);
			    	        		System.out.println("Creating chart "+typChart+"..."+sDateArr.length);
			    	        		if (typChart==1) {
			    	        			dataset = createDatasetM(sDateArr);
			    	        			chart = createChart(dataset);
			    	        		}
			    	        		else if (typChart==2) {
			    	        			//pie = new PieChart3D("ATS Pie Chart");
			    	        			String sArr[] = sDateArr;
			    	        	        for (int iCntb=0;iCntb<i_arcft;iCntb++) {
						    	        	for (iCnt=1;iCnt<iMaxDate;iCnt++) {
						    	        		System.out.println("1:"+iCnt+" 2:"+iCntb+" 3:"+iMaxDate);
			    	    	    				System.out.println("iMaxDate:"+iMaxDate+" iCntb:"+iCntb+" iCnt:"+(iCnt-1)+" "+sSeriesNm[iCntb]+ " "+ i_dt1[iCntb][iCnt-1]+" sDateArr["+(iCnt-1)+"]:"+sDateArr[iCnt-1]);
						    	        		if (i_arcft==1) {
							    	        		if (sArr[iCnt-1].contains("-")) {
						    	            			String sStrip[] = sArr[iCnt-1].split("-");
						    	            			int iMontha=0,iMonthb=0;
						    	            			for (int iCn=0;iCn<12;iCn++) {
						    	            	    		if (sStrip[0].compareTo(Month[iCn])==0) {
						    	            	    			iMontha=iCn+1;break;
						    	            	    		} 
						    	            	    	}
						    	            			for (int iCn=0;iCn<12;iCn++) {
						    	            	    		if (sStrip[1].compareTo(Month[iCn])==0) {
						    	            	    			iMonthb=iCn+1;break;
						    	            	    		} 
						    	            	    	}
						    	            			System.out.println("iMontha:"+iMontha+" iMonthb:"+iMonthb);
						    	            			iMaxDateb = (iMonthb-iMontha)+1;
						    	            			String sSeriesNmS[] = new String[iMaxDateb+iMontha+1];
						    	            	    	for (int iL=iMontha;iL<iMaxDateb+iMontha;iL++) {
						    	            	    		sSeriesNmS[iL] = Month[iL-1];
						    	            	    		System.out.println("pie:"+sSeriesNmS[iL]+ " val:"+ i_dt1[iCntb][iL]);
						    	    	    				pie.data.setValue(sSeriesNmS[iL], new Double(i_dt1[iCntb][iL]));
						    	            	    	}
							    	        		}
				    	    	    				else pie.data.setValue(sDateArr[iCnt-1], new Double(i_dt1[iCntb][iCnt-1]));
						    	        		}
						    	        		else pie.data.setValue(sSeriesNm[iCntb], new Double(i_dt1[iCntb][iCnt-1]));
						    	        		if (i_arcft>=1) break;
			    	        	        	}
			    	        	        }
			    	        	        pie.chart = chart;
			    	        			pie.create(sTitleChart);
			    	        			chart = pie.chart;
			    	        			System.out.println("Chart:"+chart+" pie.chart"+pie.chart);
			    	        		}
			    	        	}
			    	        	progbr.bar.setSelection(iCntPars);
							    if (iCntPars>=i_arcft-1) progbr.finish();
        					}
				        	//if (flgmonthkey) break;
			        	}
 					}});
    			}
	 	    }.start();
	 	    progbr.begin();
     	 }
         return flg;
    }
    
	public String getcomma(String sData){
		String sRet="";
		String s[] = sData.split(",");
		for (int i=0;i<s.length;i++) {
			System.out.println("s:"+i+":"+s[i]);
			if (i!=0) sRet+=",";
			if (s[i].contains("-")) {
    			String sStrip[] = s[i].split("-");
    			int iMontha=0,iMonthb=0;
    			for (int iCn=0;iCn<12;iCn++) {
    	    		if (sStrip[0].compareTo(Month[iCn])==0) {
    	    			iMontha=iCn+1;break;
    	    		} 
    	    	}
    			for (int iCn=0;iCn<12;iCn++) {
    	    		if (sStrip[1].compareTo(Month[iCn])==0) {
    	    			iMonthb=iCn+1;break;
    	    		} 
    	    	}
    			int iMaxDatec = iMonthb;
    	    	for (int iL=iMontha;iL<=iMaxDatec;iL++) {
    	    		System.out.println("s:"+Month[iL-1]);
    	    		if (iL!=iMontha) sRet+=",";
    	    		sRet+=Month[iL-1];
	        	}
			}
			else sRet+=s[i]; 
		}
		return sRet;
	}
	
	public String getcommayear(String sData){
		String sRet="";
		String s[] = sData.split(",");
		for (int i=0;i<s.length;i++) {
			System.out.println("s:"+i+":"+s[i]);
			if (i!=0) sRet+=",";
			if (s[i].contains("-")) {
    			String sStrip[] = s[i].split("-");
    			int iYear=0,iYearb=0;
    			if(sStrip.length>1) {
    				iYear=Integer.parseInt(sStrip[0]);
    				iYearb=Integer.parseInt(sStrip[1]);
    			}
    			int iMaxDatec = iYearb-iYear;
    			System.out.println("iYearb:"+iYearb+":"+iYear);
    	    	for (int iL=iYear;iL<=iMaxDatec+iYear;iL++) {
    	    		if (iL!=iYear) sRet+=",";
    	    		sRet+=Integer.toString(iL);
	        	}
			}
			else sRet+=s[i]; 
		}
		return sRet;
	}

	public void subQuery(int iCnt,int iCntPars,int iDate,Statement stmt) {
     	sQuery =  "select "+sSumCnt+" as count from "+criteria.g_sTname+" where type3a like '%' ";
    	if ((sRes7a[iCntPars]!=null) && (sMainkey2.compareTo("month")!=0) && (sMainkey2.compareTo("year")!=0)) sQuery += "and "+sMainkey2+" like '"+sRes7a[iCntPars]+"%' ";
    	if (flgtypkey) sTypeVal=sRes7a[iCntPars];
    	if (!flgtyp19b) {
    		String sTy[] = sTypeVal.split(",");
    		sGroupBy = "group by ";
    		for (int iLty=0;iLty<sTy.length;iLty++) {
    			if (iLty!=0) sGroupBy += " or ";
    			sGroupBy += "type3a = '"+sTy[iLty]+"'";
    		}
    	
    	}
    	
    	for (int i=0;i<listX.getItemCount();i++) {
    		//System.out.println("Val0:"+listX.getItem(i)+" sTypemsg:"+sMainkey);
    		String a = listX.getItem(i);
    		if (a.contains("*")) a=a.substring(1,a.length());
    		String sField[] = a.split(":");
    		String sFind = find(criteria.sFilename,sField[0]);
    		System.out.println("field:"+sFind);
    		
    		if ( (!listX.getItem(i).startsWith(criteria.sKey+sMainkey)) && (!sFind.startsWith("year")) && (!sFind.startsWith("month")) && (!sFind.startsWith("date")) && (!sFind.startsWith("type19b"))) {
        		//System.out.println("IN");
	        	String sX[] = listX.getItem(i).split(":");
	        	System.out.println("sXln:"+sX.length);
        		if (sX[1].compareTo(" ")==0) sX[1]="";

	         	String sXsub[] = sX[1].split(",");
	        	for (int iCnt1=0;iCnt1<sXsub.length;iCnt1++) {
	        		if (iCnt1==0) sQuery+="and (";
	        		else if (iCnt1>0) sQuery+="or ";
	           		sType = find(criteria.sFilename,sX[0]);
	           		System.out.println("subval:"+sXsub[iCnt1]);
	           		if (sXsub[iCnt1].contains("-")) {
	           			String sSubval[] = sXsub[iCnt1].split("-");
	           			if ((sSubval[0]!=null) && (sSubval[1]!=null)) { 
	           				sQuery += sType+" >= '"+sSubval[0]+"' ";
	           				sQuery += "and "+sType+" <= '"+sSubval[1]+"' ";
	           			}
	           		}
	           		else {
	           			if (sType.compareTo("type15b")==0) 
	           				sQuery += sType+" like '"+sXsub[iCnt1]+"%' ";
	           			else sQuery += sType+" like '%"+sXsub[iCnt1]+"%' ";
	           		}
		        	if (iCnt1==sXsub.length-1) sQuery+=") ";
	        	}
        	}
        }
    	
        i_dt1[iCntPars][iCnt-1]=0;
    	if (flgmonthkey) {
			String sYear[] = T_year.split(",");
			String sQueryTemp = sQuery;
			for (int iLp=0;iLp<sYear.length;iLp++) {
				sQuery = sQueryTemp;
		    	int iMonth=0;
		    	for (int iCn=0;iCn<12;iCn++) {
		    		if (C_month.compareTo(Month[iCn])==0) {iMonth=iCn+1;break;} 
		    	}
		    	String sMonth,sMonthtemp,sDate,sDatetemp;
		    	//form
		    	sMonthtemp = Integer.toString(iMonth);
		    	if (sMonthtemp.length()==1) sMonth="0"+sMonthtemp;
		    	else sMonth=sMonthtemp;
	    		sQuery=sQuery.replace("air_message", "air_message"+sYear[iLp]+"_"+sMonth);
		    	sQuery += sGroupBy;
		        System.out.println("sQuery1:"+sQuery);
		        sType=Integer.toString(iCntPars+1);
		    	try {
		    		ResultSet resultSet = stmt.executeQuery(sQuery);
		    		if (resultSet.next()) {
		    			if (resultSet.getString("count")!=null) {
							i_dt1[iCntPars][iCnt-1] += Integer.parseInt(resultSet.getString("count"));
		    				System.out.println(iCnt+":"+resultSet.getString("count")+" to i_dt1["+iCntPars+"]["+(iCnt-1)+"]");
		    			}
		    		}
		    	} catch (SQLException se) {
		    		System.err.println(se.getMessage());
		    	}
	    	}
			System.out.println("total i_dt1["+iCntPars+"]["+(iCnt-1)+"]:"+i_dt1[iCntPars][iCnt-1]);
    	}
    	else if (flgyearkey) {
			String sQueryTemp = sQuery;
		    int iLp=0;
		    String sYear[]=new String[1];
		    sYear[iLp]=sRes7a[iCntPars];
			String monthq[] = C_monthb.split(",");
			for (int iLp1=0;iLp1<monthq.length;iLp1++) {
				sQuery = sQueryTemp;
		    	int iMonth=0;
		    	for (int iCn=0;iCn<12;iCn++) {
		    		if (monthq[iLp1].compareTo(Month[iCn])==0) {iMonth=iCn+1;break;} 
		    	}
		    	String sMonth,sMonthtemp,sDate,sDatetemp;
		    	sMonthtemp = Integer.toString(iMonth);
		    	if (sMonthtemp.length()==1) sMonth="0"+sMonthtemp;
		    	else sMonth=sMonthtemp;
	    		sQuery=sQuery.replace("air_message", "air_message"+sYear[iLp]+"_"+sMonth);
		    	sQuery += sGroupBy;
		        System.out.println("sQuery1:"+sQuery);
		        sType=Integer.toString(iCntPars+1);
		    	try {
		    		ResultSet resultSet = stmt.executeQuery(sQuery);
		    		if (resultSet.next()) {
		    			if (resultSet.getString("count")!=null) {
							i_dt1[iCntPars][iCnt-1] += Integer.parseInt(resultSet.getString("count"));
		    				System.out.println(iCnt+":"+resultSet.getString("count")+" to i_dt1["+iCntPars+"]["+(iCnt-1)+"]");
		    			}
		    		}
		    	} catch (SQLException se) {
		    		System.err.println(se.getMessage());
		    	}
	    	}
			System.out.println("total i_dt1["+iCntPars+"]["+(iCnt-1)+"]:"+i_dt1[iCntPars][iCnt-1]);
    	}
    	else {
			String sQueryTemp = sQuery;
        	System.out.println("sQuerymnth:"+sQuerymnth);
        	String sMon[] = sQuerymnth.split("or");
        	for (int iLMnth=0;iLMnth<sMon.length;iLMnth++) {
        		String sMon1[] = sMon[iLMnth].split("and");
        		for (int iLMnth1=0;iLMnth1<sMon1.length;iLMnth1++) {
        			sQuery=sQueryTemp;
            		int index = sMon1[iLMnth1].indexOf("'");
            		String s = sMon1[iLMnth1].substring(index+1, index+1+7).replace("-", "_");
        			//System.out.println("sQuerymnthArr:"+s);
            		sQuery=sQuery.replace("air_message", "air_message"+s);
    		    	sQuery += sGroupBy;
        			System.out.println("sQuery:"+sQuery);
    		    	try {
    		    		ResultSet resultSet = stmt.executeQuery(sQuery);
    		    		if (resultSet.next()) {
    		    			if (resultSet.getString("count")!=null) {
    							i_dt1[iCntPars][iCnt-1] += Integer.parseInt(resultSet.getString("count"));
    		    				System.out.println(iCnt+":"+resultSet.getString("count")+" to i_dt1["+iCntPars+"]["+(iCnt-1)+"]");
    		    			}
    		    		}
    		    	} catch (SQLException se) {
    		    		System.err.println(se.getMessage());
    		    	}
        			break;
        		}
        	}
    		//sQuery+=" and ("+sQuerymnth+")";
    	}
     }
     
	public void counttm(String sYear)
	{
		int iDate=-1;
    	sQuerymnth += "(tgl >= '"+sYear+"-";
    	int iMonth=0;
    	for (int iCn=0;iCn<12;iCn++) {
    		if (C_month.compareTo(Month[iCn])==0) {iMonth=iCn+1;break;} 
    	}
    	String sMonth,sMonthtemp,sDate,sDatetemp;
    	//form
    	sMonthtemp = Integer.toString(iMonth);
    	if (sMonthtemp.length()==1) sMonth="0"+sMonthtemp;
    	else sMonth=sMonthtemp;
    	sQuerymnth += sMonth;
    	
    	//date
    	if (iDate!=-1) {
	    	sQuerymnth += "-";
	    	sDatetemp = Integer.toString(iDate);
	    	if (sDatetemp.length()==1) sDate="0"+sDatetemp;
	    	else sDate=sDatetemp;
	    	sQuerymnth += sDate;
    	}
    	//end date

    	if (iDate==-1) sQuerymnth +="-01";
    	sQuerymnth += "' ";

    	String date="",date1,yearMonths="'"+sYear+"-";
    	if (iDate!=-1) yearMonths += sMonth+"-";
		if (iDate==31) {
			date="1";//1st
			int imonth = Integer.parseInt(sMonth)+1;
			if (imonth<13) {
				String months0="0";
				String sMonths = Integer.toString(imonth);
				yearMonths = "'"+sYear+"-";
				if (sMonths.length()==1) yearMonths += (months0+sMonths);
				else yearMonths += sMonths;
				yearMonths+="-";
			}
			else {
				int iyear = Integer.parseInt(sYear)+1;
				yearMonths = "'"+Integer.toString(iyear)+"-"+"01"+"-";
			}
		}
		else date=Integer.toString(iDate+1);
		date1="0";
		if (date.length()==1) date1+=date;
		else date1=date;
		
    	sQuerymnth += "and tgl <= ";
    	//to
    	
    	if (iDate!=-1) {
        	sQuerymnth += yearMonths;
        	sQuerymnth += date1;
    	}
    	else {
			int imonth = Integer.parseInt(sMonth)+1;
			if (imonth<13) {
				String months0="0";
				String sMonths = Integer.toString(imonth);
				yearMonths = "'"+sYear+"-";
				if (sMonths.length()==1) yearMonths += (months0+sMonths);
				else yearMonths += sMonths;
				yearMonths+="-01";
			}
			else {
				int iyear = Integer.parseInt(sYear)+1;
				yearMonths = "'"+Integer.toString(iyear)+"-"+"01-01";
			}
        	sQuerymnth += yearMonths;
    	}
    	sQuerymnth += "') ";

	}
	public String find (String fname,String sSearch) {
         String sRes="";
    	 String[] subbuf = new String[100];
         try {
             File myFile = new File(fname);
             FileReader fileReader = new FileReader(myFile);
             BufferedReader buffReader = new BufferedReader(fileReader);
             String buf = null;
        	 System.out.println("search:"+sSearch+" "+fname);

             while ((buf = buffReader.readLine()) != null) {
                 if ((!buf.startsWith("#")) &&  (!buf.startsWith("//"))) {
                	 subbuf = buf.split("=");
                	 sRes = subbuf[1];
                	 if (sRes.compareTo(sSearch)==0) {
                    	 sRes = subbuf[0];
                    	 break;
                     }
                 }
             }
             buffReader.close();
         } catch (Exception err) {
        	 System.out.println("error:"+err);
             Shell sh = new Shell();
             MessageBox mg = new MessageBox(sh);
             mg.setMessage(err.getMessage());
             mg.open();
         }
         return sRes;
     }
	
	public String get_table()
	{
		String sTable="";
		String sDateArr[] = C_monthb.split(",");
		iMaxDate = sDateArr.length+1;
    	int iDate=-1;
    	int iCnt;
    	for (iCnt=1;iCnt<iMaxDate;iCnt++) {
    		if (!sDateArr[iCnt-1].contains("-")) {
    			//iDate = Integer.parseInt(sDateArr[iCnt-1]);
    			iDate=-1;
    			C_month = sDateArr[iCnt-1];
    			System.out.println("C_month:"+C_month);
    			String sYear[] = T_year.split(",");
    			for (int iLp=0;iLp<sYear.length;iLp++) {
    				if (iLp!=0) sQuerymnth+="or ";
    				counttm(sYear[iLp]);
    			}
		    	if ((!sQuerymnth.isEmpty()) && (iCnt!=iMaxDate-1)) 
		    		sQuerymnth+="or ";
    		}
    		else {
    			String sStrip[] = sDateArr[iCnt-1].split("-");
    			int iMontha=0,iMonthb=0;
    			for (int iCn=0;iCn<12;iCn++) {
    	    		if (sStrip[0].compareTo(Month[iCn])==0) {
    	    			iMontha=iCn+1;break;
    	    		} 
    	    	}
    			for (int iCn=0;iCn<12;iCn++) {
    	    		if (sStrip[1].compareTo(Month[iCn])==0) {
    	    			iMonthb=iCn+1;break;
    	    		} 
    	    	}
    			//System.out.println("iMontha:"+iMontha+" iMonthb:"+iMonthb);
    			iMaxDateb = iMonthb-iMontha+1;
    	    	for (int iL=iMontha;iL<=iMaxDateb;iL++) {
        			iDate = -1;//iL;
        			C_month = Month[iL-1];
        			String sYear[] = T_year.split(",");
        			for (int iLp=0;iLp<sYear.length;iLp++) {
        				if (iLp!=0) sQuerymnth+="or ";
        				counttm(sYear[iLp]);
        			}
			    	if ((!sQuerymnth.isEmpty()) && (iL!=iMaxDateb-1)) 
			    		sQuerymnth+="or ";
	        	}
    		}
        }
		String sQueryTemp = sQuerymnth;
		sTable="";
    	System.out.println("sQuerymnth:"+sQuerymnth);
    	String sMon[] = sQuerymnth.split("or");
    	for (int iLMnth=0;iLMnth<sMon.length;iLMnth++) {
    		String sMon1[] = sMon[iLMnth].split("and");
    		for (int iLMnth1=0;iLMnth1<sMon1.length;iLMnth1++) {
    			sQuery=sQueryTemp;
        		int index = sMon1[iLMnth1].indexOf("'");
        		String s = sMon1[iLMnth1].substring(index+1, index+1+7).replace("-", "_");
    			//System.out.println("sQuerymnthArr:"+s);
        		//sQuery=sQuery.replace("air_message", "air_message"+s);
        		sTable+="air_message"+s+",";
    			break;
    		}
    	}
    	if (!sTable.isEmpty()) {
			sTable=sTable.substring(0, sTable.length()-1);
		}
    	return sTable;
	}
}
