package com.nimsara.cupcakeapp.adapter;

import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nimsara.cupcakeapp.CupCake;
import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.user.User_cake_one_view;

import java.util.List;

public class User_Cake_Adapter extends RecyclerView.Adapter<User_Cake_Adapter.ViewHolder> {
    SQLiteDatabase db;
    List<CupCake> cakes;
    private Context context;

    public User_Cake_Adapter(Context context, SQLiteDatabase _db, List<CupCake> cakes2){
        db = _db;
        cakes = cakes2;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View items = inflater.inflate(R.layout.user_cake_view, parent, false);
        return new ViewHolder(items);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CupCake cupCake = cakes.get(position);
        holder.CName.setText(cupCake.getCakeName());
        holder.CCategory.setText(cupCake.getCategory());
        holder.CPrice.setText(String.valueOf(cupCake.getPrice()));

        Bitmap bitmap = BitmapFactory.decodeByteArray(cupCake.getCover(), 0, cupCake.getCover().length);
        holder.CCover.setImageBitmap(bitmap);

        holder.btnmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, User_cake_one_view.class);
                intent.putExtra("id", cupCake.getItemID());
                intent.putExtra("name", cupCake.getCakeName());
                intent.putExtra("price", cupCake.getPrice());
                intent.putExtra("description", cupCake.getDescription());
                intent.putExtra("category", cupCake.getCategory());
                intent.putExtra("cover", cupCake.getCover());
                ((Activity) context).setResult(Activity.RESULT_OK, intent);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cakes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView CName, CCategory, CPrice;
        ImageView CCover;
        Button btnmore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CName = itemView.findViewById(R.id.txtCName);
            CCategory = itemView.findViewById(R.id.txtCCat);
            CPrice = itemView.findViewById(R.id.txtCPrice);
            CCover = itemView.findViewById(R.id.imvCview);
            btnmore = itemView.findViewById(R.id.btnCakeMore);
        }
    }
}