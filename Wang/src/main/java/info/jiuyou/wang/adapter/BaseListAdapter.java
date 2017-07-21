package info.jiuyou.wang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/6/7 0007  11:19
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {
    protected Context mContext;
    protected List<T> dataList;

    public BaseListAdapter(Context mContext, List<T> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        protected View findViewById(int id) {
            return itemView.findViewById(id);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}