
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * 功能二：
 */
public class wf_exe_03 {
    public static void main(String[] args) throws IOException {
        if ("-d".equals(args[0])){
            readMkdirFile(args[1]);
        }else if ("-d-s".equals(args[0])){
            readMkdir(args[1]);
        }
    }

    /**
     * 读取文件夹下的所有文件
     * @param s 文件
     * @throws IOException
     */

    public static void readMkdirFile(String s) throws IOException {

        HashMap<String,Integer> hashMap =new HashMap<String,Integer>();
        File[] fileList = getCurFilesList(s);
        for (File file : fileList) {
            if (file != null)
                readFile_word(file.getAbsolutePath(), hashMap);
        }

        //遍历hashmap，输出
        int sum = 0;
        for (String k:hashMap.keySet())
            sum++;
        while (sum-- > 0) {
            Iterator<String> iterator = hashMap.keySet().iterator();
            int max = 0;
            String maxword = null;
            while (iterator.hasNext()) {
                String wd = iterator.next();
                if (hashMap.get(wd) > max) {
                    max = hashMap.get(wd);
                    maxword = wd;
                }
            }
            hashMap.remove(maxword);
            //double ans=max*1.0/sum*100;
            if (!"".equals(maxword)) {
                System.out.format("%-20s : %d\n", maxword, max);
                //n--;
            }
        }
    }

    //遍历文件读取单词放入list数组
    public static void readFile_word(String s, HashMap<String,Integer> hashMap) throws IOException {
        FileReader reader = new FileReader(s);
        //缓存数组
        BufferedReader bf = new BufferedReader(reader);
        List<String> list = new ArrayList<String>();
        String line;
        while ((line = bf.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        String receiveString = join(list, "");//数组拼接成字符串

        //将字符串按照空格切割
        String[] word = receiveString.split(" ");
        int i = 0;

        //去除单词中参杂的其他的东西
        while (i < word.length) {
            if (!word[i].matches("^[a-zA-Z]{0,}$")) {
                char[] chars = word[i].toCharArray();
                int j = 0;
                for (j = 0; j < chars.length; j++) {
                    if (!(chars[j] >= 'a' && chars[j] <= 'z' || chars[j] >= 'A' && chars[j] <= 'Z')) {
                        String[] str = word[i].split(String.valueOf(chars[j]));
                        StringBuffer sb = new StringBuffer();
                        for (int k = 0; k < str.length; k++) {
                            sb.append(str[k]);
                        }
                        word[i] = sb.toString();
                    }
                }
            }
            i++;
        }

        Set<String> wordSet = hashMap.keySet();

        for (int x = 0; x < word.length; x++) {
            if (wordSet.contains(word[x])) {
                Integer number = hashMap.get(word[x]);
                hashMap.replace(word[x],number, ++number);
            } else {
                hashMap.put(word[x], 1);
            }
        }
    }
    //将list数组转化为字符串
    public static String join(Collection var0, String var1) {
        StringBuffer var2 = new StringBuffer();
        for(Iterator var3 = var0.iterator(); var3.hasNext(); var2.append((String)var3.next())) {
            if (var2.length() != 0) {
                var2.append(var1);
            }
        }

        return var2.toString();
    }
    /*=============================================================*/
    // -d -s

    public static void readMkdir(String s) throws IOException {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();

        List<String> list = new ArrayList<String>();
        getAllFileName(s , list);
        for (String str : list) {
            readFile_word(str , hashMap);
        }
        int sum = 0;

        for (String k:hashMap.keySet())
            sum++;

        while (sum-- > 0) {
            Iterator<String> iterator = hashMap.keySet().iterator();
            int max = 0;
            String maxword = null;
            while (iterator.hasNext()) {
                String wd = iterator.next();
                if (hashMap.get(wd) > max) {
                    max = hashMap.get(wd);
                    maxword = wd;
                }
            }
            hashMap.remove(maxword);
            //double ans=max*1.0/sum*100;
            if (!"".equals(maxword)) {
                System.out.format("%-20s : %d\n", maxword, max);
                //n--;
            }
        }
    }

    //获取文件夹下的文件
    public static File[] getCurFilesList(String filePath) {
            File mkdir = new File(filePath);
                File[] files = mkdir.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return ! pathname.isDirectory();
                    }
                });
                return files;
    }

    //递归文件夹找到所有的文件
    public static void getAllFileName(String path, List<String> fileName)
    {
        File file = new File(path);
        File [] files = file.listFiles();

        if(file.isDirectory()){
            File[] filesList = getCurFilesList(path);
            for (File a : filesList) {
                    fileName.add(a.getAbsolutePath());
            }

            for(File a:files)
            {
                if(a.isDirectory())
                {
                    getAllFileName(a.getAbsolutePath(), fileName);
                }
            }
        }else {
            System.out.println("请输入文件夹！！！");
        }
    }
}
