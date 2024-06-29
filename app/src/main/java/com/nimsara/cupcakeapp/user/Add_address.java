package com.nimsara.cupcakeapp.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.login.SharedPreference;

public class Add_address extends AppCompatActivity {

    EditText txtname,txtemail,txtphone,txtstreet,txtcity,txtpostal;
    Button btnsubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        txtname = findViewById(R.id.UName);
        txtemail = findViewById(R.id.UEmail);
        txtphone = findViewById(R.id.UPhone);
        txtstreet = findViewById(R.id.UStreet);
        txtcity = findViewById(R.id.UCity);
        txtpostal = findViewById(R.id.UCode);

        btnsubmit = findViewById(R.id.ASubmit);
//        AddressDB addressDB = new AddressDB(this);

        SharedPreference preference = new SharedPreference();

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preference.SaveString(getApplicationContext(),txtname.getText().toString(),SharedPreference.NAME);
                preference.SaveString(getApplicationContext(),txtphone.getText().toString(),SharedPreference.PHONE);
                preference.SaveString(getApplicationContext(),txtstreet.getText().toString(),SharedPreference.STREET);
                preference.SaveString(getApplicationContext(),txtcity.getText().toString(),SharedPreference.CITY);
                preference.SaveString(getApplicationContext(),txtpostal.getText().toString(),SharedPreference.ZIP);

                Toast.makeText(Add_address.this, "Address added successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), User_Order_Confirmation.class);
                startActivity(intent);
                finish();
            }
        });
    }
}