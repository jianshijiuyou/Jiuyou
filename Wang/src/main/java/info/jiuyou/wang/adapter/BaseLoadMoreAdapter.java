package info.jiuyou.wang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import info.jiuyou.wang.R;
import info.jiuyou.wang.log.L;

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/4/19 0019  14:31
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
public abstract class BaseLoadMoreAdapter<T> extends BaseAdapter {
    private RecyclerView recyclerView;
    protected List<T> dataList;
    private OnLoadMoreListener onLoadMoreListener;

    /**
     * 数据正在加载
     */
    public static final int LOADMORE_STATE_LOADING = 1;
    /**
     * 没有数据
     */
    public static final int LOADMORE_STATE_NO_DATA = 2;
    /**
     * 数据加载失败
     */
    public static final int LOADMORE_STATE_ERROR = 3;
    public static final int ITEM_TYPE_MORE = 11;
    public static final int ITEM_TYPE_DEFAULT = 12;

    public BaseLoadMoreAdapter(Context mContext, List<T> dataList) {
        super(mContext);

        this.dataList = dataList;

    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return ITEM_TYPE_MORE;
        }
        return ITEM_TYPE_DEFAULT;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_MORE) {
            return baseLoadMoreVH(parent);
        }
        return baseCreateViewHolder(parent, viewType);
    }

    protected abstract BaseViewHolder baseCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_DEFAULT:
                baseBindViewHolder(holder, position);
                break;
            case ITEM_TYPE_MORE:
                if (holder instanceof AbstractLoadMoreViewHolder) {

                    AbstractLoadMoreViewHolder loadMoreViewHolder = (AbstractLoadMoreViewHolder) holder;
                    if (onLoadMoreListener != null && AbstractLoadMoreViewHolder.isRefresh()) {
                        L.d("item_type_more");
                        switch (onLoadMoreListener.onLoadMore()) {
                            case LOADMORE_STATE_LOADING:
                                loadMoreViewHolder.onLoading();
                                break;
                            case LOADMORE_STATE_NO_DATA:
                                loadMoreViewHolder.onNoData();
                                break;
                        }

                    } else {
                        loadMoreViewHolder.onNoData();
                    }
                }
                break;
        }
    }

    protected abstract void baseBindViewHolder(BaseViewHolder holder, final int position);


    protected BaseViewHolder baseLoadMoreVH(ViewGroup parent) {
        return new BaseLoadMoreViewHolder(LayoutInflater.from(mContext).inflate(R.layout.viewholder_base, parent, false));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    public void complete() {
        AbstractLoadMoreViewHolder.complete();
    }

    public void loadError(ErrorHandle e) {
//        if (recyclerView != null) {
//            L.d("loadError");
//        }
        //iLoadMore.error(e);
    }


    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    public interface OnLoadMoreListener {
        int onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    private class BaseLoadMoreViewHolder extends AbstractLoadMoreViewHolder {

        public TextView tvTitle;
        public ProgressBar pb;
        public View item;

        public BaseLoadMoreViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) findViewById(R.id.tvTitle);
            pb = (ProgressBar) findViewById(R.id.pb);
            item = findViewById(R.id.item);
        }

        @Override
        public void onLoading() {
            tvTitle.setText(R.string.viewholder_base_load_text);
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        public void onNoData() {
            tvTitle.setText("到底啦");
            pb.setVisibility(View.GONE);
        }

        @Override
        public void onError(ErrorHandle e) {
            e.handle();
        }
    }

    public interface ErrorHandle {
        void handle();
    }


    abstract public static class AbstractLoadMoreViewHolder extends BaseAdapter.BaseViewHolder {
        private static boolean _isRefresh;

        public AbstractLoadMoreViewHolder(View itemView) {
            super(itemView);
            _isRefresh = true;
        }

        public static boolean isRefresh() {
            if (_isRefresh) {
                _isRefresh = false;
                return true;
            } else {
                return false;
            }
        }

        public static void complete() {
            _isRefresh = true;
        }

        protected abstract void onLoading();

        protected abstract void onNoData();

        protected abstract void onError(ErrorHandle e);
    }
}
