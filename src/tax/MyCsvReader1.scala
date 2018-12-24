//import org.apache.spark.{SparkConf, SparkContext}
//import org.jfree.ui.RefineryUtilities
//
//object MyCsvReader1 {
//  def main(args: Array[String]): Unit = {
//    val cf = new SparkConf().setMaster("local").setAppName("GroupStr")
//    val sc = new SparkContext(cf)
//    var csvFile1 = sc.textFile("E:\\BigData\\test.csv")
//    //    var csvFile1 = sc.textFile("E:\\BigData\\tripData2013\\trip_data_1.csv\\trip_data_1.csv")
//    var header = csvFile1.first()
//    println(header)
//    csvFile1 = csvFile1.filter(row => row != header)
//    var csvData = csvFile1.map(lines => lines.split(",")).map(p => (p(7), 1))
//    var listRDDResult = csvData.reduceByKey(_ + _)
//    var aaa = listRDDResult.collect()
//
//    import org.jfree.data.category.DefaultCategoryDataset
//    val mydataset = new DefaultCategoryDataset
//    aaa.foreach(ecore => {
//      println(ecore._1 + "   " + ecore._2)
//      mydataset.addValue(ecore._2, ecore._1, ecore._1)
//    })
//
//    val chart = new BarChart_AWT("Car Usage Statistics", "Which car do you like?", mydataset)
//    chart.pack()
//    RefineryUtilities.centerFrameOnScreen(chart)
//    chart.setVisible(true)
//  }
//}