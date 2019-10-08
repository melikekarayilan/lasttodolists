package com.melikekarayilan.lasttodolist.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.melikekarayilan.lasttodolist.Activities.TodoListItemsActivity;
import com.melikekarayilan.lasttodolist.Activities.UpdateItemActivity;
import com.melikekarayilan.lasttodolist.DB.Database;
import com.melikekarayilan.lasttodolist.Model.Todoitem;
import com.melikekarayilan.lasttodolist.R;

import java.util.List;

/**
 * Created by MehmetAliATLI on 8.10.2019.
 */
public class TodoListItemsAdapter extends BaseAdapter {

    private Context mContext;
    List<Todoitem> todoitemList;
    Database db;
    CharSequence[] statusText = {"Incompleted", "Completed", "Expired"};
    AlertDialog alertDialog;
    Integer itemStatus;

    public TodoListItemsAdapter(Context context, List<Todoitem> todoitems) {
        this.mContext = context;
        this.todoitemList = todoitems;
    }

    @Override
    public int getCount() {
        return todoitemList.size();
    }

    @Override
    public Todoitem getItem(int i) {
        return todoitemList.get(i);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(String.valueOf(position));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        db = new Database(mContext);
        if (view == null) {

            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            view = inflater.inflate(R.layout.layout_itemlist, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.itemName = (TextView) view.findViewById(R.id.tv_item_name_set);
            viewHolder.itemDescription = (TextView) view.findViewById(R.id.tv_item_description_set);
            viewHolder.itemDeadline = (TextView) view.findViewById(R.id.tv_item_deadline_set);
            viewHolder.imgDelete = (ImageView) view.findViewById(R.id.img_delete_item);
            viewHolder.itemStatus = (ImageView) view.findViewById(R.id.img_status);
            viewHolder.imgUpdate = (ImageView) view.findViewById(R.id.img_update_item);
            // viewHolder.itemcreateTime = (TextView) view.findViewById(R.id.tv_item_createtime_set);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final Todoitem todoitem = getItem(i);
        viewHolder.itemName.setText(todoitem.getTodoitemName());
        viewHolder.itemDescription.setText(todoitem.getTodoitemDesc());
        viewHolder.itemDeadline.setText(todoitem.getTodoitemDeadline());
        // viewHolder.itemcreateTime.setText(todoitem.getTodoitemCreatetime());
        switch (todoitem.getTodoitemStatus()) {
            case 0:
                viewHolder.itemStatus.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                break;
            case 1:
                viewHolder.itemStatus.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                break;
            case 2:
                viewHolder.itemStatus.setBackgroundColor(mContext.getResources().getColor(R.color.red));
                break;

        }
        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteTodoitem(todoitem.getTodoitemId());
                Intent intent = new Intent(mContext, TodoListItemsActivity.class);
                intent.putExtra("userTodolistId", todoitem.getListTodouserId());
                intent.putExtra("todolistId", todoitem.getListTodoitemId());
                mContext.startActivity(intent);
                ((TodoListItemsActivity)mContext).finish();
            }
        });

        viewHolder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, UpdateItemActivity.class);
                intent.putExtra("updateItem", todoitem);
                mContext.startActivity(intent);

            }
        });

        viewHolder.itemStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(R.string.filterStatus);
                int value = todoitem.getTodoitemStatus();

                builder.setSingleChoiceItems(statusText, value, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                saveStatus(0, mContext);
                                setStatus(todoitem, 0);
                                break;
                            case 1:
                                saveStatus(1, mContext);
                                setStatus(todoitem, 1);
                                break;
                            case 2:
                                saveStatus(2, mContext);
                                setStatus(todoitem, 2);
                                break;
                        }
                        alertDialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return view;
    }




    class ViewHolder {
        TextView itemName, itemDescription, itemDeadline, itemcreateTime;
        ImageView itemStatus, imgDelete, imgUpdate;

    }

    public void setStatus(Todoitem todoitem, int status) {

        db = new Database(mContext);
        db.updateStatus(todoitem.getListTodoitemId(), todoitem.getTodoitemId(), status);
        Intent intent = new Intent(mContext, TodoListItemsActivity.class);
        intent.putExtra("userTodolistId", todoitem.getListTodouserId());
        intent.putExtra("todolistId", todoitem.getListTodoitemId());
        mContext.startActivity(intent);
        ((TodoListItemsActivity)mContext).finish();
    }

    public void saveStatus(int status, Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("todolist", 0);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putInt("status", status);
        prefsEditor.apply();
    }

    public int getStatus(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("todolist", 0);
        return mPrefs.getInt("status", 0);
    }
}
