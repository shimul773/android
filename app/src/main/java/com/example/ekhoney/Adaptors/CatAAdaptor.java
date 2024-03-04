package com.example.ekhoney.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ekhoney.Modals.CatAModel;
import com.example.ekhoney.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CatAAdaptor extends RecyclerView.Adapter<CatAAdaptor.CatAViewHolder>{

    Context context;
    ArrayList<CatAModel> catAModelArrayList;

    private OnCataClicklistener onCataClicklistener;

    public interface OnCataClicklistener{
        void OnCataclick(int position);
    }

    public void setOnCataClicklistener(OnCataClicklistener onCataClicklistener) {
        this.onCataClicklistener = onCataClicklistener;
    }

    public CatAAdaptor(Context context, ArrayList<CatAModel> catAModelArrayList) {
        this.context = context;
        this.catAModelArrayList = catAModelArrayList;
    }

    @NonNull
    @Override
    public CatAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_single_cata, parent, false);
        return new CatAViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatAViewHolder holder, int position) {

        holder.cata_title.setText(catAModelArrayList.get(position).getTitle());
        Glide.with(context).load(catAModelArrayList.get(position).getImg()).into(holder.cata_img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCataClicklistener != null){
                    onCataClicklistener.OnCataclick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return catAModelArrayList.size();
    }

    public class CatAViewHolder extends RecyclerView.ViewHolder {

        CircleImageView cata_img;
        TextView cata_title;
        public CatAViewHolder(@NonNull View itemView) {
            super(itemView);
            cata_img = itemView.findViewById(R.id.cata_img);
            cata_title = itemView.findViewById(R.id.cata_title);
        }
    }
}
