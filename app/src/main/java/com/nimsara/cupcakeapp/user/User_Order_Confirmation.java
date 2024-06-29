package com.nimsara.cupcakeapp.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.adapter.User_OrderC_Adapter;
import com.nimsara.cupcakeapp.database.CartDB;
import com.nimsara.cupcakeapp.database.OrdersDB;
import com.nimsara.cupcakeapp.login.SharedPreference;

import java.util.ArrayList;

public class User_Order_Confirmation extends AppCompatActivity {
    RecyclerView items;
    ArrayList<String> name,price,quantity;
    ArrayList<Bitmap> cover;
    CartDB cartDB;
    User_OrderC_Adapter adapter;
    TextView STotal,OCName,OCPhone,OCStreet,OCCity,OCPostal,OCCNumber,OCCDate;
    Button OCSelect,OCCSelect,PayNow;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_confirmation);

        OCName = findViewById(R.id.OCName);
        OCPhone = findViewById(R.id.OCPhone);
        OCStreet = findViewById(R.id.OCStreet);
        OCCity = findViewById(R.id.OCCity);
        OCPostal = findViewById(R.id.OCPostal);
        OCCNumber = findViewById(R.id.OCCNumber);
        OCCDate = findViewById(R.id.OCCDate);
        OCSelect = findViewById(R.id.OCSelect);
        OCCSelect = findViewById(R.id.OCCSelect);
        PayNow = findViewById(R.id.OCPay);

        items = findViewById(R.id.cartItem);
        STotal = findViewById(R.id.STotal);
        cartDB = new CartDB(User_Order_Confirmation.this);
        name = new ArrayList<>();
        price = new ArrayList<>();
        cover = new ArrayList<>();
        quantity = new ArrayList<>();

        displayData();
        setAddress();
        setCard();
        adapter = new User_OrderC_Adapter(User_Order_Confirmation.this,name,price,quantity,cover);
        items.setAdapter(adapter);
        items.setLayoutManager(new LinearLayoutManager(User_Order_Confirmation.this));

        double subtotal = calculateSubtotal();
        STotal.setText(String.format("lkr%.2f ", subtotal));

        OCSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), User_Shipping_address.class);
                startActivity(intent);
            }
        });

        OCCSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), User_Card_Details.class);
                startActivity(intent);
                setCard();
            }
        });

        PayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double subtotal = calculateSubtotal();

                String name = OCName.getText().toString();
                String address = OCStreet.getText().toString() + ", " +
                        OCCity.getText().toString() + ", " +
                        OCPostal.getText().toString();
                String number = OCPhone.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(User_Order_Confirmation.this);
                builder.setTitle("Confirm Payment");
                builder.setMessage("Are you sure you want to proceed with the payment and place the order?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Cursor cursor = cartDB.allData();
                        if (cursor != null && cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                String itemName = cursor.getString(1);
                                int itemQuantity = Integer.parseInt(cursor.getString(3));
                                double itemTotal = Double.parseDouble(cursor.getString(4));


                                OrdersDB ordersDB = new OrdersDB(User_Order_Confirmation.this);
                                long result = ordersDB.addOrder(name, address, number, itemName, itemQuantity, itemTotal, itemTotal);

                                if (result != -1) {
                                    Toast.makeText(User_Order_Confirmation.this, "Order item \"" + itemName + "\" placed successfully!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(User_Order_Confirmation.this, "Failed to place order item \"" + itemName + "\"!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            cursor.close();

                            cartDB.deleteAllData();

                            Intent intent = new Intent(User_Order_Confirmation.this, User_Home.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(User_Order_Confirmation.this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
        }
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
    void setAddress(){

        SharedPreference preference = new SharedPreference();

        String name = preference.getString(getApplicationContext(), SharedPreference.NAME);
        String phone = preference.getString(getApplicationContext(),SharedPreference.PHONE);
        String street = preference.getString(getApplicationContext(),SharedPreference.STREET);
        String city = preference.getString(getApplicationContext(),SharedPreference.CITY);
        String zip = preference.getString(getApplicationContext(),SharedPreference.ZIP);

        OCName.setText(name);
        OCPhone.setText(phone);
        OCStreet.setText(street);
        OCCity.setText(city);
        OCPostal.setText(zip);
    }
    void setCard(){
        String number = getIntent().getStringExtra("cnumber");
        String date = getIntent().getStringExtra("date");

        OCCNumber.setText(number);
        OCCDate.setText(date);
    }
}
