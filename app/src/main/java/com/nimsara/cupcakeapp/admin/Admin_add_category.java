package com.nimsara.cupcakeapp.admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nimsara.cupcakeapp.Categories;
import com.nimsara.cupcakeapp.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Admin_add_category extends AppCompatActivity {

    EditText edtName, edtDescription;
    Button btnBrowse, btnAdd, btnView;
    ImageView imvCat;
    SQLiteDatabase CatDB;
    Bitmap pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_category);

        edtName = findViewById(R.id.edtACatName);
        edtDescription = findViewById(R.id.edtACatDescription);
        btnBrowse = findViewById(R.id.btnACatIAdd);
        btnAdd = findViewById(R.id.btnACatAdd);
        btnView = findViewById(R.id.btnACatView);
        imvCat = findViewById(R.id.imvACatAView);



        SetCatDB();
        ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                Intent intent = o.getData();
                pic = (Bitmap) intent.getExtras().get("data");
                imvCat.setImageBitmap(pic);
            }
        });
        ActivityResultLauncher launcher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                Intent intent = o.getData();
                Uri uri = intent.getData();
                imvCat.setImageURI(uri);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Categories categories = new Categories();
                    categories.setCategoryName(edtName.getText().toString());
                    categories.setCatDescription(edtDescription.getText().toString());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    pic.compress(Bitmap.CompressFormat.PNG,100,stream);
                    byte[] cover = stream.toByteArray();
                    stream.close();
                    categories.setCatCover(cover);

                    categories.Save(CatDB);
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
                Intent intent = new Intent(getApplicationContext(), Admin_Category_List.class);
                startActivity(intent);
            }
        });
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(btnAdd.getContext());
                builder.setMessage("Please select the option you wish to use !");
                builder.setTitle("Select a Cake image");
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