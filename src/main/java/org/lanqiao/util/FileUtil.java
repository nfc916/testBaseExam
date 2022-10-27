package org.lanqiao.util;


import java.io.*;
import java.util.*;

/**
 * 读取配置文件内容的工具类
 * # 数据库名  dbname=bekdb
 * # 表数量 tablesize=8
 * # 表名列表 tablenames=userinfo,linkuserinfo,cakeinfo,cakeclassinfo,cakescaleinfo,orders,orderdetail,scaleinfo
 * # 指定表字段 table0fieldnames=
 * # 指定表指定字段约束   fieldconstraint=userinfo-id:unique
 * # 模块名 有默认值  modulenams=
 * # 包名 packagenames=
 * # 微信机器人  machinekey=0346fec1-d978-4968-a8a5-81dd0281a7ce
 */
public class FileUtil {
        public static String base_path;
        public static String [] all_need_files;
        public static int steps;
        public static int []step_score;
        public static Map<String,String []> step_key_fun_map = new HashMap<String,String []>();
        public static String []all_need_reflect_class;
        public static String before;
        public static String type;
        public static String workdir;
        static{
                // 读取库名  表名  字段名  全路径类名（含包名）
                Properties prop =new Properties();
                try {
                    prop.load(FileUtil.class.getClassLoader().getResourceAsStream("baseSetings.properties"));
                    prop.load(FileUtil.class.getClassLoader().getResourceAsStream("funSetings.properties"));
                    base_path = prop.getProperty("base_path");
                    all_need_files = prop.getProperty("all_need_files").split(",");
                    steps = Integer.parseInt(prop.getProperty("steps"));
                    step_score = new int[steps];
                    for(int i =0;i<steps;i++){
                       step_score[i]=Integer.parseInt(prop.getProperty("step_score").split(",")[i]);
                  
                       step_key_fun_map.put("setp"+i,prop.getProperty("setp"+i).split(":"));
                    }
                 
                    all_need_reflect_class = prop.getProperty("all_need_reflect_class").split(",");
                    before = prop.getProperty("before");
                    type = prop.getProperty("type");
                    workdir = prop.getProperty("workdir");
                } catch (IOException e) {
                        System.out.println("静态加载出错");
                }

        }

        /**
         * 读取指定目录下的是否存在指定文件
         * @return
         */
        public static boolean hasFile(String pathName) {
                File file = new File(pathName);
                return file.exists()&&file.isFile();
        }


        /**
         * 读取指定目录下的日志文件是否存在指定内容
         * @return
         */
        public static boolean hasContent(String pathName,String [] keys) {
                File file = new File(pathName);
                BufferedReader br =null;
                try {
                    br=    new BufferedReader(new FileReader(file));
                        String s =null;
                        while((s=br.readLine())!=null){
                                for(String key:keys){
                                        if(s.contains(key)){
                                                return true;
                                        }
                                }
                        }
                    
                } catch (Exception e) {

                         e.printStackTrace();

                }finally{
                    try {
                        br.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                return false;
        }

}
