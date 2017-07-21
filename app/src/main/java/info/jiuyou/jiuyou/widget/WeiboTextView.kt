package info.jiuyou.jiuyou.widget

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import info.jiuyou.greendao.gen.EmotionsDao
import info.jiuyou.jiuyou.App
import info.jiuyou.jiuyou.R
import info.jiuyou.wang.widget.UrlImageSpan
import skin.support.widget.SkinCompatTextView
import java.util.regex.Pattern


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
 * 创建日期 ：2017/6/2 0002  10:25
 * <br></br>
 * 描   述 ：
 * <br></br>
 * 修订历史 ：
 *
 * ==========================================
 */
class WeiboTextView : SkinCompatTextView {


    companion object {
        val TYPE_TOPIC = 1
        val TYPE_USER = 2
        val TYPE_LINK = 3
        //private val items = LruCache<String, SpannableString>(30)
    }

    private var textClickListener: WeiboTextClickListener? = null

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initEvent()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initEvent()
    }

    private fun initEvent() {
        setOnTouchListener(ClickableMovementMethod.getInstance())
    }


    fun setContentText(text: CharSequence) {

        //val spannableString = items.get(id)
        //if (spannableString != null) {

        //    movementMethod = LinkMovementMethod.getInstance()
        //   setText(spannableString)
        //    return
        // }

        //doAsync {
        //1、替换链接文字
        var pattern = Pattern.compile("((ht|f)tp(s?):\\/\\/|www\\.)(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)")
        var matcher = pattern.matcher(text)
        val urls = mutableListOf<String>()
        val indexs = mutableListOf<Int>()
        var sum = 0
        while (matcher.find()) {
            urls.add(matcher.group())
            indexs.add(matcher.start() - sum)
            sum += (matcher.group().length - 2)
        }
        val newText = matcher.replaceAll("链接")
        val sbs = SpannableString(newText)
        //链接
        urls.forEachIndexed { index, s ->
            sbs.setSpan(WeiboTextClickableSpan(s, TYPE_LINK, textClickListener), indexs[index], indexs[index] + 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        }

        //2、话题      #xxx#
        pattern = Pattern.compile("#.+?#")
        matcher = pattern.matcher(newText)
        while (matcher.find()) {
            sbs.setSpan(WeiboTextClickableSpan(matcher.group(), TYPE_TOPIC, textClickListener), matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        }
        //3、用户名     @xxx
        pattern = Pattern.compile("@[^@]+?(?=[\\s:：(),.。])")
        matcher = pattern.matcher(newText)
        while (matcher.find()) {
            sbs.setSpan(WeiboTextClickableSpan(matcher.group(), TYPE_USER, textClickListener), matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        }

        //4、表情
        pattern = Pattern.compile("\\[.+?\\]")
        matcher = pattern.matcher(newText)
        while (matcher.find()) {
            val e = matcher.group()
            val emotion = App.instance().daoSession?.emotionsDao
                    ?.queryBuilder()
                    ?.where(EmotionsDao.Properties.Phrase.eq(e))
                    ?.list()
                    ?.firstOrNull()
            if (emotion != null) {
                sbs.setSpan(UrlImageSpan(context, emotion.url, textSize, this@WeiboTextView), matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            }
        }

        //  uiThread {
        //movementMethod = ClickableMovementMethod.getInstance()
        //items.put(id, sbs)
        setText(sbs)
        // }
        //}

    }

    fun setTextClickListener(listener: WeiboTextClickListener) {
        this.textClickListener = listener
    }


    //点击事件类型
    //1、话题      #xxx#
    //2、用户名     @xxx
    //3、链接
    inner class WeiboTextClickableSpan(val text: String, val type: Int, val listener: WeiboTextClickListener?) : ClickableSpan() {

        override fun onClick(widget: View) {
            when (type) {
                TYPE_TOPIC -> listener?.topic(text)
                TYPE_USER -> listener?.user(text)
                TYPE_LINK -> listener?.link(text)
            }
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = resources.getColor(R.color.weibo_author_header_background)
        }
    }
}


interface WeiboTextClickListener {
    fun topic(text: String)
    fun user(text: String)
    fun link(text: String)
}