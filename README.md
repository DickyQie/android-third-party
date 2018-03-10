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

