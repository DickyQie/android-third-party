# Android支付之支付宝封装类
 <p>今天介绍下在android中如何集成支付宝支付到自己的APP中去。让APP能够拥有方便，快捷的支付功能。</p> 
<p>我们在做Android支付的时候肯定会用到支付宝支付，根据官方给出的demo做起来非常费劲，所以我们需要一次简单的封装。只需要一个方法调用便可实现。</p> 
<p>如图：</p> 
<p><img alt="" height="559" src="https://static.oschina.net/uploads/space/2016/1215/093420_gKHG_2945455.png" width="316">&nbsp;<img alt="" height="558" src="https://static.oschina.net/uploads/space/2016/1215/093434_4aPW_2945455.png" width="315"></p> 
<p>首先，导入需要的支付宝SDK资源放入商户应用工程的libs目录下</p> 
<p><img alt="" src="http://img.blog.csdn.net/20161011113359572?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center"></p> 
<p>Activity支付调用代码：</p> 
<pre><code class="language-java">public class PayDemoActivity extends Activity {

	// 商户PID
	public static final String PARTNER = Keys.DEFAULT_PARTNER;
	// 商户收款账号
	public static final String SELLER = Keys.DEFAULT_SELLER;
	// 商户私钥，pkcs8格式  ===支付宝公钥
	public static final String RSA_PRIVATE = Keys.PRIVATE;

	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_external);
		findViewById(R.id.pay).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ExternalPartner.getInstance(PayDemoActivity.this,"支付时出现的订单信息" ,"123456",
						mHandler, "0.1").doOrder();
			}
		});
		findViewById(R.id.check).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				ExternalPartner.getInstance(PayDemoActivity.this,"物业费", "123456",
						mHandler, "0.1").check();
			}
		});
	}
	
	
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(PayDemoActivity.this, "支付成功",
							Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayDemoActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PayDemoActivity.this, "支付失败",
								Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(PayDemoActivity.this, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};
}
</code></pre> 
<p>AndroidManifest.xml配置</p> 
<pre><code class="language-html">&lt;uses-permission android:name="android.permission.INTERNET" /&gt;
&lt;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /&gt;
&lt;uses-permission android:name="android.permission.ACCESS_WIFI_STATE" /&gt;
&lt;uses-permission android:name="android.permission.READ_PHONE_STATE" /&gt;
&lt;uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /&gt;    
       &lt;supports-screens
        android:anyDensity="true"
        android:largeScreens="true"
        android:normalScreens="true"
        android:resizeable="true"
        android:smallScreens="true" /&gt;

    &lt;application
        android:allowBackup="true"
        android:label="@string/app_name"
        android:theme="@android:style/Theme.Light.NoTitleBar" &gt;
        &lt;activity
            android:name="com.example.alipaydemo.PayDemoActivity"
            android:icon="@drawable/msp_icon"
            android:label="@string/app_name"&gt;
            &lt;intent-filter&gt;
                &lt;action android:name="android.intent.action.MAIN" /&gt;
                &lt;category android:name="android.intent.category.LAUNCHER" /&gt;
            &lt;/intent-filter&gt;
        &lt;/activity&gt;  

        &lt;!-- alipay sdk begin --&gt;
        &lt;activity
            android:name="com.alipay.sdk.app.H5PayActivity"
            android:configChanges="orientation|keyboardHidden|navigation"
            android:exported="false"
            android:screenOrientation="behind"
            android:windowSoftInputMode="adjustResize|stateHidden" &gt;
        &lt;/activity&gt;
    &lt;/application&gt;</code></pre> 
<p>封装工具类代码代码太多未给出，直接下载即可。</p> 
