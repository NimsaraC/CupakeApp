package com.nimsara.cupcakeapp.admin;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nimsara.cupcakeapp.Categories;
import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.adapter.Admin_category_adapter;

import java.util.List;

public class Admin_Category_List extends AppCompatActivity {

    LinearLayout AddCategory;
    SQLiteDatabase CatDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category_list);
        RecyclerView recyclerView = findViewById(R.id.itemCat);
        setCatDB();

        Categories categories = new Categories();
        List<Categories> categories1 = categories.GetCat(CatDB);
        Admin_category_adapter adapter = new Admin_category_adapter(Admin_Category_List.this,CatDB,categories1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        AddCategory = findViewById(R.id.AddItem);
        AddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Admin_add_category.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setCatDB(){
        try{
            CatDB = openOrCreateDatabase("CategoryDB",MODE_PRIVATE,null);
            CatDB.execSQL("create table if not exists Cat(id integer primary key autoincrement,name text,description text,cover blob)");
        }catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}