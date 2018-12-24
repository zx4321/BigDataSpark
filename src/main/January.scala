import java.io.{File, PrintWriter}
import java.text.SimpleDateFormat

import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkConf, SparkContext}

object January {
  def main(args: Array[String]): Unit = {
    val cf = new SparkConf().setMaster("local").setAppName("GroupStr")
    val sc = new SparkContext(cf)

    //    小规模数据测试
    //    var csvFile1 = sc.textFile("E:\\BigData\\test.csv")
    //    大规模数据
    var csvFile1 = sc.textFile("E:\\BigData\\tripData2013\\trip_data_1.csv\\trip_data_1.csv")

    //        获取第一行内容，并打印第一行
    val header = csvFile1.first()
    //    println(header)

    //    去除第一行
    csvFile1 = csvFile1.filter(row => row != header)

    //    读取每一行数据
    val csvData = csvFile1.map(lines => lines.split(","))

    //    将数据缓存，后面将会多次用到，将会加快后面的处理速度
    csvData.cache()

    //    一共有多少条数据
    val dataLength = csvData.count()
    println("The total length is " + dataLength)


    //    获取每次乘车乘客的数量的统计 比如：1人：100次
    val passengerCountRDD = csvData.map(lines => (lines(7), 1))
    val passengerCount = passengerCountRDD.reduceByKey(_ + _).collect()
    //    passengerCount.foreach(lineCount => println(lineCount._1 + "  " + lineCount._2))
    //    passengerCount.saveAsTextFile("data/passengerCount")
    val passengerCountWriter = new PrintWriter(new File("passengerCount.txt"))
    passengerCount.foreach(lineCount => {
      //      println(lineCount._1 + "  " + lineCount._2)
      passengerCountWriter.write(lineCount._1 + "  " + lineCount._2 + "\n")
    })
    passengerCountWriter.close()


    //        乘客的平均数量
    var allPassengerCount: Int = 0
    for (i <- passengerCount) {
      allPassengerCount += i._1.toInt * i._2.toInt
    }
    println("The average passenger count is " + allPassengerCount.toFloat / dataLength)


    //    获取某月中每天的打车的人数
    val formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val pickUpDateTimeDayRDD = csvData.map(lines => (formatter.parse(lines(5)).getDate, 1))
    val pickUpDateTimeDay = pickUpDateTimeDayRDD.reduceByKey(_ + _).collect()
    //    pickUpDateTimeDay.foreach(lineCount => println(lineCount._1 + "  " + lineCount._2))
    //    pickUpDateTimeDay.saveAsTextFile("data/pickUpDateTimeDay")
    val pickUpDateTimeDayWriter = new PrintWriter(new File("pickUpDateTimeDay.txt"))
    pickUpDateTimeDay.foreach(lineCount => {
      //      println(lineCount._1 + "  " + lineCount._2)
      pickUpDateTimeDayWriter.write(lineCount._1 + "  " + lineCount._2 + "\n")
    })
    pickUpDateTimeDayWriter.close()

    //获取一天中各个时间段打车的人数
    val pickUpDateTimeDayOfTimeRDD = csvData.map(lines => (formatter.parse(lines(5)).getHours, 1))
    val pickUpDateTimeDayOfTime = pickUpDateTimeDayOfTimeRDD.reduceByKey(_ + _).collect()
    //    pickUpDateTimeDayOfTime.foreach(lineCount => println(lineCount._1 + "  " + lineCount._2))
    //    pickUpDateTimeDayOfTime.saveAsTextFile("data/pickUpDateTimeDayOfTime")
    val pickUpDateTimeDayOfTimeWriter = new PrintWriter(new File("pickUpDateTimeDayOfTime.txt"))
    pickUpDateTimeDayOfTime.foreach(lineCount => {
      //      println(lineCount._1 + "  " + lineCount._2)
      pickUpDateTimeDayOfTimeWriter.write(lineCount._1 + "  " + lineCount._2 + "\n")
    })
    pickUpDateTimeDayOfTimeWriter.close()


    // 获取乘车的平均时间
    val tripTimeInSecs = csvData.map(lines => lines(8).toInt).sum
    println("The average tripTimeInSecs is " + tripTimeInSecs / dataLength.toFloat)

    // 获取乘车的时间的分布（每100S）
    val PartitionTime = 100
    val tripTimeInSecsPartitionRDD = csvData.map(lines => ((lines(8).toInt + 50) / PartitionTime, 1))
    val tripTimeInSecsPartition = tripTimeInSecsPartitionRDD.reduceByKey(_ + _).sortByKey().collect()
    //    tripTimeInSecsPartition.foreach(lineCount => println(lineCount._1 + "  " + lineCount._2))
    //    tripTimeInSecsPartition.saveAsTextFile("data/tripTimeInSecsPartition")
    val tripTimeInSecsPartitionWriter = new PrintWriter(new File("tripTimeInSecsPartition.txt"))
    tripTimeInSecsPartition.foreach(lineCount => {
      //      println(lineCount._1 + "  " + lineCount._2)
      tripTimeInSecsPartitionWriter.write(lineCount._1 + "  " + lineCount._2 + "\n")
    })
    tripTimeInSecsPartitionWriter.close()

    //     获取乘车的平均距离
    val tripDistanceSum = csvData.map(lines => lines(9).toFloat).sum
    println("The average trip distance is " + tripDistanceSum / dataLength.toFloat)

    // 获取乘车的距离的分布（每1KM）
    val tripDistanceRDD = csvData.map(lines => ((lines(9).toFloat + 0.5).toInt, 1))
    val tripDistance = tripDistanceRDD.reduceByKey(_ + _).sortByKey().collect()
    //    tripDistance.foreach(lineCount => println(lineCount._1 + "  " + lineCount._2))
    //    tripDistance.saveAsTextFile("data/tripDistance")
    val tripDistanceWriter = new PrintWriter(new File("tripDistance.txt"))
    tripDistance.foreach(lineCount => {
      //      println(lineCount._1 + "  " + lineCount._2)
      tripDistanceWriter.write(lineCount._1 + "  " + lineCount._2 + "\n")
    })
    tripDistanceWriter.close()

    // 将数据集聚类,numClusters个类,numIterations次迭代,形成数据模型
    val parsedData = csvData.map(lines => Vectors.dense(lines(10).toFloat, lines(11).toFloat))
    //    parsedData.saveAsTextFile("data/pickup_longitude_and_latitude")
    val pickUpWriter = new PrintWriter(new File("pickUp.txt"))
    parsedData.collect().foreach(lineCount => {
      //      println(lineCount._1 + "  " + lineCount._2)
      pickUpWriter.write(lineCount(0) + "  " + lineCount(1) + "\n")
    })
    pickUpWriter.close()

    val numClusters = 5
    val numIterations = 10
    val KMeansCenter = KMeans.train(parsedData, numClusters, numIterations)
    //    val KMeansCenterRDD = sc.parallelize(KMeansCenter.clusterCenters)
    //    KMeansCenterRDD.saveAsTextFile("data/KMeansCenter")
    val KMeansCenterWriter = new PrintWriter(new File("KMeansCenter.txt"))
    for (i <- KMeansCenter.clusterCenters) {
      KMeansCenterWriter.write(i(0) + "  " + i(1) + "\n")
    }
    KMeansCenterWriter.close()
  }
}
