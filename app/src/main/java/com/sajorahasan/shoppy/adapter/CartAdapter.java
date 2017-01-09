package com.sajorahasan.shoppy.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.sajorahasan.shoppy.CartFragment;
import com.sajorahasan.shoppy.Constants;
import com.sajorahasan.shoppy.R;
import com.sajorahasan.shoppy.RequestInterface;
import com.sajorahasan.shoppy.model.Bean;
import com.sajorahasan.shoppy.model.ShoppyBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import customfonts.MyTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sajorahasan.shoppy.Constants.BASE_URL;

/**
 * Created by Sajora on 18-11-2016.
 */
public class CartAdapter extends BaseAdapter {
    private static final String TAG = "CartAdapter";

    int cartId;
    ArrayList<Bean> arrayList = new ArrayList<>();
    Context context;
    private String id;
    int total = 0;

    public CartAdapter(ArrayList<Bean> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ImageView img_product;
        TextView title_product, qty_product, price_product;
        Button remove;

        final Bean pojo = arrayList.get(i);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_cart, null);

        img_product = (ImageView) view.findViewById(R.id.img_product);
        title_product = (MyTextView) view.findViewById(R.id.title_product);
        qty_product = (MyTextView) view.findViewById(R.id.qty_product);
        price_product = (MyTextView) view.findViewById(R.id.price_product);
        remove = (Button) view.findViewById(R.id.remove);

        title_product.setText(arrayList.get(i).getProductName());
        qty_product.setText(arrayList.get(i).getProductQty());
        price_product.setText(arrayList.get(i).getProductTotal());

        String url = Constants.AdminPageURL;

        Picasso.with(context)
                .load(url + arrayList.get(i).getProductImg())
                .into(img_product);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = arrayList.get(i).getId();
                remItem();
            }
        });
        return view;
    }

    private void remItem() {
        RequestInterface requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestInterface.class);

        Call<ShoppyBean> call = requestInterface.remCart(id);
        call.enqueue(new Callback<ShoppyBean>() {
            @Override
            public void onResponse(Call<ShoppyBean> call, Response<ShoppyBean> response) {

                Toast.makeText(context, "Item Successfully removed from Cart!", Toast.LENGTH_SHORT).show();
                Fragment fragment = new CartFragment();
                //replacing the fragment
                if (fragment != null) {
                    FragmentTransaction ft = ((Activity) context).getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, fragment);
                    ft.commit();
                }
            }

            @Override
            public void onFailure(Call<ShoppyBean> call, Throwable t) {

            }
        });
    }
}
