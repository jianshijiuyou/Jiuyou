package info.jiuyou.wang.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

import skin.support.app.SkinCompatActivity;

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/4/19 0019  9:21
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
public abstract class BaseActivity extends SkinCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentViewId() == 0) {
            throw new Resources.NotFoundException("请传入正确的布局资源id");
        }
        setContentView(getContentViewId());
        initView(savedInstanceState);
        initEvent();
        initData(savedInstanceState);
    }


    protected abstract int getContentViewId();

    protected abstract void initView(Bundle savedInstanceState);

    protected void initEvent() {
    }

    protected void initData(Bundle savedInstanceState) {
    }

}

