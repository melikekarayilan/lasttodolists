package com.melikekarayilan.lasttodolist.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MehmetAliATLI on 8.10.2019.
 */
public class Todolist implements Parcelable {

    private int todolistId;
    private String todolistName;
    private int userTodolistId;

    public Todolist(int id, String listName, int userListName) {
        this.todolistId = id;
        this.todolistName = listName;
        this.userTodolistId = userListName;
    }

    public Todolist() {

    }

    protected Todolist(Parcel in) {
        todolistId = in.readInt();
        todolistName = in.readString();
        userTodolistId = in.readInt();
    }

    public static final Creator<Todolist> CREATOR = new Creator<Todolist>() {
        @Override
        public Todolist createFromParcel(Parcel in) {
            return new Todolist(in);
        }

        @Override
        public Todolist[] newArray(int size) {
            return new Todolist[size];
        }
    };

    public int getTodolistId() {
        return todolistId;
    }

    public void setTodolistId(int todolistId) {
        this.todolistId = todolistId;
    }

    public String getTodolistName() {
        return todolistName;
    }

    public void setTodolistName(String todolistName) {
        this.todolistName = todolistName;
    }

    public int getUserTodolistId() {
        return userTodolistId;
    }

    public void setUserTodolistId(int userTodolistId) {
        this.userTodolistId = userTodolistId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(todolistId);
        dest.writeString(todolistName);
        dest.writeInt(userTodolistId);
    }
}
