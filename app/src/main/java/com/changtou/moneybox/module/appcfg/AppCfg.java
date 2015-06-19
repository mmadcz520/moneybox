package com.changtou.moneybox.module.appcfg;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/9.
 */
public class AppCfg
{
    //是否登录
    public static final String CFG_LOGIN = "LOGIN";
    public enum LOGIN_STATE{
        LOGIN, EN_LOGIN;
    }

    //手势密码
    public static final String GSPD = "GesturePD";

    public static final String TOKEN = "token";



    public static Map<String,String> MAP_MSG_ERROR = new HashMap<>();

    static
    {
        MAP_MSG_ERROR.put("1", "银行卡添加成功");
        MAP_MSG_ERROR.put("2", "银行卡设置成功");
        MAP_MSG_ERROR.put("3", "银行卡删除成功");
        MAP_MSG_ERROR.put("4", "银行卡添加失败");
        MAP_MSG_ERROR.put("5", "银行卡号重复");
        MAP_MSG_ERROR.put("6", "出现未知错误");
        MAP_MSG_ERROR.put("7", " 最多只容许添加3张");
        MAP_MSG_ERROR.put("8", "默认银行卡不能删除");
        MAP_MSG_ERROR.put("9", " 银行卡号格式不正确");
    }
}
