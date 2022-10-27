package org.lanqiao.util;


import org.objectweb.asm.*;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static org.objectweb.asm.Opcodes.ACC_PRIVATE;


/**
 * 解析类名、方法是否符合规范
 */
public class ClassUtil implements ClassVisitor{
    /**
     *   获取流程 ： 考生相关依赖需要打包，
     *   明明存在，反射时确提示ClassNotFoundException
     *      1 . 不同pom的依赖
     *      2 . 引用 war 包时应分别打包成 war 和 jar ，通过配置pom.xml
     *      3.  引用 springBoot 时打包的 Springboot-maven plugin 需要删除后再打
     * @param jarNames 架包所有路径
     * @return
     */
    static Map<String,String> methodClassMap =new HashMap<>();
    static Map<String,String> fieldClassMap =new HashMap<>();
    public static Map<String,Integer> constructClassMap =new HashMap<>();
    public static int constructSize=0;
    static boolean isMethod =false;
    //////////////////////////////////

    /**
     * 是否使用某项技术
     * @param JarName
     * @param isWeb
     * @return  0 符合要求，  1 JDBC 不是， 2 Servlet 不是，  3 多编程  4 . MyBatis不是 ，5 Spring不是，  6 未引用A模块，  其它的组合 如：12 13 34 125 345
     */
    public static String isUseSkill(String JarName,boolean isWeb){
        String basePath ="javap -v jar:file://"+JarName+"!/";
        InputStream fis = null;
        int isJdbc =1; // statement  connection  resultset  DriverManager
        int isServlet=2;
        int isUseAmodule=0;
        try {
            JarFile jar =new JarFile(JarName);
            Iterator<JarEntry> je = jar.stream().iterator();
            ClassReader cr =null;
            while(je.hasNext()){
                JarEntry jae = je.next();
                if(jae.getName().endsWith(".class")&&jae.getName().startsWith("org/lanqiao")){
                    fis= jar.getInputStream(jae);
                    cr =new ClassReader(fis);
                    if(isWeb){
                        boolean jdbc1 =false;
                        boolean jdbc2 =false;
                        boolean jdbc3 =false;
                    
                        // TODO 是否JDBC  需要优化   已经优化
                        if(jae.getName().startsWith("org/lanqiao/dao/")||jae.getName().startsWith("org/lanqiao/utils/")){
                            for (String s : CommandUtil.getExecResult(basePath + jae.getName())) {
                                if(s.contains("DriverManager")){
                                    jdbc1=true;
                                }
                                /*if(s.contains("java/sql/Connection")){
                                    jdbc2=true;
                                }*/
                                if(s.contains("Connection")){
                                    jdbc2=true;
                                }
                                if(s.contains("Statement")){
                                    jdbc3=true;
                                }
                                /*if(s.contains("java/sql/ResultSet")){
                                    jdbc4=true;
                                }*/
                            }
                            if(jdbc1&&jdbc2&&jdbc3){
                                 isJdbc=0;
                            }
                        }

                        //是否Servlet
                        if(jae.getName().startsWith("org/lanqiao/controller/")||jae.getName().startsWith("org/lanqiao/servlet/")){
                            if(cr.getSuperName().equals("javax/servlet/http/HttpServlet")||cr.getSuperName().equals("javax/servlet/http/GenericServlet")){
                                 isServlet=0;
                            }
                        }
                        //是否安全
                        if(jae.getName().startsWith("org/lanqiao/service/")||jae.getName().startsWith("org/lanqiao/service/impl")){
                            for (String s : CommandUtil.getExecResult(basePath + jae.getName())) {
                                if(s.contains("ACC_SYNCHRONIZED")||s.contains("monitorenter")){
                                   
                                }
                            }
                        }
                    }else {
                        //是否mybatis
                        //是否springmvc

                    }
                    //是否引用 A 模块
                    if(jae.getName().startsWith("org/lanqiao/entity")){
                        isUseAmodule=6;
                    }

                }
            }
            jar.close();
        } catch (Exception e) {
            System.out.println("技能检测异常：原因"+e.getMessage());
        }

        return isWeb?isJdbc*1000+isServlet*100+isUseAmodule+"":"";

    }

    public static Map<String,String> getClassMethods(List<String> jarNames,boolean flag){
        isMethod=flag;
        for(String jarpath:jarNames){
          getClassMethodsByAsm(jarpath);
        }
        if(isMethod)
            return methodClassMap;
        else
            return fieldClassMap;
    }

    public static void getClassMethodsByAsm(String jarPath){

        InputStream fis = null;
        try {
            JarFile jar =new JarFile(jarPath);
            Iterator<JarEntry> je = jar.stream().iterator();
            ClassReader cr =null;
            while(je.hasNext()){
                JarEntry jae = je.next();
                if(jae.getName().endsWith(".class")&&jae.getName().startsWith(isMethod?"org/lanqiao":"org/lanqiao/entity")){
                    if(!isMethod){
                        constructSize++;
                    }
                    fis= jar.getInputStream(jae);
                    cr =new ClassReader(fis);
                    cr.accept(new ClassUtil(), 0);

                }
            }
            jar.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        //System.out.println("版本："+version+",权限："+access+",名称："+name+",参数："+signature+",父类："+superName+"，接口："+ Arrays.toString(interfaces));
        cname=name.replace("/",".");
    }

    @Override
    public void visitSource(String source, String debug) {

    }

    @Override
    public void visitOuterClass(String owner, String name, String desc) {

    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return null;
    }

    @Override
    public void visitAttribute(Attribute attr) {

    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {

    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        //System.out.println("字段名："+name+",权限："+access+",描述："+desc+",参数："+signature+ ACC_PRIVATE);
        if(!isMethod&&!name.startsWith("<")&&access!=ACC_PRIVATE){
            fieldClassMap.put(cname + "#" + name, cname);
        }
        return null;
    }
    String cname =null;
    @Override//org.lanqiao.utils.DtoUtil#fail-9org.lanqiao.utils.DtoUtil
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
       // System.out.println("方法名："+name+",权限："+access+",描述："+desc+",参数："+signature);
            if(!isMethod&&name.startsWith("<init>")&&desc.equals("()V")){
                constructClassMap.put(cname + "#" + name,1);
            }

            if(isMethod&&!name.startsWith("<"))
                methodClassMap.put(cname + "#" + name, cname);
        return null;
    }

    @Override
    public void visitEnd() {

    }
}
