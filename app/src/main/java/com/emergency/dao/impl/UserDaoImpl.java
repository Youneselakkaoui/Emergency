package com.emergency.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.emergency.dao.UserDao;
import com.emergency.entity.User;
import com.emergency.helpers.DatabaseHelper;

import java.util.Date;
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

        ContentValues cv = new ContentValues();
        cv.put(User.USER_AUTRESINFOS, user.getAutresInfos());
        cv.put(User.USER_CHOLESTEROL, user.getCholesterol());
        cv.put(User.USER_DATENAISSANCE, user.getDateNaissance().getTime());
        cv.put(User.USER_DIABETE, user.getDiabete());
        cv.put(User.USER_GROUPESANGUIN, user.getGroupSanguin());
        cv.put(User.USER_NOM, user.getNom());
        cv.put(User.USER_PRENOM, user.getPrenom());
        cv.put(User.USER_SEXE, user.getSexe());

        return dbW.update(User.TABLE_USER, cv, User.USER_TELEPHONE + "= ?", new String[]{user.getTelephone()});
    }

    @Override
    public int delete() {
        return 0;
    }


    @Override
    public User selectUser() {
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
            //System.out.println("cursor.getString(0)" + cursor.getString(0)); //telephone
            //System.out.println("cursor.getString(1)" + cursor.getString(1)); //nom
            //System.out.println("cursor.getString(2)" + cursor.getString(2)); //prenom
            //System.out.println("cursor.getString(3)" + cursor.getString(3)); //date naiss
            //System.out.println("cursor.getString(4)" + cursor.getString(4)); //sexe
            //System.out.println("cursor.getString(5)" + cursor.getString(5)); //grpe sanguin
            //System.out.println("cursor.getString(6)" + cursor.getString(6)); //diabete
            //System.out.println("cursor.getString(7)" + cursor.getString(7)); //cholesterol
            //System.out.println("cursor.getString(8)" + cursor.getString(8)); //autres infos
            s = new User(
                    cursor.getString(0), //telephone
                    cursor.getString(8), //autresInfos
                    Short.parseShort(cursor.getString(7)), //cholesterol
                    new Date(Long.parseLong(cursor.getString(3))), //dateNaissance
                    Short.parseShort(cursor.getString(6)), //diabete
                    Short.parseShort(cursor.getString(5)), //groupSanguin
                    cursor.getString(1), //nom
                    cursor.getString(2), //prenom
                    Short.parseShort(cursor.getString(4)) //sexe
            );
            cursor.close();
        }

        // return contact
        return s;

    }
}
