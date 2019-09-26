import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.prefs.BackingStoreException;

public class wf_exe {
    static int  totalCount; //记录总字符的个数
    public static void main(String args[]) {
        //定义一个数组对应26个英文字母
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
        String[] b = new String[26];
        seqenc(a,b);
        for(int i = 0; i<=a.length; i++){
            a[i] = Integer.parseInt(b[i].substring(3,b[i].length()));
            //设置百分数保留两位小数
            NumberFormat nt = NumberFormat.getPercentInstance();
            nt.setMinimumFractionDigits(2);
            float d =(float)a[i] / totalCount;
            String format = nt.format(d);
            System.out.printf("%-10s",b[i]);
            System.out.println("百分比  " + format);
        }
    }
    //排序
    public static void seqenc(int[] a,String[] b){
        //字符数组存放26个英文字母和个数；
        for (int i = 0;i < a.length; i++ ){
            b[i] = (char)(i+97)+": "+a[i];
        }
        for(int i=0;i <a.length;i++){
            String tem = null;
            // 内层for循环控制相邻的两个元素进行比较
            for(int j=i+1;j < a.length;j++){
                int c = Integer.parseInt(b[i].substring(3,b[i].length()));
                int d = Integer.parseInt(b[j].substring(3,b[j].length()));
                if(c < d){
                    tem = b[j];
                    b[j]= b[i];
                    b[i] = tem;
                }
            }
        }
    }
}


