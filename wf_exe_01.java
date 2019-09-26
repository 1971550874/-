import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.prefs.BackingStoreException;

public class wf_exe {
    static int  totalCount; //��¼���ַ��ĸ���
    public static void main(String args[]) {
        //����һ�������Ӧ26��Ӣ����ĸ
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
        String[] b = new String[26];
        seqenc(a,b);
        for(int i = 0; i<=a.length; i++){
            a[i] = Integer.parseInt(b[i].substring(3,b[i].length()));
            //���ðٷ���������λС��
            NumberFormat nt = NumberFormat.getPercentInstance();
            nt.setMinimumFractionDigits(2);
            float d =(float)a[i] / totalCount;
            String format = nt.format(d);
            System.out.printf("%-10s",b[i]);
            System.out.println("�ٷֱ�  " + format);
        }
    }
    //����
    public static void seqenc(int[] a,String[] b){
        //�ַ�������26��Ӣ����ĸ�͸�����
        for (int i = 0;i < a.length; i++ ){
            b[i] = (char)(i+97)+": "+a[i];
        }
        for(int i=0;i <a.length;i++){
            String tem = null;
            // �ڲ�forѭ���������ڵ�����Ԫ�ؽ��бȽ�
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


