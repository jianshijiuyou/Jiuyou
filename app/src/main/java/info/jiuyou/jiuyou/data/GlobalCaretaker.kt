package info.jiuyou.jiuyou.data

import android.text.TextUtils
import com.google.gson.Gson
import info.jiuyou.wang.utils.SPUtils

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
 * 创建日期 ：2017/6/9 0009  10:42
 * <br></br>
 * 描   述 ：
 * <br></br>
 * 修订历史 ：
 *
 * ==========================================
 */
class GlobalCaretaker {

    companion object {
        private var instance = GlobalCaretaker()
        fun instance() = instance
    }

    fun saveSettings(sm: SettingsMemento?) {
        SPUtils.setVal("settings", Gson().toJson(sm))
    }

    fun getSettings(): SettingsMemento {
        val str = SPUtils.getVal("settings", "")
        if (TextUtils.isEmpty(str)) {
            return SettingsMemento().setThemeText("白天").setTheme(Theme.DEFAULT)
        } else {
            return Gson().fromJson(str, SettingsMemento::class.java)
        }
    }
}
