package com.sajorahasan.shoppy;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sajorahasan.shoppy.adapter.CartAdapter;
import com.sajorahasan.shoppy.model.Bean;
import com.sajorahasan.shoppy.model.ProfileData;
import com.sajorahasan.shoppy.model.ShoppyBean;
import com.sajorahasan.shoppy.model.ShoppyProfile;

import java.util.ArrayList;

import customfonts.MyTextView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
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

    private MyTextView tvShippingName, tvShippingAdd, edit, contCheckout, cart_total, price_product;
    private String uId;
    private SharedPreferences pref;
    private CompositeDisposable mCompositeDisposable;
    private ArrayList<ProfileData> myDataSource1;
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
        tvShippingName = (MyTextView) view.findViewById(R.id.tvShippingName);
        tvShippingAdd = (MyTextView) view.findViewById(R.id.tvShippingAddress);
        edit = (MyTextView) view.findViewById(R.id.edit);
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

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment fragment = new ProfileFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        loadCart();
        mCompositeDisposable = new CompositeDisposable();
        loadShippingData();
        return view;
    }

    private void loadShippingData() {
        RequestInterface requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestInterface.class);

        mCompositeDisposable.add(requestInterface.getUserData(uId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));

    }

    private void handleResponse(ShoppyProfile shoppyProfile) {

        myDataSource1 = new ArrayList<>(shoppyProfile.getProfiledata());

        tvShippingName.setText(myDataSource1.get(0).getName());
        tvShippingAdd.setText(myDataSource1.get(0).getUserAddress() + ", "
                + myDataSource1.get(0).getUserPin() + ", "
                + myDataSource1.get(0).getUserCity());


    }

    private void handleError(Throwable throwable) {
        Toast.makeText(getActivity(), "Error " + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
