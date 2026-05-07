package org.jfree.chart.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.text.TextUtilities;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;
import org.jfree.util.Log;
import org.jfree.util.PrintStreamLogTarget;

import displays.AmscSplashScreen2;

//import com.isode.xuxa.desktop.XuxaMain;

public class NOTAMchart{
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
 	String sTypemsg="",sTypemsgY="",sTypemsgb="",sTypeVal="",sType7a="",sMainkey,sMainkey2,sQuerymnth;
 	String sRes7a[] = new String[iMaxArcft];
 	int typChart;
 	ApplicationFrame AF;
 	PieChart3D pie;
 	boolean flgmonthkey,flgtypkey,flgyearkey;
 	String sSumCnt;

 	String sTypeMaster[] = {"A","identifiers","fir","code_23","code_45","trafic","purpose","scope","lower","upper","coordinates","radius"};

 	public void NOTAMcreate(Connection conn, Statement stmt,ApplicationFrame AF,final String title,List listX,int typChart,List listY) {
		if (typChart==2) {
			if (pie!=null) pie.close();
			pie = new PieChart3D("NOTAM Pie Chart");
		}
		this.typChart=typChart;
		//ApplicationFrame b = new ApplicationFrame("");//super(title);
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
	        	if (chart!=null)
	            ImageIO.write(chart.createBufferedImage(1500, 1000), ext, file);  // ignore returned boolean
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
    				dataset.addValue(i_dt1[iCntb][iCnt-1], sSeriesNm[iCntb], category[iCnt-1]);
    				//System.out.println("i_dt1["+iCntb+"]["+(iCnt-1)+"]:"+i_dt1[iCntb][iCnt-1]);
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
        	}
        }
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
    				category[iCnt-1]="";
    				dataset.addValue(i_dt1[iCntb][iCnt-1], sSeriesNm[iCntb], category[iCnt-1]);
    				//System.out.println("i_dt1["+iCntb+"]["+(iCnt-1)+"]:"+i_dt1[iCntb][iCnt-1]);
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
        	}
        }
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
        
        //final IntervalMarker target = new IntervalMarker(4.5, 7.5);
        //target.setLabel("Target Range");
        //target.setLabelFont(new Font("SansSerif", Font.ITALIC, 11));
        //target.setLabelAnchor(RectangleAnchor.LEFT);
        //target.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
        //target.setPaint(new Color(222, 222, 255, 128));
        //plot.addRangeMarker(target, Layer.BACKGROUND);
        
        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0.10);
        renderer.setMaxBarWidth(0.1);

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
     	flgmonthkey=false;flgyearkey=false;
     	flgtypkey=false;

    	sTitleY = "Value";
     	sTitleChart="";sTypemsg="";sTypeVal="";sType7a="";T_year="";C_month="";sDateList="";
		sSumCnt="count(*)";

     	for (int i=0;i<listX.getItemCount();i++) {
        	String sX[] = listX.getItem(i).split(":");
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
        	//System.out.println("sX[0]:"+sX[0]+" sTypemsg:"+sTypemsg); 
        	if (ok) {
        		if ((sX[0].compareTo("*Month")!=0) && (sX[0].compareTo("*Year")!=0) && (!sX[0].contains(criteria.sKey))) 
        			sTitleChart +=sX[0]+":"+sX[1]+" ";
        	}
        	if ( (!ok) && ( (sTypemsg.compareTo("ntm_id_serie")==0) || (sTypemsg.compareTo("year")==0)) ) {
        		System.out.println("sTypemsgOKFALSE:"+sTypemsg+" "+sX[1]);
        		ok=true;
        		sTypeVal=sX[1];
        		if (sTypemsg.compareTo("year")==0) {
        			T_year = sX[1];sTypeVal="";
        			sTitleChart +="NOTAM Chart, ";
        			if (!sX[0].contains("*")) sTitleChart+=sX[0]+":"+sX[1]+" ";
        		}
        		else sTitleChart +="Serie "+sTypeVal+" Chart, ";
        		sTypemsg=sX[0];
        		if (sX[0].startsWith(criteria.sKey+"NOTAM serie")) sTypeVal="";
        	}
     		else if (sTypemsgb.startsWith("year")) T_year = sX[1];
        	else if (sTypemsgb.startsWith("month")) C_month = sX[1];
        	else if (sTypemsgb.startsWith("date")) sDateList = sX[1];
        	if (sX[0].contains(criteria.sKey)) {
        		String sX2 = sX[0].substring(1, sX[0].length());
        		sX[0]=sX2;
        		sMainkey=sX[0]+":"+sX[1];
        		sMainkey2 = find(criteria.sFilename,sX[0]);
        		System.out.println("sFind:"+sMainkey2+" sX[0]:"+sX[0]);
        		if (sX[0].startsWith("Month")) {
        			C_month = sX[1];
        			flgmonthkey=true;
        		}
        		else if (sX[0].startsWith("Year")) {
        			T_year = sX[1];
        			flgyearkey=true;
        		}
        		else if (sX[0].startsWith("NOTAM serie")) {
        			flgtypkey=true;
        		}
        	}
        	System.out.println("aYear:"+T_year+" Month:"+C_month+" Date:"+sDateList);
        	//ok=true;
        }
     	if ((listY!=null) && (typChart!=2)){
         	for (int iii=0;iii<listY.getItemCount();iii++) {
             	for (int ii=0;ii<sTypeMaster.length;ii++) {
             		if (find(criteria.sFilename,listY.getItem(iii)).compareTo(sTypeMaster[ii])==0) {
//             			if (sTypeMaster[ii].compareTo("type19b")==0)
//             				sSumCnt="sum("+sTypeMaster[ii]+")";
             			//else 
             				sSumCnt="count("+sTypeMaster[ii]+")";
             			sTitleY+="  ("+listY.getItem(iii)+")";
             			break;
             		}
             	}
         	}
     	}

    	C_month=getcomma(C_month);
    	T_year=getcommayear(T_year);
    	C_monthb=C_month;
    	System.out.println("Year:"+T_year+" Month:"+C_month+" Date:"+sDateList);
    	System.out.println("sMainkey:"+sMainkey);
     	if (ok) {
     		ok=false;
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
//		        		else if (!sMainPeriode.isEmpty()) {
//		        			sRes7a = sMainPeriode.split(",");
//		        		}
		        		else {
		        			sRes7a = sPars7a[1].split(",");
		        		}
	        			i_arcft = sRes7a.length;
		        	}
		        }
		        else {sRes7a[0]="";i_arcft=0;}
//		        if (sPars7a[1]!=null) sRes7a = sPars7a[1].split(",");
//		        i_arcft = sRes7a.length;
//		        if (i_arcft>5) i_arcft=iMaxArcft;
	        }
	        else {i_arcft=1;sSeriesNm[0]="All of Aircraft";sRes7a[0]=null;}
	        
	        sQuerymnth="";
	        //NO KEY,,MONTH add query
	        if ((!flgmonthkey) && (!flgyearkey)){ 
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
    	        			for (int iLp=0;iLp<sYear.length;iLp++) 
    	        				counttm(sYear[iLp]);
    				    	if ((!sQuerymnth.isEmpty()) && (iL!=iMaxDateb-1)) { 
    				    		sQuerymnth+="or ";
	        	    		}
	    	        	}
	        		}
		        }
	        	System.out.println("sQuerymnth:"+sQuerymnth);
	        }
	        else {
	        	for (iCntPars=0;iCntPars<i_arcft;iCntPars++) {
	        		sSeriesNm[iCntPars] = sRes7a[iCntPars];
	        	}
	        }
        	//	

	        System.out.println("n aircraft:"+i_arcft);
	        for (iCntPars=0;iCntPars<i_arcft;iCntPars++) {
	        	//System.out.println("sDateList:"+sDateList);
	        	if (sRes7a[iCntPars]!=null) sSeriesNm[iCntPars] = sRes7a[iCntPars];
	        	iMaxDate=32;
    			final cProgressBar progbr = new cProgressBar("");
    			if (sRes7a[iCntPars]!=null) progbr.sTitleB = "Creating "+sRes7a[iCntPars] +"...";
    			else progbr.sTitleB = "Please wait...";
    			new Thread() {
 	    			public void run() {
 	    				if (AmscSplashScreen2.display.isDisposed()) return;
 	    				AmscSplashScreen2.display.asyncExec(new Runnable() {
 	    					public void run() {
					        	if (!sDateList.isEmpty()) {
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
					        	else if (!C_month.isEmpty()) {
	 	    						progbr.create(12);
					        		sTitleX = "Month";
				        			String sDateArr[] = C_monthb.split(",");
				        			iMaxDate = sDateArr.length+1;
				    	        	int iDate=-1;
						        	int iCnt;
					        		if (flgmonthkey) {
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
				    	        			//System.out.println("C_month:"+C_month);
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
					        		else if (flgyearkey) {
						        		sDateArr = T_year.split("=");
			    	        			C_month = sRes7a[iCntPars];
			    	        			System.out.println("C_month:"+C_month);
			    	        			subQuery(1,iCntPars,iDate,stmt);
					        		}
			    	        		else {
					        			sDateArr = T_year.split("=");
				    	        		subQuery(1,iCntPars,iDate,stmt);
				    	        	}

					        		if (iCntPars==i_arcft-1) {
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
							    	        		if (i_arcft>1) break;
				    	        	        	}
				    	        	        }
				    	        			//pie.data.setValue(sSeriesNm[iCntb], new Double(i_dt1[iCntb][0]));
				    	        			//pie.data.setValue("Visual Basic", new Double(10.0));
				    	        			//pie.data.setValue("C/C++", new Double(17.5));
				    	        			//pie.data.setValue("PHP", new Double(32.5));
				    	        			//pie.data.setValue("Perl", new Double(12.5));
				    	        	        pie.chart = chart;
				    	        			pie.create(sTitleChart);
				    	        			chart = pie.chart;

				    	        		}
				    	        	}
				    	        	progbr.bar.setSelection(12);
								    progbr.finish();
	        					}
 	    				   }});
		 	          }
		 	    }.start();
		 	    progbr.begin();
	        }
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
     	sQuery =  "select "+sSumCnt+" as count from "+criteria.g_sTname+" where ntm_id_serie LIKE '"+"%"+"' ";
    	if ((sRes7a[iCntPars]!=null) && (sMainkey2.compareTo("month")!=0) && (sMainkey2.compareTo("year")!=0)) sQuery += "and "+sMainkey2+" like '"+sRes7a[iCntPars]+"%' ";
    	if (flgtypkey) sTypeVal=sRes7a[iCntPars];
    	//if (!sTypeVal.isEmpty()) sGroupBy = "group by A = '"+sTypeVal+"'";
    	sGroupBy="";
    	for (int i=0;i<listX.getItemCount();i++) {
    		//System.out.println("Val0:"+listX.getItem(i)+" sTypemsg:"+sTypemsg);
    		if ( (!listX.getItem(i).startsWith(criteria.sKey+sMainkey)) && (!listX.getItem(i).startsWith("Year")) && (!listX.getItem(i).startsWith("Month")) && (!listX.getItem(i).startsWith("Date"))) {
	        	String sX[] = listX.getItem(i).split(":");
	         	String sXsub[] = sX[1].split(",");
	        	for (int iCnt1=0;iCnt1<sXsub.length;iCnt1++) {
	        		if (iCnt1==0) sQuery+="and (";
	        		else if (iCnt1>0) sQuery+="or ";
	           		sType = find(criteria.sFilename,sX[0]);
	           		//System.out.println("subval:"+sXsub[iCnt1]);
	           		if (sXsub[iCnt1].contains("-")) {
	           			String sSubval[] = sXsub[iCnt1].split("-");
	           			if ((sSubval[0]!=null) && (sSubval[1]!=null)) { 
	           				sQuery += sType+" >= '"+sSubval[0]+"' ";
	           				sQuery += "and "+sType+" <= '"+sSubval[1]+"' ";
	           			}
	           		}
	           		else sQuery += sType+" like '"+sXsub[iCnt1]+"%' ";
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
	    		sQuery=sQuery.replace("notam_multi", "notam_multi"+sYear[iLp]+"_"+sMonth);
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
	    		sQuery=sQuery.replace("notam_multi", "notam_multi"+sYear[iLp]+"_"+sMonth);
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
            		sQuery=sQuery.replace("notam_multi", "notam_multi"+s);
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
		System.out.println("sYear:"+sYear);
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
             Shell sh = new Shell();
             MessageBox mg = new MessageBox(sh);
             mg.setMessage(err.getMessage());
             mg.open();
         }
         return sRes;
     }
}
