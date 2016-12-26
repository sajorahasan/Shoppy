package com.sajorahasan.shoppy.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sajorahasan.shoppy.R;
import com.sajorahasan.shoppy.model.Beanclass;

import java.util.ArrayList;

/**
 * Created by apple on 05/04/16.
 */
public class ListbaseAdapter extends BaseAdapter {


    Context context;
    ArrayList<Beanclass> bean;


    public ListbaseAdapter(Context context, ArrayList<Beanclass> bean) {


        this.context = context;
        this.bean = bean;

    }

    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder = null;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_category, null);

            viewHolder = new ViewHolder();


            viewHolder.image = (ImageView) convertView.findViewById(R.id.catImage);
            viewHolder.title = (TextView) convertView.findViewById(R.id.catTitle);
            viewHolder.subtitle = (TextView) convertView.findViewById(R.id.subtitle);
            viewHolder.shop = (TextView) convertView.findViewById(R.id.shop);

            convertView.setTag(viewHolder);


        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }


        Beanclass bean = (Beanclass) getItem(position);

        viewHolder.image.setImageResource(bean.getImage());
        viewHolder.title.setText(bean.getTitle());
        viewHolder.subtitle.setText(bean.getSubtitle());
        viewHolder.shop.setText(bean.getShop());

        return convertView;
    }

    class ViewHolder {
        ImageView image;
        TextView title;
        TextView subtitle;
        TextView shop;

    }
}




