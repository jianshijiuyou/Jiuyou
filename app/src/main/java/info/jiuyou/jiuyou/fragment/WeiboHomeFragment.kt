package info.jiuyou.jiuyou.fragment

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.PtrHandler
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import info.jiuyou.jiuyou.App
import info.jiuyou.jiuyou.R
import info.jiuyou.jiuyou.adapter.WeiboHomeAdapter
import info.jiuyou.jiuyou.bean.weibo.StatusesResultBase
import info.jiuyou.jiuyou.mvp.presenter.WeiboPresenter
import info.jiuyou.jiuyou.mvp.presenter.impl.WeiboPresenterImpl
import info.jiuyou.jiuyou.mvp.view.WeiboView
import info.jiuyou.jiuyou.net.ApiException
import info.jiuyou.jiuyou.utils.LoadStatusUtils
import info.jiuyou.wang.log.L
import info.jiuyou.wang.utils.T
import kotlinx.android.synthetic.main.fragment_weibo_home.*
import org.jetbrains.anko.toast


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
 * 创建日期 ：2017/5/25 0025  9:57
 * <br></br>
 * 描   述 ：
 * <br></br>
 * 修订历史 ：
 *
 * ==========================================
 */
class WeiboHomeFragment : AbstractFragment(), WeiboView {
    companion object {
        val FTag: String = "WeiboHomeFragment"
    }

    var adapter: WeiboHomeAdapter? = null
    private var presenter: WeiboPresenter? = null

    var maxId = "0"
    val LoadStatus = LoadStatusUtils()
    override fun getLayoutId(): Int = R.layout.fragment_weibo_home

    override fun initView(savedInstanceState: Bundle?) {

        rvContent.layoutManager = LinearLayoutManager(context)
        initRefreshLayout()

    }

    override fun initData() {
        super.initData()

        presenter = WeiboPresenterImpl(this)
        presenter?.webStatuses(token = App.instance().weiboUser.token)
        presenter?.requestEmotions(App.instance().weiboUser.token)
    }


    private fun initRefreshLayout() {
        ptrframe.setPtrHandler(object : PtrHandler {
            override fun checkCanDoRefresh(frame: PtrFrameLayout?, content: View?, header: View?): Boolean = PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header)

            override fun onRefreshBegin(frame: PtrFrameLayout?) {
                if (adapter?.data?.size!! > 0 && LoadStatus.isNone) {
                    LoadStatus.refresh()
                    presenter?.webStatuses(token = App.instance().weiboUser.token)
                }
            }

        })

//        refreshLayout.setOnRefreshListener(object : RefreshListenerAdapter() {
//            override fun onRefresh(refreshLayout: TwinklingRefreshLayout) {
//                if (statuses.size > 0) {
//                    presenter?.webStatuses(token = App.instance().weiboUser.token, since_id = statuses[0].idstr)
//                }
//            }
//
//            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout) {
//
//                presenter?.webStatuses(token = App.instance().weiboUser.token, max_id = maxId)
//            }
//        })
    }


    override fun error(e: ApiException) {
        ptrframe.refreshComplete()
        if(LoadStatus.isMore){
            LoadStatus.none()
            adapter?.loadMoreFail()
        }
        context.toast(e.message as CharSequence)
    }

    override fun webStatusesResult(data: StatusesResultBase) {
        L.d("共获取了" + data.statuses.size + "条微博")
        ptrframe.refreshComplete()
        if (data.statuses == null || data.statuses.size <= 0) {
            T.Toast("已经没有微博可以看了")
            adapter?.loadMoreEnd()
            LoadStatus.none()
            return
        }

        if (adapter == null) {
            maxId = data.max_id
            adapter = WeiboHomeAdapter(context, data.statuses)
            adapter?.setOnLoadMoreListener({
                if (LoadStatus.isNone) {
                    LoadStatus.more()
                    presenter?.webStatuses(token = App.instance().weiboUser.token, max_id = maxId)
                }
            }, rvContent)
            rvContent.adapter = adapter
        } else {
            if (LoadStatus.isMore) {
                maxId = data.max_id
                adapter?.addData(data.statuses)
                adapter?.loadMoreComplete()

            }
            if (LoadStatus.isRefresh) {
                adapter?.setNewData(data.statuses)
            }
            LoadStatus.none()
        }

    }


    override fun showProgress() {

    }

    override fun hideProgress() {

    }

}
