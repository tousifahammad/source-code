package com.app.theshineindia.AppList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.theshineindia.R;

import java.util.List;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.MyViewHolder> {
    List<App> list;
    Context context1;

    public AppListAdapter(List<App> list, Context context) {
        this.list = list;
        context1 = context;
    }

    @NonNull
    @Override
    public AppListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_app_list, viewGroup, false);
        return new MyViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textView_App_Name, textView_App_Package_Name, tv_status;

        MyViewHolder(View view) {
            super(view);

            cardView = view.findViewById(R.id.card_view);
            imageView = view.findViewById(R.id.imageview);
            textView_App_Name = view.findViewById(R.id.Apk_Name);
            textView_App_Package_Name = view.findViewById(R.id.Apk_Package_Name);
            tv_status = view.findViewById(R.id.tv_status);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AppListAdapter.MyViewHolder viewHolder, int i) {
        try {
            final App app = list.get(i);

            viewHolder.textView_App_Name.setText(app.getName());
            viewHolder.textView_App_Package_Name.setText(app.getPackage_name());
            viewHolder.tv_status.setText(app.getStatus());
            viewHolder.imageView.setImageDrawable(app.getIcon());

            //Adding click listener on CardView to open clicked application directly from here .
            viewHolder.cardView.setOnClickListener(view -> {

                Intent intent = context1.getPackageManager().getLaunchIntentForPackage(app.getPackage_name());
                if (intent != null) {
                    context1.startActivity(intent);
                } else {
                    Toast.makeText(context1, app.getName() + " Error, Please Try Again.", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
