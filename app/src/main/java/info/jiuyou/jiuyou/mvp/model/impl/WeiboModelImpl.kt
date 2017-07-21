package info.jiuyou.jiuyou.mvp.model.impl


import info.jiuyou.jiuyou.bean.Emotions
import info.jiuyou.jiuyou.bean.weibo.StatusesResultBase
import info.jiuyou.jiuyou.mvp.model.WeiboModel
import info.jiuyou.jiuyou.net.NetCallback
import info.jiuyou.jiuyou.net.RF
import info.jiuyou.jiuyou.net.WeiboService
import retrofit2.Call
import retrofit2.Response


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
 * 创建日期 ：2017/4/24 0024  16:36
 * <br></br>
 * 描   述 ：
 * <br></br>
 * 修订历史 ：
 *
 * ==========================================
 */
class WeiboModelImpl(val myListener: WeiboModel.ResponseListener) : WeiboModel {
    override fun emotions(token: String) {
        RF.getInstance()
                .create(WeiboService::class.java)
                .emotions(token)
                .enqueue(object : NetCallback<List<Emotions>>(myListener) {
                    override fun onNetResponse(call: Call<List<Emotions>>, response: Response<List<Emotions>>) {
                        myListener.emotions(response.body())
                    }
                })
    }

    override fun webStatuses(token: String, since_id: String, max_id: String, count: String) {
        RF.getInstance()
                .create(WeiboService::class.java)
                .homeTimeline(token, since_id, max_id, count)
                .enqueue(object : NetCallback<StatusesResultBase>(myListener) {
                    override fun onNetResponse(call: Call<StatusesResultBase>, response: Response<StatusesResultBase>) {
                        myListener.webStatusesResult(response.body())
                    }
                })

    }

}
