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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nimsara.cupcakeapp.CupCake;
import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.admin.Admin_cake_one_view;

import java.util.List;

public class Admin_cake_adapter extends RecyclerView.Adapter<Admin_cake_adapter.ViewHolder> {
    SQLiteDatabase db;
    List<CupCake> cakes;
    private Context context;

    public Admin_cake_adapter(Context context,SQLiteDatabase _db, List<CupCake> cakes1){
        db = _db;
        cakes = cakes1;
        this.context = context;
    }
    @NonNull
    @Override
    public Admin_cake_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View items = inflater.inflate(R.layout.admin_cake_view,parent,false);
        ViewHolder holder = new ViewHolder(items);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_cake_adapter.ViewHolder holder, int position) {

        CupCake cupCake = cakes.get(position);
        holder.CName.setText(cupCake.getCakeName());
        holder.CCategory.setText(cupCake.getCategory());
        holder.CPrice.setText(String.valueOf(cupCake.getPrice()));

        Bitmap bitmap = BitmapFactory.decodeByteArray(cupCake.getCover(),0,cupCake.getCover().length);
        holder.CCover.setImageBitmap(bitmap);

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Admin_cake_one_view.class);
                intent.putExtra("id", cupCake.getItemID());
                intent.putExtra("name", cupCake.getCakeName());
                intent.putExtra("price", holder.CPrice.getText());
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView CName,CCategory,CPrice;
        ImageView CCover;
        LinearLayout ACakeRow;
        Button btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CName = itemView.findViewById(R.id.txtACName);
            CCategory = itemView.findViewById(R.id.txtACCat);
            CPrice = itemView.findViewById(R.id.txtACPrice);
            CCover = itemView.findViewById(R.id.imvACview);
            ACakeRow = itemView.findViewById(R.id.CakeRowAdmin);
            btn = itemView.findViewById(R.id.btnACakeMore);
        }
    }
}
