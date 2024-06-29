package com.nimsara.cupcakeapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nimsara.cupcakeapp.R;

public class User_Signup extends AppCompatActivity {

    EditText email, username, password;
    Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        email = findViewById(R.id.edtemail);
        username = findViewById(R.id.usernameUP);
        password = findViewById(R.id.passwordUP);
        btnReg = findViewById(R.id.btnsignUP);

        SharedPreference preference = new SharedPreference();
        String name = preference.getString(getApplicationContext(),SharedPreference.KEY_NAME);
        boolean staus = preference.GetBoolean(getApplicationContext(),SharedPreference.KEY_STATUS);

        if (name != null){
            if (staus){

                Intent intent = new Intent(getApplicationContext(), User_Login.class);
                startActivity(intent);
                this.finish();

            }
            else {
                Intent intent = new Intent(getApplicationContext(), User_Signup.class);
                startActivity(intent);
                this.finish();
            }
        }

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference.SaveString(getApplicationContext(),username.getText().toString(),SharedPreference.KEY_NAME);
                preference.SaveString(getApplicationContext(),email.getText().toString(),SharedPreference.KEY_EMAIL);
                preference.SaveString(getApplicationContext(),password.getText().toString(),SharedPreference.KEY_PIN);
                Toast.makeText(getApplicationContext(),
                        "Signed up successfully! ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), User_Login.class);
                startActivity(intent);finish();
            }
        });

    }
}