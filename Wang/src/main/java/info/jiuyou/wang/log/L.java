package info.jiuyou.wang.log;

import android.util.Log;

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/4/19 0019  9:24
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
public class L {
    private static final String WARNING = "warningW";
    private static final String DEBUG = "debugW";
    private static boolean tag=true;
    public static void w(String msg) {
        Log.w(WARNING, msg);
    }

    public static void d(Object msg) {
        if(!tag){
            return;
        }
        Log.d(DEBUG, ">======wang========"+msg+"=============<");
    }

    public static void d(Object des,Object msg) {
        if(!tag){
            return;
        }
        Log.d(DEBUG, ">======"+des+"========"+msg+"=============<");
    }
}
