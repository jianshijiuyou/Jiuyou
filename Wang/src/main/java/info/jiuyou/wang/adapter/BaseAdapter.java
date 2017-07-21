package info.jiuyou.wang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/4/19 0019  14:30
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
public abstract class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {
    protected Context mContext;

    public BaseAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        protected View findViewById(int id) {
            return itemView.findViewById(id);
        }
    }
}
