package com.changtou.moneybox.module.service;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class BankParserHandler extends DefaultHandler {

	/**
	 * �洢���еĽ�������
	 */
	private List<String> mBankList = new ArrayList<String>();

	private String mBankName = "";

	public BankParserHandler() {
		
	}

	public List<String> getDataList() {
		return mBankList;
	}

	@Override
	public void startDocument() throws SAXException {
		// ��������һ����ʼ��ǩ��ʱ�򣬻ᴥ���������
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// ��������ʼ��ǵ�ʱ�򣬵����������
		if (qName.equals("bank")) {
			mBankName = attributes.getValue(0);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// ����������ǵ�ʱ�򣬻�����������
		if (qName.equals("bank")) {
			mBankList.add(mBankName);
		}

	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}
}
