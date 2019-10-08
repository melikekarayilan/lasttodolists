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
public class AddNewItemActivity extends Activity {

    @BindView(R.id.et_item_name_set)
    EditText nameSet;
    @BindView(R.id.et_item_description_set)
    EditText descriptionSet;
    @BindView(R.id.et_item_deadline_set)
    EditText deadlineSet;
    @BindView(R.id.tv_kaydet)
    TextView save;

    Integer todolistUserId, todolistId;
    Database db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_addnewitem);
        ButterKnife.bind(this);


        todolistId = getIntent().getIntExtra("todolistId", 0);
        todolistUserId = getIntent().getIntExtra("userTodolistId", 0);
        db = new Database(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameSet.getText().length() > 0 && descriptionSet.getText().length() > 0 && deadlineSet.getText().length() > 0 && todolistId != null && todolistUserId != null) {
                    db.insertTodoitem(nameSet.getText().toString(), descriptionSet.getText().toString(), deadlineSet.getText().toString(), todolistId, todolistUserId);
                    Intent intent = new Intent(AddNewItemActivity.this, TodoListItemsActivity.class);
                    intent.putExtra("todolistId", todolistId);
                    intent.putExtra("userTodolistId", todolistUserId);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddNewItemActivity.this, "Enter valid name, description or deadline", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
