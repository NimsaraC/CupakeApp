package com.nimsara.cupcakeapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nimsara.cupcakeapp.Categories;
import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.admin.Admin_edit_category;

import java.util.List;

public class Admin_category_adapter extends RecyclerView.Adapter<Admin_category_adapter.ViewHolder> {
    SQLiteDatabase db;
    List<Categories> categories;
    private Context context;
    public Admin_category_adapter(Context context, SQLiteDatabase _db, List<Categories> categories1){
        db = _db;
        categories = categories1;
        this.context = context;

    }
    @NonNull
    @Override
    public Admin_category_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View items = inflater.inflate(R.layout.admin_category_view,parent,false);
        ViewHolder holder = new ViewHolder(items);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_category_adapter.ViewHolder holder, int position) {
        Categories categories2 = categories.get(position);
        holder.CName.setText(categories2.getCategoryName());
        holder.CDescription.setText(categories2.getCatDescription());

        Bitmap bitmap = BitmapFactory.decodeByteArray(categories2.getCatCover(),0,categories2.getCatCover().length);
        holder.CCover.setImageBitmap(bitmap);

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Admin_edit_category.class);
                intent.putExtra("name",categories2.getCategoryName());
                intent.putExtra("description",categories2.getCatDescription());
                intent.putExtra("cover",categories2.getCatCover());
                ((Activity) context).setResult(Activity.RESULT_OK, intent);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView CName,CDescription;
        ImageView CCover;
        Button btnMore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CName = itemView.findViewById(R.id.txtACName);
            CDescription = itemView.findViewById(R.id.txtACCatDes);
            CCover = itemView.findViewById(R.id.imvAcatview);
            btnMore = itemView.findViewById(R.id.btnACatmore);
        }
    }
}
