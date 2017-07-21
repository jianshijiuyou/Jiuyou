package info.jiuyou.jiuyou

import android.app.Application
import com.bumptech.glide.Glide
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AuthInfo
import info.jiuyou.greendao.gen.DaoMaster
import info.jiuyou.greendao.gen.DaoSession
import info.jiuyou.jiuyou.bean.WeiboUser
import info.jiuyou.jiuyou.config.WeiboConstants
import info.jiuyou.wang.Wang
import skin.support.SkinCompatManager

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
 * 创建日期 ：2017/5/24 0024  15:09
 * <br></br>
 * 描   述 ：
 * <br></br>
 * 修订历史 ：
 *
 * ==========================================
 */
class App : Application() {
    companion object {
        private var instance: App? = null
        fun instance() = instance!!
    }

    val weiboUser: WeiboUser = WeiboUser("")
    var daoSession: DaoSession? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        WbSdk.install(applicationContext, AuthInfo(applicationContext, WeiboConstants.APP_KEY, WeiboConstants.REDIRECT_URL, WeiboConstants.SCOPE))
        Wang.init(applicationContext)

        initDaoSession()

        SkinCompatManager.init(this).loadSkin()



    }

    private fun initDaoSession() {
        val devOpenHelper = DaoMaster.DevOpenHelper(applicationContext, "jiuyou_db")
        val mDaoMaster = DaoMaster(devOpenHelper.writableDatabase)
        daoSession = mDaoMaster.newSession()
    }

}
