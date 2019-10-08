package com.melikekarayilan.lasttodolist.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MehmetAliATLI on 8.10.2019.
 */
public class Todoitem implements Parcelable {

    private int todoitemId;
    private String todoitemName;

    protected Todoitem(Parcel in) {
        todoitemId = in.readInt();
        todoitemName = in.readString();
        todoitemDesc = in.readString();
        todoitemDeadline = in.readString();
        todoitemStatus = in.readInt();
        listTodoitemId = in.readInt();
        todoitemCreatetime = in.readInt();
        listTodouserId = in.readInt();
    }

    public static final Creator<Todoitem> CREATOR = new Creator<Todoitem>() {
        @Override
        public Todoitem createFromParcel(Parcel in) {
            return new Todoitem(in);
        }

        @Override
        public Todoitem[] newArray(int size) {
            return new Todoitem[size];
        }
    };

    public int getTodoitemId() {
        return todoitemId;
    }

    public void setTodoitemId(int todoitemId) {
        this.todoitemId = todoitemId;
    }

    public String getTodoitemName() {
        return todoitemName;
    }

    public void setTodoitemName(String todoitemName) {
        this.todoitemName = todoitemName;
    }

    public String getTodoitemDesc() {
        return todoitemDesc;
    }

    public void setTodoitemDesc(String todoitemDesc) {
        this.todoitemDesc = todoitemDesc;
    }

    public String getTodoitemDeadline() {
        return todoitemDeadline;
    }

    public void setTodoitemDeadline(String todoitemDeadline) {
        this.todoitemDeadline = todoitemDeadline;
    }

    public int getTodoitemStatus() {
        return todoitemStatus;
    }

    public void setTodoitemStatus(int todoitemStatus) {
        this.todoitemStatus = todoitemStatus;
    }

    public int getListTodoitemId() {
        return listTodoitemId;
    }

    public void setListTodoitemId(int listTodoitemId) {
        this.listTodoitemId = listTodoitemId;
    }

    private String todoitemDesc;
    private String todoitemDeadline;
    private int todoitemStatus;
    private int listTodoitemId;

    public int getTodoitemCreatetime() {
        return todoitemCreatetime;
    }

    public void setTodoitemCreatetime(int todoitemCreatetime) {
        this.todoitemCreatetime = todoitemCreatetime;
    }

    private int todoitemCreatetime;

    public int getListTodouserId() {
        return listTodouserId;
    }

    public void setListTodouserId(int listTodouserId) {
        this.listTodouserId = listTodouserId;
    }

    private int listTodouserId;


    public Todoitem() {

    }

    public Todoitem(int id, String name, String description, String deadline, int status, int todolistId, int listTodouserId) {
        this.todoitemId = id;
        this.todoitemName = name;
        this.todoitemDesc = description;
        this.todoitemDeadline = deadline;
        this.todoitemStatus = status;
        this.listTodoitemId = todolistId;
        this.listTodouserId = listTodouserId;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(todoitemId);
        dest.writeString(todoitemName);
        dest.writeString(todoitemDesc);
        dest.writeString(todoitemDeadline);
        dest.writeInt(todoitemStatus);
        dest.writeInt(listTodoitemId);
        dest.writeInt(todoitemCreatetime);
        dest.writeInt(listTodouserId);
    }
}
