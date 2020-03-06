package com.app.havejee.home.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.havejee.R;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private ArrayList<HomeBanner> banner_list;

    public SliderAdapterExample(Context context, ArrayList<HomeBanner> banner_list) {
        this.context = context;
        this.banner_list = banner_list;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_banner, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        try {
            viewHolder.tv_banner_title.setText(banner_list.get(position).getBanner_title());

            Glide.with(viewHolder.itemView)
                    .load(banner_list.get(position).getImagepath())
                    .into(viewHolder.iv_banner_image);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return banner_list.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView iv_banner_image;
        TextView tv_banner_title;

        SliderAdapterVH(View itemView) {
            super(itemView);
            iv_banner_image = itemView.findViewById(R.id.iv_banner_image);
            tv_banner_title = itemView.findViewById(R.id.tv_banner_title);
            this.itemView = itemView;
        }
    }
}
