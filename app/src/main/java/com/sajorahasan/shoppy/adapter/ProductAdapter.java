package com.sajorahasan.shoppy.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sajorahasan.shoppy.Constants;
import com.sajorahasan.shoppy.ProductDetailActivity;
import com.sajorahasan.shoppy.R;
import com.sajorahasan.shoppy.model.Pojo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProductAdapter extends BaseAdapter {

    Context context;
    ArrayList<Pojo> pojos;

    public ProductAdapter(Context context, ArrayList<Pojo> pojos) {
        this.pojos = pojos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return pojos.size();
    }

    @Override
    public Object getItem(int position) {
        return pojos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_product, null);

            viewHolder = new ViewHolder();

            viewHolder.proImage = (ImageView) convertView.findViewById(R.id.productImage);
            viewHolder.proTitle = (TextView) convertView.findViewById(R.id.productTitle);

            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.proTitle.setText(pojos.get(position).getProduct_title());
        final int id = pojos.get(position).getProduct_id();
        viewHolder.proImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "position" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context.getApplicationContext(), ProductDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("product_id", String.valueOf(id));
                intent.putExtra("product_name", pojos.get(position).getProduct_title());
                context.startActivity(intent);
            }
        });

        Pojo pj = (Pojo) getItem(position);
        String url = Constants.AdminPageURL;

        Picasso.with(context)
                .load(url + pojos.get(position).getProduct_image())
                .fit()
                .into(viewHolder.proImage);

        return convertView;
    }

    private class ViewHolder {
        ImageView proImage;
        TextView proTitle;

    }
}
