package com.melikekarayilan.lasttodolist.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.melikekarayilan.lasttodolist.DB.Database;
import com.melikekarayilan.lasttodolist.Model.Todoitem;
import com.melikekarayilan.lasttodolist.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MehmetAliATLI on 8.10.2019.
 */
public class ItemDetailActivity extends Activity {
    @BindView(R.id.img_delete_item)
    ImageView deleteItem;
    @BindView(R.id.tv_item_name_set)
    TextView itemName;
    @BindView(R.id.tv_item_description_set)
    TextView description;
    @BindView(R.id.tv_item_deadline_set)
    TextView deadline;
    @BindView(R.id.img_status)
    ImageView imgStatus;
    @BindView(R.id.img_update_item)
    ImageView updateItem;

    int itemId, itemStatus, todolistId, userId;
    Database db;
    Todoitem todoitem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_itemlist);

        ButterKnife.bind(this);

        db = new Database(this);
        itemId = getIntent().getIntExtra("itemId", 0);
        todolistId = getIntent().getIntExtra("todolistId", 0);
        userId = getIntent().getIntExtra("userId", 0);

        deleteItem.setVisibility(View.GONE);
        updateItem.setVisibility(View.GONE);

        if (itemId != 0) {

            todoitem = db.getTodoitem(itemId, todolistId, userId);
            if (todoitem != null) {
                itemName.setText(todoitem.getTodoitemName());
                description.setText(todoitem.getTodoitemDesc());
                deadline.setText(todoitem.getTodoitemDeadline());
                itemStatus = todoitem.getTodoitemStatus();
                if (itemStatus == 0) {

                    imgStatus.setBackgroundColor(getResources().getColor(R.color.blue));
                } else if (itemStatus == 1) {

                    imgStatus.setBackgroundColor(getResources().getColor(R.color.green));
                } else if (itemStatus == 2) {

                    imgStatus.setBackgroundColor(getResources().getColor(R.color.red));
                } else Toast.makeText(this, "An error occured.", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
