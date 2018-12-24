import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.List;

/**
 * @author wuweifeng wrote on 2018/4/16.
 */
public class TestSubtract {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().appName("JavaWordCount").master("local").getOrCreate();
        //spark对普通List的reduce操作
        //RDD1.subtract(RDD2)，返回在RDD1中出现，但是不在RDD2中出现的元素，不去重
        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkSession.sparkContext());
        List<Integer> one = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> two = Arrays.asList(1, 6, 7, 8, 9);
        JavaRDD<Integer> oneRDD = javaSparkContext.parallelize(one);
        JavaRDD<Integer> twoRDD = javaSparkContext.parallelize(two);

        List<Integer> results = oneRDD.subtract(twoRDD).collect();
        System.out.println(results);
    }
}
