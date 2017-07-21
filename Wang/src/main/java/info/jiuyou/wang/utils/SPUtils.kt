package info.jiuyou.wang.utils

import android.content.Context
import android.content.SharedPreferences

import info.jiuyou.wang.Wang

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
 * 创建日期 ：2017/4/26 0026  10:44
 * <br></br>
 * 描   述 ：
 * <br></br>
 * 修订历史 ：
 *
 * ==========================================
 */
object SPUtils {
    private var sharedPreferences: SharedPreferences? = null

    fun <T> setVal(key: String, value: T) {

        when (value) {
            is String -> setString(key, value)
            is Boolean -> setBoolean(key, value)
            is Int -> setInt(key, value)
            else -> throw IllegalArgumentException("不支持的参数类型")
        }
    }

    fun <T> getVal(key: String, def: T): T {
        var res = when (def) {
            is String -> getString(key, def)
            is Boolean -> getBoolean(key, def)
            is Int -> getInt(key, def)
            else -> throw IllegalArgumentException("不支持的参数类型")
        }
        return res as T
    }


    fun setBoolean(key: String, value: Boolean) {
        getSharedPreferences().edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, def: Boolean): Boolean {
        return getSharedPreferences().getBoolean(key, def)
    }

    fun getString(key: String, def: String): String {
        return getSharedPreferences().getString(key, def)
    }

    fun setString(key: String, value: String) {
        getSharedPreferences().edit().putString(key, value).apply()
    }

    fun setString(key: String, value: String, MD5: Boolean) {
        setString(key, if (MD5) MD5Utils.getMessageDigest(value.toByteArray()) else value)
    }

    private fun getSharedPreferences(): SharedPreferences {
        if (sharedPreferences == null) {
            sharedPreferences = Wang.getInstance().context.getSharedPreferences("config", Context.MODE_PRIVATE)
        }
        return sharedPreferences as SharedPreferences
    }

    fun setInt(key: String, value: Int) {
        getSharedPreferences().edit().putInt(key, value).apply()
    }

    fun getInt(key: String, def: Int): Int {
        return getSharedPreferences().getInt(key, def)
    }

}
