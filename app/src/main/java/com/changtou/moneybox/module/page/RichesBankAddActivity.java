package com.changtou.moneybox.module.page;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.appcfg.AppCfg;
import com.changtou.moneybox.module.entity.BankCardEntity;
import com.changtou.moneybox.module.entity.CityModel;
import com.changtou.moneybox.module.entity.DistrictModel;
import com.changtou.moneybox.module.entity.ProvinceModel;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.service.BankParserHandler;
import com.changtou.moneybox.module.service.XmlParserHandler;
import com.changtou.moneybox.module.widget.BetterSpinner;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Administrator on 2015/5/26.
 *
 * 银行卡添加页面
 */
public class RichesBankAddActivity extends CTBaseActivity
{
    private BetterSpinner mBankSpinner = null;
    private BetterSpinner mProvinceSpinner = null;
    private BetterSpinner mCitySpinner = null;

    private TextView mRankTextView = null;
    private TextView mRank2TextView = null;

    private EditText mBankNoEdit = null;
    private EditText mBranchEdit = null;

    /**
     * 银行
     */
    protected String[] mBankDatas;

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;

    protected String[] mCitisDatas = {""};
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName ="";

    private EditText mUserNameEdit = null;

    private String mFullName = "";

    private UserInfoEntity mUserInfoEntity = null;

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode ="";

    private int mURLType = 0;

    private String mBankNo = "";

    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_safe_bank_add);

        mUserInfoEntity = (UserInfoEntity)ACache.get(this).getAsObject("userinfo");

        mUserNameEdit = (EditText)findViewById(R.id.riches_safe_add_username);

        mFullName = mUserInfoEntity.getFullName().trim();
        mUserNameEdit.setText(mUserInfoEntity.getFullName());

        mBankSpinner = (BetterSpinner)findViewById(R.id.riches_safe_add_bank);
        mProvinceSpinner = (BetterSpinner)findViewById(R.id.riches_safe_add_province);
        mCitySpinner = (BetterSpinner)findViewById(R.id.riches_safe_add_city);

        mRankTextView = (TextView)findViewById(R.id.riches_safe_add_rank);
        mRank2TextView = (TextView)findViewById(R.id.riches_safe_add_rank2);

        mProvinceSpinner.setDropDownWidth(500);
        mCitySpinner.setDropDownWidth(500);

        mBankNoEdit = (EditText)findViewById(R.id.riches_bank_add_bankno);
        bankCardNumAddSpace(mBankNoEdit);

        mBranchEdit = (EditText)findViewById(R.id.riches_bank_add_branch);

        initBankDatas();
        initProvinceDatas();

        if(mBankDatas==null || mProvinceDatas==null) return;

        ArrayAdapter<String> bankAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, mBankDatas);
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(this,
            R.layout.spinner_item_layout, mProvinceDatas);
        ArrayAdapter<String> citisAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, mCitisDatas);
        mBankSpinner.setAdapter(bankAdapter);
        mProvinceSpinner.setAdapter(provinceAdapter);
        mCitySpinner.setAdapter(citisAdapter);

        //填入用户开户行所在省
        mProvinceSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mProvinceDatas != null) {
                    filterText(mProvinceDatas[position]);

                    mCitisDatas = mCitisDatasMap.get(mProvinceDatas[position]);
                    ArrayAdapter<String> citisAdapter = new ArrayAdapter<>(RichesBankAddActivity.this,
                            R.layout.spinner_item_layout, mCitisDatas);
                    mCitySpinner.setAdapter(citisAdapter);
                    citisAdapter.notifyDataSetChanged();
                }
            }
        });

        mProvinceSpinner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mProvinceSpinner.showDropDown();
            }
        });

        mCitySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                filterText2(mCitisDatas[position]);
            }
        });

        Intent intent = getIntent();
        mURLType = intent.getIntExtra("URLType", 0);
    }

    protected void initListener() {
        setOnClickListener(R.id.btn_bank_add);
    }

    public void treatClickEvent(int id)
    {
        if(!mBranchEdit.getText().toString().equals("") && !mBankSpinner.getText().toString().equals("") &&
                !mBankNoEdit.getText().toString().equals("") && !mProvinceSpinner.getText().toString().equals(""))
        {
            requestAddBank();
        }
        else
        {
            Toast.makeText(this, "请输入完整信息", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
    }

    @Override
    protected void initData() {
        setPageTitle("添加银行卡");
    }

    /**
     * 解析银行卡信息
     */
    protected void initBankDatas()
    {
        List<String> bankList;

        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("bank_data.xml");

            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            BankParserHandler handler = new BankParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            bankList = handler.getDataList();
            mBankDatas = new String[bankList.size()];

            for(int i = 0; i < bankList.size(); i++)
            {
                mBankDatas[i] = bankList.get(i);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas()
    {
        List<ProvinceModel> provinceList;

        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");

            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();

            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 过滤所填内容
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void filterText(String seleText)
    {
        if(seleText == null) return;

        CharSequence cs = "省";
        CharSequence cs1 = "市";
        CharSequence cs2 = "自治区";
        CharSequence filter = seleText;

        mRank2TextView.setVisibility(View.VISIBLE);
        mCitySpinner.setVisibility(View.VISIBLE);
        mCitySpinner.setText("", false);
        mRankTextView.setText(cs);

        if(seleText.contains(cs))
        {
            filter = seleText.replace(cs, "");
            mRankTextView.setText(cs);
        }
        else if(seleText.contains(cs1))
        {
            filter = seleText.replace(cs1, "");
            mRankTextView.setText(cs1);
            mRank2TextView.setVisibility(View.INVISIBLE);
            mCitySpinner.setVisibility(View.INVISIBLE);
        }
        else if(seleText.contains(cs2))
        {
            filter = seleText.replace(cs2, "");
            mRankTextView.setText(cs2);
        }

        mProvinceSpinner.setText(filter, false);
    }


    /**
     * 过滤市
     * @param seleText
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void filterText2(String seleText)
    {
        if(seleText == null) return;

        CharSequence cs = "市";
        CharSequence filter = seleText;

        if(seleText.contains(cs))
        {
            filter = seleText.replace(cs, "");
        }

        mCitySpinner.setText(filter, false);
    }

    /** * 银行卡四位加空格 * * @param mEditText */
    public static void bankCardNumAddSpace(final EditText mEditText) {
        mEditText.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;
            int location = 0;
            // 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = mEditText.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }
                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }
                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }
                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }
                    mEditText.setText(str);
                    Editable etable = mEditText.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }

    /**
     * 添加银行卡 服务器请求
     */
    private void requestAddBank()
    {
        String bank = mBankSpinner.getText().toString().trim();
        mBankNo = mBankNoEdit.getText().toString().trim().replaceAll(" ","");
        String place = mProvinceSpinner.getText().toString().trim() + mCitySpinner.getText().toString().trim();
        String branch = mBranchEdit.getText().toString().trim();

        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_ADDBANK);

        try
        {
            RequestParams params = new RequestParams();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cardholder", mFullName);
            jsonObject.put("bank", bank);
            jsonObject.put("account", mBankNo);
            jsonObject.put("place", place);
            jsonObject.put("branch", branch);
            params.put("data", jsonObject.toString());

            sendRequest(HttpRequst.REQ_TYPE_ADDBANK,
                    url,
                    params,
                    getAsyncClient(), false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onSuccess(String content, Object object, int reqType)
    {
        super.onSuccess(content, object, reqType);
        if(reqType == HttpRequst.REQ_TYPE_ADDBANK) {
            try {
                JSONObject json = new JSONObject(content);
                String errorCode = json.getString("errcode");

                Toast.makeText(this, AppCfg.MAP_MSG_ERROR.get(errorCode), Toast.LENGTH_LONG).show();
                if (errorCode.equals("1")) {
                    changeDefaultRequest();
                }
            } catch (Exception e) {

            }
        }
        else if(reqType == HttpRequst.REQ_TYPE_BANKCARD)
        {
            BankCardEntity entity = (BankCardEntity) object;
            mUserInfoEntity.setBankCardEntity(entity);
            ACache.get(this).put("userinfo", mUserInfoEntity);

            finish();
            if(mURLType == 0)
            {
                final Intent intent = new Intent(this, RichesBankActivity.class);
                startActivity(intent);
            }
            else if(mURLType == 1)
            {
                final Intent intent = new Intent(this, RichesRechargePage.class);
                startActivity(intent);
            }
            else if(mURLType == 2)
            {
                final Intent intent = new Intent(this, RichesWithdrawActivity.class);
                startActivity(intent);
            }
        }
        else if(reqType == HttpRequst.REQ_TYPE_CHANGEBANK)
        {
            initBankListRequest();
        }

    }

    public void onFailure(Throwable error, String content, int reqType)
    {
        super.onFailure(error, content, reqType);
        Toast.makeText(this,"内部错误", Toast.LENGTH_LONG).show();
    }

    private void initBankListRequest()
    {
        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_BANKCARD);
        sendRequest(HttpRequst.REQ_TYPE_BANKCARD,
                url,
                mParams,
                getAsyncClient(), false);
    }


    //添加后立即设置银行卡
    private void changeDefaultRequest()
    {
        try {
            String url = HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_CHANGEBANK);

            RequestParams params = new RequestParams();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("account", mBankNo);
            params.put("data", jsonObject.toString());

            sendRequest(HttpRequst.REQ_TYPE_CHANGEBANK, url, params, getAsyncClient(), false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
