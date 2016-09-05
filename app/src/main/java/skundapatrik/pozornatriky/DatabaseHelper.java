package skundapatrik.pozornatriky;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "database.db";
    public static final String TABLE_NAME = "database_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Score";
    public static final String COL_4 = "Kolo1";
    public static final String COL_5 = "Kolo2";
    public static final String COL_6 = "Kolo3";
    public static final String COL_7 = "Kolo4";
    public static final String COL_8 = "Kolo5";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_String = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_2 + " TEXT," +
                COL_3 + " INTEGER,"+
                COL_4 + " TEXT," +
                COL_5 + " TEXT," +
                COL_6 + " TEXT," +
                COL_7 + " TEXT," +
                COL_8 + " TEXT"  +")";
        db.execSQL(SQL_String);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String name,String score,String kolo1,String kolo2,String kolo3,String kolo4,String kolo5)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3, score);
        contentValues.put(COL_4, kolo1);
        contentValues.put(COL_5, kolo2);
        contentValues.put(COL_6, kolo3);
        contentValues.put(COL_7, kolo4);
        contentValues.put(COL_8, kolo5);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public void deleteDb()
    {
        getWritableDatabase().delete(TABLE_NAME, null, null);
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

}
