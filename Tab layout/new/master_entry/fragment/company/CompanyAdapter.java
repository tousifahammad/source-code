package com.app.cubeapparels.master_entry.fragment.company;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.app.cubeapparels.R;
import com.app.cubeapparels.addorder.Company;

import java.util.ArrayList;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.NotificationViewHolder> {
    ArrayList<Company> list;

    public CompanyAdapter(ArrayList<Company> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_company, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationViewHolder viewHolder, int position) {
        try {
            Company company = list.get(position);
            viewHolder.tv_name.setText(company.getCompany_name());
            viewHolder.tv_mobile.setText(company.getMobile_number());
            viewHolder.tv_address.setText(company.getAddress());
            viewHolder.tv_gst.setText(company.getGst_number());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView tv_mobile, tv_name, tv_gst, tv_address, tv_edit;


        private NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_mobile = itemView.findViewById(R.id.tv_mobile);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_gst = itemView.findViewById(R.id.tv_gst);
            tv_edit = itemView.findViewById(R.id.tv_edit);

            tv_edit.setOnClickListener(v -> {
//                Intent intent = new Intent(tv_review.getContext(), ReviewActivity.class);
//                intent.putExtra("vendor_id", list.get(getLayoutPosition()).getVendor_id());
//                intent.putExtra("booking_id", list.get(getLayoutPosition()).getBooking_id());
//                tv_review.getContext().startActivity(intent);
            });

        }

    }

}
