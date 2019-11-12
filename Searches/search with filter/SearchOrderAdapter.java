package com.app.cubeapparels.search;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.cubeapparels.R;
import com.app.cubeapparels.orderlist.Order;
import com.app.cubeapparels.utils.Alert;
import com.app.cubeapparels.utils.Animator;
import com.app.cubeapparels.utils.AppData;

public class SearchOrderAdapter extends RecyclerView.Adapter<SearchOrderAdapter.MyViewHolder> {
    private SearchActivity activity;

    SearchOrderAdapter(SearchActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_order_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, int position) {
        try {
            Order saloon = activity.saloon_list.get(position);
            viewHolder.tv_date.setText(saloon.getDelivery_date());
            viewHolder.tv_status.setText(saloon.getStatus());
            viewHolder.tv_remarks.setText(saloon.getRemarks());
            viewHolder.tv_quantity.setText(saloon.getQuantity());

            if (activity.type.equals(AppData.SearchType.customersearch)) {
                viewHolder.tv_text_design_no.setText("Design No.");
                viewHolder.tv_company_name.setText(saloon.getCompanyname());
                viewHolder.tv_design_no.setText(saloon.getDesingno());

            } else if (activity.type.equals(AppData.SearchType.designsearch)) {
                viewHolder.tv_text_design_no.setText("Customer");
                viewHolder.tv_company_name.setText(saloon.getDesingno());
                viewHolder.tv_design_no.setText(saloon.getCompanyname());
            }

        } catch (Exception e) {
            Alert.showError(activity, e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return activity.saloon_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date, tv_company_name, tv_status, tv_design_no, tv_quantity, tv_remarks, tv_text_design_no;


        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_company_name = itemView.findViewById(R.id.tv_company_name);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_design_no = itemView.findViewById(R.id.tv_design_no);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            tv_remarks = itemView.findViewById(R.id.tv_remarks);
            tv_text_design_no = itemView.findViewById(R.id.tv_text_design_no);


            itemView.setOnClickListener(v -> {
                Animator.buttonAnim(activity, itemView);
            });
        }

    }

}
