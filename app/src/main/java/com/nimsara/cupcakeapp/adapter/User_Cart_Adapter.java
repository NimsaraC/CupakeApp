package com.nimsara.cupcakeapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.database.CartDB;
import com.nimsara.cupcakeapp.user.User_cart;

import java.util.ArrayList;

public class User_Cart_Adapter extends RecyclerView.Adapter<User_Cart_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<String> name, price, quantity;
    private ArrayList<Bitmap> cover;
    private double subtotal = 0.0;

    public User_Cart_Adapter(Context context, ArrayList<String> name, ArrayList<String> price, ArrayList<String> quantity, ArrayList<Bitmap> cover) {
        this.context = context;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.cover = cover;
    }
    @NonNull
    @Override
    public User_Cart_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View items = inflater.inflate(R.layout.user_cart_one_view, parent, false);
        return new User_Cart_Adapter.ViewHolder(items);
    }

    @Override
    public void onBindViewHolder(@NonNull User_Cart_Adapter.ViewHolder holder, int position) {
        holder.txtName.setText(String.valueOf(name.get(position)));
        holder.txtPrice.setText("lkr "+ price.get(position));
        holder.txtQnt.setText(String.valueOf(quantity.get(position)));
        holder.imvCover.setImageBitmap(cover.get(position));



        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to remove this item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String itemName = name.get(position);
                        CartDB cartDB = new CartDB(context);
                        cartDB.deleteCartItem(itemName);

                        name.remove(position);
                        price.remove(position);
                        quantity.remove(position);
                        cover.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemCount());

                        ((User_cart) context).updateSubtotal(calculateSubtotal());
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        int Number = Integer.parseInt(quantity.get(position));
        holder.setNumber(Number);

        holder.btnDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentNumber = holder.getNumber();
                if (currentNumber > 1) {
                    currentNumber--;
                    holder.setNumber(currentNumber);
                    holder.txtQnt.setText(String.valueOf(currentNumber));
                    quantity.set(position, String.valueOf(currentNumber));

                    notifyDataSetChanged();
                    calculateSubtotal();
                    updateQuantityInDatabase(position, String.valueOf(currentNumber));
                }
            }
        });

        holder.btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentNumber = holder.getNumber();
                currentNumber++;
                holder.setNumber(currentNumber);
                holder.txtQnt.setText(String.valueOf(currentNumber));
                quantity.set(position, String.valueOf(currentNumber));

                notifyDataSetChanged();
                calculateSubtotal();

                updateQuantityInDatabase(position, String.valueOf(currentNumber));
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtPrice,txtQnt,btnDe,btnIn;
        ImageView imvCover,btnDelete;

        private int Number;
        public void setNumber(int number) {
            Number = number;
        }
        public int getNumber() {
            return Number;
        }
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.CartName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQnt = itemView.findViewById(R.id.txtQt);
            imvCover = itemView.findViewById(R.id.imvCartCover);
            btnDelete = itemView.findViewById(R.id.imvDelete);
            btnDe = itemView.findViewById(R.id.btnDe);
            btnIn = itemView.findViewById(R.id.btnIn);
        }
    }

    private double calculateSubtotal() {
        subtotal = 0.0;
        for (int i = 0; i < quantity.size(); i++) {
            double itemPrice = Double.parseDouble(price.get(i));
            int itemQuantity = Integer.parseInt(quantity.get(i));
            subtotal += itemPrice * itemQuantity;
        }

        ((User_cart)context).updateSubtotal(subtotal);
        return subtotal;
    }

    private void updateQuantityInDatabase(int position, String newQuantity) {
        String itemName = name.get(position);
        CartDB cartDB = new CartDB(context);
        cartDB.updateCartItemQuantity(itemName, newQuantity);
    }
}

