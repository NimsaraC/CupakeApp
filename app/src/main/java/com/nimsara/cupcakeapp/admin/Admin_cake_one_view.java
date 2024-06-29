package com.nimsara.cupcakeapp.admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.nimsara.cupcakeapp.CupCake;
import com.nimsara.cupcakeapp.R;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Admin_cake_one_view extends AppCompatActivity {


    ImageView CakeImg, BackBtn;
    TextView Name,Category,Description,EditBtn,DeleteBtn,price;
    private SQLiteDatabase CakeDB;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cake_one_view);

        CakeImg = findViewById(R.id.imvOneCakeAdmin);
        Name = findViewById(R.id.txtONameAdmin);
        Category = findViewById(R.id.OneCategoryAdmin);
        Description = findViewById(R.id.OneDescriptionAdmin);
        BackBtn = findViewById(R.id.btnback);
        price = findViewById(R.id.price);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SetCakeDB();
        BottomNav();
        getData();
        sendData();

    }

    public void BottomNav(){
         final int home=1;
         final int dash=2;
         final int notifications=3;
        @SuppressLint({"MissingInflatedId","LocalSuppress"})
        MeowBottomNavigation bottomNavigation=findViewById(R.id.bottomNav);
        bottomNavigation.add(new MeowBottomNavigation.Model(home,R.drawable.adminhome));
        bottomNavigation.add(new MeowBottomNavigation.Model(dash, R.drawable.orders));
        bottomNavigation.add(new MeowBottomNavigation.Model(notifications, R.drawable.cakes));

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                int id = model.getId();
                if (id==1){
                    Intent intent = new Intent(getApplicationContext(), Admin_Menu.class);
                    startActivity(intent);
                }
                if (id==2){
                    Intent intent = new Intent(getApplicationContext(), Admin_Order_View.class);
                    startActivity(intent);
                }
                if (id==3){
                    Intent intent = new Intent(getApplicationContext(), Admin_Cake_List.class);
                    startActivity(intent);
                }
                Toast.makeText(Admin_cake_one_view.this,"Item Click"+model.getId(),Toast.LENGTH_LONG).show();
                return null;
            }
        });
    }
    byte[] img;
    void getData(){
        if(getIntent().hasExtra("id")&&getIntent().hasExtra("name")){
            Name.setText(getIntent().getStringExtra("name"));
            Description.setText(getIntent().getStringExtra("description"));
            Category.setText(getIntent().getStringExtra("category"));
            price.setText(getIntent().getStringExtra("price"));
             img = getIntent().getByteArrayExtra("cover");
            Bitmap bitmap = BitmapFactory.decodeByteArray(img,0, img.length);
            CakeImg.setImageBitmap(bitmap);
        }
    }

    public void sendData(){
        EditBtn = findViewById(R.id.AdminEdit);

        EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (price != null) {
                    Intent intent = new Intent(Admin_cake_one_view.this, Admin_edit_cake.class);
                    intent.putExtra("name", Name.getText().toString());
                    intent.putExtra("description", Description.getText().toString());
                    intent.putExtra("price", price.getText().toString());
                    intent.putExtra("cover",img);
                    startActivity(intent);
                }
            }
        });

        DeleteBtn = findViewById(R.id.AdminDelete);
        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Admin_cake_one_view.this);
                builder.setMessage("Are you sure you want to Delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CupCake cupCakeToDelete = new CupCake();
                                cupCakeToDelete.delete(CakeDB, Name.getText().toString());
                                Toast.makeText(Admin_cake_one_view.this, "Cake Deleted successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Admin_cake_one_view.this,Admin_Cake_List.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private void SetCakeDB() {
        try {
            CakeDB=openOrCreateDatabase("CakeData",MODE_PRIVATE,null);
            CakeDB.execSQL("Create table if not exists cake(id integer primary key autoincrement,name text,description text,category text,price real,cover blob)");
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

}