package com.nimsara.cupcakeapp.admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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

import com.nimsara.cupcakeapp.CupCake;
import com.nimsara.cupcakeapp.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Admin_add_cake extends AppCompatActivity {

    EditText txtCakeName, txtCakeDescription, txtCakePrice,txtCakeCategory ;
    Button btnBrows, btnAdd, btnView;
    ImageView imvCake;
    Bitmap pic;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    private Context context;
    private SQLiteDatabase CakeDB,CatDB;
    private ArrayList<String> categoryNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_cake);

        txtCakeName = findViewById(R.id.edtACName);
        txtCakeDescription = findViewById(R.id.edtACDescription);
        txtCakePrice = findViewById(R.id.edtACPrice);
        btnBrows = findViewById(R.id.btnACIAdd);
        btnAdd = findViewById(R.id.btnACAdd);
        btnView = findViewById(R.id.btnACView);
        imvCake = findViewById(R.id.imvACAView);
        spinner = findViewById(R.id.spinner);

        SetCatDB();
        SetCakeDB();


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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(Admin_add_cake.this,"Selected Category" + item,Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);

        updateSpinner();

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

             try{
                    CupCake cupCake = new CupCake();
                    cupCake.setCakeName(txtCakeName.getText().toString());
                    cupCake.setDescription(txtCakeDescription.getText().toString());
                    String selectedCategory = spinner.getSelectedItem().toString();
                    cupCake.setCategory(selectedCategory);
                    cupCake.setPrice(Double.valueOf(txtCakePrice.getText().toString()));
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    pic.compress(Bitmap.CompressFormat.JPEG,100,stream);
                    byte[] cover = stream.toByteArray();
                    cupCake.setCover(cover);

                    cupCake.Save(CakeDB);
                    Toast.makeText(getApplicationContext(),
                            "Item added", Toast.LENGTH_LONG).show();
                }catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Admin_Cake_List.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void SetCakeDB() {
        try {
            CakeDB=openOrCreateDatabase("CakeData",MODE_PRIVATE,null);
            CakeDB.execSQL("Create table if not exists" +
                    " cake(id integer primary key autoincrement" +
                    ",name text,description text,category text,price real,cover blob)");
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
}


