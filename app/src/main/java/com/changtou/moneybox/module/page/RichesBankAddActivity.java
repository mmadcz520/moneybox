package com.changtou.moneybox.module.page;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.changtou.R;
import com.changtou.moneybox.module.entity.CityModel;
import com.changtou.moneybox.module.entity.DistrictModel;
import com.changtou.moneybox.module.entity.ProvinceModel;
import com.changtou.moneybox.module.service.XmlParserHandler;
import com.changtou.moneybox.module.widget.BetterSpinner;

import org.w3c.dom.Text;

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
    BetterSpinner spinner1;
    private BetterSpinner mProvinceSpinner = null;
    private TextView mRankTextView = null;
    private BetterSpinner mCitySpinner = null;
    private TextView mRank2TextView = null;

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

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

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode ="";


    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_safe_bank_add);
        spinner1 = (BetterSpinner)findViewById(R.id.spinner1);
        mProvinceSpinner = (BetterSpinner)findViewById(R.id.riches_safe_add_province);
        mCitySpinner = (BetterSpinner)findViewById(R.id.riches_safe_add_city);

        mRankTextView = (TextView)findViewById(R.id.riches_safe_add_rank);
        mRank2TextView = (TextView)findViewById(R.id.riches_safe_add_rank2);

        mProvinceSpinner.setDropDownWidth(500);
        mCitySpinner.setDropDownWidth(500);

        initProvinceDatas();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item_layout, mProvinceDatas);
        spinner1.setAdapter(adapter);
        mProvinceSpinner.setAdapter(adapter);
        mCitySpinner.setAdapter(adapter);

        //填入用户开户行所在省
        mProvinceSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mProvinceDatas != null) {
                    filterText(mProvinceDatas[position]);
                }
            }
        });

        mProvinceSpinner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mProvinceSpinner.showDropDown();
            }
        });
    }

    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas()
    {
        List<ProvinceModel> provinceList = null;

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
    private void filterText(String seleText)
    {
        if(seleText == null) return;

        CharSequence cs = "省";
        CharSequence cs1 = "市";
        CharSequence cs2 = "自治区";
        CharSequence filter = seleText;

        mRank2TextView.setVisibility(View.VISIBLE);
        mCitySpinner.setVisibility(View.VISIBLE);
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

        mProvinceSpinner.setText(filter);
    }
}
