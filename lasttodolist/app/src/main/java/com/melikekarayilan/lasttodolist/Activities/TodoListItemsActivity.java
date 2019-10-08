package com.melikekarayilan.lasttodolist.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.melikekarayilan.lasttodolist.Adapters.TodoListItemsAdapter;
import com.melikekarayilan.lasttodolist.DB.Database;
import com.melikekarayilan.lasttodolist.Model.Todoitem;
import com.melikekarayilan.lasttodolist.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by MehmetAliATLI on 8.10.2019.
 */
public class TodoListItemsActivity extends Activity {

    @BindView(R.id.tv_filtreleme)
    TextView filtrele;
    @BindView(R.id.liste_spinner)
    Spinner filtreleItem;
    @BindView(R.id.lv_list_items)
    ListView itemsList;
    @BindView(R.id.tv_add_new_item)
    TextView addNewItem;
    @BindView(R.id.et_search)
    EditText search;

    Database db;
    Integer userTodolistId, todolistId;
    List<Todoitem> todoitemList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_items);
        ButterKnife.bind(this);

        db = new Database(this);
        userTodolistId = getIntent().getIntExtra("userTodolistId", 0);
        todolistId = getIntent().getIntExtra("todolistId", 0);

        todoitemList = db.getTodoitemList(todolistId, userTodolistId);


        if (todoitemList.size() != 0) {
            itemsList.setAdapter(new TodoListItemsAdapter(this, todoitemList));
        } else {
            Toast.makeText(this, "liste boş", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.listemeTipleri, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filtreleItem.setAdapter(adapter);
        filtreleItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                text.equals("");
                if (text.equals("All lists")) {

                    saveStatus(0, TodoListItemsActivity.this);
                    setItemlist(userTodolistId, todolistId, 0);

                } else if (text.equals("Incompleted List")) {

                    saveStatus(1, TodoListItemsActivity.this);
                    setItemlist(userTodolistId, todolistId, 1);
                } else if (text.equals("Completed List")) {

                    saveStatus(2, TodoListItemsActivity.this);
                    setItemlist(userTodolistId, todolistId, 2);
                } else if (text.equals("Expired List")) {

                    saveStatus(3, TodoListItemsActivity.this);
                    setItemlist(userTodolistId, todolistId, 3);
                } else if (text.equals("Name")) {
                    saveStatus(4, TodoListItemsActivity.this);
                    setItemlist(userTodolistId, todolistId, 4);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TodoListItemsActivity.this, ItemDetailActivity.class);
                intent.putExtra("itemId", todoitemList.get(position).getTodoitemId());
                intent.putExtra("todolistId", todolistId);
                intent.putExtra("userId", userTodolistId);
                startActivity(intent);

            }
        });

        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TodoListItemsActivity.this, AddNewItemActivity.class);
                intent.putExtra("todolistId", todolistId);
                intent.putExtra("userTodolistId", userTodolistId);
                startActivity(intent);

            }
        });

        initSearchEdittextListener(userTodolistId, todolistId);
    }

    public void initSearchEdittextListener(final int userId, final int listId) {
        db = new Database(this);
        RxTextView.afterTextChangeEvents(search)
                .debounce(500, MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .skip(1)
                .subscribe(new io.reactivex.Observer<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onNext(TextViewAfterTextChangeEvent value) {
                        if (value.editable().length() == 0) {
                            todoitemList = db.getTodoitemList(listId, userId);
                            setAdapterforsearch(todoitemList);

                        } else if (value.editable().length() > 1) {
                            todoitemList = db.getTodoitemListWithName(listId, userId, search.getText().toString());
                            setAdapterforsearch(todoitemList);
                        }
                    }
                });
    }

    public void setAdapterforsearch(List<Todoitem> todoitems) {
        itemsList.setAdapter(new TodoListItemsAdapter(this, todoitems));
        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TodoListItemsActivity.this, ItemDetailActivity.class);
                intent.putExtra("itemId", todoitemList.get(position).getTodoitemId());
                intent.putExtra("todolistId", todoitemList.get(position).getListTodoitemId());
                intent.putExtra("userId", todoitemList.get(position).getListTodouserId());
                startActivity(intent);
            }
        });


    }

    public void setItemlist(final int userId, final int listId, int status) {
        db = new Database(this);
        if (status == 0) {
            todoitemList = db.getTodoitemList(listId, userId);
            if (todoitemList.size() > 0) {
                itemsList.setAdapter(new TodoListItemsAdapter(this, todoitemList));
                itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(TodoListItemsActivity.this, ItemDetailActivity.class);
                        intent.putExtra("itemId", todoitemList.get(position).getTodoitemId());
                        intent.putExtra("todolistId", listId);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(this, "The list you were looking for could not be found.", Toast.LENGTH_SHORT).show();
            }

        } else if (status == 4) {
            todoitemList = db.getTodoitemList(listId, userId);
            if (todoitemList.size() > 0) {

                Collections.sort(todoitemList, new Comparator<Todoitem>() {
                    @Override
                    public int compare(Todoitem item1, Todoitem item2) {
                        return item1.getTodoitemName().compareTo(item2.getTodoitemName());
                    }
                });

                itemsList.setAdapter(new TodoListItemsAdapter(this, todoitemList));
                itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(TodoListItemsActivity.this, ItemDetailActivity.class);
                        intent.putExtra("itemId", todoitemList.get(position).getTodoitemId());
                        intent.putExtra("todolistId", listId);
                        intent.putExtra("userId", userId);
                        startActivity(intent);

                    }
                });
            }

        } else {
            int newStatus = status - 1;
            todoitemList = db.getItemlistwithStatus(userId, listId, newStatus);
            if (todoitemList.size() > 0) {
                itemsList.setAdapter(new TodoListItemsAdapter(this, todoitemList));
                itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(TodoListItemsActivity.this, ItemDetailActivity.class);
                        intent.putExtra("itemId", todoitemList.get(position).getTodoitemId());
                        intent.putExtra("todolistId", listId);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(this, "The list you were looking for could not be found.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void saveStatus(int filterType, Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("todolist", 0);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putInt("filterType", filterType);
        prefsEditor.apply();
    }

    public int getStatus(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("todolist", 0);
        return mPrefs.getInt("filterType", 0);
    }

}
