import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import com.sun.glass.ui.Application;

public class Chartgenerator{
	

	public static void main(String[] args) throws Exception{
		DefaultCategoryDataset line_chart=new DefaultCategoryDataset();
		int count=0;
	  InputStream keys=new FileInputStream(new File("./keyset.txt"));
	  InputStream values=new FileInputStream(new File("./valueset.txt"));
	  BufferedReader keyreader= new BufferedReader(new InputStreamReader(keys));
	  BufferedReader valuereader= new BufferedReader(new InputStreamReader(values));
	  
	  while(((keyreader.readLine())!=null)&&((valuereader.readLine())!=null)){
		  
        count++;
        if(count<30)
		  line_chart.addValue(Double.parseDouble(valuereader.readLine()), "INFLUENCE",keyreader.readLine());
	  
	  }
	  keyreader.close();
	  valuereader.close();
	  
	 JFreeChart lineChartObjects = ChartFactory.createLineChart(
			 "NODES Vs INFLUENCE VALUES", 
			 "NODEID",
			 "INFLUENCE VALUE",
			 line_chart, 
			 PlotOrientation.VERTICAL,
			 true, 
			 true, false);
	 int width= 640;
	 int height= 480;
	 File lineChart = new File("./LineChart.jpeg");
	 ChartUtilities.saveChartAsJPEG(lineChart,lineChartObjects , width, height);
	  
	}
}
