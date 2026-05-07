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

//import com.isode.xuxa.desktop.XuxaMain;

public class METEOchart1{
//    Connection conn;
//    Statement stmt;
//	CategoryDataset dataset;
//	JFreeChart chart;
//	String[] Month = new String[]{"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
//	List listX;
//	int i_arcft,iMaxArcft=5,iMaxX=31,iCntPars;
//	String s_atrct[] = new String[6];
//    int i_dt1[][] = new int[iMaxArcft][32];
//    String s_time="",sQuery;
//    int nCnt,iMaxDate,iMaxDateb;
//    String sTitleX="",sTitleY="",series1="",sTitleChart="";
//    String sSeriesNm[] = new String[iMaxArcft];
//    String T_year="",C_month="",C_monthb="",sDateList="";
//    String sType="";
//    //String sQueryCrt="";
//    String sGroupBy="";
// 	String sTypemsg="",sTypemsgY="",sTypemsgb="",sTypeVal="",sType7a="";
// 	String sRes7a[] = new String[iMaxArcft];
// 	int typChart;
// 	ApplicationFrame AF;
// 	PieChart3D pie;
//	
//	public void METEOcreate(Connection conn, Statement stmt,ApplicationFrame AF,final String title,List listX,int typChart) {
//		if (typChart==2) {
//			if (pie!=null) pie.close();
//			pie = new PieChart3D("Metar Pie Chart");
//		}
//		this.typChart=typChart;
//		//ApplicationFrame b = new ApplicationFrame("");//super(title);
//		this.listX=listX;
//		this.conn=conn;
//		this.stmt=stmt;
//		this.AF = AF;
//    	if (chart()) {
//	        String ext = "jpg";
//	        File file = new File(criteria.g_sFile + "." + ext);
//	        try {
//	        	System.out.println("write "+criteria.g_sFile+" chart:"+chart);
//	        	if (chart!=null)
//	            ImageIO.write(chart.createBufferedImage(1500, 1000), ext, file);  // ignore returned boolean
//	        } catch(IOException e) {
//	            System.out.println("Write error for " + file.getPath() +
//	                               ": " + e.getMessage());
//	             Shell sh = new Shell();
//	             MessageBox mg = new MessageBox(sh);
//	             mg.setMessage(e.getMessage());
//	             mg.open();
//	        }
//	
//	        if (typChart==1) {
//	        	final ChartPanel chartPanel = new ChartPanel(chart);
//	        	chartPanel.setPreferredSize(new Dimension(1200, 600));
//		        AF.setContentPane(chartPanel);
//		        AF.pack();
//		        RefineryUtilities.centerFrameOnScreen(AF);
//		        AF.setVisible(true);
//		        AF.setLocation(0, 0);
//	        }
//	        else if (typChart==2){
//    			pie.pack();
//    			RefineryUtilities.centerFrameOnScreen(pie);
//    	        pie.setVisible(true);
//	        }
//    	}
//    }
//
//	public CategoryDataset createDataset(String sArr[]) {
//        // column keys...
//        String category[] = new String[32];
//      
//        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        System.out.println("max:"+i_arcft);
//        for (int iCntb=0;iCntb<i_arcft;iCntb++) {
//    		for (int iCnt=1;iCnt<iMaxDate;iCnt++) {
//    			if (!sArr[iCnt-1].contains("-")) {
//    				category[iCnt-1] = sArr[iCnt-1];
//    				category[iCnt-1]="";
//    				//System.out.println("i_dt1["+iCntb+"]["+(iCnt-1)+"]:"+i_dt1[iCntb][iCnt-1]);
//    			}
//    			else {
//        			String sStrip[] = sArr[iCnt-1].split("-");
//        	    	//System.out.println("iMaxDatebStrip2:"+iMaxDateb);
//        	    	//System.out.println("sStrip2[0]:"+sStrip[0]);
//        	    	//System.out.println("sStrip2[1]:"+sStrip[1]);
//    	        	
//        	    	for (int iL=Integer.parseInt(sStrip[0]);iL<Integer.parseInt(sStrip[0])+iMaxDateb;iL++) {
//	        			//iDate = iL;
//	        			//subQuery(iCnt,iCntPars,iDate,stmt);
//	    				category[iL] = Integer.toString(iL);
//	    				dataset.addValue(i_dt1[iCntb][iL], sSeriesNm[iCntb], category[iL]);
//        	    	}
//    			}
//        	}
//    		break;
//        }
//        return dataset;
//    }
//
//	public CategoryDataset createDatasetM(String sArr[]) {
//        // column keys...
//        String category[] = new String[32];
//      
//        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        System.out.println("no date max:"+i_arcft);
//        for (int iCntb=0;iCntb<i_arcft;iCntb++) {
//    		for (int iCnt=1;iCnt<iMaxDate;iCnt++) {
//    			if (!sArr[iCnt-1].contains("-")) {
//    				category[iCnt-1] = sArr[iCnt-1];//Integer.toString(iCnt);
//    				dataset.addValue(i_dt1[iCntb][iCnt-1], sSeriesNm[iCntb], category[iCnt-1]);
//    				//System.out.println("i_dt1["+iCntb+"]["+(iCnt-1)+"]:"+i_dt1[iCntb][iCnt-1]);
//    			}
//    			else {
//        			String sStrip[] = sArr[iCnt-1].split("-");
//        			int iMontha=0,iMonthb=0;
//        			for (int iCn=0;iCn<12;iCn++) {
//        	    		if (sStrip[0].compareTo(Month[iCn])==0) {
//        	    			iMontha=iCn+1;break;
//        	    		} 
//        	    	}
//        			for (int iCn=0;iCn<12;iCn++) {
//        	    		if (sStrip[1].compareTo(Month[iCn])==0) {
//        	    			iMonthb=iCn+1;break;
//        	    		} 
//        	    	}
//        			//System.out.println("iMontha:"+iMontha+" iMonthb:"+iMonthb);
//        			iMaxDateb = iMonthb-iMontha+1;
//        	    	for (int iL=iMontha;iL<=iMaxDateb;iL++) {
//	    				category[iL] = Month[iL-1];
//	    				dataset.addValue(i_dt1[iCntb][iL], sSeriesNm[iCntb], category[iL]);
//        	    	}
//    			}
//        	}
//        }
//        return dataset;
//    }
//
//	public JFreeChart createChart(final CategoryDataset dataset) {
//        // create the chart...
//        final JFreeChart chart = ChartFactory.createBarChart(
//            "",       // chart title
//            sTitleChart,               // domain axis label
//            sTitleY,                  // range axis label
//            dataset,                  // data
//            PlotOrientation.VERTICAL, // orientation
//            true,                    // include legend
//            true,                     // tooltips?
//            false                     // URLs?
//        );
//        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
//
//        // set the background color for the chart...
//        chart.setBackgroundPaint(Color.white);
//        
//        // get a reference to the plot for further customisation...
//        final CategoryPlot plot = chart.getCategoryPlot();
//        plot.setBackgroundPaint(Color.lightGray);
//        plot.setDomainGridlinePaint(Color.white);
//        plot.setRangeGridlinePaint(Color.white);
//        
//        //final IntervalMarker target = new IntervalMarker(4.5, 7.5);
//        //target.setLabel("Target Range");
//        //target.setLabelFont(new Font("SansSerif", Font.ITALIC, 11));
//        //target.setLabelAnchor(RectangleAnchor.LEFT);
//        //target.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
//        //target.setPaint(new Color(222, 222, 255, 128));
//        //plot.addRangeMarker(target, Layer.BACKGROUND);
//        
//        // set the range axis to display integers only...
//        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//
//        // disable bar outlines...
//        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
//        renderer.setDrawBarOutline(false);
//        renderer.setItemMargin(0.10);
//        
//        // set up gradient paints for series...
//        final GradientPaint gp0 = new GradientPaint(
//            0.0f, 0.0f, Color.blue, 
//            0.0f, 0.0f, Color.lightGray
//        );
//        final GradientPaint gp1 = new GradientPaint(
//            0.0f, 0.0f, Color.green, 
//            0.0f, 0.0f, Color.lightGray
//        );
//        final GradientPaint gp2 = new GradientPaint(
//            0.0f, 0.0f, Color.red, 
//            0.0f, 0.0f, Color.lightGray
//        );
//        final GradientPaint gp3 = new GradientPaint(
//                0.0f, 0.0f, Color.yellow, 
//                0.0f, 0.0f, Color.lightGray
//        );
//        final GradientPaint gp4 = new GradientPaint(
//                0.0f, 0.0f, Color.black, 
//                0.0f, 0.0f, Color.lightGray
//        );
//        renderer.setSeriesPaint(0, gp0);
//        renderer.setSeriesPaint(1, gp1);
//        renderer.setSeriesPaint(2, gp2);
//        renderer.setSeriesPaint(3, gp3);
//        renderer.setSeriesPaint(4, gp4);
//        
//        //renderer.setLabelGenerator(new BarChartDemo7.LabelGenerator());
//        renderer.setItemLabelsVisible(true);
//        final ItemLabelPosition p = new ItemLabelPosition(
//            ItemLabelAnchor.INSIDE12, TextAnchor.CENTER_LEFT,//.CENTER_RIGHT, 
//            TextAnchor.CENTER_LEFT,
//            //.CENTER_RIGHT, 
//            -Math.PI / 2.0
//        );
//        renderer.setPositiveItemLabelPosition(p);
//
//        final ItemLabelPosition p2 = new ItemLabelPosition(
//            ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT, 
//            TextAnchor.CENTER_LEFT, -Math.PI / 2.0
//        );
//        renderer.setPositiveItemLabelPositionFallback(p2);
//        final CategoryAxis domainAxis = plot.getDomainAxis();
//        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
//        // OPTIONAL CUSTOMISATION COMPLETED.
//        s_time=sTitleChart;
//        
//        //if (!s_time.isEmpty()) chart.setTitle(s_time);
//        return chart;
//    }
//    
//	public boolean chart(){
//        boolean b_ret=false;
//       	for (int iCntb=0;iCntb<iMaxArcft;iCntb++) {
//         	for (int iCnt=0;iCnt<iMaxX;iCnt++) {
//         		i_dt1[iCntb][iCnt] = 0;
//         	}
//       	}
//       	if (listX!=null){
//            if (parseARCFT()) b_ret=true;
//       	}
//        return b_ret;
//    }
//     
//	
//	public boolean parseARCFT(){
//     	boolean flg=true;
//        
//        //get message type
//     	boolean ok=false;
//     	sTitleY = "Value";sTitleChart="";
//    	sType7a="";T_year="";C_month="";sDateList="";
//     	for (int i=0;i<listX.getItemCount();i++) {
//     		//System.out.println("sX:"+listX.getItem(i));
//        	String sX[] = listX.getItem(i).split(":");
//        	//if (!ok) sTypemsg = find(XuxaMain.g_sFname,sX[0]);
//        	//else 
//        	sTypemsgb = find(criteria.sFilename,sX[0]);
//        	//System.out.println("sX[0]:"+sX[0]+" sTypemsg:"+sTypemsg+" sTypemsgb:"+sTypemsgb);
//        	
//    		if ((sX[0].compareTo("Month")!=0) && (!sX[0].contains("Location"))) 
//    			sTitleChart +=sX[0]+":"+sX[1]+" ";
//        	System.out.println("sTitleChart:"+sTitleChart);
//        	//if (!ok) {
//        	//	if (sTypemsg.compareTo("Location")==0) {
//        	//		ok=true;
//        	//		sTypemsg=sX[0];
//        	//		sTypeVal=sX[1];
//        	//		sTitleChart +=sTypeVal+" Chart, ";
//        	//	}
//        	//}
//        	
//        	if (sTypemsgb.compareTo("Location")==0) {sType7a=sX[0]+":"+sX[1];ok=true;} 
//     		else if (sTypemsgb.startsWith("year")) T_year = sX[1];
//        	else if (sTypemsgb.startsWith("month")) C_month = sX[1];
//        	else if (sTypemsgb.startsWith("date")) sDateList = sX[1];
//        }
//     	C_monthb=C_month;
//    	System.out.println("Year:"+T_year+" Month:"+C_month+" Date:"+sDateList);
//    	System.out.println("sType7a:"+sType7a);
//     	if (ok) {
//     		ok=false;
//
//	        if (!sType7a.isEmpty()) {
//		        String sPars7a[] = sType7a.split(":");
//		        System.out.println("aircraft:"+sPars7a.length);
//		        System.out.println("n aircraft:"+sPars7a[0]);
//
//		        if (sPars7a[1]!=null) sRes7a = sPars7a[1].split(",");
//		        i_arcft = sRes7a.length;
//		        if (i_arcft>5) i_arcft=iMaxArcft;
//	        }
//	        else {i_arcft=1;sSeriesNm[0]="All of Aircraft";sRes7a[0]=null;}
//	        System.out.println("n aircraft:"+i_arcft);
//	        for (iCntPars=0;iCntPars<i_arcft;iCntPars++) {
//	        	//System.out.println("sDateList:"+sDateList);
//	        	if (sRes7a[iCntPars]!=null) sSeriesNm[iCntPars] = sRes7a[iCntPars];
//	        	iMaxDate=32;
//    			final cProgressBar progbr = new cProgressBar("");
//    			if (sRes7a[iCntPars]!=null) progbr.sTitleB = "Creating "+sRes7a[iCntPars] +"...";
//    			else progbr.sTitleB = "Please wait...";
//    			new Thread() {
// 	    			public void run() {
// 	    				if (XuxaMain.getDisplay().isDisposed()) return;
// 	    				XuxaMain.getDisplay().asyncExec(new Runnable() {
// 	    					public void run() {
//					        	if (!sDateList.isEmpty()) {
//					        		sTitleX = "Date";
//					        		if (!C_monthb.isEmpty()) {
//					        			String sC_monthb[] = C_monthb.split(",");
//					        			if (sC_monthb[0].contains("-")) sC_monthb = sC_monthb[0].split("-");
//					        			C_month = sC_monthb[0];
//					        		}
//				        			final String sDateArr[] = sDateList.split(",");
//				        			iMaxDate = sDateArr.length+1;
//    	        	
//	 	    			        	int iCnt;
//	 	    	    	        	int iDate=-1;
//	 	    						progbr.create(31);
//				    	        	for (iCnt=1;iCnt<iMaxDate;iCnt++) {
//				    	        		//System.out.println("sDateArr[iCnt-1]:"+sDateArr[iCnt-1]);
//				    	    			progbr.bar.setSelection(iCnt);
//				    	    			if (!sDateArr[iCnt-1].contains("-")) {
//				    	        			iDate = Integer.parseInt(sDateArr[iCnt-1]);
//				    	        			subQuery(iCnt,iCntPars,iDate,stmt);
//				    	        		}
//				    	        		else {
//				    	        			String sStrip[] = sDateArr[iCnt-1].split("-");
//				    	        			iMaxDateb = (Integer.parseInt(sStrip[1]) - Integer.parseInt(sStrip[0])) + 1;
//				    	        			//System.out.println("From:"+Integer.parseInt(sStrip[0])+" iMaxDateb:"+iMaxDateb);
//				    	        	    	for (int iL=Integer.parseInt(sStrip[0]);iL<Integer.parseInt(sStrip[0])+iMaxDateb;iL++) {
//					    	        			iDate = iL;
//					    	        			subQuery(iL+1,iCntPars,iDate,stmt);
//					    	        			progbr.bar.setSelection(iCnt+iL);
//				    	    	        	}
//				    	        		}
//				    		        }
//				    	        	if (iCntPars==i_arcft-1) {
//				    	        		dataset = createDataset(sDateArr);
//				    	        		chart = createChart(dataset);
//				    	        	}
//				    	        	progbr.bar.setSelection(31);
//								    progbr.finish();
//		 	    				}
//					        	else if (!C_month.isEmpty()) {
//	 	    						progbr.create(12);
//					        		sTitleX = "Month";
//				        			String sDateArr[] = C_monthb.split(",");
//				        			iMaxDate = sDateArr.length+1;
//				    	        	int iDate=-1;
//						        	int iCnt;
//				    	        	for (iCnt=1;iCnt<iMaxDate;iCnt++) {
//			    	        			progbr.bar.setSelection(iCnt);
//				    	        		if (!sDateArr[iCnt-1].contains("-")) {
//				    	        			//iDate = Integer.parseInt(sDateArr[iCnt-1]);
//				    	        			iDate=-1;
//				    	        			C_month = sDateArr[iCnt-1];
//				    	        			//System.out.println("C_month:"+C_month);
//				    	        			subQuery(iCnt,iCntPars,iDate,stmt);
//				    	        		}
//				    	        		else {
//				    	        			String sStrip[] = sDateArr[iCnt-1].split("-");
//				    	        			int iMontha=0,iMonthb=0;
//				    	        			for (int iCn=0;iCn<12;iCn++) {
//				    	        	    		if (sStrip[0].compareTo(Month[iCn])==0) {
//				    	        	    			iMontha=iCn+1;break;
//				    	        	    		} 
//				    	        	    	}
//				    	        			for (int iCn=0;iCn<12;iCn++) {
//				    	        	    		if (sStrip[1].compareTo(Month[iCn])==0) {
//				    	        	    			iMonthb=iCn+1;break;
//				    	        	    		} 
//				    	        	    	}
//				    	        			//System.out.println("iMontha:"+iMontha+" iMonthb:"+iMonthb);
//				    	        			iMaxDateb = iMonthb-iMontha+1;
//				    	        	    	for (int iL=iMontha;iL<=iMaxDateb;iL++) {
//					    	        			iDate = -1;//iL;
//					    	        			C_month = Month[iL-1];
//					    	        			subQuery(iL+1,iCntPars,iDate,stmt);
//					    	        			progbr.bar.setSelection(iCnt+iL);
//				    	    	        	}
//				    	        		}
//				    		        }
//				    	        	if (iCntPars==i_arcft-1) {
//				    	        		System.out.println("Creating chart "+typChart+"..."+sDateArr.length);
//				    	        		if (typChart==1) {
//				    	        			dataset = createDatasetM(sDateArr);
//				    	        			chart = createChart(dataset);
//				    	        		}
//				    	        		else if (typChart==2) {
//				    	        			//pie = new PieChart3D("ATS Pie Chart");
//				    	        			String sArr[] = sDateArr;
//				    	        	        for (int iCntb=0;iCntb<i_arcft;iCntb++) {
//							    	        	for (iCnt=1;iCnt<iMaxDate;iCnt++) {
//				    	    	    				System.out.println("iMaxDate:"+iMaxDate+" iCntb:"+iCntb+" iCnt:"+(iCnt-1)+" "+sSeriesNm[iCntb]+ " "+ i_dt1[iCntb][iCnt-1]+" sDateArr["+(iCnt-1)+"]:"+sDateArr[iCnt-1]);
//							    	        		if (i_arcft==1) {
//								    	        		if (sArr[iCnt-1].contains("-")) {
//							    	            			String sStrip[] = sArr[iCnt-1].split("-");
//							    	            			int iMontha=0,iMonthb=0;
//							    	            			for (int iCn=0;iCn<12;iCn++) {
//							    	            	    		if (sStrip[0].compareTo(Month[iCn])==0) {
//							    	            	    			iMontha=iCn+1;break;
//							    	            	    		} 
//							    	            	    	}
//							    	            			for (int iCn=0;iCn<12;iCn++) {
//							    	            	    		if (sStrip[1].compareTo(Month[iCn])==0) {
//							    	            	    			iMonthb=iCn+1;break;
//							    	            	    		} 
//							    	            	    	}
//							    	            			System.out.println("iMontha:"+iMontha+" iMonthb:"+iMonthb);
//							    	            			iMaxDateb = (iMonthb-iMontha)+1;
//							    	            			String sSeriesNmS[] = new String[iMaxDateb+iMontha+1];
//							    	            	    	for (int iL=iMontha;iL<iMaxDateb+iMontha;iL++) {
//							    	            	    		sSeriesNmS[iL] = Month[iL-1];
//							    	            	    		System.out.println("pie:"+sSeriesNmS[iL]+ " val:"+ i_dt1[iCntb][iL]);
//							    	    	    				pie.data.setValue(sSeriesNmS[iL], new Double(i_dt1[iCntb][iL]));
//							    	            	    	}
//								    	        		}
//					    	    	    				else pie.data.setValue(sDateArr[iCnt-1], new Double(i_dt1[iCntb][iCnt-1]));
//							    	        		}
//							    	        		else pie.data.setValue(sSeriesNm[iCntb], new Double(i_dt1[iCntb][iCnt-1]));
//							    	        		if (i_arcft>1) break;
//				    	        	        	}
//				    	        	        }
//				    	        			//pie.data.setValue(sSeriesNm[iCntb], new Double(i_dt1[iCntb][0]));
//				    	        			//pie.data.setValue("Visual Basic", new Double(10.0));
//				    	        			//pie.data.setValue("C/C++", new Double(17.5));
//				    	        			//pie.data.setValue("PHP", new Double(32.5));
//				    	        			//pie.data.setValue("Perl", new Double(12.5));
//				    	        	        pie.chart = chart;
//				    	        			pie.create(sTitleChart);
//				    	        		}
//				    	        	}
//				    	        	progbr.bar.setSelection(12);
//								    progbr.finish();
//	        					}
// 	    				   }});
//		 	          }
//		 	    }.start();
//		 	    progbr.begin();
//	        }
//     	 }
//         return flg;
//    }
//    
//	public void subQuery(int iCnt,int iCntPars,int iDate,Statement stmt) {
//     	sQuery =  "select count(*) as count from "+criteria.g_sTname;
//    	if (sRes7a[iCntPars]!=null) sQuery += " where Location like '"+sRes7a[iCntPars]+"%'";
//    	sGroupBy = " group by Location";
//    	
//    	for (int i=0;i<listX.getItemCount();i++) {
//    		//System.out.println("Val0:"+listX.getItem(i)+" sTypemsg:"+sTypemsg);
//    		if ( (!listX.getItem(i).startsWith(sTypemsg)) && (!listX.getItem(i).startsWith(sType7a)) && (!listX.getItem(i).startsWith("Year")) && (!listX.getItem(i).startsWith("Month")) && (!listX.getItem(i).startsWith("Date"))) {
//	        	String sX[] = listX.getItem(i).split(":");
//	         	String sXsub[] = sX[1].split(",");
//	        	for (int iCnt1=0;iCnt1<sXsub.length;iCnt1++) {
//	        		if (iCnt1==0) sQuery+="and (";
//	        		else if (iCnt1>0) sQuery+="or ";
//	           		sType = find(criteria.sFilename,sX[0]);
//	           		//System.out.println("subval:"+sXsub[iCnt1]);
//	           		if (sXsub[iCnt1].contains("-")) {
//	           			String sSubval[] = sXsub[iCnt1].split("-");
//	           			if ((sSubval[0]!=null) && (sSubval[1]!=null)) { 
//	           				sQuery += sType+" >= '"+sSubval[0]+"' ";
//	           				sQuery += "and "+sType+" <= '"+sSubval[1]+"' ";
//	           			}
//	           		}
//	           		else sQuery += sType+" like '"+sXsub[iCnt1]+"%' ";
//		        	if (iCnt1==sXsub.length-1) sQuery+=") ";
//	        	}
//        	}
//        }
//	    sQuery +="and tgl >= '";
//     	
//     	// date query
//    	sQuery += T_year+"-";
//    	int iMonth=0;
//    	for (int iCn=0;iCn<12;iCn++) {
//    		if (C_month.compareTo(Month[iCn])==0) {iMonth=iCn+1;break;} 
//    	}
//    	String sMonth,sMonthtemp,sDate,sDatetemp;
//    	//form
//    	sMonthtemp = Integer.toString(iMonth);
//    	if (sMonthtemp.length()==1) sMonth="0"+sMonthtemp;
//    	else sMonth=sMonthtemp;
//    	sQuery += sMonth;
//    	
//    	//date
//    	if (iDate!=-1) {
//	    	sQuery += "-";
//	    	sDatetemp = Integer.toString(iDate);
//	    	if (sDatetemp.length()==1) sDate="0"+sDatetemp;
//	    	else sDate=sDatetemp;
//	    	sQuery += sDate;
//    	}
//    	//end date
//
//    	if (iDate==-1) sQuery +="-01";
//    	sQuery += "' ";
//
//    	String date="",date1,yearMonths="'"+T_year+"-";
//    	if (iDate!=-1) yearMonths += sMonth+"-";
//		if (iDate==31) {
//			date="1";//1st
//			int imonth = Integer.parseInt(sMonth)+1;
//			if (imonth<13) {
//				String months0="0";
//				String sMonths = Integer.toString(imonth);
//				yearMonths = "'"+T_year+"-";
//				if (sMonths.length()==1) yearMonths += (months0+sMonths);
//				else yearMonths += sMonths;
//				yearMonths+="-";
//			}
//			else {
//				int iyear = Integer.parseInt(T_year)+1;
//				yearMonths = "'"+Integer.toString(iyear)+"-"+"01"+"-";
//			}
//		}
//		else date=Integer.toString(iDate+1);
//		date1="0";
//		if (date.length()==1) date1+=date;
//		else date1=date;
//    	sQuery += "and tgl <= ";
//    	//to
//    	
//    	if (iDate!=-1) {
//        	sQuery += yearMonths;
//        	sQuery += date1;
//    	}
//    	else {
//			int imonth = Integer.parseInt(sMonth)+1;
//			if (imonth<13) {
//				String months0="0";
//				String sMonths = Integer.toString(imonth);
//				yearMonths = "'"+T_year+"-";
//				if (sMonths.length()==1) yearMonths += (months0+sMonths);
//				else yearMonths += sMonths;
//				yearMonths+="-01";
//			}
//			else {
//				int iyear = Integer.parseInt(T_year)+1;
//				yearMonths = "'"+Integer.toString(iyear)+"-"+"01-01";
//			}
//        	sQuery += yearMonths;
//    	}
//    	sQuery += "' ";
//    	//sQuery += sQueryCrt;
//    	sQuery += sGroupBy;
//        System.out.println("sQuery:"+sQuery);
//        sType=Integer.toString(iCntPars+1);
//    	try {
//    		ResultSet resultSet = stmt.executeQuery(sQuery);
//    		if (resultSet.next()) {
//    			if (resultSet.getString("count")!=null) {
//    				//System.out.println(iCnt+":"+resultSet.getString("count")+" to i_dt1["+iCntPars+"]["+(iCnt-1)+"]");
//					i_dt1[iCntPars][iCnt-1] = Integer.parseInt(resultSet.getString("count"));
//    			}
//    		}
//    	} catch (SQLException se) {
//    		se.printStackTrace();
//            Shell sh = new Shell();
//            MessageBox mg = new MessageBox(sh);
//            mg.setMessage(se.getMessage());
//            mg.open();
//    	}
//     }
//     
//	public String find (String fname,String sSearch) {
//         String sRes="";
//    	 String[] subbuf = new String[100];
//         try {
//             File myFile = new File(fname);
//             FileReader fileReader = new FileReader(myFile);
//             BufferedReader buffReader = new BufferedReader(fileReader);
//             String buf = null;
//
//             while ((buf = buffReader.readLine()) != null) {
//                 if ((!buf.startsWith("#")) &&  (!buf.startsWith("//"))) {
//                	 subbuf = buf.split("=");
//                	 sRes = subbuf[1];
//                	 if (sRes.compareTo(sSearch)==0) {
//                    	 sRes = subbuf[0];
//                    	 break;
//                     }
//                 }
//             }
//             buffReader.close();
//         } catch (Exception err) {
//             Shell sh = new Shell();
//             MessageBox mg = new MessageBox(sh);
//             mg.setMessage(err.getMessage());
//             mg.open();
//         }
//         return sRes;
//     }
}
