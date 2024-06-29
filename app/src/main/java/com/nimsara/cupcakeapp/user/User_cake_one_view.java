package com.nimsara.cupcakeapp.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.database.CartDB;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class User_cake_one_view extends AppCompatActivity {

    TextView txtName, txtCategory, txtDescription, txtPrice, txtNumber;
    Button btnIncrease, btnDecrease, btnAddCart;
    ImageView btnBack, imvCake;
    TextView OneName,OneCategory,OneDescription,OnePrice,tPrice;
    int number = 1;
    double Total = 0,TTotal = 0 ;
    Bitmap pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cake_one_view);

        BottomNav();

        txtName = findViewById(R.id.txtOName);
        txtCategory = findViewById(R.id.OneCategory);
        txtDescription = findViewById(R.id.OneDescription);
        txtPrice = findViewById(R.id.OnePrice);
        txtNumber = findViewById(R.id.number);
        btnIncrease = findViewById(R.id.btnI);
        btnDecrease = findViewById(R.id.btnD);
        btnAddCart = findViewById(R.id.button3);
        btnBack = findViewById(R.id.btnback);
        tPrice = findViewById(R.id.txtTotal);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imvCake = findViewById(R.id.imvOneCake);
        OneName = findViewById(R.id.txtOName);
        OneCategory = findViewById(R.id.OneCategory);
        OneDescription = findViewById(R.id.OneDescription);
        OnePrice = findViewById(R.id.OnePrice);


        getData();

        OnePrice.setText("lkr "+ Total);
        tPrice.setText(String.valueOf("lkr "+ Total));
        txtNumber.setText(String.valueOf(number));
        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = number + 1;
                txtNumber.setText(String.valueOf(number));
                tPrice.setText("lkr "+ Total * number);
                TTotal = Total * number;
            }
        });
        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number>1){
                    number = number - 1;
                    txtNumber.setText(String.valueOf(number));
                    tPrice.setText("lkr "+ Total * number);
                    TTotal = Total * number;
                }

            }
        });


        TTotal = Total * number;
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartDB db = new CartDB(User_cake_one_view.this);
                byte[] img = getIntent().getByteArrayExtra("cover");
                db.addCart(getApplicationContext(),OneName.getText().toString(),OneCategory.getText().toString(),number,Total,img);
            }
        });
    }

    void getData(){
        if(getIntent().hasExtra("id")&&getIntent().hasExtra("name")){
            OneName.setText(getIntent().getStringExtra("name"));
            OnePrice.setText(String.valueOf(getIntent().getDoubleExtra("price",-1)));
            Total = getIntent().getDoubleExtra("price",-1);
            OneDescription.setText(getIntent().getStringExtra("description"));
            OneCategory.setText(getIntent().getStringExtra("category"));
            byte[] img = getIntent().getByteArrayExtra("cover");
                           Bitmap bitmap = BitmapFactory.decodeByteArray(img,0, img.length);
            imvCake.setImageBitmap(bitmap);
        }
    }
    public void BottomNav(){
        final int home=1;
        final int dash=2;
        final int notifications=3;
        @SuppressLint({"MissingInflatedId","LocalSuppress"})
        MeowBottomNavigation bottomNavigation=findViewById(R.id.bottomNav);
        bottomNavigation.add(new MeowBottomNavigation.Model(home, R.drawable.store));
        bottomNavigation.add(new MeowBottomNavigation.Model(dash, R.drawable.cardt));
        bottomNavigation.add(new MeowBottomNavigation.Model(notifications, R.drawable.order));

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                int id = model.getId();
                if (id==1){
                    Intent intent = new Intent(getApplicationContext(), User_Home.class);
                    startActivity(intent);
                }
                if (id==2){
                    Intent intent = new Intent(getApplicationContext(), User_cart.class);
                    startActivity(intent);
                }
                if (id==3){
                    Intent intent = new Intent(getApplicationContext(), User_Orders.class);
                    startActivity(intent);
                }
                return null;
            }
        });
    }
}