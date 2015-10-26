package com.changtou.moneybox.module.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jone on 2015/9/14.
 */
public class ReadSMsTool {

    private Context mContext = null;

    private ReadSMSListener mReadSMSListener = null;

    public interface ReadSMSListener
    {
        void readCallback(String code);
    }

    public void init(Context coxt) {

        mContext = coxt;
        SmsContent content = new SmsContent(new Handler());
        //注册短信变化监听
        coxt.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, content);
    }

    /**
     * 从字符串中截取连续6位数字组合 ([0-9]{" + 6 + "})截取六位数字 进行前后断言不能出现数字 用于从短信中获取动态密码
     *
     * @param str 短信内容
     * @return 截取得到的6位动态密码
     */
    public static String getDynamicPassword(String str) {
        // 6是验证码的位数一般为六位
        Pattern continuousNumberPattern = Pattern.compile("(?<![0-9])([0-9]{"
                + 6 + "})(?![0-9])");
        Matcher m = continuousNumberPattern.matcher(str);
        String dynamicPassword = "";
        while (m.find()) {
            System.out.print(m.group());
            dynamicPassword = m.group();
        }

        return dynamicPassword;
    }

    /*
   * 监听短信数据库
   */
    class SmsContent extends ContentObserver {
        private Cursor cursor = null;

        public SmsContent(Handler handler) {
            super(handler);
            // TODO Auto-generated constructor stub
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onChange(boolean selfChange) {
            // TODO Auto-generated method stub
            super.onChange(selfChange);
            // 读取收件箱中指定号码的短信
            cursor = mContext.getContentResolver().query(Uri.parse("content://sms/inbox"),
                    new String[]{"_id", "address", "read", "body"},
                    " address=? and read=?",
                    new String[]{"106906060526", "0"}, "_id desc");
            // 按id排序，如果按date排序的话，修改手机时间后，读取的短信就不准了
            if (cursor != null && cursor.getCount() > 0) {
                ContentValues values = new ContentValues();
                values.put("read", "1"); // 修改短信为已读模式
                cursor.moveToNext();
                int smsbodyColumn = cursor.getColumnIndex("body");
                String smsBody = cursor.getString(smsbodyColumn);
//                edit1.setText(getDynamicPassword(smsBody));
                if(mReadSMSListener != null)
                {
                    mReadSMSListener.readCallback(getDynamicPassword(smsBody));
                }

            }
            // 在用managedQuery的时候，不能主动调用close()方法， 否则在Android 4.0+的系统上， 会发生崩溃
            if (Build.VERSION.SDK_INT < 14) {
                cursor.close();
            }
        }
    }

    public void setSMSReadListener(ReadSMSListener readSMSListener)
    {
        mReadSMSListener = readSMSListener;
    }

}
