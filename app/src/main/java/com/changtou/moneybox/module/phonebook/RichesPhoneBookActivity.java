package com.changtou.moneybox.module.phonebook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.changtou.R;
import com.changtou.moneybox.module.page.CTBaseActivity;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class RichesPhoneBookActivity extends CTBaseActivity {
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> mSourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    private static final Uri uri = Uri
            .parse("content://com.android.contacts/data/phones");
    private static final String[] projection = {"_id", "display_name",
            "data1", "sort_key"};

    /**
     * 异步获取电话号码
     */
    private AsyncQueryHandler queryHandler;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.riches_phonelist_layout);
//        initViews();
//    }


    protected void initView(Bundle bundle) {
        setContentView(R.layout.riches_phonelist_layout);
        initViews();
    }

    private void initViews() {

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Toast.makeText(getApplication(), ((SortModel) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
            }
        });

//		mSourceDateList = filledData(getResources().getStringArray(R.array.item_setting1));

        // 根据a-z进行排序源数据
//		Collections.sort(mSourceDateList, pinyinComparator);
//		adapter = new SortAdapter(this, mSourceDateList);
//		sortListView.setAdapter(adapter);


        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //初始化联系人数据
        queryHandler = new MyAsyncQueryHandler(getContentResolver());
//		alphaIndexer = new HashMap<String, Integer>();
    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr)
    {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = mSourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : mSourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || getPinYin(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    /**
     * 异步查询接口
     */
    private class MyAsyncQueryHandler extends AsyncQueryHandler {

        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);

        }

        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            mSourceDateList = new ArrayList<SortModel>();
            mSourceDateList.clear();
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    SortModel item = new SortModel();
                    item.setName(cursor.getString(1));
                    item.setNumber(Utils.formatNumber(cursor.getString(2)));
                    item.setAlpha(Utils.formatAlpha(cursor.getString(3)));

                    String pinyin = characterParser.getSelling(cursor.getString(1));
                    String sortString = pinyin.substring(0, 1).toUpperCase();

                    item.setSortLetters(Utils.formatAlpha(cursor.getString(3)));

                    // 正则表达式，判断首字母是否是英文字母
//					if(sortString.matches("[A-Z]")){
//						item.setSortLetters(sortString.toUpperCase());
//					}else{
//						item.setSortLetters("#");
//					}


                    mSourceDateList.add(item);
                    Collections.sort(mSourceDateList, pinyinComparator);
                    adapter = new SortAdapter(RichesPhoneBookActivity.this, mSourceDateList);
                    sortListView.setAdapter(adapter);
                }
            }
            if (mSourceDateList.size() > 0) {

            }
        }
    }

    /**
     * 设置数据
     */
    private void setAdapter() {
        if (adapter == null) {
//			adapter = new SortAdapter(this,mContactList);
//			listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void startQuery() {
        queryHandler.startQuery(1, null, uri, projection, "data1 is not null",
                null, "sort_key COLLATE LOCALIZED asc");
    }

    @Override
    protected void onResume() {
        super.onResume();
        startQuery();
    }


    /**
     * 将汉字转换为全拼
     *
     * @param src
     * @return String
     */
    public static String getPinYin(String src) {
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        // 设置汉字拼音输出的格式

        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断能否为汉字字符

                // System.out.println(t1[i]);

                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中

                    t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后

                } else {
                    // 如果不是汉字字符，间接取出字符并连接到字符串t4后

                    t4 += Character.toString(t1[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return t4;
    }

}
