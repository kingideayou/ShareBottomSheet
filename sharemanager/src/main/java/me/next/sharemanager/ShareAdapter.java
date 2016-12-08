package me.next.sharemanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NeXT on 16/11/18.
 */

public class ShareAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<ShareActivityInfo> mShareActivityInfoList = new ArrayList<>();

    public ShareAdapter(Context context, List<ShareActivityInfo> shareActivityInfoList) {
        this.mContext = context;
        this.mShareActivityInfoList = shareActivityInfoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ShareItemViewHolder.newInstance(mContext, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ShareItemViewHolder)holder).onBindViewHolder(position, mShareActivityInfoList.get(position), mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mShareActivityInfoList.size();
    }

    public ItemClickListener mItemClickListener;

    public void setItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int pos);
    }

}

