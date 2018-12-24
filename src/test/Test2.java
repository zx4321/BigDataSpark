import java.io.BufferedReader;
import java.io.FileReader;

public class Test2 {
    public void test2(int row, int col) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("E:\\BigData\\tripData2013\\trip_data_1.csv\\trip_data_1.csv"));//换成你的文件名
//            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            System.out.println("到这里");
            String line = null;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                if (index == col) {
                    break;
                }
                index++;
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Test2 test = new Test2();
        test.test2(1, 100);
    }
}