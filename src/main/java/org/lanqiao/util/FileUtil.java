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
        public static String baseUrl ="http://127.0.0.1:8080";
        public static String [] modulenames;
        public static String [] packagenames = {"org.lanqiao.entity","org.lanqiao.service","org.lanqiao.controller","org.lanqiao.dao"};
        public static String [] webrequesturls;
        public static String [] ssmrequesturls;
        public static String [] bootrequesturls;
        public static String [] testwebrequesturls;
        public static String [] testssmrequesturls;
        public static String [] testbootrequesturls;
        public static String [] webactionmethods;
        public static String [] ssmactionmethods;
        public static String [] bootactionmethods;
        public static String [] allRequestUrl;
        public static String  machinekey;
        public static String  dbname ;
        public static String  rediskey ;
        public static String  redisLv2key ;
        public static String  logpath ;
        public static String  gitlogpath ;
        public static String  bashPackageName ;
        public static int tablesize ;
        public static int minsqlcount ;
        public static String [] tablenames;
        public static int [] checkpointscores;
        public static int [] requesturlscores;
        public static boolean [] testregex;
        public static Integer [] skillpointids;
        public static double [] skillpointpassornots;
        public static String [][] tablesfieldnames;
       
        public static String [] modulejars;
        public static String startupTomcat;
        public static String shutdownTomcat;
        public static String scoreEnv; //上报分数的环境
        public static String userIdEnv; // 获取用户id的环境
        public static String islocaltest; // 是否本地测试
        public static String dbbuildcommand; // 是否需要初始化数据
        public static String [] dbactions;
        public static String [] dbcommands = new String[3];
        public static String [] unittestmethods;
        public static String [] unittestdao;
        public static String [] unittestservice;
        public static String  unitestpackage;
        public static String [] loglevel;
        public static String  examname;
        public static String  redissplit;

        static{
                // 读取库名  表名  字段名  全路径类名（含包名）
                Properties prop =new Properties();
                try {

                        prop.load(FileUtil.class.getClassLoader().getResourceAsStream("baseSettings.properties"));
                         prop.load(FileUtil.class.getClassLoader().getResourceAsStream("dbSettings.properties"));
                        tablenames=prop.getProperty("tablenames").trim().split(",");
                        tablesize= tablenames.length;
                        dbname=prop.getProperty("dbname").trim();
                        rediskey=prop.getProperty("rediskey").trim();

                        unitestpackage=prop.getProperty("unitestpackage").trim();
                        redisLv2key=prop.getProperty("redisLv2key").trim();
                        bashPackageName=prop.getProperty("bashPackageName").trim();
                        dbbuildcommand=prop.getProperty("dbbuildcommand").trim();

                        logpath=prop.getProperty("logpath").trim();
                        gitlogpath=prop.getProperty("gitlogpath").trim();
                        minsqlcount=Integer.parseInt(prop.getProperty("minsqlcount").trim());
                        machinekey=prop.getProperty("machinekey").trim();
                        tablenames=prop.getProperty("tablenames").trim().split(",");

                        unittestmethods=prop.getProperty("unittestmethods").trim().split(",");
                        unittestdao=prop.getProperty("unittestdao").trim().split(",");
                        unittestservice=prop.getProperty("unittestservice").trim().split(",");
                        String [] cs =prop.getProperty("checkpointscores").trim().split(",");
                        String [] rs =prop.getProperty("requesturlscores").trim().split(",");
                        String [] tr =prop.getProperty("testregex").trim().split(",");
                        checkpointscores = new int[cs.length];
                        requesturlscores = new int [rs.length];
                        testregex = new boolean[tr.length];
                        islocaltest=prop.getProperty("islocaltest").trim();
                        for(int i=0;i<cs.length;i++){
                                checkpointscores[i]=Integer.parseInt(cs[i]);
                        }
                        for(int i=0;i<rs.length;i++){
                                requesturlscores[i]=Integer.parseInt(rs[i]);
                        }
                        for(int i=0;i<tr.length;i++){
                                testregex[i]=Boolean.parseBoolean(tr[i]);
                        }
                        String [] si =prop.getProperty("skillpointid").trim().split(",");
                        skillpointids = new Integer[si.length];
                        for(int i=0;i<si.length;i++){
                                skillpointids[i]=Integer.valueOf(si[i]);
                        }
                        String [] pn =prop.getProperty("skillpointpassornot").trim().split(",");
                        skillpointpassornots = new double[pn.length];
                        for(int i=0;i<pn.length;i++){
                                skillpointpassornots[i]=Double.parseDouble(pn[i]);
                        }
                        String [] mj =prop.getProperty("modulejar").trim().split(",");
                        modulejars = new String[mj.length];
                        for(int i=0;i<mj.length;i++){
                                modulejars[i]=mj[i];
                        }
                        modulenames=prop.getProperty("modulenames").trim().split(",");
                        webrequesturls=prop.getProperty("webrequesturls").trim().split(",");
                        ssmrequesturls=prop.getProperty("ssmrequesturls").trim().split(",");
                        bootrequesturls=prop.getProperty("bootrequesturls").trim().split(",");
                        testwebrequesturls=prop.getProperty("testwebrequesturls").trim().split(",");
                        testssmrequesturls=prop.getProperty("testssmrequesturls").trim().split(",");
                        testbootrequesturls=prop.getProperty("testbootrequesturls").trim().split(",");
                        webactionmethods=prop.getProperty("webactionmethods").trim().split(",");
                        ssmactionmethods=prop.getProperty("ssmactionmethods").trim().split(",");
                        bootactionmethods=prop.getProperty("bootactionmethods").trim().split(",");

                        loglevel =prop.getProperty("loglevel").trim().split(",");

                        allRequestUrl = new String[webrequesturls.length+ssmrequesturls.length+ bootrequesturls.length];
                        System.arraycopy(webrequesturls, 0, allRequestUrl, 0, webrequesturls.length);
                        System.arraycopy(ssmrequesturls, 0, allRequestUrl, webrequesturls.length,ssmrequesturls.length);
                        System.arraycopy(bootrequesturls, 0, allRequestUrl, ssmrequesturls.length+webrequesturls.length, bootrequesturls.length);
                        packagenames=prop.getProperty("packagenames").trim().split(",");
                        tablesfieldnames=new String[tablesize][];
                        for(int i=0;i<tablesize;i++){
                                tablesfieldnames[i]= prop.getProperty("table"+i+"fieldnames").trim().split(",");
                        }

                        dbactions= prop.getProperty("dbactions").trim().split(",");
                        for(int i=0;i<3;i++){
                                dbcommands[i]= prop.getProperty("dbcommand"+i).trim();
                        }

                       
                        //    exam_id = prop.getProperty("exam_id").trim();
                        //    container_id = prop.getProperty("container_id").trim();
                        startupTomcat = prop.getProperty("startuptomcat").trim();
                        shutdownTomcat = prop.getProperty("shutdowntomcat").trim();
                        scoreEnv = prop.getProperty("scoreenv").trim();
                        userIdEnv = prop.getProperty("useridenv").trim();

                        examname=prop.getProperty("examname").trim();
                        redissplit=prop.getProperty("redissplit").trim();

                } catch (IOException e) {
                        System.out.println("静态加载出错");
                }
                getAllPaths();

        }

        public static  Map<String,List<String>> pathNamesMap;
        public static void getAllPaths (){
                pathNamesMap = new HashMap<String,List<String>> ();
                for(String name:modulenames){
                        List<String> pathNames = new ArrayList<>();
                        pathNamesMap.put(name,pathNames);
                        File dir = new File("/home/project/IdeaProjects/"+name+"/src");
                        findJavaFileList(dir,pathNames);
                }
        }

        /**
         * 读取目录下的所有 Java 类文件
         * @param dir 目录
         * @return
         */
        public static void findJavaFileList(File dir,List<String> pathNames) {
                if (!dir.exists() || !dir.isDirectory()) {
                        return;
                }
                String[] files = dir.list();
                for (int i = 0; i < files.length; i++) {
                        File file = new File(dir, files[i]);
                        if (file.isFile()&&file.getName().endsWith(".java")) {
                                pathNames.add(dir + File.separator + file.getName());
                        } else {// 如果是目录
                                findJavaFileList(file,pathNames);
                        }
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
