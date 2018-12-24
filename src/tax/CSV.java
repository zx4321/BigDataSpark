import au.com.bytecode.opencsv.CSVReader;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.io.IOException;
import java.io.StringReader;

class CSV {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("SparkIO");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> csvFile1 = sc.textFile("E:\\BigData\\tripData2013\\trip_data_1.csv\\trip_data_1.csv");
        JavaRDD<String[]> csvData = csvFile1.map(new ParseLine());
        csvData.foreach(x->{
                    for(String s : x){
                        System.out.println(s);
                    }
                }
        );
    }

}

class ParseLine implements Function<String,String[]>{

    private static final long serialVersionUID = 1L;

    public String[] apply(String line) {
        CSVReader reader = new CSVReader(new StringReader(line));
        String[] lineData = null;
        try {
            lineData = reader.readNext();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineData;
    }

    @Override
    public String[] call(String line) throws Exception {
        CSVReader reader = new CSVReader(new StringReader(line));
        String[] lineData = null;
        try {
            lineData = reader.readNext();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineData;
    }
}