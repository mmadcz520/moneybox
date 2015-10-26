package com.changtou.moneybox.module.usermodule;

/**
 * 1. 登陆事件通知接口
 * 
 * Login, Logout
 * 
 */
public interface LoginNotifier {

	// 登陆成功通知
	void loginSucNotify();

	// 登陆进行中
	void loginIngNotify();

	// 登陆出错通知
	void loginErrNotify(int errcode);

	// 登出通知
	void logoutNotify();

	void loginUserInfo(Object oject);
}
