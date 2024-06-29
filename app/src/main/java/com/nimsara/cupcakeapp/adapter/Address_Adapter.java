package com.nimsara.cupcakeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.login.SharedPreference;
import com.nimsara.cupcakeapp.user.User_Order_Confirmation;

import java.util.ArrayList;

public class Address_Adapter extends RecyclerView.Adapter<Address_Adapter.ViewHolder> {
    ArrayList UName,UPhone,UStreet,UCity,UPostal;
    Context context;
    public Address_Adapter(Context context, ArrayList UName, ArrayList UPhone, ArrayList UStreet, ArrayList UCity, ArrayList UPostal){
        this.context =  context;
        this.UName = UName;
        this.UPhone = UPhone;
        this.UStreet = UStreet;
        this.UCity = UCity;
        this.UPostal = UPostal;
    }
    @NonNull
    @Override
    public Address_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.address_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Address_Adapter.ViewHolder holder, int position) {

        SharedPreference preference = new SharedPreference();

        holder.AddName.setText(UName.get(position).toString());
        holder.AddPhone.setText(UPhone.get(position).toString());
        holder.AddStreet.setText(UStreet.get(position).toString());
        holder.AddCity.setText((CharSequence) UCity.get(position));
        holder.AddPostal.setText((CharSequence) UPostal.get(position));

        holder.selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, User_Order_Confirmation.class);
                intent.putExtra("name", UName.get(position).toString());
                intent.putExtra("phone", UPhone.get(position).toString());
                intent.putExtra("street", UStreet.get(position).toString());
                intent.putExtra("city", UCity.get(position).toString());
                intent.putExtra("postal", UPostal.get(position).toString());

                preference.SaveString(context,UName.get(position).toString(),SharedPreference.NAME);
                preference.SaveString(context,UPhone.get(position).toString(),SharedPreference.PHONE);
                preference.SaveString(context,UStreet.get(position).toString(),SharedPreference.STREET);
                preference.SaveString(context,UCity.get(position).toString(),SharedPreference.CITY);
                preference.SaveString(context,UPostal.get(position).toString(),SharedPreference.ZIP);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return UName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView AddName,AddPhone,AddStreet,AddCity,AddPostal,editButton,deleteAddress,selectButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            AddName = itemView.findViewById(R.id.AddName);
            AddPhone = itemView.findViewById(R.id.AddPhone);
            AddStreet = itemView.findViewById(R.id.AddStreet);
            AddCity = itemView.findViewById(R.id.AddCity);
            AddPostal = itemView.findViewById(R.id.AddPostal);
            editButton = itemView.findViewById(R.id.editButton);
            selectButton = itemView.findViewById(R.id.selectButton);
        }
    }
}