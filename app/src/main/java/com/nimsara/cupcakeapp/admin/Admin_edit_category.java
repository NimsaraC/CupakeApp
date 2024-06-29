package com.nimsara.cupcakeapp.admin;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nimsara.cupcakeapp.R;

public class Admin_edit_category extends AppCompatActivity {
    EditText edtName, edtDescription;
    Button btnBrowse, btnAdd, btnView;
    ImageView imvCat;
    private SQLiteDatabase CatDB;
    Bitmap pic;
    byte[] img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_category);

        edtName = findViewById(R.id.edtACatName);
        edtDescription = findViewById(R.id.edtACatDescription);
        btnBrowse = findViewById(R.id.btnACatIAdd);
        btnAdd = findViewById(R.id.btnACatAdd);
        btnView = findViewById(R.id.btnACatView);
        imvCat = findViewById(R.id.imvACatAView);

        SetCatDB();
        SelectData();
        getData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Admin_edit_category.this);
                builder.setTitle("Confirm Edit");
                builder.setMessage("Are you sure you want to edit this cake?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name1 = getIntent().getStringExtra("name");

                        String newName = edtName.getText().toString().trim();
                        String newDescription = edtDescription.getText().toString().trim();

                        ContentValues values = new ContentValues();
                        values.put("name", newName);
                        values.put("description", newDescription);
                        values.put("cover", img);

                        int rowsAffected = CatDB.update("Cat", values, "name = ?", new String[]{name1});

                        if (rowsAffected > 0) {
                            Toast.makeText(Admin_edit_category.this, "Category updated successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Admin_edit_category.this, Admin_Category_List.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Admin_edit_category.this, "Failed to update category", Toast.LENGTH_SHORT).show();
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

    }

    public void SelectData(){

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

        btnBrowse.setOnClickListener(new View.OnClickListener() {
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

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_edit_category.this, Admin_Cake_List.class);
                startActivity(intent);
            }
        });

    }

    public void getData(){
        if(getIntent().hasExtra("name")){
            edtName.setText(getIntent().getStringExtra("name"));
            edtDescription.setText(getIntent().getStringExtra("description"));
            img = getIntent().getByteArrayExtra("cover");
            Bitmap bitmap = BitmapFactory.decodeByteArray(img,0, img.length);
            imvCat.setImageBitmap(bitmap);
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