package com.nimsara.cupcakeapp.user;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.adapter.Card_Adapter;
import com.nimsara.cupcakeapp.database.CardDB;

import java.util.ArrayList;

public class User_Card_Details extends AppCompatActivity {
    CardDB cardDB;
    ArrayList<String> CName,CDate,CNumber,CCvv;
    LinearLayout addButton;
    Context context;
    Card_Adapter adapter;
    RecyclerView CardList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_card_details);
        addButton = findViewById(R.id.addCardBtn);
        CardList = findViewById(R.id.CardList);
        context = this;

        cardDB = new CardDB(User_Card_Details.this);
        CName = new ArrayList<>();
        CDate = new ArrayList<>();
        CNumber = new ArrayList<>();
        CCvv = new ArrayList<>();

        displayCard();

        adapter = new Card_Adapter(User_Card_Details.this,CName,CDate,CNumber,CCvv);
        CardList.setAdapter(adapter);
        CardList.setLayoutManager(new LinearLayoutManager(User_Card_Details.this));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Card_Details.this, Add_card.class);
                startActivity(intent);
            }
        });

    }

    void displayCard(){
        Cursor cursor = cardDB.getAllCard();
        if (cursor.getCount() == 0){
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                CName.add(cursor.getString(1));
                CNumber.add(cursor.getString(2));
                CDate.add(cursor.getString(3));
                CCvv.add(cursor.getString(4));
            }
        }
    }
}