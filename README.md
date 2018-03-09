### Android之微信开放平台实现分享（分享好友和朋友圈）
<p>开发中分享操作往往经常遇到，而且还是一些比较大型一定的平台，如微信，QQ，微博等。写这篇博客主要是把微信的的分享和相关操作表达一下，分享可以包含：文字，视频，音乐，图片等分享。</p> 
<p>分享可以有 分享给好友，群，朋友圈等。</p> 
<p>效果如下图：（注意：功能根据自己的需要选择就可以了）</p> 
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img alt="" src="https://static.oschina.net/uploads/img/201703/22163954_X9tC.gif"></p> 
<p>准备工作：</p> 
<p>微信开放平台创建应用，操作步骤请看博客：<a href="https://my.oschina.net/zhangqie/blog/864683" rel="nofollow">Android之微信开放平台创建应用</a></p> 
<p>成功之后得到AppId，放入项目中。</p> 
<p>代码：</p> 
<pre><code class="language-java">public class Constants {
    // APP_ID 替换为你的应用从官方网站申请到的合法appId==
    public static final String APP_ID = "wx1b73fd7b49ffa027";
    
    public static class ShowMsgActivity {
        public static final String STitle = "showmsg_title";
        public static final String SMessage = "showmsg_message";
        public static final String BAThumbData = "showmsg_thumb_data";
    }</code></pre> 
<pre><code class="language-java">public class AppRegister extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI api = WXAPIFactory.createWXAPI(context, null);

        // 将该app注册到微信
        api.registerApp(Constants.APP_ID);
    }
}</code></pre> 
<p>文本分享代码：</p> 
<pre><code class="language-java">// 初始化一个WXTextObject对象
                        WXTextObject textObj = new WXTextObject();
                        textObj.text = text;
                        // 用WXTextObject对象初始化一个WXMediaMessage对象
                        WXMediaMessage msg = new WXMediaMessage();
                        msg.mediaObject = textObj;
                        // 发送文本类型的消息时，title字段不起作用
                        msg.title = "Will be ignored";
                        msg.description = text;

                        // 构造一个Req
                        SendMessageToWX.Req req = new SendMessageToWX.Req();
                        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
                
                        req.message = msg;
                        /**
                         * 判断是否是朋友圈
                         */
                        req.scene = isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                        
                        // 调用api接口发送数据到微信
                        api.sendReq(req);</code></pre> 
<p>图片分享代码：（包含 本地图片，SD卡图片，网络图片）</p> 
<pre><code class="language-java">Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.send_img);
                            WXImageObject imgObj = new WXImageObject(bmp);
                            WXMediaMessage msg = new WXMediaMessage();
                            msg.mediaObject = imgObj;
                            
                            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                            bmp.recycle();
                            msg.thumbData = Util.bmpToByteArray(thumbBmp, true);  // 设置所图；

                            SendMessageToWX.Req req = new SendMessageToWX.Req();
                            req.transaction = buildTransaction("img");
                            req.message = msg;
                            req.scene = isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                            api.sendReq(req);</code></pre> 
<p>音乐分享代码</p> 
<pre><code class="language-java">WXMusicObject music = new WXMusicObject();
                            music.musicUrl="http://staff2.ustc.edu.cn/~wdw/softdown/index.asp/0042515_05.ANDY.mp3";
                            WXMediaMessage msg = new WXMediaMessage();
                            msg.mediaObject = music;
                            msg.title = "标题";
                            msg.description = "描述信息";

                            Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.send_music_thumb);
                            msg.thumbData = Util.bmpToByteArray(thumb, true);

                            SendMessageToWX.Req req = new SendMessageToWX.Req();
                            req.transaction = buildTransaction("music");
                            req.message = msg;
                            req.scene = isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                            api.sendReq(req);</code></pre> 
<p>视频分享代码</p> 
<pre><code class="language-java">WXVideoObject video = new WXVideoObject();
                            video.videoUrl = "url....mp4";//mp4视频路径
                            WXMediaMessage msg = new WXMediaMessage(video);
                            msg.title = "标题";
                            msg.description = "描述信息";

Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.send_music_thumb); msg.thumbData = Util.bmpToByteArray(thumb, true); SendMessageToWX.Req req = new SendMessageToWX.Req(); req.transaction = buildTransaction("video"); req.message = msg; req.scene = isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession; api.sendReq(req);</code></pre> 
<p>网页分享代码：</p> 
<pre><code class="language-java">WXWebpageObject webpage = new WXWebpageObject();
                            webpage.webpageUrl = "http://www.baidu.com";
                            WXMediaMessage msg = new WXMediaMessage(webpage);
                            msg.title = "标题";
                            msg.description = "描述信息";
                            Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.send_music_thumb);
                            msg.thumbData = Util.bmpToByteArray(thumb, true);
                            
                            SendMessageToWX.Req req = new SendMessageToWX.Req();
                            req.transaction = buildTransaction("webpage");
                            req.message = msg;
                            req.scene = isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                            api.sendReq(req);</code></pre> 
<p>等等。</p> 
<span id="OSC_h1_1"></span>
