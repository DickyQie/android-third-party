package com.zq.qqsignandshare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv_name;
    TextView tv_content;
    ImageView imageView;
    private UserInfo mInfo;
    public static Tencent mTencent;
    public static String mAppid="1106062414";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView()
    {
        tv_name= (TextView) findViewById(R.id.name);
        tv_content= (TextView) findViewById(R.id.content);
        imageView= (ImageView) findViewById(R.id.user_logo);
        findViewById(R.id.new_login_btn).setOnClickListener(this);
        findViewById(R.id.new_login_close).setOnClickListener(this);
        findViewById(R.id.new_login_shareqq).setOnClickListener(this);
        findViewById(R.id.new_login_shareqzone).setOnClickListener(this);
        if (mTencent == null) {
            mTencent = Tencent.createInstance(mAppid, this);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.new_login_btn:
                onClickLogin();
                break;
            case R.id.new_login_close:
                mTencent.logout(MainActivity.this);//注销登录
                break;
            case R.id.new_login_shareqq:
                onClickShare();
                break;
            case R.id.new_login_shareqzone:
                shareToQQzone();
                break;
        }
    }

    /**
     * 继承的到BaseUiListener得到doComplete()的方法信息
     */
    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {//得到用户的ID  和签名等信息  用来得到用户信息
            Log.i("lkei",values.toString());
            initOpenidAndToken(values);
            updateUserInfo();
        }
    };
    /***
     * QQ平台返回返回数据个体 loginListener的values
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Toast.makeText(MainActivity.this, "登录失败",Toast.LENGTH_LONG).show();
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Toast.makeText(MainActivity.this, "登录失败",Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(MainActivity.this, "登录成功",Toast.LENGTH_LONG).show();
            doComplete((JSONObject)response);
        }

        protected void doComplete(JSONObject values) {

        }
        @Override
        public void onError(UiError e) {
            //登录错误
        }

        @Override
        public void onCancel() {
            // 运行完成
        }
    }
    /**
     * 获取登录QQ腾讯平台的权限信息(用于访问QQ用户信息)
     * @param jsonObject
     */
    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }
    private void onClickLogin() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
        }
    }
    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {
                @Override
                public void onError(UiError e) {
                }
                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    Log.i("tag", response.toString());
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                }
                @Override
                public void onCancel() {
                }
            };
            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);

        }
    }
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    try {
                        Gson gson=new Gson();
                        User user=gson.fromJson(response.toString(),User.class);
                        if (user!=null) {
                            tv_name.setText("昵称："+user.getNickname()+"  性别:"+user.getGender()+"  地址："+user.getProvince()+user.getCity());
                            tv_content.setText("头像路径："+user.getFigureurl_qq_2());
                            Picasso.with(MainActivity.this).load(response.getString("figureurl_qq_2")).into(imageView);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    //qq分享
    private void onClickShare() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
                QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,
                "http://blog.csdn.net/DickyQie/article/list/1");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
                "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "切切歆语");
        params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
        mTencent.shareToQQ(MainActivity.this, params, new BaseUiListener1());
    }
    //回调接口  (成功和失败的相关操作)
    private class BaseUiListener1 implements IUiListener {
        @Override
        public void onComplete(Object response) {
            doComplete(response);
        }

        protected void doComplete(Object values) {
        }

        @Override
        public void onError(UiError e) {
        }

        @Override
        public void onCancel() {
        }
    }

    @SuppressWarnings("unused")
    private void shareToQQzone() {
        try {
            final Bundle params = new Bundle();
            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
                    QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "切切歆语");
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "sss");
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,
                    "http://blog.csdn.net/DickyQie/article/list/1");
            ArrayList<String> imageUrls = new ArrayList<String>();
            imageUrls.add("http://media-cdn.tripadvisor.com/media/photo-s/01/3e/05/40/the-sandbar-that-links.jpg");
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
            params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT,
                    QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
            Tencent mTencent = Tencent.createInstance("1106062414",
                    MainActivity.this);
            mTencent.shareToQzone(MainActivity.this, params,
                    new BaseUiListener1());
        } catch (Exception e) {
        }
    }

}
