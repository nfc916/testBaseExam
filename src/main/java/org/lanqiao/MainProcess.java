package org.lanqiao;

import org.lanqiao.util.FileUtil;

public class MainProcess {
            // 对args参数进行一定的处理和规范定义
    
        public static void main(String[] args) {
            // 前端还是后端
            // 默认后端检测  0 代表 true  1 false 
            //1 是否存在指定文件 每次指定文件不同，放到配置文件中
            //1.5 复制指定目录下文件到指定位置
            //2 是否存在指定功能 (有无修改原始文件)
            //3 是否可以编译
            //4 是否可以运行
            //5 是否分步  循环
            //是否需要前置操作
            //前置操作
            //6 运行结果是否正确
            //是否需要扫尾操作
            //扫尾操作
            //7 评分发送报告
          
            FileUtil.getAllPaths();
        }

}
