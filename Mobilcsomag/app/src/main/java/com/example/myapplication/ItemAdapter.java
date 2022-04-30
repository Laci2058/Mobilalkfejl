package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private ArrayList<Item> itemList;
    private Context context;

    public ItemAdapter(Context context, ArrayList<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = itemList.get(position).getNev();
        String leiras = itemList.get(position).getLeiras();
        String ar = itemList.get(position).getAr();

        holder.nameText.setText(name);
        holder.desText.setText(leiras);
        holder.priceText.setText(ar + "$");
        Glide.with(context).load(itemList.get(position).getImgresource()).into(holder.itemImg);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView desText;
        private TextView priceText;
        private ImageView itemImg;

        public MyViewHolder(@NonNull View view) {
            super(view);
            nameText = view.findViewById(R.id.Nev);
            desText = view.findViewById(R.id.Leiras);
            priceText = view.findViewById(R.id.Ar);
            itemImg = view.findViewById(R.id.item_kep);
            view.findViewById(R.id.kosarba).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Activity", "kosárba");
                    ((ShopActivity) context).updateAlertIcon();
                }
            });
        }


    }
}
