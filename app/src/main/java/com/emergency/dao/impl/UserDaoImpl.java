package com.emergency.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.PhoneNumberUtils;
import android.util.Log;

import com.emergency.dao.UserDao;
import com.emergency.entity.User;
import com.emergency.helpers.DatabaseHelper;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by elmehdiharabida on 25/04/2015.
 */
public class UserDaoImpl implements UserDao {
//    private SQLiteDatabase dbW;
//    private SQLiteDatabase dbR;
//    private DatabaseHelper databaseHelper;
//
//    public UserDaoImpl(Context context) {
//
//        this.databaseHelper = DatabaseHelper.getInstance(context);
//        this.dbW = databaseHelper.getWritableDatabase();
//        this.dbR = databaseHelper.getReadableDatabase();
//    }
//
//    public UserDaoImpl(DatabaseHelper databaseHelper) {
//        this.databaseHelper = databaseHelper;
//        this.dbW = databaseHelper.getWritableDatabase();
//        this.dbR = databaseHelper.getReadableDatabase();
//    }


    @Override
    public long insert(User user) {
//        ContentValues values = new ContentValues();
//
//        values.put(User.USER_AUTRESINFOS, user.getAutresInfos());
//        values.put(User.USER_CHOLESTEROL, user.getCholesterol());
//        if (user.getDateNaissance() != null) {
//            values.put(User.USER_DATENAISSANCE, user.getDateNaissance().getTime());
//        }
//        values.put(User.USER_DIABETE, user.getDiabete());
//        values.put(User.USER_GROUPESANGUIN, user.getGroupSanguin());
//        values.put(User.USER_NOM, user.getNom());
//        values.put(User.USER_PRENOM, user.getPrenom());
//        values.put(User.USER_SEXE, user.getSexe());
//        values.put(User.USER_TELEPHONE, user.getTelephone());
//        values.put(User.USER_GCM_DEVICE_ID, user.getGcmDeviceId());
//        values.put(User.USER_ME, user.getMe());
//        values.put(User.USER_DIGITS_ID,user.getDigitsId());
//        return (int) dbW.insert(User.TABLE_USER, null, values);
        user.save();
        return user.getId();
    }

    @Override
    public long update(User user) {

//        ContentValues cv = new ContentValues();
//        cv.put(User.USER_AUTRESINFOS, user.getAutresInfos());
//        cv.put(User.USER_CHOLESTEROL, user.getCholesterol());
//        if (user.getDateNaissance() != null) {
//            cv.put(User.USER_DATENAISSANCE, user.getDateNaissance().getTime());
//        }
//        cv.put(User.USER_DIABETE, user.getDiabete());
//        cv.put(User.USER_GROUPESANGUIN, user.getGroupSanguin());
//        cv.put(User.USER_NOM, user.getNom());
//        cv.put(User.USER_PRENOM, user.getPrenom());
//        cv.put(User.USER_SEXE, user.getSexe());
//        cv.put(User.USER_GCM_DEVICE_ID, user.getGcmDeviceId());
//        cv.put(User.USER_ME, user.getMe());
//        cv.put(User.USER_DIGITS_ID,user.getDigitsId());
//        return dbW.update(User.TABLE_USER, cv, User.USER_TELEPHONE + "= ?", new String[]{user.getTelephone()});
        user.save();
        return user.getId();
    }

    @Override
    public long delete(User user) {
        long id = user.getId();
        user.delete();
        return id;
    }


    @Override
    public User selectUser() {
        return User.findById(User.class, 1L);

    }
}
