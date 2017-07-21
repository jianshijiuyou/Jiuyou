package info.jiuyou.jiuyou.activity

import android.os.Bundle
import android.os.Handler
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.just.library.AgentWeb
import info.jiuyou.jiuyou.R
import info.jiuyou.jiuyou.business.WeiboWebViewClient
import info.jiuyou.wang.log.L
import info.jiuyou.wang.utils.T
import kotlinx.android.synthetic.main.activity_web.*
import org.jetbrains.anko.toast


class WebActivity : AbstractActivity() {
    var agentWeb: AgentWeb? = null
    override fun getContentViewId(): Int = R.layout.activity_web

    override fun initView(savedInstanceState: Bundle?) {

        val url = intent?.getStringExtra("url")
        if (url == null) {
            finish()
            toast(getString(R.string.invalid_link))
        }

        agentWeb = AgentWeb.with(this@WebActivity)//传入Activity
                .setAgentWebParent(llContent, LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator()// 使用默认进度条
                .defaultProgressBarColor() // 使用默认进度条颜色
                .setWebViewClient(WeiboWebViewClient())
                //   .setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()
                .go(url)
    }

    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

}
