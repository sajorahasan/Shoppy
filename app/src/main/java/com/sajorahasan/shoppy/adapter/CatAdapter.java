package com.sajorahasan.shoppy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sajorahasan.shoppy.Constants;
import com.sajorahasan.shoppy.ItemClickListener;
import com.sajorahasan.shoppy.ProductActivity;
import com.sajorahasan.shoppy.R;
import com.sajorahasan.shoppy.model.Pojo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sajora on 11-12-2016.
 */
public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {

    ArrayList<Pojo> arrayList = new ArrayList<>();
    Context context;

    public CatAdapter(Context context, ArrayList<Pojo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final int id = arrayList.get(position).getCat_id();
        holder.tvTitle.setText(arrayList.get(position).getCat_name());

        String url = Constants.AdminPageURL;

        Picasso.with(context)
                .load(url + arrayList.get(position).getCat_image())
                .into(holder.ivThumbnail);

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context, "position" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context.getApplicationContext(), ProductActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("cat_id", String.valueOf(id));
                intent.putExtra("cat_name", arrayList.get(position).getCat_name());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ivThumbnail;
        public TextView tvTitle;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.catImage);
            tvTitle = (TextView) itemView.findViewById(R.id.catTitle);
            itemView.setOnClickListener(this);
        }

        private void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition());
        }
    }

}
