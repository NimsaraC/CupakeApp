package com.nimsara.cupcakeapp.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.admin.Admin_Menu;
import com.nimsara.cupcakeapp.user.User_Home;

public class User_Login extends AppCompatActivity {
    Button btnsignup, btnlogin;
    EditText Uname, Password;
    TextView txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        Uname = findViewById(R.id.username);
        Password = findViewById(R.id.edtemail);
        txtPass = findViewById(R.id.txtPass);

        btnsignup = findViewById(R.id.btnsignup);
        btnlogin = findViewById(R.id.btnlogin);
        SharedPreference preference= new SharedPreference();
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), User_Signup.class);
                startActivity(intent);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass = preference.getString(getApplicationContext(),SharedPreference.KEY_PIN);
                String uname = preference.getString(getApplicationContext(),SharedPreference.KEY_NAME);

                if (Uname.getText().toString().equals("Admin") && Password.getText().toString().equals("1234")) {
                    Toast.makeText(getApplicationContext(),
                            " Admin Account Login successfully!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Admin_Menu.class);
                    startActivity(intent);
                    finish();
                }else {
                    if (Uname.getText().toString().equals(uname)){
                        txtPass.setText("Enter Password");
                        if (Password.getText().toString().equals(pass)){
                            preference.SaveBoolean(getApplicationContext(),true,SharedPreference.KEY_STATUS);
                            Intent intent = new Intent(getApplicationContext(), User_Home.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Login successfully", Toast.LENGTH_LONG).show();
                        } else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(btnlogin.getContext());
                            builder.setMessage("Please enter correct login information !");
                            builder.setTitle("Incorrect Password");
                            builder.setNeutralButton("Try again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getApplicationContext(), User_Login.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            AlertDialog dialog =  builder.create();
                            dialog.show();
                        }
                    }  else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(btnlogin.getContext());
                        builder.setMessage("Please enter correct login information !");
                        builder.setTitle("Incorrect Username");
                        builder.setNeutralButton("Try again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), User_Login.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        AlertDialog dialog =  builder.create();
                        dialog.show();
                    }
                }
            }
        });
    }
}