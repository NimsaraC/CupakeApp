package com.nimsara.cupcakeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Menu_bar extends AppCompatActivity {

    protected final int home=1;
    protected final int dash=2;
    protected final int notifications=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_bar);
        @SuppressLint({"MissingInflatedId","LocalSuppress"})
        MeowBottomNavigation bottomNavigation=findViewById(R.id.bottomNav);
        bottomNavigation.add(new MeowBottomNavigation.Model(home,R.drawable.baseline_apps_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(dash,R.drawable.baseline_apps_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(notifications,R.drawable.baseline_apps_24));

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                Toast.makeText(Menu_bar.this,"Item Click"+model.getId(),Toast.LENGTH_LONG).show();
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                String name;
                switch (model.getId()){
                    case home:name="Home";
                    break;
                    case dash:name="Dashboard";
                    break;
                    case notifications:name="Notification";
                    break;
                }
                bottomNavigation.setCount(notifications,"9");
                return null;
            }
        });
    }
}