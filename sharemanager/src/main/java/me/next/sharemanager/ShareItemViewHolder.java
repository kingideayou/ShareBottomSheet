package me.next.sharemanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by NeXT on 16/11/18.
 */

public class ShareItemViewHolder extends RecyclerView.ViewHolder {

    private View itemView;
    private TextView tvName;
    private ImageView ivIcon;

    public static ShareItemViewHolder newInstance(Context context, ViewGroup parent) {
        return new ShareItemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.share_item, parent, false));
    }

    public ShareItemViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);

    }

    public void onBindViewHolder(final int position, final ShareActivityInfo shareActivityInfo, final ShareAdapter.ItemClickListener itemClickListener) {
        tvName.setText(shareActivityInfo.getAppName());
        ivIcon.setImageDrawable(shareActivityInfo.getAppIcon());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != itemClickListener) {
                    itemClickListener.onItemClick(position);
                }
            }
        });
    }

}
