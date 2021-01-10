package com.zimug.commons.exception;

import lombok.Data;

@Data
public class AjaxResponse {
  public boolean isIsok() {
    return isok;
  }

  public void setIsok(boolean isok) {
    this.isok = isok;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  private boolean isok;  //请求是否处理成功

  private int code; //请求响应状态码（200、400、500）

  private String message;  //请求结果描述信息

  private Object data; //请求结果数据（通常用于查询操作）

  private AjaxResponse(){}

  //请求出现异常时的响应数据封装
  public static AjaxResponse error(CustomException e) {
    AjaxResponse resultBean = new AjaxResponse();
    resultBean.setIsok(false);
    resultBean.setCode(e.getCode());
    resultBean.setMessage(e.getMessage());
    return resultBean;
  }

  //请求出现异常时的响应数据封装
  public static AjaxResponse error(CustomExceptionType customExceptionType,
                                   String errorMessage) {
    AjaxResponse resultBean = new AjaxResponse();
    resultBean.setIsok(false);
    resultBean.setCode(customExceptionType.getCode());
    resultBean.setMessage(errorMessage);
    return resultBean;
  }
  //请求出现异常时的响应数据封装
  public static AjaxResponse userInputError(
                                   String errorMessage) {
    AjaxResponse resultBean = new AjaxResponse();
    resultBean.setIsok(false);
    resultBean.setCode(500);
    resultBean.setMessage(errorMessage);
    return resultBean;
  }



  //请求成功的响应，不带查询数据（用于删除、修改、新增接口）
  public static AjaxResponse success(){
    AjaxResponse ajaxResponse = new AjaxResponse();
    ajaxResponse.setIsok(true);
    ajaxResponse.setCode(200);
    ajaxResponse.setMessage("请求响应成功!");
    return ajaxResponse;
  }

  //请求成功的响应，带有查询数据（用于数据查询接口）
  public static AjaxResponse success(Object obj){
    AjaxResponse ajaxResponse = new AjaxResponse();
    ajaxResponse.setIsok(true);
    ajaxResponse.setCode(200);
    ajaxResponse.setMessage("请求响应成功!");
    ajaxResponse.setData(obj);
    return ajaxResponse;
  }

  //请求成功的响应，带有查询数据（用于数据查询接口）
  public static AjaxResponse success(Object obj,String message){
    AjaxResponse ajaxResponse = new AjaxResponse();
    ajaxResponse.setIsok(true);
    ajaxResponse.setCode(200);
    ajaxResponse.setMessage(message);
    ajaxResponse.setData(obj);
    return ajaxResponse;
  }


}
