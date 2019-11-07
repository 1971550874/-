import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.text.NumberFormat;
import java.util.*;

public class wf_exe {
    static int  totalCount; //记录总字符的个数
    public static void main(String args[]) throws FileNotFoundException {
        //定义一个数组对应26个英文字母
        int[] a = new int[26];
        if(args.length != 0){
            //读取字母
            if("-c".equals(args[0])){
                String s = args[1];
                readFile_letter(s,a);
            }
            //读取单词
            else if ("-f".equals(args[0])){
                readFile_word(args[1]);
            }
        }
    }

    /**
     * 读入TXT文件
     */
    public  static void readFile_letter(String s,int[] a) {
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
            System.out.printf("%-20s",b[i]);
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

    /* -f:===============================================================================*/
    /**
     * 读取txt_单词
     */
    public static void readFile_word(String s)  {

        FileReader reader = null;
        List<String> list = null;
        try {
                reader = new FileReader(s);
                //缓存数组
                BufferedReader bf = new BufferedReader(reader);
                list = new ArrayList<String>();
                String line;
                while ((line = bf.readLine()) != null){
                    list.add(line);
            }
        } catch (Exception e) {
            System.out.println("读取文件失败");
        }finally {
            try {
                reader.close();
            } catch (IOException e) { }
        }
        String receiveString = StringUtils.join(list, "");//数组拼接成字符串

        //将字符串按照空格切割
        String[] word = receiveString.split(" ");
        int i = 0;

        //去除单词中参杂的其他的东西
        while (i < word.length){
            if (! word[i].matches("^[a-zA-Z]{0,}$")){
                char[] chars = word[i].toCharArray();
                int j = 0;
                for (j = 0;j < chars.length; j++){
                    if (!(chars[j] >= 'a' && chars[j] <='z' || chars[j] >= 'A' && chars[j] <='Z')){
                        String[] str = word[i].split(String.valueOf(chars[j]));
                        StringBuffer sb = new StringBuffer();
                        for(int k = 0; k < str.length; k++){
                            sb. append(str[k]);
                        }
                        word[i] = sb.toString();
                    }
                }
            }
            i++;
        }

        HashMap<String,Integer> hashMap=new HashMap<String,Integer>();
        Set<String> wordSet=hashMap.keySet();
        /*if(wordSet.contains(lineWords[i])) {//判断set集合里是否有该单词
            Integer number=hashMap.get(lineWords[i]);//若有次数+1
            number++;
            hashMap.put(lineWords[i], number);
        }
        else {//没有就将其放入set里，次数为1
            hashMap.put(lineWords[i], 1);
        }*/
        for(int x = 0; x < word.length; x++){
            if (wordSet.contains(word[x])){
                Integer number = hashMap.get(word[x]);
                number++;
                hashMap.put(word[x],number);
            }else {
                hashMap.put(word[x], 1);
            }
        }
        int sum = 0;
        Iterator<String> it=hashMap.keySet().iterator();
        while(it.hasNext()){
            sum += hashMap.get(it.next());
        }

        while(sum-- >0)
        {
            Iterator<String> iterator=hashMap.keySet().iterator();
            int max=0;
            String maxword=null;
            while(iterator.hasNext()){
                String wd=iterator.next();

                if(hashMap.get(wd)>max) {
                    max=hashMap.get(wd);
                    maxword=wd;
                }
            }
            hashMap.remove(maxword);
            double ans=max*1.0/sum*100;
            if(!maxword.equals("")) {
                System.out.format("%-20s : %d\n", maxword, max);
                //n--;
            }
        }



    }









    /*public static void readFile_word(String s) throws FileNotFoundException {
        File file=new File(s);
        if(!file.exists()){
            System.out.println("未找到文件");
            return;
        }
        Scanner in = new Scanner(System.in);
        //System.out.println("请输入前n个常用单词");
        //int n=in.nextInt();
        Scanner scanner = new Scanner(file);
        HashMap<String,Integer> hashMap=new HashMap<String,Integer>();
        while(scanner.hasNextLine()) {
            String line=scanner.nextLine();
            String[] lineWords=line.split("\\s+");
            Set<String> wordSet=hashMap.keySet();
            for(int i=0;i<lineWords.length;i++) {
                if(wordSet.contains(lineWords[i])) {//判断set集合里是否有该单词
                    Integer number=hashMap.get(lineWords[i]);//若有次数+1
                    number++;
                    hashMap.put(lineWords[i], number);
                }
                else {//没有就将其放入set里，次数为1
                    hashMap.put(lineWords[i], 1);
                }
            }
        }
        //计算总体单词数
        int sum=0;
        Iterator<String> it=hashMap.keySet().iterator();
        while(it.hasNext()){
            sum+=hashMap.get(it.next());
        }
        //输出前n个单词
        while(sum-- >0)
        {
            Iterator<String> iterator=hashMap.keySet().iterator();
            int max=0;
            String maxword=null;
            while(iterator.hasNext()){
                String word=iterator.next();

                if(hashMap.get(word)>max) {
                    max=hashMap.get(word);
                    maxword=word;
                }
            }
            hashMap.remove(maxword);
            double ans=max*1.0/sum*100;
            if(!maxword.equals("")) {
                System.out.println(maxword+":"+max);
                //n--;
            }
        }

    }*/
}
