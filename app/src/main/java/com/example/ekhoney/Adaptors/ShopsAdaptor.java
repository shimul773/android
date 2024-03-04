package com.example.ekhoney.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ekhoney.Modals.ShopsModal;
import com.example.ekhoney.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShopsAdaptor extends RecyclerView.Adapter<ShopsAdaptor.ShopsViewHolder>{
    Context context;
    ArrayList<ShopsModal> shopsModalArrayList;

    private OnShopclicklistener onShopclicklistener;

    public interface OnShopclicklistener{
        void OnShopclick(int position);
    }

    public void setOnShopclicklistener(OnShopclicklistener onShopclicklistener) {
        this.onShopclicklistener = onShopclicklistener;
    }

    public ShopsAdaptor(Context context, ArrayList<ShopsModal> shopsModalArrayList) {
        this.context = context;
        this.shopsModalArrayList = shopsModalArrayList;
    }

    @NonNull
    @Override
    public ShopsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_shop, parent, false);
        return new ShopsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopsViewHolder holder, int position) {

        holder.shop_title.setText(shopsModalArrayList.get(position).getTitle());
        Glide.with(context).load(shopsModalArrayList.get(position).getImg()).into(holder.shop_img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onShopclicklistener != null){
                    onShopclicklistener.OnShopclick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopsModalArrayList.size();
    }

    public class ShopsViewHolder extends RecyclerView.ViewHolder {
        CircleImageView shop_img;
        TextView shop_title;
        public ShopsViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_img = itemView.findViewById(R.id.shop_img);
            shop_title = itemView.findViewById(R.id.shop_title);
        }
    }
}
