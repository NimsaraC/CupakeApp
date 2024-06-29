package com.nimsara.cupcakeapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nimsara.cupcakeapp.R;

import java.util.ArrayList;

public class User_OrderC_Adapter extends RecyclerView.Adapter<User_OrderC_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<String> name, price, quantity;
    private ArrayList<Bitmap> cover;
    public User_OrderC_Adapter(Context context, ArrayList<String> name, ArrayList<String> price, ArrayList<String> quantity, ArrayList<Bitmap> cover) {
        this.context = context;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.cover = cover;
    }
    @NonNull
    @Override
    public User_OrderC_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View items = inflater.inflate(R.layout.order_confirm_item, parent, false);
        return new User_OrderC_Adapter.ViewHolder(items);
    }

    @Override
    public void onBindViewHolder(@NonNull User_OrderC_Adapter.ViewHolder holder, int position) {
        holder.name.setText(String.valueOf(name.get(position)));
        holder.price.setText("lkr "+ price.get(position));
        holder.quantity.setText(String.valueOf(quantity.get(position)));
        holder.cover.setImageBitmap(cover.get(position));
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView name,quantity,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.CartCakeImv);
            name = itemView.findViewById(R.id.CartCakeName);
            quantity = itemView.findViewById(R.id.CartCakeQt);
            price = itemView.findViewById(R.id.CartCakePrice);
        }
    }
}
