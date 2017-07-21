package info.jiuyou.jiuyou.mvp.model


import info.jiuyou.jiuyou.net.ApiException

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
interface BaseModel {
    interface ResponseListener {
        fun error(e: ApiException)

    }
}
