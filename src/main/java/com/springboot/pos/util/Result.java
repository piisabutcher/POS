package com.springboot.pos.util;

import java.io.Serializable;
import java.util.List;

public class Result<T> implements Serializable {
    private int code;
    private boolean isSuccess;
    private String msg;
    private T data;
    private Iterable<T> dataList;

    /**
     * 无参构造器
     * @return
     */
    public Result(){
    }

    /**
     *
     */
    public Result(int code, boolean isSuccess, String msg){
        this.code = code;
        this.isSuccess = isSuccess;
        this.msg = msg;
    }

    /**
     * 构造函数，数据集为单个对象
     * @param code
     * @param msg
     * @param data
     * @param isSuccess
     */
    public Result(int code, String msg, T data, boolean isSuccess){
        this(code, isSuccess,msg);
        this.setData(data);
    }

    /**
     * 构造函数，数据集为对象列表
     * @param code
     * @param msg
     * @param dataList
     * @param isSuccess
     */
    public Result(int code, String msg, Iterable<T> dataList, boolean isSuccess){
        this(code, isSuccess, msg);
        this.setDataList(dataList);
    }

    /**
     * 请求数据成功
     * @param data
     */
    public static<T> Result<T> success(T data){
        return new Result<T>(200, "OK", data, true);
    }

    /**
     * 请求数据成功
     * @param dataList
     */
    public static<T> Result<T> success(Iterable<T> dataList){
        return new Result<T>(200, "OK", dataList, true);
    }

    /**
     * 失败时
     * @param code
     */
    public static<T> Result<T> error(int code){
        switch (code){
            case 204: return new Result<T>(204,false,"未获取到内容");
            case 401: return new Result<T>(401,false,"没有权限的用户");
            case 500: return new Result<T>(500,false,"服务器异常");
            case 501: return new Result<T>(501,false,"餐桌已满，请稍后");
            case 502: return new Result<T>(502,false,"该菜品已经上菜");
            case 503: return new Result<T>(5023,false,"超过该桌子最大用餐人数，请更换桌子");
            default: return new Result<T>(500,false,"服务器异常");
        }
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Iterable<T> getDataList() {
        return dataList;
    }

    public void setDataList(Iterable<T> dataList) {
        this.dataList = dataList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
