package info.jiuyou.jiuyou.bean.weibo

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/5/25 0025  15:37
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
data class StatusesResultBase(
        val statuses: List<Status>,
        val since_id: String,
        val max_id: String
)

data class Status(
        val created_at:String, //微博创建时间
        val idstr:String, //微博ID
        val mid:String, //微博MID
        val text:String, //微博信息内容
        val source:String, //微博来源
        val pic_urls:List<pic>, //图片列表
        val user:User,
        val retweeted_status:Status,
        val reposts_count:Int, //转发数
        val comments_count:Int,  //评论数
        val attitudes_count:Int, //表态数
        val original_pic:String, //原始图片地址
        val bmiddle_pic:String, //中等尺寸图片地址
        val thumbnail_pic:String //缩略图片地址
)

data class pic(val thumbnail_pic:String)

data class User(
        val idstr:String,
        val screen_name:String,
        val name:String,
        val location:String,
        val description:String,
        val profile_image_url:String,
        val gender:String,
        val followers_count:String,
        val friends_count:String,
        val statuses_count:String,
        val favourites_count:String,
        val created_at:String
)