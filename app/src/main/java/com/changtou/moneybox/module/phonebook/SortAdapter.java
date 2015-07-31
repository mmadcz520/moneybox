package com.changtou.moneybox.module.phonebook;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.http.base.HttpCallback;
import com.changtou.moneybox.common.http.impl.AsyncHttpClientImpl;
import com.changtou.moneybox.common.logger.Logger;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.entity.BankCardEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.PromotionBtn;

import org.json.JSONObject;

public class SortAdapter extends BaseAdapter implements SectionIndexer ,HttpCallback {
	
	private List<SortModel> list = null;

	private ArrayList<Boolean> btnlist = new ArrayList<>();
	
	private Context mContext;

	private SendPhoneCallBack mSendPhoneCallBack = null;

	public interface SendPhoneCallBack
	{
		void sendPhone(String phone);
	}


	public SortAdapter(Context mContext,List<SortModel> list){
		this.mContext = mContext;

		for(int i = 0; i < list.size(); i++)
		{
			btnlist.add(true);
		}

		//获取以及发送联系人的列表
		sendMobileList();

		this.list = list;
	}
	public void updateListView(List<SortModel> list){
		this.list = list;

		for(int i = 0; i < list.size(); i++)
		{
			btnlist.add(true);
		}

		notifyDataSetChanged();
	}
	

	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		final SortModel mContent = list.get(position);
		if (convertView== null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.riches_phonelist_item, null);
			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.name);
			viewHolder.tvLetter = (TextView) convertView.findViewById(R.id.catalog);
			viewHolder.tvButton = (PromotionBtn) convertView.findViewById(R.id.send_btn);

			viewHolder.tvButton.setEnabled(btnlist.get(position));

			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		//根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == getPositionForSection(section)) {
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		}else {
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		viewHolder.tvTitle.setText(this.list.get(position).getName());

		viewHolder.tvButton.setEnabled(btnlist.get(position));

		final ViewHolder finalViewHolder = viewHolder;
		viewHolder.tvButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				finalViewHolder.tvButton.setEnabled(false);
				String number = list.get(position).getNumber();
				Toast.makeText(mContext, "" + number, Toast.LENGTH_SHORT).show();

				if (mSendPhoneCallBack != null) {
					mSendPhoneCallBack.sendPhone(number);
				}

				sendMsg(number);

				btnlist.set(position, false);
				notifyDataSetChanged();
			}
		});

		return convertView;
	}

	@Override
	public Object[] getSections() {
		return null;
	}
	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	@Override
	public int getPositionForSection(int sectionIndex) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == sectionIndex) {
				return i;
			}
		}
		
		return -1;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	@Override
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	
	final static class ViewHolder{
		TextView tvLetter;
		TextView tvTitle;
		PromotionBtn tvButton;
	}
	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	public void setSendPhoneCallBack(SendPhoneCallBack sendPhoneCallBack)
	{
		this.mSendPhoneCallBack = sendPhoneCallBack;
	}

	// 登陆
	public void sendMsg(String phoneNum)
	{
		AsyncHttpClientImpl client = BaseApplication.getInstance().getAsyncClient();

		String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_RECOMMEND_SENDSMS) +
				"userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
				"&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

		try {

			Logger.e(url);

			RequestParams params = new RequestParams();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("mobile", phoneNum);
			params.put("data", jsonObject.toString());
			client.post(HttpRequst.REQ_TYPE_RECOMMEND_SENDSMS, BaseApplication.getInstance(), url, params, this);
		}
		catch (Exception e)
		{

		}

	}

	@Override
	public void onSuccess(String content, Object object, int reqType)
	{
		if(reqType == HttpRequst.REQ_TYPE_RECOMMEND_SENDSMS)
		{
			Toast.makeText(mContext, "发送短信成功", Toast.LENGTH_LONG).show();
		}
		else if(reqType == HttpRequst.REQ_TYPE_MOBILE_LIST)
		{
			Logger.e(content);
		}

	}

	@Override
	public void onFailure(Throwable error, String content, int reqType)
	{
		Logger.e(error.toString());
		Toast.makeText(mContext, "发生短信失败-网络异常", Toast.LENGTH_LONG).show();
	}


	// 登陆
	public void sendMobileList()
	{
		AsyncHttpClientImpl client = BaseApplication.getInstance().getAsyncClient();

		String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_MOBILE_LIST) +
				"userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
				"&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

		try {

			RequestParams params = new RequestParams();
			client.get(HttpRequst.REQ_TYPE_MOBILE_LIST, BaseApplication.getInstance(), url, params, this);
		}
		catch (Exception e)
		{

		}

	}

}
