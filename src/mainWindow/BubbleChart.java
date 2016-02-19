package mainWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel; 
import org.jfree.chart.*; 
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBubbleRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRendererState;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset; 
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleEdge;

import defualt.BrainFreezeMain;
  
public class BubbleChart extends ApplicationFrame{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected static final int ITD = 0;
	protected static final int LTD = 1;
	
	public BubbleChart(String s, JPanel jpanel, int type){
	
		super(s); 
		
		if (type != ITD && type != LTD){
			System.out.println("Invalid Histrogram Type Requested! Exiting!");
			System.exit(0);
		}
			
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		jpanel.add(createHistogram(type), c);  
      
   }

   private static JFreeChart createChart( XYZDataset xyzdataset, int type ){
	   
	   String title, xAxis, yAxis = "Position";
	   
	   if (type == ITD){
		   title = "ITD[mSec]";
		   xAxis = "Time";
	   }
	   else{
		   title = "LTD[dB]";
		   xAxis = "Level";
	   }
	   
       JFreeChart jfreechart = ChartFactory.createBubbleChart(
         title,                    
         yAxis,                    
         xAxis,                    
         xyzdataset,                    
         PlotOrientation.HORIZONTAL,                    
         true, true, false);
       
       XYPlot xyplot = ( XYPlot )jfreechart.getPlot( );                 
       xyplot.setForegroundAlpha( 0.95F );    
   
       XYItemRenderer xyitemrenderer =  new myXYBubbleRenderer(XYBubbleRenderer.SCALE_ON_RANGE_AXIS); 
    
       for (int i = 0; i < 9; i++){
    	   xyitemrenderer.setSeriesPaint( i , Color.BLUE ); 
    	   
       }
       xyplot.setRenderer(xyitemrenderer);
                     
       return jfreechart;
   }

   public static XYZDataset createDataset(int type)
   {
      DefaultXYZDataset defaultxyzdataset = new DefaultXYZDataset(); 
                     
      double x[ ] = { -0.900, -0.675, -0.450, -0.225, 0, 0.225, 0.450, 0.675, 0.900 };                 
        
      double temp[][] = (type == ITD) ? 
    		  BrainFreezeMain.patients.get(BrainFreezeMain.currentPatientIndex).getData().getITD_Results() :
    			  BrainFreezeMain.patients.get(BrainFreezeMain.currentPatientIndex).getData().getLTD_Results();
    			  
      
      double bins[][]= new double [9][9];
      double positions [][] = new double [9][9];
      
      if (temp.length > 0) {
          for (int i = 0; i < temp[0].length; i++) {
              for (int j = 0; j < temp.length; j++) {
                  bins[i][j] = temp[i][j]/36;
                  positions[i][j] = i + 1;
              }
            
          }
      }
      
     
     for (int i = 0; i < 9; i++){
    	 defaultxyzdataset.addSeries( "Pos " + Integer.toString(i) , new double[][] {positions[i], x, bins[i]});
     }
   
     
      return defaultxyzdataset; 
   }

   public static JPanel createHistogram( int type )
   {
      JFreeChart jfreechart = createChart( createDataset(type), type);                 
      ChartPanel chartpanel = new ChartPanel( jfreechart );
                       
      chartpanel.setDomainZoomable( false );                 
      chartpanel.setRangeZoomable( false );
                 
      return chartpanel;
   }
   
 
   
}

 class myXYBubbleRenderer extends XYBubbleRenderer{


	 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public myXYBubbleRenderer(int scaleOnDomainAxis) {
		super(scaleOnDomainAxis);
	}
	
	/**
	 * Use this to prevent legend from being drawn.
	 */
	@Override
	public LegendItem getLegendItem(int datasetIndex, int series){
		return null;
	}

	@Override
    public void drawItem(Graphics2D g2, XYItemRendererState state,
            Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot,
            ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset,
            int series, int item, CrosshairState crosshairState, int pass) {

		
		
        // return straight away if the item is not visible
        if (!getItemVisible(series, item)) {
            return;
        }

        PlotOrientation orientation = PlotOrientation.VERTICAL;

        // get the data point...
        double x = dataset.getXValue(series, item);
        double y = dataset.getYValue(series, item);
        double z = Double.NaN;
        
        if (dataset instanceof XYZDataset) {
            XYZDataset xyzData = (XYZDataset) dataset;
            z = xyzData.getZValue(series, item);
        }
        if (!Double.isNaN(z) && !(z == 0)) {
        	
            RectangleEdge domainAxisLocation = plot.getDomainAxisEdge();
            RectangleEdge rangeAxisLocation = plot.getRangeAxisEdge();
            double transX = domainAxis.valueToJava2D(x, dataArea,
                    domainAxisLocation);
            double transY = rangeAxis.valueToJava2D(y, dataArea,
                    rangeAxisLocation);

            double transDomain;
            double transRange;
            double zero;

         

            zero = domainAxis.valueToJava2D(0.0, dataArea,
                    domainAxisLocation);
            transDomain = domainAxis.valueToJava2D(z, dataArea,
                    domainAxisLocation) - zero;
            transRange = transDomain;
            
            
            transDomain = Math.abs(transDomain);
            transRange = Math.abs(transRange);
            Rectangle2D bin = null; 
  
            bin = new Rectangle2D.Double(transY - transY/(4*(item + 1))   ,
                    transX - 2*transDomain , transY/(2*(item + 1)), 2*transDomain); // should be transDmain*4 for y axis scale
    
            g2.setPaint(getItemPaint(series, item));
            g2.fill(bin);
            g2.setStroke(getItemOutlineStroke(series, item));
            g2.setPaint(getItemOutlinePaint(series, item));
            g2.draw(bin);

            if (isItemLabelVisible(series, item)) {
            
                drawItemLabel(g2, orientation, dataset, series, item,
                        transY, transX, false);
                
            }

            // add an entity if this info is being collected
            if (info != null) {
                EntityCollection entities 
                        = info.getOwner().getEntityCollection();
                if (entities != null && bin.intersects(dataArea)) {
                    addEntity(entities, bin, dataset, series, item,
                            bin.getCenterX(), bin.getCenterY());
                }
            }

            int domainAxisIndex = plot.getDomainAxisIndex(domainAxis);
            int rangeAxisIndex = plot.getRangeAxisIndex(rangeAxis);
            updateCrosshairValues(crosshairState, x, y, domainAxisIndex,
                    rangeAxisIndex, transX, transY, orientation);
        
        }

    }
   
   
}
