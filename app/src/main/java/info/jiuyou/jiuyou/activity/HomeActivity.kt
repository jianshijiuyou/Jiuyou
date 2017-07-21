package info.jiuyou.jiuyou.activity

import android.os.Bundle
import android.text.TextUtils
import info.jiuyou.jiuyou.App
import info.jiuyou.jiuyou.R
import info.jiuyou.jiuyou.config.WeiboConstants
import info.jiuyou.jiuyou.fragment.LeftMenuFragment
import info.jiuyou.jiuyou.fragment.SettingFragment
import info.jiuyou.jiuyou.fragment.WeiboAuthorizationFragment
import info.jiuyou.jiuyou.fragment.WeiboHomeFragment
import info.jiuyou.wang.log.L
import info.jiuyou.wang.utils.SPUtils
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.toast


class HomeActivity : AbstractActivity(), LeftMenuFragment.MenuHandler {

    override fun getContentViewId(): Int = R.layout.activity_home

    override fun initView(savedInstanceState: Bundle?) {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024/1024).toInt()
        L.d("TAG", "Max memory is " + maxMemory + "MB")


        createFragment<LeftMenuFragment>(WeiboAuthorizationFragment.FTag, R.id.leftMenu) {
            it.setMenuHandler(this)
        }
        val token: String = SPUtils.getVal(WeiboConstants.weibo_token, "")
        if (TextUtils.isEmpty(token)) {
            createFragment<WeiboAuthorizationFragment>(WeiboAuthorizationFragment.FTag, R.id.contentPanel)
        }else{
            App.instance().weiboUser.token=token
            createFragment<WeiboHomeFragment>(WeiboHomeFragment.FTag, R.id.contentPanel)
        }
    }

    override fun home() {
        createFragment<WeiboHomeFragment>(WeiboHomeFragment.FTag, R.id.contentPanel)
        drawerLayout.closeDrawers()
    }

    override fun setting() {
        createFragment<SettingFragment>(SettingFragment.FTag,R.id.contentPanel)
        drawerLayout.closeDrawers()
    }

}
