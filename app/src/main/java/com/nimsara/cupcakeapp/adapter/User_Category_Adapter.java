package com.nimsara.cupcakeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nimsara.cupcakeapp.Categories;
import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.user.Category_cakes;

import java.util.List;

public class User_Category_Adapter extends RecyclerView.Adapter<User_Category_Adapter.ViewHolder> {
    SQLiteDatabase db;
    List<Categories> categories;
    Context context;
    public User_Category_Adapter(Context context, SQLiteDatabase _db, List<Categories> categories1){
        this.context = context;
        db = _db;
        categories = categories1;
    }
    @NonNull
    @Override
    public User_Category_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View items = inflater.inflate(R.layout.user_category_view,parent,false);
        ViewHolder holder = new ViewHolder(items);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull User_Category_Adapter.ViewHolder holder, int position) {

        Categories categories2 = categories.get(position);
        holder.CName.setText(categories2.getCategoryName());
        Bitmap bitmap = BitmapFactory.decodeByteArray(categories2.getCatCover(),0,categories2.getCatCover().length);
        holder.CCover.setImageBitmap(bitmap);

        holder.btnCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Category_cakes.class);
                intent.putExtra("name", categories2.getCategoryName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView CName;
        ImageView CCover;
        LinearLayout btnCategorie;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CName = itemView.findViewById(R.id.textView12);
            CCover = itemView.findViewById(R.id.imageView2);
            btnCategorie = itemView.findViewById(R.id.btnCategorie);
        }
    }
}
