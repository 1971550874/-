import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class wf_exe {
    static int  totalCount;//记录总字符的个数
    public static void main(String args[]) {
        //定义一个数组对应个英文字母
        int[] a = new int[26];
        if(args.length != 0){
            if("-c".equals(args[0])){
                String s = args[1];
                readFile(s,a);
            }
        }
    }

    /**
     * 读入TXT文件
     */
    public  static void readFile(String s,int[] a) {
        try {
                FileReader reader = new FileReader(s);
                BufferedReader br = new BufferedReader(reader);
                String line;
            while ((line = br.readLine()) != null) {
                //一次读入一行数据  遍历每一行的每个字符
                for(int i = 0; i < line.length(); i++){
                    totalCount++;
                    for(int j = 0; j < 26; j++){
                        int c =(int)line.charAt(i);//把每个字符转化为asc码
                        if((c - 'a') == j || (c-'A'==j)){
                            a[j]++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("没找到文件夹！");
        }
        for(int k='a'; k<= 'z'; k++){
            char m = (char)k;//强转为字符；
            //设置百分数保留两位小数
            NumberFormat nt = NumberFormat.getPercentInstance();
            nt.setMinimumFractionDigits(2);
            float d =(float)a[k - 97] / totalCount;
            String format = nt.format(d);
            System.out.println(m+"：个数  "+ a[k-97] + "   百分比  " + format);
        }
    }
}


