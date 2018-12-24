import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}
/**
  * Created by LHX on 2018/3/20 13:26.
  */
object SparkReadFile {
  def main(args: Array[String]): Unit = {
    val localpath="E:\\BigData\\faredata2013\\trip_fare_1.csv"
//    val outpath="D:\\output\\word2"
    val conf = new SparkConf()
    conf.setAppName("SparkReadFile")
    conf.setMaster("local")
    val sparkContext = new SparkContext(conf)
    val sqlContext = new SQLContext(sparkContext)
    //读csv文件
    val data: DataFrame = sqlContext.read.format("com.databricks.spark.csv")
      .option("header", "false") //在csv第一行有属性"true"，没有就是"false"
      .option("inferSchema", true.toString) //这是自动推断属性列的数据类型
      .load(localpath)
        data.show()
    // 写csv文件
//    data.repartition(1).write.format("com.databricks.spark.csv")
//      .option("header", "false")//在csv第一行有属性"true"，没有就是"false"
//      .option("delimiter",",")//默认以","分割
//      .save(outpath)
    sparkContext.stop()
  }
}
