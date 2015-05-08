package com.craftic.Entities;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

/**
 * Created by keren on 4/29/15.
 */

@DatabaseTable(tableName = "users")
public class User {
    private static final String TAG = "User";

    public static Dao<User, Long> userDao ;

    @DatabaseField(id = true)
    private String id ;

    @DatabaseField()
    private String fname ;

    @DatabaseField()
    private String lname ;

    @DatabaseField()
    private String username ;

    @DatabaseField()
    private String email ;

    @DatabaseField()
    private String phonenumber ;

    @DatabaseField()
    private String password ;

    @DatabaseField()
    private String categorytype ;

    @DatabaseField()
    private String longitude ;

    @DatabaseField()
    private String latitude ;


    public User() {
    }

    public User(String latitude, String id, String fname, String lname, String username,
                String email, String phonenumber, String password, String categorytype, String longitude) {
        this.latitude = latitude;
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
        this.categorytype = categorytype;
        this.longitude = longitude;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean save()
    {

        try {
            Dao.CreateOrUpdateStatus createOrUpdateStatus = userDao.createOrUpdate(this);
            return (createOrUpdateStatus.isCreated() || createOrUpdateStatus.isUpdated()) ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete()
    {

        try {
            userDao.delete(this);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCategorytype() {
        return categorytype;
    }

    public void setCategorytype(String categorytype) {
        this.categorytype = categorytype;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
