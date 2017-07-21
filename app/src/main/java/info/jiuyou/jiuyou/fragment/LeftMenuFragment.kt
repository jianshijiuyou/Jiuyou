package info.jiuyou.jiuyou.fragment

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import info.jiuyou.jiuyou.R
import info.jiuyou.wang.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_left_menu.*
import kotlin.reflect.KMutableProperty0

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
 * 创建日期 ：2017/5/25 0025  8:49
 * <br></br>
 * 描   述 ：
 * <br></br>
 * 修订历史 ：
 *
 * ==========================================
 */
class LeftMenuFragment : AbstractFragment() {
    companion object{
        val FTag: String = "LeftMenuFragment"
    }
    override fun getLayoutId(): Int = R.layout.fragment_left_menu
    private val listData = listOf("首页", "设置")
    private var menuHandler: MenuHandler? = null
    override fun initView(savedInstanceState: Bundle?) {
        listView.adapter = initAdapter()

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            if (menuHandler != null) {
                when (position) {
                    0 -> menuHandler?.home()
                    1 -> menuHandler?.setting()
                }
            }

        }

    }

    private fun initAdapter(): ArrayAdapter<String> {
        return ArrayAdapter(context, R.layout.viewholder_left_menu,R.id.tv_title, listData)
    }

    fun setMenuHandler(mh: MenuHandler){
        menuHandler=mh
    }


    interface MenuHandler  {
        fun home()
        fun setting()
    }
}
