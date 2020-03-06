package com.app.cubeapparels.search;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.cubeapparels.R;
import com.app.cubeapparels.allocate_order.AllocateOrderActivity;
import com.app.cubeapparels.orderallocation.allocated.AllocatedOrder;
import com.app.cubeapparels.utils.Alert;
import com.app.cubeapparels.utils.Animator;
import com.app.cubeapparels.utils.AppData;

public class SearchAllocationAdapter extends RecyclerView.Adapter<SearchAllocationAdapter.MyViewHolder> {
    private SearchActivity activity;

    public SearchAllocationAdapter(SearchActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_allocated_order, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, int position) {
        try {
            AllocatedOrder order = activity.allocated_order__list.get(position);

            viewHolder.tv_challan_no.setText("Challan Number : " + order.getChallan_no());
            viewHolder.tv_given_date.setText(order.getGiven_date());
            viewHolder.tv_given_qty.setText(order.getQuantity());
            viewHolder.tv_receiving_date.setText(order.getReturn_date());
            viewHolder.tv_remarks.setText(order.getRemarks());

            if (activity.type.equals(AppData.SearchType.karigarsearch)) {
                viewHolder.tv_karigarname.setText("Karigar Name : " + order.getKarigarname());
                viewHolder.tv_desingno.setText("Design Number : " + order.getDesingno());

            } else if (activity.type.equals(AppData.SearchType.designsearch)) {
                viewHolder.tv_karigarname.setText("Design Number : " + order.getDesingno());
                viewHolder.tv_desingno.setText("Karigar Name : " + order.getKarigarname());
            }

        } catch (Exception e) {
            Alert.showError(activity, e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return activity.allocated_order__list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_karigarname, tv_challan_no, tv_desingno, tv_given_date, tv_given_qty, tv_receiving_date, tv_receiving_qty, tv_remarks;


        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_karigarname = itemView.findViewById(R.id.tv_karigarname);
            tv_challan_no = itemView.findViewById(R.id.tv_challan_no);
            tv_desingno = itemView.findViewById(R.id.tv_desingno);
            tv_given_date = itemView.findViewById(R.id.tv_given_date);
            tv_given_qty = itemView.findViewById(R.id.tv_given_qty);
            tv_receiving_date = itemView.findViewById(R.id.tv_receiving_date);
            tv_remarks = itemView.findViewById(R.id.tv_remarks);

            itemView.setOnClickListener(v -> {
                try {
                    Animator.buttonAnim(activity, itemView);

                    Intent intent = new Intent(activity, AllocateOrderActivity.class);
                    intent.putExtra("order", activity.allocated_order__list.get(getLayoutPosition()).getOrder());
                    activity.startActivity(intent);
                } catch (Exception e) {
                    Alert.showError(activity, e.getMessage());
                }
            });
        }

    }

}
