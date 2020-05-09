import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import au.com.bytecode.opencsv.CSVReader;

public class Temp extends JFrame {

    private static final long serialVersionUID = 1L;
    XYSeriesCollection dataset;
    JFreeChart chart;
    final ChartPanel chartPanel;
    final int chartWidth = 560;
    final int chartHeight = 367;
    CSVReader reader;
    String[] readNextLine;
    XYSeries series;

        Temp(String applicationTitle) throws IOException {
        super(applicationTitle);

        dataset = createDataset();
        chart = createChart(dataset);
        chartPanel = new ChartPanel(chart);

         this.add(chartPanel);
         this.pack();
         this.setVisible(true);
    }

    public XYSeriesCollection createDataset() throws NumberFormatException,
            IOException {
        dataset = new XYSeriesCollection();
        try {
            reader = new CSVReader(new FileReader("DMCW.csv"),',');
            // Read the header and chuck it away
            readNextLine = reader.readNext();

            // Set up series
            final XYSeries seriesX = new XYSeries("Temperature");


            while ((readNextLine = reader.readNext()) != null) {

                // add values to dataset
                if(!readNextLine[10].isEmpty()){
                double cases = Double.valueOf(readNextLine[10]);
                double temp = Double.valueOf(readNextLine[17]);

                seriesX.add(cases, temp);}

            }

            System.out.println(seriesX.getMaxX() + "; " + seriesX.getMaxY());

            dataset.addSeries(seriesX);

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }

        return dataset;
    }

    public JFreeChart createChart(XYDataset dataset)
            throws NumberFormatException, IOException {
        chart = ChartFactory.createXYLineChart("Temperature vs Number of Cases", // chart
                                                                        // title
                "Number of Cases", // domain axis label
                "Temperatute", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // the plot orientation
                true, // legend
                true, // tooltips
                false); // urls

        return chart;
    }

    public static void main(String[] args) throws IOException {

    }
}
