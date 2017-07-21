package info.jiuyou.wang;

import android.content.Context;
import android.os.Bundle;

import java.util.HashMap;

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/4/19 0019  9:14
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
public class Wang {
    private static Wang wang = new Wang();
    private Context mContext;
    private HashMap<String,Object> globalData;

    private Wang(){
        globalData=new HashMap<>();
    }
    public static Wang getInstance() {

        return wang;
    }

    public static void init(Context context) {
        wang.mContext=context.getApplicationContext();
    }

    public Context getContext(){
        return mContext;
    }

    public void put(String key,Object val){
        globalData.put(key,val);
    }

    public Object get(String key){
        return globalData.get(key);
    }

}
