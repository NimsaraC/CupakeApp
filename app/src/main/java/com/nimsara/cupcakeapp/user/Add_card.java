package com.nimsara.cupcakeapp.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.database.CardDB;

public class Add_card extends AppCompatActivity {

    EditText edtName,edtNumber,edtDate,edtCvv;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        edtName = findViewById(R.id.CName);
        edtNumber = findViewById(R.id.CNumber);
        edtDate = findViewById(R.id.CDate);
        edtCvv = findViewById(R.id.CCVV);

        btnSubmit = findViewById(R.id.CSave);
        CardDB cardDB = new CardDB(Add_card.this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String number = edtNumber.getText().toString().trim();
                String date = edtDate.getText().toString().trim();
                String cvv = edtCvv.getText().toString().trim();

                long result = cardDB.insertCard(name,number,date,cvv);

                if (result != -1) {

                    Toast.makeText(Add_card.this, "Card details saved successfully!", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(Add_card.this, "Failed to add card", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(getApplicationContext(), User_Card_Details.class);
                startActivity(intent);
            }
        });

    }
}