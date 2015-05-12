package com.emergency.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.emergency.dao.UserDao;
import com.emergency.entity.User;
import com.emergency.helpers.DatabaseHelper;

import java.util.List;

/**
 * Created by elmehdiharabida on 25/04/2015.
 */
public class UserDaoImpl implements UserDao {
    private SQLiteDatabase dbW;
    private SQLiteDatabase dbR;
    private DatabaseHelper databaseHelper;

    public UserDaoImpl(Context context) {

        this.databaseHelper = DatabaseHelper.getInstance(context);
        this.dbW = databaseHelper.getWritableDatabase();
        this.dbR = databaseHelper.getReadableDatabase();
    }

    public UserDaoImpl(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        this.dbW = databaseHelper.getWritableDatabase();
        this.dbR = databaseHelper.getReadableDatabase();
    }


    @Override
    public int insert(User user) {
        ContentValues values = new ContentValues();

        values.put(User.USER_AUTRESINFOS, user.getAutresInfos());
        values.put(User.USER_CHOLESTEROL, user.getCholesterol());
        values.put(User.USER_DATENAISSANCE, user.getDateNaissance().getTime());
        values.put(User.USER_DIABETE, user.getDiabete());
        values.put(User.USER_GROUPESANGUIN, user.getGroupSanguin());
        values.put(User.USER_NOM, user.getNom());
        values.put(User.USER_PRENOM, user.getPrenom());
        values.put(User.USER_SEXE, user.getSexe());
        values.put(User.USER_TELEPHONE, user.getTelephone());
        return (int) dbW.insert(User.TABLE_USER, null, values);
    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public int delete(User user) {
        return 0;
    }

    @Override
    public User select(String telephone) {
        User s = null;

        String selectQuery = "SELECT  * FROM " + User.TABLE_USER;

        Cursor cursor = dbR.rawQuery(selectQuery, null);
        /*Cursor cursor = dbR.query(User.TABLE_USER,
                new String[]{User.USER_AUTRESINFOS,
                        User.USER_CHOLESTEROL,
                        User.USER_DATENAISSANCE,
                        User.USER_DIABETE,
                        User.USER_GROUPESANGUIN,
                        User.USER_NOM,
                        User.USER_PRENOM,
                        User.USER_SEXE,
                        User.USER_TELEPHONE
                }, User.USER_TELEPHONE + "=?",
                new String[]{null,null,null,null, null, null, null, null,telephone};*/
        if (cursor != null) {
            cursor.moveToFirst();
            System.out.println("cursor.getString(0)" + cursor.getString(0));
            System.out.println("cursor.getString(1)" + cursor.getString(1));
            System.out.println("cursor.getString(2)" + cursor.getString(2));
            System.out.println("cursor.getString(3)" + cursor.getString(3));
            System.out.println("cursor.getString(4)" + cursor.getString(4));
            System.out.println("cursor.getString(5)" + cursor.getString(5));
            System.out.println("cursor.getString(6)" + cursor.getString(6));
            System.out.println("cursor.getString(7)" + cursor.getString(7));
            System.out.println("cursor.getString(8)" + cursor.getString(8));
       /* s = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                Short.parseShort(cursor.getString(4)),
                Short.parseShort(cursor.getString(5)));*/
        }
        // return contact
        return s;


    }

    @Override
    public User selectUser() {
        return select(null);
    }
}
