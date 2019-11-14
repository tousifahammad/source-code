package com.app.haircutuser.mybooking.fragment.upcoming;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.app.haircutuser.R;
import com.app.haircutuser.mybooking.Booking;
import com.app.haircutuser.mybooking.BookingClickListener;
import com.app.haircutuser.utils.AppData;

import java.util.ArrayList;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.MyViewHolder> {
    private ArrayList<Booking> list;
    private Context context;
    private BookingClickListener clickListener;


    public UpcomingAdapter(ArrayList<Booking> list, Context context, BookingClickListener clickListener) {
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_upcoming_booking, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, int position) {
        Booking booking = list.get(position);
        viewHolder.tv_id.setText("ID:" + booking.getBooking_id());
        viewHolder.tv_date_time.setText(booking.getBooking_date() + " | " + booking.getBooking_time());

        viewHolder.tv_name.setText(booking.getName());
        viewHolder.tv_saloon.setText(booking.getSaloonname());
        viewHolder.tv_service.setText(booking.getServicename());
        viewHolder.tv_address.setText(booking.getLocation());
        viewHolder.tv_price.setText("₹ " + booking.getPrice());
        viewHolder.tv_status.setText("Status : " + booking.getStatus());

        if (booking.getIs_reminder() != null && booking.getIs_reminder().equals("Y")) {
            viewHolder.switch_remainder.setChecked(true);
        }

        viewHolder.btn_modify.setOnClickListener(v -> {
            requestConfirmation("Modify",
                    booking.getBooking_id(),
                    booking.getVendor_id());
        });

        viewHolder.btn_cancel.setOnClickListener(v -> {
            requestConfirmation("Cancel",
                    booking.getBooking_id(),
                    booking.getVendor_id());
        });

        viewHolder.switch_remainder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            clickListener.onRemainderChanged(isChecked, booking.getBooking_id());
        });
    }

    private void requestConfirmation(String type, String booking_id, String vendor_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmation !!!")
                .setMessage("Are you sure, you want to " + type + "?")
                .setCancelable(false);
        builder.setPositiveButton(type + " Booking", (dialog, which) -> {
            if (clickListener != null) {
                if (type.equals("Modify")) {
                    AppData.service.vendor_id = vendor_id;
                    clickListener.onModifyClicked(booking_id);
                } else {
                    clickListener.onCancelClicked(booking_id);
                }
            }
        });
        builder.setNegativeButton("Not Now", (dialog, which) -> dialog.dismiss());
        builder.create();
        builder.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id, tv_date_time, tv_name, tv_saloon, tv_service, tv_address, tv_price, tv_status;
        Button btn_modify, btn_cancel;
        ImageView iv_address_image;
        Switch switch_remainder;


        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_date_time = itemView.findViewById(R.id.tv_date_time);
            tv_saloon = itemView.findViewById(R.id.tv_saloon);
            tv_service = itemView.findViewById(R.id.tv_service);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_price = itemView.findViewById(R.id.tv_price);
            btn_modify = itemView.findViewById(R.id.btn_modify);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            iv_address_image = itemView.findViewById(R.id.iv_address_image);
            switch_remainder = itemView.findViewById(R.id.switch_remainder);
            tv_status = itemView.findViewById(R.id.tv_status);
        }

    }

}
