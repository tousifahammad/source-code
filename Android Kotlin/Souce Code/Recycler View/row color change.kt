int row_index = -1;

public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv1.setText(android_versionnames[position]);

        holder.row_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
            }
        });
        if(row_index==position){
            holder.row_linearlayout.setBackgroundColor(Color.parseColor("#567845"));
            holder.tv1.setTextColor(Color.parseColor("#ffffff"));
        }
        else
        {
            holder.row_linearlayout.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.tv1.setTextColor(Color.parseColor("#000000"));
        }

    }
