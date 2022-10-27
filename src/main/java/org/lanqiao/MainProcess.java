package org.lanqiao;

import org.lanqiao.util.ClassUtil;
import org.lanqiao.util.CommandUtil;
import org.lanqiao.util.FileUtil;

public class MainProcess {
            // 对args参数进行一定的处理和规范定义
        static int score = 100 ;    

        public static void main(String[] args) {
            // 可能有通用检测
            // 前端还是后端
            if("0".equals(FileUtil.type)){
                    // 默认后端检测  0 代表 true  1 false 
                    //1 是否存在指定文件 每次指定文件不同，放到配置文件中
                    for(String filename:FileUtil.all_need_files){
                            if(!FileUtil.hasFile(filename)){
                                System.out.println("不存在指定文件或被修改");
                                score = 0;
                                return;
                            }
                    }
             
                    try {
                        //1.5 复制指定目录下文件到指定位置
                        CommandUtil.invockCmdLines(new String[]{"cp -rf "+FileUtil.base_path +"* "+FileUtil.workdir});
                        //2 是否存在指定功能 (有无修改原始文件,或将原始文件复制到指定位置进行替换)  
                        //3 是否可以编译 必须可以编译
                        CommandUtil.invockCmdLines(new String[]{"javac **/*.java "});    
                        //4 是否可以运行
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                         e.printStackTrace();
                     }    
                    
                    //5 是否分步  循环
                    for(int i = 0 ;i<FileUtil.steps;i++){
                            //是否需要前置操作
                            //前置操作
                            //6 运行结果是否正确
                           ClassUtil.doCheck(FileUtil.step_key_fun_map.get("setp"+i));
                            //是否需要扫尾操作
                            //扫尾操作
                    }
        
                    //7 评分发送报告
            }
        }

}
