package com.changtou.moneybox.module.service;

import android.util.Log;

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
	 * �洢���еĽ�������
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
	 * ��ȡ���п�������Ϣ
	 * @return
	 */
	public  Map<String, String> getBankInfoList() {
		return mBankInfoList;
	}

	@Override
	public void startDocument() throws SAXException
	{
		// ��������һ����ʼ��ǩ��ʱ�򣬻ᴥ���������
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// ��������ʼ��ǵ�ʱ�򣬵����������
		if (qName.equals("bank")) {
			mBankName = attributes.getValue(0);
			mBankIcon = attributes.getValue(1);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// ����������ǵ�ʱ�򣬻�����������
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
