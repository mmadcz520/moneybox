package com.changtou.moneybox.module.service;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankParserHandler extends DefaultHandler
{
	/**
	 * 存储所有的解析对象
	 */
	private List<String> mBankList = new ArrayList<String>();

	private Map<String, String> mBankInfoList = new HashMap<>();

	private String mBankName = "";
	private String mBankIcon = "";

	public BankParserHandler()
	{
		
	}

	public List<String> getDataList() {
		return mBankList;
	}

	/**
	 * 获取银行卡基本信息
	 * @return
	 */
	public  Map<String, String> getBankInfoList() {
		return mBankInfoList;
	}

	@Override
	public void startDocument() throws SAXException
	{
		// 当读到第一个开始标签的时候，会触发这个方法
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// 当遇到开始标记的时候，调用这个方法
		if (qName.equals("bank")) {
			mBankName = attributes.getValue(0);
			mBankIcon = attributes.getValue(1);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// 遇到结束标记的时候，会调用这个方法
		if (qName.equals("bank")) {
			mBankList.add(mBankName);
			mBankInfoList.put(mBankName,mBankIcon);
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}
}
