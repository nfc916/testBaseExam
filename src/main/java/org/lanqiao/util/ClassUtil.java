package org.lanqiao.util;

import java.io.*;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 解析类名、方法是否符合规范
 */
public class ClassUtil {


        public static boolean doCheck(String [] kv){
            Map map = getRealInfo(kv);
           // System.out.println(map);
           try {
            // 创建配置文件对应的 File 对象
            File file = new File("object.properties");
            // 创建输入流，读取文件
           // InputStream is =  new FileInputStream(file);
            OutputStream os =  new FileOutputStream(file);
            // 创建 Properties 对象
            Properties prop = new Properties();
            // 将配置文件加载入 Properties 对象
          //  prop.load(is);
            // 读取配置文件中 name 字段的值
            prop.setProperty("id", "2");
            prop.setProperty("name", "mark");
            prop.setProperty("className","org.lanqiao.entity.Person");
            prop.store(os,null);
            Class obj = Class.forName("org.lanqiao.main.CreateObject");
            Method method =  obj.getMethod("createObject");

            Object result = method.invoke(obj.newInstance());
            if(result==null){
                System.out.println("结果不正确哦，返回的对象不能为空");
                return false;
            }
            Class obj2 = Class.forName("org.lanqiao.entity.Person");
            if(result.getClass()!=obj2){
                System.out.println("结果不正确哦，返回的对象不符合题目要求");
                return false;
            }

            Field[] fs = obj2.getDeclaredFields();
            for(Field f :fs ){
                f.setAccessible(true);
                if(!f.get(result).toString().equals(prop.get(f.getName()).toString())){
                     System.out.println("结果不正确哦，字段的处理没有通用性哦");
                     return false;
                }        
            }
           return true;
        } catch (RuntimeException e) {
           System.out.println(e.getMessage().equals("null")?"代码有引起空指针的地方需要处理":e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("请不要修改配置文件名称");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("请不要修改类名或包名"+e.getMessage());
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            System.out.println("请不要修改类中的方法名");
        } catch (IllegalAccessException e) {
           
        } catch (InvocationTargetException e) {
           
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            System.out.println("请不要修改类中的构造方法或确保存在无参构造");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("请确认代码是否符合题目要求");
        } 

            return false;
        }

        /**
         * 1-int,admin-String,3-long,4.5-double: new Person
         * 值1-数据类型，值2-数据类型....: 数据类型 
         * @param kv
         * @return
         */
        private static Map getRealInfo(String [] kv){
            Map map =new HashMap();
            if(kv[0].equals("void") ){

            }else if(kv[0].startsWith("new") ){


            }
            if(kv[1].equals("void") ){

            }else if(kv[1].startsWith("new") ){


            }

            System.out.println(Arrays.toString(kv)+": length is " + kv.length);
            return map;
        }

  
}
