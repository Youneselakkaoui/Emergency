package com.emergency.helpers;

/**
 * Created by Noureddine on 19/04/2015.
 */

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

import com.emergency.entity.Situation;
import com.emergency.entity.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static DatabaseHelper sInstance;
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
    private static final String CREATE_TABLE_SITUATION = "CREATE TABLE "
            + Situation.TABLE_SITUATION + "("
            + Situation.SITUATION_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Situation.SITUATION_ID_EMETTEUR + " CHAR(20) NOT NULL,"
            //         "  `id_emetteur` VARCHAR(20) NOT NULL,\n" +
            + Situation.SITUATION_TITRE + " CHAR(45) NOT NULL,"
            //         "  `titre` VARCHAR(45) NOT NULL,\n" +
            + Situation.SITUATION_MESSAGE + " TEXT(300),"
            //         "  `message` TEXT(300) NULL,\n" +
            + Situation.SITUATION_PIECES_JOINTES + " SMALLINT(1) NULL, "
            //         "  `pieces_jointes` SMALLINT(1) NULL,\n" +
            + Situation.SITUATION_TYPE_ENVOI + " SMALLINT(1) NULL "
            //         "  `type_envoi` SMALLINT(1) NULL,";
            + ");";

    private static final String CREATE_TABLE_USER = "CREATE TABLE " + User.TABLE_USER + " ( "
            + User.USER_TELEPHONE + " CHAR(20) PRIMARY KEY, "
            + User.USER_NOM + " CHAR(30), "
            + User.USER_PRENOM + " CHAR(30), "
            + User.USER_DATENAISSANCE + " DATE, "
            + User.USER_SEXE + " SMALLINT(1), "
            + User.USER_GROUPESANGUIN + " SMALLINT(1), "
            + User.USER_DIABETE + " SMALLINT(1), "
            + User.USER_CHOLESTEROL + " SMALLINT(1), "
            + User.USER_AUTRESINFOS + " TEXT(500), "
            + User.USER_GCM_DEVICE_ID + " TEXT(200), "
            + User.USER_ME + " SMALLINT(1) "
            + ");";

    @Override
    public void onCreate(SQLiteDatabase dbo) {
        dbo.execSQL(CREATE_TABLE_SITUATION);
        dbo.execSQL(CREATE_TABLE_USER);
        dbo.execSQL("INSERT INTO " + Situation.TABLE_SITUATION + " ("
                + Situation.SITUATION_ID_EMETTEUR + ","
                + Situation.SITUATION_TITRE + ","
                + Situation.SITUATION_MESSAGE + ","
                + Situation.SITUATION_PIECES_JOINTES + ","
                + Situation.SITUATION_TYPE_ENVOI
                + ") VALUES('0654200743', 'Help !!',   " +
                "'ALERTE, HELP ME, !!!!!!!'," +
                "1, 2);");
        dbo.execSQL("INSERT INTO " + Situation.TABLE_SITUATION + " ("
                + Situation.SITUATION_ID_EMETTEUR + ","
                + Situation.SITUATION_TITRE + ","
                + Situation.SITUATION_MESSAGE + ","
                + Situation.SITUATION_PIECES_JOINTES + ","
                + Situation.SITUATION_TYPE_ENVOI
                + ") VALUES('0654200743', 'Help2 !!',   " +
                "'ALERTE, HELP ME, !!!!!!!'," +
                "1, 2);");
        dbo.execSQL("INSERT INTO " + Situation.TABLE_SITUATION + " ("
                + Situation.SITUATION_ID_EMETTEUR + ","
                + Situation.SITUATION_TITRE + ","
                + Situation.SITUATION_MESSAGE + ","
                + Situation.SITUATION_PIECES_JOINTES + ","
                + Situation.SITUATION_TYPE_ENVOI
                + ") VALUES('0654200743', 'Help3 !!',   " +
                "'ALERTE, HELP ME, !!!!!!!'," +
                "1, 2);");
        dbo.execSQL("INSERT INTO " + Situation.TABLE_SITUATION + " ("
                + Situation.SITUATION_ID_EMETTEUR + ","
                + Situation.SITUATION_TITRE + ","
                + Situation.SITUATION_MESSAGE + ","
                + Situation.SITUATION_PIECES_JOINTES + ","
                + Situation.SITUATION_TYPE_ENVOI
                + ") VALUES('0654200743', 'Accedent',   " +
                "'ALERTE, HELP ME,!!!!!!!'," +
                "1, 2);");
        dbo.execSQL("INSERT INTO " + Situation.TABLE_SITUATION + " ("
                + Situation.SITUATION_ID_EMETTEUR + ","
                + Situation.SITUATION_TITRE + ","
                + Situation.SITUATION_MESSAGE + ","
                + Situation.SITUATION_PIECES_JOINTES + ","
                + Situation.SITUATION_TYPE_ENVOI
                + ") VALUES('0654200743', 'Accedent 2',   " +
                "'ALERTE, HELP ME,!!!!!!!'," +
                "1, 2);");
        dbo.execSQL("INSERT INTO " + Situation.TABLE_SITUATION + " ("
                + Situation.SITUATION_ID_EMETTEUR + ","
                + Situation.SITUATION_TITRE + ","
                + Situation.SITUATION_MESSAGE + ","
                + Situation.SITUATION_PIECES_JOINTES + ","
                + Situation.SITUATION_TYPE_ENVOI
                + ") VALUES('0654200743', 'Accedent 3',   " +
                "'ALERTE, HELP ME,!!!!!!!'," +
                "1, 2);");
        dbo.execSQL("INSERT INTO " + Situation.TABLE_SITUATION + " ("
                + Situation.SITUATION_ID_EMETTEUR + ","
                + Situation.SITUATION_TITRE + ","
                + Situation.SITUATION_MESSAGE + ","
                + Situation.SITUATION_PIECES_JOINTES + ","
                + Situation.SITUATION_TYPE_ENVOI
                + ") VALUES('0654200743', 'Incendi',   " +
                "'ALERTE, HELP ME, I M HERE !!!!!!!'," +
                "1, 2);");
        dbo.execSQL("INSERT INTO " + Situation.TABLE_SITUATION + " ("
                + Situation.SITUATION_ID_EMETTEUR + ","
                + Situation.SITUATION_TITRE + ","
                + Situation.SITUATION_MESSAGE + ","
                + Situation.SITUATION_PIECES_JOINTES + ","
                + Situation.SITUATION_TYPE_ENVOI
                + ") VALUES('0654200743', 'Incendi X',   " +
                "'ALERTE, HELP ME, I M HERE !!!!!!!'," +
                "1, 2);");
        dbo.execSQL("INSERT INTO " + Situation.TABLE_SITUATION + " ("
                + Situation.SITUATION_ID_EMETTEUR + ","
                + Situation.SITUATION_TITRE + ","
                + Situation.SITUATION_MESSAGE + ","
                + Situation.SITUATION_PIECES_JOINTES + ","
                + Situation.SITUATION_TYPE_ENVOI
                + ") VALUES('0654200743', 'Incendi 3',   " +
                "'ALERTE, HELP ME, I M HERE !!!!!!!'," +
                "1, 2);");
        dbo.execSQL("INSERT INTO " + Situation.TABLE_SITUATION + " ("
                + Situation.SITUATION_ID_EMETTEUR + ","
                + Situation.SITUATION_TITRE + ","
                + Situation.SITUATION_MESSAGE + ","
                + Situation.SITUATION_PIECES_JOINTES + ","
                + Situation.SITUATION_TYPE_ENVOI
                + ") VALUES('0654200743', 'Incendi 5',   " +
                "'ALERTE, HELP ME, I M HERE !!!!!!!'," +
                "1, 2);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + Situation.TABLE_SITUATION);
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_USER);

        // create new tables
        onCreate(db);
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Creating a todo
     */
}
