package info.jiuyou.jiuyou.mvp.presenter.impl


import android.text.TextUtils
import com.blankj.utilcode.util.EncryptUtils
import com.google.gson.Gson
import info.jiuyou.jiuyou.App
import info.jiuyou.jiuyou.bean.Emotions
import info.jiuyou.jiuyou.bean.weibo.Status
import info.jiuyou.jiuyou.bean.weibo.StatusesResultBase
import info.jiuyou.jiuyou.config.WeiboConstants
import info.jiuyou.jiuyou.mvp.model.WeiboModel
import info.jiuyou.jiuyou.mvp.model.impl.WeiboModelImpl
import info.jiuyou.jiuyou.mvp.presenter.WeiboPresenter
import info.jiuyou.jiuyou.mvp.view.WeiboView
import info.jiuyou.jiuyou.net.ApiException
import info.jiuyou.wang.log.L
import info.jiuyou.wang.utils.ACache
import info.jiuyou.wang.utils.T
import kotlinx.android.synthetic.main.fragment_weibo_home.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.File


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
 * 创建日期 ：2017/4/24 0024  16:24
 * <br></br>
 * 描   述 ：
 * <br></br>
 * 修订历史 ：
 *
 * ==========================================
 */
class WeiboPresenterImpl(val view: WeiboView) : WeiboPresenter, WeiboModel.ResponseListener {


    private var md5Name = ""
    private var webStausesModel: WeiboModel? = null

    init {
        webStausesModel = WeiboModelImpl(this)
    }

    //当本地没有表情的时候，去服务器拉取表情
    override fun requestEmotions(token: String) {
        val count = App.instance().daoSession?.emotionsDao?.count()
        if (count != null && count <= 0) {
            webStausesModel?.emotions(token)
        }

    }

    override fun webStatuses(token: String, since_id: String, max_id: String, count: String) {
        view.showProgress()
        doAsync {
            md5Name = EncryptUtils.encryptMD5ToString("home_timeline" + max_id + since_id)
            val str = ACache.get(File(WeiboConstants.cacheDir)).getAsString(md5Name)
            if (!TextUtils.isEmpty(str)) {
                val data = Gson().fromJson(str, StatusesResultBase::class.java)
                uiThread { webStatusesResult(data) }
            } else {
                uiThread { webStausesModel?.webStatuses(token, since_id, max_id, count) }
            }
        }

    }



    override fun error(e: ApiException) {
        view.error(e)
        view.hideProgress()
    }

    override fun webStatusesResult(data: StatusesResultBase) {

        doAsync {
            ACache.get(File(WeiboConstants.cacheDir)).put(md5Name, Gson().toJson(data), ACache.TIME_HOUR * 12)
        }

        view.hideProgress()
        view.webStatusesResult(data)
    }

    override fun emotions(data: List<Emotions>) {
        doAsync {
            App.instance().daoSession?.emotionsDao?.insertInTx(data)
        }
    }
}
