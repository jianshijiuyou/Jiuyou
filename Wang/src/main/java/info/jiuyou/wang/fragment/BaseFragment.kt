package info.jiuyou.wang.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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
 * 创建日期 ：2017/5/25 0025  8:54
 * <br></br>
 * 描   述 ：
 * <br></br>
 * 修订历史 ：
 *
 * ==========================================
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(getLayoutId(), container, false)
    }
    abstract fun getLayoutId(): Int

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
        initData()
    }

    abstract fun initView(savedInstanceState: Bundle?)
    protected open fun initData() {}
}
