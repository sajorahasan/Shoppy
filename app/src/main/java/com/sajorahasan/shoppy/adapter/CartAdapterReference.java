//package com.sajorahasan.shoppy.adapter;
//
//import android.app.Activity;
//import android.app.Fragment;
//import android.app.FragmentTransaction;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.sajorahasan.shoppy.CartFragment;
//import com.sajorahasan.shoppy.Constants;
//import com.sajorahasan.shoppy.R;
//import com.sajorahasan.shoppy.RequestInterface;
//import com.sajorahasan.shoppy.model.Bean;
//import com.sajorahasan.shoppy.model.ShoppyBean;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
//import customfonts.MyTextView;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//import static com.sajorahasan.shoppy.Constants.BASE_URL;
//
///**
// * Created by Sajora on 30-12-2016.
// */
//
//public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
//    private static final String TAG = "CartAdapter";
//
//    ArrayList<Bean> arrayList = new ArrayList<>();
//    Context context;
//    private String id;
//    int total = 0;
//
//    public CartAdapter(ArrayList<Bean> arrayList, Context context) {
//        this.arrayList = arrayList;
//        this.context = context;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.list_cart, parent, false);
//        ViewHolder viewHolder = new ViewHolder(v);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, final int position) {
//
//        holder.title_product.setText(arrayList.get(position).getProductName());
//        holder.qty_product.setText(arrayList.get(position).getProductQty());
//        holder.price_product.setText(arrayList.get(position).getProductTotal());
//
//
//        total += Integer.parseInt(arrayList.get(position).getProductTotal());
//        Log.d(TAG, "onBindViewHolder: intent1 " + total);
//
//
//        Bundle bundle = new Bundle();
//        bundle.putString("total", String.valueOf(total));
//        //Log.d(TAG, "onBindViewHolder: intent1 " + String.valueOf(total));
//        CartFragment fragobj = new CartFragment();
//        fragobj.setArguments(bundle);
//
//
//        String url = Constants.AdminPageURL;
//
//        Picasso.with(context)
//                .load(url + arrayList.get(position).getProductImg())
//                .into(holder.img_product);
//
//        holder.remove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                id = arrayList.get(position).getId();
//                remItem();
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return arrayList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView img_product;
//        TextView title_product, qty_product, price_product;
//        Button remove;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            img_product = (ImageView) itemView.findViewById(R.id.img_product);
//            title_product = (MyTextView) itemView.findViewById(R.id.title_product);
//            qty_product = (MyTextView) itemView.findViewById(R.id.qty_product);
//            price_product = (MyTextView) itemView.findViewById(R.id.price_product);
//            remove = (Button) itemView.findViewById(R.id.remove);
//
//        }
//    }
//
//    private void remItem() {
//        RequestInterface requestInterface = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build().create(RequestInterface.class);
//
//        Call<ShoppyBean> call = requestInterface.remCart(id);
//        call.enqueue(new Callback<ShoppyBean>() {
//            @Override
//            public void onResponse(Call<ShoppyBean> call, Response<ShoppyBean> response) {
//
//                Toast.makeText(context, "Item Successfully removed from Cart!", Toast.LENGTH_SHORT).show();
//                Fragment fragment = new CartFragment();
//                //replacing the fragment
//                if (fragment != null) {
//                    FragmentTransaction ft = ((Activity) context).getFragmentManager().beginTransaction();
//                    ft.replace(R.id.fragment_container, fragment);
//                    ft.commit();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ShoppyBean> call, Throwable t) {
//
//            }
//        });
//    }
//}
