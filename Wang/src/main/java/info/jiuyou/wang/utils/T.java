package info.jiuyou.wang.utils;

import android.widget.Toast;

import info.jiuyou.wang.Wang;

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/4/19 0019  16:46
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
public class T {
    private static Toast mToast;
    public static void Toast(String msg){
        if (mToast == null) {
            mToast = Toast.makeText(Wang.getInstance().getContext(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
