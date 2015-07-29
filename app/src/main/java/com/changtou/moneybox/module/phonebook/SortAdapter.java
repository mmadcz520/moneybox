package com.changtou.moneybox.module.phonebook;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.R;
import com.changtou.moneybox.module.widget.PromotionBtn;

public class SortAdapter extends BaseAdapter implements SectionIndexer {
	
	private List<SortModel> list = null;

	private ArrayList<Boolean> btnlist = new ArrayList<>();
	
	private Context mContext;
	
	public SortAdapter(Context mContext,List<SortModel> list){
		this.mContext = mContext;

		for(int i = 0; i < list.size(); i++)
		{
			btnlist.add(true);
		}

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
			Log.e("CT_MONEY", "======= +++ getViewgetViewgetViewgetView"  + position + "---" + btnlist.get(position));

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

		final ViewHolder finalViewHolder = viewHolder;
		viewHolder.tvButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();
				finalViewHolder.tvButton.setEnabled(false);
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


}
