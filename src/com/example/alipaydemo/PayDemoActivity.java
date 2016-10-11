package com.example.alipaydemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.alipay.sdk.pay.demo.ExternalPartner;
import com.alipay.sdk.pay.demo.Keys;
import com.alipay.sdk.pay.demo.PayResult;

/****
 * 
 * 支付宝分装
 * 
 * @author zq
 * 
 */
public class PayDemoActivity extends Activity {

	// 商户PID
	public static final String PARTNER = Keys.DEFAULT_PARTNER;
	// 商户收款账号
	public static final String SELLER = Keys.DEFAULT_SELLER;
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = Keys.PRIVATE;
	// 支付宝公钥
	public static final String RSA_PUBLIC = Keys.PRIVATE;

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
				
				/**
				 * 支付宝快捷支付调用类
				 * 
				 * @param context
				 *            上下文
				 * @param orderNO
				 *            订单号
				 * @param mHandler
				 *            回调响应句柄
				 * @param orderFEE
				 *            价格
				 */
				ExternalPartner.getInstance(PayDemoActivity.this, "物业费",//支付时出现的订单信息
						"201610111325656896123", mHandler, "0.1").doOrder();
			}
		});
		findViewById(R.id.check).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ExternalPartner.getInstance(PayDemoActivity.this, "物业费",
						"201610111325656896123", mHandler, "0.1").check();
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
