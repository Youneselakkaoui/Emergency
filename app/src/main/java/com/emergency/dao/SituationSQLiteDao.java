package com.emergency.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.emergency.entity.Situation;
import com.emergency.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noureddine on 19/04/2015.
 */
public class SituationSQLiteDao implements SituationDao{
    private SQLiteDatabase dbW;
    private SQLiteDatabase dbR;
    private DatabaseHelper databaseHelper;

    public SituationSQLiteDao(Context context) {

        this.databaseHelper = DatabaseHelper.getInstance(context);
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

        // insert row_list_item
        return (int)this.dbW.insert(Situation.TABLE_SITUATION, null, values);
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
        Cursor cursor = dbR.query(Situation.TABLE_SITUATION,
                new String[]{Situation.SITUATION_KEY_ID,
                        Situation.SITUATION_ID_EMETTEUR,
                        Situation.SITUATION_TITRE,
                        Situation.SITUATION_MESSAGE,
                        Situation.SITUATION_TYPE_ENVOI,
                        Situation.SITUATION_PIECES_JOINTES
                }, Situation.SITUATION_KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        /*
        + Situation.SITUATION_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Situation.SITUATION_ID_EMETTEUR     + " CHAR(20) NOT NULL,"
   //         "  `id_emetteur` VARCHAR(20) NOT NULL,\n" +
            + Situation.SITUATION_TITRE           + " CHAR(45) NOT NULL,"
   //         "  `titre` VARCHAR(45) NOT NULL,\n" +
            + Situation.SITUATION_MESSAGE         + " TEXT(300),"
   //         "  `message` TEXT(300) NULL,\n" +
            + Situation.SITUATION_PIECES_JOINTES  + " SMALLINT(1) NULL, "
   //         "  `pieces_jointes` SMALLINT(1) NULL,\n" +
            + Situation.SITUATION_TYPE_ENVOI      + " SMALLINT(1) NULL "
         */
        // (int idSituation, String emetteur, String titre, String message, short piecesJointes, short typeEnvoi)
        Situation s = new Situation(Integer.parseInt(cursor.getString(0)),
                                    cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getString(3),
                                    Short.parseShort(cursor.getString(4)),
                                    Short.parseShort(cursor.getString(5)));
        // return contact
        return s;
    }

    @Override
    public List<Situation> selectAll() {
        List<Situation> list = new ArrayList<Situation>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Situation.TABLE_SITUATION;

        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                System.out.println("cursor.getString(0)" +  cursor.getString(0));
                System.out.println("cursor.getString(1)" +  cursor.getString(1));
                System.out.println("cursor.getString(2)" +  cursor.getString(2));
                System.out.println("cursor.getString(3)" +  cursor.getString(3));
                System.out.println("cursor.getString(4)" +  cursor.getString(4));
                System.out.println("cursor.getString(5)" +  cursor.getString(5));

                Situation s = new Situation(Integer.parseInt(cursor.getString(0)),
                                            cursor.getString(1),
                                            cursor.getString(2),
                                            cursor.getString(3),
                                            Short.parseShort(cursor.getString(4)),
                                            Short.parseShort(cursor.getString(5)));
                // Adding Situation to list
                list.add(s);
            } while (cursor.moveToNext());
        }

        // return situations list
        return list;
    }
}
