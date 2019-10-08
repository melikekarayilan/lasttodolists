package com.melikekarayilan.lasttodolist.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.melikekarayilan.lasttodolist.Adapters.TodoListListsAdapter;
import com.melikekarayilan.lasttodolist.DB.Database;
import com.melikekarayilan.lasttodolist.Model.Todolist;
import com.melikekarayilan.lasttodolist.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MehmetAliATLI on 8.10.2019.
 */
public class ToDoListActivity extends Activity {

    @BindView(R.id.lv_todo_list)
    ListView todoList;
    @BindView(R.id.tv_create_todolist)
    TextView createNewList;
    @BindView(R.id.img_logout)
    ImageView logout;
    @BindView(R.id.img_add_new_list)
    ImageView addnewlist;

    Integer userId;
    Database db;
    List<Todolist> gettodolists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_todolist);

        ButterKnife.bind(this);
        db = new Database(this);

        userId = getIntent().getIntExtra("userId", 0);
        gettodolists = db.getTodolist(userId);

        if (gettodolists.size() != 0) {

            todoList.setAdapter(new TodoListListsAdapter(this, gettodolists));
            todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Todolist clickTodolist = (Todolist) parent.getItemAtPosition(position);

                    Intent intent = new Intent(ToDoListActivity.this, TodoListItemsActivity.class);
                    intent.putExtra("userTodolistId", clickTodolist.getUserTodolistId());
                    intent.putExtra("todolistId", clickTodolist.getTodolistId());
                    startActivity(intent);
                }
            });
        } else {
            Toast.makeText(this, "You do not have a created list.", Toast.LENGTH_SHORT).show();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoListActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addnewlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoListActivity.this, AddNewTodolist.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();

            }
        });

    }
}
