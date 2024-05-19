package com.example.mycrud.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Helper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "crud";

    public Helper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE users (id  INTEGER PRIMARY KEY autoincrement, name TEXT NOT NULL, email TEXT NOT NULL)";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE  IF EXISTS users");
        onCreate(db);
    }

    public ArrayList<HashMap<String, String>> getAll() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        String QUERY = "SELECT * FROM users";
        SQLiteDatabase database = this.getWritableDatabase();
         Cursor cursor = database.rawQuery(QUERY, null);
         if (cursor.moveToFirst()) {
             do {
                 HashMap<String, String> map = new HashMap<>();
                 map.put("id" , cursor.getString(0));
                 map.put("name" , cursor.getString(1));
                 map.put("email" , cursor.getString(2));
                 list.add(map);
             }while (cursor.moveToNext());
         }
         cursor.close();
         return list;
    }

    public void insert(String name, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String QUERY = "INSERT INTO users (name,email) VALUES('"+name+"' , '"+email+"')";
        db.execSQL(QUERY);
    }

    public void update(int id, String name, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String QUERY = "UPDATE users SET name = '"+name+"', email = '"+email+"' WHERE id = "+id;
        db.execSQL(QUERY);
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String QUERY = "DELETE FROM users WHERE id = "+id;
        db.execSQL(QUERY);
    }
}
