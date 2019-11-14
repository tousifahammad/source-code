package com.app.cubeapparels.master_entry.fragment.design;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cubeapparels.R;
import com.app.cubeapparels.addorder.Design;
import com.app.cubeapparels.master_entry.MasterEntryClickListener;
import com.bumptech.glide.Glide;

import org.json.JSONArray;

import java.util.ArrayList;

public class DesignAdapter extends RecyclerView.Adapter<DesignAdapter.MyViewHolder> {
    private ArrayList<Design> list;
    private FragmentActivity context;
    private MasterEntryClickListener clickListener;


    public DesignAdapter(ArrayList<Design> list, FragmentActivity context, MasterEntryClickListener clickListener) {
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, int position) {
        Design design = list.get(position);

        viewHolder.tv_design_no.setText("Design Number : " + design.getDesign_number());

        LayoutInflater inflater = context.getLayoutInflater();
        LinearLayout mainLayout = viewHolder.ll_design;

        try {
            String image_path = design.getImagepath();

            JSONArray images_ja = design.getGetdesignimage();
            for (int i = 0; i < images_ja.length(); i++) {
                String images = images_ja.getJSONObject(i).getString("images");

                View myLayout = inflater.inflate(R.layout.design_layout, viewHolder.ll_design, false);
                ImageView iv_design_image = myLayout.findViewById(R.id.iv_design_image);

                Glide.with(context)
                        .load(image_path + "/" + images)
                        .override(100, 100)
                        .into(iv_design_image);

                iv_design_image.setOnClickListener(v -> {

                });

                // add our custom layout to the main layout
                mainLayout.addView(myLayout);
            }

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_design_no, tv_edit;
        LinearLayout ll_design;


        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_design_no = itemView.findViewById(R.id.tv_design_no);
            tv_edit = itemView.findViewById(R.id.tv_edit);
            ll_design = itemView.findViewById(R.id.ll_design);
        }

    }

}
