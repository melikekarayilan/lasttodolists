package com.melikekarayilan.lasttodolist.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.melikekarayilan.lasttodolist.Activities.ToDoListActivity;
import com.melikekarayilan.lasttodolist.Activities.UpdateListNameActivity;
import com.melikekarayilan.lasttodolist.DB.Database;
import com.melikekarayilan.lasttodolist.Model.Todoitem;
import com.melikekarayilan.lasttodolist.Model.Todolist;
import com.melikekarayilan.lasttodolist.R;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by MehmetAliATLI on 8.10.2019.
 */
public class TodoListListsAdapter extends BaseAdapter {
    private Context mContext;
    List<Todolist> todolists;
    List<Todoitem> todoitems;

    Database db;


    public TodoListListsAdapter(Context context, List<Todolist> todolists) {
        this.mContext = context;
        this.todolists = todolists;
    }


    @Override
    public int getCount() {
        return todolists.size();
    }

    @Override
    public Todolist getItem(int i) {
        return todolists.get(i);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(String.valueOf(position));
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        db = new Database(mContext);

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            view = inflater.inflate(R.layout.layout_todolistlists_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.listName = (TextView) view.findViewById(R.id.tv_list_name);
            viewHolder.delete = (ImageView) view.findViewById(R.id.img_delete);
            viewHolder.update = (ImageView) view.findViewById(R.id.img_update);
            viewHolder.export = (ImageView) view.findViewById(R.id.img_export);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final Todolist todolist = getItem(i);
        viewHolder.listName.setText(todolist.getTodolistName());
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteTodolist(todolist.getUserTodolistId(), todolist.getTodolistId());
                Intent intent = new Intent(mContext, ToDoListActivity.class);
                intent.putExtra("userId", todolist.getUserTodolistId());
                mContext.startActivity(intent);
                ((ToDoListActivity)mContext).finish();
            }
        });

        viewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpdateListNameActivity.class);
                intent.putExtra("todolist", todolist);
                mContext.startActivity(intent);


            }
        });

        viewHolder.export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    todoitems = db.getTodoitemList(todolist.getTodolistId(), todolist.getUserTodolistId());
                    PrintWriter printWriter = new PrintWriter(new File("Macintosh HD\\Users\\gazisoft\\Desktop\\todolist"));
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < todoitems.size(); i++) {
                        sb.append(todolist.getTodolistName());
                        sb.append(",");
                        sb.append(todoitems.get(i).getTodoitemName());
                        sb.append(",");
                        sb.append(todoitems.get(i).getTodoitemDesc());
                        sb.append(",");
                        sb.append("\r\n");

                    }

                    printWriter.write(sb.toString());
                    printWriter.close();
                    Toast.makeText(mContext, "The file created.", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Log.e("Hata", ex.getMessage(), ex);
                }

            }
        });

        return view;
    }

    class ViewHolder {
        TextView listName;
        ImageView delete, update, export;
    }
}
