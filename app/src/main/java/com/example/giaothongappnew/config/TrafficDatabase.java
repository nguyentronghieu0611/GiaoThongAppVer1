package com.example.giaothongappnew.config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.giaothongappnew.model.AmercementLevel;
import com.example.giaothongappnew.model.MarkUser;
import com.example.giaothongappnew.model.SearchHistory;
import com.example.giaothongappnew.model.User;
import com.example.giaothongappnew.model.Error;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrafficDatabase extends SQLiteOpenHelper implements Serializable {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contact.db";

    public TrafficDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        //tao bang loi
        db.execSQL(" CREATE TABLE " + "tblError" + " (" +
                "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name" + " TEXT NOT NULL, " +
                "description" + " TEXT NOT NULL, " +
                "image" + " BLOB )");

        //tao bang muc phat
        db.execSQL("CREATE TABLE tblAmercementLevel (" +
                "error_id INTEGER NOT NULL," +
                "vehicle_id INTEGER NOT NULL," +
                "amercement TEXT NOT NULL," +
                "PRIMARY KEY (error_id, vehicle_id)" +
                ")");

        //tao bang nguoi dung
        db.execSQL(" CREATE TABLE " + "tblUser" + " (" +
                "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name" + " TEXT NOT NULL, " +
                "email" + " TEXT UNIQUE NOT NULL, " +
                "role" + " INTEGER, " +
                "password" + " TEXT NOT NULL )");

        //tao bang danh dau nguoi dung
        db.execSQL(" CREATE TABLE " + "tblMarkUser" + " (" +
                "user_id" + " INTEGER , " +
                "error_id" + " INTEGER , " +
                "time" + " TEXT NOT NULL, " +
                "PRIMARY KEY (user_id, error_id)" +
                ")");

        //tao bang lich su tim kiem
        db.execSQL(" CREATE TABLE " + "tblSearchHistory" + " (" +
                "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id" + " INTEGER, " +
                "search_text" + " TEXT NOT NULL )");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //thao tac voi bang lenh
    public List<SearchHistory> getHistory(int user_id1){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select * from tblSearchHistory where user_id=? order by id desc limit 5", new String[]{String.valueOf(user_id1)});
        List<SearchHistory> result = new ArrayList<>();
        SearchHistory searchHistory;
        if(c.moveToFirst()){
            do{
                int id = c.getInt(0);
                int user_id = c.getInt(1);
                String content = c.getString(2);
                searchHistory = new SearchHistory(id,user_id,content);
                result.add(searchHistory);
            }while (c.moveToNext());
        }
        return result;
    }

    public Long insertHistory(SearchHistory searchHistory){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", searchHistory.getUser_id());
        values.put("search_text", searchHistory.getSearch_txt());
        return sqLiteDatabase.insert("tblSearchHistory", null, values);
    }

    //thao tac voi user
    public long insertUser(User user){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("name", user.getName());
        values.put("role", user.getRole());
        return sqLiteDatabase.insert("tblUser", null, values);
    }

    public List<User> login(String email, String password){
        boolean trueUser = false;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select * from tblUser where email=? and password=?", new String[]{email,password});
        List<User> result = new ArrayList<>();
        User user = null;
        if(c.moveToFirst()){
            do{
                int id = c.getInt(0);
                String name = c.getString(1);
                String email1 = c.getString(2);
                String pass = c.getString(4);
                int role = c.getInt(3);
                user = new User(id,email1,name,pass,role);
                result.add(user);
            }while (c.moveToNext());
        }
        return result;
    }

    public int updateUser(User user){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("name", user.getName());
        return sqLiteDatabase.update("tblUser",values,"id = ?",new String[]{String.valueOf(user.getId())});
    }

    public int changePassword(User user){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", user.getPassword());
        return sqLiteDatabase.update("tblUser",values,"id = ?",new String[]{String.valueOf(user.getId())});
    }


    //thao tac voi bang loi
    public List<Error> getError(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select * from tblError", null);
        List<Error> result = new ArrayList<>();
        Error error;
        if(c.moveToFirst()){
            do{
                int id = c.getInt(0);
                String name = c.getString(1);
                String description = c.getString(2);
                byte[] image = c.getBlob(3);
                error = new Error(id,name,description,image);
                result.add(error);
            }while (c.moveToNext());
        }
        return result;
    }

    public List<Error> searchError(String errorStr){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select * from tblError where upper(name) like '%"+errorStr+"%'", null);
        List<Error> result = new ArrayList<>();
        Error error;
        if(c.moveToFirst()){
            do{
                int id = c.getInt(0);
                String name = c.getString(1);
                String description = c.getString(2);
                byte[] image = c.getBlob(3);
                error = new Error(id,name,description,image);
                result.add(error);
            }while (c.moveToNext());
        }
        return result;
    }

    public long insertError(Error error){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", error.getName());
        values.put("description", error.getDescription());
        values.put("image", error.getImage());
        return sqLiteDatabase.insert("tblError", null, values);
    }

    public long deleteError(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("tblError","id=?",new String[]{String.valueOf(id)});
    }

    public int updateError(Error error){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", error.getName());
        values.put("image",error.getImage());
        return sqLiteDatabase.update("tblError",values,"id = ?",new String[]{String.valueOf(error.getId())});
    }

    //thao tac voi bang muc phat
    public List<AmercementLevel> getListAmercement(int error_id){
        List<AmercementLevel> listRs = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select * from tblAmercementLevel where error_id = ?", new String[]{String.valueOf(error_id)});
        AmercementLevel amercementLevel = null;
        if(c.moveToFirst()){
            do{
                int error_id1 = c.getInt(0);
                int vehical = c.getInt(1);
                String amercement = c.getString(2);
                amercementLevel = new AmercementLevel(error_id1,vehical,amercement);
                listRs.add(amercementLevel);
            }while (c.moveToNext());
        }
        return listRs;
    }

    public long insertAmercement(AmercementLevel amercementLevel){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("error_id", amercementLevel.getError_id());
        values.put("vehicle_id", amercementLevel.getVehical());
        values.put("amercement", amercementLevel.getAmercement());
        return sqLiteDatabase.insert("tblAmercementLevel", null, values);
    }

    public long updateAmercement(AmercementLevel amercementLevel){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("amercement", amercementLevel.getAmercement());
        return sqLiteDatabase.update("tblAmercementLevel", values, "error_id=? and vehicle_id=?", new String[]{String.valueOf(amercementLevel.getError_id()),String.valueOf(amercementLevel.getVehical())});
    }



    public long deleteAmercement(AmercementLevel amercementLevel){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("tblAmercementLevel","error_id=? and vehicle_id=?",new String[]{String.valueOf(amercementLevel.getError_id()),String.valueOf(amercementLevel.getVehical())});
    }


    //thao tac voi bang danh dau nguoi dung
    public long insertMarkUser(MarkUser markUser){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", markUser.getUser_id());
        values.put("error_id", markUser.getError_id());
        values.put("time", markUser.getTime());
        return sqLiteDatabase.insert("tblMarkUser", null, values);
    }

    public List<MarkUser> getMarkUser(int user_id1){
        List<MarkUser> listRs = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select a.user_id, a.error_id,b.name,a.time,c.name from tblMarkUser a, tblUser b, tblError c where a.user_id = b.id and a.error_id = c.id and a.user_id = ?", new String[]{String.valueOf(user_id1)});
        MarkUser obj;
        if(c.moveToFirst()){
            do{
                int user_id = c.getInt(0);
                int error_id = c.getInt(1);
                String name = c.getString(2);
                String time = c.getString(3);
                String error_name = c.getString(4);
                obj = new MarkUser(user_id,error_id,name,time,error_name);
                listRs.add(obj);
            }while (c.moveToNext());
        }
        return listRs;
    }

    public long deleteMarkUser(int user_id, int error_id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("tblMarkUser","user_id=? and error_id=?",new String[]{String.valueOf(user_id),String.valueOf(error_id)});
    }
}
