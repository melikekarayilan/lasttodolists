package com.melikekarayilan.lasttodolist.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.melikekarayilan.lasttodolist.DB.Database;
import com.melikekarayilan.lasttodolist.Model.Todolist;
import com.melikekarayilan.lasttodolist.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MehmetAliATLI on 8.10.2019.
 */
public class UpdateListNameActivity extends Activity {

    @BindView(R.id.tv_kaydet)
    TextView update;
    @BindView(R.id.et_item_name_set)
    EditText listName;

    Database db;
    Todolist todolist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_addnewlist);

        ButterKnife.bind(this);
        db = new Database(this);
        update.setText("Update");

        todolist = getIntent().getParcelableExtra("todolist");

        if (todolist != null) {
            listName.setText(todolist.getTodolistName());
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateListName(listName.getText().toString(), todolist.getUserTodolistId(), todolist.getTodolistId());
                Intent intent = new Intent(UpdateListNameActivity.this, ToDoListActivity.class);
                intent.putExtra("userId", todolist.getUserTodolistId());
                startActivity(intent);
                finish();
            }
        });
    }
}
