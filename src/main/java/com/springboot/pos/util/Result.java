package com.springboot.pos.util;

import java.io.Serializable;
import java.util.List;

public class Result<T> implements Serializable {
    private int code;
    private boolean isSuccess;
    private String msg;
    private T data;
    private List<T> dataList;

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
    public Result(int code, String msg, List<T> dataList, boolean isSuccess){
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
    public static<T> Result<T> success(List<T> dataList){
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

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
