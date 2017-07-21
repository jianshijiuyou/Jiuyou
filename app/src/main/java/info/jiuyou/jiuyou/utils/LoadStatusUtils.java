package info.jiuyou.jiuyou.utils;

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/6/23 0023  10:40
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
public class LoadStatusUtils {
    private static final int STATUE_MORE=1;
    private static final int STATUE_REFRESH=2;
    private static final int STATUE_NONE=3;

    private int status;


    public LoadStatusUtils() {
        this.status = STATUE_NONE;
    }

    public void more(){
        this.status=STATUE_MORE;
    }

    public void refresh(){
        this.status=STATUE_REFRESH;
    }

    public void none(){
        this.status = STATUE_NONE;
    }

    public boolean isMore(){
        return status==STATUE_MORE;
    }

    public boolean isRefresh(){
        return status==STATUE_REFRESH;
    }
    public boolean isNone(){
        return status==STATUE_NONE;
    }
}
