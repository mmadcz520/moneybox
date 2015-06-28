//package com.changtou.moneybox.module.page;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.provider.ContactsContract.PhoneLookup;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Toast;
//
//import com.main.db.FriendsDao;
//
//public class AddUserActivity extends Activity {
//    private EditText name;
//    private EditText phoneNum;
//    private EditText birthday;
//    private String sexText;
//
//    private FriendsDao friendsDao;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.add_user);
//
//        //得到页面控件
//        name=(EditText)findViewById(R.id.addUserTextName);
//        phoneNum=(EditText)findViewById(R.id.addUserTextNum);
//        birthday=(EditText)findViewById(R.id.addUserTextBirthday);
//        RadioGroup sex=(RadioGroup)findViewById(R.id.addUserTextSex);
//        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                Log.d("test", "select radio " + sexText);
//                sexText=((RadioButton)findViewById(checkedId)).getText().toString();
//                Log.d("test", "select radio " + sexText);
//            }
//        });
//        friendsDao=new FriendsDao(this,"ll1x.db",null,2);
//    }
//
//    public void addUserClick(View view){
//        int viewId=view.getId();
//        switch(viewId){
//            case R.id.addUserOkBut://确定
//
//                //验证
//                String name=this.name.getText().toString();
//                String phoneNum=this.phoneNum.getText().toString();
//                String birthday=this.birthday.getText().toString();
//
//                //提交 保存数据库
//                friendsDao.insertData(name, phoneNum,birthday,sexText);
//                Log.d("test", "addUserClick " + name+"|"+phoneNum+"|"+birthday+"|"+sexText.toString());
//                break;
//            case R.id.addUserNoBut://取消
//
//                break;
//            case R.id.addUserFromTXId:
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_PICK);
//                intent.setData(ContactsContract.Contacts.CONTENT_URI);
//                startActivityForResult(intent, 1);
//                break;
//        }
//    }
//
//    //重写的结果返回方法
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case 1:
//                if (data == null) {
//                    return;
//                }
//                Uri result = data.getData();
//                String contactId = result.getLastPathSegment();
//
//                String name = "";
//                String phone = "";
//
//                //得到名称
//                String[] projection = new String[] { People._ID, People.NAME ,People.NUMBER};
//                Cursor cursor = getContentResolver().query(People.CONTENT_URI,
//                        projection, // select sentence
//                        People._ID + " = ?", // where sentence
//                        new String[] { contactId }, // where values
//                        People.NAME); // order by
//
//                if (cursor.moveToFirst()) {
//                    name = cursor.getString(cursor.getColumnIndex(People.NAME));
//                }
//
//                //得到 电话
//                projection = new String[] { Contacts.Phones.PERSON_ID, Contacts.Phones.NUMBER};
//                cursor = getContentResolver().query(Contacts.Phones.CONTENT_URI,
//                        projection, // select sentence
//                        Contacts.Phones.PERSON_ID + " = ?", // where sentence
//                        new String[] { contactId }, // where values
//                        Contacts.Phones.NAME); // order by
//
//                if (cursor.moveToFirst()) {
//                    phone = cursor.getString(cursor.getColumnIndex(Contacts.Phones.NUMBER));
//                }
//
//                //显示
//                showPhone.setText(name+":"+phone);
//                break;
//        }
//    }
//}