import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class wf_exe {
    static int  totalCount;//��¼���ַ��ĸ���
    public static void main(String args[]) {
        //����һ�������Ӧ��Ӣ����ĸ
        int[] a = new int[26];
        if(args.length != 0){
            if("-c".equals(args[0])){
                String s = args[1];
                readFile(s,a);
            }
        }
    }

    /**
     * ����TXT�ļ�
     */
    public  static void readFile(String s,int[] a) {
        try {
                FileReader reader = new FileReader(s);
                BufferedReader br = new BufferedReader(reader);
                String line;
            while ((line = br.readLine()) != null) {
                //һ�ζ���һ������  ����ÿһ�е�ÿ���ַ�
                for(int i = 0; i < line.length(); i++){
                    totalCount++;
                    for(int j = 0; j < 26; j++){
                        int c =(int)line.charAt(i);//��ÿ���ַ�ת��Ϊasc��
                        if((c - 'a') == j || (c-'A'==j)){
                            a[j]++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("û�ҵ��ļ��У�");
        }
        for(int k='a'; k<= 'z'; k++){
            char m = (char)k;//ǿתΪ�ַ���
            //���ðٷ���������λС��
            NumberFormat nt = NumberFormat.getPercentInstance();
            nt.setMinimumFractionDigits(2);
            float d =(float)a[k - 97] / totalCount;
            String format = nt.format(d);
            System.out.println(m+"������  "+ a[k-97] + "   �ٷֱ�  " + format);
        }
    }
}


