package com.nimsara.cupcakeapp.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.nimsara.cupcakeapp.R;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Admin_Menu extends AppCompatActivity {

    LinearLayout ACake, ACategory, AOrders, ASales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        ACake = findViewById(R.id.AdminCake);
        ACategory = findViewById(R.id.AdminCategory);
        AOrders = findViewById(R.id.AdminOrders);

        ACake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Admin_Cake_List.class);
                startActivity(intent);
            }
        });

        ACategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Admin_Category_List.class);
                startActivity(intent);
            }
        });

        BottomNav();
        AOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Admin_Order_View.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to exit?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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
                return null;
            }
        });
    }
}