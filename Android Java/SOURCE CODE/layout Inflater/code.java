    private void setImages(int position) {
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout mainLayout = findViewById(R.id.ll_design);
        mainLayout.removeAllViews();
        try {
            JSONArray images_ja = design_list.get(position).getGetdesignimage();
            String image_path = design_list.get(position).getImagepath();
            for (int i = 0; i < images_ja.length(); i++) {
                String images = images_ja.getJSONObject(i).getString("images");

                View myLayout = inflater.inflate(R.layout.design_layout, mainLayout, false);
                ImageView iv_design_image = myLayout.findViewById(R.id.iv_design_image);

                Glide.with(this)
                        .load(image_path + "/" + images)
                        .into(iv_design_image);

                // add our custom layout to the main layout
                mainLayout.addView(myLayout);
            }
        } catch (Exception e) {
            Alert.showError(this, e.getMessage());
        }

    }
