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
import android.widget.Toast;

import com.melikekarayilan.lasttodolist.DB.Database;
import com.melikekarayilan.lasttodolist.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MehmetAliATLI on 8.10.2019.
 */
public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText userName;
    @BindView(R.id.et_password)
    EditText password;
    @BindView(R.id.et_password_again)
    EditText passwordAgain;
    @BindView(R.id.btn_login)
    Button register;

    SQLiteOpenHelper sqLiteOpenHelper;
    Database db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        ButterKnife.bind(this);

        db = new Database(this);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userName.getText().toString().length() > 0 && password.getText().toString().length() > 0 && passwordAgain.getText().toString().length() > 0) {

                    if (isValidEmail(userName.getText().toString())) {
                        if (password.getText().toString().equals(passwordAgain.getText().toString())) {

                            Boolean checkmail = db.checkmail(userName.getText().toString());
                            if (checkmail == true) {
                                Boolean insertUser = db.insertUser(userName.getText().toString(), password.getText().toString());
                                if (insertUser == true) {

                                    Toast.makeText(RegisterActivity.this, "Registered successfully.", Toast.LENGTH_SHORT).show();
                                    Intent listIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(listIntent);
                                    finish();

                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Email already exist.", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(RegisterActivity.this, "Both passwords must be same.", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(RegisterActivity.this, "Enter a valid email.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Enter a valid email and password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    boolean isValidEmail(String mail) {
        return !TextUtils.isEmpty(mail) && android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }
}
