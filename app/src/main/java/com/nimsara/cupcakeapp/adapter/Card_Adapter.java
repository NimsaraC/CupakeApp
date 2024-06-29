package com.nimsara.cupcakeapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.user.User_Order_Confirmation;

import java.util.ArrayList;

public class Card_Adapter extends RecyclerView.Adapter<Card_Adapter.ViewHolder> {

    ArrayList CName,CDate,CNumber,CCvv;
    Context context;
    public Card_Adapter(Context context, ArrayList CName, ArrayList CDate, ArrayList CNumber, ArrayList CCvv){
        this.context =  context;
        this.CName = CName;
        this.CDate = CDate;
        this.CNumber = CNumber;
        this.CCvv = CCvv;
    }
    @NonNull
    @Override
    public Card_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Card_Adapter.ViewHolder holder, int position) {
        holder.CVNumber.setText(CNumber.get(position).toString());
        holder.CVDate.setText(CDate.get(position).toString());

        holder.selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, User_Order_Confirmation.class);
                intent.putExtra("cname", CName.get(position).toString());
                intent.putExtra("date", CDate.get(position).toString());
                intent.putExtra("cnumber", CNumber.get(position).toString());
                intent.putExtra("cvv", CCvv.get(position).toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return CNumber.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView CVNumber,CVDate,selectButton,editButton,deleteButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CVNumber = itemView.findViewById(R.id.CVNumber);
            CVDate = itemView.findViewById(R.id.CVDate);
            selectButton = itemView.findViewById(R.id.selectButton);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
