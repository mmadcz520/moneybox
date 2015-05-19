package com.changtou.moneybox.module.usermodule;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.http.base.BaseHttpClient;
import com.changtou.moneybox.common.http.base.HttpCallback;
import com.changtou.moneybox.common.http.impl.AsyncHttpClientImpl;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.http.HttpRequst;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述: 用户信息管理模块
 *
* @author zhoulongfei
* @since 2015-5-19
*/
public class UserManager implements HttpCallback {

	private List<LoginNotifier> mNotifierContainer = null;
	private String mUrl = "http://autoapp.hsxiang.com/wp-admin/admin-ajax.php?action=app_user_login";
	private String mGetInfo = "http://autoapp.hsxiang.com/wp-admin/admin-ajax.php?action=app_user_get_info";

	private String mName = "";
	private String mPass = "";

	// 登陆task 异步消息
	private LoginAsyncTask mLoingTask = null;
	private GetUserInfoTask mGuit = null;

	// 各种数据常量
	public final static String UI_PRF = "ui_prf";
	public final static String UI_NICKNAME = "nickname";
	public final static String UI_AVATAR = "avatar";

	// 登陆状态(已登陆, 未登录)
	public final static int LOGIN_STATE_NEVER = 0;  // 从未登陆,或者登出
	public final static int LOGIN_STATE_ALIVE = 1;  // 登陆过,并且cooiek有效
	public final static int LOGIN_STATE_INVAID = 2; // 登陆过,cookie无效

	private int mLoginState = 0;

	private SharedPreferences mUserPrefs = null;
	private UserBean mUserBean = null;

    public BaseApplication mBaseApp = null;

	private Context mContext = null;

	/**
	 * 初始化登陆模块
	 *
	 * @throws java.io.IOException
	 */
	public void init(Context context) throws IOException {

        mBaseApp = BaseApplication.getInstance();
		this.mContext = context;

		mNotifierContainer = new ArrayList<>();
//		mGuit = new GetUserInfoTask();
//		mUserBean = new UserBean();
//
//		mGuit.execute();

        //保存登陆状态

//		mUserPrefs = context.getSharedPreferences(UI_PRF, 0);
//		String[] key = mUserBean.getKeySet();
//		Map<String, String> result = new HashMap<String, String>();
//		for (int i = 0; i < key.length; i++) {
//			result.put(key[i], mUserPrefs.getString(key[i], ""));
//			if (mUserPrefs.getString(key[i], "").equals("")) {
//				Log.e("rz_demo","LOGIN_STATE_NEVER!!!!!!!"+ mUserPrefs.getString(key[i], ""));
//				mLoginState = LOGIN_STATE_NEVER;
//			} else {
//				Log.e("rz_demo", "LOGIN_STATE_INVAID!!!!!!! "+ mUserPrefs.getString(key[i], ""));
//				mLoginState = LOGIN_STATE_INVAID;
//			}
//    }
//		mUserBean.setResult(result);
	}

	// 登陆
	public void logIn(String username, String password)
    {
        AsyncHttpClientImpl client = mBaseApp.getAsyncClient();
        String loginUrl = HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_LOGIN);
        RequestParams params = new RequestParams();
        params.put("mobile", "13816041190");
        params.put("password", "11111111");
        client.post(HttpRequst.REQ_TYPE_LOGIN, mBaseApp, loginUrl, params, this);

//		mLoingTask = new LoginAsyncTask();
//		mLoingTask.execute();
	}

	//
	public void logOut() {

		for (int i = 0; i < mNotifierContainer.size(); i++) {
			mNotifierContainer.get(i).logoutNotify();
		}

		// 清空用户资料
		String[] key = mUserBean.getKeySet();
		for (int i = 0; i < key.length; i++) {
			SharedPreferences.Editor prefsWriter = mUserPrefs.edit();
			prefsWriter.putString(key[i], "");
			prefsWriter.commit();
		}

		this.mLoginState = LOGIN_STATE_NEVER;
	}

	// 设置通知响应事件
	public void setLoginNotifier(LoginNotifier loginNotifier) {

		this.mNotifierContainer.add(loginNotifier);
	}

    /**
     *
     * @param content   返回值
     * @param object    返回的转化对象
     * @param reqType   请求的唯一识别
     */
    public void onSuccess(String content, Object object, int reqType)
    {
        Log.e("CT_MONEY", "onSuccess--->" + content);
		ACache mCache = ACache.get(mContext);
		for (int i = 0; i < mNotifierContainer.size(); i++) {
			mNotifierContainer.get(i).loginSucNotify();
		}
    }

    /**
     * \
     * @param error    错误
     * @param content   返回值
     * @param reqType  请求的唯一识别
     */
    public void onFailure(Throwable error, String content, int reqType)
    {
        Log.e("CT_MONEY", "onFailure--->" + content);
    }

    /**
	 * 实时获取用户信息
	 * @author Jone
	 */
	private class GetUserInfoTask extends AsyncTask<String, Integer, String> {

		/**
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Object[])
		 */
		protected String doInBackground(String... arg0) {
//
//			if (HttpUtil.getUserInfo(mGetInfo, mUserBean)) {
//				Log.e("rz_demo", "get user info succ!!!");
//				mLoginState = LOGIN_STATE_ALIVE;
//				saveUserInfo();
//
//				return "true";
//			} else {
//				return "false";
//			}
            return null;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		protected void onPostExecute(String result) {

			if (result.endsWith("true")) {

				for (int i = 0; i < mNotifierContainer.size(); i++) {
					mNotifierContainer.get(i).loginSucNotify();
				}
			}
		}
	}

	/**
	 * 登陆时异步处理
	 */
	private class LoginAsyncTask extends AsyncTask<String, Integer, String> {

		/**
		 * 开始登陆 (non-Javadoc)
		 *
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		protected void onPreExecute() {

		}

		/*
		 * (non-Javadoc)
		 *
		 * @see android.os.AsyncTask#onProgressUpdate(Progress[])
		 */
		protected void onProgressUpdate(Integer... values) {

			for (int i = 0; i < mNotifierContainer.size(); i++) {
				mNotifierContainer.get(i).loginIngNotify();
			}
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		protected String doInBackground(String... params) {
//			try {
//				boolean suc = HttpUtil.doLogin(mUrl, mName, mPass);
//				HttpUtil.getUserInfo(mGetInfo, mUserBean);
//
//				if (suc) {
//					publishProgress(0);
//					saveUserInfo();
//					return "true";
//				} else {
//					return "false";
//				}
//			} catch (Exception e) {
//				return "false";
//			}

            return null;
		}

		/**
		 * 登陆执行完后
		 *
		 * (non-Javadoc)
		 *
		 * @see android.os.AsyncTask#onPostExecute(Object)
		 */
		protected void onPostExecute(String result) {

			if (result.equals("true")) {
				mLoginState = LOGIN_STATE_ALIVE;
				for (int i = 0; i < mNotifierContainer.size(); i++) {
					mNotifierContainer.get(i).loginSucNotify();
				}
			} else {
				for (int i = 0; i < mNotifierContainer.size(); i++) {
					mNotifierContainer.get(i).loginErrNotify();
				}
			}

		}
	}

	/**
	 * 保存用户资料
	 */
	public void saveUserInfo() {

		// 用户信息保存到SharedPreferences
		String[] key = mUserBean.getKeySet();
		for (int i = 0; i < key.length; i++) {
			SharedPreferences.Editor prefsWriter = mUserPrefs.edit();
			prefsWriter.putString(key[i], mUserBean.getResult().get(key[i]));
			prefsWriter.commit();
		}


		saveUserAvatar();
	}

	/**
	 * 获取用户信息
	 *
	 * @return
	 */
	public UserBean getUserBean() {
		return mUserBean;
	}

	/**
	 * 保存文件
	 *
	 * @param bm
	 * @param fileName
	 * @throws java.io.IOException
	 */
	public void saveFile(Bitmap bm, String fileName) throws IOException {

		String ALBUM_PATH = Environment.getExternalStorageDirectory()
				+ "/download_test/";

		File dirFile = new File(ALBUM_PATH);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File myCaptureFile = new File(ALBUM_PATH + fileName);
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
	}

	/**
	 * Get image from newwork
	 *
	 * @param path
	 *            The path of image
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] getImage(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestMethod("GET");
		InputStream inStream = conn.getInputStream();
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return readStream(inStream);
		}
		return null;
	}

	/**
	 * Get data from stream
	 *
	 * @param inStream
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inStream.close();
		return outStream.toByteArray();
	}

	// 保存用户头像
	public void saveUserAvatar() {
		// 保存用户头像
		try {
			String url = mUserBean.getResult().get("avatar");
			Log.e("url image____________", "ddsdsdas" + url);
			byte[] data = getImage(url);
			if (data != null) {
				Log.e("save img",
						"_____________________________________________dsda_");
				Bitmap mBitmap = BitmapFactory.decodeByteArray(data, 0,
						data.length);// bitmap
				saveFile(mBitmap, "test.png");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @return
	 */
	public int getLoginState() {
		return mLoginState;
	}

	// 获取用户头像
	public Bitmap getAvatar() {
		String filePath = Environment.getExternalStorageDirectory()
				+ "/download_test/" + "test.png";
		Bitmap bm = null;
		File mfile = new File(filePath);
		if (mfile.exists()) {
			// 若该文件存在
			bm = BitmapFactory.decodeFile(filePath);
		}
		return bm;
	}
}
