package com.nimsara.cupcakeapp.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.admin.Admin_Order_View;
import com.nimsara.cupcakeapp.database.OrdersDB;
import com.nimsara.cupcakeapp.login.SharedPreference;
import com.nimsara.cupcakeapp.user.User_Orders;

import java.util.ArrayList;

public class Oreder_Adapter_User extends RecyclerView.Adapter<Oreder_Adapter_User.ViewHolder> {

    Context context;
    ArrayList OItem,OQty,OID,OStatus,OPrice;
    OrdersDB ordersDB;

    public Oreder_Adapter_User(Context context, ArrayList OItem, ArrayList OQty, ArrayList OID, ArrayList OStatus, ArrayList OPrice) {

        this.context =  context;
        this.OItem = OItem;
        this.OQty = OQty;
        this.OID = OID;
        this.OPrice = OPrice;
        this.OStatus = OStatus;

    }
    @NonNull
    @Override
    public Oreder_Adapter_User.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_order_view,parent,false);
        return new Oreder_Adapter_User.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Oreder_Adapter_User.ViewHolder holder, int position) {

        SharedPreference preference = new SharedPreference();

        String name = preference.getString(context, SharedPreference.NAME);
        String street = preference.getString(context,SharedPreference.STREET);
        String city = preference.getString(context,SharedPreference.CITY);
        String zip = preference.getString(context,SharedPreference.ZIP);

        holder.Name.setText(name);
        holder.Street.setText(street);
        holder.City.setText(city);
        holder.Zip.setText(zip);

        holder.Item.setText(OItem.get(position).toString());
        holder.Quantity.setText(OQty.get(position).toString());
        holder.ID.setText(OID.get(position).toString());
        holder.Status.setText(OStatus.get(position).toString());
        holder.Total.setText("lkr: "+OPrice.get(position).toString());

        String st = holder.Status.getText().toString();

        holder.Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                if (st.equals("Confirmed")){
                    builder.setMessage("Are you sure you want to confirm receipt of this order?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ordersDB = new OrdersDB(context);
                                    ordersDB.deleteOrder(Integer.parseInt(OID.get(position).toString()));
                                    ordersDB.editOrderStatus(Integer.parseInt(OID.get(position).toString()), "Confirmed");
                                    Intent intent = new Intent(context, User_Orders.class);
                                    context.startActivity(intent);
                                    ((Activity)context).finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    builder.setMessage("Order not confirmed yet!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }

            }
        });

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                if (!st.equals("Confirmed")){
                    builder.setMessage("Are you sure you want to Cancel & Delete this order?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ordersDB = new OrdersDB(context);
                                    ordersDB.deleteOrder(Integer.parseInt(OID.get(position).toString()));
                                    Intent intent = new Intent(context, User_Orders.class);
                                    context.startActivity(intent);
                                    ((Activity)context).finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            })
                            .show();
                }else{
                    builder.setMessage("Can't delete confirmed orders!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return OID.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name,Phone,Confirm,Cancel,Street,City,Zip,Item,Quantity,ID,Status,Total;
        ImageButton Delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Street = itemView.findViewById(R.id.street);
            City = itemView.findViewById(R.id.city);
            Zip = itemView.findViewById(R.id.zip);
            Name = itemView.findViewById(R.id.name);
            Phone = itemView.findViewById(R.id.number);
            Confirm = itemView.findViewById(R.id.confirm);
            Item = itemView.findViewById(R.id.item);
            Quantity = itemView.findViewById(R.id.quantity);
            ID = itemView.findViewById(R.id.id);
            Status = itemView.findViewById(R.id.status);
            Delete = itemView.findViewById(R.id.delete);
            Total = itemView.findViewById(R.id.total);
        }

    }
}
