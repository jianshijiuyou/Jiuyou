package info.jiuyou.jiuyou.mvp.view

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
 * 创建日期 ：2017/5/25 0025  16:55
 * <br></br>
 * 描   述 ：
 * <br></br>
 * 修订历史 ：
 *
 * ==========================================
 */
interface WeiboView :BaseView{
    fun webStatusesResult(data: StatusesResultBase)
}
