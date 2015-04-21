package com.emergency.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.emergency.entity.Situation;
import com.emergency.helpers.DatabaseHelper;

import java.util.List;

/**
 * Created by Noureddine on 19/04/2015.
 */
public class SituationSQLiteDao implements SituationDao{
    private SQLiteDatabase dbW;
    private SQLiteDatabase dbR;
    private DatabaseHelper databaseHelper;

    public SituationSQLiteDao() {
        this.databaseHelper = new DatabaseHelper();
        this.dbW = databaseHelper.getWritableDatabase();
        this.dbR = databaseHelper.getReadableDatabase();
    }

    public SituationSQLiteDao(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        this.dbW = databaseHelper.getWritableDatabase();
        this.dbR = databaseHelper.getReadableDatabase();
    }

    @Override
    public int insert(Situation s) {
        ContentValues values = new ContentValues();

        values.put(Situation.SITUATION_ID_EMETTEUR, s.getUser().getTelephone());
        values.put(Situation.SITUATION_TITRE, s.getTitre());
        values.put(Situation.SITUATION_MESSAGE, s.getMessage());
        values.put(Situation.SITUATION_PIECES_JOINTES, s.getPiecesJointes());
        values.put(Situation.SITUATION_TYPE_ENVOI, s.getTypeEnvoi());

        // insert row
       // return this.dbW.insert(Situation.TABLE_SITUATION, null, values);
        return 0;
    }

    @Override
    public int update(Situation situation) {
        return 0;
    }

    @Override
    public int delete(Situation situation) {
        return 0;
    }

    @Override
    public Situation select(int id) {
//        Cursor cursor = dbR.query(DatabaseHelper.TABLE_SITUATION,
//                        new String[] {Situation.SITUATION_KEY_ID,
//                                Situation.SITUATION_ID_EMETTEUR,
//                                Situation.SITUATION_TITRE,
//                                Situation.SITUATION_MESSAGE,
//                                Situation.SITUATION_TYPE_ENVOI,
//                                Situation.SITUATION_PIECES_JOINTES
//                        }, Situation.SITUATION_KEY_ID + "=?",
//                        new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();

//        Situation s = new Situation(Integer.parseInt(cursor.getString(0)),
//                                    cursor.getString(1), cursor.getString(2));
        // return contact
//        return s;
           return null;
    }

    @Override
    public List<Situation> selectAll() {
        return null;
    }
}
