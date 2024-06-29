package com.nimsara.cupcakeapp.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.adapter.User_Cart_Adapter;
import com.nimsara.cupcakeapp.database.CartDB;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class User_cart extends AppCompatActivity {
    RecyclerView cart;
    CartDB cartDB;
    ArrayList<String> name,price,quantity;
    ArrayList<Bitmap> cover;
    User_Cart_Adapter adapter;
    TextView SubTotal;
    Button Checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);

        SubTotal = findViewById(R.id.SubTotal);
        Checkout = findViewById(R.id.Checkout);

        cart = findViewById(R.id.cartList);
        cartDB = new CartDB(User_cart.this);
        name = new ArrayList<>();
        price = new ArrayList<>();
        cover = new ArrayList<>();
        quantity = new ArrayList<>();

        displayData();
        adapter = new User_Cart_Adapter(User_cart.this,name,price,quantity,cover);
        cart.setAdapter(adapter);
        cart.setLayoutManager(new LinearLayoutManager(User_cart.this));

        Checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),User_Order_Confirmation.class);
                startActivity(intent);
            }
        });

    }

    void displayData(){
        Cursor cursor = cartDB.allData();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                name.add(cursor.getString(1));
                quantity.add(cursor.getString(3));
                price.add(cursor.getString(4));
                byte[] coverData = cursor.getBlob(5);
                Bitmap bitmap = BitmapFactory.decodeByteArray(coverData, 0, coverData.length);
                cover.add(bitmap);

            }
            double subtotal = calculateSubtotal();
            updateSubtotal(subtotal);
        }
        BottomNav();
    }

    private double calculateSubtotal() {
        double subtotal = 0.0;
        for (int i = 0; i < quantity.size(); i++) {
            double itemPrice = Double.parseDouble(price.get(i));
            int itemQuantity = Integer.parseInt(quantity.get(i));
            subtotal += itemPrice * itemQuantity;
        }
        return subtotal;
    }

    public void updateSubtotal(double subtotal) {
        SubTotal.setText(String.format("lkr%.2f ", subtotal));
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