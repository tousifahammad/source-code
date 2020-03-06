package com.app.havejee.home.banner;

public class HomeBanner {
    private String id;
    private String banner_type;
    private String main_category_id;
    private String category_id;
    private String sub_category_id;
    private String banner_title;
    private String imagepath;

    public HomeBanner(String id, String banner_type, String main_category_id, String category_id, String sub_category_id, String banner_title, String imagepath) {
        this.id = id;
        this.banner_type = banner_type;
        this.main_category_id = main_category_id;
        this.category_id = category_id;
        this.sub_category_id = sub_category_id;
        this.banner_title = banner_title;
        this.imagepath = imagepath;
    }

    public String getId() {
        return id;
    }

    public String getBanner_type() {
        return banner_type;
    }

    public String getMain_category_id() {
        return main_category_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public String getBanner_title() {
        return banner_title;
    }

    public String getImagepath() {
        return imagepath;
    }
}
