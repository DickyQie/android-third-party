package com.example.weixindome;

import net.sourceforge.simcpux.wxapi.WXEntryActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 签名：
 * @author Administrator
 * 微信开发者第三方平台
 * 
 * http://open.weixin.qq.com
 * 
 * 
 *
 */
public class MainActivity extends Activity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		
		Intent intent=new Intent(MainActivity.this,WXEntryActivity.class);
		startActivity(intent);
	}


}
