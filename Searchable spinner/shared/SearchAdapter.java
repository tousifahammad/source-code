package com.app.baseproject.shared;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.baseproject.R;
import com.app.baseproject.home.HomeActivity;
import com.app.baseproject.utils.Alert;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.NotificationViewHolder> {
    SearchFragment searchFragment;
    ArrayList<Search> list;

    public SearchAdapter(SearchFragment searchFragment, ArrayList<Search> list) {
        this.searchFragment = searchFragment;
        this.list = list;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_search, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationViewHolder viewHolder, int position) {
        Search search = list.get(position);
        viewHolder.tv_name.setText(search.getName());
        viewHolder.tv_location.setText(search.getLocation());

        if (!search.getImage().isEmpty()) {
            viewHolder.iv_image.setVisibility(View.VISIBLE);
            Glide.with(searchFragment.getActivity())
                    .load(search.getImage())
                    .override(80, 80) // resizing
                    .into(viewHolder.iv_image);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_location;
        ImageView iv_image;


        private NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_location = itemView.findViewById(R.id.tv_location);
            iv_image = itemView.findViewById(R.id.iv_image);

            itemView.setOnClickListener(v -> {
                try {
                    searchFragment.searchEventListener.searchEvent(list.get(getLayoutPosition()).getName());
                    searchFragment.closeFragment();
                } catch (Exception e) {
                    Toast.makeText(searchFragment.getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}
