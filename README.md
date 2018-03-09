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

