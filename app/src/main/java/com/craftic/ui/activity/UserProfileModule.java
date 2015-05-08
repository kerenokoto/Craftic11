package com.craftic.ui.activity;

/**
 * Created by keren on 4/30/15.
 */
public class UserProfileModule {

    public int iconId;
    public String  title;
    public String fname;
    public String lname ;
    public String username;
    public String categorytype;

    public UserProfileModule() {
    }

    public UserProfileModule(int iconId, String categorytype, String title, String fname,
                             String lname, String username) {
        this.iconId = iconId;
        this.categorytype = categorytype;
        this.title = title;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
    }

    public String getCategorytype() {
        return categorytype;
    }

    public void setCategorytype(String categorytype) {
        this.categorytype = categorytype;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
