package com.melikekarayilan.lasttodolist.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.melikekarayilan.lasttodolist.Model.Todoitem;
import com.melikekarayilan.lasttodolist.Model.Todolist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MehmetAliATLI on 8.10.2019.
 */
public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Todolist.db";

    public static final String USER_TABLE = "user";
    public static final String USER_ID = "userId";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_PASSWORD = "userpassword";

    public static final String TODOLIST_TABLE = "todolist";
    public static final String TODOLIST_ID = "todolistId";
    public static final String TODOLIST_NAME = "todolistName";
    public static final String USERTODOLIST_ID = "userTodolistId";

    public static final String TODOITEM_TABLE = "todoitem";
    public static final String TODOITEM_ID = "todoitemId";
    public static final String TODOITEM_NAME = "todoitemName";
    public static final String TODOITEM_DESCRIPTION = "todoitemDesc";
    public static final String TODOITEM_DEADLINE = "todoitemDeadline";
    public static final String TODOITEM_STATUS = "todoitemStatus";
    // public static final String TODOITEM_CREATETIME = "todoitemCreatetime";
    public static final String LISTTODOITEM_ID = "listTodoitemId";//bagli oldugu list id
    public static final String LISTTODOUSER_ID = "listTodouserId";//bagli oldugu user id


    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 205);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            db.execSQL("CREATE TABLE " + USER_TABLE + "(userId INTEGER PRIMARY KEY AUTOINCREMENT, userEmail TEXT NOT NULL, userpassword TEXT NOT NULL )");
            db.execSQL("CREATE TABLE " + TODOLIST_TABLE + "(todolistId INTEGER PRIMARY KEY AUTOINCREMENT, todolistName TEXT NOT NULL, userTodolistId INTEGER NOT NULL )");
            db.execSQL("CREATE TABLE " + TODOITEM_TABLE + "(todoitemId INTEGER PRIMARY KEY AUTOINCREMENT, todoitemName TEXT NOT NULL, todoitemDesc TEXT NOT NULL, todoitemDeadline TEXT NOT NULL, todoitemStatus INTEGER NOT NULL, listTodoitemId INTEGER NOT NULL, listTodouserId INTEGER NOT NULL )");


        } catch (Exception ex) {
            Log.w("wtf", ex);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS user");
            db.execSQL("DROP TABLE IF EXISTS todolist");
            db.execSQL("DROP TABLE IF EXISTS todoitem");

            onCreate(db);
        }


    }

    public Boolean insertUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userEmail", email);
        contentValues.put("userpassword", password);

        long ins = db.insert("user", null, contentValues);
        if (ins == -1) return false;
        else return true;
    }

    public Boolean checkmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where userEmail=?", new String[]{email});
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    public Boolean checklogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where userEmail=? and userpassword=?", new String[]{email, password});
        if (cursor.getCount() > 0) return true;
        else return false;

    }

    public int getUserId(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where userEmail=?", new String[]{email});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getInt(cursor.getColumnIndex("userId"));
        } else return 0;
    }

    public void insertTodolist(String name, int userTodolistId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("todolistName", name);
        values.put("userTodolistId", userTodolistId);

        db.insert("todolist", null, values);

    }

    public List<Todolist> getTodolist(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Todolist> todolists = new ArrayList<Todolist>();

        Cursor cursor = db.rawQuery("Select * from " + TODOLIST_TABLE + " where " + USERTODOLIST_ID + "='" + userId + "'", null);

        Todolist todolist = null;
        if (cursor.getCount() == 0) {

            return todolists;
        } else {

            while (cursor.moveToNext()) {
                todolist = new Todolist();
                todolist.setTodolistId(Integer.parseInt(cursor.getString(0)));
                todolist.setTodolistName(cursor.getString(1));
                todolist.setUserTodolistId(Integer.parseInt(cursor.getString(2)));
                todolists.add(todolist);
            }

            return todolists;
        }

    }

    public void insertTodoitem(String name, String desc, String deadline, int todolistId, int todolistUserId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("todoitemName", name);
        values.put("todoitemDesc", desc);
        values.put("todoitemDeadline", deadline);
        values.put("todoitemStatus", 0);
        //  values.put("todoitemCreatetime", System.currentTimeMillis());
        values.put("listTodoitemId", todolistId);
        values.put("listTodouserId", todolistUserId);

        db.insert("todoitem", null, values);

    }

    public List<Todoitem> getTodoitemList(int todolistId, int userTodolistId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Todoitem> todoitemList = new ArrayList<Todoitem>();

        Cursor cursor = db.rawQuery("Select * from " + TODOITEM_TABLE + " where " + LISTTODOITEM_ID + "='" + todolistId + "'" + "and " + LISTTODOUSER_ID + "='" + userTodolistId + "'", null);
        Todoitem todoitem = null;
        if (cursor.getCount() == 0) {
            return todoitemList;
        } else {
            while (cursor.moveToNext()) {
                todoitem = new Todoitem();
                todoitem.setTodoitemId(Integer.parseInt(cursor.getString(0)));
                todoitem.setTodoitemName(cursor.getString(1));
                todoitem.setTodoitemDesc(cursor.getString(2));
                todoitem.setTodoitemDeadline(cursor.getString(3));
                todoitem.setTodoitemStatus(Integer.parseInt(cursor.getString(4)));
                // todoitem.setTodoitemCreatetime(Integer.parseInt(cursor.getString(5)));
                todoitem.setListTodoitemId(Integer.parseInt(cursor.getString(5)));
                todoitem.setListTodouserId(Integer.parseInt(cursor.getString(6)));
                todoitemList.add(todoitem);

            }
            return todoitemList;
        }
    }

    public List<Todoitem> getTodoitemListWithName(int todolistId, int userTodolistId, String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Todoitem> todoitemList = new ArrayList<Todoitem>();

        Cursor cursor = db.rawQuery("Select * from todoitem where listTodoitemId=? and listTodouserId=? and todoitemName like ?", new String[]{String.valueOf(todolistId), String.valueOf(userTodolistId), name + "%"});
        Todoitem todoitem = null;
        if (cursor.getCount() == 0) {
            return todoitemList;
        } else {
            while (cursor.moveToNext()) {
                todoitem = new Todoitem();
                todoitem.setTodoitemId(Integer.parseInt(cursor.getString(0)));
                todoitem.setTodoitemName(cursor.getString(1));
                todoitem.setTodoitemDesc(cursor.getString(2));
                todoitem.setTodoitemDeadline(cursor.getString(3));
                todoitem.setTodoitemStatus(Integer.parseInt(cursor.getString(4)));
                // todoitem.setTodoitemCreatetime(Integer.parseInt(cursor.getString(5)));
                todoitem.setListTodoitemId(Integer.parseInt(cursor.getString(5)));
                todoitem.setListTodouserId(Integer.parseInt(cursor.getString(6)));
                todoitemList.add(todoitem);

            }
            return todoitemList;
        }
    }


    public List<Todoitem> getItemlistwithStatus(int userId, int listId, int status) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Todoitem> todoitemList = new ArrayList<Todoitem>();

        Cursor cursor = db.rawQuery("Select * from " + TODOITEM_TABLE + " where " + LISTTODOUSER_ID + "='" + userId + "'" + "and " + LISTTODOITEM_ID + "='" + listId + "'" + "and " + TODOITEM_STATUS + "='" + status + "'", null);
        Todoitem todoitem = null;
        if (cursor.getCount() == 0) {
            return todoitemList;
        } else {
            while (cursor.moveToNext()) {
                todoitem = new Todoitem();
                todoitem.setTodoitemId(Integer.parseInt(cursor.getString(0)));
                todoitem.setTodoitemName(cursor.getString(1));
                todoitem.setTodoitemDesc(cursor.getString(2));
                todoitem.setTodoitemDeadline(cursor.getString(3));
                todoitem.setTodoitemStatus(Integer.parseInt(cursor.getString(4)));
                // todoitem.setTodoitemCreatetime(Integer.parseInt(cursor.getString(5)));
                todoitem.setListTodoitemId(Integer.parseInt(cursor.getString(5)));
                todoitem.setListTodouserId(Integer.parseInt(cursor.getString(6)));
                todoitemList.add(todoitem);

            }
            return todoitemList;
        }

    }

    public void deleteTodolist(int userId, int todolistId) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor= db.rawQuery("Delete * from " + TODOLIST_TABLE + " where " + USERTODOLIST_ID + "='" + userId + "'" + "and " + TODOLIST_ID + "='" + todolistId + "'",null);

        //String deleteTodolisttable = "Delete * from " + TODOLIST_TABLE + " where " + USERTODOLIST_ID + "='" + userId + "'" + "and " + TODOLIST_ID + "='" + todolistId + "'";
        // String deleteTodoitemtable = "Delete * from " + TODOITEM_TABLE + " where " + LISTTODOUSER_ID + "='" + userId + "'" + "and " + LISTTODOITEM_ID + "='" + todolistId + "'";
        // db.execSQL(deleteTodoitemtable);
        //db.execSQL(deleteTodolisttable);

        db.delete(TODOITEM_TABLE, LISTTODOITEM_ID + " = ? and " + LISTTODOUSER_ID + " = ?", new String[]{String.valueOf(todolistId), String.valueOf(userId)});
        db.delete(TODOLIST_TABLE, TODOLIST_ID + " = ? and " + USERTODOLIST_ID + " = ?", new String[]{String.valueOf(todolistId), String.valueOf(userId)});

    }

    public void deleteTodoitem(int todoitemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TODOITEM_TABLE, TODOITEM_ID + " = ?", new String[]{String.valueOf(todoitemId)});

    }

    public Todoitem getTodoitem(int itemId, int todolistId, int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Todoitem todoitem = null;
        Cursor cursor = db.rawQuery("Select * from todoitem where todoitemId=? and listTodoitemId=? and listTodouserId=?", new String[]{String.valueOf(itemId), String.valueOf(todolistId), String.valueOf(userId)});

        cursor.moveToFirst();
        if (cursor.getCount() == 0) {

            return todoitem;
        } else {
            todoitem = new Todoitem();
            cursor.moveToFirst();
            todoitem.setTodoitemName(cursor.getString(1));
            todoitem.setTodoitemDesc(cursor.getString(2));
            todoitem.setTodoitemDeadline(cursor.getString(3));
            todoitem.setTodoitemStatus(cursor.getInt(4));
            return todoitem;
        }

    }

    public void updateItem(String itemName, String description, String deadline, int userId, int todolistId, int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TODOITEM_NAME, itemName);
        values.put(TODOITEM_DESCRIPTION, description);
        values.put(TODOITEM_DEADLINE, deadline);

        db.update(TODOITEM_TABLE, values, TODOITEM_ID + " = ? and " + LISTTODOITEM_ID + " = ? and " + LISTTODOUSER_ID + " = ?", new String[]{String.valueOf(itemId), String.valueOf(todolistId), String.valueOf(userId)});

    }

    public void updateListName(String name, int userId, int todolistId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TODOLIST_NAME, name);
        db.update(TODOLIST_TABLE, values, TODOLIST_ID + " = ? and " + USERTODOLIST_ID + " = ?", new String[]{String.valueOf(todolistId), String.valueOf(userId)});

    }

    public void updateStatus(int listId, int itemId, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TODOITEM_STATUS, status);
        db.update(TODOITEM_TABLE, values, TODOITEM_ID + " = ? and " + LISTTODOITEM_ID + " = ?", new String[]{String.valueOf(itemId), String.valueOf(listId)});

    }

}
