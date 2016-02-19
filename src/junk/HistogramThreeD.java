package junk;

import java.awt.Font;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
/**
 * JFreeChart3D
 */
public class HistogramThreeD extends ApplicationFrame {
	
	private static final long serialVersionUID = 2932800943390354306L;
	
	/**
	 * 
	 */
	public static CategoryDataset createDataset() {
		//DefaultCategoryDatasetCategoryDataset
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();//
		
		//DefaultCategoryDatasetserValue(double value, Comparable rowKey, Comparable columnKey)
		//value------??
		//           
		//rowKey-----?""
		//           ""??
		//      	 ??
		//columnKey--?
		dataset.setValue(10, "AA", "");
		dataset.setValue(20, "BB", "");
		dataset.setValue(40, "CC", "");
		dataset.setValue(15, "DD", "");
		
		
		return dataset;
	}
	
	/**
	 * 
	 * @param 
	 * @return 
	 */
	public static JFreeChart createChart(CategoryDataset dataset) {
		//createBarChart3D(String title, String categoryAxisLabel, String valueAxisLabel, CategoryDataset dataset, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls)
		//categoryAxisLabel--
		//valueAxisLabel-----
		//orientation--------??VERTICAL?HORIZONTAL
		JFreeChart chart = ChartFactory.createBarChart3D("Jadyer", "", "", dataset, PlotOrientation.VERTICAL, true, true, false);
		
		//JFreeChart?
		//Font.BOLDFont.ITALIC??
		chart.setTitle(new TextTitle("3D", new Font("", Font.BOLD + Font.ITALIC, 28)));
		
		//?Title?Plot?Legend
		//?PlotCategoryPlot
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		
		CategoryAxis categoryAxis = plot.getDomainAxis(); //
		categoryAxis.setLabelFont(new Font("", Font.BOLD, 16)); //
		
		return chart;
	}
	
	/**
	 * JPanel
	 * @see JPanelContentPane
	 */
	public static JPanel createPanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart); //JFreeChartPanel
	}
	
	public HistogramThreeD(String title) {
		super(title); //?title
		this.setContentPane(createPanel()); //JPanelContentPane
	}
	
	public static void main(String[] args) {
		HistogramThreeD chart = new HistogramThreeD("");
		chart.pack();
		chart.setVisible(true);
	}
}