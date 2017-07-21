package info.jiuyou.jiuyou.utils

import android.content.Context
import android.text.format.DateUtils
import info.jiuyou.jiuyou.App
import info.jiuyou.jiuyou.R
import info.jiuyou.wang.utils.TimeUtils
import java.util.*

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
 * 创建日期 ：2017/6/5 0005  15:48
 * <br></br>
 * 描   述 ：
 * <br></br>
 * 修订历史 ：
 *
 * ==========================================
 */
object WeiboUtils {
    fun convDate(time: String): String {
        try {
            val context = App.instance().applicationContext
            val res = context.resources

            val buffer = StringBuffer()

            val createCal = Calendar.getInstance()

            if (time.length == 13) {
                try {
                    createCal.setTimeInMillis(java.lang.Long.parseLong(time))
                } catch (e: Exception) {
                    createCal.setTimeInMillis(Date.parse(time))
                }

            } else {
                createCal.setTimeInMillis(Date.parse(time))
            }

            val currentcal = Calendar.getInstance()
            currentcal.setTimeInMillis(System.currentTimeMillis())

            val diffTime = (currentcal.getTimeInMillis() - createCal.getTimeInMillis()) / 1000

            // 同一月
            if (currentcal.get(Calendar.MONTH) === createCal.get(Calendar.MONTH)) {
                // 同一天
                if (currentcal.get(Calendar.DAY_OF_MONTH) === createCal.get(Calendar.DAY_OF_MONTH)) {
                    if (diffTime < 3600 && diffTime >= 60) {
                        buffer.append(diffTime / 60).append(res.getString(R.string.msg_few_minutes_ago))
                    } else if (diffTime < 60) {
                        buffer.append(res.getString(R.string.msg_now))
                    } else {
                        buffer.append(res.getString(R.string.msg_today)).append(" ").append(TimeUtils.getTime(createCal.getTimeInMillis(), "HH:mm"))
                    }
                } else if (currentcal.get(Calendar.DAY_OF_MONTH) - createCal.get(Calendar.DAY_OF_MONTH) === 1) {
                    buffer.append(res.getString(R.string.msg_yesterday)).append(" ").append(TimeUtils.getTime(createCal.getTimeInMillis(), "HH:mm"))
                }// 前一天
            }

            if (buffer.isEmpty()) {
                buffer.append(TimeUtils.getTime(createCal.getTimeInMillis(), "MM-dd HH:mm"))
            }

            var timeStr = buffer.toString()
            if (currentcal.get(Calendar.YEAR) !== createCal.get(Calendar.YEAR)) {
                timeStr = createCal.get(Calendar.YEAR).toString() + " " + timeStr
            }

            return timeStr
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return time
    }
}
