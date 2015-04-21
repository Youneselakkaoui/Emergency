package com.emergency.helpers;

/**
 * Created by Noureddine on 19/04/2015.
 */
//import info.androidhive.sqlite.model.Tag;
//import info.androidhive.sqlite.model.Todo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper /*extends SQLiteOpenHelper*/ {


    private SQLiteDatabase db;
    // Logcat tag
    private static final String LOG = DatabaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "emergencyDB";

    // Table Names

    public static final String TABLE_USER = "user";
    //private static final String TABLE_USER_SITUATION = "todo_tags";

    // Table Create Statements
    // Todo table create statement
  //  private static final String CREATE_TABLE_SITUATION = "CREATE TABLE "
   //         + Situation.TABLE_SITUATION + "("
   //         + Situation.SITUATION_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
   //         + Situation.SITUATION_ID_EMETTEUR     + " CHAR(20) NOT NULL,"
   //         "  `id_emetteur` VARCHAR(20) NOT NULL,\n" +
   //         + Situation.SITUATION_TITRE           + " CHAR(45) NOT NULL,"
   //         "  `titre` VARCHAR(45) NOT NULL,\n" +
   //         + Situation.SITUATION_MESSAGE         + " TEXT(300),"
   //         "  `message` TEXT(300) NULL,\n" +
   //         + Situation.SITUATION_PIECES_JOINTES  + "SMALLINT(1) NULL,"
   //         "  `pieces_jointes` SMALLINT(1) NULL,\n" +
   //         + Situation.SITUATION_TYPE_ENVOI      + "SMALLINT(1) NULL"
   //         "  `type_envoi` SMALLINT(1) NULL,";
   //         + ");";
   //@Override
   public void onCreate(SQLiteDatabase dbo) {
    //    dbo.execSQL(CREATE_TABLE_SITUATION);
   }

    //@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
 //       db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITUATION);

        // create new tables
        onCreate(db);
    }

    /**
     * Creating a todo
     */

    public SQLiteDatabase getWritableDatabase() {
        this.db = this.getWritableDatabase();
        return db;
    }

    public SQLiteDatabase getReadableDatabase() {
        this.db = this.getReadableDatabase();
        return db;
    }

}
