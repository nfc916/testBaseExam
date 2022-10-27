package org.lanqiao.dto;

import java.io.Serializable;

/**
 * 数据传输对象（后端输出对象）
 * @param <T>
 */
public class Dto<T> implements Serializable{
    private Integer user_id;// 考试者id
    private T pass_detail; //具体返回数据内容(pojo、自定义VO、其他)

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public T getPass_detail() {
        return pass_detail;
    }

    public void setPass_detail(T pass_detail) {
        this.pass_detail = pass_detail;
    }

    @Override
    public String toString() {
        return "Dto{" +
                "user_id=" + user_id +
                ", pass_detail=" + pass_detail +
                '}';
    }
}