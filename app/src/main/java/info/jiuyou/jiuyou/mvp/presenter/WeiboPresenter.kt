package info.jiuyou.jiuyou.mvp.presenter

import info.jiuyou.jiuyou.bean.weibo.Status
import info.jiuyou.jiuyou.bean.weibo.StatusesResultBase

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
interface WeiboPresenter {
    fun webStatuses(token: String, since_id: String = "0", max_id: String = "0", count: String = "20")
    fun requestEmotions(token: String)
}
