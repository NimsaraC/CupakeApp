package com.nimsara.cupcakeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nimsara.cupcakeapp.admin.Admin_add_cake;
import com.nimsara.cupcakeapp.login.User_Login;
import com.nimsara.cupcakeapp.user.User_Home;

public class MainActivity extends AppCompatActivity {

    Button btnEnter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnter = findViewById(R.id.btnEnter);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), User_Login.class);
                startActivity(intent);
            }
        });
    }
}