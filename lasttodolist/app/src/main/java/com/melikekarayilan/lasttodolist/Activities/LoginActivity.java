package com.melikekarayilan.lasttodolist.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.et_username)
    EditText userName;
    @BindView(R.id.et_password)
    EditText password;
    @BindView(R.id.tv_exist_account)
    TextView existAccount;
    @BindView(R.id.btn_login)
    Button login;

    SQLiteOpenHelper openHelper;
    Database db;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        ButterKnife.bind(this);
        db = new Database(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userName.getText().toString().length() > 0 && password.getText().toString().length() > 0) {
                    if (isValidEmail(userName.getText().toString())) {

                        Boolean checkLogin = db.checklogin(userName.getText().toString(), password.getText().toString());
                        if (checkLogin == true) {

                            Integer userId= db.getUserId(userName.getText().toString());
                            if (userId!=0){
                                Intent listIntent = new Intent(LoginActivity.this, ToDoListActivity.class);
                                listIntent.putExtra("userId",userId);
                                startActivity(listIntent);
                                finish();
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "The email and password you entered did not match.", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(LoginActivity.this, "Email does not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Enter a valid email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        existAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }


    boolean isValidEmail(String mail) {
        return !TextUtils.isEmpty(mail) && android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }
}
