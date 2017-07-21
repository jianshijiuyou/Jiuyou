package info.jiuyou.jiuyou.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import info.jiuyou.wang.utils.DisplayUtils

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
 * 创建日期 ：2017/5/27 0027  16:38
 * <br></br>
 * 描   述 ：
 * <br></br>
 * 修订历史 ：
 *
 * ==========================================
 */
class WeiboNinePhotoLayout(context: Context, attrs: AttributeSet, defStyleAttr: Int) : ViewGroup(context, attrs, defStyleAttr) {

    private var childWidth: Int = 0
    private var space: Int = 0

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    init {
        space = Math.round(DisplayUtils.dpToPx(context, 4f))

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val newHeightSize: Int
        when (childCount) {
            0 -> {
                setMeasuredDimension(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY))
                return
            }
            1, 2, 3 -> newHeightSize = widthSize / 3
            4, 5, 6 -> newHeightSize = widthSize / 3 * 2
            else -> newHeightSize = widthSize
        }
        childWidth = (widthSize - space * 2) / 3
        val childMeasureSpec = View.MeasureSpec.makeMeasureSpec(childWidth, View.MeasureSpec.EXACTLY)
        measureChildren(childMeasureSpec, childMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(newHeightSize, View.MeasureSpec.EXACTLY))

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        when (childCount) {
            1 -> oneLayout()
            4 -> fourLayout()
            else -> normalLayout()
        }
    }


    private fun normalLayout() {
        aLayout {
            val cx = it % 3
            val cy = Math.ceil((it + 1) / 3.0).toInt() - 1
            Pair(cx, cy)
        }
    }

    private fun fourLayout() {
        aLayout {
            if (it < 2) {
                val cx = it % 3
                val cy = Math.ceil((it + 1) / 3.0).toInt() - 1
                Pair(cx, cy)
            } else {
                val cx = (it + 1) % 3
                val cy = Math.ceil(((it + 1) + 1) / 3.0).toInt() - 1
                Pair(cx, cy)
            }
        }
    }

    private fun oneLayout() {
        val view = getChildAt(0)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }


    private fun aLayout(cxy: (Int) -> Pair<Int, Int>) {
        val childCount = childCount
        for (i in 0..childCount - 1) {
            val child = getChildAt(i)
            val xy = cxy(i)
            val cx = xy.first
            val cy = xy.second
            val cs = childWidth + space
            child.layout(cs * cx, cs * cy, cs * cx + childWidth, cs * cy + childWidth)
        }
    }
}
