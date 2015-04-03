package com.changtou.moneybox.module.usermodule;

import java.util.HashMap;
import java.util.Map;

/**
 * 1. 设置字段
 * 
 * @author Jone
 */
public class UserBean {

	private String mNickName = null;
	private String mAvatar = null;

	private String[] keySet = { "nickname", "avatar" };
	private Map<String, String> result;

	public UserBean() {
		// TODO Auto-generated constructor stub

		result = new HashMap<String, String>();
	}

	public String[] getKeySet() {
		return keySet;
	}

	public void setResult(Map<String, String> result) {
		this.result = result;
	}

	public Map<String, String> getResult() {
		return result;
	}
}
