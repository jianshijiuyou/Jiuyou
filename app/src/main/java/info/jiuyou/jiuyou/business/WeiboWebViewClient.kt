package info.jiuyou.jiuyou.business

import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import info.jiuyou.jiuyou.config.WeiboConstants
import info.jiuyou.jiuyou.net.RF
import info.jiuyou.jiuyou.net.WeiboService
import info.jiuyou.wang.log.L
import java.io.ByteArrayInputStream

/**
 * ==========================================
 *
 *
 * 版   权 ：jianshijiuyou(c) 2017
 * <br></br>
 * 作   者 ：wq
 * <br></br>
 * 版   本 ：1.0
 * <br></br>
 * 创建日期 ：2017/6/26 0026  10:30
 * <br></br>
 * 描   述 ：
 * <br></br>
 * 修订历史 ：
 *
 * ==========================================
 */
class WeiboWebViewClient : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        //L.d("shouldOverrideUrlLoading",url)
        view.loadUrl(url)
        return true
    }


    override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse? {
        L.d("shouldInterceptRequest",url)
        //隐藏微博自带的 title bar
        //隐藏按钮 "微博内打开"
        if (url.startsWith(WeiboConstants.WEIBO_DETIAL_BASE_URL)) {
            L.d("拦截处理",url)
            var html = RF.getInstance()
                    .create(WeiboService::class.java)
                    .requestHtml(url)
                    .execute()
                    .body()
                    .string()
            html += """
<script>
    window.onload=function(){
        document.getElementsByTagName("aside")[0].style="display:none"
        document.getElementsByClassName("m-banner")[0].style="display:none"
        document.getElementsByTagName("footer")[0].style="display:none"
    }
</script>
                """

            return WebResourceResponse("text/html", "utf-8", ByteArrayInputStream(html.toByteArray()))
        }


        return null
    }
}
