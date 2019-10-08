package com.melikekarayilan.lasttodolist.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.melikekarayilan.lasttodolist.DB.Database;
import com.melikekarayilan.lasttodolist.Model.Todoitem;
import com.melikekarayilan.lasttodolist.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MehmetAliATLI on 8.10.2019.
 */
public class UpdateItemActivity extends Activity {

    @BindView(R.id.et_item_name_set)
    EditText itemNameSet;
    @BindView(R.id.et_item_description_set)
    EditText itemDescSet;
    @BindView(R.id.et_item_deadline_set)
    EditText itemDeadlineSet;
    @BindView(R.id.tv_kaydet)
    TextView update;

    Todoitem todoitem;
    Database db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_addnewitem);
        ButterKnife.bind(this);

        update.setText("Update");

        db = new Database(this);

        todoitem = getIntent().getParcelableExtra("updateItem");

        if (todoitem != null) {
            itemNameSet.setText(todoitem.getTodoitemName());
            itemDescSet.setText(todoitem.getTodoitemDesc());
            itemDeadlineSet.setText(todoitem.getTodoitemDeadline());
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateItem(itemNameSet.getText().toString(),itemDescSet.getText().toString(),itemDeadlineSet.getText().toString(),todoitem.getListTodouserId(),todoitem.getListTodoitemId(),todoitem.getTodoitemId());
                Intent intent = new Intent(UpdateItemActivity.this, TodoListItemsActivity.class);
                intent.putExtra("userTodolistId", todoitem.getListTodouserId());
                intent.putExtra("todolistId", todoitem.getListTodoitemId());
                startActivity(intent);
                finish();
            }
        });


    }
}
