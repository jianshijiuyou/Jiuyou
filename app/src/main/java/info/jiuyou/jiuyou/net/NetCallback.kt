package info.jiuyou.jiuyou.net

import info.jiuyou.jiuyou.mvp.model.BaseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/6/4  16:25
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
abstract class NetCallback<T>(val listener: BaseModel.ResponseListener) : Callback<T> {
    override fun onFailure(call: Call<T>?, t: Throwable) {
        t.printStackTrace()
        listener.error(ApiException(ApiException.MSG_NETWORK_ERROR))
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.code() != 200) {
            listener.error(ApiException(ApiException.WEIBO_SDK_ERROR))
        } else {
            onNetResponse(call, response)
        }
    }

    open fun onNetResponse(call: Call<T>, response: Response<T>) {}
}