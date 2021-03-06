package com.craftic.DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.craftic.Entities.User;
import com.craftic.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by keren on 4/29/15.
 */

    public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

        private static final String DATABASE_NAME = "craftic.db";
        private static final int DATABASE_VERSION = 1;

        /**
         * The data access object used to interact with the Sqlite database to do C.R.U.D operations.
         */
        private Dao<User, Long> userDao = null;
        private RuntimeExceptionDao<User, Long> uerRuntimeExceptionDao = null;


        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION,
                    /**
                     * R.raw.ormlite_config is a reference to the ormlite_config.txt file in the
                     * /res/raw/ directory of this project
                     * */
                    R.raw.ormlite_config);
        }

        @Override
        public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
            try {

                /**
                 * creates the database table
                 */

                TableUtils.createTable(connectionSource, User.class);



            } catch (SQLException e) {

                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                              int oldVersion, int newVersion) {
            try {
                /**
                 * Recreates the database when onUpgrade is called by the framework
                 */
                TableUtils.dropTable(connectionSource, User.class, false);
                onCreate(database, connectionSource);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        /**
         * Returns an instance of the data access object
         * @return
         * @throws SQLException
         */
        public Dao<User, Long> getUserDao() throws SQLException {
            if(userDao == null) {
                userDao = getDao(User.class);
            }
            return userDao;
        }

        public RuntimeExceptionDao<User, Long> getUserRuntimeDao()  {
            if(uerRuntimeExceptionDao == null) {
                uerRuntimeExceptionDao = getRuntimeExceptionDao(User.class);
            }
            return uerRuntimeExceptionDao;
        }

        /**
         * Close the database connections and clear any cached DAOs.
         */
        @Override
        public void close() {
            super.close();
            userDao = null;
        }



    }
