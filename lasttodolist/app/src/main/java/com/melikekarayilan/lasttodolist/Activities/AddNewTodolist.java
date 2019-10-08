package com.melikekarayilan.lasttodolist.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.melikekarayilan.lasttodolist.DB.Database;
import com.melikekarayilan.lasttodolist.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MehmetAliATLI on 8.10.2019.
 */
public class AddNewTodolist extends Activity {

    @BindView(R.id.et_item_name_set)
    EditText nameSet;
    @BindView(R.id.tv_kaydet)
    TextView save;

    Integer userId;
    Database db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_addnewlist);
        ButterKnife.bind(this);

        userId = getIntent().getIntExtra("userId", 0);
        db = new Database(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameSet.getText().toString().length() > 0 && userId != null) {
                    db.insertTodolist(nameSet.getText().toString(), userId);
                    Intent intent = new Intent(AddNewTodolist.this, ToDoListActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddNewTodolist.this, "Enter a valid name", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
