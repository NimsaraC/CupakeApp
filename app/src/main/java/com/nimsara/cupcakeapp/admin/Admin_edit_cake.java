package com.nimsara.cupcakeapp.admin;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nimsara.cupcakeapp.R;

import java.util.ArrayList;
import java.util.List;

public class Admin_edit_cake extends AppCompatActivity {

    EditText txtCakeName, txtCakeDescription, txtCakePrice,txtCakeCategory ;
    Button btnBrows, btnAdd, btnView;
    ImageView imvCake;
    Spinner spinner;
    Bitmap pic;
    ArrayAdapter<String> adapter;
    private ArrayList<String> categoryNames = new ArrayList<>();
    private SQLiteDatabase CakeDB,CatDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_cake);

        txtCakeName = findViewById(R.id.edtACName);
        txtCakeDescription = findViewById(R.id.edtACDescription);
        txtCakePrice = findViewById(R.id.edtACPrice);
        btnBrows = findViewById(R.id.btnACIAdd);
        btnAdd = findViewById(R.id.btnACAdd);
        btnView = findViewById(R.id.btnACView);
        imvCake = findViewById(R.id.imvACAView);
        spinner = findViewById(R.id.spinner);

        SetCakeDB();
        SetCatDB();
        SelectData();
        getData();
    }

    public void SelectData(){

        ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                Intent intent = o.getData();
                pic = (Bitmap) intent.getExtras().get("data");
                imvCake.setImageBitmap(pic);
            }
        });
        ActivityResultLauncher launcher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                Intent intent = o.getData();
                Uri uri = intent.getData();
                imvCake.setImageURI(uri);
            }
        });

        btnBrows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(btnAdd.getContext());
                builder.setMessage("Please select the option you wish to use !");
                builder.setTitle("Select cake image");
                builder.setPositiveButton("Use the camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        launcher.launch(intent);
                    }
                });
                builder.setNegativeButton("Use the gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/");
                        launcher2.launch(intent);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Admin_edit_cake.this);
                builder.setTitle("Confirm Edit");
                builder.setMessage("Are you sure you want to edit this cake?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name1 = getIntent().getStringExtra("name");

                        String newName = txtCakeName.getText().toString().trim();
                        String newDescription = txtCakeDescription.getText().toString().trim();
                        String newPrice = txtCakePrice.getText().toString().trim();
                        String selectedCategory = spinner.getSelectedItem().toString();

                        ContentValues values = new ContentValues();
                        values.put("name", newName);
                        values.put("description", newDescription);
                        values.put("price", newPrice);
                        values.put("category", selectedCategory);
                        values.put("cover", img);

                        int rowsAffected = CakeDB.update("cake", values, "name = ?", new String[]{name1});

                        if (rowsAffected > 0) {
                            Toast.makeText(Admin_edit_cake.this, "Cake updated successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Admin_edit_cake.this, Admin_Cake_List.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Admin_edit_cake.this, "Failed to update cake", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_edit_cake.this, Admin_Cake_List.class);
                startActivity(intent);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(Admin_edit_cake.this,"Selected Category" + item,Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
        updateSpinner();

    }

    byte[] img;
    public void getData(){
        if(getIntent().hasExtra("name")){
            txtCakeName.setText(getIntent().getStringExtra("name"));
            txtCakeDescription.setText(getIntent().getStringExtra("description"));
            txtCakePrice.setText(getIntent().getStringExtra("price"));
            img = getIntent().getByteArrayExtra("cover");
            Bitmap bitmap = BitmapFactory.decodeByteArray(img,0, img.length);
            imvCake.setImageBitmap(bitmap);
        }
    }
    static List<String> getCategoryNames(SQLiteDatabase CatDB) {
        try {
            List<String> categoryNames = new ArrayList<>();
            String query = "SELECT name FROM Cat";
            Cursor cursor = CatDB.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    String categoryName = cursor.getString(0);
                    categoryNames.add(categoryName);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return categoryNames;
        } catch (Exception ex) {
            throw ex;
        }
    }
    private void updateSpinner() {
        categoryNames.clear();
        categoryNames.addAll(getCategoryNames(CatDB));
        adapter.notifyDataSetChanged();
    }
    private void SetCakeDB() {
        try {
            CakeDB=openOrCreateDatabase("CakeData",MODE_PRIVATE,null);
            CakeDB.execSQL("Create table if not exists cake(id integer primary key autoincrement,name text,description text,category text,price real,cover blob)");
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private void SetCatDB() {
        try {
            CatDB=openOrCreateDatabase("CategoryDB",MODE_PRIVATE,null);
            CatDB.execSQL("Create table if not exists" +
                    " Cat(id integer primary key autoincrement" +
                    ",name text,description text,cover blob)");
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

}