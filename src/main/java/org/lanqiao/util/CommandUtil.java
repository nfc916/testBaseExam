package org.lanqiao.util;

import java.io.*;
import java.util.*;

/**
 * 命令行工具类
 */
public class CommandUtil {

    private static final Runtime runtime=Runtime.getRuntime();


    /**
     *  工具方法一：单结果查询
     * @param cmdlines  Linux 终端中命令行集合
     * @return   执行结果 如果查询结果为空则失败
     * @throws Exception 执行失败
     */
    public static boolean checkCmdLines(String [] cmdlines) throws  Exception{
        Process  process =null;
        BufferedReader input ;
        for(String scmd:cmdlines){
            process = runtime.exec(new String[] { "zsh", "-c", scmd });
            input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if(input.readLine()==null){
                return false;
            }
        }
        return true;
    }
    /**
     *  工具方法二：执行指令
     * @param cmdlines  需要批量执行的终端操作命令集合，不能执行阻塞式命令
     * @throws Exception
     */
    public static void invockCmdLines(String [] cmdlines) throws  Exception{
        Process  process =null;
        for(String scmd:cmdlines){
            process = runtime.exec(new String[] { "zsh", "-c", scmd });
          while(process.isAlive()){
                //等待命令执行完毕，不能执行阻塞式命令
          }
        }
        Thread.sleep(1000);
    }
    /**
     *  工具方法三：多结果查询
     * @param cmdLine  Linux 终端中 MySQL 查询命令行,可能有多个结果
     * @return   执行结果 封装成集合的结果集
     * @throws Exception
     */
    public static List<String> getExecResult(String cmdLine) {
        // 创建单条语句文件
        List<String> strList = new ArrayList<String>();
        String line=null;
        try {
            Process process = runtime.exec(new String[]{"zsh", "-c", cmdLine});
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = input.readLine()) != null) {
                strList.add(line);
            }
            input.close();
        }catch (Exception e){
            System.out.println("执行 "+ cmdLine + "失败，原因："+ e.getMessage());
        }
        return strList;
    }

    /**
     *  SpringBoot项目的启动标识：只有出现“Started xxxx in  xxx seconds ” 才是启动成功
     * @param cmdLine
     * @return
     */
    public static boolean startSpringBootProject(String cmdLine){
        String line;
        try {
            Process process = runtime.exec(new String[]{"zsh", "-c", cmdLine});
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = input.readLine()) != null) {
                if(line.contains("Started")&&line.contains("in")&&line.contains("seconds")){
                    break;
                }
            }
            input.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
