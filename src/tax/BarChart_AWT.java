//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.category.CategoryDataset;
//import org.jfree.data.category.DefaultCategoryDataset;
//import org.jfree.ui.ApplicationFrame;
//import org.jfree.ui.RefineryUtilities;
//
//public class BarChart_AWT extends ApplicationFrame {
//    public BarChart_AWT(String applicationTitle, String chartTitle,CategoryDataset dataset) {
//        super(applicationTitle);
//        JFreeChart barChart = ChartFactory.createBarChart(
//                chartTitle,
//                "Category",
//                "Score",
////                createDataset(),
//                dataset,
//                PlotOrientation.VERTICAL,
//                true, true, false);
//
//        ChartPanel chartPanel = new ChartPanel(barChart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
//        setContentPane(chartPanel);
//    }
//
//    public CategoryDataset createDataset() {
//        final String fiat = "FIAT";
//        final String audi = "AUDI";
//        final String ford = "FORD";
//        final String speed = "Speed";
//        final DefaultCategoryDataset dataset =
//                new DefaultCategoryDataset();
//
//        dataset.addValue(1.0, fiat, speed);
//
//        dataset.addValue(5.0, audi, speed);
//
//        dataset.addValue(4.0, ford, speed);
//
//        return dataset;
//    }
//
//    public static void main(String[] args) {
//        final DefaultCategoryDataset dataset =
//                new DefaultCategoryDataset();
//        BarChart_AWT chart = new BarChart_AWT("Car Usage Statistics", "Which car do you like?",dataset);
//        chart.pack();
//        RefineryUtilities.centerFrameOnScreen(chart);
//        chart.setVisible(true);
//    }
//}