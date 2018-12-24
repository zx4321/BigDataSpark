import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class SimpleMap {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("JavaWordCount");
        JavaSparkContext javaSparkContext = new JavaSparkContext(conf);
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> originRDD = javaSparkContext.parallelize(data);
        System.out.println(originRDD.reduce((a, b) -> a + b));

        //将原始元素每个都乘以2
        JavaRDD<Integer> doubleRDD = originRDD.map(s -> s * 2);
        //将RDD收集起来，组成list
        List<Integer> doubleData = doubleRDD.collect();
        System.out.println(doubleData);
//        //使用map将key变成key-value，添加value
//        List<String> list = Arrays.asList("af", "bbbb", "c", "d", "e");
//        JavaRDD<String> stringRDD = javaSparkContext.parallelize(list);
//        //转为key-value形式
//        String k = "1";
//        JavaPairRDD pairRDD = stringRDD.mapToPair(k -> new Tuple2<>(k, 1));
//        List list1 = pairRDD.collect();
//        //[(af,1), (bbbb,1), (c,1), (d,1), (e,1)]
//        System.out.println(list1);
    }
}
