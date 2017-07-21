package info.jiuyou.jiuyou.net;

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/4/24 0024  16:40
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
public class ApiException extends RuntimeException {
    private int code = -1;

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    public ApiException(int code, String detailMessage) {
        super(detailMessage);
        this.code = code;
    }

    public int code() {
        return code;
    }

    public static final String MSG_NETWORK_ERROR = "网络不稳定，请稍后重试";
    public static final String WEIBO_SDK_ERROR = "weibo_sdk_error";
}
