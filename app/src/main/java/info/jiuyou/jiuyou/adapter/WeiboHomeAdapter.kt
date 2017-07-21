package info.jiuyou.jiuyou.adapter

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import info.jiuyou.jiuyou.App
import info.jiuyou.jiuyou.R
import info.jiuyou.jiuyou.activity.WebActivity
import info.jiuyou.jiuyou.bean.weibo.Status
import info.jiuyou.jiuyou.net.RF
import info.jiuyou.jiuyou.utils.WeiboUtils
import info.jiuyou.jiuyou.widget.WeiboNinePhotoLayout
import info.jiuyou.jiuyou.widget.WeiboTextClickListener
import info.jiuyou.jiuyou.widget.WeiboTextView
import info.jiuyou.wang.utils.T
import jp.wasabeef.glide.transformations.CropCircleTransformation
import org.jetbrains.anko.toast

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/5/25 0025  14:38
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
class WeiboHomeAdapter(ctx: Context, list: List<Status>) : BaseQuickAdapter<Status, BaseViewHolder>(R.layout.viewholder_weibo_home, list), WeiboTextClickListener {
    init {
        setOnItemClickListener { adapter, view, position ->
            val url = RF.weiboLink(data[position].idstr)
            startUrl(url)
        }
    }

    override fun convert(vh: BaseViewHolder, status: Status) {
        vh.getView<WeiboTextView>(R.id.tv_content).setTextClickListener(this)
        vh.getView<WeiboTextView>(R.id.tv_content).setContentText(status.text)
        vh.setText(R.id.tv_user_name, status.user.screen_name)
        vh.setText(R.id.tv_status_time, (WeiboUtils.convDate(status.created_at) + "  " + Html.fromHtml(status.source)))
        Glide.with(mContext).load(status.user.profile_image_url).bitmapTransform(CropCircleTransformation(mContext)).into(vh.getView(R.id.img_user_head))
        //添加九图
        vh.getView<WeiboNinePhotoLayout>(R.id.nine_photo_layout).removeAllViews()
        val pic_urls = status.pic_urls
        if (pic_urls != null && pic_urls.isNotEmpty()) {
            pic_urls.forEach {

                val imageView = ImageView(mContext)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP

                if (it.thumbnail_pic.endsWith("gif")) {
                    Glide.with(mContext).load(it.thumbnail_pic).asGif().into(imageView)
                } else {
                    Glide.with(mContext).load(it.thumbnail_pic).into(imageView)
                }

                vh.getView<WeiboNinePhotoLayout>(R.id.nine_photo_layout).addView(imageView)
            }
        }

        //转发微博
        if (status.retweeted_status != null) {
            vh.getView<View>(R.id.child_weibo).visibility = View.VISIBLE
            vh.getView<WeiboTextView>(R.id.child_tv_content).setTextClickListener(this)
            if (status.retweeted_status.user == null) {
                vh.getView<WeiboTextView>(R.id.child_tv_content).setContentText(status.retweeted_status.text)
            } else {
                vh.getView<WeiboTextView>(R.id.child_tv_content).setContentText("@" + status.retweeted_status.user.screen_name + ":" + status.retweeted_status.text)
            }
            //添加九图
            vh.getView<WeiboNinePhotoLayout>(R.id.child_nine_photo_layout).removeAllViews()
            val pic_urls = status.retweeted_status.pic_urls
            if (pic_urls != null && pic_urls.isNotEmpty()) {
                pic_urls.forEach {

                    val imageView = ImageView(mContext)
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP

                    if (it.thumbnail_pic.endsWith("gif")) {
                        Glide.with(mContext).load(it.thumbnail_pic).asGif().into(imageView)
                    } else {
                        Glide.with(mContext).load(it.thumbnail_pic).into(imageView)
                    }

                    vh.getView<WeiboNinePhotoLayout>(R.id.child_nine_photo_layout).addView(imageView)
                }
            }
        } else {
            vh.getView<View>(R.id.child_weibo).visibility = View.GONE
        }


        //转发,评论,点赞
        vh.setText(R.id.tv_zhuanfa, status.reposts_count.toString())
        (vh.getView<TextView>(R.id.tv_zhuanfa).parent as View).setOnClickListener {
            T.Toast("转发")
        }
        vh.setText(R.id.tv_pinglun, status.comments_count.toString())
        (vh.getView<TextView>(R.id.tv_pinglun).parent as View).setOnClickListener {
            T.Toast("评论")
        }
        vh.setText(R.id.tv_dianzan, status.attitudes_count.toString())
        (vh.getView<TextView>(R.id.tv_dianzan).parent as View).setOnClickListener {
            T.Toast("点赞")
        }
    }

    override fun topic(text: String) {
        mContext.toast("话题" + text)
    }

    override fun user(text: String) {
        mContext.toast("用户" + text)
    }

    override fun link(text: String) {
        startUrl(text)
    }

    private fun startUrl(url: String) {
        val intent = Intent(mContext, WebActivity::class.java)
        intent.putExtra("url", url)
        mContext.startActivity(intent)
    }
}