#Android之第三方平台实现QQ登录和QQ分享 
<p>目前大多数APP都包含了第三方平台的登录，特别是QQ和微信，这篇博客主要讲的是如何实现QQ第三方平台实现QQ登录和分享功能，功能包含：</p> 
<ul> 
 <li>登录授权登录获取用户信息（昵称，头像，地址等）</li> 
 <li>QQ分享给好友</li> 
 <li>QQ分享到空间</li> 
</ul> 
<p>先看看效果图：</p> 
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img alt="" src="https://static.oschina.net/uploads/img/201703/27161004_cLsP.gif"> &nbsp;&nbsp;</p> 
<p>要实现以上功能，首先得去腾讯开放平台 注册成为开发者，然后创建应用，得到 APPID，如下图：</p> 
<p><img alt="" height="78" src="https://static.oschina.net/uploads/space/2017/0327/154506_KJZr_2945455.png" width="610"></p> 
<p>名称就是APP名称，可上传Logo图片，授权登录时会显示授权给那个APP，效果如图：</p> 
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img alt="" src="https://static.oschina.net/uploads/space/2017/0327/154639_Lxzf_2945455.png"></p> 
<p>创建成功后，取得APP ID即可，完成以上功能了。</p> 
<p>添加这两个jar包</p> 
<p><img alt="" height="43" src="https://static.oschina.net/uploads/space/2017/0327/155136_H4ge_2945455.png" width="214"></p> 
<pre><code class="language-java"> public static Tencent mTencent;
 public static String mAppid="申请的APPID";

 if (mTencent == null) {
     mTencent = Tencent.createInstance(mAppid, this);
 }</code></pre> 
<pre><code class="language-java">    /**
     * 继承的到BaseUiListener得到doComplete()的方法信息
     */
    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {//得到用户的ID  和签名等信息  用来得到用户信息
            Log.i("lkei",values.toString());
            initOpenidAndToken(values);
            updateUserInfo();
        }
    };</code></pre> 
<pre><code class="language-java">    //qq分享
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
    }</code></pre> 
<pre><code class="language-java">   private void shareToQQzone() {
        try {
            final Bundle params = new Bundle();
            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
                    QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "切切歆语");
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "sss");
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,
                    "http://blog.csdn.net/DickyQie/article/list/1");
            ArrayList&lt;String&gt; imageUrls = new ArrayList&lt;String&gt;();
            imageUrls.add("http://media-cdn.tripadvisor.com/media/photo-s/01/3e/05/40/the-sandbar-that-links.jpg");
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
            params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT,
                    QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
            Tencent mTencent = Tencent.createInstance("申请的APPID",
                    MainActivity.this);
            mTencent.shareToQzone(MainActivity.this, params,
                    new BaseUiListener1());
        } catch (Exception e) {
        }
    }</code></pre> 
<p>在AndroidManifest.xml中</p> 
<p>application 下：</p> 
<pre><code class="language-html">        &lt;activity
            android:name="com.tencent.open.yyb.AppbarActivity"
            android:configChanges="orientation|keyboardHidden"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" /&gt;
        &lt;activity android:name="com.tencent.connect.avatar.ImageActivity" /&gt;
        &lt;activity
            android:name="com.tencent.connect.common.AssistActivity"
            android:configChanges="orientation|keyboardHidden"
            android:screenOrientation="behind"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" /&gt;
        &lt;activity
            android:name="com.tencent.tauth.AuthActivity"
            android:launchMode="singleTask"
            android:noHistory="true" &gt;
            &lt;intent-filter&gt;
                &lt;action android:name="android.intent.action.VIEW" /&gt;
                &lt;category android:name="android.intent.category.DEFAULT" /&gt;
                &lt;category android:name="android.intent.category.BROWSABLE" /&gt;
                &lt;data android:scheme="tencent1106062414" /&gt;
                &lt;!--应用的AppId要相同--&gt;
            &lt;/intent-filter&gt;
        &lt;/activity&gt;</code></pre> 
<p>添加权限</p> 
<pre><code class="language-html">    &lt;uses-permission android:name="android.permission.INTERNET" /&gt;
    &lt;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /&gt;
    &lt;uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /&gt;
    &lt;!-- SDK2.1新增获取用户位置信息 --&gt;
    &lt;uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /&gt;
    &lt;uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" /&gt;
    &lt;uses-permission android:name="android.permission.ACCESS_LOCATION_EXTRA_COMMANDS" /&gt;
    &lt;uses-permission android:name="android.permission.CHANGE_WIFI_STATE" /&gt;
    &lt;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /&gt;
    &lt;uses-permission android:name="android.permission.ACCESS_WIFI_STATE" /&gt;
    &lt;uses-permission android:name="android.permission.READ_PHONE_STATE" /&gt;
    &lt;uses-permission android:name="android.permission.GET_TASKS"/&gt;</code></pre> 
<p>源码有点多就不一一贴出来了，直接下载源码即可，其他功能可以参考腾讯开放平台的文档。</p> 
<span id="OSC_h1_1"></span>
