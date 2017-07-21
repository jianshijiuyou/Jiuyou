package info.jiuyou.jiuyou.config

import android.os.Environment

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/5/24 0024  14:52
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
object WeiboConstants {
    val WEIBO_DETIAL_BASE_URL:String="https://m.weibo.cn/status/"
    val APP_KEY:String="1938759242"
    val REDIRECT_URL:String="https://api.weibo.com/oauth2/default.html"
    val SCOPE:String="all"
    val cacheDir:String=Environment.getExternalStorageDirectory().absolutePath+"/jiuyou/cache/"
    val weibo_token="weibo_token"
}