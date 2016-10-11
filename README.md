# Android支付之支付宝封装类
<div id="article_content" class="article_content">

<p class="reader-word-layer" style=""></p>
<p><span style="font-size:18px"><span style="font-family:宋体; text-indent:28px; background-color:rgb(249,249,249)">今天介绍下在android中如何集成支付宝支付到自己的APP中去。让APP能够拥有方便，快捷的支付功能。</span><br>
</span></p>
<p><span style="font-size:18px">我们在做Android支付的时候肯定会用到支付宝支付，</span><span style="font-size:18px">根据官方给出的demo做起来非常费劲，所以我们需要一次简单的封装。只需要一个方法调用便可实现。</span></p>
<p><span style="font-size:18px">如图：</span></p>
<p><span style="font-size:18px"><span style="white-space:pre"><img src="http://img.blog.csdn.net/20161011113920516?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center" alt=""></span>&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<img src="http://img.blog.csdn.net/20161011113950173?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center" alt="">&nbsp;
 &nbsp;&nbsp;&nbsp;&nbsp;</span></p>
<p><span style="font-size:18px"><br>
</span></p>
<p><span style="background-color:rgb(249,249,249); font-family:宋体; font-size:14px">首先，导入需要的支付宝SDK资源放入商户应用工程的libs目录下</span></p>
<p><span style="background-color:rgb(249,249,249); font-family:宋体; font-size:14px"><img src="http://img.blog.csdn.net/20161011113359572?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center" alt=""><br>
</span></p>
<p><span style="font-family:宋体; font-size:18px"><span style="background-color:rgb(249,249,249)"><span style="color:#cc0000">Activity支付调用</span>代码：</span></span></p>
<p></p>
<p style="font-size:18px"><strong><span style="color:#7F0055">public</span> <span style="color:#7F0055">
class&nbsp;</span></strong>AlipayDemoActivity <strong><span style="color:#7F0055">extends</span></strong> Activity {</p>
<p style="font-size:18px">&nbsp;</p>
<p style="font-size:18px">&nbsp;&nbsp; <span style="color:#3F7F5F">// </span><span style="color:#3F7F5F">商户</span><span style="color:#3F7F5F">PID</span></p>
<p style="font-size:18px">&nbsp;&nbsp; <strong><span style="color:#7F0055">public</span> <span style="color:#7F0055">
static</span> <span style="color:#7F0055">final</span></strong> String <em><span style="color:#0000C0">PARTNER</span></em> = Keys.<em><span style="color:#0000C0">DEFAULT_PARTNER</span></em>;</p>
<p style="font-size:18px">&nbsp;&nbsp; <span style="color:#3F7F5F">// </span><span style="color:#3F7F5F">商户收款账号</span></p>
<p style="font-size:18px">&nbsp;&nbsp; <strong><span style="color:#7F0055">public</span> <span style="color:#7F0055">
static</span> <span style="color:#7F0055">final</span></strong> String <em><span style="color:#0000C0">SELLER</span></em> = Keys.<em><span style="color:#0000C0">DEFAULT_SELLER</span></em>;</p>
<p style="font-size:18px">&nbsp;&nbsp; <span style="color:#3F7F5F">// </span><span style="color:#3F7F5F">商户私钥，</span><span style="color:#3F7F5F">pkcs8</span><span style="color:#3F7F5F">&#26684;式</span></p>
<p style="font-size:18px">&nbsp;&nbsp; <strong><span style="color:#7F0055">public</span> <span style="color:#7F0055">
static</span> <span style="color:#7F0055">final</span></strong> String <em><span style="color:#0000C0">RSA_PRIVATE</span></em> = Keys.<em><span style="color:#0000C0">PRIVATE</span></em>;</p>
<p style="font-size:18px">&nbsp;&nbsp; <span style="color:#3F7F5F">// </span><span style="color:#3F7F5F">支付宝公钥</span></p>
<p style="font-size:18px">&nbsp;&nbsp; <strong><span style="color:#7F0055">public</span> <span style="color:#7F0055">
static</span> <span style="color:#7F0055">final</span></strong> String <em><span style="color:#0000C0">RSA_PUBLIC</span></em> = Keys.<em><span style="color:#0000C0">PRIVATE</span></em>;</p>
<p style="font-size:18px">&nbsp;</p>
<p style="font-size:18px">&nbsp;&nbsp; <strong><span style="color:#7F0055">private</span> <span style="color:#7F0055">
static</span> <span style="color:#7F0055">final</span> <span style="color:#7F0055">
int</span></strong> <em><span style="color:#0000C0">SDK_PAY_FLAG</span></em> = 1;</p>
<p style="font-size:18px">&nbsp;</p>
<p style="font-size:18px">&nbsp;&nbsp; <strong><span style="color:#7F0055">private</span> <span style="color:#7F0055">
static</span> <span style="color:#7F0055">final</span> <span style="color:#7F0055">
int</span></strong> <em><span style="color:#0000C0">SDK_CHECK_FLAG</span></em> = 2;</p>
<p style="font-size:18px">&nbsp;</p>
<p style="font-size:18px">&nbsp;&nbsp; <span style="color:#646464">@Override</span></p>
<p style="font-size:18px">&nbsp;&nbsp; <strong><span style="color:#7F0055">protected</span><span style="color:#7F0055">void &nbsp;</span></strong>onCreate(Bundle savedInstanceState) {</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">super</span></strong>.onCreate(savedInstanceState);</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; setContentView(R.layout.<em><span style="color:#0000C0">pay_external</span></em>);</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; findViewById(R.id.<em><span style="color:#0000C0">pay</span></em>).setOnClickListener(<strong><span style="color:#7F0055">new&nbsp;</span></strong>OnClickListener() {</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#646464">@Override</span></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">public</span><span style="color:#7F0055">void&nbsp;</span></strong>onClick(View v) {</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">// </span><strong><span style="color:#7F9FBF">TODO</span></strong><span style="color:#3F7F5F">Auto-generated method stub</span></p>
<p style="font-size:18px"><span style="white-space:pre"></span></p>
<p style="color:rgb(63,127,95)"><span style="color:#3F5FBF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;/** </span>
</p>
<p style="color:rgb(63,127,95)"><span style="color:#3F5FBF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;* </span>
<strong><span style="color:#7F9FBF">@param</span></strong><span style="color:#3F5FBF"> context</span></p>
<p style="color:rgb(63,127,95)"><span style="color:#3F5FBF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="color:#3F5FBF">上下文</span></p>
<p><span style="color:rgb(63,127,95); font-size:18px; white-space:pre"></span></p>
<p style="color:rgb(63,95,191)"><span style="color:rgb(63,95,191)">&nbsp;* </span><strong><span style="color:rgb(127,159,191)">@param</span></strong><span style="color:rgb(63,95,191)"> orderName</span></p>
<p><span style="color:rgb(63,95,191)">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="color:rgb(63,95,191)">支付时出现的订单信息</span></p>
<p style="color:rgb(63,127,95)"><span style="color:#3F5FBF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;* </span>
<strong><span style="color:#7F9FBF">@param</span></strong><span style="color:#3F5FBF"> orderNO</span></p>
<p style="color:rgb(63,127,95)"><span style="color:#3F5FBF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="color:#3F5FBF">订单号</span></p>
<p style="color:rgb(63,127,95)"><span style="color:#3F5FBF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;* </span>
<strong><span style="color:#7F9FBF">@param</span></strong><span style="color:#3F5FBF"> mHandler</span></p>
<p style="color:rgb(63,127,95)"><span style="color:#3F5FBF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="color:#3F5FBF">回调响应</span></p>
<p style="color:rgb(63,127,95)"><span style="color:#3F5FBF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;* </span>
<strong><span style="color:#7F9FBF">@param</span></strong><span style="color:#3F5FBF"> order</span></p>
<p style="color:rgb(63,127,95)"><span style="color:#3F5FBF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="color:#3F5FBF">价&#26684;</span></p>
<p style="color:rgb(63,127,95)"><span style="color:#3F5FBF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;*/</span></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ExternalPartner.<em>getInstance</em>(<span style="font-size:18px">Alipay</span><span style="font-size:18px">DemoActivity</span>.<strong><span style="color:#7F0055">this</span></strong>,<span style="color:#2A00FF">&quot;</span><span style="color:#2A00FF">物业费</span><span style="color:#2A00FF">&quot;</span>,<span style="color:#3F7F5F">//</span><span style="color:#3F7F5F">支付时出现的订单信息</span></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#2A00FF">&quot;201610111325656896123&quot;</span>,<span style="color:#0000C0">handler</span>,
<span style="color:#2A00FF">&quot;0.1&quot;</span>).doOrder();</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; });</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; findViewById(R.id.<em><span style="color:#0000C0">check</span></em>).setOnClickListener(<strong><span style="color:#7F0055">new&nbsp;</span></strong>OnClickListener() {</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#646464">@Override</span></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">public</span><span style="color:#7F0055">void</span></strong>onClick(View v) {</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">// </span><strong><span style="color:#7F9FBF">TODO</span></strong><span style="color:#3F7F5F">Auto-generated method stub</span></p>
<p style="font-size:18px">&nbsp;</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ExternalPartner.<em>getInstance</em>(<span style="font-size:18px">Alipay</span><span style="font-size:18px">DemoActivity</span>.<strong><span style="color:#7F0055">this</span></strong>,<span style="color:#2A00FF">&quot;</span><span style="color:#2A00FF">物业费</span><span style="color:#2A00FF">&quot;</span>,</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#2A00FF">&quot;<span style="color:rgb(42,0,255); font-size:18px">201610111325656896123</span>&quot;</span>,<span style="color:#0000C0"><span style="color:rgb(0,0,192); font-size:18px">handler</span></span>,<span style="color:#2A00FF">&quot;0.1&quot;</span>).check();</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; });</p>
<p style="font-size:18px">&nbsp;&nbsp; }</p>
<p style="font-size:18px">&nbsp;</p>
<p style="font-size:18px">&nbsp;&nbsp; <strong><span style="color:#7F0055">private</span></strong> Handler<span style="color:#0000C0">mHandler</span> =
<strong><span style="color:#7F0055">new</span></strong>&nbsp;&nbsp;<u>Handler</u>(){</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">public</span><span style="color:#7F0055">void&nbsp;</span></strong>handleMessage(Message msg) {</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">switch</span></strong> (msg.<span style="color:#0000C0">what</span>) {</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">case</span></strong><em><span style="color:#0000C0">SDK_PAY_FLAG</span></em>: {</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; PayResult payResult = <strong><span style="color:#7F0055">new&nbsp;</span></strong>PayResult((String) msg.<span style="color:#0000C0">obj</span>);</p>
<p style="font-size:18px">&nbsp;</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">// </span><span style="color:#3F7F5F">支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签</span></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String <u>resultInfo</u> =payResult.getResult();</p>
<p style="font-size:18px">&nbsp;</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String resultStatus =payResult.getResultStatus();</p>
<p style="font-size:18px">&nbsp;</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">// </span><span style="color:#3F7F5F">判断</span><span style="color:#3F7F5F">resultStatus</span><span style="color:#3F7F5F">为</span><span style="color:#3F7F5F">“9000”</span><span style="color:#3F7F5F">则代表支付成功，具体状态码代表含义可参考接口文档</span></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">if</span></strong>(TextUtils.<em>equals</em>(resultStatus,<span style="color:#2A00FF">&quot;9000&quot;</span>)) {</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Toast.<em>makeText</em>(<span style="font-size:18px">Alipay</span><span style="font-size:18px">DemoActivity</span>.<strong><span style="color:#7F0055">this</span></strong>,<span style="color:#2A00FF">&quot;</span><span style="color:#2A00FF">支付成功</span><span style="color:#2A00FF">&quot;</span>,Toast.<em><span style="color:#0000C0">LENGTH_SHORT</span></em>).show();</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; } <strong><span style="color:#7F0055">else</span></strong> {</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">// </span><span style="color:#3F7F5F">判断</span><span style="color:#3F7F5F">resultStatus</span><span style="color:#3F7F5F">为非</span><span style="color:#3F7F5F">“9000”</span><span style="color:#3F7F5F">则代表可能支付失败</span></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">// “8000”</span><span style="color:#3F7F5F">代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）</span></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">if</span></strong>(TextUtils.<em>equals</em>(resultStatus,<span style="color:#2A00FF">&quot;8000&quot;</span>)) {</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Toast.<em>makeText</em>(<span style="font-size:18px">Alipay</span><span style="font-size:18px">DemoActivity</span>.<strong><span style="color:#7F0055">this</span></strong>,<span style="color:#2A00FF">&quot;</span><span style="color:#2A00FF">支付结果确认中</span><span style="color:#2A00FF">&quot;</span>,Toast.<em><span style="color:#0000C0">LENGTH_SHORT</span></em>).show();</p>
<p style="font-size:18px">&nbsp;</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; } <strong><span style="color:#7F0055">else</span></strong> {</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">// </span>
<span style="color:#3F7F5F">其他&#20540;就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误</span></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Toast.<em>makeText</em>(<span style="font-size:18px">Alipay</span><span style="font-size:18px">DemoActivity</span>.<strong><span style="color:#7F0055">this</span></strong>,<span style="color:#2A00FF">&quot;</span><span style="color:#2A00FF">支付失败</span><span style="color:#2A00FF">&quot;</span>,Toast.<em><span style="color:#0000C0">LENGTH_SHORT</span></em>).show();</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">break</span></strong>;</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">case</span></strong><em><span style="color:#0000C0">SDK_CHECK_FLAG</span></em>: {</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Toast.<em>makeText</em>(<span style="font-size:18px">Alipay</span><span style="font-size:18px">DemoActivity</span>.<strong><span style="color:#7F0055">this</span></strong>,<span style="color:#2A00FF">&quot;</span><span style="color:#2A00FF">检查结果为：</span><span style="color:#2A00FF">&quot;</span>
 &#43; msg.<span style="color:#0000C0">obj</span>,Toast.<em><span style="color:#0000C0">LENGTH_SHORT</span></em>).show();</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">break</span></strong>;</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">default</span></strong>:</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">break</span></strong>;</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; };</p>
<p style="font-size:18px">&nbsp;&nbsp; };</p>
<p style="font-size:18px">}</p>
<span style="font-size:18px; color:#cc0000"><br>
</span>
<p><span style="font-size:18px; color:#cc0000">AndroidManifest.xml</span><span style="font-size:18px">配置</span></p>
<p><span style="font-size:18px"></span></p>
<p><span style="color:teal">&nbsp; &nbsp; &lt;</span><span style="color:#3F7F7F">uses-permission</span><span style="color:#7F007F">android:name</span>=<em><span style="color:#2A00FF">&quot;android.permission.INTERNET&quot;</span></em><span style="color:teal">/&gt;</span></p>
<p>&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;</span><span style="color:#3F7F7F">uses-permission</span><span style="color:#7F007F">android:name</span>=<em><span style="color:#2A00FF">&quot;android.permission.ACCESS_NETWORK_STATE&quot;</span></em><span style="color:teal">/&gt;</span></p>
<p>&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;</span><span style="color:#3F7F7F">uses-permission</span><span style="color:#7F007F">android:name</span>=<em><span style="color:#2A00FF">&quot;android.permission.ACCESS_WIFI_STATE&quot;</span></em><span style="color:teal">/&gt;</span></p>
<p>&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;</span><span style="color:#3F7F7F">uses-permission</span><span style="color:#7F007F">android:name</span>=<em><span style="color:#2A00FF">&quot;android.permission.READ_PHONE_STATE&quot;</span></em><span style="color:teal">/&gt;</span></p>
<p>&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;</span><span style="color:#3F7F7F">uses-permission</span><span style="color:#7F007F">android:name</span>=<em><span style="color:#2A00FF">&quot;android.permission.WRITE_EXTERNAL_STORAGE&quot;</span></em><span style="color:teal">/&gt;</span>&nbsp;&nbsp;&nbsp;</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;</span><span style="color:#3F7F7F">supports-screens</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:anyDensity</span>=<em><span style="color:#2A00FF">&quot;true&quot;</span></em></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:largeScreens</span>=<em><span style="color:#2A00FF">&quot;true&quot;</span></em></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:normalScreens</span>=<em><span style="color:#2A00FF">&quot;true&quot;</span></em></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:resizeable</span>=<em><span style="color:#2A00FF">&quot;true&quot;</span></em></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:smallScreens</span>=<em><span style="color:#2A00FF">&quot;true&quot;</span></em><span style="color:teal">/&gt;</span></p>
<p>&nbsp;&nbsp;&nbsp; <u><span style="color:teal">&lt;</span><span style="color:#3F7F7F">application</span></u></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:allowBackup</span>=<em><span style="color:#2A00FF">&quot;true&quot;</span></em></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:label</span>=<em><span style="color:#2A00FF">&quot;@string/app_name&quot;</span></em></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:theme</span>=<em><span style="color:#2A00FF">&quot;@android:style/Theme.Light.NoTitleBar&quot;</span></em><span style="color:teal">&gt;</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;</span><span style="color:#3F7F7F">activity</span>&nbsp;<span style="color:#7F007F">android:name</span>=<em><span style="color:#2A00FF">&quot;com.example.alipaydemo.AlipayDemoActivity&quot;</span></em></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:icon</span>=<em><span style="color:#2A00FF">&quot;@drawable/msp_icon&quot;</span></em></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:label</span>=<em><span style="color:#2A00FF">&quot;@string/app_name&quot;</span></em><span style="color:teal">&gt;</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;</span><span style="color:#3F7F7F">intent-filter</span><span style="color:teal">&gt;</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;</span><span style="color:#3F7F7F">action</span><span style="color:#7F007F">android:name</span>=<em><span style="color:#2A00FF">&quot;android.intent.action.MAIN&quot;</span></em><span style="color:teal">/&gt;</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;</span><span style="color:#3F7F7F">category</span><span style="color:#7F007F">android:name</span>=<em><span style="color:#2A00FF">&quot;android.intent.category.LAUNCHER&quot;</span></em><span style="color:teal">/&gt;</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;/</span><span style="color:#3F7F7F">intent-filter</span><span style="color:teal">&gt;</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;/</span><span style="color:#3F7F7F">activity</span><span style="color:teal">&gt;</span>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F5FBF">&lt;!-- <u>alipaysdk</u> begin --&gt;</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;</span><span style="color:#3F7F7F">activity</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:name</span>=<em><span style="color:#2A00FF">&quot;com.alipay.sdk.app.H5PayActivity&quot;</span></em></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:configChanges</span>=<em><span style="color:#2A00FF">&quot;orientation|keyboardHidden|navigation&quot;</span></em></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:exported</span>=<em><span style="color:#2A00FF">&quot;false&quot;</span></em></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:screenOrientation</span>=<em><span style="color:#2A00FF">&quot;behind&quot;</span></em></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:windowSoftInputMode</span>=<em><span style="color:#2A00FF">&quot;adjustResize|stateHidden&quot;</span></em><span style="color:teal">&gt;</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;/</span><span style="color:#3F7F7F">activity</span><span style="color:teal">&gt;</span></p>
<p><br>
</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F5FBF">&lt;!-- <u>alipaysdk</u> end --&gt;</span></p>
<p>&nbsp;</p>
<p>&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;/</span><span style="color:#3F7F7F">application</span><span style="color:teal">&gt;</span></p>
<p><span style="font-size:18px"><br>
</span></p>
<p><span style="font-size:18px">封装工具类代码代码太多未给出，直接下载即可。</span></p>
<p><span style="font-size:18px"><a target="_blank" href="http://download.csdn.net/detail/dickyqie/9650564"><br>
</a></span></p>
<p><span style="font-size:18px"><span style="color:#ff0000"><a target="_blank" href="http://download.csdn.net/detail/dickyqie/9650564">http://download.csdn.net/detail/dickyqie/9650564</a></span><br>
</span></p>
<p><br>
</p>
<p><span style="white-space:pre"></span></p>
<p><br>
</p>
<br>
<p><span style="font-size:18px"><br>
</span></p>
<p class="reader-word-layer reader-word-s1-2" style=""></p>
   
</div>
