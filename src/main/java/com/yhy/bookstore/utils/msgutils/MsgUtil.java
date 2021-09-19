package com.yhy.bookstore.utils.msgutils;

import net.sf.json.JSONObject;

public class MsgUtil {

    public static final int SUCCESS = 0;
    public static final int ERROR = -1;
    public static final int LOGIN_USER_ERROR = -100;
    public static final int NOT_LOGGED_IN_ERROR = -101;

    public static final String SUCCESS_MSG = "成功！";
    public static final String LOGIN_SUCCESS_MSG = "登录成功！";
    public static final String REGISTER_SUCCESS_MSG = "注册成功！";
    public static final String LOGOUT_SUCCESS_MSG = "登出成功！";
    public static final String LOGOUT_ERR_MSG = "登出异常！";
    public static final String ERROR_MSG = "错误！";
    public static final String LOGIN_USER_ERROR_MSG = "用户名或密码错误，请重新输入！";
    public static final String LOGIN_USER_BAN_ERROR_MSG = "账户被禁用，登录失败！";
    public static final String NOT_LOGGED_IN_ERROR_MSG = "登录失效，请重新登录！";
    public static final String ADD_SUCCESS_MSG = "添加成功！";
    public static final String PURCHASE_SUCCESS_MSG = "购买成功！";
    public static final String PURCHASE_FAIL_MSG = "书籍库存不足或已被删除，购买失败！";
    public static final String DELETE_SUCCESS_MSG = "删除成功！";
    public static final String EDIT_SUCCESS_MSG = "修改成功！";
    public static final String SWITCH_AUTH_SUCCESS_MSG = "操作成功！";
    public static final String REGISTER_ERROR_MSG = "用户名已存在！";


    public static Msg makeMsg(MsgCode code, JSONObject data){
        return new Msg(code, data);
    }

    public static Msg makeMsg(MsgCode code, String msg, JSONObject data){
        return new Msg(code, msg, data);
    }

    public static Msg makeMsg(MsgCode code){
        return new Msg(code);
    }

    public static Msg makeMsg(MsgCode code, String msg){
        return new Msg(code, msg);
    }

    public static Msg makeMsg(int status, String msg, JSONObject data){
        return new Msg(status, msg, data);
    }

    public static Msg makeMsg(int status, String msg){
        return new Msg(status, msg);
    }
}
