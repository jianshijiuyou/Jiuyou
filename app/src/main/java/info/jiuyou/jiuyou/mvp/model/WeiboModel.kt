package info.jiuyou.jiuyou.mvp.model

import info.jiuyou.jiuyou.bean.Emotions
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
 * 创建日期 ：2017/5/25 0025  16:20
 * <br></br>
 * 描   述 ：
 * <br></br>
 * 修订历史 ：
 *
 * ==========================================
 */
interface WeiboModel : BaseModel {
    fun webStatuses(token: String, since_id: String = "0", max_id: String = "0", count: String = "20")
    fun emotions(token:String)
    interface ResponseListener : BaseModel.ResponseListener {
        fun webStatusesResult(data: StatusesResultBase)
        fun emotions(data:List<Emotions>)
    }
}
