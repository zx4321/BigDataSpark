import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SimpleMapPartition {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("JavaWordCount");
        JavaSparkContext javaSparkContext = new JavaSparkContext(conf);
        //使用map将key变成key-value，添加value
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        //分为2个分区
        JavaRDD<Integer> stringRDD = javaSparkContext.parallelize(list, 2);
        //与map方法类似，map是对rdd中的每一个元素进行操作，而mapPartitions(foreachPartition)则是对rdd中的每个分区的迭代器进行操作。
        // 如果在map过程中需要频繁创建额外的对象,(例如将rdd中的数据通过jdbc写入数据库,map需要为每个元素创建一个链接而mapPartition为每个partition创建一个链接),
        // 则mapPartitions效率比map高的多。
        JavaRDD rdd = stringRDD.mapPartitions(new FlatMapFunction<Iterator<Integer>, Integer>() {

            @Override
            public Iterator<Integer> call(Iterator<Integer> integerIterator) throws Exception {
                int sum = 0;
                while (integerIterator.hasNext()) {{
                    sum += integerIterator.next();
                }}
                List<Integer> list1 = new LinkedList<>();
                list1.add(sum);
                return list1.iterator();
            }
        });

        List list1 = rdd.collect();
        //[3, 12]
        System.out.println(list1);

    }
}
