package com.example.mingchao_boot.ResultModel;


import lombok.Data;

@Data
public class ResultNormal {

    private static final String SUCCESS_CODE = "200";
    private static final String ERROR_CODE = "500";

    private String code;
    private String msg;
    private Object data;

    public ResultNormal(String successCode, String msg, Object data) {
        this.code=successCode;
        this.msg=msg;
        this.data=data;
    }

    public ResultNormal(String successCode, String msg) {
        this.code=successCode;
        this.msg=msg;
    }
    //定义返回方法

    //成功查询
    public static ResultNormal success(Object data){
        return new ResultNormal(SUCCESS_CODE,"",data);
    }
    //成功插入
    public static ResultNormal success(){
        return new ResultNormal(SUCCESS_CODE,"");
    }
    public static ResultNormal success(String msg){
        return new ResultNormal(SUCCESS_CODE,msg);
    }
    //失败
    public static ResultNormal error(String msg){
        return new ResultNormal(ERROR_CODE,msg);
    }
    //验证码成功
    /*public static ResultNormal success(String code,Object data){
        return new ResultNormal(code,"",data);
    }*/
    public static ResultNormal success(String msg,Object data){
        return new ResultNormal(SUCCESS_CODE,msg,data);
    }
}
