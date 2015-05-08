package com.craftic.DAL;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import com.craftic.Entities.User;

/**
 * Created by keren on 4/29/15.
 */
public class UserRepository {

    private DatabaseHelper db;
    Dao<User, Long> userDao;

    public UserRepository(Context ctx)
    {
        try {
            DatabaseManager dbManager = new DatabaseManager();
            db = dbManager.getHelper(ctx);
            userDao = db.getUserDao();
        } catch (SQLException e) {
            // TODO: Exception Handling
            //  e.printStackTrace();
        }

    }

    public int create(User user)
    {
        try {
            return userDao.create(user);
        } catch (SQLException e) {
            // TODO: Exception Handling
            // e.printStackTrace();
        }
        return 0;
    }
    public int update(User user)
    {
        try {
            return userDao.update(user);
        } catch (SQLException e) {
            // TODO: Exception Handling
            //  e.printStackTrace();
        }
        return 0;
    }
    public int delete(User user)
    {
        try {
            return userDao.delete(user);
        } catch (SQLException e) {
            // TODO: Exception Handling
            // e.printStackTrace();
        }
        return 0;
    }

    public List<User> getAll()
    {
        try {
            return userDao.queryForAll();
        } catch (SQLException e) {
            // TODO: Exception Handling
            // e.printStackTrace();
        }
        return null;
    }

    public  List<User> getUser(String username)
    {
        try {
            List<User>  u = userDao.queryBuilder().where().eq("username", username).query();
            return u;
        } catch (SQLException e) {
            // TODO: Exception Handling
            // e.printStackTrace();
        }
        return null;
    }

}