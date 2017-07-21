package info.jiuyou.jiuyou.fragment

import android.os.Bundle
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WbConnectErrorMessage
import com.sina.weibo.sdk.auth.sso.SsoHandler
import info.jiuyou.jiuyou.App
import info.jiuyou.jiuyou.R
import info.jiuyou.jiuyou.activity.AbstractActivity
import info.jiuyou.jiuyou.config.WeiboConstants
import info.jiuyou.wang.log.L
import info.jiuyou.wang.utils.SPUtils
import kotlinx.android.synthetic.main.fragment_weibo_author.*
import org.jetbrains.anko.toast


/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/5/24 0024  11:38
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
class WeiboAuthorizationFragment : AbstractFragment(), com.sina.weibo.sdk.auth.WbAuthListener {
    companion object {
        val FTag: String = "WeiboAuthorizationFragment"
    }


    override fun getLayoutId(): Int = R.layout.fragment_weibo_author

    override fun initView(savedInstanceState: Bundle?) {
        val mSsoHandler = SsoHandler(activity)
        btnAuthor.setOnClickListener {
            mSsoHandler.authorizeWeb(this)
        }

    }

    override fun onSuccess(p0: Oauth2AccessToken?) {
        SPUtils.setVal(WeiboConstants.weibo_token, p0?.token)
        App.instance().weiboUser.token = p0?.token!!
        (activity as AbstractActivity).createFragment<WeiboHomeFragment>(WeiboHomeFragment.FTag, R.id.contentPanel)
    }

    override fun onFailure(p0: WbConnectErrorMessage?) {
        L.d("onFailure")
        L.d(p0?.errorCode, p0?.errorMessage)
    }

    override fun cancel() {
        context.toast("授权取消")
    }

}