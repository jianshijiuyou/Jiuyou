package info.jiuyou.jiuyou.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import info.jiuyou.jiuyou.R
import info.jiuyou.jiuyou.data.GlobalCaretaker
import info.jiuyou.jiuyou.data.SettingsMemento
import info.jiuyou.jiuyou.data.Theme
import kotlinx.android.synthetic.main.fragment_setting.*
import skin.support.SkinCompatManager

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/6/8 0008  14:08
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
class SettingFragment : AbstractFragment() {

    companion object {
        val FTag: String = "SettingFragment"
    }

    var sm: SettingsMemento? = null


    override fun getLayoutId(): Int = R.layout.fragment_setting

    override fun initView(savedInstanceState: Bundle?) {

        sm = GlobalCaretaker.instance().getSettings()

        tvTheme.text = sm?.themeText

        itemTheme.setOnClickListener {
            AlertDialog
                    .Builder(context)
                    .setItems(arrayOf("白天", "黑夜"), DialogInterface.OnClickListener { dialog, which ->
                        when (which) {
                            0 -> {
                                tvTheme.text = "白天"
                                SkinCompatManager.getInstance().restoreDefaultTheme()
                                sm?.themeText = "白天"
                                sm?.theme = Theme.DEFAULT
                            }
                            1 -> {
                                tvTheme.text = "黑夜"
                                SkinCompatManager.getInstance().loadSkin(Theme.NIGHT, null)
                                sm?.themeText = "黑夜"
                                sm?.theme = Theme.NIGHT
                            }
                        }
                    })
                    .create()
                    .show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()

        GlobalCaretaker.instance().saveSettings(sm)
    }
}