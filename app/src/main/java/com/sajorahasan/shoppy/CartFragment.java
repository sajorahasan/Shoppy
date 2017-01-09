package com.sajorahasan.shoppy;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sajorahasan.shoppy.adapter.CartAdapter;
import com.sajorahasan.shoppy.model.Bean;
import com.sajorahasan.shoppy.model.ShoppyBean;

import java.util.ArrayList;

import customfonts.MyTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sajorahasan.shoppy.Constants.BASE_URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private static final String TAG = "CartFragment";

    private MyTextView contCheckout, cart_total, price_product;
    private String uId;
    private SharedPreferences pref;
    private ArrayList<Bean> myDataSource;

    private ListView listView;
    int sum = 0;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        listView = (ListView) view.findViewById(R.id.listView);
        contCheckout = (MyTextView) view.findViewById(R.id.contCheckout);
        cart_total = (MyTextView) view.findViewById(R.id.cart_total);

        //Creating a shared preference
        pref = getActivity().getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        uId = pref.getString(Constants.SNO, "");

        contCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CheckoutActivity.class);
                getActivity().startActivity(i);
            }
        });

        loadCart();

        return view;
    }

    private void loadCart() {
        RequestInterface requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestInterface.class);

        Call<ShoppyBean> call = requestInterface.getCart(uId);
        call.enqueue(new Callback<ShoppyBean>() {
            @Override
            public void onResponse(Call<ShoppyBean> call, Response<ShoppyBean> response) {
                myDataSource = new ArrayList<>(response.body().getCart());
                CartAdapter adapter = new CartAdapter(myDataSource, getActivity());
                listView.setAdapter(adapter);

                for (int j = 0; j < myDataSource.size(); j++) {

                    View v = listView.getAdapter().getView(j, null, null);
                    price_product = (MyTextView) v.findViewById(R.id.price_product);
                    sum = sum + Integer.parseInt(price_product.getText().toString());
                    cart_total.setText(String.valueOf(sum));
                }

            }

            @Override
            public void onFailure(Call<ShoppyBean> call, Throwable t) {

            }
        });
    }
}
