package com.app.haircutuser.mybooking.fragment.past;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.haircutuser.R;
import com.app.haircutuser.mybooking.Booking;
import com.app.haircutuser.review.ReviewActivity;

import java.util.ArrayList;

public class PastAdapter extends RecyclerView.Adapter<PastAdapter.NotificationViewHolder> {
    ArrayList<Booking> list;

    public PastAdapter(ArrayList<Booking> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_past_booking, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationViewHolder viewHolder, int position) {
        Booking booking = list.get(position);
        viewHolder.tv_id.setText("ID:" + booking.getBooking_id());
        viewHolder.tv_date_time.setText(booking.getBooking_date() + " | " + booking.getBooking_time());
        viewHolder.tv_name.setText(booking.getName());
        viewHolder.tv_saloon.setText(booking.getSaloonname());
        viewHolder.tv_service.setText(booking.getServicename());
        viewHolder.tv_address.setText(booking.getLocation());
        viewHolder.tv_price.setText("₹ " + booking.getPrice());
        viewHolder.tv_status.setText("Status : " + booking.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id, tv_date_time, tv_name, tv_saloon, tv_service, tv_address, tv_price, tv_review, tv_status;
        ImageView iv_address_image;


        private NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_date_time = itemView.findViewById(R.id.tv_date_time);
            tv_saloon = itemView.findViewById(R.id.tv_saloon);
            tv_service = itemView.findViewById(R.id.tv_service);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_price = itemView.findViewById(R.id.tv_price);
            iv_address_image = itemView.findViewById(R.id.iv_address_image);
            tv_review = itemView.findViewById(R.id.tv_review);
            tv_status = itemView.findViewById(R.id.tv_status);

            tv_review.setOnClickListener(v -> {
                Intent intent = new Intent(tv_review.getContext(), ReviewActivity.class);
                intent.putExtra("vendor_id", list.get(getLayoutPosition()).getVendor_id());
                intent.putExtra("booking_id", list.get(getLayoutPosition()).getBooking_id());
                tv_review.getContext().startActivity(intent);
            });

        }

    }

}
