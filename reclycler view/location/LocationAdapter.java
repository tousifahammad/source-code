package com.app.JainFurnishing.location;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.JainFurnishing.R;
import com.app.JainFurnishing.utils.Const;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.NetworkPolicy;
//import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private ArrayList<Location> list;
    private ClickListener clickListener;


    public LocationAdapter(ArrayList<Location> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LocationViewHolder viewHolder, int position) {
        Location location = list.get(position);

        final String location_name = location.getLocation_name();
        final String location_image_url = location.getLocation_image();

        viewHolder.location_name.setText(location_name);
        if (!location.getLocation_id().equals(Const.other_material_id)) {
            Picasso.get().load(location_image_url)
                    .resize(100, 120).centerCrop()
                    .placeholder(R.drawable.customborder)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(viewHolder.location_image, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(location_image_url)
                                    .resize(100, 120)
                                    .centerCrop()
                                    .placeholder(R.drawable.customborder)
                                    .into(viewHolder.location_image);
                        }
                    });
        } else {
            Picasso.get().load(R.mipmap.other).resize(100, 120).centerCrop().into(viewHolder.location_image);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView location_name;
        ImageView location_image;

        private LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            location_name = itemView.findViewById(R.id.location_name);
            location_image = itemView.findViewById(R.id.location_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.itemClicked(view, getLayoutPosition());
            }
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}